// Generuje lekcje _ns_02_ethernet_lan/02_MacAddressesAndSwitching.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_02_mac_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/02_MacAddressesAndSwitching.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const first = (mac) => parseInt(mac.slice(0, 2), 16);
const isMulti = (mac) => (first(mac) & 1) === 1;
const isLocal = (mac) => (first(mac) & 2) === 2;
const kind = (mac) => (mac.toLowerCase() === 'ff:ff:ff:ff:ff:ff' ? 'broadcast' : isMulti(mac) ? 'multicast (grupowy)' : 'unicast') + ', ' + (isLocal(mac) ? 'lokalnie administrowany' : 'globalnie unikalny (OUI)');
const bin8 = (n) => n.toString(2).padStart(8, '0');

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Kto jest kim w sieci lokalnej', ...img('ns02-02-mac-structure.svg', 'Adres MAC 3C:52:82:1A:F0:07: pierwsze trzy bajty to OUI producenta, kolejne trzy — numer karty. Pierwszy bajt 0x3C rozpisany na bity 00111100 z zaznaczonymi bitami U/L i I/G. Pod spodem opis: bit I/G rozróżnia unicast i adresy grupowe, bit U/L adresy globalne i lokalnie administrowane; przykłady Docker 02:42, multicast IPv6 33:33, multicast IPv4 01:00:5e, broadcast oraz losowe adresy MAC w Wi-Fi.'),
    body:
