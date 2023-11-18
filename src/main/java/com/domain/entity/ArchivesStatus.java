package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName archives_status
 */
@TableName(value ="archives_status")
@Data
public class ArchivesStatus implements Serializable {
    /**
     * 员工编号
     */
    @TableId(type = IdType.AUTO)
    private Integer employeeid;

    /**
     * 
     */
    private String openTime;

    /**
     * 
     */
    private String openPeople;

    /**
     * 
     */
    private String reviewTime;

    /**
     * 
     */
    private String reviewPeople;

    /**
     * 
     */
    private String updateTime;

    /**
     * 
     */
    private String updatePeople;

    /**
     * 逻辑删除
     */
    private Integer delFlag;

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
        ArchivesStatus other = (ArchivesStatus) that;
        return (this.getEmployeeid() == null ? other.getEmployeeid() == null : this.getEmployeeid().equals(other.getEmployeeid()))
            && (this.getOpenTime() == null ? other.getOpenTime() == null : this.getOpenTime().equals(other.getOpenTime()))
            && (this.getOpenPeople() == null ? other.getOpenPeople() == null : this.getOpenPeople().equals(other.getOpenPeople()))
            && (this.getReviewTime() == null ? other.getReviewTime() == null : this.getReviewTime().equals(other.getReviewTime()))
            && (this.getReviewPeople() == null ? other.getReviewPeople() == null : this.getReviewPeople().equals(other.getReviewPeople()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdatePeople() == null ? other.getUpdatePeople() == null : this.getUpdatePeople().equals(other.getUpdatePeople()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEmployeeid() == null) ? 0 : getEmployeeid().hashCode());
        result = prime * result + ((getOpenTime() == null) ? 0 : getOpenTime().hashCode());
        result = prime * result + ((getOpenPeople() == null) ? 0 : getOpenPeople().hashCode());
        result = prime * result + ((getReviewTime() == null) ? 0 : getReviewTime().hashCode());
        result = prime * result + ((getReviewPeople() == null) ? 0 : getReviewPeople().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdatePeople() == null) ? 0 : getUpdatePeople().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", employeeid=").append(employeeid);
        sb.append(", openTime=").append(openTime);
        sb.append(", openPeople=").append(openPeople);
        sb.append(", reviewTime=").append(reviewTime);
        sb.append(", reviewPeople=").append(reviewPeople);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updatePeople=").append(updatePeople);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}