# JS_COURSE_WORK_PROGRESS — Drugi blok platformy JavaQuest (kurs JavaScript)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac nad kursem JavaScript. Nadpisywany,
> nie dziennik. Pełna instrukcja/standard: `JS_COURSE_STAGE_PROMPT.md`. Stan kursu Java (osobna
> inicjatywa, niezależny plik): `WORK_PROGRESS.md`.

## Aktualny etap

**Faza 1 UKOŃCZONA dla wszystkich 14/14 rozdziałów (90/90 lekcji). Faza 2: rozdziały 1-9
ukończone w całości, a w `_js_10_moduly` ukończone lekcje 1-3. Każda ukończona lekcja ma
30 ćwiczeń i 100 pytań quizowych. Aktualny następny krok znajduje się na końcu pliku.**

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

## `_js_05_obiekty` (7/7 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

7 lekcji: podstawy obiektów (tworzenie/dostęp/dynamiczny klucz/shorthand), `Object.*`
(keys/values/entries/fromEntries/assign/freeze), destrukturyzacja obiektowa, optional chaining
`?.` + nullish coalescing `??`, spread operator na obiektach (immutable update), JSON.stringify/
parse, obiekt `Date`. Lekcje 2, 6, 7 z pełnym `API_REFERENCE` (15 bloków — katalogi metod),
pozostałe 4 bez (14 bloków).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_ObjectBasics` | 12/30 | 7/100 |
| `02_ObjectStaticMethods` | 9/30 | 6/100 |
| `03_ObjectDestructuring` | 8/30 | 7/100 |
| `04_OptionalChainingAndNullishCoalescing` | 9/30 | 7/100 |
| `05_ObjectSpreadOperator` | 8/30 | 6/100 |
| `06_JsonStringifyAndParse` | 8/30 | 7/100 |
| `07_DateObject` | 9/30 | 6/100 |

Wszystkie 7 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu: wszystkie 7 lekcji zwraca poprawną liczbę bloków teorii (14/15/14/14/14/15/
15). Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), pierwsze lekcje rozdziałów 1-4 → bez zmian.
Backend zatrzymany po weryfikacji. Scommitowane w tej samej sesji.

## `_js_06_kontrola_przeplywu` (5/5 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

5 lekcji: if/else/ternary (+ guard clause/early return), switch + object-as-map (fallthrough
świadomy vs przypadkowy), trzy warianty pętli `for`/`for...of`/`for...in` (+ `Object.hasOwn()`,
pułapka indeksów-stringów w `for...in` na tablicach), `try/catch/finally` (+ własne klasy błędów
dziedziczące po `Error`), `==` kontra `===` (konwersja typów, `NaN`, `null == undefined`). Wszystkie
5 lekcji BEZ `API_REFERENCE` (14 bloków każda — czysta mechanika sterowania przepływem, nie
katalogi metod).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_IfElseAndTernary` | 10/30 | 6/100 |
| `02_SwitchAndObjectAsMap` | 8/30 | 6/100 |
| `03_ForForOfForIn` | 16/30 | 6/100 |
| `04_TryCatchFinally` | 10/30 | 6/100 |
| `05_LooseVsStrictEquality` | 9/30 | 6/100 |

Wszystkie 5 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Znaleziony i naprawiony błąd składniowy w bloku `CODE_WRONG`
`04_TryCatchFinally.json` (brakujące zamykające klamry funkcji/catch) przed weryfikacją live.
Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie backendu (`JAVA_HOME`
ustawiony ręcznie na `~/.jdks/openjdk-25.0.2` — zmienna nie była ustawiona w środowisku sesji):
wszystkie 5 lekcji zwraca poprawną liczbę bloków teorii/ćwiczeń/quizu (14/10/6, 14/8/6, 14/16/6,
14/10/6, 14/9/6). Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), pierwsze lekcje rozdziałów 1-5 → bez zmian.
Backend zatrzymany po weryfikacji (potwierdzony brak procesów `java`).

## `_js_07_this_i_konteksty` (7/7 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

7 lekcji ("Część 2, tematy dopisane pod React"): podstawy `this` (zależność od sposobu wywołania,
nie definicji), gubienie kontekstu (przypisanie do zmiennej/przekazanie jako argument/
destrukturyzacja), arrow function i this leksykalne, `call()`, `apply()`, `bind()` (+ partial
application), this w klasach i event handlerach (bind w konstruktorze vs arrow function jako pole
klasy). Wszystkie 7 lekcji BEZ `API_REFERENCE` (14 bloków każda — mechanika języka, nie katalogi
metod).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_ThisBasics` | 9/30 | 6/100 |
| `02_ThisLosingContext` | 8/30 | 6/100 |
| `03_ArrowFunctionsAndThis` | 8/30 | 6/100 |
| `04_CallMethod` | 7/30 | 6/100 |
| `05_ApplyMethod` | 7/30 | 6/100 |
| `06_BindMethod` | 8/30 | 6/100 |
| `07_ThisInClassesAndEventHandlers` | 8/30 | 6/100 |

Wszystkie 7 plików zweryfikowane: poprawny JSON, brak cyrylicy, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Znaleziony i naprawiony błąd składniowy w bloku `CODE_WRONG` `03_ArrowFunctionsAndThis.json`
(brakujące zamykające klamry obiektu/funkcji) przed weryfikacją live. Zweryfikowane live przez API
po `mvnw.cmd resources:resources` + restarcie backendu: wszystkie 7 lekcji zwraca poprawną liczbę
bloków teorii/ćwiczeń/quizu (14/9/6, 14/8/6, 14/8/6, 14/7/6, 14/7/6, 14/8/6, 14/8/6). Regresja: `GET
/api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian),
pierwsze lekcje rozdziałów 1-6 → bez zmian. Backend zatrzymany po weryfikacji (potwierdzony brak
procesów `java`).

## `_js_08_klasy` (7/7 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

