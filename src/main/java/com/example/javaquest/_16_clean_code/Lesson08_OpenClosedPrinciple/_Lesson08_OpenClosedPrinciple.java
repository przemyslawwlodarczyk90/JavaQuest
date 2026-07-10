package com.example.javaquest._16_clean_code.Lesson08_OpenClosedPrinciple;

import java.util.ArrayList;
import java.util.List;

public class _Lesson08_OpenClosedPrinciple {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: OPEN/CLOSED PRINCIPLE (OCP) ===");

        /*
         * ============================================================
         * 🔹 DEFINICJA OCP
         * ============================================================
         * - Sformulowanie Bertranda Meyera: "Byty programistyczne (klasy,
         *   moduly, funkcje) powinny byc OTWARTE NA ROZSZERZENIE, ale
         *   ZAMKNIETE NA MODYFIKACJE" (ang. "open for extension, closed for
         *   modification").
         * - W praktyce: gdy pojawia sie NOWY przypadek/wariant zachowania,
         *   powinienes miec mozliwosc DODANIA nowego kodu (nowej klasy),
         *   zamiast EDYTOWANIA istniejacego, juz dzialajacego i przetestowanego
         *   kodu.
         * - Dlaczego to wazne: kazda edycja dzialajacego kodu niesie ryzyko
         *   zepsucia czegos, co juz dzialalo (regresja). Dodanie NOWEGO kodu
         *   obok - bez ruszania starego - jest bezpieczniejsze.
         */
        System.out.println("OCP: kod otwarty na ROZSZERZENIE (dodawanie nowego), zamkniety na MODYFIKACJE (edycje istniejacego).");

        /*
         * ============================================================
         * 🔹 PRZYKLAD NARUSZENIA OCP: GIGANTYCZNY if/else PO TYPIE
         * ============================================================
         * - BadDiscountCalculator liczy rabat na podstawie typu klienta
         *   (String: "REGULAR", "PREMIUM", "VIP") uzywajac if/else if.
         * - Kazdy NOWY typ klienta (np. "STUDENT") wymaga EDYCJI tej samej
         *   metody - dopisania kolejnego "else if".
         * - Ryzyko: edytujac metode dla nowego przypadku, latwo przypadkiem
         *   zepsuc logike dla istniejacych przypadkow (np. zle miejsce
         *   wstawienia warunku, literowka w porownaniu Stringow).
         */
        demonstrateBadExample();

        /*
         * ============================================================
         * 🔍 DLACZEGO TO PROBLEM W PRAKTYCE
         * ============================================================
         * - Metoda rosnie w nieskonczonosc wraz z liczba wariantow -
         *   po latach moze miec dziesiatki galezi if/else (tzw. "arrow code").
         * - Kazda zmiana wymaga ponownego przetestowania CALEJ metody (bo
         *   zmiana w 1 galezi moze wplynac na inne przez literowke/typo).
         * - Logika jednego "typu klienta" jest rozproszona pomiedzy wieloma
         *   metodami, jesli podobny if/else po typie powtarza sie gdzie
         *   indziej (np. w metodzie liczacej limit kredytowy) - dodanie
         *   nowego typu wymaga pamietania o edycji WSZYSTKICH takich miejsc.
         * - Narusza tez SRP (Lesson07): jedna metoda/klasa "zna" logike
         *   rabatu dla WSZYSTKICH typow klienta naraz.
         */
        System.out.println("\nProblem: kazdy nowy typ klienta = edycja istniejacej metody = ryzyko regresji + rosnacy if/else.");

