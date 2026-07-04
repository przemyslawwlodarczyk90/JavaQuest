package com.example.javaquest._07_servlets.Lesson02_ServletContainers;

public class _Exercises_Lesson02_ServletContainers {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ContainerComparisonTable {
        /*
         * 🧪 Zadanie 1:
         * Wypisz (przez println, sformatowane w kolumnach) tabelę porównawczą trzech
         * kontenerów serwletów: Tomcat, Jetty, Undertow. Kolumny: Nazwa, Typowe
         * zastosowanie (np. "domyslny w Spring Boot", "embedded w narzedziach
         * budujacych", "WildFly/Quarkus"), Charakterystyka wydajnosciowa (1 zdanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ContainerResponsibilitiesList {
        /*
         * 🧪 Zadanie 2:
         * Wypisz ponumerowaną listę (1-5) pięciu odpowiedzialności kontenera serwletów
         * omówionych w tej lekcji: parsowanie HTTP, pula wątków, zarządzanie sesją,
         * bezpieczeństwo, mapowanie URL -> serwlet. Dla każdej dodaj jedno zdanie wyjaśnienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WarVsJarExplanation {
        /*
         * 🧪 Zadanie 3:
         * Wypisz dwa akapity (println): pierwszy wyjaśniający czym jest plik WAR
         * (Web Application Archive) i że wymaga zewnętrznego kontenera, drugi
         * wyjaśniający czym jest "gruby" JAR z osadzonym serwerem i że jest
         * samowystarczalny (uruchamiany "java -jar aplikacja.jar").
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_EmbeddedVsClassicDifferences {
        /*
         * 🧪 Zadanie 4:
         * Wypisz listę 3 różnic między modelem embedded a modelem klasycznym
         * (external/standalone) wdrożenia serwletów: (1) kto tworzy instancję serwera,
         * (2) format pakowania aplikacji (WAR vs JAR), (3) sposób uruchomienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BasicEmbeddedTomcatCheck {
        /*
         * 🧪 Zadanie 5:
         * Uruchom osadzony Tomcat na porcie 0 (setBaseDir na katalog tymczasowy) z
         * jednym serwletem zamapowanym pod "/wersja", zwracającym tekst
         * "Embedded Tomcat dziala.". Wykonaj GET na "/wersja", wypisz status i ciało
         * odpowiedzi, zatrzymaj serwer (stop()+destroy()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ClassifyDeploymentScenarios {
        /*
         * 🧪 Zadanie 6:
         * Mając tablicę opisów: {"plik .war w folderze webapps/ Tomcata",
         * "java -jar aplikacja.jar", "main() tworzy new Tomcat() i woła start()",
         * "rozpakowany serwer Tomcat uruchomiony skryptem startup.sh"}, sklasyfikuj
         * każdy opis jako "EMBEDDED" lub "KLASYCZNY" i wypisz wynik dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ContainerClassNameInResponse {
        /*
         * 🧪 Zadanie 7:
         * Zamapuj serwlet pod "/kontener", którego doGet() zwraca w ciele odpowiedzi
         * tekst zawierający Tomcat.class.getSimpleName() (czyli "Tomcat"). Wykonaj GET
         * i wypisz wynik sprawdzenia response.body().contains("Tomcat").
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_EmbeddedProsAndCons {
        /*
         * 🧪 Zadanie 8:
         * Wypisz listę minimum 3 zalet modelu embedded (np. brak instalacji serwera,
         * łatwe uruchamianie każdej lekcji, samodzielny JAR) oraz minimum 2 wad
         * (np. większy rozmiar JAR-a, mniejsza elastyczność wersji serwera w runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ThreadNameFromContainer {
        /*
         * 🧪 Zadanie 9:
         * Zamapuj serwlet pod "/thread", którego doGet() zwraca
         * Thread.currentThread().getName() jako tekst odpowiedzi. Wykonaj GET i
         * wypisz zwróconą nazwę wątku – zademonstruj, że to kontener (jego pula
         * wątków) przydzielił wątek obsługujący żądanie, nie main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SpringBootDefaultsTable {
        /*
         * 🧪 Zadanie 10:
         * Wypisz w formie tabeli (println z wyrównanymi kolumnami) informację, który
         * kontener jest DOMYŚLNY w Spring Boot (Tomcat), który jest ALTERNATYWNYM
         * osadzonym wyborem (Undertow), a który jest typowo używany jako STANDALONE
         * embedded w innych narzędziach (Jetty).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoResponsibilityDemoServlets {
        /*
         * 🧪 Zadanie 11:
         * W jednym kontekście embedded Tomcata zarejestruj dwa serwlety: pod
         * "/sesja-info" zwracający tekst wspominający, że HttpSession zostanie
         * omówiona w Lesson11, oraz pod "/zabezpieczenia" zwracający tekst wspominający
         * bezpieczeństwo jako odpowiedzialność kontenera. Wykonaj GET na obie ścieżki
         * i wypisz oba ciała odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThreadNamesSetAcrossRequests {
        /*
         * 🧪 Zadanie 12:
         * Zamapuj serwlet pod "/watki-puli", zwracający nazwę bieżącego wątku. Wykonaj
         * 5 kolejnych żądań GET, zbierz zwrócone nazwy do Set<String> i wypisz jego
         * rozmiar wraz z komentarzem, czy pula wątków użyła jednego, czy wielu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareContainersMethod {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę compareContainers() zwracającą wieloliniowy String opisujący
         * mocne strony Tomcat, Jetty i Undertow (po jednym zdaniu na kontener, zgodnie
         * z treścią lekcji). Wywołaj ją w main() i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ClassifyDeploymentMethod {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę classifyDeployment(String opis) zwracającą "WAR" lub "JAR" na
         * podstawie słów kluczowych (np. "webapps" -> "WAR", "java -jar" -> "JAR").
         * Przetestuj ją na 5 przykładowych opisach i wypisz wynik klasyfikacji dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TwoServletsDifferentBodies {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj embedded Tomcat z dwoma serwletami pod ścieżkami "/info1" i "/info2",
         * zwracającymi RÓŻNE fakty o kontenerach (np. jeden o Tomcat, drugi o Jetty).
         * Wykonaj GET na obie ścieżki i wypisz wynik porównania
         * (!body1.equals(body2)) potwierdzający, że treści są różne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SameServletThreeMappings {
        /*
         * 🧪 Zadanie 16:
         * Zarejestruj JEDNĄ instancję serwletu (zwracającą stały tekst "trafiono")
         * pod trzema różnymi ścieżkami: "/a", "/b", "/c" (trzy wywołania
         * addServletMappingDecoded dla tej samej nazwy serwletu). Wykonaj GET na
         * wszystkie trzy ścieżki i wypisz status oraz ciało dla każdej, potwierdzając
         * poprawne mapowanie URL -> serwlet przez kontener.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ResponsibilitiesChecklist {
        /*
         * 🧪 Zadanie 17:
         * Wypisz listę wszystkich 5 odpowiedzialności kontenera z tej lekcji, przy
         * każdej zaznaczając (jako tekst "[x]" lub "[ ]"), czy Twój prosty program
         * demo z tej lekcji faktycznie ją demonstruje bezpośrednio (parsowanie HTTP
         * i mapowanie URL - tak; pula wątków - częściowo; sesje i bezpieczeństwo - nie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_StartupTimeMeasurement {
        /*
         * 🧪 Zadanie 18:
         * Zmierz czas (System.nanoTime()) tuż przed tomcat.start() i tuż po nim.
         * Wypisz różnicę w milisekundach jako orientacyjny czas startu osadzonego
         * Tomcata, a następnie zatrzymaj serwer. Dodaj komentarz (println), że
         * kontenery embedded startują zwykle bardzo szybko.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WarVsJarTable {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę summarizeWarVsJar() zwracającą tablicę String[][] z dwoma
         * wierszami: {"WAR", "wymaga zewnetrznego kontenera"} i {"JAR",
         * "samowystarczalny, zawiera osadzony serwer"}. Wypisz oba wiersze jako
         * krótką, sformatowaną tabelę 2x2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AlternativeContainersExplanation {
        /*
         * 🧪 Zadanie 20:
         * Zamapuj serwlet pod "/alternatywy", którego doGet() zwraca tekst wymieniający
         * Jetty i Undertow jako alternatywne, osadzalne kontenery, które MOGŁYBY
         * zastąpić Tomcata bez zmiany kodu serwletu (dzięki wspólnemu API
         * jakarta.servlet.*). Wykonaj GET i wypisz otrzymane ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullComparisonReport {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj Map<String, String[]> z kluczami "Tomcat", "Jetty", "Undertow" i
         * wartościami tablic {popularnosc, typoweZastosowanie, uwagaWydajnosciowa}
         * na podstawie treści lekcji. Wypisz pełny, sformatowany raport (String.format)
         * z jednym wierszem tabeli na każdy kontener.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LifecycleUnderContainerAgnosticServlet {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj serwlet, którego init() wypisuje "start", pole AtomicInteger
         * zliczajace żądania inkrementowane w doGet() (zwracające bieżącą wartość jako
         * tekst), zamapowany pod "/raport". Wykonaj 3 żądania GET, zatrzymaj Tomcat i w
         * destroy() wypisz łączną liczbę obsłużonych żądań – skomentuj, że dokładnie
         * ten sam kod serwletu działałby identycznie w modelu klasycznym (WAR).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RecommendContainerMethod {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę recommendContainer(String wymaganie), zwracającą rekomendowany
         * kontener na podstawie treści lekcji dla wymagań: "najmniejszy narzut
         * pamieciowy" -> "Undertow", "najbardziej dojrzaly i popularny" -> "Tomcat",
         * "domyslny w Spring Boot" -> "Tomcat", "lekki, czesto embedded w narzedziach"
         * -> "Jetty". Przetestuj metodę dla wszystkich 4 wymagań i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MigrationChecklist {
        /*
         * 🧪 Zadanie 24:
         * Wypisz listę minimum 5 kroków potrzebnych do migracji aplikacji serwletowej
         * z klasycznego wdrożenia WAR (folder webapps/) do modelu embedded (main()
         * tworzący i uruchamiający Tomcata programowo), np.: dodanie zależności
         * tomcat-embed-core, napisanie main() z new Tomcat(), zamiana web.xml na
         * programowe Tomcat.addServlet(), itd.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ContainerAgnosticApiDemo {
        /*
         * 🧪 Zadanie 25:
         * Zamapuj dwa serwlety pod "/tomcat-only" (zwraca zwykły tekst) i "/api-standard"
         * (zwraca tekst tłumaczący, że ten sam kod skompilowałby się i zadziałał pod
         * Jetty/Undertow, bo wszystkie implementują jakarta.servlet.*). Wykonaj GET na
         * obie ścieżki i wypisz oba ciała, potwierdzając działanie przez rzeczywiste
         * żądanie HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AdvancedComparisonTable {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj List<String[]> z wierszami {nazwa, czasStartu ("szybki"/"sredni"),
         * typowyNarzut ("niski"/"sredni"/"wysoki"), typoweZastosowanie} dla Tomcat,
         * Jetty, Undertow zgodnie z treścią lekcji. Wypisz wszystkie wiersze jako
         * wyrównaną tabelę tekstową.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IsEmbeddableMethod {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę isEmbeddable(String nazwaKontenera) zwracającą true dla
         * "Tomcat", "Jetty", "Undertow" oraz false dla dowolnej innej nazwy (np. "IIS",
         * "Apache HTTP Server"). Przetestuj ją dla co najmniej 4 nazw (2 prawdziwe,
         * 2 fałszywe) i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ContainerSwapThoughtExperiment {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj dwa Stringi: jeden opisujący (tekstowo, bez realnego kodu Undertow)
         * pseudo-konfigurację embedded Tomcata z tej lekcji (new Tomcat(), setBaseDir,
         * setPort(0), addContext, addServlet), drugi analogiczny pseudo-opis dla
         * Undertow (inny sposób konfiguracji, ale ten sam serwlet HttpServlet).
         * Wypisz oba opisy obok siebie, podkreślając, że logika serwletu się nie zmienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ContainerFactsQuiz {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj 5 twierdzeń prawda/fałsz o kontenerach serwletów na podstawie
         * lekcji, np. "Tomcat jest domyslnym serwerem w Spring Boot" (prawda), "Jetty
         * nie moze byc uzywany jako embedded" (falsz). Dla każdego twierdzenia
         * zbuduj parę (tresc, oczekiwanaOdpowiedz) i wypisz je jako mini quiz z
         * poprawnymi odpowiedziami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMultiServletSummary {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj embedded Tomcat z 3 serwletami zamapowanymi pod "/multi-a", "/multi-b",
         * "/multi-c", z których każdy zwraca nazwę bieżącego wątku. Wykonaj po 1 żądaniu
         * GET na każdą ścieżkę, zbierz nazwy wątków do Set<String>, zmierz łączny czas
         * wykonania wszystkich 3 żądań (System.nanoTime()) i na końcu wypisz
         * podsumowanie: liczba obsłużonych żądań, liczba unikalnych wątków, łączny czas w ms.
         */
        public static void main(String[] args) { }
    }
}
