package com.example.javaquest._24_spring_security.Lesson09_ProtectingEndpoints;

public class _Exercises_Lesson09_ProtectingEndpoints {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDenyAllVsAuthenticatedFallback {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij ROZNICE miedzy `.anyRequest().denyAll()`
         * A `.anyRequest().authenticated()` jako FALLBACK.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddDeleteEndpointAdminOnly {
        /*
         * 🧪 Zadanie 2:
         * Dodaj `DELETE /api/products/{id}` chroniony `hasRole("ADMIN")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddPutEndpointAdminOnly {
        /*
         * 🧪 Zadanie 3:
         * Dodaj `PUT /api/products/{id}` chroniony `hasRole("ADMIN")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestGetOrdersWithoutAuth {
        /*
         * 🧪 Zadanie 4:
         * Wyslij GET `/api/orders/5` BEZ poswiadczen - sprawdz status.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddSecondPublicReadOnlyEndpoint {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `GET /api/categories` (publiczny, jak `/api/products`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReorderRulesAndObserveBreakage {
        /*
         * 🧪 Zadanie 6:
         * ZAMIEN kolejnosc regul GET/POST DLA `/api/products` -
         * zaobserwuj ZMIANE zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestFallbackWithAuthenticatedUser {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj, ze NAWET zalogowany "klient" DOSTAJE 403 NA
         * nieznanej sciezce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareWithLesson02SimplerRules {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: porownaj TA lekcje Z Lesson02 - CO SIE
         * ZMIENILO (metody HTTP, fallback).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestPostWithoutRoleGetsForbidden {
        /*
         * 🧪 Zadanie 9:
         * Zweryfikuj DOKLADNIE (401 vs 403) roznice DLA "brak logowania"
         * vs "zalogowany, brak roli" NA `POST /api/products`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentEndpointSecurityMatrix {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: udokumentuj macierz WSZYSTKICH endpointow
         * lekcji I ich wymagan.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddPatchEndpointWithDifferentRole {
        /*
         * 🧪 Zadanie 11:
         * Dodaj `PATCH /api/products/{id}` chroniony INNA rola (np.
         * "EDITOR").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPathVariableBasedAuthorization {
        /*
         * 🧪 Zadanie 12:
         * Zaprojektuj regule, W KTOREJ `/api/orders/{id}` sprawdza, CZY
         * `{id}` NALEZY DO zalogowanego uzytkownika (nie tylko
         * `authenticated()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddStaticResourcesRule {
        /*
         * 🧪 Zadanie 13:
         * Dodaj regule `permitAll()` DLA statycznych zasobow (`/css/**`,
         * `/js/**`) - powiazanie Z Lesson11 (custom login page).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestMultipleRolesOnSamePathDifferentMethods {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj TA SAMA sciezke Z 3 ROZNYMI regulami (GET/POST/
         * DELETE, KAZDA Z INNA rola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhyMoreSpecificRulesMustComeFirst {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij, DLACZEGO bardziej SZCZEGOLOWE
         * regoly (np. konkretna metoda) MUSZA byc PRZED ogolniejszymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddHealthCheckEndpointPubliclyAccessible {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_21_spring_boot/Lesson12` - dodaj `/health`
         * publiczny, mimo `denyAll()` fallback.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestAllHttpMethodsOnSinglePath {
        /*
         * 🧪 Zadanie 17:
         * Przetestuj WSZYSTKIE metody HTTP (GET/POST/PUT/DELETE/PATCH)
         * NA `/api/products` I zbierz WYNIKI W TABELI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementRegexBasedPathMatching {
        /*
         * 🧪 Zadanie 18:
         * Uzyj `regexMatchers(...)` (LUB odpowiednika) DO bardziej
         * ZLOZONEGO wzorca sciezki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestWithMultipleQueryParametersOnPublicEndpoint {
        /*
         * 🧪 Zadanie 19:
         * Zweryfikuj, ze `GET /api/products?sort=price&page=2` DALEJ
         * jest PUBLICZNY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignSecurityRulesForNestedResourcePaths {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj regoly DLA ZAGNIEZDZONYCH zasobow
         * (`/api/orders/{id}/items/{itemId}`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFineGrainedResourceLevelAuthorization {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z Lesson10 (`MethodSecurity`) - zaimplementuj
         * autoryzacje NA POZIOMIE metody serwisu (NIE tylko URL), DLA
         * `/api/orders/{id}`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BenchmarkRuleEvaluationOrderPerformance {
        /*
         * 🧪 Zadanie 22:
         * Porownaj WYDAJNOSC, gdy CZESTO uzywana regula jest
         * PIERWSZA vs OSTATNIA W liscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignApiGatewayStyleAuthorizationLayer {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: powiaz z `_17_architecture` - zaprojektuj,
         * jak TE SAME regoly WYGLADALYBY NA POZIOMIE API Gateway
         * (przed mikroserwisami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementConditionalAuthorizationBasedOnTime {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj `AuthorizationManager` ZEZWALAJACY NA
         * `POST /api/products` TYLKO W GODZINACH pracy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TestSecurityRulesUnderConcurrentModification {
        /*
         * 🧪 Zadanie 25:
         * Wyslij ROWNOLEGLE 10 zadan POST OD ADMIN I 10 OD USER -
         * zweryfikuj POPRAWNA izolacje wynikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignZeroTrustFallbackStrategy {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wyjasnij zasade "zero trust" NA PRZYKLADZIE
         * `denyAll()` fallback - DLACZEGO to LEPSZA PRAKTYKA NIZ
         * poleganie NA `authenticated()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementAuditLogForEveryAuthorizationDecision {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - loguj KAZDA
         * decyzje autoryzacyjna (dopuszczona/odrzucona) Z pelnym
         * kontekstem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignSecurityTestSuiteCoveringAllRules {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zaprojektuj PELNY zestaw testow POKRYWAJACY
         * WSZYSTKIE kombinacje regul Z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareUrlBasedWithMethodBasedSecurity {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: powiaz z Lesson10 - porownaj zabezpieczenie NA
         * POZIOMIE URL (tu) Z zabezpieczeniem NA POZIOMIE metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteEcommerceSecurityRuleSet {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY zestaw regul DLA sklepu (katalog, koszyk,
         * zamowienia, panel administracyjny, panel platnosci) Z co
         * najmniej 6 ROZNYMI poziomami dostepu.
         */
        public static void main(String[] args) { }
    }
}
