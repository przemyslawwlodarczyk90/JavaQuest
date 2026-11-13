// Generuje lekcje _ns_02_ethernet_lan/03_ArpAndNeighborDiscovery.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_03_arp_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/03_ArpAndNeighborDiscovery.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// --- IPv6: rozwiniecie adresu, solicited-node i MAC multicast ---
function expand6(a) {
  const [h, t] = a.split('::');
  const hp = h ? h.split(':') : [], tp = t !== undefined && t ? t.split(':') : [];
  const fill = a.includes('::') ? Array(8 - hp.length - tp.length).fill('0') : [];
  return [...hp, ...fill, ...tp].map((x) => x.padStart(4, '0'));
}
function solicited(a) {
  const g = expand6(a);
  const last24 = g[6].slice(2) + g[7];               // ostatnie 24 bity
  return `ff02::1:ff${last24.slice(0, 2)}:${last24.slice(2)}`;
}
function mcastMac(m) {
  const g = expand6(m);
  const last32 = g[6] + g[7];
  return '33:33:' + last32.match(/../g).join(':');
}
const SAMPLES = ['2001:db8::1', 'fe80::a8bb:ccff:fe00:2', '2001:db8:10::4a:bc12', 'fe80::1', '2001:db8:abcd:12::1f00:77', 'fd00:1::dead:beef'];

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Most między IP a MAC', ...img('ns02-03-arp-exchange.svg', 'Diagram sekwencji: laptop 192.168.1.20 wysyła rozgłoszeniowe zapytanie ARP o adres 192.168.1.1; inne hosty je ignorują; brama odpowiada unicastem z adresem 00:1a:2b:3c:4d:5e; laptop i brama zapisują wpisy w cache, a dopiero potem laptop wysyła ramkę z pakietem IP na MAC bramy. Uwaga u dołu: ARP nie ma uwierzytelnienia, stąd ARP spoofing i obrona DAI.'),
    body:
