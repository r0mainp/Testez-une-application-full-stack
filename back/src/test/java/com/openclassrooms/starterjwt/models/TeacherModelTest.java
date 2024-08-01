package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class TeacherModelTest {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
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
}
