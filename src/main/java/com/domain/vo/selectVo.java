package com.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class selectVo {

    /**
     *
     */
    private Integer employeeid;

    /**
     *档案编号
     */
    private String archivesId;

    /**
     * 员工姓名

     */
    private String employeeName;

    /**
     * 性别
     */
    private String sex;

    /**
     *
     */
    private String level1;

    /**
     *
     */
    private String level2;

    /**
     *
     */
    private String level3;


    /**
     * 职位名称
     */
    private String namePosition;

}
