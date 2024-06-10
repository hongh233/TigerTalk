package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.User.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public List<User> getAllUsers();
    public User getUserById(Integer userId);
    public void deleteUserById(Integer userId);
    public User updateUser(User user);
}
