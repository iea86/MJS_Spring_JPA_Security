package com.epam.esm.controller;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.response.InfoResponse;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.DataGenerator;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("orders")
public class OrderController {

    private OrderService orderService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;

    @Autowired
    public OrderController(OrderService orderService, DataGenerator dataGeneratorService, MessageSource messageSource) {
        this.orderService = orderService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('role_user')")
    public HttpEntity<List<OrderDTO>> getUserOrders() throws ServiceException {
        List<OrderDTO> ordersDTO = orderService.getByUser()
                .stream()
                .map(OrderConverter::convertToDto)
                .collect(Collectors.toList());
        addOrdersHATEOAS(ordersDTO);
        return ResponseEntity.ok(ordersDTO);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('role_user')")
    public Map<String, Object> getOrderById(@PathVariable Long orderId, @RequestParam(value = "include", required = false) String include) throws ServiceException {
        Order order = orderService.getByUserAndOrder(orderId);
        return OrderConverter.convertOrderToMap(order, include);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_user','role_admin')")
    public InfoResponse addOrder(@RequestBody OrderDTO orderDTO, Locale locale) throws ServiceException, BusinessValidationException {
        OrderValidator.validateOrderDTO(orderDTO, locale);
        Order order = OrderConverter.convertToEntity(orderDTO);
        Long orderId = orderService.create(order);
        String message = messageSource.getMessage("label.order.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ":" + orderId,
                HttpStatus.CREATED.toString());
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('role_admin')")
    public InfoResponse generateOrders(Locale locale, @RequestParam(value = "count") int count) throws ServiceException {
        dataGeneratorService.generateOrders(count);
        int numberOfRows = orderService.getAllOrders().size();
        String message = messageSource.getMessage("label.orders.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addOrdersHATEOAS(List<OrderDTO> ordersDTO) throws ServiceException {
        for (OrderDTO orderDTO : ordersDTO) {
            orderDTO.add(linkTo(methodOn(OrderController.class).getOrderById(orderDTO.getOrderId(), null)).withSelfRel());
        }
    }
}
