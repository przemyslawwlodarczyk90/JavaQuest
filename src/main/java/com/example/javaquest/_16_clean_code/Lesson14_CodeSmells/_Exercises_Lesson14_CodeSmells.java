package com.example.javaquest._16_clean_code.Lesson14_CodeSmells;

public class _Exercises_Lesson14_CodeSmells {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCodeSmellVsBugInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy "code smell" a "bledem" (bug) - dlaczego kod
         * ze smellem moze przechodzic wszystkie testy, a mimo to wymagac
         * refaktoringu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteLongMethodWithThreeSections {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `registerProduct(String name, double price, int
         * stock)` majaca 3 wyrazne sekcje (oddzielone komentarzem): walidacje
         * (name niepuste, price>0, stock>=0), obliczenia (VAT 23% doliczony do
         * price), i wypisanie potwierdzenia. To jest Long Method - nie
         * poprawiaj jej jeszcze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FixLongMethodWithExtractMethod {
        /*
         * 🧪 Zadanie 3:
         * Popraw metode z Zadania 2, rozbijajac ja na 3 male metody
         * (validateProduct, calculatePriceWithVat, printConfirmation) - metoda
         * glowna ma tylko je wolac. Zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteLargeClassWithThreeResponsibilities {
        /*
         * 🧪 Zadanie 4:
         * Napisz klase Order z polami i metodami obslugujacymi NARAZ: obliczanie
         * sumy zamowienia, generowanie tekstu faktury, oraz "wysylke"
         * powiadomienia e-mail (println). To jest Large Class.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixLargeClassWithExtractClass {
        /*
         * 🧪 Zadanie 5:
         * Popraw klase z Zadania 4, wydzielajac InvoiceGenerator i
         * OrderNotifier jako osobne klasy - klasa Order zostaje TYLKO z danymi
         * zamowienia i metoda liczaca sume. Pokaz uzycie wszystkich trzech
         * klas razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteMethodWithSixParameters {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode `createAccount(String firstName, String lastName,
         * String email, String phone, String country, boolean newsletter)` (6
         * parametrow) - to jest Long Parameter List.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FixLongParameterListWithParameterObject {
        /*
         * 🧪 Zadanie 7:
         * Popraw metode z Zadania 6, wprowadzajac record AccountData grupujacy
         * wszystkie 6 pol - nowa sygnatura to createAccount(AccountData data).
         * Wywolaj ja i porownaj czytelnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteDuplicatedValidationLogicInTwoMethods {
        /*
         * 🧪 Zadanie 8:
         * Napisz 2 metody: validateNewUser(String email) i
         * validateUserUpdate(String email) - OBIE zawieraja SKOPIOWANA
         * identyczna logike walidacji e-maila (zawiera "@" i "."). To jest
         * Duplicated Code.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixDuplicatedCodeWithSharedMethod {
        /*
         * 🧪 Zadanie 9:
         * Popraw kod z Zadania 8, wydzielajac wspolna metode isValidEmail(String
         * email), z ktorej korzystaja OBIE metody walidujace. Zweryfikuj
         * identyczny wynik dla poprawnego i niepoprawnego e-maila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IdentifySmellsInGivenSnippetDescription {
        /*
         * 🧪 Zadanie 10:
         * Dany jest opis: metoda o 8 parametrach, dlugosci 60 linii, ktora
         * pojawia sie (z drobnymi roznicami) w 2 rozny klasach. Bez terminala
         * w komentarzu wskaz WSZYSTKIE smelle z tej lekcji, ktore pasuja do
         * tego opisu (min. 3).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteFeatureEnvyMethodAccessingNestedGetters {
        /*
         * 🧪 Zadanie 11:
         * Odtworz klasy Address, Customer, Invoice z teorii lekcji (lub
         * podobne wlasne) i napisz metode `calculateShippingZoneFeatureEnvy(
         * Invoice invoice)`, ktora niemal wylacznie odwoluje sie do
         * invoice.customer.address.city - to jest Feature Envy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FixFeatureEnvyByMovingMethod {
        /*
         * 🧪 Zadanie 12:
         * Popraw metode z Zadania 11, PRZENOSZAC logike liczenia strefy
         * wysylki do klasy Address jako metoda `calculateShippingZone()` -
         * Invoice.calculateShippingZone() ma teraz tylko delegowac do
         * address.calculateShippingZone() (Tell, Don't Ask - patrz Lesson12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteDataClumpAcrossThreeMethods {
        /*
         * 🧪 Zadanie 13:
         * Napisz 3 metody przyjmujace RAZEM (w tej samej kolejnosci) parametry
         * (String street, String city, String zipCode): formatAddressLabel,
         * validateAddress, calculateDeliveryDistance - to jest Data Clump.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FixDataClumpWithAddressClass {
        /*
         * 🧪 Zadanie 14:
         * Popraw metody z Zadania 13, wprowadzajac record Address(String
         * street, String city, String zipCode) i przepisujac wszystkie 3
         * metody, by przyjmowaly JEDEN parametr typu Address.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WritePrimitiveObsessionForPhoneNumber {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode `sendSms(String phoneNumber, String message)`, gdzie
         * walidacja numeru telefonu (9 cyfr) jest POWTORZONA w tej metodzie i
         * jeszcze raz w osobnej metodzie `callPhone(String phoneNumber)`. To
         * jest Primitive Obsession (String zamiast typu PhoneNumber).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FixPrimitiveObsessionWithPhoneNumberType {
        /*
         * 🧪 Zadanie 16:
         * Popraw kod z Zadania 15, wprowadzajac klase/record PhoneNumber z
         * walidacja W KONSTRUKTORZE (rzuca IllegalArgumentException dla
         * niepoprawnego numeru) - sendSms i callPhone przyjmuja teraz
         * PhoneNumber zamiast String, bez powtorzonej walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteShotgunSurgeryForShippingCost {
        /*
         * 🧪 Zadanie 17:
         * Odtworz 3 klasy podobne do PricingCalculator/InvoiceFooterGenerator/
         * LoyaltyPointsCalculator z teorii, ale dla logiki "kosztu wysylki wg
         * wagi paczki" (0-1kg, 1-5kg, 5kg+) powtorzonej w KAZDEJ z 3 klas. To
         * jest Shotgun Surgery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FixShotgunSurgeryWithEnumHoldingLogic {
        /*
         * 🧪 Zadanie 18:
         * Popraw kod z Zadania 17, wprowadzajac enum WeightTier z metoda
         * calculateCost() zawierajaca CALA logike w JEDNYM miejscu - wszystkie
         * 3 klasy z Zadania 17 maja teraz TYLKO delegowac do WeightTier.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentifyMultipleSmellsInOneMethod {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode `processCustomerOrderMessy(String firstName, String
         * lastName, String street, String city, String zipCode, double price,
         * int qty, boolean vip)` (8 parametrow) laczaca walidacje+obliczenia+
         * formatowanie w jednej metodzie. W komentarzu wskaz WSZYSTKIE smelle
         * obecne naraz (min. 3: Long Method, Long Parameter List, Data Clump).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FixMethodWithMultipleSmellsStepByStep {
        /*
         * 🧪 Zadanie 20:
         * Popraw metode z Zadania 19 KROK PO KROKU: najpierw wprowadz
         * Address/Customer jako obiekty parametrow (Data Clump), potem rozbij
         * na male metody (Long Method) - pokaz finalna, czysta wersje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildRealisticGodClassWithFiveResponsibilities {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase ReportManager laczaca NARAZ: generowanie raportu
         * tekstowego, walidacje danych wejsciowych, "zapis do pliku"
         * (println), "wysylke e-mail" (println), i logike obliczania statystyk
         * (srednia, suma). To jest ekstremalny przypadek Large Class (5
         * odpowiedzialnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DecomposeGodClassIntoFiveFocusedClasses {
        /*
         * 🧪 Zadanie 22:
         * Rozbij ReportManager z Zadania 21 na 5 male, skupionych klas (jedna
         * na kazda odpowiedzialnosc) - napisz orkiestrator laczacy je wszystkie
         * i wywolaj cala operacje "od poczatku do konca".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IdentifyFeatureEnvyInThreeLevelObjectChain {
        /*
         * 🧪 Zadanie 23:
         * Napisz 4 klasy w lancuchu: Company -> Department -> Team -> Employee
         * (kazda ma referencje do nastepnej) - napisz metode zewnetrzna
         * `getTeamLeadName(Company c)` odwolujaca sie
         * company.department.team.employee.name (Feature Envy + naruszenie
         * Prawa Demeter - patrz Lesson12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FixFeatureEnvyByAddingDelegatingMethods {
        /*
         * 🧪 Zadanie 24:
         * Popraw kod z Zadania 23, dodajac metody delegujace na kazdym
         * poziomie (Company.getTeamLeadName(), Department.getTeamLeadName()
         * itd.) tak, by wywolujacy kod uzywal TYLKO `company.getTeamLeadName()`
         * bez lancucha kropek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildDataClumpAcrossFiveMethodsRealistic {
        /*
         * 🧪 Zadanie 25:
         * Napisz 5 metod w klasie BookingSystem przyjmujacych RAZEM (String
         * hotelName, String checkInDate, String checkOutDate, int guestCount) -
         * pokaz, ze kazda zmiana w tej "grudce danych" (np. dodanie nowego pola
         * roomType) wymagalaby zmiany az w 5 miejscach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FixDataClumpWithBookingRequestRecord {
        /*
         * 🧪 Zadanie 26:
         * Popraw kod z Zadania 25, wprowadzajac record BookingRequest -
         * przepisz wszystkie 5 metod, by przyjmowaly JEDEN obiekt. Dodaj nowe
         * pole roomType i pokaz, ze wymaga zmiany TYLKO w 1 miejscu (definicji
         * rekordu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AuditRealMethodFromEarlierChapterForSmells {
        /*
         * 🧪 Zadanie 27:
         * Wybierz 1 metode/klase z dowolnej wczesniejszej lekcji kursu (np.
         * `_10_dao` lub `_09_jdbc`) i w komentarzu przeanalizuj ja pod katem
         * WSZYSTKICH 8 smelli z tej lekcji - dla kazdego smellu napisz
         * OBECNY/BRAK z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignSmellDetectionChecklistAsCode {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `List<String> detectSmells(int methodLineCount, int
         * paramCount, int classFieldCount)`, ktora na podstawie prostych metryk
         * (np. methodLineCount>30 -> "Long Method", paramCount>=4 -> "Long
         * Parameter List", classFieldCount>15 -> "Large Class") zwraca liste
         * WYKRYTYCH smelli. Przetestuj dla min. 3 roznych zestawow wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveSmellCatalogSummary {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu tabele podsumowujaca WSZYSTKIE 8
         * smelli z tej lekcji - dla kazdego: nazwa, 1-zdaniowy sygnal
         * rozpoznawczy, nazwa techniki naprawy (bez szczegolow - poznasz je w
         * Lesson16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneMultiSmellRefactoringDemo {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz JEDNA realistyczna, "brudna" metode/
         * klase laczaca min. 4 rozne smelle z tej lekcji naraz (np. Long
         * Method + Long Parameter List + Data Clump + Duplicated Code), a
         * nastepnie w TYM SAMYM pliku napisz jej w PELNI poprawiona wersje,
         * usuwajaca WSZYSTKIE 4 smelle. Wywolaj obie wersje i pokaz identyczny
         * wynik koncowy.
         */
        public static void main(String[] args) { }
    }
}
