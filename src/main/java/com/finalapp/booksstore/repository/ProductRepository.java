package com.finalapp.booksstore.repository;


import com.finalapp.booksstore.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query("SELECT s FROM ProductEntity s ")
    List<ProductEntity> getAll();

    List<ProductEntity> findAll();

    @Query("SELECT s FROM ProductEntity s WHERE s.productID IN :inlist")
    List<ProductEntity> getAllInList(@Param("inlist") String inlist);

    List<ProductEntity> findByproductIDIn(List<Long> productIDList);

    List<ProductEntity> findByNameContains(String name);

    List<ProductEntity> findAllByCategory(String category);

}
