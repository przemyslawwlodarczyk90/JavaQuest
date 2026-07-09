package com.example.javaquest._13_libraries.Lesson10_GuavaMultimapMultisetBiMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _Lesson10_GuavaMultimapMultisetBiMap {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: GUAVA - MULTIMAP, MULTISET, BIMAP, TABLE ===");

        /*
         * ============================================================
         * 📦 STRUKTURY, KTÓRYCH JDK NIE MA WCALE
         * ============================================================
         * - W przeciwienstwie do Lekcji 9 (gdzie Guava byla "lepsza
         *   wersja" czegos, co JDK juz ma - List.of/Set.of/Map.of), ta
         *   lekcja pokazuje CZTERY struktury, dla ktorych standardowa
         *   biblioteka Javy NIE MA zadnego odpowiednika:
         *     * Multimap - jeden klucz -> WIELE wartosci,
         *     * Multiset - "worek" ze zliczaniem wystapien elementu,
         *     * BiMap - mapa dzialajaca w OBIE strony (wartosc -> klucz),
         *     * Table - mapa dwuwymiarowa (wiersz + kolumna -> wartosc).
         * - Kazda z nich da sie "recznie" zasymulowac zwykla
         *   Map/List/Set (i tak wlasnie robilo sie to przed Guava), ale
         *   kosztem powtarzalnego, podatnego na bledy boilerplate'u -
         *   ponizsze przyklady pokazuja ROZNICE wprost.
         */
        System.out.println("\nMultimap, Multiset, BiMap, Table - struktur ktorych brakuje w java.util.");

        /*
         * ============================================================
         * 🔹 MULTIMAP - JEDEN KLUCZ, WIELE WARTOŚCI
         * ============================================================
         * - Multimap<K, V> to logicznie Map<K, Collection<V>>, ale bez
         *   recznego zarzadzania wewnetrzna kolekcja dla kazdego klucza.
         * - ArrayListMultimap - wartosci pod kluczem trzymane jako LISTA
         *   (dopuszcza duplikaty, zachowuje kolejnosc wstawiania).
         * - HashMultimap - wartosci pod kluczem trzymane jako ZBIOR (bez
         *   duplikatow, bez gwarancji kolejnosci).
         * - .get(klucz) NIGDY nie zwraca null - dla nieistniejacego
         *   klucza zwraca PUSTA kolekcje (bezpieczne, bez NullPointerException).
         */
        demonstrateMultimap();

        /*
         * ============================================================
         * 🔍 PORÓWNANIE: Multimap vs RĘCZNY Map<String, List<Integer>>
         * ============================================================
         * - Bez Multimap trzeba recznie sprawdzac, czy klucz juz istnieje
         *   (computeIfAbsent), zanim doda sie wartosc do jego listy -
         *   ponizej ten sam wynik osiagniety DWIEMA metodami, do
         *   porownania ilosci kodu.
         */
        compareMultimapWithManualMap();

        /*
         * ============================================================
         * 🔹 MULTISET - LICZNIK WYSTĄPIEŃ ELEMENTU
         * ============================================================
         * - Multiset<E> to "worek" (bag) - kolekcja, ktora pamieta, ILE
         *   razy dany element zostal do niej dodany (odpowiednik
         *   Map<E, Integer> zliczajacej wystapienia, ale z gotowym API).
         * - HashMultiset - implementacja oparta o hashowanie (bez
         *   gwarancji kolejnosci elementow).
         * - count(element) - liczba wystapien danego elementu.
         * - elementSet() - zbior UNIKALNYCH elementow (bez powtorzen).
         * - size() - CALKOWITA liczba elementow (WLICZAJAC powtorzenia).
         */
        demonstrateMultiset();

        /*
         * ============================================================
         * 🔹 BIMAP - MAPA DWUKIERUNKOWA
         * ============================================================
         * - BiMap<K, V> gwarantuje unikalnosc WARTOSCI (tak jak zwykla
         *   Map gwarantuje unikalnosc kluczy) - dzieki temu mozna ja
         *   bezpiecznie "odwrocic".
         * - .inverse() zwraca BiMap<V, K> - ZYWY WIDOK (nie kopie) -
         *   zmiana w oryginalnej mapie jest od razu widoczna w odwroconej
         *   i na odwrot.
         * - put(k, v), gdy WARTOSC v jest juz przypisana do INNEGO klucza,
         *   rzuca IllegalArgumentException (ochrona przed przypadkowym
         *   zlamaniem unikalnosci) - forcePut(k, v) wymusza nadpisanie.
         */
        demonstrateBiMap();

        /*
         * ============================================================
         * 🔹 TABLE - MAPA DWUWYMIAROWA (WIERSZ x KOLUMNA -> WARTOŚĆ)
         * ============================================================
         * - Table<R, C, V> to odpowiednik Map<R, Map<C, V>>, ale z
         *   gotowym, wygodnym API zamiast recznego zagniezdzania map.
         * - HashBasedTable - implementacja oparta o hashowanie.
         * - put(wiersz, kolumna, wartosc), get(wiersz, kolumna).
         * - row(wiersz) - caly wiersz jako Map<kolumna, wartosc>.
         * - column(kolumna) - cala kolumna jako Map<wiersz, wartosc>.
         * - rowKeySet()/columnKeySet() - wszystkie klucze wierszy/kolumn.
         */
        demonstrateTable();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Multimap (ArrayListMultimap/HashMultimap) - klucz -> wiele
         *   wartosci, bez recznego computeIfAbsent.
         * - Multiset (HashMultiset) - zliczanie wystapien elementow,
         *   count()/elementSet()/size() gotowe od reki.
         * - BiMap (HashBiMap) - mapa dzialajaca w obie strony przez
         *   .inverse(), z gwarancja unikalnosci wartosci.
         * - Table (HashBasedTable) - mapa dwuwymiarowa wiersz x kolumna,
         *   bez recznego zagniezdzania Map<R, Map<C, V>>.
         * - Kolejna lekcja: Preconditions (walidacja argumentow/stanu) i
         *   LoadingCache (cache w pamieci z automatycznym ladowaniem).
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void demonstrateMultimap() {
        System.out.println("\n=== Multimap: student -> lista ocen ===");

        Multimap<String, Integer> ocenyStudentow = ArrayListMultimap.create();
        ocenyStudentow.put("Ala", 5);
        ocenyStudentow.put("Ala", 4);
        ocenyStudentow.put("Ala", 5);
        ocenyStudentow.put("Bartek", 3);
        ocenyStudentow.put("Bartek", 4);

        System.out.println("Oceny Ali: " + ocenyStudentow.get("Ala"));
        System.out.println("Oceny Bartka: " + ocenyStudentow.get("Bartek"));
        System.out.println("Oceny nieistniejacego studenta (pusta kolekcja, NIE null): " + ocenyStudentow.get("Celina"));
        System.out.println("Wszystkie wpisy (klucz=wartosc): " + ocenyStudentow.entries());
        System.out.println("Calkowita liczba wpisow (wliczajac duplikaty ocen): " + ocenyStudentow.size());

        // HashMultimap - wartosci jako ZBIOR, bez duplikatow.
        Multimap<String, String> tagiArtykulow = HashMultimap.create();
        tagiArtykulow.put("java-basics", "java");
        tagiArtykulow.put("java-basics", "poczatkujacy");
        tagiArtykulow.put("java-basics", "java"); // duplikat - zostanie zignorowany (Set)
        System.out.println("Tagi artykulu (HashMultimap, bez duplikatow): " + tagiArtykulow.get("java-basics"));
    }

    private static void compareMultimapWithManualMap() {
        System.out.println("\n=== Multimap vs reczny Map<String, List<Integer>> ===");

        // Wersja Guava - jedna linia na wpis.
        Multimap<String, Integer> zGuava = ArrayListMultimap.create();
        zGuava.put("Ala", 5);
        zGuava.put("Ala", 4);

        // Wersja reczna - trzeba pamietac o computeIfAbsent przy KAZDYM wstawieniu.
        Map<String, List<Integer>> recznie = new HashMap<>();
        recznie.computeIfAbsent("Ala", k -> new ArrayList<>()).add(5);
        recznie.computeIfAbsent("Ala", k -> new ArrayList<>()).add(4);

        System.out.println("Guava Multimap: " + zGuava.get("Ala"));
        System.out.println("Reczna Map<String,List<Integer>>: " + recznie.get("Ala"));
        System.out.println("Ten sam wynik - Multimap eliminuje potrzebe recznego computeIfAbsent.");
    }

    private static void demonstrateMultiset() {
        System.out.println("\n=== Multiset: zliczanie slow w tekscie ===");

        String tekst = "ala ma kota i kot ma ale ala ma tez psa";
        Multiset<String> licznikSlow = HashMultiset.create();
        for (String slowo : tekst.split(" ")) {
            licznikSlow.add(slowo);
        }

        System.out.println("Ile razy wystepuje 'ma': " + licznikSlow.count("ma"));
        System.out.println("Ile razy wystepuje 'ala': " + licznikSlow.count("ala"));
        System.out.println("Unikalne slowa (elementSet): " + licznikSlow.elementSet());
        System.out.println("Calkowita liczba slow WLICZAJAC powtorzenia (size): " + licznikSlow.size());

        System.out.println("Wystapienia kazdego slowa:");
        for (Multiset.Entry<String> wpis : licznikSlow.entrySet()) {
            System.out.println("  " + wpis.getElement() + " -> " + wpis.getCount());
        }
    }

    private static void demonstrateBiMap() {
        System.out.println("\n=== BiMap: kod kraju <-> nazwa kraju ===");

        BiMap<String, String> kodDoNazwy = HashBiMap.create();
        kodDoNazwy.put("PL", "Polska");
        kodDoNazwy.put("DE", "Niemcy");
        kodDoNazwy.put("FR", "Francja");

        BiMap<String, String> nazwaDoKodu = kodDoNazwy.inverse();

        System.out.println("Kod -> nazwa: PL to " + kodDoNazwy.get("PL"));
        System.out.println("Nazwa -> kod (inverse): 'Niemcy' to " + nazwaDoKodu.get("Niemcy"));

        try {
            kodDoNazwy.put("XX", "Polska"); // "Polska" juz przypisana do "PL"
        } catch (IllegalArgumentException e) {
            System.out.println("Proba przypisania zajetej wartosci 'Polska' do nowego klucza -> IllegalArgumentException.");
        }

        kodDoNazwy.forcePut("XX", "Polska"); // wymuszone nadpisanie - "PL" traci wartosc "Polska"
        System.out.println("Po forcePut('XX','Polska'): klucz 'PL' ma teraz wartosc = " + kodDoNazwy.get("PL"));
    }

    private static void demonstrateTable() {
        System.out.println("\n=== Table: wyniki sportowe druzyna x runda ===");

        Table<String, Integer, Integer> wynikiSportowe = HashBasedTable.create();
        wynikiSportowe.put("Orly", 1, 10);
        wynikiSportowe.put("Orly", 2, 15);
        wynikiSportowe.put("Sokoly", 1, 8);
        wynikiSportowe.put("Sokoly", 2, 20);

        System.out.println("Wynik Orlow w rundzie 2: " + wynikiSportowe.get("Orly", 2));
        System.out.println("Caly wiersz 'Orly' (runda -> wynik): " + wynikiSportowe.row("Orly"));
        System.out.println("Cala kolumna 'runda 1' (druzyna -> wynik): " + wynikiSportowe.column(1));
        System.out.println("Wszystkie druzyny (rowKeySet): " + wynikiSportowe.rowKeySet());
        System.out.println("Wszystkie rundy (columnKeySet): " + wynikiSportowe.columnKeySet());
    }
}
