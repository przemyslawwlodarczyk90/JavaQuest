package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class Exercise005_CompareJavaAndJvmVersions {
    public static void main(String[] args) {
        // 🔄 Porównaj wersję JDK (java.version) z wersją JVM (java.vm.version)
        // Jeśli są różne, wypisz ostrzeżenie, np. "Uwaga: wersje różne!"

        String jvm = System.getProperty("java.vm.version");
        String jdk = System.getProperty("java.version");

        if (jvm.equals(jdk)){
            System.out.println(jvm);
            System.out.println(jdk);
            System.out.println("Wersje takie same");
        } else  {
            System.out.println(jvm);
            System.out.println(jdk);
            System.out.println("Wersje są różne");
        }
    }
}
