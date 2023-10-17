package com.example.databaseattempt2;
/*
This purpose of this class is to handle the code for adding a student to the database. it has its own stage and scene and elements that make it easier for
the user to understand what they are trying to accomplish. When we add a student it saves the new student to the database.
 */
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class AddStudent {

   // Database credentials
   static final String USER = "root";
   static final String PASS = "passwordBailey1608!";

   public static void display() {
      // Create a new stage for adding a student
      Stage addStudentStage = new Stage();
      addStudentStage.setTitle("Add Student");
      addStudentStage.getIcons().add(new Image(UpdateModuleGradeInfo.class.getResourceAsStream("/MTU_logo.png")));

      // Create a grid pane to layout the text fields and labels
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);

      // Create the labels and text fields for the student information
      Label idLabel = new Label("Student ID:");
      TextField idTextField = new TextField();

      Label nameLabel = new Label("Name:");
      TextField nameTextField = new TextField();

      Label dobLabel = new Label("Date of Birth:");
      TextField dobTextField = new TextField();

      // Add the labels and text fields to the grid pane
      grid.add(idLabel, 1, 1);
      grid.add(idTextField, 2, 1);
      grid.add(nameLabel, 1, 2);
      grid.add(nameTextField, 2, 2);
      grid.add(dobLabel, 1, 3);
      grid.add(dobTextField, 2, 3);

      // Create a button to save the new student to the database
      Button saveButton = new Button("Save");
      saveButton.setOnAction(event -> {
         try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtu_student_system", USER, PASS);

            // Create a SQL statement to insert the new student into the "mtu.student" table
            String sql = "INSERT INTO mtustudent (StudentIDNum, Name, DateOfBirth) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idTextField.getText());
            pstmt.setString(2, nameTextField.getText());

            // Check if the DOB is in the correct format (YYYY-MM-DD)
            String dobText = dobTextField.getText();
            if (!dobText.matches("\\d{4}-\\d{2}-\\d{2}")) {
               // Display an alert telling the user to enter the DOB in the correct format
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Please enter the date of birth in the format YYYY-MM-DD.");
               alert.showAndWait();
               return; // stop execution of the method
            }
            pstmt.setString(3, dobText);

            // Execute the SQL statement
            pstmt.executeUpdate();

            // Close the connection and statement
            pstmt.close();
            conn.close();

            // Close the add student stage
            addStudentStage.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      });


      // Add the save button to the grid pane
      grid.add(saveButton, 1, 6);

      // Create a new scene with the grid pane and set it on the add student stage
      Scene addStudentScene = new Scene(grid, 250, 200);
      addStudentStage.setScene(addStudentScene);
      addStudentStage.show();
   }
}
