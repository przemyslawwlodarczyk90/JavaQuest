# Mastery V1 — raport z analizy i plan implementacji

> Status: **ZAIMPLEMENTOWANE (2026-10-02)** — użytkownik polecił wdrożyć plan bez czekania na akceptację.
> Odstępstwa od planu: `GET /api/mastery` bez `?track=` zwraca wszystkie tory (frontend ładuje
> mastery raz po zalogowaniu); test integracyjny powstał zgodnie z planem na `@SpringBootTest` + H2.
> Data analizy: 2026-10-02, gałąź `visual-aplication-branch`.
> Wszystkie fakty o obecnym systemie zweryfikowane w kodzie (ścieżki plików podane przy każdym punkcie).

Cel: system progresji nastawiony na **długoterminowe przyswajanie wiedzy** (retrieval practice +
spacing + mastery), oparty wyłącznie na quizach. Użytkownik widzi **stan wiedzy** (★★☆), nigdy
algorytmu (żadnych dat, terminów, liczników).

---

## 1. Jak obecnie działa progress użytkownika

- Encja `LessonProgress` (`platform/progress/LessonProgress.java`), tabela `lesson_progress`,
  unikalność `(user_id, chapter_slug, lesson_slug)`. Istnienie wiersza = „lekcja odhaczona”.
- Ustawiany **ręcznie checkboxem** (`PUT /api/chapters/{c}/lessons/{l}/progress`, idempotentne),
  czytany zbiorczo `GET /api/progress` (`ProgressController`).
- Lekcje wskazywane są **kluczami naturalnymi (slugami)**, nie FK — bo `ContentReset` czyści i
  zasiewa od nowa tabele treści (`chapters`, `lessons`, `quiz_questions`, …) przy każdym starcie.
  Tabele użytkownika (`app_user`, `lesson_progress`, `quiz_attempts`, `quiz_attempt_questions`) są
  trwałe (`ddl-auto=update`, PostgreSQL; profil `h2` w pamięci).
- Frontend: `ProgressContext.jsx` trzyma zbiór `chapter/lesson`, liczy „Zrobione X / Y” na liście
  rozdziałów (`ChapterListPage.jsx`) i lekcji (`LessonListPage.jsx`), checkbox w `LessonDetailPage.jsx`.
- **Nie ma XP, punktów, gwiazdek, streaków ani leaderboardu.** Checkbox to deklaracja użytkownika —
  nie jest w żaden sposób weryfikowany.

## 2. Jak obecnie działa quiz

Moduł `platform/quiz` — **losowanie i ocena wyłącznie na serwerze**, z pełną historią podejść:

- `QuizAttempt` (`quiz_attempts`): user, chapterSlug, lessonSlug, `createdAt`, `finishedAt`,
  `correctCount`, `passed`. `QuizAttemptQuestion` (`quiz_attempt_questions`): pozycja, numer pytania
  w lekcji (`questionNo` = `sortOrder` — klucz naturalny), `selectedOption`, `correct`.
- `POST …/quiz/attempts` — **wznawia** otwarte podejście, a jeśli go nie ma, losuje nowe (podwójny
  klik nie losuje dwa razy; nie da się „przerzucić” losowania, żeby uniknąć trudnych pytań).
- `QuizRules.draw` — max `platform.quiz.draw-size` (=20) pytań, **najpierw niewidziane** przez
  użytkownika, potem uzupełnienie widzianymi; kolejność zawsze przetasowana.
- `POST …/attempts/{id}/answers` — odpowiedź na jedno pytanie; **nie da się jej zmienić** (409),
  poprawna odpowiedź + wyjaśnienie wracają dopiero po odpowiedzi (feedback natychmiastowy). Po
  ostatnim pytaniu serwer liczy wynik: zaliczenie od `ceil(80% · n)` (`platform.quiz.pass-percent`).
- Pule pytań (zmierzone w `src/main/resources/content`): **Java 760 lekcji, 25–100 pytań (mediana
  100)**; **JS 90 lekcji, 100–109**; **Linux 437 lekcji, na dziś 0 pytań** (kurs w budowie).
- Brak kar za błędy, quiz można powtarzać bez limitu — to już jest zgodne z „retrieval practice”.
- Czas: `LocalDateTime.now()` wołane **bezpośrednio w encjach** — nie ma wstrzykiwalnego `Clock`.

