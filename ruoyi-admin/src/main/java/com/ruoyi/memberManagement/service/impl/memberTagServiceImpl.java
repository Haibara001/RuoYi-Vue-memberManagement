package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberTagMapper;
import com.ruoyi.memberManagement.domain.memberTag;
import com.ruoyi.memberManagement.service.ImemberTagService;

/**
 * 标签定义Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
@Service
public class memberTagServiceImpl implements ImemberTagService 
{
    @Autowired
    private memberTagMapper memberTagMapper;

    /**
     * 查询标签定义
     * 
     * @param tagId 标签定义主键
     * @return 标签定义
     */
    @Override
    public memberTag selectmemberTagByTagId(Long tagId)
    {
        return memberTagMapper.selectmemberTagByTagId(tagId);
    }

    /**
     * 查询标签定义列表
     * 
     * @param memberTag 标签定义
     * @return 标签定义
     */
    @Override
    public List<memberTag> selectmemberTagList(memberTag memberTag)
    {
        return memberTagMapper.selectmemberTagList(memberTag);
    }

    /**
     * 新增标签定义
     * 
     * @param memberTag 标签定义
     * @return 结果
     */
    @Override
    public int insertmemberTag(memberTag memberTag)
    {
        memberTag.setCreateTime(DateUtils.getNowDate());
        return memberTagMapper.insertmemberTag(memberTag);
    }

    /**
     * 修改标签定义
     * 
     * @param memberTag 标签定义
     * @return 结果
     */
    @Override
    public int updatememberTag(memberTag memberTag)
    {
        memberTag.setUpdateTime(DateUtils.getNowDate());
        return memberTagMapper.updatememberTag(memberTag);
    }

    /**
     * 批量删除标签定义
     * 
     * @param tagIds 需要删除的标签定义主键
     * @return 结果
     */
    @Override
    public int deletememberTagByTagIds(Long[] tagIds)
    {
        return memberTagMapper.deletememberTagByTagIds(tagIds);
    }

    /**
     * 删除标签定义信息
     * 
     * @param tagId 标签定义主键
     * @return 结果
     */
    @Override
    public int deletememberTagByTagId(Long tagId)
    {
        return memberTagMapper.deletememberTagByTagId(tagId);
    }
}
