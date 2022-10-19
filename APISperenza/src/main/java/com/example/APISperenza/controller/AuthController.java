package com.example.APISperenza.controller;

import com.example.APISperenza.model.User;
import com.example.APISperenza.repository.UserRepository;
import com.example.APISperenza.dto.JwtResponseDTO;
import com.example.APISperenza.dto.LoginDTO;
import com.example.APISperenza.dto.SignupDTO;
import com.example.APISperenza.dto.TokenDTO;
import com.example.APISperenza.dto.UserDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.security.TokenGenerator;
import com.example.APISperenza.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    UserManager userManager;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDTO signupDTO) {

        System.out.println("\n signupDTO " + signupDTO.toString());

        User user = new User(signupDTO.getUsername(), signupDTO.getPassword());
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(),
                Collections.EMPTY_LIST);

        System.out.println("\n authentication register  ");

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    //
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        System.out.println("\n login " + loginDTO.toString());

        UserDetails user = userManager.loadUserByUsername(loginDTO.getUsername());

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user,
                loginDTO.getPassword(), Collections.EMPTY_LIST);

        System.out.println("\n authentication login ");

        TokenDTO tokenDTO = tokenGenerator.createToken(authentication);

        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(loginDTO.getUsername(), tokenDTO.getAccessToken(),
                tokenDTO.getRefreshToken());

        System.out.println("\n jwtResponseDTO " + jwtResponseDTO.toString());

        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDTO> signin(@RequestBody LoginDTO loginDTO) throws Exception {

        System.out.println("\n singin  " + loginDTO.toString());

        Authentication authentication = daoAuthenticationProvider.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));

        System.out.println("\n authentication  " + loginDTO.toString());

        TokenDTO tokenDTO = tokenGenerator.createToken(authentication);

        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(loginDTO.getUsername(), tokenDTO.getAccessToken(),
                tokenDTO.getRefreshToken());

        System.out.println("\n jwtResponseDTO " + jwtResponseDTO.toString());
        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.ACCEPTED);

    }

    @PostMapping("/token")
    public ResponseEntity token(@RequestBody TokenDTO tokenDTO) {
        Authentication authentication = refreshTokenAuthProvider
                .authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