7 lekcji: konstruktor i metody instancji (`class`/`new`, niezależny stan instancji), pola klasy
(wartości domyślne, arrow function jako pole — automatyczne wiązanie `this`), gettery/settery
(`get`/`set`, konwencja `_pole` żeby uniknąć nieskończonego cyklu), pola i metody statyczne
(`static`, `NazwaKlasy.pole`, licznik instancji), dziedziczenie (`extends`/`super`, nadpisywanie
metod, `instanceof` w łańcuchu dziedziczenia), prywatne pola/metody (`#pole`, prawdziwa
enkapsulacja vs konwencja `_pole`), class kontra funkcja fabrykująca (closure jako alternatywa dla
`#`, kiedy wybrać które podejście). Wszystkie 7 lekcji BEZ `API_REFERENCE` (14 bloków każda —
mechanika języka, nie katalogi metod).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_ClassConstructorAndMethods` | 8/30 | 6/100 |
| `02_ClassFields` | 8/30 | 6/100 |
| `03_GettersAndSetters` | 8/30 | 6/100 |
| `04_StaticFieldsAndMethods` | 8/30 | 6/100 |
| `05_InheritanceExtendsSuper` | 8/30 | 6/100 |
| `06_PrivateFieldsAndMethods` | 8/30 | 6/100 |
| `07_ClassVsFactoryFunction` | 8/30 | 6/100 |

Wszystkie 7 plików zweryfikowane: poprawny JSON, brak cyrylicy, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie backendu:
wszystkie 7 lekcji zwraca poprawną liczbę bloków teorii/ćwiczeń/quizu (14/8/6 dla każdej z 7
lekcji). Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), pierwsze lekcje rozdziałów 1-7 → bez zmian.
Backend zatrzymany po weryfikacji (potwierdzony brak procesów `java`).

## `_js_09_kolekcje` (6/6 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

6 lekcji: `Set` (unikalne wartości, `.add()`/`.has()`/`.delete()`/`.size`, deduplikacja tablicy),
`Map` (klucz-wartość z kluczem dowolnego typu, w tym obiekty), iteracja po `Map` (`for...of`,
`.forEach()` — UWAGA na kolejność argumentów `(wartość, klucz)` — oraz `.keys()`/`.values()`/
`.entries()`), konwersje `Map ↔ Object` (`Object.entries()`/`Object.fromEntries()`) i `Set ↔ Array`
(`Array.from()`), kiedy wybrać `Map`/`Set` zamiast `Object`/`Array` (+ wzorzec liczenia wystąpień),
`WeakMap`/`WeakSet` (słabe referencje, brak iterowalności, zapobieganie wyciekom pamięci). Lekcje
1-3 (`Set`, `Map`, iteracja po `Map`) mają pełny `API_REFERENCE` (15 bloków — katalogi metod),
lekcje 4-6 BEZ (14 bloków — konwersje/zasady wyboru/WeakMap są bardziej koncepcyjne).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_SetUniqueValues` | 8/30 | 6/100 |
| `02_MapKeyValueWithAnyKeyType` | 8/30 | 6/100 |
| `03_IteratingOverMap` | 8/30 | 6/100 |
| `04_MapObjectSetArrayConversions` | 8/30 | 6/100 |
| `05_WhenToUseMapSetVsObjectArray` | 8/30 | 6/100 |
| `06_WeakMapAndWeakSet` | 7/30 | 6/100 |

Wszystkie 6 plików zweryfikowane: poprawny JSON, brak cyrylicy, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Znalezione i naprawione DWA błędy w `03_IteratingOverMap.json` PRZED weryfikacją live:
błąd składniowy JSON w jednej z opcji quizu (brakujący dwukropek po kluczu `"D"`) oraz brakujące
zamykające klamry w bloku `CODE_WRONG`. Zweryfikowane live przez API po `mvnw.cmd
resources:resources` + restarcie backendu: wszystkie 6 lekcji zwraca poprawną liczbę bloków
teorii/ćwiczeń/quizu (15/8/6, 15/8/6, 15/8/6, 14/8/6, 14/8/6, 14/7/6). Regresja: `GET
/api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian),
pierwsze lekcje rozdziałów 1-8 → bez zmian. Backend zatrzymany po weryfikacji (potwierdzony brak
procesów `java`).

## `_js_10_moduly` (7/7 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

7 lekcji: czym są moduły ES i dlaczego React ich używa, import nazwany (`{ nazwa }`, alias `as`),
import domyślny (`export default`, dowolna nazwa po stronie importu), łączenie obu w jednej linii
(`import domyslny, { nazwane }`), namespace import (`import * as`, eksport domyślny pod kluczem
`.default`), import dynamiczny (`import()` jako `Promise`, lazy loading, analogia do
`React.lazy()`), oraz zamykająca lekcja z pełną analogią modułów ES do organizacji komponentów
React. Wszystkie 7 lekcji BEZ `API_REFERENCE` (14 bloków każda — składnia języka, nie katalog
metod).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_WhatAreEsModules` | 7/30 | 6/100 |
| `02_NamedImport` | 8/30 | 6/100 |
| `03_DefaultImport` | 7/30 | 6/100 |
| `04_CombiningNamedAndDefaultImport` | 7/30 | 6/100 |
| `05_NamespaceImport` | 7/30 | 6/100 |
| `06_DynamicImport` | 7/30 | 6/100 |
| `07_ModulesAndReactAnalogy` | 7/30 | 6/100 |

Wszystkie 7 plików zweryfikowane: poprawny JSON, brak cyrylicy, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Znalezione i naprawione DWA błędy PRZED weryfikacją live: (1) omyłkowy zapis placeholder
zamiast treści w `07_ModulesAndReactAnalogy.json` (natychmiast nadpisany poprawną zawartością),
(2) brakujące zamykające klamry w bloku `CODE_WRONG` `06_DynamicImport.json`. Zweryfikowane live
przez API po `mvnw.cmd resources:resources` + restarcie backendu (znaleziono i zamknięto dwa
osierocone procesy `java` z poprzedniej sesji przed startem nowego backendu): wszystkie 7 lekcji
zwraca poprawną liczbę bloków teorii/ćwiczeń/quizu (14/7/6, 14/8/6, 14/7/6, 14/7/6, 14/7/6, 14/7/6,
14/7/6). Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), pierwsze lekcje rozdziałów 1-9 → bez zmian.
Backend zatrzymany po weryfikacji (potwierdzony brak procesów `java`).

## `_js_11_dom` (7/7 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

