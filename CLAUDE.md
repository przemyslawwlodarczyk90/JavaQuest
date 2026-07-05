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

## Rozdział _11_buildtools ("Build Tools w Javie – Ant, Maven, Gradle")

30 lekcji, dodane 2026-07-05 jako duży, samodzielny moduł (na życzenie użytkownika — w firmie
używa dużo Anta, więc build toole to nie "dodatek", a fundament). Dołożony na KOŃCU numeracji
jako `_11_buildtools` (ta sama zasada stabilności numeracji co przy `Lesson16_Exceptions` w
`_01_fundamentals` — bez przenumerowywania `_08_sql`/`_09_jdbc`/`_10_dao`).

Historia dwóch rund rozszerzania (ważne, jeśli trzeba będzie coś tu jeszcze zmieniać): pierwotny
brief użytkownika (21 ROZDZIAŁ + końcowy egzamin) dał bazowe 22 lekcje, z Gradle jako tylko
2 lekcje (16-17). Runda 1: użytkownik ocenił Gradle jako zbyt skromny względem Anta (8 lekcji) →
dodano 5 nowych lekcji Gradle, Gradle urósł do 7 (16-22), ogon (porównanie/migracje/praktyka/
troubleshooting/capstone) przesunięty na 23-27. Runda 2: użytkownik zażądał, żeby WSZYSTKIE 3
bloki (Ant/Maven/Gradle) miały ten samą rozszerzony zakres → Maven (miał tylko 5 lekcji) dostał
3 nowe lekcje, urósł do 8 (tyle co Ant), Gradle+ogon przesunięte o kolejne +3. Finalny stan —
**8 lekcji Ant (03-10), 8 lekcji Maven (11-18), 8 lekcji Gradle (19-26)** + 2 lekcje fundamentów
(01-02) + 4 lekcje zbiorcze (27-30) = 30 lekcji razem:

01_WhyBuildTools, 02_JavacJavaJarClasspath, 03_AntBasics, 04_AntProjectStructure, 05_AntClasspath,
06_AntTesting, 07_AntPackaging, 08_AntAdvanced, 09_AntIvy, 10_AntDebugging, 11_MavenBasics,
12_MavenDependencies, 13_MavenPlugins, 14_MavenAdvanced, 15_MavenWebAndDatabase,
16_MavenTestingAndCoverage, 17_MavenPublishing, 18_MavenTroubleshootingAndPerformance,
19_GradleBasics, 20_GradleAdvanced, 21_GradleDependencyManagement, 22_GradleTestingAndCoverage,
23_GradlePluginsEcosystem, 24_GradlePublishing, 25_GradleTroubleshootingAndPerformance,
26_AntMavenGradleComparison, 27_BuildMigrations, 28_BuildToolsInPractice,
29_BuildToolsTroubleshooting, 30_CapstoneBuildLab (= "JavaQuest Build Lab" z brief-u).

Zauważ symetrię między blokami — każdy z 3 narzędzi ma teraz odpowiedniki: podstawy, dependency
management, testing/coverage, plugins/publishing, troubleshooting/performance (Maven i Gradle
ściśle równolegle: 16/17/18 ↔ 22/24/25; Ant pokrywa te tematy inaczej rozłożone w 03-10, bo od
początku był projektowany gęściej ze względu na embedding).

Kluczowy problem projektowy: cała reszta kursu ma `main()`, który faktycznie coś RUNs (woła
Java API). Ant/Maven/Gradle to zewnętrzne narzędzia + pliki XML/Groovy/Kotlin DSL, które nie
mieszczą się w tym wzorcu 1:1. Rozwiązanie — 4 różne strategie wykonania w zależności od lekcji:

- **Lekcje 01–02 (javac/java/jar/classpath)**: w pełni embedowalne, tylko JDK, bez nowej
  zależności — `ToolProvider.getSystemJavaCompiler()`/`javax.tools.JavaCompiler` kompiluje
  wygenerowany kod źródłowy w locie do temp dir, `URLClassLoader`/`ProcessBuilder` go odpala,
  `java.util.jar.JarOutputStream`+`Manifest` buduje i odpala realne JAR-y. Te sztuczki są też
  reużyte w lekcji 29 (troubleshooting), żeby NAPRAWDĘ wywołać `ClassNotFoundException` /
  `NoClassDefFoundError` / `UnsupportedClassVersionError`, a nie tylko o nich mówić.
