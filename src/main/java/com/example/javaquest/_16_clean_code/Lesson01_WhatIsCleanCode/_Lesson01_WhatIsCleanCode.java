package com.example.javaquest._16_clean_code.Lesson01_WhatIsCleanCode;

import java.util.List;

public class _Lesson01_WhatIsCleanCode {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: CZYM JEST CLEAN CODE ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL: _16_clean_code - CLEAN CODE I REFACTORING
         * ============================================================
         * - Ten rozdzial zaklada, ze znasz juz OOP (_02_oop) i podstawy Javy -
         *   tu NIE uczymy skladni, tylko JAK PISAC DOBRY KOD w tej skladni.
         * - Mapa drogowa calego rozdzialu _16_clean_code (22 lekcje):
         *     1-6.  Podstawy czytelnosci - czym jest clean code, nazewnictwo,
         *           komentarze, male funkcje, formatowanie, male klasy.
         *     7-11. Zasady SOLID - Single Responsibility, Open/Closed, Liskov
         *           Substitution, Interface Segregation, Dependency Inversion.
         *     12.   Coupling, cohesion i Prawo Demeter.
         *     13-16. DRY/KISS/YAGNI, katalog code smelli, podstawy i katalog
         *           refaktoryzacji.
         *     17-19. Projektowanie wyjatkow, obsluga null, niezmiennosc w praktyce.
         *     20.   Narzedzia statycznej analizy (checkstyle, SpotBugs, SonarLint).
         *     21.   Praca z kodem legacy i dlug techniczny.
         *     22.   Code review, dobre praktyki zespolowe i projekt kapstonowy.
         * - Zaczynamy od pytania fundamentalnego: CZYM WLASCIWIE JEST "clean code"
         *   i dlaczego w ogole warto sie tym przejmowac, skoro kod "dziala"?
         */
        System.out.println("\nRozdzial _16_clean_code: nazewnictwo/komentarze/funkcje/formatowanie/klasy");
        System.out.println("-> SOLID -> coupling/cohesion -> DRY/KISS/refaktoryzacja -> wyjatki/null/");
        System.out.println("niezmiennosc -> statyczna analiza -> legacy code -> code review + capstone.");

        /*
         * ============================================================
         * 🔹 DEFINICJE: CO TO ZNACZY, ZE KOD JEST "CZYSTY"?
         * ============================================================
         * - Robert C. Martin ("Uncle Bob", ksiazka "Clean Code", 2008):
         *   czysty kod to taki, ktory "kazdy test przechodzi", "nie zawiera
         *   duplikacji", "wyraza intencje autora w sposob jasny" i "minimalizuje
         *   liczbe elementow" (klas, metod) potrzebnych do zrobienia swojej roboty.
         * - Martin Fowler (ksiazka "Refactoring"): kazdy glupiec potrafi napisac
         *   kod, ktory komputer zrozumie - dobrzy programisci pisza kod, ktory
         *   ZROZUMIE CZLOWIEK.
         * - Kluczowa obserwacja: kod jest CZYTANY dziesiatki razy czesciej niz
         *   PISANY. Piszesz go raz, ale Ty (za pol roku) i Twoi wspolpracownicy
         *   beda go czytac, debugowac i modyfikowac wielokrotnie przez lata.
         * - Wniosek: optymalizujemy kod pod kątem czytelnosci dla czlowieka,
         *   nie tylko pod katem "dziala i kompiluje sie".
         */
        printCleanCodeDefinitions();

        /*
         * ============================================================
         * 🔍 "BROKEN WINDOWS THEORY" I KOSZT ZLEGO KODU W CZASIE
         * ============================================================
         * - Teoria "wybitych szyb" (z kryminologii, przeniesiona do inzynierii
         *   oprogramowania): jedno niedbale miejsce w kodzie (brzydka nazwa,
         *   skopiowany fragment, "tymczasowy" hack) zacheca kolejnych
         *   programistow do dokladania kolejnego bałaganu obok - "skoro tu juz
         *   jest brudno, to jedno wiecej nie zaszkodzi".
         * - Efekt: jakosc kodu degraduje sie LAWINOWO, nie liniowo. Projekt,
         *   ktory na starcie byl szybki do zmiany, po roku "brudzenia" staje
         *   sie coraz wolniejszy - kazda nowa funkcja wymaga rozgryzienia
         *   coraz wiekszego bałaganu.
         * - Koszt zlego kodu NIE jest widoczny od razu - na starcie brzydki
         *   "quick fix" wydaje sie tanszy niz porzadne rozwiazanie. Ale koszt
         *   narasta w czasie: kazda kolejna zmiana w brudnym kodzie zajmuje
         *   wiecej czasu, generuje wiecej bugow, wymaga wiecej "archeologii"
         *   zeby zrozumiec, co kod w ogole robi.
         */
        printBrokenWindowsExplanation();

        /*
         * ============================================================
         * 📌 DLACZEGO TO SIE OPLACA BIZNESOWO
         * ============================================================
         * - Szybkosc dostarczania nowych funkcji w DLUGIM okresie jest wprost
         *   zalezna od jakosci kodu - czysty kod jest tanszy w utrzymaniu, bo:
         *     * nowi programisci szybciej go rozumieja (krotszy onboarding),
         *     * bugi latwiej znalezc (kod czytelny = latwiej zobaczyc blad),
         *     * zmiany sa lokalne (male, spojne klasy/metody = male ryzyko,
         *       ze zmiana w jednym miejscu zepsuje cos zupelnie innego).
         * - "Idziemy szybciej pisac brzydko" jest prawda TYLKO na bardzo
         *   krotka mete (pierwsze dni/tygodnie projektu). W perspektywie
         *   miesiecy/lat brudny kod ZWALNIA caly zespol - to jest wlasnie
         *   sedno "technical debt" (dlugu technicznego, temat Lekcji 21):
         *   dlug trzeba kiedys splacic, z odsetkami.
         * - Poznizej: dokladnie TA SAMA logika biznesowa (walidacja zamowienia)
         *   napisana najpierw jako "kod-bagno", a potem jako czysta wersja.
         *   Obie dzialaja identycznie - ale jedna jest do przeczytania w
         *   5 sekund, druga wymaga analizy krok po kroku.
         */
        System.out.println("\nPonizej: identyczna logika walidacji zamowienia w 2 wersjach - porownaj czytelnosc.");

