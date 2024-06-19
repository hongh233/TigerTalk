package com.group2.Tiger_Talks.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friendship_request")
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipRequestId;
    private String userFriendshipSender;   // email
    private String userFriendshipReceiver;  // email
    private String createTime;              // yyyy/mm/dd/00:00:00
    private String status;          // approved/ refused / pending


    public FriendshipRequest(String userFriendshipSender,
                             String userFriendshipReceiver,
                             String createTime) {
        this.userFriendshipSender = userFriendshipSender;
        this.userFriendshipReceiver = userFriendshipReceiver;
        this.createTime = createTime;
        this.status = "pending";
    }
    public FriendshipRequest(){}


    public Integer getFriendshipRequestId() {
        return friendshipRequestId;
    }

    public void setFriendshipRequestId(Integer friendshipRequestId) {
        this.friendshipRequestId = friendshipRequestId;
    }

    public String getUserFriendshipSender() {
        return userFriendshipSender;
    }

    public void setUserFriendshipSender(String userFriendshipSender) {
        this.userFriendshipSender = userFriendshipSender;
    }

    public String getUserFriendshipReceiver() {
        return userFriendshipReceiver;
    }

    public void setUserFriendshipReceiver(String userFriendshipReceiver) {
        this.userFriendshipReceiver = userFriendshipReceiver;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
