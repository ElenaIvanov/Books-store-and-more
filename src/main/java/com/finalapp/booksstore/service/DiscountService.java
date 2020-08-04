package com.finalapp.booksstore.service;


import com.finalapp.booksstore.domain.entity.DiscountEntity;
import com.finalapp.booksstore.domain.model.DiscountDTO;
import com.finalapp.booksstore.exception.DiscountNotFoundException;
import com.finalapp.booksstore.mapper.DiscountEntityToDiscountMapper;
import com.finalapp.booksstore.mapper.DiscountToDiscountEntityMapper;
import com.finalapp.booksstore.repository.DiscountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Validated
public class DiscountService {

    private final DiscountRepository repository;

    private final DiscountEntityToDiscountMapper discountEntityToDiscountMapper;

    private final DiscountToDiscountEntityMapper discountToDiscountEntityMapper;

    public DiscountDTO findById(String discountId) {
        return repository.findById(discountId)
                .map(discountEntityToDiscountMapper::convert)
                .orElseThrow(() -> new DiscountNotFoundException("No discount found."));
    }

    public List<DiscountDTO> getAll() {
        return repository.getAll()
                .stream()
                .map(discountEntityToDiscountMapper::convert)
                .collect(toList());
    }

    public void addDiscount(String code, Short type, BigDecimal amount) {
        DiscountDTO discountDTO = new DiscountDTO(code, type, amount);
        DiscountEntity discountEntity = discountToDiscountEntityMapper.convert(discountDTO);
        repository.save(Objects.requireNonNull(discountEntity));
    }

    @Transactional
    public Long removeDiscount(String discountCode) {
        return repository.deleteByDiscountCode(discountCode);
    }
}

