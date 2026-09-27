# Kurs Linux — stan prac

Instrukcje: `LINUX_COURSE_STAGE_PROMPT.md`. Ten plik: aktualny stan, ostatnia czynność, następny
krok. Oddzielny od `WORK_PROGRESS.md` (Java) i `JS_COURSE_WORK_PROGRESS.md` (JavaScript).

## Stan (2026-09-27)

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
- **Krok 2 (teoria) — W TOKU.** Rozdział `_lx_01_wprowadzenie` (8 lekcji) — ZROBIONY
  (2026-09-27): każda lekcja 14 bloków (pełny standard 11 sekcji + CODE_WRONG/CODE_RIGHT + NOTE),
  wyniki poleceń z prawdziwych uruchomień w kontenerach `ubuntu:24.04`, `debian:12`,
  `alpine:3.20`, `rockylinux:9`, `fedora:40`, `archlinux`, `eclipse-temurin:21-jre`.
  Pozostałe 429 lekcji (`_lx_02`…`_lx_46`) nadal PUSTE. Ćwiczenia i quiz — 0 wszędzie (krok 3).

## Konwencje ustalone przy rozdziale 1 (stosuj dalej)

- **Format przykładów**: sesja terminala — linia `$ polecenie` (komentarz `# ...` po ASCII), pod
  nią DOSŁOWNY wynik z prawdziwego uruchomienia; skracanie wyniku oznaczaj `(...fragment wyniku)`.
  Kontynuacja linii jako `> `. Pole `code` bez polskich znaków (także komentarze).
- **Wyniki sprawdzaj w kontenerze** (Docker Desktop trzeba czasem uruchomić:
  `Start-Process "C:\Program Files\Docker\Docker\Docker Desktop.exe"`). Uwaga na Git Bash: ścieżki
  typu `/usr/...` w argumentach `docker run` są przerabiane na `C:/Program Files/Git/...` —
  opakuj polecenie w `bash -c '...'` albo ustaw `MSYS_NO_PATHCONV=1`. Kontener bez TTY wypisuje
  `ls` w jednej kolumnie — dla widoku terminala użyj `ls -C -w 100 -T 0`; komunikaty błędów z
  `bash -c` mają prefiks `bash: line 1:`, w sesji interaktywnej go nie ma (sprawdzaj `bash -i`).
- **Jądro w przykładach** to `6.6.87.2-microsoft-standard-WSL2` (Docker Desktop na WSL2) — lekcje
  to wyjaśniają (rozdział 1, lekcja 3); przy systemd/sieci/dyskach potrzebny będzie inny sposób
  weryfikacji (VM/WSL z systemd) — zaznaczaj w treści, gdy wyniku nie da się uzyskać w kontenerze.
- **Pisanie**: lekcję piszę w pliku `.txt` (bloki `@@ TYP | Nagłówek`, kod po linii `@@code`) i
  konwertuję `node scripts/content-migration/lx_build_theory.js <plik.txt> <plik.json>` — skrypt
  zachowuje `exercises`/`quiz`, odrzuca polskie znaki w `code`, cyrylicę, puste `body`.
- **Weryfikacja live**: API `/api/**` wymaga teraz JWT (moduł logowania) — zamiast `curl` na
  endpoint sprawdzaj po starcie backendu tabelę `content_blocks` w Postgresie:
  `docker start javaquest-postgres` (użytkownik `user`, baza `pgDB`), potem
  `mvnw.cmd resources:resources` + `mvnw.cmd spring-boot:run` (JAVA_HOME jak w `WORK_PROGRESS.md`)
  i zapytanie `select l.slug, count(b.id) from lessons l join chapters c ... left join
  content_blocks b ...` dla rozdziału + regresja jednej lekcji Javy i JS. Po sprawdzeniu zatrzymaj
  backend i uruchom `mvnw.cmd test -Dtest=LessonContentFilesTest`.

## Następny krok

**Krok 2 — teoria, rozdział `_lx_02_wirtualizacja`** (10 lekcji, źródło LN01, LN16 §2-8), potem
kolejne rozdziały po kolei. Dla każdej lekcji pełne
bloki teorii na podstawie wskazanych sekcji notatek, przykłady poleceń z wynikiem sprawdzonym
w kontenerze (`ubuntu:24.04`). Ćwiczenia i quiz dopiero w kroku 3 (liczby do potwierdzenia z
użytkownikiem). Commit po każdym rozdziale, komunikat po polsku, bez stopki o współautorstwie.
