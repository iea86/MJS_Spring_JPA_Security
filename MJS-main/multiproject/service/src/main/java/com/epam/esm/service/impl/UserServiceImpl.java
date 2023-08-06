package com.epam.esm.service.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserBusinessValidator userBusinessValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,UserBusinessValidator userBusinessValidator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userBusinessValidator = userBusinessValidator;
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAllBy(pageable);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public User findById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User get(Long id) {
        return userRepository.findUserById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Long create(User entity) throws BusinessValidationException {
        userBusinessValidator.validateIfExists(entity.getName());
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

    @Override
    public void update(User entity) {
    }

    @Override
    public void delete(Long id) {
    }

}
