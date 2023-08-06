package com.epam.esm.dao.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class OrderRepositoryTest {

    private final static double ORDER_COST = 10.0;
    private final static Long ORDER_ID = 1L;
    private final static Long ORDER_ID_TO_DELETE = 2L;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAll() {
        List<Order> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindOrderByUser() {
        User user = userRepository.findUserById(1L);
        List<Order> orders = orderRepository.findOrdersByUser(user);
        assertEquals(1, orders.size());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSave() {
        Order order = getOrder();
        Order orderCreated = orderRepository.save(order);
        assertEquals(3, orderCreated.getId());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindById() {
        Order order = orderRepository.findOrderById(ORDER_ID);
        assertEquals(ORDER_COST, order.getCost());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindOrderByIdAndUser() {
        User user = userRepository.findUserById(ORDER_ID);
        Order order = orderRepository.findOrderByIdAndUser(1L, user);
        assertEquals(ORDER_COST, order.getCost());
    }

    @Test
    @Sql(scripts = {"/order.sql"})
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteById() {
        orderRepository.deleteById(ORDER_ID_TO_DELETE);
        assertNull(orderRepository.findOrderById(ORDER_ID_TO_DELETE));
    }

    private Order getOrder() {
        Order order = new Order();
        User user = userRepository.findUserById(ORDER_ID);
        order.setUser(user);
        order.setCost(ORDER_COST);
        return order;
    }
}
