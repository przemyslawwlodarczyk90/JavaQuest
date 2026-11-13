// Generuje lekcje _ns_02_ethernet_lan/01_EthernetFramesAndMtu.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_01_ethernet_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/01_EthernetFramesAndMtu.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { build, hexdump } = require('./ns01_05_packet.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const frameLen = (payload, vlan = false) => Math.max(64, 14 + (vlan ? 4 : 0) + payload + 4);
const pingPayload = (mtu, v6 = false) => mtu - 8 - (v6 ? 40 : 20);
const TUN = [['PPPoE', 8], ['GRE', 24], ['VXLAN', 50], ['WireGuard (rezerwa IPv6)', 80], ['IPsec ESP (typowo)', 73], ['tag VLAN 802.1Q', 0]];
const pk = build();
const HDR = hexdump(pk.frame.slice(0, 32));

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Ethernet: format, w którym podróżuje prawie wszystko', ...img('ns02-01-frame-anatomy.svg', 'Pasek ramki Ethernet: preambuła 7 bajtów, SFD 1, MAC docelowy 6, MAC źródłowy 6, EtherType 2, dane 46–1500, FCS 4, przerwa międzyramkowa 12. Pod spodem opis pól: adresy MAC, EtherType z przykładowymi wartościami, dopełnianie danych do 46 bajtów, suma CRC-32 i elementy warstwy fizycznej niewidoczne w przechwyceniu.'),
    body:
