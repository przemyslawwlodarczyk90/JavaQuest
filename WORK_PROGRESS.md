# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1 (analiza) — zakończony. Etap 2 (standard treści + komponenty) — w toku.

## Ostatnia ukończona czynność

Uporządkowano pamięć projektu: `CLAUDE.md` skrócony do trwałych faktów, cała historia pisania
kursu (_01-_31) przeniesiona do `COURSE_CONTENT_HISTORY.md` (nic nie usunięte, tylko przeniesione).

## Zmienione pliki (ten etap)

- `CLAUDE.md` — skrócony, dodane sekcje "Architektura platformy" i "Uruchamianie i weryfikacja".
- `COURSE_CONTENT_HISTORY.md` — nowy, pełna historia rozdziałów _01-_31 przeniesiona z CLAUDE.md.
- `WORK_PROGRESS.md` — nowy (ten plik).

## Wyniki testów / weryfikacji

Brak zmian w kodzie backendu/frontendu jeszcze na tym etapie — nic do skompilowania/uruchomienia.

## Problemy i decyzje

- Tytuł lekcji na platformie jest auto-generowany z slug (`LessonSlugTitles.humanize()`) i NIGDY
  nie dostał prawdziwej polskiej redakcji (np. "Spring Cloud Overview" zamiast polskiego tytułu).
  Decyzja odłożona — dotyczy 664 lekcji, do rozstrzygnięcia przy Etapie 4 (czy zmieniać architekturę
  tytułów, czy zostawić auto-generowane angielskie).
- Lampka "lekcja gotowa" (`hasContent`) jest dziś `true` dla 100% z 664 lekcji (cała treść JSON
  istnieje) — potwierdzone empirycznie, nie różnicuje już niczego, bezpieczna do usunięcia
  (nie steruje routingiem/testami, nie jest kolumną bazy — liczona w locie).

## Następny krok

Etap 2: rozszerzyć `ContentBlockType` (backend) o nowe typy bloków zgodne z 11-sekcyjnym
standardem lekcji, zbudować odpowiadające kafelki w `TheoryView.jsx` + style w `App.css`, usunąć
lampkę "gotowa" z UI (i z DTO `LessonSummary`, skoro nic więcej jej nie używa). Potem Etap 3:
lekcja wzorcowa `_02_oop/Lesson03_Constructors` (świadomie wybrana — adresuje wprost błędne
uproszczenie "konstruktor to specjalna metoda"). Potem Etap 4: migracja rozdziałami, commit po
każdym rozdziale (bez trailera współautorstwa, bez auto-push).
