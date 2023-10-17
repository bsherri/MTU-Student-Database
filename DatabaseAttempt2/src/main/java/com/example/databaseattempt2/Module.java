package com.example.databaseattempt2;
/*
This class is where we have defined everything we need for our module.
It contains information about the course name, identification number, semester, and grade received by the student.
it also contains all the necessary getters and setters we need.
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Module {
   private final StringProperty module_num ;
   private final StringProperty module_name;
   private final StringProperty semester;
   private final StringProperty grade;

   public Module(String module_num, String module_name, String semester, String grade){
      this.module_num = new SimpleStringProperty(module_num);
      this.module_name = new SimpleStringProperty(module_name);
      this.semester = new SimpleStringProperty(semester);
      this.grade = new SimpleStringProperty(grade);
   }

   public StringProperty module_numProperty(){return module_num;}
   public String getModule_num(){return module_num.get();}
   public void setModule_num(String module_num){this.module_num.set(module_num);}
   public StringProperty module_nameProperty(){return module_name;}
   public String getModule_name(){return module_name.get();}
   public void setModule_name(String module_name){this.module_name.set(module_name);}
   public StringProperty semesterProperty(){return semester;}
   public String getSemester(){return semester.get();}
   public void setSemester(String semester){this.semester.set(semester);}
   public StringProperty gradeProperty(){return grade;}
   public String getGrade(){return grade.get();}
   public void setGrade(String grade){this.grade.set(grade);}

}
