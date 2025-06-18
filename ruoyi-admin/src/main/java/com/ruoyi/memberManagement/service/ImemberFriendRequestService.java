package com.ruoyi.memberManagement.service;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberFriendRequest;

/**
 * 会员好友申请Service接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface ImemberFriendRequestService 
{
    /**
     * 查询会员好友申请
     * 
     * @param requestId 会员好友申请主键
     * @return 会员好友申请
     */
    public memberFriendRequest selectmemberFriendRequestByRequestId(Long requestId);

    /**
     * 查询会员好友申请列表
     * 
     * @param memberFriendRequest 会员好友申请
     * @return 会员好友申请集合
     */
    public List<memberFriendRequest> selectmemberFriendRequestList(memberFriendRequest memberFriendRequest);

    /**
     * 新增会员好友申请
     * 
     * @param memberFriendRequest 会员好友申请
     * @return 结果
     */
    public int insertmemberFriendRequest(memberFriendRequest memberFriendRequest);

    /**
     * 修改会员好友申请
     * 
     * @param memberFriendRequest 会员好友申请
     * @return 结果
     */
    public int updatememberFriendRequest(memberFriendRequest memberFriendRequest);

    /**
     * 批量删除会员好友申请
     * 
     * @param requestIds 需要删除的会员好友申请主键集合
     * @return 结果
     */
    public int deletememberFriendRequestByRequestIds(Long[] requestIds);

    /**
     * 删除会员好友申请信息
     * 
     * @param requestId 会员好友申请主键
     * @return 结果
     */
    public int deletememberFriendRequestByRequestId(Long requestId);
}
