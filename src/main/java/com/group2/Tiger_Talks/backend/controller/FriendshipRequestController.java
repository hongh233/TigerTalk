package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.service.Socials.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendshipRequests")
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestService friendshipRequestService;

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        return friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail)
                .map(err -> ResponseEntity.badRequest().body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been sent."));
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Integer friendshipRequestId) {
        return friendshipRequestService.acceptFriendshipRequest(friendshipRequestId)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been accepted."));
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam Integer friendshipRequestId) {
        return friendshipRequestService.rejectFriendshipRequest(friendshipRequestId)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been rejected."));
    }
}