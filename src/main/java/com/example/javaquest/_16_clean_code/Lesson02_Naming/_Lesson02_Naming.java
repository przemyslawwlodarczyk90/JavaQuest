package com.example.javaquest._16_clean_code.Lesson02_Naming;

import java.util.ArrayList;
import java.util.List;

public class _Lesson02_Naming {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: NAZEWNICTWO ===");

        /*
         * ============================================================
         * 🔹 DLACZEGO NAZEWNICTWO JEST NAJWAZNIEJSZA UMIEJETNOSCIA CLEAN CODE
         * ============================================================
         * - Nazwy (zmiennych, metod, klas, pakietow) to podstawowy "jezyk",
         *   ktorym program komunikuje sie z czlowiekiem. Kompilator nie dba
         *   o to, czy zmienna nazywa sie `d` czy `dniDoWygasnieciaKonta` -
         *   ale programista czytajacy kod dba bardzo.
         * - Dobra nazwa ODPOWIADA na pytania: dlaczego to istnieje, co robi,
         *   jak sie tego uzywa - bez potrzeby czytania implementacji ani
         *   szukania komentarza.
         * - Zla nazwa wymaga, by kazdy czytajacy "trzymal w glowie" dodatkowy
         *   kontekst (np. "a, `flag` to tak naprawde info, czy zamowienie
         *   jest oplacone") - to marnuje energie mentalna i rodzi bledy.
         */
        demonstrateBadlyNamedOrderSummary();

        /*
         * ============================================================
         * 🔍 ZASADA 1: NAZWA MA WYRAZAC INTENCJE (INTENTION-REVEALING NAMES)
         * ============================================================
         * - Nazwa powinna odpowiadac na pytanie "PO CO to istnieje?", nie
         *   tylko "JAKIEGO jest typu?".
         * - Zla: `int d;` (ile dni? od czego? do czego?)
         * - Dobra: `int elapsedDaysSinceRegistration;` - nazwa mowi WSZYSTKO
         *   bez potrzeby czytania reszty kodu.
         * - Ta sama zasada dotyczy metod: `process()` nic nie mowi,
         *   `calculateMonthlyInvoiceTotal()` mowi dokladnie, co sie stanie.
         */
        System.out.println("\nPrzyklad: `int d` vs `int elapsedDaysSinceRegistration` - ta sama wartosc, rozna czytelnosc.");

        /*
         * ============================================================
         * 🔹 ZASADA 2: UNIKAJ SKROTOW I MYLACYCH NAZW
         * ============================================================
         * - Skroty oszczedzaja kilka znakow przy PISANIU, ale kosztuja czas
         *   przy KAZDYM czytaniu (kod czytamy o wiele czesciej niz piszemy -
         *   patrz Lekcja 1).
         * - Zle: `usrMgr`, `tmpVal`, `calcTot()` - wymagaja domyslania sie.
         * - Dobre: `userManager`, `temporaryValue`, `calculateTotal()`.
         * - Mylace nazwy sa GORSZE niz brak nazwy - np. lista nazwana
         *   `accountList`, ktora w rzeczywistosci jest tablica (`Account[]`)
         *   albo zbiorem (`Set<Account>`) - czytelnik zostanie wprowadzony
         *   w blad przez sama nazwe.
         */
        System.out.println("Unikaj: usrMgr, tmpVal, calcTot() -> uzywaj: userManager, temporaryValue, calculateTotal()");

        /*
         * ============================================================
         * 🔍 ZASADA 3: SPOJNA TERMINOLOGIA W CALYM PROJEKCIE
         * ============================================================
         * - Wybierz JEDNO slowo na jedno pojecie i trzymaj sie go konsekwentnie
         *   w calym projekcie - nie mieszaj `fetch`, `retrieve`, `get` dla
         *   tej samej operacji w roznych miejscach kodu.
         * - Przyklad niespojnosci: `getUser()`, `fetchOrder()`, `retrieveInvoice()`
         *   - trzy rozne slowa dla dokladnie tej samej operacji (odczyt z bazy).
         *   Czytelnik musi sie zastanawiac, czy roznica w nazwie oznacza
         *   rozne zachowanie (np. cache vs baza) - a moze to tylko przypadek.
         * - Ta sama zasada dotyczy nazw klas realizujacych te sama role
         *   (np. wszystkie klasy DAO powinny konczyc sie na "Dao", wszystkie
         *   klasy walidujace na "Validator" itd. - spojnosc = przewidywalnosc).
         */
        demonstrateInconsistentVsConsistentTerminology();

