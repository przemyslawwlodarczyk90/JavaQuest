# JavaQuest — platforma edukacyjna (plan pracy)

> Ten plik jest **punktem startowym** dla każdej sesji pracującej nad platformą.
> Jeśli wracasz do tego projektu po przerwie / po wyczyszczeniu kontekstu:
> 1. Przeczytaj ten plik od początku do sekcji "Stan aktualny / następny krok" na końcu.
> 2. Przeczytaj `CLAUDE.md` w zakresie potrzebnym do rozumienia **istniejącego kursu**
>    (to jest "podstawa programowa" — patrz zasady niżej). Nie musisz czytać całego
>    `CLAUDE.md` za każdym razem — to głównie historia pisania 31 rozdziałów kursu,
>    potrzebna tylko gdy faktycznie referencjonujesz konkretną lekcję.
> 3. Zacznij pracę dokładnie od miejsca opisanego w "Stan aktualny / następny krok".
> 4. Commituj lokalnie często (patrz "Zasady gita" niżej), **bez pusha**.
>
> **Protokół wznowienia (ważne):** użytkownik NIE musi za każdym razem tłumaczyć całej
> wizji od nowa. Krótkie "lecimy dalej" / "kontynuuj" / "leć dalej" (w kontekście tego
> projektu) = sygnał, żeby: przeczytać ten plik, przejść do sekcji "Stan aktualny /
> następny krok" i kontynuować dokładnie od tego miejsca, bez dodatkowych pytań o zakres
> (chyba że pojawi się realna, blokująca niejasność). Po każdej sesji/większym przyroście
> pracy **zaktualizuj sekcję "Stan aktualny / następny krok"**, żeby kolejne wznowienie
> było trafne.

## 1. Wizja (spisana z rozmowy z użytkownikiem, 2026-08-10)

Zbudować na bazie istniejącego kursu Java (31 rozdziałów, setki lekcji, każda z
teorią + 30 ćwiczeniami w formie kodu Java) **aplikację edukacyjną** — front-end
+ backend — która:

- Pokazuje listę **rozdziałów**, po kliknięciu — listę **lekcji** w rozdziale.
- Każda lekcja ma **atrakcyjną, graficzną notatkę** opartą na treści merytorycznej
  już napisanej w kursie (pliki `_LessonXX_*.java`), ale przepisaną w przystępny,
  wizualny sposób — z **plastycznymi analogiami** (np. interfejs = samochód,
  wzorzec Fabryka = fabryczka jednostek z gry RTS) zamiast suchego kodu/teorii.
- Pod lekcją user wybiera: **Zadania** albo **Quizy**.
  - **Zadania**: pokazywane jedno po drugim, z polem do wpisania odpowiedzi/kodu,
    przyciskiem **"Podpowiedź"** (mała wskazówka) i przyciskiem **"Rozwiązanie"**
    (pełne rozwiązanie z przykładem, odsłaniane po podpowiedzi).
  - **Quizy**: 100 pytań ABCD na lekcję, pokazywane jedno po drugim. Zła
    odpowiedź → od razu pokazana poprawna odpowiedź + wyjaśnienie, czemu tak.
- **Pomysł do zbadania (nie zatwierdzony jako wymagany na start)**: "maszyna
  wirtualna" kompilująca/uruchamiająca kod wpisany przez użytkownika w zadaniu
  i zwracająca prawdziwy błąd kompilatora/JVM zamiast tylko tekstowej podpowiedzi.

## 2. Zasady nienaruszalne

1. **Podstawa programowa się nie kasuje.** Cały istniejący kod pod
   `src/main/java/com/example/javaquest/_01_fundamentals` … `_31_spring_cloud_microservices`
   (oraz `_00_tableOfContents`) zostaje **dokładnie taki, jaki jest** — to jest
   materiał źródłowy/referencyjny kursu, samodzielnie kompilowalny i uruchamialny
   przez `mvnw.cmd exec:java` tak jak dotychczas. Platforma go **czyta/używa jako
   inspirację przy pisaniu treści**, ale nie modyfikuje ani nie usuwa.
2. Nowa logika (encje, kontrolery, treści lekcji dla platformy, frontend) żyje w
   **całkowicie osobnych miejscach**, nigdy w pakietach `_NN_temat`:
   - Backend: `com.example.javaquest.platform.*` (patrz sekcja 4).
   - Frontend: `frontend/` (już istnieje, patrz sekcja 3).
   - Treść lekcji platformy: `content/` (patrz sekcja 4.2) — **nie** w
     `src/main/java/.../_NN_temat/`.
3. Segmentacja pracy: **najpierw szkielet (wszystkie rozdziały/lekcje jako puste
   miejsca), potem treść rozdział po rozdziale**, żeby aplikacja była użyteczna
   (choć niepełna) od wczesnego etapu, a nie dopiero po ukończeniu wszystkiego.
   Patrz Fazy w sekcji 5.
4. Ta sama zasada tempa co przy pisaniu kursu (patrz pamięć projektu
   `feedback_testing_cadence`): przy dużych partiach pracy treściowej
   weryfikuj **partiami**, nie po każdej pojedynczej lekcji.

## 3. Co już istnieje (stan na 2026-08-10, zrobione w tej sesji)

- `frontend/` — projekt React (Vite) w katalogu głównym repo.
  `frontend/vite.config.js`: `build.outDir` wskazuje na
  `src/main/resources/static` (`emptyOutDir: true`) — **każdy** `npm run build`
  ląduje dokładnie tam. `npm run dev` proxuje `/api/**` na `localhost:8080`.
  Output builda jest w `.gitignore` (`/src/main/resources/static/`) — to
  artefakt, nie źródło.
- `com.example.javaquest.web.JavaQuestApplication` — **prawdziwy, działający**
  punkt wejścia Spring Boota. Celowo w podpakiecie `web` (NIE w pakiecie
  najwyższego poziomu `com.example.javaquest`) — inaczej domyślny
  component-scan `@SpringBootApplication` łapie WSZYSTKIE 31 rozdziałów kursu
  (każdy pełen własnych, kolidujących `@Configuration`/`@Entity` zaprojektowanych
  do izolowanych kontekstów per-lekcja) i appka nie startuje
  (`BeanDefinitionOverrideException` już przy pierwszej próbie — zweryfikowane
  empirycznie). **Zasada na przyszłość: KAŻDA klasa, która ma być realnym,
  współdzielonym kontekstem Springa dla platformy, musi żyć w
  `com.example.javaquest.platform.*` albo `com.example.javaquest.web.*` — NIGDY
  bezpośrednio w `com.example.javaquest`.**
- `com.example.javaquest.web.HelloController` — placeholder `/api/hello`,
  do usunięcia gdy powstaną prawdziwe kontrolery platformy.
- Naprawiony przedistniejący blocker: `src/test/java/.../_01_fundamentals/Training/`
  (porzucone, niedokończone notatki testowe użytkownika sprzed tego kursu,
  niepowiązane z żadnym rozdziałem) blokowało `test-compile`, więc
  `spring-boot:run`/`mvn package` nie działały. Wykluczone z kompilacji testów
  przez `<testExcludes>` w `pom.xml` (pliki NIE usunięte, tylko pominięte w
  buildzie) — to nie są nasze pliki, nie wiadomo, co tam miało docelowo powstać.
  `JavaQuestApplicationTests` przeniesiony razem z `JavaQuestApplication` do
  `com.example.javaquest.web`.
