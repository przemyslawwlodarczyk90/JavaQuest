package com.example.javaquest._07_servlets.Lesson11_HttpSession;

public class _Exercises_Lesson11_HttpSession {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSessionCreation {
        /*
         * 🧪 Zadanie 1:
         * Uruchom embedded Tomcat (port 0) z serwletem mapowanym na "/start". Serwlet
         * wywołuje req.getSession() i odpowiada tekstem zawierającym session.getId() oraz
         * session.isNew(). Użyj HttpClient.newBuilder().cookieHandler(new
         * java.net.CookieManager()).build(). Wyślij jedno żądanie GET i wypisz body
         * (isNew powinno wynosić true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SameSessionIdAcrossRequests {
        /*
         * 🧪 Zadanie 2:
         * Tym samym klientem z CookieManager co w Zadaniu 1 wykonaj DWA kolejne żądania GET.
         * Odczytaj session.getId() z obu odpowiedzi i zweryfikuj, że są identyczne –
         * dowód, że to ta sama sesja dzięki cookie JSESSIONID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IntegerCounterAttribute {
        /*
         * 🧪 Zadanie 3:
         * Serwlet trzyma w sesji atrybut "licznik" (Integer), inkrementowany przy
         * każdym żądaniu (null traktuj jako 0). Wykonaj 3 kolejne żądania tym samym
         * klientem i zweryfikuj wartości 1, 2, 3 w kolejnych odpowiedziach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetSessionFalseReturnsNull {
        /*
         * 🧪 Zadanie 4:
         * Świeżym klientem (bez wcześniejszych żądań) wyślij GET do serwletu, który
         * wywołuje req.getSession(false) (NIE tworzy nowej sesji) i sprawdza wynik na null.
         * Serwlet ma odpowiedzieć "Brak istniejacej sesji" w takim przypadku. Wypisz body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InvalidateCreatesNewId {
        /*
         * 🧪 Zadanie 5:
         * Wykonaj żądanie 1 (tworzy sesję, zapamiętaj jej id), żądanie 2 z parametrem
         * "?invalidate=true" (serwlet wywołuje session.invalidate()), a następnie żądanie 3
         * (tworzy nową sesję przez getSession()). Zweryfikuj, że id z żądania 3 RÓŻNI SIĘ
         * od id z żądania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_StringAttribute {
        /*
         * 🧪 Zadanie 6:
         * Serwlet ustawia w sesji atrybut String "username"="jan.kowalski" przy pierwszym
         * żądaniu z parametrem "?login=jan.kowalski". Kolejne żądanie (bez parametru)
         * odczytuje session.getAttribute("username") i odsyła jego wartość. Wypisz obie odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CustomObjectAttribute {
        /*
         * 🧪 Zadanie 7:
         * Serwlet przechowuje w sesji atrybut typu java.util.List<String> (np. lista
         * ostatnio oglądanych produktów), ustawiony przy pierwszym żądaniu i odczytany
         * (z wypisaniem jego zawartości) przy drugim żądaniu tym samym klientem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IsNewFlagTrueThenFalse {
        /*
         * 🧪 Zadanie 8:
         * Wykonaj 2 kolejne żądania tym samym klientem do serwletu zwracającego
         * session.isNew(). Zweryfikuj, że pierwsza odpowiedź ma isNew=true, a druga isNew=false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DifferentClientsDifferentSessionIds {
        /*
         * 🧪 Zadanie 9:
         * Utwórz DWA oddzielne klienty HttpClient, każdy z WŁASNYM CookieManager. Wyślij
         * po jednym żądaniu z każdego do tego samego serwletu tworzącego sesję. Zweryfikuj,
         * że oba otrzymane session.getId() są RÓŻNE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RemoveSingleAttribute {
        /*
         * 🧪 Zadanie 10:
         * Serwlet ustawia atrybut sesji "temp"="wartosc" przy żądaniu 1. Żądanie 2 z
         * parametrem "?usun=true" wywołuje session.removeAttribute("temp"). Żądanie 3
         * odczytuje getAttribute("temp") i zweryfikuj, że zwraca null (bez wywoływania
         * invalidate() na całej sesji – usunięty jest tylko jeden atrybut).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ShoppingCartAccumulatedInSession {
        /*
         * 🧪 Zadanie 11:
         * Serwlet "/koszyk?dodaj=X" trzyma w sesji atrybut List<String> "koszyk", dopisując
         * X przy każdym wywołaniu. Wykonaj 4 kolejne żądania z ?dodaj=Chleb, ?dodaj=Mleko,
         * ?dodaj=Maslo, ?dodaj=Jajka, a na końcu żądanie odczytujące pełną listę – zweryfikuj
         * 4 elementy w poprawnej kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LoginGuardTwoServlets {
        /*
         * 🧪 Zadanie 12:
         * Zarejestruj DWA serwlety w tym samym kontekście: "/login" (ustawia atrybut sesji
         * "zalogowany"=true) i "/panel" (sprawdza ten atrybut – jeśli brak/false, odpowiada
         * "Brak dostepu"; jeśli true, odpowiada "Witaj w panelu"). Wyślij najpierw żądanie
         * do "/panel" (oczekiwane "Brak dostepu"), potem do "/login", a następnie ponownie
         * do "/panel" (oczekiwane "Witaj w panelu") – tym samym klientem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MaxInactiveIntervalReadback {
        /*
         * 🧪 Zadanie 13:
         * Serwlet wywołuje session.setMaxInactiveInterval(120) przy tworzeniu sesji.
         * Kolejne żądanie tym samym klientem odczytuje session.getMaxInactiveInterval()
         * i wypisuje wartość (120). WAŻNE: nie czekaj na faktyczne wygaśnięcie sesji –
         * to tylko odczyt ustawionej wartości, program ma zakończyć się w kilka sekund.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_JsessionidCookieMatchesSessionId {
        /*
         * 🧪 Zadanie 14:
         * Wyślij żądanie tworzące sesję i odczytaj nagłówek odpowiedzi "Set-Cookie" –
         * powinien zawierać "JSESSIONID=" z identyfikatorem. Porównaj tę wartość z
         * session.getId() zwróconym w treści odpowiedzi przez serwlet – zweryfikuj,
         * że to te same znaki (JSESSIONID w cookie == id sesji z servletu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AttributeNamesEnumeration {
        /*
         * 🧪 Zadanie 15:
         * Serwlet ustawia w sesji 3 atrybuty ("a", "b", "c") w pierwszym żądaniu. Drugie
         * żądanie używa session.getAttributeNames() (Enumeration<String>), zbiera nazwy do
         * listy, sortuje i wypisuje razem z ich wartościami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_VisitHistoryAcrossMultiplePaths {
        /*
         * 🧪 Zadanie 16:
         * Zarejestruj wspólny serwlet obsługujący 3 różne ścieżki ("/a", "/b", "/c") plus
         * ścieżkę "/historia" do odczytu. Każde wywołanie "/a"/"/b"/"/c" dopisuje nazwę
         * ścieżki do sesyjnej listy List<String> "odwiedzone" (max. 3 ostatnie). Wykonaj
         * sekwencję odwiedzin ("/a","/b","/c","/a") i sprawdź finalną zawartość ("/b","/c","/a").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_GetSessionFalseVsTrueInSameServlet {
        /*
         * 🧪 Zadanie 17:
         * Serwlet z parametrem "?sprawdz=true" wywołuje getSession(false) (samo sprawdzenie,
         * bez tworzenia), a bez tego parametru wywołuje getSession() (utworzenie w razie
         * potrzeby). Wyślij najpierw żądanie z ?sprawdz=true (świeży klient, oczekiwane
         * "brak sesji"), potem bez parametru (tworzy sesję), potem znów ze ?sprawdz=true
         * (teraz powinna istnieć).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ClientWithoutCookieManagerAlwaysNewSession {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj 3 żądania do tego samego serwletu, ale za KAŻDYM razem twórz NOWY
         * HttpClient.newHttpClient() (BEZ CookieManager). Zweryfikuj, że KAŻDE żądanie
         * dostaje isNew=true oraz różny session.getId() – dowód, że bez zarządzania
         * cookie po stronie klienta każde żądanie jest "nowym" klientem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AuthGuardThreeRequestSequence {
        /*
         * 🧪 Zadanie 19:
         * Serwlet "/chronione" sprawdza atrybut sesji "autoryzowany". Serwlet "/zaloguj"
         * ustawia ten atrybut na true. Wykonaj: (1) żądanie do "/chronione" bez logowania
         * (oczekiwany tekst "401 Brak autoryzacji" w body), (2) żądanie do "/zaloguj",
         * (3) ponowne żądanie do "/chronione" (oczekiwane "Dostep przyznany"). Wypisz
         * wszystkie 3 odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TwoServletsSharedCounterAttribute {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj serwlet "/inc" (inkrementujący sesyjny atrybut "wizyty") oraz
         * "/pokaz" (tylko odczytujący ten sam atrybut, inna klasa serwletu). Wykonaj
         * 2 żądania do "/inc", a potem jedno do "/pokaz" tym samym klientem – zweryfikuj,
         * że "/pokaz" widzi wartość 2, mimo że to inny serwlet.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLoginLogoutCycleWithRoles {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj pełny cykl: (1) "/login?user=admin&rola=ADMIN" ustawia atrybuty
         * "username" i "rola" w sesji, (2) kilka żądań do "/chronione" sprawdzających
         * obecność "rola"="ADMIN" (sukces), (3) żądanie do "/logout" wywołuje
         * session.invalidate(), (4) kolejne żądanie do "/chronione" ma być odrzucone
         * (brak atrybutów, bo nowa pusta sesja). Wypisz raport wszystkich kroków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiStepWizardAccumulation {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj 3-krokowy "kreator": "/krok1?imie=Jan" zapisuje atrybut sesji "imie",
         * "/krok2?adres=Warszawa" zapisuje "adres" (musi istnieć sesja z krok1, inaczej
         * błąd), "/krok3" tylko odczytuje OBA atrybuty i buduje pełne podsumowanie, po
         * czym wywołuje session.invalidate(). Wykonaj pełną sekwencję 3 żądań i wypisz
         * finalne podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_InterleavedClientsFullIsolation {
        /*
         * 🧪 Zadanie 23:
         * Utwórz 2 klienty (każdy z własnym CookieManager). Wykonaj w przeplocie:
         * klient1 loguje się jako "user-1", klient2 loguje się jako "user-2", klient1
         * odczytuje swoje dane sesji, klient2 odczytuje swoje. Zweryfikuj, że KAŻDY
         * klient widzi WYŁĄCZNIE własne dane ("user-1" nigdy nie miesza się z "user-2").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CookieAndSessionTogetherInOneServlet {
        /*
         * 🧪 Zadanie 24:
         * Połącz Lesson10 i Lesson11 w jednym serwlecie: cookie "motyw" (odczytywane/ustawiane
         * jak w Lesson10, wartość domyślna "jasny") ORAZ atrybut sesji "licznikOdwiedzin"
         * (inkrementowany jak w tej lekcji) – oba mechanizmy używane RÓWNOCZEŚNIE w jednej
         * odpowiedzi. Wykonaj 3 żądania i wypisz jak zmienia się licznik sesji, podczas gdy
         * cookie motywu pozostaje stałe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MutableMapAttributeCart {
        /*
         * 🧪 Zadanie 25:
         * Serwlet trzyma w sesji atrybut Map<String,Integer> "koszyk" (produkt -> ilość).
         * Parametr "?dodaj=Chleb&ilosc=2" ma zwiększać ilość danego produktu w mapie
         * (sumując, jeśli produkt już istnieje). Wykonaj 3 żądania dodające różne ilości
         * (w tym powtórnie ten sam produkt) i na końcu odczytaj pełną zawartość koszyka
         * z podsumowaniem łącznej liczby sztuk.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SimpleCountBasedRateLimiter {
        /*
         * 🧪 Zadanie 26:
         * Serwlet trzyma w sesji atrybut Integer "liczbaZadan", odrzucający (sendError 429)
         * żądania powyżej limitu 5 W RAMACH JEDNEJ SESJI (samo liczenie, bez realnego
         * odmierzania czasu/oczekiwania!). Wyślij 7 kolejnych żądań tym samym klientem i
         * zweryfikuj, że pierwsze 5 kończy się sukcesem, a 6. i 7. statusem 429.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EightRequestTwoServletReport {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj scenariusz 8 żądań na 2 serwletach ("/login-licznik" inkrementujący,
         * "/koszyk-demo" modyfikujący atrybut koszyka) z mieszaną kolejnością wywołań.
         * Dla każdego kroku zapisz do listy oczekiwaną i faktyczną wartość kluczowego
         * atrybutu sesji, a na końcu wypisz tabelę potwierdzającą ciągłość danych sesji
         * (ten sam session.getId() przez wszystkie 8 kroków).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_UsingInvalidatedSessionThrows {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj serwlet, który W JEDNYM żądaniu: pobiera sesję (getSession()), wywołuje
         * session.invalidate(), a NASTĘPNIE próbuje wywołać session.getAttribute("cokolwiek")
         * na TYM SAMYM (już unieważnionym) obiekcie session – opakuj to w try/catch
         * IllegalStateException i odeślij w odpowiedzi informację, że wyjątek został
         * poprawnie przechwycony. Zweryfikuj treść odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LastSearchQueryMemory {
        /*
         * 🧪 Zadanie 29:
         * Serwlet "/szukaj?q=X" ma: jeśli w sesji istnieje atrybut "ostatnieWyszukiwanie",
         * odesłać w odpowiedzi "Poprzednie wyszukiwanie: " + starą wartość, a następnie
         * NADPISAĆ atrybut nową wartością X. Wykonaj 3 kolejne wyszukiwania (?q=laptop,
         * ?q=telefon, ?q=tablet) tym samym klientem i wypisz wszystkie 3 odpowiedzi,
         * pokazując jak każda pamięta poprzednie zapytanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ComprehensiveIntegrationHarness {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny test integracyjny łączący wszystko z tej lekcji w jednym main():
         * wykrywanie nowej sesji (isNew), operacje CRUD na atrybutach (set/get/remove),
         * odczyt getAttributeNames(), invalidate() i ponowne utworzenie sesji z nowym id,
         * oraz test izolacji między dwoma niezależnymi klientami. Zbierz wyniki wszystkich
         * podtestów do listy i wypisz na końcu zbiorczą tabelę pass/fail.
         */
        public static void main(String[] args) { }
    }
}
