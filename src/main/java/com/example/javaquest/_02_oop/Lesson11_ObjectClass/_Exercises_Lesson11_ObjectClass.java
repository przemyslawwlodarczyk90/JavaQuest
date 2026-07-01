package com.example.javaquest._02_oop.Lesson11_ObjectClass;

import java.util.Objects;

public class _Exercises_Lesson11_ObjectClass {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefaultToString {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Car bez nadpisanego toString().
         * Wypisz obiekt Car przez System.out.println().
         * Zaobserwuj domyślny format: "ClassName@hashCodeHex".
         * Wyjaśnij w komentarzu co oznacza ten format.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_OverrideToString {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Book z polami title, author (String) i year (int).
         * Nadpisz toString() zwracający: "Book{title='X', author='Y', year=Z}".
         * Wypisz obiekt i sprawdź że konkatenacja String + obiekt też wywołuje toString().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_DefaultEquals {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Point bez nadpisanego equals().
         * Stwórz dwa obiekty Point(3,4) i Point(3,4).
         * Wypisz wyniki: == i .equals(). Oba powinny być false.
         * Wyjaśnij w komentarzu dlaczego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_OverrideEquals {
        /*
         * 🧪 Zadanie 4:
         * Nadpisz equals() w klasie Point(x, y).
         * Sprawdź: Point(3,4).equals(Point(3,4)) → true.
         * Sprawdź: null, inny typ, ten sam obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_HashCode {
        /*
         * 🧪 Zadanie 5:
         * Nadpisz hashCode() w klasie Point używając Objects.hash(x, y).
         * Sprawdź że dwa równe obiekty mają ten sam hashCode.
         * Sprawdź że różne obiekty mają (zwykle) różne hashCode.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_GetClass {
        /*
         * 🧪 Zadanie 6:
         * Utwórz obiekty: String, Integer, double[], i własna klasa MyObj.
         * Dla każdego wywołaj: getClass().getName() i getClass().getSimpleName().
         * Sprawdź czy (obj instanceof String) zgadza się z getClass() == String.class.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_NullSafeEquals {
        /*
         * 🧪 Zadanie 7:
         * Przetestuj Objects.equals(a, b) z różnymi kombinacjami null/wartości.
         * Przypadki: null/null, null/"hello", "hello"/null, "hello"/"hello", "a"/"b".
         * Wypisz wyniki i porównaj z bezpośrednim .equals() (które rzuca NPE dla null).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ObjectsHash {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę Person z polami firstName, lastName, age.
         * Nadpisz hashCode() używając Objects.hash(firstName, lastName, age).
         * Porównaj z ręczną implementacją: 31 * (31 * firstName.hashCode() + lastName.hashCode()) + age.
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_RequireNonNull {
        /*
         * 🧪 Zadanie 9:
         * Użyj Objects.requireNonNull(value, "message") do walidacji w konstruktorze.
         * Klasa Product(String name, double price).
         * Konstruktor waliduje: name != null, price > 0.
         * Przetestuj z null name (powinien rzucić NullPointerException z komunikatem).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_ObjectsToString {
        /*
         * 🧪 Zadanie 10:
         * Użyj Objects.toString(obj, defaultValue) dla obiektów mogących być null.
         * String name = null;
         * System.out.println(Objects.toString(name, "Nieznany"));
         * Przetestuj z kilkoma null i non-null obiektami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EqualsContract {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj klasę Rectangle z polami width, height.
         * Nadpisz equals() i udowodnij cały kontrakt:
         * - zwrotność: x.equals(x) → true
         * - symetryczność: x.equals(y) == y.equals(x)
         * - przechodniość: x.equals(y) && y.equals(z) → x.equals(z)
         * - spójność: wiele wywołań → ten sam wynik
         * - x.equals(null) → false
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_HashCodeContract {
        /*
         * 🧪 Zadanie 12:
         * Demonstruj kontrakt equals/hashCode:
         * JEŚLI a.equals(b) to a.hashCode() == b.hashCode().
         * Stwórz klasę Student(id, name, grade).
         * Sprawdź kontrakt dla różnych kombinacji pól w equals/hashCode.
         * Co się stanie jeśli naruszysz kontrakt? (Pokaż złą implementację i jej skutki.)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_Instanceof {
        /*
         * 🧪 Zadanie 13:
         * Hierarchia: Animal → Dog → Poodle.
         * Stwórz obiekt Poodle i sprawdź instanceof dla: Poodle, Dog, Animal, Object, String.
         * Sprawdź też null instanceof Animal.
         * Wyjaśnij wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_PatternMatching {
        /*
         * 🧪 Zadanie 14:
         * Użyj pattern matching instanceof (Java 16+):
         * Object[] data = {42, "hello", 3.14, new int[]{1,2}, true};
         * Dla każdego: jeśli Integer i → i*2, jeśli String s → s.toUpperCase(), ...
         * Wypisz przetworzone wartości.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_DeepEquals {
        /*
         * 🧪 Zadanie 15:
         * Stwórz klasę Order z polami: id (int) i items (String[]).
         * Nadpisz equals() by porównywać też zawartość tablicy (Arrays.equals).
         * Sprawdź że Order({1, ["a","b"]}) equals Order({1, ["a","b"]}) → true.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_HashCollision {
        /*
         * 🧪 Zadanie 16:
         * Stwórz prostą tablicę haszującą (hash table) uproszczoną:
         * - int[] buckets = new int[10]
         * - Metoda put(String key, int value): bucket = key.hashCode() % 10
         * - Metoda get(String key): sprawdź bucket
         * Wstaw 5 par, sprawdź kolizje (gdy dwa klucze mają ten sam bucket).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_EqualsSubclass {
        /*
         * 🧪 Zadanie 17:
         * Problem symetryczności equals() z dziedziczeniem:
         * class Point(x,y) z equals sprawdzającym typ przez getClass().
         * class ColorPoint(x,y,color) extends Point.
         * Sprawdź: point.equals(colorPoint) i colorPoint.equals(point).
         * Wyjaśnij problem i jedno z rozwiązań (getClass() vs instanceof).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ObjectsCompare {
        /*
         * 🧪 Zadanie 18:
         * Użyj Objects.compare(a, b, comparator) do porównania:
         * - Stringów alfabetycznie
         * - Własnych obiektów po różnych kryteriach
         * Stwórz tablicę Employee i posortuj według różnych kryteriów przez compare.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_EqualsSymmetry {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasy CaseInsensitiveString z polem String value.
         * equals() porównuje bez względu na wielkość liter.
         * Sprawdź symetryczność: cis.equals("hello") vs "hello".equals(cis).
         * Wyjaśnij problem i jak go rozwiązać.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Cloneable {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę Matrix implements Cloneable z polem int[][] data.
         * Nadpisz clone() tworząc głęboką kopię macierzy (nie tylko referencji).
         * Udowodnij że zmiana sklonowanej macierzy nie wpływa na oryginał.
         * W komentarzu wyjaśnij dlaczego clone() jest uważane za problematyczne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_HashSet {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj uproszczony HashSet dla Stringów:
         * - int[] buckets (tablice list na kolizje)
         * - add(String), contains(String), size()
         * - Użyj hashCode() % capacity do wyznaczenia bucketu
         * Przetestuj: add 100 elementów, sprawdź contains, zlicz unikalne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_HashMap {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj uproszczoną HashMap<String, Integer>:
         * - tablica par (klucz, wartość) z 16 bucketami
         * - put(String key, int value), get(String key), containsKey(String)
         * - obsłuż kolizje (linked list lub open addressing uproszczone)
         * Wstaw 10 par, pobierz kilka wartości.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Equals {
        /*
         * 🧪 Zadanie 23:
         * Stwórz klasę BigDecimalLike z polem String digits (np. "123.456").
         * Nadpisz equals() tak, by "1.0" equals "1.00" → true (numeryczna równość).
         * hashCode() musi być spójny (np. normalizuj do canonical form przed haszowaniem).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_VisitableObject {
        /*
         * 🧪 Zadanie 24:
         * Wszystkie klasy dziedziczą z Object.
         * Stwórz Visitor który przyjmuje Object i przez instanceof/getClass sprawdza typ.
         * Metoda void visit(Object obj) wypisuje specyficzne informacje dla:
         * String, Integer, int[], boolean, i dowolna klasa.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_EqualsList {
        /*
         * 🧪 Zadanie 25:
         * Stwórz klasę ImmutableList przechowującą String[].
         * Nadpisz equals() by porównywać zawartość list (kolejność ma znaczenie).
         * hashCode() oblicz jako sumę hashCode() elementów.
         * Sprawdź kontrakt na kilku przykładach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ProxyObject {
        /*
         * 🧪 Zadanie 26:
         * Stwórz obiekt Proxy który opakowuje inny obiekt:
         * class LoggingProxy extends Object { Object target; }
         * Nadpisz toString(), equals(), hashCode() delegując do target.
         * equals() powinno być przezroczyste: proxy.equals(original) → true.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_IdentityVsEquality {
        /*
         * 🧪 Zadanie 27:
         * Demonstruj różnicę identity vs equality:
         * Integer a = 127, b = 127 → a == b? (Integer cache)
         * Integer c = 128, d = 128 → c == d? (poza cache)
         * Wyjaśnij Integer cache (-128 do 127).
         * Sprawdź też String interning: "hello" == "hello" vs new String("hello") == "hello".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Comparable {
        /*
         * 🧪 Zadanie 28:
         * Stwórz klasę Version(major, minor, patch) implements Comparable<Version>.
         * compareTo() porównuje: najpierw major, potem minor, potem patch.
         * equals() spójny z compareTo() (equals iff compareTo == 0).
         * Posortuj tablicę wersji i znajdź najnowszą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_WeakReference {
        /*
         * 🧪 Zadanie 29:
         * Użyj java.lang.ref.WeakReference:
         * WeakReference<String> weakRef = new WeakReference<>(new String("dane"));
         * Sprawdź weakRef.get() przed i po sugestii GC (System.gc()).
         * Wyjaśnij kiedy używać WeakReference zamiast silnej referencji.
         * (hint: cache, listeners, canonicalization maps)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_EqualsPerformance {
        /*
         * 🧪 Zadanie 30:
         * Porównaj wydajność różnych implementacji equals():
         * a) bez check this==obj
         * b) z check this==obj (short-circuit)
         * c) z check null (before getClass)
         * d) z getClass() vs instanceof
         * Zmierz 10M porównań równych i nierównych obiektów.
         * Wypisz rekomendację.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
