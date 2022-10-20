package student;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        int recordBookID = 10;
        var deleteRecordBookSql = "DELETE FROM record_book WHERE id = " + recordBookID;
        var deleteStudentSql = "DELETE FROM student WHERE record_book_id = " + recordBookID;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            statement.addBatch(deleteStudentSql);
            statement.addBatch(deleteRecordBookSql);


            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            if (connection != null){
                connection.rollback();
            }
            throw e;
        }finally {
            if(connection != null){
                connection.close();
            }
            if (statement != null){
                statement.close();
            }
        }
    }
}
