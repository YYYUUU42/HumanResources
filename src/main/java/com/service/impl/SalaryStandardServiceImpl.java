package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.ResponseResult;
import com.domain.dto.salarySelectDto;
import com.domain.entity.*;
import com.domain.vo.PageVo;
import com.domain.vo.SalaryVo;
import com.service.SalaryStandardService;
import com.mapper.SalaryStandardMapper;
import com.utils.BeanCopyUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yu
 * @description 针对表【salary_standard(薪酬标准表)】的数据库操作Service实现
 * @createDate 2022-12-01 19:44:14
 */
@Service
public class SalaryStandardServiceImpl extends ServiceImpl<SalaryStandardMapper, SalaryStandard> implements SalaryStandardService {

    @Resource
    private SalaryStandardService salaryStandardService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SalaryStandardMapper salaryStandardMapper;

    @Override
    public ResponseResult getBookInfo() {
        SalaryStandard salary = new SalaryStandard(null, "", "", "", "", 0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0, "", 0, 0.0, "", "");
        salaryStandardService.save(salary);
        //得到刚刚插入的薪酬标准Id
        Integer salaryId = salary.getSalaryId();

        //得到当前的登录人和当前时间
        String o = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(o, User.class);
        String booker = login.getEname();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ' ' HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());
        String bookTime = formatter.format(date);
        return ResponseResult.okResult(new BookerTime(booker, bookTime, salaryId));
    }

    /**
     * 用来存储搜索后的数据
     */
    private List<SalaryStandard> salaryStandards;

    @Override
    public ResponseResult salarySelect(salarySelectDto dto) throws Exception {
        //为了防止重复，使用set集合
        Set<SalaryStandard> set = new HashSet<>();

        //如果填写了salaryId，就加入set中
        if (StringUtils.hasText(String.valueOf(dto.getSalaryId()))) {
            List<SalaryStandard> standard1 = getBaseMapper().salaryIdLike(dto.getSalaryId());

            for (SalaryStandard salaryStandard : standard1 ){
                set.add(salaryStandard);
            }
        }

        //如果填写了关键词
        if (StringUtils.hasText(dto.getKeyWord())) {
            //薪酬标准名称
            LambdaQueryWrapper<SalaryStandard> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.like(SalaryStandard::getSalaryName, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 0);

            List<SalaryStandard> list1 = removeByTime(salaryStandardService.list(queryWrapper1), dto);
            for (SalaryStandard standard1 : list1) {
                set.add(standard1);
            }

            //制定人
            LambdaQueryWrapper<SalaryStandard> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.like(SalaryStandard::getBooker, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 0);

            List<SalaryStandard> list2 = removeByTime(salaryStandardService.list(queryWrapper2), dto);
            for (SalaryStandard standard2 : list2) {
                set.add(standard2);
            }

            //变更人
            LambdaQueryWrapper<SalaryStandard> queryWrapper3 = new LambdaQueryWrapper<>();
            queryWrapper3.like(SalaryStandard::getUpdatePeople, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 0);

            List<SalaryStandard> list3 = removeByTime(salaryStandardService.list(queryWrapper3), dto);
            for (SalaryStandard standard3 : list3) {
                set.add(standard3);
            }

            //复核人
            LambdaQueryWrapper<SalaryStandard> queryWrapper4 = new LambdaQueryWrapper<>();
            queryWrapper4.like(SalaryStandard::getReexaminePeople, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 0);

            List<SalaryStandard> list4 = removeByTime(salaryStandardService.list(queryWrapper4), dto);
            for (SalaryStandard standard4 : list4) {
                set.add(standard4);
            }

        }

        List<SalaryStandard> salary = new ArrayList<>();
        for (SalaryStandard standard : set) {
            salary.add(standard);
        }

        salaryStandards = salary;

        return ResponseResult.okResult();
    }

    /**
     * 用来去掉不符合限定时间的数据
     *
     * @param list
     * @param dto
     * @return
     * @throws ParseException
     */
    private List<SalaryStandard> removeByTime(List<SalaryStandard> list, salarySelectDto dto) throws ParseException {
        Iterator<SalaryStandard> iterator = list.iterator();


        //将时间不是限定范围的删掉
        while (iterator.hasNext()) {
            SalaryStandard next = iterator.next();


            //得到登记时间，并将此转换为Date类型
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date bookTime = formatter.parse(next.getBookTime());


            Date start = dto.getOpenTime();
            Date end = dto.getEndTime();



            //如果开始时间和结束时间都存在
            if (Objects.isNull(dto.getOpenTime()) && Objects.isNull(dto.getEndTime())) {
                if (!(bookTime.before(end) && bookTime.after(start))) {
                    iterator.remove();
                }
            }

            //只有开始时间
            if (Objects.isNull(dto.getOpenTime()) && (!Objects.isNull(dto.getEndTime()))) {
                if (!(bookTime.after(start))) {
                    iterator.remove();
                }
            }

            //只有结束时间
            if ((!Objects.isNull(dto.getOpenTime()) && Objects.isNull(dto.getEndTime()))) {
                if (!(bookTime.before(end))) {
                    iterator.remove();
                }
            }
        }

        return list;
    }

    @Override
    public ResponseResult salaryRegistered(SalaryStandard salaryStandard) {
        Integer basic = salaryStandard.getBasic();
        salaryStandard.setEndowmentInsurance(basic * 0.08);
        salaryStandard.setMedical(basic * 0.02 + 3);
        salaryStandard.setHousingFund(basic * 0.08);
        salaryStandard.setUnemployment(basic * 0.05);

        salaryStandardService.updateById(salaryStandard);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult salaryList() {
        List<SalaryVo> salaryVos = new ArrayList<>();

        //将信息复制到vo中
        for (SalaryStandard salaryStandard : salaryStandards) {
            Double total = salaryStandard.getBasic() * (1 + 0.185) + salaryStandard.getLunch() + salaryStandard.getCommunication() + salaryStandard.getTraffic() + 3;
            SalaryVo salaryVo = BeanCopyUtils.copyBean(salaryStandard, SalaryVo.class);
            salaryVo.setTotal(total);
            salaryVos.add(salaryVo);
        }
        return ResponseResult.okResult(new PageVo(salaryVos, (long) salaryVos.size()));
    }

    @Override
    public ResponseResult getReexamine(Integer salaryId) {
        SalaryStandard salaryStandard = salaryStandardService.getById(salaryId);
        //得到复核人
        String o = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(o, User.class);
        String reexaminePeople = login.getEname();

        salaryStandard.setReexaminePeople(reexaminePeople);
        salaryStandard.setTotal(salaryStandard.getBasic() * (1 + 0.185) + salaryStandard.getLunch() + salaryStandard.getCommunication() + salaryStandard.getTraffic() + 3);
        return ResponseResult.okResult(salaryStandard);
    }


    @Override
    public ResponseResult salaryReexamine(SalaryStandard salaryStandard) {
        String o = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(o, User.class);
        String reexaminePeople = login.getEname();
        salaryStandard.setReexaminePeople(reexaminePeople);

        //就其变成已复核状态
        salaryStandard.setReexamineState(1);
        salaryStandardService.updateById(salaryStandard);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUpdate(Integer updateId) {
        SalaryStandard salaryStandard = salaryStandardService.getById(updateId);
        //得到复核人
        String o = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(o, User.class);
        String updatePeople = login.getEname();

        salaryStandard.setUpdatePeople(updatePeople);
        salaryStandard.setTotal(salaryStandard.getBasic() * (1 + 0.185) + salaryStandard.getLunch() + salaryStandard.getCommunication() + salaryStandard.getTraffic() + 3);
        return ResponseResult.okResult(salaryStandard);
    }

    @Override
    public ResponseResult salaryUpdate(SalaryStandard salaryStandard) {
        String o = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(o, User.class);
        String updatePeople = login.getEname();
        salaryStandard.setUpdatePeople(updatePeople);

        salaryStandard.setReexamineState(0);
        salaryStandardService.updateById(salaryStandard);
        return ResponseResult.okResult();
    }


    /**
     * 用来存储搜索后的数据(更改)
     */
    private List<SalaryStandard> salaryStandardsUpdate;

    @Override
    public ResponseResult salaryUpdateSelect(salarySelectDto dto) throws Exception {
        //为了防止重复，使用set集合
        Set<SalaryStandard> set = new HashSet<>();

        //如果填写了salaryId，就加入set中
        if (StringUtils.hasText(String.valueOf(dto.getSalaryId()))) {
            List<SalaryStandard> standard1 = salaryStandardMapper.salaryIdLike(dto.getSalaryId());

            for (SalaryStandard salaryStandard : standard1) {
                set.add(salaryStandard);
            }
        }

        //如果填写了关键词
        if (StringUtils.hasText(dto.getKeyWord())) {
            //薪酬标准名称
            LambdaQueryWrapper<SalaryStandard> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.like(SalaryStandard::getSalaryName, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 1);

            List<SalaryStandard> list1 = removeByTime(salaryStandardService.list(queryWrapper1), dto);
            for (SalaryStandard standard1 : list1) {
                set.add(standard1);
            }

            //制定人
            LambdaQueryWrapper<SalaryStandard> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.like(SalaryStandard::getBooker, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 1);

            List<SalaryStandard> list2 = removeByTime(salaryStandardService.list(queryWrapper2), dto);
            for (SalaryStandard standard2 : list2) {
                set.add(standard2);
            }

            //变更人
            LambdaQueryWrapper<SalaryStandard> queryWrapper3 = new LambdaQueryWrapper<>();
            queryWrapper3.like(SalaryStandard::getUpdatePeople, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 1);

            List<SalaryStandard> list3 = removeByTime(salaryStandardService.list(queryWrapper3), dto);
            for (SalaryStandard standard3 : list3) {
                set.add(standard3);
            }

            //复核人
            LambdaQueryWrapper<SalaryStandard> queryWrapper4 = new LambdaQueryWrapper<>();
            queryWrapper4.like(SalaryStandard::getReexaminePeople, dto.getKeyWord())
                    .eq(SalaryStandard::getReexamineState, 1);

            List<SalaryStandard> list4 = removeByTime(salaryStandardService.list(queryWrapper4), dto);
            for (SalaryStandard standard4 : list4) {
                set.add(standard4);
            }

        }

        List<SalaryStandard> salary = new ArrayList<>();
        for (SalaryStandard standard : set) {
            salary.add(standard);
        }

        salaryStandardsUpdate = salary;

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult salaryUpdateList() {
        List<SalaryVo> salaryVos = new ArrayList<>();

        //将信息复制到vo中
        for (SalaryStandard salaryStandard : salaryStandardsUpdate) {
            Double total = salaryStandard.getBasic() * (1 + 0.185) + salaryStandard.getLunch() + salaryStandard.getCommunication() + salaryStandard.getTraffic() + 3;
            SalaryVo salaryVo = BeanCopyUtils.copyBean(salaryStandard, SalaryVo.class);
            salaryVo.setTotal(total);
            salaryVos.add(salaryVo);
        }
        return ResponseResult.okResult(new PageVo(salaryVos, (long) salaryVos.size()));
    }
}




