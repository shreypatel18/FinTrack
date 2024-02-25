package com.FinTrack.FinTrack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.validation.annotation.Validated;


import java.util.ArrayList;
import java.util.List;

//stores all details of users

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

    @Id
    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotNull
    @Positive
    public int Balance;

    public int BalanceCurr;
    @NotNull
    @Positive
    public int foodBalance;

    public int foodBalanceCurr;
    @NotNull
    @Positive
    public int tranBalance;

    public int TranBalanceCurr;
    @NotNull
    @Positive
    public int miscllBalance;
    public int miscllBalanceCurr;

    @Embedded
    Image image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userDetails")
    public List<Bill> bills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userDetails")
    public List<Transaction> transactions = new ArrayList<>();


}

