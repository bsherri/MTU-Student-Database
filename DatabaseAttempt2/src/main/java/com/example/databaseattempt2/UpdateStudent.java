package com.example.databaseattempt2;
/*
This class that provides a GUI for updating a student's details in a database.
The class takes a Student object as a parameter and opens a new window that
displays the student's current details, with text fields for editing. When the user clicks the "Update" button,
the class updates the corresponding row in the database and updates the Student object with the new details.
 */
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStudent {

   private static Student selectedStudent;

   public static void display(Student student) {
      selectedStudent = student;

      // Create a new stage for the update student window
      Stage window = new Stage();
      window.getIcons().add(new Image(UpdateModuleGradeInfo.class.getResourceAsStream("/MTU_logo.png")));
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Update " + selectedStudent.getName() + " Information");
      window.setMinWidth(400);

      // Create labels and text fields for the student's details
      Label idLabel = new Label("ID Number:");
      TextField idTextField = new TextField(selectedStudent.getStudentID());

      Label nameLabel = new Label("Name:");
      TextField nameTextField = new TextField(selectedStudent.getName());

      Label dobLabel = new Label("Date of Birth:");
      TextField dobTextField = new TextField(selectedStudent.getDOB());

      // Create an update button to update the student's details in the database
      Button updateButton = new Button("Update");
      updateButton.setOnAction(event -> {
         // Get the updated details from the text fields
         String newID = idTextField.getText();
         String newName = nameTextField.getText();
         String newDOB = dobTextField.getText();

         // Update the student's details in the database
         try {
            Connection conn = DriverManager.getConnection(Main.DB_URL, Main.USER, Main.PASS);

            String sql = "UPDATE mtustudent SET StudentIDNum=?, Name=?, DateOfBirth=? WHERE StudentIDNum=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newID);
            pstmt.setString(2, newName);
            pstmt.setString(3, newDOB);
            pstmt.setString(4, selectedStudent.getStudentID());

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            // Update the student object in the ObservableList
            selectedStudent.setStudentID(newID);
            selectedStudent.setName(newName);
            selectedStudent.setDOB(newDOB);

            // Close the update student window
            window.close();

         } catch (SQLException e) {
            e.printStackTrace();
         }
      });

      // Create a layout and add the labels and text fields to it
      GridPane layout = new GridPane();
      layout.setPadding(new Insets(10, 10, 10, 10));
      layout.setVgap(8);
      layout.setHgap(10);
      layout.setAlignment(Pos.CENTER);

      layout.add(idLabel, 0, 0);
      layout.add(idTextField, 1, 0);

      layout.add(nameLabel, 0, 1);
      layout.add(nameTextField, 1, 1);

      layout.add(dobLabel, 0, 2);
      layout.add(dobTextField, 1, 2);


      layout.add(updateButton, 0, 5);

      Scene updateStuScene = new Scene(layout, 400, 400);
      window.setScene(updateStuScene);
      window.show();
   }
}

