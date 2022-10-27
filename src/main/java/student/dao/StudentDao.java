package student.dao;

import student.entity.StudentEntity;
import student.exception.DaoException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public interface StudentDao {
    List<Integer> getGroupByStudentCount(int count) throws DaoException;
    List<StudentEntity> getStudentGraduateOneYear(String year) throws DaoException;
    List<StudentEntity> getStudentsByAlphabet() throws DaoException;
    Optional<StudentEntity> findById(int id) throws DaoException;
    void update (StudentEntity studentEntity) throws DaoException;
    boolean create (StudentEntity studentEntity) throws DaoException;
    boolean delete(int id) throws DaoException;
    void getStudentsByStatement(List<StudentEntity> result, PreparedStatement preparedStatement) throws DaoException;
}
