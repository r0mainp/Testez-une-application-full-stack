package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link Teacher} class.
 * 
 * This class tests the functionality of the {@link Teacher} methods,
 * including the builder pattern, getters and setters, and equals,
 * and hashCode methods.
 */
public class TeacherModelTest {

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    /**
     * Tests the builder pattern of the {@link Teacher} class.
     * 
     * This method verifies that the builder sets all fields correctly and
     * checks the string representation of the builder.
     */
    @Test
    public void testTeacherBuilder() {
        LocalDateTime now = LocalDateTime.now();

        Teacher.TeacherBuilder builder = Teacher.builder()
                .id(1L)
                .lastName("Portier")
                .firstName("Romain")
                .createdAt(now)
                .updatedAt(now);

        Teacher teacher = builder.build();

        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("Portier", teacher.getLastName());
        assertEquals("Romain", teacher.getFirstName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());

        String teacherString = builder.toString();
        assertNotNull(teacherString);
        assertTrue(teacherString.contains("Teacher.TeacherBuilder(id=1"));
        assertTrue(teacherString.contains("lastName=Portier"));
        assertTrue(teacherString.contains("firstName=Romain"));
        assertTrue(teacherString.contains("createdAt="));
        assertTrue(teacherString.contains("updatedAt="));
    }

    /**
     * Tests the getters and setters of the {@link Teacher} class.
     * 
     * This method verifies that the setters correctly set the fields
     * and the getters return the expected values.
     */
    @Test
    public void testTeacherSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Portier");
        teacher.setFirstName("Romain");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("Portier", teacher.getLastName());
        assertEquals("Romain", teacher.getFirstName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());
    }

    /**
     * Tests the {@link Teacher#equals(Object)} and {@link Teacher#hashCode()} methods.
     * 
     * This method verifies that two teachers with the same id are considered equal and have the same hash code,
     * and two teachers with different ids are not equal and have different hash codes.
     */
    @Test
    public void testEqualsAndHashCode() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .build();

        Teacher teacher3 = Teacher.builder()
                .id(2L)
                .lastName("Smith")
                .firstName("Jane")
                .build();

        assertEquals(teacher1, teacher2);
        assertNotEquals(teacher1, teacher3);

        assertEquals(teacher1.hashCode(), teacher2.hashCode());
        assertNotEquals(teacher1.hashCode(), teacher3.hashCode());
    }
}