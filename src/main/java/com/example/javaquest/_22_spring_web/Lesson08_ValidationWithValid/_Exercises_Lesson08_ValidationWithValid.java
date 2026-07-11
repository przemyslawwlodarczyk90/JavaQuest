package com.example.javaquest._22_spring_web.Lesson08_ValidationWithValid;

public class _Exercises_Lesson08_ValidationWithValid {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainValidAnnotationPurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, co robi `@Valid` NA `@RequestBody` i
         * CO SIE STANIE, gdy walidacja SIE NIE POWIEDZIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnDtoWithNotBlankAndSize {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNE DTO Z `@NotBlank`/`@Size` - podepnij pod
         * WLASNY endpoint POST Z `@Valid`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementDtoWithEmailAndMinMax {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNE DTO Z `@Email`, `@Min`, `@Max`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerValidationFailureAndInspectBody {
        /*
         * 🧪 Zadanie 4:
         * Wyslij DANE naruszajace 1 regule - zapisz DOKLADNA tresc
         * domyslnej odpowiedzi bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerMultipleViolationsAtOnce {
        /*
         * 🧪 Zadanie 5:
         * Wyslij DANE naruszajace 3+ reguly NARAZ - zweryfikuj, czy
         * odpowiedz zawiera WSZYSTKIE bledy CZY TYLKO PIERWSZY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementCustomValidationMessage {
        /*
         * 🧪 Zadanie 6:
         * Ustaw WLASNY, POLSKI komunikat bledu (`message = "..."`) dla
         * KAZDEJ adnotacji walidujacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementValidatedOnRequestParam {
        /*
         * 🧪 Zadanie 7:
         * Dodaj `@Validated` NA klasie kontrolera + `@Min`/`@Max` NA
         * `@RequestParam` - zweryfikuj DZIALANIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareValidWithValidatedAnnotations {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala (dokumentacja): wyjasnij ROZNICE miedzy `@Valid`
         * (Jakarta) a `@Validated` (Spring) - KIEDY ktorego uzyc?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementValidationOnPathVariable {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z `_22_spring_web/Lesson03` - dodaj `@Positive` NA
         * `@PathVariable` (z `@Validated` na klasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyValidationHappensBeforeBusinessLogic {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_17_architecture/Lesson15_Validation`):
         * wyjasnij, dlaczego walidacja NA GRANICY API POWINNA zajsc
         * PRZED logika biznesowa.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementNestedObjectValidation {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj DTO Z ZAGNIEZDZONYM obiektem, gdzie
         * ZAGNIEZDZONE pole TEZ ma `@Valid` - zweryfikuj REKURENCYJNA
         * walidacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementListElementValidation {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj DTO Z `List<@Valid NestedDto>` - zweryfikuj
         * walidacje KAZDEGO elementu listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomConstraintAnnotation {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_14_advancedjava/Lesson13_CustomAnnotations` -
         * zaimplementuj WLASNA adnotacje walidujaca (np. `@StrongPassword`)
         * Z `ConstraintValidator`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementGroupValidation {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj GRUPY walidacji (`@Validated(OnCreate.class)`
         * vs `OnUpdate.class`) dla RÓZNYCH wymogow przy tworzeniu vs
         * edycji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCrossFieldValidation {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj walidacje "krzyzowa" MIEDZY 2 polami DTO (np.
         * `password` == `confirmPassword`) - przez WLASNA adnotacje NA
         * KLASIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementBindingResultManualHandling {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj metode Z parametrem `BindingResult` (ZAMIAST
         * polegania na WYJATKU) - reczna obsluga bledow W KODZIE metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareDefaultVsCustomValidationResponseFormat {
        /*
         * 🧪 Zadanie 17:
         * Zestaw obok siebie DOMYSLNY format bledu Spring Boota Z
         * FORMATEM RFC 7807 (`_18_rest_api/Lesson12`) - jakie SA RÓZNICE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConditionalValidationBasedOnAnotherField {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj walidacje WARUNKOWA (np. `discountCode`
         * WYMAGANY TYLKO gdy `hasDiscount == true`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureValidationOverheadForLargeDto {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas walidacji DTO Z 20+ polami I 3 zagniezdzonymi
         * obiektami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildValidationAnnotationsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" NAJCZESCIEJ uzywanych adnotacji
         * Bean Validation (`@NotNull`/`@NotBlank`/`@Size`/`@Min`/`@Max`/
         * `@Email`/`@Pattern`/`@Positive`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementAsyncValidationAgainstDatabase {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNA adnotacje walidujaca SPRAWDZAJACA
         * UNIKALNOSC w "bazie" (np. `@UniqueUsername`) - powiaz Z
         * `_10_dao`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementValidationErrorLocalizationPlAndEn {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj komunikaty walidacji W 2 JEZYKACH (PL/EN),
         * WYBIERANE po naglowku `Accept-Language`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementValidationForFileUploadMetadata {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z Lesson14 (upload plikow) - zwaliduj METADANE
         * uploadu (nazwa/rozmiar/typ) PRZED zapisaniem pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementValidationAuditTrail {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj KAZDE
         * NIEPOWODZENIE walidacji (BEZ wrazliwych danych) do dziennika
         * audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementValidationRateLimitingForRepeatedFailures {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj BLOKADE PO
         * WIELU NIEUDANYCH probach walidacji (potencjalny atak/bot).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementValidationForPartialPatchWithNullSemantics {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj walidacje DLA PATCH ROZROZNIAJACA "pole NIE
         * PODANE" OD "pole PODANE jako null" (`JsonNullable`/`Optional`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementProgrammaticValidatorInvocation {
        /*
         * 🧪 Zadanie 27:
         * Wywolaj `Validator` (Jakarta Bean Validation) PROGRAMOWO,
         * BEZ Spring MVC (poza kontrolerem) - powiaz z
         * `_19_security_basics/Lesson17`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementValidationSchemaSyncWithOpenApi {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z Lesson18/`_18_rest_api/Lesson18` - zweryfikuj, ze
         * OGRANICZENIA walidacji (`@Size` itd.) SA ODZWIERCIEDLONE w
         * WYGENEROWANEJ specyfikacji OpenAPI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareBeanValidationWithManualIfChecks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj DEKLARATYWNA walidacje (adnotacje) Z
         * RECZNYMI sprawdzeniami `if` W KODZIE - jakie SA kompromisy
         * CZYTELNOSCI I TESTOWALNOSCI?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteValidationLayeredApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne mini-API rejestracji Z PELNA walidacja -
         * standardowa (Zadanie 2-3) + wlasna adnotacja (Zadanie 13) +
         * krzyzowa (Zadanie 15) + grupy (Zadanie 14) - jeden spojny,
         * dzialajacy endpoint.
         */
        public static void main(String[] args) { }
    }
}
