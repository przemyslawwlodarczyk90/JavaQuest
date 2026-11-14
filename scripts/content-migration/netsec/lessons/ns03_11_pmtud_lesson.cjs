// Generuje lekcje _ns_03_ip_i_routing/11_FragmentationAndPmtud.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_11_pmtud_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/11_FragmentationAndPmtud.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { fragment, OVERHEAD, innerMtu, mss4, mss6, pathMtu } = require('./ns03_11_pmtud_model.cjs');
const buildQuiz = require('./ns03_11_pmtud_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const fragList = (t, m) => fragment(t, m).map((f, i) => `fragment ${i + 1}: Total Length ${f.total}, dane ${f.data} B, offset ${f.offset} (${f.offsetBytes} B), MF=${f.mf}`).join('; ');

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Gdy pakiet nie mieści się w łączu', ...img('ns03-11-blackhole.svg', 'PMTUD black hole: małe pakiety SYN, SYN-ACK i ClientHello przechodzą przez tunel o MTU 1420, a duża odpowiedź serwera 1500 bajtów z DF jest odrzucana; komunikat ICMP fragmentation needed jest blokowany przez firewall, więc serwer retransmituje bez końca, a klient zgłasza timeout handshake TLS.'),
    body:
`Każde łącze ma **MTU** — maksymalny rozmiar pakietu IP, który przeniesie (Ethernet: 1500 B, z tunelami i enkapsulacjami — mniej). Gdy pakiet jest większy niż MTU następnego łącza, są dwie drogi: **pociąć go (fragmentacja)** albo **odrzucić i powiedzieć nadawcy, żeby wysyłał mniejsze (Path MTU Discovery)**. Współczesny Internet wybiera prawie zawsze tę drugą: TCP ustawia flagę **DF** (Don’t Fragment), a router odsyła ICMP „fragmentation needed” z MTU łącza. W IPv6 routery **nigdy** nie fragmentują.

Wszystko działa — dopóki ktoś nie zablokuje ICMP. Wtedy powstaje **PMTUD black hole**: małe pakiety (SYN, ping, pierwsze bajty) przechodzą, duże giną bez śladu. Objaw dobrze znany developerom i zupełnie niezrozumiały bez tej lekcji: **„handshake TLS wisi, a ping działa”**, „curl zatrzymuje się po Client hello”, „git clone przez VPN stoi”, „małe odpowiedzi API wracają, duże nie”, „w Kubernetes z VXLAN część żądań ma timeout”.

W tej lekcji:
- fragmentacja IPv4 krok po kroku (ID, MF, offset w jednostkach 8 B) i dlaczego jest zła,
- flaga DF, PMTUD (RFC 1191 / RFC 8201 dla IPv6) i PLPMTUD,
- narzuty tuneli (PPPoE, GRE, VXLAN, WireGuard, IPsec) i MTU wewnątrz,
- **MSS** i **MSS clamping**,
- **Failure Lab z planu**: zablokuj ICMP i obniż MTU na tunelu — odtwórz zawieszone połączenie, napraw MSS clampingiem.

Cele: Network+ N10-009 (MTU, jumbo frames, problemy z MTU w troubleshootingu), CCNA v2 (MTU i fragmentacja w IPv4/IPv6, ICMP), Security+ (ataki fragmentacyjne i omijanie IDS).` },

  { type: 'DEFINITION', heading: 'Słownik fragmentacji i PMTUD', body:
`- **MTU (Maximum Transmission Unit)** — maksymalny rozmiar pakietu IP na łączu (bez nagłówka L2). Ethernet 1500, jumbo 9000 (9001 w AWS), PPPoE 1492, tunel WireGuard zwykle 1420.
- **Path MTU (PMTU)** — najmniejsze MTU na całej ścieżce między hostami (np. min(1500, 1500, 1420) = ${pathMtu([1500, 1500, 1420])}).
- **Fragmentacja IPv4** — podział datagramu na fragmenty z tym samym **ID**, flagą **MF** (więcej fragmentów) i **Fragment Offset** w jednostkach 8 B; składa **odbiorca**.
- **DF (Don’t Fragment)** — zakaz fragmentacji; router musi odrzucić za duży pakiet i odesłać **ICMP 3/4 (fragmentation needed)** z MTU następnego łącza.
- **PMTUD (RFC 1191, IPv6: RFC 8201)** — nadawca wysyła z DF i obniża rozmiar po otrzymaniu ICMP; Linux trzyma wynik w cache tras (\`ip route get\` → \`mtu 1420\`).
- **ICMPv6 Packet Too Big (typ 2)** — odpowiednik 3/4 w IPv6; w IPv6 fragmentować może tylko nadawca (nagłówek rozszerzający Fragment), minimalne MTU IPv6 to 1280.
- **PMTUD black hole** — ICMP 3/4 lub Packet Too Big są blokowane (firewall, security group, źle skonfigurowany router), więc nadawca nie dowie się o za małym MTU.
- **PLPMTUD (RFC 4821, RFC 8899)** — wykrywanie MTU ścieżki przez warstwę transportową (sondy o rosnącym rozmiarze), bez polegania na ICMP; Linux: \`net.ipv4.tcp_mtu_probing\`; QUIC ma go wbudowanego.
- **MSS (Maximum Segment Size)** — maksymalna ilość danych w segmencie TCP, ogłaszana w SYN: MTU − 40 (IPv4) lub MTU − 60 (IPv6); 1500 → ${mss4(1500)}.
- **MSS clamping** — router przepisuje (zmniejsza) MSS w pakietach SYN przechodzących przez tunel, aby segmenty od początku mieściły się w MTU ścieżki; działa bez ICMP, tylko dla TCP.
- **Narzut enkapsulacji** — bajty dodawane przez tunel: ${OVERHEAD.map(([n, o]) => `${n} ${o} B`).join(', ')}.
- **Jumbo frames** — MTU > 1500 (zwykle 9000); tylko gdy **wszystkie** urządzenia w domenie L2 je obsługują (lekcja 02/13).` },

  { type: 'ANALOGY', heading: 'Ciężarówki, tunele i znak „max 3,5 m”', body:
`Pakiet to ciężarówka, MTU to **wysokość tunelu** na trasie.
- **Fragmentacja** to przeładowanie towaru na kilka mniejszych aut przed niskim tunelem. Działa, ale: trzeba zatrzymać się i przepakować (koszt w routerze), wszystkie auta muszą dojechać, żeby odbiorca złożył dostawę (zgubienie jednego = cała dostawa do kosza), a złodziej łatwiej przemyci coś między częściami (omijanie IDS).
- **DF** to naklejka „nie przepakowywać — towar nierozdzielny”. Kierowca przed za niskim tunelem musi zawrócić — i powinien zadzwonić do nadawcy: „tunel ma 3,5 m, wysyłaj niższe auta” (**ICMP fragmentation needed**). Nadawca zapamiętuje i kolejne auta są niższe (**PMTUD**).
- **Black hole** to sytuacja, w której ktoś ze względów „bezpieczeństwa” wyłączył telefony. Kierowca zawraca, nadawca o niczym nie wie i wysyła kolejne za wysokie auta. Małe paczki (rowerem) dojeżdżają, duże nigdy.
- **MSS clamping** to dyspozytor przy wjeździe na trasę, który przy pierwszym kontakcie (SYN) mówi obu firmom: „na tej trasie maksymalna wysokość ładunku to X” — więc nikt nie wysyła za wysokich aut, nawet bez telefonów.
- **Narzut tunelu** to dach z kogutem doczepiany do każdej ciężarówki przy wjeździe do tunelu VPN — auto robi się wyższe o kilka centymetrów, więc wcześniej „mieszczące się” ładunki przestają się mieścić.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Fragmentacja policzona krok po kroku', ...img('ns03-11-fragments.svg', 'Datagram 4000 bajtów z 3980 bajtami danych dzielony na trzy fragmenty dla MTU 1500: 1480 bajtów danych z offsetem 0, 1480 bajtów z offsetem 185 i 1020 bajtów z offsetem 370; MF=1 w dwóch pierwszych. Offset w jednostkach 8 bajtów, składa odbiorca.'),
    body:
`Reguła: dane w każdym fragmencie (poza ostatnim) to **największa wielokrotność 8 nie większa niż MTU − 20**. Offset = pozycja danych w oryginale / 8. Każdy fragment ma własny 20-bajtowy nagłówek IP z tym samym ID; MF=1 we wszystkich poza ostatnim. Przykłady policzone modelem lekcji:`,
    code:
`Datagram 4000 B, MTU 1500:
${fragment(4000, 1500).map((f, i) => `  ${i + 1}) Total ${f.total}  dane ${f.data}  offset ${f.offset} (= ${f.offsetBytes} B)  MF=${f.mf}`).join('\n')}

Datagram 1500 B, MTU 1400 (np. tunel), DF=0:
${fragment(1500, 1400).map((f, i) => `  ${i + 1}) Total ${f.total}  dane ${f.data}  offset ${f.offset} (= ${f.offsetBytes} B)  MF=${f.mf}`).join('\n')}
  ← (1400 − 20) = 1380 nie dzieli się przez 8 → 1376

Datagram 3000 B, MTU 576 (minimalny reassembly IPv4):
${fragment(3000, 576).map((f, i) => `  ${i + 1}) Total ${f.total}  dane ${f.data}  offset ${f.offset}  MF=${f.mf}`).join('\n')}

Ten sam datagram z DF=1 → żadnej fragmentacji: router odrzuca i odsyła ICMP 3/4 „next-hop MTU …”` },

  { type: 'CODE_BASIC', heading: 'Diagnoza MTU: ping z DF, tracepath, cache PMTU', ...img('ns03-11-pmtud.svg', 'Path MTU Discovery: serwer wysyła pakiet 1500 bajtów z flagą DF, router R2 przed łączem tunelowym o MTU 1420 odrzuca go i odsyła ICMP fragmentation needed z MTU 1420, serwer zapamiętuje PMTU i zmniejsza pakiety; w IPv6 analogicznie Packet Too Big.'),
    body:
`Zestaw poleceń do znalezienia MTU ścieżki i sprawdzenia, czy PMTUD działa. Najszybszy test: **bisekcja rozmiaru ping z DF** — największy rozmiar danych, który przechodzi, + 28 B (IPv4) = PMTU.`,
    code:
`# Linux
ping -c1 -M do -s 1472 10.2.0.2      # 1472 + 28 = 1500 — „message too long”/brak odpowiedzi = mniejsze PMTU
ping -c1 -M do -s 1392 10.2.0.2      # 1392 + 28 = 1420 — przechodzi? → PMTU ≥ 1420
tracepath -n 10.2.0.2                # „pmtu 1420” — miejsce spadku MTU (używa ICMP, więc też wykrywa black hole)
ip route get 10.2.0.2                # po ICMP 3/4: „cache expires 587sec mtu 1420”
ip -s link show eth0                 # MTU interfejsu, liczniki błędów
ss -ti dst 10.2.0.2                  # mss:1460 pmtu:1500 advmss … — MSS i PMTU dla gniazda TCP
sysctl net.ipv4.tcp_mtu_probing      # 0 = wyłączone, 1 = włącz po wykryciu black hole, 2 = zawsze

# Windows
ping -f -l 1472 10.2.0.2             # -f = DF, -l = rozmiar danych; „Packet needs to be fragmented but DF set.”
netsh interface ipv4 show subinterfaces          # MTU interfejsów
# IPv6: ping -6 -M do -s 1452 host (1452 + 48 = 1500), tracepath -6

# Wireshark: icmp.type == 3 && icmp.code == 4   |   icmpv6.type == 2   |   ip.flags.mf == 1 || ip.frag_offset > 0
#            tcp.analysis.retransmission && frame.len > 1400   ← duże segmenty retransmitowane bez skutku` },

  { type: 'STEP_BY_STEP', heading: 'Failure Lab z planu: zablokuj ICMP i obniż MTU tunelu — napraw MSS clampingiem', body:
`Cztery network namespace’y: **cli** (10.1.0.2) — **r1** — łącze „tunelowe” MTU 1400 (10.9.0.0/30) — **r2** — **srv** (10.2.0.2). Końcówki mają MTU 1500, więc **ogłaszają MSS 1460** i nie wiedzą o wąskim gardle w środku — dokładnie jak przy VPN/VXLAN. Wymaga roota, iproute2, iptables, curl, python3.
1. **Budowa**: \`ip netns add\` cli, r1, r2, srv; pary veth: cli↔r1 (10.1.0.0/24), r1↔r2 (10.9.0.1/30–10.9.0.2/30), r2↔srv (10.2.0.0/24); trasy domyślne w cli (via 10.1.0.1) i srv (via 10.2.0.1); r1: trasa do 10.2.0.0/24 via 10.9.0.2, r2: do 10.1.0.0/24 via 10.9.0.1; \`sysctl -w net.ipv4.ip_forward=1\` w r1 i r2. Na łączu r1↔r2 ustaw **MTU 1400 po obu stronach**: \`ip -n r1 link set <if> mtu 1400\`, \`ip -n r2 link set <if> mtu 1400\`.
2. **Serwer**: \`ip netns exec srv sh -c 'head -c 2000000 /dev/urandom > /tmp/big.bin; cd /tmp; python3 -m http.server 80' &\`.
3. **PMTUD działa**: \`ip netns exec cli curl -o /dev/null -w '%{http_code} %{size_download}\\n' http://10.2.0.2/big.bin\` → 200 i 2 000 000 B. \`ip netns exec srv ip route get 10.1.0.2\` → \`mtu 1400\` w cache (serwer dostał ICMP 3/4 od r2).
4. **Zepsuj ICMP** (jak nadgorliwy firewall): \`ip netns exec r2 iptables -A OUTPUT -p icmp --icmp-type fragmentation-needed -j DROP\` (r2 przestaje mówić serwerowi o za dużych pakietach) i wyczyść cache: \`ip netns exec srv ip route flush cache\`.
5. **Objaw**: \`curl -m 15 …/big.bin\` → nagłówki HTTP przychodzą (małe pakiety), pobieranie staje i kończy się timeoutem. \`ip netns exec r2 tcpdump -ni <if do srv> 'tcp and greater 1400'\` pokazuje segmenty 1500 B od serwera retransmitowane w kółko; \`ss -ti\` w srv: rosnące \`retrans\`. To samo zrobiłby TLS z dużym łańcuchem certyfikatów: „Client hello” → cisza.
6. **Napraw MSS clampingiem** (na obu routerach, bo SYN i SYN-ACK idą w przeciwnych kierunkach): \`ip netns exec r1 iptables -t mangle -A FORWARD -p tcp --tcp-flags SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu\` i to samo w r2. Teraz w SYN przechodzącym przez łącze 1400 MSS jest przepisywany na **${mss4(1400)}**; curl pobiera cały plik **mimo zablokowanego ICMP**. Sprawdź \`ss -ti\` (mss:${mss4(1400)}) i tcpdump SYN (\`-v\` pokaże \`mss ${mss4(1400)}\`).
7. **Alternatywy do porównania**: usuń regułę DROP (właściwa naprawa źródła problemu); albo \`sysctl -w net.ipv4.tcp_mtu_probing=1\` na srv (PLPMTUD: TCP po timeoutach sam zmniejsza segmenty — działa, ale z opóźnieniem); albo obniż MTU interfejsów końcowych do 1400 (MSS z automatu 1360).
8. **Sprzątanie i notatka**: \`ip netns del …\`; zapisz objaw, dowód (tcpdump/ss), przyczynę (ICMP 3/4 blokowany + MTU 1400 w środku), naprawę i jej ograniczenie (clamping działa tylko dla TCP — UDP/QUIC potrzebują PMTUD/PLPMTUD).` },

  { type: 'NOTE', heading: 'MTU w tunelach, chmurze i Kubernetes', ...img('ns03-11-mss.svg', 'Tabela narzutów enkapsulacji dla łącza o MTU 1500: PPPoE 8 B (MTU 1492, MSS 1452), GRE 24 B, IPIP 20 B, VXLAN 50 B (1450, MSS 1410), WireGuard 60 lub 80 B (1440 lub 1420), IPsec ESP z NAT-T około 73 B, GENEVE 58 B. MSS clamping przepisuje MSS w pakietach SYN.'),
    body:
`Typowe miejsca, gdzie MTU „kurczy się” niezauważenie:
- **VPN** (WireGuard domyślnie 1420, OpenVPN, IPsec) — klienci i serwery po obu stronach muszą mieć poprawne MTU lub MSS clamping na bramie VPN.
- **Kubernetes z overlay** (Flannel/Calico VXLAN: −50 B, Cilium/GENEVE, WireGuard encryption) — MTU interfejsów podów musi uwzględniać narzut; źle ustawione CNI = sporadyczne timeouty dużych żądań między węzłami. Calico/Cilium wykrywają MTU automatycznie, ale nie zawsze (np. węzły w różnych sieciach).
- **Docker** — sieć bridge dziedziczy 1500; gdy host stoi za VPN lub w chmurze z innym MTU, ustaw \`"mtu"\` w \`daemon.json\` (lub \`com.docker.network.driver.mtu\` w compose).
- **Chmura** — AWS: 9001 w obrębie VPC, 1500 przez Internet Gateway, VPN i między regionami (peering inter-region), 8500 przez Transit Gateway; Azure 1500 (większe w VNet opcjonalnie); GCP 1460 domyślnie. Instancje z jumbo frames rozmawiające przez VPN polegają na PMTUD — **dopuść ICMP 3/4 w security groups i NACL**, inaczej black hole.
- **PPPoE** u operatora — 1492; router domowy powinien robić MSS clamping (większość robi).

**Dlaczego fragmentacja jest zła**: koszt CPU w routerach i hostach, utrata jednego fragmentu niszczy cały datagram (przy 1% strat pakietów datagram z 3 fragmentów ginie z prawdopodobieństwem ~3%), firewalle i NAT widzą porty tylko w pierwszym fragmencie, a **nakładające się/malutkie fragmenty** służą do omijania IDS (Teardrop, ataki na reassembly). Dlatego: DF + PMTUD dla TCP, PLPMTUD/odpowiednio małe datagramy dla UDP (DNS: EDNS0 bufor 1232 B — DNS Flag Day 2020).` },

  { type: 'CODE_PRACTICAL', heading: 'Konfiguracja: MTU Dockera, WireGuard, Kubernetes i MSS clamping', body:
`Najczęstsze ustawienia, które developer/DevOps zmienia, gdy diagnozuje problemy z MTU. Zasada: **MTU wewnętrzne = MTU zewnętrzne − narzut**, a tam, gdzie nie kontrolujesz końcówek — **MSS clamping na bramie**.`,
    code:
`# Docker — sieć bridge za VPN o MTU 1420
# /etc/docker/daemon.json
{ "mtu": 1420 }
# docker compose (sieć użytkownika)
networks:
  app:
    driver_opts:
      com.docker.network.driver.mtu: "1420"

# WireGuard (wg-quick): MTU interfejsu tunelu; zewnętrzne 1500 i IPv4 → 1440, IPv6 → 1420 (bezpieczna wartość domyślna)
[Interface]
MTU = 1420

# Kubernetes — Calico (VXLAN: 1500 − 50 = 1450)
kubectl patch felixconfiguration default --type merge -p '{"spec":{"vxlanMTU":1450}}'
# Cilium: --set MTU=1450 (lub auto-detect), Flannel: automatycznie z interfejsu − 50

# Brama VPN / router: MSS clamping (nftables)
table inet mangle {
  chain forward {
    type filter hook forward priority mangle;
    tcp flags syn tcp option maxseg size set rt mtu         # odpowiednik --clamp-mss-to-pmtu
  }
}
# lub stała wartość: tcp flags syn tcp option maxseg size set 1360
# Cisco: interface Tunnel0 → ip tcp adjust-mss 1360 ; ip mtu 1400

# Linux — PLPMTUD jako zabezpieczenie przed black hole
sysctl -w net.ipv4.tcp_mtu_probing=1
sysctl -w net.ipv4.tcp_base_mss=1024` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **„TLS handshake timeout”, ale tylko przez VPN / z niektórych sieci** — duży łańcuch certyfikatów (Certificate ~3–5 kB) nie mieści się w pakietach, a ICMP 3/4 jest blokowany; ping i nawiązanie TCP działają.
- **Klient Java (HttpClient, sterownik JDBC) wisi na dużych odpowiedziach**, małe zapytania przechodzą — szczególnie przez Site-to-Site VPN do bazy on-premises.
- **Kubernetes**: żądania między podami na różnych węzłach czasem kończą się timeoutem, w obrębie węzła — nigdy (overlay i MTU).
- **CI/CD**: \`docker pull\`/\`git clone\` w runnerze za VPN stoi na dużych warstwach.
- **gRPC/HTTP2**: duże wiadomości i ramki zwiększają szanse trafienia w black hole; zwykłe REST z małymi JSON-ami — nie.
- **UDP i QUIC (HTTP/3)**: nie ma MSS clampingu — QUIC zaczyna od 1200 B i sam wykrywa MTU (PLPMTUD); aplikacje UDP powinny trzymać datagramy ≤ 1200–1400 B.

**Blue team:**
- **Polityka ICMP**: blokada ICMP 3/4 / ICMPv6 Packet Too Big to błąd, nie zabezpieczenie — audytuj reguły firewalli, SG i NACL.
- **Ataki fragmentacyjne**: Teardrop (nakładające się offsety), Ping of Death (fragmenty składające się w > 65 535 B), tiny fragments (nagłówek TCP rozbity między fragmenty, by ominąć filtr portów), fragmentation overlap do ukrycia payloadu przed IDS. Obrona: reassembly przed inspekcją (Suricata \`defrag\`), odrzucanie fragmentów o nietypowej strukturze, polityki „drop fragments” na brzegu tam, gdzie fragmentacja nie jest potrzebna.
- **Wykrywanie**: Suricata/Zeek — zdarzenia o nakładających się fragmentach, fragmenty do usług, które nie powinny ich otrzymywać.
- Mapowanie: Network+ (MTU troubleshooting), CCNA (MTU, ICMP), Security+ (ataki sieciowe), CySA+ (analiza pcap).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Blokowanie całego ICMP** — tworzy PMTUD black hole (w IPv4 i IPv6).
- **Zakładanie, że MTU 1500 jest wszędzie** — tunele, PPPoE, overlay i chmura zmniejszają je po cichu.
- **Ustawienie jumbo frames tylko na części urządzeń** — błędy i odrzucone ramki w domenie L2 (lekcja 02/13).
- **Liczenie MSS jako MTU − 20** — MSS = MTU − 40 (IPv4: 20 IP + 20 TCP), IPv6: − 60; opcje TCP (timestamps) zmniejszają efektywny ładunek o kolejne 12 B.
- **MSS clamping tylko w jednym kierunku** — SYN i SYN-ACK przechodzą przez różne interfejsy; reguła musi łapać oba (FORWARD na bramie zwykle łapie oba, ale przy dwóch bramach — obie).
- **Oczekiwanie, że MSS clamping pomoże UDP/QUIC/IPsec ESP** — działa tylko dla TCP.
- **Ping 1500 jako test MTU** — ping \`-s\` to rozmiar danych ICMP; całość = \`-s\` + 28 (IPv4) / + 48 (IPv6).
- **Diagnoza z laptopa zamiast z miejsca problemu** — PMTU jest per ścieżka; test z poda/serwera.
- **„Naprawa” przez wyłączenie DF w aplikacji** — Java nie daje kontroli nad DF dla TCP, a fragmentacja i tak jest gorsza; napraw ICMP lub MSS.` },

  { type: 'CODE_WRONG', heading: 'Źle: VPN site-to-site do bazy bez MSS i z blokadą ICMP', body:
`Konfiguracja, po której aplikacja w chmurze łączy się z bazą on-premises, wykonuje małe zapytania, ale raporty z dużymi wynikami kończą się timeoutem.`,
    code:
`# brama VPN (Linux, IPsec): tunel ma efektywne MTU ~1400, końcówki 1500
iptables -A INPUT  -p icmp -j DROP          # „hardening”
iptables -A FORWARD -p icmp -j DROP         # ICMP 3/4 od bramy do serwera bazy nie dotrze
# brak MSS clamping: SYN z MSS 1460 przechodzi bez zmian
# AWS: security group bazy proxy bez reguły ICMP → także brak „fragmentation needed”
# aplikacja: spring.datasource.hikari.connection-timeout=30000, brak socketTimeout → wątki wiszą minutami` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: ICMP błędów przepuszczony, MSS clamping na bramie, timeouty w aplikacji', body:
`Naprawa wielowarstwowa: przyczyna (ICMP), zabezpieczenie (MSS clamping), odporność aplikacji (timeouty) i weryfikacja testem z DF.`,
    code:
`# brama VPN
iptables -A FORWARD -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT        # ICMP błędów jako RELATED
iptables -A INPUT -p icmp --icmp-type fragmentation-needed -j ACCEPT
iptables -A INPUT -p icmp --icmp-type time-exceeded -j ACCEPT
iptables -t mangle -A FORWARD -o vti0 -p tcp --tcp-flags SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu
iptables -t mangle -A FORWARD -i vti0 -p tcp --tcp-flags SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu
# AWS SG / NACL: ICMP typ 3 kod 4 dopuszczony z zakresu po drugiej stronie VPN
# aplikacja (PostgreSQL JDBC): socketTimeout, connectTimeout; HikariCP: connection-timeout=3000
spring.datasource.url=jdbc:postgresql://db.onprem:5432/app?connectTimeout=3&socketTimeout=60
# weryfikacja: z poda aplikacji  ping -M do -s 1372 db.onprem  (1400 − 28)  i  ss -ti dst db.onprem → mss:1360` },

  { type: 'WHEN_TO_USE', heading: 'Objaw → przyczyna → naprawa', body:
`Ściąga do diagnozy problemów z MTU:`,
    code:
`OBJAW                                              PRAWDOPODOBNA PRZYCZYNA                 NAPRAWA
ping OK, TLS „Client hello” → cisza                PMTUD black hole (ICMP 3/4 blokowany)   przepuść ICMP 3/4 / PTB; MSS clamping
małe odpowiedzi API OK, duże timeout               jw. lub MTU overlay/VPN                  MTU interfejsów, clamping, tcp_mtu_probing
timeouty tylko między podami na różnych węzłach    MTU CNI nie uwzględnia VXLAN/GENEVE      ustaw MTU CNI = MTU węzła − narzut
docker pull / git clone za VPN stoi                MTU bridge Dockera 1500 > MTU VPN        daemon.json "mtu"
QUIC/HTTP3 nie działa, HTTP/2 tak                  UDP > MTU ścieżki, blokada UDP 443       QUIC robi PLPMTUD; sprawdź UDP 443
DNS odpowiedzi „truncated”/timeout dla DNSSEC      duże odpowiedzi UDP fragmentowane        EDNS0 bufor 1232, fallback na TCP 53
IDS alert „overlapping fragments”                  próba omijania IDS / błędny sprzęt       reassembly przed inspekcją, drop
IPv6 działa dla małych, nie dla dużych             ICMPv6 Packet Too Big blokowany          przepuść ICMPv6 typ 2 (RFC 4890)` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- MTU łącza i PMTU ścieżki (minimum po drodze); tunele i overlay zmniejszają MTU o swój narzut (VXLAN 50, WireGuard 60/80, PPPoE 8…).
- Fragmentacja IPv4: ID, MF, offset w jednostkach 8 B; składa odbiorca; utrata fragmentu = utrata datagramu; ryzyka bezpieczeństwa (omijanie IDS). IPv6: routery nie fragmentują, minimalne MTU 1280.
- DF + PMTUD: router odsyła ICMP 3/4 (IPv6: Packet Too Big) z MTU; blokada tych komunikatów = **black hole** (ping OK, TLS/duże odpowiedzi wiszą).
- MSS = MTU − 40 (IPv4) / − 60 (IPv6); **MSS clamping** na bramie naprawia TCP nawet bez ICMP; UDP/QUIC wymagają PMTUD/PLPMTUD.
- Narzędzia: \`ping -M do -s\`, \`tracepath\`, \`ip route get\` (cache mtu), \`ss -ti\`, tcpdump/Wireshark (\`icmp.type==3 && icmp.code==4\`).
- Konfiguracja: MTU Dockera (daemon.json), WireGuard (MTU = 1420), CNI w Kubernetes, \`tcp_mtu_probing\`, ICMP 3/4 w SG/NACL.
- Następna lekcja: **IPv6 od środka** — notacja, typy adresów, nagłówek i extension headers, SLAAC, IPv6 w Javie i Dockerze.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [t, m] of [[4000, 1500], [2000, 1400], [1500, 1280], [5000, 1492], [3000, 576]]) {
  ex(`Podziel datagram IPv4 o Total Length ${t} B (nagłówek 20 B, DF=0) na fragmenty dla MTU ${m}. Podaj Total Length, dane, offset i MF każdego fragmentu.`,
    'Dane w fragmencie: największa wielokrotność 8 ≤ MTU − 20; offset = bajty / 8.', fragList(t, m) + '.');
}
for (const [n, o] of OVERHEAD.slice(0, 6)) {
  ex(`Łącze zewnętrzne ma MTU 1500, enkapsulacja: ${n} (narzut ${o} B). Jakie MTU ustawisz wewnątrz tunelu i jakie MSS (IPv4 i IPv6) ogłoszą hosty?`, 'MTU wewn. = 1500 − narzut; MSS = MTU − 40 (IPv4) / − 60 (IPv6).',
    `MTU ${innerMtu(1500, o)}, MSS IPv4 ${mss4(innerMtu(1500, o))}, IPv6 ${mss6(innerMtu(1500, o))}.`);
}
for (const links of [[1500, 1500, 1420, 1500], [9001, 1500, 9001], [1500, 1492, 1500, 1450], [1500, 1280, 1500]]) {
  const p = pathMtu(links);
  ex(`Ścieżka przechodzi przez łącza o MTU ${links.join(', ')}. Jakie jest PMTU, jaki największy rozmiar danych przejdzie w ping -M do -s (IPv4) i jakie MSS powinno być użyte?`, 'Minimum; ping: PMTU − 28; MSS: PMTU − 40.', `PMTU ${p}; ping -M do -s ${p - 28}; MSS ${mss4(p)}.`);
}
const more = [
  ['Wykonaj Failure Lab z planu: w topologii cli–r1–(MTU 1400)–r2–srv zablokuj ICMP fragmentation-needed na r2, odtwórz zawieszone pobieranie dużego pliku i napraw je MSS clampingiem. Pokaż dowody przed i po.', 'tcpdump greater 1400, ss -ti, iptables TCPMSS na obu routerach.', `Przed: curl pobiera nagłówki i staje; tcpdump na r2 pokazuje retransmitowane segmenty 1500 B od srv; ss -ti w srv rośnie retrans; ip route get 10.1.0.2 w srv bez mtu w cache (ICMP nie dotarł). Naprawa: iptables -t mangle -A FORWARD -p tcp --tcp-flags SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu na r1 i r2. Po: SYN ma mss ${mss4(1400)} (tcpdump -v), ss -ti mss:${mss4(1400)}, curl pobiera cały plik. Właściwa naprawa przyczyny: przepuścić ICMP 3/4.`],
  ['Wyjaśnij, dlaczego w black hole przechodzi handshake TCP i ClientHello TLS, a zawodzi odpowiedź serwera.', 'Rozmiary pakietów w handshake.', 'SYN/SYN-ACK/ACK mają po kilkadziesiąt bajtów, ClientHello zwykle kilkaset — mieszczą się w każdym MTU. Odpowiedź serwera (ServerHello + Certificate z łańcuchem + ewentualnie inne wiadomości) ma kilka kB, więc TCP wysyła pełne segmenty o MSS 1460 (bo obie strony ogłosiły MTU 1500), które nie mieszczą się w tunelu; bez ICMP 3/4 serwer nie zmniejsza segmentów i retransmituje je w nieskończoność.'],
  ['Dlaczego MSS clamping musi działać na obu kierunkach (SYN i SYN-ACK) i co się stanie, jeśli zadziała tylko na SYN klienta?', 'Każda strona wysyła dane w segmentach ≤ MSS drugiej strony.', 'Każda strona ogłasza w swoim SYN/SYN-ACK, jakie maksymalne segmenty chce OTRZYMYWAĆ. Jeśli przepiszemy tylko SYN klienta, serwer będzie wysyłał klientowi mniejsze segmenty (problem pobierania rozwiązany), ale klient — sugerując się MSS z SYN-ACK serwera (1460) — wysyła duże segmenty do serwera; upload/duże żądania (POST, gRPC streaming) nadal trafiają w black hole. Dlatego reguła łapie wszystkie pakiety z flagą SYN w obu kierunkach.'],
  ['Pody w Kubernetes z Calico VXLAN na węzłach z MTU 1500 mają MTU 1500. Opisz objaw i naprawę.', 'Narzut VXLAN 50 B.', 'Pakiety 1500 B z poda po enkapsulacji VXLAN mają 1550 B — nie mieszczą się w MTU węzła; z DF są odrzucane (ICMP może nie dotrzeć do poda przez NAT/overlay), bez DF — fragmentowane (spadek wydajności). Objaw: sporadyczne timeouty dużych żądań między podami na różnych węzłach, ruch w obrębie węzła OK. Naprawa: MTU CNI = 1500 − 50 = 1450 (Calico FelixConfiguration vxlanMTU lub autodetekcja), restart podów, by dostały nowe MTU; weryfikacja ping -M do -s 1422 między podami.'],
  ['Policz, ile danych aplikacji niesie pełny segment TCP przy MTU 1500 z opcją timestamps i jaki jest narzut procentowy nagłówków.', 'IP 20 + TCP 20 + timestamps 12.', 'MSS 1460, ale opcja timestamps (10 B + 2 B wypełnienia) zajmuje 12 B w każdym segmencie, więc dane = 1448 B. Narzut nagłówków = 52 B z 1500 ≈ 3,5% (plus nagłówek Ethernet 14 B + FCS 4 B + preambuła/odstęp 20 B na łączu: ok. 1448/1538 ≈ 94% efektywności).'],
  ['Wyjaśnij, dlaczego QUIC/HTTP3 nie korzysta z MSS clampingu i jak radzi sobie z MTU.', 'UDP, PLPMTUD, 1200 B.', 'QUIC działa na UDP — nie ma SYN ani opcji MSS, więc router nie ma czego przepisać. QUIC wymaga, by ścieżka przenosiła co najmniej 1200 B (pierwsze pakiety Initial są dopełniane do 1200) i sam wykrywa większe MTU sondami (DPLPMTUD, RFC 8899) — nie polega na ICMP, choć może go wykorzystać. Jeśli UDP 443 jest blokowany lub MTU < 1200, przeglądarka wraca do HTTP/2 po TCP.'],
  ['Opisz atak tiny fragments i overlapping fragments oraz jak nowoczesne IDS/firewalle się przed nimi bronią.', 'Porty w pierwszym fragmencie, nadpisywanie danych.', 'Tiny fragments: pierwszy fragment tak mały, że nie zawiera pełnego nagłówka TCP (np. bez flag/portów) — filtr sprawdzający porty w pierwszym fragmencie nie widzi, co jest blokowane (RFC 1858). Overlapping: kolejne fragmenty nadpisują część wcześniejszych danych; różne systemy składają je różnie, więc IDS widzi inny strumień niż ofiara (np. ukryty exploit). Obrona: składanie fragmentów przed inspekcją (Suricata defrag z politykami per system docelowy), odrzucanie fragmentów z offsetem 1 i nietypowo małych pierwszych fragmentów (RFC 1858/3128), drop fragmentów na brzegu, gdy nie są potrzebne.'],
  ['Dlaczego DNS przy DNSSEC i dużych odpowiedziach ma problem z fragmentacją i co zaleca DNS Flag Day 2020?', 'EDNS0, UDP, fragmenty.', 'Duże odpowiedzi DNS (DNSSEC, wiele rekordów) przekraczają MTU i są fragmentowane w UDP; fragmenty bywają odrzucane przez firewalle lub są celem ataków (zatruwanie przez fragmenty). DNS Flag Day 2020 zalecił domyślny rozmiar bufora EDNS0 1232 B (mieści się w minimalnym MTU IPv6 1280 minus nagłówki) i przechodzenie na TCP 53, gdy odpowiedź jest obcięta (flaga TC). Firewalle muszą przepuszczać DNS po TCP.'],
  ['Na serwerze w AWS z MTU 9001 klienci z Internetu zgłaszają zawieszone pobieranie dużych plików, a w VPC wszystko działa. Wyjaśnij i napraw.', 'IGW 1500, ICMP w SG/NACL.', 'W VPC ścieżka ma 9001, ale przez Internet Gateway — 1500. Serwer polega na PMTUD przy połączeniach z Internetu; jeśli security group lub NACL nie przepuszcza ICMP typ 3 kod 4, serwer nie dowie się o MTU 1500 i wysyła ramki ~9000 B (jeśli klient ogłosił duży MSS — rzadko) lub — częściej — problem pojawia się przy tunelach/VPN klienta. Naprawa: dodaj regułę ICMP 3/4 w SG i NACL, rozważ MTU 1500 na interfejsach obsługujących ruch internetowy lub MSS clamping na load balancerze (ALB/NLB terminuje TCP, co zwykle rozwiązuje problem po stronie instancji).'],
  ['Jak włączyć i do czego służy net.ipv4.tcp_mtu_probing? Jakie są wady?', 'PLPMTUD, 1 vs 2.', 'tcp_mtu_probing=1: Linux włącza sondowanie MTU po wykryciu black hole (powtarzające się timeouty dużych segmentów) — zmniejsza MSS do tcp_base_mss i stopniowo sonduje większe rozmiary bez polegania na ICMP; =2: sondowanie zawsze. Zaleta: odporność na zablokowany ICMP. Wady: problem jest wykrywany dopiero po timeoutach (opóźnienie pierwszego transferu, nawet kilka sekund), dotyczy tylko TCP i tylko strony z włączonym parametrem (zwykle serwera wysyłającego duże dane).'],
  ['W IPv6 host wysyła pakiety 1500 B, łącze w środku ma 1400 i ICMPv6 Packet Too Big dociera. Kto fragmentuje i jak?', 'Routery IPv6 nie fragmentują.', 'Router odrzuca pakiet i odsyła Packet Too Big (MTU 1400). Nadawca zapamiętuje PMTU 1400 i dla TCP zmniejsza segmenty (MSS), a dla UDP (gdy aplikacja wyśle datagram > 1400) fragmentuje SAM, dodając nagłówek rozszerzający Fragment (Next Header 44). Routery po drodze nigdy nie fragmentują w IPv6. Minimalne MTU IPv6 to 1280 — każde łącze musi je przenieść.'],
  ['Twoja aplikacja Java wysyła datagramy UDP po 4000 B (np. własny protokół telemetrii). Jakie problemy mogą wystąpić i jak przeprojektować wysyłkę?', 'Fragmentacja, utrata fragmentu, firewalle.', 'Każdy datagram 4000 B zostanie podzielony na 3 fragmenty IP (MTU 1500); utrata dowolnego fragmentu oznacza utratę całego datagramu (przy 1% strat ~3%), firewalle/NAT mogą odrzucać fragmenty bez nagłówka UDP, w tunelach jeszcze więcej fragmentów, część sieci blokuje fragmenty całkowicie. Przeprojektowanie: datagramy ≤ 1200–1400 B (podział w aplikacji z numeracją), kompresja, albo przejście na TCP/QUIC/gRPC; w Javie DatagramSocket nie ustawia DF dla UDP w przenośny sposób — kontroluj rozmiar po swojej stronie.'],
  ['Ustal metodą bisekcji PMTU do hosta, jeśli wiesz, że ping -M do -s 1472 nie przechodzi, a -s 1272 przechodzi. Podaj kolejne kroki do wyniku 1420.', 'Połowienie przedziału; PMTU = max przechodzący + 28.', 'Przedział rozmiaru danych: 1272 przechodzi, 1472 nie. 1372 → przechodzi (dolna granica 1372). 1422 → nie (górna 1422). 1397 → nie (górna 1397). 1384 → przechodzi. 1390 → przechodzi. 1393 → nie. 1391 → przechodzi. 1392 → przechodzi. Największy przechodzący rozmiar danych to 1392, więc PMTU = 1392 + 28 = 1420. (tracepath podaje wynik od razu; bisekcja przydaje się, gdy tracepath nie działa przez filtrowanie ICMP po drodze.)'],
  ['Wyjaśnij, dlaczego jedna zgubiona ramka niszczy cały datagram przy fragmentacji, i policz prawdopodobieństwo utraty datagramu z 3 fragmentów przy 2% strat pakietów.', 'Wszystkie fragmenty muszą dotrzeć.', 'Odbiorca może złożyć datagram tylko z kompletu fragmentów; IP nie retransmituje pojedynczych fragmentów, więc brak jednego oznacza odrzucenie całości po timeoucie reassembly (i retransmisję całego datagramu przez warstwę wyższą, jeśli w ogóle istnieje). P(dotarcia) = 0,98³ ≈ 0,941 → P(utraty) ≈ 5,9% — prawie trzy razy więcej niż dla pojedynczego pakietu.'],
  ['Dlaczego firewall z regułami na porty może źle traktować fragmenty IP i jak to obchodzą nowoczesne firewalle?', 'Porty tylko w pierwszym fragmencie.', 'Nagłówek TCP/UDP (porty) jest tylko w pierwszym fragmencie; kolejne fragmenty mają sam nagłówek IP z offsetem > 0. Prosty filtr bezstanowy nie może dopasować ich do reguły portowej — albo je przepuszcza (ryzyko), albo odrzuca (psuje legalny ruch). Firewalle stanowe (Netfilter z nf_defrag, pfSense, NGFW) składają fragmenty przed dopasowaniem reguł lub śledzą je po ID i adresach, dzięki czemu reguły działają na kompletnym datagramie.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
