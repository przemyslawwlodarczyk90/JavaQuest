// Generuje lekcje _ns_03_ip_i_routing/07_RoutingTablesAndDefaultGateway.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_07_routing_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/07_RoutingTablesAndDefaultGateway.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { lookup, fmtRoute, LAPTOP, VPN_CONFLICT, ROUTER } = require('./ns03_07_routing_model.cjs');
const buildQuiz = require('./ns03_07_routing_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const show = (table, ip) => { const r = lookup(table, ip); return `${ip.padEnd(15)} → ${fmtRoute(r.route).padEnd(48)} (${r.reason})`; };

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Każdy host jest małym routerem', ...img('ns03-07-laptop.svg', 'Laptop z interfejsami wlan0 (192.168.1.20, trasa domyślna przez 192.168.1.1), docker0 i bridge compose (172.17.0.0/16, 172.18.0.0/16) oraz tun0 VPN (10.0.0.0/8 i 172.16.0.0/12 przez 10.8.0.1). Adres 172.20.5.5 idzie do VPN, 172.17.0.2 do Dockera, 8.8.8.8 trasą domyślną, a serwer firmy 172.17.5.5 trafia błędnie do docker0.'),
    body:
`Przy każdym wysyłanym pakiecie system operacyjny podejmuje decyzję: **którym interfejsem i przez którą bramę** go wysłać. Robi to na podstawie **tablicy routingu** — listy tras „sieć docelowa → następny skok (brama) / interfejs, metryka”. Router w serwerowni robi dokładnie to samo, tylko na większą skalę.

Laptop developera ma dziś zaskakująco skomplikowaną tablicę: Wi-Fi z trasą domyślną, mosty Dockera (\`docker0\`, sieci compose), czasem WSL2 i Kubernetes (kind, minikube), a do tego **VPN firmowy**, który dopisuje własne trasy. Gdy te trasy się nakładają, pojawiają się „magiczne” błędy: *po włączeniu VPN aplikacja nie widzi bazy w Dockerze*, *z VPN nie działa mi Internet*, *działa curl do serwera A, ale nie do B w tej samej sieci*.

W tej lekcji:
- jak czytać tablicę routingu w Linuksie (\`ip route\`) i Windows (\`route print\`, \`Get-NetRoute\`),
- **longest prefix match** — reguła, która rozstrzyga prawie wszystko,
- trasa domyślna (default gateway), metryki, ECMP i — w zarysie — **policy routing** (\`ip rule\`),
- \`ip route get\` jako najważniejsze polecenie diagnostyczne,
- VPN **split tunnel** vs pełny tunel i konflikty tras,
- **Debug Lab** z planu: VPN „zjada” trasę do sieci Dockera — znajdź konflikt i napraw.

Cele: Network+ N10-009 (domena 1–2: routing, tablice, brama domyślna; domena 5: problemy z routingiem), CCNA 200-301 v2 (interpretacja tablicy routingu, longest match, dystans administracyjny i metryka — rozwinięcie w następnej lekcji).` },

  { type: 'DEFINITION', heading: 'Słownik tablicy routingu', body:
`- **Trasa (route)** — wpis „prefiks docelowy → następny skok”. Np. \`10.0.0.0/8 via 10.8.0.1 dev tun0 metric 50\`.
- **Następny skok (next hop, \`via\`)** — adres bramy w sieci bezpośrednio podłączonej, do której wysyłamy pakiet (ARP o ten adres!). Brak \`via\` = sieć podłączona bezpośrednio (connected) — ARP o sam cel.
- **Interfejs wyjściowy (\`dev\`)** — przez który interfejs wyjdzie pakiet.
- **Trasa domyślna (default, 0.0.0.0/0)** — pasuje do każdego adresu; używana, gdy nic bardziej szczegółowego nie pasuje. Jej brama to **default gateway**.
- **Longest prefix match (LPM)** — spośród pasujących tras wybierana jest ta z **najdłuższym prefiksem** (najbardziej szczegółowa), niezależnie od kolejności wpisów i metryk.
- **Metryka** — „koszt” trasy; rozstrzyga **tylko** między trasami o tym samym prefiksie (niższa wygrywa). W Windows liczy się suma metryki trasy i metryki interfejsu.
- **Dystans administracyjny (AD)** — w routerach: wiarygodność źródła trasy (connected 0, static 1, OSPF 110…); omówiony w lekcji 08.
- **ECMP (Equal-Cost Multi-Path)** — kilka tras równych we wszystkim; ruch dzielony po przepływach.
- **proto** w Linuksie — skąd trasa: \`kernel\` (z adresu interfejsu), \`dhcp\`, \`static\`, \`boot\`, \`bird\`/\`zebra\` (demony routingu).
- **scope link** — trasa do sieci na łączu (bez bramy).
- **Policy-based routing (PBR)** — wybór tablicy routingu na podstawie reguł (\`ip rule\`): źródłowego adresu, znacznika (fwmark), interfejsu wejściowego. Używają go WireGuard, Tailscale, Kubernetes i multi-homing.
- **Split tunnel** — VPN przejmuje tylko trasy do sieci firmy; reszta idzie lokalnie. **Full tunnel** — VPN przejmuje cały ruch (często trikiem \`0.0.0.0/1\` + \`128.0.0.0/1\`, które jako /1 są dłuższe niż /0 i „przykrywają” trasę domyślną bez jej usuwania).
- **Trasa powrotna** — routing jest jednokierunkowy: odpowiedź też musi mieć trasę do nadawcy.` },

  { type: 'ANALOGY', heading: 'Drogowskazy na skrzyżowaniu', body:
`Tablica routingu to zestaw drogowskazów na skrzyżowaniu:
- „**Warszawa, ul. Marszałkowska 10–20** → w lewo” (/25), „**Warszawa** → prosto” (/16), „**wszystkie kierunki** → w prawo na autostradę” (default /0).
- Kierowca jadący na Marszałkowską 15 widzi wszystkie trzy pasujące tablice — i wybiera **najbardziej szczegółową** (longest prefix match). Nie ma znaczenia, która tablica wisi wyżej ani która droga jest krótsza.
- **Metryka** przydaje się dopiero, gdy są dwie tablice „Warszawa, Marszałkowska 10–20” wskazujące różne drogi — wtedy wybierasz tę z mniejszym „kosztem” (np. bez korków).
- **Trasa domyślna** to „wszystkie inne kierunki” — bez niej kierowca jadący gdzieś, czego nie ma na tablicach, staje bezradnie („Network is unreachable”).
- **VPN** to firma, która przyjeżdża i przykleja na skrzyżowaniu własne tablice „Wszystko, co firmowe → do naszego tunelu”. Jeśli przyklei zbyt szeroką tablicę („172.18.0.0–127.255 → tunel”), kierowcy jadący do Twojego garażu (sieci Dockera pod tym samym adresem) wylądują w firmie.
- **Policy routing** to osobne zestawy drogowskazów dla różnych pojazdów: „ciężarówki (ruch ze znacznikiem) jadą według innej mapy”.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Longest prefix match na przykładzie routera', ...img('ns03-07-lpm.svg', 'Tablica routera z trasami default, 10.0.0.0/8, 10.20.0.0/16, 10.20.30.0/24, 10.20.30.128/25 i trasami do 192.168. Dla adresu 10.20.30.200 pasuje pięć tras, wygrywa najdłuższy prefiks /25 przez 10.255.0.14. Metryka rozstrzyga tylko przy równej długości prefiksu.'),
    body:
`Tablica routera (jak z \`ip route\`) i decyzje dla kilku adresów, wyliczone modelem z lekcji. Zwróć uwagę: 10.20.30.5 i 10.20.30.200 leżą w tej samej /24, ale idą różnymi drogami, bo druga połowa ma bardziej szczegółową trasę /25.`,
    code:
`${ROUTER.map(fmtRoute).join('\n')}

${['10.20.30.200', '10.20.30.5', '10.20.99.1', '10.99.1.1', '192.168.50.7', '192.168.7.7', '1.2.3.4'].map((ip) => show(ROUTER, ip)).join('\n')}` },

  { type: 'CODE_BASIC', heading: 'Czytanie tablicy routingu: Linux i Windows', body:
`Te same informacje w dwóch systemach. Najważniejsze polecenie: **\`ip route get <cel>\`** (Linux) / **\`Find-NetRoute -RemoteIPAddress <cel>\`** (Windows) — pokazuje decyzję, którą system faktycznie podejmie, łącznie z adresem źródłowym.`,
    code:
`# Linux
$ ip route
default via 192.168.1.1 dev wlan0 proto dhcp src 192.168.1.20 metric 600
10.0.0.0/8 via 10.8.0.1 dev tun0 proto static metric 50
172.17.0.0/16 dev docker0 proto kernel scope link src 172.17.0.1 linkdown
192.168.1.0/24 dev wlan0 proto kernel scope link src 192.168.1.20 metric 600

$ ip route get 10.20.1.5
10.20.1.5 via 10.8.0.1 dev tun0 src 10.8.0.5 uid 1000      ← decyzja jądra + adres źródłowy
$ ip -6 route ; ip rule ; ip route show table all | head    ← IPv6, reguły i wszystkie tabele

# Windows (PowerShell)
PS> Get-NetRoute -AddressFamily IPv4 | Sort-Object RouteMetric | Format-Table DestinationPrefix,NextHop,InterfaceAlias,RouteMetric,InterfaceMetric
PS> Find-NetRoute -RemoteIPAddress 10.20.1.5 | Select-Object -Last 1     # wybrana trasa
PS> route print -4                                                         # klasyczny widok
# Windows liczy koszt = RouteMetric + InterfaceMetric (automatyczna metryka interfejsu zależy od prędkości łącza)

# zmiany (tymczasowe, do restartu)
sudo ip route add 10.50.0.0/16 via 192.168.1.254
sudo ip route replace default via 192.168.1.1 metric 100
PS> New-NetRoute -DestinationPrefix 10.50.0.0/16 -NextHop 192.168.1.254 -InterfaceAlias Wi-Fi` },

  { type: 'STEP_BY_STEP', heading: 'Debug Lab z planu: VPN „zjada” trasę do sieci Dockera', ...img('ns03-07-vpn-conflict.svg', 'Konflikt tras: sieć docker compose 172.18.0.0/16 i trasa VPN 172.18.0.0/17. Kontener 172.18.0.5 trafia do tunelu VPN, bo /17 jest dłuższy, a kontener 172.18.200.5 nadal do mostu Dockera. Dowód: ip route get; naprawa: przeniesienie sieci Dockera poza zakresy firmy.'),
    body:
`**Odtworzenie (Linux, root, bez prawdziwego VPN):** interfejs \`dummy\` udaje tunel.
1. \`docker network create --subnet 172.18.0.0/16 lab-net\` i \`docker run -d --name db --network lab-net --ip 172.18.0.5 postgres:16\` (hasło przez \`-e POSTGRES_PASSWORD=...\`).
2. Sprawdź: \`ip route get 172.18.0.5\` → \`dev br-xxxx\` (most Dockera), \`nc -vz 172.18.0.5 5432\` → OK.
3. „Podłącz VPN”: \`ip link add tun-lab type dummy && ip addr add 10.8.0.5/24 dev tun-lab && ip link set tun-lab up\` oraz trasa, którą typowo wypycha serwer VPN: \`ip route add 172.18.0.0/17 via 10.8.0.1 dev tun-lab metric 50\`.
4. **Objaw:** \`nc -vz 172.18.0.5 5432\` — timeout; aplikacja: \`Connection timed out\` do bazy. Kontener z adresem 172.18.200.x działa dalej — „dziwne, połowa działa”.
5. **Dowód:** \`ip route get 172.18.0.5\` → \`via 10.8.0.1 dev tun-lab\`. Tablica: dwie pasujące trasy — \`172.18.0.0/16 dev br-…\` i \`172.18.0.0/17 via 10.8.0.1\`; wygrywa **/17** (dłuższy prefiks). Model z lekcji:
${['172.18.0.5', '172.18.200.5', '8.8.8.8'].map((ip) => `   - ${show(VPN_CONFLICT, ip)}`).join('\n')}
6. **Naprawa właściwa:** przenieś sieci Dockera poza zakresy firmowe — w \`/etc/docker/daemon.json\`: \`{"bip": "10.200.0.1/24", "default-address-pools": [{"base": "10.201.0.0/16", "size": 24}]}\` (zakres uzgodniony z zespołem sieci!) i odtwórz sieci; w compose — \`networks: lab: ipam: config: - subnet: 10.201.5.0/24\`.
7. **Obejście doraźne:** dłuższa lub równa trasa z niższą metryką do mostu (\`ip route add 172.18.0.0/17 dev br-xxxx metric 0\`) — ale wtedy firmowe serwery z 172.18.0.0/17 są niedostępne. Konflikt adresów nie ma rozwiązania „routingiem” — trzeba zmienić jeden z zakresów.
8. **Sprzątanie:** \`ip link del tun-lab\`, \`docker rm -f db\`, \`docker network rm lab-net\`.
9. **Notatka:** objaw (timeout do części kontenerów po VPN) → dowód (\`ip route get\`) → przyczyna (prefiks VPN dłuższy niż sieci Dockera) → zmiana (pule adresów Dockera) → test (\`nc -vz\`, \`ip route get\`).` },

  { type: 'NOTE', heading: 'Split tunnel, full tunnel i pułapki VPN', ...img('ns03-07-decision.svg', 'Kolejność decyzji o trasie: najpierw reguły policy routing wybierają tabelę, potem najdłuższy pasujący prefiks, przy równym prefiksie najniższa metryka, przy pełnej równości ECMP, na końcu wybór następnego skoku i ARP. Polecenia Linux: ip route get, ip rule; Windows: route print, Get-NetRoute, Find-NetRoute.'),
    body:
`- **Split tunnel**: VPN dodaje trasy tylko do sieci firmy (np. \`10.0.0.0/8\`, \`172.16.0.0/12\`). Internet idzie lokalnie — szybciej, ale ruch z laptopa do Internetu omija firmowe filtry (zastanów się nad tym jako obrońca).
- **Full tunnel**: cały ruch przez VPN. Klienci OpenVPN/WireGuard zwykle **nie usuwają** trasy domyślnej, tylko dodają \`0.0.0.0/1\` i \`128.0.0.0/1\` (dwie trasy /1 pokrywają całą przestrzeń i są dłuższe niż /0) oraz trasę /32 do samego serwera VPN przez starą bramę (inaczej pakiety tunelu wpadłyby do tunelu).
- **WireGuard/Tailscale** często używają **policy routing**: osobna tabela z trasą domyślną i reguła „wszystko bez znacznika fwmark → tabela VPN”. \`ip route\` (tabela main) wygląda wtedy „normalnie”, a ruch i tak idzie tunelem — dlatego zawsze \`ip route get\` i \`ip rule\`.
- **Konflikty**: VPN z trasą szerszą niż Twoja sieć lokalna (np. firma używa 192.168.1.0/24, a Ty masz w domu to samo) — wtedy trasa connected /24 i trasa VPN /24 mają równe prefiksy; decyduje metryka, a przegrana sieć „znika”.
- **Windows**: VPN-y ustawiają niską metrykę interfejsu, by wygrać przy równych prefiksach; „Use default gateway on remote network” = full tunnel.
- **DNS** to osobna sprawa: split tunnel z DNS-em firmowym dla wszystkich zapytań albo split DNS — błędna konfiguracja daje „po nazwie nie działa, po IP działa” (rozdział 05).` },

  { type: 'CODE_PRACTICAL', heading: 'Java: który interfejs i adres źródłowy wybierze system?', body:
`Java nie ma własnego routingu — korzysta z systemowego. Przydatny trik diagnostyczny: \`DatagramSocket.connect()\` nie wysyła żadnego pakietu, ale zmusza system do wybrania trasy, więc \`getLocalAddress()\` zwraca **adres źródłowy wybrany przez routing** dla danego celu. Do tego lista interfejsów i adresów. Możesz to wbudować w endpoint diagnostyczny (chroniony!) lub w log startowy aplikacji.`,
    code:
`import java.net.*;
import java.util.Collections;

public class RouteProbe {
    // adres źródłowy, którego system użyje do połączenia z celem (bez wysyłania pakietów)
    static InetAddress sourceFor(String target) throws Exception {
        try (DatagramSocket s = new DatagramSocket()) {
            s.connect(InetAddress.getByName(target), 9);      // UDP connect = tylko wybór trasy
            return s.getLocalAddress();
        }
    }

    public static void main(String[] args) throws Exception {
        for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            if (!ni.isUp()) continue;
            for (InterfaceAddress a : ni.getInterfaceAddresses())
                System.out.printf("%-12s %s/%d%n", ni.getName(), a.getAddress().getHostAddress(), a.getNetworkPrefixLength());
        }
        for (String t : args.length > 0 ? args : new String[]{"8.8.8.8", "127.0.0.1"}) {
            InetAddress src = sourceFor(t);
            NetworkInterface ni = NetworkInterface.getByInetAddress(src);
            System.out.printf("cel %-15s → źródło %-15s interfejs %s%n", t, src.getHostAddress(), ni == null ? "?" : ni.getName());
        }
    }
}
// Wynik na laptopie z VPN: cel 10.20.1.5 → źródło 10.8.0.5 interfejs tun0 — szybki dowód, że ruch idzie tunelem.
// Wymuszenie źródła: new Socket().bind(new InetSocketAddress(localIp, 0)) przed connect (policy routing po źródle).` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **„Działa bez VPN, nie działa z VPN”** (i odwrotnie) — prawie zawsze konflikt tras lub DNS; pierwsze polecenie: \`ip route get <adres>\`.
- **Docker, WSL2, kind/minikube** tworzą mosty i trasy; ich zakresy muszą omijać zakresy firmowe i VPN (lekcje 04 i 06).
- **Kubernetes**: każdy węzeł ma trasy do podsieci podów innych węzłów (Calico: trasy /26 per węzeł, często przez BGP), kube-proxy/Cilium używają policy routing i znaczników.
- **Chmura**: tablice routingu VPC (route tables) działają tak samo — LPM, \`local\` dla CIDR VPC, \`0.0.0.0/0\` do Internet Gateway (podsieć publiczna) lub NAT Gateway (prywatna), trasy do Transit Gateway, peeringu, VPN. Podsieć „publiczna” to po prostu podsieć z trasą domyślną do IGW.
- **Serwery z dwoma interfejsami** (aplikacja + zarządzanie / backup): bez policy routing odpowiedzi wychodzą trasą domyślną innym interfejsem niż przyszło żądanie → asymetria, rp_filter, „połączenie wisi”.

**Blue team:**
- **Zmiany tablicy routingu** na stacji roboczej lub serwerze (nowa trasa domyślna, trasy do 0.0.0.0/1) to możliwy wskaźnik złośliwego oprogramowania lub nieautoryzowanego VPN — telemetria EDR/Sysmon (zmiany tras), auditd.
- **Split tunnel** oznacza, że ruch do Internetu z firmowego laptopa nie przechodzi przez firmowy proxy/IDS — rozważ ZTNA, SWG na urządzeniu lub full tunnel dla wrażliwych ról.
- **Route leaks i hijacking** w Internecie (BGP) to ten sam mechanizm LPM na wielką skalę: ktoś ogłasza bardziej szczegółowy prefiks i przejmuje ruch — RPKI w lekcji o BGP.
- Mapowanie: Network+ (routing, troubleshooting), CCNA (tablica routingu), Security+ (VPN, segmentacja), CySA+ (analiza anomalii sieciowych).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Myślenie, że metryka wygrywa z prefiksem** — trasa /24 z metryką 1000 zawsze wygra z /16 o metryce 1.
- **Czytanie \`ip route\` zamiast pytania \`ip route get\`** — przy policy routing tabela main nie mówi całej prawdy.
- **Brama spoza podsieci interfejsu** — „Nexthop has invalid gateway” (lekcja 02/13).
- **Dwie trasy domyślne** o tej samej metryce (np. Wi-Fi i Ethernet) — losowe zachowanie, ECMP lub flapping; ustaw różne metryki.
- **Zapominanie o trasie powrotnej** — dodałeś trasę z A do B, ale B nie wie, jak odpowiedzieć do A.
- **Trasy dodane \`ip route add\` znikają po restarcie** — trwałe: netplan (\`routes:\`), NetworkManager (\`nmcli connection modify … +ipv4.routes\`), systemd-networkd, Windows \`route -p add\`.
- **Rozwiązywanie konfliktu adresów trasami** — przy nakładających się zakresach zawsze ktoś przegra; jedyne trwałe rozwiązanie to zmiana adresacji.
- **Ignorowanie IPv6** — VPN tunelujący tylko IPv4 przy działającym IPv6 w sieci lokalnej = „wyciek” ruchu IPv6 poza tunel.
- **Na Windows patrzenie tylko na RouteMetric** — liczy się suma z InterfaceMetric.` },

  { type: 'CODE_WRONG', heading: 'Źle: „naprawa” konfliktu VPN przez usunięcie tras', body:
`Usunięcie trasy VPN „naprawia” Dockera, ale psuje dostęp do firmy i zostanie przywrócone przy następnym połączeniu; usunięcie trasy domyślnej odcina Internet.`,
    code:
`$ sudo ip route del 172.18.0.0/17          # firma niedostępna, VPN doda trasę przy reconnect
$ sudo ip route del default                 # „bo VPN ma swoją” — a przy split tunnel Internet znika
$ sudo ip route add 172.18.0.0/16 dev br-3f1c metric 0   # nic nie zmienia: /17 z VPN nadal dłuższy niż /16
$ docker network create lab-net              # bez --subnet: Docker może wybrać zakres kolidujący z firmą` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: diagnoza ip route get i trwała zmiana adresacji Dockera', body:
`Dowód konfliktu, zmiana w źródle prawdy (pule Dockera), weryfikacja i zapis w dokumentacji zespołu.`,
    code:
`$ ip route get 172.18.0.5                  # 172.18.0.5 via 10.8.0.1 dev tun0 ← dowód konfliktu
$ ip route | grep 172.18                     # 172.18.0.0/16 dev br-3f1c ; 172.18.0.0/17 via 10.8.0.1 dev tun0
# /etc/docker/daemon.json  (zakresy uzgodnione z zespołem sieci, spoza firmy i VPN)
{ "bip": "10.200.0.1/24",
  "default-address-pools": [ { "base": "10.201.0.0/16", "size": 24 } ] }
$ sudo systemctl restart docker && docker compose down && docker compose up -d
$ docker network inspect $(docker network ls -q) -f '{{.Name}} {{range .IPAM.Config}}{{.Subnet}}{{end}}'
$ ip route get 10.201.0.5                   # dev br-… ✓ ; ip route get 172.18.0.5 → tun0 (serwer firmy) ✓` },

  { type: 'WHEN_TO_USE', heading: 'Objaw → co sprawdzić w routingu', body:
`Ściąga diagnostyczna:`,
    code:
`OBJAW                                              SPRAWDŹ
„Network is unreachable” natychmiast                brak trasy: ip route get <cel> ; ip route | grep default
działa z VPN off, nie działa z VPN on              ip route get <cel> z VPN i bez; konflikt prefiksów; ip rule
połowa adresów w sieci działa, połowa nie          bardziej szczegółowa trasa (/17, /25) dla części zakresu
z VPN nie działa Internet                          full tunnel bez NAT po stronie serwera / trasa /32 do serwera VPN
odpowiedzi nie wracają                              trasa powrotna na celu; asymetria; rp_filter
serwer z 2 interfejsami odpowiada „złym” adresem   policy routing po źródle: ip rule add from <ip> table <n>
Windows wybiera „zły” interfejs                    Find-NetRoute; InterfaceMetric (Set-NetIPInterface -InterfaceMetric)
w chmurze podsieć „nie ma Internetu”               route table: 0.0.0.0/0 → IGW (publiczna) lub NAT GW (prywatna)
kontener nie widzi usług na hoście / w LAN         trasy w kontenerze (ip route w kontenerze), NAT Dockera, konflikt zakresów` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Tablica routingu: prefiks → następny skok (via) i interfejs (dev), metryka; brak via = sieć podłączona bezpośrednio.
- Decyzja: (policy routing wybiera tabelę) → **longest prefix match** → metryka przy równym prefiksie → ECMP → ARP o następny skok.
- Trasa domyślna 0.0.0.0/0 = brama domyślna; VPN full tunnel często używa 0.0.0.0/1 + 128.0.0.0/1.
- Najważniejsze polecenia: \`ip route get\`, \`ip rule\`, \`ip route show table all\`; Windows: \`Find-NetRoute\`, \`Get-NetRoute\`, \`route print\` (koszt = metryka trasy + interfejsu).
- Konflikt VPN–Docker: dłuższy prefiks VPN przechwytuje część sieci Dockera; trwała naprawa = zmiana zakresów (daemon.json, compose ipam).
- Routing jest jednokierunkowy — pamiętaj o trasie powrotnej; zmiany trwałe zapisuj w netplan/NetworkManager/IaC.
- Następna lekcja: **routing statyczny, trasy pływające i dystans administracyjny** — z labem FRR.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const ip of ['10.20.30.200', '10.20.30.100', '10.20.200.1', '10.1.1.1', '192.168.50.200', '192.168.51.1', '172.16.0.1']) {
  const r = lookup(ROUTER, ip);
  ex(`Router ma tablicę: ${ROUTER.map(fmtRoute).join('; ')}. Którą trasą pójdzie pakiet do ${ip} i dlaczego?`, 'Wypisz pasujące trasy, wybierz najdłuższy prefiks, potem metrykę.',
    `${fmtRoute(r.route)} — ${r.reason}. Pasujące: ${r.candidates.map((c) => c.dst).join(', ')}.`);
}
for (const ip of ['10.20.1.5', '172.17.0.2', '172.31.5.5', '192.168.1.77', '172.32.1.1', '8.8.8.8']) {
  const r = lookup(LAPTOP, ip);
  ex(`Laptop ma trasy: ${LAPTOP.map(fmtRoute).join('; ')}. Przez który interfejs pójdzie ruch do ${ip}?`, 'Longest prefix match.', `${r.route.dev} — ${fmtRoute(r.route)} (${r.reason}).`);
}
const more = [
  ['Wykonaj Debug Lab z planu: odtwórz konflikt VPN (interfejs dummy z trasą 172.18.0.0/17) z siecią Dockera 172.18.0.0/16, udowodnij go i napraw trwale.', 'ip route get, daemon.json default-address-pools.', 'Odtworzenie: docker network create --subnet 172.18.0.0/16 lab-net; kontener 172.18.0.5; ip link add tun-lab type dummy; ip route add 172.18.0.0/17 via 10.8.0.1 dev tun-lab (po nadaniu adresu 10.8.0.5/24). Dowód: ip route get 172.18.0.5 → dev tun-lab; nc -vz timeout. Przyczyna: /17 dłuższy niż /16. Naprawa: daemon.json z bip i default-address-pools poza zakresami firmy, odtworzenie sieci; test ip route get i nc. Sprzątanie: ip link del tun-lab, docker network rm lab-net.'],
  ['Wyjaśnij, dlaczego klienci VPN w trybie full tunnel dodają trasy 0.0.0.0/1 i 128.0.0.0/1 zamiast zmieniać trasę domyślną, i po co trasa /32 do serwera VPN.', 'Longest prefix match; pakiety tunelu.', 'Dwie trasy /1 pokrywają całą przestrzeń IPv4 i są dłuższe niż /0, więc wygrywają z trasą domyślną bez jej usuwania — po rozłączeniu VPN wystarczy je zdjąć, stara brama zostaje nietknięta. Trasa /32 do publicznego adresu serwera VPN przez starą bramę jest potrzebna, bo zaszyfrowane pakiety tunelu muszą iść fizycznym łączem; bez niej trafiłyby do tunelu (pętla).'],
  ['Na serwerze z interfejsami eth0 (aplikacja, 203.0.113.10/24, brama .1) i eth1 (zarządzanie, 10.0.50.10/24, brama 10.0.50.1) SSH na 10.0.50.10 z sieci 10.99.0.0/16 nie działa. Wyjaśnij i napraw.', 'Asymetria; policy routing po źródle.', 'Żądanie przychodzi na eth1, ale odpowiedź do 10.99.x.x wybiera trasę domyślną przez eth0 (brak trasy do 10.99.0.0/16 przez eth1) — asymetria, rp_filter lub firewall po drodze odrzuca. Naprawa: trasa ip route add 10.99.0.0/16 via 10.0.50.1 dev eth1 albo policy routing: ip route add default via 10.0.50.1 dev eth1 table 100; ip rule add from 10.0.50.10 table 100 — odpowiedzi z adresu zarządzania zawsze wychodzą eth1. Utrwal w netplan (routing-policy).'],
  ['Porównaj wyświetlanie i wybór trasy w Windows i Linux, w tym rolę metryki interfejsu w Windows.', 'Get-NetRoute, InterfaceMetric, automatyczna metryka.', 'Linux: ip route, metryka trasy rozstrzyga przy równym prefiksie, ip route get pokazuje decyzję. Windows: Get-NetRoute/route print; koszt = RouteMetric + InterfaceMetric; InterfaceMetric domyślnie automatyczna wg prędkości łącza (np. 25 dla 1 Gb/s, wyższa dla Wi-Fi), więc Ethernet zwykle wygrywa z Wi-Fi; VPN często ustawia niską metrykę. Decyzję pokazuje Find-NetRoute -RemoteIPAddress. Zmiana: Set-NetIPInterface -InterfaceAlias "Wi-Fi" -InterfaceMetric 50.'],
  ['W AWS podsieć prywatna nie ma dostępu do Internetu, a publiczna ma. Opisz tabele routingu obu i wyjaśnij, jak instancje prywatne pobierają aktualizacje.', 'IGW, NAT Gateway, trasa local.', 'Publiczna: 10.20.0.0/16 → local, 0.0.0.0/0 → igw-…; instancje z publicznym IP wychodzą bezpośrednio. Prywatna: 10.20.0.0/16 → local, 0.0.0.0/0 → nat-… (NAT Gateway w podsieci publicznej) — wychodzą przez NAT, z zewnątrz nieosiągalne. Alternatywy bez Internetu: VPC endpoints (S3, ECR, SSM) z trasami/prefix listami w route table. LPM działa tak samo: trasa do prefix listy S3 wygrywa z 0.0.0.0/0.'],
  ['Napisz w Javie metodę, która dla listy celów (host:port z konfiguracji aplikacji) wypisuje adres źródłowy i interfejs wybrany przez system, aby w logu startowym było widać, czy ruch do bazy idzie przez VPN.', 'DatagramSocket.connect + getLocalAddress, NetworkInterface.getByInetAddress.', 'for (String host : targets) { try (DatagramSocket s = new DatagramSocket()) { s.connect(InetAddress.getByName(host), 9); InetAddress src = s.getLocalAddress(); NetworkInterface ni = NetworkInterface.getByInetAddress(src); log.info("trasa do {}: źródło {} przez {}", host, src.getHostAddress(), ni != null ? ni.getName() : "?"); } } — nie wysyła pakietów (UDP connect tylko wybiera trasę); uwaga: rozwiązuje DNS, więc przy błędnym DNS zobaczysz błąd nazwy.'],
  ['Laptop ma dwie trasy domyślne (Ethernet metric 100, Wi-Fi metric 600). Co się stanie po odłączeniu kabla i ponownym podłączeniu? Jak sprawdzić, którędy idzie ruch?', 'Metryka przy równym prefiksie; linkdown.', 'Obie trasy mają prefiks /0, wygrywa niższa metryka — Ethernet. Po odłączeniu kabla trasa Ethernetu znika lub ma flagę linkdown i ruch przechodzi na Wi-Fi (istniejące połączenia TCP zerwą się, bo zmienia się adres źródłowy). Po podłączeniu wraca Ethernet. Sprawdzenie: ip route get 8.8.8.8 (Linux), Find-NetRoute -RemoteIPAddress 8.8.8.8 (Windows).'],
  ['Wyjaśnij, czym jest ECMP i co oznacza dla aplikacji (np. przy dwóch łączach lub w Kubernetes).', 'Hash przepływu.', 'ECMP: kilka tras o równym prefiksie i koszcie; router/host rozkłada ruch na ścieżki według skrótu (hash) z adresów i portów (5-tuple), więc pakiety jednego połączenia TCP idą tą samą ścieżką (brak przestawiania kolejności), a różne połączenia — różnymi. Dla aplikacji: pojedynczy duży transfer nie przyspieszy (jedna ścieżka), wiele połączeń tak; przy zmianie liczby ścieżek część przepływów może zmienić drogę (problem dla stanowych firewalli/NAT). W Kubernetes/LB — rozkład ruchu na węzły (np. BGP z MetalLB/Calico).'],
  ['Zaproponuj, jak zespół developerski może uniknąć konfliktów tras między VPN, Dockerem, WSL2 i Kubernetesem lokalnym.', 'Polityka zakresów, dokumentacja, automatyzacja.', 'Uzgodnić z zespołem sieci zarezerwowany zakres na środowiska lokalne (np. 10.200.0.0/14) poza zakresami firmy i VPN; dystrybuować daemon.json (bip, default-address-pools) przez MDM/skrypt onboardingowy; w compose jawne ipam.subnet z tego zakresu; kind/minikube z --pod-network-cidr/--service-cluster-ip-range z tego zakresu; WSL2 — .wslconfig (networkingMode/mirrored lub stały zakres); skrypt diagnostyczny „ip route get” dla kluczowych adresów firmy w README projektu.'],
  ['Jakie zmiany tablicy routingu na stacji roboczej powinny wzbudzić alarm w SOC i jak je wykrywać?', 'Telemetria EDR, Sysmon, auditd.', 'Podejrzane: nowa trasa domyślna lub trasy 0.0.0.0/1 + 128.0.0.0/1 poza zatwierdzonym klientem VPN (nieautoryzowany VPN/tunel C2), trasy /32 do adresów DNS lub proxy przez nietypową bramę (przekierowanie ruchu), zmiany InterfaceMetric. Wykrywanie: EDR (zdarzenia zmian tras/interfejsów), Windows: zdarzenia Microsoft-Windows-TCPIP, PowerShell/route.exe w logach procesów (Sysmon EventID 1 z linią poleceń „route add”), Linux: auditd na wywołania ip/route i netlink, okresowy inwentarz ip route przez agenta i porównanie z wzorcem.'],
  ['Wyjaśnij, dlaczego dodanie trasy 172.18.0.0/16 dev br-3f1c metric 0 nie naprawia konfliktu z trasą VPN 172.18.0.0/17, a dodanie 172.18.0.0/17 dev br-3f1c metric 0 — tak (choć z efektem ubocznym).', 'LPM przed metryką.', '/16 jest krótszy niż /17, więc dla adresów 172.18.0.0–127.255 nadal wygrywa trasa VPN niezależnie od metryki. Trasa /17 do mostu ma ten sam prefiks co trasa VPN, więc decyduje metryka (0 < 50) i ruch wraca do Dockera. Efekt uboczny: serwery firmy w 172.18.0.0/17 stają się nieosiągalne — konflikt adresów nie znika, tylko przenosimy problem na drugą stronę.'],
  ['Opisz, jak sprawdzić trasy wewnątrz kontenera Docker i poda Kubernetes oraz czym różnią się od tras hosta.', 'docker exec ip route; nsenter.', 'docker exec <c> ip route (jeśli obraz ma iproute2) lub nsenter -t $(docker inspect -f {{.State.Pid}} <c>) -n ip route z hosta. Kontener w sieci bridge ma zwykle tylko: default via 172.18.0.1 (brama mostu na hoście) i trasę connected do swojej podsieci; dalszy routing i NAT robi host. Pod w Kubernetes: default via adres bramy CNI (np. 169.254.1.1 w Calico z proxy ARP), trasy do innych podów są na węzłach. Host widzi wszystkie mosty i tunele.'],
  ['Na Linuksie z WireGuard ip route pokazuje normalną trasę domyślną przez wlan0, a ruch do Internetu i tak idzie tunelem. Wyjaśnij i pokaż, jak to sprawdzić.', 'ip rule, tabele, fwmark.', 'wg-quick przy AllowedIPs=0.0.0.0/0 tworzy osobną tabelę (np. 51820) z trasą domyślną przez wg0 i reguły ip rule: „not from all fwmark 0xca6c lookup 51820” oraz „lookup main suppress_prefixlength 0”. Ruch bez znacznika trafia do tabeli VPN; pakiety samego tunelu mają fwmark i idą tabelą main. Sprawdzenie: ip rule, ip route show table 51820, ip route get 8.8.8.8 (pokaże dev wg0 i table 51820).'],
  ['Zinterpretuj wpis: „172.17.0.0/16 dev docker0 proto kernel scope link src 172.17.0.1 linkdown”.', 'proto, scope, src, linkdown.', 'Sieć 172.17.0.0/16 podłączona bezpośrednio do mostu docker0 (brak via — ARP o sam cel), trasa utworzona automatycznie przez jądro z adresu interfejsu (proto kernel), dotyczy tylko łącza (scope link), adres źródłowy preferowany 172.17.0.1, a linkdown oznacza, że most nie ma aktywnych portów (żaden kontener nie jest podłączony) — trasa jest ignorowana przy wyborze, dopóki łącze nie wstanie.'],
  ['Zaplanuj trwałą konfigurację trasy statycznej 10.50.0.0/16 przez 192.168.1.254 w netplan (Ubuntu) i w NetworkManager.', 'routes: to/via; nmcli +ipv4.routes.', 'Netplan: network: version: 2, ethernets: eth0: routes: [{to: 10.50.0.0/16, via: 192.168.1.254}] → sudo netplan try (automatyczny rollback po 120 s bez potwierdzenia), potem netplan apply. NetworkManager: nmcli connection modify "Wired connection 1" +ipv4.routes "10.50.0.0/16 192.168.1.254" && nmcli connection up "Wired connection 1". Weryfikacja: ip route get 10.50.1.1. Windows: route -p add 10.50.0.0 mask 255.255.0.0 192.168.1.254 lub New-NetRoute (trwałe domyślnie).'],
  ['Kiedy routing nie wystarczy i trzeba użyć NAT lub zmiany adresacji? Podaj trzy sytuacje.', 'Nakładające się zakresy, brak tras powrotnych.', '1) Dwie sieci z nakładającymi się zakresami muszą się komunikować (fuzja firm, VPN do partnera z tym samym 192.168.1.0/24) — potrzebny NAT (twice NAT / NETMAP) lub renumeracja. 2) Nie kontrolujesz routingu po drugiej stronie i nie możesz dodać trasy powrotnej — SNAT na swój adres (tak działa Docker do Internetu). 3) Adresy prywatne do Internetu — NAT/PAT (lekcja 09). Routing tylko wybiera drogę; nie usuwa niejednoznaczności adresów.'],
  ['Przeanalizuj wynik Find-NetRoute -RemoteIPAddress 10.20.1.5 na Windows, który pokazuje InterfaceAlias „Ethernet” zamiast „VPN-Firma”. Co sprawdzisz?', 'Czy VPN dodał trasę; metryki; split tunnel.', 'Sprawdź Get-NetRoute -DestinationPrefix 10.0.0.0/8 (lub węższe) — czy klient VPN dodał trasy do sieci firmy; jeśli nie — konfiguracja split tunnel po stronie serwera VPN lub brak połączenia. Jeśli trasy są o równym prefiksie na obu interfejsach — porównaj sumę RouteMetric + InterfaceMetric. Sprawdź też, czy w sieci lokalnej nie ma trasy bardziej szczegółowej (np. lokalna sieć 10.20.0.0/16 w domu/hotelu — konflikt).'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
