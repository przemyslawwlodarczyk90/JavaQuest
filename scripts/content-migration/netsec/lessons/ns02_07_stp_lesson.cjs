// Generuje lekcje _ns_02_ethernet_lan/07_StpRstpDeepDive.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_07_stp_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/07_StpRstpDeepDive.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { stp, COST_SHORT, COST_LONG } = require('./ns02_07_stp_sim.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const gi = (p) => `Gi0/${p}`;
const S = (n, prio, mac) => ({ n, prio, mac });

// Topologie do zadan i pytan (wyniki liczy symulator)
const TOPO = [
  { name: 'T1 (trójkąt, równe priorytety)', sw: [S('SW1', 32768, '00:1a:00:00:00:03'), S('SW2', 32768, '00:1a:00:00:00:01'), S('SW3', 32768, '00:1a:00:00:00:02')],
    links: [['SW1', 1, 'SW2', 1, '1G'], ['SW1', 2, 'SW3', 1, '1G'], ['SW2', 2, 'SW3', 2, '1G']] },
  { name: 'T2 (trójkąt, SW3 z priorytetem 4096)', sw: [S('SW1', 32768, '00:00:00:00:00:01'), S('SW2', 32768, '00:00:00:00:00:02'), S('SW3', 4096, '00:ff:00:00:00:03')],
    links: [['SW1', 1, 'SW2', 1, '1G'], ['SW1', 2, 'SW3', 1, '1G'], ['SW2', 2, 'SW3', 2, '1G']] },
  { name: 'T3 (pierścień czterech przełączników)', sw: [S('SW1', 24576, '00:aa:00:00:00:01'), S('SW2', 32768, '00:aa:00:00:00:02'), S('SW3', 32768, '00:aa:00:00:00:03'), S('SW4', 32768, '00:aa:00:00:00:04')],
    links: [['SW1', 1, 'SW2', 1, '1G'], ['SW2', 2, 'SW3', 1, '1G'], ['SW3', 2, 'SW4', 1, '1G'], ['SW4', 2, 'SW1', 2, '1G']] },
  { name: 'T4 (rdzeń i dostęp)', sw: [S('CORE1', 24576, '00:aa:00:00:00:01'), S('CORE2', 28672, '00:aa:00:00:00:02'), S('ACC1', 32768, '00:0b:00:00:00:01'), S('ACC2', 32768, '00:0b:00:00:00:02')],
    links: [['CORE1', 1, 'CORE2', 1, '10G'], ['CORE1', 2, 'ACC1', 1, '1G'], ['CORE1', 3, 'ACC2', 1, '1G'], ['CORE2', 2, 'ACC1', 2, '1G'], ['CORE2', 3, 'ACC2', 2, '1G']] },
  { name: 'T5 (trójkąt, wolne łącze 100M)', sw: [S('SW1', 28672, '00:10:00:00:00:01'), S('SW2', 32768, '00:10:00:00:00:02'), S('SW3', 32768, '00:10:00:00:00:03')],
    links: [['SW1', 1, 'SW2', 1, '100M'], ['SW1', 2, 'SW3', 1, '1G'], ['SW3', 2, 'SW2', 2, '1G']] },
  { name: 'T6 (dwa równoległe łącza)', sw: [S('SW1', 32768, '00:01:00:00:00:01'), S('SW2', 32768, '00:01:00:00:00:02')],
    links: [['SW1', 3, 'SW2', 5, '1G'], ['SW1', 4, 'SW2', 6, '1G']] },
];
TOPO.forEach((t) => { t.r = stp(t.sw, t.links); });
const descTopo = (t) => `Przełączniki: ${t.sw.map((s) => `${s.n} (priorytet ${s.prio}, MAC ${s.mac})`).join(', ')}. Łącza: ${t.links.map(([a, pa, b, pb, sp]) => `${a} ${gi(pa)} — ${b} ${gi(pb)} (${sp})`).join(', ')}. Koszty short (1G = 4, 100M = 19, 10G = 2).`;
const blocked = (t) => Object.entries(t.r.roles).filter(([, v]) => v === 'alternate').map(([k]) => { const [s, p] = k.split(':'); return `${s} ${gi(+p)}`; });
const allPorts = (t) => t.links.flatMap(([a, pa, b, pb]) => [`${a} ${gi(pa)}`, `${b} ${gi(pb)}`]);
const fullSolution = (t) => {
  const r = t.r;
  const rp = Object.entries(r.rootPort).map(([s, p]) => `${s}: root port ${gi(p)}, koszt ${r.dist[s]}`).join('; ');
  const roles = Object.entries(r.roles).map(([k, v]) => { const [s, p] = k.split(':'); return `${s} ${gi(+p)} = ${v}`; }).join(', ');
  return `Root bridge: ${r.root} (najniższy priorytet, a przy remisie najniższy MAC). ${rp}. Role: ${roles}. Zablokowane: ${blocked(t).join(', ') || 'brak'}.`;
};

