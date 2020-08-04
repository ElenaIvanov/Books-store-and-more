package com.finalapp.booksstore.controller;

import com.finalapp.booksstore.domain.model.DiscountDTO;
import com.finalapp.booksstore.exception.DiscountNotFoundException;

import com.finalapp.booksstore.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class DiscountController {

    private final DiscountService service;

    @GetMapping("/admin")
    public ModelAndView getPromotionById(ModelAndView model, @ModelAttribute("removeMessage") String message) {
        model.addObject("promotions", service.getAll());
        model.addObject("removeMessage", message);
        return model;
    }

    @PostMapping("/checkDiscount")
    public ModelAndView checkPromotion(@RequestParam("discountCode") String discountCode, RedirectAttributes ra) {
        ModelAndView model = new ModelAndView();
        try {
            DiscountDTO promo = service.findById(discountCode);
            model.addObject("discountType", promo.getDiscountType());
            model.addObject("discountAmount", promo.getAmount());
            model.setViewName("redirect:/placedOrder");
            return model;
        } catch (DiscountNotFoundException e) {
            model.addObject("discountType", 0);
            model.addObject("discountAmount", 0);
            model.setViewName("redirect:/placedOrder");
            return model;
        }
    }

    @PostMapping("/addDiscount")
    public ModelAndView addPromotion(@RequestParam("discountCode") String code, @RequestParam("discountType") Short type, @RequestParam("amount") BigDecimal amount, ModelAndView model) {

        service.addDiscount(code, type, amount);
        model.setViewName("redirect:admin");
        return model;
    }

    @PostMapping("/removeDiscount")
    public ModelAndView removePromotion(@RequestParam("code") String code, ModelAndView model) {
        Long ret = service.removeDiscount(code);

        if (ret == 0) {
            model.addObject("removeMessage", "The discount code does not exist!");
        } else {
            model.addObject("removeMessage", "The discount code " + code + " was deleted!");
        }
        model.setViewName("redirect:admin");
        return model;
    }
}