## 3. Jak obecnie działa blokowanie lekcji/rozdziałów

**Nie ma żadnego blokowania.** `ChapterController` zwraca wszystkie rozdziały toru
(`?track=JAVA|JAVASCRIPT|LINUX`) i wszystkie lekcje rozdziału; frontend linkuje każdą lekcję.
Kolejność wynika z `sort_order` (z `ChapterSeedData`). To dokładnie model „baza wiedzy” — V1 go
**zachowuje** i dokłada tylko miękkie podpowiedzi (patrz §7.6).

## 4. Klasy backendu dotknięte zmianą

| Klasa | Zmiana |
|---|---|
| `platform/quiz/QuizAttempt.java` | czas z parametru (`Clock`), `@Version` (ochrona przed równoległym zakończeniem) |
| `platform/quiz/QuizService.java` | `Clock`; po zakończeniu podejścia zwraca mastery przed/po; status quizu z mastery; losowanie z pulą „wcześniej błędnych” |
| `platform/quiz/QuizRules.java` | `draw` z dodatkowym koszykiem pytań wcześniej błędnych |
| `platform/quiz/QuizAttemptRepository.java` | zapytania: zaliczone podejścia użytkownika (projekcja slug+czas), ostatnia odpowiedź per pytanie |
| `platform/quiz/QuizController.java` | bez zmian kontraktu wejścia; odpowiedzi wzbogacone o `mastery` |
| `web/JavaQuestApplication.java` lub nowa konfiguracja | bean `Clock` + `@EnableConfigurationProperties` |
| `src/main/resources/javaquest-platform.properties` | sekcja `platform.mastery.*` |
| `platform/progress/*` | **bez zmian** (checkbox zostaje niezależny) |

## 5. Komponenty frontendu dotknięte zmianą

| Plik | Zmiana |
|---|---|
| `frontend/src/api.js` | `getMastery(track)`, `getReviews(track)` |
| `frontend/src/ProgressContext.jsx` | dodatkowo mapa mastery (`chapter/lesson → {stars, reviewSuggested}`), odświeżana po quizie |
| `frontend/src/pages/LessonListPage.jsx` | gwiazdki przy lekcji, „Warto powtórzyć”, znacznik „Proponowana następna” |
| `frontend/src/pages/LessonDetailPage.jsx` | gwiazdki obok tytułu lekcji |
| `frontend/src/components/QuizView.jsx` | ekran wyniku: komunikat o mastery (bez dat) |
| `frontend/src/pages/ChapterListPage.jsx` | (opcjonalnie) „opanowane ★★★: X / Y” na kafelku |
| `frontend/src/App.css` | style gwiazdek (statyczne, bez animacji) |

Uwaga: `api.js`, `QuizView.jsx`, `App.css`, `App.jsx` mają **niezacommitowane zmiany użytkownika**
(reset hasła, kod w pytaniu). Implementacja nałoży się na nie ostrożnie, nie nadpisując ich.

## 6. Minimalny model danych — STAN vs HISTORIA

**Decyzja: wariant B (historia), bez nowej tabeli.** Historia już istnieje: `quiz_attempts` +
`quiz_attempt_questions` zapisują każde podejście, jego czas zakończenia, wynik i każdą odpowiedź.
To jest dokładnie strumień zdarzeń „udane retrieval practice”.

Mastery jest **czystą funkcją** tej historii:

```
mastery(user, lekcja, now) = MasteryPolicy.replay(czasy zakończenia ZALICZONYCH podejść, now)
```

Dlaczego tak, a nie osobna tabela `lesson_mastery` ze stanem:

- **zero nowych tabel i zero migracji** — istniejące zaliczenia od razu „liczą się” wstecz;
- **zmiana algorytmu/interwałów = zmiana konfiguracji**, bez przeliczania danych w bazie;
- **nie da się przyznać gwiazdki dwa razy** — nie ma zapisu „przyznaj”, który mógłby się powtórzyć;
  jedno podejście = jedno zdarzenie, a funkcja jest deterministyczna i idempotentna;
- analiza skuteczności (kiedy naprawdę były powtórki, jak wypadały) jest możliwa z tych samych danych;
- brak schedulera — starzenie liczone przy odczycie.

