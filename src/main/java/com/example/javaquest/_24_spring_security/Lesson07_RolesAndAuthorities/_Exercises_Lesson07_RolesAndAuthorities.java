package com.example.javaquest._24_spring_security.Lesson07_RolesAndAuthorities;

public class _Exercises_Lesson07_RolesAndAuthorities {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainGrantedAuthorityInterface {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, CZYM jest `GrantedAuthority`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainRolePrefixConvention {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij konwencje prefiksu `ROLE_` I KIEDY
         * jest DODAWANY automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddThirdUserWithModeratorRole {
        /*
         * 🧪 Zadanie 3:
         * Dodaj TRZECIEGO uzytkownika Z rola "MODERATOR".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddModeratorProtectedEndpoint {
        /*
         * 🧪 Zadanie 4:
         * Dodaj endpoint `/moderator/**` chroniony `hasRole("MODERATOR")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TestUnauthenticatedRequestToRoleProtectedPath {
        /*
         * 🧪 Zadanie 5:
         * Wyslij zadanie DO `/admin/panel` BEZ ZADNYCH poswiadczen -
         * sprawdz, CZY dostajesz 401 CZY 403 (KOLEJNOSC sprawdzania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareHasRoleWithHasAuthority {
        /*
         * 🧪 Zadanie 6:
         * Zamien `hasRole("ADMIN")` NA `hasAuthority("ROLE_ADMIN")` -
         * zweryfikuj IDENTYCZNE zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestHasAuthorityWithoutRolePrefix {
        /*
         * 🧪 Zadanie 7:
         * Dodaj uprawnienie BEZ prefiksu `ROLE_` (np. "ORDERS_DELETE")
         * I zabezpiecz endpoint `hasAuthority("ORDERS_DELETE")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestUserWithMultipleRoles {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, ze 'szef' (role USER+ADMIN) MA dostep DO OBU
         * chronionych endpointow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhy403NotLeakEndpointExistence {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO 403 (a NIE 404) DLA
         * uwierzytelnionego uzytkownika BEZ uprawnien jest POPRAWNYM
         * zachowaniem (w odroznieniu OD 401).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareWithChapter19RbacLesson {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: powiaz z `_19_security_basics/Lesson07_
         * AuthorizationPatternsAndRbac` - porownaj RECZNA implementacje
         * RBAC Z tym, co robi Spring Security TU.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementHasAnyRoleForMultipleAllowedRoles {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `.hasAnyRole("USER", "ADMIN")` DLA endpointu dostepnego
         * DLA OBU rol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomGrantedAuthorityClass {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj WLASNA klase `implements GrantedAuthority`
         * (ZAMIAST `SimpleGrantedAuthority`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignHierarchicalRoles {
        /*
         * 🧪 Zadanie 13:
         * Skonfiguruj `RoleHierarchy` (ADMIN "dziedziczy" uprawnienia
         * USER) - zweryfikuj, ze ADMIN nie potrzebuje JAWNEJ roli USER.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineRoleAndPermissionBasedChecks {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj endpoint wymagajacy JEDNOCZESNIE roli I konkretnego
         * uprawnienia (`hasRole("ADMIN") and hasAuthority("AUDIT_READ")`
         * PRZEZ `access(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestRoleCaseSensitivity {
        /*
         * 🧪 Zadanie 15:
         * Zweryfikuj, czy `hasRole("admin")` (mala litera) DZIALA Z
         * rola "ADMIN" (wielka litera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementDynamicRoleAssignmentFromDatabase {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_23_spring_data_jpa` - zaladuj role uzytkownika Z
         * TABELI `user_roles` (relacja wiele-do-wielu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainDifferenceBetweenRbacAndAbac {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: powiaz z `_19_security_basics/Lesson07` -
         * porownaj RBAC (role) Z ABAC (atrybuty) NA przykladzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestConcurrentRequestsWithDifferentRoles {
        /*
         * 🧪 Zadanie 18:
         * Wyslij ROWNOLEGLE zadania OD roznych uzytkownikow (USER I
         * ADMIN) - zweryfikuj POPRAWNA izolacje autoryzacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddSuperAdminRoleWithFullAccess {
        /*
         * 🧪 Zadanie 19:
         * Dodaj role "SUPER_ADMIN" Z dostepem DO WSZYSTKICH sciezek
         * (`.anyRequest()` PRZED innymi regulami, Z UWZGLEDNIENIEM
         * kolejnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentRoleMatrixForApi {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: udokumentuj (jak macierz) WSZYSTKIE
         * endpointy I WYMAGANE role/uprawnienia DLA kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomAuthorizationManager {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `AuthorizationManager<RequestAuthorizationContext>`
         * Z LOGIKA WYKRACZAJACA POZA proste role (np. "tylko WLASCICIEL
         * zasobu").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMultiTenantRoleScoping {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: zaprojektuj role SKOPOWANE DO tenanta (np.
         * "ADMIN W firmie X" NIE oznacza "ADMIN W firmie Y").
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementResourceOwnershipCheck {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj autoryzacje SPRAWDZAJACA, CZY zalogowany uzytkownik
         * jest WLASCICIELEM konkretnego zasobu (nie tylko rola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkAuthorizationOverheadWithManyRules {
        /*
         * 🧪 Zadanie 24:
         * Zmierz narzut autoryzacji PRZY 50 regulach `requestMatchers`
         * W POROWNANIU Z 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementTemporaryRoleElevation {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj mechanizm CZASOWEGO podniesienia uprawnien (np.
         * "sudo mode" NA 5 minut).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AuditAllAuthorizationDenials {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson19` - loguj KAZDA
         * odmowe 403 DO dziennika audytu (kto, kiedy, jaki zasob).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareStaticRolesWithPolicyEngine {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: porownaj statyczne role (tu) Z SILNIKIEM
         * polityk (np. OPA/Open Policy Agent) - kiedy WARTO przejsc NA
         * silnik polityk.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRoleBasedRateLimiting {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_18_rest_api/Lesson16` - zroznicuj limity zapytan
         * WEDLUG roli (ADMIN BEZ limitu, USER Z limitem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignRoleMigrationForRenamedRole {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zaprojektuj BEZPIECZNA migracje PRZY zmianie
         * nazwy roli (np. "MODERATOR" -> "CONTENT_MANAGER") W
         * DZIALAJACYM systemie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullRbacDemoWithFourRolesAndMatrix {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNE demo Z 4 rolami I MACIERZA 5 endpointow x 4
         * role - zweryfikuj WSZYSTKIE 20 kombinacji.
         */
        public static void main(String[] args) { }
    }
}
