package com.group2.Tiger_Talks.backend.service.implementation.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailClient {
    final private String sendFrom;
    final private String[] sendTo;
    final private String subject;
    final private String messageContent;

    public MailClient(String[] sendTo, String sendFrom, String subject, String messageContent) {
        this.sendFrom = sendFrom;
        this.sendTo = sendTo;
        this.subject = subject;
        this.messageContent = messageContent;
    }

    public void sendMail(JavaMailSender javaMailSender) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(messageContent);
        javaMailSender.send(simpleMailMessage);
    }
}
