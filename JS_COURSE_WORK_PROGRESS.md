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

## DYREKTYWA UŻYTKOWNIKA (2026-09-16, druga sesja): 30 ćwiczeń / 100 quiz na lekcję, praca dwufazowa

Użytkownik skorygował wcześniejszy zapis (8-15 ćwiczeń/8-12 quiz) — **KAŻDA lekcja MA MIEĆ
docelowo 30 ćwiczeń i 100 pytań quizowych**, tak jak wczesne rozdziały Javy `_01`-`_16`. Teoria ma
być "dojechana w kosmos merytorycznie" — pełny, wieloakapitowy, klasyczny styl prozy (BEZ
nadużywania WIELKICH LITER — sprawdzone na `_01_fundamentals/01_Variables.json` jako wzorzec,
NIE styl "capsy wszędzie" użyty wcześniej w lekkich rozdziałach Javy `_35`-`_41`).

Ustalono PRACĘ DWUFAZOWĄ (pełny opis w `JS_COURSE_STAGE_PROMPT.md`, sekcja "Ćwiczenia — format i
liczba"): Faza 1 = kompletna teoria + tyle ćwiczeń/quizu, ile da się napisać bez spadku jakości w
danej turze. Faza 2 (PRZYSZŁE sesje) = dopisywanie kolejnych, NIEPOWTARZALNYCH ćwiczeń/pytań aż do
30/100.

## `_js_01_zmienne` (6/6 lekcji) — Faza 1 UKOŃCZONA w tej sesji, Faza 2 (uzupełnianie do 30/100) NIE rozpoczęta

Wszystkie 6 lekcji napisane od zera, pełna teoria (14 bloków każda, standard 11 sekcji, BEZ
`API_REFERENCE` — cały rozdział to czysta mechanika języka, nie "narzędzie z wieloma metodami").
Źródło: `dodatkowe materiały/kurs js/js-course/01-zmienne/` (`lekcja.js`+`cwiczenia.js`+
`dodatkowe.js`), rozwinięte do pełnego standardu + własne, dodatkowe przykłady/ćwiczenia/quiz.

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_VarKeyword` | 19/30 | 20/100 |
| `02_Hoisting` | 18/30 | 18/100 |
| `03_LetKeyword` | 17/30 | 17/100 |
| `04_ConstKeyword` | 17/30 | 15/100 |
| `05_VariableScopes` | 14/30 | 13/100 |
| `06_VarLetConstWhenToUse` | 13/30 | 11/100 |

Wszystkie 6 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu: wszystkie 6 lekcji zwraca poprawną liczbę bloków teorii (14 każda). Regresja:
`GET /api/chapters?track=JAVA` → 41 rozdziałów (bez zmian), `GET /api/chapters?track=JAVASCRIPT`
→ 14 rozdziałów (bez zmian), `_01_fundamentals/06_StringsAndBuilder` (15 bloków) i
`_40_rest_assured_testing/01_WhyRestAssured` (14 bloków) — oba bez zmian. Backend zatrzymany po
weryfikacji. Scommitowane w tej samej sesji.

## `_js_02_typy_danych` (7/7 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

Wszystkie 7 lekcji napisane: String (trzy sposoby zapisu + metody), Number/Math/parseInt,
BigInt, Boolean/truthy-falsy/null/undefined, Symbol, typy wartościowe vs referencyjne. Lekcje
`02_StringMethods` i `03_NumberAndArithmetic` mają pełny `API_REFERENCE` (15 bloków — katalogi
metod/funkcji z wieloma wariantami), pozostałe 5 lekcji BEZ `API_REFERENCE` (14 bloków —
koncepcje/mechanizmy, nie katalogi metod).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_StringBasics` | 12/30 | 8/100 |
| `02_StringMethods` | 20/30 | 8/100 |
| `03_NumberAndArithmetic` | 17/30 | 8/100 |
| `04_BigInt` | 8/30 | 6/100 |
| `05_BooleanUndefinedNull` | 10/30 | 8/100 |
| `06_Symbol` | 7/30 | 6/100 |
| `07_ValueVsReferenceTypes` | 9/30 | 8/100 |

Wszystkie 7 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu: wszystkie 7 lekcji zwraca poprawną liczbę bloków teorii (14/15/15/14/14/14/14).
Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT`
→ 14 (bez zmian), `_js_01_zmienne/01_VarKeyword` → 14 bloków (bez zmian). Backend zatrzymany po
weryfikacji. Scommitowane w tej samej sesji.

## `_js_03_funkcje` (5/5 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

5 lekcji: function declaration vs expression (+ hoisting), arrow functions, funkcje anonimowe +
IIFE, callbacki/predykaty/funkcje wyższego rzędu, closures (domknięcia — liczniki, prywatny
stan, memoizacja). Wszystkie BEZ `API_REFERENCE` (14 bloków każda — czyste mechanizmy językowe,
nie katalogi metod).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_FunctionDeclarationVsExpression` | 8/30 | 7/100 |
| `02_ArrowFunctions` | 12/30 | 7/100 |
| `03_AnonymousFunctions` | 8/30 | 7/100 |
| `04_CallbackAndPredicateFunctions` | 11/30 | 7/100 |
| `05_Closures` | 12/30 | 7/100 |

Wszystkie 5 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu: wszystkie 5 lekcji zwraca 14 bloków teorii. Regresja: `GET
/api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian),
`_js_01_zmienne/01_VarKeyword` i `_js_02_typy_danych/01_StringBasics` → 14 bloków każda (bez
zmian). Backend zatrzymany po weryfikacji. Scommitowane w tej samej sesji.

## `_js_04_tablice` (8/8 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

8 lekcji: podstawy tablic + metody mutujące (push/pop/shift/unshift/splice/reverse + szukanie),
filter(), map(), forEach(), sort() szczegółowo (comparator, pułapka leksykograficzna), flat()/
flatMap(), destrukturyzacja tablicowa, spread operator. Lekcja 1 ma pełny `API_REFERENCE` (15
bloków, katalog wielu metod tablicowych), pozostałe 7 BEZ (14 bloków — każda skupiona na JEDNYM
mechanizmie/metodzie w głębi, nie katalogu).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_ArrayBasicsAndMutatingMethods` | 24/30 | 8/100 |
| `02_FilterMethod` | 9/30 | 7/100 |
| `03_MapMethod` | 9/30 | 6/100 |
| `04_ForEachMethod` | 7/30 | 6/100 |
| `05_SortMethodInDepth` | 8/30 | 7/100 |
| `06_FlatMapAndFlat` | 7/30 | 6/100 |
| `07_ArrayDestructuring` | 9/30 | 7/100 |
| `08_ArraySpreadOperator` | 9/30 | 7/100 |

Wszystkie 8 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu: wszystkie 8 lekcji zwraca poprawną liczbę bloków teorii (15/14/14/14/14/14/
14/14). Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), pierwsze lekcje rozdziałów 1-3 → 14 bloków
każda (bez zmian). Backend zatrzymany po weryfikacji. Scommitowane w tej samej sesji.

## Następny krok

**Kontynuacja Fazy 1 przez kolejne rozdziały** (`_js_05_obiekty` jako następny w naturalnej
kolejności 01→14) — zgodnie z ustaloną strategią: najpierw przejechać Fazą 1 przez wszystkie 14
rozdziałów (pełna teoria + solidny start ćwiczeń/quizu w każdej lekcji), a DOPIERO POTEM (Faza 2,
osobna tura pracy) wracać i uzupełniać każdą lekcję do pełnych 30 ćwiczeń / 100 pytań quizowych —
dokładny stan każdej lekcji jest i będzie zapisywany w tabelach w tym pliku, żeby żadna sesja nie
musiała zgadywać, gdzie kontynuować.
