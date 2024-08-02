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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;


@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TeacherController teacherController;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;

    private Teacher teacher ;
    private TeacherDto teacherDto ;

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