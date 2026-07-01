package com.example.javaquest._01_fundamentals.Lesson15_RandomAndSecureRandom;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class _Exercises_Lesson15_RandomAndSecureRandom {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_MathRandom {
        /*
         * 🧪 Zadanie 1:
         * Użyj Math.random() aby wygenerować losową liczbę od 0.0 do 1.0.
         * Wypisz 5 kolejnych wyników.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_MathRandomToInt {
        /*
         * 🧪 Zadanie 2:
         * Wygeneruj losową liczbę całkowitą od 1 do 10 używając Math.random().
         * Wzór: (int) (Math.random() * 10) + 1.
         * Wypisz 5 wyników.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_RandomNextInt {
        /*
         * 🧪 Zadanie 3:
         * Stwórz obiekt Random i wywołaj random.nextInt(100).
         * Wypisz 5 losowych liczb od 0 do 99.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_RandomNextDouble {
        /*
         * 🧪 Zadanie 4:
         * Stwórz Random i wygeneruj 3 losowe liczby double (.nextDouble()).
         * Wypisz każdą z dokładnością do 4 miejsc po przecinku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_RandomNextBoolean {
        /*
         * 🧪 Zadanie 5:
         * Stwórz Random i wygeneruj 10 losowych wartości boolean (.nextBoolean()).
         * Policz ile było true i ile false. Wypisz statystyki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_RandomInRange {
        /*
         * 🧪 Zadanie 6:
         * Wygeneruj 5 losowych liczb całkowitych z zakresu 50 do 100 (włącznie).
         * Wzór: low + random.nextInt(high - low + 1).
         * Wypisz każdą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_RandomFromList {
        /*
         * 🧪 Zadanie 7:
         * Stwórz List<String> names = List.of("Anna", "Bartek", "Celina", "Daniel", "Ewa").
         * Wylosuj losowe imię używając Random i wypisz je 3 razy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_CollectionsShuffle {
        /*
         * 🧪 Zadanie 8:
         * Stwórz List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).
         * Użyj Collections.shuffle(numbers) i wypisz po przetasowaniu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ThreadLocalRandom {
        /*
         * 🧪 Zadanie 9:
         * Użyj ThreadLocalRandom.current().nextInt(1, 11) aby wygenerować 5 liczb od 1 do 10.
         * Wypisz każdą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_SecureRandomBasic {
        /*
         * 🧪 Zadanie 10:
         * Stwórz SecureRandom i wygeneruj 5 losowych liczb całkowitych od 0 do 99.
         * Wypisz każdą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_RandomRGB {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj 3 losowe kolory RGB używając Random.nextInt(256) dla każdej składowej.
         * Wypisz każdy w formacie "rgb(R, G, B)".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_SeededRandom {
        /*
         * 🧪 Zadanie 12:
         * Stwórz Random z seed: new Random(42).
         * Wygeneruj 5 liczb. Stwórz nowy Random(42) i wygeneruj 5 liczb ponownie.
         * Wyniki powinny być identyczne – wypisz oba zestawy i potwierdź.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_DiceRoll {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj rzut kością do gry (1-6) używając Random.
         * Wypisz wynik 10 rzutów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_CoinFlip {
        /*
         * 🧪 Zadanie 14:
         * Zasymuluj 20 rzutów monetą używając random.nextBoolean().
         * Wypisz "Orzeł" lub "Reszka" dla każdego rzutu.
         * Policz i wypisz, ile było Orłów i ile Reszek.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_RandomPassword {
        /*
         * 🧪 Zadanie 15:
         * Wygeneruj losowe hasło o długości 8 znaków.
         * Użyj znaków: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".
         * Użyj Random i wypisz wygenerowane hasło.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_SecureRandomBytes {
        /*
         * 🧪 Zadanie 16:
         * Stwórz SecureRandom i wypełnij tablicę byte[] token = new byte[16].
         * Wypisz token w formacie hex (każdy bajt jako 2 cyfry hex).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_SecureRandomPassword {
        /*
         * 🧪 Zadanie 17:
         * Wygeneruj silne hasło o długości 16 znaków używając SecureRandom.
         * Użyj znaków: litery (duże i małe), cyfry, znaki specjalne (!@#$%^&*).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ThreadLocalRandomRange {
        /*
         * 🧪 Zadanie 18:
         * Użyj ThreadLocalRandom.current().nextInt(min, max) do generowania liczb.
         * Wygeneruj 10 liczb z zakresu [100, 200).
         * Wypisz każdą i sprawdź, czy mieści się w zakresie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_LotteryPicker {
        /*
         * 🧪 Zadanie 19:
         * Wylosuj 6 unikalnych liczb od 1 do 49 (jak w Lotto).
         * Użyj List<Integer> i sprawdzaj, czy liczba już istnieje na liście przed dodaniem.
         * Wypisz wylosowane liczby w kolejności rosnącej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ShuffleNames {
        /*
         * 🧪 Zadanie 20:
         * Stwórz listę imion: ["Adam", "Beata", "Celina", "Darek", "Ewa"].
         * Przetasuj ją 3 razy i wypisz po każdym tasowaniu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_RandomWalk {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj "błądzenie losowe" (random walk) w 1D:
         * Zacznij od pozycji 0. W każdym z 20 kroków idź +1 lub -1 (losowo).
         * Wypisz pozycję po każdym kroku i końcową pozycję.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_NormalDistributionApprox {
        /*
         * 🧪 Zadanie 22:
         * Użyj Random.nextGaussian() aby wygenerować 10 liczb z rozkładu normalnego.
         * Przeskaluj je: wartość = (int) (mean + stdDev * random.nextGaussian())
         * gdzie mean = 100, stdDev = 15 (jak IQ).
         * Wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_SecureUUID {
        /*
         * 🧪 Zadanie 23:
         * Wygeneruj losowy UUID (32 znaki hex) ręcznie używając SecureRandom:
         * StringBuilder uuid = new StringBuilder();
         * for (int i = 0; i < 32; i++) { uuid.append(Integer.toHexString(sr.nextInt(16))); }
         * Wypisz UUID.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_RandomArrayFill {
        /*
         * 🧪 Zadanie 24:
         * Stwórz int[] arr = new int[10].
         * Wypełnij każdy element losową liczbą z zakresu 1-100 używając Random.
         * Wypisz tablicę, a następnie znajdź jej min i max.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_StatisticsFromRandom {
        /*
         * 🧪 Zadanie 25:
         * Wygeneruj 1000 losowych liczb z zakresu 1-6 (rzut kością).
         * Policz, ile razy wypadła każda wartość (1, 2, 3, 4, 5, 6).
         * Wypisz histagram: "1: 167 (16.7%), 2: 163 (16.3%), ..."
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_SecureVsPseudoRandom {
        /*
         * 🧪 Zadanie 26:
         * Porównaj szybkość: wygeneruj 100000 liczb używając:
         * a) Random
         * b) SecureRandom
         * Zmierz czas (System.currentTimeMillis()).
         * Wypisz czas każdego i porównaj.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_CardDeck {
        /*
         * 🧪 Zadanie 27:
         * Stwórz talię kart jako List<String> (np. "A♠", "K♥", "Q♦", itd. – 52 karty).
         * Potasuj używając Collections.shuffle(deck).
         * Wypisz 5 "wziętych" kart.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_RandomNameGenerator {
        /*
         * 🧪 Zadanie 28:
         * Stwórz listy: String[] firstNames = {"Jan", "Anna", ...} i String[] lastNames = {...}.
         * Losuj z obu list i generuj 5 pełnych imion i nazwisk.
         * Użyj Random.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_OTPGenerator {
        /*
         * 🧪 Zadanie 29:
         * Wygeneruj 6-cyfrowy kod OTP (One Time Password) używając SecureRandom.
         * Każda cyfra powinna być od 0 do 9.
         * Wypisz kod w formacie "OTP: XXXXXX" (np. "OTP: 847291").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_MonteCarloPI {
        /*
         * 🧪 Zadanie 30:
         * Aproksymuj wartość π metodą Monte Carlo używając Random:
         * Generuj N = 100000 losowych punktów (x, y) ∈ [0, 1].
         * Policz ile leży w ćwiartce koła: x² + y² <= 1.
         * π ≈ 4 * (punkty_w_kole / N).
         * Wypisz aproksymację i porównaj z Math.PI.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
