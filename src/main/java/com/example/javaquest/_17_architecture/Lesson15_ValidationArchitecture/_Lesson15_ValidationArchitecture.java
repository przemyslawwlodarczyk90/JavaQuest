package com.example.javaquest._17_architecture.Lesson15_ValidationArchitecture;

import java.util.HashSet;
import java.util.Set;

public class _Lesson15_ValidationArchitecture {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: ARCHITEKTURA WALIDACJI ===");

        /*
         * ============================================================
         * 📦 3 RODZAJE WALIDACJI - 3 ROZNE MIEJSCA W ARCHITEKTURZE
         * ============================================================
         * - "Walidacja" to NIE 1 rzecz - to CO NAJMNIEJ 3 rozne pytania,
         *   z ktorych KAZDE naturalnie nalezy do INNEJ warstwy:
         *   1) FORMAT: "czy to poprawnie zbudowane zadanie?" (DTO,
         *      Lesson07) - NIE wymaga zadnej wiedzy o stanie systemu.
         *   2) NIEZMIENNIK DOMENOWY: "czy ten obiekt biznesowy jest
         *      wewnetrznie spojny?" (encja, Lesson05) - NIE wymaga
         *      zadnego dostepu do bazy/repozytorium.
         *   3) REGULA BIZNESOWA: "czy TA operacja jest dozwolona TERAZ, w
         *      TYM stanie systemu?" (Service/Use Case) - WYMAGA dostepu
         *      do repozytorium/wspolpracownikow.
         */
        System.out.println("Walidacja = 3 ROZNE pytania: format (DTO) / niezmiennik (encja) / regula biznesowa (Service).");

        /*
         * ============================================================
         * 🔹 POZIOM 1: WALIDACJA FORMATU NA GRANICY (DTO)
         * ============================================================
         * - Sprawdza, czy zadanie jest w ogole SENSOWNIE ZBUDOWANE - pola
         *   wymagane sa obecne, format e-maila zawiera "@", dlugosc
         *   Stringa miesci sie w rozsadnym limicie.
         * - NIE WYMAGA zadnego zapytania do bazy danych - to NAJSZYBSZA i
         *   NAJTANSZA forma walidacji, wiec powinna byc pierwsza linia
         *   obrony (fail-fast, `_16_clean_code/Lesson17`).
         */
        demonstrateFormatValidationAtDtoLevel();

        /*
         * ============================================================
         * 🔍 POZIOM 2: NIEZMIENNIK DOMENOWY W ENCJI (BOGATY MODEL, LESSON05)
         * ============================================================
         * - Sprawdza, czy sam obiekt biznesowy jest WEWNETRZNIE SPOJNY -
         *   niezaleznie od KONTEKSTU wywolania (encja MUSI byc spojna
         *   ZAWSZE, w KAZDYM miejscu, gdzie jest tworzona).
         * - RowNIEZ NIE WYMAGA dostepu do repozytorium/bazy danych -
         *   regula "data konca PO dacie poczatku" nie zalezy od NICZEGO
         *   poza samymi wartosciami przekazanymi do konstruktora.
         */
        demonstrateDomainInvariantValidationInEntity();

        /*
         * ============================================================
         * 🔹 POZIOM 3: REGULA BIZNESOWA W SERVICE (WYMAGA STANU SYSTEMU)
         * ============================================================
         * - Sprawdza, czy KONKRETNA operacja jest dozwolona TERAZ, w
         *   AKTUALNYM stanie systemu - np. "czy ten e-mail jest JUZ
         *   zajety" WYMAGA zapytania do repozytorium (Lesson04/Lesson11).
         * - Ta walidacja MUSI zyc w Service (Use Case) - encja NIE MOZE
         *   jej wykonac SAMA, bo encja NIE MA (i NIE POWINNA MIEC)
         *   dostepu do repozytorium (naruszenie Dependency Rule,
         *   Lesson10 - domena zalezna od infrastruktury).
         */
        demonstrateBusinessRuleValidationInService();

        /*
         * ============================================================
         * 🔍 KOLEJNOSC: OD NAJTANSZEJ DO NAJDROZSZEJ (FAIL-FAST W PRAKTYCE)
         * ============================================================
         * - Sensowna kolejnosc: NAJPIERW format (tanie, brak I/O), POTEM
         *   niezmiennik domenowy (tanie, brak I/O), na KONCU regula
         *   biznesowa (kosztowna, wymaga zapytania do bazy).
         * - Odwrocenie tej kolejnosci (np. najpierw zapytanie do bazy,
         *   POTEM sprawdzenie formatu) marnuje zasoby na sprawdzanie
         *   regul biznesowych dla danych, ktore i tak zostana odrzucone
         *   na najtanszym poziomie.
         */
        demonstrateValidationOrderCheapestFirst();

