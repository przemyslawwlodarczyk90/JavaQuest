package com.example.javaquest._13_libraries.Lesson05_LombokAdvancedAndPitfalls;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _Lesson05_LombokAdvancedAndPitfalls {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: LOMBOK - ZAAWANSOWANE ADNOTACJE I PULAPKI ===");

        /*
         * ============================================================
         * 📦 @Slf4j - AUTOMATYCZNE POLE "log" DO LOGOWANIA
         * ============================================================
         * - Umieszczona NA KLASIE, dokleja PRYWATNE STATYCZNE FINALNE pole
         *   "log" typu org.slf4j.Logger, odpowiednik recznego:
         *   "private static final Logger log = LoggerFactory.getLogger(TwojaKlasa.class);"
         *   - dokladnie takiego idiomu, jaki poznales w Lekcji 15
         *   (_13_libraries/Lesson15_WhySlf4jNotSystemOut) - @Slf4j to po prostu
         *   skrot, ktory oszczedza wpisania tej jednej linijki w KAZDEJ klasie.
         * - Nazwa pola jest ZAWSZE "log" (male litery) - to sztywna konwencja
         *   tej adnotacji, nie mozna jej zmienic bez uzycia parametru "topic"
         *   (rzadko potrzebne).
         * - Rodzina pokrewnych adnotacji dla innych frameworkow logujacych
         *   (Lombok generuje analogiczne pole "log", tylko innego typu):
         *   @CommonsLog (Apache Commons Logging), @Log4j2 (Log4j2 API
         *   bezposrednio, bez SLF4J), @Log (java.util.logging, wbudowane w
         *   JDK). W tym projekcie SLF4J + Logback sa juz dostepne
         *   transytywnie przez spring-boot-starter (patrz Lekcja 15), wiec
         *   @Slf4j jest naturalnym wyborem - NIE ma potrzeby dodawania
         *   zadnej nowej zaleznosci.
         */
        System.out.println("\n--- @Slf4j: pole 'log' generowane automatycznie ---");
        demonstrateSlf4j();

        /*
         * ============================================================
         * 🔹 @Builder(toBuilder = true) - MODYFIKOWANA KOPIA ISTNIEJACEGO OBIEKTU
         * ============================================================
         * - Zwykly @Builder pozwala TWORZYC nowe obiekty od zera. Parametr
         *   "toBuilder = true" dodaje DODATKOWA metode instancyjna
         *   "toBuilder()" na klasie - zwraca ona builder WYPELNIONY
         *   aktualnymi wartosciami WSZYSTKICH pol istniejacego obiektu.
         * - Typowy scenariusz: masz gotowy obiekt i chcesz "kopie ze zmiana
         *   jednego pola" - zamiast recznie przepisywac wszystkie pola,
         *   wolasz existing.toBuilder().pole(nowaWartosc).build().
         * - WAZNE: oryginalny obiekt NIE jest modyfikowany - toBuilder()
         *   tworzy NIEZALEZNA kopie danych w builderze, build() zwraca
         *   ZUPELNIE NOWY obiekt.
         */
        System.out.println("\n--- @Builder(toBuilder = true): kopia ze zmiana jednego pola ---");
        demonstrateToBuilder();

        /*
         * ============================================================
         * 🔹 @With - "withX(wartosc)" DLA OBIEKTOW NIEMUTOWALNYCH
         * ============================================================
         * - Generuje dla kazdego pola metode "withNazwaPola(nowaWartosc)",
         *   ktora zwraca NOWY obiekt - IDENTYCZNY jak oryginal, ale z JEDNYM
         *   polem zmienionym. Oryginalny obiekt pozostaje NIETKNIETY.
         * - Idealnie pasuje do @Value (pola final, brak setterow) - @With
         *   daje wygodny sposob na "modyfikacje" bez psucia niemutowalnosci:
         *   zamiast setX(...) na tym samym obiekcie, dostajesz NOWY obiekt z
         *   inna wartoscia.
         * - Roznica wzgledem @Builder(toBuilder = true): @With to
         *   POJEDYNCZE, proste wywolanie metody dla JEDNEGO pola (szybsze do
         *   napisania przy jednej zmianie), podczas gdy toBuilder() lepiej
         *   sprawdza sie, gdy trzeba zmienic KILKA pol naraz w jednym
         *   wywolaniu lancuchowym.
         */
        System.out.println("\n--- @With: withX(wartosc) zwraca NOWY obiekt ---");
        demonstrateWith();

        /*
         * ============================================================
         * 🔍 PULAPKA: @EqualsAndHashCode + DZIEDZICZENIE (callSuper)
         * ============================================================
         * - Domyslnie (callSuper NIE podane jawnie) @EqualsAndHashCode
         *   generuje equals()/hashCode() TYLKO na podstawie pol WLASNEJ
         *   klasy - pola z klasy NADRZEDNEJ sa CALKOWICIE IGNOROWANE, nawet
         *   jesli klasa nadrzedna ma wazne pola identyfikujace obiekt.
         * - Jesli Twoja klasa DZIEDZICZY po innej klasie (a nie tylko po
         *   Object) i NIE podasz jawnie parametru callSuper, Lombok
         *   WYPISZE OSTRZEZENIE PRZY KOMPILACJI: "Generating
         *   equals/hashCode implementation but without a call to superclass,
         *   even though this class does not extend java.lang.Object." -
         *   Lombok "podejrzewa", ze to przeoczenie, bo bardzo czesto
         *   pomija sie pola nadklasy przez przypadek.
         * - callSuper = false (jawnie) - wycisza ostrzezenie, ale
         *   ZACHOWANIE bez zmian: pola nadklasy nadal ignorowane. Uzywaj
         *   tego, gdy naprawde CHCESZ ignorowac pola nadklasy (np. bo klasa
         *   bazowa ma tylko techniczne pole jak "createdAt", nieistotne dla
         *   tozsamosci obiektu).
         * - callSuper = true - dokleja wywolanie super.equals()/
         *   super.hashCode() na poczatku wygenerowanej metody, wiec pola
         *   nadklasy TEZ licza sie do rownosci. Uzywaj, gdy pola z nadklasy
         *   (np. wspolny identyfikator "vin" w klasie bazowej Vehicle) SA
         *   istotne dla tozsamosci obiektu.
         */
        System.out.println("\n--- @EqualsAndHashCode + dziedziczenie: callSuper=false vs callSuper=true ---");
        demonstrateEqualsHashCodeInheritancePitfall();

        /*
         * ============================================================
         * 🔍 PULAPKA: @Data / @EqualsAndHashCode NA MUTOWALNYCH ENCJACH
         * ============================================================
         * - Klasyczna encja (np. z Hibernate, _12_hibernate) ma zazwyczaj
         *   pole "id" (stabilne, ustawiane RAZ przy zapisie) oraz kilka pol
         *   MUTOWALNYCH (name, status, itd. - zmieniaja sie w czasie zycia
         *   obiektu). Domyslne @Data/@EqualsAndHashCode wlicza WSZYSTKIE
         *   pola do equals()/hashCode() - WLACZNIE z tymi mutowalnymi.
         * - Problem: HashSet/HashMap UKLADAJA obiekty w "kubelkach" wedlug
         *   hashCode() OBLICZONEGO W MOMENCIE WSTAWIENIA. Jesli PO wstawieniu
         *   obiektu do zbioru zmienisz mutowalne pole (setter), hashCode()
         *   obiektu SIE ZMIENIA - ale obiekt w strukturze wewnetrznej
         *   HashSet/HashMap POZOSTAJE w STARYM kubelku. Kolejne
         *   set.contains(ten_sam_obiekt) liczy NOWY hashCode, szuka w NOWYM
         *   kubelku - i "nie znajduje" obiektu, ktory tam WCIAZ FIZYCZNIE
         *   JEST (rozmiar zbioru sie nie zmienia, ale wyszukiwanie po
         *   referencji zawodzi). To sa realne, trudne do zdiagnozowania
         *   bledy w produkcyjnym kodzie.
         * - Naprawa: @EqualsAndHashCode(of = "id") (albo "exclude" na polach
         *   mutowalnych) - rownosc/hashCode oparte TYLKO o stabilny klucz
         *   biznesowy (najczesciej techniczne id), ktory NIGDY sie nie
         *   zmienia po utworzeniu obiektu.
         */
        System.out.println("\n--- Pulapka: mutowalne pole w equals/hashCode obiektu w HashSet ---");
        demonstrateMutableEntityEqualsPitfall();

        /*
         * ============================================================
         * 🔍 PULAPKA: DWUKIERUNKOWE ASOCJACJE + DOMYSLNE @ToString/@EqualsAndHashCode
         * ============================================================
         * - Gdy dwie klasy WZAJEMNIE sie do siebie odwoluja (np. Parent ma
         *   liste Child, kazde Child ma referencje z powrotem do swojego
         *   Parent - klasyczny wzorzec z _12_hibernate/Lesson12), a OBIE maja
         *   domyslne @ToString/@EqualsAndHashCode (wliczajace WSZYSTKIE
         *   pola), wywolanie toString()/equals() na jednym obiekcie
         *   URUCHAMIA NIESKONCZONA REKURENCJE: Parent.toString() wypisuje
         *   liste Child -> kazde Child.toString() wypisuje z powrotem swoj
         *   Parent -> Parent.toString() zaczyna sie od nowa... az do
         *   przepelnienia stosu (StackOverflowError).
         * - NAPRAWA: @ToString.Exclude / @EqualsAndHashCode.Exclude na polu
         *   "wstecznym" (back-reference) - WYSTARCZY wykluczyc JEDNA stronen
         *   cyklu (np. pole "parent" w klasie Child), zeby przerwac
         *   rekurencje - druga strona (Parent.children) moze bezpiecznie
         *   zostac wliczona.
         */
        System.out.println("\n--- Pulapka: nieskonczona rekurencja w dwukierunkowych asocjacjach ---");
        demonstrateBidirectionalRecursionPitfall();

        /*
         * ============================================================
         * 📌 delombok - "PODEJRZYJ" WYGENEROWANY KOD
         * ============================================================
         * - Adnotacje Lomboka dzialaja na poziomie AST kompilatora (podczas
         *   kompilacji), wiec w PLIKU ZRODLOWYM nigdy nie widzisz
         *   wygenerowanych metod - moze to utrudniac zrozumienie, "co
         *   dokladnie Lombok tutaj zrobil".
         * - "delombok" to narzedzie (dostepne jako cel Maven
         *   "mvn lombok:delombok" albo wtyczka/akcja w IDE, np. w IntelliJ
         *   IDEA plugin Lomboka ma opcje "Delombok" w menu kontekstowym pliku)
         *   ktore BIERZE plik z adnotacjami Lomboka i ZAPISUJE nowy plik
         *   .java z PELNYM, JAWNYM kodem Javy - zero adnotacji Lomboka,
         *   tylko "zwykla" wygenerowana Java, ktora normalnie musialbys
         *   napisac recznie. Bardzo przydatne do debugowania "dlaczego mam
         *   taki, a nie inny wynik equals()" albo do jednorazowego
         *   pokazania kursantowi, co NAPRAWDE siedzi pod adnotacja.
         * - Ponizej ilustracyjny przyklad "przed/po" (tylko tekst - w tej
         *   lekcji NIE odpalamy realnego narzedzia delombok, to wymagaloby
         *   dodatkowego pluginu Maven w pom.xml).
         */
        System.out.println("\n--- delombok: podglad wygenerowanego kodu (ilustracja) ---");
        explainDelombok();

        /*
         * ============================================================
         * 🔹 BONUS: @UtilityClass
         * ============================================================
         * - Przeznaczona dla klas "narzedziowych" typu java.util.Collections
         *   albo java.lang.Math - klasy zawierajacej WYLACZNIE statyczne
         *   metody pomocnicze, ktorej NIGDY nie tworzy sie instancji.
         * - Automatycznie: (1) dodaje "private" konstruktor bezargumentowy,
         *   ktory rzuca wyjatkiem, jesli ktos sprobuje go wywolac przez
         *   refleksje, (2) oznacza klase jako "final" (nie mozna jej
         *   rozszerzac), (3) NAJWAZNIEJSZE - czyni WSZYSTKIE pola i metody w
         *   klasie NIEJAWNIE statycznymi - nie trzeba samemu pisac "static"
         *   przy kazdej metodzie/polu.
         * - Efekt: mozesz wywolywac metody klasy bezposrednio po jej
         *   nazwie (MathUtils.square(5)), mimo ze w kodzie zrodlowym metoda
         *   NIE ma jawnego slowa "static".
         */
        System.out.println("\n--- @UtilityClass: wszystkie skladowe niejawnie statyczne ---");
        demonstrateUtilityClass();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @Slf4j (i pokrewne @CommonsLog/@Log4j2/@Log) - generuje pole
         *   "log", oszczedzajac linijke LoggerFactory.getLogger(...) w
         *   kazdej klasie (patrz Lekcja 15 po pelne omowienie SLF4J).
         * - @Builder(toBuilder = true) - metoda toBuilder() do tworzenia
         *   ZMODYFIKOWANEJ KOPII istniejacego obiektu, bez ruszania
         *   oryginalu.
         * - @With - metody withX(wartosc) zwracajace nowy, niemutowalny
         *   obiekt z JEDNYM zmienionym polem - naturalny partner @Value.
         * - @EqualsAndHashCode + dziedziczenie: PAMIETAJ o jawnym
         *   callSuper = true/false - bez tego Lombok ostrzega przy
         *   kompilacji, a domyslne "false" moze byc realnym bledem, jesli
         *   pola nadklasy sa istotne dla tozsamosci obiektu.
         * - @Data/@EqualsAndHashCode na mutowalnych encjach: UNIKAJ
         *   wliczania mutowalnych pol do equals/hashCode - obiekt w
         *   HashSet/HashMap "gubi sie" po zmianie pola. Uzywaj
         *   @EqualsAndHashCode(of = "id").
         * - Dwukierunkowe asocjacje: wykluczaj pole "wsteczne" przez
         *   @ToString.Exclude/@EqualsAndHashCode.Exclude, inaczej
         *   nieskonczona rekurencja i StackOverflowError.
         * - delombok - narzedzie do podejrzenia PELNEGO, wygenerowanego
         *   kodu Javy pod adnotacjami.
         * - @UtilityClass - klasa czysto statyczna, bez potrzeby pisania
         *   "static" recznie przy kazdej metodzie.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateSlf4j() {
        LoggingDemo loggingDemo = new LoggingDemo();
        loggingDemo.run();
        System.out.println("(Pelne omowienie poziomow logowania, konfiguracji itd. w Lekcji 15 i Lekcji 16)");
    }

    private static void demonstrateToBuilder() {
        HttpRequestConfig original = HttpRequestConfig.builder()
                .url("https://example.com/api")
                .method("GET")
                .build();
        System.out.println("Oryginal: " + original);

        HttpRequestConfig modified = original.toBuilder()
                .method("POST")
                .timeoutMillis(10_000)
                .build();
        System.out.println("Kopia ze zmiana method+timeout: " + modified);
        System.out.println("Oryginal NIETKNIETY po toBuilder(): " + original);
    }

    private static void demonstrateWith() {
        Coordinates warsaw = new Coordinates(52.2297, 21.0122);
        Coordinates shiftedLat = warsaw.withLat(52.5);
        System.out.println("Oryginal: " + warsaw);
        System.out.println("Po withLat(52.5): " + shiftedLat);
        System.out.println("Oryginal nadal ma stara wartosc lat (niemutowalnosc): " + warsaw.getLat());
    }

    private static void demonstrateEqualsHashCodeInheritancePitfall() {
        CarNoSuper car1 = new CarNoSuper();
        car1.setVin("VIN-AAA");
        car1.setModel("Model X");

        CarNoSuper car2 = new CarNoSuper();
        car2.setVin("VIN-BBB");
        car2.setModel("Model X");

        System.out.println("callSuper=false: car1.equals(car2) mimo ROZNEGO vin = "
                + car1.equals(car2) + " (BLEDNE - pole vin z nadklasy zignorowane)");

        CarWithSuper car3 = new CarWithSuper();
        car3.setVin("VIN-AAA");
        car3.setModel("Model X");

        CarWithSuper car4 = new CarWithSuper();
        car4.setVin("VIN-BBB");
        car4.setModel("Model X");

        System.out.println("callSuper=true: car3.equals(car4) przy ROZNYM vin = "
                + car3.equals(car4) + " (POPRAWNE - vin z nadklasy sie liczy)");
    }

    private static void demonstrateMutableEntityEqualsPitfall() {
        System.out.println("--- @Data (wszystkie pola w equals/hashCode) ---");
        Set<BuggyEntity> buggySet = new HashSet<>();
        BuggyEntity buggy = new BuggyEntity();
        buggy.setId(1L);
        buggy.setName("Original");
        buggySet.add(buggy);
        System.out.println("Zaraz po dodaniu, contains(buggy) = " + buggySet.contains(buggy));

        buggy.setName("Changed"); // mutacja PO wstawieniu do zbioru
        System.out.println("Po zmianie 'name' (mutacja po wstawieniu), contains(buggy) = "
                + buggySet.contains(buggy) + " (obiekt 'zgubiony', mimo ze rozmiar zbioru = " + buggySet.size() + ")");

        System.out.println("--- @EqualsAndHashCode(of = \"id\") (naprawione) ---");
        Set<FixedEntity> fixedSet = new HashSet<>();
        FixedEntity fixed = new FixedEntity();
        fixed.setId(1L);
        fixed.setName("Original");
        fixedSet.add(fixed);
        System.out.println("Zaraz po dodaniu, contains(fixed) = " + fixedSet.contains(fixed));

        fixed.setName("Changed"); // mutacja pola NIEUWZGLEDNIONEGO w equals/hashCode
        System.out.println("Po zmianie 'name', contains(fixed) = "
                + fixedSet.contains(fixed) + " (POPRAWNIE nadal znaleziony - identyfikacja tylko po id)");
    }

    private static void demonstrateBidirectionalRecursionPitfall() {
        System.out.println("--- Wersja ZEPSUTA: domyslne @ToString po obu stronach ---");
        BadParent badParent = new BadParent();
        badParent.setName("Rodzic");
        BadChild badChild = new BadChild();
        badChild.setName("Dziecko");
        badParent.getChildren().add(badChild);
        badChild.setParent(badParent);

        try {
            System.out.println(badParent.toString());
        } catch (StackOverflowError overflow) {
            System.out.println("Zlapano StackOverflowError przy badParent.toString() - "
                    + "nieskonczona rekurencja Parent<->Child (jak przewidziano w teorii)");
        }

        System.out.println("--- Wersja NAPRAWIONA: @ToString.Exclude/@EqualsAndHashCode.Exclude na polu wstecznym ---");
        GoodParent goodParent = new GoodParent();
        goodParent.setName("Rodzic");
        GoodChild goodChild = new GoodChild();
        goodChild.setName("Dziecko");
        goodParent.getChildren().add(goodChild);
        goodChild.setParent(goodParent);

        System.out.println("goodParent.toString() dziala bez rekurencji: " + goodParent);
        System.out.println("goodChild.toString() (bez pola 'parent'): " + goodChild);
        System.out.println("goodParent.equals(goodParent) tez dziala bez rekurencji: " + goodParent.equals(goodParent));
    }

    private static void explainDelombok() {
        String before = """
                @Getter
                @EqualsAndHashCode(of = "id")
                static class Product {
                    private final Long id;
                    private String name;
                }
                """;
        String after = """
                static class Product {
                    private final Long id;
                    private String name;

                    public Product(Long id, String name) {
                        this.id = id;
                        this.name = name;
                    }

                    public Long getId() { return this.id; }
                    public String getName() { return this.name; }

                    @Override
                    public boolean equals(Object o) {
                        if (o == this) return true;
                        if (!(o instanceof Product)) return false;
                        Product other = (Product) o;
                        Object thisId = this.getId();
                        Object otherId = other.getId();
                        return thisId == null ? otherId == null : thisId.equals(otherId);
                    }

                    @Override
                    public int hashCode() {
                        Object id = this.getId();
                        return (id == null ? 43 : id.hashCode());
                    }
                }
                """;
        System.out.println("PRZED (kod, ktory faktycznie piszemy - z adnotacjami Lomboka):");
        System.out.println(before);
        System.out.println("PO 'mvn lombok:delombok' / akcji IDE 'Delombok' (pelny, jawny kod Javy):");
        System.out.println(after);
        System.out.println("(To TYLKO ilustracja - wygenerowany kod moze nieznacznie roznic sie w szczegolach "
                + "od konkretnej wersji Lomboka, ale idea 'zero magii, wszystko jawnie napisane' pozostaje ta sama.)");
    }

    private static void demonstrateUtilityClass() {
        double squared = MathUtils.square(5.0);
        double area = MathUtils.circleArea(3.0);
        System.out.println("MathUtils.square(5.0) = " + squared + " (metoda 'square' NIE ma slowa 'static' w kodzie zrodlowym)");
        System.out.println("MathUtils.circleArea(3.0) = " + area);
        System.out.println("MathUtils.PI = " + MathUtils.PI + " (pole tez niejawnie statyczne)");
        System.out.println("'new MathUtils()' NIE skompilowalby sie - konstruktor jest prywatny i rzuca wyjatkiem przy probie refleksyjnego wywolania.");
    }

    // ------------------------------------------------------------------
    // @Slf4j - generuje "private static final org.slf4j.Logger log = ...".
    // ------------------------------------------------------------------
    @Slf4j
    static class LoggingDemo {
        void run() {
            log.info("Log wygenerowany przez pole 'log' z @Slf4j, klasa: {}", this.getClass().getSimpleName());
        }
    }

    // ------------------------------------------------------------------
    // @Builder(toBuilder = true) - dodatkowa metoda toBuilder() do
    // tworzenia zmodyfikowanej kopii.
    // ------------------------------------------------------------------
    @Getter
    @ToString
    @Builder(toBuilder = true)
    static class HttpRequestConfig {
        private String url;
        private String method;
        @Builder.Default
        private int timeoutMillis = 5000;
    }

    // ------------------------------------------------------------------
    // @Value + @With - niemutowalna klasa z metodami withX(wartosc).
    // ------------------------------------------------------------------
    @Value
    @With
    static class Coordinates {
        double lat;
        double lon;
    }

    // ------------------------------------------------------------------
    // Vehicle/Car - callSuper=false (domyslne zachowanie, ale podane
    // JAWNIE, zeby uniknac ostrzezenia kompilatora Lomboka).
    // ------------------------------------------------------------------
    @Getter
    @Setter
    static class Vehicle {
        private String vin;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    static class CarNoSuper extends Vehicle {
        private String model;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    static class CarWithSuper extends Vehicle {
        private String model;
    }

    // ------------------------------------------------------------------
    // @Data na "encji" - wszystkie pola (WLACZNIE z mutowalnym "name")
    // licza sie do equals/hashCode - pulapka w HashSet.
    // ------------------------------------------------------------------
    @Data
    static class BuggyEntity {
        private Long id;
        private String name;
    }

    // ------------------------------------------------------------------
    // Naprawiona wersja - equals/hashCode TYLKO na podstawie stabilnego id.
    // ------------------------------------------------------------------
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(of = "id")
    static class FixedEntity {
        private Long id;
        private String name;
    }

    // ------------------------------------------------------------------
    // BadParent/BadChild - domyslne @ToString/@EqualsAndHashCode po OBU
    // stronach dwukierunkowej asocjacji -> nieskonczona rekurencja.
    // ------------------------------------------------------------------
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    static class BadParent {
        private String name;
        private List<BadChild> children = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    static class BadChild {
        private String name;
        private BadParent parent;
    }

    // ------------------------------------------------------------------
    // GoodParent/GoodChild - pole "wsteczne" (back-reference) w Child
    // wykluczone z @ToString i @EqualsAndHashCode -> brak rekurencji.
    // ------------------------------------------------------------------
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    static class GoodParent {
        private String name;
        private List<GoodChild> children = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    static class GoodChild {
        private String name;
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private GoodParent parent;
    }

    // ------------------------------------------------------------------
    // @UtilityClass - konstruktor prywatny, klasa final, wszystkie
    // skladowe niejawnie statyczne (bez slowa "static" w kodzie zrodlowym).
    // ------------------------------------------------------------------
    @UtilityClass
    static class MathUtils {
        double PI = 3.14159;

        double square(double x) {
            return x * x;
        }

        double circleArea(double radius) {
            return PI * square(radius);
        }
    }
}
