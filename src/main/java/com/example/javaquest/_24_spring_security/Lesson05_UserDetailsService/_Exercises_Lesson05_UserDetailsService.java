package com.example.javaquest._24_spring_security.Lesson05_UserDetailsService;

public class _Exercises_Lesson05_UserDetailsService {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainUserDetailsServiceContract {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, JAKA JEDNA metode musi zaimplementowac
         * `UserDetailsService`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddThirdUserToMap {
        /*
         * 🧪 Zadanie 2:
         * Dodaj TRZECIEGO uzytkownika ("carol") DO mapy `users`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestNewUserCanLogin {
        /*
         * 🧪 Zadanie 3:
         * Zweryfikuj, ze NOWO dodany uzytkownik MOZE sie zalogowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainNoopPrefixMeaning {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij, CO oznacza prefiks `{noop}` PRZED
         * haslem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RemoveNoopPrefixAndObserveError {
        /*
         * 🧪 Zadanie 5:
         * USUN prefiks `{noop}` Z jednego hasla I zaobserwuj BLAD PRZY
         * probie logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementUserDetailsServiceBackedByHashMap {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj WLASNY `UserDetailsService` uzywajacy
         * `ConcurrentHashMap` (ZAMIAST niemutowalnego `Map.of(...)`) -
         * DODAJ metode `registerUser(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhySameErrorForUnknownUserAndWrongPassword {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: powiaz z `_19_security_basics/Lesson01` -
         * wyjasnij, DLACZEGO "nie ma takiego uzytkownika" I "zle
         * haslo" DAJA TA SAMA odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestCaseSensitivityOfUsername {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, czy "Alice" (Z WIELKIEJ litery) MOZE zalogowac
         * sie jako "alice".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddRoleToNewUser {
        /*
         * 🧪 Zadanie 9:
         * Dodaj uzytkownika Z ROLA "MODERATOR" (zapowiedz Lesson07
         * RolesAndAuthorities).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareWithLesson01SingleUser {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj TA lekcje Z Lesson01 (JEDEN wbudowany
         * uzytkownik) - CO ZYSKUJESZ dzieki WLASNEMU
         * `UserDetailsService`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementUserDetailsServiceBackedByH2 {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z `_08_sql`/`_23_spring_data_jpa` - zaimplementuj
         * `UserDetailsService` odczytujacy uzytkownikow Z bazy H2
         * (TABELA `app_users`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementAccountLockedFlag {
        /*
         * 🧪 Zadanie 12:
         * Dodaj pole `locked` DO `AppUser` I zwroc
         * `.accountLocked(true)` W `UserDetails` DLA zablokowanych
         * kont - zweryfikuj `LockedException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementAccountExpiredFlag {
        /*
         * 🧪 Zadanie 13:
         * Dodaj pole `accountExpired` I zweryfikuj
         * `AccountExpiredException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCredentialsExpiredFlag {
        /*
         * 🧪 Zadanie 14:
         * Dodaj pole `credentialsExpired` (haslo WYGASLO) I zweryfikuj
         * `CredentialsExpiredException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementDisabledFlag {
        /*
         * 🧪 Zadanie 15:
         * Dodaj pole `enabled=false` I zweryfikuj `DisabledException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareAllFourAccountStatusExceptions {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: porownaj WSZYSTKIE 4 wyjatki statusu konta
         * (locked/expired/credentialsExpired/disabled) - KIEDY ktory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCachingUserDetailsService {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_13_libraries/Lesson27_CaffeineBasics` - DODAJ
         * cache DO `UserDetailsService` (unikanie powtarzalnych
         * zapytan DO "bazy").
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestUserDetailsServiceThreadSafety {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_05_multithreading` - wyslij 20 ROWNOLEGLYCH
         * logowan I zweryfikuj brak race condition.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementUserDetailsAdapterOverExistingEntity {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj WLASNA klase `implements UserDetails`
         * (ZAMIAST fabryki `User.withUsername(...)`) OPAKOWUJACA
         * ISTNIEJACA encje domenowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyUserDetailsServiceIsStatelessContract {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij, DLACZEGO `UserDetailsService` NIE
         * powinien SAM przechowywac stanu SESJI (to ROBI Security).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCompositeUserDetailsService {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj `UserDetailsService` PROBUJACY KOLEJNO 2 zrodla
         * (np. lokalna mapa, POTEM "zewnetrzne" LDAP-symulowane) -
         * wzorzec Chain of Responsibility.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureLoadUserByUsernameLatency {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_15_jvm_internals` - zmierz REALNY czas
         * `loadUserByUsername` PRZY symulowanym opoznieniu "bazy"
         * (100ms) - oszacuj wplyw NA latencje logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignMultiTenantUserDetailsService {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: zaprojektuj `UserDetailsService`
         * WIELOTENANTOWY (username UNIKALNY TYLKO W ramach tenanta,
         * NIE globalnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAuditingOnFailedLoadUserByUsername {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson19_SecureLoggingAndAuditing` -
         * loguj (BEZPIECZNIE, BEZ hasla) KAZDA nieudana probe znalezienia
         * uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareUserDetailsServiceWithReactiveVariant {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: powiaz z `_22_spring_web/Lesson17` (WebClient/
         * reaktywnosc) - opisz roznice miedzy `UserDetailsService` A
         * reaktywnym `ReactiveUserDetailsService`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRateLimitedUserDetailsService {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_18_rest_api/Lesson16_RateLimitingAndThrottling` -
         * DODAJ limit prob logowania NA username W JEDNOSTCE czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignGracefulDegradationWhenUserStoreUnavailable {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: zaprojektuj zachowanie `UserDetailsService`,
         * gdy "baza" uzytkownikow jest NIEDOSTEPNA (np. przerwa
         * sieciowa) - CO powinno sie stac Z logowaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementUserDetailsServiceWithSoftDelete {
        /*
         * 🧪 Zadanie 28:
         * Dodaj "miekkie usuwanie" uzytkownikow (`deletedAt`) -
         * zweryfikuj, ze usuniety uzytkownik NIE MOZE sie zalogowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BenchmarkInMemoryVsDatabaseBackedService {
        /*
         * 🧪 Zadanie 29:
         * Porownaj WYDAJNOSC `UserDetailsService` W PAMIECI Z WERSJA
         * odpytujaca H2 - ILE RAZY wolniejsza jest wersja bazodanowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullUserManagementDemoWithCrud {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DEMO Z operacjami CRUD NA uzytkownikach (dodaj/
         * zablokuj/usun/przywroc) I zweryfikuj wplyw KAZDEJ operacji NA
         * mozliwosc logowania.
         */
        public static void main(String[] args) { }
    }
}
