package com.ruoyi.memberManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员-标签关联对象 association_user_tag
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
public class memberUserTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** sys_user.user_id */
    @Excel(name = "sys_user.user_id")
    private Long userId;

    /** association_tag.tag_id */
    @Excel(name = "association_tag.tag_id")
    private Long tagId;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("tagId", getTagId())
            .toString();
    }
}
