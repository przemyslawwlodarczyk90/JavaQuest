package com.example.javaquest._14_advancedjava.Lesson29_AdvancedLanguageBestPractices;

public class _Exercises_Lesson29_AdvancedLanguageBestPractices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RefactorRawListToGeneric {
        /*
         * 🧪 Zadanie 1:
         * Masz kod: `List lista = new ArrayList(); lista.add("Ala"); lista.add(10);`
         * Przepisz go na bezpieczna, generyczna wersje `List<String>` - w komentarzu
         * napisz, KTORA linia oryginalnego kodu przestalaby sie kompilowac i dlaczego
         * to DOBRZE (Lekcje 1-7).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_IdentifyClassCastExceptionSource {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `wypiszWszystkie(List lista)` uzywajaca RAW TYPE, ktora rzuca
         * ClassCastException, gdy w liscie znajdzie sie element inny niz String.
         * Zlap wyjatek i wypisz komunikat wskazujacy DOKLADNIE, w ktorym miejscu
         * generics zapobiegloby temu bledowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_LambdaForShortStatelessSort {
        /*
         * 🧪 Zadanie 3:
         * Masz liste 8 imion. Posortuj ja lambda-comparatorem wg dlugosci imienia,
         * a w razie remisu (te sama dlugosc) alfabetycznie - `thenComparing`
         * (Lekcje 8-11). Uzasadnij w komentarzu, dlaczego to wlasnie przypadek "na
         * lambde", nie na osobna klase.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ClassInsteadOfLambdaForState {
        /*
         * 🧪 Zadanie 4:
         * Napisz klase `ThrottledAction` implementujaca `Runnable`, ktora wykonuje
         * przekazana akcje TYLKO co N-te wywolanie (np. co 3. wywolanie), zliczajac
         * wywolania w polu instancji. Wyjasnij w komentarzu, dlaczego TU lambda by
         * nie wystarczyla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReflectionForSimpleFrameworkTool {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode `int policzPolaPubliczne(Object obiekt)` uzywajaca klasycznej
         * refleksji (getClass().getDeclaredFields()), ktora zlicza pola PUBLICZNE
         * dowolnego obiektu - zademonstruj na 2 roznych klasach (Lekcje 15-18).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ClosedSetSealedSwitch {
        /*
         * 🧪 Zadanie 6:
         * Zamodeluj `sealed interface DzienTygodniaTypu permits DzienRoboczy,
         * DzienWeekendowy` (Lekcje 19-22) i napisz exhaustywny switch zwracajacy
         * nazwe typu dnia - BEZ 'default'. Sprawdz na 5 przykladowych dniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_OpenHierarchyForPlugins {
        /*
         * 🧪 Zadanie 7:
         * Zamodeluj OTWARTY (nie-sealed) interfejs `RaportExporter` z metoda
         * `export(String dane)`. Zaimplementuj 2 eksportery (np. CsvExporter,
         * JsonExporter) - w komentarzu wyjasnij, dlaczego TU sealed byloby ZLYM
         * wyborem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImmutableValueObject {
        /*
         * 🧪 Zadanie 8:
         * Przepisz mutowalna klase `Punkt3D` (pola x, y, z z setterami) na niezmienny
         * `record Punkt3D` z metoda `przesun(double dx, double dy, double dz)`
         * zwracajaca NOWY obiekt (Lekcje 24-25). Zademonstruj oba na tym samym
         * scenariuszu aliasingu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_VarWhenObviousVsNot {
        /*
         * 🧪 Zadanie 9:
         * Wypisz (w komentarzu) 5 linii kodu, gdzie `var` POPRAWIA czytelnosc, i 5
         * linii, gdzie `var` ja POGARSZA (bo typ nie jest oczywisty) - uzasadnij
         * kazda linie jednym zdaniem (Lekcja 23).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SpotTheAntiPatternInSnippet {
        /*
         * 🧪 Zadanie 10:
         * Dostajesz (w komentarzu) 5 krotkich fragmentow kodu, kazdy z INNYM
         * antywzorcem z tabeli w lekcji (raw type, naduzycie refleksji, mutowalny
         * stan wspoldzielony, nadmiar wildcardow, 'lykajacy' default). Dla kazdego
         * napisz, KTORY to antywzorzec i jak go naprawic.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenericVsObjectBasedCache {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `class ProstyCache<K, V>` (mapa + metody get/put) generyczna
         * ORAZ - dla porownania w komentarzu - jej wersje z Object zamiast V. Pokaz
         * na przykladzie, ile jawnych rzutowan wymaga wersja z Object.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FunctionalInterfaceForPluggableValidation {
        /*
         * 🧪 Zadanie 12:
         * Zaprojektuj metode `boolean waliduj(String haslo, Predicate<String>...
         * reguly)` (Lekcja 11) przyjmujaca DOWOLNA liczbe regul jako lambdy (np.
         * dlugosc min. 8, zawiera cyfre) - przetestuj na 3 haslach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ReflectionVsMethodHandleMicroBenchmark {
        /*
         * 🧪 Zadanie 13:
         * Na wzor demo z lekcji: zmierz czas 100 000 wywolan metody bezargumentowej
         * przez klasyczna refleksje (Method.invoke) i przez MethodHandle - wypisz
         * oba czasy i wniosek w komentarzu (Lekcje 15-18).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SealedHierarchyExhaustivenessBreaks {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj sealed hierarchie z 2 podtypami + exhaustywny switch. Dopisz
         * TRZECI podtyp do 'permits' i - BEZ zmiany switcha - zaobserwuj (w
         * komentarzu) dokladny blad kompilacji. Napraw switch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_VisitorPatternForOpenHierarchy {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj klasyczny wzorzec Visitor dla OTWARTEJ hierarchii `Ksztalt`
         * (Kolo, Prostokat) - z interfejsem `KsztaltVisitor<T>` i metoda `accept`.
         * Porownaj w komentarzu z podejsciem sealed+switch z Lekcji 21-22.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DeepImmutabilityWithDefensiveCopying {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj niezmienna klase `Zamowienie` przechowujaca `List<String>
         * pozycje` - z OBRONNA kopia w konstruktorze i w getterze (Lesson25).
         * Zademonstruj, ze modyfikacja listy PRZEKAZANEJ do konstruktora PO
         * utworzeniu obiektu nie zmienia stanu `Zamowienie`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WildcardOveruseVsPecs {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode `kopiuj(List<? extends Number> zrodlo, List<? super Number>
         * cel)` (PECS, Lekcja 5) i - dla kontrastu w komentarzu - jej "przewildcardowana"
         * wersje z `List<?>` w obu miejscach, ktora NIE dziala. Wyjasnij dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SwallowingDefaultPitfall {
        /*
         * 🧪 Zadanie 18:
         * Napisz sealed hierarchie 3 typow + switch z 'default' RZUCAJACYM wyjatek
         * (zamiast go lykac cicho). Dopisz 4. podtyp - pokaz w komentarzu, ze
         * program teraz KOMPILUJE sie, ale wybucha w RUNTIME na nowym przypadku -
         * i porownaj z bezpieczniejszym switchem bez default z Zadania 14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ChooseRightToolForFiveScenarios {
        /*
         * 🧪 Zadanie 19:
         * Dla 5 opisanych (w komentarzu) scenariuszy - np. "serializacja dowolnego
         * obiektu do JSON w bibliotece ogolnego przeznaczenia", "walidacja jednego
         * pola formularza", "system pluginow dla zewnetrznych dostawcow" - wskaz
         * WLASCIWE narzedzie z rozdzialu i uzasadnij wybor jednym zdaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorLegacyClassToModernIdioms {
        /*
         * 🧪 Zadanie 20:
         * Dostajesz (w komentarzu) "przedwerdzenowy" fragment kodu: mutowalna klasa
         * `Pracownik` z raw-type'owa lista podwladnych, anonimowa klasa Comparator i
         * if-else zamiast switcha. Przepisz go na: record/niezmienny obiekt, lambde,
         * generyczna liste i switch expression (Lekcje 8-25).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildDecisionHelperUtility {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj i zaimplementuj klase `PraktykiDoradca` z metoda statyczna
         * `String zarekomenduj(String scenariusz)` zwracajaca rekomendacje (jako
         * String) na podstawie SLOW KLUCZOWYCH w opisie scenariusza (np. "wtyczka" ->
         * "polimorfizm/SPI", "znany z gory zbior" -> "sealed"). Przetestuj na 6
         * roznych opisach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PerformanceComparisonAcrossThreeApproaches {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj TA SAMA operacje (np. "utworz obiekt i wywolaj metode
         * bezargumentowa" 500 000 razy) na 3 sposoby: bezposrednio (`new`+wywolanie),
         * klasyczna refleksja, MethodHandle. Zmierz i wypisz PELNY raport czasow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SealedVsPolymorphismSameDomainBothWays {
        /*
         * 🧪 Zadanie 23:
         * Zamodeluj TA SAMA domene (np. "typ powiadomienia") DWA razy: raz jako
         * sealed+switch (zamknieta), raz jako klasyczny interfejs+polimorfizm
         * (otwarta). Napisz w komentarzu min. 4 zdania porownujace, KIEDY kazde z
         * podejsc bylobo lepszym wyborem w REALNYM projekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreadSafetyThroughImmutabilityDemo {
        /*
         * 🧪 Zadanie 24:
         * Uruchom 10 watkow (jak w _05_multithreading) rownolegle odczytujacych i
         * "modyfikujacych" (przez tworzenie nowych obiektow) WSPOLDZIELONY,
         * niezmienny rekord konfiguracji. Zweryfikuj brak race condition BEZ
         * jakiejkolwiek synchronizacji - wyjasnij dlaczego to dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CustomAnnotationDrivenValidationFramework {
        /*
         * 🧪 Zadanie 25:
         * Polacz adnotacje (Lekcje 12-14) i refleksje (Lekcje 15-18): zaprojektuj
         * wlasna adnotacje `@Wymagane` na polach oraz metode
         * `List<String> waliduj(Object obiekt)` skanujaca refleksja pola oznaczone
         * `@Wymagane` i zwracajaca liste bledow dla wartosci null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MigrateRawTypeLegacyApiToGenerics {
        /*
         * 🧪 Zadanie 26:
         * Dostajesz (w komentarzu) "stare" API z 2005 roku uzywajace WYLACZNIE raw
         * types (`List`, `Map`) w sygnaturach 4 metod. Zaprojektuj NOWA, generyczna
         * wersje API - opisz w komentarzu, jakie zmiany bylyby "binary compatible", a
         * jakie NIE (dla istniejacych klientow API).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExhaustivenessAsRefactoringSafetyNet {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj sealed hierarchie 4 typow z 3 RÓŻNYMI exhaustywnymi switchami
         * (operujacymi na tej samej hierarchii, w 3 roznych metodach). Dodaj PIATY
         * podtyp - wypisz w komentarzu, w KTORYCH dokladnie miejscach kompilator
         * zglasza teraz blad (siatka bezpieczenstwa refaktoryzacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CodeReviewChecklistForChapter {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz "checklist" code review (min. 8 punktow, w
         * komentarzu) opartej WYLACZNIE na tematach tego rozdzialu (generics,
         * lambdy, refleksja, sealed/pattern matching, niezmiennosc, var) - do
         * uzycia przy przegladzie PR-a kolegi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PersonalRoundupOfChapter14 {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz OSOBISTE podsumowanie (min. 8 zdan, w komentarzu)
         * calego rozdzialu _14_advancedjava - ktory z 5 klastrow tematycznych
         * (generics, funkcyjny, refleksja/adnotacje, nowoczesny system typow,
         * niezmiennosc/var/SPI) byl NAJBARDZIEJ przydatny w Twoim codziennym
         * kodzie, a ktory NAJTRUDNIEJSZY do zrozumienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullBestPracticesAudit {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie tej lekcji: wez DOWOLNY wlasny kod (z tego kursu albo
         * spoza niego, min. 50 linii) i przeprowadz PELNY audyt wg macierzy
         * decyzyjnej z lekcji - dla KAZDEGO z 6 tematow (generics, lambdy/OOP,
         * refleksja, sealed/polimorfizm, niezmiennosc, var) napisz w komentarzu, czy
         * kod jest zgodny z dobra praktyka, a jesli nie - jak go poprawic.
         */
        public static void main(String[] args) { }
    }
}
