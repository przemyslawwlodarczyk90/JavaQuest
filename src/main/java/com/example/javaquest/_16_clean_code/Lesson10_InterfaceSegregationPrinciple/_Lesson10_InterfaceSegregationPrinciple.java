package com.example.javaquest._16_clean_code.Lesson10_InterfaceSegregationPrinciple;

import java.util.List;

public class _Lesson10_InterfaceSegregationPrinciple {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: INTERFACE SEGREGATION PRINCIPLE (ISP) ===");

        /*
         * ============================================================
         * 🔹 DEFINICJA ISP
         * ============================================================
         * - Sformulowanie Roberta C. Martina: "Klienci NIE powinni byc
         *   zmuszani do zaleznosci od metod, ktorych nie uzywaja" (ang.
         *   "clients should not be forced to depend on interfaces they do
         *   not use").
         * - W praktyce: WIELE MALYCH, wyspecjalizowanych interfejsow jest
         *   lepsze niz JEDEN duzy, "tlusty" interfejs (ang. "fat interface")
         *   zbierajacy wszystkie mozliwe metody.
         * - _02_oop/Lesson08_Interfaces nauczyl Cie SKLADNI interfejsow -
         *   ISP mowi, JAK je PROJEKTOWAC, zeby nie wymuszaly na klasach
         *   implementowania metod, ktore dla nich nie maja sensu.
         */
        System.out.println("ISP: wiele malych, wyspecjalizowanych interfejsow lepsze niz jeden 'tlusty' interfejs.");

        /*
         * ============================================================
         * 🔹 PRZYKLAD NARUSZENIA ISP: "TLUSTY" INTERFEJS Worker
         * ============================================================
         * - BadWorker definiuje 3 metody: work(), eat(), sleep() - wyglada
         *   sensownie dla CZLOWIEKA-pracownika (HumanWorker naturalnie robi
         *   wszystkie trzy).
         * - Problem pojawia sie, gdy chcemy dodac RobotWorker (robota
         *   pracujacego w fabryce) - robot PRACUJE, ale NIE JE i NIE SPI.
         *   Musi jednak IMPLEMENTOWAC eat() i sleep(), bo takie wymaga
         *   interfejs BadWorker.
         * - Skutek: RobotWorker.eat() i sleep() nie maja sensownej
         *   implementacji - typowe "rozwiazanie" to rzucenie wyjatku
         *   UnsupportedOperationException.
         */
        demonstrateBadExample();

        /*
         * ============================================================
         * 🔍 DLACZEGO TO PROBLEM W PRAKTYCE
         * ============================================================
         * - Kod klienta, ktory dostaje BadWorker i (zgodnie z kontraktem
         *   interfejsu) woluje worker.eat(), moze dostac RUNTIME wyjatek,
         *   jesli worker "pod spodem" to RobotWorker - kompilator TEGO NIE
         *   WYLAPIE (to sygnaturalnie poprawne wywolanie).
         * - To jest w istocie ZAMASKOWANE naruszenie LSP (Lesson09) -
         *   RobotWorker "podszywa sie" pod BadWorker, ale nie spelnia
         *   pelnego kontraktu tego interfejsu.
         * - "Tlusty" interfejs zmusza KAZDA implementacje do dzwigania
         *   metod, ktorych czesc klientow (i czesc implementacji) w ogole
         *   nie potrzebuje - rosnie ryzyko takich "pustych"/rzucajacych
         *   wyjatek implementacji.
         */
        demonstrateThrowingExceptionProblem();

        /*
         * ============================================================
         * 📌 REFAKTORYZACJA: ROZBICIE NA MALE, WYSPECJALIZOWANE INTERFEJSY
         * ============================================================
         * - Zamiast 1 interfejsu Worker z 3 metodami, wprowadzamy 3 male
         *   interfejsy: Workable (work()), Eatable (eat()), Sleepable
         *   (sleep()).
         * - HumanWorker implementuje WSZYSTKIE TRZY (bo naprawde pracuje,
         *   je i spi).
         * - RobotWorker implementuje TYLKO Workable - bo tylko to ma dla
         *   niego sens. Nie musi udawac, ze "je" czy "spi".
         * - Kazdy klient zalezy TYLKO od interfejsu, ktorego faktycznie
         *   potrzebuje (np. metoda liczaca "godziny pracy" przyjmuje TYLKO
         *   Workable - dziala dla KAZDEGO pracownika, czlowieka czy robota).
         */
        demonstrateGoodExample();

        /*
         * ============================================================
         * 🔍 KORZYSCI Z SEGREGACJI INTERFEJSOW
         * ============================================================
         * - Brak "pustych"/rzucajacych wyjatek implementacji - kazda klasa
         *   implementuje TYLKO to, co ma sens.
         * - Bezpieczenstwo w compile time: RobotWorker w ogole NIE MA metody
         *   eat() - probujac ja wywolac dostaniemy BLAD KOMPILACJI, nie
         *   wyjatek w runtime (znacznie wczesniejsze i tansze wykrycie bledu).
         * - Male interfejsy = male, spojne "konteksty uzycia": kod
         *   operujacy na Eatable nie musi nic wiedziec o Workable/Sleepable.
         * - Zwiazek z SRP (Lesson07): kazdy maly interfejs ma TEZ jeden
         *   "powod do zmiany" (jedna, wazka odpowiedzialnosc).
         */
        System.out.println("\nKorzysc kluczowa: RobotWorker.eat() to teraz BLAD KOMPILACJI (metoda nie istnieje), nie wyjatek w runtime.");

