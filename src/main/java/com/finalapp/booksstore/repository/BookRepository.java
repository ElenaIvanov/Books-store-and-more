package com.finalapp.booksstore.repository;

import com.finalapp.booksstore.domain.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT b FROM BookEntity b")
    List<BookEntity> getAll();

}