// ---------- TEORIA ----------
const T4 = TOPO[3];
const theory = [
  { type: 'INTRO', heading: 'Protokół, który pilnuje, żeby sieć nie popełniła samobójstwa', ...img('ns02-07-roles.svg', 'Topologia czterech przełączników: CORE1 z priorytetem 24576 jest rootem, CORE2 ma priorytet 28672, ACC1 i ACC2 po 32768. Łącze CORE1–CORE2 10G, pozostałe 1G. Wyliczone role: wszystkie porty CORE1 designated, CORE2 port 1 root, porty CORE2 w stronę dostępu designated, ACC1 i ACC2 mają root port w stronę CORE1, a porty w stronę CORE2 są alternate (zablokowane).'),
    body:
`W lekcji 05 zobaczyłeś, że redundantne połączenia przełączników tworzą **pętle L2**, a pętla bez ochrony oznacza burzę rozgłoszeniową w kilka sekund. **Spanning Tree Protocol** rozwiązuje to, wyłączając logicznie nadmiarowe łącza i trzymając je w odwodzie. Brzmi prosto, ale STP to protokół, który:
- **decyduje, którędy płynie cały ruch L2** w kampusie — źle ustawiony root bridge oznacza ruch przez stary przełącznik w piwnicy,
- **decyduje o czasie przestoju przy awarii** — klasyczny 802.1D potrzebuje 30–50 s, RSTP około sekundy; dla aplikacji to różnica między „nikt nie zauważył” a „zerwane sesje, timeouty, failover klastra i alert o 3:00”,
- **jest celem ataków** — urządzenie, które wyśle lepsze BPDU, może zostać rootem i przekierować przez siebie ruch albo wywołać ciągłe przebudowy topologii.

W tej lekcji wejdziemy w szczegóły na poziomie CCNA 200-301 (domena 2: STP/RSTP, role i stany portów, PortFast, BPDU Guard, Root Guard): budowa **BPDU** i **Bridge ID**, **algorytm** wyboru root bridge, root portów i designated portów (policzysz go ręcznie dla kilku topologii), **koszty ścieżek**, różnice **802.1D vs RSTP (802.1w) vs MST (802.1s)** i warianty Cisco (PVST+, Rapid PVST+), **mechanizmy ochronne** oraz objawy problemów z perspektywy aplikacji.

Topologia na ilustracji jest wyliczona tym samym algorytmem, którego się nauczysz — możesz sprawdzić każdą rolę portu krok po kroku.` },

  { type: 'DEFINITION', heading: 'Słownik STP i RSTP', body:
`- **BPDU (Bridge Protocol Data Unit)** — komunikat STP wysyłany co **hello time** (2 s) na multicast \`01:80:c2:00:00:00\` (Cisco PVST+: \`01:00:0c:cc:cc:cd\`); niesie Root ID, Root Path Cost, Bridge ID i Port ID nadawcy.
- **Bridge ID (BID)** — 8 bajtów: priorytet (4 bity, wielokrotność 4096) + **system ID extension** (12 bitów = numer VLAN) + MAC. Mniejszy = lepszy.
- **Root bridge** — przełącznik o najniższym BID; wszystkie jego porty są designated.
- **Root Path Cost** — suma kosztów portów na drodze do roota (liczona przy odbiorze BPDU).
- **Koszt portu** — zależny od prędkości; *short* (Cisco domyślnie): 10M = 100, 100M = 19, 1G = 4, 10G = 2; *long* (802.1D-2004, \`spanning-tree pathcost method long\`): 10M = 2 000 000, 100M = 200 000, 1G = 20 000, 10G = 2 000, 100G = 200.
- **Port ID** — priorytet portu (domyślnie 128) + numer portu; ostatnie kryterium rozstrzygające.
- **Role portów**: **root port (RP)** — najlepsza droga do roota (jeden na przełącznik niebędący rootem); **designated port (DP)** — jeden na segment, przekazuje w stronę od roota; **alternate** (RSTP) — zapasowa droga do roota, blokowana; **backup** (RSTP) — zapasowy port do tego samego segmentu (rzadkie, przy hubach); **disabled**.
- **Stany 802.1D**: blocking → listening (15 s) → learning (15 s) → forwarding; plus disabled. **Stany RSTP**: discarding, learning, forwarding.
- **Timery**: hello 2 s, **max age** 20 s (ile pamiętać BPDU), **forward delay** 15 s (czas listening i learning).
- **TCN (Topology Change Notification)** — informacja o zmianie topologii; skraca czas życia wpisów w tablicach MAC do 15 s (802.1D) lub je czyści (RSTP).
- **PVST+ / Rapid PVST+** — osobne drzewo STP/RSTP dla każdego VLAN-u (Cisco). **MST (802.1s)** — grupuje VLAN-y w kilka instancji (mniej BPDU i obciążenia CPU), wymaga spójnej konfiguracji regionu.
- **Port edge / PortFast** — port brzegowy (host), od razu forwarding; **BPDU Guard**, **BPDU Filter**, **Root Guard**, **Loop Guard**, **UDLD** — mechanizmy ochronne.` },

  { type: 'ANALOGY', heading: 'Wybory kapitana i najkrótsza droga do kapitanatu', body:
`Wyobraź sobie grupę wysp połączonych mostami, które co chwilę wymieniają się listami („BPDU”) z informacją: „Moim zdaniem kapitanem jest X, a ja mam do niego odległość Y”.
1. **Wybór kapitana (root bridge)**: każda wyspa na początku uważa się za kapitana. Gdy dostanie list z „lepszym” kandydatem (niższy priorytet, a przy remisie niższy numer seryjny — MAC), przyjmuje go i przekazuje dalej. Po chwili wszyscy zgadzają się co do jednego kapitana. Uwaga: przy równych priorytetach wygrywa **najniższy numer seryjny** — czyli zwykle **najstarsza** wyspa. Nie najlepiej położona ani najmocniejsza.
2. **Root port**: każda wyspa wybiera **jeden** most, którym najtaniej dotrze do kapitana (suma „opłat” za mosty — koszt zależny od szerokości mostu, czyli prędkości łącza).
3. **Designated port**: na każdym moście ustala się, która strona „odpowiada” za ruch od kapitana — ta, która jest bliżej kapitana.
4. **Pozostałe mosty** zamyka się szlabanem (blocking/alternate). Nie są zniszczone — strażnik nasłuchuje listów i otworzy szlaban, gdy główny most się zawali.

**802.1D** to strażnik ostrożny: po awarii odczekuje (max age 20 s), potem 15 s „słucha”, 15 s „uczy się”, i dopiero wtedy otwiera szlaban. **RSTP** to strażnik, który z góry wie, który most jest zapasowy (alternate), i po awarii otwiera go natychmiast, dogadując się z sąsiadem (proposal/agreement).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Algorytm krok po kroku na topologii z ilustracji', ...img('ns02-07-bpdu.svg', 'Struktura Bridge ID: 4 bity priorytetu, 12 bitów system ID extension (numer VLAN), 48 bitów adresu MAC; przykład 32768 + VLAN 10 = 32778. Pola BPDU: Root Identifier, Root Path Cost, Bridge Identifier, Port Identifier, flagi RSTP i timery. Kolejność porównań: Root ID, koszt, Bridge ID nadawcy, Port ID.'),
    body:
`Przejdźmy algorytm dla topologii rdzeń–dostęp z ilustracji wprowadzającej. Ta sama procedura działa dla każdej sieci — to dokładnie to, czego wymaga CCNA i co robisz przy diagnozie „dlaczego ruch idzie tędy”.`,
    code:
`${descTopo(T4)}

Krok 1 — root bridge: najniższy priorytet → ${T4.r.root} (24576).
Krok 2 — root porty (najniższy koszt do roota, przy remisie: BID sąsiada, Port ID sąsiada):
${Object.entries(T4.r.rootPort).map(([s, p]) => `  ${s.padEnd(6)} → ${gi(p)}, koszt do roota ${T4.r.dist[s]}`).join('\n')}
  ACC1: przez CORE1 koszt 4, przez CORE2 koszt 2 + 4 = 6 → wybiera ${gi(T4.r.rootPort.ACC1)} (do CORE1).
Krok 3 — designated porty: na każdym łączu port przełącznika bliższego rootowi
  (przy równym koszcie — niższy BID). Wszystkie porty roota są designated.
Krok 4 — reszta blokowana:
${Object.entries(T4.r.roles).map(([k, v]) => { const [s, p] = k.split(':'); return `  ${(s + ' ' + gi(+p)).padEnd(12)} ${v}`; }).join('\n')}

Kryteria porównania BPDU (zawsze w tej kolejności):
  1. najniższy Root Bridge ID  2. najniższy Root Path Cost
  3. najniższy Bridge ID nadawcy  4. najniższy Port ID nadawcy (5. własny Port ID)` },

  { type: 'CODE_BASIC', heading: 'Weryfikacja w Cisco IOS: show spanning-tree', body:
`Tak wygląda wynik na przełączniku ACC1 z naszej topologii (Rapid PVST+, VLAN 10). Naucz się czytać trzy bloki: Root ID (kto jest rootem i jak daleko), Bridge ID (kim ja jestem) i tabelę ról portów.`,
    code:
`ACC1# show spanning-tree vlan 10

VLAN0010
  Spanning tree enabled protocol rstp
  Root ID    Priority    24586                       ← 24576 + VLAN 10
             Address     00aa.0000.0001              ← CORE1
             Cost        4                           ← koszt ACC1 → root
             Port        1 (GigabitEthernet0/1)      ← root port
             Hello Time   2 sec  Max Age 20 sec  Forward Delay 15 sec

  Bridge ID  Priority    32778  (priority 32768 sys-id-ext 10)
             Address     000b.0000.0001

Interface           Role Sts Cost      Prio.Nbr Type
------------------- ---- --- --------- -------- ------------------
Gi0/1               Root FWD 4         128.1    P2p
Gi0/2               Altn BLK 4         128.2    P2p
Gi0/10              Desg FWD 4         128.10   P2p Edge          ← PortFast (host)

Inne przydatne:
show spanning-tree root                    ! root dla każdego VLAN-u, koszt, root port
show spanning-tree summary                 ! tryb, PortFast/BPDU Guard domyślnie, liczba portów w stanach
show spanning-tree detail | include ieee|occurr|from|is exec   ! ile zmian topologii i skąd ostatnia
show spanning-tree inconsistentports       ! porty zablokowane przez Root Guard / Loop Guard
show errdisable recovery                   ! porty wyłączone np. przez BPDU Guard` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab: przechwyć BPDU, zmień root bridge, zmierz zbieżność', ...img('ns02-07-timers.svg', 'Oś czasu: w 802.1D port przechodzi przez blocking do 20 s (max age), listening 15 s, learning 15 s — forwarding po 30–50 s; w RSTP proposal/agreement daje forwarding w około sekundę, port brzegowy natychmiast. Skutki dla aplikacji: timeouty DHCP, zerwane połączenia TCP, błędy health-checków.'),
    body:
`Laboratorium z planu lekcji — w izolowanym labie (Containerlab z cEOS/IOL lub GNS3/CML; w wersji minimum trzy bridge’e Linuksa w namespace’ach połączone w trójkąt):
1. **Zbuduj trójkąt** trzech przełączników, wszystkie z domyślnym priorytetem; podłącz po jednym hoście do dwóch z nich.
2. **Przechwyć BPDU** na dowolnym łączu: Wireshark z filtrem \`stp\` (lub \`tcpdump -i eth1 -e stp\`). Odczytaj: Root Identifier, Root Path Cost, Bridge Identifier, Port Identifier, wersję protokołu (0 = STP, 2 = RSTP) i flagi.
3. **Wyznacz ręcznie** root bridge, root porty i port zablokowany (algorytm z lekcji), potem porównaj z \`show spanning-tree\` (lub \`bridge -d link show\` / \`mstpctl\`).
4. **Wymuś zmianę roota**: na wybranym przełączniku \`spanning-tree vlan 1 priority 4096\` (lub \`root primary\`). Obserwuj w Wiresharku zmianę Root Identifier i flagi TC.
5. **Zmierz zbieżność 802.1D**: przełącz tryb na \`spanning-tree mode pvst\`, uruchom \`ping -i 0.2\` między hostami tak, by ruch szedł przez port root, i wyłącz to łącze. Policz utracone pakiety × 0,2 s — spodziewaj się 30–50 s.
6. **Powtórz dla RSTP** (\`spanning-tree mode rapid-pvst\`): przerwa zwykle poniżej 1–2 s.
7. **PortFast**: porównaj czas od podłączenia hosta do pierwszego udanego DHCP z PortFast i bez (w 802.1D — ok. 30 s różnicy).
8. **Zapisz wnioski** w tabeli: tryb, zdarzenie, czas przerwy, liczba utraconych pakietów. To argument, którego użyjesz, gdy ktoś zapyta, czemu aplikacja zrywa połączenia po „drobnej zmianie w sieci”.` },

  { type: 'NOTE', heading: '802.1D vs RSTP vs MST i warianty Cisco', body:
`Porównanie protokołów, które spotkasz w sieciach i na egzaminach:`,
    code:
`cecha                 802.1D (STP)          802.1w (RSTP)                802.1s (MST)
drzewa                jedno dla wszystkich  jedno dla wszystkich         kilka instancji (grupy VLAN)
Cisco odpowiednik     PVST+ (per VLAN)      Rapid PVST+ (per VLAN)       MST
stany portu           blk/lst/lrn/fwd/dis   discarding/learning/fwd      jak RSTP
role                  root, designated,     + alternate, backup          jak RSTP + boundary
                      (blocking)
zbieżność             30–50 s               ~1 s (proposal/agreement)    ~1 s
porty brzegowe        PortFast (Cisco)      edge port (standard)         edge port
reakcja na TC         skrócenie aging 15 s  natychmiastowe czyszczenie   jak RSTP
BPDU                  tylko od roota        każdy przełącznik co hello   jak RSTP
                      (przekazywane)        (keepalive sąsiada)
obciążenie CPU        PVST+: BPDU × VLAN    Rapid PVST+: BPDU × VLAN     mało — BPDU × instancja

Rekomendacja: Rapid PVST+ w sieciach Cisco do kilkuset VLAN-ów, MST w dużych i wielodostawczych;
klasyczny 802.1D tylko przy starych urządzeniach (RSTP jest z nim wstecznie zgodny, ale traci szybkość).
Koszty (short): ${Object.entries(COST_SHORT).map(([k, v]) => `${k}=${v}`).join(', ')}
Koszty (long):  ${Object.entries(COST_LONG).map(([k, v]) => `${k}=${v}`).join(', ')}` },

  { type: 'CODE_PRACTICAL', heading: 'Konfiguracja: root, tryb, PortFast i ochrony', ...img('ns02-07-guards.svg', 'Sieć hierarchiczna: CORE1 jako root, CORE2 zapasowy, trzy przełączniki dostępowe połączone z oboma rdzeniami, hosty na portach brzegowych. Root Guard na portach rdzenia w stronę dostępu, Loop Guard i UDLD na uplinkach, PortFast z BPDU Guard i storm control na portach użytkowników.'),
    body:
`Konfiguracja zgodna z dobrymi praktykami — rdzeń ma role roota i zapasu ustawione jawnie, dostęp jest chroniony. Odpowiednik w Linuksie dla bridge’a z RSTP: \`mstpd\` (\`mstpctl\`), a w Arista EOS składnia jest niemal identyczna jak w IOS.`,
    code:
`! === CORE1 ===
spanning-tree mode rapid-pvst
spanning-tree pathcost method long          ! koszty dla łączy ≥ 10G
spanning-tree vlan 1-4094 root primary      ! priorytet 24576 (lub niżej, jeśli ktoś ma mniejszy)
interface range Te1/1/1 - 3                 ! w stronę przełączników dostępowych
 spanning-tree guard root                   ! nikt „poniżej” nie zostanie rootem
!
! === CORE2 ===
spanning-tree vlan 1-4094 root secondary    ! priorytet 28672
!
! === ACC1..ACC3 ===
spanning-tree mode rapid-pvst
spanning-tree portfast default              ! wszystkie porty access jako edge
spanning-tree portfast bpduguard default    ! BPDU na porcie edge → err-disabled
spanning-tree loopguard default             ! jednokierunkowe łącze nie odblokuje portu
udld enable                                 ! wykrywanie łączy jednokierunkowych (światłowody)
errdisable recovery cause bpduguard
errdisable recovery interval 300            ! automatyczne włączenie po 5 min (opcjonalnie)
interface range Gi1/0/1 - 46
 switchport mode access
 storm-control broadcast level 1.00
!
! Linux (mstpd): mstpctl setforcevers br0 rstp ; mstpctl settreeprio br0 0 4 ; mstpctl setportadminedge br0 veth1 yes` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz: od zrywanych połączeń po atak na roota', body:
`**Developer / SRE — objawy STP w aplikacji:**
- **Przerwy 30–50 s** po awarii lub zmianie łącza (klasyczny STP): zerwane połączenia z pulą bazodanową (HikariCP zgłasza \`Connection is not available\`), timeouty klientów HTTP, failover klastra Kafka/Elasticsearch/Hazelcast („split brain” lub wyrzucenie węzła), alarmy health-checków load balancera.
- **Brak sieci przez ~30 s po starcie** maszyny lub kontenera z fizyczną kartą (PXE boot, DHCP timeout) — port bez PortFast.
- **Okresowe „czkawki” i chwilowe zalewanie ruchem** — częste TCN (np. port do laptopa bez PortFast albo niestabilne łącze), które czyszczą tablice MAC.
- **Nieoptymalna ścieżka** — ruch między serwerami w tej samej szafie idzie przez inny budynek, bo root bridge jest w złym miejscu.
- **Wirtualizacja** — hiperwizor (vSwitch) zwykle nie uczestniczy w STP; port do hosta ESXi/Proxmox powinien być edge (trunk z PortFast) i chroniony BPDU Guard, a pętle między vSwitchami wykluczone konfiguracją.

**Blue team:**
- **Przejęcie roota** — urządzenie wysyłające BPDU z priorytetem 0 zostaje rootem: ruch zaczyna płynąć przez nie (podsłuch/man-in-the-middle) lub sieć się destabilizuje. Obrona: **Root Guard** w stronę dostępu, **BPDU Guard** na portach użytkowników.
- **Zalewanie BPDU/TCN** — ciągłe zmiany topologii wymuszają flooding i obciążają CPU przełączników. Obrona: BPDU Guard, monitoring liczby zmian topologii.
- **Detekcja** — syslog: \`%SPANTREE-2-BLOCK_BPDUGUARD\`, \`%SPANTREE-2-ROOTGUARD_BLOCK\`, \`%SPANTREE-2-LOOPGUARD_BLOCK\`, \`%SW_MATM-4-MACFLAP_NOTIF\`, zmiana roota (\`show spanning-tree detail\`, SNMP \`newRoot\`/\`topologyChange\` trapy). Każda zmiana roota w VLAN-ie produkcyjnym powinna generować alert w SIEM.
- **Wyłączanie STP** jako „optymalizacja” to ryzyko dostępności — w audycie flaguj \`no spanning-tree vlan …\` i BPDU Filter na portach dostępowych.` },

  { type: 'PITFALL', heading: 'Częste błędy z STP', body:
`- **Root wybrany przez przypadek** — domyślne priorytety + najniższy MAC = najstarszy przełącznik. Zawsze \`root primary/secondary\` na rdzeniu.
- **PortFast na porcie do przełącznika** — port od razu przekazuje, zanim STP zdąży zablokować pętlę; PortFast tylko na portach do hostów (i zawsze z BPDU Guard).
- **BPDU Filter zamiast BPDU Guard** — filter na porcie po prostu ignoruje i nie wysyła BPDU, więc pętla przez ten port nie zostanie wykryta. Używany globalnie (\`portfast bpdufilter default\`) jest bezpieczniejszy, bo port po odebraniu BPDU traci status edge — ale na interfejsie to proszenie się o pętlę.
- **Mieszanie trybów** — część przełączników w PVST+, część w MST, część z RSTP innego dostawcy: granice regionów i współpraca działają, ale zbieżność i wybór roota bywają zaskakujące. Ujednolicaj.
- **MST: niespójna konfiguracja regionu** (nazwa, rewizja, mapowanie VLAN → instancja) — przełączniki tworzą różne regiony i zachowują się nieoptymalnie.
- **Za dużo VLAN-ów w PVST+** — każdy VLAN to osobna instancja STP i BPDU co 2 s; przy setkach VLAN-ów CPU przełącznika cierpi (limit instancji). Rozwiązanie: MST.
- **Łącze jednokierunkowe** (uszkodzony światłowód) — port przestaje odbierać BPDU, uznaje, że jest designated, i przechodzi do forwarding → pętla. Obrona: Loop Guard, UDLD.
- **Mieszanie kosztów short i long** — przełączniki różnych producentów lub z różną konfiguracją liczą koszty inaczej; ścieżki stają się nieprzewidywalne. Ustal jedną metodę (dla łączy ≥ 10G — long).
- **„STP wyłączyliśmy, bo spowalniał start serwerów”** — właściwa naprawa to PortFast/edge, nie wyłączenie ochrony przed pętlami.` },

  { type: 'CODE_WRONG', heading: 'Źle: PortFast wszędzie, BPDU Filter i domyślny root', body:
`Konfiguracja „żeby serwery szybko wstawały”: PortFast i BPDU Filter na wszystkich portach, także uplinkach; klasyczny PVST+; priorytety domyślne.`,
    code:
`spanning-tree mode pvst                   ! 802.1D: 30–50 s zbieżności
! brak root primary — rootem zostaje przełącznik z najniższym MAC
interface range Gi1/0/1 - 48              ! wszystkie porty, łącznie z uplinkami 47–48
 spanning-tree portfast
 spanning-tree bpdufilter enable          ! ignoruj BPDU
!
! Skutki:
! - kabel wpięty między dwa gniazdka lub mały switch biurkowy z pętlą = burza rozgłoszeniowa
!   (porty nie wysyłają i nie przetwarzają BPDU, więc STP nie zablokuje pętli),
! - uplinki z PortFast przechodzą do forwarding natychmiast po zmianie — chwilowe pętle,
! - przy awarii rdzenia przebudowa trwa do 50 s, root może trafić do szafy dostępowej,
! - każde urządzenie może ogłosić się rootem — brak Root Guard.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: RSTP, jawny root, edge tylko dla hostów, komplet ochron', body:
`Rapid PVST+, root i zapas na rdzeniu, PortFast + BPDU Guard tylko na portach dostępowych, Root Guard na rdzeniu w stronę dostępu, Loop Guard i UDLD na uplinkach, monitoring zmian topologii.`,
    code:
`spanning-tree mode rapid-pvst
spanning-tree extend system-id
spanning-tree pathcost method long
! rdzeń: spanning-tree vlan 1-4094 root primary / root secondary
!
interface range Gi1/0/1 - 46               ! porty do hostów
 switchport mode access
 spanning-tree portfast                    ! edge
 spanning-tree bpduguard enable
!
interface range Te1/1/1 - 2                ! uplinki do rdzenia
 switchport mode trunk
 spanning-tree guard loop
 udld port aggressive
!
logging host 10.99.0.50
snmp-server enable traps bridge newroot topologychange
! SIEM: alert na zmianę roota, BPDU Guard err-disable, Root/Loop Guard inconsistent,
! nagły wzrost liczby TCN i MAC flapping.` },

  { type: 'WHEN_TO_USE', heading: 'Jak projektować z STP — i kiedy go unikać', body:
`- **Zawsze włączony** w każdej sieci L2 z możliwością pętli (czyli w praktyce każdej) — jako pas bezpieczeństwa, nawet gdy projekt nie ma redundancji L2.
- **RSTP/Rapid PVST+** jako domyślny wybór; **MST**, gdy masz setki VLAN-ów lub wielu dostawców.
- **Root i zapasowy root** zawsze na rdzeniu/dystrybucji, zgodnie z przepływem ruchu i lokalizacją bram (FHRP — rozdział 04: aktywna brama VRRP/HSRP najlepiej na tym samym przełączniku co root danego VLAN-u).
- **PortFast + BPDU Guard** na wszystkich portach do hostów, serwerów i hiperwizorów (dla trunków do hiperwizorów: \`spanning-tree portfast trunk\`).
- **Mniej L2, więcej L3** — współczesne projekty ograniczają domeny L2: routing do przełącznika dostępowego, **EtherChannel/MLAG** (lekcja 08) zamiast blokowanych łączy, w centrach danych **spine-leaf z routingiem L3 i VXLAN/EVPN** — tam STP praktycznie nie bierze udziału w przekazywaniu ruchu, a wszystkie łącza są aktywne (ECMP).
- **Monitoring**: zmiany roota, liczba TCN, porty err-disabled i inconsistent jako metryki/alerty — tak samo, jak monitorujesz błędy 5xx w aplikacji.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- STP buduje drzewo bez pętli: (1) root bridge = najniższy Bridge ID (priorytet + VLAN + MAC), (2) root port na każdym przełączniku = najniższy koszt do roota, (3) designated port na każdym segmencie, (4) reszta blokowana (alternate).
- Kryteria porównań: Root ID → koszt → Bridge ID nadawcy → Port ID nadawcy. Koszty short: 10G = 2, 1G = 4, 100M = 19, 10M = 100.
- BPDU co 2 s na 01:80:c2:00:00:00; timery: hello 2 s, max age 20 s, forward delay 15 s.
- 802.1D: blocking → listening → learning → forwarding, 30–50 s; RSTP: discarding/learning/forwarding, role alternate i backup, zbieżność ~1 s; MST: instancje grupujące VLAN-y.
- Ochrony: PortFast + BPDU Guard (hosty), Root Guard (rdzeń w stronę dostępu), Loop Guard i UDLD (uplinki), storm control.
- Objawy w aplikacji: przerwy 30–50 s, DHCP timeout po starcie, chwilowy flooding po TCN, nieoptymalne ścieżki.
- Bezpieczeństwo: przejęcie roota i zalewanie BPDU — obrona Root/BPDU Guard, alerty o zmianie roota w SIEM.
- Następna lekcja: **EtherChannel i LACP — agregacja łączy**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const t of TOPO) ex(`Topologia ${t.name}. ${descTopo(t)} Wyznacz root bridge, root port i koszt do roota każdego przełącznika, role wszystkich portów i porty zablokowane.`, 'Krok 1: najniższy BID. Krok 2: najtańsza droga do roota (remis: BID sąsiada, Port ID sąsiada). Krok 3: designated na każdym łączu. Krok 4: reszta alternate.', fullSolution(t));
for (const [p, v] of [[32768, 10], [24576, 100], [4096, 1]]) {
  ex(`Przełącznik ma skonfigurowany priorytet ${p} i działa w Rapid PVST+. Jaki Bridge Priority pokaże dla VLAN ${v}? Jaką najniższą wartość priorytetu możesz ustawić, aby z pewnością wygrać z przełącznikiem o priorytecie ${p}, gdy nie znasz jego MAC?`, 'Priorytet + numer VLAN; krok konfiguracji 4096.', `Bridge Priority = ${p} + ${v} = ${p + v}. Aby wygrać niezależnie od MAC, ustaw priorytet o co najmniej 4096 niższy: ${Math.max(p - 4096, 0)}${p === 0 ? ' (0 to minimum — przy remisie decyduje MAC)' : ''}${p - 4096 < 0 ? ' — nie da się zejść poniżej 0, przy remisie zdecyduje MAC' : ''}.`);
}
ex('Policz koszt ścieżki do roota przechodzącej przez łącza: 10G, 1G, 1G, 100M — w metodzie short i long. Którą ścieżkę wybierze STP, jeśli alternatywą są trzy łącza 1G?', 'Sumuj koszty portów odbiorczych na ścieżce.', `Short: ${COST_SHORT['10G']} + ${COST_SHORT['1G']} + ${COST_SHORT['1G']} + ${COST_SHORT['100M']} = ${COST_SHORT['10G'] + 2 * COST_SHORT['1G'] + COST_SHORT['100M']}; alternatywa 3 × 4 = 12 → wygrywa alternatywa. Long: ${COST_LONG['10G']} + 2 × ${COST_LONG['1G']} + ${COST_LONG['100M']} = ${COST_LONG['10G'] + 2 * COST_LONG['1G'] + COST_LONG['100M']}; alternatywa 3 × 20 000 = 60 000 → również alternatywa. Wolne łącze 100M mocno podnosi koszt.`);
const more = [
  ['Wykonaj Packet Lab z lekcji i przedstaw tabelę: tryb (PVST+ / Rapid PVST+), zdarzenie (awaria root portu, podłączenie hosta), czas przerwy i liczba utraconych pakietów przy ping -i 0.2.', 'Przerwa = liczba utraconych odpowiedzi × 0,2 s.', 'Typowe wyniki: PVST+ awaria pośrednia: 150–250 utraconych (30–50 s); PVST+ awaria bezpośrednia root portu z alternatywną ścieżką: ok. 150 (30 s: listening + learning); Rapid PVST+: 0–10 utraconych (do ~2 s); podłączenie hosta bez PortFast w PVST+: ok. 30 s do pierwszego DHCP, z PortFast: natychmiast. Wniosek: RSTP + PortFast jako standard.'],
  ['Odczytaj z przechwyconego BPDU (Wireshark): Root Identifier 24576 / 10 / 00:aa:00:00:00:01, Root Path Cost 4, Bridge Identifier 32768 / 10 / 00:0b:00:00:00:01, Port Identifier 0x8002. Co wiesz o nadawcy i jego położeniu?', 'Port ID = priorytet 0x80 (128) + numer portu.', 'Rootem jest 00:aa:00:00:00:01 z priorytetem 24576 w VLAN 10 (BID 24586). Nadawca 00:0b:00:00:00:01 (priorytet 32768, VLAN 10) jest jeden skok 1G (koszt 4) od roota. BPDU wyszło z portu 2 (priorytet 128). Skoro nadawca wysyła BPDU na ten segment, jego port 2 jest designated dla tego segmentu.'],
  ['Wyjaśnij, dlaczego port, który przestał otrzymywać BPDU z powodu łącza jednokierunkowego, może spowodować pętlę, i jak chroni przed tym Loop Guard i UDLD.', 'Port alternate czeka na BPDU; brak BPDU = założenie, że sąsiad zniknął.', 'Port alternate pozostaje zablokowany, dopóki otrzymuje lepsze BPDU od sąsiada. Gdy światłowód działa tylko w jedną stronę, BPDU przestają docierać; po max age port uznaje, że jest jedynym na segmencie, staje się designated i przechodzi do forwarding — powstaje pętla. Loop Guard: port, który przestał dostawać BPDU, przechodzi w stan loop-inconsistent (blokowany) zamiast forwarding. UDLD: wykrywa łącze jednokierunkowe (echo z identyfikatorem sąsiada) i wyłącza port (tryb aggressive).'],
  ['Opisz, jak Root Guard reaguje na pojawienie się lepszego BPDU na porcie i czym się różni od BPDU Guard.', 'Root Guard blokuje, BPDU Guard wyłącza.', 'Root Guard: gdy na porcie pojawi się BPDU z lepszym Root ID (ktoś próbuje zostać rootem), port przechodzi w stan root-inconsistent (blokowany, bez err-disable); po ustaniu takich BPDU wraca automatycznie. Normalne BPDU są akceptowane — używany na portach rdzenia/dystrybucji w stronę przełączników dostępowych. BPDU Guard: każde BPDU na porcie edge powoduje err-disable (port wyłączony) — używany na portach do hostów, gdzie BPDU w ogóle nie powinno być.'],
  ['Na przełączniku pojawia się co kilka minut komunikat o zmianie topologii, a użytkownicy zgłaszają krótkie spowolnienia. Jak znajdziesz źródło TCN?', 'show spanning-tree detail — „from” i „occurred”.', 'show spanning-tree vlan X detail | include ieee|occurr|from|is exec — pokazuje liczbę zmian, czas od ostatniej i interfejs, z którego przyszła. Przechodzisz na przełącznik za tym interfejsem i powtarzasz, aż znajdziesz port lokalny, na którym zmienia się stan (laptop bez PortFast, flapping łącza). Naprawa: PortFast na portach brzegowych (nie generują TCN), wymiana kabla, logowanie zmian stanu interfejsów.'],
  ['Zaprojektuj rozmieszczenie root bridge i bram (VRRP/HSRP) dla VLAN 10 i 20 w sieci z dwoma przełącznikami dystrybucyjnymi, tak aby oba łącza uplink były wykorzystywane.', 'PVST+ pozwala mieć różne rooty w różnych VLAN-ach.', 'DIST1: root primary dla VLAN 10, root secondary dla VLAN 20, aktywna brama HSRP/VRRP dla VLAN 10. DIST2: odwrotnie dla VLAN 20. Przełącznik dostępowy ma root port VLAN 10 do DIST1 (uplink do DIST2 alternate dla VLAN 10) i root port VLAN 20 do DIST2 — oba uplinki przenoszą ruch. Brama w tym samym miejscu co root unika dodatkowego skoku przez łącze między dystrybucjami.'],
  ['Wyjaśnij, jak RSTP osiąga szybką zbieżność na łączach punkt-punkt (proposal/agreement) i dlaczego nie działa to tak samo na łączach half-duplex.', 'Synchronizacja (sync) i link type.', 'Na łączu P2P (full duplex) przełącznik wysyła BPDU z flagą proposal; sąsiad blokuje swoje porty niebędące edge (sync), odpowiada agreement, a nadawca od razu przechodzi do forwarding — proces schodzi kaskadowo w dół drzewa w ułamkach sekundy. Na łączu współdzielonym (half duplex, hub) nie wiadomo, ilu jest sąsiadów, więc RSTP wraca do timerów (forward delay). Dlatego typ łącza (link-type point-to-point) ma znaczenie.'],
  ['Opisz konfigurację MST z dwoma instancjami (VLAN 10–19 i 20–29) i wyjaśnij, co musi być identyczne na wszystkich przełącznikach regionu.', 'spanning-tree mst configuration: name, revision, instance.', 'spanning-tree mode mst; spanning-tree mst configuration; name CAMPUS; revision 1; instance 1 vlan 10-19; instance 2 vlan 20-29; exit; spanning-tree mst 1 root primary (na DIST1), spanning-tree mst 2 root primary (na DIST2). Identyczne muszą być: nazwa regionu, numer rewizji i pełne mapowanie VLAN → instancja; różnica choćby w jednym VLAN-ie tworzy inny region (granica regionu, inne zachowanie).'],
  ['Dlaczego w centrach danych z architekturą spine-leaf STP prawie nie ma znaczenia dla przekazywania ruchu? Co go zastępuje?', 'Routing L3, ECMP, VXLAN/EVPN, MLAG.', 'Połączenia spine–leaf są trasami L3 (BGP/OSPF), więc nie ma pętli L2 — ruch rozkłada ECMP na wszystkie łącza (żadne nie jest blokowane). L2 dla aplikacji rozciąga się overlayem VXLAN z płaszczyzną kontrolną EVPN; serwery podłącza się do pary leafów przez MLAG/EVPN multihoming z LACP. STP zostaje włączone na portach do serwerów tylko jako zabezpieczenie (BPDU Guard).'],
  ['Atak koncepcyjny: urządzenie w VLAN-ie użytkowników wysyła BPDU z priorytetem 0. Opisz skutki w sieci bez ochron i trzy mechanizmy, które temu zapobiegają, oraz sposób wykrycia.', 'Lepsze BPDU = nowy root; przebudowa drzewa.', 'Skutki: urządzenie zostaje rootem, drzewo przebudowuje się (przerwa w ruchu), ruch między przełącznikami może zacząć płynąć przez to urządzenie (podsłuch, MITM) albo sieć niestabilnie „pływa” przy ciągłych zmianach. Ochrony: BPDU Guard na portach dostępowych (port wyłączony przy pierwszym BPDU), Root Guard na portach w stronę dostępu, PortFast default z bpduguard default. Detekcja: log BPDUGUARD/ROOTGUARD, trap newRoot, alert w SIEM na zmianę roota.'],
  ['Policz przybliżoną liczbę BPDU na sekundę wysyłanych przez przełącznik dystrybucyjny w Rapid PVST+ z 200 VLAN-ami i 24 trunkami, a następnie w MST z 3 instancjami. Jaki wniosek dla dużych sieci?', 'BPDU co 2 s na port na instancję (RSTP: każdy przełącznik wysyła na portach designated).', 'Rapid PVST+: 200 VLAN-ów × 24 porty / 2 s = 2400 BPDU/s generowanych przez CPU. MST: BPDU MST niesie wszystkie instancje w jednej ramce: 24 porty / 2 s = 12 BPDU/s. Wniosek: przy setkach VLAN-ów PVST+ obciąża CPU i może przekroczyć limity instancji STP przełącznika; MST skaluje się znacznie lepiej.'],
  ['Wyjaśnij, czym różni się alternate port od backup port w RSTP i podaj sytuację, w której zobaczysz backup.', 'Alternate — droga do roota przez innego sąsiada; backup — drugi port do segmentu, na którym ten sam przełącznik ma już designated.', 'Alternate: zablokowany port, który otrzymuje lepsze BPDU od innego przełącznika — zapasowa droga do roota (przejmie rolę root portu). Backup: zablokowany port, który otrzymuje lepsze BPDU od samego siebie (ten sam przełącznik ma już designated port na tym segmencie) — zapas dla designated. Występuje rzadko: dwa porty jednego przełącznika podłączone do tego samego huba lub segmentu współdzielonego.'],
  ['Hiperwizor (ESXi, Proxmox) jest podłączony do przełącznika trunkiem. Jak skonfigurować port przełącznika pod kątem STP i dlaczego?', 'vSwitch nie uczestniczy w STP; port powinien szybko przejść do forwarding.', 'Port trunk do hosta: spanning-tree portfast trunk (edge na trunku — VM-ki mają sieć od razu po starcie i vMotion nie powoduje przerw ani TCN) + spanning-tree bpduguard enable (gdyby maszyna wirtualna zmostkowała dwa interfejsy i wysyłała BPDU, port zostanie wyłączony zamiast tworzyć pętlę). W Proxmox z linux bridge STP na bridge’u zwykle wyłączone, a pętle wyklucza się projektem (bonding zamiast dwóch niezależnych uplinków w jednym bridge’u).'],
  ['W logach widzisz „%SW_MATM-4-MACFLAP_NOTIF: Host 0050.56ab.1234 in vlan 10 is flapping between port Gi1/0/5 and port Gi1/0/48”. Jakie są możliwe przyczyny?', 'Ten sam MAC widziany na dwóch portach naprzemiennie.', '1) Pętla L2 (np. STP wyłączony/BPDU Filter, mały przełącznik z pętlą za portem Gi1/0/5) — wtedy flapping dotyczy wielu MAC-ów i rośnie broadcast; 2) host z dwiema kartami w trybie bondingu niepoprawnie skonfigurowanym (balance-rr bez LACP) — MAC pojawia się na dwóch portach; 3) maszyna wirtualna migrująca (vMotion) — jednorazowo, normalne; 4) atak/podszywanie się pod MAC. Diagnoza: sprawdzić liczbę flappujących adresów, liczniki broadcastu, stan STP i konfigurację hosta.'],
];
for (const [q, h, a] of more) ex(q, h, a);
const extra = [
  ['Napisz w Javie metodę porównującą dwa BPDU według kryteriów STP (Root ID, koszt, Bridge ID nadawcy, Port ID) — zwróć, które jest lepsze.', 'Comparator z kolejnymi kluczami; Bridge ID jako para (priorytet, MAC).', 'record BridgeId(int priority, long mac) implements Comparable<BridgeId> {\n  public int compareTo(BridgeId o) { int c = Integer.compare(priority, o.priority); return c != 0 ? c : Long.compareUnsigned(mac, o.mac); }\n}\nrecord Bpdu(BridgeId root, int cost, BridgeId sender, int portId) {}\nstatic final Comparator<Bpdu> BETTER = Comparator.comparing(Bpdu::root)\n    .thenComparingInt(Bpdu::cost)\n    .thenComparing(Bpdu::sender)\n    .thenComparingInt(Bpdu::portId);\n// BETTER.compare(a, b) < 0 → a jest lepsze (niższe wartości wygrywają).'],
  ['Przygotuj listę kontrolną audytu STP w sieci kampusowej (minimum 7 punktów).', 'Tryb, root, edge, ochrony, monitoring.', '1) Jednolity tryb (Rapid PVST+ lub MST) na wszystkich przełącznikach. 2) Root primary/secondary na rdzeniu/dystrybucji dla każdego VLAN-u/instancji. 3) PortFast tylko na portach do hostów, z BPDU Guard (lub globalnie default). 4) Brak BPDU Filter na interfejsach dostępowych. 5) Root Guard na portach dystrybucji w stronę dostępu. 6) Loop Guard/UDLD na uplinkach światłowodowych. 7) Jednolita metoda kosztów (long przy ≥ 10G). 8) Storm control na portach dostępowych. 9) Alerty SNMP/syslog: newRoot, topologyChange, err-disable, inconsistent ports. 10) Brak VLAN-ów z wyłączonym STP.'],
];
for (const [q, h, a] of extra) ex(q, h, a);
for (const [ti, cut] of [[0, 1], [2, 0], [3, 1], [4, 1]]) {
  const t = TOPO[ti]; const [a, pa, b, pb, sp] = t.links[cut];
  const t2 = { name: t.name + ' po awarii', sw: t.sw, links: t.links.filter((_, i) => i !== cut) }; t2.r = stp(t2.sw, t2.links);
  ex(`${t.name}: ${descTopo(t)} Łącze ${a} ${gi(pa)} — ${b} ${gi(pb)} (${sp}) ulega awarii. Wyznacz nowe root porty, koszty i porty zablokowane. Ile mniej więcej potrwa przerwa w 802.1D i w RSTP?`, 'Usuń łącze z grafu i powtórz algorytm.', fullSolution(t2) + ' Przerwa: 802.1D przy awarii bezpośredniej root portu ok. 30 s (listening + learning), przy pośredniej do 50 s (max age + 30 s); RSTP — port alternate przejmuje rolę root portu w ok. 1 s.');
}
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111707);
// Pytania liczone symulatorem
TOPO.forEach((t) => {
  const r = t.r; const names = t.sw.map((s) => s.n);
  add(`${t.name}: ${descTopo(t)} Który przełącznik zostanie root bridge?`, r.root, [...names.filter((n) => n !== r.root), 'żaden — wymagany remis', 'ten z najwyższym MAC'], 'Najniższy priorytet, przy remisie najniższy MAC.');
  for (const [s, p] of Object.entries(r.rootPort)) {
    const myPorts = t.links.flatMap(([a, pa, b, pb]) => [a === s ? pa : null, b === s ? pb : null]).filter((x) => x !== null);
    const wrong = [...myPorts.filter((x) => x !== p).map(gi), 'brak — to root bridge', `${gi(p + 10)}`, 'wszystkie porty'];
    add(`${t.name} (${t.links.map(([a, pa, b, pb, sp]) => `${a} ${gi(pa)}–${b} ${gi(pb)} ${sp}`).join(', ')}; root = ${r.root}): który port jest root portem przełącznika ${s}?`, gi(p), wrong, `Najniższy koszt do roota (${r.dist[s]}), przy remisie niższy BID i Port ID sąsiada.`);
  }
  const bl = blocked(t);
  if (bl.length === 1) {
    const ports = allPorts(t).filter((x) => x !== bl[0]);
    add(`${t.name}: ${descTopo(t)} Który port zostanie zablokowany (alternate)?`, bl[0], [...ports.slice(-3), 'żaden'], `Na łączu bez root portu przegrywa port przełącznika dalszego od roota (wyższy koszt, potem wyższy BID).`);
  }
});
for (const t of [TOPO[2], TOPO[3], TOPO[4]]) {
  for (const [s, d] of Object.entries(t.r.dist)) {
    if (d === 0) continue;
    add(`${t.name} (koszty short, root = ${t.r.root}): jaki jest koszt ścieżki przełącznika ${s} do roota?`, String(d), [String(d + 4), String(d * 2 === d ? d + 2 : d * 2), String(d + 19), String(Math.max(d - 2, 1))], `Suma kosztów portów na najtańszej ścieżce: ${d}.`);
  }
}
// Bridge Priority
for (const [p, v] of [[32768, 1], [32768, 20], [24576, 10], [28672, 99], [4096, 200], [0, 5], [61440, 1000]]) {
  add(`Jaki Bridge Priority ma przełącznik o priorytecie ${p} w VLAN ${v} (Rapid PVST+)?`, String(p + v), [String(p), String(p + v + 4096), String(p * 2 || 4096), String(p - v < 0 ? p + 1 : p - v)], `Priorytet + system ID extension (VLAN): ${p} + ${v} = ${p + v}.`);
}
add('Która wartość NIE jest poprawnym priorytetem mostu STP?', '30000', ['0', '4096', '61440'], 'Priorytet musi być wielokrotnością 4096 (0–61440).');
// Koszty
for (const [sp, c] of Object.entries(COST_SHORT).filter(([k]) => k !== '100G')) add(`Jaki jest domyślny koszt STP (metoda short) łącza ${sp}?`, String(c), Object.values(COST_SHORT).filter((x) => x !== c).map(String).slice(0, 3), `Short: ${sp} = ${c}.`);
for (const [sp, c] of [['1G', COST_LONG['1G']], ['10G', COST_LONG['10G']], ['100M', COST_LONG['100M']]]) add(`Jaki jest koszt STP łącza ${sp} w metodzie long (802.1D-2004)?`, String(c), [String(c * 10), String(c / 10), '4'].filter((x) => x !== String(c)), `Long: ${sp} = ${c}.`);
const facts = [
  ['Na jaki adres MAC są wysyłane BPDU standardowego STP/RSTP?', '01:80:c2:00:00:00', ['ff:ff:ff:ff:ff:ff', '01:00:0c:cc:cc:cc', '01:00:5e:00:00:01'], 'PVST+ Cisco używa 01:00:0c:cc:cc:cd.'],
  ['Jak często domyślnie wysyłane są BPDU?', 'Co 2 sekundy (hello time)', ['Co 15 sekund', 'Co 20 sekund', 'Tylko przy zmianie topologii'], 'Max age 20 s, forward delay 15 s.'],
  ['Jaki jest domyślny forward delay?', '15 s', ['2 s', '20 s', '30 s'], 'Czas listening i czas learning.'],
  ['Jaki jest domyślny max age?', '20 s', ['2 s', '15 s', '50 s'], 'Jak długo port pamięta ostatnie BPDU.'],
  ['Jaka jest kolejność stanów portu w 802.1D przy przejściu do przekazywania?', 'blocking → listening → learning → forwarding', ['listening → blocking → forwarding → learning', 'learning → listening → forwarding', 'discarding → forwarding'], 'W listening nie uczy się MAC, w learning uczy, ale nie przekazuje.'],
  ['Jakie stany portu ma RSTP?', 'discarding, learning, forwarding', ['blocking, listening, learning, forwarding', 'up, down', 'active, standby'], 'Blocking, listening i disabled połączono w discarding.'],
  ['W którym stanie 802.1D port uczy się adresów MAC, ale jeszcze nie przekazuje ramek?', 'learning', ['listening', 'blocking', 'forwarding'], 'Przygotowuje tablicę MAC, by ograniczyć flooding.'],
  ['Ile root portów ma root bridge?', 'Zero — wszystkie jego porty są designated', ['Jeden', 'Dwa', 'Tyle, ile ma VLAN-ów'], 'Root port wskazuje drogę do roota.'],
  ['Ile designated portów jest na jednym segmencie (łączu)?', 'Jeden', ['Zero', 'Dwa', 'Zależy od prędkości'], 'Przekazuje ruch na segment w kierunku od roota.'],
  ['Czym jest port alternate w RSTP?', 'Zablokowaną zapasową drogą do roota przez innego sąsiada', ['Portem do hosta', 'Portem wyłączonym administracyjnie', 'Drugim root portem'], 'Przejmuje rolę root portu w ~1 s.'],
  ['Jakie jest pierwsze kryterium przy porównaniu BPDU?', 'Najniższy Root Bridge ID', ['Najniższy koszt', 'Najniższy Port ID', 'Najwyższa prędkość'], 'Potem koszt, Bridge ID nadawcy, Port ID.'],
  ['Co rozstrzyga wybór root portu, gdy koszty są równe?', 'Niższy Bridge ID sąsiada, a potem niższy Port ID sąsiada', ['Wyższa prędkość portu', 'Losowanie', 'Kolejność podłączenia kabli'], 'Na końcu własny Port ID.'],
  ['Ile trwa typowo zbieżność klasycznego STP 802.1D?', '30–50 s', ['Poniżej 1 s', '2–5 min', '5 s'], 'Max age + 2 × forward delay.'],
  ['Ile trwa typowo zbieżność RSTP?', 'Około 1 s lub mniej', ['30–50 s', '15 s', '2 min'], 'Proposal/agreement i alternate porty.'],
  ['Czym jest PVST+?', 'Wariantem Cisco z osobną instancją STP dla każdego VLAN-u', ['Szybszą wersją RSTP', 'Protokołem agregacji łączy', 'Protokołem wykrywania sąsiadów'], 'Rapid PVST+ — to samo z RSTP.'],
  ['Jaką zaletę ma MST (802.1s)?', 'Grupuje wiele VLAN-ów w kilka instancji, zmniejszając liczbę BPDU i obciążenie CPU', ['Wyłącza blokowanie portów', 'Nie wymaga konfiguracji', 'Działa tylko z jednym VLAN-em'], 'Wymaga spójnego regionu.'],
  ['Co musi być identyczne na przełącznikach jednego regionu MST?', 'Nazwa regionu, numer rewizji i mapowanie VLAN → instancja', ['Adres MAC', 'Priorytet mostu', 'Liczba portów'], 'Inaczej powstają osobne regiony.'],
  ['Do czego służy PortFast?', 'Pozwala portowi do hosta od razu przejść do forwarding', ['Zwiększa prędkość portu', 'Wyłącza STP na przełączniku', 'Szyfruje BPDU'], 'Tylko na portach brzegowych.'],
  ['Co robi BPDU Guard po odebraniu BPDU?', 'Wyłącza port (err-disabled)', ['Zmienia rolę portu na root', 'Ignoruje BPDU', 'Obniża priorytet przełącznika'], 'Chroni porty brzegowe.'],
  ['Czym grozi BPDU Filter włączony na interfejsie dostępowym?', 'Port ignoruje BPDU, więc pętla przez ten port nie zostanie wykryta', ['Port zostanie wyłączony', 'Przełącznik zostanie rootem', 'Nic — to zalecane'], 'Lepszy BPDU Guard.'],
  ['Co robi Root Guard?', 'Blokuje port (root-inconsistent), jeśli pojawi się na nim lepsze BPDU od roota', ['Wyłącza port na zawsze', 'Wybiera root bridge', 'Przyspiesza zbieżność'], 'Port wraca automatycznie po ustaniu lepszych BPDU.'],
  ['Przed czym chroni Loop Guard?', 'Przed odblokowaniem portu, który przestał otrzymywać BPDU (np. łącze jednokierunkowe)', ['Przed burzą multicastową', 'Przed przejęciem roota', 'Przed błędami CRC'], 'Port przechodzi w loop-inconsistent.'],
  ['Co wykrywa UDLD?', 'Łącza jednokierunkowe (np. uszkodzony światłowód)', ['Pętle routingu', 'Ataki DHCP', 'Niezgodność VLAN-ów'], 'Tryb aggressive wyłącza port.'],
  ['Gdzie włącza się Root Guard?', 'Na portach rdzenia/dystrybucji w stronę przełączników dostępowych', ['Na portach do hostów', 'Na wszystkich portach roota w stronę zapasowego roota', 'Tylko na portach konsoli'], 'Nikt poniżej nie zostanie rootem.'],
  ['Co powoduje TCN w 802.1D?', 'Skrócenie czasu życia wpisów MAC do forward delay (15 s), co może wywołać flooding', ['Restart przełącznika', 'Wyłączenie STP', 'Zmianę VLAN-u portu'], 'RSTP czyści wpisy na portach niebędących edge.'],
  ['Dlaczego porty z PortFast nie generują TCN?', 'Bo są portami brzegowymi — zmiana ich stanu nie zmienia topologii między przełącznikami', ['Bo nie mają adresu MAC', 'Bo są wyłączone', 'Bo nie należą do VLAN-u'], 'Dlatego PortFast ogranicza niepotrzebny flooding.'],
  ['Jaki objaw w aplikacji jest typowy dla klasycznego STP przy awarii łącza?', 'Przerwa 30–50 s: timeouty, zerwane połączenia, failover klastra', ['Błędy certyfikatów TLS', 'Wolne zapytania SQL o tej samej porze dnia', 'Błędy kompilacji'], 'Rozwiązanie: RSTP.'],
  ['Dlaczego serwer z PXE może nie dostać adresu DHCP zaraz po starcie?', 'Port bez PortFast przez ~30 s nie przekazuje ramek w 802.1D', ['Bo DHCP nie działa w VLAN-ach', 'Bo PXE wymaga IPv6', 'Bo serwer nie ma MAC'], 'PortFast/edge rozwiązuje problem.'],
  ['Które polecenie Cisco ustawia przełącznik jako główny root dla VLAN-ów?', 'spanning-tree vlan 1-4094 root primary', ['spanning-tree root on', 'stp primary', 'set spanning-tree root'], 'Ustawia priorytet 24576 lub niżej.'],
  ['Jaki priorytet ustawia spanning-tree root secondary?', '28672', ['24576', '32768', '4096'], 'Primary zwykle 24576.'],
  ['Które polecenie pokaże porty zablokowane przez Root Guard lub Loop Guard?', 'show spanning-tree inconsistentports', ['show interfaces trunk', 'show vlan brief', 'show errdisable recovery'], 'Stany root-inconsistent, loop-inconsistent.'],
  ['Czym jest port edge w RSTP?', 'Portem brzegowym do hosta, który od razu przechodzi do forwarding', ['Portem do roota', 'Portem zablokowanym', 'Portem trunk do rdzenia'], 'Odpowiednik PortFast.'],
  ['Jakie zagrożenie stwarza urządzenie wysyłające BPDU z priorytetem 0 w VLAN-ie użytkowników bez ochron?', 'Może zostać rootem i przekierować ruch lub destabilizować sieć', ['Może zmienić adresy IP hostów', 'Może złamać hasła Wi-Fi', 'Nie stwarza zagrożenia'], 'Obrona: BPDU Guard, Root Guard.'],
  ['Co zastępuje STP jako mechanizm wykorzystania redundancji w centrach danych spine-leaf?', 'Routing L3 z ECMP oraz VXLAN/EVPN i MLAG', ['Wyłączenie redundancji', 'Huby', 'Statyczne tablice MAC'], 'Wszystkie łącza aktywne.'],
  ['Jaką wersję protokołu (pole Protocol Version) mają BPDU RSTP?', '2', ['0', '1', '3'], '0 = STP, 2 = RSTP, 3 = MST.'],
  ['Dlaczego warto przełączyć metodę kosztów na long w sieci z łączami 10G i szybszymi?', 'Metoda short nie odróżnia dobrze szybkich łączy (10G = 2, 100G = 1)', ['Bo long jest szybsza', 'Bo short nie działa z VLAN-ami', 'Bo long wyłącza blokowanie'], 'Long ma większy zakres wartości.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const [ti, cut] of [[0, 1], [2, 0], [3, 1], [3, 2], [4, 1], [1, 2]]) {
  const t = TOPO[ti]; const [a, pa, b, pb] = t.links[cut];
  const t2 = { sw: t.sw, links: t.links.filter((_, i) => i !== cut) }; t2.r = stp(t2.sw, t2.links);
  const changed = Object.entries(t2.r.rootPort).find(([s2, p2]) => t.r.rootPort[s2] !== p2) || Object.entries(t2.r.dist).find(([s2, d2]) => t.r.dist[s2] !== d2);
  const sx = changed ? changed[0] : Object.keys(t2.r.rootPort)[0];
  const ans = `${gi(t2.r.rootPort[sx])}, koszt ${t2.r.dist[sx]}`;
  add(`${t.name}: ${t.links.map(([x, px, y, py, sp]) => `${x} ${gi(px)}–${y} ${gi(py)} ${sp}`).join(', ')}; root = ${t.r.root}. Łącze ${a} ${gi(pa)}–${b} ${gi(pb)} ulega awarii. Jaki root port i koszt do roota będzie miał ${sx}?`, ans, [`${gi(t.r.rootPort[sx])}, koszt ${t.r.dist[sx]}`, `${gi(t2.r.rootPort[sx])}, koszt ${t2.r.dist[sx] + 4}`, `${gi(t2.r.rootPort[sx])}, koszt ${Math.max(t2.r.dist[sx] - 2, 1)}`, 'brak — przełącznik zostaje odcięty'], `Po usunięciu łącza najtańsza droga ${sx} do roota: ${ans}.`);
}
const facts3 = [
  ['Czym różni się awaria bezpośrednia od pośredniej w 802.1D?', 'Przy bezpośredniej przełącznik od razu wykrywa utratę łącza; przy pośredniej czeka na wygaśnięcie BPDU (max age 20 s)', ['Niczym', 'Bezpośrednia dotyczy tylko światłowodów', 'Pośrednia nie powoduje przerwy'], 'Stąd 30 s vs do 50 s.'],
  ['Który przełącznik jako jedyny generuje BPDU w klasycznym 802.1D?', 'Root bridge — pozostałe je przekazują (aktualizując koszt)', ['Każdy przełącznik niezależnie', 'Przełącznik z najwyższym MAC', 'Żaden — BPDU wysyłają hosty'], 'W RSTP każdy przełącznik wysyła własne BPDU co hello.'],
  ['Co oznacza w show spanning-tree rola „Altn” i stan „BLK”?', 'Port alternate w stanie blokowania', ['Port zarezerwowany dla administratora', 'Port w trybie access', 'Port wyłączony przez BPDU Guard'], 'Zapasowa droga do roota.'],
  ['Co oznacza oznaczenie „P2p Edge” przy porcie w show spanning-tree?', 'Łącze punkt-punkt skonfigurowane jako port brzegowy (PortFast)', ['Port do roota', 'Port zablokowany', 'Port w EtherChannelu'], 'Typowe dla portów do hostów.'],
  ['Jak przełącznik odnotowuje port wyłączony przez BPDU Guard?', 'Jako err-disabled', ['Jako administratively down', 'Jako root-inconsistent', 'Jako forwarding'], 'Przywrócenie: shutdown/no shutdown lub errdisable recovery.'],
  ['Co jest najlepszą praktyką dla łącza przełącznika do hiperwizora z wieloma VLAN-ami?', 'spanning-tree portfast trunk z BPDU Guard', ['Wyłączenie STP na całym przełączniku', 'BPDU Filter na porcie', 'Brak PortFast i domyślne ustawienia'], 'VM-ki od razu mają sieć, pętla wyłączy port.'],
  ['Dlaczego warto umieścić root bridge VLAN-u na tym samym przełączniku co aktywną bramę VRRP/HSRP?', 'Aby ruch do bramy nie przechodził przez dodatkowe łącze między dystrybucjami', ['Bo inaczej VRRP nie działa', 'Bo root szyfruje ruch do bramy', 'Bo brama musi mieć najniższy MAC'], 'Zgodność ścieżek L2 i L3.'],
  ['Która technologia pozwala używać obu uplinków jednocześnie bez blokowania przez STP?', 'EtherChannel/LACP lub MLAG', ['BPDU Filter', 'PortFast', 'VTP'], 'Agregat jest dla STP jednym łączem — lekcja 08.'],
  ['Jaki komunikat w logach Cisco wskazuje na MAC flapping, typowy objaw pętli?', '%SW_MATM-4-MACFLAP_NOTIF', ['%LINK-3-UPDOWN', '%SYS-5-CONFIG_I', '%SEC_LOGIN-5-LOGIN_SUCCESS'], 'Ten sam MAC naprzemiennie na dwóch portach.'],
  ['Jakie zdarzenie STP powinno generować alert w SIEM w sieci produkcyjnej?', 'Zmiana root bridge w VLAN-ie', ['Każde BPDU hello', 'Podłączenie hosta do portu edge', 'Każde zapytanie ARP'], 'Trap newRoot lub log zmiany roota.'],
  ['Dlaczego RSTP potrzebuje informacji o typie łącza (point-to-point)?', 'Bo szybkie proposal/agreement działa tylko na łączach punkt-punkt (full duplex)', ['Bo inaczej nie zna prędkości', 'Bo BPDU nie działa na miedzi', 'Bo typ łącza wyznacza root bridge'], 'Na łączach współdzielonych wraca do timerów.'],
];
for (const [q, a, w, e] of facts3) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
