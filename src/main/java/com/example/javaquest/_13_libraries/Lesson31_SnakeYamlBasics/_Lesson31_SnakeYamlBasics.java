package com.example.javaquest._13_libraries.Lesson31_SnakeYamlBasics;

import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

public class _Lesson31_SnakeYamlBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 31: SNAKEYAML - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 CZYM JEST YAML I PO CO SIE GO UZYWA
         * ============================================================
         * YAML (YAML Ain't Markup Language) to format tekstowy do
         * zapisu danych ustrukturyzowanych - podobnie jak JSON (poznany
         * w _04_io/Lesson19_JsonIntro), ale czytelniejszy dla czlowieka:
         * - BRAK nawiasow klamrowych {} i kwadratowych [] - struktura
         *   wynika z WCIECIA (indentation), tak jak w Pythonie,
         * - BRAK przecinkow miedzy elementami,
         * - dozwolone KOMENTARZE (# ...) - czego JSON w ogole nie ma,
         * - stringi CZESTO nie wymagaja cudzyslowow.
         * Mozna powiedziec, ze YAML to "bardziej ludzki kuzyn JSON-a" -
         * i faktycznie kazdy poprawny dokument JSON jest jednoczesnie
         * poprawnym YAML-em (YAML to nadzbior JSON-a). Dlatego YAML jest
         * tak popularny w plikach KONFIGURACYJNYCH - Docker Compose,
         * Kubernetes, GitHub Actions, a nawet Spring Boot ma wlasny
         * `application.yml` jako alternatywe dla `application.properties`.
         * SnakeYAML to najpopularniejsza biblioteka Javy do parsowania i
         * generowania YAML-a - i to WLASNIE jej uzywa pod spodem Spring
         * Boot do wczytania `application.yml`.
         */
        System.out.println("\nYAML = 'bardziej ludzki kuzyn JSON-a': bez {}/[]/przecinkow, z komentarzami #.");
        System.out.println("SnakeYAML - biblioteka Javy do YAML, uzywana m.in. wewnatrz Spring Boota (application.yml).");

        /*
         * ============================================================
         * 🔹 SKLADNIA YAML - SKALARY, LISTY, ZAGNIEZDZONE MAPY
         * ============================================================
         * Ponizszy tekst (Java text block) to poprawny dokument YAML,
         * pokazujacy podstawowe elementy skladni:
         * - SKALARY (scalars) - pojedyncze wartosci: string, liczba,
         *   boolean, null - klucz: wartosc,
         * - stringi BEZ cudzyslowu (unquoted) - np. "JavaQuest" - YAML
         *   sam rozpoznaje, ze to tekst,
         * - stringi W cudzyslowie (quoted) - przydatne, gdy chcemy
         *   WYMUSIC typ String (patrz "problem Norwegii" nizej),
         * - LISTY (sekwencje) - kazdy element zaczyna sie od "- ",
         *   wciety wzgledem klucza,
         * - ZAGNIEZDZONE MAPY - kolejny poziom wciecia SPACJAMI tworzy
         *   obiekt zagniezdzony (odpowiednik zagniezdzonego {} w JSON).
         */
        String yamlPrzykladowy = """
                # To jest komentarz - YAML pozwala na komentarze (JSON nie)
                aplikacja: JavaQuest        # string bez cudzyslowu
                wersja: "2.3"               # string W cudzyslowie - wymuszony typ String
                port: 8080                  # liczba (Integer)
                debug: true                 # boolean
                opis: null                  # null

                # Lista (sekwencja) - kazdy element zaczyna sie od "- "
                funkcje:
                  - logowanie
                  - raportowanie
                  - eksport-pdf

                # Zagniezdzona mapa - dodatkowy poziom wciecia SPACJAMI
                baza-danych:
                  host: localhost
                  port: 5432
                  uzytkownik: admin
                """;
        System.out.println("\n=== PRZYKLADOWY DOKUMENT YAML (surowy tekst) ===");
        System.out.println(yamlPrzykladowy);

        /*
         * ============================================================
         * 🔍 Yaml.load(String) - PARSOWANIE DO Map<String, Object>
         * ============================================================
         * Klasa org.yaml.snakeyaml.Yaml to glowny punkt wejscia do
         * biblioteki. Metoda load(String) parsuje dokument YAML i
         * zwraca strukture OGOLNA - dla dokumentu, ktorego korzeniem
         * jest mapa (jak wyzej), bedzie to Map<String, Object> (konkretnie
         * java.util.LinkedHashMap - zachowuje kolejnosc kluczy z pliku).
         * Wartosci zagniezdzone (listy, mapy) sa automatycznie
         * konwertowane na java.util.List / java.util.Map - dokladnie
         * tak, jak Gson.fromJson bez podanej klasy docelowej robil to
         * dla JSON-a w _04_io/Lesson20_Gson.
         */
        demonstrateLoadToMap(yamlPrzykladowy);

        /*
         * ============================================================
         * 🔹 Yaml.dump(Object) - SERIALIZACJA Map/List Z POWROTEM DO YAML
         * ============================================================
         * dump(Object) dziala w druga strone - przyjmuje dowolny obiekt
         * (najczesciej Map lub List zbudowane recznie w kodzie) i zwraca
         * String z tekstem YAML. Uzywamy LinkedHashMap, zeby kolejnosc
         * kluczy w wygenerowanym YAML odpowiadala kolejnosci wstawiania.
         */
        demonstrateDump();

        /*
         * ============================================================
         * 🔍 loadAll(...) - WIELE DOKUMENTOW W JEDNYM STRUMIENIU
         * ============================================================
         * YAML pozwala umiescic WIELE niezaleznych dokumentow w jednym
         * pliku/strumieniu, oddzielonych linia "---". Typowy przyklad z
         * praktyki: plik z konfiguracja wielu srodowisk albo wieloma
         * zasobami Kubernetes w jednym pliku .yaml. yaml.loadAll(String)
         * zwraca Iterable<Object> - po jednym sparsowanym obiekcie na
         * kazdy dokument.
         */
        demonstrateLoadAll();

        /*
         * ============================================================
         * 📌 PULAPKA #1: TABULATORY SA ZABRONIONE W WCIECIACH
         * ============================================================
         * YAML jest WRAZLIWY na biale znaki (whitespace-sensitive) -
         * struktura zalezy WYLACZNIE od wciecia. Specyfikacja YAML
         * jednoznacznie ZABRANIA uzywania znaku TAB do wciecia - wolno
         * uzywac TYLKO spacji. SnakeYAML honoruje ten zakaz i rzuca
         * wyjatek (YAMLException), jesli w tekscie do sparsowania
         * znajdzie sie tabulator uzyty jako wciecie. To bardzo czesty
         * blad poczatkujacych - edytor moze "cicho" wstawic tab zamiast
         * spacji przy Tab/Autoindent.
         */
        demonstrateTabPitfall();

        /*
         * ============================================================
         * 📌 PULAPKA #2: "PROBLEM NORWEGII" (bare yes/no/on/off/null)
         * ============================================================
         * YAML (a za nim SnakeYAML) rozpoznaje pewne GOLE (niecytowane)
         * slowa jako wartosci logiczne lub null, a NIE jako stringi:
         * yes/Yes/YES, no/No/NO, on/On/ON, off/Off/OFF, true/false oraz
         * null/~ (a takze pusta wartosc). Nazwa "problem Norwegii"
         * (Norway problem) pochodzi z realnego przypadku, w ktorym kod
         * ISO kraju "NO" (Norwegia) byl w YAML-u bez cudzyslowu i zostal
         * SAMOWOLNIE sparsowany jako Boolean.FALSE zamiast String "NO"!
         * ROZWIAZANIE: gdy wartosc MA byc stringiem, a moglaby zostac
         * odczytana jako slowo-klucz (yes/no/on/off/null/true/false) -
         * ZAWSZE otaczaj ja cudzyslowem w YAML.
         */
        demonstrateNorwayProblem();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - YAML to czytelniejszy dla czlowieka odpowiednik JSON-a - bez
         *   {}/[]/przecinkow, oparty na wcieciu, z obsluga komentarzy #
         *   - stad jego popularnosc w plikach konfiguracyjnych (m.in.
         *   Spring Boot application.yml, Docker Compose, Kubernetes).
         * - new Yaml() + yaml.load(String) parsuje dokument do ogolnej
         *   struktury Map<String, Object> / List<Object> (podobnie jak
         *   Gson.fromJson bez klasy docelowej w _04_io/Lesson20_Gson).
         * - yaml.dump(Object) serializuje Map/List z powrotem do tekstu
         *   YAML.
         * - yaml.loadAll(String) parsuje WIELE dokumentow oddzielonych
         *   "---" w jednym strumieniu, zwracajac Iterable<Object>.
         * - PULAPKA: tabulatory w wcieciu sa ZABRONIONE - tylko spacje,
         *   inaczej SnakeYAML rzuci YAMLException.
         * - PULAPKA: gole (niecytowane) yes/no/on/off/null moga zostac
         *   auto-sparsowane jako Boolean/null zamiast String ("problem
         *   Norwegii") - w razie watpliwosci UZYWAJ cudzyslowow.
         * - W kolejnej, ostatniej lekcji tego rozdzialu: mapowanie YAML
         *   BEZPOSREDNIO na wlasne klasy Javy (yaml.loadAs, TypeDescription,
         *   Representer) zamiast generycznych Map/List.
         */
        System.out.println("\n=== KONIEC LEKCJI 31 ===");
    }

    @SuppressWarnings("unchecked")
    private static void demonstrateLoadToMap(String yamlText) {
        System.out.println("\n=== Yaml.load(String) -> Map<String, Object> ===");

        Yaml yaml = new Yaml();
        Map<String, Object> dane = yaml.load(yamlText);

        System.out.println("Typ zwrocony przez load(): " + dane.getClass().getName());
        System.out.println("aplikacja = " + dane.get("aplikacja") + " (" + dane.get("aplikacja").getClass().getSimpleName() + ")");
        System.out.println("wersja    = " + dane.get("wersja") + " (" + dane.get("wersja").getClass().getSimpleName() + ")");
        System.out.println("port      = " + dane.get("port") + " (" + dane.get("port").getClass().getSimpleName() + ")");
        System.out.println("debug     = " + dane.get("debug") + " (" + dane.get("debug").getClass().getSimpleName() + ")");
        System.out.println("opis      = " + dane.get("opis"));

        List<String> funkcje = (List<String>) dane.get("funkcje");
        System.out.println("funkcje (List<String>) = " + funkcje);

        Map<String, Object> bazaDanych = (Map<String, Object>) dane.get("baza-danych");
        System.out.println("baza-danych (Map zagniezdzona) = " + bazaDanych);
        System.out.println("baza-danych.host = " + bazaDanych.get("host"));
    }

    private static void demonstrateDump() {
        System.out.println("\n=== Yaml.dump(Object) - Map/List -> tekst YAML ===");

        Yaml yaml = new Yaml();

        Map<String, Object> serwer = new java.util.LinkedHashMap<>();
        serwer.put("nazwa", "Serwer JavaQuest");
        serwer.put("port", 9090);
        serwer.put("aktywny", true);
        serwer.put("tagi", List.of("produkcja", "eu-central"));

        String dump = yaml.dump(serwer);
        System.out.println("Wejscie: LinkedHashMap zbudowana w kodzie");
        System.out.println("Wyjscie (yaml.dump):\n" + dump);
    }

    private static void demonstrateLoadAll() {
        System.out.println("\n=== yaml.loadAll(...) - wiele dokumentow oddzielonych '---' ===");

        String wieleDokumentow = """
                ---
                nazwa: Serwer A
                port: 8080
                ---
                nazwa: Serwer B
                port: 8081
                ---
                nazwa: Serwer C
                port: 8082
                """;

        Yaml yaml = new Yaml();
        Iterable<Object> dokumenty = yaml.loadAll(wieleDokumentow);

        int licznik = 1;
        for (Object dokument : dokumenty) {
            System.out.println("Dokument " + licznik + ": " + dokument);
            licznik++;
        }
    }

    private static void demonstrateTabPitfall() {
        System.out.println("\n=== PULAPKA: tabulator (TAB) w wcieciu - YAMLException ===");

        // Ten tekst zawiera znak TAB ('\t') zamiast spacji przed "port" -
        // celowo zbudowany przez konkatenacje, zeby tab byl dobrze widoczny.
        String yamlZTabem = "serwer:\n" + "\tport: 8080\n";

        try {
            new Yaml().load(yamlZTabem);
            System.out.println("(nieoczekiwane - powinien byl wystapic blad)");
        } catch (YAMLException e) {
            System.out.println("Zlapano YAMLException - tabulator w wcieciu jest niedozwolony.");
            System.out.println("Komunikat: " + e.getMessage());
        }
    }

    private static void demonstrateNorwayProblem() {
        System.out.println("\n=== PULAPKA: 'problem Norwegii' - gole yes/no/on/off/null ===");

        String yamlNorwegia = """
                kraj: NO
                kraj_bezpieczny: "NO"
                zgoda: yes
                tryb_nocny: off
                komentarz: null
                """;

        Yaml yaml = new Yaml();
        Map<String, Object> dane = yaml.load(yamlNorwegia);

        dane.forEach((klucz, wartosc) -> {
            String typ = (wartosc == null) ? "null" : wartosc.getClass().getSimpleName();
            System.out.println(klucz + " = " + wartosc + "  [" + typ + "]");
        });
        System.out.println("Zauwaz: 'kraj: NO' bez cudzyslowu -> Boolean false (!), a 'kraj_bezpieczny: \"NO\"' -> String \"NO\".");
    }
}
