package com.example.javaquest._14_advancedjava.Lesson05_VarianceAndPecs;

import java.util.ArrayList;
import java.util.List;

public class _Lesson05_VarianceAndPecs {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: WARIANCJA I PECS (PRODUCER EXTENDS, CONSUMER SUPER) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 4 - PO CO W OGOLE WILDCARDY
         * ============================================================
         * - W Lekcji 4 poznales trzy postacie wildcardow generycznych:
         *   `? extends T` (gorne ograniczenie), `? super T` (dolne
         *   ograniczenie) i `?` (nieograniczony).
         * - Wiesz juz, ZE `List<? extends Number>` pozwala CZYTAC z listy
         *   jako Number, a `List<? super Integer>` pozwala DOPISYWAC do
         *   niej wartosci typu Integer.
         * - Tej lekcji brakuje jednej rzeczy: PROSTEJ REGULY, ktora mowi
         *   Ci od razu, KTOREGO wildcarda uzyc w danej sytuacji, bez
         *   zgadywania i bez kompilowania "na sprobe". Ta regula to
         *   PECS - i jest tematem calej tej lekcji.
         */
        System.out.println("\nLekcja 4: wildcardy (? extends / ? super / ?). Lekcja 5: KIEDY ktorego uzyc - PECS.");

        /*
         * ============================================================
         * 🔹 PECS: PRODUCER EXTENDS, CONSUMER SUPER
         * ============================================================
         * - PECS to mnemonik ukuty przez Joshua Blocha (autor "Effective
         *   Java"), ktory w jednym zdaniu rozstrzyga, ktorego wildcarda
         *   uzyc:
         *
         *     Jesli struktura danych tylko PRODUKUJE (daje Ci) wartosci
         *     typu T -> uzyj `? extends T`.
         *
         *     Jesli struktura danych tylko KONSUMUJE (przyjmuje od
         *     Ciebie) wartosci typu T -> uzyj `? super T`.
         *
         * - "Producer" = mysl o tym jak o ZRODLE danych - Ty z niego
         *   CZYTASZ (np. `T element = lista.get(i)`). Skoro tylko
         *   czytasz, kompilator moze pozwolic Ci na liste "jakiegos
         *   podtypu T", bo kazdy podtyp T DA SIE bezpiecznie potraktowac
         *   jako T.
         * - "Consumer" = mysl o tym jak o UJSCIU danych - Ty do niego
         *   PISZESZ (np. `lista.add(element)`). Skoro tylko piszesz,
         *   kompilator moze pozwolic Ci na liste "jakiegos nadtypu T",
         *   bo skoro ten nadtyp umie pomiescic T, to Twoj element typu T
         *   bezpiecznie tam trafi.
         * - Jesli struktura ROBI OBIE RZECZY na raz (i czyta, i pisze
         *   TEN SAM typ), wildcard w ogole nie pomoze - potrzebujesz
         *   zwyklego, dokladnego typu `List<T>` (patrz przyklad `sum`
         *   z Lekcji 3, gdzie i tak tylko sie czyta - stad tam
         *   wystarczylo `? extends Number`).
         */
        System.out.println("\nPECS: Producer -> ? extends T (czytasz). Consumer -> ? super T (piszesz).");

        /*
         * ============================================================
         * 🔍 PRZYKLAD Z JDK: Collections.copy - PECS W PRAKTYCE
         * ============================================================
         * - Idealnym przykladem PECS jest sygnatura metody
         *   `java.util.Collections.copy(List<? super T> dest,
         *   List<? extends T> src)`.
         * - `src` (zrodlo) jest tylko CZYTANE - kazdy jej element
         *   zostanie skopiowany do `dest`. `src` jest wiec PRODUCEREM ->
         *   `? extends T`.
         * - `dest` (cel) jest tylko ZAPISYWANY (elementy sa do niej
         *   dodawane/nadpisywane) - `dest` jest wiec CONSUMEREM ->
         *   `? super T`.
         * - Ponizej wlasna, uproszczona wersja tej samej metody -
         *   dokladnie ten sam wzorzec.
         */
        demonstrateCopyPattern();

        /*
         * ============================================================
         * 🔹 GENERYCZNA NIEZMIENNOSC (INVARIANCE) - List<Integer> TO NIE List<Number>
         * ============================================================
         * - Generyki w Javie sa DOMYSLNIE NIEZMIENNE (invariant): mimo
         *   ze Integer JEST podtypem Number, `List<Integer>` NIE JEST
         *   podtypem `List<Number>`. To dwa CALKOWICIE ODREBNE typy z
         *   punktu widzenia kompilatora - przypisanie
         *   `List<Number> n = new ArrayList<Integer>();` jest bledem
         *   kompilacji.
         * - To wlasnie dlatego wildcardy w ogole istnieja - sa jedynym
         *   sposobem, zeby "otworzyc" ten sztywny, niezmienny system
         *   typow na konkretne, bezpieczne przypadki uzycia (tylko
         *   czytanie -> extends, tylko pisanie -> super).
         */
        demonstrateInvariance();

