package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.repsitory.User.UserTemplateRepository;
import com.group2.Tiger_Talks.backend.service.User.UserTemplateService;
import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
import com.group2.Tiger_Talks.backend.service.Authentication.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserTemplateRepository userTemplateRepository;
    @Autowired
    private UserTemplateService userTemplateService;

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
            return Optional.of("Email has already existed!");
        }

        if (userTemplateRepository.findUserTemplateByBannerID(userTemplate.getBannerID()).isPresent()) {
            return Optional.of("BannerID has already existed!");
        }

        userTemplate.setStatus("pending");
        userTemplate.setUserLevel("user");
        userTemplateService.createUserTemplate(userTemplate);
        return Optional.empty();
    }


    public List<UserTemplate> getAllUserTemplates() {
        return userTemplateService.getAllUserTemplates();
    }




}
