# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1-3 — zakończone. **Etap 4 (migracja rozdziałami) — w toku.**

**DYREKTYWA UŻYTKOWNIKA:** cały projekt (wszystkie 31 rozdziałów) musi zostać przejechany migracją
w całości, bez pytania o zgodę na kontynuację (potwierdzone 2026-09-09, dwukrotnie w tej samej
sesji — użytkownik podkreślił, że zależy mu na WSZYSTKICH lekcjach od lekcji 01).

## Stan rozdziałów

- **`_01_fundamentals` (17/17) — KOMPLETNE.** Scommitowane (`97e317a`, `62a7bec`, `d158db9`).
- **`_02_oop` (15/15) — KOMPLETNE**, w tym przegląd pod kątem API_REFERENCE. Scommitowane
  (`686ff35`, `97e317a`).
- **`_03_collections` (23/23) — KOMPLETNE.** Scommitowane (`bbd54de`).
- **`_04_io` (24/24 lekcji) — KOMPLETNE.** Scommitowane (`0a1831f`).
- **`_05_multithreading` (37/37 lekcji) — KOMPLETNE.** Scommitowane w tej sesji (patrz commit
  "Etap 4: rozdzial _05_multithreading..."). Wszystkie lekcje przepisane na standard 11 sekcji.
  Lekcje 01-16, 25-29, 33-37 świadomie BEZ `API_REFERENCE` (zjawiska/mechanizmy/best practices:
  race condition, visibility, atomicity, thread-safety, synchronized, monitor, critical section,
  volatile, wait/notify, spurious wakeup, deadlock, livelock, starvation, interrupt, daemon threads,
  virtual threads, thread debugging, safe termination, best practices, common mistakes). Lekcje
  17-24, 30-32 MAJĄ pełny `API_REFERENCE` (AtomicClasses, Lock/ReentrantLock, ReadWriteLock,
  Synchronizers, ExecutorService, Callable/Future, ScheduledExecutorService, ConcurrentCollections/
  BlockingQueue, ThreadLocal, ForkJoinPool, CompletableFuture); 33_VirtualThreads też dostała krótki
  API_REFERENCE dla `Thread.ofVirtual()`/`ofPlatform()` buildera. Wszystkie 37 plików zweryfikowane:
  poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy (2 przypadki zastanej cyrylicy w
  `13_CriticalSection.json` i `34_ThreadDebugging.json` solution/hint-field naprawione w tej sesji),
  oryginalne 30 exercises + 100 quiz zachowane w każdym pliku (sprawdzone liczbowo). Zweryfikowane
  live przez API po restarcie backendu (curl) + regresja na `_01`-`_04` bez zmian.
