package com.finalapp.booksstore.controller;

import com.finalapp.booksstore.domain.model.ProductDTO;
import com.finalapp.booksstore.service.CurrentUserService;
import com.finalapp.booksstore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;


    @GetMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getAll(ModelAndView model) {

        if (CurrentUserService.logged == 0) {
            model.addObject("logged", "You are not logged in");
            model.addObject("admin", false);
        } else {
            model.addObject("logged", "You are logged in");
            if (CurrentUserService.currentUser.getUserType() == 0)
                model.addObject("admin", true);
            else
                model.addObject("admin", false);
        }
        model.addObject("prods", service.getAll());
        return model;
    }

    @GetMapping("/filter")
    public ModelAndView search(@RequestParam("searchString") String searchString, ModelAndView model) {

        if (CurrentUserService.logged == 0) {
            model.addObject("logged", "You are not logged in");
        } else {
            model.addObject("logged", "You are logged in");
        }
        model.setViewName("index");
        model.addObject("prods", service.searchBy(searchString));
        return model;
    }

    @GetMapping("/category")
    public ModelAndView filterCategory(@RequestParam("categ") String category, ModelAndView model) {

        if (CurrentUserService.logged == 0) {
            model.addObject("logged", "You are not logged in");
        } else {
            model.addObject("logged", "You are logged in");
        }
        if (category.equals("All")) {
            model.setViewName("redirect:index");
            return model;
        } else {
            model.setViewName("index");
            model.addObject("prods", service.findByCategory(category));
            return model;
        }
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getProductById(@PathVariable("id") Long productId) {
        ModelAndView model = new ModelAndView("product");
        ProductDTO prd = service.findById(productId);
        model.addObject("product", prd);
        if (CurrentUserService.logged == 1)
            model.addObject("logged", "Logged");
        return model;
    }

    @PostMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView addRating(@PathVariable("id") long productId, @RequestParam("star") int stars) {
        ModelAndView model = new ModelAndView("product");
        if (CurrentUserService.logged == 1) {
            model.addObject("logged", "Logged");
            service.addRating(productId, stars);
        }
        ProductDTO prd = service.findById(productId);
        model.addObject("product", prd);
        System.out.println(stars);
        return model;
    }

    @GetMapping("/myCart")
    public ModelAndView viewCart(ModelAndView model) {

        if (CurrentUserService.logged != 1) {
            return new ModelAndView(new RedirectView("index"));
        } else {
            List<ProductDTO> list = service.getAllInList();
            model.addObject("cart", list);
            BigDecimal totalPrice = new BigDecimal("0.0");
            if (list != null) {
                for (ProductDTO prd : list)
                    totalPrice = totalPrice.add(prd.getPrice().multiply(BigDecimal.valueOf(prd.getQuantity())));
            }
            model.addObject("totalPrice", totalPrice);
            return model;
        }
    }

    @RequestMapping(value = "/placedOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public RedirectView addOrder(RedirectAttributes ra, @ModelAttribute("discountType") Short discountType, @ModelAttribute("discountAmount") int discountAmount) {

        RedirectView rv = new RedirectView("newOrder");
        List<ProductDTO> prdList = service.getAllInList();
        int quantity, orderQuantity;
        BigDecimal price = service.getPrice(prdList, discountType, discountAmount);

        if (CurrentUserService.currentUser.getBalance().compareTo(price) >= 0) {
            ra.addFlashAttribute("prods", prdList);

            for (ProductDTO prd : prdList) {
                quantity = (service.findById(prd.getProductID())).getQuantity();
                orderQuantity = prd.getQuantity();

                if (quantity < orderQuantity) {
                    rv.setUrl("myCart");
                    ra.addFlashAttribute("money", prd.getName() + " - not enough items in stock!");
                    return rv;
                }
                prd.setQuantity(quantity - orderQuantity);
                service.updateDatabase(prd);
                prd.setQuantity(orderQuantity);
            }
            ra.addFlashAttribute("price", price);
            CurrentUserService.currentUser.setBalance(CurrentUserService.currentUser.getBalance().subtract(price));
            CurrentUserService.currentUser.setCart("");
            return rv;
        } else {
            rv.setUrl("myCart");
            ra.addFlashAttribute("money", "Not sufficient funds to place the order");
        }
        return rv;
    }
}
