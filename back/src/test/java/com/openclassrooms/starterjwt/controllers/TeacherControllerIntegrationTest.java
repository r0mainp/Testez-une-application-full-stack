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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser 
    void testFindAll() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = Teacher.builder()
            .id(1L)
            .firstName("Romain")
            .lastName("Portier")
            .createdAt(now)
            .updatedAt(now)
            .build();

        TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(1L);
            teacherDto.setFirstName("Romain");
            teacherDto.setLastName("Portier");
            teacherDto.setCreatedAt(now);
            teacherDto.setUpdatedAt(now);


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
}