package com.example.javaquest._16_clean_code.Lesson08_OpenClosedPrinciple;

public class _Exercises_Lesson08_OpenClosedPrinciple {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainOcpInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * co oznacza "otwarte na rozszerzenie, zamkniete na modyfikacje" i
         * podaj wlasny (inny niz z teorii) przyklad sytuacji, gdzie widziales
         * lub mozesz sobie wyobrazic rosnacy if/else po typie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_IdentifyOcpViolationInShapeAreaMethod {
        /*
         * 🧪 Zadanie 2:
         * Dana jest metoda calculateArea(String shapeType, double... dims),
         * ktora liczy pole figury na podstawie if/else po shapeType ("CIRCLE",
         * "RECTANGLE", "TRIANGLE"). W komentarzu wskaz, co sie stanie, gdy
         * trzeba bedzie dodac obsluge "TRAPEZOID" i dlaczego to narusza OCP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteBadSwitchBasedShippingCostCalculator {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode calculateShippingCost(String method, double weight)
         * z switch po method ("STANDARD", "EXPRESS", "OVERNIGHT") liczaca
         * koszt wysylki - celowo w stylu, ktory narusza OCP (jak w teorii).
         * Przetestuj dla wszystkich 3 wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateShippingCostStrategyInterface {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj interfejs ShippingCostPolicy z metoda
         * calculateCost(double weight) - to szkielet pod refaktoryzacje
         * Zadania 3 (implementacje - w kolejnych zadaniach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementStandardAndExpressShippingPolicies {
        /*
         * 🧪 Zadanie 5:
         * Korzystajac z interfejsu z Zadania 4, zaimplementuj
         * StandardShippingPolicy i ExpressShippingPolicy - kazda z inna stawka
         * za kg. Przetestuj obie z ta sama waga.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddOvernightShippingWithoutModifyingExisting {
        /*
         * 🧪 Zadanie 6:
         * Dodaj TRZECIA implementacje - OvernightShippingPolicy - bez
         * modyfikowania klas z Zadania 5. W komentarzu potwierdz, ze zadna
         * istniejaca klasa nie zostala zmieniona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyEditingWorkingCodeIsRisky {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: napisz w komentarzu (min. 3 zdania), dlaczego edycja
         * dzialajacej, przetestowanej metody (np. dopisanie kolejnego "else if")
         * jest ryzykowna, nawet jesli zmiana "wydaje sie prosta".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RecognizeOcpCompliantCodeSnippet {
        /*
         * 🧪 Zadanie 8:
         * Dany jest opis: system ma interfejs PaymentMethod z implementacjami
         * CardPayment, BlikPayment, a nowy sposob platnosci dodaje sie przez
         * nowa klase implementujaca PaymentMethod. W komentarzu wyjasnij,
         * dlaczego ten opis jest ZGODNY z OCP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteSimplePolicyCalculatorUsingPolymorphism {
        /*
         * 🧪 Zadanie 9:
         * Napisz klase ShippingCostCalculator z metoda
         * calculate(ShippingCostPolicy policy, double weight) delegujaca do
         * policy.calculateCost(weight) - przetestuj z kazda z 3 polityk z
         * Zadan 5-6.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareLinesChangedBadVsGoodWhenAddingVariant {
        /*
         * 🧪 Zadanie 10:
         * W komentarzu porownaj: ile linii/plikow trzeba bylo zmienic, zeby
         * dodac "OVERNIGHT" w wersji z if/else (Zadanie 3) vs w wersji z
         * interfejsem (Zadanie 6). Wyciagnij wniosek.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorTaxCalculatorFromIfElseToPolymorphism {
        /*
         * 🧪 Zadanie 11:
         * Napisz "zla" metode calculateTax(String country, double income) z
         * if/else po kraju ("PL", "DE", "FR"), a potem zrefaktoryzuj na
         * interfejs TaxPolicy + 3 implementacje + klasa TaxCalculator
         * uzywajaca interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddFourthCountryWithoutTouchingCalculator {
        /*
         * 🧪 Zadanie 12:
         * Dodaj implementacje SpainTaxPolicy do systemu z Zadania 11 - bez
         * zadnej zmiany w TaxCalculator ani w pozostalych 3 implementacjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignNotificationChannelHierarchy {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj interfejs NotificationChannel z metoda send(String
         * message) oraz implementacje EmailChannel i SmsChannel (symulacja
         * printem). Napisz klase NotificationDispatcher wysylajaca wiadomosc
         * przez LISTE kanalow (List<NotificationChannel>) bez znajomosci ich
         * konkretnych typow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IdentifyOcpSmellInValidationMethod {
        /*
         * 🧪 Zadanie 14:
         * Dana jest metoda validate(String fieldType, String value) z if/else
         * po fieldType ("EMAIL", "PHONE", "PESEL"). W komentarzu zaproponuj
         * refaktoryzacje na interfejs FieldValidator + implementacje, opisujac
         * sygnatury metod (bez pelnej implementacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementFieldValidatorHierarchyFromExercise14 {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj w pelni projekt z Zadania 14: interfejs FieldValidator
         * z metoda boolean isValid(String value) oraz min. 2 implementacje
         * (np. EmailFieldValidator, PhoneFieldValidator). Przetestuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignReportExportStrategyPattern {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj interfejs ReportExporter z metoda export(String
         * reportContent) oraz implementacje PlainTextReportExporter i
         * CsvReportExporter - klasa ReportService ma korzystac WYLACZNIE z
         * interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainRelationshipBetweenOcpAndSrp {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: w komentarzu (min. 4 zdania) wyjasnij, dlaczego
         * gigantyczny if/else po typie CZESTO narusza rowniez SRP (Lesson07) -
         * odwolaj sie do przykladu BadDiscountCalculator z teorii tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorAreaCalculatorForShapes {
        /*
         * 🧪 Zadanie 18:
         * Napisz "zla" metode calculateArea(String shapeType, double... dims)
         * (jak w Zadaniu 2), a potem zrefaktoryzuj na interfejs Shape z metoda
         * area(), z implementacjami Circle, Rectangle, Triangle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ComputeTotalAreaOfMixedShapesList {
        /*
         * 🧪 Zadanie 19:
         * Korzystajac z hierarchii Shape z Zadania 18, stworz
         * List<Shape> z roznymi figurami i policz SUME pol wszystkich figur
         * przy pomocy jednej petli (bez if/else po typie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AddPentagonShapeWithoutModifyingCalculator {
        /*
         * 🧪 Zadanie 20:
         * Dodaj implementacje Pentagon (pieciokat foremny, wzor na pole:
         * (5 * bok^2) / (4 * tan(PI/5))) do hierarchii z Zadania 18 - bez
         * zmieniania kodu, ktory sumuje pola figur z Zadania 19.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignPluginStyleReportFormattingSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj system formatowania raportow z min. 3 formatami
         * (np. Text/Html/Markdown) jako implementacje wspolnego interfejsu,
         * plus klase ReportFormatterRegistry przechowujaca formatery w
         * Map<String, ReportFormatter> i pozwalajaca "zarejestrowac" nowy
         * formater bez zmiany kodu rejestru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorLegacyDiscountEngineWithMultipleRules {
        /*
         * 🧪 Zadanie 22:
         * Napisz "zla" metode z zagniezdzonymi if/else liczaca rabat na
         * podstawie KILKU warunkow naraz (typ klienta + kwota zamowienia), a
         * nastepnie zrefaktoryzuj do listy obiektow DiscountRule (interfejs z
         * metodami boolean appliesTo(...) i double apply(...)), iterowanej
         * przez DiscountEngine.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementChainOfDiscountRulesAppliedInOrder {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz DiscountEngine z Zadania 22 tak, by mozna bylo zastosowac
         * WIELE regul po kolei (List<DiscountRule>), sumujac rabaty - dodaj
         * NOWA regule bez zmiany silnika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignEventHandlerRegistryOcpStyle {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj prosty system obslugi zdarzen: interfejs EventHandler
         * z metoda handle(String eventPayload), klase EventBus przechowujaca
         * List<EventHandler> i metoda publish(String payload) wywolujaca
         * wszystkich zarejestrowanych handlerow. Dodaj 3 rozne handlery bez
         * modyfikacji EventBus.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareStrategyPatternWithVisitorPatternBriefly {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: zaimplementuj przyklad wzorca Strategy (jak w teorii
         * lekcji) i w komentarzu (min. 4 zdania) opisz jednym akapitem, czym
         * rozni sie od wzorca Visitor jako alternatywnego sposobu dodawania
         * zachowan bez modyfikacji istniejacych klas (bez pelnej implementacji
         * Visitor - wystarczy opis koncepcyjny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignExtensibleFileParserSystem {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj interfejs FileParser z metoda parse(String content) oraz
         * implementacje CsvFileParser i JsonLikeFileParser (uproszczone
         * parsowanie, bez zewnetrznych bibliotek). Klasa FileParserFactory
         * ma wybierac odpowiedni parser na podstawie rozszerzenia pliku,
         * przechowujac mapowanie w Map<String, FileParser> (rejestracja, nie
         * if/else).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureOcpComplianceOfOwnCodebase {
        /*
         * 🧪 Zadanie 27:
         * Przejrzyj (w komentarzu, bez uruchamiania) kod, ktory napisales w
         * poprzednich lekcjach/rozdzialach kursu - znajdz i opisz 1 miejsce,
         * ktore Twoim zdaniem narusza OCP oraz zaproponuj (opisowo, bez pelnej
         * implementacji) jak by je zrefaktoryzowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementTemplateMethodAsOcpAlternative {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj abstrakcyjna klase ReportGenerationTemplate z metoda
         * finalna generate() wywolujaca kroki (collectData(), formatData() -
         * metody abstrakcyjne) - stworz 2 podklasy z roznymi implementacjami
         * krokow. W komentarzu wyjasnij, jak to tez realizuje OCP (wzorzec
         * Template Method).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignFullDiscountAndShippingSystemCombined {
        /*
         * 🧪 Zadanie 29:
         * Polacz DiscountPolicy (z teorii lekcji) i ShippingCostPolicy
         * (z Zadania 4-6) w jeden system OrderPriceCalculator liczacy cene
         * koncowa zamowienia (cena - rabat + koszt wysylki) - obie zaleznosci
         * wstrzykiwane jako parametry metody (bez `new` wewnatrz kalkulatora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneExtensiblePricingEngine {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj kompletny, rozszerzalny "silnik
         * cenowy" dla sklepu internetowego - min. 2 rodziny polityk (np.
         * rabaty + koszty wysylki), kazda za wspolnym interfejsem, plus klasa
         * orkiestrujaca liczaca cene koncowa. Zademonstruj dodanie PO FAKCIE
         * nowej polityki KAZDEGO rodzaju bez zmiany istniejacego kodu -
         * potwierdz to komentarzem.
         */
        public static void main(String[] args) { }
    }
}
