package com.example.javaquest._17_architecture.Lesson05_DomainModelVsAnemicModel;

public class _Exercises_Lesson05_DomainModelVsAnemicModel {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAnemicModelCriticismInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), dlaczego Fowler skrytykowal model anemiczny jako
         * "naruszenie podstawowej idei OOP".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteAnemicAccountClassWithPublicSetters {
        /*
         * 🧪 Zadanie 2:
         * Napisz anemiczna klase `AccountAnemic` z polem `balance` i
         * publicznym getterem/setterem - napisz osobna metode "Service"
         * wyplacajaca srodki (sprawdzajaca saldo Z ZEWNATRZ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DemonstrateInvalidStateFromOutsideForAccountAnemic {
        /*
         * 🧪 Zadanie 3:
         * Uzywajac klasy z Zadania 2, ustaw `balance` na wartosc UJEMNA
         * BEZPOSREDNIO przez setter (z pominieciem "Service") - zademonstruj,
         * ze encja SAMA nie ma jak tego zabronic.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RefactorAccountToRichModelWithWithdrawMethod {
        /*
         * 🧪 Zadanie 4:
         * Zrefaktoryzuj `AccountAnemic` na bogaty `Account` z prywatnym polem
         * `balance` i metoda `withdraw(double amount)` sprawdzajaca WEWNATRZ,
         * czy saldo jest wystarczajace (rzuca wyjatek, jesli nie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DemonstrateAccountRejectsInvalidWithdrawal {
        /*
         * 🧪 Zadanie 5:
         * Uzywajac `Account` z Zadania 4, sprobuj wyplacic wiecej niz saldo -
         * zlap wyjatek i wypisz komunikat. Zweryfikuj, ze saldo NIE zmienilo
         * sie po nieudanej probie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareAnemicVsRichForSameFeatureSideBySide {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj TA SAMA regule (np. "haslo mozna zmienic TYLKO jesli
         * stare haslo sie zgadza") jako (a) anemiczny model + Service, (b)
         * bogaty model z metoda `changePassword(old, new)`. Porownaj gdzie
         * zyje regula w obu wersjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyTellDontAskViolationInAnemicCode {
        /*
         * 🧪 Zadanie 7:
         * Dany jest kod "Service" PYTAJACY encje o stan (`getStatus()`) i
         * SAMODZIELNIE decydujacy, co robic. Napisz taki kod i w komentarzu
         * wskaz naruszenie "Tell, Don't Ask" (`_16_clean_code/Lesson12`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixTellDontAskViolationFromExercise07 {
        /*
         * 🧪 Zadanie 8:
         * Popraw kod z Zadania 7 - encja dostaje wlasna metode biznesowa, a
         * "Service" TYLKO ja woluje, bez pytania o stan z gory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyWhenAnemicModelIsAcceptable {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: opisz (min. 3 zdania) 1 wlasny przyklad aplikacji,
         * gdzie model anemiczny jest AKCEPTOWALNY (prosty CRUD bez realnych
         * regul biznesowych) - uzasadnij.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRichModelBenefitsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * korzysci bogatego modelu domenowego z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignRichOrderWithMultipleStateTransitions {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj bogata klase `Order` z metodami `confirm()`, `ship()`,
         * `cancel()`, KAZDA sprawdzajaca dozwolone przejscie ze swojego
         * WEWNETRZNEGO stanu - zademonstruj PELNY, poprawny cykl zycia
         * zamowienia (confirm -> ship) i min. 1 NIEDOZWOLONE przejscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorAnemicOrderWithScatteredValidationIntoRichModel {
        /*
         * 🧪 Zadanie 12:
         * Napisz anemiczny `OrderAnemic` z logika przejsc stanu POWTORZONA w 3
         * ROZNYCH metodach "Service" (confirm/ship/cancel, kazda sprawdzajaca
         * status z zewnatrz). Zrefaktoryzuj na bogaty model z Zadania 11 -
         * policz, ile miejsc walidacji "zniknelo" (przeniosly sie do 1 miejsca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignRichAccountWithDepositAndWithdrawEnforcingInvariant {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj bogate konto `BankAccount` z `deposit(double)` i
         * `withdraw(double)`, gdzie WEWNETRZNY niezmiennik "saldo nigdy
         * ujemne" jest CHRONIONY przez OBIE metody. Zademonstruj probe
         * naruszenia niezmiennika i jej odrzucenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateJpaLikeEntityWithPrivateSetterAndBusinessMethod {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj encje "JPA-podobna" (pola jak w `_12_hibernate`, ale bez
         * prawdziwych adnotacji) z bezargumentowym konstruktorem (wymog ORM)
         * ORAZ metoda biznesowa `activate()` (bez publicznego settera pola
         * `active`) - zademonstruj, ze ORM-owe wymogi NIE wykluczaja
         * bogatego modelu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareChangeCostWhenAddingNewStateTransitionRule {
        /*
         * 🧪 Zadanie 15:
         * Dla systemow z Zadania 12 (obie wersje), dodaj NOWA regule (np.
         * "zamowienie mozna anulowac TYLKO w ciagu 24h od zlozenia") - w
         * komentarzu policz, ile miejsc trzeba zmienic w KAZDEJ wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignRichModelForShoppingCartWithBusinessInvariant {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj bogaty `ShoppingCart` z metoda `addItem(String product,
         * int quantity)` CHRONIACA niezmiennik "maks. 20 pozycji w koszyku" -
         * zademonstruj dodanie do limitu i probe przekroczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_IdentifyAnemicSignalInRealCodeFromEarlierChapter {
        /*
         * 🧪 Zadanie 17:
         * Wybierz 1 klase-encje z dowolnej wczesniejszej lekcji kursu (np.
         * `_12_hibernate`). W komentarzu oceń, czy jest anemiczna (tylko
         * gettery/settery) czy ma wlasne zachowanie biznesowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignValueObjectEnforcingInvariantInsideConstructor {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj bogaty typ wartosciowy `PositiveAmount` (record z
         * walidacja w konstruktorze kompaktowym, `_16_clean_code/Lesson19`) -
         * uzyj go jako typu pola w bogatym `BankAccount` z Zadania 13 zamiast
         * `double`, eliminujac mozliwosc UJEMNEJ kwoty JUZ na poziomie typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignAdrJustifyingAnemicModelForSimpleReportingModule {
        /*
         * 🧪 Zadanie 19:
         * Napisz ADR (`Lesson02`) uzasadniajacy SWIADOMY wybor modelu
         * anemicznego dla modulu "generowanie raportow" (bez realnych regul
         * biznesowych, tylko transformacja danych) - z realistycznym
         * kontekstem i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildRichModelPreventingInconsistentMultiFieldState {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj bogaty `DateRange` (np. dla rezerwacji) z polami
         * `startDate`/`endDate` USTAWIANYMI TYLKO przez konstruktor, ktory
         * odrzuca `endDate` wczesniejszy niz `startDate` - zademonstruj probe
         * stworzenia niepoprawnego zakresu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignRealisticRichOrderAggregateWithLineItems {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj bogaty `Order` zawierajacy WEWNETRZNA liste
         * `OrderLine` (produkt+ilosc) - metoda `addLine(...)` CHRONI
         * niezmiennik "suma wartosci zamowienia nie moze przekroczyc limitu
         * kredytowego klienta" (limit jako parametr konstruktora Order).
         * Zademonstruj dodanie pozycji w limicie i probe przekroczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorRealisticAnemicOrderSystemToRichAggregateFromExercise21 {
        /*
         * 🧪 Zadanie 22:
         * Napisz REALISTYCZNY anemiczny system zamowien z limitem
         * kredytowym sprawdzanym w 2 ROZNYCH miejscach "Service" (przy
         * dodaniu pozycji i przy finalizacji) - zrefaktoryzuj na bogaty
         * agregat z Zadania 21, eliminujac powielona walidacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignRichModelWithDomainEventEmittedOnStateChange {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj bogaty `Order` z metoda `ship()`, ktora PO udanej
         * zmianie stanu DODAJE zdarzenie domenowe (np. String opis) do
         * WEWNETRZNEJ listy `List<String> events` - to wprowadzenie do
         * Lesson18 (EventDrivenCommunicationBetweenModules). Zademonstruj
         * odczyt zdarzen po wywolaniu ship().
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareTestabilityOfAnemicVsRichModelForBusinessRule {
        /*
         * 🧪 Zadanie 24:
         * Dla TEJ SAMEJ reguly (np. "rezerwacja lotu wymaga min. 2h przed
         * odlotem") napisz test-demo dla (a) anemicznego modelu + Service, (b)
         * bogatego modelu - w komentarzu porownaj, ile klas/obiektow trzeba
         * "postawic", zeby przetestowac SAMA regule w kazdej wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignRichModelHierarchyRespectingLspFromCleanCodeChapter {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj bogata hierarchie `Subscription` (interfejs) z 2
         * implementacjami (`MonthlySubscription`, `AnnualSubscription`),
         * KAZDA z metoda `renew()` chroniaca WLASNA regule odnowienia -
         * upewnij sie, ze hierarchia respektuje LSP (`_16_clean_code/
         * Lesson09`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildRichModelWithMultipleCollaboratingInvariants {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj bogaty `Auction` (aukcja) z metoda `placeBid(double
         * amount)` chroniaca NARAZ 2 niezmienniki: "kwota wyzsza niz
         * obecna najwyzsza oferta" ORAZ "aukcja jeszcze nie zakonczona" -
         * zademonstruj min. 3 sceanriusze (sukces, zbyt niska oferta,
         * zakonczona aukcja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForMixedModelStrategyAcrossModules {
        /*
         * 🧪 Zadanie 27:
         * Napisz ADR uzasadniajacy STRATEGIE MIESZANA: bogaty model dla
         * modulu platnosci (realne reguly biznesowe), anemiczny model dla
         * modulu raportowania (tylko transformacje) - w TYM SAMYM systemie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RefactorRealEntityFromHibernateChapterToAddRichBehavior {
        /*
         * 🧪 Zadanie 28:
         * Wybierz 1 prosta encje z `_12_hibernate` (np. podobna do Book/
         * Employee) i w KOMENTARZU zaproponuj (a nastepnie zaimplementuj jako
         * osobna, podobna klase w tym pliku) DODANIE 1 metody biznesowej,
         * ktora "wzbogaca" ja, zachowujac zgodnosc z wymogami JPA (konstruktor
         * bezargumentowy, prywatne settery).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveRichVsAnemicDecisionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) pomagajaca zdecydowac, czy DANA encja w projekcie powinna
         * byc bogata czy moze zostac anemiczna - laczac WSZYSTKIE zasady z tej
         * lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneRichDomainModelForLibrarySystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * bogaty model domenowy systemu bibliotecznego z min. 3 klasami
         * (np. `Book`, `Loan`, `Member`), KAZDA chroniaca WLASNE niezmienniki
         * przez metody biznesowe (np. `Loan.returnBook()` sprawdzajace czy juz
         * nie zwrocono, `Member.borrowBook()` sprawdzajace limit wypozyczen).
         * Zademonstruj PELNY, poprawny przeplyw ORAZ min. 2 proby naruszenia
         * niezmiennikow (zlapane).
         */
        public static void main(String[] args) { }
    }
}