- **Lekcje 03–10 (Ant, 8 lekcji)**: embedowany PRAWDZIWY silnik Ant w procesie (ten sam wzorzec
  co embedowany Tomcat w `_07_servlets` — `Project`/`ProjectHelper`/`DefaultLogger` z
  `org.apache.tools.ant`). Każda lekcja generuje realny `build.xml` (text block), zapisuje go do
  `Files.createTempDirectory(...)`, konfiguruje `Project`, wykonuje target i wypisuje REALNY
  output Anta. Dzięki temu ćwiczenia z tych lekcji też są w pełni wykonywalne w `main()` (pisz
  kod embedujący Anta), nie tylko opisowe. Wymagało dodania zależności `org.apache.ant:ant` +
  `org.apache.ant:ant-junit` (task `<junit>`, lekcja 06) do `pom.xml`.
- **Lekcje 11–18 (Maven, 8 lekcji)**: Maven 3 nie ma czystego API do embedowania w jednym
  procesie (w odróżnieniu od Anta), a odpalanie `mvnw`/`mvnw.cmd` cross-platform z wewnątrz demo
  byłoby kruche. `main()` generuje realną treść `pom.xml` (także multi-module, lekcja 14) jako
  text block, zapisuje na dysk, wypisuje/omawia strukturę — ale NIE odpala realnego `mvn`.
  Ćwiczenia w tych lekcjach są HYBRYDOWE: `main()` zostaje pusty jak wszędzie, a treść zadania
  (komentarz `🧪 Zadanie N`) prosi o utworzenie realnego projektu i odpalenie prawdziwych komend
  `mvn` w terminalu — to jedyne miejsce w kursie, gdzie zamierzenie część pracy dzieje się poza
  JVM-em demo, bo to dokładnie odzwierciedla realną pracę z Mavenem (a i tak cały ten kurs jest
  budowany Mavenem przez `mvnw.cmd`). Lekcje 16-18 (Testing/Coverage, Publishing,
  Troubleshooting/Performance) dodane w rundzie 2, równoległe tematycznie do Gradle 22/24/25.
- **Lekcje 19–25 (Gradle, 7 lekcji)**: analogicznie do Mavena — generowanie `build.gradle`/
  `settings.gradle`/`libs.versions.toml` jako text block + opis, bez embedowanego silnika Gradle
  (żaden wrapper Gradle NIE został dodany do tego repo — to osobne środowisko, niezwiązane z
  własnym build-em kursu opartym o Maven). Ćwiczenia równie hybrydowe (plik + realne komendy
  `gradle`/`./gradlew` w terminalu).
- **Lekcje 26–30 (porównanie, migracje, praktyka, troubleshooting, capstone)**: koncepcyjne/
  porównawcze — tabele porównawcze i przykładowe configi jako text blocki, a lekcja 29
  (troubleshooting) faktycznie wywołuje klasyczne błędy JVM/build przy pomocy sztuczek z
  lekcji 01–02. Lekcja 30 (capstone, "JavaQuest Build Lab") prowadzi przez budowę tego samego
  mini-projektu w 3 wersjach (Ant/Maven/Gradle) z brief-u użytkownika, z bogatszą częścią
  Gradle/Maven odzwierciedlającą rundy rozszerzeń.

Trzecia nowa zależność w `pom.xml`: `org.apache.ivy:ivy` (lekcja 09, Ant+Ivy) — realne
rozwiązywanie zależności przez Ivy, uderza w prawdziwe Maven Central. Ta sama zasada co w
`_06_networking` (real external interaction): timeout + przyjazny fallback komunikat, jeśli
brak internetu, żeby `main()` i tak kończył się samoistnie w kilka sekund.

Stan na 2026-07-05: rozdział kompletny — wszystkie 30 lekcji mają teorię + 30 ćwiczeń każda,
zweryfikowane pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem uruchomieniowym lekcji
16-18 (`mvnw.cmd exec:java`). Ostatnie brakujące elementy dopisane w tym kroku: ćwiczenia do
Lesson16_MavenTestingAndCoverage oraz kompletne Lesson17_MavenPublishing (distributionManagement,
settings.xml/servers, maven-deploy-plugin, GPG signing, central-publishing-maven-plugin) i
Lesson18_MavenTroubleshootingAndPerformance (-e/-X, dependency:tree conflict resolution,
help:effective-pom, -o/-U, -T równoległe buildy, MAVEN_OPTS) — obie lekcje w tym samym hybrydowym
stylu ćwiczeń co reszta bloku Maven (plik + realne komendy `mvn` w terminalu).

