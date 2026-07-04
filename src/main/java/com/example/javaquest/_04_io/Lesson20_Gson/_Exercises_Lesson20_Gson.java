package com.example.javaquest._04_io.Lesson20_Gson;

public class _Exercises_Lesson20_Gson {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ToJsonSingleObject {
        /*
         * 🧪 Zadanie 1:
         * Rekord Book(title, author, year). Stwórz new Gson(), wywołaj
         * toJson(...) na obiekcie Book("Lód", "Jacek Dukaj", 2007) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FromJsonSingleObject {
        /*
         * 🧪 Zadanie 2:
         * Mając String json = "{\"title\":\"Solaris\",\"author\":\"Stanisław Lem\",\"year\":1961}",
         * użyj gson.fromJson(json, Book.class) i wypisz odtworzony obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RoundTripEquals {
        /*
         * 🧪 Zadanie 3:
         * Stwórz Book, zamień na JSON (toJson), potem z powrotem na obiekt
         * (fromJson) i sprawdź metodą equals(), czy oryginał i odtworzony
         * obiekt są sobie równe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrettyVsCompact {
        /*
         * 🧪 Zadanie 4:
         * Dla tego samego obiektu Book wypisz wynik zwykłego gson.toJson(...)
         * oraz wynik new GsonBuilder().setPrettyPrinting().create().toJson(...).
         * Porównaj wizualnie oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SerializeNullsComparison {
        /*
         * 🧪 Zadanie 5:
         * Klasa Contact(name, String email) z email=null.
         * Porównaj wynik toJson zwykłym Gson (pole email pominięte) z wynikiem
         * new GsonBuilder().serializeNulls().create() (pole email widoczne jako null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SerializeListOfStrings {
        /*
         * 🧪 Zadanie 6:
         * Serializuj listę List<String> ulubionych filmów, np.
         * ["Matrix","Incepcja","Interstellar"], przy pomocy Gson i wypisz
         * powstałą tablicę JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DeserializeNumberArray {
        /*
         * 🧪 Zadanie 7:
         * Mając JSON "[10,20,30,40]", zdeserializuj go do int[] (przez fromJson)
         * i wypisz wszystkie elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FromJsonToMap {
        /*
         * 🧪 Zadanie 8:
         * Mając JSON {"a":"1","b":"2","c":"3"}, użyj
         * gson.fromJson(json, Map.class) i wypisz odtworzoną mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_RecordRoundTripAllFields {
        /*
         * 🧪 Zadanie 9:
         * Rekord Product(id, name, price, active) z 4 polami. Zserializuj
         * i zdeserializuj obiekt, sprawdź że WSZYSTKIE 4 pola są identyczne
         * po pełnym cyklu (round trip).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareOutputLengths {
        /*
         * 🧪 Zadanie 10:
         * Dla tego samego obiektu Book porównaj długość (length()) wyniku
         * zwykłego toJson vs wyniku z setPrettyPrinting() – wypisz obie
         * długości i różnicę (dodatkowe białe znaki).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SerializeListOfBooks {
        /*
         * 🧪 Zadanie 11:
         * Lista List<Book> z 3 książkami. Zserializuj ją przy pomocy
         * GsonBuilder().setPrettyPrinting() i wypisz powstałą tablicę JSON obiektów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DeserializeListWithTypeToken {
        /*
         * 🧪 Zadanie 12:
         * Mając JSON z zadania 11, zdeserializuj go z powrotem do List<Book>
         * używając new TypeToken<List<Book>>(){}.getType(). Wypisz klasę
         * pierwszego elementu (getClass().getSimpleName()) – powinno być "Book".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MapOfBooksByIsbn {
        /*
         * 🧪 Zadanie 13:
         * Map<String, Book> z kluczem ISBN (np. "978-1" -> Book,
         * "978-2" -> Book). Zserializuj przez toJson, a następnie
         * zdeserializuj używając TypeToken<Map<String,Book>>(){}.getType().
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WithoutTypeTokenProblem {
        /*
         * 🧪 Zadanie 14:
         * Mając JSON tablicy Book, spróbuj gson.fromJson(json, List.class)
         * BEZ TypeToken. Wypisz getClass().getSimpleName() elementu listy –
         * pokaż, że to NIE jest Book, tylko LinkedTreeMap (problem type erasure).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SaveAndLoadTempFile {
        /*
         * 🧪 Zadanie 15:
         * Zapisz List<Book> do pliku tymczasowego (Files.createTempFile +
         * FileWriter + prettyGson.toJson(list, writer)), odczytaj go z powrotem
         * (FileReader + TypeToken), zweryfikuj zgodność, na koniec usuń plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NestedObjectSerialization {
        /*
         * 🧪 Zadanie 16:
         * Rekord Author(name, country) oraz Book(title, Author author).
         * Zserializuj Book jednym wywołaniem toJson i wypisz zagnieżdżoną
         * strukturę JSON wygenerowaną automatycznie przez refleksję Gson.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SerializeNullsInNestedField {
        /*
         * 🧪 Zadanie 17:
         * Author(name, country) z country=null. Porównaj toJson zwykłym
         * Gson (pole country pominięte) z GsonBuilder().serializeNulls()
         * (pole country widoczne jako null) – tak jak w zadaniu 5, ale
         * dla pola zagnieżdżonego wewnątrz Book.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MissingFieldsDefaultFilled {
        /*
         * 🧪 Zadanie 18:
         * Mając tablicę JSON z 3 obiektami Book, gdzie jeden NIE ma pola "year",
         * zdeserializuj do List<Book> (TypeToken) i sprawdź, jaką wartość
         * dostaje brakujące pole "year" (domyślną dla typu, np. 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RepositoryPattern {
        /*
         * 🧪 Zadanie 19:
         * Napisz metody saveBooksToFile(List<Book>, Path) oraz
         * loadBooksFromFile(Path) korzystające z Gson + TypeToken + pliku
         * tymczasowego. Przetestuj zapis, a potem odczyt tej samej listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PerformanceComparison {
        /*
         * 🧪 Zadanie 20:
         * Wygeneruj List<Book> z 10 000 elementów. Zmierz (System.nanoTime())
         * czas serializacji+deserializacji zwykłym Gson oraz Gson z
         * setPrettyPrinting(). Wypisz oba czasy i porównaj.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_NestedLibraryStructure {
        /*
         * 🧪 Zadanie 21:
         * Klasa Library(name, List<Book> books). Zserializuj całą strukturę
         * (z listą 3 książek) do pliku tymczasowego i odczytaj ją z powrotem,
         * zweryfikuj że zagnieżdżona lista books zachowała wszystkie elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GenericFileSaveLoadUtility {
        /*
         * 🧪 Zadanie 22:
         * Napisz generyczne metody <T> void saveToJsonFile(T object, Path path, Type type)
         * oraz <T> T loadFromJsonFile(Path path, Type type), działające dla
         * DOWOLNEGO typu (pojedynczy obiekt, List, Map). Przetestuj z 3 różnymi
         * typami: Book, List<Book>, Map<String,Book>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ServerClientSimulation {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj REST: "serwer" zapisuje List<Book> (odpowiedź HTTP) do
         * pliku tymczasowego. "Klient" odczytuje plik, deserializuje przez
         * TypeToken, a następnie strumieniami (Stream) filtruje książki
         * wydane po roku 2000 i wypisuje wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_JsonFileBackedDatabase {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prostą "bazę danych" Map<String, Book> trzymaną
         * w pamięci i zapisywaną do pliku tymczasowego po każdej operacji
         * (metody insert(id, Book), findById(id), deleteById(id) – każda
         * z nich nadpisuje cały plik nową wersją mapy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_GsonAndTransientInteraction {
        /*
         * 🧪 Zadanie 25:
         * Klasa User(username, transient String password, jawny serialVersionUID).
         * Zserializuj obiekt DWOMA sposobami: ObjectOutputStream (binarnie,
         * password pominięte) i Gson.toJson (JSON). Sprawdź empirycznie
         * (czy password występuje w wyniku toJson), czy Gson również pomija
         * pola transient, i wypisz wniosek w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ListOfHeterogeneousMaps {
        /*
         * 🧪 Zadanie 26:
         * List<Map<String, Object>> reprezentująca "dokumenty" o różnych
         * kluczach/typach wartości. Zserializuj i zdeserializuj przy pomocy
         * TypeToken<List<Map<String,Object>>>(){}.getType(), zweryfikuj że
         * struktura przetrwała round trip.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MergeJsonFiles {
        /*
         * 🧪 Zadanie 27:
         * Napisz mergeJsonFiles(Path file1, Path file2, Path outputFile),
         * która czyta dwa pliki tymczasowe (każdy zawiera List<Book>),
         * łączy listy usuwając duplikaty po tytule, i zapisuje wynik
         * (prettyGson) do trzeciego pliku tymczasowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_VersionedConfigFallback {
        /*
         * 🧪 Zadanie 28:
         * Klasa ConfigV2(theme, language) wczytująca plik JSON zapisany
         * wcześniej przez "starą" wersję configu bez pola "theme"
         * ({"language":"pl"}). Wykryj że theme==null po deserializacji
         * i zastosuj wartość domyślną ("light"), logując co się stało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_StressTestRoundTrip {
        /*
         * 🧪 Zadanie 29:
         * Wygeneruj 1000 losowych Book, zserializuj prettyGson do pliku
         * tymczasowego, sprawdź rozmiar pliku (Files.size), zdeserializuj
         * z powrotem (TypeToken), zweryfikuj liczność listy i kilka losowych
         * elementów, zmierz całkowity czas operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BookCatalogCliSimulation {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj w main() symulację CLI dla katalogu książek: Map<String,Book>
         * w pamięci, stała sekwencja "poleceń" (add, remove, list, save do pliku
         * tymczasowego, load z pliku) wykonana na sztywno zakodowanych danych
         * (bez Scannera), z wypisywaniem stanu katalogu po każdej operacji.
         */
        public static void main(String[] args) { }
    }
}
