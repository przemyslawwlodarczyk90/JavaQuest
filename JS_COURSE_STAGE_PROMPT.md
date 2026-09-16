# JavaQuest — Drugi blok platformy: kurs JavaScript

> Ten plik jest JEDYNYM, obowiązującym źródłem instrukcji dla PISANIA treści (teoria/ćwiczenia/
> quiz) drugiego bloku platformy — kursu JavaScript. `STAGE2_LESSON_REDESIGN_PROMPT.md` pozostaje
> instrukcją dla kursu Java i NIE jest tu bezpośrednio stosowany — ten plik POWTARZA z niego to,
> co ma zastosowanie (format 11 sekcji, model danych), i DOPISUJE, co jest specyficzne dla
> JavaScriptu i dla sposobu, w jaki ten kurs powstaje (konsumowanie gotowego materiału źródłowego,
> nie pisanie od zera).

## Kontekst i cel

Użytkownik chce, żeby JavaQuest hostował DWA równoległe kursy pod tym samym frontendem/backendem:
**Java** (istniejący, kompletny, domyślnie uruchamiany z panelu głównego) i **JavaScript**
(nowy, budowany teraz). Panel główny dostał dwa przełączniki na górze ("Java"/"JavaScript") —
Java jest domyślna, JavaScript prowadzi do analogicznego, równoległego kursu.

Cel kursu JavaScript (zgodnie z materiałem źródłowym, patrz niżej): przygotowanie pod naukę
Reacta — solidne opanowanie mechaniki i składni **czystego JavaScriptu**, żeby sam język
przestał być przeszkodą, zanim zacznie się naukę frameworka.

## Źródło materiału — jak z niego korzystać

W `dodatkowe materiały/kurs js/js-course/` (katalog **poza kontrolą wersji tego repo** — osobne,
zagnieżdżone repozytorium Git, NIE submodule, NIE dotykać jego `.git/`) leży GOTOWY materiał
źródłowy: 14 folderów (`01-zmienne` … `14-srodowisko-przegladarkowe`), każdy z plikami:

- **`lekcja.js`** — materiał teoretyczny z działającym kodem i komentarzami, podzielony na
  sekcje oznaczone `// JS-XXX — Tytuł` (90 znaczników łącznie, w całym kursie, numerowane
  ciągle 001-090 mimo że foldery **na dysku** ponumerowane są 01-14 w innej kolejności niż
  numeracja JS-XXX — foldery `07`-`10` to NOWO dopisane tematy pod Reacta, `11`-`14`
  ("środowiskowe") to STARE tematy przesunięte w numeracji folderów, ale ich znaczniki JS-XXX
  (039-063) są WCZEŚNIEJSZE niż `07`-`10` (064-090) — to jest OK, folder na dysku = rozdział
  platformy, znacznik JS-XXX = jedna lekcja w tym rozdziale, kolejność LEKCJI W ROZDZIALE idzie
  za kolejnością znaczników w PLIKU danego folderu, NIE za globalnym numerem JS-XXX).
- **`cwiczenia.js`** — ~30-35 gotowych PROMPTÓW ćwiczeń na folder (BEZ rozwiązań), pogrupowanych
  pod nagłówkami `--- JS-XXX — Tytuł ---`, czyli już przypisanych do konkretnej przyszłej lekcji.
  Mechaniczne, jednoconceptowe zadania (jedna metoda/składnia na raz) — ŚWIADOMIE bez algorytmów,
  bez łamigłówek, bez wieloetapowych "mini-projektów" (patrz `00-spis-tresci/README.md` w tym
  folderze — "Filozofia ćwiczeń").
- **`dodatkowe.js`** — DRUGA porcja ćwiczeń w tym samym duchu, dla tych samych sekcji JS-XXX,
  gdy `cwiczenia.js` nie daje wystarczająco materiału na lekcję.
- (rozdziały 11-14) **`index.html`** — środowisko przeglądarkowe do testowania na żywo (DOM,
  zdarzenia, `fetch`, `window`) — te lekcje NIE działają przez `node lekcja.js`, tylko w
  przeglądarce.

**Sposób pracy z tym materiałem przy pisaniu lekcji platformy:**

