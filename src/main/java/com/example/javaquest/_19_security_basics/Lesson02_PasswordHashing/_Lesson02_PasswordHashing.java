package com.example.javaquest._19_security_basics.Lesson02_PasswordHashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

public class _Lesson02_PasswordHashing {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: HASHOWANIE HASEL ===");

        /*
         * ============================================================
         * 📦 NIGDY NIE PRZECHOWUJ HASEL W POSTACI JAWNEJ (PLAINTEXT)
         * ============================================================
         * `_19_security_basics/Lesson01` nauczyla, ze AuthN weryfikuje
         * "kim jestes" np. przez haslo. Ale JAK serwer POWINIEN
         * przechowac to haslo, zeby moc je pozniej zweryfikowac? NIGDY
         * jako zwykly tekst w bazie danych - jesli baza wycieknie (a to
         * SIE ZDARZA - wiele glosnych wyciekow danych w historii), KAZDE
         * haslo kazdego uzytkownika jest NATYCHMIAST skompromitowane.
         */
        demonstratePlaintextDanger();

        /*
         * ============================================================
         * 🔹 HASHOWANIE vs SZYFROWANIE - KLUCZOWA ROZNICA
         * ============================================================
         * - SZYFROWANIE (encryption) - DWUKIERUNKOWE - masz klucz, mozesz
         *   zaszyfrowac I ODSZYFROWAC z powrotem do oryginalu.
         * - HASHOWANIE (hashing) - JEDNOKIERUNKOWE - z hasla liczysz hash,
         *   ale z hasha NIE DA SIE (w praktyce) odzyskac oryginalnego
         *   hasla. Do WERYFIKACJI hasla NIE trzeba go odszyfrowywac -
         *   wystarczy policzyc hash podanego hasla i POROWNAC z
         *   zapisanym hashem.
         * Hasla ZAWSZE hashujemy, NIGDY nie szyfrujemy (nie potrzebujemy
         * nigdy odzyskac oryginalu - tylko zweryfikowac zgodnosc).
         */
        System.out.println("\nSzyfrowanie = dwukierunkowe (da sie odszyfrowac). Hashowanie = jednokierunkowe (NIE da sie 'odhashowac').");
        System.out.println("Hasla ZAWSZE hashujemy - do weryfikacji wystarczy POROWNAC hashe, nie trzeba znac oryginalu.");

        demonstrateSimpleHashWeakness();
        demonstrateSaltingSolution();
        demonstrateFastHashVulnerability();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - NIGDY plaintext - wyciek bazy = natychmiastowa kompromitacja
         *   wszystkich hasel.
         * - Hashowanie jest JEDNOKIERUNKOWE - do weryfikacji wystarczy
         *   porownanie hashy.
         * - PROSTY hash (np. goly SHA-256) BEZ soli ma 2 problemy:
         *   (a) IDENTYCZNE hasla daja IDENTYCZNY hash - atakujacy widzi
         *   "ci uzytkownicy maja to samo haslo", (b) podatny na tablice
         *   teczowe (rainbow tables) - gotowe bazy hash->haslo.
         * - SOL (salt) - losowa wartosc UNIKALNA per uzytkownik, dodana
         *   do hasla PRZED hashowaniem - rozwiazuje OBA powyzsze problemy.
         * - ALE sama sol NIE WYSTARCZY - SHA-256/MD5 sa CELOWO SZYBKIE
         *   (miliardy/s na nowoczesnym GPU) - to ZLE dla hasel (dobre dla
         *   integralnosci plikow, gdzie szybkosc jest zaleta).
         * - Rozwiazanie: WOLNE, celowo "kosztowne" algorytmy zaprojektowane
         *   SPECJALNIE do hasel (bcrypt, scrypt, Argon2) - dokladnie tym
         *   zajmuje sie NASTEPNA lekcja (Lesson03: BCrypt).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstratePlaintextDanger() {
        System.out.println("\n=== NIEBEZPIECZENSTWO PRZECHOWYWANIA HASEL JAWNYM TEKSTEM ===");
        String badDatabaseRow = "username=ala, password=MojeSuperHaslo123";
        System.out.println("ZLE (baza danych): " + badDatabaseRow);
        System.out.println("-> jesli KTOKOLWIEK (haker, ciekawski administrator, wyciek backupu) zobaczy te tabele,");
        System.out.println("   ma NATYCHMIASTOWY dostep do PRAWDZIWEGO hasla uzytkownika - rowniez do INNYCH serwisow,");
        System.out.println("   bo ludzie CZESTO uzywaja tego samego hasla wszedzie.");
    }