        /*
         * ============================================================
         * 📌 REFAKTORYZACJA: POLIMORFIZM ZAMIAST if/else PO TYPIE
         * ============================================================
         * - Wprowadzamy interfejs DiscountPolicy z metoda calculateDiscount().
         * - Kazdy typ klienta dostaje WLASNA klase implementujaca ten
         *   interfejs: RegularDiscountPolicy, PremiumDiscountPolicy,
         *   VipDiscountPolicy.
         * - PriceCalculator NIE zna konkretnych typow - operuje TYLKO na
         *   interfejsie DiscountPolicy (polimorfizm - mechanika poznana w
         *   _02_oop/Lesson08_Interfaces, tu uzyta do realizacji zasady
         *   projektowej).
         * - Dodanie NOWEGO typu klienta = napisanie NOWEJ klasy
         *   implementujacej DiscountPolicy - ZERO zmian w PriceCalculator
         *   ani w istniejacych klasach polityk rabatowych.
         */
        demonstrateGoodExample();

        /*
         * ============================================================
         * 🔍 DOWOD: DODANIE NOWEGO PRZYPADKU BEZ EDYCJI ISTNIEJACEGO KODU
         * ============================================================
         * - StudentDiscountPolicy ponizej to CALKOWICIE NOWA klasa, dodana
         *   PO fakcie - PriceCalculator, RegularDiscountPolicy,
         *   PremiumDiscountPolicy i VipDiscountPolicy NIE zostaly ani razu
         *   zmodyfikowane, zeby ja obsluzyc.
         * - To jest sedno OCP: system "rozszerza sie" przez DODAWANIE, a nie
         *   "modyfikuje" przez EDYCJE.
         */
        demonstrateAddingNewPolicyWithoutModifyingExistingCode();

