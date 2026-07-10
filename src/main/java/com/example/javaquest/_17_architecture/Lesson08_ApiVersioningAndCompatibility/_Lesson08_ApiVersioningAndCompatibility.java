package com.example.javaquest._17_architecture.Lesson08_ApiVersioningAndCompatibility;

public class _Lesson08_ApiVersioningAndCompatibility {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: WERSJONOWANIE API I KOMPATYBILNOSC ===");

        /*
         * ============================================================
         * 📦 DTO TO KONTRAKT - A KONTRAKTY SIE ZMIENIAJA
         * ============================================================
         * - Lesson07 pokazal, ze DTO (nie encja) definiuje PUBLICZNY
         *   kontrakt API. Ale kontrakty NIE SA wieczne - z czasem
         *   biznes wymaga nowych pol, innych regul, innego ksztaltu
         *   danych.
         * - Problem: zewnetrzni klienci (aplikacje mobilne, partnerzy,
         *   inne zespoly) JUZ PISALI kod przeciwko OBECNEMU ksztaltowi
         *   DTO - nie mozesz go po prostu "zmienic" bez konsekwencji.
         */
        System.out.println("DTO = kontrakt PUBLICZNY - zewnetrzni klienci juz go uzywaja, zmiana ma REALNY koszt.");

        /*
         * ============================================================
         * 🔹 ZMIANY KOMPATYBILNE (NON-BREAKING) VS LAMIACE (BREAKING)
         * ============================================================
         * - KOMPATYBILNE (mozna robic bez wersjonowania):
         *   - dodanie NOWEGO, OPCJONALNEGO pola do odpowiedzi,
         *   - dodanie NOWEGO endpointu,
         *   - dodanie NOWEGO, opcjonalnego parametru zapytania z sensowna
         *     wartoscia domyslna.
         * - LAMIACE (WYMAGAJA wersjonowania/planu migracji):
         *   - usuniecie/przemianowanie istniejacego pola,
         *   - zmiana TYPU istniejacego pola (np. String -> liczba),
         *   - zmiana ZNACZENIA istniejacego pola (ta sama nazwa, inna
         *     semantyka - NAJGROZNIEJSZA, bo kompiluje/parsuje sie bez
         *     bledu, ale daje ZLE wyniki),
         *   - zaostrzenie walidacji tak, ze poprzednio poprawne zadania
         *     zaczynaja byc odrzucane.
         */
        demonstrateCompatibleVsBreakingChange();

        /*
         * ============================================================
         * 🔍 ZASADA POSTELA (ROBUSTNESS PRINCIPLE)
         * ============================================================
         * - Jon Postel (RFC 761, 1980): "badz konserwatywny w tym, co
         *   wysylasz, a liberalny w tym, co przyjmujesz".
         * - W praktyce API: parser odpowiedzi/zadan powinien IGNOROWAC
         *   NIEZNANE pola zamiast rzucac blad - to pozwala DODAWAC nowe
         *   pola do odpowiedzi BEZ psucia starszych klientow, ktorzy o
         *   nich nie wiedza.
         */
        demonstrateForwardCompatibilityIgnoringUnknownFields();

        /*
         * ============================================================
         * 🔹 STRATEGIE WERSJONOWANIA (KROTKI PRZEGLAD)
         * ============================================================
         * - W URL (`/api/v1/products`, `/api/v2/products`) - najbardziej
         *   widoczne i proste, ale "zaszywa" wersje w kazdym linku.
         * - W naglowku HTTP (`Accept: application/vnd.myapp.v2+json`) -
         *   czystszy URL, ale mniej oczywiste dla kogos przegladajacego
         *   ruch/dokumentacje.
         * - Przez parametr zapytania (`?version=2`) - prosty, ale latwo
         *   pominac przez przypadek (traktowany jako "opcjonalny").
         * - ZADNA z tych strategii nie jest "jedynie sluszna" - wybor to
         *   decyzja architektoniczna (Lesson01), warta ADR (Lesson02).
         */
        System.out.println("\n3 typowe strategie: URL, naglowek HTTP, parametr zapytania - wybor to decyzja warta ADR (Lesson02).");

        /*
         * ============================================================
         * 🔍 OBSLUGA WIELU WERSJI RowNOCZESNIE Z 1 WEWNETRZNEGO MODELU
         * ============================================================
         * - Nie trzeba utrzymywac 2 kopii CALEJ logiki biznesowej dla 2
         *   wersji API - typowe podejscie: 1 WSPOLNY model wewnetrzny
         *   (encja/model domenowy), a KAZDA wersja API ma WLASNY,
         *   NIEZALEZNY DTO + mapper (dokladnie mechanizm z Lesson07).
         */
        demonstrateServingTwoApiVersionsFromOneInternalModel();

