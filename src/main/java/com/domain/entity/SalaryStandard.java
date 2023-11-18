package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 薪酬标准表
 * @TableName salary_standard
 */
@TableName(value ="salary_standard")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStandard implements Serializable {
    /**
     * 薪酬标准编号
     */
    @TableId(type = IdType.AUTO)
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
     * 基本工资
     */
    private Integer basic;

    /**
     * 交通补助
     */
    private Integer traffic;

    /**
     * 午餐补助
     */
    private Integer lunch;

    /**
     * 通讯补助
     */
    private Integer communication;

    /**
     * 养老保险
     */
    private Double endowmentInsurance;

    /**
     * 失业保险
     */
    private Double unemployment;

    /**
     * 医疗保险
     */
    private Double medical;

    /**
     * 住房公积金
     */
    private Double housingFund;

    /**
     * 复核意见
     */
    private String reexamine;

    /**
     * 复核状态 ，0表示待复核
     */
    private Integer reexamineState;

    /**
     * 总额（在数据库中不存在）
     */
    @TableField(exist = false)
    private Double total;

    /**
     * 变更人
     */
    private String updatePeople;

    /**
     * 复核人
     */
    private String reexaminePeople;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SalaryStandard other = (SalaryStandard) that;
        return (this.getSalaryId() == null ? other.getSalaryId() == null : this.getSalaryId().equals(other.getSalaryId()))
            && (this.getSalaryName() == null ? other.getSalaryName() == null : this.getSalaryName().equals(other.getSalaryName()))
            && (this.getMaker() == null ? other.getMaker() == null : this.getMaker().equals(other.getMaker()))
            && (this.getBooker() == null ? other.getBooker() == null : this.getBooker().equals(other.getBooker()))
            && (this.getBookTime() == null ? other.getBookTime() == null : this.getBookTime().equals(other.getBookTime()))
            && (this.getBasic() == null ? other.getBasic() == null : this.getBasic().equals(other.getBasic()))
            && (this.getTraffic() == null ? other.getTraffic() == null : this.getTraffic().equals(other.getTraffic()))
            && (this.getLunch() == null ? other.getLunch() == null : this.getLunch().equals(other.getLunch()))
            && (this.getCommunication() == null ? other.getCommunication() == null : this.getCommunication().equals(other.getCommunication()))
            && (this.getEndowmentInsurance() == null ? other.getEndowmentInsurance() == null : this.getEndowmentInsurance().equals(other.getEndowmentInsurance()))
            && (this.getUnemployment() == null ? other.getUnemployment() == null : this.getUnemployment().equals(other.getUnemployment()))
            && (this.getMedical() == null ? other.getMedical() == null : this.getMedical().equals(other.getMedical()))
            && (this.getHousingFund() == null ? other.getHousingFund() == null : this.getHousingFund().equals(other.getHousingFund()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSalaryId() == null) ? 0 : getSalaryId().hashCode());
        result = prime * result + ((getSalaryName() == null) ? 0 : getSalaryName().hashCode());
        result = prime * result + ((getMaker() == null) ? 0 : getMaker().hashCode());
        result = prime * result + ((getBooker() == null) ? 0 : getBooker().hashCode());
        result = prime * result + ((getBookTime() == null) ? 0 : getBookTime().hashCode());
        result = prime * result + ((getBasic() == null) ? 0 : getBasic().hashCode());
        result = prime * result + ((getTraffic() == null) ? 0 : getTraffic().hashCode());
        result = prime * result + ((getLunch() == null) ? 0 : getLunch().hashCode());
        result = prime * result + ((getCommunication() == null) ? 0 : getCommunication().hashCode());
        result = prime * result + ((getEndowmentInsurance() == null) ? 0 : getEndowmentInsurance().hashCode());
        result = prime * result + ((getUnemployment() == null) ? 0 : getUnemployment().hashCode());
        result = prime * result + ((getMedical() == null) ? 0 : getMedical().hashCode());
        result = prime * result + ((getHousingFund() == null) ? 0 : getHousingFund().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", salaryId=").append(salaryId);
        sb.append(", salaryName=").append(salaryName);
        sb.append(", maker=").append(maker);
        sb.append(", booker=").append(booker);
        sb.append(", bookTime=").append(bookTime);
        sb.append(", basic=").append(basic);
        sb.append(", traffic=").append(traffic);
        sb.append(", lunch=").append(lunch);
        sb.append(", communication=").append(communication);
        sb.append(", endowmentInsurance=").append(endowmentInsurance);
        sb.append(", unemployment=").append(unemployment);
        sb.append(", medical=").append(medical);
        sb.append(", housingFund=").append(housingFund);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}