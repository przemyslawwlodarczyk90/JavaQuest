package com.example.javaquest._16_clean_code.Lesson19_ImmutabilityInPractice;

import java.util.List;

public class _Lesson19_ImmutabilityInPractice {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: NIEZMIENNOSC W PRAKTYCE ===");

        /*
         * ============================================================
         * 📦 TA LEKCJA ZAKLADA ZNAJOMOSC MECHANIKI NIEZMIENNOSCI
         * ============================================================
         * - `final`, czym jest obiekt niezmienny, pulapka "final chroni
         *   TYLKO referencje" i technike OBRONNEGO KOPIOWANIA (defensive
         *   copying) poznales szczegolowo w
         *   `_14_advancedjava/Lesson24_Immutability` i
         *   `Lesson25_DefensiveCopying` - TUTAJ NIE powtarzamy tej
         *   mechaniki, tylko pytamy: JAK i KIEDY swiadomie WYBIERAC
         *   niezmiennosc jako narzedzie CZYSTEGO KODU.
         */
        System.out.println("Zaklada _14_advancedjava/Lesson24-25 - tu nacisk na PRAKTYCZNE zastosowanie w projektowaniu.");

        /*
         * ============================================================
         * 🔹 GLOWNA KORZYSC: MNIEJ MIEJSC, GDZIE MOZE WYSTAPIC BLAD
         * ============================================================
         * - Obiekt MUTOWALNY moze zmienic sie w KAZDYM miejscu, gdzie
         *   ktokolwiek trzyma do niego referencje - zeby zrozumiec jego
         *   AKTUALNY stan, teoretycznie trzeba znac CALA historie
         *   wywolan w calym programie.
         * - Obiekt NIEZMIENNY ma TYLKO JEDEN mozliwy stan przez CALY
         *   swoj czas zycia - ten stan jest ustalony raz, w konstruktorze,
         *   i NIGDY sie nie zmienia. Czytajac kod, NIE musisz sledzic
         *   "kto i kiedy mogl to zmienic".
         */
        demonstrateAliasingBugWithMutableObject();

        /*
         * ============================================================
         * 🔍 NIEZMIENNE WARTOSCI ZAMIAST "OBSESJI NA PUNKCIE PRYMITYWOW"
         * ============================================================
         * - Lesson14 (Code Smells) uczyl o "Primitive Obsession" - uzywaniu
         *   `String`/`double` tam, gdzie logicznie chodzi o odrebne
         *   pojecie. NAJLEPSZYM sposobem naprawy tego smellu jest maly,
         *   NIEZMIENNY typ wartosciowy (value object) - pilnuje swojej
         *   poprawnosci RAZ, w konstruktorze, i POTEM juz zawsze
         *   reprezentuje POPRAWNA wartosc (bo nie da sie go zmutowac na
         *   niepoprawna).
         * - `record` (poznany w `_02_oop/Lesson14_Records`) jest
         *   NATURALNYM narzedziem do takich typow - pola `final`
         *   domyslnie, konstruktor moze walidowac.
         */
        demonstrateImmutableValueObjectFixingPrimitiveObsession();

        /*
         * ============================================================
         * 🔹 WZORZEC "WITHER" - MODYFIKACJA PRZEZ NOWA KOPIE
         * ============================================================
         * - Skoro obiekt jest niezmienny, "modyfikacja" NIE MOZE zmienic
         *   istniejacego obiektu - zamiast tego metoda `withX(...)`
         *   zwraca NOWY obiekt, identyczny jak oryginal, ale z JEDNYM
         *   zmienionym polem.
         * - To NIE jest wolniejsze/gorsze niz mutacja "w miejscu" -
         *   oryginalny obiekt POZOSTAJE nienaruszony (moze byc nadal
         *   uzywany gdzie indziej z GWARANCJA, ze nikt go "pod spodem"
         *   nie zmienil).
         */
        demonstrateWitherPattern();

        /*
         * ============================================================
         * 🔍 BUILDER: KONSTRUOWANIE ZLOZONEGO NIEZMIENNEGO OBIEKTU KROK PO KROKU
         * ============================================================
         * - Obiekt niezmienny musi miec WSZYSTKIE swoje dane gotowe w
         *   momencie tworzenia (konstruktor) - to bywa niewygodne, gdy
         *   dane "przychodza" stopniowo (np. z formularza z opcjonalnymi
         *   polami).
         * - Wzorzec Builder rozwiazuje to: obiekt POMOCNICZY (mutowalny)
         *   zbiera dane KROK PO KROKU, a metoda `build()` na samym koncu
         *   tworzy JEDEN, finalny, NIEZMIENNY obiekt.
         * - To NIE jest sprzeczne z niezmiennoscia koncowego obiektu -
         *   mutowalny jest TYLKO tymczasowy Builder, nigdy finalny
         *   produkt.
         */
        demonstrateBuilderForComplexImmutableObject();

