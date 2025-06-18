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
import com.ruoyi.memberManagement.domain.memberTag;
import com.ruoyi.memberManagement.service.ImemberTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标签定义Controller
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
@RestController
@RequestMapping("/memberManagement/memberTag")
public class memberTagController extends BaseController
{
    @Autowired
    private ImemberTagService memberTagService;

    /**
     * 查询标签定义列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberTag:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberTag memberTag)
    {
        startPage();
        List<memberTag> list = memberTagService.selectmemberTagList(memberTag);
        return getDataTable(list);
    }

    /**
     * 导出标签定义列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberTag:export')")
    @Log(title = "标签定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberTag memberTag)
    {
        List<memberTag> list = memberTagService.selectmemberTagList(memberTag);
        ExcelUtil<memberTag> util = new ExcelUtil<memberTag>(memberTag.class);
        util.exportExcel(response, list, "标签定义数据");
    }

    /**
     * 获取标签定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberTag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") Long tagId)
    {
        return success(memberTagService.selectmemberTagByTagId(tagId));
    }

    /**
     * 新增标签定义
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberTag:add')")
    @Log(title = "标签定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberTag memberTag)
    {
        Long currentUserId = getUserId();
        memberTag.setCreateBy(String.valueOf(currentUserId));
        return toAjax(memberTagService.insertmemberTag(memberTag));
    }

    /**
     * 修改标签定义
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberTag:edit')")
    @Log(title = "标签定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberTag memberTag)
    {
        return toAjax(memberTagService.updatememberTag(memberTag));
    }

    /**
     * 删除标签定义
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberTag:remove')")
    @Log(title = "标签定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds)
    {
        return toAjax(memberTagService.deletememberTagByTagIds(tagIds));
    }
}
