# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1-3 — zakończone. **Etap 4 (migracja rozdziałami) — w toku.**

**DYREKTYWA UŻYTKOWNIKA:** cały projekt (wszystkie 31 rozdziałów) musi zostać przejechany migracją
w całości. Użytkownik potwierdził priorytet ponownie 2026-09-09: wszystkie lekcje od lekcji 01 mają
być dostosowane do standardu, bez pytania o zgodę na kontynuację.

## Stan rozdziałów

- **`_01_fundamentals` (17/17 lekcji, 00-16) — KOMPLETNE wg AKTUALNEGO standardu (w tym
  API_REFERENCE tam, gdzie wymagane).** Lekcje 00-13 były już zmigrowane wcześniej; W TEJ SESJI
  dopisano brakujące katalogi API_REFERENCE do: `05_Arrays`, `06_StringsAndBuilder`,
  `07_DateAndTime`, `08_MathOperations`, `09_BigNumberTypes`, `12_BinaryAndHex`,
  `13_BitwiseOperators` (pełny katalog operatorów bitowych + formy złożone `<<= >>= >>>=`).
  Lekcje `14_GarbageCollector`, `15_RandomAndSecureRandom`, `16_Exceptions` były jeszcze w STARYM,
  7-blokowym formacie (CONCEPT/CODE_EXAMPLE) — w TEJ SESJI przepisane od zera na standard 11 sekcji
  (14 i 16 bez API_REFERENCE, celowo — to koncepcje/mechanizmy, nie klasy z wieloma metodami; 15
  MA pełny katalog API Random/SecureRandom). Exercises/quiz (30+100 na lekcję, już istniejące)
  zostały oczyszczone z nadużywania WIELKICH LITER jako emfazy (patrz `scratchpad/fix_allcaps*.js`
  niżej) — NIE dotknięto pól `solution` (prawdziwy kod Java).
  **JESZCZE DO ZROBIENIA dla tego rozdziału:** restart backendu + curl + regresja + commit
  (w trakcie, patrz "Następny krok").
- **`_02_oop` (15/15 lekcji) — sprawdzone pod kątem wymogu API_REFERENCE w TEJ SESJI, zgodnie z
  dyrektywą użytkownika.** Wynik przeglądu: 14/15 lekcji NIE wymagało zmian (czyste konstrukcje
  OOP, nie "klasa z wieloma metodami"; `13_Enums` ma już w istniejących sekcjach pełne pokrycie
  swojego małego, wbudowanego API — name/ordinal/values/valueOf — więc NIE dopisano osobnego
  bloku). `11_ObjectClass` WYMAGAŁO uzupełnienia — dopisano katalog API_REFERENCE z PEŁNYM
  pokryciem metod klasy `Object`: toString, equals, hashCode, getClass, clone, finalize (deprecated),
  wait/wait(long)/wait(long,int), notify/notifyAll. Reszta rozdziału NIE wymaga rewizji.
  **JESZCZE DO ZROBIENIA:** restart+curl+regresja+commit dla tej jednej zmiany.
- **`_03_collections` (23 lekcje) — JESZCZE NIE ROZPOCZĘTE.** Następny rozdział w kolejności.
  Wymaga NAJWIĘCEJ uwagi pod kątem API_REFERENCE (ArrayList/List/Map/Set/Stream mają dziesiątki
  metod każda) — pisać OD RAZU z pełnym katalogiem.
- **`_04_...` do `_31_...` — NIE ROZPOCZĘTE.** Migracja rozdziałami, po kolei, zgodnie z
  dyrektywą użytkownika (cały kurs, bez wyjątków).

## Nowe narzędzie tej sesji: automatyczna naprawa nadużywania WIELKICH LITER

Legacy treść (pisana przed dopisaniem zasady "nie nadużywaj wielkich liter" do instrukcji) miała
dziesiątki słów WPISANYCH WIELKIMI LITERAMI jako emfaza w polach `prompt`/`hint`/`question`/
`options`/`explanation` (NIGDY w `solution`/`code`). Napisano dwuetapowy skrypt Node (NIE w repo,
tylko w scratchpad sesji — rozważ skopiowanie do `scripts/` przy najbliższej okazji):
- `scratchpad/fix_allcaps.js <plik.json>` — główny przebieg, lowercase'uje słowa 3+ znakowe w
  CAŁOŚCI wielkimi literami (poza whitelistą prawdziwych akronimów: GC, JVM, API, HTTP...),
  zachowuje wielkość litery na początku zdania. Polish-aware tokenizacja (NIE regex `\b`, bo `\b`
  nie rozumie znaków spoza ASCII i ucina słowo przed/po znaku z ogonkiem).
- `scratchpad/fix_allcaps_residual.js <plik.json>` — druga tura, naprawia dwa typy artefaktów:
  sufiksy po apostrofie (`cache'OW` -> `cache'ow`) i krótkie polskie słowa funkcyjne pozostawione
  wielkimi literami (`TO`, `ZE`, `JA`, `NA`, `OD`, `CO`, `ZA`, `DO`, `PO`, `TA`, `TE`, `MA`).
  Celowo NIE dotyka pojedynczych liter (ryzyko skasowania typu generycznego `<T>` albo etykiety
  odpowiedzi A/B/C/D).
