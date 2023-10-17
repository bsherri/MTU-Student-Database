package com.example.databaseattempt2;
/*
This class provides a screen for updating the grade/Module information of a
specific module in a MySQL database. The window includes text fields for entering
the new module number, module name, semester, and grade, as well as a Save button for committing the changes to the database.
Upon clicking the Save button, the data is validated and then stored in the database.
 */
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateModuleGradeInfo {

   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/mtu_student_system";

   // Database credentials
   static final String USER = "root";
   static final String PASS = "passwordBailey1608!";

   public static void display(Module selectedModule) {
      // Create a new stage for the Update Module Info window
      Stage updateModuleInfoStage = new Stage();

      // Create textfields for module_num, module_name, semester, and grade
      TextField module_numField = new TextField(selectedModule.getModule_num());
      TextField module_nameField = new TextField(selectedModule.getModule_name());
      TextField semesterField = new TextField(selectedModule.getSemester());
      TextField gradeField = new TextField(selectedModule.getGrade());

      // Create a save button
      Button saveButton = new Button("Save");

      // Create a layout and add the textfields and button to it
      GridPane root = new GridPane();
      root.add(new Label("Module Number:"), 0, 0);
      root.add(module_numField, 2, 0);
      root.add(new Label("Module Name:"), 0, 2);
      root.add(module_nameField, 2, 2);
      root.add(new Label("Semester:"), 0, 4);
      root.add(semesterField, 2, 4);
      root.add(new Label("Grade:"), 0, 6);
      root.add(gradeField, 2, 6);
      root.add(saveButton, 0, 8);

      // Create a Scene and set it on the stage
      Scene scene = new Scene(root, 350, 400);
      updateModuleInfoStage.setScene(scene);
      updateModuleInfoStage.setTitle("MTU Student Record System");
      updateModuleInfoStage.getIcons().add(new Image(UpdateModuleGradeInfo.class.getResourceAsStream("/MTU_logo.png")));
      updateModuleInfoStage.show();

      // Set the action for the save button
      saveButton.setOnAction(event -> {
         // Get the data from the textfields
         String moduleNum = module_numField.getText();
         String moduleName = module_nameField.getText();
         String semester = semesterField.getText();
         String grade = gradeField.getText();

         try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Use a prepared statement to update the module information in the database
            String sql = "UPDATE modules SET module_num=?, module_name=?, semester=?, grade=? WHERE module_num=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, moduleNum);
            pstmt.setString(2, moduleName);
            pstmt.setString(3, semester);
            pstmt.setString(4, grade);
            pstmt.setString(5, selectedModule.getModule_num());
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            // Update the Module object with the new data
            selectedModule.setModule_num(moduleNum);
            selectedModule.setModule_name(moduleName);
            selectedModule.setSemester(semester);
            selectedModule.setGrade(grade);

            // Close the window
            updateModuleInfoStage.close();
         } catch (SQLException e){
            e.printStackTrace();
         }
      });
   }
}
