package com.example.javaquest._19_security_basics.Lesson01_AuthenticationVsAuthorization;

public class _Exercises_Lesson01_AuthenticationVsAuthorization {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAuthNVsAuthZInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy
         * uwierzytelnianiem a autoryzacja - podaj po 1 przykladzie z
         * codziennego zycia (nie IT) dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementSimpleLoginEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z POST `/login` sprawdzajacym dane
         * logowania wzgledem Mapy uzytkownikow w pamieci (min. 2
         * uzytkownikow) - zwroc token (UUID) przy sukcesie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReturnUnauthorizedForWrongCredentials {
        /*
         * 🧪 Zadanie 3:
         * Zweryfikuj, ze `/login` z Zadania 2 zwraca 401 dla zlego hasla
         * i dla nieistniejacego uzytkownika (2 osobne przypadki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementProtectedEndpointRequiringToken {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `/profile` wymagajacy naglowka
         * "Authorization: Bearer <token>" - zwroc 401 dla brakujacego/
         * nieprawidlowego tokena.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddRoleFieldToUserModel {
        /*
         * 🧪 Zadanie 5:
         * Dodaj pole "role" do modelu uzytkownika (np. "user"/"admin") -
         * zweryfikuj, ze jest poprawnie zapisane przy logowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementRoleCheckReturning403 {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj endpoint `/admin/settings` zwracajacy 403 dla
         * zalogowanego, ale NIE-adminowego uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DistinguishThreeScenariosStatusCodes {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj (i wypisz) 3 rozne scenariusze dla `/admin/settings`:
         * brak tokena (401), zly token (401), token zwyklego usera (403) -
         * zwroc uwage na roznice miedzy 2 pierwszymi a 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhy401NameIsMisleading {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego nazwa "Unauthorized" dla kodu
         * 401 jest historycznie myląca - jaka nazwa lepiej oddawalaby
         * jego faktyczne znaczenie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementLogoutInvalidatingToken {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj `POST /logout` usuwajacy token z Mapy aktywnych
         * sesji - zweryfikuj, ze PO wylogowaniu ten sam token daje 401.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFourAuthenticationFactorTypes {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymien 4 typy "czynnikow" uwierzytelniania
         * (cos co WIESZ - haslo, cos co MASZ - token/klucz, cos czym
         * JESTES - biometria, gdzie JESTES - lokalizacja) - po 1
         * przykladzie kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementMultipleRolesPerUser {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz model uzytkownika o LISTE rol (nie 1 rola) - zwer
         * yfikuj autoryzacje dla uzytkownika z wieloma rolami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementResourceOwnershipCheck {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `/orders/{id}` zwracajacy 403, jesli zalogowany
         * uzytkownik NIE JEST wlascicielem zamowienia (autoryzacja NA
         * POZIOMIE ZASOBU, nie tylko roli) - to WAZNIEJSZY przypadek niz
         * prosta kontrola roli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementTokenExpiration {
        /*
         * 🧪 Zadanie 13:
         * Dodaj czas wygasniecia tokena (np. 5 sekund na potrzeby demo) -
         * zweryfikuj, ze PO uplywie czasu token daje 401 (foreshadowing
         * Lesson04/05).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementFailedLoginAttemptCounter {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj licznik nieudanych prob logowania per uzytkownik -
         * po 3 nieudanych probach zwracaj 401 z komunikatem o
         * TYMCZASOWYM zablokowaniu (bez faktycznej blokady, tylko komunikat).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementSameErrorMessageForSecurityReasons {
        /*
         * 🧪 Zadanie 15:
         * Zmodyfikuj `/login`, zeby zwracal IDENTYCZNY komunikat bledu
         * dla "uzytkownik nie istnieje" i "zle haslo" - w komentarzu
         * wyjasnij, dlaczego rozne komunikaty ULATWIAJA atakujacemu
         * "wykrywanie" istniejacych kont (foreshadowing OWASP w Lesson21).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementHierarchicalRoles {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj hierarchie rol (np. "admin" automatycznie ma
         * WSZYSTKIE uprawnienia "user") - zweryfikuj, ze admin moze
         * uzyskac dostep do endpointow przeznaczonych dla zwyklego usera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementPerEndpointPermissionMap {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj Mape (sciezka -> wymagana rola) i generyczny
         * "middleware" sprawdzajacy AuthN+AuthZ dla DOWOLNEGO endpointu
         * na podstawie tej Mapy, zamiast powielac logike w kazdym handlerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementAnonymousAccessForSomeEndpoints {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz middleware z Zadania 17 o obsluge endpointow
         * PUBLICZNYCH (nie wymagajacych AuthN wcale, np. `/health`) -
         * zweryfikuj, ze dzialaja BEZ tokena.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementAuditLogForAuthorizationDecisions {
        /*
         * 🧪 Zadanie 19:
         * Zaloguj (do listy w pamieci) KAZDA decyzje autoryzacyjna
         * (kto, jaki endpoint, dozwolone/odrzucone) - po serii testowych
         * requestow wypisz podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareStatelessVsStatefulAuthConceptually {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: porownaj podejscie z tej lekcji (token w Mapie
         * po stronie serwera - "stateful") z alternatywa, w ktorej caly
         * stan jest zakodowany W SAMYM tokenie ("stateless", jak JWT w
         * Lesson05) - jakie to daje roznice w skalowaniu?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullRbacWithPermissionsNotJustRoles {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj model z UPRAWNIENIAMI (permissions, np.
         * "orders:read", "orders:delete") przypisanymi do rol, zamiast
         * sprawdzania roli BEZPOSREDNIO w kodzie - endpoint deklaruje
         * WYMAGANE uprawnienie, nie WYMAGANA role (foreshadowing Lesson07).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDelegatedAuthorizationScenario {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj scenariusz "deleguj dostep" - uzytkownik A generuje
         * TYMCZASOWY token pozwalajacy uzytkownikowi B wykonac 1 KONKRETNA
         * akcje w JEGO imieniu (np. "przejrzyj moje zamowienie") -
         * ograniczony czasowo i zakresowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConcurrentSessionLimitPerUser {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj limit rownoczesnych sesji per uzytkownik (np. max
         * 2) - kolejne logowanie POWYZEJ limitu uniewaznia NAJSTARSZA
         * aktywna sesje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementStepUpAuthenticationForSensitiveActions {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj "step-up authentication" - zwykle uwierzytelnienie
         * wystarcza do przegladania danych, ale AKCJA WRAZLIWA (np. zmiana
         * hasla) wymaga DODATKOWEGO potwierdzenia (symulowanego ponownego
         * podania hasla) w TYM SAMYM requescie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementImpersonationForSupportStaff {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj funkcje "impersonacji" dla roli "support" -
         * moze DZIALAC W IMIENIU zwyklego uzytkownika (np. do
         * debugowania problemu), ale KAZDA taka akcja jest OSOBNO
         * logowana z jawnym zaznaczeniem "impersonated by X".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementAttributeBasedAccessControl {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj uproszczony ABAC (Attribute-Based Access Control) -
         * decyzja autoryzacyjna zalezy od WIELU atrybutow naraz (rola +
         * dzial + pora dnia, np. "finanse moga zatwierdzac faktury TYLKO
         * w godzinach pracy") - w odroznieniu od prostego RBAC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementTokenRevocationList {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj "blacklist" uniewaznionych tokenow (np. po
         * wykryciu podejrzanej aktywnosci) - sprawdzana PRZED normalna
         * weryfikacja tokena, nawet jesli token formalnie jeszcze
         * "nie wygasl".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementMultiTenantAuthorizationIsolation {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj izolacje wielo-najemca (multi-tenant) - uzytkownicy
         * naleza do roznych "organizacji", KAZDY dostep do zasobu musi
         * sprawdzic ZARÓWNO uprawnienia, JAK I przynaleznosc do TEJ SAMEJ
         * organizacji co zasob.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildSecurityTestSuiteForAuthzBypass {
        /*
         * 🧪 Zadanie 29:
         * Napisz "test suite" probujacy AUTOMATYCZNIE ROZNYCH obejsc
         * autoryzacji (brak tokena, wygasly token, token innej roli,
         * token innego usera na cudzym zasobie) - zweryfikuj, ze
         * WSZYSTKIE proby sa poprawnie odrzucane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAuthSystemWithAllPatternsCombined {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY system uwierzytelniania+autoryzacji laczacy:
         * role+uprawnienia (Zadanie 21), wlasnosc zasobu (Zadanie 12),
         * wygasanie tokenow (Zadanie 13), audit log (Zadanie 19), i
         * blacklist (Zadanie 27) - napisz "test suite" weryfikujacy min.
         * 10 roznych scenariuszy AuthN/AuthZ.
         */
        public static void main(String[] args) { }
    }
}
