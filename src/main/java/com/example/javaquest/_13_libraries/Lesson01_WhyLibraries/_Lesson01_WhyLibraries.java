package com.example.javaquest._13_libraries.Lesson01_WhyLibraries;

import java.util.List;

public class _Lesson01_WhyLibraries {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: PO CO UZYWAC BIBLIOTEK? ===");

        /*
         * ============================================================
         * 📦 CZYM JEST "BIBLIOTEKA" I PO CO ZACZYNAMY OD NIEJ NOWY ROZDZIAL
         * ============================================================
         * - Biblioteka (ang. library) to gotowy, spakowany kod (najczesciej
         *   plik .jar) napisany przez kogos innego, ktory dolaczasz do
         *   swojego projektu jako ZALEZNOSC (dependency) - zamiast pisac
         *   dana funkcjonalnosc od zera, WOLASZ gotowe klasy/metody.
         * - W tym kursie do tej pory poznales JDK (Java Development Kit) -
         *   biblioteke standardowa dostarczana razem z Java (java.util,
         *   java.io, java.net, java.sql...). Ten rozdzial jest o
         *   bibliotekach ZEWNETRZNYCH - spoza JDK, publikowanych przez
         *   spolecznosc/firmy trzecie (Apache, Google, Square...).
         * - W praktyce zawodowej NIKT nie pisze wszystkiego samemu -
         *   przecietny projekt Java/Spring Boot ma dziesiatki, czasem
         *   setki zaleznosci (bezposrednich i transitywnych). Ten rozdzial
         *   uczy, jak swiadomie z nich korzystac.
         */
        System.out.println("\nBiblioteka = gotowy, spakowany kod (JAR) dolaczany do projektu jako zaleznosc.");
        System.out.println("JDK to biblioteka STANDARDOWA - ten rozdzial jest o bibliotekach ZEWNETRZNYCH.");

        /*
         * ============================================================
         * 🔹 KORZYSCI Z UZYWANIA GOTOWYCH BIBLIOTEK
         * ============================================================
         * - DRY (Don't Repeat Yourself) - ktos juz napisal, przetestowal
         *   i wielokrotnie poprawil kod rozwiazujacy Twoj problem (np.
         *   parsowanie JSON, budowanie zapytan HTTP) - po co pisac to
         *   od nowa?
         * - Przetestowany, "battle-tested" kod - popularna biblioteka byla
         *   uzyta w tysiacach projektow produkcyjnych, co oznacza, ze
         *   wiekszosc bledow brzegowych zostala juz znaleziona i
         *   naprawiona przez innych, zanim Ty na nie trafisz.
         * - Spolecznosc (community) - latwo znalezc odpowiedz na Stack
         *   Overflow, w dokumentacji, na GitHubie w Issues - populary
         *   projekt ma tysiace osob, ktore juz napotkaly Twoj problem.
         * - Szybszy "time-to-market" - mniej kodu do napisania i
         *   utrzymania samemu = szybciej dowozisz dzialajaca funkcjonalnosc.
         * - Skupienie na LOGICE BIZNESOWEJ - zamiast reimplementowac
         *   niskopoziomowe detale (np. parser CSV odporny na przecinki w
         *   cudzyslowach), skupiasz czas zespolu na tym, co odroznia Twoj
         *   produkt od konkurencji.
         */
        printBenefits();

        /*
         * ============================================================
         * 🔍 KOSZTY I RYZYKA - "NIE MA NIC ZA DARMO"
         * ============================================================
         * - Rozmiar aplikacji - kazda zaleznosc to dodatkowe klasy w
         *   finalnym JAR/WAR, dluzszy czas budowania i startu aplikacji.
         * - Podatnosci bezpieczenstwa (CVE - Common Vulnerabilities and
         *   Exposures) - biblioteka moze miec luke bezpieczenstwa (glosny
         *   przyklad: Log4Shell w log4j-core, CVE-2021-44228) - trzeba
         *   sledzic aktualizacje i szybko patchowac.
         * - Ryzyko porzucenia projektu (abandonware) - autor moze
         *   przestac utrzymywac biblioteke - brak poprawek bledow, brak
         *   wsparcia dla nowych wersji Javy.
         * - Konflikty wersji / "dependency hell" - dwie Twoje zaleznosci
         *   moga wymagac ROZNYCH, niekompatybilnych wersji tej samej
         *   biblioteki transitywnej (rozwiniemy w Lekcji 2).
         * - "Licencja" - kazda biblioteka ma warunki prawne uzycia
         *   (nizej) - niezweryfikowanie ich moze byc problemem prawnym
         *   w komercyjnym projekcie.
         * - Powierzchnia zaufania (supply chain) - dodajac zaleznosc,
         *   ufasz KODOWI, ktorego nie napisales i moze nie w pelni
         *   przeczytales.
         */
        printCosts();

