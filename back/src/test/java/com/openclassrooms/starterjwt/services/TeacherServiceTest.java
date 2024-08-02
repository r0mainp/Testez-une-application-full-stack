package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

/**
 * Unit tests for the {@link TeacherService} class.
 * 
 * This test class uses Mockito to mock dependencies and verify interactions and behaviors of the {@link TeacherService} methods.
 */
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link TeacherService#findAll()} method.
     * 
     * Verifies that {@link TeacherRepository#findAll()} is called once and that the result is not null.
     */
    @Test
    public void testFindAllTeachers(){
        List<Teacher> teachers = new ArrayList<>();
        when(teacherRepository.findAll()).thenReturn(teachers);
        List<Teacher> result = teacherService.findAll();
        assertNotNull(result, "The result of findAll should not be null.");
        verify(teacherRepository, times(1)).findAll();
    }

    /**
     * Tests the {@link TeacherService#findById(Long)} method.
     * 
     * Verifies that {@link TeacherRepository#findById(Long)} is called once with the correct ID and that the result is not null.
     */
    @Test
    public void testGetTeacherById(){
        Teacher teacher = new Teacher();
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        Teacher fetchedTeacher = teacherService.findById(1L);
        assertNotNull(fetchedTeacher, "The fetched teacher should not be null.");
        verify(teacherRepository, times(1)).findById(1L);
    }

}