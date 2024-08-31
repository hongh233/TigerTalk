package tigertalk.model.Authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

import static tigertalk.model.Utils.PASSWORD_TOKEN_EXPIRATION_MINUTES;

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
        LocalDateTime expirationTime = timestamp.plusMinutes(PASSWORD_TOKEN_EXPIRATION_MINUTES);
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
