package com.example.javaquest._07_servlets.Lesson03_ServletProjectSetup;

public class _Exercises_Lesson03_ServletProjectSetup {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MinimalEmbeddedTomcatFromScratch {
        /*
         * 🧪 Zadanie 1:
         * Napisz od zera pełny wzorzec z tej lekcji: new Tomcat(), setBaseDir na
         * Files.createTempDirectory("cwiczenie1"), getConnector().setPort(0),
         * addContext("", null), Tomcat.addServlet + addServletMappingDecoded pod
         * "/pierwsza-proba" (serwlet zwraca "Dziala!"), start() w try, stop()+destroy()
         * w finally. Wykonaj GET i wypisz status oraz ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TempBaseDirExists {
        /*
         * 🧪 Zadanie 2:
         * Utwórz katalog tymczasowy przez Files.createTempDirectory("baza-tomcata")
         * i wypisz jego pełną ścieżkę ORAZ wynik Files.exists(sciezka) PRZED
         * ustawieniem go jako tomcat.setBaseDir(...) – potwierdź, że katalog musi
         * istnieć fizycznie na dysku, zanim Tomcat go użyje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PortZeroGivesRandomPort {
        /*
         * 🧪 Zadanie 3:
         * Uruchom osadzony Tomcat z getConnector().setPort(0), po tomcat.start()
         * wypisz wylosowany port (tomcat.getConnector().getLocalPort()). Uruchom
         * cały proces DWA razy pod rząd (dwie osobne instancje Tomcat w tym samym
         * main()) i wypisz oba porty – zwróć uwagę, czy są takie same czy różne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RootContextPath {
        /*
         * 🧪 Zadanie 4:
         * Utwórz kontekst tomcat.addContext("", null) (kontekst główny, root) z
         * serwletem zamapowanym pod "/root-test". Wykonaj GET na
         * "http://localhost:<port>/root-test" (BEZ żadnego dodatkowego segmentu
         * nazwy aplikacji w ścieżce) i wypisz status oraz ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ServletRegistrationNameIsInternal {
        /*
         * 🧪 Zadanie 5:
         * Zarejestruj serwlet metodą Tomcat.addServlet(context, "wewnetrznaNazwa",
         * new TwojServlet()) i zamapuj go pod ŚCIEŻKĄ "/publiczna-sciezka" (inną niż
         * nazwa rejestracji). Wykonaj GET na "/publiczna-sciezka" i wypisz odpowiedź,
         * potwierdzając, że klient używa ścieżki URL, nie wewnętrznej nazwy serwletu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MissingMappingUnreachable {
        /*
         * 🧪 Zadanie 6:
         * Zarejestruj serwlet przez Tomcat.addServlet, ale CELOWO pomiń wywołanie
         * addServletMappingDecoded. Wykonaj GET na dowolną ścieżkę, pod którą chciałbyś
         * go widzieć (np. "/niedostepny") i wypisz status odpowiedzi – zweryfikuj, że
         * bez mapowania serwlet jest nieosiągalny (404).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TryFinallyAlwaysStops {
        /*
         * 🧪 Zadanie 7:
         * Zamapuj serwlet pod "/finally-test", którego doGet() rzuca
         * RuntimeException. W main() wykonaj GET na tę ścieżkę wewnątrz try, a w
         * finally i tak wywołaj tomcat.stop() i tomcat.destroy(), wypisując na końcu
         * komunikat "Tomcat zatrzymany" – potwierdź, że blok finally wykonuje się
         * mimo błędu wewnątrz serwletu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_JakartaPackageCheck {
        /*
         * 🧪 Zadanie 8:
         * Wypisz pełną nazwę pakietu (getPackageName()) klasy HttpServlet użytej w
         * Twoim imporcie (jakarta.servlet.http.HttpServlet) oraz klasy Tomcat
         * (org.apache.catalina.startup.Tomcat) – zweryfikuj, że oba należą do
         * "nowego" świata jakarta.*, zgodnie z Tomcatem 10.x użytym w tym kursie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_StepByStepCommentedPattern {
        /*
         * 🧪 Zadanie 9:
         * Przepisz wzorzec z tej lekcji krok po kroku, dodając WŁASNY komentarz przy
         * każdej linii (setBaseDir, setPort(0), addContext, addServlet,
         * addServletMappingDecoded, start, stop+destroy) wyjaśniający, po co ta linia
         * istnieje. Zamapuj serwlet pod "/wlasny-opis" zwracający "Rozumiem wzorzec.".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MultipleServletsOneSetup {
        /*
         * 🧪 Zadanie 10:
         * W JEDNYM wywołaniu addContext zarejestruj dwa serwlety: pod "/setup-a" i
         * "/setup-b", każdy zwracający inny tekst. Wykonaj GET na obie ścieżki w tym
         * samym uruchomieniu programu i wypisz oba wyniki – potwierdzając, że jeden
         * kontekst może hostować wiele serwletów jednocześnie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ExtractSetupIntoHelperMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę statyczną Tomcat startTomcat(String nazwaKatalogu) zwracającą
         * gotowy, URUCHOMIONY obiekt Tomcat (z ustawionym baseDir i portem 0, ale BEZ
         * żadnych serwletów). W main() użyj tej metody, dodaj serwlet już PO
         * uruchomieniu (jeśli API na to pozwala) lub przenieś rejestrację przed start()
         * wewnątrz metody – wykonaj GET na zamapowaną ścieżkę i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReusableRequestHelper {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę pomocniczą String get(HttpClient client, String url) rzucającą
         * IOException/InterruptedException dalej, budującą GET i zwracającą ciało
         * odpowiedzi jako String. Użyj jej do odpytania DWÓCH różnych ścieżek
         * zamapowanych w jednym kontekście Tomcata i wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PortZeroTwoIndependentServers {
        /*
         * 🧪 Zadanie 13:
         * Uruchom DWA niezależne obiekty Tomcat (każdy z własnym baseDir, oba na
         * porcie 0) jednocześnie w tym samym main(), każdy z innym serwletem.
         * Wypisz oba wylosowane porty i wykonaj po jednym żądaniu GET do każdego z
         * nich, potwierdzając brak konfliktu portów. Zatrzymaj oba serwery w finally.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MissingBaseDirThrows {
        /*
         * 🧪 Zadanie 14:
         * Spróbuj ustawić tomcat.setBaseDir na ścieżkę do NIEISTNIEJĄCEGO katalogu
         * (np. String losowy, który na pewno nie istnieje na dysku, bez tworzenia go).
         * Owiń tomcat.start() w try-catch i wypisz, czy i jaki wyjątek został rzucony –
         * porównaj z poprawnym przypadkiem użycia Files.createTempDirectory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ServletMappingOverwrite {
        /*
         * 🧪 Zadanie 15:
         * Zarejestruj dwa różne serwlety (A i B) i spróbuj zamapować OBA pod tą samą
         * ścieżką "/konflikt" (dwa wywołania addServletMappingDecoded z tym samym
         * URL-em, ale różnymi nazwami serwletów). Wykonaj GET na "/konflikt" i wypisz,
         * KTÓRY serwlet faktycznie odpowiedział (na podstawie treści odpowiedzi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FullPatternWithTiming {
        /*
         * 🧪 Zadanie 16:
         * Przepisz pełny wzorzec z tej lekcji, dodając pomiar czasu (System.nanoTime())
         * wokół samego tomcat.start() oraz osobno wokół wykonania żądania HTTP.
         * Zamapuj serwlet pod "/pomiar-setup" i wypisz oba czasy w milisekundach
         * osobno (czas startu serwera vs czas obsługi żądania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_JakartaVsJavaxCommentDemo {
        /*
         * 🧪 Zadanie 17:
         * Napisz komentarz (i odpowiadający mu println) wyjaśniający, dlaczego import
         * "javax.servlet.http.HttpServlet" NIE skompilowałby się w tym projekcie
         * (Tomcat 10.x, jakarta.servlet.*). Następnie zamapuj poprawny serwlet
         * (z importem jakarta.servlet.http.HttpServlet) pod "/wersja-api" i wykonaj
         * na nim GET, wypisując odpowiedź jako potwierdzenie poprawnej wersji API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MultipleMappingsSamePathDifferentMethods {
        /*
         * 🧪 Zadanie 18:
         * Zamapuj JEDEN serwlet obsługujący zarówno doGet(), jak i doPost() pod
         * ścieżką "/setup-oba". Wykonaj kolejno żądanie GET i POST na tę samą ścieżkę,
         * wypisując status i ciało obu odpowiedzi w ramach jednego uruchomienia
         * osadzonego Tomcata (bez ponownego tworzenia kontekstu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CleanupVerifiedByPortRelease {
        /*
         * 🧪 Zadanie 19:
         * Uruchom Tomcat na porcie 0, zapamiętaj wylosowany port, zatrzymaj serwer
         * (stop()+destroy()). Następnie uruchom NOWY obiekt Tomcat RÓWNIEŻ z portem 0
         * i wypisz jego wylosowany port – skomentuj (println), że poprawne
         * stop()+destroy() zwalnia zasoby, umożliwiając kolejne uruchomienia bez
         * wycieków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConfigurationChecklistValidator {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę List<String> validateSetup(Tomcat tomcat, Context context),
         * sprawdzającą (po uruchomieniu) czy: port lokalny > 0, kontekst ma ścieżkę
         * "" (getPath().equals("")), baseDir nie jest pusty – zwracającą listę
         * znalezionych problemów (pustą, jeśli wszystko OK). Wywołaj ją po starcie
         * poprawnie skonfigurowanego Tomcata i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullSetupWithLifecycleAndRequest {
        /*
         * 🧪 Zadanie 21:
         * Połącz wzorzec setupu z tej lekcji z cyklem życia z Lesson01: serwlet
         * loguje init()/doGet()/destroy(), zamapowany pod "/pelny-cykl". Uruchom
         * Tomcat, wykonaj 2 żądania GET, zatrzymaj serwer i wypisz KOLEJNOŚĆ
         * wszystkich zdarzeń (init, doGet x2, destroy) zebraną w jednej liście.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReusableEmbeddedTomcatBuilder {
        /*
         * 🧪 Zadanie 22:
         * Napisz klasę pomocniczą (lub metodę) EmbeddedTomcatBuilder, pozwalającą
         * łańcuchowo dodawać serwlety (metoda addServlet(sciezka, HttpServlet)) i na
         * końcu wywołać buildAndStart() zwracające uruchomiony Tomcat. Użyj jej do
         * zarejestrowania 3 serwletów pod "/b1", "/b2", "/b3" i wykonaj GET na
         * wszystkie trzy, wypisując wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrentPortsStressCheck {
        /*
         * 🧪 Zadanie 23:
         * Uruchom w pętli 3 NIEZALEŻNE instancje Tomcat (każda z innym baseDir,
         * wszystkie na porcie 0), każda z jednym serwletem zwracającym swój numer
         * instancji. Zbierz wszystkie 3 wylosowane porty do Set<Integer> i wypisz
         * jego rozmiar – potwierdź brak kolizji portów. Zatrzymaj wszystkie 3 w
         * finally (osobno dla każdej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FailureRecoveryScenario {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj błąd konfiguracji (np. spróbuj wywołać addServletMappingDecoded z
         * nieistniejącą nazwą serwletu, której nie zarejestrowano przez addServlet).
         * Przechwyć ewentualny wyjątek przy starcie/rejestracji, wypisz komunikat
         * błędu, a następnie POPRAWNIE skonfiguruj i uruchom działający serwlet pod
         * "/naprawiony", potwierdzając odzyskanie sprawności programu przez GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FullDiagnosticReport {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj serwlet diagnostyczny pod "/diagnostyka", którego doGet() zwraca w
         * ciele odpowiedzi: aktualny port (z req.getLocalPort()), nazwę wątku
         * obsługującego, oraz liczbę dotychczas obsłużonych żądań (licznik w polu
         * serwletu). Wykonaj 3 żądania GET i wypisz pełną treść każdej odpowiedzi
         * osobno, budując mini-raport diagnostyczny konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SetupPatternUnitTestStyle {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę boolean verifyPattern(int status, String body, String
         * expectedBody), porównującą oczekiwane wartości ze status==200 i
         * body.equals(expectedBody). Użyj jej do "ręcznego testowania" 3 różnych
         * serwletów zamapowanych pod "/t1", "/t2", "/t3" w jednym Tomcacie, wypisując
         * PASS/FAIL dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GracefulShutdownUnderLoad {
        /*
         * 🧪 Zadanie 27:
         * Zamapuj serwlet pod "/obciazenie", inkrementujący AtomicInteger przy każdym
         * doGet(). Wykonaj 10 żądań GET w pętli, PO CZYM natychmiast wywołaj
         * stop()+destroy() i wypisz końcową wartość licznika – potwierdzając, że
         * wszystkie 10 żądań zostało obsłużonych PRZED zamknięciem serwera (bo
         * wywołania są sekwencyjne, nie asynchroniczne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultiContextComparisonNote {
        /*
         * 🧪 Zadanie 28:
         * Uruchom Tomcat z DWOMA kontekstami: root ("", null) z serwletem pod "/root",
         * oraz drugim kontekstem "/app" (addContext("/app", null)) z serwletem pod
         * "/app/powitanie". Wykonaj GET na obie pełne ścieżki i wypisz oba wyniki,
         * ilustrując, że jeden Tomcat może hostować wiele kontekstów (aplikacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConfigurationAsDataDrivenLoop {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj Map<String, String> konfiguracja {"/d1" -> "Dane 1", "/d2" -> "Dane 2",
         * "/d3" -> "Dane 3"}. W pętli po tej mapie zarejestruj po jednym serwlecie na
         * każdą ścieżkę (zwracającym odpowiadającą wartość). Po starcie Tomcata
         * wykonaj GET na każdą ze ścieżek z mapy i wypisz porównanie zwróconego ciała
         * z oczekiwaną wartością z mapy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneFullSetupPipeline {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "pipeline" testowy: (1) utwórz i uruchom Tomcat wg pełnego
         * wzorca z tej lekcji z 3 serwletami ("/setup-x", "/setup-y", "/setup-z"),
         * (2) wykonaj po jednym żądaniu GET na każdą ścieżkę, zbierając wyniki do
         * Map<String, Integer> (ścieżka -> status), (3) po zatrzymaniu serwera wypisz
         * pełne podsumowanie: liczba ścieżek przetestowanych, liczba sukcesów
         * (status==200), łączny czas działania serwera w ms (od start() do stop()).
         */
        public static void main(String[] args) { }
    }
}
