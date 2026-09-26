# Kurs Linux — stan prac

Instrukcje: `LINUX_COURSE_STAGE_PROMPT.md`. Ten plik: aktualny stan, ostatnia czynność, następny
krok. Oddzielny od `WORK_PROGRESS.md` (Java) i `JS_COURSE_WORK_PROGRESS.md` (JavaScript).

## Stan (2026-09-26)

- **Krok 1 (struktura) — ZROBIONY i ROZBUDOWANY (2026-09-27, dyrektywa „kozak merytorycznie”).**
  46 rozdziałów (`_lx_01`…`_lx_46`) w 4 częściach, 437 lekcji. 234 lekcje mają źródło w
  notatkach `LN01`-`LN31` (`dodatkowe materiały/LinuxMistery/`), 203 to uzupełnienia do pełnego
  programu. Mapowanie w tabeli w `LINUX_COURSE_STAGE_PROMPT.md`; sprawdzone skryptem, że każda
  sekcja każdej notatki jest przypisana do lekcji (poza końcowymi „PODSUMOWANIAMI”).
  Pierwsza wersja (20 rozdziałów / 182 lekcje) została zastąpiona — pliki były puste.
- Tor `LINUX` wpięty w platformę (enum, seed, `CourseTrackConstraintSync`, `/linux` we
  frontendzie, fallback SPA). Weryfikacja: po starcie backendu baza ma JAVA 41/760,
  JAVASCRIPT 14/90, LINUX 46/437 (rozdziały/lekcje), CHECK `chapters_track_check` obejmuje
  LINUX; `LessonContentFilesTest` przechodzi.
- Wszystkie 437 plików treści są PUSTE (`theory`/`exercises`/`quiz` = []), stan każdej lekcji:
  0 bloków teorii, 0 ćwiczeń, 0 pytań.

## Następny krok

**Krok 2 — teoria**, rozdział po rozdziale od `_lx_01_wprowadzenie`: dla każdej lekcji pełne
bloki teorii na podstawie wskazanych sekcji notatek, przykłady poleceń z wynikiem sprawdzonym
w kontenerze (`ubuntu:24.04`). Ćwiczenia i quiz dopiero w kroku 3 (liczby do potwierdzenia z
użytkownikiem). Commit po każdym rozdziale, komunikat po polsku, bez stopki o współautorstwie.
