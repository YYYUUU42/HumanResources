package com.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 薪酬标准表
 * @TableName salary_standard
 */
@TableName(value ="salary_standard")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryVo implements Serializable {
    /**
     * 薪酬标准编号
     */
    private Integer salaryId;

    /**
     * 薪酬标准名称
     */
    private String salaryName;

    /**
     * 制定人
     */
    private String maker;

    /**
     * 登记人
     */
    private String booker;

    /**
     * 登记时间
     */
    private String bookTime;

    /**
     * 总额
     */
    private Double total;


}