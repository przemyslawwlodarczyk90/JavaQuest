package com.example.javaquest._17_architecture.Lesson18_EventDrivenCommunicationBetweenModules;

public class _Exercises_Lesson18_EventDrivenCommunicationBetweenModules {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDirectCallVsEventInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), kiedy uzyc bezposredniego wywolania (Lesson17), a kiedy
         * zdarzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DesignSimpleDomainEventRecord {
        /*
         * 🧪 Zadanie 2:
         * Zaprojektuj `record UserRegistered(String userId, String email)` -
         * to prosty, niezmienny fakt biznesowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementSimpleInMemoryEventBus {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj (lub skopiuj wzorem teorii) `InMemoryEventBus` z
         * metodami `subscribe`/`publish`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PublishEventFromOneModule {
        /*
         * 🧪 Zadanie 4:
         * Zaprojektuj modul "Users" publikujacy `UserRegistered` (Zadanie 2)
         * po "rejestracji" uzytkownika - BEZ zadnego subskrybenta jeszcze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SubscribeOneListenerToEvent {
        /*
         * 🧪 Zadanie 5:
         * Dodaj 1 subskrybenta (np. "Welcome Email Sender") do zdarzenia z
         * Zadania 4 - zademonstruj reakcje na publikacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddSecondListenerWithoutChangingPublisher {
        /*
         * 🧪 Zadanie 6:
         * Dodaj DRUGIEGO subskrybenta (np. "Analytics") do TEGO SAMEGO
         * zdarzenia - zweryfikuj, ze modul publikujacy (Zadanie 4) NIE
         * zostal zmieniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyWhenDirectCallIsRequiredInsteadOfEvent {
        /*
         * 🧪 Zadanie 7:
         * Dany jest scenariusz: "modul Zamowien MUSI wiedziec, czy platnosc
         * sie powiodla, ZANIM potwierdzi zamowienie klientowi". W komentarzu
         * wyjasnij, dlaczego TU potrzebne jest bezposrednie wywolanie, nie
         * zdarzenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyWhenEventIsAppropriateInsteadOfDirectCall {
        /*
         * 🧪 Zadanie 8:
         * Dany jest scenariusz: "po udanej platnosci, wyslij powiadomienie
         * e-mail (opcjonalne, moze poczekac)". W komentarzu wyjasnij,
         * dlaczego TU zdarzenie jest lepszym wyborem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainEventualConsistencyTradeoffInOwnWords {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij (min. 3 zdania) czym jest "spojnosc
         * ostateczna" (eventual consistency) i dlaczego komunikacja
         * zdarzeniowa jej NIE UNIKA (to kompromis, nie darmowa korzysc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListEventDrivenCommunicationBenefitsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * korzysci komunikacji zdarzeniowej miedzy modulami z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignMultipleEventTypesWithDifferentSubscribers {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj 2 ROZNE zdarzenia (`OrderPlaced`, `OrderCancelled`) z
         * ROZNYMI subskrybentami dla kazdego - zademonstruj niezalezna
         * obsluge obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DesignOneEventWithMultipleIndependentSubscribers {
        /*
         * 🧪 Zadanie 12:
         * Zaprojektuj 1 zdarzenie z 3 NIEZALEZNYMI subskrybentami (np.
         * Notifications, Analytics, Loyalty Points) - zademonstruj, ze
         * KAZDY reaguje NIEZALEZNIE, bez wiedzy o pozostalych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignAdrForChoosingEventDrivenOverDirectCallForNotifications {
        /*
         * 🧪 Zadanie 13:
         * Napisz PELNY ADR (`Lesson02`) uzasadniajacy wybor komunikacji
         * zdarzeniowej (zamiast bezposredniego wywolania) dla powiadomien -
         * z kontekstem (np. "chcemy dodawac nowe kanaly powiadomien bez
         * zmiany modulu Zamowien") i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateSubscriberFailureNotBreakingPublisher {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj subskrybenta, ktory RZUCA wyjatek podczas obslugi
         * zdarzenia - w komentarzu opisz (i ewentualnie zaimplementuj w
         * `InMemoryEventBus`) jak sprawic, by BLAD 1 subskrybenta nie
         * przerywal obslugi POZOSTALYCH subskrybentow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignCacheInvalidationListenerFromLesson14Preview {
        /*
         * 🧪 Zadanie 15:
         * Polacz z `_17_architecture/Lesson14` (CachingArchitecture) -
         * zaprojektuj subskrybenta zdarzenia `ProductUpdated`, ktory
         * INWALIDUJE wpis cache'a (zamiast wywolania tego wprost w Use
         * Case, jak sugerowalo cwiczenie 26 tamtej lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareDirectCallVsEventDrivenForSameFeature {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj TA SAMA funkcjonalnosc ("po zlozeniu zamowienia,
         * zaktualizuj punkty lojalnosciowe") na 2 sposoby: (a) bezposrednie
         * wywolanie modulu Lojalnosc, (b) zdarzenie. W komentarzu porownaj
         * sprzezenie miedzy modulami w obu wersjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignEventPayloadContainingOnlyNecessaryData {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj zdarzenie z NADMIAROWYMI danymi (np. cala encje Order
         * zamiast tylko potrzebnych pol) - w komentarzu wyjasnij (por.
         * Lesson07: DTO), dlaczego zdarzenie powinno zawierac TYLKO
         * niezbedne dane, nie cala encje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorOverloadedEventPayloadFromExercise17 {
        /*
         * 🧪 Zadanie 18:
         * Popraw zdarzenie z Zadania 17, ograniczajac payload do
         * NIEZBEDNYCH pol (jak `OrderPlaced` w teorii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignEventDrivenFlowSpanningThreeModules {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj przeplyw: modul A publikuje zdarzenie, modul B
         * REAGUJE i SAM publikuje KOLEJNE zdarzenie (lancuch zdarzen), modul
         * C subskrybuje TO drugie zdarzenie - zademonstruj pelny lancuch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCodeFromCleanCodeChapterForObserverPatternSimilarity {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) `_02_oop/Lesson15_DesignPatterns` (jesli
         * zawiera wzorzec Observer) - jak sie ma do
         * `InMemoryEventBus`/publish-subscribe z tej lekcji? Czym się
         * rozni zastosowanie MIEDZY MODULAMI od zastosowania wewnatrz 1 klasy?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullEventDrivenArchitectureForECommercePlatform {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny modularny monolit (Lesson17) z 4 modulami
         * komunikujacymi sie GLOWNIE przez zdarzenia (min. 3 rozne typy
         * zdarzen, min. 5 subskrybentow razem) - zademonstruj pelny
         * przeplyw "zlozenie zamowienia" wywolujacy KASKADE reakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CombineDirectCallsAndEventsInSameSystem {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, dodaj RowNIEZ 1-2 bezposrednie wywolania
         * (Lesson17) tam, gdzie modul POTRZEBUJE natychmiastowej odpowiedzi -
         * zademonstruj SWIADOME laczenie obu stylow komunikacji w 1 systemie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignEventOrderingAndIdempotencyConsiderations {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj subskrybenta, ktory MOZE otrzymac TO SAMO zdarzenie
         * WIELOKROTNIE (symulowane celowym podwojnym `publish`) - zaprojektuj
         * go tak, by byl IDEMPOTENTNY (podwojne przetworzenie nie powoduje
         * podwojnego efektu, np. wysylki 2 e-maili).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildEventBusWithErrorIsolationBetweenSubscribers {
        /*
         * 🧪 Zadanie 24:
         * Rozszerz `InMemoryEventBus` o pelna izolacje bledow miedzy
         * subskrybentami (try-catch WOKOL kazdego wywolania handlera,
         * logowanie bledu zamiast przerwania) - zademonstruj z 3
         * subskrybentami, z ktorych SRODKOWY rzuca wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignAdrForTransitioningFromDirectCallsToEventsAcrossSystem {
        /*
         * 🧪 Zadanie 25:
         * Napisz PELNY ADR dokumentujacy STOPNIOWA migracje istniejacego
         * systemu z bezposrednich wywolan (Lesson17) na komunikacje
         * zdarzeniowa TAM, gdzie to zasadne - z planem (podobnym do
         * Strangler Fig, `_16_clean_code/Lesson21`) migracji 1 interakcji na
         * raz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignEventSourcingStyleAuditLogAsSideEffectOfSubscriber {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj subskrybenta "Audit Log", ktory zapisuje WSZYSTKIE
         * zdarzenia (dowolnego typu) do wspolnej listy - zademonstruj
         * pelna historie zdarzen po serii operacji na 2 roznych modulach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareInProcessEventBusWithRealMessageBrokerConceptually {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: napisz w komentarzu (min. 4 zdania) porownanie
         * `InMemoryEventBus` z tej lekcji z prawdziwym brokerem wiadomosci
         * (np. Kafka/RabbitMQ) - co ZOSTAJE takie samo (architektura), a co
         * SIE ZMIENIA (trwalosc, dostarczenie miedzy procesami, kolejnosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignEventDrivenSagaForCrossModuleConsistencyPreview {
        /*
         * 🧪 Zadanie 28:
         * Polacz z `_17_architecture/Lesson13` (Zadanie 23: Saga) -
         * zaprojektuj lancuch zdarzen realizujacy "kompensacje" (np.
         * "PaymentFailed" powoduje publikacje "OrderCancelled", ktora z
         * kolei zwalnia zarezerwowany stan magazynowy) - w PELNI przez
         * zdarzenia, bez rozproszonej transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveEventDrivenArchitectureChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 7
         * punktow) do projektowania komunikacji zdarzeniowej miedzy
         * modulami - laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullEventDrivenModularMonolithForRidesharingApp {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny
         * modularny monolit (Lesson17) dla aplikacji przewozu osob z min. 5
         * modulami komunikujacymi sie GLOWNIE przez zdarzenia (min. 4 typy
         * zdarzen), Z ODPOWIEDNIM uzyciem bezposrednich wywolan TAM, gdzie
         * potrzebna jest natychmiastowa odpowiedz, PELNA izolacja bledow
         * miedzy subskrybentami, oraz min. 1 lancuch zdarzen (subskrybent
         * publikujacy kolejne zdarzenie). Zademonstruj pelny przeplyw
         * "zamowienie przejazdu -> dopasowanie kierowcy -> platnosc ->
         * ocena" jako kaskade zdarzen.
         */
        public static void main(String[] args) { }
    }
}
