package it.uniroma1.userManagement.controllers;

import it.uniroma1.userManagement.models.User;
import it.uniroma1.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(Principal principal) {
        List<User> users = new ArrayList<User>();
        users.addAll(this.userRepository.findAll());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User newUser){
        return ResponseEntity.status(201).body(this.userRepository.save(newUser));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = this.userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user, Principal principal) {

        Optional<User> u = this.userRepository.findByUsername(principal.getName());
        if (u.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}",user);
        u.get().setEmail(user.getEmail());
        u.get().setPassword(user.getPassword());
        userRepository.saveAndFlush(u.get());

        return ResponseEntity.status(200).body(u.get());
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(userId);
        if (userData.isPresent()) {
            User userNew = userData.get();
            userNew.setUsername(user.getUsername());
            userNew.setEmail(user.getEmail());
            userNew.setPassword(user.getPassword());
            return new ResponseEntity<>(userRepository.save(userNew), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long userId) {
        try {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
