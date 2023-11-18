package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工的基本信息
 * @TableName basic_information
 */
@TableName(value ="basic_information")
@Data
public class BasicInformation implements Serializable {
    /**
     * 员工编号
     */
    @TableId(type = IdType.AUTO)
    private Integer employeeid;

    /**
     * 员工姓名

     */
    private String employeeName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 民族
     */
    private String people;

    /**
     * 生日
     */
    private String birth;

    /**
     * 出身地
     */
    private String origin;

    /**
     * 身份证号码
     */
    private String identity;

    /**
     * 社会保障号码
     */
    private String socialId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 学历
     */
    private String education;

    /**
     * 教育年限
     */
    private Integer educationYear;

    /**
     * 宗教信仰
     */
    private String faith;

    /**
     * 政治面貌
     */
    private String status;

    /**
     * 专业
     */
    private String professional;

    /**
     * 头像
     */
    private String picture;

    /**
     * 档案编号
     */
    private String archivesId;



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
        BasicInformation other = (BasicInformation) that;
        return (this.getEmployeeid() == null ? other.getEmployeeid() == null : this.getEmployeeid().equals(other.getEmployeeid()))
            && (this.getEmployeeName() == null ? other.getEmployeeName() == null : this.getEmployeeName().equals(other.getEmployeeName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getNationality() == null ? other.getNationality() == null : this.getNationality().equals(other.getNationality()))
            && (this.getPeople() == null ? other.getPeople() == null : this.getPeople().equals(other.getPeople()))
            && (this.getBirth() == null ? other.getBirth() == null : this.getBirth().equals(other.getBirth()))
            && (this.getIdentity() == null ? other.getIdentity() == null : this.getIdentity().equals(other.getIdentity()))
            && (this.getSocialId() == null ? other.getSocialId() == null : this.getSocialId().equals(other.getSocialId()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getEducation() == null ? other.getEducation() == null : this.getEducation().equals(other.getEducation()))
            && (this.getProfessional() == null ? other.getProfessional() == null : this.getProfessional().equals(other.getProfessional()))
            && (this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEmployeeid() == null) ? 0 : getEmployeeid().hashCode());
        result = prime * result + ((getEmployeeName() == null) ? 0 : getEmployeeName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getNationality() == null) ? 0 : getNationality().hashCode());
        result = prime * result + ((getPeople() == null) ? 0 : getPeople().hashCode());
        result = prime * result + ((getBirth() == null) ? 0 : getBirth().hashCode());
        result = prime * result + ((getIdentity() == null) ? 0 : getIdentity().hashCode());
        result = prime * result + ((getSocialId() == null) ? 0 : getSocialId().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getEducation() == null) ? 0 : getEducation().hashCode());
        result = prime * result + ((getProfessional() == null) ? 0 : getProfessional().hashCode());
        result = prime * result + ((getPicture() == null) ? 0 : getPicture().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", employeeid=").append(employeeid);
        sb.append(", employeeName=").append(employeeName);
        sb.append(", sex=").append(sex);
        sb.append(", nationality=").append(nationality);
        sb.append(", people=").append(people);
        sb.append(", birth=").append(birth);
        sb.append(", identity=").append(identity);
        sb.append(", socialId=").append(socialId);
        sb.append(", age=").append(age);
        sb.append(", education=").append(education);
        sb.append(", professional=").append(professional);
        sb.append(", picture=").append(picture);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}