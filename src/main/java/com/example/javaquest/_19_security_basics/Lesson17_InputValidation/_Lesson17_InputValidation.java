package com.example.javaquest._19_security_basics.Lesson17_InputValidation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class _Lesson17_InputValidation {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: WALIDACJA DANYCH WEJSCIOWYCH ===");

        /*
         * ============================================================
         * 📦 PODSUMOWANIE TEGO, CO JUZ WIESZ (LESSON09-16)
         * ============================================================
         * Kazda z poprzednich lekcji pokazala INNY sposob, w jaki
         * niezaufany input moze zaszkodzic aplikacji - SQL injection
         * (Lesson13), XSS (Lesson11), XXE (Lesson15), path traversal
         * (Lesson16). WSPOLNY MIANOWNIK wszystkich: aplikacja
         * ZAAKCEPTOWALA dane, ktore NIGDY nie powinny byc zaakceptowane
         * w tej postaci. Ta lekcja pokazuje FORMALNA, SYSTEMATYCZNA
         * warstwe - WALIDACJE WEJSCIA - jako PIERWSZA (ale NIE JEDYNA!)
         * linie obrony.
         */
        System.out.println("Walidacja wejscia = PIERWSZA linia obrony - ale (jak widziales) NIE zastepuje escapowania/PreparedStatement/itp.");

        demonstrateClientSideIsNotEnough();
        demonstrateAllowlistVsDenylist();
        demonstrateBeanValidationWithCollectAllErrors();

        /*
         * ============================================================
         * 🔹 WALIDACJA PO STRONIE KLIENTA vs SERWERA
         * ============================================================
         * Walidacja W PRZEGLADARCE (np. atrybut HTML `required`,
         * walidacja JS) to WYLACZNIE wygoda uzytkownika (UX) -
         * natychmiastowy feedback BEZ podrozy do serwera. Atakujacy
         * MOZE ja CALKOWICIE OMINAC (np. wywolujac API bezposrednio
         * przez `curl`/Postman, tak jak w `_18_rest_api/Lesson17`).
         * SERWER MUSI zawsze walidowac WSZYSTKO OD NOWA - to JEDYNA
         * walidacja, ktorej mozna UFAC.
         *
         * ============================================================
         * 🔹 ALLOWLIST (BIALA LISTA) vs DENYLIST (CZARNA LISTA)
         * ============================================================
         * - DENYLIST - "odrzuc znane zle wzorce" (np. blokuj `<script>`,
         *   blokuj `--`) - SLABE, bo atakujacy ZAWSZE moze znalezc
         *   WARIANT, ktorego nie przewidziales (patrz Lesson11, Zadanie
         *   15 - "zaciemnione" payloady).
         * - ALLOWLIST - "zaakceptuj TYLKO znany, bezpieczny wzorzec" (np.
         *   "nazwa uzytkownika = TYLKO litery/cyfry, 3-20 znakow") -
         *   SILNIEJSZE, bo domyslnie ODRZUCA WSZYSTKO poza jawnie
         *   dozwolonym.
         * PREFERUJ allowlist ZAWSZE, gdy mozliwe.
         *
         * ============================================================
         * 🔹 JAKARTA BEAN VALIDATION - DEKLARATYWNA WALIDACJA
         * ============================================================
         * Zamiast reczengo `if (name == null || name.isBlank()) throw...`
         * dla KAZDEGO pola, Bean Validation (adnotacje `@NotBlank`,
         * `@Size`, `@Email`, `@Pattern` itd. - juz widziane przy okazji
         * `spring-boot-starter-validation` w `_18_rest_api/Lesson13`)
         * pozwala OPISAC reguly DEKLARATYWNIE na samej klasie, a
         * `Validator` automatycznie je sprawdza i zwraca WSZYSTKIE
         * naruszenia naraz (wzorzec "collect all errors" z
         * `_18_rest_api/Lesson13_ValidationErrors").
         */
        System.out.println("\nAllowlist ('zaakceptuj TYLKO znany wzorzec') jest SILNIEJSZY niz denylist ('odrzuc znane zle wzorce').");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Walidacja klienta = TYLKO UX, NIGDY bezpieczenstwo - serwer
         *   MUSI zwalidowac wszystko od nowa.
         * - Allowlist > denylist - "co jest DOZWOLONE", nie "co jest
         *   ZABRONIONE".
         * - Bean Validation (adnotacje) daje deklaratywna, spojna
         *   walidacje z automatycznym zbieraniem WSZYSTKICH bledow.
         * - WAZNE (przypomnienie z Lesson11/13/15): walidacja wejscia
         *   sprawdza KSZTALT danych (czy to wyglada jak email/liczba/
         *   krotki tekst) - to NIE ZASTEPUJE kodowania wyjscia
         *   (escapowanie HTML), parametryzowanych zapytan (PreparedStatement),
         *   czy bezpiecznej konfiguracji parsera XML - to WSZYSTKO
         *   dziala RAZEM, na roznych warstwach.
         * - Zasada z `_17_architecture/Lesson15_ValidationArchitecture`:
         *   walidacja na GRANICY (DTO) NIE zastepuje niezmiennikow
         *   domeny (encja/konstruktor) ani regul biznesowych (serwis) -
         *   to WARSTWY, kazda z innym celem.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void demonstrateClientSideIsNotEnough() {
        System.out.println("\n=== WALIDACJA KLIENTA TO TYLKO UX, NIE BEZPIECZENSTWO ===");

        System.out.println("Formularz HTML moze miec `<input required pattern=\"[A-Za-z]+\">` - ladny czerwony obwodek dla uzytkownika.");
        System.out.println("Atakujacy pomija PRZEGLADARKE calkowicie - wysyla zadanie BEZPOSREDNIO (np. przez `curl`/Postman/HttpClient),");
        System.out.println("z DOWOLNA tresci pola, jaka zechce - dlatego SERWER musi zwalidowac te sama regule OD NOWA, samodzielnie.");
    }

    private static void demonstrateAllowlistVsDenylist() {
        System.out.println("\n=== ALLOWLIST vs DENYLIST NA PRZYKLADZIE NAZWY UZYTKOWNIKA ===");

        String maliciousInput = "admin<script>";

        boolean denylistPasses = !maliciousInput.contains("<script>"); // "zablokuj znany zly wzorzec"
        boolean allowlistPasses = maliciousInput.matches("^[a-zA-Z0-9_]{3,20}$"); // "zaakceptuj TYLKO znany dobry wzorzec"

        System.out.println("Input: \"" + maliciousInput + "\"");
        System.out.println("Denylist (blokuj '<script>') -> zaakceptowany: " + denylistPasses + " (przeszedl by, gdyby uzyc INNEGO znacznika np. <img onerror=...>!)");
        System.out.println("Allowlist (TYLKO litery/cyfry/podkreslnik, 3-20 znakow) -> zaakceptowany: " + allowlistPasses);
        System.out.println("-> allowlist odrzuca WSZYSTKO poza jawnie dozwolonym wzorcem, niezaleznie od TEGO, JAK zlosliwy jest input.");
    }

    private record RegistrationForm(
            @NotBlank(message = "Nazwa uzytkownika nie moze byc pusta")
            @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Nazwa uzytkownika: 3-20 znakow, tylko litery/cyfry/podkreslnik")
            String username,

            @NotBlank(message = "Email nie moze byc pusty")
            @Email(message = "Nieprawidlowy format email")
            String email,

            @Min(value = 13, message = "Wiek musi wynosic co najmniej 13 lat")
            @Max(value = 120, message = "Nieprawidlowy wiek")
            int age,

            @Size(min = 8, message = "Haslo musi miec co najmniej 8 znakow")
            String password
    ) {
    }

    private static void demonstrateBeanValidationWithCollectAllErrors() {
        System.out.println("\n=== JAKARTA BEAN VALIDATION - DEKLARATYWNA WALIDACJA Z ZEBRANIEM WSZYSTKICH BLEDOW ===");

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            RegistrationForm invalidForm = new RegistrationForm("ab", "nie-jest-emailem", 10, "krotkie");
            Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(invalidForm);

            System.out.println("Formularz z WIELOMA bledami naraz -> znaleziono " + violations.size() + " naruszen:");
            for (ConstraintViolation<RegistrationForm> violation : violations) {
                System.out.println("  - " + violation.getPropertyPath() + ": " + violation.getMessage());
            }
            System.out.println("-> uzytkownik dostaje WSZYSTKIE bledy NARAZ (jeden formularz, jedna proba) - lepsze UX niz 'blad po bledzie'.");

            RegistrationForm validForm = new RegistrationForm("jan_kowalski", "jan@przyklad.pl", 25, "BezpieczneHaslo123");
            Set<ConstraintViolation<RegistrationForm>> noViolations = validator.validate(validForm);
            System.out.println("\nPoprawny formularz -> naruszen: " + noViolations.size());
        }
    }
}
