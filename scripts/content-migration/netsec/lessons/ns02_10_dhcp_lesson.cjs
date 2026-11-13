// Generuje lekcje _ns_02_ethernet_lan/10_DhcpAndSlaac.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_10_dhcp_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/10_DhcpAndSlaac.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { eui64, fmtTime, timers, hostsIn, MSG, OPT } = require('./ns02_10_dhcp_model.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Skąd host wie, kim jest w sieci?', ...img('ns02-10-dora.svg', 'Wymiana DORA między klientem bez adresu a serwerem DHCP 10.20.0.2: DHCPDISCOVER jako broadcast z portu 68 na 67, DHCPOFFER z propozycją 10.20.0.57/24, bramą, DNS i dzierżawą 8 h, DHCPREQUEST jako broadcast potwierdzający wybór i DHCPACK zatwierdzający dzierżawę.'),
    body:
`Laptop podłączony do sieci, telefon w Wi-Fi, maszyna wirtualna w chmurze, pod w Kubernetesie — każde z nich w ciągu sekund dostaje **adres IP, maskę, bramę domyślną, serwery DNS** i często dużo więcej (domena wyszukiwania, NTP, serwer rozruchowy). Robi to za nich:
- **DHCP (Dynamic Host Configuration Protocol)** w IPv4 — serwer przydziela adres na określony czas (**dzierżawa**, lease) w czterech krokach **DORA**,
- w IPv6 — **SLAAC** (Stateless Address Autoconfiguration): host sam tworzy adres z prefiksu ogłoszonego przez router w **Router Advertisement**, oraz **DHCPv6** (stanowy lub bezstanowy).

Dla Java developera DHCP to nie tylko „sieć działa”. To:
- **źródło typowych awarii** — wyczerpana pula (nowe pody/VM-ki nie startują), zła opcja DNS lub domeny wyszukiwania (aplikacja nie rozwiązuje nazw lub robi to wolno), za krótka dzierżawa i zmiana adresu w trakcie działania,
- **powód, dla którego serwer ma adres statyczny (lub rezerwację), a kontener dostaje go z IPAM** — adres, od którego zależą inni, nie może się zmieniać przypadkiem,
- **wektor ataku** — **rogue DHCP** (fałszywy serwer podaje swoją bramę/DNS i przechwytuje ruch), **wyczerpanie puli** (DHCP starvation), **fałszywe RA** w IPv6; obrona: **DHCP snooping**, **RA Guard**, **DHCPv6 Guard**.

W tej lekcji: DORA, typy komunikatów i najważniejsze opcje, timery T1/T2, **DHCP relay** i opcja 82, pule i rezerwacje, SLAAC z EUI-64 i adresami prywatnymi, flagi RA (M/O/A), DHCPv6, oraz Packet Lab z \`dnsmasq\` w kontenerze. To cele Network+ (D1/D2: DHCP, SLAAC, relay, rezerwacje, opcje) i CCNA 200-301 (D4: konfiguracja i weryfikacja DHCP klienta i relay; D5: DHCP snooping).` },

  { type: 'DEFINITION', heading: 'Słownik DHCP i autokonfiguracji IPv6', body:
`- **DHCP (RFC 2131)** — protokół przydzielania konfiguracji: UDP, serwer port **67**, klient port **68**. Następca BOOTP.
- **DORA** — Discover (klient szuka, broadcast), Offer (serwer proponuje), Request (klient wybiera, broadcast), Ack (serwer potwierdza).
- **Typy komunikatów (opcja 53)**: ${MSG.map(([n, m]) => `${n} ${m}`).join(', ')}.
- **Dzierżawa (lease)** — czas ważności adresu; **T1** (domyślnie 50% dzierżawy) — odnowienie unicastem u tego samego serwera; **T2** (87,5%) — rebinding broadcastem do dowolnego serwera.
- **Pula (scope)** — zakres adresów do przydzielania; **wykluczenia** (np. brama, serwery); **rezerwacja** — stały adres dla danego MAC/Client ID.
- **Opcje** — dodatkowe parametry: 1 maska, 3 brama, 6 DNS, 15 domena, 42 NTP, 51 czas dzierżawy, 54 identyfikator serwera, 66/67 TFTP i plik rozruchowy (PXE), 82 informacje relay, 119 domeny wyszukiwania, 121 trasy statyczne.
- **DHCP relay (ip helper-address)** — router przekazuje broadcast klienta unicastem do serwera w innej podsieci; pole **giaddr** wskazuje podsieć klienta.
- **DHCP snooping** — funkcja przełącznika: odpowiedzi DHCP tylko z portów zaufanych (trusted), tabela powiązań MAC–IP–port–VLAN (podstawa dla DAI i IP Source Guard).
- **SLAAC (RFC 4862)** — host tworzy adres IPv6 z prefiksu /64 z **RA** i identyfikatora interfejsu (EUI-64 lub losowego).
- **RA (Router Advertisement, ICMPv6 134)** — ogłoszenie routera: prefiksy, flagi **M** (Managed — adresy z DHCPv6), **O** (Other — inne informacje z DHCPv6), **A** w opcji prefiksu (Autonomous — SLAAC), **RDNSS** (DNS w RA), czas życia routera.
- **EUI-64** — identyfikator interfejsu z MAC: wstawienie \`ff:fe\` w środek i odwrócenie bitu U/L.
- **Adresy prywatne / stabilne** — RFC 8981 (tymczasowe, zmieniane okresowo), RFC 7217 (stabilne, ale nieujawniające MAC).
- **DHCPv6 (RFC 8415)** — UDP 546 (klient) / 547 (serwer), multicast \`ff02::1:2\`; komunikaty Solicit, Advertise, Request, Reply (SARR); identyfikator **DUID** zamiast MAC.` },

  { type: 'ANALOGY', heading: 'Hotel: recepcja, karta pokoju i wymeldowanie', body:
`DHCP działa jak **recepcja hotelu**:
- Gość wchodzi do holu i woła do wszystkich: „Czy jest tu recepcja? Potrzebuję pokoju!” (**Discover** — broadcast, bo gość nie wie, gdzie jest recepcja).
- Recepcjonista: „Mam pokój 57 na 8 godzin, śniadanie w restauracji na parterze (brama), informacja pod numerem 9 (DNS)” (**Offer**).
- Gość głośno, by słyszały wszystkie recepcje w budynku: „Biorę pokój od recepcji nr 2!” (**Request** broadcastem — inne recepcje zwalniają swoje propozycje).
- Recepcja wydaje kartę (**Ack**). Karta ma **datę ważności** (dzierżawa). W połowie pobytu gość podchodzi do swojej recepcji przedłużyć (T1, unicast). Jeśli recepcja jest zamknięta, pod koniec pyta dowolną (T2, broadcast). Jeśli nikt nie przedłuży — po terminie musi opuścić pokój (koniec dzierżawy).
- **Rezerwacja** to stały gość, który zawsze dostaje pokój 10. **Wykluczenia** to pokoje dla personelu.
- **Relay** to portier w drugim budynku, który nie ma recepcji, ale dzwoni do głównej w imieniu gościa i mówi: „gość jest w budynku B” (giaddr).
- **Rogue DHCP** to oszust w holu, który szybciej niż recepcja krzyczy „mam dla pana pokój!” i podaje fałszywy adres restauracji (bramy) — gość idzie jeść tam, gdzie oszust podsłuchuje rozmowy. **DHCP snooping** to ochrona, która pozwala odpowiadać tylko z okienka recepcji.

**SLAAC** w IPv6 to inny model: na ścianie wisi tablica „to piętro ma prefiks 2001:db8:10::/64” (RA), a gość sam dopisuje swój numer pokoju i sprawdza, czy nikt go nie zajął (DAD).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Policz: timery dzierżawy, pule i EUI-64', ...img('ns02-10-lease.svg', 'Oś czasu dzierżawy 8-godzinnej: do T1 (4 h, 50%) adres używany spokojnie, od T1 do T2 (7 h, 87,5%) klient odnawia dzierżawę unicastem u swojego serwera, od T2 do końca próbuje dowolnego serwera broadcastem; po końcu bez odpowiedzi musi przestać używać adresu.'),
    body:
`Obliczenia, które pojawiają się w egzaminach i przy projektowaniu: kiedy klient odnowi adres, ile adresów jest w puli i jaki adres IPv6 wygeneruje SLAAC z EUI-64.`,
    code:
[3600, 28800, 86400, 691200].map((l) => { const t = timers(l); return `dzierżawa ${fmtTime(l).padEnd(10)} → T1 ${fmtTime(t.t1).padEnd(14)} T2 ${fmtTime(t.t2)}`; }).join('\n') + `

Pula dla /24 z wykluczeniem .1–.20 (brama, serwery, drukarki):
  adresy hostów: 2^8 − 2 = ${hostsIn(24)}; w puli: ${hostsIn(24)} − 20 = ${hostsIn(24) - 20}
Pula dla /22 (np. Wi-Fi gości): 2^10 − 2 = ${hostsIn(22)} hostów
Pula dla /26: ${hostsIn(26)} hostów — za mało dla sali konferencyjnej na 80 osób z telefonami i laptopami!

EUI-64:
${['00:1a:2b:3c:4d:5e', '52:54:00:12:34:56', 'a4:83:e7:01:02:03', '00:50:56:ab:cd:ef'].map((m) => `  ${m} → fe80::${eui64(m)}`).join('\n')}
  (pierwszy bajt: 00 → 02, 52 → 50, a4 → a6 — odwrócony bit U/L, czyli XOR 0x02)` },

  { type: 'CODE_BASIC', heading: 'Klient DHCP w Linuksie, Windows i Javie', body:
`Jak zobaczyć, co dał DHCP, i jak odnowić dzierżawę. W Javie możesz odczytać adresy interfejsów, ale nie to, czy pochodzą z DHCP — tę informację ma system.`,
    code:
`# Linux (NetworkManager / systemd-networkd / dhclient)
nmcli device show eth0 | grep -E 'IP4|DHCP4'     # adres, brama, DNS, opcje DHCP
nmcli connection up eth0                           # odnowienie
networkctl status eth0                             # systemd-networkd: adres, DHCP, DNS
cat /var/lib/NetworkManager/*.lease 2>/dev/null; cat /run/systemd/netif/leases/*
sudo dhclient -r eth0 && sudo dhclient -v eth0     # zwolnij i pobierz ponownie (dhclient)
ip -6 addr show dev eth0                           # adresy IPv6: global (dynamic, mngtmpaddr), link-local
rdisc6 eth0                                        # wyślij RS i pokaż RA (flagi M/O, prefiksy, RDNSS)

# Windows
ipconfig /all                                      # DHCP Enabled, Lease Obtained/Expires, DHCP Server, DNS
ipconfig /release && ipconfig /renew
netsh interface ipv6 show addresses               # typy adresów IPv6 (Public, Temporary, Link)

// Java: adresy interfejsów (bez informacji o źródle)
for (NetworkInterface nif : NetworkInterface.networkInterfaces().toList()) {
    if (!nif.isUp() || nif.isLoopback()) continue;
    for (InterfaceAddress ia : nif.getInterfaceAddresses())
        System.out.printf("%s %s/%d%n", nif.getName(), ia.getAddress().getHostAddress(), ia.getNetworkPrefixLength());
}
// Nie zapisuj „swojego” adresu IP w konfiguracji ani w cache na stałe — przy DHCP może się zmienić.` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab: DHCP z dnsmasq w kontenerze — przechwyć i odczytaj opcje', ...img('ns02-10-relay.svg', 'Klient w VLAN 10 wysyła broadcast DHCP do routera z funkcją relay (ip helper-address 10.20.0.2), który przekazuje go unicastem do serwera DHCP w VLAN 20 i zwraca odpowiedź. Pole giaddr 10.10.0.1 wskazuje pulę, opcja 82 dodaje port i VLAN klienta.'),
    body:
`Laboratorium z planu lekcji — w izolowanej sieci Dockera (nie w sieci firmowej: drugi serwer DHCP w prawdziwej sieci to incydent):
1. **Sieć bez wbudowanego IPAM-u dla klientów**: \`docker network create --subnet 172.30.0.0/24 dhcplab\` (Docker sam przydziela adresy kontenerom, więc klient DHCP skonfigurujemy na osobnym interfejsie przez namespace albo użyjemy sieci macvlan/bridge z \`--ip-range\` poza pulą dnsmasq).
2. **Serwer**: kontener z \`dnsmasq\`: \`dnsmasq -d --dhcp-range=172.30.0.100,172.30.0.150,12h --dhcp-option=3,172.30.0.1 --dhcp-option=6,172.30.0.53 --dhcp-option=15,lab.local --dhcp-option=119,lab.local,corp.lab --log-dhcp\`.
3. **Przechwytywanie**: na hoście \`tcpdump -i br-<id> -nn -vv -w dhcp.pcap 'udp port 67 or udp port 68'\`.
4. **Klient**: kontener z \`udhcpc\` (BusyBox) lub \`dhclient -v\` na drugim interfejsie; obserwuj w logach dnsmasq DHCPDISCOVER → OFFER → REQUEST → ACK.
5. **Analiza w Wiresharku** (filtr \`dhcp\`): adresy IP i porty każdego komunikatu (0.0.0.0:68 → 255.255.255.255:67), opcja 53 (typ), 50 (żądany adres), 54 (identyfikator serwera), 51 (dzierżawa), 1, 3, 6, 15, 119; flaga broadcast; pole \`chaddr\` (MAC klienta).
6. **Odnowienie**: ustaw krótką dzierżawę (\`2m\`) i obserwuj REQUEST unicastem po T1 (60 s).
7. **Release**: \`dhclient -r\` → DHCPRELEASE; sprawdź plik dzierżaw dnsmasq.
8. **Wnioski**: zanotuj, które informacje widzi każdy w segmencie (nazwa hosta w opcji 12, vendor class w opcji 60 — np. „MSFT 5.0”, „android-dhcp”) — to także materiał do fingerprintingu urządzeń przez NAC i przez napastnika.` },

  { type: 'NOTE', heading: 'Najważniejsze opcje DHCP i porty', body:
`Opcje, które warto znać na pamięć — pojawiają się w egzaminach, w konfiguracji serwerów i w analizie pcap:`,
    code:
`${OPT.map(([n, d]) => `opcja ${String(n).padStart(3)}  ${d}`).join('\n')}
opcja  12  nazwa hosta klienta        opcja  60  vendor class (np. „MSFT 5.0”, „PXEClient”)
opcja  61  client identifier          opcja 252  WPAD (proxy — historycznie nadużywane)

Porty i adresy:
  DHCPv4: serwer UDP 67, klient UDP 68; Discover 0.0.0.0 → 255.255.255.255
  DHCPv6: klient UDP 546, serwer/relay UDP 547; multicast ff02::1:2 (All_DHCP_Relay_Agents_and_Servers)
  RA/RS:  ICMPv6 134/133; RA na ff02::1, RS na ff02::2
DHCPv6 komunikaty: Solicit → Advertise → Request → Reply (SARR); Renew, Rebind, Release, Information-request (bezstanowy).` },

  { type: 'CODE_PRACTICAL', heading: 'Serwer, relay i ochrona: Cisco IOS, ISC Kea, dnsmasq', body:
`Konfiguracja serwera DHCP na routerze (małe sieci), relay na przełączniku L3 i ochrona przełączników dostępowych. W większych sieciach serwerem jest ISC **Kea**, Windows Server DHCP lub Infoblox, często z failoverem i integracją z DNS/IPAM.`,
    code:
`! Cisco IOS: serwer DHCP dla VLAN 10
ip dhcp excluded-address 10.10.0.1 10.10.0.20
ip dhcp pool USERS
 network 10.10.0.0 255.255.255.0
 default-router 10.10.0.1
 dns-server 10.20.0.53 10.20.0.54
 domain-name corp.example
 lease 0 8                                    ! 0 dni, 8 godzin
ip dhcp pool PRINTER-01                       ! rezerwacja
 host 10.10.0.30 255.255.255.0
 client-identifier 0100.1a2b.3c4d.5e
!
! Przełącznik L3: relay dla VLAN 30 do centralnego serwera
interface Vlan30
 ip address 10.30.0.1 255.255.255.0
 ip helper-address 10.20.0.2
!
! Przełącznik dostępowy: DHCP snooping
ip dhcp snooping
ip dhcp snooping vlan 10,30
no ip dhcp snooping information option         ! jeśli serwer nie obsługuje opcji 82
interface Te1/1/1
 ip dhcp snooping trust                        ! uplink w stronę serwera/relay
interface range Gi1/0/1 - 46
 ip dhcp snooping limit rate 15                ! ochrona przed zalewaniem DISCOVER
show ip dhcp snooping binding                  ! MAC, IP, dzierżawa, VLAN, port
show ip dhcp binding / show ip dhcp pool        ! (na serwerze IOS) przydziały i wykorzystanie puli

# IPv6 na routerze: SLAAC + DNS przez RA i bezstanowy DHCPv6
interface Vlan10
 ipv6 address 2001:db8:10::1/64
 ipv6 nd other-config-flag                     ! O=1: DNS/domena z DHCPv6
 ipv6 nd ra dns server 2001:db8:20::53         ! RDNSS (nowsze IOS XE)
 ipv6 dhcp server STATELESS-POOL
ipv6 nd raguard policy HOSTS
 device-role host                              ! RA z portów hostów odrzucane` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', ...img('ns02-10-slaac.svg', 'Router Advertisement z prefiksem 2001:db8:10::/64 i flagami A, M, O oraz RDNSS. Obliczenie EUI-64 z MAC 52:54:00:12:34:56 daje identyfikator 5054:ff:fe12:3456 i adres 2001:db8:10::5054:ff:fe12:3456. Uwagi o prywatności, DAD i RA Guard.'),
    body:
`**Developer / DevOps / SRE:**
- **Serwer statyczny, klient dynamiczny** — bazy, brokery, serwery DNS, bramy mają adresy statyczne lub rezerwacje (od ich adresu zależą inni, rekordy DNS, reguły firewalla); stacje robocze, VM-ki testowe i urządzenia — DHCP. W chmurze adres instancji przydziela DHCP dostawcy (VPC), ale jest stały przez całe życie interfejsu (ENI) — nie myl tego z adresem publicznym.
- **Kontenery i Kubernetes** — Docker i CNI przydzielają adresy z wbudowanego **IPAM** (host-local, Calico IPAM, Whereabouts), nie z DHCP sieci firmowej; pod dostaje adres przy starcie i traci go przy usunięciu — dlatego aplikacje adresuje się przez Service/DNS, nie przez IP poda.
- **Wyczerpanie puli** — nowe VM-ki/węzły autoskalowania nie dostają adresu: „VM startuje, ale nie ma sieci”. Monitoruj wykorzystanie puli (Kea/Windows mają metryki), dobieraj wielkość podsieci z zapasem.
- **DNS z DHCP** — błędna opcja 6/15/119 to aplikacja Javy rozwiązująca \`db\` jako \`db.corp.example\` lub czekająca na timeouty nieosiągalnego DNS; \`/etc/resolv.conf\` w kontenerze dziedziczy z hosta.
- **PXE i provisioning** — opcje 66/67 (TFTP, plik rozruchowy) uruchamiają instalację serwerów bare metal i stacji (iPXE, Foreman, MAAS).
- **IPv6** — w sieciach dual-stack host może mieć kilka adresów IPv6 (stabilny, tymczasowy, link-local); aplikacja serwerowa powinna nasłuchiwać na \`::\`/wszystkich adresach, a reguły firewalla uwzględniać, że adres klienta IPv6 może się zmieniać (adresy tymczasowe).

**Blue team:**
- **Rogue DHCP** (ATT&CK T1557.003 — DHCP Spoofing) — fałszywy serwer podaje swoją bramę lub DNS → man-in-the-middle; także przypadkowy domowy router podłączony odwrotnie. Obrona: **DHCP snooping** (porty untrusted nie mogą wysyłać OFFER/ACK), wykrywanie (alerty o OFFER z nieznanych serwerów, narzędzia typu \`nmap --script broadcast-dhcp-discover\` w audycie).
- **DHCP starvation** — zalewanie DISCOVER z losowych MAC wyczerpuje pulę (często jako wstęp do rogue DHCP). Obrona: \`ip dhcp snooping limit rate\`, port security, 802.1X.
- **Rogue RA / DHCPv6** — fałszywe Router Advertisement w sieci „tylko IPv4” (gdzie hosty i tak mają włączone IPv6) przekierowują ruch IPv6 przez napastnika. Obrona: **RA Guard**, **DHCPv6 Guard**, monitoring RA (\`ndpmon\`, \`radvdump\`), świadome zarządzanie IPv6 zamiast „wyłączyliśmy, więc nie istnieje”.
- **Tabela snoopingu jako źródło prawdy** — powiązania MAC–IP–port–czas wykorzystuje DAI (lekcja 03), IP Source Guard i dochodzenia („kto miał adres 10.10.0.57 wczoraj o 14:05?”) — logi DHCP serwera i snoopingu trzymaj w SIEM.` },

  { type: 'PITFALL', heading: 'Częste błędy z DHCP i SLAAC', body:
`- **Statyczny adres z zakresu puli** — serwer DHCP przydzieli go komuś innemu → konflikt adresów (objawy jak w lekcji 03: „działa raz na jakiś czas”). Adresy statyczne spoza puli lub wykluczone.
- **Brak relay po dodaniu nowego VLAN-u** — klienci nie dostają adresów, bo broadcast nie przechodzi przez router; po każdym nowym VLAN-ie: \`ip helper-address\`.
- **Firewall między relay a serwerem** blokuje UDP 67 — „DHCP działa w jednym VLAN-ie, a w innym nie”.
- **DHCP snooping bez \`trust\` na uplinku** — po włączeniu snoopingu wszyscy tracą adresy, bo odpowiedzi serwera są odrzucane; albo problem z opcją 82 (serwer odrzuca żądania z giaddr 0.0.0.0 i opcją 82).
- **Za krótka dzierżawa** — duży ruch DHCP i zależność od dostępności serwera; **za długa** w sieci gości — wyczerpana pula przez urządzenia, które dawno wyszły.
- **„IPv6 wyłączyliśmy, więc nie ma zagrożenia”** — systemy mają IPv6 włączone domyślnie i zaakceptują fałszywe RA; wyłącz świadomie albo zabezpiecz (RA Guard).
- **Mylenie flag RA** — M=1 bez serwera DHCPv6 = hosty bez adresów globalnych (lub tylko SLAAC, zależnie od A); Android **nie obsługuje** stanowego DHCPv6 — w sieciach z telefonami potrzebny SLAAC.
- **Adres IP jako tożsamość** — reguły „po IP” dla stacji z DHCP przestają działać po zmianie adresu; używaj rezerwacji, 802.1X/grup, nazw.
- **Zapisywanie adresu z DHCP w konfiguracji aplikacji** — np. klaster, który zapamiętał IP węzła; po restarcie i nowej dzierżawie węzeł „znika”.` },

  { type: 'CODE_WRONG', heading: 'Źle: serwer DHCP „na szybko” i brak ochrony przełącznika', body:
`Typowe ustawienia, po których przychodzą zgłoszenia o konfliktach adresów i dziwnych przekierowaniach.`,
    code:
`ip dhcp pool LAN
 network 10.10.0.0 255.255.255.0
 default-router 10.10.0.1
 dns-server 8.8.8.8                          ! wewnętrzne nazwy (AD, usługi) nie działają
 lease infinite                               ! pula wyczerpie się urządzeniami, których już nie ma
! brak ip dhcp excluded-address — serwer przydziela .1 (brama), .10 (serwer plików) …
!
! Przełącznik dostępowy: brak DHCP snooping
! - domowy router wpięty przez pracownika odpowiada szybciej i rozdaje 192.168.0.x,
! - zalewanie DISCOVER wyczerpuje pulę w kilka sekund,
! - brak tabeli powiązań → brak DAI i śladu „kto miał jaki adres”.
!
! Router IPv6: domyślnie wysyła RA z prefiksem, a przełączniki nie mają RA Guard —
! każdy host może ogłosić się routerem IPv6.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: wykluczenia, rezerwacje, rozsądna dzierżawa, snooping i RA Guard', body:
`Serwer z wykluczeniami i wewnętrznym DNS, centralny serwer przez relay, snooping z zaufanym uplinkiem i limitami, ochrona IPv6, logi do SIEM.`,
    code:
`ip dhcp excluded-address 10.10.0.1 10.10.0.20
ip dhcp pool USERS
 network 10.10.0.0 255.255.255.0
 default-router 10.10.0.1
 dns-server 10.20.0.53 10.20.0.54            ! wewnętrzne resolvery
 domain-name corp.example
 lease 0 8                                    ! 8 h w biurze; goście: 1–2 h
ip dhcp conflict logging
!
ip dhcp snooping
ip dhcp snooping vlan 10,20,30
ip dhcp snooping database flash:snooping.db    ! przetrwa restart przełącznika
interface Te1/1/1
 ip dhcp snooping trust
interface range Gi1/0/1 - 46
 ip dhcp snooping limit rate 15
 ip arp inspection limit rate 30               ! DAI korzysta z tabeli snoopingu
ip arp inspection vlan 10,20,30
!
ipv6 nd raguard policy HOSTS
 device-role host
interface range Gi1/0/1 - 46
 ipv6 nd raguard attach-policy HOSTS
 ipv6 dhcp guard attach-policy CLIENTS
!
logging host 10.99.0.50                       ! DHCP-SNOOPING-5-..., DHCPD-4-PING_CONFLICT → SIEM` },

  { type: 'WHEN_TO_USE', heading: 'Statycznie, rezerwacja, DHCP czy SLAAC?', body:
`- **Statyczny adres** — infrastruktura, od której zależą inni i która musi działać nawet bez DHCP: routery, przełączniki (zarządzanie), serwery DNS i DHCP, kontrolery domeny, bramy. Zawsze spoza puli i udokumentowany w IPAM.
- **Rezerwacja DHCP** — drukarki, kamery, serwery aplikacyjne w sieci on-prem, urządzenia IoT: stały adres, ale zarządzany centralnie (zmiana DNS/bramy bez logowania na urządzenie).
- **DHCP dynamiczny** — stacje robocze, telefony, goście, środowiska testowe; dzierżawa dopasowana do rotacji (goście krótko, biuro dłużej).
- **IPAM platformy** — kontenery, pody, VM-ki w chmurze: adresy z mechanizmów platformy (CNI IPAM, VPC DHCP), aplikacje odnajdują się przez DNS/Service discovery.
- **IPv6**: **SLAAC** (z RDNSS) — najprościej, działa na wszystkich systemach łącznie z Androidem; **bezstanowy DHCPv6** (O=1) — gdy chcesz rozdać DNS/domenę starym klientom; **stanowy DHCPv6** (M=1) — gdy potrzebujesz kontroli i rozliczalności adresów (np. w sieciach serwerowych), pamiętając o braku wsparcia w Androidzie.
- **Bezpieczeństwo** zawsze: DHCP snooping, RA Guard, DHCPv6 Guard na przełącznikach dostępowych; logi przydziałów w SIEM; monitoring wykorzystania pul.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- DHCP (UDP 67/68): DORA — Discover (broadcast), Offer, Request (broadcast), Ack; inne: Decline, NAK, Release, Inform.
- Dzierżawa: T1 = 50% (odnowienie unicastem), T2 = 87,5% (rebinding broadcastem), koniec — host musi porzucić adres.
- Opcje: 1 maska, 3 brama, 6 DNS, 15 domena, 51 dzierżawa, 53 typ, 54 serwer, 66/67 PXE, 82 relay, 119 domeny wyszukiwania, 121 trasy.
- Relay (ip helper-address) przekazuje broadcast unicastem do serwera; giaddr wybiera pulę; opcja 82 niesie port i VLAN.
- IPv6: RA (prefiks, flagi M/O/A, RDNSS) + SLAAC (EUI-64: ff:fe w środku i odwrócony bit U/L, albo adres losowy RFC 8981/7217) + DHCPv6 (546/547, SARR, DUID).
- Serwery statycznie lub z rezerwacją, klienci dynamicznie, kontenery z IPAM platformy; monitoruj pule.
- Zagrożenia: rogue DHCP, starvation, rogue RA → DHCP snooping (trust na uplinku, limit rate), RA Guard, DHCPv6 Guard, logi w SIEM.
- Następna lekcja: **Wi-Fi z perspektywy aplikacji**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const m of ['00:1a:2b:3c:4d:5e', 'a4:83:e7:01:02:03', '00:50:56:ab:cd:ef']) ex(`Wyznacz identyfikator interfejsu EUI-64 i adres link-local IPv6 dla karty o adresie MAC ${m}. Jaki będzie adres globalny w sieci 2001:db8:42::/64?`, 'Podziel MAC na pół, wstaw ff:fe, odwróć bit 0x02 w pierwszym bajcie, zapisz w grupach po 16 bitów.', `Identyfikator: ${eui64(m)}; link-local: fe80::${eui64(m)}; globalny: 2001:db8:42::${eui64(m)}. Pierwszy bajt ${m.slice(0, 2)} → ${(parseInt(m.slice(0, 2), 16) ^ 2).toString(16).padStart(2, '0')}.`);
for (const l of [7200, 86400, 1800]) { const t = timers(l); ex(`Dzierżawa DHCP trwa ${fmtTime(l)}. Kiedy klient spróbuje ją odnowić u swojego serwera (T1), kiedy zacznie szukać dowolnego serwera (T2) i co się stanie, jeśli żaden nie odpowie?`, 'T1 = 0,5 × dzierżawa, T2 = 0,875 × dzierżawa.', `T1 = ${fmtTime(t.t1)} (REQUEST unicastem), T2 = ${fmtTime(t.t2)} (REQUEST broadcastem), po ${fmtTime(l)} bez ACK klient musi przestać używać adresu — połączenia aplikacji zostaną zerwane, klient wraca do DISCOVER.`); }
for (const [p, excl, devices] of [[24, 30, 180], [23, 50, 400]]) ex(`Podsieć /${p}, wykluczone ${excl} adresów (brama, serwery, drukarki). Ile adresów zostaje w puli i czy wystarczy dla ${devices} urządzeń z zapasem 20%?`, 'Hosty = 2^(32−prefiks) − 2; pula = hosty − wykluczenia.', `Hosty: ${hostsIn(p)}; pula: ${hostsIn(p) - excl}; potrzebne z zapasem: ${Math.ceil(devices * 1.2)} → ${hostsIn(p) - excl >= Math.ceil(devices * 1.2) ? 'wystarczy' : 'NIE wystarczy — większa podsieć lub krótsza dzierżawa'}.`);
const more = [
  ['Wykonaj Packet Lab z lekcji (dnsmasq w kontenerze) i przedstaw: listę komunikatów z adresami i portami, wartości opcji 53, 54, 51, 1, 3, 6, 15 oraz moment odnowienia przy dzierżawie 2 min.', 'Wireshark filtr dhcp; dhcp.option.dhcp == 3 (Request).', 'DISCOVER 0.0.0.0:68 → 255.255.255.255:67 (opcja 53 = 1), OFFER 172.30.0.2:67 → 172.30.0.1xx lub broadcast (53 = 2, 54 = 172.30.0.2, 51 = 120 s, 1 = 255.255.255.0, 3 = 172.30.0.1, 6 = 172.30.0.53, 15 = lab.local), REQUEST broadcast (53 = 3, 50 = żądany adres, 54 = serwer), ACK (53 = 5). Odnowienie po ok. 60 s: REQUEST unicastem klient → serwer (ciaddr wypełniony), ACK. Release: 53 = 7, unicast do serwera.'],
  ['Skonfiguruj DHCP relay na przełączniku L3 dla nowego VLAN-u 40 (10.40.0.0/24) z centralnym serwerem 10.20.0.2 i wyjaśnij, jak serwer rozpozna, z której puli przydzielić adres.', 'ip helper-address na SVI; giaddr.', 'interface Vlan40; ip address 10.40.0.1 255.255.255.0; ip helper-address 10.20.0.2. Relay odbiera broadcast DISCOVER w VLAN 40, wpisuje giaddr = 10.40.0.1 (adres swojego interfejsu) i wysyła unicastem do 10.20.0.2 (UDP 67). Serwer wybiera pulę, do której należy giaddr (10.40.0.0/24), odpowiada do relay, a relay dostarcza odpowiedź klientowi. Na serwerze musi istnieć pula/scope 10.40.0.0/24; firewall musi przepuścić UDP 67 między 10.40.0.1 a 10.20.0.2.'],
  ['Po włączeniu DHCP snooping na przełączniku dostępowym wszyscy klienci przestali dostawać adresy. Podaj dwie najczęstsze przyczyny i poprawki.', 'Trusted uplink; opcja 82.', '1) Uplink w stronę serwera/relay nie jest oznaczony jako trusted — odpowiedzi OFFER/ACK są odrzucane; naprawa: ip dhcp snooping trust na uplinku. 2) Przełącznik dodaje opcję 82 z giaddr 0.0.0.0, a serwer (np. IOS, Windows) odrzuca takie żądania; naprawa: no ip dhcp snooping information option na przełączniku albo ip dhcp relay information trust-all na relay / konfiguracja serwera akceptująca opcję 82. Dodatkowo sprawdzić, czy snooping jest włączony dla właściwych VLAN-ów i czy rate limit nie jest zbyt niski.'],
  ['Opisz koncepcyjnie atak rogue DHCP i jego skutki dla aplikacji Java na stacji developera. Jak go wykryć i jak mu zapobiec?', 'Fałszywa brama/DNS → MITM.', 'Napastnik (lub przypadkowy router) odpowiada na DISCOVER szybciej niż prawdziwy serwer i podaje swoją bramę i/lub DNS. Ruch stacji idzie przez niego: może podsłuchiwać nieszyfrowany ruch, podsuwać fałszywe odpowiedzi DNS (np. dla repozytorium Maven lub API), a aplikacja łączy się z podstawionym serwerem — TLS z weryfikacją certyfikatu chroni, ale HTTP, błędnie skonfigurowane trust-all i wycieki nazw już nie. Wykrycie: OFFER/ACK z nieznanego adresu serwera (IDS, snooping log), brama/DNS niezgodne z oczekiwanymi. Zapobieganie: DHCP snooping (porty hostów untrusted), 802.1X, monitoring, w aplikacjach zawsze TLS z weryfikacją.'],
  ['Wyjaśnij, czym różnią się flagi RA: M, O i A, oraz podaj konfigurację RA dla trzech scenariuszy: tylko SLAAC z DNS w RA, SLAAC + bezstanowy DHCPv6, stanowy DHCPv6 bez SLAAC.', 'M — managed, O — other, A — autonomous w opcji prefiksu.', 'Tylko SLAAC: A=1, M=0, O=0, DNS przez RDNSS (RFC 8106). SLAAC + bezstanowy DHCPv6: A=1, M=0, O=1 — adres z SLAAC, DNS/domena z DHCPv6 (Information-request). Stanowy DHCPv6: A=0 (ipv6 nd prefix … no-autoconfig), M=1, O=1 — adresy z DHCPv6, brama zawsze z RA (DHCPv6 nie przekazuje bramy!). Uwaga: Android nie obsługuje stanowego DHCPv6 — w sieciach z telefonami zostaw SLAAC.'],
  ['Dlaczego DHCPv6 nie podaje bramy domyślnej i skąd host IPv6 ją bierze?', 'Rola RA.', 'W IPv6 bramę domyślną zawsze wyznaczają Router Advertisement — host przyjmuje adres link-local routera (źródło RA) jako bramę z czasem życia z pola Router Lifetime. DHCPv6 rozdaje adresy i parametry (DNS, domena), ale nie trasę domyślną. Skutek: nawet przy stanowym DHCPv6 potrzebne są RA — i dlatego fałszywe RA są tak groźne (RA Guard).'],
  ['Na serwerze aplikacji widzisz w ip -6 addr trzy adresy globalne IPv6 z tego samego prefiksu. Skąd się wzięły i którego użyje aplikacja do połączeń wychodzących?', 'Stabilny, tymczasowy (RFC 8981), ewentualnie EUI-64 lub z DHCPv6.', 'Typowo: adres stabilny (RFC 7217 lub EUI-64, flaga mngtmpaddr), jeden lub więcej adresów tymczasowych (temporary dynamic, RFC 8981, zmieniane co kilka godzin/dni) i ewentualnie adres z DHCPv6. Dla połączeń wychodzących system wybiera adres źródłowy wg RFC 6724 i preferencji jądra (use_tempaddr=2 → preferuje tymczasowy). Na serwerze zwykle wyłącza się adresy tymczasowe (use_tempaddr=0) lub używa statycznego, żeby reguły firewalli i logi po stronie partnerów były stabilne; aplikacja może też wiązać gniazdo do konkretnego adresu.'],
  ['Zaprojektuj monitoring DHCP dla firmy: jakie metryki, logi i alerty?', 'Pule, konflikty, rogue serwery, snooping.', 'Metryki: wykorzystanie każdej puli (% i liczba wolnych; alert > 80–90%), liczba DISCOVER/ACK/NAK na minutę (nagły wzrost = starvation lub awaria), czas odpowiedzi serwera, stan failoveru. Logi: przydziały (MAC, IP, nazwa, czas) do SIEM na potrzeby dochodzeń; konflikty (DHCPD-4-PING_CONFLICT, Decline); naruszenia snoopingu (DHCP_SNOOPING_UNTRUSTED_PORT — OFFER na porcie untrusted = rogue serwer); RA z nieautoryzowanych źródeł. Alerty: nowy serwer DHCP w sieci, pula wyczerpana, nietypowa liczba nowych MAC.'],
  ['Wyjaśnij, czym jest DHCP starvation, jak go wykryć w logach przełącznika i jakie mechanizmy go ograniczają.', 'Wiele losowych MAC.', 'Atak polega na wysyłaniu dużej liczby DISCOVER z losowych adresów MAC (pole chaddr), aż serwer przydzieli całą pulę — prawdziwi klienci nie dostają adresów (DoS), a napastnik może następnie uruchomić rogue DHCP. Wykrycie: gwałtowny wzrost liczby dzierżaw, wiele różnych MAC na jednym porcie, logi rate limit snoopingu (err-disable), alerty port security. Ograniczenie: ip dhcp snooping limit rate na portach dostępowych, port security (limit MAC na port), ip dhcp snooping verify mac-address (zgodność MAC ramki i chaddr), 802.1X.'],
  ['Opisz, jak działa provisioning PXE z opcjami 66 i 67 i jakie ryzyka bezpieczeństwa się z tym wiążą.', 'TFTP, plik rozruchowy, brak uwierzytelniania.', 'Karta z PXE wysyła DISCOVER z vendor class „PXEClient”; serwer DHCP odpowiada adresem oraz opcjami 66 (serwer TFTP) i 67 (nazwa pliku, np. pxelinux.0 lub ipxe.efi). Karta pobiera plik TFTP-em i uruchamia instalator/obraz. Ryzyka: TFTP nie ma uwierzytelniania ani szyfrowania, a rogue DHCP może wskazać złośliwy obraz rozruchowy — przejęcie serwera przy instalacji. Obrona: osobny VLAN provisioningu, DHCP snooping, iPXE z HTTPS i podpisami, Secure Boot, ograniczenie dostępu do serwera TFTP.'],
  ['Kontener w Dockerze ma adres 172.17.0.5. Czy dostał go z DHCP? Skąd bierze DNS i dlaczego nie warto zapisywać tego adresu w konfiguracji innych usług?', 'IPAM Dockera, resolv.conf, efemeryczność.', 'Nie — Docker przydziela adresy z własnego IPAM (wbudowany dla sieci bridge), bez DHCP. DNS: w domyślnej sieci bridge kontener dziedziczy /etc/resolv.conf hosta; w sieciach użytkownika używa wbudowanego DNS Dockera (127.0.0.11), który rozwiązuje nazwy kontenerów. Adres zmienia się przy ponownym utworzeniu kontenera, więc inne usługi powinny używać nazwy usługi/kontenera (DNS), nie IP.'],
  ['Wyjaśnij, co się dzieje, gdy dwa serwery DHCP w jednym VLAN-ie obsługują nakładające się pule, i jak poprawnie zapewnić wysoką dostępność DHCP.', 'Konflikty; failover/split scope.', 'Dwa niezależne serwery z nakładającymi się pulami mogą przydzielić ten sam adres dwóm klientom (każdy nie wie o przydziałach drugiego) → konflikty adresów. Poprawnie: DHCP failover (Kea HA, Windows DHCP Failover w trybie load balance lub hot standby, ISC failover) z synchronizacją dzierżaw, albo split scope (np. 80/20) — rozłączne zakresy na dwóch serwerach. Klienci dostaną odpowiedzi od obu, ale przydziały się nie nakładają.'],
  ['Policz identyfikator EUI-64 dla MAC 00:00:5e:00:53:01 i wyjaśnij, dlaczego współczesne systemy domyślnie nie używają EUI-64 w adresach globalnych.', 'Prywatność i śledzenie.', `EUI-64: ${eui64('00:00:5e:00:53:01')} → np. 2001:db8::${eui64('00:00:5e:00:53:01')}. EUI-64 zawiera MAC karty — ten sam identyfikator interfejsu pojawia się w każdej sieci, do której urządzenie się podłączy, co umożliwia śledzenie urządzenia między sieciami i ujawnia producenta (OUI). Dlatego systemy stosują adresy stabilne nieoparte o MAC (RFC 7217, różne w różnych sieciach) i tymczasowe (RFC 8981) dla połączeń wychodzących.`],
  ['Napisz w Javie metodę wyznaczającą adres link-local EUI-64 z adresu MAC.', 'Operacje na bajtach; Inet6Address.', 'static Inet6Address linkLocal(byte[] mac) throws UnknownHostException {\n  byte[] a = new byte[16];\n  a[0] = (byte) 0xfe; a[1] = (byte) 0x80;              // fe80::/64\n  a[8] = (byte) (mac[0] ^ 0x02); a[9] = mac[1]; a[10] = mac[2];\n  a[11] = (byte) 0xff; a[12] = (byte) 0xfe;\n  a[13] = mac[3]; a[14] = mac[4]; a[15] = mac[5];\n  return (Inet6Address) InetAddress.getByAddress(a);\n}\n// Inet6Address.getHostAddress() zwróci postać nieskompresowaną (fe80:0:0:0:…); do wyświetlania można ją skompresować.'],
  ['Analizujesz incydent: „kto miał adres 10.10.0.57 wczoraj o 14:05?”. Jakie źródła danych sprawdzisz i w jakiej kolejności?', 'Logi DHCP, snooping binding, ARP, NAC, przełącznik.', '1) Logi serwera DHCP (Kea/Windows: przydziały i odnowienia z MAC, nazwą hosta, czasem) — kto miał dzierżawę obejmującą 14:05. 2) Logi DHCP snooping/tabela powiązań w SIEM — port i VLAN przełącznika. 3) Logi 802.1X/NAC — użytkownik/urządzenie uwierzytelnione na tym porcie. 4) Historyczne tablice ARP/MAC (jeśli zbierane przez NMS, np. Netdisco). 5) Logi EDR/AD (logowania na hoście). Uwaga na adresy statyczne/konflikty — DHCP nie wie o nich; stąd wartość snoopingu i NAC.'],
  ['Wyjaśnij, czym jest DHCPINFORM i bezstanowy DHCPv6 (Information-request) i kiedy się ich używa.', 'Adres już jest, potrzebne tylko parametry.', 'DHCPINFORM (IPv4) wysyła host, który ma już adres (np. statyczny), a chce pobrać inne parametry (DNS, domena, WPAD) — serwer odpowiada ACK bez przydzielania adresu. W IPv6 odpowiednikiem jest Information-request bezstanowego DHCPv6 (flaga O=1 w RA): adres pochodzi z SLAAC, a DNS i domena z DHCPv6. Serwer nie musi śledzić stanu dzierżaw — prostsza konfiguracja i skalowanie.'],
];
for (const [q, h, a] of more) ex(q, h, a);
const more2 = [
  ['Laptop przeniesiony z sieci domowej (192.168.1.0/24) do biura (10.10.0.0/24) przez chwilę nie ma sieci, a w Wiresharku widać DHCPREQUEST o adres 192.168.1.34 i odpowiedź DHCPNAK. Wyjaśnij przebieg.', 'Stan INIT-REBOOT.', 'Po podłączeniu klient próbuje potwierdzić poprzednią dzierżawę (INIT-REBOOT): wysyła REQUEST o 192.168.1.34. Serwer biura widzi, że ten adres nie należy do podsieci klienta (giaddr/interfejs 10.10.0.0/24), więc odpowiada NAK. Klient porzuca stary adres i zaczyna DORA od DISCOVER — dostaje adres z 10.10.0.0/24. Krótka przerwa jest normalna.'],
  ['Zaplanuj konfigurację dzierżaw dla trzech stref: biuro (stałe stanowiska), Wi-Fi gości na lotnisku firmowym, laboratorium testowe z VM-kami tworzonymi co kilka minut.', 'Rotacja klientów vs obciążenie serwera.', 'Biuro: 8–24 h (stali klienci, mało ruchu DHCP, odporność na krótkie awarie serwera). Goście: 30 min–2 h (duża rotacja, unikanie wyczerpania puli przez urządzenia, które odeszły) i odpowiednio duża podsieć (/22 lub większa). Lab z efemerycznymi VM-kami: krótkie dzierżawy (10–30 min) lub — lepiej — IPAM platformy/rezerwacje sterowane automatyzacją i zwalnianie adresów przy usuwaniu VM (DHCPRELEASE).'],
  ['Przeanalizuj, co zobaczy napastnik w segmencie w komunikatach DHCP innych hostów (opcje 12, 60, 55) i jak tę samą informację wykorzystuje NAC.', 'Fingerprinting DHCP.', 'Opcja 12 — nazwa hosta (np. „LAPTOP-JKOWALSKI”), opcja 60 — vendor class („MSFT 5.0”, „android-dhcp-14”), opcja 55 — lista żądanych parametrów (kolejność charakterystyczna dla systemu). Napastnik dowiaduje się, jakie systemy i kto pracuje w sieci (rozpoznanie). NAC (np. Cisco ISE, ClearPass, PacketFence) używa tego samego fingerprintingu, by profilować urządzenia (drukarka, telefon, Windows) i przypisywać polityki/VLAN-y — ale profil można podrobić, więc nie zastępuje 802.1X.'],
  ['Opisz, jak zbudować w labie Linuksowy router z dnsmasq rozdającym IPv4 przez DHCP i IPv6 przez SLAAC z RDNSS. Jakie parametry dnsmasq użyjesz?', 'dhcp-range dla IPv4 i ra-only/slaac dla IPv6, enable-ra.', 'dnsmasq.conf: interface=br-lab; dhcp-range=10.50.0.100,10.50.0.200,12h; dhcp-option=option:router,10.50.0.1; dhcp-option=option:dns-server,10.50.0.1; enable-ra; dhcp-range=::,constructor:br-lab,ra-only,64,12h (RA z prefiksem interfejsu, SLAAC); dnsmasq dodaje RDNSS (adres routera jako DNS). Router: ip_forward i adres IPv6 z prefiksu /64 na br-lab. Klient: SLAAC + DNS z RA (sprawdź rdisc6 i resolvectl).'],
  ['Wyjaśnij, czym różni się opcja 121 (classless static routes) od opcji 3 i jakie ryzyko bezpieczeństwa wiąże się z nią w kontekście VPN.', 'Trasy wysyłane klientowi; obejście tunelu VPN.', 'Opcja 3 podaje tylko bramę domyślną; opcja 121 przekazuje klientowi listę tras (prefiks + brama), które mają pierwszeństwo przed trasą domyślną (dłuższy prefiks). Ryzyko: rogue DHCP może wysłać trasy obejmujące ruch, który powinien iść tunelem VPN (np. 0.0.0.0/1 i 128.0.0.0/1 przez napastnika) — ruch omija VPN (technika znana jako TunnelVision, CVE-2024-3661). Obrona: klienci VPN ignorujący opcję 121 lub blokujący ruch poza tunelem (kill switch, namespace), DHCP snooping w sieciach firmowych, ostrożność w sieciach publicznych.'],
];
for (const [q, h, a] of more2) ex(q, h, a);
ex('Przygotuj listę kontrolną audytu DHCP i autokonfiguracji IPv6 (minimum 7 punktów).', 'Serwery, pule, relay, snooping, IPv6, logi.', '1) Autoryzowane serwery DHCP znane i udokumentowane, HA przez failover (bez nakładających się pul). 2) Wykluczenia dla adresów statycznych, rezerwacje dla urządzeń infrastruktury. 3) Opcje DNS/domeny wskazują wewnętrzne resolvery. 4) Dzierżawy dopasowane do stref (goście krótko). 5) Relay skonfigurowany dla wszystkich VLAN-ów, firewall przepuszcza UDP 67 tylko między relay a serwerami. 6) DHCP snooping na wszystkich VLAN-ach dostępowych, trust tylko na uplinkach, limit rate, baza powiązań zapisywana. 7) RA Guard i DHCPv6 Guard na portach hostów (także w sieciach „bez IPv6”). 8) Logi przydziałów i naruszeń w SIEM, alerty o wyczerpaniu pul i obcych serwerach.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026112010);
for (const m of ['00:1a:2b:3c:4d:5e', '52:54:00:12:34:56', 'a4:83:e7:01:02:03', '00:50:56:ab:cd:ef', '3c:22:fb:9a:10:20', 'f0:18:98:00:aa:bb', '08:00:27:4e:66:a1', 'dc:a6:32:11:22:33']) {
  const right = eui64(m);
  const b = m.split(':');
  const noFlip = `${b[0]}${b[1]}:${b[2]}ff:fe${b[3]}:${b[4]}${b[5]}`.replace(/(^|:)0+(?=[0-9a-f])/g, '$1');
  const noFffe = `${(parseInt(b[0], 16) ^ 2).toString(16).padStart(2, '0')}${b[1]}:${b[2]}${b[3]}:${b[4]}${b[5]}:0`.replace(/(^|:)0+(?=[0-9a-f])/g, '$1');
  add(`Jaki identyfikator interfejsu EUI-64 powstanie z MAC ${m}?`, right, [noFlip === right ? right.replace(/^([0-9a-f]+)/, (x) => (parseInt(x, 16) ^ 0x0100).toString(16)) : noFlip, noFffe, `${right.split(':').reverse().join(':')}`], `Wstaw ff:fe w środek i odwróć bit U/L (XOR 0x02 w pierwszym bajcie): ${right}.`);
}
for (const l of [3600, 28800, 86400, 7200, 43200, 604800]) {
  const t = timers(l);
  add(`Dzierżawa DHCP wynosi ${fmtTime(l)}. Kiedy upływa T1?`, fmtTime(t.t1), [fmtTime(t.t2), fmtTime(l), fmtTime(l / 4)], `T1 = 50% dzierżawy = ${fmtTime(t.t1)}.`);
  add(`Dzierżawa DHCP wynosi ${fmtTime(l)}. Kiedy upływa T2?`, fmtTime(t.t2), [fmtTime(t.t1), fmtTime(l), fmtTime(l * 3 / 4)], `T2 = 87,5% dzierżawy = ${fmtTime(t.t2)}.`);
}
const mAll = MSG.map(([n, m]) => m);
for (const [n, m] of MSG) add(`Jaki komunikat DHCP ma w opcji 53 wartość ${n}?`, m, mAll.filter((x) => x !== m).slice(0, 3), `${n} = ${m}.`);
const oAll = OPT.map(([, d]) => d);
for (const [n, d] of OPT) add(`Co przenosi opcja DHCP ${n}?`, d, oAll.filter((x) => x !== d).slice(0, 3), `Opcja ${n}: ${d}.`);
for (const [p, ex2] of [[24, 10], [25, 5], [23, 20], [26, 4], [22, 100], [27, 3]]) add(`Podsieć /${p} z ${ex2} wykluczonymi adresami — ile adresów pozostaje w puli DHCP?`, String(hostsIn(p) - ex2), [String(hostsIn(p)), String(2 ** (32 - p) - ex2), String(hostsIn(p) - ex2 - 2)], `${hostsIn(p)} hostów − ${ex2} = ${hostsIn(p) - ex2}.`);
const facts = [
  ['Jakich portów UDP używa DHCPv4?', 'Serwer 67, klient 68', ['Serwer 68, klient 67', 'Serwer 53, klient 53', 'Serwer 546, klient 547'], '546/547 to DHCPv6.'],
  ['Jakich portów UDP używa DHCPv6?', 'Klient 546, serwer 547', ['Klient 68, serwer 67', 'Klient 547, serwer 546', 'Klient 5353, serwer 53'], 'Multicast ff02::1:2.'],
  ['Na jaki adres wysyłany jest DHCPDISCOVER?', '255.255.255.255', ['Adres bramy', '224.0.0.1', 'Adres serwera DNS'], 'Klient nie zna serwera ani swojego adresu (źródło 0.0.0.0).'],
  ['Dlaczego DHCPREQUEST w DORA jest wysyłany broadcastem?', 'Aby wszystkie serwery wiedziały, którą ofertę klient przyjął', ['Bo klient nie zna MAC serwera', 'Bo unicast jest zabroniony w DHCP', 'Aby ominąć firewall'], 'Inne serwery zwalniają zaproponowane adresy.'],
  ['Jak klient odnawia dzierżawę po T1?', 'Wysyła DHCPREQUEST unicastem do serwera, który przydzielił adres', ['Wysyła DISCOVER broadcastem', 'Wysyła RELEASE', 'Czeka na koniec dzierżawy'], 'Po T2 — broadcastem do dowolnego serwera.'],
  ['Co musi zrobić klient po wygaśnięciu dzierżawy bez odnowienia?', 'Przestać używać adresu i rozpocząć DORA od nowa', ['Używać adresu dalej', 'Przejść na adres 169.254.0.1 na stałe', 'Zrestartować system'], 'Połączenia zostaną zerwane.'],
  ['Co oznacza adres 169.254.x.x na kliencie?', 'Brak odpowiedzi DHCP — adres APIPA/link-local', ['Poprawny adres z DHCP', 'Adres bramy', 'Adres multicast'], 'Sprawdź łączność z serwerem DHCP lub relay.'],
  ['Do czego służy DHCPDECLINE?', 'Klient informuje, że zaproponowany adres jest już używany (konflikt)', ['Klient zwalnia adres', 'Serwer odmawia przydziału', 'Klient prosi o dłuższą dzierżawę'], 'Wykrycie przez ARP probe.'],
  ['Kiedy serwer wysyła DHCPNAK?', 'Gdy żądany adres jest niepoprawny dla tej sieci lub niedostępny', ['Gdy przydziela adres', 'Gdy klient zwalnia adres', 'Co T1'], 'Np. po przeniesieniu laptopa do innej podsieci.'],
  ['Co robi DHCP relay (ip helper-address)?', 'Przekazuje broadcast DHCP klienta unicastem do serwera w innej podsieci', ['Przydziela adresy zamiast serwera', 'Blokuje rogue DHCP', 'Tłumaczy adresy NAT'], 'Ustawia giaddr.'],
  ['Do czego serwer DHCP używa pola giaddr?', 'Do wyboru puli właściwej dla podsieci klienta', ['Do szyfrowania odpowiedzi', 'Jako adresu klienta', 'Jako bramy domyślnej klienta'], 'giaddr = adres interfejsu relay.'],
  ['Co zawiera opcja 82?', 'Informacje agenta relay, np. port i VLAN klienta', ['Adres DNS', 'Czas dzierżawy', 'Nazwę hosta klienta'], 'Relay Agent Information.'],
  ['Co robi DHCP snooping na portach untrusted?', 'Odrzuca odpowiedzi serwera DHCP (OFFER/ACK) z tych portów', ['Blokuje cały ruch DHCP', 'Szyfruje DHCP', 'Przydziela adresy'], 'Trust tylko na uplinkach.'],
  ['Który mechanizm korzysta z tabeli powiązań DHCP snooping, by blokować ARP spoofing?', 'Dynamic ARP Inspection (DAI)', ['RA Guard', 'BPDU Guard', 'Storm control'], 'Także IP Source Guard.'],
  ['Na czym polega DHCP starvation?', 'Na wyczerpaniu puli przez masowe DISCOVER z losowych MAC', ['Na podmianie bramy w OFFER', 'Na zalewaniu BPDU', 'Na przejęciu DNS'], 'Obrona: limit rate, port security.'],
  ['Jaki skutek ma rogue DHCP podający fałszywą bramę?', 'Ruch klientów przechodzi przez urządzenie napastnika (MITM)', ['Klienci tracą tylko DNS', 'Wyłącza się STP', 'Nic — klient weryfikuje serwer'], 'DHCP nie ma uwierzytelniania serwera.'],
  ['Czym jest rezerwacja DHCP?', 'Stałym adresem przydzielanym zawsze temu samemu klientowi (MAC/Client ID)', ['Wykluczeniem adresu z puli', 'Adresem statycznym wpisanym ręcznie na hoście', 'Dzierżawą bez końca dla wszystkich'], 'Centralne zarządzanie, stały adres.'],
  ['Dlaczego adres statyczny nie powinien należeć do puli DHCP?', 'Serwer może przydzielić go komuś innemu, powodując konflikt', ['Bo DHCP go zablokuje', 'Bo statyczne adresy muszą być parzyste', 'Bo inaczej nie zadziała DNS'], 'Wyklucz go lub użyj rezerwacji.'],
  ['Co to jest SLAAC?', 'Samodzielne tworzenie adresu IPv6 przez hosta z prefiksu ogłoszonego w RA', ['Stanowy DHCPv6', 'Protokół routingu', 'Odmiana ARP'], 'RFC 4862.'],
  ['Jakiego typu wiadomość ICMPv6 to Router Advertisement?', '134', ['133', '135', '136'], '133 RS, 135 NS, 136 NA.'],
  ['Co oznacza flaga M=1 w RA?', 'Adresy należy pobrać ze stanowego DHCPv6', ['DNS jest w RA', 'Host ma użyć SLAAC', 'Router jest bramą domyślną'], 'Managed address configuration.'],
  ['Co oznacza flaga O=1 w RA?', 'Inne parametry (np. DNS) należy pobrać z DHCPv6', ['Adres z DHCPv6', 'Router jest wyłączony', 'Prefiks jest tymczasowy'], 'Other configuration — bezstanowy DHCPv6.'],
  ['Skąd host IPv6 bierze bramę domyślną?', 'Z Router Advertisement (adres link-local routera)', ['Z DHCPv6 opcji router', 'Z DNS', 'Z ARP'], 'DHCPv6 nie przekazuje bramy.'],
  ['Czym jest RDNSS?', 'Opcją RA przekazującą adresy serwerów DNS', ['Rekordem DNS dla routera', 'Odmianą DHCPv6', 'Protokołem relay'], 'RFC 8106.'],
  ['Jaką długość prefiksu wymaga SLAAC?', '/64', ['/48', '/56', '/128'], 'Identyfikator interfejsu ma 64 bity.'],
  ['Dlaczego współczesne systemy unikają EUI-64 w adresach globalnych?', 'Bo zdradza MAC i umożliwia śledzenie urządzenia', ['Bo jest za długi', 'Bo nie działa z /64', 'Bo wymaga DHCPv6'], 'Adresy RFC 7217/8981.'],
  ['Co sprawdza host przed użyciem nowego adresu IPv6?', 'Czy adres nie jest zajęty (DAD przez Neighbor Solicitation)', ['Czy DNS odpowiada', 'Czy router ma NAT', 'Czy MTU wynosi 1500'], 'Duplicate Address Detection.'],
  ['Co chroni przed fałszywymi Router Advertisement?', 'RA Guard na przełącznikach', ['DHCP snooping dla IPv4', 'BPDU Guard', 'Port security'], 'Plus DHCPv6 Guard.'],
  ['Dlaczego sieć „tylko IPv4” wymaga RA Guard?', 'Bo hosty mają IPv6 włączone domyślnie i przyjmą fałszywe RA', ['Bo IPv4 używa RA', 'Bo RA zastępuje DHCP w IPv4', 'Nie wymaga'], 'Napastnik może przejąć ruch IPv6.'],
  ['Który system mobilny nie obsługuje stanowego DHCPv6?', 'Android', ['iOS', 'Windows 11', 'macOS'], 'W sieciach z telefonami potrzebny SLAAC.'],
  ['Jak nazywa się identyfikator klienta w DHCPv6?', 'DUID', ['chaddr', 'giaddr', 'OUI'], 'DHCP Unique Identifier.'],
  ['Jaka sekwencja komunikatów odpowiada DORA w DHCPv6?', 'Solicit, Advertise, Request, Reply', ['Discover, Offer, Request, Ack', 'Hello, Update, Ack', 'Query, Response'], 'SARR.'],
  ['Skąd kontener Dockera w sieci bridge bierze adres IP?', 'Z wbudowanego IPAM Dockera, bez DHCP', ['Z serwera DHCP sieci firmowej', 'Z SLAAC', 'Z DNS'], 'Pody w K8s — z IPAM CNI.'],
  ['Dlaczego serwery infrastruktury (DNS, DHCP, bramy) mają adresy statyczne?', 'Bo inni od nich zależą, a muszą działać nawet bez DHCP', ['Bo DHCP ich nie obsługuje', 'Bo statyczne adresy są szybsze', 'Bo tak wymaga TCP'], 'Udokumentowane w IPAM.'],
  ['Co sygnalizuje w logach naruszenie DHCP snooping na porcie untrusted?', 'Odpowiedź serwera DHCP z portu, gdzie nie powinno być serwera — możliwy rogue DHCP', ['Normalny przydział adresu', 'Odnowienie dzierżawy', 'Zmianę root bridge'], 'Alert w SIEM.'],
  ['Jakie opcje DHCP wskazują serwer i plik rozruchowy PXE?', '66 i 67', ['3 i 6', '51 i 53', '82 i 119'], 'TFTP server name, bootfile name.'],
  ['Co może się stać, gdy dwa niezależne serwery DHCP obsługują nakładające się pule w jednym VLAN-ie?', 'Konflikty adresów — ten sam adres dla dwóch klientów', ['Szybsze przydzielanie', 'Automatyczny failover', 'Nic'], 'Używaj failoveru lub rozłącznych zakresów.'],
  ['Co robi ip dhcp snooping limit rate 15?', 'Ogranicza liczbę pakietów DHCP na porcie do 15 na sekundę', ['Ogranicza liczbę klientów do 15', 'Skraca dzierżawę do 15 minut', 'Ustawia 15 serwerów zaufanych'], 'Przekroczenie → err-disable.'],
  ['Dlaczego nie warto zapisywać w konfiguracji aplikacji adresu IP hosta otrzymanego z DHCP?', 'Bo po nowej dzierżawie adres może się zmienić', ['Bo Java nie obsługuje IP', 'Bo adres jest tajny', 'Bo DNS tego zabrania'], 'Używaj nazw.'],
  ['Jakie źródło danych najlepiej odpowie na pytanie „kto miał dany adres IP o danej godzinie”?', 'Logi serwera DHCP i powiązania DHCP snooping (z NAC)', ['Tablica routingu', 'Logi STP', 'Konfiguracja VLAN'], 'Trzymaj je w SIEM.'],
  ['Który komunikat wysyła host ze statycznym adresem, by pobrać tylko inne parametry (DNS, domena)?', 'DHCPINFORM', ['DHCPDISCOVER', 'DHCPDECLINE', 'DHCPRELEASE'], 'W IPv6 — Information-request.'],
  ['Co powinien zawierać firewall między relay a serwerem DHCP?', 'Zezwolenie na UDP 67 (i odpowiedzi) między adresami relay a serwerami', ['Zezwolenie na TCP 53', 'Blokadę UDP 67', 'Zezwolenie na ICMP tylko'], 'Inaczej „DHCP działa tylko w jednym VLAN-ie”.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const [m, net] of [['00:1a:2b:3c:4d:5e', '2001:db8:10::'], ['52:54:00:12:34:56', '2001:db8:20::'], ['a4:83:e7:01:02:03', '2001:db8:aa::'], ['08:00:27:4e:66:a1', 'fd00:1::']]) {
  const id = eui64(m);
  add(`Jaki adres SLAAC (EUI-64) utworzy karta o MAC ${m} w sieci ${net}/64?`, net + id, ['fe80::' + id, net + m.replace(/:/g, '').match(/.{4}/g).join(':'), net + id.replace('ff:fe', 'fe:ff')], `Prefiks ${net}/64 + identyfikator EUI-64 ${id}.`);
}
const facts2 = [
  ['Jaki stan ma klient DHCP, który po ponownym podłączeniu prosi o swój poprzedni adres?', 'INIT-REBOOT', ['SELECTING', 'BOUND', 'REBINDING'], 'Wysyła REQUEST o poprzedni adres; może dostać NAK.'],
  ['Jaki stan ma klient między T1 a T2?', 'RENEWING', ['REBINDING', 'INIT', 'SELECTING'], 'Unicast do własnego serwera.'],
  ['Jaki stan ma klient między T2 a końcem dzierżawy?', 'REBINDING', ['RENEWING', 'BOUND', 'INIT-REBOOT'], 'Broadcast do dowolnego serwera.'],
  ['Co sprawdza klient po otrzymaniu DHCPACK, zanim użyje adresu?', 'Czy adres nie jest zajęty (ARP probe)', ['Czy DNS działa', 'Czy brama ma NAT', 'Czy serwer ma certyfikat'], 'Przy konflikcie wysyła DECLINE.'],
  ['Jaką podatność opisuje „TunnelVision” (CVE-2024-3661)?', 'Rogue DHCP z opcją 121 kieruje ruch poza tunel VPN', ['Przepełnienie bufora w DNS', 'Atak na STP', 'Złamanie WPA3'], 'Obrona: klient VPN odporny na trasy z DHCP.'],
  ['Co oznacza w ipconfig /all pole „Lease Expires”?', 'Moment wygaśnięcia dzierżawy, jeśli nie zostanie odnowiona', ['Datę instalacji karty', 'Termin ważności certyfikatu', 'Czas życia rekordu DNS'], 'Odnowienie zwykle następuje w T1.'],
];
for (const [q, a, w, e] of facts2) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
