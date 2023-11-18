package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName level_position
 */
@TableName(value ="level_position")
@Data
public class LevelPosition implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer employeeid;

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

    /**
     * 判断是否复核状态 0 是， 1 否
     */
    private String reexamine;


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
        LevelPosition other = (LevelPosition) that;
        return (this.getEmployeeid() == null ? other.getEmployeeid() == null : this.getEmployeeid().equals(other.getEmployeeid()))
            && (this.getLevel1() == null ? other.getLevel1() == null : this.getLevel1().equals(other.getLevel1()))
            && (this.getLevel2() == null ? other.getLevel2() == null : this.getLevel2().equals(other.getLevel2()))
            && (this.getLevel3() == null ? other.getLevel3() == null : this.getLevel3().equals(other.getLevel3()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getNamePosition() == null ? other.getNamePosition() == null : this.getNamePosition().equals(other.getNamePosition()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEmployeeid() == null) ? 0 : getEmployeeid().hashCode());
        result = prime * result + ((getLevel1() == null) ? 0 : getLevel1().hashCode());
        result = prime * result + ((getLevel2() == null) ? 0 : getLevel2().hashCode());
        result = prime * result + ((getLevel3() == null) ? 0 : getLevel3().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getNamePosition() == null) ? 0 : getNamePosition().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", employeeid=").append(employeeid);
        sb.append(", level1=").append(level1);
        sb.append(", level2=").append(level2);
        sb.append(", level3=").append(level3);
        sb.append(", category=").append(category);
        sb.append(", namePosition=").append(namePosition);
        sb.append(", title=").append(title);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}