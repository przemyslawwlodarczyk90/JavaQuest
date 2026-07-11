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

## Rozdział _13_libraries ("Biblioteki – popularne narzędzia w ekosystemie Javy")

32 lekcje, stan na 2026-07-09: **rozdział kompletny** — wszystkie 32 lekcje mają teorię + 30
ćwiczeń każda, zweryfikowane pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem
uruchomieniowym kilkunastu reprezentatywnych lekcji. Rozdział przegląda popularne biblioteki
trzecie w ekosystemie Javy: Lombok (3-5), Apache Commons Lang3/IO/Collections4 (6-8), Guava (9-11),
OkHttp (12-14), SLF4J/Logback (15-17), Guice (18-20), MapStruct (21-22), Apache POI (23-24), Jsoup
(25-26), Caffeine (27-28), Picocli (29-30), SnakeYaml (31-32). Numeracja ma lukę na Lesson14 w
pierwotnym stanie repo (12, 13, [brak 14], 15...) — uzupełniona jako `Lesson14_OkHttpStreamingAndTesting`
(streaming/multipart/timeouty + testowanie `MockWebServer`), zgodnie z podpowiedziami zostawionymi
w Lesson12 i komentarzem w `pom.xml` przy zależności `mockwebserver`. Napotkano też 3 zduplikowane,
puste katalogi lekcji z wcześniejszych sesji (`Lesson17_MdcAndAppenders` vs
`Lesson17_MdcAndLoggingBestPractices`, `Lesson26_JsoupWebScraping` vs `Lesson26_JsoupAdvancedScraping`,
`Lesson28_CaffeineAdvancedEvictionAndStats` vs `Lesson28_CaffeineLoadingAndAsyncCache`) —
rozwiązane przez zachowanie wersji zgodnej z istniejącymi forward-referencjami w już napisanej
teorii i usunięcie pustego duplikatu.

Wszystkie zależności biblioteczne dodane do `pom.xml` z jawnymi wersjami (Lombok w scope
`provided`, MapStruct + `mapstruct-processor` w scope `provided` dla annotation processingu,
OkHttp + `mockwebserver`, Guava, Apache Commons Lang3/IO/Collections4, Guice, Apache POI + POI-OOXML,
Jsoup, Caffeine, Picocli) — SLF4J/Logback i SnakeYAML NIE zostały dodane jawnie, bo są już
dostępne transytywnie przez `spring-boot-starter` (zweryfikowane `mvn dependency:tree`).

Mapowanie lekcja → temat (32 lekcje): 1. WhyLibraries, 2. ChoosingAndAddingDependencies,
3. LombokBasics, 4. LombokConstructorsAndBuilder, 5. LombokAdvancedAndPitfalls, 6. CommonsLang3,
7. CommonsIO, 8. CommonsCollections4, 9. GuavaImmutableCollections, 10. GuavaMultimapMultisetBiMap,
11. GuavaPreconditionsAndCache, 12. OkHttpBasics, 13. OkHttpAsyncAndInterceptors,
14. OkHttpStreamingAndTesting, 15. WhySlf4jNotSystemOut, 16. LogbackConfiguration,
17. MdcAndLoggingBestPractices, 18. WhyDependencyInjection, 19. GuiceBasics,
20. GuiceAdvancedModulesAndScopes, 21. MapStructBasics, 22. MapStructAdvancedMappings,
23. ApachePoiWritingExcel, 24. ApachePoiReadingAndStyling, 25. JsoupParsingHtml,
26. JsoupAdvancedScraping, 27. CaffeineBasics, 28. CaffeineLoadingAndAsyncCache, 29. PicocliBasics,
30. PicocliSubcommandsAndValidation, 31. SnakeYamlBasics, 32. YamlToObjectMapping.

Techniczne decyzje warte odnotowania: Lesson16 (konfiguracja Logbacka) generuje `logback.xml` jako
Java text block i ładuje go programowo przez `JoranConfigurator`, ZAMIAST dotykać realnego
`src/main/resources/logback.xml` projektu — ten sam wzorzec "embeduj i naprawdę uruchom" co w
`_11_buildtools`. Lesson14 (OkHttp) uruchamia zarówno lokalny `com.sun.net.httpserver.HttpServer`,
jak i prawdziwy `MockWebServer`, oba na porcie 0, bez zależności od realnej sieci.

## Rozdział _14_advancedjava ("Zaawansowane mechanizmy języka Java")

30 lekcji, dodane 2026-07-09 jako kolejny duży, samodzielny blok (na życzenie użytkownika, który
zaproponował bazową listę 18 tematów i poprosił o rozbudowę do skali `_11_buildtools`/`_12_hibernate`
— potwierdzone explicité przez `AskUserQuestion`: "Duża rozbudowa do ~30 lekcji"). Nazwa pakietu
`_14_advancedjava` (bez podkreślnika między słowami części opisowej) celowo NIE jest dosłownym
`_14_advanced_java` zaproponowanym przez użytkownika — ujednolicona z konwencją wszystkich
pozostałych rozdziałów (`_11_buildtools`, `_12_hibernate`, `_13_libraries` też nie mają
podkreślników w nazwie opisowej).

Stan na 2026-07-09: **rozdział kompletny** — wszystkie 30 lekcji mają teorię + 30 ćwiczeń każda,
zweryfikowane pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem uruchomieniowym kilkunastu
reprezentatywnych lekcji. Rozdział jest w pełni core-Javowy — ŻADNYCH nowych zależności do
`pom.xml` (generics, lambdy, adnotacje, refleksja, sealed classes, pattern matching, moduły — to
wszystko czysty JDK). Projekt celuje w Java 21 (`pom.xml`: `<java.version>21</java.version>`,
`<release>21</release>`), co jest kluczowe dla lekcji 21 (pattern matching w switch + record
patterns, JEP 441/440, sfinalizowane dopiero w Javie 21).

Przed napisaniem rozdziału zweryfikowano brak nakładania się z rozdziałami 1-12: generics, method
referencje, refleksja, sealed classes i JPMS nie istniały NIGDZIE w kursie; funkcyjne
interfejsy/lambdy/wyrażenia switch/pattern matching instanceof/niezmienność miały tylko krótkie,
pomocnicze akapity wewnątrz innych lekcji (`_02_oop/Lesson08_Interfaces`,
`_01_fundamentals/Lesson03_Conditionals`, `_02_oop/Lesson06_Polymorphism`,
`_02_oop/Lesson10_FinalKeyword`) — lekcje tego rozdziału jawnie nawiązują do tych wcześniejszych
akapitów i pogłębiają temat, zamiast go powtarzać.

Rozdział podzielony na 8 tematycznych klastrów:
1-7. Generics (WhyGenerics → generyczne klasy/metody → bounded types → wildcardy → PECS/wariancja →
type erasure → dobre praktyki/pułapki),
8-11. Programowanie funkcyjne (interfejsy funkcyjne → lambdy → referencje do metod → wbudowane
interfejsy z `java.util.function`),
12-14. Adnotacje (wbudowane → własne `@interface` + meta-adnotacje → `RetentionPolicy` +
prawdziwe przetwarzanie w czasie kompilacji przez `JavaCompiler`/`AbstractProcessor`),
15-18. Refleksja i mechanizmy dynamiczne (podstawy `Class`/`Method`/`Field` → przypadki
użycia/ryzyka → dynamiczne proxy `java.lang.reflect.Proxy` → `MethodHandles`),
19-22. Nowoczesny system typów (sealed classes → pattern matching `instanceof` → pattern matching
w `switch` + record patterns → wyrażenia switch),
23-25. Wnioskowanie typów i niezmienność (`var` → niezmienność → obronne kopiowanie),
26-28. Odkrywanie usług i modularność (`ServiceLoader`/SPI → podstawy JPMS → zaawansowane JPMS:
`opens`/`uses`/`provides`),
29-30. Podsumowanie (dobre praktyki „kiedy czego użyć" → kapsztonowy projekt łączący
sealed+pattern matching+generics+funkcyjne interfejsy+adnotacje+refleksję+niezmienność).

Kluczowy problem techniczny — lekcje 14 (adnotacje, przetwarzanie w czasie kompilacji) i 26-28
(ServiceLoader/SPI, JPMS) wymagają REALNEGO zewnętrznego mechanizmu (kompilator, osobny moduł,
osobny classloader), którego nie da się po prostu "napisać w `main()`" tak jak resztę rozdziału.
Rozwiązanie — ten sam wzorzec "embeduj i naprawdę uruchom", który już wcześniej sprawdził się w
`_11_buildtools` (`ToolProvider.getSystemJavaCompiler()` + `Files.createTempDirectory` +
`ProcessBuilder`): Lesson14 buduje i uruchamia prawdziwy `AbstractProcessor` przez
`CompilationTask.setProcessors(...)` bez `META-INF/services` (processor ustawiany programowo);
Lesson26 kompiluje interfejs + 2 implementacje do katalogu tymczasowego, dopisuje prawdziwy plik
`META-INF/services/...` i ładuje przez `URLClassLoader` + `ServiceLoader.load(Class, ClassLoader)`;
Lesson27-28 kompilują i URUCHAMIAJĄ prawdziwy wielomodułowy mini-projekt (`javac
--module-source-path` + `java --module-path`) przez `ProcessBuilder`, WŁĄCZAJĄC realne błędy
(`IllegalAccessError`/`InaccessibleObjectException`/"package is not visible") jako faktyczny,
przechwycony output podprocesu — nie udawane komunikaty. WAŻNE: cały projekt `javaQuest` pozostaje
na classpath (bez `module-info.java` w `src/main/java`) — świadoma decyzja architektoniczna
odnotowana w teorii Lesson28, żeby nie złamać kompilacji pozostałych 13 rozdziałów; JPMS jest
demonstrowany WYŁĄCZNIE w osobnych katalogach tymczasowych.

Napotkany i naprawiony błąd kompilacji: `Lesson29_AdvancedLanguageBestPractices` miał niejawne
wnioskowanie typów generycznych, które nie kompilowało się przez konflikt diamentu (`<>`) z
generyczną metodą `Comparator.naturalOrder()` przekazaną jako argument konstruktora — naprawione
przez jawne `Comparator.<String>naturalOrder()`.

Mapowanie lekcja → temat (30 lekcji): 1. GenericsIntroduction, 2. GenericClassesAndMethods,
3. BoundedTypes, 4. WildcardsExtendsSuper, 5. VarianceAndPecs, 6. TypeErasure,
7. GenericsBestPracticesAndPitfalls, 8. FunctionalInterfaces, 9. LambdaExpressions,
10. MethodReferences, 11. BuiltInFunctionalInterfaces, 12. Annotations, 13. CustomAnnotations,
14. AnnotationRetentionAndProcessing, 15. ReflectionBasics, 16. ReflectionUseCasesAndRisks,
17. DynamicProxies, 18. MethodHandles, 19. SealedClasses, 20. PatternMatchingInstanceof,
21. PatternMatchingSwitchAndRecordPatterns, 22. SwitchExpressions, 23. VarAndTypeInference,
24. Immutability, 25. DefensiveCopying, 26. ServiceLoaderAndSpi, 27. ModulesJpmsBasics,
28. ModulesAdvanced, 29. AdvancedLanguageBestPractices, 30. CapstoneAdvancedJava.

## Rozdział _15_jvm_internals ("JVM, pamięć i wydajność")

20 lekcji, dodane 2026-07-10. Wyjściowy brief użytkownika miał 12 lekcji (JdkJreJvm →
JvmTuningBasics); rozbudowany do 20 po jawnym pytaniu użytkownika o skalę — odpowiedź: "rozbudować
jeśli są dobre tematy, ale nie na siłę do 30" (świadomie NIE dociągnięty do skali
`_11_buildtools`/`_12_hibernate`/`_13_libraries`/`_14_advancedjava` (27-32 lekcje), bo tyle
dobrze uzasadnionych, nienadmuchanych tematów faktycznie się znalazło).

Rozdział jest w pełni core-Javowy (jak `_14_advancedjava`) — ŻADNYCH nowych zależności do
`pom.xml` (wszystko z `java.lang.management`, `com.sun.management`, `jdk.jfr`, `javax.tools`,
`ProcessBuilder` jest w samym JDK). Celowo NIE duplikuje istniejących, wprowadzających lekcji:
`_01_fundamentals/Lesson00_JavaPlatformBasics` (JDK/JRE/JVM wstęp), `Lesson10_HeapAndStack`
(heap/stack koncepcyjnie), `Lesson14_GarbageCollector` (GC koncepcyjnie + Weak/Soft/Phantom) —
ten rozdział jest ich pogłębieniem (prawdziwe mechanizmy JVM, prawdziwe API introspekcji, prawdziwe
generowanie plików diagnostycznych), Lesson01 jawnie się do nich odsyła jednym zdaniem zamiast
powtarzać.

Mapowanie lekcja → temat (20 lekcji): 1. JdkJreJvmAndSpecification (JVM spec vs
HotSpot/GraalVM/OpenJ9), 2. CompilationAndBytecode (javac→.class→JVM, realny `javap -c -v`),
3. ClassLoadingMechanics (hierarchia loaderów, delegacja, fazy loading/linking/initialization),
4. CustomClassLoaders (własny `ClassLoader`, zagadka "ta sama klasa, dwa różne `Class` obiekty"),
5. ClasspathVsModulepath (classpath vs JPMS module path z perspektywy classloadingu/enkapsulacji),
6. HeapStackMetaspace (`MemoryMXBean`/`MemoryPoolMXBean`, `StackOverflowError`),
7. ReferenceTypesAndStringPool (Weak/Soft/Phantom + `ReferenceQueue` + `Cleaner`, String pool/
`intern()`), 8. GarbageCollectionFoundations (hipoteza generacyjna, GC roots, mark-sweep-compact,
`GarbageCollectorMXBean`), 9. GarbageCollectorAlgorithms (Serial/Parallel/G1/ZGC/Shenandoah
porównanie, realne child JVM z różnymi `-XX:+Use*GC`), 10. G1GcDeepDive (regiony, remembered sets,
mixed collections, `-Xlog:gc`), 11. LowLatencyCollectors (ZGC/Shenandoah głębiej, fallback gdy
build JDK ich nie wspiera), 12. GcTuningAndLogging (flagi tuningowe, `-Xlog:gc` unified logging),
13. JitCompilerBasics (C1/C2, tiered compilation, warm-up demo, `-XX:+PrintCompilation`),
14. EscapeAnalysisAndInlining (scalar replacement, inlining, `-XX:+PrintInlining`),
15. MemoryLeaksInJava (statyczne kolekcje, listenery, `ThreadLocal` w puli wątków, non-static inner
class), 16. HeapDumpBasics (`HotSpotDiagnosticMXBean.dumpHeap` — realny `.hprof`),
17. ThreadDumpBasics (`ThreadMXBean.dumpAllThreads`/`findDeadlockedThreads`, realny bezpieczny
deadlock), 18. JavaFlightRecorderBasics (`jdk.jfr.Recording`/`Event` — realny `.jfr`,
`jfr summary`), 19. ProfilingBasics (mini sampling profiler przez okresowe
`Thread.getAllStackTraces()`), 20. JvmTuningAndBestPracticesCapstone (przegląd flag z całego
rozdziału + kapsztonowe demo łączące heap dump + thread dump + JFR dla jednego obciążenia).

Kluczowe decyzje techniczne:
- Ten sam wzorzec "embeduj i naprawdę uruchom" co w `_11_buildtools`/`_14_advancedjava`:
  `ToolProvider.getSystemJavaCompiler()` + `Files.createTempDirectory` do kompilacji w locie,
  `ProcessBuilder` do odpalania PRAWDZIWEGO child procesu `java`/`javac`/`javap`/`jfr` z konkretnymi
  flagami JVM (Lesson02 realny `javap -c -v`, Lesson05 realny JPMS `IllegalAccessException`,
  Lesson09-13 realne child JVM z flagami GC/JIT, Lesson18 realny `jfr summary`).
- WAŻNIEJSZA niż w poprzednich rozdziałach zasada bezpieczeństwa demo: każdy `ProcessBuilder`
  używa `process.waitFor(10, TimeUnit.SECONDS)` + `process.destroyForcibly()` przy timeout
  (NIE bezterminowego `waitFor()` jak część starszych lekcji w `_14_advancedjava`) — bo flagi typu
  `-Xlog:gc`/`-XX:+PrintCompilation` na child JVM mają większe ryzyko nietypowego zachowania niż
  zwykła kompilacja/uruchomienie. Realny (ale bezpieczny) deadlock w Lesson17 i Lesson20 powtarza
  dokładnie wzorzec z `_05_multithreading/Lesson25_Deadlock`: wątki `daemon` + `join(2000)`,
  program kończy się normalnie mimo trwającego zakleszczenia.
