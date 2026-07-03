package com.example.javaquest._03_collections.Lesson05_HashSet;

public class _Exercises_Lesson05_HashSet {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSet {
        /*
         * 🧪 Zadanie 1:
         * HashSet<String>: dodaj "java","python","java","c++","python".
         * Wypisz rozmiar i zawartość. Sprawdź contains("java") i contains("rust").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RemoveDuplicates {
        /*
         * 🧪 Zadanie 2:
         * Lista: {1,2,2,3,3,3,4,4,4,4,5}.
         * Usuń duplikaty przez HashSet. Wypisz unikalną kolekcję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SetOps {
        /*
         * 🧪 Zadanie 3:
         * A={1,2,3,4,5}, B={3,4,5,6,7}.
         * Oblicz: union (∪), intersection (∩), difference A\B, difference B\A.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_Subset {
        /*
         * 🧪 Zadanie 4:
         * Sprawdź: czy {3,4} jest podzbiorem {1,2,3,4,5}.
         * Czy {1,2,3,4,5} jest nadzbiorem {3,4}.
         * Użyj containsAll.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IterateSet {
        /*
         * 🧪 Zadanie 5:
         * HashSet<Integer>: {5,3,8,1,9,2}.
         * Iteruj for-each i wypisz każdy element.
         * Użyj forEach z lambdą.
         * Użyj Iteratora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CustomObjects {
        /*
         * 🧪 Zadanie 6:
         * Klasa Point(x,y) z equals/hashCode po (x,y).
         * Dodaj do HashSet: (1,2),(3,4),(1,2),(5,6).
         * Sprawdź rozmiar (powinien być 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NoHashCode {
        /*
         * 🧪 Zadanie 7:
         * Klasa BadPoint(x,y) BEZ equals/hashCode.
         * Dodaj (1,2),(1,2),(3,4) do HashSet.
         * Wypisz rozmiar – co i dlaczego się stało?
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SetFromList {
        /*
         * 🧪 Zadanie 8:
         * Lista: {"anna","bartek","anna","celina","bartek","damian"}.
         * Stwórz HashSet z tej listy. Wypisz liczbę unikalnych imion.
         * Wypisz też listę z powrotem z HashSeta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ContainsVsList {
        /*
         * 🧪 Zadanie 9:
         * Porównaj czas contains() dla ArrayList<Integer> vs HashSet<Integer>
         * z 1 000 000 elementów. Szukaj elementu z końca.
         * Zmierz System.nanoTime().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ClearContains {
        /*
         * 🧪 Zadanie 10:
         * HashSet: dodaj 5 elementów, sprawdź size i isEmpty.
         * clear(). Sprawdź size i isEmpty.
         * Dodaj element po clear – czy działa?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UniqueWords {
        /*
         * 🧪 Zadanie 11:
         * Tekst: "to be or not to be that is the question".
         * Podziel na słowa, wrzuć do HashSet – ile unikalnych słów?
         * Wypisz słowa których nie ma podwójnie (tylko raz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CommonElements {
        /*
         * 🧪 Zadanie 12:
         * 3 zbiory A, B, C.
         * Znajdź: wspólne dla wszystkich trzech (intersection).
         * Wspólne dla A i B, ale nie C.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_Anagram {
        /*
         * 🧪 Zadanie 13:
         * Napisz isAnagram(String a, String b) → boolean.
         * Zamień na zestaw znaków HashSet<Character> i porównaj.
         * Przetestuj: "listen"/"silent", "hello"/"world", "rat"/"tar".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_VisitedLinks {
        /*
         * 🧪 Zadanie 14:
         * System śledzenia odwiedzonych stron.
         * visit(url): dodaj do HashSet.
         * isVisited(url): sprawdź.
         * getCount(): rozmiar.
         * Symuluj 10 wizyt (z powtórzeniami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TwoSumHash {
        /*
         * 🧪 Zadanie 15:
         * Tablica {2,7,11,15} i target=9.
         * Znajdź parę sumującą do target używając HashSet.
         * Złożoność O(n): dla każdego n dodaj (target-n) do seta lub szukaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LongestNoRepeat {
        /*
         * 🧪 Zadanie 16:
         * Znajdź długość najdłuższego podciągu bez powtarzających się znaków.
         * "abcabcbb" → 3 ("abc"). Użyj HashSet + sliding window.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_Intersection {
        /*
         * 🧪 Zadanie 17:
         * Dwa int[] (mogą mieć duplikaty). Zwróć wspólne elementy
         * bez duplikatów używając HashSet.
         * Przetestuj: {1,2,2,1} ∩ {2,2} → {2}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GroupAnagrams {
        /*
         * 🧪 Zadanie 18:
         * Lista słów: {"eat","tea","tan","ate","nat","bat"}.
         * Pogrupuj anagramy razem. Użyj HashMap z HashSet jako wartością.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UniqueCharacter {
        /*
         * 🧪 Zadanie 19:
         * Znajdź pierwszy niepowtarzający się znak w Stringu.
         * "leetcode" → 'l'. Użyj LinkedHashSet (zachowuje kolejność).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PowerSet {
        /*
         * 🧪 Zadanie 20:
         * Generuj zbiór potęgowy (power set) dla {a,b,c}.
         * Wynik: {∅,{a},{b},{c},{a,b},{a,c},{b,c},{a,b,c}} – Set<Set<String>>.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BloomFilter {
        /*
         * 🧪 Zadanie 21:
         * Prosty Bloom Filter dla Stringów (używając wielu HashSet zamiast bitset).
         * Metody: add(s), mightContain(s).
         * Pokaż false positive i true negative.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GraphCycleDetect {
        /*
         * 🧪 Zadanie 22:
         * Graf nieukierunkowany jako Map<Integer,Set<Integer>>.
         * Wykryj cykl DFS używając HashSet odwiedzonych i w-stacku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WordLadder {
        /*
         * 🧪 Zadanie 23:
         * Word Ladder: "hit" → "cog", słownik {"hot","dot","dog","lot","log","cog"}.
         * BFS z HashSet dla odwiedzonych słów.
         * Zwróć długość najkrótszej ścieżki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SetEquality {
        /*
         * 🧪 Zadanie 24:
         * Napisz areEqual(Set<T> a, Set<T> b) → boolean.
         * Porównaj setA.equals(setB) vs ręczne: a.containsAll(b) && b.containsAll(a).
         * Przetestuj z równymi i nierównymi zbiorami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FriendGraph {
        /*
         * 🧪 Zadanie 25:
         * Graf znajomości: Map<String, Set<String>> friends.
         * Znajdź wspólnych znajomych Alice i Boba (intersection ich Set).
         * Znajdź znajomych 2-go stopnia Alice (union friends of friends, minus bezpośrednich).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_Duplicates {
        /*
         * 🧪 Zadanie 26:
         * Znajdź duplikaty w tablicy {1,3,4,2,2,3,4,5,1} bez sortowania.
         * Zwróć Set zduplikowanych elementów.
         * Złożoność O(n).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConsistentHashing {
        /*
         * 🧪 Zadanie 27:
         * Prosta symulacja consistent hashing z HashSet<Integer> "ring".
         * addNode(id), removeNode(id), getNode(key) → id noda odpowiedzialnego za klucz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SymmetricDiff {
        /*
         * 🧪 Zadanie 28:
         * Symmetric difference A△B = (A∪B) \ (A∩B) – elementy tylko w jednym zbiorze.
         * Zaimplementuj 3 sposobami i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_IsomorphicStrings {
        /*
         * 🧪 Zadanie 29:
         * "egg" i "add" są izomorficzne (e→a, g→d).
         * "foo" i "bar" nie są.
         * Napisz isIsomorphic(String s, String t) używając HashSet i HashMap.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_UniqueSubsets {
        /*
         * 🧪 Zadanie 30:
         * Znajdź wszystkie unikalne podzbiory tablicy {1,2,2}.
         * Wynik: {∅,{1},{2},{1,2},{2,2},{1,2,2}}.
         * Użyj HashSet<List<Integer>> do eliminacji duplikatów.
         */
        public static void main(String[] args) { }
    }
}