        /*
         * ============================================================
         * 🔹 ZASADA 4: NAZWY WYMAWIALNE I WYSZUKIWALNE
         * ============================================================
         * - Nazwa wymawialna (pronounceable) - da sie o niej mowic na
         *   spotkaniu zespolu. `genymdhms` (generation year, month, day,
         *   hour, minute, second) jest technicznie poprawna, ale nikt nie
         *   powie tego na glos - zamiast tego: `generationTimestamp`.
         * - Nazwa wyszukiwalna (searchable) - jednoliterowe nazwy (`x`, `i`
         *   poza krotkimi petlami) i "magiczne liczby" (np. `7`) sa
         *   niemozliwe do wyszukania w duzym projekcie (Ctrl+F znajdzie
         *   tysiace przypadkowych `7`). Nazwana stala
         *   `MAX_RETRY_ATTEMPTS = 7` jest ZARAZEM dokumentacja i jest
         *   latwa do znalezienia we wszystkich uzyciach.
         */
        demonstrateMagicNumberVsNamedConstant();

        /*
         * ============================================================
         * 🔍 ZASADA 5: BEZ "HUNGARIAN NOTATION" I ZBEDNYCH PREFIKSOW/TYPOW W NAZWIE
         * ============================================================
         * - Hungarian notation (notacja wegierska) to stara konwencja
         *   kodowania TYPU w nazwie zmiennej (np. `strName`, `iCount`,
         *   `bIsValid`) - w nowoczesnej Javie z silnym systemem typow i IDE
         *   pokazujacym typ na hover jest to CZYSTA nadmiarowosc.
         * - Podobnie zbedne sa prefiksy typu obiektu w nazwie klasy (np.
         *   interfejs nazwany `IOrderService` - konwencja z C#/starej Javy;
         *   w Javie zwyczajowo interfejs nazywa sie po prostu `OrderService`,
         *   a jego implementacja np. `DefaultOrderService`/`JpaOrderService`).
         * - Wyjatek potwierdzajacy regule: prefiksy `is`/`has` dla wartosci
         *   boolowskich (`isValid`, `hasPermission`) SA uzyteczne - bo
         *   opisuja ZNACZENIE (pytanie), nie typ danych.
         */
        demonstrateHungarianNotationVsIdiomaticJava();

        /*
         * ============================================================
         * 🔹 ZASADA 6: DLUGOSC NAZWY PROPORCJONALNA DO ZASIEGU (SCOPE)
         * ============================================================
         * - Zmienna licznika petli `for (int i = 0; i < 10; i++)` uzyta
         *   TYLKO wewnatrz 2 linii petli - `i` jest OK, bo caly jej "zasieg
         *   zycia" miesci sie na ekranie na raz.
         * - Zmienna/pole klasy uzywane w calej klasie (albo w calym module)
         *   MUSI miec dluzsza, opisowa nazwe - bo czytelnik widzi jej UZYCIE
         *   daleko od DEKLARACJI i nie ma szans zgadnac znaczenia `i` w
         *   metodzie o dlugosci 200 linii.
         * - Ogolna zasada: im wiekszy zasieg (scope), tym WIEKSZA
         *   odpowiedzialnosc nazwy za samodokumentowanie sie.
         */
        demonstrateScopeProportionalNaming();

        /*
         * ============================================================
         * 📌 DEMO KONCOWE: TA SAMA LOGIKA, FATALNE NAZWY VS CZYTELNE NAZWY
         * ============================================================
         * - Ponizej dokladnie ta sama logika (podsumowanie zamowien klienta)
         *   napisana najpierw z nazwami `d`, `list1`, `tmp`, `flag`, a potem
         *   z czytelnymi odpowiednikami - obie wersje daja IDENTYCZNY wynik.
         */
        demonstrateBadNamesVersion();
        demonstrateGoodNamesVersion();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Nazwa ma wyrazac intencje, byc jednoznaczna, wymawialna i
         *   wyszukiwalna.
         * - Unikaj skrotow, mylacych nazw, notacji wegierskiej.
         * - Trzymaj spojna terminologie w calym projekcie.
         * - Dlugosc nazwy powinna rosnac wraz z jej zasiegiem (scope).
         * - W kolejnej lekcji (Lesson03): komentarze - kiedy sa POTRZEBNE,
         *   a kiedy dobra nazwa czyni komentarz ZBEDNYM.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateBadlyNamedOrderSummary() {
        System.out.println("\n=== PROBLEM: NAZWY BEZ INTENCJI ===");
        int d = 45;
        System.out.println(" int d = 45; -- co to jest? dni? dolary? indeks? BEZ KONTEKSTU NIE WIADOMO.");
        int elapsedDaysSinceRegistration = 45;
        System.out.println(" int elapsedDaysSinceRegistration = 45; -- teraz wiadomo natychmiast.");
    }