        /*
         * ============================================================
         * 🔍 ZWIAZEK Z WZORCEM STRATEGY I INNYMI ZASADAMI
         * ============================================================
         * - Rozwiazanie z tej lekcji to klasyczny wzorzec projektowy Strategy
         *   (rodzina zamiennych algorytmow ukrytych za wspolnym interfejsem).
         * - OCP czesto idzie w parze z DIP (Lesson11) - PriceCalculator
         *   zalezy od ABSTRAKCJI (DiscountPolicy), nie od konkretnych klas -
         *   to wlasnie umozliwia bezpieczne rozszerzanie.
         * - OCP NIE oznacza "przewiduj wszystko z gory" - stosuje sie go
         *   TAM, gdzie w praktyce widac powtarzajacy sie wzorzec zmian
         *   (np. czesto dochodzace nowe typy klienta). Nadmierne "uodpornianie
         *   na przyszlosc" (over-engineering) miejsc, ktore nigdy sie nie
         *   zmieniaja, to strata czasu.
         */
        System.out.println("\nRozwiazanie = wzorzec Strategy; OCP stosuj tam, gdzie realnie czesto dochodza nowe warianty.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - OCP: dodawaj nowe zachowania przez NOWY kod (nowe klasy), nie
         *   przez edycje istniejacego, dzialajacego kodu.
         * - Sygnal ostrzegawczy: if/else lub switch rozgalezione po TYPIE
         *   obiektu/stringu, ktore trzeba edytowac przy kazdym nowym
         *   przypadku.
         * - Lekarstwo: polimorfizm - wspolny interfejs + osobna klasa na
         *   kazdy wariant.
         * - W kolejnej lekcji (Lesson09): Liskov Substitution Principle -
         *   kiedy podtyp (jak Twoje nowe klasy polityk rabatowych) NAPRAWDE
         *   moze bezpiecznie zastapic typ bazowy.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateBadExample() {
        System.out.println("\n=== ZLY PRZYKLAD: BadDiscountCalculator (if/else po typie String) ===");

        BadDiscountCalculator calculator = new BadDiscountCalculator();
        double price = 1000.0;

        for (String customerType : List.of("REGULAR", "PREMIUM", "VIP")) {
            double discount = calculator.calculateDiscount(customerType, price);
            System.out.println(" Typ: " + customerType + " -> rabat = " + discount + " zl (cena bazowa " + price + " zl)");
        }
        System.out.println("Dodanie np. \"STUDENT\" wymagaloby edycji metody calculateDiscount() powyzej.");
    }

    private static void demonstrateGoodExample() {
        System.out.println("\n=== DOBRY PRZYKLAD: DiscountPolicy (interfejs) + implementacje ===");

        double price = 1000.0;
        List<DiscountPolicy> policies = List.of(
                new RegularDiscountPolicy(),
                new PremiumDiscountPolicy(),
                new VipDiscountPolicy()
        );

        PriceCalculator calculator = new PriceCalculator();
        for (DiscountPolicy policy : policies) {
            double discount = calculator.calculateDiscount(policy, price);
            System.out.println(" " + policy.getClass().getSimpleName() + " -> rabat = " + discount + " zl");
        }
        System.out.println("PriceCalculator zna TYLKO interfejs DiscountPolicy - nie zna zadnej konkretnej implementacji.");
    }

    private static void demonstrateAddingNewPolicyWithoutModifyingExistingCode() {
        System.out.println("\n=== ROZSZERZENIE: NOWY TYP KLIENTA BEZ EDYCJI ISTNIEJACEGO KODU ===");

        // StudentDiscountPolicy to NOWA klasa - PriceCalculator ani inne
        // polityki nie zostaly przy tym zmienione ani o linie.
        DiscountPolicy newPolicy = new StudentDiscountPolicy();
        PriceCalculator calculator = new PriceCalculator();

        double discount = calculator.calculateDiscount(newPolicy, 1000.0);
        System.out.println(" StudentDiscountPolicy (NOWA klasa) -> rabat = " + discount + " zl");
        System.out.println("Zero zmian w PriceCalculator/RegularDiscountPolicy/PremiumDiscountPolicy/VipDiscountPolicy.");
    }

    /**
     * ZLY PRZYKLAD - kazdy nowy typ klienta wymaga edycji tej metody.
     * Narusza OCP (zamkniete na modyfikacje - a tu trzeba modyfikowac).
     */
    static class BadDiscountCalculator {
        double calculateDiscount(String customerType, double price) {
            if ("REGULAR".equals(customerType)) {
                return price * 0.0; // brak rabatu
            } else if ("PREMIUM".equals(customerType)) {
                return price * 0.10; // 10% rabatu
            } else if ("VIP".equals(customerType)) {
                return price * 0.20; // 20% rabatu
            } else {
                throw new IllegalArgumentException("Nieznany typ klienta: " + customerType);
            }
            // Dodanie "STUDENT" = kolejny "else if" TUTAJ = edycja tej metody.
        }
    }

    /**
     * DOBRY PRZYKLAD - wspolny interfejs dla wszystkich polityk rabatowych.
     * Nowy wariant = nowa klasa implementujaca ten interfejs.
     */
    interface DiscountPolicy {
        double calculateDiscount(double price);
    }

    static class RegularDiscountPolicy implements DiscountPolicy {
        @Override
        public double calculateDiscount(double price) {
            return price * 0.0;
        }
    }

    static class PremiumDiscountPolicy implements DiscountPolicy {
        @Override
        public double calculateDiscount(double price) {
            return price * 0.10;
        }
    }

    static class VipDiscountPolicy implements DiscountPolicy {
        @Override
        public double calculateDiscount(double price) {
            return price * 0.20;
        }
    }

    /**
     * NOWY wariant dodany PO NAPISANIU PriceCalculator - dowod, ze system
     * jest "otwarty na rozszerzenie" bez zadnej edycji istniejacego kodu.
     */
    static class StudentDiscountPolicy implements DiscountPolicy {
        @Override
        public double calculateDiscount(double price) {
            return price * 0.15;
        }
    }

    /**
     * DOBRY PRZYKLAD - zalezy TYLKO od abstrakcji (DiscountPolicy), nigdy
     * nie musi byc zmieniana, gdy dochodzi nowy typ klienta.
     */
    static class PriceCalculator {
        double calculateDiscount(DiscountPolicy policy, double price) {
            return policy.calculateDiscount(price);
        }

        double calculateFinalPrice(DiscountPolicy policy, double price) {
            return price - calculateDiscount(policy, price);
        }
    }
}