## Rozdziały _08_sql, _09_jdbc, _10_dao ("SQL", "JDBC", "DAO")

Stan na 2026-07-05: wszystkie 3 rozdziały kompletne (teoria + 30 ćwiczeń w każdej lekcji):
`_08_sql` (20 lekcji), `_09_jdbc` (20 lekcji), `_10_dao` (28 lekcji) — łącznie 68 lekcji.
Ćwiczenia dopisane w tym kroku (teoria była już gotowa wcześniej) — cały projekt zweryfikowany
pełną kompilacją (`mvnw.cmd compile`). Wszystkie zadania SQL/JDBC korzystają z bazy H2 in-memory
(`jdbc:h2:mem:...;DB_CLOSE_DELAY=-1`), zgodnie z wzorcem z `_08_sql/Lesson01`. Wyjątek: lekcje
`_09_jdbc/Lesson18_DomainModel` i `Lesson19_Dto` mają czysto obiektowe (bez SQL) ćwiczenia, bo ich
teoria też jest czysto obiektowa; `_10_dao/Lesson25_DatabaseMigrations` (Flyway) ma ćwiczenia
oparte na mechanice plików migracji (`V<wersja>__<opis>.sql`, `flyway_schema_history`) miast na
wzorcu "napisz DAO", bo taka jest specyfika tej lekcji.

## Rozdział _12_hibernate ("Hibernate – ORM i JPA w praktyce")

Dodany 2026-07-05 jako kolejny duży, samodzielny blok (na życzenie użytkownika, bezpośrednia
kontynuacja `_08_sql`/`_09_jdbc`/`_10_dao` — ten sam H2 in-memory, ale przez pryzmat ORM zamiast
ręcznego SQL/JDBC). 30 lekcji, ta sama skala co `_11_buildtools`.

Stan na 2026-07-05: **rozdział kompletny** — wszystkie 30 lekcji mają teorię + 30 ćwiczeń każda,
zweryfikowane pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem uruchomieniowym WSZYSTKICH
30 lekcji (`mvnw.cmd exec:java` dla każdej, zero błędów). W odróżnieniu od `_11_buildtools`, ten
rozdział jest w PEŁNI wykonywalny (`main()` naprawdę tworzy `SessionFactory` na H2 in-memory i
wykonuje operacje — decyzja użytkownika, potwierdzona explicité, żeby zachować spójność z
`_08_sql`/`_09_jdbc`/`_10_dao`, w przeciwieństwie do Mavena/Gradle, gdzie `main()` tylko generował
pliki konfiguracyjne). Ćwiczenia w każdej lekcji wracają do standardowej konwencji kursu (30
statycznych klas zagnieżdżonych `ExerciseNN_...` z opisem zadania w komentarzu i pustym `main()` —
kursant sam pisze kod), tak jak w `_08_sql`/`_09_jdbc`/`_10_dao`.

Ważny techniczny problem napotkany i rozwiązany podczas pisania: domyślna nazwa encji JPA dla
klasy ZAGNIEŻDŻONEJ (nasz wzorzec — encje jako `static class` wewnątrz pliku lekcji) to w tej
wersji Hibernate PEŁNA, BINARNA nazwa Javy (np. `_Lesson18_HqlBasics$Book`, ze znakiem `$`), NIE
prosta nazwa `Book` — przez co zapytania HQL typu `"from Book"` kończyły się
`UnknownEntityException`. Naprawione przez jawne `@Entity(name = "Book")` na KAŻDEJ encji w całym
rozdziale (wymuszone masowo przez skrypt `perl` na już napisanych lekcjach 1-18, potem stosowane
od razu w lekcjach 19-30) — z wyjaśnieniem tej pułapki dopisanym do teorii Lesson04. W REALNYM
projekcie (encje jako zwykłe, NIE zagnieżdżone klasy) ten problem w ogóle nie występuje — to
specyfika sposobu, w jaki zbudowany jest ten kurs.

Inne warte odnotowania decyzje z pisania treści: Lesson26 (blokowanie pesymistyczne) uczciwie
opisuje w komentarzu, że H2 (silnik MVStore/MVCC) NIE honoruje w pełni hinta
`jakarta.persistence.lock.timeout` tak jak zrobiłby to PostgreSQL/MySQL — zademonstrowane empirycznie
(wątek faktycznie czeka na zwolnienie blokady, ale nie przerywa się po zadanym timeout) zamiast
udawać idealne zachowanie, którego H2 nie zapewnia. Lesson24 (cache drugiego poziomu) wymagał
dodania `hibernate-jcache`/`hibernate-envers`/`org.ehcache:ehcache` do `pom.xml` (zweryfikowane
`mvn dependency:resolve`, rozwiązały się na wersjach zarządzanych przez BOM Spring Boota).

