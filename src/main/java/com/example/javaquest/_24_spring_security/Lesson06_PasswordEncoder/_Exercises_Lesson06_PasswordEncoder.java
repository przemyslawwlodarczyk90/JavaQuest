package com.example.javaquest._24_spring_security.Lesson06_PasswordEncoder;

public class _Exercises_Lesson06_PasswordEncoder {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPasswordEncoderInterface {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij 2 metody interfejsu `PasswordEncoder`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_EncodeAndMatchOwnPassword {
        /*
         * 🧪 Zadanie 2:
         * Zakoduj WLASNE haslo `BCryptPasswordEncoder` I zweryfikuj
         * `matches(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareWithJbcryptFromChapter19 {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: powiaz z `_19_security_basics/Lesson03` -
         * porownaj `BCryptPasswordEncoder.encode(...)` Z
         * `BCrypt.hashpw(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestEncodeProducesDifferentHashesEachTime {
        /*
         * 🧪 Zadanie 4:
         * Zakoduj TO SAMO haslo 5 RAZY - zweryfikuj, ze WSZYSTKIE 5
         * hashy jest ROZNYCH.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainNoopPrefixDangers {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO `{noop}` NIGDY nie
         * powinien trafic DO PRODUKCJI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestDelegatingEncoderWithMultiplePrefixes {
        /*
         * 🧪 Zadanie 6:
         * Przetestuj `DelegatingPasswordEncoder` Z 3 ROZNYMI prefiksami
         * (`{noop}`, `{bcrypt}`) NA ROZNYCH hasłach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ChangeUserPasswordAndVerifyOldStopsWorking {
        /*
         * 🧪 Zadanie 7:
         * Zmien haslo uzytkownika "dorota" I zweryfikuj, ze STARE haslo
         * JUZ NIE dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddSecondUserWithBCrypt {
        /*
         * 🧪 Zadanie 8:
         * Dodaj DRUGIEGO uzytkownika DO `InMemoryUserDetailsManager` Z
         * WLASNYM haslem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyPasswordEncoderIsInjectedNotNew {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO `PasswordEncoder` jest
         * WSTRZYKIWANY (Lesson10 `_20_spring_core`), NIE tworzony
         * `new BCryptPasswordEncoder()` W KAZDYM miejscu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareBCryptWithNoopSecurityImplications {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj bezpieczenstwo `{noop}` (Lesson05) Z
         * `{bcrypt}` (tu) NA konkretnym scenariuszu wycieku bazy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigureBCryptStrengthParameter {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `new BCryptPasswordEncoder(12)` (WYZSZA sila) I porownaj
         * CZAS `encode(...)` Z DOMYSLNA sila (10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureBCryptEncodingTime {
        /*
         * 🧪 Zadanie 12:
         * Zmierz CZAS zakodowania 100 hasel - oszacuj MAKSYMALNY
         * throughput logowan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomPasswordEncoderWrapper {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj WLASNY `PasswordEncoder` OPAKOWUJACY
         * `BCryptPasswordEncoder` Z DODATKOWYM logowaniem (bez
         * ujawniania hasel - powiazanie Z
         * `_19_security_basics/Lesson19`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RegisterMultipleEncodersInDelegatingMap {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj WLASNA mape `id -> PasswordEncoder` I przekaz JA DO
         * konstruktora `DelegatingPasswordEncoder` (ZAMIAST fabryki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestUpgradeEncodingDetection {
        /*
         * 🧪 Zadanie 15:
         * Uzyj `upgradeEncoding(String)` DO wykrycia, CZY dany hash
         * WYMAGA ponownego zakodowania (np. z {noop} NA {bcrypt}).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementPasswordChangeWorkflow {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj PELNY przeplyw zmiany hasla: sprawdz STARE (matches),
         * zakoduj NOWE, zaktualizuj `UserDetails`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareBCryptWithArgon2 {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj BCrypt Z Argon2 (`Argon2PasswordEncoder`,
         * dostepny W Spring Security) - kompromisy OBU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestPasswordEncoderWithUnicodePasswords {
        /*
         * 🧪 Zadanie 18:
         * Zakoduj I zweryfikuj haslo Z ZNAKAMI diakrytycznymi/emoji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainWhyDelegatingIsDefaultInSpringBoot {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij, DLACZEGO Spring Boot UZYWA
         * `DelegatingPasswordEncoder` jako DOMYSLNY (a NIE po prostu
         * `BCryptPasswordEncoder`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignMigrationPlanFromNoopToBcrypt {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zaprojektuj PLAN migracji WSZYSTKICH kont Z
         * `{noop}` NA `{bcrypt}` W dzialajacej aplikacji (BEZ
         * wymuszania resetu hasel).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementLazyRehashingOnSuccessfulLogin {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj mechanizm "lazy rehashing" - PRZY udanym
         * logowaniu Z {noop}, NATYCHMIAST przekoduj haslo NA {bcrypt}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BenchmarkBcryptStrengthVsSecurityTradeoff {
        /*
         * 🧪 Zadanie 22:
         * Zmierz CZAS `encode(...)` DLA sily 4/10/14/18 - zbuduj
         * TABELE kompromisow bezpieczenstwo/wydajnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementPasswordHistoryCheck {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj sprawdzanie, CZY NOWE haslo NIE POWTARZA
         * jednego Z 5 OSTATNICH (przechowywanych jako hashe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignPasswordStrengthPolicyBeforeEncoding {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson17_InputValidation` -
         * zaprojektuj walidacje SILY hasla PRZED zakodowaniem (nie
         * PO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementTimingAttackResistantComparison {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: wyjasnij, DLACZEGO `matches(...)` MUSI byc
         * ODPORNE NA ataki czasowe (timing attacks) - jak BCrypt to
         * zapewnia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareStorageSizeOfDifferentAlgorithms {
        /*
         * 🧪 Zadanie 26:
         * Porownaj DLUGOSC (w bajtach) hashy BCrypt/SHA-256/Argon2 -
         * wplyw NA rozmiar kolumny W bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementGracefulEncoderRotation {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj mechanizm PLYNNEJ ROTACJI algorytmu (np. Z BCrypt
         * NA Argon2) BEZ przestoju systemu logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestConcurrentEncodingUnderLoad {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_05_multithreading` - zweryfikuj, ze
         * `BCryptPasswordEncoder` jest BEZPIECZNY WATKOWO PRZY
         * WSPOLBIEZNYM kodowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignEncoderSelectionBasedOnComplianceRequirements {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zaprojektuj wybor algorytmu hashowania NA
         * PODSTAWIE wymogow zgodnosci (np. FIPS 140-2) - powiazanie Z
         * `_19_security_basics`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullPasswordLifecycleDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNE demo cyklu zycia hasla: rejestracja (encode) ->
         * logowanie (matches) -> wykryta koniecznosc migracji
         * (upgradeEncoding) -> zmiana hasla -> logowanie NOWYM haslem.
         */
        public static void main(String[] args) { }
    }
}
