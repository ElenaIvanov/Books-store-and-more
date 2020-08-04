package com.finalapp.booksstore.mapper;

import com.finalapp.booksstore.domain.entity.DiscountEntity;
import com.finalapp.booksstore.domain.model.DiscountDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DiscountEntityToDiscountMapper implements Converter<DiscountEntity, DiscountDTO> {
    @Override
    public DiscountDTO convert(DiscountEntity source) {
        return DiscountDTO.builder()
                .discountCode(source.getDiscountCode())
                .discountType(source.getDiscountType())
                .amount(source.getAmount())
                .build();
    }
}
