package com.FinTrack.FinTrack.CustomExceptionHandling;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InconsistentData extends RuntimeException {
    String message;

    public InconsistentData(String message) {
        this.message = message;
    }
}