        /*
         * ============================================================
         * 🔍 JAK OCENIAC JAKOSC BIBLIOTEKI PRZED UZYCIEM
         * ============================================================
         * - Aktywnosc repozytorium - data ostatniego commita/release'u
         *   (repo nietykane od 5 lat to sygnal ostrzegawczy), czestotliwosc
         *   wydan, liczba otwartych/zamknietych Issues.
         * - Popularnosc - liczba gwiazdek (stars) na GitHubie, liczba
         *   pobrań na Maven Central (widoczna np. na mvnrepository.com w
         *   sekcji "Used By") - NIE jest to gwarancja jakosci, ale silny
         *   sygnal, ze wielu innych uzytkownikow juz przetestowalo
         *   biblioteke w praniu.
         * - Licencja - najczesciej spotykane w ekosystemie Java:
         *     * Apache 2.0 - bardzo permisywna, mozna uzywac komercyjnie,
         *       modyfikowac, wymaga jedynie zachowania informacji o
         *       licencji/copyright (uzywana m.in. przez Apache Commons,
         *       Guava).
         *     * MIT - jeszcze prostsza, bardzo permisywna, praktycznie
         *       bez ograniczen poza zachowaniem informacji o licencji
         *       (uzywana m.in. przez OkHttp, wiele bibliotek JS).
         *     * GPL / LGPL (copyleft) - wymaga, by kod, ktory Z NIA
         *       LACZYSZ, byl rowniez udostepniony na podobnych zasadach
         *       (w pewnych warunkach) - w projektach komercyjnych/
         *       zamknietych czesto UNIKANE lub wymaga konsultacji
         *       prawnej przed uzyciem.
         *   W PRAKTYCE: przed dodaniem biblioteki do projektu firmowego
         *   zawsze warto sprawdzic plik LICENSE w repozytorium.
         * - Dokumentacja - czy jest README z przykladami, oficjalna
         *   strona z API docs (Javadoc), przewodnik "Getting Started"?
         * - Zaleznosci TEJ biblioteki - czy sama nie ciagnie za soba
         *   dziesiatek innych, ciezkich zaleznosci (rozwiniemy w Lekcji 2,
         *   "transitive dependencies").
         */
        printQualityChecklist();

        /*
         * ============================================================
         * 🔍 SEMANTIC VERSIONING (SemVer): MAJOR.MINOR.PATCH
         * ============================================================
         * - Wiekszosc bibliotek Java (i nie tylko) numeruje wydania wg
         *   schematu MAJOR.MINOR.PATCH, np. "4.12.0" (OkHttp) albo
         *   "33.4.0" (Guava):
         *     * MAJOR - zmiana NIEKOMPATYBILNA wstecz (breaking change) -
         *       usunieta/zmieniona metoda, inny kontrakt API. Aktualizacja
         *       MAJOR wymaga przejrzenia release notes i czesto poprawek
         *       w Twoim kodzie.
         *     * MINOR - nowa funkcjonalnosc, KOMPATYBILNA wstecz - mozesz
         *       zaktualizowac bezpiecznie, nic starego nie powinno sie
         *       zepsuc.
         *     * PATCH - poprawki bledow, bez nowych funkcji, w pelni
         *       kompatybilne - zawsze bezpieczne (i zalecane) do
         *       aktualizacji, czesto zawieraja poprawki bezpieczenstwa.
         * - W Mavenie (pom.xml) mozna uzyc zakresow wersji (np. "[4.12,5.0)"),
         *   ale w praktyce wiekszosc projektow (w tym ten kurs) wskazuje
         *   KONKRETNA wersje - powtarzalne, przewidywalne buildy.
         */
        demonstrateSemVer();

