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

**Stan na 2026-08-10**: Faza 0 zakończona i zweryfikowana (patrz sekcja 3).
Ten plik właśnie utworzony, jeszcze nie zacommitowany.

**Następny krok**: rozpocząć Fazę 1 — encje `Chapter`/`Lesson`, loader z
`_TableOfContents.java`, REST API, frontend z routingiem i pełną (choć pustą
treściowo) nawigacją po wszystkich 31 rozdziałach.
