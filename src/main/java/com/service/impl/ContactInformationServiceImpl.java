package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.entity.ContactInformation;
import com.mapper.ContactInformationMapper;
import com.service.ContactInformationService;
import org.springframework.stereotype.Service;

/**
* @author Yu
* @description 针对表【contact_information(员工的联系方式)】的数据库操作Service实现
* @createDate 2022-11-28 14:27:33
*/
@Service
public class ContactInformationServiceImpl extends ServiceImpl<ContactInformationMapper, ContactInformation> implements ContactInformationService {

}




