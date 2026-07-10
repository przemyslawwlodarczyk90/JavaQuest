package com.example.javaquest._16_clean_code.Lesson03_Comments;

public class _Exercises_Lesson03_Comments {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IdentifyRedundantComment {
        /*
         * 🧪 Zadanie 1:
         * Dany jest fragment: `int count = 0; count = count + 1; // zwieksz count o 1`
         * W komentarzu wyjasnij, dlaczego ten komentarz jest redundantny i
         * przepisz linie bez niego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RemoveCommentedOutDeadCode {
        /*
         * 🧪 Zadanie 2:
         * Dany jest fragment:
         *   int price = 100;
         *   // int oldTax = price * 22 / 100; // stara stawka VAT
         *   int tax = price * 23 / 100;
         * Usun zakomentowana linie martwego kodu i wyjasnij w komentarzu,
         * gdzie POWINNA byc przechowywana historia takich zmian zamiast tego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteGoodTodoComment {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode `calculateShippingCost(double weight)`, ktora na
         * razie obsluguje tylko przesylki krajowe, z dobrym komentarzem TODO
         * (z odwolaniem do fikcyjnego numeru zadania) informujacym o braku
         * obslugi przesylek zagranicznych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteWhyCommentForNonObviousChoice {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode uzywajaca `Math.round(...)` zamiast rzutowania
         * `(int)` przy zaokraglaniu ceny - dodaj komentarz wyjasniajacy
         * DLACZEGO wybrano Math.round (a nie CO robi zaokraglenie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteJavadocForPublicMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz publiczna metode statyczna `discountedPrice(double price,
         * double discountRate)` z pelnym Javadoc (opis, @param x2, @return,
         * @throws dla nieprawidlowego discountRate spoza [0.0, 1.0]).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyLyingCommentInSnippet {
        /*
         * 🧪 Zadanie 6:
         * Dany jest fragment:
         *   // Zwraca posortowana liste malejaco
         *   List<Integer> sortAscending(List<Integer> nums) {
         *       return nums.stream().sorted().toList();
         *   }
         * W komentarzu wyjasnij, dlaczego komentarz KLAMIE (sortuje rosnaco,
         * nie malejaco) i napisz poprawiona wersje (albo kodu, albo komentarza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReplaceCommentWithBetterVariableName {
        /*
         * 🧪 Zadanie 7:
         * Dany jest fragment: `int x = 18; // x to minimalny wiek pelnoletnosci`.
         * Zastap zmienna `x` nazwa `minimumAdultAge`, tak by komentarz stal
         * sie zbedny, i usun go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteWarningCommentForThreadUnsafeMethod {
        /*
         * 🧪 Zadanie 8:
         * Napisz metode statyczna modyfikujaca wspoldzielone pole `static
         * int counter` (bez synchronizacji) z komentarzem ostrzegawczym
         * informujacym, ze metoda NIE jest bezpieczna watkowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListGoodVsBadCommentTypes {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wypisz w komentarzu 4 typy DOBRYCH komentarzy i 4
         * typy ZLYCH komentarzy z tej lekcji, kazdy z jednozdaniowym opisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyCommentsCanGoStale {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij w komentarzu (min. 3 zdania), dlaczego
         * komentarze "gnija" (staja sie nieaktualne) latwiej niz kod, i co
         * mozna zrobic, by to ryzyko zminimalizowac.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorCommentSectionsIntoMethods {
        /*
         * 🧪 Zadanie 11:
         * Dana jest metoda z komentarzami-sekcjami:
         *   // --- krok 1: walidacja ---
         *   ...
         *   // --- krok 2: obliczenia ---
         *   ...
         *   // --- krok 3: zapis wyniku ---
         *   ...
         * Napisz taka metode (dowolna prosta logike biznesowa) i
         * zrefaktoryzuj kazda sekcje do osobnej, nazwanej metody prywatnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteJavadocWithExceptionDocumentation {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode `parsePositiveInt(String text)` zwracajaca int, z
         * pelnym Javadoc dokumentujacym zarowno przypadek sukcesu, jak i
         * NumberFormatException oraz IllegalArgumentException (gdy liczba
         * <= 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FindAndFixStaleCommentAfterCodeChange {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode z komentarzem opisujacym STARE zachowanie (np.
         * "zwraca maks. 10 elementow"), po czym zmien logike (np. na maks.
         * 20 elementow) BEZ aktualizacji komentarza - zauwaz w komentarzu do
         * zadania, gdzie dokladnie powstala niezgodnosc, i napraw ja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DecideWhenCommentIsNecessary {
        /*
         * 🧪 Zadanie 14:
         * Dane sa 3 fragmenty kodu (bez komentarzy) - jeden oczywisty
         * (np. dodawanie dwoch liczb), jeden z nieoczywista decyzja
         * biznesowa (np. specyficzny algorytm zaokraglania podatku), jeden z
         * potencjalnie niebezpiecznym efektem ubocznym. Dla kazdego zdecyduj
         * (w komentarzu), czy potrzebuje komentarza, i jaki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteApiJavadocForWholeClass {
        /*
         * 🧪 Zadanie 15:
         * Napisz klase `PriceCalculator` z Javadoc klasy (opis roli klasy) i
         * min. 2 publicznymi metodami, kazda z wlasnym Javadoc - klasa ma
         * reprezentowac publiczne API modulu do obliczania cen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RewriteTodoWithProperTracking {
        /*
         * 🧪 Zadanie 16:
         * Dany jest zly komentarz `// TODO fix this later`. Przepisz go na
         * dobry TODO z konkretnym opisem, co dokladnie trzeba zrobic i
         * (fikcyjnym) odwolaniem do numeru zadania w trackerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareCommentDensityBeforeAfterRefactor {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode z 6 komentarzami "tlumaczacymi" zle nazwane zmienne,
         * a potem jej czysta wersje z lepszym nazewnictwem i JEDNYM
         * komentarzem "dlaczego" (jesli potrzebny) - policz i porownaj
         * liczbe komentarzy w obu wersjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteCommentExplainingWorkaround {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode z komentarzem wyjasniajacym obejscie (workaround)
         * hipotetycznego buga w starszej wersji biblioteki (np. "uzywamy
         * podwojnego wywolania flush(), bo w wersji X.Y biblioteki Z bufor
         * nie czyscil sie za pierwszym razem") - to typowy dobry komentarz "dlaczego".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentifyOverCommentedRealExample {
        /*
         * 🧪 Zadanie 19:
         * Przejrzyj dowolny wczesniejszy plik w tym kursie (np. w
         * `_01_fundamentals`) i znajdz miejsce, gdzie komentarz Twoim
         * zdaniem tylko powtarza kod - zacytuj go w komentarzu i zaproponuj
         * usuniecie/skrocenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteTeamCommentGuidelines {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz krotki (min. 6 punktow) przewodnik "kiedy
         * pisac komentarze w tym zespole", oparty na zasadach z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorHeavilyCommentedLegacyMethod {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode (min. 20 linii, dowolna logike np. przetwarzanie
         * zamowienia) z co najmniej 8 komentarzami roznego typu (dobrymi i
         * zlymi wymieszanymi) - a potem zrefaktoryzuj ja tak, by zostaly
         * TYLKO komentarze rzeczywiscie wartosciowe (maks. 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignCommentPolicyForPublicLibrary {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: zaprojektuj polityke komentowania dla PUBLICZNEJ
         * biblioteki (kod, ktorego uzytkownicy NIE widza implementacji) -
         * czym rozni sie ona od polityki dla wewnetrznego kodu aplikacji
         * (gdzie autor i czytelnik sa w tym samym zespole)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildStaleCommentDetectorHeuristic {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (opisz w komentarzu, zaimplementuj jesli mozliwe)
         * prosta heurystyke wykrywania "podejrzanych" komentarzy - np.
         * komentarz zawierajacy slowo "zwraca X", podczas gdy metoda
         * faktycznie zwraca inny typ/inna wartosc (uproszczone, tekstowe
         * dopasowanie na potrzeby cwiczenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DocumentComplexAlgorithmWithHighValueComments {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prosty algorytm (np. wlasna implementacje
         * wyszukiwania binarnego) i udokumentuj go MINIMALNA liczba
         * komentarzy najwyzszej wartosci (np. wyjasnienie niezmiennika petli
         * "dlaczego low <= high", a nie "co robi kazda linia").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RewriteJavadocAfterBehaviorChange {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode z Javadoc opisujacym zachowanie A (np. "rzuca
         * wyjatek gdy input jest null"), a nastepnie ZMIEN zachowanie na B
         * (np. "zwraca wartosc domyslna gdy input jest null") - zaktualizuj
         * Javadoc tak, by byl SPOJNY z nowa implementacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareCommentVsSelfDocumentingApproach {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj TA SAMA regule biznesowa (np. kwalifikacja klienta
         * VIP) dwa razy: raz jako dlugi warunek if z komentarzem
         * wyjasniajacym kazdy fragment, raz jako wywolanie dobrze nazwanej
         * metody `isVipCustomer(...)` bez zadnego komentarza - porownaj w
         * komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AuditRealClassForCommentQuality {
        /*
         * 🧪 Zadanie 27:
         * Wybierz dowolna klase z rozdzialu `_09_jdbc` lub `_10_dao` w tym
         * kursie i przeprowadz "audyt komentarzy" - dla kazdego znalezionego
         * komentarza zaklasyfikuj go (dobry/zly, i dlaczego) w komentarzu do
         * tego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_WriteMultiParagraphClassLevelJavadoc {
        /*
         * 🧪 Zadanie 28:
         * Napisz Javadoc klasy (min. 3 akapity: cel klasy, przyklad uzycia w
         * bloku {@code ...} lub tekstowym, uwagi o watkowosci) dla fikcyjnej
         * klasy `InMemoryOrderRepository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ArgueForOrAgainstCommentInGivenCase {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: dany jest sporny przypadek - zespol dyskutuje, czy
         * kazda prywatna metoda powinna miec komentarz opisujacy jej dzialanie.
         * Napisz argumentacje (min. 6 zdan) za lub przeciw, odwolujac sie do
         * zasad z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneCleanUpHeavilyCommentedModule {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz WLASNY, celowo "przekomentowany"
         * modul (min. 25 linii, dowolny temat biznesowy) z mieszanka
         * redundantnych, klamiacych i zastepujacych refaktoryzacje
         * komentarzy, a nastepnie oczysc go: popraw nazewnictwo, wydziel
         * metody, usun zbedne komentarze, zostaw TYLKO te naprawde
         * wartosciowe - obie wersje musza dawac identyczny wynik.
         */
        public static void main(String[] args) { }
    }
}
