# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1-3 — zakończone. **Etap 4 (migracja rozdziałami) — w toku.**

**DYREKTYWA UŻYTKOWNIKA:** cały projekt (wszystkie 31 rozdziałów) musi zostać przejechany migracją
w całości, bez pytania o zgodę na kontynuację (potwierdzone 2026-09-09, dwukrotnie w tej samej
sesji — użytkownik podkreślił, że zależy mu na WSZYSTKICH lekcjach od lekcji 01).

## Stan rozdziałów

- **`_01_fundamentals` (17/17) — KOMPLETNE.** Scommitowane (`97e317a`).
- **`_02_oop` (15/15) — KOMPLETNE**, w tym przegląd pod kątem API_REFERENCE. Scommitowane (`97e317a`).
- **`_03_collections` (23/23) — KOMPLETNE.** Scommitowane (`bbd54de`).
- **`_04_io` (24/24 lekcji) — KOMPLETNE wg nowego standardu, JESZCZE NIE SCOMMITOWANE.**
  Wszystkie lekcje przepisane ze starego 7-blokowego formatu na standard 11 sekcji. Większość
  (17/24) z pełnym katalogiem `API_REFERENCE` (InputStream/OutputStream/Reader/Writer bazowe,
  FileReader/Writer, BufferedReader/Writer, BufferedInputStream/OutputStream, DataStream,
  PrintWriter/PrintStream, Scanner, File, Path, Files, RandomAccessFile, Charset, wyjątki
  plikowe, NIO Channel/Buffer, ObjectOutputStream/InputStream, Gson, Jackson, ZIP). 7 lekcji
  świadomie BEZ API_REFERENCE (konstrukcje/koncepcje, nie klasy z wieloma metodami:
  try-with-resources, serialVersionUID, transient, wprowadzenie do JSON, porównanie
  Serializable/JSON, CSV — format tekstowy uczony przez już-skatalogowane BufferedReader/Writer).
  Zweryfikowane: wszystkie 24 pliki poprawny JSON, 0 "native code", 0 cyrylicy, oryginalne
  30 exercises + 100 quiz zachowane (tylko oczyszczone z nadużywania wielkich liter).
  **NASTĘPNY KROK: restart backendu (w trakcie) + curl weryfikacyjny + commit lokalny całego
  rozdziału `_04_io`.**
- **`_05_multithreading` i dalsze (do `_31_spring_cloud_microservices`) — NIE ROZPOCZĘTE.**
  Migracja rozdziałami, po kolei, bez pomijania żadnego.

## Ostatnia ukończona czynność

Przepisano od zera i wzbogacono wszystkie 24 lekcje `_04_io` (01_IOIntroduction … 24_ZIP) na
standard 11 sekcji + API_REFERENCE tam, gdzie merytorycznie uzasadnione. Treść źródłowa
(exercises/quiz) była już wysokiej jakości — wykorzystana w całości jako podstawa merytoryczna
nowej teorii, uzupełniona o brakujące metody API (np. pełny katalog `Files`, `Path`,
`ObjectOutputStream`/`InputStream`, `ZipOutputStream`/`InputStream`/`ZipEntry`, Gson, Jackson).

## Wyniki testów / weryfikacji

- 24/24 plików `_04_io` — poprawny JSON, `grep -c "native code"` = 0, brak cyrylicy.
- Wszystkie zachowują oryginalne 30 exercises + 100 quiz (sprawdzone liczbowo).
- `mvnw.cmd compile` — nie wymagany (żadna zmiana kodu Java, tylko dane JSON).
- **W TRAKCIE:** restart backendu z KOMPLETNYM `_04_io` + curl na próbce (01, 10, 20, 24) +
  regresja na `_01_fundamentals`/`_02_oop`/`_03_collections` — PO tym dopiero commit.

## Problemy i decyzje

- **Powtarzający się drobny błąd tej sesji:** przy kilku plikach (`14_Optional` w `_03_collections`,
  `18_TransientKeyword` i `24_ZIP` w `_04_io`) `Edit` z `old_string` zawierającym dodany przeze
  mnie (błędnie) przecinek/cudzysłów nie pasował do faktycznej treści pliku i rzucał błąd — ZAWSZE
  wtedy odczytywano faktyczną treść pliku (`Read`) i poprawiano `old_string` na dokładne dopasowanie,
  zanim ponowiono `Edit`. Nigdy nie doszło do trwałego uszkodzenia pliku — każdy taki przypadek był
  wykryty PRZED zapisem (błąd narzędzia), a nie po nim.
