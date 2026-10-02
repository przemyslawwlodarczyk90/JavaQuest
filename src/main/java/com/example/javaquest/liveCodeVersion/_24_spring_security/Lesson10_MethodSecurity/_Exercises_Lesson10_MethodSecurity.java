package com.example.javaquest.liveCodeVersion._24_spring_security.Lesson10_MethodSecurity;

public class _Exercises_Lesson10_MethodSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPreAuthorizeMechanism {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, PRZEZ JAKI mechanizm (AOP) dziala
         * `@PreAuthorize`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddPostAuthorizeExample {
        /*
         * 🧪 Zadanie 2:
         * Dodaj metode Z `@PostAuthorize` (sprawdzajaca WYNIK PO
         * wykonaniu, NIE PRZED).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddSecondAdminOnlyMethod {
        /*
         * 🧪 Zadanie 3:
         * Dodaj DRUGA metode Z `@PreAuthorize("hasRole('ADMIN')")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestUnauthenticatedRequestToProtectedMethod {
        /*
         * 🧪 Zadanie 4:
         * Wyslij zadanie BEZ poswiadczen DO endpointu wywolujacego
         * metode Z `@PreAuthorize` - sprawdz status.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseHasAnyRoleInPreAuthorize {
        /*
         * 🧪 Zadanie 5:
         * Zmien `@PreAuthorize("hasRole('USER')")` NA
         * `hasAnyRole('USER', 'ADMIN')`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainSpelExpressionAccessToAuthentication {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: powiaz z `_20_spring_core/Lesson17_SpelBasics` -
         * wyjasnij, SKAD `@PreAuthorize` MA dostep DO `authentication`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestOwnProfileAccessForBothUsers {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj, ze OBAJ uzytkownicy MOGA dostac SWOJ WLASNY
         * profil, ale NIE CUDZY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddThirdMethodWithMultipleParameterCheck {
        /*
         * 🧪 Zadanie 8:
         * Dodaj metode Z 2 parametrami I `@PreAuthorize` UZYWAJACYM
         * OBU (np. `#ownerId == authentication.name and #amount < 1000`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareWithLesson09UrlBasedSecurity {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj TA lekcje Z Lesson09 - KIEDY uzyc
         * URL-owej, A KIEDY metodowej autoryzacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAllMethodSecurityAnnotations {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz WSZYSTKIE adnotacje Method Security
         * (`@PreAuthorize`/`@PostAuthorize`/`@PreFilter`/`@PostFilter`/
         * `@Secured`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementPreFilterOnCollectionParameter {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@PreFilter` DO PRZEFILTROWANIA kolekcji parametrow
         * PRZED wykonaniem metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPostFilterOnReturnedCollection {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@PostFilter` DO PRZEFILTROWANIA zwracanej listy (np.
         * TYLKO WLASNE zamowienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareSecuredAnnotationWithPreAuthorize {
        /*
         * 🧪 Zadanie 13:
         * Dodaj metode Z `@Secured("ROLE_ADMIN")` (starsza adnotacja) -
         * porownaj Z `@PreAuthorize`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomPermissionEvaluator {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj WLASNY `PermissionEvaluator` I uzyj
         * `hasPermission(...)` W `@PreAuthorize`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestSelfInvocationPitfall {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_20_spring_core/Lesson22` - wywolaj metode Z
         * `@PreAuthorize` PRZEZ `this.metoda()` Z WEWNATRZ TEJ SAMEJ
         * klasy - zweryfikuj, ze SPRAWDZENIE zostalo POMINIETE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementMethodSecurityOnRepositoryLayer {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_23_spring_data_jpa` - dodaj `@PreAuthorize` NA
         * WLASNEJ metodzie repozytorium (custom `@Query`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareOrderOfPreAuthorizeAndTransactional {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_23_spring_data_jpa/Lesson08` - sprawdz KOLEJNOSC
         * dzialania `@PreAuthorize` I `@Transactional` NA TEJ SAMEJ
         * metodzie (KTORY proxy jest "na zewnatrz").
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureMethodSecurityOverhead {
        /*
         * 🧪 Zadanie 18:
         * Zmierz narzut `@PreAuthorize` NA 1000 wywolan metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementNestedMethodSecurityChecks {
        /*
         * 🧪 Zadanie 19:
         * Wywolaj metode Z `@PreAuthorize` Z WEWNATRZ INNEGO beana
         * (NIE self-invocation) - zweryfikuj, ze SPRAWDZENIE DZIALA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignServiceLayerVsControllerLayerSecurity {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: powiaz z `_17_architecture/Lesson04_
         * ControllerServiceRepository` - zaprojektuj, GDZIE powinna
         * ZYC autoryzacja (kontroler vs serwis).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementComplexPermissionEvaluatorWithDomainObjects {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj `PermissionEvaluator` operujacy NA obiektach
         * domenowych (`hasPermission(#order, 'DELETE')`), NIE tylko
         * ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignAuditTrailForMethodSecurityDenials {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_19_security_basics/Lesson19` - loguj KAZDE
         * odrzucenie `@PreAuthorize` Z pelnym kontekstem (metoda,
         * parametry, uzytkownik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareMethodSecurityWithAspectJWeaving {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: powiaz z `_20_spring_core/Lesson21` - porownaj
         * proxy-based Method Security Z PELNYM AspectJ weavingiem
         * (kompilacja/load-time) - kompromisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementDynamicPermissionResolutionFromDatabase {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_23_spring_data_jpa` - wczytaj UPRAWNIENIA
         * DYNAMICZNIE Z bazy (NIE statyczne role) W `PermissionEvaluator`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TestMethodSecurityWithReactiveMethods {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: opisz ROZNICE W dzialaniu Method Security DLA
         * metod ZWRACAJACYCH `Mono`/`Flux` (reaktywnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCachingLayerRespectingMethodSecurity {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_13_libraries/Lesson27` - zweryfikuj, ze CACHE (np.
         * `@Cacheable`) NIE OMIJA `@PreAuthorize` NA TEJ SAMEJ metodzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMultiLevelAuthorizationCombiningUrlAndMethod {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj aplikacje LACZACA URL-owa (Lesson09) I metodowa
         * (tu) autoryzacje JAKO 2 NIEZALEZNE WARSTWY obrony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BenchmarkPermissionEvaluatorWithComplexRules {
        /*
         * 🧪 Zadanie 28:
         * Zmierz WYDAJNOSC ZLOZONEGO `PermissionEvaluator` (np.
         * sprawdzajacego hierarchie organizacyjna) PRZY 10000 wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFeatureFlagAwareMethodSecurity {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj `@PreAuthorize` UWZGLEDNIAJACY flage funkcji
         * (np. "nowa funkcja dostepna TYLKO DLA beta testerow").
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullDomainWithFineGrainedMethodSecurity {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DOMENE "system bankowy" (konto/przelew/wyciag) Z
         * PELNA, WIELOWARSTWOWA autoryzacja metodowa (wlasciciel konta,
         * limit kwoty, rola pracownika banku).
         */
        public static void main(String[] args) { }
    }
}
