package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/friendshipRequests")
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestService friendshipRequestService;

    @GetMapping("/{email}")
    public ResponseEntity<List<FriendshipRequestDTO>> getAllFriendRequests(@PathVariable String email) {
        List<FriendshipRequestDTO> requests = friendshipRequestService.getAllFriendRequests(email);
        return ResponseEntity.ok(requests);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        return friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail)
                .map(err -> ResponseEntity.badRequest().body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been sent."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Integer friendshipRequestId) {
        return friendshipRequestService.acceptFriendshipRequest(friendshipRequestId)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been accepted."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam Integer friendshipRequestId) {
        return friendshipRequestService.rejectFriendshipRequest(friendshipRequestId)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been rejected."));
    }
}