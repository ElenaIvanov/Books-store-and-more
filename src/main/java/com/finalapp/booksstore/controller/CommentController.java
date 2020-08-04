package com.finalapp.booksstore.controller;

import com.finalapp.booksstore.domain.model.CommentDTO;
import com.finalapp.booksstore.service.BookService;
import com.finalapp.booksstore.service.CommentService;
import com.finalapp.booksstore.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/books/{id}/comments")
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;
    private final UserService userService;

}
