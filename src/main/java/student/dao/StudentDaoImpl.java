package student.dao;

import student.entity.StudentEntity;
import student.exception.DaoException;
import student.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao{
    public StudentDaoImpl(){

    }
    private static final String DELETE_STUDENT_SQL = """
            DELETE FROM student
            WHERE id = ?
            """;

    private static final String DELETE_RECORD_BOOK_SQL = """
            DELETE FROM record_book
            WHERE student_id = ?
            """;

    private static final String CREATE_SQL = """
            INSERT INTO student(first_name, second_name, group_id)
            VALUES (?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE student
            SET first_name = ?,
                second_name = ?,
                group_id = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ID = """
            SELECT id,
                first_name,
                second_name,
                group_id
            FROM student
            WHERE id = ?
            """;

    private static final String READ_GROUP = """
            SELECT student_group.id, COUNT(s.id) FROM student_group join student s on student_group.id = s.group_id
            GROUP BY student_group.id
            HAVING COUNT(s.id) > ?
            """;

    private static final String READ_STUDENT_GRADUATE = """
            SELECT *
            FROM student join student_group sg on sg.id = student.group_id
            WHERE TO_CHAR (graduation, 'YYYY') = ?
            """;

    private static final String READ_STUDENT_BY_ALPHABET = """
            SELECT *
            FROM student
            ORDER BY second_name DESC
            """;
    @Override
    public List<Integer> getGroupByStudentCount(int count) throws DaoException {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(READ_GROUP)){

            prepareStatement.setInt(1, count);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getInt("id"));
            }
            return result;
        }catch (SQLException e){
            throw new DaoException(e);
        }

    }

    @Override
    public List<StudentEntity> getStudentGraduateOneYear(String year) throws DaoException {
        List<StudentEntity> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_STUDENT_GRADUATE)){
            preparedStatement.setString(1, year);
            getStudentsByStatement(result, preparedStatement);
            return result;
        }catch (SQLException e){
            throw new DaoException(e);
        }


    }
    @Override
    public Optional<StudentEntity> findById(int id) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            StudentEntity student = null;

            if (resultSet.next()){
                student = new StudentEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getInt("group_id")
                );
            }
            return Optional.ofNullable(student);
        }catch (SQLException e){
            throw new DaoException(e);
        }
    }
    @Override
    public void update (StudentEntity studentEntity) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, studentEntity.getFirstName());
            preparedStatement.setString(2, studentEntity.getSecondName());
            preparedStatement.setInt(3, studentEntity.getGroupId());

            preparedStatement.setInt(4, studentEntity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean create (StudentEntity studentEntity) throws DaoException {
        try ( Connection connection = ConnectionManager.get();
              PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, studentEntity.getFirstName());
            preparedStatement.setString(2, studentEntity.getSecondName());
            preparedStatement.setInt(3, studentEntity.getGroupId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                studentEntity.setId(generatedKeys.getInt("id"));
            }
            return true;
        }catch (SQLException e){
            throw new DaoException(e);
        }
    }
    @Override
    public boolean delete(int id) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement deleteRecordBookStatement = connection.prepareStatement(DELETE_RECORD_BOOK_SQL);
             PreparedStatement deleteStudentStatement = connection.prepareStatement(DELETE_STUDENT_SQL)){

            deleteRecordBookStatement.setInt(1, id);
            deleteStudentStatement.setInt(1, id);

            deleteRecordBookStatement.executeUpdate();
            deleteStudentStatement.executeUpdate();
            return true;
        }catch (SQLException sqlException){
            throw new DaoException(sqlException);
        }
    }

    @Override
    public List<StudentEntity> getStudentsByAlphabet() throws DaoException {
        List<StudentEntity> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_STUDENT_BY_ALPHABET)){
            getStudentsByStatement(result, preparedStatement);
        }catch (SQLException sqlException){
            throw new DaoException(sqlException);
        }
        return result;
    }

    public void getStudentsByStatement(List<StudentEntity> result, PreparedStatement preparedStatement) throws DaoException {
        ResultSet resultSet;
        StudentEntity student;
        try {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                student = new StudentEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getInt("group_id")
                );
                result.add(student);
            }
        }catch (SQLException sqlException) {
            throw new DaoException(sqlException);
        }
    }
}
