package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

/**
 * Unit tests for the {@link SessionMapperImpl} class.
 * <p>
 * These tests ensure that the methods in {@link SessionMapperImpl} correctly map between {@link SessionDto}
 * and {@link Session} objects, and handle lists of these objects appropriately.
 * </p>
 */
public class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the conversion of a list of {@link SessionDto} objects to a list of {@link Session} objects.
     * <p>
     * This test creates two {@link SessionDto} objects with associated {@link Teacher} and {@link User} objects,
     * mocks the service layer responses, performs the mapping, and then verifies that the resulting {@link Session}
     * objects have the correct properties and relationships.
     * </p>
     */
    @Test
    public void testToEntityList() {
        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setDescription("Description session 1");
        sessionDto1.setTeacher_id(1L);
        sessionDto1.setUsers(Arrays.asList(1L, 2L));

        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setDescription("Description session 2");
        sessionDto2.setTeacher_id(2L);
        sessionDto2.setUsers(Arrays.asList(3L, 4L));

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);

        User user3 = new User();
        user3.setId(3L);
        User user4 = new User();
        user4.setId(4L);

        when(teacherService.findById(1L)).thenReturn(teacher1);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);
        when(teacherService.findById(2L)).thenReturn(teacher2);
        when(userService.findById(3L)).thenReturn(user3);
        when(userService.findById(4L)).thenReturn(user4);

        List<SessionDto> sessionDtos = Arrays.asList(sessionDto1, sessionDto2);

        List<Session> sessions = sessionMapper.toEntity(sessionDtos);

        assertEquals(2, sessions.size());

        Session session1 = sessions.get(0);
        assertEquals("Description session 1", session1.getDescription());
        assertEquals(1L, session1.getTeacher().getId());
        assertEquals(2, session1.getUsers().size());
        assertTrue(session1.getUsers().stream().anyMatch(user -> user.getId().equals(1L)));
        assertTrue(session1.getUsers().stream().anyMatch(user -> user.getId().equals(2L)));

        Session session2 = sessions.get(1);
        assertEquals("Description session 2", session2.getDescription());
        assertEquals(2L, session2.getTeacher().getId());
        assertEquals(2, session2.getUsers().size());
        assertTrue(session2.getUsers().stream().anyMatch(user -> user.getId().equals(3L)));
        assertTrue(session2.getUsers().stream().anyMatch(user -> user.getId().equals(4L)));
    }

    /**
     * Tests the conversion of a list of {@link Session} objects to a list of {@link SessionDto} objects.
     * <p>
     * This test creates two {@link Session} objects with associated {@link Teacher} and {@link User} objects,
     * performs the mapping to {@link SessionDto} objects, and then verifies that the resulting DTOs have the correct
     * properties and relationships.
     * </p>
     */
    @Test
    public void testToDtoList() {
        // Create first session
        Session session1 = new Session();
        session1.setDescription("Description session 1");

        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        session1.setTeacher(teacher1);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        session1.setUsers(Arrays.asList(user1, user2));

        // Create second Session
        Session session2 = new Session();
        session2.setDescription("Description session 2");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        session2.setTeacher(teacher2);

        User user3 = new User();
        user3.setId(3L);
        User user4 = new User();
        user4.setId(4L);
        session2.setUsers(Arrays.asList(user3, user4));

        // Make a list with sessions
        List<Session> sessions = Arrays.asList(session1, session2);

        // Call toDto with
        List<SessionDto> sessionDtos = sessionMapper.toDto(sessions);

        // Do the checks
        SessionDto dto1 = sessionDtos.get(0);
        assertEquals("Description session 1", dto1.getDescription());
        assertEquals(1L, dto1.getTeacher_id());
        assertEquals(2, dto1.getUsers().size());
        assertTrue(dto1.getUsers().contains(1L));
        assertTrue(dto1.getUsers().contains(2L));

        SessionDto dto2 = sessionDtos.get(1);
        assertEquals("Description session 2", dto2.getDescription());
        assertEquals(2L, dto2.getTeacher_id());
        assertEquals(2, dto2.getUsers().size());
        assertTrue(dto2.getUsers().contains(3L));
        assertTrue(dto2.getUsers().contains(4L));
    }
}