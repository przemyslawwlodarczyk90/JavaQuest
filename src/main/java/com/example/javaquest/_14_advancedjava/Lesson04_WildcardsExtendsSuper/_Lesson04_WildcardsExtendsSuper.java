package com.example.javaquest._14_advancedjava.Lesson04_WildcardsExtendsSuper;

import java.util.ArrayList;
import java.util.List;

public class _Lesson04_WildcardsExtendsSuper {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: WILDCARDY - ? extends I ? super ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 3 I ZAPOWIEDZ
         * ============================================================
         * - W Lekcji 3 poznales ograniczenia (bounded types) przy
         *   DEFINIOWANIU wlasnych klas/metod generycznych: `<T extends X>`.
         * - Ta lekcja pokazuje INNY mechanizm - wildcard `?` (znak zapytania,
         *   "dziki znak") - uzywany, gdy PRZYJMUJESZ generyczny typ jako
         *   parametr metody, a nie definiujesz wlasny typ generyczny.
         * - Wildcardy rozwiazuja konkretny, bardzo praktyczny problem:
         *   "dlaczego List<Integer> nie jest podtypem List<Number>, mimo ze
         *   Integer JEST podtypem Number?" - i pokazuja, jak napisac metody,
         *   ktore akceptuja List<Integer>, List<Double> i List<Number>
         *   jednoczesnie.
         */
        System.out.println("\nDzisiaj: ? (wildcard), ? extends X (producer), ? super X (consumer), roznica T vs ?.");

        /*
         * ============================================================
         * 🔹 PROBLEM: GENERYKI SA NIEWARIANTNE (INVARIANT)
         * ============================================================
         * - Mimo ze Integer JEST podtypem Number, List<Integer> NIE JEST
         *   podtypem List<Number> - generyki w Javie sa NIEWARIANTNE
         *   (invariant).
         * - To CELOWE ograniczenie jezyka - gdyby bylo inaczej, mozna by
         *   wlozyc Double do "List<Integer> udajacej List<Number>" i
         *   dostac ClassCastException w runtime (dokladnie problem, ktory
         *   generyki mialy rozwiazac!).
         * - Skutek: metoda `static void printAll(List<Number> list)` NIE
         *   przyjmie `List<Integer>` jako argumentu - kod ponizej pokazuje
         *   to (zakomentowane, bo nie skompilowaloby sie).
         */
        demonstrateInvarianceProblem();

        /*
         * ============================================================
         * 🔍 WILDCARD `?` - "NIEZNANY, ALE KONKRETNY TYP"
         * ============================================================
         * - Wildcard `?` uzywasz w TYPIE PARAMETRU metody (nigdy przy
         *   definiowaniu wlasnej klasy/interfejsu generycznego - do tego
         *   sluzy <T> z Lekcji 1-2).
         * - `List<?>` czytamy jako "lista jakiegos, nieznanego (ale
         *   konkretnego) typu" - w przeciwienstwie do raw `List` (ktora
         *   "zapomina" o generykach calkowicie i wraca do swiata sprzed
         *   Javy 5), `List<?>` WCIAZ jest w pelni bezpieczna typowo.
         * - Nieograniczony wildcard (unbounded wildcard) `List<?>` przyjmie
         *   List<String>, List<Integer>, List<CokolwiekBadz> - dowolna liste.
         */
        demonstrateUnboundedWildcard();

        /*
         * ============================================================
         * 🔹 GORNY WILDCARD (UPPER-BOUNDED): List<? extends Number> - PRODUCENT
         * ============================================================
         * - `List<? extends Number>` oznacza "lista jakiegos NIEZNANEGO
         *   podtypu Number" - moze to byc List<Integer>, List<Double>,
         *   List<Number> samo w sobie, itd.
         * - Dzieki temu metoda MOZE przyjac zarowno List<Integer>, jak i
         *   List<Double> - rozwiazuje dokladnie problem niewariancji z
         *   sekcji wyzej.
         * - CENA: skoro kompilator NIE WIE, czy to konkretnie List<Integer>
         *   czy List<Double>, to NIE POZWALA dodawac (add) NICZEGO do takiej
         *   listy (poza literalem `null`) - bo dodanie np. Integer mogloby
         *   naruszyc faktyczny typ, gdyby to byla "w sekrecie" List<Double>.
         * - MOZNA za to swobodnie CZYTAC elementy - kazdy z nich jest
         *   GWARANTOWANY jako co najmniej Number (bo wszystko, co jest
         *   podtypem Number, samo jest Number).
         * - Dlatego `? extends X` nazywamy "producentem" (producer) - lista
         *   PRODUKUJE (oddaje) dla Ciebie elementy typu X, ale nie przyjmuje
         *   nowych.
         */
        demonstrateUpperBoundedWildcard();

