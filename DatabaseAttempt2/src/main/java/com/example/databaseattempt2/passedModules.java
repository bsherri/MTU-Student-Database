package com.example.databaseattempt2;
/*
This class is responsible for displaying a table of passed modules for a given student.
It is using a TableView that displays the module number, name, semester, and grade for each passed module of the selected student.
The class connects to the database to retrieve the module data and provides functionality
for deleting modules and updating module grades.
The class also includes a method for displaying a window with the passed module data for a given student.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class passedModules {

   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/mtu_student_system";

   // Database credentials
   static final String USER = "root";
   static final String PASS = "passwordBailey1608!";

   public static void display(Student selectedStudent) throws FileNotFoundException {

      InputStream stream = new FileInputStream("C:/Users/brian/OneDrive - mycit.ie/Munster Technological University/Object Oriented Projects/OOP Assignment2/DatabaseAttempt2/src/main/resources/studentImage.png");
      Image image = new Image(stream);
      ImageView imageView = new ImageView();
      imageView.setImage(image);
      imageView.setX(10);
      imageView.setY(10);
      imageView.setFitWidth(100);
      imageView.setPreserveRatio(true);
      Group root2 = new Group(imageView);

      Label studentName = new Label(selectedStudent.getName());
      studentName.setFont(new Font("Arial", 30));

      GridPane headers = new GridPane();
      headers.add(root2, 0,0);
      headers.add(studentName,2,0);

      Stage stage = new Stage();
      stage.setTitle(selectedStudent.getName());
      TableView<Module> table = new TableView<>();

      // Connect to the database
      try {
         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

         // Create a SQL statement to retrieve data from the "mtu.module" table for the selected student
         String sql = "SELECT * FROM modules WHERE StudentIDNum='" + selectedStudent.getStudentID() + "' AND grade >= 40";

         // Execute the SQL statement
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);

         // Create a list to store the Module objects
         ObservableList<Module> moduleList = FXCollections.observableArrayList();

         // Loop through the result set and add the Module objects to the list
         while (rs.next()) {
            Module module = new Module(
                    rs.getString("module_num"),
                    rs.getString("module_name"),
                    rs.getString("semester"),
                    rs.getString("grade")
            );
            moduleList.add(module);
         }

         // Close the connection, statement, and result set
         rs.close();
         stmt.close();
         conn.close();

         // Create the columns for the TableView
         TableColumn<Module, String> module_numCol = new TableColumn<>("Module Num");
         module_numCol.setCellValueFactory(cellData -> cellData.getValue().module_numProperty());
         module_numCol.setPrefWidth(132.5);

         TableColumn<Module, String> nameCol = new TableColumn<>("Passed Modules");
         nameCol.setCellValueFactory(cellData -> cellData.getValue().module_nameProperty());
         nameCol.setPrefWidth(132.5);

         TableColumn<Module, String> semesterCol = new TableColumn<>("Semester");
         semesterCol.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());
         semesterCol.setPrefWidth(132.5);

         TableColumn<Module, String> gradeCol = new TableColumn<>("Grade");
         gradeCol.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
         gradeCol.setPrefWidth(132.5);

         // Add the columns to the TableView
         table.getColumns().addAll(module_numCol, nameCol, semesterCol, gradeCol);

         // Add the data to the TableView
         table.setItems(moduleList);

         Button updateInfo = new Button("Add Information");
         updateInfo.setPrefWidth(120);
         Button updateModInfo = new Button("Update Modules");
         updateModInfo.setPrefWidth(120);
         Button deleteButton = new Button("Delete Module");
         deleteButton.setPrefWidth(120);

         deleteButton.setOnAction(event -> {
            // Get the selected module from the TableView
            Module selectedModule = table.getSelectionModel().getSelectedItem();
            if (selectedModule != null) {
               try {
                  // Connect to the database
                  Connection conn2 = DriverManager.getConnection(DB_URL, USER, PASS);

                  // Create a SQL DELETE statement to delete the module from the "modules" table
                  String sql2 = "DELETE FROM modules WHERE module_num='" + selectedModule.getModule_num() + "'";

                  // Execute the SQL statement
                  Statement stmt2 = conn2.createStatement();
                  int rowCount = stmt2.executeUpdate(sql2);

                  // Close the connection and statement
                  stmt2.close();
                  conn2.close();

                  // Remove the selected module from the TableView
                  table.getItems().remove(selectedModule);

               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }
         });


         updateModInfo.setOnAction(event -> {
            Module selectedModule = table.getSelectionModel().getSelectedItem();
            if (selectedModule != null) {
               UpdateModuleGradeInfo.display(selectedModule);
            }
         });


         updateInfo.setOnAction(event -> {
            // Create a new stage for the Add Module Info window
            Stage addModuleInfoStage = new Stage();

            // Set the title of the stage
            addModuleInfoStage.setTitle("Add Module Info");

            // Create textfields for module_num, module_name, semester, and grade

            TextField module_numField = new TextField();
            module_numField.setPromptText("Module Number");
            TextField module_nameField = new TextField();
            module_nameField.setPromptText("Module Name");
            TextField semesterField = new TextField();
            semesterField.setPromptText("Semester 1/2");
            TextField gradeField = new TextField();
            gradeField.setPromptText("Grade (1-100)");

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
            addModuleInfoStage.setScene(scene);
            addModuleInfoStage.show();

            // Set the action for the save button
            saveButton.setOnAction(saveEvent -> {
               // Get the data from the textfields
               String moduleNum = module_numField.getText();
               String moduleName = module_nameField.getText();
               String semester = semesterField.getText();
               String grade = gradeField.getText();

               if (grade.matches("^([1-9]|[1-9]\\d|100)$")) {
                  // Connect to the database
                  try {
                     Connection conn2 = DriverManager.getConnection(DB_URL, USER, PASS);

                     // Create a SQL statement to insert the data into the "mtu.module" table
                     String sql2 = "INSERT INTO modules (StudentIDNum, module_num, module_name, semester, grade) " +
                             "VALUES ('" + selectedStudent.getStudentID() + "', '" + moduleNum + "', '" + moduleName + "', '" + semester + "', '" + grade + "')";

                     // Execute the SQL statement
                     Statement stmt2 = conn2.createStatement();
                     stmt2.executeUpdate(sql2);

                     // Close the connection and statement
                     stmt2.close();
                     conn2.close();

                     // Refresh the table in the main window
                     table.getItems().clear();
                     table.setItems(getModuleList(selectedStudent));

                     // Close the Add Module Info window
                     addModuleInfoStage.close();
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }else {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Invalid Grade");
                  alert.setHeaderText("Invalid Grade");
                  alert.setContentText("Grade must be a number between 1 and 100");
                  alert.showAndWait();
               }
            });
         });


         VBox buttonsVbox = new VBox();
         buttonsVbox.setSpacing(5);
         buttonsVbox.setPadding(new Insets(5));
         buttonsVbox.getChildren().addAll(updateInfo, updateModInfo, deleteButton);
         // Create a layout and add the TableView to it
         VBox vbox = new VBox();
         vbox.getChildren().addAll(headers ,table, buttonsVbox);



         // Create a Scene and set it on the stage
         Scene scene = new Scene(vbox, 545, 528);
         stage.setScene(scene);
         stage.getIcons().add(new Image(UpdateModuleGradeInfo.class.getResourceAsStream("/MTU_logo.png")));
         stage.show();

      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static ObservableList<Module> getModuleList(Student selectedStudent) {
      ObservableList<Module> moduleList = FXCollections.observableArrayList();

      // Connect to the database
      try {
         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

         // Create a SQL statement to retrieve data from the "mtu.module" table for the selected student
         String sql = "SELECT * FROM modules WHERE StudentIDNum='" + selectedStudent.getStudentID() + "'";

         // Execute the SQL statement
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);

         // Loop through the result set and add the Module objects to the list
         while (rs.next()) {
            Module module = new Module(
                    rs.getString("module_num"),
                    rs.getString("module_name"),
                    rs.getString("semester"),
                    rs.getString("grade")
            );
            moduleList.add(module);
         }

         // Close the connection, statement, and result set
         rs.close();
         stmt.close();
         conn.close();

      } catch (SQLException e) {
         e.printStackTrace();
      }

      return moduleList;
   }
}