Koszt: przy odczycie listy lekcji serwer czyta zaliczone podejścia użytkownika (projekcja
`chapter_slug, lesson_slug, finished_at` — kilka bajtów na wiersz; realnie setki–kilka tysięcy
wierszy na użytkownika) i przelicza w pamięci. Jeśli kiedyś okaże się to wąskim gardłem, dokładamy
tabelę-cache `lesson_mastery`, **odtwarzalną w 100% z historii** — bez zmiany semantyki.

Świadomy efekt uboczny: zmiana interwałów działa retroaktywnie na wszystkich (to cecha — jeden
spójny model), co trzeba pamiętać przy ewentualnym zaostrzaniu reguł.

**Relacja:** `User 1—* QuizAttempt (chapter_slug, lesson_slug)` → `Lesson (chapter.slug, slug)` →
`Chapter.track (JAVA|JAVASCRIPT|LINUX)`. Mastery = f(User, Lesson). Tor nie jest zapisywany przy
podejściu — wynika z rozdziału (slugi rozdziałów są globalnie unikalne: `_01_…`, `_js_…`, `_lx_…`).

## 7. Algorytm mastery V1

### 7.1 Pojęcia

- **zdarzenie** = zakończone, **zaliczone** podejście do quizu lekcji (`passed = true`), z czasem
  `finishedAt`. Niezaliczone podejścia są zapisane (historia, analiza, pytania błędne), ale **nie
  zmieniają mastery ani w górę, ani w dół**.
- `level` ∈ {0,1,2,3}, `lastConfirmedAt` — czas ostatniego zdarzenia, które **zostało policzone**.
- Quiz można rozwiązywać **zawsze**. Mastery nie musi rosnąć po każdym podejściu.

### 7.2 Starzenie (efektywny poziom w chwili `t`)

```
effective(level, lastConfirmedAt, t):
  age = t - lastConfirmedAt
  L = level
  while L >= 2 and age > retention(L):
      age -= retention(L)      # spadek o jeden poziom, kolejny poziom „żyje” od momentu spadku
      L -= 1
  return L                     # ★ jest podłogą — nie spada do 0
```

### 7.3 Przetwarzanie zdarzenia (zaliczony quiz w chwili `t`)

```
L = effective(level, lastConfirmedAt, t)
if L == 0:                                  → level = 1, lastConfirmedAt = t      (pierwsze ★)
elif t - lastConfirmedAt >= spacing(L):     → level = min(3, L+1), lastConfirmedAt = t
else:                                       → bez zmian (za wcześnie — quiz zapisany, mastery nie)
```

Przy `L == 3` warunek spacingu oznacza „odświeżenie”: poziom zostaje ★★★, ale `lastConfirmedAt`
przesuwa się na `t` — wiedza potwierdzona, zegar starzenia od nowa.

Zaliczenie „za wcześnie” **nie** przesuwa `lastConfirmedAt` — inaczej ktoś, kto powtarza quiz co
godzinę, w nieskończoność odsuwałby sobie moment zdobycia kolejnej gwiazdki.

### 7.4 „Warto powtórzyć” (bez dat)

`reviewSuggested = level ≥ 1 && t - lastConfirmedAt >= spacing(effective)` — czyli **zaliczenie quizu
TERAZ zmieniłoby stan** (dałoby gwiazdkę, przywróciło utraconą albo odświeżyło ★★★). Flaga jest
binarna — UI pokazuje neutralne „Warto powtórzyć”, nigdy „za ile”.

### 7.5 Zwrot do frontendu

```json
{ "chapterSlug": "_03_collections", "lessonSlug": "08_HashMap", "stars": 2, "reviewSuggested": false }
```

Żadnych pól czasowych w żadnym DTO. Wynik quizu dostaje `masteryBefore` / `masteryAfter` (liczby
gwiazdek), żeby ekran wyniku mógł powiedzieć „Zdobywasz gwiazdkę!” albo „Wiedza potwierdzona —
kolejne gwiazdki zdobywa się powtórkami po przerwie” (zdanie ogólne, bez terminu).

### 7.6 Miękka progresja zamiast blokad

- Wszystkie rozdziały i lekcje **pozostają otwarte** (jak dziś). Zero hard-locków.
- Lista lekcji oznacza **„Proponowana następna”**: pierwsza lekcja rozdziału (wg `sort_order`) z 0 ★.
  Czysto frontendowe, liczone z mastery.
