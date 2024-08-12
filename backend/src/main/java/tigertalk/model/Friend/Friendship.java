//package com.group2.Tiger_Talks.backend.model.Friend;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.group2.Tiger_Talks.backend.model.FullyDTOConvertible;
//import com.group2.Tiger_Talks.backend.model.User.UserProfile;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Optional;
//
//@Entity
//public class Friendship {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer friendshipId;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_email", referencedColumnName = "email")
//    @JsonBackReference(value = "sender-friendship")
//    private UserProfile sender;
//
//    @ManyToOne
//    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
//    @JsonBackReference(value = "receiver-friendship")
//    private UserProfile receiver;
//
//    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FriendshipMessage> friendshipMessageList = new LinkedList<>();
//
//    private String senderEmailTemp;
//    private String receiverEmailTemp;
//
//    private String senderProfilePictureUrlTemp;
//    private String receiverProfilePictureUrlTemp;
//
//    private String senderUserNameTemp;
//    private String receiverUserNameTemp;
//
//    private LocalDate createTime = LocalDate.now();         // yyyy/mm/dd/00:00:00
//
//    public Friendship(UserProfile sender,
//                      UserProfile receiver) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.senderEmailTemp = sender.getEmail();
//        this.receiverEmailTemp = receiver.getEmail();
//        this.senderProfilePictureUrlTemp = sender.getProfilePictureUrl();
//        this.receiverProfilePictureUrlTemp = receiver.getProfilePictureUrl();
//        this.senderUserNameTemp = sender.getUserName();
//        this.receiverUserNameTemp = receiver.getUserName();
//    }
//
//    public Friendship() {
//    }
//
//
//    public Integer getFriendshipId() {
//        return friendshipId;
//    }
//
//    public void setFriendshipId(Integer friendshipId) {
//        this.friendshipId = friendshipId;
//    }
//
//    public UserProfile getSender() {
//        return sender;
//    }
//
//    public void setSender(UserProfile userFriendshipSender) {
//        this.sender = userFriendshipSender;
//    }
//
//    public UserProfile getReceiver() {
//        return receiver;
//    }
//
//    public void setReceiver(UserProfile userFriendshipReceiver) {
//        this.receiver = userFriendshipReceiver;
//    }
//
//    public LocalDate getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(LocalDate createTime) {
//        this.createTime = createTime;
//    }
//
//    public List<FriendshipMessage> getMessageList() {
//        return friendshipMessageList;
//    }
//
//    public void setMessageList(List<FriendshipMessage> friendshipMessageList) {
//        this.friendshipMessageList = friendshipMessageList;
//    }
//
//    public String getSenderEmailTemp() {
//        return senderEmailTemp;
//    }
//
//    public void setSenderEmailTemp(String senderEmail) {
//        this.senderEmailTemp = senderEmail;
//    }
//
//    public String getReceiverEmailTemp() {
//        return receiverEmailTemp;
//    }
//
//    public void setReceiverEmailTemp(String receiverEmail) {
//        this.receiverEmailTemp = receiverEmail;
//    }
//
//
//    public String getSenderProfilePictureUrlTemp() {
//        return senderProfilePictureUrlTemp;
//    }
//
//    public void setSenderProfilePictureUrlTemp(String senderProfilePictureUrlTemp) {
//        this.senderProfilePictureUrlTemp = senderProfilePictureUrlTemp;
//    }
//
//    public String getReceiverProfilePictureUrlTemp() {
//        return receiverProfilePictureUrlTemp;
//    }
//
//    public void setReceiverProfilePictureUrlTemp(String receiverProfilePictureUrlTemp) {
//        this.receiverProfilePictureUrlTemp = receiverProfilePictureUrlTemp;
//    }
//
//    public String getSenderUserNameTemp() {
//        return senderUserNameTemp;
//    }
//
//    public void setSenderUserNameTemp(String senderUserNameTemp) {
//        this.senderUserNameTemp = senderUserNameTemp;
//    }
//
//    public String getReceiverUserNameTemp() {
//        return receiverUserNameTemp;
//    }
//
//    public void setReceiverUserNameTemp(String receiverUserNameTemp) {
//        this.receiverUserNameTemp = receiverUserNameTemp;
//    }
//
//}

package tigertalk.model.Friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import tigertalk.model.User.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    @JsonBackReference(value = "sender-friendship")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    @JsonBackReference(value = "receiver-friendship")
    private UserProfile receiver;

    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipMessage> messages = new LinkedList<>();

    private LocalDate createTime = LocalDate.now();

    public Friendship(UserProfile sender, UserProfile receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Friendship() {
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }
    public Integer getFriendshipId() {
        return friendshipId;
    }

    public UserProfile getSender() {
        return sender;
    }

    public UserProfile getReceiver() {
        return receiver;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public List<FriendshipMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FriendshipMessage> messages) {
        this.messages = messages;
    }

}

