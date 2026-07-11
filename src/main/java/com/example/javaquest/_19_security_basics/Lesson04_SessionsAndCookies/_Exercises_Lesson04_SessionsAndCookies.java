package com.example.javaquest._19_security_basics.Lesson04_SessionsAndCookies;

public class _Exercises_Lesson04_SessionsAndCookies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainStatelessHttpInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, co oznacza, ze HTTP
         * jest "bezstanowy" i jaki problem to stwarza dla logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementLoginSettingCookie {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/login` ustawiajacym naglowek
         * `Set-Cookie: SESSIONID=<uuid>` po poprawnym logowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadCookieFromRequestHeader {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode odczytujaca wartosc konkretnego ciastka z
         * naglowka `Cookie` zadania - przetestuj na kilku przykladowych
         * naglowkach (w tym z wieloma ciastkami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementProtectedEndpointCheckingSession {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `/profile` sprawdzajacy, czy `SESSIONID` z
         * ciastka istnieje w Mapie sesji po stronie serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseHttpClientWithCookieManager {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj `HttpClient` z `.cookieHandler(new CookieManager())` i
         * wykonaj sekwencje login -> profile - zweryfikuj, ze ciastko
         * przetrwalo miedzy zadaniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ShowRequestWithoutCookieManagerLosesSession {
        /*
         * 🧪 Zadanie 6:
         * Powtorz Zadanie 5, ale BEZ `CookieManager` - zweryfikuj, ze
         * `/profile` zwraca 401 (klient "zapomnial" ciastko).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementLogoutRemovingServerSideSession {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj `/logout` USUWAJACY sesje z Mapy po stronie
         * serwera - zweryfikuj, ze `/profile` po wylogowaniu zwraca 401.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GenerateSessionIdWithSecureRandom {
        /*
         * 🧪 Zadanie 8:
         * Zamien `UUID.randomUUID()` na recznie wygenerowany identyfikator
         * z `SecureRandom` (min. 16 bajtow, zakodowany Base64) - wyjasnij
         * w komentarzu, dlaczego identyfikator MUSI byc nieprzewidywalny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddHttpOnlyAttributeToCookie {
        /*
         * 🧪 Zadanie 9:
         * Dodaj atrybut `HttpOnly` do `Set-Cookie` - wyjasnij w
         * komentarzu, przed czym chroni (podpowiedz: JavaScript).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainCookieVsLocalStorageForSessionId {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego przechowywanie identyfikatora
         * sesji w ciastku z `HttpOnly` jest bezpieczniejsze niz w
         * `localStorage` przegladarki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementSessionExpiration {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do sesji znacznik czasu utworzenia - zaimplementuj
         * sprawdzanie "wygasniecia" (np. sesja starsza niz 30 "sekund"
         * symulowanego czasu) przy kazdym dostepie do `/profile`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSlidingSessionExpiration {
        /*
         * 🧪 Zadanie 12:
         * Zmodyfikuj Zadanie 11 tak, by KAZDY udany dostep do `/profile`
         * ODNAWIAL czas wygasniecia sesji (sliding expiration) - pokaz
         * roznice wobec sztywnego wygasniecia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RegenerateSessionIdAfterLogin {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj "regeneracje" identyfikatora sesji PO udanym
         * logowaniu (nawet jesli klient mial juz jakas anonimowa sesje
         * wczesniej) - wyjasnij w komentarzu, przed jakim atakiem to
         * chroni (session fixation).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LimitConcurrentSessionsPerUser {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj limit "maks. 2 aktywne sesje na uzytkownika" -
         * przy 3. logowaniu usun NAJSTARSZA sesje tego uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRememberMeWithSeparateLongLivedToken {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj opcjonalny "remember me" - osobny, dlugozyjacy
         * token (inny niz sesyjny) w osobnym ciastku, weryfikowany TYLKO
         * do automatycznego zalogowania, nie do autoryzacji operacji
         * wrazliwych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LogSessionCreationAndDestructionEvents {
        /*
         * 🧪 Zadanie 16:
         * Dodaj logowanie (na konsole) kazdego utworzenia i usuniecia
         * sesji z znacznikiem czasu i identyfikatorem uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DetectAndRejectTamperedSessionId {
        /*
         * 🧪 Zadanie 17:
         * Wyslij zadanie z RECZNIE ZMODYFIKOWANYM identyfikatorem sesji
         * (istniejacy identyfikator z 1 zmienionym znakiem) - zweryfikuj,
         * ze serwer poprawnie odrzuca (401), a NIE np. rzuca wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementPerSessionActivityMetadata {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz sesje o metadane: adres IP i "user-agent" zadania przy
         * tworzeniu - zaimplementuj (opcjonalne) ostrzezenie, gdy te same
         * dane sesji sa uzyte z INNEGO adresu IP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareServerSideSessionsWithStatelessTokens {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj (tabela w komentarzu) sesje po stronie
         * serwera (ta lekcja) z bezstanowymi tokenami (zapowiedz
         * Lesson05: JWT) pod katem: skalowalnosc, mozliwosc
         * natychmiastowego uniewaznienia, rozmiar danych w zadaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementLogoutFromAllSessions {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj `/logout-all` usuwajacy WSZYSTKIE sesje danego
         * uzytkownika (np. po zmianie hasla) - zweryfikuj, ze wszystkie
         * wczesniej wydane ciastka przestaja dzialac.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementSessionStoreWithBackgroundCleanup {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj watek (daemon, z bezpiecznym zakonczeniem)
         * okresowo usuwajacy wygasle sesje z Mapy - zweryfikuj dzialanie
         * bez realnego dlugiego oczekiwania (uzyj krotkich, symulowanych
         * czasow wygasniecia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateSessionHijackingWithStolenCookie {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj kradziez ciastka (recznie skopiuj wartosc
         * `SESSIONID` do 2. klienta) - pokaz, ze 2. klient uzyskuje
         * dostep do `/profile` PIERWSZEGO uzytkownika - skomentuj, jak
         * `HttpOnly`+`Secure` ZMNIEJSZAJA (ale nie eliminuja calkowicie)
         * to ryzyko.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BindSessionToClientFingerprint {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz sesje o prosty "fingerprint" klienta (np. hash
         * user-agent+IP) - odrzuc dostep, jesli fingerprint w kolejnym
         * zadaniu sie nie zgadza, mimo poprawnego identyfikatora sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCsrfTokenBoundToSession {
        /*
         * 🧪 Zadanie 24:
         * Wygeneruj token CSRF powiazany z sesja przy logowaniu - wymagaj
         * go (jako naglowka) przy operacjach modyfikujacych stan (np.
         * `/profile/update`) - zapowiedz pelnego tematu z Lesson10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureMemoryFootprintOfSessionStore {
        /*
         * 🧪 Zadanie 25:
         * Utworz 100 000 sesji w pamieci - zmierz przyblizone zuzycie
         * pamieci (np. przez `Runtime.getRuntime().totalMemory()`) i
         * skomentuj, dlaczego produkcyjne systemy CZESTO przechowuja
         * sesje w Redis/bazie zamiast w pamieci JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementDistributedSessionSimulationWithSharedMap {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj 2 "instancje" serwera (2 osobne `HttpServer` na
         * roznych portach) DZIELACE JEDNA wspolna Mape sesji - pokaz, ze
         * logowanie na 1 instancji dziala tez na 2 (podstawa "sticky
         * sessions" vs "shared session store" w architekturze
         * skalowanej poziomo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSecureAttributeAndExplainHttpOnlyContext {
        /*
         * 🧪 Zadanie 27:
         * Dodaj atrybut `Secure` do ciastka i wyjasnij w komentarzu,
         * dlaczego test lokalny (HTTP, nie HTTPS) w tej lekcji NIE MOZE
         * w pelni zademonstrowac jego dzialania - zapowiedz Lesson08
         * (HTTPS/TLS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DetectSessionReplayAfterLogout {
        /*
         * 🧪 Zadanie 28:
         * Zapisz uzyty juz identyfikator sesji na "czarnej liscie" po
         * wylogowaniu (zamiast tylko usuwac z Mapy) - zweryfikuj, ze
         * probe ponownego uzycia TEGO SAMEGO identyfikatora da sie
         * odroznic od "nigdy nieistniejacej" sesji w logach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BenchmarkConcurrentHashMapSessionStoreUnderLoad {
        /*
         * 🧪 Zadanie 29:
         * Uzyj puli watkow do rownoleglego tworzenia/odczytu/usuwania
         * sesji (np. 20 watkow, po 1000 operacji kazdy) - zweryfikuj
         * brak race conditions (np. licznik aktywnych sesji zawsze
         * poprawny na koniec).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureSessionDemo {
        /*
         * 🧪 Zadanie 30:
         * Polacz wygasanie (Zadanie 11-12), regeneracje ID (Zadanie 13),
         * limit sesji (Zadanie 14), logowanie zdarzen (Zadanie 16) i
         * wylogowanie ze wszystkich urzadzen (Zadanie 20) w 1 spojne
         * demo z czytelnym logiem przeplywu dla 2 uzytkownikow.
         */
        public static void main(String[] args) { }
    }
}
