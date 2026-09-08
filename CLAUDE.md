# javaQuest

Kurs Java tworzony na wzór kursów [Codenga](https://codenga.pl) — teoria + ćwiczenia praktyczne,
budowany rozdziałami (dużymi blokami tematycznymi), z myślą o docelowej sprzedaży jako kurs online.

> **Platforma edukacyjna wokół tego kursu** (frontend React + Spring Boot serwujący go).
> Cały kod pod `_01_fundamentals` … `_31_spring_cloud_microservices` (opisany w
> `COURSE_CONTENT_HISTORY.md`) pozostaje nienaruszoną "podstawą programową" tej platformy — nie
> kasować, nie modyfikować przy pracy nad platformą. Pełna historia decyzji przy pisaniu SAMEGO
> kursu (chronologicznie, rozdział po rozdziale) jest w `COURSE_CONTENT_HISTORY.md` — czytaj ten
> plik tylko gdy trzeba zrozumieć/naprawić/rozszerzyć sam kod źródłowy lekcji Javy, nie przy
> pracy nad platformą.

## Struktura projektu (kurs Java — podstawa programowa)

Kod źródłowy: `src/main/java/com/example/javaquest/`

Każdy **rozdział** (duży blok kursu, odpowiednik osobnego kursu na Codenga, np. "Java - Kolekcje")
to osobny pakiet najwyższego poziomu: `_01_fundamentals`, `_02_oop`, `_03_collections`, itd. — 31
rozdziałów łącznie (`_01_fundamentals` … `_31_spring_cloud_microservices`), pełna lista i historia
każdego w `COURSE_CONTENT_HISTORY.md`.

Wewnątrz rozdziału każda **lekcja** to podpakiet `LessonXX_Temat` (numeracja od 00 lub 01).
W każdym podpakiecie lekcji są dokładnie **2 pliki**:

- `_LessonXX_Temat.java` — notatki teoretyczne. Jedna klasa, jedna metoda `main`, treść w blokach
  komentarzy `/* ... */` z nagłówkami (`===`, emoji jak 📦🔹🔍📌) i przykładami kodu do uruchomienia.
- `_Exercises_LessonXX_Temat.java` — dokładnie **30 ćwiczeń** jako statyczne klasy zagnieżdżone
  `Exercise01_NazwaXxx` … `Exercise30_NazwaXxx`, każda z:
  - komentarzem `/* 🧪 Zadanie N: ... */` opisującym zadanie (po polsku, konkretne dane wejściowe),
  - pustą metodą `public static void main(String[] args) { }` — **bez rozwiązania**, kursant sam pisze kod
    (ewentualnie ze znacznikiem `// TODO: twój kod tutaj`).

  Poziom trudności rośnie i jest oznaczony nagłówkami sekcji:
  - 🟢 POZIOM 1 – PODSTAWY (1-10)
  - 🟡 POZIOM 2 – ŚREDNI (11-20)
  - 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

Ta konwencja (2 pliki/lekcja, 30 ćwiczeń/plik) obowiązuje we wszystkich rozdziałach kursu. Cały
kurs (`_01`-`_31`) ma już komplet teorii + 30 ćwiczeń w każdej lekcji — patrz
`COURSE_CONTENT_HISTORY.md` dla szczegółów i pułapek każdego rozdziału.

## Platforma edukacyjna — architektura (aktualny, żywy projekt)

Backend Spring Boot (`com.example.javaquest.platform`, `com.example.javaquest.web`) serwuje
frontend React (`frontend/`) i API `/api/chapters/...` czytające treść lekcji z bazy H2
**in-memory** (resetuje się przy każdym restarcie — `ContentSeeder`/`LessonContentLoader` zasilają
ją od zera z `ChapterSeedData.java` + plików JSON pod `src/main/resources/content/<rozdział>/
<lekcja>.json`). Brak jeszcze modelu postępu użytkownika (Faza 4+) — nic w bazie nie jest trwałe
między restartami, więc migracje treści są bezpieczne (nie ma czego "zgubić").

Model danych (jeden, wspólny dla wszystkich lekcji):
- `Chapter` (slug/title/sortOrder), `Lesson` (slug/title/sortOrder, tytuł lekcji auto-generowany
  z slug przez `LessonSlugTitles.humanize()` — **nigdy nie dostał prawdziwej polskiej redakcji**,
  do rozważenia w Etapie 2/4 pracy nad jakością).
- `ContentBlock` (`type` z enuma `ContentBlockType`, `heading`, `body`, opcjonalny `code`) — blok
  teorii, kolejność przez `sortOrder`.
- `Exercise` (`prompt`/`hint`/`solution`), `QuizQuestion` (`question`/4 opcje/`correctOption`/`explanation`).

Frontend: `App.jsx` → `ChapterListPage` → `LessonListPage` → `LessonDetailPage` (3 zakładki:
Teoria/Zadania/Quiz, komponenty `TheoryView`/`ExercisesView`/`QuizView`). Jeden wspólny plik
`App.css` (motyw "ciepły industrialny", zmienne w `index.css`, jasny/ciemny przez
`prefers-color-scheme`). Build frontendu: `npm run build` w `frontend/` pisze **ręcznie** (brak
frontend-maven-plugin) prosto do `src/main/resources/static` (Spring Boot serwuje stąd `/`).

## Uruchamianie i weryfikacja

- **PowerShell** (nie Bash) do `mvnw.cmd` — `JAVA_HOME` nie jest ustawiony globalnie w tym
  środowisku: `$env:JAVA_HOME = "C:\Users\kapit\.jdks\openjdk-25.0.2"` na początku każdej sesji.
- Backend na porcie **8082** (8080 zajęty systemowo). Po starcie: pierwsza odpowiedź HTTP 200
  pojawia się ZANIM `ContentSeeder`/`LessonContentLoader` skończą ładować treść — trzeba odczekać
  dodatkowe ~40-70s po pierwszym 200, zanim `hasContent`/treść lekcji są wiarygodne.
- Weryfikacja zmiany treści/wyglądu: `mvnw.cmd compile` (backend) + `npm run build` (frontend,
  jeśli zmieniono komponenty) → restart backendu → `curl http://localhost:8082/api/chapters/
  <rozdział>/lessons` (sprawdź `hasContent:true` dla zmienionych lekcji) → sprawdź regresję na
  co najmniej jednym NIEZWIĄZANYM, wcześniej ukończonym rozdziale.
- **Testy automatyczne są bardzo skromne**: tylko `JavaQuestApplicationTests.contextLoads()`
  (czy kontekst Springa w ogóle startuje) — brak testów sprawdzających kształt/poprawność treści
  JSON, brak testów frontendu (tylko `oxlint` jako linter, żaden framework testowy). Weryfikacja
  jakości treści jest więc ręczna (patrz wyżej) — nie zakładaj istnienia testów, których nie ma.
- Znany, przedistniejący, niezwiązany błąd kompilacji: `src/test/java/.../_01_fundamentals/
  Training/StudentTest.java` używa `assert` jako nazwy zmiennej (słowo kluczowe od Javy 1.4) — nie
  naprawiać w ramach pracy nad platformą, poza zakresem.

## Inicjatywy w toku

- **Faza 1 platformy (treść JSON dla wszystkich 664 lekcji, wygenerowana z kursu Javy) —
  KOMPLETNA.** Pełna historia: `EDU_PLATFORM_PLAN.md`.
- **Etap 2 platformy (jakość merytoryczna + spójny wygląd lekcji) — W TOKU.** Docelowy standard
  i pełna instrukcja: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Aktualny stan prac, ostatnia czynność i
  następny krok: **`WORK_PROGRESS.md`** — czytaj TEN plik jako pierwszy punkt startowy przy
  komendzie "kontynuuj".
