package com.group2.Tiger_Talks.backend.model.Socials;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "friendship_request")
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipRequestId;

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    private UserTemplate sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    private UserTemplate receiver;


    private LocalDate createTime;              // yyyy/mm/dd/00:00:00


    public FriendshipRequest(UserTemplate sender,
                             UserTemplate receiver,
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

    public UserTemplate getSender() {
        return sender;
    }

    public void setSender(UserTemplate userFriendshipSender) {
        this.sender = userFriendshipSender;
    }

    public UserTemplate getReceiver() {
        return receiver;
    }

    public void setReceiver(UserTemplate userFriendshipReceiver) {
        this.receiver = userFriendshipReceiver;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

}
