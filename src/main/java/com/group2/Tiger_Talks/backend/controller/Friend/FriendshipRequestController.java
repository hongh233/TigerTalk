package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling friendship requests.
 */
@RestController
@RequestMapping("/friendshipRequests")
public class FriendshipRequestController {

    private final FriendshipRequestService friendshipRequestService;

    public FriendshipRequestController(FriendshipRequestService friendshipRequestService) {
        this.friendshipRequestService = friendshipRequestService;
    }

    /**
     * Retrieves all friendship requests for a given email.
     *
     * @param email the email of the user whose friendship requests are to be retrieved
     * @return ResponseEntity with a list of friendship request DTOs
     */
    @GetMapping("/{email}")
    public ResponseEntity<List<FriendshipRequestDTO>> getAllFriendRequests(@PathVariable("email") String email) {
        List<FriendshipRequestDTO> requests = friendshipRequestService.getAllFriendRequests(email);
        return ResponseEntity.ok(requests);
    }

    /**
     * Sends a friendship request from one user to another.
     *
     * @param senderEmail   the email of the user sending the request
     * @param receiverEmail the email of the user receiving the request
     * @return ResponseEntity with a success message or an error message
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        return friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("Friend request has been sent."));
    }

    /**
     * Accepts a friendship request.
     *
     * @param friendshipRequestId the ID of the friendship request to accept
     * @return ResponseEntity with a success message or an error message
     */
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam("id") Integer friendshipRequestId) {
        return friendshipRequestService.acceptFriendshipRequest(friendshipRequestId)
                .map(ResponseEntity.status(HttpStatus.NOT_FOUND)::body)
                .orElseGet(() -> ResponseEntity.ok("Friend request has been accepted."));
    }

    /**
     * Rejects a friendship request.
     *
     * @param friendshipRequestId the ID of the friendship request to reject
     * @return ResponseEntity with a success message or an error message
     */
    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam("id") Integer friendshipRequestId) {
        return friendshipRequestService.rejectFriendshipRequest(friendshipRequestId)
                .map(ResponseEntity.status(HttpStatus.NOT_FOUND)::body)
                .orElseGet(() -> ResponseEntity.ok("Friend request has been rejected."));
    }

    @GetMapping("/areFriendshipRequestExist/{email1}/{email2}")
    public boolean areFriendshipRequestExist(@PathVariable("email1") String email1, @PathVariable("email2") String email2) {
        return friendshipRequestService.areFriendshipRequestExist(email1, email2);
    }


}
