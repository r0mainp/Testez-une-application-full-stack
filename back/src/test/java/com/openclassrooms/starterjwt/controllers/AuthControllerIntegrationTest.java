package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtils jwtUtils;

    private ObjectMapper objectMapper;
    private User user;
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Romain");
        user.setLastName("Portier");
        user.setPassword("password1234");
        user.setAdmin(false);
        user.setId(1L);

        userDetails = UserDetailsImpl.builder()
            .id(1L)
            .username("test@test.com")
            .firstName("Romain")
            .lastName("Portier")
            .admin(false)
            .password("password1234")
            .build();
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("password1234");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())))
            .thenReturn(authentication);
        
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mock-jwt-token");

        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("mock-jwt-token"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("test@test.com"))
            .andExpect(jsonPath("$.firstName").value("Romain"))
            .andExpect(jsonPath("$.lastName").value("Portier"))
            .andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    public void testRegisterUser() throws Exception {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setFirstName("Romain");
        signUpRequest.setLastName("Portier");
        signUpRequest.setPassword("password1234");

        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);

        mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
}
