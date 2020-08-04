package com.finalapp.booksstore.mapper;

import com.finalapp.booksstore.domain.entity.ProductEntity;
import com.finalapp.booksstore.domain.model.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.core.convert.converter.Converter;

@Component
@AllArgsConstructor
public class ProductToProductEntityMapper implements Converter<ProductDTO, ProductEntity> {

    @Override
    public ProductEntity convert(ProductDTO source) {
        return ProductEntity.builder()
                .productID(source.getProductID())
                .name(source.getName())
                .quantity(source.getQuantity())
                .category(source.getCategory())
                .description(source.getDescription())
                .price(source.getPrice())
                .shippingDays(source.getShippingDays())
                .rating(source.getRating())
                .totalRating(source.getTotalrating())
                .build();
    }

}
