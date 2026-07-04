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

## Rozdział _01_fundamentals ("Podstawy Javy")

16 lekcji (00–15) + `Lesson16_Exceptions` dopisana 2026-07-04 (decyzja użytkownika: brakowało
porządnej, ogólnej lekcji o wyjątkach — try-catch-finally, checked vs unchecked, throw/throws,
własne wyjątki, łańcuchowanie/cause, stack trace; dotąd wyjątki były tylko przy okazji
`_04_io/Lesson14_FileExceptions`, który jest wąsko scoped do I/O). Dodana na KOŃCU numeracji jako
Lesson16 (a nie wstawiona np. po Lesson04_Loops), żeby nie przenumerowywać istniejących 16 lekcji —
świadomy kompromis między kolejnością dydaktyczną a stabilnością numeracji.

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

24 lekcje, komplet (teoria + 30 ćwiczeń każda), stan na 2026-07-03. Numeracja lekcji nie
odpowiada numeracji z oryginalnego sylabusu (tam
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

Ćwiczenia (30/lekcję) dopisane 2026-07-03 — rozdział kompletny (teoria + ćwiczenia).

## Rozdział _05_multithreading ("Multithreading / Wątki w Javie")

37 lekcji, na razie tylko teoria (ćwiczenia dochodzą w kolejnym kroku, tak jak przy poprzednich
rozdziałach). Oryginalny sylabus numerował tematy 190–233 (44 tematy) w ramach wspólnej listy
tematów kursu — tu numeracja lekcji zaczyna się od 01. Kilka bardzo wąskich podtematów sylabusu
scalono w jedną lekcję (żeby uniknąć rozdrabniania), a jeden moduł dodano od siebie:

- 193+194 (Runnable jako klasa anonimowa / jako lambda) → `Lesson04_RunnableAnonymousAndLambda`
- 196–202 (cykl życia + wszystkie 6 stanów Thread.State: NEW/RUNNABLE/BLOCKED/WAITING/
  TIMED_WAITING/TERMINATED) → jedna lekcja `Lesson06_ThreadLifecycleAndStates`
- 219+220 (Concurrent collections + BlockingQueue) → `Lesson24_ConcurrentCollectionsAndBlockingQueue`,
  celowo NIE powtarza API z `_03_collections/Lesson20_ConcurrentCollections`, tylko pokazuje kąt
  poprawności wątkowej (dlaczego zwykłe kolekcje zawodzą współbieżnie) i wzorzec producer-consumer
  z poison pill
- `Lesson20_Synchronizers` (DODANE — CountDownLatch, CyclicBarrier, Semaphore) — sylabus tego nie
  miał, ale to standardowe narzędzia z java.util.concurrent często spotykane w praktyce, brakowało
  ich obok Lock/ReadWriteLock

Pełne mapowanie lekcja → temat z sylabusu:
1. ThreadsIntroduction (190), 2. ThreadClass (191), 3. Runnable (192),
4. RunnableAnonymousAndLambda (193,194), 5. ThreadBasicMethods (195),
6. ThreadLifecycleAndStates (196-202), 7. RaceCondition (203), 8. VisibilityProblem (204),
9. Atomicity (205), 10. ThreadSafety (206), 11. Synchronized (207), 12. Monitor (208),
13. CriticalSection (209), 14. Volatile (210), 15. WaitNotifyNotifyAll (211),
16. SpuriousWakeup (212), 17. AtomicClasses (213), 18. LockAndReentrantLock (214),
19. ReadWriteLock (215), 20. Synchronizers (DODANE), 21. ExecutorService (216),
22. CallableAndFuture (217), 23. ScheduledExecutorService (218),
24. ConcurrentCollectionsAndBlockingQueue (219,220), 25. Deadlock (221), 26. Livelock (222),
27. Starvation (223), 28. Interrupt (224), 29. DaemonThreads (225), 30. ThreadLocal (226),
31. ForkJoinPool (227), 32. CompletableFuture (228), 33. VirtualThreads (229),
34. ThreadDebugging (230), 35. SafeThreadTermination (231), 36. BestPractices (232),
37. CommonMistakes (233).

Zasada bezpieczeństwa demo (ważne przy rozszerzaniu tego rozdziału): KAŻDA lekcja musi kończyć
`main()` samoistnie w ciągu kilku sekund — żadnych realnych deadlocków/nieskończonych pętli.
Zagrożenia (deadlock/livelock/starvation) demonstrowane przez wątki daemon + ograniczony czasowo
`join()`/pętlę, nigdy przez faktyczne zawieszenie JVM. Każdy ExecutorService musi być jawnie
zamykany (`shutdown()`+`awaitTermination`).

Stan na 2026-07-04: rozdział kompletny (teoria + 30 ćwiczeń) dla wszystkich 37 lekcji, zweryfikowane
pełną kompilacją projektu (`mvn compile` / `mvnw.cmd compile`).

## Rozdział _06_networking ("Programowanie sieciowe")

14 lekcji, komplet (teoria + 30 ćwiczeń każda), stan na 2026-07-04. Pierwszy z dwóch rozdziałów
poświęconych sieci/webowi (drugi to `_07_servlets`) — świadomie rozdzielone na dwa osobne rozdziały
zamiast jednego (decyzja użytkownika: "duży blok" = osobny kurs Codenga, a materiał sieciowy +
servletowy razem był policzony na ~49 lekcji, zbyt dużo na jeden rozdział). Blok "Przejście do
Spring Boot" (adnotacje @Controller/@RestController itd.) świadomie NIE wchodzi w skład żadnego
z tych dwóch rozdziałów — Spring Boot będzie osobnym, ostatnim rozdziałem całego kursu.

Mapowanie lekcja → temat z pierwotnego sylabusu (numeracja 272-285 w ramach wspólnej listy tematów):
1. NetworkingIntroduction (272), 2. InetAddress (273), 3. Socket (274), 4. SocketWhois (275),
5. SocketHttpDownload (276), 6. ServerSocket (277), 7. ServerSocketMultithreaded (278), 8. URL (279),
9. URLConnection (280), 10. HttpURLConnection (281), 11. HttpProtocol (282),
12. JsonOverNetwork (283), 13. XmlParsing (284), 14. HtmlUnit (285).

Decyzje techniczne ważne przy rozszerzaniu tego rozdziału:
- Lekcje 4 (WHOIS) i 5 (surowe HTTP przez Socket) oraz 14 (HtmlUnit) świadomie łączą się z REALNYMI
  zewnętrznymi hostami (whois.iana.org, example.com) — to jest sens tych lekcji. Zawsze z
  `setSoTimeout`/timeoutem i try-catch z czytelnym komunikatem "brak internetu", żeby `main()`
  kończył się samoistnie w kilka sekund nawet bez połączenia z siecią.
- Lekcje 9, 10, 12 (URLConnection, HttpURLConnection, JSON przez sieć) celowo NIE zależą od
  prawdziwego internetu — używają lokalnego `com.sun.net.httpserver.HttpServer` (wbudowany w JDK,
  port 0) jako serwera testowego, dla niezawodności/szybkości demo.
- Lekcja 3 (Socket) jest samowystarczalna: lokalny `ServerSocket` na wątku daemon + klient w tym
  samym `main()`, bez zależności od sieci — prawdziwe zewnętrzne połączenia są zarezerwowane dla
  lekcji 4/5.
- Zależność `org.htmlunit:htmlunit:4.4.0` dodana do `pom.xml` (lekcja 14 to jedyne jej użycie).
- Ta sama zasada bezpieczeństwa demo co w `_05_multithreading`: `main()` musi kończyć się
  samoistnie w kilka sekund (bounded accept(), CountDownLatch zamiast sleepów, wątki
  daemon/joinowane z timeoutem, zawsze zamykane sockety).

## Rozdział _07_servlets ("Servlety – Java Web bez Spring Boot")

19 lekcji, komplet (teoria + 30 ćwiczeń każda), stan na 2026-07-04. Drugi z dwóch rozdziałów
sieciowych (patrz `_06_networking` wyżej dla kontekstu podziału). Uczy surowego Servlet API
(bez Springa) — Spring Boot będzie osobnym, ostatnim rozdziałem kursu.

Mapowanie lekcja → temat z pierwotnego sylabusu (numeracja 286-304):
1. ServletApiIntroduction (286), 2. ServletContainers (287), 3. ServletProjectSetup (288),
4. HttpServlet (289), 5. HttpServletRequest (290), 6. HttpServletResponse (291),
7. GetAndPost (292), 8. OtherHttpMethods (293), 9. FormParameters (294), 10. Cookies (295),
11. HttpSession (296), 12. ServletConfig (297), 13. ServletContext (298), 14. Filters (299),
15. Listeners (300), 16. ServletAnnotations (301), 17. ForwardAndRedirect (302),
18. FileUpload (303), 19. JSP (304).

Kluczowa decyzja techniczna: żaden zewnętrzny kontener (Tomcat/Jetty) nie jest instalowany —
każda lekcja uruchamia **embedded Apache Tomcat programowo wewnątrz `main()`**. To działa bez
dodawania żadnej nowej zależności, bo `tomcat-embed-core:10.1.39` (razem z pełnym API
`jakarta.servlet.*`, Jakarta EE 10) jest już obecne transytywnie przez istniejącą zależność
`spring-boot-starter-web`. Kanoniczny wzorzec (powtarzany w każdej lekcji, wyjaśniony szczegółowo
w Lekcji 3):

```java
Tomcat tomcat = new Tomcat();
tomcat.setBaseDir(Files.createTempDirectory("lessonXX").toString());
tomcat.getConnector().setPort(0); // 0 = system wybiera wolny port
Context context = tomcat.addContext("", null);
Tomcat.addServlet(context, "nazwa", new MojServlet());
context.addServletMappingDecoded("/sciezka", "nazwa");
try {
    tomcat.start();
    int port = tomcat.getConnector().getLocalPort();
    // żądanie(a) przez java.net.http.HttpClient na http://localhost:<port>/...
} finally {
    tomcat.stop();
    tomcat.destroy();
}
```

Inne ważne decyzje przy tej implementacji:
- Lekcja 8 (PUT/DELETE/PATCH): `HttpServlet` obsługuje `doPut`/`doDelete` przez zwykłe przesłonięcia,
  ale NIE ma `doPatch()` — PATCH wymaga ręcznego przesłonięcia `service()` i sprawdzenia
  `request.getMethod()`.
- Lekcje 10 (Cookies) i 11 (HttpSession) wymagają `HttpClient` zbudowanego z
  `.cookieHandler(new CookieManager())`, inaczej ciasteczka/sesja nie przetrwają między kolejnymi
  żądaniami — zweryfikowane działającym demo (te same ID sesji między żądaniami, invalidate()
  tworzy nowe ID).
- Lekcja 16 (adnotacje @WebServlet/@WebFilter/@WebListener): bez skanowania adnotacji w gołym
  embedded Tomcacie (wymagałoby pełnej struktury WEB-INF/classes) — serwlet ma adnotację DLA
  DOKUMENTACJI, ale rejestracja nadal odbywa się ręcznie przez `Tomcat.addServlet`, z jawnym
  komentarzem tłumaczącym tę rozbieżność.
- Lekcja 18 (upload plików): `MultipartConfigElement` ustawiany na `Wrapper` (zwróconym przez
  `Tomcat.addServlet`) PRZED `tomcat.start()`; klient buduje treść `multipart/form-data` ręcznie
  bajt po bajcie (`HttpClient` nie ma wbudowanego wsparcia dla multipart).
- Lekcja 19 (JSP): celowo BEZ realnego silnika JSP (wymagałoby `tomcat-embed-jasper` + fizycznych
  plików `.jsp` na dysku) — czysto koncepcyjna lekcja z przykładami jako Java text blocks, zgodnie
  z ramowaniem "JSP dziś rzadziej używane".
- Ta sama zasada bezpieczeństwa demo co w `_05_multithreading`/`_06_networking`: `main()` musi
  kończyć się samoistnie w kilka sekund, port zawsze 0, Tomcat zawsze zatrzymywany w `finally`.
