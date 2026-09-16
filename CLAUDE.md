# javaQuest

Projekt: kurs Java (`_01_fundamentals` … `_31_spring_cloud_microservices` + rozdziały `_32`+) +
platforma edukacyjna (frontend React + Spring Boot) serwująca ten kurs. Od 2026-09 platforma
hostuje DWA równoległe kursy/tory ("Java" i "JavaScript", przełącznik na panelu głównym, Java
domyślna) — patrz niżej.

**Wszystkie aktualne instrukcje robocze znajdują się w innych plikach — ten plik celowo NIE
zawiera własnych instrukcji, żeby uniknąć sprzecznych poleceń:**

- **`STAGE2_LESSON_REDESIGN_PROMPT.md`** — jedyne, obowiązujące źródło instrukcji dla pracy nad
  KURSEM JAVA (przebudowa jakości i wyglądu lekcji platformy). Czytaj TEN plik jako punkt startowy
  dla pracy nad Javą.
- **`WORK_PROGRESS.md`** — aktualny stan prac nad kursem Java, ostatnia czynność, następny krok.
  Czytaj przy komendzie „kontynuuj” w kontekście Javy.
- **`JS_COURSE_STAGE_PROMPT.md`** — jedyne, obowiązujące źródło instrukcji dla pracy nad KURSEM
  JAVASCRIPT (drugi blok platformy — struktura rozdziałów, standard treści, jak korzystać z
  materiału źródłowego). Czytaj TEN plik jako punkt startowy dla pracy nad JavaScriptem.
- **`JS_COURSE_WORK_PROGRESS.md`** — aktualny stan prac nad kursem JavaScript, ostatnia czynność,
  następny krok. Osobny plik od `WORK_PROGRESS.md` (Java) — dwie niezależne inicjatywy.
- `EDU_PLATFORM_PLAN.md` — historia Fazy 1 platformy (treść JSON wygenerowana z kursu) — archiwum,
  nie instrukcje.
- `COURSE_CONTENT_HISTORY.md` — pełna historia pisania samego kursu Javy (`_01`-`_31`) — archiwum,
  czytaj tylko gdy trzeba zrozumieć/naprawić kod źródłowy lekcji Javy, nie przy pracy nad platformą.

Kod pod `_01_fundamentals` … `_31_spring_cloud_microservices` pozostaje nienaruszoną „podstawą
programową” platformy — nie kasować, nie modyfikować przy pracy nad platformą.

`dodatkowe materiały/kurs js/js-course/` — materiał źródłowy do kursu JavaScript, osobne,
zagnieżdżone repozytorium Git (nie submodule) — patrz `JS_COURSE_STAGE_PROMPT.md` po szczegóły
korzystania z niego. Nie dotykać jego `.git/`.
