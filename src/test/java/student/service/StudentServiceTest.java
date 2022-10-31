package student.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.dao.StudentDao;
import student.dao.StudentDaoImpl;
import student.entity.StudentEntity;
import student.exception.DaoException;
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
    public void findByIdTest() {
        try {
            StudentEntity expected = new StudentEntity();
            when(studentDao.findById(any(Integer.class))).thenReturn(Optional.of(new StudentEntity()));
            when(input.nextInt()).thenReturn(1);
            Optional<StudentEntity> actual = studentService.findById();
            actual.ifPresent(studentEntity -> assertEquals(studentEntity, expected));
        } catch (DaoException e) {
            fail("Incorrect data", e);
        }
    }

    @Test
    public void getStudentsByAlphabet(){
        try {
            List<StudentEntity> expected = new ArrayList<>();
            when(studentDao.getStudentsByAlphabet()).thenReturn(new ArrayList<>());
            List<StudentEntity> actual = studentService.getStudentsByAlphabet();
            assertEquals(actual, expected);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getStudentGraduateOneYearTest(){
        try {
            List<StudentEntity> expected = new ArrayList<>();
            when(studentDao.getStudentGraduateOneYear(String.valueOf(String.class))).thenReturn(new ArrayList<>());
            when(input.nextLine()).thenReturn("2023");
            List<StudentEntity> actual = studentService.getStudentGraduateOneYear();
            assertEquals(actual, expected);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createStudentTest() {

        try {
            when(studentDao.create(any())).thenReturn(true);
            when(input.nextInt()).thenReturn(1);
            when(input.nextLine()).thenReturn("Leanova");
            boolean actual = studentService.create();
            assertTrue(actual);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteStudentTest(){
        try {
            when(studentDao.delete(any(Integer.class))).thenReturn(true);
            when(input.nextInt()).thenReturn(1);
            boolean actual = studentService.delete();
            assertTrue(actual);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateStudentTest(){
        try {
            when(input.nextLine()).thenReturn("Leanova");
            when(input.nextInt()).thenReturn(1);
            when(studentDao.update(any())).thenReturn(true);
            boolean actual = studentService.update();
            assertTrue(actual);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


}