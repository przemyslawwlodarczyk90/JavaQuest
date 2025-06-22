package com.example.javaquest._01_fundamentals.Training;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    /*public class Person {
        private String name;
        private int age;
        private boolean employed;

        public Person(String name, int age, boolean employed) {
            this.name = name;
            this.age = age;
            this.employed = employed;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public boolean isEmployed() {
            return employed;
        }
    } */


   static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person("Tomo", 20, true);
    }


    @Test
    void checkTheNameIsProper() {
        assertEquals("Tomo", person.getName());
    }


    @Test
    void checkTheAgeIsProper() {
        assertEquals(20, person.getAge());
    }

    @Test
    void  checkThePersonIsEmployed(){
        assertTrue(person.isEmployed());
    }


    }








