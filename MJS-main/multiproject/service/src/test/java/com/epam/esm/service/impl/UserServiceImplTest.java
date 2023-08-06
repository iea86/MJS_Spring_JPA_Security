package com.epam.esm.service.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.validator.UserBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    private final static Long USER_ID = 1L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserBusinessValidator userBusinessValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws  BusinessValidationException {
        User user = createUser();
        List<Role> roles = createRoles();
        doNothing().when(userBusinessValidator).validateIfExists(user.getName());
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.findAllBy()).thenReturn(roles);
        Long id = userService.create(user);
        assertEquals(USER_ID, id);
    }


    @Test
    public void testGet()  {
        User user = createUser();
        when(userRepository.findUserById(USER_ID)).thenReturn(user);
        User userActual = userService.get(USER_ID);
        assertEquals(user, userActual);
        verify(userRepository, times(1)).findUserById(USER_ID);
    }


    @Test
    public void getAllUsers()  {
        List<User> users = createUsers();
        when(userRepository.findAll()).thenReturn(users);
        List<User> actualUsers=userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(users, actualUsers);
    }

    @Test
    public void getUsers()  {
        Page<User> users = mock(Page.class);
        when(userRepository.findAllBy(null)).thenReturn(users);
        Page<User> actualUsers=userService.getUsers(null);
        verify(userRepository, times(1)).findAllBy(null);
        assertEquals(users, actualUsers);
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("userName");
        List<Role> role = createRoles();
        user.setRoles(role);
        return user;
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        return users;
    }

    private List<Role> createRoles() {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        role.setRoleName(UserRole.ROLE_USER);
        roles.add(role);
        return roles;
    }


}




