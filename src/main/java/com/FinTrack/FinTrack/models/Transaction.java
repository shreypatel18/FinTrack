package com.FinTrack.FinTrack.models;


import com.FinTrack.FinTrack.Service.TimeService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


//to record transaction
@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    Integer tranId;

    LocalDate date1;


    String category;

    @NotNull
    @Positive
    Integer amount;

    String Description;

    String name;

    @ManyToOne(cascade = CascadeType.ALL)
    UserDetails userDetails;
}


