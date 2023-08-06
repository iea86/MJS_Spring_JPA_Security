package com.epam.esm.dao.impl;

import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class UserRepositoryTest {

    private final static String USER_NAME = "user1";
    private final static Long USER_ID_CREATED= 2L;

    @Autowired
    UserRepository userRepository;

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAll() {
        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByName() {
        User user = userRepository.findByName(USER_NAME);
        assertEquals(1, user.getId());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindById() {
        User user = userRepository.findUserById(1L);
        assertEquals(USER_NAME, user.getName());
    }


    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSave() {
        User user=getUser();
        User userCreated = userRepository.save(user);
        assertEquals(USER_ID_CREATED, userCreated.getId());
    }

    private User getUser() {
        User user = new User();
        user.setStatus(Status.ACTIVE);
        user.setEmail("newUser@gmail.com");
        user.setName("newUser");
        return user;
    }
}
