package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

public class UserMapperTest {

    @InjectMocks
    UserMapperImpl userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    public void testToEntityList() {

        LocalDateTime now = LocalDateTime.now();


        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setEmail("test1@test.com");
        userDto1.setFirstName("Romain");
        userDto1.setLastName("Portier");
        userDto1.setPassword("password1");
        userDto1.setAdmin(true);
        userDto1.setCreatedAt(now);
        userDto1.setUpdatedAt(now);

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("test2@test.com");
        userDto2.setFirstName("Someone");
        userDto2.setLastName("Else");
        userDto2.setPassword("password2");
        userDto2.setAdmin(false);
        userDto2.setCreatedAt(now);
        userDto2.setUpdatedAt(now);

        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2);


        List<User> userList = userMapper.toEntity(userDtoList);


        assertEquals(2, userList.size());

        User user1 = userList.get(0);
        assertEquals(1L, user1.getId());
        assertEquals("test1@test.com", user1.getEmail());
        assertEquals("Romain", user1.getFirstName());
        assertEquals("Portier", user1.getLastName());
        assertEquals("password1", user1.getPassword());
        assertTrue(user1.isAdmin());
        assertEquals(now, user1.getCreatedAt());
        assertEquals(now, user1.getUpdatedAt());

        User user2 = userList.get(1);
        assertEquals(2L, user2.getId());
        assertEquals("test2@test.com", user2.getEmail());
        assertEquals("Someone", user2.getFirstName());
        assertEquals("Else", user2.getLastName());
        assertEquals("password2", user2.getPassword());
        assertFalse(user2.isAdmin());
        assertEquals(now, user2.getCreatedAt());
        assertEquals(now, user2.getUpdatedAt());
    }

    @Test
    public void testToDtoList() {
        LocalDateTime now = LocalDateTime.now();

        User user1 = User.builder()
                .id(1L)
                .email("test1@test.com")
                .firstName("Romain")
                .lastName("Doe")
                .password("password1")
                .admin(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("test2@test.com")
                .firstName("Someone")
                .lastName("Else")
                .password("password2")
                .admin(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<User> userList = Arrays.asList(user1, user2);

        List<UserDto> userDtoList = userMapper.toDto(userList);

        assertEquals(2, userDtoList.size());

        UserDto userDto1 = userDtoList.get(0);
        assertEquals(1L, userDto1.getId());
        assertEquals("test1@test.com", userDto1.getEmail());
        assertEquals("Romain", userDto1.getFirstName());
        assertEquals("Doe", userDto1.getLastName());
        assertEquals("password1", userDto1.getPassword());
        assertTrue(userDto1.isAdmin());
        assertEquals(now, userDto1.getCreatedAt());
        assertEquals(now, userDto1.getUpdatedAt());

        UserDto userDto2 = userDtoList.get(1);
        assertEquals(2L, userDto2.getId());
        assertEquals("test2@test.com", userDto2.getEmail());
        assertEquals("Someone", userDto2.getFirstName());
        assertEquals("Else", userDto2.getLastName());
        assertEquals("password2", userDto2.getPassword());
        assertFalse(userDto2.isAdmin());
        assertEquals(now, userDto2.getCreatedAt());
        assertEquals(now, userDto2.getUpdatedAt());
    }
}
