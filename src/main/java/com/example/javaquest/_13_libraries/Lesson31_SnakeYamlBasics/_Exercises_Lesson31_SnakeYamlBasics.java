package com.example.javaquest._13_libraries.Lesson31_SnakeYamlBasics;

public class _Exercises_Lesson31_SnakeYamlBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LoadSimpleScalarsToMap {
        /*
         * 🧪 Zadanie 1:
         * Zaladuj (Yaml.load) nastepujacy tekst YAML:
         *   imie: Jan
         *   wiek: 30
         *   aktywny: true
         * Wypisz kazda wartosc z otrzymanej Map<String, Object> razem z jej
         * klasa (value.getClass().getSimpleName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LoadListOfStrings {
        /*
         * 🧪 Zadanie 2:
         * Zaladuj tekst YAML:
         *   jezyki:
         *     - Java
         *     - Kotlin
         *     - Python
         * Pobierz z Map wartosc pod kluczem "jezyki", rzutuj na List<String>
         * i wypisz kazdy element w petli z numerem porzadkowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_LoadNestedMap {
        /*
         * 🧪 Zadanie 3:
         * Zaladuj tekst YAML z zagniezdzona mapa:
         *   adres:
         *     miasto: Warszawa
         *     kod-pocztowy: "00-001"
         * Pobierz zagniezdzona mape pod kluczem "adres" i wypisz jej pola
         * miasto oraz kod-pocztowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DumpSimpleMap {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj recznie java.util.LinkedHashMap<String, Object> z polami
         * "produkt" (String), "cena" (double), "dostepny" (boolean) i
         * wypisz wynik yaml.dump(mapa) na konsole.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DumpListOfStrings {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj java.util.List<String> {"poniedzialek", "wtorek", "sroda"}
         * i przekaz ja bezposrednio do yaml.dump(...) - wypisz wynikowy
         * tekst YAML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CountKeysInYamlDocument {
        /*
         * 🧪 Zadanie 6:
         * Zaladuj YAML z co najmniej 5 roznymi kluczami na poziomie
         * glownym i wypisz ich LICZBE (mapa.size()) oraz wszystkie nazwy
         * kluczy (mapa.keySet()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_QuotedVsUnquotedNumberString {
        /*
         * 🧪 Zadanie 7:
         * Zaladuj YAML:
         *   kod_pocztowy_zle: 00123
         *   kod_pocztowy_dobrze: "00123"
         * Wypisz typ (getClass) obu wartosci i skomentuj (w println) roznice -
         * czy wiodace zero zniknelo w ktorejs z wersji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_YamlWithComments {
        /*
         * 🧪 Zadanie 8:
         * Zaladuj YAML zawierajacy co najmniej 2 linie komentarzy (#) oraz
         * 3 pary klucz-wartosc. Udowodnij, ze komentarze NIE trafiaja do
         * wynikowej Map (wypisz mapa.size() i jej zawartosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_LoadListOfMaps {
        /*
         * 🧪 Zadanie 9:
         * Zaladuj YAML reprezentujacy liste 2 osob, kazda jako mapa
         * imie/wiek:
         *   osoby:
         *     - imie: Anna
         *       wiek: 25
         *     - imie: Piotr
         *       wiek: 31
         * Wypisz imie i wiek kazdej osoby w petli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RootLevelList {
        /*
         * 🧪 Zadanie 10:
         * Zaladuj YAML, ktorego KORZENIEM jest od razu lista (bez klucza
         * glownego), np.:
         *   - Java
         *   - Kolekcje
         *   - IO
         * Wypisz typ zwrocony przez yaml.load(...) (powinien to byc
         * java.util.List, nie Map) oraz jego zawartosc.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LoadAllTwoDocuments {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj tekst z DWOMA dokumentami YAML oddzielonymi "---",
         * kazdy reprezentujacy inny "profil uzytkownika" (login, rola).
         * Uzyj yaml.loadAll(...) i wypisz oba dokumenty z numerem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LoadAllCountDocuments {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj tekst z 4 dokumentami YAML oddzielonymi "---". Uzyj
         * yaml.loadAll(...), policz ILE dokumentow zwrocilo (bez
         * zakladania z gory liczby - iteruj i licz w petli) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DumpNestedStructure {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj recznie zagniezdzona strukture: LinkedHashMap z kluczem
         * "serwer" wskazujacym na KOLEJNA LinkedHashMap (host, port).
         * Przekaz cala strukture do yaml.dump(...) i wypisz wynik -
         * sprawdz, czy wciecie zagniezdzonej mapy jest poprawne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RoundTripLoadThenDump {
        /*
         * 🧪 Zadanie 14:
         * Zaladuj dowolny tekst YAML do Map<String, Object>, NASTEPNIE
         * przekaz otrzymana mape z powrotem do yaml.dump(...) - wypisz
         * oryginalny tekst i wynik "round-trip" obok siebie, zeby
         * porownac (moga sie nieznacznie roznic formatowaniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TabIndentationThrowsException {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj String zawierajacy YAML z tabulatorem ('\t') uzytym jako
         * wciecie (np. "root:\n\tklucz: wartosc\n"). Zaladuj go w bloku
         * try-catch(YAMLException) i wypisz komunikat wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NorwayProblemBooleanValues {
        /*
         * 🧪 Zadanie 16:
         * Zaladuj YAML z 4 polami zawierajacymi gole slowa: "yes", "no",
         * "on", "off" (bez cudzyslowow). Wypisz kazda wartosc razem z jej
         * klasa - potwierdz, ze wszystkie 4 staly sie java.lang.Boolean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_NorwayProblemFix {
        /*
         * 🧪 Zadanie 17:
         * Napraw problem z Zadania 16 - zapisz te same 4 wartosci W
         * CUDZYSLOWACH ("yes", "no", "on", "off") i udowodnij (przez
         * getClass), ze teraz sa to obiekty String, a nie Boolean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NullValuesInYaml {
        /*
         * 🧪 Zadanie 18:
         * Zaladuj YAML z polem zapisanym jako "null" oraz polem zapisanym
         * jako "~" (alternatywny zapis null w YAML) - sprawdz (== null),
         * ze OBA sa odczytywane jako null w Javie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MultilineStringBlockScalar {
        /*
         * 🧪 Zadanie 19:
         * Zaladuj YAML z polem wykorzystujacym blokowy string wieloliniowy
         * (znak "|"), np.:
         *   opis: |
         *     Linia pierwsza.
         *     Linia druga.
         * Wypisz wynikowa wartosc String i sprawdz (contains lub split),
         * ze zawiera znak nowej linii miedzy "pierwsza." a "Linia druga.".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildSimpleConfigLoader {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode Map<String, Object> wczytajKonfiguracje(String yamlText),
         * ktora opakowuje yaml.load(...) - przetestuj ja na przykladowej
         * "konfiguracji aplikacji" z co najmniej 4 polami (nazwa, port,
         * debug, lista funkcji) i wypisz zwrocona mape.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ValidateRequiredKeysPresent {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode walidujKonfiguracje(Map<String, Object> config,
         * String... wymaganeKlucze), ktora sprawdza, czy WSZYSTKIE podane
         * klucze wystepuja w mapie - jesli nie, zbiera brakujace w liste i
         * wypisuje czytelny komunikat bledu. Przetestuj z konfiguracja
         * brakujaca 2 z 4 wymaganych kluczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MergeTwoYamlConfigs {
        /*
         * 🧪 Zadanie 22:
         * Zaladuj DWA osobne teksty YAML do dwoch osobnych Map<String, Object>
         * (np. "domyslna konfiguracja" i "nadpisania uzytkownika"). Napisz
         * metode laczacaKonfiguracje(domyslna, nadpisania), ktora zwraca
         * NOWA mape, gdzie klucze z "nadpisania" NADPISUJA te z "domyslna" -
         * wypisz wynik polaczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FlattenNestedYamlToDotNotation {
        /*
         * 🧪 Zadanie 23:
         * Zaladuj YAML z co najmniej 2 poziomami zagniezdzenia (np.
         * serwer.baza-danych.host). Napisz metode rekurencyjna, ktora
         * "splaszcza" zagniezdzona Map do pojedynczej Map<String, Object>
         * z kluczami w notacji kropkowej (np. "serwer.baza-danych.host") -
         * wypisz splaszczona mape.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_LoadAllAndFilterByField {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj tekst z 5 dokumentami YAML reprezentujacymi "zadania"
         * (pole "priorytet": WYSOKI/SREDNI/NISKI). Uzyj loadAll, zbierz
         * wszystkie dokumenty do java.util.List<Map<String, Object>>, a
         * nastepnie (bez uzywania Streams, zwykla petla) wyfiltruj i
         * wypisz TYLKO te o priorytecie "WYSOKI".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DetectNorwayProblemInUserYaml {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode sprawdzPulapkeNorweska(Map<String, Object> dane),
         * ktora przechodzi po wszystkich wartosciach mapy i wypisuje
         * OSTRZEZENIE dla kazdej wartosci typu Boolean (bo mogla powstac
         * przez przypadek z golego slowa yes/no/on/off) - przetestuj na
         * mapie z co najmniej 1 prawdziwym polem logicznym i 1
         * "przypadkowym" (np. kod kraju "NO").
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConvertJavaMapToYamlAndBack {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj recznie "profil produktu" (nazwa, cena, kategorie jako
         * List<String>, dostepny jako boolean) jako LinkedHashMap.
         * Wykonaj pelny cykl: dump -> load -> porownaj oryginalna mape z
         * mapa po round-tripie (equals) - wypisz, czy sa rowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_YamlBasedFeatureFlags {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj YAML reprezentujacy "flagi funkcji" (feature flags) -
         * mape nazwa-flagi -> boolean, min. 5 flag. Zaladuj ja i napisz
         * metode czyWlaczona(Map<String, Object> flagi, String nazwa),
         * ktora bezpiecznie zwraca false, gdy flaga nie istnieje w mapie
         * (bez NullPointerException) - przetestuj na istniejacej i
         * nieistniejacej fladze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultiEnvironmentConfigViaLoadAll {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj YAML z 3 dokumentami (dev/test/prod), kazdy z polami
         * "srodowisko" i "url-bazy-danych". Uzyj loadAll, zbuduj
         * Map<String, Map<String, Object>> indeksowana po wartosci pola
         * "srodowisko", a nastepnie odczytaj konfiguracje TYLKO dla "prod".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SafeNestedValueExtractor {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode ogolna Object pobierzZagniezdzone(Map<String, Object>
         * dane, String... sciezkaKluczy), ktora schodzi po kolejnych
         * kluczach przez zagniezdzone mapy (np. pobierzZagniezdzone(dane,
         * "serwer", "baza-danych", "host")) i zwraca null (zamiast rzucac
         * wyjatek), jesli ktorys klucz po drodze nie istnieje. Przetestuj
         * na poprawnej i niepoprawnej sciezce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniYamlConfigValidatorReport {
        /*
         * 🧪 Zadanie 30:
         * Polacz wiedze z tej lekcji w mini-narzedzie: zaladuj YAML
         * "konfiguracji aplikacji" (min. 6 pol, w tym 1 zagniezdzona mapa i
         * 1 lista), zwaliduj wymagane klucze (jak w Zadaniu 21), wykryj
         * potencjalny "problem Norwegii" (jak w Zadaniu 25) i wypisz
         * czytelny "raport walidacji" podsumowujacy znalezione problemy
         * (lub informacje, ze konfiguracja jest poprawna).
         */
        public static void main(String[] args) { }
    }
}
