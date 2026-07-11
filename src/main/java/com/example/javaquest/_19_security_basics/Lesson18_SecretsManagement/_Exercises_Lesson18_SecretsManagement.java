package com.example.javaquest._19_security_basics.Lesson18_SecretsManagement;

public class _Exercises_Lesson18_SecretsManagement {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyHardcodedSecretsAreDangerous {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego zahardkodowane haslo w kodzie
         * jest niebezpieczne nawet w repozytorium PRYWATNYM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadSecretFromEnvironmentVariableWithFallback {
        /*
         * 🧪 Zadanie 2:
         * Odczytaj sekret ze zmiennej srodowiskowej (nawiazanie do
         * `_10_dao/Lesson13_EnvironmentVariables`) z fallbackiem na
         * wartosc domyslna dla lokalnego developmentu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareCliArgumentVisibilityWithEnvVariable {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij, dlaczego przekazanie hasla jako
         * argumentu `main(String[] args)` jest gorsze niz zmienna
         * srodowiskowa (widocznosc w liscie procesow systemu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementMaskedToStringForSensitiveClass {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj klase z polem `apiKey`, ktorej `toString()`
         * NIGDY nie ujawnia pelnej wartosci (np. tylko ostatnie 4 znaki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DetectHardcodedPasswordWithRegex {
        /*
         * 🧪 Zadanie 5:
         * Napisz regex wykrywajacy wzorzec `password = "..."` w liscie
         * przykladowych linii kodu - przetestuj na 5 roznych liniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainGitHistoryLeakProblem {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego usuniecie pliku `.env` z
         * najnowszego commita NIE usuwa hasla z HISTORII repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementSecretProviderInterface {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj interfejs `SecretProvider` z metoda `fetchSecret(String
         * key)` i JEDNA prosta implementacje w pamieci (symulacja vaulta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainLeastPrivilegeForSecretAccess {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij zasade najmniejszych uprawnien w
         * kontekscie dostepu do sekretow - powiaz z
         * `Lesson07_AuthorizationPatternsAndRbac`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ThrowExceptionWithoutLeakingSecretValue {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj metode laczaca sie z "baza danych", ktora przy
         * bledzie rzuca wyjatek BEZ ujawniania hasla w komunikacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListPlacesWhereSecretsCanLeak {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz co najmniej 5 miejsc, gdzie sekret moze
         * przypadkiem wyciec (logi, zrzuty pamieci, argumenty CLI, Git,
         * komunikaty bledow).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCachingSecretStoreWithTtl {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `CachingSecretStore` (jak w teorii) z konfigurowalnym
         * TTL - zweryfikuj, ze po wygasnieciu TTL nastepuje PONOWNE
         * zapytanie do dostawcy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSecretRotationSimulation {
        /*
         * 🧪 Zadanie 12:
         * Zasymuluj rotacje sekretu (dostawca zmienia wartosc "w tle") -
         * pokaz, ze cache z krotszym TTL szybciej "widzi" nowa wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ScanMultipleSourceLinesForVariousSecretTypes {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj skaner z teorii o wzorce dla: kluczy prywatnych, tokenow
         * Slacka (`xox[baprs]-...`), tokenow GitHuba (`ghp_...`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementDifferentSecretsPerEnvironment {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj wybor sekretu w zaleznosci od srodowiska (dev/test/
         * prod) na podstawie zmiennej `APP_ENV` - z bezpiecznym fallbackiem
         * dla dev.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidateSecretFormatBeforeUse {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj walidacje FORMATU sekretu (np. klucz API musi
         * pasowac do wzorca dlugosci/znakow) PRZED jego uzyciem - odrzuc
         * pusty/oczywiscie bledny sekret wczesnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSecretWrapperPreventingAccidentalLogging {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj klase `Secret<T>` opakowujaca dowolna wartosc,
         * ktorej `toString()`/`equals()` NIGDY nie ujawnia oryginalu -
         * dostep tylko przez jawna metode `expose()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareEnvVarsVaultAndConfigFileTradeoffs {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj 3 podejscia (zmienne srodowiskowe, plik
         * konfiguracyjny, dedykowany vault) pod katem bezpieczenstwa,
         * prostoty i skalowalnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementFallbackChainOfSecretSources {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj lancuch zrodel sekretu (najpierw vault, potem
         * zmienna srodowiskowa, potem wartosc domyslna) z jasnym logiem,
         * KTORE zrodlo zostalo uzyte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DetectSecretInExceptionMessage {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode wykrywajaca (regexem), czy komunikat wyjatku
         * przypadkiem zawiera slowo "password"/"secret"/"token" wraz z
         * wartoscia - ostrzez, jesli tak.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementPerServiceScopedSecretAccess {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj symulacje kontroli dostepu - "serwis A" moze
         * odczytac TYLKO sekrety z prefiksem "serviceA.", "serwis B" tylko
         * "serviceB." - odrzuc probe dostepu poza swoim zakresem.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementShortLivedDynamicSecretSimulation {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj "krotkozywotny" sekret (np. token wazny 5 sekund) -
         * zweryfikuj, ze proba uzycia po wygasnieciu konczy sie
         * jednoznacznym bledem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildAuditTrailForSecretAccess {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj `CachingSecretStore` o audyt kazdego odczytu (kto, kiedy,
         * jaki KLUCZ - NIGDY wartosc) - powiaz z `Lesson19_SecureLoggingAndAuditing`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementEncryptedSecretAtRestSimulation {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj przechowywanie sekretu "zaszyfrowanego" w pamieci
         * (proste XOR lub Base64 jako SYMULACJA, z jasnym komentarzem, ze
         * to NIE jest prawdziwe szyfrowanie) - odszyfruj TYLKO przy uzyciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSecretScannerAsPreCommitSimulation {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj metode symulujaca "pre-commit hook" - skanuje liste
         * "plikow do commitowania" (Stringi) i BLOKUJE commit, jesli
         * znajdzie zahardkodowany sekret.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareStaticVsDynamicSecretsSecurityImpact {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: wyjasnij, dlaczego sekrety DYNAMICZNE (generowane
         * na zadanie, krotkozywotne) sa bezpieczniejsze niz statyczne
         * (raz wygenerowane, uzywane miesiacami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementGracefulDegradationWhenVaultUnavailable {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj obsluge sytuacji, gdy "vault" jest niedostepny
         * (wyjatek przy fetchSecret) - z jasna strategia (uzyj cache mimo
         * wygasniecia TTL / odrzuc zadanie z czytelnym bledem) zamiast
         * milczacej awarii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSecretVersioningForSafeRotation {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj wersjonowanie sekretu (stara i nowa wersja
         * "aktywne" jednoczesnie przez okres przejsciowy) - wyjasnij,
         * dlaczego to bezpieczniejsze niz natychmiastowa wymiana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildCompleteSecretLifecycleDemo {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj demo pelnego cyklu zycia sekretu: utworzenie -> uzycie z
         * cache'em -> rotacja -> wygasniecie starej wersji - z czytelnym
         * logiem kazdego etapu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AuditCodebaseSimulationForSecretHygiene {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode "audytujaca" liste symulowanych plikow projektu pod
         * katem higieny sekretow (brak hardcode, obecnosc `.gitignore` dla
         * `.env`, brak sekretu w komunikatach bledow) - wygeneruj raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildProductionReadySecretManagementLayer {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna warstwe zarzadzania sekretami laczaca: lancuch
         * zrodel (Zadanie 18), cache z TTL, audyt dostepu (Zadanie 22),
         * obsluge niedostepnosci vaulta (Zadanie 26) - zweryfikuj co
         * najmniej 4 scenariusze z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
