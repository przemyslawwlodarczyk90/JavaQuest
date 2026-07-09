package com.example.javaquest._14_advancedjava.Lesson13_CustomAnnotations;

public class _Exercises_Lesson13_CustomAnnotations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MarkerAnnotationDefinition {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj adnotację-znacznik (bez elementów) @Experimental z
         * @Retention(RUNTIME) i @Target(ElementType.METHOD). Oznacz nią
         * dowolną metodę i sprawdź przez refleksję
         * (method.isAnnotationPresent(Experimental.class)), czy jest
         * obecna - wypisz wynik (true/false) dla oznaczonej i nieoznaczonej
         * metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SingleValueAnnotationDefinition {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj adnotację @Author z JEDNYM elementem String value()
         * (bez wartości domyślnej), @Retention(RUNTIME), @Target(TYPE).
         * Oznacz nią klasę w składni skróconej @Author("Twoje Imię") i
         * odczytaj wartość przez refleksję (getAnnotation(Author.class).value()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FullAnnotationWithDefaults {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj adnotację @Version z elementami int major(), int
         * minor() default 0, String codename() default "" -
         * @Retention(RUNTIME), @Target(TYPE). Oznacz jedną klasę PODAJĄC
         * tylko major, drugą PODAJĄC wszystkie trzy elementy. Odczytaj i
         * wypisz wartości obu (w tym domyślne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TargetFieldOnly {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj adnotację @NotBlank (marker, @Retention(RUNTIME),
         * @Target(ElementType.FIELD) - TYLKO pola). Oznacz nią pole
         * String name w prostej klasie. W komentarzu opisz, jaki błąd
         * kompilacji by wystąpił, gdybyś spróbował użyć @NotBlank na
         * całej klasie zamiast na polu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TargetMultipleElementTypes {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj adnotację @Loggable z @Target({ElementType.TYPE,
         * ElementType.METHOD}) (dozwolona na klasie ORAZ na metodzie) i
         * @Retention(RUNTIME). Oznacz nią JEDNĄ klasę i JEDNĄ jej metodę
         * jednocześnie. Odczytaj obie adnotacje przez refleksję i
         * potwierdź ich obecność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RetentionSourcePreview {
        /*
         * 🧪 Zadanie 6:
         * Zdefiniuj adnotację @CompileTimeOnly z
         * @Retention(RetentionPolicy.SOURCE) i @Target(ElementType.METHOD).
         * Oznacz nią metodę i w RUNTIME spróbuj odczytać ją przez
         * method.getAnnotation(CompileTimeOnly.class) - wypisz wynik
         * (spodziewane null) i wyjaśnij w komentarzu dlaczego (pełny temat
         * w Lekcji 14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DocumentedMarker {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj adnotację @PublicApi (marker, @Documented,
         * @Retention(RUNTIME), @Target(ElementType.TYPE)). Oznacz nią
         * klasę. W komentarzu wyjaśnij, jaki byłby PRAKTYCZNY skutek
         * usunięcia @Documented (dla generowanego Javadoc), mimo że
         * refleksja i kompilacja działałyby identycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ValueElementShorthand {
        /*
         * 🧪 Zadanie 8:
         * Zdefiniuj adnotację @Priority z DWOMA elementami: int value()
         * (bez domyślnej - element "value") oraz String note() default "".
         * Użyj jej DWA razy: raz w skróconej formie @Priority(5) (tylko
         * value), raz w pełnej @Priority(value = 3, note = "pilne").
         * Odczytaj i wypisz oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InheritedBasicDemo {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj adnotację @Cacheable (marker, @Inherited,
         * @Retention(RUNTIME), @Target(ElementType.TYPE)). Oznacz nią
         * klasę bazową ParentService, utwórz podklasę ChildService BEZ
         * własnej adnotacji. Sprawdź przez
         * ChildService.class.getAnnotation(Cacheable.class), że mimo to
         * jest ona "widoczna" (dzięki @Inherited).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ArrayTypeElement {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj adnotację @Tags z elementem String[] value() -
         * @Retention(RUNTIME), @Target(ElementType.TYPE). Oznacz klasę
         * @Tags({"web", "api", "public"}). Odczytaj tablicę przez
         * refleksję i wypisz każdy tag w pętli.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EnumTypeElement {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj enum Environment { DEV, TEST, PROD }. Zdefiniuj
         * adnotację @ActiveIn z elementem Environment[] value() -
         * @Retention(RUNTIME), @Target(ElementType.METHOD). Oznacz DWIE
         * metody różnymi kombinacjami środowisk (np. {DEV, TEST} i
         * {PROD}) i wypisz, w jakich środowiskach każda jest aktywna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NestedAnnotationTypeElement {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj adnotację @Author (jak w Zadaniu 2, z elementem
         * String name()). Zdefiniuj DRUGĄ adnotację @Reviewed z elementem
         * Author reviewer() (typ elementu to INNA adnotacja!) -
         * @Retention(RUNTIME), @Target(ElementType.TYPE). Oznacz klasę
         * @Reviewed(reviewer = @Author("Jan Kowalski")) i odczytaj
         * zagnieżdżoną wartość przez refleksję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RangeValidationAtRuntime {
        /*
         * 🧪 Zadanie 13:
         * Użyj adnotacji @Range z lekcji (min/max) na polu int age w
         * klasie Person. Napisz metodę statyczną validate(Person p), która
         * przez refleksję odczytuje @Range z pola "age", pobiera WARTOŚĆ
         * pola (field.setAccessible(true); field.getInt(p)) i sprawdza,
         * czy mieści się w zakresie - wypisz wynik dla osoby z wiekiem
         * poprawnym i niepoprawnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RepeatableWithoutExplicitContainerUsage {
        /*
         * 🧪 Zadanie 14:
         * Użyj adnotacji @Schedule/@Schedules z lekcji (albo zdefiniuj
         * własny odpowiednik @Tag/@Tags jako @Repeatable). Oznacz metodę
         * DWOMA powtórzeniami. Odczytaj je NA DWA SPOSOBY: (a) przez
         * method.getAnnotationsByType(Schedule.class) (rozpakowane), (b)
         * przez method.getAnnotation(Schedules.class) (surowy kontener) -
         * porównaj oba wyniki w wypisywanym tekście.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CustomExceptionAnnotationDrivenMessages {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj adnotację @ErrorCode z elementem int value() -
         * @Retention(RUNTIME), @Target(ElementType.TYPE). Oznacz nią DWIE
         * klasy wyjątków (np. @ErrorCode(404) NotFoundException,
         * @ErrorCode(400) BadRequestException extends RuntimeException).
         * Napisz metodę wypisującą "Błąd {kod}: {komunikat}" na podstawie
         * odczytanej adnotacji i wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TargetParameterDemo {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj adnotację @NonNull (marker, @Retention(RUNTIME),
         * @Target(ElementType.PARAMETER)). Napisz metodę
         * greet(@NonNull String name) - wewnątrz NIE waliduj automatycznie
         * (to tylko metadana), ale NAPISZ osobną metodę, która przez
         * refleksję (method.getParameterAnnotations()) sprawdza, KTÓRE
         * parametry mają @NonNull, i wypisuje ich indeksy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DocumentedVsUndocumentedComparison {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj DWIE adnotacje o identycznej strukturze - @Internal
         * (BEZ @Documented) i @Public (Z @Documented), obie
         * @Retention(RUNTIME) @Target(TYPE). Oznacz nimi dwie klasy. W
         * komentarzu wyjaśnij (nie da się tego sprawdzić w runtime - to
         * właściwość generowanego Javadoc), dlaczego refleksja w main()
         * i tak zwróci obie adnotacje identycznie, mimo różnicy w
         * @Documented.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MultiElementValidationAnnotation {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj adnotację @StringLength z elementami int min() default 0,
         * int max() default Integer.MAX_VALUE - @Retention(RUNTIME),
         * @Target(ElementType.FIELD). Oznacz pole String username w klasie
         * User zakresem 3-20. Napisz metodę walidującą przez refleksję
         * wartość pola względem odczytanych min/max i przetestuj dla
         * "ab" (za krótkie), "Kasia123" (poprawne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_InheritedDoesNotApplyToMethods {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj adnotację @Cacheable (jak w Zadaniu 9) TYLKO z
         * @Target(ElementType.METHOD) (BEZ zmiany reguły @Inherited -
         * mimo obecności @Inherited na adnotacji, meta-adnotacja ta i tak
         * działa TYLKO dla klas). Oznacz metodę w klasie bazowej, podklasa
         * ją nadpisuje BEZ adnotacji. Sprawdź przez refleksję, że
         * podklasa NIE dziedziczy @Cacheable na poziomie metody - wypisz
         * wynik i skomentuj różnicę względem Zadania 9.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AnnotationEqualsAndToString {
        /*
         * 🧪 Zadanie 20:
         * Użyj dowolnej własnej adnotacji z elementami (np. @Version z
         * Zadania 3). Odczytaj tę samą adnotację DWA RAZY z dwóch RÓŻNYCH
         * klas oznaczonych IDENTYCZNYMI wartościami elementów. Sprawdź
         * przez equals(), czy dwie instancje adnotacji (Annotation
         * proxy) o tych samych wartościach są sobie równe - wypisz wynik
         * i wyjaśnij w komentarzu, dlaczego adnotacje porównuje się przez
         * WARTOŚCI elementów, a nie tożsamość obiektu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MiniValidationFrameworkFieldScan {
        /*
         * 🧪 Zadanie 21:
         * Połącz @NotBlank (Zadanie 4) i @Range (z lekcji) na różnych
         * polach jednej klasy RegistrationForm (String email z @NotBlank,
         * int age z @Range(min=18, max=120)). Napisz metodę
         * List<String> validate(Object obj), która przez
         * obj.getClass().getDeclaredFields() PRZECHODZI WSZYSTKIE pola,
         * sprawdza obecne adnotacje walidacyjne i zwraca listę komunikatów
         * o błędach. Przetestuj na poprawnym i niepoprawnym obiekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RepeatableWithRichContainer {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj OD ZERA (własne nazwy, nie kopiuj @Schedule z
         * lekcji) parę adnotacji @Permission (@Repeatable(Permissions.class),
         * element String role()) + kontener @Permissions (element
         * Permission[] value()) - obie @Retention(RUNTIME)
         * @Target(ElementType.TYPE). Oznacz klasę AdminPanel TRZEMA
         * powtórzeniami @Permission z różnymi rolami i wypisz je wszystkie
         * przez getAnnotationsByType().
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AnnotationDrivenRoutingTable {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj adnotację @Route z elementami String path() i
         * String method() default "GET" - @Retention(RUNTIME),
         * @Target(ElementType.METHOD). Oznacz TRZY metody w klasie
         * ApiController różnymi ścieżkami/metodami HTTP. Napisz kod,
         * który przez refleksję skanuje WSZYSTKIE metody klasy, buduje
         * mapę Map<String, String> (path -> nazwa metody Javy) i wypisuje
         * ją jako "tabelę routingu".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_InheritedChainThreeLevels {
        /*
         * 🧪 Zadanie 24:
         * Użyj @Inherited adnotacji @AuditableEntity z lekcji (albo
         * własnego odpowiednika) na klasie GrandparentEntity. Zbuduj
         * łańcuch GrandparentEntity -> ParentEntity -> ChildEntity (żadna
         * z dwóch niższych klas NIE ma własnej adnotacji). Sprawdź przez
         * refleksję, że ChildEntity.class.getAnnotation(...) wciąż zwraca
         * adnotację z NAJWYŻSZEGO przodka - wypisz jej wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CustomAnnotationBasedBuilderMarker {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj adnotację @Injectable z elementem String qualifier()
         * default "" - @Retention(RUNTIME), @Target(ElementType.FIELD).
         * Oznacz DWA pola w klasie ServiceContainer (np. @Injectable
         * Logger logger, @Injectable(qualifier = "primary") DataSource ds).
         * Napisz metodę skanującą pola klasy i wypisującą dla każdego
         * pola z @Injectable jego nazwę, typ i qualifier.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MetaAnnotationOnCustomAnnotation {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj META-adnotację @ApiEndpoint (@Retention(RUNTIME),
         * @Target(ElementType.ANNOTATION_TYPE) - działa TYLKO na innych
         * adnotacjach!) z elementem String group(). Oznacz nią WŁASNĄ
         * adnotację @GetEndpoint (@ApiEndpoint(group = "read")) i
         * @PostEndpoint (@ApiEndpoint(group = "write")). Odczytaj przez
         * refleksję @ApiEndpoint bezpośrednio Z KLASY adnotacji
         * GetEndpoint.class.getAnnotation(ApiEndpoint.class) i wypisz group.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CircularReferenceInAnnotationDesignDiscussion {
        /*
         * 🧪 Zadanie 27:
         * W KOMENTARZU (bez kompilowalnego kodu) opisz, dlaczego adnotacja
         * NIE MOŻE mieć elementu typu "samej siebie" (np. @interface Node {
         * Node next(); } jest NIEDOZWOLONE) - jakie ograniczenie typów
         * elementów adnotacji to wymusza (prymityw/String/Class/enum/
         * adnotacja/tablica - żadnych dowolnych referencji obiektowych).
         * Potem zaprojektuj (i zaimplementuj) POPRAWNĄ alternatywę do
         * wyrażenia hierarchii przy pomocy String parentId() zamiast
         * bezpośredniej referencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnnotationBasedTestRunner {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj adnotację @MiniTest (marker, @Retention(RUNTIME),
         * @Target(ElementType.METHOD)). Oznacz nią TRZY metody w klasie
         * MathTests (bez parametrów, void, testujące np. proste asercje
         * przez if+throw AssertionError). Napisz "mini test runner" -
         * metodę, która przez refleksję znajduje WSZYSTKIE metody z
         * @MiniTest, wywołuje każdą (method.invoke) w try-catch, i
         * wypisuje podsumowanie: ile testów PASSED, ile FAILED (z komunikatem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CombiningRetentionSourceAndRuntimeAnnotations {
        /*
         * 🧪 Zadanie 29:
         * Oznacz JEDNĄ metodę DWIEMA różnymi własnymi adnotacjami
         * jednocześnie: jedną z @Retention(SOURCE) (np. @Todo-podobna,
         * tylko dla dokumentacji), drugą z @Retention(RUNTIME) (np.
         * @Monitored, marker). W runtime odczytaj OBIE przez
         * getAnnotation() - wypisz, która zwraca null, a która realny
         * obiekt, i wyjaśnij w komentarzu dlaczego (nawiązanie do
         * pełnego tematu w Lekcji 14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignSmallDeclarativeConfigSystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj mini "deklaratywny system konfiguracji" łączący
         * WSZYSTKIE 4 meta-adnotacje poznane w lekcji: adnotację
         * @ConfigProperty (@Target(FIELD), @Retention(RUNTIME),
         * @Documented) z elementami String key() i String defaultValue()
         * default "", ORAZ adnotację klasową @ConfigSource
         * (@Target(TYPE), @Retention(RUNTIME), @Inherited) z elementem
         * String file(). Zbuduj klasę AppConfig oznaczoną @ConfigSource i
         * z kilkoma polami @ConfigProperty, oraz podklasę
         * ExtendedAppConfig (dziedziczącą @ConfigSource bez powtarzania).
         * Napisz metodę "dump", która przez refleksję wypisuje pełną
         * "deklarowaną konfigurację" (źródło + wszystkie property z ich
         * kluczami/wartościami domyślnymi) dla obu klas.
         */
        public static void main(String[] args) { }
    }
}
