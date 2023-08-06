package com.epam.esm.auth.service;


import com.epam.esm.auth.entity.Role;
import com.epam.esm.auth.entity.User;
import com.epam.esm.auth.repository.RoleRepository;
import com.epam.esm.auth.repository.UserRepository;
import com.epam.esm.auth.validator.UserBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserBusinessValidator userBusinessValidator;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserBusinessValidator userBusinessValidator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userBusinessValidator = userBusinessValidator;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Long create(User entity) throws BusinessValidationException {
        userBusinessValidator.validateIfEmailExists(entity.getEmail());
        userBusinessValidator.validateIfNameExists(entity.getName());
        List<Role> roles = entity.getRoles();
        List<Role> existingRoles = roleRepository.findAllBy();
            roles.forEach(t -> {
                Optional<Role> result = existingRoles
                        .stream()
                        .filter(num -> num.getRoleName().equals((t.getRoleName())))
                        .findFirst();
                result.ifPresent(r -> t.setId(r.getId()));
            });
        return userRepository.save(entity).getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public User findById(Long id) {
        return userRepository.findUserById(id);
    }

}