Mapowanie lekcja → temat (30 lekcji):
1. OrmIntroduction (impedance mismatch, po co ORM, alternatywy: MyBatis/jOOQ),
2. HibernateArchitecture (SessionFactory/Session, Configuration, ServiceRegistry),
3. ProjectSetupAndConfiguration (zależności Maven, `hibernate.cfg.xml` vs JPA `persistence.xml`,
   `StandardServiceRegistryBuilder` vs `Persistence.createEntityManagerFactory`),
4. FirstEntityAndBasicMapping (`@Entity`/`@Table`/`@Id`/`@Column`, pierwszy zapis/odczyt),
5. PrimaryKeyGeneration (`@GeneratedValue`: IDENTITY/SEQUENCE/TABLE/UUID/assigned),
6. CrudOperations (`persist`/`find`/`merge`/`remove` JPA vs `save`/`get`/`update`/`delete` Session),
7. SessionVsEntityManager (natywne API Hibernate vs standard JPA, kiedy czego używać),
8. Transactions (Transaction API, `EntityTransaction`, granice transakcji),
9. EmbeddableTypes (`@Embeddable`/`@Embedded`, value objects),
10. EnumsAndAttributeConverters (`@Enumerated` ORDINAL/STRING, `@Temporal`, `@Converter`),
11. OneToOneAssociation (uni/bidirectional, `mappedBy`, współdzielony PK vs FK),
12. OneToManyAndManyToOne (bidirectional, `mappedBy`, `orphanRemoval`),
13. ManyToManyAssociation (tabela pośrednicząca, przejście na jawną encję łączącą),
14. CascadeTypes (ALL/PERSIST/MERGE/REMOVE/REFRESH/DETACH, pułapki kaskadowania),
15. FetchTypesAndNPlusOne (LAZY vs EAGER, demo problemu N+1, fetch join jako naprawa),
16. EntityLifecycle (transient/persistent/detached/removed, `@PrePersist` itp.),
17. DirtyCheckingAndFlush (automatyczne dirty checking, `FlushMode`, flush vs commit),
18. HqlBasics (SELECT/WHERE/ORDER BY, parametry),
19. HqlAdvanced (joiny, funkcje agregujące, GROUP BY, podzapytania, projekcje DTO),
20. CriteriaApi (`CriteriaBuilder`, zapytania typowane, wzmianka o metamodelu),
21. NativeSqlQueries (`createNativeQuery`, `@SqlResultSetMapping`),
22. NamedQueries (`@NamedQuery`/`@NamedNativeQuery`),
23. FirstLevelCache (cache sesji, identity map, zasięg per-sesja),
24. SecondLevelCacheAndQueryCache (L2 cache przez `hibernate-jcache`+Ehcache, `@Cacheable`, query cache),
25. OptimisticLocking (`@Version`, `OptimisticLockException`),
26. PessimisticLocking (`LockModeType`, SELECT FOR UPDATE),
27. InheritanceMapping (SINGLE_TABLE/JOINED/TABLE_PER_CLASS, `@MappedSuperclass`),
28. BeanValidationIntegration (Jakarta Bean Validation + Hibernate Validator, walidacja przy persist),
29. HibernateEnvers (audytowanie `@Audited`, odczyt historii rewizji),
30. BestPracticesAndCapstone (typowe pułapki, wydajność, projekt podsumowujący).

Zależności dodane do `pom.xml` (wersje zarządzane przez `spring-boot-starter-parent` BOM, bez
jawnego numeru wersji — zweryfikowane `mvn dependency:resolve`, rozwiązały się na
`hibernate-core`/`hibernate-envers`/`hibernate-jcache` 6.6.11.Final i `ehcache` 3.10.8):
`org.hibernate.orm:hibernate-core`, `org.hibernate.orm:hibernate-envers`,
`org.hibernate.orm:hibernate-jcache`, `org.ehcache:ehcache`. `jakarta.persistence-api` przychodzi
transytywnie z `hibernate-core`. `hibernate-validator` (lekcja 28) już jest obecny transytywnie
przez istniejącą zależność `spring-boot-starter-validation` — NIE dodawaj go ponownie.
