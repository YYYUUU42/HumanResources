package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.ResponseResult;
import com.domain.dto.salarySelectDto;
import com.domain.entity.SalaryStandard;

/**
* @author Yu
* @description 针对表【salary_standard(薪酬标准表)】的数据库操作Service
* @createDate 2022-12-01 19:44:14
*/
public interface SalaryStandardService extends IService<SalaryStandard> {

    ResponseResult getBookInfo();

    ResponseResult salarySelect(salarySelectDto dto) throws Exception;

    ResponseResult salaryRegistered(SalaryStandard salaryStandard);

    ResponseResult salaryList();
    ResponseResult getReexamine(Integer salaryId);
    ResponseResult salaryReexamine(SalaryStandard salaryStandard);


    ResponseResult getUpdate(Integer updateId);

    ResponseResult salaryUpdate(SalaryStandard salaryStandard);

    ResponseResult salaryUpdateSelect(salarySelectDto dto) throws Exception;

    ResponseResult salaryUpdateList();

}
