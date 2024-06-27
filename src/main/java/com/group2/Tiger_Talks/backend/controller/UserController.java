package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import com.group2.Tiger_Talks.backend.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserProfileService userProfileService;

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserProfile userProfile) {
        Optional<String> err = userProfileService.updateUserProfile(userProfile);
        if (err.isPresent()) {
            return ResponseEntity.status(400).body(err.get());
        } else {
            return ResponseEntity.ok(userProfile);
        }
    }
}
