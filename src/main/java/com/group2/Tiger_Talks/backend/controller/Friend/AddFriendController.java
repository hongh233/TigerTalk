package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.service.Friend.AddFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend")
public class AddFriendController {
    @Autowired
    private AddFriendService addFriendService;

    @PostMapping("/add")
    public ResponseEntity<?> addFriend(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        addFriendService.sendFriendRequest(senderEmail, receiverEmail);
        return ResponseEntity.ok("Friend request sent.");
    }
}
