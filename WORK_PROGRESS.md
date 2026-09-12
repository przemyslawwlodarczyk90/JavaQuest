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
- **`_24_spring_security` i dalsze (do `_31_spring_cloud_microservices`) — NIE ROZPOCZĘTE.**
  Migracja rozdziałami, po kolei, bez pomijania żadnego. **NASTĘPNY KROK: sprawdź format
  `_24_spring_security` (prawdopodobnie stary format jak `_17`-`_23`), potem przepisz tym samym
  wzorcem 11 sekcji. Ten rozdział prawdopodobnie odwołuje się wprost do `_19_security_basics`
  (protokołowe podstawy bezpieczeństwa) jako fundamentu i pokazuje konkretny mechanizm Spring
  Security — sprawdź, czy wspomina GET/POST (np. `.requestMatchers(HttpMethod.GET, ...)`) i w razie
  potrzeby uruchom `fix_http_method_case.js`.**

## Ostatnia ukończona czynność

Ukończono i scommitowano rozdział `_23_spring_data_jpa` (15/15, KOMPLETNE, commit `6bac804`) w tej
sesji, po `_22_spring_web` (19/19, `a29984c`), `_21_spring_boot` (16/16, `2d3bd84`) i
`_20_spring_core` (23/23, `f6b420b`) — cztery rozdziały ukończone w tej samej, ciągłej sesji. Dla
`_23_spring_data_jpa`: każda z 15 lekcji przepisana od zera z 8-blokowego formatu
CONCEPT/CODE_EXAMPLE/ANALOGY na standard 11 sekcji, potem `fix_allcaps.js` + `fix_allcaps_residual.js`
+ `fix_http_method_case.js` (bez istotnych trafień, rozdział nie dotyczy bezpośrednio HTTP),
walidacja Node po każdej lekcji. Lekcje 2 (hierarchia interfejsów) i 4 (query methods) dostały
dodatkowy blok `API_REFERENCE`. Jedno odstępstwo: literówka własnego autorstwa "PROZNIEJSZY"
zamiast "PÓŹNIEJSZY" w lekcji 13, wykryta i naprawiona ręcznie przed uruchomieniem fix scripts. Po
napisaniu wszystkich 15 lekcji: pełna walidacja zbiorcza (wszystkie OK), `mvnw.cmd
resources:resources`, restart backendu (stary proces zatrzymany przez PowerShell `Stop-Process` po
PID z `netstat`, seedowanie treści do H2 zakończone po ~54s od startu procesu), weryfikacja live
przez `/api/chapters/{chapter}/lessons/{lesson}/theory|exercises|quiz` na próbkach lekcji 1, 2, 4,
9, 15, regresja bez zmian na `_22_spring_web`, `_21_spring_boot`, `_20_spring_core`,
`_19_security_basics`, `_18_rest_api`, `_12_hibernate`, `_01_fundamentals`. Jeden commit z pełnym
opisem wszystkich 15 lekcji, bez stopki atrybucji (zgodnie z STAGE2_LESSON_REDESIGN_PROMPT.md).
Zgodnie z dyrektywą użytkownika ("nie pytaj o zgodę pomiędzy lekcjami, leć do samego końca")
migracja była kontynuowana bez zatrzymywania się między lekcjami i rozdziałami — przy następnym
"kontynuuj" zacznij `_24_spring_security` od sprawdzenia formatu lekcji 1, patrz "Następny krok"
wyżej.

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

1. Kontynuuj `_16_clean_code` (22 lekcje) dokładnie od lekcji 18
   `18_NullHandling.json` (lekcje 1-17 już ukończone i scommitowane), tym samym wzorcem pracy
   (patrz punkt 3 niżej), dalej po kolei: `19_ImmutabilityInPractice`, `20_StaticAnalysisTools`,
   `21_LegacyCodeAndTechnicalDebt`, `22_CodeReviewBestPracticesAndCapstone` (koniec rozdziału). To
   rozdział o zasadach/praktykach (SOLID, DRY/KISS/YAGNI, code smells, refaktoryzacja) —
   potwierdzone w lekcjach 1-17: WSZYSTKIE dotychczasowe BEZ API_REFERENCE, oceniać per-lekcja
   dalej (możliwy wyjątek: `20_StaticAnalysisTools` może uzasadniać krótki katalog narzędzi, ocenić
   przy pisaniu). Po ukończeniu lekcji 22: `mvnw.cmd resources:resources` + restart + curl
   weryfikacyjny na próbce + regresja + JEDEN finalny commit oznaczający `_16_clean_code` jako
   KOMPLETNE (22/22), analogicznie do zamknięcia `_15_jvm_internals`.
   PRZED restartem backendu ZAWSZE zatrzymaj stary proces jako pierwszy krok (TaskStop,
   albo jeśli task ID nie jest już śledzony w bieżącej sesji — `netstat -ano | grep :8082` +
   `taskkill //PID <pid> //F`), dopiero potem uruchom nowy (patrz "Problemy i decyzje" o pamięci).
   PO restarcie odczekaj ok. 40-50s i ponów curl kilka razy zanim uznasz brak treści za błąd —
   seedowanie całego kursu do H2 zajmuje teraz zauważalnie więcej czasu niż na początku migracji
   (rośnie z każdym kolejnym scommitowanym rozdziałem).
2. Kontynuuj rozdziałami `_16_...` do `_31_...` w kolejności, bez pomijania żadnego, zgodnie z
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
