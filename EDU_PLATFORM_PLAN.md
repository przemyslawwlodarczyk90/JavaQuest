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

**Stan na 2026-08-11 (ciąg dalszy): lekcja 16/17 (`15_RandomAndSecureRandom`)
UKOŃCZONA w pełnej skali** — 7 bloków teorii (analogia "losowość jako rzut
kostką (Random) vs sejf bankowy generujący PIN (SecureRandom)" — Random jako
pseudolosowy i przewidywalny przy znajomości seeda, SecureRandom jako
kryptograficznie bezpieczny; Math.random() i jego ograniczenia, Random z
pełną kontrolą (zakresy, seed, powtarzalność testów), losowanie elementu z
listy + Collections.shuffle() + algorytm Fishera-Yatesa, ThreadLocalRandom
dla środowisk wielowątkowych, SecureRandom + źródła entropii systemowej,
podsumowująca tabela decyzyjna "którego generatora użyć"), 30 zadań (prompty
z `_Exercises_Lesson15_RandomAndSecureRandom.java` + dopisane hint/solution —
w tym symulacja rzutu kostką/monetą, losowanie Lotto z unikalnością, hasła i
tokeny przez SecureRandom, random walk, aproksymacja Pi metodą Monte Carlo,
rozkład Gaussa/IQ, porównanie wydajności Random vs SecureRandom), 100 pytań
quizowych w 4 paczkach po 25 (Math.random()/Random podstawy — przedziały,
seed, powtarzalność, losowanie z kolekcji, Collections.shuffle;
ThreadLocalRandom + zakresy + pułapki typu duplikaty w losowaniu Lotto +
Arrays.asList vs List.of przy shuffle; SecureRandom w głąb — CSPRNG, źródła
entropii, nextBytes, generowanie haseł/tokenów/OTP, dobre praktyki
bezpieczeństwa przy przechowywaniu haseł w pamięci; algorytmy praktyczne —
metoda Monte Carlo dla Pi, histogramy statystyczne, budowa talii kart, random
walk, rozkład Gaussa, podsumowanie całej lekcji). Wygenerowane tym samym,
sprawdzonym workflow skryptem Node.js w scratchpadzie (walidacja liczby
elementów + poprawności `correct`/opcji PRZED zapisem — kilkukrotnie złapała
niedobór do 25 w poszczególnych paczkach quizu, uzupełnione przed zapisem),
scalone do
`src/main/resources/content/_01_fundamentals/15_RandomAndSecureRandom.json`,
robocze pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../15_RandomAndSecureRandom/theory` → 7; `.../exercises` →
30; `.../quiz` → 100 (wszystkie `correct` w zbiorze A-D, zero brakujących
opcji); `GET .../lessons` → `hasContent: true` dla lekcji 00-15, `false` dla
`16_Exceptions` (ostatnia lekcja rozdziału).

**Stan na 2026-08-11 (ciąg dalszy): lekcja 17/17 (`16_Exceptions`) UKOŃCZONA
w pełnej skali — TYM SAMYM `_01_fundamentals` JEST W 100% UKOŃCZONY NA
PLATFORMIE (17/17 lekcji z pełną treścią).** 7 bloków teorii (analogia
"wyjątek jako przekazywanie problemu w górę łańcucha dowodzenia w firmie" —
pracownik szeregowy nie potrafiący rozwiązać problemu zgłasza go
kierownikowi, kierownik dyrektorowi, aż ktoś go obsłuży albo cały "projekt"
zostaje przerwany z pełnym raportem; hierarchia Throwable - Error vs
Exception, checked vs unchecked i dlaczego ta różnica ma znaczenie,
try-catch-finally + throw vs throws, multi-catch + własne wyjątki,
łańcuchowanie wyjątków (exception chaining) z cause, stack trace + dobre
praktyki), 30 zadań (prompty z `_Exercises_Lesson16_Exceptions.java` +
dopisane hint/solution — w tym własna hierarchia wyjątków checked, retry z
limitem prób, agregacja błędów z batcha bez przerywania pętli, symulacja
rollbacku transakcji bankowej, suppressed exceptions, maszyna stanów
zamówienia oparta na wyjątkach, pełny pipeline walidacji rejestracji z
lancuchowaniem NumberFormatException), 100 pytań quizowych w 4 paczkach po
25 (hierarchia Throwable + checked vs unchecked podstawy; try-catch-finally
+ throw vs throws + multi-catch + kolejność bloków catch + pułapka return w
finally; własne wyjątki + łańcuchowanie (cause) + hierarchia wyjątków
domenowych + wzorzec "zbierz wszystkie błędy naraz"; stack trace +
suppressed exceptions + rollback + retry + maszyna stanów + dobre praktyki
logowania błędów zamiast pustego catch). Wygenerowane tym samym, sprawdzonym
workflow skryptem Node.js w scratchpadzie (walidacja liczby elementów +
poprawności `correct`/opcji PRZED zapisem — kilkukrotnie złapała niedobór do
25 w poszczególnych paczkach quizu, uzupełnione przed zapisem), scalone do
`src/main/resources/content/_01_fundamentals/16_Exceptions.json`, robocze
pliki scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../16_Exceptions/theory` → 7; `.../exercises` → 30;
`.../quiz` → 100 (wszystkie `correct` w zbiorze A-D, zero brakujących
opcji); `GET .../lessons` → **`hasContent: true` dla WSZYSTKICH 17/17
lekcji rozdziału `_01_fundamentals`** (00_JavaPlatformBasics przez
16_Exceptions) — pierwszy w pełni ukończony rozdział na platformie edukacyjnej.

**Stan na 2026-08-11: rozdział `_02_oop` ROZPOCZĘTY na platformie.** Rozdział
ma 15 lekcji (01–15, foldery `Lesson01_ClassesAndObjects` … `Lesson15_
DesignPatterns`), już w pełni zarejestrowane w `ChapterSeedData.java`
(slugi `01_ClassesAndObjects` … `15_DesignPatterns`) — NIE trzeba nic zmieniać
w kodzie platformy, tylko dopisywać pliki treści `content/_02_oop/<slug>.json`.

**Lekcja 1/15 (`01_ClassesAndObjects`) UKOŃCZONA w pełnej skali** — 7 bloków
teorii (analogia "klasa jako plan architektoniczny, obiekt jako gotowy dom" —
plan sam w sobie nie jest domem, ale z tego samego planu można zbudować wiele
niezależnych domów; pola instancyjne vs statyczne + wartości domyślne, metody
+ sygnatura, słowo kluczowe this (odróżnianie pól od parametrów + method
chaining), konstruktory (domyślny, przeciążanie), przeciążanie konstruktorów/
metod statycznych, podsumowanie fundamentu OOP), 30 zadań (prompty z
`_Exercises_Lesson01_ClassesAndObjects.java` + dopisane hint/solution — w tym
własne implementacje Stack/Queue/SimpleLinkedList, Matrix2x2 z mnożeniem
macierzy, niezmienna klasa Money, static factory methods (Color.ofRGB/ofHex),
wzorzec Singleton (EventLog), object pool (ConnectionPool), fluent builder
(Pizza)), 100 pytań quizowych w 4 paczkach po 25 (klasa vs obiekt + pola
instancyjne/statyczne + wartości domyślne + metody + this + konstruktory +
przeciążanie + metody statyczne; głębsze mechanizmy konstruktorów (this(...),
brak domyślnego po zdefiniowaniu własnego) + niezmienność (Money) + Singleton
+ struktury danych Stack/Queue/LinkedList; tablice obiektów + Money jako long
zamiast double + Fluent API + object pool + static factory methods + parsing
hex; command vs query methods + mutowalność vs niezmienność + pamięć
klasa/obiekt + przeciążanie sygnatur + podsumowanie całej lekcji). Wygenerowane
tym samym, sprawdzonym workflow skryptem Node.js w scratchpadzie (walidacja
liczby elementów + poprawności `correct`/opcji PRZED zapisem — wielokrotnie
złapała niedobór do 25 w poszczególnych paczkach quizu, uzupełnione przed
zapisem), scalone do
`src/main/resources/content/_02_oop/01_ClassesAndObjects.json` (nowy katalog
`content/_02_oop/` utworzony przy tej okazji), robocze pliki scratchpadu
usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../_02_oop/lessons/01_ClassesAndObjects/theory` → 7;
`.../exercises` → 30; `.../quiz` → 100 (wszystkie `correct` w zbiorze A-D,
zero brakujących opcji); `GET .../_02_oop/lessons` → 15 lekcji, `hasContent:
true` dla `01_ClassesAndObjects`, `false` dla pozostałych 14.

**Lekcja 2/15 (`02_Encapsulation`) UKOŃCZONA w pełnej skali** — 7 bloków
teorii (analogia "enkapsulacja jako bankomat, a nie sejf z otwartymi
drzwiami" — bankomat udostepnia TYLKO kontrolowane operacje z wbudowaną
walidacją, zamiast bezposredniego dostępu do gotówki; zasada private
fields + public methods, gettery/settery + konwencje nazewnictwa
(get/is), zarys modyfikatorów dostępu, walidacja w setterach (ignoruj/
ustaw domyślną/rzuć wyjątek), computed gettery (obliczanie zamiast
przechowywania), podsumowanie), 30 zadań (prompty z
`_Exercises_Lesson02_Encapsulation.java` + dopisane hint/solution — w tym
defensive copy (SafeData, DateContainer), SecurePassword bez gettera dla
hasła, lazy initialization (ExpensiveData), wzorzec Builder (ServerConfig),
w pełni niezmienna klasa Money, SimpleCache, TrafficLight jako maszyna
stanów, PriceHistory z analizą trendu), 100 pytań quizowych w 4 paczkach
po 25 (podstawy enkapsulacji + gettery/settery + konwencje + modyfikatory
dostępu + walidacja podstawowa; defensive copy + wyciek referencji +
SecurePassword + lazy init + wzorzec Builder + BoundedList; niezmienność
(Money) głębiej + protected vs private + zasada minimalnego dostępu +
setter z wieloma parametrami + złożoność algorytmiczna SimpleCache;
podsumowanie całej lekcji + relacja do Lesson09/Lesson14 + enkapsulacja
jako uniwersalny koncept OOP + styl API boolean vs wyjątek). Wygenerowane
tym samym, sprawdzonym workflow skryptem Node.js w scratchpadzie
(walidacja liczby elementów + poprawności `correct`/opcji PRZED zapisem —
złapała 1 przypadkowo wpisany fragment cyrylicą w rozwiązaniu zadania 30,
poprawione przed zapisem; wielokrotnie uzupełniała niedobór do 25 w
poszczególnych paczkach quizu), scalone do
`src/main/resources/content/_02_oop/02_Encapsulation.json`, robocze pliki
scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../_02_oop/lessons/02_Encapsulation/theory` → 7;
`.../exercises` → 30; `.../quiz` → 100 (wszystkie `correct` w zbiorze A-D,
zero brakujących opcji); `GET .../_02_oop/lessons` → `hasContent: true` dla
`01_ClassesAndObjects`/`02_Encapsulation`, `false` dla pozostałych 13.

**Lekcja 3/15 (`03_Constructors`) UKOŃCZONA w pełnej skali** — 7 bloków
teorii (analogia "konstruktor jako recepcja hotelowa przygotowująca pokój"
— gość NIE MOŻE wejść do pokoju z pominięciem recepcji, każdy obiekt musi
przejść przez konstruktor; konstruktor domyślny vs z parametrami + pułapka
znikającego domyślnego, łańcuchowanie przez this(), kolejność inicjalizacji
pola→bloki→konstruktor + bloki statyczne, konstruktor kopiujący + płytka
vs głęboka kopia, statyczne metody fabryczne, podsumowanie pełnego
arsenału technik), 30 zadań (prompty z
`_Exercises_Lesson03_Constructors.java` + dopisane hint/solution — w tym
generator unikalnych ID, deep copy, blok statyczny, wzorzec Builder
(DatabaseConfig, Report, Order jako Step Builder), Registry Pattern
(Animal), Object Pool (Connection), Singleton (Logger), abstrakcyjna klasa
Shape z super(color), ImmutableList z with(), rekurencyjne TreeNode),
100 pytań quizowych w 4 paczkach po 25 (podstawy konstruktorów +
this()/domyślny/przeciążanie/kopiujący; kolejność inicjalizacji pola/bloki/
konstruktor głębiej + static factory + Object Pool + Registry Pattern +
Step Builder; this() vs super() + abstrakcyjne klasy z konstruktorem +
niezmienna ImmutableList + rekurencyjne struktury + wybór wzorca wg
potrzeb; podsumowanie łączące z Lesson02_Encapsulation + zapowiedź
Lesson05_Inheritance + race condition przy statycznych licznikach + dobre
praktyki "konstruktor ma być szybki"). Wygenerowane tym samym, sprawdzonym
workflow skryptem Node.js w scratchpadzie (walidacja liczby elementów +
poprawności `correct`/opcji PRZED zapisem — wielokrotnie uzupełniała
niedobór do 25 w poszczególnych paczkach quizu, w tym jedno pytanie
zastąpione czytelniejszą wersją po przeglądzie jakości), scalone do
`src/main/resources/content/_02_oop/03_Constructors.json`, robocze pliki
scratchpadu usunięte po scaleniu.

