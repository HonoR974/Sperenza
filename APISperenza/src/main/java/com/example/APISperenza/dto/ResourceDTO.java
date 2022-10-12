package com.example.APISperenza.dto;

import lombok.Data;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ResourceDTO {

    private long id;

    private String name;

    private float largeur;

    private float longueur;

}
