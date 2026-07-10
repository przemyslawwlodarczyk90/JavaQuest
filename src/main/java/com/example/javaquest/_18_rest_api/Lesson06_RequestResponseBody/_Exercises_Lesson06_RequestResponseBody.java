package com.example.javaquest._18_rest_api.Lesson06_RequestResponseBody;

public class _Exercises_Lesson06_RequestResponseBody {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListFourBodyFormats {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 4 formaty ciala requestu (JSON, XML,
         * form-urlencoded, multipart/form-data) - dla kazdego 1 typowy
         * przypadek uzycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementPostAcceptingJsonBody {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z POST `/notes` przyjmujacym JSON
         * `{"content": "..."}` i zwracajacym utworzony zasob z nadanym ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementGetReturningJsonArray {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj GET `/notes` zwracajacy WSZYSTKIE notatki jako
         * tablice JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VerifyEmptyBodyForDelete {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj DELETE `/notes/{id}` i zweryfikuj po stronie
         * klienta, ze odpowiedz ma status 204 i PUSTE cialo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhenBodyShouldBeEmpty {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wymien min. 3 sytuacje, w ktorych cialo powinno
         * byc PUSTE (request lub odpowiedz) - z uzasadnieniem dla kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareBareArrayAndEnvelopeStyles {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: napisz przykladowa odpowiedz dla listy 2
         * uzytkownikow w stylu "gola tablica" i w stylu "koperta" -
         * porownaj czytelnosc obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SetContentTypeWithCharset {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj endpoint jawnie ustawiajacy naglowek
         * "Content-Type: application/json; charset=utf-8" - zweryfikuj po
         * stronie klienta, ze naglowek dotarl w tej dokladnej postaci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_HandleMissingRequiredFieldInBody {
        /*
         * 🧪 Zadanie 8:
         * Rozszerz POST `/notes` tak, zeby zwracal 400, jesli pole
         * "content" jest nieobecne lub puste w ciele requestu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SendRequestWithPolishCharactersInBody {
        /*
         * 🧪 Zadanie 9:
         * Wyslij POST z tekstem zawierajacym polskie znaki (np. "zolw",
         * "gora") w ciele - zweryfikuj, ze serwer odczytal je poprawnie
         * (porownaj oryginal z odczytana wartoscia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainDifferenceBetweenHeadersAndBody {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij roznice miedzy naglowkami (metadane) a
         * cialem (dane wlasciwe) - podaj po 2 przyklady tego, co powinno
         * trafic do kazdego z nich.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementNestedJsonBodyStructure {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj POST `/orders` przyjmujacy ZAGNIEZDZONY JSON
         * (np. {"customer": {"name": "..."}, "items": [{"name":"...",
         * "quantity":2}]}) - odczytaj i wypisz wartosci z obu poziomow
         * zagniezdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementConsistentEnvelopeAcrossEndpoints {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj min. 2 endpointy zwracajace dane w SPOJNYM stylu
         * "koperty" ({"data": ..., "meta": {...}}) - zweryfikuj identyczna
         * strukture zewnetrzna dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementPartialUpdateBodyWithOnlyChangedFields {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj PATCH przyjmujacy cialo z TYLKO WYBRANYMI polami
         * (np. {"price": 99.99}) - zaktualizuj TYLKO obecne pola,
         * pozostaw reszte zasobu bez zmian - zweryfikuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ValidateBodySizeLimit {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj sprawdzanie rozmiaru ciala PRZED jego pelnym
         * przetworzeniem (na podstawie Content-Length) - odrzuc (413)
         * cialo wieksze niz 1KB, zweryfikuj dla obu przypadkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementArrayBodyForBulkCreate {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj POST `/notes/bulk` przyjmujacy TABLICE obiektow
         * (nie pojedynczy obiekt) - utworz WSZYSTKIE zasoby naraz, zwroc
         * tablice utworzonych elementow z nadanymi ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareFormUrlencodedVsJsonBody {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj 2 wersje TEGO SAMEGO endpointu - jedna przyjmujaca
         * "application/x-www-form-urlencoded" (recznie sparsuj klucz=wartosc),
         * druga "application/json" - porownaj kod parsowania obu w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_HandleMalformedJsonGracefully {
        /*
         * 🧪 Zadanie 17:
         * Wyslij celowo ZLE SFORMATOWANY JSON (np. brakujacy nawias) -
         * obsluz wyjatek parsowania po stronie serwera i zwroc 400 z
         * czytelnym komunikatem, zamiast 500/wyjatku "wyciekajacego" do klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementResponseWithNullVsMissingField {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj endpoint zwracajacy zasob, gdzie 1 pole ma
         * wartosc `null`, a inne jest CALKOWICIE POMINIETE w JSON - w
         * komentarzu wyjasnij semantyczna roznice miedzy "null" a
         * "brak pola" (istotne przy PATCH, Lesson04).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementRequestBodyValidationWithMultipleErrors {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj walidacje ciala zbierajaca WSZYSTKIE bledy naraz
         * (nie tylko pierwszy napotkany) - zwroc 400 z lista wszystkich
         * naruszen w ciele odpowiedzi (foreshadowing Lesson13).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureBodySizeDifferenceJsonVsXml {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj TE SAME dane (np. 5 produktow) jako JSON i jako recznie
         * napisany XML - porownaj rozmiar w bajtach obu reprezentacji -
         * skomentuj wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementStreamingLargeJsonArrayResponse {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj endpoint zwracajacy DUZA tablice (np. 10 000
         * elementow) jako chunked response (Lesson01) generowana W LOCIE,
         * bez budowania calego JSON w pamieci na raz - zmierz uzycie
         * pamieci w porownaniu do wersji "zbuduj wszystko na raz".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRequestBodySchemaValidator {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj prosty walidator "schematu" ciala - metoda
         * przyjmujaca Mape pol->typ oczekiwany (np. "name"->String,
         * "price"->Double) i sprawdzajaca odebrany JSON wzgledem niego -
         * przetestuj dla poprawnych i niepoprawnych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConditionalResponseShapeByAcceptHeader {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj endpoint zwracajacy TA SAMA dane w 2 KSZTALTACH
         * (gola tablica vs koperta) w zaleznosci od naglowka
         * "X-Response-Style" - zweryfikuj oba warianty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRequestBodyCompressionSupport {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj obsluge ciala requestu skompresowanego GZIP
         * (naglowek "Content-Encoding: gzip") - odkompresuj po stronie
         * serwera przed sparsowaniem JSON - zweryfikuj dla skompresowanego
         * i nieskompresowanego requestu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildBidirectionalDtoMapperForRequestResponse {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj mapper konwertujacy MIEDZY wewnetrznym modelem domenowym
         * a osobnymi DTO dla requestu (CreateProductRequest) i odpowiedzi
         * (ProductResponse) - zademonstruj, ze te 2 ksztalty MOGA sie
         * roznic (np. odpowiedz ma dodatkowe pole "createdAt").
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementIdempotentBulkUpsertWithMixedResults {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj POST `/products/bulk-upsert` przyjmujacy tablice
         * produktow - dla kazdego elementu zwroc W ODPOWIEDZI, czy zostal
         * utworzony czy zaktualizowany (np. {"id":1,"action":"updated"}) -
         * zweryfikuj dla mieszanki nowych i istniejacych ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_HandleUnicodeEdgeCasesInBody {
        /*
         * 🧪 Zadanie 27:
         * Przetestuj cialo zawierajace emoji i znaki spoza podstawowej
         * plaszczyzny Unicode (np. "🎉") - zweryfikuj poprawny "round-trip"
         * (wyslane == odczytane) przez cala sciezke request->serwer->odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCircularReferenceProtectionInSerialization {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj model z POTENCJALNA referencja cykliczna (np. Order ma
         * liste Item, Item ma referencje do Order) - zaimplementuj
         * bezpieczna serializacje (np. przez osobne DTO bez cyklu) -
         * zademonstruj co by sie stalo bez tego zabezpieczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildContentLengthMismatchDetector {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj "middleware" weryfikujacy PO WYSLANIU odpowiedzi,
         * czy zadeklarowany Content-Length zgadza sie z rzeczywista liczba
         * wyslanych bajtow - zaloguj ostrzezenie przy niezgodnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullRequestResponseBodyAuditLog {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj kompletny system logowania - dla KAZDEGO requestu
         * zapisz (do listy w pamieci) metode, sciezke, rozmiar ciala
         * requestu i odpowiedzi, oraz Content-Type obu - po serii
         * testowych wywolan wypisz podsumowanie w formie tabeli.
         */
        public static void main(String[] args) { }
    }
}
