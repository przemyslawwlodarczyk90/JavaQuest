package com.example.javaquest._19_security_basics.Lesson19_SecureLoggingAndAuditing;

public class _Exercises_Lesson19_SecureLoggingAndAuditing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDifferenceBetweenAppLogsAndAuditTrail {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij roznice miedzy zwyklymi logami
         * aplikacyjnymi a dziennikiem audytu bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListFieldsThatMustNeverBeLogged {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wypisz co najmniej 5 typow danych, ktorych
         * NIGDY nie wolno logowac w formie jawnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MaskCreditCardNumberBeforeLogging {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj metode maskujaca numer karty platniczej (widoczne
         * TYLKO ostatnie 4 cyfry) przed zalogowaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SanitizeNewlineCharactersBeforeLogging {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj funkcje sanityzujaca input przed zalogowaniem
         * (zamiana `\n`/`\r` na widoczne znaki) - przetestuj na 3 roznych
         * inputach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DemonstrateLogInjectionAttack {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj przyklad ataku log injection (falszywy wpis "udanego
         * logowania") - pokaz zarowno NIEBEZPIECZNA, jak i BEZPIECZNA
         * (zsanityzowana) wersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateSeparateLoggerForSecurityEvents {
        /*
         * 🧪 Zadanie 6:
         * Zaloz osobny `Logger` (inna nazwa niz domyslny logger
         * aplikacji) dedykowany WYLACZNIE zdarzeniom bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_LogAuthenticationSuccessAndFailureEvents {
        /*
         * 🧪 Zadanie 7:
         * Zaloguj (do konsoli lub pliku) 3 udane i 2 nieudane proby
         * logowania z rozroznieniem poziomu (`INFO`/`WARNING`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyStackTracesCanLeakSensitiveInfo {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, jak pelny stack trace zalogowany
         * uzytkownikowi koncowemu moze ujawnic wrazliwe informacje
         * (sciezki, wersje bibliotek, fragmenty zapytan SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteRealLogFileWithFileHandler {
        /*
         * 🧪 Zadanie 9:
         * Zapisz realny plik logu przez `java.util.logging.FileHandler`
         * do katalogu tymczasowego - wypisz sciezke i rozmiar pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ComputeSha256HashOfString {
        /*
         * 🧪 Zadanie 10:
         * Oblicz skrot SHA-256 (hex) dowolnego Stringa przy uzyciu
         * `MessageDigest` - zweryfikuj, ze ta sama tresc daje ZAWSZE ten
         * sam skrot.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementAuditEventRecord {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj rekord `AuditEvent` (aktor, akcja, zasob, wynik, czas)
         * i utworz 5 przykladowych zdarzen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementHashChainAppendMethod {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj metode dopisujaca zdarzenie do lancucha, liczaca
         * hash zalezny od TRESCI zdarzenia I hasha poprzednika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementChainVerificationMethod {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj metode `verifyChain` przechodzaca caly lancuch i
         * porownujaca przeliczone skroty z zapisanymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateTamperDetectionOnModifiedEvent {
        /*
         * 🧪 Zadanie 14:
         * Zmien jedno pole w srodkowym zdarzeniu lancucha (bez
         * przeliczania hasha) - zweryfikuj, ze `verifyChain` wykrywa
         * manipulacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementAuditLoggerWithStructuredFormat {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj logger audytu zapisujacy zdarzenia w formacie
         * ustrukturyzowanym (np. `klucz=wartosc` oddzielone spacja) -
         * latwym do parsowania przez narzedzia SIEM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LogPermissionDeniedEventsWithContext {
        /*
         * 🧪 Zadanie 16:
         * Zaloguj zdarzenia "odmowa dostepu" (403) z pelnym kontekstem
         * (kto, do jakiego zasobu, jakiej roli brakowalo) - powiaz z
         * `Lesson07_AuthorizationPatternsAndRbac`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementLogRotationSimulation {
        /*
         * 🧪 Zadanie 17:
         * Zasymuluj rotacje pliku logu (po przekroczeniu limitu linii,
         * zacznij pisac do NOWEGO pliku z sufiksem) - wyjasnij, po co
         * sluzy rotacja logow w produkcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DetectSuspiciousPatternInAuditLog {
        /*
         * 🧪 Zadanie 18:
         * Przeanalizuj liste zdarzen logowania i wykryj podejrzany wzorzec
         * (np. 5 nieudanych prob logowania tego samego uzytkownika w
         * krotkim czasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareMutableLogFileWithHashChainedLog {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj zwykly plik tekstowy logu (latwy do
         * cichej edycji) z lancuchem skrotow (tamper-evident) - kiedy
         * warto uzyc ktorego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementCorrelationIdAcrossAuditEvents {
        /*
         * 🧪 Zadanie 20:
         * Dodaj pole `correlationId` do `AuditEvent`, laczace WSZYSTKIE
         * zdarzenia nalezace do JEDNEGO zadania - powiaz z MDC z
         * `_13_libraries/Lesson17`.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullAuditTrailForUserLifecycle {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj lancuch audytu obejmujacy caly "cykl zycia" uzytkownika
         * (rejestracja, logowanie, zmiana uprawnien, usuniecie konta) -
         * zweryfikuj integralnosc na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementPeriodicChainCheckpointing {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj mechanizm "checkpointu" - co N zdarzen zapisz hash
         * calego lancucha do OSOBNEGO, zaufanego miejsca (symulacja
         * zewnetrznego, niezaleznego magazynu skrotow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DetectDeletedEventFromChain {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj lancuch, nastepnie USUN jedno zdarzenie ze srodka listy
         * (bez przeliczania) - zweryfikuj, ze `verifyChain` (lub jej
         * rozszerzona wersja) wykrywa BRAKUJACY link w lancuchu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAsyncAuditLoggingWithoutBlockingMainFlow {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj logowanie audytu na OSOBNYM watku (np. przez
         * `ExecutorService`, patrz `_05_multithreading`), zeby zapis logu
         * nie spowalnial glownej logiki biznesowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAlertingRuleOnRepeatedFailures {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj prosta regule alertujaca (wypisujaca ostrzezenie),
         * gdy WYKRYTO co najmniej 3 zdarzenia `AUTH_FAILURE` tego samego
         * IP w ciagu symulowanej minuty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainSiemIntegrationConceptually {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wyjasnij, czym jest SIEM (Security Information
         * and Event Management) i jak ustrukturyzowany dziennik audytu
         * (Zadanie 15) ulatwia integracje z takim systemem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRedactionLayerForArbitraryLogMessages {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj warstwe "redakcji" - metoda przyjmujaca DOWOLNY
         * komunikat i automatycznie maskujaca wzorce wygladajace jak
         * haslo/e-mail/numer karty PRZED przekazaniem do loggera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareCentralizedVsPerServiceAuditLogs {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: w architekturze modularnego monolitu/mikroserwisow
         * (patrz `_17_architecture/Lesson17-18`) - porownaj scentralizowany
         * dziennik audytu z osobnymi dziennikami per modul/serwis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementImmutableAppendOnlyAuditStore {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj magazyn audytu "append-only" (brak metod
         * modyfikujacych/usuwajacych istniejace wpisy w publicznym API) -
         * wyjasnij, dlaczego to WLASCIWOSC architektoniczna, nie tylko
         * konwencja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteTamperEvidentAuditSystem {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system audytu laczacy: lancuch skrotow (Zadanie
         * 12-13), redakcje wrazliwych danych (Zadanie 27), wykrywanie
         * podejrzanych wzorcow (Zadanie 18/25), zapis do realnego pliku -
         * zweryfikuj integralnosc po symulowanej probie manipulacji.
         */
        public static void main(String[] args) { }
    }
}
