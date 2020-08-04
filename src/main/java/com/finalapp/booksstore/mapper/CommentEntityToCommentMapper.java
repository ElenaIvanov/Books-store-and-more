package com.finalapp.booksstore.mapper;


import com.finalapp.booksstore.domain.entity.CommentEntity;
import com.finalapp.booksstore.domain.model.CommentDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentEntityToCommentMapper implements Converter<CommentEntity, CommentDTO> {
    @Override
    public CommentDTO convert(CommentEntity source) {
        return CommentDTO.builder()
                .content(source.getContent())
                .time(source.getDate())
                .userName(String.valueOf(source.getUser()))
                .build();
    }
}


