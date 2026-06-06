package com.ishalika.SpringJobPortalRevisionJWT.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JWTServiceTest {

    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=");
        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L);
    }

    @Test
    void generateTokenCreatesTokenWithUsername() {
        String token = jwtService.generateToken("ishalika");

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractUserName(token)).isEqualTo("ishalika");
    }

    @Test
    void validateTokenReturnsTrueForMatchingUser() {
        String token = jwtService.generateToken("ishalika");
        UserDetails userDetails = User.withUsername("ishalika")
                .password("hashed-password")
                .authorities("unused")
                .build();

        boolean valid = jwtService.validateToken(token, userDetails);

        assertThat(valid).isTrue();
    }

    @Test
    void validateTokenReturnsFalseForDifferentUser() {
        String token = jwtService.generateToken("ishalika");
        UserDetails userDetails = User.withUsername("other")
                .password("hashed-password")
                .authorities("unused")
                .build();

        boolean valid = jwtService.validateToken(token, userDetails);

        assertThat(valid).isFalse();
    }
}
