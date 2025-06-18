package com.ruoyi.memberManagement.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.memberManagement.domain.memberUserTag;
import com.ruoyi.memberManagement.service.ImemberUserTagService;
import org.springframework.dao.DuplicateKeyException;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员-标签关联Controller
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
@RestController
@RequestMapping("/memberManagement/memberUserTag")
public class memberUserTagController extends BaseController
{
    @Autowired
    private ImemberUserTagService memberUserTagService;

    /**
     * 查询会员-标签关联列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberUserTag:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberUserTag memberUserTag)
    {
        startPage();
        List<memberUserTag> list = memberUserTagService.selectmemberUserTagList(memberUserTag);
        return getDataTable(list);
    }

    /**
     * 导出会员-标签关联列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberUserTag:export')")
    @Log(title = "会员-标签关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberUserTag memberUserTag)
    {
        List<memberUserTag> list = memberUserTagService.selectmemberUserTagList(memberUserTag);
        ExcelUtil<memberUserTag> util = new ExcelUtil<memberUserTag>(memberUserTag.class);
        util.exportExcel(response, list, "会员-标签关联数据");
    }

    /**
     * 获取会员-标签关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberUserTag:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(memberUserTagService.selectmemberUserTagByUserId(userId));
    }

    /**
     * 新增会员-标签关联
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberUserTag:add')")
    @Log(title = "会员-标签关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberUserTag memberUserTag)
    {
        // —— 在这里给它填上当前登录用户的 ID
        Long currentUserId = getUserId();
        memberUserTag.setUserId(currentUserId);

        try
        {
            return toAjax(memberUserTagService.insertmemberUserTag(memberUserTag));
        }
        catch (DuplicateKeyException ex)
        {
            // 捕获主键冲突
            return AjaxResult.error("您已添加过该标签");
        }
        catch (Exception ex)
        {
            // 其它异常再往外抛
            throw ex;
        }
    }

    /**
     * 修改会员-标签关联
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberUserTag:edit')")
    @Log(title = "会员-标签关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberUserTag memberUserTag)
    {
        return toAjax(memberUserTagService.updatememberUserTag(memberUserTag));
    }

    /**
     * 删除会员-标签关联
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberUserTag:remove')")
    @Log(title = "会员-标签关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(memberUserTagService.deletememberUserTagByUserIds(userIds));
    }
}
