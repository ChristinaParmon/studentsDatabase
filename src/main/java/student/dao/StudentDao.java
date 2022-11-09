package student.dao;

import student.entity.StudentEntity;
import student.exception.DaoException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public interface StudentDao {
    List<Integer> getGroupByStudentCount(long count) throws DaoException;

    List<StudentEntity> getStudentGraduateOneYear(String year) throws DaoException;

    List<StudentEntity> getStudentsByAlphabet() throws DaoException;

    Optional<StudentEntity> findById(long id) throws DaoException;

    boolean update(StudentEntity studentEntity) throws DaoException;

    boolean create(StudentEntity studentEntity) throws DaoException;

    boolean delete(long id) throws DaoException;

    void getStudentsByStatement(List<StudentEntity> result, PreparedStatement preparedStatement) throws DaoException;
}
