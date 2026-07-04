package com.example.javaquest._07_servlets.Lesson13_ServletContext;

public class _Exercises_Lesson13_ServletContext {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ContextInitParameterReadback {
        /*
         * 🧪 Zadanie 1:
         * Uruchom embedded Tomcat (port 0). Przed startem wywołaj
         * context.addParameter("appName", "JavaQuest Sklep Demo") na obiekcie Context
         * zwróconym przez tomcat.addContext("", null). Zarejestruj jeden serwlet mapowany
         * na "/info", odczytujący getServletContext().getInitParameter("appName") i
         * odsyłający jego wartość. Wyślij GET i wypisz body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AttributePersistsAcrossRequestsSameServlet {
        /*
         * 🧪 Zadanie 2:
         * Serwlet mapowany na "/stan" ustawia atrybut kontekstu "wersja"="1.0" TYLKO jeśli
         * jeszcze nie istnieje (getAttribute == null), inaczej go nie zmienia. Wyślij 2
         * kolejne żądania GET i zweryfikuj, że w obu odpowiedziach atrybut ma tę samą,
         * ustawioną raz wartość "1.0" – atrybuty kontekstu przetrwały między żądaniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TwoServletsShareAttribute {
        /*
         * 🧪 Zadanie 3:
         * Zarejestruj DWA różne serwlety (różne klasy) w tym samym kontekście: "/ustaw"
         * (ustawia getServletContext().setAttribute("wiadomosc", "Halo")) i "/odczytaj"
         * (tylko odczytuje ten sam atrybut). Wyślij żądanie do "/ustaw", a potem do
         * "/odczytaj" – zweryfikuj, że drugi serwlet widzi wartość ustawioną przez pierwszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SharedCounterAcrossTwoInstances {
        /*
         * 🧪 Zadanie 4:
         * Zarejestruj DWIE instancje serwletu inkrementującego atrybut kontekstu "licznik"
         * pod różnymi ścieżkami ("/x" i "/y"). Wyślij żądanie do "/x" (licznik=1), potem
         * do "/y" (licznik=2) – zweryfikuj, że mimo dwóch RÓŻNYCH instancji obiektu servletu,
         * licznik jest wspólny (bo ServletContext jest jeden dla całej aplikacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MultipleContextInitParameters {
        /*
         * 🧪 Zadanie 5:
         * Ustaw 3 parametry kontekstu przed startem: context.addParameter("appName", ...),
         * context.addParameter("wersja", "2.1"), context.addParameter("srodowisko", "test").
         * W jednym serwlecie odczytaj wszystkie trzy przez getServletContext().getInitParameter(...)
         * i wypisz razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MissingAttributeReturnsNullDefault {
        /*
         * 🧪 Zadanie 6:
         * W serwlecie odczytaj getServletContext().getAttribute("nieustawiony") PRZED jego
         * jakimkolwiek ustawieniem gdziekolwiek w aplikacji. Zweryfikuj, że zwraca null i
         * obsłuż to jako wartość domyślną 0 (Integer) zamiast rzucać wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InitParameterVsAttributeSeparateNamespaces {
        /*
         * 🧪 Zadanie 7:
         * Ustaw context.addParameter("nazwa", "StaticValue") ORAZ (w innym serwlecie, przy
         * pierwszym wywołaniu) getServletContext().setAttribute("nazwa", "DynamicValue")
         * pod TĄ SAMĄ nazwą "nazwa". Zweryfikuj, że getInitParameter("nazwa") oraz
         * getAttribute("nazwa") zwracają DWIE RÓŻNE wartości – to osobne przestrzenie nazw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RemoveContextAttribute {
        /*
         * 🧪 Zadanie 8:
         * Serwlet z parametrem "?ustaw=true" ustawia atrybut kontekstu "tymczasowy"="X".
         * Serwlet z parametrem "?usun=true" wywołuje removeAttribute("tymczasowy"). Wykonaj
         * sekwencję: ustaw, potem odczytaj (obecny), potem usun, potem odczytaj ponownie
         * (zweryfikuj null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ContextInitParameterNamesEnumeration {
        /*
         * 🧪 Zadanie 9:
         * Ustaw 4 parametry kontekstu przed startem. W serwlecie użyj
         * getServletContext().getInitParameterNames() (Enumeration<String>), zbierz do
         * listy, posortuj i wypisz wszystkie nazwy parametrów kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SingleServletSequentialVisitCounter {
        /*
         * 🧪 Zadanie 10:
         * Serwlet mapowany na "/odwiedz" inkrementuje atrybut kontekstu "liczbaOdwiedzin"
         * przy każdym żądaniu. Wyślij 3 kolejne żądania GET tym samym servletem i
         * zweryfikuj wartości 1, 2, 3 w kolejnych odpowiedziach.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CounterAndStatsTwoServletsVariant {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz wzorzec z teorii lekcji, ale z własnymi nazwami: serwlet "/rejestruj"
         * inkrementuje atrybut "totalHits", serwlet "/raport" go odczytuje razem z
         * parametrem kontekstu "appName". Wyślij "/rejestruj" 4 razy, potem "/raport"
         * raz i zweryfikuj wartość 4 w raporcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_IncrementDecrementReadThreeServlets {
        /*
         * 🧪 Zadanie 12:
         * Zarejestruj 3 serwlety: "/plus" (inkrementuje atrybut "wartosc"), "/minus"
         * (dekrementuje, z podłogą 0 – nie może zejść poniżej 0), "/wartosc" (tylko
         * odczytuje). Wykonaj sekwencję: plus, plus, plus, minus, minus, minus, minus
         * (próba zejścia poniżej 0), wartosc – zweryfikuj końcową wartość 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SharedLogListAppendedByTwoServlets {
        /*
         * 🧪 Zadanie 13:
         * Serwlet "/a" i serwlet "/b" (różne klasy) każdy dopisuje własny tag ("A-wizyta"
         * / "B-wizyta") do współdzielonej listy kontekstu List<String> "log". Serwlet "/c"
         * tylko odczytuje i wypisuje CAŁĄ zaakumulowaną listę. Wykonaj sekwencję a, b, a, c
         * i zweryfikuj kolejność 3 wpisów w logu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConfigAndContextCoexistence {
        /*
         * 🧪 Zadanie 14:
         * Połącz z Lesson12: serwlet ma WŁASNY parametr init "servletLabel" (ustawiony
         * przez Wrapper.addInitParameter PRZED startem) ORAZ czyta WSPÓLNY parametr
         * kontekstu "appName" (context.addParameter). Wypisz obie wartości razem w
         * odpowiedzi, jasno oznaczając które źródło (config vs context) dostarczyło którą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SynchronizedSequentialIncrementSafety {
        /*
         * 🧪 Zadanie 15:
         * Serwlet inkrementuje atrybut kontekstu "licznik" wewnątrz bloku
         * synchronized(getServletContext()) { ... } (odczyt, inkrementacja, zapis). Wyślij
         * 5 kolejnych żądań SEKWENCYJNIE (jedno po drugim, program ma zakończyć się w kilka
         * sekund) i zweryfikuj, że licznik osiąga dokładnie 5 – żadna aktualizacja nie zginęła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ProductViewsMapSharedByTwoServlets {
        /*
         * 🧪 Zadanie 16:
         * Kontekst trzyma atrybut Map<String,Integer> "widokiProduktow". Serwlet
         * "/produkt?id=X" inkrementuje licznik wyświetleń danego produktu w mapie.
         * Serwlet "/produkty-raport" (inna klasa) wypisuje CAŁĄ mapę posortowaną po id.
         * Wykonaj kilka wywołań "/produkt" z różnymi id (w tym powtórzenia) i sprawdź raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImmutableInitParamVsMutableAttributeSameName {
        /*
         * 🧪 Zadanie 17:
         * Ustaw context.addParameter("appName", "OryginalnaNazwa") przed startem. W innym
         * serwlecie wywołaj getServletContext().setAttribute("appName", "ZmienionaNazwa")
         * (celowo TA SAMA nazwa co parametr init). Zweryfikuj, że getInitParameter("appName")
         * WCIĄŻ zwraca "OryginalnaNazwa" (niezmienne), a getAttribute("appName") zwraca
         * nową wartość "ZmienionaNazwa" – dwie osobne przestrzenie danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OnlineUsersSetAcrossThreeServlets {
        /*
         * 🧪 Zadanie 18:
         * Kontekst trzyma atrybut Set<String> "onlineUzytkownicy". Serwlet "/login?user=X"
         * dodaje X do zbioru, "/logout?user=X" usuwa X ze zbioru, "/kto-online" wypisuje
         * całą zawartość. Wykonaj: login "jan", login "ewa", logout "jan", kto-online –
         * zweryfikuj, że pozostała tylko "ewa".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SessionVsContextCombinedInOneServlet {
        /*
         * 🧪 Zadanie 19:
         * Połącz z Lesson11: JEDEN serwlet trzyma zarówno atrybut SESJI "mojeOdwiedziny"
         * (per-klient, jak w HttpSession) jak i atrybut KONTEKSTU "wszystkieOdwiedziny"
         * (globalny, wspólny dla wszystkich). Wyślij 2 żądania klientem A i 1 żądanie
         * klientem B (osobny CookieManager) – zweryfikuj, że "mojeOdwiedziny" różni się
         * między klientami (2 i 1), a "wszystkieOdwiedziny" wynosi łącznie 3 dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_InitParameterVsAttributeOnlyAsAttribute {
        /*
         * 🧪 Zadanie 20:
         * Ustaw atrybut kontekstu "dynamiczneDane"="X" TYLKO przez setAttribute (bez
         * odpowiadającego context.addParameter o tej samej nazwie). Zweryfikuj w serwlecie,
         * że getInitParameter("dynamiczneDane") zwraca null, a getAttribute("dynamiczneDane")
         * zwraca "X" – wypisz obie wartości obok siebie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreeServletAppWideDashboard {
        /*
         * 🧪 Zadanie 21:
         * Zarejestruj 3 serwlety: "/logowanie" (inkrementuje "liczbaLogowan"), "/zakup"
         * (inkrementuje "liczbaZakupow"), "/dashboard" (odczytuje OBA atrybuty i buduje
         * podsumowanie). Wykonaj 3 wywołania "/logowanie", 2 wywołania "/zakup", a na
         * końcu "/dashboard" – zweryfikuj wartości 3 i 2 w raporcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AllFourMechanismsInOneRequest {
        /*
         * 🧪 Zadanie 22:
         * Połącz Lesson10 (cookie "motyw"), Lesson11 (atrybut sesji "mojeOdwiedziny"),
         * Lesson12 (parametr init servletu "przywitanie" ustawiony przez Wrapper) oraz
         * Lesson13 (atrybut kontekstu "wszystkieOdwiedziny") w JEDNYM serwlecie i JEDNEJ
         * odpowiedzi. Wyślij 2 żądania tym samym klientem i wypisz wszystkie 4 wartości
         * przy każdym z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HitCounterPerPageWithReportServlet {
        /*
         * 🧪 Zadanie 23:
         * Kontekst trzyma Map<String,Integer> "licznikStron". Ogólny serwlet mapowany np.
         * na "/strona" czyta parametr "?nazwa=X" i inkrementuje licznik dla X. Osobny
         * serwlet "/raport-stron" wypisuje wszystkie strony posortowane malejąco po
         * liczbie odwiedzin. Wykonaj kilka wywołań z różnymi nazwami stron (w tym powtórzenia)
         * i zweryfikuj kolejność w raporcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SynchronizedVsAtomicCorrectnessDemo {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj serwlet inkrementujący atrybut kontekstu "licznik" (Integer) WEWNĄTRZ
         * synchronized(getServletContext()) (get -> increment -> set). Wyślij SEKWENCYJNIE
         * 20 żądań (program kończy się w kilka sekund) i zweryfikuj, że finalna wartość to
         * dokładnie 20 – w komentarzu/println wyjaśnij, dlaczego brak synchronized mógłby
         * (przy współbieżnych żądaniach z wielu wątków) prowadzić do "zgubionych aktualizacji".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_InventoryStockManagement {
        /*
         * 🧪 Zadanie 25:
         * Kontekst trzyma Map<String,Integer> "magazyn" zainicjalizowaną startowymi
         * stanami (np. "Chleb"=5, "Mleko"=3). Serwlet "/kup?produkt=X" dekrementuje stan,
         * zwracając błąd tekstowy gdy stan osiągnął 0. Serwlet "/stan-magazynowy" wypisuje
         * cały aktualny stan. Kup "Chleb" 5 razy (ostatni raz oczekiwany błąd "Brak na stanie")
         * i sprawdź raport końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FeatureFlagToggleThenCheckSequence {
        /*
         * 🧪 Zadanie 26:
         * Kontekst trzyma Map<String,Boolean> "flagi". Serwlet "/admin/toggle?flaga=X"
         * przełącza (neguje) wartość flagi X. Serwlet "/funkcja?flaga=X" zwraca różny
         * tekst zależnie od stanu flagi X (domyślnie false, jeśli nieustawiona). Wykonaj
         * sekwencję: sprawdź (false), przełącz, sprawdź (true), przełącz, sprawdź (false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AuditTrailAcrossThreePaths {
        /*
         * 🧪 Zadanie 27:
         * Kontekst trzyma List<String> "auditLog". KAŻDE wywołanie dowolnego z 3 serwletów
         * ("/x", "/y", "/z") ma dopisywać linię w formacie "N: /sciezka" (N = kolejny numer
         * globalny, licznik trzymany też w kontekście) do listy. Wykonaj sekwencję x, y, x,
         * z, y (5 wywołań), a potem żądanie do "/audit" wypisujące pełny, uporządkowany log.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AtomicIntegerRefactorComparison {
        /*
         * 🧪 Zadanie 28:
         * Zamiast synchronized+Integer (jak w Zadaniu 24), zbuduj serwlet trzymający
         * java.util.concurrent.atomic.AtomicInteger jako atrybut kontekstu (utworzony i
         * zapisany przy PIERWSZYM żądaniu, jeśli jeszcze nie istnieje), inkrementowany
         * metodą incrementAndGet(). Wyślij sekwencyjnie 10 żądań i zweryfikuj wynik 10,
         * porównując w komentarzu prostotę tego podejścia względem ręcznej synchronizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConfigPlusContextTenRequestReport {
        /*
         * 🧪 Zadanie 29:
         * Zarejestruj 3 serwlety, każdy z WŁASNYMI parametrami init (Lesson12) ORAZ
         * odczytujące/zapisujące 2 wspólne atrybuty kontekstu (Lesson13). Wykonaj mieszaną
         * sekwencję 10 żądań rozłożonych między te 3 serwlety i wypisz na końcu skonsolidowany
         * raport stanu: parametry per-servlet obok wspólnego stanu kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMiniApplication {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini aplikację e-commerce łączącą WSZYSTKIE mechanizmy z lekcji 10-13:
         * cookie "jezyk" (Lesson10), koszyk w sesji per-klient (Lesson11), serwlet
         * konfigurowany parametrami init np. nazwą sklepu (Lesson12), oraz wspólny
         * magazyn/licznik odwiedzin w kontekście (Lesson13) – minimum 4 serwlety/ścieżki.
         * Opisz i wykonaj pełną sekwencję żądań (ustaw język, dodaj do koszyka x2, sprawdź
         * magazyn, sprawdź globalny licznik odwiedzin) i wypisz końcowy raport potwierdzający,
         * że wszystkie cztery mechanizmy działają poprawnie razem.
         */
        public static void main(String[] args) { }
    }
}
