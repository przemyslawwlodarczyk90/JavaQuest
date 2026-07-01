package com.example.javaquest._02_oop.Lesson08_Interfaces;

import java.util.Arrays;
import java.util.List;

public class _Lesson08_Interfaces {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📋 INTERFEJSY – CZYM SĄ?
         * ============================================================
         * Interfejs (interface) to czysty kontrakt: definiuje CO obiekt
         * potrafi zrobić, bez określania JAK to robi.
         *
         * Klasa implementuje interfejs słowem `implements`.
         * Klasa może implementować WIELE interfejsów (brak limitu).
         *
         * Interfejs to typ – zmiennej interfejsowej można przypisać
         * dowolny obiekt, który go implementuje.
         *
         * Domyślnie wszystkie pola w interfejsie to public static final.
         * Domyślnie wszystkie metody (do Java 8) to public abstract.
         */

        Printable doc = new Document("Umowa.pdf", "Treść umowy...");
        doc.print();

        Saveable db = new DatabaseRecord("user_42", "Jan Kowalski");
        db.save();

        /*
         * ============================================================
         * 🔗 WIELE INTERFEJSÓW (multiple interface implementation)
         * ============================================================
         */

        SmartDevice phone = new SmartPhone("iPhone 15");
        phone.connect();
        phone.charge();
        phone.play();

        /*
         * ============================================================
         * 🔹 METODY DEFAULT (od Java 8)
         * ============================================================
         * Interfejs może dostarczyć domyślną implementację metody.
         * Klasa może ją dziedziczyć lub nadpisać.
         *
         * Korzyść: dodanie nowej metody do interfejsu nie psuje klas,
         * które go już implementują.
         */

        Logger consoleLogger = new ConsoleLogger();
        consoleLogger.log("Aplikacja startuje");
        consoleLogger.logError("Błąd połączenia"); // default method
        consoleLogger.logInfo("Diagnostyka OK");   // default method

        /*
         * ============================================================
         * ⚡ METODY STATYCZNE W INTERFEJSACH (od Java 8)
         * ============================================================
         * Interfejs może mieć metody statyczne (pomocnicze).
         * Wywoływane przez: InterfaceName.staticMethod()
         */

        String result = StringTransformer.removeSpaces("Hel lo Wor ld");
        System.out.println("Bez spacji: " + result);

        /*
         * ============================================================
         * 🧩 INTERFEJSY FUNKCYJNE (@FunctionalInterface)
         * ============================================================
         * Interfejs z dokładnie jedną metodą abstrakcyjną.
         * Może być wyrażony jako wyrażenie lambda.
         *
         * Wbudowane: Runnable, Comparator, Predicate, Function itp.
         */

        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;

        System.out.println("3 + 4 = " + add.calculate(3, 4));
        System.out.println("3 * 4 = " + multiply.calculate(3, 4));

        // Comparator to @FunctionalInterface
        List<String> names = Arrays.asList("Zofia", "Adam", "Maria", "Bartłomiej");
        names.sort((a, b) -> a.compareTo(b));
        System.out.println("Posortowane: " + names);

        /*
         * ============================================================
         * 🔒 STAŁE W INTERFEJSACH
         * ============================================================
         * Pola w interfejsie są implicitly public static final.
         * Służą jako stałe dostępne dla wszystkich implementatorów.
         */

        System.out.println("Limit HTTP: " + HttpConstants.MAX_CONNECTIONS);
        System.out.println("Timeout: " + HttpConstants.TIMEOUT_MS + " ms");

        /*
         * ============================================================
         * ⚖️ INTERFACE vs ABSTRACT CLASS (podsumowanie)
         * ============================================================
         *
         * Użyj interfejsu gdy:
         * ✅ klasa musi implementować wiele kontraktów jednocześnie
         * ✅ chcesz zdefiniować czyste API bez stanu
         * ✅ niepowiązane klasy mają implementować to samo zachowanie
         *
         * Użyj abstract class gdy:
         * ✅ masz wspólny stan (pola) i wspólną logikę
         * ✅ hierarchia klas jest blisko powiązana (IS-A)
         * ✅ potrzebujesz konstruktorów lub private metod
         *
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - interface: kontrakt (CO), nie implementacja (JAK)
         * - implements: klasa może implementować wiele interfejsów
         * - default method: domyślna implementacja w interfejsie (Java 8+)
         * - static method w interfejsie: metoda pomocnicza (Java 8+)
         * - @FunctionalInterface: jeden abstract → można użyć lambdy
         * - pola interfejsu = public static final (stałe)
         */
    }
}

interface Printable {
    void print();
}

interface Saveable {
    void save();
}

interface Connectable {
    void connect();
}

interface Chargeable {
    void charge();
}

interface MediaPlayer {
    void play();
}

// Klasa implementująca jedno wybrany interfejs
class Document implements Printable {
    private String name;
    private String content;

    Document(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public void print() {
        System.out.println("[DRUKOWANIE] " + name + ": " + content);
    }
}

class DatabaseRecord implements Saveable {
    private String id;
    private String data;

    DatabaseRecord(String id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public void save() {
        System.out.println("[DB] Zapisuję rekord " + id + ": " + data);
    }
}

// Klasa implementująca wiele interfejsów
interface SmartDevice extends Connectable, Chargeable, MediaPlayer {}

class SmartPhone implements SmartDevice {
    private String model;

    SmartPhone(String model) {
        this.model = model;
    }

    @Override
    public void connect() {
        System.out.println(model + ": łączenie z Wi-Fi...");
    }

    @Override
    public void charge() {
        System.out.println(model + ": ładowanie...");
    }

    @Override
    public void play() {
        System.out.println(model + ": odtwarzanie muzyki...");
    }
}

// Interfejs z default methods
interface Logger {
    void log(String message); // abstract

    default void logError(String message) { // default – można nadpisać
        log("[ERROR] " + message);
    }

    default void logInfo(String message) { // default
        log("[INFO] " + message);
    }
}

class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}

// Interfejs ze static method
interface StringTransformer {
    String transform(String input);

    static String removeSpaces(String input) {
        return input.replace(" ", "");
    }
}

// Interfejs funkcyjny
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

// Interfejs ze stałymi
interface HttpConstants {
    int MAX_CONNECTIONS = 100;   // implicitly public static final
    int TIMEOUT_MS = 5000;
    String DEFAULT_PROTOCOL = "HTTPS";
}
