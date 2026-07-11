package com.example.javaquest._19_security_basics.Lesson07_AuthorizationPatternsAndRbac;

public class _Exercises_Lesson07_AuthorizationPatternsAndRbac {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRbacVsAbacInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy RBAC a
         * ABAC - podaj po 1 przykladzie regoly, ktora latwiej wyrazic
         * kazdym z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefineRolePermissionMap {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj Mape rola->Set uprawnien dla 3 rol: "VIEWER",
         * "EDITOR", "ADMIN" - wypisz uprawnienia kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AssignRolesToUsers {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj Mape username->rola dla 4 uzytkownikow - wypisz
         * przypisania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementHasPermissionCheck {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode `hasPermission(username, permission)` zwracajaca
         * boolean na podstawie Map z Zadan 2-3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RejectActionForInsufficientRole {
        /*
         * 🧪 Zadanie 5:
         * Zweryfikuj, ze uzytkownik z rola "VIEWER" NIE MA uprawnienia
         * "articles:delete" - wypisz czytelny komunikat odmowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementRoleHierarchy {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj hierarchie rol - "ADMIN" DZIEDZICZY wszystkie
         * uprawnienia "EDITOR", ktory dziedziczy uprawnienia "VIEWER" -
         * zweryfikuj, ze ADMIN ma TEZ uprawnienie "articles:read".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListAllPermissionsForUser {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode zwracajaca PELNA (z hierarchia z Zadania 6) liste
         * uprawnien danego uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementMultipleRolesPerUser {
        /*
         * 🧪 Zadanie 8:
         * Zmien model tak, by uzytkownik mogl miec WIECEJ NIZ 1 role
         * (np. Set<String> zamiast pojedynczego stringa) - uprawnienia to
         * SUMA uprawnien wszystkich jego rol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyNewUserShouldStartWithNoPermissions {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij zasade najmniejszych uprawnien i
         * dlaczego nowe konto powinno startowac BEZ zadnych uprawnien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CentralizePermissionChecksInOneMethod {
        /*
         * 🧪 Zadanie 10:
         * Zrefaktoryzuj (zaprojektuj) tak, by WSZYSTKIE sprawdzenia
         * uprawnien w aplikacji przechodzily przez 1 centralna metode -
         * wyjasnij w komentarzu zalete takiego podejscia.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementResourceOwnershipCheck {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj (jak w teorii) sprawdzenie "tylko wlasciciel
         * zasobu" dla rekordu `Order(id, ownerUsername, amount)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CombineRoleCheckWithOwnershipCheck {
        /*
         * 🧪 Zadanie 12:
         * Polacz RBAC i ABAC: "EDITOR moze edytowac TYLKO WLASNE
         * artykuly, ADMIN moze edytowac WSZYSTKIE" - zaimplementuj i
         * przetestuj oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementTimeBasedAccessRestriction {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj regoly ABAC zalezna od "pory dnia" (symulowanej
         * zmiennej `LocalTime`) - np. dostep do `/admin/reports` TYLKO w
         * godzinach 8-18.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementDepartmentBasedAccess {
        /*
         * 🧪 Zadanie 14:
         * Dodaj atrybut "dzial" do uzytkownika i zasobu - zaimplementuj
         * regole "pracownik widzi TYLKO dokumenty swojego dzialu" (chyba
         * ze ma role ADMIN).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BuildPermissionCheckingAnnotationStyleWrapper {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj (bez realnej refleksji/AOP) "wrapper" metody,
         * ktory PRZED wywolaniem logiki biznesowej sprawdza wymagane
         * uprawnienie - zasymuluj `@RequiresPermission("articles:delete")`
         * jako zwykle wywolanie metody sprawdzajacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LogAuthorizationDenialsForAudit {
        /*
         * 🧪 Zadanie 16:
         * Dodaj logowanie (na konsole) KAZDEJ odmowy dostepu z
         * username, wymaganym uprawnieniem i znacznikiem czasu -
         * zapowiedz Lesson19 (Secure Logging).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDynamicRoleAssignmentAtRuntime {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj metody `grantRole`/`revokeRole` zmieniajace role
         * uzytkownika W TRAKCIE dzialania programu - zweryfikuj, ze
         * zmiana natychmiast wplywa na kolejne sprawdzenia uprawnien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PreventPrivilegeEscalationViaSelfRoleChange {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj zabezpieczenie: zwykly uzytkownik NIE MOZE
         * przyznac SOBIE roli ADMIN (nawet gdyby mial dostep do metody
         * `grantRole`) - tylko istniejacy ADMIN moze przyznawac role.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementDenyOverridesAllowConflictResolution {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj sytuacje, gdzie uzytkownik ma role dajaca
         * uprawnienie ORAZ jawna, ABAC-owa regule ZABRANIAJACA (np.
         * "zablokowane konto") - zweryfikuj, ze DENY ZAWSZE wygrywa z
         * ALLOW.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareRbacAbacWithAccessControlLists {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zbadaj i porownaj (tabela w komentarzu) RBAC,
         * ABAC i ACL (Access Control List - uprawnienia przypisane
         * BEZPOSREDNIO do pojedynczych zasobow) pod katem skalowalnosci i
         * latwosci zarzadzania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildPolicyEnginePluggableRules {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj prosty "silnik polityk" - interfejs
         * `AuthorizationPolicy` z metoda `boolean allows(User, Resource,
         * Action)` - zaimplementuj 2-3 rozne polityki i sprawdz je PO
         * KOLEI (pierwsza DENY konczy sprawdzanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementAttributeBasedPolicyWithMultipleConditions {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj polityke ABAC z WIELOMA warunkami polaczonymi
         * (np. dzial ORAZ pora dnia ORAZ status konta) - zweryfikuj
         * wszystkie kombinacje prawda/falsz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasurePerformanceOfPermissionCheckAtScale {
        /*
         * 🧪 Zadanie 23:
         * Zmierz czas 100 000 sprawdzen uprawnien dla 1000 uzytkownikow
         * z roznymi rolami - zoptymalizuj strukture danych, jesli
         * potrzeba (np. cache uprawnien per rola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementTemporaryElevatedAccess {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj "tymczasowe podniesienie uprawnien" (np. "just
         * in time access") - uzytkownik dostaje dodatkowe uprawnienie na
         * ograniczony czas (symulowany), automatycznie wygasajace.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AuditFullPermissionMatrixForAllRoles {
        /*
         * 🧪 Zadanie 25:
         * Wygeneruj i wypisz PELNA "macierz" rola x uprawnienie (tabela
         * true/false) dla wszystkich rol i wszystkich mozliwych
         * uprawnien w systemie - przydatne do audytu bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSegregationOfDutiesConstraint {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj regole "segregation of duties" - uzytkownik NIE
         * MOZE miec JEDNOCZESNIE rol "CREATE_PAYMENT" i "APPROVE_PAYMENT"
         * (typowa kontrola antyfraudowa w systemach finansowych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildResourceLevelPermissionOverrides {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj mozliwosc NADPISANIA uprawnien roli dla
         * KONKRETNEGO zasobu (np. "jan ma zwykle tylko odczyt, ale dla
         * DOKUMENTU X-123 dostal wyjatkowo prawo edycji") - zweryfikuj
         * priorytet: override zasobu > uprawnienia z roli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SimulateConfusedDeputyProblem {
        /*
         * 🧪 Zadanie 28:
         * Zbadaj i zademonstruj (uproszczony przyklad w kodzie) problem
         * "confused deputy" - gdy uslugra o WYSOKICH uprawnieniach
         * wykonuje operacje NA ZLECENIE uzytkownika o NISKICH
         * uprawnieniach bez odpowiedniej weryfikacji - napraw przez
         * przekazanie/weryfikacje tozsamosci ZLECENIODAWCY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementPermissionChangeAuditTrail {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj historie WSZYSTKICH zmian uprawnien/rol (kto,
         * kiedy, co zmienil) - umozliwiajaca odtworzenie "kto mial jakie
         * uprawnienia w danym momencie w przeszlosci".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAuthorizationSystemDemo {
        /*
         * 🧪 Zadanie 30:
         * Polacz RBAC z hierarchia (Zadanie 6), ABAC z wieloma warunkami
         * (Zadanie 22), segregation of duties (Zadanie 26) i audyt
         * (Zadanie 29) w 1 spojne demo - zweryfikuj co najmniej 5
         * scenariuszy z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
