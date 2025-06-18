package com.ruoyi.memberManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员扩展资料对象 association_profile
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
public class memberProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 对应sys_user.user_id */
    private Long userId;

    /** 学号 */
    @Excel(name = "学号")
    private String studentId;

    /** 入会时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入会时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinTime;

    /** 协会职务 */
    @Excel(name = "协会职务")
    private String position;

    /** 个人介绍 */
    @Excel(name = "个人介绍")
    private String introduction;

    /** 删除标志(0=正常,1=删除) */
    private String delFlag;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }

    public void setJoinTime(Date joinTime) 
    {
        this.joinTime = joinTime;
    }

    public Date getJoinTime() 
    {
        return joinTime;
    }

    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }

    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("studentId", getStudentId())
            .append("joinTime", getJoinTime())
            .append("position", getPosition())
            .append("introduction", getIntroduction())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
