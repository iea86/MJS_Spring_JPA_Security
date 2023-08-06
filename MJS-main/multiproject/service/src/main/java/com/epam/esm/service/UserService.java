package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends ManagementService<User> {
    /**
     * Get list of users which satisfy pagination
     *
     * @param pageable entered on front-end
     * @return Page<User>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    Page<User> getUsers(Pageable pageable) throws ServiceException;

    /**
     * Get list of users
     *
     * @return List<User>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Get user by id
     *
     * @param id of user
     * @return User
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    User findById(Long id) throws ServiceException;
}
