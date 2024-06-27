package com.group2.Tiger_Talks.backend.model.Socials;

import com.group2.Tiger_Talks.backend.model.Message;
import com.group2.Tiger_Talks.backend.model.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    @ManyToOne
    @JoinColumn(name = "sender_email", referencedColumnName = "email")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email", referencedColumnName = "email")
    private UserProfile receiver;

    private LocalDate createTime;         // yyyy/mm/dd/00:00:00

    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList = new LinkedList<>();

    public Friendship(UserProfile sender, UserProfile receiver, LocalDate createTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.createTime = createTime;
    }

    public Friendship() {
    }


    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

}
