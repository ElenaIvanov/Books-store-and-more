package com.finalapp.booksstore.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountEntity {

    @Id
    private String discountCode;

    @Column(name = "discountType", nullable = false)
    private Short discountType;

    private BigDecimal amount;



}
