package com.example.Tiger_Talks.backend.service.impl.User;

import com.example.Tiger_Talks.backend.model.User.UserTemplate;
import com.example.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.example.Tiger_Talks.backend.service.UserTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTemplateServiceImpl implements UserTemplateService {
    @Autowired
    UserTemplateRepository userTemplateRepository;


    @Override
    public List<UserTemplate> findAllUserTempl() {
        return userTemplateRepository.findAll();
    }

    @Override
    public Optional<String> saveUserTemplateToDb(UserTemplate userTemplate) {
        return findUserTemplateByUsername(userTemplate.getUserName())
                .map((_) -> "Cannot create user as the username " + userTemplate.getUserName() + " is not unique")
                .or(() -> {
                    /*
                        Since B00 is used to join a temporary B00 has to be given to the user so,
                        we just use the decremented value of the last ID
                     */
                    List<UserTemplate> all = findAllUserTemplates();
                    if (!all.isEmpty()) {
                        System.out.println("Last id:" + all.getLast().getUserId());
                        userTemplate.setB00(-all.getLast().getUserId());
                    }
                    userTemplateRepository.save(userTemplate);
                    return Optional.empty();
                });
    }

    @Override
    public Optional<UserTemplate> findUserTemplateByUsername(String userName) {
        return userTemplateRepository.findUserTemplateByUserName(userName);
    }

    @Override
    public List<UserTemplate> findAllUserTemplates() {
        return userTemplateRepository.findAll();
    }
}
