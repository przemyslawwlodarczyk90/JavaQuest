package com.example.javaquest._14_advancedjava.Lesson05_VarianceAndPecs;

public class _Exercises_Lesson05_VarianceAndPecs {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExtendsForProducer {
        /*
         * 🧪 Zadanie 1:
         * Napisz metode `double sumAll(List<? extends Number> lista)`, ktora
         * sumuje wszystkie elementy listy jako double. Przetestuj ja na
         * List<Integer> zawierajacej 1, 2, 3, 4, 5 (oczekiwany wynik: 15.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SuperForConsumer {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `void addNumbers(List<? super Integer> lista)`, ktora
         * dodaje do listy liczby 1, 2, 3. Przetestuj ja na pustej
         * ArrayList<Number> i na pustej ArrayList<Object> - wypisz obie listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyProducerOrConsumer {
        /*
         * 🧪 Zadanie 3:
         * Dla trzech opisanych metod: (a) kopiuje elementy Z listy do tablicy,
         * (b) wypelnia liste losowymi liczbami, (c) liczy srednia elementow
         * listy - w komentarzu przy kazdej okresl, czy lista jest producerem
         * czy konsumerem, i jakiego wildcarda by uzyl/a. Wypisz swoje
         * odpowiedzi przez System.out.println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InvarianceCompileError {
        /*
         * 🧪 Zadanie 4:
         * Utworz List<Integer> z wartosciami 1, 2, 3. Sprobuj (jako
         * ZAKOMENTOWANA linia kodu, z komentarzem wyjasniajacym) przypisac ja
         * do zmiennej typu List<Number> - wypisz w println, dlaczego to sie
         * nie kompiluje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WildcardFixForInvariance {
        /*
         * 🧪 Zadanie 5:
         * Majac List<Integer> z Zadania 4, przypisz ja tym razem do zmiennej
         * typu `List<? extends Number>` (to juz powinno zadzialac) i odczytaj
         * z niej pierwszy element jako Number - wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ArrayCovarianceCompiles {
        /*
         * 🧪 Zadanie 6:
         * Utworz tablice String[] z 3 elementami, przypisz ja do zmiennej
         * typu Object[] i wypisz jej zawartosc - pokaz, ze takie przypisanie
         * kompiluje sie bez problemu (kowariancja tablic).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ArrayStoreExceptionDemo {
        /*
         * 🧪 Zadanie 7:
         * Uzywajac tablicy z Zadania 6 (jako Object[]), sprobuj wstawic do
         * niej liczbe Integer w bloku try-catch - zlap i wypisz nazwe
         * wyjatku ArrayStoreException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CopyMethodBasic {
        /*
         * 🧪 Zadanie 8:
         * Napisz wlasna generyczna metode `<T> void copyAll(List<? super T>
         * dest, List<? extends T> src)`, ktora dodaje (add) kazdy element
         * src do dest. Przetestuj kopiujac List<Integer> {10,20,30} do
         * pustej List<Number>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UnboundedWildcardPrint {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode `void printSize(List<?> lista)`, ktora wypisuje
         * tylko rozmiar dowolnej listy (bez wzgledu na typ elementow).
         * Przetestuj ja na List<String> i List<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PecsDecisionTable {
        /*
         * 🧪 Zadanie 10:
         * Wypisz (System.out.println) tabelke "decyzyjna" PECS jako tekst:
         * co najmniej 3 przypadki uzycia (np. "tylko odczyt -> ? extends T",
         * "tylko zapis -> ? super T", "odczyt i zapis -> zwykly typ T") -
         * potraktuj to jako Twoja wlasna sciage.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MaxOfListWithExtends {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `<T extends Comparable<? super T>> T max(List<?
         * extends T> lista)`, ktora zwraca najwiekszy element listy.
         * Przetestuj ja na List<Integer> {3, 7, 2, 9, 4} (oczekiwany wynik: 9).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FillListWithSuper {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode `void fillWithZeros(List<? super Integer> lista, int
         * count)`, ktora dodaje `count` zer do listy. Przetestuj na
         * ArrayList<Number> i ArrayList<Object>, wypisz obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_StackPushPopPecs {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode `<T> void pushAll(Deque<T> stos, Collection<?
         * extends T> elementy)` dodajaca wszystkie elementy na stos (push) -
         * uzasadnij w komentarzu, dlaczego `elementy` to producer. Przetestuj
         * na Deque<Number> i Collection<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StackPopAllPecs {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode `<T> void popAll(Deque<T> stos, Collection<? super
         * T> cel)` przenoszaca wszystkie elementy ze stosu do kolekcji `cel`
         * (poprzez pop) - uzasadnij w komentarzu, dlaczego `cel` to consumer.
         * Przetestuj na Deque<Integer> i Collection<Number>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WhyNoWildcardBothReadWrite {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode `void swapFirstTwo(List<T> lista)` (zwykly typ T, BEZ
         * wildcarda), ktora zamienia miejscami pierwsze dwa elementy listy -
         * w komentarzu wyjasnij, dlaczego wildcard (? extends/? super) NIE
         * dziala dla tej operacji (czyta I pisze ten sam typ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GenericArrayCopyComparison {
        /*
         * 🧪 Zadanie 16:
         * Porownaj (2 przyklady kodu + komentarz): kopiowanie Integer[] do
         * Object[] (kowariancja tablic, dziala, ale ryzykowne) vs proba
         * przypisania List<Integer> do List<Object> (nie kompiluje sie w
         * ogole) - wypisz wnioski.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BoundedWildcardWithCustomHierarchy {
        /*
         * 🧪 Zadanie 17:
         * Majac hierarchie klas Zwierze -> Pies, Kot (proste klasy), napisz
         * metode `void nakarm(List<? extends Zwierze> zwierzeta)` wypisujaca
         * nazwe kazdego zwierzecia, oraz metode `void dodajDoSchroniska(List<?
         * super Pies> schronisko, Pies pies)` dodajaca psa - przetestuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MixedListsCopyExercise {
        /*
         * 🧪 Zadanie 18:
         * Uzywajac metody `copy` z teorii (Collections.copy-podobnej), skopiuj
         * List<Double> {1.1, 2.2, 3.3} do List<Number> oraz do List<Object> -
         * pokaz, ze obie dzialaja dzieki `? super T`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ArrayStoreExceptionInSortMethod {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode `void wstawNaKoniec(Object[] tablica, Object
         * element)` wstawiajaca element na ostatnia wolna (null) pozycje.
         * Wywolaj ja na String[] "przebranej" za Object[] z elementem typu
         * Integer w try-catch - zlap ArrayStoreException i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_JustifyEachWildcardChoice {
        /*
         * 🧪 Zadanie 20:
         * Majac 4 gotowe sygnatury metod (np. z java.util.Collections:
         * max/min/copy/addAll), dla kazdej wypisz w println, KTORY parametr
         * jest producerem a ktory konsumerem i dlaczego uzyto danego
         * wildcarda (mozesz sprawdzic sygnatury w dokumentacji/IDE).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericRepositoryMerge {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj metode `<T> void merge(List<? super T> baza, List<?
         * extends T> nowe)` symulujaca "merge" nowych rekordow do bazy
         * (dodawanie bez duplikatow wg equals). Przetestuj na przykladzie z
         * rekordami/klasami reprezentujacymi "Uzytkownik".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_EventBusPublishSubscribePecs {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj uproszczony "EventBus": metode `<T> void publish(List<?
         * super T> subscribers, T event)` symulujaca wyslanie eventu do
         * wszystkich subskrybentow (dodanie eventu do ich list) - uzasadnij
         * wybor wildcarda w komentarzu. Przetestuj na przykladowych zdarzeniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ReadOnlyApiWrapper {
        /*
         * 🧪 Zadanie 23:
         * Napisz klase `ReadOnlyView<T>` z polem `List<? extends T> dane` i
         * metoda `T get(int index)` (tylko odczyt, bez zadnej metody
         * modyfikujacej) - zademonstruj, ze API tej klasy fizycznie
         * uniemozliwia modyfikacje danych z zewnatrz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteOnlySink {
        /*
         * 🧪 Zadanie 24:
         * Napisz klase `WriteOnlySink<T>` z polem `List<? super T> cel` i
         * metoda `void accept(T element)` (tylko zapis) - zademonstruj jej
         * uzycie z List<Object> jako celem dla elementow typu String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareTwoImplementationsPerformance {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj dwie wersje kopiowania 100 000 elementow: (a) reczna
         * petla for z Collections.copy-podobna metoda uzywajaca PECS, (b)
         * uzycie zwyklego List<T> bez wildcardow (ten sam typ po obu
         * stronach) - zmierz czas (System.nanoTime()) i wypisz, ze wydajnosc
         * jest identyczna (PECS to kwestia BEZPIECZENSTWA TYPOW, nie wydajnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_GenericArrayStoreExceptionInSortedInsert {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode `void wstawPosortowane(Number[] tablica, int index,
         * Number wartosc)`. Utworz Integer[] "przebrana" za Number[], w
         * try-catch sprobuj wstawic Double na jedna z pozycji - zlap
         * ArrayStoreException i wyjasnij w komentarzu dlaczego generyczny
         * odpowiednik (List<Integer>) nie pozwolilby na taki blad JUZ NA
         * ETAPIE KOMPILACJI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiSourceAggregator {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode `<T> List<T> aggregate(List<? extends T>... zrodla)`
         * (varargs producerow) laczaca elementy z wielu list w jedna nowa
         * ArrayList<T>. Przetestuj na 3 zrodlach roznych podtypow Number
         * (Integer, Long, Double).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BoundedTypeParameterWithPecs {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `<T extends Comparable<? super T>> void sortAndCopy(
         * List<? super T> dest, List<? extends T> src)`, ktora kopiuje
         * POSORTOWANE elementy z src do dest (nie modyfikujac src). Przetestuj
         * na List<Integer> nieposortowanej jako zrodle i List<Number> jako celu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignApiFromScratch {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj od zera mini-API "Kolejka priorytetowa zadan":
         * `<T> void enqueueAll(Queue<? super T> kolejka, Collection<? extends
         * T> zadania)` - napisz pelna implementacje wraz z testem na
         * przykladowych zadaniach (np. String jako opis zadania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullPecsCaseStudy {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne studium przypadku "system rekomendacji produktow":
         * metoda producenta `List<? extends Produkt> pobierzRekomendacje()`,
         * metoda konsumenta `void zapiszDoKoszyka(List<? super Produkt>
         * koszyk, List<? extends Produkt> rekomendacje)` oraz metoda laczaca
         * oba typy operacji `<T extends Produkt> void przetworz(List<? super
         * T> wyjscie, List<? extends T> wejscie)` - zademonstruj dzialanie
         * calosci na przykladowej hierarchii Produkt/Ksiazka/Elektronika.
         */
        public static void main(String[] args) { }
    }
}
