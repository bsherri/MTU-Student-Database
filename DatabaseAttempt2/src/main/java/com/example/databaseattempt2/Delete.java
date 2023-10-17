package com.example.databaseattempt2;
/*
The purpose of this class is to handle the code that deletes a student from the database. When a student is selected and deleted from the
database it deletes them from both the students table and the modules table in the database.
 */
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/mtu_student_system";

   // Database credentials
   static final String USER = "root";
   static final String PASS = "passwordBailey1608!";

   public static void deleteSelectedRows(TableView<Student> table, ObservableList<Student> studentList) {
      // Get the selected items from the table

      ObservableList<Student> selectedStudents = table.getSelectionModel().getSelectedItems();

      // Loop through the selected items and delete them from the database and the table
      for (Student student : selectedStudents) {
         String sql = "DELETE mtustudent, modules\n" +
                 "FROM mtustudent\n" +
                 "INNER JOIN modules ON mtustudent.StudentIDNum = modules.StudentIDNum\n" +
                 "WHERE mtustudent.StudentIDNum = ?\n";

         try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
              PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentID());
            stmt.executeUpdate();
            studentList.remove(student);
         } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error deleting data from the database: " + e.getMessage());
            alert.showAndWait();
         }
      }
   }
}