7 lekcji ("Część 3, środowisko przeglądarkowe"): czym jest DOM (drzewiasta reprezentacja HTML,
`document` jako korzeń), selekcja elementów (`getElementById`/`querySelector`/`querySelectorAll`,
nawigacja `.children`/`.parentElement`/`.nextElementSibling`), manipulacja treścią
(`textContent`/`innerHTML`/`value`, ryzyko XSS), tworzenie/usuwanie elementów
(`createElement`/`appendChild`/`insertAdjacentHTML`/`remove`/`cloneNode`), atrybuty i data-atrybuty
(`getAttribute`/`setAttribute`/`dataset`, wartości zawsze jako string), `classList`
(`add`/`remove`/`toggle`/`contains`), zmiana stylów CSS przez JS (`element.style`,
`getComputedStyle`, CSS Custom Properties, `requestAnimationFrame`). Lekcje 2 (selekcja) i 6
(classList) mają pełny `API_REFERENCE` (15 bloków — katalogi metod), pozostałe 5 BEZ (14 bloków).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_WhatIsTheDom` | 7/30 | 6/100 |
| `02_SelectingDomElements` | 8/30 | 6/100 |
| `03_ManipulatingElementContent` | 7/30 | 6/100 |
| `04_CreatingAndRemovingElements` | 8/30 | 6/100 |
| `05_AttributesAndDataAttributes` | 8/30 | 6/100 |
| `06_ClassListDynamicClasses` | 8/30 | 6/100 |
| `07_ChangingCssStylesViaJs` | 8/30 | 6/100 |

Wszystkie 7 plików zweryfikowane: poprawny JSON, brak cyrylicy, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie backendu
(znaleziono i zamknięto kolejne dwa osierocone procesy `java` sprzed restartu — WZORZEC
POWTARZAJĄCY SIĘ W TEJ SESJI, warto zawsze sprawdzać `Get-Process java` PRZED każdym startem):
wszystkie 7 lekcji zwraca poprawną liczbę bloków teorii/ćwiczeń/quizu (14/7/6, 15/8/6, 14/7/6,
14/8/6, 14/8/6, 15/8/6, 14/8/6). Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), pierwsze lekcje rozdziałów 1-10 → bez zmian.
Backend zatrzymany po weryfikacji (potwierdzony brak procesów `java`).

## `_js_12_zdarzenia` (6/6 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

6 lekcji: `addEventListener`/`removeEventListener` (+ opcja `once`, pułapka anonimowej arrow
function przy usuwaniu handlera), obiekt zdarzenia (`target`/`preventDefault`/`stopPropagation`),
zdarzenia myszy, zdarzenia klawiatury i formularza, propagacja i delegacja zdarzeń, wyrażenia
regularne. Wszystkie 6 lekcji BEZ `API_REFERENCE` (14 bloków każda).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_AddEventListener` | 8/30 | 6/100 |
| `02_EventObjectTargetPreventDefaultStopPropagation` | 7/30 | 6/100 |
| `03_MouseEvents` | 8/30 | 6/100 |
| `04_KeyboardAndFormEvents` | 8/30 | 6/100 |
| `05_EventPropagationAndDelegation` | 7/30 | 6/100 |
| `06_RegularExpressions` | 8/30 | 6/100 |

Wszystkie 6 plików zweryfikowane: poprawny JSON, brak "native code", brak cyrylicy,
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` +
restarcie backendu: wszystkie 6 lekcji zwraca 14 bloków teorii. Regresja: `GET
/api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian),
`_js_11_dom/01_WhatIsTheDom` bez zmian. Backend zatrzymany po weryfikacji. Scommitowane w tej
samej sesji.

## `_js_13_asynchronicznosc` (6/6 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji

6 lekcji: `setTimeout`/`setInterval` (+ `clearTimeout`/`clearInterval`), `Promise` (`then`/
`catch`/`finally`/`all`), `async`/`await` (+ sekwencyjny `await` kontra `Promise.all`), `fetch()`
(`response.ok`/`response.status`, rozróżnienie `TypeError` sieciowego od błędu HTTP), `FormData` +
wysyłanie danych (JSON kontra `multipart/form-data`, submit formularza), wzorce REST API
(konwencje `GET`/`POST`/`PUT`/`PATCH`/`DELETE`, klient API wielokrotnego użytku przez funkcję
fabrykującą). Wszystkie 6 lekcji BEZ `API_REFERENCE` (14 bloków każda).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_SetTimeoutAndSetInterval` | 8/30 | 6/100 |
| `02_Promises` | 8/30 | 6/100 |
| `03_AsyncAwait` | 8/30 | 6/100 |
| `04_FetchApi` | 8/30 | 6/100 |
| `05_FormDataAndSendingData` | 8/30 | 6/100 |
| `06_RestApiPatterns` | 8/30 | 6/100 |

Wszystkie 6 plików zweryfikowane: poprawny JSON, brak cyrylicy, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` ORAZ `fix_http_method_case.js` (jedyny rozdział
dotyczący HTTP) — po uruchomieniu tego drugiego skryptu znalezione i ręcznie naprawione DWA
fałszywe trafienia (`formData.get(...)`/`api.get(...)`/`post.title` omyłkowo zamienione na
`.GET(...)`/`POST.title` w `05_FormDataAndSendingData.json` i `06_RestApiPatterns.json` — DOKŁADNIE
ten typ pułapki, przed którym ostrzega `JS_COURSE_STAGE_PROMPT.md`), poprawione ręcznie i
zweryfikowane ponownym `grep -nE '\.(GET|POST|PUT|PATCH|DELETE)\('` → brak wyników. Zweryfikowane
live przez API: wszystkie 6 lekcji zwraca 14/8/6 (teoria/ćwiczenia/quiz). Regresja: oba tory bez
zmian, `_01_fundamentals/06_StringsAndBuilder` (15 bloków) bez zmian. Backend zatrzymany po
weryfikacji. Scommitowane w tej samej sesji.

## `_js_14_srodowisko_przegladarkowe` (6/6 lekcji) — Faza 1 UKOŃCZONA w tej samej sesji, OSTATNI rozdział kursu

6 lekcji, ZAMYKAJĄCE CAŁY kurs JavaScript: obiekt `window` (wymiary, scroll, zdarzenia `resize`/
`load`/`DOMContentLoaded`/`beforeunload`), `localStorage`/`sessionStorage` (+ bezpieczne funkcje
pomocnicze `storageGet`/`storageSet`, zdarzenie `storage` do synchronizacji między kartami),
`window.location` (+ `URLSearchParams`, konstruktor `URL`, `location.href`/`assign`/`replace`),
`window.history`/History API (`pushState`/`replaceState`/`popstate` — nawigacja bez przeładowania,
wzorzec pod React Router), standardy ECMAScript (historia ES5→ES2024, `caniuse.com`, wzorzec
sprawdzania dostępności metody z fallbackiem), debugowanie (breakpointy, `debugger;`, skróty VS
Code F5/F9/F10/F11/Shift+F11, `console.table`/`console.time`). Wszystkie 6 lekcji BEZ
`API_REFERENCE` (14 bloków każda).

