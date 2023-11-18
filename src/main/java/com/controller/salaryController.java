package com.controller;

import com.common.ResponseResult;
import com.domain.dto.salarySelectDto;
import com.domain.entity.SalaryStandard;
import com.service.SalaryStandardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/salary")
public class salaryController {

    @Resource
    private SalaryStandardService salaryStandardService;

    /**
     * 薪酬标准登记（给出薪酬标准编号和登记人，登记时间）
     */
    @GetMapping("/salaryRegistered")
    public ResponseResult getBookInfo() {
        return salaryStandardService.getBookInfo();
    }

    /**
     * 薪酬标准登记
     */
    @PostMapping("/salaryRegistered")
    public ResponseResult salaryRegistered(@RequestBody SalaryStandard salaryStandard) {
        return salaryStandardService.salaryRegistered(salaryStandard);
    }

    /**
     * 薪酬标准查询
     */
    @PostMapping("/salarySelect")
    public ResponseResult salarySelect(@RequestBody salarySelectDto dto) throws Exception {
        return salaryStandardService.salarySelect(dto);
    }


    /**
     * 展示待复核列表
     */
    @GetMapping("/salaryList")
    public ResponseResult salaryList() {
        return salaryStandardService.salaryList();
    }

    /**
     * 存储需要复核salaryId
     */
    private static Integer reexamineSalaryId;

    /**
     * 得到需要复核的salaryId
     */
    @PostMapping("/getReexamine/{salaryId}")
    public ResponseResult getSalaryId(@PathVariable("salaryId") Integer salaryId) {
        reexamineSalaryId = salaryId;
        return ResponseResult.okResult();
    }

    /**
     * 薪酬标准登记复核回显
     */
    @GetMapping("/salaryReexamine")
    public ResponseResult getReexamine() {
        return salaryStandardService.getReexamine(reexamineSalaryId);
    }

    /**
     * 薪酬标准登记复核
     */
    @PostMapping("/salaryReexamine")
    public ResponseResult salaryReexamine(@RequestBody SalaryStandard salaryStandard) {
        return salaryStandardService.salaryReexamine(salaryStandard);
    }

    /**
     * 存储需要修改的salaryId
     */
    private static Integer updateId;

    /**
     * 得到需要更改的salaryId
     */
    @PostMapping("/getUpdate/{id}")
    public ResponseResult getUpdateId(@PathVariable("id") Integer salaryId){
        updateId=salaryId;
        return ResponseResult.okResult();
    }

    /**
     * 薪酬标准更改回显
     */
    @GetMapping("/salaryUpdate")
    public ResponseResult getUpdate(){
        return salaryStandardService.getUpdate(updateId);
    }

    /**
     * 薪酬标准更改
     */
    @PostMapping("/salaryUpdate")
    public ResponseResult salaryUpdate(@RequestBody SalaryStandard salaryStandard){
        return salaryStandardService.salaryUpdate(salaryStandard);
    }

    /**
     * 薪酬标准查询(复核后的)
     */
    @PostMapping("/salaryUpdateSelect")
    public ResponseResult salaryUpdateSelect(@RequestBody salarySelectDto dto) throws Exception {
        return salaryStandardService.salaryUpdateSelect(dto);
    }


    /**
     * 展示列表
     */
    @GetMapping("/salaryUpdateList")
    public ResponseResult salaryUpdateList() {
        return salaryStandardService.salaryUpdateList();
    }
}
