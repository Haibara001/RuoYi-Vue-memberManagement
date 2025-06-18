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
import com.ruoyi.memberManagement.domain.memberFriend;
import com.ruoyi.memberManagement.service.ImemberFriendService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员好友关系Controller
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/memberManagement/memberFriend")
public class memberFriendController extends BaseController
{
    @Autowired
    private ImemberFriendService memberFriendService;

    /**
     * 查询会员好友关系列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriend:list')")
    @GetMapping("/list")
    public TableDataInfo list(memberFriend memberFriend)
    {
        startPage();
        List<memberFriend> list = memberFriendService.selectmemberFriendList(memberFriend);
        return getDataTable(list);
    }

    /**
     * 导出会员好友关系列表
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriend:export')")
    @Log(title = "会员好友关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, memberFriend memberFriend)
    {
        List<memberFriend> list = memberFriendService.selectmemberFriendList(memberFriend);
        ExcelUtil<memberFriend> util = new ExcelUtil<memberFriend>(memberFriend.class);
        util.exportExcel(response, list, "会员好友关系数据");
    }

    /**
     * 获取会员好友关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriend:query')")
    @GetMapping(value = "/{userA}")
    public AjaxResult getInfo(@PathVariable("userA") Long userA)
    {
        return success(memberFriendService.selectmemberFriendByUserA(userA));
    }

    /**
     * 新增会员好友关系
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriend:add')")
    @Log(title = "会员好友关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody memberFriend memberFriend)
    {
        Long userA = memberFriend.getUserA();
        Long userB = memberFriend.getUserB();
        Date now = new Date();

        // 先插 A→B
        memberFriend mf1 = new memberFriend();
        mf1.setUserA(userA);
        mf1.setUserB(userB);
        mf1.setCreateTime(now);
        int rows1 = memberFriendService.insertmemberFriend(mf1);

        // 再插 B→A
        memberFriend mf2 = new memberFriend();
        mf2.setUserA(userB);
        mf2.setUserB(userA);
        mf2.setCreateTime(now);
        int rows2 = memberFriendService.insertmemberFriend(mf2);

        // rows1 + rows2 应该是 2 才算全部成功
        int total = rows1 + rows2;
        return total == 2
                ? AjaxResult.success("好友关系添加成功")
                : AjaxResult.error("好友关系添加失败");
    }

    /**
     * 修改会员好友关系
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriend:edit')")
    @Log(title = "会员好友关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody memberFriend memberFriend)
    {
        return toAjax(memberFriendService.updatememberFriend(memberFriend));
    }

    /**
     * 删除会员好友关系
     */
    @PreAuthorize("@ss.hasPermi('memberManagement:memberFriend:remove')")
    @Log(title = "会员好友关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userAs}")
    public AjaxResult remove(@PathVariable Long[] userAs)
    {
        return toAjax(memberFriendService.deletememberFriendByUserAs(userAs));
    }
}