- **`_06_networking` (14/14 lekcji) — KOMPLETNE.** Scommitowane w tej sesji (patrz commit "Etap 4:
  rozdzial _06_networking..."). Lekcje bez API_REFERENCE (koncepcje/wzorce/protokoły):
  `01_NetworkingIntroduction`, `03_Socket`, `04_SocketWhois`, `05_SocketHttpDownload`,
  `07_ServerSocketMultithreaded`, `11_HttpProtocol`. Lekcje z pełnym API_REFERENCE (konkretne
  klasy): `02_InetAddress`, `06_ServerSocket`, `08_URL`, `09_URLConnection`, `10_HttpURLConnection`,
  `12_JsonOverNetwork` (HttpClient), `13_XmlParsing` (DOM), `14_HtmlUnit` (WebClient/HtmlPage).
  Wszystkie 14 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
  oryginalne 30 exercises + 100 quiz zachowane. Zweryfikowane live przez API po restarcie
  backendu + regresja na `_01`-`_05` bez zmian.
- **`_07_servlets` (19/19 lekcji) — KOMPLETNE.** Scommitowane w tej sesji (patrz commit "Etap 4:
  rozdzial _07_servlets..."). Lekcje bez API_REFERENCE (koncepcje/wzorce): `02_ServletContainers`,
  `03_ServletProjectSetup`, `04_HttpServlet`, `07_GetAndPost`, `15_Listeners`,
  `17_ForwardAndRedirect`, `19_JSP`. Lekcje z pełnym API_REFERENCE (konkretne klasy/API):
  `01_ServletApiIntroduction` (HttpServlet), `05_HttpServletRequest`, `06_HttpServletResponse`,
  `08_OtherHttpMethods` (HttpClient), `09_FormParameters`, `10_Cookies`, `11_HttpSession`,
  `12_ServletConfig`, `13_ServletContext`, `14_Filters`, `16_ServletAnnotations`, `18_FileUpload`
  (Part). Wszystkie 19 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak
  cyrylicy, oryginalne 30 exercises + 100 quiz zachowane. Zweryfikowane live przez API po
  restarcie backendu + regresja na `_01`-`_06` bez zmian.
- **`_08_sql` (20/20 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Lekcje z pełnym
  `API_REFERENCE`: `04_SqlDataTypes`, `08_DDL`, `09_DML`, `10_Select`, `11_Filtering`,
  `12_Sorting`, `13_Grouping`, `14_Joins` (konkretne polecenia/klauzule SQL o wielu wariantach).
  Lekcje bez `API_REFERENCE` (koncepcje/wzorce): `01_DatabaseIntroduction`, `02_RelationalModel`,
  `03_TableDesign`, `05_NullValues`, `06_DataConstraints`, `07_Normalization`,
  `15_SqlRelationships`, `16_Subqueries`, `17_Views`, `18_Indexes`, `19_Transactions`,
  `20_TransactionIsolationLevels`. Lekcje 16-20 (dokończone w tej sesji) obejmują m.in.: EXISTS/
  NOT EXISTS/NOT IN+NULL pułapkę (16), widoki i łańcuch widoków (17), indeksy + selektywność +
  EXPLAIN (18), transakcje/ACID/SAVEPOINT (19), poziomy izolacji + dirty/non-repeatable/phantom
  read + lost update + blokada pesymistyczna/optymistyczna (20). Wszystkie 20 plików
  zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30
  exercises + 100 quiz zachowane w każdym pliku (sprawdzone liczbowo i live przez API po
  `mvnw.cmd resources:resources` + restarcie backendu). Regresja na `_01`-`_07` bez zmian.
- **`_09_jdbc` (20/20 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Lekcje z pełnym
  `API_REFERENCE` (konkretne klasy/interfejsy JDBC z wieloma metodami): `02_JdbcDriver`
  (Driver/DriverManager), `03_Connection`, `04_Statement`, `05_PreparedStatement`, `06_ResultSet`,
  `13_JdbcExceptions` (SQLException), `16_BatchProcessing`, `20_Mapper` (EntityMapper<E,C,R>).
  Lekcje bez `API_REFERENCE` (wzorce/wprowadzenia/koncepcje): `01_JdbcIntroduction`, `07_JdbcInsert`
  (create-and-fetch), `08_JdbcSelect` (findById/findAll), `09_JdbcUpdate`, `10_JdbcDelete`
  (hard/soft delete), `11_CreateAndDropTableFromJava`, `12_TryWithResourcesInJdbc`,
  `14_SqlInjection`, `15_JdbcTransactions`, `17_ResultSetMapping`, `18_DomainModel`, `19_Dto`.
  Wszystkie 20 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
  oryginalne 30 exercises + 100 quiz zachowane (sprawdzone liczbowo i live przez API po
  `mvnw.cmd resources:resources` + restarcie backendu). Regresja na `_01`-`_08` bez zmian.
- **`_10_dao` (28/28 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Lekcje bez `API_REFERENCE`
  (wzorce architektoniczne/koncepcje): `01_DaoIntroduction`, `02_LayeredArchitecture`,
  `04_JdbcDaoImplementation`, `05_CrudInDao`, `07_ListResultsInDao`, `08_OneToManyDao` (relacja
  1:N, N+1 problem), `09_ManyToManyDao` (relacja N:M, tabela pośrednia), `10_RepositoryVsDao`,
  `11_ConnectionFactory`, `12_DatabaseConfiguration`, `13_EnvironmentVariables`, `15_DataSource`,
  `16_ServiceLayer`, `17_TransactionsInServiceLayer`, `18_SharedConnectionAcrossDao`,
  `20_ErrorHandlingAcrossLayers`, `21_ValidationBeforeSave`, `22_PaginationInJdbc`,
  `23_DynamicSorting`, `24_DynamicFiltering`, `26_TestingDao`, `27_SqlLogging`,
  `28_JdbcBestPractices` (lekcja podsumowująca 9 zasad — PITFALL/CODE_WRONG/CODE_RIGHT oparte na
  jednym przykładzie łączącym dwie zasady naraz, zgodnie z ustaloną wcześniej konwencją). Lekcje z
  pełnym `API_REFERENCE`: `03_DaoInterface`, `06_OptionalInDao` (Optional<T>), `14_ConnectionPool`
  (HikariConfig/HikariDataSource), `19_UnitOfWork` (SqlWork<T>/UnitOfWork), `25_DatabaseMigrations`
  (Flyway: configure/dataSource/locations/load/migrate/info/validate). Naprawiono 2 przypadki
  zastanej cyrylicy (`03_DaoInterface` pole `explanation` quizu — "opartа"→"oparta";
  `23_DynamicSorting` pole `hint` ćwiczenia — "parе"→"pare"). Wszystkie 28 plików zweryfikowane:
  poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz
  zachowane w każdym pliku (sprawdzone liczbowo i live przez API po `mvnw.cmd resources:resources`
  + restarcie backendu). Regresja na `_01`-`_09` (próbka po jednej lekcji z każdego rozdziału) bez
  zmian.
- **`_11_buildtools` (30/30 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Rozdział pokrywa Ant
  (`01`-`10`: podstawy, struktura projektu, classpath, testy/JUnit, pakowanie JAR/WAR/fat-jar,
  zaawansowane mechanizmy import/macrodef/condition/antcall, Ivy, debugowanie), Maven (`11`-`18`:
  podstawy/lifecycle, zależności/scope, pluginy, properties/parent/profiles, web+db, testy
  surefire/failsafe/JaCoCo, publikacja, troubleshooting/wydajność), Gradle (`19`-`25`: podstawy,
  custom tasks/multi-project, version catalog/BOM, testy+JaCoCo, ekosystem pluginów Shadow/Spring
  Boot/Checkstyle, publishing maven-publish, troubleshooting/Daemon), oraz blok zamykający
  (`26`-`30`: porównanie 3 narzędzi, migracje między nimi, CI/CD+sekrety+wersjonowanie w praktyce,
  rodzina wyjątków classloadingu, kapszton "JavaQuest Build Lab" łączący wszystkie trzy podejścia
  w jednym projekcie). Lekcje z pełnym `API_REFERENCE` (konkretne API/DSL z wieloma
  metodami/atrybutami): `03`, `06`, `07`, `08`, `09`, `10` (embedowany Ant + taski XML), `11`-`18`
  (pom.xml/scope/pluginy/lifecycle Maven), `19`-`25` (build.gradle/konfiguracje zależności/taski
  Gradle), `29` (rodzina wyjątków classloadingu). Lekcje bez `API_REFERENCE` (koncepcje/porównania/
  wzorce): `01`, `02`, `04`, `05`, `26`, `27`, `28`, `30`. Wszystkie 30 plików zweryfikowane:
  poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, brak znaków zastępczych (U+FFFD),
  oryginalne 30 exercises + 100 quiz zachowane w każdym pliku (sprawdzone liczbowo i live przez API
  po `mvnw.cmd resources:resources` + restarcie backendu). Regresja na `_01`-`_10` bez zmian.
- **`_12_hibernate` (30/30 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Lekcje 1-8 (bootstrap,
  architektura, konfiguracja, pierwsza encja, generowanie kluczy, CRUD, Session vs EntityManager,
  transakcje), 10, 18-22, 24-29 mają pełny `API_REFERENCE` (konkretne API/adnotacje z wieloma
  metodami: Transaction/EntityTransaction, @Enumerated/AttributeConverter, HQL podstawy/zaawansowane,
  CriteriaBuilder, createNativeQuery, @NamedQuery, @Cacheable/@Cache, @Version, LockModeType,
  @Inheritance, Bean Validation adnotacje, Hibernate Envers/AuditReader). Lekcje bez `API_REFERENCE`
  (koncepcje/wzorce/mechanizmy): `09_EmbeddableTypes`, `11_OneToOneAssociation`,
  `12_OneToManyAndManyToOne`, `13_ManyToManyAssociation`, `14_CascadeTypes`,
  `15_FetchTypesAndNPlusOne`, `16_EntityLifecycle`, `17_DirtyCheckingAndFlush`, `23_FirstLevelCache`,
  `30_BestPracticesAndCapstone` (lekcja podsumowująca — PITFALL/CODE_WRONG/CODE_RIGHT oparte na
  jednym przykładzie łączącym trzy zasady naraz: długa transakcja + EAGER + brak @Version, zgodnie
  z konwencją lekcji-podsumowań). Naprawiono 1 przypadek zastanej cyrylicy w quizie
  `26_PessimisticLocking` (pole `options`/`explanation`, "przekroczyло" -> "przekroczylo", spójnie
  z ASCII-stylem otaczającego tekstu). Wszystkie 30 plików zweryfikowane: poprawny JSON,
  `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane w każdym
  pliku (sprawdzone liczbowo i live przez API po `mvnw.cmd resources:resources` + restarcie
  backendu). Regresja na `_01_fundamentals/06_StringsAndBuilder`,
  `_05_multithreading/32_CompletableFuture`, `_09_jdbc/20_Mapper`, `_11_buildtools/30_CapstoneBuildLab`
  bez zmian.
- **`_13_libraries` (32/32 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Rozdział miał 32 lekcje
  (nie 30, jak pierwotnie zanotowano) — Lombok (03-05), Apache Commons Lang3/IO/Collections4
  (06-08), Guava (09-11), OkHttp (12-14), SLF4J+Logback (15-17), Guice (18-20), MapStruct (21-22),
  Apache POI (23-24), Jsoup (25-26), Caffeine (27-28), Picocli (29-30), SnakeYAML (31-32), plus
  wprowadzenie (01) i zarządzanie zależnościami (02). Lekcje BEZ `API_REFERENCE` (wprowadzenia
  koncepcyjne): `01_WhyLibraries`, `18_WhyDependencyInjection`. Wszystkie pozostałe 30 lekcji MAJĄ
  pełny `API_REFERENCE` (konkretne biblioteki/klasy z wieloma metodami). Wszystkie 32 pliki
  zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises
  + 100 quiz zachowane w każdym pliku (sprawdzone liczbowo i live przez API po
  `mvnw.cmd resources:resources` + restarcie backendu). Regresja na `_01_fundamentals/
  06_StringsAndBuilder`, `_05_multithreading/32_CompletableFuture`, `_09_jdbc/20_Mapper`,
  `_11_buildtools/30_CapstoneBuildLab`, `_12_hibernate/29_HibernateEnvers` bez zmian.
- **`_14_advancedjava` (26/30 lekcji gotowe, JESZCZE NIE SCOMMITOWANE w chwili pisania — będą
  scommitowane na koniec tej sesji razem z tym wpisem) — W TOKU.** Rozdział pokrywa generics,
  lambdy/functional interfaces, adnotacje, refleksję, nowoczesny system typów, niemutowalność/SPI,
  i dalej (27-30) prawdopodobnie JPMS (moduły) — lekcja 26 wspomina "Lekcja 27 - JPMS podstawy,
  Lekcja 28 - JPMS zaawansowane", więc 29-30 nieznane, sprawdź listę plików w katalogu przy
  wznowieniu. Gotowe: blok generyków 1-7, blok lambd 8-10, blok adnotacji 11-14, blok refleksji
  15-18, blok nowoczesnego systemu typów 19-22 (wszystkie opisane we wcześniejszych wpisach tego
  pliku), oraz NOWY blok niemutowalności/SPI 23-26: `23_VarAndTypeInference` (var jako local-type
  inference, gdzie działa/nie działa, pusty diament, BEZ API_REFERENCE), `24_Immutability` (final
  chroni tylko referencję nie zawartość, przepis na pełną niemutowalność, BEZ API_REFERENCE),
  `25_DefensiveCopying` (List.copyOf vs Collections.unmodifiableList, płytka vs głęboka kopia, BEZ
  API_REFERENCE), `26_ServiceLoaderAndSpi` (SPI jako wzorzec, ServiceLoader, META-INF/services,
  powiązanie z JDBC/NIO/XML z wcześniejszych rozdziałów, Z API_REFERENCE). Wszystkie 26 plików
  zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises
  + 100 quiz zachowane, live przez API po `mvnw.cmd resources:resources` + restarcie backendu.
  Regresja na `_01_fundamentals/06_StringsAndBuilder`, `_13_libraries/32_YamlToObjectMapping` bez
  zmian. **UWAGA: backend bywa zabijany przez system z powodu niskiej pamięci — PRZED restartem
  zawsze najpierw zatrzymaj STARY proces backendu (TaskStop) i DOPIERO POTEM uruchom nowy (nie
  trzymaj dwóch instancji Spring Boot naraz) — sprawdzone, działa niezawodnie przez całą sesję.
  Sprawdzaj wolną pamięć PowerShell: `Get-CimInstance Win32_OperatingSystem | Select FreePhysicalMemory`.**
  **NASTĘPNY KROK: kontynuuj lekcję 27** (sprawdź dokładną nazwę pliku w katalogu
  `_14_advancedjava`, prawdopodobnie JPMS podstawy wg wzmianki w lekcji 26), dalej po kolei do
  `30`. Po ukończeniu wszystkich 30: `mvnw.cmd resources:resources` + restart backendu + curl
  weryfikacyjny + regresja na `_01`-`_13` + JEDEN commit lokalny całego rozdziału.
- **`_15_...` i dalsze (do `_31_spring_cloud_microservices`) — NIE ROZPOCZĘTE.**
  Migracja rozdziałami, po kolei, bez pomijania żadnego.

## Ostatnia ukończona czynność

Dokończono rozdziały `_12_hibernate` (30/30, KOMPLETNE) i `_13_libraries` (32/32, KOMPLETNE), oraz
rozpoczęto `_14_advancedjava` (26/30, W TOKU — bloki generyków 1-7, lambd/method references 8-10,
adnotacji 11-14, refleksji 15-18, nowoczesnego systemu typów 19-22, niemutowalności/SPI 23-26) w
tej sesji. `mvnw.cmd resources:resources` + restart backendu + weryfikacja live próbek + regresja
bez zmian po każdym checkpoincie + commity: jeden dla `_12_hibernate`, trzy kolejne dla
`_13_libraries`, sześć kolejnych dla częściowego stanu `_14_advancedjava` (5/30, potem 10/30, potem
14/30, potem 18/30, potem 22/30, potem 26/30). WAŻNA LEKCJA operacyjna z tej sesji: przed
restartem backendu ZAWSZE najpierw zatrzymaj stary proces (TaskStop), dopiero potem uruchom nowy —
trzymanie dwóch instancji Spring Boot naraz przy niskiej wolnej pamięci (obserwowano spadek do ok.
2.7GB/16GB) ryzykuje, że system zabije proces backendu; stosowanie tej kolejności rozwiązało
problem do końca sesji (żaden restart po jej wdrożeniu się nie wysypał). Zgodnie z dyrektywą
użytkownika ("cisnij dalej nie pytaj się pomiędzy lekcjami o zgodę") migracja była kontynuowana bez
zatrzymywania się między lekcjami i rozdziałami — przy następnym "kontynuuj" wznów
`_14_advancedjava` dokładnie od lekcji 27, patrz "Następny krok" niżej.

## Wyniki testów / weryfikacji

- 28/28 plików `_10_dao` — poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy (po naprawie
  w `03_DaoInterface` i `23_DynamicSorting`), 30 exercises + 100 quiz zachowane liczbowo. Live curl
  po `mvnw.cmd resources:resources` + restarcie backendu: poprawna liczba bloków teorii (14 lub 15
  z API_REFERENCE), 30 exercises, 100 quiz — sprawdzone dla wszystkich lekcji 21-28 (nowych w tej
  sesji). Regresja na `_01_fundamentals/06_StringsAndBuilder`, `_02_oop/11_ObjectClass`,
  `_03_collections/08_HashMap`, `_04_io/20_Gson`, `_05_multithreading/32_CompletableFuture`,
  `_06_networking/14_HtmlUnit`, `_07_servlets/10_Cookies`, `_08_sql/16_Subqueries`,
  `_09_jdbc/20_Mapper` — wszystkie bez zmian.

## Problemy i decyzje

- **WAŻNA LEKCJA TEJ SESJI — backend czyta z `target/classes`, NIE bezpośrednio z `src/main/resources`:**
  `LessonContentLoader` (`platform/content/LessonContentLoader.java`) wczytuje pliki JSON przez
  `ClassPathResource`, czyli z `target/classes/content/...`, skopiowanych tam przez Maven przy
  buildzie. Samo edytowanie plików w `src/main/resources/content/` i restart `mvnw.cmd spring-boot:run`
  BEZ wcześniejszego odświeżenia zasobów powoduje, że backend serwuje STARĄ (albo brakującą) treść —
  endpoint zwraca `[]` zamiast bloków teorii, MIMO że lekcja istnieje w bazie (bo `ContentSeeder`
  zasila tylko metadane nawigacyjne). **Rozwiązanie: zawsze uruchom `mvnw.cmd resources:resources`
  (szybkie, nie kompiluje kodu Java) PRZED restartem backendu za każdym razem, gdy zmieniono TYLKO
  pliki JSON z treścią** — dopiero potem `mvnw.cmd spring-boot:run`. Zweryfikuj to sprawdzając
  `grep -c "type" target/classes/content/<rozdzial>/<lekcja>.json` przed restartem.
- Kontynuacja zasady z poprzednich rozdziałów: lekcje o KONKRETNEJ klasie/API z wieloma metodami
  (AtomicInteger, Lock, ExecutorService, ConcurrentHashMap, ThreadLocal, ForkJoinPool,
  CompletableFuture...) dostają pełny `API_REFERENCE`; lekcje o zjawiskach/mechanizmach/wzorcach
  (race condition, deadlock, livelock, starvation, interrupt, synchronized jako słowo kluczowe,
  best practices, common mistakes...) NIE dostają — nie są "katalogiem metod jednej klasy".
- Dwa przypadki zastanej cyrylicy w treści (`13_CriticalSection.json` w polu `solution`,
  `34_ThreadDebugging.json` w polu `hint`) wykryte przez standardowy grep cyrylicy i naprawione —
  pojedyncze znaki cyrylicy wmieszane w polski tekst (np. "dajа" zamiast "daja", "Waznа" zamiast
  "Wazna"), najpewniej zaszłość z wcześniejszego etapu pisania kursu, nie błąd tej sesji.
- Ustalona zasada dot. lekcji-podsumowań (jak `28_JdbcBestPractices` w `_10_dao`, wcześniej
  `36_BestPractices`/`37_CommonMistakes` w `_05_multithreading`, które w oryginale miały nietypowy
  format z wieloma "zasadami"/"błędami" w jednym): nadal wymagają pełnej struktury 11 sekcji, ale
  PITFALL/CODE_WRONG/CODE_RIGHT można oprzeć na JEDNYM, reprezentatywnym przykładzie łączącym kilka
  zasad/błędów naraz, zamiast próbować upchnąć wszystkie oddzielnie.
- **Skrypty `fix_allcaps.js`/`fix_allcaps_residual.js` przeniesione z tymczasowego scratchpada do
  repo:** żyją teraz na stałe w `scripts/content-migration/` (scommitowane), NIE w
  `scratchpad/` (katalog tymczasowy per-sesja, czyszczony między sesjami — poprzedni zapis w tym
  pliku był nieaktualny). Wołaj je jako `node scripts/content-migration/fix_allcaps.js <plik.json>`
  i analogicznie dla `_residual.js`.
- **JAVA_HOME nie jest ustawiony domyślnie w tej powłoce** — przed `mvnw.cmd` (resources:resources
  ORAZ spring-boot:run) trzeba ręcznie: `export JAVA_HOME="/c/Users/kapit/.jdks/openjdk-25.0.2"` +
  `export PATH="$JAVA_HOME/bin:$PATH"` (JDK 17 pod `.jdks/ms-17.0.17` też dostępny, ale projekt
  wymaga Javy 21 — `openjdk-25.0.2` kompiluje z `--release 21` poprawnie).
- **Port 8080 jest TRWALE zajęty przez systemowy proces "AgentService"** (nie backend Javy) — to
  oczekiwane i już udokumentowane w `application.properties`. Platforma nasłuchuje na porcie 8082.
- **Background `spring-boot:run` przez Bash tool: NIE łączyć `nohup ... &` z `run_in_background:
  true`** — uruchomienie samodzielnego `&` wewnątrz polecenia powoduje, że sam wrapper kończy się
  natychmiast (fałszywe "completed"), a proces potomny bywa zabity razem z drzewem procesów. Podaj
  `./mvnw.cmd spring-boot:run > out.log 2> err.log` WPROST jako komenda z `run_in_background: true`,
  bez własnego `&`/`nohup` — tool sam zarządza cyklem życia w tle.

## Następny krok (dokładnie, w kolejności)

1. Kontynuuj `_14_advancedjava` (30 lekcji) dokładnie od lekcji 27 (sprawdź dokładną nazwę pliku w
   katalogu — bloki 1-26 już ukończone, patrz "Stan rozdziałów"), tym samym wzorcem pracy (patrz
   punkt 3 niżej). Dla lekcji o konkretnej klasie/API z wieloma metodami PISZ OD RAZU z pełnym
   katalogiem API_REFERENCE; dla lekcji o koncepcjach/mechanizmach języka pomiń go. PRZED
   restartem backendu ZAWSZE zatrzymaj stary proces (TaskStop) jako pierwszy krok, potem dopiero
   uruchom nowy (patrz "Problemy i decyzje" o pamięci).
2. Kontynuuj rozdziałami `_15_...` do `_31_...` w kolejności, bez pomijania żadnego, zgodnie z
   dyrektywą użytkownika z Etapu 4 ("kontynuuj prace, nie pytaj się o zgodę pomiędzy rozdziałami",
   potwierdzone ponownie 2026-09-10). Po każdym rozdziale: `mvnw.cmd resources:resources` (odśwież
   zasoby) + restart backendu + curl na próbce + regresja na poprzednich rozdziałach + JEDEN
   commit lokalny dla całego rozdziału.
3. **Wzorzec pracy ustalony i sprawdzony w tej sesji (powtarzaj dla każdej kolejnej lekcji/rozdziału):**
   a) Grep `"exercises": [` żeby znaleźć koniec sekcji theory bez czytania całego pliku.
   b) Read tylko sekcji theory (offset/limit).
   c) Edit zastępujący WYŁĄCZNIE starą tablicę theory nową (11 sekcji + API_REFERENCE, gdy
      uzasadnione, w kolejności: INTRO, DEFINITION, ANALOGY, VISUAL_EXAMPLE, CODE_BASIC,
      CODE_PRACTICAL, STEP_BY_STEP, NOTE, USAGE, PITFALL, CODE_WRONG, CODE_RIGHT,
      [API_REFERENCE], WHEN_TO_USE, SUMMARY) — NIGDY Write na pliku z istniejącymi
      exercises/quiz.
   d) Uruchom `node scripts/content-migration/fix_allcaps.js <plik.json>` i
      `node scripts/content-migration/fix_allcaps_residual.js <plik.json>`.
   e) Waliduj (node, bez polegania na `grep -P` — locale w tej powłoce go nie wspiera): JSON valid
      + liczba bloków theory/exercises(30)/quiz(100), `(tekst.match(/native code/g)||[]).length`,
      `(tekst.match(/[Ѐ-ӿ]/g)||[]).length` (cyrylica) — napraw natychmiast jeśli coś
      wykryte, nawet jeśli to zastana treść sprzed tej sesji.
   f) Po całym rozdziale: `export JAVA_HOME=...` + `mvnw.cmd resources:resources` + restart
      backendu (`run_in_background: true`, BEZ własnego `&`/`nohup`) + curl na próbce nowych lekcji
      + regresja na próbce z poprzednich rozdziałów + JEDEN commit lokalny dla całego rozdziału +
      aktualizacja tego pliku (`WORK_PROGRESS.md`).