- **Krytyczny incydent z wcześniejszej fazy (przy `_03_collections/01_ArrayList`):** błędne użycie
  `Write` zamiast `Edit` na już istniejącym pliku tymczasowo usunęło 19/30 exercises i 100 quiz —
  naprawione natychmiast przez odzyskanie z `git show HEAD`. Od tego momentu w całej sesji używano
  WYŁĄCZNIE `Edit` ograniczonego do sekcji `theory`, nigdy `Write` na pliku z cennymi danymi.
  Zgłoszono jako feedback narzędziowy (SendFeedback, lokalna kolejka).
- Ustalona i konsekwentnie stosowana zasada klasyfikacji: lekcje o KONKRETNEJ klasie/API z wieloma
  metodami (Files, Path, Scanner, Gson, Jackson, ZIP...) dostają pełny `API_REFERENCE`; lekcje o
  KONSTRUKCJACH/KONCEPCJACH językowych (try-with-resources, transient, serialVersionUID, format
  CSV/JSON jako taki) NIE dostają — ich "API" albo nie istnieje, albo jest już skatalogowane w
  innej, powiązanej lekcji (np. metody String/BufferedReader używane do CSV są już w `_01_fundamentals`
  i `_04_io/Lesson03`).

## Następny krok (dokładnie, w kolejności)

1. **Poczekaj na gotowość backendu** (uruchomiony w tle, z KOMPLETNYM `_04_io`).
2. `curl` weryfikacyjny na próbce: `_04_io/01_IOIntroduction` (15 bloków), `10_FilesClass` (15),
   `20_Gson` (15), `24_ZIP` (15), regresja na `_01_fundamentals/06_StringsAndBuilder` (15),
   `_02_oop/11_ObjectClass` (15), `_03_collections/08_HashMap` (15) — wszystkie bez zmian.
3. Jeśli wszystko OK: `git add src/main/resources/content/_04_io/ WORK_PROGRESS.md` + JEDEN
   lokalny commit PO POLSKU, BEZ stopki Co-Authored-By/linku do sesji (zgodnie z sekcją "Zasady
   commitowania" w `STAGE2_LESSON_REDESIGN_PROMPT.md`). NIE `git push`.
4. Przejdź do `_05_multithreading` — sprawdź obecny format lekcji (prawdopodobnie stary
   7-blokowy) i zacznij migrację tym samym, sprawdzonym wzorcem: dla lekcji o konkretnym
   narzędziu z wieloma metodami (Thread, ExecutorService, synchronized, Lock, CompletableFuture,
   klasy z java.util.concurrent...) PISZ OD RAZU z pełnym katalogiem API_REFERENCE.
5. Kontynuuj rozdziałami `_06_...` do `_31_...` w kolejności, bez pomijania żadnego, zgodnie z
   dyrektywą użytkownika z Etapu 4. Po każdym rozdziale: restart+curl+regresja+commit.
6. **Wzorzec pracy ustalony w tej sesji (powtarzaj dla każdej kolejnej lekcji/rozdziału):**
   a) Grep "exercises": [ żeby znaleźć koniec sekcji theory bez czytania całego pliku.
   b) Read tylko sekcji theory (offset/limit).
   c) Edit zastępujący WYŁĄCZNIE starą tablicę theory nową (11 sekcji + API_REFERENCE, gdy
      uzasadnione) — NIGDY Write na pliku z istniejącymi exercises/quiz.
   d) Uruchom `scratchpad/fix_allcaps.js` i `scratchpad/fix_allcaps_residual.js` na pliku (jeśli
      legacy treść ma nadużywanie wielkich liter — w nowo pisanych rozdziałach zwykle niepotrzebne,
      ale nie zaszkodzi uruchomić jako zabezpieczenie).
   e) Waliduj: `node -e "require(...)"` (JSON + liczba bloków), `grep -c "native code"`,
      `grep -lP '[\x{0400}-\x{04FF}]'` (cyrylica).
   f) Po całym rozdziale: restart backendu + curl na próbce + regresja na poprzednich rozdziałach
      + JEDEN commit lokalny dla całego rozdziału.
