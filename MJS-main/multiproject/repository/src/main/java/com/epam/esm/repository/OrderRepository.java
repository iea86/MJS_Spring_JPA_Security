package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findAll();

    Order findOrderById(Long id);

    List<Order> findOrdersByUser(User user);

    Order findOrderByIdAndUser(Long orderId, User user);

    Order save(Order order);

    void deleteById(Long id);
}
