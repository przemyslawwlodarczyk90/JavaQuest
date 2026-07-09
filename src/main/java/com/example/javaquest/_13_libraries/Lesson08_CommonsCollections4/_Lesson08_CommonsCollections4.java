package com.example.javaquest._13_libraries.Lesson08_CommonsCollections4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class _Lesson08_CommonsCollections4 {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: APACHE COMMONS COLLECTIONS4 ===");

        /*
         * ============================================================
         * 📦 CZYM JEST COMMONS-COLLECTIONS4
         * ============================================================
         * - `org.apache.commons:commons-collections4` (pakiet bazowy
         *   `org.apache.commons.collections4`) to trzecia, aktualna linia
         *   rozwojowa biblioteki Apache Commons Collections - "4" w
         *   nazwie odnosi sie do wersji generycznej/nowoczesnej API
         *   (starsze `commons-collections` 3.x nie mialo generykow).
         * - To NIE jest zamiennik `java.util` - to zestaw NARZEDZI i
         *   DODATKOWYCH STRUKTUR DANYCH, ktorych `java.util` nie ma wcale
         *   (Bag, MultiValuedMap) oraz null-safe/wygodnych metod
         *   statycznych operujacych na zwyklych kolekcjach z `java.util`
         *   (CollectionUtils, MapUtils, ListUtils).
         * - Ta lekcja jest trzecia (i ostatnia) z mini-grupy "Apache
         *   Commons" (Lekcja 6 - commons-lang3, Lekcja 7 - commons-io) -
         *   tu skupiamy sie na KOLEKCJACH. Nastepne dwie lekcje (9 i 10)
         *   pokazuja analogiczny temat od strony biblioteki Guava - na
         *   koncu tej lekcji krotkie porownanie, kiedy siegac po ktora.
         * - Zaleznosc jest juz dodana w `pom.xml` tego projektu
         *   (`commons-collections4:4.4`).
         */
        System.out.println("commons-collections4 = narzedzia i dodatkowe struktury danych rozszerzajace java.util.");

        /*
         * ============================================================
         * 🔹 CollectionUtils - NULL-SAFE SPRAWDZENIA I OPERACJE MNOGOSCIOWE
         * ============================================================
         * - `collection.isEmpty()` rzuca NullPointerException, gdy
         *   zmienna `collection` jest `null` - to metoda INSTANCYJNA.
         *   `CollectionUtils.isEmpty(collection)` i `isNotEmpty(...)` sa
         *   STATYCZNE i null-safe - `null` traktowane jest jak PUSTA
         *   kolekcja, bez wyjatku.
         * - `union`/`intersection`/`subtract`/`disjunction` licza
         *   operacje mnogosciowe na DOWOLNYCH kolekcjach (nie tylko
         *   Set) z uwzglednieniem KROTNOSCI elementow (ile razy dany
         *   element wystepuje) - wewnetrznie oparte o `Bag` (patrz
         *   nizej). To jest wazna roznica wzgledem prostego przeciecia
         *   zbiorow: jesli element wystepuje 2 razy w jednej kolekcji i
         *   3 razy w drugiej, `intersection` zwroci go 2 razy (mniejsza
         *   z krotnosci), a nie tylko raz.
         */
        demonstrateCollectionUtils();

        /*
         * ============================================================
         * 🔹 MapUtils - NULL-SAFE SPRAWDZENIA I GETTERY NA MAPACH
         * ============================================================
         * - Analogicznie do `CollectionUtils`: `MapUtils.isEmpty(map)` i
         *   `isNotEmpty(map)` NIE rzucaja wyjatku dla `null`.
         * - `MapUtils.getString(map, klucz, wartoscDomyslna)`,
         *   `getInteger(...)`, `getIntValue(...)` - null-safe gettery,
         *   ktore zwracaja WARTOSC DOMYSLNA zarowno gdy mapa jest `null`,
         *   jak i gdy klucz w niej nie istnieje - bez recznego
         *   `map != null && map.containsKey(klucz) ? ... : domyslna`.
         */
        demonstrateMapUtils();

        /*
         * ============================================================
         * 🔹 ListUtils - OPERACJE NA LISTACH Z ZACHOWANIEM KOLEJNOSCI
         * ============================================================
         * - `ListUtils.union`/`intersection`/`subtract` dzialaja
         *   podobnie do `CollectionUtils`, ale operuja konkretnie na
         *   `List` i ZACHOWUJA KOLEJNOSC elementow z listy wejsciowej -
         *   wynik jest przewidywalny i deterministyczny.
         * - `ListUtils.partition(lista, rozmiar)` dzieli liste na
         *   kolejne podlisty o zadanym rozmiarze (ostatnia moze byc
         *   krotsza) - dokladny odpowiednik `Lists.partition` z Guavy
         *   (Lekcja 9), przydatny np. do przetwarzania danych w
         *   paczkach (batch processing, zapytania do bazy w porcjach).
         */
        demonstrateListUtils();

        /*
         * ============================================================
         * 🔹 Bag / HashBag - KOLEKCJA ZLICZAJACA WYSTAPIENIA (MULTISET)
         * ============================================================
         * - `Bag<E>` to "worek" - kolekcja, ktora pamieta, ILE razy dany
         *   element zostal do niej dodany. W `java.util` nie ma
         *   odpowiednika - trzeba by recznie prowadzic
         *   `Map<E, Integer>` zliczajaca wystapienia.
         * - `HashBag` to podstawowa implementacja oparta o hashowanie
         *   (bez gwarancji kolejnosci elementow).
         * - `add(element)` dodaje JEDNO wystapienie, `add(element, n)`
         *   dodaje `n` wystapien naraz. `getCount(element)` zwraca
         *   liczbe wystapien. `uniqueSet()` zwraca zbior UNIKALNYCH
         *   elementow. `size()` zwraca CALKOWITA liczbe elementow
         *   WLICZAJAC powtorzenia (bo `Bag` implementuje `Collection`).
         * - PORE Z GUAVA: to DOKLADNIE ten sam koncept co `Multiset` z
         *   biblioteki Guava (Lekcja 10, `HashMultiset`) - `getCount`
         *   odpowiada `count`, `uniqueSet` odpowiada `elementSet`. Roznia
         *   sie tylko nazwami metod i biblioteka pochodzenia - jesli w
         *   projekcie jest juz Guava, sensowniej uzyc `Multiset` (ma
         *   bogatsze API, np. gotowe `Multisets.copyHighestCountFirst`);
         *   jesli projekt ma juz `commons-collections4` (a nie ma Guavy),
         *   `Bag`/`HashBag` robi dokladnie to samo bez dokladania nowej
         *   zaleznosci.
         */
        demonstrateBag();

        /*
         * ============================================================
         * 🔹 MultiValuedMap / ArrayListValuedHashMap - JEDEN KLUCZ, WIELE WARTOSCI
         * ============================================================
         * - `MultiValuedMap<K, V>` to logicznie `Map<K, Collection<V>>`,
         *   ale bez recznego zarzadzania wewnetrzna kolekcja dla kazdego
         *   klucza (bez recznego `computeIfAbsent(klucz, k -> new
         *   ArrayList<>()).add(wartosc)`).
         * - `ArrayListValuedHashMap` - wartosci pod kluczem trzymane
         *   jako LISTA (dopuszcza duplikaty, zachowuje kolejnosc
         *   wstawiania). Istnieje tez `HashSetValuedHashMap` - wartosci
         *   jako ZBIOR (bez duplikatow).
         * - `get(klucz)` zwraca WIDOK kolekcji wartosci pod danym
         *   kluczem - dla nieistniejacego klucza PUSTA kolekcje, NIGDY
         *   `null`.
         * - PORE Z GUAVA: to DOKLADNIE ten sam koncept co `Multimap` z
         *   Guavy (Lekcja 10, `ArrayListMultimap`/`HashMultimap`) - taka
         *   sama semantyka, inna nazwa biblioteki. Wybor miedzy nimi w
         *   praktyce zalezy od tego, ktora biblioteka jest juz obecna w
         *   projekcie, a nie od realnej roznicy mozliwosci.
         */
        demonstrateMultiValuedMap();

        /*
         * ============================================================
         * 🔹 Predicate / Transformer - INTERFEJSY FUNKCYJNE SPRZED JAVY 8
         * ============================================================
         * - `org.apache.commons.collections4.Predicate<T>` (metoda
         *   `boolean evaluate(T object)`) i
         *   `org.apache.commons.collections4.Transformer<I, O>` (metoda
         *   `O transform(I input)`) to WLASNE interfejsy funkcyjne tej
         *   biblioteki - powstaly LATA przed `java.util.function`
         *   (Java 8, 2014), kiedy Java nie miala jeszcze lambd ani
         *   Streamow.
         * - `CollectionUtils.select(kolekcja, predicate)` filtruje
         *   kolekcje (zwraca NOWA kolekcje z elementami spelniajacymi
         *   warunek, oryginal bez zmian). `CollectionUtils.collect(
         *   kolekcja, transformer)` mapuje kazdy element na nowa
         *   wartosc (odpowiednik `Stream.map`).
         * - UCZCIWA UWAGA: we WSPOLCZESNYM kodzie (Java 8+) prawie
         *   zawsze lepiej uzyc `java.util.function.Predicate`/
         *   `Function` razem ze Streamami (`_03_collections`, lekcje o
         *   Streams) - sa standardem jezyka, dzialaja z kazda biblioteka
         *   i nie wymagaja dodatkowej zaleznosci. `Predicate`/
         *   `Transformer` z `commons-collections4` warto ROZPOZNAWAC,
         *   bo wciaz wystepuja w starszym, legacy kodzie (projekty
         *   sprzed Javy 8 lub pisane pod starsze API tej biblioteki) -
         *   ale NIE sa to pierwszy wybor w nowym kodzie.
         */
        demonstratePredicateAndTransformer();

        /*
         * ============================================================
         * 🔍 KIEDY commons-collections4, KIEDY GUAVA, KIEDY SAME STREAMY
         * ============================================================
         * - Same Streamy (`java.util.stream`, JDK, bez zadnej
         *   zaleznosci) - domyslny wybor dla PROSTYCH operacji:
         *   filtrowania, mapowania, sortowania, zbierania do listy/mapy.
         *   Zawsze dostepne, zero dodatkowego jara.
         * - `commons-collections4` - gdy potrzebna jest gotowa struktura
         *   danych, ktorej JDK nie ma (`Bag`, `MultiValuedMap`), a
         *   projekt juz i tak ciagnie za soba inne biblioteki Apache
         *   Commons (np. `commons-lang3`, `commons-io` z lekcji 6-7) -
         *   spojnosc z reszta kodu.
         * - Guava - gdy potrzebne sa te same struktury (Multiset,
         *   Multimap), ale zalezy Ci na bogatszym, bardziej
         *   dopracowanym API (np. `BiMap`, `Table`, `RangeSet`, ktorych
         *   `commons-collections4` NIE MA), lub projekt juz uzywa Guavy
         *   do czegos innego (np. `LoadingCache` z Lekcji 11).
         * - W praktyce: nie dodawaj OBU bibliotek "na wszelki wypadek" -
         *   wybierz jedna zgodnie z tym, co juz jest w projekcie, i
         *   trzymaj sie jej dla nowego kodu.
         */
        System.out.println("\n=== Wybor biblioteki: Streamy (proste operacje) > jedna z (commons-collections4, Guava) dla struktur, ktorych brakuje w JDK ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CollectionUtils/MapUtils/ListUtils - null-safe sprawdzenia
         *   (isEmpty/isNotEmpty) i operacje mnogosciowe
         *   (union/intersection/subtract/disjunction/partition) na
         *   zwyklych kolekcjach z java.util.
         * - Bag/HashBag - "worek" zliczajacy wystapienia elementu,
         *   odpowiednik Multiset z Guavy.
         * - MultiValuedMap/ArrayListValuedHashMap - jeden klucz, wiele
         *   wartosci, odpowiednik Multimap z Guavy.
         * - Predicate/Transformer - legacy interfejsy funkcyjne sprzed
         *   Javy 8, wciaz spotykane w starszym kodzie, ale w nowym
         *   kodzie preferuj java.util.function + Streamy.
         * - Kolejne lekcje wracaja do Guavy (Preconditions, narzedzia
         *   do stringow, Ordering, Range).
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateCollectionUtils() {
        System.out.println("\n=== CollectionUtils: null-safe sprawdzenia i operacje mnogosciowe ===");

        List<String> pustaLista = null;
        System.out.println("CollectionUtils.isEmpty(null) = " + CollectionUtils.isEmpty(pustaLista) + " (bez NullPointerException)");
        System.out.println("CollectionUtils.isNotEmpty(null) = " + CollectionUtils.isNotEmpty(pustaLista));

        List<String> zespolA = Arrays.asList("Ala", "Bartek", "Celina");
        List<String> zespolB = Arrays.asList("Bartek", "Celina", "Damian");

        Collection<String> suma = CollectionUtils.union(zespolA, zespolB);
        Collection<String> czescWspolna = CollectionUtils.intersection(zespolA, zespolB);
        Collection<String> tylkoWZespoleA = CollectionUtils.subtract(zespolA, zespolB);
        Collection<String> tylkoWJednym = CollectionUtils.disjunction(zespolA, zespolB);

        System.out.println("Zespol A: " + zespolA);
        System.out.println("Zespol B: " + zespolB);
        System.out.println("union (suma, bez duplikatow po krotnosci): " + suma);
        System.out.println("intersection (czesc wspolna): " + czescWspolna);
        System.out.println("subtract (tylko w A, bez elementow z B): " + tylkoWJednym.isEmpty() ? "-" : tylkoWZespoleA.toString());
        System.out.println("disjunction (w A lub w B, ale NIE w obu): " + tylkoWJednym);
    }

    private static void demonstrateMapUtils() {
        System.out.println("\n=== MapUtils: null-safe sprawdzenia i gettery ===");

        Map<String, String> ustawienia = null;
        System.out.println("MapUtils.isEmpty(null) = " + MapUtils.isEmpty(ustawienia) + " (bez NullPointerException)");

        Map<String, String> konfiguracja = new HashMap<>();
        konfiguracja.put("timeout", "30");
        konfiguracja.put("host", "localhost");

        String host = MapUtils.getString(konfiguracja, "host", "brak-hosta");
        String brakujacyKlucz = MapUtils.getString(konfiguracja, "port", "8080-domyslny");
        Integer timeout = MapUtils.getInteger(konfiguracja, "timeout", 60);

        System.out.println("MapUtils.getString(konfiguracja, \"host\", ...) = " + host);
        System.out.println("MapUtils.getString(konfiguracja, \"port\" - klucza NIE MA, ...) = " + brakujacyKlucz);
        System.out.println("MapUtils.getInteger(konfiguracja, \"timeout\", ...) = " + timeout);
    }

    private static void demonstrateListUtils() {
        System.out.println("\n=== ListUtils: union/intersection/subtract z zachowaniem kolejnosci, partition ===");

        List<Integer> listaA = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> listaB = Arrays.asList(3, 4, 5, 6, 7);

        System.out.println("Lista A: " + listaA);
        System.out.println("Lista B: " + listaB);
        System.out.println("union (kolejnosc zachowana): " + ListUtils.union(listaA, listaB));
        System.out.println("intersection: " + ListUtils.intersection(listaA, listaB));
        System.out.println("subtract (A bez elementow z B): " + ListUtils.subtract(listaA, listaB));

        List<Integer> numeryZgloszen = Arrays.asList(101, 102, 103, 104, 105, 106, 107);
        List<List<Integer>> paczki = ListUtils.partition(numeryZgloszen, 3);
        System.out.println("partition po 3 (ostatnia paczka moze byc krotsza): " + paczki);
    }

    private static void demonstrateBag() {
        System.out.println("\n=== Bag/HashBag: zliczanie wystapien elementu ===");

        String tekst = "ala ma kota i kot ma ale ala ma tez psa";
        Bag<String> licznikSlow = new HashBag<>();
        for (String slowo : tekst.split(" ")) {
            licznikSlow.add(slowo);
        }

        System.out.println("Ile razy wystepuje 'ma': " + licznikSlow.getCount("ma"));
        System.out.println("Ile razy wystepuje 'ala': " + licznikSlow.getCount("ala"));
        System.out.println("Unikalne slowa (uniqueSet): " + licznikSlow.uniqueSet());
        System.out.println("Calkowita liczba slow WLICZAJAC powtorzenia (size): " + licznikSlow.size());

        licznikSlow.add("java", 5);
        System.out.println("Po add(\"java\", 5): getCount(\"java\") = " + licznikSlow.getCount("java"));
    }

    private static void demonstrateMultiValuedMap() {
        System.out.println("\n=== MultiValuedMap/ArrayListValuedHashMap: jeden klucz, wiele wartosci ===");

        MultiValuedMap<String, Integer> ocenyStudentow = new ArrayListValuedHashMap<>();
        ocenyStudentow.put("Ala", 5);
        ocenyStudentow.put("Ala", 4);
        ocenyStudentow.put("Ala", 5);
        ocenyStudentow.put("Bartek", 3);
        ocenyStudentow.put("Bartek", 4);

        System.out.println("Oceny Ali: " + ocenyStudentow.get("Ala"));
        System.out.println("Oceny Bartka: " + ocenyStudentow.get("Bartek"));
        System.out.println("Oceny nieistniejacego studenta (pusta kolekcja, NIE null): " + ocenyStudentow.get("Celina"));
        System.out.println("Wszystkie klucze (keySet): " + ocenyStudentow.keySet());
        System.out.println("Calkowita liczba wpisow (wliczajac duplikaty ocen): " + ocenyStudentow.size());
    }

    private static void demonstratePredicateAndTransformer() {
        System.out.println("\n=== Predicate/Transformer: legacy interfejsy funkcyjne + CollectionUtils.select/collect ===");

        List<Integer> liczby = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        Predicate<Integer> jestParzysta = liczba -> liczba % 2 == 0;
        Transformer<Integer, Integer> doKwadratu = liczba -> liczba * liczba;

        Collection<Integer> parzyste = CollectionUtils.select(liczby, jestParzysta);
        Collection<Integer> kwadraty = CollectionUtils.collect(liczby, doKwadratu);

        System.out.println("Liczby wejsciowe: " + liczby);
        System.out.println("CollectionUtils.select(liczby, jestParzysta) = " + parzyste);
        System.out.println("CollectionUtils.collect(liczby, doKwadratu) = " + kwadraty);
        System.out.println("Rownowazny kod wspolczesny (java.util.function + Stream) uzylby "
                + "liczby.stream().filter(x -> x % 2 == 0).toList() oraz .map(x -> x * x).toList() - "
                + "identyczny wynik, bez zaleznosci od commons-collections4.");
    }
}
