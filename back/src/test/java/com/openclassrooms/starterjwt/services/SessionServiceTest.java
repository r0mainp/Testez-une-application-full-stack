package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;

public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllSessions() {
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> result = sessionService.findAll();
        assertNotNull(result);
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void testGetById() {
        Session session = new Session();
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        Session result = sessionService.getById(1L);
        assertNotNull(result);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateSession() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        Session createdSession = sessionService.create(session);
        assertNotNull(createdSession);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testUpdateSession(){
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        Session updatedSession = sessionService.update(1L,session);
        assertNotNull(updatedSession);
        assertEquals(session.getId(), updatedSession.getId());
    }

}
