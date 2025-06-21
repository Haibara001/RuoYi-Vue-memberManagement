package com.ruoyi.memberManagement.service;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberPost;

/**
 * 会员日志/帖子Service接口
 * 
 * @author ruoyi
 * @date 2025-06-21
 */
public interface ImemberPostService 
{
    /**
     * 查询会员日志/帖子
     * 
     * @param postId 会员日志/帖子主键
     * @return 会员日志/帖子
     */
    public memberPost selectmemberPostByPostId(Long postId);

    /**
     * 查询会员日志/帖子列表
     * 
     * @param memberPost 会员日志/帖子
     * @return 会员日志/帖子集合
     */
    public List<memberPost> selectmemberPostList(memberPost memberPost);

    /**
     * 新增会员日志/帖子
     * 
     * @param memberPost 会员日志/帖子
     * @return 结果
     */
    public int insertmemberPost(memberPost memberPost);

    /**
     * 修改会员日志/帖子
     * 
     * @param memberPost 会员日志/帖子
     * @return 结果
     */
    public int updatememberPost(memberPost memberPost);

    /**
     * 批量删除会员日志/帖子
     * 
     * @param postIds 需要删除的会员日志/帖子主键集合
     * @return 结果
     */
    public int deletememberPostByPostIds(Long[] postIds);

    /**
     * 删除会员日志/帖子信息
     * 
     * @param postId 会员日志/帖子主键
     * @return 结果
     */
    public int deletememberPostByPostId(Long postId);
}
