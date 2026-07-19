package com.example.javaquest._24_spring_security.Lesson01_WhatIsSpringSecurity;

public class _Exercises_Lesson01_WhatIsSpringSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAuthenticationVsAuthorizationRecap {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: powiaz z `_19_security_basics/Lesson01` -
         * przypomnij roznice miedzy uwierzytelnianiem A autoryzacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddSpringBootStarterSecurityToNewController {
        /*
         * 🧪 Zadanie 2:
         * Dodaj DRUGI `@RestController` Z WLASNYM endpointem DO tej
         * lekcji I zweryfikuj, ze TEZ jest domyslnie zabezpieczony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ObserveRandomPasswordInLogs {
        /*
         * 🧪 Zadanie 3:
         * USUN `spring.security.user.password` Z Properties I
         * zaobserwuj, gdzie Spring Boot WYPISUJE losowo wygenerowane
         * haslo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestWithMissingAuthorizationHeader {
        /*
         * 🧪 Zadanie 4:
         * Wyslij zadanie CALKOWICIE BEZ naglowka `Authorization` I
         * sprawdz DOKLADNA tresc odpowiedzi 401.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TestWithMalformedBasicAuthHeader {
        /*
         * 🧪 Zadanie 5:
         * Wyslij naglowek `Authorization: Basic zle-base64` I sprawdz
         * status odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ChangeDefaultUsername {
        /*
         * 🧪 Zadanie 6:
         * Zmien `spring.security.user.name` NA WLASNA nazwe I
         * zweryfikuj, ze STARA nazwa "user" JUZ NIE dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InspectWwwAuthenticateHeader {
        /*
         * 🧪 Zadanie 7:
         * Odczytaj naglowek `WWW-Authenticate` Z odpowiedzi 401 -
         * wyjasnij JEGO ZNACZENIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareWithRawServletFilterFromChapter07 {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: powiaz z `_07_servlets/Lesson14_Filters` -
         * porownaj `FilterChainProxy` Z WLASNYM `Filter` napisanym
         * TAMTEJ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhySecurityRunsBeforeDispatcherServlet {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO Security MUSI dzialac
         * PRZED `DispatcherServlet`, NIE PO nim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWhatSpringSecurityAutomatesFromChapter19 {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz MIN. 4 mechanizmy Z `_19_security_basics`,
         * KTORE Spring Security AUTOMATYZUJE (do rozwiniecia W kolejnych
         * lekcjach).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ExplainWhyGlobalExcludePropertyExists {
        /*
         * 🧪 Zadanie 11:
         * Bez terminala: wyjasnij, PO CO `application.properties` tego
         * projektu domyslnie WYLACZA auto-konfiguracje Security (patrz
         * komentarz W `application.properties`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RemoveOverrideAndObserveOtherLessonBreaking {
        /*
         * 🧪 Zadanie 12:
         * TYMCZASOWO usun `spring.autoconfigure.exclude=""` Z TEJ
         * lekcji I zaobserwuj, ze `/hello` DALEJ dziala (bo globalne
         * wylaczenie WCIAZ obowiazuje) - PRZYWROC nadpisanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddSecondEndpointAndTestBothWithSameCredentials {
        /*
         * 🧪 Zadanie 13:
         * Dodaj DRUGI endpoint I zweryfikuj, ze TE SAME poswiadczenia
         * dzialaja DLA OBU (Security dziala NA POZIOMIE aplikacji, NIE
         * pojedynczego endpointu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureLatencyOverheadOfSecurityFilterChain {
        /*
         * 🧪 Zadanie 14:
         * Porownaj CZAS odpowiedzi zabezpieczonego I NIEZABEZPIECZONEGO
         * (`_22_spring_web`) endpointu - oszacuj NARZUT filtrow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainDefaultInMemoryUserLimitation {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij, DLACZEGO domyslny, JEDEN wbudowany
         * uzytkownik NIE nadaje sie DO produkcji (zapowiedz Lesson05
         * `UserDetailsService`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestConcurrentRequestsWithSameCredentials {
        /*
         * 🧪 Zadanie 16:
         * Wyslij 5 ROWNOLEGLYCH zadan Z tymi samymi poswiadczeniami -
         * zweryfikuj, ze WSZYSTKIE dostaja 200.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainStatelessNatureOfBasicAuth {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, DLACZEGO HTTP Basic Auth jest
         * BEZSTANOWY (poswiadczenia WYSYLANE PRZY KAZDYM zadaniu) - w
         * odroznieniu OD sesji (`_19_security_basics/Lesson04`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareBasicAuthWithJwtFromChapter19 {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: porownaj HTTP Basic Auth (tu) Z JWT
         * (`_19_security_basics/Lesson05`) - kompromisy OBU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestWithUppercaseAuthorizationHeaderName {
        /*
         * 🧪 Zadanie 19:
         * Zweryfikuj, czy nazwa naglowka `Authorization` jest
         * case-insensitive (zgodnie ze specyfikacja HTTP).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentSecurityBehaviorForTeam {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz KROTKA notatke (jak ADR Z
         * `_17_architecture/Lesson02`) opisujaca, DLACZEGO ten projekt
         * domyslnie WYLACZA Security dla wiekszosci lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InspectFilterChainProxyViaDebugger {
        /*
         * 🧪 Zadanie 21:
         * Ustaw punkt przerwania (debugger) W `FilterChainProxy` I
         * zbadaj REALNA liste filtrow W lancuchu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_EnableDebugLoggingForSecurityFilterChain {
        /*
         * 🧪 Zadanie 22:
         * Ustaw `logging.level.org.springframework.security=DEBUG` I
         * przeanalizuj PELNY log przetwarzania jednego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareOverheadOfSecurityVsNoSecurityAtScale {
        /*
         * 🧪 Zadanie 23:
         * Wykonaj 1000 zadan Z Security I 1000 BEZ - zmierz SREDNI
         * narzut latencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExplainWhySecurityIsAutoconfiguredNotManual {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: powiaz z `_21_spring_boot/Lesson04_
         * AutoConfiguration` - wyjasnij MECHANIZM, KTORY sprawia, ze
         * SAMO dodanie zaleznosci WLACZA zabezpieczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignMultiModuleSecurityExclusionStrategy {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: zaprojektuj, jak WYGLADALABY ta sama sytuacja
         * (jedna zaleznosc Security psujaca inne moduly) W PRAWDZIWYM,
         * WIELOMODULOWYM projekcie Maven (osobne artefakty ZAMIAST
         * jednego classpath) - powiazanie Z `_11_buildtools`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestSecurityWithHttpsSimulatedHeaders {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson08_HttpsTlsBasics` -
         * opisz, CZY/JAK Security wplywa NA wymog HTTPS (osobno OD
         * uwierzytelniania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ProfileSecurityFilterChainExecutionTime {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_15_jvm_internals/Lesson19_ProfilingBasics` -
         * zmierz, ILE czasu (W mikrosekundach) zajmuje SAM lancuch
         * filtrow Security NA jedno zadanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareWithOAuth2ResourceServerFilterChain {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zapowiedz Lesson15 - jak DODATKOWY filtr
         * `BearerTokenAuthenticationFilter` rozni sie OD tego, co
         * widziales W TEJ lekcji (Basic Auth).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignHealthCheckExemptFromSecurity {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zaprojektuj, JAK endpoint `/actuator/health`
         * (powiazanie Z `_21_spring_boot/Lesson12`) POWINIEN byc
         * dostepny BEZ uwierzytelnienia, mimo ze Security jest wlaczony
         * (zapowiedz Lesson09 `ProtectingEndpoints`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteMigrationChecklistForAddingSecurityToExistingApp {
        /*
         * 🧪 Zadanie 30:
         * Bez terminala: napisz CHECKLISTE krokow POTRZEBNYCH DO
         * bezpiecznego dodania `spring-boot-starter-security` DO
         * ISTNIEJACEJ, dzialajacej JUZ aplikacji (bez zepsucia
         * istniejacych endpointow) - wykorzystaj DOSWIADCZENIE Z
         * naprawy regresji W TYM projekcie (patrz `application.properties`
         * I CLAUDE.md).
         */
        public static void main(String[] args) { }
    }
}
