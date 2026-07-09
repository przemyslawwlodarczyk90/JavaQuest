package com.example.javaquest._14_advancedjava.Lesson07_GenericsBestPracticesAndPitfalls;

import java.util.ArrayList;
import java.util.List;

public class _Lesson07_GenericsBestPracticesAndPitfalls {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: DOBRE PRAKTYKI I PULAPKI GENERYKOW ===");

        /*
         * ============================================================
         * 📦 GDZIE JESTESMY - ZAMKNIECIE BLOKU GENERICS
         * ============================================================
         * - To jest OSTATNIA lekcja z bloku poswieconego generykom
         *   (Lekcje 1-7). Lekcje 1-2 nauczyly Cie SKLADNI (klasy i metody
         *   generyczne), Lekcja 3 - ograniczen (`extends`), Lekcja 4 -
         *   wildcardow, Lekcja 5 - reguly PECS, Lekcja 6 - jak generyki
         *   dzialaja "pod spodem" (type erasure) i jakie z tego wynikaja
         *   ograniczenia.
         * - Ta lekcja nie wprowadza nowej "mechaniki" - zbiera PRAKTYCZNE
         *   wnioski: czego unikac (raw types), kiedy ostrzezenie
         *   kompilatora mozna swiadomie wyciszyc, jak poprawnie uzywac
         *   `@SafeVarargs`, jak obejsc jedno konkretne ograniczenie
         *   wildcardow (capture) oraz kiedy generyki juz nie pomagaja, tylko
         *   przeszkadzaja.
         */
        System.out.println("\nLekcja 7: praktyka - raw types, unchecked warnings, @SafeVarargs, capture helper, kiedy NIE generyfikowac.");

        /*
         * ============================================================
         * 🔹 RAW TYPES - DLACZEGO NALEZY ICH UNIKAC
         * ============================================================
         * - "Raw type" (typ surowy) to uzycie klasy/interfejsu generycznego
         *   BEZ parametru typu - np. `List lista = new ArrayList();` zamiast
         *   `List<String> lista = new ArrayList<>();`.
         * - Raw type ISTNIEJE w Javie WYLACZNIE dla WSTECZNEJ KOMPATYBILNOSCI
         *   z kodem sprzed Javy 5 (2004) - nie jest to "skrocona" ani
         *   "wygodniejsza" forma generykow, tylko historyczny relikt.
         * - Konsekwencja: raw type CALKOWICIE WYLACZA sprawdzanie typow przez
         *   kompilator dla tej zmiennej. Mozna do niej dodac COKOLWIEK, a
         *   blad ujawni sie dopiero w RUNTIME, jako `ClassCastException`
         *   przy PROBIE ODCZYTU - i to nie w miejscu, gdzie zly element
         *   zostal dodany, tylko gdzies indziej, przy rzutowaniu.
         * - Rozroznij raw type od `List<?>` (wildcard nieograniczony,
         *   patrz Lekcja 4) - `List<?>` NADAL jest w pelni bezpieczny
         *   (kompilator wie, ze to "lista czegos", i pilnuje, zeby nic
         *   niewlasciwego do niej nie trafilo), tylko nie wie, czego
         *   konkretnie. Raw type nie pilnuje NICZEGO.
         */
        demonstrateRawTypeDanger();

        /*
         * ============================================================
         * 🔍 UNCHECKED WARNINGS - KIEDY @SuppressWarnings("unchecked") JEST NAPRAWDE BEZPIECZNY
         * ============================================================
         * - Ostrzezenie "unchecked" (`unchecked call`, `unchecked cast`,
         *   `unchecked conversion`) to komunikat kompilatora: "w tym
         *   miejscu NIE MOGE zagwarantowac bezpieczenstwa typow w runtime -
         *   ufam Ci na slowo". Pojawia sie typowo przy rzutowaniach
         *   zwiazanych z erasure (patrz Lekcja 6: `(T[]) new Object[n]`,
         *   `(T) obiekt` po odczycie z surowej struktury itp.).
         * - `@SuppressWarnings("unchecked")` NIE naprawia problemu - tylko
         *   go WYCISZA. Uzasadnione uzycie wymaga, zebys TY, jako
         *   programista, RECZNIE udowodnil sobie (i innym), ze rzutowanie
         *   jest bezpieczne, mimo ze kompilator nie potrafi tego sprawdzic.
         * - Trzy zasady bezpiecznego uzycia:
         *   1) NAJWEZSZY mozliwy zasieg - adnotacja na POJEDYNCZEJ zmiennej
         *      lokalnej (Java 5+) albo na calej metodzie TYLKO gdy nie da
         *      sie zawezic bardziej - NIGDY na calej klasie.
         *   2) KOMENTARZ tuz obok, wyjasniajacy DLACZEGO rzutowanie jest
         *      bezpieczne (jaki niezmiennik to gwarantuje).
         *   3) Rzutowanie musi byc rzeczywiscie bezpieczne - jesli nie
         *      jestes w stanie tego uzasadnic, `@SuppressWarnings` to nie
         *      rozwiazanie, tylko ukrywanie bledu.
         */
        demonstrateSuppressWarningsGoodPractice();

