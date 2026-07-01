package com.example.javaquest._03_collections.Lesson19_ComparableVsComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class _Lesson19_ComparableVsComparator {

    public static void main(String[] args) {

        /*
         * ============================================================
         * ⚖️ COMPARABLE VS COMPARATOR – PEŁNE PORÓWNANIE
         * ============================================================
         *
         * COMPARABLE (java.lang.Comparable<T>):
         * ┌───────────────────────────────────────────────────────┐
         * │ interface Comparable<T> {                             │
         * │     int compareTo(T other);                           │
         * │ }                                                     │
         * └───────────────────────────────────────────────────────┘
         * - Implementowana PRZEZ SAMĄ KLASĘ
         * - Definiuje NATURALNE PORZĄDKOWANIE (natural ordering)
         * - Jedna klasa = jedna kolejność (można ją zmienić!)
         * - Używana automatycznie przez: Collections.sort, TreeSet,
         *   TreeMap, PriorityQueue (bez Comparatora)
         *
         * COMPARATOR (java.util.Comparator<T>):
         * ┌───────────────────────────────────────────────────────┐
         * │ interface Comparator<T> {                             │
         * │     int compare(T a, T b);                            │
         * │     // + metody default (Java 8+): reversed, then...  │
         * │ }                                                     │
         * └───────────────────────────────────────────────────────┘
         * - ZEWNĘTRZNA strategia sortowania (osobna klasa lub lambda)
         * - Może być wiele różnych Comparatorów dla jednej klasy
         * - Przekazywana do: Collections.sort(list, comp),
         *   list.sort(comp), new TreeSet<>(comp), new PriorityQueue<>(comp)
         *
         * Wynik compareTo/compare:
         *   < 0  → first element comes BEFORE second
         *   = 0  → equal
         *   > 0  → first element comes AFTER second
         */

        /*
         * ============================================================
         * 🔹 COMPARABLE – NATURALNE PORZĄDKOWANIE
         * ============================================================
         */

        System.out.println("=== COMPARABLE ===");

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Zofia", "Kowalska", 5500));
        employees.add(new Employee("Adam", "Nowak", 4200));
        employees.add(new Employee("Maria", "Kowalska", 6800));
        employees.add(new Employee("Bartek", "Wiśniewski", 4200));
        employees.add(new Employee("Celina", "Nowak", 3900));

        Collections.sort(employees); // używa compareTo() – naturalne: po nazwisku, potem imieniu
        System.out.println("Naturalne (nazwisko, imię):");
        employees.forEach(System.out::println);

        // TreeSet też używa compareTo() automatycznie
        TreeSet<Employee> empSet = new TreeSet<>(employees);
        System.out.println("\nTreeSet (naturalny):");
        empSet.forEach(System.out::println);

        /*
         * ============================================================
         * 🔹 COMPARATOR – NIESTANDARDOWE PORZĄDKOWANIE
         * ============================================================
         */

        System.out.println("\n=== COMPARATOR ===");

        // 1. Po pensji rosnąco
        Comparator<Employee> bySalaryAsc = Comparator.comparingInt(Employee::getSalary);
        employees.sort(bySalaryAsc);
        System.out.println("Po pensji rosnąco:");
        employees.forEach(System.out::println);

        // 2. Po pensji malejąco
        employees.sort(Comparator.comparingInt(Employee::getSalary).reversed());
        System.out.println("\nPo pensji malejąco:");
        employees.forEach(System.out::println);

        // 3. Po imieniu
        employees.sort(Comparator.comparing(Employee::getFirstName));
        System.out.println("\nPo imieniu:");
        employees.forEach(System.out::println);

        // 4. Po pensji, potem po nazwisku, potem po imieniu
        employees.sort(Comparator.comparingInt(Employee::getSalary)
                .thenComparing(Employee::getLastName)
                .thenComparing(Employee::getFirstName));
        System.out.println("\nPensja → Nazwisko → Imię:");
        employees.forEach(System.out::println);

        // 5. TreeSet z custom Comparatorem
        TreeSet<Employee> byFirstName = new TreeSet<>(Comparator.comparing(Employee::getFirstName));
        byFirstName.addAll(employees);
        System.out.println("\nTreeSet po imieniu:");
        byFirstName.forEach(System.out::println);

        /*
         * ============================================================
         * 🔹 COMPARATOR – STARY STYL (PRZED JAVA 8)
         * ============================================================
         */

        System.out.println("\n=== Comparator stary styl ===");

        // Anonimowa klasa
        Comparator<Employee> oldStyle = new Comparator<Employee>() {
            @Override
            public int compare(Employee a, Employee b) {
                int salaryComp = Integer.compare(a.getSalary(), b.getSalary());
                if (salaryComp != 0) return salaryComp;
                return a.getLastName().compareTo(b.getLastName());
            }
        };

        employees.sort(oldStyle);
        employees.forEach(System.out::println);

        /*
         * ============================================================
         * 🔹 KIEDY COMPARABLE, KIEDY COMPARATOR?
         * ============================================================
         *
         * COMPARABLE gdy:
         * ✅ Klasa ma jedno "oczywiste" naturalne porządkowanie
         * ✅ Ktoś inny użyje twojej klasy (masz dostęp do kodu)
         * Przykłady: String (alfa), Integer (liczbowo), LocalDate (chronologicznie)
         *
         * COMPARATOR gdy:
         * ✅ Potrzebujesz wielu różnych sposobów sortowania
         * ✅ Nie masz dostępu do kodu klasy (klasy zewnętrzne/biblioteczne)
         * ✅ Chcesz posortować po różnych kryteriach w różnych kontekstach
         * ✅ Sortowanie ad-hoc (jednorazowe, inline)
         *
         * Mogą też działać RAZEM:
         * Klasa implementuje Comparable (domyślne) + masz Comparatory (alternatywne)
         *
         * ============================================================
         * 📌 PODSUMOWANIE compareTo i compare
         * ============================================================
         * Dobrze pamiętać: zwracaj Integer.compare(a.field, b.field)
         * dla int/long/double zamiast a.field - b.field (overflow!)
         *
         * String porównaj: a.field.compareTo(b.field)
         *
         * Null-safe: Comparator.nullsFirst/nullsLast
         */
    }
}

class Employee implements Comparable<Employee> {
    private String firstName;
    private String lastName;
    private int salary;

    Employee(String firstName, String lastName, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    String getFirstName() { return firstName; }
    String getLastName() { return lastName; }
    int getSalary() { return salary; }

    @Override
    public int compareTo(Employee other) {
        int lastNameComp = this.lastName.compareTo(other.lastName);
        if (lastNameComp != 0) return lastNameComp;
        return this.firstName.compareTo(other.firstName);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-12s %d PLN", firstName, lastName, salary);
    }
}
