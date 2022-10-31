package student.service;
import student.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
public interface StudentService{

    List<Integer> getGroupByStudentCount();
    List<StudentEntity> getStudentGraduateOneYear();
    List<StudentEntity> getStudentsByAlphabet();
    Optional<StudentEntity> findById();
    boolean update ();
    boolean create ();
    boolean delete();

}
