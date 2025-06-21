package com.ruoyi.memberManagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.memberManagement.domain.memberPost;
import com.ruoyi.memberManagement.service.ImemberPostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员日志/帖子Controller
 * 
 * @author ruoyi
 * @date 2025-06-21
 */
@RestController
@RequestMapping("/memberManagement/memberPost")
public class memberPostController extends BaseController
{
    @Autowired
    private ImemberPostService memberPostService;

    /**
     * 查询会员日志/帖子列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberPost:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberPost memberPost)
    {
        startPage();
        List<memberPost> list = memberPostService.selectmemberPostList(memberPost);
        return getDataTable(list);
    }

    /**
     * 导出会员日志/帖子列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberPost:export')")
    @Log(title = "会员日志/帖子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberPost memberPost)
    {
        List<memberPost> list = memberPostService.selectmemberPostList(memberPost);
        ExcelUtil<memberPost> util = new ExcelUtil<memberPost>(memberPost.class);
        util.exportExcel(response, list, "会员日志/帖子数据");
    }

    /**
     * 获取会员日志/帖子详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberPost:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable("postId") Long postId)
    {
        return success(memberPostService.selectmemberPostByPostId(postId));
    }

    /**
     * 新增会员日志/帖子
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberPost:add')")
    @Log(title = "会员日志/帖子", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberPost memberPost)
    {
        memberPost.setAuthorId(this.getUserId());
        return toAjax(memberPostService.insertmemberPost(memberPost));
    }

    /**
     * 修改会员日志/帖子
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberPost:edit')")
    @Log(title = "会员日志/帖子", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberPost memberPost)
    {
        return toAjax(memberPostService.updatememberPost(memberPost));
    }

    /**
     * 删除会员日志/帖子
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberPost:remove')")
    @Log(title = "会员日志/帖子", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(memberPostService.deletememberPostByPostIds(postIds));
    }
}
