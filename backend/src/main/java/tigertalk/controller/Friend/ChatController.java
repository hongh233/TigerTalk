package tigertalk.controller.Friend;

import tigertalk.model.Friend.FriendshipMessageDTO;
import tigertalk.service.Friend.FriendshipMessageService;
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
