package com.ruoyi.memberManagement.controller;

import java.util.Date;
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
import com.ruoyi.memberManagement.domain.memberApplication;
import com.ruoyi.memberManagement.service.ImemberApplicationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import com.ruoyi.system.service.ISysRoleService;


/**
 * 会员入会申请Controller
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@RestController
@RequestMapping("/memberManagement/memberApplication")
public class memberApplicationController extends BaseController
{
    @Autowired
    private ImemberApplicationService memberApplicationService;
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 查询会员入会申请列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberApplication:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberApplication memberApplication)
    {
        startPage();
        List<memberApplication> list = memberApplicationService.selectmemberApplicationList(memberApplication);
        return getDataTable(list);
    }

    /**
     * 导出会员入会申请列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberApplication:export')")
    @Log(title = "会员入会申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberApplication memberApplication)
    {
        List<memberApplication> list = memberApplicationService.selectmemberApplicationList(memberApplication);
        ExcelUtil<memberApplication> util = new ExcelUtil<memberApplication>(memberApplication.class);
        util.exportExcel(response, list, "会员入会申请数据");
    }

    /**
     * 获取会员入会申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberApplication:query')")
    @GetMapping(value = "/{appId}")
    public AjaxResult getInfo(@PathVariable("appId") Long appId)
    {
        return success(memberApplicationService.selectmemberApplicationByAppId(appId));
    }

    /**
     * 新增会员入会申请
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberApplication:add')")
    @Log(title = "会员入会申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberApplication memberApplication)
    {
        Long currentUserId = getUserId();
        memberApplication.setUserId(currentUserId);
        return toAjax(memberApplicationService.insertmemberApplication(memberApplication));
    }

    /**
     * 修改会员入会申请
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberApplication:edit')")
    @Log(title = "会员入会申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberApplication memberApplication)
    {
        // 如果状态是审核操作，则自动记录当前用户和当前时间
        String newStatus = memberApplication.getStatus();
        // 只针对“待审核”→“已通过/已拒绝”这种真正的审核操作执行
        if ("已通过".equals(newStatus) || "已拒绝".equals(newStatus)) {
            // 当前登录人的 userId
            Long currentUserId = getUserId();
            memberApplication.setReviewerId(currentUserId);
            memberApplication.setReviewTime(new Date());
        }

        // 1. 更新入会申请表
        int rows = memberApplicationService.updatememberApplication(memberApplication);
        if (rows <= 0) {
            return AjaxResult.error("更新失败");
        }

        // 2. 如果审核通过，则切换角色
        if ("已通过".equals(newStatus)) {
            Long userId = memberApplication.getUserId();
            Long memberRoleId  = 2L;   // 你库里“会员”对应的 role_id
            Long visitorRoleId = 101L;   // 你库里“游客”对应的 role_id

            sysRoleService.deleteAuthUsers(visitorRoleId, new Long[]{userId});
            sysRoleService.insertAuthUsers(memberRoleId, new Long[]{userId});
//            // 1) 先从“游客”角色里把这个用户移除
//            sysRoleService.deleteAuthUsers(visitorRoleId, new Long[]{ userId });
//            // 2) 再把这个用户加入到“会员”角色
//            sysRoleService.insertAuthUsers(visitorRoleId /* 注意：第一个参数是roleId */,
//                    new Long[]{ userId });

//            // 删除“游客”角色
//            sysRoleService.deleteUserRoleInfo(userId, visitorRoleId);
//            // 添加“会员”角色
//            sysRoleService.insertUserRole(userId, memberRoleId);

        }

        return toAjax(memberApplicationService.updatememberApplication(memberApplication));
    }

    /**
     * 删除会员入会申请
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberApplication:remove')")
    @Log(title = "会员入会申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appIds}")
    public AjaxResult remove(@PathVariable Long[] appIds)
    {
        return toAjax(memberApplicationService.deletememberApplicationByAppIds(appIds));
    }
}
