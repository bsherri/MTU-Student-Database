module com.example.databaseattempt2 {
   requires javafx.controls;
   requires javafx.fxml;
   requires java.sql;


   opens com.example.databaseattempt2 to javafx.fxml;
   exports com.example.databaseattempt2;
}