- Prerequisites między rozdziałami: **nie w V1** (nie ma takich danych w modelu; dołożenie ich to
  osobny projekt treściowy). Kolejność rozdziałów = rekomendacja.

### 7.7 Checkbox „zrobione” vs mastery

Dwie **niezależne** informacje: checkbox = deklaracja „przerobiłem materiał” (zostaje bez zmian, bez
migracji), gwiazdki = zweryfikowana quizem, starzejąca się wiedza. Ćwiczenia programistyczne **nie
dają** mastery (brak automatycznej weryfikacji) — zostają materiałem.

## 8. Interwały i progi V1 — propozycja i uzasadnienie

| Parametr | Wartość | Dlaczego |
|---|---|---|
| `platform.quiz.draw-size` | **20** (bez zmian) | przy pulach 25–100 pytań daje 4–5 rozłącznych zestawów; 20 pytań to ~10 min — rzetelny pomiar, a nie męczarnia |
| `platform.quiz.pass-percent` | **80** (bez zmian) | klasyczny próg mastery learning (80–90%); 16/20 dopuszcza 4 pomyłki przy pytaniach-pułapkach |
| `platform.mastery.min-questions` | **10** | quiz z mniejszej puli nie daje gwiazdek (zbyt łatwo zgadnąć/zapamiętać); dziś dotyczy tylko lekcji Linux bez pytań; lekcje Java mają ≥25 |
| `spacing(★→★★)` | **20 h** | „następnego dnia” — sen konsoliduje pamięć; 20 h zamiast 24 h, żeby nauka o 20:00 i powtórka nazajutrz o 18:00 nie była „za wcześnie” (użytkownik nie widzi zegara, nie może się do niego dopasować) |
| `spacing(★★→★★★)` | **6 dni** | „za tydzień” z tym samym marginesem; ★★★ wymaga więc min. ~7 dni od pierwszego zaliczenia i trzech udanych odtworzeń |
| `spacing(★★★→odświeżenie)` | **30 dni** | powtórka ★★★ ma sens dopiero gdy zaczyna się zapominanie; częściej = farmienie bez wartości |
| `retention(★★★)` | **60 dni** | ★★★ bez powtórki przez 2 miesiące → ★★ |
| `retention(★★)` | **30 dni** | dalsze 30 dni → ★ |
| `retention(★)` | **∞ (podłoga)** | 0 znaczy „nigdy nie potwierdzone”; raz zaliczona lekcja nie znika do zera — inaczej kurs z 1300 lekcji „gnije” do pustej planszy i demotywuje |

Kolejność retention > spacing jest zachowana na każdym poziomie (z ★★ na ★★★ jest 6 dni okna przed
24 dniami zapasu do spadku), więc regularny uczeń nigdy nie traci gwiazdek „pomiędzy” powtórkami.

Wszystko w **jednym miejscu**: `MasteryProperties` (`@ConfigurationProperties("platform.mastery")`)
z wartościami domyślnymi i wpisami w `javaquest-platform.properties`, np.:

```properties
platform.mastery.min-questions=10
platform.mastery.spacing.level1=20h
platform.mastery.spacing.level2=6d
platform.mastery.spacing.level3=30d
platform.mastery.retention.level2=30d
platform.mastery.retention.level3=60d
```

Walidacja przy starcie (`@Validated`): spacing > 0, `retention(L) > spacing(L)` — błędna konfiguracja
nie wystartuje aplikacji zamiast po cichu łamać model.

## 9. Starzenie bez schedulera

Nic w bazie nie jest „zmniejszane”. `effective()` liczy poziom deterministycznie z
`lastConfirmedAt` i `now` (z wstrzykiwanego `Clock`). Brak background jobów, brak wyścigów job vs
quiz, test = podanie innego `Clock`/`now`.

## 10. Odzyskanie utraconego mastery

