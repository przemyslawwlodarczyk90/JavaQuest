package com.example.javaquest._19_security_basics.Lesson17_InputValidation;

public class _Exercises_Lesson17_InputValidation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyClientValidationIsNotSecurity {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego walidacja w przegladarce
         * (HTML/JS) NIGDY nie moze byc jedyna linia obrony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementManualFieldValidation {
        /*
         * 🧪 Zadanie 2:
         * Napisz reczna walidacje (bez adnotacji) dla pol: username
         * (niepuste), email (zawiera "@"), wiek (13-120).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareAllowlistWithDenylistForUsername {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj OBIE strategie (denylist blokujaca `<`/`>`,
         * allowlist regex `[a-zA-Z0-9_]+`) dla nazwy uzytkownika -
         * porownaj na 3 przykladowych, roznych inputach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddBeanValidationAnnotationsToRecord {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj rekord z adnotacjami `@NotBlank`, `@Size`, `@Email` -
         * zweryfikuj poprawny i niepoprawny przypadek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CollectAllViolationsAtOnce {
        /*
         * 🧪 Zadanie 5:
         * Uzyj `Validator.validate()` na obiekcie z WIELOMA bledami -
         * wypisz WSZYSTKIE naruszenia naraz (nie tylko pierwsze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ValidateNumericRangeWithMinMax {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `@Min`/`@Max` do pola liczbowego (np. ilosc w
         * zamowieniu) - przetestuj wartosci graniczne (dokladnie na
         * granicy, ponizej, powyzej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ValidateStringPatternWithRegex {
        /*
         * 🧪 Zadanie 7:
         * Dodaj `@Pattern` dla numeru telefonu (np. format polski) -
         * przetestuj kilka poprawnych i niepoprawnych formatow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainDifferenceBetweenSyntacticAndSemanticValidation {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij roznice miedzy walidacja SKLADNIOWA
         * (czy email "wyglada" jak email) a SEMANTYCZNA (czy adres
         * NAPRAWDE istnieje/odbiera poczte) - ktora robi Bean
         * Validation?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_HandleNullVsEmptyStringDifference {
        /*
         * 🧪 Zadanie 9:
         * Zweryfikuj roznice miedzy `@NotNull`, `@NotEmpty`, `@NotBlank`
         * na przykladach: `null`, `""`, `"   "`, `"tekst"`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ValidateBeforeUsingDataInBusinessLogic {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj metode, ktora ZANIM wykona logike biznesowa,
         * SPRAWDZA `violations.isEmpty()` i rzuca wyjatek z czytelna
         * lista bledow, jesli nie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomValidationConstraint {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNA adnotacje walidujaca (`@interface` +
         * `ConstraintValidator`) - np. `@StrongPassword` sprawdzajaca
         * min. 1 cyfre i 1 wielka litere.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ValidateCrossFieldConstraint {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj walidacje "krzyzowa" miedzy 2 polami (np.
         * "haslo" i "potwierdzenie hasla" musza byc zgodne) - uzyj
         * `@AssertTrue` na metodzie pomocniczej lub wlasnej adnotacji
         * na poziomie klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ValidateNestedObjectGraphs {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj walidacje ZAGNIEZDZONEGO obiektu (np. `Order`
         * zawierajacy liste `OrderItem`) z `@Valid` na polu listy -
         * zweryfikuj, ze bledy z ZAGNIEZDZONYCH obiektow TEZ sa zebrane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementValidationGroupsForDifferentScenarios {
        /*
         * 🧪 Zadanie 14:
         * Uzyj "grup walidacji" (`groups = ...`) do rozroznienia regul
         * dla "tworzenia" (wszystkie pola wymagane) i "aktualizacji"
         * (pola opcjonalne) TEGO SAMEGO obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SanitizeAfterValidationForDefenseInDepth {
        /*
         * 🧪 Zadanie 15:
         * Polacz walidacje (Bean Validation) z escapowaniem HTML z
         * Lesson11 - wyjasnij w komentarzu, dlaczego OBIE warstwy sa
         * potrzebne (walidacja odrzuca ZLY KSZTALT, escapowanie chroni
         * PRZY WYSWIETLANIU nawet poprawnie ksztaltowanych danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ValidateFileUploadMetadataBeforeProcessing {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj walidacje metadanych uploadu (nazwa, rozszerzenie,
         * rozmiar) PRZED przekazaniem do logiki z Lesson16 - powiaz obie
         * lekcje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementLengthLimitsToPreventDos {
        /*
         * 🧪 Zadanie 17:
         * Dodaj `@Size(max=...)` na polach tekstowych (np. komentarz max
         * 1000 znakow) - wyjasnij, jak BRAK takiego limitu moglby
         * ulatwic atak DoS (bardzo dlugie stringi zuzywajace pamiec/CPU).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidateEnumLikeStringAgainstAllowedValues {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj walidacje pola "status" (String) wobec DOKLADNEJ
         * listy dozwolonych wartosci (np. "NEW", "SHIPPED", "DELIVERED") -
         * odrzuc kazda inna wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildValidationErrorResponseAsRfc7807 {
        /*
         * 🧪 Zadanie 19:
         * Przeksztalc `Set<ConstraintViolation>` na format bledu RFC 7807
         * (Problem Details) - powiaz z `_18_rest_api/Lesson12-13`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareBeanValidationWithManualValidationPerformance {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas 10 000 walidacji Bean Validation vs recznej
         * walidacji `if` - skomentuj, czy narzut jest istotny w praktyce.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildLayeredValidationArchitecture {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj 3 warstwy walidacji z
         * `_17_architecture/Lesson15_ValidationArchitecture`: DTO (Bean
         * Validation), niezmiennik encji (konstruktor rzucajacy wyjatek),
         * regula biznesowa (serwis) - dla tego samego przypadku uzycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomValidatorWithExternalDependency {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY `ConstraintValidator`, ktory sprawdza
         * unikalnosc wartosci wobec Mapy "juz istniejacych" wartosci
         * (symulacja sprawdzenia unikalnosci w bazie danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildAllowlistBasedSanitizerForRichInput {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj walidator/sanitizer OPARTY NA ALLOWLIST dla
         * pola dopuszczajacego "troche" formatowania (np. TYLKO cyfry,
         * spacje i myslniki dla numeru telefonu) - odrzuc/wyczysc
         * wszystko inne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRateLimitedValidationForExpensiveChecks {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj walidacje, ktora wymaga KOSZTOWNEGO sprawdzenia
         * (np. wywolania zewnetrznego serwisu weryfikujacego adres) -
         * polacz z Lesson16 z `_18_rest_api` (rate limiting), zeby
         * atakujacy nie mogl naduzyc tego endpointu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AuditApplicationForMissingServerSideValidation {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode "audytujaca" liste endpointow (symulowanych) pod
         * katem obecnosci walidacji SERWEROWEJ (nie tylko opisu
         * "walidacja w JS we frontendzie") - oznacz brakujace.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementInternationalizedValidationMessages {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj komunikaty walidacji w WIELU jezykach (np. PL/EN)
         * uzywajac `message = "{klucz.komunikatu}"` + `ResourceBundle` -
         * przetestuj oba warianty jezykowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ValidateAgainstBusinessInvariantsNotJustSyntax {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj regule BIZNESOWA (nie tylko skladniowa) - np.
         * "data dostawy nie moze byc wczesniejsza niz data zamowienia" -
         * wyjasnij, dlaczego Bean Validation na polu SAMYM W SOBIE tego
         * nie wyrazi (potrzebna walidacja na poziomie KLASY/serwisu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BenchmarkCustomValidatorCachingStrategy {
        /*
         * 🧪 Zadanie 28:
         * Zoptymalizuj kosztowny `ConstraintValidator` z Zadania 22 przez
         * cache (np. Caffeine z `_13_libraries`) - zmierz poprawe
         * wydajnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFullRequestValidationPipeline {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj kompletny "pipeline" walidacji zadania HTTP: parsowanie
         * -> Bean Validation (DTO) -> reguly biznesowe (serwis) ->
         * odpowiedz RFC 7807 przy bledzie - zintegruj Zadania 19, 21, 27.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureRegistrationWithAllValidationLayers {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system rejestracji laczacy WSZYSTKIE techniki:
         * Bean Validation z wlasnymi adnotacjami (Zadanie 11), walidacje
         * krzyzowa (Zadanie 12), regula biznesowa (Zadanie 27),
         * odpowiedz RFC 7807 (Zadanie 19) - zweryfikuj co najmniej 5
         * scenariuszy z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
