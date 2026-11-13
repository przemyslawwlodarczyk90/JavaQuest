// Generuje lekcje _ns_01_fundamenty_sieci/04_OsiModelInPractice.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_04_osi_lesson.cjs src/main/resources/content/_ns_01_fundamenty_sieci/04_OsiModelInPractice.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const NAMES = { 1: 'fizyczna', 2: 'łącza danych', 3: 'sieciowa', 4: 'transportowa', 5: 'sesji', 6: 'prezentacji', 7: 'aplikacji' };
const L = (n) => `Warstwa ${n} — ${NAMES[n]}`;
const others = (n, k = 3) => [n + 1, n - 1, n + 2, n - 2, n + 3, n - 3].filter((x) => x >= 1 && x <= 7).slice(0, k).map(L);

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Wspólny język do rozmowy o awariach', ...img('ns01-04-osi-stack.svg', 'Tabela siedmiu warstw OSI od aplikacji do fizycznej: jednostki danych (dane, segment, pakiet, ramka, bity), przykładowe protokoły (HTTP, TLS, TCP, IP, Ethernet) i urządzenia (proxy, firewall, router, przełącznik, hub).'),
    body:
`„Mamy problem na czwórce”, „to load balancer L7”, „firewall działa na L3/L4”, „WAF chroni warstwę aplikacji”. Te zdania padają codziennie w zespołach backendowych, SRE i SOC. Ich wspólny mianownik to **model OSI** (Open Systems Interconnection, ISO/IEC 7498-1) — siedmiowarstwowy model, który dzieli komunikację sieciową na odpowiedzialności.

Model OSI **nie jest implementacją**. Internet działa na stosie TCP/IP (następna lekcja), a żaden program nie ma klasy „Warstwa5”. Mimo to OSI jest niezastąpiony jako:
- **mapa diagnostyczna** — pozwala zawęzić problem do warstwy zamiast zgadywać („ping działa, ale port nie odpowiada” → warstwa 4),
- **język zespołów** — developer, administrator sieci i analityk SOC opisują ten sam problem tymi samymi słowami,
- **mapa zabezpieczeń** — każda warstwa ma własne zagrożenia i kontrole (obrona w głąb),
- **materiał egzaminacyjny** — Network+, CCNA i Security+ pytają o warstwy protokołów, urządzeń i ataków.

W tej lekcji nie będziemy recytować warstw. Nauczysz się przypisywać do nich protokoły, urządzenia, komunikaty błędów Javy, narzędzia i ataki — tak, by przy następnym \`ConnectException\` od razu wiedzieć, od czego zacząć.` },

  { type: 'DEFINITION', heading: 'Siedem warstw: co naprawdę robi każda', body:
`- **7. Aplikacji** — protokoły, z których korzysta aplikacja: HTTP, DNS, SMTP, IMAP, SSH, FTP, DHCP, SNMP, LDAP. To *nie* sama aplikacja (przeglądarka, Spring Boot), tylko jej „język” sieciowy.
- **6. Prezentacji** — reprezentacja danych: kodowanie znaków (UTF-8), serializacja (JSON, Protobuf), kompresja, szyfrowanie. Tu najczęściej umieszcza się **TLS**.
- **5. Sesji** — nawiązywanie, utrzymywanie i kończenie dialogu: sesje RPC, NetBIOS, punkty kontrolne. W praktyce realizują to aplikacje i biblioteki.
- **4. Transportowa** — komunikacja *proces–proces*: **porty**, niezawodność, kolejność, kontrola przepływu i przeciążenia. TCP (połączeniowy), UDP (bezpołączeniowy). PDU: **segment** (TCP) lub **datagram** (UDP).
- **3. Sieciowa** — komunikacja *host–host* przez wiele sieci: **adresy IP**, routing, fragmentacja, TTL. IPv4, IPv6, ICMP, IPsec. PDU: **pakiet**.
- **2. Łącza danych** — dostarczenie w obrębie jednego segmentu: **adresy MAC**, ramki, wykrywanie błędów (FCS), VLAN-y. Ethernet, Wi-Fi 802.11, ARP, PPP. PDU: **ramka**. Podwarstwy: LLC i MAC.
- **1. Fizyczna** — bity jako sygnał: napięcie, światło, fale radiowe, złącza, kable, kodowanie linii, szybkość. PDU: **bit**.

**Enkapsulacja**: każda warstwa dodaje własny nagłówek do danych warstwy wyższej (dane → segment → pakiet → ramka → bity). Odbiorca zdejmuje nagłówki w odwrotnej kolejności (dekapsulacja). Szczegóły bajt po bajcie — w następnej lekcji.

Mnemotechnika od góry: *All People Seem To Need Data Processing* (Application, Presentation, Session, Transport, Network, Data link, Physical); od dołu: *Please Do Not Throw Sausage Pizza Away*.` },

  { type: 'ANALOGY', heading: 'List wysłany przez firmową kancelarię', body:
`Wyobraź sobie, że prezes firmy w Warszawie wysyła umowę do partnera w Lizbonie:
- **7 — aplikacji**: prezes pisze treść umowy. Interesuje go *co*, nie *jak*.
- **6 — prezentacji**: tłumacz przekłada umowę na portugalski i zamyka ją w zapieczętowanej kopercie (format, szyfrowanie).
- **5 — sesji**: asystentka pilnuje, że to część dłuższej korespondencji: numer sprawy, kolejność pism, potwierdzenie zakończenia.
- **4 — transportowa**: kancelaria dzieli gruby dokument na ponumerowane przesyłki, śledzi potwierdzenia odbioru i ponawia zagubione (TCP), albo wysyła pocztówkę bez potwierdzenia (UDP). Adresuje do konkretnego *działu* odbiorcy (port).
- **3 — sieciowa**: poczta czyta adres miasta i kraju (IP) i wybiera trasę przez sortownie (routery).
- **2 — łącza danych**: lokalny kurier wiezie paczkę od jednej sortowni do następnej — zna tylko najbliższy punkt (MAC); na każdym odcinku jest inny kurier i nowa etykieta.
- **1 — fizyczna**: samochód, samolot, droga.

Każda warstwa ufa, że ta pod nią wykona swoją pracę, i nie wnika w szczegóły. Dzięki temu można wymienić samolot na pociąg (Wi-Fi na kabel) bez przepisywania umowy (aplikacji).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Kto czyta które nagłówki: hub, przełącznik, router, firewall, proxy', ...img('ns01-04-devices.svg', 'Pasek ramki z kolejnymi nagłówkami: Ethernet, IP, TCP, TLS, HTTP z danymi. Pod nim zakres czytany przez urządzenia: hub tylko bity, przełącznik nagłówek Ethernet, router do IP, firewall stanowy i load balancer L4 do TCP, reverse proxy i WAF całość łącznie z HTTP po zakończeniu TLS.'),
    body:
`Urządzenia „żyją” na różnych warstwach, bo czytają różne części ramki. To wyjaśnia, *co mogą zobaczyć, zablokować i zalogować*:`,
    code:
`urządzenie                 warstwa  decyzję podejmuje na podstawie            czego NIE widzi
hub, repeater, kabel       1        nic — powiela sygnał                      niczego nie interpretuje
przełącznik, AP, bridge    2        MAC docelowy, VLAN                        adresów IP, portów
router, przełącznik L3     3        IP docelowy + tablica routingu            portów (chyba że ACL L4)
firewall stanowy, NAT      3–4      IP, porty, stan połączenia TCP            ścieżki URL, treści
load balancer L4 (NLB)     4        IP i port, rozkłada połączenia TCP/UDP    nagłówków HTTP
reverse proxy, LB L7 (ALB) 7        host, ścieżka, nagłówki, ciasteczka       — kończy TLS, widzi wszystko
WAF, API gateway           7        treść żądań (parametry, JSON)             —
NGFW (next-gen firewall)   3–7      porty + identyfikacja aplikacji + IPS     treści TLS bez inspekcji

Konsekwencja: reguła na firewallu L4 nie odróżni GET /api/users od GET /api/admin —
to potrafi tylko element L7. Za to element L7 musi odszyfrować TLS, więc staje się
miejscem, gdzie są klucze i dane w jawnej postaci.` },

  { type: 'CODE_BASIC', heading: 'Wyjątki Javy i ich warstwy', body:
`Każdy wyjątek sieciowy w Javie podpowiada warstwę, na której komunikacja się zatrzymała. Ten sam klient, różne przyczyny:`,
    code:
`import java.net.*;
import java.net.http.*;
import javax.net.ssl.SSLHandshakeException;

public class LayerProbe {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().connectTimeout(java.time.Duration.ofSeconds(3)).build();
        HttpRequest req = HttpRequest.newBuilder(URI.create(args[0])).build();
        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("L7 OK, status " + res.statusCode());   // 4xx/5xx = problem w warstwie 7
        } catch (java.net.ConnectException e) {
            // "Connection refused" → L4: host żyje, ale na porcie nikt nie nasłuchuje (RST)
            // UnresolvedAddressException/UnknownHost w przyczynie → DNS (L7 zależności)
            System.out.println("Brak połączenia: " + e + " / przyczyna: " + e.getCause());
        } catch (HttpConnectTimeoutException e) {
            // brak odpowiedzi na SYN → L3/L4: zła trasa, firewall cicho odrzuca (DROP)
            System.out.println("Timeout połączenia — sprawdź trasę i firewall");
        } catch (SSLHandshakeException e) {
            // PKIX path building failed, no cipher suites in common → L6 (TLS)
            System.out.println("TLS: " + e.getMessage());
        }
    }
}

