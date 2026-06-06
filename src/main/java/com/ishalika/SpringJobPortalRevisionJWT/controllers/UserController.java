package com.ishalika.SpringJobPortalRevisionJWT.controllers;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import com.ishalika.SpringJobPortalRevisionJWT.service.JWTService;
import com.ishalika.SpringJobPortalRevisionJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins="http://localhost:5173")
public class UserController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService service;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if(service.findByUserName(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        service.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

    }
    @PostMapping("login")
    public String login(@RequestBody User user) {
         Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(auth.isAuthenticated())
        {
            return jwtService.generateToken(user.getUsername());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

}
