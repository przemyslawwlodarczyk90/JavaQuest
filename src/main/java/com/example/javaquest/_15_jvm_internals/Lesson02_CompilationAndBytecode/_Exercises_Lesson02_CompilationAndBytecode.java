package com.example.javaquest._15_jvm_internals.Lesson02_CompilationAndBytecode;

public class _Exercises_Lesson02_CompilationAndBytecode {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_ExplainCompilationStages {
        /*
         * 🧪 Zadanie 1:
         * W komentarzu wypisz w kolejności 4 etapy pracy javac
         * (tokenizacja, parsowanie, analiza semantyczna, generacja
         * bajtkodu) razem z jednym zdaniem opisu każdego etapu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_ExplainMagicNumber {
        /*
         * 🧪 Zadanie 2:
         * W komentarzu wyjaśnij, czym jest "magic number" 0xCAFEBABE w
         * pliku .class i po co JVM go sprawdza.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_CompileSimpleClassInMemory {
        /*
         * 🧪 Zadanie 3:
         * Użyj ToolProvider.getSystemJavaCompiler(), aby skompilować do
         * katalogu tymczasowego prostą klasę "Hello" z metodą main, która
         * wypisuje "Witaj ze skompilowanej klasy!". Wypisz kod wyjścia
         * kompilacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_RunJavapOnJdkClass {
        /*
         * 🧪 Zadanie 4:
         * Uruchom (przez ProcessBuilder, z timeoutem 10 sekund) komendę
         * "javap java.lang.String" (bez -c/-v) i wypisz jej output -
         * powinieneś zobaczyć listę publicznych metod String bez ich ciała.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_ExplainConstantPool {
        /*
         * 🧪 Zadanie 5:
         * W komentarzu wyjaśnij własnymi słowami, czym jest constant pool
         * i podaj przykład jednego rodzaju wpisu, który może się w nim
         * znaleźć (np. nazwa metody, literal String, sygnatura typu).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_CompileAndRunViaProcessBuilder {
        /*
         * 🧪 Zadanie 6:
         * Zapisz plik źródłowy "Add.java" (klasa z metodą main sumującą
         * dwie liczby na sztywno, np. 3 i 4, i wypisującą wynik) do
         * katalogu tymczasowego, skompiluj go PRAWDZIWĄ komendą "javac"
         * (ProcessBuilder + timeout), a potem uruchom PRAWDZIWĄ komendą
         * "java".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_ListClassFileVersionForCurrentJava {
        /*
         * 🧪 Zadanie 7:
         * Skompiluj dowolną prostą klasę, a następnie uruchom na niej
         * "javap -v" i znajdź w outpucie linię z "major version" - wypisz
         * ją osobno (np. przez proste przeszukanie linii tekstu).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ExplainStackMachineVsRegisterMachine {
        /*
         * 🧪 Zadanie 8:
         * W komentarzu wyjaśnij różnicę między maszyną stosową (jak JVM)
         * a maszyną rejestrową (jak typowy fizyczny CPU) - min. 3 zdania.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_CompileClassWithSyntaxErrorAndReadDiagnostics {
        /*
         * 🧪 Zadanie 9:
         * Zapisz do pliku tymczasowego kod z celowym błędem składniowym
         * (np. brakujący średnik) i skompiluj go przez
         * ToolProvider.getSystemJavaCompiler(). Wypisz kod wyjścia (powinien
         * być różny od 0) oraz komunikat błędu przechwycony przez
         * przekazany System.err.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_ListInstructionNamesSeenInLesson {
        /*
         * 🧪 Zadanie 10:
         * Wypisz (na sztywno, bez uruchamiania javap) listę co najmniej 8
         * nazw instrukcji bajtkodu poznanych w tej lekcji razem z krótkim
         * opisem każdej (np. "iadd - dodaje dwie wartosci int ze stosu").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_CompileClassWithLoopAndInspectBytecode {
        /*
         * 🧪 Zadanie 11:
         * Skompiluj klasę z metodą zawierającą pętlę while (np. licząca
         * silnię liczby 5), a następnie uruchom "javap -c" na wyniku i
         * wypisz cały output. Znajdź w nim instrukcję odpowiedzialną za
         * warunek pętli (np. if_icmpge/goto) i skomentuj ją jednym zdaniem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_CompareBytecodeOfTwoSimilarMethods {
        /*
         * 🧪 Zadanie 12:
         * Skompiluj klasę z dwiema metodami: jedną zwracającą sumę dwóch
         * intów przez "a + b", a drugą przez wywołanie Math.addExact(a, b).
         * Uruchom javap -c na obu i porównaj, która generuje więcej
         * instrukcji bajtkodu (wypisz obie sekcje i swój wniosek).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_ExtractMethodDescriptorsWithJavapMinusS {
        /*
         * 🧪 Zadanie 13:
         * Skompiluj klasę z kilkoma przeciążonymi metodami o tej samej
         * nazwie, ale różnych parametrach. Uruchom "javap -s" (opcja
         * pokazująca deskryptory sygnatur, np. "(I)V") i wypisz output,
         * wyjaśniając w komentarzu znaczenie jednego z deskryptorów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_MeasureClassFileSize {
        /*
         * 🧪 Zadanie 14:
         * Skompiluj dwie klasy - jedną bardzo prostą (pusta metoda main) i
         * jedną z wieloma polami i metodami - i porównaj rozmiary
         * wynikowych plików .class (Files.size()). Wypisz oba rozmiary i
         * różnicę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_InspectPrivateVsPublicMethodVisibilityInJavap {
        /*
         * 🧪 Zadanie 15:
         * Skompiluj klasę z jedną metodą public i jedną private. Uruchom
         * zwykłe "javap" (bez flag) na wyniku i sprawdź, czy metoda
         * private w ogóle pojawia się w outpucie - wypisz swoją obserwację.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_CompileClassUsingStringConcatenationAndInspectInvokedynamic {
        /*
         * 🧪 Zadanie 16:
         * Skompiluj klasę, która łączy Stringi operatorem "+" (np. "a" + x
         * + "b"), uruchom "javap -c" i poszukaj w outpucie instrukcji
         * "invokedynamic" (nowoczesny javac generuje ją dla konkatenacji
         * Stringów). Wypisz fragment outputu i skomentuj, co widzisz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CompileWithDebugInfoVsWithout {
        /*
         * 🧪 Zadanie 17:
         * Skompiluj tę samą klasę dwukrotnie: raz z flagą "-g" (pełne
         * informacje debugowania), raz z "-g:none". Porównaj rozmiary
         * wynikowych plików .class i wypisz, która wersja jest większa
         * oraz o ile.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ExtractLineNumberTableFromVerboseJavap {
        /*
         * 🧪 Zadanie 18:
         * Skompiluj klasę z metodą wielolinijkową, uruchom "javap -l"
         * (LineNumberTable) i wypisz fragment outputu pokazujący mapowanie
         * instrukcji bajtkodu na numery linii kodu źródłowego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_CompileInterfaceAndInspectAbstractMethods {
        /*
         * 🧪 Zadanie 19:
         * Skompiluj interfejs z jedną metodą abstrakcyjną i jedną metodą
         * default. Uruchom "javap -c" i sprawdź, czy metoda default ma
         * ciało bajtkodu, a metoda abstrakcyjna nie - wypisz swoją
         * obserwację na podstawie realnego outputu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_CompareBytecodeOfForAndWhileLoop {
        /*
         * 🧪 Zadanie 20:
         * Napisz dwie metody realizujące ten sam algorytm (np. sumowanie
         * liczb od 1 do 10) - jedną za pomocą pętli for, drugą za pomocą
         * pętli while. Skompiluj obie w jednej klasie, porównaj bajtkod
         * (javap -c) i wypisz wniosek, czy różnią się istotnie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_BuildReusableCompileAndDisassembleHelper {
        /*
         * 🧪 Zadanie 21:
         * Napisz własną metodę pomocniczą
         * String compileAndDisassemble(String className, String sourceCode),
         * która: zapisuje źródło do katalogu tymczasowego, kompiluje je
         * przez JavaCompiler, uruchamia "javap -c -v" i zwraca cały output
         * jako String. Przetestuj ją na co najmniej 2 różnych klasach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_CountBytecodeInstructionsPerMethod {
        /*
         * 🧪 Zadanie 22:
         * Uruchom javap -c na skompilowanej klasie z kilkoma metodami,
         * przetwórz tekstowy output (np. linia po linii) i policz, ile
         * instrukcji bajtkodu ma każda metoda. Wypisz ranking metod od
         * najdłuższej do najkrótszej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_DetectRecursiveMethodInBytecode {
        /*
         * 🧪 Zadanie 23:
         * Skompiluj klasę z metodą rekurencyjną (np. silnia). Uruchom
         * javap -c -v i znajdź w bajtkodzie instrukcję "invokevirtual" lub
         * "invokestatic" wywołującą TĘ SAMĄ metodę (po nazwie w constant
         * poolu) - wypisz dowód na to, że metoda woła samą siebie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_CompareAutoboxingBytecodeOverhead {
        /*
         * 🧪 Zadanie 24:
         * Skompiluj metodę sumującą int-y w tablicy int[] oraz metodę
         * sumującą Integer w liście List<Integer> (ta sama logika).
         * Porównaj bajtkod (javap -c) i wypisz, gdzie widać dodatkowe
         * instrukcje związane z autoboxingiem (np. wywołania
         * Integer.valueOf/intValue).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_BuildMiniClassFileHexDumpReader {
        /*
         * 🧪 Zadanie 25:
         * Skompiluj prostą klasę, wczytaj surowe bajty pliku .class
         * (Files.readAllBytes) i wypisz pierwsze 8 bajtów w formacie
         * szesnastkowym (hex) - powinieneś zobaczyć "ca fe ba be" na
         * początku, a potem 4 bajty wersji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_CompareBytecodeAcrossSourceCompatibilityLevels {
        /*
         * 🧪 Zadanie 26:
         * Skompiluj tę samą klasę dwukrotnie z różnymi opcjami "--release"
         * (np. 17 i 21, o ile obie są dostępne w Twoim JDK) i porównaj
         * "major version" w wynikowych plikach .class (javap -v) - wypisz
         * różnicę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_SimulateSwitchExpressionBytecodeInspection {
        /*
         * 🧪 Zadanie 27:
         * Skompiluj metodę z nowoczesnym wyrażeniem switch (Java 14+,
         * np. switch po Stringu z "yield" lub strzałkami "->"). Uruchom
         * javap -c i poszukaj instrukcji "tableswitch" albo "lookupswitch"
         * - wypisz fragment outputu i skomentuj różnicę między nimi.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_BuildBytecodeInstructionFrequencyReport {
        /*
         * 🧪 Zadanie 28:
         * Skompiluj większą klasę (kilka metod, pętle, warunki) i
         * przetwórz output javap -c tak, aby policzyć CZĘSTOŚĆ
         * wystąpień każdej unikalnej instrukcji (np. "iload: 12 razy",
         * "invokevirtual: 3 razy"). Wypisz posortowany ranking.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CompareTryCatchBytecodeExceptionTable {
        /*
         * 🧪 Zadanie 29:
         * Skompiluj metodę z blokiem try-catch. Uruchom "javap -c -v" i
         * znajdź sekcję "Exception table" w outpucie - wypisz ją i
         * wyjaśnij (na podstawie realnych danych) co oznaczają kolumny
         * "from", "to", "target", "type".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullBytecodeExplorationTool {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne narzędzie: przyjmuje kod źródłowy klasy jako
         * String, kompiluje ją, uruchamia javap -c -v, a następnie
         * automatycznie wypisuje: (1) major/minor version, (2) liczbę
         * wpisów w constant poolu, (3) listę nazw metod, (4) dla każdej
         * metody liczbę instrukcji bajtkodu. Przetestuj na własnej,
         * dowolnej klasie z co najmniej 3 metodami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
