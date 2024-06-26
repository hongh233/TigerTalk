package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.service.Socials.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Friendship>> getAllFriends(@RequestParam String email) {
        return ResponseEntity.ok(friendshipService.getAllFriends(email));
    }

    @DeleteMapping("/deleteByEmail")
    public ResponseEntity<String> deleteFriendshipByEmail(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        return friendshipService.deleteFriendshipByEmail(senderEmail, receiverEmail)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friendship is successfully deleted."));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteFriendshipById(@RequestParam Integer friendshipId) {
        return friendshipService.deleteFriendshipById(friendshipId)
                .map(err -> ResponseEntity.status(404).body(err))
                .orElseGet(() -> ResponseEntity.ok("Friendship is successfully deleted."));
    }
}