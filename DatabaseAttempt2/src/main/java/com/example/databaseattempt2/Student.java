package com.example.databaseattempt2;
/*
This class is where we have defined everything we need for our Students.
It contains information about the student name, student number, Date of birth for the student.
It also contains all the necessary getters and setters we need.
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Student {

   private final StringProperty studentID;
   private final StringProperty name;
   private final StringProperty DOB;

   public Student(String studentID, String name, String DOB) {
      this.studentID = new SimpleStringProperty(studentID);
      this.name = new SimpleStringProperty(name);
      this.DOB = new SimpleStringProperty(DOB);
   }

   public String getStudentID() {
      return studentID.get();
   }

   public StringProperty studentIDProperty() {
      return studentID;
   }

   public void setStudentID(String studentID) {
      this.studentID.set(studentID);
   }

   public String getName() {
      return name.get();
   }

   public StringProperty nameProperty() {
      return name;
   }

   public void setName(String name) {
      this.name.set(name);
   }

   public String getDOB() {
      return DOB.get();
   }

   public StringProperty DOBProperty() {
      return DOB;
   }

   public void setDOB(String DOB) {
      this.DOB.set(DOB);
   }


}
