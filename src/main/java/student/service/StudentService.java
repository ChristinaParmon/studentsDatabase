package student.service;

import student.entity.StudentEntity;
import student.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Integer> getGroupByStudentCount() throws ServiceException;

    List<StudentEntity> getStudentGraduateOneYear() throws ServiceException;

    List<StudentEntity> getStudentsByAlphabet() throws ServiceException;

    Optional<StudentEntity> findById() throws ServiceException;

    boolean update() throws ServiceException;

    boolean create() throws ServiceException;

    boolean delete() throws ServiceException;

}