Przykład: ★★★ (ostatnie potwierdzenie dzień 0), brak nauki → dzień 61: efektywnie ★★,
„Warto powtórzyć”. Użytkownik zalicza quiz w dniu 61: `effective = 2`, odstęp 61 d ≥ 6 d → **★★★**,
`lastConfirmedAt = 61`. Jedna udana powtórka przywraca jeden poziom — utracona wiedza wraca
szybciej niż przy pierwszej nauce (nie trzeba czekać tygodnia), ale tylko przez realne odtworzenie.
Spadek do ★ (dzień 91+) → potrzebne dwie powtórki w odstępie ≥ 20 h, żeby wrócić do ★★★… dokładniej:
★ → ★★ (od razu, bo odstęp od ostatniego potwierdzenia jest ogromny), ★★ → ★★★ po kolejnych ≥ 6 dniach.

## 11. Ochrona przed farmieniem i manipulacją

| Zagrożenie | Ochrona |
|---|---|
| ★★★ przez 3× ten sam quiz pod rząd | spacing 20 h / 6 d — zaliczenia „za wcześnie” nie liczą się (§7.3) |
| przesuwanie terminu przez spam quizów | zaliczenie za wcześnie nie rusza `lastConfirmedAt` |
| zapamiętanie kolejności/odpowiedzi | już jest: losowanie z puli ~100, najpierw niewidziane, tasowanie pozycji; V1 dodaje koszyk pytań wcześniej błędnych |
| przerzucanie losowania | już jest: otwarte podejście zawsze wznawiane, nie da się go porzucić |
| zmiana odpowiedzi / „przeklikanie” ABCD | już jest: odpowiedź nieodwracalna (409) |
| podrobiony wynik z klienta | już jest: klient wysyła tylko literę; wynik liczy serwer; poprawna odpowiedź ujawniana po fakcie |
| cudze dane | już jest: wszystko po `Authentication`, `findByIdAndUser` |
| podwójne wysłanie ostatniej odpowiedzi (równoległe requesty) | **nowe**: `@Version` na `QuizAttempt` — drugi commit dostaje `OptimisticLockException` → 409; a nawet gdyby dwa podejścia się zaliczyły, model zdarzeniowy policzy je jako jedno potwierdzenie (drugie jest „za wcześnie”) |
| przypadkowe przyznanie mastery dwa razy | niemożliwe z konstrukcji — mastery nie jest zapisywane, tylko wyliczane |
| mała pula = zgadywanie | `min-questions` (podejścia z < 10 pytaniami nie są zdarzeniami mastery) |

Pytania wcześniej błędne (V1, prosto): pytanie jest „do poprawy”, jeśli **ostatnia** odpowiedź
użytkownika na nie była błędna. `QuizRules.draw` bierze najpierw do `platform.quiz.retry-wrong-max`
(**5**) takich pytań, resztę jak dziś (niewidziane, potem widziane). Dzięki limitowi quiz nie
zamienia się w „same moje błędy”, a retrieval słabych punktów następuje naturalnie.

## 12. Kompatybilność z obecnym progressem

- `lesson_progress` — **bez zmian**, nie migrujemy, nie łączymy z mastery (patrz §7.7).
- `quiz_attempts` — bez zmian schematu poza kolumną `version` (dodawana przez `ddl-auto=update`
  jako nullable; istniejące wiersze dostaną `0` jednorazowym `UPDATE … WHERE version IS NULL` w
  komponencie startowym, wzorem `CourseTrackConstraintSync`). **Nic nie jest kasowane.**
- Istniejące zaliczone podejścia **od razu dają mastery** wg algorytmu (to prawdziwe, serwerowo
  ocenione odtworzenia — uczciwie je liczyć).
- **Żadnej destrukcyjnej migracji.**

## 13. Endpointy

| Metoda | Ścieżka | Opis |
|---|---|---|
| `GET` | `/api/mastery?track=JAVA` | mastery wszystkich lekcji toru, które mają ≥ 1 ★ (reszta = 0) |
| `GET` | `/api/mastery/reviews?track=JAVA` | lekcje z `reviewSuggested`, najpierw te po spadku poziomu (tani dodatek: ten sam przelicznik + filtr) |
| `GET` | `/api/chapters/{c}/lessons/{l}/quiz` | **zmiana**: + `mastery {stars, reviewSuggested}` |
| `POST` | `…/quiz/attempts/{id}/answers` | **zmiana**: `result` + `masteryBefore`, `masteryAfter` |

Pytania błędne dla przyszłego trybu powtórek: dane są (zapytanie z §11), endpoint **nie w V1**.

