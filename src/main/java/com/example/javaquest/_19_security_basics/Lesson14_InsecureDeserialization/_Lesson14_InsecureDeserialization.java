package com.example.javaquest._19_security_basics.Lesson14_InsecureDeserialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class _Lesson14_InsecureDeserialization {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: NIEBEZPIECZNA DESERIALIZACJA ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z _04_io/Lesson16_ObjectSerialization
         * ============================================================
         * Poznales juz MECHANIKE serializacji Javy - `Serializable`,
         * `ObjectOutputStream.writeObject()`, `ObjectInputStream
         * .readObject()`. Ta lekcja pokazuje ta sama mechanike z
         * PERSPEKTYWY ATAKU - co sie stanie, gdy `readObject()` zostanie
         * wywolane na danych pochodzacych OD NIEZAUFANEGO ZRODLA (np.
         * przeslanych przez uzytkownika w zadaniu HTTP).
         */
        System.out.println("Ta lekcja poglebia _04_io/Lesson16 - od strony ATAKU na readObject() z niezaufanych danych.");

        demonstrateNormalDeserialization();
        demonstrateWhyDeserializationIsDangerous();
        demonstrateObjectInputFilterDefense();

        /*
         * ============================================================
         * 🔹 DLACZEGO readObject() JEST NIEBEZPIECZNE?
         * ============================================================
         * `ObjectInputStream.readObject()` NIE TYLKO odczytuje dane -
         * ono TWORZY OBIEKTY, wywolujac ich konstruktory/metody
         * specjalne (np. `readObject()` zdefiniowane w klasie,
         * `readResolve()`, `finalize()`). Jesli na CLASSPATH aplikacji
         * istnieje (nawet POSREDNIO, przez zaleznosc trzecia jak Apache
         * Commons Collections w slynnych CVE z lat 2015+) KLASA, ktorej
         * te metody wykonuja NIEBEZPIECZNA operacje (np. wywolanie
         * polecenia systemowego), atakujacy MOZE zbudowac SPECJALNY
         * strumien bajtow (tzw. "gadget chain" - lancuch polaczonych ze
         * soba istniejacych klas), ktory PO DESERIALIZACJI wykona
         * DOWOLNY kod na serwerze - to jest Remote Code Execution (RCE),
         * jedna z NAJGROZNIEJSZYCH klas podatnosci w ogole.
         * WAZNE: ta lekcja NIE URUCHAMIA prawdziwego zlosliwego gadget
         * chain (wymagaloby to konkretnej, podatnej biblioteki na
         * classpath i byloby ryzykowne/niestabilne do demonstracji) -
         * pokazuje MECHANIZM problemu i REALNA obrone (JEP 290).
         */
        System.out.println("\n'Gadget chain' = lancuch ISTNIEJACYCH klas na classpath, ktory po deserializacji wykonuje kod atakujacego (RCE).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `readObject()` na danych OD NIEZAUFANEGO ZRODLA jest
         *   NIEBEZPIECZNE - moze prowadzic do Remote Code Execution
         *   przez tzw. "gadget chains".
         * - Podatnosc NIE wymaga bledu w TWOIM kodzie - wystarczy
         *   podatna biblioteka TRZECIA na classpath (Apache Commons
         *   Collections, Spring, wiele innych mialo takie CVE).
         * - Najlepsza obrona: NIE deserializuj danych od niezaufanego
         *   zrodla w ogole - uzyj formatow bez wykonywalnej logiki (JSON
         *   przez Jackson/Gson - patrz `_04_io/Lesson19-21`).
         * - Jesli deserializacja Javy jest NIEZBEDNA: `ObjectInputFilter`
         *   (JEP 290, od Javy 9) - BIALA LISTA dozwolonych klas do
         *   deserializacji, wszystko inne ODRZUCONE PRZED
         *   skonstruowaniem obiektu.
         * - Zasada ogolna: TRAKTUJ deserializacje jak WYKONANIE KODU, nie
         *   jak "zwykle wczytanie danych" - bo TECHNICZNIE nia jest.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private record UserPreferences(String username, String theme) implements Serializable {
    }

    private static void demonstrateNormalDeserialization() throws Exception {
        System.out.println("\n=== NORMALNA, ZAUFANA DESERIALIZACJA (PRZYPOMNIENIE) ===");

        UserPreferences original = new UserPreferences("jan.kowalski", "dark-mode");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteStream)) {
            out.writeObject(original);
        }

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()))) {
            UserPreferences restored = (UserPreferences) in.readObject();
            System.out.println("Zserializowano i odtworzono: " + restored + " -> to NORMALNIE bezpieczne, bo dane pochodza z WLASNEGO, zaufanego kodu.");
        }
    }

    private static void demonstrateWhyDeserializationIsDangerous() {
        System.out.println("\n=== DLACZEGO readObject() Z NIEZAUFANEGO ZRODLA JEST NIEBEZPIECZNE ===");

        System.out.println("Wyobrazmy sobie endpoint: `byte[] danePrzeslanePrzezUzytkownika = ...; objectInputStream.readObject();`");
        System.out.println("readObject() NIE JEST 'zwyklym odczytem' - TWORZY obiekty, wywoluje konstruktory/metody specjalne.");
        System.out.println("Jesli na classpath istnieje klasa X, ktorej metoda specjalna (np. wywolana posrednio w lancuchu)");
        System.out.println("wykonuje np. `Runtime.getRuntime().exec(...)`, atakujacy moze zbudowac bajty reprezentujace TAKI obiekt");
        System.out.println("BEZ znajomosci hasel/kluczy - to WYSTARCZY, ze klasa jest OBECNA na classpath (np. przez transytywna zaleznosc).");
        System.out.println("Historyczne przyklady: CVE-2015-4852 (WebLogic + Apache Commons Collections), liczne inne w Javie i innych jezykach.");
    }

    /** Klasa celowo BEZPIECZNA (Serializable), uzyta do pokazania dzialania filtra - NIE jest to "gadget". */
    private record SimpleMessage(String text) implements Serializable {
    }

    private static void demonstrateObjectInputFilterDefense() throws Exception {
        System.out.println("\n=== OBRONA: ObjectInputFilter (JEP 290) - BIALA LISTA DOZWOLONYCH KLAS ===");

        SimpleMessage allowedObject = new SimpleMessage("Witaj!");
        UserPreferences disallowedObject = new UserPreferences("haker", "custom-theme");

        byte[] allowedBytes = serialize(allowedObject);
        byte[] disallowedBytes = serialize(disallowedObject);

        // Filtr akceptuje TYLKO SimpleMessage - WSZYSTKO inne jest ODRZUCONE, ZANIM zostanie skonstruowane.
        ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
                SimpleMessage.class.getName() + ";!*"); // "dozwolone: SimpleMessage; wszystko inne (!*): odrzucone"

        System.out.println("Filtr dopuszcza TYLKO: " + SimpleMessage.class.getSimpleName() + " - wszystko inne odrzucone.");

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(allowedBytes))) {
            in.setObjectInputFilter(filter);
            SimpleMessage restored = (SimpleMessage) in.readObject();
            System.out.println("Deserializacja DOZWOLONEJ klasy (" + SimpleMessage.class.getSimpleName() + ") -> sukces: " + restored);
        }

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(disallowedBytes))) {
            in.setObjectInputFilter(filter);
            in.readObject();
            System.out.println("BLAD: niedozwolona klasa zostala zaakceptowana - filtr nie zadzialal!");
        } catch (InvalidClassException e) {
            System.out.println("Deserializacja NIEDOZWOLONEJ klasy (" + UserPreferences.class.getSimpleName()
                    + ") -> ODRZUCONA przez filtr, zanim obiekt zostal skonstruowany: " + e.getMessage());
        }
    }

    private static byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteStream)) {
            out.writeObject(object);
        }
        return byteStream.toByteArray();
    }
}
