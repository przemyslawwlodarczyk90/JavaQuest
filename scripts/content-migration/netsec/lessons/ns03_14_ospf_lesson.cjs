// Generuje lekcje _ns_03_ip_i_routing/14_OspfSingleArea.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_14_ospf_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/14_OspfSingleArea.json
// Kod Javy (SPF) jest w ns03_14_OspfSpf.java.txt — przetestowany na JDK 25.
const fs = require('fs');
const path = require('path');
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { cost, spf, paths, electDr, STATES, LINKS, graphFrom } = require('./ns03_14_ospf_model.cjs');
const buildQuiz = require('./ns03_14_ospf_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const JAVA_SPF = fs.readFileSync(path.join(__dirname, 'ns03_14_OspfSpf.java.txt'), 'utf8').trim();
const route = (ref, ov = {}) => { const g = graphFrom(LINKS, ref, ov), r = spf(g, 'R1'); return { g, r, p: paths(r, 'R4', 'R1') }; };
const D100 = route(100), D10k = route(10000), DOV = route(10000, { 'R1-R2': 500, 'R2-R1': 500 });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Routery, które same rysują mapę sieci', ...img('ns03-14-lsa.svg', 'Przepływ w OSPF: LSA typu 1 (router) i typu 2 (network, od DR) tworzą identyczną bazę LSDB w obszarze, każdy router liczy SPF algorytmem Dijkstry i instaluje trasy O z AD 110.'),
    body:
`Trasy statyczne (lekcja 08) wystarczają w małej sieci. W sieci z kilkudziesięcioma routerami i łączami zapasowymi ręczne utrzymywanie tras jest niemożliwe — i nie reaguje na awarie. Tu wchodzi **routing dynamiczny**, a w sieciach firmowych i centrach danych najczęściej **OSPF (Open Shortest Path First)**.

OSPF to protokół **link-state**: każdy router opisuje swoje łącza i ich **koszt** w komunikatach **LSA**, rozsyła je do wszystkich routerów w **obszarze**, dzięki czemu każdy ma tę samą **mapę sieci (LSDB)** i sam liczy z niej najkrótsze ścieżki algorytmem **Dijkstry (SPF)**. Gdy łącze padnie, zmienia się mapa, wszyscy przeliczają trasy — w sekundy, bez pętli.

Po co to Java developerowi? Bo czas zbieżności OSPF to czas, przez który Twoja aplikacja widzi timeouty po awarii łącza w serwerowni; bo w Kubernetes i centrach danych spotkasz routing dynamiczny (Calico, MetalLB, Cilium z BGP — następne lekcje); bo błędy typu „po zmianie MTU zniknęła połowa tras” lub „ruch idzie wolnym łączem” to konfiguracja OSPF; a przede wszystkim — bo CCNA v2 wymaga OSPFv2 w jednym obszarze.

W tej lekcji: link-state vs distance-vector, pakiety Hello/DBD/LSR/LSU/LSAck, **stany sąsiedztwa** (Down → Full), Router ID, **DR/BDR**, **koszt i referencyjna przepustowość** (z klasyczną pułapką), OSPFv2 vs OSPFv3, typowe problemy (timery, MTU, maski, obszary), uwierzytelnianie oraz **Guided Lab z planu** na FRR: 4 routery, odczyt LSDB, zmiana kosztu i nowej ścieżki, zepsute MTU i stan EXSTART.` },

  { type: 'DEFINITION', heading: 'Słownik OSPF', body:
`- **OSPF** — protokół routingu link-state (IGP) działający bezpośrednio nad IP (**protokół 89**); OSPFv2 dla IPv4 (RFC 2328), OSPFv3 dla IPv6 (RFC 5340).
- **Router ID (RID)** — 32-bitowy identyfikator routera w zapisie IPv4: ustawiony ręcznie (\`router-id\`), inaczej najwyższy adres loopback, inaczej najwyższy adres aktywnego interfejsu; musi być unikalny.
- **Obszar (area)** — grupa routerów z tą samą LSDB; obszar 0 (**backbone**) — w tej lekcji jedyny.
- **LSA (Link-State Advertisement)** — opis fragmentu sieci: **typ 1 (Router LSA)** — łącza i koszty routera, **typ 2 (Network LSA)** — segment broadcast opisany przez DR; (3, 4, 5, 7 — przy wielu obszarach i redystrybucji).
- **LSDB** — baza wszystkich LSA obszaru, identyczna na każdym routerze.
- **SPF (Dijkstra)** — algorytm liczący drzewo najkrótszych ścieżek od siebie do wszystkich celów; wynik trafia do RIB jako trasy **O** z AD **110**.
- **Koszt** — metryka interfejsu: **referencyjna przepustowość / przepustowość interfejsu** (min. 1); domyślna referencja **100 Mb/s** — dziś za mała. Można też ustawić koszt ręcznie (\`ip ospf cost\`).
- **Pakiety OSPF**: 1 **Hello** (sąsiedztwo, keepalive), 2 **DBD** (opis bazy), 3 **LSR** (żądanie LSA), 4 **LSU** (aktualizacja), 5 **LSAck** (potwierdzenie).
- **Stany sąsiedztwa**: ${STATES.join(' → ')}.
- **DR/BDR** — Designated Router i zapasowy na segmencie broadcast (Ethernet z wieloma routerami): reszta (DROTHER) ma pełne sąsiedztwo tylko z DR i BDR. Wybór: najwyższy priorytet interfejsu (0 = nigdy), potem najwyższy RID; bez wywłaszczania.
- **Typy sieci**: broadcast (DR/BDR), point-to-point (bez DR — zalecane dla łączy między dwoma routerami), NBMA, point-to-multipoint.
- **Timery**: Hello 10 s / Dead 40 s (broadcast, P2P) — muszą być zgodne u sąsiadów; szybsze wykrywanie awarii: **BFD**.
- **Interfejs pasywny** — sieć jest ogłaszana, ale na interfejsie nie są wysyłane Hello (sieci użytkowników, serwerów) — ochrona przed obcymi routerami.
- **Multicast**: 224.0.0.5 AllSPFRouters, 224.0.0.6 AllDRouters (OSPFv3: ff02::5, ff02::6).` },

  { type: 'ANALOGY', heading: 'Mapa miasta zamiast drogowskazów od sąsiadów', body:
`**Distance-vector (RIP)** to podróżowanie według rad sąsiadów: pytasz na skrzyżowaniu „jak daleko do dworca?” — „5 przecznic w lewo”, i ufasz. Nikt nie ma całej mapy; jeśli most się zawali, wieść rozchodzi się powoli, a rady mogą się zapętlić („idź do niego”, „nie, do niego”).

**Link-state (OSPF)** to sytuacja, w której każdy kierowca ma **tę samą, aktualną mapę miasta**. Każde skrzyżowanie (router) rozsyła wszystkim ogłoszenie: „ode mnie wychodzą takie ulice, o takiej przepustowości” (**LSA**). Wszyscy składają z tego identyczną mapę (**LSDB**), a każdy sam wyznacza najkrótszą drogę nawigacją (**Dijkstra**). Zamknięcie ulicy = nowe ogłoszenie, wszyscy przeliczają trasy.

**Koszt** to „czas przejazdu” ulicy: autostrada tania, osiedlowa droga droga. Pułapka referencji 100 Mb/s to mapa, na której każda droga szybsza niż „100 km/h” dostaje ten sam czas przejazdu — więc nawigacja uznaje autostradę i drogę lokalną za równoważne i wysyła połowę aut lokalną.

**DR** na rondzie z wieloma wjazdami to dyżurny: zamiast żeby każdy wjazd ustalał wszystko z każdym (n(n−1)/2 rozmów), wszyscy rozmawiają tylko z dyżurnym i jego zastępcą (**BDR**).

**Stan EXSTART** to dwóch kierowców, którzy próbują wymienić się mapami, ale jeden ma za mały bagażnik (MTU) na paczkę drugiego — wiecznie zaczynają od nowa i nigdy nie kończą.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Koszt i ścieżka: pułapka referencji 100 Mb/s', ...img('ns03-14-cost.svg', 'Topologia R1–R4 z łączami 1 Gb/s, 100 Mb/s i 10 Mb/s. Przy domyślnej referencji 100 Mb/s łącza 1 Gb/s i 100 Mb/s mają koszt 1, więc R1 rozkłada ruch do R4 na dwie ścieżki (także wolną). Przy referencji 10 000 Mb/s koszty to 10 i 100, wybierana jest tylko ścieżka przez R2.'),
    body:
`Topologia labu: R1–R2 i R2–R4 po 1 Gb/s, R1–R3 i R3–R4 po 100 Mb/s, R2–R3 10 Mb/s. SPF policzony modelem lekcji (i tym samym algorytmem w Javie niżej):`,
    code:
`koszt = max(1, referencja / przepustowość)
referencja 100 Mb/s:    10 Mb/s → ${cost(10)}   100 Mb/s → ${cost(100)}   1 Gb/s → ${cost(1000)}   10 Gb/s → ${cost(10000)}
referencja 10 000 Mb/s: 10 Mb/s → ${cost(10, 10000)}  100 Mb/s → ${cost(100, 10000)}  1 Gb/s → ${cost(1000, 10000)}  10 Gb/s → ${cost(10000, 10000)}
referencja 100 000 Mb/s: 25 Gb/s → ${cost(25000, 100000)}  100 Gb/s → ${cost(100000, 100000)}

ref 100:     koszty od R1 ${JSON.stringify(D100.r.dist)}  →  R1→R4: ${D100.p.map((x) => x.join('→')).join(' oraz ')} (ECMP — także przez 100 Mb/s!)
ref 10 000:  koszty od R1 ${JSON.stringify(D10k.r.dist)}  →  R1→R4: ${D10k.p.map((x) => x.join('→')).join(', ')}
ref 10 000 + ip ospf cost 500 na R1–R2: ${JSON.stringify(DOV.r.dist)}  →  R1→R4: ${DOV.p.map((x) => x.join('→')).join(', ')}
   (R2 osiągany teraz przez R3 i R4: 100 + 100 + 10 = ${DOV.r.dist.R2})` },

  { type: 'CODE_BASIC', heading: 'Konfiguracja OSPFv2: FRR i Cisco IOS', ...img('ns03-14-dr.svg', 'Pięć routerów w jednym VLAN-ie: R3 z priorytetem 100 zostaje DR, R2 z najwyższym Router ID wśród pozostałych BDR, R1 i R4 DROTHER, R5 z priorytetem 0 nigdy nie zostanie DR.'),
    body:
`Konfiguracja jednego routera w obszarze 0. Dwa style włączania OSPF na interfejsach: polecenie \`network\` z wildcardem (klasyczne CCNA) albo \`ip ospf area 0\` na interfejsie (czytelniejsze). FRR celowo przypomina IOS.`,
    code:
`! FRR (vtysh → configure) — R1
router ospf
 ospf router-id 1.1.1.1
 auto-cost reference-bandwidth 10000          ! ta sama wartość na WSZYSTKICH routerach
 passive-interface default                    ! domyślnie bez Hello…
 no passive-interface eth1                    ! …poza łączami do routerów
 no passive-interface eth2
 network 10.0.12.0/30 area 0
 network 10.0.13.0/30 area 0
 network 192.168.10.0/24 area 0               ! sieć użytkowników — ogłaszana, ale pasywna
interface eth1
 ip ospf network point-to-point               ! łącze między dwoma routerami: bez DR/BDR
 ip ospf cost 10                              ! ręczny koszt (nadpisuje auto-cost)
 ip ospf hello-interval 10
 ip ospf dead-interval 40

! Cisco IOS — to samo (network z wildcardem albo na interfejsie)
router ospf 1
 router-id 1.1.1.1
 auto-cost reference-bandwidth 10000
 passive-interface default
 no passive-interface GigabitEthernet0/1
 network 10.0.12.0 0.0.0.3 area 0
interface GigabitEthernet0/1
 ip ospf network point-to-point
 ip ospf 1 area 0
interface GigabitEthernet0/2
 ip ospf priority 100                         ! kandydat na DR w segmencie broadcast

! Weryfikacja (IOS i FRR)
show ip ospf neighbor ; show ip ospf interface brief ; show ip ospf database ; show ip route ospf` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab z planu: OSPF na FRR w 4 routerach', body:
`Cztery kontenery FRR (każdy to osobny network namespace) połączone sieciami Dockera jak na rysunku kosztów. Wymaga Dockera (Linux/WSL2).
1. **compose.yaml**: usługi r1–r4, obraz \`quay.io/frrouting/frr:10.1.1\`, \`cap_add: [NET_ADMIN, SYS_ADMIN]\`, \`sysctls: { net.ipv4.ip_forward: 1 }\`, wolumen z plikiem \`daemons\` (\`ospfd=yes\`) i \`frr.conf\`. Sieci (z \`ipam.subnet\`): n12 10.0.12.0/24, n24 10.0.24.0/24, n13 10.0.13.0/24, n34 10.0.34.0/24, n23 10.0.23.0/24; adresy routerów .1x (Docker zajmuje .1 na bramę mostu). Za R4 sieć serwerów: interfejs dummy \`ip link add srv0 type dummy; ip addr add 192.168.40.1/24 dev srv0\`.
2. **Konfiguracja**: w każdym \`frr.conf\` — \`router ospf\`, unikalny \`ospf router-id\`, \`network … area 0\` dla wszystkich jego sieci, na R4 także 192.168.40.0/24 (pasywnie), \`ip ospf network point-to-point\` na łączach. Na tym etapie zostaw **domyślną referencję 100**.
3. **Sąsiedztwa**: \`docker compose exec r1 vtysh -c "show ip ospf neighbor"\` → dwóch sąsiadów w stanie **Full**. \`show ip ospf database\` → po jednym Router LSA (typ 1) od każdego z 4 routerów (na łączach P2P nie ma Network LSA).
4. **Ścieżka**: \`show ip route 192.168.40.0/24\` na R1 → **dwie** ścieżki równego kosztu (przez R2 i przez R3) — pułapka referencji. Potwierdź \`ip route get 192.168.40.10\` (Linux wybierze jedną z tras ECMP per przepływ).
5. **Napraw koszt**: \`auto-cost reference-bandwidth 10000\` na **wszystkich** routerach (Docker nie raportuje rzeczywistej prędkości veth — ustaw koszty ręcznie: \`ip ospf cost 10\` na łączach „1G”, \`100\` na „100M”, \`1000\` na „10M”). Teraz jedna trasa przez R2.
6. **Zmień koszt i obserwuj**: na R1 \`interface <do R2>\` → \`ip ospf cost 500\`; po kilku sekundach trasa do 192.168.40.0/24 idzie przez R3 (koszt 200). W \`show ip ospf database router 1.1.1.1\` widać nowy koszt łącza w Router LSA R1.
7. **Zepsuj MTU**: na R2 \`ip link set <iface do R4> mtu 1400\` (R4 zostaje 1500). Zrestartuj sąsiedztwo (\`clear ip ospf process\` lub \`ip link set … down/up\`). \`show ip ospf neighbor\` na R4 → sąsiad R2 utknął w **ExStart/Exchange**, w logach FRR: komunikaty o odrzuceniu DBD z niezgodnym MTU. Trasy przez łącze R2–R4 znikają.
8. **Napraw**: wyrównaj MTU po obu stronach (właściwa naprawa) albo — jeśli różnica jest celowa i znana — \`ip ospf mtu-ignore\` po obu stronach (ryzyko: zbyt duże LSU zostaną odrzucone). Sąsiedztwo wraca do **Full**.
9. **Sprzątanie i notatka**: \`docker compose down\`; zapisz LSDB, tabele tras przed/po zmianie kosztu, wynik \`show ip ospf neighbor\` z EXSTART i wniosek.` },

  { type: 'NOTE', heading: 'Stany sąsiedztwa i typowe problemy', ...img('ns03-14-states.svg', 'Stany sąsiedztwa OSPF: Down, Init, 2-Way (wybór DR/BDR), ExStart (ustalanie master/slave, tu zatrzymuje się przy niezgodnym MTU), Exchange (DBD), Loading (LSR i LSU), Full. Typy pakietów i parametry Hello.'),
    body:
`Większość problemów z OSPF widać w **stanie sąsiedztwa** i w tym, na którym kroku utknął:
- **Brak sąsiada w ogóle (Down)** — Hello nie dochodzi: interfejs pasywny, ACL/firewall blokuje protokół 89 lub multicast 224.0.0.5, różne podsieci na łączu, zły VLAN.
- **Init** — widzę Hello sąsiada, ale on nie widzi moich: problem jednokierunkowy (ACL w jedną stronę, filtr multicastu).
- **Hello się nie zgadza → brak sąsiedztwa** — różne **obszary**, **maski** (na sieciach broadcast), **timery Hello/Dead**, **uwierzytelnianie**, typ obszaru (stub), **duplikat Router ID**.
- **2-Way między DROTHER** — to normalne, nie błąd (pełne sąsiedztwo tylko z DR/BDR).
- **ExStart/Exchange** — klasycznie **niezgodne MTU** (DBD większe niż MTU sąsiada są odrzucane), także duplikat RID.
- **Full, ale brak tras** — sieć nie jest ogłaszana (brak \`network\`/\`ip ospf area\`), zła maska w \`network\`, trasa przegrywa z lepszym AD (statyczna), filtr dystrybucji.
- **Trasy jest, ale ruch idzie „dziwnie”** — koszty (referencja!), ECMP, asymetria.
- **Wolna reakcja na awarię** — Dead interval 40 s (gdy łącze nie traci nośnika, np. przez przełącznik): dla aplikacji 40 s timeoutów. Rozwiązanie: **BFD** (wykrycie w setki ms) lub krótsze timery.

**OSPFv3 (IPv6)** działa na adresach link-local, LSA nie zawierają prefiksów w Router LSA (osobne Intra-Area-Prefix LSA), a jeden proces może obsługiwać IPv4 i IPv6 (address families). Koncepcje — sąsiedztwa, DR, koszty, SPF — te same.` },

  { type: 'CODE_PRACTICAL', heading: 'SPF w Javie: ten sam algorytm, który liczy OSPF', body:
`Dijkstra z zachowaniem ścieżek równego kosztu (ECMP) na topologii z labu. Program przyjmuje referencyjną przepustowość jako argument — uruchom go z 100 i 10000 i porównaj wynik z tablicą tras FRR. Algorytm przyda się też w zadaniach rekrutacyjnych i w narzędziach (np. symulacja „co się stanie z ruchem po awarii łącza X”).`,
    code: JAVA_SPF + `
// java OspfSpf.java 100    → R1 -> R4: [R1>R2>R4, R1>R3>R4]   (ECMP — także przez 100 Mb/s)
// java OspfSpf.java 10000  → R1 -> R4: [R1>R2>R4]` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **Czas zbieżności = czas niedostępności**: po awarii łącza bez utraty nośnika OSPF czeka Dead interval (40 s) — w tym czasie Twoje połączenia mogą wisieć. Ustaw w aplikacji rozsądne timeouty i retry; po stronie sieci — BFD.
- **Centra danych**: OSPF (lub IS-IS) w underlay sieci spine-leaf, BGP do serwerów i Kubernetesa; w laboratoriach i małych środowiskach FRR na Linuksie robi to samo co routery sprzętowe.
- **Chmura**: OSPF zwykle nie jest dostępny w VPC (tam trasy statyczne i BGP z VPN/Direct Connect), ale spotkasz go po stronie on-premises w połączeniach hybrydowych.
- **MTU w overlay/VPN** (lekcja 11) potrafi zerwać sąsiedztwa OSPF w tunelach — objaw EXSTART.

**Blue team:**
- **Wstrzyknięcie tras**: atakujący w sieci użytkowników uruchamia FRR/Quaggę, nawiązuje sąsiedztwo i ogłasza trasy (np. bardziej szczegółowe prefiksy lub trasę domyślną) — przejmuje ruch (MITM) lub tworzy czarną dziurę. Obrona: **interfejsy pasywne** wszędzie poza łączami do routerów, **uwierzytelnianie** OSPF (HMAC-SHA wg RFC 5709 / key chain; MD5 tylko jako starsza opcja), ACL dla protokołu 89, segmentacja.
- **Monitoring**: zdarzenia zmiany sąsiedztwa (syslog \`%OSPF-5-ADJCHG\`, logi FRR \`ospfd\`), nowe RID w LSDB, nagły wzrost liczby LSA — reguły w SIEM.
- **DoS**: flood LSA / częste zmiany (flapping) obciążają CPU; ochrona: CoPP, tłumienie (throttling) SPF i LSA.
- Mapowanie: CCNA v2 domena 3 (OSPFv2 single-area: sąsiedztwa, DR/BDR, Router ID, P2P, koszt), Network+ (protokoły routingu dynamicznego), Security+ (ataki na routing).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Domyślna referencja 100 Mb/s** — łącza 100M, 1G, 10G mają ten sam koszt; ruch idzie wolnymi łączami (ECMP). Ustaw np. 10 000 lub 100 000 — **na wszystkich** routerach.
- **Różne referencje na routerach** — niespójne koszty, nieoptymalne lub asymetryczne ścieżki.
- **Niezgodne MTU** po obu stronach łącza → EXSTART/EXCHANGE.
- **Niezgodne timery Hello/Dead, obszary, maski, uwierzytelnianie** → brak sąsiedztwa.
- **Duplikat Router ID** (np. sklonowane VM/kontenery z tym samym loopbackiem) — dziwne, flapping LSA.
- **Brak \`passive-interface\` w sieciach użytkowników** — ktokolwiek z FRR może zostać sąsiadem i ogłaszać trasy.
- **Oczekiwanie wywłaszczania DR** — router z wyższym priorytetem nie przejmie roli, dopóki obecny DR działa.
- **Typ sieci broadcast na łączu dwóch routerów** — zbędny wybór DR i wolniejsza zbieżność; ustaw point-to-point (po obu stronach!).
- **Zła maska wildcard w \`network\`** — sieć nieogłoszona lub ogłoszona zbyt szeroko.
- **Zmiana Router ID bez restartu procesu** — nie zadziała do \`clear ip ospf process\`.` },

  { type: 'CODE_WRONG', heading: 'Źle: OSPF „na wszystkich interfejsach” bez ochrony', body:
`Konfiguracja, która działa w laboratorium, a w firmie pozwala dowolnemu komputerowi w sieci użytkowników zostać routerem i przejąć ruch.`,
    code:
`router ospf 1
 network 0.0.0.0 255.255.255.255 area 0      ! OSPF na KAŻDYM interfejsie, także w VLAN-ach użytkowników
 ! brak router-id → RID z adresu interfejsu, zmienia się po zmianie adresacji
 ! brak auto-cost reference-bandwidth → 1G i 100M z tym samym kosztem
 ! brak passive-interface → Hello wysyłane do laptopów; FRR na laptopie = nowy sąsiad
 ! brak uwierzytelniania → sąsiad z dowolnymi LSA (np. 0.0.0.0/0 lub /32 serwera płatności)` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: jawny RID, referencja, pasywne interfejsy, uwierzytelnianie, BFD', body:
`Minimalny „hardening” OSPF i ustawienia, które sprawiają, że koszty odpowiadają przepustowości, a awarie są wykrywane szybko.`,
    code:
`key chain OSPF-KEYS
 key 1
  key-string <sekret z sejfu>
  cryptographic-algorithm hmac-sha-256
router ospf 1
 router-id 10.255.0.1
 auto-cost reference-bandwidth 100000          ! 100 Gb/s — spójnie na wszystkich routerach
 passive-interface default
 no passive-interface GigabitEthernet0/1
 bfd all-interfaces                             ! wykrycie awarii w setki ms
interface GigabitEthernet0/1
 ip ospf network point-to-point
 ip ospf authentication key-chain OSPF-KEYS
 ip ospf 1 area 0
! sieci użytkowników: ip ospf 1 area 0 na interfejsie, ale pasywnie (tylko ogłaszane)
! monitoring: syslog %OSPF-5-ADJCHG → SIEM, alarm na nowy RID i nieoczekiwane prefiksy` },

  { type: 'WHEN_TO_USE', heading: 'Objaw → co sprawdzić w OSPF', body:
`Ściąga diagnostyczna:`,
    code:
`OBJAW                                         SPRAWDŹ                                          POLECENIE
brak sąsiada                                   pasywny interfejs, ACL proto 89, podsieć, VLAN   show ip ospf interface ; tcpdump proto 89
sąsiad w INIT                                  Hello tylko w jedną stronę                       debug/log Hello, ACL
brak sąsiedztwa mimo Hello                     obszar, maska, timery, auth, duplikat RID        show ip ospf interface (Hello/Dead)
EXSTART / EXCHANGE                             MTU po obu stronach                              show interface ; ip link
2-WAY między DROTHER                           normalne                                          show ip ospf neighbor
Full, ale brak trasy                           network/area, AD (statyczna wygrywa), filtr      show ip ospf database ; show ip route
ruch idzie wolnym łączem                       referencja, koszty, ECMP                         show ip ospf interface (Cost) ; show ip route
wolne przełączanie po awarii                   Dead interval, brak BFD                          show bfd peers
częste przeliczenia SPF / flapping             niestabilne łącze, duplikat RID                  show ip ospf statistics ; logi` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- OSPF: link-state, protokół IP 89, AD 110; każdy router ma tę samą LSDB z LSA (typ 1 router, typ 2 network) i liczy SPF (Dijkstra).
- Sąsiedztwo: Down → Init → 2-Way → ExStart → Exchange → Loading → Full; Hello musi się zgadzać (obszar, maska, timery 10/40, auth, unikalny RID); MTU sprawdzany w DBD (EXSTART).
- DR/BDR na segmentach broadcast (priorytet, potem RID, bez wywłaszczania); na łączach dwóch routerów — point-to-point.
- Koszt = referencja / przepustowość (min. 1); domyślne 100 Mb/s zrównuje 100M/1G/10G — ustaw większą referencję wszędzie lub koszty ręcznie.
- Bezpieczeństwo: pasywne interfejsy, uwierzytelnianie HMAC-SHA, monitoring sąsiedztw; szybkość: BFD.
- OSPFv3 dla IPv6 (link-local, ff02::5/6) — te same koncepcje.
- Następna lekcja: **redundancja bramy: HSRP, VRRP i GLBP** — z keepalived przed parą reverse proxy.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [bw, ref] of [[10, 100], [100, 100], [1000, 100], [10000, 100], [100, 10000], [1000, 10000], [25000, 100000], [40000, 100000]]) {
  ex(`Jaki koszt OSPF ma interfejs ${bw >= 1000 ? bw / 1000 + ' Gb/s' : bw + ' Mb/s'} przy referencji ${ref.toLocaleString('pl-PL')} Mb/s?`, 'koszt = max(1, floor(ref / bw)).', `${cost(bw, ref)}.`);
}
for (const [ref, ov, label] of [[100, {}, 'domyślna referencja 100'], [10000, {}, 'referencja 10 000'], [10000, { 'R1-R2': 500, 'R2-R1': 500 }, 'referencja 10 000 i koszt 500 na łączu R1–R2'], [10000, { 'R2-R4': 300, 'R4-R2': 300 }, 'referencja 10 000 i koszt 300 na łączu R2–R4']]) {
  const g = graphFrom(LINKS, ref, ov), r = spf(g, 'R1'), p = paths(r, 'R4', 'R1');
  ex(`Topologia z lekcji (R1–R2 1G, R2–R4 1G, R1–R3 100M, R3–R4 100M, R2–R3 10M), ${label}. Policz SPF z R1: koszty do wszystkich routerów i ścieżkę(i) do R4.`, 'Dijkstra; przy równym koszcie — ECMP.', `Koszty: ${JSON.stringify(r.dist)}; R1→R4: ${p.map((x) => x.join('→')).join(' oraz ')}.`);
}
for (const rs of [
  [{ name: 'R1', prio: 1, rid: '1.1.1.1' }, { name: 'R2', prio: 1, rid: '2.2.2.2' }, { name: 'R3', prio: 1, rid: '3.3.3.3' }],
  [{ name: 'R1', prio: 200, rid: '1.1.1.1' }, { name: 'R2', prio: 1, rid: '9.9.9.9' }, { name: 'R3', prio: 0, rid: '10.0.0.1' }],
  [{ name: 'R1', prio: 0, rid: '1.1.1.1' }, { name: 'R2', prio: 0, rid: '2.2.2.2' }, { name: 'R3', prio: 5, rid: '3.3.3.3' }, { name: 'R4', prio: 5, rid: '4.4.4.4' }],
]) {
  const e = electDr(rs);
  ex(`Routery na jednym segmencie broadcast startują jednocześnie: ${rs.map((r) => `${r.name} (priorytet ${r.prio}, RID ${r.rid})`).join(', ')}. Który zostanie DR, a który BDR?`, 'Najwyższy priorytet (0 = nie kandyduje), przy remisie najwyższy RID.', `DR: ${e.dr}, BDR: ${e.bdr ?? 'brak'}.`);
}
const more = [
  ['Wykonaj Guided Lab z planu: OSPF na FRR w 4 routerach — odczytaj LSDB, zmień koszt i obserwuj nową ścieżkę; zepsuj MTU i zdiagnozuj stan EXSTART.', 'show ip ospf neighbor/database, ip ospf cost, ip link mtu.', `LSDB: 4 Router LSA (typ 1), brak Network LSA na łączach P2P. Przy domyślnej referencji trasa do 192.168.40.0/24 ma dwie ścieżki (ECMP przez R2 i R3); po ustawieniu kosztów zgodnych z przepustowością — tylko przez R2 (koszt 20 przy ref 10 000). ip ospf cost 500 na R1–R2 → trasa przez R3 (koszt 200), nowy koszt widoczny w Router LSA R1. MTU 1400 na R2 vs 1500 na R4 → sąsiad w ExStart/Exchange, trasy przez łącze znikają; naprawa: wyrównanie MTU (ewentualnie ip ospf mtu-ignore po obu stronach).`],
  ['Dlaczego dwóch DROTHER-ów na tym samym segmencie pozostaje w stanie 2-Way i czy to błąd?', 'Pełne sąsiedztwo tylko z DR i BDR.', 'Na segmencie broadcast routery DROTHER synchronizują bazę wyłącznie z DR i BDR (wysyłają LSU na 224.0.0.6, DR rozsyła na 224.0.0.5). Między sobą zatrzymują się w 2-Way — to zamierzone zachowanie zmniejszające liczbę sąsiedztw i floodingu, nie błąd.'],
  ['Policz liczbę pełnych sąsiedztw (Full) dla 6 routerów w jednym VLAN-ie bez DR/BDR i z DR/BDR.', 'n(n−1)/2 vs 2(n−2)+1.', 'Bez DR (każdy z każdym): 6·5/2 = 15. Z DR i BDR: DR–BDR 1 + każdy z 4 DROTHER z DR i z BDR 4·2 = 8 → 9.'],
  ['Wymień pięć parametrów, które muszą się zgadzać w pakietach Hello, aby powstało sąsiedztwo OSPF, i jeden, który sprawdzany jest dopiero w DBD.', 'Hello vs DBD.', 'Hello: identyfikator obszaru (i jego typ, np. stub), maska podsieci (na sieciach broadcast/NBMA), Hello interval, Dead interval, uwierzytelnianie (typ i klucz); dodatkowo unikalny Router ID i ta sama podsieć. MTU jest porównywany dopiero w pakietach DBD (stan ExStart/Exchange).'],
  ['Na routerze R1 ustawiono router-id 1.1.1.1, ale show ip ospf nadal pokazuje 10.0.12.11. Dlaczego i co zrobić?', 'Zmiana RID wymaga restartu procesu.', 'Router ID jest ustalany przy starcie procesu OSPF; zmiana w konfiguracji zacznie obowiązywać dopiero po restarcie procesu: clear ip ospf process (Cisco/FRR) — co zrywa sąsiedztwa, więc robi się to w oknie serwisowym.'],
  ['Wyjaśnij różnicę między poleceniem network 10.0.12.0 0.0.0.3 area 0 a ip ospf area 0 na interfejsie.', 'Dopasowanie adresów interfejsów.', 'network z wildcardem włącza OSPF na wszystkich interfejsach, których adres pasuje do zakresu (tu 10.0.12.0–10.0.12.3), i ogłasza ich sieci; nie oznacza „ogłoś tę sieć” w sensie trasy statycznej. ip ospf area 0 (Cisco: ip ospf <proces> area 0) włącza OSPF bezpośrednio na konkretnym interfejsie — czytelniejsze i bez ryzyka złego wildcardu.'],
  ['Atakujący w sieci użytkowników uruchamia FRR i ogłasza trasę 10.50.1.10/32. Jakie warunki muszą być spełnione, by atak się udał, i jak mu zapobiec?', 'Sąsiedztwo, LPM, ochrona.', 'Interfejs routera w VLAN-ie użytkowników musi wysyłać Hello (nie być pasywny), parametry Hello muszą się zgadzać (obszar, timery, maska), brak uwierzytelniania (lub znany klucz). Wtedy sąsiedztwo dochodzi do Full, a /32 jako dłuższy prefiks przejmie ruch do 10.50.1.10 (MITM lub czarna dziura). Zapobieganie: passive-interface w sieciach użytkowników, uwierzytelnianie HMAC-SHA, ACL blokujące protokół 89 z sieci użytkowników, monitoring nowych sąsiadów i nowych RID w LSDB.'],
  ['Dlaczego po awarii przełącznika między dwoma routerami (łącze routerów nie traci nośnika) ruch przestaje płynąć na ok. 40 s i jak to skrócić?', 'Dead interval, BFD.', 'Interfejsy routerów są UP (połączone z przełącznikiem), więc OSPF nie widzi awarii od razu — dopiero po braku Hello przez Dead interval (domyślnie 40 s) sąsiedztwo spada i SPF wyznacza nowe trasy. Skrócenie: BFD (wykrycie w setki ms, np. 3 × 100 ms) zintegrowane z OSPF, ewentualnie krótsze timery Hello/Dead (np. 1/4 s; większe obciążenie), łącza bezpośrednie bez przełącznika pośrodku.'],
  ['Porównaj OSPFv2 i OSPFv3.', 'Adresy, LSA, uwierzytelnianie.', 'OSPFv2 — IPv4, pakiety z adresów interfejsów, multicast 224.0.0.5/6, uwierzytelnianie w nagłówku OSPF (MD5/HMAC-SHA). OSPFv3 — zaprojektowany dla IPv6: działa na adresach link-local (ff02::5/6), prefiksy przeniesione do osobnych LSA (Link LSA typ 8, Intra-Area-Prefix LSA typ 9), Router LSA opisuje tylko topologię; uwierzytelnianie przez IPsec lub trailer (RFC 7166); może obsługiwać IPv4 i IPv6 przez address families. Koncepcje sąsiedztw, DR/BDR, kosztów i SPF — takie same.'],
  ['W show ip route widzisz „O 192.168.40.0/24 [110/20] via 10.0.12.12”. Rozszyfruj wpis.', 'Kod, AD, metryka.', 'O — trasa wewnątrzobszarowa OSPF; [110/20] — dystans administracyjny 110 / koszt OSPF 20 (suma kosztów interfejsów wyjściowych na ścieżce); via 10.0.12.12 — następny skok (adres sąsiada na łączu). Gdyby była trasa statyczna do tej samej sieci (AD 1), wygrałaby z OSPF.'],
  ['Dlaczego na łączu między dwoma routerami warto ustawić ip ospf network point-to-point po obu stronach?', 'Brak DR/BDR, szybsza zbieżność.', 'Na Ethernecie domyślnym typem jest broadcast — routery wybierają DR/BDR (czekając Wait timer = Dead interval przy starcie), DR generuje Network LSA (więcej LSA w bazie). Przy point-to-point nie ma wyboru DR, sąsiedztwo powstaje szybciej, LSDB jest prostsza. Typ musi być zgodny po obu stronach — inaczej problemy z sąsiedztwem i trasami (np. niezgodne timery lub brak tras).'],
  ['Jak w FRR szybko sprawdzić koszt każdego interfejsu OSPF i kto jest DR na segmencie?', 'show ip ospf interface.', 'vtysh -c "show ip ospf interface" — dla każdego interfejsu: obszar, typ sieci, Cost, stan (DR, BDR, DROther, Point-To-Point), priorytet, adresy DR i BDR, timery Hello/Dead, liczba sąsiadów. Skrócona wersja na Cisco: show ip ospf interface brief.'],
  ['Jakie zdarzenia OSPF warto przesyłać do SIEM i jakie reguły na nich zbudować?', 'Sąsiedztwa, RID, prefiksy.', 'Zmiany stanów sąsiedztwa (Cisco %OSPF-5-ADJCHG, FRR logi ospfd „Neighbor … Full/Down”), błędy uwierzytelniania (niezgodny klucz), nowe Router ID w LSDB, zmiany konfiguracji routerów. Reguły: nowe sąsiedztwo na interfejsie, który powinien być pasywny lub z nieznanego RID; częste flapowanie sąsiada (niestabilne łącze lub atak); pojawienie się nieoczekiwanych prefiksów (np. /32 dla serwerów krytycznych lub 0.0.0.0/0 spoza routerów brzegowych).'],
  ['Wyjaśnij, dlaczego referencyjna przepustowość musi być taka sama na wszystkich routerach obszaru.', 'Spójność kosztów w SPF.', 'Każdy router liczy koszt swoich interfejsów z własnej referencji i ogłasza go w Router LSA; SPF na każdym routerze sumuje koszty ogłoszone przez innych. Przy różnych referencjach to samo łącze 1 Gb/s ma koszt 1 u jednego routera i 10 u drugiego — ścieżki stają się nieoptymalne i asymetryczne (ruch tam inną drogą niż z powrotem), co psuje stanowe firewalle i utrudnia diagnozę.'],
  ['Uruchom program OspfSpf z lekcji dla referencji 100 i 10000, a potem zmodyfikuj go tak, by wypisywał trasy do wszystkich routerów oraz pierwszy skok (next hop) z R1 — jak w tablicy routingu.', 'Pierwszy skok = drugi element ścieżki.', 'Dla każdego celu d ≠ R1: List<String> ps = paths(prev, "R1", d); zbiór pierwszych skoków = ps.stream().map(p -> p.split(">")[1]).collect(toCollection(TreeSet::new)); wypisz "d koszt=dist.get(d) via " + nextHops. Dla ref 100: R4 przez {R2, R3} (ECMP), R2 przez {R2}, R3 przez {R3}; dla ref 10000: R4 przez {R2}, R3 przez {R3} (koszt 100). To dokładnie odpowiada wpisom „O … via …” w show ip route.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
