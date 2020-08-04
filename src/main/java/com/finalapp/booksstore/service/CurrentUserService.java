package com.finalapp.booksstore.service;


import com.finalapp.booksstore.domain.model.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Validated
public class CurrentUserService {

    public static UserDTO currentUser;
    public static int logged = 0;

}
