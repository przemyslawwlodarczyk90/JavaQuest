package com.example.javaquest._15_jvm_internals.Lesson02_CompilationAndBytecode;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class _Lesson02_CompilationAndBytecode {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: KOMPILACJA I BAJTKOD ===\n");

        /*
         * ============================================================
         * 🔹 PIPELINE javac: OD ZRODLA DO BAJTKODU
         * ============================================================
         * Kiedy uruchamiasz "javac Foo.java", w skrocie dzieje sie kilka
         * krokow (kazdy z nich to caly, osobny temat kompilatorow - tutaj
         * tylko konceptualny przeglad):
         *
         *  1) TOKENIZACJA (lexical analysis) - tekst zrodlowy zostaje
         *     podzielony na najmniejsze sensowne "tokeny": slowa kluczowe
         *     (class, if, return...), identyfikatory, literaly, operatory,
         *     nawiasy. Np. "int x = 1 + 2;" -> tokeny [int][x][=][1][+][2][;].
         *  2) PARSOWANIE (syntax analysis) - tokeny ukladaja sie w drzewo
         *     skladniowe (AST - Abstract Syntax Tree), zgodne z gramatyka
         *     jezyka opisana w JLS. Na tym etapie wykrywane sa bledy
         *     skladniowe (np. brakujacy srednik).
         *  3) ANALIZA SEMANTYCZNA - sprawdzanie typow (type checking),
         *     rozwiazywanie nazw (czy zmienna/metoda istnieje), wnioskowanie
         *     typow generycznych. Tu wykrywane sa bledy typu "incompatible
         *     types" albo "cannot find symbol".
         *  4) GENERACJA BAJTKODU - drzewo AST zostaje przetlumaczone na
         *     instrukcje bajtkodu (zgodnie z JVMS) i zapisane w formacie
         *     pliku .class.
         *
         * Wynikiem calego pipeline'u jest plik .class - NIE kod maszynowy
         * procesora, tylko posrednia reprezentacja, ktora dopiero JVM
         * (interpreter + JIT, patrz lekcje 13-14) przeksztalci w
         * instrukcje faktycznie wykonywane przez CPU.
         */
        explainCompilationPipeline();

        /*
         * ============================================================
         * 🔹 STRUKTURA PLIKU .class (KONCEPCYJNIE)
         * ============================================================
         * Plik .class to scisle okreslony format binarny (opisany co do
         * bajtu w JVMS, rozdzial 4). Najwazniejsze sekcje, w kolejnosci:
         *
         *  - MAGIC NUMBER - pierwsze 4 bajty, ZAWSZE 0xCAFEBABE. To "podpis"
         *    pozwalajacy JVM natychmiast rozpoznac, ze to prawdziwy plik
         *    klasy Javy (a nie przypadkowy plik binarny).
         *  - MINOR/MAJOR VERSION - wersja formatu bajtkodu (np. major 65
         *    odpowiada Javie 21) - JVM odrzuci plik nowszy niz sama siebie.
         *  - CONSTANT POOL (pula stalych) - tablica WSZYSTKICH literalow,
         *    nazw klas/metod/pol, sygnatur typow uzywanych w tej klasie.
         *    Reszta pliku odwoluje sie do stalych przez INDEKS w tej puli
         *    (np. instrukcja "wywolaj metode System.out.println" w bajtkodzie
         *    to tak naprawde "invokevirtual #7", gdzie #7 wskazuje wpis w
         *    constant poolu opisujacy te konkretna metode).
         *  - ACCESS FLAGS - modyfikatory samej klasy (public, final,
         *    interface, abstract...).
         *  - THIS_CLASS / SUPER_CLASS / INTERFACES - wskazniki (znow do
         *    constant poola) na nazwe tej klasy, jej klase nadrzedna i
         *    implementowane interfejsy.
         *  - FIELDS - lista pol (kazde: nazwa, typ, modyfikatory).
         *  - METHODS - lista metod, a w nich atrybut "Code" zawierajacy
         *    FAKTYCZNY bajtkod (sekwencje instrukcji) danej metody.
         *  - ATTRIBUTES - dodatkowe metadane (np. LineNumberTable - mapowanie
         *    instrukcji na numery linii zrodla, potrzebne do stack trace).
         *
         * Ponizej zobaczymy fragmenty TEJ struktury na prawdziwym przykladzie
         * - narzedzie javap potrafi je czytelnie wypisac.
         */
        explainClassFileStructure();

        /*
         * ============================================================
         * 🔍 DEMO: KOMPILUJEMY PRAWDZIWA KLASE I CZYTAMY JEJ BAJTKOD
         * ============================================================
         * Piszemy mala klase z prostymi operacjami arytmetycznymi i petla,
         * kompilujemy ja PROGRAMOWO (javax.tools.JavaCompiler - ten sam
         * mechanizm co w _11_buildtools/Lesson02), a nastepnie wywolujemy
         * PRAWDZIWE narzedzie "javap -c -v" jako podproces, zeby zobaczyc
         * REALNY bajtkod tej konkretnej klasy.
         */
        Path workDir = Files.createTempDirectory("lesson02-bytecode");
        try {
            Path srcDir = workDir.resolve("src");
            Path classesDir = workDir.resolve("classes");
            Files.createDirectories(srcDir);
            Files.createDirectories(classesDir);

            Path calculatorSrc = srcDir.resolve("Calculator.java");
            Files.writeString(calculatorSrc, """
                    public class Calculator {

                        public int sumOfSquares(int limit) {
                            int total = 0;
                            for (int i = 1; i <= limit; i++) {
                                total += i * i;
                            }
                            return total;
                        }

                        public static void main(String[] args) {
                            Calculator calculator = new Calculator();
                            int result = calculator.sumOfSquares(5);
                            System.out.println("Suma kwadratow 1..5 = " + result);
                        }
                    }
                    """);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("Brak systemowego kompilatora - upewnij sie, ze uzywasz JDK, nie JRE.");
            }

            System.out.println("--- Kompilacja Calculator.java (javax.tools.JavaCompiler) ---");
            int compileResult = compiler.run(null, System.out, System.err,
                    "-d", classesDir.toString(),
                    "-g", // dolacz tabele debugowania (numery linii, lokalne zmienne) - przydatne w javap -v
                    calculatorSrc.toString());
            System.out.println("Wynik kompilacji (0 = sukces): " + compileResult + "\n");

            System.out.println("--- Uruchomienie skompilowanej klasy (dla kontekstu) ---");
            String javaHome = System.getProperty("java.home");
            String javaExe = javaHome + File.separator + "bin" + File.separator + "java";
            String javapExe = javaHome + File.separator + "bin" + File.separator + "javap";

            runProcess("java (uruchomienie Calculator.main)",
                    javaExe, "-cp", classesDir.toString(), "Calculator");

            System.out.println("\n--- PRAWDZIWY bajtkod przez 'javap -c -v' ---");
            runProcess("javap -c -v Calculator",
                    javapExe, "-c", "-v", "-cp", classesDir.toString(), "Calculator");

            /*
             * ============================================================
             * 🔍 CZYTANIE OUTPUTU javap - KLUCZOWE INSTRUKCJE BAJTKODU
             * ============================================================
             * W wypisanym powyzej outpucie dla metody sumOfSquares znajdziesz
             * (dokladne numery offsetow moga sie nieznacznie roznic miedzy
             * wersjami JDK, ale RODZAJE instrukcji beda te same):
             *
             *  - iconst_0 / iconst_1 - wrzuca na stos maly staly int (0, 1...)
             *    bez odwolania do constant poola (optymalizacja dla czestych
             *    malych wartosci).
             *  - istore_1 / istore_2 - zdejmuje wartosc int ze stosu operandow
             *    i zapisuje ja do lokalnej zmiennej o danym indeksie (np.
             *    "total" albo "i" w naszej metodzie).
             *  - iload_1 / iload_2 - odwrotnosc istore: wczytuje wartosc int
             *    z lokalnej zmiennej z powrotem na stos operandow.
             *  - imul - zdejmuje DWIE wartosci int ze stosu, mnozy je, wynik
             *    wklada z powrotem na stos (odpowiada "i * i").
             *  - iadd - analogicznie dla dodawania ("total += ...").
             *  - if_icmpgt / goto - instrukcje sterujace przeplywem (warunek
             *    petli for, skok z powrotem na poczatek petli).
             *  - invokevirtual - wywoluje metode INSTANCJI z polimorfizmem
             *    (np. System.out.println(...) - JVM sprawdza faktyczny typ
             *    obiektu w runtime, zeby wybrac wlasciwa implementacje).
             *  - invokespecial - wywoluje konstruktor (<init>) albo metode
             *    prywatna/super - BEZ polimorfizmu, wiadomo dokladnie ktora
             *    metoda zostanie wywolana juz w czasie kompilacji.
             *  - areturn / ireturn / return - zwroc wartosc typu referencyjnego
             *    / int / void z metody.
             *
             * Kazda z tych instrukcji to POJEDYNCZY bajt kodu operacji (stad
             * "bajtkod") plus ewentualne argumenty (np. indeks w constant
             * poolu) - JVM czyta je jedna po drugiej jak prosty jezyk maszyny
             * stosowej (stack machine), w odroznieniu od fizycznych CPU,
             * ktore zwykle sa maszynami rejestrowymi.
             */
            System.out.println("\n(Zobacz powyzszy output javap -c -v - to REALNY bajtkod skompilowanej metody sumOfSquares).");

        } finally {
            deleteRecursively(workDir);
        }

        System.out.println("\n=== KONIEC LEKCJI 2 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - javac: zrodlo -> tokenizacja -> parsowanie (AST) -> analiza
         *   semantyczna (typy) -> generacja bajtkodu -> plik .class.
         * - Plik .class ma scisla strukture: magic number 0xCAFEBABE,
         *   wersja, CONSTANT POOL (wszystkie stale/nazwy/sygnatury), access
         *   flags, this/super/interfaces, fields, methods (z atrybutem
         *   Code = faktyczny bajtkod), dodatkowe atrybuty.
         * - Bajtkod dziala jak jezyk maszyny STOSOWEJ: instrukcje
         *   wrzucaja/zdejmuja wartosci ze stosu operandow (iconst, istore,
         *   iload, imul, iadd, if_icmpgt, invokevirtual, invokespecial,
         *   areturn...).
         * - javap -c -v to narzedzie do INSPEKCJI skompilowanych klas -
         *   pokazuje bajtkod, constant pool i metadane bez potrzeby
         *   dekompilacji do zrodla.
         * - Nastepna lekcja (3): jak DOKLADNIE ten plik .class trafia do
         *   pamieci JVM - mechanika ladowania klas (loading/linking/
         *   initialization) i hierarchia class loaderow.
         */
    }

    private static void explainCompilationPipeline() {
        System.out.println("[Pipeline javac]");
        System.out.println("zrodlo .java -> tokenizacja -> parsowanie (AST) -> analiza semantyczna -> bajtkod .class\n");
    }

    private static void explainClassFileStructure() {
        System.out.println("[Struktura pliku .class - konceptualnie]");
        System.out.println("magic (0xCAFEBABE) -> wersja -> constant pool -> access flags -> this/super/interfaces");
        System.out.println("-> fields -> methods (atrybut Code = bajtkod) -> dodatkowe atrybuty\n");
    }

    /**
     * Uruchamia PRAWDZIWY proces systemowy (javac/java/javap), z limitem
     * czasu 10 sekund - jesli proces nie zdazy, zostaje zabity, zeby main()
     * zawsze konczyl sie samoistnie.
     */
    private static int runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println(">>> [" + description + "] " + String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output;
        try (InputStream in = process.getInputStream()) {
            output = new String(in.readAllBytes());
        }
        if (!output.isEmpty()) {
            System.out.print(output);
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            System.out.println("(TIMEOUT - proces zabity)");
            return -1;
        }
        return process.exitValue();
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    // Ignorujemy pojedyncze bledy sprzatania katalogu tymczasowego.
                }
            });
        }
    }
}