        /*
         * ============================================================
         * 📦 ZAPOWIEDZ ROZDZIALU: KATEGORIE BIBLIOTEK, KTORE POZNASZ
         * ============================================================
         * Ten rozdzial pokazuje najpopularniejsze biblioteki w
         * ekosystemie Java, pogrupowane wg kategorii/problemu, ktory
         * rozwiazuja:
         *   - Redukcja boilerplate'u -> Lombok (@Getter/@Setter/@Builder
         *     generowane w czasie kompilacji zamiast recznie pisanego kodu).
         *   - Narzedzia ogolnego przeznaczenia (utilities) -> Apache
         *     Commons (Lang3/IO/Collections4) i Google Guava - rozszerzaja
         *     to, czego brakuje w standardowej bibliotece Javy.
         *   - Klient HTTP -> OkHttp - nowoczesna alternatywa dla
         *     java.net.http poznanego w rozdziale _06_networking.
         *   - Logowanie -> SLF4J + Logback - profesjonalny odpowiednik
         *     System.out.println uzywany w KAZDYM realnym projekcie.
         *   - Dependency Injection -> Guice - lekki kontener DI (Spring
         *     ma wlasny, ale warto poznac koncepcje na prostszym narzedziu).
         *   - Mapowanie obiektow -> MapStruct - generowanie mapperow
         *     Encja<->DTO w czasie kompilacji (zero refleksji w runtime).
         *   - Praca z plikami Excel -> Apache POI.
         *   - Parsowanie/scraping HTML -> Jsoup.
         *   - Cache w pamieci -> Caffeine.
         *   - Budowanie aplikacji CLI -> Picocli.
         *   - Konfiguracja w formacie YAML -> SnakeYAML.
         * Rozdzial NIE powtarza tematow z innych rozdzialow: JSON/Gson/
         *   Jackson (_04_io), parsowanie XML i java.net.http (_06_networking),
         *   Maven/Gradle (_11_buildtools), Hibernate/JPA (_12_hibernate).
         */
        printChapterRoadmap();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Biblioteki przyspieszaja prace i daja przetestowany kod, ale
         *   NIE sa darmowe - kazda zaleznosc to dlug do zarzadzania
         *   (rozmiar, bezpieczenstwo, aktualnosc, licencja).
         * - Przed dodaniem biblioteki: sprawdz aktywnosc repo, popularnosc,
         *   licencje i dokumentacje.
         * - SemVer (MAJOR.MINOR.PATCH) mowi, jak "bezpieczna" jest
         *   aktualizacja - PATCH zawsze bezpieczny, MINOR zwykle
         *   bezpieczny, MAJOR wymaga uwagi.
         * - W kolejnej lekcji: jak PRAKTYCZNIE dodac i ocenic zaleznosc
         *   w Mavenie/Gradle (dependency:tree, scope, exclusions, BOM).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void printBenefits() {
        System.out.println("\n=== KORZYSCI Z BIBLIOTEK ===");
        List<String> benefits = List.of(
                "DRY - nie piszesz od nowa tego, co juz istnieje",
                "Kod przetestowany w tysiacach projektow produkcyjnych",
                "Wsparcie spolecznosci (Stack Overflow, GitHub Issues)",
                "Szybszy time-to-market",
                "Wiecej czasu na logike biznesowa, mniej na 'reinventing the wheel'"
        );
        benefits.forEach(b -> System.out.println(" + " + b));
    }

    private static void printCosts() {
        System.out.println("\n=== KOSZTY I RYZYKA ===");
        String format = "%-32s | %-45s%n";
        System.out.printf(format, "Ryzyko", "Przyklad / konsekwencja");
        System.out.println("-".repeat(80));
        System.out.printf(format, "Rozmiar aplikacji", "wiecej klas w JAR, wolniejszy start");
        System.out.printf(format, "Podatnosci (CVE)", "np. Log4Shell w log4j-core (CVE-2021-44228)");
        System.out.printf(format, "Porzucenie projektu", "brak poprawek, brak wsparcia nowej Javy");
        System.out.printf(format, "Konflikty wersji", "'dependency hell' - patrz Lekcja 2");
        System.out.printf(format, "Licencja", "GPL moze wymagac ujawnienia Twojego kodu");
    }

    private static void printQualityChecklist() {
        System.out.println("\n=== CHECKLISTA: JAK OCENIC BIBLIOTEKE PRZED UZYCIEM ===");
        List<String> checklist = List.of(
                "Data ostatniego commita/release'u (repo aktywne?)",
                "Liczba gwiazdek na GitHubie / pobran na Maven Central",
                "Licencja (Apache 2.0 / MIT permisywne, GPL wymaga uwagi)",
                "Dokumentacja (README, Javadoc, Getting Started)",
                "Wlasne zaleznosci biblioteki (czy nie ciagnie 'polowy internetu')"
        );
        for (int i = 0; i < checklist.size(); i++) {
            System.out.println(" [" + (i + 1) + "] " + checklist.get(i));
        }
    }

    private static void demonstrateSemVer() {
        System.out.println("\n=== SEMANTIC VERSIONING NA PRZYKLADACH Z TEGO PROJEKTU ===");
        printVersionChange("4.12.0", "4.12.1", "PATCH - poprawka buga, bezpieczna aktualizacja");
        printVersionChange("4.12.0", "4.13.0", "MINOR - nowa funkcja, kompatybilna wstecz");
        printVersionChange("4.12.0", "5.0.0", "MAJOR - mozliwe zmiany lamiace kompatybilnosc");
    }

    private static void printVersionChange(String from, String to, String meaning) {
        System.out.println(" " + from + " -> " + to + " : " + meaning);
    }

    private static void printChapterRoadmap() {
        System.out.println("\n=== MAPA DROGOWA ROZDZIALU _13_libraries ===");
        String format = "%-16s | %-40s%n";
        System.out.printf(format, "Kategoria", "Biblioteka");
        System.out.println("-".repeat(60));
        System.out.printf(format, "Boilerplate", "Lombok");
        System.out.printf(format, "Utilities", "Apache Commons (Lang3/IO/Collections4), Guava");
        System.out.printf(format, "HTTP", "OkHttp");
        System.out.printf(format, "Logging", "SLF4J + Logback");
        System.out.printf(format, "DI", "Guice");
        System.out.printf(format, "Mapowanie obiektow", "MapStruct");
        System.out.printf(format, "Excel", "Apache POI");
        System.out.printf(format, "HTML", "Jsoup");
        System.out.printf(format, "Cache", "Caffeine");
        System.out.printf(format, "CLI", "Picocli");
        System.out.printf(format, "Konfiguracja YAML", "SnakeYAML");
    }
}
