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
import com.ruoyi.memberManagement.domain.memberProfile;
import com.ruoyi.memberManagement.service.ImemberProfileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员扩展资料Controller
 * 
 * @author ruoyi
 * @date 2025-06-12
 */
@RestController
@RequestMapping("/memberManagement/memberProfile")
public class memberProfileController extends BaseController
{
    @Autowired
    private ImemberProfileService memberProfileService;

    /**
     * 查询会员扩展资料列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberProfile:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberProfile memberProfile)
    {
        startPage();
        List<memberProfile> list = memberProfileService.selectmemberProfileList(memberProfile);
        return getDataTable(list);
    }

    /**
     * 导出会员扩展资料列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberProfile:export')")
    @Log(title = "会员扩展资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberProfile memberProfile)
    {
        List<memberProfile> list = memberProfileService.selectmemberProfileList(memberProfile);
        ExcelUtil<memberProfile> util = new ExcelUtil<memberProfile>(memberProfile.class);
        util.exportExcel(response, list, "会员扩展资料数据");
    }

    /**
     * 获取会员扩展资料详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberProfile:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(memberProfileService.selectmemberProfileByUserId(userId));
    }

    /**
     * 新增会员扩展资料
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberProfile:add')")
    @Log(title = "会员扩展资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberProfile memberProfile)
    {
        // 获取当前登录用户的ID
        Long currentUserId = getUserId();
        // 设置用户ID
        memberProfile.setUserId(currentUserId);
        
        return toAjax(memberProfileService.insertmemberProfile(memberProfile));
    }

    /**
     * 修改会员扩展资料
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberProfile:edit')")
    @Log(title = "会员扩展资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberProfile memberProfile)
    {
        return toAjax(memberProfileService.updatememberProfile(memberProfile));
    }

    /**
     * 删除会员扩展资料
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberProfile:remove')")
    @Log(title = "会员扩展资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(memberProfileService.deletememberProfileByUserIds(userIds));
    }
}
