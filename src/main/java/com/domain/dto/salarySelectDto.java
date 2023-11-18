package com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class salarySelectDto {
    private Integer salaryId;

    private String keyWord;

    private Date openTime;

    private Date endTime;
}
