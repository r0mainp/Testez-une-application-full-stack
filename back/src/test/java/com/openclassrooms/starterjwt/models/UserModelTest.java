package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public void testAllArgsConstructor() {
        // Create a User instance using the all-args constructor
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
            1L,                   // id
            "test@test.com",      // email
            "Doe",                // lastName
            "John",               // firstName
            "password123",        // password
            false,                 // admin
            now,                  // createdAt
            now                   // updatedAt
        );

        // Verify that all fields are correctly initialized
        assertNotNull(user, "User should not be null.");
        assertEquals(1L, user.getId(), "ID should match.");
        assertEquals("test@test.com", user.getEmail(), "Email should match.");
        assertEquals("Doe", user.getLastName(), "Last name should match.");
        assertEquals("John", user.getFirstName(), "First name should match.");
        assertEquals("password123", user.getPassword(), "Password should match.");
        assertFalse(user.isAdmin(), "Admin flag should match.");
        assertEquals(now, user.getCreatedAt(), "Created at should match.");
        assertEquals(now, user.getUpdatedAt(), "Updated at should match.");
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

    @Test
    public void testUserSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setPassword("password123");
        user.setAdmin(true);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("password123", user.getPassword());
        assertEquals(true, user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }
    @Test
    public void testEqualsAndHashCode() {
        User user1 = User.builder()
                         .id(1L)
                         .email("test1@test.com")
                         .lastName("Doe")
                         .firstName("John")
                         .password("password123")
                         .admin(true)
                         .createdAt(LocalDateTime.now())
                         .updatedAt(LocalDateTime.now())
                         .build();

        User user2 = User.builder()
                         .id(1L)
                         .email("test2@test.com")
                         .lastName("Smith")
                         .firstName("Jane")
                         .password("password456")
                         .admin(false)
                         .createdAt(LocalDateTime.now())
                         .updatedAt(LocalDateTime.now())
                         .build();

        User user3 = User.builder()
                         .id(2L)
                         .email("test3@test.com")
                         .lastName("Brown")
                         .firstName("Emily")
                         .password("password789")
                         .admin(true)
                         .createdAt(LocalDateTime.now())
                         .updatedAt(LocalDateTime.now())
                         .build();

        // Test equality
        assertEquals(user1, user2, "User objects with the same id should be equal.");
        assertNotEquals(user1, user3, "User objects with different ids should not be equal.");

        // Test hashCode
        assertEquals(user1.hashCode(), user2.hashCode(), "Hash codes should be the same for users with the same id.");
        assertNotEquals(user1.hashCode(), user3.hashCode(), "Hash codes should be different for users with different ids.");
    }
}
