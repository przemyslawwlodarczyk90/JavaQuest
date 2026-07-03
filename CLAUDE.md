# javaQuest

Kurs Java tworzony na wzór kursów [Codenga](https://codenga.pl) — teoria + ćwiczenia praktyczne,
budowany rozdziałami (dużymi blokami tematycznymi), z myślą o docelowej sprzedaży jako kurs online.

## Struktura projektu

Kod źródłowy: `src/main/java/com/example/javaquest/`

Każdy **rozdział** (duży blok kursu, odpowiednik osobnego kursu na Codenga, np. "Java - Kolekcje")
to osobny pakiet najwyższego poziomu: `_01_fundamentals`, `_02_oop`, `_03_collections`, kolejne
rozdziały będą dochodzić jako `_04_...`, `_05_...` itd.

Wewnątrz rozdziału każda **lekcja** to podpakiet `LessonXX_Temat` (numeracja od 00 lub 01).
W każdym podpakiecie lekcji są dokładnie **2 pliki**:

- `_LessonXX_Temat.java` — notatki teoretyczne. Jedna klasa, jedna metoda `main`, treść w blokach
  komentarzy `/* ... */` z nagłówkami (`===`, emoji jak 📦🔹🔍📌) i przykładami kodu do uruchomienia.
- `_Exercises_LessonXX_Temat.java` — dokładnie **30 ćwiczeń** jako statyczne klasy zagnieżdżone
  `Exercise01_NazwaXxx` … `Exercise30_NazwaXxx`, każda z:
  - komentarzem `/* 🧪 Zadanie N: ... */` opisującym zadanie (po polsku, konkretne dane wejściowe),
  - pustą metodą `public static void main(String[] args) { }` — **bez rozwiązania**, kursant sam pisze kod
    (ewentualnie ze znacznikiem `// TODO: twój kod tutaj`).

  Poziom trudności rośnie i jest oznaczony nagłówkami sekcji:
  - 🟢 POZIOM 1 – PODSTAWY (1-10)
  - 🟡 POZIOM 2 – ŚREDNI (11-20)
  - 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

Ta konwencja (2 pliki/lekcja, 30 ćwiczeń/plik) obowiązuje we wszystkich rozdziałach i musi być
zachowana przy dodawaniu nowych lekcji lub całych rozdziałów.

## Rozdział _03_collections ("Java - Kolekcje")

Odpowiada kursowi Codenga "Java - Kolekcje". Oryginalny sylabus Codenga (moduły): ArrayList,
LinkedList, Vector, Stack, HashMap, TreeMap, podsumowanie, LinkedHashMap, HashSet, TreeSet,
LinkedHashSet, Hashtable, PriorityQueue, ArrayDeque, mini-projekt.

Nasza wersja jest szersza (23 lekcje) i pokrywa wszystkie te kolekcje, część z nich łączona
tematycznie:
- Vector, Stack, Hashtable, Properties, Enumeration → `Lesson21_LegacyCollections` (jako "stare"
  kolekcje, z rekomendacją nowoczesnych zamienników)
- ArrayDeque → `Lesson18_Deque`
- Reszta (ArrayList, LinkedList, HashMap, TreeMap, LinkedHashMap, HashSet, TreeSet, LinkedHashSet,
  PriorityQueue) ma własne dedykowane lekcje.

Dodatkowo rozdział wykracza poza sylabus Codenga o tematy: iterację, metody kolekcji, Comparator,
Streams (4 lekcje), Optional, Comparable vs Comparator, kolekcje współbieżne, Queue, mapy specjalne.

Stan na 2026-07-03: lekcje 01–05 mają komplet (teoria + 30 ćwiczeń). Lekcje 06–23 mają teorię;
ćwiczenia są uzupełniane.
