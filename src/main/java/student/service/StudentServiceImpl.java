package student.service;

import student.dao.StudentDao;
import student.dao.StudentDaoImpl;
import student.entity.StudentEntity;
import student.exception.DaoException;
import student.exception.ServiceException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class StudentServiceImpl implements StudentService {
    private Scanner input = new Scanner(System.in);
    private StudentDao studentDao = new StudentDaoImpl();

    public StudentServiceImpl(Scanner input, StudentDao studentDao) {
        this.input = input;
        this.studentDao = studentDao;
    }

    public StudentServiceImpl() {
        new StudentServiceImpl(new Scanner(System.in), new StudentDaoImpl());
    }

    @Override
    public List<Integer> getGroupByStudentCount() throws ServiceException {
        long count = inputAndValidate("Enter students' number: ", "Input should be a number.");
        try {
            return studentDao.getGroupByStudentCount(count);
        } catch (DaoException e) {
            throw new ServiceException("Error with getting group by student count", e);
        }
    }

    @Override
    public List<StudentEntity> getStudentGraduateOneYear() throws ServiceException {
        System.out.println("Input a year: ");
        String year = input.nextLine();
        try {
            return studentDao.getStudentGraduateOneYear(year);
        } catch (DaoException e) {
            throw new ServiceException("Error with getting students graduate one year", e);
        }
    }

    @Override
    public List<StudentEntity> getStudentsByAlphabet() throws ServiceException {
        try {
            return studentDao.getStudentsByAlphabet();
        } catch (DaoException e) {
            throw new ServiceException("Error with getting students by alphabet", e);
        }
    }

    @Override
    public Optional<StudentEntity> findById() throws ServiceException {
        long id = inputAndValidate("Enter student's id: ", "Student id should be a number.");
        try {
            return studentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error with finding by Id", e);
        }
    }

    @Override
    public boolean update() throws ServiceException {
        Optional<StudentEntity> studentOptional = findById();
        System.out.println("Input student's firstname: ");
        String newName = input.nextLine();

        studentOptional.ifPresent(studentEntity -> {
            studentEntity.setFirstName(newName);
            try {
                studentDao.update(studentEntity);
            } catch (DaoException e) {
                try {
                    throw new ServiceException("Error with updating", e);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return true;
    }

    @Override
    public boolean create() throws ServiceException {
        long id = inputAndValidate("Enter student's id: ", "Student id should be a number.");
        long groupId = inputAndValidate(
                "Input student's groupId: ",
                "Student groupId should be 1, 2 or 3",
                (value) -> !(value == 3 || value == 2 || value == 1));

        System.out.println("Input student's firstname: ");
        String firstName = input.nextLine();
        System.out.println("Input student's lastName: ");
        String secondName = input.nextLine();

        StudentEntity studentEntity = new StudentEntity(id, firstName, secondName, groupId);

        try {
            return studentDao.create(studentEntity);
        } catch (DaoException e) {
            throw new ServiceException("Error with creating", e);
        }
    }

    @Override
    public boolean delete() throws ServiceException {
        long id = inputAndValidate("Enter student's id: ", "Student id should be a number.");

        try {
            return studentDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Error with deleting", e);
        }
    }

    private long inputAndValidate(String message, String validateMessage, Function<Long, Boolean> validate) {
        Long id = null;

        do {
            try {
                System.out.println(message);
                id = input.nextLong();
                input.nextLine();
            } catch (InputMismatchException mismatchException) {
                System.out.println(validateMessage);
                input.nextLong();
            }
        } while (id == null || validate.apply(id));
        return id;
    }

    private long inputAndValidate(String message, String validateMessage) {
        return inputAndValidate(message, validateMessage, (value) -> false);
    }
}
