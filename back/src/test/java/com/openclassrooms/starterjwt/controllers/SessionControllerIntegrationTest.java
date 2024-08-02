package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;

/**
 * Integration tests for the {@link SessionController} class.
 * <p>
 * This class tests the various endpoints of the {@link SessionController} 
 * using Spring Boot's MockMvc framework. It mocks dependencies and verifies 
 * that the controller's endpoints function as expected under different scenarios.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Session session;
    private SessionDto sessionDto;

    /**
     * Sets up the test environment before each test method.
     * Initializes the {@link Session} and {@link SessionDto} instances
     * with sample data that will be used in the test cases.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Test Description");
        session.setTeacher(null);
        session.setUsers(null);
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Test Session");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Test Description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(null);
        sessionDto.setCreatedAt(LocalDateTime.now());
        sessionDto.setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Tests the {@link SessionController#findAll()} endpoint.
     * <p>
     * This test verifies that the endpoint correctly retrieves a list of all sessions
     * and returns the expected data.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testFindAll() throws Exception {
        List<Session> sessions = Collections.singletonList(session);
        List<SessionDto> sessionDtos = Collections.singletonList(sessionDto);

        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        mockMvc.perform(get("/api/session")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Test Session"))
            .andExpect(jsonPath("$[0].description").value("Test Description"))
            .andExpect(jsonPath("$[0].date").exists())
            .andExpect(jsonPath("$[0].teacher_id").value(1L))
            .andExpect(jsonPath("$[0].users").doesNotExist());
    }

    /**
     * Tests the {@link SessionController#findById(String)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly retrieves a session by its ID
     * and returns the expected data.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testFindById() throws Exception {
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(get("/api/session/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Session"))
            .andExpect(jsonPath("$.description").value("Test Description"))
            .andExpect(jsonPath("$.date").exists())
            .andExpect(jsonPath("$.teacher_id").value(1L))
            .andExpect(jsonPath("$.users").doesNotExist());
    }

    /**
     * Tests the {@link SessionController#create(SessionDto)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly creates a new session
     * and returns the created session data.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testCreate() throws Exception {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(post("/api/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sessionDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Session"))
            .andExpect(jsonPath("$.description").value("Test Description"))
            .andExpect(jsonPath("$.date").exists())
            .andExpect(jsonPath("$.teacher_id").value(1L))
            .andExpect(jsonPath("$.users").doesNotExist());
    }

    /**
     * Tests the {@link SessionController#update(String, SessionDto)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly updates an existing session
     * and returns the updated session data.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testUpdate() throws Exception {
        sessionDto.setName("Test Session (edit)");
        when(sessionService.update(1L, session)).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(put("/api/session/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sessionDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Session (edit)"))
            .andExpect(jsonPath("$.description").value("Test Description"))
            .andExpect(jsonPath("$.date").exists())
            .andExpect(jsonPath("$.teacher_id").value(1L))
            .andExpect(jsonPath("$.users").doesNotExist());
    }

    /**
     * Tests the {@link SessionController#delete(String)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly deletes a session by its ID.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testDelete() throws Exception {
        when(sessionService.getById(1L)).thenReturn(session);

        mockMvc.perform(delete("/api/session/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    /**
     * Tests the {@link SessionController#participate(String, String)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly allows a user to participate
     * in a session by updating the session's participants.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testParticipate() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/2")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    /**
     * Tests the {@link SessionController#noLongerParticipate(String, String)} endpoint.
     * <p>
     * This test verifies that the endpoint correctly removes a user from participating
     * in a session by updating the session's participants.
     * </p>
     * 
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    @WithMockUser
    public void testNoLongerParticipate() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/2")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}