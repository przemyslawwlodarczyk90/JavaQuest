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
- **`_14_advancedjava` (30/30 lekcji) — KOMPLETNE.** Scommitowane w tej sesji. Rozdział pokrywa
  pięć klastrów: generyki (1-7: raw types, Pair<K,V>, bounded types, wildcards/PECS, type erasure,
  best practices), programowanie funkcyjne (8-11: functional interfaces/SAM, lambda/effectively
  final, method references, java.util.function), adnotacje/refleksja/mechanizmy dynamiczne (12-18:
  @Override/@Deprecated, własne @interface, retencja/annotation processing, Reflection API,
  dynamic proxies, MethodHandles), nowoczesny system typów (19-22: sealed classes, pattern matching
  instanceof, switch pattern matching/record patterns, switch expressions), wnioskowanie
  typów/niemutowalność/modularność (23-30: var, immutability, defensive copying, ServiceLoader/SPI,
  JPMS podstawy i zaawansowane, best practices, kapston łączący wszystko). Lekcje z pełnym
  `API_REFERENCE`: `10` (method references), `11` (java.util.function), `26` (ServiceLoader). Reszta
  BEZ API_REFERENCE (koncepcje/mechanizmy językowe). Wszystkie 30 plików zweryfikowane: poprawny
  JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane w
  każdym pliku (sprawdzone liczbowo i live przez API po `mvnw.cmd resources:resources` + restarcie
  backendu). Regresja na `_01_fundamentals/06_StringsAndBuilder`,
  `_05_multithreading/32_CompletableFuture`, `_09_jdbc/20_Mapper`, `_12_hibernate/29_HibernateEnvers`,
  `_13_libraries/32_YamlToObjectMapping` bez zmian. **WAŻNA LEKCJA OPERACYJNA z tej sesji: backend
  bywa zabijany przez system z powodu niskiej pamięci — PRZED restartem zawsze najpierw zatrzymaj
  STARY proces backendu (TaskStop) i DOPIERO POTEM uruchom nowy (nie trzymaj dwóch instancji Spring
  Boot naraz) — sprawdzone, działa niezawodnie. Sprawdzaj wolną pamięć PowerShell:
  `Get-CimInstance Win32_OperatingSystem | Select FreePhysicalMemory`.**
- **`_15_jvm_internals` (20/20 lekcji) — KOMPLETNE.** Rozdział schodzi poniżej API do samej JVM:
  classloading/bajtkod (1-5, Z API_REFERENCE dla javap w lekcji 2), obszary pamięci heap/stack/
  metaspace (6-7, Z API_REFERENCE dla MemoryMXBean w lekcji 6), Garbage Collector podstawy i
  algorytmy (8-12: hipoteza generacyjna/GC roots Z API_REFERENCE dla GarbageCollectorMXBean w 8,
  Serial/Parallel/G1/ZGC-Shenandoah BEZ API_REFERENCE w 9, G1 regiony/RSet/CSet/Mixed Collections
  BEZ API_REFERENCE w 10, ZGC/Shenandoah colored/Brooks pointers BEZ API_REFERENCE w 11, strojenie
  i Unified JVM Logging Z API_REFERENCE w 12), JIT (13-14: tiered compilation/OSR/deoptymalizacja Z
  API_REFERENCE flag diagnostycznych w 13, escape analysis/scalar replacement/inlining Z
  API_REFERENCE flag diagnostycznych w 14), wycieki pamięci (15, BEZ API_REFERENCE — zjawisko, nie
  klasa), diagnostyka produkcyjna (16-19: heap dump Z API_REFERENCE dla HotSpotDiagnosticMXBean/
  jcmd/jmap w 16, thread dump Z API_REFERENCE dla ThreadMXBean/jcmd/jstack w 17, JFR Z
  API_REFERENCE dla Recording/jcmd JFR.*/jfr CLI w 18, profilowanie Z API_REFERENCE dla
  async-profiler/JMH w 19), oraz capstone (20, BEZ API_REFERENCE, metodyka i antywzorce strojenia
  na jednym reprezentatywnym scenariuszu). Wszystkie 20 plików zweryfikowane: poprawny JSON,
  `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane, live
  przez API po `mvnw.cmd resources:resources` + restarcie backendu (wszystkie 20 lekcji sprawdzone
  curl-em na końcu — poprawna liczba bloków 14 lub 15). Regresja na `_01_fundamentals`,
  `_02_oop/11_ObjectClass`, `_09_jdbc/20_Mapper`, `_10_dao/28_JdbcBestPractices`,
  `_11_buildtools/30_CapstoneBuildLab`, `_12_hibernate/30_BestPracticesAndCapstone`,
  `_13_libraries/32_YamlToObjectMapping`, `_14_advancedjava/30_CapstoneAdvancedJava` — wszystkie bez
  zmian. Scommitowane w dwóch commitach tej sesji (lekcje 9-14, potem 15-20 z finalną adnotacją
  "ROZDZIAL KOMPLETNY 20/20"). **UWAGA operacyjna: lekcje 9-10 zostały napisane w POPRZEDNIEJ
  sesji, ale NIE scommitowane wtedy (git log pokazywał commit tylko do lekcji 8) — odkryte i
  naprawione na początku tej sesji.**
- **`_16_clean_code` (22/22 lekcji) — KOMPLETNE.** Scommitowane (5 commitów, do `67a1096`,
  lekcje 18-22 dopisane w poprzedniej sesji ale WORK_PROGRESS.md nie był zaktualizowany — stan
  odkryty i naprawiony na początku tej sesji). Wszystkie 22 pliki zweryfikowane w tej sesji:
  poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, 30 exercises + 100 quiz zachowane
  liczbowo, WSZYSTKIE BEZ API_REFERENCE poza `20_StaticAnalysisTools` (15 bloków — PMD/SpotBugs).
  Zweryfikowane live przez API po restarcie backendu (lekcje 18-22 + regresja na
  `_01_fundamentals/06_StringsAndBuilder`, `_09_jdbc/20_Mapper`,
  `_15_jvm_internals/20_JvmTuningAndBestPracticesCapstone` — wszystkie bez zmian).
- **`_17_architecture` (20/20 lekcji) — KOMPLETNE, dokończone w tej sesji.** 20 lekcji o
  architekturze aplikacji: fundamenty (1-2: po co architektura, ADR), warstwy i role (3-5: layered
  architecture, Controller/Service/Repository, anemiczny vs bogaty model), granice modelu i API
  (6-9: bounded context/DDD-lite, DTO/mapper, wersjonowanie API, package-by-layer vs by-feature),
  kierunek zależności i hexagonal (10-12: Dependency Rule, hexagonal intro, porty/adaptery w
  praktyce), przekroje poprzeczne (13-16: granice transakcji, cache, walidacja, obsługa błędów),
  organizacja systemów (17-19: modularny monolit, komunikacja zdarzeniowa, kiedy mikroserwisy mają
  sens), kapston (20: "Platforma Zapisów na Kursy" łącząca wszystkie 19 lekcji w 1 działający
  modularny monolit). **WAŻNE ODKRYCIE tej sesji: cały rozdział był w STARYM formacie treści**
  (typy `CONCEPT`/`CODE_EXAMPLE`, nie 11-sekcyjny standard) — wymagał pełnej przebudowy teorii jak
  rozdziały `_01`-`_16`; wykonano dla wszystkich 20 lekcji. Znaleziony i poprawiony błąd
  merytoryczny z oryginalnej treści: autorstwo "Big Ball of Mud" (1997) to Brian Foote i Joseph
  Yoder (nie "Foster i Hyland/Hylanda" jak było w starej treści) — poprawiono w lekcji 1.
  **ZNANY, ŚWIADOMIE ODŁOŻONY PROBLEM (dotyczy też prawdopodobnie `_18_rest_api`,
  `_19_security_basics`): exercises/quiz tego rozdziału są napisane CELOWO ASCII-only** (bez
  polskich znaków) — udokumentowana decyzja z sesji pisania SAMEGO KURSU Javy
  (`COURSE_CONTENT_HISTORY.md` ok. linii 799-838, dot. plików źródłowych `_17`-`_19`), którą
  odziedziczyła wygenerowana z niej treść JSON platformy. Zgodnie z wzorcem z 16 poprzednich
  rozdziałów NIE modyfikowano exercises/quiz poza uruchomieniem
  `fix_allcaps.js`/`fix_allcaps_residual.js` (te nie przywracają diakrytyków, tylko naprawiają
  WIELKIE LITERY) — pełne odtworzenie polskich znaków w exercises/quiz dla `_17`-`_19` to osobne,
  większe zadanie (wymaga podejścia słownikowego, nie global-replace), zostawione do Etapu 5
  audytu. Cała NOWO PISANA teoria ma pełne polskie znaki jak wszędzie indziej. Wszystkie 20 plików
  zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30
  exercises + 100 quiz zachowane liczbowo w każdym pliku.
- **`_18_rest_api` (20/20 lekcji) — KOMPLETNE.** 20 lekcji o projektowaniu REST API: HTTP deep
  dive, REST intro (6 ograniczeń Fieldinga + Richardson Maturity Model), zasoby/endpointy, metody
  HTTP z API_REFERENCE, kody statusu z API_REFERENCE, ciało request/response, content negotiation,
  projektowanie JSON, path/query params, paginacja/sortowanie/filtrowanie, cache HTTP (ETag/
  If-Match), projektowanie błędów (RFC 7807), błędy walidacji (collect-all), wersjonowanie
  (4 strategie), idempotencja (klucz idempotencji), rate limiting (token bucket), Postman,
  OpenAPI/Swagger, REST vs RPC vs GraphQL, kapston "JavaQuest Tasks API" łączący wszystkie 19
  lekcji w 1 działające mini-API. Ten sam problem ASCII-only w exercises/quiz co `_17_architecture`
  (odłożony do Etapu 5, patrz wyżej). Wszystkie 20 plików zweryfikowane: poprawny JSON,
  `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane.
