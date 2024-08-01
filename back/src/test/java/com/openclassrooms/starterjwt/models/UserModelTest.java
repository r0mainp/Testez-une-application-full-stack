package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                .email("test@test.com")
                .lastName("Romain")
                .firstName("Portier")
                .password("password123")
                .admin(true)
                .createdAt(now)
                .updatedAt(now);


        User user = builder.build();
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("Romain", user.getLastName());
        assertEquals("Portier", user.getFirstName());
        assertEquals("password123", user.getPassword());
        assertEquals(true, user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());


        String userString = builder.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("User.UserBuilder(id=1"));
        assertTrue(userString.contains("email=test@test.com"));
        assertTrue(userString.contains("lastName=Romain"));
        assertTrue(userString.contains("firstName=Portier"));
        assertTrue(userString.contains("password=password123"));
        assertTrue(userString.contains("admin=true"));
        assertTrue(userString.contains("createdAt="));
        assertTrue(userString.contains("updatedAt="));
    }

    @Test
    public void testRequiredArgsConstructor() {
        String email = "test@test.com";
        String lastName = "Portier";
        String firstName = "Romain";
        String password = "password123";
        boolean admin = true;

        User user = new User(email, lastName, firstName, password, admin);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(lastName, user.getLastName());
        assertEquals(firstName, user.getFirstName());
        assertEquals(password, user.getPassword());
        assertEquals(admin, user.isAdmin());
    }

    @Test
    public void testRequiredArgsConstructorWithNullFields() {
        assertThrows(NullPointerException.class, () -> {
            new User(null, "Doe", "John", "password123", true);
        });

        assertThrows(NullPointerException.class, () -> {
            new User("test@test.com", null, "John", "password123", true);
        });

        assertThrows(NullPointerException.class, () -> {
            new User("test@test.com", "Doe", null, "password123", true);
        });

        assertThrows(NullPointerException.class, () -> {
            new User("test@test.com", "Doe", "John", null, true);
        });
    }
}
