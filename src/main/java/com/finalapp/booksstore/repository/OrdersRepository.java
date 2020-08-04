package com.finalapp.booksstore.repository;


import com.finalapp.booksstore.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity,Long> {

    @Query("SELECT s FROM OrderEntity s WHERE userID = ?1" )
    List<OrderEntity> getAllForUser(Long id);

}
