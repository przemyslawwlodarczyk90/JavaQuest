package com.example.javaquest._04_io.Lesson22_SerializableVsJson;

public class _Exercises_Lesson22_SerializableVsJson {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSerializable {
        /*
         * 🧪 Zadanie 1:
         * Klasa Book(title, pages) implementująca Serializable. Zapisz obiekt
         * Book("Wiedzmin", 400) do pliku tymczasowego przez ObjectOutputStream.writeObject(),
         * a potem odczytaj go z powrotem przez ObjectInputStream.readObject().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TransientField {
        /*
         * 🧪 Zadanie 2:
         * Do klasy Book dodaj pole transient String secretNote. Ustaw je na "tajne",
         * zserializuj i zdeserializuj obiekt binarnie – sprawdź, że po odczycie pole
         * secretNote jest null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SameObjectAsJson {
        /*
         * 🧪 Zadanie 3:
         * Ten sam obiekt Book zserializuj do JSON-a przy pomocy ObjectMapper.writeValueAsString().
         * Wypisz wygenerowany JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareFileSizes {
        /*
         * 🧪 Zadanie 4:
         * Zapisz ten sam obiekt Book raz do pliku .ser (binarnie) i raz do pliku .json (tekstowo).
         * Porównaj rozmiary obu plików (Files.size) i wypisz który jest większy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NotSerializableException {
        /*
         * 🧪 Zadanie 5:
         * Klasa Gadget NIE implementująca Serializable. Spróbuj ją zserializować
         * przez ObjectOutputStream.writeObject() i przechwyć NotSerializableException –
         * wypisz komunikat wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReadabilityComparison {
        /*
         * 🧪 Zadanie 6:
         * Wypisz pierwsze 32 bajty pliku .ser jako HEX (jak w lekcji) obok pełnej
         * zawartości pliku .json jako tekst. Porównaj czytelność dla człowieka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SerialVersionUID {
        /*
         * 🧪 Zadanie 7:
         * Dodaj do klasy Book pole private static final long serialVersionUID = 1L.
         * Zapisz i odczytaj obiekt, wypisz komentarzem/w konsoli po co służy to pole
         * (kontrola zgodności wersji klasy przy odczycie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TransientNotIgnoredInJson {
        /*
         * 🧪 Zadanie 8:
         * Klasa Account(login, transient password). Zserializuj obiekt do JSON-a Jacksonem
         * i sprawdź, że pole "password" MIMO transient nadal pojawia się w JSON-ie
         * (transient działa tylko dla Serializable, nie dla Jacksona).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MissingFieldScenario {
        /*
         * 🧪 Zadanie 9:
         * Napisz komentarzem/kodem demonstrację: zdeserializuj JSON {"name":"Ola"} do klasy
         * Person(name, age) – sprawdź jaka wartość ma age (domyślna int 0). Skomentuj czym
         * różniłoby się to od próby odczytu starego pliku .ser przy zmienionej klasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RoundTripBoth {
        /*
         * 🧪 Zadanie 10:
         * Dla obiektu Book wykonaj pełny round-trip obiema metodami: binarnie
         * (zapis+odczyt) i JSON-em (zapis+odczyt). Porównaj pola odtworzonych obiektów
         * z oryginałem (assert/println equals pól).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CircularReference {
        /*
         * 🧪 Zadanie 11:
         * Dwie klasy Serializable wzajemnie się referencujące (np. Employee <-> Department,
         * department.employees zawiera employee, employee.department wskazuje z powrotem).
         * Zserializuj binarnie taki graf (Serializable radzi sobie z cyklami) i odtwórz go.
         * Następnie spróbuj (i skomentuj wynik) zserializować to samo Jacksonem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ListSizeComparison {
        /*
         * 🧪 Zadanie 12:
         * Lista List<Product> z 50 elementami. Zserializuj całą listę raz binarnie
         * (writeObject(list)) i raz do JSON-a. Porównaj rozmiary wynikowych plików.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddingFieldScenario {
        /*
         * 🧪 Zadanie 13:
         * Skomentuj i zademonstruj: zapisz obiekt starą wersją klasy (V1: name, age).
         * Wyobraź sobie nową wersję klasy (V2: name, age, email) – jak zmiana wpłynie na
         * odczyt starego pliku .ser (bez serialVersionUID) vs odczyt starego pliku .json
         * (Jackson po prostu ustawi email na null)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BenchmarkSpeed {
        /*
         * 🧪 Zadanie 14:
         * Wygeneruj listę 10 000 obiektów Book. Zmierz System.nanoTime() dla serializacji
         * binarnej całej listy oraz dla serializacji do JSON-a. Wypisz oba czasy w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ToJsonOrBinaryUtility {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę save(Object o, Path path, boolean asJson), która w zależności
         * od flagi zapisuje obiekt binarnie albo jako JSON pod wskazaną ścieżkę.
         * Przetestuj oboma trybami na obiekcie Book.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SettingsAsJson {
        /*
         * 🧪 Zadanie 16:
         * Klasa AppSettings(theme, fontSize, autoSave). Zapisz przykładowe ustawienia
         * jako plik JSON ("settings.json") w katalogu tymczasowym, a przy "starcie aplikacji"
         * wczytaj je z powrotem przez ObjectMapper.readValue(File, AppSettings.class).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExcludeSensitiveBothWays {
        /*
         * 🧪 Zadanie 17:
         * Klasa Credentials(username, password) z polem password oznaczonym jednocześnie
         * jako transient (dla Serializable) i @JsonIgnore (dla Jacksona). Zserializuj
         * obiekt oboma sposobami i potwierdź, że password znika w OBU formatach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SerializableToJsonAndBack {
        /*
         * 🧪 Zadanie 18:
         * Klasa Book implementująca zarówno Serializable jak i mająca gettery/konstruktor
         * bezargumentowy wymagany przez Jacksona. Zserializuj do JSON-a i odtwórz z powrotem –
         * potwierdź, że jedna klasa może obsługiwać oba mechanizmy jednocześnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TwoDeliveryChannels {
        /*
         * 🧪 Zadanie 19:
         * Zasymuluj dwa "kanały" dla tego samego obiektu Session: zapis okresowy do lokalnego
         * pliku cache binarnie (ObjectOutputStream) oraz wysyłkę "do zewnętrznego systemu"
         * jako JSON String (samo mapper.writeValueAsString, bez realnego HTTP). Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_EnumField {
        /*
         * 🧪 Zadanie 20:
         * Klasa Order(id, Status status) gdzie Status to enum {NEW, PAID, SHIPPED}.
         * Zserializuj obiekt binarnie i do JSON-a, porównaj jak każdy format reprezentuje
         * wartość enuma (JSON: zwykły String z nazwą stałej).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiObjectBinaryStore {
        /*
         * 🧪 Zadanie 21:
         * Zapisz kilka obiektów Session do JEDNEGO pliku binarnego, wywołując writeObject()
         * kilkukrotnie na tym samym ObjectOutputStream. Odczytaj je wszystkie w pętli
         * wywołując readObject() aż do złapania EOFException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CrossFormatAuditReport {
        /*
         * 🧪 Zadanie 22:
         * Dla danego obiektu wygeneruj oba formaty (binarny i JSON) i zbuduj raport
         * porównawczy: rozmiar w bajtach, czy zawiera pola oznaczone jako wrażliwe
         * (transient/@JsonIgnore), oraz subiektywną ocenę czytelności. Wypisz raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_VersionMigrationSimulation {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj metodę loadConfig(Path jsonPath, ConfigV2.class), która wczyta
         * plik zapisany starszą wersją klasy (mniej pól) i uzupełni brakujące pole
         * wartością domyślną. Skomentuj, dlaczego dla formatu binarnego taka migracja
         * byłaby dużo trudniejsza (wymaga custom readObject()/serialVersionUID).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CyclicGraphWithBackReference {
        /*
         * 🧪 Zadanie 24:
         * Klasy Order i OrderItem, gdzie OrderItem ma referencję zwrotną do swojego Order
         * (cykl). Zapisz cały graf binarnie (działa od razu). Następnie spróbuj Jacksonem
         * i dodaj @JsonIgnore na referencji zwrotnej, żeby uniknąć nieskończonej rekurencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExportFormatChoice {
        /*
         * 🧪 Zadanie 25:
         * Enum ExportFormat {BINARY, JSON}. Metoda export(Object o, Path targetDir, ExportFormat format),
         * która zapisuje obiekt do pliku z odpowiednim rozszerzeniem (.ser albo .json)
         * w zależności od wybranego formatu. Przetestuj oba warianty na obiekcie Book.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_GzipCompressionComparison {
        /*
         * 🧪 Zadanie 26:
         * Skompresuj oba pliki (.ser i .json) tego samego obiektu przy pomocy
         * GZIPOutputStream i porównaj rozmiary po kompresji – sprawdź, czy tekstowy
         * JSON kompresuje się relatywnie lepiej niż binarny format.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BinaryToJsonMigrationTool {
        /*
         * 🧪 Zadanie 27:
         * Zapisz kilka obiektów Product do pliku binarnego (po kolei przez writeObject()).
         * Napisz narzędzie, które odczyta je wszystkie i zapisze jako JEDNĄ tablicę JSON
         * do nowego pliku (migracja formatu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ResilienceToCorruption {
        /*
         * 🧪 Zadanie 28:
         * Zapisz obiekt binarnie, "zepsuj" kilka bajtów w środku pliku .ser (nadpisz je)
         * i spróbuj go odczytać – złap wyjątek. Zrób to samo dla pliku .json (podmień
         * fragment tekstu na niepoprawny JSON) i porównaj zachowanie/komunikaty błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_InternalEntityVsDto {
        /*
         * 🧪 Zadanie 29:
         * Klasa wewnętrzna UserEntity (Serializable, z polami wrażliwymi jak passwordHash)
         * oraz osobna klasa UserDto (tylko pola bezpieczne do wysłania na zewnątrz).
         * Napisz metodę mapującą UserEntity -> UserDto i zserializuj DTO do JSON-a
         * do wysłania "na zewnątrz", a encję zachowaj binarnie do wewnętrznego cache.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullDualPersistenceScenario {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj dwie metody: saveState(User user, Path path) – zapisuje obiekt
         * binarnie do wewnętrznego cache (transient hasło pomijane), oraz
         * exportToApi(User user) -> String – zwraca JSON do wysyłki na zewnątrz
         * (@JsonIgnore na haśle). Przetestuj obie na tym samym obiekcie User i potwierdź,
         * że hasło nie wycieka w żadnym z formatów.
         */
        public static void main(String[] args) { }
    }
}
