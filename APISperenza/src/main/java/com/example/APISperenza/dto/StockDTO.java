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
public class StockDTO {

    private long id;

    private String name;

    @Nullable
    private List<ResourceDTO> lResources;
}