        /*
         * ============================================================
         * 🔹 NIEZMIENNE KOLEKCJE JAKO DOMYSLNY WYBOR W API
         * ============================================================
         * - Metoda zwracajaca kolekcje (np. `List<String> getTags()`)
         *   powinna domyslnie zwracac NIEMODYFIKOWALNA kolekcje
         *   (`List.of(...)`, `Collections.unmodifiableList(...)`) -
         *   zapobiega to sytuacji, gdy KTOS z zewnatrz przypadkowo
         *   (lub celowo) zmodyfikuje "wewnetrzna" liste obiektu przez
         *   referencje zwrocona z gettera.
         * - Poznales juz te technike jako "obronne kopiowanie" w
         *   `_14_advancedjava/Lesson25_DefensiveCopying` - tutaj
         *   podkreslamy, ze to powinien byc DOMYSLNY nawyk projektowy w
         *   KAZDYM publicznym API zwracajacym kolekcje, nie wyjatek.
         */
        demonstrateImmutableCollectionAsDefaultApiChoice();

        /*
         * ============================================================
         * 🔍 NIEZMIENNOSC I BEZPIECZENSTWO WATKOWE (KROTKO)
         * ============================================================
         * - Obiekt NIEZMIENNY jest z DEFINICJI bezpieczny watkowo
         *   (thread-safe) - skoro jego stan NIGDY sie nie zmienia po
         *   utworzeniu, WIELE watkow moze go czytac RowNOCZESNIE bez
         *   zadnej synchronizacji (`synchronized`, `Lock`), bo nie ma
         *   ryzyka race condition (`_05_multithreading/Lesson07_RaceCondition`).
         * - To NIE jest glowny temat tej lekcji (pelne bezpieczenstwo
         *   watkowe poznales w calym rozdziale `_05_multithreading`) -
         *   ale to WAZNY, "darmowy" efekt uboczny wyboru niezmiennosci w
         *   kodzie, ktory MOZE byc pozniej uzywany rownolegle.
         */
        System.out.println("\nObiekt niezmienny jest AUTOMATYCZNIE bezpieczny watkowo - bez synchronized/Lock (por. _05_multithreading).");

