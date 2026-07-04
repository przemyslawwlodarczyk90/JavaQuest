package com.example.javaquest._06_networking.Lesson01_NetworkingIntroduction;

public class _Exercises_Lesson01_NetworkingIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateEndpoint {
        /*
         * 🧪 Zadanie 1:
         * Utwórz InetSocketAddress dla hosta "example.com" i portu 443.
         * Wypisz jego toString(), getHostString() oraz getPort().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LocalhostEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Utwórz InetSocketAddress dla hosta "localhost" i portu 8080.
         * Wypisz pełne informacje o endpoint (toString(), host, port).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UnresolvedEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Utwórz InetSocketAddress.createUnresolved("nieistniejaca-domena-xyz.test", 9999).
         * Sprawdź i wypisz wynik metody isUnresolved().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WellKnownPortsTable {
        /*
         * 🧪 Zadanie 4:
         * Wypisz sformatowaną tabelę (printf) portów "well-known": 20, 21, 22, 25,
         * 53, 80, 443 wraz z nazwą protokołu (FTP, SSH, SMTP, DNS, HTTP, HTTPS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_PortRangeClassifier {
        /*
         * 🧪 Zadanie 5:
         * Napisz metodę classifyPort(int port) zwracającą "WELL-KNOWN" (0-1023),
         * "REGISTERED" (1024-49151) lub "DYNAMIC" (49152-65535).
         * Przetestuj dla portów: 22, 8080, 50000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TcpOrUdpForPort {
        /*
         * 🧪 Zadanie 6:
         * Dla tablicy portów {80, 443, 53, 22, 21} wypisz, jaki protokół
         * transportowy zwykle jest z nimi kojarzony (TCP lub UDP), na podstawie
         * wiedzy z tej lekcji (np. 53/DNS kojarzy się głównie z UDP).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ProtocolFactSheet {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj tablicę String[][] z parami (cecha, wartość) opisującymi różnice
         * TCP vs UDP (połączeniowość, niezawodność, szybkość) i wypisz ją jako
         * czytelną listę faktów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IPv4Validator {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę isValidIPv4(String s) sprawdzającą (bez regexu, przez
         * split(".") i parsowanie liczb), czy String ma format 4 liczb 0-255
         * oddzielonych kropkami. Przetestuj na "192.168.0.1" oraz "999.1.1.1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrivateIpChecker {
        /*
         * 🧪 Zadanie 9:
         * Napisz metodę isPrivateIp(String ip) sprawdzającą, czy adres IPv4
         * mieści się w zakresach prywatnych (10.x, 172.16-31.x, 192.168.x).
         * Przetestuj na: "10.0.0.5", "172.20.1.1", "192.168.1.1", "8.8.8.8".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EndpointEquality {
        /*
         * 🧪 Zadanie 10:
         * Utwórz dwa InetSocketAddress.createUnresolved("test.local", 8000) z tymi
         * samymi parametrami. Porównaj je metodami equals() i hashCode() oraz
         * wypisz wynik wraz z krótkim komentarzem, dlaczego są (lub nie są) równe.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ParseHostPortString {
        /*
         * 🧪 Zadanie 11:
         * Mając String "example.com:8080", ręcznie rozdziel go (split(":")) na
         * host i port, a następnie zbuduj z nich InetSocketAddress. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_EndpointList {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj List<InetSocketAddress> reprezentującą 3 usługi: serwer WWW
         * ("web.example.com", 80), serwer SSH ("ssh.example.com", 22) i serwer
         * DNS ("dns.example.com", 53). Wypisz każdy wraz z opisem usługi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SchemeToDefaultPort {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę defaultPortForScheme(String scheme) mapującą "http"->80,
         * "https"->443, "ftp"->21, "ssh"->22. Przetestuj dla wszystkich czterech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ProtocolRecommender {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę recommendProtocol(String scenario) zwracającą "TCP" lub
         * "UDP" na podstawie słów kluczowych w opisie scenariusza (np. zawiera
         * "wideo" lub "gra" -> UDP, zawiera "plik" lub "transakcja" -> TCP).
         * Przetestuj dla 4 różnych opisów scenariuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PortRangeReport {
        /*
         * 🧪 Zadanie 15:
         * Dla tablicy portów {22, 80, 443, 1024, 8080, 49152, 65000} użyj metody
         * classifyPort z Zadania 5 i policz, ile portów wpada w każdą z 3 kategorii.
         * Wypisz raport liczbowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MiniDnsCache {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj Map<String,String> symulującą pamięć podręczną DNS (np.
         * "example.com"->"93.184.216.34"). Napisz metodę resolve(String host)
         * zwracającą adres z mapy lub "UNKNOWN", jeśli hosta nie ma w mapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EndpointsSameHostDifferentPorts {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj 3 InetSocketAddress dla tego samego hosta "example.com" i portów
         * 80, 443, 22. Posortuj je rosnąco wg portu (Comparator) i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HttpVsHttpsComparison {
        /*
         * 🧪 Zadanie 18:
         * Wypisz sformatowaną tabelę porównawczą (cecha, HTTP, HTTPS) dla: portu
         * domyślnego, szyfrowania, integralności danych, uwierzytelnienia serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IpClassifierBatch {
        /*
         * 🧪 Zadanie 19:
         * Dla tablicy adresów IP {"127.0.0.1", "192.168.1.1", "8.8.8.8", "10.0.0.1"}
         * sklasyfikuj każdy jako "LOOPBACK", "PRYWATNY" lub "PUBLICZNY" (na podstawie
         * metod z Zadań 8-9) i wypisz wynik w formie tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TcpHandshakeSimulation {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj tablicę String[] {"SYN", "SYN-ACK", "ACK"} i wypisz kolejne kroki
         * symulowanego "three-way handshake" TCP w pętli, z numerem kroku i
         * krótkim opisem, kto (klient/serwer) wysyła daną wiadomość.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConfigParser {
        /*
         * 🧪 Zadanie 21:
         * Mając tablicę linii konfiguracyjnych {"web=example.com:80",
         * "ssh=example.com:22", "dns=example.com:53"}, sparsuj każdą linię
         * (klucz=host:port) do Map<String, InetSocketAddress> i wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PortConflictDetector {
        /*
         * 🧪 Zadanie 22:
         * Mając listę InetSocketAddress (kilka par host+port, w tym celowo
         * powtórzone kombinacje), wykryj duplikaty (Set<InetSocketAddress>)
         * i wypisz, które endpointy pojawiają się więcej niż raz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_NetworkGlossaryQuiz {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj Map<String,String> z definicjami pojęć: TCP, UDP, DNS, port, IP,
         * HTTP, HTTPS (na podstawie tej lekcji). Wypisz sformatowany "arkusz
         * quizowy": termin - definicja, jedna linia na termin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_EndpointBuilder {
        /*
         * 🧪 Zadanie 24:
         * Napisz klasę pomocniczą EndpointBuilder z metodą build(String host,
         * String scheme, int port), która, jeśli port == 0, używa domyślnego
         * portu dla podanego schematu (Zadanie 13), a w przeciwnym razie użyje
         * podanego portu. Przetestuj dla ("example.com","https",0) i
         * ("example.com","http",8080).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ProtocolDecisionEngine {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę decideProtocol(boolean latencySensitive, boolean
         * reliabilityRequired) zwracającą "TCP" lub "UDP" wg prostych reguł
         * (np. reliabilityRequired=true -> zawsze TCP; w przeciwnym razie
         * latencySensitive=true -> UDP, inaczej TCP). Wypisz tabelę prawdy dla
         * wszystkich 4 kombinacji true/false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IpRangeCounter {
        /*
         * 🧪 Zadanie 26:
         * Dla tablicy adresów IP (kilka z zakresu 192.168.x.x i kilka spoza),
         * policz ręcznie (porównując pierwsze dwa oktety jako Stringi), ile z
         * nich należy do zakresu 192.168.0.0/16. Wypisz liczbę i listę pasujących.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MiniRoutingTable {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj Map<String,String> symulującą tablicę routingu: prefiks sieci
         * (np. "192.168.") -> nazwa bramy ("brama-domowa"). Napisz metodę
         * findRoute(String ip) zwracającą pasującą bramę na podstawie
         * najdłuższego pasującego prefiksu (String.startsWith), albo "BRAK
         * TRASY". Przetestuj na kilku adresach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_HandshakeAndTeardownSimulation {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę simulateConnectionLifecycle(String clientName), która
         * wypisuje pełen cykl życia połączenia TCP jako kolejne kroki: handshake
         * (SYN, SYN-ACK, ACK), wymiana danych ("DATA: ..."), zamknięcie (FIN,
         * ACK, FIN, ACK). Wywołaj ją dla jednego przykładowego klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ProtocolSuiteReport {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj listę scenariuszy (host, port, opis zastosowania) i dla każdego
         * wypisz jeden wiersz raportu łączący: klasyfikację portu (Zadanie 5),
         * rekomendowany protokół (Zadanie 14) i sprawdzenie, czy port jest
         * poprawny (0-65535). Wypisz pełną tabelę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_NetworkingConceptsExam {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj Map<String,String> z pytaniami i poprawnymi odpowiedziami
         * dotyczącymi pojęć z tej lekcji (np. "Domyslny port HTTPS?" -> "443").
         * Zbuduj równoległą listę odpowiedzi "studenta" (część poprawnych, część
         * błędnych) i napisz metodę sprawdzającą wynik, wypisującą liczbę
         * poprawnych odpowiedzi i podsumowanie na końcu.
         */
        public static void main(String[] args) { }
    }
}
