# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1 (analiza) — zakończony. Etap 2 (standard + komponenty) — zakończony. Etap 3 (lekcja
wzorcowa) — zakończony. **Etap 4 (migracja rozdziałami) — w toku: rozdział `_02_oop` (15/15
lekcji) napisany, weryfikacja końcowa (restart+curl+regresja) w trakcie, jeszcze BEZ commita.**

## Ostatnia ukończona czynność

Przepisano teorię WSZYSTKICH 15 lekcji `_02_oop` (01_ClassesAndObjects … 15_DesignPatterns) na
nowy standard 11 sekcji (14-15 bloków/lekcję). Poprawiono polskie znaki w zadaniach i quizach
(pola narracyjne, nigdy `solution`/`code`) świadomym, kumulatywnym słownikiem
(`scratchpad/polish-dict.js`, ~200 pozycji). Znaleziono i naprawiono **poważny, systemowy błąd
z Fazy 1**: w 141 miejscach (10 z 15 plików `_02_oop`) słowa "constructor"/"toString"/"valueOf"
były zastąpione zserializowanym kodem źródłowym wbudowanych funkcji JS (np.
`"function toString() { [native code] }()"` zamiast `"toString()"`) — najpewniej bug w
oryginalnym skrypcie generującym ten rozdział w Fazie 1. Naprawiono mechanicznym, bezpiecznym
skryptem (`scratchpad/fix_native_code_corruption.js`) — w odróżnieniu od poprawek diakrytyków,
to była BEZPIECZNA globalna zamiana (wzorzec jednoznaczny, nigdy nie występuje legalnie w polskim
tekście ani w kodzie Java). Kilka plików miało zagnieżdżoną korupcję (2-3 warstwy) — skrypt trzeba
było uruchomić do skutku (aż do 0 wystąpień), nie tylko raz.

## Zmienione pliki (Etap 4, ten rozdział)

Wszystkie 15: `src/main/resources/content/_02_oop/01_ClassesAndObjects.json` …
`15_DesignPatterns.json` — pełna przebudowa teorii + poprawki diakrytyków + naprawa korupcji
"native code". Nowe pliki robocze w scratchpadzie (NIE w repo): `polish-dict.js` (współdzielony,
kumulatywny słownik), `fix_native_code_corruption.js`, `migrate_02oop_NN.js` (per lekcja).

## Wyniki testów / weryfikacji

- Liczniki policzone dla wszystkich 15 plików: teoria 14-15 / zadania 30 / quiz 100 — zgodne.
- `grep -c "native code"` na wszystkich 15 plikach → 0 wszędzie (potwierdzone finalnym sweepem).
- Restart backendu w toku w momencie zapisu tej notatki — **jeszcze NIE potwierdzono** przez
  `curl` że wszystkie 15 lekcji ładuje się bez błędu (`ContentBlockType.valueOf` dla nowych 12
  typów enuma) ani że nie ma regresji na innym rozdziale. TO JEST NASTĘPNY KROK.
- `mvnw.cmd compile` / `npm run build`: bez zmian od Etapu 2 (nie modyfikowano kodu Java/JSX w
  tej turze, tylko treść JSON) — nie wymaga ponownego uruchamiania w tym kroku.

## Problemy i decyzje

- Poprawka merytoryczna zastosowana konsekwentnie: "konstruktor to NIE metoda" (zamiast "to
  specjalna metoda") — znaleziona i naprawiona w Lesson01 i Lesson03 (jedyne miejsca, gdzie
  faktycznie występowała w treści quizu/teorii tego rozdziału).
- Tytuły lekcji nadal auto-generowane z slug (nierozwiązane, jak poprzednio odnotowano).
- **WAŻNE dla kontynuacji migracji innych rozdziałów**: PRZED założeniem, że plik jest wolny od
  bugu "native code", zawsze zrób `grep -c "native code"` z NIEescapowanym nawiasem (nie
  `"native code\]"` — w tej sesji escapowana wersja dała fałszywie "0" dla plików, które
  faktycznie miały korupcję; dokładna przyczyna tej rozbieżności nie została ustalona, ale
  NIEescapowany wzorzec `"native code"` okazał się wiarygodny). Sprawdź TAKŻE inne rozdziały
  kursu pod kątem tego samego bugu, jeśli/gdy migracja do nich dotrze — na razie potwierdzone
  TYLKO w `_02_oop`, ale nie zweryfikowano pozostałych 29 rozdziałów.

## Następny krok

1. Poczekaj na pełny start backendu (Tomcat + ContentSeeder/LessonContentLoader, ~90-100s).
2. `curl /api/chapters/_02_oop/lessons/<KAZDA-Z-15>/theory` — potwierdź, że każda lekcja ładuje
   pełną liczbę bloków (14-15) bez błędu enuma.
3. `curl /api/chapters/_20_spring_core/lessons` (regresja na niepowiązanym rozdziale) — potwierdź
   brak regresji.
4. Commit lokalny (bez trailera współautorstwa, bez push) — "Etap 4: rozdzial _02_oop wg nowego
   standardu 11 sekcji (teoria+diakrytyki+naprawa buga native-code) - rozdzial KOMPLETNY".
5. Zapytać użytkownika / kontynuować kolejny rozdział (`_01_fundamentals`, 17 lekcji) tym samym
   trybem, zachowując rygor: grep pod kątem "native code" jako PIERWSZY krok przy każdym kolejnym
   pliku, zanim założysz, że jest czysty.
