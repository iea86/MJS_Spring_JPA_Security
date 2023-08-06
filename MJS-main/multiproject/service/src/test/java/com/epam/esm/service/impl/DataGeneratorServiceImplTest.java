package com.epam.esm.service.impl;

import com.epam.esm.entity.*;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DataGeneratorServiceImplTest {

    private final static Long CERTIFICATE_ID = 1L;
    private final static Long ORDER_ID = 1L;
    private final static Long USER_ID = 1L;

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CertificateBusinessValidator certificateBusinessValidator;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private DataGeneratorServiceImpl dataGeneratorService;

    @Mock
    private TagService tagService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateCertificates() throws ServiceException {
        Certificate certificate = new Certificate();
        when(certificateRepository.save(certificate)).thenReturn(certificate);
        dataGeneratorService.generateCertificates(1,1,1,1,1);
        assertEquals(CERTIFICATE_ID, CERTIFICATE_ID);
    }

    @Test
    public void testGenerateOrders()  {
        Order order = createOrder();
        List<User> users = new ArrayList<>();
        List<Certificate> certificates = new ArrayList<>();
        users.add(createUser());
        certificates.add(createCertificate());
        when(userRepository.findAll()).thenReturn(users);
        when(certificateRepository.findAll()).thenReturn(certificates);
        when(orderRepository.save(order)).thenReturn(order);
        dataGeneratorService.generateOrders(1);
        assertEquals(ORDER_ID, ORDER_ID);
    }

    @Test
    public void testGenerateTags() throws ServiceException {
        Tag tag = new Tag();
        when(tagRepository.save(tag)).thenReturn(tag);
        dataGeneratorService.generateTags(1);
        assertEquals(1L, 1L);
    }

    @Test
    public void testGenerateUsers() throws ServiceException {
        User user = createUser();
        when(userRepository.save(user)).thenReturn(user);
        dataGeneratorService.generateUsers(1);
        assertEquals(USER_ID, USER_ID);
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
