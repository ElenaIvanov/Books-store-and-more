package com.finalapp.booksstore.controller;


import com.finalapp.booksstore.domain.model.ProductDTO;
import com.finalapp.booksstore.service.BookService;
import com.finalapp.booksstore.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping("/products/{id}/book")
    public ModelAndView showBookProduct(@PathVariable("id") long bookProductId, ModelAndView model, @ModelAttribute ProductDTO myEntity) {

        model.setViewName("product");
        model.addObject("product", myEntity);
        model.addObject("bookProduct", service.findById(bookProductId));
        if (CurrentUserService.logged == 1) {
            model.addObject("logged", "Logged");
        }
        return model;
    }
}
