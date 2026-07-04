package com.example.javaquest._04_io.Lesson19_JsonIntro;

public class _Exercises_Lesson19_JsonIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteProductJson {
        /*
         * 🧪 Zadanie 1:
         * Napisz (jako text block """...""") i wypisz JSON obiektu "Product"
         * z polami: name ("Laptop"), price (3499.99), inStock (true),
         * category ("Elektronika").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteNumberArray {
        /*
         * 🧪 Zadanie 2:
         * Napisz i wypisz JSON tablicy 5 ulubionych liczb, np. [7, 13, 21, 42, 99].
         * Podpisz w komentarzu, że to JSON typu "array", nie "object".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyStructure {
        /*
         * 🧪 Zadanie 3:
         * Dany JSON (text block):
         * { "title": "Wiedźmin", "year": 1990, "tags": ["fantasy","polska"] }
         * Wypisz (przez System.out.println): ile kluczy najwyższego poziomu,
         * które z nich to obiekt/tablica/prosta wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NullVsMissingKey {
        /*
         * 🧪 Zadanie 4:
         * Napisz dwa warianty JSON dla osoby: jeden z kluczem "phone": null,
         * drugi całkowicie BEZ klucza "phone". Wypisz oba i skomentuj
         * różnicę znaczeniową (jawny brak wartości vs brak informacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ValidVsInvalidTrailingComma {
        /*
         * 🧪 Zadanie 5:
         * Napisz poprawny JSON {"a":1,"b":2} oraz niepoprawny {"a":1,"b":2,}
         * (przecinek po ostatnim elemencie). Wypisz oba i skomentuj, dlaczego
         * drugi jest niepoprawny w standardowym JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NestedTwoLevels {
        /*
         * 🧪 Zadanie 6:
         * Napisz JSON dla "person" z zagnieżdżonym "address", które z kolei
         * zawiera "city". Struktura: { "name": "Ala", "address": { "city": "Kraków" } }.
         * Wypisz i zaznacz w komentarzu głębokość zagnieżdżenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_JsonVsXmlCar {
        /*
         * 🧪 Zadanie 7:
         * Napisz ten sam zestaw danych "car" (brand: "Toyota", model: "Corolla",
         * year: 2022) jako JSON i jako XML (dwa text blocki). Wypisz oba obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FourDifferences {
        /*
         * 🧪 Zadanie 8:
         * Na podstawie lekcji wypisz (jako komentarze + println) co najmniej
         * 4 różnice między JSON a XML (zwięzłość, typy natywne, schemat,
         * komentarze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AllValueTypes {
        /*
         * 🧪 Zadanie 9:
         * Napisz JSON tablicę zawierającą po jednym przykładzie każdego typu
         * wartości JSON: string, number, boolean, null, object, array –
         * np. ["tekst", 42, true, null, {"k":"v"}, [1,2,3]]. Wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EscapingSpecialCharacters {
        /*
         * 🧪 Zadanie 10:
         * Napisz JSON z wartościami zawierającymi polskie znaki diakrytyczne
         * ("Łódź", "Wrocław"), cudzysłów w tekście (escapowany \") oraz znak
         * nowej linii (\n). Wypisz surowy JSON i skomentuj sposób escapowania.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OwnIsBalancedMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz WŁASNĄ metodę boolean isJsonBalanced(String json) (nie kopiuj
         * kodu z lekcji), sprawdzającą zbalansowanie { } i [ ] z pominięciem
         * nawiasów wewnątrz stringów. Przetestuj na 4 przykładach (2 poprawne,
         * 2 niepoprawne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExtractTopLevelKeysSimple {
        /*
         * 🧪 Zadanie 12:
         * Napisz własną metodę extractTopLevelKeysSimple(String json) dla
         * PROSTYCH, płaskich obiektów (bez zagnieżdżeń), np.
         * {"name":"Ala","age":"30"}. Przetestuj na 2-3 przykładach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CountArrayElements {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę countArrayElements(String json) liczącą elementy
         * prostej płaskiej tablicy liczb, np. "[1,2,3,4,5]" -> 5.
         * Przetestuj na 3 różnych tablicach (w tym pustej "[]").
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MaxNestingDepth {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę getMaxDepth(String json) zwracającą maksymalną
         * głębokość zagnieżdżenia { } / [ ]. Przetestuj na 3 przykładach
         * o różnej głębokości (1, 2, 3 poziomy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DetectValueType {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę detectValueType(String rawValue) zwracającą etykietę
         * typu JSON ("string"/"number"/"boolean"/"null") dla surowych wartości:
         * "\"Anna\"", "30", "true", "null". Przetestuj wszystkie cztery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RoughValidator {
        /*
         * 🧪 Zadanie 16:
         * Mając 5 przykładów JSON (część poprawnych, część z typowymi błędami:
         * brak przecinka, klucz bez cudzysłowów, pojedyncze cudzysłowy),
         * napisz prosty walidator heurystyczny (bez pełnego parsera)
         * oznaczający oczywiście niepoprawne przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_HandWriteFromDescription {
        /*
         * 🧪 Zadanie 17:
         * Mając opisane dane: osoba o imieniu "Kasia", wieku 27, liście hobby
         * ["taniec","gotowanie"], ręcznie napisz dokładnie odpowiadający im
         * text block JSON i wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_JsonVsXmlSizeComparison {
        /*
         * 🧪 Zadanie 18:
         * Dla tych samych danych książki (title, author, year) napisz wersję
         * JSON i XML jako text blocki, porównaj długość (String.length())
         * obu i wypisz różnicę oraz procent "oszczędności" JSON względem XML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_JsonArrayToJavaList {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę jsonArrayToJavaList(String json) parsującą WYŁĄCZNIE
         * płaskie tablice stringów, np. ["a","b","c"] -> List<String>
         * zawierającą "a","b","c" (bez żadnej biblioteki, ręczne parsowanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FlatKeyValueExtractor {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę parseFlatObject(String json) parsującą płaski obiekt
         * z wartościami string, np. {"a":"1","b":"2"}, do Map<String,String>
         * (ręcznie, bez biblioteki). Wypisz odtworzoną mapę.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_StrictStructureValidator {
        /*
         * 🧪 Zadanie 21:
         * Napisz isValidJsonStructure(String json), która sprawdza jednocześnie:
         * zbalansowanie nawiasów, że każdy cudzysłów otwierający ma domykający,
         * oraz brak przecinka tuż przed zamykającym } lub ]. Przetestuj na
         * 6 "podchwytliwych" przykładach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FlattenNestedKeys {
        /*
         * 🧪 Zadanie 22:
         * Dla JSON dwupoziomowego, np.
         * {"name":"Ala","address":{"city":"Kraków","zip":"30-000"}},
         * napisz metodę zwracającą listę kluczy w notacji kropkowej,
         * np. ["name", "address.city", "address.zip"].
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HandWrittenPrettyPrinter {
        /*
         * 🧪 Zadanie 23:
         * Napisz od zera metodę prettyPrintJson(String compactJson), która
         * formatuje jednoliniowy JSON z wcięciami (2 spacje) i nową linią po
         * każdym { [ , – bez użycia żadnej biblioteki (przypomina to, co
         * w kolejnej lekcji zrobi za nas GsonBuilder.setPrettyPrinting()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HandWrittenMinifier {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę minifyJson(String prettyJson) usuwającą zbędne białe
         * znaki (spacje, nowe linie, wcięcia) POZA stringami, na podstawie
         * wieloliniowego text blocku z lekcji jako danych wejściowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareTopLevelKeySets {
        /*
         * 🧪 Zadanie 25:
         * Napisz compareJsonStructureOnly(String json1, String json2) zwracającą
         * true, gdy dwa płaskie obiekty JSON mają dokładnie ten sam zbiór
         * kluczy najwyższego poziomu (niezależnie od kolejności i wartości).
         * Przetestuj na 3 parach przykładów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExtractAllGenres {
        /*
         * 🧪 Zadanie 26:
         * Zamodeluj mały "katalog filmów" jako zagnieżdżony JSON: tablica
         * 3-4 filmów, każdy z "title", "year" i tablicą "genres".
         * Napisz extractAllGenres(String json) zbierającą wszystkie unikalne
         * gatunki występujące w całym katalogu (ręczne skanowanie tekstu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DetectDuplicateKeys {
        /*
         * 🧪 Zadanie 27:
         * Napisz detectDuplicateKeys(String json) dla płaskiego obiektu,
         * wykrywającą powtórzone klucze najwyższego poziomu, np.
         * {"a":1,"b":2,"a":3} -> wykryj, że "a" występuje dwukrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ValidatorReportCapstone {
        /*
         * 🧪 Zadanie 28:
         * Mając 5 przykładowych JSON-ów (mieszanka: niezbalansowany,
         * z duplikatem klucza, z przecinkiem końcowym, z niecudzysłowionym
         * kluczem, poprawny), uruchom na każdym wszystkie walidatory
         * z poprzednich zadań i wypisz raport: VALID/INVALID + powód.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_XmlToJsonTranslation {
        /*
         * 🧪 Zadanie 29:
         * Dany XML z listą 3 elementów "employee" (name, age jako dzieci),
         * ręcznie "przetłumacz" go na odpowiadającą tablicę JSON (text block),
         * zachowując wszystkie dane, a następnie zweryfikuj wynik swoim
         * isJsonBalanced z zadania 11.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_JsonDiffTool {
        /*
         * 🧪 Zadanie 30:
         * Napisz compareJsonValues(String json1, String json2) dla dwóch
         * PŁASKICH obiektów o tej samej strukturze kluczy: sparsuj oba
         * (metodą z zadania 20) do Map<String,String>, a następnie wypisz
         * klucze o różnych wartościach, klucze tylko w pierwszym i tylko
         * w drugim obiekcie.
         */
        public static void main(String[] args) { }
    }
}
