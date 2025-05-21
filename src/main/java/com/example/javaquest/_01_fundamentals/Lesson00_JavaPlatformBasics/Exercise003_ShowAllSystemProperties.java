package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class Exercise003_ShowAllSystemProperties {
    public static void main(String[] args) {
        // 📋 Wypisz wszystkie właściwości systemowe JVM, które zaczynają się od „java.” lub „os.”
        // Użyj System.getProperties()


        System.out.println("\nWszystkie właściwości JVM:");
        System.getProperties().forEach((key, value) -> {
            String keyStr = key.toString();
            if (keyStr.startsWith("java.") || keyStr.startsWith("os.")) {
                System.out.println(keyStr + " = " + value);
            }
        });



    }
}
