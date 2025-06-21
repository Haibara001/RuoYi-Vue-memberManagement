package com.ruoyi.memberManagement.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.memberManagement.mapper.memberPostMapper;
import com.ruoyi.memberManagement.domain.memberPost;
import com.ruoyi.memberManagement.service.ImemberPostService;

/**
 * 会员日志/帖子Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-21
 */
@Service
public class memberPostServiceImpl implements ImemberPostService 
{
    @Autowired
    private memberPostMapper memberPostMapper;

    /**
     * 查询会员日志/帖子
     * 
     * @param postId 会员日志/帖子主键
     * @return 会员日志/帖子
     */
    @Override
    public memberPost selectmemberPostByPostId(Long postId)
    {
        return memberPostMapper.selectmemberPostByPostId(postId);
    }

    /**
     * 查询会员日志/帖子列表
     * 
     * @param memberPost 会员日志/帖子
     * @return 会员日志/帖子
     */
    @Override
    public List<memberPost> selectmemberPostList(memberPost memberPost)
    {
        return memberPostMapper.selectmemberPostList(memberPost);
    }

    /**
     * 新增会员日志/帖子
     * 
     * @param memberPost 会员日志/帖子
     * @return 结果
     */
    @Override
    public int insertmemberPost(memberPost memberPost)
    {
        memberPost.setCreateTime(DateUtils.getNowDate());
        return memberPostMapper.insertmemberPost(memberPost);
    }

    /**
     * 修改会员日志/帖子
     * 
     * @param memberPost 会员日志/帖子
     * @return 结果
     */
    @Override
    public int updatememberPost(memberPost memberPost)
    {
        memberPost.setUpdateTime(DateUtils.getNowDate());
        return memberPostMapper.updatememberPost(memberPost);
    }

    /**
     * 批量删除会员日志/帖子
     * 
     * @param postIds 需要删除的会员日志/帖子主键
     * @return 结果
     */
    @Override
    public int deletememberPostByPostIds(Long[] postIds)
    {
        return memberPostMapper.deletememberPostByPostIds(postIds);
    }

    /**
     * 删除会员日志/帖子信息
     * 
     * @param postId 会员日志/帖子主键
     * @return 结果
     */
    @Override
    public int deletememberPostByPostId(Long postId)
    {
        return memberPostMapper.deletememberPostByPostId(postId);
    }
}