        /*
         * ============================================================
         * 🔍 DOLNY WILDCARD (LOWER-BOUNDED): List<? super Integer> - KONSUMENT
         * ============================================================
         * - `List<? super Integer>` oznacza "lista jakiegos NIEZNANEGO
         *   NADTYPU Integer" - moze to byc List<Integer>, List<Number> czy
         *   nawet List<Object>.
         * - CENA: skoro nie wiadomo, czy to konkretnie List<Integer> czy
         *   List<Object>, przy ODCZYCIE kompilator gwarantuje TYLKO, ze
         *   kazdy element jest co najmniej Object - wiec `get()` zwraca Object,
         *   NIE Integer (trzeba by rzutowac recznie, jesli potrzebny Integer).
         * - ZA TO MOZNA swobodnie DODAWAC (add) Integer (i kazdy jego podtyp) -
         *   bo Integer na pewno "zmiesci sie" w liscie NADTYPU Integer,
         *   niezaleznie czy to faktycznie List<Integer>, List<Number> czy
         *   List<Object>.
         * - Dlatego `? super X` nazywamy "konsumentem" (consumer) - lista
         *   KONSUMUJE (przyjmuje) od Ciebie elementy typu X, ale nie oddaje
         *   ich z powrotem jako cos bardziej szczegolowego niz Object.
         */
        demonstrateLowerBoundedWildcard();

        /*
         * ============================================================
         * 🔹 UNBOUNDED WILDCARD List<?> - CO WOLNO, A CZEGO NIE
         * ============================================================
         * - `List<?>` to najbardziej restrykcyjny przypadek - kompilator
         *   nie wie NIC o typie elementow (poza tym, ze sa jakims Object).
         * - MOZNA: czytac elementy jako Object, wywolywac size(), isEmpty(),
         *   clear() (bo clear() nie wymaga znajomosci typu elementow).
         * - NIE MOZNA: dodawac zadnych elementow (poza null) - bo kompilator
         *   nie ma ZADNEJ gwarancji, jaki typ by pasowal.
         * - Uzywasz List<?>, gdy metoda w ogole NIE INTERESUJE SIE typem
         *   elementow - np. `printSize(List<?> list)` (potrzebuje tylko
         *   size(), nie interesuje jej co jest w srodku).
         */
        demonstrateUnboundedWildcardRestrictions();

