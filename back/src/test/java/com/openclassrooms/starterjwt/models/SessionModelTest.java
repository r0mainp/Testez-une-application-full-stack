package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Date;

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


}
