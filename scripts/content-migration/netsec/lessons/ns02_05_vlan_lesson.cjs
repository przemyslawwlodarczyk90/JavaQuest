// Generuje lekcje _ns_02_ethernet_lan/05_VlanTrunkingAndStp.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_05_vlan_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/05_VlanTrunkingAndStp.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const h4 = (n) => '0x' + n.toString(16).toUpperCase().padStart(4, '0');
const tci = (pcp, dei, vid) => (pcp << 13) | (dei << 12) | vid;
const parse = (t) => ({ pcp: t >> 13, dei: (t >> 12) & 1, vid: t & 0xfff });
const bin16 = (t) => t.toString(2).padStart(16, '0').match(/..../g).join(' ');
// Bridge ID w PVST+/Rapid PVST+: priorytet (wielokrotnosc 4096) + numer VLAN (system ID extension)
const bid = (prio, vlan) => prio + vlan;
// Wybor root bridge: najnizszy (priorytet+VLAN), przy remisie najnizszy MAC
const macNum = (m) => parseInt(m.replace(/[:.]/g, ''), 16);
const root = (sws, vlan) => [...sws].sort((a, b) => (bid(a[1], vlan) - bid(b[1], vlan)) || (macNum(a[2]) - macNum(b[2])))[0][0];

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Jeden kabel, wiele sieci: po co VLAN-y', ...img('ns02-05-vlan-logical.svg', 'Po lewej fizyczny przełącznik SW1 z czterema komputerami: PC-A i PC-B w VLAN 10, PC-C i PC-D w VLAN 20. Po prawej ten sam układ logicznie: dwie oddzielne sieci 192.168.10.0/24 i 192.168.20.0/24. Broadcast z PC-A dociera tylko do PC-B; ruch między VLAN-ami wymaga routera.'),
    body:
