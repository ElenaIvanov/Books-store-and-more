package com.finalapp.booksstore.service;


import com.finalapp.booksstore.domain.entity.OrderEntity;
import com.finalapp.booksstore.domain.model.OrderDTO;
import com.finalapp.booksstore.domain.model.ProductDTO;
import com.finalapp.booksstore.exception.OrdersNotFoundException;
import com.finalapp.booksstore.mapper.OrderEntityToOrderMapper;
import com.finalapp.booksstore.mapper.OrderToOrderEntityMapper;
import com.finalapp.booksstore.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class OrderService {

    private final OrdersRepository repository;

    private final OrderEntityToOrderMapper ordersEntityToOrdersMapper;

    private final OrderToOrderEntityMapper ordersToOrdersEntityMapper;

    public OrderDTO createOrder(@Valid OrderDTO product) {
        OrderEntity ordersEntity = ordersToOrdersEntityMapper.convert(product);
        OrderEntity savedEntity = repository.save(Objects.requireNonNull(ordersEntity));
        return ordersEntityToOrdersMapper.convert(savedEntity);
    }

    public OrderDTO addNewOrder(ArrayList<ProductDTO> productList, BigDecimal price) {

        OrderDTO newOrd = new OrderDTO();
        newOrd.setUserID(CurrentUserService.currentUser.getUserID());
        newOrd.setOrderDate(LocalDate.now());
        StringBuilder orderProducts = new StringBuilder();
        int maxDays = 0;

        for (ProductDTO prd : productList) {

            orderProducts.append(prd.getProductID()).append("#").append(prd.getQuantity()).append(";");
            if (prd.getShippingDays() > maxDays) {
                maxDays = prd.getShippingDays();
            }
        }
        newOrd.setOrderProducts(orderProducts.toString());
        newOrd.setStatus("Processed");
        newOrd.setPrice(price);
        LocalDate ld = LocalDate.now();
        DayOfWeek d = ld.getDayOfWeek();
        if (d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY) {
            maxDays = maxDays + 2;
        }
        newOrd.setShippingDate(ld.plusDays(maxDays));

        OrderEntity ordersEntity = ordersToOrdersEntityMapper.convert(newOrd);
        OrderEntity savedEntity = repository.save(Objects.requireNonNull(ordersEntity));
        return ordersEntityToOrdersMapper.convert(savedEntity);
    }

    public OrderDTO findById(long orderId) {
        return repository.findById(orderId)
                .map(ordersEntityToOrdersMapper::convert)
                .orElseThrow(() -> new OrdersNotFoundException("No product with provided id found!"));
    }

    public List<OrderDTO> getAllForUser(Long id) {
        return repository.getAllForUser(id)
                .stream()
                .map(ordersEntityToOrdersMapper::convert)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public void updateTransactional(OrderDTO orders) {
//        OrderEntity existingEntity = repository.findById(orders.getOrderID())
//                .orElseThrow(() -> new OrdersNotFoundException("The Order with id code provided cannot be found!"));
//        updateFields(existingEntity, orders);
//    }

    private void updateFields(OrderEntity existingEntity, OrderDTO products) {
        existingEntity.setStatus(products.getStatus());
    }
}

