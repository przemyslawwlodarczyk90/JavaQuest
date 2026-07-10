package com.example.javaquest._16_clean_code.Lesson11_DependencyInversionPrinciple;

public class _Exercises_Lesson11_DependencyInversionPrinciple {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDipInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) obie czesci definicji DIP i co oznacza "modul wysoko-" a co
         * "niskopoziomowy".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RecreateBadNotificationServiceAndObserveRigidity {
        /*
         * 🧪 Zadanie 2:
         * Odtworz klasy EmailSender i BadNotificationService z teorii lekcji.
         * Wywolaj notifyOrderShipped(...) i w komentarzu wyjasnij, dlaczego NIE
         * da sie latwo podmienic sposobu wysylki bez zmiany kodu klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateMessageSenderInterface {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj interfejs MessageSender z metoda send(String recipient,
         * String message) - to fundament pod poprawna (zgodna z DIP) wersje
         * z kolejnych zadan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementEmailAndSmsMessageSenders {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj 2 klasy implementujace MessageSender z Zadania 3:
         * EmailMessageSender i SmsMessageSender. Wywolaj send() na obu
         * bezposrednio i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RefactorNotificationServiceToUseConstructorInjection {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj GoodNotificationService przyjmujacy MessageSender przez
         * KONSTRUKTOR (constructor injection). Uruchom go z EmailMessageSender
         * i osobno z SmsMessageSender z Zadania 4 - bez zadnej zmiany kodu
         * GoodNotificationService.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyConstructorInjectionIsNotAFrameworkFeature {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * "wstrzykiwanie zaleznosci" NIE wymaga zadnego frameworka (Spring/
         * Guice) - to zwykla technika Javy z konstruktorem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteTestDoubleImplementationForTesting {
        /*
         * 🧪 Zadanie 7:
         * Napisz klase RecordingMessageSender implementujaca MessageSender,
         * ktora TYLKO zapamietuje ostatniego odbiorce i wiadomosc (bez
         * prawdziwej wysylki). Uzyj jej z GoodNotificationService i wypisz
         * zapamietane dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyDipViolationInGivenDescription {
        /*
         * 🧪 Zadanie 8:
         * Dany jest opis: klasa `ReportGenerator` tworzy w sobie `new
         * MySqlDatabaseConnection()` do pobrania danych. W komentarzu wyjasnij,
         * dlaczego to narusza DIP i zaproponuj (opisowo) abstrakcje, ktora
         * powinna byc uzyta zamiast tego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementFixedReportGeneratorWithDataSourceAbstraction {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj poprawiona wersje z Zadania 8: interfejs DataSource
         * (getData()) i klasa ReportGenerator przyjmujaca DataSource przez
         * konstruktor - stworz 1 prosta implementacje (np. InMemoryDataSource)
         * i wywolaj generowanie raportu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWarningSignsOfDipViolation {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu min. 3 sygnaly ostrzegawcze
         * naruszenia DIP w kodzie (np. `new KonkretnaKlasa()` wewnatrz logiki
         * biznesowej, brak mozliwosci podmiany implementacji w tescie).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignPaymentProcessorDependingOnAbstraction {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj interfejs PaymentGateway (charge(double amount)) oraz 2
         * implementacje (StripeLikeGateway, PaypalLikeGateway) - klasa
         * CheckoutService (wysokopoziomowa) przyjmuje PaymentGateway przez
         * konstruktor. Wywolaj checkout z obiema implementacjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteFailingPaymentGatewayForErrorHandlingTest {
        /*
         * 🧪 Zadanie 12:
         * Napisz implementacje `AlwaysFailingPaymentGateway` (zawsze rzuca
         * wyjatek RuntimeException) - uzyj jej z CheckoutService z Zadania 11,
         * zeby przetestowac obsluge bledu platnosci BEZ prawdziwej bramki
         * platniczej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyDipViolationInLoggingExample {
        /*
         * 🧪 Zadanie 13:
         * Dany jest opis: klasa `OrderProcessor` woluje bezposrednio statyczna
         * metode `FileLogger.writeToFile(...)` - w komentarzu wyjasnij, dlaczego
         * to narusza DIP (nawet bez `new` - statyczne wywolanie TEZ jest
         * sztywna zaleznoscia od konkretu) i zaproponuj interfejs Logger.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementLoggerAbstractionWithTwoImplementations {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj interfejs Logger (log(String message)) z 2
         * implementacjami: ConsoleLogger i InMemoryLogger (zapisuje do
         * wewnetrznej listy) - klasa OrderProcessor przyjmuje Logger przez
         * konstruktor. Przetestuj obie implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignNotificationSystemWithMultipleChannelsAtOnce {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz GoodNotificationService (z teorii/wczesniejszych zadan) tak,
         * by przyjmowal `List<MessageSender>` zamiast pojedynczego - metoda
         * notifyOrderShipped ma wysylac powiadomienie PRZEZ WSZYSTKIE kanaly
         * naraz. Przetestuj z 2 kanalami (email+sms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareDirectInstantiationVsConstructorInjectionForTestability {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj TA SAMA funkcjonalnosc (walidacja + "zapis" zamowienia)
         * na 2 sposoby: (a) klasa tworzaca swoja zaleznosc przez `new` w
         * srodku, (b) klasa przyjmujaca zaleznosc przez konstruktor. W
         * komentarzu wyjasnij, ktora wersja latwiej przetestowac i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignRepositoryAbstractionForInMemoryAndFakeImplementations {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj interfejs UserRepository (findById(String id),
         * save(User)) - zaimplementuj InMemoryUserRepository (HashMap) oraz
         * klase UserService przyjmujaca UserRepository przez konstruktor.
         * Przetestuj zapis i odczyt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWhenConcreteDependencyIsFineNotDipViolation {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * zaleznosc od `java.util.ArrayList` czy `java.lang.String` w Twoim
         * wlasnym kodzie NIE jest naruszeniem DIP - kiedy zaleznosc od
         * konkretnej klasy jest w porzadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorTightlyCoupledReportExporter {
        /*
         * 🧪 Zadanie 19:
         * Napisz "zla" klase ReportExporter tworzaca w sobie `new
         * CsvFileWriter()`. Zrefaktoryzuj: wprowadz interfejs FileWriter
         * (write(String content)) z implementacjami CsvFileWriter i
         * JsonFileWriter, ReportExporter przyjmuje FileWriter przez
         * konstruktor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeDipInComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu tabele/liste porownujaca kod
         * ZGODNY z DIP i NIEZGODNY z DIP - min. 3 pary przykladow (moga byc z
         * tej lekcji lub wlasne).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullOrderSystemWithMultipleAbstractions {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj system zamowien z 3 abstrakcjami: PaymentGateway,
         * MessageSender, OrderRepository - klasa OrderService (wysokopoziomowa)
         * przyjmuje WSZYSTKIE TRZY przez konstruktor. Zaimplementuj po 1
         * konkretnej implementacji kazdej i przeprowadz pelny przeplyw
         * zamowienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildTestVersionOfOrderServiceWithAllFakes {
        /*
         * 🧪 Zadanie 22:
         * Korzystajac z OrderService z Zadania 21, zbuduj WERSJE TESTOWA
         * uzywajaca 3 prostych "fake" implementacji (np. zawsze-udanych/
         * zapamietujacych wywolania) - pokaz, ze cala logike biznesowa mozna
         * przetestowac BEZ prawdziwej bazy/sieci/platnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IdentifyAndFixDipViolationInSchedulerExample {
        /*
         * 🧪 Zadanie 23:
         * Dany jest opis: klasa `TaskScheduler` woluje bezposrednio
         * `System.currentTimeMillis()` do decyzji "czy uruchomic zadanie
         * teraz" - w testach to sprawia, ze wynik zalezy od PRAWDZIWEGO czasu
         * systemowego. Zaprojektuj interfejs Clock (now()) i zaimplementuj
         * SystemClock oraz FixedClock (do testow) - TaskScheduler przyjmuje
         * Clock przez konstruktor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DemonstrateDeterministicTestUsingFixedClock {
        /*
         * 🧪 Zadanie 24:
         * Korzystajac z rozwiazania z Zadania 23, napisz 2 scenariusze testowe
         * z FixedClock ustawionym na rozne "czasy" - pokaz, ze TaskScheduler
         * zachowuje sie DETERMINISTYCZNIE i przewidywalnie (bez zaleznosci od
         * prawdziwego zegara).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignPluggableDiscountStrategySystem {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj interfejs DiscountStrategy (calculate(double amount)) z
         * min. 3 implementacjami (NoDiscount, PercentageDiscount,
         * FixedAmountDiscount) - klasa PriceCalculator (wysokopoziomowa)
         * przyjmuje DiscountStrategy przez konstruktor. Przetestuj wszystkie
         * 3 strategie na tej samej kwocie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RefactorLegacySingletonDependencyToInjectedAbstraction {
        /*
         * 🧪 Zadanie 26:
         * Napisz "legacy" klase `ConfigManager` z metoda statyczna
         * `getInstance()` (singleton) uzywana BEZPOSREDNIO przez klase
         * `AppService` (`ConfigManager.getInstance().getValue(...)`). W
         * komentarzu wyjasnij, dlaczego globalny singleton wywolywany wprost
         * TEZ jest naruszeniem DIP, a nastepnie zrefaktoryzuj do interfejsu
         * ConfigSource przekazywanego przez konstruktor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildLayeredArchitectureRespectingDipAcrossLayers {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj 3-warstwowa architekture: warstwa domenowa (interfejs
         * `InventoryRepository`), warstwa infrastruktury (implementacja
         * `InMemoryInventoryRepository`), warstwa aplikacji (`InventoryService`
         * zalezny TYLKO od interfejsu z warstwy domenowej). Zademonstruj, ze
         * warstwa domenowa NIE importuje NIC z warstwy infrastruktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareArchitectureWithAndWithoutDipForNewRequirement {
        /*
         * 🧪 Zadanie 28:
         * Wyobraz sobie NOWE wymaganie: zmiana z bazy w pamieci na (hipotetyczna)
         * baze SQL. W komentarzu opisz, ILE plikow/klas trzeba by zmienic w
         * architekturze Z Zadania 27 (zgodnej z DIP), a ile w hipotetycznej
         * architekturze, gdzie InventoryService tworzylby
         * InMemoryInventoryRepository przez `new` bezposrednio.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveDipComplianceChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste DIP (min.
         * 5 punktow: czy logika biznesowa tworzy zaleznosci przez `new`?, czy
         * zaleznosci sa przekazywane przez konstruktor?, czy latwo podmienic
         * implementacje w tescie?, czy warstwa domenowa importuje szczegoly
         * techniczne? itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullSolidCompliantMiniApplication {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji (i CALEGO bloku SOLID, Lesson07-11): zaprojektuj
         * i zaimplementuj kompletny, mały system (np. system obslugi zgloszen
         * serwisowych) laczacy WSZYSTKIE 5 zasad SOLID naraz: 1 wyrazna
         * odpowiedzialnosc na klase (SRP), rozszerzalnosc bez modyfikacji
         * (OCP), podtypy podstawialne (LSP), male wyspecjalizowane interfejsy
         * (ISP), oraz zaleznosc logiki od abstrakcji przekazywanych przez
         * konstruktor (DIP). Zademonstruj dzialanie calego systemu.
         */
        public static void main(String[] args) { }
    }
}
