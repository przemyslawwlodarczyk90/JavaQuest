package com.example.javaquest._03_collections.Lesson01_ArrayList;

import java.util.ArrayList;
import java.util.List;

public class _Lesson01_ArrayList {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📋 ARRAYLIST – CZYM JEST?
         * ============================================================
         * ArrayList to implementacja interfejsu List oparta na tablicy.
         *
         * Różnice ArrayList vs tablica (array):
         * - ArrayList: dynamicznie rośnie, ma metody (add, remove, contains...)
         * - tablica:   stały rozmiar, dostęp przez [], brak metod
         *
         * Kiedy ArrayList?
         * - gdy liczba elementów jest zmienna
         * - gdy potrzebujesz częstego dostępu po indeksie (O(1))
         * - gdy kolejność elementów ma znaczenie
         *
         * Pakiet: java.util.ArrayList
         */

        /*
         * ============================================================
         * 🔹 TWORZENIE ARRAYLIST
         * ============================================================
         */

        ArrayList<String> fruits = new ArrayList<>();          // pusta lista
        ArrayList<Integer> numbers = new ArrayList<>(10);      // pojemność startowa 10
        List<String> colors = new ArrayList<>();               // przez interfejs List

        /*
         * ============================================================
         * ➕ DODAWANIE ELEMENTÓW
         * ============================================================
         * add(element)        → na końcu
         * add(index, element) → na konkretnej pozycji (przesuwa resztę)
         * addAll(kolekcja)    → dodaje całą kolekcję
         */

        fruits.add("Jabłko");
        fruits.add("Banan");
        fruits.add("Gruszka");
        fruits.add("Mango");
        fruits.add(1, "Kiwi"); // wstawia na indeksie 1

        System.out.println("Owoce: " + fruits);
        System.out.println("Rozmiar: " + fruits.size());

        /*
         * ============================================================
         * 🔍 DOSTĘP DO ELEMENTÓW
         * ============================================================
         * get(index)      → element o danym indeksie (O(1))
         * indexOf(elem)   → pierwszy indeks elementu (-1 jeśli nie ma)
         * lastIndexOf(e)  → ostatni indeks
         * contains(elem)  → czy element istnieje (boolean)
         */

        System.out.println("Element [0]: " + fruits.get(0));
        System.out.println("Indeks Banana: " + fruits.indexOf("Banan"));
        System.out.println("Czy zawiera Mango? " + fruits.contains("Mango"));
        System.out.println("Czy zawiera Arbuz? " + fruits.contains("Arbuz"));

        /*
         * ============================================================
         * ✏️ MODYFIKOWANIE ELEMENTÓW
         * ============================================================
         * set(index, newElement) → zastępuje element (zwraca stary)
         */

        String old = fruits.set(2, "Truskawka");
        System.out.println("Zastąpiony: " + old + " → Truskawka");
        System.out.println("Po set: " + fruits);

        /*
         * ============================================================
         * ❌ USUWANIE ELEMENTÓW
         * ============================================================
         * remove(index)   → usuwa po indeksie (zwraca usunięty element)
         * remove(Object)  → usuwa pierwsze wystąpienie obiektu (boolean)
         * clear()         → usuwa wszystkie elementy
         * removeIf(pred)  → usuwa pasujące do predykatu (Java 8+)
         */

        fruits.remove(0);                   // usuwa "Jabłko"
        fruits.remove("Kiwi");              // usuwa "Kiwi"
        System.out.println("Po usunięciu: " + fruits);

        numbers.add(10); numbers.add(20); numbers.add(5); numbers.add(30); numbers.add(15);
        numbers.removeIf(n -> n < 15);     // usuwa wszystkie < 15
        System.out.println("Po removeIf(<15): " + numbers);

        /*
         * ============================================================
         * 🔢 ROZMIAR I SPRAWDZANIE PUSTOŚCI
         * ============================================================
         */

        System.out.println("Rozmiar: " + fruits.size());
        System.out.println("Pusta? " + fruits.isEmpty());

        ArrayList<String> empty = new ArrayList<>();
        System.out.println("Pusta lista: " + empty.isEmpty());

        /*
         * ============================================================
         * 📋 PODLISTA (subList)
         * ============================================================
         * subList(from, to) → widok (view) fragmentu listy [from, to)
         * Uwaga: zmiany na podliście wpływają na oryginał!
         */

        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) nums.add(i);

        List<Integer> sub = nums.subList(2, 6); // [3, 4, 5, 6]
        System.out.println("Podlista [2,6): " + sub);

        /*
         * ============================================================
         * 🔄 KONWERSJA
         * ============================================================
         * toArray()     → ArrayList → Object[]
         * List.of(...)  → immutable lista (nie ArrayList)
         * Arrays.asList → fixed-size lista
         */

        Object[] array = fruits.toArray();
        System.out.println("Jako tablica: " + java.util.Arrays.toString(array));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * ArrayList<T>:
         * - add(e), add(i,e), addAll(coll)     → dodawanie
         * - get(i), indexOf(e), contains(e)     → dostęp
         * - set(i, e)                           → modyfikacja
         * - remove(i), remove(e), removeIf(p)  → usuwanie
         * - size(), isEmpty(), clear()          → stan
         * - subList(from, to)                  → widok fragmentu
         *
         * Złożoność:
         * - get/set: O(1) – szybki dostęp po indeksie
         * - add na końcu: O(1) amortyzowane
         * - add/remove w środku: O(n) – przesuwanie elementów
         */
    }
}
