package com.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LevelDto {
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

    private Date openTime;

    private Date endTime;
}
