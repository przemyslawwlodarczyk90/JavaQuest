# WORK_PROGRESS — Etap 2 platformy JavaQuest (przebudowa jakości i wyglądu lekcji)

> Ten plik jest JEDYNYM miejscem do sprawdzenia stanu prac. Nadpisywany, nie dziennik. Pełna
> instrukcja/standard: `STAGE2_LESSON_REDESIGN_PROMPT.md`. Trwałe fakty o projekcie: `CLAUDE.md`.

## Aktualny etap

Etap 1-3 — zakończone. **Etap 4 (migracja rozdziałami) — w toku.** `_02_oop` (15/15) KOMPLETNY
i scommitowany. `_01_fundamentals`: lekcje 00-13 z 17 napisane wg standardu 11 sekcji,
zweryfikowane (restart+curl+regresja) i **scommitowane** — ALE patrz WAŻNA ZMIANA WYMAGAŃ niżej:
te lekcje wymagają jeszcze DODATKOWEGO przeglądu pod nowym kątem (pełne pokrycie API), zanim
rozdział zostanie uznany za w pełni zgodny z aktualnym standardem.

## WAŻNA ZMIANA WYMAGAŃ (2026-09-08, późno w sesji) — PRZECZYTAJ PRZED KONTYNUACJĄ

Użytkownik dodał nowy, obowiązkowy wymóg do `STAGE2_LESSON_REDESIGN_PROMPT.md` (sekcja "Pełne
pokrycie API dla lekcji o narzędziu z wieloma metodami"): lekcje ucząсe KONKRETNEJ klasy/API z
wieloma metodami (String, Math, Arrays, Random, LocalDate/Time, BigInteger/BigDecimal, Integer i
inne wrappery, kolekcje w przyszłych rozdziałach...) MUSZĄ wymieniać i pokazywać WSZYSTKIE
istotne w praktyce metody tego narzędzia, nie tylko podzbiór z oryginalnego materiału źródłowego.
Dodano do tego nowy typ bloku **`API_REFERENCE`** (📚 "Katalog metod") w `ContentBlockType.java`
+ `TheoryView.jsx` + `App.css` — już zaimplementowany, skompilowany, zbudowany (backend
`mvnw.cmd compile` i frontend `npm run build` obie OK), **scommitowany** (`66fc5c4`).

**NASTĘPNY KROK (dosłownie pierwsza rzecz do zrobienia po "kontynuuj"): przejrzeć WSZYSTKIE już
napisane lekcje (najpierw `_01_fundamentals` 00-13, potem CAŁY `_02_oop`) pod kątem tego nowego
wymogu i dopisać brakujące katalogi metod (blok `API_REFERENCE`) tam, gdzie potrzeba, ZANIM
przejdziesz do nowych, niezmigrowanych lekcji.**

Użytkownik wprost wskazał jako wymagające poprawki:
- **`_01_fundamentals/06_StringsAndBuilder.json`** — brakuje pełnego katalogu metod String
  (length, charAt, isEmpty, isBlank, equals, equalsIgnoreCase, compareTo, compareToIgnoreCase,
  contains, startsWith, endsWith, indexOf, lastIndexOf, substring, trim, strip, stripLeading,
  stripTrailing, toLowerCase, toUpperCase, replace, replaceAll, replaceFirst, split, join,
  repeat, toCharArray, format, valueOf, concat, matches, chars, lines, intern, hashCode) ORAZ
  StringBuilder (append, insert, delete, deleteCharAt, replace, reverse, setLength, setCharAt,
  charAt, length, capacity, indexOf, substring).
- **`_01_fundamentals/08_MathOperations.json`** — brakuje pełnego katalogu metod Math (abs, max,
  min, pow, sqrt, cbrt, sin/cos/tan/asin/acos/atan/atan2, toRadians/toDegrees, ceil, floor,
  round, rint, log, log10, exp, random, PI, E, floorDiv, floorMod, addExact/subtractExact/
  multiplyExact — overflow-checking, signum, hypot, copySign).

Dodatkowo, na podstawie własnej analizy tej sesji, kandydaci do tego samego przeglądu (lekcje
"o narzędziu z metodami", NIE lekcje o czystych konstrukcjach językowych):
- `05_Arrays.json` — katalog metod klasy `Arrays` (sort, copyOf, copyOfRange, equals, deepEquals,
  fill, binarySearch, asList, deepToString, stream, hashCode).
- `07_DateAndTime.json` — katalog metod LocalDate/LocalTime/LocalDateTime (plusX/minusX/withX/
  isBefore/isAfter/isEqual/getX/isLeapYear/lengthOfMonth/atStartOfDay itd.) i ZonedDateTime/Instant.
- `09_BigNumberTypes.json` — katalog metod BigInteger (add/subtract/multiply/divide/mod/pow/gcd/
  isProbablePrime/intValue/longValue/compareTo/bitLength/testBit...) i BigDecimal (setScale/
  stripTrailingZeros/compareTo/toBigInteger/negate/abs...).
- `12_BinaryAndHex.json` — pełniejszy katalog metod Integer/Long związanych z systemami liczbowymi
  (toBinaryString/toHexString/toOctalString/parseInt z radix/toString z radix/bitCount/
  numberOfLeadingZeros/numberOfTrailingZeros/highestOneBit/reverse).
- `13_BitwiseOperators.json` — czy WSZYSTKIE operatory bitowe (`& | ^ ~ << >> >>>` i ich formy
  złożone `&= |= ^= <<= >>= >>>=`) są jawnie pokazane, nie tylko część.
- `15_RandomAndSecureRandom.json` (jeszcze NIE napisana w tej sesji) — pełny katalog metod Random
  (nextInt/nextInt(bound)/nextInt(origin,bound)/nextLong/nextDouble/nextFloat/nextBoolean/
  nextGaussian/nextBytes/ints()/doubles()/longs() jako strumienie) — pisz to OD RAZU z pełnym
  pokryciem, zamiast pisać i potem wracać poprawiać.

Lekcje BEZ tego wymogu (czyste konstrukcje językowe, zostają jak są): 00, 01 (częściowo — sam
konzept zmiennych, nie API), 02_Operators (do sprawdzenia czy wszystkie operatory pokazane, ale
to nie jest "klasa z metodami"), 03_Conditionals, 04_Loops, 10_HeapAndStack, 11_TypeCasting,
14_GarbageCollector, 16_Exceptions.

Cała reszta rozdziałów kursu (`_02_oop` już zrobiony, oraz WSZYSTKIE przyszłe: `_03_collections`
to będzie SZCZEGÓLNIE ważne dla tego wymogu — List/Map/Set/Stream mają dziesiątki metod każda).

## Ostatnia ukończona czynność

Dopisano wymóg pełnego pokrycia API do `STAGE2_LESSON_REDESIGN_PROMPT.md`, zaimplementowano nowy
typ bloku `API_REFERENCE`, zweryfikowano (compile+build), scommitowano. Napisano i zweryfikowano
`_01_fundamentals` lekcje 00-13 (WG STAREGO zakresu wymagań, przed dodaniem wymogu katalogu API —
wymagają jeszcze uzupełnienia, patrz wyżej).

## Zmienione pliki (ten etap, od początku Etapu 4)

- `_02_oop/*.json` (15 plików) — KOMPLETNE, scommitowane (`686ff35`), NIE wymaga rewizji pod
  kątem API_REFERENCE (żadna lekcja OOP nie jest "klasą z wieloma metodami" w tym sensie).
- `_01_fundamentals/00-13_*.json` (14 plików) — napisane, zweryfikowane, scommitowane
  (`osobny wcześniejszy commit tej sesji — sprawdź `git log` jeśli potrzebujesz dokładnego hasha`),
  ALE wymagają jeszcze uzupełnienia o `API_REFERENCE` tam, gdzie wskazano wyżej.
- `STAGE2_LESSON_REDESIGN_PROMPT.md`, `ContentBlockType.java`, `TheoryView.jsx`, `App.css` —
  nowy wymóg + nowy typ bloku, scommitowane (`66fc5c4`).
- `scratchpad/polish-dict.js` (NIE w repo, tylko w katalogu roboczym sesji) — kumulatywny słownik
  poprawek polskich znaków, ~280 pozycji. Jeśli sesja jest nowa/scratchpad wyczyszczony, TRZEBA
  go odtworzyć od zera (był budowany iteracyjnie, lekcja po lekcji) — nie ma go nigdzie w repo,
  tylko w tym pliku roboczym. Warto rozważyć skopiowanie go do repo (np. `scripts/polish-dict.js`)
  przy najbliższej okazji, żeby nie odtwarzać go w kolejnej sesji od zera.
- `scratchpad/fix_native_code_corruption.js` (też NIE w repo) — mechaniczny fix bugu "function
  X() { [native code] }" (patrz niżej) — TEŻ warto skopiować do repo.

## Wyniki testów / weryfikacji

- `mvnw.cmd compile` i `npm run build` — oba OK po dodaniu API_REFERENCE.
- Restart backendu + `curl` na kilkunastu lekcjach `_01_fundamentals` (00, 06, 08, 09, 13) →
  wszystkie ładują 14 bloków teorii bez błędu. Regresja na `_02_oop/03_Constructors` → 15 bloków,
  bez zmian.
- Wszystkie 17 plików `_01_fundamentals` (00-16, włącznie z jeszcze niezmigrowanymi 14-16)
  sprawdzone `grep -c "native code"` → 0 wszędzie.

## Problemy i decyzje

- **Bug "native code" WYSTĘPUJE POZA `_02_oop`** — znaleziony i naprawiony też w
  `_01_fundamentals/01_Variables.json` i `05_Arrays.json` (nie tylko w `_02_oop`, jak wcześniej
  sądzono). **Przy KAŻDYM kolejnym rozdziale rób `grep -c "native code" plik.json` jako PIERWSZY
  krok weryfikacji KAŻDEGO pliku, nie zakładaj, że rozdział jest "bezpieczny", bo poprzedni był.**
- **Zaobserwowana, niewyjaśniona zawodność pojedynczego sprawdzenia grep/node zaraz po zapisie
  pliku** (raz dało fałszywe "0" mimo obecności korupcji) — praktyczna zasada: rób corruption-fix
  jako DOSŁOWNIE OSTATNI krok na danym pliku (po wszystkich innych edycjach), w pętli aż script
  zwróci "0 poprawek" W TYM SAMYM wywołaniu, i dodaj `sleep 1` przed finalnym potwierdzającym
  grepem, jeśli coś budzi wątpliwość.
- Tytuły lekcji nadal auto-generowane z slug (nierozwiązane, temat na później, mniejszy priorytet
  niż katalogi API).

## Następny krok (dokładnie, w kolejności)

1. **Przejrzyj i uzupełnij o `API_REFERENCE` (katalog metod)**: `06_StringsAndBuilder.json`,
   `08_MathOperations.json` (jawnie wskazane przez użytkownika), potem `05_Arrays.json`,
   `07_DateAndTime.json`, `09_BigNumberTypes.json`, `12_BinaryAndHex.json`,
   `13_BitwiseOperators.json` (sprawdź kompletność operatorów).
2. Dokończ resztę `_01_fundamentals`: `14_GarbageCollector` (JUŻ NAPISANA w tej sesji, ale NIE
   zapisana do pliku — patrz niżej, PRAWDOPODOBNIE trzeba ją napisać na nowo, sprawdź czy
   `14_GarbageCollector.json` już ma nową teorię czy jeszcze starą 7-blokową), `15_RandomAndSecureRandom`
   (pisz OD RAZU z pełnym katalogiem API Random/SecureRandom), `16_Exceptions`.
3. Restart+curl+regresja+commit dla całego dokończonego `_01_fundamentals` (17/17).
4. Przejrzyj CAŁY już ukończony `_02_oop` (15 lekcji) pod kątem wymogu API_REFERENCE — bardzo
   mało prawdopodobne, żeby cokolwiek tam wymagało zmiany (to lekcje o konstrukcjach OOP, nie o
   klasach z API), ale zgodnie z poleceniem użytkownika NALEŻY to jawnie sprawdzić i odnotować
   wynik (nawet jeśli to "sprawdzone, nic do zmiany").
5. Dopiero PO punktach 1-4 przejdź do `_03_collections` (23 lekcje) — TEN rozdział będzie
   wymagał NAJWIĘCEJ uwagi pod kątem API_REFERENCE (ArrayList/List/Map/Set/Stream mają dziesiątki
   metod każda) — pisz go OD RAZU z pełnym katalogiem, nie zostawiaj na potem.

**UWAGA o stanie 14_GarbageCollector.json**: w trakcie tej sesji WYGENEROWANO nową teorię dla tej
lekcji (napisano `migrate_01f_14.js` i URUCHOMIONO go) - ale przez przerwanie sesji (user "kończę
na dziś") NIE zdążono zweryfikować/scommitować. SPRAWDŹ NAJPIERW `node -e "console.log(require(...).theory.length)"` -
jeśli pokazuje 14, plik już ma nową teorię (tylko brakuje weryfikacji+commitu); jeśli 7, trzeba
uruchomić skrypt ponownie.
