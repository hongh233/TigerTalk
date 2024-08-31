package tigertalk.controller.Friend;

import tigertalk.model.Friend.FriendshipMessage;
import tigertalk.model.Friend.FriendshipMessageDTO;
import tigertalk.service.Friend.FriendshipMessageService;
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
     */
    @PostMapping("/message/create")
    public ResponseEntity<String> createMessage(@RequestBody FriendshipMessage message) {
        Optional<String> error = friendshipMessageService.createMessage(message);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Message created successfully");
        }
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
        Optional<String> error = friendshipMessageService.markMessageAsRead(messageId);
        if (error.isPresent()) {
            return ResponseEntity.status(400).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Message ID " + messageId + " marked as read");
        }
    }

}
