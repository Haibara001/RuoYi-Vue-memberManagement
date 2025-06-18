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
import com.ruoyi.memberManagement.domain.memberFriendRequest;
import com.ruoyi.memberManagement.service.ImemberFriendRequestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员好友申请Controller
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/memberManagement/memberFriendRequest")
public class memberFriendRequestController extends BaseController
{
    @Autowired
    private ImemberFriendRequestService memberFriendRequestService;

    /**
     * 查询会员好友申请列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriendRequest:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberFriendRequest memberFriendRequest)
    {
        startPage();
        List<memberFriendRequest> list = memberFriendRequestService.selectmemberFriendRequestList(memberFriendRequest);
        return getDataTable(list);
    }

    /**
     * 导出会员好友申请列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriendRequest:export')")
    @Log(title = "会员好友申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberFriendRequest memberFriendRequest)
    {
        List<memberFriendRequest> list = memberFriendRequestService.selectmemberFriendRequestList(memberFriendRequest);
        ExcelUtil<memberFriendRequest> util = new ExcelUtil<memberFriendRequest>(memberFriendRequest.class);
        util.exportExcel(response, list, "会员好友申请数据");
    }

    /**
     * 获取会员好友申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriendRequest:query')")
    @GetMapping(value = "/{requestId}")
    public AjaxResult getInfo(@PathVariable("requestId") Long requestId)
    {
        return success(memberFriendRequestService.selectmemberFriendRequestByRequestId(requestId));
    }

    /**
     * 新增会员好友申请
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriendRequest:add')")
    @Log(title = "会员好友申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberFriendRequest memberFriendRequest)
    {
        return toAjax(memberFriendRequestService.insertmemberFriendRequest(memberFriendRequest));
    }

    /**
     * 修改会员好友申请
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriendRequest:edit')")
    @Log(title = "会员好友申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberFriendRequest memberFriendRequest)
    {
        return toAjax(memberFriendRequestService.updatememberFriendRequest(memberFriendRequest));
    }

    /**
     * 删除会员好友申请
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriendRequest:remove')")
    @Log(title = "会员好友申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{requestIds}")
    public AjaxResult remove(@PathVariable Long[] requestIds)
    {
        return toAjax(memberFriendRequestService.deletememberFriendRequestByRequestIds(requestIds));
    }
}
