package com.group2.Tiger_Talks.backend.model.Group;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class GroupMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    private LocalDateTime messageCreateTime = LocalDateTime.now();

    private String content;

    public GroupMessage(String content) {
        this.content = content;
    }

    public GroupMessage() {
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LocalDateTime getMessageCreateTime() {
        return messageCreateTime;
    }

    public void setMessageCreateTime(LocalDateTime messageCreateTime) {
        this.messageCreateTime = messageCreateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}