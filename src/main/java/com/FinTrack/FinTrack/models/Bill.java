package com.FinTrack.FinTrack.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


//blueprint for light bill, electricitybillm, phone bills,monthlybills
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bill {

    public Bill(int amount, Boolean status, String category) {
        this.category = category;
        this.status = status;
        this.amount = amount;
    }

    @Id
    @GeneratedValue
    public int billId;
    public int amount;
    public Boolean status;
    public String category;

    @ManyToOne(cascade = CascadeType.ALL)
    UserDetails userDetails;
}