`Kabel w biurze, łącze w data center, wirtualny interfejs \`veth\` kontenera, sieć w chmurze pod spodem VPC, Wi-Fi po przetłumaczeniu przez punkt dostępowy — niemal wszędzie dane podróżują w **ramkach Ethernet**. Każdy pakiet IP, który wysyła Twoja aplikacja Java, jest zapakowany w ramkę z adresami MAC, typem zawartości i sumą kontrolną.

Dla developera najważniejsza liczba tej lekcji to **MTU** — największy pakiet IP, który zmieści się w ramce. W klasycznym Ethernecie to **1500 bajtów**. Problemy z MTU należą do najbardziej podstępnych awarii: ping działa, mały JSON działa, a duża odpowiedź, handshake TLS z długim łańcuchem certyfikatów albo upload pliku „wisi”. Dzieje się tak szczególnie w tunelach (VPN, VXLAN w Kubernetesie, WireGuard), które zabierają część miejsca w ramce.

W tej lekcji:
- rozbierzesz ramkę Ethernet II na pola (z prawdziwym zrzutem bajtów),
- poznasz tag VLAN 802.1Q, jumbo frames i minimalną długość ramki,
- policzysz narzut tuneli i MTU wewnątrz nich,
- znajdziesz MTU ścieżki pingiem z flagą DF,
- zobaczysz, jak MTU ustawia się w Linuksie, Dockerze, Kubernetesie i AWS — oraz co liczniki błędów ramek mówią obrońcy.` },

  { type: 'DEFINITION', heading: 'Pola ramki i pojęcia warstwy łącza', body:
`- **Preambuła i SFD** (8 B) — wzorzec synchronizacji zegara odbiornika i znacznik początku ramki; warstwa fizyczna, nie trafia do przechwycenia.
- **Adres MAC docelowy i źródłowy** (po 6 B) — adresy kart w tym samym segmencie L2; zmieniają się na każdym przeskoku przez router.
- **EtherType** (2 B) — typ zawartości: 0x0800 IPv4, 0x86DD IPv6, 0x0806 ARP, 0x8100 tag VLAN, 0x88CC LLDP, 0x888E EAPOL. Wartość **≤ 1500** oznacza zamiast tego *długość* (stary format IEEE 802.3 z LLC), **≥ 0x0600 (1536)** — EtherType.
- **Dane (payload)** — 46–1500 B; krótsze dane karta dopełnia zerami (*padding*), by ramka miała co najmniej **64 B** (z FCS).
- **FCS** (4 B) — suma CRC-32; ramka z błędną sumą jest odrzucana po cichu.
- **IFG** (12 B) — przerwa międzyramkowa wymagana przez standard.
- **MTU (Maximum Transmission Unit)** — maksymalny rozmiar *pakietu warstwy 3* w ramce (1500); ramka maksymalnie 1518 B (1522 z tagiem VLAN).
- **Jumbo frames** — ramki z MTU ok. 9000 B; używane w data center i sieciach storage, wymagają obsługi przez wszystkie urządzenia na ścieżce.
- **Tag 802.1Q** — 4 B wstawione po adresach MAC: TPID 0x8100 i TCI (priorytet PCP 3 bity, DEI 1 bit, VLAN ID 12 bitów).
- **Runt / giant** — ramka krótsza niż 64 B / dłuższa niż dopuszczalna; liczniki na przełączniku sygnalizują problemy (kolizje, zła konfiguracja MTU).
- **Duplex** — full duplex (jednoczesne nadawanie i odbiór, przełączniki) vs half duplex (dawne huby, CSMA/CD); niezgodność duplexu powoduje kolizje i błędy.
- **MACsec (802.1AE)** — szyfrowanie i integralność ramek na łączu L2 (np. między przełącznikami, w data center).` },

  { type: 'ANALOGY', heading: 'Kontener morski i tunel kolejowy', body:
`Ramka Ethernet jest jak **standardowy kontener morski**: ma ustalone wymiary, oznaczenia nadawcy i odbiorcy na drzwiach (MAC), etykietę „co w środku” (EtherType) i plombę (FCS). Port (przełącznik) nie otwiera kontenera — czyta oznaczenia i stawia go na właściwym statku.

**MTU** to wewnętrzna wysokość kontenera. Ładunek (pakiet IP) musi się zmieścić — inaczej trzeba go podzielić albo odrzucić.

**Tunel** (VPN, VXLAN) to sytuacja, gdy cały kontener trzeba przewieźć **wewnątrz innego kontenera** — np. przez tunel kolejowy, w którym wagony mają ograniczoną wysokość. Ściany zewnętrznego kontenera zabierają kilkadziesiąt centymetrów, więc do środka zmieści się mniejszy ładunek niż zwykle. Jeśli nadawca o tym nie wie i ładuje „pełny wymiar”, ładunek zostaje na rampie — a nadawca czeka na potwierdzenie, które nigdy nie przyjdzie.

**Jumbo frames** to większe kontenery — wydajniejsze na trasach, które je obsługują w całości, ale bezużyteczne, jeśli choć jeden most po drodze jest za niski.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Ramka z tagiem VLAN i jumbo frames', ...img('ns02-01-vlan-jumbo.svg', 'Dwa paski: ramka zwykła do 1518 bajtów (MAC docelowy, MAC źródłowy, Type, dane do 1500, FCS) oraz ramka z tagiem VLAN do 1522 bajtów z dodatkowymi polami TPID 0x8100 i TCI z VLAN ID. Adnotacje: jumbo frames do około 9000 bajtów danych, tag VLAN nie zmienia MTU.'),
    body:
`Nagłówek ramki z poprzedniego rozdziału (żądanie HTTP między kontenerami) — pierwsze 32 bajty:`,
    code:
`${HDR}

bajty 0–5    02 42 ac 11 00 02   MAC docelowy
bajty 6–11   02 42 ac 11 00 03   MAC źródłowy
bajty 12–13  08 00               EtherType = IPv4
bajt 14…     45 00 …             początek pakietu IPv4 (wersja 4, IHL 5)

Ta sama ramka z tagiem VLAN 100 (priorytet 0):
bajty 12–13  81 00               TPID = 802.1Q
bajty 14–15  00 64               TCI: PCP=0, DEI=0, VLAN ID = 0x064 = 100
bajty 16–17  08 00               EtherType = IPv4 (przesunięty o 4 bajty)

Rozmiary: ramka minimalna 64 B, maksymalna 1518 B (1522 z tagiem, 1526 z dwoma tagami QinQ),
jumbo zwykle do 9018–9022 B ramki (MTU 9000).` },

  { type: 'CODE_BASIC', heading: 'Parser nagłówka Ethernet z obsługą tagów VLAN w Javie', body:
`Prosty parser, który odczyta adresy MAC, rozpozna tag 802.1Q (także podwójny QinQ) i zwróci właściwy EtherType oraz offset danych. Ten sam algorytm stosują dekodery Wiresharka i sondy IDS.`,
    code:
`import java.nio.ByteBuffer;
import java.util.HexFormat;

record EthHeader(String dst, String src, int vlanId, int etherType, int payloadOffset) {}

final class EthernetParser {
    static EthHeader parse(byte[] frame) {
        ByteBuffer b = ByteBuffer.wrap(frame);              // big-endian = kolejność sieciowa
        String dst = mac(frame, 0), src = mac(frame, 6);
        int off = 12, vlan = -1;
        int type = b.getShort(off) & 0xFFFF;
        while (type == 0x8100 || type == 0x88A8) {          // 802.1Q lub QinQ (802.1ad)
            int tci = b.getShort(off + 2) & 0xFFFF;
            vlan = tci & 0x0FFF;                             // 12 bitów VLAN ID (zostaje wewnętrzny)
            off += 4;
            type = b.getShort(off) & 0xFFFF;
        }
        if (type <= 1500) throw new IllegalArgumentException("ramka 802.3 z długością " + type + " (LLC), nie Ethernet II");
        return new EthHeader(dst, src, vlan, type, off + 2);
    }
    private static String mac(byte[] f, int o) {
        return HexFormat.ofDelimiter(":").formatHex(f, o, o + 6);
    }
}
// parse(ramka) → EthHeader[dst=02:42:ac:11:00:02, src=02:42:ac:11:00:03, vlanId=-1, etherType=2048, payloadOffset=14]` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab: znajdź MTU ścieżki i pola ramki', ...img('ns02-01-pmtu-discovery.svg', 'Ścieżka: laptop, router domowy (MTU 1500), tunel VPN (MTU 1420), serwer. Trzy próby: ping -M do -s 1472 za duży (komunikat Frag needed, mtu 1420 lub cisza), ping -M do -s 1392 przechodzi (1392 + 28 = 1420), ping -M do -s 1393 za duży. Pod spodem odpowiedniki: ping -f -l w Windows i tracepath w Linuksie.'),
    body:
`1. **Sprawdź MTU interfejsów**: \`ip link\` (Linux/WSL), \`netsh interface ipv4 show subinterfaces\` (Windows), w kontenerze \`ip link show eth0\`.
2. **Przechwyć ramkę** (\`tcpdump -i eth0 -nn -e -c 5\`) — opcja \`-e\` pokazuje adresy MAC i EtherType. Otwórz plik w Wiresharku i odczytaj pola *Ethernet II*.
3. **Wyślij ping z zakazem fragmentacji** (DF): Linux \`ping -M do -s <dane> <cel>\`, Windows \`ping -f -l <dane> <cel>\`. Rozmiar pakietu = dane + 8 (ICMP) + 20 (IPv4) — dla MTU 1500 największe dane to **1472**.
4. **Zmniejszaj rozmiar** (wyszukiwanie binarne), aż ping przejdzie. Największy działający rozmiar + 28 = **MTU ścieżki**.
5. **Zinterpretuj odpowiedzi**: „Frag needed and DF set (mtu = 1420)” lub „Message too long” — router po drodze odesłał ICMP z właściwym MTU (dobrze). **Brak odpowiedzi** dla dużych pingów przy działających małych — ICMP jest blokowany: to przepis na *PMTUD black hole*.
6. **Potwierdź narzędziem**: \`tracepath <cel>\` wypisuje \`pmtu\` na każdym skoku.
7. **Porównaj z MSS w SYN**: \`tcpdump -nn -v 'tcp[tcpflags] & tcp-syn != 0'\` — MSS ogłaszany przez hosty (1460 przy MTU 1500).
8. **Zanotuj w dzienniku**: MTU interfejsu, MTU ścieżki, różnica i jej przyczyna (VPN? WSL? Docker?).` },

  { type: 'NOTE', heading: 'Narzut tuneli i MTU wewnątrz', ...img('ns02-01-tunnel-overhead.svg', 'Paski MTU wewnątrz tunelu przy łączu 1500: bez tunelu 1500, PPPoE 1492 (8 bajtów narzutu), GRE 1476 (24), VXLAN 1450 (50), IPsec ESP około 1427 (73, wartość typowa), WireGuard 1420 (80).'),
    body:
`Tunel dokłada własne nagłówki *wewnątrz* pakietu o MTU łącza — na dane wewnętrzne zostaje mniej. Typowe wartości przy łączu 1500:`,
    code:
`technologia                     narzut     MTU wewnątrz   gdzie spotkasz
PPPoE                           8 B        1492           łącza DSL/światłowodowe u części operatorów
GRE (IPv4)                      24 B       1476           tunele site-to-site, starsze VPN
VXLAN (IPv4, UDP 4789)          50 B       1450           Kubernetes (Flannel, Calico w trybie VXLAN), overlay DC
Geneve                          50+ B      ≤ 1450         AWS Gateway Load Balancer, OVN, Cilium
IPsec ESP (tryb tunelowy)       ok. 50–73  ok. 1427–1450  VPN site-to-site; zależnie od szyfru i NAT-T
WireGuard                       60/80 B    1420 (domyśl.) VPN; 80 B rezerwy, by działać także po IPv6
tag VLAN 802.1Q                 4 B        1500           ramka rośnie do 1522, MTU pakietu bez zmian
MACsec                          kilkadziesiąt B  zwykle 1500 (sprzęt obsługuje większe ramki)

Reguła: MTU wewnątrz = MTU łącza − nagłówki tunelu (zewnętrzny IP, UDP/GRE, nagłówek tunelu, ew. Ethernet).
MSS dla TCP w tunelu = MTU wewnątrz − 40 (IPv4) lub − 60 (IPv6).` },

  { type: 'CODE_PRACTICAL', heading: 'MTU w Linuksie, Dockerze, Kubernetesie i AWS', body:
`Gdzie i jak ustawia się MTU w środowiskach, z którymi pracuje backend developer:`,
    code:
`# Linux: odczyt i zmiana (tymczasowo)
ip link show eth0                          # ... mtu 1500 ...
sudo ip link set dev eth0 mtu 9000         # jumbo — tylko gdy cała ścieżka L2 to obsługuje
ethtool -S eth0 | grep -Ei 'crc|err|drop'  # liczniki błędów ramek

# Docker: MTU sieci (np. gdy host jest w VPN z MTU 1400)
docker network create --opt com.docker.network.driver.mtu=1400 lab-net
# lub globalnie /etc/docker/daemon.json:  { "mtu": 1400 }
# compose:
# networks:
#   lab: { driver_opts: { com.docker.network.driver.mtu: "1400" } }

# Kubernetes: MTU ustawia wtyczka CNI (Calico, Cilium, Flannel) — często automatycznie,
# ale przy nietypowym podkładzie (VPN, chmura z MTU 9001) trzeba je jawnie skonfigurować.

# AWS: w obrębie VPC MTU 9001 (jumbo) na większości typów instancji;
# ruch przez Internet Gateway, VPN i część połączeń między VPC/regionami — 1500.
# Sprawdzenie na EC2:  ip link show ens5    → mtu 9001

# MSS clamping na bramie VPN (nftables) — dopasowanie MSS do MTU trasy
nft add rule inet filter forward tcp flags syn tcp option maxseg size set rt mtu` },

  { type: 'USAGE', heading: 'Gdzie MTU i ramki mają znaczenie w praktyce', body:
`**Dla developera i SRE:**
- **Docker na laptopie z VPN** — sieć firmowa ma MTU 1400, Docker zakłada 1500: małe żądania działają, duże (pobieranie zależności Maven, obrazy, odpowiedzi API) wiszą.
- **Kubernetes z overlay VXLAN** — MTU podów musi być mniejsze o narzut tunelu; niespójne ustawienia dają losowe zawieszenia połączeń między węzłami.
- **Chmura** — jumbo frames w VPC przyspieszają transfery między instancjami (bazy, analityka), ale ruch do Internetu i przez VPN ma 1500.
- **gRPC i duże wiadomości** — same w sobie nie zależą od MTU (TCP dzieli strumień), ale problemy MTU objawiają się jako zawieszone strumienie i timeouty.

**Dla obrońcy (blue team):**
- **Liczniki błędów** (CRC, runts, giants, late collisions) na przełącznikach to nie tylko awarie kabli — nagły wzrost na porcie może oznaczać podłączenie nieautoryzowanego urządzenia lub problem z duplexem po zmianie sprzętu.
- **MACsec (802.1AE)** szyfruje ramki na łączach L2 — chroni przed podsłuchem fizycznym w szafach i między budynkami.
- **Tunelowanie jako technika ukrywania** — nietypowe enkapsulacje (GRE, VXLAN, ICMP z dużymi danymi) w sieci, gdzie ich nie powinno być, to sygnał do analizy (rozdział 28).
- **Fragmentacja i duże pakiety** bywają wykorzystywane do omijania prostych filtrów; nowoczesne IDS/IPS składają fragmenty przed analizą.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy z ramkami i MTU', body:
`- **„MTU 1500 to rozmiar ramki”** — nie: to rozmiar *pakietu IP*. Ramka z nagłówkiem i FCS ma do 1518 B.
- **Niezgodne MTU w jednym segmencie L2** — jeden host z 9000, drugi z 1500: duże ramki od pierwszego są odrzucane przez drugiego (lub przełącznik). Na łączu L2 MTU musi być spójne.
- **Jumbo „bo szybciej”** — bez obsługi na całej ścieżce (przełączniki, routery, karty, hypervisor) jumbo frames powodują trudne do znalezienia straty.
- **Blokowanie całego ICMP** — psuje PMTUD (black hole). Przepuszczaj co najmniej ICMP „fragmentation needed” (IPv4 typ 3 kod 4) i ICMPv6 „Packet Too Big”.
- **Zapomniany narzut tunelu** — VXLAN, WireGuard, IPsec i PPPoE zmniejszają dostępne MTU; ustaw MTU lub MSS clamping.
- **Wiara w Wiresharka na nadawcy** — offloading (TSO/GSO) pokazuje „ramki” po 64 kB; to artefakt przechwytywania przed kartą.
- **Duplex mismatch** — ręcznie ustawiony full duplex po jednej stronie i autonegocjacja po drugiej daje half duplex, kolizje i dramatyczny spadek wydajności.
- **„Brak FCS w przechwyceniu = błąd”** — karty zwykle usuwają FCS przed przekazaniem ramki do systemu; to normalne.` },

  { type: 'CODE_WRONG', heading: 'Źle: lab w Dockerze za VPN z domyślnym MTU', body:
`Na laptopie z firmowym VPN (MTU interfejsu tunelu 1400) budowanie obrazu i pobieranie zależności z kontenera zawiesza się, a zespół „naprawia” to restartami i dłuższymi timeoutami.`,
    code:
`# docker-compose.yml
services:
  build-agent:
    image: maven:3.9-eclipse-temurin-21
    command: mvn -B dependency:go-offline     # duże pobrania z repozytorium przez VPN
    networks: [default]                        # MTU 1500 (domyślne), a ścieżka ma 1400

# objaw: mvn wisi na pobieraniu, curl małych plików działa, ping działa
# „naprawa”: MAVEN_OPTS=-Dmaven.wagon.http.readTimeout=600000  ← maskuje problem, nie usuwa go` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: MTU dopasowane do ścieżki (i MSS clamping)', body:
`Najpierw pomiar (ping z DF, tracepath), potem jawne MTU sieci Dockera. Dla całej floty — MSS clamping na bramie VPN, który naprawia wszystkie połączenia TCP niezależnie od konfiguracji hostów.`,
    code:
`# pomiar: największy ping z DF przechodzi przy 1372 B danych → MTU ścieżki = 1372 + 28 = 1400

# docker-compose.yml
services:
  build-agent:
    image: maven:3.9-eclipse-temurin-21
    command: mvn -B dependency:go-offline
    networks: [build]
networks:
  build:
    driver_opts:
      com.docker.network.driver.mtu: "1400"   # zgodne ze ścieżką przez VPN

# po stronie bramy VPN (dla wszystkich użytkowników):
# nft add rule inet filter forward oifname "wg0" tcp flags syn tcp option maxseg size set rt mtu
# i przepuszczenie ICMP typ 3 kod 4 / ICMPv6 Packet Too Big na firewallach` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy zmieniać MTU, a kiedy zostawić 1500', body:
`- **Zostaw 1500** — sieci biurowe, Internet, większość serwerów aplikacyjnych. To najbezpieczniejsza wartość domyślna.
- **Zmniejsz MTU (lub stosuj MSS clamping)** — gdy ruch przechodzi przez tunel: VPN, VXLAN/Geneve w Kubernetesie, PPPoE, IPsec. Zmierz MTU ścieżki i ustaw je jawnie.
- **Jumbo frames (9000)** — sieci storage (iSCSI, NFS), replikacja baz i ruch east-west w DC/VPC, **pod warunkiem**, że wszystkie elementy ścieżki je obsługują; zawsze testuj pingiem z DF na pełny rozmiar.
- **Tag VLAN** — nie wymaga zmiany MTU pakietów, ale przełączniki muszą akceptować ramki 1522 B (standard).
- **MACsec** — łącza między szafami, budynkami lub do dostawców, gdy wymagane jest szyfrowanie L2; uwzględnij większe ramki w konfiguracji sprzętu.
- **Diagnoza** — przy każdym „działa dla małych, wisi dla dużych” zacznij od MTU.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Ramka Ethernet II: MAC docelowy (6) + MAC źródłowy (6) + EtherType (2) + dane (46–1500) + FCS (4); na kablu także preambuła/SFD (8) i IFG (12).
- EtherType ≥ 0x0600 to typ (0x0800 IPv4, 0x86DD IPv6, 0x0806 ARP, 0x8100 VLAN); ≤ 1500 to długość (802.3).
- Ramka: minimum 64 B (padding), maksimum 1518 B, z tagiem VLAN 1522 B.
- **MTU** = największy pakiet IP w ramce (1500); jumbo ok. 9000; tunele zmniejszają MTU wewnątrz (VXLAN 1450, WireGuard 1420).
- MTU ścieżki znajdziesz pingiem z DF: dane + 28 B (IPv4); blokowanie ICMP prowadzi do PMTUD black hole.
- W Dockerze, Kubernetesie i chmurze MTU trzeba świadomie dopasować do ścieżki (driver_opts, CNI, MSS clamping).
- Dla obrońcy: liczniki błędów ramek, MACsec i nietypowe enkapsulacje to elementy monitoringu L2.
- Następna lekcja: **adresy MAC i działanie przełącznika**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [mtu, v6] of [[1500, false], [1420, false], [9000, false], [1500, true], [1280, true]]) {
  ex(`Jaki największy rozmiar danych (-s) przejdzie w ping z flagą DF przy MTU ścieżki ${mtu} dla ${v6 ? 'IPv6' : 'IPv4'}?`, `Pakiet = dane + 8 (ICMP) + ${v6 ? '40 (IPv6)' : '20 (IPv4)'}.`, `${mtu} − 8 − ${v6 ? 40 : 20} = ${pingPayload(mtu, v6)} B (np. ping ${v6 ? '-6 ' : ''}-M do -s ${pingPayload(mtu, v6)} cel).`);
}
for (const p of [10, 46, 500, 1500]) {
  ex(`Ile bajtów ma ramka Ethernet (z FCS, bez preambuły) niosąca ${p} B danych warstwy 3? A z tagiem VLAN?`, 'Ramka = 14 + dane + 4 (min. 64); tag VLAN +4.', `Bez tagu: max(64, 14 + ${p} + 4) = ${frameLen(p)} B${p < 46 ? ' (dane dopełnione do 46 B)' : ''}. Z tagiem: max(64, 18 + ${p} + 4) = ${frameLen(p, true)} B.`);
}
for (const [n, o] of TUN.filter((t) => t[1] > 0)) {
  ex(`Policz MTU wewnątrz tunelu „${n}” przy łączu 1500 oraz MSS dla TCP/IPv4 w tym tunelu.`, `Narzut ${n}: ${o} B.`, `MTU wewnątrz = 1500 − ${o} = ${1500 - o}; MSS = ${1500 - o} − 40 = ${1460 - o} B.`);
}
const hexes = [['0x0800', 'EtherType IPv4'], ['0x86DD', 'EtherType IPv6'], ['0x0806', 'EtherType ARP'], ['0x05DC', 'długość 1500 B (ramka IEEE 802.3 z LLC, nie Ethernet II)'], ['0x8100', 'tag VLAN 802.1Q — prawdziwy EtherType jest 4 bajty dalej']];
for (const [h, d] of hexes) ex(`Bajty 12–13 ramki mają wartość ${h}. Co to oznacza i co zrobi parser?`, 'Porównaj z granicą 1500 (0x05DC) i 0x0600 (1536).', `${d}.`);
ex('Przechwyć 5 ramek z flagą -e (tcpdump -i eth0 -nn -e -c 5) i odczytaj: MAC źródłowy, MAC docelowy, EtherType i długość. Porównaj z Wiresharkiem.', 'tcpdump -e pokazuje nagłówek warstwy 2 w każdej linii.', 'Przykład linii: „02:42:ac:11:00:03 > 02:42:ac:11:00:02, ethertype IPv4 (0x0800), length 133: IP 172.17.0.3.51334 > 172.17.0.2.8080: ...”. W Wiresharku: drzewo Ethernet II z tymi samymi polami; długość 133 = ramka bez FCS.');
ex('Znajdź MTU ścieżki z Twojego komputera do wybranego serwera w Internecie (np. example.com) metodą wyszukiwania binarnego z ping DF. Porównaj z wynikiem tracepath.', 'Zacznij od 1472, potem połowienie przedziału.', 'Typowo bez VPN: 1472 przechodzi → MTU 1500. Z VPN lub PPPoE: np. 1464 → MTU 1492, albo 1392 → 1420. tracepath wypisze „pmtu 1500” (lub mniejsze) i wskaże skok, na którym spada. Brak odpowiedzi dla dużych pingów przy działających małych może oznaczać filtrowanie ICMP.');
ex('Utwórz sieć Dockera z MTU 1400, uruchom w niej dwa kontenery i sprawdź: ip link (MTU interfejsu), ping -M do -s 1372 i -s 1373 między nimi. Wyjaśnij wyniki.', 'docker network create --opt com.docker.network.driver.mtu=1400 mtu-test', 'Interfejs eth0 w kontenerach ma mtu 1400. ping -M do -s 1372 przechodzi (1372 + 28 = 1400), -s 1373 kończy się błędem „message too long” po stronie nadawcy (pakiet większy niż MTU interfejsu z DF).');
ex('W Wiresharku znajdź ramkę z tagiem VLAN (jeśli nie masz, utwórz VLAN na interfejsie: ip link add link eth0 name eth0.100 type vlan id 100) i odczytaj VLAN ID, PCP i wewnętrzny EtherType.', 'Filtr: vlan; pola vlan.id, vlan.priority, vlan.etype.', 'Przykład: 802.1Q Virtual LAN, PRI: 0, DEI: 0, ID: 100, Type: IPv4 (0x0800). Uwaga: na wielu kartach tag jest zdejmowany sprzętowo (VLAN offload), więc przechwycenie na interfejsie fizycznym może go nie pokazać — lepiej przechwytywać na interfejsie bez offloadu lub z SPAN.');
ex('Odczytaj liczniki błędów interfejsu (ethtool -S lub ip -s link) na swoim serwerze/VM. Które liczniki świadczą o problemie fizycznym, a które o przeciążeniu?', 'Błędy CRC/frame vs dropped/missed.', 'Problemy fizyczne/łącza: rx_crc_errors, frame errors, alignment, carrier errors, late collisions (duplex). Przeciążenie/bufory: rx_dropped, rx_missed_errors, tx_dropped, fifo errors. Wzrost CRC na jednym porcie → kabel/wkładka/zakłócenia; wzrost dropped przy dużym ruchu → za małe bufory lub CPU.');
ex('Rozbuduj parser Ethernet z lekcji o rozpoznawanie nazw EtherType (IPv4, IPv6, ARP, LLDP, EAPOL) i napisz test JUnit dla ramki z podwójnym tagiem QinQ.', 'Ramka QinQ: TPID 0x88A8 (zewnętrzny), 0x8100 (wewnętrzny), potem EtherType.', 'Mapa: Map.of(0x0800,"IPv4",0x86DD,"IPv6",0x0806,"ARP",0x88CC,"LLDP",0x888E,"EAPOL"). Test: zbuduj bajty dst(6) src(6) 88 A8 00 0A 81 00 00 64 08 00 + dane; oczekuj vlanId = 100 (wewnętrzny), etherType = 0x0800, payloadOffset = 22.');
ex('Zespół chce włączyć jumbo frames (MTU 9000) między serwerami aplikacji i bazą w data center. Przygotuj listę kontrolną weryfikacji przed i po zmianie.', 'Każdy element ścieżki L2 i L3 musi obsługiwać duże ramki.', 'Przed: potwierdzenie obsługi na kartach, przełącznikach (MTU portów i VLAN-ów), routerach/firewallach na ścieżce, hypervisorze; plan wycofania. Po: ping -M do -s 8972 w obie strony między wszystkimi parami; brak wzrostu błędów (giants, drops); test wydajności (iperf3) i aplikacji; monitoring retransmisji TCP przez kilka dni. Ruch do innych sieci nadal 1500 (MSS/PMTUD).');
ex('Wyjaśnij, dlaczego w AWS ping z DF na 8972 B działa między dwiema instancjami w tym samym VPC, a do serwera w Internecie nie.', 'MTU 9001 w VPC, 1500 przez Internet Gateway.', 'W obrębie VPC (ten sam region, obsługiwane typy instancji) MTU wynosi 9001, więc pakiet 9000 B przechodzi. Ruch przez Internet Gateway (i VPN) jest ograniczony do 1500 — duży pakiet z DF jest odrzucany, a instancja powinna dostać ICMP „fragmentation needed” (o ile Security Group/NACL go przepuszcza) i dopasować PMTU.');
ex('Zablokuj w labie (nftables na „routerze” między dwoma namespace’ami/kontenerami) ICMP typ 3 i obniż MTU łącza do 1400. Pokaż, że duże pobranie curl wisi, a potem napraw to MSS clampingiem.', 'nft add rule inet filter forward icmp type destination-unreachable drop; ip link set dev <if> mtu 1400.', 'Po blokadzie: curl małego pliku działa, duży wisi (pakiety 1500 z DF giną, nadawca nie dostaje informacji o MTU). Po dodaniu reguły tcp option maxseg size set rt mtu w łańcuchu forward hosty negocjują MSS ≤ 1360 i transfer działa mimo blokady ICMP. Wniosek: MSS clamping maskuje PMTUD black hole dla TCP (nie dla UDP/QUIC).');
ex('Wyjaśnij, dlaczego QUIC/HTTP3 jest szczególnie wrażliwy na problemy MTU i dlaczego MSS clamping mu nie pomaga.', 'QUIC działa nad UDP; MSS to opcja TCP.', 'QUIC używa UDP, więc nie ma negocjacji MSS w SYN — MSS clamping na bramie nie działa. QUIC sam wykonuje odkrywanie MTU (DPLPMTUD) i zaczyna od bezpiecznych rozmiarów (≥ 1200 B), ale w sieciach z małym MTU lub blokadą dużych datagramów połączenia mogą zawodzić — przeglądarki wracają wtedy do HTTP/2 po TCP.');
ex('Zinterpretuj fragment ip -s link: „RX: bytes 9123456 packets 81234 errors 1532 dropped 0 overrun 0 mcast 120”. Co sprawdzisz dalej?', 'Błędy odbioru przy zerowych dropach wskazują na warstwę fizyczną.', 'errors 1532 przy dropped 0 sugeruje błędy ramek (CRC, frame) — problem fizyczny: kabel, złącze, wkładka, zakłócenia lub duplex mismatch. Dalej: ethtool -S eth0 (szczegółowe liczniki: rx_crc_errors, rx_frame_errors), ethtool eth0 (prędkość i duplex), wymiana kabla/portu, obserwacja, czy licznik nadal rośnie.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111406);
for (const [mtu, v6] of [[1500, false], [1492, false], [1450, false], [1420, false], [9000, false], [9001, false], [1500, true], [1420, true], [1280, true], [1400, false]]) {
  const v = pingPayload(mtu, v6);
  add(`Jaki największy rozmiar danych ping (z DF) przejdzie przy MTU ${mtu} dla ${v6 ? 'IPv6' : 'IPv4'}?`, String(v), [String(mtu - 20), String(mtu), String(v6 ? mtu - 28 : mtu - 48), String(v - 8)], `${mtu} − 8 (ICMP) − ${v6 ? 40 : 20} (nagłówek IP) = ${v}.`);
}
for (const p of [1, 20, 46, 100, 1000, 1500]) {
  const f = frameLen(p);
  add(`Ile bajtów ma ramka Ethernet II (z FCS) z ${p} B danych warstwy 3, bez tagu VLAN?`, String(f), [String(14 + p + 4 === f ? f + 4 : 14 + p + 4), String(f - 4), String(f + 8), String(p + 14)], `max(64, 14 + ${p} + 4) = ${f}.`);
}
for (const [n, o] of TUN.filter((t) => t[1] > 0)) add(`Jakie MTU zostaje wewnątrz tunelu „${n}” przy łączu 1500?`, String(1500 - o), [String(1500), String(1500 - o - 20), String(1500 + o), String(1460 - o)], `1500 − ${o} = ${1500 - o}.`);
const et = [['0x0800', 'IPv4'], ['0x86DD', 'IPv6'], ['0x0806', 'ARP'], ['0x8100', 'tag VLAN 802.1Q'], ['0x88CC', 'LLDP'], ['0x888E', 'EAPOL (802.1X)'], ['0x88A8', 'zewnętrzny tag QinQ (802.1ad)']];
const etAll = et.map((x) => x[1]);
for (const [h, n] of et) add(`Co oznacza EtherType ${h}?`, n, etAll.filter((x) => x !== n).slice(0, 3), `${h} = ${n}.`);
for (const [v, isType] of [['0x0600', true], ['0x05DC', false], ['0x0040', false], ['0x0800', true], ['0x002E', false]]) {
  const n = parseInt(v, 16);
  add(`Pole po adresach MAC ma wartość ${v} (${n}). Jak je interpretować?`, isType ? 'Jako EtherType (Ethernet II)' : 'Jako długość danych (IEEE 802.3 z LLC)', [isType ? 'Jako długość danych (IEEE 802.3 z LLC)' : 'Jako EtherType (Ethernet II)', 'Jako numer VLAN', 'Jako suma kontrolna'], `Wartości ≥ 0x0600 (1536) to EtherType, ≤ 1500 (0x05DC) — długość.`);
}
const sizes = [['Ile bajtów ma adres MAC?', '6'], ['Ile bajtów ma pole EtherType?', '2'], ['Ile bajtów ma pole FCS?', '4'], ['Ile bajtów dokłada tag 802.1Q?', '4'], ['Ile bajtów mają razem preambuła i SFD?', '8'], ['Ile bajtów ma przerwa międzyramkowa (IFG)?', '12'], ['Jaka jest minimalna długość ramki Ethernet z FCS?', '64'], ['Jaka jest maksymalna długość zwykłej ramki Ethernet z FCS?', '1518'], ['Jaka jest maksymalna długość ramki z jednym tagiem VLAN?', '1522'], ['Ile bitów ma pole VLAN ID?', '12']];
const sizeAll = ['2', '4', '6', '8', '12', '14', '46', '64', '1500', '1518', '1522', '16'];
for (const [q, a] of sizes) add(q, a, sizeAll.filter((x) => x !== a).slice((q.length) % 8, (q.length) % 8 + 3).concat(sizeAll.filter((x) => x !== a)).filter((x, i, arr) => arr.indexOf(x) === i).slice(0, 3), `Odpowiedź: ${a}.`);
const errs = [['Wiele błędów CRC na jednym porcie przełącznika', 'Uszkodzony kabel, złącze lub wkładka; zakłócenia'], ['Late collisions i spadek wydajności po wymianie karty', 'Niezgodność duplexu (duplex mismatch)'], ['Liczne ramki giant na porcie', 'Niezgodne MTU — urządzenie wysyła ramki większe niż akceptowane'], ['Wysoki licznik rx_dropped przy dużym ruchu', 'Przeciążenie: za małe bufory lub za mało CPU na odbiór'], ['Ping działa, duże pobrania przez VPN wiszą', 'PMTUD black hole — za duże pakiety giną, ICMP zablokowane']];
const errAll = errs.map((x) => x[1]);
for (const [s, c] of errs) add(`Objaw: ${s}. Najbardziej prawdopodobna przyczyna?`, c, errAll.filter((x) => x !== c).slice(0, 3), `Przyczyna: ${c.toLowerCase()}.`);
const facts = [
  ['Co oznacza MTU 1500 w Ethernecie?', 'Maksymalny rozmiar pakietu IP przenoszonego w ramce', ['Maksymalny rozmiar ramki z nagłówkiem', 'Przepustowość 1500 Mb/s', 'Maksymalną liczbę ramek na sekundę'], 'Ramka z nagłówkiem i FCS ma do 1518 B.'],
  ['Co robi karta sieciowa z danymi krótszymi niż 46 bajtów?', 'Dopełnia je zerami (padding) do minimalnej ramki 64 B', ['Odrzuca ramkę', 'Łączy z następną ramką', 'Wysyła bez zmian jako runt'], 'Minimalna długość ramki wynika z historycznego wykrywania kolizji.'],
  ['Co dzieje się z ramką o błędnej sumie FCS?', 'Jest odrzucana po cichu przez odbiorcę', ['Jest naprawiana', 'Jest odsyłana do nadawcy', 'Jest przekazywana z ostrzeżeniem'], 'Ponowienie zapewnia TCP, nie Ethernet.'],
  ['Dlaczego w przechwyceniu zwykle nie widać FCS?', 'Karta sieciowa usuwa FCS przed przekazaniem ramki do systemu', ['Bo FCS nie istnieje w Ethernecie II', 'Bo Wireshark go ukrywa ze względów bezpieczeństwa', 'Bo FCS jest szyfrowany'], 'Niektóre karty można skonfigurować, by przekazywały FCS.'],
  ['Kiedy jumbo frames są dobrym pomysłem?', 'Gdy wszystkie urządzenia na ścieżce je obsługują, np. sieć storage lub ruch w obrębie VPC', ['Zawsze w Internecie', 'W sieci Wi-Fi gości', 'Gdy ruch idzie przez VPN do klienta'], 'Niezgodne MTU na ścieżce powoduje straty.'],
  ['Jakie MTU ma ruch w obrębie VPC AWS na większości typów instancji?', '9001', ['1500', '576', '65535'], 'Przez Internet Gateway i VPN obowiązuje 1500.'],
  ['Jakie domyślne MTU ustawia WireGuard?', '1420', ['1500', '1280', '9000'], '80 B rezerwy na nagłówki (z IPv6).'],
  ['Ile bajtów narzutu dokłada VXLAN?', '50', ['8', '24', '4'], 'Zewnętrzny Ethernet 14 + IP 20 + UDP 8 + VXLAN 8.'],
  ['Jak w Linuksie wysłać ping z zakazem fragmentacji i 1472 B danych?', 'ping -M do -s 1472 cel', ['ping -f -l 1472 cel', 'ping -s 1472 -DF cel', 'ping --mtu 1500 cel'], 'Windows: ping -f -l 1472 cel.'],
  ['Jak w Windows wysłać ping z flagą DF i 1472 B danych?', 'ping -f -l 1472 cel', ['ping -M do -s 1472 cel', 'ping -n 1472 cel', 'ping -t 1472 cel'], '-f ustawia DF, -l rozmiar danych.'],
  ['Które narzędzie Linuksa wypisuje MTU ścieżki na każdym skoku?', 'tracepath', ['arp', 'ss', 'dig'], 'Pokazuje pmtu dla kolejnych skoków.'],
  ['Jak ustawić MTU sieci Dockera?', 'Opcją com.docker.network.driver.mtu przy tworzeniu sieci (lub mtu w daemon.json)', ['Zmienną JAVA_OPTS', 'W pliku Dockerfile poleceniem MTU', 'Nie da się'], 'W compose: driver_opts sieci.'],
  ['Do czego służy MSS clamping na bramie VPN?', 'Do obniżenia MSS w segmentach SYN tak, by pakiety TCP mieściły się w MTU tunelu', ['Do szyfrowania ruchu', 'Do blokowania ICMP', 'Do zwiększenia MTU łącza'], 'Naprawia TCP niezależnie od konfiguracji hostów.'],
  ['Które komunikaty ICMP trzeba przepuszczać, by działało PMTUD?', 'ICMP Fragmentation Needed (IPv4 typ 3 kod 4) i ICMPv6 Packet Too Big', ['Tylko Echo Request', 'ICMP Redirect', 'Żadnych'], 'Ich blokada powoduje PMTUD black hole.'],
  ['Czym jest MACsec?', 'Standardem 802.1AE szyfrowania i ochrony integralności ramek na łączu L2', ['Adresem MAC w trybie bezpiecznym', 'Protokołem routingu', 'Odmianą Wi-Fi'], 'Chroni łącza między przełącznikami i budynkami.'],
  ['Co oznacza ramka runt?', 'Ramkę krótszą niż minimalne 64 B', ['Ramkę z tagiem VLAN', 'Ramkę większą niż 1518 B', 'Ramkę rozgłoszeniową'], 'Często skutek kolizji lub usterek.'],
  ['Gdzie w ramce 802.1Q znajduje się VLAN ID?', 'W 12 młodszych bitach pola TCI, po TPID 0x8100', ['W adresie MAC', 'W polu FCS', 'W nagłówku IP'], 'TCI: PCP 3 bity, DEI 1 bit, VID 12 bitów.'],
  ['Dlaczego tag VLAN nie zmniejsza MTU pakietów IP?', 'Bo standard dopuszcza ramki 1522 B — rośnie ramka, a nie maleje miejsce na dane', ['Bo tag jest w nagłówku IP', 'Bo VLAN wyłącza FCS', 'Bo tag ma 0 bajtów'], 'Przełączniki obsługują ramki z tagiem.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const p of [1, 30, 42, 100, 1400, 1500]) {
  const f = frameLen(p, true);
  add(`Ile bajtów ma ramka z tagiem VLAN (z FCS) niosąca ${p} B danych warstwy 3?`, String(f), [String(frameLen(p)), String(f + 4), String(f - 8), String(p + 22 === f ? p + 18 : p + 22)], `max(64, 18 + ${p} + 4) = ${f}.`);
}
for (const p of [46, 100, 500, 1000, 1500, 9000]) {
  const f = Math.max(64, 18 + p) + 20;
  add(`Ile bajtów zajmuje na kablu (z preambułą/SFD 8 B i IFG 12 B) ramka bez tagu z ${p} B danych?`, String(f), [String(f - 20), String(f + 4), String(f - 8)], `Ramka max(64, 14 + ${p} + 4) = ${Math.max(64, 18 + p)} B + 20 B = ${f} B.`);
}
for (const [n, o] of TUN.filter((t) => t[1] > 0)) add(`Jaki MSS (TCP/IPv4) zastosujesz w tunelu „${n}” przy łączu 1500?`, String(1460 - o), [String(1460), String(1500 - o), String(1440 - o), String(1420 - o)], `MTU wewnątrz ${1500 - o} − 40 = ${1460 - o}.`);
const lines = [
  ['02:42:ac:11:00:03 > ff:ff:ff:ff:ff:ff, ethertype ARP (0x0806), length 42', 'Rozgłoszeniowe zapytanie ARP od hosta 02:42:ac:11:00:03'],
  ['02:42:ac:11:00:02 > 02:42:ac:11:00:03, ethertype IPv4 (0x0800), length 66', 'Ramka unicast z pakietem IPv4 między dwoma kontenerami'],
  ['00:1b:21:aa:bb:cc > 33:33:00:00:00:01, ethertype IPv6 (0x86dd), length 86', 'Ramka multicast IPv6 (adres MAC 33:33:…) z pakietem IPv6'],
  ['00:1b:21:aa:bb:cc > 01:80:c2:00:00:0e, ethertype LLDP (0x88cc), length 60', 'Ogłoszenie LLDP do adresu multicast zarezerwowanego dla protokołów L2'],
  ['00:1b:21:aa:bb:cc > 00:50:56:01:02:03, ethertype 802.1Q (0x8100), vlan 30, p 0, ethertype IPv4', 'Ramka z tagiem VLAN 30 niosąca IPv4'],
];
const lAll = lines.map((x) => x[1]);
for (const [l, d] of lines) add(`Co opisuje linia tcpdump -e: „${l}”?`, d, lAll.filter((x) => x !== d).slice(0, 3), d + '.');
const std = [['1000BASE-T', 'Gigabit Ethernet po skrętce miedzianej'], ['10GBASE-T', '10 Gb/s po skrętce miedzianej (Cat6a)'], ['100BASE-TX', 'Fast Ethernet 100 Mb/s po skrętce'], ['10GBASE-SR', '10 Gb/s po światłowodzie wielomodowym (krótki zasięg)'], ['10GBASE-LR', '10 Gb/s po światłowodzie jednomodowym (długi zasięg)'], ['25GBASE-SR', '25 Gb/s po światłowodzie wielomodowym (serwery w DC)']];
const stdAll = std.map((x) => x[1]);
for (const [k, d] of std) add(`Co oznacza standard ${k}?`, d, stdAll.filter((x) => x !== d).slice(0, 3), `${k}: ${d}.`);
add('Dlaczego współczesne sieci przełączane nie potrzebują CSMA/CD?', 'Bo łącza przełącznik–host działają w full duplex bez współdzielonego medium i kolizji', ['Bo CSMA/CD jest szyfrowane', 'Bo przełączniki używają Wi-Fi', 'Bo ramki są krótsze'], 'CSMA/CD dotyczył huba i half duplex.');
add('Co oznacza duplex mismatch?', 'Jedna strona łącza pracuje w full, druga w half duplex, co powoduje kolizje i błędy', ['Dwa różne VLAN-y na porcie', 'Dwie różne prędkości Wi-Fi', 'Różne adresy MAC na końcach'], 'Najczęściej przez ręczne ustawienia po jednej stronie.');
add('Co oznacza adres MAC ff:ff:ff:ff:ff:ff?', 'Adres rozgłoszeniowy — ramka trafia do wszystkich w segmencie L2', ['Adres bramy domyślnej', 'Adres nieprawidłowy', 'Adres multicast IPv6'], 'Używany m.in. przez zapytania ARP i DHCP Discover.');
for (const p of [0, 10, 40]) add(`Ile bajtów dopełnienia (padding) doda karta do ramki z ${p} B danych?`, String(Math.max(0, 46 - p)), [String(64 - p), String(46), String(Math.max(0, 46 - p) + 4)].filter((x, i, a) => a.indexOf(x) === i && x !== String(Math.max(0, 46 - p))).concat(['18']).slice(0, 3), `Dane muszą mieć co najmniej 46 B: 46 − ${p} = ${Math.max(0, 46 - p)} B dopełnienia.`);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
