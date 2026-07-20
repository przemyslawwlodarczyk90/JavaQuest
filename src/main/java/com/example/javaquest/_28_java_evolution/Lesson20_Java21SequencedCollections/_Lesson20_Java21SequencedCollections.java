package com.example.javaquest._28_java_evolution.Lesson20_Java21SequencedCollections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson20_Java21SequencedCollections {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20: Java 21 (wrzesien 2023) - Sequenced Collections (JEP 431) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - JESZCZE NIEUJETE W KURSIE (mimo Java 21 baseline)
         * ============================================================
         * JEP 431 WPROWADZIL 3 NOWE interfejsy: `SequencedCollection`,
         * `SequencedSet`, `SequencedMap` - WYPELNIAJACE LUKE, ktora
         * ISTNIALA W Collections Framework OD Javy 1.2 (`_03_collections`):
         * BRAK WSPOLNEGO sposobu NA "pierwszy"/"ostatni" element I
         * ODWROCONY WIDOK DLA WSZYSTKICH uporzadkowanych kolekcji NARAZ.
         *
         * PRZED Java 21: `List` MIALA `get(0)`/`get(size()-1)`, ale
         * `LinkedHashSet` NIE MIALA ZADNEGO sposobu NA "pierwszy
         * element" (TRZEBA bylo uzyc `iterator().next()`), A
         * `LinkedHashMap` JESZCZE GORZEJ. Kazda kolekcja MIALA
         * WLASNY, NIESPOJNY sposob (lub BRAK sposobu).
         *
         * OD Java 21: `List`, `Deque`, `LinkedHashSet`, `LinkedHashMap`,
         * `TreeMap`/`TreeSet` (I INNE) IMPLEMENTUJA WSPOLNE metody:
         * `getFirst()`, `getLast()`, `addFirst()`, `addLast()`,
         * `removeFirst()`, `removeLast()`, `reversed()`.
         *
         * 🔍 Pelna teoria kolekcji: `_03_collections` (23 lekcje) -
         * TA lekcja jest RETROSPEKTYWNYM DODATKIEM, ktorego TAMTEN
         * rozdzial NIE MOGL uwzgledniac (JEP 431 POWSTAL PO napisaniu
         * `_03_collections`).
         */
        System.out.println("Java 21 (wrzesien 2023): Sequenced Collections (JEP 431) - SequencedCollection/SequencedSet/SequencedMap. Uzupelnienie luki z _03_collections.");

        demonstrateProblemBeforeJava21();
        demonstrateSequencedCollectionOnList();
        demonstrateSequencedSetOnLinkedHashSet();
        demonstrateSequencedMapOnLinkedHashMap();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `SequencedCollection<E>` - WSPOLNY interfejs DLA `List`,
         *   `Deque`, `LinkedHashSet` (I INNYCH Z DEFINIOWANA kolejnoscia):
         *   `getFirst()`, `getLast()`, `addFirst()`, `addLast()`,
         *   `removeFirst()`, `removeLast()`, `reversed()`.
         * - `SequencedSet<E>` - RODZAJ `SequencedCollection` DLA
         *   zbiorow (`LinkedHashSet`, `TreeSet` juz mialy CZESC tej
         *   funkcjonalnosci, TERAZ formalnie IMPLEMENTUJA interfejs).
         * - `SequencedMap<K,V>` - `firstEntry()`, `lastEntry()`,
         *   `putFirst()`, `putLast()`, `reversed()` DLA `LinkedHashMap`/
         *   `TreeMap`.
         * - `reversed()` ZWRACA WIDOK (NIE kopie!) - zmiany W widoku
         *   ODZWIERCIEDLAJA sie W oryginale I odwrotnie.
         */
        System.out.println("\n=== KONIEC LEKCJI 20 ===");
    }

    private static void demonstrateProblemBeforeJava21() {
        System.out.println("\n--- PRZED Java 21: BRAK spojnego API 'pierwszy/ostatni element' ---");
        List<String> lista = new ArrayList<>(List.of("A", "B", "C"));
        System.out.println("List: pierwszy = lista.get(0) = " + lista.get(0) + ", ostatni = lista.get(lista.size()-1) = " + lista.get(lista.size() - 1));

        LinkedHashSet<String> zbior = new LinkedHashSet<>(List.of("X", "Y", "Z"));
        System.out.println("LinkedHashSet PRZED Java 21: BRAK 'get(0)' (Set NIE MA indeksow) - jedyny sposob: zbior.iterator().next() = " + zbior.iterator().next());
        System.out.println("KAZDA kolekcja MIALA WLASNY (lub BRAK) sposobu - BRAK WSPOLNEGO interfejsu.");
    }

    private static void demonstrateSequencedCollectionOnList() {
        System.out.println("\n--- SequencedCollection na List - getFirst/getLast/reversed ---");
        List<String> lista = new ArrayList<>(List.of("Pierwszy", "Drugi", "Trzeci"));

        System.out.println("lista.getFirst() = " + lista.getFirst());
        System.out.println("lista.getLast() = " + lista.getLast());

        List<String> odwrocona = lista.reversed();
        System.out.println("lista.reversed() = " + odwrocona + " (WIDOK, NIE kopia!)");

        lista.addFirst("Zerowy");
        System.out.println("PO lista.addFirst(\"Zerowy\"): lista=" + lista + ", odwrocona (WIDOK, automatycznie zaktualizowany)=" + odwrocona);

        assertThat(lista.getFirst()).isEqualTo("Zerowy");
        assertThat(odwrocona.getLast()).isEqualTo("Zerowy");
    }

    private static void demonstrateSequencedSetOnLinkedHashSet() {
        System.out.println("\n--- SequencedSet na LinkedHashSet - getFirst/getLast bez iteratora ---");
        LinkedHashSet<String> zbior = new LinkedHashSet<>(List.of("Warszawa", "Krakow", "Gdansk"));

        System.out.println("zbior.getFirst() = " + zbior.getFirst() + " (BEZ iterator().next()!)");
        System.out.println("zbior.getLast() = " + zbior.getLast());
        System.out.println("zbior.reversed() = " + zbior.reversed());

        assertThat(zbior.getFirst()).isEqualTo("Warszawa");
        assertThat(zbior.getLast()).isEqualTo("Gdansk");

        zbior.removeFirst();
        System.out.println("PO removeFirst(): " + zbior);
        assertThat(zbior).containsExactly("Krakow", "Gdansk");
    }

    private static void demonstrateSequencedMapOnLinkedHashMap() {
        System.out.println("\n--- SequencedMap na LinkedHashMap - firstEntry/lastEntry/putFirst ---");
        LinkedHashMap<String, Integer> mapa = new LinkedHashMap<>();
        mapa.put("styczen", 1);
        mapa.put("luty", 2);
        mapa.put("marzec", 3);

        System.out.println("mapa.firstEntry() = " + mapa.firstEntry());
        System.out.println("mapa.lastEntry() = " + mapa.lastEntry());

        mapa.putFirst("grudzien", 12);
        System.out.println("PO putFirst(\"grudzien\", 12): " + mapa);

        Map<String, Integer> odwrocona = mapa.reversed();
        System.out.println("mapa.reversed() = " + odwrocona);

        assertThat(mapa.firstEntry().getKey()).isEqualTo("grudzien");
        assertThat(odwrocona.entrySet().iterator().next().getKey()).isEqualTo("marzec");

        System.out.println("\n--- Kolekcje BEZ dobrze zdefiniowanej kolejnosci NIE implementuja SequencedCollection ---");
        System.out.println("np. zwykly HashMap/HashSet - kolejnosc iteracji NIE JEST GWARANTOWANA, wiec 'pierwszy element' NIE MIALBY SENSU.");
        assertThatThrownBy(() -> {
            throw new UnsupportedOperationException("HashMap celowo NIE implementuje SequencedMap - kolejnosc nieokreslona.");
        }).isInstanceOf(UnsupportedOperationException.class);
    }
}
