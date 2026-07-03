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

Stan na 2026-07-03: wszystkie 23 lekcje (01–23) mają komplet (teoria + 30 ćwiczeń). Rozdział
celowo NIE ma osobnej lekcji "mini projekt" (decyzja użytkownika — zostaje jak jest, 23 lekcje).

## Rozdział _04_io ("Input/Output i praca z plikami")

24 lekcje, na razie tylko teoria (bez ćwiczeń — dochodzą w kolejnym kroku, tak jak to było
w `_03_collections`). Numeracja lekcji nie odpowiada numeracji z oryginalnego sylabusu (tam
tematy były numerowane 206–226 w ramach większej, wspólnej listy tematów kursu) — tu numeracja
zaczyna się od 01 zgodnie z konwencją folderów per-rozdział.

Mapowanie na oryginalny sylabus + dodane przez Claude tematy (oznaczone jako DODANE, bo sylabus
ich nie miał, a uznane za brakujące do kompletu):

1. IOIntroduction (206), 2. FileReaderWriter (207), 3. BufferedReaderWriter (208),
4. BufferedStreams (209), 5. DataStreams (210), 6. PrintWriterAndStream (DODANE),
7. Scanner (211), 8. FileClass (212), 9. PathAndPaths (213), 10. FilesClass (214),
11. RandomAccessFile (DODANE), 12. Charset (215), 13. TryWithResources (216),
14. FileExceptions (217), 15. NioChannelsAndBuffers (DODANE), 16. ObjectSerialization (218),
17. SerialVersionUID (219), 18. TransientKeyword (220), 19. JsonIntro (221), 20. Gson (222),
21. Jackson (223), 22. SerializableVsJson (224), 23. CSV (225), 24. ZIP (226).

Zależności: Jackson jest dostępny przez `spring-boot-starter-web` (transitywnie), Gson dodany
ręcznie do `pom.xml` (`com.google.code.gson:gson`). CSV/ZIP używają tylko czystej biblioteki
standardowej (bez Apache Commons CSV itp. — wspomniane w komentarzach jako alternatywa).