- **WAŻNY BŁĄD ZNALEZIONY I NAPRAWIONY W TEJ SESJI: `fix_allcaps.js` psuł metody HTTP.**
  Skrypt używany od początku migracji (Etap 4, wszystkie rozdziały) nie miał `GET`/`POST`/`PUT`/
  `PATCH`/`DELETE`/`HEAD`/`OPTIONS` na liście `WHITELIST` — trakt ował je jak nadużycie WIELKICH
  LITER i zamieniał na `get`/`post`/... (środek zdania) albo `Get`/`Post`/... (początek zdania) w
  polach `exercises`/`quiz`. Naprawiono `WHITELIST` (dodano metody HTTP + kilka innych częstych
  skrótów: HATEOAS, RBAC, CORS, CSRF, SOLID itp.) i napisano `fix_http_method_case.js` do naprawy
  już uszkodzonej treści. **Naprawiono i scommitowano `_06_networking` i `_07_servlets`**
  (jedyne dotąd ukończone rozdziały intensywnie omawiające metody HTTP) — **PRZED przejściem do
  kolejnych rozdziałów Spring (`_20`+) sprawdź, czy `_19_security_basics` (gdy powstanie) i każdy
  rozdział wspominający GET/POST/PUT/DELETE/PATCH w exercises/quiz też przechodzi przez
  `fix_http_method_case.js`** — dodane teraz do standardowego wzorca pracy (patrz punkt 3d niżej).
- **`_19_security_basics` (21/21 lekcji) — KOMPLETNE.** 21 lekcji (nie 20 — kapston to lekcja 21):
  AuthN vs AuthZ (1), hasła — hashowanie i BCrypt (2-3), sesje/ciasteczka (4), JWT (5), OAuth2/OIDC
  (6), RBAC/ABAC (7), HTTPS/TLS (8), CORS (9), CSRF (10), XSS (11), nagłówki bezpieczeństwa (12),
  SQL injection deep dive (13), niebezpieczna deserializacja (14), XXE (15), path traversal/upload
  (16), walidacja wejścia (17), zarządzanie sekretami (18), bezpieczne logowanie/audyt z łańcuchem
  skrótów (19), bezpieczeństwo zależności/supply chain (20), kapston OWASP Top 10 łączący
  wszystkie 20 lekcji w 1 zabezpieczony mini-endpoint (21). Ten sam problem ASCII-only w
  exercises/quiz co `_17`/`_18` (odłożony do Etapu 5). Każda lekcja przeszła przez
  `fix_http_method_case.js` — rozdział intensywnie wspomina GET/POST/PUT/DELETE w przykładach.
  Wszystkie 21 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
  oryginalne 30 exercises + 100 quiz zachowane.
- **`_20_spring_core` (23/23 lekcji) — KOMPLETNE.** Scommitowane (`f6b420b`). 23 lekcje (nie 20 —
  odkryto podczas pracy, że rozdział ma pełne fundamenty Spring Core): co to jest Spring i przegląd
  wersji (1-2), IoC/Hollywood principle (3), Dependency Injection ogólnie (4), style konfiguracji
  XML/Java/adnotacje (5), Bean i ApplicationContext (6-7), stereotypy @Component/@Service/
  @Repository/@Controller (8), @Configuration+@Bean pełny vs lite mode z CODE_WRONG/CODE_RIGHT (9),
  constructor injection (10), field injection i dlaczego unikać (11), @Qualifier/@Primary (12),
  cykliczne zależności i zmiana domyślnego zachowania w Spring Boot 2.6 (13), zakresy beanów
  singleton/prototype i pułapka "zamrożonego" prototype naprawiana przez ObjectProvider (14),
  profile @Profile z negacją/koniunkcją (15), właściwości konfiguracyjne @Value/Environment/
  PropertySource (16), SpEL (17), callbacki cyklu życia @PostConstruct/@PreDestroy vs
  InitializingBean/DisposableBean vs initMethod/destroyMethod (18), BeanPostProcessor/
  BeanFactoryPostProcessor jako mechanizm pod spodem @Autowired/@PostConstruct (19), zdarzenia
  aplikacyjne ApplicationEventPublisher/@EventListener jako rozwiązanie cyklicznych zależności (20),
  podstawy AOP z pełnym API_REFERENCE 5 rodzajów rady i JDK proxy vs CGLIB (21), zaawansowane
  pointcuty execution() i pułapka self-invocation omijająca proxy (22), kapston "JavaQuest Order
  Processing" łączący wszystkie 22 lekcje w jednym gołym `AnnotationConfigApplicationContext` bez
  Spring Boota (23). Każda lekcja przeszła przez `fix_http_method_case.js` — rozdział wspomina
  GET/POST/PUT w kontekście `@GetMapping`/`@PostMapping`; potwierdzono, że word-boundary regex nie
  koliduje z nazwami adnotacji Spring (np. "GetMapping" nie jest dotykane, bo nie ma dopasowania na
  granicy słowa w obie strony). Wszystkie 23 pliki zweryfikowane: poprawny JSON, `grep -c
  "native code"` = 0, brak cyrylicy (jedno przypadkowe wystąpienie cyrylickiego "е" w słowie "ideę"
  w lekcji 15 znalezione i naprawione ręcznie), oryginalne 30 exercises + 100 quiz zachowane, live
  przez API (próbki: lekcje 1, 13, 21, 23) i regresja bez zmian na `_19_security_basics`,
  `_18_rest_api`, `_17_architecture`, `_07_servlets` (w tym potwierdzono, że naprawa
  `fix_http_method_case.js` z poprzedniej sesji trzyma się — pozostałe małe litery get/post/put w
  `_07_servlets` to wyłącznie legalne fragmenty URL-i i wywołania metod Map, nie uszkodzone nazwy
  metod HTTP), `_01_fundamentals`.
- **`_21_spring_boot` (16/16 lekcji) — KOMPLETNE.** Scommitowane (`2d3bd84`). 16 lekcji: co to jest
  Spring Boot i różnica wobec gołego Spring Framework (1), setup projektu przez Spring Initializr
  (2), startery jako "kurator zależności" wsparty przez BOM (3), auto-konfiguracja z pełnym
  API_REFERENCE adnotacji `@Conditional*` (4), `application.properties`/`.yml` automatyczne
  ładowanie bez `@PropertySource` (5), profile w Boot z automatycznym ładowaniem
  `application-{profil}` i profile groups (6), `CommandLineRunner`/`ApplicationRunner` (7),
  `@ConfigurationProperties` z walidacją fail-fast i alternatywą rekordową (8), DevTools i mechanizm
  dwóch classloaderów (9), logowanie Logback auto-konfigurowane (10), `BasicErrorController` i
  `ResponseStatusException` (11), Actuator i własny `HealthIndicator` (12), Micrometer/Observation
  API i distributed tracing, różnica Boot 2 vs Boot 3 (Sleuth→Micrometer Tracing) (13), budowanie
  wykonywalnego jara i natywny obraz GraalVM (14), własna klasa `@AutoConfiguration` i publikowalny
  starter dwumodułowy (15), kapston "JavaQuest Notes Service" łączący wszystkie 15 lekcji w
  działającym mini-serwisie REST z prawdziwym wbudowanym Tomcatem (16). Każda lekcja przeszła przez
  `fix_http_method_case.js` — rozdział wspomina GET/POST w kontekście REST endpointów demonstracyjnych
  (`@GetMapping` nie było dotknięte, potwierdzone). Wszystkie 16 plików zweryfikowane: poprawny
  JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane,
  live przez API (próbki: lekcje 1, 4, 8, 16 — w tym potwierdzenie API_REFERENCE w lekcji 4) i
  regresja bez zmian na `_20_spring_core` (lekcje 1 i 13), `_19_security_basics`, `_18_rest_api`,
  `_07_servlets`, `_01_fundamentals`.
