# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1-3 — zakończone. **Etap 4 (migracja rozdziałami) — w toku.**

**DYREKTYWA UŻYTKOWNIKA:** cały projekt (wszystkie 31 rozdziałów) musi zostać przejechany migracją
w całości, bez pytania o zgodę na kontynuację (potwierdzone 2026-09-09).

## Stan rozdziałów

- **`_01_fundamentals` (17/17) — KOMPLETNE.** Scommitowane (`97e317a`).
- **`_02_oop` (15/15) — KOMPLETNE**, w tym przegląd pod kątem API_REFERENCE. Scommitowane (`97e317a`).
- **`_03_collections` (23/23 lekcji) — KOMPLETNE wg nowego standardu, JESZCZE NIE SCOMMITOWANE.**
  Wszystkie lekcje przepisane ze starego 7-blokowego formatu (CONCEPT/CODE_EXAMPLE) na standard
  11 sekcji. 21/23 lekcje mają dodatkowo pełny katalog `API_REFERENCE` (wyjątki świadome, NIE
  pomyłka: `15_LinkedHashSet` dziedziczy całe API z `05_HashSet` bez nowych metod;
  `19_ComparableVsComparator` to lekcja porównawcza, API już skatalogowane w `07_Comparator`).
  Zweryfikowane: wszystkie 23 pliki poprawny JSON, 0 wystąpień bugu "native code", 0 zanieczyszczeń
  cyrylicą (napotkany i naprawiony jeden przypadek w trakcie sesji — patrz "Problemy i decyzje"),
  wszystkie zachowały oryginalne 30 exercises + 100 quiz (nienaruszone, tylko exercises/quiz
  oczyszczone z nadużywania WIELKICH LITER przez `scratchpad/fix_allcaps*.js`).
  **NASTĘPNY KROK: restart backendu (w trakcie) + curl weryfikacyjny na próbce lekcji + commit
  lokalny CAŁEGO rozdziału `_03_collections` jako jeden commit (zgodnie z zasadą "po każdym
  ukończonym rozdziale").**
- **`_04_io` i dalsze (`_05_multithreading` ... `_31_spring_cloud_microservices`) — NIE
  ROZPOCZĘTE.** Migracja rozdziałami, po kolei, bez pomijania żadnego.

## Ostatnia ukończona czynność

Przepisano od zera i wzbogacono WSZYSTKIE 23 lekcje `_03_collections`
(01_ArrayList … 23_SpecialMaps) na standard 11 sekcji + API_REFERENCE. Treść źródłowa
(exercises/quiz) była już wysokiej jakości — wykorzystano ją w całości jako podstawę merytoryczną
nowej teorii, uzupełniając o brakujące metody API tam, gdzie trzeba (np. `Collections`, `Map`,
`Stream`/`Collectors`, `TreeMap`/`TreeSet` metody nawigacyjne, `Deque`, kolekcje concurrent,
legacy, specjalne mapy). Wszystkie pliki zwalidowane (JSON + native-code + cyrylica).

## Wyniki testów / weryfikacji

- 23/23 plików `_03_collections` — poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy.
- Wszystkie zachowują oryginalne 30 exercises + 100 quiz (sprawdzone liczbowo).
- `mvnw.cmd compile` — nie wymagany (żadna zmiana kodu Java w tej paczce, tylko dane JSON).
- Backend restartowany wielokrotnie w trakcie sesji, każdorazowo potwierdzone poprawne ładowanie
  zmienionych lekcji przez `curl` (świeże liczby bloków teorii zgodne z oczekiwaniami).
- **W TRAKCIE:** ostateczny restart backendu z KOMPLETNYM `_03_collections` + curl na próbce
  (01, 08, 13, 23) — PO tym dopiero commit całego rozdziału.

## Problemy i decyzje

- **Krytyczny błąd, naprawiony w trakcie sesji:** przy pierwszej lekcji `_03_collections`
  (`01_ArrayList`) błędnie użyto narzędzia Write zamiast Edit, co nadpisało CAŁY plik i
  tymczasowo usunęło 19/30 exercises oraz wszystkie 100 quiz. Naprawione natychmiast przez
  odzyskanie tych sekcji z `git show HEAD` i scalenie z nową teorią. **Od tego momentu w całej
  sesji używano WYŁĄCZNIE Edit z precyzyjnym `old_string`/`new_string` ograniczonym do sekcji
  `theory`, NIGDY Write na już istniejącym pliku z cennymi exercises/quiz.**
  Zgłoszono jako feedback narzędziowy (SendFeedback) — ryzyko dla przyszłych sesji: zawsze
  weryfikuj po edycji dużego pliku, że `exercises`/`quiz` nie zniknęły.
- Drugi drobny incydent: raz w kodzie API_REFERENCE (`07_Comparator`) pojawił się przypadkowy
  literał z cyrylicą w nazwie zmiennej (`produкти` zamiast `produkty`) — najpewniej pomyłka
  klawiatury/autouzupełniania. Naprawiony natychmiast, DODANO regularny grep na cyrylicę
  (`grep -lP '[\x{0400}-\x{04FF}]'`) jako krok weryfikacji KAŻDEGO kolejnego pliku w tej sesji.
- Raz zdarzył się niezamierzony niezescapowany cudzysłów w treści `body` (JSON się zepsuł) — od
  razu wykryty przez walidację `node -e "require(...)"` i naprawiony. Lekcja: unikać literalnych
  cudzysłowów wewnątrz treści opisowej (`.orElse("coś")` w prozie), pisać `.orElse(...)` zamiast.
- Skrypty `scratchpad/fix_allcaps.js` i `scratchpad/fix_allcaps_residual.js` (patrz commit
  `97e317a` dla `_01_fundamentals`) użyte na WSZYSTKICH 23 plikach `_03_collections` — działały
  bezbłędnie (JSON zawsze poprawny po przebiegu, exercises/quiz zachowane liczbowo).

## Następny krok (dokładnie, w kolejności)

1. **Poczekaj na gotowość backendu** (uruchomiony w tle po raz kolejny, z KOMPLETNYM
   `_03_collections`).
2. `curl` weryfikacyjny na próbce: `_03_collections/01_ArrayList` (15 bloków), `08_HashMap`
   (15), `13_StreamsAdvanced` (15), `23_SpecialMaps` (15), regresja na `_01_fundamentals/06_
   StringsAndBuilder` (15, bez zmian) i `_02_oop/11_ObjectClass` (15, bez zmian).
3. Jeśli wszystko OK: `git add src/main/resources/content/_03_collections/ WORK_PROGRESS.md` +
   JEDEN lokalny commit PO POLSKU, BEZ stopki Co-Authored-By/linku do sesji (zgodnie z sekcją
   "Zasady commitowania" w `STAGE2_LESSON_REDESIGN_PROMPT.md`). NIE `git push`.
4. Przejdź do `_04_io` — sprawdź obecny format lekcji (prawdopodobnie stary 7-blokowy, jak
   `_03_collections` przed tą sesją) i zacznij migrację tym samym wzorcem: dla lekcji o
   konkretnym narzędziu z wieloma metodami (File, Path, Files, BufferedReader/Writer, Scanner,
   strumienie I/O) PISZ OD RAZU z pełnym katalogiem API_REFERENCE.
5. Kontynuuj rozdziałami `_05_...` do `_31_...` w kolejności, bez pomijania żadnego, zgodnie z
   dyrektywą użytkownika z Etapu 4. Po każdym rozdziale: restart+curl+regresja+commit.
