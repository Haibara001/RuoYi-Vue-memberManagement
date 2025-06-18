package com.ruoyi.memberManagement.service;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberUserTag;

/**
 * 会员-标签关联Service接口
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
public interface ImemberUserTagService 
{
    /**
     * 查询会员-标签关联
     * 
     * @param userId 会员-标签关联主键
     * @return 会员-标签关联
     */
    public memberUserTag selectmemberUserTagByUserId(Long userId);

    /**
     * 查询会员-标签关联列表
     * 
     * @param memberUserTag 会员-标签关联
     * @return 会员-标签关联集合
     */
    public List<memberUserTag> selectmemberUserTagList(memberUserTag memberUserTag);

    /**
     * 新增会员-标签关联
     * 
     * @param memberUserTag 会员-标签关联
     * @return 结果
     */
    public int insertmemberUserTag(memberUserTag memberUserTag);

    /**
     * 修改会员-标签关联
     * 
     * @param memberUserTag 会员-标签关联
     * @return 结果
     */
    public int updatememberUserTag(memberUserTag memberUserTag);

    /**
     * 批量删除会员-标签关联
     * 
     * @param userIds 需要删除的会员-标签关联主键集合
     * @return 结果
     */
    public int deletememberUserTagByUserIds(Long[] userIds);

    /**
     * 删除会员-标签关联信息
     * 
     * @param userId 会员-标签关联主键
     * @return 结果
     */
    public int deletememberUserTagByUserId(Long userId);
}
