package com.example.javaquest._01_fundamentals.Training;

public class Person {
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
}
