package com.example.javaquest._13_libraries.Lesson09_GuavaImmutableCollections;

public class _Exercises_Lesson09_GuavaImmutableCollections {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateImmutableListOfFruits {
        /*
         * 🧪 Zadanie 1:
         * Utworz ImmutableList<String> zawierajaca owoce: "jablko", "banan",
         * "gruszka", "wisnia" przy pomocy ImmutableList.of(...) i wypisz ja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateImmutableSetRemovingDuplicates {
        /*
         * 🧪 Zadanie 2:
         * Utworz ImmutableSet<Integer> z wartosci: 5, 3, 5, 8, 3, 1, 8 - wypisz
         * zbior i sprawdz, ze duplikaty zostaly zredukowane (rozmiar = 4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateImmutableMapOfCapitals {
        /*
         * 🧪 Zadanie 3:
         * Utworz ImmutableMap<String, String> stolica panstwa->nazwa stolicy dla
         * co najmniej 4 par (np. "Polska"->"Warszawa") przez ImmutableMap.of(...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CatchUnsupportedOperationOnAdd {
        /*
         * 🧪 Zadanie 4:
         * Utworz ImmutableList<Integer> z 3 liczbami. Sprobuj wywolac na niej
         * .add(99) w bloku try-catch i wypisz nazwe zlapanego wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BuildImmutableListWithBuilder {
        /*
         * 🧪 Zadanie 5:
         * Uzyj ImmutableList.builder() zeby dodac po kolei liczby od 1 do 10,
         * a nastepnie zbuduj i wypisz gotowa ImmutableList.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConditionalBuilderEvenNumbersOnly {
        /*
         * 🧪 Zadanie 6:
         * Majac liste wejsciowa List.of(1,2,3,4,5,6,7,8,9,10), uzyj
         * ImmutableList.builder() w petli, aby dodac WYLACZNIE liczby parzyste -
         * pokazuje to warunkowe budowanie, ktorego .of(...) nie umozliwia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PartitionOrderList {
        /*
         * 🧪 Zadanie 7:
         * Majac liste 10 numerow zamowien (np. 1001..1010), uzyj
         * Lists.partition(...) aby podzielic ja na paczki po 4 - wypisz kazda
         * paczke z numerem porzadkowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UnionOfTwoSets {
        /*
         * 🧪 Zadanie 8:
         * Majac dwa zbiory Set<String> jezykiAli = {"Java","SQL"} i
         * jezykiBartka = {"Python","SQL","Go"}, uzyj Sets.union(...) i wypisz
         * wynikowy widok.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IntersectionOfTwoSets {
        /*
         * 🧪 Zadanie 9:
         * Na tych samych zbiorach co w Zadaniu 8 uzyj Sets.intersection(...) aby
         * znalezc jezyki wspolne dla obu osob.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DifferenceOfTwoSets {
        /*
         * 🧪 Zadanie 10:
         * Na tych samych zbiorach co w Zadaniu 8 uzyj Sets.difference(...) aby
         * znalezc jezyki, ktore zna TYLKO Ala (nie zna ich Bartek).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImmutableMapBuilderDuplicateKeyThrows {
        /*
         * 🧪 Zadanie 11:
         * Uzyj ImmutableMap.builder(), dodaj dwukrotnie ten sam klucz "produkt" z
         * roznymi wartosciami i wywolaj .buildOrThrow() w bloku try-catch - wypisz
         * zlapany wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareOrderJdkVsGuavaMap {
        /*
         * 🧪 Zadanie 12:
         * Utworz ImmutableMap.of("z",1,"a",2,"m",3) ORAZ Map.of("z",1,"a",2,"m",3)
         * z JDK - wypisz obie kolejnosci kluczy i skomentuj w println roznice
         * (deterministyczna vs niegwarantowana).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MutableMapSilentOverwritePitfall {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj zwykla HashMap<String,Integer> wstawiajac klucz "wynik" TRZY
         * razy z roznymi wartosciami w petli (symulujac blad w kodzie) - wypisz
         * koncowa wartosc i skomentuj, ze podmiana byla CICHA (bez ostrzezenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuilderWithConditionalBusinessLogic {
        /*
         * 🧪 Zadanie 14:
         * Majac liste 8 pracownikow (imie + wiek jako rekord/klasa pomocnicza),
         * uzyj ImmutableList.builder() aby zbudowac liste TYLKO pelnoletnich
         * pracownikow (wiek >= 18).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MapsDifferenceReport {
        /*
         * 🧪 Zadanie 15:
         * Majac dwie mapy cen produktow Map<String,Double> cenyStyczen i
         * cenyLuty (min. 5 produktow, niektore ceny takie same, niektore rozne),
         * uzyj Maps.difference(...) i wypisz osobno produkty o TAKIEJ SAMEJ cenie
         * oraz produkty, ktorych cena sie zmienila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MaterializeSetViewForReuse {
        /*
         * 🧪 Zadanie 16:
         * Uzyj Sets.union(...) na dwoch zbiorach 1000-elementowych (np. liczby
         * 0-999 i 500-1499), zmaterializuj wynik RAZ przez ImmutableSet.copyOf(...)
         * i wypisz jego rozmiar - skomentuj w println, dlaczego materializacja ma
         * sens przy WIELOKROTNYM uzyciu wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PartitionForBatchProcessingSimulation {
        /*
         * 🧪 Zadanie 17:
         * Majac liste 23 "rekordow do przetworzenia" (np. String "rekord-1"
         * .. "rekord-23"), podziel ja Lists.partition(...) na paczki po 5 i
         * zasymuluj przetwarzanie KAZDEJ paczki osobnym printlnem "przetwarzam
         * paczke X z Y elementami".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImmutableListReverseView {
        /*
         * 🧪 Zadanie 18:
         * Utworz ImmutableList<Integer> liczb 1-5 i wypisz jej odwrocona wersje
         * uzywajac metody .reverse() z ImmutableList.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombineTwoImmutableListsWithBuilder {
        /*
         * 🧪 Zadanie 19:
         * Majac dwie ImmutableList<String> (zespolA, zespolB), zbuduj JEDNA
         * ImmutableList zawierajaca wszystkich czlonkow OBU zespolow (bez
         * duplikatow imion) uzywajac ImmutableList.builder().addAll(...) oraz
         * ImmutableSet do usuniecia duplikatow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NullRejectionComparison {
        /*
         * 🧪 Zadanie 20:
         * Sprobuj utworzyc ImmutableList.of("a", null, "b") w bloku try-catch -
         * wypisz zlapany wyjatek i skomentuj w println, ze zachowanie jest
         * identyczne jak przy List.of() z JDK.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConfigurationBuilderFromFlags {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode buildFeatureFlags(boolean darkMode, boolean betaFeatures,
         * boolean debugMode) zwracajaca ImmutableSet<String> nazw wlaczonych flag
         * (np. "DARK_MODE"), budowana WARUNKOWO przez builder w zaleznosci od
         * argumentow - przetestuj dla 3 roznych kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DeduplicateAndSortWithImmutableBuilder {
        /*
         * 🧪 Zadanie 22:
         * Majac liste 20 losowych liczb calkowitych (z powtorzeniami, zakres
         * 1-10), zbuduj ImmutableSortedSet (import com.google.common.collect.
         * ImmutableSortedSet) zawierajacy unikalne wartosci w kolejnosci
         * rosnacej - skomentuj, ze JDK nie ma gotowej fabryki na taka strukture.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BatchApiCallsSimulation {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj wysylanie 50 "identyfikatorow" do zewnetrznego API, ktore
         * przyjmuje maksymalnie 10 na raz - uzyj Lists.partition(...) i dla
         * kazdej paczki wypisz symulowane wywolanie "API call: [lista]".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreeWaySetComparisonReport {
        /*
         * 🧪 Zadanie 24:
         * Majac 3 zbiory umiejetnosci trzech kandydatow, wygeneruj RAPORT
         * (println) pokazujacy: umiejetnosci wspolne dla WSZYSTKICH trzech
         * (zlozenie kilku Sets.intersection), oraz unikalne wylacznie dla
         * kazdego z osobna (zlozenie Sets.difference).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImmutableMapBuilderFromDynamicSource {
        /*
         * 🧪 Zadanie 25:
         * Majac "surowe dane" jako List<String> w formacie "klucz=wartosc" (np.
         * "PL=Polska", "DE=Niemcy" - min. 6 wpisow, BEZ duplikatow kluczy),
         * sparsuj je i zbuduj ImmutableMap<String,String> przez builder - obsluz
         * (try-catch) przypadek gdyby duplikat sie pojawil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PerformanceComparisonViewVsCopy {
        /*
         * 🧪 Zadanie 26:
         * Zmierz (System.nanoTime()) czas 100 000-krotnego odpytania rozmiaru
         * Sets.union(...) jako WIDOKU vs 100 000-krotnego odpytania rozmiaru
         * ImmutableSet skopiowanego RAZ przez ImmutableSet.copyOf(widok) -
         * wypisz oba czasy i wniosek w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MapsDifferenceForConfigDrift {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj "config drift" miedzy srodowiskiem TEST i PROD (dwie mapy
         * Map<String,String> z ustawieniami aplikacji, min. 8 kluczy, kilka
         * roznic) - uzyj Maps.difference(...) do wygenerowania czytelnego
         * raportu roznic dla zespolu DevOps.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImmutableListOfImmutableLists {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj "macierz" jako ImmutableList<ImmutableList<Integer>> 3x3 (kazdy
         * wiersz to osobna ImmutableList zbudowana przez builder w petli) i
         * wypisz ja czytelnie wiersz po wierszu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RefactorLegacyMutableCodeToImmutable {
        /*
         * 🧪 Zadanie 29:
         * Dany jest "legacy" fragment kodu budujacy liste i mape recznie
         * (ArrayList + HashMap, z mozliwoscia przypadkowej mutacji pozniej w
         * kodzie) - przepisz go na wersje z ImmutableList/ImmutableMap tak, aby
         * gwarantowac niezmiennosc od momentu utworzenia. Wypisz obie wersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullReportCombiningAllTools {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY raport analityczny laczacy WSZYSTKIE narzedzia z tej
         * lekcji: ImmutableMap.builder() (dane wejsciowe), Lists.partition
         * (podzial na paczki), Sets.union/intersection/difference (porownanie
         * dwoch grup danych) i Maps.difference (zmiany miedzy dwoma okresami) -
         * z czytelnymi printlnami na kazdym kroku.
         */
        public static void main(String[] args) { }
    }
}
