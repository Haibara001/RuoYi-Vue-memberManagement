package com.ruoyi.memberManagement.mapper;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberFriend;

/**
 * 会员好友关系Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface memberFriendMapper 
{
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
     * 删除会员好友关系
     * 
     * @param userA 会员好友关系主键
     * @return 结果
     */
    public int deletememberFriendByUserA(Long userA);

    /**
     * 批量删除会员好友关系
     * 
     * @param userAs 需要删除的数据主键集合
     * @return 结果
     */
    public int deletememberFriendByUserAs(Long[] userAs);
}
