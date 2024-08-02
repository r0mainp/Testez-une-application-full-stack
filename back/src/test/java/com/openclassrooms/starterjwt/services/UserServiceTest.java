package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

/**
 * Unit tests for the {@link UserService} class.
 * 
 * This class tests the functionality of the {@link UserService} methods by mocking the {@link UserRepository}
 * and verifying interactions and expected behaviors.
 */
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link UserService#findById(Long)} method.
     * 
     * Verifies that the {@link UserRepository#findById(Long)} method is called once with the correct ID
     * and that the result from the service is not null.
     */
    @Test
    public void testGetUserById(){
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User fetchedUser = userService.findById(1L);
        assertNotNull(fetchedUser, "The fetched user should not be null.");
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests the {@link UserService#delete(Long)} method.
     * 
     * Verifies that the {@link UserRepository#deleteById(Long)} method is called once with the correct ID.
     */
    @Test
    public void testDeleteUser(){
        doNothing().when(userRepository).deleteById(anyLong());
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}