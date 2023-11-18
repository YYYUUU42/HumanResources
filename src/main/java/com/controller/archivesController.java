package com.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.ResponseResult;
import com.domain.dto.LevelDto;
import com.domain.dto.archivesDto;
import com.domain.dto.archivesReexamineDto;
import com.domain.dto.archivesUpdateDto;
import com.domain.entity.*;
import com.domain.vo.PageVo;
import com.domain.vo.ReexamineVo;
import com.domain.vo.UpdateVo;
import com.domain.vo.selectVo;
import com.service.*;
import com.service.impl.BasicInformationServiceImpl;
import com.utils.BeanCopyUtils;
import com.utils.archivesUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.LogManager;

@RestController
@RequestMapping("/archives")
public class archivesController {

    @Resource
    private BasicInformationServiceImpl basicInformationService;

    @Resource
    private DetailInformationService detailInformationService;

    @Resource
    private ContactInformationService contactInformationService;

    @Resource
    private LevelPositionService levelPositionService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ArchivesStatusService archivesStatusService;

    @Resource
    private SalaryStandardService salaryStandardService;

    /**
     * 将前端传回来的数据 添加进数据库中（档案登记）
     *
     * @param dto
     * @return
     */
    @PostMapping("/registered")
    public ResponseResult registered(@RequestBody archivesDto dto) {
        BasicInformation basicInformation = BeanCopyUtils.copyBean(dto, BasicInformation.class);
        basicInformationService.save(basicInformation);

        ContactInformation contactInformation = BeanCopyUtils.copyBean(dto, ContactInformation.class);
        contactInformationService.save(contactInformation);

        DetailInformation detailInformation = BeanCopyUtils.copyBean(dto, DetailInformation.class);
        detailInformationService.save(detailInformation);

        LevelPosition levelPosition = BeanCopyUtils.copyBean(dto, LevelPosition.class);
        levelPositionService.save(levelPosition);

        ArchivesStatus archivesStatus = BeanCopyUtils.copyBean(dto, ArchivesStatus.class);
        archivesStatus.setOpenTime(dto.getOpenTime());
        archivesStatus.setOpenPeople(dto.getOpenPeople());
        archivesStatusService.save(archivesStatus);

        //将档案编号加进去
        dto.setEmployeeid(basicInformation.getEmployeeid());
        String archivesId = archivesUtils.getArchivesId(dto);
        basicInformation.setArchivesId(archivesId);
        basicInformationService.updateById(basicInformation);

        return ResponseResult.okResult();
    }

