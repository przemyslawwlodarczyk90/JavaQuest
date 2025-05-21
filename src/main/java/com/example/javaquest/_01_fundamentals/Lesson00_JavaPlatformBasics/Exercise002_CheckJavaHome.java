package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class Exercise002_CheckJavaHome {
    public static void main(String[] args) {
        // 🔍 Sprawdź, czy systemowa ścieżka java.home zawiera słowo „jdk” lub „java”
        // Jeśli tak, wypisz „Środowisko ustawione poprawnie”

        String javaHome = System.getProperty("java.home");

        if (javaHome != null && (javaHome.toLowerCase().contains("jdk") || javaHome.toLowerCase().contains("java"))) {
            System.out.println("Środowisko ustawione poprawnie");
        } else {
            System.out.println("Środowisko może być niepoprawnie skonfigurowane");
        }
    }
}
