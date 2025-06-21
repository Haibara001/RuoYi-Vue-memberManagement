package com.ruoyi.memberManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员日志/帖子对象 association_post
 * 
 * @author ruoyi
 * @date 2025-06-21
 */
public class memberPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long postId;

    /** 作者sys_user.user_id */
    private Long authorId;

    /** 日志标题 */
    @Excel(name = "日志标题")
    private String title;

    /** 日志内容 */
    @Excel(name = "日志内容")
    private String content;

    /** 可见范围 */
    @Excel(name = "可见范围")
    private String visibility;

    /** 删除标志(0=正常,1=删除) */
    private String delFlag;

    public void setPostId(Long postId) 
    {
        this.postId = postId;
    }

    public Long getPostId() 
    {
        return postId;
    }

    public void setAuthorId(Long authorId) 
    {
        this.authorId = authorId;
    }

    public Long getAuthorId() 
    {
        return authorId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setVisibility(String visibility) 
    {
        this.visibility = visibility;
    }

    public String getVisibility() 
    {
        return visibility;
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
            .append("postId", getPostId())
            .append("authorId", getAuthorId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("visibility", getVisibility())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
