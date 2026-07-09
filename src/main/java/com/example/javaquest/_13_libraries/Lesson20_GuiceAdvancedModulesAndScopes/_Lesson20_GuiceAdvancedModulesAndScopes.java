package com.example.javaquest._13_libraries.Lesson20_GuiceAdvancedModulesAndScopes;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class _Lesson20_GuiceAdvancedModulesAndScopes {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20: GUICE - ZAAWANSOWANE MODULY I SCOPE'Y ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LESSON19
         * ============================================================
         * W Lesson19 poznalismy podstawy: "@Inject" (konstruktor/pole),
         * "Module"/"AbstractModule" z metoda "configure()",
         * "bind(Interfejs.class).to(Impl.class)", "Guice.createInjector(...)"
         * i "injector.getInstance(...)". To wystarcza do PROSTEGO,
         * jednoznacznego wiazania "jeden interfejs -> jedna implementacja".
         * W tej lekcji poznamy narzedzia na trudniejsze przypadki: kiedy
         * chcemy JEDNA WSPOLNA instancje zamiast nowej za kazdym razem,
         * kiedy budowa obiektu wymaga WLASNEJ LOGIKI (nie samego "new"), i
         * kiedy MAMY WIELE implementacji tego samego interfejsu jednoczesnie.
         */

        /*
         * ============================================================
         * 🔹 SCOPE: BRAK SCOPE'U (DOMYSLNIE) = NOWA INSTANCJA ZA KAZDYM RAZEM
         * ============================================================
         * Domyslnie (tak jak widzielismy w Lesson19, Exercise14) Guice
         * tworzy NOWA instancje KAZDEGO obiektu przy KAZDYM
         * "getInstance(...)" (lub kazdym wstrzyknieciu jako zaleznosc innej
         * klasy). Ponizej dwa kolejne pobrania "RequestIdGenerator" daja
         * DWA ROZNE obiekty (rozne "hashCode"), co potwierdzamy operatorem
         * "==".
         */
        System.out.println("\n=== PRZYKLAD 1: BRAK SCOPE'U - NOWA INSTANCJA ZA KAZDYM RAZEM ===");
        Injector noScopeInjector = Guice.createInjector(new NoScopeModule());
        RequestIdGenerator gen1 = noScopeInjector.getInstance(RequestIdGenerator.class);
        RequestIdGenerator gen2 = noScopeInjector.getInstance(RequestIdGenerator.class);
        System.out.println("gen1 == gen2 ? " + (gen1 == gen2) + " (oczekiwane: false - dwie ROZNE instancje)");

        /*
         * ============================================================
         * 🔹 @Singleton I Scopes.SINGLETON: JEDNA WSPOLNA INSTANCJA
         * ============================================================
         * Czasami chcemy, by CALA aplikacja dzielila JEDEN, wspolny obiekt
         * (np. polaczenie z baza danych, cache, licznik globalny). Guice
         * oferuje DWA rownowazne sposoby oznaczenia takiej klasy:
         *   1) adnotacja "@Singleton" WPROST na klasie implementacji,
         *   2) "bind(X.class).to(Y.class).in(Scopes.SINGLETON)" w module
         *      (przydatne, gdy nie mozesz zmienic kodu klasy, np. z
         *      biblioteki zewnetrznej).
         * Guice GWARANTUJE, ze bez wzgledu na to, ile razy zazadasz danego
         * typu (bezposrednio przez getInstance albo posrednio jako
         * zaleznosc innej klasy), dostaniesz ZAWSZE TEN SAM obiekt.
         */
        System.out.println("\n=== PRZYKLAD 2: @Singleton - JEDNA WSPOLNA INSTANCJA ===");
        Injector singletonInjector = Guice.createInjector(new SingletonModule());
        AppConfig config1 = singletonInjector.getInstance(AppConfig.class);
        AppConfig config2 = singletonInjector.getInstance(AppConfig.class);
        System.out.println("config1 == config2 ? " + (config1 == config2) + " (oczekiwane: true - TA SAMA instancja, @Singleton)");

        /*
         * ============================================================
         * 🔍 ALTERNATYWA: Scopes.SINGLETON W MODULE (BEZ ADNOTACJI NA KLASIE)
         * ============================================================
         * Gdy nie mozesz (lub nie chcesz) dodac adnotacji "@Singleton"
         * WPROST na klasie implementacji (np. bo pochodzi z zewnetrznej
         * biblioteki, ktorej kodu nie edytujesz), to samo osiagniesz w
         * module: "bind(X.class).to(Y.class).in(Scopes.SINGLETON)". Ponizej
         * "InMemoryCacheService" NIE ma adnotacji "@Singleton" na klasie -
         * scope jest nadany WYLACZNIE w module.
         */
        System.out.println("\n=== PRZYKLAD 2b: Scopes.SINGLETON W MODULE (ALTERNATYWA DLA @Singleton) ===");
        Injector cacheInjector = Guice.createInjector(new CacheModule());
        CacheService cacheA = cacheInjector.getInstance(CacheService.class);
        CacheService cacheB = cacheInjector.getInstance(CacheService.class);
        System.out.println("cacheA == cacheB ? " + (cacheA == cacheB) + " (oczekiwane: true - Scopes.SINGLETON w module)");

        /*
         * ============================================================
         * 🔹 @Provides: METODY BUDUJACE OBIEKT Z WLASNA LOGIKA
         * ============================================================
         * Zwykle "bind(X.class).to(Y.class)" wystarcza, gdy Guice moze sam
         * skonstruowac obiekt przez konstruktor z "@Inject". Ale co, gdy
         * budowa wymaga logiki (np. odczytu konfiguracji, wywolania fabryki
         * statycznej, jak "Clock.systemDefaultZone()")? Do tego sluzy metoda
         * oznaczona "@Provides" WEWNATRZ modulu - Guice wywola ja za KAZDYM
         * razem, gdy potrzebny bedzie obiekt danego typu (chyba ze polaczysz
         * ja z "@Singleton" na samej metodzie - wtedy wynik jest liczony
         * TYLKO RAZ i pamietany).
         */
        System.out.println("\n=== PRZYKLAD 3: @Provides - RECZNA LOGIKA BUDOWY OBIEKTU ===");
        Injector providesInjector = Guice.createInjector(new ClockModule());
        Clock clock = providesInjector.getInstance(Clock.class);
        System.out.println("Clock dostarczony przez metode @Provides: " + Instant.now(clock));
        TimestampService timestampService = providesInjector.getInstance(TimestampService.class);
        System.out.println("TimestampService (zalezny od Clock z @Provides): " + timestampService.currentTimestamp());

        /*
         * ============================================================
         * 🔍 @Provides + @Singleton NA METODZIE
         * ============================================================
         * Ponizej "ExpensiveResource" symuluje "kosztowny" obiekt (np.
         * polaczenie sieciowe) - licznik "creationCount" pokazuje, ile razy
         * metoda "@Provides" faktycznie sie wykonala. Dzieki "@Singleton"
         * NAD metoda, wykona sie TYLKO RAZ, mimo dwoch pobran.
         */
        System.out.println("\n=== PRZYKLAD 4: @Provides + @Singleton NA METODZIE ===");
        Injector expensiveInjector = Guice.createInjector(new ExpensiveResourceModule());
        ExpensiveResource resourceA = expensiveInjector.getInstance(ExpensiveResource.class);
        ExpensiveResource resourceB = expensiveInjector.getInstance(ExpensiveResource.class);
        System.out.println("resourceA == resourceB ? " + (resourceA == resourceB));
        System.out.println("Metoda @Provides wykonala sie " + ExpensiveResourceModule.creationCount + " raz(y) - nie dwa.");

        /*
         * ============================================================
         * 🔹 Provider<T>: LENIWE / WIELOKROTNE TWORZENIE OBIEKTOW
         * ============================================================
         * Zamiast wstrzykiwac gotowy obiekt "T", mozna wstrzyknac
         * "Provider<T>" - "fabryke", ktora na zawolanie "get()" DOSTARCZA
         * obiekt (dla braku scope'u: nowy przy KAZDYM wywolaniu "get()").
         * Przydatne, gdy: 1) potrzebujesz WIELU niezaleznych instancji
         * wewnatrz jednej klasy (np. w petli), 2) chcesz OPOZNIC utworzenie
         * obiektu do momentu, gdy faktycznie jest potrzebny (a nie w chwili
         * budowy klasy nadrzednej).
         */
        System.out.println("\n=== PRZYKLAD 5: Provider<T> - FABRYKA NA ZAWOLANIE ===");
        Injector providerInjector = Guice.createInjector(new NoScopeModule());
        OrderBatchProcessor batchProcessor = providerInjector.getInstance(OrderBatchProcessor.class);
        batchProcessor.processBatch(3);

        /*
         * ============================================================
         * 🔹 @Named / Names.named(...): WIELE IMPLEMENTACJI TEGO SAMEGO TYPU
         * ============================================================
         * Zwykle "bind(X.class).to(Y.class)" pozwala na JEDNO wiazanie na
         * interfejs. Co, gdy potrzebujemy DWOCH implementacji "PaymentGateway"
         * jednoczesnie (np. jednej "podstawowej" i jednej "zapasowej")?
         * Rozwiazuje to adnotacja "@Named("etykieta")" - w module wiazemy:
         *
         *   bind(PaymentGateway.class)
         *       .annotatedWith(Names.named("primary"))
         *       .to(CreditCardGateway.class);
         *   bind(PaymentGateway.class)
         *       .annotatedWith(Names.named("backup"))
         *       .to(PayPalGateway.class);
         *
         * a w klasie korzystajacej oznaczamy KAZDY parametr osobno:
         *
         *   OrderService(@Named("primary") PaymentGateway primary,
         *                @Named("backup") PaymentGateway backup)
         *
         * Guice rozroznia wiazania po PARZE (typ, adnotacja) - dzieki temu
         * ten sam interfejs moze miec WIELE roznych "wersji" jednoczesnie.
         */
        System.out.println("\n=== PRZYKLAD 6: @Named - WIELE IMPLEMENTACJI TEGO SAMEGO INTERFEJSU ===");
        Injector namedInjector = Guice.createInjector(new NamedPaymentModule());
        DualGatewayOrderService dualService = namedInjector.getInstance(DualGatewayOrderService.class);
        dualService.placeOrderWithFallback(300.00);

        /*
         * ============================================================
         * 🔹 toInstance(...): WIAZANIE NA GOTOWY, KONKRETNY OBIEKT
         * ============================================================
         * Czasami mamy JUZ GOTOWY obiekt (np. wczytany z konfiguracji przed
         * startem aplikacji) i chcemy, by Guice zawsze zwracal DOKLADNIE
         * TEN obiekt (nigdy nie tworzyl nowego). Sluzy do tego
         * "toInstance(...)" - dziala jak niejawny "@Singleton" (bo instancja
         * jest tylko JEDNA, ustalona z gory), typowo uzywane dla prostych,
         * NIEMUTOWALNYCH wartosci (String, liczby, gotowe obiekty konfiguracji).
         */
        System.out.println("\n=== PRZYKLAD 7: toInstance(...) - WIAZANIE NA GOTOWY OBIEKT ===");
        Injector instanceInjector = Guice.createInjector(new AppNameModule("JavaQuest-Shop"));
        GreetingService greetingService = instanceInjector.getInstance(GreetingService.class);
        greetingService.greet();

        /*
         * ============================================================
         * 🔹 LACZENIE WIELU MODULOW W JEDNYM INJECTORZE
         * ============================================================
         * "Guice.createInjector(moduleA, moduleB, moduleC)" przyjmuje
         * DOWOLNA liczbe modulow naraz - kazdy moze odpowiadac za INNY
         * "obszar" aplikacji (np. platnosci, powiadomienia, konfiguracje).
         * Wszystkie wiazania trafiaja do JEDNEGO, wspolnego Injectora -
         * klasy z jednego modulu moga swobodnie zalezec od interfejsow
         * powiazanych w INNYM module. To dokladnie to, co widzielismy w
         * Lesson19 Przykladzie 6 - tu podsumowujemy to jako celowa TECHNIKE
         * organizacji duzych aplikacji (jeden modul na "warstwe"/"modul
         * biznesowy", zamiast jednego wielkiego modulu na wszystko).
         */
        System.out.println("\n=== PRZYKLAD 8: LACZENIE WIELU MODULOW ===");
        Injector combinedInjector = Guice.createInjector(
                new SingletonModule(),
                new ClockModule(),
                new AppNameModule("JavaQuest-Combined"));
        System.out.println("Jeden Injector, TRZY moduly - kazdy odpowiada za inny obszar konfiguracji.");
        System.out.println("AppConfig: " + combinedInjector.getInstance(AppConfig.class));
        System.out.println("Clock: " + combinedInjector.getInstance(Clock.class).getZone());

        /*
         * ============================================================
         * 🔍 DALSZA LEKTURA (SKROTOWO): PrivateModule I MULTIBINDINGS
         * ============================================================
         * Guice oferuje tez bardziej zaawansowane narzedzia, ktorych ta
         * lekcja tylko dotyka: "PrivateModule" pozwala UKRYC wiazania
         * wewnetrzne modulu (widoczne tylko wewnatrz niego) i wyeksponowac
         * na zewnatrz jedynie wybrane przez "expose(...)" - przydatne w
         * duzych aplikacjach, by uniknac przypadkowych kolizji nazw/typow
         * miedzy niezaleznymi modulami. "Multibindings" (dodatkowy modul
         * "com.google.inject.multibindings.Multibinder") pozwalaja zbindowac
         * WIELE implementacji tego samego interfejsu jako JEDNA, wstrzykiwana
         * "Set<T>" lub "Map<K, T>" (rozwiazuje dokladnie problem zasygnalizowany
         * w Lesson19 Exercise23 - wstrzykniecie LISTY/ZBIORU wszystkich
         * implementacji naraz, np. wszystkich walidatorow albo wszystkich
         * handlerow zdarzen). Oba tematy warte poznania przy pracy nad
         * wieksza aplikacja korzystajaca z Guice.
         */
        System.out.println("\n=== DALSZA LEKTURA: PrivateModule i Multibindings (Multibinder) ===");
        System.out.println("Nie demonstrowane w tej lekcji - patrz oficjalna dokumentacje Guice.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Domyslnie (bez scope'u) Guice tworzy NOWA instancje za kazdym
         *   "getInstance(...)"/wstrzyknieciem.
         * - "@Singleton" (na klasie) lub ".in(Scopes.SINGLETON)" (w module) -
         *   JEDNA, wspoldzielona instancja dla calego Injectora.
         * - "@Provides" wewnatrz Module - metoda z WLASNA logika budowy
         *   obiektu (zamiast prostego "bind().to()"); mozna ja tez oznaczyc
         *   "@Singleton", by wynik byl liczony TYLKO RAZ.
         * - "Provider<T>" - wstrzykiwana "fabryka" dostarczajaca obiekt NA
         *   ZAWOLANIE (przydatna do wielu niezaleznych instancji / leniwego
         *   tworzenia).
         * - "@Named("etykieta")" + "Names.named(...)" - WIELE implementacji
         *   tego samego interfejsu, rozroznianych etykieta.
         * - "toInstance(...)" - wiazanie na GOTOWY, wczesniej utworzony obiekt
         *   (dziala jak niejawny singleton).
         * - "Guice.createInjector(moduleA, moduleB, ...)" - dowolna liczba
         *   modulow skladajacych sie w JEDEN, spojny graf zaleznosci.
         * - "PrivateModule" i "Multibinder" (multibindings) - narzedzia na
         *   wieksza skale, warte poznania osobno.
         */
        System.out.println("\n=== KONIEC LEKCJI 20 ===");
    }

    // ---------------------------------------------------------------
    // Przyklad 1: brak scope'u
    // ---------------------------------------------------------------

    static class RequestIdGenerator {
        private final String id = "REQ-" + System.nanoTime();

        String getId() {
            return id;
        }
    }

    static class NoScopeModule extends AbstractModule {
        // Brak "configure()" nie jest tu nawet potrzebne - RequestIdGenerator
        // jest klasa konkretna z domyslnym konstruktorem, Guice obsluzy ja
        // przez Just-In-Time binding (jak w Lesson19 Exercise09). Modul
        // zostaje pusty/celowo minimalny, uzywany tez dla Provider<T> nizej.
    }

    // ---------------------------------------------------------------
    // Przyklad 2: @Singleton
    // ---------------------------------------------------------------

    @Singleton
    static class AppConfig {
        private final String environment = "PRODUCTION";

        @Override
        public String toString() {
            return "AppConfig{environment='" + environment + "'}";
        }
    }

    static class SingletonModule extends AbstractModule {
        // AppConfig ma "@Singleton" WPROST na klasie - nie trzeba nic
        // dodatkowo konfigurowac w configure(), Guice sam uszanuje adnotacje.
    }

    interface CacheService {
        void put(String key, String value);
    }

    static class InMemoryCacheService implements CacheService {
        // CELOWO bez "@Singleton" na klasie - scope nadajemy w module,
        // ponizej Scopes.SINGLETON, jako alternatywa dla adnotacji.
        private final java.util.Map<String, String> storage = new java.util.HashMap<>();

        @Override
        public void put(String key, String value) {
            storage.put(key, value);
            System.out.println(" [InMemoryCacheService] Zapisano " + key + "=" + value
                    + " (rozmiar cache: " + storage.size() + ")");
        }
    }

    static class CacheModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(CacheService.class).to(InMemoryCacheService.class).in(Scopes.SINGLETON);
        }
    }

    // ---------------------------------------------------------------
    // Przyklad 3 i 4: @Provides
    // ---------------------------------------------------------------

    static class TimestampService {
        private final Clock clock;

        @Inject
        TimestampService(Clock clock) {
            this.clock = clock;
        }

        String currentTimestamp() {
            return Instant.now(clock).toString();
        }
    }

    static class ClockModule extends AbstractModule {
        @Provides
        Clock provideClock() {
            // Logika budowy, ktorej nie da sie wyrazic samym "bind().to()" -
            // Clock nie ma publicznego konstruktora, tylko fabryki statyczne.
            return Clock.system(ZoneId.of("Europe/Warsaw"));
        }
    }

    static class ExpensiveResource {
        private final String connectionInfo;

        ExpensiveResource(String connectionInfo) {
            this.connectionInfo = connectionInfo;
        }

        @Override
        public String toString() {
            return "ExpensiveResource{" + connectionInfo + "}";
        }
    }

    static class ExpensiveResourceModule extends AbstractModule {
        static int creationCount = 0;

        @Provides
        @Singleton
        ExpensiveResource provideExpensiveResource() {
            creationCount++;
            System.out.println(" [ExpensiveResourceModule] Tworze NOWY ExpensiveResource (koszt symulowany)...");
            return new ExpensiveResource("polaczenie-" + creationCount);
        }
    }

    // ---------------------------------------------------------------
    // Przyklad 5: Provider<T>
    // ---------------------------------------------------------------

    static class OrderBatchProcessor {
        private final Provider<RequestIdGenerator> requestIdProvider;

        @Inject
        OrderBatchProcessor(Provider<RequestIdGenerator> requestIdProvider) {
            this.requestIdProvider = requestIdProvider;
        }

        void processBatch(int count) {
            for (int i = 1; i <= count; i++) {
                RequestIdGenerator generator = requestIdProvider.get(); // NOWA instancja za kazdym "get()"
                System.out.println(" Zamowienie " + i + " -> " + generator.getId());
            }
        }
    }

    // ---------------------------------------------------------------
    // Przyklad 6: @Named
    // ---------------------------------------------------------------

    interface PaymentGateway {
        void charge(double amount);
    }

    static class CreditCardGateway implements PaymentGateway {
        @Override
        public void charge(double amount) {
            System.out.println(" [CreditCardGateway] (PRIMARY) Obciazono karte kwota: " + amount + " PLN");
        }
    }

    static class PayPalGateway implements PaymentGateway {
        @Override
        public void charge(double amount) {
            System.out.println(" [PayPalGateway] (BACKUP) Obciazono PayPal kwota: " + amount + " PLN");
        }
    }

    static class NamedPaymentModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(PaymentGateway.class).annotatedWith(Names.named("primary")).to(CreditCardGateway.class);
            bind(PaymentGateway.class).annotatedWith(Names.named("backup")).to(PayPalGateway.class);
        }
    }

    static class DualGatewayOrderService {
        private final PaymentGateway primaryGateway;
        private final PaymentGateway backupGateway;

        @Inject
        DualGatewayOrderService(@Named("primary") PaymentGateway primaryGateway,
                                 @Named("backup") PaymentGateway backupGateway) {
            this.primaryGateway = primaryGateway;
            this.backupGateway = backupGateway;
        }

        void placeOrderWithFallback(double amount) {
            System.out.println("Probuje glowna bramke platnicza...");
            primaryGateway.charge(amount);
            System.out.println("Bramka zapasowa dostepna na wypadek awarii glownej:");
            backupGateway.charge(amount);
        }
    }

    // ---------------------------------------------------------------
    // Przyklad 7: toInstance(...)
    // ---------------------------------------------------------------

    static class GreetingService {
        private final String appName;

        @Inject
        GreetingService(@Named("appName") String appName) {
            this.appName = appName;
        }

        void greet() {
            System.out.println("Witaj w aplikacji: " + appName);
        }
    }

    static class AppNameModule extends AbstractModule {
        private final String appName;

        AppNameModule(String appName) {
            this.appName = appName;
        }

        @Override
        protected void configure() {
            // toInstance(...) - wiazemy GOTOWY, JUZ ISTNIEJACY obiekt (String),
            // Guice nigdy nie utworzy nowego - zawsze zwroci DOKLADNIE ten.
            bind(String.class).annotatedWith(Names.named("appName")).toInstance(appName);
        }
    }
}
