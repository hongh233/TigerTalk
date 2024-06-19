package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.service.Friend.AcceptFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend")
public class AcceptFriendController {
    @Autowired
    private AcceptFriendService acceptFriendService;

    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriend(@RequestParam Integer friendshipRequestId) {
        acceptFriendService.acceptFriendRequest(friendshipRequestId);
        return ResponseEntity.ok("Friend request accepted.");
    }
}