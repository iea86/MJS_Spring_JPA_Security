package com.epam.esm.service.impl;

import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.*;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderBusinessValidator orderBusinessValidator;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, OrderBusinessValidator orderBusinessValidator) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderBusinessValidator = orderBusinessValidator;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Long create(Order entity) throws BusinessValidationException {
        orderBusinessValidator.validateIfCertificateExists(entity);
        entity.setUser(getAuthenticatedUser());
        return orderRepository.save(entity).getId();
    }

    @Override
    public Order get(Long id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public List<Order> getByUser() {
        return orderRepository.findOrdersByUser(getAuthenticatedUser());
    }

    @Override
    public void update(Order entity) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getByUserAndOrder(Long orderId) {
        return orderRepository.findOrderByIdAndUser(orderId, getAuthenticatedUser());
    }

    @Override
    public User getAuthenticatedUser() {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userRepository.findByName(principal.getUsername());
    }
}
