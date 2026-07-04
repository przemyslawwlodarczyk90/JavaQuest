package com.example.javaquest._07_servlets.Lesson12_ServletConfig;

public class _Exercises_Lesson12_ServletConfig {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleInitParameterReadback {
        /*
         * 🧪 Zadanie 1:
         * Uruchom embedded Tomcat (port 0). Zarejestruj serwlet przez
         * Wrapper wrapper = Tomcat.addServlet(context, "welcomeServlet", new WelcomeServlet())
         * i ustaw wrapper.addInitParameter("welcomeMessage", "Witaj w sklepie!") PRZED
         * tomcat.start(). W serwlecie odczytaj getServletConfig().getInitParameter("welcomeMessage")
         * i odeślij w odpowiedzi. Wyślij GET na "/powitanie" i wypisz body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ShorthandGetInitParameter {
        /*
         * 🧪 Zadanie 2:
         * Do serwletu z Zadania 1 dodaj drugi odczyt: this.getInitParameter("welcomeMessage")
         * (skrót HttpServlet, bez jawnego getServletConfig()). Zweryfikuj, że oba sposoby
         * zwracają IDENTYCZNĄ wartość – wypisz obie linie w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MultipleInitParametersOnOneServlet {
        /*
         * 🧪 Zadanie 3:
         * Ustaw na jednym Wrapperze dwa parametry: addInitParameter("maxItems", "50") oraz
         * addInitParameter("minItems", "1"). W serwlecie odczytaj OBA po nazwie i wypisz
         * w jednej odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InitParameterNamesEnumeration {
        /*
         * 🧪 Zadanie 4:
         * Ustaw 3 parametry init na Wrapperze ("a", "b", "c"). W serwlecie użyj
         * getServletConfig().getInitParameterNames() (Enumeration<String>), zamień na
         * listę (Collections.list), posortuj i wypisz wszystkie nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MissingParameterReturnsNull {
        /*
         * 🧪 Zadanie 5:
         * W serwlecie z jednym parametrem "welcomeMessage" spróbuj odczytać
         * getInitParameter("nieistniejacy") i zweryfikuj, że zwraca null – wypisz
         * "brak parametru" zamiast rzucać wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SameClassTwoIndependentConfigs {
        /*
         * 🧪 Zadanie 6:
         * Zarejestruj TĘ SAMĄ klasę serwletu DWA razy pod różnymi nazwami ("servletA" pod
         * "/a", "servletB" pod "/b"), każdy z INNĄ wartością parametru "greeting"
         * ("Czesc z A!" i "Czesc z B!"). Wyślij GET do obu ścieżek i zweryfikuj, że każdy
         * zwraca SWOJĄ własną wartość, mimo tej samej klasy servletu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ZeroInitParametersEmptyEnumeration {
        /*
         * 🧪 Zadanie 7:
         * Zarejestruj serwlet BEZ żadnych wywołań addInitParameter. W serwlecie sprawdź
         * getServletConfig().getInitParameterNames().hasMoreElements() i zweryfikuj, że
         * zwraca false – wypisz "Brak parametrow init" w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NumericParameterParsing {
        /*
         * 🧪 Zadanie 8:
         * Ustaw addInitParameter("maxItems", "100"). W serwlecie odczytaj wartość jako
         * String i sparsuj do int (Integer.parseInt), a następnie wypisz wynik pomnożony
         * przez 2 (weryfikacja poprawnego parsowania: 200).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BooleanParameterParsing {
        /*
         * 🧪 Zadanie 9:
         * Ustaw addInitParameter("debugMode", "true"). W serwlecie sparsuj wartość
         * Boolean.parseBoolean(...) i na tej podstawie dołącz do odpowiedzi dodatkową
         * linię "TRYB DEBUG WLACZONY" tylko gdy flaga jest true. Zweryfikuj obecność tej linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CommaSeparatedListParameter {
        /*
         * 🧪 Zadanie 10:
         * Ustaw addInitParameter("colors", "red,green,blue"). W serwlecie rozbij wartość
         * ręcznie po przecinku (String.split(",")) i wypisz każdy kolor w osobnej linii
         * odpowiedzi, z numerem porządkowym.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeServletsThreeConfigsReport {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj 3 serwlety (mogą być 2 różne klasy) pod ścieżkami "/s1", "/s2", "/s3",
         * każdy z INNYM zestawem parametrów init (różne nazwy i/lub wartości). Wyślij GET
         * do wszystkich trzech i wypisz skonsolidowany raport: ścieżka -> odczytane parametry.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReadOnlyFlagControllingPutDelete {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj serwlet z doPut()/doDelete() (nawiązanie do Lesson08) kontrolowany
         * parametrem init "readOnly". Ustaw addInitParameter("readOnly", "true") – PUT i
         * DELETE mają wtedy zwracać resp.sendError(403, "Serwlet w trybie tylko do odczytu").
         * Zweryfikuj status 403 dla obu metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ResponseFormatFromInitParameter {
        /*
         * 🧪 Zadanie 13:
         * Ustaw addInitParameter("responseFormat", "text/plain") na jednym Wrapperze oraz
         * "text/html" na drugim (2 rejestracje tej samej klasy, różne ścieżki). Serwlet
         * ustawia resp.setContentType(...) na podstawie tego parametru. Wyślij GET do obu
         * i zweryfikuj nagłówek Content-Type każdej odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SameParameterNameDifferentParsingLogic {
        /*
         * 🧪 Zadanie 14:
         * Zarejestruj 2 serwlety z parametrem o TEJ SAMEJ nazwie "limit", ale w jednym
         * serwlecie interpretowanym jako liczba całkowita (limit elementów listy), a w
         * drugim jako liczba w procentach (limit% – dopisywany znak "%" w odpowiedzi).
         * Zweryfikuj różne, poprawne dla każdego kontekstu, wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FailFastRequiredParameterValidation {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj serwlet, który przy PIERWSZYM żądaniu sprawdza, czy parametr init
         * "requiredMessage" jest ustawiony. Zarejestruj JEDNĄ instancję BEZ tego parametru
         * i wyślij żądanie – serwlet ma odpowiedzieć statusem 500 z komunikatem
         * "Brakuje wymaganego parametru requiredMessage".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FormParameterOverridesInitDefault {
        /*
         * 🧪 Zadanie 16:
         * Połącz z Lesson09: ustaw addInitParameter("domyslnyJezyk", "pl") jako wartość
         * domyślną. Serwlet doPost() sprawdza req.getParameter("jezyk") – jeśli podane
         * (np. "jezyk=en"), używa go, w przeciwnym razie używa wartości z init param.
         * Przetestuj raz z podanym parametrem formularza, raz bez niego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultiLanguageGreetingTwoRegistrations {
        /*
         * 🧪 Zadanie 17:
         * Zarejestruj TĘ SAMĄ klasę serwletu 2 razy: "greetingPl" pod "/pl" z
         * addInitParameter("lang","pl") i "greetingEn" pod "/en" z addInitParameter("lang","en").
         * Serwlet ma zaszytą mapę tłumaczeń (Map<String,String>) i zwraca powitanie w
         * odpowiednim języku na podstawie parametru "lang". Wyślij GET do obu ścieżek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FiveParametersCountVerification {
        /*
         * 🧪 Zadanie 18:
         * Ustaw 5 różnych parametrów init na jednym Wrapperze. W serwlecie policz liczbę
         * elementów zwróconych przez getInitParameterNames() (Collections.list(...).size())
         * i zweryfikuj, że wynosi dokładnie 5, a następnie odczytaj każdy z osobna po nazwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NumericLimitCappingListSize {
        /*
         * 🧪 Zadanie 19:
         * Ustaw addInitParameter("maxItems", "3"). Serwlet ma zaszytą wewnętrzną listę 10
         * elementów (np. nazw produktów) i w doGet() zwraca TYLKO pierwsze maxItems z nich
         * (Integer.parseInt(getInitParameter("maxItems"))). Zweryfikuj, że odpowiedź
         * zawiera dokładnie 3 elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SameClassThreeEndpointsResultTable {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj TĘ SAMĄ klasę serwletu 3 razy pod różnymi ścieżkami ("/w1","/w2","/w3"),
         * z rosnącą wartością pojedynczego parametru "mnoznik" (1, 2, 3), gdzie servlet
         * zwraca wynik obliczenia (np. 10 * mnoznik). Wyślij żądania do wszystkich trzech
         * w pętli i zbuduj tabelę wyników (ścieżka -> mnoznik -> wynik).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_PerServletVsSharedConceptualComparison {
        /*
         * 🧪 Zadanie 21:
         * Zarejestruj 2 serwlety, każdy z WŁASNYM parametrem init "instanceLabel" (różne
         * wartości). W odpowiedzi każdego serwletu wypisz jego label oraz komentarz/println
         * wyjaśniający, że te wartości SĄ WYŁĄCZNIE per-servlet (ServletConfig) i – w
         * odróżnieniu od ServletContext (Lesson13) – żaden inny serwlet w tej aplikacji NIE
         * MA do nich dostępu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ConfigurableRequestLimitCounter {
        /*
         * 🧪 Zadanie 22:
         * Ustaw addInitParameter("maxRequestsDemo", "3") na serwlecie trzymającym w polu
         * instancyjnym int licznik żądań. Każde żądanie ponad limit ma zwracać
         * "LIMIT OSIAGNIETY", a do limitu "OK". Wyślij 5 kolejnych żądań i zweryfikuj,
         * że pierwsze 3 dają "OK", a kolejne 2 "LIMIT OSIAGNIETY".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FourTenantsSameClassFullReport {
        /*
         * 🧪 Zadanie 23:
         * Zarejestruj TĘ SAMĄ klasę serwletu 4 razy, symulując 4 "sklepy", każdy z parami
         * parametrów init "sklepNazwa" i "waluta" (np. "SklepPL"/"PLN", "SklepDE"/"EUR",
         * "SklepUK"/"GBP", "SklepUS"/"USD") pod ścieżkami "/sklep1".."sklep4". Wyślij po
         * jednym GET do każdego i wypisz skonsolidowany raport wszystkich 4 sklepów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_OverriddenInitMethodValidation {
        /*
         * 🧪 Zadanie 24:
         * Nadpisz w serwlecie metodę init(ServletConfig config) (wywołując super.init(config)
         * na początku), sprawdzając w niej obecność parametru "requiredMessage". Jeśli brak –
         * zapisz w polu instancyjnym flagę błędu, a doGet() ma na tej podstawie zwrócić
         * status 500. Zarejestruj serwlet BEZ tego parametru i zweryfikuj status 500.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DerivedValueFromTwoParameters {
        /*
         * 🧪 Zadanie 25:
         * Ustaw dwa parametry init "cena" ("1000") i "vatProcent" ("23") na jednym
         * Wrapperze. Serwlet oblicza cenę brutto (cena * (1 + vatProcent/100.0)) i zwraca
         * w odpowiedzi. Zarejestruj drugą instancję z innymi wartościami ("cena"="500",
         * "vatProcent"="8") i porównaj wyniki obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplicitInitOverrideStillWorksWithGetServletConfig {
        /*
         * 🧪 Zadanie 26:
         * Nadpisz init(ServletConfig config) RĘCZNIE zapisując config w polu (this.config = config)
         * zamiast polegać wyłącznie na super.init(config) – ale i tak wywołaj super.init(config),
         * żeby getServletConfig() nadal działało poprawnie. Zweryfikuj w doGet(), że
         * getServletConfig().getInitParameter(...) zwraca oczekiwaną wartość mimo ręcznej
         * modyfikacji init().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FeatureToggleCapabilityReport {
        /*
         * 🧪 Zadanie 27:
         * Ustaw 3 parametry logiczne: "featureA"="true", "featureB"="false", "featureC"="true".
         * Serwlet buduje raport capability tekstowy wypisujący stan każdej funkcji (WŁĄCZONA/
         * WYŁĄCZONA) na podstawie Boolean.parseBoolean każdego parametru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FivePermutationsComparisonTable {
        /*
         * 🧪 Zadanie 28:
         * Zarejestruj TĘ SAMĄ klasę serwletu 5 razy z pełną permutacją 3 parametrów
         * (np. różne kombinacje "poziom", "region", "waluta") pod ścieżkami "/p1".."p5".
         * Wyślij żądania do wszystkich 5 i wypisz pełną tabelę porównawczą wszystkich
         * odczytanych konfiguracji obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_EmptyStringVsNotSetDistinction {
        /*
         * 🧪 Zadanie 29:
         * Zarejestruj 2 serwlety tej samej klasy: pierwszy z addInitParameter("welcomeMessage", "")
         * (jawnie pusty string), drugi BEZ żadnego wywołania addInitParameter dla tej nazwy
         * (brak w ogóle). Serwlet ma rozróżnić oba przypadki (getInitParameter zwraca ""
         * vs null) i odpowiednio zaraportować "PUSTY STRING" albo "PARAMETR NIEUSTAWIONY".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ProductCatalogTotalValueAggregation {
        /*
         * 🧪 Zadanie 30:
         * Zarejestruj TĘ SAMĄ klasę serwletu 3 razy jako mini "katalog produktów", każdy
         * z parami parametrów init "nazwaProduktu" i "cena" (np. "Laptop"/"3000",
         * "Telefon"/"1500", "Tablet"/"800") pod ścieżkami "/produkt1".."produkt3". Wyślij
         * żądania do wszystkich trzech, zsumuj odczytane ceny i wypisz raport końcowy z
         * listą produktów oraz łączną wartością katalogu (5300).
         */
        public static void main(String[] args) { }
    }
}
