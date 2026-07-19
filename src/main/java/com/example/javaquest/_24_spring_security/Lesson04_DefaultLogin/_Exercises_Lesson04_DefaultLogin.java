package com.example.javaquest._24_spring_security.Lesson04_DefaultLogin;

public class _Exercises_Lesson04_DefaultLogin {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyBothHttpBasicAndFormLoginAreEnabled {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, DLACZEGO domyslnie WLACZONE SA OBA
         * mechanizmy (httpBasic I formLogin) jednoczesnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TestLoginPageInBrowser {
        /*
         * 🧪 Zadanie 2:
         * URUCHOM te lekcje BEZ zamykania kontekstu (dodaj
         * `Thread.sleep`) I otworz `/login` W przegladarce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestLoginWithWrongPassword {
        /*
         * 🧪 Zadanie 3:
         * Wyslij POST `/login` Z BLEDNYM haslem - sprawdz status I
         * `Location` odpowiedzi (oczekiwane: `/login?error`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestAccessingProtectedResourceWithoutLogin {
        /*
         * 🧪 Zadanie 4:
         * Wyslij GET `/api/hello` BEZ wczesniejszego logowania - sprawdz
         * DOKLADNY status I naglowek `Location` (przekierowanie DO `/login`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainCsrfHiddenField {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, PO CO formularz logowania zawiera
         * ukryte pole `_csrf`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestLoginWithoutCsrfToken {
        /*
         * 🧪 Zadanie 6:
         * SPROBUJ zalogowac sie BEZ pola `_csrf` W formularzu -
         * zweryfikuj status 403.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareSessionCookieNameAcrossServers {
        /*
         * 🧪 Zadanie 7:
         * Sprawdz NAZWE ciasteczka sesji (`JSESSIONID`) I porownaj Z
         * `_19_security_basics/Lesson04` (recznie napisana sesja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestSecondRequestReusingSessionCookie {
        /*
         * 🧪 Zadanie 8:
         * Wyslij DRUGIE zadanie DO INNEGO chronionego endpointu W TEJ
         * SAMEJ sesji - zweryfikuj, ze NIE trzeba logowac sie PONOWNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddLogoutEndpointTest {
        /*
         * 🧪 Zadanie 9:
         * Wyslij POST `/logout` (domyslnie dostepny) I zweryfikuj, ze
         * sesja PRZESTAJE dawac dostep.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareWithLesson01BasicAuthApproach {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj TA lekcje Z Lesson01 - KIEDY uzyc
         * formularza, A KIEDY Basic Auth.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ExtractSessionCookieManually {
        /*
         * 🧪 Zadanie 11:
         * Odczytaj I wypisz WARTOSC ciasteczka sesji BEZPOSREDNIO Z
         * `CookieManager` (BEZ polegania na automatycznej obsludze
         * `HttpClient`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestCsrfTokenRotationAfterLogin {
        /*
         * 🧪 Zadanie 12:
         * Sprawdz, CZY token CSRF ZMIENIA SIE PO udanym zalogowaniu
         * (nowa sesja = nowy token?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestConcurrentLoginsWithDifferentSessions {
        /*
         * 🧪 Zadanie 13:
         * Uzyj 2 NIEZALEZNYCH `HttpClient` (2 rozne `CookieManager`) -
         * zaloguj sie NIEZALEZNIE W obu I zweryfikuj IZOLACJE sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CustomizeDefaultRedirectAfterLogin {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `.defaultSuccessUrl("/api/hello", true)` NA `formLogin()`
         * I zweryfikuj ZMIANE celu przekierowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhyRedirectInsteadOfDirectResponse {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij, DLACZEGO Spring Security PRZEKIEROWUJE
         * (302) PO logowaniu, ZAMIAST zwrocic tresc BEZPOSREDNIO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestFormLoginWithMissingUsernameField {
        /*
         * 🧪 Zadanie 16:
         * Wyslij POST `/login` BEZ pola `username` - sprawdz zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InspectDefaultLoginPageHtmlStructure {
        /*
         * 🧪 Zadanie 17:
         * Wypisz PELNY HTML strony `/login` I zidentyfikuj WSZYSTKIE
         * pola formularza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareRegexCsrfExtractionWithProperHtmlParsing {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_06_networking/Lesson13_XmlParsing`/Jsoup
         * (`_13_libraries/Lesson25`) - zastap regex WLASCIWYM parserem
         * HTML DO wyciagania tokenu CSRF.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestLoginErrorMessageContent {
        /*
         * 🧪 Zadanie 19:
         * Odczytaj strone `/login?error` PO nieudanym logowaniu -
         * sprawdz, CZY zawiera komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureSessionTimeout {
        /*
         * 🧪 Zadanie 20:
         * Sprawdz DOMYSLNY czas wygasniecia sesji (`server.servlet.
         * session.timeout`) I zmien go NA WLASNA wartosc.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementRememberMeFunctionality {
        /*
         * 🧪 Zadanie 21:
         * Dodaj `.rememberMe(Customizer.withDefaults())` I zweryfikuj
         * ciasteczko "remember-me" PO zalogowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestSessionFixationProtection {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wyjasnij, jak Spring Security CHRONI PRZED
         * atakiem "session fixation" (powiazanie Z
         * `_19_security_basics/Lesson04`) - zweryfikuj EMPIRYCZNIE, ze
         * ID sesji ZMIENIA SIE PO zalogowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LimitConcurrentSessionsPerUser {
        /*
         * 🧪 Zadanie 23:
         * Skonfiguruj `maximumSessions(1)` I zweryfikuj, ze DRUGIE
         * logowanie WYLOGOWUJE pierwsza sesje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignCustomAuthenticationFailureHandler {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj WLASNY `AuthenticationFailureHandler` zwracajacy
         * JSON ZAMIAST przekierowania (zapowiedz Lesson16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareStatefulLoginWithStatelessJwtApproach {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: porownaj TEN sesyjny model (stateful) Z
         * podejsciem JWT (zapowiedz Lesson12-13, bezstanowe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestLoginPerformanceUnderLoad {
        /*
         * 🧪 Zadanie 26:
         * Zmierz czas 100 kolejnych logowan (osobne sesje) - oszacuj
         * narzut hashowania hasla (zapowiedz Lesson06 BCrypt).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomLoginSuccessEventListener {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_20_spring_core/Lesson20_ApplicationEvents` -
         * nasluchuj `AuthenticationSuccessEvent` I zaloguj UDANE
         * logowanie DO audytu (powiazanie Z
         * `_19_security_basics/Lesson19_SecureLoggingAndAuditing`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAccountLockoutAfterFailedAttempts {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zaprojektuj mechanizm BLOKADY konta PO 5
         * nieudanych probach (powiazanie Z `UserDetails.isAccountNonLocked()`,
         * zapowiedz Lesson05).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestCrossSiteRequestWithStolenCsrfToken {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: opisz, DLACZEGO SAMO posiadanie tokenu CSRF
         * (bez WAZNEJ sesji) NIE WYSTARCZY DO ataku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullLoginLogoutAuditTrail {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY scenariusz: logowanie -> dostep DO 2 zasobow ->
         * wylogowanie -> proba dostepu PO wylogowaniu (oczekiwane:
         * ODMOWA) - Z RAPORTEM KAZDEGO kroku.
         */
        public static void main(String[] args) { }
    }
}
