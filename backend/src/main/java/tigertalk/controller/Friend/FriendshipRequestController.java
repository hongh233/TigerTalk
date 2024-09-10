package tigertalk.controller.Friend;

import org.springframework.beans.factory.annotation.Autowired;
import tigertalk.model.Friend.FriendshipRequestDTO;
import tigertalk.model.Friend.UserProfileDTOFriendship;
import tigertalk.service.Friend.FriendshipRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<FriendshipRequestDTO> getAllFriendRequests(@PathVariable("email") String email) {
        return friendshipRequestService.getAllFriendRequests(email);
    }
    @GetMapping("/doubleSided/{email}")
    public List<FriendshipRequestDTO> getAllFriendRequestsDoubleSided(@PathVariable("email") String email) {
        return friendshipRequestService.getAllFriendRequests_doubleSided(email);
    }

    /**
     * Sends a friendship request from one user to another.
     *
     * @param senderEmail   the email of the user sending the request
     * @param receiverEmail the email of the user receiving the request
     * @return ResponseEntity with a success message or an error message
     */
    @PostMapping("/send")
    public ResponseEntity<FriendshipRequestDTO> sendFriendRequest(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        FriendshipRequestDTO result = friendshipRequestService.sendFriendshipRequest(senderEmail, receiverEmail);
        return ResponseEntity.status(200).body(result);
    }

    /**
     * Accepts a friendship request.
     *
     * @param friendshipRequestId the ID of the friendship request to accept
     * @return ResponseEntity with a success message or an error message
     */
    @PostMapping("/accept")
    public ResponseEntity<UserProfileDTOFriendship> acceptFriendRequest(@RequestParam("id") Integer friendshipRequestId) {
        UserProfileDTOFriendship result = friendshipRequestService.acceptFriendshipRequest(friendshipRequestId);
        return ResponseEntity.status(200).body(result);
    }

    /**
     * Rejects a friendship request.
     *
     * @param friendshipRequestId the ID of the friendship request to reject
     * @return ResponseEntity with a success message or an error message
     */
    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam("id") Integer friendshipRequestId) {
        Optional<String> error = friendshipRequestService.rejectFriendshipRequest(friendshipRequestId);
        if (error.isPresent()) {
            return ResponseEntity.status(404).body(error.get());
        } else {
            return ResponseEntity.status(200).body("Friend request has been rejected.");
        }
    }

    /**
     * Checks if a friendship request exists between two users.
     *
     * @param email1 the email of the user1
     * @param email2 the email of the user2
     * @return true if a friendship request exists, false otherwise
     */
    @GetMapping("/areFriendshipRequestExist/{email1}/{email2}")
    public boolean areFriendshipRequestExist(@PathVariable("email1") String email1, @PathVariable("email2") String email2) {
        return friendshipRequestService.areFriendshipRequestExist(email1, email2);
    }


}
