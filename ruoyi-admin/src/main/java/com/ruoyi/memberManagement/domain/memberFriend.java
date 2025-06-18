package com.ruoyi.memberManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员好友关系对象 association_friend
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public class memberFriend extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 好友一sys_user.user_id */
    private Long userA;

    /** 好友二sys_user.user_id */
    private Long userB;

    public void setUserA(Long userA) 
    {
        this.userA = userA;
    }

    public Long getUserA() 
    {
        return userA;
    }

    public void setUserB(Long userB) 
    {
        this.userB = userB;
    }

    public Long getUserB() 
    {
        return userB;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userA", getUserA())
            .append("userB", getUserB())
            .append("createTime", getCreateTime())
            .toString();
    }
}
