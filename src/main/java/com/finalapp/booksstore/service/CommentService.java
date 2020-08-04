package com.finalapp.booksstore.service;

import com.finalapp.booksstore.domain.entity.CommentEntity;
import com.finalapp.booksstore.domain.model.CommentDTO;
import com.finalapp.booksstore.exception.CommentNotFound;
import com.finalapp.booksstore.mapper.CommentEntityToCommentMapper;
import com.finalapp.booksstore.mapper.CommentToCommentEntityMapper;
import com.finalapp.booksstore.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@AllArgsConstructor
@Validated
public class CommentService {

    private final CommentRepository repository;

    private final CommentEntityToCommentMapper commentEntityToCommentMapper;

    private final CommentToCommentEntityMapper commentToCommentEntityMapper;

    public CommentDTO getComment(Integer id) {

        return repository.findById(id)
                .map(commentEntityToCommentMapper::convert)
                .orElseThrow(() -> new CommentNotFound("Comment with id provided not found"));
    }

    public void addComment(CommentDTO comment) {
        CommentDTO commentDTO = new CommentDTO(comment);
        CommentEntity commentEntity = commentToCommentEntityMapper.convert(commentDTO);
        repository.save(commentEntity);
    }

    public void deleteComment(Integer commentId) {
        repository.deleteById(commentId);
    }

    public List<CommentEntity> getAllComments(Integer bookId) {

        return repository.findAllByBookId(bookId);
    }
}
