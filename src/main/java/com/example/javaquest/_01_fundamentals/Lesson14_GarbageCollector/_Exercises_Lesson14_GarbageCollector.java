package com.example.javaquest._01_fundamentals.Lesson14_GarbageCollector;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class _Exercises_Lesson14_GarbageCollector {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_StrongReference {
        /*
         * 🧪 Zadanie 1:
         * Stwórz silną referencję (strong reference): Object obj = new Object().
         * Wypisz obj.
         * Dodaj komentarz: obiekt nie zostanie usunięty przez GC, dopóki istnieje silna referencja.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_NullifyAndGC {
        /*
         * 🧪 Zadanie 2:
         * Stwórz Object obj = new Object() i wypisz go.
         * Następnie ustaw obj = null.
         * Wywołaj System.gc().
         * Wypisz "Obiekt może być teraz usunięty przez GC".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_GCNotGuaranteed {
        /*
         * 🧪 Zadanie 3:
         * Wywołaj System.gc() i wypisz komunikat.
         * Dodaj komentarz wyjaśniający: System.gc() jest tylko SUGESTIĄ – GC może, ale nie musi uruchomić się.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_WeakReferenceBasic {
        /*
         * 🧪 Zadanie 4:
         * Stwórz WeakReference<String> weakRef = new WeakReference<>(new String("GC Test")).
         * Wypisz weakRef.get() przed wywołaniem GC.
         * Wywołaj System.gc() i wypisz weakRef.get() po GC (może być null).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_StrongVsWeakRef {
        /*
         * 🧪 Zadanie 5:
         * Stwórz silną referencję strongRef = new Object().
         * Stwórz WeakReference<Object> weakRef = new WeakReference<>(strongRef).
         * Ustaw strongRef = null i wywołaj System.gc().
         * Sprawdź, czy weakRef.get() zwraca null.
         * Wypisz wyniki z wyjaśnieniem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_FreeMemoryInfo {
        /*
         * 🧪 Zadanie 6:
         * Wypisz dostępną pamięć przed i po stworzeniu dużej tablicy (int[1000000]).
         * Następnie ustaw tablicę na null i wywołaj System.gc().
         * Wypisz pamięć ponownie.
         * Użyj: Runtime.getRuntime().freeMemory() / 1024 + " KB".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_MultipleObjects {
        /*
         * 🧪 Zadanie 7:
         * Stwórz 5 obiektów StringBuilder w pętli i wypisz każdy.
         * Następnie ustaw je wszystkie na null (lub wyjedź poza zakres).
         * Wywołaj System.gc().
         * Wypisz "Wszystkie obiekty mogą być teraz zebrane przez GC".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ReferenceCounter {
        /*
         * 🧪 Zadanie 8:
         * Stwórz tablicę Object[] refs = new Object[5].
         * Wypełnij ją nowymi obiektami. Wypisz ile referencji istnieje (refs.length).
         * Ustaw refs[0] = null i refs[2] = null.
         * Wypisz, że teraz 3 obiekty mają silne referencje, 2 kwalifikują się do GC.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ScopeAndGC {
        /*
         * 🧪 Zadanie 9:
         * W bloku { } stwórz Object obj = new Object() i wypisz go.
         * Poza blokiem wypisz komunikat "obj jest poza zasięgiem – kwalifikuje się do GC".
         * Dodaj komentarz: Java automatycznie zwalnia lokalne zmienne po opuszczeniu zakresu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_CheckWeakRefAfterGC {
        /*
         * 🧪 Zadanie 10:
         * Stwórz WeakReference<String> ref = new WeakReference<>(new String("słabe")).
         * Sprawdź ref.get() != null przed GC.
         * Wywołaj System.gc() i sprawdź ref.get() po GC.
         * Wypisz odpowiedni komunikat w obu przypadkach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_SoftReferenceBasic {
        /*
         * 🧪 Zadanie 11:
         * Stwórz SoftReference<byte[]> softRef = new SoftReference<>(new byte[1024]).
         * Wypisz czy softRef.get() != null.
         * Dodaj komentarz: SoftReference jest zbierana tylko gdy JVM potrzebuje pamięci
         * (w przeciwieństwie do WeakReference, która jest zbierana przy każdym GC).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_GCStatsBefore {
        /*
         * 🧪 Zadanie 12:
         * Pobierz i wypisz statystyki pamięci przed dużą alokacją:
         * - Runtime.getRuntime().totalMemory()
         * - Runtime.getRuntime().freeMemory()
         * - Runtime.getRuntime().maxMemory()
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_AllocateAndFreeMemory {
        /*
         * 🧪 Zadanie 13:
         * Wypisz freeMemory(). Stwórz int[] arr = new int[500000].
         * Wypisz freeMemory() (powinno być mniej). Ustaw arr = null, wywołaj gc().
         * Wypisz freeMemory() ponownie. Zaobserwuj zmianę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_FinalizeMockup {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę wewnętrzną z przesłoniętą metodą finalize() (stara, zdeprecjonowana):
         * @Override protected void finalize() { System.out.println("GC: obiekt usunięty!"); }
         * Stwórz obiekt, ustaw null i wywołaj gc().
         * Dodaj komentarz: finalize() jest zdeprecjonowana – preferuj try-with-resources.
         */
        public static void main(String[] args) throws InterruptedException {
            // TODO: twój kod tutaj
        }
        static class CleanupDemo {
            @SuppressWarnings("deprecation")
            @Override
            protected void finalize() {
                System.out.println("GC: obiekt CleanupDemo usunięty!");
            }
        }
    }

    static class Exercise15_AutoCloseableVsFinalize {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj klasę wewnętrzną Resource implements AutoCloseable:
         * W close() wypisz "Zasób zamknięty".
         * Użyj try-with-resources: try (Resource r = new Resource()) { ... }
         * Dodaj komentarz: to preferowane podejście zamiast finalize().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
        static class Resource implements AutoCloseable {
            @Override
            public void close() {
                System.out.println("Zasób zamknięty");
            }
        }
    }

    static class Exercise16_CircularReferenceExplained {
        /*
         * 🧪 Zadanie 16:
         * Stwórz dwa obiekty A i B, gdzie A zawiera referencję do B i odwrotnie.
         * Ustaw oba na null i wywołaj gc().
         * Wypisz: "Nowoczesny GC radzi sobie z referencjami cyklicznymi".
         * Dodaj komentarz: Java GC używa algorytmu śledzenia (mark-and-sweep), nie licznika referencji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
        static class NodeA { NodeB partner; }
        static class NodeB { NodeA partner; }
    }

    static class Exercise17_LargeObjectGC {
        /*
         * 🧪 Zadanie 17:
         * W pętli 10 razy:
         * - stwórz byte[] big = new byte[10 * 1024 * 1024] (10MB)
         * - wypisz numer iteracji i freeMemory
         * - ustaw big = null
         * Obserwuj, jak freeMemory zmienia się między iteracjami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_WeakReferenceCache {
        /*
         * 🧪 Zadanie 18:
         * Stwórz tablicę WeakReference<String>[] cache = new WeakReference[5].
         * Wypełnij ją obiektami String (przez new String("wartość N")).
         * Wywołaj System.gc() i sprawdź, ile referencji jest jeszcze żywych (get() != null).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_GCMythBuster {
        /*
         * 🧪 Zadanie 19:
         * Wypisz trzy mity o GC i ich wyjaśnienia:
         * 1. Mit: "GC usuwa obiekty od razu po utracie referencji" → Fakt: działa wg algorytmu
         * 2. Mit: "System.gc() = natychmiastowe czyszczenie" → Fakt: tylko sugestia
         * 3. Mit: "Nie musimy nigdy myśleć o pamięci" → Fakt: wciąż możliwe wycieki
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_TotalMemoryCheck {
        /*
         * 🧪 Zadanie 20:
         * Wypisz informacje o pamięci JVM w czytelnym formacie:
         * "Pamięć JVM: max=X MB, całkowita=Y MB, wolna=Z MB, używana=W MB"
         * gdzie używana = całkowita - wolna.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_ManualCacheClear {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj cache: String[] cache = new String[100].
         * Wypełnij go wartościami "wartość_N".
         * Wypisz rozmiar i freeMemory. Wyczyść cache (ustaw null).
         * Wywołaj gc() i sprawdź freeMemory ponownie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_WeakRefGetNotNull {
        /*
         * 🧪 Zadanie 22:
         * Stwórz zmienną: String strong = new String("utrzymane").
         * Stwórz WeakReference<String> weak = new WeakReference<>(strong).
         * Nie zwalniaj strong. Wywołaj System.gc().
         * Sprawdź weak.get() – powinno nadal zwracać wartość (bo strong trzyma referencję).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_MemoryAfterStringConcat {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj String w pętli 1000 razy używając + (tworzy dużo tymczasowych obiektów).
         * Zmierz czas i freeMemory przed i po.
         * Zrób to samo ze StringBuilder.
         * Porównaj wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_GCAlgorithmInfo {
        /*
         * 🧪 Zadanie 24:
         * Wypisz dostępne właściwości systemowe związane z GC:
         * System.getProperty("java.version") i java.vm.name.
         * Dodaj komentarz opisujący różne algorytmy GC w JVM:
         * Serial GC, Parallel GC, G1 GC (domyślny), ZGC, Shenandoah.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_ReferenceTypes {
        /*
         * 🧪 Zadanie 25:
         * Podsumuj typy referencji kodem:
         * 1. Silna (zwykła): Object strong = new Object()
         * 2. Słaba: WeakReference<Object> weak = new WeakReference<>(new Object())
         * 3. Miękka: SoftReference<Object> soft = new SoftReference<>(new Object())
         * Wypisz stan każdej przed i po System.gc().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_AutoCloseableResource {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj klasę FileSimulator implements AutoCloseable z:
         * - konstruktorem drukującym "Otworzono zasób"
         * - close() drukującym "Zamknięto zasób"
         * Użyj try-with-resources i sprawdź, że close() jest wywołane automatycznie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
        static class FileSimulator implements AutoCloseable {
            FileSimulator() { System.out.println("Otworzono zasób"); }
            @Override public void close() { System.out.println("Zamknięto zasób"); }
        }
    }

    static class Exercise27_OutOfMemoryPrevention {
        /*
         * 🧪 Zadanie 27:
         * Zademonstruj, jak unikać OutOfMemoryError:
         * Stwórz pętlę, która alokuje i zwalnia pamięć po każdej iteracji (ustaw null + gc).
         * Porównaj z pętlą, która kumuluje obiekty (komentarz: to powoduje OOME).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_StringPoolAndGC {
        /*
         * 🧪 Zadanie 28:
         * Stwórz 3 Stringi przez literał i 3 przez new String().
         * Wyjaśnij w komentarzu: String Pool (literały) jest zarządzany inaczej niż zwykły heap.
         * Obiekty z new String() kwalifikują się do GC jak każdy inny obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_GCMonitor {
        /*
         * 🧪 Zadanie 29:
         * Wypisz "raport GC":
         * - aktualna pamięć (totalMemory, freeMemory, maxMemory)
         * - liczba procesorów
         * - wersja JVM
         * Wywołaj System.gc() i wypisz ponownie.
         * Sprawdź, czy wolna pamięć wzrosła.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_MemoryLeakSimulation {
        /*
         * 🧪 Zadanie 30:
         * Zasymuluj wyciek pamięci:
         * Stwórz statyczną listę (java.util.ArrayList) i w pętli dodawaj do niej duże tablice.
         * Wypisz freeMemory co 10 iteracji. Zaobserwuj spadek.
         * Następnie wyczyść listę (.clear()) i wywołaj gc(). Wypisz freeMemory.
         * Wyjaśnij w komentarzu: statyczne kolekcje z rosnącymi danymi to częste źródło wycieków.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
