package com.group2.Tiger_Talks.backend.repsitory;

import com.group2.Tiger_Talks.backend.service.implementation.Authentication.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findPasswordTokenByToken(String token);
}
