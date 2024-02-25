package com.FinTrack.FinTrack.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//used to store user image

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Image {
    @Lob
    @Column(length = 512000) // 500KB
    byte[] img;
}
