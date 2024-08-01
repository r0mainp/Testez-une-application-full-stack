package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

/**
 * Unit tests for the {@link SessionService} class.
 * 
 * This class tests the functionality of the {@link SessionService} methods by mocking dependencies
 * and verifying interactions and expected behaviors.
 */
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link SessionService#findAll()} method.
     * 
     * Verifies that the {@link SessionRepository#findAll()} method is called once and that the result
     * from the service is not null.
     */
    @Test
    public void testFindAllSessions() {
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> result = sessionService.findAll();
        assertNotNull(result, "The result of findAll should not be null.");
        verify(sessionRepository, times(1)).findAll();
    }

    /**
     * Tests the {@link SessionService#getById(Long)} method.
     * 
     * Verifies that the {@link SessionRepository#findById(Long)} method is called once with the correct ID
     * and that the result from the service is not null.
     */
    @Test
    public void testGetById() {
        Session session = new Session();
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        Session result = sessionService.getById(1L);
        assertNotNull(result, "The result of getById should not be null.");
        verify(sessionRepository, times(1)).findById(1L);
    }

    /**
     * Tests the {@link SessionService#create(Session)} method.
     * 
     * Verifies that the {@link SessionRepository#save(Session)} method is called once with any Session object
     * and that the result from the service is not null.
     */
    @Test
    public void testCreateSession() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        Session createdSession = sessionService.create(session);
        assertNotNull(createdSession, "The created session should not be null.");
        verify(sessionRepository, times(1)).save(session);
    }

    /**
     * Tests the {@link SessionService#update(Long, Session)} method.
     * 
     * Verifies that the {@link SessionRepository#save(Session)} method is called once with any Session object
     * and that the updated session has the correct ID.
     */
    @Test
    public void testUpdateSession(){
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        Session updatedSession = sessionService.update(1L, session);
        assertNotNull(updatedSession, "The updated session should not be null.");
        assertEquals(session.getId(), updatedSession.getId(), "The session ID should match.");
    }

    /**
     * Tests the {@link SessionService#delete(Long)} method.
     * 
     * Verifies that the {@link SessionRepository#deleteById(Long)} method is called once with the correct ID.
     */
    @Test
    public void testDeleteSession() {
        doNothing().when(sessionRepository).deleteById(anyLong());
        sessionService.delete(1L);
        verify(sessionRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests the {@link SessionService#participate(Long, Long)} method.
     * 
     * Verifies that a user is added to a session and that the repository methods are called with the correct parameters.
     */
    @Test
    public void testParticipate(){
        Session session = new Session();
        session.setUsers(new ArrayList<>());

        User user = new User();
        user.setId(1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        sessionService.participate(1L, 1L);

        assertTrue(session.getUsers().contains(user), "The user should be added to the session.");
        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).save(session);
    }

    /**
     * Tests the {@link SessionService#noLongerParticipate(Long, Long)} method.
     * 
     * Verifies that a user is removed from a session and that the repository methods are called with the correct parameters.
     */
    @Test
    public void testNoLongerParticipate(){
        Session session = new Session();
        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        sessionService.noLongerParticipate(1L, 1L);

        assertFalse(session.getUsers().contains(user), "The user should be removed from the session.");
        verify(sessionRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).save(session);
    }

    /**
     * Tests the {@link SessionService#participate(Long, Long)} method when the user is already participating.
     * 
     * Verifies that a {@link BadRequestException} is thrown when trying to add a user who is already participating in the session.
     */
    @Test
    public void testParticipate_WhenUserAlreadyParticipate(){
        Session session = new Session();
        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L), "Exception should be thrown when user already participates.");
    }

    /**
     * Tests the {@link SessionService#noLongerParticipate(Long, Long)} method when the user is not participating.
     * 
     * Verifies that a {@link BadRequestException} is thrown when trying to remove a user who is not participating in the session.
     */
    @Test
    public void testNoLongerParticipate_WhenUserNotParticipating() {
        Session session = new Session();
        session.setUsers(new ArrayList<>());

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 1L), "Exception should be thrown when user is not participating.");
    }
}