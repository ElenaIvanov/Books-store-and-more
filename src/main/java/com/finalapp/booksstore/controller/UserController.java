package com.finalapp.booksstore.controller;

import com.finalapp.booksstore.domain.model.UserDTO;
import com.finalapp.booksstore.exception.UsersNotFoundException;
import com.finalapp.booksstore.service.CurrentUserService;
import com.finalapp.booksstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;


@RestController
@RequestMapping("")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/addToCart")
    public RedirectView addToCart(@RequestParam("name") String name, @RequestParam("quantity") int quantity, @RequestParam("productId") Long productId, RedirectAttributes ra) {
        if (CurrentUserService.logged == 1) {
            service.addToCart(productId, quantity);
            ra.addFlashAttribute("order", name + " has been added to the cart!");
        } else {
            ra.addFlashAttribute("You must first Lon-in in order to add items to cart!");
        }
        return new RedirectView("index");
    }

    @PostMapping("/updateCart")
    public RedirectView updateCart(@RequestParam("remove") Long removeId, ModelAndView model) {
        service.updateCart(removeId);
        return new RedirectView("myCart"); //myBasket
    }

    @GetMapping("/account")
    public ModelAndView account(ModelAndView model) {
        if (CurrentUserService.logged == 1) {
            model.addObject("balance", CurrentUserService.currentUser.getBalance());
            model.addObject("logged", 1);
        }
        return model;
    }

    @PostMapping("/account")
    public RedirectView account(@RequestParam("amount") double amount, @RequestParam("CVC") String CVC, @RequestParam("cardNumber") String cardNumber, RedirectAttributes ra) {
        if (CurrentUserService.logged == 1) {
            cardNumber = cardNumber.replaceAll("\\s+", "");
            if (CVC.matches("[0-9]+") && CVC.length() == 3 && cardNumber.matches("[0-9]+") && cardNumber.length() == 16) {
                service.addBalance((int) amount);
                ra.addFlashAttribute("order", "Your new balance is " + CurrentUserService.currentUser.getBalance().toString());
                return new RedirectView("index");
            } else {
                ra.addFlashAttribute("message", "Something went wrong. Incorrect information provided.");
                return new RedirectView("account");
            }
        }
        return new RedirectView("account");
    }

    @GetMapping("/register")
    public ModelAndView showRegister(ModelAndView model) {
        model.addObject("user", new UserDTO());
        return model;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") UserDTO user) {

        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);

        UserDTO newUser = service.createRegister(user);
        CurrentUserService.currentUser = newUser;
        CurrentUserService.logged = 1;
        return new ModelAndView(new RedirectView("index"));
    }

    @GetMapping("/userUpdate")
    public RedirectView updateCurrentUser(RedirectAttributes ra, @ModelAttribute("price") BigDecimal price) {

        service.updateCurrentUser(CurrentUserService.currentUser);
        RedirectView rv = new RedirectView("index");
        ra.addFlashAttribute("order", "Your order of " + price + " has been placed!");
        return rv;
    }

    @GetMapping("/login")
    public ModelAndView showAll() {
        ModelAndView model;
        if (CurrentUserService.logged == 1) {
            model = new ModelAndView(new RedirectView("index"));
            return model;
        }
        model = new ModelAndView("login");
        return model;
    }

    @GetMapping("/logout1")
    public ModelAndView logout() {
        ModelAndView model;
        if (CurrentUserService.logged == 1) {
            System.out.println("Here");
            CurrentUserService.logged = 0;
            CurrentUserService.currentUser = null;
        }
        model = new ModelAndView(new RedirectView("index"));
        return model;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam("password") String password, @RequestParam("email") String email, ModelAndView model) {
        try {
            UserDTO userFound = service.findByEmail(email);
            if (passwordEncoder.matches(password, userFound.getPassword())) {
                CurrentUserService.currentUser = userFound;
                CurrentUserService.logged = 1;
            } else {
                model.addObject("message", "The password you entered is incorrect!");
                return model;
            }
        } catch (UsersNotFoundException e) {
            model.addObject("message", "The email you have entered is not registered!");
            return model;
        }
        model = new ModelAndView(new RedirectView("index"));
        return model;
    }
}
