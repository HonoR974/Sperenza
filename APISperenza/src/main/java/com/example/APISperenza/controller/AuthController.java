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
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@CrossOrigin
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

        private final OAuth2AuthorizedClientService authorizedClientService;

        public AuthController(OAuth2AuthorizedClientService authorizedClientService) {
                this.authorizedClientService = authorizedClientService;
        }

        @PostMapping("/register")
        public ResponseEntity<JwtResponseDTO> register(@RequestBody SignupDTO signupDTO) {

                System.out.println("\n signupDTO " + signupDTO.toString());

                User user = new User(signupDTO.getUsername(), signupDTO.getPassword(), signupDTO.getEmail());
                userDetailsManager.createUser(user);

                Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user,
                                signupDTO.getPassword(),
                                Collections.EMPTY_LIST);

                System.out.println("\n authentication register  ");

                TokenDTO tokenDTO = tokenGenerator.createToken(authentication);

                JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(user.getUsername(), tokenDTO.getAccessToken(),
                                tokenDTO.getRefreshToken());
                return new ResponseEntity<>(jwtResponseDTO, HttpStatus.CREATED);
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
                                UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(),
                                                loginDTO.getPassword()));

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

        /*
         * @PostMapping("/signout")
         * public ResponseEntity<?> logoutUser() {
         * UserDetailsImpl userDetails = (UserDetailsImpl)
         * SecurityContextHolder.getContext().getAuthentication()
         * .getPrincipal();
         * Long userId = userDetails.getId();
         * refreshTokenService.deleteByUserId(userId);
         * return ResponseEntity.ok(new MessageResponse("Log out successful!"));
         * }
         */

        @GetMapping(value = "/sucess")
        public String getMethod3Name(Principal user) {
                StringBuffer userInfo = new StringBuffer();

                if (user instanceof OAuth2AuthenticationToken) {

                        System.out.println("\n user instance of OAuth2AuthenticationToken");

                        userInfo.append(getOauth2LoginInfo(user));
                } else if (user instanceof UsernamePasswordAuthenticationToken) {
                        System.out.println("\n user instance of UsernamePasswordAuthenticationToken");
                        userInfo.append(getUsernamePasswordLoginInfo(user));
                        return "Sucess ";
                }
                return userInfo.toString();
        }

        // accéder aux informations protégées de l'utilisateur
        // situées dans le token d’accès
        private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
                StringBuffer usernameInfo = new StringBuffer();

                UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);

                System.out.println("\n token " + token.getPrincipal());

                System.out.println("\n token " + user);

                if (token.isAuthenticated()) {

                        System.out.println("\n token caster " + (User) token.getPrincipal());

                        User u = (User) token.getPrincipal();
                        usernameInfo.append("Welcome , " + u.getUsername());
                } else {
                        usernameInfo.append("NA");
                }
                return usernameInfo;
        }

        /**
         * La classe OAuth2AuthenticationToken contient
         * des méthodes à utiliser pour des ressources protégées,
         * comme celles contenues dans l’objet user .
         */
        private StringBuffer getOauth2LoginInfo(Principal user) {

                StringBuffer protectedInfo = new StringBuffer();

                OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
                OAuth2AuthorizedClient authClient = this.authorizedClientService
                                .loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(),
                                                authToken.getName());
                OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();

                if (authToken.isAuthenticated()) {

                        Map<String, Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal())
                                        .getAttributes();

                        String userToken = authClient.getAccessToken().getTokenValue();
                        protectedInfo.append("Welcome, " + userAttributes.get("name") + "<br><br>");
                        protectedInfo.append("e-mail: " + userAttributes.get("email") + "<br><br>");
                        protectedInfo.append("Access Token: " + userToken + "<br><br>");

                        System.out.println("\n \n userAttributes " + userAttributes.toString());

                        OidcIdToken idToken = getIdToken(principal);

                        System.out.println("\n id token " + idToken.toString());
                        if (idToken != null) {

                                protectedInfo.append("idToken value: " + idToken.getTokenValue() + "<br><br>");
                                protectedInfo.append("Token mapped values <br><br>");

                                Map<String, Object> claims = idToken.getClaims();

                                for (String key : claims.keySet()) {
                                        protectedInfo.append("  " + key + ": " + claims.get(key) + "<br>");
                                }
                        }
                } else {
                        protectedInfo.append("NA");
                }
                return protectedInfo;
        }

        private OidcIdToken getIdToken(OAuth2User principal) {
                if (principal instanceof DefaultOidcUser) {
                        DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
                        return oidcUser.getIdToken();
                }
                return null;
        }

}
