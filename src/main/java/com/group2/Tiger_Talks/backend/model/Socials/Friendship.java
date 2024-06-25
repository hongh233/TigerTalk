package com.group2.Tiger_Talks.backend.model.Socials;

import com.group2.Tiger_Talks.backend.model.Message;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    private String sendersEmail;
    private String receiversEmail;
    private LocalDate timeSent;         // yyyy/mm/dd/00:00:00

    @OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList;

    public Friendship(String sendersEmail, String receiversEmail, LocalDate timeSent) {
        this.sendersEmail = sendersEmail;
        this.receiversEmail = receiversEmail;
        this.timeSent = timeSent;
        this.messageList = new LinkedList<>();
    }

    public Friendship() {
    }


    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    public String getSendersEmail() {
        return sendersEmail;
    }

    public void setSendersEmail(String userFriendshipSender) {
        this.sendersEmail = userFriendshipSender;
    }

    public String getReceiversEmail() {
        return receiversEmail;
    }

    public void setReceiversEmail(String userFriendshipReceiver) {
        this.receiversEmail = userFriendshipReceiver;
    }

    public LocalDate getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDate createTime) {
        this.timeSent = createTime;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

}
