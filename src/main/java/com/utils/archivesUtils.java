package com.utils;

import com.domain.dto.archivesDto;

import java.util.Calendar;

public class archivesUtils {

    public static String getArchivesId(archivesDto dto) {

        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String level1 = "";
        String level2 = "";
        String level3 = "";

        //获取一级机构编号
        switch (dto.getLevel1()) {
            case "集团":
                level1 = "01";
                break;
        }

        //获取二级机构编号
        switch (dto.getLevel2()) {
            case "软件公司":
                level2 = "01";
                break;
            case "外包公司":
                level2 = "02";
                break;
        }


        //获取三级机构编号
        switch (dto.getLevel3()){
            case "外包组":
                level3 = "01";
                break;
            case "开发组":
                level3 = "02";
                break;
            case "运维组":
                level3 = "03";
                break;
            case "测试组":
                level3 = "04";
                break;
        }

        //得到员工编号
        String employeeId = String.valueOf(dto.getEmployeeid());

        return year+level1+level2+level3+employeeId;
    }
}
