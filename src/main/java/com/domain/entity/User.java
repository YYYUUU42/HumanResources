package com.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工登录信息
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 员工编号
     */
    @TableId
    private String employeeid;

    /**
     * 员工名字
     */
    private String ename;


    /**
     * 登录的密码
     */
    private String pwd;

    /**
     * 职务
     */
    private String position;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}