`Aplikacja zna adres IP celu. Karta sieciowa potrafi wysłać ramkę tylko na **adres MAC**. Między jednym a drugim stoi mały, niepozorny protokół: **ARP** (Address Resolution Protocol) w IPv4 i **Neighbor Discovery** (ND, część ICMPv6) w IPv6.

Zanim Twój Spring Boot wyśle pierwszy bajt do bazy w tej samej podsieci albo do bramy domyślnej, jądro systemu musi zapytać: *„kto ma adres 10.0.3.1? podaj swój MAC”*. Odpowiedź trafia do **cache sąsiadów** i jest używana przez kolejne minuty.

To dlaczego ta lekcja jest tak ważna?
- **Diagnoza**: objawy „ping działa raz na jakiś czas”, „po wymianie serwera połączenia wiszą przez minutę”, „po przełączeniu VIP część klientów nie widzi usługi” to zwykle ARP/ND.
- **Wysoka dostępność**: VRRP, keepalived i wirtualne IP przenoszone między węzłami działają dzięki **gratuitous ARP**.
- **Bezpieczeństwo**: ARP nie ma żadnego uwierzytelnienia. Każdy w segmencie może ogłosić „to ja jestem bramą” — to **ARP spoofing**, klasyczna droga do ataku man-in-the-middle w LAN. W IPv6 odpowiednikami są fałszywe Neighbor/Router Advertisement. Obrońca musi umieć rozpoznać ich ślady i znać kontrole: DHCP snooping, Dynamic ARP Inspection, RA Guard.

Poznasz budowę pakietu ARP, stany cache sąsiadów w Linuksie, komunikaty ND (RS, RA, NS, NA), adresy solicited-node i DAD, a na koniec przechwycisz rozwiązanie adresu bramy w IPv4 i IPv6.` },

  { type: 'DEFINITION', heading: 'ARP i Neighbor Discovery: pojęcia', body:
`- **ARP Request** — broadcast (\`ff:ff:ff:ff:ff:ff\`) „kto ma IP X? powiedz Y”; pole *Target MAC* = \`00:00:00:00:00:00\`.
- **ARP Reply** — unicast „IP X jest pod MAC M”.
- **Cache ARP / tablica sąsiadów** — mapowanie IP → MAC z czasem ważności (Linux: \`ip neigh\`; Windows: \`Get-NetNeighbor\`, \`arp -a\`).
- **Gratuitous ARP (GARP)** — ARP, w którym nadawca ogłasza własny adres (Sender IP = Target IP); służy do wykrywania konfliktów IP i aktualizacji cache innych hostów po przeniesieniu adresu (failover).
- **Proxy ARP** — router odpowiada na ARP w imieniu hostów z innej sieci; dziś rzadkie, bywa źródłem dziwnych zachowań.
- **Neighbor Discovery (ND, RFC 4861)** — zestaw komunikatów ICMPv6 zastępujących ARP i część DHCP w IPv6:
  - **RS (Router Solicitation, typ 133)** — host prosi routery o ogłoszenie,
  - **RA (Router Advertisement, typ 134)** — router ogłasza prefiksy, bramę, MTU, flagi SLAAC/DHCPv6,
  - **NS (Neighbor Solicitation, typ 135)** — odpowiednik ARP Request (i test osiągalności),
  - **NA (Neighbor Advertisement, typ 136)** — odpowiednik ARP Reply,
  - **Redirect (typ 137)** — router wskazuje lepszy next hop.
- **Adres solicited-node multicast** — \`ff02::1:ff\` + ostatnie 24 bity adresu IPv6; NS jest wysyłany na ten adres zamiast broadcastu (IPv6 nie ma broadcastu).
- **DAD (Duplicate Address Detection)** — przed użyciem adresu host wysyła NS z adresu \`::\`, by sprawdzić, czy nikt go nie używa.
- **NUD (Neighbor Unreachability Detection)** — mechanizm sprawdzania osiągalności sąsiadów (stany REACHABLE, STALE, DELAY, PROBE).` },

  { type: 'ANALOGY', heading: 'Pytanie zadane na cały pokój', body:
`Jesteś na sali konferencyjnej i musisz oddać dokument osobie o nazwisku „Kowalska” (adres IP), ale nie wiesz, jak wygląda (adres MAC).
- Wstajesz i pytasz **na cały pokój**: „Kto z państwa to pani Kowalska?” (ARP Request — broadcast).
- Wszyscy słyszą, ale odpowiada tylko ona: „To ja, siedzę przy oknie w niebieskim swetrze” (ARP Reply — unicast).
- **Zapisujesz w notesie**: „Kowalska — niebieski sweter przy oknie” (cache). Przy kolejnym dokumencie nie pytasz znowu, dopóki wpis jest świeży.
- Gdy Kowalska przesiądzie się, sama ogłasza: „Uwaga, teraz siedzę przy drzwiach!” (gratuitous ARP po failoverze).

A teraz problem bezpieczeństwa: nikt nie sprawdza dokumentów. Ktokolwiek może wstać i powiedzieć: „To ja jestem Kowalską” — a Ty oddasz mu dokument (ARP spoofing). Albo powtarzać co kilka sekund „Kowalska siedzi tutaj!”, żeby wszyscy poprawili notesy. W sali, gdzie ochrona na wejściu wydaje identyfikatory i pilnuje listy (DHCP snooping + DAI), taka sztuczka nie przejdzie.

W IPv6 zamiast pytać cały pokój, pytasz tylko stolik osób o tej samej końcówce nazwiska (solicited-node multicast) — mniej hałasu, ten sam brak uwierzytelnienia.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Pakiet ARP od środka', ...img('ns02-03-arp-packet.svg', 'Pola pakietu ARP z rozmiarami: HTYPE 2 B (1 = Ethernet), PTYPE 2 B (0x0800 = IPv4), HLEN 1 B (6), PLEN 1 B (4), OPER 2 B (1 request, 2 reply), SHA 6 B (MAC nadawcy), SPA 4 B (IP nadawcy), THA 6 B (MAC celu, zera w zapytaniu), TPA 4 B (szukany IP). Obok przykład zapytania w Wiresharku i opis gratuitous ARP.'),
    body:
`ARP nie jest pakietem IP — jedzie bezpośrednio w ramce Ethernet z EtherType **0x0806**. Tak wygląda para zapytanie–odpowiedź w \`tcpdump -nn -e arp\`:`,
    code:
`3c:52:82:1a:f0:07 > ff:ff:ff:ff:ff:ff, ethertype ARP (0x0806), length 42:
    Request who-has 192.168.1.1 tell 192.168.1.20, length 28
00:1a:2b:3c:4d:5e > 3c:52:82:1a:f0:07, ethertype ARP (0x0806), length 42:
    Reply 192.168.1.1 is-at 00:1a:2b:3c:4d:5e, length 28

Gratuitous ARP (np. po przełączeniu VRRP na zapasowy router):
00:1a:2b:3c:4d:99 > ff:ff:ff:ff:ff:ff, ethertype ARP (0x0806), length 42:
    Request who-has 192.168.1.1 tell 192.168.1.1, length 28     ← Sender IP = Target IP

IPv6 — to samo przez ICMPv6 (tcpdump -nn -e icmp6):
3c:52:82:1a:f0:07 > 33:33:ff:00:00:01, ethertype IPv6 (0x86dd):
    fe80::3e52:82ff:fe1a:f007 > ff02::1:ff00:1: ICMP6, neighbor solicitation, who has fe80::1
00:1a:2b:3c:4d:5e > 3c:52:82:1a:f0:07, ethertype IPv6 (0x86dd):
    fe80::1 > fe80::3e52:82ff:fe1a:f007: ICMP6, neighbor advertisement, tgt is fe80::1` },

  { type: 'CODE_BASIC', heading: 'Odczyt tablicy sąsiadów z Javy i obserwacja zmian MAC bramy', body:
`Java nie ma API do ARP, ale na Linuksie tablica jest dostępna w \`/proc/net/arp\`, a pełne stany zwraca \`ip -j neigh\` (JSON). Prosty monitor zmian MAC bramy to zaczątek detekcji ARP spoofingu na hoście (tak działa arpwatch).`,
    code:
`import java.nio.file.*;
import java.util.*;

public class GatewayMacWatch {
    // /proc/net/arp: IP address  HW type  Flags  HW address  Mask  Device
    static Map<String, String> readArp() throws Exception {
        Map<String, String> m = new HashMap<>();
        for (String line : Files.readAllLines(Path.of("/proc/net/arp")).subList(1, Integer.MAX_VALUE == 0 ? 1 : Files.readAllLines(Path.of("/proc/net/arp")).size())) {
            String[] c = line.trim().split("\\\\s+");
            if (c.length >= 4 && !c[3].equals("00:00:00:00:00:00")) m.put(c[0], c[3]);
        }
        return m;
    }

    public static void main(String[] args) throws Exception {
        String gw = args[0];                                   // np. 192.168.1.1
        String known = readArp().get(gw);
        System.out.println("MAC bramy: " + known);
        while (true) {
            Thread.sleep(5_000);
            String now = readArp().get(gw);
            if (now != null && known != null && !now.equals(known)) {
                System.out.printf("ALERT: MAC bramy %s zmienił się %s → %s%n", gw, known, now);   // → SIEM
            }
            if (now != null) known = now;
        }
    }
}
// W produkcji tę rolę pełnią arpwatch, Zeek (arp.log z pakietem) i reguły IDS — ale mechanizm jest ten sam.` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab: rozwiązanie adresu bramy w IPv4 i IPv6', ...img('ns02-03-neigh-states.svg', 'Automat stanów sąsiada w Linuksie: INCOMPLETE (wysłano zapytanie), REACHABLE (potwierdzony), STALE (nieaktualny), DELAY (czeka na potwierdzenie z TCP), PROBE (aktywnie sprawdza), FAILED (brak odpowiedzi), ze strzałkami przejść; poniżej przykładowy wynik ip neigh i uwaga, że FAILED lub INCOMPLETE dla bramy oznacza problem warstwy 2.'),
    body:
`Ćwiczenie z planu lekcji (Linux/WSL lub kontener z \`--cap-add NET_ADMIN\`):
1. **Sprawdź bramę**: \`ip route\` (IPv4) i \`ip -6 route\` (IPv6, brama zwykle \`fe80::…\`).
2. **Obejrzyj cache**: \`ip neigh\` — stan wpisu bramy (REACHABLE/STALE).
3. **Uruchom przechwytywanie**: \`tcpdump -i eth0 -nn -e 'arp or icmp6' -w neigh.pcap\`.
4. **Wyczyść cache**: \`sudo ip neigh flush dev eth0\` (IPv4 i IPv6).
5. **Wygeneruj ruch**: \`ping -c 2 <brama>\` i \`ping -6 -c 2 <brama-ipv6>%eth0\` (adres link-local wymaga strefy/interfejsu).
6. **Zatrzymaj i przeanalizuj w Wiresharku**: filtr \`arp\` — Request (broadcast) i Reply (unicast); filtr \`icmpv6.type == 135 || icmpv6.type == 136\` — NS na adres solicited-node (\`ff02::1:ff…\`, MAC \`33:33:ff:…\`) i NA.
7. **Obserwuj stany**: \`watch -n1 ip neigh\` — po odpowiedzi REACHABLE, po ok. 30 s bez potwierdzeń STALE, przy kolejnym ruchu DELAY → PROBE → REACHABLE.
8. **Wywołaj FAILED**: \`ping -c 3 <nieistniejący-adres-w-podsieci>\` → \`ip neigh\` pokazuje INCOMPLETE, potem FAILED; ping zgłasza „Destination Host Unreachable” z Twojego własnego adresu (to Twój host nie znalazł MAC).
9. **Zobacz DAD i RA** (opcjonalnie): \`sudo ip -6 addr add 2001:db8::99/64 dev eth0\` i obserwuj NS z adresu \`::\`; \`rdisc6 eth0\` (pakiet ndisc6) wysyła RS i pokazuje RA routera.` },

  { type: 'NOTE', heading: 'Adresy solicited-node i multicast MAC w IPv6', body:
`IPv6 nie ma broadcastu. Zamiast pytać wszystkich, NS trafia do grupy multicast, do której należy tylko szukany host (i nieliczne inne o tej samej końcówce). Obliczenie jest mechaniczne:`,
    code:
`solicited-node(adres) = ff02::1:ff  +  ostatnie 24 bity adresu
MAC dla multicastu IPv6 = 33:33     +  ostatnie 32 bity adresu multicast

adres                          ostatnie 24 bity   solicited-node             MAC docelowy ramki
${SAMPLES.map((a) => `${a.padEnd(30)} ${(expand6(a)[6].slice(2) + expand6(a)[7]).padEnd(17)} ${solicited(a).padEnd(26)} ${mcastMac(solicited(a))}`).join('\n')}

Inne ważne adresy ND:
ff02::1   wszystkie węzły (np. RA wysyłane okresowo)    MAC 33:33:00:00:00:01
ff02::2   wszystkie routery (cel RS)                    MAC 33:33:00:00:00:02
::        adres nieokreślony (źródło NS przy DAD)` },

  { type: 'CODE_PRACTICAL', heading: 'Polecenia: ip neigh, arping, Windows i przełączniki', body:
`Narzędzia do diagnozy i do ćwiczeń z gratuitous ARP (wyłącznie w labie):`,
    code:
`# Linux — tablica sąsiadów
ip neigh                                  # IPv4 i IPv6, ze stanami
ip -6 neigh show dev eth0
sudo ip neigh flush dev eth0              # wyczyszczenie (wymusza nowe zapytania)
sudo ip neigh replace 192.168.1.1 lladdr 00:1a:2b:3c:4d:5e dev eth0 nud permanent   # wpis statyczny
sysctl net.ipv4.neigh.eth0.base_reachable_time_ms     # domyślnie 30000 (losowo 15–45 s)

# arping (pakiet iputils-arping)
arping -I eth0 -c 3 192.168.1.1           # zapytania ARP na poziomie L2 (działa mimo blokady ICMP)
arping -D -I eth0 -c 2 192.168.1.50       # wykrywanie duplikatu: czy ktoś już używa adresu?
arping -U -I eth0 -c 2 192.168.1.50       # gratuitous ARP (ogłoszenie) — tylko własny adres, w labie

# Windows
Get-NetNeighbor -AddressFamily IPv4 | Where-Object State -ne Unreachable
arp -a
Remove-NetNeighbor -InterfaceAlias Ethernet -Confirm:$false

# Cisco — obrona L2
ip dhcp snooping
ip dhcp snooping vlan 10
ip arp inspection vlan 10                  # DAI: ARP sprawdzane z tablicą wiązań DHCP snooping
interface Gi1/0/1
 ip dhcp snooping trust                    # tylko port do serwera DHCP / uplink
 ip arp inspection trust
ipv6 nd raguard policy HOSTS               # RA Guard: hosty nie mogą wysyłać RA
 device-role host
show ip arp inspection statistics` },

  { type: 'USAGE', heading: 'ARP i ND w pracy developera, SRE i obrońcy', ...img('ns02-03-arp-spoofing-defense.svg', 'Po lewej objawy ARP spoofingu: w cache ofiary MAC bramy zmienia się z 00:1a:2b:3c:4d:5e na 66:77:88:99:aa:bb, w przechwyceniu seria niezamówionych odpowiedzi ARP, Wireshark ostrzega o zduplikowanym użyciu adresu. Po prawej wykrywanie i obrona: DHCP snooping, Dynamic ARP Inspection, arpwatch i Zeek, IDS, statyczne wpisy, 802.1X, TLS. Na dole odpowiedniki IPv6: fałszywe NA i RA oraz obrona RA Guard i ND Inspection.'),
    body:
`**Developer / SRE:**
- **Wirtualne IP i failover** (keepalived/VRRP przed Nginx, Pacemaker, Patroni dla PostgreSQL, MetalLB w trybie L2 w Kubernetesie) przenoszą adres na inny węzeł i wysyłają **gratuitous ARP**. Jeśli przełącznik lub klienci go zignorują, część ruchu przez kilkadziesiąt sekund trafia do martwego węzła.
- **Wymiana serwera z tym samym IP** — klienci w podsieci mają stary MAC w cache; objaw: „nowy serwer działa, ale niektóre usługi łączą się dopiero po minucie”.
- **Konflikt adresów IP** — dwa hosty z tym samym IP (statyczny adres w puli DHCP): połączenia losowo zrywane, logi o „duplicate address”.
- **Chmura** — w AWS/Azure/GCP ARP obsługuje warstwa wirtualizacji (hypervisor odpowiada za VPC), dlatego gratuitous ARP nie przenosi tam adresów — failover robi się przez API (przeniesienie Elastic IP/ENI, routing).

**Blue team:**
- **ARP spoofing / poisoning** (MITRE ATT&CK T1557.002) — napastnik w LAN ogłasza się jako brama i przechwytuje ruch. Ślady: zmiana MAC bramy w cache, zalew niezamówionych ARP Reply, ten sam MAC dla wielu IP, alert „duplicate use of IP” w Wiresharku, alerty arpwatch/Zeek/IDS.
- **Rogue RA (IPv6)** — fałszywy router ogłasza prefiks i bramę; hosty z włączonym IPv6 (domyślnie wszystkie współczesne systemy) zaczynają kierować ruch przez napastnika, nawet w „sieci tylko IPv4”. Obrona: **RA Guard**, monitorowanie RA (np. \`ndpmon\`, Zeek), wyłączenie IPv6 tylko tam, gdzie świadomie nie jest używany — lepiej je kontrolować niż ignorować.
- **Kontrole**: DHCP snooping + **Dynamic ARP Inspection**, RA Guard/ND Inspection, 802.1X, segmentacja VLAN, a w warstwie aplikacji **TLS z weryfikacją certyfikatów** — nawet skuteczny MITM nie odczyta ani nie zmieni treści.` },

  { type: 'PITFALL', heading: 'Typowe problemy i błędne założenia', body:
`- **„Destination Host Unreachable” = problem po stronie celu** — często komunikat wysyła **Twój własny host**: nie dostał odpowiedzi ARP (cel wyłączony, zły VLAN, zła maska).
- **Ping działa „co jakiś czas”** — konflikt IP (dwa MAC odpowiadają na ARP naprzemiennie) albo źle działający failover.
- **Blokowanie ICMPv6 jak ICMP w IPv4** — w IPv6 bez ICMPv6 (NS/NA/RS/RA/Packet Too Big) sieć przestaje działać. Filtruj selektywnie, nie całościowo.
- **„W mojej sieci nie ma IPv6”** — hosty i tak mają adresy link-local i słuchają RA; brak kontroli IPv6 to otwarta furtka dla rogue RA.
- **Statyczne wpisy ARP jako ochrona całej sieci** — nie skalują się i łatwo o pomyłkę; stosuj je wyłącznie punktowo (krytyczne hosty), a ochronę opieraj na DAI i 802.1X.
- **Adres link-local bez interfejsu** — \`ping fe80::1\` bez \`%eth0\` nie wie, którym interfejsem wysłać.
- **Długie cache w urządzeniach sieciowych** — routery i przełączniki L3 często trzymają ARP 4 godziny; po migracji serwera potrzebny GARP albo ręczne wyczyszczenie.
- **Gratuitous ARP z cudzym adresem w labie, który „przeciekł” do sieci domowej** — ćwiczenia ze spoofingiem tylko w odizolowanych sieciach Dockera/namespace’ach.` },

  { type: 'CODE_WRONG', heading: 'Źle: zaufanie do sieci lokalnej „bo to LAN”', body:
`Usługa wewnętrzna łączy się z bazą i API innego zespołu bez TLS „bo wszystko jest w tym samym VLAN-ie”. Wystarczy jeden przejęty host w segmencie i ARP spoofing, by przechwycić hasła i dane.`,
    code:
`# application.yml
spring:
  datasource:
    url: jdbc:postgresql://10.0.3.10:5432/orders?sslmode=disable   # hasło i dane jawnie w sieci
integration:
  billing-url: http://10.0.3.20:8080/api                            # tokeny w nagłówkach jawnie

# „Zabezpieczenie” sieci:
# - jeden płaski VLAN dla serwerów aplikacji, baz i stacji administratorów
# - brak DHCP snooping, DAI i RA Guard na przełącznikach
# - brak monitoringu zmian ARP` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: obrona w głąb — L2, sieć i aplikacja', body:
`Kontrole na przełącznikach utrudniają spoofing, segmentacja ogranicza zasięg, a TLS z weryfikacją certyfikatu sprawia, że nawet udany MITM nic nie daje (połączenie się nie zestawi).`,
    code:
`# application.yml — weryfikacja tożsamości serwera, nie tylko szyfrowanie
spring:
  datasource:
    url: jdbc:postgresql://db.internal:5432/orders?sslmode=verify-full&sslrootcert=/etc/pki/internal-ca.pem
integration:
  billing-url: https://billing.internal/api           # TLS (lub mTLS) z firmowym CA

# Przełączniki (dostęp):
#   ip dhcp snooping vlan 30 + ip arp inspection vlan 30   (DAI)
#   ipv6 nd raguard policy HOSTS na portach dostępowych
#   port security / 802.1X
# Sieć: osobne VLAN-y dla baz, aplikacji i stacji administracyjnych (rozdział 21)
# Monitoring: Zeek arp/ndp + reguła SIEM „zmiana MAC bramy” i statystyki DAI` },

  { type: 'WHEN_TO_USE', heading: 'Które narzędzie i kontrolę wybrać', body:
`- **\`ip neigh\` / \`Get-NetNeighbor\`** — pierwszy krok przy problemach w obrębie podsieci i z bramą (warstwa 2 z rozdziału 01).
- **\`arping\`** — sprawdzenie osiągalności L2 (gdy ICMP jest blokowany), wykrywanie duplikatów IP (\`-D\`), test failoveru VIP.
- **tcpdump/Wireshark z filtrem \`arp or icmp6\`** — gdy cache się nie wypełnia, wpisy skaczą lub podejrzewasz spoofing.
- **Gratuitous ARP** — po przeniesieniu adresu IP (keepalived/VRRP robi to automatycznie); ręcznie tylko w labie lub w procedurze migracji.
- **DHCP snooping + DAI** — każda sieć dostępowa z użytkownikami; najskuteczniejsza ochrona przed ARP spoofingiem.
- **RA Guard / ND Inspection** — każda sieć z hostami (także „bez IPv6”).
- **Statyczne wpisy ARP** — tylko pojedyncze, krytyczne relacje (np. serwer ↔ brama w odizolowanej strefie).
- **TLS z weryfikacją** — zawsze w aplikacjach; to ostatnia linia obrony, gdy warstwa 2 zawiedzie.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- ARP (IPv4, EtherType 0x0806) i Neighbor Discovery (IPv6, ICMPv6) zamieniają adres IP następnego skoku na adres MAC.
- ARP Request to broadcast z Target MAC = 00…00; Reply to unicast; wynik ląduje w cache (\`ip neigh\`) ze stanami REACHABLE, STALE, DELAY, PROBE, FAILED.
- ND: RS (133), RA (134), NS (135), NA (136), Redirect (137); NS idzie na adres solicited-node \`ff02::1:ff\` + 24 bity (MAC \`33:33:ff:…\`); DAD sprawdza duplikaty z adresu \`::\`.
- Gratuitous ARP ogłasza własny adres — podstawa failoveru VIP (VRRP, keepalived); w chmurze failover robi się przez API.
- ARP i ND nie mają uwierzytelnienia: ARP spoofing i rogue RA umożliwiają MITM. Obrona: DHCP snooping + DAI, RA Guard, 802.1X, segmentacja, monitoring zmian i TLS z weryfikacją certyfikatów.
- Następna lekcja: **unicast, broadcast, multicast i anycast**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const tasks = [
  ['Wykonaj Packet Lab z lekcji i zapisz: numer ramki z ARP Request, numer ramki z Reply, MAC bramy, czas między nimi.', 'Filtr arp; kolumna Time ustawiona na „since previous displayed”.', 'Request: broadcast od Twojego MAC, „Who has <brama>? Tell <Twój IP>”; Reply: unicast od bramy z jej MAC; czas zwykle poniżej 1 ms w LAN. Zapisz MAC bramy — przyda się jako baseline do wykrywania spoofingu.'],
  ['Wyjaśnij, dlaczego ping do nieistniejącego adresu w Twojej podsieci zwraca „Destination Host Unreachable” z Twojego własnego adresu, a ping do nieistniejącego adresu w Internecie — timeout.', 'Pierwszy cel jest w tej samej podsieci (ARP), drugi przez bramę.', 'Adres w tej samej podsieci: host wysyła ARP o MAC celu, nie dostaje odpowiedzi (INCOMPLETE → FAILED) i sam generuje komunikat unreachable. Adres w Internecie: ramka idzie do bramy (MAC bramy znany), a dalej pakiet ginie lub nikt nie odpowiada — brak komunikatu, więc timeout (chyba że router odeśle ICMP).'],
  ['Policz adres solicited-node i MAC docelowy ramki NS dla adresu 2001:db8:1::abcd:1234.', 'Ostatnie 24 bity: cd:1234.', `Solicited-node: ${solicited('2001:db8:1::abcd:1234')}; MAC: ${mcastMac(solicited('2001:db8:1::abcd:1234'))}.`],
  ['Zaobserwuj DAD: dodaj adres IPv6 do interfejsu w labie i przechwyć ruch ICMPv6. Jak wygląda NS przy DAD i czym różni się od zwykłego NS?', 'Źródło NS przy DAD to ::.', 'NS przy DAD: źródło :: (adres nieokreślony), cel ff02::1:ffXX:XXXX (solicited-node testowanego adresu), Target = testowany adres, brak opcji Source Link-Layer Address. Jeśli nikt nie odpowie NA, adres przechodzi ze stanu tentative do preferred (ip -6 addr pokazuje „tentative”, potem znika).'],
  ['W labie z dwoma namespace’ami ustaw ten sam adres IP na obu i pokaż konflikt: co pokaże arping -D oraz co dzieje się z połączeniami?', 'arping -D -I <if> <adres> zwraca kod błędu, gdy ktoś odpowie.', 'arping -D otrzymuje odpowiedź od drugiego hosta → „Received 1 response(s)” i kod wyjścia 1 (duplikat). Trzeci host, który komunikuje się z tym IP, ma w cache raz jeden, raz drugi MAC — połączenia TCP zrywają się (RST od hosta, który nie zna połączenia). Linux może też logować „duplicate address detected” dla IPv6.'],
  ['Skonfiguruj keepalived (VRRP) dla dwóch kontenerów Nginx w labie z wirtualnym IP i przechwyć gratuitous ARP przy przełączeniu. Ile trwała przerwa dla klienta?', 'Zatrzymaj główny węzeł i obserwuj tcpdump -nn -e arp oraz pętlę curl z timestampami.', 'Po zatrzymaniu MASTER zapasowy węzeł przejmuje VIP po kilku interwałach VRRP (np. 3 × 1 s) i wysyła serię gratuitous ARP z nowym MAC. Klienci aktualizują cache, przełącznik — tablicę MAC. Przerwa widziana przez klienta: zwykle 1–4 s; trwające połączenia TCP zostają zerwane (nowy węzeł ich nie zna).'],
  ['Napisz regułę detekcji (pseudokod lub zapytanie SIEM) wykrywającą ARP spoofing na podstawie logów Zeek arp.log lub alertów DAI.', 'Szukaj zmian mapowania IP → MAC dla bramy i dużej liczby niezamówionych odpowiedzi.', 'Przykład: dla IP bramy z listy (gateway_ips) alert, gdy zaobserwowany MAC ≠ MAC z baseline; dodatkowo: count(ARP reply bez wcześniejszego request) od jednego MAC > 20 w 1 min; jeden MAC ogłaszający > 3 różne IP w 5 min. Dla przełączników: syslog DAI „%SW_DAI-4-DHCP_SNOOPING_DENY” → alert z portem i MAC.'],
  ['Wyjaśnij, dlaczego TLS z weryfikacją certyfikatu chroni przed skutkami ARP spoofingu, a TLS bez weryfikacji (trust all) — nie.', 'MITM może zestawić TLS z ofiarą, ale nie ma klucza prywatnego serwera.', 'Napastnik w środku może przechwycić połączenie, ale nie przedstawi poprawnego certyfikatu serwera podpisanego przez zaufane CA dla tej nazwy — klient z weryfikacją przerwie handshake. Klient „trust all” (wyłączona walidacja, sslmode=require bez verify) przyjmie certyfikat napastnika — MITM odczyta i zmodyfikuje cały ruch.'],
  ['Na przełączniku (lub w opisie) skonfiguruj DHCP snooping i DAI dla VLAN 30. Które porty oznaczysz jako trusted i dlaczego?', 'Trusted tylko w stronę legalnego serwera DHCP i uplinków.', 'ip dhcp snooping; ip dhcp snooping vlan 30; ip arp inspection vlan 30; na uplinku i porcie serwera DHCP: ip dhcp snooping trust + ip arp inspection trust. Porty dostępowe (użytkownicy) pozostają untrusted — ich ARP jest sprawdzany z tablicą wiązań, a oferty DHCP z nich są blokowane (ochrona przed rogue DHCP). Hosty ze statycznym IP wymagają wpisów ARP ACL.'],
  ['Dlaczego gratuitous ARP nie działa jako mechanizm failoveru w AWS VPC i jak realizuje się tam przenoszenie adresu?', 'Kto odpowiada na ARP w VPC?', 'W VPC ruch L2 obsługuje warstwa wirtualizacji AWS, która zna przypisania IP do interfejsów ENI i nie uczy się z GARP. Przeniesienie adresu: przypisanie secondary private IP lub Elastic IP do innej ENI przez API (np. skrypt keepalived wywołujący AWS CLI), zmiana tras w route table albo load balancer (NLB) przed instancjami.'],
  ['Porównaj wynik ip neigh i Get-NetNeighbor (Windows) dla tej samej sieci. Jak nazywają się odpowiedniki stanów REACHABLE, STALE i FAILED?', 'Windows ma stany Reachable, Stale, Unreachable, Permanent, Incomplete, Probe, Delay.', 'Linux REACHABLE ↔ Windows Reachable; STALE ↔ Stale; FAILED ↔ Unreachable; INCOMPLETE ↔ Incomplete; PERMANENT ↔ Permanent (wpisy statyczne). Oba systemy implementują NUD z RFC 4861 także dla IPv4.'],
  ['Przygotuj procedurę migracji serwera bazy na nowy sprzęt z tym samym adresem IP, minimalizującą problemy z cache ARP u klientów i na routerze.', 'Myśl o GARP, czasach ARP na routerach i weryfikacji.', '1) Obniż czas przerwy: przygotuj nowy serwer z adresem w stanie down. 2) Wyłącz interfejs starego, włącz na nowym i wyślij GARP (arping -U -c 3). 3) Sprawdź na routerze/przełączniku L3 wpis ARP (show ip arp <IP>) — jeśli stary, wyczyść (clear ip arp <IP>). 4) Zweryfikuj z klientów: ip neigh, połączenia aplikacji, logi puli połączeń. 5) Plan wycofania: odwrotna kolejność.'],
];
for (const [q, h, a] of tasks) ex(q, h, a);
for (const a of ['2001:db8::1', 'fe80::a8bb:ccff:fe00:2', 'fd00:1::dead:beef', '2001:db8:abcd:12::1f00:77']) {
  ex(`Policz adres solicited-node i adres MAC ramki Neighbor Solicitation dla ${a}.`, 'ff02::1:ff + ostatnie 24 bity; MAC 33:33 + ostatnie 32 bity adresu multicast.', `Adres rozwinięty: ${expand6(a).join(':')}. Solicited-node: ${solicited(a)}. MAC: ${mcastMac(solicited(a))}.`);
}
const reads = [
  ['Request who-has 10.0.0.1 tell 10.0.0.1', 'Gratuitous ARP — host ogłasza własny adres 10.0.0.1 (np. po starcie interfejsu lub przejęciu VIP).'],
  ['Reply 10.0.0.1 is-at 66:77:88:99:aa:bb (wielokrotnie co 2 s, bez zapytań)', 'Seria niezamówionych odpowiedzi — typowy objaw ARP spoofingu (podszywanie się pod 10.0.0.1), zwłaszcza jeśli MAC różni się od znanego MAC bramy.'],
  ['Request who-has 10.0.0.77 tell 10.0.0.20 (powtarzane co 1 s, brak odpowiedzi)', 'Host 10.0.0.20 próbuje dotrzeć do 10.0.0.77, który nie odpowiada: wyłączony, w innym VLAN-ie lub nie istnieje — w cache stan INCOMPLETE/FAILED.'],
  ['ICMP6, router advertisement from fe80::bad:1, prefix 2001:db8:66::/64 (w sieci, w której nie ma routera IPv6)', 'Podejrzenie rogue RA — urządzenie ogłasza się jako router IPv6; hosty mogą skonfigurować adresy i bramę przez napastnika. Weryfikacja źródła (port, MAC) i włączenie RA Guard.'],
];
for (const [l, a] of reads) ex(`Zinterpretuj linię z przechwycenia: „${l}”.`, 'Kto wysyła, do kogo i czy jest odpowiedź?', a);
ex('Wyjaśnij, dlaczego w IPv6 nie wolno blokować całego ICMPv6 na firewallach hostów i sieci. Wypisz typy, które muszą przechodzić.', 'ND i PMTUD w IPv6 opierają się na ICMPv6.', 'Bez ICMPv6 nie działa: rozwiązywanie adresów (NS 135, NA 136), autokonfiguracja i wykrywanie routerów (RS 133, RA 134), PMTUD (Packet Too Big, typ 2), diagnostyka (Echo 128/129, Time Exceeded 3, Destination Unreachable 1, Parameter Problem 4). RFC 4890 opisuje rekomendacje filtrowania — przepuszczaj typy niezbędne, filtruj resztę (np. ograniczaj RA/Redirect z nieuprawnionych źródeł).');
ex('Zaprojektuj prosty „sentinel” dla laboratorium SOC: Zeek na porcie SPAN w VLAN-ie użytkowników. Jakie logi i alerty z warstwy 2 i ND warto zbierać?', 'Zeek ma analizatory ARP (pakiet) i ICMPv6/NDP.', 'Zbierać: zmiany IP ↔ MAC (arp, z baseline), niezamówione odpowiedzi ARP, ten sam MAC dla wielu IP, RA od nieuprawnionych źródeł (porównanie z listą routerów), duże ilości NS (skanowanie sąsiadów), DAD z konfliktami. Alerty kierować do SIEM z identyfikacją portu (łączenie z tablicą MAC przełącznika).');
const more = [
  ['Odczytaj w Wiresharku pola pakietu ARP Request z Twojego przechwycenia (Sender MAC/IP, Target MAC/IP, Opcode) i porównaj z ilustracją lekcji.', 'Rozwiń drzewo Address Resolution Protocol.', 'Opcode request (1), Sender MAC = MAC Twojej karty, Sender IP = Twój adres, Target MAC = 00:00:00:00:00:00, Target IP = adres bramy. W Reply: Opcode 2, Sender = brama, Target = Ty.'],
  ['Wyjaśnij, dlaczego po zapytaniu ARP od laptopa także brama ma w cache wpis laptopa, choć nic o niego nie pytała.', 'Brama widzi Sender IP i Sender MAC w zapytaniu.', 'Odbiorca zapytania (cel) zapisuje mapowanie nadawcy z pól SPA/SHA — i tak za chwilę będzie musiał odpowiedzieć i wymieniać ramki z pytającym. Oszczędza to drugie zapytanie ARP w przeciwnym kierunku.'],
  ['W labie (dwa namespace’y i bridge) ustaw na jednym z nich statyczny wpis ARP z błędnym MAC drugiego. Co się stanie z pingiem i jak to wykryjesz?', 'ip neigh replace … nud permanent.', 'Ramki trafiają na nieistniejący MAC — przełącznik je zalewa lub odrzuca, ping nie działa, a ip neigh pokazuje PERMANENT z błędnym adresem (wpis nie wygasa, więc problem nie zniknie sam). Wykrycie: porównanie MAC w ip neigh z ip link drugiego hosta; usunięcie wpisu ip neigh del.'],
  ['Przygotuj w Javie metodę, która dla adresu IPv6 zwraca jego adres solicited-node (z użyciem Inet6Address i operacji na bajtach).', 'Ostatnie 3 bajty adresu + prefiks ff02::1:ff00:0/104.', 'byte[] a = InetAddress.getByName(addr).getAddress();\nbyte[] sn = new byte[16];\nsn[0] = (byte) 0xff; sn[1] = 0x02; sn[11] = 0x01; sn[12] = (byte) 0xff;\nsn[13] = a[13]; sn[14] = a[14]; sn[15] = a[15];\nreturn InetAddress.getByAddress(sn).getHostAddress();   // np. ff02:0:0:0:0:1:ff00:1'],
  ['Przy przełączeniu VIP keepalived część klientów nadal przez 2 minuty łączy się ze starym węzłem. Wymień trzy możliwe przyczyny i sposoby weryfikacji.', 'GARP nie dotarł, cache długi, przełącznik.', '1) GARP zablokowany (DAI, polityki przełącznika, VLAN) — tcpdump na kliencie; 2) klienci/routery ignorują GARP lub mają długie cache (routery L3 nawet 4 h) — show ip arp, ip neigh; 3) przełącznik ma stary wpis MAC na porcie starego węzła — show mac address-table. Rozwiązania: garp_master_repeat/refresh w keepalived, krótsze cache, wyjątki DAI dla VIP.'],
  ['Opisz, jak wyglądałby atak rogue RA w sieci „bez IPv6” i dlaczego wyłączenie IPv6 na hostach nie zawsze jest najlepszą odpowiedzią.', 'Hosty mają IPv6 włączone domyślnie i preferują je.', 'Napastnik wysyła RA z prefiksem i sobą jako bramą — hosty konfigurują adresy IPv6 i (np. z fałszywym serwerem DNS przez RDNSS) kierują część ruchu przez napastnika. Wyłączenie IPv6 na wszystkich hostach bywa trudne (aktualizacje, aplikacje Microsoft zakładają IPv6) i niezalecane; lepiej kontrolować IPv6: RA Guard na przełącznikach, monitoring RA, docelowo świadome wdrożenie IPv6.'],
  ['Porównaj liczbę ramek rozgłoszeniowych w dużym VLAN-ie IPv4 (2000 hostów) i jego odpowiedniku IPv6. Dlaczego ND jest „cichsze”?', 'Broadcast vs solicited-node multicast z IGMP/MLD snooping.', 'W IPv4 każde zapytanie ARP to broadcast — przetwarza je każdy z 2000 hostów. W IPv6 NS trafia do grupy solicited-node, do której należy zwykle jeden host; z MLD snooping przełącznik wysyła je tylko na właściwy port. Hosty nie są przerywane cudzymi zapytaniami.'],
  ['Na podstawie lekcji przygotuj krótką listę kontrolną „podejrzewam ARP spoofing” dla dyżurnego SOC.', 'Od weryfikacji do ograniczenia skutków.', '1) Porównaj MAC bramy u ofiary (ip neigh/arp -a) z wartością baseline. 2) Ustal, do którego portu należy podejrzany MAC (tablica MAC przełącznika). 3) Sprawdź logi DAI/DHCP snooping i alerty IDS. 4) Odizoluj port (shutdown/VLAN kwarantanny), zabezpiecz przechwycenie ruchu. 5) Oceń, jakie sesje mogły zostać przechwycone (protokoły bez TLS), wymuś zmianę haseł/tokenów. 6) Popraw kontrole (DAI, 802.1X).'],
];
for (const [q, h, a] of more) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111408);
for (const a of SAMPLES.concat(['2001:db8:5::7:8', 'fe80::200:5eff:fe00:101', '2001:db8::c0a8:164', 'fd12:3456::ab:cdef'])) {
  const sn = solicited(a);
  const g = expand6(a);
  const wrongA = `ff02::1:ff${g[7].slice(0, 2)}:${g[7].slice(2)}${g[6].slice(2)}`;
  add(`Jaki jest adres solicited-node multicast dla ${a}?`, sn, [wrongA, `ff02::1:${g[6]}:${g[7]}`, `ff02::2:ff${(g[6].slice(2) + g[7]).slice(0, 2)}:${(g[6].slice(2) + g[7]).slice(2)}`, 'ff02::1'], `ff02::1:ff + ostatnie 24 bity (${g[6].slice(2)}${g[7]}) → ${sn}.`);
}
for (const m of ['ff02::1', 'ff02::2', 'ff02::1:ff00:1', 'ff02::1:ff1a:f007', 'ff02::fb', 'ff02::1:ffbe:ef']) {
  const mac = mcastMac(m);
  add(`Jaki adres MAC docelowy ma ramka do grupy multicast IPv6 ${m}?`, mac, ['ff:ff:ff:ff:ff:ff', '01:00:5e:' + mac.slice(9), mac.replace('33:33', '33:33:ff').slice(0, 17)].filter((x, i, arr) => x !== mac && arr.indexOf(x) === i).concat(['33:33:00:00:00:00']).slice(0, 3), `33:33 + ostatnie 32 bity adresu (${expand6(m)[6]}${expand6(m)[7]}) → ${mac}.`);
}
const types = [[133, 'Router Solicitation'], [134, 'Router Advertisement'], [135, 'Neighbor Solicitation'], [136, 'Neighbor Advertisement'], [137, 'Redirect']];
const tAll = types.map((t) => t[1]);
for (const [n, d] of types) {
  add(`Który komunikat ND ma typ ICMPv6 ${n}?`, d, tAll.filter((x) => x !== d).slice(0, 3), `${n} = ${d}.`);
  add(`Jaki numer typu ICMPv6 ma ${d}?`, String(n), types.map((t) => String(t[0])).filter((x) => x !== String(n)).slice(0, 3), `${d} = typ ${n}.`);
}
const st = [['REACHABLE', 'Sąsiad niedawno potwierdził osiągalność'], ['STALE', 'Wpis nieaktualny — używany, ale wymaga ponownego potwierdzenia'], ['INCOMPLETE', 'Wysłano zapytanie, nie ma jeszcze odpowiedzi'], ['FAILED', 'Rozwiązanie adresu się nie powiodło — brak odpowiedzi'], ['PROBE', 'Host aktywnie sprawdza sąsiada zapytaniami unicast'], ['PERMANENT', 'Wpis statyczny dodany ręcznie, nie wygasa']];
const stAll = st.map((x) => x[1]);
for (const [k, d] of st) add(`Co oznacza stan „${k}” w wyniku ip neigh?`, d, stAll.filter((x) => x !== d).slice(0, 3), `${k}: ${d.toLowerCase()}.`);
const facts = [
  ['Jaki EtherType ma ARP?', '0x0806', ['0x0800', '0x86DD', '0x8100'], 'ARP jedzie bezpośrednio w ramce Ethernet, nie w IP.'],
  ['Na jaki adres MAC wysyłane jest zapytanie ARP Request?', 'ff:ff:ff:ff:ff:ff (broadcast)', ['Adres MAC bramy', '33:33:00:00:00:01', '00:00:00:00:00:00'], 'Pytający nie zna MAC celu.'],
  ['Jaką wartość ma pole Target MAC w ARP Request?', '00:00:00:00:00:00', ['ff:ff:ff:ff:ff:ff', 'MAC nadawcy', 'MAC bramy'], 'Pole jest nieznane — to właśnie szukamy.'],
  ['Jaki kod operacji (OPER) ma ARP Reply?', '2', ['1', '0', '6'], '1 = request, 2 = reply.'],
  ['Ile bajtów ma pakiet ARP dla Ethernet i IPv4?', '28', ['20', '42', '64'], '8 B nagłówka + 2 × (6 + 4) B adresów; ramka po dopełnieniu ma 60/64 B.'],
  ['Czym charakteryzuje się gratuitous ARP?', 'Adres IP nadawcy jest równy adresowi IP celu — host ogłasza własny adres', ['Jest wysyłany tylko przez routery', 'Ma zawsze kod 3', 'Jest szyfrowany'], 'Używany przy failoverze i wykrywaniu konfliktów.'],
  ['Do czego służy gratuitous ARP w VRRP/keepalived?', 'Do aktualizacji cache ARP klientów i tablic przełączników po przejęciu wirtualnego IP', ['Do szyfrowania VIP', 'Do wyboru mastera', 'Do synchronizacji czasu'], 'Bez niego klienci wysyłaliby ruch do martwego węzła.'],
  ['Dlaczego ARP jest podatny na spoofing?', 'Bo nie ma uwierzytelnienia — hosty przyjmują odpowiedzi od każdego w segmencie', ['Bo używa UDP', 'Bo jest szyfrowany słabym algorytmem', 'Bo działa tylko w Wi-Fi'], 'Stąd potrzeba DAI i innych kontroli.'],
  ['Które zabezpieczenie przełącznika sprawdza pakiety ARP z tablicą wiązań DHCP snooping?', 'Dynamic ARP Inspection (DAI)', ['RA Guard', 'BPDU Guard', 'Storm control'], 'Odrzuca ARP z niezgodnym mapowaniem IP ↔ MAC.'],
  ['Które zabezpieczenie chroni przed fałszywymi Router Advertisement w IPv6?', 'RA Guard', ['DAI', 'Port security', 'HSTS'], 'Blokuje RA z portów hostów.'],
  ['Jak nazywa się technika ATT&CK obejmująca ARP cache poisoning?', 'Adversary-in-the-Middle (T1557.002)', ['Phishing (T1566)', 'Valid Accounts (T1078)', 'Data Encrypted for Impact (T1486)'], 'Podtechnika ARP Cache Poisoning.'],
  ['Co ogłasza Router Advertisement?', 'Prefiksy, bramę domyślną (router), MTU i flagi konfiguracji (SLAAC/DHCPv6)', ['Adres MAC przełącznika', 'Listę użytkowników', 'Certyfikat TLS'], 'Na tej podstawie hosty konfigurują IPv6.'],
  ['Z jakiego adresu źródłowego wysyłany jest NS podczas DAD?', '::', ['ff02::1', 'fe80::1', 'Z testowanego adresu'], 'Adres nie jest jeszcze potwierdzony, więc host nie może go użyć.'],
  ['Dlaczego IPv6 używa adresu solicited-node zamiast broadcastu?', 'Bo IPv6 nie ma broadcastu, a multicast ogranicza zapytanie do nielicznych hostów', ['Bo broadcast jest szyfrowany', 'Bo tak wymaga TCP', 'Bo MAC w IPv6 ma 64 bity'], 'Mniej przerwań dla hostów, których zapytanie nie dotyczy.'],
  ['Kto wysyła komunikat „Destination Host Unreachable” przy pingu do wyłączonego hosta w tej samej podsieci?', 'Najczęściej własny host nadawcy, który nie otrzymał odpowiedzi ARP', ['Zawsze router brzegowy', 'Serwer DNS', 'Host docelowy'], 'Warto patrzeć, z jakiego adresu przyszedł komunikat.'],
  ['Które polecenie wykryje, czy adres IP jest już używany w sieci, zanim go przydzielisz?', 'arping -D -I eth0 <adres>', ['ping -6 <adres>', 'ip route get <adres>', 'dig <adres>'], 'Tryb wykrywania duplikatów (DAD w IPv4).'],
  ['Dlaczego gratuitous ARP nie przenosi adresu IP w AWS VPC?', 'Bo mapowania w VPC zarządza warstwa wirtualizacji, która nie uczy się z GARP', ['Bo AWS nie obsługuje IPv4', 'Bo GARP jest szyfrowany', 'Bo VPC nie ma adresów MAC'], 'Failover robi się przez API (ENI, Elastic IP, trasy).'],
  ['Co jest najskuteczniejszą ochroną treści komunikacji, gdy napastnikowi uda się ARP spoofing?', 'TLS z weryfikacją certyfikatu serwera', ['Statyczny wpis ARP na jednym hoście', 'Dłuższy czas cache ARP', 'Wyłączenie IPv6'], 'MITM nie przedstawi poprawnego certyfikatu.'],
  ['Jak wysłać ping do adresu link-local fe80::1 w Linuksie?', 'ping -6 fe80::1%eth0 (z identyfikatorem interfejsu)', ['ping fe80::1', 'ping -4 fe80::1', 'arping fe80::1'], 'Link-local jest niejednoznaczny bez interfejsu.'],
  ['Jaki objaw sugeruje konflikt adresów IP w podsieci?', 'Połączenia losowo zrywane, a w przechwyceniu dwa różne MAC odpowiadają na ARP dla tego samego IP', ['Stały wysoki RTT', 'Błędy certyfikatu TLS', 'Brak rekordów DNS'], 'Wireshark: „duplicate use of IP detected”.'],
  ['Czym jest proxy ARP?', 'Odpowiadaniem routera na zapytania ARP w imieniu hostów z innej sieci', ['Szyfrowaniem ARP', 'ARP przez HTTP proxy', 'Buforowaniem ARP w przeglądarce'], 'Dziś rzadko używane, bywa źródłem nieoczekiwanych zachowań.'],
  ['Ile domyślnie wynosi bazowy czas osiągalności sąsiada w Linuksie (base_reachable_time)?', '30 sekund (losowo 15–45 s)', ['4 godziny', '1 sekunda', 'Wpis nigdy nie wygasa'], 'Routery i przełączniki L3 często trzymają ARP dłużej (np. 4 h).'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
const reads2 = [
  ['Request who-has 192.168.1.1 tell 192.168.1.1', 'Gratuitous ARP'], ['Reply 192.168.1.1 is-at 00:1a:2b:3c:4d:5e (po zapytaniu)', 'Zwykła odpowiedź ARP'],
  ['NS z adresu :: dla 2001:db8::5', 'Wykrywanie duplikatu adresu (DAD)'], ['RA od nieznanego urządzenia na porcie użytkownika', 'Podejrzenie rogue RA'],
  ['Seria niezamówionych Reply z nowym MAC dla IP bramy', 'Podejrzenie ARP spoofingu'],
];
const r2All = reads2.map((x) => x[1]);
for (const [l, d] of reads2) add(`Jak sklasyfikować zdarzenie: „${l}”?`, d, r2All.filter((x) => x !== d).slice(0, 3), `${d}.`);
const scen = [
  ['10.0.0.20', '3c:52:82:1a:f0:07', '10.0.0.1'], ['192.168.5.7', '00:50:56:9a:bc:01', '192.168.5.254'], ['172.16.3.40', '02:42:ac:10:03:28', '172.16.3.41'], ['10.10.10.5', 'b8:27:eb:12:34:56', '10.10.10.1'],
];
for (const [ip, mac, tgt] of scen) {
  const pool = [ip, tgt, '0.0.0.0', '255.255.255.255', mac, '00:00:00:00:00:00', 'ff:ff:ff:ff:ff:ff'];
  add(`Host ${ip} (MAC ${mac}) pyta ARP o ${tgt}. Jaki jest Target IP (TPA) w zapytaniu?`, tgt, pool.filter((x) => x !== tgt && x.includes('.')).slice(0, 3), 'TPA to szukany adres IP.');
  add(`Host ${ip} (MAC ${mac}) pyta ARP o ${tgt}. Jaki jest Target MAC (THA) w zapytaniu?`, '00:00:00:00:00:00', [mac, 'ff:ff:ff:ff:ff:ff', '33:33:00:00:00:01'], 'MAC celu jest nieznany — pole wypełnione zerami (adres ramki to broadcast).');
  add(`Host ${ip} (MAC ${mac}) pyta ARP o ${tgt}. Jaki jest docelowy adres MAC ramki Ethernet z tym zapytaniem?`, 'ff:ff:ff:ff:ff:ff', [mac, '00:00:00:00:00:00', '01:00:5e:00:00:01'], 'ARP Request jest rozgłaszany.');
  add(`Host ${tgt} odpowiada na zapytanie ARP od ${ip} (${mac}). Jaki jest docelowy adres MAC ramki z odpowiedzią?`, mac, ['ff:ff:ff:ff:ff:ff', '00:00:00:00:00:00', '33:33:ff:00:00:01'], 'ARP Reply jest wysyłany unicastem do pytającego.');
}
const more6 = ['2001:db8::a:b', 'fe80::1234:5678', '2001:db8:1:2:3:4:5:6', 'fe80::42:acff:fe11:2', 'fd00::100', '2001:db8::ff:fe00:1', 'fe80::dead:beef:cafe', '2001:db8:aa::bbbb:cccc', 'fd34::1:2:3', '2001:db8:0:1::9'];
for (const a of more6) {
  const mac = mcastMac(solicited(a));
  add(`Jaki adres MAC docelowy będzie miała ramka z Neighbor Solicitation o adres ${a}?`, mac, ['ff:ff:ff:ff:ff:ff', '33:33:00:00:00:01', mac.replace('33:33:ff', '33:33:00'), '01:00:5e:00:00:fb', '33:33:ff:ff:ff:ff'].filter((x, i, arr) => x !== mac && arr.indexOf(x) === i).slice(0, 3), `Solicited-node ${solicited(a)} → MAC 33:33 + ostatnie 32 bity = ${mac}.`);
}
const nd = [
  ['Host chce poznać MAC sąsiada IPv6', 'Neighbor Solicitation'], ['Sąsiad podaje swój MAC w odpowiedzi', 'Neighbor Advertisement'], ['Host po uruchomieniu prosi routery o ogłoszenie', 'Router Solicitation'],
  ['Router okresowo ogłasza prefiks i bramę', 'Router Advertisement'], ['Router wskazuje hostowi lepszy następny skok', 'Redirect'], ['Host sprawdza, czy nikt nie używa jego nowego adresu (DAD)', 'Neighbor Solicitation'],
  ['Host ogłasza zmianę swojego adresu MAC (niezamówione ogłoszenie)', 'Neighbor Advertisement'],
];
const ndAll = ['Neighbor Solicitation', 'Neighbor Advertisement', 'Router Solicitation', 'Router Advertisement', 'Redirect'];
for (const [d, a] of nd) add(`Który komunikat ND służy do: ${d.toLowerCase()}?`, a, ndAll.filter((x) => x !== a).slice(0, 3), `${d}: ${a}.`);
const extra = [
  ['Które polecenie Linuksa czyści tablicę sąsiadów na interfejsie eth0?', 'ip neigh flush dev eth0', ['ip route flush', 'arp -a', 'ss -K'], 'Wymusza ponowne rozwiązanie adresów.'],
  ['Które polecenie Windows PowerShell pokaże tablicę sąsiadów IPv4?', 'Get-NetNeighbor -AddressFamily IPv4', ['Get-NetRoute', 'Resolve-DnsName', 'Get-NetTCPConnection'], 'Odpowiednik ip neigh.'],
  ['Która opcja arping wysyła gratuitous ARP (ogłoszenie)?', '-U', ['-D', '-c', '-I'], '-D służy do wykrywania duplikatów, -U do niezamówionego ogłoszenia.'],
  ['Na jaki adres IPv6 wysyłane jest Router Solicitation?', 'ff02::2 (wszystkie routery)', ['ff02::1', 'ff02::1:ff00:1', '::'], 'Odpowiedź RA może przyjść na ff02::1 lub unicastem.'],
  ['Na jaki adres IPv6 router okresowo wysyła Router Advertisement?', 'ff02::1 (wszystkie węzły)', ['ff02::2', 'ff02::fb', 'fe80::1'], 'Wszystkie hosty w łączu słuchają RA.'],
  ['Co zawiera tablica wiązań DHCP snooping, z której korzysta DAI?', 'Mapowania adres IP ↔ MAC ↔ port ↔ VLAN z przydziałów DHCP', ['Listę certyfikatów', 'Tablicę routingu', 'Hasła użytkowników'], 'ARP niezgodny z tablicą jest odrzucany.'],
  ['Dlaczego DAI wymaga szczególnej konfiguracji dla hostów ze statycznym adresem IP?', 'Bo nie mają wpisu w tablicy wiązań DHCP — potrzebne są ARP ACL lub wpisy statyczne', ['Bo statyczne adresy nie używają ARP', 'Bo DAI działa tylko w IPv6', 'Bo statyczne hosty nie mają MAC'], 'Inaczej ich ARP zostanie zablokowany.'],
  ['Który element w Wiresharku sygnalizuje możliwy ARP spoofing?', 'Ostrzeżenie Expert Info „duplicate use of IP detected”', ['Kolor zielony dla HTTP', 'Kolumna Length', 'Statystyka Protocol Hierarchy'], 'Dwa różne MAC dla jednego IP.'],
];
for (const [q, a, w, e] of extra) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
