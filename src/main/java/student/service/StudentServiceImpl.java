package student.service;

import student.dao.StudentDao;
import student.dao.StudentDaoImpl;
import student.entity.StudentEntity;
import student.exception.DaoException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class StudentServiceImpl implements StudentService{
    private final Scanner input = new Scanner(System.in);
    private final StudentDao studentDao = new StudentDaoImpl();
    @Override
    public List<Integer> getGroupByStudentCount() {
        int count = inputAndValidate("Enter students' number: ", "Input should be a number.");
        try {
            return studentDao.getGroupByStudentCount(count);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StudentEntity> getStudentGraduateOneYear() {
        System.out.println("Input a year: ");
        String year = input.nextLine();
        try {
            return studentDao.getStudentGraduateOneYear(year);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StudentEntity> getStudentsByAlphabet() {
        try {
            return studentDao.getStudentsByAlphabet();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<StudentEntity> findById() {
        int id = inputAndValidate("Enter student's id: ", "Student id should be a number.");
        try {
            return studentDao.findById(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        Optional<StudentEntity> maybeStudent = findById();
        System.out.println("Input student's firstname: ");
        String newName = input.nextLine();

        maybeStudent.ifPresent(studentEntity -> {
            studentEntity.setFirstName(newName);
            try {
                studentDao.update(studentEntity);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean create() {
        int id = inputAndValidate("Enter student's id: ", "Student id should be a number.");
        int groupId = inputAndValidate(
                "Input student's groupId: ",
                "Student groupId should be 1, 2 or 3",
                (value) -> !(value == 3 || value == 2|| value ==1));


        System.out.println("Input student's firstname: ");
        String firstName = input.nextLine();
        System.out.println("Input student's lastName: ");
        String secondName = input.nextLine();

        StudentEntity studentEntity = new StudentEntity(id, firstName, secondName, groupId);

        try {
            return studentDao.create(studentEntity);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete() {
        int id = inputAndValidate("Enter student's id: ", "Student id should be a number.");

        try {
            return studentDao.delete(id);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }



    private int inputAndValidate(String message, String validateMessage, Function<Integer, Boolean> validate){
        Integer id = null;

        do {
            try {
                System.out.println(message);
                id = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException mismatchException) {
                System.out.println(validateMessage);
                input.nextLine();
            }
        } while (id == null || validate.apply(id));

        return id;
    }
    private int inputAndValidate(String message, String validateMessage){
        return inputAndValidate(message, validateMessage, (value) -> false);
    }
}
