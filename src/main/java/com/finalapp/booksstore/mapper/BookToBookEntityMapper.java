package com.finalapp.booksstore.mapper;

import com.finalapp.booksstore.domain.entity.BookEntity;
import com.finalapp.booksstore.domain.model.BookDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookToBookEntityMapper implements Converter<BookDTO, BookEntity> {
    @Override
    public BookEntity convert(BookDTO source) {
        return BookEntity.builder()
                .id((source.getId()))
                .title(source.getTitle())
                .author(source.getAuthor())
                .publisher(source.getPublisher())
                .pages(source.getPages())
                .category(source.getCategory())
                .publishedYear(source.getPublishedYear())
                .languages(source.getLanguages())
                .build();
    }
}
