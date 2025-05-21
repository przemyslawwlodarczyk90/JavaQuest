package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class Exercise004_CheckCompilerVendor {
    public static void main(String[] args) {
        // 🔎 Wypisz nazwę producenta JVM oraz sprawdź czy to Oracle, OpenJDK, czy coś innego.
        // Użyj System.getProperty("java.vm.vendor")


        String vendor = System.getProperty("java.vm.vendor");
        System.out.println("Producent JVM: " + vendor);

        if (vendor.toLowerCase().contains("oracle")) {
            System.out.println("To JVM od Oracle.");
        } else if (vendor.toLowerCase().contains("openjdk")) {
            System.out.println("To JVM OpenJDK.");
        } else {
            System.out.println("To inny producent JVM.");
        }
    }
}
