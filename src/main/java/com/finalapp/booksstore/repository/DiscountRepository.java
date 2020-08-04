package com.finalapp.booksstore.repository;


import com.finalapp.booksstore.domain.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, String> {

    @Query("select d from DiscountEntity d ")
    List<DiscountEntity> getAll();

    
    Long deleteByDiscountCode(String discountCode);
}
