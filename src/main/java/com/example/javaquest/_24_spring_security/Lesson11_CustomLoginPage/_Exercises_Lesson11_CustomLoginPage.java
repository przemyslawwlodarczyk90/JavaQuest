package com.example.javaquest._24_spring_security.Lesson11_CustomLoginPage;

public class _Exercises_Lesson11_CustomLoginPage {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainLoginPageOption {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, CO robi `.loginPage("/login")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ChangeCustomLoginPagePath {
        /*
         * 🧪 Zadanie 2:
         * Zmien sciezke strony logowania NA `/wlasne-logowanie`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddCustomStylingToLoginPage {
        /*
         * 🧪 Zadanie 3:
         * Dodaj PROSTY CSS (INLINE `<style>`) DO strony logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestMissingCsrfTokenOnCustomPage {
        /*
         * 🧪 Zadanie 4:
         * USUN pole `_csrf` Z WLASNEGO formularza I zweryfikuj status
         * 403.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyPermitAllNeededOnLoginPage {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO `.loginPage(...)` MUSI byc
         * OZNACZONA `permitAll()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddErrorMessageDisplayOnLoginPage {
        /*
         * 🧪 Zadanie 6:
         * Dodaj obsluge parametru `?error` W WLASNEJ stronie (komunikat
         * "Bledne dane logowania").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareWithDefaultGeneratedPageFromLesson04 {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: porownaj TA lekcje Z Lesson04 - CO ZYSKUJESZ,
         * a CO MUSISZ zrobic SAM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestLoginPageAccessibleWithoutSession {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, ze `/login` jest dostepny BEZ ISTNIEJACEJ sesji
         * (nowy `HttpClient` bez cookie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainCsrfTokenAutoInjectionMechanism {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, jak Spring MVC ROZPOZNAJE typ
         * `CsrfToken` I AUTOMATYCZNIE go wstrzykuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AddLogoLinkToHomepage {
        /*
         * 🧪 Zadanie 10:
         * Dodaj LINK "Powrot NA strone glowna" DO WLASNEGO formularza.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddRegistrationLinkToLoginPage {
        /*
         * 🧪 Zadanie 11:
         * Dodaj DRUGA sciezke `/register` (publiczna) I LINK DO NIEJ Z
         * `/login`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExtractHtmlGenerationToSeparateMethod {
        /*
         * 🧪 Zadanie 12:
         * Wydziel GENEROWANIE HTML DO OSOBNEJ, TESTOWALNEJ metody
         * (`String buildLoginHtml(String csrfParam, String csrfValue)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseJavaTextBlockForHtml {
        /*
         * 🧪 Zadanie 13:
         * Przepisz konkatenacje String NA text block (`"""..."""`) DLA
         * CZYTELNOSCI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRememberMeCheckboxOnLoginPage {
        /*
         * 🧪 Zadanie 14:
         * Dodaj checkbox "Zapamietaj mnie" DO formularza I
         * `.rememberMe(...)` DO konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestCustomLoginPageWithRedirectParameter {
        /*
         * 🧪 Zadanie 15:
         * Zaladuj chroniony zasob BEZ logowania, przechwyc parametr
         * przekierowania I zweryfikuj powrot PO zalogowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareRawHtmlWithThymeleafApproach {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: opisz, JAK TA SAMA strona wygladalaby Z
         * Thymeleaf (`th:action`, `${_csrf.token}`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AddClientSideValidationToForm {
        /*
         * 🧪 Zadanie 17:
         * Dodaj atrybuty HTML5 `required`/`minlength` DO pol formularza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementDarkModeToggleOnLoginPage {
        /*
         * 🧪 Zadanie 18:
         * Dodaj PROSTY przelacznik trybu ciemnego (CSS + odrobina JS)
         * DO strony logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestLoginPageResponseContentType {
        /*
         * 🧪 Zadanie 19:
         * Zweryfikuj naglowek `Content-Type` odpowiedzi `/login` -
         * CZY jest `text/html`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementMultiLanguageLoginPage {
        /*
         * 🧪 Zadanie 20:
         * Dodaj obsluge parametru `?lang=pl/en` ZMIENIAJACEGO JEZYK
         * formularza.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCsrfTokenViaCookieRepository {
        /*
         * 🧪 Zadanie 21:
         * Skonfiguruj `CookieCsrfTokenRepository` (ZAMIAST domyslnego,
         * sesyjnego) - powiazanie Z SPA/JavaScript.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignAccessibleLoginFormWithAriaLabels {
        /*
         * 🧪 Zadanie 22:
         * Dodaj atrybuty ARIA (`aria-label`, `role`) DLA
         * dostepnosci (a11y).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRateLimitedLoginPageRendering {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_18_rest_api/Lesson16` - dodaj limit ODWIEDZIN
         * strony logowania Z JEDNEGO adresu IP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareServerRenderedWithSpaLoginApproach {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: porownaj TEN model (server-rendered form) Z
         * podejsciem SPA (formularz W JavaScript, API `/api/login`
         * zwracajace JSON).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementLoginPageWithSocialLoginButtons {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: opisz, jak DODAC przycisk "Zaloguj sie PRZEZ
         * Google" DO tej strony (zapowiedz Lesson15 OAuth2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignContentSecurityPolicyForLoginPage {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson12_SecurityHeaders` -
         * dodaj naglowek `Content-Security-Policy` DO odpowiedzi
         * `/login`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementServerSideTemplatingWithoutNewDependency {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj PROSTY, WLASNY silnik podstawiania zmiennych W
         * szablonie HTML (BEZ Thymeleaf) - `{{zmienna}}` -> wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestLoginPageUnderHighConcurrency {
        /*
         * 🧪 Zadanie 28:
         * Wyslij 50 ROWNOLEGLYCH zadan GET `/login` - zweryfikuj, ze
         * KAZDE dostaje UNIKALNY token CSRF.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignProgressiveEnhancementForLoginForm {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zaprojektuj formularz DZIALAJACY zarowno BEZ
         * JavaScript, JAK I Z ulepszeniami JS (progressive enhancement).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullBrandedLoginExperienceWithLogoutPage {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNE doswiadczenie: WLASNA strona logowania +
         * WLASNA strona "wylogowano" + WLASNA strona bledu 403 - Z
         * SPOJNYM stylem.
         */
        public static void main(String[] args) { }
    }
}
