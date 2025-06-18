package com.ruoyi.memberManagement.mapper;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberTag;

/**
 * 标签定义Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
public interface memberTagMapper 
{
    /**
     * 查询标签定义
     * 
     * @param tagId 标签定义主键
     * @return 标签定义
     */
    public memberTag selectmemberTagByTagId(Long tagId);

    /**
     * 查询标签定义列表
     * 
     * @param memberTag 标签定义
     * @return 标签定义集合
     */
    public List<memberTag> selectmemberTagList(memberTag memberTag);

    /**
     * 新增标签定义
     * 
     * @param memberTag 标签定义
     * @return 结果
     */
    public int insertmemberTag(memberTag memberTag);

    /**
     * 修改标签定义
     * 
     * @param memberTag 标签定义
     * @return 结果
     */
    public int updatememberTag(memberTag memberTag);

    /**
     * 删除标签定义
     * 
     * @param tagId 标签定义主键
     * @return 结果
     */
    public int deletememberTagByTagId(Long tagId);

    /**
     * 批量删除标签定义
     * 
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletememberTagByTagIds(Long[] tagIds);
}
