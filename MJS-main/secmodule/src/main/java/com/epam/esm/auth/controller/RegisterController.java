package com.epam.esm.auth.controller;


import com.epam.esm.auth.converter.UserConverter;
import com.epam.esm.auth.dto.UserDTO;
import com.epam.esm.auth.entity.User;
import com.epam.esm.auth.response.InfoResponse;
import com.epam.esm.auth.service.BusinessValidationException;
import com.epam.esm.auth.service.UserService;
import com.epam.esm.auth.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController
@RequestMapping(value = "/auth")
public class RegisterController {

    private UserService userService;
    private MessageSource messageSource;
    private UserValidator userValidator;

    @Autowired
    public RegisterController(UserService userService, MessageSource messageSource, UserValidator userValidator) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.userValidator = userValidator;
    }

    @PostMapping("/signup")
    public InfoResponse signup(@RequestBody UserDTO userDTO, Locale locale) throws BusinessValidationException {
        userValidator.validateUserDTO(userDTO, locale);
        User user = UserConverter.convertToEntity(userDTO);
        Long userCreatedId = userService.create(user);
        User newUser = userService.findById(userCreatedId);
        String message = messageSource.getMessage("label.user.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ": " + newUser.getUsername(),
                HttpStatus.CREATED.toString());
    }
}

