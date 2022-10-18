package com.example.APISperenza.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "SignupDTO [username=" + username + ", password=" + password + "]";
    }

}
