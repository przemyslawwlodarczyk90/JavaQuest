package com.example.javaquest._16_clean_code.Lesson12_CouplingCohesionAndLawOfDemeter;

public class _Exercises_Lesson12_CouplingCohesionAndLawOfDemeter {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCouplingAndLawOfDemeterInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) czym jest sprzezenie (coupling) i Prawo Demeter - i jak sie
         * ze soba lacza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteTightlyCoupledClassAccessingPublicField {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase Engine z PUBLICZNYM polem horsePower, i klase Car
         * siegajaca bezposrednio `engine.horsePower` do obliczen. To jest
         * wysokie sprzezenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FixTightCouplingWithPrivateFieldAndGetter {
        /*
         * 🧪 Zadanie 3:
         * Popraw klasy z Zadania 2: pole horsePower staje sie `private`, Engine
         * dostaje metode `describePower()` uzywajaca tego pola wewnetrznie -
         * Car woluje TYLKO describePower(), bez dostepu do pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteTrainWreckChainOfThreeGetters {
        /*
         * 🧪 Zadanie 4:
         * Odtworz klasy Order/Customer/Address z teorii lekcji (lub podobne
         * wlasne). Napisz kod wywolujacy `order.getCustomer().getAddress()
         * .getCity()` - policz w komentarzu, ile "kropek" (naruszen) zawiera
         * to wywolanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixTrainWreckWithOneDelegatingMethod {
        /*
         * 🧪 Zadanie 5:
         * Popraw kod z Zadania 4, dodajac metody delegujace
         * (Order.getCustomerCity(), Customer.getCity()) - kod zewnetrzny woluje
         * TYLKO `order.getCustomerCity()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyLawOfDemeterViolationInGivenSnippet {
        /*
         * 🧪 Zadanie 6:
         * Dany jest opis: kod `car.getEngine().getFuelTank().getCurrentLevel()`.
         * W komentarzu wskaz KAZDA "kropke" jako osobne naruszenie i policz
         * calkowita liczbe naruszen Prawa Demeter w tym wywolaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementFixedCarEngineFuelTankWithDelegation {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj klasy Car/Engine/FuelTank z Zadania 6 (z odpowiednimi
         * polami) i dodaj metody delegujace na kazdym poziomie tak, by kod
         * zewnetrzny wolal TYLKO `car.getFuelLevel()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainTellDontAskInOwnWords {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) zasade "Tell, Don't Ask" i jak rozwiazuje ona problem
         * naruszen Prawa Demeter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_RefactorAskStyleCodeToTellStyle {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode zewnetrzna `printDiscountedPrice(ShoppingCart cart)`,
         * ktora PYTA koszyk o liste produktow, sama liczy sume i sama liczy
         * rabat (styl "ask"). Zrefaktoryzuj: dodaj metode
         * `cart.calculateDiscountedTotal()` (styl "tell") - kod zewnetrzny
         * TYLKO ja woluje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWarningSignsOfTightCouplingAndDemeterViolations {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu min. 4 sygnaly ostrzegawcze
         * wysokiego sprzezenia/naruszenia Prawa Demeter (np. lancuchy getterow,
         * publiczne pola, metody "ask" zamiast "tell").
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignLooselyCoupledPaymentSystemUsingInterface {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj interfejs `Wallet` (getBalance(), withdraw(double)) -
         * klasa `Shop` przyjmuje `Wallet` przez konstruktor i uzywa TYLKO
         * interfejsu (nie zna konkretnej implementacji). Zaimplementuj 1
         * implementacje i zademonstruj zakup.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureCouplingBetweenTwoGivenClasses {
        /*
         * 🧪 Zadanie 12:
         * Napisz 2 klasy (np. `ReportBuilder` i `DataSource`), gdzie
         * ReportBuilder siega do 3 RÓŻNYCH publicznych pol DataSource
         * bezposrednio. W komentarzu policz "punkty sprzezenia" (liczbe
         * bezposrednich odwolan do wewnetrznych szczegolow innej klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ReduceCouplingByIntroducingSingleAccessMethod {
        /*
         * 🧪 Zadanie 13:
         * Zredukuj sprzezenie z Zadania 12: DataSource dostaje 1 metode
         * `buildSummary()` zwracajaca wszystko naraz jako String/obiekt -
         * ReportBuilder woluje TYLKO ta 1 metode. Porownaj liczbe "punktow
         * sprzezenia" przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteFourLevelChainAndCountDemeterViolations {
        /*
         * 🧪 Zadanie 14:
         * Napisz lancuch 4 klas (Company -> Department -> Team -> Leader) i
         * kod zewnetrzny wywolujacy `company.getDepartment().getTeam()
         * .getLeader().getName()`. W komentarzu policz, ile kropek/naruszen to
         * wywolanie zawiera (odpowiedz: 3, bo `getName()` na ostatnim obiekcie
         * jest juz bezposrednim uzyciem "znajomego").
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FixFourLevelChainWithDelegationAtEveryLevel {
        /*
         * 🧪 Zadanie 15:
         * Popraw lancuch z Zadania 14, dodajac metode delegujaca na KAZDYM
         * poziomie (Company.getLeaderName(), Department.getLeaderName(),
         * Team.getLeaderName()) - kod zewnetrzny woluje TYLKO
         * `company.getLeaderName()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignHighCohesionLowCouplingInvoiceModule {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj 2 klasy: `InvoiceLine` (wysoka spojnosc - metody uzywaja
         * wszystkich pol) i `InvoiceLineFormatter` (luzne sprzezenie - zna
         * TYLKO publiczne API InvoiceLine, nie jej wewnetrzne pola). Wywolaj
         * formatowanie kilku linii faktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_IdentifyWhenChainOnDtoIsAcceptableException {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj 2 recordy `AddressDto(String city)` i `CustomerDto(String
         * name, AddressDto address)` (proste DTO bez logiki biznesowej) -
         * wywolaj `customerDto.address().city()`. W komentarzu wyjasnij,
         * dlaczego to jest AKCEPTOWALNY wyjatek od Prawa Demeter (w
         * odroznieniu od obiektow z logika biznesowa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorAskStyleValidationToTellStyle {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode `boolean isCartEligibleForFreeShipping(ShoppingCart
         * cart)` pytajaca koszyk o liste produktow i sama liczaca sume cen
         * (styl "ask"). Zrefaktoryzuj do `cart.isEligibleForFreeShipping()`
         * (styl "tell") - logika przenosi sie DO ShoppingCart.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareCouplingOfTwoDesignsForSameFeature {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj TA SAMA funkcjonalnosc (obliczenie laczne kosztu
         * wysylki wg wagi i kraju) na 2 sposoby: (a) `ShippingCalculator`
         * siegajacy do publicznych pol `Package` i `Destination` bezposrednio,
         * (b) `Package`/`Destination` z metodami `calculateShippingCost(...)`
         * jako "tell". Porownaj sprzezenie obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeCouplingCohesionDemeterInComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu tabele laczaca 3 pojecia z tej
         * lekcji (sprzezenie, spojnosc, Prawo Demeter) - dla kazdego: 1
         * definicja, 1 sygnal naruszenia, 1 technika naprawy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignRealisticEcommerceModuleRespectingDemeter {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj system e-commerce z klasami Cart -> CartItem -> Product
         * (kazda z odpowiednimi polami) - napisz metode
         * `cart.calculateTotalWeight()` obliczajaca sume wag produktow BEZ
         * ZADNEGO lancucha getterow na zewnatrz (cala logika wewnatrz klas,
         * kazda metoda pyta TYLKO bezposredniego "znajomego").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorRealisticFeatureEnvyCodeIntoTellDontAsk {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode zewnetrzna liczaca "czy zamowienie kwalifikuje sie do
         * rabatu VIP" pytajac o dane klienta przez lancuch
         * `order.getCustomer().getLoyaltyPoints()` i
         * `order.getCustomer().getMembershipYears()` (2 osobne lancuchy = 2
         * naruszenia). Zrefaktoryzuj do 1 metody `order.isEligibleForVipDiscount()`
         * delegujacej do Customer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignLooselyCoupledEventSystemUsingInterfaces {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj interfejs `EventListener` (onEvent(String eventType)) -
         * klasa `EventBus` przechowuje `List<EventListener>` i NIE zna zadnej
         * konkretnej implementacji sluchacza. Zaimplementuj 2 rozne
         * implementacje (np. LoggingListener, CountingListener) i wywolaj
         * publikacje zdarzenia do obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureAndReduceCouplingInRealisticBankingSystem {
        /*
         * 🧪 Zadanie 24:
         * Napisz system bankowy z klasami Bank -> Branch -> Account, gdzie
         * kod zewnetrzny (np. metoda main) siega
         * `bank.getBranch(id).getAccount(accNo).getBalance()` (train wreck).
         * Zrefaktoryzuj do `bank.getAccountBalance(branchId, accNo)` delegujacej
         * przez wszystkie poziomy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignHighCohesionLowCouplingReportingModule {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj modul raportowania z min. 3 klasami: `SalesData`
         * (wysoka spojnosc - wlasne pola+metody), `SalesReportFormatter`
         * (zalezny TYLKO od publicznego API SalesData), `ReportPrinter`
         * (zalezny TYLKO od Stringa zwroconego przez formatter). Zademonstruj
         * pelny przeplyw generowania raportu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IdentifyHiddenCouplingThroughSharedMutableState {
        /*
         * 🧪 Zadanie 26:
         * Napisz 2 klasy dzielace WSPOLNA, mutowalna liste (np. `List<String>
         * sharedLog` przekazywana do obu konstruktorow) - zademonstruj, ze
         * zmiana wykonana przez 1 klase jest "niewidzialnie" widoczna w
         * drugiej. W komentarzu wyjasnij, dlaczego to TEZ jest forma
         * ukrytego, niebezpiecznego sprzezenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FixSharedMutableStateCouplingWithEncapsulation {
        /*
         * 🧪 Zadanie 27:
         * Popraw kod z Zadania 26: kazda klasa dostaje WLASNA, hermetyzowana
         * kopie danych (lub komunikuje sie przez jawne wywolania metod, nie
         * dzielony stan) - zademonstruj brak "niewidzialnego" wplywu jednej
         * klasy na druga.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildCouplingMetricAsUtilityMethod {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `int countMethodChainDots(String expression)`
         * przyjmujaca String reprezentujacy wywolanie (np.
         * "order.getCustomer().getAddress().getCity()") i zwracajaca liczbe
         * wywolan metod w lancuchu (na podstawie liczby "()" w stringu).
         * Przetestuj dla min. 3 roznych lancuchow o roznej dlugosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveCouplingCohesionDemeterChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 5
         * punktow) do oceny sprzezenia/spojnosci/zgodnosci z Prawem Demeter w
         * realnym kodzie - laczac wszystkie 3 pojecia z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneLooselyCoupledOrderFulfillmentSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny system
         * realizacji zamowien (Order -> Customer -> Address -> Warehouse -> Stock)
         * stosujacy WSZYSTKIE zasady z tej lekcji naraz: luzne sprzezenie
         * przez interfejsy tam, gdzie to sensowne, wysoka spojnosc w kazdej
         * klasie, ZERO naruszen Prawa Demeter (zadnego lancucha `a.getB()
         * .getC()` w kodzie zewnetrznym - wylacznie metody delegujace/tell).
         * Zademonstruj pelny przeplyw realizacji zamowienia.
         */
        public static void main(String[] args) { }
    }
}
