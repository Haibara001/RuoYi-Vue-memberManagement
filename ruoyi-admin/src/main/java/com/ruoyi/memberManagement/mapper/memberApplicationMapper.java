package com.ruoyi.memberManagement.mapper;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberApplication;

/**
 * 会员入会申请Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface memberApplicationMapper 
{
    /**
     * 查询会员入会申请
     * 
     * @param appId 会员入会申请主键
     * @return 会员入会申请
     */
    public memberApplication selectmemberApplicationByAppId(Long appId);

    /**
     * 查询会员入会申请列表
     * 
     * @param memberApplication 会员入会申请
     * @return 会员入会申请集合
     */
    public List<memberApplication> selectmemberApplicationList(memberApplication memberApplication);

    /**
     * 新增会员入会申请
     * 
     * @param memberApplication 会员入会申请
     * @return 结果
     */
    public int insertmemberApplication(memberApplication memberApplication);

    /**
     * 修改会员入会申请
     * 
     * @param memberApplication 会员入会申请
     * @return 结果
     */
    public int updatememberApplication(memberApplication memberApplication);

    /**
     * 删除会员入会申请
     * 
     * @param appId 会员入会申请主键
     * @return 结果
     */
    public int deletememberApplicationByAppId(Long appId);

    /**
     * 批量删除会员入会申请
     * 
     * @param appIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletememberApplicationByAppIds(Long[] appIds);
}
