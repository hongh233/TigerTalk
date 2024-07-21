package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessageDTO;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friendships")
public class FriendshipMessageController {

    @Autowired
    private FriendshipMessageService friendshipMessageService;

    /**
     * Create a new friendship message.
     *
     * @param message the FriendshipMessage object containing the message details
     * @return a ResponseEntity containing a success message or an error message
     * @apiExample {json} Request-Example:
     * {
     *   "messageContent": "Hello, how are you?",
     *   "sender": {
     *     "email": "a@dal.ca"
     *   },
     *   "receiver": {
     *     "email": "n@dal.ca"
     *   },
     *   "friendship": {
     *     "friendshipId": 23
     *   }
     * }
     */
    @PostMapping("/message/create")
    public ResponseEntity<String> createMessage(@RequestBody FriendshipMessage message) {
        Optional<String> result = friendshipMessageService.createMessage(message);
        return result.map(s -> ResponseEntity.badRequest().body(s)).orElseGet(() -> ResponseEntity.ok("Message created successfully"));
    }

    /**
     * Get all messages for a given friendship ID.
     *
     * @param friendshipId the ID of the friendship
     * @return a ResponseEntity containing a list of FriendshipMessageDTOs
     */
    @GetMapping("/message/getAll/{friendshipId}")
    public List<FriendshipMessageDTO> getAllMessagesByFriendshipId(@PathVariable int friendshipId) {
        return friendshipMessageService.getAllMessagesByFriendshipId(friendshipId);
    }

    /**
     * Mark a message as read.
     *
     * @param messageId the ID of the message
     * @return a ResponseEntity containing a success message or an error message
     */
    @PatchMapping("/message/setRead/{messageId}")
    public ResponseEntity<String> markMessageAsRead(@PathVariable int messageId) {
        Optional<String> result = friendshipMessageService.markMessageAsRead(messageId);
        return result.map(s -> ResponseEntity.badRequest().body(s))
                .orElseGet(() -> ResponseEntity.ok("Message ID " + messageId + " marked as read"));
    }

}
