package com.example.javaquest._13_libraries.Lesson17_MdcAndLoggingBestPractices;

public class _Exercises_Lesson17_MdcAndLoggingBestPractices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicMdcPutGet {
        /*
         * 🧪 Zadanie 1:
         * Wywolaj MDC.put("sessionId", "SES-1"), wypisz MDC.get("sessionId")
         * w konsoli, potem MDC.remove("sessionId") i ponownie wypisz wynik
         * MDC.get("sessionId") (powinien byc null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MdcClearMultipleKeys {
        /*
         * 🧪 Zadanie 2:
         * Ustaw TRZY klucze w MDC ("requestId", "userId", "sessionId"),
         * wypisz wszystkie trzy przez MDC.get(...), wywolaj MDC.clear() i
         * wypisz je ponownie - zweryfikuj ze WSZYSTKIE sa teraz null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PatternWithSingleMdcKey {
        /*
         * 🧪 Zadanie 3:
         * Zaladuj (przez JoranConfigurator, wzorem z teorii) config
         * ConsoleAppender z wzorcem "[%X{orderId}] %msg%n". Ustaw
         * MDC.put("orderId", "ORD-9") i zaloguj 2 komunikaty INFO -
         * zweryfikuj, ze "ORD-9" pojawia sie w nawiasie przy obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_EmptyMdcPlaceholder {
        /*
         * 🧪 Zadanie 4:
         * Uzywajac tego samego wzorca co w Zadaniu 3, zaloguj komunikat
         * BEZ wczesniejszego MDC.put(...) - zaobserwuj, ze %X{orderId}
         * wstawia PUSTY string (nie "null", nie blad).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryFinallyPattern {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode processTask(String taskId) uzywajaca
         * try { MDC.put(...); ... } finally { MDC.clear(); } - w bloku try
         * zaloguj 2 komunikaty INFO. Wywolaj ja dla "TASK-1" i "TASK-2" i
         * zweryfikuj, ze po KAZDYM wywolaniu MDC.get("taskId") zwraca null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ParameterizedVsConcatenationRecap {
        /*
         * 🧪 Zadanie 6:
         * Przypomnienie z Lekcji 15: przepisz ponizszy log na wersje
         * SPARAMETRYZOWANA:
         *   String productName = "Laptop"; int quantity = 3;
         *   logger.info("Zamowiono " + productName + " w ilosci " + quantity);
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_LevelSelectionScenarios {
        /*
         * 🧪 Zadanie 7:
         * Dla KAZDEGO z 4 scenariuszy przypisz WLASCIWY poziom logowania i
         * zaimplementuj odpowiedni wpis: (a) aplikacja wystartowala,
         * (b) baza danych niedostepna, operacja PRZERWANA, (c) uzyto
         * wartosci domyslnej configu bo brakowalo pliku, (d) wypisanie
         * wartosci zmiennej petli przy bardzo szczegolowym debugowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NeverLogPasswordDemo {
        /*
         * 🧪 Zadanie 8:
         * Dany kod logujacy haslo: logger.info("login={} haslo={}", login, password) -
         * przepisz go na BEZPIECZNA wersje, ktora NIE ujawnia hasla w
         * logu (zaloguj tylko login).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExceptionAsLastArgument {
        /*
         * 🧪 Zadanie 9:
         * W bloku try-catch wywolaj Integer.parseInt("xyz") (rzuci wyjatek)
         * i zaloguj go POPRAWNIE - logger.error("Nie udalo sie sparsowac
         * liczby", exception) - throwable jako OSTATNI argument, NIE
         * konkatenacja getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MdcVsMessageSummary {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu wyjasnij (min. 4 zdania) roznice
         * miedzy przekazaniem danych KONTEKSTOWYCH przez MDC a wpisaniem
         * ich wprost w tresc komunikatu - kiedy wybrac ktore podejscie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChildThreadDoesNotSeeMdc {
        /*
         * 🧪 Zadanie 11:
         * Wzorujac sie na demo z teorii: ustaw MDC.put("traceId", "T-1") w
         * watku glownym, odpal DAEMON watek potomny (joinowany z timeoutem
         * 2 sekund) ktory wypisuje MDC.get("traceId") - zweryfikuj, ze
         * wynik to null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ManualMdcPropagationToChild {
        /*
         * 🧪 Zadanie 12:
         * Napraw Zadanie 11: uzyj MDC.getCopyOfContextMap() w watku glownym
         * i MDC.setContextMap(kopia) w watku potomnym (z MDC.clear() w
         * finally watku potomnego) - zweryfikuj, ze tym razem watek
         * potomny widzi "T-1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleMdcKeysInPattern {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj wzorzec z TRZEMA tokenami MDC naraz: "[req=%X{requestId}
         * user=%X{userId} tenant=%X{tenantId}] %msg%n" - ustaw wszystkie
         * trzy klucze i zaloguj 3 komunikaty, zmieniajac za kazdym razem
         * WARTOSC jednego z kluczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SimulateHttpRequestPipeline {
        /*
         * 🧪 Zadanie 14:
         * Zasymuluj obsluge 3 "requestow" (REQ-A, REQ-B, REQ-C) w petli -
         * kazdy z WLASNYM MDC.put("requestId", ...), logujacy 2 komunikaty
         * INFO wewnatrz try/finally z MDC.clear() - zweryfikuj wzrokowo,
         * ze kazdy log ma POPRAWNY requestId (bez "przeciekania" miedzy
         * iteracjami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LevelGuidanceTableImplementation {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj metode logAllLevelsWithComment() wywolujaca
         * logger na wszystkich 5 poziomach, KAZDA linijka z komentarzem
         * tuz nad nia wyjasniajacym, DLACZEGO wybrano ten a nie inny
         * poziom dla danego (wymyslonego) scenariusza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ThreadPoolMdcLeakRisk {
        /*
         * 🧪 Zadanie 16:
         * Uzywajac jednowatkowego ExecutorService (patrz _05_multithreading),
         * wykonaj DWA zadania (Runnable) POD RZAD na tym samym watku puli:
         * pierwsze ustawia MDC ale CELOWO NIE czysci go (brak finally),
         * drugie NIE ustawia MDC wcale, tylko je odczytuje - zaobserwuj
         * "wyciek" wartosci z pierwszego zadania do drugiego. Zamknij
         * ExecutorService poprawnie (shutdown + awaitTermination).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ThreadPoolMdcLeakFixed {
        /*
         * 🧪 Zadanie 17:
         * Napraw Zadanie 16 - dodaj MDC.clear() w finally KAZDEGO zadania -
         * zweryfikuj, ze drugie zadanie NIE widzi juz wartosci z pierwszego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExceptionChainWithLogger {
        /*
         * 🧪 Zadanie 18:
         * Nawiazujac do _01_fundamentals/Lesson16_Exceptions: rzuc wlasny
         * wyjatek z "cause" (RuntimeException opakowujacy
         * NumberFormatException) i zaloguj go logger.error(msg, exception) -
         * zweryfikuj w konsoli, ze widac OBA wyjatki ("Caused by:").
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MdcWithLoopedBusinessOperation {
        /*
         * 🧪 Zadanie 19:
         * Zasymuluj przetwarzanie listy 5 zamowien (kazde z wlasnym ID) -
         * dla kazdego: MDC.put("orderId", id) w try, logger.info na
         * poczatku i koncu przetwarzania, MDC.clear() w finally. Dodaj
         * config z %X{orderId} w patternie i zweryfikuj poprawnosc dla
         * wszystkich 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BadVsGoodLoggingSideBySide {
        /*
         * 🧪 Zadanie 20:
         * Dla scenariusza "rejestracja uzytkownika" napisz DWIE wersje tej
         * samej logiki logowania - ZLA (konkatenacja, brak MDC, haslo w
         * logu, throwable przez getMessage()) i DOBRA (sparametryzowane,
         * MDC, bez hasla, throwable jako argument) - uruchom obie i
         * porownaj output.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RequestScopedMdcHelper {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj klase pomocnicza "RequestContext" z metoda
         * statyczna "runWithContext(String requestId, Runnable action)",
         * ktora ustawia MDC, wywoluje action.run() w try, i CZYSCI MDC w
         * finally (nawet gdy action rzuci wyjatek) - przetestuj z
         * Runnable rzucajacym RuntimeException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MdcCopyAcrossThreadPoolTasks {
        /*
         * 🧪 Zadanie 22:
         * Uzywajac ExecutorService (min. 2 watki), zlec 4 zadania - w
         * watku GLOWNYM ustaw MDC PRZED zleceniem kazdego zadania,
         * skopiuj kontekst (MDC.getCopyOfContextMap()) i przekaz go do
         * kazdego zadania osobno (np. przez opakowanie Runnable) - w
         * kazdym zadaniu ustaw skopiowany kontekst, zaloguj, wyczysc.
         * Zamknij pule poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MdcContextAwareRunnableWrapper {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode "Runnable withCurrentMdc(Runnable task)" zwracajaca
         * NOWY Runnable, ktory PRZED wykonaniem task.run() ustawia
         * skopiowany kontekst MDC z watku wywolujacego, a PO wykonaniu go
         * czysci (finally) - uzyj jej do opakowania zadania zlecanego do
         * nowego watku i zweryfikuj dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SecretMaskingUtility {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode "String mask(String secret)" ktora zwraca
         * zamaskowana wersje sekretu (np. tylko ostatnie 2 znaki widoczne,
         * reszta jako '*') - uzyj jej przy logowaniu numeru karty
         * platniczej "4111111111111234" (NIE loguj pelnego numeru NIGDY,
         * nawet w wersji "do testow").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LogLevelDecisionAudit {
        /*
         * 🧪 Zadanie 25:
         * Dany zestaw 8 fikcyjnych komunikatow logu BEZ przypisanego
         * poziomu (w komentarzu) - dla KAZDEGO zdecyduj i zaimplementuj
         * WLASCIWY poziom, uzasadniajac wybor krotkim komentarzem przy
         * kazdej linijce (min. 8 wywolan logger.X(...) razem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_StructuredContextVsStringBuilding {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj DWIE wersje logowania tego samego zdarzenia
         * biznesowego (zlozenie zamowienia z 5 atrybutami: orderId,
         * userId, amount, currency, status) - jedna wpisujaca WSZYSTKO w
         * tresc komunikatu, druga uzywajaca MDC dla WSZYSTKICH 5 pol
         * (tresc komunikatu zostaje krotka: "Zamowienie utworzone") -
         * porownaj czytelnosc obu podejsc w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExceptionLoggingInDeepCallStack {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj 3-warstwowy stos wywolan (metoda A wywoluje B, B wywoluje
         * C), gdzie C rzuca wyjatek - zloguj go WYLACZNIE w warstwie A
         * (throwable jako ostatni argument), NIE w B ani C (unikanie
         * podwojnego/wielokrotnego logowania tego samego wyjatku - typowy
         * blad w wiekszych aplikacjach) - w komentarzu wyjasnij, dlaczego
         * logowanie w kazdej warstwie byloby zle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullMdcAndLevelsIntegration {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj kompletny scenariusz obslugi zamowienia
         * e-commerce: MDC z requestId+userId na starcie, logi na
         * WLASCIWYCH poziomach na kazdym etapie (walidacja=DEBUG,
         * przyjecie zamowienia=INFO, brak stanu magazynowego=WARN, blad
         * platnosci=ERROR z throwable), config z %X{requestId} %X{userId}
         * w patternie, MDC.clear() w finally.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MdcOverheadBenchmark {
        /*
         * 🧪 Zadanie 29:
         * Zmierz narzut czasowy 100 000 wywolan logger.debug("{}", i) BEZ
         * MDC vs 100 000 wywolan Z aktywnym MDC (3 klucze ustawione) -
         * poziom DEBUG WYLACZONY w configu w obu przypadkach - wypisz oba
         * czasy w ms i skomentuj, czy MDC dodaje zauwazalny narzut, gdy
         * poziom loga jest wylaczony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_LoggingPolicyCapstone {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i ZAIMPLEMENTUJ kompletna
         * "polityke logowania" dla fikcyjnego systemu bankowego -
         * scenariusz: przelew miedzy kontami. Wymagania: MDC z
         * transactionId+accountId, WSZYSTKIE poziomy uzyte poprawnie wedlug
         * "reguly kciuka" z teorii, ZERO sekretow/PII w logach (numer konta
         * zamaskowany), throwable zawsze jako ostatni argument przy
         * bledach, komunikaty WYLACZNIE sparametryzowane. Zademonstruj
         * przeplyw: rozpoczecie -> walidacja -> (symulowany blad
         * niewystarczajacych srodkow) -> zakonczenie z bledem.
         */
        public static void main(String[] args) { }
    }
}
