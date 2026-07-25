package com.example.javaquest._31_spring_cloud_microservices.Lesson14_SagaPatternIntro;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class _Lesson14_SagaPatternIntro {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: Saga Pattern - transakcje rozproszone ===");

        /*
         * ============================================================
         * 📦 PROBLEM: BRAK transakcji ACID MIEDZY mikroserwisami
         * ============================================================
         * W monolicie transakcja bazodanowa (`_10_dao/Lesson19_
         * UnitOfWork`) GWARANTUJE: WSZYSTKIE operacje SIE UDAJA, ALBO
         * ZADNA (atomowosc). W mikroserwisach KAZDY serwis MA WLASNA
         * baze danych (Database per Service) - NIE MA JEDNEJ, wspolnej
         * transakcji obejmujacej WIELE serwisow (rozproszone
         * transakcje 2PC sa W PRAKTYCE ZBYT KOSZTOWNE/blokujace).
         *
         * Saga TO SEKWENCJA lokalnych transakcji (PO JEDNEJ NA
         * serwis) - jesli KTORYS krok ZAWIEDZIE, saga URUCHAMIA
         * "transakcje kompensujace" (compensating transactions),
         * KTORE COFAJA skutki JUZ WYKONANYCH krokow - eventual
         * consistency ZAMIAST natychmiastowej atomowosci.
         */
        System.out.println("Saga = sekwencja LOKALNYCH transakcji + kompensacje PRZY awarii - eventual consistency ZAMIAST 2PC.");

        demonstrateOrchestrationSuccess();
        demonstrateOrchestrationWithCompensation();
        demonstrateChoreographyConcept();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Orkiestracja (orchestration): CENTRALNY "dyrygent" (saga
         *   orchestrator) JAWNIE woła kolejne kroki I decyduje O
         *   kompensacji - LATWIEJSZE DO SLEDZENIA, ALE dyrygent TO
         *   dodatkowy komponent.
         * - Choreografia (choreography): KAZDY serwis SLUCHA zdarzen
         *   POPRZEDNIEGO I publikuje WLASNE (powiazanie Z
         *   `_17_architecture/Lesson18_EventDrivenCommunicationBetweenModules`) -
         *   BRAK centralnego punktu, ALE TRUDNIEJ prosledzic CALY
         *   przeplyw.
         * - Kompensacja MUSI byc IDEMPOTENTNA I NIEZAWODNA - Lesson15
         *   pokaze REALNA implementacje event-driven PRZEZ Spring
         *   Cloud Stream.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    interface Krok {
        void wykonaj();

        void kompensuj();
    }

    static class SagaOrchestrator {
        private final Deque<Krok> wykonaneKroki = new ArrayDeque<>();

        void uruchom(Krok... kroki) {
            for (Krok krok : kroki) {
                try {
                    krok.wykonaj();
                    wykonaneKroki.push(krok);
                } catch (RuntimeException e) {
                    System.out.println("  [orchestrator] Krok ZAWIODL: " + e.getMessage() + " -> URUCHAMIAM kompensacje.");
                    kompensujWszystko();
                    return;
                }
            }
            System.out.println("  [orchestrator] Saga ZAKONCZONA SUKCESEM - WSZYSTKIE kroki wykonane.");
        }

        private void kompensujWszystko() {
            while (!wykonaneKroki.isEmpty()) {
                Krok krok = wykonaneKroki.pop();
                krok.kompensuj();
            }
            System.out.println("  [orchestrator] Kompensacja ZAKONCZONA - system WROCIL DO spojnego stanu.");
        }
    }

    private static Krok prostyKrok(String nazwa, Supplier<Boolean> akcja) {
        return new Krok() {
            @Override
            public void wykonaj() {
                System.out.println("  [" + nazwa + "] wykonuje krok...");
                if (!akcja.get()) {
                    throw new RuntimeException(nazwa + " NIE POWIODL SIE");
                }
                System.out.println("  [" + nazwa + "] krok UDANY.");
            }

            @Override
            public void kompensuj() {
                System.out.println("  [" + nazwa + "] KOMPENSACJA - cofam skutki tego kroku.");
            }
        };
    }

    private static void demonstrateOrchestrationSuccess() {
        System.out.println("\n--- Orkiestracja: WSZYSTKIE kroki SIE UDAJA ---");
        SagaOrchestrator saga = new SagaOrchestrator();
        saga.uruchom(
                prostyKrok("Zarezerwuj-magazyn", () -> true),
                prostyKrok("Pobierz-platnosc", () -> true),
                prostyKrok("Wyslij-zamowienie", () -> true));
    }

    private static void demonstrateOrchestrationWithCompensation() {
        System.out.println("\n--- Orkiestracja: 3-ci krok ZAWODZI -> KOMPENSACJA 2 poprzednich ---");
        SagaOrchestrator saga = new SagaOrchestrator();
        saga.uruchom(
                prostyKrok("Zarezerwuj-magazyn", () -> true),
                prostyKrok("Pobierz-platnosc", () -> true),
                prostyKrok("Wyslij-zamowienie", () -> false)); // magazynier BRAK kuriera - awaria
    }

    private static void demonstrateChoreographyConcept() {
        System.out.println("\n--- Choreografia (koncepcyjnie - BEZ centralnego orchestratora) ---");

        // Symulacja: KAZDY "serwis" SUBSKRYBUJE zdarzenie POPRZEDNIKA I publikuje WLASNE -
        // dokladnie TA SAMA idea CO `_17_architecture/Lesson18`, TERAZ MIEDZY ODDZIELNYMI
        // "serwisami" (w tym demo dalej W JEDNYM procesie, DLA prostoty).
        Consumer<String> zamowieniaService = zdarzenie -> System.out.println("  [orders] Otrzymano '" + zdarzenie + "' -> publikuje 'ZamowienieUtworzone'.");
        Consumer<String> magazynService = zdarzenie -> System.out.println("  [inventory] Otrzymano '" + zdarzenie + "' -> rezerwuje towar, publikuje 'TowarZarezerwowany'.");
        Consumer<String> platnosciService = zdarzenie -> System.out.println("  [payments] Otrzymano '" + zdarzenie + "' -> pobiera platnosc, publikuje 'PlatnoscPobrana'.");

        zamowieniaService.accept("ZlozZamowienie");
        magazynService.accept("ZamowienieUtworzone");
        platnosciService.accept("TowarZarezerwowany");

        System.out.println("Zauwaz: ZADEN 'serwis' NIE ZNA CALEGO przeplywu - kazdy TYLKO REAGUJE NA zdarzenie POPRZEDNIKA.");
    }
}
