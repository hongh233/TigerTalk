package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PasswordTokenImpl {
    static final byte EXPIRATION_MINUTES = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String token;

    private LocalDateTime timestamp;

    private PasswordTokenImpl(String token, LocalDateTime timestamp, String email) {
        this.token = token;
        this.timestamp = timestamp;
        this.email = email;
    }

    public PasswordTokenImpl() {
    }

    public static PasswordTokenImpl createPasswordResetToken(String email) {
        return new PasswordTokenImpl(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                email
        );
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public boolean isTokenExpired() {
        LocalDateTime expirationTime = timestamp.plusMinutes(EXPIRATION_MINUTES);
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
