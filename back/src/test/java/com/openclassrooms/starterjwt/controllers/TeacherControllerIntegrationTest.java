package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

/**
 * Integration tests for the {@link TeacherController} class.
 * <p>
 * This class contains test cases for the various endpoints of the {@link TeacherController} 
 * using Spring Boot's MockMvc framework. It mocks dependencies and verifies the correct 
 * functionality of the controller's methods.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;

    private Teacher teacher;
    private TeacherDto teacherDto;

    /**
     * Sets up the test environment before each test method.
     * Initializes {@link Teacher} and {@link TeacherDto} instances with sample data
     * to be used in the test cases.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        LocalDateTime now = LocalDateTime.now();
        teacher = Teacher.builder()
            .id(1L)
            .firstName("Romain")
            .lastName("Portier")
            .createdAt(now)
            .updatedAt(now)
            .build();

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("Romain");
        teacherDto.setLastName("Portier");
        teacherDto.setCreatedAt(now);
        teacherDto.setUpdatedAt(now);
    }

    /**
     * Tests the {@link TeacherController#findAll()} endpoint.
     * <p>
     * This test verifies that the endpoint correctly retrieves a list of all teachers
     * and returns the expected data in JSON format.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    void testFindAll() throws Exception {
        List<Teacher> teachers = Collections.singletonList(teacher);
        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(Collections.singletonList(teacherDto));

        mockMvc.perform(get("/api/teacher"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].firstName").value("Romain"))
            .andExpect(jsonPath("$[0].lastName").value("Portier"));
    }

    /**
     * Tests the {@link TeacherController#findById(Long)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly retrieves a teacher by its ID
     * and returns the expected data in JSON format.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testFindById() throws Exception {
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        mockMvc.perform(get("/api/teacher/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("Romain"))
            .andExpect(jsonPath("$.lastName").value("Portier"));
    }
}