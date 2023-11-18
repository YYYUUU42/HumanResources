package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.dto.salarySelectDto;
import com.domain.entity.SalaryStandard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Yu
* @description 针对表【salary_standard(薪酬标准表)】的数据库操作Mapper
* @createDate 2022-12-01 19:44:14
* @Entity generator.domain.SalaryStandard
*/
@Mapper
public interface SalaryStandardMapper extends BaseMapper<SalaryStandard> {
    List<SalaryStandard> salaryIdLike(Integer salaryId);
}




