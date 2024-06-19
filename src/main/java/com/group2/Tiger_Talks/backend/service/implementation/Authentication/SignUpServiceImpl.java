package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.UserTemplate;
import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import com.group2.Tiger_Talks.backend.service.User.UserTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SignUpServiceImpl implements SignUpService {
    private static final Pattern PASSWORD_NORM =
            Pattern.compile(
                    "^(?=.*[a-z])" +
                            "(?=.*[A-Z])" +
                            "(?=.*[0-9])" +
                            "(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~])" +
                            "[a-zA-Z0-9!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]{8,}$");
    private static final Pattern EMAIL_NORM =
            Pattern.compile(
                    "^[A-Za-z0-9]+" +
                            "@" +
                            "[A-Za-z0-9.]*" +
                            "dal\\.ca$");
    @Autowired
    private UserTemplateRepository userTemplateRepository;
    @Autowired
    private UserTemplateService userTemplateService;

    @Override
    public Optional<String> signUpUserTemplate(UserTemplate userTemplate) {
        if (!EMAIL_NORM.matcher(userTemplate.getEmail()).matches()) {
            return Optional.of("Invalid email address. Please user dal email address!");
        }

        if (!PASSWORD_NORM.matcher(userTemplate.getPassword()).matches()) {
            return Optional.of("Password must have a minimum length of 8 characters, " +
                    "at least 1 uppercase character, 1 lowercase character, 1 number and 1 special character.");
        }

        if (userTemplateRepository.findUserTemplateByEmail(userTemplate.getEmail()).isPresent()) {
            return Optional.of("Email already existed!");
        }

        if(userTemplateRepository.findUserTemplateByUserName(userTemplate.getUserName()).isPresent()) {
            return Optional.of("Username already existed!");
        }

        userTemplate.setStatus(UserTemplate.PENDING);
        userTemplate.setUserLevel(UserTemplate.USER);



        userTemplateRepository.save(userTemplate);
        return Optional.empty();
    }


    public List<UserTemplate> getAllUserTemplates() {
        return userTemplateService.getAllUserTemplates();
    }


}
