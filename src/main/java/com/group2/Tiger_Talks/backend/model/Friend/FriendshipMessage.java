package com.group2.Tiger_Talks.backend.model.Friend;

import jakarta.persistence.*;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDateTime;

@Entity
public class FriendshipMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    private LocalDateTime createTime = LocalDateTime.now();
    private String messageContent;

    private boolean friendshipSenderIsMessageSender;

    @ManyToOne
    @JoinColumn(name = "friendship_id", nullable = false)
    private Friendship friendship;

    public FriendshipMessage(String messageContent, boolean friendshipSenderIsMessageSender) {
        this.messageContent = messageContent;
        this.friendshipSenderIsMessageSender = friendshipSenderIsMessageSender;
    }

    public FriendshipMessage() {
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public boolean isFriendshipSenderIsMessageSender() {
        return friendshipSenderIsMessageSender;
    }

    public void setFriendshipSenderIsMessageSender(boolean friendshipSenderIsMessageSender) {
        this.friendshipSenderIsMessageSender = friendshipSenderIsMessageSender;
    }




}
