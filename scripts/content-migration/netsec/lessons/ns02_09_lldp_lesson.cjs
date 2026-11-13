// Generuje lekcje _ns_02_ethernet_lan/09_LldpCdpAndL2Discovery.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_09_lldp_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/09_LldpCdpAndL2Discovery.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const hdr = (type, len) => (type << 9) | len;
const h4 = (n) => '0x' + n.toString(16).toUpperCase().padStart(4, '0');
const dec = (h) => ({ type: h >> 9, len: h & 0x1ff });
const TLV = [
  [0, 'End of LLDPDU', 'koniec listy TLV'],
  [1, 'Chassis ID', 'identyfikator urządzenia (zwykle MAC)'],
  [2, 'Port ID', 'identyfikator portu nadawcy'],
  [3, 'Time To Live', 'ile sekund przechowywać informację'],
  [4, 'Port Description', 'opis portu (description)'],
  [5, 'System Name', 'nazwa hosta urządzenia'],
  [6, 'System Description', 'system i wersja oprogramowania'],
  [7, 'System Capabilities', 'rola: bridge, router, telefon, stacja, WLAN AP'],
  [8, 'Management Address', 'adres IP do zarządzania'],
  [127, 'Organizationally Specific', 'rozszerzenia z OUI (802.1, 802.3, LLDP-MED)'],
];
const tlvName = Object.fromEntries(TLV.map(([t, n]) => [t, n]));

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Kto jest po drugiej stronie tego kabla?', ...img('ns02-09-exchange.svg', 'Przełącznik SW-WAW-ACC-01 port Gi1/0/12 i serwer srv-app-07 port eno1 wymieniają co 30 s komunikaty LLDP na adres 01:80:c2:00:00:0e z TTL 120 s. Serwer widzi nazwę przełącznika, port, adres zarządzania, VLAN i system; przełącznik widzi nazwę serwera, port eno1, rolę stacji i opis systemu.'),
    body:
