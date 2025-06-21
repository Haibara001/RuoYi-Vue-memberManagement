package com.ruoyi.memberManagement.mapper;

import java.util.List;
import com.ruoyi.memberManagement.domain.memberPost;

/**
 * 会员日志/帖子Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-21
 */
public interface memberPostMapper 
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
     * 删除会员日志/帖子
     * 
     * @param postId 会员日志/帖子主键
     * @return 结果
     */
    public int deletememberPostByPostId(Long postId);

    /**
     * 批量删除会员日志/帖子
     * 
     * @param postIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletememberPostByPostIds(Long[] postIds);
}