- Zweryfikowane end-to-end: `mvnw.cmd spring-boot:run` startuje w ~10s,
  `GET /` serwuje zbudowany frontend, `GET /api/hello` odpowiada z backendu.

## 4. Architektura docelowa (propozycja — założenia do potwierdzenia/korekty)

### 4.1 Backend — model danych

Nowy pakiet `com.example.javaquest.platform`, podpakiety `chapter`, `lesson`,
`exercise`, `quiz`, `progress`. Encje (Spring Data JPA + H2, już w projekcie):

- `Chapter(id, slug, title, order)` — np. `slug="_01_fundamentals"`.
- `Lesson(id, chapterId, slug, title, order)` — np. `slug="00_JavaPlatformBasics"`.
- `ContentBlock(id, lessonId, order, type, payload)` — **teoria jako sekwencja
  typowanych bloków**, nie jeden blob HTML/Markdown. `type` np.:
  `CONCEPT` (krótkie wyjaśnienie), `ANALOGY` (wizualna analogia + opis
  ilustracji/emoji/diagramu), `CODE_EXAMPLE` (fragment kodu z kursu, z
  komentarzem), `DIAGRAM` (opis do wyrenderowania jako prosty SVG/mermaid).
  Dzięki typowaniu front-end renderuje każdy blok innym, dopracowanym
  komponentem (karta z ilustracją dla `ANALOGY`, blok kodu z podświetlaniem dla
  `CODE_EXAMPLE`) zamiast jednej ściany tekstu.
- `Exercise(id, lessonId, order, prompt, hint, solutionExplanation, difficulty)`.
- `QuizQuestion(id, lessonId, order, question, optionA..D, correctOption, explanation)`.
- `UserProgress`/`ExerciseAttempt`/`QuizAttempt` — na start **bez logowania**
  (jeden domyślny "lokalny" użytkownik), bo to na razie osobisty projekt
  edukacyjny, nie SaaS wieloosobowy. Prawdziwe konto/auth (z wykorzystaniem
  wiedzy z `_24_spring_security`!) to naturalne rozszerzenie później, nie
  blocker na start.

**Założenie do potwierdzenia**: treść (`ContentBlock`/`Exercise`/`QuizQuestion`)
przechowywana jako **pliki źródłowe w repo** (`content/<rozdział>/<lekcja>.json`),
wczytywane do H2 przy starcie aplikacji (prosty loader), NIE edytowana ręcznie
w bazie ani przez panel admina. Powód: to setki lekcji do napisania — pisanie
plików JSON/YAML jest dużo szybsze do iterowania, w pełni diffowalne w git, i
nie wymaga budowania CMS-a zanim jest jakakolwiek treść. `UserProgress` i inne
dane per-użytkownik **zostają w bazie** (to jest stan, nie treść). Jeśli wolisz
od razu CMS/edycję przez UI — powiedz, to zmienia architekturę Fazy 2.

### 4.2 Skąd bierze się treść lekcji platformy

`_TableOfContents.java` (`src/main/java/.../_00_tableOfContents/`) już zawiera
pełną, autorytatywną listę 31 rozdziałów i ich lekcji (`record Chapter(pakiet,
tytul, lekcje)`) — **to jest źródło prawdy dla Fazy 1** (szkielet). Faza 1 nie
wymaga pisania żadnej nowej treści — tylko zaimportowanie tej listy jako
`Chapter`/`Lesson` (bez `ContentBlock`/`Exercise`/`QuizQuestion` — te przychodzą
w Fazie 2+, rozdział po rozdziale).

Pisanie właściwej treści (Faza 2+) = **nowa praca redakcyjna**, inspirowana
komentarzami teoretycznymi z `_LessonXX_*.java` danej lekcji, ale świadomie
PRZEPISANA pod format "graficznej notatki z analogią" — to nie jest
mechaniczne kopiowanie, tylko nowe pisanie w innym stylu.

### 4.3 Frontend

`frontend/` (React + Vite, już istnieje). Struktura widoków:

```
/                                    -> lista rozdziałów (karty, ikona/kolor per rozdział)
/rozdzial/:chapterSlug                -> lista lekcji rozdziału
/rozdzial/:chapterSlug/:lessonSlug     -> widok lekcji, zakładki:
    [ Teoria ]  [ Zadania ]  [ Quiz ]
```

- **Teoria**: renderuje sekwencję `ContentBlock` (patrz 4.1) jako karty/sekcje.
- **Zadania**: jedno zadanie na ekranie, pole tekstowe na odpowiedź/kod,
  przycisk "Podpowiedź" (odsłania `hint`), przycisk "Rozwiązanie" (odsłania
  `solutionExplanation`) — nawigacja "następne zadanie".
- **Quiz**: jedno pytanie ABCD na ekranie, po odpowiedzi natychmiastowy
  feedback (poprawna/błędna + `explanation`), licznik postępu (np. "12/100"),
  wynik końcowy na koniec.

Do dodania w Fazie 1: `react-router-dom` (routing) — jedyna nowa zależność
frontendowa potrzebna na tym etapie. Zapytania do API przez zwykły `fetch`
(jak w `HelloController`/`App.jsx`) na start — bez React Query/Redux, dopóki
złożoność tego faktycznie nie uzasadni.

### 4.4 Pomysł "maszyny wirtualnej" (kompilacja kodu ucznia) — Faza 4, eksploracyjna

Nie blokuje żadnej wcześniejszej fazy. Techniczne podejście do zbadania, gdy
przyjdzie czas: dokładnie ten sam wzorzec "embeduj i naprawdę uruchom", który
kurs już wielokrotnie stosuje (`_11_buildtools`, `_14_advancedjava/Lesson14`,
`_16_clean_code/Lesson20`) — `ToolProvider.getSystemJavaCompiler()` kompiluje
kod ucznia w katalogu tymczasowym, `ProcessBuilder` z **twardym timeoutem**
(`waitFor(N, SECONDS)` + `destroyForcibly()`, jak w `_15_jvm_internals`)
uruchamia go w osobnym procesie, wynik (stdout/stderr/błąd kompilatora) wraca
do frontendu. To wystarczy dla użytku **osobistego/lokalnego** (jeden proces
na żądanie, z limitem czasu) — gdyby platforma miała kiedyś obsłużyć wielu
użytkowników jednocześnie, potrzebna byłaby dodatkowa izolacja (kontener/limity
zasobów), ale to zdecydowanie nie jest problem Fazy 1-3.

## 5. Fazy pracy

- **Faza 0 — zrobione (2026-08-10)**: `frontend/` + `JavaQuestApplication`
  realnie serwujące frontend przez Spring Boota. Patrz sekcja 3.
- **Faza 1 — szkielet nawigacyjny (NASTĘPNY KROK)**: encje `Chapter`/`Lesson`
  (bez treści), loader zasilający je z `_TableOfContents.java`, REST API
  (`GET /api/chapters`, `GET /api/chapters/{slug}/lessons`), frontend z
  routingiem pokazujący pełną listę 31 rozdziałów i wszystkich lekcji (bez
  treści — placeholder "treść w przygotowaniu"). **Efekt: aplikacja od razu
  wygląda i nawiguje się jak docelowy produkt**, mimo zerowej właściwej treści.
