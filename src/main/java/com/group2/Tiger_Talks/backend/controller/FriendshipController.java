package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Socials.FriendshipDTO;
import com.group2.Tiger_Talks.backend.model.UserProfileFriendshipDTO;
import com.group2.Tiger_Talks.backend.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/{email}")
    public ResponseEntity<List<FriendshipDTO>> getAllFriends(@PathVariable("email") String email) {
        return ResponseEntity.ok(friendshipService.getAllFriends(email));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/DTO/{email}")
    public List<UserProfileFriendshipDTO> getAllFriendsDTO(@PathVariable("email") String email) {
        return friendshipService.getAllFriendsDTO(email);
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @DeleteMapping("/deleteByEmail/{senderEmail}/{receiverEmail}")
    public ResponseEntity<String> deleteFriendshipByEmail(@PathVariable("senderEmail") String senderEmail, @PathVariable("receiverEmail") String receiverEmail) {
        return friendshipService.deleteFriendshipByEmail(receiverEmail, senderEmail)
                .map(x -> friendshipService.deleteFriendshipByEmail(senderEmail, receiverEmail)
                        .map(err -> ResponseEntity.status(404).body(err))
                        .orElseGet(() -> ResponseEntity.ok("Friendship is successfully deleted."))
                ).orElseGet(() -> ResponseEntity.ok("Friendship is successfully deleted."));
    }

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteFriendshipById(@RequestParam Integer friendshipId) {
        return friendshipService.deleteFriendshipById(friendshipId)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friendship is successfully deleted."));
    }
}