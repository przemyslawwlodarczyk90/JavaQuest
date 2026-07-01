package com.example.javaquest._01_fundamentals.Lesson00_JavaPlatformBasics;

public class _Exercises_Lesson00_JavaPlatformBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_PrintJavaVersion {
        /*
         * 🧪 Zadanie 1:
         * Wypisz wersję Javy używając System.getProperty("java.version").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_PrintJavaHome {
        /*
         * 🧪 Zadanie 2:
         * Wypisz ścieżkę do katalogu JDK/JRE używając System.getProperty("java.home").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintOsName {
        /*
         * 🧪 Zadanie 3:
         * Wypisz nazwę systemu operacyjnego używając System.getProperty("os.name").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_PrintJvmName {
        /*
         * 🧪 Zadanie 4:
         * Wypisz nazwę JVM używając System.getProperty("java.vm.name").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_PrintJavaVendor {
        /*
         * 🧪 Zadanie 5:
         * Wypisz producenta środowiska Java używając System.getProperty("java.vendor").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_PrintUserHome {
        /*
         * 🧪 Zadanie 6:
         * Wypisz katalog domowy użytkownika używając System.getProperty("user.home").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_PrintOsVersion {
        /*
         * 🧪 Zadanie 7:
         * Wypisz wersję systemu operacyjnego używając System.getProperty("os.version").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_PrintUserName {
        /*
         * 🧪 Zadanie 8:
         * Wypisz nazwę aktualnie zalogowanego użytkownika systemu.
         * Użyj System.getProperty("user.name").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_PrintJvmVersion {
        /*
         * 🧪 Zadanie 9:
         * Wypisz wersję JVM (java.vm.version) oraz wersję JDK (java.version).
         * Sprawdź, czy są takie same.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_PrintClassPath {
        /*
         * 🧪 Zadanie 10:
         * Wypisz ścieżkę klas (classpath) używając System.getProperty("java.class.path").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_PrintMultipleProperties {
        /*
         * 🧪 Zadanie 11:
         * Wypisz naraz: java.version, java.vendor, os.name, os.version, user.name.
         * Każda właściwość w osobnej linii z czytelną etykietą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_CheckJavaVersionNumber {
        /*
         * 🧪 Zadanie 12:
         * Pobierz wersję Javy jako String, wypisz ją, a następnie sprawdź
         * czy zaczyna się od "21" (czyli czy to Java 21).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_PrintAllJavaProperties {
        /*
         * 🧪 Zadanie 13:
         * Wypisz wszystkie właściwości systemowe, których klucz zaczyna się od "java.".
         * Użyj System.getProperties().forEach().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_PrintAllOsProperties {
        /*
         * 🧪 Zadanie 14:
         * Wypisz wszystkie właściwości systemowe, których klucz zaczyna się od "os.".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_PrintJdkCompilerVendor {
        /*
         * 🧪 Zadanie 15:
         * Sprawdź i wypisz vendor kompilatora Javy.
         * Użyj System.getProperty("java.compiler").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_PrintArchitecture {
        /*
         * 🧪 Zadanie 16:
         * Wypisz architekturę systemu (np. amd64, x86_64).
         * Użyj System.getProperty("os.arch").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CountJavaProperties {
        /*
         * 🧪 Zadanie 17:
         * Policz, ile właściwości systemowych zaczyna się od "java.".
         * Wypisz tę liczbę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_PrintFileSeparator {
        /*
         * 🧪 Zadanie 18:
         * Wypisz separator ścieżek dla aktualnego systemu operacyjnego.
         * Użyj System.getProperty("file.separator").
         * Na Windows to "\", na Linux/Mac to "/".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_JvmRuntimeMemory {
        /*
         * 🧪 Zadanie 19:
         * Wypisz informacje o pamięci JVM:
         * - dostępna pamięć: Runtime.getRuntime().availableProcessors()
         * - pamięć całkowita: Runtime.getRuntime().totalMemory()
         * - wolna pamięć: Runtime.getRuntime().freeMemory()
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_JdkVsJreCheck {
        /*
         * 🧪 Zadanie 20:
         * Wypisz java.home i sprawdź, czy ścieżka zawiera "jdk" lub "jre" (ignorując wielkość liter).
         * Wypisz komunikat: "Używasz JDK" lub "Używasz JRE".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_PrintSystemInfo {
        /*
         * 🧪 Zadanie 21:
         * Stwórz mini-raport środowiska:
         * ==================== ŚRODOWISKO JAVA ====================
         * Wersja Java: ...
         * Producent JVM: ...
         * System operacyjny: ...
         * Architektura: ...
         * Katalog domowy: ...
         * =========================================================
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_CompareJavaAndVmVersion {
        /*
         * 🧪 Zadanie 22:
         * Pobierz java.version i java.vm.version.
         * Wypisz oba, a następnie sprawdź czy są identyczne i wypisz wynik porównania.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_FilterPropertiesByPrefix {
        /*
         * 🧪 Zadanie 23:
         * Wypisz wszystkie właściwości systemowe, których klucze zawierają słowo "version".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_PrintLineSeparator {
        /*
         * 🧪 Zadanie 24:
         * Wypisz separator linii dla aktualnego systemu.
         * Użyj System.getProperty("line.separator").
         * Wypisz jego długość (Windows: 2 znaki \r\n, Linux/Mac: 1 znak \n).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_AvailableProcessors {
        /*
         * 🧪 Zadanie 25:
         * Wypisz liczbę procesorów dostępnych dla JVM.
         * Użyj Runtime.getRuntime().availableProcessors().
         * Wypisz odpowiedni komunikat, np. "Twoja maszyna ma X rdzeni.".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_MaxMemoryVsTotal {
        /*
         * 🧪 Zadanie 26:
         * Pobierz i wypisz:
         * - maxMemory (Runtime.getRuntime().maxMemory())
         * - totalMemory
         * - freeMemory
         * Wszystkie przelicz na megabajty (podziel przez 1024 * 1024).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_SortedPropertiesList {
        /*
         * 🧪 Zadanie 27:
         * Pobierz wszystkie klucze właściwości systemowych, posortuj je alfabetycznie
         * i wypisz każdy klucz razem z jego wartością.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_TempDirectory {
        /*
         * 🧪 Zadanie 28:
         * Wypisz lokalizację katalogu tymczasowego systemu.
         * Użyj System.getProperty("java.io.tmpdir").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_JavaSpecificationVersion {
        /*
         * 🧪 Zadanie 29:
         * Wypisz wersję specyfikacji Javy (java.specification.version)
         * oraz wersję specyfikacji JVM (java.vm.specification.version).
         * Sprawdź, czy są równe i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullEnvironmentDiagnostic {
        /*
         * 🧪 Zadanie 30:
         * Napisz pełną diagnostykę środowiska, która:
         * 1. Wypisuje wersję Javy i sprawdza, czy to Java 17+ (LTS).
         * 2. Wypisuje system operacyjny i architekturę.
         * 3. Wypisuje dostępną pamięć JVM w MB.
         * 4. Wypisuje liczbę procesorów.
         * 5. Wypisuje katalog domowy użytkownika.
         * Sformatuj wyniki jako tabelę lub listę z etykietami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
