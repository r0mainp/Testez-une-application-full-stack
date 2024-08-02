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

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp(){
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

    @Test
    @WithMockUser
    public void testFindById()  throws Exception{

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

        @Test
        @WithMockUser(username = "test@test.com")
        public void testDelete() throws Exception {
            when(userService.findById(1L)).thenReturn(user);
    
            mockMvc.perform(delete("/api/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
    }
