package com.example.databaseattempt2;
/*
This class provides functionality for searching for a student by their name, id or DOB. it returns whatever students fit our search parameters
to a tableview
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchWindow {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/mtu_student_system";

   static final String USER = "root";
   static final String PASS = "passwordBailey1608!";

   public static void search(String nameSearchString, String idSearchString, String dobSearchString, TableView<Student> table) {
      // Create a list to store the Student objects
      ObservableList<Student> studentList = FXCollections.observableArrayList();

      // Connect to the database
      try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement()) {
         String sql = "SELECT * FROM mtustudent WHERE 1=1";
         if (!nameSearchString.isEmpty()) {
            sql += " AND Name LIKE '%" + nameSearchString + "%'";
         }
         if (!idSearchString.isEmpty()) {
            sql += " AND StudentIDNum LIKE '%" + idSearchString + "%'";
         }
         if (!dobSearchString.isEmpty()) {
            sql += " AND DateOfBirth LIKE '%" + dobSearchString + "%'";
         }

         // Execute the SQL statement
         ResultSet rs = stmt.executeQuery(sql);

         // Loop through the result set and add the Student objects to the list
         while (rs.next()) {
            Student student = new Student(
                    rs.getString("StudentIDNum"),
                    rs.getString("Name"),
                    rs.getString("DateOfBirth")
            );
            studentList.add(student);
         }

         // Close the result set and statement
         rs.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      // Set the items in the table
      table.setItems(studentList);
   }

   public static void openSearchWindow() {
      Stage searchStage = new Stage();
      searchStage.setTitle("Search For Student");

      //Labels and Textfields for all necessary items.
      Label nameLabel = new Label("Name:");
      nameLabel.setPrefWidth(100);
      nameLabel.setMaxWidth(100);
      Label idLabel = new Label("ID:");
      idLabel.setPrefWidth(100);
      idLabel.setMaxWidth(100);
      Label dobLabel = new Label("Date of Birth:");
      dobLabel.setPrefWidth(100);
      dobLabel.setMaxWidth(100);
      TextField nameField = new TextField();
      nameField.setPrefWidth(200);
      nameField.setMaxWidth(200);
      TextField idField = new TextField();
      idField.setPrefWidth(200);
      idField.setMaxWidth(200);
      TextField dobField = new TextField();
      dobField.setPrefWidth(200);
      dobField.setMaxWidth(200);

      HBox nameBox = new HBox();
      nameBox.getChildren().addAll(nameLabel, nameField);
      HBox idBox = new HBox();
      idBox.getChildren().addAll(idLabel, idField);
      HBox dobBox = new HBox();
      dobBox.getChildren().addAll(dobLabel, dobField);

      Button viewMods = new Button("View Modules");
      viewMods.setPrefWidth(200);
      viewMods.setPadding(new Insets(10));

      Button viewPassed = new Button("Passed Modules");
      viewPassed.setPrefWidth(200);
      viewPassed.setPadding(new Insets(10));

      Button searchButton = new Button("Search");
      searchButton.setPrefWidth(200);
      searchButton.setPadding(new Insets(10));

      HBox buttonBox = new HBox();
      buttonBox.getChildren().addAll(searchButton, viewMods, viewPassed);

      TableView<Student> table = new TableView<>();
      TableColumn<Student, String> studentIDCol = new TableColumn<>("Student ID");
      studentIDCol.setCellValueFactory(cellData -> cellData.getValue().studentIDProperty());
      studentIDCol.setPrefWidth(75);

      TableColumn<Student, String> nameCol = new TableColumn<>("Name");
      nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
      nameCol.setPrefWidth(250);

      TableColumn<Student, String> dobCol = new TableColumn<>("D.O.B");
      dobCol.setCellValueFactory(cellData -> cellData.getValue().DOBProperty());
      dobCol.setPrefWidth(75);

      table.getColumns().add(studentIDCol);
      table.getColumns().add(nameCol);
      table.getColumns().add(dobCol);

      VBox vbox = new VBox();
      vbox.getChildren().addAll(nameBox, idBox, dobBox, table, buttonBox);
      vbox.setSpacing(5);

      //View all the modules
      viewMods.setOnAction(w -> {
         Student selectedStudent = table.getSelectionModel().getSelectedItem();
         if (selectedStudent != null) {
            try {
               ViewStudentProfile.display(selectedStudent);
            } catch (FileNotFoundException ex) {
               throw new RuntimeException(ex);
            }
            table.refresh();
         }
      });

      //view all the passed modules for the student
      viewPassed.setOnAction(e -> {
         Student selectedStudent = table.getSelectionModel().getSelectedItem();
         if (selectedStudent != null) {
            try {
               passedModules.display(selectedStudent);
            } catch (FileNotFoundException ex) {
               throw new RuntimeException(ex);
            }
            table.refresh();
         }
      });

      searchButton.setOnAction(e -> {
         String nameSearchString = nameField.getText();
         String idSearchString = idField.getText();
         String dobSearchString = dobField.getText();
         search(nameSearchString, idSearchString, dobSearchString, table);
      });


      Scene scene = new Scene(vbox, 400, 400);
      searchStage.setScene(scene);
      searchStage.setResizable(false);
      searchStage.getIcons().add(new Image(UpdateModuleGradeInfo.class.getResourceAsStream("/MTU_logo.png")));
      searchStage.show();
   }
}