- **`_22_spring_web` (19/19 lekcji) — KOMPLETNE.** Scommitowane (`a29984c`). 19 lekcji: @Controller
  vs @RestController (1), @RequestMapping i skróty GET/POST/PUT/PATCH/DELETE z pełnym
  API_REFERENCE (2), @PathVariable (3), @RequestParam z 3 wariantami opcjonalności — wymagany/
  defaultValue/Optional (4), @RequestBody i automatyczna deserializacja JSON (5), ResponseEntity i
  pełna kontrola statusu/nagłówków/ciała (6), DTO w REST API oddzielone od encji, Request DTO ≠
  Response DTO (7), @Valid i udokumentowana pułapka: @Validated+ConstraintViolationException daje
  500 bez własnego handlera, nie 400 (8), @RestControllerAdvice — globalna obsługa błędów kontra
  lokalny @ExceptionHandler (9), własne ErrorResponseDto vs wbudowany ProblemDetail zgodny z RFC
  7807 (10), HttpMessageConverter i content negotiation przez produces/consumes (11), stronicowanie
  ręczne jako fundament pod przyszły Spring Data (12), sortowanie i filtrowanie ręczne (13), upload
  plików przez MultipartFile (14), CORS w Spring MVC z podwójną warstwą ochrony — serwer i
  przeglądarka (15), HandlerInterceptor vs Filter (16), trzy klienty HTTP RestTemplate/WebClient/
  RestClient z pełnym API_REFERENCE porównawczym (17), dokumentacja API przez springdoc-openapi/
  Swagger UI generowana automatycznie ze skanowania kontrolerów (18), kapston "JavaQuest Tasks API"
  łączący wszystkie 18 lekcji w działającym REST API na Spring MVC (19). Każda lekcja przeszła przez
  `fix_http_method_case.js` — rozdział intensywnie wspomina GET/POST/PUT/PATCH/DELETE w kontekście
  Spring MVC. Jedno przypadkowe cyrylickie "а" w słowie "dajа" znalezione i naprawione w lekcji 2
  (w tekście exercises/quiz, nie w nowo napisanej teorii). Wszystkie 19 plików zweryfikowane:
  poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz
  zachowane, live przez API (próbki: lekcje 1, 2, 8, 17, 19) i regresja bez zmian na
  `_21_spring_boot`, `_20_spring_core`, `_19_security_basics`, `_18_rest_api`, `_07_servlets`,
  `_01_fundamentals`.
- **`_23_spring_data_jpa` (15/15 lekcji) — KOMPLETNE.** Scommitowane (`6bac804`). 15 lekcji: co to
  jest Spring Data JPA i dynamiczny proxy repozytorium (1), hierarchia interfejsów
  Repository→CrudRepository→PagingAndSortingRepository→JpaRepository z pełnym API_REFERENCE (2),
  pułapka `save()` INSERT vs UPDATE po obecności ID i niepewność `deleteById` na brakującym ID
  (wersja zależna) (3), query methods generowane z nazwy metody z pełnym API_REFERENCE słów
  kluczowych (4), custom queries przez `@Query` z JPQL/SQL natywnym i `@Modifying` wymagające
  transakcji (5), `Pageable`/`Page`/`Slice` jako automatyzacja ręcznego stronicowania z
  `_22_spring_web` (6), relacje encji przez repozytoria z kaskadowym zapisem i nawigacją przez
  podkreślnik w query methods (7), `@Transactional` z dwoma udokumentowanymi pułapkami — checked
  wyjątek nie cofa domyślnie, self-invocation omija proxy (8), N+1 zmierzony naprawdę przez własny
  `StatementInspector` i naprawiony `JOIN FETCH` (9), `@EntityGraph` jako deklaratywna alternatywa
  dla `JOIN FETCH` (10), projekcje zamknięte/otwarte/DTO/dynamiczne (11), `Specification` jako
  dynamiczne query budowane programowo (12), JPA Auditing z pułapką cichego `null` bez
  `AuditorAware` (13), automatyczna integracja Flyway z pułapką rekurencyjnego skanowania
  lokalizacji (14), kapston "Biblioteka" łączący wszystkie 14 lekcji w działającej domenie
  Autor-Książka z prawdziwym schematem Flyway (15). Każda lekcja przeszła przez
  `fix_http_method_case.js` (bez istotnych trafień — rozdział nie dotyczy bezpośrednio HTTP).
  Wszystkie 15 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy
  (jedna literówka własnego autorstwa "PROZNIEJSZY" zamiast "PÓŹNIEJSZY" w lekcji 13 wykryta i
  naprawiona przed uruchomieniem fix scripts), oryginalne 30 exercises + 100 quiz zachowane, live
  przez API (próbki: lekcje 1, 2, 4, 9, 15) i regresja bez zmian na `_22_spring_web`,
  `_21_spring_boot`, `_20_spring_core`, `_19_security_basics`, `_18_rest_api`, `_12_hibernate`,
  `_01_fundamentals`.
- **`_24_spring_security` (17/17 lekcji) — KOMPLETNE w tej sesji.** 17 lekcji: co to jest Spring
  Security i łańcuch filtrów servletowych przed `DispatcherServlet` (1), `SecurityFilterChain` jako
  jedyny aktualny sposób konfiguracji (2), ewolucja API `WebSecurityConfigurerAdapter`→bean,
  `authorizeRequests/antMatchers`→`authorizeHttpRequests/requestMatchers`,
  `@EnableGlobalMethodSecurity`→`@EnableMethodSecurity` (3), domyślne logowanie formularzowe z
  sesją i CSRF (4), `UserDetailsService` (5), `PasswordEncoder`/BCrypt/`DelegatingPasswordEncoder`
  z pełnym API_REFERENCE (6), role i uprawnienia `hasRole`/`hasAuthority` (7), jawna konfiguracja
  `formLogin` z `defaultSuccessUrl`/`failureUrl` (8), warstwowe reguły autoryzacji per metoda HTTP z
  `denyAll()` fallback (9), `@PreAuthorize`/`@PostAuthorize` i pułapka self-invocation (10), własna
  strona logowania przez `.loginPage()` (11), własny filtr JWT (`OncePerRequestFilter`) (12),
  `SessionCreationPolicy`/`STATELESS` (13), `.cors()`/`.csrf()` wbudowane w Security (14), OAuth2
  Client vs Resource Server, `.oauth2ResourceServer()` (15), `AuthenticationEntryPoint` vs
  `AccessDeniedHandler` ze spójnym JSON (16), kapston łączący wszystkie 16 lekcji w jednym API z
  JWT+RBAC+CORS+spójne błędy (17). Lekcja 1 była już częściowo przepisana na początku tej sesji
  (niescommitowana zmiana z poprzedniej sesji) — dokończona i zweryfikowana. Każda lekcja przeszła
  przez `fix_allcaps.js` + `fix_allcaps_residual.js` + `fix_http_method_case.js` (rozdział wspomina
  GET/POST w kontekście `.requestMatchers(HttpMethod.GET, ...)`). Wszystkie 17 plików zweryfikowane:
  poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz
  zachowane liczbowo w każdym pliku.
- **`_25_unit_testing` (20/20 lekcji) — KOMPLETNE w tej sesji.** 20 lekcji: po co testy
  jednostkowe i piramida testów (1), architektura JUnit 5 Platform/Jupiter/Vintage (2), pierwszy
  test i wbudowane asercje `Assertions.*` z pełnym API_REFERENCE (3), adnotacje cyklu życia
  `@BeforeEach`/`@AfterEach`/`@BeforeAll`/`@AfterAll` i `PER_METHOD`/`PER_CLASS` (4), wprowadzenie
  do AssertJ i łańcuchowe `assertThat` (5), AssertJ dla kolekcji i map z `extracting` (6), AssertJ
  dla wyjątków i własne asercje przez `AbstractAssert` (7), `@ParameterizedTest` z pełnym
  API_REFERENCE źródeł danych (8), `@Nested`/`@DisplayName` (9), kontrola kolejności testów i
  `@RepeatedTest` (10), wykonywanie warunkowe i `Assumptions` (11), `@Tag` i filtrowanie (12),
  podstawy Mockito z pełnym API_REFERENCE (13), matchery argumentów i `ArgumentCaptor` (14),
  `@Mock`/`@InjectMocks`/`MockitoExtension` (15), taksonomia dubli testowych (dummy/fake/stub/spy/
  mock) (16), systematyczne testowanie przypadków brzegowych (17), nazewnictwo i organizacja
  testów (AAA, `@Nested` wg scenariusza) (18), podstawy pokrycia kodu przez JaCoCo z pełnym
  API_REFERENCE (19), kapston łączący JUnit 5+AssertJ+Mockito w jednym pełnym pakiecie testów dla
  `OrderService` (20). Lekcja 1 była już częściowo przepisana na początku tej sesji (niescommitowana
  zmiana z poprzedniej sesji, tego samego rozdziału `_24`) — nie dotyczyło `_25`, cały rozdział
  `_25` przepisany od zera w tej sesji. Każda lekcja przeszła przez `fix_allcaps.js` +
  `fix_allcaps_residual.js` (rozdział nie dotyczy bezpośrednio HTTP, `fix_http_method_case.js`
  pominięty świadomie). Wszystkie 20 plików zweryfikowane: poprawny JSON, `grep -c "native code"` =
  0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane liczbowo, live przez API (próbki:
  lekcje 1, 5, 8, 13, 16, 20) i regresja bez zmian na `_24_spring_security` (lekcje 1, 17),
  `_23_spring_data_jpa`, `_20_spring_core`, `_19_security_basics`, `_01_fundamentals`.
