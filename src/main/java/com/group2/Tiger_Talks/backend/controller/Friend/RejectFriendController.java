package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.service.Friend.RejectFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friend")
public class RejectFriendController {
    @Autowired
    private RejectFriendService rejectFriendService;

    @PostMapping("/reject")
    public ResponseEntity<?> rejectFriend(@RequestParam Integer friendshipRequestId) {
        rejectFriendService.rejectFriendRequest(friendshipRequestId);
        return ResponseEntity.ok("Friend request rejected.");
    }
}