- Lesson11 (ZGC/Shenandoah): na maszynie deweloperskiej (OpenJDK 25.0.2) Shenandoah faktycznie
  jest niedostępny w tym buildzie — lekcja realnie ćwiczy i pokazuje przyjazny fallback ("ten
  kolektor nie jest dostępny w tym buildzie JDK") zamiast udawać wsparcie, którego nie ma.
  Ten sam wzorzec fallbacku co przy braku internetu w `_06_networking`.
- Lesson16/18 generują prawdziwe pliki `.hprof`/`.jfr` do `Files.createTempDirectory` i CELOWO
  ich nie usuwają na końcu (w przeciwieństwie do innych tymczasowych katalogów w kursie) — chodzi
  o to, żeby kursant mógł je faktycznie otworzyć w VisualVM/JMC/Eclipse MAT; ścieżka i rozmiar
  pliku są wypisywane na konsolę.
- `_TableOfContents.java` zaktualizowany o nowy `Chapter("_15_jvm_internals", ...)` na końcu listy
  `ROZDZIALY` (stabilność numeracji — ta sama zasada co przy `Lesson16_Exceptions` w
  `_01_fundamentals`).

Stan na 2026-07-10: rozdział kompletny (teoria + 30 ćwiczeń w każdej z 20 lekcji), zweryfikowany
pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem uruchomieniowym reprezentatywnej próbki
lekcji (Lesson02 realny bytecode, Lesson09 realne logi GC z 3 różnych kolektorów, Lesson17 realna
detekcja deadlocku, Lesson20 kapsztonowe demo) — wszystkie kończą się samoistnie w kilka sekund.

## Rozdział _16_clean_code ("Clean Code i refactoring")

Rozpoczęty 2026-07-10. Wyjściowy brief użytkownika miał 16 lekcji (WhatIsCleanCode →
CodeReviewBasics). Po pytaniu o skalę (jak przy `_15_jvm_internals`) użytkownik wybrał "umiarkowaną
rozbudowę" — bez naciągania do 30, tylko dobrze uzasadnione dodatki. Finalny, ZATWIERDZONY program
to **22 lekcje**. Stan na 2026-07-10: **rozdział kompletny** — wszystkie 22 lekcje mają teorię + 30
ćwiczeń każda, zweryfikowane pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem uruchomieniowym
wszystkich 15 lekcji dopisanych w tym kroku (01-03/07-09/13/14/18 były już gotowe wcześniej):

1. WhatIsCleanCode, 2. Naming, 3. Comments (DODANE — klasyczny temat "dobre vs złe komentarze" z
   Clean Code, brakowało go w brief-ie), 4. MethodsAndFunctions, 5. Formatting (DODANE —
   formatowanie pionowe/poziome kodu), 6. ClassesAndResponsibilities,
   7. SingleResponsibilityPrinciple, 8. OpenClosedPrinciple, 9. LiskovSubstitutionPrinciple,
   10. InterfaceSegregationPrinciple, 11. DependencyInversionPrinciple,
   12. CouplingCohesionAndLawOfDemeter (DODANE), 13. DryKissYagni, 14. CodeSmells,
   15. RefactoringBasics, 16. RefactoringCatalog (DODANE — katalog konkretnych technik: Extract
   Method/Class, Replace Conditional with Polymorphism, Introduce Parameter Object itp.),
   17. ExceptionDesign, 18. NullHandling, 19. ImmutabilityInPractice,
   20. StaticAnalysisTools (DODANE — planowane osadzenie PRAWDZIWYCH narzędzi PMD/SpotBugs
   analizujących wygenerowany kod źródłowy, analogicznie do wzorca embedowania Anta w
   `_11_buildtools` — UWAGA dla kontynuacji: dokładne współrzędne Maven dla PMD to
   `net.sourceforge.pmd:pmd-core`+`net.sourceforge.pmd:pmd-java`, dla SpotBugs
   `com.github.spotbugs:spotbugs` — dla Checkstyle współrzędne NIE zostały jeszcze zweryfikowane,
   sprawdź w Maven Central przed dodaniem do `pom.xml`, nie zgaduj), 21. LegacyCodeAndTechnicalDebt
   (DODANE), 22. CodeReviewBestPracticesAndCapstone (capstone — ta sama konwencja co ostatnia
   lekcja w `_11_buildtools`/`_12_hibernate`/`_14_advancedjava`/`_15_jvm_internals`).

Ten rozdział jest inny technicznie od `_14_advancedjava`/`_15_jvm_internals` — to głównie
demonstracje "zły kod vs dobry kod" (przed/po refaktoryzacji) jako zagnieżdżone klasy
`BadExample`/`GoodExample` z wywołaniami metod i wyjaśnieniem w komentarzu, NIE wymaga
`ProcessBuilder`/child JVM/exotic JDK API (poza Lesson20, który świadomie osadza PRAWDZIWE
narzędzia statycznej analizy, wzorem `_11_buildtools`). Brak potrzeby zasady "main() kończy się w
kilka sekund" w sensie deadlocków/GC — tu ryzyko jest inne: pokusa pisania WYŁĄCZNIE tekstu bez
kompilowalnego kodu. Zasada do pilnowania: KAŻDY blok teorii nadal musi być realnym,
kompilującym się kodem Java (klasy `BadXxx`/`GoodXxx` z prawdziwymi polami/metodami, wywoływanymi
i drukującymi wynik), nie tylko komentarzem.

Lesson20 (StaticAnalysisTools) embeduje NAPRAWDĘ PMD 7.13.0 i SpotBugs 4.9.3 w `main()`, zweryfikowane
uruchomieniowo (nie tylko kompilacyjnie) — API obu bibliotek różni się od starszych wersji/tutoriali
w internecie, więc przed dalszą edycją tej lekcji sprawdź realne sygnatury (`javap`), nie zgaduj:
- **PMD 7.x** (przeprojektowane API względem PMD 6): `PMDConfiguration` + `PmdAnalysis.create(config)`
  (try-with-resources) + `pmd.files().addFile(Path)` (auto-wykrywa język po rozszerzeniu `.java`) +
  `pmd.newRuleSetLoader().loadFromResource("category/java/bestpractices.xml")` (ruleset wbudowany w
  jar `pmd-java`, BEZ potrzeby internetu) + `pmd.addRuleSet(...)` +
  `pmd.performAnalysisAndCollectReport()` zwracające `Report` z `getViolations(): List<RuleViolation>`
  (`violation.getRule().getName()`, `getDescription()`, `getLocation().getStartLine()`).
- **SpotBugs 4.9.3** (klasyczne pakiety `edu.umd.cs.findbugs.*`, bez zmian od ery FindBugs): wymaga
  NAJPIERW skompilowania kodu do `.class` (`ToolProvider.getSystemJavaCompiler()`, jak w
  `_11_buildtools`/`_14_advancedjava`) — SpotBugs analizuje BYTECODE, nie źródło. Wzorzec: `new
  Project()` + `project.addFile(katalogZKlasami)` + `new BugCollectionBugReporter(project)` +
  `setPriorityThreshold(Priorities.LOW_PRIORITY)` + `try (FindBugs2 engine = new FindBugs2())` +
  `engine.setBugReporter(...)`/`setProject(...)`/`setDetectorFactoryCollection(DetectorFactoryCollection.instance())`
  + **KRYTYCZNE, łatwe do przeoczenia**: `engine.setUserPreferences(UserPreferences.createDefaultUserPreferences())`
  MUSI być wywołane przed `finishSettings()`/`execute()`, inaczej `NullPointerException` w
  `FindBugs.isDetectorEnabled` (brak `UserPreferences` nie jest ustawiany domyślnie). Wynik:
  `bugReporter.getBugCollection()` (implementuje `Iterable<BugInstance>`, `bug.getType()`/`getMessage()`).
- Przykładowy "brudny" kod demo (`unusedField` nieużywane, `a == b` zamiast `.equals()` na
  Stringach, martwy zapis `x=5; x=10;`) DAJE deterministyczne, zweryfikowane wyniki: PMD zgłasza 5
  naruszeń (`UnusedPrivateField`, `UnusedLocalVariable`, 2× `UnusedAssignment`, `SystemPrintln`),
  SpotBugs zgłasza 2 (`ES_COMPARING_PARAMETER_STRING_WITH_EQ`, `URF_UNREAD_FIELD`) — jeśli przyszła
  aktualizacja wersji PMD/SpotBugs zmieni te liczby, to nie błąd w kodzie lekcji, tylko zmiana w
  zestawie reguł danej wersji.


## Rozdział _17_architecture ("Architektura aplikacji Java")

Rozpoczęty 2026-07-10, na wyraźne życzenie użytkownika (rozdział + wyjściowa lista 15 lekcji podana
przez użytkownika). Kontynuacja `_16_clean_code` — tamten rozdział uczył czystego kodu NA POZIOMIE
metody/klasy, ten uczy organizacji kodu NA POZIOMIE CAŁEJ APLIKACJI (warstwy, granice modułów,
kierunek zależności, granice transakcji/walidacji/błędów/cache'a). Świadomie BEZ Springa — Spring
Boot to OSTATNI, osobny rozdział całego kursu (potwierdzone explicité przez użytkownika w tej samej
rozmowie: "SPRING BEDZIE NA SAM KONIEC DOPIERO").

Po zapytaniu o skalę (ten sam wzorzec co przy `_15_jvm_internals`/`_16_clean_code`, przez
`AskUserQuestion`) użytkownik wybrał umiarkowaną rozbudowę z 15 do **20 lekcji** — dodano 5 dobrze
uzasadnionych tematów (ADR, bounded contexts/DDD-lite, API versioning, caching architecture,
event-driven komunikacja między modułami), rozmieszczonych w logicznych miejscach oryginalnej
listy, NIE doklejonych na końcu (w odróżnieniu od wzorca "dopisz na końcu, nie przenumerowuj" z
`_01_fundamentals/Lesson16_Exceptions`/`_15_jvm_internals` — tu bezpiecznie przenumerowano całość,
bo ŻADNA lekcja nie miała jeszcze napisanej treści, więc koszt przenumerowania był zerowy).

Stan na 2026-07-10: **rozdział kompletny** — wszystkie 20 lekcji mają teorię + 30 ćwiczeń każda,
zweryfikowane pełną kompilacją (`mvnw.cmd compile`) oraz smoke-testem uruchomieniowym kilkunastu
reprezentatywnych lekcji (wszystkie kończą się poprawnie, bez wyjątków). Lesson20 (kapszton) to w
pełni DZIAŁAJĄCA (nie tylko opisowa) symulacja modularnego monolitu "Platforma Zapisów na Kursy" —
2 moduły (`Courses`, `Enrollments`) + moduł `Notifications` jako subskrybent zdarzeń, uruchamialna
i zweryfikowana pod kątem 6 scenariuszy (sukces x2, wypełniony limit, zły format, nieznaleziony
kurs, nieoczekiwany wyjątek) — każdy scenariusz daje DOKŁADNIE oczekiwany wynik.

Finalna lista 20 lekcji (nazwy folderów już zarejestrowane w `_TableOfContents.java` — 5 nowych
tematów oznaczonych NIŻEJ jako DODANE):
1. WhyArchitectureMatters, 2. ArchitectureDecisionRecords (DODANE), 3. LayeredArchitecture,
4. ControllerServiceRepository, 5. DomainModelVsAnemicModel, 6. BoundedContextsAndDddLite (DODANE),
7. DtoEntityMapper, 8. ApiVersioningAndCompatibility (DODANE), 9. PackageByLayerVsPackageByFeature,
10. DependencyDirection, 11. HexagonalArchitectureIntro, 12. PortsAndAdapters,
13. TransactionBoundaries, 14. CachingArchitecture (DODANE), 15. ValidationArchitecture,
16. ErrorHandlingArchitecture, 17. ModularMonolith,
18. EventDrivenCommunicationBetweenModules (DODANE), 19. WhenMicroservicesMakeSense,
20. ArchitectureCapstone.

Planowana treść merytoryczna każdej lekcji (roboczy plan do rozwinięcia przy pisaniu — nie
ostateczny, ale punkt wyjścia, żeby nie zaczynać od zera po ewentualnej przerwie):
1. **WhyArchitectureMatters** — koszt zmiany rośnie z czasem/rozmiarem systemu bez architektury;
   architektura = decyzje TRUDNE do odwrócenia później; cele dobrej architektury (testowalność,
   niezależność od frameworka/UI/bazy danych — nawiązanie do Uncle Boba "Clean Architecture").
2. **ArchitectureDecisionRecords** (DODANE) — ADR (Michael Nygard, 2011) jako lekki format
   dokumentowania WAŻNYCH decyzji architektonicznych (kontekst, decyzja, konsekwencje) — dlaczego
   "dlaczego" jest równie ważne co sam kod; naturalne rozwinięcie Lesson01.
3. **LayeredArchitecture** — klasyczne warstwy prezentacja/logika biznesowa/dostęp do danych,
   POGŁĘBIENIE `_10_dao/Lesson02_LayeredArchitecture` (tamta lekcja była wąsko o DAO, ta jest o
   całej aplikacji) — jawnie odesłać, nie powielać.
4. **ControllerServiceRepository** — kanoniczna trójka (świadomie BEZ Spring Boota; symulacja przez
   zwykłe klasy Javy), odpowiedzialność każdej warstwy, typowy błąd (logika biznesowa w
   kontrolerze, repozytorium przeciekające do kontrolera).
5. **DomainModelVsAnemicModel** — anemiczny model domenowy (encje = worki na dane, cała logika w
   serwisach, krytykowany przez Fowlera) vs bogaty model domenowy (zachowanie w encjach).
6. **BoundedContextsAndDddLite** (DODANE) — strategiczny DDD (Eric Evans) w pigułce: bounded
   context jako granica, w której dany model/język ma jednoznaczne znaczenie; ten sam termin
   biznesowy (np. "Produkt") może znaczyć co innego w różnych kontekstach (Katalog vs Magazyn) —
   naturalne rozwinięcie Lesson05, pomost do Lesson17 (ModularMonolith).
7. **DtoEntityMapper** — DLACZEGO nie eksponować encji wprost na granicy API (sprzężenie kontraktu
   z schematem bazy, nadmiarowe dane) — POGŁĘBIENIE `_09_jdbc/Lesson19_Dto`/`Lesson20_Mapper` na
   poziomie architektonicznym, nie mechaniki mapowania.
8. **ApiVersioningAndCompatibility** (DODANE) — jak zmieniać kontrakt API bez łamania istniejących
   klientów (wersjonowanie w URL/nagłówku, dodawanie pól jako zmiana kompatybilna, usuwanie/zmiana
   typu jako niekompatybilna) — naturalne rozwinięcie Lesson07 (DTO to właśnie ten kontrakt).
9. **PackageByLayerVsPackageByFeature** — `controller/service/repository` (poziomo) vs
   `orders/users/payments` (pionowo, per funkcja) — kompromisy obu.
10. **DependencyDirection** — Dependency Rule (Clean/Onion Architecture) — zależności ZAWSZE do
    środka, w stronę domeny; bezpośrednie powiązanie z DIP (`_16_clean_code/Lesson11`).
11. **HexagonalArchitectureIntro** — Ports and Adapters (Alistair Cockburn) — "hexagon" to tylko
    metafora (dowolna liczba "boków"), rdzeń domenowy niezależny od technologii.
12. **PortsAndAdapters** — konkretna implementacja: porty jako interfejsy, adaptery jako
    implementacje (DB/HTTP/CLI), korzyść dla testowalności (podmiana adaptera na testowy).
13. **TransactionBoundaries** — GDZIE powinna żyć granica transakcji (warstwa serwisu, nie
    kontroler ani DAO) — POGŁĘBIENIE `_10_dao/Lesson19_UnitOfWork` na poziomie architektonicznym.
14. **CachingArchitecture** (DODANE) — GDZIE w architekturze powinien żyć cache (najczęściej
    warstwa serwisu/repozytorium, nie kontroler), inwalidacja przy zapisie, cache jako przekrój
    poprzeczny (cross-cutting concern) analogicznie do granic transakcji z Lesson13; nawiązanie do
    `_13_libraries/Lesson27-28` (Caffeine) na poziomie mechaniki — tu nacisk na UMIEJSCOWIENIE.
15. **ValidationArchitecture** — warstwowa strategia walidacji: granica API (DTO) vs niezmienniki
    domeny (encja/konstruktor) vs reguły biznesowe (serwis) — co gdzie powinno być sprawdzane.
16. **ErrorHandlingArchitecture** — scentralizowana obsługa błędów NA GRANICY aplikacji, spójny
    kształt odpowiedzi błędu — POGŁĘBIENIE `_16_clean_code/Lesson17_ExceptionDesign` (tam: projekt
    pojedynczego wyjątku; tu: architektura obsługi błędów w całym systemie).
17. **ModularMonolith** — moduły w JEDNYM wdrożeniu z wymuszonymi granicami (bez rozproszenia
    sieciowego mikroserwisów) — "najlepsze z obu światów" dla wielu projektów; opiera się na
    bounded contexts z Lesson06.
18. **EventDrivenCommunicationBetweenModules** (DODANE) — jak moduły w monolicie (Lesson17) mogą
    komunikować się LUŹNO przez zdarzenia domenowe (domain events, prosty in-memory publisher/
    listener) zamiast bezpośrednich wywołań metod między modułami — redukuje sprzężenie między
    bounded contexts (Lesson06); naturalny pomost do Lesson19 (mikroserwisy komunikują się
    analogicznie, ale przez sieć).
19. **WhenMicroservicesMakeSense** — koszty systemu rozproszonego, kiedy NIE warto skakać do
    mikroserwisów, prawo Conwaya.
20. **ArchitectureCapstone** — projekt łączący WSZYSTKIE poprzednie 19 lekcji w 1 spójnej,
    mniejszej aplikacji (np. moduł zamówień) — architektura warstwowa/heksagonalna + bounded
    contexts + DTO/Mapper/wersjonowanie + granice transakcji/cache/walidacji/błędów + komunikacja
    zdarzeniowa między modułami, zademonstrowane razem.

Kluczowa decyzja techniczna do utrzymania przy pisaniu: ten rozdział jest w PEŁNI konceptualny/
architektoniczny jak `_16_clean_code` — ŻADNYCH nowych zależności do `pom.xml`, ŻADNEGO Springa
(Spring Boot to OSTATNI, osobny rozdział całego kursu — architektura tu musi być demonstrowana
CZYSTĄ Javą: zagnieżdżone klasy statyczne udające warstwy/moduły, `HashMap` jako "baza w pamięci",
interfejsy jako porty, implementacje jako adaptery — dokładnie styl `BadExample`/`GoodExample`
znany z `_16_clean_code`). Każda lekcja nadal musi być REALNYM, kompilującym się i uruchamialnym
kodem (nie tylko tekstem w komentarzach) — ta sama zasada co w `_16_clean_code`.

Pułapka napotkana wielokrotnie przy pisaniu (Lesson04 i Lesson15) — WARTO ZAPAMIĘTAĆ na przyszłość:
`record Foo(boolean success, ...)` NIE MOŻE mieć statycznej metody fabrykującej o TEJ SAMEJ nazwie
co komponent rekordu (np. `static Foo success()` koliduje z auto-generowanym akcesorem
`success()`) — daje błąd kompilacji `invalid accessor method in record` w miejscu deklaracji, oraz
mylący błąd `incompatible types` we WSZYSTKICH miejscach wywołania akcesora. Rozwiązanie: nazwij
fabrykę inaczej niż komponent (np. `ok()` zamiast `success()`/`valid()`) — zastosowane w
`Lesson04_ControllerServiceRepository.TaskResult` i `Lesson15_ValidationArchitecture.ValidationResult`.

Kapszton (Lesson20) demonstruje modularny monolit "Platforma Zapisów na Kursy": moduł `Courses`
(bogata encja `Course` z niezmiennikiem pojemności, port `CourseRepositoryPort` + adapter in-memory
+ dekorator cache'ujący, publiczne API `CoursesModuleApi`) i moduł `Enrollments` (DTO wejściowe,
`EnrollStudentUseCase` orkiestrujący 3 poziomy walidacji z Lesson15 + granicę transakcji z Lesson13,
`BoundaryErrorHandler` z Lesson16, publikacja zdarzenia `EnrollmentCreated` z Lesson18) — moduł
`Notifications` subskrybuje zdarzenie BEZ wiedzy modułu Enrollments o jego istnieniu. 6 scenariuszy
w `main()`: 2 udane zapisy, wyczerpanie limitu miejsc (409), zły format e-maila (400), nieznaleziony
kurs (404), i celowy `NullPointerException` demonstrujący bezpieczny fallback (500 bez wycieku
szczegółów) — wszystkie zweryfikowane uruchomieniowo, nie tylko kompilacyjnie.

Stan na 2026-07-10: `_17_architecture` **kompletny i zweryfikowany** — 20/20 lekcji z teorią +
30 ćwiczeń, `mvnw.cmd compile` przechodzi bez błędów (uruchomione z `$env:JAVA_HOME =
"C:\Users\kapit\.jdks\openjdk-25.0.2"` — na tej maszynie `JAVA_HOME` NIE jest ustawione globalnie
w środowisku powłoki, trzeba je ustawić ręcznie w każdej nowej sesji przed `mvnw.cmd`).

## Rozdział _18_rest_api — KOMPLETNY (stan na 2026-07-10)

**`_18_rest_api` ("REST API i projektowanie HTTP") jest w PEŁNI ukończony: 20/20 lekcji, każda z
teorią + 30 ćwiczeniami, każda skompilowana ORAZ uruchomieniowo zweryfikowana
`mvnw.cmd exec:java` — zero błędów.** Lesson17 (PostmanBasics) użyła hybrydowego stylu (jak
Maven/Gradle w `_11_buildtools`) — `main()` uruchamia prawdziwy lokalny serwer + generuje realne
pliki JSON kolekcji/środowiska Postmana jako text blocki, ćwiczenia proszą o realne kroki w
zewnętrznym Postmanie. Lesson18 (OpenApiSwaggerIntro) generuje realną specyfikację OpenAPI 3.0
jako text block ORAZ prawdziwy, refleksyjny generator schematu z rekordu Java — zawiera zapowiedź
`springdoc-openapi-starter-webmvc-ui` dla przyszłego rozdziału Spring Boot (patrz notatka niżej).
Lesson20 (kapszton "JavaQuest Tasks API") łączy w 1 działającym mini-API: zasoby/metody/statusy
(Lesson03-05), spójny JSON (Lesson06/08), stronicowanie+filtrowanie (Lesson10), ETag+If-Match
(Lesson11), błędy RFC 7807 + walidacja collect-all (Lesson12/13), klucz idempotencji (Lesson15) i
rate limiting (Lesson16) — zweryfikowane end-to-end przez 6 scenariuszy w `main()`.

**`_19_security_basics` jest w PEŁNI ukończony (stan na 2026-07-11): 21/21 lekcji, każda z
teorią + 30 ćwiczeniami, cały projekt skompilowany (`mvnw.cmd compile`) ORAZ każda z 4 ostatnio
dopisanych lekcji (18-21) uruchomieniowo zweryfikowana `mvnw.cmd exec:java` — zero błędów.**
Lesson01-17 były już gotowe z wcześniejszej sesji (ta notatka w CLAUDE.md była nieaktualna —
błędnie sugerowała, że ukończona jest tylko Lesson01; w praktyce Lesson01-17 miały już komplet
teorii i ćwiczeń, zweryfikowano to przy starcie tej sesji przez odczyt zawartości plików, nie tylko
istnienia folderów). W tej sesji dopisano brakujące Lesson18-21:

- **Lesson18_SecretsManagement** — rozwinięcie `_10_dao/Lesson13_EnvironmentVariables` (zmienne
  środowiskowe to "krok 1"): maskowanie sekretu w `toString()`, wzorzec klienta menedżera sekretów
  (Vault/AWS Secrets Manager) z cache'em i TTL (`CachingSecretStore`), prosty regexowy skaner
  zahardkodowanych sekretów (wzorem git-secrets/TruffleHog).
- **Lesson19_SecureLoggingAndAuditing** — czego NIGDY nie logować, log injection (wstrzyknięcie
  `\n` fałszujące wpis w logu) + sanityzacja, REALNY plik dziennika audytu przez
  `java.util.logging.FileHandler` (ten sam wzorzec "generuj prawdziwy plik" co `.hprof`/`.jfr` w
  `_15_jvm_internals`), oraz dziennik audytu z ŁAŃCUCHEM SKRÓTÓW SHA-256 (tamper-evident) —
  zweryfikowane uruchomieniowo, że modyfikacja jednego wpisu bez przeliczenia hasha WYKRYWA
  manipulację (`verifyChain` zwraca `false`).
- **Lesson20_DependencyAndSupplyChainSecurity** — parsuje REALNY `pom.xml` TEGO projektu (DOM,
  `disallow-doctype-decl` jak w Lesson15) i generuje z niego mini-SBOM z faktycznej listy
  zależności (nie przykładowych danych), plus koncepcje: typosquatting, dependency confusion,
  pinowanie wersji vs zakresy. Świadomie NIE wywołuje realnego `mvn dependency:tree` (zbyt
  kosztowne/kruche w demo) — w odróżnieniu od Ant w `_11_buildtools`, Maven nie ma czystego API do
  embedowania w procesie.
- **Lesson21_OwaspTop10OverviewAndCapstone** — mapowanie 10 kategorii OWASP Top 10 (2021) na
  konkretne lekcje rozdziału (jako `Map<String,String>` wypisywana programowo, nie tylko tekst w
  komentarzu); JAWNIE przyznana luka: A10 SSRF nie ma własnej lekcji w tym rozdziale (wyjaśnione
  krótko koncepcyjnie, bez pełnej implementacji — świadoma decyzja, żeby nie rozdymać rozdziału).
  Kapszton: REALNY `HttpServer` z endpointami `/login` (walidacja → BCrypt → wystawienie JWT) i
  `/admin/report` (weryfikacja JWT → RBAC → nagłówki bezpieczeństwa), audytowany przez łańcuch
  skrótów z Lesson19 — 7 scenariuszy w `main()` (sukces, złe hasło, pusty input, autoryzowany
  dostęp, 403 przy braku uprawnień, 401 brak tokenu, 401 zmanipulowany token), wszystkie
  zweryfikowane uruchomieniowo z oczekiwanymi kodami statusu.

Drobna pułapka napotkana i naprawiona: w Lesson20 przypadkiem wpisany 1 znak z polskim
diakrytykiem (`RÓWNOLEGLE`) w pliku pisanym celowo w konwencji ASCII-only (jak cały `_18_rest_api`
i teraz `_19_security_basics`) spowodował "krzaczek" w wyjściu terminala PowerShell — poprawione na
`ROWNOLEGLE`. Ten rozdział (w odróżnieniu od wcześniejszej notatki dla `_18_rest_api`) też został
konsekwentnie napisany ASCII-only w `println`/komentarzach, mimo że nie jest to wymagana konwencja
całego repo (patrz sprostowanie w sekcji "Pułapki techniczne napotkane przy pisaniu `_18_rest_api`"
niżej) — utrzymano spójność wewnątrz całego rozdziału `_19_security_basics`.

### Pułapki techniczne napotkane przy pisaniu `_18_rest_api` — WARTO PAMIĘTAĆ przy `_19_security_basics`

- **Demo oparte na timerach/refill (np. rate limiting, token bucket)** jest z natury zależne od
  czasu — jeśli 2 sekcje demo dzielące tego samego "klienta"/stan są rozdzielone innym demo z
  `Thread.sleep`, stan może się zdążyć zmienić między nimi i zepsuć założony rezultat (wystąpiło w
  Lesson16: `demonstratePerClientIndependence` pierwotnie zakładała, że `client-B` z wcześniejszej
  sekcji jest wciąż wyczerpany — po dodaniu 1.1s sleep w sekcji między nimi już nie był).
  Rozwiązanie: użyj ŚWIEŻEGO stanu i ustaw go BEZPOŚREDNIO przed sprawdzeniem, zamiast polegać na
  stanie z odległej wcześniejszej sekcji.
- **Literał `*/*` (wildcard Accept/media type) wewnątrz bloku komentarza `/* ... */` PRZEDWCZEŚNIE
  GO ZAMYKA** (bo `*/` to token zamykający) — cała reszta bloku staje się wtedy nielegalnym kodem,
  dając mylące błędy kompilacji daleko od prawdziwej przyczyny (`illegal start of type`,
  `unclosed string literal`). Rozwiązanie: w komentarzach pisz `*\/*` (backslash łamie token `*/`,
  nie ma innego znaczenia składniowego w komentarzu) — wewnątrz String literałów (`"*/*"` w kodzie
  wykonywalnym, np. `System.out.println`) NIE ma tego problemu, tam `*/` jest zwykłym tekstem.
  Mniej prawdopodobne w `_19_security_basics`, ale warto pamiętać przy dowolnym temacie
  wspominającym gwiazdki/wildcardy w komentarzach.
- Weryfikacja KAŻDEJ lekcji = 2 kroki: `mvnw.cmd -q compile` (kompilacja całego projektu) ORAZ
  `mvnw.cmd -q exec:java "-Dexec.mainClass=<pelna.klasa._LessonXX_Nazwa>"` (faktyczne uruchomienie
  — sprawdza że demo NIE rzuca wyjątków w runtime, nie tylko że się kompiluje). `JAVA_HOME` NIE
  jest ustawione globalnie w powłoce na tej maszynie — przed `mvnw.cmd` w KAŻDEJ nowej sesji
  PowerShell trzeba ustawić `$env:JAVA_HOME = "C:\Users\kapit\.jdks\openjdk-25.0.2"` (Bash tool ma
  inny, nieskonfigurowany `JAVA_HOME` — używaj PowerShell do `mvnw.cmd`, nie Bash).
- SPROSTOWANIE własnej wcześniejszej notatki: polskie znaki diakrytyczne NIE są zakazaną
  konwencją w tym repo — występują w wielu istniejących lekcjach we WSZYSTKICH rozdziałach
  (`_01`...`_17`), włącznie z `System.out.println(...)`. Mimo to `_18_rest_api` został
  konsekwentnie napisany BEZ nich (ASCII-only w println/komentarzach) — nie dlatego, że to
  wymagana konwencja repo, ale bo raz napotkany przypadek zniekształconego znaku w outpucie
  terminala (najpewniej kosmetyczna usterka renderowania konsoli PowerShell przy przechwytywaniu
  `mvnw.cmd`, nie realny błąd w kodzie) skłonił do zachowania spójności wewnątrz całego rozdziału.
  Przy pisaniu `_19_security_basics` MOŻNA pisać naturalną polszczyzną (zgodnie z resztą repo) —
  ale jeśli zauważysz podobne "krzaczki" w outpucie weryfikacyjnym, to nieszkodliwy artefakt
  wyświetlania, nie powód do paniki; i tak warto to sprostować dla czytelności, jak robiono w `_18`.

Oba rozdziały to kontynuacja `_17_architecture` — świadomie WCIĄŻ bez Springa (Spring Boot to
OSTATNI, osobny rozdział całego kursu, potwierdzone wielokrotnie przez użytkownika). REST API i
bezpieczeństwo są uczone czystą Javą (serwery embedowane przez `com.sun.net.httpserver.HttpServer`/
`HttpsServer`, klient przez `java.net.http.HttpClient`) — dokładnie ten sam "embeduj i naprawdę
uruchom" duch co w `_06_networking`/`_07_servlets`/`_11_buildtools`/`_14_advancedjava`/
`_15_jvm_internals`, ale bez zależności od Tomcata (nie jest tu potrzebny — REST API na poziomie
projektowania nie wymaga pełnego kontenera serwletów).

Obie listy tematów wyjściowo podał użytkownik; za jego zgodą ("jeśli uważasz że trzeba rozbudować,
to zrób") rozdziały zostały umiarkowanie rozbudowane (ten sam wzorzec co przy
`_15_jvm_internals`/`_16_clean_code`/`_17_architecture`) — nowe tematy oznaczone niżej jako DODANE.

### _18_rest_api ("REST API i projektowanie HTTP") — 20 lekcji (rozbudowane z 16)

1. HttpDeepDive, 2. RestIntroduction (+ krótkie wprowadzenie do HATEOAS/Richardson Maturity Model,
   bez osobnej lekcji — nie na tyle bogaty temat w praktyce codziennych REST API), 3. ResourcesAndEndpoints,
   4. HttpMethods, 5. StatusCodes, 6. RequestResponseBody, 7. ContentNegotiation (DODANE — nagłówki
   Accept/Content-Type, media types, wersjonowane media types jako alternatywa dla Lesson14),
   8. JsonApiDesign, 9. PathVariablesAndQueryParams, 10. PaginationSortingFiltering,
   11. HttpCachingAndConditionalRequests (DODANE — ETag/Last-Modified/Cache-Control/If-None-Match;
   ŚWIADOMIE NIE duplikuje `_17_architecture/Lesson14_CachingArchitecture`, który jest o TYM, GDZIE
   w architekturze aplikacji powinien żyć cache — ta lekcja jest o mechanice cache'owania na
   poziomie protokołu HTTP, jawnie się odsyła do Lesson14 zamiast powtarzać), 12. ErrorResponseDesign,
   13. ValidationErrors, 14. Versioning, 15. Idempotency, 16. RateLimitingAndThrottling (DODANE —
   429, nagłówki `X-RateLimit-*`/`Retry-After`, koncepcja token bucket), 17. PostmanBasics,
   18. OpenApiSwaggerIntro, 19. RestVsRpcVsGraphQL (DODANE — pozycjonuje REST wśród alternatyw,
   naturalny pomost przed podsumowaniem), 20. RestApiBestPracticesAndCapstone (best practices +
   kapszton połączone w jedną ostatnią lekcję — ta sama konwencja co ostatnia lekcja w
   `_12_hibernate`/`_14_advancedjava`/`_15_jvm_internals`/`_16_clean_code`/`_17_architecture`).

Planowane decyzje techniczne (do zweryfikowania/dopracowania przy pisaniu, nie ostateczne):
- ŻADNA nowa zależność do `pom.xml` nie powinna być potrzebna — cały rozdział da się zrobić samym
  JDK (`com.sun.net.httpserver.HttpServer` + `java.net.http.HttpClient`), tak jak już zrobiono w
  `_06_networking/Lesson09-10,12` i `_13_libraries/Lesson14`.
- Lesson17 (PostmanBasics) — Postman to zewnętrzne narzędzie GUI, nie da się go wywołać z `main()`.
  HYBRYDOWY wzorzec ćwiczeń jak w blokach Maven/Gradle z `_11_buildtools` (opis + realne kroki w
  Postmanie do wykonania przez kursanta), `main()` może za to wystawić lokalny endpoint (port 0),
  na którym kursant faktycznie poćwiczy w Postmanie.
- Lesson18 (OpenApiSwaggerIntro) — bez `springdoc`/nowej zależności; `main()` generuje i wypisuje
  realną specyfikację OpenAPI 3.0 (YAML/JSON jako text block) opisującą endpointy z wcześniejszych
  lekcji, ten sam wzorzec "generuj prawdziwy plik konfiguracyjny" co Maven/Gradle w `_11_buildtools`.
  WAŻNA UWAGA (zgłoszona przez użytkownika 2026-07-10): w Spring Boot user normalnie generuje
  Swagger/OpenAPI PRAWIE bezobsługowo przez samo dodanie 1 zależności do `pom.xml`:
  `org.springdoc:springdoc-openapi-starter-webmvc-ui` (auto-skanuje `@RestController`/mapping
  adnotacje przez Spring MVC dispatcher i wystawia `/v3/api-docs` + `/swagger-ui.html`) — TEGO
  NIE da się tu embedować naprawdę, bo wymaga żywego kontekstu Spring MVC (którego ten rozdział
  świadomie NIE ma). Lesson18 powinien to jawnie WSPOMNIEĆ jako zapowiedź ("w Spring Boot ten sam
  efekt osiągniesz 1 zależnością, bez ręcznego pisania YAML") i odesłać do ostatniego,
  osobnego rozdziału Spring Boot na końcu kursu, GDZIE dopiero `springdoc-openapi-starter-webmvc-ui`
  powinien zostać naprawdę dodany do `pom.xml` i zademonstrowany działająco z prawdziwymi
  kontrolerami. Współrzędna artefaktu podana przez użytkownika z pamięci — ZWERYFIKUJ dokładną
  najnowszą wersję (`${springdoc.version}`) w Maven Central, kiedy przyjdzie czas pisać rozdział
  Spring Boot, nie zgaduj.
- Ta sama zasada bezpieczeństwa demo co w `_06_networking`/`_07_servlets`: port zawsze 0, serwer
  zawsze zatrzymywany w `finally`, `main()` kończy się samoistnie w kilka sekund, bez zależności od
  realnego internetu (w odróżnieniu od `_06_networking`, tu nie ma dobrego powodu do łączenia się z
  prawdziwymi zewnętrznymi hostami).

### _19_security_basics ("Podstawy bezpieczeństwa aplikacji") — 21 lekcji (rozbudowane z 13)

1. AuthenticationVsAuthorization, 2. PasswordHashing, 3. BCrypt, 4. SessionsAndCookies,
   5. JwtIntroduction, 6. OAuth2AndOpenIdConnectIntro (DODANE — delegowana autoryzacja, authorization
   code flow koncepcyjnie, gdzie JWT pasuje jako format tokenu), 7. AuthorizationPatternsAndRbac
   (DODANE — RBAC/ABAC, sprawdzanie uprawnień, zasada najmniejszych uprawnień; pogłębienie
   Lesson01 poza samą definicję), 8. HttpsTlsBasics, 9. Cors, 10. Csrf, 11. Xss,
   12. SecurityHeaders (DODANE — CSP/X-Frame-Options/HSTS/X-Content-Type-Options; naturalne
   uzupełnienie Xss/Csrf), 13. SqlInjectionDeepDive (POGŁĘBIENIE `_09_jdbc/Lesson14_SqlInjection`
   — tamta lekcja to wprowadzenie na poziomie JDBC, ta idzie głębiej: UNION-based, blind
   boolean-based, second-order injection — jawnie się odsyła zamiast powtarzać),
   14. InsecureDeserialization (DODANE, na wyraźną prośbę użytkownika o dalszą rozbudowę bloku
   security — klasyczna kategoria OWASP, POGŁĘBIENIE `_04_io/Lesson16_ObjectSerialization` od
   strony ataku: niebezpieczeństwo `ObjectInputStream.readObject()` na danych od niezaufanego
   klienta, koncepcja "gadget chain" (Apache Commons Collections, ysoserial) BEZ faktycznego
   uruchamiania złośliwego payloadu, obrona przez `ObjectInputFilter` (JEP 290)),
   15. XxeAndXmlExternalEntityAttacks (DODANE — POGŁĘBIENIE `_06_networking/Lesson13_XmlParsing`
   od strony ataku: `DocumentBuilderFactory` z domyślnymi ustawieniami pozwala na wczytanie
   zewnętrznych encji/plików lokalnych przez DTD, realne demo podatnego i zabezpieczonego parsera
   `setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)`),
   16. PathTraversalAndFileUploadSecurity (DODANE — `../../../etc/passwd` w nazwie pliku,
   "zip slip" jako specyficzny przypadek przy rozpakowywaniu ZIP, POGŁĘBIENIE
   `_07_servlets/Lesson18_FileUpload` i `_04_io/Lesson24_ZIP` od strony bezpieczeństwa: walidacja
   znormalizowanej ścieżki przez `Path.normalize()`/`startsWith` przed zapisem),
   17. InputValidation (było 14), 18. SecretsManagement (było 15),
   19. SecureLoggingAndAuditing (DODANE — nielogowanie sekretów/PII, audit trail zdarzeń
   bezpieczeństwa, naturalne uzupełnienie Lesson18), 20. DependencyAndSupplyChainSecurity
   (DODANE — podatne zależności tranzytywne, koncepcja SBOM, `mvn dependency:tree` pod kątem
   bezpieczeństwa — nawiązanie do `_11_buildtools`), 21. OwaspTop10OverviewAndCapstone (przegląd +
   kapszton łączący hashowanie/JWT/walidację/nagłówki bezpieczeństwa w jednym małym, zabezpieczonym
   demo-endpoincie — ta sama konwencja "ostatnia lekcja = best practices + capstone" co w innych
   rozdziałach).

Świadomie NIE dodano osobnej lekcji o brute-force/blokadzie konta po nieudanych logowaniach (blisko
związana z Lesson01/Lesson04) ani osobnej lekcji o narzędziach SAST/DAST (częściowo już pokryte
przez `_16_clean_code/Lesson20_StaticAnalysisTools` — PMD/SpotBugs; tu wystarczy krótkie
nawiązanie w Lesson20/21) — żeby nie rozdymać rozdziału ponad potrzebę.

Planowane decyzje techniczne (do zweryfikowania/dopracowania przy pisaniu, nie ostateczne):
- **BCrypt (Lesson03)**: wymaga nowej zależności. Plan: `org.mindrot:jbcrypt` (mała, samodzielna
  implementacja BCrypt BEZ ciągnięcia całego Spring Security) zamiast
  `spring-security-crypto` — świadomie unikamy jakiejkolwiek zależności `spring-security-*` przed
  ostatnim rozdziałem kursu. **NIE dodawaj współrzędnych na pamięć — zweryfikuj dokładną, aktualną
  wersję w Maven Central przed edycją `pom.xml`**, dokładnie ta sama zasada ostrożności co przy
  PMD/SpotBugs w `_16_clean_code/Lesson20` i Checkstyle (tam jeszcze niezweryfikowany).
- **JWT (Lesson05)**: wymaga nowej zależności. Plan: `io.jsonwebtoken:jjwt-api` +
  `io.jsonwebtoken:jjwt-impl` + `io.jsonwebtoken:jjwt-jackson` (JJWT — integruje się z Jacksonem
  już obecnym transytywnie przez `spring-boot-starter-web`). Tak samo: zweryfikuj wersję w Maven
  Central przed dodaniem, nie zgaduj.
- **HttpsTlsBasics (Lesson08)**: ambitny plan — realny, samopodpisany certyfikat wygenerowany
  programowo LUB przez wywołanie prawdziwego `keytool` przez `ProcessBuilder` (ten sam wzorzec co
  wywoływanie `javac`/`javap`/`jfr` w `_11_buildtools`/`_15_jvm_internals`), żeby postawić
  faktyczny `com.sun.net.httpserver.HttpsServer` i połączyć się do niego prawdziwym TLS handshake
  z `HttpClient` (wymaga custom `TrustManager`/truststore po stronie klienta, bo certyfikat jest
  samopodpisany). Jeśli okaże się zbyt kruche/niestabilne przy pisaniu — fallback: lekcja
  częściowo opisowa (koncepcja handshake/certyfikatów) + realne demo TYLKO na warstwie HTTP
  nagłówków (`Strict-Transport-Security` itp.), bez pełnego TLS.
- **OAuth2AndOpenIdConnectIntro (Lesson06)**: docelowo NIE tylko opisowa — plan: uproszczony,
  lokalny "authorization server" + "resource server" jako dwa `HttpServer` na porcie 0 w tym samym
  `main()`, żeby naprawdę przejść przez authorization code flow i pokazać wymianę kodu na token.
  Jeśli zbyt złożone na jedną lekcję — fallback: opisowa lekcja z sekwencją kroków jako tekst +
  diagram ASCII, bez pełnej implementacji.
- Reszta rozdziału (Cors/Csrf/Xss/SecurityHeaders/SqlInjectionDeepDive) — realne demo przez
  `com.sun.net.httpserver.HttpServer` (np. faktyczna obsługa preflight OPTIONS dla CORS, faktyczne
  odbicie niezescapowanego inputu dla XSS pokazane obok wersji bezpiecznej) + H2 in-memory (już
  używane w `_08_sql`/`_09_jdbc`/`_10_dao`/`_12_hibernate`) dla SqlInjectionDeepDive, bez nowej
  zależności bazodanowej.
- Ta sama zasada bezpieczeństwa demo co w `_18_rest_api`: port zawsze 0, serwer zatrzymywany w
  `finally`, `main()` kończy się samoistnie w kilka sekund.

Obie listy lekcji są już zarejestrowane w `_TableOfContents.java`. Foldery lekcji (puste) już
utworzone pod `src/main/java/com/example/javaquest/_18_rest_api/LessonXX_Temat/` i
`_19_security_basics/LessonXX_Temat/`. Następny krok pracy: zacząć pisać
`_Lesson01_HttpDeepDive.java` + `_Exercises_Lesson01_HttpDeepDive.java` w `_18_rest_api`.

## PLAN: Rozdziały _20_spring_core, _21_spring_boot, _22_spring_web, _23_spring_data_jpa,
## _24_spring_security (Spring / Spring Boot) — ZAPLANOWANE, TREŚĆ JESZCZE NIE NAPISANA

Zapisane 2026-07-11, zaktualizowane tego samego dnia po rozpoczęciu pisania treści. Status:
**foldery WSZYSTKICH 89 lekcji utworzone, wszystkie 5 rozdziałów zarejestrowane w
`_TableOfContents.java`.** `_20_spring_core` ma już **23 lekcje** (nie 22 — użytkownik jawnie
zażądał dodania `Lesson02_SpringVersionsAndCompatibilityOverview` NA POCZĄTKU rozdziału, zaraz po
wstępie, reszta lekcji przesunięta o +1; pełna, aktualna lista lekcji rozdziału jest niżej w tej
sekcji). **`_20_spring_core` jest w PEŁNI ukończony (stan na 2026-07-11): 23/23 lekcje, każda z teorią +
30 ćwiczeniami, skompilowane I uruchomieniowo zweryfikowane `mvnw.cmd exec:java` — zero błędów,
włącznie z kapsztonem (Lesson23) łączącym WSZYSTKIE 22 poprzednie mechanizmy w jednym, spójnym,
2-profilowym demo "JavaQuest Order Processing".**

**`_21_spring_boot` jest w PEŁNI ukończony (stan na 2026-07-11): 16/16 lekcji, każda z teorią +
30 ćwiczeniami, skompilowane I uruchomieniowo zweryfikowane — WŁĄCZNIE z kapsztonem (Lesson16,
"JavaQuest Notes Service") łączącym starter/auto-konfigurację/`@ConfigurationProperties`/profile/
`CommandLineRunner`/Actuator w jednym, REALNIE działającym serwisie REST z wbudowanym Tomcatem,
zweryfikowanym w 2 scenariuszach profilowych (dev/prod) przez prawdziwe wywołania HTTP.**
Następny krok pracy: zacząć `_22_spring_web` od `_Lesson01_ControllerVsRestController.java`.
Pozostałe 2 rozdziały (`_23`-`_24`) mają TYLKO puste foldery.

**NOWA zależność:** `spring-boot-starter-actuator` dodana do `pom.xml` dla Lesson12-13.

Warte odnotowania z Lesson13-16: Lesson14 (budowanie jara/native image) świadomie NIE wywołuje
pełnego `mvnw.cmd package` wewnątrz demo (zbyt wolne/kruche do uruchamiania przy każdej
weryfikacji) — zamiast tego sprawdza REALNĄ obecność `spring-boot-maven-plugin` w `pom.xml` i
REALNĄ dostępność `native-image` na maszynie (z przyjaznym fallbackiem, gdy GraalVM nie jest
zainstalowany — potwierdzone: NIE jest). Lesson15 zamyka pętlę z Lesson04 przez pisanie
prawdziwej klasy `@AutoConfiguration` + zapis prawdziwego pliku `.imports` do katalogu
tymczasowego (bez ryzykownej pełnej dynamicznej klasy ładującej — uznane za zbyt kruche/czasochłonne
względem wartości dydaktycznej, zostawione jako zadanie 21 dla zainteresowanych).

**Kolejny wariant tej samej pułapki `@ComponentScan` (Lesson11) — TYM RAZEM przez
`@SpringBootApplication`, nie ręczny `@ComponentScan`:** `@RestController` jest SAM w sobie
`@Component`, więc domyślny component-scan wewnątrz `@SpringBootApplication` ZNAJDUJE go
automatycznie. Jawne DODATKOWE `@Bean` zwracające `new FailingController()` dało DWIE rejestracje
tego samego kontrolera i błąd Spring MVC "Ambiguous mapping" (dwa handlery na tę samą ścieżkę URL).
**Zasada dla `_21_spring_boot`/`_22_spring_web` (gdzie `@SpringBootApplication` ZAWSZE
component-scanuje): kontrolery/`@Component`-podobne klasy z stereotypem NIGDY nie potrzebują
DODATKOWEGO jawnego `@Bean` — rejestruj je PRZEZ stereotyp (component-scan) ALBO przez `@Bean`,
NIGDY oba naraz w tym samym `@SpringBootApplication`.** Dla klas BEZ żadnego stereotypu (`plain`
POJO, jak w Lesson09/10 demo) jawny `@Bean` jest oczywiście nadal poprawny i konieczny.

Warte odnotowania z Lesson05-08: Lesson08 demo fail-fast walidacji `@ConfigurationProperties`
pokazuje NAPRAWDĘ oryginalny diagnostyczny raport błędu Boota ("APPLICATION FAILED TO START" +
dokładna ścieżka właściwości + komunikat Hibernate Validatora PO POLSKU) — nie symulacja, realne
zachowanie frameworka przechwycone przez `try/catch` w demo. Zasada z `_20_spring_core/Lesson19`
(publiczna klasa + publiczne gettery/settery wymagane dla wiązania JavaBean) zastosowana od razu
prewencyjnie w `PoolProperties`/`ValidatedPoolProperties` — zero powtórki tego buga.

Warte odnotowania z Lesson01-04: Lesson02 realnie parsuje `pom.xml` TEGO projektu (ten sam wzorzec
co `_19_security_basics/Lesson20`) i wypisuje 5 realnych starterów. Lesson04 realnie CZYTA plik
`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` z JAR-a
`spring-boot-autoconfigure` na classpath (przez `getClassLoader().getResourceAsStream(...)`) —
znaleziono 153 prawdziwe klasy auto-konfiguracji, i zweryfikowano że `ObjectMapper` jest NAPRAWDĘ
auto-konfigurowany (bean istnieje bez jawnej rejestracji). Wzorzec `@Conditional*` z
`_20_spring_core`-owej reguły "unikaj `@ComponentScan` przy wielu grupach demo w 1 pliku" nie
dotyczy tego rozdziału tak samo — każdy `demonstrateXxx()` tworzy WŁASNY, EFEMERYCZNY
`SpringApplicationBuilder(...).sources(...)`, więc grupy demo są już naturalnie izolowane przez
jawne `.sources(...)` zamiast global component-scan.

**NOWA zależność dodana do `pom.xml` na potrzeby Lesson21-23 (AOP):**
`org.springframework.boot:spring-boot-starter-aop` — WYJĄTEK od wcześniejszej obietnicy "zero
nowych zależności dla `_20_spring_core`" z sekcji planu niżej; okazało się to NIEUNIKNIONE, bo
realne Spring AOP ze składnią `@Aspect`/`execution(...)` wymaga `aspectjweaver` na classpath
(sam `spring-aop` jest już transytywnie obecny przez `spring-context`, ale parser wyrażeń pointcut
w stylu adnotacji @AspectJ potrzebuje dodatkowo tej biblioteki). Zaktualizuj tę obietnicę w opisie
`_21_spring_boot`/`_22_spring_web`, jeśli któraś z ich lekcji też natrafi na podobną potrzebę.

**NAJWAŻNIEJSZA pułapka całego rozdziału, odkryta przy Lesson21-23 (AOP z adnotacjami NIE
DZIAŁAŁO WCALE, cicho, bez żadnego błędu kompilacji ani runtime — najgroźniejszy rodzaj bugu):**
wyrażenia pointcut `@annotation(NazwaAdnotacji)` przekazywane jako STRING do `@Before`/`@Around`/
itd. są parsowane przez silnik AspectJ, który NIE ZNA zasięgu (scope) Javy — prosta nazwa
ZAGNIEŻDŻONEJ adnotacji (np. `LogExecution` zadeklarowana jako `@interface` WEWNĄTRZ tej samej
klasy lekcji — standardowy wzorzec w tym kursie) nie rozwiązuje się do niczego, więc pointcut PO
CICHU dopasowuje ZERO metod → Spring w ogóle NIE TWORZY proxy dla takiego beana → `@Aspect` nic
nie robi, ale KOD SIĘ KOMPILUJE I URUCHAMIA BEZ WYJĄTKU (tylko brak logów aspektu zdradzał
problem — wykryte WYŁĄCZNIE dzięki faktycznemu uruchomieniu i czytaniu outputu, nie samej
kompilacji). To samo dotyczy `execution(...)` z NIEPEŁNĄ kwalifikowaną nazwą (pominięcie
prefiksu zewnętrznej klasy `_LessonXX_...` przed nazwą klasy zagnieżdżonej — pakiet Javy TO NIE
TO SAMO co pełna ścieżka do klasy zagnieżdżonej). **ZASADA NA PRZYSZŁOŚĆ (dla `_21`-`_24`, gdziekolwiek
pojawi się `@Aspect`/AOP, np. przy `@Transactional`-podobnych demo w `_23_spring_data_jpa` lub
`@PreAuthorize` w `_24_spring_security`, jeśli używają zagnieżdżonych własnych adnotacji):
ZAWSZE używaj PEŁNEJ kwalifikowanej nazwy (`pakiet.NazwaZewnetrznejKlasy.NazwaZagniezdzonejAdnotacji`)
w wyrażeniach pointcut, NIGDY prostej nazwy — najlepiej jako `private static final String` stała
(patrz wzorzec `LOG_EXECUTION`/`AUDITED` w Lesson21-23), i ZAWSZE zweryfikuj uruchomieniowo, że
logi aspektu FAKTYCZNIE się pojawiają, nie tylko że kod się kompiluje.**
Lesson16 wymagał nowego pliku zasobu `src/main/resources/lesson16-app.properties` (app.name/
app.version) — utworzony.

**UOGÓLNIONA ZASADA WIDOCZNOŚCI (2 wystąpienia — Lesson17 SpEL, Lesson19 BeanFactoryPostProcessor +
`PropertyValues`):** wszędzie indziej w tym kursie klasy/metody pomocnicze domyślnie mają widoczność
PAKIETOWĄ (brak modyfikatora) — to działa dla zwykłego DI (`@Autowired` na polu/konstruktorze radzi
sobie z `setAccessible(true)`). Ale co najmniej DWA mechanizmy Springa oparte na **refleksji
JavaBean/`java.beans.Introspector`** (nie na `Field`/`Constructor` reflection) wymagają PUBLICZNEJ
klasy I PUBLICZNYCH metod, inaczej milcząco "nie widzą" ich wcale: (1) **SpEL**
(`ReflectiveMethodResolver` woła `Class.getMethods()`, tylko publiczne) — dało `EL1004E: Method
call ... cannot be found` w Lesson17; (2) **`BeanDefinition.getPropertyValues()` / setter injection
przez `BeanWrapperImpl`** (introspekcja JavaBean property, wymaga PUBLICZNEGO getter+setter PARY,
NIE tylko poprawnej nazwy) — dało mylący błąd "property is not writable" w Lesson19, nawet PO
dodaniu poprawnie nazwanego `getGreeting()`/`setGreeting()` — dopiero `public` na klasie I obu
metodach naprawiło problem. **Zasada na przyszłość: jeśli lekcja demonstruje SpEL, programową
manipulację `PropertyValues`/`BeanDefinition`, lub cokolwiek opartego o `java.beans.Introspector` —
użyta klasa pomocnicza i jej metody MUSZĄ być `public`, nie tylko poprawnie nazwane.**

**Zmiana procesu (2026-07-11, na wyraźną prośbę użytkownika o tempo):** kompilacja nadal NASTĘPUJE
po KAŻDEJ lekcji (tania, natychmiastowa informacja zwrotna), ale URUCHAMIANIE (`exec:java`) i
odczyt realnego outputu odbywa się PARAMI (2 lekcje na raz) zamiast pojedynczo — nadal ZERO
lekcji trafia do repo bez faktycznego uruchomienia i sprawdzenia wyniku, tylko mniej rund
tool-call/odczyt na tę samą liczbę zweryfikowanych lekcji.

**STANDARDOWE ROZWIĄZANIE dla nawracającej pułapki `@ComponentScan` (4. wystąpienie, w Lesson10 i
Lesson13 — ZAKTUALIZOWANE po tym, jak filtr wykluczający okazał się NIEWYSTARCZAJĄCY):** jeśli plik
lekcji ma WIĘCEJ NIŻ JEDNĄ klasę `@Configuration` i/lub WIĘCEJ NIŻ JEDNĄ grupę klas `@Component`
należących do RÓŻNYCH `demonstrateXxx()` (a w tym kursie ma to miejsce niemal zawsze), to
`@ComponentScan` — nawet z `excludeFilters = @Filter(type = ANNOTATION, classes =
Configuration.class)` — WCIĄŻ zgarnie WSZYSTKIE `@Component` z INNYCH demo w TYM SAMYM pliku (bo
są w TYM SAMYM pakiecie, a filtr wykluczał tylko `@Configuration`, nie zwykłe `@Component`). W
Lesson13 dało to FAŁSZYWY wynik — drugi test (field injection + `allowCircularReferences=false`)
obserwowanie awarii spowodowanej PRZEZ ZUPEŁNIE INNĄ parę klas z PIERWSZEGO demo w tym samym pliku
(`ConstructorCycleA`/`B`), nie przez zamierzoną parę `FieldCycleA`/`B` — błąd byłby NIEWYKRYTY,
gdyby nie porównanie treści komunikatu z oczekiwaną nazwą klasy. **NOWA, TWARDA ZASADA:** NIE
UŻYWAJ `@ComponentScan` w plikach lekcji tego rozdziału, chyba że CAŁA lekcja demonstruje TYLKO
JEDNĄ, spójną grupę beanów (jak Lesson01/02/06/11) — w KAŻDEJ innej sytuacji (wiele niezależnych
`demonstrateXxx()`, każda z WŁASNYMI klasami `@Component`-podobnymi) rejestruj beany WYŁĄCZNIE
jawnie przez metody `@Bean` w danej klasie `@Configuration` (parametry metody = zależności — Spring
i tak stosuje `@Autowired`-processing na polach/setterach NIEZALEŻNIE od tego, czy bean powstał
przez skan czy przez `@Bean`, więc field/setter injection demo działa identycznie). Przed napisaniem
KAŻDEJ kolejnej lekcji zadaj sobie pytanie: "czy ten plik ma więcej niż 1 grupę demo-klas?" — jeśli
tak, NIE sięgaj po `@ComponentScan`.

Lesson09 dodatkowo pokazuje (zweryfikowane uruchomieniowo, real CGLIB) różnicę `@Configuration`
"full mode" (domyślny — CGLIB przechwytuje "ręczne" wywołania jednej metody `@Bean` z wnętrza
drugiej, gwarantując singleton) vs `@Configuration(proxyBeanMethods = false)` "lite mode" (brak
przechwytywania — takie "ręczne" wywołanie tworzy NOWĄ instancję, potwierdzone przez `==` i licznik
wywołań w teście).

**Trzecia decyzja techniczna warta zapamiętania (z Lesson08):** `org.springframework.dao.*`
(`DataAccessException`, `PersistenceExceptionTranslationPostProcessor`) żyje w module `spring-tx`,
który NIE JEST jeszcze zależnością projektu (pojawi się naturalnie w `_23_spring_data_jpa` przez
`spring-boot-starter-data-jpa`) — próba użycia go w `_20_spring_core` dała błąd kompilacji
`package org.springframework.dao does not exist`. Naprawione przez zbudowanie WŁASNEGO,
uproszczonego odpowiednika mechanizmu tłumaczenia wyjątków przez zwykły `java.lang.reflect.Proxy`
(ta sama idea co prawdziwe `@Repository`, zero nowej zależności, i przy okazji naturalna zapowiedź
`Lesson21_SpringAopFundamentals` — proxy to fundament AOP). Zasada na przyszłość: jeśli lekcja w
`_20_spring_core`/`_21_spring_boot`/`_22_spring_web` (obiecane jako "zero nowych zależności")
potrzebuje czegoś z `spring-tx`/`spring-orm`/`spring-jdbc`/`spring-security-*`, zbuduj lekki,
własnoręczny odpowiednik zamiast dodawać zależność przedwcześnie — prawdziwe API pojawi się we
właściwym rozdziale (`_23`/`_24`) i wtedy będzie mogło być pokazane NAPRAWDĘ.

**Druga pułapka odkryta przy pisaniu Lesson07 (WARTO PAMIĘTAĆ przy `@ComponentScan` w dalszych
lekcjach tego rozdziału):** `@Configuration` jest SAMA W SOBIE meta-oznaczona `@Component` — jeśli
w JEDNYM pliku lekcji (jak w tym kursie, gdzie WSZYSTKIE demo-klasy żyją jako zagnieżdżone `static
class` w tym samym pakiecie) użyjesz `@ComponentScan` bez ograniczeń, skaner znajdzie TEŻ inne,
wcześniej zdefiniowane w tym samym pliku klasy `@Configuration` (np. z poprzedniej metody
`demonstrateXxx()`) i PONOWNIE uruchomi ich metody `@Bean` — dało to redundantny, ale
nieszkodliwy dodatkowy log przy pierwszym uruchomieniu Lesson07 (dodatkowe utworzenie
`LoggingBean` z INNEGO demo). Naprawione przez rezygnację z `@ComponentScan` tam, gdzie
wystarczy jawna rejestracja pojedynczej klasy przez `@Bean` — PREFERUJ jawną rejestrację
(`@Bean` zwracające `new Xxx()`) zamiast `@ComponentScan` w lekcjach, które mają WIELE
niezależnych metod `demonstrateXxx()` z WŁASNYMI klasami `@Configuration` w tym samym pliku.

**Nowa pułapka odkryta i udokumentowana przy pisaniu Lesson06 (WARTO PAMIĘTAĆ przy dalszych
lekcjach tego rozdziału, wzorem analogicznej pułapki nazw encji w `_12_hibernate`):** domyślna
nazwa beana dla klasy ZAGNIEŻDŻONEJ (nasz wzorzec — komponenty jako `static class` wewnątrz pliku
lekcji) NIE JEST prostym `Introspector.decapitalize(SimpleName)` (jak dla klasy najwyższego
poziomu) — Spring najpierw zamienia `$` na `.` w pełnej nazwie (`Outer$Inner` → `Outer.Inner`),
DOPIERO POTEM dekapitalizuje TYLKO pierwszy znak całego łańcucha — dla zagnieżdżonej klasy
`ManagedCounter` w pliku `_Lesson06_Bean.java` faktyczna nazwa beana to
`"_Lesson06_Bean.ManagedCounter"`, NIE `"managedCounter"` (zweryfikowane uruchomieniowo:
`NoSuchBeanDefinitionException` przy hardkodowaniu zgadywanej nazwy). Rozwiązanie stosowane w
tym rozdziale: NIGDY nie zgaduj/hardkoduj nazwy beana dla zagnieżdżonej klasy — zawsze znajdź ją
dynamicznie przez `context.getBeanNamesForType(Xxx.class)[0]`, ewentualnie nadaj jawną nazwę przez
`@Component("nazwa")`/`@Bean("nazwa")` (JAWNA nazwa NIE zależy od zagnieżdżenia). W REALNYM
projekcie (komponenty jako zwykłe, NIE zagnieżdżone klasy) ten problem w ogóle nie występuje — to
specyfika sposobu, w jaki zbudowany jest ten kurs (analogicznie do zastrzeżenia o `@Entity(name=
...)` w `_12_hibernate`).

Warte odnotowania decyzje techniczne z pisania Lesson01-04 (przydatne przy kontynuacji):
- Lesson01/02/04 są w PEŁNI wykonywalne — component-scanning DZIAŁA na zagnieżdżonych klasach
  statycznych `@Component` (bo pakietem klasy zagnieżdżonej jest pakiet klasy najwyższego poziomu
  ją zawierającej — Spring skanuje WSZYSTKIE pliki `.class` w katalogu pakietu, w tym `Outer$Inner
  .class`) — dokładnie ten sam wzorzec zagnieżdżonych encji/komponentów co w `_12_hibernate`.
  `@ComponentScan` bez argumentów na zagnieżdżonej klasie `@Configuration` skanuje pakiet klasy
  ZEWNĘTRZNEJ (czyli też plik `_Exercises_...java` w tym samym folderze — nieszkodliwe, bo tam nie
  ma żadnych `@Component`).
- Lesson02 realnie odczytuje wersję Springa PRZEZ KOD (`SpringVersion.getVersion()`/
  `SpringBootVersion.getVersion()`) zamiast opisywać ją z pamięci — zweryfikowane uruchomieniowo:
  Spring Framework 6.2.5, Spring Boot 3.4.4 (zgodne z `pom.xml`).
- Wszystkie fakty "stare vs nowe" w Lesson02 zweryfikowane przez WebSearch 2026-07-11 (nie tylko
  pamięć modelu) — źródła i szczegóły w sekcji "Zweryfikowane fakty" niżej w tym samym bloku
  planistycznym.
- Ten rozdział pisany NATURALNĄ polszczyzną z pełnymi znakami diakrytycznymi (w odróżnieniu od
  świadomie ASCII-only `_18_rest_api`/`_19_security_basics`) — zgodnie z większościową konwencją
  reszty repozytorium.

### Dlaczego ta kolejność rozdziałów (decyzja użytkownika, potwierdzona explicité)

Spring zaczynamy od IDEI (Spring Core: IoC/DI/kontener), NIE od Spring Boota — świadomie
odwrotna kolejność niż większość kursów "hello world Spring Boot w 5 minut". Kursant ma najpierw
zrozumieć, CO Spring Boot robi automatycznie za niego (auto-konfiguracja, wbudowany serwer,
starter POM-y), zanim zobaczy "magię" — dokładnie ta sama filozofia co w całym kursie
(`_11_buildtools` uczy `javac`/`java`/classpath PRZED Mavenem/Gradle, `_15_jvm_internals` uczy
JVM PRZED tuningiem). Kolejność: `_20_spring_core` (idea, kontener, DI) →
`_21_spring_boot` (jak Boot automatyzuje to, co poznaliśmy w Core) → `_22_spring_web`
(REST na Springu — kursant MA JUŻ `_18_rest_api` protokołowo i `_07_servlets` na surowym Servlet
API, więc to "jak to samo wygląda w Springu") → `_23_spring_data_jpa` (kursant MA JUŻ
`_12_hibernate`, więc to będzie — cytując użytkownika — "bardzo przyjemne", bo Spring Data JPA to
głównie cienka warstwa abstrakcji NAD tym, co już zna) → `_24_spring_security` (kursant MA JUŻ
cały `_19_security_basics` czystą Javą, więc to "jak Spring Security automatyzuje to, co już
umiesz zrobić ręcznie").

**Testowanie (`@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest`, `@MockitoBean`, Testcontainers)
ŚWIADOMIE NIE wchodzi w skład żadnego z tych 5 rozdziałów** — użytkownik wyraźnie zastrzegł
(2026-07-11): "testy beda odzielnym blokiem nie musisz ich robic teraz". Będzie to OSOBNY,
przyszły rozdział (numeracja/nazwa jeszcze nieustalona, prawdopodobnie `_25_spring_testing` lub
podobny, do ustalenia gdy przyjdzie czas) — nie dodawaj lekcji o testowaniu do żadnego z 5
rozdziałów opisanych niżej, nawet jeśli wydaje się to naturalne w danym miejscu (np. przy
`_21_spring_boot/Lesson12_SpringBootActuator` czy przy `_23_spring_data_jpa`).

**Zależności Maven już DOSTĘPNE bez żadnych zmian w `pom.xml`** (zweryfikowane 2026-07-11):
`spring-boot-starter-parent:3.4.4` jest już rodzicem projektu, `spring-boot-starter`,
`spring-boot-starter-web` i `spring-boot-starter-validation` są już zależnościami (dodane
wcześniej głównie transytywnie dla Jacksona/Tomcata/Bean Validation w `_04_io`/`_07_servlets`/
`_18_rest_api`/`_19_security_basics`) — to oznacza, że **`_20_spring_core`, `_21_spring_boot` i
`_22_spring_web` da się w całości napisać BEZ dodawania jakiejkolwiek nowej zależności**.
Zależności do DODANIA gdy przyjdzie czas (zweryfikuj dokładne najnowsze wersje/koordynaty w Maven
Central przed dodaniem — nie zgaduj, ta sama zasada co przy BCrypt/JJWT/PMD w poprzednich
rozdziałach):
- `_23_spring_data_jpa` → `org.springframework.boot:spring-boot-starter-data-jpa` (Spring Data
  JPA repository abstraction + `PlatformTransactionManager`; UWAGA: projekt ma już bezpośrednio
  dodane `hibernate-core`/`hibernate-envers`/`hibernate-jcache` z `_12_hibernate` — sprawdź `mvn
  dependency:tree` po dodaniu tego startera pod kątem ewentualnego konfliktu/duplikacji wersji
  Hibernate, BOM Boota powinien to rozwiązać spójnie, ale zweryfikuj zamiast zakładać).
- `_24_spring_security` → `org.springframework.boot:spring-boot-starter-security` (podstawa
  rozdziału); dla `Lesson15_OAuth2LoginAndResourceServerIntro` prawdopodobnie
  `org.springframework.boot:spring-boot-starter-oauth2-client` i/lub
  `-oauth2-resource-server` (zweryfikuj DOKŁADNE artifactId w Maven Central przy pisaniu tej
  lekcji — nie zgaduj na pamięć, mimo że koordynaty są względnie pewne). Do
  `Lesson12_JwtAuthentication` — do decyzji przy pisaniu: reużyć `io.jsonwebtoken:jjwt-*`
  (już w `pom.xml` z `_19_security_basics`, spójne z tym, co kursant już zna) CZY pokazać
  natywne wsparcie Spring Security dla JWT (`NimbusJwtDecoder`/OAuth2 Resource Server) —
  prawdopodobnie WARTO pokazać OBA jako kolejny przykład "jak to robiłeś ręcznie (Lesson12/13 z
  `_19_security_basics`) vs jak robi to za ciebie framework".

### `_20_spring_core` ("Spring Core i Dependency Injection") — 23 lekcje (rozbudowane z 14)

Wyjściowa lista użytkownika (14 lekcji) rozbudowana o 9 tematów oznaczonych niżej jako DODANE —
użytkownik poprosił o rozbudowę "żeby było solidnie", z naciskiem na dokładne wyjaśnienie
mechanizmów Spring Core, w tym różnic między starszymi a nowszymi wersjami. **2026-07-11, druga
tura:** użytkownik jawnie zażądał lekcji o różnicach międzywersyjnych "na początku" rozdziału
("wiem ze w jednym springu dziala cos tak a w drugim dziala cos inaczej") — dodano
`Lesson02_SpringVersionsAndCompatibilityOverview` zaraz po wstępie, PRZED jakąkolwiek lekcją
techniczną, jako mapa orientacyjna dla całego 5-rozdziałowego łuku Spring/Spring Boot (reszta
lekcji przesunięta o +1 numer względem poprzedniej wersji tej notatki):

1. WhatIsSpring — w tej lekcji JAWNIE zaznacz, że kurs uczy Spring Framework 6.x / Spring Boot
   3.x (bo `pom.xml` ma `spring-boot-starter-parent:3.4.4`) — co oznacza namespace `jakarta.*`
   (NIE `javax.*`) wszędzie, i Javę 17+ jako minimum (projekt celuje w Javę 21). Krótki przegląd
   ekosystemu (Spring Framework/Boot/Data/Security/Cloud) też tu.
2. SpringVersionsAndCompatibilityOverview (DODANE, 2. tura, celowo na POCZĄTKU rozdziału) —
   dedykowana lekcja-mapa PRZED jakąkolwiek techniczną treścią: (a) jak się mapują wersje Spring
   Boot 1.x/2.x/3.x na wersje Spring Framework 4.x/5.x/6.x i na wymagany Java baseline
   (8/8/17+) oraz namespace (`javax.*` vs `jakarta.*`); (b) TABELA mechanizmów, które DZIAŁAJĄ
   INACZEJ w zależności od wersji, z odesłaniem do lekcji, która idzie w kazdy temat głębiej:
   style konfiguracji XML/Java Config (Lesson05 tego rozdziału), niejawna constructor injection
   od 4.3 (Lesson10), domyślna obsługa cyklicznych zależności zmieniona w Boot 2.6 (Lesson13),
   `WebSecurityConfigurerAdapter`→`SecurityFilterChain` (`_24_spring_security/Lesson03`),
   `RestTemplate`→`WebClient`→`RestClient` (`_22_spring_web/Lesson17`), `spring.factories`→
   `AutoConfiguration.imports` (`_21_spring_boot/Lesson04`), `@MockBean`→`@MockitoBean`
   (przyszły rozdział o testowaniu); (c) PRAKTYCZNA umiejętność: jak rozpoznać PO SAMYM KODZIE,
   pod jaką wersję Springa pisany jest tutorial/odpowiedź na Stack Overflow, ZANIM stracisz czas
   na kopiowanie nieaktualnego API (importy `javax.*` = Boot 2.x, `WebSecurityConfigurerAdapter`
   = sprzed 2022, `spring.factories` = starsza biblioteka/starszy Boot).
3. InversionOfControl — IoC jako ZASADA (kontener, nie Twój kod, tworzy i zarządza obiektami),
   NIE mechanizm — DI to JEDNA z technik realizujących IoC (ServiceLocator to inna, historyczna).
4. DependencyInjection — 3 rodzaje wstrzykiwania (konstruktor/setter/pole) jako zapowiedź
   Lesson10/11.
5. ConfigurationStylesXmlVsJavaVsAnnotations (DODANE) — jawna lekcja o HISTORYCZNEJ EWOLUCJI
   konfiguracji Springa: XML `<beans><bean id="..." class="...">` (Spring 1.x-2.x, dziś
   praktycznie wymarłe, ale kursant MOŻE je spotkać w starym, legacy kodzie) → adnotacje +
   component-scanning (`@Component`+`@ComponentScan`, mainstream od Spring 2.5/3.0) → czysty Java
   Config (`@Configuration`+`@Bean`, DOMYŚLNY i JEDYNY styl w Spring Boot — Boot NIE UŻYWA XML
   wcale). Pokaż TEN SAM bean zdefiniowany wszystkimi 3 sposobami obok siebie. Pogłębienie
   tabeli z Lesson02.
6. Bean — czym jest "bean" (obiekt zarządzany przez kontener) vs zwykły obiekt Javy.
7. ApplicationContext — `ApplicationContext` vs starszy, bardziej podstawowy `BeanFactory`
   (lazy vs eager, `ApplicationContext` to "rozszerzony" `BeanFactory` z eventami/i18n/zasobami).
8. ComponentServiceRepository — stereotypy `@Component`/`@Service`/`@Repository`/`@Controller` —
   semantycznie tożsame dla kontenera, ale `@Repository` dodaje translację wyjątków
   (`PersistenceExceptionTranslationPostProcessor`), `@Controller`/`@RestController` włącza
   obsługę przez Spring MVC (zapowiedź `_22_spring_web`).
9. ConfigurationAndBeanAnnotation — `@Configuration`+`@Bean` dla obiektów, których NIE jesteś
   właścicielem kodu (biblioteki zewnętrzne) — kontrast z `@Component` (Twoje własne klasy).
10. ConstructorInjection — REKOMENDOWANY, domyślny styl — WAŻNE: od Spring 4.3 (2016) `@Autowired`
    na konstruktorze jest OPCJONALNE, jeśli klasa ma DOKŁADNIE JEDEN konstruktor ("implicit
    constructor injection") — pokaż PRZED (jawne `@Autowired`) i PO (bez adnotacji) na przykładzie.
11. FieldInjectionWhyAvoid — dlaczego wstrzykiwanie do pola (`@Autowired private X x;`) jest
    ODRADZANE (niemożliwość `final`, ukryte zależności, trudność testowania bez Springa/refleksji,
    możliwość cyklu zależności, którego constructor injection by WYŁAPAŁ od razu) — mimo że
    WCIĄŻ powszechnie spotykane w starym/legacy kodzie i tutorialach.
12. QualifierAndPrimary (DODANE) — co się dzieje, gdy jest WIĘCEJ NIŻ JEDEN bean danego typu
    (np. 2 implementacje interfejsu) — `@Qualifier("nazwa")` vs `@Primary` (domyślny wybór) —
    bez tego `NoUniqueBeanDefinitionException`.
13. CircularDependencies (DODANE) — cykl A→B→A jako BŁĄD projektowy (nie tylko techniczny) —
    WAŻNA różnica wersji: setter/field injection historycznie POZWALAŁ na cykle (Spring
    "naprawiał" to przez wczesną referencję do proxy), constructor injection ZAWSZE failował z
    `BeanCurrentlyInCreationException`. Spring Boot **2.6 (2021) ZMIENIŁ DOMYŚLNE zachowanie**:
    `spring.main.allow-circular-references` domyślnie `false` (wcześniej `true`) — czyli PRZED
    Boot 2.6 cykl przez setter/field injection cicho "działał", PO 2.6 rzuca wyjątek od razu,
    chyba że jawnie włączysz starą flagę. Zweryfikuj dokładny numer wersji w oficjalnych release
    notes Spring Boot 2.6 przy pisaniu tej lekcji, zamiast polegać wyłącznie na tej notatce.
14. BeanScopes — `singleton` (domyślny) vs `prototype` vs `request`/`session`/`application`
    (web-owe, wymagają `_22_spring_web`) — pułapka: prototype bean wstrzyknięty do singletona.
15. Profiles — `@Profile("dev")`/`@Profile("prod")`, aktywacja przez
    `spring.profiles.active` — zapowiedź głębszego potraktowania w `_21_spring_boot/Lesson06`.
16. PropertiesAndConfiguration — `@Value("${klucz}")`, `Environment` abstraction, `PropertySource`.
17. SpelBasics (DODANE) — Spring Expression Language (`#{...}`) używane w `@Value`, WARUNKOWYCH
    beanach, a PÓŹNIEJ w wyrażeniach `@PreAuthorize` w `_24_spring_security/Lesson10` — wprowadź
    tu RAZ, żeby nie tłumaczyć składni SpEL od nowa przy Security.
18. LifecycleCallbacks — `@PostConstruct`/`@PreDestroy` (JAKARTA, nie własne Springa) vs
    interfejsy `InitializingBean`/`DisposableBean` (starsze, Spring-specific, dziś odradzane na
    rzecz adnotacji JSR-250) vs atrybuty `initMethod`/`destroyMethod` na `@Bean`.
19. BeanPostProcessorsAndContainerExtensionPoints (DODANE) — jak DZIAŁA kontener "pod spodem" —
    `BeanPostProcessor`/`BeanFactoryPostProcessor` jako mechanizm, na którym OPIERAJĄ SIĘ
    `@Autowired`, `@Value`, a nawet proxy AOP (Lesson21-22) — ten sam duch "zajrzyj pod maskę" co
    `_15_jvm_internals`.
20. ApplicationEvents (DODANE) — `ApplicationEventPublisher`/`@EventListener` — luźne powiązanie
    między komponentami przez zdarzenia W JEDNYM kontenerze Springa — bezpośrednie
    NAWIĄZANIE do `_17_architecture/Lesson18_EventDrivenCommunicationBetweenModules` (tam:
    ręczny publisher/listener czystą Javą; tu: to samo, ale kontener Springa robi
    "podpinanie" słuchaczy za Ciebie).
21. SpringAopFundamentals (DODANE) — aspekty jako rozwiązanie "cross-cutting concerns" (logowanie,
    security, transakcje — TO WŁAŚNIE jest mechanizm, na którym stoi `@Transactional` z
    `_23_spring_data_jpa/Lesson08`!) — `@Aspect`, `@Before`/`@After`/`@Around`, jak Spring TWORZY
    proxy (JDK dynamic proxy dla interfejsów vs CGLIB dla klas — `proxy-target-class=true`
    DOMYŚLNIE w Spring Boot od dawna, w przeciwieństwie do "gołego" Spring Framework, gdzie
    domyślnie było JDK proxy dla klas implementujących interfejs).
22. SpringAopAdvancedPointcutsAndProxies (DODANE) — składnia wyrażeń pointcut (`execution(...)`),
    KLASYCZNA pułapka self-invocation (wywołanie metody `@Transactional`/`@Cacheable` z TEJ SAMEJ
    klasy omija proxy — częsty, mylący błąd w praktyce), `@Order` przy wielu aspektach.
23. SpringCoreCapstone — mini-aplikacja łącząca WSZYSTKIE 22 poprzednich lekcji (kontener +
    profile + AOP + eventy + lifecycle) w jednym spójnym demo, BEZ Spring Boota (świadomie —
    pokazuje "gołego" `AnnotationConfigApplicationContext`, żeby kursant docenił, ile automatyzuje
    `_21_spring_boot`).

### `_21_spring_boot` ("Spring Boot") — 16 lekcji (rozbudowane z 12)

1. WhatIsSpringBoot — Boot jako "opinionated" warstwa NAD Spring Frameworkiem — auto-config +
   embedded server + starter POM-y = mniej boilerplate'u z `_20_spring_core`.
2. ProjectSetup — Spring Initializr (koncepcyjnie — ten projekt już ISTNIEJE, więc pokaż RÓWNIEŻ
   jak wyglądałby ten sam `pom.xml` wygenerowany od zera).
3. Starters — `spring-boot-starter-*` jako "kurator zależności" (BOM + sensowne domyślne wersje).
4. AutoConfiguration — MECHANIZM: `@EnableAutoConfiguration`, `@Conditional*` (`@ConditionalOn
   Class`/`@ConditionalOnMissingBean`/`@ConditionalOnProperty`). **WAŻNA różnica wersji do
   pokazania WPROST**: rejestracja klas auto-konfiguracji przez plik `META-INF/spring.factories`
   pod kluczem `org.springframework.boot.autoconfigure.EnableAutoConfiguration` (STARY mechanizm,
   PRZED Spring Boot 2.7) vs plik `META-INF/spring/org.springframework.boot.autoconfigure.
   AutoConfiguration.imports` + adnotacja `@AutoConfiguration` (NOWY mechanizm, Boot 2.7+,
   zachowana wsteczna kompatybilność ze `spring.factories` w 2.7, ale to STANDARD od tej wersji
   wzwyż — projekt na Boot 3.4.4 powinien uczyć TEGO mechanizmu jako domyślnego, ze wzmianką o
   `spring.factories` jako "zobaczysz to w starszych bibliotekach/tutorialach").
5. ApplicationPropertiesYaml — `.properties` vs `.yml` (zagnieżdżanie, czytelność list) — oba
   WCIĄŻ wspierane, YAML popularniejszy dla złożonej konfiguracji.
6. ProfilesInSpringBoot — `application-{profile}.yml`, `spring.profiles.active`, pogłębienie
   `_20_spring_core/Lesson15_Profiles`.
7. CommandLineRunner — `CommandLineRunner`/`ApplicationRunner` — kod uruchamiany PO starcie
   kontekstu, PRZED przyjęciem ruchu.
8. ConfigurationProperties — `@ConfigurationProperties` (type-safe, hierarchiczna konfiguracja)
   vs pojedyncze `@Value` z Lesson15 `_20_spring_core` — kiedy które.
9. DevToolsAndProductivity (DODANE) — `spring-boot-devtools` (automatyczny restart przy zmianie
   kodu, LiveReload) — narzędzie DEV-ONLY, WYŁĄCZONE automatycznie w produkcyjnym jarze.
10. LoggingInSpringBoot — domyślny Logback (już znany z `_13_libraries/Lesson16-17`) +
    autokonfiguracja poziomów logowania przez `application.yml` (`logging.level.*`).
11. ErrorHandlingBasics — domyślna strona/JSON błędu Boota (`/error`, `BasicErrorController`) —
    PRZED pełnym `@ControllerAdvice` z `_22_spring_web/Lesson09`.
12. SpringBootActuator — `/actuator/health`, `/actuator/metrics`, `/actuator/info` — endpointy
    operacyjne "za darmo".
13. ObservabilityMicrometerAndTracing (DODANE) — **WAŻNA różnica Boot 2 vs Boot 3**: Boot 2 —
    Actuator + Micrometer głównie dla METRYK; Boot 3 wprowadził ujednolicony Micrometer
    Observation API + natywne wsparcie dla distributed tracingu (Micrometer Tracing, następca
    zarchiwizowanego Spring Cloud Sleuth) — zweryfikuj dokładne szczegóły/wersje przy pisaniu.
14. BuildingExecutableJarAndNativeImage — klasyczny "fat/uber jar" (`spring-boot-maven-plugin`,
    zagnieżdżone jary w layoucie Boota) ORAZ **nowość Boot 3**: kompilacja do natywnego obrazu
    przez GraalVM (Spring AOT processing, `native-image`) — znacząco szybszy start/mniejsze
    zużycie pamięci kosztem czasu kompilacji i pewnych ograniczeń (refleksja/proxy wymagają
    jawnych "hints"). Zweryfikuj czy warto/da się to zademonstrować realnie (wymaga GraalVM na
    maszynie — prawdopodobnie TYLKO koncepcyjnie + polecenia do uruchomienia ręcznie, podobnie do
    hybrydowego stylu Mavena/Gradle w `_11_buildtools`).
15. CustomAutoConfigurationAndStarters (DODANE) — jak NAPISAĆ WŁASNĄ auto-konfigurację (własny
    `@AutoConfiguration` + wpis w `AutoConfiguration.imports`) — zamyka pętlę z Lesson04,
    kursant przechodzi od "użytkownika" do "twórcy" mechanizmu.
16. SpringBootCapstone (DODANE — oryginalna lista użytkownika NIE miała capstone'u, dodany dla
    spójności z KAŻDYM innym rozdziałem tego kursu) — mała, kompletna aplikacja Boot łącząca
    starter/auto-config/properties/profile/CommandLineRunner/Actuator w jednym demo.

### `_22_spring_web` ("Spring Web i REST API") — 19 lekcji (rozbudowane z 16)

Kursant ma już `_18_rest_api` (projektowanie REST protokołowo, czystym `HttpServer`) i
`_07_servlets` (surowe Servlet API) — ten rozdział to "jak Spring MVC robi to samo za Ciebie".
Jawnie odsyłaj do konkretnych lekcji `_18_rest_api` zamiast powtarzać teorię protokołu HTTP.

1. ControllerVsRestController — `@Controller`+`@ResponseBody` per metoda vs `@RestController`
   (skrót = `@Controller`+`@ResponseBody` na całej klasie) — MVC (widoki) vs REST (dane).
2. RequestMappingGetPostPutDelete — **stary styl** `@RequestMapping(method = RequestMethod.GET)`
   vs **nowy styl** `@GetMapping`/`@PostMapping`/`@PutMapping`/`@DeleteMapping`/`@PatchMapping`
   (skróty wprowadzone w Spring 4.3, 2016 — dziś DOMYŚLNY, idiomatyczny styl) — pokaż OBA obok
   siebie, `@RequestMapping` nadal potrzebny na poziomie KLASY (wspólny prefix ścieżki).
3. PathVariable — `@PathVariable`, powiązanie z `_18_rest_api/Lesson09`.
4. RequestParam — `@RequestParam` (wymagane/opcjonalne/wartość domyślna).
5. RequestBody — `@RequestBody` + Jackson (deserializacja JSON->obiekt) automatyczna dzięki
   `HttpMessageConverter` (zapowiedź Lesson11).
6. ResponseEntity — pełna kontrola nad statusem/nagłówkami/ciałem odpowiedzi, powiązanie z
   `_18_rest_api/Lesson05_StatusCodes`.
7. DtoInRestApi — powiązanie z `_17_architecture/Lesson07_DtoEntityMapper` i
   `_09_jdbc/Lesson19-20` — teraz w kontekście Springowego `@RequestBody`/`@ResponseBody`.
8. ValidationWithValid — `@Valid`/`@Validated` + Bean Validation (już znane z
   `_19_security_basics/Lesson17`) — TERAZ zintegrowane automatycznie przez Spring MVC
   (`MethodArgumentNotValidException` przy błędzie, zamiast ręcznego `Validator.validate()`).
9. GlobalExceptionHandler — `@ControllerAdvice`+`@ExceptionHandler` — scentralizowana obsługa
   błędów, POGŁĘBIENIE `_16_clean_code/Lesson17`+`_17_architecture/Lesson16` w kontekście Springa.
10. ErrorResponseDto — `ProblemDetail` (RFC 7807, WBUDOWANE w Spring 6/Boot 3 — `org.springframework.
    http.ProblemDetail`, NOWOŚĆ względem Boot 2, gdzie trzeba było ręcznie implementować RFC 7807
    jak w `_18_rest_api/Lesson12`) vs własny DTO błędu — pokaż PRZEJŚCIE od ręcznej implementacji
    (znanej z `_18_rest_api`) do wbudowanego wsparcia frameworka.
11. ContentNegotiationAndMessageConverters (DODANE) — `produces`/`consumes`, jak Spring wybiera
    `HttpMessageConverter` (Jackson dla JSON domyślnie) — Spring-owa MECHANIKA tego, co
    `_18_rest_api/Lesson07_ContentNegotiation` uczył na poziomie protokołu.
12. PaginationInApi — `Pageable`/`Page<T>` Spring Data (zapowiedź `_23_spring_data_jpa/Lesson06`),
    powiązanie z `_18_rest_api/Lesson10`.
13. SortingAndFiltering — `Sort` z Spring Data, parametry `sort=`/`filter=` w praktyce Springa.
14. FileUpload — `MultipartFile`, POGŁĘBIENIE `_07_servlets/Lesson18` (tam: surowy
    `MultipartConfigElement` na embedded Tomcacie; tu: Spring robi to za Ciebie).
15. CorsInSpring — `@CrossOrigin` per-kontroler vs globalna konfiguracja (Lesson16) — powiązanie
    z `_19_security_basics/Lesson09_Cors`.
16. InterceptorsAndWebMvcConfigurer (DODANE) — `HandlerInterceptor` (Springowy odpowiednik
    `Filter` z `_07_servlets/Lesson14`, ale działający NA POZIOMIE Spring MVC, z dostępem do
    `HandlerMethod`) + `WebMvcConfigurer` jako miejsce CENTRALNEJ rejestracji interceptorów/CORS/
    konwerterów — "jak to wszystko się ze sobą spina".
17. HttpClientsRestTemplateWebClientRestClient (DODANE) — **kluczowa lekcja "stare vs nowe"**:
    `RestTemplate` (Spring 3.0, 2009 — dziś w "trybie utrzymaniowym", bez nowych funkcji, ale
    WCIĄŻ szeroko spotykany w istniejącym kodzie) → `WebClient` (Spring 5, 2017, reaktywny,
    część WebFlux — działa też synchronicznie przez `.block()`, ale to "reaktywne narzędzie użyte
    nie-reaktywnie") → `RestClient` (Spring Framework **6.1**, 2023/Boot **3.2** — NOWY,
    SYNCHRONICZNY klient, płynne API jak `WebClient`, ale bez narzutu reaktywnego — zalecany
    domyślny wybór dla zwykłych, blokujących aplikacji Spring MVC od Boot 3.2 wzwyż). Pokaż
    IDENTYCZNE zapytanie HTTP wykonane WSZYSTKIMI TRZEMA, obok siebie.
18. SwaggerOpenApi — `springdoc-openapi-starter-webmvc-ui` — TU WŁAŚNIE realizuje się zapowiedź z
    `_18_rest_api/Lesson18_OpenApiSwaggerIntro` ("w Spring Boot ten sam efekt osiągniesz jedną
    zależnością") — DODAJ TERAZ tę zależność do `pom.xml` (zweryfikuj `${springdoc.version}` w
    Maven Central, nie zgaduj — ta sama zasada co zawsze), pokaż auto-skanowanie
    `@RestController` bez ręcznego pisania YAML.
19. RestApiCapstone — mini-API łączące wszystkie 18 poprzednich lekcji, analogiczne do
    `_18_rest_api/Lesson20` ale zaimplementowane w Springu zamiast czystym `HttpServer`.

### `_23_spring_data_jpa` ("Spring Data JPA") — 15 lekcji (BEZ zmian względem listy użytkownika)

Użytkownik NIE poprosił o rozbudowę tego rozdziału i nie ma potrzeby — lista jest już kompletna
i ściśle lustrzana względem `_12_hibernate` (kursant ma PEŁNE 30 lekcji Hibernate/JPA za sobą, więc
ten rozdział to głównie "jak Spring Data JPA automatyzuje to, co już umiesz": `Repository`
zamiast ręcznego DAO, `@Query` zamiast ręcznego HQL, itd. — jawnie odsyłaj do konkretnych lekcji
`_12_hibernate` zamiast powtarzać teorię JPA/ORM od zera):
01_WhatIsSpringDataJpa, 02_RepositoryInterfaces, 03_CrudRepositoryJpaRepository, 04_QueryMethods,
05_CustomQueries, 06_PaginationAndSorting, 07_EntityRelationshipsInSpring, 08_TransactionsInSpring
(powiązanie z `_17_architecture/Lesson13_TransactionBoundaries` — GDZIE żyje granica transakcji),
09_LazyLoadingAndNPlusOne (powiązanie z `_12_hibernate/Lesson15`), 10_EntityGraph,
11_Projections, 12_Specifications, 13_Auditing (powiązanie z `_12_hibernate/Lesson29_
HibernateEnvers` — inne podejście do tego samego problemu), 14_MigrationsWithFlyway (powiązanie z
`_10_dao/Lesson25_DatabaseMigrations`), 15_SpringDataJpaCapstone.

### `_24_spring_security` ("Spring Security") — 17 lekcji (rozbudowane z 15)

Kursant ma już CAŁY `_19_security_basics` (21 lekcji czystą Javą) — ten rozdział to "jak Spring
Security automatyzuje ręczne mechanizmy, które already umiesz zbudować sam" — jawnie odsyłaj do
konkretnych lekcji zamiast tłumaczyć np. czym jest BCrypt/JWT/CSRF od zera.

1. WhatIsSpringSecurity — filtr bezpieczeństwa jako WARSTWA PRZED Spring MVC (Servlet Filter
   Chain, powiązanie z `_07_servlets/Lesson14_Filters`).
2. SecurityFilterChain — **TO JEST WSPÓŁCZESNY, JEDYNY sposób konfiguracji w Spring Security 6.x
   /Boot 3.x** — bean `SecurityFilterChain` budowany przez lambda DSL na `HttpSecurity`. Ucz TEGO
   jako JEDYNEGO, aktualnego API (bez pokazywania od razu starego stylu — to zadanie Lesson03).
3. SecurityConfigEvolutionOldVsNew (DODANE) — DEDYKOWANA lekcja "jak to wyglądało kiedyś", bo
   Security ma NAJWIĘCEJ zmian międzywersyjnych z całego Springa (zweryfikowane WebSearch
   2026-07-11, źródła: spring.io blog, docs.spring.io, Baeldung):
   - XML `<http>`/`<intercept-url>` (najstarsze, Spring Security 2.x-3.x, dziś martwe).
   - `WebSecurityConfigurerAdapter` (dziedziczenie z `configure(HttpSecurity)`) —
     WPROWADZONY jako mainstream przez lata, **DEPRECATED w Spring Security 5.7.0-M2 / Spring
     Boot 2.7.0**, **CAŁKOWICIE USUNIĘTY w Spring Security 6.x** (czyli w KAŻDYM projekcie na
     Boot 3.x — w tym W TYM kursie — ten kod PO PROSTU SIĘ NIE SKOMPILUJE, klasa nie istnieje).
   - `SecurityFilterChain` jako bean (metoda BEZ dziedziczenia) — WPROWADZONY jako opcja w
     Spring Security **5.4**, stał się JEDYNYM sposobem od 6.x — to jest styl z Lesson02.
   - `authorizeRequests()`+`antMatchers()` (STARE, deprecated od Security 5.7+, oznaczone do
     USUNIĘCIA w Security 7.0) vs `authorizeHttpRequests()`+`requestMatchers()` (NOWE, JEDYNE
     zalecane od 6.x) — UWAGA: NIE MIESZAĆ obu w jednej konfiguracji (błąd w runtime).
   - `@EnableGlobalMethodSecurity(prePostEnabled = true)` (STARE, deprecated od Spring Security
     6.0) vs `@EnableMethodSecurity` (NOWE, `prePostEnabled=true` DOMYŚLNIE, nie trzeba go już
     jawnie ustawiać) — powiązanie z Lesson10.
   Ta lekcja istnieje PO TO, żeby kursant natrafiający w internecie na starszy tutorial (a
   WIĘKSZOŚĆ tutoriali Spring Security w necie WCIĄŻ uczy `WebSecurityConfigurerAdapter`, bo
   powstały przed 2022) rozumiał, DLACZEGO ten kod nie działa i jak go przetłumaczyć na
   współczesne API.
4. DefaultLogin — domyślna strona logowania/wygenerowane hasło Boota (`spring-boot-starter-
   security` "z automatu") jako PUNKT WYJŚCIA, zanim zacznie się to konfigurować ręcznie.
5. UserDetailsService — własna implementacja `UserDetailsService`, powiązanie z
   `_19_security_basics/Lesson01` (`User`/`role` tam jako zwykły rekord, tu jako framework-owy
   kontrakt).
6. PasswordEncoder — `BCryptPasswordEncoder` (Spring-owy WRAPPER na TO SAMO jBCrypt API poznane w
   `_19_security_basics/Lesson03` — pokaż, że to KONCEPCYJNIE identyczny algorytm, inny tylko
   interfejs), `DelegatingPasswordEncoder` (obsługa wielu formatów/migracji hasła naraz).
7. RolesAndAuthorities — `GrantedAuthority`, `ROLE_` prefix convention, powiązanie z
   `_19_security_basics/Lesson07_AuthorizationPatternsAndRbac`.
8. FormLogin — `formLogin()` w `SecurityFilterChain`, powiązanie z `_19_security_basics/Lesson04`.
9. ProtectingEndpoints — `authorizeHttpRequests()` w praktyce (`permitAll()`/`authenticated()`/
   `hasRole()`/`hasAuthority()`).
10. MethodSecurity — `@PreAuthorize`/`@PostAuthorize`/`@Secured` z wyrażeniami SpEL (powiązanie
    z `_20_spring_core/Lesson17_SpelBasics`) — `@EnableMethodSecurity` z Lesson03.
11. CustomLoginPage — własny widok logowania (Thymeleaf lub prosty HTML) zamiast domyślnej
    strony Boota.
12. JwtAuthentication — implementacja JWT w Spring Security — DO DECYZJI przy pisaniu: reużyć
    jjwt (jak `_19_security_basics/Lesson05`) w WŁASNYM filtrze `OncePerRequestFilter`, CZY
    natywne `NimbusJwtDecoder`/OAuth2 Resource Server — prawdopodobnie pokazać OBA.
13. StatelessSecurity — `SessionCreationPolicy.STATELESS`, dlaczego JWT+sesje się WYKLUCZAJĄ
    koncepcyjnie, powiązanie z `_19_security_basics/Lesson04` (sesje) vs `Lesson05` (JWT).
14. CorsAndCsrfInSpringSecurity — konfiguracja CORS/CSRF W RAMACH `SecurityFilterChain` (różni
    się od czystego `_19_security_basics/Lesson09-10`, bo Security ma WŁASNY mechanizm CSRF
    zintegrowany z sesją) — WAŻNE: CSRF jest WŁĄCZONY domyślnie w Spring Security, trzeba go
    świadomie wyłączyć dla bezstanowego API JWT (i wyjaśnić, DLACZEGO to bezpieczne tylko wtedy).
15. OAuth2LoginAndResourceServerIntro (DODANE) — `spring-boot-starter-oauth2-client` (logowanie
    "Zaloguj się przez Google" itp.) i `-oauth2-resource-server` (walidacja tokenów JWT wydanych
    przez ZEWNĘTRZNY authorization server) — Spring-owa REALIZACJA koncepcji poznanej czystą Javą
    w `_19_security_basics/Lesson06_OAuth2AndOpenIdConnectIntro`.
16. SecurityExceptionHandling — `AuthenticationEntryPoint`/`AccessDeniedHandler` — spójne
    401/403 w stylu Spring Security, powiązanie z `_18_rest_api/Lesson12_ErrorResponseDesign`.
17. SpringSecurityCapstone — zabezpieczone REST API łączące WSZYSTKIE poprzednie 16 lekcji,
    analogiczne w duchu do `_19_security_basics/Lesson21` (tam: ręczny `HttpServer`+BCrypt+JWT;
    tu: TO SAMO, ale Spring Security robi ciężką pracę za kursanta) — najlepsza okazja, żeby
    kursant PORÓWNAŁ ilość kodu "ręcznie" vs "frameworkiem" na identycznym scenariuszu.

### Zweryfikowane fakty "stare vs nowe" (WebSearch 2026-07-11) — źródło prawdy dla powyższych lekcji

Poniższe zostało sprawdzone przez wyszukiwanie w sieci (nie tylko pamięć modelu) przy planowaniu
tego rozdziału — użyj jako punkt odniesienia przy pisaniu treści, ale i tak zweryfikuj ponownie
przy faktycznym pisaniu lekcji (informacje w sieci mogły się zmienić):
- `@MockBean`/`@SpyBean` — **deprecated od Spring Boot 3.4.0** (do usunięcia w 4.0.0), zastąpione
  przez `@MockitoBean`/`@MockitoSpyBean` (dostępne od Boot 3.2). Dotyczy PRZYSZŁEGO rozdziału o
  testowaniu, nie żadnego z 5 opisanych wyżej — zanotowane tu, żeby nie zgubić przy pisaniu tamtego
  rozdziału. Różnica: `@MockitoBean` NIE jest wspierane na `@Configuration`/`@Component` (w
  odróżnieniu od `@MockBean`).
- `WebSecurityConfigurerAdapter` — deprecated Spring Security 5.7.0-M2/Boot 2.7.0, USUNIĘTY w
  Security 6.x. `SecurityFilterChain` bean — wprowadzony jako opcja w Security 5.4.
- `authorizeRequests()`/`antMatchers()` — deprecated od 5.7+, do usunięcia w Security 7.0.
  `authorizeHttpRequests()`/`requestMatchers()` — aktualny standard.
- `@EnableGlobalMethodSecurity` — deprecated od Spring Security 6.0. `@EnableMethodSecurity` —
  aktualny standard, `prePostEnabled=true` domyślnie.
- `RestTemplate` (Spring Framework 3.0, 2009) → `WebClient` (Spring 5, reaktywny) → `RestClient`
  (Spring Framework 6.1, 2023, wsparcie w Boot od 3.2) — RestClient to "płynne API WebClienta +
  synchroniczna, blokująca infrastruktura RestTemplate", zalecany domyślny wybór dla zwykłych
  (nie-reaktywnych) aplikacji Spring MVC od Boot 3.2 wzwyż.
- Auto-konfiguracja: `META-INF/spring.factories` (stary mechanizm) →
  `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` +
  `@AutoConfiguration` (nowy mechanizm od Boot 2.7, `spring.factories` nadal honorowany w 2.7 dla
  wstecznej kompatybilności, ale to NIE jest już zalecany sposób rejestrowania WŁASNYCH
  auto-konfiguracji).
- Constructor injection bez jawnego `@Autowired` — możliwe od Spring **4.3** (2016), TYLKO gdy
  klasa ma DOKŁADNIE JEDEN konstruktor; przy więcej niż jednym trzeba jawnie oznaczyć wybrany.
- `javax.*` → `jakarta.*` — namespace zmieniony przy przejściu Jakarta EE spod Oracle do Eclipse
  Foundation; Spring Framework 6/Spring Boot 3 WYMAGAJĄ `jakarta.*` (Java 17+ jako baseline) —
  KAŻDY tutorial/kod używający `javax.servlet`/`javax.persistence`/`javax.validation` jest pisany
  pod Spring Boot 2.x i NIE zadziała bez zmiany importów w tym projekcie (Boot 3.4.4).

### Uwaga o kolejności PISANIA (nie tylko planowania)

Rozdziały MOGĄ (ale nie muszą) być pisane w innej kolejności niż logiczna kolejność kursu, jeśli
przyszła sesja uzna to za praktyczne — ALE `_20_spring_core` powinien powstać PIERWSZY, bo
`_21_spring_boot` i dalsze ZAKŁADAJĄ znajomość DI/kontenera/AOP z Core (dokładnie tak, jak
użytkownik to zaplanował: "Tu bym zaczął Springa nie od Boota, tylko od idei"). W razie
wyczerpania limitu w trakcie pisania KTÓREGOKOLWIEK z tych rozdziałów, zaktualizuj tę sekcję CLAUDE.md
o dokładny stan (które lekcje mają już treść, które nie) — ta sama konwencja co przy wszystkich
poprzednich rozdziałach tego kursu.

### Stan `_22_spring_web` na 2026-07-11

**`_22_spring_web` jest w PEŁNI ukończony (stan na 2026-07-11): 19/19 lekcji, każda z teorią +
30 ćwiczeniami, cały projekt skompilowany (`mvnw.cmd compile`) i uruchomieniowo zweryfikowany
(`mvnw.cmd exec:java` dla każdej lekcji) — zero błędów.** `_20_spring_core` (23/23) i
`_21_spring_boot` (16/16) pozostają KOMPLETNE. Lesson19 (kapszton "JavaQuest Tasks API") łączy w
1 działającym mini-API zbudowanym na prawdziwym Spring MVC: routing/DTO (Lesson01-07), `@Valid`+
`@RestControllerAdvice`+`ProblemDetail` RFC 7807 (Lesson08-10), stronicowanie (Lesson12), CORS
(Lesson15) i `HandlerInterceptor` do logowania dostępu (Lesson16) — zweryfikowane end-to-end przez
6 scenariuszy w `main()` (lista+stronicowanie, utworzenie 201+Location, 404 nieznaleziony zasób,
400 błąd walidacji, aktualizacja, usunięcie 204), wszystkie z oczekiwanymi kodami statusu. Ten sam
wzorzec embedowania co w `_18_rest_api`/
`_19_security_basics`, ale TERAZ przez prawdziwy Spring MVC (`@SpringBootApplication` +
`SpringApplicationBuilder(...).properties("server.port=0").run()` + zagnieżdżone
`@RestController` w `main()`), NIE przez surowy `com.sun.net.httpserver.HttpServer`.

Dwie nowe pułapki znalezione przy pisaniu `_22_spring_web` (WAŻNE dla Lesson09+ i dla `_23`/`_24`):

- **`ConstraintViolationException` z `@Validated` na `@RequestParam`/`@PathVariable` NIE MA
  domyślnego handlera w Spring MVC** — w odróżnieniu od `MethodArgumentNotValidException`
  (rzucanego przez `@Valid` na `@RequestBody`, który Spring Boot automatycznie tłumaczy na 400 Bad
  Request), `ConstraintViolationException` z walidacji POJEDYNCZYCH parametrów (przez AOP-owy
  `MethodValidationPostProcessor` wyzwalany przez `@Validated` NA KLASIE) propaguje się
  NIEOBSŁUŻONY aż do Tomcata, dając **500 Internal Server Error, NIE 400** — mimo że wygląda jak
  "zwykły" błąd walidacji. Odkryte empirycznie w Lesson08 (test dał realne 500 zamiast
  oczekiwanego 400). Naprawa: własny `@ExceptionHandler(ConstraintViolationException.class)`
  zwracający `ResponseEntity.status(HttpStatus.BAD_REQUEST)...` — Lesson08 demonstruje TERAZ oba
  warianty (bez handlera = 500, z handlerem = 400) jako CELOWĄ lekcję o tej pułapce, nie ukrywa
  jej. **Zapamiętaj na `Lesson09_GlobalExceptionHandler`**: `@ExceptionHandler`/`@ControllerAdvice`
  MUSI jawnie obsłużyć `ConstraintViolationException` obok `MethodArgumentNotValidException`,
  inaczej walidacja parametrów zapytania/ścieżki nadal będzie dawać 500 nawet z globalnym
  handlerem, jeśli ten globalny handler też o tym zapomni.
- **Ten sam wariant bug-family co w `_20_spring_core`/`_21_spring_boot`**: WIELE `@SpringBootApplication`
  + `@RestController` zagnieżdżonych w JEDNYM pliku lekcji dzieli TĘ SAMĄ paczkę (Java package) —
  implicit component-scan KAŻDEGO `@SpringBootApplication` (scan bazowy = paczka klasy z adnotacją)
  wykrywa WSZYSTKICH sąsiadujących `@RestController` w TYM SAMYM pliku, NIE TYLKO tego
  "przypisanego" do danej metody demo. Jeśli DWIE takie klasy kontrolera mapują TĘ SAMĄ ścieżkę +
  metodę HTTP (nawet jeśli są używane w RÓŻNYCH metodach `demonstrateXxx()` z RÓŻNYMI
  `@SpringBootApplication`), Spring rzuci `Ambiguous mapping` w OBU kontekstach. Wystąpiło 2x w tej
  samej sesji (Lesson07: `LeakyController`/`SafeController` obie na `GET /users/{id}`; Lesson08:
  `UnhandledParamValidationController`/`HandledParamValidationController` obie na
  `GET /products/search`) — naprawione przez NADANIE KAŻDEMU kontrolerowi w pliku UNIKALNEJ
  ścieżki. NIGDY nie zakładaj, że dwie klasy kontrolerów w tym samym pliku "są od siebie
  niezależne" tylko dlatego, że są wywoływane z różnych metod `demonstrateXxx()`.
  **DOPRECYZOWANIE (Lesson11)**: ta kolizja NIE występuje, jeśli dwie metody na tej samej ścieżce
  różnią się atrybutem `produces` (Spring MVC traktuje `produces`/`consumes` jako część klucza
  mapowania, nie tylko ścieżkę+metodę HTTP) — zweryfikowane empirycznie: `NegotiationController`
  (`produces=application/json`) i `CsvController` (`produces=text/csv`) na TEJ SAMEJ ścieżce
  `GET /products/P1`, w tym samym pliku/paczce, NIE dały "Ambiguous mapping" — Spring poprawnie
  wybiera handler po nagłówku `Accept`. Zasada unikalnych ścieżek dotyczy więc tylko przypadku, gdy
  WSZYSTKIE kryteria mapowania (ścieżka+metoda+produces+consumes) się pokrywają.
- **CORS z originem SPOZA dozwolonej listy: Spring odrzuca NA SERWERZE (403), nie tylko pomija
  nagłówek** — pierwotny tekst Lesson15 błędnie zakładał (przez analogię do `_19_security_basics/
  Lesson09`, gdzie CORS był implementowany ręcznie na surowym `HttpServer`), że serwer zawsze
  zwraca 200, a TYLKO przeglądarka blokuje odczyt odpowiedzi po swojej stronie. Zweryfikowane
  empirycznie: wbudowany `DefaultCorsProcessor` Springa jest BARDZIEJ restrykcyjny — gdy `Origin`
  nie pasuje do skonfigurowanej listy, zwraca 403 Forbidden i kontroler NIGDY nie zostaje wywołany
  (podwójna warstwa ochrony: serwer ORAZ przeglądarka, nie tylko poleganie na przeglądarce). Test
  dał realne 403 zamiast zakładanego 200 — poprawione w treści i komentarzach Lesson15.
- **NAJPOWAŻNIEJSZA usterka tego rozdziału — realny, zweryfikowany bug w `springdoc-openapi`
  2.8.15/2.8.16/2.8.17 ZEPSUŁ WSZYSTKIE lekcje Spring w CAŁYM projekcie, nie tylko Lesson18**:
  po dodaniu `springdoc-openapi-starter-webmvc-ui` (potrzebnego do Lesson18) w wersji 2.8.17
  (wtedy najnowsza 2.x, zweryfikowana WebSearch), KAŻDA aplikacja Spring Boot w PROJEKCIE zaczęła
  padać na starcie z `BeanCreationException: Error creating bean with name
  'resourceHandlerMapping' ... No more pattern data allowed after {*...} or ** pattern element` —
  włącznie z Lesson01, który nie ma NIC wspólnego z Swaggerem. Przyczyna: to jest AUTO-KONFIGURACJA
  (rejestruje handler zasobów dla `/webjars/**` itd.) uruchamiana w KAŻDYM kontekście Spring Boot
  na classpath, więc zły wzorzec ścieżki psuje WSZYSTKO, nie tylko lekcję, która go używa. Znany,
  udokumentowany błąd (GitHub `springdoc/springdoc-openapi#3210`): 2.8.15+ generuje wzorzec
  zasobu `/swagger-ui/**/*swagger-initializer.js` (gwiazdka PO `**`), którego NOWY, ściślejszy
  `PathPattern` parser Spring Framework 6/Boot 3.4.4 nie akceptuje. **Naprawa: przypięcie wersji
  na 2.8.14** (ostatnia sprzed regresji) — zweryfikowane empirycznie, że 2.8.14 działa poprawnie
  ze WSZYSTKIMI lekcjami `_20_spring_core`/`_21_spring_boot`/`_22_spring_web`. **ZASADA NA
  PRZYSZŁOŚĆ (dla `_23`/`_24` i każdej kontynuacji tego rozdziału)**: gdy dodajesz JAKĄKOLWIEK
  nową zależność Spring Boot auto-konfigurowalną (starter/springdoc/podobne), ZAWSZE uruchom
  PONOWNIE (nie tylko skompiluj) co najmniej 1 WCZEŚNIEJ już zweryfikowaną, niepowiązaną lekcję z
  INNEGO rozdziału Spring — sama kompilacja NIE WYKRYJE tego rodzaju regresji uruchomieniowej,
  bo błąd pojawia się dopiero przy starcie `ApplicationContext`, nie przy `javac`.
- **Druga nowa zależność**: `spring-boot-starter-webflux` — potrzebna do `WebClient` (Lesson17).
  Dodanie NIE przełącza aplikacji na `WebApplicationType.REACTIVE` — obecność
  `spring-boot-starter-web` (klasa wskaźnikowa `DispatcherServlet`) wygrywa w
  `WebApplicationType.deduceFromClasspath()`, więc `SERVLET` pozostaje typem domyślnym mimo
  obecności WebFluksa na classpath — zweryfikowane empirycznie (wszystkie lekcje nadal startują
  na embedded Tomcacie, nie Netty). To standardowy, wspierany wzorzec "aplikacja MVC z
  `WebClient` jako klientem HTTP".

## Stan `_23_spring_data_jpa` na 2026-07-11

Rozpoczęty. Foldery WSZYSTKICH 15 lekcji już istniały (utworzone wcześniej razem z resztą bloku
Spring). **Lesson01-08 gotowe** (teoria + 30 ćwiczeń, skompilowane ORAZ uruchomieniowo
zweryfikowane `mvnw.cmd exec:java`), Lesson09-15 jeszcze NIE napisane. Następny krok:
`Lesson09_LazyLoadingAndNPlusOne`.

Warte odnotowania z Lesson08 (`@Transactional`): zweryfikowane empirycznie WSZYSTKIE 3 zjawiska
opisane w teorii — (1) `RuntimeException` w `@Transactional` COFA WSZYSTKIE zmiany, WŁĄCZNIE Z
operacją `save()`, która już się "wykonała" przed wyjątkiem; (2) CHECKED wyjątek (`Exception`, NIE
`RuntimeException`) domyślnie NIE cofa transakcji — dane ZOSTAJĄ zatwierdzone mimo wyjątku, chyba
że jawnie dodasz `rollbackFor = Exception.class`; (3) self-invocation (`this.metodaTransactional()`
wywołane Z TEJ SAMEJ klasy) OMIJA proxy Springa — `@Transactional` PO CICHU przestaje działać,
DOKŁADNIE ta sama pułapka co przy AOP w `_20_spring_core/Lesson22`, teraz zademonstrowana dla
`@Transactional` konkretnie. Lesson05 (`@Modifying` bez `@Transactional`) TEŻ zweryfikowany
empirycznie: RZUCA `InvalidDataAccessApiUsageException` (potwierdza teorię, w odróżnieniu od
`deleteById` z Lesson03, gdzie empiryczna rzeczywistość zaprzeczyła Javadocowi).

**Nowa zależność:** `org.springframework.boot:spring-boot-starter-data-jpa` — dodana BEZ
jawnej wersji (zarządzana przez `spring-boot-starter-parent` BOM, TA SAMA wersja Hibernate co
już obecne `hibernate-core`/`hibernate-envers`/`hibernate-jcache` z `_12_hibernate` —
zweryfikowane brakiem konfliktu przy kompilacji i uruchomieniu). Zweryfikowano (nauczka ze
`springdoc` w `_22_spring_web`) przez PONOWNE uruchomienie niepowiązanych, wcześniej
zweryfikowanych lekcji z `_20_spring_core`/`_22_spring_web` PO dodaniu tej zależności — zero
regresji.

### NAJWAŻNIEJSZA, CHARAKTERYSTYCZNA DLA TEGO ROZDZIAŁU pułapka: repozytoria Spring Data JPA jako ZAGNIEŻDŻONE interfejsy

Zgodnie z konwencją kursu, repozytoria (`interface XxxRepository extends JpaRepository<...>`) są
zagnieżdżone WEWNĄTRZ klasy lekcji (jak wszystko inne w tym kursie) — ale to NATRAFIA na
mechanizm CAŁKOWICIE INNY od zwykłego `@ComponentScan` (które domyślnie ZNAJDUJE zagnieżdżone
`@Component`, jak ustalono w `_20_spring_core`/`_21_spring_boot`/`_22_spring_web`):

- **`@EnableJpaRepositories` (i domyślna auto-konfiguracja repozytoriów w Spring Boot) ma WŁASNY
  skaner, z atrybutem `considerNestedRepositories`, DOMYŚLNIE `false`** — zagnieżdżone interfejsy
  repozytoriów są PO CICHU IGNOROWANE, dając `NoSuchBeanDefinitionException: No qualifying bean
  of type '...Repository' available` przy próbie `context.getBean(XxxRepository.class)`. Naprawa:
  jawna adnotacja `@EnableJpaRepositories(considerNestedRepositories = true)` NA KAŻDEJ klasie
  źródłowej (`@SpringBootApplication`/`@Configuration`) przekazywanej do `SpringApplicationBuilder`.
  **Zapamiętaj na Lesson05-15**: KAŻDA klasa-źródło w tym rozdziale MUSI mieć tę adnotację, inaczej
  `context.getBean(Repository.class)` zawsze zawiedzie.
- **DRUGA warstwa tej samej pułapki, WYKRYTA DOPIERO PO naprawieniu pierwszej**: pliki lekcji Z
  WIELOMA metodami `demonstrateXxx()` (WIĘCEJ NIŻ 1 `@SpringBootApplication`/`@Configuration` W
  TYM SAMYM pliku/pakiecie) dają `BeanDefinitionOverrideException: Cannot register bean
  definition ... since there is already ... bound`, GDY DWIE (lub więcej) klas źródłowych W TYM
  SAMYM pliku mają WŁASNE `@EnableJpaRepositories`. Przyczyna: `@SpringBootApplication` niesie ZE
  SOBĄ implicit `@ComponentScan` (jak zawsze), KTÓRY ZNAJDUJE sąsiednie klasy
  `@SpringBootApplication`/`@Configuration` W TYM SAMYM pakiecie JAKO zwykłe beany
  `@Configuration` — Spring wtedy PRZETWARZA TEŻ ICH WŁASNE `@EnableJpaRepositories`
  (bo to adnotacja typu `@Import`, uruchamiana dla KAŻDEJ odkrytej klasy `@Configuration`,
  NIEZALEŻNIE od tego, czy jest ona "aktywnym" źródłem `SpringApplicationBuilder` czy tylko
  "przypadkowo znalezionym sąsiadem") — TO SAMO repozytorium zostaje zarejestrowane DWA RAZY W
  JEDNYM kontekście. **NAPRAWA (zastosowana w Lesson02-04, obowiązuje DLA WSZYSTKICH kolejnych
  lekcji tego rozdziału Z WIĘCEJ NIŻ 1 klasą źródłową w pliku)**: ZASTĄP `@SpringBootApplication`
  kombinacją `@Configuration` + `@EnableAutoConfiguration` (BEZ `@ComponentScan`) — to ZACHOWUJE
  auto-konfigurację (`DataSource`/`EntityManagerFactory`/skanowanie encji JPA, bo
  `@EnableAutoConfiguration` niesie ZE SOBĄ `@AutoConfigurationPackage`, KTÓRY rejestruje pakiet
  DLA skanowania encji NIEZALEŻNIE od `@ComponentScan`), ale USUWA implicit component-scan, KTÓRY
  wcześniej ZNAJDOWAŁ sąsiednie klasy źródłowe i PODWAJAŁ rejestrację repozytoriów. Wzorzec:
  ```java
  @Configuration
  @EnableAutoConfiguration
  @EnableJpaRepositories(considerNestedRepositories = true)
  static class DemoApp { }
  ```
  Lesson01 (TYLKO 1 klasa źródłowa W pliku) NIE POTRZEBOWAŁ tej drugiej naprawy — `@SpringBootApplication`
  tam działa poprawnie, bo NIE MA żadnego sąsiada do przypadkowego odkrycia. **ZASADA: jeśli plik
  lekcji ma TYLKO 1 klasę źródłową, `@SpringBootApplication` + `@EnableJpaRepositories` wystarczy;
  jeśli ma WIĘCEJ NIŻ 1 (niemal zawsze W TYM kursie), UŻYWAJ `@Configuration` +
  `@EnableAutoConfiguration` ZAMIAST `@SpringBootApplication`, żeby uniknąć implicit
  component-scan.**

### Druga pułapka (mniej poważna, udokumentowana W treści Lesson03, NIE naprawiana W kodzie)

`deleteById(id)` na NIEISTNIEJĄCYM ID — Javadoc `CrudRepository` DOPUSZCZA
`EmptyResultDataAccessException`, ale zweryfikowane EMPIRYCZNIE na tej wersji Spring Data JPA
(Boot 3.4.4): `deleteById` na brakującym ID NIE RZUCA wyjątku (cichy no-op) — zachowanie
`SimpleJpaRepository` zmieniało się między wersjami. Lesson03 demonstruje to PRZEZ `try/catch`
obsługujący OBA warianty (nie zakłada z góry, który zajdzie) i drukuje RZECZYWISTY wynik zamiast
twierdzić coś, czego nie zweryfikowano. **Zasada: NIE ufaj Javadocowi bez testu na WŁASNEJ
wersji, szczególnie dla zachowań, które Spring Data zmieniał między wersjami.**
