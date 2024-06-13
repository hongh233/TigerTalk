package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.User.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    void deleteUserByEmail(String email);

    User updateUser(User user);
}