- **Faza 2 — pierwszy pionowy przekrój (1 rozdział od A do Z)**: pełna treść
  (`ContentBlock` + `Exercise` + `QuizQuestion`, z prawdziwymi 100 pytaniami
  quizowymi na lekcję) dla **`_01_fundamentals`** (17 lekcji — najmniejszy sensowny
  rozdział na sprawdzenie formatu). Cel: dopracować format/styl "graficznej
  notatki z analogią" i szablon quizu/zadania, zanim rozmnożymy go na 30
  kolejnych rozdziałów.
- **Faza 3 — reszta rozdziałów, iteracyjnie**: kontynuacja rozdział po
  rozdziale, tym samym rytmem co pisanie oryginalnego kursu (patrz historia w
  `CLAUDE.md`) — jedna sesja może zrobić 1 rozdział albo kilka lekcji, zależnie
  od limitu. Po każdym rozdziale: aktualizacja sekcji "Stan aktualny" niżej.
- **Faza 4 — eksploracyjna**: "maszyna wirtualna" do kompilowania kodu ucznia
  (sekcja 4.4), ewentualnie konta/logowanie (`_24_spring_security`), panel do
  ręcznej edycji treści zamiast plików.

## 6. Zasady gita dla tej pracy

- Commituj **lokalnie i często** (po każdym sensownym przyroście: koniec fazy,
  koniec rozdziału, większa partia treści) — to zabezpieczenie na wypadek
  wyczerpania limitu w trakcie sesji.
- **Nie pushuj** — commity zostają lokalne, chyba że użytkownik jawnie poprosi
  o push.
- **Bez linii `Co-Authored-By: Claude...`** w commitach tej gałęzi pracy — na
  wyraźną prośbę użytkownika (2026-08-10), odstępstwo od domyślnej konwencji.
- Commituj na bieżącym branchu (`visual-aplication-branch`) chyba że
  użytkownik każe inaczej.

## 7. Stan aktualny / następny krok

**Stan na 2026-08-10: Faza 0 i Faza 1 ZAKOŃCZONE i zweryfikowane end-to-end.**

Faza 1 zaimplementowana w `com.example.javaquest.platform.chapter`:
- `Chapter`/`Lesson` — encje JPA (H2 in-memory, własny, niezależny schemat
  tworzony przez Hibernate `ddl-auto=create-drop`, BEZ Flyway).
- `ChapterSeedData` — własna, ręcznie zsynchronizowana kopia listy 31
  rozdziałów/lekcji z `_TableOfContents.java` (celowo NIE import — tamten plik
  jest podstawą programową i jego `ROZDZIALY`/`Chapter` są pakietowo-prywatne).
  **Gdy w kursie przybędzie nowy rozdział/lekcja, trzeba dopisać go RĘCZNIE
  też tutaj.**
- `ContentSeeder` (`ApplicationRunner`) — zasila bazę przy starcie, jeśli pusta.
- `ChapterRepository`/`LessonRepository` (Spring Data JPA) + `ChapterController`
  (`GET /api/chapters`, `GET /api/chapters/{slug}/lessons`, 404 dla nieznanego
  rozdziału).
- `LessonSlugTitles` — prowizoryczny "humanizer" slugów lekcji (np.
  `06_StringsAndBuilder` → "Strings And Builder") do czasu prawdziwych,
  redakcyjnie napisanych tytułów w Fazie 2+.
- `SpaFallbackController` (`com.example.javaquest.web`) — `GET /rozdzial/**`
  → forward do `index.html`, żeby twarde przeładowanie/bezpośredni link na
  trasę React Routera nie dawał 404 z domyślnego handlera zasobów statycznych.

