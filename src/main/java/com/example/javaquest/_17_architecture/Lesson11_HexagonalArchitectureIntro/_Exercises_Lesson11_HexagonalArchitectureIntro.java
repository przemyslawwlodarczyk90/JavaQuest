package com.example.javaquest._17_architecture.Lesson11_HexagonalArchitectureIntro;

public class _Exercises_Lesson11_HexagonalArchitectureIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDrivingVsDrivenPortsInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy portem driving a driven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhyHexagonIsJustAMetaphor {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego Cockburn wybral
         * ksztalt szesciokata, a nie kwadratu czy kola, do zilustrowania
         * tego wzorca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DesignDrivingPortForSimpleUseCase {
        /*
         * 🧪 Zadanie 3:
         * Zaprojektuj interfejs `RegisterUserUseCase` (port driving) z metoda
         * `register(String email)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DesignDrivenPortForPersistence {
        /*
         * 🧪 Zadanie 4:
         * Zaprojektuj interfejs `UserRepositoryPort` (port driven) z metoda
         * `save(String email)` - uzyj go w implementacji `RegisterUserUseCase`
         * z Zadania 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementSingleDrivenAdapter {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj `InMemoryUserRepositoryAdapter implements
         * UserRepositoryPort` - zademonstruj pelny przeplyw rejestracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementTwoDrivingAdaptersForSamePort {
        /*
         * 🧪 Zadanie 6:
         * Napisz 2 metody symulujace ROZNE adaptery driving (np.
         * "formularz webowy" i "import CSV") wywolujace TEN SAM
         * `RegisterUserUseCase` z Zadania 3-5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyPortTypeForGivenScenario {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: dla 3 scenariuszy - (a) "aplikacja wysyla e-mail
         * przez zewnetrzny serwis", (b) "uzytkownik klika przycisk w UI", (c)
         * "aplikacja czyta z bazy danych" - okresl, KAZDY jest portem driving
         * czy driven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SwapDrivenAdapterWithoutChangingApplicationCore {
        /*
         * 🧪 Zadanie 8:
         * Dodaj DRUGA implementacje `UserRepositoryPort` (np. "legacy plikowa")
         * z Zadania 4 - zademonstruj, ze `RegisterUserUseCase` NIE wymaga
         * zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareHexagonalTerminologyWithCleanArchitectureFromLesson10 {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: napisz w komentarzu tabele odpowiadajaca pojeciom z
         * `_17_architecture/Lesson10` (Domena/Przypadki uzycia/Adaptery) na
         * pojecia z tej lekcji (Aplikacja/Porty/Adaptery).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListHexagonalArchitectureBenefitsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * korzysci architektury heksagonalnej z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignFullHexagonForOrderManagementSystem {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj kompletny "heksagon" dla systemu zarzadzania
         * zamowieniami: 1 port driving (`ManageOrderUseCase`), 2 porty driven
         * (`OrderRepositoryPort`, `NotificationPort`) - zaimplementuj
         * aplikacje (srodek) korzystajaca z OBU portow driven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementThreeDifferentDrivingAdaptersForOrderSystem {
        /*
         * 🧪 Zadanie 12:
         * Dla systemu z Zadania 11, zaimplementuj 3 ROZNE adaptery driving
         * (np. "REST", "CLI", "wewnetrzny batch job") - zademonstruj, ze
         * WSZYSTKIE 3 dzialaja przez TEN SAM port bez duplikacji logiki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementMultipleDrivenAdaptersForNotificationPort {
        /*
         * 🧪 Zadanie 13:
         * Dla portu `NotificationPort` z Zadania 11, zaimplementuj min. 3
         * adaptery driven (Email, SMS, "brak powiadomien" jako Null Object -
         * `_16_clean_code/Lesson18`) - zademonstruj podmiane wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignAdrForChoosingHexagonalOverLayeredArchitecture {
        /*
         * 🧪 Zadanie 14:
         * Napisz PELNY ADR (`Lesson02`) uzasadniajacy wybor architektury
         * heksagonalnej zamiast prostej warstwowej (Lesson03) dla nowego
         * modulu - z realistycznym kontekstem (np. "spodziewamy sie wielu
         * roznych klientow: web, mobile, integracje partnerow").
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestApplicationCoreInIsolationFromAnyAdapter {
        /*
         * 🧪 Zadanie 15:
         * Dla aplikacji z Zadania 11, napisz "test" (metoda w main())
         * uzywajacy WLASNYCH, prostych implementacji OBU portow driven (test
         * doubles, `_16_clean_code/Lesson11`) - zademonstruj, ze logike
         * biznesowa mozna przetestowac BEZ zadnego prawdziwego adaptera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignPortWithMultipleMethodsRespectingIsp {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj port driven z 4 metodami - oceń (wg ISP,
         * `_16_clean_code/Lesson10`), czy powinien byc 1 duzym interfejsem,
         * czy rozbity na mniejsze - zaimplementuj wybrane rozwiazanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareDrivingAdapterComplexityAcrossTransportTypes {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj adapter driving dla "prostego" transportu (np.
         * bezposrednie wywolanie metody) i dla "zlozonego" (np. symulacja
         * parsowania JSON-a z zadania) - w komentarzu porownaj, GDZIE
         * powinna zyc logika parsowania (odpowiedz: w adapterze, nie w
         * aplikacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignHexagonWhereCoreDefinesRichDomainModel {
        /*
         * 🧪 Zadanie 18:
         * Polacz architekture heksagonalna z bogatym modelem domenowym
         * (`_17_architecture/Lesson05`) - zaprojektuj `Order` (bogata encja) w
         * SRODKU heksagonu, uzywana przez use case, z portem driven do
         * przechowywania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentifyMisplacedLogicInDrivingAdapter {
        /*
         * 🧪 Zadanie 19:
         * Napisz adapter driving, ktory SAM zawiera regule biznesowa (np.
         * walidacje limitu kwoty) zamiast delegowac ja do aplikacji - w
         * komentarzu wskaz naruszenie (analogiczne do "grubego kontrolera",
         * `_17_architecture/Lesson04`) i popraw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealSystemFromDaoChapterForHexagonalReadiness {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) `_10_dao/Lesson02_LayeredArchitecture` -
         * jak wygladalyby "porty" (driving i driven), gdyby przepisac ten
         * system na architekture heksagonalna?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullHexagonalArchitectureForBookingPlatform {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletna architekture heksagonalna dla platformy
         * rezerwacji: min. 2 porty driving, min. 3 porty driven (repozytorium,
         * platnosci, powiadomienia) - aplikacja w srodku koordynuje
         * WSZYSTKIE porty driven.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullSetOfAdaptersForBookingPlatform {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, zaimplementuj KOMPLET adapterow: min. 2
         * driving (REST-podobny, CLI-podobny) i min. 1 implementacja KAZDEGO
         * portu driven - zademonstruj pelny, dzialajacy przeplyw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimulateAddingNewDrivingAdapterWithoutTouchingCore {
        /*
         * 🧪 Zadanie 23:
         * Dla systemu z Zadania 21-22, dodaj TRZECI adapter driving (np.
         * "webhook od partnera") - zademonstruj, ze aplikacja (srodek
         * heksagonu) NIE wymaga ZADNEJ zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignTestSuiteUsingOnlyFakeAdaptersForAllPorts {
        /*
         * 🧪 Zadanie 24:
         * Dla systemu z Zadania 21, zaimplementuj PROSTE, testowe wersje
         * WSZYSTKICH portow driven (fakes) i napisz "zestaw testowy"
         * wywolujacy WSZYSTKIE przypadki uzycia WYLACZNIE przez te fakes -
         * zero prawdziwych adapterow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareHexagonalArchitectureCostVsSimpleLayeredForSmallApp {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: dla MALEJ aplikacji (np. prosty konwerter walut bez
         * trwalego przechowywania) oceń (min. 3 zdania), czy pelna
         * architektura heksagonalna jest uzasadniona, czy to nadmiarowa
         * ceremonia (YAGN, `_16_clean_code/Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignPortNamingConventionDistinguishingDrivingFromDriven {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj WLASNA konwencje nazewnicza rozrozniajaca porty driving
         * (np. sufiks `UseCase`) od driven (np. sufiks `Port`) - zastosuj ja
         * konsekwentnie w systemie z min. 4 portami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrDocumentingPortNamingConvention {
        /*
         * 🧪 Zadanie 27:
         * Napisz ADR (`Lesson02`) dokumentujacy konwencje nazewnicza z Zadania
         * 26 jako oficjalna decyzje zespolu, z uzasadnieniem (spojnosc,
         * czytelnosc dla nowych czlonkow zespolu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RefactorLayeredSystemFromLesson03IntoHexagonalStyle {
        /*
         * 🧪 Zadanie 28:
         * Wez system z `_17_architecture/Lesson03` (LoanApprovalService) i
         * PRZEPISZ go w stylu heksagonalnym - nazwij porty (driving/driven)
         * jawnie, zachowujac identyczna logike biznesowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveHexagonalArchitectureChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do projektowania architektury heksagonalnej - laczac
         * WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullHexagonalSystemWithSwappableEverything {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny
         * system (np. system zarzadzania biblioteka) w PELNI heksagonalny -
         * min. 3 porty driving z ROZNYMI adapterami KAZDY, min. 3 porty
         * driven z ROZNYMI adapterami KAZDY, aplikacja w srodku z bogatym
         * modelem domenowym (Lesson05). Zademonstruj PODMIANE dowolnego
         * adaptera (driving LUB driven) bez zadnej zmiany w rdzeniu aplikacji.
         */
        public static void main(String[] args) { }
    }
}
