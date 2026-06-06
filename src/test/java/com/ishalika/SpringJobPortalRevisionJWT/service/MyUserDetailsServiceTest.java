package com.ishalika.SpringJobPortalRevisionJWT.service;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import com.ishalika.SpringJobPortalRevisionJWT.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {

    @Mock
    private UserRepo repo;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Test
    void loadUserByUsernameReturnsUserDetailsWhenUserExists() {
        User user = new User();
        user.setUsername("ishalika");
        user.setPassword("hashed-password");
        when(repo.findByUsername("ishalika")).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("ishalika");

        assertThat(userDetails.getUsername()).isEqualTo("ishalika");
        assertThat(userDetails.getPassword()).isEqualTo("hashed-password");
        assertThat(userDetails.getAuthorities()).isEmpty();
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUserIsMissing() {
        when(repo.findByUsername("missing")).thenReturn(null);

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("missing"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("missing");
    }
}
