# JS_COURSE_WORK_PROGRESS — Drugi blok platformy JavaQuest (kurs JavaScript)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac nad kursem JavaScript. Nadpisywany,
> nie dziennik. Pełna instrukcja/standard: `JS_COURSE_STAGE_PROMPT.md`. Stan kursu Java (osobna
> inicjatywa, niezależny plik): `WORK_PROGRESS.md`.

## Aktualny etap

**Projektowanie zakończone, scaffold zaimplementowany. Pisanie treści — NIE rozpoczęte.**

Sesja 2026-09-16: użytkownik poprosił o drugi blok platformy — równoległy kurs JavaScript,
zbudowany na bazie gotowego materiału źródłowego w `dodatkowe materiały/kurs js/js-course/`
(osobne repo, poza kontrolą wersji tego projektu). Zakres tej sesji (za wyraźną prośbą
użytkownika): zaprojektować rozdziały/lekcje i zapisać instrukcję startową — NIE pisać jeszcze
treści lekcji/ćwiczeń/quizów (`w następnym kroku będziemy uzupełniać lekcje, ćwiczenia i quizy`).

## Co zrobiono w tej sesji

1. **Analiza materiału źródłowego** — 14 folderów (`01-zmienne`…`14-srodowisko-przegladarkowe`),
   90 znaczników `JS-XXX` łącznie (= 90 przyszłych lekcji), każdy z `lekcja.js`
   (teoria+kod+komentarze), `cwiczenia.js`+`dodatkowe.js` (gotowe prompty ćwiczeń, BEZ
   hint/solution), rozdziały 11-14 dodatkowo z `index.html` (środowisko przeglądarkowe).
2. **Zaprojektowano strukturę** 14 rozdziałów / 90 lekcji — pełna tabela w
   `JS_COURSE_STAGE_PROMPT.md`, sekcja "Docelowa struktura".
3. **Zaimplementowano architekturę dwóch torów kursu** (Java/JavaScript) w kodzie platformy:
   - `CourseTrack` (nowy enum: `JAVA`/`JAVASCRIPT`) w `com.example.javaquest.platform.chapter`.
   - `Chapter` — nowe pole `track` (`@Enumerated(STRING)`), nowy wymagany parametr konstruktora.
   - `ChapterSeedData.ChapterSeed` — DODATKOWY, 4-argumentowy konstruktor z jawnym `CourseTrack`
     (rozdziały Java, 3-argumentowy stary konstruktor, BEZ ZMIAN, domyślnie `JAVA`).
   - `ContentSeeder` — przekazuje `seed.track()` do `Chapter`.
   - `ChapterRepository` — nowa metoda `findAllByTrackOrderBySortOrderAsc(CourseTrack)`.
   - `ChapterController` — `GET /api/chapters` przyjmuje teraz `?track=JAVA|JAVASCRIPT`
     (domyślnie `JAVA` — zachowuje dotychczasowe zachowanie, gdy parametr pominięty).
   - `ChapterSeedData.CHAPTERS` — dopisano 14 rozdziałów kursu JS (`_js_01_zmienne` …
     `_js_14_srodowisko_przegladarkowe`), CAŁKOWICIE PUSTych (bez plików JSON treści) — TA SAMA,
     udokumentowana konwencja "treść w przygotowaniu" co przy scaffoldingu `_35`-`_41` kursu Java.
4. **Zaimplementowano UI przełącznika** w frontendzie:
   - `App.jsx` — usunięto tekst "Platforma edukacyjna oparta na kursie Java.", dodano
     `TrackSwitcher` (dwa linki `Java`/`JavaScript`, trasy `/` i `/js`).
   - `ChapterListPage.jsx` — przyjmuje prop `track`, przekazuje do `getChapters(track)`.
   - `api.js` — `getChapters(track = 'JAVA')` dokłada `?track=...` do zapytania.
   - `LessonListPage.jsx` — link powrotny "Wszystkie rozdziały" rozpoznaje kurs po prefiksie
     sluga rozdziału (`_js_` → `/js`, inaczej `/`).
   - `App.css` — nowe klasy `.track-switcher`/`.track-switcher__button` (styl spójny z istniejącym
     `.app-header__critical-button`).
5. **Napisano `JS_COURSE_STAGE_PROMPT.md`** — jedyne, obowiązujące źródło instrukcji dla PISANIA
   treści tego kursu (standard 11 sekcji, format ćwiczeń/quizu, zasady weryfikacji, jak korzystać
   z materiału źródłowego). `CLAUDE.md` zaktualizowany o wskaźnik do tego pliku i do tego
   dziennika.

## Zweryfikowano

- `mvnw.cmd compile` — bez błędów.
- `npm run build` (frontend) — bez błędów.
- Backend uruchomiony live (port 8082, `JAVA_HOME` ustawiony):
  - `GET /api/chapters` (bez parametru) → 41 rozdziałów — domyślne zachowanie `track=JAVA`
    zachowane, zgodność wsteczna potwierdzona.
  - `GET /api/chapters?track=JAVA` → 41 rozdziałów kursu Java, identyczne jak przed zmianą
    (regresja bez zmian, sprawdzone pierwsze/ostatnie dwa wpisy).
  - `GET /api/chapters?track=JAVASCRIPT` → 14 nowych rozdziałów `_js_01_zmienne`…
    `_js_14_srodowisko_przegladarkowe`, poprawne tytuły, `lessonCount` sumujący się do 90.
  - `GET /api/chapters/_js_01_zmienne/lessons` → 6 lekcji z poprawnymi slugami i
    humanizowanymi tytułami (`01_VarKeyword` → "Var Keyword" itd. — prowizoryczne tytuły
    Fazy 1, jak w kursie Java, do zastąpienia właściwą treścią przy pisaniu lekcji).
  - `GET /api/chapters/_js_01_zmienne/lessons/01_VarKeyword/theory` → `[]` (pusta treść,
    poprawnie renderowana przez istniejący mechanizm "w przygotowaniu" na froncie).
  - Backend zatrzymany po weryfikacji (`Stop-Process`, potwierdzone brak procesów `java`).

## Następny krok

**Pisanie treści lekcji, rozdział po rozdziale, zaczynając od `_js_01_zmienne`** (6 lekcji:
`01_VarKeyword` … `06_VarLetConstWhenToUse`) — dokładnie wg `JS_COURSE_STAGE_PROMPT.md`. Po
każdym ukończonym rozdziale: walidacja JSON, `fix_allcaps.js`+`fix_allcaps_residual.js`,
weryfikacja live przez API (`?track=JAVASCRIPT`), regresja na obu torach, lokalny commit.

Kolejność rozdziałów: naturalna 01→14, chyba że użytkownik zdecyduje inaczej.
