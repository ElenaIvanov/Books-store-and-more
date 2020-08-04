package com.finalapp.booksstore.service;

import com.finalapp.booksstore.domain.entity.UserEntity;
import com.finalapp.booksstore.domain.model.UserDTO;
import com.finalapp.booksstore.exception.PhoneNumberNotFoundException;
import com.finalapp.booksstore.exception.UsersNotFoundException;
import com.finalapp.booksstore.mapper.UserEntityToUserMapper;
import com.finalapp.booksstore.mapper.UserToUserEntityMapper;
import com.finalapp.booksstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class UserService {

    private final UserRepository repository;

    private final UserEntityToUserMapper userEntityToUserMapper;

    private final UserToUserEntityMapper userToUserEntityMapper;

    public UserDTO createRegister(@Valid UserDTO user) {
        user.setBalance(new BigDecimal(0));
        user.setCart("");
        user.setUserType(1L);
        user.setFidelityPoints(0L);
        UserEntity usersEntity = userToUserEntityMapper.convert(user);
        UserEntity savedEntity = repository.save(Objects.requireNonNull(usersEntity));
        return userEntityToUserMapper.convert(savedEntity);
    }

    public UserDTO findById(Long userId) {
        return repository.findById(userId)
                .map(userEntityToUserMapper::convert)
                .orElseThrow(() -> new UsersNotFoundException("The user with the provided id number was not found"));
    }

    public UserDTO findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber)
                .map(userEntityToUserMapper::convert)
                .orElseThrow(() -> new PhoneNumberNotFoundException("The user with the provided phone number was not found"));
    }

    public UserDTO findByEmail(String email) {
        return repository.findByEmail(email)
                .map(userEntityToUserMapper::convert)
                .orElseThrow(() -> new UsersNotFoundException("The user with the provided id number was not found"));
    }

    public List<UserDTO> getAll() {
        return repository.getAll()
                .stream()
                .map(userEntityToUserMapper::convert)
                .collect(Collectors.toList());
    }

    public void updateCurrentUser(UserDTO user) {
        UserEntity existingEntity = repository.findById(user.getUserID())
                .orElseThrow(() -> new UsersNotFoundException("User not found in DB - Method: updateCurrentUser"));

        updateAfterOrder(existingEntity, user);
        repository.save(existingEntity);
    }

    private void updateAfterOrder(UserEntity existingEntity, UserDTO user) {
        existingEntity.setBalance(user.getBalance());
        existingEntity.setCart(user.getCart());
    }

    public void addToCart(Long productId, int quantity) {
        String cart = CurrentUserService.currentUser.getCart();
        StringBuilder newCart = new StringBuilder();
        boolean isPresent = false;
        String[] productstWithQuantity = cart.split(";");
        if (!cart.equals("")) {
            for (String prod : productstWithQuantity) {
                int delim = prod.indexOf("#");
                if (Long.parseLong(prod.substring(0, delim)) == productId) {
                    isPresent = true;
                    long newQuantity = Long.parseLong(prod.substring(delim + 1)) + quantity;
                    newCart.append(prod.substring(0, delim + 1)).append(newQuantity).append(";");
                } else {
                    newCart.append(prod).append(";");
                }
            }
        }
        if (!isPresent) {
            CurrentUserService.currentUser.setCart(newCart.toString() + productId + "#" + quantity + ";");
        } else {
            CurrentUserService.currentUser.setCart(newCart.toString());
        }
        saveCart(CurrentUserService.currentUser);
    }

    public void updateCart(Long productId) {
        StringBuilder newCart = new StringBuilder();
        String[] productstWithQuantity = CurrentUserService.currentUser.getCart().split(";");
        for (String prod : productstWithQuantity) {
            int delim = prod.indexOf("#");
            if (Long.parseLong(prod.substring(0, delim)) == productId) {

            } else {
                newCart.append(prod).append(";");
            }
        }
        CurrentUserService.currentUser.setCart(newCart.toString());
        saveCart(CurrentUserService.currentUser);
    }

    public void saveCart(UserDTO user) {
        UserEntity existingEntity = repository.findById(user.getUserID())
                .orElseThrow(() -> new UsersNotFoundException("User not found."));
        existingEntity.setCart(user.getCart());
        repository.save(existingEntity);
    }

    public void addBalance(Integer amount) {
        BigDecimal oldBalance = CurrentUserService.currentUser.getBalance();
        CurrentUserService.currentUser.setBalance(oldBalance.add(BigDecimal.valueOf(amount)));
        UserEntity existingEntity = repository.findById(CurrentUserService.currentUser.getUserID())
                .orElseThrow(() -> new UsersNotFoundException("User not found."));
        existingEntity.setBalance(CurrentUserService.currentUser.getBalance());
        repository.save(existingEntity);
    }
}

