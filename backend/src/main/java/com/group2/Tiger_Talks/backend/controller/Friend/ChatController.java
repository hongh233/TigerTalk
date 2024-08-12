package com.group2.Tiger_Talks.backend.controller.Friend;

import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessage;
import com.group2.Tiger_Talks.backend.model.Friend.FriendshipMessageDTO;
import com.group2.Tiger_Talks.backend.service.Friend.FriendshipMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// This class deal with WebSocket message
@Controller
public class ChatController {

    @Autowired
    private FriendshipMessageService friendshipMessageService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public FriendshipMessageDTO sendMessage(Integer messageId) {
        return friendshipMessageService.getFriendshipMessageDTOById(messageId);
    }
}