Aktualny stan ćwiczeń/quizu (Faza 1, cel docelowy: 30/100 na lekcję):

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_WindowObject` | 7/30 | 6/100 |
| `02_LocalStorageAndSessionStorage` | 8/30 | 6/100 |
| `03_WindowLocation` | 8/30 | 6/100 |
| `04_WindowHistoryAndHistoryApi` | 8/30 | 6/100 |
| `05_EcmaScriptStandards` | 8/30 | 6/100 |
| `06_DebuggingInVsCode` | 8/30 | 6/100 |

Wszystkie 6 plików zweryfikowane: poprawny JSON, brak "native code", brak cyrylicy, przepuszczone
przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie
dotyczy HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie
backendu: wszystkie 6 lekcji zwraca 14/7-8/6 (teoria/ćwiczenia/quiz) — dokładnie zgodnie z
tabelą powyżej. Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET
/api/chapters?track=JAVASCRIPT` → 14 (bez zmian), `_js_13_asynchronicznosc/06_RestApiPatterns`
(14 bloków) i `_01_fundamentals/06_StringsAndBuilder` (15 bloków) — oba bez zmian. Backend
zatrzymany po weryfikacji (potwierdzony brak procesów `java`). Scommitowane w tej samej sesji —
TO ZAMYKA FAZĘ 1 CAŁEGO kursu JavaScript (14/14 rozdziałów, 90/90 lekcji).

## DYREKTYWA UŻYTKOWNIKA (2026-09-17, trzecia sesja): Faza 2 w toku, więcej pytań z kodem w quizie

Użytkownik potwierdził cel 30 ćwiczeń / 100 quiz na lekcję i poprosił, żeby quiz w dużej części
składał się z pytań pokazujących fragment kodu i pytających "co wypisze poniższy kod?" (zamiast
wyłącznie pytań czysto pojęciowych, których przy 100 pytaniach na wąski temat szybko zaczyna
brakować). Ustalony format: `question` zawiera `"Co wypisze poniższy kod?\n<fragment kodu z \\n>"`
— TA SAMA konwencja co w kursie Java (sprawdzone: 480 istniejących wystąpień w `_01_fundamentals`
i innych rozdziałach Javy) — quiz-view renderuje to jako zwykły akapit (bez zachowania łamania
linii wizualnie, ale to zaakceptowana, już wcześniej stosowana konwencja w kursie Java, więc
zachowana też tutaj dla spójności). Dodatkowo: powiększono pole na rozwiązanie ćwiczenia w UI
(`frontend/src/components/ExercisesView.jsx`, `rows={8}` → `rows={10}`, ok. +25-30%) — scommitowane
osobno w tej sesji.

**WAŻNA UWAGA JAKOŚCIOWA**: przy pisaniu quizu z kodem WERYFIKUJ RĘCZNIE poprawność odpowiedzi
(nie tylko poprawność JSON) — w tej sesji znaleziono i naprawiono DWA przypadki, w których
wygenerowana odpowiedź "correct" była błędna (hoisting: `var wynik=1; function wynik(){}; wynik=
wynik+1;` — poprawna odpowiedź to 2, nie NaN; oraz analogiczny przypadek w `04_ConstKeyword.json`
z parametrem zacieniającym `const` — poprawna odpowiedź to 10, nie 20). Zawsze prześledź kod
krok po kroku samemu przed zapisaniem `correct`, szczególnie przy pytaniach dotykających
hoistingu/shadowingu, gdzie intuicja często myli kolejność wykonania z kolejnością zapisu w kodzie.

## `_js_01_zmienne` (6/6 lekcji) — Faza 2 UKOŃCZONA w tej sesji (30/100 KAŻDA lekcja)

Wszystkie 6 lekcji rozdziału 1 uzupełnione od stanu Fazy 1 (patrz tabela historyczna niżej) do
pełnych **30 ćwiczeń / 100 pytań quizowych** (01_VarKeyword ma 102 quiz — niewielka nadwyżka,
zaakceptowana). Większość nowych pytań quizowych to pytania z kodem (`"Co wypisze poniższy kod?"`)
— sprawdzają hoisting, zasięg blokowy vs funkcyjny, shadowing, mutację obiektów/tablic przy
`const`, referencje vs wartości, domknięcia (wprowadzenie, pełne wyjaśnienie w rozdziale 3).
Pozostała mniejszość to pytania decyzyjne ("które słowo kluczowe pasuje do tego scenariusza") —
szczególnie w `06_VarLetConstWhenToUse`.

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_VarKeyword` | 30/30 | 102/100 |
| `02_Hoisting` | 30/30 | 100/100 |
| `03_LetKeyword` | 30/30 | 100/100 |
| `04_ConstKeyword` | 30/30 | 100/100 |
| `05_VariableScopes` | 30/30 | 100/100 |
| `06_VarLetConstWhenToUse` | 30/30 | 100/100 |

Wszystkie 6 plików zweryfikowane: poprawny JSON, brak "native code", brak cyrylicy, struktura
quizu (4 opcje A-D, `correct` w A-D, `explanation` obecne) i ćwiczeń (prompt/hint/solution obecne)
sprawdzone programowo, przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js`. [Weryfikacja
live przez API — patrz niżej / do uzupełnienia po zakończeniu tej sesji]. Rozdział CAŁKOWICIE
GOTOWY (Faza 1 + Faza 2) — jedyny w calym kursie JS na ten moment osiągający pełny target 30/100.

## `_js_02_typy_danych` (7/7 lekcji) — Faza 2 UKOŃCZONA w tej sesji

Wszystkie 7 lekcji rozdziału 2 uzupełnione do pełnych **30 ćwiczeń / 100 pytań quizowych**:
`01_StringBasics`, `02_StringMethods`, `03_NumberAndArithmetic`, `04_BigInt`,
`05_BooleanUndefinedNull`, `06_Symbol`, `07_ValueVsReferenceTypes` — WSZYSTKIE 30/100.
Zweryfikowane live przez API (theory/exercises/quiz dla każdej), regresja czysta na obu torach.
Rozdział 2 CAŁKOWICIE GOTOWY (Faza 1 + Faza 2), drugi (po `_js_01_zmienne`) w pełni ukończony
rozdział kursu JS.

## `_js_03_funkcje` (5/5 lekcji) — Faza 2 UKOŃCZONA w tej sesji (30/100 KAŻDA lekcja)

