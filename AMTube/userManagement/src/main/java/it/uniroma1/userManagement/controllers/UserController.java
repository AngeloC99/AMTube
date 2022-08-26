package it.uniroma1.userManagement.controllers;

import it.uniroma1.userManagement.models.User;
import it.uniroma1.userManagement.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = new ArrayList<>();
        users.addAll(this.userRepository.findAll());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User newUser){
        return ResponseEntity.status(201).body(this.userRepository.save(newUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = this.userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping
    public ResponseEntity<?> getUserInfo(Principal principal) {
        Optional<User> user = this.userRepository.findByUsername(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(200).body(user.get());
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user, Principal principal) {
        Optional<User> u = this.userRepository.findByUsername(principal.getName());
        if (u.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}",user);
        if(user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            u.get().setUsername(user.getUsername());
        }
        if(user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            u.get().setEmail(user.getEmail());
        }
        if(user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            u.get().setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userRepository.saveAndFlush(u.get());

        return ResponseEntity.status(200).body(u.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long userId) {
        try {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserLogged(Principal principal) {
        try {
            this.userRepository.deleteByUsername(principal.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
