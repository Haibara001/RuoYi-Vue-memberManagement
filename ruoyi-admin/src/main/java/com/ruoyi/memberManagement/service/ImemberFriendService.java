package com.ruoyi.memberManagement.service;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberFriend;
import com.ruoyi.memberManagement.domain.myFriend;

/**
 * 会员好友关系Service接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface ImemberFriendService 
{



    /**
     * 查询“我的好友”列表
     * @param userId 当前登录用户ID
     * @return 对方 ID + 对方姓名 + 成为好友时间
     */
    List<myFriend> listMyFriends(Long userId);


    /**
     * 查询会员好友关系
     * 
     * @param userA 会员好友关系主键
     * @return 会员好友关系
     */
    public memberFriend selectmemberFriendByUserA(Long userA);

    /**
     * 查询会员好友关系列表
     * 
     * @param memberFriend 会员好友关系
     * @return 会员好友关系集合
     */
    public List<memberFriend> selectmemberFriendList(memberFriend memberFriend);

    /**
     * 新增会员好友关系
     * 
     * @param memberFriend 会员好友关系
     * @return 结果
     */
    public int insertmemberFriend(memberFriend memberFriend);

    /**
     * 修改会员好友关系
     * 
     * @param memberFriend 会员好友关系
     * @return 结果
     */
    public int updatememberFriend(memberFriend memberFriend);

    /**
     * 批量删除会员好友关系
     * 
     * @param userAs 需要删除的会员好友关系主键集合
     * @return 结果
     */
    public int deletememberFriendByUserAs(Long[] userAs);

    /**
     * 删除会员好友关系信息
     * 
     * @param userA 会员好友关系主键
     * @return 结果
     */
    public int deletememberFriendByUserA(Long userA);

    /** 删除 userA→userB 的好友关系 */
    int deleteByUserAAndUserB(Long userA, Long userB);

}
