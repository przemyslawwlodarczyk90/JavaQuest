package com.example.javaquest._16_clean_code.Lesson09_LiskovSubstitutionPrinciple;

public class _Exercises_Lesson09_LiskovSubstitutionPrinciple {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainLspInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * co oznacza "podtyp musi byc podstawialny za typ bazowy" i podaj
         * przyklad (inny niz kwadrat/prostokat) sytuacji z zycia, gdzie
         * "podtyp" zachowuje sie inaczej, niz sugeruje "typ bazowy".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RecreateBadRectangleAndBadSquare {
        /*
         * 🧪 Zadanie 2:
         * Odtworz (skopiuj lub napisz od nowa) klasy BadRectangle i BadSquare
         * z teorii lekcji. Stworz obiekt BadSquare(5), wywolaj setWidth(10) i
         * wypisz szerokosc oraz wysokosc PO wywolaniu - zaobserwuj efekt
         * uboczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteInvariantTestForRectangle {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode testRectangle(BadRectangle r), ktora ustawia
         * szerokosc=6, wysokosc=3, sprawdza czy area()==18, i wypisuje
         * PASS/FAIL. Wywolaj ja dla zwyklego BadRectangle - powinno byc PASS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ShowInvariantTestFailsForSquare {
        /*
         * 🧪 Zadanie 4:
         * Uzyj metody testRectangle(BadRectangle r) z Zadania 3, ale wywolaj
         * ja z obiektem BadSquare podstawionym jako BadRectangle - zaobserwuj
         * i wypisz, ze test daje FAIL (area() != oczekiwana wartosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateSharedShapeInterface {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj interfejs Shape z metoda int area() - to fundament pod
         * poprawna (zgodna z LSP) hierarchie figur w kolejnych zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementIndependentRectangleAndSquare {
        /*
         * 🧪 Zadanie 6:
         * Korzystajac z interfejsu Shape z Zadania 5, zaimplementuj klasy
         * Rectangle (szerokosc+wysokosc niezalezne) i Square (jeden bok) -
         * BEZ zadnego dziedziczenia miedzy nimi. Przetestuj area() dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyStructuralMatchIsNotEnough {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij w komentarzu (min. 3 zdania), dlaczego
         * fakt, ze BadSquare ma WSZYSTKIE metody BadRectangle (jest
         * "strukturalnie" poprawnym podtypem - kompiluje sie bez bledu) NIE
         * oznacza, ze spelnia LSP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyLspViolationInBirdExample {
        /*
         * 🧪 Zadanie 8:
         * Dany jest opis: klasa Bird z metoda fly(), oraz podklasa Penguin
         * extends Bird, ktora przeslania fly() rzucajac
         * UnsupportedOperationException. W komentarzu wyjasnij, dlaczego to
         * narusza LSP i zaproponuj (opisowo) lepsza hierarchie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementFixedBirdHierarchy {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj poprawiona wersje hierarchii z Zadania 8: interfejs
         * Bird (np. z metoda eat()) oraz osobny interfejs FlyingBird
         * (z metoda fly()) - Sparrow implementuje oba, Penguin implementuje
         * TYLKO Bird. Przetestuj obie klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWarningSignsOfLspViolation {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu min. 3 "sygnaly ostrzegawcze"
         * (code smells) sugerujace naruszenie LSP w kodzie (np. przeslonieta
         * metoda rzuca nowy wyjatek, ktorego metoda bazowa nie rzucala).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignVehicleHierarchyRespectingLsp {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj interfejs Vehicle z metoda accelerate() oraz 2 klasy
         * (Car, Bicycle) - upewnij sie, ze obie implementacje "zachowuja sie"
         * zgodnie z oczekiwaniem interfejsu (np. accelerate() zawsze zwieksza
         * predkosc, nigdy nie rzuca wyjatku "nieobslugiwana operacja").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_IdentifyLspViolationInFileReaderExample {
        /*
         * 🧪 Zadanie 12:
         * Dany jest opis: klasa ReadOnlyFile z metoda write(String data)
         * rzucajaca UnsupportedOperationException, dziedziczaca po File z
         * metodami read()/write() obiema "normalnie" dzialajacymi. W
         * komentarzu wyjasnij naruszenie LSP i zaproponuj podzial na
         * ReadableFile/WritableFile.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementReadableAndWritableFileInterfaces {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj projekt z Zadania 12: interfejsy ReadableFile
         * (read()) i WritableFile (write(String)), klasa TextFile
         * implementujaca oba, klasa ReadOnlyLogFile implementujaca TYLKO
         * ReadableFile. Przetestuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteGenericSubstitutionTest {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode generyczna `static void assertBehavior(Shape s, int
         * expectedArea, String label)`, ktora porownuje s.area() z
         * expectedArea i wypisuje PASS/FAIL z etykieta - uzyj jej do
         * przetestowania min. 3 roznych implementacji Shape.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignImmutableRectangleAndSquareWithoutSetters {
        /*
         * 🧪 Zadanie 15:
         * Przeprojektuj Rectangle i Square (z teorii/Zadania 6) tak, by byly
         * NIEZMIENNE (immutable - final pola, brak setterow, tylko
         * konstruktor) - w komentarzu wyjasnij, dlaczego brak mutowalnych
         * setterow CALKOWICIE eliminuje pierwotny problem z Square/Rectangle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_IdentifyLspViolationInCollectionExample {
        /*
         * 🧪 Zadanie 16:
         * Dany jest opis: klasa ImmutableList extends ArrayList, ktora
         * przeslania add()/remove() tak, by zawsze rzucaly
         * UnsupportedOperationException. W komentarzu wyjasnij, dlaczego to
         * narusza LSP wzgledem kodu, ktory oczekuje "zwyklej" listy (por.
         * realny problem z java.util.Collections.unmodifiableList - jak to
         * API unika tego problemu, nie dziedziczac po ArrayList).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignPaymentMethodHierarchyRespectingLsp {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj interfejs PaymentMethod z metoda boolean pay(double
         * amount) - zaimplementuj CardPayment i CashPayment tak, by OBIE
         * zachowywaly sie spojnie (zwracaly true/false wedlug tej samej
         * logiki sukcesu, bez rzucania wyjatkow, ktorych druga nie rzuca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteTestSuiteForPaymentMethodSubtypes {
        /*
         * 🧪 Zadanie 18:
         * Napisz JEDNA metode testowa `static void testPaymentContract(
         * PaymentMethod method)` sprawdzajaca podstawowe oczekiwania
         * kontraktu (np. pay(0) zwraca false/rzuca IllegalArgumentException -
         * ustal wlasny kontrakt) i uruchom ja dla obu implementacji z
         * Zadania 17 - obie musza przejsc IDENTYCZNY test.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorAccountHierarchyToFixLspViolation {
        /*
         * 🧪 Zadanie 19:
         * Napisz "zla" klase SavingsAccount extends BankAccount, ktora
         * przeslania withdraw(double amount) tak, by zawsze rzucac wyjatek
         * (konto oszczednosciowe "nie pozwala" na wyplaty w tej wersji) -
         * pokaz, ze kod testujacy dowolny BankAccount.withdraw() zawodzi dla
         * SavingsAccount. Nastepnie zrefaktoryzuj do wspolnego interfejsu
         * Account + osobnych podinterfejsow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeLspInComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu krotka tabele/liste porownujaca
         * "zgodne z LSP" i "niezgodne z LSP" uzycie dziedziczenia - min. 3
         * pary przykladow (moga byc z tej lekcji lub wlasne).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignEmployeeHierarchyWithSalaryCalculation {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj interfejs Employee z metoda double calculateMonthlyPay()
         * - zaimplementuj FullTimeEmployee i ContractorEmployee tak, by OBIE
         * zawsze zwracaly sensowna, nieujemna wartosc (bez specjalnych
         * przypadkow/wyjatkow lamiacych kontrakt).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildPolymorphicPayrollProcessor {
        /*
         * 🧪 Zadanie 22:
         * Korzystajac z hierarchii z Zadania 21, napisz klase
         * PayrollProcessor z metoda processAll(List<Employee> employees)
         * sumujaca wyplaty - dodaj TRZECI typ pracownika (np. InternEmployee)
         * i pokaz, ze PayrollProcessor dziala bez zadnej zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IdentifyAndFixLspViolationInStackVsQueue {
        /*
         * 🧪 Zadanie 23:
         * Dany jest opis: klasa LimitedStack extends Stack (z java.util),
         * ktora przeslania push() tak, by po przekroczeniu limitu ROZMIARU
         * CICHO IGNOROWAC nowy element (zamiast go dodac, jak zrobilby
         * zwykly Stack). W komentarzu wyjasnij naruszenie LSP i zaproponuj
         * (kompozycja zamiast dziedziczenia) wlasna klase BoundedStack BEZ
         * dziedziczenia po Stack.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBoundedStackUsingComposition {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj projekt z Zadania 23: klasa BoundedStack<T> majaca
         * WEWNATRZ pole `java.util.Deque<T>` (kompozycja, nie dziedziczenie),
         * z metodami push(T)/pop() i limitem rozmiaru zwracajacym false/rzucajacym
         * WLASNY, udokumentowany wyjatek przy przepelnieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignDocumentHierarchyWithConsistentSaveContract {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj interfejs Document z metoda boolean save() - zaimplementuj
         * PdfDocument i WordDocument tak, by kontrakt (co oznacza true/false,
         * kiedy rzucany jest wyjatek) byl IDENTYCZNY dla obu klas. Napisz test
         * potwierdzajacy zgodnosc kontraktu dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AnalyzeRealJdkLspCompliantHierarchy {
        /*
         * 🧪 Zadanie 26:
         * Przeanalizuj (w komentarzu, bez uruchamiania) hierarchie
         * java.util.List -> ArrayList/LinkedList z JDK - wyjasnij, dlaczego
         * ta hierarchia POPRAWNIE spelnia LSP (mozna podstawic ArrayList w
         * miejsce LinkedList i odwrotnie bez zmiany poprawnosci dzialania
         * kodu operujacego na interfejsie List).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignNotifierHierarchyWithPreconditionCheck {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj interfejs Notifier z metoda void notify(String message)
         * - zaimplementuj 2 klasy, z ktorych JEDNA (celowo zla, do analizy)
         * dorzuca DODATKOWY warunek wstepny (np. rzuca wyjatek dla wiadomosci
         * dluzszych niz 10 znakow, czego interfejs nie zapowiada). W
         * komentarzu wyjasnij, dlaczego "wzmacnianie warunkow wstepnych"
         * (strengthening preconditions) w podtypie tez lamie LSP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FixNotifierByWeakeningPostconditionsCorrectly {
        /*
         * 🧪 Zadanie 28:
         * Popraw klase z Zadania 27 tak, by NIE dorzucala dodatkowych
         * warunkow wstepnych (akceptuje kazdy String zgodnie z kontraktem
         * interfejsu) - w komentarzu wyjasnij zasade "podtyp moze tylko
         * OSLABIAC warunki wstepne i WZMACNIAC warunki koncowe" (Design by
         * Contract, Bertrand Meyer).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveLspComplianceChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu wlasna, szczegolowa checkliste
         * LSP (min. 5 punktow: np. "czy podtyp rzuca nowe wyjatki?", "czy
         * wzmacnia warunki wstepne?", "czy oslabia warunki koncowe?", "czy
         * zmienia widoczny stan obiektu w nieoczekiwany sposob?" itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneShapeAndAccountLspDemo {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: polacz w jednym demo (main()) DWA
         * niezalezne przyklady zgodne z LSP z tej lekcji: hierarchie Shape
         * (Rectangle/Square/min. 1 nowa figura) ORAZ hierarchie
         * Account/Employee/PaymentMethod (dowolna z poprzednich zadan) -
         * napisz 1 wspolna metode generyczna testujaca substitutability dla
         * obu hierarchii i wypisz PASS dla wszystkich przypadkow.
         */
        public static void main(String[] args) { }
    }
}
