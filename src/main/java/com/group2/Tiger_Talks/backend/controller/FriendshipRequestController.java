package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

/**
 * REST controller for handling friendship requests.
 */
@RestController
@RequestMapping("/friendshipRequests")
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestService friendshipRequestService;

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
                .map(err -> ResponseEntity.badRequest().body(err))
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
                .map(err -> ResponseEntity.status(404).body(err))
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
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friend request has been rejected."));
    }
}
