package student.dao;

import student.entity.StudentEntity;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public interface StudentDao {
    List<Integer> getGroupByStudentCount(int count);
    List<StudentEntity> getStudentGraduateOneYear(String year);
    List<StudentEntity> getStudentsByAlphabet();
    Optional<StudentEntity> findById(int id);
    void update (StudentEntity studentEntity);
    StudentEntity create (StudentEntity studentEntity);
    void delete(int id);
    void getStudentsByStatement(List<StudentEntity> result, PreparedStatement preparedStatement);
}
