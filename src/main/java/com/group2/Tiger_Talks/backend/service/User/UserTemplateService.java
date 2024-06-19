package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.UserTemplate;

import java.util.List;
import java.util.Optional;

public interface UserTemplateService {

    List<UserTemplate> getAllUserTemplates();

    Optional<UserTemplate> getUserTemplateByEmail(String email);

    void deleteUserTemplateByEmail(String email);

    UserTemplate updateUserTemplate(UserTemplate userTemplate);


}
