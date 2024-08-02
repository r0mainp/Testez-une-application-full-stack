package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
            .id(1L)
            .email("test@test.com")
            .firstName("Romain")
            .lastName("Portier")
            .password("password1234")
            .build();
    }

    @Test
    public void testLoadUserByUsername_Success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@test.com");

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getFirstName(), ((UserDetailsImpl) userDetails).getFirstName());
        assertEquals(user.getLastName(), ((UserDetailsImpl) userDetails).getLastName());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> 
            userDetailsService.loadUserByUsername("test@test.com")
        );
    }
}