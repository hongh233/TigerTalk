package com.group2.Tiger_Talks.backend.model.Friend;

import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FriendshipMessage implements FullyDTOConvertible<FriendshipMessageDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    private LocalDateTime createTime = LocalDateTime.now();
    private String messageContent;

    @ManyToOne
    @JoinColumn(name = "friendship_id", nullable = false)
    private Friendship friendship;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserProfile receiver;

    public FriendshipMessage(String messageContent) {
        this.messageContent = messageContent;
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


    public String getMessageSenderEmail() {
        return sender.getEmail();
    }

    public String getMessageReceiverEmail() {
        return receiver.getEmail();
    }

    public UserProfile getSender() {
        return sender;
    }

    public void setSender(UserProfile sender) {
        this.sender = sender;
    }

    public UserProfile getReceiver() {
        return receiver;
    }

    public void setReceiver(UserProfile receiver) {
        this.receiver = receiver;
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
