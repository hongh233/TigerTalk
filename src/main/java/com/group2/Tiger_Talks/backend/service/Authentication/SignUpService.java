package com.group2.Tiger_Talks.backend.service.Authentication;

import com.group2.Tiger_Talks.backend.model.User.UserTemplate;

import java.util.List;
import java.util.Optional;

public interface SignUpService {
    /**
     * Try to save a userTemplate to a database, ensure unique userName,
     * valid and unique BannerID, valid-form and unique email and valid-form password
     * @param userTemplate The user template to save
     * @return String as error message,
     *         otherwise returns an empty optional
     */
    Optional<String> signUpUserTemplate(UserTemplate userTemplate);

    List<UserTemplate> getAllUserTemplates();
}