1. Przeczytaj sekcję `JS-XXX` z `lekcja.js` — to jest SUROWY szkielet merytoryczny (poprawny,
   zweryfikowany), NIE gotowa treść platformy. Rozwiń go do pełnych 11 sekcji (patrz niżej) —
   dopisz wprowadzenie, definicję, analogię, wyjaśnienie krok po kroku, zastosowanie, pułapki
   (materiał źródłowy czasem JUŻ pokazuje pułapkę w komentarzu, np. hoisting `var` — wykorzystaj
   to jako gotowy `PITFALL`/`CODE_WRONG`/`CODE_RIGHT`, ale i tak przepisz na pełny, samodzielny
   blok zgodny ze standardem).
2. Z odpowiadającej sekcji `--- JS-XXX ---` w `cwiczenia.js` i `dodatkowe.js` WYBIERZ i
   ADAPTUJ zadania do pola `exercises` (patrz niżej dla dokładnego formatu) — te pliki dają
   PROMPT, ale NIGDY `hint`/`solution` — oba trzeba DOPISAĆ (solution = poprawny, DZIAŁAJĄCY kod
   JS rozwiązujący dokładnie to zadanie).
3. Nie kopiuj kodu z `lekcja.js` 1:1 do bloków teorii bez weryfikacji — sprawdzaj poprawność
   merytoryczną tak samo krytycznie, jak przy Javie (wersje ECMAScript, status funkcji, czy coś
   jest deprecated) — materiał źródłowy jest solidny, ale to wciąż tylko punkt wyjścia.
4. Ten katalog źródłowy jest tylko dla Ciebie (materiał roboczy) — NIE jest serwowany przez
   platformę i NIE trzeba go commitować/wersjonować w tym repo (już nie jest, ma własny `.git`).

## Docelowa struktura: 14 rozdziałów, 90 lekcji

Zaprojektowana i zapisana jako scaffold (BEZ treści JSON, wyłącznie struktura nawigacyjna) w
`ChapterSeedData.java`, sekcja "DRUGI BLOK PLATFORMY: KURS JAVASCRIPT". Każdy rozdział ma slug
z prefiksem `_js_NN_...` (odróżnia go od rozdziałów Java `_NN_...`, używany też przez frontend
`LessonListPage` do ustalenia, czy link "wszystkie rozdziały" ma wracać na `/` czy `/js`).

| Rozdział (slug) | Tytuł | Lekcje (JS-XXX źródłowe) |
|---|---|---|
| `_js_01_zmienne` | JavaScript - zmienne i zasięgi | 001-006 |
| `_js_02_typy_danych` | JavaScript - typy danych | 007-013 |
| `_js_03_funkcje` | JavaScript - funkcje | 014-018 |
| `_js_04_tablice` | JavaScript - tablice | 019-026 |
| `_js_05_obiekty` | JavaScript - obiekty | 027-033 |
| `_js_06_kontrola_przeplywu` | JavaScript - sterowanie przepływem | 034-038 |
| `_js_07_this_i_konteksty` | JavaScript - this i konteksty wywołania | 064-070 |
| `_js_08_klasy` | JavaScript - klasy | 071-077 |
| `_js_09_kolekcje` | JavaScript - kolekcje Map i Set | 078-083 |
| `_js_10_moduly` | JavaScript - moduły ES | 084-090 |
| `_js_11_dom` | JavaScript - DOM | 039-045 |
| `_js_12_zdarzenia` | JavaScript - zdarzenia | 046-051 |
| `_js_13_asynchronicznosc` | JavaScript - asynchroniczność | 052-057 |
| `_js_14_srodowisko_przegladarkowe` | JavaScript - środowisko przeglądarkowe | 058-063 |

Dokładne slugi lekcji (PascalCase, numer + nazwa, np. `01_VarKeyword`) są w `ChapterSeedData.java`
— to JEDYNE miejsce, które trzeba sprawdzić, żeby poznać dokładną nazwę pliku JSON dla danej
lekcji (`src/main/resources/content/<rozdzial>/<lekcja>.json`, dokładnie jak w kursie Java).

