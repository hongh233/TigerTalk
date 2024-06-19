package com.group2.Tiger_Talks.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "message")
public class Message {

    @Id
    private Integer messageId;
    private String createTime;     // yyyy/mm/dd/00:00:00
    private String messageContent;


}