`Każdy, kto pracował przy serwerowni, zna to pytanie: „do którego portu którego przełącznika jest wpięty ten serwer?”. Dokumentacja jest nieaktualna, etykiety odpadły, kable krzyżują się w szafie. Odpowiedź od lat daje **protokół odkrywania sąsiadów warstwy 2**:
- **LLDP (Link Layer Discovery Protocol, IEEE 802.1AB)** — standard wielu producentów; działa na przełącznikach, routerach, serwerach Linux (\`lldpd\`), hiperwizorach (VMware, Proxmox), telefonach IP i punktach dostępowych,
- **CDP (Cisco Discovery Protocol)** — starszy, własny protokół Cisco, domyślnie włączony na urządzeniach Cisco.

Urządzenie co kilkadziesiąt sekund wysyła na każdym porcie krótką ramkę: „jestem SW-WAW-ACC-01, to mój port Gi1/0/12, mój adres zarządzania to 10.99.0.11, jestem przełącznikiem, działam na IOS XE 17.12”. Sąsiad zapisuje to w **tabeli sąsiedztwa**, ale **nie przekazuje dalej** — LLDP widzi tylko bezpośrednich sąsiadów.

Dla developera i DevOpsa to szybka odpowiedź na „gdzie jest mój serwer”, sprawdzenie, czy oba łącza bondu idą do **różnych** przełączników, czy port ma właściwy VLAN, oraz źródło danych do automatycznych diagramów i CMDB. Dla blue teamu to **miecz obosieczny**: te same informacje (model, wersja oprogramowania, adres zarządzania, VLAN-y) za darmo dostaje każdy, kto podłączy laptopa do gniazdka — dlatego LLDP/CDP wyłącza się na portach brzegowych i niezaufanych.

W tej lekcji: format ramki i **TLV** (policzysz nagłówki), polecenia na przełącznikach i w Linuksie, **LLDP-MED** dla telefonów VoIP, budowanie diagramu z danych sąsiedztwa oraz zasady bezpieczeństwa. To cele CCNA 200-301 (D2: konfiguracja i weryfikacja CDP i LLDP) i Network+ (narzędzia dokumentowania sieci).` },

  { type: 'DEFINITION', heading: 'Słownik protokołów odkrywania', body:
`- **LLDP (802.1AB)** — protokół jednokierunkowych ogłoszeń: każdy port wysyła **LLDPDU** na multicast \`01:80:c2:00:00:0e\` (EtherType **0x88CC**), domyślnie co **30 s** (msgTxInterval), z **TTL = interval × multiplier** (domyślnie 30 × 4 = **120 s**). Nie ma odpowiedzi ani potwierdzeń.
- **LLDPDU** — ciąg struktur **TLV** (Type–Length–Value); nagłówek TLV: 7 bitów typu + 9 bitów długości.
- **TLV obowiązkowe**: Chassis ID (1), Port ID (2), TTL (3), End (0). **Opcjonalne**: Port Description (4), System Name (5), System Description (6), System Capabilities (7), Management Address (8), Organizationally Specific (127).
- **TLV 127 — rozszerzenia**: IEEE 802.1 (Port VLAN ID, nazwy VLAN-ów, agregacja), IEEE 802.3 (prędkość/duplex, PoE, MTU), **LLDP-MED** (TIA-1057: polityka sieci dla głosu — VLAN i priorytet, lokalizacja dla numerów alarmowych, inwentarz).
- **CDP** — protokół Cisco: multicast \`01:00:0c:cc:cc:cc\` (enkapsulacja SNAP), ogłoszenia co **60 s**, **holdtime 180 s**; przekazuje m.in. Device ID, adres IP, platformę, wersję IOS, natywny VLAN, duplex, VTP domain.
- **Tabela sąsiedztwa** — lista odebranych ogłoszeń z czasem wygaśnięcia; sąsiad znika po upływie TTL/holdtime bez nowego ogłoszenia.
- **lldpd / lldpad** — demony LLDP w Linuksie; \`lldpctl\` / \`lldpcli\` do odczytu. **systemd-networkd** potrafi odbierać i wysyłać LLDP (\`LLDP=yes\`, \`EmitLLDP=yes\`).
- **Inne protokoły odkrywania**: EDP (Extreme), FDP (Foundry/Brocade), MNDP (MikroTik), **NDP** (Nortel) — oraz w IPv6 zupełnie inny „Neighbor Discovery” (lekcja 03, nie mylić).` },

  { type: 'ANALOGY', heading: 'Wizytówka przyklejona po obu stronach drzwi', body:
`Wyobraź sobie biurowiec, w którym drzwi między pokojami nie mają tabliczek. Każdy pracownik co 30 sekund przykleja na swoich drzwiach od strony korytarza **wizytówkę**: „Anna Kowalska, księgowość, pokój 12, telefon 1043, pracuję na Windows 11”. Na wizytówce jest **data ważności** (TTL 120 s) — jeśli nikt nie przyklei nowej, stara jest zrywana.
- Sąsiad z naprzeciwka **czyta** wizytówkę i wie, kto jest po drugiej stronie — ale **nie przekazuje** jej dalej. Żeby poznać cały budynek, administrator musi obejść wszystkie pokoje i złożyć mapę z wizytówek (zbieranie sąsiedztwa ze wszystkich urządzeń).
- **LLDP-MED** to dodatkowa naklejka dla telefonów: „telefony — proszę używać linii 30 i priorytetu 5” (voice VLAN i QoS).
- **Problem bezpieczeństwa**: wizytówkę czyta każdy, kto przejdzie korytarzem — także **gość** czy **intruz**. Dowiaduje się, kto gdzie siedzi, jakim systemem pracuje (wersja oprogramowania → znane podatności) i jaki ma numer telefonu (adres zarządzania). Dlatego w korytarzu dla gości (porty dostępowe w strefie gości, łącze do Internetu) wizytówek się nie wiesza.
- **Fałszywa wizytówka** — nic nie stoi na przeszkodzie, by ktoś przykleił wizytówkę z nieprawdziwymi danymi; LLDP nie ma uwierzytelniania. Danych z LLDP używaj do dokumentacji i diagnozy, ale nie do decyzji bezpieczeństwa bez dodatkowej weryfikacji.` },

  { type: 'VISUAL_EXAMPLE', heading: 'TLV: policz nagłówki jak parser', ...img('ns02-09-tlv.svg', 'Ramka LLDP: MAC docelowy 01:80:c2:00:00:0e, MAC źródłowy, EtherType 0x88CC, obowiązkowe TLV Chassis ID, Port ID i TTL, opcjonalne TLV 4–127 i End. Nagłówek TLV: 7 bitów typu i 9 bitów długości; przykład: System Name 13 bajtów → 0x0A0D, TTL → 0x0602 z wartością 0x0078.'),
    body:
`Nagłówek TLV to 16 bitów: górne 7 to **typ**, dolne 9 to **długość wartości** w bajtach. Kodowanie: \`(typ << 9) | długość\`; dekodowanie: \`typ = h >> 9\`, \`długość = h & 0x1FF\`. Format TLV spotkasz w dziesiątkach protokołów (RADIUS, BGP, TLS extensions, ASN.1/DER w uproszczeniu), więc warto go mieć „w palcach”.`,
    code:
[[1, 7, 'Chassis ID: podtyp 4 (MAC) + 6 B adresu'], [2, 9, 'Port ID: podtyp 5 (nazwa) + „Gi1/0/12”'], [3, 2, 'TTL: 0x0078 = 120 s'], [4, 10, 'Port Description: „srv-app-07”'], [5, 13, 'System Name: „SW-WAW-ACC-01”'], [7, 4, 'System Capabilities: 2 B dostępne + 2 B włączone'], [8, 12, 'Management Address: IPv4 10.99.0.11'], [127, 6, 'Org. specific 802.1: OUI 00-80-C2, podtyp 1 (Port VLAN ID), VID 20'], [0, 0, 'End of LLDPDU']].map(([t, l, d]) => `typ ${String(t).padStart(3)} dł. ${String(l).padStart(2)} → ${h4(hdr(t, l))}   ${d}`).join('\n') + `

Dekodowanie z surowych bajtów (tcpdump -xx):
  02 07 04 00 1a 2b 3c 4d 5e  → 0x0207: typ 1 (Chassis ID), 7 B: podtyp 04 (MAC) + 00:1a:2b:3c:4d:5e
  06 02 00 78                 → 0x0602: typ 3 (TTL), 2 B: 0x0078 = 120 s
Maksymalna długość wartości: 2^9 − 1 = 511 bajtów.` },

  { type: 'CODE_BASIC', heading: 'Polecenia: Cisco, Linux, Windows, VMware', body:
`Na przełącznikach Cisco CDP jest zwykle włączony domyślnie, LLDP — nie zawsze. W Linuksie potrzebny jest demon \`lldpd\` (lub systemd-networkd).`,
    code:
`! Cisco IOS / IOS XE
lldp run                                   ! globalnie włącz LLDP
interface Gi1/0/1
 lldp transmit / lldp receive              ! per port (no lldp transmit — tylko odbiór)
 no cdp enable                             ! wyłącz CDP na porcie
show lldp neighbors                        ! skrót: Device ID, Local Intf, Hold-time, Capability, Port ID
show lldp neighbors detail                 ! pełne TLV: system, adres zarządzania, VLAN, PoE
show cdp neighbors detail                  ! platforma, wersja IOS, IP, native VLAN, duplex
show lldp traffic                          ! liczniki wysłanych/odebranych/błędnych ramek
lldp timer 30 / lldp holdtime 120          ! interwał i TTL

# Linux (Debian/Ubuntu: apt install lldpd)
systemctl enable --now lldpd
lldpcli show neighbors                     # sąsiedzi na każdym interfejsie
lldpcli show neighbors details
lldpcli show neighbors -f json             # do skryptów / CMDB
lldpcli configure system hostname srv-app-07
lldpcli configure lldp portidsubtype ifname
networkctl lldp                            # alternatywnie systemd-networkd (LLDP=yes)

# Podgląd surowych ramek
tcpdump -i eno1 -nn -v -s 1500 'ether proto 0x88cc'
tcpdump -i eno1 -nn -v 'ether host 01:00:0c:cc:cc:cc'      # CDP

# Windows: brak wbudowanego klienta CLI; Windows Server ma funkcję „Data Center Bridging” (LLDP agent),
# w praktyce: Wireshark z filtrem lldp || cdp albo narzędzia producenta karty.
# VMware vSphere: Distributed Switch → Discovery protocol: LLDP/CDP, tryb Listen/Advertise/Both.` },

  { type: 'STEP_BY_STEP', heading: 'Zadanie z planu: zbierz sąsiedztwo i narysuj diagram fizyczny', ...img('ns02-09-diagram.svg', 'Po lewej siedem par sąsiedztwa z trzech przełączników; po prawej narysowany z nich diagram: CORE1 połączony z ACC1 i ACC2, łącze ACC1–ACC2, serwer srv-db-01 z dwoma łączami do ACC1, punkt dostępowy i telefon przy ACC2. Wniosek: oba łącza serwera do jednego przełącznika — brak redundancji przełącznika.'),
    body:
`Laboratorium — w Containerlabie z lekcji 06 (cEOS/SR Linux mają LLDP domyślnie włączony; dla kontenerów Linux doinstaluj \`lldpd\`):
1. **Włącz LLDP** na wszystkich urządzeniach labu (\`lldp run\`, \`systemctl start lldpd\`). Odczekaj 30–60 s.
2. **Zbierz tabele sąsiedztwa** z każdego urządzenia: \`show lldp neighbors\` (przełączniki) i \`lldpcli show neighbors -f json\` (hosty). Zapisz je do plików.
3. **Zbuduj listę krawędzi**: każda linia to para „urządzenie:port ↔ urządzenie:port”. Usuń duplikaty — każde łącze zobaczysz z obu stron.
4. **Narysuj diagram** — ręcznie lub automatycznie: skrypt (Python/Java) zamienia krawędzie na plik **Graphviz DOT** lub **Mermaid**; Containerlab sam potrafi pokazać topologię (\`containerlab graph\`) — porównaj.
5. **Zweryfikuj z zamierzonym projektem**: czy każde łącze jest tam, gdzie powinno? Czy oba porty bondu serwera idą do **różnych** przełączników? Czy port ma właściwy VLAN (TLV 802.1 Port VLAN ID)?
6. **Odczytaj ryzyka**: które urządzenia ogłaszają wersję oprogramowania i adres zarządzania na portach, gdzie nie powinny (np. do hostów użytkowników)?
7. **Wyłącz LLDP** na porcie do „hosta gościa” i sprawdź, że po upływie TTL (120 s) przełącznik znika z jego tabeli sąsiedztwa.
8. **Wynik**: diagram (PNG/SVG) + tabela połączeń w repozytorium — tak wygląda „dokumentacja jako kod” sieci.` },

  { type: 'NOTE', heading: 'LLDP vs CDP w liczbach', body:
`Porównanie, które przydaje się na egzaminie i przy migracji sieci wielodostawczej:`,
    code:
`cecha                    LLDP (802.1AB)                 CDP (Cisco)
standard                 IEEE, wielu producentów        własnościowy Cisco
adres docelowy           01:80:c2:00:00:0e              01:00:0c:cc:cc:cc
enkapsulacja             EtherType 0x88CC               LLC/SNAP (PID 0x2000)
interwał                 30 s                           60 s
czas życia               120 s (TTL = 30 × 4)           180 s (holdtime)
domyślnie na Cisco       często wyłączony (lldp run)    włączony
przekazywanie dalej      nie (tylko sąsiad)             nie (tylko sąsiad)
rozszerzenia             TLV 127: 802.1, 802.3, MED     natywne pola: VTP, native VLAN, PoE
VoIP                     LLDP-MED (network policy)      CDP voice VLAN (telefony Cisco)
Linux                    lldpd, systemd-networkd        lldpd (odbiór i wysyłanie CDP)
uwierzytelnianie         brak                           brak

TTL = msgTxInterval × msgTxHold: 30 × 4 = 120 s; 10 × 4 = 40 s; 60 × 3 = 180 s.` },

  { type: 'CODE_PRACTICAL', heading: 'Automatyzacja: z JSON lldpcli do diagramu w Javie', body:
`Prosty program, który z wyjścia \`lldpcli show neighbors -f json\` zebranego z wielu hostów (lub z odpowiedzi API przełączników) buduje listę unikatowych łączy i plik Graphviz DOT. Ten sam wzorzec stosują narzędzia typu NetBox (import), Nautobot, LibreNMS i Netdisco.`,
    code:
`import com.fasterxml.jackson.databind.*;
import java.nio.file.*;
import java.util.*;

public class LldpToDot {
    record End(String device, String port) {}
    record Link(End a, End b) {
        Link normalized() {                       // A–B i B–A to to samo łącze
            return (a.device + a.port).compareTo(b.device + b.port) <= 0 ? this : new Link(b, a);
        }
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        Set<Link> links = new TreeSet<>(Comparator.comparing((Link l) -> l.a.device + l.a.port + l.b.device + l.b.port));
        for (String file : args) {                // pliki: <hostname>.json
            String host = Path.of(file).getFileName().toString().replace(".json", "");
            JsonNode ifaces = om.readTree(Path.of(file).toFile()).path("lldp").path("interface");
            for (JsonNode i : ifaces) {           // format lldpcli: {"eno1": {"chassis": {...}, "port": {...}}}
                i.fields().forEachRemaining(e -> {
                    String nbr = e.getValue().path("chassis").fieldNames().next();
                    String nbrPort = e.getValue().path("port").path("id").path("value").asText();
                    links.add(new Link(new End(host, e.getKey()), new End(nbr, nbrPort)).normalized());
                });
            }
        }
        StringBuilder dot = new StringBuilder("graph lan {\\n");
        links.forEach(l -> dot.append("  \\"%s\\" -- \\"%s\\" [label=\\"%s ↔ %s\\"];\\n"
            .formatted(l.a.device, l.b.device, l.a.port, l.b.port)));
        Files.writeString(Path.of("lan.dot"), dot.append("}\\n"));   // dot -Tsvg lan.dot > lan.svg
    }
}` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', ...img('ns02-09-security.svg', 'Dwie kolumny: LLDP/CDP włączone na łączach między przełącznikami, portach do serwerów, hiperwizorów, telefonów VoIP, punktów dostępowych i w sieci zarządzania; wyłączone na interfejsach do Internetu, w strefie gości, salach konferencyjnych, DMZ i nieużywanych portach. Ramka ujawnia nazwę, model, wersję oprogramowania, adres zarządzania i VLAN-y.'),
    body:
`**Developer / DevOps / SRE:**
- **„Gdzie jest mój serwer?”** — \`lldpcli show neighbors\` na serwerze od razu podaje przełącznik i port; bez szukania kabli i bez czekania na zespół sieci.
- **Weryfikacja redundancji** — oba porty bondu (lekcja 08) muszą prowadzić do **dwóch różnych** przełączników pary MLAG; LLDP to pokazuje w sekundę.
- **Weryfikacja VLAN-u i prędkości** — TLV 802.1 (Port VLAN ID) i 802.3 (MAC/PHY) pozwalają wykryć „serwer w złym VLAN-ie” czy „port wynegocjował 100M”.
- **Automatyzacja i CMDB** — NetBox/Nautobot, Ansible (\`lldp_neighbors\`), Netdisco i LibreNMS budują z LLDP inwentarz i diagramy; provisioning serwerów bare metal (np. MAAS, Ironic/OpenStack, Metal3 w Kubernetesie) używa LLDP do automatycznego ustalenia, do którego portu podłączono nowy serwer.
- **Hiperwizory** — vSphere Distributed Switch i Proxmox pokazują sąsiadów LLDP/CDP fizycznych kart — szybka diagnoza „VM-ki na tym hoście nie mają sieci”.

**Blue team:**
- **Rozpoznanie (reconnaissance)** — przechwycona ramka LLDP/CDP ujawnia model i wersję systemu przełącznika (porównanie z listą podatności), adres zarządzania (cel ataku), VLAN-y (w tym voice VLAN), nazwy hostów. ATT&CK: rozpoznanie sieci, T1590/T1016 (zbieranie informacji o konfiguracji sieci). Obrona: wyłączanie na portach brzegowych i niezaufanych.
- **Fałszywe ogłoszenia** — LLDP/CDP nie ma uwierzytelniania; podszycie się pod telefon IP może dać dostęp do voice VLAN (telefon „dostaje” VLAN przez CDP/LLDP-MED). Obrona: 802.1X/MAB dla telefonów, ograniczenie voice VLAN do potrzebnego ruchu.
- **DoS na tabelę sąsiedztwa** — zalewanie tysiącami fałszywych ogłoszeń CDP zużywało pamięć/CPU starszych urządzeń; aktualizacje i wyłączanie CDP tam, gdzie zbędny.
- **Detekcja** — nieoczekiwany nowy sąsiad LLDP na porcie (np. przełącznik zamiast komputera), zmiana sąsiada na łączu między przełącznikami, ramki CDP/LLDP z portów użytkowników w sieci, gdzie LLDP jest wyłączony — to sygnały do SIEM/NAC.` },

  { type: 'PITFALL', heading: 'Częste nieporozumienia i błędy', body:
`- **„LLDP pokaże całą sieć”** — nie; widzisz tylko **bezpośrednich** sąsiadów. Pełna mapa wymaga zebrania danych ze wszystkich urządzeń.
- **Mylenie LLDP z IPv6 Neighbor Discovery** — to zupełnie różne protokoły (L2 ogłoszenia vs ICMPv6 rozwiązywanie adresów — lekcja 03).
- **CDP włączony domyślnie wszędzie** — na routerze brzegowym do operatora czy na porcie w sali konferencyjnej rozgłasza wersję IOS i adres zarządzania obcym. Wyłącz na interfejsach zewnętrznych i dostępowych tam, gdzie zbędny.
- **Wyłączenie LLDP/CDP wszędzie „dla bezpieczeństwa”** — psuje telefony VoIP (voice VLAN, PoE negotiation), automatyzację i diagnozę. Wyłączaj selektywnie.
- **Ufanie danym z LLDP** — informacje są nieuwierzytelnione; świetne do dokumentacji, nie jako podstawa decyzji dostępowych.
- **Kontenery i maszyny wirtualne** — sąsiad LLDP serwera to przełącznik fizyczny; VM-ki za vSwitchem zwykle go nie widzą (vSwitch nie przekazuje ramek 01:80:c2:00:00:0e — to adres „nie przekazuj dalej”), a niektóre karty z firmware/DCB same przechwytują LLDP, przez co \`lldpd\` w systemie nic nie widzi.
- **Różne podtypy Port ID** — jedno urządzenie ogłasza nazwę portu („Gi1/0/12”), inne MAC lub indeks; skrypty muszą to normalizować.
- **Zbyt długi TTL po zmianie** — po przepięciu kabla stary sąsiad widnieje do 120 s (CDP 180 s); nie panikuj, odczekaj lub wyczyść tabelę (\`clear lldp table\`).` },

  { type: 'CODE_WRONG', heading: 'Źle: CDP i LLDP wszędzie, także na granicy', body:
`Ustawienia domyślne na routerze brzegowym i przełączniku dostępowym w strefie gości.`,
    code:
`! Router brzegowy (interfejs do operatora / Internetu)
interface GigabitEthernet0/0/0
 description WAN do operatora
 ip address 203.0.113.2 255.255.255.252
 ! cdp enable (domyślnie), lldp transmit (jeśli lldp run)
!
! Przełącznik w strefie gości / sala konferencyjna
lldp run
interface range Gi1/0/1 - 24
 switchport access vlan 60
 ! CDP i LLDP wysyłane na każdy port gościa
!
! Skutek: każdy gość z Wiresharkiem i każdy sąsiad u operatora widzi:
! Device ID: RTR-EDGE-WAW, Platform: Cisco ISR4451, Version 17.6.x,
! Management address: 10.99.0.1, Native VLAN: 1, VTP domain: CORP …` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: selektywnie — tam, gdzie pomaga, i tylko tam', body:
`LLDP na łączach infrastrukturalnych, do serwerów, AP i telefonów (LLDP-MED); wyłączony na granicy i w strefach niezaufanych; CDP wyłączony tam, gdzie wystarczy LLDP.`,
    code:
`lldp run
no cdp run                                 ! jeśli telefony/urządzenia nie wymagają CDP
!
interface GigabitEthernet0/0/0             ! WAN
 no lldp transmit
 no lldp receive
 no cdp enable
!
interface range Gi1/0/1 - 24               ! strefa gości
 no lldp transmit
 no lldp receive
!
interface range Gi1/0/25 - 44              ! biuro z telefonami IP
 switchport access vlan 10
 switchport voice vlan 30
 lldp transmit                             ! LLDP-MED: network policy (VLAN 30, PCP 5)
 lldp receive
 authentication port-control auto          ! 802.1X/MAB dla telefonów i komputerów
!
interface range Te1/1/1 - 4                ! uplinki i serwery
 lldp transmit
 lldp receive
! Monitoring: zmiana sąsiada LLDP na uplinku → alert; sąsiad typu „Bridge” na porcie dostępowym → alert.` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy włączać, a kiedy nie', body:
`- **Włączaj LLDP** na: łączach między przełącznikami i routerami (diagnoza, automatyczne diagramy), portach do serwerów i hiperwizorów (weryfikacja okablowania, provisioning bare metal), portach do telefonów IP i punktów dostępowych (LLDP-MED, PoE, voice VLAN), w sieci zarządzania.
- **CDP** — tylko tam, gdzie potrzebują go urządzenia Cisco (starsze telefony, niektóre funkcje), w nowych projektach preferuj LLDP jako standard.
- **Wyłączaj** na: interfejsach do Internetu i operatorów, portach w strefach gości, salach konferencyjnych, DMZ z obcymi systemami, portach nieużywanych i tam, gdzie nie masz kontroli nad sąsiadem.
- **Tryb tylko odbiór** (\`no lldp transmit\`, \`lldp receive\`) — gdy chcesz wiedzieć, co jest podłączone, ale nie ujawniać informacji o sobie (np. porty dostępowe z NAC).
- **Serwery Linux** — \`lldpd\` warto mieć w obrazie bazowym serwerów bare metal: kosztuje kilka MB, a skraca diagnozę okablowania z godzin do sekund.
- **Dokumentacja** — traktuj LLDP jako źródło prawdy o fizycznych połączeniach do porównywania z dokumentacją (NetBox) i wykrywania odchyleń.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- LLDP (IEEE 802.1AB) i CDP (Cisco) ogłaszają bezpośrednim sąsiadom: nazwę, port, rolę, system, adres zarządzania, VLAN; informacje nie są przekazywane dalej.
- LLDP: 01:80:c2:00:00:0e, EtherType 0x88CC, co 30 s, TTL 120 s (30 × 4); CDP: 01:00:0c:cc:cc:cc (SNAP), co 60 s, holdtime 180 s.
- LLDPDU to ciąg TLV: nagłówek = 7 bitów typu + 9 bitów długości → (typ << 9) | długość; obowiązkowe: Chassis ID (1), Port ID (2), TTL (3), End (0); rozszerzenia w TLV 127 (802.1, 802.3, LLDP-MED).
- Narzędzia: show lldp/cdp neighbors [detail], lldpcli show neighbors [-f json], tcpdump 'ether proto 0x88cc'.
- Zastosowania: lokalizacja serwerów, weryfikacja bondów i VLAN-ów, diagramy i CMDB, provisioning bare metal, VoIP (LLDP-MED).
- Bezpieczeństwo: ujawnianie informacji i brak uwierzytelniania → wyłączaj na granicy i w strefach niezaufanych, monitoruj nieoczekiwanych sąsiadów.
- Następna lekcja: **DHCP i autokonfiguracja adresów (SLAAC)**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [t, l] of [[5, 13], [8, 12], [6, 120], [127, 9]]) ex(`Zakoduj nagłówek TLV LLDP dla typu ${t} (${tlvName[t]}) o długości wartości ${l} bajtów. Podaj wynik binarnie i szesnastkowo.`, 'Nagłówek = (typ << 9) | długość; 7 bitów typu, 9 bitów długości.', `(${t} << 9) | ${l} = ${t * 512} + ${l} = ${hdr(t, l)} = ${h4(hdr(t, l))} = ${hdr(t, l).toString(2).padStart(16, '0').replace(/^(.{7})/, '$1 ')} (typ ${t.toString(2).padStart(7, '0')}, długość ${l.toString(2).padStart(9, '0')}).`);
for (const h of [0x0207, 0x0A0D, 0xFE06, 0x0C1C]) { const d = dec(h); ex(`Zdekoduj nagłówek TLV ${h4(h)}: jaki to typ i ile bajtów ma wartość?`, 'typ = h >> 9, długość = h & 0x1FF.', `${h4(h)} = ${h.toString(2).padStart(16, '0')} → typ ${d.type} (${tlvName[d.type] || 'inny'}), długość ${d.len} B.`); }
for (const [i, m] of [[30, 4], [10, 3], [60, 3]]) ex(`LLDP wysyła ogłoszenia co ${i} s z mnożnikiem ${m}. Jaki TTL znajdzie się w ramce i po jakim czasie od ostatniego ogłoszenia sąsiad zniknie z tabeli?`, 'TTL = interwał × mnożnik.', `TTL = ${i} × ${m} = ${i * m} s; sąsiad zniknie po ${i * m} s bez nowego ogłoszenia (np. po odłączeniu kabla bez utraty sygnału lub wyłączeniu LLDP po drugiej stronie). Przy utracie łącza (link down) wpis jest usuwany od razu.`);
const more = [
  ['Wykonaj zadanie z planu lekcji: zbierz sąsiedztwo LLDP w labie (Containerlab) i narysuj diagram fizyczny. Przedstaw tabelę połączeń i wnioski.', 'show lldp neighbors / lldpcli -f json; usuń duplikaty łączy.', 'Przykładowa tabela: sw1 eth1 ↔ sw2 eth1; sw1 eth2 ↔ pc1 eth1; sw1 eth3 ↔ pc2 eth1; sw2 eth2 ↔ pc3 eth1; sw2 eth3 ↔ pc4 eth1. Każde łącze widać z obu stron (np. sw1 widzi sw2:eth1, sw2 widzi sw1:eth1) — po normalizacji 5 unikatowych łączy. Diagram w Graphviz/Mermaid zgodny z containerlab graph. Wnioski: lista portów, na których LLDP ogłasza wersję systemu do hostów (do wyłączenia transmit), weryfikacja zgodności z plikiem topologii.'],
  ['Na serwerze z bondem dwóch kart uruchom lldpcli show neighbors (lub opisz wynik). Jak sprawdzisz, czy bond zapewnia redundancję przełącznika?', 'Sąsiad każdej karty.', 'Wynik: eno1 → SysName LEAF-A, PortID Ethernet5; eno2 → SysName LEAF-B, PortID Ethernet5. Różne SysName (i Chassis ID) = łącza do dwóch różnych przełączników (para MLAG) → redundancja przełącznika. Jeśli obie karty widzą ten sam przełącznik, awaria tego przełącznika odetnie serwer — trzeba przepiąć kabel.'],
  ['Przechwyć ramkę LLDP tcpdumpem (lub opisz) i wskaż w niej: adres docelowy, EtherType, TLV Chassis ID, Port ID, TTL i System Name.', 'tcpdump -i eth0 -nn -v -xx ether proto 0x88cc.', 'Nagłówek Ethernet: dst 01:80:c2:00:00:0e, src MAC portu, EtherType 0x88cc. TLV: 02 07 04 <6 B MAC> (Chassis ID, podtyp MAC), 04 0a 05 <„Gi1/0/12”> lub inny podtyp (Port ID), 06 02 00 78 (TTL 120), 0a 0d <„SW-WAW-ACC-01”> (System Name), …, 00 00 (End). tcpdump -v dekoduje te pola słownie.'],
  ['Wyjaśnij, dlaczego ramka LLDP nie jest przekazywana przez przełącznik dalej, i jakie ma to konsekwencje dla wirtualizacji.', 'Adres 01:80:c2:00:00:0e należy do zakresu „nie przekazuj” (Nearest Bridge).', 'Adresy 01:80:c2:00:00:00–0f są zarezerwowane dla protokołów sterujących mostów (STP, LACP, LLDP, 802.1X); zgodne z 802.1D przełączniki ich nie przekazują — LLDP z adresem Nearest Bridge dociera tylko do bezpośredniego sąsiada. Konsekwencja: maszyny wirtualne za vSwitchem (lub kontenery za bridge’em Linuksa) zwykle nie widzą fizycznego przełącznika — informację o sąsiedztwie zbiera się na hiperwizorze (vDS discovery, lldpd na hoście).'],
  ['Opisz, co LLDP-MED przekazuje telefonowi VoIP i dlaczego to ważne dla segmentacji.', 'Network Policy TLV: VLAN, priorytet L2, DSCP.', 'LLDP-MED (TIA-1057) przekazuje m.in. Network Policy (aplikacja Voice: VLAN ID, priorytet 802.1p np. 5, DSCP np. EF 46), informacje o PoE (pobór mocy), lokalizację (dla numerów alarmowych) i inwentarz. Telefon sam przełącza się do voice VLAN i oznacza ruch właściwym priorytetem — głos jest odseparowany od danych komputera i chroniony QoS. Ryzyko: urządzenie podszywające się pod telefon też może dostać voice VLAN — stąd 802.1X/MAB.'],
  ['Zaproponuj politykę LLDP/CDP dla firmy z biurami, serwerownią i strefą gości. Wskaż, gdzie transmit, gdzie receive, gdzie nic.', 'Selektywność.', 'Serwerownia (uplinki, serwery, hiperwizory): LLDP transmit + receive; CDP wyłączony, jeśli niepotrzebny. Biura: porty z telefonami — LLDP-MED transmit + receive (lub CDP dla telefonów Cisco), porty komputerów bez telefonu — tylko receive (wiemy, co jest wpięte, nie ujawniamy siebie). Strefa gości, sale konferencyjne, DMZ z obcymi systemami, WAN/Internet: wyłączone całkowicie. Monitoring zmian sąsiadów na uplinkach i nieoczekiwanych urządzeń typu Bridge na portach dostępowych.'],
  ['Analiza z perspektywy obrońcy: jakie informacje z przechwyconej ramki CDP są najbardziej wartościowe dla napastnika i co z nimi zrobi (koncepcyjnie)?', 'Platforma, wersja, adres zarządzania, VLAN-y.', 'Najcenniejsze: wersja oprogramowania i platforma (porównanie z bazą podatności, np. CISA KEV — wybór exploita), adres zarządzania (cel logowania/ataku na interfejs WWW/SSH), native VLAN i voice VLAN (próby przejścia do innych VLAN-ów, podszycie pod telefon), nazwy urządzeń i domena VTP (mapa sieci, socjotechnika). Wniosek obronny: wyłączyć CDP/LLDP na portach dostępowych niezaufanych, segmentować zarządzanie, aktualizować oprogramowanie.'],
  ['Sąsiad LLDP na porcie użytkownika Gi1/0/22 zmienił się z „Station” (laptop) na „Bridge” (przełącznik). Co mogło się stać i jak zareagujesz?', 'Nieautoryzowany przełącznik lub punkt dostępowy.', 'Ktoś podłączył mały przełącznik lub punkt dostępowy (np. by podłączyć więcej urządzeń albo celowo, by rozszerzyć dostęp). Ryzyka: pętla L2, nieautoryzowane urządzenia za przełącznikiem, nieautoryzowane Wi-Fi. Reakcja: sprawdzić tablicę MAC na porcie (ile urządzeń), czy BPDU Guard zadziałał, zlokalizować i usunąć urządzenie, ewentualnie port security/802.1X. W SIEM: reguła alertu na zmianę capabilities sąsiada na porcie dostępowym.'],
  ['Napisz w Javie parser nagłówków TLV, który z tablicy bajtów LLDPDU (bez nagłówka Ethernet) wypisze typ, długość i surową wartość każdego TLV, kończąc na TLV End.', 'Czytaj 2 bajty, wylicz typ i długość, przesuń wskaźnik.', 'record Tlv(int type, byte[] value) {}\nstatic List<Tlv> parse(byte[] pdu) {\n  var out = new ArrayList<Tlv>(); int i = 0;\n  while (i + 2 <= pdu.length) {\n    int h = ((pdu[i] & 0xff) << 8) | (pdu[i + 1] & 0xff);\n    int type = h >>> 9, len = h & 0x1ff; i += 2;\n    if (type == 0) break;                       // End of LLDPDU\n    if (i + len > pdu.length) throw new IllegalArgumentException("uszkodzony TLV");\n    out.add(new Tlv(type, Arrays.copyOfRange(pdu, i, i + len))); i += len;\n  }\n  return out;\n}\n// Walidacja długości chroni przed odczytem poza buforem — klasyczny błąd parserów protokołów.'],
  ['Wyjaśnij, czym są podtypy Chassis ID i Port ID oraz dlaczego utrudniają porównywanie danych z różnych urządzeń.', 'Podtyp to pierwszy bajt wartości.', 'Pierwszy bajt wartości TLV Chassis ID/Port ID to podtyp: dla Chassis ID m.in. 4 = adres MAC, 5 = adres sieciowy, 7 = lokalnie przypisany; dla Port ID m.in. 3 = MAC, 5 = nazwa interfejsu, 7 = lokalny (np. indeks). Jedno urządzenie ogłasza port jako „Gi1/0/12”, inne jako „GigabitEthernet1/0/12”, „ge-0/0/12” albo MAC — skrypty budujące diagramy muszą normalizować nazwy (np. mapując indeks SNMP ifIndex lub rozwijając skróty).'],
  ['Porównaj, jak zebrać sąsiedztwo z 200 przełączników: SSH + parsowanie show lldp neighbors, SNMP (LLDP-MIB) i API (NETCONF/gNMI/eAPI). Zalety i wady.', 'Tekst vs dane ustrukturyzowane.', 'SSH + parsowanie: działa wszędzie, ale tekst różni się między wersjami i producentami — kruche (TextFSM/Genie pomagają). SNMP LLDP-MIB (lldpRemTable): ustrukturyzowane, wspierane przez większość urządzeń, wymaga SNMPv3 i znajomości OID/indeksów. API (NETCONF/YANG openconfig-lldp, gNMI, Arista eAPI JSON): najlepsza struktura i wydajność, wymaga nowszych urządzeń. W praktyce: narzędzia (Netdisco, LibreNMS, NetBox z wtyczkami, Nornir) łączą te metody.'],
  ['Po przepięciu serwera do innego portu show lldp neighbors nadal pokazuje go na starym porcie. Dlaczego i co zrobisz?', 'TTL wpisu.', 'Jeśli stary port nie zgłosił link down (np. kabel wyjęty po stronie patch panelu z aktywnym urządzeniem pośrednim) lub wpis pochodzi z innego źródła, sąsiad pozostaje w tabeli do wygaśnięcia TTL (120 s LLDP, 180 s CDP). Rozwiązanie: odczekać, sprawdzić Hold-time malejący w show lldp neighbors, ewentualnie clear lldp table / clear cdp table; zweryfikować stan portu (show interfaces status).'],
  ['Wyjaśnij różnicę między LLDP a IPv6 Neighbor Discovery oraz między LLDP a ARP — trzy protokoły „o sąsiadach”.', 'Warstwa, cel, mechanizm.', 'LLDP (L2, 802.1AB): jednokierunkowe ogłoszenia o tożsamości urządzenia i portu do bezpośredniego sąsiada, do zarządzania i dokumentacji; nie służy do przesyłania danych. ARP (L2/L3 IPv4): zapytanie „kto ma ten adres IP” → odpowiedź z MAC, potrzebne do wysłania każdego pakietu IPv4 w segmencie. IPv6 ND (ICMPv6): odpowiednik ARP dla IPv6 plus wykrywanie routerów, SLAAC, DAD. LLDP nie zastępuje ani nie wpływa na rozwiązywanie adresów.'],
  ['Jak włączyć wysyłanie LLDP z serwera Linux bez instalowania lldpd (systemd-networkd)? Kiedy to wystarczy?', 'Plik .network z LLDP= i EmitLLDP=.', 'W pliku /etc/systemd/network/10-eno1.network w sekcji [Network]: LLDP=yes (odbiór) i EmitLLDP=nearest-bridge (lub yes) — wysyłanie. Odczyt sąsiadów: networkctl lldp. Wystarcza do podstawowej identyfikacji serwera i odczytu sąsiada; lldpd daje więcej (CDP, LLDP-MED, JSON, konfiguracja TLV, SNMP subagent).'],
  ['Opisz, jak provisioning serwerów bare metal (np. MAAS, OpenStack Ironic) wykorzystuje LLDP.', 'Introspekcja nowego serwera.', 'Nowy serwer startuje z obrazu introspekcyjnego (PXE), który zbiera dane o sprzęcie i odbiera ramki LLDP na każdej karcie — wie, do którego przełącznika i portu podłączona jest każda karta. System provisioningu automatycznie konfiguruje port przełącznika (VLAN, kanał LACP) i przypisuje serwer do właściwej sieci bez ręcznego mapowania kabli. Błąd okablowania wychodzi od razu (LLDP pokaże inny port niż w projekcie).'],
  ['Podaj trzy reguły detekcji (SIEM/NAC) oparte na danych LLDP/CDP, które pomogą wykryć nieautoryzowane urządzenia lub zmiany.', 'Zmiana sąsiada, typ urządzenia, porty bez LLDP.', '1) Zmiana Chassis ID/System Name sąsiada na łączu między przełącznikami (uplink) bez zgłoszenia zmiany → alert wysokiego priorytetu (możliwe przepięcie lub urządzenie pośredniczące). 2) Sąsiad z capability Bridge/Router/WLAN AP na porcie dostępowym użytkownika → nieautoryzowany przełącznik/AP. 3) Pojawienie się sąsiada z nieznaną wersją systemu lub nazwą spoza konwencji nazewniczej w sieci zarządzania → weryfikacja inwentarza. Dodatkowo: ramki CDP/LLDP odebrane na portach, gdzie protokoły powinny być wyłączone (konfiguracja niezgodna z polityką).'],
];
for (const [q, h, a] of more) ex(q, h, a);
ex('Policz rozmiar LLDPDU (bez nagłówka Ethernet) zawierającego: Chassis ID (podtyp MAC), Port ID „Gi1/0/12” (podtyp nazwa), TTL, System Name „SW-WAW-ACC-01” i End. Czy ramka zmieści się w minimalnej ramce Ethernet?', 'Każde TLV = 2 B nagłówka + wartość; Chassis ID = 1 + 6 B, Port ID = 1 + 8 B.', `Chassis ID: 2 + 7 = 9 B; Port ID: 2 + 9 = 11 B; TTL: 2 + 2 = 4 B; System Name: 2 + 13 = 15 B; End: 2 B. Razem ${9 + 11 + 4 + 15 + 2} B. To mniej niż minimalny ładunek ramki (46 B), więc karta dopełni ramkę do 64 B (z nagłówkiem i FCS) — zgodnie z lekcją 01 o ramce Ethernet.`);
ex('Skonfiguruj na przełączniku Cisco interwał LLDP 10 s i holdtime 40 s, a następnie wyjaśnij, kiedy krótszy interwał ma sens i jaki ma koszt.', 'lldp timer, lldp holdtime.', 'lldp timer 10; lldp holdtime 40 (TTL = 40 s). Sens: środowiska z częstymi zmianami okablowania (lab, provisioning bare metal), gdzie szybciej chcesz widzieć nowego sąsiada i usuwać nieaktualnych. Koszt: więcej ramek (na każdym porcie co 10 s — pomijalne pasmo, ale więcej przetwarzania CPU na dużych przełącznikach) i częstsze zmiany w systemach monitorujących. W produkcji zwykle zostawia się 30/120.');
ex('Przygotuj listę kontrolną audytu LLDP/CDP (minimum 6 punktów).', 'Globalnie, per port, VoIP, granica, monitoring, dokumentacja.', '1) Jednolity wybór protokołu (LLDP jako standard, CDP tylko gdy potrzebny). 2) Wyłączone na interfejsach WAN/Internet i do operatorów. 3) Wyłączone w strefach gości, salach konferencyjnych, DMZ. 4) Porty dostępowe bez telefonów — co najwyżej receive. 5) VoIP: LLDP-MED/CDP tylko na portach z telefonami, z 802.1X/MAB. 6) Uplinki i serwery: transmit + receive, dane zbierane do NetBox/CMDB. 7) Alerty na zmianę sąsiada na uplinkach i nieoczekiwane typy urządzeń. 8) Aktualne oprogramowanie (historyczne podatności w obsłudze CDP/LLDP).');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111909);
const tAll = TLV.map(([t, n]) => `${t} — ${n}`);
for (const [t, n, d] of TLV) add(`Który typ TLV LLDP przenosi: ${d}?`, `${t} — ${n}`, tAll.filter((x) => x !== `${t} — ${n}`).slice(0, 3), `TLV ${t}: ${n}.`);
for (const [t, l] of [[1, 7], [2, 9], [3, 2], [4, 10], [5, 13], [6, 60], [7, 4], [8, 12], [127, 6], [5, 20], [4, 25], [6, 255]]) {
  const h = hdr(t, l);
  add(`Jaki jest nagłówek TLV (16 bitów) dla typu ${t} i długości ${l} B?`, h4(h), [h4((l << 9) | t), h4((t << 8) | l) === h4(h) ? h4(h + 1) : h4((t << 8) | l), h4(h ^ 0x0200)], `(${t} << 9) | ${l} = ${h} = ${h4(h)}.`);
}
for (const h of [0x0207, 0x0409, 0x0602, 0x0A0D, 0x0C3C, 0x0E04, 0x100C, 0xFE06, 0x0814, 0xFE09]) {
  const d = dec(h);
  add(`Jaki typ i długość koduje nagłówek TLV ${h4(h)}?`, `typ ${d.type}, ${d.len} B`, [`typ ${h >> 8}, ${h & 0xff} B`, `typ ${d.len & 0x7f}, ${d.type} B`, `typ ${d.type + 1}, ${d.len} B`, `typ ${d.type}, ${d.len + 256} B`], `typ = h >> 9 = ${d.type} (${tlvName[d.type] || '—'}), długość = h & 0x1FF = ${d.len}.`);
}
for (const [i, m] of [[30, 4], [10, 4], [20, 3], [60, 2], [5, 4], [30, 3]]) add(`Jaki TTL ogłasza LLDP przy interwale ${i} s i mnożniku ${m}?`, `${i * m} s`, [`${i + m} s`, `${i} s`, `${i * m * 2} s`, `${m} s`], `TTL = ${i} × ${m} = ${i * m} s.`);
const facts = [
  ['Jakim standardem IEEE jest LLDP?', '802.1AB', ['802.1Q', '802.3ad', '802.1X'], 'LLDP = Link Layer Discovery Protocol.'],
  ['Na jaki adres MAC wysyłane są ramki LLDP (Nearest Bridge)?', '01:80:c2:00:00:0e', ['01:00:0c:cc:cc:cc', '01:80:c2:00:00:00', 'ff:ff:ff:ff:ff:ff'], '01:80:c2:00:00:00 to STP.'],
  ['Na jaki adres MAC wysyłane są ramki CDP?', '01:00:0c:cc:cc:cc', ['01:80:c2:00:00:0e', '01:00:5e:00:00:01', '01:00:0c:cc:cc:cd'], '...cd to PVST+.'],
  ['Jaki EtherType mają ramki LLDP?', '0x88CC', ['0x8809', '0x8100', '0x0800'], '0x8809 to LACP.'],
  ['Jak często domyślnie wysyłane są ogłoszenia LLDP?', 'Co 30 s', ['Co 60 s', 'Co 2 s', 'Co 5 minut'], 'TTL 120 s.'],
  ['Jak często domyślnie wysyłane są ogłoszenia CDP?', 'Co 60 s', ['Co 30 s', 'Co 2 s', 'Co 10 minut'], 'Holdtime 180 s.'],
  ['Jaki jest domyślny holdtime CDP?', '180 s', ['120 s', '60 s', '30 s'], 'Trzy interwały.'],
  ['Czy LLDP przekazuje informacje o sąsiadach dalej przez sieć?', 'Nie — widzisz tylko bezpośrednich sąsiadów', ['Tak, do całej domeny L2', 'Tak, do roota STP', 'Tylko przez trunki'], 'Pełna mapa wymaga zebrania danych ze wszystkich urządzeń.'],
  ['Które TLV są w LLDPDU obowiązkowe?', 'Chassis ID, Port ID, TTL i End', ['System Name i System Description', 'Management Address i VLAN', 'Tylko Chassis ID'], 'Pozostałe są opcjonalne.'],
  ['Ile bitów ma pole typu w nagłówku TLV LLDP?', '7', ['8', '9', '16'], 'Długość ma 9 bitów.'],
  ['Jaka jest maksymalna długość wartości TLV LLDP?', '511 bajtów', ['255 bajtów', '1500 bajtów', '65 535 bajtów'], '9 bitów długości.'],
  ['Do czego służy TLV typu 127?', 'Do rozszerzeń organizacji (802.1, 802.3, LLDP-MED) identyfikowanych przez OUI', ['Do zakończenia ramki', 'Do szyfrowania', 'Do przenoszenia danych użytkownika'], 'Np. Port VLAN ID, PoE, voice policy.'],
  ['Co przekazuje LLDP-MED telefonom VoIP?', 'Politykę sieci dla głosu (VLAN, priorytet), informacje PoE i lokalizację', ['Numery telefonów', 'Klucze szyfrowania rozmów', 'Adres serwera SIP i hasło'], 'TIA-1057.'],
  ['Czy LLDP ma uwierzytelnianie?', 'Nie — ogłoszenia można podrobić', ['Tak, oparte na certyfikatach', 'Tak, kluczem współdzielonym', 'Tylko w wersji MED'], 'Nie używaj go do decyzji dostępowych.'],
  ['Dlaczego wyłącza się LLDP/CDP na interfejsach do Internetu?', 'Aby nie ujawniać modelu, wersji oprogramowania i adresu zarządzania', ['Aby przyspieszyć łącze', 'Bo LLDP nie działa przez światłowód', 'Bo blokuje BGP'], 'Rozpoznanie dla napastnika.'],
  ['Które polecenie Cisco globalnie włącza LLDP?', 'lldp run', ['lldp enable', 'cdp run', 'spanning-tree lldp'], 'cdp run włącza CDP.'],
  ['Które polecenie Cisco wyłącza CDP na interfejsie?', 'no cdp enable', ['no cdp run', 'cdp disable', 'shutdown cdp'], 'no cdp run wyłącza globalnie.'],
  ['Jak wyłączyć tylko wysyłanie LLDP na porcie Cisco, zostawiając odbiór?', 'no lldp transmit', ['no lldp receive', 'no lldp run', 'lldp passive'], 'Tryb „tylko słucham”.'],
  ['Którym poleceniem Linuksa (lldpd) wyświetlisz sąsiadów w formacie JSON?', 'lldpcli show neighbors -f json', ['ip neigh show', 'arp -a', 'lldp --json'], 'Do skryptów i CMDB.'],
  ['Który filtr tcpdump przechwyci ramki LLDP?', "ether proto 0x88cc", ['udp port 88', 'arp', 'ether proto 0x8100'], 'CDP: ether host 01:00:0c:cc:cc:cc.'],
  ['Co pokazuje kolumna „Capability” w show lldp neighbors?', 'Rolę sąsiada (np. B — bridge, R — router, T — telefon, S — station)', ['Prędkość łącza', 'Liczbę VLAN-ów', 'Pojemność bufora'], 'Przydatne do wykrywania nieautoryzowanych przełączników.'],
  ['Jak LLDP pomaga sprawdzić redundancję bondu serwera?', 'Pokazuje, do których przełączników i portów prowadzi każda karta', ['Mierzy przepustowość bondu', 'Konfiguruje LACP', 'Blokuje jedno z łączy'], 'Dwa różne przełączniki = redundancja.'],
  ['Dlaczego maszyny wirtualne zwykle nie widzą sąsiada LLDP fizycznego przełącznika?', 'Bo vSwitch/bridge nie przekazuje ramek na adres 01:80:c2:00:00:0e', ['Bo VM nie mają kart sieciowych', 'Bo LLDP działa tylko w IPv6', 'Bo hiperwizor szyfruje ramki'], 'Dane zbiera się na hoście.'],
  ['Czym LLDP różni się od IPv6 Neighbor Discovery?', 'LLDP ogłasza tożsamość urządzenia L2, a ND rozwiązuje adresy i wykrywa routery w IPv6', ['To ten sam protokół', 'ND działa tylko na przełącznikach', 'LLDP zastępuje ARP'], 'Nie mylić nazw.'],
  ['Jakie zagrożenie wiąże się z podszyciem się pod telefon IP przez CDP/LLDP-MED?', 'Uzyskanie dostępu do voice VLAN', ['Przejęcie roli root bridge', 'Zmiana MTU', 'Wyłączenie DHCP'], 'Obrona: 802.1X/MAB.'],
  ['Które narzędzie automatycznie buduje inwentarz i diagramy z danych LLDP?', 'Netdisco / LibreNMS / NetBox (z importem)', ['Wireshark', 'curl', 'JUnit'], 'Dokumentacja jako kod.'],
  ['Jakie polecenie Cisco czyści tabelę sąsiadów LLDP?', 'clear lldp table', ['reset lldp', 'no lldp neighbors', 'clear mac address-table'], 'Przydatne po przepięciach.'],
  ['Co oznacza podtyp 4 w TLV Chassis ID?', 'Identyfikator jest adresem MAC', ['Identyfikator jest adresem IPv4', 'Identyfikator jest nazwą portu', 'Brak identyfikatora'], 'Pierwszy bajt wartości.'],
  ['Jaki MIB pozwala odczytać sąsiadów LLDP przez SNMP?', 'LLDP-MIB (lldpRemTable)', ['IF-MIB', 'BRIDGE-MIB', 'HOST-RESOURCES-MIB'], 'Ustrukturyzowane dane do automatyzacji.'],
  ['Które urządzenie w capability LLDP na porcie użytkownika powinno wzbudzić alarm?', 'Bridge (przełącznik) lub WLAN AP', ['Station', 'Telephone na porcie z voice VLAN', 'Brak sąsiada'], 'Możliwy nieautoryzowany przełącznik lub AP.'],
  ['W jakim celu provisioning bare metal używa LLDP?', 'Aby automatycznie ustalić, do którego portu przełącznika podłączono kartę serwera', ['Aby zainstalować system', 'Aby zaszyfrować dysk', 'Aby przydzielić licencje'], 'MAAS, Ironic, Metal3.'],
  ['Jak systemd-networkd włącza wysyłanie LLDP?', 'EmitLLDP= w pliku .network', ['LLDP=send w /etc/hosts', 'lldp on w sysctl', 'Nie da się'], 'Odczyt: networkctl lldp.'],
  ['Który protokół odkrywania sąsiadów jest standardem wielu producentów?', 'LLDP', ['CDP', 'PAgP', 'VTP'], 'CDP i PAgP to protokoły Cisco.'],
  ['Co jest bezpieczniejszą alternatywą dla całkowitego wyłączenia LLDP na portach dostępowych z komputerami?', 'Tryb tylko odbioru (bez transmit)', ['Wydłużenie interwału do 300 s', 'Włączenie CDP zamiast LLDP', 'Zmiana adresu docelowego'], 'Wiesz, co jest wpięte, nie ujawniasz siebie.'],
  ['Jakie informacje typowo zawiera CDP, których nie ma w podstawowym LLDP?', 'Natywny VLAN, domena VTP, platforma i wersja IOS w polach natywnych', ['Hasło enable', 'Tablicę routingu', 'Klucze SSH'], 'W LLDP podobne dane w TLV opcjonalnych/127.'],
  ['Dlaczego parser TLV musi sprawdzać długość względem rozmiaru bufora?', 'Aby uniknąć odczytu poza buforem przy uszkodzonej lub złośliwej ramce', ['Aby przyspieszyć parsowanie', 'Bo LLDP jest szyfrowany', 'Bo długość jest zawsze 0'], 'Klasyczna podatność parserów protokołów.'],
  ['Ile czasu po odłączeniu sąsiada (bez link down) jego wpis LLDP pozostanie w tabeli przy domyślnych ustawieniach?', 'Do 120 s', ['Do 30 s', 'Do 180 s', 'Na zawsze'], 'Dla CDP do 180 s.'],
  ['Który protokół zbierze z przełącznika informację o PoE i prędkości portu dla hosta?', 'LLDP z TLV 802.3 (MAC/PHY, Power via MDI)', ['ARP', 'DHCP', 'ICMP'], 'TLV 127, OUI 00-12-0F.'],
  ['Która z informacji ogłaszanych przez LLDP jest najcenniejsza dla audytu podatności?', 'System Description (platforma i wersja oprogramowania)', ['TTL', 'End of LLDPDU', 'Port Description'], 'Porównanie z biuletynami bezpieczeństwa.'],
  ['Czy LLDP działa na łączach trunk z wieloma VLAN-ami?', 'Tak — ramki LLDP są wysyłane bez znacznika VLAN na łączu fizycznym', ['Nie, tylko na portach access', 'Tylko w VLAN 1', 'Tylko w native VLAN z tagiem'], 'To protokół łącza fizycznego.'],
  ['Co zrobić, gdy lldpd na serwerze nie widzi żadnego sąsiada, choć przełącznik ma LLDP włączony?', 'Sprawdzić, czy karta (firmware/DCB) nie przechwytuje LLDP i czy port ma lldp transmit', ['Zrestartować DNS', 'Zmienić MTU', 'Wyłączyć IPv6'], 'Niektóre karty obsługują LLDP sprzętowo.'],
  ['Jaką korzyść daje LLDP przy zgłoszeniu „serwer w złym VLAN-ie”?', 'TLV 802.1 Port VLAN ID pokazuje VLAN portu przełącznika', ['Automatycznie zmienia VLAN', 'Wyłącza port', 'Generuje adres IP'], 'Szybka diagnoza bez dostępu do przełącznika.'],
  ['Które polecenie Cisco pokazuje liczniki ramek LLDP?', 'show lldp traffic', ['show lldp counters all', 'show interfaces lldp', 'debug lldp'], 'Wysłane, odebrane, błędne.'],
  ['Na czym polega ryzyko DoS związane z CDP na starszych urządzeniach?', 'Zalewanie fałszywymi ogłoszeniami wyczerpywało pamięć i CPU', ['CDP wyłączało porty', 'CDP zmieniało root bridge', 'CDP kasowało konfigurację'], 'Obrona: aktualizacje, wyłączanie tam, gdzie zbędny.'],
  ['Jak należy traktować dane z LLDP w kontekście bezpieczeństwa?', 'Jako przydatne, ale nieuwierzytelnione — wymagające weryfikacji przed decyzjami', ['Jako w pełni zaufane', 'Jako szyfrowane i podpisane', 'Jako bezużyteczne'], 'Dobre do dokumentacji i detekcji anomalii.'],
  ['Jaki podtyp Port ID oznacza nazwę interfejsu (np. Gi1/0/12)?', '5 (interface name)', ['3 (MAC)', '4 (adres sieciowy)', '1 (alias)'], 'Różne urządzenia stosują różne podtypy.'],
  ['Jaki skutek ma wyłączenie LLDP/CDP na wszystkich portach biura z telefonami IP?', 'Telefony mogą nie dostać voice VLAN i właściwej polityki QoS', ['Nic się nie stanie', 'Telefony zaczną szyfrować', 'Wyłączy się STP'], 'Wyłączaj selektywnie.'],
  ['Jak nazywa się protokół odkrywania sąsiadów MikroTik?', 'MNDP', ['CDP', 'EDP', 'FDP'], 'EDP to Extreme, FDP Foundry/Brocade.'],
  ['Które narzędzie Containerlab rysuje topologię labu?', 'containerlab graph', ['containerlab draw', 'clab-diagram', 'lldpcli graph'], 'Porównaj z diagramem z LLDP.'],
  ['Który element ramki LLDP zawiera adres IP do zarządzania urządzeniem?', 'TLV 8 Management Address', ['TLV 3 TTL', 'TLV 2 Port ID', 'Nagłówek Ethernet'], 'Cel dla napastnika — ogranicz ujawnianie.'],
  ['Czy LLDP jest dwukierunkową wymianą z potwierdzeniami?', 'Nie — każda strona niezależnie wysyła ogłoszenia, bez potwierdzeń', ['Tak, z potwierdzeniem ACK', 'Tak, jak TCP', 'Tylko między przełącznikami'], 'Jednokierunkowe ogłoszenia.'],
  ['Jakie polecenie Cisco pokaże wersję IOS sąsiada przez CDP?', 'show cdp neighbors detail', ['show version', 'show cdp traffic', 'show lldp interface'], 'Pole Version.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const name of ['SW1', 'core-sw-01', 'SW-WAW-ACC-01', 'srv-app-07', 'leaf-a.dc1.example', 'R1', 'ap-2p-03', 'phone-1043', 'fw-edge-01', 'k8s-node-12']) {
  const len = Buffer.byteLength(name, 'utf8'); const h = hdr(5, len);
  add(`Ile bajtów zajmie w LLDPDU całe TLV System Name z wartością „${name}” i jaki ma nagłówek?`, `${2 + len} B, nagłówek ${h4(h)}`, [`${len} B, nagłówek ${h4(h)}`, `${2 + len} B, nagłówek ${h4((len << 9) | 5)}`, `${4 + len} B, nagłówek ${h4(h)}`, `${2 + len} B, nagłówek ${h4((5 << 8) | len)}`], `Wartość ${len} B + 2 B nagłówka = ${2 + len} B; nagłówek (5 << 9) | ${len} = ${h4(h)}.`);
}
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
