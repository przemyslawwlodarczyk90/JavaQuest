package com.example.javaquest._24_spring_security.Lesson17_SpringSecurityCapstone;

public class _Exercises_Lesson17_SpringSecurityCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllMechanismsUsedInCapstone {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz WSZYSTKIE mechanizmy Spring Security
         * uzyte W kapsztonie (min. 8) - DLA KAZDEGO podaj lekcje,
         * W KTOREJ byl wprowadzony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddThirdUserWithModeratorRole {
        /*
         * 🧪 Zadanie 2:
         * Dodaj TRZECIEGO uzytkownika Z rola "MODERATOR".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddModeratorOnlyEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Dodaj endpoint `/api/moderator/queue` chroniony
         * `@PreAuthorize("hasRole('MODERATOR')")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestLoginWithUnknownUsername {
        /*
         * 🧪 Zadanie 4:
         * Sprobuj zalogowac NIEZNANEGO uzytkownika - zaobserwuj (I
         * NAPRAW, jesli trzeba) zachowanie `UsernameNotFoundException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddLogoutEquivalentForJwt {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO "wylogowanie" W API JWT
         * ROZNI SIE OD wylogowania sesyjnego (Lesson04/08).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestCorsWithAllowedOrigin {
        /*
         * 🧪 Zadanie 6:
         * Wyslij zadanie Z DOZWOLONYM `Origin` - zweryfikuj status 200.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddSecondPublicEndpoint {
        /*
         * 🧪 Zadanie 7:
         * Dodaj DRUGI publiczny endpoint `/api/public/health`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestReportServiceDirectlyBypassingUrl {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, DLACZEGO `@PreAuthorize` NA
         * `ReportService` chroni RAPORT, NAWET GDYBY URL rule byla
         * `permitAll()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DocumentFullEndpointMatrix {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: udokumentuj macierz WSZYSTKICH endpointow I
         * WYMAGANYCH rol/tokenow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareCodeVolumeWithChapter19Capstone {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: policz W PRZYBLIZENIU linie kodu TEGO
         * kapsztonu I porownaj Z `_19_security_basics/Lesson21`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementRefreshTokenEndpoint {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z Lesson12 - dodaj endpoint `/api/refresh` wydajacy
         * NOWY token NA PODSTAWIE STAREGO (jeszcze wazncgo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddAuditLoggingForAllAuthEvents {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_19_security_basics/Lesson19` - loguj KAZDE
         * logowanie (udane/nieudane) I KAZDA odmowe dostepu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementRateLimitingOnLoginEndpoint {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_18_rest_api/Lesson16` - dodaj limit prob
         * logowania NA `/api/login`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddSecondReportTypeWithDifferentPermission {
        /*
         * 🧪 Zadanie 14:
         * Dodaj DRUGI typ raportu Z INNYM wymaganym uprawnieniem
         * (`hasAuthority("REPORTS_FINANCIAL")` ZAMIAST roli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementSwaggerDocumentationForSecureApi {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_22_spring_web/Lesson18` - opisz, JAK
         * udokumentowac WYMOG tokenu Bearer W OpenAPI/Swagger.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestConcurrentLoginsFromDifferentUsers {
        /*
         * 🧪 Zadanie 16:
         * Wyslij 10 ROWNOLEGLYCH logowan (mix 'kasia'/'dyrektor') -
         * zweryfikuj POPRAWNOSC WSZYSTKICH tokenow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementPasswordChangeEndpoint {
        /*
         * 🧪 Zadanie 17:
         * Dodaj `POST /api/change-password` (wymaga uwierzytelnienia,
         * weryfikuje STARE haslo PRZED zapisaniem NOWEGO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddHealthEndpointExemptFromAuth {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_21_spring_boot/Lesson12` - dodaj `/actuator/health`
         * PUBLICZNY, mimo `authenticated()` fallback.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementTokenClaimsForEmailAndDepartment {
        /*
         * 🧪 Zadanie 19:
         * Dodaj DODATKOWE claims (email, dzial) DO tokenu I ODCZYTAJ
         * JE W kontrolerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignApiVersioningForSecureEndpoints {
        /*
         * 🧪 Zadanie 20:
         * Powiaz z `_18_rest_api/Lesson14_Versioning` - zaprojektuj
         * `/api/v2/**` Z INNYMI regulami bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MigrateToRealDatabaseBackedUserStore {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_23_spring_data_jpa` - zastap
         * `InMemoryUserDetailsManager` PRAWDZIWYM repozytorium JPA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullOAuth2ResourceServerVariant {
        /*
         * 🧪 Zadanie 22:
         * Przepisz kapszton, ZEBY uzywal `.oauth2ResourceServer(...)`
         * (Lesson15) ZAMIAST WLASNEGO filtra (Lesson12) - porownaj
         * ILOSC kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AddMethodSecurityForOwnershipCheck {
        /*
         * 🧪 Zadanie 23:
         * Dodaj `@PreAuthorize("#username == authentication.name")`
         * DO NOWEGO endpointu "WLASNE dane" (powiazanie Z Lesson10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignMultiTenantSecureApi {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: zaprojektuj wersje TEGO API DLA WIELU
         * tenantow (izolacja danych I uprawnien PER tenant).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFullIntegrationTestSuite {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: zaprojektuj PELNY zestaw testow integracyjnych
         * POKRYWAJACY WSZYSTKIE 9 scenariuszy Z tej lekcji (zapowiedz
         * przyszlego rozdzialu o testowaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareWithSpringSecurityBestPracticesChecklist {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: skonfrontuj TEN kapszton Z oficjalna
         * checklista OWASP ASVS - CO jeszcze BRAKUJE DO produkcji
         * (np. rate limiting, monitoring, rotacja kluczy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignZeroDowntimeKeyRotationForCapstone {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z Lesson13 Zadanie 26 - zaprojektuj ROTACJE klucza
         * JWT DLA TEGO KONKRETNEGO API BEZ przestoju.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementFullObservabilityForSecurityEvents {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_21_spring_boot/Lesson13` - dodaj METRYKI
         * Micrometer DLA logowan/odmow dostepu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignMigrationFromCapstoneToRealMicroservice {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: opisz, CO TRZEBA BY zmienic, ZEBY TEN
         * kapszton stal sie CZESCIA prawdziwej architektury
         * mikroserwisowej (powiazanie Z `_17_architecture/Lesson19`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildExtendedCapstoneWithAllExercisesCombined {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj ROZSZERZONA wersje kapsztonu LACZACA WYNIKI Zadan
         * 2-29 (min. 6) W JEDNYM, spojnym demo Z WLASNYM raportem
         * podsumowujacym NA koniec - PODSUMUJ, jak TA wersja rozni sie
         * OD `_19_security_basics/Lesson21`.
         */
        public static void main(String[] args) { }
    }
}