`W lekcji 02 przełącznik był jedną **domeną rozgłoszeniową**: broadcast z dowolnego portu trafiał na wszystkie pozostałe. W lekcji 04 zobaczyłeś, ile kosztuje broadcast i jak protokoły rozgłoszeniowe (LLMNR, mDNS) ujawniają informacje każdemu w segmencie. Gdyby firma z 2000 stanowisk, drukarkami, kamerami i serwerami miała jedną płaską sieć L2, każdy host widziałby ruch rozgłoszeniowy wszystkich, a zainfekowany laptop mógłby bezpośrednio rozmawiać z kontrolerem domeny i bazą danych.

**VLAN (Virtual LAN, IEEE 802.1Q)** dzieli jeden fizyczny przełącznik (lub ich grupę) na kilka **logicznych** przełączników. Każdy VLAN to osobna domena rozgłoszeniowa i — w dobrym projekcie — osobna podsieć IP. Między VLAN-ami ruch przechodzi wyłącznie przez router lub przełącznik L3, a tam możesz postawić **firewall i reguły** — to fundament segmentacji sieci, o której będziemy mówić w całej części o cyberbezpieczeństwie.

Ale VLAN-y to dopiero połowa historii. Żeby przenieść kilka VLAN-ów jednym kablem między przełącznikami, potrzebny jest **trunk** ze znacznikami 802.1Q. A gdy dla niezawodności łączysz przełączniki nadmiarowo, powstają **pętle L2**, które bez **STP (Spanning Tree Protocol)** w kilka sekund kładą całą sieć.

Dla Java developera to codzienność, nawet jeśli tego nie widzi: sieci Dockera to bridge’e, sieci Kubernetesa z Multusem i macvlan używają VLAN-ów, VPC w chmurze to „VLAN na sterydach”, a zasada „baza danych w osobnym segmencie” to właśnie VLAN + ACL. Ta lekcja odpowiada celom Network+ (D2: VLAN, trunking, STP) i CCNA v2 (D2: konfiguracja i weryfikacja VLAN-ów, trunków 802.1Q, native VLAN, podstawy STP).` },

  { type: 'DEFINITION', heading: 'Słownik: VLAN, access, trunk, native, STP', body:
`- **VLAN** — logiczna domena rozgłoszeniowa wydzielona na przełącznikach; identyfikowana numerem **VID** (1–4094). VLAN 1 to domyślny VLAN wszystkich portów na większości przełączników.
- **Port access** — port należący do **jednego** VLAN-u; host wysyła i odbiera ramki **bez znacznika**, przełącznik sam przypisuje je do VLAN-u portu.
- **Port trunk** — port przenoszący **wiele** VLAN-ów; ramki mają znacznik **802.1Q** (4 bajty: TPID 0x8100 + PCP + DEI + VID). Łączy przełączniki, przełącznik z routerem („router on a stick”), z hiperwizorem lub firewallem.
- **Native VLAN** — VLAN, którego ramki na trunku idą **bez znacznika** (domyślnie VLAN 1). Obie strony trunku muszą mieć ten sam native VLAN.
- **Allowed VLANs** — lista VLAN-ów dozwolonych na trunku (domyślnie wszystkie — zła praktyka).
- **Voice VLAN** — dodatkowy VLAN na porcie access dla telefonu IP (ramki telefonu ze znacznikiem, komputer za telefonem bez).
- **SVI (Switched Virtual Interface)** — wirtualny interfejs L3 przełącznika w danym VLAN-ie (\`interface vlan 10\`), brama domyślna podsieci; routing między VLAN-ami na przełączniku L3.
- **Router on a stick** — routing między VLAN-ami przez jeden fizyczny port routera z podinterfejsami (\`Gi0/0.10\`, \`Gi0/0.20\`).
- **DTP (Dynamic Trunking Protocol)** — protokół Cisco negocjujący trunk; wyłączany ze względów bezpieczeństwa.
- **QinQ (802.1ad)** — podwójne znakowanie (S-tag 0x88A8 + C-tag 0x8100), używane przez operatorów.
- **Pętla L2** — więcej niż jedna ścieżka między przełącznikami w tym samym VLAN-ie; Ethernet nie ma TTL, więc ramki krążą w nieskończoność.
- **Burza rozgłoszeniowa (broadcast storm)** — lawinowe powielanie broadcastów w pętli, wysycające łącza i CPU.
- **STP (802.1D) / RSTP (802.1w) / MST (802.1s)** — protokoły budujące z topologii z pętlami **drzewo** przez blokowanie wybranych portów. **Root bridge** — przełącznik o najniższym **Bridge ID** (priorytet + numer VLAN + MAC).` },

  { type: 'ANALOGY', heading: 'Biurowiec z kolorowymi piętrami i jedną windą towarową', body:
`Wyobraź sobie biurowiec, w którym każdy dział ma **swoje kolorowe drzwi i korytarze** — księgowość niebieskie, IT zielone, goście żółte. Ludzie z niebieskiego korytarza słyszą tylko ogłoszenia z niebieskich głośników (domena rozgłoszeniowa). To są **VLAN-y**.
- **Port access** to drzwi do pokoju: pracownik nie musi wiedzieć, jakiego koloru jest korytarz — po prostu wchodzi do swojego.
- **Trunk** to **winda towarowa** między budynkami: przewozi paczki wszystkich działów, ale każda ma **naklejkę z kolorem** (znacznik 802.1Q), żeby na drugim końcu trafiła do właściwego korytarza.
- **Native VLAN** to paczki **bez naklejki** — na drugim końcu trafiają do „domyślnego” działu. Jeśli w dwóch budynkach domyślny dział jest inny (native VLAN mismatch), paczki lądują nie tam, gdzie trzeba.
- **Router między VLAN-ami** to **recepcja**: żeby przejść z niebieskiego do zielonego korytarza, musisz przejść przez recepcję, która sprawdza przepustkę (ACL/firewall).
- **Pętla L2** to sytuacja, w której dwa przejścia między budynkami tworzą okrąg, a goniec z ogłoszeniem do wszystkich biega w kółko, w każdym przejściu klonując się — po minucie tysiące gońców blokują korytarze. **STP** to ochrona, która zamyka jedno z przejść „na kłódkę” i otwiera je tylko wtedy, gdy główne się zawali.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Znacznik 802.1Q bit po bicie', ...img('ns02-05-dot1q.svg', 'Ramka Ethernet z 4-bajtowym znacznikiem 802.1Q między adresem źródłowym a polem EtherType. Znacznik: TPID 0x8100 (16 bitów), PCP (3 bity priorytetu), DEI (1 bit), VID (12 bitów numeru VLAN). Przykład: TCI 0xA014 to PCP 5, DEI 0, VID 20; ramka z tagiem ma maksymalnie 1522 bajty, a użytecznych numerów VLAN jest 4094.'),
    body:
`Znacznik ma 4 bajty: **TPID** (Tag Protocol Identifier, zawsze \`0x8100\` dla 802.1Q — stoi w miejscu, gdzie normalnie byłby EtherType, więc odbiorca od razu wie, że ramka jest znakowana) i **TCI** (Tag Control Information, 16 bitów): **PCP** 3 bity (priorytet 0–7, QoS 802.1p), **DEI** 1 bit (ramka może być odrzucona przy przeciążeniu), **VID** 12 bitów (0–4095; 0 = tylko priorytet, 4095 zarezerwowany → **4094** użyteczne VLAN-y).

Dekodowanie TCI z heksadecymalnego zrzutu (np. z Wiresharka lub \`tcpdump -e -xx\`):`,
    code:
[[5, 0, 20], [0, 0, 10], [3, 0, 100], [6, 0, 1], [0, 1, 4094], [7, 0, 999]].map(([p, d, v]) => { const t = tci(p, d, v); return `TCI ${h4(t)} = ${bin16(t)} → PCP=${p} DEI=${d} VID=${v}`; }).join('\n') + `

Bajty na łączu (początek ramki znakowanej):
ff ff ff ff ff ff  52 54 00 12 34 56  81 00  a0 14  08 06 ...
└── MAC docelowy ─┘└── MAC źródłowy ─┘└TPID┘└TCI─┘└EtherType ARP

Rozmiary: ramka bez tagu maks. 1518 B → z tagiem 1522 B → QinQ (dwa tagi) 1526 B.
MTU IP (1500) się nie zmienia — tag jest „ponad” MTU, ale stare urządzenia mogą go odrzucić jako „baby giant”.` },

  { type: 'CODE_BASIC', heading: 'VLAN-y na Linuksie: interfejsy znakowane i bridge z vlan_filtering', body:
`Linux potrafi wszystko, co przełącznik: tworzyć podinterfejsy VLAN (jak router on a stick) i działać jako przełącznik z VLAN-ami (bridge z \`vlan_filtering\`). Tak działa Proxmox, wiele routerów domowych i sieci Kubernetesa z macvlan/Multus.`,
    code:
`# 1) Podinterfejs znakowany (host/serwer na porcie trunk)
ip link add link eth0 name eth0.10 type vlan id 10
ip addr add 192.168.10.5/24 dev eth0.10
ip link set eth0.10 up
ip -d link show eth0.10              # "vlan protocol 802.1Q id 10"

# 2) Bridge jako przełącznik z VLAN-ami
ip link add br0 type bridge vlan_filtering 1
ip link set veth-a master br0; ip link set veth-b master br0; ip link set veth-c master br0
bridge vlan add dev veth-a vid 10 pvid untagged   # port access VLAN 10
bridge vlan add dev veth-b vid 10 pvid untagged   # port access VLAN 10
bridge vlan add dev veth-c vid 20 pvid untagged   # port access VLAN 20
bridge vlan del dev veth-a vid 1                  # usuń domyślny VLAN 1 z portów access
bridge vlan show                                  # tabela port → VLAN (PVID = native/untagged)

# 3) Port trunk na bridge'u (np. do drugiego przełącznika lub VM-ki)
bridge vlan add dev uplink vid 10                 # znakowany VLAN 10
bridge vlan add dev uplink vid 20                 # znakowany VLAN 20

# Podgląd znaczników w ruchu
tcpdump -i eth0 -e -nn vlan                       # -e pokazuje nagłówek L2, "vlan 10, p 0"` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab: dwa VLAN-y na bridge’u Linuksa — sprawdź izolację', ...img('ns02-05-trunk.svg', 'Dwa przełączniki SW1 i SW2 połączone trunkiem 802.1Q z ramkami VLAN 10 i 20 ze znacznikami oraz ramkami bez znacznika w native VLAN; do każdego przełącznika podłączone są komputery na portach access w VLAN 10 i VLAN 20. Host na porcie access nie widzi znacznika — dodaje i zdejmuje go przełącznik.'),
    body:
`Laboratorium z planu lekcji — na maszynie wirtualnej z Linuksem (root), bez wpływu na inne sieci:
1. **Utwórz trzy namespace’y** \`ns-a\`, \`ns-b\`, \`ns-c\` i trzy pary veth; jeden koniec każdej pary przenieś do namespace’u, drugi zostaw w hoście (\`veth-a\`, \`veth-b\`, \`veth-c\`).
2. **Utwórz bridge** \`br0\` z \`vlan_filtering 1\`, podłącz do niego trzy końce veth i podnieś interfejsy.
3. **Nadaj adresy z jednej podsieci** wszystkim trzem namespace’om: 10.0.0.1/24, 10.0.0.2/24, 10.0.0.3/24 — celowo ta sama podsieć, żeby udowodnić, że izoluje **VLAN**, a nie adresacja.
4. **Bez VLAN-ów** (wszystkie porty w domyślnym PVID 1): \`ip netns exec ns-a ping -c2 10.0.0.3\` — działa.
5. **Przypisz VLAN-y**: \`veth-a\` i \`veth-b\` → VID 10 pvid untagged, \`veth-c\` → VID 20 pvid untagged; usuń VID 1 z portów.
6. **Test izolacji**: ping z ns-a do ns-b działa, z ns-a do ns-c — nie. \`ip netns exec ns-a ip neigh\` pokaże dla 10.0.0.3 stan FAILED/INCOMPLETE: zapytanie ARP (broadcast) nie dotarło do innego VLAN-u.
7. **Przechwyć**: \`tcpdump -i br0 -e -nn\` (na bridge’u) oraz \`tcpdump -i veth-c -e\` — ns-c nie widzi ARP od ns-a.
8. **Trunk**: dodaj czwarty namespace „router” z podinterfejsami \`eth0.10\` i \`eth0.20\` (adresy z dwóch **różnych** podsieci), port bridge’a ustaw jako trunk (VID 10 i 20 tagged), włącz \`net.ipv4.ip_forward=1\` — teraz ruch między VLAN-ami przechodzi przez „router”, gdzie możesz dodać reguły nftables.
9. **Wniosek**: zapisz, który element zapewnia izolację (bridge z vlan_filtering), a który kontrolę dostępu między VLAN-ami (reguły na routerze). Posprzątaj: \`ip netns del ...\`, \`ip link del br0\`.` },

  { type: 'NOTE', heading: 'Konfiguracja na przełączniku Cisco (zapowiedź lekcji 06)', body:
`Tak wyglądają te same pojęcia w Cisco IOS — składnię CLI omówimy dokładnie w następnej lekcji. Zwróć uwagę na polecenia bezpieczeństwa: wyłączenie DTP, zmiana native VLAN, ograniczenie allowed VLANs.`,
    code:
`vlan 10
 name USERS
vlan 20
 name SERVERS
vlan 999
 name NATIVE-UNUSED
!
interface GigabitEthernet1/0/5
 description PC biurowy
 switchport mode access              ! stały access — bez negocjacji DTP
 switchport access vlan 10
 switchport voice vlan 30            ! telefon IP za komputerem
 switchport nonegotiate
 spanning-tree portfast              ! port brzegowy: od razu forwarding
 spanning-tree bpduguard enable      ! wyłącz port, jeśli pojawi się BPDU
!
interface GigabitEthernet1/0/48
 description trunk do SW2
 switchport mode trunk
 switchport trunk native vlan 999    ! native ≠ 1 i nieużywany przez hosty
 switchport trunk allowed vlan 10,20,30
 switchport nonegotiate
!
show vlan brief                       ! VLAN → porty access
show interfaces trunk                 ! tryb, native, allowed, aktywne VLAN-y
show spanning-tree vlan 10            ! root bridge, role i stany portów` },

  { type: 'CODE_PRACTICAL', heading: 'STP w zarysie: kto zostaje rootem i dlaczego', ...img('ns02-05-stp-loop.svg', 'Dwa trójkąty z przełącznikami SW1, SW2, SW3. Po lewej bez STP ramki krążą w pętli i mnożą się (Ethernet nie ma TTL, tablice MAC skaczą). Po prawej z STP: SW1 jest root bridge, port SW3 w stronę SW2 jest zablokowany jako alternate, więc topologia jest drzewem, a przy awarii łącza STP go odblokuje.'),
    body:
`STP działa w trzech krokach: (1) **wybór root bridge** — przełącznika o najniższym Bridge ID; (2) każdy inny przełącznik wybiera **root port** — port o najniższym koszcie ścieżki do roota; (3) na każdym segmencie jeden **designated port**; pozostałe porty są **blokowane**. Przełączniki wymieniają się ramkami **BPDU** (multicast \`01:80:c2:00:00:00\`) co 2 sekundy.

**Bridge ID** = priorytet (domyślnie 32768, zmiana co 4096) + **system ID extension** (numer VLAN, w PVST+ osobne drzewo na VLAN) + MAC. Wygrywa **najniższy** — przy równych priorytetach decyduje najniższy MAC, czyli zwykle **najstarszy** przełącznik w sieci. Dlatego root ustawia się ręcznie na przełączniku rdzeniowym. Przykłady:`,
    code:
(() => {
  const sws = [['SW-CORE', 24576, '00:1a:2b:00:00:10'], ['SW-ACC1', 32768, '00:0c:29:aa:00:01'], ['SW-ACC2', 32768, '00:0c:29:aa:00:02'], ['SW-OLD', 32768, '00:00:0c:11:22:33']];
  const rows = sws.map(([n, p, m]) => `${n.padEnd(8)} priorytet ${String(p).padStart(5)}  BID w VLAN 10 = ${String(bid(p, 10)).padStart(5)}.${m}`);
  const noCore = sws.slice(1);
  return rows.join('\n') + `\n\nRoot w VLAN 10: ${root(sws, 10)} (najniższy priorytet).\nBez SW-CORE root to ${root(noCore, 10)} — równe priorytety, wygrywa najniższy MAC (stary sprzęt!).\n\nDomyślne koszty portów (802.1D-2004 / „krótkie” Cisco): 10 Mb/s = 2 000 000 / 100, 100 Mb/s = 200 000 / 19,\n1 Gb/s = 20 000 / 4, 10 Gb/s = 2 000 / 2.\nStany portu w 802.1D: blocking → listening (15 s) → learning (15 s) → forwarding — do 50 s przerwy;\nRSTP (802.1w) zbiega w ~1–2 s. Szczegóły: lekcja 07.\n\n# Linux: STP na bridge'u\nip link set br0 type bridge stp_state 1\nbridge -d link show        # state forwarding/blocking dla portów`;
})() },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **Segmentacja środowisk** — prod, test, zarządzanie (iLO/iDRAC, przełączniki), kopie zapasowe, VoIP, goście, IoT w osobnych VLAN-ach z osobnymi podsieciami; Twoja aplikacja w VLAN-ie serwerów rozmawia z bazą w VLAN-ie baz przez firewall — stąd „otwórz port 5432 z podsieci 10.20.0.0/24”.
- **Wirtualizacja** — hiperwizor (VMware vSwitch, Proxmox, Hyper-V) ma trunk do przełącznika, a maszyny wirtualne dostają VLAN-y jako „port groups”. Nieprawidłowy VLAN na karcie VM to klasyczny powód „VM nie ma sieci”.
- **Kontenery** — sterownik Dockera \`macvlan\` z \`parent=eth0.10\` wpuszcza kontener bezpośrednio do VLAN-u 10; w Kubernetesie Multus + macvlan/ipvlan robi to samo dla podów (np. sieci telekomunikacyjne, przemysłowe).
- **Chmura** — VPC i podsieci to abstrakcja realizowana przez overlay (VXLAN/Geneve, 24-bitowy identyfikator — 16 mln segmentów zamiast 4094). Security Groups grają rolę ACL między segmentami.
- **Awarie** — „aplikacja traci połączenia na 30–50 sekund po wymianie kabla” to klasyczny objaw klasycznego STP bez PortFast lub zmiany topologii STP; RSTP i PortFast skracają to do sekund.

**Blue team:**
- **Segmentacja** to jedno z najskuteczniejszych zabezpieczeń przed ruchem bocznym (lateral movement) i ransomware — zainfekowana stacja w VLAN-ie użytkowników nie powinna mieć bezpośredniego dostępu do VLAN-u zarządzania czy kopii zapasowych.
- **VLAN hopping** (MITRE ATT&CK nie wyróżnia osobnej techniki; to klasyczny atak L2): *switch spoofing* (host udaje przełącznik przez DTP i dostaje trunk) oraz *double tagging* (ramka z dwoma znacznikami, gdy napastnik jest w native VLAN). Obrona: \`switchport mode access\` + \`nonegotiate\`, native VLAN nieużywany i ≠ 1, \`vlan dot1q tag native\`. Szczegóły w części o bezpieczeństwie sieci.
- **Ataki na STP** — podłączenie urządzenia ogłaszającego BPDU z niskim priorytetem przejmuje rolę roota; obrona: **BPDU Guard** na portach brzegowych, **Root Guard** na portach w stronę dostępu.
- **Monitoring** — logi zmian topologii STP (TCN), alerty o przekroczeniu storm control, zmiany konfiguracji VLAN-ów w syslogu/SIEM.` },

  { type: 'PITFALL', heading: 'Częste błędy z VLAN-ami, trunkami i STP', body:
`- **„VLAN = bezpieczeństwo”** — VLAN rozdziela domeny rozgłoszeniowe, ale **nie filtruje** ruchu routowanego. Jeśli przełącznik L3 ma SVI we wszystkich VLAN-ach i nie ma ACL, wszystko rozmawia ze wszystkim. Segmentacja = VLAN **+** reguły na granicy.
- **Native VLAN mismatch** — różne native VLAN po dwóch stronach trunku: ramki bez tagu przeskakują między VLAN-ami, CDP/LLDP zgłasza błąd, STP może blokować port. Zawsze ustawiaj ten sam, nieużywany native VLAN.
- **Allowed VLANs „all”** — trunk przenosi wszystkie VLAN-y, także te, które nie powinny docierać do danego przełącznika czy hiperwizora; rozlewa broadcast i zwiększa powierzchnię ataku.
- **\`switchport trunk allowed vlan 30\` zamiast \`... add 30\`** — klasyczny błąd: polecenie **zastępuje** listę, a nie dopisuje; trunk nagle przenosi tylko VLAN 30 i połowa firmy traci sieć.
- **Pozostawione DTP** (\`dynamic auto/desirable\`) na portach użytkowników — umożliwia switch spoofing.
- **Wyłączenie STP „bo spowalnia”** — pierwsza pętla (ktoś wepnie kabel między dwoma gniazdkami) kładzie sieć w kilka sekund. Zamiast tego: RSTP, PortFast + BPDU Guard na portach brzegowych.
- **Root bridge wybrany przez przypadek** — przy domyślnych priorytetach rootem zostaje przełącznik z najniższym MAC, często najstarszy w szafie na końcu budynku; ruch płynie nieoptymalnie.
- **Ta sama podsieć w dwóch VLAN-ach** — technicznie możliwe (lab powyżej), ale w produkcji prowadzi do nieosiągalnych hostów i trudnych błędów; zasada: **1 VLAN = 1 podsieć IP**.
- **Zapomniany tag na serwerze** — interfejs serwera skonfigurowany jako znakowany (\`eth0.10\`), a port przełącznika jako access (lub odwrotnie): łącze jest „up”, ale nic nie działa.` },

  { type: 'CODE_WRONG', heading: 'Źle: płaska sieć i trunk z domyślnymi ustawieniami', body:
`Konfiguracja „żeby działało”: wszystkie urządzenia w VLAN-ie 1, porty w trybie dynamicznym, trunk z native VLAN 1 i wszystkimi VLAN-ami, STP z domyślnymi priorytetami.`,
    code:
`! każdy port: domyślnie VLAN 1, DTP dynamic auto
interface range GigabitEthernet1/0/1 - 46
 ! (brak konfiguracji — porty mogą wynegocjować trunk z podłączonym urządzeniem)
!
interface GigabitEthernet1/0/48
 switchport mode trunk          ! native VLAN 1, allowed: 1-4094
!
interface vlan 1
 ip address 192.168.1.2 255.255.255.0   ! zarządzanie przełącznikiem w VLAN-ie użytkowników
!
! Skutki:
! - laptop gościa, drukarka, kamera, serwer bazy i interfejs zarządzania w jednej domenie L2,
! - host z DTP może wynegocjować trunk i dostać się do wszystkich VLAN-ów,
! - napastnik w VLAN 1 (native) może próbować double taggingu,
! - root bridge = przełącznik z najniższym MAC, BPDU z dowolnego portu przyjmowane.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: segmentacja, twarde porty i chroniony STP', body:
`Każda strefa w osobnym VLAN-ie i podsieci, porty użytkowników na sztywno access, trunk z ograniczoną listą i nieużywanym native VLAN, root ustawiony świadomie, porty brzegowe chronione BPDU Guard, zarządzanie w osobnym VLAN-ie.`,
    code:
`vlan 10 ; name USERS          ! 10.10.0.0/24
vlan 20 ; name SERVERS        ! 10.20.0.0/24
vlan 30 ; name VOICE
vlan 99 ; name MGMT           ! 10.99.0.0/24 — tylko z jump hosta
vlan 999; name NATIVE-UNUSED  ! brak hostów
!
spanning-tree mode rapid-pvst
spanning-tree vlan 10,20,30,99 root primary   ! na przełączniku rdzeniowym (priorytet 24576)
!
interface range GigabitEthernet1/0/1 - 46
 switchport mode access
 switchport access vlan 10
 switchport nonegotiate
 spanning-tree portfast
 spanning-tree bpduguard enable
!
interface range GigabitEthernet1/0/47 - 48
 switchport mode trunk
 switchport trunk native vlan 999
 switchport trunk allowed vlan 10,20,30,99
 switchport nonegotiate
!
vlan dot1q tag native          ! znakuj także native VLAN (utrudnia double tagging)
interface vlan 1
 shutdown
interface vlan 99
 ip address 10.99.0.2 255.255.255.0
! Ruch między VLAN-ami → firewall/ACL na routerze: USERS → SERVERS tylko 443, MGMT tylko z jump hosta.` },

  { type: 'WHEN_TO_USE', heading: 'Jak projektować VLAN-y: praktyczne zasady', body:
`- **Dziel według funkcji i poziomu zaufania**, nie według pięter: użytkownicy, serwery, bazy, zarządzanie, VoIP, drukarki, IoT/OT, goście, DMZ, kopie zapasowe. Każda strefa = VLAN + podsieć + reguły na granicy.
- **1 VLAN = 1 podsieć IP**; numer VLAN-u warto powiązać z adresacją (VLAN 20 → 10.20.0.0/24) — ułatwia diagnozę.
- **VLAN-y lokalne zamiast rozciągniętych** — nie ciągnij jednego VLAN-u przez cały kampus czy kilka serwerowni; duże domeny L2 to duże burze i duże awarie. Nowoczesne sieci centrów danych: routing do przełącznika dostępowego (L3 do szafy) i VXLAN/EVPN, gdy L2 jest naprawdę potrzebne.
- **Trunk tylko tam, gdzie trzeba** (przełącznik–przełącznik, hiperwizor, firewall), z jawną listą allowed VLANs i nieużywanym native VLAN.
- **STP zawsze włączony** (RSTP lub MST), root ustawiony ręcznie, PortFast + BPDU Guard na portach brzegowych.
- **Mikrosegmentacja** — gdy VLAN-ów jest za mało lub segmentacja ma iść w głąb (pojedyncze aplikacje, pody): firewalle hostowe, NetworkPolicy w Kubernetesie, Security Groups w chmurze, 802.1X z dynamicznym przypisaniem VLAN-u (NAC, rozdział 13).
- **Developer w praktyce**: znaj VLAN i podsieć swojego serwera, wiedz, przez jaki firewall przechodzi ruch do bazy, i nie proś o „otwarcie wszystkiego między VLAN-ami” — proś o konkretny port, źródło i cel.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- VLAN dzieli przełącznik na logiczne domeny rozgłoszeniowe; zasada: 1 VLAN = 1 podsieć IP, ruch między VLAN-ami przez router/L3 z ACL lub firewallem.
- Port access: jeden VLAN, ramki bez tagu; port trunk: wiele VLAN-ów, znaczniki 802.1Q; native VLAN bez tagu (domyślnie 1 — zmień na nieużywany).
- Znacznik 802.1Q: 4 B = TPID 0x8100 + TCI (PCP 3 b, DEI 1 b, VID 12 b); 4094 VLAN-y; ramka maks. 1522 B; QinQ 0x88A8.
- Linux: \`ip link ... type vlan id N\`, bridge z \`vlan_filtering\` i \`bridge vlan\`; podgląd tagów \`tcpdump -e vlan\`.
- Pętla L2 bez STP = burza rozgłoszeniowa (Ethernet nie ma TTL). STP wybiera root bridge (najniższy priorytet + VLAN + MAC) i blokuje nadmiarowe porty; 802.1D zbiega do 50 s, RSTP w sekundy.
- Bezpieczeństwo: VLAN to nie firewall; wyłącz DTP, native VLAN ≠ 1, ogranicz allowed VLANs, BPDU Guard i Root Guard, zarządzanie w osobnym VLAN-ie (VLAN hopping i ataki STP — w części o bezpieczeństwie sieci).
- Następna lekcja: **CLI urządzeń sieciowych — Cisco IOS w praktyce**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const t of [0xA014, 0x2064, 0x1FFE, 0xE3E7]) {
  const p = parse(t);
  ex(`Zdekoduj pole TCI znacznika 802.1Q o wartości ${h4(t)}: podaj PCP, DEI i VID.`, 'Rozpisz 16 bitów: 3 bity PCP, 1 bit DEI, 12 bitów VID.', `${h4(t)} = ${bin16(t)} → PCP = ${p.pcp}, DEI = ${p.dei}, VID = ${p.vid}.`);
}
for (const [pcp, vid] of [[5, 30], [3, 2000]]) {
  const t = tci(pcp, 0, vid);
  ex(`Zakoduj znacznik 802.1Q dla VLAN ${vid} z priorytetem PCP ${pcp} (DEI 0). Podaj 4 bajty znacznika w zapisie szesnastkowym.`, 'TPID 0x8100, potem TCI = PCP·2^13 + DEI·2^12 + VID.', `TCI = ${pcp}·8192 + ${vid} = ${t} = ${h4(t)} (${bin16(t)}). Znacznik: 81 00 ${h4(t).slice(2, 4).toLowerCase()} ${h4(t).slice(4).toLowerCase()}.`);
}
{
  const sws = [['SW1', 32768, '00:1b:54:aa:10:01'], ['SW2', 32768, '00:0a:41:00:00:07'], ['SW3', 28672, '00:2c:c8:99:00:01'], ['SW4', 32768, '00:0a:41:00:00:05']];
  ex(`Cztery przełączniki w VLAN 1: ${sws.map(([n, p, m]) => `${n} (priorytet ${p}, MAC ${m})`).join(', ')}. Który zostanie root bridge? Który zostałby rootem, gdyby wszystkie miały priorytet 32768?`, 'Najpierw porównaj priorytet (+ VLAN), dopiero przy remisie MAC.', `Root: ${root(sws, 1)} (BID ${bid(28672, 1)}). Przy równych priorytetach: ${root(sws.map(([n, , m]) => [n, 32768, m]), 1)} — najniższy MAC 00:0a:41:00:00:05.`);
  ex('Przełącznik ma priorytet STP 32768 i działa w Rapid PVST+. Jaki Bridge Priority pokaże show spanning-tree dla VLAN 10, VLAN 100 i VLAN 4000? Dlaczego priorytet można zmieniać tylko co 4096?', 'System ID extension = numer VLAN dodawany do priorytetu.', `VLAN 10: ${bid(32768, 10)}, VLAN 100: ${bid(32768, 100)}, VLAN 4000: ${bid(32768, 4000)}. Z 16 bitów pola priorytetu dolne 12 zajmuje numer VLAN, więc administrator ustawia tylko górne 4 bity — wartości co 2^12 = 4096 (0, 4096, …, 61440).`);
}
const more = [
  ['Wykonaj Guided Lab z lekcji: trzy namespace’y na bridge’u z vlan_filtering, dwa w VLAN 10, jeden w VLAN 20. Pokaż wynik bridge vlan show oraz udowodnij izolację (ping i ip neigh).', 'bridge vlan add dev veth-x vid N pvid untagged; usuń VID 1.', 'bridge vlan show: veth-a 10 PVID Egress Untagged, veth-b 10 PVID Egress Untagged, veth-c 20 PVID Egress Untagged. ns-a → ns-b: ping OK; ns-a → ns-c: 100% packet loss, ip neigh: 10.0.0.3 FAILED — zapytanie ARP (broadcast) nie opuszcza VLAN-u 10, mimo że adresy są z tej samej podsieci. Izolację zapewnia bridge (L2), nie adresacja.'],
  ['Rozbuduj lab o „router on a stick”: namespace z podinterfejsami eth0.10 (10.10.0.1/24) i eth0.20 (10.20.0.1/24) na porcie trunk bridge’a. Następnie dodaj regułę nftables, która pozwala z VLAN 10 do VLAN 20 tylko na TCP 443.', 'Port bridge’a: bridge vlan add dev X vid 10; vid 20 (tagged). ip_forward=1. Łańcuch forward z polityką drop.', 'W routerze: ip link add link eth0 name eth0.10 type vlan id 10 (i .20), adresy, sysctl net.ipv4.ip_forward=1. Hosty: brama 10.10.0.1 / 10.20.0.1. nft: table inet f { chain fwd { type filter hook forward priority 0; policy drop; ct state established,related accept; iifname "eth0.10" oifname "eth0.20" tcp dport 443 accept } }. Wynik: curl https z VLAN 10 do 20 działa, ping i SSH — nie. To minimalny model segmentacji: VLAN izoluje, router z regułami kontroluje.'],
  ['Przechwyć tcpdump -i <trunk> -e -nn -xx kilka ramek z lab-u (lub z pliku pcap z ruchem 802.1Q) i wskaż w bajtach TPID, TCI i EtherType. Jaki VLAN i priorytet mają ramki?', 'Szukaj 81 00 po 12 bajtach adresów MAC.', 'Przykład: ... 81 00 00 0a 08 00 ... → TPID 0x8100, TCI 0x000A → PCP 0, DEI 0, VID 10; EtherType 0x0800 (IPv4). Wireshark: pola vlan.id, vlan.priority; filtr vlan.id == 10.'],
  ['Serwer ma skonfigurowany interfejs eth0.20 (znakowany VLAN 20), a port przełącznika jest w trybie access VLAN 20. Łącze jest „up”, ale serwer nie ma łączności. Wyjaśnij przyczynę i dwa sposoby naprawy.', 'Kto dodaje znacznik w trybie access?', 'Port access oczekuje ramek bez znacznika i sam przypisuje je do VLAN 20; ramki serwera mają tag VID 20, więc przełącznik je odrzuca (lub traktuje niezgodnie). Naprawa: (1) na serwerze używać eth0 bez tagu (adres na eth0), albo (2) skonfigurować port przełącznika jako trunk z allowed VLAN 20 (jeśli serwer ma obsługiwać wiele VLAN-ów, np. hiperwizor).'],
  ['Administrator wpisał na trunku switchport trunk allowed vlan 30, chcąc dodać VLAN telefonów. Co się stało i jak należało to zrobić?', 'Polecenie bez add zastępuje listę.', 'Lista allowed VLANs została zastąpiona tylko VLAN-em 30 — wszystkie inne VLAN-y (np. 10, 20, 99) przestały przechodzić przez trunk i hosty za drugim przełącznikiem straciły sieć. Poprawnie: switchport trunk allowed vlan add 30. Weryfikacja: show interfaces trunk przed i po zmianie; zmiany na trunkach wykonuj w oknie serwisowym z dostępem konsolowym.'],
  ['Wyjaśnij objaw: po przełożeniu kabla laptopa do innego gniazdka przez ok. 30 sekund nie dostaje on adresu z DHCP, a klient Javy zgłasza timeout przy starcie. Jaki mechanizm za to odpowiada i jak go skrócić?', 'Stany portu w 802.1D: listening i learning po 15 s.', 'Klasyczny STP przeprowadza nowy port przez listening (15 s) i learning (15 s) przed forwarding — ok. 30 s bez przekazywania ramek, więc DHCP Discover ginie. Naprawa: spanning-tree portfast na portach brzegowych (od razu forwarding) z bpduguard, lub RSTP, w którym porty brzegowe (edge) przechodzą do forwarding natychmiast. W Linuksie analogicznie bridge z STP ma forward_delay.'],
  ['Opisz, jak zaprojektujesz VLAN-y dla małej firmy (30 pracowników, 5 serwerów, drukarki, telefony VoIP, Wi-Fi gości, kamery). Podaj numery VLAN-ów, podsieci i 4 najważniejsze reguły między strefami.', 'Strefy wg zaufania; 1 VLAN = 1 podsieć; domyślnie deny między strefami.', 'Przykład: VLAN 10 USERS 10.10.0.0/24, VLAN 20 SERVERS 10.20.0.0/24, VLAN 30 VOICE 10.30.0.0/24, VLAN 40 PRINTERS 10.40.0.0/24, VLAN 50 CCTV 10.50.0.0/24, VLAN 60 GUEST 10.60.0.0/24, VLAN 99 MGMT 10.99.0.0/24, native 999. Reguły: (1) GUEST → tylko Internet, brak dostępu do sieci wewnętrznych; (2) USERS → SERVERS tylko potrzebne porty (443, 445 do serwera plików, 53/88/389 do AD); (3) CCTV → tylko rejestrator, bez Internetu; (4) MGMT dostępny tylko z jump hosta/administratorów. Domyślnie deny i logowanie.'],
  ['Zbuduj w lab-ie (namespace’y lub GNS3/Containerlab) pętlę z trzech bridge’y z wyłączonym STP, a następnie z włączonym. Opisz obserwacje (liczniki ramek, CPU, tablica FDB). Wykonuj tylko w izolowanym środowisku!', 'stp_state 0 vs 1; jeden broadcast (arping) wystarczy, by wywołać burzę.', 'Bez STP: jeden broadcast (np. arping) zaczyna krążyć — liczniki rx/tx (ip -s link) rosną o tysiące pakietów na sekundę, CPU rośnie, bridge fdb show pokazuje adres MAC nadawcy „skaczący” między portami (MAC flapping). Z stp_state 1: po czasie zbieżności jeden port jest w stanie blocking (bridge -d link show), ruch normalny. Wniosek: STP jest konieczny przy każdej redundancji L2.'],
  ['Masz trzy przełączniki: rdzeń (CORE) i dwa dostępowe. Wszystkie mają priorytet 32768, a dostępowy ACC2 jest najstarszy (najniższy MAC). Jakie są skutki i jak to naprawić poleceniem Cisco?', 'Root wybierany po najniższym MAC przy równych priorytetach.', 'Rootem zostaje ACC2, więc drzewo STP buduje się od przełącznika dostępowego: ruch między ACC1 a rdzeniem może iść przez ACC2, łącze ACC1–CORE może zostać zablokowane, a awaria ACC2 przebudowuje całe drzewo. Naprawa: na CORE spanning-tree vlan 1-4094 root primary (lub priority 24576), na zapasowym rdzeniu root secondary (28672), weryfikacja show spanning-tree root.'],
  ['Wyjaśnij, dlaczego VLAN sam w sobie nie jest mechanizmem kontroli dostępu. Podaj przykład konfiguracji, w której segmentacja VLAN-ami nie daje żadnej ochrony.', 'Co robi przełącznik L3 z SVI w każdym VLAN-ie bez ACL?', 'VLAN izoluje ruch L2 (broadcast, bezpośrednie ramki), ale jeśli istnieje router/przełącznik L3 z interfejsami we wszystkich VLAN-ach i bez ACL, routuje ruch między nimi bez ograniczeń — każdy host może połączyć się z każdym. Przykład: przełącznik rdzeniowy z interface vlan 10/20/99 i ip routing, bez access-group — VLAN użytkowników ma pełny dostęp do VLAN-u zarządzania. Ochronę daje dopiero ACL/firewall na granicy.'],
  ['Opisz dwa warianty VLAN hopping (koncepcyjnie) i dla każdego podaj konfigurację obronną.', 'Switch spoofing (DTP) i double tagging (native VLAN).', 'Switch spoofing: host wysyła ramki DTP i negocjuje trunk, uzyskując dostęp do wszystkich VLAN-ów. Obrona: switchport mode access + switchport nonegotiate na portach użytkowników, trunk tylko statyczny. Double tagging: napastnik w native VLAN wysyła ramkę z dwoma znacznikami; pierwszy przełącznik zdejmuje zewnętrzny (native, bez tagu na trunku), drugi widzi wewnętrzny i przekazuje ramkę do VLAN-u ofiary (tylko w jedną stronę). Obrona: native VLAN nieużywany przez hosty i różny od 1, vlan dot1q tag native, porty użytkowników nigdy w native VLAN.'],
  ['Jak sprawdzić w Linuksie, na hoście z Dockerem, jakie bridge’e i VLAN-y istnieją, i jak uruchomić kontener bezpośrednio w VLAN-ie 10 sieci fizycznej?', 'ip -d link, bridge vlan, docker network create -d macvlan.', 'Podgląd: ip -d link show type bridge, ip -d link show type vlan, bridge vlan show, docker network ls. Kontener w VLAN 10: docker network create -d macvlan --subnet 10.10.0.0/24 --gateway 10.10.0.1 -o parent=eth0.10 vlan10 (Docker sam utworzy eth0.10, jeśli nie istnieje), potem docker run --network vlan10 --ip 10.10.0.50 ... Port przełącznika musi być trunkiem z VLAN 10. Uwaga: host domyślnie nie komunikuje się z kontenerami macvlan na tym samym interfejsie.'],
  ['Policz maksymalny rozmiar ramki Ethernet (bez preambuły) dla: zwykłej ramki, ramki z jednym znacznikiem 802.1Q, ramki QinQ oraz ramki jumbo MTU 9000 z jednym znacznikiem.', '14 B nagłówka + dane + 4 B FCS; każdy znacznik +4 B.', 'Zwykła: 14 + 1500 + 4 = 1518 B. 802.1Q: 1522 B. QinQ: 1526 B. Jumbo 9000 z tagiem: 14 + 4 + 9000 + 4 = 9022 B. MTU warstwy IP pozostaje 1500 (lub 9000) — znaczniki nie zmniejszają MTU, ale wszystkie urządzenia na ścieżce L2 muszą akceptować większe ramki.'],
  ['Na przełączniku pojawił się log „%SPANTREE-2-BLOCK_BPDUGUARD: Received BPDU on port Gi1/0/12 with BPDU Guard enabled. Disabling port.” Co to znaczy, co sprawdzisz i czy to incydent bezpieczeństwa?', 'BPDU na porcie brzegowym = podłączono urządzenie mówiące STP.', 'Na porcie brzegowym (PortFast) odebrano BPDU — ktoś podłączył przełącznik (np. mały switch biurkowy, punkt dostępowy w trybie bridge) albo urządzenie celowo wysyłające BPDU; BPDU Guard wyłączył port (err-disabled). Sprawdzić: co jest podłączone (show cdp/lldp neighbors, fizycznie), czy to zgłoszona zmiana. Najczęściej to błąd użytkownika, ale wpis trafia do SIEM jako potencjalna próba manipulacji STP/nieautoryzowane urządzenie. Przywrócenie: usunąć urządzenie, shutdown/no shutdown lub errdisable recovery.'],
  ['Porównaj VLAN 802.1Q i VXLAN: rozmiar identyfikatora, liczba segmentów, gdzie działa i jaki narzut dodaje do ramki.', 'VXLAN = L2 w UDP; VNI 24 bity.', '802.1Q: VID 12 bitów → 4094 VLAN-y, działa w obrębie domeny L2 (przełączniki połączone trunkami), narzut 4 B. VXLAN: VNI 24 bity → ~16 mln segmentów, ramka L2 kapsułkowana w UDP/IP (port 4789), więc segment może rozciągać się przez sieć L3 (centra danych, chmura, Kubernetes — np. Flannel/Calico w trybie VXLAN); narzut ~50 B, dlatego MTU w overlay to zwykle 1450 (lekcja 01).'],
  ['Telefon IP i komputer za nim korzystają z jednego gniazdka. Opisz, jak działa voice VLAN i jak telefon dowiaduje się, którego VLAN-u użyć.', 'Ramki telefonu znakowane, komputera nie; LLDP-MED/CDP.', 'Port jest w trybie access (data VLAN, np. 10) z dodatkowym voice VLAN (np. 30). Przełącznik ogłasza voice VLAN przez CDP lub LLDP-MED (lekcja 09); telefon znakuje swoje ramki VID 30 z priorytetem PCP 5 (QoS dla głosu), a ramki komputera przekazuje bez znacznika (VLAN 10). Dzięki temu głos i dane są w osobnych podsieciach i z różnym priorytetem.'],
];
for (const [q, h, a] of more) ex(q, h, a);
const extra = [
  ['Napisz w Javie metodę, która z tablicy bajtów surowej ramki Ethernet zwraca numer VLAN (lub -1, jeśli ramka nie jest znakowana) i rzeczywisty EtherType.', 'Bajty 12–13: 0x8100 lub 0x88A8 oznaczają znacznik; VID to dolne 12 bitów TCI.', 'record VlanInfo(int vid, int pcp, int etherType) {}\nstatic VlanInfo vlan(byte[] f) {\n  int tpid = ((f[12] & 0xff) << 8) | (f[13] & 0xff);\n  if (tpid != 0x8100 && tpid != 0x88A8) return new VlanInfo(-1, -1, tpid);\n  int tci = ((f[14] & 0xff) << 8) | (f[15] & 0xff);\n  int et = ((f[16] & 0xff) << 8) | (f[17] & 0xff);\n  return new VlanInfo(tci & 0x0FFF, tci >>> 13, et);\n}\n// Dla QinQ (0x88A8) wewnętrzny znacznik zaczyna się od bajtu 16 — można wywołać rekurencyjnie z przesunięciem.'],
  ['Przygotuj listę kontrolną hardeningu warstwy 2 (minimum 6 punktów) do audytu przełączników dostępowych.', 'Porty access, trunki, STP, zarządzanie, nieużywane porty.', '1) Porty użytkowników: switchport mode access + nonegotiate. 2) Nieużywane porty: shutdown i przypisanie do nieużywanego VLAN-u (np. 998 „parking”). 3) Trunki: statyczne, allowed VLANs jawnie, native VLAN nieużywany ≠ 1, vlan dot1q tag native. 4) STP: RSTP/MST, root ustawiony ręcznie, PortFast + BPDU Guard na portach brzegowych, Root Guard w stronę dostępu. 5) Storm control na portach dostępowych. 6) Zarządzanie w osobnym VLAN-ie, SVI VLAN 1 wyłączone, SSH zamiast Telnetu. 7) Port security / 802.1X (lekcja 02 i rozdział 13). 8) Logi zmian (syslog, TCN STP) do SIEM.'],
  ['W Wiresharku przechwyć BPDU (filtr stp) w lab-ie lub z pliku przykładowego. Odczytaj Root Identifier, Bridge Identifier i Root Path Cost. Który przełącznik jest rootem?', 'Pola stp.root.hw, stp.root.prio, stp.bridge.hw, stp.root.cost.', 'Przykład: Root Identifier 32768 / 10 / 00:0c:29:aa:00:01, Root Path Cost 4, Bridge Identifier 32768 / 10 / 00:0c:29:aa:00:02 → rootem jest przełącznik 00:0c:29:aa:00:01 (priorytet 32768 + VLAN 10), a nadawca BPDU jest jeden skok dalej (koszt 4 = łącze 1 Gb/s). Ramka idzie na multicast 01:80:c2:00:00:00 (STP) lub 01:00:0c:cc:cc:cd (Cisco PVST+).'],
  ['Wyjaśnij, dlaczego pętla L2 jest groźniejsza niż pętla routingu na L3.', 'Pole TTL/Hop Limit w IP; brak odpowiednika w Ethernecie.', 'Pakiet IP w pętli routingu ma TTL (Hop Limit), zmniejszany na każdym routerze — po co najwyżej 255 skokach jest odrzucany. Ramka Ethernet nie ma żadnego licznika, więc w pętli L2 krąży bez końca; broadcasty i ramki do nieznanych adresów są dodatkowo powielane na każdym przełączniku (flooding), co daje wzrost wykładniczy. Do tego tablice MAC są stale nadpisywane (flapping), więc nawet ruch unicast trafia w złe miejsca.'],
  ['Kubernetes: wyjaśnij, czym różni się izolacja przez NetworkPolicy od izolacji przez VLAN, i kiedy w klastrze pojawiają się prawdziwe VLAN-y.', 'NetworkPolicy = filtr L3/L4 w CNI; VLAN = domena L2.', 'NetworkPolicy działa na L3/L4 (adresy podów, etykiety, porty) i jest egzekwowana przez CNI (np. Calico, Cilium) na każdym węźle — pody mogą być w jednej sieci płaskiej, a reguły i tak ograniczają ruch (mikrosegmentacja). VLAN to podział na poziomie L2 w sieci fizycznej. Prawdziwe VLAN-y pojawiają się w klastrze, gdy węzły są w różnych VLAN-ach sieci fizycznej, gdy używa się Multus z macvlan/ipvlan/SR-IOV (pod dostaje interfejs w VLAN-ie) lub w sieciach bare-metal z MetalLB w trybie L2.'],
  ['Przeanalizuj objaw: w biurze co kilka minut cała sieć „zamiera” na kilka sekund, a w logach przełączników widać częste „Topology Change”. Jakie są możliwe przyczyny i jak je zdiagnozujesz?', 'TCN powoduje skrócenie czasu życia wpisów MAC i flooding; źródłem bywa port bez PortFast.', 'Każda zmiana topologii (TCN) w 802.1D skraca czas życia wpisów w tablicach MAC do 15 s, co wywołuje masowy flooding. Częste TCN generują porty brzegowe bez PortFast (każde włączenie/wyłączenie komputera), niestabilne łącze (flapping) lub nieautoryzowany przełącznik. Diagnoza: show spanning-tree detail | include ieee|occurr|from|is exec — który port i kiedy zgłosił zmianę; śledzić do źródła przełącznik po przełączniku. Naprawa: PortFast na portach brzegowych (nie generują TCN), wymiana kabla, RSTP.'],
];
for (const [q, h, a] of extra) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111505);
// Dekodowanie TCI
for (const t of [0xA014, 0x000A, 0x0064, 0x2001, 0xC0C8, 0x13E8, 0xE00F, 0x6FFE, 0x8190, 0x4BB8]) {
  const p = parse(t);
  add(`Pole TCI znacznika 802.1Q ma wartość ${h4(t)}. Jaki to VLAN?`, `VLAN ${p.vid}`, [`VLAN ${t & 0xff}`, `VLAN ${t >> 4}`, `VLAN ${(t & 0xfff) + 1}`, `VLAN ${t}`, `VLAN ${p.vid ^ 0x800}`, `VLAN ${(p.vid + 100) % 4095}`], `${bin16(t)} → dolne 12 bitów = ${p.vid} (PCP ${p.pcp}, DEI ${p.dei}).`);
}
for (const t of [0xA014, 0x6064, 0xE001, 0x200A, 0xC3E8, 0x8014]) {
  const p = parse(t);
  add(`Jaki priorytet PCP zawiera TCI ${h4(t)}?`, String(p.pcp), [String((t >> 12) & 7), String(t >> 12), String((p.pcp + 1) % 8), String((p.pcp + 4) % 8), String((p.pcp + 2) % 8), String((p.pcp + 6) % 8)], `Górne 3 bity ${bin16(t).slice(0, 4)}… → PCP = ${p.pcp}.`);
}
// Kodowanie TCI
for (const [pcp, vid] of [[0, 10], [5, 20], [3, 100], [6, 1], [0, 4094], [7, 300], [1, 50], [4, 1000]]) {
  const t = tci(pcp, 0, vid);
  add(`Jaką wartość ma TCI dla VLAN ${vid} z priorytetem ${pcp} (DEI 0)?`, h4(t), [h4((vid << 4) | pcp), h4(vid), h4((pcp << 12) | vid) === h4(t) ? h4(t + 1) : h4((pcp << 12) | vid), h4(t ^ 0x1000)], `TCI = ${pcp}·8192 + ${vid} = ${t} = ${h4(t)}.`);
}
// Bridge ID
for (const [p, v] of [[32768, 1], [32768, 10], [24576, 20], [4096, 100], [28672, 99], [61440, 1], [0, 30], [32768, 4094]]) {
  add(`Jaki Bridge Priority pokaże Rapid PVST+ dla VLAN ${v}, gdy skonfigurowany priorytet to ${p}?`, String(bid(p, v)), [String(p), String(p + v + 1), String(p * v || p + 1), String(p - v)], `Priorytet + system ID extension (numer VLAN): ${p} + ${v} = ${bid(p, v)}.`);
}
// Wybor root bridge
const rootSets = [
  [['SW-A', 32768, '00:1a:00:00:00:01'], ['SW-B', 32768, '00:0b:00:00:00:01'], ['SW-C', 32768, '00:1c:00:00:00:01']],
  [['SW-A', 32768, '00:01:00:00:00:01'], ['SW-B', 28672, '00:ff:00:00:00:01'], ['SW-C', 32768, '00:02:00:00:00:01']],
  [['SW-A', 24576, '00:aa:00:00:00:09'], ['SW-B', 24576, '00:aa:00:00:00:03'], ['SW-C', 32768, '00:00:00:00:00:01']],
  [['SW-A', 4096, '00:cc:00:00:00:01'], ['SW-B', 8192, '00:01:00:00:00:01'], ['SW-C', 61440, '00:00:00:00:00:02']],
  [['SW-A', 32768, '7c:21:00:00:00:10'], ['SW-B', 32768, '7c:20:ff:00:00:10'], ['SW-C', 36864, '00:00:0c:00:00:01']],
  [['SW-A', 61440, '00:00:00:00:00:01'], ['SW-B', 57344, '00:ff:ff:ff:ff:ff'], ['SW-C', 61440, '00:00:00:00:00:00']],
];
rootSets.forEach((set, i) => {
  const r = root(set, 1);
  add(`Który przełącznik zostanie root bridge w VLAN 1? ${set.map(([n, p, m]) => `${n}: priorytet ${p}, MAC ${m}`).join('; ')}`, r, ['żaden — remis blokuje STP', ...set.map((x) => x[0]).filter((n) => n !== r)], `Najpierw najniższy priorytet, przy remisie najniższy MAC → ${r}.`);
});
// Rozmiary ramek
const sizes = [
  ['Jaki jest maksymalny rozmiar ramki Ethernet (bez preambuły) ze znacznikiem 802.1Q przy MTU 1500?', '1522 B', ['1518 B', '1500 B', '1526 B']],
  ['Jaki jest maksymalny rozmiar ramki QinQ (dwa znaczniki) przy MTU 1500?', '1526 B', ['1522 B', '1530 B', '1518 B']],
  ['Ile bajtów dodaje jeden znacznik 802.1Q?', '4', ['2', '8', '12']],
  ['Ile bitów ma pole VID?', '12', ['8', '16', '24']],
  ['Ile użytecznych numerów VLAN daje 802.1Q?', '4094', ['4096', '1024', '16 777 214']],
  ['Ile bitów ma identyfikator segmentu VNI w VXLAN?', '24', ['12', '16', '32']],
];
for (const [q, a, w] of sizes) add(q, a, w, 'Tag 802.1Q: 4 B (TPID 16 b + PCP 3 b + DEI 1 b + VID 12 b); VID 0 i 4095 zarezerwowane; VXLAN VNI 24 b.');
// Fakty
const facts = [
  ['Czym jest VLAN?', 'Logiczną domeną rozgłoszeniową wydzieloną na przełącznikach', ['Fizycznym kablem między budynkami', 'Rodzajem routera', 'Protokołem szyfrowania ramek'], 'Każdy VLAN to osobna domena rozgłoszeniowa.'],
  ['Jak host na porcie access wysyła ramki?', 'Bez znacznika — VLAN przypisuje przełącznik', ['Zawsze ze znacznikiem 802.1Q', 'Z podwójnym znacznikiem', 'Z adresem VLAN w nagłówku IP'], 'Tag dodaje i zdejmuje przełącznik.'],
  ['Do czego służy port trunk?', 'Do przenoszenia ruchu wielu VLAN-ów jednym łączem ze znacznikami 802.1Q', ['Do podłączenia jednego komputera', 'Do zasilania PoE', 'Do szyfrowania ruchu między przełącznikami'], 'Łączy przełączniki, hiperwizory, routery.'],
  ['Jaką wartość ma TPID znacznika 802.1Q?', '0x8100', ['0x0800', '0x86DD', '0x0806'], '0x0800 to IPv4, 0x86DD IPv6, 0x0806 ARP.'],
  ['Jaką wartość TPID ma zewnętrzny znacznik QinQ (802.1ad)?', '0x88A8', ['0x8100', '0x8847', '0x88CC'], '0x8847 to MPLS, 0x88CC LLDP.'],
  ['Czym jest native VLAN?', 'VLAN-em, którego ramki idą przez trunk bez znacznika', ['VLAN-em zarządzania', 'Pierwszym VLAN-em utworzonym na przełączniku', 'VLAN-em z najwyższym priorytetem'], 'Domyślnie VLAN 1 — warto zmienić na nieużywany.'],
  ['Co jest skutkiem niezgodności native VLAN po dwóch stronach trunku?', 'Ramki bez znacznika trafiają do innego VLAN-u niż zamierzony', ['Trunk działa szybciej', 'Znaczniki są szyfrowane', 'Nic — native VLAN jest lokalny'], 'Błąd zgłaszany przez CDP/LLDP; ryzyko przecieku między VLAN-ami.'],
  ['Jak ruch przechodzi z jednego VLAN-u do drugiego?', 'Przez router lub przełącznik L3 (routing między VLAN-ami)', ['Bezpośrednio przez przełącznik L2', 'Przez broadcast', 'Nie może przejść w żaden sposób'], 'Tam też umieszcza się ACL/firewall.'],
  ['Czym jest „router on a stick”?', 'Routingiem między VLAN-ami przez jeden port routera z podinterfejsami znakowanymi', ['Routerem bez portów', 'Przełącznikiem bez VLAN-ów', 'Routerem zasilanym PoE'], 'Np. Gi0/0.10 i Gi0/0.20.'],
  ['Czym jest SVI?', 'Wirtualnym interfejsem L3 przełącznika w danym VLAN-ie', ['Fizycznym portem trunk', 'Protokołem negocjacji trunku', 'Rodzajem kabla'], 'interface vlan 10 — brama podsieci.'],
  ['Dlaczego pętla L2 powoduje burzę rozgłoszeniową?', 'Ethernet nie ma TTL, a broadcasty są powielane na każdym przełączniku', ['Bo routery odsyłają broadcasty', 'Bo DNS się zapętla', 'Bo MTU jest za małe'], 'Wzrost lawinowy w sekundach.'],
  ['Co robi STP?', 'Blokuje nadmiarowe porty, tworząc drzewo bez pętli', ['Szyfruje ruch L2', 'Przydziela adresy IP', 'Łączy kilka łączy w jedno'], 'Przy awarii odblokowuje port zapasowy.'],
  ['Który przełącznik zostaje root bridge?', 'Ten z najniższym Bridge ID (priorytet, potem MAC)', ['Ten z najwyższym priorytetem', 'Ten z największą liczbą portów', 'Ten, który uruchomił się pierwszy'], 'Przy równych priorytetach — najniższy MAC.'],
  ['Jaki jest domyślny priorytet STP przełącznika?', '32768', ['0', '4096', '65535'], 'Zmienia się w krokach co 4096.'],
  ['Na jaki adres MAC wysyłane są BPDU standardowego STP?', '01:80:c2:00:00:00', ['ff:ff:ff:ff:ff:ff', '01:00:5e:00:00:00', '33:33:00:00:00:01'], 'Adres multicast zarezerwowany dla mostów IEEE.'],
  ['Ile maksymalnie trwa przejście portu do forwarding w klasycznym STP (802.1D) przy domyślnych timerach?', 'Ok. 30–50 s', ['Poniżej 1 s', 'Ok. 5 minut', 'Ok. 2 s'], 'Max age 20 s + listening 15 s + learning 15 s.'],
  ['Co daje PortFast na porcie brzegowym?', 'Port przechodzi od razu do forwarding, bez listening i learning', ['Szybsze łącze', 'Szyfrowanie ruchu', 'Wyłączenie VLAN-ów'], 'Tylko dla portów z hostami, z BPDU Guard.'],
  ['Co robi BPDU Guard?', 'Wyłącza port (err-disabled), gdy odbierze na nim BPDU', ['Zmienia priorytet STP', 'Szyfruje BPDU', 'Blokuje broadcasty'], 'Chroni przed nieautoryzowanymi przełącznikami.'],
  ['Co robi Root Guard?', 'Nie pozwala, by przełącznik za danym portem został root bridge', ['Wyłącza STP', 'Blokuje VLAN 1', 'Przyspiesza zbieżność'], 'Port przechodzi w stan root-inconsistent.'],
  ['Który protokół zbiega w sekundy zamiast dziesiątek sekund?', 'RSTP (802.1w)', ['802.1D', 'DTP', 'VTP'], 'Szczegóły w lekcji 07.'],
  ['Dlaczego wyłącza się DTP na portach użytkowników?', 'Bo host mógłby wynegocjować trunk i uzyskać dostęp do wszystkich VLAN-ów', ['Bo spowalnia łącze', 'Bo DTP szyfruje ramki', 'Bo DTP wyłącza STP'], 'switchport mode access + nonegotiate.'],
  ['Na czym polega double tagging?', 'Na wysłaniu ramki z dwoma znacznikami z native VLAN, by trafiła do innego VLAN-u', ['Na zmianie adresu MAC', 'Na przejęciu roli root bridge', 'Na zalewaniu tablicy MAC'], 'Obrona: native VLAN nieużywany, tagowanie native.'],
  ['Który zestaw ustawień najlepiej chroni trunk?', 'Statyczny trunk, jawna lista allowed VLANs, nieużywany native VLAN ≠ 1', ['Tryb dynamic desirable', 'Native VLAN 1 i allowed all', 'Wyłączony STP'], 'Plus vlan dot1q tag native.'],
  ['Co robi polecenie switchport trunk allowed vlan 30 na trunku z VLAN-ami 10 i 20?', 'Zastępuje listę — trunk przenosi tylko VLAN 30', ['Dodaje VLAN 30 do listy', 'Usuwa VLAN 30', 'Nic nie zmienia'], 'Do dopisywania służy … allowed vlan add 30.'],
  ['Czy VLAN sam w sobie filtruje ruch między segmentami?', 'Nie — izoluje L2, ale ruch routowany trzeba filtrować ACL/firewallem', ['Tak, zawsze blokuje cały ruch', 'Tak, ale tylko TCP', 'Tylko w IPv6'], 'Segmentacja = VLAN + reguły na granicy.'],
  ['Jaka jest zasada mapowania VLAN-ów na adresację?', '1 VLAN = 1 podsieć IP', ['Wszystkie VLAN-y w jednej podsieci', 'Każdy host w osobnym VLAN-ie', 'VLAN-y nie mają związku z adresacją'], 'Ułatwia routing, filtrowanie i diagnozę.'],
  ['Które polecenie Linuksa tworzy podinterfejs VLAN 10 na eth0?', 'ip link add link eth0 name eth0.10 type vlan id 10', ['ip addr add vlan 10 dev eth0', 'brctl addvlan eth0 10', 'ifconfig eth0 vlan 10'], 'Moduł 8021q.'],
  ['Jakiej opcji bridge’a Linuksa potrzeba, by działał jak przełącznik z VLAN-ami?', 'vlan_filtering 1', ['stp_state 1', 'mcast_snooping 1', 'ageing_time 0'], 'Potem bridge vlan add … pvid untagged.'],
  ['Jak w tcpdump zobaczyć znaczniki VLAN w ramkach?', 'tcpdump -e -nn vlan', ['tcpdump -A', 'tcpdump -X port 80', 'tcpdump -v icmp'], '-e pokazuje nagłówek warstwy 2.'],
  ['Do czego służy voice VLAN?', 'Do oddzielenia ruchu telefonu IP od komputera podłączonego za telefonem', ['Do nagrywania rozmów', 'Do szyfrowania VoIP', 'Do łączenia VLAN-ów'], 'Telefon znakuje ramki i używa wyższego PCP.'],
  ['Jakie polecenie Cisco pokaże tryb, native VLAN i allowed VLANs trunków?', 'show interfaces trunk', ['show vlan brief', 'show ip route', 'show mac address-table'], 'show vlan brief pokazuje porty access.'],
  ['Jakim sterownikiem Dockera wpuścisz kontener bezpośrednio do VLAN-u sieci fizycznej?', 'macvlan (z parent=eth0.N)', ['bridge', 'none', 'overlay bez VXLAN'], 'Port przełącznika musi być trunkiem z tym VLAN-em.'],
  ['Dlaczego w centrach danych i chmurze używa się VXLAN zamiast samych VLAN-ów?', 'Daje ~16 mln segmentów i pozwala rozciągnąć L2 przez sieć L3', ['Bo jest szybszy od Ethernetu', 'Bo nie ma narzutu', 'Bo nie wymaga IP'], 'VNI 24 bity, kapsułkowanie w UDP 4789.'],
  ['Co oznacza bit DEI w znaczniku 802.1Q?', 'Że ramkę można odrzucić w pierwszej kolejności przy przeciążeniu', ['Że ramka jest szyfrowana', 'Że ramka jest broadcastem', 'Że ramka pochodzi z native VLAN'], 'Dawniej CFI.'],
  ['Do czego służy pole PCP?', 'Do oznaczania priorytetu ramki (QoS 802.1p, 0–7)', ['Do numeru VLAN', 'Do sumy kontrolnej', 'Do długości ramki'], 'Głos zwykle PCP 5.'],
  ['Jaki objaw sugeruje pętlę L2?', 'Gwałtowny wzrost broadcastów, MAC flapping i wysokie CPU przełączników', ['Pojedyncze błędy DNS', 'Wolne logowanie do aplikacji o 3:00', 'Brak certyfikatu TLS'], 'Logi: „MAC flapping”, liczniki broadcastów.'],
  ['Co robi przełącznik z ramką ze znacznikiem VLAN, który nie jest dozwolony na trunku?', 'Odrzuca ją', ['Przekazuje do VLAN 1', 'Zdejmuje znacznik i przekazuje', 'Wysyła do wszystkich VLAN-ów'], 'Dlatego ważna jest zgodna lista allowed VLANs.'],
  ['Gdzie powinien znajdować się interfejs zarządzania przełącznikami?', 'W osobnym VLAN-ie zarządzania, dostępnym tylko z wybranych hostów', ['W VLAN-ie 1 razem z użytkownikami', 'W VLAN-ie gości', 'W native VLAN trunku'], 'Plus SSH, AAA i ACL.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
// Scenariusze: kto odbierze broadcast (porty access na jednym przelaczniku)
const portMap = { Gi1: 10, Gi2: 10, Gi3: 20, Gi4: 20, Gi5: 10, Gi6: 30, Gi7: 30, Gi8: 20 };
const desc = Object.entries(portMap).map(([p, v]) => `${p}=VLAN ${v}`).join(', ');
for (const src of ['Gi1', 'Gi3', 'Gi6', 'Gi8', 'Gi5', 'Gi7']) {
  const v = portMap[src];
  const recv = Object.keys(portMap).filter((p) => p !== src && portMap[p] === v);
  const others = Object.keys(portMap).filter((p) => p !== src);
  const wrongA = others.join(', ');
  const wrongB = Object.keys(portMap).filter((p) => p !== src && portMap[p] !== v).slice(0, 3).join(', ');
  const wrongC = recv.length > 1 ? recv.slice(0, 1).join(', ') : 'żaden port';
  add(`Przełącznik ma porty access: ${desc}. Host na ${src} wysyła ramkę rozgłoszeniową. Które porty ją otrzymają?`, recv.join(', '), [wrongA, wrongB, wrongC, 'tylko port do routera'], `Broadcast zostaje w VLAN ${v}: ${recv.join(', ')}.`);
}
const facts2 = [
  ['Który EtherType następuje po znaczniku 802.1Q w ramce z pakietem IPv4?', '0x0800', ['0x8100', '0x86DD', '0x0806'], 'Po znaczniku stoi „prawdziwy” EtherType ładunku.'],
  ['Co oznacza VID 0 w znaczniku 802.1Q?', 'Ramka niesie tylko priorytet (priority tag), bez przypisania do VLAN-u', ['VLAN domyślny', 'VLAN zarządzania', 'Ramkę rozgłoszeniową'], 'VID 0 i 4095 są zarezerwowane.'],
  ['Jaki skutek ma wpięcie kabla między dwa gniazdka tego samego przełącznika przy wyłączonym STP?', 'Pętla L2 i burza rozgłoszeniowa w kilka sekund', ['Podwojenie przepustowości', 'Nic — przełącznik to wykryje bez STP', 'Utrata adresu IP tylko przez jeden host'], 'Dlatego STP + BPDU Guard.'],
  ['Czym jest MAC flapping?', 'Ciągłym przenoszeniem tego samego adresu MAC między portami w tablicy przełącznika', ['Zmianą adresu MAC przez system operacyjny', 'Losowaniem MAC w Wi-Fi', 'Rodzajem szyfrowania'], 'Typowy objaw pętli L2.'],
  ['Dlaczego warto ręcznie ustawić root bridge?', 'Bo przy domyślnych priorytetach rootem zostaje przełącznik z najniższym MAC, często przypadkowy', ['Bo bez tego STP nie działa', 'Bo root szyfruje ruch', 'Bo root przydziela VLAN-y'], 'Root powinien być w rdzeniu sieci.'],
  ['Jak PVST+ różni się od klasycznego STP?', 'Buduje osobne drzewo STP dla każdego VLAN-u', ['Nie używa BPDU', 'Działa tylko w IPv6', 'Wyłącza blokowanie portów'], 'Pozwala rozkładać ruch VLAN-ów na różne łącza.'],
  ['Co robi przełącznik L3 z interfejsami SVI we wszystkich VLAN-ach bez ACL?', 'Routuje ruch między wszystkimi VLAN-ami bez ograniczeń', ['Blokuje ruch między VLAN-ami', 'Wysyła tylko broadcasty', 'Łączy VLAN-y w jedną domenę L2'], 'Segmentacja wymaga ACL/firewalla.'],
  ['Dokąd przypisać nieużywane porty przełącznika?', 'Wyłączyć je i przypisać do nieużywanego VLAN-u „parking”', ['Do VLAN-u 1', 'Do VLAN-u serwerów', 'Ustawić jako trunk'], 'Utrudnia nieautoryzowane podłączenia.'],
  ['Który mechanizm VPC w chmurze pełni rolę ACL między segmentami na poziomie instancji?', 'Security Groups (oraz Network ACL)', ['VLAN 1', 'DTP', 'BPDU Guard'], 'VPC to segmentacja realizowana przez overlay.'],
  ['Jakie polecenie Linuksa pokaże stany STP portów bridge’a?', 'bridge -d link show', ['ip route show', 'ss -tlnp', 'arp -a'], 'Stany: disabled, listening, learning, forwarding, blocking.'],
  ['Dlaczego serwer z interfejsem znakowanym (eth0.20) nie działa na porcie access VLAN 20?', 'Port access oczekuje ramek bez znacznika, a serwer wysyła znakowane', ['Bo VLAN 20 nie istnieje', 'Bo STP blokuje serwery', 'Bo port access nie ma adresu IP'], 'Albo bez tagu na serwerze, albo trunk na porcie.'],
  ['Jaki problem rozwiązuje QinQ?', 'Pozwala operatorowi przenieść VLAN-y klienta wewnątrz własnego VLAN-u (dwa znaczniki)', ['Zwiększa MTU IP', 'Szyfruje VLAN-y', 'Zastępuje STP'], 'S-tag 0x88A8 + C-tag 0x8100.'],
];
for (const [q, a, w, e] of facts2) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
