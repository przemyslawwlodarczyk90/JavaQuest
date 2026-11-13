// Generuje lekcje _ns_02_ethernet_lan/08_EtherChannelLacp.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_08_lacp_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/08_EtherChannelLacp.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { MODES, negotiate, hashIp, hashL4, lastOct } = require('./ns02_08_lacp_model.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const BOND = [
  [0, 'balance-rr', 'pakiety po kolei na każde łącze (round-robin)', 'wymaga statycznego kanału po stronie przełącznika; zmienia kolejność pakietów TCP'],
  [1, 'active-backup', 'jedno łącze aktywne, drugie czeka', 'działa z dowolnym przełącznikiem, bez konfiguracji na przełączniku'],
  [2, 'balance-xor', 'hash z adresów wybiera łącze', 'wymaga statycznego kanału (on)'],
  [3, 'broadcast', 'każda ramka na wszystkie łącza', 'zastosowania specjalne'],
  [4, '802.3ad', 'LACP — dynamiczna agregacja z hashowaniem', 'wymaga LACP na przełączniku; standard w serwerowniach'],
  [5, 'balance-tlb', 'adaptacyjne równoważenie nadawania', 'bez konfiguracji przełącznika; odbiór jednym łączem'],
  [6, 'balance-alb', 'jak tlb + równoważenie odbioru przez ARP', 'bez konfiguracji przełącznika; manipuluje odpowiedziami ARP'],
];

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Dwa kable to nie zawsze dwa razy więcej', ...img('ns02-08-vs-stp.svg', 'Po lewej dwa łącza 1G między SW1 i SW2 bez agregacji: STP blokuje jedno, aktywne jest tylko jedno. Po prawej Port-channel1 z LACP: oba łącza aktywne jako jedno łącze logiczne, które STP traktuje jak jeden port. Podpis: pojedynczy przepływ TCP nadal korzysta z jednego kabla.'),
    body:
