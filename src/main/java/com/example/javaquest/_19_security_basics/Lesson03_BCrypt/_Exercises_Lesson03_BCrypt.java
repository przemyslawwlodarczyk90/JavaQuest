package com.example.javaquest._19_security_basics.Lesson03_BCrypt;

public class _Exercises_Lesson03_BCrypt {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_HashPasswordWithBcrypt {
        /*
         * 🧪 Zadanie 1:
         * Uzyj `BCrypt.hashpw(haslo, BCrypt.gensalt())` do zahashowania
         * hasla "SekretneHaslo!" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyCorrectPassword {
        /*
         * 🧪 Zadanie 2:
         * Zahashuj haslo, a nastepnie zweryfikuj je `BCrypt.checkpw()` -
         * wypisz, ze weryfikacja sie powiodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyWrongPassword {
        /*
         * 🧪 Zadanie 3:
         * Zweryfikuj NIEPOPRAWNE haslo wobec zapisanego hasha - wypisz,
         * ze weryfikacja zawiodla (false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ShowSameHashedPasswordGivesDifferentOutput {
        /*
         * 🧪 Zadanie 4:
         * Zahashuj to samo haslo 3 razy - wypisz wszystkie 3 hashe i
         * zweryfikuj, ze sa rozne (dzieki automatycznej soli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ParseVersionAndCostFromHash {
        /*
         * 🧪 Zadanie 5:
         * Wypisz wersje algorytmu ($2a/$2b) i cost factor wyciagniete z
         * hasha BCrypt (podziel string po "$").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_HashWithCustomCostFactor {
        /*
         * 🧪 Zadanie 6:
         * Zahashuj haslo z jawnie podanym cost factor (np. 8) uzywajac
         * `BCrypt.gensalt(8)` - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareTimeForDifferentCostFactors {
        /*
         * 🧪 Zadanie 7:
         * Zmierz czas hashowania dla cost factor 4, 8, 12 - wypisz wyniki
         * w tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementInMemoryUserStoreWithBcrypt {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj Mape username->hashBCrypt dla 3 uzytkownikow -
         * zarejestruj kazdego z inna sila hasla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementLoginUsingBcryptCheckpw {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj `login(username, password)` korzystajac z Mapy z
         * Zadania 8 i `BCrypt.checkpw()` - przetestuj sukces i porazke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyBcryptHashLengthIsAlwaysTheSame {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego dlugosc hasha BCrypt jest
         * ZAWSZE taka sama (60 znakow), niezaleznie od dlugosci
         * wejsciowego hasla.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_MeasureCostFactorDoublingPattern {
        /*
         * 🧪 Zadanie 11:
         * Zmierz czas dla cost factor 8, 9, 10, 11, 12 i wypisz stosunek
         * czasu miedzy kolejnymi wartosciami - zweryfikuj, ze kazdy +1
         * W PRZYBLIZENIU podwaja czas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ChooseCostFactorForTargetResponseTime {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode, ktora automatycznie znajduje NAJWIEKSZY cost
         * factor, przy ktorym hashowanie trwa PONIZEJ 250 ms na tym
         * komputerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DemonstrateBcrypt72ByteTruncation {
        /*
         * 🧪 Zadanie 13:
         * Zademonstruj (jak w teorii), ze BCrypt ignoruje bajty POWYZEJ
         * 72 - zbuduj 2 hasla rozniace sie TYLKO za 72. znakiem i pokaz,
         * ze maja TEN SAM efektywny hash.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_HandleUnicodePasswordsCorrectly {
        /*
         * 🧪 Zadanie 14:
         * Zahashuj i zweryfikuj haslo zawierajace polskie znaki
         * diakrytyczne (np. "HasloZĄĆŚŻ123") - upewnij sie, ze
         * weryfikacja dziala poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RejectEmptyOrNullPasswordBeforeHashing {
        /*
         * 🧪 Zadanie 15:
         * Napisz walidacje odrzucajaca puste/`null` haslo PRZED
         * wywolaniem `BCrypt.hashpw()` (ktore rzuci wyjatek dla `null`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementPasswordUpgradeOnLogin {
        /*
         * 🧪 Zadanie 16:
         * Zasymuluj "stare" konto z hashem o niskim cost factor (np. 4) -
         * przy udanym logowaniu, ZAHASHUJ PONOWNIE haslo z WYZSZYM cost
         * factorem i zaktualizuj zapis (typowy wzorzec "rehash on login").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareBcryptWithPbkdf2FromLesson2 {
        /*
         * 🧪 Zadanie 17:
         * Zahashuj to samo haslo BCryptem i PBKDF2 (z Lesson02, Zadanie
         * 25) - porownaj czytelnosc kodu i dlugosc wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildRegistrationWithPasswordConfirmation {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj rejestracje wymagajaca podania hasla DWA razy
         * (haslo + potwierdzenie) - odrzuc, jesli sie nie zgadzaja,
         * inaczej zahashuj BCryptem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HandleBcryptExceptionForMalformedHash {
        /*
         * 🧪 Zadanie 19:
         * Sprobuj wywolac `BCrypt.checkpw()` z celowo USZKODZONYM
         * (nie-BCryptowym) stringiem jako "hashem" - przechwyc wyjatek i
         * obsluz go bezpiecznie (bez ujawniania szczegolow uzytkownikowi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyCostFactorNeedsPeriodicReview {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij, dlaczego cost factor ustawiony "raz na
         * zawsze" (np. w 2015) MOZE stac sie za slaby w 2026 - co
         * powinna zrobic firma, ktora to zauwazy?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullRegistrationLoginWithRehashOnCostChange {
        /*
         * 🧪 Zadanie 21:
         * Polacz Zadania 8, 9, 16 w 1 spojne demo: rejestracja, logowanie,
         * i automatyczny "rehash" gdy wykryty cost factor zapisanego
         * hasha jest NIZSZY niz aktualny standard aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateBruteForceCostComparison {
        /*
         * 🧪 Zadanie 22:
         * Zmierz, ile prob hasel/sekunde da sie wykonac przy cost factor
         * 4 vs 12 - przelicz na przyblizony czas potrzebny do
         * "brute force" 4-cyfrowego PINu w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementAccountLockoutAfterFailedAttempts {
        /*
         * 🧪 Zadanie 23:
         * Dodaj licznik nieudanych prob logowania per uzytkownik -
         * zablokuj konto na "5 minut" (symulowane, nie realny sleep) po 5
         * nieudanych probach z rzedu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareBcryptScryptArgon2Conceptually {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: zbadaj i porownaj (tabela w komentarzu) BCrypt
         * vs scrypt vs Argon2id pod katem: rok powstania, odpornosc na
         * GPU/ASIC (memory-hardness), dostepnosc w JDK/bibliotekach Java.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BenchmarkBcryptUnderConcurrentLogins {
        /*
         * 🧪 Zadanie 25:
         * Uzyj puli watkow (np. `ExecutorService` z 10 watkami) do
         * rownoleglego zweryfikowania 100 logowan BCryptem - zmierz
         * calkowity czas i porownaj z wykonaniem sekwencyjnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ProtectAgainstNullByteInjectionInPassword {
        /*
         * 🧪 Zadanie 26:
         * Sprawdz (i wyjasnij w komentarzu), jak jBCrypt zachowuje sie z
         * haslem zawierajacym bajt zerowy (`\0`) - zaimplementuj
         * walidacje odrzucajaca takie haslo PRZED hashowaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMultiFactorFallbackAfterPasswordCheck {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj (zaimplementuj w pamieci) drugi "czynnik" po
         * poprawnej weryfikacji BCrypt hasla - np. staly 6-cyfrowy kod
         * wypisany na "konsole" jako symulacja SMS/email, wymagany do
         * pelnego zalogowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MigrateLegacySha256UsersToBcryptGradually {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj baze z 2 typami uzytkownikow: "starzy" (SHA-256+sol z
         * Lesson02) i "nowi" (BCrypt) - zaimplementuj logowanie, ktore
         * rozpoznaje typ hasha (po prefiksie) i przy udanym logowaniu
         * "starego" uzytkownika MIGRUJE go na BCrypt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LoadTestCostFactorSelectionForServerBudget {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj metode dobierajaca cost factor tak, by
         * hashowanie mieściło sie w budzecie "maks. 500ms NA RDZEN przy
         * 50 rownoleglych logowaniach" - uzasadnij wybrana wartosc
         * pomiarem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureAuthDemoWithBcrypt {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo: rejestracja z walidacja sily hasla
         * (Lesson02, Zadanie 15) + BCrypt + blokada konta (Zadanie 23) +
         * automatyczny rehash (Zadanie 16) - wypisz czytelny log calego
         * przeplywu dla 2 uzytkownikow.
         */
        public static void main(String[] args) { }
    }
}
