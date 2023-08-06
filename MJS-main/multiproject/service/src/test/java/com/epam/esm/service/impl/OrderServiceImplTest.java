package com.epam.esm.service.impl;

import com.epam.esm.entity.*;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.validator.OrderBusinessValidator;
import com.epam.esm.validator.UserBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

    private final static Long ORDER_ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private OrderBusinessValidator orderBusinessValidator;
    @Mock
    private UserBusinessValidator userBusinessValidator;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws BusinessValidationException {
        Order order = createOrder();
        doNothing().when(orderBusinessValidator).validateIfCertificateExists(order);
        User user = createUser();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = new Principal();
        principal.setUsername("userName");
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(principal);
        when(userRepository.findByName(user.getName())).thenReturn(user);
        when(orderRepository.save(order)).thenReturn(order);
        Long id = orderService.create(order);
        assertEquals(ORDER_ID, id);
    }

    @Test
    public void testGetAuthUser() {
        User user = createUser();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = new Principal();
        principal.setUsername("userName");
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(principal);
        when(userRepository.findByName(user.getName())).thenReturn(user);
        User userAuth = orderService.getAuthenticatedUser();
        assertEquals(user, userAuth);
    }

    @Test
    public void testRead() {
        Order order = createOrder();
        when(orderRepository.findOrderById(ORDER_ID)).thenReturn(order);
        Order orderActual = orderService.get(ORDER_ID);
        assertEquals(order, orderActual);
    }

    @Test
    public void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);
        orderService.getAllOrders();
        verify(orderRepository, times(1)).findAll();
    }


    @Test
    public void testReadByUser() {
        User user = createUser();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = new Principal();
        principal.setUsername("userName");
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(principal);
        when(userRepository.findByName(user.getName())).thenReturn(user);
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder());
        when(orderRepository.findOrdersByUser(user)).thenReturn(orders);
        List<Order> ordersActual = orderService.getByUser();
        assertEquals(orders, ordersActual);
    }

    @Test
    public void testReadByUserAndOrder() {
        User user = createUser();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = new Principal();
        principal.setUsername("userName");
        Mockito.when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(principal);
        when(userRepository.findByName(user.getName())).thenReturn(user);
        Order order = createOrder();
        when(orderRepository.findOrderByIdAndUser(order.getId(), user)).thenReturn(order);
        Order orderActual = orderService.getByUserAndOrder(ORDER_ID);
        assertEquals(order, orderActual);
    }

    private Order createOrder() {
        Order order = new Order();
        order.setId(1L);
        OrderDetail od = new OrderDetail();
        od.setOrderDetailsId(1L);
        od.setQuantity(1);
        od.setCertificate(createCertificate());
        List<OrderDetail> ods = new ArrayList<>();
        ods.add(od);
        order.setOrderDetails(ods);
        order.setUser(createUser());
        return order;
    }

    private Certificate createCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        tags.add(tag);
        certificate.setTags(tags);
        return certificate;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("userName");
        return user;
    }
}
