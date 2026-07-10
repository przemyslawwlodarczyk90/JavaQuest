package com.example.javaquest._15_jvm_internals.Lesson01_JdkJreJvmAndSpecification;

public class _Exercises_Lesson01_JdkJreJvmAndSpecification {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_PrintVmName {
        /*
         * 🧪 Zadanie 1:
         * Wypisz nazwę implementacji JVM używając System.getProperty("java.vm.name").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_PrintVmVersion {
        /*
         * 🧪 Zadanie 2:
         * Wypisz wersję JVM używając System.getProperty("java.vm.version").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintVmVendor {
        /*
         * 🧪 Zadanie 3:
         * Wypisz producenta JVM używając System.getProperty("java.vm.vendor").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_PrintSpecificationVersion {
        /*
         * 🧪 Zadanie 4:
         * Wypisz wersję specyfikacji Javy używając
         * System.getProperty("java.specification.version").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_PrintSpecificationName {
        /*
         * 🧪 Zadanie 5:
         * Wypisz nazwę specyfikacji Javy używając
         * System.getProperty("java.specification.name").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_PrintRuntimeVersionObject {
        /*
         * 🧪 Zadanie 6:
         * Pobierz Runtime.version() i wypisz cały obiekt (toString()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_PrintFeatureVersion {
        /*
         * 🧪 Zadanie 7:
         * Pobierz Runtime.version().feature() i wypisz go z etykietą
         * "Glowny numer wersji Javy: ".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_CompareVmNameContainsHotSpot {
        /*
         * 🧪 Zadanie 8:
         * Sprawdź, czy wartość java.vm.name zawiera napis "HotSpot" (bez
         * uwzględniania wielkości liter) i wypisz odpowiedni komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_PrintJavaVendorVersion {
        /*
         * 🧪 Zadanie 9:
         * Wypisz System.getProperty("java.vendor.version") - może zwrócić
         * null, jeśli producent nie ustawił tej właściwości. Obsłuż ten
         * przypadek i wypisz "brak" zamiast null.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_ListAllVmProperties {
        /*
         * 🧪 Zadanie 10:
         * Wypisz wszystkie właściwości systemowe, których klucz zawiera
         * napis "vm" (np. java.vm.name, java.vm.version...).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_BuildJvmIdentityReport {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj krótki raport tożsamości JVM zawierający: nazwę, wersję,
         * producenta JVM oraz wersję specyfikacji, sformatowany jako
         * kilka linii tekstu z czytelnymi etykietami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_CompareSpecVersionVsJavaVersion {
        /*
         * 🧪 Zadanie 12:
         * Pobierz java.specification.version oraz java.version. Wypisz
         * oba i sprawdź, czy java.version zaczyna się od wartości
         * specyfikacji (np. "21" zawarte w "21.0.2").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_DetectImplementationGuess {
        /*
         * 🧪 Zadanie 13:
         * Na podstawie java.vm.name napisz prostą logikę if/else, która
         * wypisuje "To jest HotSpot", "To jest GraalVM", "To jest OpenJ9"
         * albo "Nieznana implementacja JVM" (sprawdzając odpowiednie
         * fragmenty tekstu, ignorując wielkość liter).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_PrintRuntimeVersionParts {
        /*
         * 🧪 Zadanie 14:
         * Wypisz osobno: feature(), interim(), update() oraz patch() z
         * Runtime.version(), każdy w osobnej linii z etykietą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_CheckIfPreReleaseVersion {
        /*
         * 🧪 Zadanie 15:
         * Sprawdź metodą Runtime.version().pre() czy aktualna JVM jest
         * wersją pre-release (np. early access build). Wypisz Optional
         * i jego zawartość, albo komunikat "wersja stabilna", jeśli pusty.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_ListJavaAndVmPropertiesSorted {
        /*
         * 🧪 Zadanie 16:
         * Zbierz wszystkie właściwości systemowe, których klucz zaczyna
         * się od "java.vm" LUB "java.specification", posortuj je
         * alfabetycznie po kluczu i wypisz każdą w formacie "klucz = wartosc".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CompareTwoRuntimeVersionsManually {
        /*
         * 🧪 Zadanie 17:
         * Pobierz Runtime.version() dwukrotnie do dwóch zmiennych i
         * porównaj je metodą compareTo() oraz equals(). Wypisz wyniki
         * obu porównań (powinny być odpowiednio 0 i true).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ExplainJlsVsJvmsInComment {
        /*
         * 🧪 Zadanie 18:
         * Bez uruchamiania kodu: w komentarzu (min. 4 zdania) wyjaśnij
         * własnymi słowami różnicę między JLS a JVMS oraz podaj po jednym
         * przykładzie tego, co reguluje każda z tych specyfikacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj (uzupełnij komentarz powyżej)
        }
    }

    static class Exercise19_SimulateImplementationCheckFunction {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę pomocniczą String describeJvmImplementation(String vmName),
         * która na podstawie przekazanego napisu (np. "OpenJDK 64-Bit Server VM",
         * "Eclipse OpenJ9 VM", "Substrate VM") zwraca opis implementacji.
         * Wywołaj ją dla co najmniej 3 różnych przykładowych napisów (na sztywno
         * w kodzie, nie muszą pochodzić z realnej JVM).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_BuildComparisonTableOfKnownJvms {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (na sztywno w kodzie, jako tablicę/listę rekordów lub
         * String[][]) porównawczą tabelkę 3 implementacji JVM (HotSpot,
         * GraalVM, Eclipse OpenJ9) z kolumnami: nazwa, kluczowa cecha,
         * typowe zastosowanie. Wypisz ją sformatowaną w konsoli.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_JvmIdentityDiagnosticTool {
        /*
         * 🧪 Zadanie 21:
         * Napisz małe "narzędzie diagnostyczne": metodę, która zbiera
         * WSZYSTKIE właściwości systemowe pasujące do wzorca (np. zaczynające
         * się od "java.") do java.util.Map<String,String>, a następnie
         * wypisuje je pogrupowane w sekcje: "JVM", "Specyfikacja", "Vendor",
         * "Inne" (na podstawie fragmentu klucza). Wywołaj i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_CompareCurrentJvmAgainstMinimumRequirement {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę boolean meetsMinimumJavaVersion(int required),
         * która porównuje Runtime.version().feature() z wymaganym numerem
         * i zwraca true/false. Przetestuj ją dla wymagań 8, 11, 17, 21 i 25
         * oraz wypisz wynik każdego porównania.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_ParseVmNameIntoTokens {
        /*
         * 🧪 Zadanie 23:
         * Pobierz java.vm.name, rozbij go na tokeny (słowa rozdzielone
         * spacjami, użyj String.split) i wypisz każdy token w osobnej linii
         * z numerem porządkowym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_SafePropertyReaderWithDefault {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę String readPropertyOrDefault(String key, String def),
         * która używa System.getProperty(key, def) (wersja z domyślną
         * wartością). Przetestuj ją dla istniejącego klucza ("java.vm.name")
         * i dla nieistniejącego (np. "moj.wymyslony.klucz") z domyślną
         * wartością "BRAK".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_CountPropertiesByPrefixGroups {
        /*
         * 🧪 Zadanie 25:
         * Policz, ile właściwości systemowych zaczyna się kolejno od:
         * "java.", "os.", "user.", "sun." (jeśli występują), i wypisz
         * te liczby jako podsumowanie (np. "java.* -> 45 wlasciwosci").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_SimulateSpecificationComplianceCheck {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę, która symuluje "sprawdzenie zgodności ze
         * specyfikacją": przyjmuje listę wymaganych właściwości systemowych
         * (np. "java.specification.version", "java.vm.name",
         * "java.vendor") i sprawdza, czy WSZYSTKIE są dostępne (niepuste)
         * w bieżącej JVM. Wypisz raport OK/BRAK dla każdej z nich.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_BuildFullJvmReportWithFormatting {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj sformatowany raport (ramka z "=", jak w innych lekcjach
         * kursu) zawierający WSZYSTKIE informacje z tej lekcji: nazwę i
         * wersję JVM, producenta, wersję i nazwę specyfikacji, pełny
         * Runtime.version() oraz jego cztery składowe liczbowe.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_CompareTwoHypotheticalJvmVersions {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj (bez realnego drugiego JVM) porównanie dwóch numerów
         * wersji podanych jako stringi (np. "17.0.9" i "21.0.2") - rozbij
         * je po kropce, porównaj numerycznie pierwszy segment (major) i
         * wypisz, która wersja jest nowsza.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_ExplainWriteOnceRunAnywhereWithEvidence {
        /*
         * 🧪 Zadanie 29:
         * Wypisz aktualne wartości java.vm.name oraz os.name, a następnie
         * w komentarzu (min. 3 zdania) wyjaśnij, dlaczego TEN SAM plik
         * .class mógłby zostać uruchomiony na innej implementacji JVM lub
         * innym systemie operacyjnym bez rekompilacji - odwołaj się do
         * pojęcia specyfikacji JVMS poznanego w tej lekcji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullJvmAndSpecificationAudit {
        /*
         * 🧪 Zadanie 30:
         * Napisz kompletne narzędzie audytujące bieżące środowisko JVM:
         * 1. Wypisuje tożsamość JVM (nazwa, wersja, producent).
         * 2. Wypisuje wersję i nazwę specyfikacji.
         * 3. Zgaduje implementację (HotSpot/GraalVM/OpenJ9/nieznana) na
         *    podstawie java.vm.name.
         * 4. Sprawdza, czy Runtime.version().feature() >= 21 i wypisuje
         *    ostrzeżenie, jeśli nie.
         * 5. Wypisuje pełne podsumowanie jako czytelny, sformatowany raport.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
