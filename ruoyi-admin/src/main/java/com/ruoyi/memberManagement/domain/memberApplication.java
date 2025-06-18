package com.ruoyi.memberManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员入会申请对象 association_application
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public class memberApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long appId;

    /** 申请人sys_user.user_id（游客NULL） */
    @Excel(name = "申请人sys_user.user_id", readConverterExp = "游=客NULL")
    private Long userId;

    /** 申请人姓名 */
    @Excel(name = "申请人姓名")
    private String name;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 申请理由 */
    @Excel(name = "申请理由")
    private String reason;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String status;

    /** 审核人sys_user.user_id */
    @Excel(name = "审核人sys_user.user_id")
    private Long reviewerId;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    public void setAppId(Long appId) 
    {
        this.appId = appId;
    }

    public Long getAppId() 
    {
        return appId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setReviewerId(Long reviewerId) 
    {
        this.reviewerId = reviewerId;
    }

    public Long getReviewerId() 
    {
        return reviewerId;
    }

    public void setReviewTime(Date reviewTime) 
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() 
    {
        return reviewTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("appId", getAppId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("contact", getContact())
            .append("reason", getReason())
            .append("status", getStatus())
            .append("reviewerId", getReviewerId())
            .append("reviewTime", getReviewTime())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
