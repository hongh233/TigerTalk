package com.group2.Tiger_Talks.backend.repsitory.User;

import com.group2.Tiger_Talks.backend.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
