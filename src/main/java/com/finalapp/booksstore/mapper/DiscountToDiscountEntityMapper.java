package com.finalapp.booksstore.mapper;

import com.finalapp.booksstore.domain.entity.DiscountEntity;
import com.finalapp.booksstore.domain.model.DiscountDTO;
import org.springframework.core.convert.converter.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DiscountToDiscountEntityMapper implements Converter<DiscountDTO, DiscountEntity> {

    @Override
    public DiscountEntity convert(DiscountDTO source) {
        return DiscountEntity.builder()
                .discountCode(source.getDiscountCode())
                .discountType(source.getDiscountType())
                .amount(source.getAmount())
                .build();
    }
}