- **`_26_integration_testing` (16/16 lekcji) — KOMPLETNE w tej sesji.** 16 lekcji: co to jest test
  integracyjny i różnica wobec jednostkowego (1), cztery główne wyzwania — szybkość, niestabilność,
  setup/teardown, izolacja (2), H2 in-memory a produkcyjne silniki baz danych (3), wprowadzenie do
  Testcontainers (4), testowanie repozytorium JDBC przeciw Testcontainers (5), cykl życia i reuse
  kontenera (6), wprowadzenie do WireMock (7), stubowanie i weryfikacja żądań HTTP (8), testowanie
  systemu plików i I/O (9), strategie zarządzania danymi testowymi — Test Data Builder i seeder
  (10), izolacja testów i idempotentność (11), niestabilne testy i pięć sposobów ich naprawy (12),
  wprowadzenie do testów kontraktowych/Consumer-Driven Contracts (13), integracja testów z
  pipeline'ami CI/CD (14), siedem zasad dobrych praktyk testów integracyjnych (15), kapston łączący
  wszystkie 15 lekcji w scenariuszu przetwarzania zamówień z prawdziwą bazą H2 + stubowanym
  serwisem płatności przez WireMock (16). Każda lekcja przeszła przez `fix_allcaps.js` +
  `fix_allcaps_residual.js`; lekcje 7, 8 i 16 (WireMock, wspominające GET/POST) dodatkowo przez
  `fix_http_method_case.js`, retroaktywnie zastosowany też do lekcji 1-6 i 9-15 (bez istotnych
  trafień poza lekcją 5). Wszystkie 16 plików zweryfikowane: poprawny JSON, `grep -c "native code"`
  = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane liczbowo, live przez API (próbki:
  lekcje 1, 4, 7, 10, 13, 16) i regresja bez zmian na `_25_unit_testing` (lekcje 1, 20),
  `_24_spring_security`, `_23_spring_data_jpa`, `_01_fundamentals`.
- **`_27_spring_test` (20/20 lekcji) — KOMPLETNE w tej sesji.** 20 lekcji: przegląd modułu Spring
  Test i `SpringExtension` (1), podstawy `@SpringBootTest` (2), opcje `webEnvironment` z pełnym
  API_REFERENCE (3), koncepcja testów wycinkowych (4), `@WebMvcTest`+`MockMvc` (5), `@DataJpaTest`+
  `TestEntityManager` (6), `@JsonTest` (7), `@MockitoBean`/`@MockitoSpyBean` (8),
  `@TestConfiguration` (9), `@ActiveProfiles` w testach (10), `@TestPropertySource`+
  `@DynamicPropertySource` (11), `TestRestTemplate`+`WebTestClient` z pełnym API_REFERENCE (12),
  testowanie zabezpieczonych endpointów `@WithMockUser` (13), testowanie kodu transakcyjnego
  `TestTransaction` (14), `@ServiceConnection` dla Testcontainers (15), testowanie
  `@Async`/`@Scheduled` (16), cachowanie kontekstu i wydajność testów (17), ArchUnit z pełnym
  API_REFERENCE (18), dobre praktyki Spring Test (19), kapston łączący wszystko w pełnym teście
  API zadań z prawdziwą bazą + mockiem + deterministycznym czasem + ArchUnit (20). Lekcje 5, 12,
  13, 20 (dotyczące MockMvc/TestRestTemplate/HTTP) przeszły przez `fix_http_method_case.js`.
  Wszystkie 20 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
  oryginalne 30 exercises + 100 quiz zachowane liczbowo, live przez API (próbki: lekcje 1, 5, 9,
  12, 18, 20) i regresja bez zmian na `_26_integration_testing` (lekcje 1, 16),
  `_25_unit_testing`, `_24_spring_security`, `_01_fundamentals`.
