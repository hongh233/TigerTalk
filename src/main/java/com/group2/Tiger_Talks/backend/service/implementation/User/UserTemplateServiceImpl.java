package com.group2.Tiger_Talks.backend.service.implementation.User;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.User.UserTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTemplateServiceImpl implements UserTemplateService {
    @Autowired
    private UserTemplateRepository userTemplateRepository;

    @Override
    public UserTemplate createUserTemplate(UserTemplate userTemplate) {
        return userTemplateRepository.save(userTemplate);
    }

    @Override
    public List<UserTemplate> getAllUserTemplates() {
        return userTemplateRepository.findAll();
    }

    @Override

    public UserTemplate getUserTemplateById(Integer userId) {
        return userTemplateRepository.findById(userId).orElse(null);
    }

    @Override

    public void deleteUserTemplateById(Integer userId) {
        userTemplateRepository.deleteById(userId);
    }

    @Override
    public UserTemplate updateUserTemplate(UserTemplate userTemplate) {
        return userTemplateRepository.save(userTemplate);
    }

}
