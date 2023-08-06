package com.epam.esm.validator;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.UserRole;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserValidator {

    private MessageSource messageSource;
    private UserService userService;

    @Autowired
    public UserValidator(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    public void validateUserDTO(UserDTO userDTO, Locale locale) {
        if (userDTO.getUsername() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.name", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (userDTO.getPassword() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.password", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (userDTO.getRoles() != null) {
            userDTO.getRoles().forEach(role -> {
                if (UserRole.from(role.getName()) == null) {
                    String errorMessage = messageSource.getMessage("label.not.found.role", null, locale);
                    throw new IllegalArgumentException(errorMessage);
                }
            });
        }
    }

}
