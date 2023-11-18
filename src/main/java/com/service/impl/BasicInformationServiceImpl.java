package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.entity.BasicInformation;
import com.mapper.BasicInformationMapper;
import com.service.BasicInformationService;
import org.springframework.stereotype.Service;

/**
* @author Yu
* @description 针对表【basic_information(员工的基本信息)】的数据库操作Service实现
* @createDate 2022-11-28 14:27:23
*/
@Service
public class BasicInformationServiceImpl extends ServiceImpl<BasicInformationMapper, BasicInformation> implements BasicInformationService {

}




