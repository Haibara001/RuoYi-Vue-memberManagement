package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberFriendMapper;
import com.ruoyi.memberManagement.domain.memberFriend;
import com.ruoyi.memberManagement.service.ImemberFriendService;

/**
 * 会员好友关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@Service
public class memberFriendServiceImpl implements ImemberFriendService 
{
    @Autowired
    private memberFriendMapper memberFriendMapper;

    /**
     * 查询会员好友关系
     * 
     * @param userA 会员好友关系主键
     * @return 会员好友关系
     */
    @Override
    public memberFriend selectmemberFriendByUserA(Long userA)
    {
        return memberFriendMapper.selectmemberFriendByUserA(userA);
    }

    /**
     * 查询会员好友关系列表
     * 
     * @param memberFriend 会员好友关系
     * @return 会员好友关系
     */
    @Override
    public List<memberFriend> selectmemberFriendList(memberFriend memberFriend)
    {
        return memberFriendMapper.selectmemberFriendList(memberFriend);
    }

    /**
     * 新增会员好友关系
     * 
     * @param memberFriend 会员好友关系
     * @return 结果
     */
    @Override
    public int insertmemberFriend(memberFriend memberFriend)
    {
        memberFriend.setCreateTime(DateUtils.getNowDate());
        return memberFriendMapper.insertmemberFriend(memberFriend);
    }

    /**
     * 修改会员好友关系
     * 
     * @param memberFriend 会员好友关系
     * @return 结果
     */
    @Override
    public int updatememberFriend(memberFriend memberFriend)
    {
        return memberFriendMapper.updatememberFriend(memberFriend);
    }

    /**
     * 批量删除会员好友关系
     * 
     * @param userAs 需要删除的会员好友关系主键
     * @return 结果
     */
    @Override
    public int deletememberFriendByUserAs(Long[] userAs)
    {
        return memberFriendMapper.deletememberFriendByUserAs(userAs);
    }

    /**
     * 删除会员好友关系信息
     * 
     * @param userA 会员好友关系主键
     * @return 结果
     */
    @Override
    public int deletememberFriendByUserA(Long userA)
    {
        return memberFriendMapper.deletememberFriendByUserA(userA);
    }
}
