package com.example.javaquest._02_oop.Lesson07_AbstractClasses;

public class _Lesson07_AbstractClasses {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🎨 KLASY ABSTRAKCYJNE – CZYM SĄ?
         * ============================================================
         * Klasa abstrakcyjna (abstract class) to klasa, która:
         * - NIE MOŻE być bezpośrednio instantiowana (new AbstractClass() → błąd)
         * - Może zawierać METODY ABSTRAKCYJNE (bez ciała) i KONKRETNE (z ciałem)
         * - Służy jako szablon dla podklas
         *
         * Kiedy używać abstract class?
         * - gdy wiesz, JAK coś robić (wspólna logika) i CO robić (abstrakcyjny kontrakt)
         * - gdy podklasy mają wspólny stan (pola)
         * - gdy chcesz wymusić implementację konkretnych metod przez podklasy
         */

        // AbstractVehicle vehicle = new AbstractVehicle(); // ❌ błąd kompilacji!

        Vehicle car = new VehicleCar("Toyota Corolla", 1500);
        Vehicle motorcycle = new VehicleMotorcycle("Harley-Davidson", 300);
        Vehicle truck = new VehicleTruck("Volvo FH", 12000);

        Vehicle[] vehicles = { car, motorcycle, truck };

        for (Vehicle v : vehicles) {
            System.out.println("--- " + v.name + " ---");
            v.startEngine();   // abstrakcyjna – różna implementacja
            v.displayInfo();   // konkretna (z nadklasy) + abstract call wewnętrznie
            System.out.println("Opłata drogowa: " + v.calculateToll() + " PLN");
        }

        /*
         * ============================================================
         * 🔹 METODY ABSTRAKCYJNE
         * ============================================================
         * abstract void metoda(); // brak ciała!
         *
         * - Zadeklarowane w klasie abstrakcyjnej
         * - Każda nie-abstrakcyjna podklasa MUSI je zaimplementować
         * - Jeśli podklasa nie implementuje wszystkich abstract metod,
         *   ona też musi być abstract
         *
         * Kompilator pilnuje, że nie zapomnisz o implementacji.
         */

        /*
         * ============================================================
         * 🔹 METODY KONKRETNE W KLASIE ABSTRAKCYJNEJ
         * ============================================================
         * Klasa abstrakcyjna może zawierać metody z ciałem.
         * Podklasy dziedziczą je automatycznie lub mogą nadpisać.
         *
         * Przykład: displayInfo() w Vehicle
         * - zawiera wspólną logikę (wypisuje markę)
         * - wewnętrznie wywołuje abstract getType() → polimorfizm
         */

        /*
         * ============================================================
         * ⚖️ ABSTRACT CLASS vs INTERFACE
         * ============================================================
         *
         * Abstract Class:
         * ✅ może mieć pola instancyjne (stan)
         * ✅ może mieć konstruktory
         * ✅ może mieć metody konkretne i abstrakcyjne
         * ✅ może mieć private / protected metody
         * ❌ klasa może dziedziczyć TYLKO z jednej abstract class
         *
         * Interface (szczegóły w Lesson08):
         * ✅ klasa może implementować WIELE interfejsów
         * ✅ definiuje kontrakt (API)
         * ❌ pola to stałe (static final)
         * ❌ (do Java 8) brak metod konkretnych
         *
         * Wybór:
         * - powiązane klasy + wspólny stan → abstract class
         * - kontrakt bez stanu / wielokrotna implementacja → interface
         */

        /*
         * ============================================================
         * 🔹 TEMPLATE METHOD PATTERN (wzorzec szablonowy)
         * ============================================================
         * Klasa abstrakcyjna definiuje „szkielet algorytmu" (template method),
         * a podklasy wypełniają konkretne kroki.
         */

        DataExporter csvExporter = new CsvExporter();
        DataExporter jsonExporter = new JsonExporter();

        System.out.println("\n--- Export CSV ---");
        csvExporter.export(new String[]{"Jan", "Anna", "Tomek"});

        System.out.println("\n--- Export JSON ---");
        jsonExporter.export(new String[]{"Jan", "Anna", "Tomek"});

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - abstract class: nie można tworzyć instancji (new)
         * - abstract method: deklaracja bez ciała – podklasa musi zaimplementować
         * - klasa abstrakcyjna może mieć pola, konstruktory, metody konkretne
         * - extends abstract class: jedna klasa nadrzędna (brak wielokrotnego)
         * - template method: algorytm w nadklasie, kroki w podklasach
         */
    }
}

abstract class Vehicle {
    protected String name;
    protected int weightKg;

    Vehicle(String name, int weightKg) {
        this.name = name;
        this.weightKg = weightKg;
    }

    // Metody abstrakcyjne – każda podklasa musi je zaimplementować
    abstract void startEngine();
    abstract String getType();
    abstract double calculateToll();

    // Metoda konkretna – wspólna dla wszystkich pojazdów
    void displayInfo() {
        System.out.println("Typ: " + getType() + ", waga: " + weightKg + " kg");
    }
}

class VehicleCar extends Vehicle {
    VehicleCar(String name, int weightKg) {
        super(name, weightKg);
    }

    @Override
    void startEngine() {
        System.out.println(name + ": Vroom! (silnik benzynowy)");
    }

    @Override
    String getType() {
        return "Samochód";
    }

    @Override
    double calculateToll() {
        return 10.0;
    }
}

class VehicleMotorcycle extends Vehicle {
    VehicleMotorcycle(String name, int weightKg) {
        super(name, weightKg);
    }

    @Override
    void startEngine() {
        System.out.println(name + ": Brum! (silnik motocyklowy)");
    }

    @Override
    String getType() {
        return "Motocykl";
    }

    @Override
    double calculateToll() {
        return 5.0;
    }
}

class VehicleTruck extends Vehicle {
    VehicleTruck(String name, int weightKg) {
        super(name, weightKg);
    }

    @Override
    void startEngine() {
        System.out.println(name + ": BRUM BRUM! (silnik diesla)");
    }

    @Override
    String getType() {
        return "Ciężarówka";
    }

    @Override
    double calculateToll() {
        return weightKg / 1000.0 * 5; // opłata od wagi
    }
}

// Template Method Pattern
abstract class DataExporter {
    // Template method – definiuje kolejność kroków
    final void export(String[] data) {
        openFile();
        writeHeader();
        for (String row : data) {
            writeRow(row);
        }
        writeFooter();
        closeFile();
    }

    void openFile()  { System.out.println("Otwieranie pliku..."); }
    void closeFile() { System.out.println("Zamykanie pliku."); }

    abstract void writeHeader();
    abstract void writeRow(String row);
    abstract void writeFooter();
}

class CsvExporter extends DataExporter {
    @Override void writeHeader() { System.out.println("name"); }
    @Override void writeRow(String row) { System.out.println(row + ","); }
    @Override void writeFooter() { System.out.println("# end of csv"); }
}

class JsonExporter extends DataExporter {
    @Override void writeHeader() { System.out.println("["); }
    @Override void writeRow(String row) { System.out.println("  \"" + row + "\","); }
    @Override void writeFooter() { System.out.println("]"); }
}
