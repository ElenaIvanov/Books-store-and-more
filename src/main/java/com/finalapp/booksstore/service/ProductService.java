package com.finalapp.booksstore.service;

import com.finalapp.booksstore.domain.entity.ProductEntity;
import com.finalapp.booksstore.domain.model.ProductDTO;
import com.finalapp.booksstore.exception.ProductsNotFoundException;
import com.finalapp.booksstore.mapper.ProductEntityToProductMapper;
import com.finalapp.booksstore.mapper.ProductToProductEntityMapper;
import com.finalapp.booksstore.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Service
@AllArgsConstructor
@Validated
public class ProductService {

    private final ProductRepository repository;

    private final ProductEntityToProductMapper productsEntityToProductsMapper;

    private final ProductToProductEntityMapper productsToProductsEntityMapper;

    public ProductDTO create(@Valid ProductDTO product) {
        ProductEntity productsEntity = productsToProductsEntityMapper.convert(product);
        ProductEntity savedEntity = repository.save(Objects.requireNonNull(productsEntity));
        return productsEntityToProductsMapper.convert(savedEntity);
    }

    public ProductDTO findById(long productId) {
        return repository.findById(productId)
                .map(productsEntityToProductsMapper::convert)
                .orElseThrow(() -> new ProductsNotFoundException("No product found"));
    }

    public List<ProductDTO> getAll() {
        return repository.getAll()
                .stream()
                .map(productsEntityToProductsMapper::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchBy(String name) {
        return repository.findByNameContains(name)
                .stream()
                .map(productsEntityToProductsMapper::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllInList() {
        List<Long> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        if (CurrentUserService.currentUser.getCart().equals("") || CurrentUserService.currentUser.getCart() == null) {
            return null;
        }
        String[] productstWithQuantity = CurrentUserService.currentUser.getCart().split(";");

        for (String prod : productstWithQuantity) {
            int delim = prod.indexOf("#");
            productIds.add(Long.parseLong(prod.substring(0, delim)));
            quantities.add(Integer.parseInt(prod.substring(delim + 1)));
        }
        List<ProductEntity> l = repository.findByproductIDIn(productIds);
        List<ProductDTO> ret = l.stream()
                .map(productsEntityToProductsMapper::convert)
                .collect(Collectors.toList());
        int idx;
        for (ProductDTO prd : ret) {
            idx = productIds.indexOf(prd.getProductID());
            prd.setQuantity(quantities.get(idx));
        }
        return ret;
    }

    public void updateDatabase(ProductDTO products) {

        ProductEntity existingEntity = repository.findById(products.getProductID())
                .orElseThrow(() -> new ProductsNotFoundException("The products with id provided cannot be found"));

        updateFields(existingEntity, products);
        repository.save(existingEntity);
    }

    private void updateFields(ProductEntity existingEntity, ProductDTO products) {
        existingEntity.setQuantity(products.getQuantity());
    }

    public void addRating(long productId, int stars) {
        ProductDTO prd = repository.findById(productId)
                .map(productsEntityToProductsMapper::convert)
                .orElseThrow(() -> new ProductsNotFoundException("No product found"));

        double newRating = ((prd.getRating().doubleValue() * prd.getTotalrating()) + stars) / (prd.getTotalrating() + 1);
        prd.setRating(BigDecimal.valueOf(newRating).setScale(2, RoundingMode.HALF_EVEN));
        prd.setTotalrating(prd.getTotalrating() + 1);
        ProductEntity prdEntity = productsToProductsEntityMapper.convert(prd);
        repository.save(Objects.requireNonNull(prdEntity));
    }

    public List<ProductDTO> findByCategory(String category) {
        return repository.findAllByCategory(category).stream()
                .map(productsEntityToProductsMapper::convert)
                .collect(Collectors.toList());
    }

    public BigDecimal getPrice(List<ProductDTO> prdList, Short discountType, int discountAmount) {
        BigDecimal price = new BigDecimal("0.00");
        for (ProductDTO prd : prdList) {
            price = price.add(prd.getPrice().multiply(BigDecimal.valueOf(prd.getQuantity())));
        }
        if (discountType != 0) {
            if (discountType == 1) {
                BigDecimal pr = price.multiply(BigDecimal.valueOf(discountAmount / 100.00));
                price = price.subtract(pr);
            }
            if (discountType == 2) {
                price = price.subtract(BigDecimal.valueOf(discountAmount));
            }
        }
        return price;
    }
}
