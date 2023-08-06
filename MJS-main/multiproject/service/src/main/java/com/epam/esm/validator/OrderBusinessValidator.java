package com.epam.esm.validator;

import com.epam.esm.repository.OrderRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDetail;
import com.epam.esm.exception.BusinessValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderBusinessValidator {

    private MessageSource messageSource;
    private OrderRepository orderRepository;
    private CertificateBusinessValidator certificateBusinessValidator;

    @Autowired
    public OrderBusinessValidator(MessageSource messageSource, OrderRepository orderRepository, CertificateBusinessValidator certificateBusinessValidator) {
        this.messageSource = messageSource;
        this.orderRepository = orderRepository;
        this.certificateBusinessValidator = certificateBusinessValidator;
    }

    public void validateIfExists(Long orderId) throws BusinessValidationException {
        if (orderRepository.findOrderById(orderId) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.order", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfCertificateExists(Order order) throws BusinessValidationException {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail od : orderDetails) {
            certificateBusinessValidator.validateIfExistsById(od.getCertificate().getId());
            certificateBusinessValidator.validateIfExists(od.getCertificate().getName());
        }
    }
}



