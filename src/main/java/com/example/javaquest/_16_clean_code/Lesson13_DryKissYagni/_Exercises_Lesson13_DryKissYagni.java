package com.example.javaquest._16_clean_code.Lesson13_DryKissYagni;

public class _Exercises_Lesson13_DryKissYagni {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDryInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjasnij wlasnymi
         * slowami, czym jest zasada DRY i dlaczego dotyczy duplikacji WIEDZY
         * biznesowej, a nie tylko identycznego tekstu kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FindDuplicatedValidationInThreeMethods {
        /*
         * 🧪 Zadanie 2:
         * Napisz 3 metody statyczne: registerProduct(String name, double price),
         * updateProductPrice(double price), importProduct(String name, double price) -
         * kazda niech powiela ten sam warunek: cena musi byc > 0, w przeciwnym
         * razie zwroc "BLAD: niepoprawna cena". Uruchom wszystkie 3 z bledna ceną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExtractSharedValidationMethod {
        /*
         * 🧪 Zadanie 3:
         * Zrefaktoruj kod z Zadania 2 - wyciagnij warunek walidacji ceny do
         * osobnej metody prywatnej validatePrice(double price) i wywolaj ja z
         * 3 miejsc. Sprawdz, ze wynik dziala tak samo jak przed refaktoryzacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainKissInOwnWords {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjasnij zasade KISS i
         * podaj wlasny przyklad "sprytnego" fragmentu kodu, ktory latwiej
         * bylo napisac prosciej bez utraty wydajnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareEvenCheckSmartVsSimple {
        /*
         * 🧪 Zadanie 5:
         * Napisz 2 metody sprawdzajace czy liczba jest parzysta: jedna uzywajac
         * bitowego AND (n & 1) == 0, druga uzywajac n % 2 == 0. Uruchom obie
         * dla kilku liczb i w komentarzu napisz, ktora jest czytelniejsza dla
         * poczatkujacego programisty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainYagniInOwnWords {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjasnij zasade YAGNI i
         * podaj przyklad abstrakcji (interfejs, fabryka, konfigurowalny
         * parametr), ktorej Twoim zdaniem NIE warto budowac bez konkretnej,
         * dzisiejszej potrzeby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteXorSwapAndTriggerBug {
        /*
         * 🧪 Zadanie 7:
         * Odtworz metode swapSmart(int[] arr, int i, int j) z teorii (XOR
         * swap) i wywolaj ja z i == j na dowolnej tablicy - zaobserwuj i
         * wypisz, ze wartosc pod tym indeksem zostaje wyzerowana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteSimpleSwapAndCompare {
        /*
         * 🧪 Zadanie 8:
         * Napisz swapSimple(int[] arr, int i, int j) uzywajaca zmiennej
         * pomocniczej i wywolaj ja z tymi samymi argumentami co w Zadaniu 7
         * (i == j) - pokaz, ze tablica pozostaje niezmieniona (poprawnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyOverengineeredExampleFromLesson {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: przypomnij sobie przyklad NotificationSender +
         * NotificationSenderFactory + EmailNotificationStrategy z teorii - w
         * komentarzu napisz, ile klas/interfejsow trzeba przejsc, zeby dojsc
         * do miejsca, gdzie realnie "dzieje sie" wysylka e-maila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteSimpleDirectNotifierClass {
        /*
         * 🧪 Zadanie 10:
         * Napisz WLASNA, prosta klase SmsNotifier z jedna metoda
         * send(String to, String message) zwracajaca sformatowany String -
         * BEZ zadnego interfejsu ani fabryki. Wywolaj ja i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FindAndExtractDuplicatedDiscountLogic {
        /*
         * 🧪 Zadanie 11:
         * Napisz 2 metody: calculateOrderTotal(double price, int quantity) i
         * calculateInvoiceTotal(double price, int quantity) - obie niech
         * powielaja logike: jesli quantity >= 10, zastosuj 10% rabatu.
         * Nastepnie zrefaktoruj, wyciagajac wspolna metode applyBulkDiscount.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DistinguishAccidentalFromRealDuplication {
        /*
         * 🧪 Zadanie 12:
         * Napisz 2 metody o identycznym dzis wzorze: calculateEmployeeBonus
         * (premia = pensja * 0.1) i calculateReferralBonus (premia za
         * polecenie = pensja * 0.1). W komentarzu wyjasnij, czy to
         * duplikacja PRZYPADKOWA czy RZECZYWISTA - i uzasadnij, czy warto je
         * polaczyc w 1 metode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RefactorThreeCopiesOfEmailValidation {
        /*
         * 🧪 Zadanie 13:
         * Odtworz klase Duplicated z teorii (registerUser/updateUserEmail/
         * importUserFromCsv z powielona walidacja) i samodzielnie
         * zrefaktoruj ja do postaci z jedna metoda validateEmail - porownaj
         * liczbe linii kodu przed i po (wypisz obie liczby w komentarzu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteCleverBitHackAndSimpleAlternative {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode obliczajaca maksimum z 3 liczb calkowitych na 2
         * sposoby: (a) jednym "sprytnym" wyrazeniem z zagniezdzonymi
         * operatorami trojargumentowymi (?:), (b) przez proste 2 instrukcje
         * if. Uruchom obie dla tych samych 3 liczb i porownaj czytelnosc w
         * komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignMinimalConfigWithoutFutureProofing {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj prosta klase AppConfig z DOKLADNIE tymi polami, ktore
         * sa dzis potrzebne (np. String databaseUrl, int maxConnections) -
         * BEZ dodawania "na zapas" pol typu cacheStrategy czy retryPolicy,
         * ktorych nikt jeszcze nie potrzebuje. Utworz instancje i wypisz jej
         * zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RemoveUnnecessaryFactoryForSingleImplementation {
        /*
         * 🧪 Zadanie 16:
         * Odtworz NotificationSenderFactory + EmailNotificationStrategy z
         * teorii, a nastepnie napisz wersje BEZ fabryki i interfejsu -
         * bezposrednia klase EmailNotifier - i pokaz, ze daje ten sam
         * efekt koncowy przy mniejszej liczbie typow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteThreeMethodsSharingParameterValidation {
        /*
         * 🧪 Zadanie 17:
         * Napisz 3 metody operujace na koncie bankowym: deposit(double
         * amount), withdraw(double amount), transfer(double amount) - kazda
         * powielajaca warunek "amount musi byc > 0". Zrefaktoruj do wspolnej
         * metody requirePositiveAmount(double amount), ktora rzuca
         * IllegalArgumentException z czytelnym komunikatem gdy warunek nie
         * jest spelniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareTwoImplementationsOfIsPalindrome {
        /*
         * 🧪 Zadanie 18:
         * Napisz 2 metody sprawdzajace czy String jest palindromem: jedna
         * "sprytna" (np. przy uzyciu StringBuilder.reverse() i porownania
         * calego stringu), druga prosta (petla dwoch wskaznikow od
         * konca/poczatku). Zmierz w komentarzu, czy ktoras jest realnie
         * szybsza dla krotkich napisow - i ktora jest czytelniejsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentifySpeculativeGeneralityInGivenSnippet {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: zaprojektuj (w komentarzu, jako pseudo-opis) klase
         * z co najmniej 3 metodami/parametrami, ktore sa "spekulacyjna
         * ogolnoscia" (speculative generality) - czyli istnieja "na wszelki
         * wypadek" bez konkretnego uzycia. Wyjasnij, co usunalbys zgodnie z
         * YAGNI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorDuplicatedLoopsIntoSingleMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz 2 metody: sumEvenNumbers(int[] arr) i sumOddNumbers(int[]
         * arr) - kazda z osobna petla sumujaca. Zrefaktoruj do jednej
         * metody generycznej sumMatching(int[] arr, java.util.function.IntPredicate
         * predicate), ktora przyjmuje warunek jako parametr (uzyj jej dla
         * parzystych i nieparzystych).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildRealisticDuplicationAcrossThreeClasses {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj 3 rozne klasy (np. OrderService, InvoiceService,
         * ShipmentService), z ktorych KAZDA ma metode formatCustomerName(String
         * first, String last) z IDENTYCZNA logika (np. "Nazwisko, Imie" z
         * duzej litery). Zrefaktoruj, wyciagajac wspolna klase narzedziowa
         * NameFormatter uzywana przez wszystkie 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ArgueWhenDuplicationShouldStay {
        /*
         * 🧪 Zadanie 22:
         * Napisz 2 metody z identycznym dzis kodem: validateOrderQuantity
         * (musi byc miedzy 1 a 100) i validateReviewRating (musi byc miedzy
         * 1 a 100 - skala ocen 1-100 tego konkretnego systemu). W komentarzu
         * uzasadnij (min. 4 zdania), dlaczego POŁACZENIE ich w 1 metode
         * byloby bledem, mimo identycznego warunku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasurePerformanceOfSmartVsSimpleSwap {
        /*
         * 🧪 Zadanie 23:
         * Uruchom swapSmart i swapSimple (z teorii/Zadan 7-8) milion razy w
         * petli na tablicy 2-elementowej i zmierz czas przez System.nanoTime()
         * dla obu wariantow. Wypisz oba czasy i w komentarzu skomentuj, czy
         * "sprytna" wersja daje jakakolwiek realna przewage wydajnosciowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignMinimalPaymentProcessorWithoutPlugins {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj klase PaymentProcessor obslugujaca TYLKO platnosc
         * kartowa (jedyna metoda platnosci wymagana dzis) - metoda
         * processCardPayment(double amount). NIE dodawaj systemu wtyczek
         * (plugin) dla przyszlych metod platnosci (przelew, crypto), ktorych
         * nikt jeszcze nie wymaga. Wywolaj i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorLargeSwitchDuplicationIntoMap {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode z powielonym schematem: 4 gałęzie if/else if, z
         * ktorych kazda robi "sprawdz warunek + zwroc opis" dla statusow
         * zamowienia (NEW, PAID, SHIPPED, DELIVERED). Zrefaktoruj do
         * java.util.Map<String, String> z gotowymi opisami (mniej
         * powielonej struktury warunkowej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DetectFeatureCreepInConfigClass {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: przeanalizuj (w komentarzu) hipotetyczna klase
         * ReportGenerator z metodami generatePdf(), generateExcel(),
         * generateWord(), generatePowerpoint() - podczas gdy projekt
         * dzisiaj potrzebuje TYLKO generatePdf(). Zaproponuj, co zostawic, a
         * co usunac zgodnie z YAGNI, i jak podejsc do tematu, gdyby PDF byl
         * jedynym realnym wymaganiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CombineDryKissYagniInOneRefactor {
        /*
         * 🧪 Zadanie 27:
         * Napisz "przed": 3 metody walidujace rozne obiekty (User, Product,
         * Order), kazda z wlasna, powielona logika sprawdzania null +
         * "sprytnym" jednolinijkowym warunkiem laczacym kilka sprawdzen przez
         * zagniezdzone && i ||. Napisz "po": wspolna, czytelna metoda
         * generyczna requireNonNull(Object value, String fieldName) uzywana
         * we wszystkich 3 miejscach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_JustifyKeepingTwoSimilarButIndependentRules {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj 2 metody: calculateLatePickupFee (oplata za spozniony
         * odbior z wypozyczalni) i calculateLateReturnFee (oplata za
         * spozniony zwrot) - obie dzis licza `dni_spoznienia * 10.0`. Napisz
         * kod obu, uruchom je, a w komentarzu wyjasnij min. 3 scenariusze
         * biznesowe, w ktorych te 2 reguly moga zacząć sie od siebie roznic
         * mimo dzisiejszej identycznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RewriteCleverOneLinerAsReadableMethod {
        /*
         * 🧪 Zadanie 29:
         * Znajdz (lub napisz) "sprytny" jednolinijkowy kod liczacy sume
         * cyfr liczby calkowitej przy uzyciu rekurencji w wyrazeniu
         * trojargumentowym w jednej linii. Przepisz go jako czytelna,
         * kilkulinijkowa metode z petla i nazwanymi zmiennymi - uruchom obie
         * wersje dla tej samej liczby i porownaj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneAuditSmallCodebaseForDryKissYagni {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz maly, samodzielny fragment "systemu"
         * (3-4 metody/klasy) zawierajacy CELOWO wszystkie 3 problemy z tej
         * lekcji: (1) powielona logike walidacji w 2+ miejscach, (2) co
         * najmniej jeden "sprytny" fragment kodu, (3) jedna niepotrzebna
         * warstwe abstrakcji (interfejs/fabryke dla 1 implementacji).
         * Nastepnie w OSOBNEJ wersji (druga metoda main lub druga klasa)
         * pokaz poprawiona wersje stosujaca DRY, KISS i YAGNI - uruchom obie
         * i porownaj w komentarzu.
         */
        public static void main(String[] args) { }
    }
}
