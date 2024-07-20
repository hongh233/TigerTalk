package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.service.Friend.FriendshipMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendships")
public class FriendshipMessageController {

    @Autowired
    private FriendshipMessageService friendshipMessageService;


}
