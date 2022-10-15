package com.example.APISperenza.dto;

import java.util.List;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class FournisseurDTO {

    private long id;

    private String name;

    private String adresse;

    // constraint 5
    private String codePostal;

    // constaint 10
    private String numeroTelephone;

    @Nullable
    private List<ResourceDTO> lResources;

}
