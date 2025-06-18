package com.ruoyi.memberManagement.service;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberProfile;

/**
 * 会员扩展资料Service接口
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
public interface ImemberProfileService 
{
    /**
     * 查询会员扩展资料
     * 
     * @param userId 会员扩展资料主键
     * @return 会员扩展资料
     */
    public memberProfile selectmemberProfileByUserId(Long userId);

    /**
     * 查询会员扩展资料列表
     * 
     * @param memberProfile 会员扩展资料
     * @return 会员扩展资料集合
     */
    public List<memberProfile> selectmemberProfileList(memberProfile memberProfile);

    /**
     * 新增会员扩展资料
     * 
     * @param memberProfile 会员扩展资料
     * @return 结果
     */
    public int insertmemberProfile(memberProfile memberProfile);

    /**
     * 修改会员扩展资料
     * 
     * @param memberProfile 会员扩展资料
     * @return 结果
     */
    public int updatememberProfile(memberProfile memberProfile);

    /**
     * 批量删除会员扩展资料
     * 
     * @param userIds 需要删除的会员扩展资料主键集合
     * @return 结果
     */
    public int deletememberProfileByUserIds(Long[] userIds);

    /**
     * 删除会员扩展资料信息
     * 
     * @param userId 会员扩展资料主键
     * @return 结果
     */
    public int deletememberProfileByUserId(Long userId);
}
