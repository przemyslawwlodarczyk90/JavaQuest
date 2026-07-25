package com.example.javaquest._31_spring_cloud_microservices.Lesson09_CircuitBreakerConcepts;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

public class _Lesson09_CircuitBreakerConcepts {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: Circuit Breaker - koncepcje (closed/open/half-open) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: kaskadowa awaria (cascading failure)
         * ============================================================
         * Serwis A wola serwis B. Serwis B zaczyna byc WOLNY/PADA.
         * BEZ zabezpieczenia, serwis A CZEKA NA KAZDE wywolanie DO
         * TIMEOUTU - jego WLASNE watki/polaczenia SIE WYCZERPUJA,
         * SAM staje sie wolny/niedostepny - AWARIA "ROZLEWA SIE"
         * NA KOLEJNE serwisy, KTORE WOLAJA serwis A. Circuit breaker
         * (wzorzec Z ksiazki Michaela Nygarda "Release It!", 2007)
         * ZATRZYMUJE to szybko: PO PRZEKROCZENIU progu bledow,
         * PRZESTAJE W OGOLE PROBOWAC wolac B (zamiast czekac NA
         * timeout PRZY KAZDYM zadaniu) - zwraca blad NATYCHMIAST.
         */
        System.out.println("Circuit breaker CHRONI przed kaskadowa awaria - gdy serwis B pada, A PRZESTAJE go wolac (zamiast czekac NA KAZDY timeout).");

        demonstrateThreeStates();
        demonstrateStateMachine();
        demonstrateWhyHalfOpenMatters();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CLOSED (domyslny) - wywolania PRZECHODZA normalnie,
         *   breaker LICZY bledy.
         * - OPEN - PO przekroczeniu progu bledow, WYWOLANIA SA
         *   NATYCHMIAST odrzucane (BEZ prawdziwego wywolania) PRZEZ
         *   okreslony czas (wait duration).
         * - HALF-OPEN - PO uplywie czasu, breaker PRZEPUSZCZA proba
         *   liczbe zadan TESTOWYCH - jesli sie UDADZA, WRACA DO CLOSED;
         *   jesli NIE, WRACA DO OPEN.
         * - Lesson10 pokaze PRAWDZIWA implementacje (Resilience4j +
         *   Spring Cloud Circuit Breaker abstrakcja).
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateThreeStates() {
        System.out.println("\n--- Trzy stany circuit breakera ---");
        System.out.println("CLOSED (zamkniety) - normalna praca, wywolania PRZECHODZA, breaker liczy sukcesy/porazki.");
        System.out.println("OPEN (otwarty) - PRZEKROCZONY prog bledow - wywolania NATYCHMIAST odrzucane, BEZ prawdziwej proby.");
        System.out.println("HALF-OPEN (polotwarty) - PO czasie oczekiwania, OGRANICZONA liczba zadan TESTOWYCH przechodzi.");
    }

    enum Stan { CLOSED, OPEN, HALF_OPEN }

    static class ProstyCircuitBreaker {
        private final int progBledowProcent;
        private final int oknoProbek;
        private final Deque<Boolean> historia = new ArrayDeque<>();
        private Stan stan = Stan.CLOSED;

        ProstyCircuitBreaker(int progBledowProcent, int oknoProbek) {
            this.progBledowProcent = progBledowProcent;
            this.oknoProbek = oknoProbek;
        }

        <T> T wykonaj(Supplier<T> wywolanie, Supplier<T> fallback) {
            if (stan == Stan.OPEN) {
                System.out.println("  [breaker OPEN] Zadanie ODRZUCONE NATYCHMIAST (BEZ prawdziwego wywolania).");
                return fallback.get();
            }
            try {
                T wynik = wywolanie.get();
                zarejestruj(true);
                if (stan == Stan.HALF_OPEN) {
                    System.out.println("  [breaker HALF_OPEN] Zadanie TESTOWE udane -> powrot DO CLOSED.");
                    stan = Stan.CLOSED;
                    historia.clear();
                }
                return wynik;
            } catch (RuntimeException e) {
                zarejestruj(false);
                if (stan == Stan.HALF_OPEN) {
                    System.out.println("  [breaker HALF_OPEN] Zadanie TESTOWE NIEUDANE -> powrot DO OPEN.");
                    stan = Stan.OPEN;
                }
                return fallback.get();
            }
        }

