package com.epam.esm.converter;
import com.epam.esm.dto.OrderDetailDTO;
import com.epam.esm.entity.OrderDetail;


public class OrderDetailConverter {

    private OrderDetailConverter() {
    }

    public static OrderDetailDTO convertToDto(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailsId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setCertificateId(orderDetail.getCertificate().getId());
        orderDetailDTO.setCertificateName(orderDetail.getCertificate().getName());
        orderDetailDTO.setCertificatePrice(orderDetail.getCertificate().getPrice());
        return orderDetailDTO;
    }
}