- **`_28_java_evolution` (24/24 lekcji) — KOMPLETNE.** Wszystkie 24 lekcje (retrospektywa Java 8 →
  25: cykl wydań/LTS, lambdy/streamy/Optional w perspektywie historycznej, metody default/static w
  interfejsach, java.time, JPMS i mniejsze funkcje Javy 9, var w Javie 10, nowe metody
  String/Files + HttpClient w Javie 11, mechanizm preview features na przykładzie switch-expression
  Java 12-14, text blocks, rekordy + pattern matching instanceof w Javie 16, sealed classes w Javie
  17, UTF-8 domyślne + Simple Web Server w Javie 18, wątki wirtualne i structured concurrency Java
  19-21, pattern matching w switch + record patterns + Sequenced Collections w Javie 21, Unnamed
  Variables/FFM API/Stream Gatherers Java 22-24, Scoped Values/Compact Source Files Java 25, wybór
  wersji Javy dla nowego projektu, kapston "JavaQuest Task Processor" łączący sealed rekordy +
  pattern matching + wątki wirtualne + SequencedMap + text blocks) przepisane na standard 11 sekcji
  (14-15 bloków, `API_REFERENCE` tylko tam, gdzie uzasadnione: nowe metody String/Files z lekcji 9,
  Sequenced Collections z lekcji 20 — reszta to zjawiska/ewolucja języka, nie katalogi metod jednej
  klasy). Każda lekcja przez `fix_allcaps.js` + `fix_allcaps_residual.js`; lekcja 10 (HttpClient,
  GET) dodatkowo przez `fix_http_method_case.js`. Wszystkie 24 pliki zweryfikowane: poprawny JSON,
  `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane
  liczbowo, live przez API (próbki: lekcje 1, 4, 9, 10, 15, 18, 20, 24) i regresja bez zmian na
  `_27_spring_test` (lekcje 1, 20), `_26_integration_testing`, `_25_unit_testing`,
  `_01_fundamentals`. Scommitowane checkpointami WIP w ciągu sesji (lekcje 1-3 były już w toku z
  poprzedniej, przerwanej sesji), scommitowane finalnym commitem `7020eb0` ("Etap 4: rozdzial
  _28_java_evolution - 24/24 lekcje... - KOMPLETNE") po ośmiu WIP checkpointach z tej sesji.
- **`_29_spring_reactive` (17/17 lekcji) — KOMPLETNE.** Cały rozdział przepisany na standard 11
  sekcji: dlaczego reaktywność/C10K/backpressure (1), specyfikacja Reactive Streams przez
  `java.util.concurrent.Flow` (2), Project Reactor - Mono/Flux jako implementacja, lazy evaluation
  (3), Mono w szczególe (4), Flux w szczególe (5), operatory map/flatMap/concatMap (6), obsługa
  błędów/retry/fallback (7), subscribeOn/publishOn (8), MVC vs WebFlux (9), kontrakt kontrolera/SSE
  (10), WebFlux.fn (11), WebClient (12), R2DBC (13), Reactive Security (14), StepVerifier (15),
  kiedy reaktywność a kiedy blokujący kod (16), kapston łączący cały rozdział z realnym gapem
  naprawionym w teorii (status 201/204 wyprowadzany z wyniku zapisu/liczby usuniętych wierszy, nie
  zakładany z góry) (17). Wszystkie 17 plików zweryfikowane: poprawny JSON, teoria 14-15 bloków w
  poprawnej kolejności 11 sekcji + API_REFERENCE gdzie uzasadnione (12/17 lekcji ma API_REFERENCE;
  1-3, 5, 9 i 16 świadomie bez — czyste koncepcje/porównania), `grep -c "native code"` = 0, brak
  cyrylicy, oryginalne 30 exercises + 100 quiz zachowane liczbowo w każdym pliku. Zweryfikowane
  live przez API po `mvnw.cmd resources:resources` + restarcie backendu (lekcje 13-17 + regresja na
  `_01_fundamentals` bez zmian) — backend zatrzymany po weryfikacji.
  **BŁĄD ZNALEZIONY I NAPRAWIONY w tej sesji: `fix_http_method_case.js` uszkodził prawdziwy kod R2DBC
  w `13_R2dbcIntro.json`** — skrypt zamienił `ConnectionFactories.get(...)` i `row.get(...)`
  (legalne wywołania metod Javy w polu `hint`, nie HTTP GET) na `.GET(...)`, bo dopasowuje słowo
  "get" bez względu na to, czy jest poprzedzone kropką (wywołanie metody) czy nie (czasownik HTTP).
  Naprawiono ręcznie (41 wystąpień cofniętych do `.get(`). **Wniosek na przyszłość (rozdziały 30-31):
  NIE uruchamiaj `fix_http_method_case.js` "na wszelki wypadek" na lekcjach, które nie dotyczą
  bezpośrednio HTTP/REST — dla lekcji, które trzeba nim objąć, PO uruchomieniu sprawdź
  `grep -nE '\.(GET|POST|PUT|PATCH|DELETE|HEAD|OPTIONS)\(' plik.json` i ręcznie zweryfikuj, czy to
  faktycznie kod HTTP, a nie metoda Javy (np. `Map.get`, `List.put`, encja `Rezerwacja.delete`, itp.)
  przypadkowo złapana przez dopasowanie na granicy słowa.** Scommitowane finalnym commitem tej
  sesji.
- **`_30_spring_messaging_and_async` (16/16 lekcji) — KOMPLETNE.** Cały rozdział przepisany z
  formatu CONCEPT/CODE_EXAMPLE na standard 11 sekcji: `@Async`/`@EnableAsync` (1), konfiguracja
  `ThreadPoolTaskExecutor` (2), `@Async` z `CompletableFuture` (3), `@Scheduled`/`@EnableScheduling`
  (4), `ApplicationEvent`/`@EventListener` + `@Async` (5), surowe JMS (6), `JmsTemplate`/
  `@JmsListener` (7), koncepcje AMQP/RabbitMQ (8), Spring AMQP (`RabbitTemplate`/`@RabbitListener`)
  (9), koncepcje Kafki (10), Spring Kafka (`KafkaTemplate`/`@KafkaListener`) (11), wzorce
  architektoniczne message-driven (12), retry + Dead Letter Queue (13), testowanie kodu
  asynchronicznego/messagingu (14), RabbitMQ vs Kafka (15), kapston "JavaQuest Order Processing"
  łączący cały rozdział (16). Wszystkie 16 plików zweryfikowane: poprawny JSON, teoria 14-15 bloków
  w poprawnej kolejności 11 sekcji (API_REFERENCE tylko w lekcji 2 `ThreadPoolTaskExecutor` i 6
  surowe JMS — reszta to mechanizmy/wzorce/porównania, nie katalogi metod jednej klasy),
  `grep -c "native code"` = 0, brak cyrylicy, oryginalne 30 exercises + 100 quiz zachowane
  liczbowo. **WAŻNE ODKRYCIE: `fix_http_method_case.js` NIE URUCHOMIONY na żadnej lekcji tego
  rozdziału** — sprawdzono explicite, że jedyne dopasowania słów GET/POST/PUT/... w polach
  narracyjnych to `Future.get()`/`CompletableFuture.get()` (prawdziwe wywołania metod Javy, nie
  HTTP), więc uruchomienie skryptu uszkodziłoby je tak samo jak wcześniej `ConnectionFactories.get()`
  w `_29/13_R2dbcIntro.json` — nauczka z tamtego bledu zastosowana świadomie. Zweryfikowane live
  przez API po `mvnw.cmd resources:resources` + restarcie backendu (próbki lekcji 1, 4, 8, 11, 16)
  + regresja na `_29_spring_reactive/17_ReactiveCapstone` bez zmian.
- **`_31_spring_cloud_microservices` (19/19 lekcji) — KOMPLETNE.** Ostatni rozdział całego kursu.
  Spring Cloud jako "parasol" projektów (1), service discovery koncepcyjnie (2), Eureka (3),
  Config Server + klient (4-5), API Gateway + filtry (6-7), client-side load balancing (8),
  circuit breaker koncepcyjnie + Resilience4j (9-10), distributed tracing koncepcyjnie + Zipkin
  (11-12), OpenFeign (13), wzorzec Saga (14), Spring Cloud Stream (15), bezpieczeństwo
  międzyserwisowe/defense in depth (16), konteneryzacja (multi-stage Docker) (17), observability
  (trzy filary: logi/metryki/tracing) (18), kapston "JavaQuest Microservices Demo" łączący
  Eurekę+LoadBalancer+Gateway+JWT+tracing/metryki w 5 kontekstach Spring w 1 JVM (19). Wszystkie
  19 plików zweryfikowane: poprawny JSON, teoria 14 bloków w poprawnej kolejności 11 sekcji +
  API_REFERENCE gdzie uzasadnione (żaden — cały rozdział to koncepcje/wzorce/porównania
  narzędzi, nie katalogi metod jednej klasy), `grep -c "native code"` = 0, brak cyrylicy,
  oryginalne 30 exercises + 100 quiz zachowane liczbowo. `fix_http_method_case.js` uruchomiony
  bezpiecznie na lekcjach 6/7/13/16/19 (dotyczących HTTP) DOPIERO PO sprawdzeniu grep na
  wystąpienia `.metoda(` (0 fałszywych trafień w całym rozdziale — nauczka z `_29/13_R2dbcIntro`
  zastosowana konsekwentnie). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
  restarcie backendu + regresja na `_29_spring_reactive`/`_30_spring_messaging_and_async` bez
  zmian.

## CAŁY KURS (31/31 rozdziałów) — Etap 4 ZAKOŃCZONY

Wszystkie 31 rozdziałów (`_01_fundamentals` … `_31_spring_cloud_microservices`) są teraz na
standardzie 11 sekcji. Zobacz `STAGE2_LESSON_REDESIGN_PROMPT.md` sekcja "Etap 5 – audyt całości"
— to jedyny zaplanowany krok, który mógł pozostać po zamknięciu Etapu 4 (spójność wizualna,
powtarzające się treści, brakujące zagadnienia, błędy techniczne, polskie znaki, pozostałości
oznaczenia "lekcja gotowa"). Nie rozpoczęty w tej sesji — do potwierdzenia z użytkownikiem, czy
i kiedy go podjąć.

## PLANOWANIE (puste rozdziały) — 8 nowych rozdziałów dla pozostałych tematów "do opracowania"

Po zamknięciu `_32`/`_33`/`_34` (wszystkie 44 tematy `"critical"` w 100% pokryte), użytkownik
poprosił o rozpisanie (na razie PUSTYCH, bez treści JSON) rozdziałów/lekcji dla WSZYSTKICH
pozostałych tematów z `critical-topics.json` oznaczonych jako "do opracowania" — z DWOMA
wyjątkami na wyraźną prośbę użytkownika: `other-react`/`other-angular` USUNIĘTE CAŁKOWICIE z
`critical-topics.json` (nie będą miały żadnego rozdziału), a `other-nosql` ma dostać TYLKO
lekki, 3-lekcyjny rozdział ("jedynie zaczaczyć z grubsza", nie pełny kurs).

Dodano do `ChapterSeedData.java` (SAME nazwy lekcji, ZERO plików treści JSON — każda lekcja
pokazuje się w nawigacji jako "treść w przygotowaniu", zweryfikowane live przez API):

- **`_35_docker_compose`** (8 lekcji) — pokrywa `tools-docker-compose` (very-important),
  naturalne przedłużenie `_34_docker_fundamentals`.
- **`_36_kubernetes_fundamentals`** (6 lekcji, CELOWO płytki) — pokrywa `cloud-kubernetes`
  (important); raport rynkowy WPROST odradza głębsze wejście na tym etapie kariery.
- **`_37_cloud_aws_fundamentals`** (9 lekcji) — pokrywa `cloud-aws` (important); AWS wybrany
  jako "jeden dostawca" zgodnie z rekomendacją raportu.
- **`_38_redis_and_caching`** (7 lekcji) — pokrywa `other-redis` (important).
- **`_39_observability_prometheus_grafana`** (7 lekcji) — pokrywa
  `observability-prometheus-grafana` (important).
- **`_40_rest_assured_testing`** (5 lekcji, celowo krótszy — buduje na `_25`/`_26`/`_27`) —
  pokrywa `testing-rest-assured` (important).
- **`_41_nosql_overview`** (3 lekcje, NAJKRÓTSZY celowo) — pokrywa `other-nosql` (important),
  wyłącznie ogólna świadomość/orientacja, NIE pełny kurs NoSQL.

**AKTUALIZACJA (ta sama sesja, natychmiast po powyższym): użytkownik kazał USUNĄĆ
`_35_linux_fundamentals` w całości** ("wypierdol linuxa, to bedzie zupelnie co innego") —
Linux/terminal to ODRĘBNY temat, świadomie wyłączony z tej partii rozdziałów. Rozdział USUNIĘTY
z `ChapterSeedData.java`, pozostałe 7 rozdziałów PRZENUMEROWANE w dół, żeby zamknąć lukę
(`_36`→`_35` Docker Compose, `_37`→`_36` Kubernetes, `_38`→`_37` AWS, `_39`→`_38` Redis,
`_40`→`_39` Prometheus/Grafana, `_41`→`_40` REST Assured, `_42`→`_41` NoSQL). `tools-linux` w
`critical-topics.json` WRACA do stanu "do opracowania" bez żadnego przypisanego rozdziału —
NIE jest już częścią zaplanowanej kolejki, do ewentualnego osobnego potraktowania później, jeśli
użytkownik zdecyduje inaczej. Zweryfikowano: `mvnw compile` bez błędów, `_35_linux_fundamentals`
zwraca teraz HTTP 404 (nie istnieje), wszystkie 7 przenumerowanych rozdziałów widocznych w
nawigacji z poprawną liczbą lekcji, regresja na `_01_fundamentals`/`_34_docker_fundamentals` bez
zmian.

`critical-topics.json`: `other-react`/`other-angular` USUNIĘTE z pliku (nie "do opracowania" —
w ogóle nieobecne). Pozostałych 8 tematów WCIĄŻ oznaczonych jako "do opracowania" (`chapterSlug:
null`) — CELOWO nie zaktualizowano ich na nowe rozdziały, żeby zakładka "Krytyczne" nie pokazywała
przedwcześnie "w kursie" dla lekcji bez żadnej treści — flip nastąpi ROZDZIAŁ PO ROZDZIALE, w
miarę pisania treści (dokładnie ten sam wzorzec co przy `_32`/`_33`/`_34`).

Zweryfikowano: `mvnw compile` bez błędów, wszystkie 8 rozdziałów widoczne w nawigacji live przez
API z poprawną liczbą lekcji, przykładowa lekcja (`_35/01_WhyLinuxMatters`) faktycznie zwraca
pustą treść (`[]`), regresja na `_01_fundamentals`, `_32`, `_33`, `_34` bez zmian. Scommitowane
jednym commitem (czysto strukturalna zmiana, bez treści merytorycznej do recenzji per-lekcja).

**NASTĘPNY KROK: napisać treść dla tych 7 rozdziałów, rozdział po rozdziale, tym samym wzorcem
co `_32`/`_33`/`_34`** (14-15 bloków teorii, zmienna mała liczba exercises/quiz) — kolejność
nieustalona przez użytkownika, sugerowana: `_35_docker_compose` ("very-important", naturalna
kontynuacja `_34`) jako pierwszy.

**UWAGA operacyjna z tej sesji (powtórzona, TRZECI raz): osierocone procesy `java.exe` znalezione
PONOWNIE przed weryfikacją tej zmiany (tym razem 4 procesy: dwie pary, w tym jedna z INNEGO JDK —
`ms-17.0.17` — nieużywanego świadomie w żadnym poleceniu tej sesji) — zatrzymane ręcznie przez
`Stop-Process -Force` przed każdym restartem. To się powtarza wystarczająco często (3 razy w tej
sesji), żeby potraktować to jako STANDARDOWY krok, nie wyjątek: ZAWSZE sprawdzaj `Get-Process
-Name java` i zatrzymaj wszystko PRZED każdym `spring-boot:run`, niezależnie od tego, czy
poprzedni `TaskStop` zgłosił sukces.**

## `_34_docker_fundamentals` (11/11 lekcji) — KOMPLETNE, napisany od zera w tej sesji

Trzeci rozdział dopisany PO zamknięciu Etapu 4 (po `_32`, `_33`). W przeciwieństwie do `_32`/`_33`
(które pokrywały tematy CAŁKOWICIE nieobecne w kursie), ten rozdział pokrywa temat, który JUŻ MIAŁ
przypisany rozdział w `critical-topics.json` (`_31_spring_cloud_microservices/17_Containerizing
SpringBootApps`), ale z JAWNĄ notatką: "Kurs uczy Dockera w kontekście konteneryzacji mikroserwisów
(multi-stage build) - warto dodać osobny, wprowadzający rozdział z docker run/volumes/networks."
Po dokończeniu tego rozdziału zaktualizowano wpis `tools-docker-basics`: wskazuje teraz na
`_34_docker_fundamentals/03_DockerfileBasics`, `note` wyczyszczone na `null` — **WSZYSTKIE 44
tematy priorytetu `"critical"` w `critical-topics.json` mają teraz przypisany rozdział BEZ
żadnej notatki flagującej niepełne pokrycie** (sprawdzone programowo po tej sesji).

ŚWIADOMIE NIE obejmuje: Docker Compose (`tools-docker-compose`, osobny temat "very-important"),
Kubernetes (`cloud-kubernetes`, "important") — zgodnie z wyraźną instrukcją użytkownika w tej
sesji ("linux bedzie zupelnie innym tematem, nie rob go jesli nie jest w lekcjach krytycznych,
jedynie jesli cos zahacza o krytyczne") ograniczono zakres WYŁĄCZNIE do tego, co dotyczyło
BEZPOŚREDNIO tematu krytycznego (Dockerfile/image/container/run/volumes/networks — dokładnie
zakres z notatki), NIE rozszerzając na sąsiednie, ale osobne tematy niższego priorytetu.

Ten sam lekki format co `_32`/`_33` (14-15 bloków teorii, `API_REFERENCE` niemal wszędzie — cały
rozdział to konkretne polecenia CLI Dockera — 3-4 unikalne ćwiczenia / 5 unikalnych quizów na
lekcję). 11 lekcji: kontener jako izolowany proces dzielący jądro hosta vs pełna VM + analogia
kontenera transportowego (1), format nazwy obrazu i ID jako prawdziwa tożsamość vs tag jako
ruchoma/nieruchoma etykieta — bezpośrednia analogia do gałęzi/tagu Gita z `_33/Lesson03,11` (2),
Dockerfile — FROM/WORKDIR/COPY/RUN/EXPOSE/ENTRYPOINT/CMD + `.dockerignore` jako odpowiednik
`.gitignore` z `_33/Lesson12` (3), `docker run` (NOWY kontener) vs `docker start` (ISTNIEJĄCY) +
`-p HOST:KONTENER` (4), warstwy jako niezmienne migawki + cache budowania, efekt domina przy
unieważnieniu (5), `ENV`/`ARG`/`-e` + pułapka sekretu wypieczonego na stałe w warstwie obrazu,
analogia do zacommitowanego sekretu z `_33/Lesson12` (6), wolumeny jako przechowywanie niezależne
od cyklu życia kontenera (7), sieci Dockera z wbudowanym DNS + pułapka `localhost` między
kontenerami (8), multi-stage build (JDK+Maven w etapie budowania, TYLKO JRE w finalnym) (9),
`USER` + `COPY --chown=` przeciw domyślnemu rootowi + pełna checklist dobrych praktyk (10),
kapston "JavaQuest Tasks API + PostgreSQL" łączący wszystkie 10 lekcji w jeden dwukontenerowy
system, z pułapką "kontener działa" != "usługa gotowa" (`HEALTHCHECK` jako rozwiązanie) (11).

Wszystkie 11 plików napisane, zwalidowane (JSON poprawny, `grep -c "native code"` = 0, brak
cyrylicy/znaków zastępczych — DWA przypadkowe znaki cyrylicy znalezione i naprawione ręcznie
w trakcie pisania, w lekcjach 9 i 10) i przepuszczone przez `fix_allcaps.js`/
`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy HTTP) w tej samej
sesji co napisanie. Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie
backendu (wszystkie 11 lekcji rozdziału, PO odczekaniu na ustąpienie krótkiego wyścigu startowego)
+ regresja na `_01_fundamentals/06_StringsAndBuilder`, `_09_jdbc/20_Mapper`, `_32_algorithms_and_
data_structures/15_AlgorithmsCapstone`, `_33_git_essentials/16_GitWorkflowCapstone`, `_31_spring_
cloud_microservices/19_MicroservicesCapstone` — bez zmian. Scommitowane 12 komitami WIP (jeden na
lekcję + jeden dla scaffoldu ChapterSeedData z lekcją 1).

**UWAGA operacyjna z tej sesji: przed startem backendu do weryfikacji `_34`, znaleziono DWA
osierocone procesy `java.exe` (ten sam PID startowy co poprzedni, niezabity poprawnie przez
wcześniejsze `TaskStop` mimo komunikatu o sukcesie) — zatrzymane ręcznie przez PowerShell
`Stop-Process -Force` PRZED uruchomieniem nowego backendu, zgodnie z ustaloną zasadą "zawsze
zatrzymaj STARY proces przed uruchomieniem nowego". Warto przy przyszłych sesjach sprawdzać
`Get-Process -Name java` PRZED każdym nowym `spring-boot:run`, nawet jeśli poprzedni `TaskStop`
zgłosił sukces.**

## `_33_git_essentials` (16/16 lekcji) — KOMPLETNE, napisany od zera w tej sesji

Drugi rozdział dopisany PO zamknięciu Etapu 4 (po `_32_algorithms_and_data_structures`),
pokrywający JEDYNY temat oznaczony w `critical-topics.json` priorytetem `"critical"` bez
przypisanego rozdziału: `tools-git` ("Git - codzienne operacje (branch, merge, rebase, PR)",
wcześniej `chapterSlug: null` z notatką "Brak dedykowanego rozdziału o Git w kursie").
Zaktualizowano ten wpis: teraz wskazuje `_33_git_essentials/07_PullRequestWorkflow`.

Ten sam lekki format co `_32` (14-15 bloków teorii, 11 standardowych sekcji + `API_REFERENCE`
gdzie uzasadnione — TU niemal wszędzie, bo cały rozdział to konkretne polecenia CLI Gita —
ZMIENNA, mała liczba exercises/quiz, 4-6 ćwiczeń / 6-8 quizów na lekcję, NIE stałe 30/100).

16 lekcji: dlaczego kontrola wersji + rozproszony vs scentralizowany model (1), repozytorium/
HEAD/odwołania względne + `--amend` (2), branching jako lekki, ruchomy wskaźnik (3), merge
fast-forward vs trójstronny + rozwiązywanie konfliktów (4), rebase + złota zasada "nigdy na
współdzielonej historii" (5), remote/fetch/pull/push + non-fast-forward (6), Pull Request
workflow jako funkcja platformy na wierzchu branch+push+merge (7), stash jako stos LIFO (8),
mapa 4 poziomów cofania: restore/reset (3 tryby)/revert (9), cherry-pick jako selektywny
rebase pojedynczego commita + backport (10), tagi jako nieruchome wskaźniki (kontrast z
gałęzią z lekcji 3) (11), gitignore (filtr NA WEJŚCIU) vs gitattributes + wyciek sekretu w
historii (12), blame + bisect jako binary search na commitach (jawne połączenie z
`_32_algorithms_and_data_structures/Lesson08`) (13), Conventional Commits + strategie
branchowania (trunk-based/GitHub Flow/GitFlow) (14), git hooks + pułapka `.git/hooks/`
niewspółdzielonego między klonami (15), kapston "JavaQuest Tasks API" łączący wszystkie 15
lekcji w jeden tydzień pracy zespołu (branch->stash->rebase+konflikt->PR->tag->bisect->
cherry-pick backport, z pułapką pokusy `reset --hard` na współdzielonej gałęzi release) (16).

Wszystkie 16 plików napisane, zwalidowane (JSON poprawny, `grep -c "native code"` = 0, brak
cyrylicy/znaków zastępczych) i przepuszczone przez `fix_allcaps.js`/`fix_allcaps_residual.js`
(BEZ `fix_http_method_case.js` — rozdział nie dotyczy HTTP) w tej samej sesji co napisanie.
Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie backendu
(wszystkie 16 lekcji rozdziału, PO odczekaniu na ustąpienie krótkiego wyścigu startowego
opisanego w uwadze operacyjnej wyżej) + regresja na `_01_fundamentals/06_StringsAndBuilder`,
`_09_jdbc/20_Mapper`, `_32_algorithms_and_data_structures/15_AlgorithmsCapstone`,
`_31_spring_cloud_microservices/19_MicroservicesCapstone` — bez zmian. Scommitowane 16
komitami WIP (jeden na lekcję) + wpis całego rozdziału w `ChapterSeedData.java` dodany
razem z lekcją 1 w jednym commicie.

## `_32_algorithms_and_data_structures` (15/15 lekcji) — KOMPLETNE, dopisany PO zamknięciu Etapu 4

Nowy rozdział (NIE część oryginalnego Etapu 4 migracji `_01`-`_31`) dodany, żeby pokryć lukę
zidentyfikowaną w raporcie rynkowym o rekrutacji Java w Polsce (`kytyyczne tematy.md` w katalogu
głównym) — kurs nie miał dedykowanego miejsca na wzorce algorytmiczne sprawdzane na assessmentach
rekrutacyjnych (two pointers, sliding window, BFS/DFS itd.). Zakładka "Krytyczne" na platformie
(dodana w commicie `c02bc60`, PRZED rozpoczęciem tego rozdziału) linkuje tu dla tematów
algorytmicznych. `ChapterSeedData.java` już zawierał wpis dla wszystkich 15 lekcji tego rozdziału
(dopisany z wyprzedzeniem w tej samej sesji/commicie co zakładka "Krytyczne").

**Struktura inna niż rozdziały `_01`-`_31`:** ta sama teoria 14-15 blokow (11 standardowych sekcji
+ `API_REFERENCE` gdzie uzasadnione), ALE exercises/quiz mają ZMIENNĄ, MAŁĄ liczbę pozycji
(6-12 unikalnych ćwiczeń/pytań na lekcję, NIE stałe 30/100 jak w reszcie kursu) — świadoma decyzja
zastana na początku pracy nad tym rozdziałem (widoczna już w lekcjach 1-11 napisanych we
wcześniejszej sesji), kontynuowana w lekcjach 12-15 dokończonych w tej sesji.

15 lekcji: Big-O (1), tablice/stringi (2), two pointers (3), sliding window (4), wzorce hashmap (5),
stack/queue (6), sortowanie (7), binary search (8), rekurencja/backtracking (9), wzorce na liście
jednokierunkowej (10), drzewa binarne/BST (11), BFS (12), DFS (13), systematyczny proces
rozpoznawania wzorca na rozmowie rekrutacyjnej — ZROZUM/DOPASUJ/ZAPLANUJ/ZAIMPLEMENTUJ/PRZETESTUJ
(14, lekcja metodologiczna BEZ API_REFERENCE), kapston "JavaQuest Friend Network" łączący BFS+DFS
w jednej klasie z celową pułapką współdzielonego stanu `visited` między niezależnymi wywołaniami
metod — błąd ogólny (zarządzanie cyklem życia stanu obiektu), nie specyficzny dla samego
algorytmu (15).

Lekcje 12-15 dokończone i scommitowane w TEJ sesji (poprzednia sesja/agent zostawiła rozdział na
12/15, NIEscommitowane lekcje 13-15 nie istniały jeszcze). Wszystkie 4 pliki (12-15) przeszły przez
`fix_allcaps.js`/`fix_allcaps_residual.js` (bez `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy/znaków
zastępczych (U+FFFD — jeden przypadkowy w lekcji 14 znaleziony i naprawiony ręcznie PRZED
uruchomieniem fix scripts). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu (wszystkie 15 lekcji rozdziału + regresja na `_01_fundamentals/
06_StringsAndBuilder`, `_09_jdbc/20_Mapper`, `_31_spring_cloud_microservices/
19_MicroservicesCapstone` — bez zmian). Zaktualizowano też `critical-topics.json`: wpis
`algo-bfs-dfs` (wcześniej `chapterSlug: null` z notatką "brak w kursie") teraz wskazuje na
`_32_algorithms_and_data_structures/12_BreadthFirstSearch`.

**UWAGA operacyjna z tej sesji — fałszywy alarm przy weryfikacji API:** wzorzec
`curl ... | node -e "process.stdin..."` (pipe bezpośrednio do node) w tej powłoce Git Bash
sporadycznie dawał `PARSE_ERR`/puste wyniki dla poprawnie działających endpointów — objaw
nie miał nic wspólnego z backendem (potwierdzone: te same endpointy zwracały poprawną,
pełną treść po zapisaniu `curl -s ... > plik.json` do prawdziwej ścieżki Windows i odczycie
przez `Read`). Podobnie, mieszanie stylu ścieżek POSIX (`/c/Users/...`) jako argumentu dla
`node -e "...readFileSync('/c/Users/...')"` zawodzi (node.exe NIE tłumaczy ścieżek Git Bash,
dostaje dosłownie `C:\c\Users\...`) — użyj ścieżki Windows (`C:/Users/...` albo
`C:\Users\...`) przy odczycie plików przez `node`/`Read`, nie ścieżki POSIX z Bash.
**Wniosek na przyszłość:** przy weryfikacji API w tej powłoce, zapisuj `curl` do pliku pod
prawdziwą ścieżką Windows i sprawdzaj rozmiar/treść pliku (`ls -la`, `Read`), zamiast ufać
wynikowi z `node -e` czytającego stdin/plik przez potok lub ścieżkę POSIX.

**DODATKOWA obserwacja z tej samej sesji (potwierdzona DWUKROTNIE, przy weryfikacji
`_32` i osobno `_33`):** nawet przy poprawnej metodzie (curl -> prawdziwa ścieżka Windows
-> odczyt), zaraz PO tym jak endpoint nawigacyjny (`/lessons`) zaczyna odpowiadać
(sygnał "backend gotowy"), zapytania o TREŚĆ lekcji (`/theory`, `/exercises`, `/quiz`) mogą
przez dosłownie kilkanaście-kilkadziesiąt sekund zwracać puste `[]`, mimo że
`LessonContentLoader` w rzeczywistości skończył pracę bezbłędnie (bez wyjątków w logu) —
to najwyraźniej krótkie, nieszkodliwe opóźnienie/wyścig przy starcie tego konkretnego
backendu, nie błąd w treści. **Wniosek na przyszłość:** po potwierdzeniu, że `/lessons`
odpowiada, odczekaj dodatkowe ~15-30s (albo odpytuj w pętli aż rozmiar odpowiedzi
przestanie być `2` bajty) PRZED wyciąganiem wniosków z pustej odpowiedzi na endpoint
treści — nie zakładaj od razu błędu w danych.

## Ostatnia ukończona czynność

**CAŁY KURS (31/31 rozdziałów) UKOŃCZONY w tej sesji.** Sesja zaczęła się komendą użytkownika
"kontynuuj pracę, wychodzę z domu, nie pytaj się o zgodę między rozdziałami" — Claude Code
(poprzednia sesja/agent, roboczo nazywany "codex" przez użytkownika) zostawił rozdział
`_29_spring_reactive` na 12/17 lekcji, z lekcjami 13-17 napisanymi ale NIEZWERYFIKOWANYMI
(uncommitted). Ta sesja: (1) zweryfikowała i domknęła `_29_spring_reactive` (17/17, commit
`82d77fa`) — po drodze znaleziono i naprawiono błąd `fix_http_method_case.js`, który uszkodził
prawdziwy kod R2DBC (`ConnectionFactories.get()`→`.GET()`) w `13_R2dbcIntro.json`; (2) napisała od
zera cały rozdział `_30_spring_messaging_and_async` (16/16, commit `a2692f2` + WIP checkpointy);
(3) napisała od zera cały rozdział `_31_spring_cloud_microservices` (19/19, ostatni commit
zamykający w toku pod koniec tej sesji). Od tego momentu WSZYSTKIE 31 rozdziałów kursu są na
standardzie 11 sekcji. **Ważne odkrycie tej sesji o API:** endpointy weryfikacyjne to
`GET /api/chapters/{chapterSlug}/lessons` (lista) i
`GET /api/chapters/{chapterSlug}/lessons/{lessonSlug}/{theory|exercises|quiz}` (treść) —
`lessonSlug` ZAWIERA numer prefiksu; pełny zapis w sekcji "Problemy i decyzje" niżej.
**Kolejny krok NIE jest ustalony** — zobacz sekcję "CAŁY KURS" wyżej i "Następny krok" niżej:
potencjalny Etap 5 (audyt całości) wymaga potwierdzenia z użytkownikiem, zanim zostanie
rozpoczęty.

**AKTUALIZACJA (sesja z 2026-09-15):** dokończono `_32_algorithms_and_data_structures` (12/15 -> 15/15,
lekcje 13-15: DFS, Interview Strategy, Algorithms Capstone) — patrz sekcja
"`_32_algorithms_and_data_structures` (15/15 lekcji) — KOMPLETNE" wyżej po pełne szczegóły.
Sesja rozpoczęta komendą "kontynuuj prace, wychodze z domu wiec nie pytaj sie miedzy
rozdzialami o zgode" — kontynuowano dyrektywę z Etapu 4 (nie pytać o zgodę między
jednostkami pracy) zastosowaną tu do dokończenia rozdziału `_32`.

**AKTUALIZACJA 2 (ta sama sesja z 2026-09-15):** użytkownik napisał "kontynuj nastepne
rodzialy critic" (kontynuuj kolejne rozdziały [dla tematów] krytycznych) — zinterpretowane
jako polecenie napisania NOWEGO rozdziału pokrywającego jedyny temat oznaczony w
`critical-topics.json` priorytetem `"critical"` bez przypisanego rozdziału: Git. Napisano
od zera CAŁY rozdział `_33_git_essentials` (16/16 lekcji) w tej samej sesji — patrz osobna
sekcja "`_33_git_essentials` (16/16 lekcji) — KOMPLETNE" niżej po pełne szczegóły.

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

- **Poprawny format endpointów weryfikacyjnych API (ustalony w sesji `_28_java_evolution`):**
  lista lekcji rozdziału: `GET /api/chapters/{chapterSlug}/lessons` (slug rozdziału z podkreślnikiem,
  np. `_28_java_evolution`, zwraca `[{slug, title}]` — `slug` lekcji ZAWIERA numer prefiksu, np.
  `01_JavaReleaseCadenceAndLtsExplained`, NIE samą nazwę bez numeru). Treść lekcji jest pod TRZEMA
  oddzielnymi endpointami, nie jednym: `GET /api/chapters/{chapterSlug}/lessons/{lessonSlug}/theory`,
  `.../exercises`, `.../quiz` (każdy zwraca sam JSON-owy array, bez opakowania) — `/api/courses/...`
  nie istnieje. Jeśli nie znasz dokładnego `lessonSlug` danego rozdziału, najpierw odpytaj listę
  lekcji zamiast zgadywać nazwę.
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

1. **Etap 4 (migracja rozdziałami) jest ZAKOŃCZONY — wszystkie 31/31 rozdziałów na standardzie 11
   sekcji.** Nic więcej nie robić w ramach Etapu 4.
2. `STAGE2_LESSON_REDESIGN_PROMPT.md` przewiduje **Etap 5 – audyt całości** jako kolejny,
   ostatni etap: spójność wizualna, powtarzające się treści, brakujące zagadnienia, błędy
   techniczne, polskie znaki, uszkodzone fragmenty kodu, nieprawidłowe odpowiedzi quizowe,
   niewyjaśnione pojęcia wymagane przez zadania, pozostałości oznaczenia "lekcja gotowa" (patrz
   pełna treść tej sekcji w `STAGE2_LESSON_REDESIGN_PROMPT.md`). **NIE rozpoczynać Etapu 5 bez
   wyraźnego potwierdzenia użytkownika** — w przeciwieństwie do Etapu 4 (gdzie użytkownik dał
   trwałą dyrektywę "przejedź wszystkie rozdziały bez pytania"), zakres i priorytety Etapu 5 nie
   zostały jeszcze przez użytkownika ustalone.
3. **Znane, świadomie odłożone problemy z wcześniejszych rozdziałów, które Etap 5 powinien objąć:**
   ASCII-only exercises/quiz w `_17_architecture`/`_18_rest_api`/`_19_security_basics` (brak
   polskich znaków, odziedziczone z `COURSE_CONTENT_HISTORY.md`, wymaga podejścia słownikowego
   zamiast global-replace — patrz zapis w sekcji "Stan rozdziałów" przy `_17_architecture`).
4. **Wzorzec pracy ustalony i sprawdzony w wielu sesjach (powtarzaj dla każdej kolejnej lekcji/rozdziału, gdyby Etap 5 lub inna przyszła praca tego wymagała):**
   a) Grep `"exercises": [` żeby znaleźć koniec sekcji theory bez czytania całego pliku.
   b) Read tylko sekcji theory (offset/limit).
   c) Edit zastępujący WYŁĄCZNIE starą tablicę theory nową (11 sekcji + API_REFERENCE, gdy
      uzasadnione, w kolejności: INTRO, DEFINITION, ANALOGY, VISUAL_EXAMPLE, CODE_BASIC,
      CODE_PRACTICAL, STEP_BY_STEP, NOTE, USAGE, PITFALL, CODE_WRONG, CODE_RIGHT,
      [API_REFERENCE], WHEN_TO_USE, SUMMARY) — NIGDY Write na pliku z istniejącymi
      exercises/quiz.
   d) Uruchom `node scripts/content-migration/fix_allcaps.js <plik.json>` i
      `node scripts/content-migration/fix_allcaps_residual.js <plik.json>`. Dla rozdziałów
      wspominających metody HTTP w exercises/quiz (`_06`,`_07`,`_17`,`_18`,`_19`+, Spring `_20`+)
      uruchom DODATKOWO `node scripts/content-migration/fix_http_method_case.js <plik.json>`
      (naprawia GET/POST/PUT/PATCH/DELETE/HEAD/OPTIONS do WIELKICH liter).
   e) Waliduj (node, bez polegania na `grep -P` — locale w tej powłoce go nie wspiera): JSON valid
      + liczba bloków theory/exercises(30)/quiz(100), `(tekst.match(/native code/g)||[]).length`,
      `(tekst.match(/[Ѐ-ӿ]/g)||[]).length` (cyrylica) — napraw natychmiast jeśli coś
      wykryte, nawet jeśli to zastana treść sprzed tej sesji.
   f) Po całym rozdziale: `export JAVA_HOME=...` + `mvnw.cmd resources:resources` + restart
      backendu (`run_in_background: true`, BEZ własnego `&`/`nohup`) + curl na próbce nowych lekcji
      + regresja na próbce z poprzednich rozdziałów + JEDEN commit lokalny dla całego rozdziału +
      aktualizacja tego pliku (`WORK_PROGRESS.md`).
