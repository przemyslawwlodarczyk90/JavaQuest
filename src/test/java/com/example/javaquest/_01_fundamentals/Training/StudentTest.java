package com.example.javaquest._01_fundamentals.Training;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    static Student student;

   @BeforeAll
    static void setUp(){
     student = new Student("Tomo", 2022, true);
   };


   @Test
    void twoDiffrentStudentsAreNotSamePerson(){
       Student secontStudent = new Student("Miki", 2021, false);

       assertNotEquals(secontStudent, student);
   }

   @Test
    void studentFieldsArePropper(){
       assertAll(
               assert
       );
   }




}