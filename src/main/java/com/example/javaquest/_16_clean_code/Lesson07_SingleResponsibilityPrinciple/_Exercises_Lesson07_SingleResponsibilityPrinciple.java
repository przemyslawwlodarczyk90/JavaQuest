package com.example.javaquest._16_clean_code.Lesson07_SingleResponsibilityPrinciple;

public class _Exercises_Lesson07_SingleResponsibilityPrinciple {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSrpInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * co oznacza "jeden powod do zmiany" i dlaczego to NIE to samo, co
         * "klasa ma miec tylko jedna metode".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_IdentifyResponsibilitiesInReportClass {
        /*
         * 🧪 Zadanie 2:
         * Dana jest klasa Report z metodami: generateData(), exportToPdf(),
         * sendByEmail(). W komentarzu wypisz, ile ROZNYCH powodow do zmiany
         * ma ta klasa i krotko opisz kazdy z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SpotSrpViolationInUserManagerSnippet {
        /*
         * 🧪 Zadanie 3:
         * Dany jest opis klasy UserManager: waliduje dane uzytkownika, zapisuje
         * uzytkownika do bazy danych ORAZ wysyla e-mail powitalny. W komentarzu
         * wskaz, ktora zasada jest zlamana i zaproponuj nazwy 3 nowych klas,
         * na jakie nalezaloby ja rozbic.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateSimpleOrderClassOnlyData {
        /*
         * 🧪 Zadanie 4:
         * Stworz klase Order z polami: String productName, int quantity,
         * double unitPrice oraz metoda calculateTotal() zwracajaca
         * quantity * unitPrice. Klasa ma robic TYLKO to - bez zapisu, bez
         * wydruku. Przetestuj z przykladowymi danymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateOrderRepositorySeparately {
        /*
         * 🧪 Zadanie 5:
         * Korzystajac z klasy Order z Zadania 4, stworz OSOBNA klase
         * OrderRepository z metoda save(Order order) - metoda ma jedynie
         * wypisac na konsole symulacje zapisu (np. "Zapisano zamowienie: ...").
         * Przetestuj oddzielnie od logiki liczenia sumy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListTwoReasonsToChangeForClass {
        /*
         * 🧪 Zadanie 6:
         * Dla klasy z Zadania 4 (Order, TYLKO dane + calculateTotal) w
         * komentarzu napisz: jaki jest JEDYNY powod, dla ktorego ta klasa
         * moglaby wymagac zmiany w przyszlosci?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CountResponsibilitiesInGivenClassDescription {
        /*
         * 🧪 Zadanie 7:
         * Opis klasy: EmployeeReportGenerator liczy premie pracownikow,
         * formatuje raport jako HTML oraz zapisuje raport na dysku. W
         * komentarzu policz i wypisz 3 osobne odpowiedzialnosci oraz
         * zaproponuj nazwy klas docelowych po rozbiciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CreatePrinterClassForOrder {
        /*
         * 🧪 Zadanie 8:
         * Korzystajac z klasy Order z Zadania 4, stworz OSOBNA klase
         * OrderPrinter z metoda print(Order order) wypisujaca sformatowane
         * podsumowanie zamowienia (nazwa, ilosc, cena, suma).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyGodObjectIsProblematic {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij w komentarzu (min. 3 zdania), czym jest
         * "God Object" (obiekt-bog) i jakim naruszeniem SOLID/SRP zazwyczaj
         * jest jego istnienie w projekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RefactorConstructorDoingValidationAndSaving {
        /*
         * 🧪 Zadanie 10:
         * Dany jest opis: konstruktor klasy Product waliduje dane (cena > 0)
         * ORAZ od razu zapisuje produkt do "bazy" (np. do statycznej listy).
         * Zaproponuj (w kodzie) rozbicie na Product (dane + walidacja we
         * wlasnym setterze/konstruktorze) i ProductRepository (zapis).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorInvoiceLikeClassFromScratch {
        /*
         * 🧪 Zadanie 11:
         * Napisz od podstaw wlasna wersje klasy BadReceipt, ktora liczy sume
         * paragonu, "zapisuje" go (symulacja) i "drukuje" go - wszystko w
         * jednej klasie. Nastepnie w tym samym pliku napisz rozbita wersje
         * (Receipt/ReceiptRepository/ReceiptPrinter) i porownaj obie w
         * komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestBusinessLogicWithoutTouchingIO {
        /*
         * 🧪 Zadanie 12:
         * Uzywajac rozbitej klasy Invoice z teorii lekcji (skopiuj/odtworz),
         * napisz metode testujaca TYLKO calculateTotal() dla 3 roznych
         * zestawow pozycji - bez wywolywania save() ani print(). Wypisz
         * PASS/FAIL dla kazdego przypadku (porownanie z oczekiwana suma).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignNotificationClassesBySrp {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj (i zaimplementuj) 2 klasy: NotificationContent (TYLKO
         * tresc/tytul powiadomienia) oraz NotificationSender (TYLKO wysylka -
         * symulowana printem) - zamiast jednej klasy robiacej oba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IdentifyHiddenSecondResponsibility {
        /*
         * 🧪 Zadanie 14:
         * Dana jest klasa Temperature z metoda toFahrenheit() ORAZ metoda
         * logToConsole() wypisujaca kazda konwersje do "logu" audytowego.
         * W komentarzu wskaz ukryta, dodatkowa odpowiedzialnosc i zaproponuj
         * rozdzielenie na Temperature i TemperatureLogger.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorReportClassIntoThreeParts {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj klase SalesReport (dane: lista kwot sprzedazy +
         * metoda sumTotal()), SalesReportFormatter (formatuje liste jako
         * String) i SalesReportExporter (symuluje zapis sformatowanego
         * raportu). Przetestuj cala trojke razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareTestabilityBeforeAndAfter {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj BadOrderProcessor (liczy rabat I zapisuje do "bazy"
         * w jednej metodzie process()) oraz GoodOrderProcessor (TYLKO liczy
         * rabat, zapis w osobnej klasie OrderRepository). W komentarzu opisz,
         * ktora wersja jest latwiejsza do przetestowania bez efektow
         * ubocznych (side effects) i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignConfigLoaderWithSingleResponsibility {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj klase ConfigParser z JEDNA odpowiedzialnoscia: parsowanie
         * tekstu w formacie "klucz=wartosc\n..." do Map<String,String>. Metoda
         * wczytywania z pliku ma byc w OSOBNEJ klasie (nie implementuj realnego
         * IO - wystarczy symulacja stringiem wejsciowym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorValidationOutOfEntityClass {
        /*
         * 🧪 Zadanie 18:
         * Dana jest klasa User z polem email oraz metoda isValidEmail()
         * zawierajaca skomplikowana logike walidacji (regex). Wydziel
         * walidacje do osobnej klasy EmailValidator z metoda statyczna
         * isValid(String email) i uzyj jej z klasy User.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureClassSizeBeforeAfterRefactor {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj (lub odtworz) BadInvoice z teorii lekcji oraz rozbita
         * wersje (Invoice/InvoiceRepository/InvoicePrinter). W komentarzu
         * policz przyblizona liczbe linii/metod kazdej klasy po rozbiciu i
         * napisz, dlaczego mniejsze klasy sa latwiejsze w utrzymaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainActorBasedDefinitionOfSrp {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij w komentarzu (min. 5 zdan) definicje SRP
         * opartaa na "aktorach"/interesariuszach (kto zglasza zmiane) zamiast
         * na liczbie metod - podaj wlasny przyklad 2 roznych aktorow, ktorzy
         * mogliby chciec zmienic te sama (zla) klase.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignLibrarySystemWithSrp {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj od zera mini-system biblioteczny: klasa Book (dane),
         * BookRepository (symulacja zapisu/wyszukiwania w liscie w pamieci),
         * BookAvailabilityChecker (TYLKO logika sprawdzania dostepnosci na
         * podstawie stanu wypozyczen). Zademonstruj wspolprace wszystkich
         * trzech klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorGodClassEmployeeManagementSystem {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj "zla" klase EmployeeSystem z metodami:
         * calculateSalary(), generatePayslipText(), saveToDatabase() (symulacja),
         * sendPayslipEmail() (symulacja) - wszystko w 1 klasie. Nastepnie
         * rozbij ja na minimum 4 klasy wedlug SRP i pokaz dzialajaca wersje
         * po refaktoryzacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignPluggableInvoiceExportFormats {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz InvoicePrinter z teorii tak, by istnialy 2 warianty:
         * PlainTextInvoicePrinter i HtmlInvoicePrinter (obie klasy TYLKO
         * formatuja - zero logiki liczenia sumy). Przetestuj obie z tym samym
         * obiektem Invoice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteUnitTestsForEachResponsibilitySeparately {
        /*
         * 🧪 Zadanie 24:
         * Dla rozbitych klas Invoice/InvoiceRepository/InvoicePrinter (teoria
         * lekcji) napisz 3 oddzielne metody testujace (bez frameworka, zwykle
         * asercje przez if+System.out) - po jednej dla kazdej klasy, dowodzac
         * ze mozna je testowac NIEZALEZNIE od siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_IdentifySrpViolationInThirdPartyLikeApi {
        /*
         * 🧪 Zadanie 25:
         * Dany jest opis klasy z hipotetycznej biblioteki: HttpClient, ktora
         * oprocz wysylania zadan HTTP TAKZE loguje kazde zadanie do pliku i
         * automatycznie parsuje JSON na obiekty domenowe. W komentarzu
         * zaproponuj podzial na min. 3 klasy/interfejsy zgodnie z SRP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureCouplingReductionAfterSrpRefactor {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj scenariusz: zmiana formatu zapisu faktury (z "pliku"
         * na "baze danych" - symulacja) w wersji PRZED rozbiciem (BadInvoice)
         * i PO rozbiciu (InvoiceRepository). W komentarzu opisz, ile miejsc w
         * kodzie trzeba bylo zmienic w kazdym przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignOrderProcessingPipelineWithSrp {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj potok przetwarzania zamowienia zlozony z minimum 4 klas
         * o pojedynczych odpowiedzialnosciach: walidacja danych zamowienia,
         * obliczenie ceny koncowej (z rabatem), zapis (symulacja), powiadomienie
         * klienta (symulacja) - polacz je w jednej metodzie orkiestrujacej
         * (np. w klasie OrderProcessingPipeline), ktora TYLKO woluje pozostale
         * klasy w kolejnosci (bez wlasnej logiki biznesowej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareSrpWithCohesionConcept {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz w komentarzu (min. 5 zdan) jak SRP wiaze sie
         * z pojeciem "wysokiej spojnosci" (high cohesion) klasy - odwolaj sie
         * do przykladu Invoice/InvoiceRepository/InvoicePrinter z teorii.
         * (Pelne omowienie coupling/cohesion bedzie w Lesson12.)
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RefactorLegacyReportGeneratorEndToEnd {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj kompletny scenariusz "przed/po": klasa
         * LegacyMonthlyReport robiaca wszystko (zbieranie danych sprzedazy,
         * liczenie statystyk, formatowanie tekstowe, "zapis" do pliku) - a
         * potem jej pelna refaktoryzacja na min. 4 male klasy wedlug SRP,
         * zademonstrowana dzialajacym kodem obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteSrpChecklistAndApplyToCapstoneIdea {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz w komentarzu wlasna "checkliste SRP"
         * (min. 4 pytania, ktore mozna zadac o dowolna klase, zeby sprawdzic,
         * czy lamie SRP), a nastepnie zaimplementuj maly przyklad (dowolna
         * dziedzina, np. system rezerwacji sal) skladajacy sie z min. 3 klas,
         * ktory PRZECHODZI Twoja checkliste.
         */
        public static void main(String[] args) { }
    }
}
