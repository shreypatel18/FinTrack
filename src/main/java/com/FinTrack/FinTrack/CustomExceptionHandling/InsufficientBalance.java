package com.FinTrack.FinTrack.CustomExceptionHandling;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientBalance extends RuntimeException {
    public String message;

    public InsufficientBalance(String message) {
        this.message = message;
    }
}