**Kolejność pracy nad rozdziałami:** naturalna, 01→14 (rdzeń języka → tematy pod Reacta →
środowisko przeglądarkowe) — chyba że użytkownik zdecyduje inaczej.

## Model danych i standard 11 sekcji — DOKŁADNIE jak w kursie Java

Backend jest WSPÓLNY dla obu kursów — ten sam `ContentBlockType` enum (`INTRO`, `DEFINITION`,
`ANALOGY`, `VISUAL_EXAMPLE`, `CODE_BASIC`, `CODE_PRACTICAL`, `STEP_BY_STEP`, `USAGE`, `NOTE`,
`PITFALL`, `CODE_WRONG`, `CODE_RIGHT`, `WHEN_TO_USE`, `SUMMARY`, `API_REFERENCE`), ten sam
schemat pliku JSON lekcji (`{"theory": [...], "exercises": [...], "quiz": [...]}`), te same
zasady jak w `STAGE2_LESSON_REDESIGN_PROMPT.md`, sekcje "Obowiązkowa struktura każdej lekcji" i
"Pełne pokrycie API dla lekcji o «narzędziu z wieloma metodami»" — przeczytaj je, są w pełni
zastosowane tutaj, tylko z przykładami/analogiami/kodem w JavaScripcie zamiast Javy.

**`API_REFERENCE` w kursie JS** — dotyczy lekcji o KONKRETNYM obiekcie/klasie wbudowanej z wieloma
metodami (analogicznie do `String`/`Math`/`Arrays` w Javie): metody string (JS-008), metody
tablicowe (JS-019/020/021/022/024), `Object.*` (JS-028), `Map`/`Set` (JS-078-083), `Date`
(JS-033), `call`/`apply`/`bind` (JS-067-069), moduły `import`/`export` (JS-085-089), DOM
selektory/manipulacja (JS-040-045), `Promise`/`async`/`await`/`fetch` (JS-053-055). NIE dotyczy
czystych konstrukcji językowych (`if`, pętle, `this` jako koncepcja, domknięcia, klasy jako
mechanizm) — tam zwykłe 11 sekcji bez sztucznego dopisywania listy metod.

**Analogie** — jak w Javie, unikaj jednego, powtarzanego przykładu przez cały kurs. Materiał
źródłowy (`lekcja.js`) czasem już sugeruje dobrą analogię w komentarzu — wykorzystaj ją, ale
sformułuj jako pełny, samodzielny blok `ANALOGY`.

