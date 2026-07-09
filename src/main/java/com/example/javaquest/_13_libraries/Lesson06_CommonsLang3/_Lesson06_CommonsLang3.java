package com.example.javaquest._13_libraries.Lesson06_CommonsLang3;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class _Lesson06_CommonsLang3 {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: APACHE COMMONS LANG3 ===");

        /*
         * ============================================================
         * 📦 CZYM JEST COMMONS-LANG3
         * ============================================================
         * - `org.apache.commons:commons-lang3` to biblioteka Apache Commons,
         *   którą można traktować jak "rozszerzenie" pakietu `java.lang`
         *   (stąd nazwa "lang3" - trzecia, aktualna linia rozwojowa API,
         *   niezgodna binarnie ze starym `commons-lang` 2.x).
         * - JDK celowo trzyma klasy typu String/Object minimalne - Commons
         *   Lang dokłada setki metod pomocniczych, których w praktyce
         *   brakuje: null-safe operacje na String, porównania obiektów,
         *   walidację argumentów, generowanie losowych danych, operacje
         *   na tablicach.
         * - Zależność jest już dodana w `pom.xml` tego projektu - w
         *   realnym projekcie Maven wystarczy dodać `commons-lang3` jako
         *   `<dependency>` (bez podawania wersji, jeśli - jak tutaj -
         *   projekt dziedziczy z `spring-boot-starter-parent`, który
         *   zarządza wersją przez swoje BOM).
         * - Najważniejsza cecha WSZYSTKICH metod poniżej: są STATYCZNE i
         *   NULL-SAFE - w przeciwieństwie do metod instancyjnych na
         *   `String`, nie rzucają `NullPointerException`, gdy dostaną
         *   `null` jako argument. To jest główny powód, dla którego
         *   ludzie sięgają po Commons Lang zamiast pisać własne "if (x
         *   == null)" w każdym miejscu kodu.
         */
        System.out.println("commons-lang3 = zestaw null-safe metod pomocniczych rozszerzających java.lang.");

        /*
         * ============================================================
         * 🔹 StringUtils - NULL-SAFE OPERACJE NA STRING
         * ============================================================
         * - `String.isEmpty()` (od zawsze) i `String.isBlank()` (od Javy
         *   11) rzucają NPE, gdy wywołasz je na zmiennej `null` - bo są
         *   metodami INSTANCYJNYMI. `StringUtils.isEmpty(str)` i
         *   `StringUtils.isBlank(str)` są metodami STATYCZNYMI, które
         *   przyjmują `null` jako zwykły argument i zwracają `true` -
         *   to jest KLUCZOWA różnica i główny powód, dla którego
         *   `StringUtils` wciąż ma sens nawet po dodaniu `isBlank()` do
         *   JDK 11: nie trzeba pisać `str != null && str.isBlank()`.
         * - `isEmpty` sprawdza tylko długość ("" jest puste), `isBlank`
         *   dodatkowo traktuje same białe znaki ("   ") jako puste.
         * - `trim()`/`strip()` na `null` w JDK rzucają NPE - odpowiedniki
         *   `StringUtils.trim(null)` i `StringUtils.strip(null)` zwracają
         *   `null` bez wyjątku.
         */
        System.out.println("\n=== StringUtils: isBlank/isEmpty na null ===");
        String nullString = null;
        System.out.println("StringUtils.isEmpty(null) = " + StringUtils.isEmpty(nullString));
        System.out.println("StringUtils.isBlank(\"   \") = " + StringUtils.isBlank("   "));
        System.out.println("StringUtils.isBlank(\"tekst\") = " + StringUtils.isBlank("tekst"));
        // String.isBlank() od Javy 11 działa TYLKO gdy zmienna nie jest null:
        System.out.println("\"   \".isBlank() (JDK, od Javy 11) = " + "   ".isBlank());
        System.out.println("StringUtils.trim(null) = " + StringUtils.trim(nullString) + " (bez NPE!)");

        System.out.println("\n=== StringUtils: pozostałe przydatne metody ===");
        System.out.println("capitalize(\"java\") = " + StringUtils.capitalize("java"));
        System.out.println("abbreviate(\"Programowanie w Javie\", 12) = " + StringUtils.abbreviate("Programowanie w Javie", 12));
        System.out.println("join(new String[]{\"a\", \"b\", \"c\"}, \", \") = " + StringUtils.join(new String[]{"a", "b", "c"}, ", "));
        System.out.println("repeat(\"ab\", 3) = " + StringUtils.repeat("ab", 3));
        System.out.println("reverse(\"kolekcje\") = " + StringUtils.reverse("kolekcje"));
        System.out.println("substringBetween(\"<b>tekst</b>\", \"<b>\", \"</b>\") = "
                + StringUtils.substringBetween("<b>tekst</b>", "<b>", "</b>"));

        /*
         * ============================================================
         * 🔹 ObjectUtils - BEZPIECZNA PRACA Z OBIEKTAMI MOGĄCYMI BYĆ NULL
         * ============================================================
         * - `defaultIfNull(obj, default)` - odpowiednik wzorca "wartość
         *   domyślna" bez ręcznego `obj != null ? obj : default`
         *   (w JDK od Javy 9 jest podobny `Objects.requireNonNullElse`,
         *   ale zachowuje się nieco inaczej przy `default == null`).
         * - `firstNonNull(values...)` - zwraca pierwszy argument różny
         *   od `null` z całej listy - wygodne przy łańcuchu wartości
         *   zapasowych (np. konfiguracja: parametr -> zmienna
         *   środowiskowa -> wartość domyślna).
         */
        System.out.println("\n=== ObjectUtils ===");
        String userProvidedName = null;
        System.out.println("defaultIfNull(null, \"Gość\") = " + ObjectUtils.defaultIfNull(userProvidedName, "Gość"));
        System.out.println("firstNonNull(null, null, \"trzecia wartość\") = "
                + ObjectUtils.firstNonNull(null, null, "trzecia wartość"));

        /*
         * ============================================================
         * 🔹 Validate - WALIDACJA ARGUMENTÓW Z CZYTELNYMI WYJĄTKAMI
         * ============================================================
         * - Zamiast ręcznego `if (x == null) throw new
         *   IllegalArgumentException("...")` w każdej metodzie, `Validate`
         *   robi to w jednej linii i pozwala przekazać SFORMATOWANY
         *   komunikat (jak `String.format`).
         * - `Validate.notNull(obj, message)` rzuca `NullPointerException`
         *   z czytelnym komunikatem, jeśli `obj == null`.
         * - `Validate.isTrue(warunek, message)` rzuca
         *   `IllegalArgumentException`, jeśli warunek jest `false` -
         *   idealne do walidacji zakresów, np. wieku, kwoty.
         */
        System.out.println("\n=== Validate ===");
        try {
            Validate.notNull(null, "Parametr '%s' nie może być null", "klientId");
        } catch (NullPointerException e) {
            System.out.println("Złapano NPE z Validate.notNull: " + e.getMessage());
        }
        try {
            int wiek = -5;
            Validate.isTrue(wiek >= 0, "Wiek nie może być ujemny, otrzymano: %d", wiek);
        } catch (IllegalArgumentException e) {
            System.out.println("Złapano IllegalArgumentException z Validate.isTrue: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 RandomStringUtils / RandomUtils - LOSOWE DANE TESTOWE
         * ============================================================
         * - Przydatne przy generowaniu danych do testów, przykładowych
         *   haseł tymczasowych, identyfikatorów sesji itp. - bez ręcznego
         *   pisania pętli z `java.util.Random` i tablicą znaków.
         * - `RandomStringUtils.randomAlphabetic(n)` - n losowych liter,
         *   `randomNumeric(n)` - n losowych cyfr, `randomAlphanumeric(n)`
         *   - litery i cyfry.
         * - `RandomUtils.nextInt(start, end)` - losowa liczba całkowita z
         *   przedziału `[start, end)`.
         * - UWAGA: te generatory NIE są kryptograficznie bezpieczne
         *   (podobnie jak zwykły `java.util.Random`) - do haseł/tokenów
         *   produkcyjnych używaj `java.security.SecureRandom`, tu chodzi
         *   wyłącznie o szybkie dane TESTOWE.
         */
        System.out.println("\n=== RandomStringUtils / RandomUtils (dane testowe) ===");
        System.out.println("randomAlphabetic(8) = " + RandomStringUtils.randomAlphabetic(8));
        System.out.println("randomNumeric(6) = " + RandomStringUtils.randomNumeric(6));
        System.out.println("randomAlphanumeric(10) = " + RandomStringUtils.randomAlphanumeric(10));
        System.out.println("RandomUtils.nextInt(1, 100) = " + RandomUtils.nextInt(1, 100));

        /*
         * ============================================================
         * 🔹 ArrayUtils - OPERACJE NA TABLICACH, KTÓRYCH BRAKUJE W JDK
         * ============================================================
         * - Tablice w Javie nie mają metod takich jak `List` (brak
         *   `contains`, `indexOf`, `add`, `remove`) - trzeba by pisać
         *   własną pętlę albo konwertować do `List` przez `Arrays.asList`.
         * - `ArrayUtils` dostarcza te operacje bezpośrednio na tablicach,
         *   bez konwersji: `contains`, `indexOf`, `add` (zwraca NOWĄ,
         *   większą tablicę - tablice w Javie mają stały rozmiar),
         *   `remove` (zwraca NOWĄ, mniejszą tablicę).
         */
        System.out.println("\n=== ArrayUtils ===");
        int[] numbers = {10, 20, 30};
        System.out.println("tablica: " + ArrayUtils.toString(numbers));
        System.out.println("contains(numbers, 20) = " + ArrayUtils.contains(numbers, 20));
        System.out.println("indexOf(numbers, 30) = " + ArrayUtils.indexOf(numbers, 30));
        int[] withAdded = ArrayUtils.add(numbers, 40);
        System.out.println("add(numbers, 40) = " + ArrayUtils.toString(withAdded) + " (nowa tablica, dłuższa o 1)");
        int[] withRemoved = ArrayUtils.remove(withAdded, 0);
        System.out.println("remove(withAdded, indeks 0) = " + ArrayUtils.toString(withRemoved) + " (nowa tablica, krótsza o 1)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE: KIEDY NADAL WARTO SIĘGAĆ PO COMMONS LANG3
         * ============================================================
         * - Od Javy 11 mamy `String.isBlank()`/`.strip()`, od Javy 9
         *   `Objects.requireNonNullElse`, więc CZĘŚĆ funkcji Commons Lang
         *   ma dziś odpowiedniki w JDK - ale te odpowiedniki są
         *   INSTANCYJNE i wymagają, żeby zmienna NIE była `null`.
         * - `StringUtils`/`ObjectUtils` wygrywają, gdy pracujesz z
         *   wartościami, które MOGĄ być `null` (dane z formularza, z
         *   bazy, z zewnętrznego API) - jedna metoda zamiast `if (x !=
         *   null && ...)` w każdym miejscu.
         * - `Validate`, generatory losowych danych i `ArrayUtils` NIE
         *   mają prostych odpowiedników w JDK - to funkcje, których
         *   Commons Lang po prostu DOKŁADA.
         * - W dużych, starszych bazach kodu (wiele firm, w tym branża
         *   Ant/Maven z rozdziału `_11_buildtools`) `commons-lang3` jest
         *   praktycznie wszechobecny - warto znać jego API, nawet jeśli
         *   w nowym kodzie część rzeczy da się zrobić czystym JDK.
         */
        System.out.println("\n=== PODSUMOWANIE ===");
        System.out.println("StringUtils/ObjectUtils - gdy wartosc MOZE byc null. Validate/RandomStringUtils/ArrayUtils - brak odpowiednika w JDK.");

        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }
}
