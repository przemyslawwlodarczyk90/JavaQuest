// Generuje lekcje _ns_03_ip_i_routing/08_StaticRoutingAndFloatingRoutes.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_08_static_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/08_StaticRoutingAndFloatingRoutes.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { AD, rib, fmt, LAB, MIXED } = require('./ns03_08_static_model.cjs');
const buildQuiz = require('./ns03_08_static_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const ribLines = (cands, down = []) => Object.values(rib(cands, down)).map((r) => `  ${fmt(r)}`).join('\n');

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Trasy, które wpisujesz sam — i plan B na awarię', ...img('ns03-08-floating.svg', 'Topologia labu: r1 połączony z r2 i r3, oba połączone z serwerem w sieci 198.51.100.0/24. Normalnie w RIB r1 jest trasa statyczna AD 1 przez r2, a trasa przez r3 z AD 250 czeka nieaktywna. Po wyłączeniu łącza r1–r2 trasa główna znika i instalowana jest pływająca przez r3.'),
    body:
`W poprzedniej lekcji czytaliśmy tablicę routingu. Teraz ją **budujemy**. Trasa statyczna to wpis dodany ręcznie (lub przez IaC): „do sieci X idź przez Y”. Brzmi prymitywnie przy OSPF i BGP, ale statyki są wszędzie: trasa domyślna na routerze oddziału, trasa do sieci partnera przez VPN, trasy w tablicach VPC w chmurze, trasy w kontenerach i na serwerach z dwoma interfejsami. Są przewidywalne, nie generują ruchu sterującego i nie da się ich „oszukać” fałszywym ogłoszeniem — ale **nie reagują same** na awarie na dalszych odcinkach.

Dlatego poznamy **trasę pływającą (floating static route)** — zapasową trasę z celowo podniesionym **dystansem administracyjnym (AD)**, która „wypływa” do tablicy dopiero wtedy, gdy trasa główna zniknie. I sam **dystans administracyjny**: miarę zaufania do źródła trasy, która rozstrzyga, gdy tę samą sieć zna kilka protokołów.

W tej lekcji:
- następny skok vs interfejs wyjściowy vs trasa w pełni określona; trasy /32 i domyślne,
- dystans administracyjny (connected 0, static 1, eBGP 20, OSPF 110, iBGP 200…) i kolejność: prefiks → AD → metryka,
- trasa pływająca i jej ograniczenia (wykrywa tylko awarię lokalnego łącza — chyba że użyjesz IP SLA/BFD),
- trasy statyczne IPv6 (next hop link-local wymaga interfejsu),
- routing między VLAN-ami: **router-on-a-stick** i **SVI na przełączniku L3**,
- **Guided Lab z planu**: FRR w trzech routerach — trasa główna i pływająca; zerwij łącze i obserwuj przełączenie.

Cele: CCNA 200-301 v2 domena 3 (konfiguracja i weryfikacja routingu statycznego IPv4 i IPv6: default, network, host, floating; dystans administracyjny; routing między VLAN-ami), Network+ (routing statyczny vs dynamiczny, AD).` },

  { type: 'DEFINITION', heading: 'Słownik routingu statycznego', body:
`- **Trasa statyczna** — wpis skonfigurowany ręcznie: prefiks + następny skok i/lub interfejs [+ AD].
- **Trasa sieciowa / hosta / domyślna** — do sieci (\`10.9.0.0/16\`), do pojedynczego adresu (\`/32\`, IPv6 \`/128\`), do wszystkiego (\`0.0.0.0/0\`, \`::/0\`; Cisco: „gateway of last resort”).
- **Następny skok (next hop)** — adres sąsiedniego routera; wymaga **rekurencyjnego wyszukania** (router musi wiedzieć, jak dojść do next hopa — zwykle connected).
- **Trasa „directly attached”** (tylko interfejs) — sensowna na łączach punkt-punkt; na Ethernecie wymusza ARP o każdy cel i proxy ARP.
- **Trasa w pełni określona (fully specified)** — interfejs + next hop; wymagana, gdy next hop to adres **link-local IPv6** (fe80:: jest na każdym łączu, więc trzeba wskazać które).
- **Dystans administracyjny (AD)** — wiarygodność źródła trasy (0–255, niższy lepszy). Domyślne (Cisco): ${AD.map(([n, v]) => `${n} ${v}`).join(', ')}.
- **RIB / FIB** — Routing Information Base (zwycięzcy z każdego źródła) i Forwarding Information Base (to, czego faktycznie używa przekazywanie; w Linuksie — tablica jądra).
- **Trasa pływająca (floating static)** — trasa zapasowa z AD wyższym niż trasa główna (np. 250 dla statyka zapasowego wobec statyka 1, albo 130 wobec OSPF 110). Nieaktywna, dopóki główna istnieje.
- **IP SLA / tracking, BFD** — mechanizmy, które usuwają trasę statyczną, gdy **dalszy** odcinek przestaje działać (ping/HTTP probe lub szybkie wykrywanie awarii sąsiada), a nie tylko gdy lokalny interfejs jest DOWN.
- **Null route (Null0 / blackhole)** — trasa „donikąd” (\`ip route 10.0.0.0 255.0.0.0 Null0\`, \`ip route add blackhole 10.0.0.0/8\`): odrzuca ruch do agregatu, którego części nie istnieją (zapobiega pętlom), używana też do blokowania ruchu (RTBH).
- **Router-on-a-stick** — jeden fizyczny interfejs routera z podinterfejsami 802.1Q dla każdego VLAN-u.
- **SVI (Switch Virtual Interface)** — interfejs \`Vlan10\` na przełączniku L3 z adresem bramy VLAN-u; wymaga \`ip routing\`.` },

  { type: 'ANALOGY', heading: 'Nawigacja z objazdem zapisanym w notesie', body:
`Trasa statyczna to **trasa zapisana w notesie kierowcy**: „do magazynu w Poznaniu jedź A2”. Nie zmienia się sama — jeśli na A2 jest wypadek 100 km dalej, kierowca i tak w niego wjedzie (statyk nie widzi awarii dalej niż własny wyjazd z bazy).

**Trasa pływająca** to dopisek „jeśli wjazd na A2 jest zamknięty, jedź drogą krajową 92” — z gwiazdką „gorsza, używaj tylko awaryjnie” (wyższy AD). Kierowca sięga po nią **tylko**, gdy pierwszy wariant fizycznie zniknie (zamknięty wjazd = interfejs DOWN). Jeśli chcesz, żeby reagował na wypadek dalej, potrzebujesz kogoś, kto co minutę dzwoni i sprawdza, czy A2 jest przejezdna (**IP SLA**) albo radia CB z natychmiastowymi komunikatami (**BFD**).

**Dystans administracyjny** to zaufanie do źródeł informacji o drodze: własny notes (static, 1) > komunikat kolegi z firmy przewozowej (OSPF, 110) > plotka z postoju (RIP, 120). Gdy dwa źródła mówią o **tym samym celu**, wierzysz bardziej zaufanemu — nawet jeśli plotka obiecuje krótszą drogę (metryk różnych protokołów się nie porównuje).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Kto wygrywa w RIB: AD, metryka i awarie', ...img('ns03-08-ad.svg', 'Wykres dystansów administracyjnych: connected 0, static 1, eBGP 20, EIGRP 90, OSPF 110, IS-IS 115, RIP 120, EIGRP zewnętrzny 170, iBGP 200, 255 nieużywana. Przykład: ta sama sieć 10.50.0.0/16 z OSPF (AD 110) i statyczna (AD 1) — do RIB trafia statyczna.'),
    body:
`Router zna kilka sieci z różnych źródeł. Poniżej RIB policzony modelem lekcji — w stanie normalnym i po awariach poszczególnych interfejsów. Zwróć uwagę na 10.60.0.0/16: statyk z AD 130 jest **pływającym zapasem dla OSPF** (110), a 0.0.0.0/0 z AD 210 — zapasem dla eBGP (20).`,
    code:
`Kandydaci:
${MIXED.map((c) => `  ${c.prefix.padEnd(14)} ${c.source.padEnd(7)} AD ${String(c.ad ?? AD.find(([n]) => n === c.source)[1]).padEnd(4)} metric ${String(c.metric).padEnd(3)} via ${c.via} (${c.dev})`).join('\n')}

RIB — normalnie:
${ribLines(MIXED)}

RIB — awaria Gi0/1 (sąsiad OSPF):
${ribLines(MIXED, ['Gi0/1'])}

RIB — awaria Gi0/0 (łącze do operatora BGP):
${ribLines(MIXED, ['Gi0/0'])}` },

  { type: 'CODE_BASIC', heading: 'Trasy statyczne IPv4 i IPv6: Cisco IOS, FRR i Linux', ...img('ns03-08-nexthop.svg', 'Trzy formy trasy statycznej: z następnym skokiem (rekurencyjne wyszukanie i ARP o bramę), tylko z interfejsem (na Ethernecie ARP o każdy cel i zależność od proxy ARP, poprawne na łączach punkt-punkt) oraz w pełni określona (interfejs i następny skok, wymagana dla next hop link-local w IPv6).'),
    body:
`Te same trasy w trzech składniach. FRR (\`vtysh\`) celowo przypomina IOS — to, czego nauczysz się w labie, przeniesiesz na egzamin CCNA.`,
    code:
`! Cisco IOS
ip route 0.0.0.0 0.0.0.0 203.0.113.1                  ! domyślna (gateway of last resort)
ip route 10.9.0.0 255.255.0.0 10.0.12.2               ! sieciowa, next hop
ip route 10.9.5.7 255.255.255.255 10.0.12.2           ! hosta /32
ip route 10.9.0.0 255.255.0.0 10.0.13.2 250           ! pływająca (AD 250)
ip route 10.0.0.0 255.0.0.0 Null0                     ! blackhole dla agregatu
ipv6 unicast-routing
ipv6 route ::/0 GigabitEthernet0/0 FE80::1            ! link-local next hop — interfejs obowiązkowy
ipv6 route 2001:db8:9::/48 2001:db8:12::2
show ip route static ; show ip route 10.9.0.0 ; show ipv6 route static

! FRR (vtysh) — identyczna składnia z prefiksem CIDR
ip route 198.51.100.0/24 10.0.12.2
ip route 198.51.100.0/24 10.0.13.2 250
ipv6 route 2001:db8:9::/48 2001:db8:12::2

# Linux (jądro nie zna AD — zapas robi się wyższą metryką; trasa przez interfejs DOWN znika)
ip route add 198.51.100.0/24 via 10.0.12.2 metric 10
ip route add 198.51.100.0/24 via 10.0.13.2 metric 250
ip route add blackhole 10.0.0.0/8
ip -6 route add default via fe80::1 dev eth0          # link-local: dev obowiązkowy` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab z planu: FRR w trzech routerach — główna i pływająca', body:
`Każdy kontener to osobny **network namespace** z własnym stosem — tak samo jak w labie z lekcji 02/13, tylko z FRR (zebra + staticd) zamiast ręcznych poleceń \`ip\`. Wymagany Docker (Linux/WSL2). Topologia: r1 łączy się z r2 (\`10.0.12.0/24\`) i r3 (\`10.0.13.0/24\`); r2 i r3 łączą się z serwerem srv (\`10.0.24.0/24\`, \`10.0.34.0/24\`), który ma adres \`198.51.100.10\` na loopbacku.
1. **Plik \`compose.yaml\`**: usługi r1, r2, r3 z obrazem \`quay.io/frrouting/frr:10.1.1\`, \`cap_add: [NET_ADMIN, SYS_ADMIN]\`, \`sysctls: net.ipv4.ip_forward: 1\`, oraz srv (\`alpine\` z \`iproute2\`). Sieci: \`n12\`, \`n13\`, \`n24\`, \`n34\` z jawnymi \`ipam.subnet\` i stałymi \`ipv4_address\` (np. r1 = 10.0.12.11 i 10.0.13.11, r2 = 10.0.12.2 i 10.0.24.2, r3 = 10.0.13.2 i 10.0.34.2, srv = 10.0.24.10 i 10.0.34.10). Uwaga: Docker rezerwuje .1 każdej sieci dla bramy mostu — dlatego routery mają adresy .2/.11.
2. **Konfiguracja FRR** (\`docker compose exec r1 vtysh\`, potem \`configure\`): na r1 \`ip route 198.51.100.0/24 10.0.12.2\` i \`ip route 198.51.100.0/24 10.0.13.2 250\`; na r2 \`ip route 198.51.100.0/24 10.0.24.10\`, na r3 \`ip route 198.51.100.0/24 10.0.34.10\`; trasy powrotne do r1 (np. adres testowy r1 \`192.168.10.1/32\` na loopbacku): na r2 i r3 \`ip route 192.168.10.1/32 10.0.12.11\` / \`10.0.13.11\`, na srv \`ip route add 192.168.10.1/32 via 10.0.24.2 metric 10\` i \`via 10.0.34.2 metric 20\`.
3. **Stan normalny**: \`docker compose exec r1 vtysh -c "show ip route 198.51.100.0/24"\` → \`S>* [1/0] via 10.0.12.2\`, a trasa z AD 250 widoczna jako nieaktywna; \`ip route get 198.51.100.10\` w r1 → przez sieć n12. Test: \`docker compose exec r1 ping -I 192.168.10.1 198.51.100.10\`.
4. **Awaria**: zerwij łącze r1–r2 — \`docker compose exec r1 ip link set <interfejs w n12> down\` (nazwę sprawdź \`ip -br addr\`). Obserwuj \`show ip route\`: trasa główna znika, **\`S>* [250/0] via 10.0.13.2\`** jest instalowana. Ping przechodzi dalej (srv odpowiada przez r3 dzięki wyższej metryce zapasowej).
5. **Ograniczenie**: przywróć łącze, a zamiast niego zatrzymaj **r2** w środku (\`docker compose exec r2 ip link set <iface do srv> down\`). Łącze r1–r2 jest UP, więc trasa główna **zostaje** — ruch ginie w r2. Statyk nie widzi awarii za sąsiadem.
6. **Naprawa ograniczenia**: BFD w FRR (\`bfdd\`; \`ip route 198.51.100.0/24 10.0.12.2 bfd\` w nowszych wersjach lub BFD w protokole dynamicznym), na Cisco IP SLA + \`track\` (\`ip route … 10.0.12.2 track 1\`). Albo routing dynamiczny (lekcja 14, OSPF).
7. **Notatka**: zapisz wyjście \`show ip route\` przed/po, czas przełączenia (ile pingów zginęło) i wniosek o ograniczeniu.` },

  { type: 'NOTE', heading: 'Routing między VLAN-ami: router-on-a-stick i SVI', ...img('ns03-08-intervlan.svg', 'Porównanie routingu między VLAN-ami: router-on-a-stick z podinterfejsami Gi0/0.10 i Gi0/0.20 na jednym łączu trunk 802.1Q do przełącznika L2, oraz przełącznik L3 z poleceniem ip routing i interfejsami SVI Vlan10 i Vlan20 pełniącymi rolę bram.'),
    body:
`VLAN-y dzielą sieć na osobne domeny rozgłoszeniowe i podsieci (lekcja 02/05). Żeby host z VLAN 10 porozmawiał z hostem z VLAN 20, potrzebny jest **router** — urządzenie z nogą w obu podsieciach, które jest bramą domyślną dla każdej z nich.

**Router-on-a-stick** (klasyka CCNA): jedno łącze trunk 802.1Q do przełącznika, na routerze podinterfejsy:
\`interface Gi0/0.10\` → \`encapsulation dot1Q 10\` → \`ip address 10.10.0.1 255.255.255.0\` (i analogicznie .20; natywny VLAN: \`encapsulation dot1Q 99 native\`). Zaleta: tani, działa z każdym przełącznikiem L2. Wada: ruch między VLAN-ami przechodzi **dwa razy** przez jedno łącze i przez CPU/forwarding routera — wąskie gardło.

**SVI na przełączniku L3** (standard w kampusie i centrum danych): \`ip routing\`, \`interface Vlan10\` → \`ip address 10.10.0.1 255.255.255.0\` → \`no shutdown\`. Routing odbywa się w ASIC-u przełącznika z pełną prędkością. SVI jest UP tylko, gdy VLAN istnieje i ma przynajmniej jeden aktywny port (lub trunk) — klasyczna pułapka „SVI down/down”. Trzecia opcja: **routed port** (\`no switchport\`) — port przełącznika L3 działający jak interfejs routera (łącza do routerów/firewalli).

Dla developera: brama domyślna Twojego serwera lub poda to często SVI albo jego wirtualny odpowiednik (FHRP — lekcja 15). W chmurze tę rolę pełni niewidoczny router VPC pod adresem \`.1\` podsieci.` },

  { type: 'CODE_PRACTICAL', heading: 'Trasy statyczne jako kod: Terraform (AWS) i netplan', body:
`W praktyce developera/DevOps trasy statyczne rzadko wpisuje się ręcznie — są w IaC. Te same zasady (prefiks, next hop, zapas, trasa powrotna) obowiązują w tablicach routingu VPC i w konfiguracji serwerów.`,
    code:
`# Terraform: tablica routingu podsieci prywatnej — domyślna przez NAT GW, sieć biura przez Transit Gateway
resource "aws_route_table" "private_a" {
  vpc_id = aws_vpc.main.id
  route { cidr_block = "0.0.0.0/0"     nat_gateway_id     = aws_nat_gateway.a.id }
  route { cidr_block = "10.100.0.0/16" transit_gateway_id = aws_ec2_transit_gateway.hub.id }   # biuro / VPN
  # trasa local (10.20.0.0/16) jest dodawana automatycznie; LPM: 10.100.x → TGW, reszta → NAT
}
resource "aws_route_table_association" "private_a" {
  subnet_id = aws_subnet.private[0].id
  route_table_id = aws_route_table.private_a.id
}

# netplan (Ubuntu): serwer z interfejsem zarządzania — trasa do sieci adminów i zapas z wyższą metryką
network:
  version: 2
  ethernets:
    eth1:
      addresses: [10.0.50.10/24]
      routes:
        - to: 10.99.0.0/16
          via: 10.0.50.1
          metric: 10
        - to: 10.99.0.0/16
          via: 10.0.50.2          # drugi router zarządzania — „pływająca” w wersji Linux (metryka)
          metric: 200
# sudo netplan try   ← rollback po 120 s, jeśli stracisz łączność (zawsze przy zmianach tras na zdalnym serwerze!)` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **Oddziały i małe sieci**: router z jedną trasą domyślną do operatora i pływającą przez LTE/5G (AD 250) — klasyczny zapas łącza.
- **Chmura**: każda trasa w route table VPC jest statyczna (poza propagacją z VPN/TGW/BGP); trasy do sieci on-premises, peeringu, endpointów. Błąd w jednej trasie = „połowa usług nie działa”.
- **Kontenery i Kubernetes**: proste CNI (host-gw, Flannel host-gw) instalują na węzłach trasy statyczne do podsieci podów innych węzłów.
- **Serwery z kilkoma interfejsami**: trasy statyczne do sieci zarządzania/backupu przez właściwy interfejs (lekcja 07 — asymetria).
- **Testy**: trasa /32 lub blackhole do zależności zewnętrznej, żeby zasymulować jej awarię (chaos engineering na poziomie sieci).

**Blue team:**
- **Trasy statyczne są odporne na fałszywe ogłoszenia** (brak protokołu do przejęcia) — dlatego krytyczne trasy (np. do sieci zarządzania) bywają statyczne; ale wymagają kontroli zmian.
- **RTBH (Remotely Triggered Black Hole)**: trasa do Null0 dla adresu atakowanego lub atakującego, rozgłaszana do routerów brzegowych — szybkie odcięcie ruchu DDoS.
- **Null route dla agregatów** zapobiega pętlom routingu, gdy część agregatu nie istnieje.
- **Niespodziewana nowa trasa statyczna** (szczególnie /32 do DNS, proxy, serwera aktualizacji lub domyślna przez nieznany next hop) to wskaźnik kompromitacji lub nieautoryzowanej zmiany — monitoruj konfigurację (RANCID/Oxidized, Git diff konfiguracji, syslog „%SYS-5-CONFIG_I”).
- Mapowanie: CCNA (routing statyczny, AD, inter-VLAN), Network+ (routing, redundancja), Security+ (kontrola zmian, DDoS mitigation).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Trasa pływająca z AD niższym niż główna** — np. zapas statyczny AD 1 obok głównego OSPF (110): zapas wygrywa zawsze. Zapas dla OSPF musi mieć AD > 110 (np. 130), dla eBGP > 20, dla statyka > 1.
- **Oczekiwanie, że statyk wykryje awarię za sąsiadem** — usuwany jest tylko wtedy, gdy next hop staje się nieosiągalny lokalnie (interfejs DOWN). Na Ethernecie przez przełącznik interfejs zwykle zostaje UP nawet przy martwym routerze. Potrzebne IP SLA/BFD.
- **Trasa „tylko interfejs” na Ethernecie** — ARP o każdy cel, zależność od proxy ARP, ogromne tablice ARP.
- **IPv6 z next hopem link-local bez interfejsu** — trasa odrzucona lub nieprawidłowa.
- **Brak trasy powrotnej** — dodałeś trasę na r1, ale r2/srv nie wie, jak odpowiedzieć.
- **Pętla routingu** — dwa routery z trasami domyślnymi wskazującymi na siebie nawzajem; pakiety krążą do wyczerpania TTL (traceroute pokazuje powtarzające się adresy).
- **Router-on-a-stick bez \`encapsulation dot1Q\`** lub z niezgodnym natywnym VLAN-em; **SVI down**, bo VLAN nie ma aktywnych portów albo brak \`ip routing\` na przełączniku L3.
- **Zmiana tras na zdalnym serwerze bez zabezpieczenia** — odcinasz sobie SSH; używaj \`netplan try\`, \`at now + 5 min\` z rollbackiem lub konsoli out-of-band.
- **Linux: oczekiwanie AD** — jądro zna tylko metrykę; AD działa w FRR/BIRD, które instalują zwycięzców do jądra.` },

  { type: 'CODE_WRONG', heading: 'Źle: zapas, który nigdy nie zadziała (albo działa zawsze)', body:
`Dwa typowe błędy konfiguracji zapasowego łącza na routerze oddziału.`,
    code:
`! główne łącze przez OSPF (AD 110), zapas przez LTE:
ip route 0.0.0.0 0.0.0.0 192.168.100.1          ! AD 1 — wygrywa z OSPF ZAWSZE, cały ruch idzie przez LTE (i płacisz za transfer)
! albo zapas do głównej trasy statycznej przez przełącznik operatora:
ip route 0.0.0.0 0.0.0.0 203.0.113.1            ! główna
ip route 0.0.0.0 0.0.0.0 192.168.100.1 250      ! pływająca
! awaria routera operatora ZA przełącznikiem: Gi0/0 nadal UP → główna zostaje w RIB → oddział bez Internetu` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: AD dobrany do źródła i śledzenie osiągalności (IP SLA)', body:
`Zapas z AD wyższym niż źródło główne oraz trasa główna powiązana z sondą IP SLA — znika, gdy przestaje odpowiadać zdalny punkt (np. DNS operatora), nie tylko gdy lokalny interfejs padnie.`,
    code:
`ip sla 1
 icmp-echo 198.51.100.53 source-interface GigabitEthernet0/0     ! zdalny cel za łączem operatora
 frequency 5
ip sla schedule 1 life forever start-time now
track 1 ip sla 1 reachability
 delay down 10 up 30                                             ! histereza — bez „mrugania” trasą
ip route 0.0.0.0 0.0.0.0 203.0.113.1 track 1                    ! główna, zależna od sondy
ip route 0.0.0.0 0.0.0.0 192.168.100.1 250                      ! pływająca przez LTE
! (przy OSPF jako głównym źródle zapas ma AD > 110, np. 130)
show track 1 ; show ip sla statistics 1 ; show ip route 0.0.0.0` },

  { type: 'WHEN_TO_USE', heading: 'Statycznie czy dynamicznie, i jaki AD dla zapasu', body:
`Ściąga projektowa:`,
    code:
`SYTUACJA                                              WYBÓR
mała sieć / oddział z jednym wyjściem                  statyczna trasa domyślna
zapasowe łącze (LTE, drugi operator)                   pływająca: AD > AD trasy głównej (+ IP SLA/BFD)
sieć z wieloma routerami i zmianami topologii          routing dynamiczny (OSPF — lekcja 14), statyki tylko na brzegu
trasa do sieci partnera przez VPN                      statyczna (lub BGP przy wielu prefiksach i redundancji)
chmura (VPC route tables)                              statyczne trasy w IaC; propagacja z VGW/TGW, gdy dynamiczne
blokada/odcięcie ruchu, agregaty                       Null0 / blackhole (RTBH przy DDoS)
routing między VLAN-ami w LAB/małej sieci              router-on-a-stick
routing między VLAN-ami w kampusie                     SVI na przełączniku L3 (+ FHRP dla redundancji bramy)
IPv6 z bramą link-local                                trasa w pełni określona: interfejs + fe80::…
zapas dla:  static 1 → AD 2–254 · eBGP 20 → 21+ · OSPF 110 → 111+ · iBGP 200 → 201+` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Trasa statyczna: prefiks + next hop (rekurencja) / interfejs (P2P) / oba (w pełni określona; wymagana dla link-local IPv6). Domyślna 0.0.0.0/0, hosta /32, sieciowa.
- Wybór: najdłuższy prefiks → przy tym samym prefiksie najniższy **AD** (connected 0, static 1, eBGP 20, OSPF 110, RIP 120, iBGP 200) → metryka w obrębie protokołu.
- **Trasa pływająca**: AD wyższy niż trasa główna; instalowana, gdy główna zniknie. Statyk znika tylko przy lokalnej utracie next hopa — na awarie dalej IP SLA/track lub BFD.
- Linux nie ma AD (metryka); AD jest w FRR/BIRD i routerach.
- Routing między VLAN-ami: router-on-a-stick (podinterfejsy dot1Q) lub SVI na przełączniku L3 (\`ip routing\`), routed ports.
- Null0/blackhole: agregaty i RTBH. Zmiany tras na zdalnych serwerach — z rollbackiem (\`netplan try\`).
- Następna lekcja: **NAT, PAT i port forwarding** — conntrack, wyczerpanie portów, hairpin NAT i NAT w Dockerze.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const down of [[], ['Gi0/1'], ['Gi0/0'], ['Gi0/2'], ['Gi0/1', 'Gi0/2'], ['Gi0/4']]) {
  ex(`Router ma kandydatów: ${MIXED.map((c) => `${c.prefix} ${c.source}${c.ad ? ` AD ${c.ad}` : ''} metric ${c.metric} via ${c.via} (${c.dev})`).join('; ')}. Podaj zawartość RIB ${down.length ? `po awarii ${down.join(' i ')}` : 'w stanie normalnym'}.`,
    'Dla każdego prefiksu: najniższy AD spośród tras przez działające interfejsy.',
    Object.values(rib(MIXED, down)).map(fmt).join('; ') || 'brak tras');
}
for (const [main, mainAd] of [['OSPF', 110], ['eBGP', 20], ['static', 1], ['iBGP', 200], ['RIP', 120], ['EIGRP (wewn.)', 90]]) {
  ex(`Trasa główna do sieci pochodzi z ${main} (AD ${mainAd}). Jaki AD nadasz zapasowej trasie statycznej, żeby była pływająca, i co się stanie, jeśli dasz AD ${mainAd - 1 < 1 ? 1 : mainAd - 1}?`,
    'Pływająca: AD wyższy niż główna.', `Dowolny AD od ${mainAd + 1} do 254 (np. ${Math.min(mainAd + 20, 250)}). Z AD ${mainAd - 1 < 1 ? 1 : mainAd - 1} ${mainAd === 1 ? 'oba statyki mają AD 1 — przy tym samym prefiksie router zainstaluje oba (ECMP) lub zachowanie zależy od platformy; zapas przestaje być zapasem' : 'zapas wygrywałby z trasą główną zawsze — ruch szedłby łączem zapasowym'}.`);
}
const more = [
  ['Wykonaj Guided Lab z planu: FRR w trzech routerach — skonfiguruj trasę główną i pływającą (AD 250), zerwij łącze r1–r2 i zanotuj przełączenie; potem wyłącz łącze r2–srv i wyjaśnij, dlaczego ruch nie przełączył się.', 'show ip route przed/po; statyk zależy od lokalnego next hopa.', 'Normalnie: S>* 198.51.100.0/24 [1/0] via 10.0.12.2, trasa [250/0] via 10.0.13.2 nieaktywna. Po ip link set (n12) down na r1: główna znika, instalowana [250/0] via 10.0.13.2; ping przechodzi przez r3 (srv ma trasę powrotną przez r3 z wyższą metryką). Po awarii r2–srv: łącze r1–r2 UP, next hop 10.0.12.2 osiągalny → trasa główna zostaje, ruch ginie w r2 (r2 nie ma już trasy do srv lub wysyła w próżnię). Rozwiązanie: BFD/IP SLA lub routing dynamiczny.'],
  ['Skonfiguruj router-on-a-stick dla VLAN 10 (10.10.0.0/24), 20 (10.20.0.0/24) i natywnego 99 na Gi0/0 oraz odpowiadający trunk na przełączniku.', 'Podinterfejsy, encapsulation dot1Q, switchport mode trunk.', 'Router: interface Gi0/0 / no shutdown; interface Gi0/0.10 / encapsulation dot1Q 10 / ip address 10.10.0.1 255.255.255.0; interface Gi0/0.20 / encapsulation dot1Q 20 / ip address 10.20.0.1 255.255.255.0; interface Gi0/0.99 / encapsulation dot1Q 99 native. Przełącznik: interface Gi1/0/24 / switchport mode trunk / switchport trunk native vlan 99 / switchport trunk allowed vlan 10,20,99. Hosty: brama .1 w swoim VLAN-ie. Weryfikacja: show ip interface brief, show interfaces trunk, ping między VLAN-ami.'],
  ['Skonfiguruj routing między VLAN-ami 10 i 20 na przełączniku L3 przy użyciu SVI i wymień 3 przyczyny, dla których SVI może być w stanie down.', 'ip routing; interface VlanX.', 'ip routing; vlan 10; vlan 20; interface Vlan10 / ip address 10.10.0.1 255.255.255.0 / no shutdown; interface Vlan20 / ip address 10.20.0.1 255.255.255.0 / no shutdown. SVI down: VLAN nie istnieje w bazie VLAN (vlan 10 nie utworzony), brak jakiegokolwiek aktywnego portu dostępowego/trunku z tym VLAN-em (autostate), SVI administracyjnie wyłączony (shutdown); dodatkowo brak ip routing = SVI UP, ale ruch nie jest routowany.'],
  ['Dodaj trasę domyślną IPv6 na routerze Cisco przez sąsiada o adresie link-local fe80::1 na Gi0/0 oraz trasę do 2001:db8:9::/48 przez 2001:db8:12::2. Dlaczego pierwsza wymaga interfejsu?', 'Link-local jest unikalny tylko na łączu.', 'ipv6 unicast-routing; ipv6 route ::/0 GigabitEthernet0/0 FE80::1; ipv6 route 2001:db8:9::/48 2001:db8:12::2. Adres fe80::1 może istnieć na każdym łączu routera (link-local jest unikalny tylko w obrębie łącza), więc sam adres nie określa, przez który interfejs wysłać ruch — trzeba podać interfejs (trasa w pełni określona). Adres globalny 2001:db8:12::2 jest rozwiązywany rekurencyjnie przez trasę connected.'],
  ['Wyjaśnij, czym jest trasa do Null0 przy agregacji i jak zapobiega pętli routingu.', 'Agregat z dziurami, trasa domyślna u sąsiada.', 'Router ogłasza agregat 10.0.0.0/16, ale ma tylko część podsieci. Pakiet do nieistniejącej 10.0.99.0/24 trafia do niego (agregat), nie pasuje do żadnej szczegółowej trasy i — gdyby nie Null0 — poszedłby jego trasą domyślną do routera wyżej, który odesłałby go z powrotem (agregat) → pętla aż do TTL. Trasa 10.0.0.0/16 Null0 (dłuższa niż default) sprawia, że takie pakiety są odrzucane od razu. OSPF/EIGRP tworzą ją automatycznie przy sumaryzacji.'],
  ['Opisz RTBH (Remotely Triggered Black Hole) i rolę tras statycznych/Null0 w obronie przed DDoS.', 'Trasa do Null0 dla atakowanego adresu, propagacja BGP.', 'RTBH: na routerze wyzwalającym dodaje się trasę statyczną do atakowanego adresu (/32) z tagiem; jest ona redystrybuowana do BGP z next hopem wskazującym na adres, który na wszystkich routerach brzegowych jest skierowany do Null0. W efekcie ruch do ofiary jest odrzucany na brzegu sieci (lub u operatora przez community blackhole, np. 65535:666 — RFC 7999), zanim zatka łącza. Wada: ofiara staje się nieosiągalna (atak „wygrywa”, ale reszta sieci działa). Wariant source-based RTBH z uRPF odrzuca ruch od atakujących źródeł.'],
  ['Dlaczego w Linuksie bez demona routingu nie ma dystansu administracyjnego i jak FRR/BIRD się z tym łączą?', 'RIB demona vs FIB jądra; metryka.', 'Jądro Linuksa przechowuje tylko trasy do przekazywania (FIB) z metryką i protokołem pochodzenia, bez pojęcia AD. Demony (FRR zebra, BIRD) zbierają trasy z wielu źródeł (static, OSPF, BGP), wybierają zwycięzców według AD/preferencji i instalują je w jądrze przez netlink (proto zebra/bird). Bez demona zapas robi się metryką: dwie trasy o tym samym prefiksie, wyższa metryka = zapas; trasa przez interfejs DOWN jest usuwana (lub ignorowana przy linkdown).'],
  ['W tablicy routingu VPC prywatnej podsieci są trasy: 10.20.0.0/16 local, 10.100.0.0/16 → TGW, 0.0.0.0/0 → NAT GW. Dokąd trafi ruch do 10.100.5.5, 10.20.7.7, 8.8.8.8 i 10.101.0.1?', 'LPM w tablicy VPC.', '10.100.5.5 → Transit Gateway (/16 pasuje); 10.20.7.7 → local (w obrębie VPC); 8.8.8.8 → NAT Gateway (tylko default); 10.101.0.1 → NAT Gateway (pasuje tylko 0.0.0.0/0) — ruch do „sieci firmowej”, której nie ma w tablicy, wyjdzie do Internetu i zginie: częsty błąd przy dodawaniu nowych zakresów biura.'],
  ['Zaprojektuj zapasowe łącze LTE dla oddziału z routerem Cisco: główne łącze światłowodowe z trasą statyczną, zapas LTE. Uwzględnij wykrywanie awarii za łączem i powrót.', 'IP SLA, track, delay, AD 250.', 'Główna: ip route 0.0.0.0 0.0.0.0 <brama operatora> track 1; IP SLA 1 icmp-echo do stabilnego celu za łączem (np. DNS operatora lub własny serwer w chmurze) przez interfejs główny, frequency 5; track 1 ip sla 1 reachability z delay down 10 up 60 (histereza). Zapas: ip route 0.0.0.0 0.0.0.0 <brama LTE> 250. NAT dla obu interfejsów (route-map per interfejs). Monitoring: syslog zmian stanu track, alert o pracy na LTE (koszty). Test: odłączenie światłowodu i zablokowanie celu sondy.'],
  ['Jak wykryć pętlę routingu w traceroute i co ją typowo powoduje przy trasach statycznych?', 'Powtarzające się adresy, TTL exceeded.', 'Traceroute pokazuje te same dwa (lub więcej) adresy na zmianę (np. 10.0.12.2, 10.0.12.11, 10.0.12.2…) aż do maksymalnej liczby skoków; ping kończy się „TTL expired in transit”. Przyczyny: dwa routery z trasami (domyślnymi lub do tej samej sieci) wskazującymi na siebie nawzajem, brak trasy do Null0 dla agregatu, błędna trasa powrotna. Naprawa: popraw trasę na jednym z routerów, dodaj Null0 dla agregatów.'],
  ['Opisz bezpieczną procedurę zmiany tras statycznych na zdalnym serwerze Linux, do którego łączysz się przez SSH.', 'netplan try, at, konsola.', '1) Zapisz bieżący stan (ip route > /root/routes-before.txt). 2) Zmiany przez netplan try (automatyczny rollback po 120 s bez potwierdzenia) lub zaplanuj rollback: echo "netplan apply /root/backup.yaml" | at now + 5 min, a po sukcesie atrm. 3) Miej dostęp out-of-band (konsola chmury, iDRAC/iLO). 4) Zmieniaj jedną rzecz, testuj ip route get i połączenie SSH z innej sesji. 5) Utrwal w repozytorium IaC i opisz w zgłoszeniu zmiany.'],
  ['Wyjaśnij różnicę między RIB a FIB i jak zobaczyć jedną i drugą w FRR/Linux oraz na Cisco.', 'show ip route vs show ip cef; ip route.', 'RIB: wszyscy zwycięzcy wyboru tras (z informacją o źródle i AD), na Cisco show ip route, w FRR vtysh show ip route (z flagami > wybrana, * zainstalowana w FIB). FIB: struktura używana do przekazywania pakietów — na Cisco show ip cef (CEF), w Linuksie tablica jądra (ip route). Różnice zdarzają się, gdy trasa jest w RIB, ale nie mogła być zainstalowana (np. next hop nierozwiązywalny) — w FRR brak gwiazdki *.'],
  ['Dlaczego trasa statyczna „ip route 10.9.0.0 255.255.0.0 Gi0/1” na Ethernecie może działać tylko dzięki proxy ARP i jakie ma to skutki?', 'ARP o każdy cel.', 'Router uznaje cel za bezpośrednio podłączony i wysyła zapytanie ARP o każdy adres docelowy z 10.9.0.0/16. Odpowie tylko sąsiedni router z włączonym proxy ARP (podając swój MAC). Skutki: tysiące wpisów ARP (po jednym na każdy cel), obciążenie CPU, zależność od proxy ARP (często wyłączanego ze względów bezpieczeństwa) — po jego wyłączeniu ruch przestaje działać. Na Ethernecie zawsze podawaj next hop.'],
  ['Twoja aplikacja w Kubernetes (CNI host-gw) nie łączy się z podami na nowym węźle. Na starych węzłach brakuje trasy do podsieci podów nowego węzła. Wyjaśnij mechanizm i diagnozę.', 'Statyczne trasy CNI per węzeł.', 'W trybie host-gw (Flannel) każdy węzeł ma trasę statyczną do podsieci podów każdego innego węzła przez jego adres (np. 10.244.3.0/24 via 192.168.1.13). Trasy dodaje agent CNI na podstawie obiektów Node; jeśli agent na starych węzłach nie zauważył nowego węzła (błąd, brak uprawnień, inna podsieć L2 — host-gw wymaga wspólnego L2), trasy brak. Diagnoza: ip route | grep 10.244 na węźle, kubectl get nodes -o wide i podCIDR, logi daemonsetu CNI, ip route get <IP poda>.'],
  ['Porównaj trasę statyczną i OSPF z perspektywy bezpieczeństwa (odporność na manipulację, ryzyko błędów, audyt).', 'Brak protokołu vs uwierzytelnianie OSPF.', 'Statyczna: brak protokołu, więc nie da się wstrzyknąć fałszywej trasy z sieci; ryzyko — błąd człowieka i brak reakcji na awarie; audyt przez kontrolę zmian konfiguracji. OSPF: automatycznie reaguje na awarie, ale wymaga uwierzytelniania (SHA) i pasywnych interfejsów w sieciach użytkowników — inaczej atakujący w LAN może zestawić sąsiedztwo i rozgłosić trasy; audyt przez monitorowanie sąsiedztw i LSDB. W praktyce: OSPF w rdzeniu z uwierzytelnianiem, statyki na brzegu i do krytycznych sieci zarządzania.'],
  ['Wymień trzy sposoby wykrycia awarii next hopa przez trasę statyczną i oceń czas reakcji każdego.', 'Interfejs DOWN, IP SLA, BFD.', '1) Utrata nośnika na łączu bezpośrednim (interfejs DOWN) — natychmiast, ale tylko przy łączu P2P bez przełącznika pośrodku. 2) IP SLA + track (ICMP/TCP/HTTP do celu) — sekundy do dziesiątek sekund (frequency × próby + delay), wykrywa też awarie dalej. 3) BFD — dziesiątki–setki milisekund (np. 3 × 50 ms), wykrywa awarię ścieżki do sąsiada; wymaga obsługi po obu stronach.'],
  ['Zinterpretuj fragment FRR: „S>* 198.51.100.0/24 [1/0] via 10.0.12.2, eth1, weight 1, 00:05:12” oraz „S   198.51.100.0/24 [250/0] via 10.0.13.2, eth2 inactive”.', 'Kody i nawiasy.', 'S — trasa statyczna; > — wybrana (najlepsza dla prefiksu); * — zainstalowana w FIB (jądrze); [1/0] — AD 1 / metryka 0; via 10.0.12.2 przez eth1, weight dla ECMP, od 5 min 12 s. Druga: statyczna z AD 250, nie wybrana (brak >) i nieaktywna — trasa pływająca czekająca na awarię głównej.'],
  ['Na routerze są trasy 10.9.0.0/16 static via 10.0.12.2 i 10.9.5.0/24 OSPF [110/20] via 10.0.13.2. Dokąd pójdzie pakiet do 10.9.5.7 i do 10.9.6.1? Czy AD ma tu znaczenie?', 'LPM przed AD.', '10.9.5.7 → OSPF via 10.0.13.2 (dłuższy prefiks /24); 10.9.6.1 → static via 10.0.12.2 (pasuje tylko /16). AD nie ma znaczenia, bo prefiksy są różne — AD porównuje się tylko między trasami do tego samego prefiksu.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
