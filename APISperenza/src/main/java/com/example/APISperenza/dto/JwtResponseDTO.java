package com.example.APISperenza.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtResponseDTO {

    private String username;
    private String accessToken;
    private String refreshToken;

    public JwtResponseDTO(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
