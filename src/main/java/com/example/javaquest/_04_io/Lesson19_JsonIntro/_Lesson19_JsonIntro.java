package com.example.javaquest._04_io.Lesson19_JsonIntro;

import java.util.ArrayList;
import java.util.List;

public class _Lesson19_JsonIntro {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 CZYM JEST JSON?
         * ============================================================
         * JSON (JavaScript Object Notation) to lekki, tekstowy format
         * zapisu danych, powszechnie używany do:
         * - komunikacji między serwerem a klientem (REST API)
         * - plików konfiguracyjnych
         * - zapisu/wczytywania danych aplikacji
         *
         * JSON jest niezależny od języka programowania – mimo nazwy
         * (wywodzi się ze składni obiektów JavaScript) używa się go
         * praktycznie wszędzie: Java, Python, JS, Go, C#...
         *
         * JSON opiera się na DWÓCH strukturach:
         * 🔹 OBIEKT  { }  → zbiór par "klucz": wartość (jak Map<String, ?>)
         * 🔹 TABLICA [ ]  → uporządkowana lista wartości (jak List<?>)
         */

        /*
         * ============================================================
         * 🔹 TYPY WARTOŚCI W JSON
         * ============================================================
         * Wartością w JSON (czy to w obiekcie, czy w tablicy) może być:
         *
         *   string  → tekst w cudzysłowach:      "Anna"
         *   number  → liczba (int lub double):    30, 3.14, -5
         *   boolean → true / false
         *   null    → brak wartości:              null
         *   object  → zagnieżdżony obiekt:         { "miasto": "Warszawa" }
         *   array   → zagnieżdżona tablica:        [1, 2, 3]
         *
         * WAŻNE różnice względem Javy:
         * ❌ klucze MUSZĄ być w cudzysłowach (nie ma "gołych" identyfikatorów)
         * ❌ nie ma rozróżnienia int/double/long – jest tylko "number"
         * ❌ nie ma komentarzy w standardowym JSON
         * ❌ nie ma przecinka po ostatnim elemencie (trailing comma – błąd!)
         */

        /*
         * ============================================================
         * 🔍 PRZYKŁAD SUROWEGO JSON-A (jako String w kodzie Java)
         * ============================================================
         * W Javie 15+ wygodnie zapisać wieloliniowy JSON jako text block ("""...""").
         */

        String personJson = """
                {
                  "name": "Anna Nowak",
                  "age": 30,
                  "isStudent": false,
                  "email": null,
                  "address": {
                    "city": "Warszawa",
                    "zipCode": "00-001"
                  },
                  "hobbies": ["czytanie", "szachy", "programowanie"]
                }
                """;

        System.out.println("=== SUROWY JSON ===");
        System.out.println(personJson);

        /*
         * Analiza struktury powyższego JSON-a:
         * - cały dokument to OBIEKT { }
         * - "name"      → string
         * - "age"       → number (int)
         * - "isStudent" → boolean
         * - "email"     → null
         * - "address"   → zagnieżdżony OBIEKT { "city": ..., "zipCode": ... }
         * - "hobbies"   → TABLICA stringów [ "czytanie", "szachy", "programowanie" ]
         */

        /*
         * ============================================================
         * 📋 JSON vs XML – PORÓWNANIE
         * ============================================================
         * Te same dane co wyżej, zapisane w XML:
         */

        String personXml = """
                <person>
                  <name>Anna Nowak</name>
                  <age>30</age>
                  <isStudent>false</isStudent>
                  <email></email>
                  <address>
                    <city>Warszawa</city>
                    <zipCode>00-001</zipCode>
                  </address>
                  <hobbies>
                    <hobby>czytanie</hobby>
                    <hobby>szachy</hobby>
                    <hobby>programowanie</hobby>
                  </hobbies>
                </person>
                """;

        System.out.println("=== TEN SAM DOKUMENT W XML ===");
        System.out.println(personXml);

        /*
         * Porównanie:
         *
         * ✅ JSON jest BARDZIEJ ZWIĘZŁY – brak zamykających tagów </...>,
         *    mniej znaków = mniejszy rozmiar danych w sieci
         * ✅ JSON jest CZYTELNIEJSZY dla typowych struktur danych
         *    (mapy, listy, wartości proste)
         * ✅ JSON MA natywne typy (number, boolean, null) –
         *    w XML wszystko to zawsze tekst, trzeba go samemu konwertować
         * ✅ JSON NIE WYMAGA schematu (jak XSD/DTD w XML), żeby był poprawny –
         *    wystarczy poprawna składnia
         * ❌ XML ma bogatsze możliwości: atrybuty, przestrzenie nazw,
         *    komentarze, mieszaną zawartość (tekst + elementy) – lepszy
         *    dla dokumentów (np. formatowany tekst), gorszy dla "czystych danych"
         * ❌ XML ma wsparcie dla walidacji przez schemat (XSD) "z pudełka",
         *    JSON Schema istnieje, ale jest osobnym, mniej ustandaryzowanym bytem
         *
         * W praktyce: JSON zdominował komunikację API (REST), XML wciąż
         * używany jest w dokumentach, starszych systemach (SOAP), konfiguracji
         * (np. pom.xml w Mavenie!).
         */

