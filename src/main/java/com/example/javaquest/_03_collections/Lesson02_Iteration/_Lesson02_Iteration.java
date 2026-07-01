package com.example.javaquest._03_collections.Lesson02_Iteration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class _Lesson02_Iteration {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔁 SPOSOBY ITERACJI PO KOLEKCJI
         * ============================================================
         * Java oferuje kilka sposobów przechodzenia przez kolekcję:
         * 1. for z indeksem
         * 2. for-each (enhanced for)
         * 3. Iterator
         * 4. ListIterator
         * 5. forEach z lambdą (Java 8+)
         */

        ArrayList<String> names = new ArrayList<>();
        names.add("Anna"); names.add("Bartek"); names.add("Celina");
        names.add("Damian"); names.add("Ewa");

        /*
         * ============================================================
         * 🔹 1. FOR Z INDEKSEM
         * ============================================================
         * Najlepsza gdy potrzebujemy indeksu.
         * Możemy iterować wstecz.
         */

        System.out.println("=== for z indeksem ===");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + ": " + names.get(i));
        }

        // Iteracja wstecz
        System.out.println("Wstecz:");
        for (int i = names.size() - 1; i >= 0; i--) {
            System.out.print(names.get(i) + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 🔹 2. FOR-EACH (ENHANCED FOR)
         * ============================================================
         * Najprostszy i najczytelniejszy.
         * NIE daje dostępu do indeksu.
         * NIE można modyfikować listy podczas iteracji! (ConcurrentModificationException)
         */

        System.out.println("=== for-each ===");
        for (String name : names) {
            System.out.println("Imię: " + name);
        }

        /*
         * ============================================================
         * 🔹 3. ITERATOR
         * ============================================================
         * Interfejs Iterator<E> z metodami:
         * - boolean hasNext() → czy jest następny element
         * - E next()          → zwraca następny i przesuwa kursor
         * - void remove()     → usuwa ostatnio zwrócony element (bezpiecznie!)
         *
         * ✅ Iterator.remove() to BEZPIECZNY sposób usuwania podczas iteracji.
         */

        System.out.println("=== Iterator ===");
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String name = it.next();
            System.out.print(name + " ");
        }
        System.out.println();

        // Bezpieczne usuwanie przez iterator
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) numbers.add(i);

        Iterator<Integer> numIt = numbers.iterator();
        while (numIt.hasNext()) {
            int n = numIt.next();
            if (n % 2 == 0) {
                numIt.remove(); // ✅ bezpieczne – nie ConcurrentModificationException
            }
        }
        System.out.println("Nieparzyste (przez Iterator.remove): " + numbers);

        /*
         * ============================================================
         * ❌ PUŁAPKA: MODYFIKACJA W FOR-EACH
         * ============================================================
         * list.remove() w pętli for-each → ConcurrentModificationException!
         *
         * // BŁĄD:
         * for (String s : names) {
         *     if (s.startsWith("A")) names.remove(s); // ❌ wyjątek!
         * }
         *
         * ROZWIĄZANIE:
         * 1. Użyj Iterator.remove()
         * 2. Użyj removeIf (Java 8+)
         * 3. Zbierz do nowej listy
         */

        ArrayList<String> test = new ArrayList<>();
        test.add("Anna"); test.add("Adam"); test.add("Bartek"); test.add("Alicja");
        test.removeIf(s -> s.startsWith("A")); // ✅ bezpieczne
        System.out.println("Po removeIf(startsWith A): " + test);

        /*
         * ============================================================
         * 🔹 4. LIST ITERATOR
         * ============================================================
         * ListIterator<E> extends Iterator<E> – tylko dla List!
         * Dodatkowe możliwości:
         * - hasPrevious(), previous() → iteracja wstecz
         * - add(e)  → wstawia element podczas iteracji
         * - set(e)  → zastępuje ostatnio zwrócony element
         * - nextIndex(), previousIndex()
         */

        System.out.println("=== ListIterator ===");
        ArrayList<String> words = new ArrayList<>();
        words.add("hello"); words.add("world"); words.add("java");

        ListIterator<String> lit = words.listIterator();
        while (lit.hasNext()) {
            String word = lit.next();
            lit.set(word.toUpperCase()); // zamień na wielkie litery podczas iteracji
        }
        System.out.println("Po toUpperCase: " + words);

        // Iteracja wstecz przez ListIterator
        System.out.print("Wstecz przez ListIterator: ");
        while (lit.hasPrevious()) {
            System.out.print(lit.previous() + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 🔹 5. FOREACH Z LAMBDĄ (Java 8+)
         * ============================================================
         * collection.forEach(element -> ...);
         * Najzwięźlejsza forma, nie daje kontroli nad iteracją.
         */

        System.out.println("=== forEach (lambda) ===");
        names.forEach(name -> System.out.println(">> " + name));

        /*
         * ============================================================
         * 📌 KIEDY KTÓRY SPOSÓB?
         * ============================================================
         * for z indeksem  → gdy potrzeba indeksu lub iteracji wstecz
         * for-each        → najprostszy, gdy indeks nie potrzebny
         * Iterator        → gdy usuwasz elementy podczas iteracji
         * ListIterator    → gdy dodajesz/zmieniasz elementy podczas iteracji
         * forEach(lambda) → funkcyjny styl, czytelne operacje
         */
    }
}