`Adres IP mówi, **dokąd** pakiet zmierza w skali Internetu. Adres **MAC** mówi, **która karta sieciowa w tym segmencie** ma odebrać ramkę. Na każdym odcinku drogi pakietu ramka ma inne adresy MAC — i to przełącznik, a nie router, decyduje, na który port ją wysłać.

Przełącznik (switch) działa w warstwie 2: **uczy się**, za którym portem stoi który adres MAC, i dzięki temu wysyła ramki tylko tam, gdzie trzeba. Gdy czegoś nie wie — **zalewa** ramką wszystkie porty (flooding). Na tym prostym mechanizmie opiera się każda sieć LAN, bridge Dockera i wirtualne przełączniki w chmurze i hypervisorach.

Dla obrońcy adres MAC to ważny ślad (logi DHCP, NAC, tablice przełączników pozwalają ustalić, które urządzenie miało adres IP w chwili incydentu), ale też słaby identyfikator — da się go zmienić jednym poleceniem, a telefony domyślnie losują go w każdej sieci Wi-Fi.

W tej lekcji poznasz budowę adresu MAC (OUI, bity I/G i U/L), algorytm uczenia się przełącznika, domeny kolizyjne i rozgłoszeniowe, zbudujesz przełącznik z bridge’a Linuksa i przyjrzysz się atakom warstwy 2 od strony wykrywania i obrony.` },

  { type: 'DEFINITION', heading: 'Adres MAC i przełączanie: pojęcia', body:
`- **Adres MAC** — 48-bitowy identyfikator interfejsu warstwy 2, zapisywany jako 6 bajtów hex (\`3c:52:82:1a:f0:07\`, \`3C-52-82-1A-F0-07\`, \`3c52.821a.f007\` w Cisco).
- **OUI (Organizationally Unique Identifier)** — pierwsze 24 bity przydzielane producentowi przez IEEE (są też mniejsze bloki MA-M i MA-S); pozwala rozpoznać producenta karty.
- **Bit I/G** (najmłodszy bit pierwszego bajtu) — 0 unicast, 1 adres grupowy (multicast, broadcast).
- **Bit U/L** (drugi najmłodszy bit) — 0 adres globalnie unikalny (nadany przez producenta), 1 lokalnie administrowany (nadany programowo: kontenery, VM, losowe MAC).
- **Broadcast** — \`ff:ff:ff:ff:ff:ff\`; ramkę odbierają wszystkie urządzenia w domenie rozgłoszeniowej (VLAN).
- **Tablica MAC (CAM, FDB)** — w przełączniku: adres MAC → port (i VLAN), z czasem wygaśnięcia (*aging*, typowo 300 s).
- **Uczenie się (learning)** — przełącznik zapisuje **adres źródłowy** każdej odebranej ramki z portem, z którego przyszła.
- **Przekazywanie (forwarding) / filtrowanie** — ramka do znanego adresu idzie tylko na właściwy port; jeśli adresat jest za portem źródłowym — jest odrzucana.
- **Flooding** — ramka do nieznanego adresu unicast, broadcast lub (bez IGMP snooping) multicast trafia na wszystkie porty VLAN-u poza źródłowym.
- **Domena kolizyjna** — obszar, w którym dwie stacje nadające jednocześnie zakłócają się (hub: wszyscy; przełącznik: każdy port osobno).
- **Domena rozgłoszeniowa** — obszar, do którego dociera broadcast (cały VLAN; granicę stawia router lub VLAN).
- **Store-and-forward vs cut-through** — przełącznik odbiera całą ramkę i sprawdza FCS przed wysłaniem albo zaczyna wysyłać po odczytaniu adresu docelowego (mniejsze opóźnienie, ale przepuszcza uszkodzone ramki).` },

  { type: 'ANALOGY', heading: 'Portier w biurowcu, który uczy się po głosie', body:
`Wyobraź sobie portiera w biurowcu z wieloma korytarzami (portami). Na początku nie wie, kto gdzie pracuje.
- Ktoś z korytarza 1 przekazuje list „dla Basi”. Portier **zapamiętuje**: „nadawca, Ania, siedzi w korytarzu 1”. Basi jeszcze nie zna — więc **ogłasza w każdym korytarzu** (poza pierwszym): „list dla Basi!” (flooding).
- Basia odpowiada z korytarza 2. Portier zapisuje: „Basia — korytarz 2” i zanosi odpowiedź **tylko** do korytarza 1.
- Od tej chwili listy Ani do Basi idą prosto do korytarza 2 — inni nawet o nich nie wiedzą.
- Jeśli ktoś długo się nie odzywa, portier wykreśla go z notesu (aging).
- Ogłoszenie „do wszystkich” (broadcast) zawsze słyszy cały biurowiec — chyba że biurowiec podzielono na niezależne skrzydła (VLAN-y) albo budynki (routery).

Słabość portiera: wierzy każdemu, kto się przedstawi. Ktoś może przedstawić się jako Basia z innego korytarza (MAC spoofing) albo zasypać go tysiącami fałszywych imion, aż notes się przepełni i portier zacznie wszystko ogłaszać wszystkim (MAC flooding). Dlatego w porządnych biurowcach portier sprawdza identyfikatory (802.1X) i pozwala na ograniczoną liczbę osób na korytarz (port security).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Algorytm przełącznika krok po kroku', ...img('ns02-02-switch-learning.svg', 'Cztery panele z przełącznikiem SW i hostami A, B, C, D na portach p1–p4: 1) A wysyła do nieznanego B — przełącznik uczy się A na p1 i zalewa ramką porty p2, p3, p4; 2) B odpowiada — przełącznik uczy się B na p2 i wysyła tylko na p1; 3) kolejna ramka A do B idzie tylko na p2; 4) broadcast od A trafia na wszystkie pozostałe porty. Obok każdego panelu tablica MAC.'),
    body:
`Dla każdej odebranej ramki przełącznik wykonuje ten sam algorytm (w obrębie VLAN-u portu wejściowego):`,
    code:
`odebrano ramkę na porcie P (VLAN V)
1. UCZENIE:   tablica[V][MAC_źródłowy] = P    (odśwież licznik wygaśnięcia)
2. DECYZJA:
   jeśli MAC_docelowy == ff:ff:ff:ff:ff:ff (broadcast)  → FLOOD: wszystkie porty VLAN V poza P
   jeśli MAC_docelowy jest grupowy (bit I/G = 1)         → FLOOD (lub tylko do zainteresowanych przy IGMP/MLD snooping)
   jeśli tablica[V][MAC_docelowy] nie istnieje          → FLOOD (unknown unicast)
   jeśli tablica[V][MAC_docelowy] == P                  → FILTER: odrzuć (adresat jest za portem źródłowym)
   w przeciwnym razie                                    → FORWARD: tylko na port tablica[V][MAC_docelowy]

Przykładowa tablica (Cisco: show mac address-table):
  Vlan    Mac Address       Type        Ports
  ----    -----------       --------    -----
   10     3c52.821a.f007    DYNAMIC     Gi1/0/3
   10     0050.5601.0203    DYNAMIC     Gi1/0/7
   20     001b.21aa.bbcc    STATIC      Gi1/0/12` },

  { type: 'CODE_BASIC', heading: 'Analiza adresu MAC w Javie', body:
`Kilka linijek, które rozpoznają typ adresu (unicast/multicast/broadcast, globalny/lokalny) i wyciągają OUI — przydatne przy analizie logów DHCP, NAC i inwentaryzacji urządzeń.`,
    code:
`import java.util.HexFormat;

record MacInfo(String normalized, boolean multicast, boolean broadcast, boolean locallyAdministered, String oui) {}

final class Macs {
    static MacInfo parse(String mac) {
        String hex = mac.replaceAll("[^0-9A-Fa-f]", "");          // obsługa ':', '-', '.'
        if (hex.length() != 12) throw new IllegalArgumentException("zły MAC: " + mac);
        byte[] b = HexFormat.of().parseHex(hex);
        int first = b[0] & 0xFF;
        boolean broadcast = hex.equalsIgnoreCase("ffffffffffff");
        return new MacInfo(
            HexFormat.ofDelimiter(":").formatHex(b),
            (first & 0x01) != 0,                                   // bit I/G
            broadcast,
            (first & 0x02) != 0,                                   // bit U/L
            HexFormat.ofDelimiter(":").withUpperCase().formatHex(b, 0, 3));
    }
}
// Macs.parse("3C-52-82-1A-F0-07") → multicast=false, local=false, oui=3C:52:82
// Macs.parse("02:42:ac:11:00:02") → local=true (Docker)
// Macs.parse("33:33:00:00:00:01") → multicast=true (IPv6 all-nodes)` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab: przełącznik z bridge’a Linuksa', ...img('ns02-02-bridge-lab.svg', 'Bridge br0 połączony parami veth z trzema przestrzeniami nazw ns1, ns2, ns3 o adresach 10.0.0.1–3/24; pod spodem wynik bridge fdb show po pingu z ns1 do ns2 z dwoma nauczonymi adresami MAC.'),
    body:
`Ćwiczenie z planu lekcji (Linux lub WSL2, jako root). Bridge Linuksa to dokładnie ten sam mechanizm, którego używa Docker (\`docker0\`).
1. **Utwórz bridge i trzy przestrzenie nazw**: \`ip link add br0 type bridge && ip link set br0 up\`; \`for n in 1 2 3; do ip netns add ns$n; done\`.
2. **Połącz je parami veth**: dla każdego n: \`ip link add veth-ns$n type veth peer name veth-ns$n-br\`, \`ip link set veth-ns$n netns ns$n\`, \`ip link set veth-ns$n-br master br0 up\`.
3. **Nadaj adresy** (opcjonalnie stałe MAC dla czytelności): \`ip -n ns$n link set veth-ns$n address aa:bb:cc:00:00:0$n\`, \`ip -n ns$n addr add 10.0.0.$n/24 dev veth-ns$n\`, \`ip -n ns$n link set veth-ns$n up\`.
4. **Obejrzyj pustą tablicę**: \`bridge fdb show br br0 | grep -v permanent\` — brak nauczonych adresów.
5. **Wygeneruj ruch**: \`ip netns exec ns1 ping -c 2 10.0.0.2\` i ponownie \`bridge fdb show\` — pojawiają się MAC ns1 i ns2 na właściwych portach, ns3 nadal nieznany.
6. **Zobacz flooding**: w ns3 uruchom \`tcpdump -i veth-ns3 -nn -e\`, wyczyść tablicę (\`bridge fdb flush dev veth-ns2-br\` lub ustaw krótkie \`ageing_time\`) i ponów ping z ns1 — ns3 widzi zapytanie ARP (broadcast), ale nie widzi ramek unicast ns1↔ns2, gdy tablica jest już nauczona.
7. **Zmień czas wygasania**: \`ip link set br0 type bridge ageing_time 1000\` (10 s, w setnych częściach sekundy) i obserwuj znikanie wpisów.
8. **Posprzątaj**: \`ip netns del ns1 ns2 ns3\`; \`ip link del br0\`.` },

  { type: 'NOTE', heading: 'Domeny kolizyjne i rozgłoszeniowe', ...img('ns02-02-domains.svg', 'Trzy panele: hub — jedna domena kolizyjna i jedna rozgłoszeniowa; przełącznik — cztery domeny kolizyjne (po jednej na port) i jedna rozgłoszeniowa na VLAN; router z dwoma interfejsami — osobne domeny kolizyjne i dwie domeny rozgłoszeniowe.'),
    body:
`Klasyczne pytanie egzaminacyjne Network+ i CCNA: ile jest domen w danej topologii? Reguły:`,
    code:
`urządzenie       domeny kolizyjne                         domeny rozgłoszeniowe
hub, repeater    nie dzieli (wszystkie porty = 1 domena)   nie dzieli
przełącznik      każdy port = osobna domena                nie dzieli (1 na VLAN)
VLAN             —                                         każdy VLAN = osobna domena
router           każdy interfejs = osobna domena           każdy interfejs = osobna domena

Przykład: router z 2 interfejsami; do jednego przełącznik 8-portowy (7 hostów),
do drugiego hub z 4 hostami.
  kolizyjne:      8 (porty przełącznika, w tym uplink do routera) + 1 (hub z łączem do routera) = 9
  rozgłoszeniowe: 2 (po jednej na interfejs routera)

W pełnym duplexie (przełącznik ↔ host) kolizje w praktyce nie występują —
pojęcie domeny kolizyjnej ma dziś głównie znaczenie historyczne i egzaminacyjne.
Domena rozgłoszeniowa jest za to bardzo aktualna: duży VLAN = dużo broadcastów (ARP, DHCP, mDNS).` },

  { type: 'CODE_PRACTICAL', heading: 'Tablice MAC w praktyce: Linux, Windows, Cisco, Docker', body:
`Gdzie podejrzeć adresy MAC i tablice przełączania w różnych środowiskach:`,
    code:
`# Własne interfejsy
ip -br link                                  # Linux: nazwy, stan, MAC
Get-NetAdapter | Select Name, MacAddress     # Windows
getmac /v                                    # Windows (cmd)

# Tablica bridge’a Linuksa / Dockera
bridge fdb show br docker0
docker network inspect bridge --format '{{range .Containers}}{{.Name}} {{.MacAddress}}{{"\\n"}}{{end}}'

# Przełącznik Cisco
show mac address-table
show mac address-table address 3c52.821a.f007     # gdzie jest to urządzenie?
show mac address-table count                       # zajętość tablicy (wykrywanie MAC flooding)
show port-security interface Gi1/0/3

# Producent po OUI
grep -i '^3C:52:82' /usr/share/wireshark/manuf    # baza producentów Wiresharka
# W Wiresharku kolumna „Resolved” pokazuje producenta (np. HewlettP_1a:f0:07)

# Zmiana MAC (lab!) — pokazuje, jak słabym identyfikatorem jest MAC
sudo ip link set dev eth0 down && sudo ip link set dev eth0 address 02:00:00:00:00:99 && sudo ip link set dev eth0 up` },

  { type: 'USAGE', heading: 'Adres MAC oczami developera i obrońcy', body:
`**Developer / DevOps:**
- **Kontenery i VM** dostają lokalnie administrowane MAC (bit U/L = 1), generowane programowo — kolizje MAC w jednej sieci (np. sklonowane VM) powodują niestabilną łączność.
- **Wirtualne przełączniki** (bridge Linuksa, Open vSwitch, vSwitch w hypervisorach, sieci VPC) działają według tego samego algorytmu uczenia się.
- **Licencje i „identyfikator maszyny” oparty na MAC** — zły pomysł w kontenerach i chmurze, gdzie MAC zmienia się przy każdym uruchomieniu.

**Blue team:**
- **Atrybucja** — w incydencie łańcuch IP → MAC (logi DHCP, tablice ARP) → port przełącznika (tablica MAC) → gniazdo w biurze / użytkownik (802.1X) pozwala znaleźć fizyczne urządzenie.
- **Inwentaryzacja i wykrywanie nieznanych urządzeń** — nowe MAC na portach, nieznane OUI (np. Raspberry Pi w sieci serwerowej), urządzenia IoT.
- **MAC flooding (przepełnienie tablicy CAM)** — zasypanie przełącznika tysiącami fałszywych adresów źródłowych, by przeszedł w tryb zalewania i ujawniał ruch innych. Wykrywanie: gwałtowny wzrost liczby adresów na porcie, alerty port security. Obrona: **port security** (limit adresów na port, akcja *restrict/shutdown*), **802.1X/NAC**.
- **MAC spoofing** — podszycie się pod adres zaufanego urządzenia (np. drukarki z wyjątkiem w NAC, *MAB*). Wykrywanie: ten sam MAC na dwóch portach („MAC flapping” w logach przełącznika), niezgodny fingerprint urządzenia. Obrona: 802.1X z certyfikatami zamiast samego MAB.
- **Losowe MAC w Wi-Fi** — utrudniają śledzenie użytkowników (prywatność), ale też inwentaryzację i polityki oparte na MAC; firmowe sieci powinny opierać się na uwierzytelnianiu (802.1X), a nie na MAC.` },

  { type: 'PITFALL', heading: 'Najczęstsze nieporozumienia', body:
`- **„Przełącznik zna adresy IP”** — przełącznik L2 widzi tylko MAC (i VLAN). Adresy IP interesują router i przełącznik L3.
- **„MAC jest unikalny i stały”** — można go zmienić jednym poleceniem; telefony i komputery losują go w Wi-Fi; kontenery i VM dostają programowe adresy.
- **„MAC podróżuje przez Internet”** — nie: zmienia się na każdym przeskoku przez router. Serwer w Internecie nie zna MAC Twojego laptopa.
- **„Flooding to błąd”** — to normalne zachowanie dla nieznanych adresów i broadcastu; problemem jest *ciągły* flooding (przepełniona tablica, asymetryczny routing, wygasające wpisy).
- **Pętla L2** — dwa połączenia między przełącznikami bez STP: broadcast krąży bez końca, tablica MAC „skacze” między portami (MAC flapping) — sieć pada w sekundy (lekcja o STP).
- **Uczenie się po adresie docelowym** — nie: przełącznik uczy się wyłącznie z adresu **źródłowego**.
- **Zaufanie do filtrowania po MAC** — allow-listy MAC (w Wi-Fi czy NAC) nie są uwierzytelnieniem; adres jest widoczny w ruchu i łatwy do podrobienia.
- **Duże, płaskie VLAN-y** — tysiące urządzeń w jednej domenie rozgłoszeniowej to dużo broadcastów, większy promień rażenia i łatwiejsze ataki L2.` },

  { type: 'CODE_WRONG', heading: 'Źle: autoryzacja urządzeń na podstawie adresu MAC', body:
`Aplikacja wewnętrzna „zabezpiecza” endpoint administracyjny listą dozwolonych adresów MAC przesyłanych przez klienta w nagłówku. Adres MAC nie dociera nawet do serwera za routerem — a podany w nagłówku można dowolnie sfałszować.`,
    code:
`@PostMapping("/admin/reboot")
ResponseEntity<?> reboot(@RequestHeader("X-Device-Mac") String mac) {
    Set<String> allowed = Set.of("3c:52:82:1a:f0:07", "00:50:56:01:02:03");
    if (!allowed.contains(mac.toLowerCase())) return ResponseEntity.status(403).build();
    service.reboot();                                     // każdy, kto zna (lub podsłucha) MAC, może zrestartować system
    return ResponseEntity.ok().build();
}` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: uwierzytelnienie urządzenia certyfikatem (mTLS) i autoryzacja ról', body:
`Tożsamość urządzenia potwierdza certyfikat klienta (mTLS — rozdział 07), a operację autoryzuje rola. Adres MAC może być co najwyżej dodatkową informacją w logach inwentaryzacyjnych.`,
    code:
`# application.yml — wymagany certyfikat klienta wystawiony przez firmowe CA urządzeń
server:
  ssl:
    client-auth: need
    bundle: device-ca

@Configuration
class AdminSecurity {
    @Bean
    SecurityFilterChain admin(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/admin/**")
            .x509(x -> x.subjectPrincipalRegex("CN=(.*?)(?:,|$)"))    // tożsamość z certyfikatu
            .authorizeHttpRequests(a -> a.anyRequest().hasRole("DEVICE_ADMIN"))
            .build();
    }
}
// Dodatkowo: logowanie CN certyfikatu i adresu IP (zdarzenie bezpieczeństwa), a w sieci — 802.1X z EAP-TLS.` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy adres MAC jest przydatny, a kiedy nie wystarcza', body:
`- **Przydatny**: diagnoza L2 (ARP, tablice przełączników), inwentaryzacja urządzeń, korelacja w incydencie (DHCP → MAC → port), identyfikacja producenta (OUI), rezerwacje DHCP, statyczne wpisy dla urządzeń infrastruktury.
- **Pomocniczy**: MAB (MAC Authentication Bypass) dla urządzeń bez obsługi 802.1X (drukarki, telefony VoIP) — tylko z dodatkowymi zabezpieczeniami (osobny VLAN, ograniczone uprawnienia, profilowanie).
- **Niewystarczający**: uwierzytelnianie użytkowników i urządzeń, autoryzacja w aplikacjach, „licencjonowanie” w środowiskach wirtualnych, śledzenie urządzeń z losowymi MAC.
- **Port security** — stosuj na portach dostępowych (limit adresów, akcja *restrict* z alertem lub *shutdown*), by ograniczyć MAC flooding i nieautoryzowane przełączniki/huby.
- **802.1X z certyfikatami** — właściwa kontrola dostępu do sieci (rozdział 13 i 20).` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Adres MAC: 48 bitów; OUI (24 bity) identyfikuje producenta; bit I/G — unicast/grupowy; bit U/L — globalny/lokalnie administrowany.
- Przełącznik uczy się z adresu **źródłowego**, przekazuje po **docelowym**; nieznany unicast, broadcast i multicast (bez snoopingu) — flooding.
- Tablica MAC ma wpisy z czasem wygaśnięcia (typowo 300 s) i jest per VLAN.
- Hub: 1 domena kolizyjna; przełącznik: domena kolizyjna na port, rozgłoszeniowa na VLAN; router: osobna domena rozgłoszeniowa na interfejs.
- Bridge Linuksa (i Docker) działa jak przełącznik: \`bridge fdb show\`.
- MAC to ważny ślad w incydencie (DHCP → MAC → port), ale słaby identyfikator: spoofing, losowe MAC, MAC flooding — obrona: port security, 802.1X, monitoring „MAC flapping”.
- Następna lekcja: **ARP i IPv6 Neighbor Discovery** — jak host zamienia adres IP na MAC.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const macs = ['3c:52:82:1a:f0:07', '02:42:ac:11:00:02', '33:33:00:00:00:fb', '01:00:5e:00:00:fb', 'ff:ff:ff:ff:ff:ff', 'da:a1:19:5e:22:01', '00:50:56:9a:bc:01', '01:80:c2:00:00:00'];
for (const m of macs) {
  ex(`Określ typ adresu ${m}: unicast/multicast/broadcast oraz globalny/lokalnie administrowany. Rozpisz pierwszy bajt bitowo.`, 'Najmłodszy bit pierwszego bajtu = I/G, drugi najmłodszy = U/L.', `Pierwszy bajt 0x${m.slice(0, 2)} = ${bin8(first(m))}: I/G = ${first(m) & 1}, U/L = ${(first(m) >> 1) & 1} → ${kind(m)}.${m.startsWith('02:42') ? ' (Docker)' : m.startsWith('33:33') ? ' (multicast IPv6)' : m.startsWith('01:00:5e') ? ' (multicast IPv4)' : m.startsWith('01:80:c2') ? ' (protokoły L2, np. STP)' : ''}`);
}
const domainQ = [
  ['Jeden przełącznik 24-portowy z 20 hostami, bez VLAN-ów.', '20 kolizyjnych (zajęte porty), 1 rozgłoszeniowa.'],
  ['Hub 8-portowy z 6 hostami.', '1 kolizyjna, 1 rozgłoszeniowa.'],
  ['Router z 3 interfejsami; do każdego podłączony przełącznik z 5 hostami.', 'Kolizyjne: na każdym przełączniku 6 (5 hostów + uplink) → 18; rozgłoszeniowe: 3.'],
  ['Przełącznik z VLAN 10 (8 hostów) i VLAN 20 (8 hostów), router-on-a-stick na jednym porcie trunk.', 'Kolizyjne: 17 (16 hostów + port do routera); rozgłoszeniowe: 2 (po jednej na VLAN).'],
];
for (const [t, a] of domainQ) ex(`Policz domeny kolizyjne i rozgłoszeniowe: ${t}`, 'Przełącznik dzieli kolizyjne na port, router i VLAN — rozgłoszeniowe.', a);
const actions = [
  ['Tablica pusta; A (p1) wysyła do B.', 'Uczy się A → p1; B nieznany → flooding na wszystkie porty VLAN-u poza p1.'],
  ['Tablica: A → p1, B → p2; A wysyła do B.', 'Odświeża A → p1; forward tylko na p2.'],
  ['Tablica: A → p1, B → p1 (oba za hubem na p1); A wysyła do B.', 'Filter — adresat jest za portem źródłowym, ramka nie jest wysyłana dalej.'],
  ['Tablica: A → p1; A wysyła broadcast ARP.', 'Flooding na wszystkie porty VLAN-u poza p1.'],
  ['Tablica: A → p1, B → p2; nagle ramka ze źródłem B przychodzi na p3.', 'Aktualizacja wpisu B → p3 (urządzenie przeniesione lub spoofing); częste przeskoki = MAC flapping w logach — sygnał pętli L2 lub podszywania się.'],
];
for (const [t, a] of actions) ex(`Co zrobi przełącznik? ${t}`, 'Najpierw uczenie (źródło), potem decyzja (cel).', a);
ex('Wykonaj Guided Lab z bridge’em Linuksa i zapisz wynik bridge fdb show przed pingiem, po pingu ns1 → ns2 i po upływie ageing_time. Wyjaśnij różnice.', 'Pomijaj wpisy permanent (własne adresy portów).', 'Przed: brak dynamicznych wpisów. Po pingu: aa:bb:cc:00:00:01 dev veth-ns1-br, aa:bb:cc:00:00:02 dev veth-ns2-br (master br0). Po ageing_time bez ruchu: wpisy znikają. ns3 nie pojawia się, dopóki nie wyśle żadnej ramki.');
ex('W labie z bridge’em pokaż flooding: uruchom tcpdump w ns3 i wyślij ramki z ns1 do ns2 przy pustej i przy nauczonej tablicy. Co widzi ns3 w obu przypadkach?', 'Ustaw ageing_time na 1 s lub wyczyść fdb przed testem.', 'Pusta tablica: ns3 widzi zapytanie ARP (broadcast) i pierwsze ramki unicast do nieznanego jeszcze MAC (flooding). Nauczona tablica: ns3 widzi tylko broadcasty; ramki ns1 ↔ ns2 idą wyłącznie przez ich porty.');
ex('Napisz w Javie metodę, która z logu DHCP (linie „DHCPACK on 10.0.0.15 to 3c:52:82:1a:f0:07 (laptop-anna) via eth0”) buduje mapę IP → MAC i zwraca, które urządzenie miało dany adres IP w podanym czasie.', 'Parsuj regexem; przechowuj historię z czasem, bo adres IP może zmieniać właściciela.', 'Regex: "(\\\\S+ \\\\S+) .*DHCPACK on (\\\\S+) to ([0-9a-f:]{17}) \\\\((.*?)\\\\)". Struktura: Map<String ip, NavigableMap<Instant, String mac>>; zapytanie: floorEntry(czas) dla IP. Uwaga na losowe MAC i zmiany nazw hostów; w produkcji te dane dostarcza SIEM.');
ex('Na przełączniku (lub w opisie) skonfiguruj port security: maksymalnie 2 adresy MAC na porcie dostępowym, akcja restrict z logowaniem. Wyjaśnij różnice między akcjami protect, restrict i shutdown.', 'Cisco: switchport port-security maximum 2, violation restrict.', 'interface Gi1/0/3\n switchport mode access\n switchport port-security\n switchport port-security maximum 2\n switchport port-security violation restrict\n switchport port-security mac-address sticky\nprotect — odrzuca ramki nadmiarowych adresów bez logu; restrict — odrzuca i loguje/zwiększa licznik (alert dla SOC); shutdown — wyłącza port (err-disabled), najbezpieczniejsze, ale wymaga interwencji.');
ex('Analityk dostał alert: „%SW_MATM-4-MACFLAP_NOTIF: Host 3c52.821a.f007 in vlan 10 is flapping between port Gi1/0/3 and port Gi1/0/9”. Jakie są możliwe przyczyny i jak je odróżnisz?', 'Pętla L2, podszywanie się, urządzenie z dwoma kartami.', 'Możliwe: pętla L2 (STP nieskonfigurowany lub zablokowany — wtedy flapuje wiele adresów i rośnie broadcast), MAC spoofing (jeden konkretny adres na nieoczekiwanym porcie, często wraz z innymi anomaliami), urządzenie z dwiema kartami/bonding źle skonfigurowany, mobilność (stacja dokująca). Diagnoza: ile adresów flapuje, statystyki broadcastów, co jest podłączone do Gi1/0/9, logi STP i NAC.');
ex('Sprawdź w Wiresharku producentów urządzeń w przechwyceniu z sieci domowej (kolumna Resolved/eth.src_resolved). Które adresy są losowe (lokalnie administrowane) i dlaczego nie mają producenta?', 'Losowe MAC mają bit U/L = 1 i nie należą do żadnego OUI.', 'Urządzenia z adresami globalnymi pokazują producenta (np. Apple, Samsung, TP-Link). Telefony i laptopy z włączoną randomizacją MAC (bit U/L = 1, np. da:…, 2a:…) nie mają producenta w bazie OUI — adres jest generowany losowo dla tej sieci.');
ex('Dlaczego allow-lista adresów MAC w sieci Wi-Fi (MAC filtering) nie jest skutecznym zabezpieczeniem? Zaproponuj właściwe rozwiązanie.', 'Adresy MAC są widoczne w ramkach i łatwe do zmiany.', 'Adresy MAC autoryzowanych urządzeń są widoczne w ramkach radiowych (także zaszyfrowanej sieci), a zmiana własnego MAC zajmuje sekundy — napastnik podszywa się pod dozwolone urządzenie. Dodatkowo randomizacja MAC utrudnia zarządzanie listą. Właściwie: WPA3-Enterprise/802.1X z EAP-TLS (certyfikaty), sieć gości z izolacją, NAC z oceną stanu urządzeń.');
ex('Uruchom dwa kontenery w jednej sieci Dockera i porównaj ich adresy MAC (ip link w kontenerach) z wpisami bridge fdb show na hoście. Czy bity U/L są ustawione?', 'docker network inspect pokazuje MacAddress kontenerów.', 'Adresy kontenerów (np. 02:42:… lub losowe z bitem U/L = 1) są lokalnie administrowane. W bridge fdb show widać je na portach vethXXXX podłączonych do bridge’a sieci (br-…). To ten sam mechanizm uczenia się co w fizycznym przełączniku.');
ex('Zmień w labie adres MAC namespace’u ns3 na adres ns2 i wyślij z ns3 ping. Co dzieje się w tablicy bridge’a i jaki wpływ ma to na komunikację ns1 ↔ ns2?', 'Przełącznik aktualizuje wpis według ostatniego źródła.', 'Bridge przenosi wpis MAC ns2 na port ns3 (ostatnia ramka z tym źródłem). Ramki ns1 → „ns2” trafiają do ns3, dopóki ns2 nie wyśle czegoś i wpis nie wróci — komunikacja się rwie, a wpis „skacze” (MAC flapping). To demonstracja MAC spoofingu i powodu, dla którego przełączniki raportują flapping.');
ex('Zaproponuj trzy detekcje (np. dla SIEM) oparte na danych warstwy 2, które pomogą wykryć nieautoryzowane urządzenia lub ataki L2.', 'Źródła: logi przełączników (syslog), port security, DHCP, NAC.', '1) Alert na naruszenia port security (restrict/shutdown) i MAC flapping z logów przełączników. 2) Nowe urządzenie: MAC (lub OUI) niewidziany wcześniej w danym VLAN-ie serwerowym — porównanie z inwentarzem/CMDB. 3) Gwałtowny wzrost liczby adresów MAC na jednym porcie (MAC flooding) lub nagły wzrost broadcastów w VLAN-ie (pętla, atak).');
ex('Wyjaśnij, jak randomizacja adresów MAC w telefonach wpływa na: a) prywatność użytkowników, b) firmową sieć Wi-Fi z rezerwacjami DHCP, c) analizę incydentu.', 'Telefon może mieć inny MAC w każdej sieci, a czasem zmieniać go okresowo.', 'a) Utrudnia śledzenie urządzenia między sieciami (np. w sklepach, na lotniskach). b) Rezerwacje DHCP i filtry oparte na MAC przestają działać; firmowe sieci powinny używać 802.1X i profili MDM (które mogą wyłączać randomizację dla sieci firmowej). c) Atrybucja po MAC jest trudniejsza — trzeba korelować z uwierzytelnieniem (konto w RADIUS/802.1X, logi MDM).');
ex('Oblicz, ile różnych adresów może przydzielić producent z jednego bloku OUI (MA-L) i dlaczego duzi producenci mają wiele OUI.', 'Producent dysponuje dolnymi 24 bitami.', '2^24 = 16 777 216 adresów na jedno OUI. Duzi producenci (karty sieciowe, telefony) wyczerpują to szybko, więc mają wiele bloków OUI; mniejsze firmy mogą kupić mniejsze bloki (MA-M: 2^20, MA-S: 2^12 adresów).');
ex('Opisz, jak przełącznik obsługuje multicast bez i z IGMP snooping, oraz dlaczego ma to znaczenie dla aplikacji Java używających multicastu (np. wykrywanie członków klastra).', 'Multicast to adres grupowy (bit I/G = 1).', 'Bez IGMP snooping przełącznik traktuje multicast jak broadcast — zalewa nim wszystkie porty VLAN-u. Z IGMP snooping śledzi zgłoszenia członkostwa (IGMP join) i wysyła ruch grupy tylko na porty zainteresowanych hostów. Aplikacje (np. Hazelcast z wykrywaniem przez multicast, JGroups) w dużych VLAN-ach generują ruch do wszystkich, a w chmurze multicast często w ogóle nie działa — dlatego w produkcji używa się wykrywania przez TCP/IP lub Kubernetes API.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111407);
const qmacs = ['3c:52:82:1a:f0:07', '02:42:ac:11:00:02', '33:33:00:00:00:01', '01:00:5e:7f:ff:fa', 'ff:ff:ff:ff:ff:ff', 'da:a1:19:5e:22:01', '00:50:56:9a:bc:01', '01:80:c2:00:00:00', '0a:00:27:00:00:00', 'f6:3e:1d:00:12:34', '00:1b:21:aa:bb:cc', '03:00:00:00:00:01', '52:54:00:12:34:56', '01:00:0c:cc:cc:cc'];
const kinds = ['unicast, globalnie unikalny (OUI)', 'unicast, lokalnie administrowany', 'multicast (grupowy), globalnie unikalny (OUI)', 'multicast (grupowy), lokalnie administrowany', 'broadcast, lokalnie administrowany'];
for (const m of qmacs) {
  const k = kind(m);
  add(`Jakiego typu jest adres MAC ${m}?`, k, kinds.filter((x) => x !== k).slice(0, 3), `Pierwszy bajt 0x${m.slice(0, 2)} = ${bin8(first(m))}: I/G = ${first(m) & 1}, U/L = ${(first(m) >> 1) & 1}.`);
}
for (const m of ['3c:52:82:1a:f0:07', '00:50:56:9a:bc:01', '00:1b:21:aa:bb:cc', 'b8:27:eb:12:34:56']) {
  const oui = m.slice(0, 8).toUpperCase();
  add(`Jaka część adresu ${m} to OUI?`, oui, [m.slice(9).toUpperCase(), m.slice(3, 11).toUpperCase(), m.slice(0, 5).toUpperCase()], 'OUI to pierwsze 3 bajty (24 bity) — identyfikator producenta przydzielony przez IEEE.');
}
const acts = [
  ['Ramka do adresu, którego nie ma w tablicy MAC', 'Flooding na wszystkie porty VLAN-u poza źródłowym'],
  ['Ramka do adresu znanego na innym porcie', 'Przekazanie (forward) tylko na port z tablicy'],
  ['Ramka do adresu znanego na tym samym porcie, z którego przyszła', 'Odrzucenie (filter)'],
  ['Ramka broadcast ff:ff:ff:ff:ff:ff', 'Flooding na wszystkie porty VLAN-u poza źródłowym'],
  ['Ramka z nowym adresem źródłowym', 'Zapisanie adresu źródłowego z portem w tablicy (uczenie)'],
];
const actAll = [...new Set(acts.map((x) => x[1]))].concat(['Wysłanie zapytania ARP przez przełącznik']);
for (const [s, a] of acts) add(`Co zrobi przełącznik L2 w sytuacji: ${s}?`, a, actAll.filter((x) => x !== a).slice(0, 3), a + '.');
const doms = [
  ['Hub z 10 hostami', '1', '1'], ['Przełącznik bez VLAN z 10 hostami', '10', '1'], ['Router z 4 interfejsami, każdy z jednym hostem', '4', '4'], ['Przełącznik z 3 VLAN-ami po 5 hostów (bez routera)', '15', '3'],
];
for (const [t, c, bc] of doms) {
  add(`Ile domen kolizyjnych tworzy: ${t}?`, c, [bc === c ? String(+c + 1) : bc, String(+c * 2), '0'].concat([String(+c + 2), String(+c + 10), '11']).filter((x, i, a) => x !== c && a.indexOf(x) === i).slice(0, 3), `Domeny kolizyjne: ${c}.`);
  add(`Ile domen rozgłoszeniowych tworzy: ${t}?`, bc, [c === bc ? String(+bc + 1) : c, String(+bc * 2), '0'].concat([String(+bc + 2), String(+bc + 10), '11']).filter((x, i, a) => x !== bc && a.indexOf(x) === i).slice(0, 3), `Domeny rozgłoszeniowe: ${bc}.`);
}
const facts = [
  ['Z którego adresu przełącznik uczy się położenia urządzeń?', 'Z adresu MAC źródłowego ramki', ['Z adresu MAC docelowego', 'Z adresu IP źródłowego', 'Z numeru portu TCP'], 'Uczenie zawsze po źródle.'],
  ['Ile bitów ma adres MAC?', '48', ['32', '64', '128'], '6 bajtów.'],
  ['Ile bitów ma OUI?', '24', ['12', '32', '48'], 'Pierwsze 3 bajty adresu.'],
  ['Jaki jest typowy domyślny czas wygasania wpisu w tablicy MAC?', '300 sekund', ['3 sekundy', '24 godziny', 'Wpisy nie wygasają'], 'Cisco i bridge Linuksa domyślnie 300 s.'],
  ['Co oznacza ustawiony bit U/L w adresie MAC?', 'Adres lokalnie administrowany (nadany programowo)', ['Adres multicast', 'Adres uszkodzony', 'Adres producenta Cisco'], 'Kontenery, VM, losowe MAC.'],
  ['Co oznacza ustawiony bit I/G w adresie MAC?', 'Adres grupowy (multicast lub broadcast)', ['Adres lokalny', 'Adres IPv6', 'Adres bramy'], 'Najmłodszy bit pierwszego bajtu.'],
  ['Czym jest atak MAC flooding?', 'Zasypaniem przełącznika fałszywymi adresami źródłowymi, by przepełnić tablicę i wymusić zalewanie ruchu', ['Wysłaniem zbyt dużych ramek', 'Zmianą adresu IP', 'Atakiem na DNS'], 'Obrona: port security, 802.1X.'],
  ['Które zabezpieczenie ogranicza liczbę adresów MAC na porcie przełącznika?', 'Port security', ['DHCP snooping', 'HSTS', 'DNSSEC'], 'Akcje: protect, restrict, shutdown.'],
  ['Co sygnalizuje komunikat MAC flapping w logach przełącznika?', 'Ten sam adres MAC pojawia się naprzemiennie na różnych portach — pętla L2 lub podszywanie', ['Wygaśnięcie certyfikatu', 'Zbyt duży ruch DNS', 'Brak zasilania PoE'], 'Wymaga analizy topologii i STP.'],
  ['Dlaczego filtrowanie po MAC w Wi-Fi nie jest uwierzytelnieniem?', 'Adres MAC jest widoczny w ruchu i łatwy do sfałszowania', ['Bo Wi-Fi nie używa MAC', 'Bo MAC jest szyfrowany', 'Bo MAC zmienia się co sekundę'], 'Używaj 802.1X / WPA3-Enterprise.'],
  ['Czy serwer w Internecie widzi adres MAC Twojego laptopa?', 'Nie — adres MAC zmienia się na każdym przeskoku przez router', ['Tak, zawsze', 'Tylko przez HTTPS', 'Tylko przy IPv4'], 'MAC ma znaczenie w obrębie segmentu L2.'],
  ['Które polecenie pokaże tablicę bridge’a Linuksa?', 'bridge fdb show', ['ip route', 'arp -s', 'ss -tlnp'], 'FDB = forwarding database.'],
  ['Które polecenie Cisco pokaże, na którym porcie widziany jest dany MAC?', 'show mac address-table address <mac>', ['show ip route', 'show vlan brief', 'show running-config'], 'Kluczowe przy lokalizacji urządzenia w incydencie.'],
  ['Czym różni się przełączanie cut-through od store-and-forward?', 'Cut-through zaczyna wysyłać po odczytaniu adresu docelowego, store-and-forward czeka na całą ramkę i sprawdza FCS', ['Cut-through szyfruje ramki', 'Store-and-forward działa w L3', 'Nie ma różnicy'], 'Cut-through ma mniejsze opóźnienie, ale przepuszcza uszkodzone ramki.'],
  ['Jakie urządzenie dzieli domeny rozgłoszeniowe?', 'Router (lub podział na VLAN-y)', ['Hub', 'Przełącznik L2 bez VLAN', 'Repeater'], 'Broadcast nie przechodzi przez router.'],
  ['Jakie urządzenie dzieli domeny kolizyjne, ale nie rozgłoszeniowe?', 'Przełącznik L2', ['Hub', 'Router', 'Repeater'], 'Każdy port przełącznika to osobna domena kolizyjna.'],
  ['Jak w incydencie ustalić, które fizyczne urządzenie miało dany adres IP?', 'IP → MAC (logi DHCP/ARP) → port przełącznika (tablica MAC) → gniazdo/użytkownik (802.1X, dokumentacja)', ['Tylko z logu aplikacji', 'Z certyfikatu TLS serwera', 'Z rekordu MX w DNS'], 'Wymaga logów DHCP i dostępu do przełączników.'],
  ['Dlaczego sklonowane maszyny wirtualne z tym samym MAC powodują problemy?', 'Przełącznik i ARP nie odróżnią dwóch urządzeń o tym samym adresie — łączność staje się niestabilna', ['Bo VM nie obsługują MAC', 'Bo hypervisor szyfruje MAC', 'Bo DNS się wyłącza'], 'Hypervisory generują nowe MAC przy klonowaniu, jeśli tak skonfigurowano.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
const ouis = [['02:42', 'Docker (adresy lokalnie administrowane)'], ['33:33', 'multicast IPv6'], ['01:00:5e', 'multicast IPv4'], ['01:80:c2:00:00:00', 'STP (BPDU)']];
const ouiAll = ouis.map((x) => x[1]);
for (const [p, d] of ouis) add(`Z czym kojarzy się adres MAC zaczynający się od ${p}?`, d, ouiAll.filter((x) => x !== d), `${p}… — ${d}.`);
const cisco = (m) => { const h = m.replace(/:/g, ''); return `${h.slice(0, 4)}.${h.slice(4, 8)}.${h.slice(8)}`; };
const dash = (m) => m.toUpperCase().replace(/:/g, '-');
for (const m of ['3c:52:82:1a:f0:07', '00:50:56:9a:bc:01', '02:42:ac:11:00:02', 'b8:27:eb:12:34:56', '00:1b:21:aa:bb:cc', 'da:a1:19:5e:22:01', '52:54:00:12:34:56', '0a:00:27:00:00:00']) {
  const c = cisco(m);
  add(`Jak zapisać adres ${m} w notacji Cisco (kropkowej)?`, c, [dash(m), c.split('.').reverse().join('.'), m.replace(/:/g, '').slice(0, 6) + '.' + m.replace(/:/g, '').slice(6)], 'Notacja Cisco: trzy grupy po 4 cyfry hex oddzielone kropkami.');
}
for (const b of ['00', '01', '02', '03', '3c', '33', 'da', 'f6', 'fe', 'ff']) {
  const n = parseInt(b, 16);
  const ig = n & 1, ul = (n >> 1) & 1;
  const ans = `I/G = ${ig}, U/L = ${ul}`;
  const all = ['I/G = 0, U/L = 0', 'I/G = 1, U/L = 0', 'I/G = 0, U/L = 1', 'I/G = 1, U/L = 1'];
  add(`Jakie wartości mają bity I/G i U/L, gdy pierwszy bajt adresu MAC to 0x${b} (${bin8(n)})?`, ans, all.filter((x) => x !== ans), `I/G to najmłodszy bit (${ig}), U/L drugi najmłodszy (${ul}).`);
}
const states = [
  ['{A→p1, B→p2, C→p3}', 'p1', 'C', 'Forward tylko na p3'], ['{A→p1}', 'p1', 'D', 'Flooding na wszystkie porty VLAN-u poza p1'], ['{A→p1, B→p1}', 'p1', 'B', 'Filter — odrzucenie ramki'],
  ['{A→p1, B→p2}', 'p2', 'A', 'Forward tylko na p1'], ['{}', 'p4', 'ff:ff:ff:ff:ff:ff', 'Flooding na wszystkie porty VLAN-u poza p4'], ['{A→p1, B→p2, C→p3, D→p4}', 'p3', 'D', 'Forward tylko na p4'],
  ['{B→p2}', 'p3', 'A', 'Flooding na wszystkie porty VLAN-u poza p3'], ['{A→p1, D→p4}', 'p4', 'A', 'Forward tylko na p1'],
];
const actOpts = ['Forward tylko na p1', 'Forward tylko na p3', 'Forward tylko na p4', 'Flooding na wszystkie porty VLAN-u poza p1', 'Flooding na wszystkie porty VLAN-u poza p3', 'Flooding na wszystkie porty VLAN-u poza p4', 'Filter — odrzucenie ramki'];
states.forEach(([t, inp, dst, a], i) => add(`Tablica MAC: ${t}. Na port ${inp} przychodzi ramka do ${dst}. Co zrobi przełącznik? (scenariusz ${i + 1})`, a, actOpts.filter((x) => x !== a).slice(i % 4, i % 4 + 3), `Decyzja według tablicy i portu wejściowego: ${a.toLowerCase()}.`));
const topo = [[2, 6, 1], [1, 12, 4], [3, 5, 2], [4, 3, 1], [2, 10, 3]];
for (const [routers, hostsPerSw, vlans] of topo) {
  const coll = routers * (hostsPerSw + 1);
  const bc = routers * vlans;
  add(`Router z ${routers} interfejsami; do każdego podłączony przełącznik z ${hostsPerSw} hostami podzielonymi na ${vlans} VLAN(-y) (trunk do routera). Ile jest domen rozgłoszeniowych?`, String(bc), [String(routers), String(coll), String(bc + routers)].filter((x, i2, a) => x !== String(bc) && a.indexOf(x) === i2).concat([String(bc + 1), String(bc * 2 + 1)]).slice(0, 3), `Każdy VLAN za każdym interfejsem to osobna domena: ${routers} × ${vlans} = ${bc}.`);
  add(`Ta sama topologia (router z ${routers} interfejsami, przełączniki z ${hostsPerSw} hostami i uplinkiem). Ile jest domen kolizyjnych na przełącznikach?`, String(coll), [String(routers * hostsPerSw), String(routers), String(coll + routers)].filter((x, i2, a) => x !== String(coll) && a.indexOf(x) === i2).concat([String(coll - 1), String(coll + 2)]).slice(0, 3), `Każdy zajęty port przełącznika (hosty + uplink) to domena: ${routers} × (${hostsPerSw} + 1) = ${coll}.`);
}
const more = [
  ['Co oznacza wpis STATIC w tablicy MAC przełącznika Cisco?', 'Wpis skonfigurowany ręcznie (lub sticky), który nie wygasa', ['Wpis o złym adresie', 'Wpis dla multicastu', 'Wpis tymczasowy na 5 s'], 'Dynamiczne wpisy wygasają po czasie aging.'],
  ['Co robi opcja port-security mac-address sticky?', 'Zapamiętuje dynamicznie nauczone adresy jako stałe w konfiguracji portu', ['Wyłącza port', 'Losuje adresy MAC', 'Szyfruje ramki'], 'Ułatwia wdrożenie port security.'],
  ['Dlaczego duży, płaski VLAN zwiększa ryzyko?', 'Większa domena rozgłoszeniowa: więcej broadcastów i szerszy zasięg ataków L2 oraz ruchu bocznego', ['Bo VLAN-y są szyfrowane', 'Bo wymaga IPv6', 'Bo zmniejsza MTU'], 'Segmentacja ogranicza promień rażenia.'],
  ['Które urządzenie w biurze najlepiej przechwyci ruch wielu hostów do analizy bezpieczeństwa?', 'Sensor podłączony do portu SPAN lub TAP-a', ['Laptop w trybie promiscuous na zwykłym porcie', 'Drukarka sieciowa', 'Router domowy'], 'Przełącznik nie wyśle cudzego ruchu unicast na zwykły port.'],
  ['Co jest skuteczniejsze przeciw podszywaniu się pod urządzenie z wyjątkiem w NAC (MAB)?', '802.1X z certyfikatami urządzeń', ['Dłuższa lista MAC', 'Wyłączenie STP', 'Zmiana VLAN na 1'], 'MAB opiera się na łatwym do podrobienia MAC.'],
  ['Na której warstwie OSI działa przełącznik bez funkcji routingu?', 'Warstwie 2 (łącza danych)', ['Warstwie 1', 'Warstwie 3', 'Warstwie 4'], 'Decyzje po adresach MAC i VLAN.'],
  ['Czym jest FDB w kontekście bridge’a Linuksa?', 'Forwarding database — tablicą adresów MAC i portów', ['Bazą danych DNS', 'Plikiem konfiguracyjnym Dockera', 'Bazą certyfikatów'], 'Odpowiednik tablicy MAC/CAM przełącznika.'],
  ['Co się stanie z wpisem w tablicy MAC, gdy urządzenie długo nie wysyła ramek?', 'Wpis wygaśnie po czasie aging, a ruch do urządzenia będzie najpierw zalewany', ['Wpis zostanie na zawsze', 'Przełącznik wyśle zapytanie ARP', 'Port zostanie wyłączony'], 'Kolejna ramka od urządzenia odtworzy wpis.'],
  ['W którym logu najszybciej znajdziesz powiązanie adresu IP z adresem MAC w sieci firmowej?', 'W logach serwera DHCP', ['W logach aplikacji Java', 'W rejestrze certyfikatów', 'W logach DNS serwera autorytatywnego'], 'DHCP przydziela adres IP konkretnemu MAC (klientowi).'],
];
for (const [q, a, w, e] of more) add(q, a, w, e);
add('Dlaczego kontenery i maszyny wirtualne mają zwykle adresy MAC z ustawionym bitem U/L?', 'Bo adresy są generowane programowo, a nie przydzielane przez producenta karty', ['Bo są multicastowe', 'Bo pochodzą z OUI Dockera nadanego przez IEEE', 'Bo działają w IPv6'], 'Lokalnie administrowane adresy nie kolidują z pulami producentów.');
add('Co zrobi przełącznik z IGMP snooping z ruchem multicast grupy, do której zapisały się dwa hosty?', 'Wyśle go tylko na porty tych dwóch hostów (i routera multicast)', ['Zaleje nim wszystkie porty', 'Odrzuci go', 'Zamieni go na broadcast'], 'Bez snoopingu multicast jest zalewany jak broadcast.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
