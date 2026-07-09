package com.example.javaquest._14_advancedjava.Lesson07_GenericsBestPracticesAndPitfalls;

public class _Exercises_Lesson07_GenericsBestPracticesAndPitfalls {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RawTypeAllowsMixedElements {
        /*
         * 🧪 Zadanie 1:
         * Utworz surowa (raw) liste `List surowa = new ArrayList();` i dodaj
         * do niej String "tekst" oraz Integer 42 - pokaz, ze kompiluje sie
         * to bez bledu (z ostrzezeniem). Wypisz zawartosc listy przez
         * System.out.println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RawTypeClassCastAtReadTime {
        /*
         * 🧪 Zadanie 2:
         * Uzywajac listy z Zadania 1, sprobuj w petli rzutowac kazdy element
         * na String w bloku try-catch - zlap ClassCastException przy
         * elemencie Integer i wypisz jego komunikat. Skomentuj, ze blad
         * ujawnil sie DALEKO od miejsca dodania zlego elementu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UnboundedWildcardIsSafeAlternative {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode `void wypiszRozmiar(List<?> lista)` i wywolaj ja dla
         * List<String> oraz List<Integer> - w komentarzu wyjasnij, czym
         * `List<?>` rozni sie od surowego typu `List` (nadal jest
         * bezpieczny, tylko typ elementow jest nieznany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SuppressWarningsNarrowScope {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode generyczna `<T> T pierwszy(List<T> lista)`
         * korzystajaca z `lista.toArray()` i rzutowania `(T)
         * tablica[0]` - umiesc @SuppressWarnings("unchecked") w
         * NAJWEZSZYM mozliwym zasiegu (na zmiennej lokalnej, nie na calej
         * metodzie) razem z komentarzem uzasadniajacym. Przetestuj na
         * List<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SafeVarargsOnStaticMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz statyczna, generyczna metode z varargs oznaczona
         * @SafeVarargs `<T> List<T> doListy(T... elementy)`, ktora zwraca
         * nowa ArrayList z przekazanymi elementami. Przetestuj dla 4
         * Integerow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SafeVarargsOnFinalMethod {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase z metoda instancyjna `final` i generyczna z varargs
         * oznaczona @SafeVarargs `<T> void wypisz(T... elementy)`
         * wypisujaca wszystkie elementy w petli. Przetestuj na instancji tej
         * klasy z 3 Stringami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SafeVarargsOnConstructor {
        /*
         * 🧪 Zadanie 7:
         * Napisz klase generyczna `Paczka<T>` z konstruktorem varargs
         * `Paczka(T... elementy)` oznaczonym @SafeVarargs, kopiujaca
         * elementy do wewnetrznej listy (metoda `List<T> zawartosc()`).
         * Przetestuj tworzac Paczke<String> z 3 elementami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NonFinalMethodCannotUseSafeVarargs {
        /*
         * 🧪 Zadanie 8:
         * W komentarzu zapisz klase z ZWYKLA (nie-static, nie-final,
         * nie-private) metoda generyczna z varargs oznaczona @SafeVarargs -
         * wypisz (println) dokladny komunikat bledu kompilacji, jaki by sie
         * pojawil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WildcardCaptureProblemDemo {
        /*
         * 🧪 Zadanie 9:
         * W komentarzu zapisz metode `void swap(List<?> lista, int i, int
         * j)`, ktora probuje bezposrednio zrobic
         * `lista.set(i, lista.get(j))` - wypisz (println), jaki blad
         * kompilacji by sie pojawil (niezgodnosc "capture#1 of ?").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WildcardCaptureHelperFix {
        /*
         * 🧪 Zadanie 10:
         * Napraw problem z Zadania 9: napisz publiczna metode `void
         * swap(List<?> lista, int i, int j)` delegujaca do prywatnej metody
         * pomocniczej generycznej `<T> void swapHelper(List<T> lista, int i,
         * int j)`. Przetestuj na List<Integer> {1, 2, 3, 4, 5}, zamieniajac
         * elementy o indeksach 0 i 4.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RawTypeInLegacyApiInterop {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `void dodajDoStarejListy(List surowa, Object
         * element)` symulujaca interakcje z "kodem sprzed generykow"
         * (raw type jako parametr). Wywolaj ja, przekazujac List<String>
         * jako `surowa` i dodajac String - w komentarzu wyjasnij, kiedy
         * (rzadko) raw type jest usprawiedliwiony (integracja ze starym API).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SuppressWarningsWithJustificationComment {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode generyczna `<T> T[] naTabliceUnsafe(List<T> lista,
         * T[] wzor)` uzywajaca `lista.toArray(wzor)` NIE wymagajaca
         * suppress (porownaj z wersja `(T[]) new Object[lista.size()]`,
         * ktora WYMAGA @SuppressWarnings z komentarzem). Zaimplementuj obie
         * wersje i porownaj w komentarzu, ktora jest bezpieczniejsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PrivateSafeVarargsJava9 {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase z PRYWATNA metoda generyczna z varargs oznaczona
         * @SafeVarargs (dozwolone od Javy 9) `<T> List<T> pomocnicza(T...
         * elementy)`, wywolywana z metody publicznej bez varargs. Przetestuj
         * dzialanie calosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReverseListWithWildcardHelper {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode `void odwroc(List<?> lista)` (parametr publiczny
         * jako wildcard), ktora deleguje do prywatnej `<T>
         * odwrocHelper(List<T> lista)` odwracajacej kolejnosc elementow "w
         * miejscu" (bez uzycia Collections.reverse). Przetestuj na
         * List<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FillWildcardListWithHelper {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode `void wypelnijPierwszymElementem(List<?> lista)`
         * (jesli lista niepusta, nadpisuje WSZYSTKIE pozostale elementy
         * kopia pierwszego), delegujaca do prywatnej metody generycznej `<T>
         * wypelnijHelper(List<T> lista)`. Przetestuj na List<Integer>
         * {9, 0, 0, 0}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_OverGenericSingleUseClassRefactor {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj klase generyczna `Opakowanie<T>` uzywana WYLACZNIE do
         * przechowywania String (nigdy niczego innego w tym kodzie). Napisz
         * DRUGA wersje bez generyka - `OpakowanieTekstu` (pole String). W
         * komentarzu wyjasnij, ktora wersja jest lepsza w tym konkretnym
         * przypadku i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompositionOverGenericHierarchy {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj (jako komentarz-szkic + prosty dzialajacy kod)
         * ALTERNATYWE dla hipotetycznej, rozbudowanej hierarchii generycznej
         * `AbstractProcessor<T, R> -> StringProcessor extends
         * AbstractProcessor<String, String> -> ...` - zamien ja na
         * KOMPOZYCJE: prosty interfejs `Processor<T, R> { R process(T t); }`
         * skladany z innymi obiektami zamiast dziedziczony. Zademonstruj
         * dzialajacy przyklad kompozycji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UncheckedWarningFromCollectionToArray {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode generyczna `<T> T[] kopiaJakoTablica(List<T> lista,
         * Class<T> typ)` korzystajaca z `Array.newInstance` (bez potrzeby
         * @SuppressWarnings na calej metodzie - tylko na finalnym
         * rzutowaniu). Przetestuj dla List<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SafeVarargsMisuseWarningExplanation {
        /*
         * 🧪 Zadanie 19:
         * W komentarzu opisz REALNY scenariusz, w ktorym brak @SafeVarargs
         * (lub jego niepoprawne uzycie na nadpisywalnej metodzie) moglby
         * doprowadzic do heap pollution przez podklase nadpisujaca metode w
         * nieoczekiwany sposob. Wypisz (println) wnioski jako podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RawTypeVsWildcardSideBySideComparison {
        /*
         * 🧪 Zadanie 20:
         * Napisz DWIE metody o tym samym celu (wypisanie wszystkich
         * elementow): `void wypiszRaw(List lista)` (raw type) oraz `void
         * wypiszWildcard(List<?> lista)`. Wywolaj obie na tej samej liscie i
         * w komentarzu wypisz co najmniej 2 konkretne roznice w
         * bezpieczenstwie typow miedzy nimi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericStackWithCaptureHelperPeek {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase `StosOgolny` operujaca na `Deque<?>` przekazywanym z
         * zewnatrz - metoda publiczna `Object podgladIZamien(Deque<?> stos,
         * Object nowyElement)` (zdejmuje wierzch, wstawia nowyElement,
         * zwraca zdjety element) MUSI delegowac do prywatnej metody
         * generycznej `<T> T podgladIZamienHelper(Deque<T> stos, T
         * nowyElement)`, zeby ominac capture. Przetestuj na Deque<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SuppressWarningsAuditTrail {
        /*
         * 🧪 Zadanie 22:
         * Zbierz w JEDNEJ klasie (jako statyczne metody z komentarzami) 3
         * PRZYKLADY uzasadnionego @SuppressWarnings("unchecked") z tej i
         * poprzedniej lekcji (tablica generyczna, odczyt z toArray,
         * konwersja przez Array.newInstance) - kazdy z krotkim komentarzem
         * "audytowym" wyjasniajacym gwarantowany niezmiennik. Wywolaj
         * wszystkie trzy i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SafeVarargsChainedHelperMethods {
        /*
         * 🧪 Zadanie 23:
         * Napisz DWIE statyczne metody generyczne z varargs oznaczone
         * @SafeVarargs: `<T> List<T> doListy(T... elementy)` oraz `<T>
         * List<T> polacz(List<T>... listy)` (varargs LIST generycznych -
         * lacznie z heap pollution wyjasnionym w Lekcji 6). Uzyj obu razem,
         * laczac 3 listy Stringow zbudowane przez doListy(...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_GenericBoundedHelperForWildcardCaptureWithComparable {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `void posortuj(List<?> lista)`, ktora deleguje do
         * prywatnej `<T extends Comparable<? super T>> void
         * posortujHelper(List<T> lista)` (Collections.sort wymaga
         * Comparable) - pokaz, ze wildcard capture MUSI wspolpracowac z
         * ograniczeniami typu jednoczesnie. Przetestuj na List<Integer>
         * nieposortowanej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorOverGenericRepositoryToComposition {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj (od zera, dzialajacy kod) prosty `MagazynProduktow`
         * (BEZ generyka, bo w tym systemie zawsze przechowuje tylko obiekty
         * `Produkt`) skladajacy sie z mniejszych, wspolpracujacych klas
         * (np. `Produkt`, `Cennik`, `MagazynProduktow` uzywajacy Cennika
         * przez kompozycje) - w komentarzu uzasadnij, dlaczego generyczny
         * `Magazyn<T>` NIE bylby tu lepszym rozwiazaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SafeGenericArrayFactoryWithNarrowSuppress {
        /*
         * 🧪 Zadanie 26:
         * Napisz klase narzedziowa `Tablice` z JEDNA metoda statyczna `<T>
         * T[] pusta(Class<T> typ, int rozmiar)` uzywajaca Array.newInstance
         * z @SuppressWarnings("unchecked") ZAWEZONYM do jednej linii i
         * komentarzem uzasadniajacym. Przetestuj tworzac tablice String[5]
         * i Integer[3].
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WildcardCaptureWithMultipleHelperLayers {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode `void przenumeruj(List<? extends List<?>> listaList)`
         * (lista list o nieznanych, mozliwe rozne, typach elementow), ktora
         * dla kazdej wewnetrznej listy woła prywatny generyczny helper
         * odwracajacy jej kolejnosc (reuzyj wzorca z Zadania 14/21).
         * Przetestuj na liscie zawierajacej List<String> i List<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignDecisionGenericVsSpecificClasses {
        /*
         * 🧪 Zadanie 28:
         * Napisz KROTKI raport (jako seria println) analizujacy 3
         * hipotetyczne klasy z projektu: (a) generyczny `Cache<K, V>` uzyty
         * w 5 roznych miejscach z roznymi typami - ZOSTAW generyczny; (b)
         * `Parser<T>` uzyty tylko raz dla String - ZAMIEN na
         * `ParserTekstu`; (c) `Repository<T, ID>` z 4 metodami uzywany dla
         * 3 encji - ZOSTAW generyczny. Uzasadnij kazda decyzje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullSafeVarargsAndCaptureCaseStudy {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj klase `NarzedziaList` laczaca WSZYSTKIE techniki z tej
         * lekcji: statyczna metoda z @SafeVarargs tworzaca liste, metoda
         * publiczna z `List<?>` delegujaca do prywatnego generycznego
         * helpera (capture) do zamiany dwoch elementow, oraz metode ze
         * scisle zawezonym @SuppressWarnings("unchecked") do konwersji na
         * tablice. Przetestuj wszystkie trzy razem w jednym scenariuszu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_GenericsBlockCapstoneReview {
        /*
         * 🧪 Zadanie 30:
         * Podsumuj CALY blok Generics (Lekcje 1-7) jednym, dzialajacym
         * mini-programem: zdefiniuj generyczna klase `Magazyn<T extends
         * Comparable<T>>` z metodami dodaj(T), `T najwiekszy()` (bounded
         * type, Lekcja 3), `void dodajWszystkie(List<? extends T> zrodlo)`
         * (PECS producer, Lekcja 5) oraz wewnetrznie uzyj tablicy `Object[]`
         * zamiast `T[]` (erasure workaround, Lekcja 6) z NAJWEZSZYM
         * @SuppressWarnings (Lekcja 7). Przetestuj na Magazyn<Integer> z co
         * najmniej 5 elementami dodanymi na oba sposoby.
         */
        public static void main(String[] args) { }
    }
}
