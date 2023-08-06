package com.epam.esm.controller;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.response.InfoResponse;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.DataGenerator;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;
    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, DataGenerator dataGeneratorService, MessageSource messageSource, UserValidator userValidator) {
        this.userService = userService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
        this.userValidator = userValidator;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('role_admin')")
    public HttpEntity<List<UserDTO>> getUsers(Pageable pageable, Locale locale) throws ServiceException, BusinessValidationException {
        PaginationValidator.validatePagination(pageable, locale);
        List<UserDTO> usersDTO = userService.getUsers(pageable)
                .stream()
                .map(UserConverter::convertToDto)
                .collect(Collectors.toList());
        addTagsHATEOAS(usersDTO, pageable, locale);
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('role_admin')")
    public HttpEntity<UserDTO> getUser(@PathVariable Long userId, Pageable pageable, Locale locale) throws ServiceException, BusinessValidationException {
        UserDTO userDTO = UserConverter.convertToDto(userService.findById(userId));
        addTagHATEOAS(userDTO, pageable, locale);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role_admin')")
    public InfoResponse addUser(@RequestBody UserDTO userDTO, Locale locale) throws ServiceException, BusinessValidationException {
        userValidator.validateUserDTO(userDTO, locale);
        User user = UserConverter.convertToEntity(userDTO);
        Long userCreatedId = userService.create(user);
        User newUser = userService.findById(userCreatedId);
        String message = messageSource.getMessage("label.user.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ": " + newUser.getName(),
                HttpStatus.CREATED.toString());
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('role_admin')")
    public InfoResponse generateUsers(@RequestParam(value = "count") int count, Locale locale) throws ServiceException {
        dataGeneratorService.generateUsers(count);
        int numberOfRows = userService.getAllUsers().size();
        String message = messageSource.getMessage("label.users.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addTagsHATEOAS(List<UserDTO> usersDTO, Pageable pageable, Locale locale) throws ServiceException, BusinessValidationException {
        for (UserDTO userDTO : usersDTO) {
            userDTO.add(linkTo(methodOn(UserController.class).getUser(userDTO.getId(), pageable, locale)).withSelfRel());
        }
    }

    private void addTagHATEOAS(UserDTO userDTO, Pageable pageable, Locale locale) throws ServiceException, BusinessValidationException {
        userDTO.add(linkTo(methodOn(UserController.class).getUser(userDTO.getId(), pageable, locale)).withSelfRel());
        userDTO.add(linkTo(methodOn(UserController.class).getUsers(pageable, locale)).withRel("users"));
    }
}
