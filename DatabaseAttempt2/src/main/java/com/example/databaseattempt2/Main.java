package com.example.databaseattempt2;
/*
The Main class of the MTU Student System application is responsible for displaying a TableView that shows a list of students retrieved from a MySQL database.
It extends the javafx.application.Application class and provides an implementation for the start() method. The start() method sets up the UI elements,
connects to the database, retrieves data and populates the TableView with the data.
The class includes the JDBC driver name, database URL, database credentials
and UI elements such as TableView, Buttons, and ImageView. It also defines methods to handle events, such as deleting a row,
adding a new student, updating a student, viewing a student's profile, and displaying passed modules.
The Main class serves as the entry point for the MTU Student System application and provides the user interface
for interacting with the data stored in the MySQL database.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

public class Main extends Application {

   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/mtu_student_system";

   // Database credentials
   static final String USER = "root";
   static final String PASS = "passwordBailey1608!";


   @Override
   public void start(Stage primaryStage) throws Exception {

      InputStream stream = new FileInputStream("C:/Users/brian/OneDrive - mycit.ie/Munster Technological University/Object Oriented Projects/OOP Assignment2/DatabaseAttempt2/src/main/resources/rsz_mtuLogoBanner.png");
      Image image = new Image(stream);
      ImageView imageView = new ImageView();
      imageView.setImage(image);
      imageView.setX(10);
      imageView.setY(10);
      imageView.setFitWidth(545);
      imageView.setFitHeight(150);
      imageView.setPreserveRatio(true);
      Group root2 = new Group(imageView);
      VBox headers = new VBox();
      headers.getChildren().add(root2);


      // Create a TableView to display the data
      TableView<Student> table = new TableView<>();

      // Connect to the database
      Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

      // Create a SQL statement to retrieve data from the "mtu.student" table
      String sql = "SELECT * FROM mtustudent";

      // Execute the SQL statement
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      // Create a list to store the Student objects
      ObservableList<Student> studentList = FXCollections.observableArrayList();

      // Loop through the result set and add the Student objects to the list
      while (rs.next()) {
         Student student = new Student(
                 rs.getString("StudentIDNum"),
                 rs.getString("Name"),
                 rs.getString("DateOfBirth")
         );
         studentList.add(student);
      }

      // Close the connection, statement, and result set
      rs.close();
      stmt.close();
      conn.close();

      // Create the columns for the TableView
      TableColumn<Student, String> idCol = new TableColumn<>("ID Num");
      idCol.setCellValueFactory(cellData -> cellData.getValue().studentIDProperty());
      idCol.setPrefWidth(180);

      TableColumn<Student, String> nameCol = new TableColumn<>("Name");
      nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
      nameCol.setPrefWidth(180);

      TableColumn<Student, String> dobCol = new TableColumn<>("Date Of Birth");
      dobCol.setCellValueFactory(cellData -> cellData.getValue().DOBProperty());
      dobCol.setPrefWidth(180);

      // Add the columns to the TableView
      table.getColumns().addAll(idCol, nameCol, dobCol);
      // Add the data to the TableView
      table.setItems(studentList);

      // Create Buttons
      Button deleteButton = new Button("Delete");
      deleteButton.setPrefWidth(180);

      Button addStudent = new Button("Add Student");
      addStudent.setPrefWidth(180);

      Button updateStudent = new Button("Update Student");
      updateStudent.setPrefWidth(180);

      Button refreshButton = new Button("Refresh");
      refreshButton.setPrefWidth(180);

      Button viewProfile = new Button("View Student Profile");
      viewProfile.setPrefWidth(180);

      Button passedModsBtn = new Button("Passed Modules");
      passedModsBtn.setPrefWidth(170);

      Button searchButton = new Button("Search");
      searchButton.setPrefWidth(180);

      Button memoryLeakButton = new Button("Memory Leak");
      memoryLeakButton.setStyle("-fx-font-weight: bold; -fx-background-color: red");

      memoryLeakButton.setPrefWidth(165);
      memoryLeakButton.setOnAction(event -> {
         try {
            while(true) {
               Student student = new Student("12345", "John Doe", "01/01/2000");
            }
         } catch(OutOfMemoryError e) {
            System.out.println("Out of memory error occurred");
            e.printStackTrace();
         }
      });

      searchButton.setOnAction(event -> {
         SearchWindow.openSearchWindow();
      });


      //All the event handlers
      //Delete
      deleteButton.setOnAction(event -> {
         Delete.deleteSelectedRows(table, studentList);
      });
      //Add Student
      addStudent.setOnAction(e -> {
         AddStudent.display();
      });
      //Update Student
      updateStudent.setOnAction(event -> {
         // Get the selected student
         Student selectedStudent = table.getSelectionModel().getSelectedItem();
         if (selectedStudent != null) {
            // Open the UpdateStudent window and pass in the selected student
            UpdateStudent.display(selectedStudent);
            // Refresh the TableView to show any changes
            table.refresh();
         }
      });
      //View Profile
      viewProfile.setOnAction(e -> {
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

      passedModsBtn.setOnAction(e -> {
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

      //Refresh Table
      refreshButton.setOnAction(event -> {
         try {
            // Connect to the database
            Connection conn2 = DriverManager.getConnection(DB_URL, USER, PASS);
            // Create a SQL statement to retrieve data from the "mtu.student" table
            String sql2 = "SELECT * FROM mtustudent";
            // Execute the SQL statement
            Statement stmt2 = conn2.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql2);
            // Clear the existing data in the studentList
            studentList.clear();
            // Loop through the result set and add the Student objects to the list
            while (rs2.next()) {
               Student student = new Student(
                       rs2.getString("StudentIDNum"),
                       rs2.getString("Name"),
                       rs2.getString("DateOfBirth")
               );
               studentList.add(student);
            }
            // Close the connection, statement, and result set
            rs2.close();
            stmt2.close();
            conn2.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      });

      VBox tableVbox = new VBox();
      tableVbox.setSpacing(10);
      tableVbox.getChildren().add(table);

      HBox threeButtons = new HBox();
      threeButtons.setSpacing(10);
      threeButtons.setPadding(new Insets(10));
      threeButtons.getChildren().addAll(addStudent,deleteButton,refreshButton);

      HBox nextThree = new HBox();
      nextThree.setSpacing(10);
      nextThree.setPadding(new Insets(10));
      nextThree.getChildren().addAll(updateStudent,viewProfile,searchButton);

      HBox lastButton = new HBox();
      lastButton.setSpacing(10);
      lastButton.setPadding(new Insets(10));
      lastButton.getChildren().addAll(passedModsBtn, memoryLeakButton);

      VBox root = new VBox();
      root.getChildren().addAll(headers,tableVbox,threeButtons,nextThree,lastButton);

      // Create a Scene and set it on the primary stage
      Scene scene = new Scene(root, 545, 528);
      primaryStage.setScene(scene);
      primaryStage.setTitle("MTU Student Record System");
      primaryStage.getIcons().add(new Image(UpdateModuleGradeInfo.class.getResourceAsStream("/MTU_logo.png")));
      primaryStage.show();
   }
   public static void main(String[] args) {
      launch(args);
   }
}

