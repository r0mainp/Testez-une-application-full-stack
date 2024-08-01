package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class SessionModelTest {
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSessionBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Date sessionDate = new Date();

        Session session = Session.builder()
                .id(1L)
                .name("Name")
                .date(sessionDate)
                .description("Description")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals("Name", session.getName());
        assertEquals(sessionDate, session.getDate());
        assertEquals("Description", session.getDescription());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    public void testSessionSettersAndGetters() {
        Session session = new Session();
        LocalDateTime now = LocalDateTime.now();
        Date sessionDate = new Date();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(user);

        session.setId(1L);
        session.setName("Session Name");
        session.setDate(sessionDate);
        session.setDescription("This is a session description.");
        session.setTeacher(teacher);
        session.setUsers(users);
        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals("Session Name", session.getName());
        assertEquals(sessionDate, session.getDate());
        assertEquals("This is a session description.", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    public void testToString() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Name");

        String expectedToString = "Session(id=1, name=Name, date=null, description=null, teacher=null, users=null, createdAt=null, updatedAt=null)";
        assertEquals(expectedToString, session.toString());
    }
}
