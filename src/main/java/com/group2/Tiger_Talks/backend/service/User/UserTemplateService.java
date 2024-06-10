package com.group2.Tiger_Talks.backend.service.User;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;

import java.util.List;
import java.util.Optional;

public interface UserTemplateService {

    public UserTemplate createUserTemplate(UserTemplate userTemplate);


    public List<UserTemplate> getAllUserTemplates();

    public UserTemplate getUserTemplateById(Integer userId);

    public void deleteUserTemplateById(Integer userId);
    public UserTemplate updateUserTemplate(UserTemplate userTemplate);


}
