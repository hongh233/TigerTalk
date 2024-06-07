package com.group2.Tiger_Talks.backend.service.impl;

import com.group2.Tiger_Talks.backend.service.UserTemplateService;
import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    UserTemplateService userTemplateService;

    @Override
    public Optional<String> trySaveUser(UserTemplate userTemplate) {
        return userTemplateService.saveUserTemplateToDb(userTemplate);
    }

    @Override
    public List<UserTemplate> getAllUserTemplates() {
        return userTemplateService.findAllUserTempl();
    }
}
