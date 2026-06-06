package com.ishalika.SpringJobPortalRevisionJWT.service;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import com.ishalika.SpringJobPortalRevisionJWT.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder encoder;

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }
    public boolean findByUserName(String username) {

        User user= repo.findByUsername(username);
        return user != null;
    }

}
