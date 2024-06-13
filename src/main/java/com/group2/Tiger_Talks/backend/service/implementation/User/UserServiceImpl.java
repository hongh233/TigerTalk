package com.group2.Tiger_Talks.backend.service.implementation.User;

import com.group2.Tiger_Talks.backend.model.User.User;
import com.group2.Tiger_Talks.backend.repsitory.User.UserRepository;
import com.group2.Tiger_Talks.backend.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteById(email);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
