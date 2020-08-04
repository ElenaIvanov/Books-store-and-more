package com.finalapp.booksstore.mapper;


import com.finalapp.booksstore.domain.entity.CommentEntity;
import com.finalapp.booksstore.domain.model.CommentDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentToCommentEntityMapper implements Converter<CommentDTO, CommentEntity> {
    @Override
    public CommentEntity convert(CommentDTO source) {
        return CommentEntity.builder()
                .content(source.getContent())
                .date(source.getTime())
                .build();
    }
}


