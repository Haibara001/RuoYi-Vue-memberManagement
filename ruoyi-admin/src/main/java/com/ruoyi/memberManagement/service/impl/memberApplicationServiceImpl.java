package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberApplicationMapper;
import com.ruoyi.memberManagement.domain.memberApplication;
import com.ruoyi.memberManagement.service.ImemberApplicationService;

/**
 * 会员入会申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class memberApplicationServiceImpl implements ImemberApplicationService 
{
    @Autowired
    private memberApplicationMapper memberApplicationMapper;

    /**
     * 查询会员入会申请
     * 
     * @param appId 会员入会申请主键
     * @return 会员入会申请
     */
    @Override
    public memberApplication selectmemberApplicationByAppId(Long appId)
    {
        return memberApplicationMapper.selectmemberApplicationByAppId(appId);
    }

    /**
     * 查询会员入会申请列表
     * 
     * @param memberApplication 会员入会申请
     * @return 会员入会申请
     */
    @Override
    public List<memberApplication> selectmemberApplicationList(memberApplication memberApplication)
    {
        return memberApplicationMapper.selectmemberApplicationList(memberApplication);
    }

    /**
     * 新增会员入会申请
     * 
     * @param memberApplication 会员入会申请
     * @return 结果
     */
    @Override
    public int insertmemberApplication(memberApplication memberApplication)
    {
        memberApplication.setCreateTime(DateUtils.getNowDate());
        return memberApplicationMapper.insertmemberApplication(memberApplication);
    }

    /**
     * 修改会员入会申请
     * 
     * @param memberApplication 会员入会申请
     * @return 结果
     */
    @Override
    public int updatememberApplication(memberApplication memberApplication)
    {
        memberApplication.setUpdateTime(DateUtils.getNowDate());
        return memberApplicationMapper.updatememberApplication(memberApplication);
    }

    /**
     * 批量删除会员入会申请
     * 
     * @param appIds 需要删除的会员入会申请主键
     * @return 结果
     */
    @Override
    public int deletememberApplicationByAppIds(Long[] appIds)
    {
        return memberApplicationMapper.deletememberApplicationByAppIds(appIds);
    }

    /**
     * 删除会员入会申请信息
     * 
     * @param appId 会员入会申请主键
     * @return 结果
     */
    @Override
    public int deletememberApplicationByAppId(Long appId)
    {
        return memberApplicationMapper.deletememberApplicationByAppId(appId);
    }
}
