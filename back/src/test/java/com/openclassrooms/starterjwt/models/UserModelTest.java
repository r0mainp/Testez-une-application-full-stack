package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link User} class.
 * 
 * This class tests the functionality of the {@link User} methods,
 * including the builder pattern, required arguments constructor, 
 * getters and setters, and equals and hashCode methods.
 */
public class UserModelTest {
    
    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the builder pattern of the {@link User} class.
     * 
     * This method verifies that the builder sets all fields correctly and
     * checks the string representation of the builder.
     */
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

    /**
     * Tests the required arguments constructor of the {@link User} class.
     * 
     * This method verifies that the constructor sets all fields correctly.
     */
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

    /**
     * Tests the required arguments constructor of the {@link User} class with null fields.
     * 
     * This method verifies that the constructor throws {@link NullPointerException} when
     * any of the required fields are null.
     */
    @Test
    public void testRequiredArgsConstructorWithNullFields() {
        assertThrows(NullPointerException.class, () -> {
            new User(null, "Portier", "Romain", "password123", true);
        });

        assertThrows(NullPointerException.class, () -> {
            new User("test@test.com", null, "Romain", "password123", true);
        });

        assertThrows(NullPointerException.class, () -> {
            new User("test@test.com", "Portier", null, "password123", true);
        });

        assertThrows(NullPointerException.class, () -> {
            new User("test@test.com", "Portier", "Romain", null, true);
        });
    }

    /**
     * Tests the getters and setters of the {@link User} class.
     * 
     * This method verifies that the setters correctly set the fields
     * and the getters return the expected values.
     */
    @Test
    public void testUserSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setLastName("Portier");
        user.setFirstName("Romain");
        user.setPassword("password123");
        user.setAdmin(true);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("Portier", user.getLastName());
        assertEquals("Romain", user.getFirstName());
        assertEquals("password123", user.getPassword());
        assertEquals(true, user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    /**
     * Tests the {@link User#equals(Object)} and {@link User#hashCode()} methods.
     * 
     * This method verifies that two users with the same id are considered equal and have the same hash code,
     * and two users with different ids are not equal and have different hash codes.
     */
    @Test
    public void testEqualsAndHashCode() {
        User user1 = User.builder()
            .id(1L)
            .email("test1@test.com")
            .lastName("Portier")
            .firstName("Romain")
            .password("password123")
            .admin(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        User user2 = User.builder()
            .id(1L)
            .email("test2@test.com")
            .lastName("Someone")
            .firstName("Else")
            .password("password456")
            .admin(false)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        User user3 = User.builder()
            .id(2L)
            .email("test3@test.com")
            .lastName("Another")
            .firstName("One")
            .password("password789")
            .admin(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        assertEquals(user1, user2, "User objects with the same id should be equal.");
        assertNotEquals(user1, user3, "User objects with different ids should not be equal.");

        assertEquals(user1.hashCode(), user2.hashCode(), "Hash codes should be the same for users with the same id.");
        assertNotEquals(user1.hashCode(), user3.hashCode(), "Hash codes should be different for users with different ids.");
    }
}
