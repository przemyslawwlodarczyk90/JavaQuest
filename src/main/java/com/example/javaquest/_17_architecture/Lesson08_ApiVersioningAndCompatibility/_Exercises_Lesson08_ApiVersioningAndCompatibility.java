package com.example.javaquest._17_architecture.Lesson08_ApiVersioningAndCompatibility;

public class _Exercises_Lesson08_ApiVersioningAndCompatibility {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCompatibleVsBreakingChangeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy zmiana kompatybilna a lamiaca kontrakt API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddOptionalFieldToExistingDto {
        /*
         * 🧪 Zadanie 2:
         * Zaprojektuj `record UserDtoV1(long id, String email)`, a nastepnie
         * `UserDtoV1WithOptionalField` z dodatkowym polem `String
         * displayName` - w komentarzu potwierdz, ze to zmiana kompatybilna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyBreakingChangeInGivenFieldRename {
        /*
         * 🧪 Zadanie 3:
         * Dany jest DTO z polem `fullName` - zaproponowano zmiane na
         * `name`. W komentarzu wyjasnij, dlaczego to jest ZMIANA LAMIACA,
         * mimo ze "logicznie to ta sama wartosc".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DesignTwoVersionsOfSameDtoWithTypeChange {
        /*
         * 🧪 Zadanie 4:
         * Zaprojektuj `PriceDtoV1(String price)` (np. "99.99 PLN" jako tekst) i
         * `PriceDtoV2(double amount, String currency)` (rozbite na pola) -
         * w komentarzu wyjasnij, dlaczego przejscie V1->V2 wymaga
         * wersjonowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementPostelsLawIgnoringUnknownField {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode parsujaca prosty String "klucz=wartosc;..." na DTO z 2
         * polami, IGNORUJACA dodatkowe, nieznane klucze - przetestuj z
         * wejsciem majacym 3 pola (1 nieznane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DesignUrlBasedVersioningSimulation {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode `String routeRequest(String path)` symulujaca
         * routing wg wersji w URL (np. "/api/v1/orders" vs "/api/v2/orders") -
         * zwracajaca inna odpowiedz dla kazdej wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DesignHeaderBasedVersioningSimulation {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode `String routeByHeader(String acceptHeader)`
         * symulujaca routing wg naglowka (np. "application/vnd.app.v1+json"
         * vs "v2") - porownaj w komentarzu z podejsciem URL z Zadania 6.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DesignSharedEntityWithTwoDtoMappersForTwoVersions {
        /*
         * 🧪 Zadanie 8:
         * Zaprojektuj 1 encje `OrderEntity` i 2 mappery (do `OrderDtoV1` i
         * `OrderDtoV2`, rozne ksztalty) - zademonstruj mapowanie tej samej
         * encji do OBU wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MarkDtoVersionAsDeprecatedWithComment {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do `OrderDtoV1` z Zadania 8 komentarz "@deprecated"
         * (stylem javadoc) z data planowanego wycofania i wskazaniem V2 jako
         * zastepstwa - w komentarzu wyjasnij, dlaczego to WAZNE dla klientow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListVersioningStrategiesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) 3
         * strategie wersjonowania API z tej lekcji, z 1 zaleta i 1 wada
         * kazdej.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignThreeVersionEvolutionOfSameResource {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj 3 KOLEJNE wersje DTO dla zasobu "Uzytkownik" (V1: 2
         * pola, V2: +1 pole kompatybilne, V3: zmiana typu 1 pola - lamiaca) -
         * dla kazdej pary V(n)->V(n+1) oceń w komentarzu, czy to kompatybilna
         * czy lamiaca zmiana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMapperChainFromSharedEntityToAllThreeVersions {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj 1 wspolna encje i 3 mappery (do V1/V2/V3 z Zadania
         * 11) - zademonstruj wywolanie wszystkich trzech dla tej samej
         * instancji encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignBackwardCompatibleValidationRelaxation {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj walidacje pola "wiek" - V1 wymaga 18-99, V2 rozszerza
         * na 0-99 (ROZLUZNIENIE, wiec kompatybilne) - w komentarzu wyjasnij,
         * dlaczego ROZLUZNIENIE walidacji jest zwykle kompatybilne, a
         * ZAOSTRZENIE - zwykle NIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateValidationTighteningAsBreakingChange {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj walidacje "kod pocztowy" - V1 akceptuje DOWOLNY tekst,
         * V2 wymaga formatu "00-000" (ZAOSTRZENIE). Zademonstruj przypadek
         * danych POPRAWNYCH w V1, ktore V2 by ODRZUCILO - to jest zmiana
         * lamiaca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignAdrForChoosingVersioningStrategy {
        /*
         * 🧪 Zadanie 15:
         * Napisz PELNY ADR (`Lesson02`) dla decyzji "URL czy naglowek HTTP do
         * wersjonowania nowego publicznego API" - z realistycznym kontekstem
         * (np. liczba zewnetrznych partnerow, latwosc debugowania) i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignGracefulDeprecationTimeline {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj `record DeprecationNotice(String version, String
         * sunsetDate, String replacementVersion)` - zbuduj przyklad dla V1
         * (zastapione przez V2, data wygasniecia za 6 miesiecy) i wypisz
         * czytelny komunikat dla klientow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SimulateClientIgnoringNewOptionalFieldGracefully {
        /*
         * 🧪 Zadanie 17:
         * Napisz "stary klient" (metoda) czytajacy TYLKO 2 z 4 pol nowej
         * odpowiedzi serwera - zademonstruj, ze dziala poprawnie mimo
         * dodatkowych, nieznanych mu pol (zasada Postela).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignApiChangeRequiringNewEndpointInsteadOfVersioning {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz scenariusz, w ktorym LEPIEJ dodac ZUPELNIE
         * NOWY endpoint (np. `/orders/summary`) niz wersjonowac istniejacy -
         * kiedy nowy endpoint jest czystszym rozwiazaniem niz V2 starego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareApiVersioningWithDatabaseMigrationVersioning {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) wersjonowanie
         * API z wersjonowaniem migracji bazy danych (`_10_dao/
         * Lesson25_DatabaseMigrations`) - jakie sa PODOBIENSTWA w podejsciu
         * do zmian "juz uzywanych przez kogos" struktur?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealDtoFromJdbcChapterForVersioningReadiness {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) DTO z `_09_jdbc/Lesson19_Dto` - gdyby
         * trzeba bylo dodac nowe pole do `UserResponse`, czy bylaby to zmiana
         * kompatybilna czy lamiaca? Uzasadnij.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullTwoVersionApiForRealisticOrderSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletne 2-wersyjne API systemu zamowien: V1 (podstawowe
         * pola), V2 (dodane opcjonalne pole + 1 ZMIANA lamiaca, np. rozbicie
         * adresu na osobne pola) - z 1 wspolna encja i mapperami dla obu
         * wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementVersionRouterServingBothVersionsSimultaneously {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj "router wersji" (podobny do Strangler Router z
         * `_16_clean_code/Lesson21`) obslugujacy ZADANIA do V1 i V2 systemu z
         * Zadania 21 RowNOCZESNIE - zademonstruj oba dzialajace w tym samym
         * czasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureClientImpactOfPlannedV3BreakingChange {
        /*
         * 🧪 Zadanie 23:
         * Zaplanuj V3 systemu z Zadania 21 z KOLEJNA zmiana lamiaca - w
         * komentarzu opisz PELNY plan migracji (deprecation notice, okres
         * wsparcia V1/V2, kryteria wycofania) zanim faktycznie usuniesz
         * cokolwiek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignContractTestVerifyingBackwardCompatibility {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `boolean isBackwardCompatible(List<String> oldFields,
         * List<String> newFields)` sprawdzajaca, czy WSZYSTKIE stare pola
         * nadal istnieja w nowej wersji (podstawowa heurystyka kompatybilnosci)
         * - przetestuj dla zmiany kompatybilnej i lamiacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignFeatureFlagControlledApiRollout {
        /*
         * 🧪 Zadanie 25:
         * Polacz wersjonowanie API z "feature toggle"
         * (`_16_clean_code/Lesson21`) - zaprojektuj mechanizm, w ktorym V2
         * jest dostepne TYLKO dla wybranych klientow (np. lista dozwolonych
         * ID) przed pelnym wydaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignMultiVersionApiRespectingSingleResponsibilityInMappers {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj system z 3 wersjami API, gdzie KAZDY mapper (V1/V2/V3)
         * jest OSOBNA, mala klasa (SRP, `_16_clean_code/Lesson07`) zamiast 1
         * wielkiej klasy z if/else po wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareUrlVersusHeaderVersioningForRealClientScenario {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj TEN SAM endpoint w OBU stylach (URL i naglowek) dla
         * systemu z Zadania 21 - w komentarzu opisz, ktory bylby latwiejszy
         * do debugowania przez zespol wsparcia klienta (np. przegladajac
         * logi serwera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAdrForRetiringOldApiVersionWithSunsetDate {
        /*
         * 🧪 Zadanie 28:
         * Napisz ADR dokumentujacy DECYZJE o calkowitym wycofaniu V1 (po
         * okresie wsparcia) - z danymi o spadku uzycia jako uzasadnieniem w
         * kontekscie, i konsekwencjami (np. "klienci nadal na V1 dostana
         * blad 410 Gone").
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveApiEvolutionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do bezpiecznej ewolucji API - laczac WSZYSTKIE zasady z
         * tej lekcji (kompatybilnosc, zasada Postela, strategie
         * wersjonowania, deprecation).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullApiLifecycleForBookingPlatform {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj PELNY cykl
         * zycia API platformy rezerwacji (podobnej do Lesson06/07) - V1
         * (podstawowa), V2 (kompatybilne rozszerzenie), V3 (lamiaca zmiana z
         * pelnym planem migracji: deprecation notice, router obslugujacy
         * WSZYSTKIE 3 wersje rownoczesnie, 1 wspolna encja pod spodem).
         * Zademonstruj dzialanie WSZYSTKICH 3 wersji naraz.
         */
        public static void main(String[] args) { }
    }
}