        /*
         * ============================================================
         * 🔹 ANTY-WZORZEC: WSZYSTKO W 1 METODZIE "validate()" W KONTROLERZE
         * ============================================================
         * - Dokladnie ten sam problem co "gruby kontroler" (Lesson04) -
         *   jesli KONTROLER sam sprawdza WSZYSTKIE 3 poziomy naraz
         *   (format + niezmiennik + regule biznesowa wymagajaca
         *   repozytorium), kontroler musi znac ZBYT WIELE (wlacznie z
         *   dostepem do repozytorium) - narusza granice warstw
         *   (Lesson03) i architektury portow (Lesson10-11).
         */
        System.out.println("\nAnty-wzorzec: WSZYSTKIE 3 poziomy walidacji w 1 metodzie kontrolera - jak 'gruby kontroler' (Lesson04).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Walidacja formatu (DTO): tania, brak I/O, NAJPIERW.
         * - Niezmiennik domenowy (encja): tania, brak I/O, ZAWSZE
         *   egzekwowany, niezaleznie od kontekstu.
         * - Regula biznesowa (Service): kosztowna, wymaga repozytorium,
         *   NA KONCU.
         * - Kolejnosc fail-fast: od najtanszej do najdrozszej.
         * - Anty-wzorzec: wszystkie 3 poziomy w 1 miejscu (kontroler).
         * - W kolejnej lekcji (Lesson16): architektura obslugi bledow -
         *   co sie dzieje, gdy KTORAKOLWIEK z tych 3 walidacji zawiedzie,
         *   i jak to spojnie zakomunikowac na granicy systemu.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    // ------------------------------------------------------------------
    // POZIOM 1: Format (DTO)
    // ------------------------------------------------------------------

    record RegisterUserRequestDto(String email, String password, int age) {
    }

    private static ValidationResult validateFormat(RegisterUserRequestDto request) {
        if (request.email() == null || !request.email().contains("@")) {
            return ValidationResult.invalid("niepoprawny format e-maila");
        }
        if (request.password() == null || request.password().length() < 8) {
            return ValidationResult.invalid("haslo musi miec min. 8 znakow");
        }
        return ValidationResult.ok();
    }

    private static void demonstrateFormatValidationAtDtoLevel() {
        System.out.println("\n=== POZIOM 1: WALIDACJA FORMATU (DTO) - BEZ DOSTEPU DO BAZY ===");

        RegisterUserRequestDto badFormat = new RegisterUserRequestDto("zly-email", "sekret123", 25);
        System.out.println("Zly format: " + validateFormat(badFormat));

        RegisterUserRequestDto goodFormat = new RegisterUserRequestDto("ala@example.com", "sekret123", 25);
        System.out.println("Dobry format: " + validateFormat(goodFormat));
    }

    record ValidationResult(boolean valid, String errorMessage) {
        static ValidationResult ok() {
            return new ValidationResult(true, null);
        }

        static ValidationResult invalid(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }

        @Override
        public String toString() {
            return valid ? "OK" : "BLAD: " + errorMessage;
        }
    }

    // ------------------------------------------------------------------
    // POZIOM 2: Niezmiennik domenowy (encja)
    // ------------------------------------------------------------------

    /** Bogata encja (Lesson05) - EGZEKWUJE niezmiennik "wiek >= 0" ZAWSZE, bez wzgledu na kontekst. */
    static class User {
        private final String email;
        private final int age;

        User(String email, int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Wiek nie moze byc ujemny: " + age);
            }
            this.email = email;
            this.age = age;
        }

        String describe() {
            return email + " (wiek: " + age + ")";
        }
    }

    private static void demonstrateDomainInvariantValidationInEntity() {
        System.out.println("\n=== POZIOM 2: NIEZMIENNIK DOMENOWY (ENCJA) - BEZ DOSTEPU DO BAZY ===");

        User validUser = new User("ala@example.com", 25);
        System.out.println("Poprawny uzytkownik: " + validUser.describe());

        try {
            new User("zly@example.com", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Niezmiennik odrzucil niepoprawny wiek: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // POZIOM 3: Regula biznesowa (Service, wymaga repozytorium)
    // ------------------------------------------------------------------

    interface UserRepositoryPort {
        boolean existsByEmail(String email);

        void save(User user);
    }

    static class InMemoryUserRepository implements UserRepositoryPort {
        private final Set<String> emails = new HashSet<>();

        @Override
        public boolean existsByEmail(String email) {
            return emails.contains(email);
        }

        @Override
        public void save(User user) {
            emails.add(user.email);
        }
    }

    static class UserRegistrationService {
        private final UserRepositoryPort repository;

        UserRegistrationService(UserRepositoryPort repository) {
            this.repository = repository;
        }

        ValidationResult register(RegisterUserRequestDto request) {
            // Regula biznesowa WYMAGAJACA dostepu do repozytorium - NIE MOZE
            // zyc w encji User (encja nie ma i nie powinna miec repozytorium).
            if (repository.existsByEmail(request.email())) {
                return ValidationResult.invalid("e-mail juz zarejestrowany: " + request.email());
            }
            User user = new User(request.email(), request.age()); // niezmiennik encji (poziom 2) sprawdzony TUTAJ
            repository.save(user);
            return ValidationResult.ok();
        }
    }

    private static void demonstrateBusinessRuleValidationInService() {
        System.out.println("\n=== POZIOM 3: REGULA BIZNESOWA (SERVICE) - WYMAGA REPOZYTORIUM ===");

        UserRepositoryPort repository = new InMemoryUserRepository();
        UserRegistrationService service = new UserRegistrationService(repository);

        RegisterUserRequestDto request = new RegisterUserRequestDto("bartek@example.com", "sekret123", 30);
        System.out.println("Pierwsza rejestracja: " + service.register(request));
        System.out.println("Druga rejestracja (TEN SAM e-mail): " + service.register(request));
    }

    private static void demonstrateValidationOrderCheapestFirst() {
        System.out.println("\n=== KOLEJNOSC: NAJTANSZA WALIDACJA NAJPIERW ===");

        UserRepositoryPort repository = new InMemoryUserRepository();
        UserRegistrationService service = new UserRegistrationService(repository);

        RegisterUserRequestDto badFormatRequest = new RegisterUserRequestDto("zly-email", "krotkie", 25);

        ValidationResult formatResult = validateFormat(badFormatRequest);
        if (!formatResult.valid()) {
            System.out.println("Odrzucone na POZIOMIE 1 (format) - ZERO zapytan do bazy danych: " + formatResult);
            return;
        }
        // Regula biznesowa (poziom 3, kosztowna) wywolana by TYLKO, gdyby poziom 1 przeszedl:
        System.out.println(service.register(badFormatRequest));
    }
}