        /*
         * ============================================================
         * 📌 KOMPROMIS: KOSZT KOPII VS BEZPIECZENSTWO
         * ============================================================
         * - Niezmiennosc ma REALNY koszt: kazda "modyfikacja" tworzy NOWY
         *   obiekt (alokacja pamieci) zamiast zmieniac istniejacy "w
         *   miejscu" - dla bardzo czestych, drobnych zmian w petli o
         *   ogromnej liczbie iteracji, to MOZE miec znaczenie
         *   wydajnosciowe.
         * - Praktyczna zasada: DOMYSLNIE wybieraj niezmiennosc (koszt
         *   kopii jest zwykle pomijalny wobec korzysci w czytelnosci i
         *   bezpieczenstwie) - siegaj po mutowalnosc SWIADOMIE, gdy
         *   profilowanie (patrz `_15_jvm_internals`) wykaze REALNY
         *   problem wydajnosciowy, nie "na wszelki wypadek" z gory.
         */
        System.out.println("\nDomyslnie niezmiennosc - mutowalnosc TYLKO gdy realny, zmierzony powod wydajnosciowy.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Niezmiennosc eliminuje CALA kategorie bledow "aliasingu"
         *   (nieoczekiwana zmiana obiektu widoczna gdzie indziej).
         * - Male, niezmienne typy wartosciowe naprawiaja Primitive
         *   Obsession (Lesson14).
         * - Wzorzec "wither" - modyfikacja przez zwrocenie nowej kopii.
         * - Builder - bezpieczne konstruowanie zlozonych niezmiennych
         *   obiektow krok po kroku.
         * - Niezmienne kolekcje jako DOMYSLNY wybor w publicznym API.
         * - "Darmowe" bezpieczenstwo watkowe jako efekt uboczny.
         * - Kompromis: koszt kopii vs bezpieczenstwo - domyslnie
         *   niezmiennosc, mutowalnosc tylko ze zmierzonego powodu.
         * - W kolejnej lekcji (Lesson20): narzedzia statycznej analizy
         *   (PMD/SpotBugs) - jak AUTOMATYCZNIE wykrywac wiele smelli i
         *   naruszen zasad z calego tego rozdzialu (wlacznie z
         *   niezmiennoscia).
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    private static void demonstrateAliasingBugWithMutableObject() {
        System.out.println("\n=== BLAD ALIASINGU: OBIEKT MUTOWALNY ZMIENIONY 'GDZIE INDZIEJ' ===");

        MutablePoint original = new MutablePoint(10, 20);
        MutablePoint reference = original; // 2 zmienne wskazuja na TEN SAM obiekt

        reference.x = 999; // zmiana przez 'reference' jest WIDOCZNA rowniez przez 'original'!
        System.out.println("[MUTOWALNY] original.x po zmianie PRZEZ 'reference': " + original.x
                + " (niespodzianka - nikt 'wprost' nie zmienil 'original')");

        ImmutablePoint immutableOriginal = new ImmutablePoint(10, 20);
        ImmutablePoint immutableReference = immutableOriginal;
        // Brak setterow - NIE MA SPOSOBU, by 'immutableReference' zmienil stan 'immutableOriginal'.
        System.out.println("[NIEZMIENNY] immutableOriginal.x (gwarantowane, ze sie nie zmienilo): " + immutableOriginal.x);
    }

    static class MutablePoint {
        int x;
        int y;

        MutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static final class ImmutablePoint {
        final int x;
        final int y;

        ImmutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void demonstrateImmutableValueObjectFixingPrimitiveObsession() {
        System.out.println("\n=== NIEZMIENNY TYP WARTOSCIOWY ZAMIAST 'GOLEGO' STRINGA ===");

        try {
            new Email("zly-adres-bez-malpy");
        } catch (IllegalArgumentException e) {
            System.out.println("[NIEPOPRAWNY] Email odrzucony JUZ w konstruktorze: " + e.getMessage());
        }

        Email email = new Email("ala@example.com");
        System.out.println("[POPRAWNY] Email raz zwalidowany, ZAWSZE poprawny (nie da sie go 'zmutowac' na zly): "
                + email.value());
    }

    /** Maly, niezmienny typ wartosciowy - waliduje SIEBIE RAZ w konstruktorze (naprawia Primitive Obsession, Lesson14). */
    record Email(String value) {
        Email {
            if (value == null || !value.contains("@") || !value.contains(".")) {
                throw new IllegalArgumentException("Niepoprawny adres e-mail: " + value);
            }
        }
    }

    private static void demonstrateWitherPattern() {
        System.out.println("\n=== WZORZEC 'WITHER' - MODYFIKACJA PRZEZ NOWA KOPIE ===");

        Money original = new Money(100.0, "PLN");
        Money discounted = original.withAmount(90.0);

        System.out.println("original (NIENARUSZONY): " + original);
        System.out.println("discounted (NOWY obiekt): " + discounted);
        System.out.println("original == discounted? " + (original == discounted) + " (2 rozne obiekty, jak oczekiwano)");
    }

    record Money(double amount, String currency) {
        Money withAmount(double newAmount) {
            return new Money(newAmount, this.currency); // NOWY obiekt - oryginal pozostaje bez zmian
        }
    }

    private static void demonstrateBuilderForComplexImmutableObject() {
        System.out.println("\n=== BUILDER: KROK PO KROKU DO NIEZMIENNEGO OBIEKTU ===");

        Reservation reservation = new ReservationBuilder()
                .withGuestName("Ala Kowalska")
                .withRoomNumber(101)
                .withNights(3)
                .withBreakfastIncluded(true)
                .build();

        System.out.println("Zbudowana (niezmienna) rezerwacja: " + reservation);
    }

    /** Finalny obiekt - CALKOWICIE niezmienny, tworzony TYLKO przez ReservationBuilder.build(). */
    record Reservation(String guestName, int roomNumber, int nights, boolean breakfastIncluded) {
    }

    /** Builder - MUTOWALNY pomocnik, zbiera dane krok po kroku PRZED utworzeniem niezmiennego produktu. */
    static class ReservationBuilder {
        private String guestName;
        private int roomNumber;
        private int nights;
        private boolean breakfastIncluded;

        ReservationBuilder withGuestName(String guestName) {
            this.guestName = guestName;
            return this;
        }

        ReservationBuilder withRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        ReservationBuilder withNights(int nights) {
            this.nights = nights;
            return this;
        }

        ReservationBuilder withBreakfastIncluded(boolean breakfastIncluded) {
            this.breakfastIncluded = breakfastIncluded;
            return this;
        }

        Reservation build() {
            return new Reservation(guestName, roomNumber, nights, breakfastIncluded);
        }
    }

    private static void demonstrateImmutableCollectionAsDefaultApiChoice() {
        System.out.println("\n=== NIEZMIENNA KOLEKCJA JAKO DOMYSLNY WYBOR W API ===");

        TagHolder holder = new TagHolder(List.of("java", "clean-code"));
        List<String> tags = holder.getTags();

        try {
            tags.add("hacked-tag"); // proba modyfikacji zwroconej listy
        } catch (UnsupportedOperationException e) {
            System.out.println("[DOBRA] Proba modyfikacji listy zwroconej z API zakonczyla sie wyjatkiem: "
                    + e.getClass().getSimpleName());
            System.out.println("        Wewnetrzny stan TagHolder pozostaje NIENARUSZONY: " + holder.getTags());
        }
    }

    static class TagHolder {
        private final List<String> tags;

        TagHolder(List<String> tags) {
            this.tags = List.copyOf(tags); // obronna kopia jako NIEMODYFIKOWALNA lista
        }

        List<String> getTags() {
            return tags; // bezpieczne - 'tags' jest JUZ niemodyfikowalne, nie trzeba kopiowac przy KAZDYM wywolaniu
        }
    }
}