**"Żywe przykłady" (wyraźne zastrzeżenie użytkownika: "chcę, żeby lekcje były dojechane
merytorycznie z kodem, żeby wszystko było widać, fajne żywe przykłady")** — każdy blok
`CODE_BASIC`/`CODE_PRACTICAL` musi pokazywać NIE TYLKO kod, ale też **efekt jego uruchomienia**
jako komentarz (`// -> wynik`), dokładnie jak w `lekcja.js` źródłowym i jak w istniejących
lekcjach kursu Java (patrz np. `_01_fundamentals/06_StringsAndBuilder.json`). Kod w rozdziałach
01-10 musi dać się uruchomić przez `node plik.js` (czysty JS, bez przeglądarki) — kod w
rozdziałach 11-14 zakłada środowisko przeglądarkowe (DOM/`window`/`fetch`) i powinien to jawnie
zaznaczać w treści (`STEP_BY_STEP`/`NOTE`), tak jak materiał źródłowy z `index.html`.

## Ćwiczenia — format i liczba

Model `Exercise` (`prompt`/`hint`/`solution`) jest identyczny jak w Javie. **W odróżnieniu od
"lekkich" rozdziałów Java `_32`-`_41`** (gdzie `prompt`/`solution` to String z wyjaśnieniem
tekstowym), ten kurs jest **praktyczny/mechaniczny jak wczesne rozdziały Javy `_01`-`_16`** —
`prompt` każe NAPISAĆ konkretny kod, `solution` to PRAWDZIWY, DZIAŁAJĄCY kod JavaScript
rozwiązujący dokładnie to zadanie (nie opis słowny) — dokładnie w duchu materiału źródłowego
(`cwiczenia.js`/`dodatkowe.js` już mają takie prompty, tylko bez `hint`/`solution`).

**DYREKTYWA UŻYTKOWNIKA (2026-09-16, koryguje wcześniejszy zapis "8-15/8-12" powyżej —
NIEAKTUALNY, zostawiony wyłącznie jako historia decyzji): każda lekcja MA MIEĆ dokładnie 30
ćwiczeń i 100 pytań quizowych** — TA SAMA liczba, co we wczesnych rozdziałach Javy `_01`-`_16`
(patrz np. `_01_fundamentals/01_Variables.json`). Teoria ma być "dojechana w kosmos
merytorycznie" — pełne pokrycie tematu, wyczerpujące przykłady, żadnych skrótów jakościowych
względem kursu Java. To NIE jest "lekki" rozdział w stylu `_32`-`_41`.

**Sposób pracy: DWUFAZOWY, zgodnie z wyraźną instrukcją użytkownika ("zacznij przygotować
lekcje, a później je uzupełniaj")** — NIE trzeba osiągnąć 30/100 w jednym przebiegu na każdej
lekcji:

1. **Faza 1 (przygotowanie)** — dla każdej lekcji najpierw kompletna, w pełni dojechana teoria
   (14-15 bloków) + tyle ćwiczeń/quizu, ile realnie da się napisać W TEJ turze bez spadku
   jakości (nie mniej niż kilkanaście ćwiczeń i kilkanaście pytań, jeśli to możliwe) —
   TRAKTOWANE jako lekcja "w przygotowaniu", NIE jako gotowa.
2. **Faza 2 (uzupełnianie)** — w KOLEJNYCH turach/sesjach, wracaj do lekcji Fazy 1 i DOPISUJ
   kolejne, NIEPOWTARZALNE ćwiczenia/pytania, aż osiągniesz 30/100 — dopiero WTEDY lekcja jest
   w pełni gotowa.

`JS_COURSE_WORK_PROGRESS.md` MUSI dla KAŻDEJ lekcji jawnie zapisywać aktualną liczbę ćwiczeń/
quizu (np. "18/30 ćwiczeń, 22/100 quiz — Faza 1") — żeby kolejna sesja wiedziała, gdzie
dokładnie kontynuować uzupełnianie, bez zgadywania.

Źródłowe `cwiczenia.js`+`dodatkowe.js` dają maksymalnie ~10-15 gotowych promptów na sekcję
`JS-XXX` — to WYSTARCZA na dobry START (Faza 1), ale do 30 trzeba DOPISAĆ własne, w tym samym
duchu (mechaniczne, jednoconceptowe, bez algorytmów/łamigłówek — patrz "Filozofia ćwiczeń" w
`00-spis-tresci/README.md`). Quiz (100 pytań) trzeba napisać w całości samodzielnie — źródło nie
ma pytań quizowych w ogóle.

## Polskie znaki, jakość języka, weryfikacja

Te same zasady co w `STAGE2_LESSON_REDESIGN_PROMPT.md` (poprawne polskie znaki we WSZYSTKICH
polach widocznych dla użytkownika, NIGDY w `solution`/`code`, naturalny styl bez nadużywania
wielkich liter). **Nazwy zmiennych/funkcji w przykładach kodu — po polsku, tak jak w kursie
Java** (`liczbaKotow`, `sprawdzStatus`, nie transliteracja z angielskiego materiału źródłowego
1:1), zgodnie z konwencją WIDOCZNĄ w `cwiczenia.js` źródłowym (już jest po polsku).

Przed uznaniem pliku za gotowy: `node -e "JSON.parse(...)"` (poprawny JSON), `grep -c "native
code"` (=0 — mało prawdopodobne w świeżo pisanej treści, ale sprawdzaj jako siatkę
bezpieczeństwa), sprawdzenie braku cyrylicy, `fix_allcaps.js`+`fix_allcaps_residual.js` na
`exercises`/`quiz` (skrypty są język-agnostyczne, działają identycznie jak dla Javy) —
`fix_http_method_case.js` uruchamiaj WYŁĄCZNIE dla `_js_13_asynchronicznosc` (jedyny rozdział
faktycznie omawiający metody HTTP przez `fetch`/REST API), i tam PO uruchomieniu sprawdź `grep
-nE '\.(GET|POST|PUT|PATCH|DELETE)\('` pod kątem fałszywych trafień (nauczka z `_29_spring_
reactive/13_R2dbcIntro`, patrz `WORK_PROGRESS.md`) — w JS realnym ryzykiem są wywołania typu
`formData.get(...)`/`mapa.get(...)`/`Object.get`.

## Weryfikacja live i commity

Ten sam wzorzec co w Javie: `mvnw.cmd resources:resources` → restart backendu (`JAVA_HOME`
ustawiony, port 8082) → `curl http://localhost:8082/api/chapters/<rozdzial>/lessons/<lekcja>/
theory` (sprawdź liczbę bloków) → regresja na co najmniej jednej NIEZWIĄZANEJ, wcześniej
ukończonej lekcji obu kursów (Java i JS) → dopiero wtedy commit. Endpoint `/api/chapters` PRZYJMUJE
teraz `?track=JAVA` (domyślnie) LUB `?track=JAVASCRIPT` — sprawdzaj oba przy regresji, żeby mieć
pewność, że zmiana w jednym kursie nie zepsuła drugiego.

Po każdym ukończonym rozdziale — lokalny commit (jak w `STAGE2_LESSON_REDESIGN_PROMPT.md`,
zwięzły komunikat po polsku, bez stopki o współautorstwie AI zgodnie z zasadą commitowania z tego
samego pliku — TA sama zasada obowiązuje tutaj).

## Architektura: jak działa przełącznik Java/JavaScript (już zaimplementowane)

- `Chapter` (encja JPA) ma nowe pole `track` (enum `CourseTrack`: `JAVA`/`JAVASCRIPT`).
  `ChapterSeedData.ChapterSeed` ma DODATKOWY, 4-argumentowy konstruktor z jawnym `CourseTrack`
  (rozdziały Java używają starego, 3-argumentowego, który domyślnie ustawia `JAVA` — NIC w nich
  nie trzeba było zmieniać).
- `GET /api/chapters?track=JAVA|JAVASCRIPT` (domyślnie `JAVA`) zwraca rozdziały wyłącznie z
  danego toru.
- Frontend: `App.jsx` ma `TrackSwitcher` (dwa linki `/` i `/js`, stylowane `.track-switcher` w
  `App.css`) zamiast usuniętego napisu "Platforma edukacyjna oparta na kursie Java.". Trasa `/`
  renderuje `<ChapterListPage track="JAVA" />`, `/js` renderuje `<ChapterListPage
  track="JAVASCRIPT" />`. `LessonListPage` rozpoznaje kurs po prefiksie sluga rozdziału
  (`_js_` → link powrotny na `/js`, w przeciwnym razie na `/`).
- Rozdziały/lekcje kursu JavaScript są już WIDOCZNE w nawigacji (scaffold, 14/14 rozdziałów, 90/90
  lekcji) — każda pokazuje się jako "treść w przygotowaniu" (ten sam, już istniejący mechanizm co
  przy pustych rozdziałach `_35`-`_41` kursu Java), dopóki nie doda się plików JSON.

## Pamięć projektu i kontynuacja pracy

Ten plik (`JS_COURSE_STAGE_PROMPT.md`) + `JS_COURSE_WORK_PROGRESS.md` (aktualny stan, ostatnia
czynność, następny krok — analogicznie do `WORK_PROGRESS.md` kursu Java, ale ODDZIELNY plik, żeby
nie mieszać dwóch niezależnych inicjatyw w jednym, już bardzo długim dzienniku) są PARĄ plików do
przeczytania na starcie KAŻDEJ sesji dotyczącej kursu JavaScript. `CLAUDE.md` zawiera do nich
wskaźnik.

**Następny krok (do wykonania w kolejnej turze/sesji):** pisanie treści lekcji, rozdział po
rozdziale, zaczynając od `_js_01_zmienne` — dokładnie ten sam schemat pracy, co przy rozdziałach
Java (`mvnw.cmd compile` po zmianach kodu, weryfikacja JSON, live przez API, regresja, commit
po każdym rozdziale).