        demonstrateMessyOrderValidation();
        demonstrateCleanOrderValidation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Clean code = kod czytelny dla czlowieka, wyrazajacy intencje,
         *   bez duplikacji, latwy w zmianie.
         * - Kod jest czytany czesciej niz pisany - inwestycja w czytelnosc
         *   zwraca sie wielokrotnie.
         * - Zly kod nie "kosztuje od razu" - koszt narasta w czasie (broken
         *   windows theory) i spowalnia caly zespol.
         * - W kolejnych lekcjach (2-6) rozlozymy "czystosc kodu" na konkretne,
         *   praktyczne techniki: nazewnictwo, komentarze, male funkcje,
         *   formatowanie i male, spojne klasy.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void printCleanCodeDefinitions() {
        System.out.println("\n=== DEFINICJE CLEAN CODE ===");
        List<String> definitions = List.of(
                "Robert C. Martin: kod bez duplikacji, wyrazajacy intencje, przechodzacy testy",
                "Martin Fowler: kod, ktory rozumie CZLOWIEK, nie tylko komputer",
                "Kod jest czytany czesciej niz pisany - optymalizuj pod czytelnosc"
        );
        definitions.forEach(d -> System.out.println(" - " + d));
    }

    private static void printBrokenWindowsExplanation() {
        System.out.println("\n=== BROKEN WINDOWS THEORY ===");
        System.out.println(" 1 brzydkie miejsce w kodzie -> zacheca do dokladania kolejnych");
        System.out.println(" Jakosc kodu degraduje sie lawinowo, nie liniowo");
        System.out.println(" Koszt zmiany w brudnym kodzie rosnie z kazdym miesiacem projektu");
    }

    /*
     * ZLY PRZYKLAD: ta sama walidacja zamowienia napisana jako "kod-bagno" -
     * jednoliterowe nazwy, brak struktury, glebokie zagniezdzenie if-ow,
     * jedna gigantyczna metoda robiaca wszystko naraz.
     */
    private static void demonstrateMessyOrderValidation() {
        System.out.println("\n=== ZLY PRZYKLAD: 'KOD-BAGNO' ===");

        String n = "Jan Kowalski";
        double a = 250.0;
        int q = 3;
        String s = "PL";

        boolean r;
        if (n != null) {
            if (!n.isEmpty()) {
                if (a > 0) {
                    if (q > 0) {
                        if (s != null) {
                            if (s.equals("PL") || s.equals("DE") || s.equals("FR")) {
                                if (a < 10000) {
                                    r = true;
                                } else {
                                    r = false;
                                }
                            } else {
                                r = false;
                            }
                        } else {
                            r = false;
                        }
                    } else {
                        r = false;
                    }
                } else {
                    r = false;
                }
            } else {
                r = false;
            }
        } else {
            r = false;
        }

        System.out.println(" wynik walidacji (n=" + n + ", a=" + a + ", q=" + q + ", s=" + s + "): " + r);
        System.out.println(" ^ zeby zrozumiec TEN kod trzeba przesledzic 6 poziomow zagniezdzenia if-ow");
    }

    /*
     * DOBRY PRZYKLAD: identyczna logika, ale nazwy mowia CO reprezentuja,
     * warunki sa "wypłaszczone" (early return zamiast zagniezdzania), a
     * kazdy warunek biznesowy ma wlasna, nazwana metode.
     */
    private static void demonstrateCleanOrderValidation() {
        System.out.println("\n=== DOBRY PRZYKLAD: CZYSTA WERSJA ===");

        boolean isValid = isOrderValid("Jan Kowalski", 250.0, 3, "PL");
        System.out.println(" wynik walidacji zamowienia Jana Kowalskiego: " + isValid);
        System.out.println(" ^ ta sama logika, ale czytelna w kilka sekund - kazdy warunek ma nazwe");
    }

    private static final double MAX_ORDER_AMOUNT = 10_000.0;
    private static final List<String> SUPPORTED_COUNTRY_CODES = List.of("PL", "DE", "FR");

    private static boolean isOrderValid(String customerName, double totalAmount, int itemCount, String countryCode) {
        if (!hasCustomerName(customerName)) {
            return false;
        }
        if (!hasPositiveAmount(totalAmount)) {
            return false;
        }
        if (!hasAtLeastOneItem(itemCount)) {
            return false;
        }
        if (!isShippingCountrySupported(countryCode)) {
            return false;
        }
        return !exceedsMaxOrderAmount(totalAmount);
    }

    private static boolean hasCustomerName(String customerName) {
        return customerName != null && !customerName.isEmpty();
    }

    private static boolean hasPositiveAmount(double totalAmount) {
        return totalAmount > 0;
    }

    private static boolean hasAtLeastOneItem(int itemCount) {
        return itemCount > 0;
    }

    private static boolean isShippingCountrySupported(String countryCode) {
        return countryCode != null && SUPPORTED_COUNTRY_CODES.contains(countryCode);
    }

    private static boolean exceedsMaxOrderAmount(double totalAmount) {
        return totalAmount >= MAX_ORDER_AMOUNT;
    }
}
