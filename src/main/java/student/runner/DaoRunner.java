package student.runner;

import student.service.StudentService;
import student.entity.StudentEntity;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class DaoRunner {
    private static final Scanner input = new Scanner(System.in);
    private static final StudentService studentService = StudentService.getInstance();
    public static void main(String[] args) {

        System.out.println(studentService.getStudentsByAlphabet());
        searchStudentGraduateOneYear();
        searchGroupByStudentCount();
        createStudent();
        searchStudent();

        updateStudent();
        deleteStudent();
        deleteStudent();

        input.close();

    }

    private static Optional<StudentEntity> searchStudent(){
        int id = inputAndValidate();

        return studentService.findById(id);
    }
    private static void updateStudent(){

        Optional<StudentEntity> maybeStudent = searchStudent();
        System.out.println("Input student's firstname: ");
        String newName = input.nextLine();

        maybeStudent.ifPresent(studentEntity -> {
            studentEntity.setFirstName(newName);
            studentService.update(studentEntity);
        });
    }

    private static void deleteStudent() {

        int id = inputAndValidate();

        studentService.delete(id);
    }

    private static void createStudent() {
        int id = inputAndValidate();
        int groupId = inputAndValidate(
                "Input student's groupId: ",
                "Student groupId should be 1, 2 or 3",
                (value) -> !(value == 3 || value == 2|| value ==1));


        System.out.println("Input student's firstname: ");
        String firstName = input.nextLine();
        System.out.println("Input student's lastName: ");
        String secondName = input.nextLine();


        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(id);
        studentEntity.setFirstName(firstName);
        studentEntity.setSecondName(secondName);
        studentEntity.setGroupId(groupId);

        StudentEntity createdStudent = studentService.create(studentEntity);
        System.out.println(createdStudent);
    }

    private static void searchStudentGraduateOneYear(){
        System.out.println("Input a year: ");
        String year = input.nextLine();
        System.out.println(studentService.getStudentGraduateOneYear(year));
    }

    private static void searchGroupByStudentCount(){
        int count = inputAndValidate();
        System.out.println(studentService.getGroupByStudentCount(count));
    }

    private static int inputAndValidate(String message, String validateMessage, Function<Integer, Boolean> validate){
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
    private static int inputAndValidate(){
        return inputAndValidate("Input student's id: ", "Student's id should be a number", (value) -> false);
    }
}