Wszystkie 5 lekcji rozdziału 3 uzupełnione od stanu Fazy 1 do pełnych **30 ćwiczeń / 100 pytań
quizowych** — TRZECI w pełni ukończony rozdział kursu JS (po `_js_01`, `_js_02`). Sesja
rozpoczęta komendą użytkownika "kontynuuj prace wychodze z domu nie pytaj o zgode" — kontynuacja
Fazy 2 zgodnie z ustaloną w poprzedniej sesji dyrektywą (bez pytania o zgodę między lekcjami/
rozdziałami). Zdecydowana większość nowych pytań quizowych to pytania z kodem
(`"Co wypisze poniższy kod?"`), zgodnie z dyrektywą z 2026-09-17.

| Lekcja | Ćwiczenia | Quiz |
|---|---|---|
| `01_FunctionDeclarationVsExpression` | 30/30 | 100/100 |
| `02_ArrowFunctions` | 30/30 | 100/100 |
| `03_AnonymousFunctions` | 30/30 | 100/100 |
| `04_CallbackAndPredicateFunctions` | 30/30 | 100/100 |
| `05_Closures` | 30/30 | 100/100 |

**Znaleziony i naprawiony błąd merytoryczny z Fazy 1 przy okazji tej sesji:**
`01_FunctionDeclarationVsExpression.json` (blok `CODE_WRONG`, jedno `exercise` i jedno `quiz`)
błędnie twierdził, że wywołanie function expression zadeklarowanej przez `const` PRZED linią jej
przypisania rzuca `TypeError` ("policzRabat is not a function") — w rzeczywistości `const`/`let` są
w Temporal Dead Zone do momentu wykonania linii deklaracji, więc taka próba rzuca
`ReferenceError: Cannot access '...' before initialization`. `TypeError` pojawia się TYLKO przy
`var` (zmienna ma wartość `undefined` przed przypisaniem, więc próba jej WYWOŁANIA jako funkcji
rzuca `TypeError`). Naprawiono wszystkie trzy miejsca PRZED dopisaniem nowych pytań quizowych
dotykających tego samego rozróżnienia (żeby nie powielić błędu w nowej treści).

**Decyzje o zakresie tematycznym pytań (żeby uniknąć testowania pojęć z PÓŹNIEJSZYCH rozdziałów
kursu JS, zanim zostaną formalnie wprowadzone):** unikano w tym rozdziale pytań opartych na
rest/spread operatorze (`...`, rozdział 4 lekcja 8 / rozdział 5 lekcja 5), pętli `for` (rozdział 6),
`Set`/`Map` (rozdział 9) oraz mechanizmu `this` (rozdział 7) — mimo że `.filter()`/`.map()`/
`.sort()`/`.forEach()`/`.push()`/`.slice()` (formalnie rozdział 4) są używane celowo i świadomie w
lekcjach 3-4 tego rozdziału, bo sama teoria Fazy 1 tych lekcji już je wprowadza jako przykłady
funkcji wyższego rzędu/callbacków (`.filter()`, `.map()` wymienione wprost w `DEFINITION`
`04_CallbackAndPredicateFunctions.json`) — TA decyzja została podjęta świadomie przez autora
Fazy 1, nie w tej sesji, więc kontynuowano ten sam wzorzec w Fazie 2.

Wszystkie 5 plików zweryfikowane: poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy,
struktura quizu (4 opcje A-D, `correct` w A-D, `explanation` obecne) i ćwiczeń (prompt/hint/
solution obecne) sprawdzone programowo dla każdego pliku, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP).

## `_js_04_tablice` (8/8 lekcji) — Faza 2 UKOŃCZONA w kolejnej sesji (2026-09-18, komenda
użytkownika "kontynuuj prace wychodze z domu nie pytaj o zgode")

Wszystkie 8 lekcji rozdziału 4 uzupełnione do pełnych **30 ćwiczeń / 100 pytań quizowych**:
`01_ArrayBasicsAndMutatingMethods`, `02_FilterMethod`, `03_MapMethod`, `04_ForEachMethod`,
`05_SortMethodInDepth`, `06_FlatMapAndFlat`, `07_ArrayDestructuring`, `08_ArraySpreadOperator` —
WSZYSTKIE 30/100. CZWARTY w pełni ukończony rozdział kursu JS (po `_js_01`, `_js_02`, `_js_03`).
Zdecydowana większość nowych pytań quizowych to pytania z kodem (`"Co wypisze poniższy kod?"`),
zgodnie z dyrektywą z 2026-09-17.

**Błąd narzędziowy znaleziony i naprawiony w tej sesji**: `fix_allcaps.js`/`fix_allcaps_residual.js`
NIE odróżniają emfazy WIELKIMI LITERAMI (do naprawienia) od LITERALNEGO wyniku wykonania kodu typu
`.toUpperCase()` pokazanego w opcji quizu (np. `"PIES"` jako poprawny wynik `"pies".toUpperCase()`)
— skrypt bezmyślnie zamienia WSZYSTKIE wielkie słowa na Title Case, co przy takich pytaniach
TWORZY DUPLIKATY opcji (dwie identyczne, "poprawione" wersje) i psuje poprawność merytoryczną.
ROZWIĄZANIE zastosowane w tej sesji: unikać w nowych pytaniach quizowych multi-literowych WIELKICH
SŁÓW jako dosłownego wyniku `.toUpperCase()` w opcjach odpowiedzi — zamiast tego testować efekt
`.toUpperCase()` pośrednio (np. `wynik[0] === wynik[0].toUpperCase()` zwracające boolean, albo
`.join()` łączący sformatowany string, gdzie tylko PIERWSZA litera jest wielka, co nie koliduje z
whitelistą skryptu). PO KAŻDYM URUCHOMIENIU `fix_allcaps.js` na nowym pliku: ZAWSZE ponownie
sprawdzić duplikaty opcji quizu (`new Set(Object.values(q.options)).size !== 4` dla każdego pytania)
— w tej sesji znaleziono i naprawiono 2 takie przypadki w `03_MapMethod.json` PRZED commitem.

