package com.example.APISperenza.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private String name;

    private float prix;

    private float note;

}
