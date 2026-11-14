// Generuje lekcje _ns_03_ip_i_routing/10_IcmpPingTraceroute.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_10_icmp_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/10_IcmpPingTraceroute.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { ICMP, ICMP6, analyze, MTR, traceroute } = require('./ns03_10_icmp_model.cjs');
const buildQuiz = require('./ns03_10_icmp_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const mtrTable = (hops) => `HOST                Loss%   Avg   Best  Wrst\n${hops.map((h, i) => `${String(i + 1).padStart(2)}. ${h.host.padEnd(16)} ${String(h.loss).padStart(4)}%  ${String(h.avg).padStart(5)} ${String(h.best).padStart(5)} ${String(h.worst).padStart(5)}`).join('\n')}`;

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'ICMP: system nerwowy sieci, który wszyscy chcą wyłączyć', ...img('ns03-10-traceroute.svg', 'Mechanizm traceroute: pakiet z TTL 1 wygasa w R1, który odsyła ICMP Time Exceeded; TTL 2 wygasa w R2; TTL 3 w R3; pakiet z TTL 4 dociera do serwera, który odpowiada Port Unreachable dla UDP lub Echo Reply dla ICMP. Gwiazdka oznacza brak odpowiedzi.'),
    body:
`**ICMP (Internet Control Message Protocol, protokół IP nr 1; ICMPv6 — nr 58)** to kanał, którym sieć mówi nadawcy, co poszło nie tak: „nie ma trasy”, „host nie odpowiada”, „port zamknięty”, „pakiet za duży”, „TTL się skończył”. Na nim opierają się dwa najczęściej używane narzędzia diagnostyczne: **ping** (Echo Request/Reply) i **traceroute** (Time Exceeded).

Problem w tym, że ICMP jest często traktowany jako „niebezpieczny” i blokowany w całości — co psuje sieć w subtelny sposób (PMTUD, IPv6), a jednocześnie wyniki ping/traceroute bywają **źle interpretowane**: 40% strat na jednym routerze w mtr wywołuje panikę i zgłoszenie do operatora, choć to tylko router, który oszczędza CPU i ogranicza odpowiedzi ICMP.

W tej lekcji:
- typy i kody ICMP (echo, unreachable, time exceeded, fragmentation needed) i ICMPv6,
- TTL i Hop Limit jako fundament traceroute,
- traceroute UDP, ICMP i **TCP** (\`traceroute -T\`, \`tcptraceroute\`) — który przejdzie przez firewall,
- interpretacja gwiazdek, asymetrii i opóźnień,
- **mtr** i odróżnianie strat realnych od rate-limitu ICMP,
- dlaczego blokowanie **całego** ICMP psuje sieć i co blokować z głową,
- Java: dlaczego \`InetAddress.isReachable()\` nie robi tego, co myślisz.

Zadanie z planu: **analiza 5 wyników mtr — gdzie jest problem, a gdzie router tylko ignoruje ICMP**. Cele: Network+ N10-009 (narzędzia: ping, traceroute/tracert, pathping, mtr; troubleshooting), CCNA v2 (ping, traceroute, interpretacja), Security+ i CySA+ (ICMP w rekonesansie, tunelowanie ICMP, polityka filtrowania).` },

  { type: 'DEFINITION', heading: 'Słownik ICMP i narzędzi', body:
`- **Typ / kod ICMP** — rodzaj komunikatu (np. 3 = Destination Unreachable) i jego wariant (kod 4 = fragmentation needed).
- **Echo Request (8) / Echo Reply (0)** — ping; ICMPv6: 128/129.
- **Destination Unreachable (3)** — kody: 0 net, 1 host, 3 port (odpowiedź na UDP do zamkniętego portu), 4 **fragmentation needed and DF set** (PMTUD), 13 administratively prohibited (firewall z REJECT).
- **Time Exceeded (11)** — kod 0: TTL spadł do 0 w trasie (traceroute), kod 1: nie złożono fragmentów na czas.
- **Redirect (5)** — router podpowiada lepszą bramę (zwykle wyłączany na serwerach i routerach brzegowych).
- **ICMPv6** — oprócz powyższych obejmuje **Neighbor Discovery** (NS/NA 135/136 — zamiast ARP), **Router Solicitation/Advertisement** (133/134 — SLAAC), **Packet Too Big** (2), MLD. Bez ICMPv6 IPv6 nie działa.
- **TTL / Hop Limit** — licznik skoków w nagłówku IPv4/IPv6; router zmniejsza go o 1, przy 0 odrzuca pakiet i wysyła Time Exceeded.
- **RTT** — round-trip time, czas tam i z powrotem.
- **Rate limiting ICMP** — routery generują ICMP w procesorze sterującym (control plane), więc ograniczają ich liczbę (np. Linux: \`net.ipv4.icmp_ratelimit\`); skutek: straty i wysokie opóźnienia **tylko na danym skoku** w traceroute/mtr.
- **traceroute** (Linux: UDP 33434+; \`-I\` ICMP; \`-T\` TCP SYN), **tracert** (Windows, ICMP), **tcptraceroute**, **pathping** (Windows: trasa + statystyka strat), **mtr** (My Traceroute: ciągły pomiar per skok).
- **Asymetria** — droga powrotna odpowiedzi ICMP może być inna niż droga pakietu w przód; traceroute pokazuje tylko drogę „tam”.
- **ICMP tunneling** — ukrywanie danych w polu danych Echo (eksfiltracja/C2); wykrywane po nietypowym rozmiarze i częstości.` },

  { type: 'ANALOGY', heading: 'Listy polecone z potwierdzeniem odbioru', body:
`- **Ping** to list z prośbą „odpisz, że dotarło” — wiesz, że adresat istnieje i ile trwała podróż w obie strony. Brak odpowiedzi nie znaczy, że adresata nie ma: może po prostu nie odpisuje na takie listy (firewall).
- **Traceroute** to sprytny trik: wysyłasz list z pieczątką „ważny przez 1 sortownię”, potem „przez 2”, „przez 3”… Każda sortownia, w której list wygasa, odsyła Ci kartkę „list przeterminowany u nas” — z jej adresem. Tak poznajesz kolejne sortownie na trasie.
- **Gwiazdka** to sortownia, która nie odsyła kartek (albo ma limit: „odsyłamy najwyżej 10 kartek na minutę”) — list mógł przejść dalej bez problemu.
- **mtr** to wysyłanie takich listów co sekundę przez kilka minut i liczenie, ile kartek wróciło z każdej sortowni. Jeśli tylko jedna sortownia „gubi” kartki, a do adresata wszystko dochodzi — sortownia jest leniwa w pisaniu kartek, nie gubi listów. Jeśli od sortowni nr 4 aż do adresata brakuje 12% — listy naprawdę giną gdzieś za sortownią 3.
- **Blokowanie całego ICMP** to zakaz wysyłania jakichkolwiek kartek zwrotnych — także tej najważniejszej: „paczka za duża na naszą ciężarówkę, przepakuj” (fragmentation needed). Nadawca w nieskończoność wysyła za duże paczki, a odbiorca nic nie dostaje.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Najważniejsze typy ICMP i ICMPv6', ...img('ns03-10-types.svg', 'Tabela typów ICMP: 0 Echo Reply, 3/0 net unreachable, 3/1 host unreachable, 3/3 port unreachable, 3/4 fragmentation needed (nie wolno blokować — PMTUD), 3/13 administratively prohibited, 5 Redirect, 8 Echo Request, 11/0 TTL exceeded, 11/1 reassembly time exceeded. ICMPv6 jest obowiązkowy.'),
    body:
`Ściąga typów — z tym, co dany komunikat mówi diagnoście. W Wiresharku filtruj \`icmp.type == 3 && icmp.code == 4\`, \`icmp.type == 11\`, \`icmpv6.type == 2\`.`,
    code:
`ICMP (IPv4)  typ/kod  znaczenie
${ICMP.map(([t, c, n, d]) => `  ${String(t).padStart(2)}/${String(c).padEnd(3)} ${n.padEnd(62)} ${d}`).join('\n')}

ICMPv6       typ   znaczenie
${ICMP6.map(([t, n]) => `  ${String(t).padStart(4)}   ${n}`).join('\n')}` },

  { type: 'CODE_BASIC', heading: 'ping, traceroute, mtr — polecenia i jak czytać wynik', ...img('ns03-10-tracemodes.svg', 'Porównanie trybów traceroute: UDP na porty 33434 i wyżej kończony Port Unreachable, często blokowany; ICMP Echo kończony Echo Reply; TCP SYN na port usługi kończony SYN-ACK lub RST, przechodzi tam, gdzie działa usługa; mtr łączy traceroute z ciągłym pomiarem strat i opóźnień.'),
    body:
`Zestaw poleceń z najważniejszymi przełącznikami. Dla diagnozy aplikacji najcenniejszy jest **traceroute/mtr w trybie TCP na port usługi** — idzie tą samą drogą i przez te same reguły firewalla co prawdziwy ruch.`,
    code:
`# ping
ping -c 5 -i 0.2 api.example.com        # 5 pakietów co 200 ms (Linux); Windows: ping -n 5
ping -c 3 -s 1472 -M do 10.0.0.1        # test MTU (lekcja 01, 11)
ping -6 ipv6.google.com                 # IPv6
#  64 bytes from 93.184.216.34: icmp_seq=1 ttl=56 time=11.8 ms   ← TTL 56 = start 64, 8 skoków
#  --- 5 packets transmitted, 5 received, 0% packet loss, rtt min/avg/max/mdev = 11.2/11.9/12.8/0.5 ms

# traceroute — trzy tryby
traceroute -n api.example.com           # UDP 33434+ (Linux), -n: bez DNS (szybciej, bez mylących nazw)
traceroute -n -I api.example.com        # ICMP Echo (jak Windows tracert)
sudo traceroute -n -T -p 443 api.example.com   # TCP SYN na 443 — przechodzi tam, gdzie działa HTTPS
tracert -d api.example.com              # Windows
#  3  10.10.0.1  7.9 ms  8.1 ms  7.7 ms
#  4  * * *                              ← router nie odpowiada (lub limit) — NIE oznacza awarii
#  5  198.51.100.9  12.4 ms  12.1 ms *

# mtr — traceroute + ciągły pomiar
mtr -rwzbc 100 api.example.com          # raport 100 cykli: -w szeroko, -z AS, -b IP i nazwa
mtr -T -P 443 -rwc 100 api.example.com  # TCP na port 443
# Windows: pathping -n api.example.com ; PowerShell: Test-NetConnection api.example.com -Port 443 -TraceRoute` },

  { type: 'STEP_BY_STEP', heading: 'Zadanie z planu: analiza 5 wyników mtr', ...img('ns03-10-mtr.svg', 'Pięć wyników mtr z analizą: A — 40% strat tylko na jednym skoku pośrednim, cel bez strat: rate limiting ICMP; B — około 12% strat od skoku 4 do celu: realny problem; C — skok opóźnienia o 90 ms na łączu transatlantyckim utrzymany do celu: realne opóźnienie; D — wysokie opóźnienie tylko na jednym routerze: wolne ICMP; E — duża zmienność już od pierwszego skoku: Wi-Fi lub przeciążenie lokalne.'),
    body:
`Trzy reguły interpretacji (stosuj w tej kolejności):
1. **Patrz najpierw na ostatni wiersz (cel).** Jeśli cel ma 0% strat i stabilne opóźnienie — ścieżka działa; straty na skokach pośrednich to ograniczanie ICMP przez routery (one przekazują ruch sprzętowo, a odpowiedzi ICMP generują „z doskoku” w procesorze).
2. **Realna strata zaczyna się na pewnym skoku i utrzymuje do celu.** Podejrzany jest odcinek między ostatnim „czystym” skokiem a pierwszym stratnym (lub cokolwiek dalej). Pamiętaj o asymetrii — problem może być na drodze powrotnej.
3. **Opóźnienie liczy się, gdy skok w górę utrzymuje się do celu** (odległość, łącze transoceaniczne, przeciążenie). Pojedynczy wysoki średni czas na jednym routerze = wolne generowanie ICMP. Duża zmienność od **pierwszego** skoku = problem lokalny (Wi-Fi, przeciążone łącze domowe).

Wyniki i wnioski (model lekcji):
${Object.entries(MTR).map(([k, hops]) => `**${k}**: ${hops.map((h) => `${h.host} ${h.loss}%/${h.avg} ms`).join(' → ')}\n   → ${analyze(hops).join('; ')}`).join('\n')}

Do zgłoszenia u operatora/dostawcy chmury dołącz: \`mtr -rwzbc 200\` w **obu kierunkach** (z serwera do klienta też), czas testu, protokół (\`-T -P 443\`, jeśli ICMP jest filtrowany) i opis objawu w aplikacji.` },

  { type: 'NOTE', heading: 'Dlaczego blokowanie całego ICMP psuje sieć', body:
`„Zablokujmy ICMP, bo ping to rekonesans” — to jedna z najdroższych „optymalizacji bezpieczeństwa”:
- **PMTUD black hole** — bez ICMP 3/4 (IPv4) i Packet Too Big (IPv6) nadawca nie dowie się, że pakiet jest za duży dla ścieżki (tunel, VPN, PPPoE). Objaw: połączenie TCP nawiązuje się (małe pakiety), ping działa, a **TLS handshake lub duże odpowiedzi HTTP wiszą** (lekcja 11).
- **IPv6 przestaje działać** — Neighbor Discovery, Router Advertisement i PMTUD to ICMPv6. RFC 4890 opisuje, które typy filtrować, a których nie wolno.
- **Diagnoza staje się niemożliwa** — brak traceroute, brak „port unreachable” (aplikacje czekają na timeout zamiast natychmiastowego błędu), brak „net unreachable”.
- **Monitoring** — systemy oparte na ping widzą fałszywe awarie.

Co robić zamiast tego: **przepuszczaj** Destination Unreachable (szczególnie 3/4), Time Exceeded, Packet Too Big i niezbędne ICMPv6 (ND/RA tylko na łączu); **Echo** dopuszczaj z rate limitem (lub tylko z sieci zarządzania/monitoringu); **blokuj** Redirect, Source Quench, Timestamp, Address Mask, Router Advertisement z nieuprawnionych źródeł (RA Guard). W chmurze security groups domyślnie blokują ICMP — dodaj regułę dla „ICMP – Destination Unreachable – fragmentation needed” (AWS: typ 3 kod 4), jeśli masz tunele lub nietypowe MTU.` },

  { type: 'CODE_PRACTICAL', heading: 'Java: isReachable() to nie ping — sprawdzaj usługę', body:
`\`InetAddress.isReachable(timeout)\` w JDK próbuje wysłać **ICMP Echo tylko, jeśli proces ma uprawnienia** (root lub \`CAP_NET_RAW\`); w przeciwnym razie łączy się **TCP na port 7 (echo)** i uznaje host za osiągalny także przy odpowiedzi RST. Na Windows zachowanie zależy od wersji JDK. W kontenerze bez uprawnień i z firewallem wynik bywa fałszywie negatywny lub pozytywny. Dla aplikacji liczy się **dostępność usługi**, nie ICMP — sprawdzaj połączenie TCP na właściwy port lub endpoint health.`,
    code:
`import java.io.IOException;
import java.net.*;
import java.time.Duration;

public class ReachabilityCheck {
    // to sprawdza, czy USŁUGA przyjmuje połączenia — a nie czy host odpowiada na ICMP
    static String tcpCheck(String host, int port, Duration timeout) {
        long t0 = System.nanoTime();
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(host, port), (int) timeout.toMillis());
            return "OK %d ms".formatted((System.nanoTime() - t0) / 1_000_000);
        } catch (ConnectException e) {          // RST — host osiągalny, port zamknięty (lub REJECT)
            return "ODMOWA: " + e.getMessage();
        } catch (SocketTimeoutException e) {    // brak odpowiedzi — firewall DROP, brak trasy powrotnej, host wyłączony
            return "TIMEOUT";
        } catch (NoRouteToHostException e) {    // często ICMP host/net unreachable
            return "BRAK TRASY: " + e.getMessage();
        } catch (UnknownHostException e) {
            return "DNS: nieznany host";
        } catch (IOException e) {
            return "BŁĄD: " + e;
        }
    }

    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "example.com";
        System.out.println("isReachable (ICMP lub TCP/7): " + InetAddress.getByName(host).isReachable(2000));
        System.out.println("TCP 443: " + tcpCheck(host, 443, Duration.ofSeconds(2)));
        System.out.println("TCP 81 : " + tcpCheck(host, 81, Duration.ofSeconds(2)));   // zwykle TIMEOUT (DROP) lub ODMOWA
    }
}
// Spring Boot: health indicator do zależności (DataSource, Redis, własny HTTP endpoint) zamiast „pingu” hosta.` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **„Serwis jest wolny”** — mtr TCP do endpointu z miejsca, gdzie działa aplikacja (pod, VM), a nie z laptopa; porównanie z mtr z innej lokalizacji odróżnia problem sieci od problemu aplikacji.
- **Timeout vs natychmiastowy błąd** — klient dostaje szybkie „Connection refused” lub „No route to host” dzięki ICMP/RST; przy DROP czeka do timeoutu. Ustaw rozsądne \`connectTimeout\` w klientach HTTP/JDBC.
- **Kubernetes/chmura** — security groups i NetworkPolicy domyślnie nie przepuszczają ICMP; ping między podami może „nie działać”, choć TCP działa. Load balancery często nie odpowiadają na ping wcale — testuj portem.
- **Monitoring** — blackbox exporter (Prometheus) z modułami \`icmp\`, \`tcp_connect\`, \`http_2xx\`; alarmy na TCP/HTTP, ICMP tylko jako dodatkowy sygnał.

**Blue team:**
- **Rekonesans**: ping sweep (\`nmap -sn\`), traceroute do mapowania sieci, ICMP Timestamp/Address Mask do fingerprintingu — wykrywanie w IDS (Suricata/Zeek: wiele Echo do kolejnych adresów).
- **ICMP tunneling** (ptunnel, icmpsh) — dane ukryte w Echo: alarm na Echo o nietypowym rozmiarze, dużej liczbie pakietów do jednego zewnętrznego adresu, zmiennej treści payloadu; ogranicz Echo do Internetu z hostów, które go nie potrzebują.
- **Smurf / ICMP flood** — historyczne ataki wzmacniające na broadcast (dziś \`no ip directed-broadcast\` domyślnie) i zalewanie Echo; rate limiting i CoPP na routerach.
- **ICMP Redirect** — możliwość przekierowania ruchu (MITM) w LAN; wyłącz akceptowanie (\`net.ipv4.conf.all.accept_redirects=0\`).
- Mapowanie: Network+ (narzędzia), CCNA (ping/traceroute), Security+ (rekonesans, filtrowanie), CySA+ (analiza ruchu), BTL1 (analiza pcap z ICMP).` },

  { type: 'PITFALL', heading: 'Częste błędy interpretacji', body:
`- **„Router na skoku 3 gubi 40% pakietów”** — jeśli kolejne skoki i cel mają 0%, to rate limiting ICMP, nie strata ruchu.
- **„Ping nie działa, więc serwer leży”** — ICMP może być filtrowany; sprawdź TCP na port usługi.
- **„Traceroute kończy się gwiazdkami, więc cel jest nieosiągalny”** — firewall celu może blokować UDP traceroute; użyj \`-T -p 443\`.
- **Ufanie nazwom DNS w traceroute** — rekordy PTR bywają mylące lub nieaktualne; używaj \`-n\` i sprawdzaj AS (\`mtr -z\`).
- **Ignorowanie asymetrii** — problem może być na drodze powrotnej; rób mtr z obu stron.
- **Pomiar z laptopa na Wi-Fi** zamiast z serwera aplikacji — mierzysz swoje Wi-Fi.
- **Zbyt krótki test** — 10 pakietów to za mało, by wykryć 1–2% strat; mtr minimum 100 cykli.
- **Blokowanie całego ICMP/ICMPv6** — PMTUD black hole, zepsute IPv6.
- **\`InetAddress.isReachable()\` jako health check** — zależny od uprawnień i portu 7; sprawdzaj usługę.
- **Wnioskowanie o systemie wyłącznie z TTL** — tylko heurystyka (64/128/255), łatwo zmienić.` },

  { type: 'CODE_WRONG', heading: 'Źle: firewall „bez ICMP” i health check na ping', body:
`Polityka, która wygląda bezpiecznie, a powoduje zawieszone połączenia TLS przez VPN, niedziałające IPv6 i fałszywe alarmy monitoringu.`,
    code:
`# nftables — „blokujemy wszystko, co ICMP”
nft add rule inet filter input  ip protocol icmp drop
nft add rule inet filter input  ip6 nexthdr icmpv6 drop          # IPv6 przestaje działać (ND, RA)
nft add rule inet filter output ip protocol icmp drop            # nikt nie dostanie „fragmentation needed” od nas

// Java: health check zależności
boolean dbUp = InetAddress.getByName("db.internal").isReachable(1000);   // bez CAP_NET_RAW: TCP/7 → zwykle false
// alarm „baza leży”, choć PostgreSQL na 5432 działa` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: ICMP z głową i health check na usługę', body:
`Przepuszczanie komunikatów niezbędnych do działania sieci, ograniczanie Echo, blokowanie niebezpiecznych typów; monitoring sprawdza usługę, a ICMP jest dodatkowym sygnałem.`,
    code:
`table inet filter {
  chain input {
    type filter hook input priority 0; policy drop;
    ct state established,related accept                                   # w tym ICMP błędów dla naszych połączeń
    ip protocol icmp icmp type { destination-unreachable, time-exceeded } accept
    ip protocol icmp icmp type echo-request limit rate 10/second accept    # ping z limitem
    ip6 nexthdr icmpv6 icmpv6 type { destination-unreachable, packet-too-big, time-exceeded, parameter-problem } accept
    ip6 nexthdr icmpv6 icmpv6 type { nd-neighbor-solicit, nd-neighbor-advert, nd-router-advert } ip6 hoplimit 255 accept
    ip6 nexthdr icmpv6 icmpv6 type echo-request limit rate 10/second accept
  }
}
# sysctl: net.ipv4.conf.all.accept_redirects=0, net.ipv6.conf.all.accept_redirects=0, net.ipv4.conf.all.send_redirects=0

// health check: połączenie z usługą (Spring Boot: DataSourceHealthIndicator, własny HTTP endpoint /actuator/health)` },

  { type: 'WHEN_TO_USE', heading: 'Które narzędzie do jakiego pytania', body:
`Ściąga diagnosty:`,
    code:
`PYTANIE                                               NARZĘDZIE
czy host odpowiada / jakie jest RTT                      ping (pamiętaj: ICMP może być filtrowany)
czy usługa przyjmuje połączenia                          nc -vz host port, curl -v, Test-NetConnection -Port, tcpCheck w Javie
którędy idzie ruch                                       traceroute -n (UDP/ICMP), traceroute -T -p <port> przez firewalle
czy i gdzie giną pakiety / skąd opóźnienie               mtr -rwzbc 100 (TCP: -T -P <port>), Windows: pathping
jaki MTU ma ścieżka                                      ping -M do -s, tracepath (lekcja 11)
jaki ICMP przychodzi w odpowiedzi                        tcpdump -ni any icmp or icmp6
zgłoszenie do operatora                                   mtr w obu kierunkach, 200+ cykli, czas, protokół
monitoring produkcji                                     blackbox exporter: tcp_connect/http_2xx; icmp pomocniczo` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- ICMP (protokół 1) i ICMPv6 (58) przenoszą komunikaty sterujące: Echo (8/0), Destination Unreachable (3: net, host, port, **fragmentation needed**, prohibited), Time Exceeded (11), Redirect (5); ICMPv6 dodatkowo ND, RA, Packet Too Big.
- Traceroute zwiększa TTL o 1 i zbiera Time Exceeded; tryby UDP/ICMP/TCP — TCP na port usługi przechodzi przez firewalle.
- Interpretacja mtr: patrz na cel; strata realna trwa od pewnego skoku do celu; strata/opóźnienie tylko na jednym skoku = rate limiting ICMP; zmienność od 1. skoku = problem lokalny; mierz z obu stron.
- Nie blokuj całego ICMP: PMTUD black hole i niedziałające IPv6; przepuszczaj błędy, limituj Echo, blokuj Redirect.
- Java: \`isReachable()\` to nie ping — sprawdzaj usługę TCP/HTTP; rozróżniaj odmowę, timeout i brak trasy.
- Blue team: ping sweep, ICMP tunneling, redirect — wykrywanie i polityka filtrowania.
- Następna lekcja: **fragmentacja i Path MTU Discovery** — Failure Lab z zawieszonym TLS i MSS clampingiem.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [k, hops] of Object.entries(MTR)) {
  ex(`Zadanie z planu — mtr ${k}:\n${mtrTable(hops)}\nGdzie jest problem (jeśli jest) i co zrobisz dalej?`,
    'Najpierw cel; potem czy strata/opóźnienie utrzymuje się do celu; zmienność od pierwszego skoku.',
    `${analyze(hops).join('; ')}. ${k === 'A' || k === 'D' ? 'Nie zgłaszaj problemu operatorowi — to zachowanie control plane routera.' : k === 'B' ? 'Zbierz mtr w obu kierunkach (także TCP -T -P 443), zgłoś operatorowi odcinek isp-core-1 → isp-edge-fra z czasem testu.' : k === 'C' ? 'To fizyka (łącze transatlantyckie ~90 ms); rozważ region bliżej użytkowników lub CDN.' : 'Sprawdź Wi-Fi (sygnał, kanał), obciążenie łącza domowego, test kablem.'}`);
}
const tr = traceroute(['R1 10.0.0.1', 'R2 198.51.100.1', 'R3 203.0.113.9', 'serwer 93.184.216.34']);
ex('Opisz, co dzieje się w kolejnych krokach traceroute do serwera położonego 4 skoki dalej (3 routery + cel).', 'TTL 1..4, kto odsyła jaki ICMP.', tr.map((t) => `TTL=${t.ttl}: ${t.reply}`).join('; '));
for (const [ttl, start] of [[57, 64], [118, 128], [244, 255], [49, 64], [110, 128]]) {
  ex(`Ping zwraca ttl=${ttl}. Oszacuj system operacyjny/urządzenie i liczbę skoków. Dlaczego to tylko heurystyka?`, 'Najbliższa typowa wartość startowa powyżej: 64, 128, 255.', `Start ${start} (${start === 64 ? 'Linux/macOS/Android/iOS' : start === 128 ? 'Windows' : 'urządzenie sieciowe, np. router'}), ${start - ttl} skoków. Heurystyka: TTL można zmienić (sysctl, rejestr), NAT/proxy/load balancer odpowiada swoim TTL, droga powrotna może mieć inną długość.`);
}
const more = [
  ['Traceroute UDP do serwera kończy się na skoku 9 gwiazdkami aż do 30, a curl https://serwer działa. Wyjaśnij i pokaż lepszy test.', 'Firewall celu, tryby traceroute.', 'Firewall serwera (lub przed nim) odrzuca bez odpowiedzi pakiety UDP na porty 33434+ i nie odsyła Port Unreachable — traceroute nie wie, że dotarł do celu, i dalej zwiększa TTL. Ścieżka działa (curl OK). Lepszy test: sudo traceroute -n -T -p 443 serwer lub mtr -T -P 443 -rwc 100 serwer — SYN na port usługi przechodzi przez te same reguły co prawdziwy ruch i kończy się SYN-ACK.'],
  ['Napisz regułę nftables, która przepuszcza niezbędne ICMP/ICMPv6, limituje ping i blokuje resztę ICMP. Uzasadnij każdy element.', 'RFC 4890, PMTUD, ND.', 'Patrz blok CODE_RIGHT: established/related (błędy ICMP dotyczące naszych połączeń), destination-unreachable i time-exceeded (diagnoza, PMTUD — w tym 3/4), echo-request z limitem 10/s (monitoring bez floodu), ICMPv6 packet-too-big (PMTUD w IPv6 — routery nie fragmentują), parameter-problem, ND/RA z hoplimit 255 (tylko z łącza), reszta (redirect, timestamp, address mask, router advertisement z zewnątrz) blokowana przez policy drop.'],
  ['Aplikacja Java łączy się z bazą danych w innej podsieci; po zmianie reguł firewalla połączenie kończy się po 30 s błędem „Connect timed out”, a wcześniej szybkim „Connection refused”. Co się zmieniło?', 'DROP vs REJECT.', 'Wcześniej firewall (lub host) odpowiadał RST lub ICMP 3/13/3/3 (REJECT/port zamknięty) — klient natychmiast dostawał ConnectException. Teraz reguła robi DROP (brak odpowiedzi), więc klient czeka do connectTimeout (domyślnie długi). Dla aplikacji: ustaw krótki connectTimeout (np. 3 s w HikariCP connectionTimeout / sterowniku), a w sieci rozważ REJECT dla ruchu wewnętrznego (szybsza diagnoza).'],
  ['Wyjaśnij, dlaczego InetAddress.isReachable() może zwracać false dla działającego serwera i true dla wyłączonej usługi.', 'Uprawnienia ICMP, TCP port 7, RST.', 'Bez uprawnień do surowych gniazd JDK próbuje połączenia TCP na port 7 (echo). Jeśli firewall odrzuca ruch na port 7 przez DROP lub host nie odpowiada na ICMP — false, mimo że serwer i jego usługi działają. Jeśli host odpowie RST na port 7 (port zamknięty, ale host żyje) — true, choć interesująca nas usługa (np. 5432) może nie działać. Wynik mówi o hoście, nie o usłudze. Sprawdzaj usługę: Socket.connect na właściwy port lub health endpoint.'],
  ['Jak wykryć ICMP tunneling w ruchu sieciowym? Podaj trzy cechy i źródło danych.', 'Rozmiar, częstość, zawartość payloadu.', 'Cechy: Echo Request/Reply o nietypowym, zmiennym rozmiarze (zamiast 64/84 B lub 32 B Windows), duża liczba pakietów Echo do jednego zewnętrznego adresu przez długi czas, zawartość payloadu niebędąca typowym wzorcem (losowe/zaszyfrowane dane zamiast sekwencji bajtów), niedopasowanie Request/Reply. Źródła: Zeek (icmp logi, conn.log), Suricata (reguły na rozmiar i treść), NetFlow/IPFIX (wolumen ICMP per host), pcap. Zapobieganie: ogranicz Echo do Internetu z hostów, które tego nie potrzebują.'],
  ['Co to jest ping sweep i jak go wykryć oraz ograniczyć w sieci firmowej?', 'nmap -sn, wiele adresów.', 'Ping sweep to wysyłanie Echo (i/lub innych sond: TCP SYN 443, ARP w LAN) do wielu adresów w celu ustalenia aktywnych hostów (nmap -sn 10.0.0.0/24). Wykrywanie: IDS — jeden źródłowy adres wysyła Echo do wielu kolejnych adresów w krótkim czasie (Suricata/Zeek scan detection), NetFlow. Ograniczanie: blokada Echo między segmentami użytkowników, filtrowanie na brzegu, segmentacja; w LAN ARP-sweep i tak zadziała, więc ważniejsza jest detekcja i reagowanie.'],
  ['Dlaczego na Linuksie warto wyłączyć akceptowanie ICMP Redirect na serwerach i jak to zrobić?', 'MITM, sysctl.', 'Redirect pozwala routerowi (lub atakującemu podszywającemu się pod router w LAN) zmienić bramę dla danego celu w tablicy hosta — możliwe przekierowanie ruchu przez maszynę atakującego (MITM). Na serwerach z poprawną konfiguracją redirecty nie są potrzebne. sysctl: net.ipv4.conf.all.accept_redirects=0, net.ipv4.conf.default.accept_redirects=0, net.ipv6.conf.all.accept_redirects=0, net.ipv4.conf.all.secure_redirects=0, a na hostach niebędących routerami także send_redirects=0; utrwalić w /etc/sysctl.d/.'],
  ['W AWS dwie instancje w tej samej VPC nie odpowiadają sobie na ping, a połączenie TCP 8080 działa. Wyjaśnij i popraw konfigurację.', 'Security groups domyślnie bez ICMP.', 'Security group dopuszcza tylko zdefiniowane protokoły/porty (TCP 8080), a ICMP nie ma w regułach — Echo jest odrzucane. Ping nie jest potrzebny do działania aplikacji, ale do diagnozy można dodać regułę ICMP Echo Request (typ 8) z zakresu VPC lub z SG monitoringu. Warto też dopuścić ICMP typ 3 kod 4 (fragmentation needed) przy tunelach/VPN i nietypowym MTU — inaczej PMTUD black hole.'],
  ['Opisz, czym różni się pathping (Windows) od tracert i jak go użyć do zgłoszenia problemu.', 'Statystyka strat per skok.', 'tracert pokazuje jednorazowo kolejne skoki (3 próby na skok). pathping najpierw odkrywa trasę jak tracert, a potem przez kilka minut (domyślnie 100 zapytań na skok) wysyła pakiety do każdego skoku i liczy straty oraz opóźnienia — odpowiednik mtr na Windows. Kolumny „Lost/Sent” rozróżniają straty na samym routerze i na łączu do niego. Do zgłoszenia: pathping -n -q 200 cel, czas testu, oraz (jeśli możliwe) wynik z drugiej strony.'],
  ['Dlaczego ping do load balancera w chmurze często „nie działa”, mimo że aplikacja działa? Jak monitorować taki endpoint?', 'LB nie odpowiada na ICMP.', 'Load balancery (AWS ALB/NLB, wiele usług PaaS) nie odpowiadają na ICMP Echo — obsługują tylko skonfigurowane listenery TCP/UDP/HTTP. Brak odpowiedzi na ping jest normalny. Monitoruj tak, jak korzysta z niego klient: TCP connect na port, HTTP GET na endpoint health z oczekiwanym kodem i czasem odpowiedzi (blackbox exporter http_2xx, syntetyczne testy w chmurze), alarm na kod/latencję, nie na ICMP.'],
  ['Na skoku 5 traceroute widzisz adres 10.255.3.1 (prywatny) w środku drogi do serwera w Internecie. Czy to błąd?', 'Sieci operatorów, MPLS.', 'Nie musi być błędem: operatorzy używają adresów prywatnych (lub CGNAT 100.64/10) na łączach wewnętrznych swojej sieci; router odpowiada Time Exceeded ze swojego wewnętrznego adresu. Odpowiedzi z adresów prywatnych mogą być jednak filtrowane przez inne sieci (bogon filtering), stąd czasem gwiazdki. W sieciach MPLS część skoków bywa ukryta (no-propagate TTL) — traceroute pokazuje mniej skoków niż w rzeczywistości.'],
  ['Zaplanuj monitoring dostępności zewnętrznego API, od którego zależy Twoja aplikacja, z użyciem Prometheus blackbox exporter.', 'Moduły tcp_connect, http_2xx, icmp.', 'Moduły: http_2xx (GET na endpoint health lub lekki endpoint API, oczekiwany status, timeout, TLS — także data ważności certyfikatu), tcp_connect (port 443), pomocniczo icmp. Sondy z miejsc, gdzie działa aplikacja (ten sam klaster/region), plus jedna zewnętrzna. Alarmy: probe_success == 0 przez 2–3 cykle, probe_duration_seconds powyżej progu, probe_ssl_earliest_cert_expiry < 14 dni. Dashboard z latencją i mtr uruchamianym ręcznie przy incydencie.'],
  ['Wyjaśnij, dlaczego RTT widziany na skoku pośrednim w traceroute może być WIĘKSZY niż RTT do celu.', 'Control plane, asymetria.', 'Router przekazuje ruch tranzytowy sprzętowo (szybko), ale odpowiedź ICMP Time Exceeded generuje jego procesor sterujący z niskim priorytetem (opóźnienie, rate limit). Dodatkowo odpowiedź ICMP może wracać inną, dłuższą drogą (asymetria). Cel odpowiada „normalnie” i krótszą drogą. Dlatego liczy się opóźnienie, które utrzymuje się do celu.'],
  ['Zdiagnozuj: ping do serwera w Japonii ma 250 ms i 0% strat, użytkownicy skarżą się na wolną aplikację. Czy to problem sieci?', 'Fizyka, RTT w handshake.', 'Opóźnienie 250 ms wynika z odległości (prędkość światła w światłowodzie ~200 000 km/s, trasy dłuższe niż linia prosta) — to nie awaria. Ale aplikacja cierpi, bo każdy round trip (TCP handshake, TLS handshake, sekwencyjne zapytania API) kosztuje 250 ms. Rozwiązania: serwery/CDN bliżej użytkowników, mniej round tripów (HTTP/2 lub HTTP/3, TLS 1.3, 0-RTT, łączenie zapytań, keep-alive), cache.'],
  ['Jakie komunikaty ICMPv6 są niezbędne do działania sieci IPv6 i dlaczego Router Advertisement należy filtrować na portach użytkowników?', 'ND, RA, PTB, RA Guard.', 'Niezbędne: Neighbor Solicitation/Advertisement (135/136 — odpowiednik ARP), Router Solicitation/Advertisement (133/134 — SLAAC, brama domyślna), Packet Too Big (2 — PMTUD, routery nie fragmentują), Destination Unreachable, Time Exceeded, Parameter Problem, Echo (diagnoza). RA z portów użytkowników należy blokować (RA Guard na przełącznikach), bo fałszywy Router Advertisement pozwala atakującemu stać się bramą domyślną lub ogłosić fałszywy prefiks/DNS dla całego segmentu (MITM).'],
  ['Przechwyć tcpdumpem ruch podczas traceroute (UDP) do dowolnego hosta w Internecie i opisz, jakie pakiety widzisz.', 'tcpdump -ni any "udp portrange 33434-33534 or icmp".', 'Wychodzące: pakiety UDP z rosnącym TTL (1, 1, 1, 2, 2, 2, …) na porty 33434, 33435… (każda sonda inny port, by dopasować odpowiedź). Przychodzące: ICMP Time Exceeded (typ 11 kod 0) od kolejnych routerów, każdy z kopią nagłówka IP i 8 bajtów UDP oryginalnej sondy (stąd traceroute wie, której sondy dotyczy odpowiedź); na końcu ICMP Port Unreachable (typ 3 kod 3) od celu lub brak odpowiedzi, jeśli cel filtruje UDP.'],
  ['Dlaczego komunikat ICMP o błędzie zawiera fragment oryginalnego pakietu i do czego to służy?', 'Dopasowanie błędu do gniazda.', 'ICMP Destination Unreachable/Time Exceeded zawiera nagłówek IP oryginalnego pakietu i co najmniej 8 bajtów danych (czyli porty TCP/UDP). Dzięki temu system nadawcy może dopasować błąd do konkretnego gniazda/połączenia (np. zwrócić aplikacji ECONNREFUSED dla UDP albo zaktualizować PMTU dla połączenia TCP po „fragmentation needed”), a traceroute — do konkretnej sondy. Firewall stanowy używa go, by przepuścić ICMP jako RELATED do istniejącego połączenia.'],
  ['Twoja usługa UDP (np. syslog na 514) nie otrzymuje danych, a klient nie zgłasza błędu. Jak ICMP może pomóc w diagnozie?', 'Port Unreachable, connected UDP socket.', 'Gdy na serwerze nic nie słucha na porcie UDP, system odsyła ICMP Port Unreachable (3/3), chyba że firewall go blokuje. Klient UDP bez connect() zwykle tego nie widzi; z „połączonym” gniazdem UDP (DatagramSocket.connect) kolejna operacja zgłosi PortUnreachableException w Javie. Diagnoza: tcpdump -ni any "udp port 514 or icmp" po stronie serwera i klienta — widać, czy datagramy dochodzą i czy wraca Port Unreachable (usługa nie słucha) albo nic (DROP po drodze).'],
  ['Wyjaśnij różnicę między ICMP 3/13 (administratively prohibited) a brakiem odpowiedzi (DROP) z perspektywy atakującego i obrońcy.', 'REJECT ujawnia filtr.', 'REJECT z 3/13 (lub TCP RST) szybko informuje klienta, że ruch jest zablokowany — dobre dla legalnych użytkowników i diagnozy wewnątrz sieci, ale ujawnia skanerowi obecność filtra i przyspiesza skanowanie. DROP spowalnia skanowanie (timeouty) i nie zdradza polityki, ale utrudnia diagnozę i powoduje długie oczekiwanie klientów. Typowa praktyka: DROP na brzegu od strony Internetu, REJECT wewnątrz sieci firmowej.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