        private void zarejestruj(boolean sukces) {
            historia.addLast(sukces);
            if (historia.size() > oknoProbek) {
                historia.removeFirst();
            }
            if (stan == Stan.CLOSED && historia.size() >= oknoProbek) {
                long porazki = historia.stream().filter(s -> !s).count();
                int procentBledow = (int) (porazki * 100 / historia.size());
                if (procentBledow >= progBledowProcent) {
                    System.out.println("  [breaker] Prog bledow (" + procentBledow + "% >= " + progBledowProcent + "%) PRZEKROCZONY -> OPEN.");
                    stan = Stan.OPEN;
                }
            }
        }

        void probaHalfOpen() {
            if (stan == Stan.OPEN) {
                System.out.println("  [breaker] Uplynal czas oczekiwania -> HALF_OPEN (proba testowa).");
                stan = Stan.HALF_OPEN;
            }
        }

        Stan getStan() {
            return stan;
        }
    }

    private static void demonstrateStateMachine() {
        System.out.println("\n--- Symulacja maszyny stanow (prog: 50% bledow W oknie 4 probek) ---");

        ProstyCircuitBreaker breaker = new ProstyCircuitBreaker(50, 4);
        boolean[] wynikiWywolan = {true, false, false, false}; // 3/4 porazek -> 75% >= 50% -> OPEN
        int i = 0;
        for (boolean sukces : wynikiWywolan) {
            i++;
            int indeks = i;
            String wynik = breaker.wykonaj(
                    () -> {
                        if (!sukces) {
                            throw new RuntimeException("Serwis B nie odpowiada (wywolanie " + indeks + ")");
                        }
                        return "OK (wywolanie " + indeks + ")";
                    },
                    () -> "FALLBACK (wywolanie " + indeks + ")");
            System.out.println("Wywolanie " + i + " -> " + wynik + " | stan breakera: " + breaker.getStan());
        }

        System.out.println("\nKolejne wywolanie PRZY stanie OPEN:");
        String wynikOpen = breaker.wykonaj(() -> "NIGDY nie zostanie wywolane", () -> "FALLBACK (breaker OPEN)");
        System.out.println("-> " + wynikOpen + " | stan: " + breaker.getStan());

        System.out.println("\nSymulacja uplywu czasu oczekiwania -> HALF_OPEN:");
        breaker.probaHalfOpen();
        String wynikTestowy = breaker.wykonaj(() -> "Serwis B ZNOWU DZIALA", () -> "FALLBACK (test nieudany)");
        System.out.println("Zadanie testowe -> " + wynikTestowy + " | stan koncowy: " + breaker.getStan());
    }

    private static void demonstrateWhyHalfOpenMatters() {
        System.out.println("\n--- Dlaczego HALF_OPEN jest potrzebny (nie tylko CLOSED/OPEN) ---");
        System.out.println("BEZ HALF_OPEN: breaker musialby ALBO wrocic DO CLOSED "
                + "NA SLEPO (ryzyko ZALANIA jeszcze niegotowego serwisu B PELNYM ruchem),");
        System.out.println("ALBO zostac W OPEN NA ZAWSZE (serwis B NIGDY nie dostanie SZANSY udowodnic, ze JUZ DZIALA).");
        System.out.println("HALF_OPEN to KOMPROMIS: OGRANICZONA liczba zadan TESTOWYCH weryfikuje stan PRZED PELNYM otwarciem ruchu.");
    }
}
