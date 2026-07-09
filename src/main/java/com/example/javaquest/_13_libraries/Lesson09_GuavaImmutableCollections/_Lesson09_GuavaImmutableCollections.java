package com.example.javaquest._13_libraries.Lesson09_GuavaImmutableCollections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class _Lesson09_GuavaImmutableCollections {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: GUAVA - KOLEKCJE NIEMUTOWALNE ===");

        /*
         * ============================================================
         * 📦 CZYM JEST GUAVA
         * ============================================================
         * - Guava (com.google.guava:guava) to biblioteka Google, jedna z
         *   najpopularniejszych bibliotek "utility" w calym ekosystemie
         *   Java - rozszerza to, czego brakuje w standardowej bibliotece
         *   (java.util) o dodatkowe kolekcje, narzedzia do stringow,
         *   cache w pamieci, prymitywy wspolbieznosci, walidacje
         *   argumentow i wiele wiecej.
         * - Powstala okolo 2009-2010, a wiec DLUGO przed tym, jak JDK
         *   dostal wlasne "wygodne" fabryki niemutowalnych kolekcji
         *   (List.of/Set.of/Map.of pojawily sie dopiero w Javie 9, 2017).
         *   Przez lata Guava byla wrecz JEDYNYM wygodnym sposobem na
         *   tworzenie niemutowalnych kolekcji w Javie - std stad ogromna
         *   popularnosc i mnostwo starszego kodu, ktory z niej korzysta.
         * - Ten rozdzial NIE powtarza standardowych kolekcji JDK
         *   (ArrayList/HashMap/HashSet - szczegolowo w _03_collections) -
         *   skupia sie na tym, czego JDK NIE MA wcale (Multimap, Multiset,
         *   BiMap, Table - Lekcja 10) oraz na roznicach tam, gdzie oba
         *   swiaty sie pokrywaja (kolekcje niemutowalne - ta lekcja).
         */
        System.out.println("\nGuava = biblioteka Google rozszerzajaca kolekcje/stringi/cache/wspolbieznosc JDK.");

        /*
         * ============================================================
         * 🔹 IMMUTABLELIST / IMMUTABLESET / IMMUTABLEMAP - TWORZENIE PRZEZ .of(...)
         * ============================================================
         * - ImmutableList/ImmutableSet/ImmutableMap to niemutowalne
         *   odpowiedniki List/Set/Map - raz utworzone, NIE MOZNA ich
         *   zmienic (add/remove/put rzuca UnsupportedOperationException).
         * - Najprostszy sposob tworzenia: statyczna metoda .of(...) -
         *   dokladnie jak List.of()/Set.of()/Map.of() z JDK.
         */
        demonstrateOfFactories();

        /*
         * ============================================================
         * 🔍 BUILDER - GDY TRZEBA DODAWAC WARUNKOWO / W PETLI
         * ============================================================
         * - .of(...) wymaga podania WSZYSTKICH elementow z gory, w
         *   jednym wywolaniu - nie da sie nim nic dodac WARUNKOWO.
         * - Builder (ImmutableList.builder(), ImmutableSet.builder(),
         *   ImmutableMap.builder()) pozwala budowac kolekcje KROK PO
         *   KROKU (np. w petli, z warunkami if), a na koniec "zamrozic"
         *   ja jednym wywolaniem .build() - od tego momentu jest juz
         *   w pelni niemutowalna.
         * - To jest jedna z sytuacji, gdzie Guava wciaz ma realna
         *   przewage nad czystym JDK: List.of() nie ma odpowiednika
         *   buildera - trzeba by najpierw zbudowac zwykla, mutowalna
         *   ArrayList, a dopiero potem opakowac ja List.copyOf(...).
         *   Builder Guavy robi to w jednym, czytelnym laancuchu wywolan.
         */
        demonstrateBuilder();

        /*
         * ============================================================
         * 🔍 RÓŻNICE WZGLĘDEM List.of()/Set.of()/Map.of() Z JDK
         * ============================================================
         * - Kolejnosc iteracji: List.of()/Map.of()/Set.of() z JDK CELOWO
         *   losuja kolejnosc iteracji miedzy uruchomieniami JVM (aby
         *   zniechecic programistow do polegania na kolejnosci, ktorej
         *   kontrakt nie gwarantuje) - ImmutableList/ImmutableSet/
         *   ImmutableMap Guavy ZAWSZE zachowuja kolejnosc WSTAWIANIA,
         *   deterministycznie, przy kazdym uruchomieniu.
         * - Duplikat klucza w mapie: ImmutableMap.Builder rzuca
         *   IllegalArgumentException, gdy dwukrotnie wywolasz .put() z
         *   TYM SAMYM kluczem - "fail fast", blad wychodzi na jaw od
         *   razu przy budowaniu. To zachowanie kontrastuje ze zwykla,
         *   mutowalna HashMap, gdzie map.put(k, v) po raz drugi z tym
         *   samym kluczem CICHO PODMIENIA wartosc, bez ostrzezenia - w
         *   petli budujacej mape recznie latwo nie zauwazyc takiego buga
         *   (patrz przyklad nizej).
         * - Ani null (elementy list/setow, klucze/wartosci map) - w tym
         *   punkcie Guava i JDK sa IDENTYCZNE, obie rzucaja
         *   NullPointerException przy probie uzycia null.
         * - Guava ma bogatszy zestaw metod pomocniczych na samych
         *   kolekcjach: np. ImmutableList.reverse() (widok w odwrotnej
         *   kolejnosci), ImmutableSortedSet/ImmutableSortedMap (JDK NIE
         *   MA gotowej fabryki na niemutowalna, POSORTOWANA mape/zbior -
         *   TreeMap/TreeSet sa mutowalne, a Collections.unmodifiableXxx
         *   tylko OPAKOWUJE mutowalna kolekcje, nie daje prawdziwej
         *   gwarancji niezmiennosci - ktos z dostepem do oryginalu wciaz
         *   moze ja zmienic).
         */
        compareWithJdkAndMutableMap();

        /*
         * ============================================================
         * 🔹 LISTS / SETS / MAPS - STATYCZNE FABRYKI I NARZĘDZIA
         * ============================================================
         * - Obok samych typow kolekcji Guava dostarcza klasy narzedziowe
         *   z metodami statycznymi dzialajacymi na ZWYKLYCH (nie tylko
         *   Immutable) kolekcjach: Lists, Sets, Maps.
         * - Lists.partition(list, size) - dzieli liste na kolejne
         *   podlisty o zadanym rozmiarze (ostatnia moze byc krotsza) -
         *   przydatne np. do przetwarzania danych w paczkach (batch).
         */
        demonstrateListsPartition();

        /*
         * ============================================================
         * 🔍 SETS.UNION / INTERSECTION / DIFFERENCE - WIDOKI, NIE KOPIE
         * ============================================================
         * - Sets.union(a, b), Sets.intersection(a, b), Sets.difference(a, b)
         *   zwracaja SetView - "leniwy" WIDOK na oba zbiory wejsciowe, a
         *   NIE nowa, w pelni policzona kopie.
         * - Roznica wydajnosciowa: widok NIE zajmuje dodatkowej pamieci
         *   na elementy (nie kopiuje niczego), ale kazda operacja na nim
         *   (np. kolejna iteracja, kolejne wywolanie .size()) PRZELICZA
         *   wynik NA NOWO na podstawie zbiorow zrodlowych. Jesli wynik
         *   bedzie uzywany WIELOKROTNIE, oplaca sie zmaterializowac go
         *   RAZ przez ImmutableSet.copyOf(widok) - inaczej powtarzasz te
         *   sama prace obliczeniowa przy kazdym uzyciu.
         * - Analogicznie Maps.difference(mapaA, mapaB) porownuje dwie
         *   mapy i zwraca MapDifference z wpisami wspolnymi, roznymi
         *   i wystepujacymi tylko po jednej stronie.
         */
        demonstrateSetsViewsAndMapsDifference();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ImmutableList/ImmutableSet/ImmutableMap - niemutowalne
         *   kolekcje Guavy, tworzone przez .of(...) (proste przypadki)
         *   lub .builder() (budowanie warunkowe/w petli - tego JDK nie
         *   ma wprost).
         * - Od Javy 9 wiele prostych przypadkow pokrywa juz
         *   List.of()/Set.of()/Map.of() z JDK - Guava wciaz wygrywa przy
         *   builderach, kolekcjach POSORTOWANYCH (ImmutableSortedSet/Map)
         *   i deterministycznej kolejnosci iteracji.
         * - Lists/Sets/Maps to klasy narzedziowe ze statycznymi metodami
         *   (partition, union/intersection/difference jako WIDOKI,
         *   difference dla map) dzialajacymi na zwyklych kolekcjach.
         * - Kolejna lekcja: struktury, ktorych JDK NIE MA WCALE -
         *   Multimap, Multiset, BiMap, Table.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateOfFactories() {
        System.out.println("\n=== ImmutableList/Set/Map.of(...) ===");

        ImmutableList<String> jezyki = ImmutableList.of("Java", "Kotlin", "Scala");
        ImmutableSet<Integer> unikalneLiczby = ImmutableSet.of(1, 2, 3, 2, 1);
        ImmutableMap<String, Integer> stanowiska = ImmutableMap.of(
                "Junior", 1,
                "Mid", 3,
                "Senior", 5
        );

        System.out.println("ImmutableList: " + jezyki);
        System.out.println("ImmutableSet (duplikaty zredukowane): " + unikalneLiczby);
        System.out.println("ImmutableMap: " + stanowiska);

        try {
            jezyki.add("Groovy");
        } catch (UnsupportedOperationException e) {
            System.out.println("Proba modyfikacji ImmutableList -> UnsupportedOperationException (jak spodziewano).");
        }
    }

    private static void demonstrateBuilder() {
        System.out.println("\n=== ImmutableList/Set/Map.builder() - dodawanie warunkowe ===");

        List<String> kandydaci = List.of("Ala", "Bartek", "Celina", "Damian", "Ewa", "Filip");

        // Warunkowe dodawanie w petli - niemozliwe wprost przez ImmutableList.of(...).
        ImmutableList.Builder<String> parzysteDlugoscImieniaBuilder = ImmutableList.builder();
        for (String imie : kandydaci) {
            if (imie.length() % 2 == 0) {
                parzysteDlugoscImieniaBuilder.add(imie);
            }
        }
        ImmutableList<String> parzysteDlugoscImienia = parzysteDlugoscImieniaBuilder.build();
        System.out.println("Imiona o parzystej dlugosci (zbudowane warunkowo): " + parzysteDlugoscImienia);

        ImmutableMap<String, Integer> dlugosciImion = ImmutableMap.<String, Integer>builder()
                .put("Ala", 3)
                .put("Bartek", 7)
                .put("Celina", 6)
                .buildOrThrow();
        System.out.println("Mapa imie->dlugosc (builder): " + dlugosciImion);
    }

    private static void compareWithJdkAndMutableMap() {
        System.out.println("\n=== ROZNICE: KOLEJNOSC ITERACJI I DUPLIKATY KLUCZY ===");

        // Guava ImmutableMap - deterministyczna kolejnosc WSTAWIANIA, zawsze taka sama.
        ImmutableMap<String, Integer> immutableMap = ImmutableMap.of("c", 3, "a", 1, "b", 2);
        System.out.println("Guava ImmutableMap (kolejnosc wstawiania, zawsze 'c,a,b'): " + immutableMap.keySet());

        // JDK Map.of() - kolejnosc NIE jest gwarantowana i moze sie roznic miedzy uruchomieniami JVM.
        Map<String, Integer> jdkMap = Map.of("c", 3, "a", 1, "b", 2);
        System.out.println("JDK Map.of() (kolejnosc NIEgwarantowana): " + jdkMap.keySet());

        // Guava Builder - duplikat klucza rzuca wyjatek NATYCHMIAST, przy build().
        try {
            ImmutableMap.<String, Integer>builder()
                    .put("Java", 1)
                    .put("Java", 2)
                    .buildOrThrow();
        } catch (IllegalArgumentException e) {
            System.out.println("ImmutableMap.Builder z duplikatem klucza 'Java' -> IllegalArgumentException (fail fast).");
        }

        // Zwykla, mutowalna HashMap - duplikat klucza CICHO podmienia wartosc, bez ostrzezenia.
        Map<String, Integer> zwyklaMapa = new HashMap<>();
        zwyklaMapa.put("Java", 1);
        zwyklaMapa.put("Java", 2); // brak wyjatku - wartosc dla "Java" po prostu staje sie 2
        System.out.println("Zwykla HashMap z duplikatem klucza -> CICHA podmiana, wartosc koncowa: " + zwyklaMapa.get("Java"));
    }

    private static void demonstrateListsPartition() {
        System.out.println("\n=== Lists.partition(lista, rozmiar) ===");

        List<Integer> numeryZamowien = List.of(101, 102, 103, 104, 105, 106, 107);
        List<List<Integer>> paczki = Lists.partition(numeryZamowien, 3);

        System.out.println("Zamowienia podzielone na paczki po 3 (ostatnia moze byc krotsza):");
        for (int i = 0; i < paczki.size(); i++) {
            System.out.println("  Paczka " + (i + 1) + ": " + paczki.get(i));
        }
    }

    private static void demonstrateSetsViewsAndMapsDifference() {
        System.out.println("\n=== Sets.union/intersection/difference (WIDOKI) i Maps.difference ===");

        Set<String> umiejetnosciAli = new LinkedHashSet<>(Set.of("Java", "SQL", "Docker"));
        Set<String> umiejetnosciBartka = new LinkedHashSet<>(Set.of("Java", "Python", "Docker", "Kubernetes"));

        Sets.SetView<String> wszystkieRazem = Sets.union(umiejetnosciAli, umiejetnosciBartka);
        Sets.SetView<String> wspolne = Sets.intersection(umiejetnosciAli, umiejetnosciBartka);
        Sets.SetView<String> tylkoAla = Sets.difference(umiejetnosciAli, umiejetnosciBartka);

        System.out.println("Suma umiejetnosci (widok, liczona przy kazdym uzyciu): " + wszystkieRazem);
        System.out.println("Czesc wspolna (widok): " + wspolne);
        System.out.println("Tylko u Ali (widok): " + tylkoAla);

        // Widok bedzie uzywany wielokrotnie -> oplaca sie zmaterializowac RAZ.
        ImmutableSet<String> wspolneZmaterializowane = ImmutableSet.copyOf(wspolne);
        System.out.println("Czesc wspolna zmaterializowana RAZ (do wielokrotnego uzycia): " + wspolneZmaterializowane);

        Map<String, Integer> ocenyAli = Map.of("Java", 5, "SQL", 4);
        Map<String, Integer> ocenyBartka = Map.of("Java", 4, "SQL", 4);
        var roznica = Maps.difference(ocenyAli, ocenyBartka);
        System.out.println("Maps.difference - wspolne i identyczne: " + roznica.entriesInCommon());
        System.out.println("Maps.difference - te same klucze, rozne wartosci: " + roznica.entriesDiffering());
    }
}
