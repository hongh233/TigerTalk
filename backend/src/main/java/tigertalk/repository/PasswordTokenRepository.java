package tigertalk.repository;

import tigertalk.model.Authentication.PasswordTokenImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordTokenImpl, Long> {
    Optional<PasswordTokenImpl> findPasswordTokenByToken(String token);
}
