package com.finalapp.booksstore.service;

import com.finalapp.booksstore.domain.entity.BookEntity;
import com.finalapp.booksstore.domain.model.BookDTO;
import com.finalapp.booksstore.exception.BookNotFoundException;
import com.finalapp.booksstore.mapper.BookEntityToBookMapper;
import com.finalapp.booksstore.mapper.BookToBookEntityMapper;
import com.finalapp.booksstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class BookService {

    private final BookRepository repository;

    private final BookEntityToBookMapper bookEntityToBookMapper;

    private final BookToBookEntityMapper bookToBookEntityMapper;

    public BookDTO create(@Valid BookDTO bookProducts) {
        BookEntity bookEntity = bookToBookEntityMapper.convert(bookProducts);
        BookEntity savedBookEntity = repository.save(Objects.requireNonNull(bookEntity));
        return bookEntityToBookMapper.convert(savedBookEntity);
    }

    public BookDTO findById(long productId) {
        return repository.findById(productId)
                .map(bookEntityToBookMapper::convert)
                .orElseThrow(() -> new BookNotFoundException("The book with specified id can not found."));
    }

    public List<BookDTO> getAll() {
        return repository.getAll()
                .stream()
                .map(bookEntityToBookMapper::convert)
                .collect(Collectors.toList());
    }
}
