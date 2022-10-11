package com.example.APISperenza.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    private long id;

    private String name;

    // contrainte : 2 apres la virgule
    private float prix;

    private float note;

}
