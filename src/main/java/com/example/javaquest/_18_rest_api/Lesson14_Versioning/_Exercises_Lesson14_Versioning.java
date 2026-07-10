package com.example.javaquest._18_rest_api.Lesson14_Versioning;

public class _Exercises_Lesson14_Versioning {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ClassifyFiveChangesAsCompatibleOrBreaking {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: dla 5 zmian (dodanie opcjonalnego pola, usuniecie
         * pola, zmiana typu pola, dodanie nowego endpointu, zmiana nazwy
         * pola) okresl, czy sa kompatybilne czy breaking.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementV1Endpoint {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/v1/products` zwracajacym produkty w
         * "starym" ksztalcie (1 pole "price").
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementV2EndpointSideBySide {
        /*
         * 🧪 Zadanie 3:
         * Dodaj `/v2/products` zwracajace produkty w "nowym" ksztalcie
         * (np. zagniezdzony obiekt "price": {"amount":..., "currency":...}) -
         * zweryfikuj, ze OBA endpointy dzialaja RÓWNOLEGLE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementHeaderBasedVersioning {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj wersjonowanie przez naglowek "X-API-Version" dla
         * TEGO SAMEGO URI - zweryfikuj rozne odpowiedzi dla wartosci "1" i "2".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyUriVersioningIsMostPopular {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, dlaczego wersjonowanie w URI jest
         * NAJPOPULARNIEJSZE w praktyce, mimo ze "teoretycznie mniej
         * czyste" niz media type.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementDefaultVersionWhenHeaderMissing {
        /*
         * 🧪 Zadanie 6:
         * Rozszerz Zadanie 4 - jesli klient NIE wysle "X-API-Version",
         * serwer domyslnie odpowiada NAJSTARSZA wspierana wersja -
         * zweryfikuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddDeprecationHeaderToOldVersion {
        /*
         * 🧪 Zadanie 7:
         * Dodaj naglowek "Deprecation: true" do odpowiedzi z v1 (Zadanie 2)
         * - zweryfikuj po stronie klienta, ze naglowek dotarl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddSunsetHeaderWithDate {
        /*
         * 🧪 Zadanie 8:
         * Dodaj naglowek "Sunset" z konkretna data do odpowiedzi v1 -
         * zweryfikuj format daty (RFC 1123, np. "Sat, 31 Dec 2026
         * 23:59:59 GMT").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareFourVersioningStrategies {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj 4 strategie wersjonowania (URI,
         * naglowek, media type, query param) w tabeli - kolumny:
         * widocznosc, zgodnosc z REST, popularnosc w praktyce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ImplementQueryParamVersioning {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj wersjonowanie przez `?version=1` / `?version=2` -
         * porownaj kod z wersjonowaniem naglowkiem (Zadanie 4).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementBackwardCompatibleFieldAddition {
        /*
         * 🧪 Zadanie 11:
         * Dodaj NOWE, opcjonalne pole do ISTNIEJACEGO endpointu (BEZ
         * tworzenia nowej wersji) - zweryfikuj, ze "stary" kod klienta
         * (ignorujacy nieznane pole) WCIAZ dziala poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFieldRenameWithDualSupport {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj ZMIANE nazwy pola (np. "name" -> "fullName") w
         * SPOSOB KOMPATYBILNY - zwracaj OBA pola naraz przez okres
         * przejsciowy zamiast od razu tworzyc nowa wersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementApiVersionNegotiationMiddleware {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj "middleware" odczytujacy wersje z DOWOLNEGO z 3
         * mozliwych zrodel (URI prefix, naglowek, query param) wg
         * ustalonego priorytetu - zweryfikuj kolejnosc pierwszenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSharedBusinessLogicAcrossVersions {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj v1 i v2 UZYWAJACE tej samej, WSPOLNEJ logiki
         * biznesowej (1 serwis wewnetrzny) - roznica MIEDZY wersjami
         * powinna byc TYLKO w warstwie mapowania na DTO odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementVersionSpecificValidationRules {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj sytuacje, gdzie v2 ma DODATKOWA regule walidacji
         * (Lesson13), ktorej v1 nie ma (dla zachowania kompatybilnosci
         * wstecznej) - zweryfikuj rozne zachowanie obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementDeprecationWarningInResponseBody {
        /*
         * 🧪 Zadanie 16:
         * Oprocz naglowka Deprecation, dodaj informacje w SAMYM CIELE
         * odpowiedzi (np. pole "_meta.deprecationNotice") - w komentarzu
         * uzasadnij, dlaczego OBA miejsca (naglowek + cialo) maja sens.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementVersionSunsetEnforcement {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj rzeczywiste WYLACZENIE starej wersji po dacie
         * "Sunset" - requesty do v1 PO tej dacie dostaja 410 Gone
         * (Lesson05) z odnosnikiem do dokumentacji migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureMaintenanceCostOfMultipleVersions {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz w komentarzu, JAKI koszt utrzymania
         * powoduje wspieranie 3 rownoleglych wersji API (kod, testy,
         * dokumentacja) - jaka strategie ograniczenia tego kosztu
         * moglbys zastosowac?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementVersionUsageAnalytics {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj zliczanie liczby requestow PER wersja API (Mapa w
         * pamieci) - po serii testowych wywolan wypisz, ktora wersja jest
         * NADAL najczesciej uzywana (przydatne do decyzji o sunset).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementGracefulFallbackForUnknownVersion {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj obsluge NIEZNANEJ wersji (np. "X-API-Version: 99") -
         * zwroc 400 z czytelnym komunikatem listujacym WSPIERANE wersje,
         * zamiast po prostu 404 lub domyslnego zachowania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullVersionRoutingFramework {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny "framework" routingu wersji - rejestracja
         * handlerow per (sciezka, wersja), automatyczny wybor NAJBLIZSZEJ
         * wspieranej wersji, gdy dokladne dopasowanie nie istnieje (np.
         * klient prosi o v1.5, dostaje v1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementContractTestsAcrossVersions {
        /*
         * 🧪 Zadanie 22:
         * Napisz "test kontraktu" weryfikujacy, ze v1 i v2 zwracaja
         * SPOJNE (nie sprzeczne) dane dla TEGO SAMEGO zasobu - tylko w
         * INNYM ksztalcie - wychwyc przypadek, gdyby v2 mial "inne dane",
         * a nie tylko inny ksztalt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementAutomaticFieldMappingBetweenVersions {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj automatyczny mapper miedzy ksztaltem v1 i v2 (np.
         * przez deklaratywna Mape "stare pole"->"nowe pole") - uzyj go do
         * wygenerowania odpowiedzi v1 Z ODPOWIEDZI v2, zamiast pisac 2
         * osobne implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementVersionedWebhookPayloadCompatibility {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj wersjonowanie DLA WEBHOOKOW (API wysyla dane DO
         * klienta, nie odwrotnie) - klient rejestruje, jakiej wersji
         * payloadu oczekuje, serwer respektuje to przy WYSYLANIU zdarzen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCanaryRolloutForNewVersion {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj STOPNIOWE wdrazanie nowej wersji - np. tylko 10%
         * requestow (na podstawie hasha ID klienta) dostaje v2, reszta v1 -
         * zweryfikuj rozklad na duzej probce requestow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementVersionMigrationGuideGenerator {
        /*
         * 🧪 Zadanie 26:
         * Napisz narzedzie automatycznie generujace "przewodnik migracji"
         * (tekst) na podstawie ROZNIC miedzy zdefiniowanymi schematami v1
         * i v2 (dodane/usuniete/zmienione pola) - wykorzystaj podejscie z
         * `_18_rest_api/Lesson08` Zadanie 26 (JSON diff).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementBreakingChangeDetectionInCi {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj narzedzie porownujace 2 "schematy" API (Mapy
         * pol->typ) i WYKRYWAJACE automatycznie zmiany BREAKING
         * (usuniecie/zmiana typu) vs kompatybilne (dodanie opcjonalnego
         * pola) - symulacja bramki jakosci w CI/CD.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementMultiVersionLoadTestComparison {
        /*
         * 🧪 Zadanie 28:
         * Zmierz i porownaj wydajnosc v1 vs v2 dla 10 000 requestow -
         * zweryfikuj, czy dodatkowa zlozonosc v2 (np. zagniezdzone
         * obiekty) wprowadza mierzalny narzut wydajnosciowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementClientSdkVersionCompatibilityMatrix {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj "macierz kompatybilnosci" (Mapa wersja-klienta ->
         * wspierane-wersje-API) i walidator sprawdzajacy, czy dany klient
         * (po naglowku "User-Agent" z wersja SDK) MOZE bezpiecznie
         * wywolac dana wersje API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiVersionApiWithFullLifecycle {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE mini-API z 3 wersjami (v1 deprecated+sunset,
         * v2 aktywna, v3 "beta") - kazda z wlasnym ksztaltem odpowiedzi,
         * wlasciwymi naglowkami cyklu zycia (Deprecation/Sunset), i
         * wspolna logika biznesowa pod spodem - napisz "test suite"
         * weryfikujacy poprawne zachowanie wszystkich 3 wersji naraz.
         */
        public static void main(String[] args) { }
    }
}
