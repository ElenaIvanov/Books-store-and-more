package com.finalapp.booksstore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private String author;

    private String publisher;

    private int pages;

    private String category;

    private LocalDate publishedYear;

    private String languages;

}
