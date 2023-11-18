package com.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookerTime {
    private String booker;
    private String bookTime;
    private Integer salaryId;
}
