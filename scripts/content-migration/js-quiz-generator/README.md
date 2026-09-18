# Generator quizu/cwiczen dla kursu JavaScript (Faza 2)

Narzedzia uzywane od rozdzialu `_js_06` do uzupelniania lekcji do 30 cwiczen / 100 quiz.
Kluczowa idea: **poprawna odpowiedz pytania "Co wypisze ponizszy kod?" jest WYLICZANA przez
wykonanie kodu w Node (`vm`)** - nie pisana recznie, wiec klucz `correct` nie moze sie mylic.
Dystraktory generowane sa automatycznie (`lib.js`, funkcja `candidates`).

## Uzycie (dla jednej lekcji)
1. Skopiuj `example-lesson-data.js` jako `lNN_MM.js`, zmien tresc `ex` (cwiczenia:
   `[prompt, hint, solution]`) i `q` (pytania: `C(code, wyjasnienie)` - z kodem, wynik liczony
   automatycznie; `T(pytanie, poprawna, [3 bledne], wyjasnienie)` - pojeciowe), oraz sciezke w
   `apply(...)` na koncu. Pierwsza linia: `require('./lib.js')`.
2. Uruchom plik z tego katalogu (`node lNN_MM.js`). Skrypt DOPISUJE do istniejacego JSON-a
   (Edit-semantyka), wiec przed ponownym uruchomieniem zrob `git checkout -- <plik.json>`.
   `apply(plik, {ex, q}, 30, 100)` ostrzega, jesli liczby sie nie zgadzaja.
3. `node scripts/content-migration/fix_allcaps.js <plik>` i `fix_allcaps_residual.js <plik>`.
4. `node scripts/content-migration/js-quiz-generator/verify.js <plik>` - ponownie wykonuje kod
   kazdego pytania i porownuje z kluczem (lapie uszkodzenia wprowadzone przez fix_allcaps).
   `review.js <plik> <od>` wypisuje poprawne odpowiedzi i dystraktory do przegladu wzrokowego,
   `ident.js` wykrywa niespojna wielkosc liter identyfikatorow w kodzie pytan.

## Pulapki (wszystkie zdarzyly sie naprawde)
- `fix_allcaps.js` psuje WIELKIE LITERY W KODZIE pytan: `Number.MAX_SAFE_INTEGER` ->
  `Number.Max_safe_integer`, `"KOT"` -> `"kot"`, `"IT"` -> `"It"`, `EPSILON`. Generator rzuca
  wyjatek, gdy kod/opcje zawieraja wielkie slowa spoza bialej listy (JSON, URL, API, ...).
  W kodzie pytan uzywaj camelCase/malych liter; zamiast `Number.EPSILON` uzyj `0.000001`.
- NIE uruchamiaj `fix_allcaps.js` ponownie na starszych plikach (rozdzialy 1-5) - zniszczy
  poprawki z commita "poprawki blednych odpowiedzi quizu w rozdzialach 1-5".
- Do edycji tych skryptow NIE uzywaj `sed`/`node -e` z sekwencjami `\n` w powloce - psuje pliki;
  uzywaj narzedzia Edit.
- Pytania o `this` bez obiektu przed kropka: zaczynaj kod od `"use strict";` (deterministyczne
  `undefined`), bo w trybie luznym `this` to obiekt globalny.
- Daty: nigdy nie zalezyc od strefy czasowej (`new Date(2025, 0, 1)` + toISOString/DST) - uzywaj
  `new Date("2025-01-01T12:00:00Z")` albo Math.round zamiast Math.ceil przy roznicy dni.
- `structuredClone` jest dostepny w sandboxie przez polyfill w `lib.js` (POLY).
