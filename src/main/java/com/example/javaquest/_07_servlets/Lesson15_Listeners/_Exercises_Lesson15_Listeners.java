package com.example.javaquest._07_servlets.Lesson15_Listeners;

public class _Exercises_Lesson15_Listeners {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicContextListener {
        /*
         * 🧪 Zadanie 1:
         * Napisz ServletContextListener logujący "APP START" w contextInitialized() oraz
         * "APP STOP" w contextDestroyed(). Zarejestruj go przez
         * context.addApplicationListener(NazwaKlasy.class.getName()). Uruchom Tomcat
         * (port 0), wyślij jedno zadanie GET do prostego serwletu, zatrzymaj Tomcat i
         * potwierdź obecność obu logów w konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ContextAttributeSetAtStartup {
        /*
         * 🧪 Zadanie 2:
         * W contextInitialized() ustaw atrybut kontekstu:
         * sce.getServletContext().setAttribute("appName", "JavaQuest"). Serwlet pod
         * "/info" odczytuje ten atrybut przez getServletContext().getAttribute("appName")
         * i zwraca go w treści odpowiedzi. Zweryfikuj wartość w odpowiedzi klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StartupTimestampListener {
        /*
         * 🧪 Zadanie 3:
         * Listener zapisuje System.currentTimeMillis() jako atrybut kontekstu "startTime"
         * w contextInitialized(). Serwlet pod "/uptime" oblicza różnicę
         * (System.currentTimeMillis() - startTime) i zwraca ją jako liczbę milisekund.
         * Wyślij zadanie i wypisz zwrócony czas działania aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MultipleListenersRegistered {
        /*
         * 🧪 Zadanie 4:
         * Zarejestruj dwie OSOBNE klasy ServletContextListener, każda logująca inny,
         * rozpoznawalny komunikat w contextInitialized() i contextDestroyed(). Uruchom i
         * zatrzymaj Tomcat, wypisz oba zestawy logów, potwierdzając, że oba listenery
         * zostały wywołane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ListenerRequiresNoArgConstructor {
        /*
         * 🧪 Zadanie 5:
         * Napisz listener z jawnym publicznym konstruktorem bezargumentowym, który loguje
         * "instancja utworzona przez kontener". Zarejestruj go WYŁĄCZNIE przez nazwę klasy
         * (addApplicationListener). Uruchom Tomcat i potwierdź, że log konstruktora
         * pojawił się bez jakiegokolwiek jawnego "new" w Twoim kodzie main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ContextDestroyedCleanupLog {
        /*
         * 🧪 Zadanie 6:
         * Listener w contextDestroyed() loguje "zasoby zwolnione" oraz inkrementuje
         * statyczny licznik zamknięć. Uruchom Tomcat, wyślij jedno zadanie, jawnie wywołaj
         * tomcat.stop()+tomcat.destroy() i wypisz wartość licznika PO zatrzymaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CounterListenerAcrossRequests {
        /*
         * 🧪 Zadanie 7:
         * Listener w contextInitialized() ustawia atrybut kontekstu "requestCounter" jako
         * AtomicInteger(0). Prosty serwlet pod "/hit" inkrementuje ten atrybut przy każdym
         * wywołaniu i zwraca aktualną wartość. Wyślij 3 zadania GET pod rząd i wypisz
         * zwracane wartości licznika (1, 2, 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListenerLoadsConfigMap {
        /*
         * 🧪 Zadanie 8:
         * Listener w contextInitialized() tworzy Map<String,String> z kilkoma parami
         * klucz-wartość (symulacja wczytanej konfiguracji) i zapisuje ją jako atrybut
         * kontekstu "config". Serwlet pod "/config" odczytuje jedną konkretną wartość z
         * mapy po kluczu i zwraca ją w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_OrderOfEventsDemo {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj jeden listener i jeden serwlet pod "/status", każdy z jasno oznaczonym
         * komunikatem ("[1] contextInitialized", "[2] doGet", "[3] contextDestroyed").
         * Uruchom, wyślij zadanie, zatrzymaj Tomcat i wypisz w konsoli zaobserwowaną,
         * pełną kolejność zdarzeń w kolejności ich faktycznego wystąpienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SameListenerTwoSeparateStarts {
        /*
         * 🧪 Zadanie 10:
         * Uruchom DWA OSOBNE, niezależne embedded Tomcaty (jeden po drugim, sekwencyjnie w
         * tym samym main()), oba z tym samym listenerem, który loguje statyczny licznik
         * uruchomień aplikacji. Wypisz wartość licznika po pierwszym starcie i po drugim,
         * potwierdzając, że licznik statyczny narasta między niezależnymi uruchomieniami.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ContextListenerPlusFilterLogging {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj razem: ServletContextListener logujący start/stop aplikacji ORAZ
         * filtr (z Lekcji 14) logujący każde zadanie. Wyślij do serwletu dwa zadania GET i
         * wypisz połączone logi z obu mechanizmów w kolejności ich wystąpienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SessionListenerBasic {
        /*
         * 🧪 Zadanie 12:
         * Napisz HttpSessionListener logujący sessionCreated() i sessionDestroyed().
         * Serwlet "/login" wywołuje request.getSession(true) (tworzy sesję), serwlet
         * "/logout" wywołuje session.invalidate(). Użyj HttpClient z obsługą cookies,
         * wyślij GET /login, potem GET /logout i potwierdź oba logi listenera sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ContextAttributeSharedBetweenServlets {
        /*
         * 🧪 Zadanie 13:
         * Listener w contextInitialized() ustawia atrybut kontekstu "visitCount" jako
         * AtomicInteger(0). DWA różne serwlety ("/a" i "/b") inkrementują i odczytują TEN
         * SAM atrybut. Wyślij GET /a, potem GET /b, potem znów GET /a - wypisz wartości
         * licznika po każdym zadaniu, potwierdzając współdzielony stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ListenerValidatesStartupPrecondition {
        /*
         * 🧪 Zadanie 14:
         * Ustaw statyczne pole "featureEnabled" (boolean) PRZED tomcat.start(). Listener w
         * contextInitialized() odczytuje to pole i zapisuje jego wartość jako atrybut
         * kontekstu "feature.enabled", logując odpowiedni komunikat. Uruchom Tomcat
         * najpierw z featureEnabled=true, potem (w osobnym, drugim uruchomieniu) z false -
         * porównaj logi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ListenerAndInitParamFilterCombo {
        /*
         * 🧪 Zadanie 15:
         * Listener ustawia atrybut kontekstu "greetingPrefix" = "Witaj". Filtr z
         * init-parametrem "suffix" = "!" odczytuje oba te źródła (atrybut kontekstu +
         * własny init-param) i łączy je w jeden nagłówek odpowiedzi X-Greeting. Zweryfikuj
         * finalną wartość nagłówka w odpowiedzi klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleContextListenersOrderDependency {
        /*
         * 🧪 Zadanie 16:
         * Zarejestruj DWA listenery w konkretnej kolejności. Pierwszy w
         * contextInitialized() ustawia atrybut kontekstu "base" = 10. Drugi odczytuje
         * "base", dolicza 5 i zapisuje jako "derived". Serwlet zwraca wartość "derived" -
         * potwierdź, że wynosi 15 (czyli listenery odpaliły się w kolejności rejestracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SessionCounterListener {
        /*
         * 🧪 Zadanie 17:
         * HttpSessionListener utrzymuje AtomicInteger "activeSessions" jako atrybut
         * kontekstu, inkrementowany w sessionCreated() i dekrementowany w
         * sessionDestroyed(). Utwórz dwie sesje (dwa różne zadania z osobnymi klientami
         * HttpClient/cookie jarami), potem unieważnij jedną z nich. Serwlet
         * "/active-sessions" zwraca aktualną wartość licznika - zweryfikuj ją po każdym
         * kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FilterAndListenerSharedCounter {
        /*
         * 🧪 Zadanie 18:
         * Listener inicjalizuje atrybut kontekstu "totalRequests" = AtomicInteger(0). Filtr
         * na "/*" inkrementuje go przy każdym zadaniu. Osobny serwlet "/stats" zwraca
         * aktualną wartość. Wyślij kilka zadań do różnych serwletów, na końcu odpytaj
         * "/stats" i zweryfikuj sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GracefulShutdownResourceSimulation {
        /*
         * 🧪 Zadanie 19:
         * Listener w contextInitialized() "otwiera" symulowany zasób (loguje komunikat,
         * ustawia atrybut kontekstu "resourceOpen" = true). Serwlet w trakcie działania
         * sprawdza, że atrybut jest true. W contextDestroyed() listener sprawdza flagę,
         * loguje "zasób zamknięty" i ustawia ją na false. Zweryfikuj sekwencję logów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TwoSeparateAppsIsolatedListeners {
        /*
         * 🧪 Zadanie 20:
         * Uruchom DWA NIEZALEŻNE embedded Tomcaty (sekwencyjnie w tym samym main(), różne
         * porty), oba z tym samym listenerem, ale każdy ustawia INNĄ wartość atrybutu
         * kontekstu "instanceLabel" (np. przez inny statyczny parametr konfiguracji
         * odczytany przed startem). Zweryfikuj, że wartości odczytane z serwletów obu
         * instancji są od siebie niezależne (izolacja kontekstów).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLifecycleAuditLog {
        /*
         * 🧪 Zadanie 21:
         * Połącz ServletContextListener, filtr (Lekcja 14) i HttpSessionListener - każdy
         * dopisuje wpis do wspólnej, statycznej listy List<String> audytu (np.
         * "CONTEXT_INIT", "SESSION_CREATED", "REQUEST_FILTERED", "SESSION_DESTROYED",
         * "CONTEXT_DESTROYED"). Przeprowadź pełny scenariusz (login, zadanie, logout,
         * stop) i wypisz cały uporządkowany dziennik audytu na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ListenerBackedRequestIdGenerator {
        /*
         * 🧪 Zadanie 22:
         * Listener inicjalizuje w kontekście AtomicLong "requestIdSeq" = 0. Filtr na "/*"
         * pobiera z kontekstu ten licznik, generuje kolejny numer (incrementAndGet) i
         * ustawia go jako atrybut request "requestId". Serwlet odsyła ten numer w
         * odpowiedzi. Wyślij 3 zadania i zweryfikuj rosnące id: 1, 2, 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimulatedDbConnectionPoolListener {
        /*
         * 🧪 Zadanie 23:
         * Listener w contextInitialized() tworzy listę N nazw "połączeń" (np. "conn-1",
         * "conn-2", "conn-3") jako atrybut kontekstu. Serwlet "/borrow" "pożycza" jedno
         * połączenie (usuwa je z listy) i zwraca jego nazwę. Wyślij kilka zadań "/borrow",
         * a w contextDestroyed() wypisz, ile połączeń pozostało w puli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SessionExpiryVsExplicitInvalidateDemo {
        /*
         * 🧪 Zadanie 24:
         * Utwórz sesję przez serwlet "/start", następnie wywołaj jawnie
         * session.invalidate() z osobnego serwletu "/end" (symulując wygaśnięcie zamiast
         * czekać na realny timeout). HttpSessionListener loguje moment sessionDestroyed()
         * z dopiskiem "invalidate jawny" - zweryfikuj, że zdarzenie wystąpiło natychmiast
         * po wywołaniu "/end", a nie z opóźnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ListenerDrivenFeatureToggleAffectsFilter {
        /*
         * 🧪 Zadanie 25:
         * Listener w contextInitialized() ustawia atrybut kontekstu "maintenanceMode" na
         * podstawie statycznej flagi ustawionej PRZED startem. Filtr na "/*" sprawdza ten
         * atrybut przy każdym zadaniu i zwraca 503 zamiast wywołać chain.doFilter(...),
         * gdy tryb konserwacji jest włączony. Uruchom dwa osobne scenariusze (włączony i
         * wyłączony) i porównaj statusy odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiListenerDependencyChain {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj TRZY listenery w konkretnej kolejności: pierwszy ustawia atrybut
         * "base"=10, drugi czyta "base" i zapisuje "doubled"=20, trzeci czyta "doubled" i
         * zapisuje "final"=21. Serwlet zwraca wartość "final" - zweryfikuj poprawny łańcuch
         * zależności między listenerami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_StatsAggregatorAcrossFilterAndListener {
        /*
         * 🧪 Zadanie 27:
         * Listener inicjalizuje Map<String,Integer> "pathHits" jako atrybut kontekstu.
         * Filtr na "/*" inkrementuje licznik dla httpRequest.getRequestURI() przy każdym
         * zadaniu. Wyślij różne liczby zadań do kilku ścieżek, a serwlet "/stats" zwraca
         * zagregowany raport posortowany malejąco po liczbie trafień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GracefulResourceReleaseVerification {
        /*
         * 🧪 Zadanie 28:
         * Listener otwiera symulowaną pulę zasobów o stałym rozmiarze (lista N elementów w
         * atrybucie kontekstu). Serwlet w kilku kolejnych zadaniach "pożycza i zwraca"
         * zasób (usuwa i z powrotem dodaje element do listy). W contextDestroyed()
         * sprawdź, czy rozmiar puli wciąż odpowiada oryginalnemu rozmiarowi i wypisz
         * "OK" lub "WYCIEK ZASOBU".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SessionListenerPlusFilterAuthCombo {
        /*
         * 🧪 Zadanie 29:
         * Połącz HttpSessionListener z filtrem autoryzacji (Lekcja 14): filtr sprawdza
         * atrybut sesji "user". Serwlet "/login" ustawia ten atrybut (tworząc sesję),
         * serwlet "/secure" jest chroniony filtrem, serwlet "/logout" unieważnia sesję.
         * Zweryfikuj cały cykl: dostęp zablokowany przed logowaniem, dozwolony po
         * logowaniu, ponownie zablokowany po wylogowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMiniAppAudit {
        /*
         * 🧪 Zadanie 30:
         * Kapstone: połącz listener startu/zamknięcia z znacznikami czasu, filtr liczący
         * zadania, listener sesji liczący utworzone sesje - wszystko jako atrybuty
         * kontekstu. Serwlet "/audit" zwraca raport: czas działania aplikacji (uptime),
         * całkowitą liczbę zadań i całkowitą liczbę utworzonych sesji. Przeprowadź kilka
         * zadań i sesji przed odpytaniem "/audit", zweryfikuj poprawność raportu.
         */
        public static void main(String[] args) { }
    }
}
