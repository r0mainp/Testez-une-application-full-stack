package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

public class TeacherMapperTest {
    @InjectMocks
    private TeacherMapperImpl teacherMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntityList() {
        LocalDateTime now = LocalDateTime.now();

        TeacherDto teacherDto1 = new TeacherDto();
        teacherDto1.setId(1L);
        teacherDto1.setFirstName("Romain");
        teacherDto1.setLastName("Portier");
        teacherDto1.setCreatedAt(now);
        teacherDto1.setUpdatedAt(now);

        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setFirstName("Someone");
        teacherDto2.setLastName("Else");
        teacherDto2.setCreatedAt(now);
        teacherDto2.setUpdatedAt(now);

        List<TeacherDto> teacherDtoList = Arrays.asList(teacherDto1, teacherDto2);

        List<Teacher> teacherList = teacherMapper.toEntity(teacherDtoList);

        assertEquals(2, teacherList.size());

        Teacher teacher1 = teacherList.get(0);
        assertEquals(1L, teacher1.getId());
        assertEquals("Romain", teacher1.getFirstName());
        assertEquals("Portier", teacher1.getLastName());
        assertEquals(now, teacher1.getCreatedAt());
        assertEquals(now, teacher1.getUpdatedAt());

        Teacher teacher2 = teacherList.get(1);
        assertEquals(2L, teacher2.getId());
        assertEquals("Someone", teacher2.getFirstName());
        assertEquals("Else", teacher2.getLastName());
        assertEquals(now, teacher2.getCreatedAt());
        assertEquals(now, teacher2.getUpdatedAt());
    }

    @Test
    public void testToDtoList() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("Romain")
                .lastName("Portier")
                .createdAt(now)
                .updatedAt(now)
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(2L)
                .firstName("Someone")
                .lastName("Else")
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<Teacher> teacherList = Arrays.asList(teacher1, teacher2);

        List<TeacherDto> teacherDtoList = teacherMapper.toDto(teacherList);

        assertEquals(2, teacherDtoList.size());

        TeacherDto teacherDto1 = teacherDtoList.get(0);
        assertEquals(1L, teacherDto1.getId());
        assertEquals("Romain", teacherDto1.getFirstName());
        assertEquals("Portier", teacherDto1.getLastName());
        assertEquals(now, teacherDto1.getCreatedAt());
        assertEquals(now, teacherDto1.getUpdatedAt());

        TeacherDto teacherDto2 = teacherDtoList.get(1);
        assertEquals(2L, teacherDto2.getId());
        assertEquals("Someone", teacherDto2.getFirstName());
        assertEquals("Else", teacherDto2.getLastName());
        assertEquals(now, teacherDto2.getCreatedAt());
        assertEquals(now, teacherDto2.getUpdatedAt());
    }
}