## 14. Testy

Czas wyłącznie przez `Clock` / jawne `Instant` — żaden test nie zależy od realnego zegara.

**`MasteryPolicyTest`** (czysta logika, bez Springa — wzorem `QuizRulesTest`):
1. pierwsze zaliczenie → ★
2. drugie zaliczenie godzinę później → nadal ★ (próba ponownego zdobycia natychmiast)
3. zaliczenie po ≥ 20 h → ★★; po kolejnych ≥ 6 d → ★★★
4. 3 zaliczenia w ciągu jednego dnia → ★ (anty-farm)
5. zaliczenie za wcześnie nie przesuwa `lastConfirmedAt` (późniejsze zaliczenie nadal awansuje)
6. starzenie: ★★★ po 60 d+ε → ★★, po 90 d+ε → ★, nigdy 0
7. granice: dokładnie `retention` → bez spadku; `+1 s` → spadek
8. odzyskanie: ★★★ → (61 d) ★★ → zaliczenie → ★★★
9. odświeżenie ★★★ po ≥ 30 d resetuje zegar starzenia; po < 30 d — nie
10. `reviewSuggested` true/false w każdym stanie
11. niezaliczone podejścia ignorowane (filtr na wejściu)

**`QuizRulesTest`** (rozszerzenie): koszyk błędnych — limit `retry-wrong-max`, brak duplikatów,
pytania błędne usunięte z puli nie są losowane.

**`MasteryServiceTest`** (Mockito na repozytoriach, stały `Clock`):
- dwóch użytkowników — niezależny progress,
- dwie lekcje tego samego użytkownika — niezależne,
- tory JAVA / JAVASCRIPT / LINUX — filtr `?track` zwraca tylko swoje lekcje,
- `min-questions`: podejście z puli < 10 pytań nie daje ★.

**`QuizMasteryIntegrationTest`** (`@SpringBootTest`, profil `h2`, `@MockBean`/`@Primary` `MutableClock`):
- pełny przepływ start → 20 odpowiedzi → ★; niezdany quiz → bez zmiany; ponowna próba → nowe losowanie,
- ponowne wysłanie odpowiedzi na to samo pytanie → 409, mastery bez zmian,
- przesunięcie zegara o 21 h → zaliczenie → ★★.

Ryzyko: kontekst platformy nigdy nie był podnoszony w teście (jedyny `@SpringBootTest` to
`JavaQuestApplicationTests` w pakiecie `web`, wymaga Postgresa). Jeśli kontekst na H2 okaże się
kosztowny do postawienia (wspólny classpath z 31 rozdziałami kursu), test integracyjny zostanie
zastąpiony `@DataJpaTest` + `QuizService` złożonym ręcznie — zgłoszę to przed zmianą podejścia.

## 15. XP, streaki, leaderboard

- **XP: nie w V1.** Dziś nie daje wartości edukacyjnej ponad mastery, a dokłada drugi licznik do
  zrozumienia i pokusę farmienia. Historia podejść jest kompletna, więc XP można kiedyś **wyliczyć
  wstecz** (np. „liczba zaliczonych powtórek”) bez utraty danych — i wtedy nigdy nie wygasa.
- Streak, leaderboard, odznaki, animacje — **nie** (zgodnie z założeniami).

---

## PROPOSED V1

```
Lekcja (np. _03_collections / 08_HashMap)

 ☆☆☆ ──(1)──▶ ★☆☆ ──(2)──▶ ★★☆ ──(3)──▶ ★★★ ◀──(4)── odświeżenie
                ▲            │  ▲          │
                │           (5) │         (5)
                │            ▼  │          ▼
                └─────────── ★☆☆ └──(6)─── ★★☆
                (podłoga)        (powtórka)
```

