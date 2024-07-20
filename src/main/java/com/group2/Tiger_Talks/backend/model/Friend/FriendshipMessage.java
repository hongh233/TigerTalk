package com.group2.Tiger_Talks.backend.model.Friend;

import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FriendshipMessage implements FullyDTOConvertible<FriendshipMessageDTO> {

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

    public String getMessageSenderEmail() {
        return (isFriendshipSenderIsMessageSender() ? getFriendship().getSender() : getFriendship().getReceiver()).getEmail();
    }

    public String getMessageReceiverEmail() {
        return (!isFriendshipSenderIsMessageSender() ? getFriendship().getSender() : getFriendship().getReceiver()).getEmail();
    }

    @Override
    public FriendshipMessageDTO toDto() {
        return new FriendshipMessageDTO(
                this.messageId,
                this.createTime,
                this.messageContent,
                this.getMessageSenderEmail(),
                this.getMessageReceiverEmail()
        );
    }

    @Override
    public void updateFromDto(FriendshipMessageDTO friendshipMessageDTO) {
        this.setMessageContent(friendshipMessageDTO.messageContent());
    }
}
