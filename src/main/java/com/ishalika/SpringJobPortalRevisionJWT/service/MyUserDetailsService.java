package com.ishalika.SpringJobPortalRevisionJWT.service;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import com.ishalika.SpringJobPortalRevisionJWT.model.UserPrinciple;
import com.ishalika.SpringJobPortalRevisionJWT.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
   private UserRepo repo;
//    private UserPrinciple userPrincipal;
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user=  repo.findByUsername(username);
      if(user==null)
      {
          System.out.println("user not found");
          throw new UsernameNotFoundException(username);
      }

          return new UserPrinciple(user) ;
    }
}