| # | Zdarzenie | Warunek | Efekt |
|---|---|---|---|
| 1 | zaliczony quiz (≥ 16/20) | brak wcześniejszych potwierdzeń | ★ ; zegar = teraz |
| 2 | zaliczony quiz | ★, od ostatniego potwierdzenia ≥ 20 h | ★★ ; zegar = teraz |
| 3 | zaliczony quiz | ★★, od ostatniego potwierdzenia ≥ 6 d | ★★★ ; zegar = teraz |
| 4 | zaliczony quiz | ★★★, od ostatniego potwierdzenia ≥ 30 d | ★★★ ; zegar = teraz (odświeżenie) |
| 5 | **upływ czasu** (bez zapisu w bazie) | ★★★ > 60 d / ★★ > 30 d od ostatniego potwierdzenia (kaskadowo) | −1 poziom; nigdy poniżej ★ |
| 6 | zaliczony quiz po spadku | dowolny poziom ≥ ★, odstęp ≥ spacing | +1 poziom (powrót) |
| — | zaliczony quiz „za wcześnie” | odstęp < spacing | zapisany jako podejście; **mastery bez zmian**, zegar bez zmian |
| — | niezaliczony quiz | — | zapisany; feedback + wyjaśnienia; **mastery bez zmian**, nic nie tracisz |

Użytkownik widzi: `HashMap  ★★☆` oraz ewentualnie `Warto powtórzyć`. Nigdy datę ani licznik.

## FILES TO MODIFY

- `src/main/java/com/example/javaquest/platform/quiz/QuizAttempt.java`
- `src/main/java/com/example/javaquest/platform/quiz/QuizService.java`
- `src/main/java/com/example/javaquest/platform/quiz/QuizRules.java`
- `src/main/java/com/example/javaquest/platform/quiz/QuizAttemptRepository.java`
- `src/main/resources/javaquest-platform.properties`
- `src/test/java/com/example/javaquest/platform/quiz/QuizRulesTest.java`
- `frontend/src/api.js`
- `frontend/src/ProgressContext.jsx`
- `frontend/src/pages/LessonListPage.jsx`
- `frontend/src/pages/LessonDetailPage.jsx`
- `frontend/src/pages/ChapterListPage.jsx` (opcjonalnie)
- `frontend/src/components/QuizView.jsx`
- `frontend/src/App.css`
- `README.md` (sekcja o mastery)

## FILES TO ADD

- `platform/mastery/MasteryProperties.java` — jedyne miejsce z interwałami/progami
- `platform/mastery/MasteryPolicy.java` — czysta funkcja replay + decay (bez Springa)
- `platform/mastery/MasteryService.java` — czyta historię, filtruje po torze, liczy dla `now(clock)`
- `platform/mastery/MasteryController.java` — `GET /api/mastery`, `GET /api/mastery/reviews`
- `platform/PlatformClockConfig.java` (lub w istniejącej konfiguracji) — bean `Clock`
- `platform/quiz/QuizAttemptVersionBackfill.java` — jednorazowe `version = 0` dla starych wierszy
- `frontend/src/components/MasteryStars.jsx` — ★★☆ + aria-label
- testy: `MasteryPolicyTest`, `MasteryServiceTest`, `QuizMasteryIntegrationTest`

## IMPLEMENTATION ORDER

1. **Clock** — bean `Clock`, `QuizAttempt`/`QuizService` biorą czas z niego (bez zmiany zachowania). Testy istniejące zielone.
2. **MasteryProperties + MasteryPolicy + MasteryPolicyTest** — sama logika, 100% testów jednostkowych.
3. **Zapytanie o historię + MasteryService + MasteryServiceTest** (użytkownicy, lekcje, tory, min-questions).
4. **MasteryController** (`/api/mastery`, `/api/mastery/reviews`) + wzbogacenie `QuizStatus` i `AnswerResponse`.
5. **`@Version` + backfill** — ochrona przed równoległym zakończeniem, test 409.
6. **Koszyk pytań błędnych w `QuizRules.draw`** + testy.
7. **Test integracyjny** przepływu z przesuwanym zegarem.
8. **Frontend**: `MasteryStars`, kontekst, lista lekcji („Warto powtórzyć”, „Proponowana następna”), detal, ekran wyniku quizu.
9. **README / dokumentacja** — opis modelu dla użytkownika i dewelopera.

Każdy krok = osobny commit, aplikacja działa po każdym z nich.

Najważniejsze decyzje (do ewentualnej korekty — każda to zmiana konfiguracji albo jednej klasy):
(a) mastery wyliczane z historii podejść, bez nowej tabeli; (b) ★ jako podłoga starzenia;
(c) interwały 20 h / 6 d / 30 d i retencja 30 d / 60 d; (d) brak XP w V1; (e) koszyk 5 pytań błędnych.
