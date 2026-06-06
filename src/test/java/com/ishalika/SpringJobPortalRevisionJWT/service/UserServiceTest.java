package com.ishalika.SpringJobPortalRevisionJWT.service;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import com.ishalika.SpringJobPortalRevisionJWT.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void saveUserEncodesPasswordBeforeSaving() {
        User user = new User();
        user.setId(1);
        user.setUsername("ishalika");
        user.setPassword("plain-password");

        when(encoder.encode("plain-password")).thenReturn("hashed-password");
        when(repo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getPassword()).isEqualTo("hashed-password");
        verify(encoder).encode("plain-password");
        verify(repo).save(user);
    }

    @Test
    void findByUserNameReturnsTrueWhenUserExists() {
        User user = new User();
        user.setUsername("ishalika");
        when(repo.findByUsername("ishalika")).thenReturn(user);

        boolean exists = userService.findByUserName("ishalika");

        assertThat(exists).isTrue();
    }

    @Test
    void findByUserNameReturnsFalseWhenUserDoesNotExist() {
        when(repo.findByUsername("missing")).thenReturn(null);

        boolean exists = userService.findByUserName("missing");

        assertThat(exists).isFalse();
    }
}
