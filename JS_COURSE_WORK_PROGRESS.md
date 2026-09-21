# JS_COURSE_WORK_PROGRESS — Drugi blok platformy JavaQuest (kurs JavaScript)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac nad kursem JavaScript. Nadpisywany,
> nie dziennik. Pełna instrukcja/standard: `JS_COURSE_STAGE_PROMPT.md`. Stan kursu Java (osobna
> inicjatywa, niezależny plik): `WORK_PROGRESS.md`.

## Aktualny etap

**KURS JAVASCRIPT UKOŃCZONY W CAŁOŚCI — Faza 1 i Faza 2 dla wszystkich 14/14 rozdziałów
(90/90 lekcji). Każda lekcja ma docelowe 30 ćwiczeń i co najmniej 100 pytań quizowych
(kilka lekcji z niewielką nadwyżką, np. 101/107/109 — zaakceptowane, jak w kursie Java).
Sesja 2026-09-21 dokończyła ostatni rozdział, `_js_14_srodowisko_przegladarkowe`
(lekcje 04-06) — patrz sekcja niżej po szczegóły. Brak dalszych kroków dla tego kursu,
chyba że użytkownik zleci nowe rozdziały/materiał.**

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

## `_js_10_moduly` (7/7 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-20, fork kontynuujący pracę)

Wszystkie 7 lekcji rozdziału 10 mają **30 ćwiczeń / 100 quiz**: `01_WhatAreEsModules`,
`02_NamedImport`, `03_DefaultImport` (ukończone w poprzedniej sesji), oraz w TEJ sesji
`04_CombiningNamedAndDefaultImport`, `05_NamespaceImport`, `06_DynamicImport`,
`07_ModulesAndReactAnalogy`. DZIESIĄTY w pełni ukończony rozdział kursu JS (po `_js_01`-`_js_09`).
Commit: "Kurs JavaScript: _js_10_moduly UKONCZONY (7/7, 30/100 kazda lekcja)".

Lekcje 4-7 napisane NOWĄ METODĄ (generator w `scripts/content-migration/js-quiz-generator/`,
odpowiedzi wyliczane wykonaniem prawdziwego, wieloplikowego kodu ES modules przez `modrun.mjs`/
`vm.SourceTextModule` — WSZYSTKIE cztery lekcje intensywnie korzystają z rzeczywistych
`import`/`export` w kodzie pytań, nie z symulacji). Tematy: łączenie importu domyślnego i
nazwanego w jednej linii (+ alias, wspólny stan modułu, `export {x as default}` vs `export default
x` czyli kopia kontra żywe wiązanie), namespace import (`import * as X`, klucz `.default`,
read-only obiektu namespace ale MUTOWALNA zawartość wewnątrz, żywe wiązania dla zmiennych, cache
modułu), import dynamiczny (`import()` jako `Promise`, `.then()`, `Promise.all` dla równoległego
ładowania, cache przy wielokrotnym imporcie tej samej ścieżki, obsługa błędów przez try/catch,
lazy loading warunkowe), i zamykająca lekcja z pełną analogią do komponentów React (barrel files/
`export * from`, komponent importujący inny komponent, `React.lazy()` = `import()` pod spodem).

**Ustalenia/pułapki znalezione w tej sesji (ważne dla `_js_11`-`_js_14`):**
1. **`eval("import ...")` NIGDY nie działa** — `import`/`export` to deklaracje statyczne, niedozwolone
   wewnątrz `eval()`, więc `eval('import x, {} from "./a.js"')` ZAWSZE rzuca `SyntaxError`,
   NIEZALEŻNIE od tego, czy testowany import jest poprawny czy nie. Użycie tego triku do pytań
   "czy to SyntaxError?" daje pozornie poprawną (bo execution-verified), ale MERYTORYCZNIE BŁĘDNĄ
   odpowiedź (np. "puste klamry to błąd" — nieprawda, są poprawne). Do testowania błędów
   modułowych (zła kolejność importu, brakujący `export default`, brakująca nazwana wartość) pisz
   PRAWDZIWY, dosłowny (niepoprawny) `import` na najwyższym poziomie pliku `main.js` w wieloplikowym
   bloku — `modrun.mjs` łapie prawdziwy `SyntaxError` z parsowania/linkowania modułu przez własny
   `try/catch` i to jest wiarygodny wynik.