        /*
         * ============================================================
         * 🔍 KIEDY STOSOWAC ISP - PRAKTYCZNA WSKAZOWKA
         * ============================================================
         * - Sygnal ostrzegawczy: implementacja interfejsu ma metode, ktora
         *   zostaje pusta, rzuca wyjatek, albo ma komentarz w stylu "nie
         *   dotyczy" / "not supported" / "TODO: nigdy nie wywolywac".
         * - Male interfejsy MOZNA laczyc: klasa moze `implements Workable,
         *   Eatable, Sleepable` jednoczesnie (jak HumanWorker) - segregacja
         *   nie oznacza "1 klasa = 1 interfejs", tylko "1 interfejs = 1
         *   spojny zestaw zachowan".
         * - To samo dotyczy interfejsow z wieloma metodami "per uzytkownik" -
         *   np. duzy interfejs `Repository` z metodami CRUD + eksport CSV +
         *   wysylka e-mail powinien byc rozbity analogicznie.
         */
        System.out.println("\nKlasa MOZE implementowac kilka malych interfejsow naraz - segregacja to nie 'jeden interfejs na klase'.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ISP: klient (i implementacja) nie powinny byc zmuszane do
         *   zaleznosci od metod, ktorych nie uzywaja/nie maja sensu.
         * - Sygnal ostrzegawczy: implementacja z pusta metoda albo
         *   throw new UnsupportedOperationException().
         * - Lekarstwo: rozbij duzy interfejs na male, spojne interfejsy -
         *   klasy implementuja TYLKO te, ktore maja sens.
         * - W kolejnej lekcji (Lesson11): Dependency Inversion Principle -
         *   jak moduly wysokopoziomowe powinny zalezec od abstrakcji
         *   (takich jak male interfejsy z tej lekcji), a nie od konkretnych
         *   implementacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void demonstrateBadExample() {
        System.out.println("\n=== ZLY PRZYKLAD: 'tlusty' interfejs BadWorker (work/eat/sleep) ===");

        BadWorker human = new HumanWorker();
        System.out.println("HumanWorker: " + human.work() + " / " + human.eat() + " / " + human.sleep());

        BadWorker robot = new RobotWorker();
        System.out.println("RobotWorker.work() = " + robot.work() + " (to ma sens dla robota)");
    }

    private static void demonstrateThrowingExceptionProblem() {
        System.out.println("\n=== PROBLEM: RobotWorker MUSI 'udawac', ze je i spi ===");

        BadWorker robot = new RobotWorker();
        try {
            robot.eat();
        } catch (UnsupportedOperationException e) {
            System.out.println(" robot.eat() rzucil wyjatek w RUNTIME: " + e.getMessage()
                    + " (kompilator NIC nie ostrzegl - sygnatura byla poprawna!)");
        }
    }

    private static void demonstrateGoodExample() {
        System.out.println("\n=== DOBRY PRZYKLAD: Workable / Eatable / Sleepable (male interfejsy) ===");

        GoodHumanWorker human = new GoodHumanWorker();
        System.out.println("GoodHumanWorker implementuje Workable+Eatable+Sleepable: "
                + human.work() + " / " + human.eat() + " / " + human.sleep());

        GoodRobotWorker robot = new GoodRobotWorker();
        System.out.println("GoodRobotWorker implementuje TYLKO Workable: " + robot.work());
        System.out.println("robot.eat() w ogole NIE ISTNIEJE - proba wywolania to blad KOMPILACJI, nie runtime.");

        // Kod klienta zalezny TYLKO od potrzebnego mu interfejsu:
        List<Workable> allWorkers = List.of(human, robot);
        for (Workable worker : allWorkers) {
            System.out.println(" Workable.work() -> " + worker.work());
        }
    }

    /**
     * ZLY PRZYKLAD - "tlusty" interfejs laczacy 3 rozne, niekoniecznie
     * powiazane zachowania. Narusza ISP.
     */
    interface BadWorker {
        String work();

        String eat();

        String sleep();
    }

    static class HumanWorker implements BadWorker {
        @Override
        public String work() {
            return "czlowiek pracuje";
        }

        @Override
        public String eat() {
            return "czlowiek je obiad";
        }

        @Override
        public String sleep() {
            return "czlowiek spi w nocy";
        }
    }

    /**
     * ZLY PRZYKLAD - robot MUSI zaimplementowac eat()/sleep(), mimo ze nie
     * maja one dla niego zadnego sensu. Typowe "rozwiazanie": rzucic
     * wyjatek w runtime.
     */
    static class RobotWorker implements BadWorker {
        @Override
        public String work() {
            return "robot pracuje bez przerwy";
        }

        @Override
        public String eat() {
            throw new UnsupportedOperationException("Roboty nie jedza!");
        }

        @Override
        public String sleep() {
            throw new UnsupportedOperationException("Roboty nie spia!");
        }
    }

    /**
     * DOBRY PRZYKLAD - male, wyspecjalizowane interfejsy zamiast jednego
     * "tlustego" interfejsu.
     */
    interface Workable {
        String work();
    }

    interface Eatable {
        String eat();
    }

    interface Sleepable {
        String sleep();
    }

    /**
     * Czlowiek implementuje WSZYSTKIE trzy male interfejsy - bo naprawde
     * pracuje, je i spi.
     */
    static class GoodHumanWorker implements Workable, Eatable, Sleepable {
        @Override
        public String work() {
            return "czlowiek pracuje";
        }

        @Override
        public String eat() {
            return "czlowiek je obiad";
        }

        @Override
        public String sleep() {
            return "czlowiek spi w nocy";
        }
    }

    /**
     * Robot implementuje TYLKO Workable - nie musi "udawac" jedzenia/snu.
     * Metody eat()/sleep() w ogole NIE ISTNIEJA dla tej klasy.
     */
    static class GoodRobotWorker implements Workable {
        @Override
        public String work() {
            return "robot pracuje bez przerwy";
        }
    }
}
