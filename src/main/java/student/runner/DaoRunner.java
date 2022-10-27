package student.runner;

import student.service.StudentService;
import student.service.StudentServiceImpl;

public class DaoRunner {
    public static void main(String[] args) {

        StudentService studentService = new StudentServiceImpl();
        System.out.println(studentService.getGroupByStudentCount());
        System.out.println(studentService.getStudentsByAlphabet());
        System.out.println(studentService.getStudentGraduateOneYear());
        System.out.println(studentService.findById());
    }

}
