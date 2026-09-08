# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1 (analiza) — zakończony. Etap 2 (standard treści + komponenty) — zakończony. Etap 3
(lekcja wzorcowa) — zakończony i zweryfikowany. Etap 4 (migracja rozdziałami) — jeszcze
NIE rozpoczęty.

## Ostatnia ukończona czynność

Lekcja wzorcowa `_02_oop/03_Constructors` przebudowana w całości wg nowego standardu 11 sekcji —
zweryfikowana end-to-end (restart backendu, `curl` na `theory`/`exercises`/`quiz`, zero regresji
na `_20_spring_core`).

## Zmienione pliki (od początku Etapu 2)

- `CLAUDE.md`, `COURSE_CONTENT_HISTORY.md`, `WORK_PROGRESS.md` — porządkowanie pamięci (Etap 1).
- `src/main/java/.../platform/content/ContentBlockType.java` — enum rozszerzony o 12 nowych
  typów (`INTRO`, `DEFINITION`, `VISUAL_EXAMPLE`, `CODE_BASIC`, `CODE_PRACTICAL`,
  `STEP_BY_STEP`, `USAGE`, `NOTE`, `PITFALL`, `CODE_WRONG`, `CODE_RIGHT`, `WHEN_TO_USE`,
  `SUMMARY`), stare typy (`CONCEPT`/`CODE_EXAMPLE`/`DIAGRAM`) zachowane jako legacy dla
  jeszcze niezmigrowanych lekcji.
- `frontend/src/components/TheoryView.jsx` — nowe kafle (`TYPE_META` dla 16 typów) + logika
  parowania dwóch bloków kodu obok siebie (`CODE_BASIC`+`CODE_PRACTICAL`, `CODE_WRONG`+`CODE_RIGHT`).
- `frontend/src/App.css` — style nowych kafli + `.theory-code-pair` (grid 2 kolumny na szerokim
  ekranie, 1 kolumna poniżej 900px) + usunięta lampka „gotowa” (`.lesson-list__badge*`).
- `frontend/src/pages/LessonListPage.jsx` — usunięta lampka „gotowa”/„treść w przygotowaniu”.
- `src/main/java/.../platform/chapter/ChapterController.java` — usunięte pole `hasContent`
  z `LessonSummary` (nic więcej z niego nie korzystało — zweryfikowane w Etapie 1).
- `src/main/resources/content/_02_oop/03_Constructors.json` — pełna przebudowa teorii (7 →
  15 bloków, nowy standard), poprawka merytoryczna („konstruktor to NIE metoda” zamiast „to
  specjalna metoda”), poprawione polskie znaki w 30 zadaniach i 100 pytaniach quizu (pola
  `prompt`/`hint`/`question`/`options`/`explanation` — pole `solution` z kodem Java celowo
  NIE tknięte).

## Wyniki testów / weryfikacji

- `mvnw.cmd compile` → BUILD SUCCESS (po zmianach w Etapie 2).
- `npm run build` (frontend) → sukces, build trafia do `src/main/resources/static`.
- Restart backendu + `curl /api/chapters/_02_oop/lessons/03_Constructors/theory` → 15/15 bloków,
  wszystkie nowe typy `ContentBlockType` poprawnie się ładują (brak `IllegalArgumentException`).
- `curl /api/chapters/_02_oop/lessons/03_Constructors/exercises` → 30/30, `/quiz` → 100/100.
- `curl /api/chapters/_20_spring_core/lessons` (rozdział niepowiązany) → 23/23 lekcji, bez
  regresji, kształt DTO poprawnie już bez `hasContent`.
- Testy automatyczne: bez zmian (projekt ma tylko `contextLoads()` — nie dotyczy tej zmiany).

## Problemy i decyzje

- Diakrytyki w zadaniach/quizie poprawione świadomym, ręcznie zweryfikowanym słownikiem słów
  (nie ślepym globalnym find-replace) — zastosowanym WYŁĄCZNIE do pól narracyjnych
  (`prompt`/`hint`/`question`/`options`/`explanation`), nigdy do `solution` (kod Java). Nie jest
  to gwarancja 100% wyłapania wszystkich literówek w 130 elementach — jeśli w kolejnych
  rozdziałach znajdziesz podobne przypadki, dopisuj do analogicznego słownika w skrypcie migracji.
- Tytuł lekcji nadal auto-generowany z slug (nie ruszane w tym etapie — patrz decyzja odłożona
  wcześniej, do rozstrzygnięcia przy Etapie 4).
- Stary enum (`CONCEPT`/`CODE_EXAMPLE`/`DIAGRAM`) MUSI zostać w `ContentBlockType`, dopóki
  wszystkie pozostałe 663 lekcje nie zostaną zmigrowane — nie usuwać przedwcześnie.

## Następny krok

Etap 4: migracja rozdziałami. Kolejność do ustalenia z użytkownikiem (proponowany start: reszta
`_02_oop`, potem `_01_fundamentals`, dalej chronologicznie) — commit lokalny po każdym ukończonym
rozdziale (bez trailera współautorstwa, bez `git push`), z tą samą weryfikacją co przy pilotażu
(restart + curl + regresja na 1 niepowiązanym rozdziale) po każdej paczce.
