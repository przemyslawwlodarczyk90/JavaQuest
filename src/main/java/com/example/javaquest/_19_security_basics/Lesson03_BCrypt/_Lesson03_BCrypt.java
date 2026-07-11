package com.example.javaquest._19_security_basics.Lesson03_BCrypt;

import org.mindrot.jbcrypt.BCrypt;

public class _Lesson03_BCrypt {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: BCRYPT ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 2
         * ============================================================
         * SHA-256/MD5 + sol rozwiazuja problem identycznych hasel i
         * tablic teczowych, ale SA ZA SZYBKIE - miliony prob/sekunde przy
         * brute-force. Potrzebujemy algorytmu CELOWO WOLNEGO. BCrypt
         * (Niels Provos i David Mazieres, 1999, oparty na szyfrze
         * Blowfish) jest dokladnie takim algorytmem - ZAPROJEKTOWANYM
         * specjalnie do hasel.
         */
        System.out.println("BCrypt = algorytm hashowania CELOWO WOLNY, zaprojektowany specjalnie do hasel (1999, Blowfish).");

        demonstrateBasicHashing();
        demonstrateSaltIsBuiltIn();
        demonstrateCostFactor();
        demonstrateVerification();

        /*
         * ============================================================
         * 🔹 CO ZAWIERA STRING ZWRACANY PRZEZ BCrypt.hashpw()?
         * ============================================================
         * Przykladowy wynik: $2a$12$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW
         * - $2a$   -> wersja algorytmu BCrypt
         * - $12$   -> cost factor (log2 liczby rund, tu 2^12 = 4096)
         * - reszta -> 22 znaki soli (zakodowanej Base64) + 31 znakow hasha
         * WSZYSTKO w JEDNYM stringu - NIE trzeba osobno przechowywac soli
         * w bazie (w odroznieniu od recznego rozwiazania z Lesson02) -
         * BCrypt generuje i osadza sol AUTOMATYCZNIE przy kazdym wywolaniu.
         */
        System.out.println("\nFormat $2a$12$<22-znakowa-sol><31-znakowy-hash> - sol jest OSADZONA w wyniku, nie trzeba jej osobno przechowywac.");

        demonstrateWhyPasswordLengthIsLimited();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - BCrypt = CELOWO wolny, adaptacyjny (cost factor rosnie z
         *   moca sprzetu) algorytm hashowania hasel.
         * - Sol jest GENEROWANA AUTOMATYCZNIE i OSADZONA w wyniku -
         *   nie trzeba jej osobno przechowywac ani przekazywac.
         * - `BCrypt.checkpw(haslo, zapisanyHash)` weryfikuje bez
         *   potrzeby recznego wyciagania soli.
         * - Cost factor (typowo 10-12) kontroluje "koszt" - kazdy +1
         *   PODWAJA czas obliczen.
         * - BCrypt ma limit dlugosci wejscia (efektywnie 72 BAJTY) -
         *   praktyczny szczegol, o ktorym trzeba wiedziec.
         * - BCrypt NIE jest jedynym dobrym wyborem - scrypt i Argon2
         *   (zwlaszcza Argon2id) sa NOWSZE i dodaja odpornosc na ataki
         *   z uzyciem duzej ilosci PAMIECI (memory-hardness), czego
         *   BCrypt nie ma - w NOWYCH projektach czesto rekomendowany jest
         *   Argon2id, ale BCrypt wciaz jest szeroko uzywany i uznawany za
         *   bezpieczny.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateBasicHashing() {
        System.out.println("\n=== PODSTAWOWE HASHOWANIE BCRYPT ===");

        String password = "MojeSuperHaslo123";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Haslo: " + password);
        System.out.println("Hash BCrypt: " + hashed);
        System.out.println("Dlugosc hasha: " + hashed.length() + " znakow (zawsze taka sama dla danej wersji/cost factora).");
    }

    private static void demonstrateSaltIsBuiltIn() {
        System.out.println("\n=== SOL JEST AUTOMATYCZNA I UNIKALNA PRZY KAZDYM WYWOLANIU ===");

        String password = "TakieSameHaslo"; // to samo haslo, 2 wywolania

        String hash1 = BCrypt.hashpw(password, BCrypt.gensalt());
        String hash2 = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Haslo: " + password);
        System.out.println("Hash #1: " + hash1);
        System.out.println("Hash #2: " + hash2);
        System.out.println("Hashe identyczne: " + hash1.equals(hash2)
                + "  -> mimo TEGO SAMEGO hasla, BCrypt.gensalt() za kazdym razem losuje NOWA sol.");
    }

    private static void demonstrateCostFactor() {
        System.out.println("\n=== COST FACTOR - REGULOWANA 'KOSZTOWNOSC' OBLICZEN ===");

        String password = "TestoweHaslo";

        for (int cost : new int[] {4, 10, 12}) {
            long start = System.nanoTime();
            BCrypt.hashpw(password, BCrypt.gensalt(cost));
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Cost factor " + cost + " -> czas hashowania: " + elapsedMs + " ms (2^" + cost + " = " + (1 << cost) + " rund).");
        }
        System.out.println("Kazdy +1 do cost factora PODWAJA czas obliczen - pozwala z czasem 'nadazac' za coraz szybszym sprzetem atakujacych.");
        System.out.println("Typowa rekomendacja produkcyjna: cost factor 10-12 (kompromis miedzy bezpieczenstwem a czasem odpowiedzi logowania).");
    }

    private static void demonstrateVerification() {
        System.out.println("\n=== WERYFIKACJA HASLA PRZEZ BCrypt.checkpw() ===");

        String password = "Kotek123";
        String storedHash = BCrypt.hashpw(password, BCrypt.gensalt(10));

        System.out.println("Zapisany hash w 'bazie danych': " + storedHash);

        boolean correctAttempt = BCrypt.checkpw("Kotek123", storedHash);
        boolean wrongAttempt = BCrypt.checkpw("Kotek124", storedHash);

        System.out.println("Proba logowania z poprawnym haslem \"Kotek123\" -> " + correctAttempt);
        System.out.println("Proba logowania z blednym haslem \"Kotek124\" -> " + wrongAttempt);
        System.out.println("checkpw() SAMO wyciaga sol z zapisanego hasha - nie trzeba jej przekazywac osobno.");
    }

    private static void demonstrateWhyPasswordLengthIsLimited() {
        System.out.println("\n=== PUlAPKA: BCRYPT SKRACA HASLA DLUZSZE NIZ 72 BAJTY ===");

        String shortPassword = "a".repeat(72);
        String longPassword = "a".repeat(72) + "COS_CO_NIE_MA_ZNACZENIA_BO_JEST_ODCIETE";

        String hashShort = BCrypt.hashpw(shortPassword, BCrypt.gensalt(4));

        boolean matchesLongerVariant = BCrypt.checkpw(longPassword, hashShort);
        System.out.println("Hash 72 znakow 'a' pasuje do hasla 72 znakow 'a' + dodatkowy sufiks: " + matchesLongerVariant);
        System.out.println("-> BCrypt UZYWA (efektywnie) TYLKO pierwszych 72 bajtow wejscia - reszta jest ignorowana.");
        System.out.println("   W praktyce rzadko problem (haslo >72 znakow to rzadkosc), ale WARTO o tym wiedziec.");
    }
}
