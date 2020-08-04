package com.finalapp.booksstore.controller;

import com.finalapp.booksstore.domain.model.ProductDTO;
import com.finalapp.booksstore.service.CurrentUserService;
import com.finalapp.booksstore.service.OrderService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class OrdersController {

    private final OrderService service;

    @GetMapping("newOrder")
    public RedirectView addOrder(@ModelAttribute("prods") ArrayList<ProductDTO> productList, @ModelAttribute("price") BigDecimal price, RedirectAttributes ra) {

        service.addNewOrder(productList, price);
        ra.addFlashAttribute("price", price);
        return new RedirectView("userUpdate");
    }

    @GetMapping("/account/orders")
    public ModelAndView getAllForUser(ModelAndView model) {
        model.addObject("orders", service.getAllForUser(CurrentUserService.currentUser.getUserID()));
        model.setViewName("orders");
        return model;
    }
}
