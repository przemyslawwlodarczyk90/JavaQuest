package com.example.javaquest._19_security_basics.Lesson21_OwaspTop10OverviewAndCapstone;

public class _Exercises_Lesson21_OwaspTop10OverviewAndCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllTenOwaspCategoriesFromMemory {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz WSZYSTKIE 10 kategorii OWASP Top 10
         * (2021) - mozesz sprawdzic z teoria, jesli utknales.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MapEachChapterLessonToOwaspCategory {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: dla kazdej lekcji 01-20 tego rozdzialu przypisz
         * (jesli pasuje) odpowiednia kategorie OWASP Top 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhySsrfHasNoOwnLesson {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij na czym polega SSRF i dlaczego ten
         * rozdzial celowo NIE ma dla niego osobnej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RunCapstoneServerAndInspectResponses {
        /*
         * 🧪 Zadanie 4:
         * Uruchom serwer z kapsztonu i wyslij recznie (przez `curl`/Postman,
         * patrz `_18_rest_api/Lesson17`) zadanie do `/login` - zaobserwuj
         * odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyLoginValidationHappensBeforeBcrypt {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, dlaczego w kapsztonie walidacja pustego
         * username/hasla nastepuje PRZED wywolaniem `BCrypt.checkpw()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddThirdUserWithDifferentRole {
        /*
         * 🧪 Zadanie 6:
         * Dodaj do kapsztonu 3. uzytkownika z NOWA rola (np. "viewer") i
         * NOWYM zestawem uprawnien - zweryfikuj dostep do `/admin/report`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainDifferenceBetween401And403InCapstone {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wskaz w kodzie kapsztonu WSZYSTKIE miejsca
         * zwracajace 401 i WSZYSTKIE zwracajace 403 - wyjasnij roznice
         * kazdego przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_VerifySecurityHeadersArePresentOnEveryResponse {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj (przez `HttpResponse.headers()`), ze naglowki
         * bezpieczenstwa (Lesson12) sa obecne w KAZDEJ odpowiedzi
         * kapsztonu, wlacznie z odpowiedziami bledu (400/401/403).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CountAuditEventsPerOutcomeType {
        /*
         * 🧪 Zadanie 9:
         * Po przebiegu demo, policz ile zdarzen audytu ma kazdy typ
         * wyniku (`AUTH_SUCCESS`, `AUTH_FAILURE`, `GRANTED`,
         * `PERMISSION_DENIED` itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SummarizeWhatEachLayerOfCapstoneDoes {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: opisz WLASNYMI SLOWAMI, co robi kazda z 5 warstw
         * kapsztonu (walidacja, BCrypt, JWT, RBAC, audyt) i w jakiej
         * KOLEJNOSCI sa wykonywane.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_AddRateLimitingToLoginEndpoint {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do `/login` prosty rate limiting (np. max 5 prob na IP w
         * ciagu okna czasowego) - powiaz z
         * `_18_rest_api/Lesson16_RateLimitingAndThrottling`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddNewEndpointWithDifferentPermission {
        /*
         * 🧪 Zadanie 12:
         * Dodaj nowy endpoint `/admin/users` wymagajacy INNEGO uprawnienia
         * niz `report:read` (np. `users:manage`) - zweryfikuj, ze rola
         * "admin" bez tego uprawnienia dostaje 403.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementLogoutInvalidatingToken {
        /*
         * 🧪 Zadanie 13:
         * Dodaj endpoint `/logout` uniewazniajacy token PRZED jego
         * naturalnym wygasnieciem (np. przez prosta "czarna liste" tokenow
         * w pamieci) - powiaz z ograniczeniem JWT z Lesson05.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ValidateUsernameFormatBeforeLookup {
        /*
         * 🧪 Zadanie 14:
         * Rozbuduj walidacje `/login` o sprawdzenie FORMATU username
         * (allowlist regex z Lesson17) PRZED przeszukaniem mapy
         * uzytkownikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddSanitizedAuditLoggingForUsernameField {
        /*
         * 🧪 Zadanie 15:
         * Zabezpiecz wywolania `audit(...)` przed log injection (Lesson19)
         * - zsanityzuj pole username PRZED zapisaniem do dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DemonstrateTamperedAuditChainDetection {
        /*
         * 🧪 Zadanie 16:
         * Po przebiegu demo, recznie zmien jedno pole w liscie zdarzen
         * audytu (bez przeliczania hasha) - zweryfikuj, ze
         * `verifyAuditChain()` wykrywa manipulacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExtractOwaspMappingIntoReusableRecord {
        /*
         * 🧪 Zadanie 17:
         * Przeksztalc `Map<String, String>` z mapowaniem OWASP na rekord
         * `OwaspCategory(String id, String name, List<String> lessons)` -
         * wypisz w bardziej czytelnym formacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddRequestIdCorrelationAcrossAuditEvents {
        /*
         * 🧪 Zadanie 18:
         * Dodaj do kazdego zadania unikalny `requestId` (np. `UUID`)
         * przekazywany do `audit(...)` - powiaz zdarzenia nalezace do
         * TEGO SAMEGO zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddDependencyCheckStepToStartupSequence {
        /*
         * 🧪 Zadanie 19:
         * Przed startem serwera w kapsztonie, wywolaj (symulowany) skan
         * zaleznosci z Lesson20 - jesli wykryto podatnosc KRYTYCZNA,
         * przerwij start serwera z czytelnym komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureEndToEndLatencyOfEachScenario {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas trwania (`System.nanoTime()`) kazdego z 7
         * scenariuszy demo - wypisz, ktory jest najwolniejszy i
         * wyjasnij dlaczego (podpowiedz: BCrypt).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementRefreshTokenFlow {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj przeplyw refresh tokenu - krotkozywotny JWT
         * dostepowy + dlugozywotny refresh token wymieniany na nowy JWT
         * bez ponownego logowania haslem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AddAbacRuleOnTopOfRbac {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj `/admin/report` o regule ABAC (Lesson07) - np. dostep
         * TYLKO w symulowanych "godzinach pracy" - polacz z istniejaca
         * kontrola RBAC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AddCsrfProtectionForStateChangingEndpoint {
        /*
         * 🧪 Zadanie 23:
         * Dodaj endpoint ZMIENIAJACY stan (np. `POST /admin/report/reset`)
         * zabezpieczony tokenem CSRF (Lesson10) - zweryfikuj zadanie BEZ
         * poprawnego tokenu CSRF konczace sie odrzuceniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AddCorsPolicyToCapstoneServer {
        /*
         * 🧪 Zadanie 24:
         * Dodaj do kapsztonu polityke CORS (Lesson09) - zezwol TYLKO
         * jednemu, konkretnemu originowi na odczyt `/admin/report`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAccountLockoutAfterRepeatedFailures {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj blokade konta po N nieudanych probach logowania
         * (np. 5) na okreslony czas - zaloguj zdarzenie `ACCOUNT_LOCKED`
         * do audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildFullRequestPipelineWithAllArchitectureLayers {
        /*
         * 🧪 Zadanie 26:
         * Polacz kapszton z warstwowa architektura walidacji z
         * `_17_architecture/Lesson15` - jawnie rozdziel walidacje DTO,
         * niezmiennik domeny i regule biznesowa dla `/login`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GenerateSecurityAuditReportCombiningAllLessons {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj raport bezpieczenstwa aplikacji laczacy: mini-SBOM
         * (Lesson20), status naglowkow bezpieczenstwa (Lesson12),
         * integralnosc dziennika audytu (Lesson19) - jeden spojny
         * wydruk podsumowujacy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SimulateFullAttackScenarioAndDefenseChain {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj pelny scenariusz ataku (proba brute-force logowania,
         * proba dostepu bez tokenu, proba eskalacji uprawnien przez
         * zmodyfikowany JWT) - pokaz, KTORA warstwa obrony zatrzymuje
         * KAZDA z tych prob.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareCapstoneWithSpringSecurityEquivalent {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: opisz, jak te SAME warstwy (BCrypt, JWT, RBAC,
         * CORS, naglowki) wygladalyby w Spring Security (koncepcyjnie -
         * `@EnableWebSecurity`, `SecurityFilterChain`, `PasswordEncoder`) -
         * bez pisania kodu Springa (ostatni rozdzial kursu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildProductionReadyChecklistFromEntireChapter {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna liste kontrolna (checklist) "gotowosci
         * produkcyjnej" laczaca WSZYSTKIE 21 lekcji tego rozdzialu (jeden
         * punkt na lekcje, z krotkim uzasadnieniem) - wypisz jako
         * ponumerowana liste.
         */
        public static void main(String[] args) { }
    }
}
