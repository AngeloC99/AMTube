package it.uniroma1.userManagement.controllers;

import it.uniroma1.userManagement.auth.DetailsUser;
import it.uniroma1.userManagement.auth.DetailsUserService;
import it.uniroma1.userManagement.models.User;
import it.uniroma1.userManagement.models.auth.Login;
import it.uniroma1.userManagement.models.auth.Token;
import it.uniroma1.userManagement.repositories.UserRepository;
import it.uniroma1.userManagement.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final DetailsUserService detailsUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, DetailsUserService detailsUserService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.detailsUserService = detailsUserService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> loginAndCreateToken(@Valid @RequestBody Login authenticationRequest) {
        this.logger.info("Login request {}", authenticationRequest);
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch(BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }

        final DetailsUser userDetails = this.detailsUserService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = this.jwtUtil.generateToken(userDetails);

        Token token = new Token(jwt, this.jwtUtil.extractExpiration(jwt));

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        Optional<User> u1 = this.userRepository.findByUsername(user.getUsername());
        Optional<User> u2 = this.userRepository.findByEmail(user.getEmail());
        if (u1.isPresent() || u2.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.userRepository.save(user);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/verification")
    public ResponseEntity<User> verify(Principal principal) {
        try {
            logger.info("User From Verification: " + principal.getName());
            Optional<User> user = this.userRepository.findByUsername(principal.getName());
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }
    }

}
