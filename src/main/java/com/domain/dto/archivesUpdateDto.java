package com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class archivesUpdateDto {
    /**
     * 员工编号
     */
    private Integer employeeid;

    private String level1;


    private String level2;

    private String level3;

    /**
     * 职位分类
     */
    private String category;

    /**
     * 职位名称
     */
    private String namePosition;

    /**
     * 职称
     */
    private String title;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 民族
     */
    private String people;

    /**
     * 生日
     */
    private String birth;

    /**
     * 出生地
     */
    private String origin;

    /**
     * 身份证号码
     */
    private String identity;

    /**
     * 社会保障号码
     */
    private String socialId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 学历
     */
    private String education;

    /**
     * 教育年限
     */
    private Integer educationYear;

    /**
     * 宗教信仰
     */
    private String faith;

    /**
     * 政治面貌
     */
    private String status;

    /**
     * 专业
     */
    private String professional;

    /**
     * 头像
     */
    private String picture;



    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * QQ
     */
    private String qq;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postcode;


    /**
     * 开户行
     */
    private String bankid;

    /**
     * 账号
     */
    private String eaccount;

    /**
     * 变更时间
     */
    private String updateTime;

    /**
     * 特长
     */
    private String specialty;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 个人履历
     */
    private String experience;

    /**
     * 家庭关系
     */
    private String family;

    /**
     * 备注
     */
    private String ecomment;

    /**
     * 变更人
     */
    private String updatePeople;

    /**
     * 工资
     */
    private String salary;

    private String archivesId;



}
