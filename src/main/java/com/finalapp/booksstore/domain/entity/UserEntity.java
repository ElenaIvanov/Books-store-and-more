package com.finalapp.booksstore.domain.entity;

import com.finalapp.booksstore.validator.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "userid", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long userID;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    private String password;

    private String email;

    private BigDecimal balance;

    @Column(name = "usertype", nullable = false)
    private Long userType;

    @Column(name = "fidelitypoints", nullable = false)
    private Long fidelityPoints;

    private String address;

    private String cart;

    @PhoneNumber
    private String phoneNumber;


}
