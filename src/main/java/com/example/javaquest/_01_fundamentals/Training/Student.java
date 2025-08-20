package com.example.javaquest._01_fundamentals.Training;

public class Student {
    private String name;
    private int year;
    private boolean fullTime;

    public Student(String name, int year, boolean fullTime) {
        this.name = name;
        this.year = year;
        this.fullTime = fullTime;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public boolean isFullTime() {
        return fullTime;
    }
}
