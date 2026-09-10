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
- **`_12_hibernate` i dalsze (do `_31_spring_cloud_microservices`) — NIE ROZPOCZĘTE.**
  Migracja rozdziałami, po kolei, bez pomijania żadnego.
  **NASTĘPNY KROK: zacznij `_12_hibernate` (30 lekcji) od lekcji 1**, tym samym wzorcem pracy
  (patrz sekcja "Wzorzec pracy" niżej).

## Ostatnia ukończona czynność

Ukończono migrację całego rozdziału `_11_buildtools` (30/30 lekcji) na standard 11 sekcji,
zweryfikowano live przez backend (po `mvnw.cmd resources:resources` + restart), zregresjono
`_01`-`_10`, i scommitowano jako JEDEN lokalny commit. Zgodnie z dyrektywą użytkownika ("kontynuuj
prace, nie pytaj się o zgodę pomiędzy rozdziałami") migracja przechodzi teraz bez przerwy do
`_12_hibernate`.

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

1. Zacznij `_11_buildtools` (30 lekcji, Ant/Maven/Gradle) od lekcji 1, tym samym wzorcem pracy
   (patrz punkt 3 niżej). Dla lekcji o konkretnym narzędziu/pluginie z wieloma opcjami/komendami
   PISZ OD RAZU z pełnym katalogiem API_REFERENCE; dla lekcji o koncepcjach/wzorcach (np. "czym
   jest build tool", "cykl życia builda" jako pojęcie) pomiń go.
2. Kontynuuj rozdziałami `_12_...` do `_31_...` w kolejności, bez pomijania żadnego, zgodnie z
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
