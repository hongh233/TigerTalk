package com.group2.Tiger_Talks.backend.model;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    private String userFriendshipSender;   // email
    private String userFriendshipReceiver;  // email
    private String createTime;              // yyyy/mm/dd/00:00:00

    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList;

    public Friendship(String userFriendshipSender, String userFriendshipReceiver, String createTime) {
        this.userFriendshipSender = userFriendshipSender;
        this.userFriendshipReceiver = userFriendshipReceiver;
        this.createTime = createTime;
        this.messageList = new LinkedList<>();
    }
    public Friendship() {}


    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

}