// Typowe komunikaty i warstwy:
// java.net.UnknownHostException: api.example.com    → DNS (protokół warstwy 7, ale blokuje wszystko niżej)
// java.net.NoRouteToHostException: No route to host  → L3 (brak trasy, ICMP host unreachable)
// java.net.ConnectException: Connection refused      → L4 (RST z hosta docelowego)
// java.net.SocketTimeoutException: Connect timed out → L3/L4 (pakiety giną: firewall DROP, zła trasa)
// java.net.SocketException: Connection reset         → L4 (RST w trakcie połączenia: proxy, timeout bezczynności)
// javax.net.ssl.SSLHandshakeException: PKIX ...      → L6 (zaufanie do certyfikatu)
// HTTP 502/503/504 z proxy                           → L7 (proxy działa, backend nie)` },

  { type: 'STEP_BY_STEP', heading: 'Diagnoza warstwa po warstwie', ...img('ns01-04-troubleshooting.svg', 'Schody od L1 do L7: L1 czy jest link (ip link, ethtool), L2 czy znam MAC (ip neigh), L3 czy jest trasa (ip route get, ping), L4 czy port przyjmuje połączenie (nc, ss), L5–6 czy TLS się zestawia (openssl s_client), L7 czy aplikacja odpowiada (curl -v). Obok typowe komunikaty błędów.'),
    body:
`Metoda *bottom-up* (od dołu) jest najpewniejsza, gdy nie masz hipotezy. Każdy krok to jedno pytanie i jedno narzędzie:
1. **L1 — czy jest link?** \`ip link\` (stan UP, brak NO-CARRIER), \`ethtool eth0\` (prędkość, duplex), liczniki błędów CRC. W chmurze i kontenerach: czy interfejs istnieje.
2. **L2 — czy znam sąsiada?** \`ip neigh\` — wpis bramy w stanie REACHABLE/STALE, a nie INCOMPLETE/FAILED. Zły VLAN albo duplikat IP wychodzi tutaj.
3. **L3 — czy jest trasa i odpowiedź?** \`ip route get 10.0.5.17\` (którym interfejsem i przez jaką bramę), \`ping\`, \`traceroute\`/\`mtr\`. Pamiętaj: ICMP może być blokowany, więc brak pinga nie przesądza sprawy.
4. **L4 — czy port przyjmuje połączenie?** \`nc -zv db.local 5432\`, \`curl -v telnet://host:port\`, na serwerze \`ss -tlnp\` (czy proces nasłuchuje i na jakim adresie — 127.0.0.1 czy 0.0.0.0).
5. **L5–6 — czy TLS się zestawia?** \`openssl s_client -connect host:443 -servername host\` — łańcuch certyfikatów, wersja, szyfr, błąd weryfikacji.
6. **L7 — czy aplikacja odpowiada poprawnie?** \`curl -v https://host/health\`, kod odpowiedzi, nagłówki, logi aplikacji i proxy.

Alternatywy z metodyki CompTIA: **top-down** (zacznij od aplikacji, gdy objaw dotyczy jednej usługi) i **divide and conquer** (zacznij od środka — np. od L3/L4 — i idź w stronę, która zawodzi). Najszybciej działa podejście mieszane: hipoteza z komunikatu błędu, potem potwierdzenie narzędziem warstwy.` },

  { type: 'NOTE', heading: 'OSI to model, nie implementacja — i jego niejasne miejsca', body:
`Model OSI powstał równolegle z TCP/IP (lata 70.–80.). Jako *stos protokołów* przegrał, ale jako *model pojęciowy* zwyciężył. Kilka rzeczy, których nie ma w tabelkach:`,
    code:
`Gdzie jest...?          Odpowiedź praktyka
TLS                     zwykle „warstwa 6” (szyfrowanie, reprezentacja), ale działa nad TCP i jest
                        biblioteką aplikacji; egzaminy CompTIA zwykle przypisują SSL/TLS do 6 (czasem 7)
ARP                     łączy L3 z L2 (IP → MAC); najczęściej przypisywany do warstwy 2 („2,5”)
ICMP                    warstwa 3 (mimo że ma własny nagłówek wewnątrz pakietu IP)
BGP, OSPF               funkcja: routing (L3); BGP technicznie jedzie po TCP/179 jak aplikacja
DNS, DHCP               warstwa 7 — choć bez nich nic poniżej nie zacznie pracy aplikacji
QUIC (HTTP/3)           transport (L4) zbudowany nad UDP, z wbudowanym TLS 1.3 → łamie podział warstw
VPN, VXLAN, GRE         tunel: cała ramka/pakiet jako dane innego protokołu (L2 w L4 itp.)
warstwy 5 i 6           w TCP/IP nie istnieją osobno — robi je aplikacja i biblioteki

Wniosek: używaj OSI do myślenia i rozmowy, a do analizy pakietów — rzeczywistych
nagłówków stosu TCP/IP (Wireshark pokazuje właśnie je).` },

  { type: 'CODE_PRACTICAL', heading: 'Ściąga narzędzi według warstw (Linux i Windows)', body:
`Jedna tabela, którą warto mieć pod ręką przy każdej awarii. Polecenia Windows w PowerShell.`,
    code:
`warstwa  pytanie                       Linux                                     Windows (PowerShell)
L1       link, prędkość, błędy         ip -s link, ethtool eth0                  Get-NetAdapter, Get-NetAdapterStatistics
L2       MAC, ARP, VLAN                ip neigh, bridge fdb, tcpdump -e          Get-NetNeighbor, arp -a
L3       adres, trasa, osiągalność     ip addr, ip route get X, ping, mtr        Get-NetIPAddress, Find-NetRoute, Test-NetConnection X
L4       porty, gniazda, połączenia    ss -tlnp, ss -tan, nc -zv host port       Get-NetTCPConnection, Test-NetConnection X -Port 5432
L5–6     TLS, certyfikaty              openssl s_client -connect h:443           Test-NetConnection + certutil / Invoke-WebRequest
L7       HTTP, DNS, aplikacja          curl -v, dig, logi (journalctl)           Invoke-WebRequest, Resolve-DnsName
przekrój wszystkie naraz               tcpdump -i any -w cap.pcap → Wireshark    pktmon, Wireshark

W Javie: -Djavax.net.debug=ssl:handshake (L6), logi HttpClient (jdk.httpclient.HttpClient.log=all),
-Djava.net.preferIPv4Stack=true przy problemach z IPv6 (L3).` },

  { type: 'USAGE', heading: 'Warstwy w pracy obrońcy: zagrożenia i kontrole', ...img('ns01-04-defense-layers.svg', 'Dla każdej warstwy od 7 do 1 para: zagrożenia (SQL injection i XSS, downgrade TLS, przejęcie sesji, SYN flood i skanowanie, IP spoofing, ARP spoofing i MAC flooding, podpięcie do gniazda) i zabezpieczenia (walidacja i WAF, TLS 1.3 i HSTS, krótkie sesje, firewall stanowy i SYN cookies, ACL i uRPF, 802.1X i DAI, zamki i wyłączone porty).'),
    body:
`Security+ i CySA+ wymagają, by przypisywać ataki i zabezpieczenia do warstw. To nie jest sztuka dla sztuki — **obrona w głąb** oznacza, że każda warstwa ma własną kontrolę, więc obejście jednej nie oznacza przegranej:
- **L1** — bezpieczeństwo fizyczne: zamknięte szafy, wyłączone nieużywane gniazda, ochrona przed zagłuszaniem Wi-Fi.
- **L2** — 802.1X i NAC, port security, DHCP snooping, Dynamic ARP Inspection, osobne VLAN-y. Ataki: ARP spoofing, MAC flooding, VLAN hopping, rogue DHCP.
- **L3** — ACL, uRPF przeciw podszywaniu adresów, segmentacja, filtrowanie ICMP z głową, RPKI. Ataki: IP spoofing, ICMP flood, przejęcie tras.
- **L4** — firewall stanowy, SYN cookies, limity połączeń, ochrona DDoS. Ataki: SYN flood, skanowanie portów.
- **L5–6** — TLS 1.3, HSTS, poprawna walidacja certyfikatów, ochrona tokenów i sesji. Ataki: downgrade, SSL stripping, przejęcie sesji.
- **L7** — walidacja wejścia, autoryzacja, WAF, rate limiting, MFA, logowanie zdarzeń bezpieczeństwa. Ataki: wstrzyknięcia, XSS, BOLA, credential stuffing.

Dla developera Javy najważniejsze są warstwy 5–7 — to twój kod. Dla analityka SOC wszystkie: alert z IDS (L3/L4), log WAF (L7) i log uwierzytelnienia (L7) to fragmenty tej samej historii ataku.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy w posługiwaniu się modelem OSI', body:
`- **„Ping działa, więc sieć działa”.** Ping to ICMP (L3). Port aplikacji (L4) może być zamknięty, a TLS (L6) może się nie zestawiać. I odwrotnie: brak pinga nie znaczy, że host nie żyje — ICMP bywa blokowane.
- **Szukanie błędu w kodzie przy problemie L3.** \`SocketTimeoutException: Connect timed out\` niemal nigdy nie jest bugiem w Javie — to pakiety ginące po drodze (firewall, Security Group, trasa).
- **Mylenie „Connection refused” z timeoutem.** Refused = ktoś *odpowiedział* RST (host i trasa działają, nie ma usługi na porcie). Timeout = *nikt* nie odpowiedział (DROP, brak trasy, host wyłączony).
- **Urządzenie = jedna warstwa.** Przełącznik L3 robi routing, NGFW zagląda do L7, router domowy jest jednocześnie przełącznikiem, AP, serwerem DHCP i DNS.
- **Złe nazwy PDU.** Segment (TCP), datagram (UDP), pakiet (IP), ramka (Ethernet). Na egzaminie to częste pułapki.
- **Recytowanie bez rozumienia.** Znajomość mnemotechniki nie pomoże przy awarii; pomoże umiejętność przypisania komunikatu błędu do warstwy i narzędzia.
- **„Warstwa 8”.** Żart o użytkowniku bywa prawdziwy (zły adres w konfiguracji), ale nie zastępuje diagnozy — najpierw dowody z narzędzi.` },

  { type: 'CODE_WRONG', heading: 'Źle: obsługa błędu, która zaciera warstwę', body:
`Klient REST łapie wszystko jednym \`catch (Exception)\` i loguje „błąd sieci”. Na dyżurze nie wiadomo, czy padł DNS, firewall, certyfikat, czy backend — a „naprawa” polega na zwiększeniu timeoutu.`,
    code:
`public Optional<User> fetchUser(long id) {
    try {
        return Optional.of(restClient.get().uri("/users/{id}", id).retrieve().body(User.class));
    } catch (Exception e) {
        log.warn("Błąd sieci");          // brak typu wyjątku, przyczyny, hosta, czasu trwania
        return Optional.empty();         // awaria wygląda jak "użytkownik nie istnieje"
    }
}
// application.yml: "naprawa" każdego problemu
// connect-timeout: 120s                ← przy DROP na firewallu wątek wisi 2 minuty` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: błędy, które mówią, na której warstwie szukać', body:
`Rozróżniamy klasy błędów, logujemy przyczynę i kontekst, a timeouty ustawiamy świadomie. Dyżurny od razu wie, czy dzwonić do zespołu sieci, PKI czy właściciela backendu.`,
    code:
`public User fetchUser(long id) {
    try {
        return restClient.get().uri("/users/{id}", id).retrieve().body(User.class);
    } catch (ResourceAccessException e) {                  // I/O: L3–L6
        Throwable root = NestedExceptionUtils.getMostSpecificCause(e);
        String layer = switch (root) {
            case UnknownHostException u -> "DNS";
            case ConnectException c -> "L4 (refused: brak usługi na porcie)";
            case SocketTimeoutException t -> "L3/L4 (timeout: trasa lub firewall)";
            case javax.net.ssl.SSLException s -> "L6 (TLS/certyfikat)";
            default -> "I/O";
        };
        log.error("users-api niedostępne [{}]: {}", layer, root.toString());
        throw new UpstreamUnavailableException("users-api", layer, e);
    } catch (HttpServerErrorException e) {                 // L7: backend odpowiedział 5xx
        log.error("users-api zwróciło {} dla id={}", e.getStatusCode(), id);
        throw e;
    }
}
// application.yml: connect-timeout 2s, read-timeout 5s — szybka porażka zamiast wiszących wątków` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy myśleć w OSI, a kiedy w TCP/IP', body:
`- **Diagnoza awarii** — OSI jako lista kontrolna od dołu lub od góry; zawęża problem do jednej warstwy i jednego zespołu.
- **Rozmowa z innymi zespołami** — „L4 load balancer”, „blokada na L3”, „problem L7” są zrozumiałe dla sieciowców, SRE i security.
- **Projektowanie zabezpieczeń** — przypisz kontrolę do każdej warstwy (obrona w głąb) i sprawdź, czy któraś warstwa nie została bez ochrony.
- **Wybór urządzenia lub usługi** — NLB czy ALB, firewall stanowy czy WAF: decyduje, czy potrzebujesz informacji z L4, czy z L7.
- **Egzaminy** — Network+, CCNA, Security+ i CySA+ używają terminologii OSI.
- **Analiza pakietów i implementacja** — model **TCP/IP** (4 warstwy) i rzeczywiste nagłówki: tak działają systemy operacyjne, biblioteki Javy i Wireshark.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- OSI to **siedmiowarstwowy model pojęciowy**: fizyczna, łącza danych, sieciowa, transportowa, sesji, prezentacji, aplikacji.
- PDU: bity (L1), ramka (L2), pakiet (L3), segment/datagram (L4), dane (L5–7).
- Adresy: MAC (L2), IP (L3), port (L4). Urządzenia: hub (L1), przełącznik (L2), router (L3), firewall stanowy / LB L4, proxy / WAF / LB L7.
- TLS zwykle przypisuje się warstwie 6, DNS/HTTP/DHCP — warstwie 7, ARP — 2, ICMP — 3.
- Komunikaty błędów wskazują warstwę: refused = L4, timeout = L3/L4, PKIX = L6, 5xx = L7, UnknownHost = DNS.
- Diagnoza: bottom-up, top-down lub divide and conquer — pierwsza warstwa, która nie przechodzi testu, to miejsce problemu.
- Obrona w głąb: każda warstwa ma własne zagrożenia i kontrole.
- Następna lekcja: **model TCP/IP i enkapsulacja** — co dokładnie dokłada każda warstwa, bajt po bajcie.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const symptoms = [
  ['java.net.ConnectException: Connection refused podczas łączenia z PostgreSQL', 4, 'na serwerze: ss -tlnp | grep 5432 (czy nasłuchuje i na jakim adresie); z klienta: nc -zv db 5432', 'host i trasa działają (przyszedł RST), ale nikt nie nasłuchuje na porcie albo nasłuchuje tylko na 127.0.0.1'],
  ['javax.net.ssl.SSLHandshakeException: PKIX path building failed', 6, 'openssl s_client -connect host:443 -showcerts; sprawdź truststore JVM (keytool -list)', 'JVM nie ufa łańcuchowi certyfikatów: brak pośredniego certyfikatu, prywatne CA, certyfikat proxy inspekcyjnego'],
  ['java.net.UnknownHostException: payments.internal', 7, 'dig payments.internal; resolvectl status; cat /etc/resolv.conf', 'nazwa nie rozwiązuje się w DNS (literówka, zły resolver, split-horizon, brak rekordu)'],
  ['ip link pokazuje „NO-CARRIER” na eth0', 1, 'ip link, ethtool eth0, sprawdzenie kabla i portu przełącznika', 'brak sygnału: odłączony kabel, wyłączony port, uszkodzony transceiver'],
  ['ip neigh pokazuje bramę 192.168.1.1 w stanie FAILED', 2, 'ip neigh, tcpdump -e arp, sprawdzenie VLAN na porcie', 'host nie dostaje odpowiedzi ARP od bramy: zły VLAN, zła podsieć, brama wyłączona'],
  ['ping 10.20.0.5 zwraca „Network is unreachable”', 3, 'ip route, ip route get 10.20.0.5', 'brak trasy do sieci docelowej (ani trasy domyślnej)'],
  ['HttpConnectTimeoutException po 10 s przy łączeniu z API w innym VPC', 4, 'nc -zv -w 3 api 443; sprawdzenie Security Group, NACL, tablic routingu; traceroute -T -p 443', 'pakiety SYN giną bez odpowiedzi: firewall/SG w trybie DROP, brak trasy powrotnej, brak peeringu'],
  ['HTTP 502 Bad Gateway z Nginxa przed aplikacją Spring Boot', 7, 'logi Nginx (upstream prematurely closed / connect() failed), curl do backendu z hosta proxy', 'proxy działa, ale nie może uzyskać poprawnej odpowiedzi od backendu (backend leży, zły port upstream)'],
  ['java.net.SocketException: Connection reset w trakcie długiego zapytania', 4, 'tcpdump na obu końcach, logi proxy/LB (idle timeout), ss -ti', 'ktoś wysłał RST w trakcie połączenia: proxy lub firewall zamknął bezczynne połączenie, serwer zrestartował się'],
  ['Wysoki licznik błędów CRC na porcie przełącznika', 1, 'show interfaces / ethtool -S, wymiana kabla lub wkładki', 'uszkodzone medium lub zakłócenia: ramki docierają przekłamane'],
  ['traceroute kończy się po trzecim skoku komunikatem „!H” (host unreachable)', 3, 'traceroute, sprawdzenie tras na routerze, ACL', 'router po drodze nie zna trasy do hosta lub filtr odrzuca ruch z informacją ICMP'],
  ['SSLHandshakeException: Received fatal alert: protocol_version', 6, 'openssl s_client -tls1_2 / -tls1_3, sprawdzenie jdk.tls.disabledAlgorithms', 'klient i serwer nie mają wspólnej wersji TLS (np. serwer tylko TLS 1.0, JVM ją wyłączyła)'],
  ['HTTP 401 Unauthorized z API mimo poprawnego połączenia', 7, 'curl -v z nagłówkiem Authorization, logi Spring Security', 'warstwy 1–6 działają; problem to uwierzytelnienie aplikacyjne (token, klucz API)'],
  ['Handshake TLS „wisi”, choć ping i połączenie TCP działają (tylko przez VPN)', 3, 'ping -M do -s 1400, tracepath, sprawdzenie MTU tunelu', 'PMTUD black hole: duże pakiety (certyfikaty) giną, bo ICMP fragmentation needed jest blokowane — problem L3 widoczny w L6'],
  ['Dwa komputery w biurze naprzemiennie tracą łączność, w logach „duplicate address detected”', 2, 'arping -D, ip neigh, tablica MAC na przełączniku', 'konflikt adresów IP: dwa MAC odpowiadają na ARP dla tego samego IP'],
];
for (const [s, layer, tool, why] of symptoms) {
  ex(`Objaw: ${s}. Przypisz warstwę OSI, podaj pierwsze narzędzie diagnostyczne i najbardziej prawdopodobną przyczynę.`, 'Zastanów się, która warstwa jako pierwsza „nie przeszła testu”.', `Warstwa: ${L(layer)}.\nNarzędzie: ${tool}.\nPrzyczyna: ${why}.`);
}
const devs = [
  ['przełącznik zarządzalny bez routingu', 'warstwa 2: decyduje po adresie MAC i VLAN; nie widzi portów ani URL'],
  ['AWS Network Load Balancer', 'warstwa 4: rozkłada połączenia TCP/UDP po IP i porcie; nie czyta HTTP (TLS może tylko zakończyć jako TLS listener)'],
  ['AWS Application Load Balancer', 'warstwa 7: routing po hoście, ścieżce, nagłówkach; kończy TLS'],
  ['Security Group w AWS', 'warstwy 3–4: stanowy filtr po IP, protokole i porcie'],
  ['ModSecurity z OWASP CRS przed aplikacją', 'warstwa 7: analizuje treść żądań HTTP (parametry, nagłówki, ciało)'],
];
for (const [d, a] of devs) ex(`Na której warstwie (warstwach) działa ${d} i czego nie jest w stanie zobaczyć?`, 'Pomyśl, jakie nagłówki musi przeczytać, by podjąć decyzję.', a + '.');
ex('Ułóż w kolejności enkapsulacji (od danych aplikacji do sygnału) i nazwij PDU: nagłówek TCP, ramka Ethernet z FCS, dane HTTP, nagłówek IP, bity na kablu.', 'Każda warstwa niżej opakowuje to, co dostała z góry.', 'dane HTTP (dane, L7) → + nagłówek TCP = segment (L4) → + nagłówek IP = pakiet (L3) → + nagłówek i stopka Ethernet = ramka (L2) → bity/sygnał (L1).');
ex('Dla każdego ataku wskaż warstwę OSI i jedną kontrolę obronną: ARP spoofing, SYN flood, SQL injection, SSL stripping, podłączenie laptopa do wolnego gniazda w sali konferencyjnej.', 'Kontrola powinna działać na tej samej lub niższej warstwie co atak.', 'ARP spoofing — L2 — Dynamic ARP Inspection z DHCP snooping.\nSYN flood — L4 — SYN cookies, ochrona DDoS, limity połączeń.\nSQL injection — L7 — zapytania parametryzowane, walidacja, WAF.\nSSL stripping — L6 — HSTS (z preload), przekierowanie i wyłącznie HTTPS.\nPodłączenie do gniazda — L1/L2 — wyłączone nieużywane porty, 802.1X/NAC.');
ex('Zespół chce blokować dostęp do ścieżki /admin aplikacji tylko z sieci biurowej. Czy wystarczy reguła w Security Group? Uzasadnij warstwami i zaproponuj rozwiązanie.', 'Security Group widzi IP i port, nie ścieżkę URL.', 'Nie: Security Group (L3–4) nie widzi ścieżki /admin, bo jest ona w nagłówku HTTP (L7) i zaszyfrowana TLS. Rozwiązania: reguła na ALB/WAF (L7) z warunkiem ścieżki i źródłowego IP, osobny listener lub osobna usługa admina z SG ograniczonym do biura, plus autoryzacja w aplikacji (Spring Security) — obrona w głąb.');
ex('Na podstawie wyjścia: „ping db.internal: 64 bytes from 10.0.3.10 time=0.4 ms” oraz „nc -zv db.internal 5432: Connection refused” wskaż warstwę problemu i trzy możliwe przyczyny.', 'Która warstwa działa, a która nie?', 'L1–L3 działają (ping z odpowiedzią), problem na L4: nic nie nasłuchuje na 5432 lub nasłuchuje na innym adresie. Przyczyny: PostgreSQL nie działa; listen_addresses = \'localhost\'; inny port; firewall hosta odrzucający z RST (REJECT).');
ex('Napisz w Javie metodę String classify(Throwable t), która zwraca warstwę (DNS, L3/L4, L4, L6, I/O) dla wyjątków UnknownHostException, NoRouteToHostException, ConnectException, SocketTimeoutException i SSLException, przeglądając łańcuch przyczyn.', 'Idź pętlą po getCause(); uważaj na kolejność (NoRouteToHostException dziedziczy po SocketException).', 'static String classify(Throwable t) {\n    for (Throwable c = t; c != null; c = c.getCause()) {\n        if (c instanceof java.net.UnknownHostException) return "DNS";\n        if (c instanceof java.net.NoRouteToHostException) return "L3";\n        if (c instanceof java.net.ConnectException) return "L4";\n        if (c instanceof java.net.SocketTimeoutException) return "L3/L4";\n        if (c instanceof javax.net.ssl.SSLException) return "L6";\n    }\n    return "I/O";\n}');
ex('Wyjaśnij, dlaczego reverse proxy kończące TLS jest jednocześnie bardzo cennym miejscem dla obrońcy i atrakcyjnym celem dla napastnika.', 'Co widzi element L7, którego nie widzi firewall L4?', 'Proxy L7 widzi odszyfrowane żądania: może logować ścieżki, nagłówki i odpowiedzi, stosować WAF, rate limiting i wykrywać ataki aplikacyjne — to bogate źródło telemetrii. Jednocześnie przechowuje klucze prywatne TLS i przetwarza dane w postaci jawnej, często ciasteczka i tokeny — przejęcie proxy daje dostęp do całego ruchu. Dlatego proxy się utwardza, aktualizuje, ogranicza dostęp administracyjny i monitoruje.');
ex('W labie Docker uruchom aplikację Spring Boot, a następnie kolejno: zatrzymaj kontener, zmień port aplikacji, odłącz kontener od sieci, podmień certyfikat na samopodpisany. Dla każdego przypadku zapisz komunikat curl i klienta Java oraz warstwę.', 'Przygotuj tabelę: zmiana → curl → Java → warstwa.', 'Przykładowe wyniki:\nzatrzymany kontener → curl: Could not resolve host lub Connection refused (zależnie od DNS Dockera) → UnknownHost/ConnectException → DNS/L4.\nzły port → Connection refused → ConnectException → L4.\nodłączona sieć → Could not resolve host / timeout → UnknownHost / HttpConnectTimeout → DNS/L3.\nsamopodpisany certyfikat → SSL certificate problem: self-signed → SSLHandshakeException PKIX → L6.');
ex('Przygotuj „kartę dyżurną” dla zespołu: dla każdej z warstw L1–L7 jedno pytanie kontrolne, jedno polecenie Linux i jedno PowerShell oraz kogo eskalować, gdy test nie przechodzi.', 'Wykorzystaj ściągę narzędzi z lekcji i strukturę zespołów w firmie.', 'Przykład wiersza: L4 — czy port przyjmuje połączenie? — nc -zv host 5432 — Test-NetConnection host -Port 5432 — eskalacja: właściciel usługi (czy działa) lub zespół sieci/chmury (Security Group, firewall). Analogicznie: L1/L2 — zespół sieci/datacenter, L3 — sieć/chmura, L6 — PKI/właściciel certyfikatu, L7 — właściciel aplikacji.');
ex('Analityk SOC dostał trzy alerty z tej samej minuty: IDS „ET SCAN Nmap SYN scan” z hosta 10.0.8.15, WAF „SQL injection attempt” z tego samego hosta i log Spring Security „20 nieudanych logowań na konto admin”. Przypisz warstwy i opisz, co mówi połączenie tych alertów.', 'Każdy alert pochodzi z innego czujnika na innej warstwie.', 'IDS — L4 (skanowanie portów), WAF — L7 (atak na aplikację), Spring Security — L7 (zgadywanie haseł). Razem: ten sam host wykonuje rozpoznanie, a potem atakuje aplikację — typowy łańcuch. Host 10.0.8.15 jest wewnętrzny, więc prawdopodobnie przejęty lub nieautoryzowany skaner: izolacja, sprawdzenie właściciela, analiza EDR.');
ex('Wyjaśnij koledze z zespołu frontendu, dlaczego „CORS error” w przeglądarce jest problemem warstwy 7, a nie sieci, i jak to sprawdzić narzędziami.', 'Czy żądanie w ogóle dotarło do serwera?', 'Przeglądarka wysłała żądanie (lub preflight OPTIONS) i dostała odpowiedź — warstwy 1–6 działają. Błąd CORS oznacza, że odpowiedź nie zawiera nagłówków Access-Control-Allow-*, których wymaga polityka Same-Origin przeglądarki (L7). Sprawdzenie: zakładka Network w DevTools, curl -v -X OPTIONS z nagłówkiem Origin, konfiguracja CORS w Spring (CorsConfigurationSource).');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111304);
const protos = [
  ['HTTP', 7], ['DNS', 7], ['SMTP', 7], ['SSH', 7], ['FTP', 7], ['DHCP', 7], ['SNMP', 7], ['LDAP', 7], ['IMAP', 7], ['NTP', 7],
  ['TCP', 4], ['UDP', 4], ['IPv4', 3], ['IPv6', 3], ['ICMP', 3], ['IPsec', 3], ['Ethernet (adresy MAC)', 2], ['ARP', 2], ['802.1Q (tagowanie VLAN)', 2], ['PPP', 2], ['NetBIOS (usługa sesji)', 5], ['kodowanie znaków UTF-8 i kompresja danych', 6],
];
for (const [p, n] of protos) add(`Do której warstwy OSI przypisuje się zwykle ${p}?`, L(n), others(n), `${p}: ${L(n)}.`);
add('Do której warstwy OSI najczęściej przypisuje się TLS?', L(6), [L(4), L(3), L(2)], 'TLS odpowiada za szyfrowanie i reprezentację danych — w modelu OSI zwykle warstwa 6 (w praktyce biblioteka aplikacji działająca nad TCP).');
const pdus = [['Jak nazywa się PDU warstwy 2?', 'Ramka', ['Pakiet', 'Segment', 'Bit']], ['Jak nazywa się PDU warstwy 3?', 'Pakiet', ['Ramka', 'Segment', 'Wiadomość']], ['Jak nazywa się PDU protokołu TCP w warstwie 4?', 'Segment', ['Ramka', 'Pakiet', 'Bit']], ['Jak nazywa się PDU protokołu UDP?', 'Datagram', ['Ramka', 'Bit', 'Pakiet ARP']], ['Jaka jest jednostka danych warstwy fizycznej?', 'Bit (symbol)', ['Ramka', 'Segment', 'Pakiet']]];
for (const [qq, a, w] of pdus) add(qq, a, w, 'Dane (L5–7) → segment/datagram (L4) → pakiet (L3) → ramka (L2) → bity (L1).');
const devices = [['Przełącznik bez routingu', 2], ['Router', 3], ['Hub', 1], ['Repeater (wzmacniak sygnału)', 1], ['Most (bridge)', 2], ['Punkt dostępowy Wi-Fi w trybie mostu', 2], ['Load balancer rozkładający połączenia TCP po IP i porcie', 4], ['Web Application Firewall', 7], ['Reverse proxy routujące po ścieżce URL', 7], ['Konwerter mediów miedź–światłowód', 1]];
for (const [d, n] of devices) add(`Na której warstwie OSI działa przede wszystkim: ${d}?`, L(n), others(n), `${d}: ${L(n)}.`);
const addrs = [['adres MAC', 2], ['adres IP', 3], ['numer portu', 4]];
for (const [a, n] of addrs) add(`Która warstwa OSI posługuje się pojęciem „${a}”?`, L(n), others(n), `MAC — L2, IP — L3, port — L4.`);
const sym = [
  ['Connection refused', 4, 'host odpowiedział RST — brak usługi na porcie'], ['PKIX path building failed', 6, 'JVM nie ufa łańcuchowi certyfikatów'], ['No route to host', 3, 'brak trasy lub ICMP host unreachable'],
  ['NO-CARRIER na interfejsie', 1, 'brak sygnału fizycznego'], ['HTTP 503 Service Unavailable', 7, 'aplikacja lub proxy odpowiada, ale usługa jest niedostępna'], ['wpis ARP bramy w stanie INCOMPLETE', 2, 'brak odpowiedzi ARP w segmencie'],
  ['Connection reset by peer w trakcie transferu', 4, 'RST w środku połączenia TCP'], ['błędy CRC na porcie', 1, 'uszkodzone medium, zakłócenia'], ['TTL exceeded w traceroute w pętli między dwoma routerami', 3, 'pętla routingu'], ['handshake_failure: no cipher suites in common', 6, 'brak wspólnych zestawów szyfrów TLS'],
];
for (const [s, n, why] of sym) add(`Komunikat lub objaw „${s}” wskazuje najpierw na warstwę:`, L(n), others(n), `${L(n)}: ${why}.`);
const atk = [['ARP spoofing', 2], ['MAC flooding', 2], ['VLAN hopping', 2], ['SYN flood', 4], ['skanowanie portów TCP', 4], ['IP spoofing', 3], ['SQL injection', 7], ['cross-site scripting (XSS)', 7], ['SSL stripping', 6], ['podsłuch przez podpięcie się do kabla', 1]];
for (const [a, n] of atk) add(`Na którą warstwę OSI jest wymierzony atak: ${a}?`, L(n), others(n), `${a}: ${L(n)}.`);
const ctl = [['Dynamic ARP Inspection', 2], ['802.1X na portach przełącznika', 2], ['lista ACL filtrująca po adresach IP', 3], ['SYN cookies', 4], ['HSTS', 6], ['Web Application Firewall', 7], ['zamknięta szafa rack i wyłączone nieużywane gniazda', 1]];
for (const [c, n] of ctl) add(`Na której warstwie działa zabezpieczenie: ${c}?`, L(n), others(n), `${c}: ${L(n)}.`);
const tools = [['ethtool eth0', 1], ['ip neigh', 2], ['ip route get 10.0.0.5', 3], ['nc -zv host 5432', 4], ['openssl s_client -connect host:443', 6], ['curl -v https://host/health', 7]];
for (const [t, n] of tools) add(`Którą warstwę sprawdza przede wszystkim polecenie „${t}”?`, L(n), others(n), `${t} — ${L(n)}.`);
add('Ping do serwera działa, ale nc -zv serwer 443 zwraca timeout. Co to najprawdopodobniej oznacza?', 'Filtr (firewall/Security Group) odrzuca bez odpowiedzi ruch na port 443, a ICMP przepuszcza', ['Serwer jest wyłączony', 'Problem z certyfikatem TLS', 'Brak wpisu ARP'], 'L3 działa (ICMP), L4 nie — pakiety SYN na 443 giną bez RST.');
add('Czym różni się „Connection refused” od „Connect timed out”?', 'Refused oznacza odpowiedź RST od hosta; timeout oznacza brak jakiejkolwiek odpowiedzi', ['To synonimy', 'Refused to błąd TLS, timeout to błąd DNS', 'Timeout oznacza zły certyfikat'], 'Refused: host i trasa działają. Timeout: pakiety giną (DROP, brak trasy, host wyłączony).');
add('Dlaczego mówi się, że OSI to model pojęciowy, a nie implementacja?', 'Internet działa na stosie TCP/IP, a OSI służy do opisu i podziału odpowiedzialności', ['Bo OSI nie ma warstwy fizycznej', 'Bo OSI został zastąpiony przez IPv6', 'Bo protokoły OSI są szyfrowane'], 'Stos protokołów OSI nie przyjął się; model warstw pozostał językiem branży.');
add('Która metoda diagnozy zaczyna od warstwy fizycznej i idzie w górę?', 'Bottom-up', ['Top-down', 'Divide and conquer', 'Follow the path'], 'Top-down zaczyna od aplikacji, divide and conquer od środka modelu.');
add('Jaka jest główna rola warstwy transportowej?', 'Komunikacja między procesami (porty), niezawodność i kontrola przepływu', ['Wybór trasy między sieciami', 'Kodowanie sygnału na kablu', 'Szyfrowanie certyfikatami'], 'L4 multipleksuje ruch wielu aplikacji na jednym hoście za pomocą portów.');
add('Jaka jest główna rola warstwy sieciowej?', 'Adresowanie logiczne i routing pakietów między sieciami', ['Kontrola przepływu danych aplikacji', 'Dostarczanie ramek w obrębie segmentu', 'Reprezentacja danych'], 'IP i routery to serce warstwy 3.');
add('Która warstwa dzieli się na podwarstwy LLC i MAC?', L(2), others(2), 'Łącze danych: LLC (sterowanie łączem logicznym) i MAC (dostęp do medium).');
add('Load balancer L7 w odróżnieniu od L4 może:', 'Kierować ruch na podstawie ścieżki URL, hosta i nagłówków HTTP', ['Działać bez adresów IP', 'Pracować tylko z UDP', 'Zastąpić przełącznik'], 'L4 widzi tylko IP i porty; L7 kończy TLS i rozumie HTTP.');
add('Co oznacza termin „obrona w głąb” w kontekście modelu OSI?', 'Stosowanie zabezpieczeń na wielu warstwach, by obejście jednej nie dawało pełnego dostępu', ['Szyfrowanie tylko na warstwie 1', 'Umieszczenie firewalla jak najgłębiej w sieci', 'Blokowanie całego ICMP'], 'Np. 802.1X (L2), ACL (L3), firewall stanowy (L4), TLS (L6), WAF i autoryzacja (L7).');
add('Który element realizuje warstwy 5–7 w typowej aplikacji Spring Boot?', 'Sama aplikacja i jej biblioteki (Tomcat, JSSE, Jackson)', ['Karta sieciowa', 'Przełącznik', 'Jądro systemu w warstwie IP'], 'Warstwy 1–4 obsługuje system operacyjny i sprzęt; wyższe — proces aplikacji.');
add('Dlaczego QUIC (HTTP/3) bywa podawany jako przykład łamania podziału warstw?', 'Łączy funkcje transportu i szyfrowanie TLS 1.3 w jednym protokole działającym nad UDP', ['Bo działa bez IP', 'Bo nie ma nagłówków', 'Bo działa wyłącznie w warstwie 1'], 'QUIC realizuje niezawodność i kontrolę przeciążenia (L4) oraz szyfrowanie (L6) w przestrzeni użytkownika.');
add('Który zestaw najlepiej opisuje informacje, jakie widzi firewall stanowy L3/L4?', 'Adresy IP, protokół, porty i stan połączenia', ['Ścieżki URL i ciasteczka', 'Treść zapytań SQL', 'Hasła użytkowników'], 'Bez inspekcji TLS i funkcji L7 firewall nie zna treści żądań.');
add('Która mnemotechnika opisuje warstwy OSI od dołu?', 'Please Do Not Throw Sausage Pizza Away', ['All People Seem To Need Data Processing', 'Never Eat Shredded Wheat', 'Every Good Boy Deserves Fun'], 'Physical, Data link, Network, Transport, Session, Presentation, Application.');
add('Która warstwa odpowiada za wykrywanie błędów transmisji w ramce sumą kontrolną FCS?', L(2), others(2), 'Ramka Ethernet kończy się polem FCS (CRC-32); przełącznik odrzuca uszkodzone ramki.');
add('W której warstwie działa protokół routingu OSPF z punktu widzenia funkcji?', L(3), others(3), 'OSPF wymienia informacje o trasach dla warstwy sieciowej i działa bezpośrednio nad IP (protokół 89).');
add('Co robi router z nagłówkiem Ethernet ramki, którą przekazuje dalej?', 'Zdejmuje go i tworzy nowy nagłówek dla następnego odcinka (nowe adresy MAC)', ['Przekazuje bez zmian', 'Szyfruje go', 'Dopisuje do niego port TCP'], 'Adresy IP zostają (poza NAT), adresy MAC zmieniają się na każdym przeskoku.');
add('Które pole zmniejsza router przy każdym przeskoku?', 'TTL (Hop Limit w IPv6)', ['Numer portu', 'Adres MAC źródłowy w pakiecie IP', 'Numer sekwencyjny TCP'], 'TTL chroni przed pętlami; przy wartości 0 router odrzuca pakiet i wysyła ICMP Time Exceeded.');
add('Aplikacja Java zwraca UnknownHostException. Jaki jest pierwszy krok diagnozy?', 'Sprawdzić rozwiązywanie nazwy (dig, resolvectl, /etc/resolv.conf)', ['Zwiększyć timeout połączenia', 'Wymienić certyfikat', 'Zrestartować przełącznik'], 'Bez adresu IP żadna niższa warstwa nie zacznie pracy.');
add('Która warstwa zapewnia, że dwie aplikacje na tym samym hoście otrzymują właściwe dane?', L(4), others(4), 'Porty źródłowe i docelowe identyfikują procesy (multipleksacja).');
add('Przełącznik L3 to urządzenie, które:', 'Przełącza ramki jak przełącznik i dodatkowo routuje pakiety między VLAN-ami', ['Działa tylko na warstwie 1', 'Zastępuje firewall L7', 'Szyfruje ruch TLS'], 'Urządzenia rzadko są „jednowarstwowe”.');
add('Gdzie powinien działać mechanizm wykrywający przejęcie konta przez credential stuffing?', 'W warstwie 7 — w aplikacji, IdP lub WAF analizującym próby logowania', ['W warstwie 1', 'W warstwie 2 na przełączniku', 'Wyłącznie w routerze brzegowym'], 'Atak używa poprawnego HTTP i TLS; wyróżnia go dopiero wzorzec logowań.');
add('Dlaczego blokowanie całego ICMP na firewallu może psuć połączenia TLS przez VPN?', 'Blokuje komunikaty Fragmentation Needed potrzebne do Path MTU Discovery', ['Bo TLS używa ICMP do wymiany kluczy', 'Bo ICMP przenosi certyfikaty', 'Bo VPN działa na warstwie 1'], 'Duże pakiety giną bez informacji zwrotnej (PMTUD black hole).');
add('Który opis najlepiej pasuje do warstwy sesji?', 'Nawiązywanie, utrzymywanie i kończenie dialogu między aplikacjami', ['Wybór trasy przez Internet', 'Kodowanie bitów w sygnał', 'Adresowanie fizyczne MAC'], 'W TCP/IP tę rolę pełnią aplikacje i biblioteki (np. sesje RPC).');
add('Komunikat „certificate has expired” z curl dotyczy warstwy:', L(6), others(6), 'Weryfikacja certyfikatu jest częścią zestawiania TLS.');
add('Który element zobaczy nagłówek User-Agent żądania HTTPS bez inspekcji TLS?', 'Tylko element kończący TLS: reverse proxy, LB L7 lub sama aplikacja', ['Każdy router po drodze', 'Przełącznik dostępowy', 'Firewall stanowy L4'], 'Nagłówki HTTP są zaszyfrowane wewnątrz TLS.');
add('Które urządzenie nie ogranicza domeny kolizyjnej ani rozgłoszeniowej, bo tylko powtarza sygnał?', 'Hub', ['Przełącznik', 'Router', 'Firewall stanowy'], 'Hub działa w warstwie 1; przełącznik dzieli domeny kolizyjne, router — rozgłoszeniowe.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
