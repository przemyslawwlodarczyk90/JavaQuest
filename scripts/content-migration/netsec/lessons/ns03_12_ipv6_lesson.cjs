// Generuje lekcje _ns_03_ip_i_routing/12_Ipv6InDepth.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_12_ipv6_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/12_Ipv6InDepth.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const M = require('./ns03_12_ipv6_model.cjs');
const buildQuiz = require('./ns03_12_ipv6_quiz.cjs');
const { full, compress, eui64, slaac, linkLocal, solicitedNode, multicastMac, classify6, net6, sub6, count6 } = M;

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'IPv6: nie „IPv4 z dłuższymi adresami”', ...img('ns03-12-host.svg', 'Wynik ip -6 addr: adres globalny tymczasowy do połączeń wychodzących, adres globalny stabilny z SLAAC, adres ULA fd5a:3c91:7e04:12::20 i adres link-local fe80::. Interfejs należy do grup multicast ff02::1 i solicited-node, brama domyślna fe80::1 z Router Advertisement.'),
    body:
`IPv6 to już nie przyszłość: ponad 40% ruchu do dużych usług (Google, Facebook) idzie po IPv6, operatorzy mobilni często dają klientom **tylko IPv6** (z NAT64 do starego Internetu), a chmury pobierają opłaty za publiczne adresy IPv4. Twoja aplikacja Java prawie na pewno już dziś obsługuje klientów IPv6 — pytanie, czy o tym wiesz.

IPv6 to inny protokół, z innymi przyzwyczajeniami:
- adres ma **128 bitów** i zapisuje się szesnastkowo, z regułami skracania,
- każdy interfejs ma **kilka adresów naraz** (link-local, globalny stabilny, globalny tymczasowy, często ULA),
- **nie ma ARP ani broadcastu** — zamiast nich Neighbor Discovery na ICMPv6 i multicast,
- hosty konfigurują się same (**SLAAC** z Router Advertisement, lekcja 02/10), LAN to zawsze **/64**,
- **nie ma NAT** (zwykle) — każdy host ma adres globalny, a bezpieczeństwo zapewnia firewall,
- nagłówek ma **stałe 40 B**, a opcje idą do **nagłówków rozszerzających**; routery nie fragmentują.

W tej lekcji: notacja i skracanie, typy adresów (GUA, ULA, link-local, multicast, specjalne), nagłówek i extension headers, identyfikator interfejsu (EUI-64, adresy prywatne i tymczasowe), Neighbor Discovery od strony adresów (solicited-node, MAC 33:33), IPv6 w Javie (\`Inet6Address\`, scope id, nawiasy w URL) i w Dockerze, oraz **Guided Lab z planu**: uruchom Spring Boot na IPv6 w Dockerze, połącz się \`curl -6\` i przechwyć ruch.

Cele: Network+ N10-009 (IPv6: typy adresów, SLAAC, dual-stack, tunelowanie), CCNA v2 (adresacja IPv6, typy adresów, EUI-64, konfiguracja i weryfikacja), Security+ (atakowanie i obrona ND/RA).` },

  { type: 'DEFINITION', heading: 'Słownik IPv6', body:
`- **Zapis** — 8 grup po 16 bitów (4 cyfry hex) rozdzielonych dwukropkami; **skracanie**: usuń zera wiodące w grupie, jeden najdłuższy ciąg grup zerowych zastąp \`::\` (RFC 5952: małe litery, \`::\` raz, nie dla jednej grupy).
- **Prefiks /n** — jak CIDR; podsieć LAN zawsze **/64** (64 bity sieci + 64 bity identyfikatora interfejsu).
- **GUA (Global Unicast, 2000::/3)** — adresy globalne, routowane w Internecie (np. 2a00:…, 2001:…).
- **ULA (Unique Local, fc00::/7, w praktyce fd00::/8)** — adresy wewnętrzne, nie routowane w Internecie; z losowym 40-bitowym Global ID (RFC 4193).
- **Link-local (fe80::/10)** — automatyczny adres na każdym interfejsie, ważny tylko na łączu; używany przez ND, RA i jako brama domyślna. Wymaga **identyfikatora strefy** (\`fe80::1%eth0\`).
- **Multicast (ff00::/8)** — \`ff02::1\` wszystkie węzły na łączu, \`ff02::2\` wszystkie routery, \`ff02::1:ffXX:XXXX\` **solicited-node** (zastępuje ARP-broadcast), \`ff02::fb\` mDNS. MAC dla multicastu: **33:33 + ostatnie 32 bity**.
- **Anycast** — ten sam adres na wielu interfejsach; pakiet trafia do najbliższego (DNS root, CDN).
- **Specjalne** — \`::\` nieokreślony, \`::1\` loopback, \`::ffff:0:0/96\` IPv4-mapped, \`64:ff9b::/96\` NAT64, \`2001:db8::/32\` dokumentacyjny.
- **Identyfikator interfejsu (IID)** — ostatnie 64 bity: z **EUI-64** (MAC + ff:fe + odwrócony bit U/L — ujawnia MAC), **stabilny prywatny** (RFC 7217, hash prefiksu i sekretu), **tymczasowy** (RFC 8981, losowy, zmieniany), ręczny lub z DHCPv6.
- **Nagłówek IPv6** — 40 B: Version, Traffic Class, Flow Label, Payload Length, Next Header, Hop Limit, adresy; bez sumy kontrolnej i pól fragmentacji.
- **Extension headers** — Hop-by-Hop (0), Routing (43), Fragment (44), Destination Options (60), AH (51), ESP (50); łańcuch przez **Next Header**.
- **Neighbor Discovery (RFC 4861)** — NS/NA (135/136) rozwiązywanie adresów i DAD, RS/RA (133/134) routery i prefiksy, Redirect (137). Wszystko na ICMPv6.
- **Zakres (scope)** — interface-local, link-local, site/organization, global; w Javie \`Inet6Address.getScopeId()\` / nazwa interfejsu.` },

  { type: 'ANALOGY', heading: 'Nowa numeracja telefonów i osiedlowe tablice ogłoszeń', body:
`- **Adres 128-bitowy** to numer telefonu tak długi, że każdy dom, każdy telefon i każda żarówka może mieć własny numer — bez centralki (NAT) przy wejściu do budynku. Żeby nie dzwonił do Ciebie byle kto, potrzebujesz bramkarza (firewalla), a nie centralki.
- **Skracanie** to mówienie „dwa tysiące jeden, de-be-osiem, dwa razy dwukropek, osiem” zamiast wymieniania wszystkich zer — ale „dwa razy dwukropek” wolno powiedzieć tylko raz, inaczej nie wiadomo, ile zer gdzie brakuje.
- **Link-local** to numer wewnętrzny działający tylko na Twoim piętrze — każdy telefon ma go od razu po podłączeniu, a dozorca (router) też ma taki numer i przez niego się z nim rozmawia.
- **Zamiast krzyczeć na całe piętro (broadcast ARP)** IPv6 wiesza ogłoszenie na **tablicy przy konkretnych drzwiach** (solicited-node multicast) — słyszą je tylko ci, których numer kończy się tak samo, a nie wszyscy.
- **Router Advertisement** to ogłoszenie dozorcy na tablicy: „numer kierunkowy budynku to 2001:db8:abcd:12, a ja jestem bramą” — mieszkańcy sami składają z tego swój pełny numer (SLAAC).
- **Adres tymczasowy** to numer jednorazowy do dzwonienia na zewnątrz, żeby sklepy nie śledziły, że to wciąż ten sam telefon; **stabilny** — numer na wizytówce, pod który można oddzwonić.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Notacja: rozwijanie, skracanie i klasyfikacja', ...img('ns03-12-notation.svg', 'Skracanie adresu IPv6 2001:0db8:0000:0000:0008:0800:200c:417a: usunięcie zer wiodących daje 2001:db8:0:0:8:800:200c:417a, zastąpienie najdłuższego ciągu zer przez :: daje 2001:db8::8:800:200c:417a, rozwinięcie przywraca pełny zapis.'),
    body:
`Przykłady wyliczone modelem lekcji (i zgodne z Pythonem \`ipaddress\`). Zwróć uwagę na przypadki brzegowe: dwa ciągi zer tej samej długości (skracamy pierwszy), pojedyncza grupa zerowa (nie skracamy), adres z końcówką IPv4.`,
    code:
`${['2001:0db8:0000:0000:0008:0800:200c:417a', '2001:0db8:0000:0001:0000:0000:0000:0001', '2001:0db8:0000:0000:0001:0000:0000:0001', '2001:0db8:0001:0000:0001:0001:0001:0001', 'fe80:0000:0000:0000:0a00:27ff:fe4e:66a1', '0000:0000:0000:0000:0000:0000:0000:0001', 'ff02:0000:0000:0000:0000:0001:ff4e:66a1']
    .map((a) => `${a}  →  ${compress(a).padEnd(26)} ${classify6(compress(a)).name}`).join('\n')}

Rozwijanie:  ${['2001:db8::1', 'fe80::1', '::', 'fd5a:3c91:7e04:12::20'].map((a) => `${a} = ${full(a)}`).join('\n             ')}

Sieć /64 adresu:  2001:db8:abcd:12:9c4e:1f2a:77b0:3d11  →  ${net6('2001:db8:abcd:12:9c4e:1f2a:77b0:3d11', 64)}
Podsieć /64 nr 18 w 2001:db8:abcd::/48  →  ${sub6('2001:db8:abcd::', 48, 64, 18)}  (z ${count6(48, 64).toLocaleString('pl-PL')} możliwych)` },

  { type: 'CODE_BASIC', heading: 'IPv6 w Linuksie: adresy, trasy, sąsiedzi', body:
`Polecenia z rodziny \`ip\` działają tak samo dla IPv6 z przełącznikiem \`-6\`. Odpowiednikiem tablicy ARP jest **tablica sąsiadów** (\`ip -6 neigh\`), a bramę domyślną zwykle dostajesz z Router Advertisement jako adres link-local.`,
    code:
`ip -6 addr show dev wlan0
#  inet6 2001:db8:abcd:12:9c4e:1f2a:77b0:3d11/64 scope global temporary dynamic valid_lft 86321sec preferred_lft 14321sec
#  inet6 2001:db8:abcd:12:5f1:2a7c:e3d9:8b44/64 scope global dynamic mngtmpaddr
#  inet6 fe80::5f1:2a7c:e3d9:8b44/64 scope link
ip -6 route
#  2001:db8:abcd:12::/64 dev wlan0 proto ra metric 600
#  default via fe80::1 dev wlan0 proto ra metric 600 pref medium
ip -6 neigh                        # fe80::1 dev wlan0 lladdr 00:11:22:33:44:55 router REACHABLE
ip -6 maddr show dev wlan0         # grupy multicast: ff02::1, ff02::1:ffb0:3d11, ff02::1:ffd9:8b44 …
ping -6 -c3 fe80::1%wlan0          # link-local wymaga strefy (%interfejs)
ping -6 ff02::1%wlan0              # „ping wszystkich” na łączu — odpowiadają wszystkie węzły IPv6
sysctl net.ipv6.conf.wlan0.use_tempaddr      # 2 = preferuj adresy tymczasowe dla połączeń wychodzących
sysctl net.ipv6.conf.all.disable_ipv6        # 0 = IPv6 włączone

# ręczna konfiguracja (serwer) — adres i brama
ip -6 addr add 2001:db8:abcd:12::10/64 dev eth0
ip -6 route add default via 2001:db8:abcd:12::1 dev eth0
# Windows: ipconfig /all, netsh interface ipv6 show neighbors, Get-NetIPAddress -AddressFamily IPv6` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab z planu: Spring Boot na IPv6 w Dockerze, curl -6 i capture', body:
`Cel: zobaczyć, że aplikacja Spring Boot obsługuje IPv6 bez zmian w kodzie, i umieć to sprawdzić. Wymaga Dockera na Linuksie/WSL2 (Docker Desktop: IPv6 włącza się w ustawieniach silnika).
1. **Sieć IPv6 dla kontenerów**: \`docker network create --ipv6 --subnet fd5a:3c91:7e04:20::/64 --subnet 172.30.0.0/24 v6lab\` (sieć dual-stack z prefiksem ULA; Docker ≥ 27 domyślnie robi NAT66 dla takich sieci przez ip6tables).
2. **Aplikacja**: dowolna aplikacja Spring Boot z Actuatorem (\`spring-boot-starter-actuator\`) zbudowana do obrazu (\`./mvnw spring-boot:build-image\` lub Dockerfile z JRE 21). Uruchom: \`docker run -d --name app --network v6lab -p 8080:8080 app:latest\`. Tomcat domyślnie słucha na \`[::]:8080\` — gniazdo dual-stack przyjmuje IPv4 i IPv6.
3. **Sprawdź adresy**: \`docker inspect app -f '{{range .NetworkSettings.Networks}}{{.GlobalIPv6Address}} {{.IPAddress}}{{end}}'\` → np. \`fd5a:3c91:7e04:20::2 172.30.0.2\`; w kontenerze \`docker exec app cat /proc/net/if_inet6\` lub \`ss -ltn\` (jeśli obraz ma narzędzia): \`LISTEN *:8080\` / \`[::]:8080\`.
4. **Połączenia**: z hosta \`curl -6 -s http://[fd5a:3c91:7e04:20::2]:8080/actuator/health\` (bezpośrednio do kontenera), \`curl -6 -s http://[::1]:8080/actuator/health\` (przez opublikowany port — Docker publikuje też na \`[::]\`), \`curl -4 -s http://127.0.0.1:8080/actuator/health\` (IPv4). Zwróć uwagę na **nawiasy kwadratowe** w URL.
5. **Capture**: \`sudo tcpdump -ni any 'ip6 and tcp port 8080' -c 10\` podczas curl -6 — widać handshake TCP po IPv6 (40-bajtowy nagłówek, adresy fd5a:…). W Wiresharku filtr \`ipv6 && tcp.port == 8080\`, rozwiń nagłówek IPv6: Next Header 6, Hop Limit 64, Flow Label.
6. **Co widzi aplikacja**: dodaj endpoint zwracający \`request.getRemoteAddr()\` — dla curl -6 zobaczysz adres IPv6 (bramy sieci Dockera lub \`::1\`, zależnie od ścieżki), a dla curl -4 przez gniazdo dual-stack często **\`::ffff:172.30.0.1\`** lub zwykły IPv4 — pamiętaj o tym w logach i allow-listach (lekcja 06).
7. **Sprzątanie**: \`docker rm -f app && docker network rm v6lab\`. **Notatka**: adresy kontenera, wynik ss/tcpdump, różnice w remoteAddr dla -4 i -6.` },

  { type: 'NOTE', heading: 'Identyfikator interfejsu, EUI-64 i prywatność', ...img('ns03-12-eui64.svg', 'Konwersja MAC 00:1a:2b:3c:4d:5e na identyfikator EUI-64: wstawienie ff:fe w środek i odwrócenie bitu U/L daje 21a:2bff:fe3c:4d5e; adres link-local fe80::21a:2bff:fe3c:4d5e i adres SLAAC; solicited-node multicast ff02::1:ff3c:4d5e i MAC 33:33:ff:3c:4d:5e.'),
    body:
`**EUI-64** (pierwotny sposób SLAAC): MAC 48-bitowy dzielimy na połowy, wstawiamy \`ff:fe\`, odwracamy bit U/L (7. bit pierwszego bajtu). Dla MAC 00:1a:2b:3c:4d:5e → IID **${eui64('00:1a:2b:3c:4d:5e')}**, link-local **${linkLocal('00:1a:2b:3c:4d:5e')}**, adres w prefiksie 2001:db8:abcd:12::/64 → **${slaac('2001:db8:abcd:12::', '00:1a:2b:3c:4d:5e')}**. Problem: MAC jest widoczny w adresie w każdej sieci → śledzenie urządzenia. Dlatego współczesne systemy używają:
- **stabilnych adresów prywatnych (RFC 7217)** — IID = hash(prefiks, interfejs, sekret), stały w danej sieci, inny w każdej sieci;
- **adresów tymczasowych (RFC 8981)** — losowe, odnawiane (np. co dobę), preferowane jako źródło połączeń wychodzących.
Routery i serwery mają zwykle **adresy statyczne** (np. \`::1\` bramy, \`::10\` serwera) lub z DHCPv6.

**Neighbor Discovery od strony adresów**: zamiast ARP-broadcastu host wysyła Neighbor Solicitation na **solicited-node multicast** celu: \`ff02::1:ff\` + ostatnie 24 bity adresu (dla ${linkLocal('00:1a:2b:3c:4d:5e')} → **${solicitedNode(linkLocal('00:1a:2b:3c:4d:5e'))}**), a na poziomie Ethernetu ramka idzie na MAC **${multicastMac(solicitedNode(linkLocal('00:1a:2b:3c:4d:5e')))}** (33:33 + ostatnie 32 bity). Karty sieciowe odbierają tylko grupy, do których należą — reszta hostów nie jest „budzona” jak przy broadcastcie. Tym samym mechanizmem działa **DAD** (sprawdzenie, czy adres jest wolny — lekcja 02/13).

**Dla blue teamu**: adresy tymczasowe utrudniają korelację logów (ten sam laptop — różne adresy co dobę); trzeba logować mapowanie IPv6 ↔ MAC ↔ użytkownik (logi ND z przełączników/routerów, NDP snooping, logi DHCPv6, agent EDR).` },

  { type: 'CODE_PRACTICAL', heading: 'IPv6 w Javie: Inet6Address, strefa, URL i porównywanie', body:
`Java obsługuje IPv6 od dawna, ale kilka szczegółów regularnie psuje aplikacje: nawiasy w URL, identyfikator strefy przy link-local, porównywanie tekstu zamiast adresów, IPv4-mapped w gniazdach dual-stack. Program poniżej pokazuje każdy z nich.`,
    code:
`import java.net.*;
import java.util.Collections;

public class Ipv6Demo {
    public static void main(String[] args) throws Exception {
        // 1) parsowanie i normalizacja — porównuj obiekty, nie tekst
        InetAddress a = InetAddress.getByName("2001:0DB8:0000:0000:0008:0800:200C:417A");
        InetAddress b = InetAddress.getByName("2001:db8::8:800:200c:417a");
        System.out.println(a.equals(b) + " " + a.getHostAddress());   // true 2001:db8:0:0:8:800:200c:417a (Java nie skraca ::)

        // 2) typy adresów
        Inet6Address ll = (Inet6Address) InetAddress.getByName("fe80::1%1");
        System.out.println(ll.isLinkLocalAddress() + " scope=" + ll.getScopeId());   // true scope=1

        // 3) URL z IPv6 — nawiasy kwadratowe obowiązkowe
        URI uri = URI.create("http://[2001:db8::10]:8080/actuator/health");
        System.out.println(uri.getHost() + " port " + uri.getPort());   // [2001:db8::10] port 8080

        // 4) IPv4-mapped: gniazdo dual-stack może raportować klienta IPv4 tak
        InetAddress mapped = InetAddress.getByName("::ffff:192.0.2.7");
        System.out.println(mapped.getClass().getSimpleName() + " " + mapped.getHostAddress());   // Inet4Address 192.0.2.7

        // 5) adresy IPv6 lokalnych interfejsów (z identyfikatorem strefy)
        for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces()))
            for (InetAddress ia : Collections.list(ni.getInetAddresses()))
                if (ia instanceof Inet6Address v6)
                    System.out.printf("%-10s %s %s%n", ni.getName(), v6.getHostAddress(),
                            v6.isLinkLocalAddress() ? "(link-local)" : v6.isSiteLocalAddress() ? "(site-local, przestarzały)" : "");
    }
}
// Spring Boot: server.address=:: (domyślnie wszystkie interfejsy v4+v6), JDBC: jdbc:postgresql://[2001:db8::20]:5432/app
// Preferencja v4/v6 przy rozwiązywaniu nazw: -Djava.net.preferIPv6Addresses=true|false|system — lekcja 13.` },

  { type: 'USAGE', heading: 'Nagłówek, extension headers i IPv6 w praktyce', ...img('ns03-12-header.svg', 'Nagłówek IPv6 o stałej długości 40 bajtów: Version, Traffic Class, Flow Label, Payload Length, Next Header, Hop Limit, 128-bitowe adresy. Brak pól fragmentacji i sumy kontrolnej. Łańcuch nagłówków rozszerzających połączonych polem Next Header.'),
    body:
`**Nagłówek**: stałe 40 B upraszczają sprzętowe przekazywanie; **Hop Limit** = TTL; **Flow Label** pozwala routerom ECMP trzymać przepływ na jednej ścieżce bez zaglądania w porty; **brak sumy kontrolnej** (chronią L2 i L4 — w IPv6 suma UDP jest obowiązkowa); **brak fragmentacji w routerach** (lekcja 11). Opcje trafiają do **nagłówków rozszerzających** — elastyczne, ale problematyczne: firewall musi przejść cały łańcuch, by dotrzeć do TCP/UDP, a nietypowe łańcuchy są często odrzucane po drodze (RFC 7872). Routing Header typu 0 został wycofany (RFC 5095) z powodu ataków wzmacniających.

**Developer / DevOps:**
- **Chmura**: AWS — VPC z blokiem /56 od Amazonu, podsieci /64, instancje z GUA (bez NAT — egress-only Internet Gateway dla ruchu tylko wychodzącego); opłaty za publiczne IPv4 skłaniają do IPv6/dual-stack. Kubernetes: dual-stack od 1.23 (\`ipFamilies\`, \`ipFamilyPolicy\`).
- **Docker**: IPv6 trzeba włączyć dla sieci (\`--ipv6\`, \`daemon.json\`), inaczej kontener nie ma GUA/ULA.
- **Aplikacja**: logi i bazy z polami adresów muszą mieścić 45 znaków (np. \`VARCHAR(45)\`) lub typ \`inet\` w PostgreSQL; walidacja adresów biblioteką; rate limiting per **/64**, nie per adres (klient ma miliardy adresów w swojej /64).

**Blue team:**
- **Fałszywe RA** (rogue RA) — atakujący ogłasza się bramą lub podaje fałszywy prefiks/DNS → MITM; obrona: **RA Guard**, ochrona ND na przełącznikach, \`accept_ra=0\` na serwerach ze statyczną konfiguracją.
- **Wyczerpanie tablicy sąsiadów** (ND cache exhaustion) — skanowanie /64 zmusza router do NS dla nieistniejących adresów; obrona: limity, odpowiednio mniejsze podsieci dla routerów punkt-punkt (/127, RFC 6164).
- **Niewidoczny IPv6** — „nie mamy IPv6”, a hosty mają link-local i SLAAC z rogue RA lub tunele (Teredo, 6to4) omijające firewall IPv4. Monitoruj i filtruj IPv6 tak samo starannie jak IPv4.
- **Skanowanie** — /64 jest zbyt duże do skanowania „po kolei”; atakujący zgadują adresy (::1, ::10, EUI-64 z OUI producenta, adresy z DNS i logów certyfikatów) — nie polegaj na „ukryciu w przestrzeni adresów”.` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Dwa razy \`::\`** w adresie — zapis niejednoznaczny i niepoprawny.
- **URL bez nawiasów** (\`http://2001:db8::1:8080\`) — parser nie odróżni portu od grupy adresu.
- **Link-local bez strefy** — \`ping fe80::1\` nie wie, przez który interfejs (błąd lub zły interfejs); w Javie potrzebny scope id.
- **Porównywanie tekstu** (\`2001:DB8::1\` vs \`2001:db8:0:0::1\`) — używaj \`InetAddress.equals\`/biblioteki.
- **Pola w bazie na 15 znaków** (dla IPv4) — adres IPv6 ma do 39 znaków (45 z IPv4-mapped).
- **Rate limiting/blokady per pojedynczy adres IPv6** — klient zmienia adres w obrębie /64 dowolnie; limituj per /64 (lub /56/48 dla dostawców).
- **Podsieci mniejsze niż /64 dla LAN** — SLAAC i wiele mechanizmów przestaje działać.
- **Wyłączanie IPv6 „dla bezpieczeństwa”** w systemach — łamie oprogramowanie (localhost ::1, usługi Windows), a i tak nie chroni przed tunelami; lepiej kontrolować i monitorować.
- **Blokada ICMPv6** — IPv6 przestaje działać (ND, RA, PTB).
- **Zakładanie, że brak NAT = brak firewalla** — host z GUA bez firewalla jest osiągalny z Internetu; włącz stanowy firewall z domyślnym DROP dla ruchu przychodzącego.` },

  { type: 'CODE_WRONG', heading: 'Źle: aplikacja „gotowa na IPv4”', body:
`Kod i konfiguracja, które działają w środowisku testowym tylko z IPv4, a psują się, gdy klienci, kontenery lub bazy dostają adresy IPv6.`,
    code:
`String hostPort = host + ":" + port;                       // "2001:db8::10:5432" — nieczytelne dla parsera
URI uri = URI.create("http://" + host + ":8080/api");      // brak nawiasów → IllegalArgumentException / zły host
CREATE TABLE audit_log (client_ip VARCHAR(15));             // ucina adresy IPv6
if (clientIp.startsWith("10.") || clientIp.equals("127.0.0.1")) trustInternal();   // ::1, fd00::/8, ::ffff:10.x pominięte
rateLimiter.limit(clientIp);                                 // klient IPv6 omija limit, zmieniając adres w /64
server.address=0.0.0.0                                       // tylko IPv4 — klienci IPv6-only nie połączą się` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: kod i konfiguracja niezależne od wersji IP', body:
`Adresy jako obiekty, nawiasy i porty składane przez API, pola bazy o odpowiedniej długości lub typ \`inet\`, limity per prefiks, nasłuch na wszystkich rodzinach adresów.`,
    code:
`InetSocketAddress sa = new InetSocketAddress(host, port);                    // obsługuje oba formaty
URI uri = new URI("http", null, host, 8080, "/api", null, null);           // dodaje [ ] dla IPv6 automatycznie
CREATE TABLE audit_log (client_ip INET);                                     // PostgreSQL: typ inet (lub VARCHAR(45))
InetAddress ip = InetAddress.getByName(clientIp);                            // normalizacja (::ffff:a.b.c.d → Inet4Address)
boolean internal = INTERNAL_PREFIXES.stream().anyMatch(p -> p.contains(ip)); // biblioteka prefiksów: 10/8, fd00::/8, ::1…
String limiterKey = ip instanceof Inet6Address v6 ? prefix64(v6) : ip.getHostAddress();   // limit per /64
server.address=::                                                            // Tomcat: dual-stack (IPv4 i IPv6)` },

  { type: 'WHEN_TO_USE', heading: 'Który typ adresu IPv6 do czego', body:
`Ściąga projektowa:`,
    code:
`ZASTOSOWANIE                                     TYP / PRZYKŁAD
publiczne usługi, serwery w Internecie            GUA ze stałym IID (2001:db8:abcd:20::10) + firewall
LAN użytkowników                                  /64 z SLAAC (RFC 7217 + adresy tymczasowe) lub DHCPv6
sieć wewnętrzna bez dostępu do Internetu          ULA fd<losowe 40 bitów>::/48 (RFC 4193)
łącza router–router                               /127 (RFC 6164) lub tylko link-local
brama domyślna hostów                             link-local routera (fe80::1) z RA
lokalny test na jednym hoście                     ::1 (loopback)
dokumentacja i testy                              2001:db8::/32
komunikacja IPv6-only z IPv4                      64:ff9b::/96 (NAT64, lekcja 13)
przydział dla lokalizacji / klienta                /48 dla firmy/lokalizacji, /56 dla klienta domowego
chmura                                            AWS: VPC /56, podsieci /64; egress-only IGW zamiast NAT` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Adres 128-bitowy w hex; skracanie: zera wiodące precz, jeden najdłuższy ciąg zer → \`::\` (RFC 5952); w URL — \`[adres]:port\`.
- Typy: GUA 2000::/3, ULA fc00::/7 (fd00::/8), link-local fe80::/10 (zawsze, ze strefą), multicast ff00::/8 (ff02::1, ff02::2, solicited-node), specjalne ::, ::1, ::ffff:0:0/96, 64:ff9b::/96, 2001:db8::/32.
- LAN = /64; IID z EUI-64 (ujawnia MAC), RFC 7217 (stabilny prywatny) lub RFC 8981 (tymczasowy).
- Nagłówek 40 B: Hop Limit, Flow Label, Next Header; extension headers; brak sumy kontrolnej i fragmentacji w routerach.
- ND zamiast ARP: NS/NA na solicited-node multicast (MAC 33:33…), RS/RA, DAD; wszystko na ICMPv6.
- Java: \`Inet6Address\`, scope id, \`InetSocketAddress\`/\`URI\` z nawiasami, porównywanie obiektów, \`server.address=::\`; bazy: \`inet\`/VARCHAR(45); limity per /64.
- Bezpieczeństwo: RA Guard, firewall zamiast „NAT-u”, monitorowanie IPv6 i tuneli, logowanie IPv6↔MAC.
- Następna lekcja: **dual-stack, Happy Eyeballs i NAT64** — co się dzieje, gdy rekord AAAA istnieje, a IPv6 nie działa.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const a of ['2001:0db8:0000:0000:0008:0800:200c:417a', '2001:0db8:0000:0001:0000:0000:0000:0001', 'fe80:0000:0000:0000:0a00:27ff:fe4e:66a1', '2001:0db8:00a0:0000:0000:0000:0000:0000']) {
  ex(`Skróć zgodnie z RFC 5952: ${a}. Sklasyfikuj adres.`, 'Zera wiodące, najdłuższy ciąg zer → ::, małe litery.', `${compress(a)} — ${classify6(compress(a)).name} (${classify6(compress(a)).range}).`);
}
for (const a of ['2001:db8::1', 'fe80::1:2', 'fd5a:3c91:7e04:12::20', '::ffff:c000:207']) {
  ex(`Rozwiń do pełnej postaci (8 grup po 4 cyfry): ${a}.`, 'Policz brakujące grupy w miejscu ::.', `${full(a)}.`);
}
for (const mac of ['00:1a:2b:3c:4d:5e', '02:42:ac:11:00:02', '08:00:27:4e:66:a1', 'b8:27:eb:12:34:56']) {
  ex(`Wyznacz identyfikator EUI-64, adres link-local i adres SLAAC w prefiksie 2001:db8:abcd:12::/64 dla MAC ${mac}. Podaj też solicited-node multicast adresu link-local.`, 'ff:fe w środek, odwróć bit 0x02 pierwszego bajtu; solicited-node = ff02::1:ff + 24 bity.',
    `IID ${eui64(mac)}; link-local ${linkLocal(mac)}; SLAAC ${slaac('2001:db8:abcd:12::', mac)}; solicited-node ${solicitedNode(linkLocal(mac))} (MAC ${multicastMac(solicitedNode(linkLocal(mac)))}).`);
}
for (const [base, p, np, n] of [['2001:db8:abcd::', 48, 64, 18], ['2001:db8:abcd::', 48, 56, 5], ['2001:db8:abcd:1200::', 56, 64, 255]]) {
  ex(`Ile podsieci /${np} mieści ${base}/${p}? Podaj podsieć numer ${n}.`, 'Liczysz w cyfrach hex.', `${count6(p, np).toLocaleString('pl-PL')} podsieci; nr ${n} (0x${n.toString(16)}): ${sub6(base, p, np, n)}.`);
}
const more = [
  ['Wykonaj Guided Lab z planu: uruchom Spring Boot w sieci Dockera z IPv6, połącz się curl -6 (bezpośrednio do kontenera i przez opublikowany port) i przechwyć ruch. Opisz, co widzisz w nagłówku IPv6.', 'docker network create --ipv6, nawiasy w URL, tcpdump ip6.', 'Sieć: docker network create --ipv6 --subnet fd5a:3c91:7e04:20::/64 --subnet 172.30.0.0/24 v6lab; kontener dostaje np. fd5a:3c91:7e04:20::2. Tomcat słucha na [::]:8080 (dual-stack). curl -6 http://[fd5a:3c91:7e04:20::2]:8080/actuator/health → {"status":"UP"}; curl -6 http://[::1]:8080/... przez port opublikowany. tcpdump -ni any "ip6 and tcp port 8080": handshake TCP po IPv6; w Wiresharku nagłówek 40 B: Version 6, Traffic Class 0, Flow Label (losowy lub 0), Payload Length, Next Header 6 (TCP), Hop Limit 64, adresy fd5a:…. Aplikacja widzi remoteAddr IPv6, a dla curl -4 często ::ffff:… lub IPv4.'],
  ['Dlaczego LAN w IPv6 to zawsze /64? Co się stanie, gdy dasz użytkownikom /80?', 'SLAAC, IID 64 bity.', 'SLAAC i większość mechanizmów IID (EUI-64, RFC 7217, tymczasowe) zakłada 64-bitowy identyfikator interfejsu; Router Advertisement z prefiksem innym niż /64 nie pozwoli hostom skonfigurować adresów automatycznie (flaga A ignorowana). /80 wymusiłoby DHCPv6 lub ręczną konfigurację, łamie część implementacji (Android nie obsługuje DHCPv6 dla adresów). Przestrzeni nie brakuje — /48 daje 65 536 sieci /64.'],
  ['Opisz atak rogue Router Advertisement i trzy środki obrony.', 'Fałszywa brama, RA Guard.', 'Atakujący w segmencie wysyła RA, ogłaszając się routerem (często z wyższym priorytetem) i/lub podając własny prefiks i serwer DNS (RDNSS). Hosty ustawiają go jako bramę domyślną i/lub konfigurują adresy w jego prefiksie — ruch przechodzi przez atakującego (MITM) lub jest zakłócany (DoS). Obrona: RA Guard na portach dostępowych przełączników (RA tylko z portów routerów), DHCPv6 Guard i ND inspection (SAVI), na serwerach ze statyczną konfiguracją accept_ra=0, monitoring RA (ndpmon, Zeek) i alarm na nowy router.'],
  ['Dlaczego rate limiting per pojedynczy adres IPv6 jest nieskuteczny i jak go poprawić w aplikacji Java?', 'Klient ma całą /64.', 'Klient (lub atakujący) z przydzieloną /64 może używać 2^64 różnych adresów źródłowych (adresy tymczasowe, losowe IID), więc limit per adres jest trywialnie omijany. Klucz limitu dla IPv6: prefiks /64 (dla dostawców hostingowych często /56 lub /48), dla IPv4 — adres. W Javie: byte[] b = v6.getAddress(); Arrays.fill(b, 8, 16, (byte) 0); key = InetAddress.getByAddress(b).getHostAddress() + "/64".'],
  ['Wyjaśnij, czym jest identyfikator strefy (zone/scope id) i dlaczego jest potrzebny dla adresów link-local.', 'fe80:: na każdym interfejsie.', 'Adresy link-local fe80::/10 istnieją na każdym interfejsie i mogą się powtarzać między łączami (np. brama fe80::1 na eth0 i na wlan0). Sam adres nie mówi, przez który interfejs wysłać pakiet, więc dopisuje się strefę: fe80::1%eth0 (Linux, nazwa) lub fe80::1%12 (Windows, indeks). W Javie Inet6Address.getByAddress(host, bytes, NetworkInterface) lub zapis z %; w URL: http://[fe80::1%25eth0]/ (procent kodowany jako %25).'],
  ['Porównaj DHCPv6 i SLAAC (z RA flagami M i O) i wskaż, który daje lepszą kontrolę w sieci firmowej.', 'Flagi Managed i Other.', 'SLAAC: host buduje adres z prefiksu z RA (flaga A) — bez serwera, brak centralnej ewidencji adresów; DNS przez RDNSS w RA lub DHCPv6 stateless (flaga O). DHCPv6 stateful (flaga M): serwer przydziela adresy i rejestruje je (ewidencja, logi). W firmie DHCPv6 daje lepszą kontrolę i audyt, ale Android nie obsługuje DHCPv6 dla adresów — dlatego często łączy się SLAAC z logowaniem ND (mapowanie IPv6↔MAC) i 802.1X. Brama domyślna zawsze z RA (DHCPv6 jej nie podaje).'],
  ['Twoja aplikacja zapisuje adres klienta do kolumny VARCHAR(15). Jakie problemy wystąpią z IPv6 i jak zmigrować dane?', 'Długość adresu, typ inet.', 'Adresy IPv6 mają do 39 znaków (45 z IPv4-mapped), więc zostaną ucięte lub wstawienie się nie powiedzie (błąd długości) — logi audytowe stracą wartość dowodową. Migracja: ALTER TABLE … ALTER COLUMN client_ip TYPE inet USING client_ip::inet (PostgreSQL) lub VARCHAR(45); w aplikacji zapis znormalizowanego adresu (InetAddress.getHostAddress lub format RFC 5952), indeksy po typie inet umożliwiają zapytania po prefiksie (client_ip << \'2001:db8::/32\').'],
  ['Dlaczego wyłączenie IPv6 w systemie „dla bezpieczeństwa” bywa złym pomysłem i co zrobić zamiast tego?', 'Zależności, tunele, kontrola.', 'Wyłączenie łamie oprogramowanie oczekujące ::1 (np. usługi Windows, niektóre bazy, Kubernetes dual-stack), nie chroni przed tunelami IPv6-w-IPv4 (Teredo, 6to4) i odcina przyszłą migrację. Zamiast tego: włączony IPv6 z kontrolą — firewall z domyślnym DROP dla ruchu przychodzącego (IPv4 i IPv6 równoważnie), RA Guard, wyłączenie nieużywanych tuneli (Teredo), monitoring ruchu IPv6 w SIEM, inwentaryzacja adresów.'],
  ['W logach firewalla widzisz ruch przychodzący do hosta 2001:db8:abcd:12:21a:2bff:fe3c:4d5e. Co możesz powiedzieć o urządzeniu z samego adresu?', 'EUI-64, OUI.', 'Fragment ff:fe w środku IID wskazuje EUI-64 — można odtworzyć MAC: odwrócić bit U/L w pierwszym bajcie (02 → 00) i usunąć ff:fe → 00:1a:2b:3c:4d:5e; pierwsze 3 bajty (OUI 00:1a:2b) identyfikują producenta karty. Taki adres jest stały we wszystkich sieciach (śledzenie) i przewidywalny dla atakujących. To raczej urządzenie wbudowane/serwer/starszy system — współczesne systemy klienckie używają adresów RFC 7217/8981.'],
  ['Zaprojektuj adresację IPv6 dla firmy z przydziałem 2001:db8:abcd::/48 i trzema lokalizacjami, po 10 VLAN-ów każda.', 'Hierarchia w cyfrach hex.', 'Podział czwartej grupy: pierwsza cyfra hex = lokalizacja (0 centrala, 1 oddział A, 2 oddział B — po /52 = 4096 sieci /64 na lokalizację), kolejne cyfry = VLAN. Np. centrala VLAN 10: 2001:db8:abcd:0010::/64, oddział A VLAN 20: 2001:db8:abcd:1020::/64. Łącza punkt-punkt: osobny blok, np. 2001:db8:abcd:ff00::/56 z /127 per łącze; loopbacki routerów /128 z 2001:db8:abcd:ffff::/64. Granice na cyfrach hex ułatwiają agregację (jedna trasa /52 na lokalizację) i odczyt.'],
  ['Wyjaśnij, czym jest Flow Label w nagłówku IPv6 i do czego wykorzystują go routery oraz load balancery.', '20 bitów, ECMP.', 'Flow Label (20 bitów) to etykieta ustawiana przez nadawcę (zwykle pseudolosowa per przepływ, RFC 6437), stała dla pakietów jednego połączenia. Routery i load balancery mogą używać trójki (źródło, cel, Flow Label) do haszowania w ECMP/LAG bez zaglądania w nagłówki warstwy 4 (które mogą być za łańcuchem extension headers lub zaszyfrowane), co utrzymuje przepływ na jednej ścieżce. Nie jest gwarancją QoS ani nie jest chroniony — nie używaj go do bezpieczeństwa.'],
  [`Wyznacz adres solicited-node multicast i odpowiadający mu MAC dla adresów: 2001:db8::1:2:3abc:def0 oraz fd5a:3c91:7e04:12::20.`, 'ff02::1:ff + ostatnie 24 bity; MAC 33:33 + ostatnie 32 bity grupy.', `${solicitedNode('2001:db8::1:2:3abc:def0')} (MAC ${multicastMac(solicitedNode('2001:db8::1:2:3abc:def0'))}); ${solicitedNode('fd5a:3c91:7e04:12::20')} (MAC ${multicastMac(solicitedNode('fd5a:3c91:7e04:12::20'))}).`],
  [`Jaki adres MAC ma ramka Ethernet niosąca pakiet do grupy ff02::1, a jaki do ff02::2? Dlaczego nie jest to ff:ff:ff:ff:ff:ff?`, 'Mapowanie multicast IPv6 → MAC.', `ff02::1 → ${multicastMac('ff02::1')}, ff02::2 → ${multicastMac('ff02::2')}. IPv6 nie ma broadcastu — używa multicastu, mapowanego na MAC 33:33 + ostatnie 32 bity grupy; karty sieciowe filtrują ramki multicast sprzętowo i budzą system tylko dla grup, do których host należy.`],
  ['Dlaczego na łączach router–router stosuje się /127 (RFC 6164), a nie /64?', 'Ping-pong, ND exhaustion.', 'Na łączu punkt-punkt z /64 ruch do nieistniejących adresów z tej /64 może krążyć między routerami (ping-pong) lub zmuszać je do Neighbor Solicitation dla miliardów adresów (wyczerpanie tablicy sąsiadów — DoS). /127 daje tylko dwa adresy, oba używane przez routery, co eliminuje te problemy. Alternatywa: łącza tylko z adresami link-local (routing po fe80::).'],
  ['Na serwerze Linux ze statycznym adresem IPv6 nagle zmieniła się brama domyślna na nieznany adres fe80::. Co mogło się stać i jak zapobiec?', 'accept_ra, rogue RA.', 'Serwer domyślnie akceptuje Router Advertisement (accept_ra=1), więc nowe RA (rogue RA od atakującego, źle skonfigurowany router, kontener/VM z radvd) dodało trasę domyślną przez nowy router (ip -6 route: default via fe80::… proto ra). Zapobieganie: net.ipv6.conf.<if>.accept_ra=0 na serwerach z konfiguracją statyczną, RA Guard na przełączniku, monitoring zmian tras i RA (Zeek, ndpmon), alarm w SIEM.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