`Z lekcji 07 wiesz, że STP zablokuje drugi kabel między dwoma przełącznikami — redundancja jest, ale połowa przepustowości stoi bezczynnie, a awaria aktywnego kabla wywołuje przebudowę drzewa. **Agregacja łączy** (Link Aggregation, w Cisco **EtherChannel**, w Linuksie **bonding**, w Windows **NIC teaming**) łączy kilka fizycznych portów w **jeden port logiczny**:
- **przepustowość** — ruch wielu przepływów rozkłada się na wszystkie łącza,
- **redundancja** — awaria jednego kabla zmniejsza przepustowość, ale nie przerywa łącza logicznego i **nie zmienia topologii STP**,
- **prostota** — STP, VLAN-y i adresacja widzą jeden interfejs (\`Port-channel1\`, \`bond0\`).

Standardem negocjacji jest **LACP** (IEEE 802.3ad, dziś **802.1AX**); Cisco ma też własny, starszy **PAgP**, a tryb **on** tworzy kanał bez żadnego protokołu.

Dla Java developera najważniejsza jest jedna, notorycznie mylona prawda: **agregacja nie przyspiesza pojedynczego połączenia**. Przełącznik (lub jądro Linuksa) przypisuje każdy przepływ do jednego łącza na podstawie **hasha z nagłówków**, żeby nie zmieniać kolejności pakietów TCP. Kopia pliku przez jedno połączenie SCP po „4 × 10G” pojedzie z prędkością 10G. Wydajność rośnie dla wielu klientów, wielu połączeń (pula połączeń JDBC, równoległe strumienie) — a rozkład zależy od tego, jak różnorodne są adresy i porty.

W tej lekcji: LACP vs PAgP vs on i macierz trybów, algorytmy hashowania i ich pułapki, konfiguracja i weryfikacja w Cisco IOS, **bonding mode 4 w Linuksie** (Guided Lab na namespace’ach), NIC teaming w Windows, **MLAG/vPC** w zarysie oraz aspekty bezpieczeństwa i diagnostyki. Odpowiada to celom CCNA 200-301 (D2: konfiguracja i weryfikacja EtherChannel L2/L3, LACP) i Network+ (D2: agregacja portów).` },

  { type: 'DEFINITION', heading: 'Słownik agregacji łączy', body:
`- **LAG (Link Aggregation Group)** — grupa portów działających jako jeden port logiczny; w Cisco **EtherChannel / Port-channel**, w Linuksie **bond**, w Windows **team**, w Junos **ae (aggregated Ethernet)**.
- **LACP (Link Aggregation Control Protocol, 802.3ad → 802.1AX)** — standard negocjacji: strony wymieniają **LACPDU** (multicast \`01:80:c2:00:00:02\`, EtherType 0x8809 Slow Protocols), sprawdzają zgodność i włączają porty do grupy. Tryby: **active** (inicjuje), **passive** (odpowiada).
- **PAgP** — protokół Cisco; tryby **desirable** (inicjuje), **auto** (odpowiada). Dziś rzadko używany.
- **Tryb on** — kanał statyczny bez protokołu; nie wykrywa błędów okablowania ani niezgodnej konfiguracji drugiej strony.
- **LACP rate** — częstotliwość LACPDU: **slow** co 30 s (timeout 90 s), **fast** co 1 s (timeout 3 s).
- **System ID / Key / Port priority** — identyfikatory LACP: priorytet systemu + MAC, klucz grupy (porty o tym samym kluczu mogą się agregować), priorytet portu (które porty aktywne, gdy jest limit).
- **Load balancing / hash** — algorytm wyboru łącza dla przepływu: \`src-mac\`, \`dst-mac\`, \`src-dst-mac\`, \`src-dst-ip\`, \`src-dst-port\`/\`l3+l4\`.
- **min-links** — minimalna liczba aktywnych łączy, poniżej której cały kanał jest wyłączany (np. aby ruch poszedł inną drogą).
- **Bonding (Linux)** — tryby 0–6; **mode 4 = 802.3ad (LACP)**, **mode 1 = active-backup**; parametr \`xmit_hash_policy\` (layer2, layer2+3, layer3+4).
- **NIC teaming (Windows)** — tryby Switch Independent, Static, LACP; w Hyper-V nowszy **SET (Switch Embedded Teaming)**.
- **MLAG / vPC / MC-LAG** — agregat zakończony na **dwóch** przełącznikach widocznych dla partnera jako jeden system LACP; standardowym następcą jest **EVPN multihoming**.
- **L3 EtherChannel** — kanał z adresem IP (\`no switchport\`), np. między przełącznikami L3 lub do routera.` },

  { type: 'ANALOGY', heading: 'Autostrada z kilkoma pasami i zasadą „nie zmieniaj pasa”', body:
`Agregat to **autostrada z kilkoma pasami** zamiast jednej drogi. Ale obowiązuje dziwna zasada: **każdy konwój (przepływ TCP) musi jechać jednym pasem od początku do końca**, bo gdyby ciężarówki z jednego konwoju jechały różnymi pasami, dojechałyby w złej kolejności i odbiorca musiałby je układać (zmiana kolejności pakietów TCP = retransmisje, spadek wydajności).
- **Bramka przy wjeździe** (funkcja hash) przydziela pas na podstawie tablicy rejestracyjnej konwoju (adresy, porty) — nie patrzy, który pas jest pusty. Jeśli wszystkie konwoje mają podobne tablice (np. wszystkie z jednego routera, ten sam adres MAC), mogą trafić na ten sam pas, a pozostałe będą puste.
- **Jeden konwój nigdy nie pojedzie szybciej niż pozwala jeden pas** — cztery pasy pomagają, gdy konwojów jest wiele.
- **Zamknięcie pasa** (awaria kabla) — bramka przydziela jego konwoje do pozostałych pasów; ruch zwalnia, ale autostrada działa. Bez agregacji zamknięcie drogi oznaczałoby objazd przez inne miasto (przebudowa STP).
- **LACP** to **dyspozytorzy po obu stronach**, którzy co chwilę przez radio potwierdzają: „pas 3 jest mój i prowadzi do ciebie”. Tryb **on** to brak radia: jeśli po drugiej stronie pas prowadzi gdzie indziej (błąd w okablowaniu), nikt się nie zorientuje — część ruchu wpadnie w przepaść.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Jak hash rozkłada przepływy — policz sam', ...img('ns02-08-hash.svg', 'Pięć przepływów od różnych klientów do serwerów trafia do funkcji hash liczonej z adresów IP i portów modulo liczba łączy; każdy przepływ przypisany do jednego z czterech łączy 1G, łącze 0 niesie dwa przepływy. Rozkład zależy od różnorodności nagłówków, nie od obciążenia.'),
    body:
`Producenci używają różnych funkcji hash, ale idea jest wspólna: z wybranych pól nagłówka liczona jest wartość, a jej dolne bity (modulo liczba łączy) wybierają łącze. W lekcji używamy **uproszczonego modelu dydaktycznego**: \`(ostatni oktet src IP XOR ostatni oktet dst IP) mod N\` dla \`src-dst-ip\` oraz z dodatkowym XOR portów dla \`l3+l4\`. Przykłady dla 4 łączy:`,
    code:
(() => {
  const rows = [['10.0.0.11', '10.0.1.50', 51000, 5432], ['10.0.0.12', '10.0.1.50', 51001, 5432], ['10.0.0.13', '10.0.1.60', 51002, 443], ['10.0.0.14', '10.0.1.50', 51003, 5432], ['10.0.0.15', '10.0.1.70', 51004, 9092]];
  return 'src → dst                     src-dst-ip         l3+l4 (z portami)\n' + rows.map(([s, d, sp, dp]) => `${(s + ' → ' + d).padEnd(28)}  (${lastOct(s)}^${lastOct(d)})%4 = ${hashIp(s, d, 4)}       (${sp}^${dp}^${lastOct(s) ^ lastOct(d)})%4 = ${hashL4(s, d, sp, dp, 4)}`).join('\n') +
`

Wniosek 1: ten sam przepływ zawsze daje ten sam wynik → zawsze to samo łącze → brak zmiany kolejności.
Wniosek 2: ruch z JEDNEGO klienta do JEDNEGO serwera przy src-dst-ip zawsze idzie jednym łączem,
           nawet przy wielu połączeniach; l3+l4 rozkłada połączenia po portach źródłowych.
Wniosek 3: src-dst-mac między dwoma routerami (zawsze te same MAC) = wszystko na jednym łączu!
Wniosek 4: przy 3 łączach (lub 6) rozkład bywa nierówny — wybieraj 2, 4 lub 8 łączy.`;
})() },

  { type: 'CODE_BASIC', heading: 'EtherChannel w Cisco IOS: konfiguracja i weryfikacja', body:
`Wszystkie porty członkowskie muszą mieć **identyczną** konfigurację: prędkość, duplex, tryb (access/trunk), VLAN-y, native VLAN. Konfigurujesz porty fizyczne poleceniem \`channel-group\`, a dalsze ustawienia (trunk, VLAN-y) — na \`interface Port-channel\`.`,
    code:
`SW1(config)# interface range GigabitEthernet1/0/47 - 48
SW1(config-if-range)# channel-group 1 mode active          ! LACP, inicjuje negocjację
SW1(config-if-range)# exit
SW1(config)# interface Port-channel1
SW1(config-if)# switchport mode trunk
SW1(config-if)# switchport trunk allowed vlan 10,20,30
SW1(config-if)# switchport trunk native vlan 999
SW1(config)# port-channel load-balance src-dst-ip          ! globalnie (platformy Catalyst)
SW1(config)# lacp system-priority 100                       ! opcjonalnie

SW1# show etherchannel summary
Flags:  D - down        P - bundled in port-channel
        I - stand-alone s - suspended
        S - Layer2      U - in use      (SU = L2 i w użyciu)
Group  Port-channel  Protocol    Ports
------+-------------+-----------+--------------------------------
1      Po1(SU)         LACP      Gi1/0/47(P)   Gi1/0/48(P)

SW1# show lacp neighbor                ! partner: System ID, port, klucz, flagi (fast/slow, active)
SW1# show etherchannel load-balance    ! aktywny algorytm hash
SW1# show interfaces Port-channel1     ! BW 2000000 Kbit (suma łączy)
SW1# test etherchannel load-balance interface po1 ip 10.0.0.11 10.0.1.50   ! które łącze wybierze (niektóre platformy)

Flaga (I) stand-alone lub (s) suspended przy porcie = niezgodna konfiguracja lub brak partnera LACP.` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab: bond LACP między dwoma namespace’ami', ...img('ns02-08-modes.svg', 'Macierz trybów kanału: active, passive, on, desirable, auto. Kanał LACP powstaje dla active z active i active z passive, PAgP dla desirable z desirable i desirable z auto, statyczny tylko dla on z on; passive z passive i auto z auto nie tworzą kanału, a on z trybem negocjowanym grozi pętlą lub utratą ruchu.'),
    body:
`Laboratorium z planu lekcji — na maszynie wirtualnej z Linuksem (root):
1. **Utwórz dwa namespace’y** \`ns1\` i \`ns2\` oraz dwie pary veth łączące je bezpośrednio (\`a1–b1\`, \`a2–b2\`) — dwa „kable”.
2. **Utwórz bond w ns1**: \`ip link add bond0 type bond mode 802.3ad lacp_rate fast xmit_hash_policy layer3+4 miimon 100\`; podepnij \`a1\` i \`a2\` (\`ip link set a1 down; ip link set a1 master bond0\`), nadaj adres 192.168.50.1/24, podnieś interfejsy. To samo w ns2 z \`b1\`, \`b2\` i adresem 192.168.50.2/24.
3. **Sprawdź negocjację**: \`ip netns exec ns1 cat /proc/net/bonding/bond0\` — szukaj \`Bonding Mode: IEEE 802.3ad\`, \`Partner Mac Address\` różny od zer, ten sam \`Aggregator ID\` dla obu portów, \`LACP rate: fast\`.
4. **Przechwyć LACPDU**: \`ip netns exec ns1 tcpdump -i a1 -e ether proto 0x8809\` — co sekundę (rate fast) pakiet do \`01:80:c2:00:00:02\`.
5. **Test ciągłości**: \`ip netns exec ns1 ping -i 0.2 192.168.50.2\` i w trakcie \`ip link set a1 down\` (w ns1) — zanotuj liczbę utraconych pakietów (zwykle 0–1). Przywróć łącze.
6. **Test rozkładu**: uruchom \`iperf3 -s\` w ns2 i \`iperf3 -c 192.168.50.2 -P 8\` w ns1; liczniki \`ip -s link show a1\` i \`a2\` pokażą ruch na obu łączach. Z \`-P 1\` (jeden strumień) — tylko na jednym.
7. **Błąd celowy**: zmień w ns2 tryb na \`active-backup\` (bez LACP) — w ns1 \`/proc/net/bonding/bond0\` pokaże brak partnera i porty w osobnych agregatorach; zapisz, jak to wygląda, bo tak wygląda awaria konfiguracji w produkcji.
8. **Posprzątaj**: \`ip netns del ns1; ip netns del ns2\`.` },

  { type: 'NOTE', heading: 'Tryby bondingu w Linuksie i ich wymagania', body:
`Wybór trybu zależy od tego, co potrafi (i jak jest skonfigurowany) przełącznik po drugiej stronie. W serwerowni standardem jest **mode 4 (802.3ad)** z LACP na przełączniku; gdy nie masz wpływu na przełącznik — **mode 1 (active-backup)**.`,
    code:
`${BOND.map(([n, name, d, req]) => `mode ${n}  ${name.padEnd(13)} ${d.padEnd(46)} ${req}`).join('\n')}

xmit_hash_policy (mode 2 i 4):
  layer2     — XOR adresów MAC (domyślnie): ruch do bramy = zawsze jedno łącze
  layer2+3   — MAC + IP: lepiej dla wielu hostów docelowych
  layer3+4   — IP + porty: najlepszy rozkład wielu połączeń (uwaga: fragmenty IP bez portów)

Netplan (Ubuntu):
  bonds:
    bond0:
      interfaces: [enp1s0f0, enp1s0f1]
      parameters: {mode: 802.3ad, lacp-rate: fast, transmit-hash-policy: layer3+4, mii-monitor-interval: 100}
      addresses: [10.20.0.15/24]
Windows Server:  New-NetLbfoTeam -Name Team1 -TeamMembers NIC1,NIC2 -TeamingMode Lacp -LoadBalancingAlgorithm Dynamic
Hyper-V (SET):   New-VMSwitch -Name SETswitch -NetAdapterName NIC1,NIC2 -EnableEmbeddedTeaming $true` },

  { type: 'CODE_PRACTICAL', heading: 'Diagnoza: czy agregat naprawdę działa', body:
`Najczęstsze pytanie z produkcji: „mamy 2 × 10G, a kopia idzie 9,4 Gb/s” albo „jedno łącze ma 90%, drugie 5%”. Narzędzia i odpowiedzi:`,
    code:
`# Linux: stan bondingu i partnera LACP
cat /proc/net/bonding/bond0
#   Slave Interface: enp1s0f0 ... Aggregator ID: 1 ... Partner Mac Address: 00:1c:73:aa:bb:cc
#   (różne Aggregator ID lub partner 00:00:00:00:00:00 = LACP nie wynegocjowany)
ip -s link show enp1s0f0 ; ip -s link show enp1s0f1      # rozkład bajtów TX/RX na łączach
ethtool enp1s0f0 | grep -E 'Speed|Duplex'                # członkowie muszą mieć tę samą prędkość
cat /sys/class/net/bond0/bonding/xmit_hash_policy

# Test przepustowości — jeden vs wiele przepływów
iperf3 -c 10.20.0.50 -t 20            # 1 strumień: max prędkość JEDNEGO łącza
iperf3 -c 10.20.0.50 -t 20 -P 8       # 8 strumieni: szansa na wykorzystanie wszystkich łączy

# Cisco
show etherchannel summary             # P = w kanale, I = stand-alone, s = suspended, D = down
show lacp neighbor detail
show interfaces po1 counters          # porównaj liczniki członków: show interfaces gi1/0/47 counters
show etherchannel load-balance

# Java: wiele połączeń zamiast jednego, żeby skorzystać z agregatu (np. równoległe pobieranie zakresów)
HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
// N żądań z nagłówkiem Range: bytes=a-b → N połączeń TCP → różne porty źródłowe → różne łącza (przy l3+l4)` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', ...img('ns02-08-mlag.svg', 'Dwa przełączniki LEAF-A i LEAF-B połączone peer-linkiem i keepalive tworzą parę MLAG; serwer z bondingiem LACP ma po jednym łączu do każdego i widzi je jako jednego partnera LACP. Awaria całego przełącznika nie odcina serwera.'),
    body:
`**Developer / DevOps / SRE:**
- **Serwery bare metal i hiperwizory** — prawie zawsze dwa porty w bondzie LACP do pary przełączników MLAG/vPC: odporność na awarię kabla, karty, a nawet całego przełącznika. Na tym stoją bazy danych, węzły Kafki, Kubernetes on-prem.
- **„Mamy 2 × 25G, a replikacja bazy idzie 25G”** — replikacja (PostgreSQL streaming, MySQL binlog) to zwykle jedno połączenie TCP → jedno łącze. To nie błąd sieci, tylko natura hashowania.
- **Nierówny rozkład** — ruch do jednego adresu (np. load balancer, brama) przy \`layer2\` lub \`src-dst-ip\` ląduje na jednym łączu. Rozwiązanie: \`layer3+4\` (Linux) / \`src-dst-port\` lub \`src-dst-mixed-ip-port\` (przełącznik), więcej połączeń w puli.
- **Kubernetes i chmura** — w chmurze publicznej nie konfigurujesz bondingu (dostawca robi to pod spodem), ale węzły on-prem i serwery bazodanowe — tak. Awaria jednego łącza w bondzie nie powinna generować żadnego zdarzenia w aplikacji; jeśli generuje — konfiguracja jest zła (np. mode 0 bez kanału na przełączniku).
- **Testy wydajności** — mierz \`iperf3 -P\`, a wyniki pojedynczego strumienia interpretuj jako górną granicę dla jednego połączenia.

**Blue team / dostępność:**
- **Dostępność to część triady CIA** — agregaty i MLAG to podstawowa ochrona dostępności usług; monitoruj liczbę aktywnych członków (\`min-links\`, alert przy degradacji do jednego łącza — kolejna awaria oznacza przestój).
- **Tryb on i błędy okablowania** — kanał statyczny po stronie przełącznika i brak kanału po stronie hosta lub błędnie wpięty kabel mogą tworzyć pętle L2; Cisco ma **EtherChannel Misconfig Guard** (porty err-disabled). LACP wykrywa takie sytuacje.
- **Monitoring LACP** — logi \`%EC-5-UNBUNDLE\`, \`%LINK-3-UPDOWN\` na członkach, flapping LACP; nieoczekiwane zmiany partnera LACP (inny System ID) mogą oznaczać przepięcie kabla do nieautoryzowanego urządzenia.
- **Analiza ruchu** — przy SPAN/TAP na agregacie trzeba objąć wszystkie łącza członkowskie (lub Port-channel), inaczej sensor IDS widzi tylko część przepływów (rozdział o NSM).` },

  { type: 'PITFALL', heading: 'Częste błędy z agregacją', body:
`- **„4 × 10G = 40G dla mojego transferu”** — nie; pojedynczy przepływ korzysta z jednego łącza. Agregat skaluje liczbę przepływów, nie prędkość jednego.
- **Niezgodna konfiguracja członków** — inna prędkość, duplex, VLAN-y, native VLAN lub tryb na jednym porcie → port w stanie suspended/stand-alone; kanał działa „na pół gwizdka”.
- **Tryb on po jednej stronie, LACP po drugiej** — kanał nie powstaje poprawnie, ryzyko pętli lub utraty ruchu; zawsze LACP active (lub active/passive) po obu stronach.
- **passive + passive (lub auto + auto)** — nikt nie inicjuje, kanał nie powstaje, a STP zablokuje nadmiarowe łącze; łatwo przeoczyć, bo „coś działa”.
- **Bond mode 0 (balance-rr) na zwykłym przełączniku** — ten sam MAC pojawia się na dwóch portach naprzemiennie (MAC flapping), pakiety TCP przychodzą w złej kolejności; bez kanału statycznego na przełączniku to przepis na problemy.
- **Agregat do dwóch różnych przełączników bez MLAG/vPC/stosu** — LACP wykryje dwóch różnych partnerów i utworzy dwa osobne agregatory (jeden nieaktywny) albo — w trybie statycznym — powstanie pętla/czarna dziura.
- **Liczba łączy niebędąca potęgą dwójki** — 3 lub 6 łączy daje nierówny rozkład w wielu implementacjach hasha.
- **Hash \`src-mac\`/\`layer2\` przy ruchu przez router** — wszystkie ramki mają ten sam MAC bramy → jedno łącze przeciążone, reszta pusta.
- **LACP rate slow przy szybkich wymaganiach** — wykrycie „cichej” awarii (łącze up, ale partner nie odpowiada) trwa do 90 s; \`lacp rate fast\` skraca to do 3 s (detekcja utraty łącza fizycznego jest i tak natychmiastowa).` },

  { type: 'CODE_WRONG', heading: 'Źle: bond mode 0 na serwerze i kanał statyczny „na pałę”', body:
`Serwer z dwiema kartami w trybie round-robin podłączony do dwóch zwykłych portów, a na innym przełączniku kanał \`on\` bez protokołu.`,
    code:
`# /etc/netplan/01-bond.yaml (serwer)
bonds:
  bond0:
    interfaces: [eno1, eno2]
    parameters: {mode: balance-rr}           # mode 0
# Przełącznik: Gi1/0/5 i Gi1/0/6 jako zwykłe porty access (bez kanału)
# Skutki: MAC serwera „skacze” między portami (MACFLAP), pakiety TCP w złej kolejności,
#         retransmisje, losowe spadki wydajności aplikacji i dziwne timeouty.

! Inny przełącznik: kanał bez protokołu
interface range Gi1/0/47 - 48
 channel-group 1 mode on
! Druga strona skonfigurowana jako LACP active (lub w ogóle bez kanału):
! kanał nie negocjuje się, porty mogą przejść w err-disabled albo tworzyć pętlę;
! błędnie wpięty kabel (do innego przełącznika) nie zostanie wykryty.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: LACP po obu stronach, MLAG, właściwy hash i monitoring', body:
`Serwer w bondzie 802.3ad z \`layer3+4\` i \`lacp_rate fast\`, przełączniki w parze MLAG/vPC z kanałem LACP active, min-links i alertami.`,
    code:
`# Serwer (netplan)
bonds:
  bond0:
    interfaces: [eno1, eno2]                  # eno1 → LEAF-A, eno2 → LEAF-B (para MLAG)
    parameters:
      mode: 802.3ad
      lacp-rate: fast
      transmit-hash-policy: layer3+4
      mii-monitor-interval: 100

! LEAF-A i LEAF-B (Arista EOS, MLAG — analogicznie vPC w NX-OS)
interface Ethernet5
 channel-group 5 mode active
interface Port-Channel5
 switchport access vlan 20
 mlag 5                                       ! ten sam numer na obu przełącznikach pary
 port-channel min-links 1
 spanning-tree portfast
 spanning-tree bpduguard enable
!
! Monitoring: alert, gdy liczba aktywnych członków < 2 (degradacja),
! syslog: zdarzenia unbundle/LACP timeout, zmiana partnera LACP; SNMP/gNMI: liczniki per członek.` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy agregacja, a kiedy coś innego', body:
`- **Agregacja LACP** — połączenia przełącznik–przełącznik (zamiast blokowanych przez STP łączy), serwery i hiperwizory z dwiema kartami, połączenia do firewalli i load balancerów. Zawsze z LACP active, nigdy \`on\` bez ważnego powodu.
- **MLAG/vPC/EVPN multihoming** — gdy chcesz przetrwać awarię całego przełącznika (serwery krytyczne, pary dystrybucyjne). Wymaga dodatkowej konfiguracji i dyscypliny (peer-link, keepalive, spójna konfiguracja pary).
- **Active-backup (mode 1)** — gdy nie masz wpływu na przełącznik lub łącza idą do różnych, niezależnych przełączników bez MLAG; tylko redundancja, bez sumowania przepustowości.
- **Szybsze łącze zamiast agregatu** — jeśli potrzebujesz przepustowości dla pojedynczych przepływów (backup, replikacja, duże transfery), 1 × 25G lepiej niż 4 × 10G. Agregat nie zastąpi szybszego portu.
- **Routing L3 z ECMP** — w centrach danych (spine-leaf) między przełącznikami zamiast LAG-ów stosuje się routing z wieloma równymi trasami — podobna idea (hash przepływów), ale na L3 i bez ograniczeń L2.
- **Aplikacja** — jeśli chcesz wykorzystać kilka łączy, używaj wielu połączeń (pula, równoległe strumienie, HTTP/2 do wielu backendów), a nie jednego „grubego” połączenia.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Agregacja (EtherChannel, bond, team) łączy porty w jeden logiczny: więcej przepustowości dla wielu przepływów, redundancja, brak blokowania przez STP.
- Protokoły: LACP (802.1AX; active/passive), PAgP (Cisco; desirable/auto), on (statyczny). Kanał powstaje: active+active, active+passive, desirable+desirable/auto, on+on.
- Hash przypisuje każdy przepływ do jednego łącza → jeden przepływ TCP nie przyspieszy; rozkład zależy od różnorodności adresów i portów (layer3+4 / src-dst-port najlepszy dla wielu połączeń).
- Członkowie muszą mieć identyczną konfigurację; weryfikacja: show etherchannel summary (P, I, s, D), show lacp neighbor, /proc/net/bonding/bond0.
- Linux: mode 4 = 802.3ad (LACP, wymaga przełącznika), mode 1 = active-backup (bez przełącznika); mode 0 bez kanału na przełączniku = MAC flapping.
- LACP rate: slow 30 s (timeout 90 s), fast 1 s (timeout 3 s); min-links i alerty o degradacji.
- MLAG/vPC/EVPN multihoming: agregat do dwóch przełączników — odporność na awarię całego urządzenia.
- Następna lekcja: **LLDP i CDP — kto jest po drugiej stronie kabla**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const pairsEx = [['active', 'passive'], ['passive', 'passive'], ['on', 'active'], ['desirable', 'auto'], ['active', 'desirable']];
ex(`Dla par trybów ${pairsEx.map(([a, b]) => `${a}/${b}`).join(', ')} określ, czy powstanie kanał i dlaczego.`, 'LACP: active inicjuje; PAgP: desirable inicjuje; on bez protokołu; protokoły się nie mieszają.', pairsEx.map(([a, b]) => `${a}/${b}: ${negotiate(a, b).long}`).join('; ') + '.');
for (const [s, d, n] of [['10.0.0.21', '10.0.0.1', 2], ['192.168.5.7', '192.168.9.200', 4], ['172.16.0.33', '172.16.1.17', 8]]) {
  ex(`W modelu dydaktycznym src-dst-ip (ostatni oktet src XOR ostatni oktet dst, mod N) wyznacz łącze dla przepływu ${s} → ${d} w agregacie ${n} łączy. Czy odpowiedź (${d} → ${s}) pójdzie tym samym łączem?`, 'XOR jest przemienny.', `${lastOct(s)} XOR ${lastOct(d)} = ${lastOct(s) ^ lastOct(d)}, mod ${n} = ${hashIp(s, d, n)} → łącze ${hashIp(s, d, n)}. Odpowiedź daje ten sam XOR (przemienność), więc po stronie przełącznika z tym samym algorytmem trafi na łącze o tym samym numerze — ale uwaga: każda strona agregatu liczy hash niezależnie i może mieć inny algorytm, więc ruch w dwie strony może iść różnymi kablami.`);
}
for (const [links, sp, flows] of [[4, 10, 1], [4, 10, 16], [2, 25, 3]]) {
  ex(`Agregat ${links} × ${sp} Gb/s. Ile maksymalnie może osiągnąć ${flows === 1 ? 'pojedynczy przepływ TCP' : `${flows} równoległych przepływów (przy idealnym rozkładzie)`} i jaka jest suma przepustowości kanału?`, 'Jeden przepływ = jedno łącze; suma = liczba łączy × prędkość.', `Suma kanału: ${links * sp} Gb/s. ${flows === 1 ? `Pojedynczy przepływ: maks. ${sp} Gb/s (jedno łącze).` : `${flows} przepływów: maks. ${Math.min(flows, links) * sp} Gb/s łącznie (każdy przepływ ≤ ${sp} Gb/s; ${flows < links ? 'za mało przepływów, by zająć wszystkie łącza' : 'w praktyce rozkład hasha jest nierówny, więc wynik będzie niższy'}).`}`);
}
const more = [
  ['Wykonaj Guided Lab z lekcji (bond LACP między namespace’ami) i przedstaw: fragment /proc/net/bonding/bond0, liczbę utraconych pakietów przy wyłączeniu jednego łącza, wynik iperf3 dla -P 1 i -P 8.', 'Partner MAC, Aggregator ID, liczniki ip -s link.', 'Przykład: Bonding Mode: IEEE 802.3ad Dynamic link aggregation; Transmit Hash Policy: layer3+4; LACP rate: fast; oba slave’y Aggregator ID: 1, Partner Mac Address: (MAC bond0 w ns2). Wyłączenie a1: 0–1 utraconych pakietów przy ping -i 0.2. iperf3 -P 1: ruch tylko na jednym veth (liczniki drugiego prawie stałe); -P 8: ruch na obu. Po zmianie trybu ns2 na active-backup: Partner Mac 00:00:00:00:00:00, różne Aggregator ID.'],
  ['Skonfiguruj na dwóch przełącznikach Cisco EtherChannel LACP z dwóch portów jako trunk z VLAN-ami 10 i 20. Podaj kolejność poleceń i weryfikację. Co się stanie, jeśli na jednym porcie członkowskim zostanie inny native VLAN?', 'channel-group N mode active na portach, potem konfiguracja Port-channel.', 'Na obu: interface range Gi1/0/47 - 48; channel-group 1 mode active; interface Port-channel1; switchport mode trunk; switchport trunk allowed vlan 10,20; switchport trunk native vlan 999. Weryfikacja: show etherchannel summary — Po1(SU) LACP Gi1/0/47(P) Gi1/0/48(P); show interfaces trunk — Po1. Port z innym native VLAN zostanie zawieszony (s) lub stand-alone (I) — niezgodna konfiguracja; kanał działa na pozostałym łączu.'],
  ['Zespół bazodanowy zgłasza: „serwer ma bond 2 × 10G, a replikacja do repliki osiąga max 9,4 Gb/s”. Wyjaśnij przyczynę i zaproponuj trzy opcje poprawy.', 'Jeden przepływ = jedno łącze.', 'Replikacja to jedno połączenie TCP; hash przypisuje je do jednego łącza 10G, więc 9,4 Gb/s to praktyczne maksimum (narzut nagłówków). Opcje: (1) szybsze łącze (25G/100G) zamiast agregatu; (2) mechanizm replikacji/kopii z wieloma równoległymi połączeniami (np. równoległe strumienie backupu, pgBackRest z process-max); (3) jumbo frames i tuning TCP podnoszą efektywność, ale nie przekroczą 10G. Agregat nadal daje redundancję i przepustowość dla wielu klientów.'],
  ['Na serwerze z bond mode 4 widzisz w /proc/net/bonding/bond0 dla jednego portu „Partner Mac Address: 00:00:00:00:00:00” i inny Aggregator ID. Co to oznacza i co sprawdzisz?', 'Port nie wynegocjował LACP z partnerem.', 'Ten port nie otrzymuje LACPDU od przełącznika (lub otrzymuje od innego systemu), więc nie należy do aktywnego agregatora — ruch idzie tylko przez pozostałe łącza (degradacja). Sprawdzić: czy port przełącznika jest w channel-group z mode active/passive (a nie on lub bez kanału), czy kabel jest wpięty do właściwego przełącznika (w parze MLAG — czy MLAG jest skonfigurowany), show lacp neighbor po stronie przełącznika, prędkość/duplex portu (ethtool).'],
  ['Wyjaśnij, dlaczego ruch między dwoma routerami połączonymi agregatem 4 łączy z algorytmem src-dst-mac może trafić tylko na jedno łącze. Co zmienić?', 'Ile różnych par MAC widzi agregat między dwoma routerami?', 'Między dwoma routerami wszystkie ramki mają ten sam MAC źródłowy i docelowy (interfejsy routerów), więc hash z MAC zawsze daje ten sam wynik — cały ruch na jednym łączu. Zmiana: algorytm z adresami IP i portami (src-dst-ip lub src-dst-mixed-ip-port / l3+l4), który różnicuje przepływy różnych hostów i połączeń; alternatywnie zamiast LAG — łącza L3 z ECMP.'],
  ['Porównaj tryby bondingu mode 1 (active-backup) i mode 4 (802.3ad): wymagania wobec przełącznika, przepustowość, czas przełączenia i ryzyka.', 'Mode 1 nie wymaga nic od przełącznika.', 'Mode 1: jedno łącze aktywne, drugie zapasowe; działa z dowolnymi przełącznikami (także dwoma niezależnymi), bez konfiguracji; przepustowość jednego łącza; po awarii przełączenie w czasie miimon (np. 100 ms) + gratuitous ARP do aktualizacji tablic MAC. Mode 4: wszystkie łącza aktywne (rozkład hashem), wymaga LACP na przełączniku (lub parze MLAG); przy awarii natychmiastowe przeniesienie przepływów. Ryzyka: mode 1 — niewykorzystana przepustowość; mode 4 — zależność od poprawnej konfiguracji po obu stronach.'],
  ['Opisz, jak działa MLAG/vPC i jakie są typowe pułapki (peer-link, keepalive, split brain).', 'Dwa przełączniki udają jeden system LACP.', 'Para przełączników wymienia stan przez peer-link (synchronizacja tablic MAC, ruch, gdy jedna strona straciła łącze do hosta) i osobny keepalive (wykrywanie, czy partner żyje). Wobec hosta oba używają wspólnego System ID LACP, więc host widzi jednego partnera i tworzy jeden agregat. Pułapki: awaria peer-link przy działającym keepalive — urządzenie podrzędne wyłącza swoje porty MLAG (by uniknąć split brain); brak keepalive przez osobną ścieżkę — ryzyko, że oba uznają się za główne; niespójna konfiguracja VLAN-ów/portów po obu stronach; porty orphan (podłączone tylko do jednego przełącznika).'],
  ['Zaplanuj monitoring agregatów w środowisku produkcyjnym: jakie metryki i alerty skonfigurujesz?', 'Liczba aktywnych członków, rozkład ruchu, zdarzenia LACP.', 'Metryki: liczba aktywnych członków na kanał (alert przy < liczby skonfigurowanej — degradacja), wykorzystanie każdego członka osobno (alert przy dużej nierównowadze, np. > 80% na jednym przy < 30% na innych), błędy CRC na członkach, zdarzenia LACP (unbundle, timeout, zmiana partnera). Źródła: SNMP/gNMI (IF-MIB, LAG-MIB), syslog (%EC-5-UNBUNDLE, %LINK-3-UPDOWN), na serwerach node_exporter (bonding collector: node_bonding_active vs node_bonding_slaves).'],
  ['Wyjaśnij, czym jest L3 EtherChannel i kiedy go użyjesz zamiast trunku L2 lub routingu ECMP.', 'no switchport na Port-channel, adres IP.', 'L3 EtherChannel to kanał bez funkcji przełączania — Port-channel ma adres IP (no switchport) i działa jak jeden interfejs routowany. Użycie: połączenie dwóch przełączników L3 lub przełącznika z routerem/firewallem, gdy chcesz jednego sąsiedztwa routingu i jednej podsieci punkt-punkt z odpornością na awarię kabla. ECMP (wiele osobnych łączy L3) daje podobny efekt, ale wymaga wielu sąsiedztw i adresów; L2 trunk w kanale — gdy potrzebujesz przenieść VLAN-y.'],
  ['Analizujesz SPAN/TAP dla sensora IDS na łączu, które jest agregatem 2 × 10G. Co zrobisz, by sensor widział cały ruch, i jaki problem może się pojawić?', 'Ruch jednego przepływu na jednym łączu, ale różne przepływy na różnych.', 'Trzeba objąć wszystkie łącza członkowskie (lub interfejs Port-channel jako źródło SPAN, jeśli platforma pozwala) i zebrać je do sensora (agregujący TAP / packet broker). Monitorując tylko jedno łącze, sensor widzi część przepływów, a jeśli kierunki jednego połączenia idą różnymi kablami (niezależne hashe po obu stronach) — widzi tylko połowę rozmowy, co psuje rekonstrukcję sesji TCP i detekcję. Uwaga też na przepustowość portu SPAN (2 × 10G nie zmieści się w jednym porcie 10G).'],
  ['Zaimplementuj w Javie dydaktyczny wybór łącza dla przepływu (src/dst IP i port, liczba łączy) w wariantach src-dst-ip i l3+l4 i sprawdź rozkład 1000 losowych połączeń z jednego klienta do jednego serwera.', 'Użyj ostatnich oktetów i XOR; zlicz wyniki w tablicy.', 'static int hashIp(byte[] s, byte[] d, int n) { return ((s[3] & 0xff) ^ (d[3] & 0xff)) % n; }\nstatic int hashL4(byte[] s, byte[] d, int sp, int dp, int n) { return ((sp ^ dp) ^ ((s[3] & 0xff) ^ (d[3] & 0xff))) % n; }\n// 1000 połączeń z losowymi portami źródłowymi (49152–65535) do portu 5432:\n// src-dst-ip: wszystkie 1000 na jednym łączu (stałe adresy),\n// l3+l4: ok. 250 na każde z 4 łączy — dlatego dla puli połączeń do jednej bazy wybiera się hash z portami.'],
  ['Opisz, jak skonfigurujesz NIC teaming LACP w Windows Server i jak zweryfikujesz jego stan. Czym różni się SET w Hyper-V?', 'New-NetLbfoTeam, Get-NetLbfoTeam; SET = Switch Embedded Teaming.', 'New-NetLbfoTeam -Name Team1 -TeamMembers "NIC1","NIC2" -TeamingMode Lacp -LoadBalancingAlgorithm Dynamic; weryfikacja: Get-NetLbfoTeam, Get-NetLbfoTeamMember (OperationalStatus Active), liczniki w Menedżerze zadań. Port przełącznika: kanał LACP. SET (Hyper-V, Azure Stack HCI) wbudowuje teaming w wirtualny przełącznik, obsługuje RDMA i jest zalecany dla nowych wdrożeń Hyper-V; działa w trybie Switch Independent (bez LACP na przełączniku).'],
  ['Na przełączniku pojawił się log „%EC-5-CANNOT_BUNDLE2: Gi1/0/48 is not compatible with Gi1/0/47 and will be suspended (speed of Gi1/0/48 is 100M)”. Co się stało i jak naprawić?', 'Członkowie muszą mieć tę samą prędkość.', 'Port Gi1/0/48 wynegocjował 100 Mb/s (zły kabel, uszkodzona para, ustawienie speed na drugim końcu), więc nie może dołączyć do kanału z portem 1G — zostaje zawieszony; kanał działa na jednym łączu (brak redundancji). Naprawa: sprawdzić kabel (test cable-diagnostics tdr), ustawienia speed/duplex po obu stronach, wymienić patchcord/SFP; po naprawie port dołączy automatycznie (flaga P).'],
  ['Wyjaśnij, czym różni się wykrycie awarii łącza fizycznego od awarii „cichej” w LACP i jak lacp rate wpływa na czas reakcji.', 'Link down vs partner przestaje wysyłać LACPDU.', 'Utrata sygnału (kabel wyjęty) jest wykrywana natychmiast — port przechodzi w down i wypada z kanału. Awaria cicha (łącze fizycznie up, ale partner nie przetwarza ruchu, np. zawieszony proces, problem w konwerterze, łącze jednokierunkowe) wykrywana jest dopiero po braku LACPDU: rate slow — 3 × 30 s = 90 s, rate fast — 3 × 1 s = 3 s. W tym czasie część przepływów trafia w czarną dziurę. Dlatego w serwerowniach często lacp rate fast (plus UDLD/BFD).'],
];
for (const [q, h, a] of more) ex(q, h, a);
const extra = [
  ['Przygotuj listę kontrolną wdrożenia agregatu serwer–przełącznik (minimum 6 punktów).', 'Konfiguracja obu stron, okablowanie, testy.', '1) Tryb LACP active po stronie przełącznika (i 802.3ad na serwerze), nigdy on/balance-rr bez uzgodnienia. 2) Identyczne ustawienia członków: prędkość, VLAN-y, MTU. 3) Hash z portami (layer3+4 / src-dst-port), liczba łączy 2/4/8. 4) Przy dwóch przełącznikach — MLAG/vPC skonfigurowany i zweryfikowany. 5) PortFast/edge + BPDU Guard na Port-channel do serwera. 6) Testy: wyjęcie każdego kabla po kolei z ciągłym pingiem/ruchem aplikacji, iperf3 -P. 7) Monitoring: alert przy degradacji członków. 8) Dokumentacja: które porty serwera do których portów przełączników (LLDP — lekcja 09).'],
  ['Zastanów się, jak duży wpływ na aplikację Java z pulą 20 połączeń do jednej bazy ma wybór algorytmu hash na agregacie 2 × 10G przy src-dst-ip vs l3+l4.', 'Wszystkie połączenia mają te same adresy IP, różne porty źródłowe.', 'src-dst-ip: wszystkie 20 połączeń (te same IP klienta i bazy) trafia na jedno łącze — limit 10G dla całej puli, drugie łącze bezczynne (tylko redundancja). l3+l4: połączenia rozkładają się po portach źródłowych na oba łącza (statystycznie ok. 10/10) — pula może wykorzystać do 20G. Wniosek: dla ruchu typu „wiele połączeń między tymi samymi hostami” hash musi uwzględniać porty.'],
  ['Wyjaśnij, czym jest LACP System ID i do czego służy porównanie System ID partnera na każdym łączu.', 'Priorytet systemu + MAC; porty z różnymi partnerami nie mogą być w jednym agregatorze.', 'System ID = priorytet systemu LACP (2 bajty) + MAC systemu. Każdy LACPDU niesie System ID nadawcy i klucz; port może dołączyć do agregatora tylko z portami, które widzą tego samego partnera (System ID i klucz). Dzięki temu błędnie wpięty kabel do innego przełącznika zostanie wykryty (inny partner → osobny agregator, nieaktywny). W MLAG oba przełączniki pary ogłaszają ten sam wirtualny System ID.'],
];
for (const [q, h, a] of extra) ex(q, h, a);
for (const [n, conns] of [[4, [['10.0.0.11', '10.0.1.50'], ['10.0.0.12', '10.0.1.50'], ['10.0.0.13', '10.0.1.50'], ['10.0.0.14', '10.0.1.50']]], [2, [['192.168.1.2', '192.168.1.100'], ['192.168.1.4', '192.168.1.100'], ['192.168.1.6', '192.168.1.100'], ['192.168.1.8', '192.168.1.100']]], [4, [['10.5.0.1', '10.5.0.9'], ['10.5.0.2', '10.5.0.10'], ['10.5.0.3', '10.5.0.11'], ['10.5.0.4', '10.5.0.12']]]]) {
  const res = conns.map(([s2, d2]) => `${s2} → ${d2}: (${lastOct(s2)}^${lastOct(d2)})%${n} = łącze ${hashIp(s2, d2, n)}`);
  const used = new Set(conns.map(([s2, d2]) => hashIp(s2, d2, n)));
  ex(`Agregat ${n} łączy, model src-dst-ip. Wyznacz łącza dla przepływów: ${conns.map(([a, b]) => `${a} → ${b}`).join(', ')}. Ile łączy jest wykorzystanych i co to mówi o rozkładzie?`, 'Policz XOR ostatnich oktetów modulo liczba łączy dla każdego przepływu.', res.join('; ') + `. Wykorzystane łącza: ${used.size} z ${n}. ${used.size < n ? 'Rozkład nierówny — hash zależy od wzorca adresów, nie od obciążenia; pomóc może algorytm z portami.' : 'Rozkład równy dla tego zestawu adresów.'}`);
}
const extra2 = [
  ['Opisz różnicę między agregacją łączy (LAG) a ECMP na L3. Kiedy który mechanizm jest lepszy?', 'LAG = jeden interfejs L2/L3; ECMP = wiele tras o równym koszcie.', 'LAG łączy porty w jeden interfejs logiczny między dwoma urządzeniami (lub parą MLAG), działa niezależnie od routingu, przenosi VLAN-y; awaria członka jest niewidoczna dla wyższych warstw. ECMP to wiele równorzędnych tras L3 (różne łącza, często do różnych urządzeń) — router hashuje przepływy między next-hopy; skaluje się na wiele urządzeń (spine-leaf), nie wymaga L2 ani MLAG. LAG — do serwerów, hiperwizorów, połączeń, które muszą przenosić VLAN-y; ECMP — szkielet centrum danych i sieci L3.'],
  ['Wyjaśnij, dlaczego zmiana liczby członków agregatu (dodanie łącza) może chwilowo zmienić ścieżki wielu przepływów i jakie to ma skutki.', 'Hash mod N — zmienia się N.', 'Wybór łącza to hash mod N; po zmianie N (z 2 na 3 lub 4) większość przepływów dostaje nowe łącze. Przy przenoszeniu przepływu pakiety w locie na starym łączu mogą dotrzeć po pakietach z nowego — chwilowa zmiana kolejności i drobne retransmisje TCP. Zwykle niezauważalne, ale przy wrażliwych aplikacjach (VoIP, handel) zmiany robi się w oknie serwisowym. Niektóre platformy stosują „resilient hashing”, który minimalizuje przemieszanie przepływów.'],
];
extra2.push(['Wyjaśnij, czym jest LACP port priority i system priority oraz kiedy mają znaczenie.', 'Limit aktywnych portów i wybór, która strona decyduje.', 'System priority (z MAC) wyznacza, która strona agregatu decyduje o tym, które porty są aktywne, gdy nie wszystkie mogą być (niższa wartość = decydująca). Port priority określa, które porty zostaną aktywne przy limicie (np. 16 skonfigurowanych, 8 aktywnych — reszta w hot-standby); niższa wartość wygrywa. W typowych agregatach 2–4 portów nie trzeba ich zmieniać.']);
for (const [q, h, a] of extra2) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111808);
const negPairs = [['active', 'active'], ['active', 'passive'], ['passive', 'passive'], ['on', 'on'], ['on', 'active'], ['desirable', 'auto'], ['auto', 'auto'], ['desirable', 'desirable'], ['active', 'desirable'], ['passive', 'auto'], ['on', 'passive'], ['auto', 'desirable']];
const negOpts = ['Tak — kanał LACP', 'Tak — kanał PAgP', 'Tak — kanał statyczny', 'Nie — kanał nie powstanie', 'Nie — ryzyko pętli lub utraty ruchu'];
const negAns = (a, b) => { const r = negotiate(a, b); if (r.ok) return r.short.includes('LACP') ? negOpts[0] : r.short.includes('PAgP') ? negOpts[1] : negOpts[2]; return r.risk ? negOpts[4] : negOpts[3]; };
for (const [a, b] of negPairs) { const c = negAns(a, b); add(`Czy powstanie EtherChannel, gdy jedna strona ma tryb ${a}, a druga ${b}?`, c, negOpts.filter((x) => x !== c).slice(0, 3), negotiate(a, b).long + '.'); }
for (const [s, d, n] of [['10.0.0.11', '10.0.1.50', 4], ['10.0.0.12', '10.0.1.50', 4], ['192.168.1.10', '192.168.1.20', 2], ['192.168.1.10', '192.168.1.21', 2], ['172.16.0.7', '172.16.5.100', 8], ['10.1.1.255', '10.1.1.1', 4], ['10.9.9.64', '10.9.9.65', 8], ['10.2.2.3', '10.2.2.200', 4]]) {
  const h = hashIp(s, d, n);
  add(`Model dydaktyczny src-dst-ip: (ostatni oktet src XOR ostatni oktet dst) mod ${n}. Na które łącze trafi przepływ ${s} → ${d}?`, `łącze ${h}`, Array.from({ length: Math.max(n, 4) }, (_, i) => `łącze ${i}`).filter((x) => x !== `łącze ${h}`).slice(0, 3), `${lastOct(s)} XOR ${lastOct(d)} = ${lastOct(s) ^ lastOct(d)}; mod ${n} = ${h}.`);
}
for (const [s, d, sp, dp, n] of [['10.0.0.11', '10.0.1.50', 51000, 5432, 4], ['10.0.0.11', '10.0.1.50', 51001, 5432, 4], ['10.0.0.11', '10.0.1.50', 51002, 5432, 4], ['10.0.0.20', '10.0.1.30', 40000, 443, 2]]) {
  const h = hashL4(s, d, sp, dp, n);
  add(`Model dydaktyczny l3+l4: ((port src XOR port dst) XOR (oktet src XOR oktet dst)) mod ${n}. Łącze dla ${s}:${sp} → ${d}:${dp}?`, `łącze ${h}`, Array.from({ length: 4 }, (_, i) => `łącze ${i}`).filter((x) => x !== `łącze ${h}`).slice(0, 3), `(${sp} ^ ${dp}) ^ (${lastOct(s)} ^ ${lastOct(d)}) = ${(sp ^ dp) ^ (lastOct(s) ^ lastOct(d))}; mod ${n} = ${h}. Zmiana portu źródłowego może zmienić łącze.`);
}
for (const [links, sp] of [[2, 1], [4, 10], [2, 25], [8, 10], [4, 100]]) {
  add(`Jaką maksymalną przepustowość osiągnie pojedynczy przepływ TCP w agregacie ${links} × ${sp} Gb/s?`, `${sp} Gb/s`, [`${links * sp} Gb/s`, `${(links * sp) / 2} Gb/s` === `${sp} Gb/s` ? `${sp * 3} Gb/s` : `${(links * sp) / 2} Gb/s`, `${sp / 2} Gb/s`], 'Przepływ jest przypisany do jednego łącza.');
  add(`Jaka jest sumaryczna przepustowość agregatu ${links} × ${sp} Gb/s?`, `${links * sp} Gb/s`, [`${sp} Gb/s`, `${links * sp * 2} Gb/s`, `${links + sp} Gb/s`], 'Suma prędkości łączy członkowskich.');
}
const bAll = BOND.map(([n, name]) => `mode ${n} (${name})`);
for (const [n, name, d] of BOND) add(`Który tryb bondingu Linuksa działa tak: ${d}?`, `mode ${n} (${name})`, bAll.filter((x) => x !== `mode ${n} (${name})`).slice(0, 3), `mode ${n} = ${name}.`);
for (const [r, i, t] of [['fast', '1 s', '3 s'], ['slow', '30 s', '90 s']]) {
  add(`Co ile sekund wysyłane są LACPDU przy lacp rate ${r}?`, i, ['1 s', '30 s', '2 s', '10 s'].filter((x) => x !== i), `Rate ${r}: co ${i}, timeout ${t}.`);
  add(`Po jakim czasie bez LACPDU port wypada z kanału przy lacp rate ${r}?`, t, ['3 s', '90 s', '15 s', '60 s'].filter((x) => x !== t), 'Trzy pominięte LACPDU.');
}
const facts = [
  ['Jaki standard IEEE opisuje LACP?', '802.3ad (obecnie 802.1AX)', ['802.1Q', '802.1D', '802.1X'], '802.1Q to VLAN, 802.1D STP, 802.1X kontrola dostępu.'],
  ['Na jaki adres MAC wysyłane są LACPDU?', '01:80:c2:00:00:02', ['01:80:c2:00:00:00', 'ff:ff:ff:ff:ff:ff', '01:00:0c:cc:cc:cc'], 'Slow Protocols, EtherType 0x8809.'],
  ['Jaki EtherType mają ramki LACP?', '0x8809', ['0x8100', '0x88CC', '0x0806'], '0x88CC to LLDP.'],
  ['Który tryb LACP inicjuje negocjację?', 'active', ['passive', 'auto', 'on'], 'passive tylko odpowiada.'],
  ['Który protokół agregacji jest własnością Cisco?', 'PAgP', ['LACP', 'LLDP', 'MSTP'], 'Tryby desirable i auto.'],
  ['Dlaczego agregat przypisuje przepływ do jednego łącza zamiast rozdzielać pakiety?', 'Aby nie zmieniać kolejności pakietów w przepływie', ['Bo łącza mają różne adresy IP', 'Bo STP tego wymaga', 'Bo pakiety są szyfrowane'], 'Zmiana kolejności szkodzi TCP.'],
  ['Jak STP traktuje EtherChannel?', 'Jako jeden port logiczny', ['Blokuje wszystkie łącza poza jednym', 'Każde łącze jako osobny port root', 'Wyłącza się na kanale'], 'Awaria członka nie zmienia topologii.'],
  ['Co musi być identyczne na portach członkowskich kanału?', 'Prędkość, duplex, tryb (access/trunk), VLAN-y i native VLAN', ['Opisy portów', 'Numery portów', 'Adresy MAC'], 'Inaczej port jest zawieszany.'],
  ['Co oznacza flaga (P) przy porcie w show etherchannel summary?', 'Port jest w kanale (bundled)', ['Port jest wyłączony', 'Port jest w trybie passive', 'Port jest zablokowany przez STP'], '(I) stand-alone, (s) suspended, (D) down.'],
  ['Co oznacza (SU) przy Port-channel?', 'Kanał warstwy 2 w użyciu', ['Kanał zawieszony', 'Kanał bez członków', 'Kanał warstwy 3 wyłączony'], 'R oznaczałoby kanał L3.'],
  ['Który algorytm hash da najlepszy rozkład wielu połączeń między tymi samymi dwoma hostami?', 'Z portami warstwy 4 (src-dst-port / layer3+4)', ['src-mac', 'dst-mac', 'src-dst-ip'], 'Połączenia różnią się portami źródłowymi.'],
  ['Dlaczego hash src-dst-mac między dwoma routerami jest złym wyborem?', 'Bo wszystkie ramki mają te same MAC i trafiają na jedno łącze', ['Bo routery nie mają MAC', 'Bo MAC są szyfrowane', 'Bo powoduje pętle'], 'Wybierz hash z IP/portami.'],
  ['Dlaczego warto mieć 2, 4 lub 8 łączy w agregacie?', 'Bo wiele implementacji hasha równomiernie dzieli ruch tylko na potęgi dwójki', ['Bo inaczej LACP nie działa', 'Bo STP wymaga parzystej liczby', 'Bo kable są sprzedawane parami'], 'Przy 3 łączach rozkład bywa nierówny.'],
  ['Co daje min-links w kanale?', 'Wyłącza kanał, gdy liczba aktywnych łączy spadnie poniżej progu', ['Ogranicza liczbę VLAN-ów', 'Zmniejsza MTU', 'Wymusza tryb on'], 'Ruch może pójść inną, sprawniejszą ścieżką.'],
  ['Który tryb bondingu Linuksa nie wymaga konfiguracji przełącznika i daje tylko redundancję?', 'mode 1 (active-backup)', ['mode 4 (802.3ad)', 'mode 0 (balance-rr)', 'mode 2 (balance-xor)'], 'Jedno łącze aktywne.'],
  ['Jaki problem powoduje bond mode 0 podłączony do zwykłych portów przełącznika?', 'MAC flapping i zmiana kolejności pakietów TCP', ['Brak adresu IP', 'Wyłączenie STP', 'Szyfrowanie ruchu'], 'Wymaga kanału statycznego.'],
  ['Gdzie w Linuksie sprawdzisz stan LACP bondu?', '/proc/net/bonding/bond0', ['/etc/hosts', '/proc/net/arp', '/sys/class/net/bond0/address'], 'Partner MAC, Aggregator ID, rate.'],
  ['Co wskazuje Partner Mac Address 00:00:00:00:00:00 w bondzie 802.3ad?', 'Brak partnera LACP na tym łączu', ['Poprawną negocjację', 'Tryb active-backup', 'Szyfrowanie LACP'], 'Sprawdź konfigurację przełącznika.'],
  ['Czym jest MLAG/vPC?', 'Agregatem zakończonym na dwóch przełącznikach widocznych jako jeden partner LACP', ['Agregatem między dwoma portami jednego serwera bez przełącznika', 'Protokołem routingu', 'Odmianą STP'], 'Odporność na awarię całego przełącznika.'],
  ['Do czego służy peer-link w MLAG/vPC?', 'Do synchronizacji stanu i przenoszenia ruchu między przełącznikami pary', ['Do zasilania PoE', 'Do zarządzania przez SSH', 'Do łączenia z Internetem'], 'Keepalive idzie osobną ścieżką.'],
  ['Co zrobi LACP, gdy jeden kabel agregatu zostanie błędnie wpięty do innego przełącznika?', 'Wykryje innego partnera i nie włączy tego portu do agregatu', ['Utworzy pętlę', 'Wyłączy cały kanał', 'Nic nie zauważy'], 'W trybie on błąd nie zostałby wykryty.'],
  ['Czym jest L3 EtherChannel?', 'Kanałem z adresem IP działającym jak interfejs routowany', ['Kanałem tylko dla VLAN 3', 'Kanałem z trzema łączami', 'Kanałem z szyfrowaniem L3'], 'no switchport na Port-channel.'],
  ['Które polecenie Cisco pokaże partnera LACP na portach?', 'show lacp neighbor', ['show cdp neighbors', 'show mac address-table', 'show ip route'], 'System ID, klucz, flagi.'],
  ['Jak Windows nazywa agregację kart sieciowych?', 'NIC teaming (LBFO) lub SET w Hyper-V', ['Bonding', 'Port-channel', 'Bridge'], 'New-NetLbfoTeam.'],
  ['Jaki sposób na wykorzystanie agregatu przez aplikację jest właściwy?', 'Wiele równoległych połączeń (pula, równoległe strumienie)', ['Zwiększenie bufora jednego połączenia', 'Zmiana portu docelowego na 0', 'Wyłączenie Nagle’a'], 'Hash rozkłada przepływy, nie pakiety.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
const facts2 = [
  ['Ile maksymalnie aktywnych łączy obsługuje typowo EtherChannel na Catalyst (LACP)?', '8 aktywnych (plus do 8 w gotowości)', ['2', '4', '64'], 'LACP pozwala skonfigurować 16 portów, z czego 8 aktywnych.'],
  ['Co stanie się z ruchem po awarii jednego z czterech łączy agregatu?', 'Przepływy z tego łącza zostaną przeniesione na pozostałe, kanał działa dalej', ['Cały kanał przestanie działać', 'STP przebuduje drzewo', 'Wszystkie połączenia TCP zostaną zerwane'], 'Spada tylko sumaryczna przepustowość.'],
  ['Czy agregat może łączyć porty o różnych prędkościach (1G i 10G)?', 'Nie — członkowie muszą mieć tę samą prędkość', ['Tak, zawsze', 'Tak, jeśli to LACP', 'Tylko w trybie on'], 'Port o innej prędkości jest zawieszany.'],
  ['Który tryb bondingu jest odpowiednikiem kanału LACP po stronie serwera?', 'mode 4 (802.3ad)', ['mode 1 (active-backup)', 'mode 6 (balance-alb)', 'mode 3 (broadcast)'], 'Wymaga LACP na przełączniku.'],
  ['Jakie polecenie przypisuje port Cisco do kanału 1 w trybie LACP active?', 'channel-group 1 mode active', ['port-channel 1 lacp', 'lacp enable 1', 'switchport channel 1'], 'Konfiguracja na portach fizycznych.'],
  ['Gdzie konfiguruje się trunk i VLAN-y dla EtherChannelu w Cisco?', 'Na interfejsie Port-channel', ['Tylko na pierwszym porcie członkowskim', 'W trybie VLAN', 'Na interfejsie SVI'], 'Ustawienia propagują się do członków.'],
  ['Co oznacza flaga (s) przy porcie w show etherchannel summary?', 'Port zawieszony (suspended) z powodu niezgodności', ['Port w trybie standby LACP', 'Port szybki (fast)', 'Port bezpieczny'], 'Sprawdź prędkość, VLAN-y, tryb.'],
  ['Co oznacza flaga (I) przy porcie w show etherchannel summary?', 'Port działa samodzielnie (stand-alone), bez kanału', ['Port wyłączony administracyjnie', 'Port z błędami CRC', 'Port w trybie IPv6'], 'Zwykle brak partnera LACP.'],
  ['Dlaczego tryb on jest ryzykowny?', 'Bo nie wykrywa błędów okablowania ani braku kanału po drugiej stronie', ['Bo jest wolniejszy od LACP', 'Bo wymaga licencji', 'Bo wyłącza VLAN-y'], 'Zalecany LACP active.'],
  ['Jaki parametr Linuksa wybiera algorytm hash dla bondingu?', 'xmit_hash_policy', ['lacp_rate', 'miimon', 'primary'], 'layer2, layer2+3, layer3+4.'],
  ['Do czego służy parametr miimon w bondingu?', 'Do okresowego sprawdzania stanu łącza (np. co 100 ms)', ['Do ustawienia MTU', 'Do wyboru trybu LACP', 'Do szyfrowania'], 'Wykrywanie awarii łącza.'],
  ['Jaki jest domyślny xmit_hash_policy w bondingu Linuksa?', 'layer2', ['layer3+4', 'layer2+3', 'encap3+4'], 'XOR adresów MAC.'],
  ['Jak bond mode 6 (balance-alb) równoważy ruch przychodzący?', 'Manipulując odpowiedziami ARP, by różni klienci używali różnych MAC', ['Przez LACP', 'Przez STP', 'Przez DNS'], 'Nie wymaga konfiguracji przełącznika.'],
  ['Dlaczego test iperf3 z jednym strumieniem nie pokaże przepustowości całego agregatu?', 'Bo jeden strumień TCP jedzie jednym łączem', ['Bo iperf3 nie obsługuje agregatów', 'Bo agregat wyłącza TCP', 'Bo iperf3 wymaga UDP'], 'Użyj -P z wieloma strumieniami.'],
  ['Co jest typowym objawem nierównego hashowania?', 'Jedno łącze mocno obciążone, pozostałe prawie puste', ['Wszystkie łącza wyłączone', 'Błędy CRC', 'Zmiana root bridge'], 'Zmień algorytm lub zwiększ różnorodność przepływów.'],
  ['Kto oblicza hash dla ruchu w danym kierunku w agregacie?', 'Strona nadająca (każda strona niezależnie)', ['Zawsze przełącznik', 'Zawsze serwer', 'Root bridge'], 'Kierunki tego samego połączenia mogą iść różnymi łączami.'],
  ['Co oznacza „orphan port” w parze MLAG/vPC?', 'Port podłączony tylko do jednego przełącznika pary', ['Port bez VLAN-u', 'Port w trybie on', 'Port wyłączony przez BPDU Guard'], 'Nie ma redundancji przy awarii tego przełącznika.'],
  ['Co robi przełącznik podrzędny w vPC, gdy padnie peer-link, a keepalive działa?', 'Wyłącza swoje porty vPC, aby uniknąć split brain', ['Zostaje rootem STP', 'Restartuje się', 'Przełącza porty w tryb on'], 'Ruch idzie przez przełącznik główny.'],
  ['Który standard zastępuje rozwiązania MLAG różnych producentów w nowoczesnych sieciach?', 'EVPN multihoming (ESI)', ['STP', 'VTP', 'PAgP'], 'Oparty na BGP EVPN.'],
  ['Dlaczego sensor IDS podłączony do SPAN jednego członka agregatu może widzieć niepełne sesje?', 'Bo różne przepływy (a czasem kierunki) idą różnymi łączami', ['Bo LACP szyfruje ruch', 'Bo SPAN nie działa na agregatach', 'Bo członkowie nie mają MAC'], 'Monitoruj wszystkie członki lub Port-channel.'],
  ['Jaki log Cisco informuje o wyłączeniu portu z kanału?', '%EC-5-UNBUNDLE', ['%SYS-5-RESTART', '%DHCP-6-ADDRESS_ASSIGN', '%SPANTREE-5-ROOTCHANGE'], 'Warto alarmować w monitoringu.'],
  ['Co zapewnia lacp rate fast?', 'Szybsze wykrycie cichej awarii partnera (3 s zamiast 90 s)', ['Wyższą prędkość łącza', 'Szyfrowanie LACPDU', 'Lepszy rozkład hasha'], 'Utrata sygnału jest wykrywana natychmiast niezależnie od rate.'],
  ['Jaki jest główny cel agregacji z punktu widzenia triady CIA?', 'Dostępność', ['Poufność', 'Integralność', 'Rozliczalność'], 'Redundancja łączy i urządzeń.'],
  ['Co jest lepszym wyborem dla pojedynczego dużego transferu: 4 × 10G w agregacie czy 1 × 40G?', '1 × 40G', ['4 × 10G', 'Bez różnicy', '2 × 10G'], 'Pojedynczy przepływ ograniczony prędkością jednego łącza.'],
  ['Jak nazywa się agregat w Juniper Junos?', 'ae (aggregated Ethernet)', ['Port-channel', 'bond', 'team'], 'np. ae0.'],
  ['Co trzeba skonfigurować na porcie przełącznika podłączonym do serwera z bondem active-backup?', 'Nic szczególnego — zwykłe porty, bez kanału', ['Kanał LACP', 'Kanał on', 'PAgP desirable'], 'Tylko jedno łącze nadaje naraz.'],
  ['Co wysyła bond active-backup po przełączeniu na zapasowe łącze?', 'Gratuitous ARP, aby przełączniki zaktualizowały tablice MAC', ['BPDU', 'LACPDU', 'IGMP join'], 'Ruch szybko trafia na nowy port.'],
  ['Czy LACP pozwala połączyć porty z dwóch niezależnych przełączników (bez MLAG/stosu) w jeden aktywny agregat?', 'Nie — porty widzą różnych partnerów i trafią do różnych agregatorów', ['Tak, zawsze', 'Tak, w trybie passive', 'Tak, jeśli są w tym samym VLAN-ie'], 'Potrzebny MLAG/vPC lub stos.'],
  ['Jakie polecenie Cisco zmienia algorytm rozkładu ruchu w EtherChannelu?', 'port-channel load-balance src-dst-ip', ['etherchannel hash ip', 'lacp load-balance', 'channel-group hash'], 'Zwykle globalnie.'],
  ['Co jest zaletą LACP nad PAgP?', 'Jest standardem IEEE działającym między różnymi producentami', ['Jest szybszy w transmisji danych', 'Nie wymaga konfiguracji', 'Szyfruje ruch'], 'PAgP działa tylko z Cisco.'],
];
for (const [q, a, w, e] of facts2) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