- Użyto na `14_GarbageCollector.json`, `15_RandomAndSecureRandom.json`, `16_Exceptions.json`.
  Warto uruchomić na KOLEJNYCH, jeszcze niezmigrowanych plikach, jeśli natrafisz na ten sam wzorzec
  (prawdopodobnie NIE wystąpi w `_03_collections`+, bo te lekcje będą pisane od zera wg nowego
  standardu, ale sprawdź `grep -lE "[A-ZĄĆĘŁŃÓŚŹŻ]{4,}" plik.json` jako szybki test).

## Ostatnia ukończona czynność

Dopisano brakujące `API_REFERENCE` do 7 lekcji `_01_fundamentals` (05-09,12,13), przepisano od
zera 3 lekcje w starym formacie (14-16), naprawiono nadużywanie wielkich liter w ich
exercises/quiz, uzupełniono `_02_oop/11_ObjectClass` o katalog metod `Object`. WSZYSTKIE pliki
zwalidowane jako poprawny JSON, sprawdzone pod kątem bugu "native code" (0 wszędzie).
W TRAKCIE: restart backendu (uruchomiony w tle) + curl weryfikacyjny — patrz "Następny krok".

## Wyniki testów / weryfikacji

- Wszystkie 17 plików `_01_fundamentals` (00-16) — poprawny JSON, `grep -c "native code"` = 0.
- Wszystkie 15 plików `_02_oop` — poprawny JSON, `grep -c "native code"` = 0.
- `mvnw.cmd compile` — OK (exit code 0), TYLKO ostrzeżenia JVM o native-access/Unsafe (nieszkodliwe,
  niezwiązane ze zmianami tej sesji).
- Backend wystartował (Tomcat na porcie 8082, log potwierdza "Started JavaQuestApplication").
- **JESZCZE DO ZROBIENIA:** curl na kilku lekcjach `_01_fundamentals` (sprawdzić liczbę bloków
  teorii = 14 lub 15 zależnie od API_REFERENCE) + `_02_oop/11_ObjectClass` (15 bloków) + regresja
  na `_02_oop/03_Constructors` (bez zmian, powinno dalej dawać 15 bloków) — PO tym dopiero commit.
- `npm run build` (frontend) — NIE URUCHOMIONO w tej sesji, bo NIE zmieniono żadnego pliku
  frontendu/Java w tej sesji (tylko dane JSON) — nie jest wymagany wg wzorca weryfikacji.

## Problemy i decyzje

- Bug "native code" — sprawdzony na WSZYSTKICH 32 plikach `_01_fundamentals`+`_02_oop` w tej sesji,
  wszędzie 0. Nie napotkano nowych wystąpień.
- Legacy lekcje (14-16 `_01_fundamentals`) miały ZUPEŁNIE INNY styl pisania niż obecny standard
  (masowe nadużywanie WIELKICH LITER) — rozwiązane przez dedykowany skrypt (patrz wyżej), NIE
  ręcznie linijka po linijce (nieopłacalne przy 30 exercises + 100 quiz na lekcję).
- `13_Enums` rozważona jako kandydat do API_REFERENCE, ale ODRZUCONA — jej wbudowane API
  (name/ordinal/values/valueOf) jest JUŻ w pełni pokryte w istniejących sekcjach 11-punktowych,
  dopisywanie osobnego bloku byłoby duplikacją.

## Następny krok (dokładnie, w kolejności)

1. **Poczekaj na gotowość backendu** (uruchomiony w tle, PowerShell, log w
   `$env:TEMP\javaquest-backend.log`) — Tomcat już wystartował, trwa dodatkowe oczekiwanie na
   załadowanie treści JSON do H2.
2. `curl` na: `_01_fundamentals/06_StringsAndBuilder` (oczekuj 15 bloków teorii),
   `_01_fundamentals/14_GarbageCollector` (14 bloków), `_02_oop/11_ObjectClass` (15 bloków),
   regresja na `_02_oop/03_Constructors` (15 bloków, bez zmian).
3. Jeśli wszystko OK: `git add` + lokalny commit (PO POLSKU, BEZ stopki Co-Authored-By/linku do
   sesji — zgodnie z sekcją "Zasady commitowania" w `STAGE2_LESSON_REDESIGN_PROMPT.md`, która
   NADPISUJE domyślną stopkę atrybucji dla tych commitów) obejmujący WSZYSTKIE zmiany
   `_01_fundamentals` + `_02_oop/11_ObjectClass` + `WORK_PROGRESS.md`. NIE `git push`.
4. Przejdź do `_03_collections` (23 lekcje, jeszcze nie zaczęte) — pisz KAŻDĄ lekcję OD RAZU wg
   pełnego standardu 11 sekcji + API_REFERENCE (List/ArrayList/Map/Set/Stream mają dziesiątki
   metod każda, to będzie najbardziej pracochłonny rozdział jak dotąd). Po ukończeniu paczki lekcji
   (lub całego rozdziału) — kompilacja/build jeśli dotyczy, restart+curl+regresja+commit.
5. Kontynuuj rozdziałami `_04_...` do `_31_...` w kolejności, bez pomijania żadnego, zgodnie z
   dyrektywą użytkownika z Etapu 4.
