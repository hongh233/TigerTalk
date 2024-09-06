package tigertalk.model.Authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String token;

    private LocalDateTime timestamp;

    public PasswordToken() {
    }

    public static PasswordToken createPasswordResetToken(String email) {
        PasswordToken passwordToken = new PasswordToken();
        passwordToken.token = UUID.randomUUID().toString();
        passwordToken.timestamp = LocalDateTime.now();
        passwordToken.email = email;
        return passwordToken;
    }

    public boolean isTokenExpired() {
        LocalDateTime expirationTime = timestamp.plusMinutes(10); // PASSWORD_TOKEN_EXPIRATION_MINUTES: 10
        return LocalDateTime.now().isAfter(expirationTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
