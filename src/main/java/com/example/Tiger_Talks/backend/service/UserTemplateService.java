package com.example.Tiger_Talks.backend.service;

import com.example.Tiger_Talks.backend.model.User.UserTemplate;

import java.util.List;
import java.util.Optional;

public interface UserTemplateService {
    List<UserTemplate> findAllUserTempl();

    Optional<String> saveUserTemplateToDb(UserTemplate userTemplate);

    Optional<UserTemplate> findUserTemplateByUsername(String userName);

    List<UserTemplate> findAllUserTemplates();
}
