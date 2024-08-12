package tigertalk.model.Authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

import static tigertalk.model.Utils.PASSWORD_TOKEN_EXPIRATION_MINUTES;

@Entity
public class PasswordTokenImpl {

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
        LocalDateTime expirationTime = timestamp.plusMinutes(PASSWORD_TOKEN_EXPIRATION_MINUTES);
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
