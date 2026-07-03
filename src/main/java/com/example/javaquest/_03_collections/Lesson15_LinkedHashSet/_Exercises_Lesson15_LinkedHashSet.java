package com.example.javaquest._03_collections.Lesson15_LinkedHashSet;

public class _Exercises_Lesson15_LinkedHashSet {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicInsertionOrder {
        /*
         * 🧪 Zadanie 1:
         * LinkedHashSet<String>: dodaj "Wtorek","Poniedziałek","Środa","Piątek","Czwartek".
         * Wypisz zbiór – sprawdź, że kolejność wypisania odpowiada kolejności dodawania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HashSetVsLinkedHashSet {
        /*
         * 🧪 Zadanie 2:
         * Dodaj te same 6 elementów String ("F","A","C","B","E","D") najpierw do HashSet,
         * potem do LinkedHashSet. Wypisz oba zbiory i porównaj kolejność iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NoDuplicates {
        /*
         * 🧪 Zadanie 3:
         * LinkedHashSet<Integer>: dodaj 10, 20, 10, 30, 20, 40.
         * Wypisz rozmiar (powinno być 4) oraz zawartość w kolejności pierwszego wystąpienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BasicOperations {
        /*
         * 🧪 Zadanie 4:
         * LinkedHashSet<String> owoce = {"Jabłko","Gruszka","Śliwka"}.
         * Sprawdź contains("Gruszka") i contains("Banan").
         * Usuń "Gruszka" i wypisz zbiór po usunięciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConstructFromList {
        /*
         * 🧪 Zadanie 5:
         * Lista: List.of(5, 1, 5, 3, 1, 4, 2, 4).
         * Zbuduj LinkedHashSet<Integer> z tej listy jednym konstruktorem.
         * Wypisz wynik i porównaj z wynikiem HashSet z tej samej listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IterateThreeWays {
        /*
         * 🧪 Zadanie 6:
         * LinkedHashSet<String> {"Ala","Basia","Celina","Dorota"}.
         * Zaiteruj po nim: pętlą for-each, metodą forEach z lambdą, oraz przez Iterator.
         * Za każdym razem kolejność powinna być identyczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SizeIsEmptyClear {
        /*
         * 🧪 Zadanie 7:
         * LinkedHashSet: dodaj 4 elementy, wypisz size() i isEmpty().
         * Wywołaj clear(), sprawdź size() i isEmpty() ponownie.
         * Dodaj nowy element po clear() i wypisz zbiór.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CustomObjectOrder {
        /*
         * 🧪 Zadanie 8:
         * Klasa Osoba(imie, wiek) z equals/hashCode po imie.
         * Dodaj do LinkedHashSet: Osoba("Ola",20), Osoba("Piotr",25), Osoba("Ola",30) (duplikat po imieniu).
         * Wypisz rozmiar (2) i kolejność wstawiania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ToArrayAndBack {
        /*
         * 🧪 Zadanie 9:
         * LinkedHashSet<Integer> {7,2,9,4}.
         * Zamień na tablicę Integer[] metodą toArray() – sprawdź, że kolejność w tablicy
         * odpowiada kolejności wstawiania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ContainsAll {
        /*
         * 🧪 Zadanie 10:
         * LinkedHashSet A = {"a","b","c","d"}, B = {"b","c"}.
         * Sprawdź A.containsAll(B) oraz B.containsAll(A).
         * Wypisz wyniki obu sprawdzeń.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DedupPreserveOrder {
        /*
         * 🧪 Zadanie 11:
         * Tekst: "ala ma kota kot ma ale ala nie ma psa".
         * Podziel na słowa i wypisz unikalne słowa w kolejności PIERWSZEGO wystąpienia
         * używając LinkedHashSet. Porównaj wynik z HashSet (bez gwarancji kolejności).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UnionIntersectionOrdered {
        /*
         * 🧪 Zadanie 12:
         * setA = LinkedHashSet {1,2,3,4,5}, setB = LinkedHashSet {4,5,6,7,8}.
         * Oblicz union (addAll) i intersection (retainAll) zachowując kolejność wstawiania
         * elementów z setA jako bazowej kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FirstUniqueCharacter {
        /*
         * 🧪 Zadanie 13:
         * Napisz firstUniqueChar(String s) → char, zwracającą pierwszy niepowtarzający się znak.
         * "swiss" → 'w'. Użyj LinkedHashSet<Character> lub LinkedHashMap<Character,Integer>
         * do zachowania kolejności napotkanych znaków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RecentSearches {
        /*
         * 🧪 Zadanie 14:
         * Symuluj historię wyszukiwań: dodawaj frazy "java", "python", "sql", "java", "go".
         * Użyj LinkedHashSet, żeby powtórzone frazy nie duplikowały się w historii,
         * ale kolejność pierwszego wpisania była zachowana. Wypisz finalną historię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OrderedSetFromStream {
        /*
         * 🧪 Zadanie 15:
         * Lista Integer {3,1,4,1,5,9,2,6,5,3,5}.
         * Użyj Stream API (distinct() lub collect) żeby otrzymać LinkedHashSet
         * zachowujący kolejność pierwszych wystąpień. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RemoveWhilePreservingOrder {
        /*
         * 🧪 Zadanie 16:
         * LinkedHashSet<Integer> {1,2,3,4,5,6,7,8,9,10}.
         * Usuń wszystkie liczby parzyste metodą removeIf.
         * Wypisz pozostałe elementy – kolejność musi zostać zachowana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MergeMultipleSetsOrdered {
        /*
         * 🧪 Zadanie 17:
         * Trzy LinkedHashSet<String> reprezentujące playlisty A, B, C (z powtarzającymi się utworami).
         * Połącz je w jeden LinkedHashSet zachowując kolejność: najpierw unikalne z A,
         * potem nowe z B, potem nowe z C.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareIterationOrderAfterRemoval {
        /*
         * 🧪 Zadanie 18:
         * LinkedHashSet<String> {"A","B","C","D","E"}.
         * Usuń "C", a następnie dodaj "C" ponownie.
         * Wypisz zbiór i sprawdź, na którym miejscu w kolejności wylądowało "C"
         * (na końcu, bo re-insert liczy się jako nowe wstawienie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UniqueLinesFromLog {
        /*
         * 🧪 Zadanie 19:
         * Tablica String[] logi = {"login:ala","error:404","login:ala","login:piotr","error:404","logout:ala"}.
         * Wypisz unikalne wpisy w kolejności pierwszego wystąpienia (LinkedHashSet)
         * oraz policz ile duplikatów zostało pominiętych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SetEqualityIgnoringOrder {
        /*
         * 🧪 Zadanie 20:
         * setA = LinkedHashSet {1,2,3} wstawione w tej kolejności,
         * setB = LinkedHashSet {3,2,1} wstawione w innej kolejności.
         * Sprawdź setA.equals(setB) – wyjaśnij komentarzem w kodzie (bez rozwiązania),
         * że equals() dla Set ignoruje kolejność mimo że to LinkedHashSet.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LRULikeVisitedPages {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj klasę VisitedPages z polem LinkedHashSet<String> i metodą visit(url):
         * jeśli url już istnieje w zbiorze – usuń go i dodaj ponownie (przesuwa na koniec,
         * czyli "ostatnio odwiedzone"). Jeśli rozmiar > 5, usuń najstarszy wpis (pierwszy iterator()).
         * Symuluj 10 wizyt i wypisz końcowy stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TopologicalOrderTracking {
        /*
         * 🧪 Zadanie 22:
         * Graf zależności zadań jako Map<String, List<String>> (zadanie → lista zależności).
         * Zaimplementuj prosty DFS budujący kolejność topologiczną, zapisując odwiedzone
         * węzły do LinkedHashSet<String> (kolejność dodania = kolejność topologiczna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DeduplicateStreamOfEvents {
        /*
         * 🧪 Zadanie 23:
         * Strumień zdarzeń: lista rekordów Event(userId, action) w kolejności czasowej.
         * Znajdź dla każdego userId pierwszą unikalną akcję (LinkedHashSet<String> per user)
         * i wypisz wynik w kolejności napotkania userów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_OrderedGraphBFS {
        /*
         * 🧪 Zadanie 24:
         * Graf nieskierowany Map<Integer, List<Integer>>.
         * Zaimplementuj BFS od wierzchołka startowego, zapisując kolejność odwiedzin
         * do LinkedHashSet<Integer> (żeby uniknąć duplikatów i zachować porządek odwiedzin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CombineWithTreeSetComparison {
        /*
         * 🧪 Zadanie 25:
         * Lista losowych Integer (z duplikatami, np. 15 elementów).
         * Zbuduj z niej: HashSet, LinkedHashSet i TreeSet.
         * Wypisz wszystkie trzy i skomentuj (w komentarzu) różnicę w kolejności iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_UniquePermutationTracking {
        /*
         * 🧪 Zadanie 26:
         * Generuj permutacje tablicy {1,2,2} (z powtórzeniami) rekurencyjnie.
         * Użyj LinkedHashSet<List<Integer>> żeby zebrać unikalne permutacje
         * w kolejności ich pierwszego wygenerowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EventDeduplicationWindow {
        /*
         * 🧪 Zadanie 27:
         * Symuluj strumień identyfikatorów zdarzeń (int) napływających w czasie.
         * Utrzymuj "okno" ostatnich N unikalnych zdarzeń w LinkedHashSet (usuwając najstarsze,
         * gdy przekroczy rozmiar N) – podobnie jak prosty deduplikator z limitem pamięci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_OrderedSetOperationsPipeline {
        /*
         * 🧪 Zadanie 28:
         * Trzy zbiory LinkedHashSet<String>: nowi_klienci, aktywni_klienci, vip_klienci.
         * Wykonaj potok operacji: (nowi ∩ aktywni) ∪ vip, zachowując możliwie kolejność
         * wstawiania na każdym etapie. Wypisz wynik pośredni i końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CycleDetectionWithOrderedVisited {
        /*
         * 🧪 Zadanie 29:
         * Graf skierowany Map<String, List<String>>.
         * Wykryj cykl metodą DFS, przechowując ścieżkę bieżącego wywołania (rekursji)
         * w LinkedHashSet<String>, żeby móc wypisać cykl w kolejności odwiedzin, gdy zostanie znaleziony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_StableGroupingByFirstSeen {
        /*
         * 🧪 Zadanie 30:
         * Lista transakcji Transakcja(kategoria, kwota) w losowej kolejności.
         * Pogrupuj kategorie używając LinkedHashMap<String, LinkedHashSet<Transakcja>>,
         * tak żeby kategorie i transakcje w każdej grupie zachowały kolejność pierwszego wystąpienia.
         * Wypisz podsumowanie grup w tej stabilnej kolejności.
         */
        public static void main(String[] args) { }
    }
}
