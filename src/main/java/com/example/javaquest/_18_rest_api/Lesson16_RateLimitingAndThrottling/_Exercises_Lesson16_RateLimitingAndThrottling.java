package com.example.javaquest._18_rest_api.Lesson16_RateLimitingAndThrottling;

public class _Exercises_Lesson16_RateLimitingAndThrottling {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyRateLimitingIsNeeded {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 3 powody, dla ktorych API powinno miec
         * rate limiting - po 1 zdaniu uzasadnienia kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementSimpleRequestCounter {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/api/ping` liczacym CALKOWITA liczbe
         * requestow (licznik atomowy w pamieci) - wypisz licznik po
         * kazdym requescie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementBasicRequestLimit {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz Zadanie 2 - po przekroczeniu 5 requestow zwroc 429
         * zamiast 200 - zweryfikuj dla 6 kolejnych requestow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddRateLimitLimitHeader {
        /*
         * 🧪 Zadanie 4:
         * Dodaj naglowek "X-RateLimit-Limit" z wartoscia stalego limitu -
         * zweryfikuj po stronie klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddRateLimitRemainingHeader {
        /*
         * 🧪 Zadanie 5:
         * Dodaj naglowek "X-RateLimit-Remaining" pokazujacy ile requestow
         * zostalo - zweryfikuj, ze MALEJE z kazdym requestem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddRetryAfterHeaderOn429 {
        /*
         * 🧪 Zadanie 6:
         * Dodaj naglowek "Retry-After" TYLKO w odpowiedzi 429 - zweryfikuj
         * jego obecnosc/brak w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementPerClientLimitByHeader {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj limit LICZONY OSOBNO per klient (naglowek
         * "X-Client-Id") - zweryfikuj, ze 2 rozni klienci maja NIEZALEZNE
         * limity.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainDifferenceBetweenRateLimitingAndThrottling {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij (jesli widzisz roznice) miedzy "rate
         * limiting" (odrzucenie po przekroczeniu) a "throttling"
         * (spowolnienie zamiast odrzucenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementTokenBucketBasic {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj podstawowa klase `TokenBucket` (pojemnosc,
         * szybkosc odnawiania, metoda tryConsume()) - przetestuj dla
         * min. 10 wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListThreeRateLimitingAlgorithms {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymien 3 algorytmy rate limitingu (fixed window,
         * sliding window, token bucket) - po 1 zdaniu opisu kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementFixedWindowAlgorithm {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj algorytm fixed window (licznik resetowany co
         * ustalony interwal, np. co 5 sekund) - zademonstruj problem
         * "wybuchu" na granicy 2 okien (2x limit w krotkim czasie wokol
         * granicy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSlidingWindowAlgorithm {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj algorytm sliding window (lista znacznikow czasu
         * ostatnich requestow, liczba w ostatnich N sekundach OD TERAZ) -
         * porownaj zachowanie z fixed window na granicy okna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementTokenBucketWithBurstCapacity {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz `TokenBucket` z Zadania 9 o test "wybuchu" - klient
         * wysyla 5 requestow NATYCHMIAST (wykorzystujac cala pojemnosc
         * wiadra) - zweryfikuj, ze WSZYSTKIE 5 przechodzi, mimo ze
         * srednia szybkosc odnawiania jest nizsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementTokenRefillVerification {
        /*
         * 🧪 Zadanie 14:
         * Zweryfikuj (z pomiarem czasu), ze tokeny odnawiaja sie w TEMPIE
         * zgodnym z konfiguracja (np. 2/s) - zmierz, ile requestow
         * przechodzi w ciagu 3 sekund CIAGLEGO wysylania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementDifferentLimitsPerEndpoint {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj ROZNE limity dla roznych endpointow (np.
         * `/api/search` ma nizszy limit niz `/api/status`) - zweryfikuj
         * niezaleznosc limitow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementTieredRateLimitsByClientPlan {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj rozne limity w zaleznosci od "planu" klienta
         * (np. "free"=5/min, "premium"=50/min, odczytane z naglowka lub
         * Mapy) - zweryfikuj oba poziomy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRateLimitResetHeader {
        /*
         * 🧪 Zadanie 17:
         * Dodaj naglowek "X-RateLimit-Reset" pokazujacy dokladny czas
         * (timestamp), kiedy limit sie odnowi - zweryfikuj poprawnosc
         * obliczonej wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementIpBasedRateLimiting {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj limit oparty na adresie IP klienta (zamiast
         * naglowka) - odczytaj `exchange.getRemoteAddress()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementGracefulDegradationInsteadOfHardReject {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj throttling (spowolnienie odpowiedzi o rosnace
         * opoznienie przy zblizaniu sie do limitu) jako ALTERNATYWE dla
         * twardego odrzucenia 429 - porownaj w komentarzu obie strategie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureOverheadOfRateLimitingCheck {
        /*
         * 🧪 Zadanie 20:
         * Zmierz narzut wydajnosciowy sprawdzania limitu (10 000 wywolan
         * tryConsume()) w porownaniu do braku jakiejkolwiek kontroli -
         * skomentuj wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDistributedRateLimitingSimulation {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj 2 instancje serwera dzielace TEN SAM limit klienta
         * (wspolny magazyn licznikow, np. wspolna Mapa symulujaca Redis) -
         * zweryfikuj, ze SUMA requestow z obu instancji respektuje
         * WSPOLNY limit, nie 2x wiekszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementAdaptiveRateLimitingBasedOnServerLoad {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj DYNAMICZNY limit zalezny od symulowanego
         * "obciazenia serwera" (np. gdy obciazenie > 80%, limit spada o
         * polowe) - zweryfikuj adaptacje przy zmiennym obciazeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRateLimitBypassForTrustedClients {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj liste "zaufanych" klientow (whitelist po ID/kluczu
         * API) calkowicie POMIJAJACYCH rate limiting - w komentarzu
         * przedyskutuj ryzyka takiego mechanizmu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_StressTestConcurrentTokenBucketAccess {
        /*
         * 🧪 Zadanie 24:
         * Uzywajac 20 watkow atakujacych TEN SAM `TokenBucket`
         * "jednoczesnie", zweryfikuj, ze LACZNA liczba udanych requestow
         * NIGDY nie przekracza pojemnosci wiadra (brak race condition w
         * synchronizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementLeakyBucketAlgorithm {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj ALTERNATYWNY algorytm "leaky bucket" (requesty
         * trafiaja do kolejki o STALEJ pojemnosci, przetwarzane ze STALA
         * szybkoscia, nadmiar odrzucany) - porownaj zachowanie z token
         * bucket dla tego samego wzorca ruchu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRateLimitingMetricsAndAlerting {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj zbieranie metryk (liczba 429 per klient/endpoint w
         * ostatniej godzinie) - dodaj prosty "alert" (log ostrzegawczy),
         * gdy KTORYS klient regularnie uderza w limit (sugeruje albo
         * naduzycie, albo za niski limit).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCostBasedRateLimiting {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj limit oparty na "koszcie" operacji, nie na
         * liczbie requestow (np. `/api/search` kosztuje 5 tokenow,
         * `/api/ping` kosztuje 1) - zweryfikuj rozne tempo wyczerpywania
         * limitu dla roznych endpointow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRateLimitHeaderStandardCompliance {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj naglowki zgodne z projektem standardu IETF
         * "RateLimit" (`RateLimit-Limit`, `RateLimit-Remaining`,
         * `RateLimit-Reset`, BEZ prefiksu "X-") jako alternatywe dla
         * nieoficjalnej konwencji z tej lekcji - w komentarzu wyjasnij
         * status "X-" naglowkow (deprecated jako prefiks wg RFC 6648).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementQueueBasedRequestSmoothingUnderLimit {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj KOLEJKOWANIE requestow przekraczajacych
         * chwilowy limit (zamiast natychmiastowego 429) - requesty
         * czekaja az do zwolnienia miejsca, z maksymalnym czasem
         * oczekiwania (potem 429 z timeout).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteRateLimitingSystemWithMultipleStrategies {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system rate limitingu wspierajacy
         * WYMIENNIE min. 2 algorytmy (token bucket + sliding window),
         * rozne limity per plan klienta, pelne naglowki informacyjne, i
         * metryki - napisz "test suite" weryfikujacy min. 8 scenariuszy
         * (w tym wspolbiezny dostep).
         */
        public static void main(String[] args) { }
    }
}
