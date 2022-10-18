package com.example.APISperenza.dto;

import com.example.APISperenza.model.User;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private long id;
    private String username;

    public static UserDTO from(User user) {
        return builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
