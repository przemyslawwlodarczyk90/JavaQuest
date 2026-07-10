package com.example.javaquest._16_clean_code.Lesson06_ClassesAndResponsibilities;

public class _Exercises_Lesson06_ClassesAndResponsibilities {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ApplyOneSentenceTestToThreeOwnClasses {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wybierz (lub wymysl) 3 klasy i sprobuj opisac KAZDA
         * jednym zdaniem bez spojnika "i" - dla kazdej napisz PASS (da sie) lub
         * FAIL (nie da sie, bo robi za duzo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteClassWithTooManyPublicHelperMethods {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase TemperatureConverter z metoda konwertujaca Celsjusz na
         * Fahrenheita, ale zaimplementowana przez 3 DODATKOWE metody publiczne
         * (multiplyByRatio, addOffset, roundResult), ktore w praktyce sa tylko
         * krokami wewnetrznymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MakeHelperMethodsPrivate {
        /*
         * 🧪 Zadanie 3:
         * Popraw klase z Zadania 2, oznaczajac 3 metody pomocnicze jako
         * `private` - zostaw TYLKO 1 metode publiczna (convert). Zweryfikuj
         * identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteClassWithLowCohesionThreeUnrelatedFields {
        /*
         * 🧪 Zadanie 4:
         * Napisz klase UserProfileBox z 3 polami (String nickname, int
         * loginCount, String lastIpAddress) i 3 metodami, z ktorych KAZDA
         * uzywa TYLKO JEDNEGO pola. W komentarzu wskaz, ze to niska spojnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SplitLowCohesionClassIntoThreeCohesiveClasses {
        /*
         * 🧪 Zadanie 5:
         * Rozbij klase z Zadania 4 na 3 osobne, male klasy (kazda z 1 polem +
         * 1 metoda uzywajaca go) - zweryfikuj identyczne wyniki formatowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteClassWithFieldsInRandomOrder {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase z polami, konstruktorem i metodami w LOSOWEJ kolejnosci
         * (np. metoda publiczna, potem pole, potem konstruktor, potem druga
         * metoda) - powinna sie kompilowac (kolejnosc skladowych w Javie NIE
         * wplywa na dzialanie), ale byc trudna do czytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReorderClassToConventionalStructure {
        /*
         * 🧪 Zadanie 7:
         * Przepisz klase z Zadania 6 w konwencjonalnej kolejnosci: stale, pola,
         * konstruktor, metody publiczne, metody prywatne. Zweryfikuj
         * identyczne dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteHighCohesionClassWithAllMethodsUsingAllFields {
        /*
         * 🧪 Zadanie 8:
         * Napisz klase Rectangle z polami width/height, gdzie WSZYSTKIE 3
         * metody (area(), perimeter(), describe()) uzywaja OBU pol. W
         * komentarzu potwierdz wysoka spojnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyPublicMethodsThatShouldBePrivate {
        /*
         * 🧪 Zadanie 9:
         * Dana jest klasa z 5 metodami publicznymi, z ktorych 3 sa wywolywane
         * WYLACZNIE przez 2 pozostale (nigdy z zewnatrz). Napisz taka klase i w
         * komentarzu wskaz, KTORE 3 metody powinny byc `private`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListClassDesignRulesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 4
         * zasady projektowania klas z tej lekcji (rozmiar, enkapsulacja,
         * spojnosc, kolejnosc skladowych).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MeasureCohesionOfGivenRealisticClass {
        /*
         * 🧪 Zadanie 11:
         * Napisz klase Employee z 4 polami (name, baseSalary, yearsOfService,
         * department) i 4 metodami - dla KAZDEJ metody w komentarzu wypisz,
         * KTORYCH pol uzywa. Policz "wskaznik spojnosci" (srednia liczba pol
         * uzywanych na metode / liczba pol).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorEmployeeToImproveCohesionIfNeeded {
        /*
         * 🧪 Zadanie 12:
         * Na podstawie analizy z Zadania 11, jesli spojnosc jest niska - rozbij
         * Employee na 2 mniejsze klasy (np. EmployeeIdentity i
         * EmployeeCompensation). Jesli spojnosc byla wysoka, w komentarzu
         * uzasadnij, dlaczego podzial NIE jest potrzebny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignMinimalPublicApiForStackImplementation {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj wlasna klase BoundedStack<T> (oparta na
         * java.util.Deque) z publicznymi push/pop/peek/isEmpty, ale
         * WEWNETRZNA logika walidacji rozmiaru jako metoda `private`. Wywolaj
         * publiczne API i pokaz, ze dziala poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IdentifyGodClassAmongThreeGivenDescriptions {
        /*
         * 🧪 Zadanie 14:
         * Dane sa 3 opisy klas: (a) "liczy podatek od pensji", (b) "liczy
         * podatek, generuje PIT, wysyla do urzedu skarbowego, archiwizuje",
         * (c) "waliduje adres e-mail". W komentarzu wskaz, ktora (jesli
         * ktorakolwiek) to "god class" i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementAndSplitGodClassFromExercise14 {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj klase (b) z Zadania 14 jako 1 duza klasa TaxProcessor,
         * a nastepnie rozbij ja na min. 3 male klasy (TaxCalculator,
         * PitGenerator, TaxOfficeSubmitter) - wywolaj wszystkie razem w 1
         * przepływie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareTwoVersionsOfSameClassForEncapsulation {
        /*
         * 🧪 Zadanie 16:
         * Napisz 2 wersje klasy BankAccount: (a) z publicznymi polami balance i
         * publicznymi metodami pomocniczymi validateAmount/logTransaction, (b)
         * z prywatnym polem balance i prywatnymi metodami pomocniczymi, tylko
         * deposit/withdraw/getBalance publiczne. Porownaj bezpieczenstwo obu
         * wersji (czy zewnetrzny kod moze "zepsuc" stan obiektu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignCohesiveShoppingCartClass {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj klase ShoppingCart z polem List<String> items i metodami
         * addItem/removeItem/getItemCount/getSummary - WSZYSTKIE metody musza
         * uzywac pola items. Wywolaj wszystkie metody i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_IdentifyFieldsThatDoNotBelongInShoppingCart {
        /*
         * 🧪 Zadanie 18:
         * Dodaj do ShoppingCart z Zadania 17 pole `String lastVisitedPageUrl`
         * uzywane TYLKO przez 1 nowa metode `logPageVisit()`, niepowiazana z
         * items. W komentarzu wyjasnij, dlaczego to pole obniza spojnosc klasy
         * i gdzie powinno "naprawde" mieszkac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExtractMisplacedFieldToOwnClass {
        /*
         * 🧪 Zadanie 19:
         * Popraw ShoppingCart z Zadania 18, usuwajac pole
         * lastVisitedPageUrl/logPageVisit() i przenoszac je do osobnej klasy
         * PageVisitTracker - pokaz uzycie obu klas OBOK siebie (bez laczenia w
         * 1 klase).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealClassFromEarlierChapterForCohesionAndApiSize {
        /*
         * 🧪 Zadanie 20:
         * Wybierz 1 realna klase z dowolnej wczesniejszej lekcji kursu (np.
         * `_10_dao`). W komentarzu oceń: (a) czy spelnia test 1 zdania, (b)
         * czy publiczne API jest minimalne, (c) czy metody uzywaja
         * wiekszosci pol - dla kazdego punktu PASS/FAIL z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignRealisticLibrarySystemWithMultipleCohesiveClasses {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj system biblioteki z min. 3 spojnymi klasami: Book (dane
         * ksiazki), BorrowRecord (kto/kiedy wypozyczyl), LibraryCatalog
         * (przechowuje liste Book, wyszukuje po tytule) - kazda klasa musi
         * przejsc test 1 zdania. Zademonstruj wspoldzialanie wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorRealisticGodClassIntoCohesiveSystem {
        /*
         * 🧪 Zadanie 22:
         * Napisz "god class" LibrarySystemEverything laczaca WSZYSTKIE
         * odpowiedzialnosci z Zadania 21 w 1 klasie (dane ksiazek + zapisy
         * wypozyczen + wyszukiwanie), a nastepnie rozbij ja DOKLADNIE na
         * klasy z Zadania 21 - zweryfikuj identyczny wynik dzialania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignMinimalApiForCacheImplementation {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj prosta klase SimpleCache<K, V> (oparta na
         * java.util.HashMap) z publicznymi metodami put/get/containsKey/size -
         * logike ewentualnego czyszczenia/limitu rozmiaru zaimplementuj jako
         * `private`. Wywolaj publiczne API i pokaz dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureAndImproveCohesionOfRealisticOrderClass {
        /*
         * 🧪 Zadanie 24:
         * Napisz klase Order z 5 polami (customerName, items, totalPrice,
         * shippingAddress, trackingNumber) i 5 metodami. Dla kazdej metody w
         * komentarzu wypisz uzywane pola - jesli znajdziesz metody uzywajace
         * TYLKO 1 pola niepowiazanego z reszta, wydziel je do osobnej klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignClassHierarchyRespectingSizeAndCohesionTogether {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj system rezerwacji lotow z min. 4 malymi, spojnymi
         * klasami (Flight, Passenger, Seat, BookingConfirmation) - kazda musi
         * przejsc test 1 zdania I miec WYSOKA spojnosc (kazda metoda uzywa
         * wiekszosci pol). Zademonstruj pelny przeplyw rezerwacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareTwoDesignsForSameFeatureCohesionTradeoffs {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj TA SAMA funkcjonalnosc (system ocen studentow) na 2
         * sposoby: (a) 1 duza klasa StudentGradebook robaca wszystko, (b) 3
         * male klasy (Student, Grade, GradebookReport). Zaimplementuj OBIE
         * wersje i w komentarzu porownaj spojnosc kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignClassWithConstantsFieldsConstructorsInConventionalOrder {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj klase DiscountPolicy z 2 stalymi statycznymi (progi
         * rabatowe), 2 polami instancyjnymi, 1 konstruktorem, 2 metodami
         * publicznymi i 1 metoda prywatna - WSZYSTKO w konwencjonalnej
         * kolejnosci z teorii. Wywolaj publiczne metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildCohesionScoreCalculatorAsUtilityMethod {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `double estimateCohesionScore(int totalFields, int[]
         * fieldsUsedPerMethod)` liczaca prosty wskaznik spojnosci (srednia z
         * fieldsUsedPerMethod podzielona przez totalFields) - przetestuj dla
         * min. 2 roznych zestawow danych (wysoka i niska spojnosc) i wypisz
         * interpretacje wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveClassDesignChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu WLASNA, szczegolowa checkliste
         * (min. 5 punktow) do oceny projektu klasy - laczac test 1 zdania,
         * enkapsulacje, spojnosc, kolejnosc skladowych i rozszerzalnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneWellDesignedMiniSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * realistyczny mini-system (np. system zamowien w restauracji) skladajacy
         * sie z MIN. 4 klas, z ktorych KAZDA: przechodzi test 1 zdania, ma
         * minimalne publiczne API (szczegoly `private`), ma wysoka spojnosc
         * (metody uzywaja wiekszosci pol), i jest ulozona w konwencjonalnej
         * kolejnosci skladowych. Zademonstruj pelny przeplyw dzialania systemu.
         */
        public static void main(String[] args) { }
    }
}
