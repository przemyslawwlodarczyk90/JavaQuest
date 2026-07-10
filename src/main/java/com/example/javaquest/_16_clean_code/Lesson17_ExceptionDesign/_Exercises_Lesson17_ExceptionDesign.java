package com.example.javaquest._16_clean_code.Lesson17_ExceptionDesign;

public class _Exercises_Lesson17_ExceptionDesign {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainExceptionsVsErrorCodesInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), dlaczego wyjatki sa lepsze niz kody bledow (np. zwracanie
         * -1 lub null przy bledzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteMethodUsingErrorCodeThenRefactorToException {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `int findIndexErrorCode(int[] array, int target)`
         * zwracajaca -1, gdy elementu brak. Napisz DRUGA wersje
         * `findIndexException(...)` rzucajaca `NoSuchElementException`, gdy
         * elementu brak. Wywolaj obie dla brakujacego elementu i porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteVagueExceptionMessageThenImproveWithContext {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode walidujaca haslo (min. 8 znakow) rzucajaca wyjatek z
         * OGOLNIKOWYM komunikatem "Nieprawidlowe haslo". Popraw komunikat, by
         * zawieral DLUGOSC podanego hasla i wymagane minimum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DesignCustomExceptionNamedFromBusinessPerspective {
        /*
         * 🧪 Zadanie 4:
         * Zaprojektuj wyjatek `SeatAlreadyTakenException extends
         * RuntimeException` (dla systemu rezerwacji miejsc) - nazwa
         * odzwierciedla regule BIZNESOWA, nie szczegol techniczny. Rzuc go i
         * zlap w try-catch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteMethodCatchingTooBroadException {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode wywolujaca `String s = null; s.length();` (realny blad
         * programisty - NullPointerException) opakowana w `catch (Exception
         * e)`. Zaobserwuj, ze blad zostaje "po cichu" zlapany zamiast
         * naprawiony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FixTooBroadCatchWithSpecificType {
        /*
         * 🧪 Zadanie 6:
         * Napisz osobna metode z PRAWDZIWYM, oczekiwanym przypadkiem bledu
         * (np. `IllegalArgumentException` z walidacji) i zlap go KONKRETNYM
         * typem (`catch (IllegalArgumentException e)`) - w komentarzu wyjasnij
         * roznice wobec Zadania 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseExceptionForControlFlowAntiPattern {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode uzywajaca WLASNEGO wyjatku do "wyjscia" z zagniezdzonej
         * petli po znalezieniu wartosci (anty-wzorzec z teorii). Wywolaj ja i
         * zaobserwuj dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RefactorControlFlowExceptionToNormalReturn {
        /*
         * 🧪 Zadanie 8:
         * Zrefaktoryzuj metode z Zadania 7, zastepujac wyjatek zwyklym
         * `return`/`break` - zweryfikuj identyczny wynik, porownaj czytelnosc
         * i koszt (brak tworzenia wyjatku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteBigTryBlockWithMultipleOperations {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode z DUZYM blokiem try zawierajacym 3 rozne operacje
         * (parsowanie, walidacje, obliczenia) - jedna z nich rzuca wyjatek.
         * W komentarzu wskaz, dlaczego trudno okreslic KTORA operacja
         * faktycznie zawiodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RefactorBigTryBlockToSingleResponsibility {
        /*
         * 🧪 Zadanie 10:
         * Zrefaktoryzuj metode z Zadania 9, wydzielajac operacje do osobnych
         * metod tak, by blok `try` zawieral TYLKO 1 operacje. Zweryfikuj
         * identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignHierarchyOfDomainSpecificExceptions {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj hierarchie wyjatkow dla systemu bankowego: bazowy
         * `BankingException extends RuntimeException`, oraz 2 podklasy
         * `InsufficientFundsException` i `AccountNotFoundException`. Rzuc
         * kazdy i zlap najpierw KONKRETNYM typem, potem (osobno) wspolnym
         * typem bazowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TranslateLowLevelExceptionToDomainException {
        /*
         * 🧪 Zadanie 12:
         * Napisz "niskopoziomowa" metode rzucajaca wlasny
         * `RawStorageException` (symulujaca blad techniczny). Napisz warstwe
         * WYZSZA lapiaca ten wyjatek i rzucajaca `ProfileLoadException`
         * (domenowy) z ZACHOWANIEM oryginalnej przyczyny (`cause`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_VerifyCauseChainIsPreservedAfterTranslation {
        /*
         * 🧪 Zadanie 13:
         * Zlap wyjatek z Zadania 12 i wypisz JEGO komunikat ORAZ komunikat
         * `getCause()` - potwierdz, ze oryginalna, techniczna przyczyna nadal
         * jest dostepna mimo "przetlumaczenia" na wyjatek domenowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareCheckedVsUncheckedForSameScenario {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj TA SAMA walidacje (np. "nieprawidlowy format daty") jako
         * (a) checked exception `extends Exception`, (b) unchecked exception
         * `extends RuntimeException`. Napisz metody uzywajace obu wersji - w
         * komentarzu porownaj, jak zmienia sie sygnatura metod POSREDNICH
         * (czy musza deklarowac `throws`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DemonstrateOclpViolationCausedByCheckedException {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj lancuch 3 metod (A wola B wola C), gdzie C rzuca checked
         * exception - zaobserwuj, ze A i B TEZ musza dodac `throws` do swoich
         * sygnatur, mimo ze SAME nie obsluguja tego wyjatku. W komentarzu
         * polacz to z naruszeniem OCP (Lesson08).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorCheckedChainToUncheckedException {
        /*
         * 🧪 Zadanie 16:
         * Zrefaktoryzuj lancuch z Zadania 15, zamieniajac checked exception na
         * unchecked - pokaz, ze metody posrednie A i B NIE musza juz
         * deklarowac `throws`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignExceptionWithAdditionalContextFields {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj wyjatek `OrderValidationException` z DODATKOWYMI polami
         * (np. `String fieldName`, `Object invalidValue`) obok komunikatu -
         * rzuc go i w bloku catch wypisz WSZYSTKIE te pola osobno (nie tylko
         * `getMessage()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteMethodUsingExceptionForNormalFlowControl {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode parsujaca liste Stringow na liczby, ktora uzywa
         * wyjatku do "pomijania" niepoprawnych elementow zamiast normalnego
         * `if`/`continue` (anty-wzorzec). Wywolaj ja dla listy z 2 niepoprawnymi
         * elementami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorParsingMethodToAvoidControlFlowException {
        /*
         * 🧪 Zadanie 19:
         * Zrefaktoryzuj metode z Zadania 18, uzywajac zwyklego sprawdzenia
         * (np. regex lub `try/catch` TYLKO wokol prawdziwego parsowania, bez
         * wlasnego wyjatku "sterujacego") - zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealMethodFromEarlierChapterForExceptionDesignQuality {
        /*
         * 🧪 Zadanie 20:
         * Wybierz 1 metode z dowolnej wczesniejszej lekcji kursu (np.
         * `_09_jdbc` lub `_04_io`) uzywajaca wyjatkow. W komentarzu oceń ja wg
         * WSZYSTKICH 8 zasad z tej lekcji - dla kazdej PASS/FAIL z
         * uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignCompleteExceptionHierarchyForEcommerceSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletna hierarchie wyjatkow dla systemu e-commerce:
         * bazowy `EcommerceException`, oraz min. 4 podklasy pokrywajace rozne
         * kategorie bledow (platnosc, magazyn, dostawa, walidacja) -
         * zademonstruj rzucanie i lapanie kazdej, ORAZ lapanie wszystkich
         * naraz przez typ bazowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildMultiLayerExceptionTranslationPipeline {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj 3-warstwowy lancuch tlumaczenia wyjatkow: warstwa
         * "infrastruktury" rzuca techniczny wyjatek, warstwa "domenowa"
         * tlumaczy go na wyjatek biznesowy, warstwa "aplikacji" lapie
         * finalny wyjatek i wypisuje komunikat DLA UZYTKOWNIKA (bez szczegolow
         * technicznych) ORAZ osobno PELNY lancuch przyczyn (dla logow/debugowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignRetryableVsNonRetryableExceptionDistinction {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj 2 wyjatki: `RetryableOperationException` (blad
         * przejsciowy - np. timeout sieciowy, warto ponowic) i
         * `NonRetryableOperationException` (blad trwaly - np. dane
         * niepoprawne, ponawianie nic nie da). Napisz metode symulujaca
         * operacje rzucajaca losowo jeden z nich i logike decydujaca, czy
         * ponowic probe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRetryLoopUsingExceptionDistinctionFromExercise23 {
        /*
         * 🧪 Zadanie 24:
         * Korzystajac z rozwiazania z Zadania 23, zaimplementuj petle
         * ponawiajaca (max 3 proby) operacje TYLKO dla
         * RetryableOperationException - dla NonRetryableOperationException
         * petla ma NATYCHMIAST przerwac i przekazac blad dalej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignValidationExceptionCollectingMultipleErrors {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj wyjatek `MultiFieldValidationException` przechowujacy
         * LISTE bledow (np. `List<String> fieldErrors`) zamiast tylko 1
         * komunikatu - napisz metode walidujaca formularz z 3 polami,
         * ZBIERAJACA wszystkie bledy naraz (a nie przerywajaca na pierwszym) i
         * rzucajaca 1 wyjatek z pelna lista na koncu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareFailFastVsCollectAllErrorsStrategies {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj TA SAMA walidacje formularza (3 pola) na 2 sposoby: (a)
         * fail-fast (przerywa na PIERWSZYM bledzie), (b) collect-all (zbiera
         * WSZYSTKIE bledy z Zadania 25). W komentarzu porownaj, kiedy ktora
         * strategia jest lepsza z perspektywy uzytkownika koncowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignExceptionSafeResourceCleanupWithTryWithResources {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj wlasna klase implementujaca `AutoCloseable` (np.
         * `FakeConnection`) rzucajaca wyjatek W TRAKCIE dzialania - uzyj jej w
         * `try-with-resources` (poznanym w `_04_io/Lesson13_TryWithResources`)
         * i zademonstruj, ze `close()` jest wywolane MIMO wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildGlobalStyleExceptionLoggingBoundary {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj metode `runApplicationBoundary(Runnable action)`
         * symulujaca "granice aplikacji" - lapie WSZYSTKIE wyjatki wychodzace
         * z `action` (jedyne uzasadnione miejsce na szeroki `catch`), loguje
         * je (println z pelnym typem i komunikatem) i NIE pozwala programowi
         * sie wywalic. Przetestuj z akcja rzucajaca rozne typy wyjatkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveExceptionDesignChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do oceny projektu wyjatkow w kodzie - laczac WSZYSTKIE
         * zasady z tej lekcji (kody bledow vs wyjatki, checked vs unchecked,
         * kontekst w komunikacie, perspektywa wolujacego, tlumaczenie,
         * kontrola przeplywu, szerokosc catch, wielkosc bloku try).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullyDesignedExceptionSystemForBookingApp {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny system
         * obslugi bledow dla aplikacji rezerwacji wydarzen, stosujacy
         * WSZYSTKIE zasady z tej lekcji naraz: hierarchia wyjatkow domenowych
         * (unchecked), komunikaty z pelnym kontekstem, tlumaczenie wyjatkow
         * niskopoziomowych na domenowe z zachowaniem cause, male bloki try o
         * pojedynczej odpowiedzialnosci, wezskie catch, oraz 1 "granica
         * aplikacji" lapiaca wszystko na samej gorze. Zademonstruj pelny
         * przeplyw z co najmniej 2 roznymi scenariuszami bledow.
         */
        public static void main(String[] args) { }
    }
}