Frontend (`frontend/src/`): `react-router-dom` dodany, routing `/` (lista
rozdziałów, kafelki) i `/rozdzial/:chapterSlug` (lista lekcji z odznaką "treść
w przygotowaniu"), `api.js` (cienka warstwa nad `fetch`).

**Dwie pułapki napotkane i naprawione przy pisaniu Fazy 1 (WAŻNE dla
kontynuacji, opisane pełniej w kodzie/komentarzach):**
1. `spring-boot-starter-data-r2dbc` (z `_29_spring_reactive`) auto-konfiguruje
   globalny bean `ConnectionFactory`, a `DataSourceAutoConfiguration` ma
   `@ConditionalOnMissingBean(ConnectionFactory.class)` — bez jawnego
   `@SpringBootApplication(exclude = R2dbcAutoConfiguration.class)` na
   `JavaQuestApplication`, `entityManagerFactory` NIE POWSTAWAŁ WCALE (błąd
   bez żadnej wzmianki o DataSource w logu — zdiagnozowane dopiero przez
   `--debug` i raport warunków auto-konfiguracji). Wykluczenie musi być przez
   ATRYBUT adnotacji, nie przez `spring.autoconfigure.exclude` we
   `.properties(...)` — globalny `application.properties` już ustawia ten
   klucz (Security/Rabbit/ActiveMQ), więc `.properties(...)` (niższy
   priorytet) zostałby całkowicie nadpisany, nie zmergowany.
2. Osierocony proces `java` z wcześniejszego, nigdy poprawnie niezabitego
   `spring-boot:run` blokował port podczas kolejnych prób uruchomienia
   (`Web server failed to start. Port ... was already in use`) — jeśli
   `spring-boot:run` nagle nie startuje mimo poprawnego kodu, sprawdź
   `Get-NetTCPConnection -LocalPort <port>` / `Get-Process java` przed
   szukaniem błędu w kodzie.

**Zweryfikowane end-to-end (kompilacja + `spring-boot:run` + realne żądania
HTTP)**: `GET /api/chapters` → 31 rozdziałów; `GET
/api/chapters/_01_fundamentals/lessons` → 17 lekcji z czytelnymi tytułami;
`GET /api/chapters/_nope/lessons` → 404; `GET /rozdzial/_01_fundamentals`
(twarde wejście, nie SPA-nawigacja) → 200 + `index.html` (fallback działa).

**Stan na 2026-08-10 (ciąg dalszy): Faza 2 ROZPOCZĘTA — pipeline treści gotowy
i zweryfikowany, treść napisana dla 1 z 17 lekcji `_01_fundamentals`.**

Zaimplementowane w `com.example.javaquest.platform.content`:
- `ContentBlock`/`Exercise`/`QuizQuestion` — encje JPA (analogicznie do
  `Chapter`/`Lesson`, `@Lob` na polach tekstowych, żeby uniknąć limitu
  VARCHAR(255) Hibernate).
- `ContentBlockType` — enum `CONCEPT`/`ANALOGY`/`CODE_EXAMPLE`/`DIAGRAM`.
- `LessonContentFile` — rekordy Java (Jackson) opisujące kształt pliku JSON
  treści lekcji: `{ theory: [...], exercises: [...], quiz: [...] }` — patrz
  javadoc klasy dla pełnego przykładu.
- `LessonContentLoader` (`ApplicationRunner`, `@Order(2)`, uruchamiany PO
  `ContentSeeder` `@Order(1)`) — dla KAŻDEJ zasianej lekcji szuka pliku
  `classpath:content/<rozdział>/<lekcja>.json`; jeśli istnieje, parsuje go i
  zapisuje `ContentBlock`/`Exercise`/`QuizQuestion`. Brak pliku = lekcja
  zostaje z samych metadanych nawigacyjnych (Faza 1) — to NORMALNE i
  oczekiwane dla 16/17 lekcji na tym etapie. `@Transactional` na `run()` —
  bez tego `lesson.getChapter().getSlug()` (LAZY) rzucałby
  `LazyInitializationException` (sesja Hibernate zamyka się zaraz po
  `lessonRepository.findAll()`).
- `LessonContentController` — `GET .../theory`, `.../exercises`, `.../quiz`
  (404 dla nieznanej pary rozdział/lekcja, pusta lista `[]` dla znanej lekcji
  bez treści). Odpowiedź zawiera OD RAZU `hint`/`solution`/`correct`/
  `explanation` — ujawnianie na froncie jest czysto kosmetyczne (przycisk
  chowa/pokazuje), NIE ma osobnego endpointu "sprawdź odpowiedź". Świadome
  uproszczenie architektury dla aplikacji jednoosobowej/lokalnej (patrz
  sekcja 4.1) — do rewizji, gdyby platforma miała kiedyś więcej użytkowników
  i sens "nieoszukiwania" quizu przez podgląd odpowiedzi w Network tab.
- `ChapterController.LessonSummary` rozszerzony o `hasContent: boolean`
  (sprawdzane przez `ContentBlockRepository.existsByLessonId`) — frontend
  pokazuje realny status zamiast zawsze "treść w przygotowaniu".

Frontend (`frontend/src/`): `LessonDetailPage` (trasa
`/rozdzial/:chapterSlug/:lessonSlug`) z zakładkami Teoria/Zadania/Quiz,
leniwie pobieranymi per zakładka. `components/TheoryView.jsx` (karty per typ
bloku, inna kolorystyka dla `ANALOGY`/`CODE_EXAMPLE`),
`components/ExercisesView.jsx` (1 zadanie na ekranie, `<textarea>` na
odpowiedź, przyciski Podpowiedź/Rozwiązanie, nawigacja poprzednie/następne),
`components/QuizView.jsx` (1 pytanie ABCD na ekranie, natychmiastowy
feedback + wyjaśnienie, licznik postępu, wynik końcowy + restart).

**Napisana treść (wzorzec formatu na przyszłość)**:
`content/_01_fundamentals/00_JavaPlatformBasics.json` — 6 bloków teorii
(w tym analogia "JDK/JRE/JVM jako ekosystem konsoli do gier"), 10 zadań
(z podpowiedzią i rozwiązaniem każde), **20 pytań quizowych** (NIE 100 —
świadoma decyzja tej sesji, patrz niżej).

**Korekta po informacji zwrotnej użytkownika**: pierwsza wersja tej sesji miała
tylko 10 zadań / 20 quizów jako "reprezentatywna próbkę", ale użytkownik
sprecyzował: **każda lekcja MA MIEĆ 30 zadań (wzorem `_Exercises_LessonXX_*.java`
z reszty kursu) + 100 pytań quizowych — bez kompromisów w liczbie**. Zadania NIE
są wymyślane od zera — ich TREŚĆ (prompt) pochodzi wprost z istniejącego pliku
`_Exercises_Lesson00_JavaPlatformBasics.java` (30 zagnieżdżonych klas
`ExerciseNN_*` z komentarzem zadania) — platforma dopisuje do każdego z nich
`hint`/`solution`, których oryginalny plik kursu celowo nie ma (kursant sam
pisze kod). To dobra, powtarzalna metoda na przyszłe lekcje: **czytaj istniejący
plik `_Exercises_LessonXX_*.java` danej lekcji, wyciągnij 30 promptów z komentarzy
`🧪 Zadanie N`, dopisz hint+solution do każdego** — nie trzeba wymyślać zadań od
zera, kurs już je ma.
`content/_01_fundamentals/00_JavaPlatformBasics.json` ma teraz PEŁNE **30 zadań
i 100 pytań quizowych**, zweryfikowane end-to-end przez API
(`.../exercises` → 30, `.../quiz` → 100, log startowy bez żadnego `ERROR`).

**Dwie nowe pułapki napotkane i naprawione w tej fazie:**
1. Ta sama kategoria co pułapka R2DBC z Fazy 1: `spring-boot-starter-artemis`
   (z `_30_spring_messaging_and_async/Lesson07`) automatycznie próbował
   odpalić embedded broker JMS przy KAŻDYM starcie naszej aplikacji, dając
   widoczny (ale niefatalny) błąd w logu (`AMQ224000: Failure in
   initialisation` / `UnsupportedOperationException: getSubject is not
   supported` - niezgodność natywnego kodu Artemis z JAAS na nowszych JDK).
   Naprawione dokładnie tym samym wzorcem co R2DBC:
   `@SpringBootApplication(exclude = {..., ArtemisAutoConfiguration.class})`.
   Skróciło to też start aplikacji o kilka sekund.
2. `ChapterController` (pakiet `platform.chapter`) potrzebował wstrzyknąć
   `ContentBlockRepository` z SĄSIEDNIEGO pakietu `platform.content` (dla
   flagi `hasContent`) — zadziałało bez żadnej dodatkowej konfiguracji, bo
   `scanBasePackages` w `JavaQuestApplication` już obejmuje CAŁY
   `com.example.javaquest.platform` (nie per-podpakiet) - warto pamiętać na
   przyszłość, że podpakiety `platform.*` mogą swobodnie zależeć od siebie
   nawzajem.

**Zweryfikowane end-to-end** (kompilacja + build frontendu + `spring-boot:run`
+ realne żądania HTTP, log startowy BEZ żadnego `ERROR`): `GET
.../00_JavaPlatformBasics/theory` → 6 bloków; `.../exercises` → 10 zadań;
`.../quiz` → 20 pytań z poprawnymi `correct`; `GET .../lessons` → `hasContent:
true` TYLKO dla `00_JavaPlatformBasics`, pozostałe 16 lekcji `false`; `GET
.../nope/theory` → 404; `GET /rozdzial/_01_fundamentals/00_JavaPlatformBasics`
(twarde wejście) → 200 (fallback SPA nadal działa dla zagnieżdżonych tras).

**Stan na 2026-08-10 (ciąg dalszy): lekcja 2/17 (`01_Variables`) ukończona w
pełnej skali** — 7 bloków teorii (analogia "zmienne jako bagaż i przechowalnia"
dla prymitywów vs referencji), 30 zadań (prompty z
`_Exercises_Lesson01_Variables.java` + dopisane hint/solution), 100 pytań
quizowych. Zweryfikowane end-to-end (log startowy bez `ERROR`, API zwraca
poprawne liczby, `hasContent: true` dla obu ukończonych lekcji).

**Ustalony, powtarzalny workflow generowania treści (WAŻNE dla kolejnych
lekcji)**: pisanie 100 pytań + 30 zadań ręcznie w jednym pliku JSON jest
podatne na błędy escapowania cudzysłowów/backslashy. Sprawdzona metoda:
generować treść **skryptem Node.js** (obiekty JS z template literals, potem
`JSON.stringify(..., null, 2)` do pliku) w katalogu scratchpad, w kilku
mniejszych częściach (np. teoria+zadania osobno, quiz w 4 paczkach po 25),
na końcu **scalić jednym skryptem merge** i zapisać do
`src/main/resources/content/<rozdział>/<lekcja>.json`. To ZERO ręcznego
escapowania i łatwa walidacja (`node -e` sprawdzające liczby elementów i
poprawność `correct`/opcji przed zapisem).

**Incydent i naprawa (2026-08-10, po "lecimy dalej")**: użytkownik zgłosił
`Web server failed to start. Port 8080 was already in use` przy próbie
uruchomienia z IDE, mimo "zabicia portu". Diagnoza: (1) port 8080 na tej
maszynie jest TRWALE zajęty przez systemowy proces `AgentService` (PID stały
między restartami, `taskkill` daje "Odmowa dostępu" — to nie coś, co da się
trwale zabić); (2) w międzyczasie ktoś/coś dopisało `server.port = 8082`
BEZPOŚREDNIO do globalnego `application.properties` (niezacommitowana
zmiana) — ale osierocony proces `java` z wcześniejszej próby wciąż siedział
na porcie 8082, więc KOLEJNE próby też się wywalały. Naprawa:
1. Zabity osierocony proces trzymający 8082.
2. **Usunięta** linia `server.port = 8082` z globalnego `application.properties`
   — zostawienie jej TAM byłoby niebezpieczne: ten plik ma WYŻSZY priorytet
   niż `.properties(...)` używane przez KAŻDĄ z 31 lekcji kursu, które
   zakładają `server.port=0` (losowy port) — globalny `server.port` nadpisałby
   je WSZYSTKIE naraz.
3. Port **8082 jest teraz ustawiany przez `System.setProperty("server.port",
   "8082")`** w `JavaQuestApplication.main()` — TYLKO System property ma
   wystarczająco wysoki priorytet, żeby nadpisać cokolwiek, co globalny plik
   mógłby w przyszłości ustawić, bez ryzyka dla innych lekcji.
4. Zaktualizowane `frontend/vite.config.js` (proxy dev-server) i
   `frontend/README.md` na port 8082.

Zweryfikowane: **`mvnw.cmd spring-boot:run` bez ŻADNEGO argumentu** poprawnie
startuje na porcie 8082, `GET /` i `GET /api/chapters` zwracają 200, log
startowy bez `ERROR`. **Appka jest teraz dostępna pod `http://localhost:8082`
(NIE 8080)** — zapamiętaj to przy każdym kolejnym uruchomieniu/instrukcji dla
użytkownika.

**Stan na 2026-08-11: lekcja 3/17 (`02_Operators`) UKOŃCZONA w pełnej skali —
dokończona dokładnie od miejsca przerwania (part1/part2 quizu i
theory+exercises były już gotowe z poprzedniej sesji, dopisano part3/part4).**
7 bloków teorii (bez zmian), 30 zadań (bez zmian), **100 pytań quizowych**
(part1-2 z poprzedniej sesji: podstawy arytmetyki/inkrementacji/przypisań
złożonych/porównań/logiki/short-circuit/priorytetu/trójkowego/bitowych
podstaw; nowe part3-4 tej sesji: złożone wyrażenia mieszane, przepełnienie
`int` (`Integer.MAX_VALUE+1`, `MIN_VALUE/-1`), dzielenie przez zero
int-vs-double (`ArithmeticException` vs `Infinity`/`NaN`), `NaN != NaN`,
promocja typu w operatorze trójkowym (`true ? intX : 2.5` → `"5.0"`),
arytmetyka na `char`, konkatenacja `+` zależna od kolejności operandów,
cache'owanie `Integer` (-128..127) i pułapka `==`, `>>` vs `>>>` na liczbach
ujemnych, znak wyniku `%` (podąża za dzielną), pełna tabela priorytetów
(`&` > `&&`, addytywne > shift, relacyjne > rówości, przypisanie/trójkowy
najniżej), idiomy bitowe (ustawianie/czyszczenie/przełączanie bitu,
sprawdzanie potęgi dwójki). Scalone przez ten sam skrypt Node.js co
`01_Variables` (walidacja liczby elementów + poprawności `correct`/opcji
PRZED zapisem), zapisane do
`src/main/resources/content/_01_fundamentals/02_Operators.json`,
robocze pliki `content-drafts/` USUNIĘTE po scaleniu.

**Zweryfikowane end-to-end** (kompilacja + `spring-boot:run` na porcie 8082 +
realne żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany
po weryfikacji): `.../02_Operators/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla `00_JavaPlatformBasics`/`01_Variables`/`02_Operators`,
`false` dla `03_Conditionals` i pozostałych.

**Decyzja z tej sesji (potwierdzona przez użytkownika)**: rytm pracy
POZOSTAJE pionowym przekrojem lekcja-po-lekcji (teoria+30 zadań+100 quizów
razem, dopiero potem kolejna lekcja) — NIE przechodzimy na 3 osobne obiegi
(najpierw cała teoria, potem wszystkie zadania, potem wszystkie quizy) mimo
rozważenia tej opcji. Powód: każda ukończona lekcja jest od razu w pełni
użyteczna w appce, mniejsze ryzyko przy przerwaniu sesji w połowie.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 4/17 (`03_Conditionals`) UKOŃCZONA w
pełnej skali** — 7 bloków teorii (analogia "if/else jako bramki kontrolne na
lotnisku, switch jako sortownia paczek"; if/else if/else, operator trójkowy,
klasyczny switch z pułapką fall-through, nowa składnia switch (`->`), switch z
`yield`, zagnieżdżone ify + switch bezpieczny na null), 30 zadań (prompty z
`_Exercises_Lesson03_Conditionals.java` + dopisane hint/solution), 100 pytań
quizowych w 4 paczkach po 25 (podstawy if/else+trójnik; klasyczny switch
case/break/fall-through/default; nowa składnia switch z `->`/wieloma
etykietami/`yield`; zagnieżdżone ify + null-safe switch + priorytet `&&`
vs `||` + pułapki typu dangling-else i short-circuit z auto-unboxingiem).
Wygenerowane tym samym, sprawdzonym workflow skryptem Node.js w scratchpadzie
(walidacja liczby elementów + poprawności `correct`/opcji PRZED zapisem),
scalone do `src/main/resources/content/_01_fundamentals/03_Conditionals.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (kompilacja niepotrzebna — czysty plik treści;
`spring-boot:run` na porcie 8082 + realne żądania HTTP, log startowy bez
`ERROR`, proces zatrzymany i posprzątany po weryfikacji):
`.../03_Conditionals/theory` → 7; `.../exercises` → 30; `.../quiz` → 100
(wszystkie `correct` w zbiorze A-D); `GET .../lessons` → `hasContent: true`
dla `00_JavaPlatformBasics`/`01_Variables`/`02_Operators`/`03_Conditionals`,
`false` dla `04_Loops` i pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 5/17 (`04_Loops`) UKOŃCZONA w
pełnej skali** — 7 bloków teorii (analogia "pętle jako różne rodzaje treningu
na siłowni" — while jako trener sprawdzający puls PRZED serią, do-while jako
trener każący zrobić pierwszą serię zawsze; while, do-while, for, foreach,
break vs continue, zagnieżdżone pętle na przykładzie tablicy 2D), 30 zadań
(prompty z `_Exercises_Lesson04_Loops.java` + dopisane hint/solution — w tym
algorytm Euklidesa, konwersja na binarny/szesnastkowy, trójkąt Pascala,
sprawdzanie liczb pierwszych/palindromów), 100 pytań quizowych w 4 paczkach
po 25 (while/do-while — miejsce sprawdzania warunku, gwarancja min. 1
wykonania; klasyczny for — inicjalizacja/warunek/krok, zasięg zmiennej
licznikowej, wielokrotna inicjalizacja przecinkami; foreach + break/continue
— brak dostępu do indeksu, kopia wartości a nie referencja, etykietowany
break, ConcurrentModificationException; zagnieżdżone pętle — tablice
"zębate"/jagged arrays, złożoność O(n²), off-by-one errors,
ArrayIndexOutOfBoundsException). Wygenerowane tym samym, sprawdzonym
workflow skryptem Node.js w scratchpadzie (walidacja liczby elementów +
poprawności `correct`/opcji PRZED zapisem — złapała 2 literówki w liczbie
pytań w tej sesji, poprawione przed zapisem), scalone do
`src/main/resources/content/_01_fundamentals/04_Loops.json`, robocze pliki
scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../04_Loops/theory` → 7; `.../exercises` → 30; `.../quiz` →
100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` → `hasContent:
true` dla lekcji 00-04, `false` dla `05_Arrays` i pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 6/17 (`05_Arrays`) UKOŃCZONA w
pełnej skali** — 7 bloków teorii (analogia "tablica jako rząd ponumerowanych
skrytek na dworcu" — stała liczba, indeksowanie od 0, ArrayIndexOutOfBounds
jako "ochrona zatrzymująca Cię przy nieistniejącej skrytce"; deklaracja/
inicjalizacja na 3 sposoby, indeksowanie, `length` jako pole nie metoda,
wartości domyślne, klasa `Arrays` (sort/toString/copyOf/equals), tablice 2D
jako tablica tablic z jagged arrays), 30 zadań (prompty z
`_Exercises_Lesson05_Arrays.java` + dopisane hint/solution — w tym
wyszukiwanie binarne, sortowanie bąbelkowe, transpozycja i mnożenie macierzy,
usuwanie duplikatów bez kolekcji, przechodzenie spiralne macierzy), 100 pytań
quizowych w 4 paczkach po 25 (deklaracja/indeksowanie/`length`/wartości
domyślne; klasa `Arrays` + semantyka referencyjna — `==` vs `Arrays.equals`,
płytkie kopiowanie przez przypisanie vs `copyOf`/`clone`; algorytmy na
tablicach — max/min/suma/średnia/odwracanie/duplikaty/wyszukiwanie liniowe i
binarne, złożoność O(n²) vs O(log n); tablice 2D — jagged arrays, `length` vs
`matrix[row].length`, transpozycja, `Arrays.deepToString`). Wygenerowane tym
samym, sprawdzonym workflow skryptem Node.js w scratchpadzie (walidacja
liczby elementów + poprawności `correct`/opcji PRZED zapisem — złapała 1
błędną odpowiedź w pytaniu o `binarySearch`, poprawione przed zapisem),
scalone do `src/main/resources/content/_01_fundamentals/05_Arrays.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../05_Arrays/theory` → 7; `.../exercises` → 30; `.../quiz` →
100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` → `hasContent:
true` dla lekcji 00-05, `false` dla `06_StringsAndBuilder` i pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 7/17 (`06_StringsAndBuilder`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "String jako tekst
wyryty w kamieniu, StringBuilder jako tablica z kredą" — niemutowalność,
String Pool jako "wspólna biblioteka gotowych kamieni"; niemutowalność String,
String Pool + `==` vs `equals()` + `intern()`, metody analityczne (length/
charAt/contains/startsWith/indexOf/isEmpty/isBlank), przekształcanie tekstu
(substring/trim/strip/replace warianty/split/join), StringBuilder jako
mutowalna alternatywa + dlaczego wydajniejszy w pętli, przykład method
chaining), 30 zadań (prompty z `_Exercises_Lesson06_StringsAndBuilder.java` +
dopisane hint/solution — w tym String Pool/`intern()`, konwersja
camelCase→snake_case, budowanie HTML przez StringBuilder, ekstrakcja liczb
przez regex), 100 pytań quizowych w 4 paczkach po 25 (niemutowalność + String
Pool + `==` vs `equals()` + `intern()` + konkatenacja stałych kompilowana
statycznie; metody analityczne/wyszukujące; substring/trim vs strip/replace
warianty/split/join/repeat/`String.format`; StringBuilder — append/insert/
delete/replace/reverse, method chaining, StringBuffer, referencyjność).
Wygenerowane tym samym, sprawdzonym workflow skryptem Node.js w scratchpadzie
(walidacja liczby elementów + poprawności `correct`/opcji PRZED zapisem —
złapała 1 samosprzeczne, źle oznaczone pytanie o `equals()` z różną
wielkością liter, poprawione przed zapisem), scalone do
`src/main/resources/content/_01_fundamentals/06_StringsAndBuilder.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji — przy okazji zauważone i posprzątane 2 osierocone procesy
`java` z wcześniejszej sesji, blokujące nic, ale warte wyczyszczenia):
`.../06_StringsAndBuilder/theory` → 7; `.../exercises` → 30; `.../quiz` →
100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` → `hasContent:
true` dla lekcji 00-06, `false` dla `07_DateAndTime` i pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 8/17 (`07_DateAndTime`) UKOŃCZONA
w pełnej skali** — 7 bloków teorii (analogia "java.time jako różne narzędzia
do mierzenia czasu" — LocalDate jako kartka z kalendarza, LocalTime jako
zegarek, LocalDateTime jako kalendarz z zegarem, ZonedDateTime jako to samo
z metką strefy, Instant jako uniwersalny stoper; LocalDate, LocalTime,
LocalDateTime, ZonedDateTime + Instant, DateTimeFormatter formatowanie i
parsowanie, ChronoUnit obliczenia między datami + niemutowalność całego
`java.time`), 30 zadań (prompty z `_Exercises_Lesson07_DateAndTime.java` +
dopisane hint/solution — w tym odliczanie do urodzin, kalkulator wieku,
konwersja stref czasowych, generator cyklicznych spotkań, parsowanie
wielu formatów dat), 100 pytań quizowych w 4 paczkach po 25 (LocalDate/
LocalTime/LocalDateTime — niemutowalność, przepełnienia godzin/miesięcy,
isBefore/isAfter/isEqual; ZonedDateTime i Instant — strefy czasowe,
`withZoneSameInstant`, epoch, dlaczego regionalne nazwy stref są lepsze niż
stałe offsety; DateTimeFormatter — litery wzorca dd/MM/yyyy/HH/EEEE/MMMM,
Locale, `format()` vs `parse()`, pułapka europejski vs amerykański format;
ChronoUnit + niemutowalność całego `java.time` + historyczne porównanie z
`java.util.Date`/`Calendar`). Wygenerowane tym samym, sprawdzonym workflow
skryptem Node.js w scratchpadzie (walidacja liczby elementów + poprawności
`correct`/opcji PRZED zapisem), scalone do
`src/main/resources/content/_01_fundamentals/07_DateAndTime.json`, robocze
pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../07_DateAndTime/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-07, `false` dla `08_MathOperations` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 9/17 (`08_MathOperations`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "klasa Math jako
skrzynka narzędziowa na ścianie warsztatu" — wszystkie metody statyczne, bez
`new Math()`; podstawowe operacje abs/max/min, potęgowanie i pierwiastki
pow/sqrt/cbrt, zaokrąglanie ceil/floor/round/rint z pułapką "round half to
even", trygonometria + `toRadians`/`toDegrees`, logarytmy/`exp`/stałe PI i E,
`Math.random()` + wzorzec generowania zakresu), 30 zadań (prompty z
`_Exercises_Lesson08_MathOperations.java` + dopisane hint/solution — w tym
metoda Newtona dla pierwiastka, aproksymacja Pi metodą Monte Carlo, procent
składany, odchylenie standardowe, odległość euklidesowa), 100 pytań
quizowych w 4 paczkach po 25 (abs/max/min + pow/sqrt/cbrt w tym pułapka
`Math.abs(Integer.MIN_VALUE)`; zaokrąglanie — różnica `round()` vs `rint()`
przy `.5`, `ceil`/`floor` dla liczb ujemnych, `(int)` rzutowanie vs
`Math.floor()`; trygonometria/logarytmy/stałe — radiany vs stopnie, brak
wbudowanego log o dowolnej podstawie; `Math.random()` + zastosowania
praktyczne — wzorzec zakresu, Monte Carlo, metoda Newtona, `SecureRandom`
jako właściwy wybór kryptograficzny). Wygenerowane tym samym, sprawdzonym
workflow skryptem Node.js w scratchpadzie (walidacja liczby elementów +
poprawności `correct`/opcji PRZED zapisem), scalone do
`src/main/resources/content/_01_fundamentals/08_MathOperations.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../08_MathOperations/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-08, `false` dla `09_BigNumberTypes` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 10/17 (`09_BigNumberTypes`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "BigInteger/
BigDecimal jako nieskończona kartka papieru, int/double jako mały kalkulator
kieszonkowy" — twardy limit kalkulatora vs ręczne liczenie bez ograniczeń;
BigInteger podstawy, operacje na BigInteger metodami nie operatorami,
dlaczego NIE double dla pieniędzy (binarna niedokładność 0.1+0.2), dzielenie
BigDecimal wymaga RoundingMode, pułapka `equals()` vs `compareTo()` przy
różnej skali, praktyczny VAT + obsługa dzielenia przez zero), 30 zadań
(prompty z `_Exercises_Lesson09_BigNumberTypes.java` + dopisane hint/
solution — w tym silnia 50!, 100. liczba Fibonacciego, aproksymacja Pi
szeregiem Leibniza, kalkulator raty kredytu hipotecznego, konwersja walut),
100 pytań quizowych w 4 paczkach po 25 (BigInteger podstawy — brak
operatorów, niemutowalność, `pow`/`gcd`/`isProbablePrime`, cicha utrata
precyzji przy `intValue()`; BigDecimal podstawy — dlaczego nie double,
tworzenie ZAWSZE ze Stringa nie z double, `ArithmeticException` przy
nieskończonym rozwinięciu dziesiętnym, pułapka skali w `equals()`;
RoundingMode szczegółowo — HALF_UP/HALF_DOWN/HALF_EVEN/FLOOR/CEILING/UP/DOWN,
bankierskie zaokrąglanie, konwersje, obliczenia finansowe; algorytmy
zaawansowane — silnia/Fibonacci/szereg Leibniza/rata kredytu + przegląd
mieszany różnic BigInteger vs BigDecimal). Wygenerowane tym samym,
sprawdzonym workflow skryptem Node.js w scratchpadzie (walidacja liczby
elementów + poprawności `correct`/opcji PRZED zapisem — złapała 1 pytanie z
niedokończonym, samosprzecznym tekstem opcji, poprawione przed zapisem),
scalone do `src/main/resources/content/_01_fundamentals/09_BigNumberTypes.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../09_BigNumberTypes/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-09, `false` dla `10_HeapAndStack` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 11/17 (`10_HeapAndStack`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "Stack jako plecak na
wycieczkę per wywołanie metody, Heap jako wspólny magazyn z kwitami" —
plecak oddawany natychmiast po zakończeniu metody, paczka w magazynie żyje
dopóki istnieje choć jeden kwit; stos jako ramki LIFO, sterta jako
współdzielona pula obiektów, Java ZAWSZE pass-by-value (dla obiektów: kopia
referencji, nie obiektu), modyfikacja pola obiektu vs przypisanie nowej
referencji wewnątrz metody, Garbage Collector + `System.gc()` jako tylko
sugestia, przykład Dog z modifyPrimitive/modifyObject), 30 zadań (prompty z
`_Exercises_Lesson10_HeapAndStack.java` + dopisane hint/solution — w tym
Integer Cache, płytka vs głęboka kopia macierzy, diagnostyka pamięci przez
`Runtime`, `printStackTrace()` bez `throw`, porównanie wydajności String `+`
vs StringBuilder), 100 pytań quizowych w 4 paczkach po 25 (stos — ramki,
LIFO, StackOverflowError, per-wątek; sterta + Garbage Collector — GC roots,
osiągalność, `System.gc()` jako sugestia, wycieki pamięci przez statyczne
kolekcje; przekazywanie argumentów — pass-by-value dla prymitywów i
referencji, modyfikacja pola vs reassignment, niemożność "prawdziwego" swap;
Integer Cache + płytka/głęboka kopia + method chaining + diagnostyka pamięci
+ podsumowanie całej lekcji). Wygenerowane tym samym, sprawdzonym workflow
skryptem Node.js w scratchpadzie (walidacja liczby elementów + poprawności
`correct`/opcji PRZED zapisem — złapała 1 literówkę w wyjaśnieniu
`maxMemory()`, poprawioną przed zapisem), scalone do
`src/main/resources/content/_01_fundamentals/10_HeapAndStack.json`, robocze
pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../10_HeapAndStack/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-10, `false` dla `11_TypeCasting` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 12/17 (`11_TypeCasting`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "widening jako
przelewanie do większego naczynia, narrowing jako przelewanie do
mniejszego" — ryzyko rozlania jako utrata danych; widening automatyczny
byte→short→int→long→float→double, narrowing z ryzykiem obcięcia/
przepełnienia (arytmetyka modularna, cicho bez wyjątku), upcasting zawsze
bezpieczny vs downcasting wymagający `instanceof`+`ClassCastException`,
konwersje String↔liczba przez `parseXxx`/`valueOf`, autoboxing/unboxing +
pułapki `NullPointerException`/Integer Cache, konwersje char + brak
konwersji boolean + nowoczesny `instanceof` pattern matching Java 16+),
30 zadań (prompty z `_Exercises_Lesson11_TypeCasting.java` + dopisane hint/
solution — w tym hierarchia Vehicle→Car→ElectricCar, tablica Object[] z
mieszanymi typami, konwersje liczb na binarny/hex/ósemkowy), 100 pytań
quizowych w 4 paczkach po 25 (widening/narrowing prymitywów + arytmetyka
modularna przy przepełnieniu; upcasting/downcasting + `instanceof` +
`ClassCastException` + `getClass()`; konwersje String↔liczba +
autoboxing/unboxing + Integer Cache + `NumberFormatException`; konwersje
char + brak konwersji boolean + binarna promocja numeryczna + `toHexString`/
`toBinaryString` + klasa `Number` jako wspólna nadklasa wrapperów).
Wygenerowane tym samym, sprawdzonym workflow skryptem Node.js w
scratchpadzie (walidacja liczby elementów + poprawności `correct`/opcji
PRZED zapisem), scalone do
`src/main/resources/content/_01_fundamentals/11_TypeCasting.json`, robocze
pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../11_TypeCasting/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-11, `false` dla `12_BinaryAndHex` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 13/17 (`12_BinaryAndHex`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "systemy liczbowe
jako różne alfabety liczenia" — dziesiętny jak palce obu rąk, binarny jak
rząd przełączników światła, hex jako skrót grupujący 4 bity naraz; system
binarny + wartości pozycyjne, system szesnastkowy + dlaczego 1 cyfra hex =
4 bity, konwersje przez `Integer.toBinaryString`/`toHexString`/`parseInt`
z podstawą, literały `0b`/`0x`/wiodące zero + podkreślenia jako separator,
system ósemkowy + pułapka przypadkowego wiodącego zera, praktyczny przykład
flag bitowych z formatowaniem `String.format`+`replace`), 30 zadań (prompty
z `_Exercises_Lesson12_BinaryAndHex.java` + dopisane hint/solution — w tym
parser koloru HTML, ręczna konwersja dziesiętny↔binarny bez wbudowanych
metod, pakowanie/rozpakowywanie RGB przez przesunięcia bitowe, sprawdzanie
potęgi dwójki, licznik bitów, kalkulator binarny AND/OR/XOR), 100 pytań
quizowych w 4 paczkach po 25 (system binarny — wartości pozycyjne, literały
`0b`, `toBinaryString`/`parseInt`; system hex + literały `0x` + system
ósemkowy + pułapka wiodącego zera; konwersje krzyżowe przez wspólny
mianownik dziesiętny + parser kolorów RGB + ręczne algorytmy konwersji +
`Integer.bitCount`; flagi bitowe — sprawdzanie/ustawianie/czyszczenie/
przełączanie bitu, sprawdzanie potęgi dwójki, bitmaski uprawnień, oszczędność
pamięci względem `boolean[]`). Wygenerowane tym samym, sprawdzonym workflow
skryptem Node.js w scratchpadzie (walidacja liczby elementów + poprawności
`correct`/opcji PRZED zapisem — złapała 1 niedokończone, samosprzeczne
wyjaśnienie z resztkami "obliczania na głos", poprawione przed zapisem),
scalone do `src/main/resources/content/_01_fundamentals/12_BinaryAndHex.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../12_BinaryAndHex/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-12, `false` dla `13_BitwiseOperators` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 14/17 (`13_BitwiseOperators`)
UKOŃCZONA w pełnej skali** — celowo NIE powtarza materiału z `12_BinaryAndHex`
(tamta lekcja: systemy liczbowe + konwersje; ta: mechanika samych operatorów
bitowych + głębsze algorytmy). 7 bloków teorii (analogia "operatory bitowe
jako rząd malutkich bramek logicznych" — AND/OR/XOR jak różne typy
przełączników korytarzowych, NOT jak dźwignia odwracająca lampki; tabele
prawdy AND/OR/XOR, NOT + przesunięcie w lewo jako mnożenie, przesunięcie w
prawo ze znakiem vs bez znaku, praktyczne algorytmy parzystości/wartości
bezwzględnej bez if, XOR swap + wyodrębnianie nibble, pełny przykład systemu
uprawnień), 30 zadań (prompty z `_Exercises_Lesson13_BitwiseOperators.java` +
dopisane hint/solution — w tym proste szyfrowanie XOR, odwracanie bitów,
dodawanie tylko operatorami bitowymi, pakowanie 4 wartości w jeden int,
znajdowanie najwyższego ustawionego bitu), 100 pytań quizowych w 4 paczkach
po 25 (AND/OR/XOR/NOT + tabele prawdy + różnica `&`/`&&`; przesunięcia `<<`/
`>>`/`>>>` + pułapka przepełnienia + przesunięcie modulo rozmiar typu +
`>>31` jako ekstraktor znaku; algorytmy — parzystość, wartość bezwzględna,
XOR swap + pułapka aliasingu, nibble, potęga dwójki, liczenie bitów,
pakowanie/rozpakowywanie bajtów; systemy flag/uprawnień + szyfrowanie XOR +
pułapka XOR do usuwania flagi + `EnumSet` jako czytelniejsza alternatywa).
Wygenerowane tym samym, sprawdzonym workflow skryptem Node.js w
scratchpadzie (walidacja liczby elementów + poprawności `correct`/opcji
PRZED zapisem — złapała 1 błąd składniowy z powielonym kluczem obiektu),
scalone do
`src/main/resources/content/_01_fundamentals/13_BitwiseOperators.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../13_BitwiseOperators/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D); `GET .../lessons` →
`hasContent: true` dla lekcji 00-13, `false` dla `14_GarbageCollector` i
pozostałych.

**Stan na 2026-08-11 (ciąg dalszy): lekcja 15/17 (`14_GarbageCollector`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "GC jako dozorca
budynku sprawdzający, kto jeszcze mieszka" — dozorca NIE wyrzuca lokatora,
dopóki choć jeden klucz/referencja do niego istnieje; osiągalność obiektów +
GC roots, `WeakReference` jako "klucz, który nie liczy się dla dozorcy",
`SoftReference` jako "trzymaj, dopóki nie brakuje miejsca", deprecated
`finalize()` + dlaczego jest niebezpieczny/nieprzewidywalny, `AutoCloseable`
+ try-with-resources jako właściwa alternatywa dla sprzątania zasobów, mity
vs fakty o GC + wycieki pamięci w Javie mimo automatycznego GC), 30 zadań
(prompty z `_Exercises_Lesson14_GarbageCollector.java` + dopisane hint/
solution), 100 pytań quizowych w 4 paczkach po 25 (osiągalność + GC roots +
podstawy działania GC; `WeakReference`/`SoftReference`/`PhantomReference` w
głąb — różnice zastosowań, `ReferenceQueue`, WeakHashMap; deprecacja
`finalize()` + `AutoCloseable`/try-with-resources jako zamiennik;
mity vs fakty o GC + wzorce wycieków pamięci — statyczne kolekcje, listenery,
`ThreadLocal`, monitoring przez `Runtime` + nawiązanie podsumowujące do
`10_HeapAndStack`). Wygenerowane tym samym, sprawdzonym workflow skryptem
Node.js w scratchpadzie (walidacja liczby elementów + poprawności
`correct`/opcji PRZED zapisem — kilkukrotnie złapała niedobór do 25 w
poszczególnych paczkach quizu, uzupełnione przed zapisem), scalone do
`src/main/resources/content/_01_fundamentals/14_GarbageCollector.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../14_GarbageCollector/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D, zero brakujących opcji);
`GET .../lessons` → `hasContent: true` dla lekcji 00-14, `false` dla
`15_RandomAndSecureRandom` i pozostałych.

**Następny krok**: `15_RandomAndSecureRandom` (przedostatnia z pozostałych
lekcji `_01_fundamentals`), tym samym workflow (czytaj
`_Exercises_Lesson15_RandomAndSecureRandom.java` po prompty zadań, generuj
treść skryptem Node.js w scratchpadzie w mniejszych częściach, scal i
zweryfikuj end-to-end, zaktualizuj tę sekcję). Potem `16_Exceptions` jako
ostatnia lekcja rozdziału. Po całym `_01_fundamentals` (17/17) — `_02_oop`
jako kolejny rozdział. Użytkownik poprosił (2026-08-11), żeby między
lekcjami/etapami NIE pytać o zgodę — kontynuować automatycznie ten sam rytm
pracy bez przerywania na potwierdzenia.
