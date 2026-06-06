package com.ishalika.SpringJobPortalRevisionJWT.repo;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
