package com.epam.esm.auth.validator;

import com.epam.esm.auth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
public class UserValidator {

    private MessageSource messageSource;

    @Autowired
    public UserValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void validateUserDTO(UserDTO userDTO, Locale locale) {
        if (userDTO.getUsername() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.name", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (userDTO.getPassword() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.password", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (userDTO.getEmail() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.email", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
