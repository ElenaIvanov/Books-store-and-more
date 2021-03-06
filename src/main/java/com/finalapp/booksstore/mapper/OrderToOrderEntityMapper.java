package com.finalapp.booksstore.mapper;

import com.finalapp.booksstore.domain.entity.OrderEntity;
import com.finalapp.booksstore.domain.model.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderToOrderEntityMapper implements Converter<OrderDTO, OrderEntity> {

    @Override
    public OrderEntity convert(OrderDTO source) {
        return OrderEntity.builder()
                .orderID(source.getOrderID())
                .userID(source.getUserID())
                .price(source.getPrice())
                .orderDate(source.getOrderDate())
                .shippingDate(source.getShippingDate())
                .orderProducts(source.getOrderProducts())
                .status(source.getStatus())
                .build();

    }
}

