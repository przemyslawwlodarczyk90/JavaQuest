package com.example.javaquest._13_libraries.Lesson04_LombokConstructorsAndBuilder;

public class _Exercises_Lesson04_LombokConstructorsAndBuilder {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_NoArgsAndAllArgsConstructor {
        /*
         * 🧪 Zadanie 1:
         * Utworz klase Rectangle z polami int width, int height (zwykle, NIE
         * final). Dodaj @NoArgsConstructor, @AllArgsConstructor i @Getter. W
         * main() utworz jeden obiekt konstruktorem bezargumentowym i jeden
         * przez konstruktor Rectangle(5, 10) - wypisz oba przez gettery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OnlyAllArgsConstructor {
        /*
         * 🧪 Zadanie 2:
         * Utworz klase Config z polami String host, int port. Dodaj TYLKO
         * @AllArgsConstructor (bez @NoArgsConstructor) oraz @Getter. W main()
         * utworz obiekt new Config("localhost", 8080) i wypisz jego pola. W
         * komentarzu zapisz, dlaczego "new Config()" (bez argumentow) NIE
         * skompilowalby sie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RequiredArgsConstructorBasic {
        /*
         * 🧪 Zadanie 3:
         * Utworz klase Employee z polem final String pesel oraz zwyklym
         * String department. Dodaj @RequiredArgsConstructor, @Getter i
         * @Setter. Utworz obiekt konstruktorem przyjmujacym TYLKO pesel,
         * potem ustaw department setterem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RequiredArgsConstructorWithBooleanDefault {
        /*
         * 🧪 Zadanie 4:
         * Utworz klase Task z polami final String title (wymagane) oraz
         * boolean done (zwykle, bez inicjalizatora). Dodaj
         * @RequiredArgsConstructor, @Getter, @Setter. Zweryfikuj w main(), ze
         * wygenerowany konstruktor przyjmuje TYLKO title.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NonNullOnNonFinalField {
        /*
         * 🧪 Zadanie 5:
         * Utworz klase Contact z polem @NonNull String phoneNumber (NIE
         * final) oraz String name. Dodaj @RequiredArgsConstructor i @Getter.
         * Zweryfikuj w main(), ze proba utworzenia new Contact(null) rzuca
         * NullPointerException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BuilderBasic {
        /*
         * 🧪 Zadanie 6:
         * Utworz klase Book z polami String isbn, String title, int pages.
         * Dodaj @Builder, @Getter i @ToString. Zbuduj obiekt przez
         * Book.builder().isbn(...).title(...).pages(...).build() i wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BuilderSkippedFieldNoDefault {
        /*
         * 🧪 Zadanie 7:
         * Utworz klase Coordinates z polami double lat, double lon (BEZ
         * inicjalizatorow). Dodaj @Builder. Zbuduj obiekt ustawiajac przez
         * buildera TYLKO lat (pomin lon) - wypisz obiekt i zaobserwuj, ze lon
         * ma wartosc domyslna 0.0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuilderIgnoresFieldInitializerPitfall {
        /*
         * 🧪 Zadanie 8:
         * Utworz klase Server z polami String host oraz int port = 8080
         * (pole MA inicjalizator), BEZ @Builder.Default. Dodaj @Builder.
         * Zbuduj obiekt przez buildera BEZ jawnego ustawienia .port(...) i
         * zaobserwuj w main(), ze port wynosi 0 (a NIE 8080) - to typowa
         * pulapka Buildera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixPitfallWithBuilderDefault {
        /*
         * 🧪 Zadanie 9:
         * Napraw problem z Zadania 8: dodaj @Builder.Default do pola port w
         * klasie Server. Zweryfikuj w main(), ze teraz port=8080, gdy nie jest
         * jawnie ustawiony, oraz ze mozna go nadpisac np. na 9090 przez
         * .port(9090).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ValueBasicImmutability {
        /*
         * 🧪 Zadanie 10:
         * Utworz klase Point3D (@Value) z polami int x, int y, int z. Utworz
         * obiekt przez konstruktor wszystkich pol wygenerowany automatycznie
         * (new Point3D(1, 2, 3)), wypisz go. W komentarzu wyjasnij, dlaczego
         * kod "point.setX(5)" nie skompiluje sie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RequiredArgsIgnoresNonFinalInitializer {
        /*
         * 🧪 Zadanie 11:
         * Utworz klase Money z polami final java.math.BigDecimal amount
         * (wymagane) oraz String note = "" (zwykle, MA inicjalizator). Dodaj
         * @RequiredArgsConstructor, @Getter, @Setter. Zweryfikuj w main(), ze
         * konstruktor przyjmuje TYLKO amount, a note po utworzeniu obiektu ma
         * wartosc null (NIE ""), bo @RequiredArgsConstructor NIE respektuje
         * inicjalizatorow pol nie-final - opisz w komentarzu roznice wzgledem
         * @Builder.Default z Zadania 9.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TwoConstructorAnnotationsTogether {
        /*
         * 🧪 Zadanie 12:
         * Utworz klase Person z polami final String firstName, final String
         * lastName (oba wymagane) oraz int age (zwykle). Dodaj JEDNOCZESNIE
         * @RequiredArgsConstructor i @AllArgsConstructor oraz @ToString. W
         * main() utworz jeden obiekt konstruktorem 2-argumentowym i drugi
         * konstruktorem 3-argumentowym - wypisz oba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NonNullInAllArgsConstructor {
        /*
         * 🧪 Zadanie 13:
         * Utworz klase Event z polami String name, java.time.LocalDate date,
         * @NonNull String organizer. Dodaj @AllArgsConstructor. Zweryfikuj w
         * main(), ze przekazanie null jako organizer do konstruktora
         * 3-argumentowego rzuca NullPointerException - potwierdz w
         * komentarzu, ze @NonNull dziala rowniez w @AllArgsConstructor, nie
         * tylko w @RequiredArgsConstructor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuilderWithWholeListArgument {
        /*
         * 🧪 Zadanie 14:
         * Utworz klase Playlist z polami String name, java.util.List&lt;String&gt;
         * songs. Dodaj @Builder i @ToString. W main() zbuduj obiekt
         * przekazujac do buildera GOTOWA liste 3 piosenek jednym wywolaniem
         * .songs(list) - wypisz wynik. W komentarzu zapisz, ze zwykly
         * @Builder (bez @Singular) NIE pozwala dodawac elementow po jednym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BuilderWrappedInFactoryMethod {
        /*
         * 🧪 Zadanie 15:
         * Utworz klase Range z polami int min, int max. Dodaj @Builder. Dopisz
         * RECZNIE statyczna metode "of(int min, int max)", ktora wewnatrz
         * woła Range.builder().min(min).max(max).build(). Zademonstruj w
         * main() wywolanie Range.of(1, 10) obok bezposredniego wywolania
         * Range.builder()....build().
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuilderDefaultOnNonNullField {
        /*
         * 🧪 Zadanie 16:
         * Utworz klase User z polami final @NonNull String id, String email,
         * @Builder.Default boolean active = true. Dodaj @Builder. Zbuduj dwa
         * obiekty: jeden bez jawnego .active(...), drugi z .active(false) -
         * porownaj wyniki w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ValueWithExtraInstanceMethod {
        /*
         * 🧪 Zadanie 17:
         * Utworz klase ImmutableRange (@Value) z polami int start, int end.
         * Dopisz RECZNIE metode instancyjna "length()" zwracajaca end - start
         * (klasa z @Value moze miec WLASNE dodatkowe metody obok pol).
         * Zweryfikuj dzialanie length() w main() na przykladzie (2, 9).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuilderAndRequiredArgsSameClass {
        /*
         * 🧪 Zadanie 18:
         * Utworz klase Wallet z polami final String owner (wymagane),
         * @Builder.Default double balance = 0.0. Dodaj JEDNOCZESNIE @Builder i
         * @RequiredArgsConstructor. W main() utworz jeden obiekt przez
         * Wallet.builder().owner(...).build() i jeden przez konstruktor
         * wymagany new Wallet("..."). Porownaj wartosc balance w obu
         * przypadkach i wyjasnij w komentarzu roznice (tylko @Builder.Default
         * dziala przy tworzeniu przez buildera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuilderNonNullFailsAtBuildTime {
        /*
         * 🧪 Zadanie 19:
         * Utworz klase Shape z polami String type, @NonNull String color,
         * double area. Dodaj @Builder. Zweryfikuj w main(), ze pominiecie
         * .color(...) przy budowaniu (Shape.builder().type(...).area(...).build())
         * rzuca NullPointerException DOKLADNIE w momencie wywolania build().
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ValueWithStaticFactoryMethod {
        /*
         * 🧪 Zadanie 20:
         * Utworz klase Snapshot (@Value) z polami String label, long
         * timestamp. Dopisz RECZNIE statyczna metode "now(String label)"
         * zwracajaca new Snapshot(label, System.currentTimeMillis()). W
         * main() utworz 2 snapshoty z krotkim opoznieniem (np. Thread.sleep)
         * miedzy nimi i porownaj ich pole timestamp.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_NestedValueInsideBuilderClass {
        /*
         * 🧪 Zadanie 21:
         * Utworz klase Address (@Value: String street, String city). Utworz
         * klase Order z polami final @NonNull String orderId, Address
         * shippingAddress (zwykle pole), @Builder.Default
         * java.util.List&lt;String&gt; items = new java.util.ArrayList&lt;&gt;(). Dodaj
         * @Builder i @RequiredArgsConstructor na Order. Zbuduj w main()
         * zamowienie z zagniezdzonym Address i lista pozycji przez buildera -
         * wypisz pelny obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ManualConstructorCallingSuper {
        /*
         * 🧪 Zadanie 22:
         * Utworz klase bazowa Vehicle z polem final String vin
         * (@RequiredArgsConstructor, @Getter) oraz klase Car extends Vehicle z
         * WLASNYM, RECZNIE NAPISANYM konstruktorem, ktory woła super(vin) i
         * ustawia dodatkowe pole model. Wyjasnij w komentarzu, ze Lombok
         * generuje konstruktor TYLKO dla pol WLASNEJ klasy - dziedziczenie
         * trzeba obsluzyc recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ValueWithFactoryAndConversionMethod {
        /*
         * 🧪 Zadanie 23:
         * Utworz klase Temperature (@Value) z polami double celsius, String
         * unit. Dopisz statyczna metode fabrykujaca "ofCelsius(double celsius)"
         * zwracajaca new Temperature(celsius, "C") oraz instancyjna metode
         * "toFahrenheit()" zwracajaca NOWY obiekt Temperature (bo klasa jest
         * niemutowalna). Zademonstruj obie metody w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreeConstructorStylesComparisonTable {
        /*
         * 🧪 Zadanie 24:
         * Utworz klase Batch z polami final String batchId (wymagane),
         * @Builder.Default int size = 100, @Builder.Default boolean validated
         * = false. Dodaj RAZEM @Builder, @RequiredArgsConstructor,
         * @AllArgsConstructor. W main() utworz PO JEDNYM obiekcie kazdym z 3
         * sposobow (builder, required-args, all-args) i wypisz tabele
         * (System.out.printf) pokazujaca, dla ktorych sposobow inicjalizatory
         * pol (size=100, validated=false) faktycznie zadzialaly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RequiredArgsConstructorStaticName {
        /*
         * 🧪 Zadanie 25:
         * Utworz klase Threshold z polami @NonNull final Double limit oraz
         * String description. Dodaj @RequiredArgsConstructor(staticName =
         * "of") - sprawdz w dokumentacji Lomboka, co robi parametr
         * staticName (ukrywa konstruktor jako prywatny i generuje zamiast
         * niego statyczna metode fabrykujaca o podanej nazwie). Utworz obiekt
         * przez Threshold.of(5.0) - w komentarzu zapisz, ze "new
         * Threshold(5.0)" juz sie NIE skompiluje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ValueObjectsGroupedByCondition {
        /*
         * 🧪 Zadanie 26:
         * Utworz klase Grade (@Value) z polami String studentName, int score.
         * Dopisz statyczna metode "classify(java.util.List&lt;Grade&gt; grades)"
         * grupujaca oceny wg przedzialow (np. score&gt;=90 -&gt; "A", score&gt;=75
         * -&gt; "B", pozostale -&gt; "C") do java.util.Map&lt;String,
         * java.util.List&lt;Grade&gt;&gt;. Zademonstruj na liscie 5 przykladowych
         * ocen zbudowanych przez konstruktor generowany przez @Value.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuilderDefaultWithNonNullRequiredCombo {
        /*
         * 🧪 Zadanie 27:
         * Utworz klase RetryPolicy z polami @Builder.Default int maxAttempts
         * = 3, @Builder.Default long delayMillis = 1000, @NonNull final
         * String operationName. Dodaj @Builder i @RequiredArgsConstructor.
         * Dopisz metode instancyjna "describe()" zwracajaca sformatowany opis
         * polityki. Zbuduj w main() 2 warianty (domyslny i z nadpisanymi
         * wartosciami) i wypisz ich describe().
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ValueWithManualValidationMethod {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj klase ImmutableInterval (@Value: java.time.LocalDate
         * start, java.time.LocalDate end) z DODATKOWA, RECZNIE napisana
         * metoda "isValid()" (true, gdy start jest przed end lub rowny end).
         * W main() utworz 3 interwaly (poprawny, odwrocony, ten sam dzien) i
         * wypisz wynik isValid() dla kazdego - w komentarzu zapisz, ze
         * @Value NIE waliduje danych automatycznie, walidacje trzeba dopisac
         * recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GenericClassWithBuilder {
        /*
         * 🧪 Zadanie 29:
         * Utworz klase generyczna ApiResponse&lt;T&gt; z polami final int
         * statusCode (wymagane), final T body (wymagane), @Builder.Default
         * String requestId = "unknown". Dodaj @Builder i
         * @RequiredArgsConstructor. Zbuduj w main() ApiResponse&lt;String&gt; i
         * ApiResponse&lt;Integer&gt; przez buildera, wypisz oba - w komentarzu
         * zapisz, ze @Builder wspiera typy generyczne bez dodatkowej
         * konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniDomainModelWithThreeLinkedClasses {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-model domenowy z 3 powiazanych klas: Customer (@Value:
         * String id, String name), Product (@Builder, @Builder.Default: String
         * sku, double price, int stock = 0), Order (@Builder,
         * @RequiredArgsConstructor: final Customer customer wymagany,
         * @Builder.Default java.util.List&lt;Product&gt; products = new
         * java.util.ArrayList&lt;&gt;()). W main() zbuduj klienta, 2 produkty i
         * zamowienie laczace je przez buildera, a nastepnie wypisz
         * podsumowanie zamowienia (liczba produktow, suma cen) - uzasadnij w
         * komentarzu wybor kazdej kombinacji adnotacji.
         */
        public static void main(String[] args) { }
    }
}
