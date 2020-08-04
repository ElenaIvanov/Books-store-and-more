package com.finalapp.booksstore.mapper;


import com.finalapp.booksstore.domain.entity.UserEntity;
import com.finalapp.booksstore.domain.model.UserDTO;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import org.springframework.core.convert.converter.Converter;

@Component
@AllArgsConstructor
public class UserEntityToUserMapper implements Converter<UserEntity, UserDTO> {

    @Override
    public UserDTO convert(UserEntity source) {
        return UserDTO.builder()
                .userID(source.getUserID())
                .lastName(source.getLastName())
                .firstName(source.getFirstName())
                .password(source.getPassword())
                .email(source.getEmail())
                .balance(source.getBalance())
                .userType(source.getUserType())
                .fidelityPoints(source.getFidelityPoints())
                .address(source.getAddress())
                .cart(source.getCart())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}





