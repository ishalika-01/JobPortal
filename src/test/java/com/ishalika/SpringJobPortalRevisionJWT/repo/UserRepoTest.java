package com.ishalika.SpringJobPortalRevisionJWT.repo;

import com.ishalika.SpringJobPortalRevisionJWT.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    void findByUsernameReturnsUserWhenPresent() {
        User user = new User();
        user.setId(1);
        user.setUsername("ishalika");
        user.setPassword("hashed-password");
        userRepo.save(user);

        User result = userRepo.findByUsername("ishalika");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("ishalika");
    }

    @Test
    void findByUsernameReturnsNullWhenMissing() {
        User result = userRepo.findByUsername("missing");

        assertThat(result).isNull();
    }
}
