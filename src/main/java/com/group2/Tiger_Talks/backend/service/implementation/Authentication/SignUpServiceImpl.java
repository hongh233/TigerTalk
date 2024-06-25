package com.group2.Tiger_Talks.backend.service.implementation.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;
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
    private static final Pattern PASSWORD_NORM_LENGTH =
            Pattern.compile("^.{8,}$");
    private static final Pattern PASSWORD_NORM_UPPERCASE =
            Pattern.compile("^(?=.*[A-Z]).+$");
    private static final Pattern PASSWORD_NORM_LOWERCASE =
            Pattern.compile("^(?=.*[a-z]).+$");
    private static final Pattern PASSWORD_NORM_NUMBER =
            Pattern.compile("^(?=.*[0-9]).+$");
    private static final Pattern PASSWORD_NORM_SPECIAL_CHARACTER =
            Pattern.compile("^(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~]).+$");

    private static final Pattern EMAIL_NORM =
            Pattern.compile(
                    "^[A-Za-z0-9]+" + "@dal\\.ca$");
    @Autowired
    private UserTemplateRepository userTemplateRepository;
    @Autowired
    private UserTemplateService userTemplateService;

    @Override
    public Optional<String> signUpUserTemplate(UserTemplate userTemplate) {
        if (!EMAIL_NORM.matcher(userTemplate.getEmail()).matches()) {
            return Optional.of("Invalid email address. Please use dal email address!");
        }
        if (userTemplateRepository.findUserTemplateByEmail(userTemplate.getEmail()).isPresent()) {
            return Optional.of("Email has already existed!");
        }
        if (!PASSWORD_NORM_LENGTH.matcher(userTemplate.getPassword()).matches()) {
            return Optional.of("Password must have a minimum length of 8 characters.");
        }
        if (!PASSWORD_NORM_UPPERCASE.matcher(userTemplate.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 uppercase character.");
        }
        if (!PASSWORD_NORM_LOWERCASE.matcher(userTemplate.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 lowercase character.");
        }
        if (!PASSWORD_NORM_NUMBER.matcher(userTemplate.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 number.");
        }
        if (!PASSWORD_NORM_SPECIAL_CHARACTER.matcher(userTemplate.getPassword()).matches()) {
            return Optional.of("Password must have at least 1 special character.");
        }
        userTemplateRepository.save(userTemplate);
        return Optional.empty();
    }


    public List<UserTemplate> getAllUserTemplates() {
        return userTemplateService.getAllUserTemplates();
    }


}
