package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class Exercise001_JdkJreJvm {
    public static void main(String[] args) {
        // 🔧 Wypisz informacje o JVM, JDK i JRE z użyciem System.getProperty()
        // - java.version
        // - java.vm.name
        // - java.home
        // - os.name
        // 👇 Dodaj kod tutaj

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.vm.name"));
        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("os.name"));
    }
}
