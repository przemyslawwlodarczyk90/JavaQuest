package com.example.javaquest._16_clean_code.Lesson02_Naming;

public class _Exercises_Lesson02_Naming {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RenameSingleLetterVariables {
        /*
         * 🧪 Zadanie 1:
         * Dany jest fragment: `int d = 30; double p = 19.99; int q = 4;`
         * (dni gwarancji, cena jednostkowa, ilosc sztuk). Przepisz go z
         * czytelnymi, wyrazajacymi intencje nazwami zmiennych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RenameAbbreviatedMethodNames {
        /*
         * 🧪 Zadanie 2:
         * Dane sa nazwy metod: `calcTot()`, `getUsr()`, `chkVal()`. Zaproponuj
         * dla kazdej pelna, czytelna nazwe (w komentarzu) i napisz jedna z
         * nich (dowolnie wybrana) jako realna metode zwracajaca np. String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyMisleadingNameInSnippet {
        /*
         * 🧪 Zadanie 3:
         * Dany jest fragment: `List<Account> accountList = new HashSet<>();`
         * (nazwa sugeruje liste, a to zbior - HashSet). W komentarzu wyjasnij,
         * dlaczego ta nazwa jest MYLACA i zaproponuj poprawna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReplaceMagicNumberWithNamedConstant {
        /*
         * 🧪 Zadanie 4:
         * Dany jest fragment: `if (age >= 18) { ... }`. Zastap magiczna
         * liczbe 18 nazwana stala `static final int ADULT_AGE_THRESHOLD = 18`
         * i uzyj jej w warunku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ChooseConsistentTerminology {
        /*
         * 🧪 Zadanie 5:
         * Dane sa 3 metody realizujace ta sama operacje (odczyt z bazy):
         * `getUser()`, `fetchOrder()`, `retrieveInvoice()`. Przepisz je tak,
         * by uzywaly SPOJNEGO czasownika (np. wszystkie `get...`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RewriteHungarianNotationNames {
        /*
         * 🧪 Zadanie 6:
         * Dany jest fragment: `String strName = "Ala"; int iAge = 30; boolean
         * bIsAdmin = false;` (notacja wegierska). Przepisz go bez prefiksow
         * typu, z zachowaniem `isAdmin` (prefiks znaczeniowy, nie typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NameLoopVariableByScope {
        /*
         * 🧪 Zadanie 7:
         * Napisz krotka petle `for` sumujaca liczby 1-5, w ktorej licznik
         * nazywa sie `i` (bo zasieg jest maly) - a nastepnie napisz metode o
         * dlugiej logice (min. 10 linii), gdzie ta sama rola zmiennej
         * wymagalaby DLUZSZEJ, opisowej nazwy - uzasadnij w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MakeBooleanNameReadAsQuestion {
        /*
         * 🧪 Zadanie 8:
         * Dany jest fragment: `boolean flag = order.getAmount() > 100;`.
         * Zmien nazwe `flag` tak, aby czytala sie jak pytanie (np.
         * `qualifiesForDiscount`), z zachowaniem tej samej logiki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyPronounceabilityMatters {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: dana jest nazwa `genymdhms` (generation year month
         * day hour minute second). W komentarzu wyjasnij, dlaczego ta nazwa
         * jest problematyczna i zaproponuj czytelniejsza alternatywe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFiveNamingRulesFromLesson {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu 5 zasad dobrego nazewnictwa
         * poznanych w tej lekcji, kazda z 1-zdaniowym wyjasnieniem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorEntireMethodWithBadNames {
        /*
         * 🧪 Zadanie 11:
         * Dany jest fragment:
         *   double calc(double a, double b, int c) {
         *       double t = a * b;
         *       if (c > 10) { t = t * 0.9; }
         *       return t;
         *   }
         * (obliczanie ceny: cena jednostkowa * ilosc, rabat gdy ilosc > 10).
         * Przepisz metode z czytelnymi nazwami parametrow, zmiennych i samej
         * metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DesignConsistentDaoNamingScheme {
        /*
         * 🧪 Zadanie 12:
         * Zaprojektuj (jako komentarz + szkielet 3 klas) spojny schemat
         * nazewnictwa dla klas DAO: UserDao, OrderDao, InvoiceDao - kazda z
         * metodami `findById`, `findAll`, `save`, `deleteById` (te same nazwy
         * metod w kazdej klasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RenameClassWithTypePrefix {
        /*
         * 🧪 Zadanie 13:
         * Dany jest interfejs `IOrderService` (prefiks "I" w stylu C#) z
         * implementacja `COrderService`. W komentarzu przepisz te nazwy
         * zgodnie z idiomatyczna konwencja Javy (bez prefiksow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FindAllUsagesOfMagicNumberManually {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode uzywajaca liczby `100` w 3 roznych miejscach o
         * ROZNYM znaczeniu (np. max rozmiar strony, prog rabatu, limit
         * znakow) - a nastepnie zastap KAZDE wystapienie osobna, poprawnie
         * nazwana stala i pokaz, ze wartosci sa teraz latwe do odroznienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NameOverloadedMethodsClearly {
        /*
         * 🧪 Zadanie 15:
         * Napisz 2 metody liczace cene: jedna dla zamowien krajowych, druga
         * dla zagranicznych (z doliczonym cłem) - nadaj im takie nazwy, by
         * z samej sygnatury bylo jasne, czym sie roznia (np.
         * calculateDomesticPrice / calculateInternationalPrice).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AvoidNameCollisionWithBuiltInTypes {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij w komentarzu, dlaczego nazywanie wlasnej
         * zmiennej `list` (bez wskazania CZEGO to lista) albo `string` jest
         * ryzykowne, i zaproponuj lepsze nazwy dla `List<String> list` z
         * kontekstu "lista adresow email klienta".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RefactorBooleanMethodNames {
        /*
         * 🧪 Zadanie 17:
         * Dane sa metody `boolean check(Order o)` i `boolean valid(User u)`.
         * Przepisz je z nazwami czytajacymi sie jak pytanie (np.
         * `isOrderReadyToShip`, `isUserEligibleForDiscount`) - dostosuj tez
         * cialo metody do nowej, bardziej precyzyjnej nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NameConstantsForConfigurationValues {
        /*
         * 🧪 Zadanie 18:
         * Napisz klase `AppConfig` z min. 4 nazwanymi stalymi (`static final`)
         * reprezentujacymi typowe wartosci konfiguracyjne (np. maksymalna
         * liczba polaczen, timeout w ms, adres URL bazowy) - kazda z jasna,
         * SCREAMING_SNAKE_CASE nazwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareTwoNamingStylesSideBySide {
        /*
         * 🧪 Zadanie 19:
         * Napisz TĘ SAMA metode walidujaca haslo (min. 8 znakow, zawiera
         * cyfre) dwa razy - raz z nazwami `s`, `n`, `ok`, raz z czytelnymi
         * odpowiednikami - uruchom obie i porownaj czytelnosc w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteGuidelinesDocumentForTeam {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz krotki "przewodnik nazewnictwa" dla zespolu
         * (min. 6 punktow) - zasady, ktorych kazdy programista w projekcie
         * powinien przestrzegac, oparte na tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorEntireClassNamingScheme {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase `Mgr` z polami `List<Object> l`, `int c`, metodami
         * `add(Object o)`, `get(int i)`, `sz()` (menedzer elementow o
         * limicie pojemnosci) - a potem przepisz CALA klase z czytelnym
         * nazewnictwem (klasa, pola, metody, parametry).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignDomainGlossaryForProject {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: zaprojektuj "słowniczek domenowy" (min. 6 pojec) dla
         * fikcyjnego systemu sklepu internetowego (np. Order vs Cart vs
         * Invoice - czym rozni sie kazde pojecie) - taki słowniczek wymusza
         * spojna terminologie w calym zespole.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RenameAcrossMultipleRelatedClasses {
        /*
         * 🧪 Zadanie 23:
         * Napisz 3 male klasy reprezentujace ta sama koncepcje w 3 roznych,
         * NIESPOJNYCH wariantach nazw (np. `Client`, `Customer`, `User` dla
         * tego samego pojecia w systemie) - w komentarzu zdecyduj, ktora
         * nazwa powinna wygrac w calym projekcie i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_IdentifyAndFixNameThatLiedAfterChange {
        /*
         * 🧪 Zadanie 24:
         * Dana jest metoda `List<User> getActiveUsers()`, ktora w praktyce
         * (po zmianie wymagan) zwraca WSZYSTKICH uzytkownikow (aktywnych i
         * nieaktywnych) - napisz poprawiona wersje: albo zmien nazwe metody,
         * albo przywroc oryginalne zachowanie (filtr) - uzasadnij wybor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildNamingChecklistValidator {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode `static List<String> checkNamingIssues(String name)`,
         * ktora sprawdza podana nazwe pod katem prostych regul (dlugosc < 3
         * znaki, zawiera cyfry na koncu jak `value2`, same wielkie litery) i
         * zwraca liste znalezionych problemow - przetestuj dla min. 4 nazw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RefactorLegacyStyleFieldNames {
        /*
         * 🧪 Zadanie 26:
         * Dana jest klasa z polami `m_name`, `m_age`, `_temp` (stare
         * konwencje prefiksow pol z C++/starszej Javy) - przepisz klase
         * zgodnie ze wspolczesna konwencja Javy (bez prefiksow `m_`/`_`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareNamingAcrossTwoRealCourseChapters {
        /*
         * 🧪 Zadanie 27:
         * Przejrzyj 2 rozdzialy kursu (np. `_08_sql` i `_10_dao`) i znajdz
         * min. 2 przyklady dobrze nazwanych metod/klas - w komentarzu
         * wyjasnij, co dokladnie czyni te nazwy dobrymi wedlug zasad z tej
         * lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignNamingConventionForBooleanFlags {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (jako komentarz + przyklady kodu) konwencje nazewnicza
         * dla flag boolowskich w calym projekcie: kiedy `is...`, kiedy
         * `has...`, kiedy `can...` - z min. 2 przykladami dla kazdego
         * prefiksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasureImpactOfRenamingOnCodeReviewSpeed {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport (min. 6 zdan) opisujacy, jak zle
         * nazewnictwo w Pull Requeście wydluza code review (recenzent musi
         * pytac "co to `tmp2`?" zamiast oceniac logike) - odwolaj sie do
         * konkretnego przykladu z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneRenameFullMessyClassHierarchy {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz WLASNA, celowo zle nazwana
         * hierarchie 2-3 klas (min. 15 linii razem, np. system rezerwacji)
         * z jednoliterowymi/skrotowymi nazwami wszedzie, a nastepnie
         * zrefaktoryzuj CALOSC do czytelnych nazw - obie wersje musza miec
         * identyczna logike i wynik dzialania.
         */
        public static void main(String[] args) { }
    }
}
