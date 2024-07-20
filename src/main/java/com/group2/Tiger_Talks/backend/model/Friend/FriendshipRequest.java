package com.group2.Tiger_Talks.backend.model.Friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
import com.group2.Tiger_Talks.backend.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "friendship_request")
public class FriendshipRequest implements FullyDTOConvertible<FriendshipRequestDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipRequestId;

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    private UserProfile receiver;

    private String senderEmailTemp;
    private String receiverEmailTemp;

    private String senderProfilePictureUrlTemp;
    private String receiverProfilePictureUrlTemp;

    private String senderUserNameTemp;
    private String receiverUserNameTemp;

    private LocalDate createTime = LocalDate.now();              // yyyy/mm/dd/00:00:00


    public FriendshipRequest(UserProfile sender,
                             UserProfile receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderEmailTemp = sender.getEmail();
        this.receiverEmailTemp = receiver.getEmail();
        this.senderProfilePictureUrlTemp = sender.getProfilePictureUrl();
        this.receiverProfilePictureUrlTemp = receiver.getProfilePictureUrl();
        this.senderUserNameTemp = sender.getUserName();
        this.receiverUserNameTemp = receiver.getUserName();
    }

    public FriendshipRequest() {
    }


    public Integer getFriendshipRequestId() {
        return friendshipRequestId;
    }

    public void setFriendshipRequestId(Integer friendshipRequestId) {
        this.friendshipRequestId = friendshipRequestId;
    }

    public UserProfile getSender() {
        return sender;
    }

    public void setSender(UserProfile userFriendshipSender) {
        this.sender = userFriendshipSender;
    }

    public UserProfile getReceiver() {
        return receiver;
    }

    public void setReceiver(UserProfile userFriendshipReceiver) {
        this.receiver = userFriendshipReceiver;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }


    public String getSenderEmailTemp() {
        return senderEmailTemp;
    }

    public void setSenderEmailTemp(String senderEmail) {
        this.senderEmailTemp = senderEmail;
    }

    public String getReceiverEmailTemp() {
        return receiverEmailTemp;
    }

    public void setReceiverEmailTemp(String receiverEmail) {
        this.receiverEmailTemp = receiverEmail;
    }


    public String getSenderProfilePictureUrlTemp() {
        return senderProfilePictureUrlTemp;
    }

    public void setSenderProfilePictureUrlTemp(String senderProfilePictureUrlTemp) {
        this.senderProfilePictureUrlTemp = senderProfilePictureUrlTemp;
    }

    public String getReceiverProfilePictureUrlTemp() {
        return receiverProfilePictureUrlTemp;
    }

    public void setReceiverProfilePictureUrlTemp(String receiverProfilePictureUrlTemp) {
        this.receiverProfilePictureUrlTemp = receiverProfilePictureUrlTemp;
    }

    public String getSenderUserNameTemp() {
        return senderUserNameTemp;
    }

    public void setSenderUserNameTemp(String senderUserNameTemp) {
        this.senderUserNameTemp = senderUserNameTemp;
    }

    public String getReceiverUserNameTemp() {
        return receiverUserNameTemp;
    }

    public void setReceiverUserNameTemp(String receiverUserNameTemp) {
        this.receiverUserNameTemp = receiverUserNameTemp;
    }

    @Override
    public FriendshipRequestDTO toDto() {
        return new FriendshipRequestDTO(
                this.friendshipRequestId,
                this.sender.getEmail(),
                this.sender.getUserName(),
                this.receiver.getEmail(),
                this.receiver.getUserName(),
                this.sender.getProfilePictureUrl(),
                this.receiver.getProfilePictureUrl()
        );
    }

    @Override
    public void updateFromDto(FriendshipRequestDTO dto) {
        this.sender.setEmail(dto.senderEmail());
        this.sender.setUserName(dto.senderName());
        this.receiver.setEmail(dto.receiverEmail());
        this.receiver.setUserName(dto.receiverName());
        this.sender.setProfilePictureUrl(dto.senderProfilePictureUrl());
        this.receiver.setProfilePictureUrl(dto.receiverProfilePictureUrl());
    }
}