2. **Generator odrzuca WIELKIE LITERY (3+ znaki, poza białą listą `JSON/URL/API/HTTP/HTML/CSS/UTF/
   JVM`) WSZĘDZIE w kodzie pytania z kodem** — nie tylko w identyfikatorach (`KURS_EUR`, `MAX_WPISOW`),
   ale też w STRINGACH i KOMENTARZACH (`"PLN"`, `"NAGLOWEK"`, `"ID-"`, `"PDF"`, `// POPRAWNIE`)
   ORAZ osobno w gotowych OPCJACH quizu (próg 2+ znaki tam). Pisz stałe modułowe w kursie JS
   CAMELCASE (`kursEur`, `maxWpisow`), nie `SCREAMING_SNAKE_CASE` — w exercises (`ex`) to ograniczenie
   NIE obowiązuje (nie są sprawdzane przez `build()`), ale dla spójności warto stosować to samo.
3. Dwie stare (Faza 1) lekcje w tym rozdziale miały ćwiczenia "napisz komentarz pokazujący import
   komponentu X.jsx" z DOSŁOWNYM, nieskomentowanym `import ... from "./X.jsx"` wskazującym na
   nieistniejący plik — `verify.js`/`run()` traktuje to jako prawdziwy kod i rzuca błędem
   (`Cannot find module`). Naprawione w `04_CombiningNamedAndDefaultImport` (ćwiczenie 4, Card.jsx)
   i `07_ModulesAndReactAnalogy` (ćwiczenie 1, Card.jsx) przez zakomentowanie linii `import` —
   zgodnie z oryginalną intencją zadania ("napisz KOMENTARZ pokazujący..."). Sprawdź podobny wzorzec
   przy pracy nad `_js_11`-`_js_14`, jeśli tam też występują analogiczne "ilustracyjne" ćwiczenia.
4. `apply()` rzuca przy duplikacie DOKŁADNEGO tekstu pytania — łatwo przypadkowo skopiować ten sam
   scenariusz dwa razy przy pisaniu wielu bloków tematycznych pod rząd (zdarzyło się w tej sesji
   w `07_ModulesAndReactAnalogy` — 3 duplikaty wykryte i usunięte przed sukcesem generatora).

Wszystkie 4 pliki zweryfikowane: `verify.js` czyste (T14/E30/Q100, brak `BAD:`), brak duplikatów
opcji quizu, brak cyrylicy, brak `native code`, przepuszczone przez `fix_allcaps.js`+
`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` — rozdział nie dotyczy HTTP), `ident.js`
pokazuje wyłącznie fałszywe trafienia (nazwa komponentu/funkcji pisana wielką literą kontra jej
zwracany string pisany małą, np. `Button`/`button`, `Alert`/`alert` — nie błędy). Zweryfikowane live
przez API na TYMCZASOWYM backendzie na porcie 8091 (NIE na porcie 8082, gdzie już działał backend
uruchomiony przez użytkownika z IntelliJ — CELOWO nie dotknięty, żeby nie przerwać sesji
użytkownika): wszystkie 4 lekcje zwracają 14/30/100, `GET /api/chapters?track=JAVA` → 41 (bez
zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian), `_js_09_kolekcje/01_SetUniqueValues`
(100 quiz) i `_01_fundamentals/06_StringsAndBuilder` (15 bloków teorii) — oba bez zmian. Tymczasowy
backend zatrzymany po weryfikacji. Scommitowane w jednym commicie.

## `_js_11_dom` (7/7 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-20)

Wszystkie 7 lekcji rozdziału 11 uzupełnione do pełnych **30 ćwiczeń / 100 pytań quizowych**:
`01_WhatIsTheDom`, `02_SelectingDomElements`, `03_ManipulatingElementContent`,
`04_CreatingAndRemovingElements`, `05_AttributesAndDataAttributes`, `06_ClassListDynamicClasses`,
`07_ChangingCssStylesViaJs` — WSZYSTKIE 30/100. JEDENASTY w pełni ukończony rozdział kursu JS
(po `_js_01`-`_js_10`). Trzy commity w tej sesji (lekcje 1-3, potem 4-5, potem 6-7).

**ROZWIĄZANIE problemu braku `document`/`window` w piaskownicy generatora (opisanego jako otwarty
problem w poprzedniej wersji tej sekcji, patrz niżej)** — NIE trzeba było dopisywać stubów DOM do
`lib.js`/`modrun.mjs`. Zastosowano trzy techniki, w tej kolejności ważności:
1. **Trik ReferenceError/typeof** (większość pytań z kodem, ok. 3-6 na lekcję): SKORO `document`/
   `window` NIE ISTNIEJĄ w piaskownicy `vm`, każde ich użycie (`document.title`, `document.
   querySelector(...)` itd.) rzuca PRAWDZIWY `ReferenceError` — DOKŁADNIE to, czego uczy PITFALL
   każdej lekcji tego rozdziału ("document nie istnieje poza przeglądarką"). `typeof document`
   (bez rzucania błędu, bo `typeof` na gołym identyfikatorze nigdy nie rzuca) zwraca `"undefined"` —
   też legalny, odrębny typ pytania. Generator liczy `correct` automatycznie (`ReferenceError`/
   `"undefined"`), więc te pytania są w 100% wiarygodne i nie wymagają ręcznej weryfikacji.
