package com.example.javaquest._04_io.Lesson16_ObjectSerialization;

public class _Exercises_Lesson16_ObjectSerialization {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SerializeBook {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Book (implements Serializable) z polami title, author,
         * year. Zserializuj obiekt Book("Pan Tadeusz","Adam Mickiewicz",1834)
         * do pliku tymczasowego .ser przez ObjectOutputStream. Wypisz
         * potwierdzenie zapisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DeserializeBook {
        /*
         * 🧪 Zadanie 2:
         * Zserializuj obiekt Book z Zadania 1 do pliku, a następnie w tym
         * samym programie odczytaj go z powrotem przez ObjectInputStream
         * i wypisz jego toString().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TransientField {
        /*
         * 🧪 Zadanie 3:
         * Dodaj do klasy Book pole transient int borrowCount = 5 ustawione
         * przed serializacją. Zserializuj i zdeserializuj obiekt, wypisz
         * wartość borrowCount po odczycie (powinno być 0 - wartość domyślna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SerializeList {
        /*
         * 🧪 Zadanie 4:
         * Stwórz List<String> {"jeden","dwa","trzy"}. Zserializuj całą listę
         * jednym writeObject() do pliku, odczytaj ją z powrotem i wypisz
         * odtworzoną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ArrayVsListSerialization {
        /*
         * 🧪 Zadanie 5:
         * Zserializuj int[] {1,2,3,4,5} bezpośrednio (tablice są Serializable)
         * do pliku i odczytaj z powrotem. Porównaj z serializacją tej samej
         * zawartości jako List<Integer> - czy oba podejścia działają
         * bez problemu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NotSerializableException {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę NieSerializowalna BEZ implements Serializable.
         * Spróbuj ją zserializować przez ObjectOutputStream.writeObject().
         * Złap NotSerializableException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SerialVersionUidComparison {
        /*
         * 🧪 Zadanie 7:
         * Stwórz dwie klasy: KlasaZUid z jawnie zadeklarowanym
         * serialVersionUID=1L, i KlasaBezUid bez tego pola. Wypisz w
         * komentarzu/println różnicę - dlaczego brak jawnego UID jest
         * ryzykowny przy zmianach klasy w przyszłości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_StaticFieldNotSerialized {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę z polem static int licznikInstancji. Zwiększ je,
         * zserializuj instancję obiektu, potem zmień wartość statycznego
         * pola w pamięci, zdeserializuj obiekt i sprawdź że pole statyczne
         * (wspólne dla klasy) nie zostało w ogóle zapisane/odczytane
         * z pliku - jest niezależne od serializacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IndependentCopies {
        /*
         * 🧪 Zadanie 9:
         * Zserializuj ten sam obiekt Book do DWÓCH osobnych plików.
         * Zdeserializuj oba pliki do dwóch zmiennych i sprawdź (==) że to
         * dwie ODDZIELNE instancje w pamięci, mimo identycznej zawartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GenericRoundTripHelper {
        /*
         * 🧪 Zadanie 10:
         * Napisz generyczną metodę pomocniczą
         * <T> T serializeThenDeserialize(T obj, Path tempFile), która
         * zapisuje obiekt do pliku i od razu go odczytuje z powrotem.
         * Przetestuj na prostym obiekcie Book.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EmployeeWithSkillsAndPassword {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj klasę Employee (jak w teorii) o pole transient String
         * password oraz pole List<String> skills. Zserializuj pracownika
         * z listą umiejętności i hasłem, zdeserializuj i sprawdź że skills
         * są zachowane, a password == null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SharedReferenceIdentity {
        /*
         * 🧪 Zadanie 12:
         * Stwórz jeden obiekt Address, przypisz go do pól address DWÓCH
         * różnych obiektów Employee. Umieść oba w jednym obiekcie Company
         * i zserializuj Company. Po deserializacji sprawdź (==) że oba
         * pola address dalej wskazują na TEN SAM obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CyclicObjectGraph {
        /*
         * 🧪 Zadanie 13:
         * Stwórz klasę Node(String name, Node next) implements Serializable.
         * Zbuduj cykl: nodeA.next = nodeB, nodeB.next = nodeA. Zserializuj
         * nodeA i zdeserializuj - sprawdź że się udało (bez nieskończonej
         * pętli/StackOverflowError) i że restoredA.next.next == restoredA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CustomWriteReadObject {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę z polem transient int cache, oraz prywatnymi metodami
         * writeObject(ObjectOutputStream) i readObject(ObjectInputStream)
         * wywołującymi defaultWriteObject()/defaultReadObject(), a w
         * readObject() dodatkowo ustawiającymi cache na wyliczoną domyślną
         * wartość (np. -1) zamiast zera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SerializeMap {
        /*
         * 🧪 Zadanie 15:
         * Stwórz Map<String, Person> z 3 wpisami (klucz to imię). Zserializuj
         * całą mapę do pliku, zdeserializuj i wypisz wszystkie wpisy -
         * sprawdź że HashMap i jego zawartość są poprawnie odtworzone.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_VersionCompatibilitySimulation {
        /*
         * 🧪 Zadanie 16:
         * Zapisz obiekt klasy z polami (name, age) i serialVersionUID=1L.
         * W komentarzu opisz (i jeśli to możliwe zademonstruj) co by się
         * stało, gdyby do klasy dodać nowe pole (np. email) z tym samym UID
         * i spróbować odczytać STARY plik - jaka byłaby wartość nowego pola
         * po deserializacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EnumSerialization {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj enum Status {AKTYWNY, NIEAKTYWNY, ZAWIESZONY}. Stwórz
         * klasę z polem tego enuma, zserializuj i zdeserializuj obiekt,
         * sprawdź że odtworzona wartość enuma jest poprawna (== oryginalnej
         * stałej enum, bo enumy zachowują tożsamość instancji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OrderWithNestedProducts {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasy Product(name, price) i Order(id, List<Product> items).
         * Zserializuj listę kilku zamówień (List<Order>) do jednego pliku,
         * zdeserializuj i wypisz wszystkie zamówienia z ich produktami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FileSizeWithVsWithoutTransient {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę z dużym polem (np. String o 10000 znakach). Zserializuj
         * ją raz z tym polem jako zwykłym (nie-transient), a raz oznaczając
         * je jako transient. Porównaj Files.size() obu wynikowych plików.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GenericObjectFileStore {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj generyczną klasę ObjectFileStore<T extends Serializable>
         * z metodami void save(T obj, Path path) i T load(Path path),
         * opakowującymi ObjectOutputStream/ObjectInputStream. Przetestuj
         * na własnej klasie (np. Book).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SaveGameSystem {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj klasy: Item(name), Inventory(List<Item>), Player(name, Inventory),
         * GameState(List<Player> players). Utwórz stan gry z 2 graczami
         * i różnymi przedmiotami, zserializuj cały GameState do pliku,
         * zdeserializuj do nowej zmiennej i zweryfikuj że wszystkie dane
         * (gracze, ekwipunek) się zgadzają z oryginałem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_VersionEvolutionV1toV2 {
        /*
         * 🧪 Zadanie 22:
         * Zdefiniuj PersonV1(name, age) z serialVersionUID=42L, zserializuj
         * instancję do pliku. Zdefiniuj PersonV2(name, age, email) z TYM
         * SAMYM serialVersionUID=42L. Spróbuj odczytać plik zapisany przez
         * PersonV1 jako PersonV2 (wczytując bajty ręcznie z podmienioną
         * definicją klasy w tym samym uruchomieniu) i opisz/wypisz
         * zaobserwowany wynik (np. wartość domyślna dla email).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DeepCopyViaSerialization {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę <T extends Serializable> T deepCopy(T obj) używającą
         * ByteArrayOutputStream/ByteArrayInputStream (bez pliku na dysku)
         * do serializacji i deserializacji w pamięci. Przetestuj na obiekcie
         * z zagnieżdżoną listą - zmień listę w kopii i sprawdź że oryginał
         * pozostał niezmieniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_UndoHistoryStack {
        /*
         * 🧪 Zadanie 24:
         * Stwórz mutowalną klasę Document(StringBuilder content) implements
         * Serializable. Zaimplementuj stos historii (Deque<Document>)
         * przechowujący głębokie kopie (przez deepCopy z Zadania 23) po
         * każdej zmianie treści. Zaimplementuj undo() przywracające
         * poprzedni stan. Przetestuj 3 zmiany i 2 wywołania undo().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CyclicGraphIdentityAfterRestore {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj przykład z Zadania 13: zweryfikuj dokładnie, że po
         * deserializacji zachowana jest zarówno struktura cyklu
         * (restoredA.next.next == restoredA), jak i to, że jeśli inny
         * obiekt też referencjonuje nodeA, to po deserializacji wszystkie
         * referencje wskazują na ten sam odtworzony węzeł.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_KeyedObjectFileCache {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj klasę ObjectCache z katalogiem bazowym, metodami
         * put(String key, Serializable obj) (zapisuje do pliku key.ser),
         * Object get(String key) (odczytuje plik jeśli istnieje, inaczej
         * null) i delete(String key). Przetestuj zapisując 3 różne obiekty
         * pod różnymi kluczami i odczytując je z powrotem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SerializationVsManualTextWriting {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj DWIE wersje zapisu obiektu Company (z Employee
         * dzielącymi wspólny Address, jak w teorii): (a) natywna serializacja
         * Javy, (b) ręczny zapis pól do pliku tekstowego (linia po linii,
         * bez zachowania tożsamości referencji). Po odczycie obu wersji
         * porównaj czy współdzielenie referencji adresu zostało zachowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExternalizableImplementation {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj klasę implements Externalizable (zamiast Serializable)
         * z ręcznie napisanymi metodami writeExternal(ObjectOutput) i
         * readExternal(ObjectInput), zapisującymi/odczytującymi pola ręcznie
         * (writeUTF, writeInt itp.). Przetestuj zapis/odczyt i porównaj
         * ilość kodu z podejściem Serializable.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SafeDeserializationGuard {
        /*
         * 🧪 Zadanie 29:
         * Stwórz klasę Person(name, age) z prywatną metodą
         * readObject(ObjectInputStream), która po defaultReadObject()
         * sprawdza czy age mieści się w zakresie 0-150, a jeśli nie -
         * rzuca InvalidObjectException. Przetestuj na spreparowanym pliku
         * z niepoprawnym wiekiem (np. zapisanym przez obiekt zbudowany
         * z pominięciem walidacji konstruktora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FileBasedObjectDatabase {
        /*
         * 🧪 Zadanie 30:
         * Mini-projekt: zaimplementuj klasę SimpleObjectDb<T extends Serializable>
         * przechowującą List<T> w jednym pliku .ser. Metody: saveAll(List<T>),
         * List<T> loadAll(), append(T record) (wczytuje istniejącą listę,
         * dodaje rekord, zapisuje z powrotem), List<T> findBy(Predicate<T>).
         * Przetestuj rosnącą listę obiektów Person poprzez kilka cykli
         * append/loadAll w jednym uruchomieniu main.
         */
        public static void main(String[] args) { }
    }
}