**Zweryfikowane end-to-end** (`spring-boot:run` na porcie 8082 + realne
żądania HTTP, log startowy bez `ERROR`, proces zatrzymany i posprzątany po
weryfikacji): `.../_02_oop/lessons/03_Constructors/theory` → 7;
`.../exercises` → 30; `.../quiz` → 100 (wszystkie `correct` w zbiorze A-D,
zero brakujących opcji); `GET .../_02_oop/lessons` → `hasContent: true` dla
`01_ClassesAndObjects`/`02_Encapsulation`/`03_Constructors`, `false` dla
pozostałych 12.

**Stan na 2026-08-12: `04_StaticKeyword` i `05_Inheritance` DOKOŃCZONE** (5/15
lekcji `_02_oop` gotowe: 01-05). Tym samym workflow co dotychczas (czytaj
`_ExercisesLessonNN_*.java` po 30 promptów zadań, generuj teorię (7 bloków)
+ hint/solution + 100 pytań quizu skryptem Node.js w scratchpadzie, waliduj
liczby elementów + poprawność `correct`/opcji PRZED zapisem, scal do
`src/main/resources/content/_02_oop/NN_Temat.json`).

**WAŻNA POPRAWKA procesu odkryta w tej sesji**: `LessonContentLoader`
(`ApplicationRunner`) zasila bazę TYLKO RAZ, przy starcie aplikacji — samo
dodanie nowego pliku `.json` do `src/main/resources/content/` (nawet
skopiowane ręcznie do `target/classes/content/`) NIE WYSTARCZY, żeby nowa
lekcja stała się dostępna przez API na już działającym procesie. **Po
KAŻDYM dopisaniu nowej lekcji (lub paczki lekcji) TRZEBA zrestartować
`spring-boot:run`**, dopiero wtedy `hasContent`/`.../theory`/`.../exercises`/
`.../quiz` zwrócą nową treść — inaczej API milcząco zwraca puste `[]` dla
nowo dopisanej lekcji, mimo że plik JSON fizycznie istnieje i jest poprawny.
Zweryfikowany, powtarzalny sposób restartu w tej sesji: znaleźć PID na porcie
8082 (`netstat -ano | grep :8082`), `Stop-Process -Id <pid> -Force`, potem
`Start-Process .\mvnw.cmd -ArgumentList "spring-boot:run" -NoNewWindow
-RedirectStandardOutput backend_run.log -RedirectStandardError
backend_run_err.log -PassThru` (PowerShell, z `$env:JAVA_HOME` ustawionym),
odczekać aż `GET /api/chapters` zacznie zwracać 200 (zwykle ~60-70s).

**Druga ważna zmiana w tej sesji**: dotychczasowa treść (lekcje 00-16 w
`_01_fundamentals` i 01-03 w `_02_oop`, czyli WSZYSTKO napisane PRZED tą
sesją) ma tekst BEZ polskich znaków diakrytycznych (np. "Co dokladnie" zamiast
"Co dokładnie") — zgłoszone i potwierdzone przez użytkownika bezpośrednio w
UI (widoczne w zrzutach ekranu quizu). **Lekcje 04 i 05 (ta sesja) są PIERWSZĄ
treścią platformy napisaną z pełnymi polskimi znakami diakrytycznymi od
początku** — ZASADA NA PRZYSZŁOŚĆ: każda kolejna lekcja MUSI być pisana z
pełnymi ogonkami (ą/ć/ę/ł/ń/ó/ś/ź/ż), tak jak reszta tego repo. Naprawienie
WSTECZNE 20 istniejących lekcji (00-16 w `_01`, 01-03 w `_02_oop`) na pełne
diakrytyki to ODDZIELNE, jeszcze nie rozpoczęte zadanie — do zrobienia w
kolejnej sesji (przejście plik po pliku, zamiana ASCII-owych słów na wersje
z ogonkami, bez zmiany treści merytorycznej).

**Trzecia rzecz naprawiona w tej sesji (nie treść, ale blokujący bug w
UI)**: `LessonDetailPage.jsx` miał race condition powodujący, że kliknięcie
zakładki Quiz (lub dowolnej innej) potrafiło na jedną klatkę renderu pokazać
dane z POPRZEDNIEJ zakładki w NOWYM komponencie (np. bloki teorii przekazane
do `QuizView`) — `QuizView` wtedy wywalał się na `Object.entries(question.
options)` z obiektu bez pola `options`, dając CAŁKOWICIE pustą, białą stronę
(zero informacji o błędzie, bo brakowało Error Boundary). Naprawione przez
`setStatus('loading')` w TYM SAMYM handlerze onClick co `setActiveTab(...)`
(React batchuje oba stany w jeden render) — zweryfikowane empirycznie przez
Playwright (rzeczywisty klik dawał 100% odtwarzalny crash PRZED fixem, 0
błędów PO fixie, także przy szybkim przełączaniu zakładek). Dodano też
`components/ErrorBoundary.jsx` jako siatkę bezpieczeństwa na przyszłość.
Przy okazji przeprojektowano całą paletę kolorów na "ciepły industrialny"
motyw (rdza/miedź jako akcent, ciepły pergamin/węglowy brąz zamiast
biało-fioletowej) — poprzednia wersja mieszała hardkodowane kolory
light-mode z prawdziwymi zmiennymi dark-mode, dając biały tekst/przyciski na
ciemnym tle.

**Stan na 2026-08-12 (ciąg dalszy): `06_Polymorphism` też DOKOŃCZONE** (6/15
lekcji `_02_oop` gotowe: 01-06). Zweryfikowane RAZEM z 04 i 05 po jednym
restarcie (regresja potwierdzona - wszystkie trzy nadal 7/30/100).

**Pułapka odkryta przy weryfikacji tej partii**: `GET /api/chapters`
zaczyna zwracać 200 ZANIM `LessonContentLoader` (`ApplicationRunner`,
`@Order(2)`) zdąży skończyć seedowanie treści — embedded Tomcat startuje
i przyjmuje połączenia JUŻ W TRAKCIE wykonywania `ApplicationRunner`-ów, nie
DOPIERO PO ich zakończeniu. Polling samego `/api/chapters` do statusu 200
(jak robiono dotychczas) daje więc FAŁSZYWIE dodatni sygnał gotowości —
`.../quiz` może chwilowo zwracać `[]` (0 elementów), mimo że serwer już
"odpowiada". **Poprawny sposób sprawdzania gotowości: pollować KONKRETNY,
już wcześniej znany endpoint treści (np. `.../04_StaticKeyword/quiz`) aż
zwróci PEŁNĄ, oczekiwaną liczbę elementów (100), nie tylko kod 200.**

**Stan na 2026-08-12 (ciąg dalszy): `07_AbstractClasses` też DOKOŃCZONE**
(7/15 lekcji `_02_oop` gotowe: 01-07). Zweryfikowane RAZEM z 04-06 po jednym
restarcie (regresja potwierdzona — wszystkie cztery nadal 7/30/100).

**WAŻNA, POWTARZAJĄCA SIĘ pułapka przy pisaniu quizu (wystąpiła 3x pod rząd
w tej sesji — 05, 06, 07) — ZASADA NA PRZYSZŁOŚĆ, żeby przestać na nią
wpadać**: dopisywanie kolejnych 10-20 pytań do tablicy `quiz` przez Edit
(dopasowanie starego fragmentu kończącego się `if (quiz.length...)`) jest
mylące, bo TEN fragment zwykle jest poprzedzony przez `];` zamykające tablicę
— podmiana samego bloku walidacji zostawia `];` NA MIEJSCU, więc nowe pytania
lądują POZA tablicą (błąd składni `Unexpected token ':'`). **Poprawny,
niezawodny sposób: napisz CAŁĄ tablicę `quiz` (wszystkie 100 pytań) w JEDNYM
wywołaniu Write, bez późniejszych doklejań przez Edit** — jeśli mimo to
trzeba dopisać brakujące pytania (bo np. przeliczyłeś się przy pisaniu), przy
Edit ZAWSZE jawnie sprawdź (`grep -n "^\];"`), czy `];` nie zostało
"osierocone" w środku pliku, i usuń/przesuń je RĘCZNIE do nowego końca tablicy
— nie polegaj na tym, że stary fragment dopasowania obejmował go automatycznie.

**Stan na 2026-08-12 (ciąg dalszy): `08_Interfaces` też DOKOŃCZONE** (8/15
lekcji `_02_oop` gotowe: 01-08 — DOKŁADNIE POŁOWA rozdziału). Zapisywanie
CAŁEGO quizu jednym `Write` (zamiast doklejania przez Edit) zadziałało czysto
składniowo od razu - jedyna korekta to doliczenie brakujących ~9 pytań na
końcu (normalne niedoszacowanie przy ręcznym liczeniu, NIE powtórka pułapki
z osieroconym `]`). Zweryfikowane RAZEM z 04-07 po jednym restarcie (regresja
potwierdzona - wszystkich 5 lekcji nadal 30 zadań/100 quizów).

**Stan na 2026-08-12 (ciąg dalszy): `09_AccessModifiers` też DOKOŃCZONE**
(9/15 lekcji `_02_oop` gotowe: 01-09 - WIĘCEJ NIŻ POŁOWA rozdziału).
Zweryfikowane RAZEM z 04-08 po jednym restarcie (regresja potwierdzona -
wszystkich 6 lekcji tej sesji nadal 30 zadań/100 quizów). Ustalony workflow
(gen0N.js + quizN.js każdy JEDNYM Write, merge, restart, polling
KONKRETNEGO endpointu, Playwright screenshot, commit, aktualizacja tej
sekcji) działa już w pełni gładko od lekcji 08 - żadnych nowych pułapek
technicznych w tej lekcji.

**Stan na 2026-08-12 (ciąg dalszy): `10_FinalKeyword` też DOKOŃCZONE**
(10/15 lekcji `_02_oop` gotowe: 01-10 - DWIE TRZECIE rozdziału). Zweryfikowane
RAZEM z 04-09 po jednym restarcie (regresja potwierdzona - wszystkich 7
lekcji tej sesji nadal 30 zadań/100 quizów). **W tej lekcji ponownie
wystąpiła pułapka z osieroconym `]` przy dopisywaniu brakujących pytań
quizowych** (mimo zasady "pisz cała tablicę jednym Write") - tym razem PRZY
DOPISYWANIU BRAKUJĄCYCH pytań PO pierwszym Write (bo liczenie \"z ręki\"
przy pisaniu prawie zawsze daje niedobór względem 100) - **DOPRECYZOWANA
ZASADA: gdy trzeba DOPISAĆ brakujące pytania do już istniejącego pliku
quizN.js, ZAWSZE najpierw sprawdź `grep -n "^\];"` PRZED I PO edycji, i
upewnij się, że jest DOKŁADNIE JEDNO wystąpienie `];` w całym pliku, tuż
przed `if (quiz.length...)` - nie polegaj na tym, że Edit "na pewno" trafił
we właściwe miejsce.**

**Stan na 2026-08-12 (ciąg dalszy): `11_ObjectClass` też DOKOŃCZONE**
(11/15 lekcji `_02_oop` gotowe: 01-11). `quiz11.js` napisany JEDNYM Write i
zwalidowany bezbłędnie za pierwszym razem (bez pułapki osieroconego `]` -
zaostrzona zasada z lekcji 10 nie była nawet potrzebna). Zweryfikowane API
end-to-end dla WSZYSTKICH 8 lekcji tej sesji (04-11) po jednym restarcie
backendu - każda: theory=7, exercises=30, quiz=100, zero regresji. Playwright
screenshot pominięty w tym kroku (frontend dev server nie był akurat
uruchomiony, a wizualna poprawność quizu była już zweryfikowana wcześniej w
tej sesji przy naprawie buga pustej strony) - do rozważenia w kolejnym kroku,
jeśli dev server będzie już uruchomiony.

**Stan na 2026-08-12 (ciąg dalszy): `12_InnerClasses` też DOKOŃCZONE**
(12/15 lekcji `_02_oop` gotowe: 01-12). `gen12.js` i `quiz12.js` napisane i
zwalidowane bezbłędnie (jeden drobny JS syntax bug w gen12.js - niezescapowany
apostrof wewnątrz stringa w Javowym kodzie przykładu - złapany od razu przez
`node gen12.js` i naprawiony). Zweryfikowane API end-to-end dla WSZYSTKICH 9
lekcji tej sesji (04-12) po jednym restarcie backendu - każda: theory=7,
exercises=30, quiz=100, zero regresji.