        /*
         * ============================================================
         * 🔍 KONTRAST: TABLICE SA KOWARIANTNE (COVARIANT) - I TO JEST NIEBEZPIECZNE
         * ============================================================
         * - W przeciwienstwie do generykow, TABLICE w Javie sa
         *   KOWARIANTNE: skoro Integer jest podtypem Object, to
         *   `Integer[]` JEST podtypem `Object[]` - takie przypisanie
         *   kompiluje sie bez problemu.
         * - Problem: ta wygoda przy kompilacji jest OKUPIONA ryzykiem w
         *   RUNTIME. Tablica "pamieta" swoj prawdziwy typ elementow, a
         *   JVM sprawdza go przy KAZDYM zapisie do tablicy - jesli
         *   sprobujesz wstawic element niezgodnego typu (np. String do
         *   tablicy, ktora w rzeczywistosci jest `Integer[]`, tylko
         *   "widziana" jako `Object[]`), dostaniesz WYJATEK W TRAKCIE
         *   DZIALANIA programu: `ArrayStoreException`.
         * - Generyki CELOWO zrezygnowaly z takiej kowariancji "domyslnie
         *   wszedzie" - sa niezmienne, a bezpieczna, KONTROLOWANA
         *   "kowariancje" (i "kontrawariancje") dostajesz TYLKO tam,
         *   gdzie jawnie o nia poprosisz przez wildcard. Dzieki temu caly
         *   ta klasa bledow jest wylapywana przez KOMPILATOR, a nie przez
         *   wyjatek w produkcji.
         */
        demonstrateArrayCovarianceDanger();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PECS (Producer Extends, Consumer Super) to prosta regula na
         *   wybor wildcarda: strukturz, ktora tylko DAJE Ci wartosci T ->
         *   `? extends T`; struktura, ktora tylko PRZYJMUJE od Ciebie
         *   wartosci T -> `? super T`.
         * - Klasyczny przyklad z JDK: `Collections.copy(List<? super T>
         *   dest, List<? extends T> src)` - src produkuje, dest
         *   konsumuje.
         * - Generyki sa DOMYSLNIE NIEZMIENNE - `List<Integer>` to NIE
         *   `List<Number>`, mimo ze Integer jest podtypem Number.
         *   Wildcardy to JEDYNY, kontrolowany sposob na wprowadzenie
         *   elastycznosci do tego systemu typow.
         * - Tablice sa natomiast KOWARIANTNE (`Integer[]` JEST
         *   `Object[]`) - ta wygoda kosztuje mozliwoscia
         *   `ArrayStoreException` w trakcie dzialania programu. Generyki
         *   unikaja calej tej klasy bledow, przenoszac kontrole na etap
         *   kompilacji.
         * - Kolejna lekcja: TYPE ERASURE - jak kompilator "pod spodem"
         *   implementuje generyki i dlaczego w runtime `List<String>` i
         *   `List<Integer>` to tak naprawde ten sam typ.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    // Wlasna, uproszczona wersja Collections.copy - podrecznikowy przyklad PECS.
    private static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i = 0; i < src.size(); i++) {
            dest.set(i, src.get(i));
        }
    }

    private static void demonstrateCopyPattern() {
        System.out.println("\n=== Collections.copy - wlasna implementacja z PECS ===");

        List<Integer> zrodlo = List.of(1, 2, 3, 4, 5);
        // Cel musi umiescic Integer -> moze byc lista Integer ALBO dowolnego jego nadtypu (Number, Object...).
        List<Number> cel = new ArrayList<>(List.of(0, 0, 0, 0, 0));

        copy(cel, zrodlo);
        System.out.println("Zrodlo (producer, ? extends Integer): " + zrodlo);
        System.out.println("Cel po kopiowaniu (consumer, ? super Integer): " + cel);

        // Cel jako List<Object> - Object jest nadtypem Integer, wiec tez dziala (consumer).
        List<Object> celObiekt = new ArrayList<>(List.of("x", "y", "z", "w", "v"));
        copy(celObiekt, zrodlo);
        System.out.println("Cel typu List<Object> tez zadziala jako consumer: " + celObiekt);
    }

    private static void demonstrateInvariance() {
        System.out.println("\n=== Niezmiennosc generykow (invariance) ===");

        List<Integer> liczbyCalkowite = new ArrayList<>(List.of(1, 2, 3));

        // List<Number> liczby = liczbyCalkowite; // BLAD KOMPILACJI:
        // incompatible types: List<Integer> cannot be converted to List<Number>

        // Jedyny bezpieczny sposob "podniesienia" typu do czytania - wildcard.
        List<? extends Number> liczbyDoOdczytu = liczbyCalkowite;
        Number pierwsza = liczbyDoOdczytu.get(0); // czytanie OK
        System.out.println("Odczyt przez ? extends Number: " + pierwsza);
        // liczbyDoOdczytu.add(4); // BLAD KOMPILACJI - kompilator nie wie, czy to naprawde List<Integer>

        System.out.println("List<Integer> NIE JEST List<Number> - generyki sa niezmienne (invariant).");
    }

    private static void demonstrateArrayCovarianceDanger() {
        System.out.println("\n=== Kowariancja tablic - ArrayStoreException w runtime ===");

        Integer[] liczby = {1, 2, 3};
        Object[] jakoObiekty = liczby; // OK przy kompilacji - Integer[] JEST podtypem Object[]

        System.out.println("Integer[] przypisana do Object[] - kompiluje sie bez problemu (kowariancja).");

        try {
            jakoObiekty[0] = "to nie jest liczba"; // kompiluje sie, ale...
        } catch (ArrayStoreException e) {
            System.out.println("Proba wstawienia String do tablicy Integer[] -> ArrayStoreException w RUNTIME.");
        }

        System.out.println("Generyki nie maja tego problemu - niezmiennosc + wildcardy przenosza kontrole na kompilacje.");
    }
}
