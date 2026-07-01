package com.example.javaquest._03_collections.Lesson05_HashSet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class _Lesson05_HashSet {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔵 HASHSET – CZYM JEST?
         * ============================================================
         * HashSet to implementacja interfejsu Set oparta na HashMap.
         *
         * Cechy HashSet:
         * ✅ BRAK DUPLIKATÓW – każdy element unikalny
         * ✅ SZYBKIE operacje: add, remove, contains → O(1) średnio
         * ❌ BRAK KOLEJNOŚCI – nie gwarantuje kolejności elementów
         * ❌ NIE jest List – brak get(index)
         *
         * Kiedy Set?
         * - gdy zależy Ci na unikalności elementów
         * - gdy potrzebujesz szybkiego contains()
         * - gdy kolejność nie ma znaczenia
         *
         * Jak działa? Wewnętrznie używa hashCode() do wyznaczenia bucketu.
         * Dlatego klasy w HashSet MUSZĄ mieć poprawne equals() i hashCode()!
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWE OPERACJE
         * ============================================================
         */

        HashSet<String> set = new HashSet<>();
        set.add("Java");
        set.add("Python");
        set.add("C++");
        set.add("Java");     // duplikat – nie zostanie dodany
        set.add("JavaScript");

        System.out.println("Set: " + set); // kolejność może być różna!
        System.out.println("Rozmiar: " + set.size()); // 4, nie 5!
        System.out.println("Zawiera Java? " + set.contains("Java"));
        System.out.println("Zawiera Rust? " + set.contains("Rust"));

        set.remove("Python");
        System.out.println("Po usunięciu Python: " + set);

        /*
         * ============================================================
         * 🔹 OPERACJE NA ZBIORACH (Set Theory)
         * ============================================================
         * union         → addAll
         * intersection  → retainAll
         * difference    → removeAll
         * subset check  → containsAll
         */

        Set<Integer> setA = new HashSet<>(java.util.List.of(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(java.util.List.of(3, 4, 5, 6, 7));

        // Suma (union) – nie mutujemy oryginałów
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("\nA ∪ B (suma): " + union);

        // Iloczyn (intersection)
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("A ∩ B (iloczyn): " + intersection);

        // Różnica
        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("A \\ B (różnica): " + difference);

        // Sprawdzenie podzbioru
        Set<Integer> subset = new HashSet<>(java.util.List.of(3, 4));
        System.out.println("Czy {3,4} ⊆ setA? " + setA.containsAll(subset));

        /*
         * ============================================================
         * 🔹 HASHSET Z WŁASNYMI OBIEKTAMI
         * ============================================================
         * Aby HashSet poprawnie wykrywał duplikaty własnych obiektów,
         * klasa MUSI nadpisać equals() i hashCode()!
         *
         * Bez nadpisania: dwa obiekty o tych samych danych → różne (domyślne)
         * Z nadpisaniem: dwa obiekty o tych samych danych → równe
         */

        HashSet<Student> students = new HashSet<>();
        students.add(new Student("Jan", 1001));
        students.add(new Student("Anna", 1002));
        students.add(new Student("Jan", 1001)); // duplikat – nie zostanie dodany

        System.out.println("\nStudenci: " + students.size() + " unikalnych");
        students.forEach(System.out::println);

        /*
         * ============================================================
         * 🔹 ITERACJA PO HASHSET
         * ============================================================
         * for-each – najczęstsza forma (kolejność nieznana)
         * Iterator – gdy trzeba usuwać podczas iteracji
         * forEach  – lambda (Java 8+)
         */

        System.out.println("\nIteracja:");
        for (String lang : set) {
            System.out.println("  " + lang);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * HashSet<T>:
         * - add(e)       → dodaje (false jeśli duplikat)
         * - remove(e)    → usuwa (false jeśli brak)
         * - contains(e)  → sprawdza (O(1) amortyzowane)
         * - size()       → liczba elementów
         * - isEmpty()    → sprawdza pustość
         * - addAll()     → union   (suma zbiorów)
         * - retainAll()  → intersection (iloczyn)
         * - removeAll()  → difference (różnica)
         * - containsAll()→ subset check (podzbiór?)
         *
         * ⚠️ Klasy w HashSet muszą mieć equals() + hashCode()!
         * ⚠️ Kolejność elementów NIE jest gwarantowana!
         */
    }
}

class Student {
    private String name;
    private int id;

    Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student s = (Student) o;
        return id == s.id && Objects.equals(name, s.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Student{" + name + ", id=" + id + "}";
    }
}
