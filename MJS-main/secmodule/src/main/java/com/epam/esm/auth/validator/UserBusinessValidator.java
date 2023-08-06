package com.epam.esm.auth.validator;

import com.epam.esm.auth.repository.UserRepository;
import com.epam.esm.auth.service.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessValidator {
    private UserRepository userRepository;
    private MessageSource messageSource;

    @Autowired
    public UserBusinessValidator(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    public void validateIfEmailExists(String email) throws BusinessValidationException {
        if (userRepository.findUserByEmail(email) != null) {
            String errorMessage = messageSource.getMessage("label.already.exists.email", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfNameExists(String name) throws BusinessValidationException {
        if (userRepository.findByName(name) != null) {
            String errorMessage = messageSource.getMessage("label.already.exists.user", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
