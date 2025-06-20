package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.memberManagement.domain.myFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberFriendRequestMapper;
import com.ruoyi.memberManagement.domain.memberFriendRequest;
import com.ruoyi.memberManagement.service.ImemberFriendRequestService;

/**
 * 会员好友申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@Service
public class memberFriendRequestServiceImpl implements ImemberFriendRequestService 
{
    @Autowired
    private memberFriendRequestMapper memberFriendRequestMapper;
    /**
     * 查询会员好友申请
     * 
     * @param requestId 会员好友申请主键
     * @return 会员好友申请
     */
    @Override
    public memberFriendRequest selectmemberFriendRequestByRequestId(Long requestId)
    {
        return memberFriendRequestMapper.selectmemberFriendRequestByRequestId(requestId);
    }

    /**
     * 查询会员好友申请列表
     * 
     * @param memberFriendRequest 会员好友申请
     * @return 会员好友申请
     */
    @Override
    public List<memberFriendRequest> selectmemberFriendRequestList(memberFriendRequest memberFriendRequest)
    {
        return memberFriendRequestMapper.selectmemberFriendRequestList(memberFriendRequest);
    }

    /**
     * 新增会员好友申请
     * 
     * @param memberFriendRequest 会员好友申请
     * @return 结果
     */
    @Override
    public int insertmemberFriendRequest(memberFriendRequest memberFriendRequest)
    {
        memberFriendRequest.setCreateTime(DateUtils.getNowDate());
        return memberFriendRequestMapper.insertmemberFriendRequest(memberFriendRequest);
    }

    /**
     * 修改会员好友申请
     * 
     * @param memberFriendRequest 会员好友申请
     * @return 结果
     */
    @Override
    public int updatememberFriendRequest(memberFriendRequest memberFriendRequest)
    {
        memberFriendRequest.setUpdateTime(DateUtils.getNowDate());
        return memberFriendRequestMapper.updatememberFriendRequest(memberFriendRequest);
    }

    /**
     * 批量删除会员好友申请
     * 
     * @param requestIds 需要删除的会员好友申请主键
     * @return 结果
     */
    @Override
    public int deletememberFriendRequestByRequestIds(Long[] requestIds)
    {
        return memberFriendRequestMapper.deletememberFriendRequestByRequestIds(requestIds);
    }

    /**
     * 删除会员好友申请信息
     * 
     * @param requestId 会员好友申请主键
     * @return 结果
     */
    @Override
    public int deletememberFriendRequestByRequestId(Long requestId)
    {
        return memberFriendRequestMapper.deletememberFriendRequestByRequestId(requestId);
    }
}
