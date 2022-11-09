package student.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.dao.StudentDao;
import student.dao.StudentDaoImpl;
import student.entity.StudentEntity;
import student.exception.DaoException;
import student.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class StudentServiceTest {
    private static StudentDao studentDao;
    private static StudentService studentService;
    private static Scanner input;

    @BeforeClass
    public static void setUp() {
        studentDao = mock(StudentDaoImpl.class);
        input = mock(Scanner.class);
        studentService = new StudentServiceImpl(input, studentDao);
    }


    @AfterClass
    public static void tearDown() {
        studentDao = null;
        input = null;
    }

    @Test
    public void findByIdTest() throws DaoException, ServiceException {

        StudentEntity expected = new StudentEntity();
        when(studentDao.findById(any(Long.class))).thenReturn(Optional.of(new StudentEntity()));
        when(input.nextLong()).thenReturn(1L);
        Optional<StudentEntity> actual = studentService.findById();
        actual.ifPresent(studentEntity -> assertEquals(studentEntity, expected));
    }


    @Test
    public void getStudentsByAlphabet() throws DaoException, ServiceException {

        List<StudentEntity> expected = new ArrayList<>();
        when(studentDao.getStudentsByAlphabet()).thenReturn(new ArrayList<>());
        List<StudentEntity> actual = studentService.getStudentsByAlphabet();
        assertEquals(actual, expected);
    }

    @Test
    public void getStudentGraduateOneYearTest() throws DaoException, ServiceException {

        List<StudentEntity> expected = new ArrayList<>();
        when(studentDao.getStudentGraduateOneYear(String.valueOf(String.class))).thenReturn(new ArrayList<>());
        when(input.nextLine()).thenReturn("2023");
        List<StudentEntity> actual = studentService.getStudentGraduateOneYear();
        assertEquals(actual, expected);

    }

    @Test
    public void createStudentTest() throws DaoException, ServiceException {
        when(studentDao.create(any())).thenReturn(true);
        when(input.nextLong()).thenReturn(1L);
        when(input.nextLine()).thenReturn("Leanova");
        boolean actual = studentService.create();
        assertTrue(actual);

    }

    @Test
    public void deleteStudentTest() throws DaoException, ServiceException {

        when(studentDao.delete(any(Long.class))).thenReturn(true);
        when(input.nextLong()).thenReturn(2L);
        boolean actual = studentService.delete();
        assertTrue(actual);

    }

    @Test
    public void updateStudentTest() throws DaoException, ServiceException {

        when(input.nextLine()).thenReturn("Leanova");
        when(input.nextLong()).thenReturn(1L);
        when(studentDao.update(any())).thenReturn(true);
        boolean actual = studentService.update();
        assertTrue(actual);

    }
}