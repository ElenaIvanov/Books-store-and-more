package com.finalapp.booksstore.repository;

import com.finalapp.booksstore.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    void deleteById(Integer commentId);

    List<CommentEntity> findAllByBookId(Integer bookId);
}
