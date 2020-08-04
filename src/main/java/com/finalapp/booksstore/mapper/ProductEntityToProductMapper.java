package com.finalapp.booksstore.mapper;


import com.finalapp.booksstore.domain.entity.ProductEntity;
import com.finalapp.booksstore.domain.model.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductEntityToProductMapper implements Converter<ProductEntity, ProductDTO> {

    @Override
    public ProductDTO convert(ProductEntity source) {
        return ProductDTO
                .builder()
                .productID(source.getProductID())
                .name(source.getName())
                .category(source.getCategory())
                .quantity(source.getQuantity())
                .description(source.getDescription())
                .price(source.getPrice())
                .shippingDays(source.getShippingDays())
                .rating(source.getRating())
                .totalrating(source.getTotalRating())
                .build();



    }
}
