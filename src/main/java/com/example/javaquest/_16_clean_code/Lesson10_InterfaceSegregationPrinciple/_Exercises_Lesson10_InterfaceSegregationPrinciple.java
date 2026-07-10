package com.example.javaquest._16_clean_code.Lesson10_InterfaceSegregationPrinciple;

public class _Exercises_Lesson10_InterfaceSegregationPrinciple {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainIspInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania)
         * zasade ISP i podaj wlasny przyklad (inny niz Worker z teorii)
         * "tlustego" interfejsu, ktory wymuszalby na jakiejs implementacji
         * napisanie metody bez sensu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RecreateBadWorkerAndObserveException {
        /*
         * 🧪 Zadanie 2:
         * Odtworz interfejs BadWorker (work/eat/sleep) i klase RobotWorker z
         * teorii lekcji. Wywolaj robot.eat() w bloku try-catch i wypisz
         * komunikat zlapanego wyjatku UnsupportedOperationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SplitBadWorkerIntoThreeInterfaces {
        /*
         * 🧪 Zadanie 3:
         * Rozbij BadWorker z Zadania 2 na 3 male interfejsy: Workable, Eatable,
         * Sleepable. Stworz klase HumanWorker implementujaca wszystkie trzy i
         * wywolaj kazda metode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementRobotWithOnlyWorkableInterface {
        /*
         * 🧪 Zadanie 4:
         * Korzystajac z interfejsow z Zadania 3, zaimplementuj RobotWorker
         * implementujacy TYLKO Workable. W komentarzu potwierdz, ze proba
         * wywolania robot.eat() teraz w ogole nie kompiluje sie (metoda nie
         * istnieje w tym typie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DesignFatPrinterInterface {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj "tlusty" interfejs MultiFunctionDevice z metodami print(),
         * scan(), fax(). Zaimplementuj klase SimplePrinter, ktora musi
         * "obsluzyc" wszystkie trzy metody, mimo ze fizycznie potrafi tylko
         * drukowac (dla scan()/fax() rzuca UnsupportedOperationException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SplitMultiFunctionDeviceIntoSmallInterfaces {
        /*
         * 🧪 Zadanie 6:
         * Rozbij MultiFunctionDevice z Zadania 5 na Printable, Scannable,
         * Faxable. Zaimplementuj SimplePrinter (tylko Printable) i
         * AllInOnePrinter (wszystkie trzy) - wywolaj odpowiednie metody dla
         * obu klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainCompileTimeVsRuntimeSafety {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania) roznice miedzy
         * bledem WYKRYTYM W KOMPILACJI (brak metody w typie) a bledem
         * wykrytym W RUNTIME (UnsupportedOperationException) - dlaczego ten
         * pierwszy jest zawsze lepszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyFatInterfaceInRepositoryExample {
        /*
         * 🧪 Zadanie 8:
         * Dany jest opis: interfejs Repository z metodami save(), findById(),
         * deleteAll(), exportToCsv(), sendEmailReport(). W komentarzu wskaz,
         * ktore metody NIE naleza do podstawowej odpowiedzialnosci
         * "przechowywanie danych" i zaproponuj rozbicie na osobne interfejsy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementSplitRepositoryInterfaces {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj podzial zaproponowany w Zadaniu 8: interfejsy
         * CrudRepository (save/findById/deleteAll) i CsvExportable
         * (exportToCsv) - klasa UserRepository implementuje oba, klasa
         * ReadOnlyAuditRepository implementuje TYLKO CrudRepository (z pustym
         * save() rzucajacym wyjatek - zaobserwuj, ze to WCIAZ smierdzi i
         * wymaga dalszego podzialu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWarningSignsOfFatInterfaces {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu min. 3 sygnaly ostrzegawcze
         * sugerujace, ze interfejs jest "tlusty" i wymaga segregacji (np. puste
         * implementacje, throw UnsupportedOperationException, nazwa interfejsu
         * zbyt ogolna typu "Manager"/"Service").
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignSmartHomeDeviceInterfaces {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj male interfejsy Switchable (turnOn/turnOff), Dimmable
         * (setBrightness(int)), ColorChangeable (setColor(String)) - napisz
         * klase SimpleLightBulb (tylko Switchable) i SmartLightBulb (wszystkie
         * trzy). Wywolaj odpowiednie metody dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteMethodAcceptingOnlyNeededInterface {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode `turnOffAll(List<Switchable> devices)`, ktora dziala
         * dla KAZDEGO urzadzenia implementujacego Switchable (z Zadania 11),
         * niezaleznie czy to SimpleLightBulb czy SmartLightBulb. Wywolaj ja
         * dla listy mieszanej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyIspViolationInMediaPlayerExample {
        /*
         * 🧪 Zadanie 13:
         * Dany jest opis: interfejs MediaPlayer z metodami playAudio(),
         * playVideo(), recordAudio(), streamLive() - klasa SimpleMp3Player
         * potrafi tylko playAudio(). W komentarzu wyjasnij naruszenie ISP i
         * zaproponuj min. 3 male interfejsy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSplitMediaPlayerInterfaces {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj podzial z Zadania 13: AudioPlayable, VideoPlayable,
         * LiveStreamable - klasa SimpleMp3Player implementuje TYLKO
         * AudioPlayable, klasa SmartTvApp implementuje wszystkie trzy.
         * Przetestuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignPaymentGatewayInterfacesForDifferentProviders {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj interfejsy Chargeable (charge(double)), Refundable
         * (refund(double)), Subscribable (createSubscription(double)) -
         * zaimplementuj SimpleCardGateway (Chargeable+Refundable) i
         * PaypalGateway (wszystkie trzy). Wywolaj odpowiednie metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteClientCodeDependingOnlyOnChargeable {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode `processOneTimePurchase(Chargeable gateway, double
         * amount)`, ktora dziala dla dowolnej bramki platnosci z Zadania 15
         * implementujacej Chargeable - wywolaj ja dla SimpleCardGateway i
         * PaypalGateway bez zadnej zmiany kodu metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_IdentifyIspViolationInJdbcLikeInterface {
        /*
         * 🧪 Zadanie 17:
         * Dany jest opis: wlasny interfejs DataSource z metodami
         * getConnection(), getReadOnlyConnection(), getMetrics(),
         * getBackupSchedule() - klasa SimpleDataSource realnie potrafi tylko
         * dawac polaczenia. W komentarzu wyjasnij naruszenie i zaproponuj
         * podzial.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementSplitDataSourceInterfaces {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj podzial z Zadania 17: ConnectionProvider
         * (getConnection/getReadOnlyConnection) i Monitorable (getMetrics) -
         * klasa SimpleDataSource implementuje TYLKO ConnectionProvider, klasa
         * ManagedDataSource implementuje oba. Przetestuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareFatInterfaceVsSegregatedForNewImplementation {
        /*
         * 🧪 Zadanie 19:
         * Wyobraz sobie, ze trzeba dodac NOWA implementacje (np. InMemoryCache
         * jako "DataSource" z Zadania 17/18). W komentarzu porownaj, ile metod
         * musialaby "udawac"/rzucac wyjatki w wersji z JEDNYM tlustym
         * interfejsem, a ile w wersji rozbitej na male interfejsy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeIspBenefitsInChecklist {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu wlasna checkliste (min. 4 punkty)
         * do oceny, czy interfejs w Twoim kodzie narusza ISP - i jak to
         * sprawdzic PRZED napisaniem implementacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignVehicleCapabilityInterfacesForFleetSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj interfejsy Driveable (drive()), Flyable (fly()),
         * Sailable (sail()) dla systemu floty pojazdow - zaimplementuj Car
         * (Driveable), SeaplaneCar (Driveable+Flyable+Sailable, "amfibia
         * latajaca"), Boat (Sailable). Napisz metode `startAllVehicles(List<
         * Driveable> vehicles)` uzywajaca TYLKO potrzebnego interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorRealCodeSmellFatServiceInterface {
        /*
         * 🧪 Zadanie 22:
         * Napisz "tlusty" interfejs UserService z 6 metodami: register(),
         * login(), sendPasswordResetEmail(), generateActivityReport(),
         * banUser(), exportUsersToJson(). Zaimplementuj 1 klase realizujaca
         * wszystkie 6 (SimpleUserService) - w komentarzu wskaz, ktore metody
         * naleza do 3 roznych odpowiedzialnosci (uwierzytelnianie,
         * raportowanie, moderacja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SplitUserServiceIntoThreeCohesiveInterfaces {
        /*
         * 🧪 Zadanie 23:
         * Rozbij UserService z Zadania 22 na 3 spojne interfejsy:
         * AuthenticationService (register/login/sendPasswordResetEmail),
         * ReportingService (generateActivityReport/exportUsersToJson),
         * ModerationService (banUser). Zaimplementuj je w JEDNEJ klasie
         * (moze implementowac wszystkie 3 naraz) i pokaz kod klienta zalezny
         * TYLKO od AuthenticationService.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignPluginSystemWithSegregatedCapabilityInterfaces {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj system pluginow: interfejs bazowy Plugin (getName()) +
         * opcjonalne interfejsy Configurable (configure(String)),
         * Schedulable (schedule(String cron)) - napisz 2 klasy pluginow, z
         * ktorych jedna implementuje tylko Plugin, druga wszystkie trzy.
         * Napisz metode sprawdzajaca `instanceof Configurable` przed
         * wywolaniem configure().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AnalyzeRealJdkInterfaceForIspCompliance {
        /*
         * 🧪 Zadanie 25:
         * Przeanalizuj (w komentarzu, bez uruchamiania) interfejs
         * java.util.List z JDK - czy zawiera metody, ktore NIEKTORE
         * implementacje (np. List.of(), czyli niemodyfikowalne listy) musza
         * "odrzucac" rzucajac UnsupportedOperationException (np. add/remove)?
         * Wyjasnij, czy to jest naruszenie ISP, czy swiadomy kompromis
         * projektowy JDK.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignNotificationChannelInterfacesWithOptionalFeatures {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj interfejs bazowy NotificationChannel (send(String
         * message)) oraz osobny DeliveryTrackable (getDeliveryStatus()) -
         * zaimplementuj EmailChannel (oba) i SmsChannel (tylko
         * NotificationChannel, bez sledzenia statusu). Napisz metode
         * `printStatusIfAvailable(NotificationChannel channel)` sprawdzajaca
         * `instanceof DeliveryTrackable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RefactorLegacyFatInterfaceStepByStep {
        /*
         * 🧪 Zadanie 27:
         * Napisz "tlusty" interfejs ReportGenerator z 5 metodami:
         * generatePdf(), generateExcel(), generateCsv(), emailReport(),
         * scheduleRecurring(). Zaimplementuj SimpleCsvReportGenerator, ktora
         * realnie potrafi tylko generateCsv() (reszta rzuca wyjatek).
         * Nastepnie KROK PO KROKU (min. 2 etapy w komentarzu) rozbij interfejs
         * na male czesci i pokaz finalna, poprawiona implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildGenericProcessorUsingOnlyNeededInterfaces {
        /*
         * 🧪 Zadanie 28:
         * Korzystajac z wynikow Zadania 27, napisz metode generyczna
         * `generateAllCsvReports(List<? extends CsvGeneratable> generators)`
         * (nazwij wlasciwy maly interfejs) dzialajaca dla dowolnej liczby
         * implementacji, bez odwolywania sie do metod PDF/Excel/email.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveIspComplianceChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste ISP (min.
         * 5 punktow: rozmiar interfejsu, spojnosc metod, obecnosc pustych/
         * rzucajacych wyjatek implementacji, mozliwosc podzialu wg klienta,
         * mozliwosc laczenia malych interfejsow w jednej klasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneDeviceAndServiceIspDemo {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: polacz w jednym demo (main()) DWA niezalezne
         * przyklady zgodne z ISP z tej lekcji: hierarchie urzadzen (Worker/
         * SmartHome/MediaPlayer - dowolna z poprzednich zadan) ORAZ hierarchie
         * uslug (UserService/ReportGenerator/NotificationChannel - dowolna z
         * poprzednich zadan). Napisz 1 wspolna metode generyczna
         * demonstrujaca, ze klient zalezy TYLKO od potrzebnego mu malego
         * interfejsu w OBU przypadkach.
         */
        public static void main(String[] args) { }
    }
}