Wszystkie 8 plików zweryfikowane: poprawny JSON, brak cyrylicy, brak duplikatów opcji quizu
(sprawdzone programowo), struktura quizu/ćwiczeń kompletna, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP). Zweryfikowane live przez API po `mvnw.cmd resources:resources` + restarcie backendu
(backend w tej sesji startował NIETYPOWO DŁUGO — ok. 76 sekund do `Tomcat started`, prawdopodobnie
narastający koszt seedowania coraz większej ilości treści JSON obu kursów — uwzględnić w
przyszłych sesjach przy szacowaniu czasu oczekiwania, NIE traktować jako zawieszenie procesu):
wszystkie 8 lekcji zwraca 30/100 (i poprawną liczbę bloków teorii 14 lub 15). Regresja: `GET
/api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian),
`_01_fundamentals/06_StringsAndBuilder` (15 bloków) i `_js_03_funkcje/05_Closures` (14 bloków) —
oba bez zmian. Backend zatrzymany po weryfikacji. Scommitowane w DWÓCH commitach w tej samej
sesji (lekcje 1-6, potem lekcje 7-8) — CAŁY rozdział `_js_04_tablice` w pełni gotowy.

## `_js_05_obiekty` (7/7 lekcji) — Faza 2 UKOŃCZONA w kolejnej sesji (2026-09-18, ten sam
autonomiczny ciąg pracy co `_js_04_tablice`, komenda "kontynuuj prace... nie pytaj o zgode")

Wszystkie 7 lekcji rozdziału 5 uzupełnione do pełnych **30 ćwiczeń / 100 pytań quizowych**:
`01_ObjectBasics`, `02_ObjectStaticMethods`, `03_ObjectDestructuring`,
`04_OptionalChainingAndNullishCoalescing`, `05_ObjectSpreadOperator`, `06_JsonStringifyAndParse`,
`07_DateObject` — WSZYSTKIE 30/100. PIĄTY w pełni ukończony rozdział kursu JS (po `_js_01`-`_js_04`).

**Błąd narzędziowy dodatkowo potwierdzony w tej sesji**: przy pisaniu dużej liczby pytań quizowych
pod rząd zdarzyło się KILKAKROTNIE wpisać przypadkowy, nieprawidłowy klucz JSON (np.
`"astonished: false"`) zamiast `"correct": "A"` — najpewniej artefakt autouzupełniania przy
szybkim, powtarzalnym wzorcu edycji. Każdy taki przypadek został wykryty NATYCHMIAST przez
`node -e "JSON.parse(...)"` (plik przestaje się parsować) i naprawiony przed kontynuacją. Wniosek
dla kolejnych sesji: PO KAŻDYM większym bloku dopisanych pytań (nie tylko na końcu pliku) uruchom
szybki `JSON.parse` — nie czekać do końca całej lekcji.

Wszystkie 7 plików zweryfikowane: poprawny JSON, brak cyrylicy, brak duplikatów opcji quizu
(sprawdzone programowo), struktura quizu/ćwiczeń kompletna, przepuszczone przez
`fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy
HTTP; ZAWSZE re-sprawdzano duplikaty PO uruchomieniu fix_allcaps.js, zgodnie z pułapką opisaną
wyżej — w tej sesji naprawiono kolejne 2-3 takie przypadki w różnych plikach). Zweryfikowane live
przez API po `mvnw.cmd resources:resources` + restarcie backendu (backend nadal startuje ok. 80-90
sekund — potwierdzone kilkukrotnie w tej sesji, NIE traktować jako zawieszenie): wszystkie 7 lekcji
zwraca 30/100 (i poprawną liczbę bloków teorii 14 lub 15, w zależności od obecności API_REFERENCE).
Regresja: `GET /api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` →
14 (bez zmian), `_01_fundamentals/06_StringsAndBuilder` i `_js_04_tablice/08_ArraySpreadOperator` —
oba bez zmian. Backend zatrzymany po każdej weryfikacji. Scommitowane w CZTERECH commitach w tej
samej sesji (lekcje 1-3, potem 4-5, potem 6-7) — CAŁY rozdział `_js_05_obiekty` w pełni gotowy.

## `_js_06_kontrola_przeplywu` (5/5 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-18, ten sam autonomiczny ciąg)

Wszystkie 5 lekcji rozdziału 6 mają **30 ćwiczeń / 100 quiz**: `01_IfElseAndTernary`,
`02_SwitchAndObjectAsMap`, `03_ForForOfForIn`, `04_TryCatchFinally`, `05_LooseVsStrictEquality`.
SZÓSTY w pełni ukończony rozdział kursu JS. Zweryfikowane live przez API (14/30/100 każda lekcja),
regresja czysta (`JAVA` 41 rozdziałów, `JAVASCRIPT` 14, `_01_fundamentals/06_StringsAndBuilder` 15
bloków, `_js_05_obiekty/07_DateObject` 100 quiz). Uwaga operacyjna: proces `java` z
`IntelliJ IDEA ...` (JPS build server, `-Xmx700m`) to NIE osierocony backend — nie zabijać;
backend platformy startuje jako `mvnw.cmd -q spring-boot:run
-Dspring-boot.run.main-class=com.example.javaquest.web.JavaQuestApplication` (ok. 60 s do gotowości),
a zatrzymuje się po `Get-NetTCPConnection -LocalPort 8082` → `Stop-Process`.

### NOWA METODA PISANIA QUIZU (od `_js_06`): odpowiedzi WYLICZANE wykonaniem kodu

Narzędzia są w repo: **`scripts/content-migration/js-quiz-generator/`** (`README.md` opisuje
użycie i wszystkie pułapki). Zamiast pisać `correct` ręcznie, pytanie z kodem `C(code, wyjaśnienie)`
jest WYKONYWANE w `vm` Node, a poprawna opcja i dystraktory generowane automatycznie — klucz
odpowiedzi nie może się już mylić. `verify.js` ponownie wykonuje kod każdego pytania i porównuje z
kluczem (wykrywa też uszkodzenia od `fix_allcaps`). **Ta metoda ZASTĘPUJE ręczne pisanie
pytań opisane wyżej w tym pliku — używać jej dla wszystkich kolejnych lekcji (`_js_07`-`_js_14`).**

### Znalezione i naprawione błędy w rozdziałach 1-5 (commit "poprawki blednych odpowiedzi quizu")

