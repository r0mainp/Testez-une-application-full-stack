package com.openclassrooms.starterjwt.mapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

public class SessionMapperTest {
    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntity(){
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(1L, 2L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        Session session = sessionMapper.toEntity(sessionDto);

        assertNotNull(session);
        assertEquals("Description", session.getDescription());
        assertNotNull(session.getTeacher());
        assertEquals(2, session.getUsers().size());
    }

    @Test
    public void testToDtoList(){
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

        //Make a list with sessions
        List<Session> sessions = Arrays.asList(session1, session2);

        // Call toDto with
        List<SessionDto> sessionDtos = sessionMapper.toDto(sessions);

        //Do the checks
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