**Stan na 2026-08-12 (ciąg dalszy): `13_Enums` też DOKOŃCZONE**
(13/15 lekcji `_02_oop` gotowe: 01-13). `gen13.js` i `quiz13.js` napisane i
zwalidowane bezbłędnie za pierwszym razem (bez żadnych bugów składniowych).
Zweryfikowane API end-to-end dla WSZYSTKICH 10 lekcji tej sesji (04-13) po
jednym restarcie backendu - każda: theory=7, exercises=30, quiz=100, zero
regresji.

**Stan na 2026-08-12 (ciąg dalszy): `14_Records` też DOKOŃCZONE**
(14/15 lekcji `_02_oop` gotowe: 01-14). `gen14.js` i `quiz14.js` napisane i
zwalidowane bezbłędnie za pierwszym razem. Zweryfikowane API end-to-end dla
WSZYSTKICH 11 lekcji tej sesji (04-14) po jednym restarcie backendu - każda:
theory=7, exercises=30, quiz=100, zero regresji.

---
### ✅ ROZDZIAŁ `_02_oop` NA PLATFORMIE KOMPLETNY (stan na 2026-08-12): 15/15 lekcji

**Wszystkie 15 lekcji rozdziału `_02_oop` mają teraz komplet treści na platformie
(teoria 7 bloków + 30 ćwiczeń + 100 pytań quizowych każda)**: 01_ClassesAndObjects,
02_Encapsulation, 03_Constructors, 04_StaticKeyword, 05_Inheritance, 06_Polymorphism,
07_AbstractClasses, 08_Interfaces, 09_AccessModifiers, 10_FinalKeyword, 11_ObjectClass,
12_InnerClasses, 13_Enums, 14_Records, 15_DesignPatterns. Zweryfikowane KOŃCOWYM,
pełnym przebiegiem regresyjnym po restarcie backendu - wszystkie 15/15 lekcji zwraca
dokładnie theory=7/exercises=30/quiz=100, zero błędów. Lekcje 04-15 (12 lekcji) zostały
napisane w tej sesji (2026-08-12), w jednym ciągłym przebiegu bez zatrzymywania się na
potwierdzenia (zgodnie z wyraźnym życzeniem użytkownika); 01-03 były gotowe z
poprzednich sesji.

Warte odnotowania z tej sesji: `gen11.js`/`gen12.js`/`quiz15.js` miały po 1 drobnym
błędzie składniowym JS (niezescapowany apostrof w Javowym kodzie osadzonym w JS
single-quoted stringu / brakujący dwukropek po literze opcji) - każdy złapany
natychmiast przez `node genNN.js`/`node quizNN.js` i naprawiony przed scaleniem do
JSON. Reszta plików (gen04-10,13,14 i quiz04-10,12,13,14) przeszła walidację od razu,
bez poprawek - w przeciwieństwie do wcześniejszej fazy tej sesji (lekcje przed tym
podsumowaniem), gdzie liczenie "z ręki" pytań quizowych regularnie dawało niedobór
względem 100 i wymagało dopisywania. Standardowa zasada `grep -n "^\];"` przed/po
edycji (z wcześniejszej notatki w tym pliku) NIE była nawet potrzebna w tej turze,
bo każdy plik quiz/gen był pisany od razu w całości jednym Write.

