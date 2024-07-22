package com.group2.Tiger_Talks.backend.controller;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public FriendshipMessage sendMessage(FriendshipMessage message) throws Exception {
        return message;
    }
}