2. **Symulacja przez czysty JS** (spora część pozostałych pytań z kodem): `NodeList`/tablica →
   zwykła tablica lub obiekt `{0:..,1:..,length:N}` + `Array.from()`; `classList` → `Set` (ma
   `add`/`delete`/`has`, identyczne działanie do `add`/`remove`/`contains`, `toggle` pisane jako
   mała funkcja pomocnicza); `cloneNode(true)` (głęboki klon) → `structuredClone` (DOSTĘPNY w
   piaskownicy przez polyfill `POLY` w `lib.js` — prawdziwe, wykonywalne demo niezależności kopii);
   `cloneNode()`/`cloneNode(false)` (płytki klon) → spread `{ ...obiekt, dzieci: [] }`; `dataset`
   → zwykły obiekt JS ze stringami (dataset TO w praktyce zwykły obiekt, więc symulacja jest
   dokładna, nie przybliżona); `element.style` → zwykły obiekt JS z kluczami camelCase.
3. **Pytania POJĘCIOWE (`T()`, większość pytań ogółem w tym rozdziale)** dla faktów, których nie
   da się ani wykonać, ani sensownie zasymulować (np. "co robi `getComputedStyle`", "czym różni się
   `classList` od `className`").

**WAŻNA POPRAWKA NARZĘDZIA w tej sesji**: `apply()` w `lib.js` (funkcja dopisująca ćwiczenia do
JSON) rzucała błędem przy KAŻDYM ćwiczeniu, którego `solution` odwołuje się do `document`/`window`
(bo `run(sol)` w piaskownicy rzuca `ReferenceError`) — `verify.js` miał już wyjątek na to
(`/DOM|document|window|fetch/.test(...)`), ale `apply()` nie. Dodano IDENTYCZNY wyjątek do `apply()`
(`scripts/content-migration/js-quiz-generator/lib.js`), inaczej NIE dałoby się dopisać żadnego
nowego ćwiczenia stylu Fazy 1 tego rozdziału (`document.getElementById(...)...`) przez generator —
trzeba było je dopisywać ręcznie/przez Edit. Ta poprawka jest częścią commitu lekcji 1-3 i dotyczy
też `_js_12`-`_js_14` (ten sam problem wystąpi tam ponownie).

Wszystkie 7 plików zweryfikowane: `verify.js` czyste (T14-15/E30/Q100, brak `BAD:`), brak duplikatów
opcji quizu (sprawdzone programowo), brak duplikatów tekstu pytań (w tym względem JUŻ ISTNIEJĄCYCH
pytań Fazy 1 — kilkukrotnie złapane przez `apply()` i poprawione przed sukcesem, patrz uwaga niżej),
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js` (BEZ `fix_http_method_case.js` —
rozdział nie dotyczy HTTP). **Uwaga praktyczna dla `_js_12`-`_js_14`**: `fix_allcaps.js` lowercase'uje
"DOM" (nie jest na białej liście akronimów) do "dom" w tekście pytań — jeśli piszesz WARIANTY
pytania Fazy 1 zawierającego "DOM"/"dom" w treści, sprawdź PO uruchomieniu `fix_allcaps.js`, czy
nowy wariant nie kolabuje tekstowo z istniejącym pytaniem (złapane i poprawione 2x w lekcji 1 tego
rozdziału).

Weryfikacja live przez API POMINIĘTA w tej sesji — próba uruchomienia tymczasowego backendu na
porcie 8091 napotkała krytyczny brak pamięci RAM w systemie (2,2 GB wolnego z 16 GB, prawdopodobnie
przez równoległe działanie IntelliJ + backendu użytkownika na porcie 8082 + próby uruchomienia
drugiego backendu), harness ubił dwa procesy monitorujące start backendu z komunikatem o niskiej
pamięci — backend zatrzymany, PONOWNA próba NIE podjęta zgodnie z ostrzeżeniem harnessu. Zamiast
tego poleganie na: `verify.js` (wykonuje KAŻDY kod pytania i porównuje z kluczem — to najsilniejsza
dostępna weryfikacja poprawności), `node -e "JSON.parse(...)"` na wszystkich 7 plików, `git status`
potwierdzający, że zmienione zostały WYŁĄCZNIE pliki treści tego rozdziału (żadnego kodu Java/
config). Ryzyko regresji ocenione jako bardzo niskie (wyłącznie dane JSON, ten sam schemat co
istniejące pliki) — **ZALECANE: przy następnej okazji, gdy backend użytkownika i tak będzie
restartowany, zweryfikować `_js_11_dom` przez API razem z kolejnym rozdziałem**, dokładnie jak
opisano w sekcji "Weryfikacja live i commity" w `JS_COURSE_STAGE_PROMPT.md`.

## `_js_12_zdarzenia` (6/6 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-20, kontynuacja)

Lekcja 5 (`05_EventPropagationAndDelegation`, 30/100) i lekcja 6 (`06_RegularExpressions`, 30/100)
dokończone w tej sesji (lekcje 1-4 były już gotowe z poprzedniej sesji, niescommitowane resztki
w working tree — dokończone, zweryfikowane, scommitowane). **DWUNASTY w pełni ukończony rozdział
kursu JS (po `_js_01`-`_js_11`).** Znaleziony i naprawiony 1 literowka cyrylicka w lekcji 5
(`propagujа` z cyrylickim „а” zamiast łacińskiego „a”) przed commitem.

Zweryfikowane live przez API (backend na porcie 8082): wszystkie 6 lekcji 14/30/100, `GET
/api/chapters?track=JAVA` → 41 (bez zmian), `GET /api/chapters?track=JAVASCRIPT` → 14 (bez zmian).
**PRZY OKAZJI zweryfikowano też `_js_11_dom` (pominięty w poprzedniej sesji z powodu braku RAM)**
— wszystkie 7 lekcji 14-15/30/100, czysto.

## `_js_13_asynchronicznosc` — W TRAKCIE (sesja 2026-09-20, ten sam ciąg)

Lekcja 1 (`01_SetTimeoutAndSetInterval`, 30/101) i lekcja 2 (`02_Promises`, 30/101) UKOŃCZONE.
Pozostałe: `03_AsyncAwait`, `04_FetchApi`, `05_FormDataAndSendingData`, `06_RestApiPatterns` —
wciąż na poziomie Fazy 1 (8/30, 6/100 każda, patrz tabela historyczna wyżej w tym pliku).

**WAŻNE ODKRYCIA TECHNICZNE tej sesji (kluczowe dla `03_AsyncAwait`, `04_FetchApi`,
`05_FormDataAndSendingData`, `06_RestApiPatterns` — cały rozdział intensywnie używa Promise):**

1. **`setInterval`/`clearInterval` NIE ISTNIEJĄ w piaskownicy `run()` w `lib.js`** (tylko
   `setTimeout`/`clearTimeout` są zasymulowane) — użycie ich w kodzie QUIZU („Co wypisze") dałoby
   FAŁSZYWY `ReferenceError` (w przeciwieństwie do `document`/`window` w `_js_11`, gdzie brak w
   piaskownicy odzwierciedla PRAWDZIWY fakt językowy — `setInterval` istnieje naprawdę wszędzie,
   więc pokazywanie go jako niedostępny to dezinformacja). **Zasada: `setInterval`/`clearInterval`
   TYLKO w ćwiczeniach (`ex`) i pytaniach pojęciowych (`T()`), NIGDY w kodzie quizowym (`C()`).**
   Dodano wyjątek `setInterval|clearInterval` do regexów w `apply()` (`lib.js`) i `verify.js`
   (analogicznie do istniejącego `DOM|document|window|fetch`), żeby ćwiczenia z `setInterval` w
   `solution` nie wywalały generatora przy weryfikacji wykonania — commit scommitowany razem z
   lekcją 1.
2. **Kod z `Promise` NIGDY nie wykona swoich callbacków `.then()` w zwykłej, synchronicznej
   piaskownicy `run()` z `lib.js`** — `vm.runInNewContext(...)` jest w pełni synchroniczne i
   funkcja `run()` zwraca wynik natychmiast po nim, BEZ NIGDY drenowania kolejki mikrotasków (w
   przeciwieństwie do `modrun.mjs`, który ma prawdziwą pętlę `await flush()` z `setImmediate`).
   Zweryfikowane empirycznie: `run('Promise.resolve(5).then(n=>console.log(n))')` → `{logs: []}`
   (PUSTE, mimo że w prawdziwym JS wypisałoby `5`). **ROZWIĄZANIE: dopisz `// main.js\n` jako
   PIERWSZĄ linię kodu przed wywołaniem `run()`/`build()`** — `isModuleCode()` w `lib.js` wykrywa
   nagłówek pliku (`/^\/\/ [\w.\/-]+\.m?js\s*$/m`) i przekierowuje wykonanie przez `modrun.mjs`
   (osobny proces Node z `--experimental-vm-modules`, PRAWDZIWA pętla zdarzeń) — tam `Promise`,
   `Promise.all/allSettled/race/any`, kolejność mikrotask-przed-makrotask, `finally()`,
   propagacja błędu przez `throw`/`catch()` — WSZYSTKO działa poprawnie i jest w pełni
   zweryfikowane wykonaniem. Nagłówek `// main.js` zostaje WIDOCZNY w pytaniu pokazywanym
   studentowi jako pierwsza linia kodu (kosmetyczny, niegroźny artefakt — wygląda jak nazwa
   pliku, nie przeszkadza w zrozumieniu pytania). W `l13_02.js` zdefiniowano helper `const m =
   (code) => '// main.js\n' + code;` używany jako `C(m(c\`...\`), wyjaśnienie)`.
3. **Kod tworzący PRAWDZIWIE nieobsłużone odrzucenie Promise (`Promise.reject(...)` bez
   `.catch()`) w ĆWICZENIU (`ex`, wykonywane przez zwykłe `run()`, BEZ nagłówka `// main.js`)
   WYWALA CAŁY SKRYPT GENERATORA pod koniec jego działania** (Node.js domyślnie traktuje
   nieobsłużone odrzucenie Promise jako błąd krytyczny procesu, nawet jeśli powstało głęboko
   wewnątrz `vm.runInNewContext`) — objawia się jako `Error` rzucony PO tym, jak `apply()` już
   zakończyło zapis pliku (mylące, wygląda jakby się udało). **Zasada: NIGDY nie pisz ćwiczenia,
   które tworzy faktycznie nieobsłużony `Promise.reject()` w wykonywanym kodzie — pokazuj
   koncept "unhandled rejection" WYŁĄCZNIE przez komentarz w `solution` (bez uruchamiania
   prawdziwego, nieobsłużonego reject), tak jak naprawiono w tej sesji.**
4. Przy liczeniu docelowej liczby nowych `ex`/`q` PRZED uruchomieniem generatora: licz programowo
   (`require` pliku z podmienionym `apply(...)` na `module.exports`), NIE ręcznie ani przez grep
   po wzorcu `^\s*\['` (fałszywie łapie linie tablicy `wrong` wewnątrz wieloliniowych wywołań
   `T(...)`) — w tej sesji dwa razy pomylono się w liczeniu i trzeba było dopisywać brakujące
   pozycje w drugiej turze (`git checkout -- <plik.json>` przed ponownym uruchomieniem, żeby nie
   zduplikować już zaaplikowanych wpisów).

Wszystkie 2 pliki zweryfikowane: `verify.js` czyste, brak duplikatów opcji, brak cyrylicy (w
lekcji 1 znaleziono i naprawiono 2 literówki cyrylickie: `woла`→`wola`, `dzwiека`→`dzwieku`), brak
`native code`, przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js`. Live przez API:
`_js_13_asynchronicznosc` jeszcze NIE zweryfikowany (backend zatrzymany po weryfikacji `_js_12` i
`_js_11`, przed napisaniem lekcji 1-2 tego rozdziału) — **zweryfikuj obie lekcje przy najbliższej
okazji restartu backendu, razem z kolejnymi lekcjami tego rozdziału**.

## `_js_13_asynchronicznosc` (6/6 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-20, ten sam ciąg)

Wszystkie 6 lekcji rozdziału 13 ukończone do **30/100+ każda**: `01_SetTimeoutAndSetInterval`
(30/101), `02_Promises` (30/101), `03_AsyncAwait` (30/100), `04_FetchApi` (30/100),
`05_FormDataAndSendingData` (30/100), `06_RestApiPatterns` (30/100). **TRZYNASTY w pełni
ukończony rozdział kursu JS (po `_js_01`-`_js_12`).**

**Dodatkowe odkrycia techniczne (uzupełnienie sekcji wyżej), ważne dla `_js_14`:**
1. **`fetch`/`FormData` NIE istnieją w piaskownicy, ale (w przeciwieństwie do `document`/
   `window`) są realnie dostępne w większości środowisk (przeglądarka + Node 18+)** — więc
   zamiast triku `ReferenceError`, każde pytanie/ćwiczenie definiuje WŁASNĄ, lokalną funkcję
   `fetch(url, opcje)` (mock zwracający `Promise<{ok,status,json,text}>` albo `TypeError` dla
   symulacji błędu sieci) — w pełni wykonywalne i zweryfikowane. Dla `FormData` dodano
   PRAWDZIWY, współdzielony polyfill do `POLY` w `lib.js` (append/get/getAll/has/delete/set/
   entries/keys/values/forEach) — działa identycznie jak prawdziwa klasa dla wartości
   tekstowych. **Zasada ogólna do zastosowania w `_js_14`:** jeśli obiekt (np. `localStorage`,
   `URLSearchParams`, `history`) jest real w wielu środowiskach (nie tylko przeglądarce), NIE
   używaj triku `ReferenceError` — albo dopisz współdzielony polyfill do `POLY`, albo mockuj go
   lokalnie per-pytanie. Trik `ReferenceError` zostaw wyłącznie dla rzeczy naprawdę
   przeglądarkowych (`document`, prawdziwy `window` jako globalny obiekt itp., jak w `_js_11`).
2. **Białą listę ALLCAPS w `build()` (`lib.js`) rozszerzono o `GET|POST|PUT|PATCH|DELETE|HEAD`**
   — potrzebne wszędzie, gdzie kod pytania/opcji zawiera nazwę metody HTTP jako literał string.
3. **Dwa razy w tej sesji nowo napisane pytanie `T()` kolidowało z ISTNIEJĄCYM pytaniem z Fazy
   1** (duplikat dokładnego tekstu) — zdarza się, gdy oryginalne polskie zdanie akurat nie
   zawiera żadnych znaków diakrytycznych (ą/ę/ł/ń/ó/ś/ź/ż), więc mój bezdiakrytyczny zapis
   przypadkiem pokrywa się 1:1 z oryginałem. `apply()` rzuca błąd PRZED zapisem pliku (bezpieczne
   — nic nie ginie), więc wystarczy zmienić/przeformułować kolidujące pytanie i uruchomić
   ponownie.
4. **Dwa razy w tej sesji znaleziono ćwiczenie z Fazy 1, które odwoływało się do zmiennej/funkcji
   zdefiniowanej w "poprzednim" ćwiczeniu** (pedagogicznie ciągłe, ale niewykonywalne w izolacji
   przez `run()`, które wykonuje każde `ex.solution` osobno) — objawia się jako `exerr` w
   `verify.js`, ale NIE blokuje `apply()` (który tylko sprawdza execution error, nie porównuje z
   kluczem) — więc pojawia się dopiero przy `verify.js`, PO zapisaniu pliku. Naprawka: dopisz
   brakującą definicję (funkcję/zmienną) na początku `solution`, żeby ćwiczenie było w pełni
   samodzielne. **Sprawdzaj `verify.js` na KAŻDYM ukończonym pliku, nie tylko `apply()`.**
5. Ważna zasada z SQL-owego odruchu: NIGDY nie escapuj apostrofu wewnątrz stringa JS podwojeniem
   (`'Don''t'`) — to składnia SQL, nie JavaScript, i powoduje błąd składniowy. Użyj innego
   sformułowania bez apostrofu, albo `\'` w stringu, albo zmień na podwójne cudzysłowy.

Wszystkie 6 plików zweryfikowane: `verify.js` czyste, brak duplikatów opcji, brak cyrylicy, brak
`native code`, przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js`.

## `_js_14_srodowisko_przegladarkowe` (6/6 lekcji) — Faza 2 UKOŃCZONA (sesja 2026-09-21) — ZAMYKA CAŁY KURS JS

Lekcje `01_WindowObject`, `02_LocalStorageAndSessionStorage`, `03_WindowLocation` były już
ukończone (30/100+) z poprzedniej sesji. W tej sesji dokończono pozostałe trzy:

- `04_WindowHistoryAndHistoryApi` — 30 ćwiczeń / 109 quiz. Kod z kodem wykonywalnym korzysta
  z lokalnych klas `MockHistory`/`MiniEventTarget` (ten sam wzorzec co `_js_11`/`_js_12`) zamiast
  prawdziwego `history`/`window`, bo `history` jest przeglądarkowo-wyłączny (nie istnieje w
  piaskownicy `vm`). Odkryto i naprawiono PRZY OKAZJI dwa **pre-istniejące** błędy sprzed tej
  sesji: (1) dwa duplikaty pytań quizowych z Fazy 1 kolidujące z nowymi (`Co robi
  history.pushState...`, `Co zawiera e.state...`) — przeformułowane; (2) dwa ćwiczenia Fazy 1
  (`exerr3`, `exerr5`) używające gołego `history.xxx` (bez `window.`/`location.`) rzucały
  `ReferenceError` w `verify.js`, bo `history` nie był na białej liście wyjątków w
  `lib.js`/`verify.js` (był tam tylko `document|window|fetch|...|location\.`) — DODANO
  `history\.` do obu regexów (trwała poprawka, przyda się każdemu kolejnemu rozdziałowi
  używającemu `history`).
- `05_EcmaScriptStandards` — 30 ćwiczeń / 107 quiz. Lekcja jest w większości KONCEPCYJNA
  (historia wersji ES/TC39/caniuse.com), więc quiz to głównie pytania `T()` (pojęciowe) plus
  mniejsza pula pytań z kodem opartych o REALNIE wykonywalne, nowoczesne metody ES2019-ES2023
  (`toSorted`/`toReversed`/`toSpliced`/`with`/`at`/`Object.hasOwn`/`replaceAll`/`flat`/
  `flatMap`/`Object.fromEntries`) — wszystkie te metody DZIAŁAJĄ naprawdę w piaskownicy (silnik
  V8 w tym Node.js je wspiera), więc dały się w pełni zweryfikować wykonaniem, bez żadnych
  sztuczek/mocków.
- `06_DebuggingInVsCode` — 30 ćwiczeń / 100 quiz. **WAŻNE ODKRYCIE TECHNICZNE**: `console.table`,
  `console.time`, `console.timeEnd` NIE są zaimplementowane w obiekcie `con` piaskownicy
  `lib.js` (tylko `log`/`error`/`warn`/`info`) — każde ich użycie rzuca `TypeError`, więc (a) NIE
  można ich użyć w pytaniach quizowych z kodem (`C()`, bo `build()` też by rzucił), (b) ćwiczenia
  Fazy 1 z tej lekcji używające ich (`exerr` w `verify.js`) wymagały DODANIA
  `console\.(table|time|timeEnd|group|groupEnd|count|assert|trace)` do białej listy wyjątków w
  `lib.js`/`verify.js` (analogicznie do poprawki `history\.` wyżej — trwała, przyda się w
  przyszłości). Sam `debugger;` jest za to bezpieczny do wykonania — w piaskownicy bez
  aktywnego inspektora jest cichym no-opem (zweryfikowane empirycznie), więc pytania z kodem
  zawierające `debugger;` obok zwykłej logiki działają normalnie. Quiz tej lekcji, z uwagi na
  temat (narzędzia, nie mechanika języka), jest w większości pojęciowy (`T()`), z mniejszością
  pytań `C()` opartych o klasyczne, W PEŁNI wykonywalne "błędy do znalezienia debuggerem"
  (off-by-one, brak `return`, `==` vs `===`, `var` kontra `let` w pętli z `setTimeout`, mutacja
  obiektu przez referencję, `this` gubione w zwykłej funkcji, shadowing, `typeof null`,
  `NaN === NaN`, kolejność `setTimeout` kontra kod synchroniczny).

Wszystkie 3 pliki zweryfikowane: poprawny JSON, `grep`/test na cyrylicę i `native code` (brak),
przepuszczone przez `fix_allcaps.js`+`fix_allcaps_residual.js`, `verify.js` czyste (`OK`) dla
wszystkich trzech po poprawkach regexów opisanych wyżej, `ident.js` bez rzeczywistych problemów
(tylko oczekiwane false-positive przy wielkiej literze na początku zdania/cudzysłowie).

Zweryfikowane live przez tymczasowy backend na porcie 8091 (`JAVA_HOME` ustawiony ręcznie na
`~/.jdks/openjdk-25.0.2`, `mvnw.cmd resources:resources` przed startem): `GET
/api/chapters?track=JAVA` → 41 rozdziałów (bez zmian), `GET /api/chapters?track=JAVASCRIPT` →
14 rozdziałów, suma `lessonCount` = 90 (bez zmian). Wszystkie 6 lekcji `_js_14` zwraca przez API
dokładnie 14 bloków teorii / 30 ćwiczeń / quiz zgodny z liczbami wyżej (100, 100, 101, 109, 107,
100). Regresja Javy bez zmian: `_01_fundamentals/06_StringsAndBuilder` → 15 bloków teorii,
`_js_13_asynchronicznosc/06_RestApiPatterns` → 14 bloków. Backend zatrzymany po weryfikacji
(potwierdzony brak procesów `java`). Scratch pliki generatora (`l14_04.js`/`l14_05.js`/
`l14_06.js`) usunięte po zakończeniu pracy, zgodnie z konwencją.

## KURS JAVASCRIPT UKOŃCZONY W CAŁOŚCI (2026-09-21)

**Wszystkie 14/14 rozdziałów, 90/90 lekcji — Faza 1 (pełna teoria, 11 sekcji standardu) i Faza 2
(30 ćwiczeń / 100+ pytań quizowych na lekcję) UKOŃCZONE.** Nie ma dalszego "następnego kroku" dla
istniejącego zakresu tego kursu — kolejna praca nad kursem JS to już nowy zakres zlecony przez
użytkownika (np. nowy rozdział spoza materiału źródłowego `dodatkowe materiały/kurs js/js-course/`,
albo przegląd jakościowy istniejących 90 lekcji), a nie kontynuacja obecnego planu.

**Dwie trwałe poprawki narzędziowe z tej sesji, ważne dla KAŻDEJ przyszłej pracy nad generatorem
`scripts/content-migration/js-quiz-generator/`:** biała lista wyjątków od błędu `ReferenceError`/
`TypeError` przy weryfikacji ćwiczeń (`lib.js` `apply()` i `verify.js`) obejmuje teraz również
`history\.` oraz `console\.(table|time|timeEnd|group|groupEnd|count|assert|trace)` — oba
dodano, bo to REALNE, przeglądarkowe/hostowe API niezaimplementowane w minimalnej piaskownicy
`vm`, analogicznie do wcześniej istniejących `document`/`window`/`fetch`/`location\.`. Jeśli
przyszła praca (Java lub JS) natrafi na podobny `TypeError`/`ReferenceError` dla INNEGO,
realnego, ale niezaimplementowanego w piaskownicy API — rozszerz ten sam regex, zamiast obchodzić
problem inaczej.

**KOREKTA WAŻNEJ NIEŚCISŁOŚCI z poprzedniej wersji tej sekcji**: moduły ES (`import`/`export`)
**DZIAŁAJĄ** w generatorze — `lib.js` ma funkcję `isModuleCode()` + `runModule()`/`modrun.mjs`
(`vm.SourceTextModule` w osobnym procesie Node z `--experimental-vm-modules`), która automatycznie
przechwytuje kod zaczynający się od `import`/`export` albo zawierający nagłówki `// plik.js` i
wykonuje go jako PRAWDZIWE, wieloplikowe moduły ES. Rozdział `_js_10_moduly` (w tym lekcje 4-7
ukończone w tej sesji) intensywnie z tego korzysta — NIE trzeba symulować modułów przez IIFE. Ta
sama technika NIE jest jednak potrzebna dla `_js_11`-`_js_14` (te rozdziały nie dotyczą już
`import`/`export`), ale wymagają CZEGOŚ INNEGO: piaskownica `vm`/`modrun.mjs` NIE MA `document`,
`window`, `fetch`, `URLSearchParams`, `localStorage` (przeglądarkowe API) — pytania z kodem
wymagające tych obiektów albo trzeba pisać jako pytania POJĘCIOWE (`T()`, bez wykonania), albo
dopisać do `lib.js`/`modrun.mjs` lekkie stuby tych API PRZED napisaniem pytań z kodem dla `_js_11`
(DOM) i `_js_14` (środowisko przeglądarkowe/`window`) — sprawdź na początku pracy nad `_js_11`,
czy taki stub już istnieje, zanim zaczniesz pisać pytania zakładające jego brak.

**Pułapki z sesji ukończenia `_js_10` (pełny opis wyżej, sekcja `_js_10_moduly`), zastosuj też do
`_js_11`-`_js_14`:**
1. NIGDY nie testuj błędów składniowych przez `eval("...")` — `eval` nie obsługuje deklaracji
   `import`/`export` (nie dotyczy to już `_js_11`+, ale zasada ogólna: nie używaj `eval` do
   testowania SyntaxError, gdy błąd zależy od czegoś, czego `eval` sam z siebie nie obsługuje).
2. Generator odrzuca WIELKIE LITERY (3+ znaki, poza białą listą) WSZĘDZIE w kodzie i OPCJACH
   pytania z kodem — również w stringach/komentarzach, nie tylko identyfikatorach. Pisz stałe
   camelCase.
3. Sprawdź istniejące, stare (Faza 1) ćwiczenia typu "napisz komentarz pokazujący..." pod kątem
   dosłownego, nieskomentowanego kodu odwołującego się do nieistniejących plików — `verify.js`
   będzie to zgłaszał jako `exerr`.
4. `apply()` rzuca przy duplikacie identycznego tekstu pytania — łatwo się powtórzyć przy pisaniu
   wielu bloków tematycznych pod rząd, szczególnie gdy kopiuje się wzorce z wcześniejszych sekcji.

Dla każdej lekcji: przeczytaj istniejący plik (teoria, dotychczasowe ćwiczenia/quiz), skopiuj
`example-lesson-data.js` jako scratch `lNN_MM.js` w `scripts/content-migration/js-quiz-generator/`
(NIE commituj scratchy — usuń je po zakończeniu pracy nad plikiem/rozdziałem), napisz dane (14-23
nowych ćwiczeń, ~85-95 pytań z kodem + ~10-15 pojęciowych, docelowo dokładnie 30/100), uruchom
generator, `fix_allcaps*.js`+`fix_allcaps_residual.js`, `verify.js` (musi być czyste), `ident.js`,
przejrzyj `review.js`, commit. Po całym rozdziale: `mvnw.cmd resources:resources`, backend, curl API
(teoria/ćwiczenia/quiz każdej lekcji), regresja na obu torach, zatrzymanie backendu, commit. **Jeśli
backend już działa na porcie 8082 (np. uruchomiony przez użytkownika z IntelliJ) — NIE zatrzymuj go
i NIE restartuj, tylko uruchom WŁASNY, tymczasowy backend na innym porcie (np. `--server.port=8091`
przez `-Dspring-boot.run.arguments=--server.port=8091`) do weryfikacji, i zatrzymaj TYLKO ten
tymczasowy proces po zakończeniu.** Pytania o `this` bez obiektu przed kropką ZAWSZE zaczynaj od
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
