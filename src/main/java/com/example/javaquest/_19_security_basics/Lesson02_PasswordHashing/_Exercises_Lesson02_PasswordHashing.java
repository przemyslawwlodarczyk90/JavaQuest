package com.example.javaquest._19_security_basics.Lesson02_PasswordHashing;

public class _Exercises_Lesson02_PasswordHashing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainHashingVsEncryptionInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy
         * hashowaniem a szyfrowaniem - podaj po 1 przyklad zastosowania
         * kazdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HashPasswordWithSha256 {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode liczaca SHA-256 z podanego hasla (String) i
         * wypisujaca wynik jako hex. Uzyj hasla "TajneHaslo!".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ShowIdenticalPasswordsGiveIdenticalHashes {
        /*
         * 🧪 Zadanie 3:
         * Policz SHA-256 dla 2 identycznych hasel od 2 "roznych
         * uzytkownikow" i wypisz, ze hashe sa identyczne - skomentuj,
         * dlaczego to problem bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GenerateRandomSaltPerUser {
        /*
         * 🧪 Zadanie 4:
         * Uzyj `SecureRandom` do wygenerowania 16-bajtowej losowej soli
         * dla 3 "uzytkownikow" - wypisz kazda sol jako hex, zweryfikuj
         * ze wszystkie 3 sa rozne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HashPasswordWithSalt {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj hashowanie hasla POLACZONEGO z sola
         * (sol + haslo -> SHA-256) i wypisz wynik dla hasla "Kotek123"
         * z wlasnie wygenerowana sola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ShowSameSaltedPasswordsGiveDifferentHashes {
        /*
         * 🧪 Zadanie 6:
         * Policz posolony hash dla TEGO SAMEGO hasla z 2 roznymi solami -
         * zweryfikuj, ze wynikowe hashe sa rozne mimo identycznego hasla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyPasswordAgainstStoredSaltAndHash {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode `verify(String podaneHaslo, String zapisanaSol,
         * String zapisanyHash)` zwracajaca boolean - przetestuj z
         * poprawnym i niepoprawnym haslem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MeasureSha256SpeedForBruteForce {
        /*
         * 🧪 Zadanie 8:
         * Zmierz czas policzenia 100 000 hashy SHA-256 - wypisz wynik w
         * ms oraz przelicz na przyblizona liczbe hashy/sekunde.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyRainbowTablesFailWithSalt {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego tablice teczowe (rainbow
         * tables) staja sie bezuzyteczne, gdy kazdy uzytkownik ma
         * unikalna sol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_StoreSaltAndHashTogetherInRecord {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj rekord `StoredPassword(String salt, String hash)` i
         * zapisz w Mapie username->StoredPassword dla 2 uzytkownikow.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_BuildInMemoryUserRegistrationWithHashing {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `register(username, password)` zapisujacy
         * uzytkownika z wygenerowana sola i posolonym hashem (bez
         * przechowywania jawnego hasla NIGDZIE, nawet w zmiennej po
         * zakonczeniu metody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildLoginUsingStoredSaltAndHash {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `login(username, password)` zwracajacy boolean -
         * odczytuje sol i hash zapisanego uzytkownika, liczy hash podanego
         * hasla z ta sola i porownuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseConstantTimeComparisonForHashes {
        /*
         * 🧪 Zadanie 13:
         * Zamien proste `String.equals()` na
         * `MessageDigest.isEqual(byte[], byte[])` przy porownywaniu
         * hashy - wyjasnij w komentarzu, przed czym to chroni (timing
         * attack).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateTimingAttackVulnerability {
        /*
         * 🧪 Zadanie 14:
         * Zmierz (w petli, usredniajac) czas porownania `String.equals()`
         * dla stringow roznlacych sie na 1. znaku vs na ostatnim znaku -
         * skomentuj, czy roznica jest widoczna na tym komputerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementPasswordStrengthCheck {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode sprawdzajaca sile hasla (min. 8 znakow, 1 cyfra,
         * 1 wielka litera) PRZED zahashowaniem - odrzuc za slabe haslo z
         * czytelnym komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareMd5Sha1Sha256Sha512Speeds {
        /*
         * 🧪 Zadanie 16:
         * Zmierz czas policzenia 100 000 hashy dla MD5, SHA-1, SHA-256,
         * SHA-512 - wypisz porownanie i skomentuj, ktory jest
         * "najbezpieczniejszy" DLA HASEL (podpowiedz: zaden - wszystkie
         * sa za szybkie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SimulateDatabaseBreachWithSaltedHashes {
        /*
         * 🧪 Zadanie 17:
         * Zasymuluj "wyciek bazy" (wypisz mape username->StoredPassword)
         * i skomentuj, co atakujacy MOZE, a czego NIE MOZE zrobic majac
         * TYLKO te dane (bez proby brute-force).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementPasswordChangeFlow {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj `changePassword(username, staryHaslo, noweHaslo)` -
         * najpierw zweryfikuj stare haslo, dopiero potem zapisz nowa
         * sol+hash.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RejectReusingSameSaltAcrossUsers {
        /*
         * 🧪 Zadanie 19:
         * Napisz test/asercje sprawdzajace, ze 100 wygenerowanych soli
         * (dla 100 "uzytkownikow") NIE zawiera ani jednego duplikatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhySaltIsNotSecret {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij, dlaczego sol MOZE byc jawnie zapisana
         * obok hasha w bazie (nie musi byc tajna) - co faktycznie musi
         * pozostac sekretem?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementPepperInAdditionToSalt {
        /*
         * 🧪 Zadanie 21:
         * Dodaj "pieprz" (pepper) - stala, sekretna wartosc znana TYLKO
         * serwerowi (nie w bazie), dolaczana obok soli przed hashowaniem -
         * wyjasnij w komentarzu roznice miedzy sola a pieprzem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateBruteForceAttackOnWeakPassword {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj atak brute-force na SLABE, znane haslo (np. 4-cyfrowy
         * PIN) uzywajac goleog SHA-256+sol - zmierz ile prob/czasu
         * potrzeba, zeby je znalezc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareDictionaryAttackWithAndWithoutSalt {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj mala "slownikowa" liste popularnych hasel i pokaz, jak
         * atak slownikowy dziala na hash BEZ soli (masowo, 1 raz na caly
         * slownik) vs Z sola (osobno na kazdego uzytkownika).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementKeyStretchingWithManualIterations {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj recznie "key stretching" - hashuj wynik hashowania
         * N razy (np. 10 000 iteracji) i zmierz, o ile to spowalnia
         * pojedyncza weryfikacje w porownaniu do 1 iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_UsePbkdf2WithHmacSha256 {
        /*
         * 🧪 Zadanie 25:
         * Uzyj wbudowanego `javax.crypto.SecretKeyFactory` z algorytmem
         * "PBKDF2WithHmacSHA256" (JDK ma to gotowe) do zahashowania hasla
         * z solą i 100 000 iteracjami - porownaj kod z recznym
         * rozwiazaniem z Zadania 24.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasurePbkdf2CostAtDifferentIterationCounts {
        /*
         * 🧪 Zadanie 26:
         * Zmierz czas PBKDF2 dla 10 000, 100 000 i 600 000 iteracji -
         * skomentuj kompromis miedzy bezpieczenstwem (wolniej dla
         * atakujacego) a UX (wolniej tez dla prawdziwego uzytkownika przy
         * logowaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignPasswordResetTokenFlow {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj (i zaimplementuj w pamieci) przeplyw "resetu hasla"
         * - losowy, jednorazowy token z czasem wygasniecia, NIGDY samo
         * haslo, wyslany "emailem" (wypisany na konsole).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DetectAndRejectPasswordReuseHistory {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj historie ostatnich 3 hashy hasel per uzytkownik -
         * odrzuc zmiane hasla, jesli nowe haslo hashuje sie (z ODPOWIEDNIA
         * sola z historii) na jeden z tych 3 hashy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExplainWhyBcryptScryptArgon2AreBetterThanPbkdf2 {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zbadaj i wyjasnij (kilka zdan), czym rozni sie
         * PBKDF2 od bcrypt/scrypt/Argon2 pod katem odpornosci na ataki
         * sprzetowe (GPU/ASIC) - "memory-hardness".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteRegistrationLoginDemoWithPbkdf2 {
        /*
         * 🧪 Zadanie 30:
         * Polacz wszystko w 1 dzialajace demo: rejestracja (PBKDF2+sol),
         * logowanie (poprawne i niepoprawne haslo), zmiana hasla z
         * odrzuceniem ponownego uzycia - wypisz czytelny log kazdego kroku.
         */
        public static void main(String[] args) { }
    }
}
