package com.example.javaquest.liveCodeVersion._01_fundamentals.Lesson14_GarbageCollector;

import java.lang.ref.WeakReference;

public class _Lesson14_GarbageCollector {
    public static void main(String[] args) {

        /*
         * =====================================================================
         * ♻️ GARBAGE COLLECTOR (GC) W JAVIE
         * =====================================================================
         * - GC to automatyczny mechanizm zarządzania pamięcią w JVM
         * - Usuwa obiekty, które nie są już osiągalne (tzn. nie ma do nich referencji)
         * - Zapobiega wyciekom pamięci i odciąża programistę od ręcznego zwalniania pamięci
         *
         * GC ŚLEDZI:
         * - które obiekty są wciąż używane (osiągalne)
         * - które można bezpiecznie usunąć
         * - nie działa od razu po stworzeniu obiektu – uruchamia się wg algorytmów JVM
         */

        Object strongRef = new Object(); // silna referencja – obiekt nie zostanie usunięty

        // WeakReference – słaba referencja, obiekt może zostać usunięty przez GC
        WeakReference<Object> weakRef = new WeakReference<>(new Object());

        System.out.println("Silna referencja: " + strongRef);
        System.out.println("Słaba referencja: " + weakRef.get());

        System.gc(); // sygnalizacja: proszę uruchomić GC (nie gwarantuje wykonania!)

        // Możemy sprawdzić później, czy obiekt pod słabą referencją został już usunięty
        System.out.println("Po GC, słaba referencja: " + weakRef.get());

        /*
         * =====================================================================
         * 📦 TYPY REFERENCJI
         * =====================================================================
         * - Strong Reference (domyślna) – obiekt "żyje", dopóki istnieje referencja
         * - WeakReference – GC może usunąć obiekt w dowolnym momencie
         * - SoftReference – obiekt utrzymywany, dopóki JVM nie potrzebuje pamięci
         * - PhantomReference – do zarządzania cyklem życia obiektów, po ich usunięciu
         */

        /*
         * =====================================================================
         * ⚠️ finalize() – DLACZEGO NIE UŻYWAĆ?
         * =====================================================================
         * - finalize() to metoda, która mogła być nadpisana w klasie i wywoływana
         *   przez GC przed usunięciem obiektu
         * - ale:
         *   ❌ jest nieprzewidywalna (nie wiadomo kiedy się uruchomi)
         *   ❌ obniża wydajność (GC musi sprawdzić finalize)
         *   ❌ może opóźniać usunięcie obiektów
         *   ✅ ZASTOSUJ AutoCloseable i try-with-resources dla zasobów!
         */

        /*
         * =====================================================================
         * 🧠 MITY I FAKTY O GC
         * =====================================================================
         * ❌ "GC usuwa wszystkie nieużywane obiekty od razu" – FAŁSZ
         * ✅ GC działa wg algorytmu (np. G1, ZGC, Serial) i harmonogramu
         * ❌ "System.gc() = natychmiastowe czyszczenie" – FAŁSZ
         * ✅ System.gc() jedynie SUGERUJE uruchomienie zbieracza
         * ❌ "Nie potrzebujemy już nigdy myśleć o pamięci" – FAŁSZ
         * ✅ Nadal musimy unikać silnych referencji cyklicznych i wycieków pamięci
         */

        System.out.println("Lekcja zakończona. GC zadbaj o pamięć, ale używaj świadomie!");
    }
}