        /*
         * ============================================================
         * 🔧 RĘCZNA "INSPEKCJA" STRUKTURY JSON (bez zewnętrznej biblioteki)
         * ============================================================
         * Zanim poznamy prawdziwe parsery (Gson w następnej lekcji),
         * zobaczmy "na piechotę", jak można rozpoznać strukturę tekstu JSON:
         * liczenie głębokości zagnieżdżenia obiektów/tablic oraz sprawdzenie,
         * czy nawiasy są poprawnie zbalansowane.
         */

        String compact = "{\"name\":\"Anna\",\"age\":30,\"hobbies\":[\"czytanie\",\"szachy\"]}";

        System.out.println("=== ANALIZA STRUKTURY (ręczny mini-\"parser\") ===");
        System.out.println("JSON: " + compact);
        System.out.println("Zbalansowany? " + isBalanced(compact));
        System.out.println("Klucze na najwyższym poziomie: " + extractTopLevelKeys(compact));

        String brokenJson = "{\"name\":\"Anna\", \"age\":30"; // brakuje zamykającego }
        System.out.println("\nJSON: " + brokenJson);
        System.out.println("Zbalansowany? " + isBalanced(brokenJson)); // false

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JSON = obiekty { } (klucz-wartość) + tablice [ ] (lista wartości)
         * - Typy wartości: string, number, boolean, null, object, array
         * - JSON jest zwięzły, czytelny i nie wymaga schematu – stąd jego
         *   popularność w REST API i plikach konfiguracyjnych
         * - XML jest bogatszy (atrybuty, schematy, komentarze), ale bardziej
         *   "gadatliwy" (verbose)
         * - Ręczne parsowanie JSON-a "na piechotę" jest możliwe, ale niewygodne
         *   i podatne na błędy – w kolejnej lekcji poznamy bibliotekę Gson,
         *   która robi to za nas w jednej linijce kodu
         */
    }

    /*
     * Prosty walidator zbalansowania nawiasów { } oraz [ ] w tekście JSON.
     * Ignoruje nawiasy, które znajdują się WEWNĄTRZ napisów (stringów),
     * żeby np. wartość "text with } inside" nie zaburzała liczenia.
     */
    private static boolean isBalanced(String json) {
        int depth = 0;
        boolean insideString = false;
        boolean escaped = false;

        for (char c : json.toCharArray()) {
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                insideString = !insideString;
                continue;
            }
            if (insideString) {
                continue;
            }
            if (c == '{' || c == '[') {
                depth++;
            } else if (c == '}' || c == ']') {
                depth--;
                if (depth < 0) {
                    return false; // zamknięcie bez otwarcia
                }
            }
        }
        return depth == 0;
    }

    /*
     * Wyciąga nazwy kluczy znajdujących się na NAJWYŻSZYM poziomie obiektu JSON
     * (głębokość 1), bez wchodzenia w zagnieżdżone obiekty/tablice.
     * To uproszczony przykład – prawdziwy parser (patrz Gson w Lesson20)
     * obsługuje dużo więcej przypadków brzegowych.
     */
    private static List<String> extractTopLevelKeys(String json) {
        List<String> keys = new ArrayList<>();
        int depth = 0;
        boolean insideString = false;
        boolean escaped = false;
        StringBuilder currentToken = new StringBuilder();
        boolean expectingKey = true;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (escaped) {
                escaped = false;
                currentToken.append(c);
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                insideString = !insideString;
                if (!insideString && depth == 1 && expectingKey) {
                    keys.add(currentToken.toString());
                    currentToken.setLength(0);
                    expectingKey = false;
                }
                continue;
            }
            if (insideString) {
                if (depth == 1 && expectingKey) {
                    currentToken.append(c);
                }
                continue;
            }
            if (c == '{' || c == '[') {
                depth++;
            } else if (c == '}' || c == ']') {
                depth--;
            } else if (c == ',' && depth == 1) {
                expectingKey = true;
            }
        }
        return keys;
    }
}
