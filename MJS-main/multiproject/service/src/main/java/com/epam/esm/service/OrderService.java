package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface OrderService extends ManagementService<Order> {
    /**
     * Get orders of user
     *
     * @return List<Order>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<Order> getByUser() throws ServiceException;

    /**
     * Get all orders
     *
     * @return List<Order>
     */
    List<Order> getAllOrders() throws ServiceException;

    /**
     * Get particular order of user
     *
     * @param id of order
     * @return Order
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    Order getByUserAndOrder(Long id) throws ServiceException;

    /**
     * Get authenticated user
     *
     * @return User
     */
    User getAuthenticatedUser();
}