    /**
     * 登录后，在档案部分，输出档案时间，和复核人
     *
     * @return
     */
    @GetMapping("/registered")
    public ResponseResult returnTime() {

        String o = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(o, User.class);
        String s = login.getEname();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ' ' HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());
        TimeName timeName = new TimeName(formatter.format(date), s);
        return ResponseResult.okResult(timeName);
    }

    /**
     * 得到薪酬标准
     */
    @GetMapping("/getSalary")
    public ResponseResult getSalary(){
        LambdaQueryWrapper<SalaryStandard> queryWrapper = new LambdaQueryWrapper<>();
        //是复核后的状态
        queryWrapper.eq(SalaryStandard::getReexamineState,1);

        List<SalaryStandard> list = salaryStandardService.list(queryWrapper);

        return ResponseResult.okResult(list);
    }

    /**
     * 人力资源档案复核列表
     *
     * @return
     */
    @GetMapping("/selectReexamine")
    public ResponseResult selectReexamine() {
        //将状态为复核状态的搜索出来
        LambdaQueryWrapper<LevelPosition> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LevelPosition::getReexamine, "0");

        List<LevelPosition> level = levelPositionService.list(queryWrapper);
        List<selectVo> vos = new ArrayList<>();

        //返回复核列表中的vo
        for (LevelPosition position : level) {
            Integer employeeid = position.getEmployeeid();
            BasicInformation basicInformation = basicInformationService.getById(employeeid);

            String employeeName = basicInformation.getEmployeeName();
            String sex = basicInformation.getSex();

            selectVo vo = BeanCopyUtils.copyBean(position, selectVo.class);
            vo.setArchivesId(basicInformation.getArchivesId());
            vo.setEmployeeName(employeeName);
            vo.setSex(sex);
            vos.add(vo);
        }

        PageVo pageVo = new PageVo(vos, (long) vos.size());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 用来存储搜索到的列表
     */
    private List<selectVo> selectList;

    /**
     * 人力资源档案查询
     *
     * @param dto
     * @return
     * @throws ParseException
     */
    @PostMapping("/selectLevel")
    public ResponseResult selectLevel(@RequestBody LevelDto dto) throws ParseException {
        LambdaQueryWrapper<LevelPosition> queryWrapper = new LambdaQueryWrapper<>();

        //里面有三个参数，第一个参数表示，如果条件为true就将这个条件附加上去，即某个查询条件没有输入该查询条件将对结果不起限定作用
        queryWrapper.eq(StringUtils.hasText(dto.getLevel1()), LevelPosition::getLevel1, dto.getLevel1())
                .eq(StringUtils.hasText(dto.getLevel2()), LevelPosition::getLevel2, dto.getLevel2())
                .eq(StringUtils.hasText(dto.getLevel3()), LevelPosition::getLevel3, dto.getLevel3())
                .eq(StringUtils.hasText(dto.getCategory()), LevelPosition::getCategory, dto.getCategory())
                .eq(StringUtils.hasText(dto.getNamePosition()), LevelPosition::getNamePosition, dto.getNamePosition())
                // 而且是复核通过的状态
                .eq(LevelPosition::getReexamine,"1");

        List<LevelPosition> level = levelPositionService.list(queryWrapper);

        Iterator<LevelPosition> iterator = level.iterator();


        //将时间不是限定范围的删掉
        while (iterator.hasNext()) {
            LevelPosition next = iterator.next();
            Integer employeeid = next.getEmployeeid();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String time = archivesStatusService.getById(employeeid).getOpenTime();
            Date openTime = formatter.parse(time);

            String start=formatter.format(dto.getOpenTime());
            String end= formatter.format(dto.getEndTime());

            //如果开始时间和结束时间都存在
            if (StringUtils.hasText(start)&&StringUtils.hasText(end)) {
                if (!(openTime.before(dto.getEndTime()) && openTime.after(dto.getOpenTime()))) {
                    iterator.remove();
                }
            }

            //只有开始时间
            if (StringUtils.hasText(start)&&(!StringUtils.hasText(end))) {
                if (!(openTime.after(dto.getOpenTime()))) {
                    iterator.remove();
                }
            }

            //只有结束时间
            if ((!StringUtils.hasText(start))&&StringUtils.hasText(end)) {
                if (!(openTime.before(dto.getEndTime()) )) {
                    iterator.remove();
                }
            }
        }

        selectList = BeanCopyUtils.copyBeanList(level, selectVo.class);

        for (selectVo selectVo : selectList) {
            Integer employeeid = selectVo.getEmployeeid();
            BasicInformation basicInformation = basicInformationService.getById(employeeid);
            selectVo.setArchivesId(basicInformation.getArchivesId());
            selectVo.setSex(basicInformation.getSex());
            selectVo.setEmployeeName(basicInformation.getEmployeeName());
        }
        return ResponseResult.okResult();
    }

    /**
     * 返回查询到的结果
     */
    @GetMapping("/selectList")
    public ResponseResult select() {
        return ResponseResult.okResult(new PageVo(selectList, (long) selectList.size()));
    }


    public static String ReexamineId;

    /**
     * 得到人力资源档案变更的employeeid
     *
     * @param employeeid
     * @return
     */
    @PostMapping("/getReexamine/{id}")
    public ResponseResult getReexamineId(@PathVariable("id") String employeeid) {
        ReexamineId = employeeid;
        return ResponseResult.okResult();
    }

    /**
     * 复核档案信息回显
     *
     * @return
     */
    @GetMapping("/getReexamine")
    public ResponseResult getReexamine() {
        ReexamineVo vo = new ReexamineVo();

        LambdaQueryWrapper<BasicInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInformation::getArchivesId, ReexamineId);
        Integer employeeid = basicInformationService.getOne(queryWrapper).getEmployeeid();

        //根据传过来的id来得到档案信息
        LevelPosition levelPosition = levelPositionService.getById(employeeid);
        BasicInformation basicInformation = basicInformationService.getById(employeeid);
        ContactInformation contactInformation = contactInformationService.getById(employeeid);
        DetailInformation detailInformation = detailInformationService.getById(employeeid);

        //得到当前登录人的名字，以及当前时间
        String s = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(s, User.class);
        String reviewPeople = login.getEname();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ' ' HH:mm:ss ");
        Date reviewTime = new Date(System.currentTimeMillis());

        vo.setReviewPeople(reviewPeople);
        vo.setReviewTime(formatter.format(reviewTime));

        //将档案信息弄到一个vo中来
        String[] ignoreProperties = {"employeeid"};
        vo.setArchivesId(basicInformation.getArchivesId());
        BeanUtils.copyProperties(levelPosition, vo, ignoreProperties);
        BeanUtils.copyProperties(basicInformation, vo, ignoreProperties);
        BeanUtils.copyProperties(contactInformation, vo, ignoreProperties);
        BeanUtils.copyProperties(detailInformation, vo, ignoreProperties);

        return ResponseResult.okResult(vo);
    }


    /**
     * 得到要修改的用户id
     */
    @PostMapping("/getUpdateId/{id}")
    public ResponseResult getUpdateId(@PathVariable("id") String id) {
        UpdateId = id;
        return ResponseResult.okResult();
    }

    /**
     * 用于修改时的id
     */
    private static String UpdateId;

    /**
     * 修改档案信息回显
     */
    @GetMapping("/getUpdate")
    public ResponseResult getUpdate() {
        UpdateVo vo = new UpdateVo();

        //利用得到的档案id来得到employeeId
        LambdaQueryWrapper<BasicInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInformation::getArchivesId, UpdateId);
        Integer employeeid = basicInformationService.getOne(queryWrapper).getEmployeeid();

        //根据传过来的id来得到档案信息
        LevelPosition levelPosition = levelPositionService.getById(employeeid);
        BasicInformation basicInformation = basicInformationService.getById(employeeid);
        ContactInformation contactInformation = contactInformationService.getById(employeeid);
        DetailInformation detailInformation = detailInformationService.getById(employeeid);

        //得到当前登录人的名字，以及当前时间
        String s = stringRedisTemplate.opsForValue().get("login");
        User login = JSON.parseObject(s, User.class);
        String People = login.getEname();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ' ' HH:mm:ss ");
        Date Time = new Date(System.currentTimeMillis());
        vo.setUpdatePeople(People);
        vo.setUpdateTime(formatter.format(Time));

        //将档案信息弄到一个vo中来
        String[] ignoreProperties = {"employeeid"};
        vo.setArchivesId(basicInformation.getArchivesId());
        BeanUtils.copyProperties(levelPosition, vo, ignoreProperties);
        BeanUtils.copyProperties(basicInformation, vo, ignoreProperties);
        BeanUtils.copyProperties(contactInformation, vo, ignoreProperties);
        BeanUtils.copyProperties(detailInformation, vo, ignoreProperties);
        return ResponseResult.okResult(vo);
    }


    /**
     * 得到需要查询的id
     */
    private static String detailId;

    /**
     * 得到需要查询的id
     */
    @PostMapping("/getDetailId/{id}")
    public ResponseResult getDetailId(@PathVariable("id") String id) {
        detailId = id;
        return ResponseResult.okResult();
    }

    /**
     * 查询后查看明细
     */
    @GetMapping("/getDetail")
    public ResponseResult getDetail() {
        LambdaQueryWrapper<BasicInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInformation::getArchivesId, detailId);
        Integer employeeid = basicInformationService.getOne(queryWrapper).getEmployeeid();
        UpdateVo vo = new UpdateVo();

        //根据传过来的id来得到档案信息
        LevelPosition levelPosition = levelPositionService.getById(employeeid);
        BasicInformation basicInformation = basicInformationService.getById(employeeid);
        ContactInformation contactInformation = contactInformationService.getById(employeeid);
        DetailInformation detailInformation = detailInformationService.getById(employeeid);

        //将档案信息弄到一个vo中来
        String[] ignoreProperties = {"employeeid"};
        vo.setArchivesId(basicInformation.getArchivesId());
        BeanUtils.copyProperties(levelPosition, vo, ignoreProperties);
        BeanUtils.copyProperties(basicInformation, vo, ignoreProperties);
        BeanUtils.copyProperties(contactInformation, vo, ignoreProperties);
        BeanUtils.copyProperties(detailInformation, vo, ignoreProperties);
        return ResponseResult.okResult(vo);
    }

    /**
     * 复核通过
     *
     * @param dto
     * @return
     */
    @PostMapping("/archivesReexamine")
    public ResponseResult archivesReexamine(@RequestBody archivesReexamineDto dto) {
        LambdaQueryWrapper<BasicInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInformation::getArchivesId,dto.getArchivesId());
        dto.setEmployeeid( basicInformationService.getOne(queryWrapper).getEmployeeid());


        BasicInformation basicInformation = BeanCopyUtils.copyBean(dto, BasicInformation.class);
        basicInformationService.updateById(basicInformation);

        ContactInformation contactInformation = BeanCopyUtils.copyBean(dto, ContactInformation.class);
        contactInformationService.updateById(contactInformation);

        DetailInformation detailInformation = BeanCopyUtils.copyBean(dto, DetailInformation.class);
        detailInformationService.updateById(detailInformation);

        LevelPosition levelPosition = BeanCopyUtils.copyBean(dto, LevelPosition.class);
        // 1 表示已经完成复核的
        levelPosition.setReexamine("1");
        levelPositionService.updateById(levelPosition);

        ArchivesStatus archivesStatus = BeanCopyUtils.copyBean(dto, ArchivesStatus.class);
        archivesStatus.setReviewPeople(dto.getReviewPeople());
        archivesStatus.setReviewTime(dto.getReviewTime());
        archivesStatusService.updateById(archivesStatus);

        return ResponseResult.okResult();
    }

    /**
     * 修改档案
     *
     * @param dto
     * @return
     */
    @PostMapping("/archivesUpdate")
    public ResponseResult archivesUpdate(@RequestBody archivesUpdateDto dto) {
        LambdaQueryWrapper<BasicInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInformation::getArchivesId,dto.getArchivesId());
        dto.setEmployeeid( basicInformationService.getOne(queryWrapper).getEmployeeid());

        BasicInformation basicInformation = BeanCopyUtils.copyBean(dto, BasicInformation.class);
        basicInformationService.updateById(basicInformation);

        ContactInformation contactInformation = BeanCopyUtils.copyBean(dto, ContactInformation.class);
        contactInformationService.updateById(contactInformation);

        DetailInformation detailInformation = BeanCopyUtils.copyBean(dto, DetailInformation.class);
        detailInformationService.updateById(detailInformation);

        LevelPosition levelPosition = BeanCopyUtils.copyBean(dto, LevelPosition.class);
        //修改后需要重新复核
        levelPosition.setReexamine("0");
        levelPositionService.updateById(levelPosition);

        ArchivesStatus archivesStatus = BeanCopyUtils.copyBean(dto, ArchivesStatus.class);
        archivesStatus.setUpdateTime(dto.getUpdateTime());
        archivesStatus.setUpdatePeople(dto.getUpdatePeople());
        archivesStatusService.updateById(archivesStatus);

        return ResponseResult.okResult();
    }

    /**
     * 删除档案
     */
    @PostMapping("/archivesDelete/{id}")
    public ResponseResult archivesDelete(@PathVariable("id") String id) {
        //根据档案编号得到员工编号
        LambdaQueryWrapper<BasicInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInformation::getArchivesId,id);
        Integer employeeid = basicInformationService.getOne(queryWrapper).getEmployeeid();

        //使用逻辑删除，就是将status表中的del_flag 改为1
        ArchivesStatus archivesStatus = archivesStatusService.getById(employeeid);
        archivesStatus.setDelFlag(1);
        archivesStatusService.updateById(archivesStatus);
        return ResponseResult.okResult();
    }
}
