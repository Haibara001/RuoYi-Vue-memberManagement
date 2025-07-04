package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberUserTagMapper;
import com.ruoyi.memberManagement.domain.memberUserTag;
import com.ruoyi.memberManagement.service.ImemberUserTagService;

/**
 * 会员-标签关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
@Service
public class memberUserTagServiceImpl implements ImemberUserTagService 
{
    @Autowired
    private memberUserTagMapper memberUserTagMapper;

    /**
     * 查询会员-标签关联
     * 
     * @param userId 会员-标签关联主键
     * @return 会员-标签关联
     */
    @Override
    public memberUserTag selectmemberUserTagByUserId(Long userId)
    {
        return memberUserTagMapper.selectmemberUserTagByUserId(userId);
    }

    /**
     * 查询会员-标签关联列表
     * 
     * @param memberUserTag 会员-标签关联
     * @return 会员-标签关联
     */
    @Override
    public List<memberUserTag> selectmemberUserTagList(memberUserTag memberUserTag)
    {
        return memberUserTagMapper.selectmemberUserTagList(memberUserTag);
    }

    /**
     * 新增会员-标签关联
     * 
     * @param memberUserTag 会员-标签关联
     * @return 结果
     */
    @Override
    public int insertmemberUserTag(memberUserTag memberUserTag)
    {
        return memberUserTagMapper.insertmemberUserTag(memberUserTag);
    }

    /**
     * 修改会员-标签关联
     * 
     * @param memberUserTag 会员-标签关联
     * @return 结果
     */
    @Override
    public int updatememberUserTag(memberUserTag memberUserTag)
    {
        return memberUserTagMapper.updatememberUserTag(memberUserTag);
    }

    /**
     * 批量删除会员-标签关联
     * 
     * @param userIds 需要删除的会员-标签关联主键
     * @return 结果
     */
    @Override
    public int deletememberUserTagByUserIds(Long[] userIds)
    {
        return memberUserTagMapper.deletememberUserTagByUserIds(userIds);
    }

    /**
     * 删除会员-标签关联信息
     * 
     * @param userId 会员-标签关联主键
     * @return 结果
     */
    @Override
    public int deletememberUserTagByUserId(Long userId)
    {
        return memberUserTagMapper.deletememberUserTagByUserId(userId);
    }
}
