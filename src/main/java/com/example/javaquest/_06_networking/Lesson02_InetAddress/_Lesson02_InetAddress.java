package com.example.javaquest._06_networking.Lesson02_InetAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class _Lesson02_InetAddress {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 java.net.InetAddress
         * ============================================================
         * InetAddress to podstawowa klasa reprezentujaca adres IP
         * (IPv4 lub IPv6) w Javie. Potrafi tez wykonac ROZWIAZYWANIE
         * NAZWY (DNS lookup) - podajemy nazwe hosta (np. "example.com"),
         * a InetAddress zapyta DNS o odpowiadajacy jej adres IP (patrz
         * Lesson01 - jak dziala DNS w skrocie).
         *
         * WAZNE: metody InetAddress moga wykonywac PRAWDZIWE zapytania
         * sieciowe (DNS, ping) - wiec moga rzucac wyjatki (glownie
         * UnknownHostException / IOException), jesli sieci nie ma albo
         * host nie istnieje. Dlatego kazde wywolanie opakowujemy w
         * try-catch, zeby lekcja zawsze konczyla sie poprawnie, nawet
         * bez dostepu do internetu.
         */

        System.out.println("=== java.net.InetAddress ===");

        /*
         * ============================================================
         * 🔹 getLoopbackAddress() - adres petli zwrotnej (127.0.0.1)
         * ============================================================
         * Nie wymaga ZADNEGO zapytania sieciowego - to stala wartosc
         * "wskazujaca na te sama maszyne". Uzywana m.in. w Lesson03+,
         * gdy laczymy sie do wlasnego, lokalnie uruchomionego serwera.
         */

        InetAddress loopback = InetAddress.getLoopbackAddress();
        System.out.println("\ngetLoopbackAddress(): " + loopback);
        System.out.println("  getHostAddress(): " + loopback.getHostAddress());
        System.out.println("  getHostName():    " + loopback.getHostName());

        /*
         * ============================================================
         * 🔹 getByName("localhost") - rozwiazanie nazwy "localhost"
         * ============================================================
         * "localhost" jest zwykle rozwiazywane LOKALNIE (bez pytania
         * zewnetrznego serwera DNS - system operacyjny ma ten wpis
         * "wbudowany", np. w pliku /etc/hosts lub jego odpowiedniku),
         * dlatego dziala nawet bez dostepu do internetu.
         */

        System.out.println("\n=== getByName(\"localhost\") ===");
        try {
            InetAddress localhost = InetAddress.getByName("localhost");
            System.out.println("Adres: " + localhost.getHostAddress());
            System.out.println("Nazwa hosta: " + localhost.getHostName());
        } catch (UnknownHostException e) {
            // W praktyce prawie niemozliwe dla "localhost", ale API
            // wymaga obslugi checked exception - zawsze reagujemy.
            System.out.println("Nie udalo sie rozwiazac 'localhost': " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔍 getLocalHost() - adres TEJ maszyny w sieci lokalnej
         * ============================================================
         * Zwraca adres IP komputera, na ktorym dziala JVM (np. adres
         * w sieci domowej/firmowej, typu 192.168.x.x). Wynik zalezy od
         * konfiguracji sieciowej maszyny, na ktorej uruchamiasz kod -
         * dlatego NIE zakladamy konkretnej wartosci w komentarzach.
         */

        System.out.println("\n=== getLocalHost() ===");
        try {
            InetAddress local = InetAddress.getLocalHost();
            System.out.println("Adres lokalny: " + local.getHostAddress());
            System.out.println("Nazwa hosta:   " + local.getHostName());
        } catch (UnknownHostException e) {
            // Moze sie zdarzyc np. w niektorych kontenerach/srodowiskach
            // bez poprawnie skonfigurowanej nazwy hosta.
            System.out.println("Nie udalo sie ustalic lokalnego hosta: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔍 getByName / getAllByName - PRAWDZIWY DNS lookup
         * ============================================================
         * getByName(host)     -> zwraca JEDEN adres IP dla danej nazwy
         * getAllByName(host)  -> zwraca WSZYSTKIE adresy IP przypisane
         *                        do danej nazwy (duze serwisy czesto
         *                        maja wiele adresow IP - load balancing)
         *
         * Uzywamy "example.com" - domeny zarezerwowanej przez IANA
         * (Internet Assigned Numbers Authority) WLASNIE do celow
         * dokumentacyjnych/edukacyjnych. Gwarantowane, ze istnieje
         * i nie zniknie - w przeciwienstwie do przypadkowej domeny.
         *
         * Mimo to opakowujemy w try-catch: bez internetu (np. na
         * maszynie CI/CD bez sieci) zapytanie DNS zawiedzie -
         * lekcja ma wtedy wypisac czytelny komunikat, a NIE wywalic
         * wyjatku i przerwac dzialanie programu.
         */

        System.out.println("\n=== getByName(\"example.com\") - realny DNS lookup ===");
        try {
            InetAddress example = InetAddress.getByName("example.com");
            System.out.println("Adres IP:  " + example.getHostAddress());
            System.out.println("Host name: " + example.getHostName());
            System.out.println("Canonical: " + example.getCanonicalHostName());
        } catch (UnknownHostException e) {
            System.out.println("Brak polaczenia z internetem lub host nieosiagalny: " + e.getMessage());
        }

        System.out.println("\n=== getAllByName(\"example.com\") - wszystkie adresy IP ===");
        try {
            InetAddress[] all = InetAddress.getAllByName("example.com");
            System.out.println("Znaleziono adresow: " + all.length);
            for (InetAddress addr : all) {
                System.out.println("  - " + addr.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Brak polaczenia z internetem lub host nieosiagalny: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 getCanonicalHostName() - "kanoniczna" (pelna) nazwa hosta
         * ============================================================
         * Wykonuje odwrotne zapytanie DNS (reverse DNS lookup): majac
         * adres IP, probuje ustalic jego "oficjalna" nazwe. Czasem
         * rozni sie od nazwy, ktora podalismy (np. alias -> nazwa
         * kanoniczna serwera). Jesli reverse DNS nie jest skonfigurowany,
         * metoda po prostu zwraca sam adres IP jako String - to NIE jest
         * blad.
         */

        /*
         * ============================================================
         * 📌 isReachable(timeout) - sprawdzenie osiagalnosci hosta
         * ============================================================
         * Probuje "dopukac sie" do hosta (najpierw ICMP echo/ping,
         * jesli sie nie uda - proba polaczenia na porcie echo).
         * ZAWSZE podajemy limit czasu (timeout w milisekundach) -
         * bez tego metoda moglaby czekac bardzo dlugo. Uzywamy
         * krotkiego timeoutu (2000 ms), zeby lekcja szybko sie konczyla
         * niezaleznie od wyniku.
         *
         * Wynik false NIE zawsze oznacza "host nie istnieje" - wiele
         * serwerow/firewalli blokuje ICMP ze wzgledow bezpieczenstwa,
         * wiec isReachable() moze zwrocic false nawet dla dzialajacego
         * serwera. To metoda orientacyjna, nie w 100% pewna.
         */

        System.out.println("\n=== isReachable(2000) ===");
        try {
            InetAddress example = InetAddress.getByName("example.com");
            boolean reachable = example.isReachable(2000);
            System.out.println("example.com osiagalny: " + reachable);
            if (!reachable) {
                System.out.println("(false moze tez oznaczac zablokowane ICMP, a nie brak hosta)");
            }
        } catch (IOException e) {
            // isReachable rzuca IOException, getByName rzuca UnknownHostException
            // (ktory rowniez jest IOException) - lapiemy oba naraz.
            System.out.println("Brak polaczenia z internetem lub blad sprawdzania osiagalnosci: " + e.getMessage());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - InetAddress reprezentuje adres IP i potrafi wykonac DNS
         *   lookup (zamiana nazwy hosta na adres IP).
         * - getLoopbackAddress() -> 127.0.0.1, bez zadnego zapytania sieciowego.
         * - getByName(nazwa)    -> jeden adres IP dla nazwy (moze rzucic
         *   UnknownHostException).
         * - getAllByName(nazwa) -> wszystkie adresy IP dla nazwy.
         * - getLocalHost()      -> adres TEJ maszyny w sieci lokalnej.
         * - getHostName()/getHostAddress()/getCanonicalHostName() ->
         *   odczyt nazwy / adresu / pelnej (kanonicznej) nazwy hosta.
         * - isReachable(timeout) -> orientacyjne sprawdzenie osiagalnosci
         *   (ZAWSZE z limitem czasu!), wynik false nie zawsze oznacza
         *   brak hosta (moze byc zablokowane ICMP).
         * - KAZDA metoda wykonujaca realny DNS/ping lookup moze rzucic
         *   wyjatek (UnknownHostException / IOException) - zawsze
         *   obslugujemy go, by program konczyl sie poprawnie nawet bez
         *   dostepu do internetu.
         */
    }
}
