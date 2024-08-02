package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // Set the private fields using reflection
        Field secretField = JwtUtils.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(jwtUtils, "testSecretKey");

        Field expirationField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtils, 600000); // 10 minutes
    }

    @Test
    public void testGenerateJwtToken() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
            .username("test@test.com")
            .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        Claims claims = Jwts.parser()
            .setSigningKey("testSecretKey")
            .parseClaimsJws(token)
            .getBody();

        assertEquals("test@test.com", claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        String token = Jwts.builder()
            .setSubject("test@test.com")
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + 600000))
            .signWith(SignatureAlgorithm.HS512, "testSecretKey")
            .compact();

        String username = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals("test@test.com", username);
    }

    @Test
    public void testValidateJwtToken() {
        String token = Jwts.builder()
            .setSubject("test@test.com")
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + 600000))
            .signWith(SignatureAlgorithm.HS512, "testSecretKey")
            .compact();

        boolean isValid = jwtUtils.validateJwtToken(token);

        assertTrue(isValid);
    }
}