Uruchomienie `verify` na całym ukończonym materiale wykazało ~40 błędnych/uszkodzonych pytań:
(1) `fix_allcaps.js` niszczył WIELKIE LITERY W KODZIE i opcjach pytań (`Number.MAX_SAFE_INTEGER` →
`Number.Max_safe_integer`, `"KOT"` → `"kot"`, `"ABC"` → `"Abc"`, `DANE`, `IMIE, WIEK` itd.) → klucz
odpowiedzi przestawał się zgadzać z kodem, część opcji zduplikowana; (2) błędy autora
(`01_VarKeyword` #25: wynik 3 nie 2; `03_LetKeyword` #19: `typeof` nieistniejącego `let` daje
`"undefined"`, nie ReferenceError; `02_FilterMethod` #9/#80; `02_StringMethods` #13/#78/#55;
`01_StringBasics` #99; `07_DateObject` #11); (3) pytania o daty zależne od strefy czasowej
(`07_DateObject` #10, #83 — teraz `Math.round` / data ISO z `Z`). Wszystkie poprawione. **NIE
uruchamiać `fix_allcaps.js` ponownie na plikach rozdziałów 1-5** (zniszczy te poprawki); dla NOWYCH
plików generator sam blokuje wielkie słowa w kodzie/opcjach.

## `_js_07_this_i_konteksty` (7/7 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-18, ten sam ciąg)

Wszystkie 7 lekcji mają **30 ćwiczeń / 100 quiz**: `01_ThisBasics`, `02_ThisLosingContext`,
`03_ArrowFunctionsAndThis`, `04_CallMethod`, `05_ApplyMethod`, `06_BindMethod`,
`07_ThisInClassesAndEventHandlers`. SIÓDMY w pełni ukończony rozdział kursu JS. Napisane nową
metodą (generator w `scripts/content-migration/js-quiz-generator/`, odpowiedzi wyliczane
wykonaniem kodu, `verify.js` zielony dla wszystkich 7 plików). Zweryfikowane live przez API (14/30/100
każda lekcja), regresja czysta (JAVA 41, JS 14, `_01_fundamentals/06_StringsAndBuilder` 15 bloków,
`_js_06/05` i `_js_05/07` quiz 100). Backend zatrzymany po weryfikacji.

Ustalenia z pisania rozdziału 7 (dopisane do generatora): wirtualny zegar `setTimeout`/`clearTimeout`
w piaskownicy (callbacki wykonują się po kodzie głównym, w kolejności opóźnień); NIE pisać pytań o
`this` wewnątrz zwykłych callbacków `setTimeout` (Node ustawia tam `this` = obiekt Timeout, przeglądarka
undefined/window — środowiska się różnią) ani o `this`/`arguments` na najwyższym poziomie pliku (CJS/ESM/
przeglądarka dają różne wyniki); symulację zdarzeń DOM robić klasą z `fn.call(this, zdarzenie)`.
`Number.EPSILON`, `Date.UTC`, `"IT"` i inne słowa pisane WIELKIMI LITERAMI w kodzie pytań psuje
`fix_allcaps.js` — generator rzuca wyjątek, trzeba użyć innej formy.

## `_js_08_klasy` (7/7 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-18, ten sam ciąg)

Wszystkie 7 lekcji mają **30 ćwiczeń / 100 quiz**: `01_ClassConstructorAndMethods`, `02_ClassFields`,
`03_GettersAndSetters`, `04_StaticFieldsAndMethods`, `05_InheritanceExtendsSuper`,
`06_PrivateFieldsAndMethods`, `07_ClassVsFactoryFunction`. ÓSMY w pełni ukończony rozdział kursu JS.
Generator + `verify.js` zielone dla wszystkich 7 plików; live przez API 14/30/100 każda lekcja, regresja
czysta (JAVA 41, JS 14). Pytania o prywatne pola: dostęp `obiekt.#x` spoza klasy to SyntaxError na etapie
parsowania — w pytaniach pokazywany przez `eval("obiekt.#x")` w `try/catch`.

Technika przy pisaniu (nie wpisywać wszystkiego w jednym pliku): dane lekcji w `lNN_MM.js`, brakujące
ćwiczenia/pytania dopisywane osobnym plikiem `extraNN.js` (eksportuje `extraEx`, `extraC`, `extraT`) —
edycje przez narzędzie Edit/Write, NIE przez `sed`/heredoc z `\n` (psuje pliki). Ćwiczenia/pytania
Fazy 1 nie są nadpisywane — `apply()` dopisuje do nich, więc przed KAŻDYM ponownym uruchomieniem
generatora `git checkout -- <plik.json>`. Uwaga: pytania pojęciowe `T()` nie mogą duplikować pytań
z Fazy 1 (generator wykrywa identyczne `question` i rzuca wyjątek).

## `_js_09_kolekcje` (6/6 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-18, ten sam ciąg)

Wszystkie 6 lekcji mają **30 ćwiczeń / 100 quiz**: `01_SetUniqueValues`, `02_MapKeyValueWithAnyKeyType`,
`03_IteratingOverMap`, `04_MapObjectSetArrayConversions`, `05_WhenToUseMapSetVsObjectArray`,
`06_WeakMapAndWeakSet`. DZIEWIĄTY w pełni ukończony rozdział kursu JS. Generator + `verify.js` zielone
dla 6 plików; live przez API 15/30/100 (lekcje 1-3) i 14/30/100 (lekcje 4-6), regresja czysta (JAVA 41,
JS 14). Backend na tej maszynie wymaga `JAVA_HOME=C:\Users\kapit\.jdks\openjdk-25.0.2` (ms-17 daje
`UnsupportedClassVersionError`); po weryfikacji zatrzymywać tylko procesy `mvnw`/`spring-boot:run`, NIE
proces IntelliJ JPS (długo żyjący `java` z `-Xmx700m`).

## `_js_10_moduly` — Faza 2 W TOKU (3/7 lekcji ukończone)

Ukończone lekcje: `01_WhatAreEsModules`, `02_NamedImport`, `03_DefaultImport` — każda ma dokładnie
30 ćwiczeń i 100 pytań quizowych. Lekcja 3 została przejęta ze stanu 29/78 i domknięta o praktykę
re-eksportu domyślnego przez plik zbiorczy oraz 22 pytania. Nowe pytania obejmują m.in. wyrażenia
jako default, mutowalne obiekty przy niemutowalnym wiązaniu importu, hoisting deklaracji funkcji,
niedozwolone `export default const`, fakt że `export *` pomija default, jawny re-eksport,
`import domyslny, * as namespace`, Promise, klasy i jednokrotne wykonanie modułu.

`verify.js` ponownie wykonał prawdziwe, wieloplikowe moduły ES przez `vm.SourceTextModule`:
`03_DefaultImport.json` ma 14 bloków teorii, 30 ćwiczeń, 100 quizów, w tym 76 pytań z kodem;
brak błędnych kluczy odpowiedzi, duplikatów opcji, cyrylicy i frazy `native code`.

WAŻNA POPRAWKA GENERATORA: `fmt()` w `lib.js` nie formatował `Map`/`Set` (wypisywał `{}`), więc w lekcjach
`01`-`03` cztery klucze odpowiedzi były błędne (`{}` zamiast `Set(2) { 2, 4 }` itd.) — poprawione, `fmt` zna
teraz format Node: `Map(2) { "a" => 1, "b" => 2 }`, `Set(3) { 1, 2, 3 }`. `verify.js` re-wykonuje kod tym
samym `fmt`, więc PRZED tą poprawką nie wykryłby błędu — przy nowych typach wypisywanych wartości
(`WeakMap`, `Date`, klasy z polami, `Symbol`) sprawdzić wzrokowo `review.js`, czy poprawne odpowiedzi nie są
puste (`{}`). Piaskownica `vm` nie ma `URLSearchParams`, `document`, `window`, `fetch` (rozdziały 11-14
wymagają ich stubów albo pytań bez wykonywania kodu). `WeakMap` na kluczach symbolowych działa w Node 24.

## Następny krok (AKTUALNY — nadpisuje starszy opis poniżej)

Kontynuować od `_js_10_moduly/04_CombiningNamedAndDefaultImport` (Faza 1: 7/30 ćwiczeń,
6/100 quiz), potem lekcje 5-7 tego rozdziału oraz
`_js_11_dom` (7), `_js_12_zdarzenia` (6), `_js_13_asynchronicznosc` (6), `_js_14_srodowisko_przegladarkowe`
(6) — wszystkie wciąż na poziomie Fazy 1 (patrz tabele historyczne wyżej). Rozdziały `_js_01`..`_js_09`
są w pełni gotowe (30/100 każda lekcja). Uwaga: moduły ES (`import`/`export`) nie działają w `vm.runInNewContext`
— pytania z kodem trzeba pisać jako pojedyncze pliki bez `import` albo symulować moduły obiektami/funkcjami
(wzorzec IIFE), a `import()` dynamiczny pokazywać przez `Promise.resolve(...)` w `setTimeout`. Wzór danych:
`scripts/content-migration/js-quiz-generator/example-lesson-data.js`. Dla każdej lekcji:
przeczytaj istniejący plik (teoria, dotychczasowe ćwiczenia/quiz), napisz dane w stylu
`example-lesson-data.js` (14-21 nowych ćwiczeń, ~78 pytań z kodem + ~16 pojęciowych, docelowo
30/100), uruchom generator, `fix_allcaps*.js`, `verify.js`, przejrzyj `review.js`, commit. Po
rozdziale: `mvnw.cmd resources:resources`, backend, curl API (teoria/ćwiczenia/quiz), regresja,
zatrzymanie backendu, commit. Pytania o `this` bez obiektu przed kropką ZAWSZE zaczynaj od
`"use strict";`. Bez pytania użytkownika o zgodę między lekcjami/rozdziałami.

## Następny krok (STARSZY opis z sesji 2026-09-17/18, zachowany dla kontekstu)

**Faza 2 UKOŃCZONA dla `_js_01_zmienne` (6/6), `_js_02_typy_danych` (7/7), `_js_03_funkcje` (5/5),
`_js_04_tablice` (8/8) i `_js_05_obiekty` (7/7) — PIĘĆ PIERWSZYCH rozdziałów kursu JS w pełni
gotowe.** Kontynuuj Fazę 2 od `_js_06_kontrola_przeplywu/01_IfElseAndTernary` (Faza 1: 10/30
ćwiczeń, 6/100 quiz — patrz tabela historyczna wyżej w tym pliku przy `_js_06_kontrola_przeplywu`),
tą samą metodą co dotychczas. Rozdziały `_js_06` do `_js_14` WCIĄŻ na poziomie Fazy 1 (patrz tabele
historyczne wyżej w tym pliku — każda lekcja ma zapisaną dokładną liczbę ćwiczeń/quizu z Fazy 1,
zwykle 7-16 ćwiczeń i 6-8 pytań quizowych, czyli WCIĄŻ daleko od celu 30/100). Następny krok:
kontynuować Fazę 2 chronologicznie od `_js_06_kontrola_przeplywu` w dół, TĄ SAMĄ metodą co
zastosowana dla `_js_01`-`_js_05` w poprzednich sesjach — czytać istniejący plik lekcji, dopisywać
brakujące ćwiczenia/quiz (Edit, nie Write — nie nadpisywać już napisanej teorii), z NACISKIEM na
pytania quizowe z kodem (`"Co wypisze poniższy kod?"`), ręcznie weryfikując poprawność każdej
odpowiedzi przed zapisaniem (patrz błędy znalezione w poprzednich sesjach dot.
TypeError/ReferenceError przy TDZ/hoistingu — zawsze prześledź kod krok po kroku), a przy pytaniach
o `.toUpperCase()`/`.toLowerCase()` w opcjach quizu PATRZ WYŻEJ na sekcję o pułapce
`fix_allcaps.js` — unikaj multi-literowych WIELKICH SŁÓW jako dosłownych opcji odpowiedzi, testuj
efekt pośrednio, i ZAWSZE sprawdź duplikaty opcji PO uruchomieniu skryptu, przed commitem. RÓWNIEŻ
sprawdzaj `node -e "JSON.parse(...)"` PO KAŻDYM większym bloku dopisanych pytań (nie tylko na
końcu pliku) — patrz sekcja wyżej o przypadkowych nieprawidłowych kluczach JSON. Po każdym
rozdziale: fix_allcaps.js+fix_allcaps_residual.js (i fix_http_method_case.js WYŁĄCZNIE dla
`_js_13_asynchronicznosc`), weryfikacja JSON/struktury/duplikatów, weryfikacja live przez API z
regresją na obu torach, dopiero potem commit.

**Uwaga o skali**: uzupełnienie WSZYSTKICH 90 lekcji do 30/100 to bardzo duża ilość pracy
(orientacyjnie: każda lekcja wymaga dopisania ok. 10-20 ćwiczeń i 80-95 pytań quizowych) —
realistycznie wielosesyjne przedsięwzięcie. Ten plik jest jedynym miejscem prawdy o dokładnym
postępie (tabela per rozdział) — każda kolejna sesja powinna zacząć od przeczytania go, ustalić
pierwszy rozdział wciąż na poziomie Fazy 1, i kontynuować od niego, bez pytania użytkownika o
zgodę między rozdziałami (ustalone w tej sesji).

**Uwaga techniczna dla kolejnych sesji**: w tym środowisku `JAVA_HOME` NIE jest ustawiony domyślnie
w PowerShell/Bash — przed `mvnw.cmd` trzeba ręcznie ustawić `$env:JAVA_HOME =
"$env:USERPROFILE\.jdks\openjdk-25.0.2"` (JDK zarządzany przez IntelliJ, potwierdzony działający).
ZAWSZE sprawdzaj `Get-Process java` PRZED startem nowego backendu i zabijaj osierocone procesy z
poprzednich sesji (Stop-Process -Force) — w tej sesji wielokrotnie pozostawały uruchomione po
wcześniejszym `spring-boot:run`.
