package com.example.javaquest._18_rest_api.Lesson08_JsonApiDesign;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class _Lesson08_JsonApiDesign {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: PROJEKTOWANIE KSZTALTU JSON ===");

        /*
         * ============================================================
         * 📦 PO CO OSOBNA LEKCJA O JSON, SKORO BYLA JUZ LEKCJA 6?
         * ============================================================
         * Lesson06 uczyla, GDZIE zyje cialo i jakiej jest STRUKTURY
         * wysokiego poziomu (goła tablica vs koperta). Ta lekcja schodzi
         * NIZEJ - jak zaprojektowac SAM KSZTALT danych JSON wewnatrz tej
         * struktury, zeby byl SPOJNY, przewidywalny i latwy w uzyciu dla
         * kazdego klienta API.
         */
        System.out.println("Lesson06 = STRUKTURA odpowiedzi (koperta/tablica). Lesson08 = KSZTALT danych WEWNATRZ tej struktury.");

        demonstrateNamingConsistency();
        demonstrateDateTimeFormat();
        demonstrateTypesNotStringified();
        demonstrateArrayVsMapKeyedById();
        demonstrateNullVsOmittedField();
        demonstrateFlatVsOverNested();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Konwencja nazewnictwa pol (camelCase LUB snake_case) - WYBIERZ
         *   1 i trzymaj sie jej w CALYM API, NIGDY nie mieszaj.
         * - Daty/czas: ZAWSZE ISO-8601 (np. "2026-07-10T14:30:00Z") -
         *   NIGDY formatow lokalnych ("10.07.2026") ani timestampow bez
         *   jednoznacznej jednostki.
         * - Typy MUSZA byc PRAWDZIWE (liczba jako number, nie "42" jako
         *   string) - klient nie powinien musiec parsowac stringow, zeby
         *   dostac liczbe.
         * - Kolekcje jako TABLICE obiektow z polem "id", NIE jako obiekt
         *   kluczowany po ID - tablice sa uniwersalne (latwo iterowac,
         *   sortowac, paginowac - Lesson09/10).
         * - "null" (pole OBECNE, wartosc null) != "pole POMINIETE" - wybierz
         *   SWIADOMIE i bad konsekwentny (Lesson04 PATCH: to ma znaczenie!).
         * - Unikaj NADMIERNEGO zagniezdzenia - jesli klient musi isc
         *   4 poziomy w dol po dane, rozwaz splaszczenie.
         * - Kolejna lekcja (Lesson09): jak dodac parametry sciezki i query
         *   do tych dobrze zaprojektowanych zasobow.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateNamingConsistency() {
        System.out.println("\n=== SPOJNA KONWENCJA NAZEWNICTWA POL ===");

        String inconsistent = "{\"user_id\":1,\"firstName\":\"Ala\",\"LastName\":\"Kowalska\",\"email_Address\":\"ala@example.com\"}";
        String consistentCamel = "{\"userId\":1,\"firstName\":\"Ala\",\"lastName\":\"Kowalska\",\"emailAddress\":\"ala@example.com\"}";
        String consistentSnake = "{\"user_id\":1,\"first_name\":\"Ala\",\"last_name\":\"Kowalska\",\"email_address\":\"ala@example.com\"}";

        System.out.println("ZLE (mieszanka konwencji): " + inconsistent);
        System.out.println("DOBRZE (spojny camelCase): " + consistentCamel);
        System.out.println("DOBRZE (spojny snake_case): " + consistentSnake);
        System.out.println("-> obie konwencje sa akceptowalne - problemem jest MIESZANIE ich w 1 API.");
    }

    private static void demonstrateDateTimeFormat() {
        System.out.println("\n=== DATY I CZAS: ZAWSZE ISO-8601 ===");

        Instant now = Instant.parse("2026-07-10T14:30:00Z");
        String iso8601 = DateTimeFormatter.ISO_INSTANT.format(now);

        System.out.println("ZLE:     \"createdAt\": \"10.07.2026 14:30\"       (format zalezny od lokalizacji - niejednoznaczny)");
        System.out.println("ZLE:     \"createdAt\": 1783968600                 (timestamp - sekundy czy milisekundy? jaka strefa?)");
        System.out.println("DOBRZE:  \"createdAt\": \"" + iso8601 + "\"   (ISO-8601, jednoznaczna strefa UTC 'Z')");
        System.out.println("-> ISO-8601 jest jednoznaczny, sortowalny leksykograficznie, i wspierany przez KAZDY jezyk programowania.");
    }

    private static void demonstrateTypesNotStringified() {
        System.out.println("\n=== TYPY MUSZA BYC PRAWDZIWE, NIE 'STRINGIFIED' ===");

        String badTypes = "{\"price\":\"99.99\",\"inStock\":\"true\",\"quantity\":\"5\"}";
        String goodTypes = "{\"price\":99.99,\"inStock\":true,\"quantity\":5}";

        System.out.println("ZLE (liczby/booleany jako stringi): " + badTypes);
        System.out.println("DOBRZE (prawdziwe typy JSON):        " + goodTypes);
        System.out.println("-> klient musialby recznie parsowac \"99.99\" na liczbe - zrodlo bledow i niepotrzebnej pracy.");
    }

    private static void demonstrateArrayVsMapKeyedById() {
        System.out.println("\n=== KOLEKCJA JAKO TABLICA, NIE JAKO OBIEKT KLUCZOWANY PO ID ===");

        String badShape = "{\"1\":{\"name\":\"Klawiatura\"},\"2\":{\"name\":\"Monitor\"}}";
        String goodShape = "[{\"id\":1,\"name\":\"Klawiatura\"},{\"id\":2,\"name\":\"Monitor\"}]";

        System.out.println("ZLE (obiekt kluczowany po ID): " + badShape);
        System.out.println("DOBRZE (tablica obiektow z polem id): " + goodShape);
        System.out.println("-> tablice MOZNA iterowac/sortowac/paginowac natywnie w kazdym jezyku; klucze obiektu tego nie gwarantuja (kolejnosc, typ klucza).");
    }

    private static void demonstrateNullVsOmittedField() {
        System.out.println("\n=== 'null' (POLE OBECNE) vs POLE CALKOWICIE POMINIETE ===");

        Gson gson = new GsonBuilder().serializeNulls().create();
        Map<String, Object> withExplicitNull = new LinkedHashMap<>();
        withExplicitNull.put("name", "Ala");
        withExplicitNull.put("middleName", null); // JAWNIE: "wiemy, ze nie ma drugiego imienia"

        Map<String, Object> withOmittedField = new LinkedHashMap<>();
        withOmittedField.put("name", "Jan"); // "middleName" w ogole nie wystepuje

        System.out.println("Z jawnym null:  " + gson.toJson(withExplicitNull) + "  <- pole ISTNIEJE, wartosc nieznana/brak");
        System.out.println("Z pominietym:   " + new Gson().toJson(withOmittedField) + "                       <- pole CALKOWICIE nieobecne");
        System.out.println("-> ta roznica MA znaczenie przy PATCH (Lesson04) - null moze oznaczac 'wyczysc pole', brak = 'nie dotykaj pola'.");
    }

    private static void demonstrateFlatVsOverNested() {
        System.out.println("\n=== UNIKAJ NADMIERNEGO ZAGNIEZDZENIA JSON ===");

        String overNested = """
                {
                  "order": {
                    "details": {
                      "customer": {
                        "info": {
                          "name": "Ala Kowalska"
                        }
                      }
                    }
                  }
                }""";
        String flattened = """
                {
                  "orderId": 42,
                  "customerName": "Ala Kowalska"
                }""";

        System.out.println("ZLE (4 poziomy zagniezdzenia, zeby dostac 1 nazwe):");
        System.out.println(overNested.indent(2));
        System.out.println("DOBRZE (splaszczone do tego, co faktycznie potrzebne):");
        System.out.println(flattened.indent(2));
        System.out.println("-> zagniezdzenie MA sens, gdy odzwierciedla REALNA strukture danych (np. adres wewnatrz uzytkownika) - nie dodawaj go bez powodu.");
    }
}
