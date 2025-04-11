package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class _Lesson00_JavaPlatformBasics {

    /*
     * ===================================================
     *  JVM, JRE, JDK - CO TO JEST I JAK TO DZIAŁA
     * ===================================================
     */

    /*
     * 🔹 JVM – Java Virtual Machine
     * JVM to wirtualna maszyna, która uruchamia kod bajtkodowy (.class).
     * Działa jak interpreter – przekształca bajtkod na kod maszynowy zależny od systemu operacyjnego.
     *
     * JVM odpowiada m.in. za:
     * - zarządzanie pamięcią (Heap, Stack)
     * - obsługę wyjątków
     * - ładowanie klas
     * - Garbage Collection (GC)
     *
     * Przykład: uruchomienie programu Java = wykonanie kodu przez JVM.
     *
     * 🔹 JRE – Java Runtime Environment
     * To środowisko uruchomieniowe, które zawiera:
     * - JVM
     * - standardowe biblioteki klas (np. java.lang, java.util)
     * - pliki konfiguracyjne
     *
     * JRE NIE zawiera kompilatora – tylko uruchamia gotowe programy.
     *
     * 🔹 JDK – Java Development Kit
     * To zestaw narzędzi do tworzenia programów w Javie.
     * Zawiera:
     * - kompilator `javac` (kompiluje .java do .class)
     * - narzędzie `java` (uruchamia kod przez JVM)
     * - narzędzia: `javadoc`, `jar`, `jshell`
     * - całe JRE
     *
     * Inaczej mówiąc:
     * 📦 JDK = JRE + kompilator i narzędzia programistyczne
     *
     * 🔄 Proces działania aplikacji Java:
     *
     *    [PLIK .java] --javac--> [PLIK .class] --java--> [URUCHOMIONA APLIKACJA]
     *
     *    javac = kompilator → tworzy plik .class
     *    java = uruchamia klasę przez JVM
     */

    public static void main(String[] args) {

        /*
         * ===================================================
         *  🔍 SPRAWDZANIE ŚRODOWISKA W KODZIE JAVA
         * ===================================================
         */

        // Wersja Javy (JDK użyty do uruchomienia)
        String javaVersion = System.getProperty("java.version");
        System.out.println("Wersja Java (JDK/JRE): " + javaVersion);

        // Wersja JVM
        String jvmVersion = System.getProperty("java.vm.version");
        String jvmName = System.getProperty("java.vm.name");
        System.out.println("Wersja JVM: " + jvmVersion);
        System.out.println("Nazwa JVM: " + jvmName);

        // Ścieżka do folderu JDK (JAVA_HOME)
        String javaHome = System.getProperty("java.home");
        System.out.println("Ścieżka JDK/JRE (java.home): " + javaHome);

        // System operacyjny
        System.out.println("System operacyjny: " + System.getProperty("os.name"));

        /*
         * 🔧 TE WŁASNOŚCI SĄ DOSTĘPNE DZIĘKI JVM,
         * który podczas uruchamiania programu ustawia je jako systemowe.
         */

        /*
         * ===================================================
         *  👨‍🔧 JAK SPRAWDZIĆ ŚRODOWISKO W TERMINALU
         * ===================================================
         * (można to zrobić z poziomu terminala, np. w IntelliJ):
         *
         * 👉 `java -version`
         *    - pokazuje wersję JVM i producenta (np. OpenJDK, Oracle)
         *
         * 👉 `javac -version`
         *    - pokazuje wersję kompilatora (czyli JDK)
         *
         * 👉 `echo %JAVA_HOME%` (Windows) lub `echo $JAVA_HOME` (Linux/Mac)
         *    - pokazuje ścieżkę do katalogu JDK
         *
         * 👉 `where java` / `which java`
         *    - pokazuje ścieżkę do pliku wykonywalnego `java`
         */

        /*
         * ===================================================
         *  📚 PODSUMOWANIE W PIGUŁCE:
         *
         * - JVM → uruchamia program
         * - JRE → zawiera JVM i biblioteki do uruchamiania
         * - JDK → zawiera JRE + kompilator + narzędzia programistyczne
         *
         * Bez JDK nie można pisać i kompilować kodu.
         * Bez JRE nie można uruchomić programu (chyba że używasz pełnego JDK).
         *
         * W praktyce: programista używa JDK, użytkownik potrzebuje tylko JRE.
         */

        /*
         * ===================================================
         *  🧪 DODATKOWE: JAK SPRAWDZIĆ WŁASNOŚCI SYSTEMOWE
         * ===================================================
         */
        System.out.println("\nWszystkie właściwości JVM:");
        System.getProperties().forEach((k, v) -> {
            if (k.toString().startsWith("java.") || k.toString().startsWith("os.")) {
                System.out.println(k + " = " + v);
            }
        });
    }
}
