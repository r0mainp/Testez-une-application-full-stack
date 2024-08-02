package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    private SignupRequest signupRequest1;
    private SignupRequest signupRequest2;

    /**
     * Sets up the test environment by initializing instances of SignupRequest.
     * Initializes mocks using MockitoAnnotations.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        signupRequest1 = new SignupRequest();
        signupRequest1.setEmail("test@test.com");
        signupRequest1.setFirstName("Romain");
        signupRequest1.setLastName("Portier");
        signupRequest1.setPassword("password1234");

        signupRequest2 = new SignupRequest();
        signupRequest2.setEmail("test@test.com");
        signupRequest2.setFirstName("Romain");
        signupRequest2.setLastName("Portier");
        signupRequest2.setPassword("password1234");
    }

    /**
     * Tests the equals and hashCode methods to ensure they work consistently.
     */
    @Test
    public void testEqualsAndHashCode() {
        assertTrue(signupRequest1.equals(signupRequest2));
        assertTrue(signupRequest2.equals(signupRequest1));

        assertEquals(signupRequest1.hashCode(), signupRequest2.hashCode());

        assertTrue(signupRequest1.equals(signupRequest1));

        int initialHashCode = signupRequest1.hashCode();
        assertEquals(initialHashCode, signupRequest1.hashCode());

        assertFalse(signupRequest1.equals(null));
        assertFalse(signupRequest1.equals(new Object()));

        signupRequest2.setEmail("other@test.com");
        assertFalse(signupRequest1.equals(signupRequest2));
        assertNotEquals(signupRequest1.hashCode(), signupRequest2.hashCode());
    }

    /**
     * Tests the toString method to ensure it generates the expected string representation.
     */
    @Test
    public void testToString() {
        String expectedString = "SignupRequest(email=test@test.com, firstName=Romain, lastName=Portier, password=password1234)";
        assertEquals(expectedString, signupRequest1.toString());
    }
}