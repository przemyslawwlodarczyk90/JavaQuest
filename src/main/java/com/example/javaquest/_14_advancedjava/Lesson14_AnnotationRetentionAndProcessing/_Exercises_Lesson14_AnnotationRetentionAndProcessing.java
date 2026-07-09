package com.example.javaquest._14_advancedjava.Lesson14_AnnotationRetentionAndProcessing;

public class _Exercises_Lesson14_AnnotationRetentionAndProcessing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineRuntimeRetentionAndReadIt {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj adnotację @Marker1 z @Retention(RetentionPolicy.RUNTIME)
         * i @Target(ElementType.METHOD). Oznacz nią metodę i przez
         * refleksję (method.getAnnotation(Marker1.class)) sprawdź, że
         * zwraca NIE-null obiekt. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefineSourceRetentionAndConfirmNull {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj adnotację @Marker2 z @Retention(RetentionPolicy.SOURCE)
         * i @Target(ElementType.METHOD). Oznacz nią metodę i przez
         * refleksję sprawdź, że getAnnotation(Marker2.class) zwraca null.
         * W komentarzu wyjaśnij dlaczego (jednym zdaniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DefaultRetentionIsClass {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj adnotację @Marker3 BEZ ŻADNEGO @Retention (tylko
         * @Target(ElementType.METHOD)). Oznacz nią metodę, sprawdź przez
         * refleksję, że getAnnotation(Marker3.class) zwraca null. W
         * komentarzu zapisz, JAKA jest domyślna wartość RetentionPolicy,
         * gdy @Retention zostanie pominięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SideBySideComparisonTable {
        /*
         * 🧪 Zadanie 4:
         * Użyj TRZECH adnotacji z Zadań 1-3 razem na JEDNEJ metodzie.
         * Wypisz w pętli (albo ręcznie) wynik getAnnotation() dla
         * wszystkich trzech obok siebie, jako czytelną "tabelkę" w
         * konsoli (nazwa adnotacji -> wynik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RuntimeRetentionOnField {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj adnotację @Sensitive (marker,
         * @Retention(RetentionPolicy.RUNTIME), @Target(ElementType.FIELD)).
         * Oznacz nią pole String password w klasie LoginRequest. Przez
         * refleksję (field.isAnnotationPresent(Sensitive.class)) sprawdź
         * i wypisz, czy pole jest wrażliwe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IsSystemJavaCompilerAvailable {
        /*
         * 🧪 Zadanie 6:
         * Pobierz javax.tools.JavaCompiler przez
         * ToolProvider.getSystemJavaCompiler() i sprawdź, czy zwrócony
         * obiekt jest różny od null - wypisz komunikat "Kompilator
         * dostępny" albo "Brak kompilatora - to nie jest JDK".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompileSimpleSourceInMemory {
        /*
         * 🧪 Zadanie 7:
         * Analogicznie do Lesson02_JavacJavaJarClasspath (rozdział
         * _11_buildtools) i tej lekcji: zapisz do pliku tymczasowego
         * .java prostą klasę "Hello" z metodą main wypisującą jeden
         * tekst, skompiluj ją przez compiler.run(...) do katalogu
         * tymczasowego i wypisz kod wyjścia kompilacji (0 = sukces).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SupportedAnnotationTypesWildcard {
        /*
         * 🧪 Zadanie 8:
         * Napisz minimalny AbstractProcessor oznaczony
         * @SupportedAnnotationTypes("*") (obsługuje WSZYSTKIE adnotacje)
         * i @SupportedSourceVersion(SourceVersion.RELEASE_21), którego
         * process(...) po prostu wypisuje liczbę napotkanych typów
         * adnotacji (annotations.size()) w KAŻDEJ rundzie. Skompiluj
         * dowolny prosty plik źródłowy z tym procesorem (bez adnotacji w
         * źródle) i zaobserwuj, ile razy process() się wywołał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MessagerNoteVsWarning {
        /*
         * 🧪 Zadanie 9:
         * Rozbuduj procesor z Zadania 8 (albo z lekcji) tak, żeby dla
         * KAŻDEGO znalezionego elementu wypisywał JEDNĄ notatkę
         * (Diagnostic.Kind.NOTE) i JEDNO ostrzeżenie (Diagnostic.Kind.WARNING)
         * z różną treścią. Skompiluj plik z jedną oznaczoną metodą,
         * zbierz diagnostyki przez DiagnosticCollector i wypisz OBA
         * komunikaty z ich Kind.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CleanUpTempDirectory {
        /*
         * 🧪 Zadanie 10:
         * Wykonaj dowolną kompilację w locie (np. jak w Zadaniu 7) do
         * katalogu tymczasowego, a NASTĘPNIE napisz i wywołaj metodę
         * rekurencyjnie USUWAJĄCĄ ten katalog (Files.walk + sortowanie
         * odwrotne + deleteIfExists, jak w lekcji). Sprawdź przez
         * Files.exists(...) PRZED i PO sprzątaniu, wypisując oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeRetentionsCustomScenario {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj WŁASNY scenariusz biznesowy (inny niż w lekcji) z
         * trzema adnotacjami o różnej retencji, np. @DesignNote (SOURCE),
         * @InternalMarker (CLASS), @Traceable (RUNTIME) - wszystkie
         * @Target(ElementType.TYPE). Oznacz nimi jedną klasę i wypisz,
         * która z nich jest odczytywalna w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ProcessorCountsAnnotatedMethods {
        /*
         * 🧪 Zadanie 12:
         * Napisz procesor obsługujący jedną, konkretną adnotację (np.
         * "Audited", zdefiniowaną WEWNĄTRZ generowanego źródła jak w
         * lekcji), który zlicza WSZYSTKIE oznaczone nią metody w
         * kompilowanym pliku i na końcu wypisuje jedną notatkę
         * podsumowującą "Znaleziono N metod oznaczonych @Audited".
         * Przetestuj na źródle z co najmniej 3 oznaczonymi metodami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ProcessorDistinguishesElementKind {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj adnotację @Tracked z @Target({ElementType.METHOD,
         * ElementType.FIELD}) (dozwolona na obu). W generowanym źródle
         * oznacz nią JEDNĄ metodę i JEDNO pole. Napisz procesor, który
         * dla każdego znalezionego elementu sprawdza
         * element.getKind() (ElementKind.METHOD vs ElementKind.FIELD) i
         * wypisuje odpowiednio różne komunikaty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompilationFailsWithoutRequiredAnnotation {
        /*
         * 🧪 Zadanie 14:
         * Napisz procesor, który dla adnotacji @RequiresJustification
         * (element String value()) sprawdza, czy value() NIE jest puste -
         * jeśli jest puste, zgłasza Diagnostic.Kind.ERROR (nie NOTE!)
         * przez Messager.printMessage(Kind.ERROR, ..., element) - to
         * SPRAWIA, że task.call() zwróci false (kompilacja się nie uda).
         * Przetestuj OBA przypadki: poprawną wartość i pustą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RetentionAffectsFrameworkChoice {
        /*
         * 🧪 Zadanie 15:
         * W komentarzu wypisz TRZY przykłady adnotacji ZNANYCH z
         * wcześniejszych rozdziałów kursu (np. @Entity z _12_hibernate,
         * @Mapper z _13_libraries, @Test z JUnit) i dla każdej zapisz,
         * jaką retencję (SOURCE/CLASS/RUNTIME) MUSI mieć, żeby dany
         * framework działał tak, jak działa - uzasadnij krótko.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ProcessorReadsAnnotationElementValue {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj (w generowanym źródle) adnotację @Since z elementem
         * String value(). Napisz procesor, który dla każdego oznaczonego
         * elementu pobiera WARTOŚĆ elementu przez
         * AnnotationMirror/AnnotationValue (albo prościej:
         * element.getAnnotationMirrors() i przeszukanie par klucz-wartość)
         * i wypisuje ją w komunikacie Messager. Przetestuj dla dwóch
         * różnych wartości "value".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RoundEnvironmentMultipleAnnotationTypes {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj DWIE różne adnotacje w generowanym źródle (@Fast i
         * @Slow, oba markery, @Target(METHOD)). Napisz JEDEN procesor
         * obsługujący OBIE (@SupportedAnnotationTypes({"Fast", "Slow"})),
         * który w process() rozróżnia, KTÓRY typ adnotacji aktualnie
         * przetwarza (annotationType.getSimpleName()) i wypisuje osobne
         * komunikaty dla każdej grupy elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DiagnosticCollectorFiltering {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj kompilację generującą JEDNOCZEŚNIE zwykłe ostrzeżenia
         * kompilatora (np. użycie przestarzałej metody - patrz Lekcja 12)
         * ORAZ notatki Twojego procesora. Zbierz wszystko w JEDEN
         * DiagnosticCollector, a potem PRZEFILTRUJ i wypisz OSOBNO tylko
         * diagnostyki typu Diagnostic.Kind.NOTE i osobno typu WARNING.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainWhyBothSourceFilesNeeded {
        /*
         * 🧪 Zadanie 19:
         * W komentarzu wyjaśnij, dlaczego w tej lekcji adnotacja
         * przetwarzana przez procesor (@Loggable) MUSIAŁA być
         * zdefiniowana WEWNĄTRZ tego samego generowanego pliku źródłowego
         * co klasa, którą oznaczaliśmy - a nie np. jako osobna, wcześniej
         * skompilowana klasa Javy z pliku .class na classpath. Podpowiedź:
         * pomyśl o tym, co dokładnie kompiluje CompilationTask.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ProcessorReturnsFalseVsTrue {
        /*
         * 🧪 Zadanie 20:
         * Napisz DWA warianty tego samego prostego procesora - jeden,
         * którego process() zwraca true ("obsłużyłem te adnotacje"),
         * drugi, którego process() zwraca false. Skompiluj TEN SAM plik
         * źródłowy osobno z każdym wariantem procesora. W komentarzu
         * (na podstawie dokumentacji Processor.process) opisz różnicę w
         * znaczeniu wartości zwracanej - czy inne procesory nadal mogą
         * przetworzyć te same adnotacje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenerateNewSourceFileWithFiler {
        /*
         * 🧪 Zadanie 21:
         * Rozbuduj procesor tak, żeby dla KAŻDEJ klasy oznaczonej
         * adnotacją @GenerateToString (marker, @Target(TYPE)) WYGENEROWAŁ
         * NOWY plik źródłowy (przez processingEnv.getFiler().createSourceFile(...))
         * z prostą klasą narzędziową, np. "XxxDescriber" z jedną metodą
         * statyczną zwracającą String. Skompiluj i sprawdź (przez
         * Files.exists lub przez wypisanie zawartości katalogu wyjściowego),
         * że nowy plik .class faktycznie powstał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiRoundProcessingDemo {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj procesor, który w PIERWSZEJ rundzie generuje nowy plik
         * źródłowy zawierający adnotację, którą procesor RÓWNIEŻ obsługuje
         * (samo-referencyjny łańcuch). Zaobserwuj (przez liczenie wywołań
         * process() w polu statycznym/liczniku procesora), że
         * annotation processing jest WIELORUNDOWE - javac wywoła Twój
         * procesor PONOWNIE dla nowo wygenerowanego pliku. Wypisz liczbę
         * rund.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimulateLombokStyleGetterGenerationDiscussion {
        /*
         * 🧪 Zadanie 23:
         * W komentarzu (bez implementacji - to WYKRACZA poza oficjalne
         * API) wyjaśnij, dlaczego Lombok (poznany w _13_libraries,
         * Lesson04/Lesson05) NIE MOŻE działać wyłącznie przez standardowe
         * Filer.createSourceFile(...) tak jak MapStruct - Lombok
         * MODYFIKUJE ISTNIEJĄCĄ klasę (dopisuje gettery/settery DO NIEJ),
         * a nie generuje NOWY plik obok. Opisz, jakiego (niepublicznego,
         * zależnego od implementacji kompilatora) mechanizmu Lombok musi
         * użyć zamiast tego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ProcessorValidatesMethodSignature {
        /*
         * 🧪 Zadanie 24:
         * Napisz procesor dla adnotacji @EventHandler (@Target(METHOD)),
         * który sprawdza przez ExecutableElement (element rzutowany z
         * Element), czy oznaczona metoda ma DOKŁADNIE JEDEN parametr -
         * jeśli nie, zgłasza Diagnostic.Kind.ERROR z jasnym komunikatem.
         * Przetestuj na metodzie z jednym parametrem (sukces) i z zerem
         * parametrów (błąd kompilacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RetentionRoundTripThroughJar {
        /*
         * 🧪 Zadanie 25:
         * Skompiluj klasę z metodą oznaczoną adnotacją RUNTIME (jak w
         * Zadaniu 1) do katalogu .class, ZAPAKUJ ją do pliku .jar (jak w
         * Lesson02_JavacJavaJarClasspath z _11_buildtools), wczytaj klasę
         * z powrotem przez URLClassLoader wskazujący na ten .jar i
         * potwierdź przez refleksję, że adnotacja WCIĄŻ jest odczytywalna
         * po przejściu przez cały cykl kompilacja->JAR->wczytanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareCompileTimeVsRuntimeCost {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj DWA rozwiązania tego samego problemu (np.
         * "zbuduj mapper Employee -> EmployeeDto"): jedno oparte na
         * odczycie adnotacji RUNTIME przez refleksję w KAŻDYM wywołaniu
         * (jak prosty ręczny "mini-ORM"), drugie - opisz W KOMENTARZU
         * (bez pełnej implementacji) jak wyglądałoby analogiczne
         * rozwiązanie oparte na SOURCE + annotation processing (jak
         * MapStruct z _13_libraries). Zaimplementuj WARIANT Z REFLEKSJĄ i
         * zmierz w przybliżeniu czas wykonania 100 000 wywołań
         * (System.nanoTime()) - skomentuj, dlaczego wariant kompilacyjny
         * byłby szybszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ProcessorHandlesMultipleFilesInOneCompilation {
        /*
         * 🧪 Zadanie 27:
         * Skompiluj DWA osobne pliki źródłowe NARAZ (jedna kompilacja,
         * dwie JavaFileObject w compilationUnits) - każdy z klasą
         * oznaczającą inne elementy tą samą adnotacją @Reportable
         * (marker). Sprawdź, że TWÓJ procesor zgłasza komunikaty dla
         * elementów z OBU plików w tej samej rundzie/kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SupportedOptionsAndProcessorEnvironment {
        /*
         * 🧪 Zadanie 28:
         * Napisz procesor oznaczony @SupportedOptions("debugMode"),
         * odczytujący processingEnv.getOptions().get("debugMode") w
         * process() i wypisujący dodatkowy komunikat TYLKO jeśli opcja
         * jest ustawiona. Przekaż tę opcję do kompilacji przez
         * "-AdebugMode=true" w liście "options" przekazywanej do
         * compiler.getTask(...). Przetestuj z opcją i bez niej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildMiniValidationProcessorRejectingBadRange {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj procesor dla adnotacji @Range(min, max) (z Lekcji
         * 13, zdefiniowanej w generowanym źródle), który w process()
         * sprawdza WARTOŚCI min/max wyciągnięte z AnnotationMirror - jeśli
         * min > max, zgłasza Diagnostic.Kind.ERROR (błąd logiczny wykryty
         * W CZASIE KOMPILACJI, zanim program w ogóle ruszy!). Przetestuj
         * na poprawnym zakresie (0, 100) i niepoprawnym (100, 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMiniAnnotationFramework {
        /*
         * 🧪 Zadanie 30:
         * Połącz WSZYSTKO z klastra Lekcji 12-14 w jeden mini-framework:
         * (a) własna adnotacja @RestEndpoint(path, method) z RUNTIME
         * retention używana na metodach klasy kontrolera (odczytywana w
         * main() przez zwykłą refleksję, budująca "tabelę routingu" jak w
         * ćwiczeniach Lekcji 13), (b) DRUGA, ODDZIELNA adnotacja
         * @ValidatedController (SOURCE retention) sprawdzana przez WŁASNY
         * annotation processor (jak w tej lekcji) - procesor ma
         * zweryfikować W CZASIE KOMPILACJI, że KAŻDA metoda oznaczona
         * @RestEndpoint w klasie z @ValidatedController zwraca String
         * (inaczej Diagnostic.Kind.ERROR). Zademonstruj DZIAŁANIE OBU
         * części osobno w main() (refleksja RUNTIME + kompilacja w locie
         * z procesorem) i podsumuj w komentarzu, jak to pokazuje pełny
         * cykl życia adnotacji: definicja -> retencja -> (ew.) przetwarzanie
         * w kompilacji -> (ew.) odczyt w runtime.
         */
        public static void main(String[] args) { }
    }
}