        /*
         * ============================================================
         * 🔹 @SafeVarargs - PELNE ZASADY UZYCIA (ROZWINIECIE LEKCJI 6)
         * ============================================================
         * - W Lekcji 6 poznales `@SafeVarargs` jako "obietnice" dana
         *   kompilatorowi w kontekscie heap pollution. Tutaj pelne, formalne
         *   zasady, KIEDY WOLNO jej uzyc:
         *
         *   ✅ `static` metody
         *   ✅ `final` metody instancyjne
         *   ✅ `private` metody instancyjne (dopiero od Javy 9)
         *   ✅ konstruktory (zawsze efektywnie "final" - nie mozna ich
         *      nadpisac)
         *
         *   ❌ zwykla, NIE-final, NIE-private metoda instancyjna - kompilator
         *      odrzuci `@SafeVarargs` komunikatem bledu, bo taka metoda MOZE
         *      zostac NADPISANA w podklasie w sposob, ktorego autor
         *      oryginalnej metody nie kontroluje - "obietnica" bezpieczenstwa
         *      przestalaby cokolwiek gwarantowac.
         * - Krotko: `@SafeVarargs` wolno uzyc TYLKO tam, gdzie metoda nie
         *   moze zmienic swojego zachowania "pod Toba" przez polimorfizm -
         *   czyli tam, gdzie Ty (autor) masz PELNA i OSTATECZNA kontrole nad
         *   cialem metody.
         */
        demonstrateSafeVarargsRules();

        /*
         * ============================================================
         * 🔍 PROBLEM WILDCARD CAPTURE
         * ============================================================
         * - "Wildcard capture" to sytuacja, gdy kompilator NIE POZWALA Ci
         *   zapisac do struktury typu `List<?>`, mimo ze logicznie
         *   operacja jest bezpieczna - bo `?` oznacza "jakis KONKRETNY, ale
         *   NIEZNANY typ", a kompilator nie moze udowodnic, ze wartosc,
         *   ktora chcesz wstawic, jest TYM SAMYM nieznanym typem.
         * - Klasyczny przyklad: metoda `void swap(List<?> lista, int i, int
         *   j)` NIE MOZE bezposrednio zrobic
         *   `lista.set(i, lista.get(j))` - kompilator widzi tylko, ze
         *   `get(j)` zwraca "jakies `capture#1 of ?`", a `set` oczekuje
         *   TEGO SAMEGO `capture#1 of ?` - i formalnie nie potrafi tego
         *   dopasowac, mimo ze operacja jest w 100% bezpieczna (bierzemy
         *   element Z TEJ SAMEJ listy i wstawiamy z powrotem DO NIEJ).
         * - ROZWIAZANIE: prywatna metoda pomocnicza generyczna z realnym
         *   parametrem typu `<T>`, ktora "przechwytuje" (captures) wildcard
         *   jako konkretny (choc nieznany dla wywolujacego) typ `T`. Metoda
         *   publiczna deleguje do niej wywolanie - to CALKOWICIE
         *   standardowy, powszechnie stosowany wzorzec (uzywany m.in. w
         *   kodzie samego JDK).
         */
        demonstrateWildcardCaptureHelper();

