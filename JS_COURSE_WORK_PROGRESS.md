# JS_COURSE_WORK_PROGRESS — Drugi blok platformy JavaQuest (kurs JavaScript)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac nad kursem JavaScript. Nadpisywany,
> nie dziennik. Pełna instrukcja/standard: `JS_COURSE_STAGE_PROMPT.md`. Stan kursu Java (osobna
> inicjatywa, niezależny plik): `WORK_PROGRESS.md`.

## Aktualny etap

**Faza 1 UKOŃCZONA dla wszystkich 14/14 rozdziałów (90/90 lekcji) — pełna teoria, częściowe
ćwiczenia/quiz w każdej lekcji. Faza 2 (uzupełnianie do 30 ćwiczeń/100 quiz na lekcję) —
NIE rozpoczęta, patrz "Następny krok" na końcu tego pliku.**

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

## `_js_02_typy_danych` — Faza 2 W TOKU (2/7 lekcji ukończone w tej sesji)

`01_StringBasics`: **30/30 ćwiczeń, 100/100 quiz — UKOŃCZONA.**
`02_StringMethods`: **30/30 ćwiczeń, 100/100 quiz — UKOŃCZONA.**
Obie zweryfikowane live przez API, regresja czysta. Pozostałe 5 lekcji rozdziału
(`03_NumberAndArithmetic` do `07_ValueVsReferenceTypes`) WCIĄŻ na poziomie Fazy 1 — patrz tabela
historyczna wyżej w tym pliku.

## Następny krok

**Faza 2 dla `_js_01_zmienne` UKOŃCZONA, `_js_02_typy_danych` w toku (2/7).** Kontynuuj od
`_js_02_typy_danych/03_NumberAndArithmetic` (Faza 1: 17/30 ćwiczeń, 8/100 quiz), tą samą metodą.
Rozdziały `_js_02` (pozostałe 5 lekcji) do `_js_14` WCIĄŻ na poziomie Fazy 1
(patrz tabele historyczne wyżej w tym pliku — każda lekcja ma zapisaną dokładną liczbę
ćwiczeń/quizu z Fazy 1, zwykle 7-24 ćwiczeń i 6-20 pytań quizowych, czyli WCIĄŻ daleko od celu
30/100). Następny krok: kontynuować Fazę 2 chronologicznie od `_js_02_typy_danych` w dół, TĄ SAMĄ
metodą co zastosowana dla `_js_01` w tej sesji — czytać istniejący plik lekcji, dopisywać
brakujące ćwiczenia/quiz (Edit, nie Write — nie nadpisywać już napisanej teorii), z NACISKIEM na
pytania quizowe z kodem (`"Co wypisze poniższy kod?"`), ręcznie weryfikując poprawność każdej
odpowiedzi przed zapisaniem. Po każdym rozdziale: fix_allcaps.js+fix_allcaps_residual.js (i
fix_http_method_case.js WYŁĄCZNIE dla `_js_13_asynchronicznosc`), weryfikacja JSON/struktury,
weryfikacja live przez API z regresją na obu torach, dopiero potem commit.

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
