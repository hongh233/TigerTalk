package com.group2.Tiger_Talks.backend.service.implementation.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Optional;

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

    public Optional<String> sendMail(JavaMailSender javaMailSender) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sendFrom);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(messageContent, true);
            javaMailSender.send(mimeMessage);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of("Failed to send email. Please try again.");
        }
    }
}
