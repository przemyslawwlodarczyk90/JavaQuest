package com.example.javaquest._14_advancedjava.Lesson14_AnnotationRetentionAndProcessing;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class _Lesson14_AnnotationRetentionAndProcessing {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: @Retention I PRZETWARZANIE ADNOTACJI ===\n");

        /*
         * ============================================================
         * 📦 TRZY POZIOMY RetentionPolicy - DOKŁADNIE
         * ============================================================
         * W Lekcji 13 poznaliśmy @Retention tylko "z lotu ptaka". Teraz
         * czas na PEŁNY obraz - RetentionPolicy to enum z TRZEMA
         * wartościami, opisującymi, jak DŁUGO adnotacja "przeżywa" kolejne
         * etapy budowania i uruchamiania programu:
         *
         *   źródło .java --[javac]--> bajtkod .class --[JVM: classloading]--> obiekty w pamięci
         *
         * - RetentionPolicy.SOURCE
         *     Adnotacja jest widoczna TYLKO na etapie kompilacji (dla
         *     samego kompilatora i narzędzi typu annotation processor -
         *     patrz druga część tej lekcji). Kompilator ODRZUCA ją przy
         *     generowaniu pliku .class - w bajtkodzie nie ma po niej
         *     ŚLADU. Przykłady wbudowane: @Override, @SuppressWarnings.
         *     Sensowna, gdy adnotacja służy WYŁĄCZNIE do sprawdzeń/
         *     generowania kodu W CZASIE KOMPILACJI i nikt nigdy nie
         *     będzie jej odpytywał w runtime.
         *
         * - RetentionPolicy.CLASS (DOMYŚLNA - jeśli @Retention pominięto!)
         *     Adnotacja TRAFIA do pliku .class (jest zapisana w bajtkodzie,
         *     można ją zobaczyć np. przez "javap -v"), ALE JVM jej NIE
         *     ładuje do pamięci przy wczytywaniu klasy - z punktu widzenia
         *     kodu uruchomionego w runtime (w tym refleksji), jest
         *     NIEWIDOCZNA, tak jakby jej nie było. Rzadko używana wprost -
         *     głównie przez narzędzia operujące BEZPOŚREDNIO na plikach
         *     .class (np. bytecode weaving), a nie przez zwykłą refleksję.
         *
         * - RetentionPolicy.RUNTIME
         *     Adnotacja trafia do .class ORAZ jest ładowana przez JVM do
         *     pamięci - dostępna w runtime przez REFLEKSJĘ
         *     (Class/Method/Field.getAnnotation(...)). To NA TEJ wartości
         *     opierają się WSZYSTKIE frameworki, które "odkrywają" Twój
         *     kod w czasie działania programu: JUnit (@Test), Hibernate
         *     (@Entity, @Column - rozdział _12_hibernate), Spring
         *     (@Autowired, @Service). MapStruct (rozdział _13_libraries)
         *     jest tu wyjątkiem świadomym - jego @Mapper ma retention
         *     SOURCE/CLASS, bo MapStruct działa WYŁĄCZNIE w czasie
         *     kompilacji (generuje gotową klasę Javy), nie potrzebuje
         *     niczego w runtime.
         *
         * Poniżej zdefiniujemy TRZY adnotacje - po jednej dla każdego
         * poziomu - nałożymy wszystkie trzy na tę samą metodę, i
         * SPRAWDZIMY w runtime, która z nich faktycznie "odpowiada" na
         * odpytanie przez refleksję.
         */

        Method demoMethod = DemoTarget.class.getDeclaredMethod("annotatedMethod");

        SourceOnlyMarker sourceOnly = demoMethod.getAnnotation(SourceOnlyMarker.class);
        ClassOnlyMarker classOnly = demoMethod.getAnnotation(ClassOnlyMarker.class);
        RuntimeVisibleMarker runtimeVisible = demoMethod.getAnnotation(RuntimeVisibleMarker.class);

        System.out.println("--- getAnnotation(...) na metodzie oznaczonej WSZYSTKIMI trzema adnotacjami ---");
        System.out.println("@SourceOnlyMarker  (RetentionPolicy.SOURCE)  -> " + sourceOnly
                + "   (kompilator ODRZUCIŁ ją przed zapisem .class - w runtime jej po prostu NIE MA)");
        System.out.println("@ClassOnlyMarker   (RetentionPolicy.CLASS)   -> " + classOnly
                + "   (JEST w pliku .class, ale JVM jej nie ładuje do pamięci - refleksja jej nie widzi)");
        System.out.println("@RuntimeVisibleMarker (RetentionPolicy.RUNTIME) -> " + runtimeVisible
                + "   (JEDYNA dostępna w runtime - stąd konkretny obiekt, nie null)");

        /*
         * Zwróć uwagę: WSZYSTKIE TRZY adnotacje są nałożone na tę samą
         * metodę annotatedMethod() w klasie DemoTarget (zobacz na końcu
         * pliku) - w KODZIE ŹRÓDŁOWYM wyglądają identycznie. Różnica
         * ujawnia się DOPIERO w runtime, przy próbie odpytania przez
         * getAnnotation() - i wynika WYŁĄCZNIE z wartości @Retention na
         * definicji każdej z nich. To dokładnie ten mechanizm decyduje,
         * czy dana adnotacja może zasilić framework działający w czasie
         * wykonania programu, czy tylko narzędzia analizujące/generujące
         * kod przy kompilacji.
         */

        /*
         * ============================================================
         * 🔹 CO WYBRAĆ? PRAKTYCZNA ZASADA
         * ============================================================
         * - Piszesz adnotację dla kompilatora/IDE (sprawdzenie reguł,
         *   generowanie kodu w czasie kompilacji - jak MapStruct)?
         *   -> RetentionPolicy.SOURCE (albo nawet CLASS, jeśli narzędzie
         *   operuje na bajtkodzie, nie na drzewie składniowym źródeł).
         * - Piszesz adnotację, którą Twój program/framework ma ODCZYTAĆ
         *   W TRAKCIE DZIAŁANIA (konfiguracja, walidacja, DI, ORM, testy)?
         *   -> RetentionPolicy.RUNTIME - jedyna opcja, żeby getAnnotation()
         *   cokolwiek zwrócił.
         * - RetentionPolicy.CLASS w praktyce używa się RZADKO wprost -
         *   to głównie "punkt pośredni" dla zaawansowanych narzędzi
         *   operujących bezpośrednio na plikach .class.
         */
        System.out.println("\n--- Reguła: SOURCE/CLASS = tylko narzędzia budujące, RUNTIME = odczyt w działającym programie ---");

        /*
         * ============================================================
         * 🔍 PRAWDZIWE PRZETWARZANIE ADNOTACJI W CZASIE KOMPILACJI
         * ============================================================
         * Do tej pory adnotacje SOURCE i CLASS omówiliśmy tylko
         * teoretycznie - "kompilator je widzi, ale w runtime znikają".
         * Ale CO dokładnie robi z nimi kompilator? Odpowiedź: annotation
         * processing (przetwarzanie adnotacji) - mechanizm javax.annotation.processing,
         * na którym opierają się m.in. Lombok, MapStruct (_13_libraries)
         * czy Dagger. Podczas KAŻDEJ kompilacji javac uruchamia
         * zarejestrowane "procesory adnotacji" (AbstractProcessor),
         * które mogą:
         *  - PRZESKANOWAĆ drzewo składniowe w poszukiwaniu adnotacji,
         *  - wygenerować NOWE pliki źródłowe (tak działa MapStruct -
         *    generuje XxxMapperImpl.java, który potem sam trafia z
         *    powrotem do kompilacji),
         *  - zgłosić błąd/ostrzeżenie/notatkę kompilacji przez Messager,
         *  - a NIE mogą modyfikować ISTNIEJĄCYCH już skompilowanych
         *    drzew składniowych (annotation processing jest wieloprzebiegowe
         *    - "runda po rundzie", aż żaden procesor nie wygeneruje nic
         *    nowego).
         *
         * Napiszemy WŁASNY, prosty AbstractProcessor i uruchomimy go
         * DOKŁADNIE tak, jak wcześniej embedowaliśmy kompilator w
         * _11_buildtools/Lesson02_JavacJavaJarClasspath - przez
         * javax.tools.JavaCompiler, tylko tym razem z dodatkowym krokiem
         * task.setProcessors(...).
         */

        System.out.println("\n--- Prawdziwe annotation processing przez javax.tools.JavaCompiler ---");

        // KROK 1: przygotuj katalog roboczy - dokładnie jak w Lesson02.
        Path workDir = Files.createTempDirectory("lesson14-annotation-processing");
        Path srcDir = workDir.resolve("src");
        Path outDir = workDir.resolve("out");
        Files.createDirectories(srcDir);
        Files.createDirectories(outDir);

        // KROK 2: generujemy plik źródłowy Z WŁASNĄ adnotacją @Loggable
        // (retention SOURCE - ma sens TYLKO dla narzędzi kompilacyjnych,
        // patrz wyżej) oraz DWIE metody, z których TYLKO jedna jest nią
        // oznaczona. Procesor (napisany niżej w tym pliku) ma za zadanie
        // znaleźć WSZYSTKIE elementy oznaczone @Loggable i zgłosić o nich
        // notatkę kompilacji (Messager.printMessage).
        Path sourceFile = srcDir.resolve("PaymentService.java");
        Files.writeString(sourceFile, """
                import java.lang.annotation.ElementType;
                import java.lang.annotation.Retention;
                import java.lang.annotation.RetentionPolicy;
                import java.lang.annotation.Target;

                @Retention(RetentionPolicy.SOURCE)
                @Target(ElementType.METHOD)
                @interface Loggable {
                    String value() default "";
                }

                public class PaymentService {

                    @Loggable("płatność kartą")
                    public void payByCard(double amount) {
                        System.out.println("Płatność kartą: " + amount);
                    }

                    @Loggable("płatność przelewem")
                    public void payByTransfer(double amount) {
                        System.out.println("Płatność przelewem: " + amount);
                    }

                    public void internalHelper() {
                        // BEZ @Loggable - procesor NIE powinien jej zgłosić.
                    }
                }
                """);

        // KROK 3: standardowy JavaCompiler, tak jak w Lesson02.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Brak systemowego kompilatora Javy - upewnij się, że używasz JDK, nie tylko JRE.");
        }

        // KROK 4: DiagnosticCollector zbiera WSZYSTKIE komunikaty
        // zgłoszone podczas kompilacji - w tym te wysłane przez NASZ
        // procesor przez Messager.printMessage(Kind.NOTE, ...). Dzięki
        // temu możemy je odczytać programowo zamiast parsować System.err.
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(List.of(sourceFile.toFile()));
        List<String> options = List.of("-d", outDir.toString());

        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, options, null, compilationUnits);

        // KROK 5: KLUCZOWY krok tej lekcji - task.setProcessors(...)
        // rejestruje NASZ procesor PROGRAMOWO, bez potrzeby pliku
        // META-INF/services/javax.annotation.processing.Processor
        // (który jest wymagany tylko, gdy procesor ma zostać odkryty
        // AUTOMATYCZNIE, np. jako zależność Maven na classpath innego
        // projektu - patrz META-INF/services w kontekście ServiceLoader,
        // Lekcja 26_ServiceLoaderAndSpi). My uruchamiamy go W TYM SAMYM
        // procesie JVM, więc wystarczy podać gotową instancję.
        LoggableAnnotationProcessor processor = new LoggableAnnotationProcessor();
        task.setProcessors(List.of(processor));

        // KROK 6: task.call() faktycznie URUCHAMIA kompilację - w jej
        // trakcie javac wywoła nasz procesor dla KAŻDEGO napotkanego
        // typu adnotacji z listy @SupportedAnnotationTypes.
        boolean success = task.call();
        System.out.println("Wynik kompilacji PaymentService.java z aktywnym procesorem (true = sukces): " + success);

        // KROK 7: wypisujemy WSZYSTKIE zebrane diagnostyki - wśród nich
        // powinny być DWIE notatki NASZEGO procesora (payByCard,
        // payByTransfer), a NIE powinno być notatki dla internalHelper().
        System.out.println("\nKomunikaty zebrane podczas kompilacji (w tym notatki procesora):");
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            System.out.println("  [" + diagnostic.getKind() + "] " + diagnostic.getMessage(null));
        }

        // Sprzątanie plików tymczasowych - lekcja ma zostawić dysk czysty.
        deleteRecursively(workDir);

        /*
         * ============================================================
         * 🔍 DLACZEGO TO WAŻNE?
         * ============================================================
         * Powyższy procesor tylko WYPISUJE komunikaty - ale DOKŁADNIE
         * ten sam mechanizm (AbstractProcessor + RoundEnvironment +
         * Messager) stoi za:
         *  - Lombokiem: procesor widzi @Getter/@Setter/@Data (rozdział
         *    _13_libraries) i (przez sztuczki poza oficjalnym API,
         *    modyfikując wewnętrzne drzewo javac) DOPISUJE metody do
         *    ISTNIEJĄCEJ klasy,
         *  - MapStructem: procesor widzi @Mapper i GENERUJE NOWY plik
         *    XxxMapperImpl.java (przez Filer, pokrewny do Messager),
         *  - Dagger/Hilt (DI w Androidzie): procesor widzi @Inject i
         *    generuje całe drzewo klas "fabryk".
         * Nasz procesor w tej lekcji jest CELOWO minimalny (tylko
         * Messager, bez generowania nowych plików przez Filer) - chodzi
         * o pokazanie SAMEGO MECHANIZMU haczenia się w proces kompilacji,
         * nie o odtworzenie całego Lomboka.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - I KONIEC KLASTRA "ADNOTACJE" (LEKCJE 12-14)
         * ============================================================
         * - RetentionPolicy.SOURCE - znika po kompilacji, widoczna
         *   TYLKO dla kompilatora/annotation processorów.
         * - RetentionPolicy.CLASS (domyślna, gdy @Retention pominięto) -
         *   zapisana w .class, ale NIEŁADOWANA przez JVM - niewidoczna
         *   dla zwykłej refleksji w runtime.
         * - RetentionPolicy.RUNTIME - JEDYNA widoczna dla
         *   getAnnotation()/refleksji w działającym programie - na niej
         *   opierają się JUnit, Hibernate, Spring i inne frameworki
         *   odczytujące adnotacje "w locie".
         * - Prawdziwe annotation processing (javax.annotation.processing)
         *   działa W CZASIE KOMPILACJI - AbstractProcessor rejestrowany
         *   przez CompilationTask.setProcessors(...) (programowo, jak
         *   tutaj) albo przez META-INF/services (automatyczne odkrycie
         *   na classpath) - dostaje dostęp do drzewa składniowego przez
         *   RoundEnvironment, zgłasza komunikaty przez Messager, może
         *   generować nowe pliki przez Filer. To dokładnie ten mechanizm
         *   napędza Lomboka, MapStructa i wiele innych narzędzi poznanych
         *   we wcześniejszych rozdziałach tego kursu.
         * - Tym samym zamykamy KLASTER "Adnotacje" (Lekcje 12-14):
         *   Lekcja 12 pokazała wbudowane adnotacje (@Override,
         *   @Deprecated, @SuppressWarnings, ...), Lekcja 13 nauczyła
         *   definiować WŁASNE (@interface + 4 meta-adnotacje +
         *   @Repeatable), a ta lekcja domknęła temat retencją i
         *   realnym przetwarzaniem w czasie kompilacji.
         * - DALEJ: Lekcja 15_ReflectionBasics uczy mechanizmu refleksji
         *   OD PODSTAW (Class, Method, Field, Constructor) - dokładnie
         *   tego API, którego użyliśmy tu "przy okazji"
         *   (getDeclaredMethod, getAnnotation) do odczytu adnotacji
         *   RUNTIME. Lekcja 16 pokaże praktyczne zastosowania i ryzyka
         *   refleksji.
         */

        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    /** Rekurencyjnie usuwa katalog tymczasowy razem z zawartością (sprzątanie po demie kompilacji). */
    private static void deleteRecursively(Path root) throws java.io.IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (var paths = Files.walk(root)) {
            paths.sorted((a, b) -> b.compareTo(a)) // najpierw pliki/podkatalogi, katalog nadrzędny na końcu
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (java.io.IOException e) {
                            // Best effort sprzątanie tymczasowego katalogu - ignorujemy pojedyncze błędy.
                        }
                    });
        }
    }

    /** Metoda oznaczona wszystkimi trzema adnotacjami demo - patrz main() dla porównania retencji. */
    static class DemoTarget {

        @SourceOnlyMarker
        @ClassOnlyMarker
        @RuntimeVisibleMarker
        public void annotatedMethod() {
            // ciało nieistotne - liczy się tylko to, co JVM "widzi" w metadanych metody
        }
    }

    /** Procesor adnotacji: wyszukuje elementy oznaczone @Loggable (z KOMPILOWANEGO w locie źródła) i zgłasza notatkę. */
    @SupportedAnnotationTypes("Loggable") // adnotacja bez pakietu w generowanym źródle - prosta nazwa wystarczy
    static class LoggableAnnotationProcessor extends AbstractProcessor {

        @Override
        public SourceVersion getSupportedSourceVersion() {
            // latestSupported() zamiast sztywnej stałej (np. RELEASE_21) - procesor
            // sam dopasowuje się do wersji JDK, na którym akurat działa kompilacja,
            // więc kurs (napisany pod Javę 21) nie "ostrzega" niepotrzebnie o
            // niedopasowaniu wersji, nawet gdy ktoś uruchomi go na nowszym JDK.
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            Messager messager = processingEnv.getMessager();
            for (TypeElement annotationType : annotations) {
                // roundEnv.getElementsAnnotatedWith(...) zwraca WSZYSTKIE
                // elementy (tu: metody) oznaczone konkretnym typem
                // adnotacji w bieżącej rundzie przetwarzania.
                for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(annotationType)) {
                    messager.printMessage(Diagnostic.Kind.NOTE,
                            "@Loggable znaleziony na elemencie: " + annotatedElement.getSimpleName());
                }
            }
            return true; // "obsłużyliśmy" te adnotacje - żaden inny procesor nie musi ich już przetwarzać
        }
    }

    // ================================================================
    // TRZY ADNOTACJE DEMO - identyczna struktura, RÓŻNA @Retention
    // ================================================================

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface SourceOnlyMarker {
    }

    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.METHOD)
    @interface ClassOnlyMarker {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface RuntimeVisibleMarker {
    }
}
