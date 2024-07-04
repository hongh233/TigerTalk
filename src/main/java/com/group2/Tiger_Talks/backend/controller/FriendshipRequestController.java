package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipRequestDTO;
import com.group2.Tiger_Talks.backend.model.Test;
import com.group2.Tiger_Talks.backend.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.group2.Tiger_Talks.backend.model.Utils.TestGen.MAX_USERS;
import static com.group2.Tiger_Talks.backend.model.Utils.TestGen.MIN_USERS;

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
                .map(ResponseEntity.status(404)::body)
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
                .map(ResponseEntity.status(404)::body)
                .orElseGet(() -> ResponseEntity.ok("Friend request has been rejected."));
    }

    @PostMapping("/Test")
    public String bulkFriends(@RequestParam("numOfUsers") int numOfUsers) {
        if (numOfUsers > MAX_USERS || MIN_USERS > numOfUsers)
            throw new IllegalArgumentException("Cannot generate more than 26 and less than 3 users as the names are A-Z check impl to change this behaviour");

        int MAIN_USER = Test.alpha((numOfUsers / 2)) ^ 32;
        Queue<Integer> queue = new LinkedList<>(List.of(MAIN_USER));
        HashSet<Integer> friends = new HashSet<>();

        final String email = "%c@dal.ca";

        while (!queue.isEmpty()) {
            int curr = queue.remove();
            if (friends.contains(curr) || curr < (Test.alpha(0) ^ 32) || curr > (Test.alpha(numOfUsers) ^ 32))
                continue;
            friends.add(curr);

            int LF = curr - 2; // Left Friend
            if (LF >= (Test.alpha(0) ^ 32)) {
                createFriendshipReturningRequestID(
                        (char) MAIN_USER,
                        queue,
                        (char) curr,
                        (char) LF
                );
            }

            int RF = curr + 2;  // Right Friend
            if (RF < (Test.alpha(numOfUsers) ^ 32)) {
                createFriendshipReturningRequestID(
                        (char) MAIN_USER,
                        queue,
                        (char) curr,
                        (char) RF
                );
            }
        }


        var numOfTotalRequests = friendshipRequestService.findNumOfTotalRequests();
        for (int i = 0; i < numOfTotalRequests;) {
            acceptFriendRequest(++i);
        }

        System.out.println("DONE");

        return email.formatted(MAIN_USER);
    }

    private void createFriendshipReturningRequestID(char MAIN_USER,
                                                    Queue<Integer> queue,
                                                    char curr,
                                                    char friendNum) {
        sendFriendRequest(
                "%c@dal.ca".formatted(curr),
                "%c@dal.ca".formatted(friendNum)
        );

        if (MAIN_USER != friendNum) {
            sendFriendRequest(
                    "%c@dal.ca".formatted(MAIN_USER),
                    "%c@dal.ca".formatted(friendNum)
            );
        }

        queue.add((int) friendNum);
    }
}
