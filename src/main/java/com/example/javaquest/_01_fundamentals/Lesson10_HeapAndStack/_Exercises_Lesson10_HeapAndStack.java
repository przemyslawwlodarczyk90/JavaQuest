package com.example.javaquest._01_fundamentals.Lesson10_HeapAndStack;

public class _Exercises_Lesson10_HeapAndStack {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_PrimitiveOnStack {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj int x = 10 (typ prymitywny – przechowywany na stosie).
         * Wypisz wartość x.
         * Dodaj komentarz wyjaśniający, że prymitywy są przechowywane bezpośrednio na stosie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_ObjectOnHeap {
        /*
         * 🧪 Zadanie 2:
         * Stwórz obiekt StringBuilder sb = new StringBuilder("Hello").
         * Wypisz sb.
         * Dodaj komentarz: referencja 'sb' jest na stosie, ale obiekt StringBuilder jest na stercie (heap).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PassPrimitiveToMethod {
        /*
         * 🧪 Zadanie 3:
         * Napisz metodę static void doubleValue(int x) { x = x * 2; System.out.println("W metodzie: " + x); }
         * Wywołaj ją z wartością 5.
         * Wypisz wartość oryginału po wywołaniu. Czy się zmieniła?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_PassReferenceToMethod {
        /*
         * 🧪 Zadanie 4:
         * Stwórz tablicę int[] arr = {1, 2, 3}.
         * Napisz metodę static void addTen(int[] a) { a[0] = a[0] + 10; }
         * Wywołaj metodę i wypisz arr[0] po wywołaniu. Czy się zmieniło?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_CopyPrimitive {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj int a = 5, int b = a.
         * Zmień b = 99.
         * Wypisz a i b. Czy a się zmieniło?
         * Wyjaśnij w komentarzu: prymityw kopiuje wartość, nie referencję.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_SharedReference {
        /*
         * 🧪 Zadanie 6:
         * Stwórz int[] arr1 = {1, 2, 3}.
         * Przypisz int[] arr2 = arr1.
         * Zmień arr2[0] = 99.
         * Wypisz arr1[0]. Co się stało?
         * Wyjaśnij: obie zmienne wskazują na ten sam obiekt w heap.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_NullReference {
        /*
         * 🧪 Zadanie 7:
         * Stwórz String s = null.
         * Sprawdź, czy s == null i wypisz komunikat.
         * Spróbuj wywołać s.length() w bloku try-catch i obsłuż NullPointerException.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_GarbageCollectionSuggestion {
        /*
         * 🧪 Zadanie 8:
         * Stwórz obiekt Object obj = new Object() i wypisz go.
         * Następnie ustaw obj = null.
         * Wywołaj System.gc() – to sugestia dla JVM, aby uruchomił garbage collector.
         * Wypisz komunikat "GC zasugerowany".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_StackOverflowDemo {
        /*
         * 🧪 Zadanie 9:
         * Napisz metodę static void recurse(int n) która wywołuje siebie (rekurencja).
         * Wywołaj ją z n=10 (bezpiecznie) i wypisz n w każdym wywołaniu.
         * Dodaj komentarz: nieskończona rekurencja powoduje StackOverflowError.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_MultipleReferences {
        /*
         * 🧪 Zadanie 10:
         * Stwórz StringBuilder sb1 = new StringBuilder("Hello").
         * Przypisz StringBuilder sb2 = sb1.
         * Wywołaj sb2.append(" World").
         * Wypisz sb1 – czy też ma " World"?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_ImmutableStringVsHeap {
        /*
         * 🧪 Zadanie 11:
         * Stwórz String s1 = "Hello" i String s2 = s1.
         * Wywołaj s1 = s1 + " World".
         * Wypisz s1 i s2.
         * Wyjaśnij: String jest immutable, więc s1 + " World" tworzy NOWY obiekt w heap.
         * s2 nadal wskazuje na stary "Hello".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_MethodFrame {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę static int calculate(int a, int b) { int result = a + b; return result; }
         * Wywołaj ją z (3, 4) i wypisz wynik.
         * Dodaj komentarz: każde wywołanie metody tworzy własną ramkę na stosie (stack frame)
         * ze zmiennymi lokalnymi (a, b, result). Po powrocie ramka jest usuwana.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_ModifyObjectInMethod {
        /*
         * 🧪 Zadanie 13:
         * Stwórz klasę wewnętrzną static class Box { int value; Box(int v) { value = v; } }.
         * Napisz metodę static void increment(Box b) { b.value++; }.
         * Stwórz Box box = new Box(10), wywołaj increment(box) i wypisz box.value.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_ReassignReferenceInMethod {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę static void reassign(int[] arr) { arr = new int[]{99, 98, 97}; }
         * Stwórz int[] original = {1, 2, 3}, wywołaj reassign(original).
         * Wypisz original[0] po wywołaniu. Czy się zmieniło?
         * Wyjaśnij: lokalna zmienna arr w metodzie wskazała na nowy obiekt, oryginał się nie zmienił.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_HeapMemoryInfo {
        /*
         * 🧪 Zadanie 15:
         * Wypisz informacje o pamięci JVM:
         * - Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB"
         * - Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB"
         * - Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB"
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_PassByValueConfirmation {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę static void swapPrimitives(int a, int b) { int temp = a; a = b; b = temp; }
         * Wywołaj ją z x=10, y=20.
         * Wypisz x i y po wywołaniu – czy zostały zamienione?
         * Wyjaśnij: Java przekazuje prymitywy przez wartość (kopia), nie przez referencję.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_StringPoolVsHeap {
        /*
         * 🧪 Zadanie 17:
         * Stwórz: String a = "test" (String Pool) i String b = new String("test") (Heap).
         * Wypisz a == b i a.equals(b).
         * Wyjaśnij różnicę między String Pool a zwykłym Heap.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_WrapperCachingVsHeap {
        /*
         * 🧪 Zadanie 18:
         * Stwórz Integer a = 100, b = 100 i Integer c = 200, d = 200.
         * Sprawdź a == b i c == d. Wyjaśnij w komentarzu Integer cache (-128 do 127).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_CreateAndNullifyObjects {
        /*
         * 🧪 Zadanie 19:
         * W pętli stwórz 5 obiektów StringBuilder, wypisz ich zawartość.
         * Po pętli ustaw je wszystkie na null.
         * Wywołaj System.gc().
         * Dodaj komentarz: obiekty bez referencji są kandydatami do GC.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ArrayOfObjects {
        /*
         * 🧪 Zadanie 20:
         * Stwórz tablicę StringBuilder[] builders = new StringBuilder[3].
         * Przypisz nowe obiekty: builders[0] = new StringBuilder("A"), itd.
         * Wypisz każdy obiekt.
         * Wyjaśnij: tablica referencji jest na heapie, każdy StringBuilder też na heapie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_RecursiveStackUsage {
        /*
         * 🧪 Zadanie 21:
         * Napisz rekurencyjną metodę static int factorial(int n).
         * Dodaj println na początku każdego wywołania, żeby zobaczyć głębokość stosu.
         * Wywołaj factorial(5) i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_MemoryLeakSimulation {
        /*
         * 🧪 Zadanie 22:
         * Stwórz tablicę 1000 elementów i wypełnij ją obiektami StringBuilder.
         * Następnie przypisz do połowy elementów null.
         * Sprawdź rozmiar pamięci przed i po (Runtime.getRuntime().freeMemory()).
         * Wywołaj System.gc() i sprawdź pamięć ponownie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_DeepVsShallowCopy {
        /*
         * 🧪 Zadanie 23:
         * Stwórz int[][] matrix = {{1,2},{3,4}}.
         * Skopiuj przez: int[][] shallow = matrix (płytka kopia).
         * Zmień shallow[0][0] = 99.
         * Wypisz matrix[0][0] – czy się zmieniło?
         * Wyjaśnij różnicę między kopią płytką a głęboką w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_StaticVsInstance {
        /*
         * 🧪 Zadanie 24:
         * Stwórz klasę wewnętrzną Counter z polem static int count = 0 i int id.
         * Stwórz 3 obiekty Counter, wypisz ich id i count.
         * Wyjaśnij: pola statyczne są wspólne dla wszystkich obiektów (na heapie w obszarze metadanych).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_NullPointerSafeguard {
        /*
         * 🧪 Zadanie 25:
         * Stwórz metodę static int safeLength(String s).
         * Zwróć 0 jeśli s == null, inaczej zwróć s.length().
         * Przetestuj na: "hello", null, "".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ObjectIdentityVsEquality {
        /*
         * 🧪 Zadanie 26:
         * Stwórz dwa obiekty StringBuilder z taką samą zawartością "abc".
         * Porównaj przez == (identity – czy to ten sam obiekt) i przez .toString().equals() (equality – czy mają taką samą zawartość).
         * Wypisz oba wyniki i wyjaśnij.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_ChainedMethodCallsOnHeap {
        /*
         * 🧪 Zadanie 27:
         * Stwórz StringBuilder sb = new StringBuilder().
         * Zbuduj łańcuch metodami: sb.append("Java").append(" ").append("2025").append("!").
         * Wypisz wynik.
         * Wyjaśnij: każda metoda .append() zwraca this (ten sam obiekt na heapie) – method chaining.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_ArrayVsObjectOnHeap {
        /*
         * 🧪 Zadanie 28:
         * Porównaj:
         * a) int[] arr = new int[1000000] – alokacja 4MB na heapie
         * b) String s = "Hello" – mały obiekt na heapie (lub String Pool)
         * Wypisz freeMemory przed i po każdej alokacji.
         * Ustaw na null i wywołaj gc().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_TraceMethodCallStack {
        /*
         * 🧪 Zadanie 29:
         * Napisz 3 metody: a() wywołuje b(), b() wywołuje c(), c() wypisuje stos wywołań.
         * W metodzie c() użyj: new RuntimeException("Ślad stosu").printStackTrace() – pokaże stos.
         * Wywołaj a() i przeanalizuj wydruk.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_MemoryOptimizationComparison {
        /*
         * 🧪 Zadanie 30:
         * Porównaj zużycie pamięci między:
         * a) Łączenie String w pętli (operator +) 10000 razy
         * b) StringBuilder .append() 10000 razy
         * Zmierz czas (System.currentTimeMillis()) i pamięć (Runtime.getRuntime().freeMemory()) dla obu.
         * Wypisz porównanie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
