package com.group2.Tiger_Talks.backend.model.Socials;

import com.group2.Tiger_Talks.backend.model.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipRequestId;

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    private UserProfile receiver;


    private LocalDate createTime;              // yyyy/mm/dd/00:00:00


    public FriendshipRequest(UserProfile sender,
                             UserProfile receiver,
                             LocalDate createTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.createTime = createTime;
    }
    public FriendshipRequest() {}


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

}