    private static void demonstrateInconsistentVsConsistentTerminology() {
        System.out.println("\n=== SPOJNA TERMINOLOGIA ===");
        System.out.println(" Niespojnie: getUser(), fetchOrder(), retrieveInvoice() -- 3 slowa, ta sama operacja");
        System.out.println(" Spojnie:    getUser(), getOrder(), getInvoice()       -- 1 slowo, jasny wzorzec");
    }

    private static void demonstrateMagicNumberVsNamedConstant() {
        System.out.println("\n=== MAGICZNA LICZBA VS NAZWANA STALA ===");

        int attempts = 0;
        // ZLA: '7' to magiczna liczba - nie da sie jej wyszukac ani zrozumiec bez kontekstu
        boolean canRetryMagic = attempts < 7;
        System.out.println(" attempts < 7 (magiczna liczba): " + canRetryMagic);

        // DOBRA: nazwana stala jest jednoczesnie dokumentacja i jest wyszukiwalna
        final int maxRetryAttempts = 7;
        boolean canRetryNamed = attempts < maxRetryAttempts;
        System.out.println(" attempts < maxRetryAttempts (nazwana stala): " + canRetryNamed);
    }

    private static void demonstrateHungarianNotationVsIdiomaticJava() {
        System.out.println("\n=== HUNGARIAN NOTATION VS IDIOMATYCZNA JAVA ===");

        String strName = "Anna"; // ZLA: prefiks typu w nazwie - nadmiarowy, IDE i tak pokazuje typ
        boolean bIsActive = true; // ZLA: prefiks typu boolowskiego

        String name = "Anna"; // DOBRA: bez prefiksu typu
        boolean isActive = true; // DOBRA: prefiks "is" opisuje ZNACZENIE (pytanie), nie typ

        System.out.println(" zle:    strName=" + strName + ", bIsActive=" + bIsActive);
        System.out.println(" dobrze: name=" + name + ", isActive=" + isActive);
    }

    private static void demonstrateScopeProportionalNaming() {
        System.out.println("\n=== DLUGOSC NAZWY A ZASIEG (SCOPE) ===");

        int sum = 0;
        for (int i = 0; i < 5; i++) { // 'i' OK - zasieg to 1 linia petli, caly widoczny na raz
            sum += i;
        }
        System.out.println(" suma z krotkiej petli (i jako licznik): " + sum);

        // Gdyby ta zmienna byla polem klasy albo uzywana w metodzie 100+ linii,
        // 'i' bylaby ZLYM wyborem - potrzebna bylaby opisowa nazwa, np. currentRowIndex.
        System.out.println(" w duzym zasiegu 'i' zamienilibysmy na np. currentRowIndex");
    }

    /*
     * ZLY PRZYKLAD: fatalne, jednoliterowe/skrotowe nazwy - dziala poprawnie,
     * ale kazdy czytelnik musi zgadywac, co reprezentuje kazda zmienna.
     */
    private static void demonstrateBadNamesVersion() {
        System.out.println("\n=== ZLY PRZYKLAD: FATALNE NAZWY ===");

        List<Double> list1 = new ArrayList<>();
        list1.add(120.0);
        list1.add(45.5);
        list1.add(300.0);

        double tmp = 0;
        for (double d : list1) {
            tmp += d;
        }

        boolean flag = tmp > 200;

        System.out.println(" list1=" + list1 + ", tmp=" + tmp + ", flag=" + flag);
        System.out.println(" ^ co to jest 'flag'? 'tmp'? trzeba czytac cala logike, zeby sie domyslic.");
    }

    /*
     * DOBRY PRZYKLAD: identyczna logika, ale nazwy mowia dokladnie, co
     * reprezentuja - czytelnik NIE musi analizowac implementacji.
     */
    private static void demonstrateGoodNamesVersion() {
        System.out.println("\n=== DOBRY PRZYKLAD: CZYTELNE NAZWY ===");

        List<Double> orderAmounts = new ArrayList<>();
        orderAmounts.add(120.0);
        orderAmounts.add(45.5);
        orderAmounts.add(300.0);

        double totalOrderAmount = 0;
        for (double orderAmount : orderAmounts) {
            totalOrderAmount += orderAmount;
        }

        boolean qualifiesForFreeShipping = totalOrderAmount > 200;

        System.out.println(" orderAmounts=" + orderAmounts + ", totalOrderAmount=" + totalOrderAmount
                + ", qualifiesForFreeShipping=" + qualifiesForFreeShipping);
        System.out.println(" ^ ta sama logika, ale znaczenie kazdej wartosci jest oczywiste od razu.");
    }
}