        /*
         * ============================================================
         * 🔹 NIE PRZESADZAJ Z GENERYKAMI - KIEDY LEPIEJ ICH NIE UZYWAC
         * ============================================================
         * - Generyki sa potezne, ale MAJA KOSZT - dodatkowa warstwa
         *   abstrakcji, dluzsze sygnatury, trudniejsze do zrozumienia
         *   komunikaty bledow (zwlaszcza przy zagniezdzonych wildcardach
         *   typu `Map<String, List<? extends Comparable<?>>>`).
         * - Typowe sygnaly "over-engineeringu" generykami:
         *   - klasa generyczna z JEDNYM parametrem typu, ktory jest uzywany
         *     w JEDNYM miejscu i nigdy nie bedzie inny niz konkretny typ -
         *     to sygnal, ze generyk nie jest tu potrzebny;
         *   - hierarchia klas generycznych z wieloma poziomami dziedziczenia
         *     i wieloma parametrami typu "na wszelki wypadek" - czesto
         *     LEPIEJ zastapic ja KOMPOZYCJA (mala klasa/interfejs
         *     realizujacy jedno zadanie, skladana z innymi) niz kolejnym
         *     poziomem generycznej hierarchii;
         *   - wlasny, "uniwersalny" kontener generyczny, podczas gdy
         *     biblioteka standardowa (`List`, `Map`, `Optional`...) juz w
         *     pelni pokrywa potrzebe.
         * - Zasada praktyczna: generyki dodawaj TAM, gdzie realnie
         *   eliminujesz rzutowania i wylapujesz bledy typow NA ETAPIE
         *   KOMPILACJI (patrz Lekcja 1) - a nie "bo tak wypada" albo "moze
         *   sie kiedys przyda".
         */
        demonstrateOverGenericsPitfall();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE LEKCJI 7 I CALEGO BLOKU GENERICS (LEKCJE 1-7)
         * ============================================================
         * - Raw types (`List` bez `<>`) istnieja TYLKO dla wstecznej
         *   kompatybilnosci - unikaj ich, bo wylaczaja CALE sprawdzanie
         *   typow przez kompilator. `List<?>` to bezpieczna alternatywa,
         *   gdy naprawde nie znasz/nie potrzebujesz typu elementow.
         * - `@SuppressWarnings("unchecked")` jest uzasadniony TYLKO gdy: ma
         *   NAJWEZSZY mozliwy zasieg, jest opatrzony komentarzem
         *   wyjasniajacym DLACZEGO rzutowanie jest bezpieczne, i to
         *   rzutowanie FAKTYCZNIE jest bezpieczne.
         * - `@SafeVarargs` wolno uzyc TYLKO na `static`, `final`, `private`
         *   (Java 9+) metodach oraz konstruktorach - bo tylko tam autor ma
         *   pelna kontrole nad cialem metody (brak ryzyka nadpisania).
         * - Problem wildcard capture (np. `swap` na `List<?>`) rozwiazuje
         *   sie prywatna metoda pomocnicza z realnym parametrem typu `<T>`,
         *   ktora "lapie" wildcard jako konkretny typ.
         * - Generyki dodawaj TAM, gdzie realnie eliminuja rzutowania i
         *   lapia bledy przy kompilacji - nie generyfikuj "na zapas";
         *   zlozone hierarchie generyczne czesto lepiej zastapic prosta
         *   kompozycja.
         *
         * - CALY BLOK GENERICS (Lekcje 1-7) w pigulce: Lekcja 1 - PO CO
         *   generyki (bezpieczenstwo typow, brak rzutowan); Lekcja 2 -
         *   SKLADNIA klas i metod generycznych; Lekcja 3 - ograniczenia
         *   `extends`; Lekcja 4 - wildcardy `? extends`/`? super`/`?`;
         *   Lekcja 5 - regula PECS (kiedy ktorego wildcarda uzyc); Lekcja 6
         *   - type erasure (jak to dziala "pod spodem" i jakie sa tego
         *   konsekwencje); Lekcja 7 (ta) - praktyka i pulapki. Razem dają
         *   pelny obraz jednego z najwazniejszych mechanizmow jezyka Java.
         * - Kolejny blok tematyczny (Lekcja 8 i dalej): INTERFEJSY
         *   FUNKCYJNE, LAMBDY I REFERENCJE DO METOD - jak Java 8
         *   wprowadzila programowanie funkcyjne do jezyka obiektowego,
         *   zaczynajac od dokladnej reguly SAM (Single Abstract Method) w
         *   Lekcji 8.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 (I CALEGO BLOKU GENERICS) ===");
    }

    @SuppressWarnings("unchecked")
    private static void demonstrateRawTypeDanger() {
        System.out.println("\n=== Raw types - dlaczego sa niebezpieczne ===");

        List surowa = new ArrayList(); // raw type - kompilator NIC nie sprawdza
        surowa.add("tekst");
        surowa.add(42); // kompiluje sie (z ostrzezeniem), mimo ze to "powinna byc" lista Stringow!

        System.out.println("Raw type pozwolil dodac String i Integer do 'tej samej' listy bez bledu kompilacji.");

        try {
            for (Object element : surowa) {
                String napis = (String) element; // BLAD dopiero TUTAJ, przy odczycie Integera jako String
                System.out.println("Element jako String: " + napis);
            }
        } catch (ClassCastException e) {
            System.out.println("ClassCastException przy ODCZYCIE - daleko od miejsca, gdzie powstal blad (surowa.add(42)).");
        }

        List<?> bezpiecznaAlternatywa = List.of("a", "b", "c");
        System.out.println("Kontrast: List<?> " + bezpiecznaAlternatywa + " - nadal bezpieczna, tylko typ elementow nieznany.");
    }

    // Metoda pomocnicza, ktora KONWERTUJE Object[] na List<T> - rzutowanie jest bezpieczne,
    // bo TA metoda gwarantuje (przez wlasna implementacje), ze wszystkie elementy tablicy
    // sa faktycznie typu T (zostaly do niej wczesniej dodane jako T przez t.add(...)).
    @SuppressWarnings("unchecked") // uzasadnienie: tablica pochodzi WYLACZNIE z ArrayList<T>.toArray(), wiec kazdy element to T
    private static <T> T pierwszyElement(List<T> lista) {
        Object[] tablica = lista.toArray();
        return (T) tablica[0]; // bezpieczne - patrz komentarz wyzej, zasieg zawezony do jednej linii
    }

    private static void demonstrateSuppressWarningsGoodPractice() {
        System.out.println("\n=== @SuppressWarnings(\"unchecked\") - waskie i uzasadnione uzycie ===");

        List<String> imiona = List.of("Ala", "Basia", "Celina");
        String pierwsze = pierwszyElement(imiona);

        System.out.println("Pierwszy element uzyskany przez waskie, uzasadnione rzutowanie: " + pierwsze);
        System.out.println("Zasada: NAJWEZSZY zasieg + komentarz uzasadniajacy + rzutowanie FAKTYCZNIE bezpieczne.");
    }

    @SafeVarargs
    private static <T> List<T> listOf(T... elementy) {
        List<T> wynik = new ArrayList<>();
        for (T element : elementy) {
            wynik.add(element);
        }
        return wynik;
    }

    private static void demonstrateSafeVarargsRules() {
        System.out.println("\n=== @SafeVarargs - pelne zasady (static/final/private/konstruktor) ===");

        List<String> lista = listOf("x", "y", "z");
        System.out.println("listOf(...) jest static -> @SafeVarargs dozwolone: " + lista);

        System.out.println("@SafeVarargs NIE zadziala na zwyklej, nadpisywalnej metodzie instancyjnej -");
        System.out.println("kompilator zglosi blad: 'Invalid SafeVarargs annotation. Note: ... is not final...'");
    }

    // Metoda publiczna - NIE MOZE bezposrednio zrobic lista.set(i, lista.get(j)) na List<?>.
    private static void swap(List<?> lista, int i, int j) {
        swapHelper(lista, i, j); // delegacja do prywatnej metody generycznej - to "lapie" wildcard jako T
    }

    // Prywatna metoda pomocnicza z REALNYM parametrem typu <T> - "przechwytuje" wildcard.
    private static <T> void swapHelper(List<T> lista, int i, int j) {
        T tymczasowy = lista.get(i);
        lista.set(i, lista.get(j)); // teraz kompilator WIE, ze oba elementy to ten sam typ T
        lista.set(j, tymczasowy);
    }

    private static void demonstrateWildcardCaptureHelper() {
        System.out.println("\n=== Wildcard capture - pomocnicza metoda generyczna jako obejscie ===");

        List<Integer> liczby = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        System.out.println("Przed swap: " + liczby);

        swap(liczby, 0, 4); // wywolanie przez List<?> - dziala dzieki swapHelper
        System.out.println("Po swap(0, 4) przez List<?> + prywatny helper <T>: " + liczby);
    }

    // PRZYKLAD OVER-ENGINEERINGU (celowo NIEUZYWANY dalej) - generyk bez realnej korzysci:
    // klasa opakowujaca DOKLADNIE jedna, zawsze konkretna wartosc String nie potrzebuje <T>.
    static class ZbedneOpakowanie<T> {
        private final T wartosc;

        ZbedneOpakowanie(T wartosc) {
            this.wartosc = wartosc;
        }

        T pobierz() {
            return wartosc;
        }
    }

    // PROSTSZA, LEPSZA ALTERNATYWA - kompozycja, bez generyka, bo typ jest i tak zawsze ten sam.
    static class KonfiguracjaAplikacji {
        private final String nazwa;

        KonfiguracjaAplikacji(String nazwa) {
            this.nazwa = nazwa;
        }

        String pobierzNazwe() {
            return nazwa;
        }
    }

    private static void demonstrateOverGenericsPitfall() {
        System.out.println("\n=== Kiedy NIE generyfikowac - przyklad over-engineeringu ===");

        ZbedneOpakowanie<String> zbedne = new ZbedneOpakowanie<>("wartosc");
        System.out.println("ZbedneOpakowanie<String> dziala, ale <T> tu NIC nie daje: " + zbedne.pobierz());

        KonfiguracjaAplikacji prostsza = new KonfiguracjaAplikacji("javaQuest");
        System.out.println("Prostsza, niegeneryczna alternatywa (kompozycja zamiast <T>): " + prostsza.pobierzNazwe());

        System.out.println("Zasada: generyk dodawaj tam, gdzie realnie eliminuje rzutowania - nie 'na zapas'.");
    }
}
