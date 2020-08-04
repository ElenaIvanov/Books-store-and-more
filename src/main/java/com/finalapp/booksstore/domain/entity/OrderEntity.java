package com.finalapp.booksstore.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @Column(name = "orderID",nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long orderID;

    private Long userID;

    private BigDecimal price;

    @Column(name = "orderdate",nullable = false)
    private LocalDate orderDate;

    @Column(name = "shippingdate",nullable = false)
    private LocalDate shippingDate;

    @Column(name = "orderproducts",nullable = false)
    private String orderProducts;

    private String status;
}
