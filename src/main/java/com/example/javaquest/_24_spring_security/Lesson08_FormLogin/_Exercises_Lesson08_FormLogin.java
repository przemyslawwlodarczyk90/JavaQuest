package com.example.javaquest._24_spring_security.Lesson08_FormLogin;

public class _Exercises_Lesson08_FormLogin {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDefaultSuccessUrlSecondArgument {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij ROZNICE miedzy drugim argumentem
         * `true` A `false` W `.defaultSuccessUrl(url, bool)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ChangeDefaultSuccessUrlToNewPath {
        /*
         * 🧪 Zadanie 2:
         * Zmien `.defaultSuccessUrl(...)` NA INNA sciezke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestDefaultSuccessUrlFalseVariant {
        /*
         * 🧪 Zadanie 3:
         * Zmien drugi argument NA `false` I zweryfikuj INNE zachowanie
         * przekierowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ChangeFailureUrl {
        /*
         * 🧪 Zadanie 4:
         * Zmien `.failureUrl(...)` NA INNA sciezke I zweryfikuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TestLoginProcessingUrlCustomization {
        /*
         * 🧪 Zadanie 5:
         * Uzyj `.loginProcessingUrl("/perform-login")` (ZAMIAST
         * domyslnego `/login`) I zaktualizuj POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyRedirectAfterPost {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: powiaz z `_18_rest_api` - wyjasnij wzorzec
         * "POST-Redirect-GET".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestFailureUrlContainsErrorParam {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj DOKLADNA tresc parametru W URL PO nieudanym
         * logowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareWithDefaultBehaviorFromLesson04 {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: porownaj TA lekcje Z Lesson04 (BEZ jawnej
         * konfiguracji formLogin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestOriginalTargetPreservedWithFalseFlag {
        /*
         * 🧪 Zadanie 9:
         * Zaladuj chroniony zasob BEZ logowania (przekierowanie DO
         * `/login`), zaloguj sie Z `defaultSuccessUrl(url, false)` -
         * zweryfikuj powrot DO ORYGINALNEGO zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAllFormLoginCustomizationOptions {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz WSZYSTKIE metody konfiguracyjne
         * dostepne NA `FormLoginConfigurer` (dokumentacja Springa).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomAuthenticationSuccessHandler {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNY `AuthenticationSuccessHandler`
         * (ZAMIAST prostego URL) zwracajacy JSON zamiast przekierowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomAuthenticationFailureHandler {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj WLASNY `AuthenticationFailureHandler` LOGUJACY
         * nieudana probe DO audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestUsernameParameterCustomization {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `.usernameParameter("login")` (ZAMIAST domyslnego
         * "username") I zaktualizuj zadanie POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestPasswordParameterCustomization {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `.passwordParameter("haslo")` (ZAMIAST domyslnego
         * "password").
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CombineFormLoginWithHttpBasicSimultaneously {
        /*
         * 🧪 Zadanie 15:
         * Dodaj `.httpBasic(...)` OBOK `.formLogin(...)` I zweryfikuj,
         * ze OBA DZIALAJA JEDNOCZESNIE (jak W Lesson01/04 domyslnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestRedirectLoopPrevention {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij, DLACZEGO strona logowania MUSI byc
         * `permitAll()`, inaczej powstanie NIESKONCZONA petla
         * przekierowan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureLoginLatencyWithCsrfHandling {
        /*
         * 🧪 Zadanie 17:
         * Zmierz CALKOWITY czas przeplywu GET /login + POST /login +
         * GET zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestFormLoginWithSpecialCharactersInPassword {
        /*
         * 🧪 Zadanie 18:
         * Zaloguj sie haslem ZAWIERAJACYM znaki specjalne (`&`, `=`,
         * spacja) - zweryfikuj POPRAWNE kodowanie URL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementLoginAttemptCounter {
        /*
         * 🧪 Zadanie 19:
         * W `AuthenticationFailureHandler` licz KOLEJNE nieudane proby
         * NA username (zapowiedz blokady konta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareServerSideVsClientSideRedirectHandling {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: porownaj, jak PRZEGLADARKA (automatycznie)
         * vs `HttpClient` (recznie) OBSLUGUJE przekierowanie 302.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementJsonBasedFormLoginResponse {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj `AuthenticationSuccessHandler`/`FailureHandler`
         * zwracajace JSON (ZAMIAST przekierowania) - DLA API
         * konsumowanego przez SPA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMultiStepLoginFlow {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: zaprojektuj WIELOETAPOWY login (haslo +
         * kod SMS) NA bazie mechanizmow `formLogin`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCaptchaAfterFailedAttempts {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj mechanizm WYMAGAJACY CAPTCHA PO 3 nieudanych
         * probach (integracja Z `AuthenticationFailureHandler`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestFormLoginUnderConcurrentAttacks {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj 50 ROWNOLEGLYCH prob logowania (brute-force) -
         * zaobserwuj brak WBUDOWANEJ ochrony (zapowiedz rate limiting).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDeviceFingerprintingOnLogin {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: opisz, jak MOZNA by dolaczyc "fingerprint"
         * urzadzenia DO procesu logowania (WYKRYWANIE nowych urzadzen).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareFormLoginWithSpaAuthPatterns {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: porownaj klasyczny `formLogin` (server-side)
         * Z podejsciem SPA+JWT (zapowiedz Lesson12-13).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementLoginSuccessAuditWithChainedHashes {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - zapisz KAZDE UDANE
         * logowanie DO lancucha skrotow (tamper-evident audit trail).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignGracefulSessionMigrationOnRedeploy {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zaprojektuj, CO SIE DZIEJE Z aktywnymi sesjami
         * PRZY restarcie aplikacji (in-memory sesje vs zewnetrzny
         * magazyn).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementProgressiveDelayOnFailedLogins {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj ROSNACE opoznienie odpowiedzi PO KOLEJNYCH
         * nieudanych logowaniach (throttling).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullFormLoginFlowWithCustomHandlersAndAudit {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY przeplyw: WLASNE handlery sukcesu/porazki +
         * audyt + licznik prob - Z RAPORTEM na koniec.
         */
        public static void main(String[] args) { }
    }
}
