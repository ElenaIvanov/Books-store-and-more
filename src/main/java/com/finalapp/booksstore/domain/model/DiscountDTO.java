package com.finalapp.booksstore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    private String discountCode;

    private Short discountType;

    private BigDecimal amount;



}
