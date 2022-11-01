package student.runner;

import student.exception.ServiceException;
import student.service.StudentService;
import student.service.StudentServiceImpl;

public class Runner {
    public static void main(String[] args) {
        StudentService studentService = new StudentServiceImpl();
        try {
            System.out.println(studentService.getGroupByStudentCount());
            System.out.println(studentService.getStudentsByAlphabet());
            System.out.println(studentService.getStudentGraduateOneYear());
            System.out.println(studentService.findById());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