    private static void demonstrateSimpleHashWeakness() throws NoSuchAlgorithmException {
        System.out.println("\n=== SLABOSC 'GOLEGO' HASHA (BEZ SOLI) ===");

        String password1 = "MojeSuperHaslo123";
        String password2 = "MojeSuperHaslo123"; // 2 rozni uzytkownicy z TYM SAMYM haslem

        String hash1 = sha256(password1);
        String hash2 = sha256(password2);

        System.out.println("Haslo uzytkownika A: \"" + password1 + "\" -> hash: " + hash1);
        System.out.println("Haslo uzytkownika B: \"" + password2 + "\" -> hash: " + hash2);
        System.out.println("Hashe IDENTYCZNE: " + hash1.equals(hash2)
                + "  -> atakujacy z dostepem do bazy WIDZI, ze A i B maja TO SAMO haslo (i moze uzyc gotowej tablicy teczowej).");
    }

    private static void demonstrateSaltingSolution() throws NoSuchAlgorithmException {
        System.out.println("\n=== ROZWIAZANIE: SOL (SALT) UNIKALNA PER UZYTKOWNIK ===");

        String password = "MojeSuperHaslo123"; // TO SAMO haslo dla obu

        String saltA = generateSalt();
        String saltB = generateSalt();

        String hashA = sha256(saltA + password);
        String hashB = sha256(saltB + password);

        System.out.println("Sol uzytkownika A: " + saltA + " -> hash(sol+haslo): " + hashA);
        System.out.println("Sol uzytkownika B: " + saltB + " -> hash(sol+haslo): " + hashB);
        System.out.println("Hashe IDENTYCZNE: " + hashA.equals(hashB)
                + "  -> mimo TEGO SAMEGO hasla, hashe sa CALKOWICIE ROZNE dzieki unikalnej soli.");
        System.out.println("W bazie danych przechowujemy OBA: sol I hash - sol NIE MUSI byc tajna, musi byc UNIKALNA.");

        // Weryfikacja: uzytkownik A podaje haslo ponownie przy logowaniu
        String loginAttempt = "MojeSuperHaslo123";
        boolean matches = sha256(saltA + loginAttempt).equals(hashA);
        System.out.println("Proba logowania A z poprawnym haslem -> zgodnosc hashy: " + matches);
    }

    private static void demonstrateFastHashVulnerability() throws NoSuchAlgorithmException {
        System.out.println("\n=== DLACZEGO SHA-256 TO WCIAZ ZA MALO? SZYBKOSC = SLABOSC ===");

        long start = System.nanoTime();
        int iterations = 1_000_000;
        for (int i = 0; i < iterations; i++) {
            sha256("test-haslo-" + i);
        }
        long elapsedMillis = (System.nanoTime() - start) / 1_000_000;

        System.out.println("Policzenie " + iterations + " hashy SHA-256 zajelo: " + elapsedMillis + " ms na TYM komputerze.");
        System.out.println("-> to oznacza MILIONY prob hasel/sekunde przy ataku 'brute force' - a wyspecjalizowany");
        System.out.println("   sprzet (GPU/ASIC) osiaga jeszcze wieksze predkosci. SHA-256 zostal zaprojektowany");
        System.out.println("   ZEBY BYC SZYBKI (dobre dla sum kontrolnych plikow) - to WLASNIE CZYNI GO ZLYM wyborem do hasel.");
        System.out.println("-> potrzebujemy algorytmu CELOWO WOLNEGO i 'kosztownego' obliczeniowo - Lesson03: BCrypt.");
    }

    private static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[8];
        random.nextBytes(saltBytes);
        return HexFormat.of().formatHex(saltBytes);
    }
}
