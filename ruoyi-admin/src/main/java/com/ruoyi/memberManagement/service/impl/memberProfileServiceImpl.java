package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberProfileMapper;
import com.ruoyi.memberManagement.domain.memberProfile;
import com.ruoyi.memberManagement.service.ImemberProfileService;

/**
 * 会员扩展资料Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
@Service
public class memberProfileServiceImpl implements ImemberProfileService 
{
    @Autowired
    private memberProfileMapper memberProfileMapper;

    /**
     * 查询会员扩展资料
     * 
     * @param userId 会员扩展资料主键
     * @return 会员扩展资料
     */
    @Override
    public memberProfile selectmemberProfileByUserId(Long userId)
    {
        return memberProfileMapper.selectmemberProfileByUserId(userId);
    }

    /**
     * 查询会员扩展资料列表
     * 
     * @param memberProfile 会员扩展资料
     * @return 会员扩展资料
     */
    @Override
    public List<memberProfile> selectmemberProfileList(memberProfile memberProfile)
    {
        return memberProfileMapper.selectmemberProfileList(memberProfile);
    }

    /**
     * 新增会员扩展资料
     * 
     * @param memberProfile 会员扩展资料
     * @return 结果
     */
    @Override
    public int insertmemberProfile(memberProfile memberProfile)
    {
        return memberProfileMapper.insertmemberProfile(memberProfile);
    }

    /**
     * 修改会员扩展资料
     * 
     * @param memberProfile 会员扩展资料
     * @return 结果
     */
    @Override
    public int updatememberProfile(memberProfile memberProfile)
    {
        memberProfile.setUpdateTime(DateUtils.getNowDate());
        return memberProfileMapper.updatememberProfile(memberProfile);
    }

    /**
     * 批量删除会员扩展资料
     * 
     * @param userIds 需要删除的会员扩展资料主键
     * @return 结果
     */
    @Override
    public int deletememberProfileByUserIds(Long[] userIds)
    {
        return memberProfileMapper.deletememberProfileByUserIds(userIds);
    }

    /**
     * 删除会员扩展资料信息
     * 
     * @param userId 会员扩展资料主键
     * @return 结果
     */
    @Override
    public int deletememberProfileByUserId(Long userId)
    {
        return memberProfileMapper.deletememberProfileByUserId(userId);
    }
}
