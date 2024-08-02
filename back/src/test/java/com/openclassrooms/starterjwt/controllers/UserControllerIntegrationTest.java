package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

/**
 * Integration tests for the {@link UserController} class.
 * <p>
 * This class contains test cases for the various endpoints of the {@link UserController}
 * using Spring Boot's MockMvc framework. It mocks dependencies and verifies the correct
 * functionality of the controller's methods.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    private User user;
    private UserDto userDto;

    /**
     * Sets up the test environment before each test method.
     * Initializes {@link User} and {@link UserDto} instances with sample data
     * to be used in the test cases.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        LocalDateTime now = LocalDateTime.now();
        user = User.builder()
            .id(1L)
            .email("test@test.com")
            .firstName("Romain")
            .lastName("Portier")
            .password("password1234")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setFirstName("Romain");
        userDto.setLastName("Portier");
        userDto.setPassword("password1234");
        userDto.setCreatedAt(now);
        userDto.setUpdatedAt(now);
    }

    /**
     * Tests the {@link UserController#findById(Long)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly retrieves a user by its ID
     * and returns the expected data in JSON format, excluding sensitive information such as password.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testFindById() throws Exception {
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mockMvc.perform(get("/api/user/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.email").value("test@test.com"))
            .andExpect(jsonPath("$.firstName").value("Romain"))
            .andExpect(jsonPath("$.lastName").value("Portier"))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    /**
     * Tests the {@link UserController#deleteUser(Long)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly deletes a user by its ID
     * and returns a successful status response.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser(username = "test@test.com")
    public void testDelete() throws Exception {
        when(userService.findById(1L)).thenReturn(user);
    
        mockMvc.perform(delete("/api/user/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}