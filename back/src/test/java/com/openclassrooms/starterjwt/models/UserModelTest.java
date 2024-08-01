package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class UserModelTest {
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserBuilder() {
        LocalDateTime now = LocalDateTime.now();

        User.UserBuilder builder = User.builder()
                .id(1L)
                .email("test@example.com")
                .lastName("Romain")
                .firstName("Portier")
                .password("password123")
                .admin(true)
                .createdAt(now)
                .updatedAt(now);


        User user = builder.build();
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Romain", user.getLastName());
        assertEquals("Portier", user.getFirstName());
        assertEquals("password123", user.getPassword());
        assertEquals(true, user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());


        String userString = builder.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("User.UserBuilder(id=1"));
        assertTrue(userString.contains("email=test@example.com"));
        assertTrue(userString.contains("lastName=Romain"));
        assertTrue(userString.contains("firstName=Portier"));
        assertTrue(userString.contains("password=password123"));
        assertTrue(userString.contains("admin=true"));
        assertTrue(userString.contains("createdAt="));
        assertTrue(userString.contains("updatedAt="));
    }
}
