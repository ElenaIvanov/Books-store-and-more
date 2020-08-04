package com.finalapp.booksstore.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private Long productID;

    private String name;

    private String category;

    private int quantity;

    private String description;

    private BigDecimal price;

    @Column(name = "shippingDays",nullable = false)
    private int shippingDays;

    private BigDecimal rating;

    private Long totalRating;

}