        /*
         * ============================================================
         * 📌 RÓZNICA MIEDZY WILDCARDEM A PARAMETREM TYPU <T>
         * ============================================================
         * - Parametr typu <T> (Lekcje 1-3) - UZYWASZ go, gdy definiujesz
         *   WLASNA klase/interfejs/metode generyczna, i gdy chcesz ODWOLAC
         *   SIE do TEGO SAMEGO typu w wielu miejscach sygnatury (np. metoda
         *   przyjmuje T i ZWRACA T - to ten sam, konkretny typ dla danego
         *   wywolania).
         * - Wildcard `?` - UZYWASZ go w TYPIE PARAMETRU metody, gdy typ
         *   elementow NIE MUSI byc powiazany z niczym innym w sygnaturze -
         *   np. metoda tylko CZYTA elementy albo tylko je DODAJE, i nie musi
         *   "pamietac" dokladnego typu miedzy parametrami/wynikiem.
         * - Regula kciuka (uproszczona): jesli w sygnaturze metody parametr
         *   typu wystepuje TYLKO RAZ i nie jest powiazany z typem zwracanym -
         *   prawdopodobnie wystarczy wildcard. Jesli TEN SAM typ musi
         *   "przeplywac" miedzy kilkoma parametrami albo do wyniku - potrzebny
         *   jest <T>.
         * - Ponizej: 2 wersje tej samej idei - jedna z <T>, jedna z wildcard -
         *   zeby zobaczyc roznice na wlasne oczy.
         */
        demonstrateWildcardVsTypeParameter();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE I ZAPOWIEDZ
         * ============================================================
         * - Generyki sa NIEWARIANTNE - List<Integer> to NIE jest List<Number>.
         * - `List<?>` (unbounded) - nieznany typ, tylko odczyt jako Object,
         *   zero dodawania (poza null).
         * - `List<? extends X>` (upper-bounded, PRODUCENT) - mozna CZYTAC
         *   jako X, NIE mozna dodawac (poza null).
         * - `List<? super X>` (lower-bounded, KONSUMENT) - mozna DODAWAC X
         *   (i podtypy), odczyt tylko jako Object.
         * - Wildcard rozni sie od <T> tym, ze nie "pamieta" konkretnego typu
         *   miedzy wywolaniami/parametrami - to inne narzedzie do innego celu.
         * - W Lekcji 5 poznasz MNEMONIK, ktory spina to wszystko w jedna,
         *   latwa do zapamietania regule: PECS - "Producer Extends,
         *   Consumer Super". Dzisiejsza lekcja dala Ci wszystkie klocki -
         *   Lekcja 5 pokaze, jak ulozyc je w prosta zasade na przyszlosc.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void demonstrateInvarianceProblem() {
        System.out.println("\n=== PROBLEM: GENERYKI SA NIEWARIANTNE ===");

        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3));

        // static void printAllNumbers(List<Number> list) { ... }
        // printAllNumbers(integers); // <-- TO NIE SKOMPILUJE SIE!
        // Blad: "List<Integer> nie moze byc przekonwertowane do List<Number>"
        // mimo ze Integer JEST podtypem Number.

        System.out.println("List<Integer> integers = " + integers);
        System.out.println("Mimo ze Integer extends Number, List<Integer> NIE JEST podtypem List<Number>.");
        System.out.println("Metoda przyjmujaca List<Number> odrzuci wywolanie z List<Integer> - blad kompilacji.");
    }

    private static void demonstrateUnboundedWildcard() {
        System.out.println("\n=== UNBOUNDED WILDCARD: List<?> ===");

        List<String> strings = List.of("a", "b", "c");
        List<Integer> integers = List.of(1, 2, 3);

        System.out.println("printSize(List<String>) = " + printSize(strings));
        System.out.println("printSize(List<Integer>) = " + printSize(integers));
        System.out.println("Ta sama metoda printSize(List<?>) przyjela OBA rozne typy list.");
    }

    /**
     * List<?> - nie interesuje nas typ elementow, tylko rozmiar listy.
     * Dzieki wildcardowi metoda przyjmuje DOWOLNA liste.
     */
    private static int printSize(List<?> list) {
        return list.size();
    }

    private static void demonstrateUpperBoundedWildcard() {
        System.out.println("\n=== UPPER-BOUNDED WILDCARD: List<? extends Number> (PRODUCENT) ===");

        List<Integer> integers = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.5, 2.5, 3.5);

        System.out.println("sumOfAnyNumberList(List<Integer>) = " + sumOfAnyNumberList(integers));
        System.out.println("sumOfAnyNumberList(List<Double>) = " + sumOfAnyNumberList(doubles));
        System.out.println("Jedna metoda z List<? extends Number> obsluzyla List<Integer> I List<Double>.");
    }

    /**
     * List<? extends Number> - "producent": mozemy CZYTAC elementy jako
     * Number (kazdy podtyp Number JEST Number), ale nie mozemy nic DODAC
     * do tej listy (poza null) - kompilator nie wie, czy to faktycznie
     * List<Integer>, List<Double> czy cos innego.
     */
    private static double sumOfAnyNumberList(List<? extends Number> list) {
        double total = 0;
        for (Number n : list) { // odczyt jako Number - zawsze bezpieczny
            total += n.doubleValue();
        }
        // list.add(5); // <-- TO NIE SKOMPILUJE SIE! Nie wiadomo, czy to List<Integer>,
        //                    List<Double> czy inny podtyp - dodanie mogloby zlamac typ.
        return total;
    }

    private static void demonstrateLowerBoundedWildcard() {
        System.out.println("\n=== LOWER-BOUNDED WILDCARD: List<? super Integer> (KONSUMENT) ===");

        List<Integer> intList = new ArrayList<>();
        List<Number> numberList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();

        addIntegers(intList);
        addIntegers(numberList);
        addIntegers(objectList);

        System.out.println("intList po addIntegers(...) = " + intList);
        System.out.println("numberList po addIntegers(...) = " + numberList);
        System.out.println("objectList po addIntegers(...) = " + objectList);
        System.out.println("Jedna metoda z List<? super Integer> DODAWALA do List<Integer>, List<Number> I List<Object>.");
    }

    /**
     * List<? super Integer> - "konsument": mozemy DODAWAC Integer (i jego
     * podtypy) - bo Integer na pewno "zmiesci sie" w liscie dowolnego
     * NADTYPU Integer. Odczyt jest mozliwy TYLKO jako Object (nieznana
     * dokladna gora hierarchii).
     */
    private static void addIntegers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
        // Integer first = list.get(0); // <-- TO NIE SKOMPILUJE SIE! get() zwraca Object,
        //                                     nie Integer - trzeba by jawnie rzutowac.
        Object first = list.get(0); // odczyt tylko jako Object - jedyna gwarancja
        System.out.println(" pierwszy element (jako Object) = " + first);
    }

    private static void demonstrateUnboundedWildcardRestrictions() {
        System.out.println("\n=== List<?> - CO WOLNO, A CZEGO NIE ===");

        List<String> strings = List.of("x", "y", "z");
        printAsObjects(strings);
        // addAnything(strings); // <-- gdyby taka metoda probowala list.add("cokolwiek"),
        //                             nie skompilowalaby sie dla List<?>.

        System.out.println("List<?>: mozna czytac jako Object, size(), isEmpty() - NIE mozna dodawac elementow.");
    }

    private static void printAsObjects(List<?> list) {
        for (Object o : list) { // jedyny bezpieczny typ odczytu dla List<?>
            System.out.println(" element (jako Object): " + o);
        }
    }

    private static void demonstrateWildcardVsTypeParameter() {
        System.out.println("\n=== ROZNICA: WILDCARD `?` A PARAMETR TYPU <T> ===");

        List<Integer> numbers = List.of(3, 1, 4, 1, 5);

        // Wersja z wildcard - typ elementu nie jest nigdzie indziej potrzebny,
        // metoda tylko CZYTA elementy jako Number.
        System.out.println("countElements(List<? extends Number>) = " + countElements(numbers));

        // Wersja z <T> - typ T "przeplywa" miedzy parametrem a wynikiem
        // (metoda zwraca DOKLADNIE ten sam typ, co w liscie).
        Integer first = firstElementTyped(numbers);
        System.out.println("firstElementTyped(List<T>) zwraca dokladnie T -> " + first);

        System.out.println("Wildcard: typ elementu 'nie wraca' do niczego innego w sygnaturze.");
        System.out.println("Parametr <T>: typ 'przeplywa' miedzy parametrem a wynikiem - musi byc TEN SAM typ.");
    }

    /**
     * Wildcard wystarcza - metoda tylko CZYTA elementy, nie musi "pamietac"
     * dokladnego typu ani go nigdzie zwracac.
     */
    private static int countElements(List<? extends Number> list) {
        return list.size();
    }

    /**
     * Tu potrzebny jest <T> (nie wildcard) - metoda ZWRACA DOKLADNIE ten sam
     * typ, jaki jest w liscie (T "przeplywa" z parametru do wyniku).
     * Z List<?> nie dalo by sie zadeklarowac typu zwracanego jako cos
     * konkretniejszego niz Object.
     */
    private static <T> T firstElementTyped(List<T> list) {
        return list.get(0);
    }
}
