package com.example.javaquest._12_hibernate.Lesson26_PessimisticLocking;

public class _Exercises_Lesson26_PessimisticLocking {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicPessimisticWriteLock {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Seat (id, row, number, reserved) na H2
         * ("jdbc:h2:mem:l26ex01;DB_CLOSE_DELAY=-1"). Uzyj session.find(Seat.class,
         * id, LockModeType.PESSIMISTIC_WRITE) i sprawdz w show_sql, ze wygenerowal
         * sie SELECT ... FOR UPDATE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicPessimisticReadLock {
        /*
         * 🧪 Zadanie 2:
         * Uzyj LockModeType.PESSIMISTIC_READ zamiast WRITE - sprawdz w show_sql
         * roznice w wygenerowanym SQL (jesli H2 rozroznia FOR SHARE od FOR UPDATE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_LockTimeoutHintBasic {
        /*
         * 🧪 Zadanie 3:
         * Dodaj hint "jakarta.persistence.lock.timeout" (np. 500ms) do find() z
         * PESSIMISTIC_WRITE. Wywolaj na ISTNIEJACYM, niezablokowanym wierszu -
         * zweryfikuj, ze zadziala natychmiast (brak konfliktu = brak oczekiwania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_LockOnQueryResult {
        /*
         * 🧪 Zadanie 4:
         * Uzyj query.setLockMode(LockModeType.PESSIMISTIC_WRITE) na HQL zamiast
         * find() - zablokuj WSZYSTKIE wiersze pasujace do warunku WHERE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LockReleasedOnCommit {
        /*
         * 🧪 Zadanie 5:
         * Zdobadz blokade PESSIMISTIC_WRITE, wywolaj commit() - w KOLEJNEJ, NOWEJ
         * Session sprobuj zdobyc TA SAMA blokade - zweryfikuj, ze dziala natychmiast
         * (poprzednia blokada zostala zwolniona po commit).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LockReleasedOnRollback {
        /*
         * 🧪 Zadanie 6:
         * Zdobadz blokade, wywolaj rollback() (zamiast commit) - zweryfikuj, ze
         * blokada TEZ zostala zwolniona (rollback rowniez konczy transakcje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FindWithoutLockModeDefaultBehavior {
        /*
         * 🧪 Zadanie 7:
         * Wywolaj zwykle find() (bez LockModeType) i sprawdz w show_sql, ze NIE
         * wygenerowal sie "for update" - domyslnie find() NIE blokuje wiersza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_LockModeNoneExplicit {
        /*
         * 🧪 Zadanie 8:
         * Uzyj JAWNIE LockModeType.NONE (rownowazne zwyklemu find()) - zweryfikuj,
         * ze zachowanie jest IDENTYCZNE jak w Zadaniu 7.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintLockModeUsageTable {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wypisz WLASNA (nie kopiuj z lekcji) tabele porownawcza
         * PESSIMISTIC_READ vs PESSIMISTIC_WRITE vs NONE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TransactionRequiredForPessimisticLock {
        /*
         * 🧪 Zadanie 10:
         * Sprobuj wywolac find() z LockModeType.PESSIMISTIC_WRITE BEZ aktywnej
         * transakcji. Zapisz w komentarzu, czy i jaki blad Hibernate zwraca (blokady
         * wymagaja transakcji).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoThreadsSequentialLockAcquisition {
        /*
         * 🧪 Zadanie 11:
         * Uruchom DWA watki (jak w lekcji) - watek A zdobywa blokade i trzyma ja
         * przez 500ms, watek B probuje zdobyc TA SAMA blokade z hintem timeout=2000ms
         * (WYSTARCZAJACO dlugim) - zweryfikuj (zmierzonym czasem), ze B FAKTYCZNIE
         * czekal, a potem sie udalo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureActualWaitTime {
        /*
         * 🧪 Zadanie 12:
         * Zmierz DOKLADNY czas oczekiwania watku B z Zadania 11 (System.currentTimeMillis
         * przed i po find()) - zapisz w komentarzu, czy czas oczekiwania odpowiadal
         * mniej wiecej czasowi trzymania blokady przez watek A.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LockTimeoutTooShortBehaviorOnH2 {
        /*
         * 🧪 Zadanie 13:
         * Powtorz scenariusz z Zadania 11, ale z hintem timeout=50ms (KROTSZYM niz
         * czas trzymania blokady przez A). Zapisz w komentarzu FAKTYCZNIE
         * zaobserwowane zachowanie na H2 (patrz uwaga o MVCC z lekcji - moze sie
         * roznic od oczekiwan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PessimisticLockOnCollectionOfSeats {
        /*
         * 🧪 Zadanie 14:
         * Zablokuj WIELE wierszy naraz (np. wszystkie miejsca w konkretnym rzedzie)
         * przez query.setLockMode() na HQL zwracajacym liste - zweryfikuj, ze
         * WSZYSTKIE pasujace wiersze zostaly zablokowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ReservationSystemWithPessimisticLock {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj metode "reserveSeat(Session, Long seatId)" - blokuje miejsce
         * (PESSIMISTIC_WRITE), sprawdza czy juz zarezerwowane (jesli tak, rzuca
         * wyjatek), w przeciwnym razie ustawia reserved=true i commituje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConcurrentReservationAttempts {
        /*
         * 🧪 Zadanie 16:
         * Uruchom DWA watki jednoczesnie probujace zarezerwowac TO SAMO miejsce
         * przez reserveSeat() z Zadania 15 - zweryfikuj, ze TYLKO JEDEN watek
         * zarezerwowal je pomyslnie (dzieki blokadzie pesymistycznej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PessimisticLockScopeExtended {
        /*
         * 🧪 Zadanie 17:
         * Uzyj PessimisticLockScope.EXTENDED (jesli dostepne, hint jakarta.persistence
         * .lock.scope) - zapisz w komentarzu, na czym polega rozszerzenie zakresu
         * blokady na powiazane encje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefreshWithLockCombination {
        /*
         * 🧪 Zadanie 18:
         * Uzyj session.refresh(entity, LockModeType.PESSIMISTIC_WRITE) - odswiez
         * JUZ zaladowana encje ORAZ jednoczesnie zdobadz na niej blokade.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ComparePessimisticVsOptimisticForSameScenario {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj TA SAMA rezerwacje miejsca (Zadanie 15) RAZ przez
         * pesymistyczne blokowanie, RAZ przez optymistyczne (@Version, Lesson25) -
         * porownaj w komentarzu zachowanie przy wspolbieznych probach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LockAcquisitionOrderConsistency {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode blokujaca DWA rozne wiersze ZAWSZE w TEJ SAMEJ kolejnosci
         * (np. sortujac po Id PRZED zablokowaniem) - zapisz w komentarzu, jak to
         * ZAPOBIEGA deadlockom (odniesienie do _05_multithreading/Lesson25_Deadlock).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullTicketBookingSystemWithLocks {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY system rezerwacji biletow (Event, Seat) z metoda
         * reserveSeat() (Zadanie 15) - uruchom 5 watkow probujacych zarezerwowac
         * ROZNE miejsca jednoczesnie i zweryfikuj poprawnosc wszystkich rezerwacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DeadlockPreventionWithOrderedLocking {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj transfer miedzy DWOMA "kontami" (jak w Lesson08) z
         * blokowaniem OBU wierszy w USTALONEJ kolejnosci (Zadanie 20) - uruchom 2
         * watki wykonujace transfery w PRZECIWNYCH kierunkach i zweryfikuj BRAK deadlocka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LockTimeoutWithGracefulDegradation {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode probujaca zdobyc blokade z KROTKIM timeoutem, a w razie
         * niepowodzenia (LockTimeoutException) - zwracajaca uzytkownikowi CZYTELNY
         * komunikat "Spróbuj ponownie za chwile" zamiast surowego wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HighContentionStressTestPessimistic {
        /*
         * 🧪 Zadanie 24:
         * Uruchom 10 watkow PROBUJACYCH jednoczesnie zablokowac i zmodyfikowac TEN
         * SAM wiersz (z rozsadnym timeoutem i retry) - zmierz calkowity czas i
         * porownaj z rownowaznym testem optymistycznym (Lesson25/Zadanie 23).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PessimisticLockWithBusinessValidation {
        /*
         * 🧪 Zadanie 25:
         * Rozszerz reserveSeat() (Zadanie 15) o DODATKOWA walidacje biznesowa PO
         * zdobyciu blokady (np. sprawdzenie, czy wydarzenie NIE zostalo odwolane) -
         * zademonstruj poprawne dzialanie WEWNATRZ zablokowanej sekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareH2AndDocumentedPostgresBehavior {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: napisz porownanie (min. 4 punkty, w komentarzu) zachowania
         * blokad pesymistycznych na H2 (MVCC, zaobserwowane w tej lekcji) vs
         * DOKUMENTOWANEGO zachowania na PostgreSQL/MySQL (prawdziwe blokowanie
         * wierszy) - z odniesieniem do WLASNYCH obserwacji z Zadan 11-13.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LockEscalationDiscussion {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: wypisz wyjasnienie (min. 3 zdania) koncepcji "lock
         * escalation" (baza automatycznie zamienia wiele blokad wierszowych na
         * jedna blokade tabeli, gdy jest ich zbyt wiele) - i jak to moze wplynac na
         * system z wieloma jednoczesnymi rezerwacjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullReportPessimisticLockingArchitectureDecision {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz architektoniczna decyzje (min. 5 zdan, w
         * komentarzu) dla WYOBRAZONEGO systemu rezerwacji biletow lotniczych -
         * uzasadnij wybor pesymistycznego LUB optymistycznego blokowania (lub
         * hybrydy) z konkretnymi argumentami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnPessimisticLockingBestPractices {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy dobre praktyki pesymistycznego blokowania - krotkie
         * transakcje, stala kolejnosc blokowania (unikanie deadlockow), rozsadne timeouty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullPessimisticLockingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system rezerwacji biletow
         * laczacy: reserveSeat z walidacja biznesowa (Zadanie 25), zapobieganie
         * deadlockom (Zadanie 22), graceful degradation przy timeout (Zadanie 23), i
         * stress test wielu watkow (Zadanie 24).
         */
        public static void main(String[] args) { }
    }
}
