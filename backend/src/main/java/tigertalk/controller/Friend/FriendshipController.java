package tigertalk.controller.Friend;

import tigertalk.model.Friend.UserProfileDTOFriendship;
import tigertalk.service.Friend.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user friendships.
 */
@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    /**
     * Retrieves all friends of a user by their email, returning UserProfileFriendshipDTO.
     *
     * @param email the email of the user
     * @return List of UserProfileFriendshipDTO representing all friends
     */
    @GetMapping("/DTO/{email}")
    public List<UserProfileDTOFriendship> getAllFriendsDTO(@PathVariable("email") String email) {
        return friendshipService.getAllFriendsDTO(email);
    }

    /**
     * Deletes a friendship relationship between two users by their emails.
     *
     * @param senderEmail   the email of the sender
     * @param receiverEmail the email of the receiver
     * @return ResponseEntity indicating success or failure of the operation
     */
    @DeleteMapping("/deleteByEmail/{senderEmail}/{receiverEmail}")
    public ResponseEntity<String> deleteFriendshipByEmail(@PathVariable("senderEmail") String senderEmail,
                                                          @PathVariable("receiverEmail") String receiverEmail) {
        return friendshipService.deleteFriendshipByEmail(receiverEmail, senderEmail)
                .map(err -> friendshipService.deleteFriendshipByEmail(senderEmail, receiverEmail)
                        .map(ResponseEntity.status(404)::body)
                        .orElseGet(() -> ResponseEntity.ok("Friendship successfully deleted."))
                ).orElseGet(() -> ResponseEntity.ok("Friendship successfully deleted."));
    }

    /**
     * Checks if two users are friends based on their emails.
     *
     * @param email1 email of the first user
     * @param email2 email of the second user
     * @return true if they are friends, false otherwise
     */
    @GetMapping("/areFriends/{email1}/{email2}")
    public boolean areFriends(@PathVariable("email1") String email1, @PathVariable("email2") String email2) {
        return friendshipService.areFriends(email1, email2);
    }
}
