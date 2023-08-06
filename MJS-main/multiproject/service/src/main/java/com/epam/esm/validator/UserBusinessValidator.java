package com.epam.esm.validator;

import com.epam.esm.repository.UserRepository;
import com.epam.esm.exception.BusinessValidationException;
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

    public void validateIfExists(Long userId) throws BusinessValidationException {
        if (userRepository.findUserById(userId) != null) {
            String errorMessage = messageSource.getMessage("label.not.found.user", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExists(String name) throws BusinessValidationException {
        if (userRepository.findByName(name) != null) {
            String errorMessage = messageSource.getMessage("label.already.exists.user", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
