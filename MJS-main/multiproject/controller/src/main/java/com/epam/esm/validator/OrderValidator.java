package com.epam.esm.validator;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderDetailDTO;
import com.epam.esm.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class OrderValidator {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public static void validateOrderDTO(OrderDTO orderDTO, Locale locale) throws ServiceException {
        if (orderDTO.getOrderDetails().isEmpty()) {
            String errorMessage = messageSource.getMessage("label.not.entered.order.details", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (!orderDTO.getOrderDetails().isEmpty()) {
            for (OrderDetailDTO od : orderDTO.getOrderDetails()) {
                OrderDetailValidator.validateOrderDetailDTO(od, locale);
            }
        }
    }
}