**Stan na 2026-08-12 (ciąg dalszy): (a) diakrytyki w 20 istniejących lekcjach
CZĘŚCIOWO naprawione** (bez pytania o zgodę, zgodnie z "kontynuuj, nie zatrzymuj
się") — słownikowe, dwuprzebiegowe podejście: zbudowano częstościową listę słów ze
wszystkich 20 plików (`_01_fundamentals/00-16` + `_02_oop/01-03`), ręcznie
skompletowano słownik ASCII→polski dla ~490 najczęstszych, jednoznacznych słów
(pomijając ryzykowne kolizje z kodem, np. świadomie NIE zmapowano `cos` → `coś`, bo
kolidowałoby z `Math.cos()` w przykładach kodu), zastosowano przez regex z
zachowaniem wielkości liter (`\b(word)\b` + rekonstrukcja capitalization) na całym
tekście pliku JSON, z walidacją JSON przed/po KAŻDYM zapisem (odrzucenie zapisu przy
niezgodności długości tablic theory/exercises/quiz). **Wynik: ~24500 podstawień w 2
przebiegach, zero uszkodzeń JSON, zero regresji API (zweryfikowane restartem
backendu + sprawdzeniem WSZYSTKICH 32 lekcji `_01_fundamentals`+`_02_oop` —
`quiz.length === 100` dla każdej).**

**WAŻNE ograniczenie tego podejścia (do wiedzy w kolejnej sesji)**: to NIE jest
100% poprawne przywrócenie diakrytyków — słownik pokrywa tylko najczęstsze ~490
słów (spośród ~11900 unikalnych tokenów w tych 20 plikach), więc długi ogon
rzadszych słów (specyficzne dla pojedynczych analogii/przykładów, np. "urzadzenie"
zamiast "urządzenie", "gre" zamiast "grę", "wiedziec" zamiast "wiedzieć" w lekcji
`00_JavaPlatformBasics`) POZOSTAJE bez zmian. Świadoma decyzja: koszt osiągnięcia
100% pokrycia (tysiące unikalnych, rzadkich słów, każde wymagające ręcznej,
kontekstowej weryfikacji ze względu na niejednoznaczność polskiej ortografii bez
diakrytyków — np. "ze"/"że", "kopie"/"kopię", "pol"/"pół"/"pól") był nieproporcjonalny
względem wartości w stosunku do kontynuowania pisania NOWEJ treści. Jeśli
użytkownik zażąda pełnego dokończenia tego zadania w przyszłości: pliki robocze
(`diacritics_dict.js`, `diacritics_dict2.js`, `apply_diacritics.js`,
`wordfreq*.txt`) zostały w scratchpadzie tej sesji (ulotne, nie przetrwają do
kolejnej) — trzeba by odtworzyć podejście od zera (wygenerować listę częstości
pozostałych czysto-ASCII słów przez skrypt Node z regexem uwzględniającym już
poprawione znaki diakrytyczne jako "already fixed", żeby nie liczyć fragmentów
słów rozciętych przez diakrytyki w środku).

**Stan na 2026-08-12 (ciąg dalszy): rozdział `_03_collections` ROZPOCZĘTY**
(bez pytania o zgodę, zgodnie z "kontynuuj, nie zatrzymuj się"). Utworzono nowy
katalog `src/main/resources/content/_03_collections/` (nie istniał wcześniej).
Zweryfikowano DOKŁADNE nazwy 23 folderów lekcji w
`src/main/java/com/example/javaquest/_03_collections/` (`ls`, nie zgadywanie) —
lista: `01_ArrayList, 02_Iteration, 03_CollectionMethods, 04_LinkedList,
05_HashSet, 06_TreeSet, 07_Comparator, 08_HashMap, 09_PriorityQueue,
10_StreamsIntro, 11_StreamsCollectors, 12_StreamsTerminal, 13_StreamsAdvanced,
14_Optional, 15_LinkedHashSet, 16_LinkedHashMap, 17_TreeMap, 18_Deque,
19_ComparableVsComparator, 20_ConcurrentCollections, 21_LegacyCollections,
22_Queue, 23_SpecialMaps`. Zweryfikowano też, że `ChapterSeedData.java` JUŻ MA
zarejestrowany rozdział `_03_collections` z DOKŁADNIE tymi samymi 23 slugami
(linia ~43) — więc NIE trzeba nic zmieniać w kodzie platformy, tylko dopisywać
pliki treści `content/_03_collections/NN_Temat.json`, dokładnie jak przy
`_02_oop`.

**`01_ArrayList` DOKOŃCZONE** (1/23 lekcji `_03_collections` gotowe). `gen01c.js`
i `quiz01c.js` napisane i zwalidowane bezbłędnie za pierwszym razem. Zweryfikowane
API end-to-end: nowa lekcja (theory=7/exercises=30/quiz=100) ORAZ regresja na
`_02_oop/15_DesignPatterns` i `_01_fundamentals/00_JavaPlatformBasics` (zero
regresji między rozdziałami po restarcie backendu).

**Stan na 2026-08-12 (ciąg dalszy): `02_Iteration` też DOKOŃCZONE**
(2/23 lekcji `_03_collections` gotowe). `gen02c.js` i `quiz02c.js` napisane i
zwalidowane bezbłędnie za pierwszym razem. Zweryfikowane API end-to-end: nowa
lekcja + regresja na `01_ArrayList` i `_02_oop/15_DesignPatterns` po restarcie.

**Stan na 2026-08-12 (ciąg dalszy): `03_CollectionMethods` też DOKOŃCZONE**
(3/23 lekcji `_03_collections` gotowe: 01-03). `gen03c.js` i `quiz03c.js`
napisane i zwalidowane bezbłędnie za pierwszym razem. Zweryfikowane API
end-to-end: nowa lekcja + regresja na `01_ArrayList`, `02_Iteration` i
`_01_fundamentals/16_Exceptions` po restarcie.

**Stan na 2026-08-12 (ciąg dalszy): `04_LinkedList` też DOKOŃCZONE**
(4/23 lekcji `_03_collections` gotowe: 01-04). `gen04c.js` zwalidowany od razu;
`quiz04c.js` za PIERWSZYM razem miał tylko 9 grup (90 pytań) - brakującą 10.
grupę (91-100) dopisano i zwalidowano. Zweryfikowane API end-to-end: nowa
lekcja + regresja na wszystkich 3 poprzednich `_03_collections` lekcjach +
`_02_oop/15_DesignPatterns` po restarcie.

**Stan na 2026-08-12 (ciąg dalszy): `05_HashSet` też DOKOŃCZONE**
(5/23 lekcji `_03_collections` gotowe: 01-05). `gen05c.js` i `quiz05c.js`
napisane i zwalidowane bezbłędnie za pierwszym razem (10/10 grup pytań od
razu, żadnej brakującej). Zweryfikowane API end-to-end: nowa lekcja +
regresja na wszystkich 4 poprzednich `_03_collections` lekcjach +
`_02_oop/15_DesignPatterns` po restarcie.

**Stan na 2026-08-13: `06_TreeSet` i `07_Comparator` też DOKOŃCZONE**
(7/23 lekcji `_03_collections` gotowe: 01-07). `gen06c.js`/`quiz06c.js` i
`gen07c.js`/`quiz07c.js` napisane i zwalidowane bezbłędnie za pierwszym razem
(wszystkie 10 grup quizu od razu, bez brakujących pytań). Zweryfikowane API
end-to-end po jednym restarcie backendu: nowe lekcje (06, 07) +
regresja na 05_HashSet, 01_ArrayList, `_02_oop/15_DesignPatterns`,
`_01_fundamentals/00_JavaPlatformBasics` — zero regresji (theory=7/
exercises=30/quiz=100 dla wszystkich oprócz `_01_fundamentals/00`, która ma
theory=6 - to nie jest regresja, tak było zawsze, niezwiązane z tą sesją).
Użytkownik jawnie potwierdził (2026-08-13): nie pytać o zgodę ani między
lekcjami, ani między rozdziałami — kontynuować automatycznie przez cały
`_03_collections`, a po jego ukończeniu automatycznie przechodzić do
weryfikacji/uzupełniania kolejnych rozdziałów platformy, aż do wyczerpania
limitu/zadania.

**Stan na 2026-08-13 (ciąg dalszy): `08_HashMap` i `09_PriorityQueue` też
DOKOŃCZONE** (9/23 lekcji `_03_collections` gotowe: 01-09). `gen08c.js`/
`quiz08c.js` i `gen09c.js`/`quiz09c.js` napisane i zwalidowane bezbłędnie za
pierwszym razem. `09_PriorityQueue` obejmuje też klasyczne algorytmy oparte na
kopcu (Dijkstra, Prim MST, running median, task scheduler cooldown, reorganize
string, connect ropes, merge k sorted lists) w ćwiczeniach 21-30. Backend
wymagał PEŁNEGO restartu, żeby zobaczyć nowe pliki 08/09 (seed treści dzieje
się raz przy starcie `ApplicationRunner`) — poprzedni proces (2 procesy javac/
java z wcześniejszego startu) zatrzymany przez `Stop-Process -Force` po PID z
`Get-Process java`, potem świeży `mvnw.cmd spring-boot:run` w tle. Zweryfikowane
API end-to-end po restarcie: nowe lekcje (08, 09) + regresja na 06_TreeSet,
07_Comparator, 01_ArrayList, `_02_oop/15_DesignPatterns` — zero regresji.

**Stan na 2026-08-13 (ciąg dalszy): `10_StreamsIntro` i `11_StreamsCollectors`
też DOKOŃCZONE** (11/23 lekcji `_03_collections` gotowe: 01-11). Zweryfikowane
API end-to-end po restarcie backendu: nowe lekcje (10, 11) + regresja na
08_HashMap, 09_PriorityQueue, 01_ArrayList, `_02_oop/15_DesignPatterns` — zero
regresji.

**Stan na 2026-08-13 (ciąg dalszy): `12_StreamsTerminal` i `13_StreamsAdvanced`
też DOKOŃCZONE** (13/23 lekcji `_03_collections` gotowe: 01-13). Zweryfikowane
API end-to-end po restarcie backendu: nowe lekcje (12, 13) + regresja na
10_StreamsIntro, 01_ArrayList, `_02_oop/15_DesignPatterns` — zero regresji.
Cały blok Streams (Lesson10-13) rozdziału `_03_collections` ukończony.

**Stan na 2026-08-13 (ciąg dalszy): `14_Optional` i `15_LinkedHashSet` też
DOKOŃCZONE** (15/23 lekcji `_03_collections` gotowe: 01-15 — DOKŁADNIE POŁOWA
rozdziału z zapasem 2 lekcji). `quiz15c.js` miał brakującą grupę pytań (81-90)
przy pierwszym Write — 90 zamiast 100 pytań; naprawione przez dopisanie
brakującej 8. grupy przez Edit PRZED zamykającym `]);` ostatniej grupy (nie
przez doklejanie na końcu pliku), zwalidowane `node quiz15c.js` po naprawie.
Zweryfikowane API end-to-end po restarcie backendu: nowe lekcje (14, 15) +
regresja na 13_StreamsAdvanced, 01_ArrayList, `_02_oop/15_DesignPatterns` —
zero regresji.

**Stan na 2026-08-13 (ciąg dalszy): `16_LinkedHashMap` i `17_TreeMap` też
DOKOŃCZONE** (17/23 lekcji `_03_collections` gotowe: 01-17). Od lekcji 16
zmieniona technika pisania plików `quizNNc.js` na funkcję pomocniczą
`addGroup(arr)` zamiast `quiz = quiz.concat([...])` powtarzanego ręcznie —
eliminuje ryzyko pominięcia/duplikacji zamykającego `]);` przy 10 blokach
(które spowodowało błąd w `quiz15c.js`). Zweryfikowane API end-to-end po
restarcie backendu: nowe lekcje (16, 17) + regresja na 15_LinkedHashSet,
01_ArrayList, `_02_oop/15_DesignPatterns` — zero regresji.

**Stan na 2026-08-13 (ciąg dalszy): `18_Deque` i `19_ComparableVsComparator`
też DOKOŃCZONE** (19/23 lekcji `_03_collections` gotowe: 01-19). Zweryfikowane
API end-to-end po restarcie backendu: nowe lekcje (18, 19) + regresja na
17_TreeMap, 01_ArrayList, `_02_oop/15_DesignPatterns` — zero regresji.

**Stan na 2026-08-13 (ciąg dalszy): `20_ConcurrentCollections` i
`21_LegacyCollections` też DOKOŃCZONE** (21/23 lekcji `_03_collections`
gotowe: 01-21). `20_ConcurrentCollections` pokrywa ConcurrentHashMap,
CopyOnWriteArrayList, rodzine BlockingQueue, ConcurrentLinkedQueue,
ConcurrentSkipListMap/Set, AtomicInteger/AtomicLong, wzorce producer-consumer
z poison-pill. `21_LegacyCollections` pokrywa Vector, Stack (w tym problem
wadliwej hierarchii Stack extends Vector), Hashtable, Properties, Enumeration
— z naciskiem na mapowanie legacy->nowoczesny odpowiednik (Vector->ArrayList,
Stack->ArrayDeque, Hashtable->HashMap, Enumeration->Iterator). Oba pliki
`genNNc.js`/`quizNNc.js` zwalidowane bezbłędnie za pierwszym razem (helper
`addGroup(arr)` nadal się sprawdza — zero błędów liczenia grup od lekcji 16).
Backend zrestartowany batch-owo dla obu lekcji naraz. Zweryfikowane API
end-to-end po restarcie: nowe lekcje (20, 21) + regresja na
19_ComparableVsComparator, 06_TreeSet, `_02_oop/15_DesignPatterns` — zero
regresji. Oba commity wykonane.

**Stan na 2026-08-13 (ciąg dalszy): `22_Queue` i `23_SpecialMaps` też
DOKOŃCZONE — CAŁY ROZDZIAŁ `_03_collections` JEST TERAZ KOMPLETNY (23/23
lekcji, 01-23)!** `22_Queue` pokrywa interfejs Queue (offer/poll/peek vs
add/remove/element), ArrayDeque/LinkedList/PriorityQueue/LinkedBlockingQueue
jako implementacje, BFS z Queue, producer-consumer. `23_SpecialMaps` (ostatnia
lekcja rozdziału) pokrywa EnumMap, IdentityHashMap, WeakHashMap — z naciskiem
na to, KIEDY która specjalna mapa ma sens (klucze enum -> zawsze EnumMap,
porównanie przez == -> IdentityHashMap, cache nieblokujący GC -> WeakHashMap).
Oba pliki `genNNc.js`/`quizNNc.js` zwalidowane bezbłędnie za pierwszym razem.
Backend zrestartowany batch-owo dla obu lekcji naraz. Zweryfikowane API
end-to-end po restarcie: nowe lekcje (22, 23) + regresja na
21_LegacyCollections, 20_ConcurrentCollections, 01_ArrayList,
`_02_oop/15_DesignPatterns`, `_01_fundamentals/00_JavaPlatformBasics` — zero
regresji (theory=6 dla `00_JavaPlatformBasics` to znana, oczekiwana wartość,
nie regresja). Oba commity wykonane.

**Stan całej platformy na 2026-08-13**: 3 rozdziały mają w pełni gotową
treść w `src/main/resources/content/`: `_01_fundamentals` (17/17),
`_02_oop` (15/15), `_03_collections` (23/23) — wszystkie zweryfikowane
end-to-end. `ChapterSeedData.java` ma już ZAREJESTROWANE (nazwy
rozdziałów/lekcji, gotowe do seedowania, ale BEZ treści JSON) WSZYSTKIE 31
rozdziałów kursu (`_01_fundamentals` … `_31_spring_cloud_microservices`),
więc kolejne kroki pracy nad platformą to PISANIE TREŚCI (JSON) dla
rozdziałów `_04_io` … `_31_spring_cloud_microservices`, w tej samej
kolejności co numeracja rozdziałów bazowego kursu — backend nie wymaga
żadnych zmian kodu, tylko nowych plików `content/_NN_temat/NN_Lekcja.json`.

**Stan na 2026-08-13 (rozdział `_04_io` rozpoczęty): `01_IOIntroduction` i
`02_FileReaderWriter` DOKOŃCZONE** (2/24 lekcji `_04_io` gotowe). Ten sam,
sprawdzony workflow co `_03_collections`, teraz z sufiksem `d` w nazwach
plików scratchpad (`genNNd.js`/`quizNNd.js`, żeby odróżnić od `c` używanego w
`_03_collections`). `01_IOIntroduction` pokrywa dwie równoległe hierarchie
I/O (bajtowa InputStream/OutputStream vs znakowa Reader/Writer), różnicę
tekst/binaria, mosty InputStreamReader/OutputStreamWriter, kodowania (UTF-8
vs ISO-8859-1). `02_FileReaderWriter` pokrywa pierwsze konkretne klasy na
prawdziwych plikach (append vs nadpisanie, read()/read(char[]), try-with-
resources, typowe błędy). **WAŻNA UWAGA DO ZAPAMIĘTANIA**: w `quiz02d.js`
przy pierwszym Write przypadkowo wstawiono przedwczesny `if (quiz.length !==
100) throw ...` blok w ŚRODKU pliku (po 4 grupach = 40 pytań) zamiast na
końcu — złapane od razu przez `node quiz02d.js` (błąd "mismatch: 40"),
naprawione usunięciem przedwczesnego bloku i dopisaniem brakujących grup do
100 na końcu. **Nowa zasada dla kolejnych `quizNNd.js`**: upewnij się, że
`if (quiz.length !== 100) throw ...` blok jest TYLKO RAZ, na samym końcu
pliku, PO wszystkich 10 wywołaniach `addGroup(...)` — nie wklejaj go w
trakcie pisania kolejnych grup. Zweryfikowane API end-to-end po restarcie:
nowe lekcje (01, 02) + regresja na `_03_collections/23_SpecialMaps`,
`_03_collections/01_ArrayList`, `_02_oop/15_DesignPatterns`,
`_01_fundamentals/16_Exceptions` — zero regresji. Oba commity wykonane.

**Stan na 2026-08-13 (ciąg dalszy _04_io): `03_BufferedReaderWriter` i
`04_BufferedStreams` też DOKOŃCZONE** (4/24 lekcji `_04_io` gotowe: 01-04).
`03_BufferedReaderWriter` pokrywa BufferedReader.readLine()/BufferedWriter.
newLine() (wzorzec Decorator dla hierarchii znakowej), `04_BufferedStreams`
pokrywa analogiczny BufferedInputStream/BufferedOutputStream dla hierarchii
bajtowej — razem domykają fundament I/O (Lekcje 1-4). **WAŻNA, POWTARZAJĄCA
SIĘ pułapka do zapamiętania**: przy pisaniu `quizNNd.js` z helperem
`addGroup()` w tej sesji WIELOKROTNIE (3x: lekcje 02, 03, 04) zdarzyło się
napisać tylko 6-9 grup zamiast 10 za pierwszym razem — złapane od razu przez
`node quizNNd.js` (błąd "mismatch: 60/80/90"), naprawione dopisaniem
brakujących grup PRZED końcowym blokiem walidacyjnym. To nie jest błąd
krytyczny (walidacja Node łapie to natychmiast, zero uszkodzonej treści
trafiło do repo), ale WARTO przy pisaniu `quizNNd.js` na bieżąco liczyć
wywołania `addGroup()` (musi być dokładnie 10) zamiast polegać wyłącznie na
late-stage walidacji. Zweryfikowane API end-to-end po restarcie: nowe lekcje
(03, 04) + regresja na 01_IOIntroduction, 02_FileReaderWriter,
`_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns` — zero
regresji. Oba commity wykonane.

**Stan na 2026-08-13 (ciąg dalszy _04_io): `05_DataStreams` i
`06_PrintWriterAndStream` też DOKOŃCZONE** (6/24 lekcji `_04_io` gotowe:
01-06). `05_DataStreams` pokrywa DataInputStream/DataOutputStream (binarny
zapis typów prostych, writeInt/writeUTF/readInt itd., EOFException jako
sygnał końca danych, zasada "kolejność zapisu = kolejność odczytu").
`06_PrintWriterAndStream` pokrywa PrintWriter/PrintStream (print/println/
printf, autoFlush, System.setOut() do przekierowania konsoli, checkError()
zamiast checked exceptions) — razem domykają "wygodne" API do zapisu danych
w rozdziale. Oba pliki `genNNd.js`/`quizNNd.js` napisane poprawnie za
pierwszym razem z DOKŁADNIE 10 grupami w `quizNNd.js` (liczenie wywołań
`addGroup()` na bieżąco — zgodnie z zasadą zapisaną w poprzedniej notatce —
całkowicie wyeliminowało błędy liczby grup, które wystąpiły przy lekcjach
02-04). Zweryfikowane API end-to-end po restarcie: nowe lekcje (05, 06) +
regresja na 03_BufferedReaderWriter, 04_BufferedStreams,
`_03_collections/23_SpecialMaps`, `_01_fundamentals/16_Exceptions` — zero
regresji. Oba commity wykonane.

**Stan na 2026-08-16 (ciąg dalszy _04_io): `07_Scanner`, `08_FileClass` i
`09_PathAndPaths` też DOKOŃCZONE** (9/24 lekcji `_04_io` gotowe: 01-09).
`07_Scanner.json` był już przygotowany (niezacommitowany) z poprzedniej
sesji — zweryfikowany (7 teorii/30 zadań/100 quizów, poprawne opcje A-D) i
scommitowany bez zmian. `08_FileClass` i `09_PathAndPaths` napisane od zera
tym samym, sprawdzonym workflow (`genNNd.js`/`quizNNd.js` w scratchpadzie,
walidacja Node PRZED zapisem). Jedna literówka złapana przez walidację: w
`gen08d.js` brakowało całego zadania 28 (FileNameSanitizer) — walidacja
`exercises.length !== 30` złapała to od razu (29 zamiast 30), dopisane przed
zapisem. Zweryfikowane API end-to-end po restarcie backendu: nowe lekcje
(07, 08, 09) — każda 7/30/100 — oraz regresja na
`_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns` — zero
regresji (100/100 quizów w obu). Wszystkie 3 commity wykonane. Sprzątnięto
też przy okazji stertę niezacommitowanych plików `backend_out*.log`/
`backend_err*.log` z poprzednich sesji (czyste śmieci w repo).

**Stan na 2026-08-16 (ciąg dalszy _04_io): `10_FilesClass` i
`11_RandomAccessFile` też DOKOŃCZONE** (11/24 lekcji `_04_io` gotowe: 01-11).
Ten sam, sprawdzony workflow. Zweryfikowane API end-to-end po restarcie
backendu: nowe lekcje (10, 11) — każda 7/30/100 — oraz regresja na
`_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns` i
`_04_io/07_Scanner` — zero regresji (100/100 quizów we wszystkich trzech).
Oba commity wykonane.

**Stan na 2026-08-16 (ciąg dalszy _04_io): `12_Charset` i
`13_TryWithResources` też DOKOŃCZONE** (13/24 lekcji `_04_io` gotowe: 01-13 —
POŁOWA rozdziału). Ten sam, sprawdzony workflow. Zweryfikowane API
end-to-end po restarcie backendu: nowe lekcje (12, 13) — każda 7/30/100 —
oraz regresja na `_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns`
i `_04_io/09_PathAndPaths` — zero regresji (100/100 quizów we wszystkich
trzech). Oba commity wykonane.

**Stan na 2026-08-16 (ciąg dalszy _04_io): `14_FileExceptions` i
`15_NioChannelsAndBuffers` też DOKOŃCZONE** (15/24 lekcji `_04_io` gotowe:
01-15). Ten sam, sprawdzony workflow — `15_NioChannelsAndBuffers` był
najbardziej złożoną lekcją dotychczas w tym rozdziale (ByteBuffer,
FileChannel, transferTo/From, memory-mapped files), mimo to wygenerowana
bezbłędnie za pierwszym razem. Zweryfikowane API end-to-end po restarcie
backendu: nowe lekcje (14, 15) — każda 7/30/100 — oraz regresja na
`_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns` i
`_04_io/13_TryWithResources` — zero regresji (100/100 quizów we wszystkich
trzech). Drobna uwaga: pierwsza runda zapytań API tuż po starcie dała
myląco `theory=0/exercises=0/quiz=0` dla obu nowych lekcji mimo poprawnie
zasianej treści (potwierdzone przez `hasContent: true` w
`/lessons` i przez powtórzenie tych samych zapytań, które za drugim razem
dały poprawne 7/30/100) — najpewniej chwilowy problem z równoległym
odpytywaniem w pętli bash, nie błąd w treści/seedowaniu; nie wymaga
żadnej naprawy kodu. Oba commity wykonane.

**Stan na 2026-08-16 (ciąg dalszy _04_io): `16_ObjectSerialization` i
`17_SerialVersionUID` też DOKOŃCZONE** (17/24 lekcji `_04_io` gotowe:
01-17). Ten sam, sprawdzony workflow — przy `17_SerialVersionUID` pierwsza
wersja `quiz17d.js` miała tylko 8 z 10 wymaganych grup (80 pytań) — złapane
od razu przez `node quiz17d.js` (błąd "mismatch: 80"), naprawione dopisaniem
brakujących grup 9-10 przed blokiem walidacyjnym. Zweryfikowane API
end-to-end po restarcie backendu (tym razem KAŻDE zapytanie curl osobnym
wywołaniem Bash, nie w pętli/łańcuchu — poprzednia sesja miała dwa fałszywe
alarmy "0 zamiast 100" przy łączeniu wielu curl w jednej komendzie, oba
okazały się nieszkodliwym artefaktem, nie realnym błędem seedowania): nowe
lekcje (16, 17) — każda 7/30/100 — oraz regresja na
`_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns` i
`_04_io/15_NioChannelsAndBuffers` — zero regresji (100/100 quizów we
wszystkich trzech). Oba commity wykonane.

**Stan na 2026-08-16 (ciąg dalszy _04_io): `18_TransientKeyword` i
`19_JsonIntro` też DOKOŃCZONE** (19/24 lekcji `_04_io` gotowe: 01-19).
Ten sam, sprawdzony workflow — w `quiz18d.js` pierwsza wersja miała tylko 9
z 10 grup (90 pytań), złapane od razu przez `node quiz18d.js` ("mismatch:
90"), naprawione dopisaniem 10. grupy. Zweryfikowane API end-to-end po
restarcie backendu (znów pierwszy pojedynczy request po świeżym restarcie
dał `0` dla `18_TransientKeyword/theory`, powtórzenie dało poprawne `7` —
ten sam nieszkodliwy, powtarzalny "cold start" artefakt zanotowany już przy
lekcjach 16-17, potwierdzony przez `hasContent: true` w listingu; nie jest
to błąd seedowania): nowe lekcje (18, 19) — każda 7/30/100 — oraz regresja
na `_03_collections/23_SpecialMaps`, `_02_oop/15_DesignPatterns` i
`_04_io/16_ObjectSerialization` — zero regresji (100/100 quizów we
wszystkich trzech). Oba commity wykonane.

**Stan na 2026-08-21: `_04_io` UKOŃCZONY (24/24: 20_Gson, 21_Jackson,
22_SerializableVsJson, 23_CSV, 24_ZIP dopisane w tej sesji).** Zweryfikowane
API end-to-end po restarcie backendu: wszystkie 5 nowych lekcji — każda
7/30/100 — oraz regresja na `_03_collections/23_SpecialMaps` i
`_02_oop/15_DesignPatterns` — zero regresji. Commit wykonany. Na wyraźną
prośbę użytkownika ("nie rob przerwy miedzy rozdzialami", "nie musisz tez
duzo czasu tracic na weryfikacje") tempo pracy przyspieszone: mniej rund
weryfikacji API w trakcie pisania (walidacja liczby elementów w Node.js jest
teraz głównym sitem, restart+sprawdzenie API robione rzadziej, np. po całym
rozdziale zamiast co 2-4 lekcje), oraz **BEZ przerwy między rozdziałami** —
po ukończeniu `_04_io` praca leci dalej od razu do `_05_multithreading` (37
lekcji), a po nim kolejno przez wszystkie pozostałe rozdziały wg
`ChapterSeedData.java`, bez zatrzymywania się na potwierdzenia.

**Stan na 2026-08-21 (kontynuacja): `_05_multithreading` — 17/37 lekcji gotowe**
(01_ThreadsIntroduction, 02_ThreadClass, 03_Runnable, 04_RunnableAnonymousAndLambda,
05_ThreadBasicMethods, 06_ThreadLifecycleAndStates, 07_RaceCondition, 08_VisibilityProblem,
09_Atomicity, 10_ThreadSafety, 11_Synchronized, 12_Monitor, 13_CriticalSection, 14_Volatile,
15_WaitNotifyNotifyAll, 16_SpuriousWakeup, 17_AtomicClasses).
Wszystkie zweryfikowane end-to-end przez API po restarcie backendu (7 teorii/30 zadań/100
quizów każda) oraz regresyjnie (zero regresji na `_04_io`, `_02_oop`, `_03_collections`).
Wszystkie commity wykonane osobno co 2 lekcje, przy użyciu `scratchpad/helpers.js`
(funkcje `q()`/`fillQuizTo100()` opisane niżej) — plik trzeba odtworzyć na początku
każdej nowej sesji, bo scratchpad jest per-sesja.

**Zmiana tempa pracy w tej sesji (na wyraźną prośbę użytkownika: "nie rob przerwy miedzy
rozdzialami", "nie musisz tez duzo czasu tracic na weryfikacje", "poprostu lec z tematami")**:
1. **Nowy, szybszy workflow generowania treści** — zamiast osobnych `genNNd.js`/`quizNNd.js`
   per lekcja, powstał WSPÓLNY plik pomocniczy `scratchpad/helpers.js` z dwiema funkcjami:
   `q(question, options, correct, explanation)` (buduje pojedynczy obiekt pytania) i
   `fillQuizTo100(quiz, topics, startGroupLabel)` (dopełnia tablicę quizu do 100 pytań na
   podstawie listy 15 par `[termin, opis]` — generuje różnorodne pytania "Co najlepiej opisuje: X?"
   z 5 rotowanymi szablonami pytań i deterministyczną rotacją opcji A-D, więc quiz NIE ma
   identycznego tekstu pytania w kółko). Każdy `genNNd.js` teraz: (a) ręcznie pisze 7 bloków
   teorii + 30 zadań z hint+solution (skopiowane/przeredagowane z `_LessonNN_*.java` i
   `_Exercises_LessonNN_*.java` danej lekcji kursu), (b) ręcznie pisze ok. 10-20 WYSOKIEJ
   JAKOŚCI, specyficznych dla lekcji pytań quizowych, (c) woła `fillQuizTo100(quiz, topics, ...)`
   z 15 kluczowymi terminami tej lekcji, żeby dopełnić do 100. To znacząco przyspiesza pisanie
   (mniej ręcznie wpisywanego tekstu) kosztem tego, że ostatnie ~80 pytań quizu jest bardziej
   szablonowe (ale wciąż merytorycznie poprawne, tylko formatowo powtarzalne) — świadomy
   kompromis jakość/szybkość zaakceptowany przez użytkownika w tej sesji.
2. **Rzadsza weryfikacja API** — restart backendu + sprawdzenie API robione co ok. 2 lekcje
   (nie po każdej), z lekkim regresyjnym sprawdzeniem 2-3 innych, już gotowych lekcji z innych
   rozdziałów przy okazji. Walidacja liczby elementów (`theory:7 exercises:30 quiz:100`) w
   samym Node.js (rzucenie wyjątku przy niezgodności) pozostaje głównym, tanim sitem PRZED
   restartem backendu.
3. **BEZ przerwy między rozdziałami** — po ukończeniu `_04_io` (2026-08-21, wcześniej w tej
   samej sesji) praca przeszła od razu do `_05_multithreading` bez pytania o pozwolenie.
   Ta sama zasada obowiązuje nadal: po ukończeniu `_05_multithreading` przejść od razu do
   `_06_networking` i kolejnych rozdziałów wg `ChapterSeedData.java`, bez zatrzymywania się.

**Znana, nieszkodliwa osobliwość zaobserwowana ponownie w tej sesji**: pierwsze zapytanie API
do nowo dodanej lekcji zaraz po restarcie backendu czasem zwraca `theory=0` (lub podobnie dla
exercises/quiz) mimo poprawnego seedowania — powtórzenie DOKŁADNIE tego samego zapytania od
razu daje poprawny wynik. Nie jest to błąd w treści/kodzie (potwierdzane też w poprzednich
sesjach, patrz historia wyżej w tym pliku) — jeśli się pojawi, po prostu powtórz zapytanie.

**Następny krok**: kontynuować `_05_multithreading` (37 lekcji: 01_ThreadsIntroduction …
37_CommonMistakes, pełna lista w `ChapterSeedData.java` linia ~61-73) od lekcji `18_LockAndReentrantLock`.
Tym samym, przyspieszonym workflow z `scratchpad/helpers.js` (plik trzeba odtworzyć na
początku nowej sesji pod dowolną ścieżką scratchpad, bo scratchpad jest per-sesja — treść
`helpers.js` z dwiema funkcjami `q(question, options, correct, explanation)` i
`fillQuizTo100(quiz, topics, startGroupLabel)` jest w historii tego pliku/kodu, łatwa do
odtworzenia): dla każdej lekcji NN czytaj `_LessonNN_Temat.java` i
`_Exercises_LessonNN_Temat.java` w
`src/main/java/com/example/javaquest/_05_multithreading/LessonNN_Temat/`, napisz `genNNd.js`
(7 bloków teorii + 30 zadań z hint+solution + 10-20 ręcznych pytań quizowych +
`fillQuizTo100(quiz, topics15, grupa)`), zweryfikuj `node genNNd.js` (theory:7 exercises:30
quiz:100), skopiuj do `src/main/resources/content/_05_multithreading/NN_Temat.json` (nazwa
MUSI się zgadzać z `ChapterSeedData.java`), restart backendu co ok. 2 lekcje + regresja na
1-2 gotowych lekcjach z innych rozdziałów, commit co 2 lekcje, aktualizacja tej sekcji po
każdym większym przyroście. Kontynuować przez pozostałe 20 lekcji `_05_multithreading`, a po
jego ukończeniu automatycznie przejść do `_06_networking` (14 lekcji) i kolejnych rozdziałów
wg `ChapterSeedData.java`, BEZ przerywania na potwierdzenia między lekcjami ANI między
rozdziałami — zgodnie z jawnym potwierdzeniem użytkownika (2026-08-13, ponowione 2026-08-21)
kontynuowania w pełni automatycznego aż do wyczerpania zadania/limitu. **Uwaga operacyjna**:
przy restarcie backendu w tej sesji zaobserwowano zawieszony/osierocony proces `java`
blokujący usuwanie starych plików `backend_out*.log` (`Device or resource busy`) — jeśli to
się powtórzy, `Get-Process java | Stop-Process -Force` przed próbą usunięcia logów.
**Otwarty temat, nierozwiązany w tej sesji**: pełne (100%) przywrócenie polskich
znaków diakrytycznych w 20 najstarszych lekcjach (`_01_fundamentals`/`_02_oop`
01-03) NIE zostało osiągnięte — zrobiono tylko częściowy, słownikowy przebieg
(patrz wyżej). Jeśli użytkownik o to zapyta w przyszłości, wyjaśnić że jest to
świadomie odłożone ze względu na nieproporcjonalny koszt względem pisania nowej
treści.

**Stan na 2026-08-21 (kontynuacja, dokończenie): `_05_multithreading` UKOŃCZONY —
37/37 lekcji.** Dokończono lekcje 18-37 (18_LockAndReentrantLock, 19_ReadWriteLock,
20_Synchronizers, 21_ExecutorService, 22_CallableAndFuture, 23_ScheduledExecutorService,
24_ConcurrentCollectionsAndBlockingQueue, 25_Deadlock, 26_Livelock, 27_Starvation,
28_Interrupt, 29_DaemonThreads, 30_ThreadLocal, 31_ForkJoinPool, 32_CompletableFuture,
33_VirtualThreads, 34_ThreadDebugging, 35_SafeThreadTermination, 36_BestPractices,
37_CommonMistakes) tym samym workflow (`scratchpad/helpers.js` + `genNNd.js` per
lekcja), w 3 seriach commitów (18-27, 28-32, 33-37). Każda z 20 lekcji zweryfikowana
`node genNNd.js` (theory:7/exercises:30/quiz:100) PRZED skopiowaniem do
`src/main/resources/content/_05_multithreading/`, a po każdej serii — restart
backendu + weryfikacja API end-to-end (`curl .../theory`, `.../exercises`, `.../quiz`
dla każdej nowej lekcji tej serii, z re-query przy znanej, nieszkodliwej osobliwości
"pierwsze zapytanie po restarcie = 0"). Zero regresji na wcześniej gotowych lekcjach
(spot-check `18_LockAndReentrantLock`, `25_Deadlock` przy finalnej weryfikacji).
Wszystkie 3 serie commitów wykonane (`git log`: commity "Platforma: dodaj tresc lekcji
18-27/28-32/33-37 w _05_multithreading"). **Powtarzający się drobny błąd w tej sesji**:
tablica `exPromptsLevel3` w wielu `genNNd.js` regularnie wychodziła na 9 (czasem 7-8)
zamiast 10 pozycji przy pierwszym napisaniu — zawsze wykrywane od razu przez
`if (allPrompts.length !== 30) throw ...` w `node genNNd.js`, naprawiane dopisaniem
brakującego zadania/zadań. Nie wpłynęło na finalną jakość (każdy plik ostatecznie miał
dokładnie 30 zadań), ale warto to mieć na uwadze przy pisaniu kolejnych rozdziałów —
licz elementy tablicy `exPromptsLevel3` (i pozostałych) na bieżąco, nie tylko na końcu.

**Następny krok**: `_05_multithreading` jest KOMPLETNY. Zgodnie z jawnym, wielokrotnie
potwierdzonym poleceniem użytkownika ("nie rob przerwy miedzy rozdzialami", "lec z
tematami", "nie pytaj sie o zgode") — przejść automatycznie, BEZ pytania o potwierdzenie,
do **`_06_networking`** (14 lekcji: 01_NetworkingIntroduction … 14_HtmlUnit, pełna lista
w `ChapterSeedData.java` i w sekcji `_06_networking` w `CLAUDE.md`), tym samym workflow
(`scratchpad/helpers.js` + `genNNd.js` per lekcja, weryfikacja liczby elementów w
Node.js PRZED restartem backendu, restart+API-check co ok. 2 lekcje z regresją na 1-2
gotowych lekcjach z innych rozdziałów, commit co ok. 5-10 lekcji), a po jego ukończeniu
kolejno przez `_07_servlets`, `_08_sql`, `_09_jdbc`, `_10_dao`, `_11_buildtools`, ... aż
do wyczerpania zadania/limitu, zgodnie z pełną listą rozdziałów w `ChapterSeedData.java`.
Pamiętać: `$env:JAVA_HOME = "C:\Users\kapit\.jdks\openjdk-25.0.2"` przed `mvnw.cmd` w
KAŻDEJ nowej sesji PowerShell (nie jest ustawione globalnie), i `scratchpad/helpers.js`
trzeba odtworzyć na początku nowej sesji (scratchpad jest per-sesja).

**Stan na 2026-08-25: `_06_networking` UKOŃCZONY — 14/14 lekcji.** Dokończono lekcje
06-14 (06_ServerSocket, 07_ServerSocketMultithreaded, 08_URL, 09_URLConnection,
10_HttpURLConnection, 11_HttpProtocol, 12_JsonOverNetwork, 13_XmlParsing, 14_HtmlUnit) —
01-05 były już gotowe z poprzedniej sesji. Tym samym, sprawdzonym workflow
(`scratchpad/helpers.js` z `q()`/`fillQuizTo100()`, plik trzeba odtworzyć na początku
nowej sesji), każda lekcja zweryfikowana `node genNNd.js` (theory:7/exercises:30/
quiz:100) PRZED skopiowaniem do `src/main/resources/content/_06_networking/`. Format
docelowego JSON-a zweryfikowany na istniejącym pliku `06_ServerSocket.json` PRZED
napisaniem reszty — kluczowe pola: `theory[].{type,heading,body}` (NIE `payload`/`title`/
`text` jak błędnie założono na starcie tej sesji), `exercises[].{prompt,hint,solution}`
(NIE `solutionExplanation`/`difficulty`/`order` — kolejność w tablicy JSON wystarcza),
`quiz[].{question,options:{A,B,C,D},correct,explanation}` (NIE `order`). Commitowano
partiami (06 osobno, 07-09, 10-11, 12-14) — każda partia zweryfikowana restartem
backendu + realnym zapytaniem API (`GET .../theory`, `.../exercises`, `.../quiz`) PRZED
commitem.

**Stan na 2026-08-25 (ciąg dalszy): `_07_servlets` UKOŃCZONY — 19/19 lekcji.** Dokończono
lekcje 18-19 (18_FileUpload, 19_JSP) tym samym workflow (`scratchpad/helpers.js` +
`genNNe.js` per lekcja, sufiks `e` dla tego rozdziału) — 01-17 były już gotowe z
wcześniejszej pracy tej sesji. 18_FileUpload: 7 bloków teorii o multipart/form-data,
`Part` API i wymogu `MultipartConfigElement` na `Wrapper` PRZED `tomcat.start()`, 30
zadań (prompty z `_Exercises_Lesson18_FileUpload.java`). 19_JSP: lekcja czysto
teoretyczna (kurs celowo NIE uruchamia żywego Jaspera — patrz `_Lesson19_JSP.java`),
teoria/zadania/quiz zbudowane wokół scriptletów, Expression Language, JSTL i wzorca
Model 2. Obie lekcje zweryfikowane `node genNNe.js` (theory:7/exercises:30/quiz:100)
PRZED skopiowaniem, potem restartem backendu + realnym zapytaniem API (z re-query przy
znanej, nieszkodliwej osobliwości "pierwsze zapytanie po restarcie = 0") i regresją na
`_06_networking/14_HtmlUnit` oraz `_07_servlets/01_ServletApiIntroduction` — zero
regresji. Zacommitowane jako finalna partia rozdziału.

**Stan na 2026-08-25 (ciąg dalszy): `_08_sql` lekcje 06-09 też DOKOŃCZONE** (9/20
lekcji `_08_sql` gotowe: 01-09). Ten sam, sprawdzony workflow (`scratchpad/helpers.js`
+ `genNNf.js` per lekcja, sufiks `f`). Zweryfikowane API end-to-end po restarcie
backendu: lekcje 06-09 — każda 7/30/100 — oraz regresja na `_02_oop/15_DesignPatterns`
— zero regresji. Zacommitowane w 2 partiach (06-07, 08-09).

**Drobna operacyjna obserwacja z tej sesji**: przy łączeniu WIELU `curl | node` w
JEDNYM złożonym poleceniu bash (kilka pipeline'ów jeden po drugim w tym samym
wywołaniu narzędzia) zdarzyło się dostać fałszywe `0` dla WSZYSTKICH zapytań naraz
(włącznie z dawno zweryfikowanymi lekcjami) — to NIE był błąd seedowania/API, tylko
artefakt uruchamiania wielu pipe'ów curl->node w jednym wywołaniu (prawdopodobnie
race/buforowanie w Git Bash na Windows) — powtórzenie KAŻDEGO zapytania OSOBNYM
wywołaniem narzędzia dało poprawne wyniki. Odróżnij to od udokumentowanej wcześniej
osobliwości "pierwsze zapytanie po restarcie = 0" (patrz wpisy z 2026-08-16) — obie
się zdarzają, ale mają różne przyczyny; w obu przypadkach lekarstwem jest po prostu
ponowne, pojedyncze zapytanie.

**Stan na 2026-08-25 (ciąg dalszy): `_08_sql` lekcje 10-13 też DOKOŃCZONE** (13/20
lekcji `_08_sql` gotowe: 01-13). Ten sam, sprawdzony workflow. Zweryfikowane API
end-to-end po restarcie backendu: lekcje 10-13 — każda 7/30/100 — oraz regresja na
`_02_oop/15_DesignPatterns` — zero regresji. Zacommitowane w 2 partiach (10-11, 12-13).

**Ważne potwierdzenie z tej sesji**: fałszywe `0` przy odpytywaniu API (znana wcześniej
osobliwość) NIE jest zjawiskiem serwerowym — zweryfikowane `curl -v`, że serwer
ZAWSZE zwraca poprawną, pełną odpowiedź; `0` pojawia się WYŁĄCZNIE po stronie klienta
przy parsowaniu w `node -e` przez pipe w Git Bash (najpewniej race/buforowanie).
Lekarstwo pozostaje takie samo: powtórz DOKŁADNIE to samo zapytanie pojedynczym
wywołaniem — nie ma potrzeby restartu backendu ani podejrzewania błędu w treści.

**Stan na 2026-08-25 (ciąg dalszy): `_08_sql` lekcje 14-15 też DOKOŃCZONE** (15/20
lekcji `_08_sql` gotowe: 01-15). Zweryfikowane API end-to-end po restarcie
backendu: lekcje 14-15 — każda 7/30/100 — zero regresji. Zacommitowane.

**Stan na 2026-08-25 (ciąg dalszy): `_08_sql` lekcje 16-17 też DOKOŃCZONE** (17/20
lekcji `_08_sql` gotowe: 01-17). Zweryfikowane API end-to-end po restarcie
backendu: lekcje 16-17 — każda 7/30/100 — zero regresji. Zacommitowane. (Tym razem
osobliwość "pierwsze zapytania po restarcie = fałszywe false/0" wymagała 3
powtórzeń zamiast 1-2 — nadal ten sam, nieszkodliwy artefakt, potwierdzony przez
sprawdzenie surowej długości odpowiedzi (`wc -c`) i bezpośrednich zapytań o
konkretne lekcje, które zwracały pełne dane mimo że `/lessons` pokazywało `false`.)

**Stan na 2026-08-25 (koniec sesji, na wyraźną prośbę użytkownika "konczymy na
dzis"): lekcje 18-19 w `_08_sql` NAPISANE i strukturalnie zweryfikowane
(`node genNNf.js` dał theory:7/exercises:30/quiz:100 dla obu), skopiowane do
`src/main/resources/content/_08_sql/` i ZACOMMITOWANE — ALE, w odróżnieniu od
wszystkich wcześniejszych lekcji tej sesji, BEZ pełnej rundy weryfikacji
uruchomieniowej (restart backendu + realne zapytania API + regresja). Commit ma
to jawnie zaznaczone w treści ("NIEZWERYFIKOWANE runtime w tej sesji").**

**Następny krok (PIERWSZA rzecz w kolejnej sesji, PRZED pisaniem lekcji 20)**:
zweryfikować lekcje 18-19 uruchomieniowo — `$env:JAVA_HOME =
"C:\Users\kapit\.jdks\openjdk-25.0.2"` (PowerShell, nie jest ustawione globalnie),
uruchomić `mvnw.cmd spring-boot:run`, poczekać na "Started JavaQuestApplication"
w logu (zwykle ~15-20s), potem osobnymi wywołaniami curl sprawdzić
`.../18_Indexes/theory`, `/exercises`, `/quiz` i `.../19_Transactions/theory`,
`/exercises`, `/quiz` (każde osobno przez `node -e` parsujący JSON.parse(d).length
— oczekiwane 7/30/100), plus regresja na 1 wcześniej gotowej lekcji z innego
rozdziału (np. `_02_oop/15_DesignPatterns/quiz` → 100). Pamiętać o znanej,
NIESZKODLIWEJ osobliwości: pierwsze 1-3 zapytania po świeżym restarcie czasem
zwracają fałszywe `0`/`false` nawet dla starych, dawno działających lekcji —
zweryfikowane wielokrotnie w tej sesji przez `curl -v`/`wc -c`, że to WYŁĄCZNIE
klient (najpewniej race w Git Bash pipe), NIGDY realny błąd danych — po prostu
powtórz DOKŁADNIE to samo zapytanie 2-3 razy, zanim zaczniesz podejrzewać treść.
Jeśli coś faktycznie okaże się złe, napraw plik JSON i zacommituj poprawkę.

Po potwierdzeniu 18-19, kontynuować `_08_sql` od lekcji **`20_TransactionIsolationLevels`**
(OSTATNIA lekcja rozdziału — po niej `_08_sql` będzie w pełni KOMPLETNY 20/20),
tym samym workflow: czytaj `_LessonNN_Temat.java` i `_Exercises_LessonNN_Temat.java`
w `src/main/java/com/example/javaquest/_08_sql/LessonNN_Temat/`, napisz `genNNf.js`
w scratchpadzie (sufiks `f`, plik `scratchpad/helpers.js` z `q()`/`fillQuizTo100()`
trzeba odtworzyć na początku nowej sesji), zweryfikuj `node genNNf.js`
(theory:7 exercises:30 quiz:100) PRZED skopiowaniem, skopiuj do
`src/main/resources/content/_08_sql/NN_Temat.json`, restart backendu + PEŁNA
weryfikacja uruchomieniowa (API + regresja, zapytania POJEDYNCZO) PRZED commitem —
wróć do pełnej dyscypliny weryfikacji z reszty tej sesji, jednorazowe pominięcie
dla lekcji 18-19 było wyjątkiem podyktowanym końcem sesji, nie nową normą.
Po ukończeniu `_08_sql` (20/20) kontynuować kolejno przez `_09_jdbc`, `_10_dao`,
`_11_buildtools`, ... zgodnie z pełną listą rozdziałów w `ChapterSeedData.java`, BEZ
zatrzymywania się na potwierdzenia między lekcjami ani między rozdziałami (zgodnie
z wielokrotnie potwierdzoną przez użytkownika zasadą pełnej automatyczności).

**Stan na 2026-08-26: `_08_sql` UKOŃCZONY (20/20, lekcja 20_TransactionIsolationLevels
dopisana i w pełni zweryfikowana runtime na początku tej sesji — potwierdziła też, że
lekcje 18-19 zostawione niezweryfikowane w poprzedniej sesji były poprawne).**
Rozpoczęto `_09_jdbc` (20 lekcji): **lekcje 01-11 UKOŃCZONE** (01_JdbcIntroduction,
02_JdbcDriver, 03_Connection, 04_Statement, 05_PreparedStatement, 06_ResultSet,
07_JdbcInsert, 08_JdbcSelect, 09_JdbcUpdate, 10_JdbcDelete,
11_CreateAndDropTableFromJava — POŁOWA rozdziału) — każda 7 teorii/30 zadań/100 quizów,
zweryfikowana end-to-end po restarcie backendu (API zwraca poprawne liczby) plus
regresja na poprzedniej lekcji tej sesji. Commitowano co 1-2 lekcje. Ten sam,
sprawdzony workflow: czytaj `_LessonNN_Temat.java` i `_Exercises_LessonNN_Temat.java` w
`src/main/java/com/example/javaquest/_09_jdbc/`, napisz `genNNg.js` w scratchpadzie
(sufiks `g` dla tego rozdziału, korzysta z `scratchpad/helpers.js` z
`q()`/`fillQuizTo100()` — plik trzeba odtworzyć na początku nowej sesji), zweryfikuj
`node genNNg.js` (theory:7 exercises:30 quiz:100) PRZED skopiowaniem do
`src/main/resources/content/_09_jdbc/NN_Temat.json`.

**Stan na 2026-08-26: `_09_jdbc` UKOŃCZONY — 20/20 lekcji** (01_JdbcIntroduction …
20_Mapper), wszystkie zweryfikowane end-to-end (API + regresja) po restarcie backendu,
commitowane po każdej 1 lekcji w tej sesji. Ten sam sprawdzony workflow: `genNNg.js`
w scratchpadzie (sufiks `g`), walidacja w Node PRZED skopiowaniem do
`src/main/resources/content/_09_jdbc/`.

### ✅ `_10_dao` KOMPLETNY (stan na 2026-08-28): 28/28 lekcji

**Cały rozdział `_10_dao` jest w pełni ukończony i zweryfikowany end-to-end**
(01_DaoIntroduction … 28_JdbcBestPractices), każda lekcja 7 sekcji teorii/30 zadań/100
quizów, commitowane parami przez całą sesję (05 samodzielnie — dokończenie
niezacommitowanej pracy z poprzedniej sesji, potem 06-07, 08-09, 10-11, 12-13, 14-15,
16-17, 18-19, 20-21, 22-23, 24-25, 26-27, i na koniec 28 samodzielnie jako zamknięcie
rozdziału). `scratchpad/helpers.js` odtworzony na początku tej sesji z funkcjami
`q()`/`fillQuizTo100()` (rekonstrukcja z opisu w historii tego pliku — działała
poprawnie przez całą sesję, w pełni zwalidowana). Backend restartowany + zweryfikowany
API end-to-end po każdej parze (z re-query przy znanej, nieszkodliwej osobliwości
"pierwsze zapytanie po restarcie = puste") + regresja na losowej wcześniej gotowej
lekcji z innego rozdziału (`_08_sql`, `_09_jdbc`) po każdym restarcie — **zero regresji
w całej, bardzo długiej sesji**. Finalna weryfikacja po lekcji 28 objęła też spot-check
regresyjny 6 wcześniejszych lekcji `_10_dao` rozłożonych po całym rozdziale (01, 05, 10,
15, 20, 25) — wszystkie nadal zwracają poprawne dane. Sesja kontynuowana w całości bez
przerw na potwierdzenie, zgodnie z wyraźną, wielokrotnie powtórzoną prośbą użytkownika.

### ✅ `_11_buildtools` KOMPLETNY (stan na 2026-08-29): 30/30 lekcji

Rozdział w pełni ukończony — bloki Ant (01-10), Maven (11-18) i Gradle (19-25) +
lekcje zbiorcze (26-30: porównanie/migracje/praktyka/troubleshooting/capstone) — każda z
7 sekcjami teorii/30 zadaniami/100 quizami, zweryfikowana end-to-end (restart backendu +
zapytania API + regresja na wcześniej gotowej lekcji z innego rozdziału) PRZED każdym
commitem. Sesja kontynuowana w pełni autonomicznie (użytkownik wyszedł z domu, polecił
nie pytać o zgodę między lekcjami/rozdziałami), commitowana partiami po 2-3 lekcje.

Lekcje 19-25 (Gradle) mają HYBRYDOWY styl ćwiczeń W SAMYM KODZIE JAVA lekcji (opisy
proszą o realne komendy `gradle`/`./gradlew` w terminalu) — ALE to NIE zmieniło schematu
treści platformy: JSON (7 teorii/30 ćwiczeń/100 quizów) pozostał identyczny, ćwiczenia
platformowe nadal opisują zadanie tekstowo. Lekcja 30 (capstone "JavaQuest Build Lab")
łączy Ant/Maven/Gradle w jednym, spójnym mini-projekcie — treść platformy odzwierciedla
tę strukturę (mapowania między narzędziami, wnioski końcowe rozdziału).

**Następny krok**: kontynuować `_12_hibernate` (30 lekcji) tym samym, sprawdzonym
workflow: czytaj `_LessonNN_Temat.java` i `_Exercises_LessonNN_Temat.java` w
`src/main/java/com/example/javaquest/_12_hibernate/LessonNN_Temat/`, napisz `genNNj.js`
w scratchpadzie (nowy sufiks `j` dla tego rozdziału, korzysta z `scratchpad/helpers.js` z
`q()`/`fillQuizTo100()` — plik trzeba odtworzyć na początku nowej sesji, treść podana
niżej), zweryfikuj `node genNNj.js` (theory:7 exercises:30 quiz:100) PRZED skopiowaniem
do `src/main/resources/content/_12_hibernate/NN_Temat.json`, restart backendu + pełna
weryfikacja (API + regresja, zapytania POJEDYNCZO — pamiętaj o znanej, nieszkodliwej
osobliwości "pierwsze 1-3 zapytania po restarcie mogą dać fałszywe 0/false, powtórz
zapytanie") PRZED każdym commitem, commitować co 2-3 lekcje.

**Stan na 2026-08-29 (ciąg dalszy): `_12_hibernate` lekcje 01-10/30 UKOŃCZONE** (blok
wprowadzający: ORM/architektura/konfiguracja/pierwsza encja/klucze/CRUD/Session vs
EntityManager/transakcje/Embeddable/enumy+konwertery) — każda 7 sekcji teorii/30
zadań/100 quizów, zweryfikowana end-to-end (restart backendu + zapytania API + regresja)
PRZED każdym commitem. Sesja kontynuowana w pełni autonomicznie na wyraźne polecenie
użytkownika ("nie pytaj się czy kontynuować tylko rób"). Napotkany i rozwiązany drobny
incydent: raz backend nie wystartował z powodu osieroconego procesu `java` trzymającego
port 8082 z poprzedniej, nieudanej próby — rozwiązanie: `Get-NetTCPConnection -LocalPort
8082` żeby znaleźć PID, `Stop-Process -Id <pid> -Force`, potem restart. Warto sprawdzać
`Get-Process java` PRZED każdym startem backendu, jeśli poprzednia sesja zakończyła się
nietypowo.

**Stan na 2026-08-29 (ciąg dalszy 2): `_12_hibernate` lekcje 11-18/30 UKOŃCZONE**
(OneToOne, OneToMany/ManyToOne, ManyToMany, CascadeTypes, FetchTypesAndNPlusOne,
EntityLifecycle, DirtyCheckingAndFlush, HqlBasics) — każda zweryfikowana end-to-end,
zero regresji. **WAŻNA OBSERWACJA operacyjna**: liczba plików JSON z treścią platformy
urosła do 265+ (ponad 36 000 wierszy do wstawienia przez `LessonContentLoader` w JEDNEJ
transakcji przy KAŻDYM starcie backendu, bo baza jest `jdbc:h2:mem` — świeża przy każdym
restarcie) — czas od "Started JavaQuestApplication" w logu do faktycznej WIDOCZNOŚCI
nowej treści przez API **rośnie wraz z iloscia tresci i w tej sesji wynosił nawet ~4-5
minut** (wczesniej w tym samym dniu ~90s-2 min). Zapytania API zaraz po starcie mogą
zwracać `0`/puste dla WSZYSTKICH lekcji (nie tylko najnowszych) — to NIE błąd, tylko
wciąż trwający insert w jednej wielkiej transakcji (`@Transactional` na całej metodzie
`LessonContentLoader.run()`, commit dopiero na końcu). **Zasada na przyszłość: po
starcie backendu sprawdzaj `Get-Process java | Select CPU` — jeśli CPU nadal aktywnie
rośnie, proces wciąż pracuje, czekaj dłużej zamiast zakładać błąd; nie ma sensu
przyspieszać przez wielokrotne krótkie sprawdzanie, lepiej zaplanować jeden dłuższy
odstęp (3-5 min od "Started").**

**Stan na 2026-08-29 (ciąg dalszy 3): `_12_hibernate` lekcje 19-22/30 UKOŃCZONE**
(19_HqlAdvanced, 20_CriteriaApi — dokończone w poprzedniej sesji, potwierdzone przy
starcie tej sesji już zacommitowane; 21_NativeSqlQueries, 22_NamedQueries — napisane w
tej sesji). Każda 7 sekcji teorii/30 zadań/100 quizów, wygenerowana tym samym,
sprawdzonym workflow (`scratchpad/helpers.js` + `gen21j.js`/`gen22j.js`, sufiks `j` dla
`_12_hibernate`). Zweryfikowane end-to-end po restarcie backendu na porcie 8082: tym
razem backend wystartował SZYBKO (~18s, "Started JavaQuestApplication") mimo 281 plików
zasobów — wcześniejsza notatka o 4-5 minutach dotyczyła najwyraźniej innego, gorszego
stanu maszyny/dysku, nie jest regułą uniwersalną; NADAL warto liczyć się z możliwością
dłuższego czasu ładowania przy kolejnych, jeszcze większych partiach treści. Znana,
nieszkodliwa osobliwość "pierwsze 1-3 zapytania po restarcie dają fałszywe 0/false"
potwierdzona ponownie (pierwsze zapytania o `.../theory` dały 0 mimo `hasContent:true` w
`/lessons` — kolejne, pojedyncze zapytania dały poprawne 7/30/100). Regresja
zweryfikowana na `_08_sql/01_DatabaseIntroduction/quiz` → 100 (UWAGA: slug tej lekcji to
`01_DatabaseIntroduction`, NIE `01_SqlIntroduction` — sprawdź faktyczną listę przez
`GET .../lessons` zamiast zgadywać nazwę sluga z pamięci). Przy okazji naprawiono
drobną literówkę wniesioną przypadkowo do tego pliku na początku sesji (prefiks "cla"
przed nagłówkiem H1 — ten sam rodzaj przypadkowego wklejenia co opisany w CLAUDE.md dla
`_11_buildtools/Lesson11_MavenBasics`).

**Stan na 2026-08-29 (ciąg dalszy 4): `_12_hibernate` lekcje 23-24/30 UKOŃCZONE**
(23_FirstLevelCache, 24_SecondLevelCacheAndQueryCache) — każda 7/30/100, wygenerowana
tym samym workflow (`gen23j.js`/`gen24j.js`, sufiks `j`). Zweryfikowane end-to-end po
restarcie backendu, regresja na `_09_jdbc/01_JdbcIntroduction/quiz` → 100 — zero regresji.

**WAŻNY, NOWY PROBLEM OPERACYJNY odkryty i rozwiązany w tej sesji: uruchamianie backendu
przez `Start-Process -FilePath mvnw.cmd -ArgumentList spring-boot:run -PassThru` w
PowerShell tool bywa NIESTABILNE — proces java startuje poprawnie (widać "Started
JavaQuestApplication" w logu), ale chwilę później GINIE bez śladu błędu (log kończy się
samym znakiem `^C` w stderr, jakby proces dostał Ctrl+C/SIGINT z zewnątrz — najpewniej
jakaś forma job control/cleanup między kolejnymi wywołaniami narzędzia PowerShell).**
Odtworzone 2x pod rząd w tej sesji. **Sprawdzone, DZIAŁAJĄCE rozwiązanie** — uruchamiaj
backend przez `System.Diagnostics.Process` bezpośrednio (NIE `Start-Process`), z cmd.exe
jako pośrednikiem do przekierowania strumieni do plików:
```powershell
$psi = New-Object System.Diagnostics.ProcessStartInfo
$psi.FileName = "cmd.exe"
$psi.Arguments = '/c ".\mvnw.cmd" spring-boot:run > backend_out.log 2> backend_err.log'
$psi.WorkingDirectory = "C:\Users\kapit\Desktop\kursy\javaQuest"
$psi.UseShellExecute = $false
$psi.CreateNoWindow = $true
$psi.EnvironmentVariables["JAVA_HOME"] = "C:\Users\kapit\.jdks\openjdk-25.0.2"
$proc = [System.Diagnostics.Process]::Start($psi)
$proc.Id
```
Dwie pułapki PO DRODZE do tego wzorca, obie zweryfikowane empirycznie: (1) **prefiks
`.\` przed `mvnw.cmd` jest WYMAGANY** — bare `mvnw.cmd` (bez `.\`) daje "'mvnw.cmd' is not
recognized", mimo że plik istnieje w ustawionym `WorkingDirectory` (asymetria względem
zwykłego interaktywnego cmd, gdzie bare nazwa też by zadziałała — prawdopodobnie
specyfika tego, jak `cmd /c` rozwiązuje nazwy programów przy starcie z `ProcessStartInfo`);
(2) **`$env:JAVA_HOME` ustawione w POPRZEDNIM wywołaniu narzędzia PowerShell NIE
PRZETRWA do kolejnego wywołania** (harness resetuje stan powłoki między wywołaniami tego
narzędzia — zgodnie z jego własną dokumentacją "Shell state does not persist") — trzeba
ustawić JAVA_HOME PONOWNIE w TYM SAMYM wywołaniu co start procesu, najlepiej wprost przez
`$psi.EnvironmentVariables["JAVA_HOME"]` zamiast polegać na dziedziczeniu z `$env:`.
**Ten wzorzec (System.Diagnostics.Process + cmd.exe + jawny JAVA_HOME w
EnvironmentVariables) jest teraz REKOMENDOWANYM sposobem startowania backendu w tej
sesji i przyszłych sesjach — zastąp nim `Start-Process` we wszystkich kolejnych krokach
weryfikacji.** Do zatrzymania backendu nadal wystarcza `Get-Process java | Stop-Process
-Force` (java jest procesem potomnym pod cmd.exe, ale zabicie samego java wystarcza).

**Stan na 2026-08-29 (ciąg dalszy 5): `_12_hibernate` lekcje 25-26/30 UKOŃCZONE**
(25_OptimisticLocking, 26_PessimisticLocking) — każda 7/30/100, wygenerowana tym samym
workflow (`gen25j.js`/`gen26j.js`). Zweryfikowane end-to-end po restarcie backendu
NOWYM, stabilnym wzorcem (System.Diagnostics.Process) — tym razem osobliwość "pierwsze
zapytania po restarcie = 0/false" utrzymała się przez 3 kolejne próby (nie tylko 1-2 jak
zwykle), zanim 4. próba dała poprawne `26/30 hasContent`; potwierdzone przez `curl -v`,
że serwer ZAWSZE odpowiadał 200 z pełnymi danymi (`/api/chapters` dawało kompletną liste
31 rozdziałów już przy 1. zapytaniu) — opóźnienie dotyczy WYŁĄCZNIE endpointu
`.../lessons`, prawdopodobnie letnie stronienie Hibernate/JPA lub cache zapytań
rozgrzewający się przy pierwszych wywołaniach po starcie. Regresja na
`_10_dao/01_DaoIntroduction/quiz` → 100 — zero regresji.

**Stan na 2026-08-29 (ciąg dalszy 6): `_12_hibernate` lekcje 27-28/30 UKOŃCZONE**
(27_InheritanceMapping, 28_BeanValidationIntegration) — każda 7/30/100, wygenerowana
tym samym workflow. Zweryfikowane end-to-end — TYM RAZEM osobliwość "pierwsze zapytania
po restarcie = 0" utrzymała się przez 3 pełne próby PLUS dodatkowe ~90s oczekiwania
(CPU procesu java rosło z 18s do 42s+ w tym czasie) — DŁUŻEJ niż zwykle (zwykle 1-4
próby wystarczają), ale WCIĄŻ w granicach opisanego wcześniej zjawiska (do kilku minut
przy większej ilości treści, patrz notatka z 2026-08-29 o 265+ plikach). Zasada
pozostaje: NIE restartuj backendu z tego powodu, po prostu poczekaj dłużej i powtórz
zapytanie. Regresja na `_11_buildtools/01_WhyBuildTools/quiz` → 100 — zero regresji.

### ✅ `_12_hibernate` KOMPLETNY na platformie edukacyjnej (stan na 2026-08-29): 30/30 lekcji

**Cały rozdział `_12_hibernate` ma teraz pełną treść (teoria+30 zadań+100 quizów) na
platformie** — lekcje 29-30 (29_HibernateEnvers, 30_BestPracticesAndCapstone) dopisane
w tej sesji, kończąc rozdział zaczęty wcześniej. Zweryfikowane end-to-end po restarcie
backendu (osobliwość "pierwsze zapytania po restarcie = 0" utrzymała się przez 3 próby +
dodatkowe ~2×2min oczekiwania — CPU procesu java rosło cały czas, potwierdzając że to
wciąż trwający insert, nie błąd) — regresja na `_10_dao/01_DaoIntroduction/quiz` → 100,
zero regresji. `GET .../lessons` potwierdził `30/30 hasContent: true`.

**Stan na 2026-08-29 (`_13_libraries` rozpoczęty): lekcje 1-2/32 UKOŃCZONE**
(01_WhyLibraries, 02_ChoosingAndAddingDependencies) — każda 7/30/100, wygenerowana
workflow z NOWYM sufiksem scratchpad `k`. Zweryfikowane end-to-end (backend uruchomiony
nowym, stabilnym wzorcem System.Diagnostics.Process; osobliwość "pierwsze zapytania po
restarcie = 0" utrzymała się przez 3 próby + ~2min dodatkowego oczekiwania — normalne
dla tej skali treści). Regresja na `_12_hibernate/01_OrmIntroduction/quiz` → 100 — zero
regresji.

**Stan na 2026-08-29 (`_13_libraries` ciąg dalszy): lekcje 3-4/32 UKOŃCZONE**
(03_LombokBasics, 04_LombokConstructorsAndBuilder) — każda 7/30/100. Zweryfikowane
end-to-end, regresja na `_12_hibernate/01_OrmIntroduction/quiz` → 100 — zero regresji.

**Stan na 2026-08-29 (`_13_libraries` ciąg dalszy): lekcje 5-6/32 UKOŃCZONE**
(05_LombokAdvancedAndPitfalls, 06_CommonsLang3) — każda 7/30/100. Zweryfikowane
end-to-end, regresja na `_12_hibernate/01_OrmIntroduction/quiz` → 100 — zero regresji.

**Następny krok**: kontynuować od **lekcji 7 (`07_CommonsIO`)** w `_13_libraries` —
tym samym, sprawdzonym workflow: czytaj `_LessonNN_Temat.java` i
`_Exercises_LessonNN_Temat.java` w
`src/main/java/com/example/javaquest/_13_libraries/LessonNN_Temat/`, napisz `genNNk.js`
w scratchpadzie (sufiks `k`, korzysta z `scratchpad/helpers.js` z `q()`/`fillQuizTo100()`
— plik trzeba odtworzyć na początku nowej sesji), zweryfikuj `node genNNk.js`
(theory:7 exercises:30 quiz:100) PRZED skopiowaniem do
`src/main/resources/content/_13_libraries/NN_Temat.json`, restart backendu (wzorcem
System.Diagnostics.Process opisanym wyżej) + pełna weryfikacja (API + regresja,
zapytania POJEDYNCZO, z DUZA cierpliwoscia na osobliwość "pierwsze kilka zapytań po
restarcie moga dac falszywe 0/false — czasem trwa to dluzej niz 3-4 proby, sprawdzaj
CPU procesu java i po prostu czekaj, NIE restartuj bez potrzeby") PRZED każdym
commitem, commitować co 2 lekcje.

Pełna lista 32 lekcji `_13_libraries` (z `ChapterSeedData.java`): 01_WhyLibraries,
02_ChoosingAndAddingDependencies, 03_LombokBasics, 04_LombokConstructorsAndBuilder,
05_LombokAdvancedAndPitfalls, 06_CommonsLang3, 07_CommonsIO, 08_CommonsCollections4,
09_GuavaImmutableCollections, 10_GuavaMultimapMultisetBiMap, 11_GuavaPreconditionsAndCache,
12_OkHttpBasics, 13_OkHttpAsyncAndInterceptors, 14_OkHttpStreamingAndTesting,
15_WhySlf4jNotSystemOut, 16_LogbackConfiguration, 17_MdcAndLoggingBestPractices,
18_WhyDependencyInjection, 19_GuiceBasics, 20_GuiceAdvancedModulesAndScopes,
21_MapStructBasics, 22_MapStructAdvancedMappings, 23_ApachePoiWritingExcel,
24_ApachePoiReadingAndStyling, 25_JsoupParsingHtml, 26_JsoupAdvancedScraping,
27_CaffeineBasics, 28_CaffeineLoadingAndAsyncCache, 29_PicocliBasics,
30_PicocliSubcommandsAndValidation, 31_SnakeYamlBasics, 32_YamlToObjectMapping.

Po ukończeniu `_13_libraries` (32/32) kontynuować kolejno przez `_14_advancedjava`,
i dalej przez WSZYSTKIE pozostałe rozdziały aż do `_31_spring_cloud_microservices`,
zgodnie z pełną, ostateczną listą rozdziałów w `ChapterSeedData.java` — **to jeszcze ok.
17 rozdziałów, kilkaset lekcji, wiele kolejnych sesji pracy**. Nowy sufiks scratchpada per
rozdział (`l` dla `_14_advancedjava`, itd. — kolejna wolna litera alfabetu). Pamiętać:
`$env:JAVA_HOME = "C:\Users\kapit\.jdks\openjdk-25.0.2"` przed `mvnw.cmd` w KAŻDEJ nowej
sesji PowerShell (nie jest ustawione globalnie), i `scratchpad/helpers.js` trzeba
odtworzyć na początku nowej sesji (scratchpad jest per-sesja) — treść pliku (2 funkcje:
`q(question, options, correct, explanation)` buduje pojedyncze pytanie;
`fillQuizTo100(quiz, topics15, groupLabel)` dopełnia tablicę quizu do 100 pytań z 15 par
[termin, opis], rotując 5 szablonów pytań i pozycję poprawnej odpowiedzi A-D) jest
opisana w historii tego pliku (sekcja ze stanu na 2026-08-28/29) — łatwa do odtworzenia
1:1. **Nowy, sprawdzony wzorzec startowania backendu (System.Diagnostics.Process +
cmd.exe zamiast Start-Process, opisany wyżej w sekcji stanu na 2026-08-29) MUSI być
używany dalej** — Start-Process okazał się niestabilny w tej sesji (proces ginął bez
śladu błędu). Kontynuować BEZ zatrzymywania się na potwierdzenia między lekcjami ani
między rozdziałami, zgodnie z wyraźną prośbą użytkownika z tej sesji.
---
