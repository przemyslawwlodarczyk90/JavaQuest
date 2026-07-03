package com.example.javaquest._04_io.Lesson16_ObjectSerialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class _Lesson16_ObjectSerialization {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 SERIALIZACJA OBIEKTÓW – CZYM JEST?
         * ============================================================
         * Serializacja to zamiana STANU obiektu (jego pol) na strumien
         * bajtow, ktory mozna zapisac do pliku, wyslac przez siec itd.
         * Deserializacja to proces odwrotny - odtworzenie obiektu z bajtow.
         *
         * Warunek: klasa musi implementowac Serializable - to interfejs
         * MARKERowy (bez zadnych metod!), ktory tylko "oznacza" klase jako
         * mozliwa do zserializowania. Mechanizm serializacji obsluguje
         * wtedy sama maszyna JVM (przez refleksje).
         *
         * interface Serializable { } // pusty - marker interface
         *
         * Narzedzia:
         * - ObjectOutputStream.writeObject(obj) -> zapisuje obiekt
         * - ObjectInputStream.readObject()      -> odczytuje obiekt (Object,
         *   wymaga rzutowania)
         */

        Path tempFile = Files.createTempFile("lesson16_person", ".ser");
        Path listFile = Files.createTempFile("lesson16_list", ".ser");
        Path graphFile = Files.createTempFile("lesson16_graph", ".ser");

        try {
            /*
             * ============================================================
             * 🔹 SERIALIZACJA POJEDYNCZEGO OBIEKTU
             * ============================================================
             */

            System.out.println("=== Zapis pojedynczego obiektu ===");
            Person person = new Person("Anna Kowalska", 30, "tajne-haslo");

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempFile.toFile()))) {
                out.writeObject(person);
            }
            System.out.println("Zapisano: " + person);

            System.out.println("=== Odczyt (deserializacja) pojedynczego obiektu ===");
            Person restored;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(tempFile.toFile()))) {
                restored = (Person) in.readObject();
            }
            System.out.println("Odczytano: " + restored);
            // Pole "password" jest transient -> NIE zostalo zserializowane!
            System.out.println("Haslo po deserializacji: " + restored.password); // null

            System.out.println();

            /*
             * ============================================================
             * 🔍 SERIALIZACJA LISTY OBIEKTÓW
             * ============================================================
             * ArrayList (i wiekszosc kolekcji z java.util) implementuje
             * Serializable - mozna wiec zserializowac cala liste obiektow
             * jednym wywolaniem writeObject(), pod warunkiem ze elementy
             * listy TEZ sa Serializable.
             */

            System.out.println("=== Zapis listy obiektow ===");
            List<Person> people = new ArrayList<>();
            people.add(new Person("Jan Nowak", 25, "haslo1"));
            people.add(new Person("Ewa Wisniewska", 40, "haslo2"));
            people.add(new Person("Piotr Zielinski", 35, "haslo3"));

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(listFile.toFile()))) {
                out.writeObject(people);
            }
            System.out.println("Zapisano liste " + people.size() + " osob");

            System.out.println("=== Odczyt listy obiektow ===");
            @SuppressWarnings("unchecked")
            List<Person> restoredPeople;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(listFile.toFile()))) {
                restoredPeople = (List<Person>) in.readObject();
            }
            restoredPeople.forEach(System.out::println);

            System.out.println();

            /*
             * ============================================================
             * ⚠️ SERIALIZACJA GRAFU OBIEKTÓW (REFERENCJE) vs ZWYKŁY ZAPIS PÓL
             * ============================================================
             * Gdy obiekt A ma pole bedace referencja do obiektu B, to
             * serializacja A automatycznie serializuje TAKZE caly "graf"
             * powiazanych obiektow (B, i to co B referencuje, itd.) -
             * o ile wszystkie sa Serializable.
             *
             * To NIE jest to samo co zwykly zapis "plaskich" pol (np. samych
             * String/int) - mechanizm serializacji ZACHOWUJE TOZSAMOSC
             * (identity) obiektow w grafie:
             *
             * Jesli DWA pola wskazuja na TEN SAM obiekt w pamieci, to po
             * deserializacji te dwa pola BEDA WSKAZYWAC NA TEN SAM (jeden)
             * zdeserializowany obiekt (== bedzie true), a nie na dwie
             * oddzielne kopie!
             */

            System.out.println("=== Graf obiektow - zachowanie tozsamosci referencji ===");
            Address sharedAddress = new Address("Warszawa", "Marszalkowska 1");
            Employee boss = new Employee("Szefowa", sharedAddress);
            Employee worker = new Employee("Pracownik", sharedAddress); // ta sama referencja!

            System.out.println("Przed serializacja - ten sam adres (==)? "
                    + (boss.address == worker.address)); // true

            Company company = new Company(List.of(boss, worker));

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(graphFile.toFile()))) {
                out.writeObject(company);
            }

            Company restoredCompany;
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(graphFile.toFile()))) {
                restoredCompany = (Company) in.readObject();
            }

            Employee restoredBoss = restoredCompany.employees.get(0);
            Employee restoredWorker = restoredCompany.employees.get(1);

            System.out.println("Po deserializacji - ten sam adres (==)? "
                    + (restoredBoss.address == restoredWorker.address)); // true - JVM odtworzyl WSPOLNA referencje!
            System.out.println("Adres szefowej: " + restoredBoss.address);
            System.out.println("Adres pracownika: " + restoredWorker.address);

            /*
             * Gdyby to byl "zwykly zapis pol" (np. recznie String po Stringu
             * do pliku tekstowego), nie byloby zadnego pojecia "tej samej
             * referencji" - odtworzylibysmy po prostu DWIE oddzielne kopie
             * danych adresu, bez zwiazku miedzy nimi. Serializacja natywna
             * Javy zachowuje pelna strukture grafu obiektow, wlacznie z
             * dzielonymi referencjami i (potencjalnie) cyklami.
             */

        } finally {
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(listFile);
            Files.deleteIfExists(graphFile);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Serializable to interfejs markerowy (bez metod) - "oznacza"
         *   klase jako mozliwa do zapisania/odczytania przez JVM
         * - ObjectOutputStream.writeObject() / ObjectInputStream.readObject()
         *   - zapis i odczyt calych obiektow (nie tylko prostych typow)
         * - Kolekcje (ArrayList, itp.) sa Serializable - mozna serializowac
         *   cale listy obiektow, o ile elementy tez sa Serializable
         * - pole "transient" -> pomijane przy serializacji (np. hasla,
         *   dane wrazliwe, pola liczone na nowo)
         * - Serializacja GRAFU obiektow zachowuje TOZSAMOSC referencji:
         *   wspoldzielony obiekt zostaje zapisany RAZ i po deserializacji
         *   nadal jest wspoldzielony (== true), w przeciwienstwie do
         *   naiwnego zapisu pol, ktory tworzylby oddzielne kopie
         * - serialVersionUID (widoczny w klasach ponizej) pozwala
         *   kontrolowac zgodnosc wersji klasy miedzy zapisem a odczytem
         */
    }

    /**
     * Prosta klasa z polem transient (haslo nie jest serializowane).
     */
    static class Person implements Serializable {
        private static final long serialVersionUID = 1L;

        String name;
        int age;
        transient String password; // NIE bedzie zserializowane

        Person(String name, int age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", password=" + password + "}";
        }
    }

    static class Address implements Serializable {
        private static final long serialVersionUID = 1L;

        String city;
        String street;

        Address(String city, String street) {
            this.city = city;
            this.street = street;
        }

        @Override
        public String toString() {
            return city + ", " + street;
        }
    }

    static class Employee implements Serializable {
        private static final long serialVersionUID = 1L;

        String name;
        Address address; // referencja do obiektu - czesc grafu

        Employee(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    static class Company implements Serializable {
        private static final long serialVersionUID = 1L;

        List<Employee> employees;

        Company(List<Employee> employees) {
            this.employees = new ArrayList<>(employees);
        }
    }
}
