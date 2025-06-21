package com.ruoyi.memberManagement.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.memberManagement.domain.memberPost;
import com.ruoyi.memberManagement.domain.memberProfile;
import com.ruoyi.memberManagement.service.ImemberFriendService;
import com.ruoyi.memberManagement.service.ImemberPostService;
import com.ruoyi.memberManagement.service.ImemberProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;

@RestController
@RequestMapping("/memberManagement/memberSpace")
public class memberSpaceController {
    @Autowired
    private ImemberPostService memberPostService;
    @Autowired
    private ImemberFriendService memberFriendService;
    @Autowired
    private ImemberProfileService memberProfileService;

    @GetMapping("/{friendId}")
    public AjaxResult getFriendSpace(@PathVariable Long friendId) {
        Long user = getUserId();
        memberProfile profile = memberProfileService.selectmemberProfileByUserId(friendId);
        boolean isFriend = memberFriendService.areFriends(user, friendId);

        memberPost filter = new memberPost();
        filter.setAuthorId(friendId);
        List<memberPost> allPosts = memberPostService.selectmemberPostList(filter);

        List<memberPost> visiblePosts = allPosts.stream()
                .filter(post -> {
                    String vis = post.getVisibility();
                    // “全部可见”
                    if ("全部可见".equals(vis)) {
                        return true;
                    }
                    // “好友可见” 且双方是好友
                    if ("好友可见".equals(vis) && isFriend) {
                        return true;
                    }
                    // “自己可见” 且查看者是本人
                    if ("自己可见".equals(vis) && Objects.equals(user, friendId)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        // 5. 组装返回
        Map<String, Object> data = new HashMap<>();
        data.put("profile", profile);
        data.put("isFriend", isFriend);
        data.put("posts", visiblePosts);

        return AjaxResult.success(data);
    }
}
