package com.ruoyi.memberManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员好友申请对象 association_friend_request
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public class memberFriendRequest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long requestId;

    /** 发起者sys_user.user_id */
    @Excel(name = "发起者sys_user.user_id")
    private Long fromUser;

    /** 接收者sys_user.user_id */
    @Excel(name = "接收者sys_user.user_id")
    private Long toUser;

    /** 申请状态 */
    @Excel(name = "申请状态")
    private String status;

    /** 申请理由 */
    @Excel(name = "申请理由")
    private String reason;


    public void setRequestId(Long requestId) 
    {
        this.requestId = requestId;
    }

    public Long getRequestId() 
    {
        return requestId;
    }

    public void setFromUser(Long fromUser) 
    {
        this.fromUser = fromUser;
    }

    public Long getFromUser() 
    {
        return fromUser;
    }

    public void setToUser(Long toUser) 
    {
        this.toUser = toUser;
    }

    public Long getToUser() 
    {
        return toUser;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("requestId", getRequestId())
            .append("fromUser", getFromUser())
            .append("toUser", getToUser())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("reason", getReason())
                .toString();
    }
}
