package student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcStarter {

    public static void main(String[] args) throws SQLException {
        System.out.println(getStudentsByAlphabet());
        String year = "2023";
        System.out.println(getStudentGraduateOneYear(year));
        int number = 1;
        System.out.println(getGroupByStudentCount(number));
    }

    private static List<Object> getStudentsByAlphabet() throws SQLException {
        String sql = """
            SELECT *
            FROM student
            ORDER BY second_name DESC
            """;

        List<Object> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                result.add(resultSet.getInt("id"));
                result.add(resultSet.getString("first_name"));
                result.add(resultSet.getString("second_name"));
            }
        }
        return result;
    }

    private static List<Object> getStudentGraduateOneYear(String year) throws SQLException {
        String sql = """
            SELECT *
            FROM student join student_group sg on sg.id = student.group_id
            WHERE TO_CHAR (graduation, 'YYYY') = ?
            """;

        List<Object> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)){

            prepareStatement.setString(1, year);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getInt("id"));
                result.add(resultSet.getString("second_name"));
                result.add(resultSet.getString("graduation"));
            }
        }
        return result;
    }

    private static List<Integer> getGroupByStudentCount(int number) throws SQLException {
        String sql = """
            SELECT student_group.id, COUNT(s.id) FROM student_group join student s on student_group.id = s.group_id
            GROUP BY student_group.id
            HAVING COUNT(s.id) > ?
            """;

        List<Integer> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)){

            prepareStatement.setInt(1, number);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getInt("id"));
            }
        }
        return result;
    }


}