        /*
         * ============================================================
         * 🔹 DEPRECATION: WYCOFYWANIE STAREJ WERSJI STOPNIOWO
         * ============================================================
         * - Stara wersja NIE znika z dnia na dzien - oznacza sie ja jako
         *   "deprecated" (z jasnym komunikatem/data wygasniecia),
         *   monitoruje faktyczne uzycie, i USUWA dopiero, gdy ruch na
         *   niej spadnie do zera (albo minie uzgodniony okres wsparcia).
         * - To DOKLADNIE ten sam rytm co Strangler Fig
         *   (`_16_clean_code/Lesson21`) - stopniowe przejscie zamiast
         *   nagle "wylaczonej" starej wersji.
         */
        System.out.println("\nWycofywanie starej wersji = stopniowe (jak Strangler Fig, _16_clean_code/Lesson21), nie nagle wylaczenie.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zmiany kompatybilne (dodanie opcjonalnego pola/endpointu) nie
         *   wymagaja wersjonowania - zmiany lamiace (usuniecie/zmiana
         *   typu/znaczenia pola) wymagaja.
         * - Zasada Postela: ignoruj nieznane pola przy parsowaniu -
         *   umozliwia dodawanie pol bez psucia starszych klientow.
         * - 3 strategie wersjonowania (URL/naglowek/parametr) - wybor to
         *   decyzja architektoniczna warta ADR.
         * - Wiele wersji moze korzystac z 1 wspolnego modelu wewnetrznego -
         *   kazda wersja ma WLASNY DTO+mapper (Lesson07).
         * - Wycofywanie starej wersji jest STOPNIOWE (jak Strangler Fig).
         * - W kolejnej lekcji (Lesson09): organizacja pakietow - czy dzielic
         *   kod wg warstwy (controller/service/repository), czy wg
         *   funkcji biznesowej.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateCompatibleVsBreakingChange() {
        System.out.println("\n=== ZMIANA KOMPATYBILNA VS ZMIANA LAMIACA ===");

        ProductDtoV1 v1 = new ProductDtoV1(1L, "Laptop", 3000.0);
        System.out.println("[V1, ORYGINAL] " + v1);

        // KOMPATYBILNA: dodanie NOWEGO, opcjonalnego pola - starzy klienci
        // parsujacy TYLKO pola V1 nadal dzialaja bez zmian.
        ProductDtoV1WithOptionalField v1Extended = new ProductDtoV1WithOptionalField(1L, "Laptop", 3000.0, "Elektronika");
        System.out.println("[KOMPATYBILNA ZMIANA] dodano opcjonalne pole 'category': " + v1Extended);
        System.out.println("-> Klient V1, ktory NIE ZNA pola 'category', dalej odczyta id/name/price bez problemu.");

        // LAMIACA: zmiana TYPU pola price z double na String (np. "3000.00 PLN") -
        // KAZDY istniejacy klient parsujacy 'price' jako liczbe PRZESTANIE dzialac.
        System.out.println("[LAMIACA ZMIANA] zmiana typu 'price' z double na String -> KAZDY dotychczasowy klient sie zepsuje.");
    }

    record ProductDtoV1(long id, String name, double price) {
    }

    record ProductDtoV1WithOptionalField(long id, String name, double price, String category) {
    }

    private static void demonstrateForwardCompatibilityIgnoringUnknownFields() {
        System.out.println("\n=== ZASADA POSTELA: IGNORUJ NIEZNANE POLA ===");

        // Symulacja: "serwer" wysyla odpowiedz z NOWYM polem 'warrantyMonths',
        // ktorego "stary klient" NIE ZNA - parsujemy TYLKO znane pola.
        String serverResponseSimulated = "id=1;name=Laptop;price=3000.0;warrantyMonths=24";

        ProductDtoV1 parsedByOldClient = parseKnownFieldsOnly(serverResponseSimulated);
        System.out.println("Surowa odpowiedz serwera (z NOWYM polem): " + serverResponseSimulated);
        System.out.println("Stary klient sparsowal TYLKO znane pola: " + parsedByOldClient);
        System.out.println("-> 'warrantyMonths' zostalo ZIGNOROWANE, nie spowodowalo bledu parsowania.");
    }

    private static ProductDtoV1 parseKnownFieldsOnly(String rawResponse) {
        String[] fields = rawResponse.split(";");
        long id = Long.parseLong(fields[0].split("=")[1]);
        String name = fields[1].split("=")[1];
        double price = Double.parseDouble(fields[2].split("=")[1]);
        // fields[3] ('warrantyMonths') CELOWO zignorowane - stary klient go nie zna.
        return new ProductDtoV1(id, name, price);
    }

    private static void demonstrateServingTwoApiVersionsFromOneInternalModel() {
        System.out.println("\n=== 2 WERSJE API, 1 WSPOLNY MODEL WEWNETRZNY ===");

        ProductEntity entity = new ProductEntity(1L, "Laptop", 3000.0, "Elektronika", 45.0);

        ProductDtoV1 responseV1 = mapToV1(entity);
        ProductDtoV2 responseV2 = mapToV2(entity);

        System.out.println("Klient V1 dostaje: " + responseV1);
        System.out.println("Klient V2 dostaje: " + responseV2);
        System.out.println("-> OBIE odpowiedzi pochodza z TEGO SAMEGO ProductEntity - rozny mapper na kazda wersje.");
    }

    /** Wspolny model wewnetrzny - NIE wie NIC o wersjach API (Lesson07: encja niezalezna od kontraktu). */
    record ProductEntity(long id, String name, double price, String category, double weightKg) {
    }

    record ProductDtoV2(long id, String name, double price, String category) {
    }

    private static ProductDtoV1 mapToV1(ProductEntity entity) {
        return new ProductDtoV1(entity.id(), entity.name(), entity.price());
    }

    private static ProductDtoV2 mapToV2(ProductEntity entity) {
        return new ProductDtoV2(entity.id(), entity.name(), entity.price(), entity.category());
    }
}
