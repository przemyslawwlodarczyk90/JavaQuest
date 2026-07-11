package com.example.javaquest._20_spring_core.Lesson20_ApplicationEvents;

public class _Exercises_Lesson20_ApplicationEvents {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPublishSubscribePattern {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wzorzec publish/subscribe wlasnymi
         * slowami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnEventExtendingApplicationEvent {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNE zdarzenie dziedziczace po `ApplicationEvent`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementListenerViaApplicationListenerInterface {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj listener przez interfejs `ApplicationListener<T>`
         * dla zdarzenia z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementListenerViaEventListenerAnnotation {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj TEN SAM listener przez `@EventListener` (metoda,
         * NIE interfejs) - porownaj skladnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_PublishEventAndVerifyListenerReacts {
        /*
         * 🧪 Zadanie 5:
         * Opublikuj zdarzenie z Zadania 2 - zweryfikuj, ze listener
         * ZAREAGOWAL (np. zwiekszyl licznik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementPlainPojoEvent {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj zdarzenie jako ZWYKLY rekord (BEZ dziedziczenia
         * po `ApplicationEvent`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyApplicationListenerCannotHandlePojoEvent {
        /*
         * 🧪 Zadanie 7:
         * Sprobuj uzyc `ApplicationListener<T>` (interfejs) dla zdarzenia
         * z Zadania 6 - zaobserwuj BLAD KOMPILACJI i wyjasnij dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddSecondListenerWithoutModifyingPublisher {
        /*
         * 🧪 Zadanie 8:
         * Dodaj DRUGI listener dla ISTNIEJACEGO zdarzenia - zweryfikuj,
         * ze publisher NIE WYMAGA zadnej zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyEventsReduceCoupling {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego zdarzenia REDUKUJA sprzezenie
         * (coupling) miedzy klasami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRealWorldEventExamples {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: podaj 3 REALNE przyklady zdarzen biznesowych z
         * typowej aplikacji e-commerce.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementConditionalEventListener {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@EventListener(condition = "...")` (wyrazenie SpEL,
         * powiaz z `Lesson17_SpelBasics`) - listener reaguje TYLKO gdy
         * warunek jest spelniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementOrderedMultipleListeners {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@Order` na WIELU listenerach TEGO SAMEGO zdarzenia -
         * zweryfikuj wymuszona kolejnosc wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementListenerPublishingAnotherEvent {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj listener, ktory W ODPOWIEDZI publikuje KOLEJNE,
         * INNE zdarzenie - zbuduj "lancuch" 3 zdarzen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareSynchronousEventPublishingTiming {
        /*
         * 🧪 Zadanie 14:
         * Zweryfikuj (printy z timestampami), ze domyslnie
         * `publishEvent()` jest SYNCHRONICZNE - kod PO wywolaniu czeka,
         * az WSZYSCY listenerzy skoncza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementAsyncEventListenerWithExecutor {
        /*
         * 🧪 Zadanie 15:
         * Skonfiguruj `ApplicationEventMulticaster` z `Executor`-em (lub
         * uzyj `@Async`, jesli dostepne) - zweryfikuj ASYNCHRONICZNE
         * wykonanie listenera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementEventHierarchyWithInheritance {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj hierarchie zdarzen (bazowe `OrderEvent`,
         * podklasy `OrderPlacedEvent`/`OrderCancelledEvent`) - listener na
         * BAZOWYM typie lapie WSZYSTKIE podklasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementTransactionalEventListenerConceptually {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala (lub eksperymentalnie): sprawdz `@TransactionalEventListener` -
         * jak rozni sie od zwyklego `@EventListener` (zapowiedz
         * `_23_spring_data_jpa/Lesson08_TransactionsInSpring`)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementEventBasedAuditLog {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj listener nasluchujacy WSZYSTKICH zdarzen
         * (`ApplicationListener<ApplicationEvent>` lub `Object` w
         * `@EventListener`) i zapisujacy je do listy "dziennika audytu" -
         * powiaz z `_19_security_basics/Lesson19`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HandleExceptionThrownInsideListener {
        /*
         * 🧪 Zadanie 19:
         * Rzuc wyjatek WEWNATRZ JEDNEGO listenera (majac WIELU listenerow
         * dla tego samego zdarzenia) - zweryfikuj, czy POZOSTALI listenerzy
         * TEZ sie wykonuja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildEventCatalogDocumentation {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) "katalog zdarzen" WLASNEJ aplikacji -
         * nazwa zdarzenia, publisher, WSZYSCY subskrybenci - jako
         * dokumentacja architektury zdarzeniowej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomApplicationEventMulticaster {
        /*
         * 🧪 Zadanie 21:
         * Zarejestruj WLASNY `ApplicationEventMulticaster` (bean o nazwie
         * `applicationEventMulticaster`) - dostosuj strategie dostarczania
         * zdarzen (np. z wlasnym Executor-em).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementEventDrivenSagaPattern {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj uproszczony wzorzec "Saga" - lancuch 4+ zdarzen
         * reprezentujacych kroki DLUGOTRWALEJ operacji biznesowej (np.
         * proces zamowienia: zlozenie->platnosc->wysylka->dostawa), z
         * mozliwoscia "cofniecia" (kompensacji) przy bledzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementEventSourcingLiteWithReplayCapability {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj UPROSZCZONY "event sourcing" - WSZYSTKIE zdarzenia
         * zapisywane do listy, stan obiektu ODTWARZANY przez "replay"
         * zdarzen OD ZERA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareEventDrivenWithDirectCallsInModularMonolith {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: powiaz z `_17_architecture/Lesson17-18` -
         * zaprojektuj (opisz), KIEDY w modularnym monolicie uzyc zdarzen,
         * a KIEDY bezposredniego wywolania publicznego API modulu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDeadLetterHandlingForFailedListeners {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj mechanizm "dead letter" - jesli listener rzuci
         * wyjatek, zdarzenie trafia do OSOBNEJ listy "nieudanych" do
         * pozniejszej analizy/ponowienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureEventPublishingOverheadAtScale {
        /*
         * 🧪 Zadanie 26:
         * Zmierz narzut publikowania 100 000 zdarzen z 5 listenerami
         * kazdy - porownaj z 100 000 bezposrednich wywolan metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementEventFilteringPipeline {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj "potok" (pipeline) listenerow - PIERWSZY filtruje/
         * wzbogaca zdarzenie i publikuje NOWE, DERYWOWANE zdarzenie dla
         * KOLEJNEGO listenera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCrossModuleEventContractVersioning {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (zaimplementuj) wersjonowanie "kontraktu" zdarzenia
         * (np. `OrderPlacedEventV2` z dodatkowym polem) - zweryfikuj, ze
         * STARY listener (V1) nadal dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareInProcessEventsWithMessageQueueConceptually {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj zdarzenia Springa (W JEDNYM procesie)
         * z kolejka wiadomosci (Kafka/RabbitMQ, MIEDZY procesami/
         * serwisami) - kiedy WEWNETRZNE zdarzenia JUZ NIE WYSTARCZAJA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteEventDrivenOrderSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, zdarzeniowy system zamowien - min. 3 zdarzenia,
         * 5+ listenerow (powiadomienia, magazyn, audyt, fakturowanie),
         * obsluga bledow (Zadanie 25) - zweryfikuj co najmniej 4
         * scenariusze end-to-end.
         */
        public static void main(String[] args) { }
    }
}
