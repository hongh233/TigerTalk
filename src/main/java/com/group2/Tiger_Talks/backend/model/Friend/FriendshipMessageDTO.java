package com.group2.Tiger_Talks.backend.model.Friend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class FriendshipMessageDTO {

    private int messageId;
    private LocalDateTime createTime;
    private String messageContent;

    private String messageSender;   // email
    private String messageReceiver;  // email


    // add as much things as you want

    public FriendshipMessageDTO(FriendshipMessage friendshipMessage) {
        this.messageId = friendshipMessage.getMessageId();
        this.createTime = friendshipMessage.getCreateTime();
        this.messageContent = friendshipMessage.getMessageContent();
        this.messageSender = String.valueOf(friendshipMessage.isFriendshipSenderIsMessageSender() ?
                friendshipMessage.getFriendship().getSender() : friendshipMessage.getFriendship().getReceiver());
        this.messageReceiver = String.valueOf(!friendshipMessage.isFriendshipSenderIsMessageSender() ?
                friendshipMessage.getFriendship().getSender() : friendshipMessage.getFriendship().getReceiver());
    }


    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageReceiver() {
        return messageReceiver;
    }

    public void setMessageReceiver(String messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

}
