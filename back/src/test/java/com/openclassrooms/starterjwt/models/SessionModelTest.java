package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link Session} class.
 * 
 * This class tests the functionality of the {@link Session} methods,
 * including the builder pattern, getters and setters, toString, equals,
 * and hashCode methods.
 */
public class SessionModelTest {

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the builder pattern of the {@link Session} class.
     * 
     * This method verifies that the builder sets all fields correctly and
     * checks the string representation of the builder.
     */
    @Test
    public void testSessionBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Date sessionDate = new Date();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(user);

        Session.SessionBuilder builder = Session.builder()
                .id(1L)
                .name("Name")
                .date(sessionDate)
                .description("Description")
                .teacher(teacher)
                .users(users)
                .createdAt(now)
                .updatedAt(now);

        Session session = builder.build();
        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals("Name", session.getName());
        assertEquals(sessionDate, session.getDate());
        assertEquals("Description", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
        
        String builderString = builder.toString();
        assertNotNull(builderString);
        assertTrue(builderString.contains("Session.SessionBuilder(id=1"));
        assertTrue(builderString.contains("name=Name"));
        assertTrue(builderString.contains("description=Description"));
        assertTrue(builderString.contains("teacher=Teacher(id=1"));
        assertTrue(builderString.contains("users=[User(id=1"));
        assertTrue(builderString.contains("createdAt="));
        assertTrue(builderString.contains("updatedAt="));
    }

    /**
     * Tests the getters and setters of the {@link Session} class.
     * 
     * This method verifies that the setters correctly set the fields
     * and the getters return the expected values.
     */
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

    /**
     * Tests the {@link Session#toString()} method.
     * 
     * This method verifies that the toString method returns the expected string representation.
     */
    @Test
    public void testToString() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Name");

        String expectedToString = "Session(id=1, name=Name, date=null, description=null, teacher=null, users=null, createdAt=null, updatedAt=null)";
        assertEquals(expectedToString, session.toString());
    }

    /**
     * Tests the {@link Session#equals(Object)} and {@link Session#hashCode()} methods.
     * 
     * This method verifies that two sessions with the same id are considered equal and have the same hash code,
     * and two sessions with different ids are not equal and have different hash codes.
     */
    @Test
    public void testEqualsAndHashCode() {
        Session session1 = new Session();
        session1.setId(1L);

        Session session2 = new Session();
        session2.setId(1L);

        assertEquals(session1, session2);
        assertEquals(session1.hashCode(), session2.hashCode());

        session2.setId(2L);
        assertNotEquals(session1, session2);
        assertNotEquals(session1.hashCode(), session2.hashCode());
    }
}