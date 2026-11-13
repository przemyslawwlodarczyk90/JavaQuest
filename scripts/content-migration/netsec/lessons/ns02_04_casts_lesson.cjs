// Generuje lekcje _ns_02_ethernet_lan/04_UnicastBroadcastMulticast.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_04_casts_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/04_UnicastBroadcastMulticast.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const hx = (n) => n.toString(16).padStart(2, '0');
const mac4 = (ip) => { const o = ip.split('.').map(Number); return `01:00:5e:${hx(o[1] & 0x7f)}:${hx(o[2])}:${hx(o[3])}`; };
const ipInt = (ip) => ip.split('.').reduce((a, o) => a * 256 + Number(o), 0);
const intIp = (n) => [24, 16, 8, 0].map((s) => Math.floor(n / 2 ** s) % 256).join('.');
const bcast = (ip, p) => intIp(Math.floor(ipInt(ip) / 2 ** (32 - p)) * 2 ** (32 - p) + 2 ** (32 - p) - 1);
function cls(ip) {
  const o = ip.split('.').map(Number);
  if (ip === '255.255.255.255') return 'broadcast ograniczony (limited broadcast)';
  if (o[0] >= 224 && o[0] <= 239) {
    if (o[0] === 224 && o[1] === 0 && o[2] === 0) return 'multicast lokalny łącza (224.0.0.0/24, nie routowany)';
    if (o[0] === 232) return 'multicast SSM (232.0.0.0/8)';
    if (o[0] === 239) return 'multicast administracyjny (239.0.0.0/8, wewnątrz organizacji)';
    return 'multicast (224.0.0.0/4)';
  }
  return 'unicast';
}

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Do kogo właściwie wysyłasz pakiet?', ...img('ns02-04-modes.svg', 'Cztery panele: unicast — nadawca łączy się z jednym hostem; broadcast — z wszystkimi hostami w segmencie; multicast — tylko z hostami zapisanymi do grupy (IGMP/MLD join); anycast — z najbliższym z wielu serwerów o tym samym adresie. Podpis: IPv6 nie ma broadcastu, rolę przejął multicast.'),
    body:
`Prawie cały ruch Twojej aplikacji to **unicast** — jeden nadawca, jeden odbiorca: żądanie HTTP, zapytanie do bazy, wiadomość do Kafki. Ale sieć potrafi więcej:
- **broadcast** — „do wszystkich w segmencie” (ARP, DHCP Discover, stary NetBIOS),
- **multicast** — „do wszystkich zainteresowanych” (mDNS, SSDP, OSPF, VRRP, strumienie wideo, wykrywanie węzłów klastra),
- **anycast** — „do najbliższego z wielu” (publiczne resolvery DNS, serwery główne DNS, CDN).

Dlaczego developer i obrońca powinni to rozumieć?
- **Diagnoza**: klaster Hazelcast/JGroups, który „nie widzi węzłów” w chmurze, bo multicast tam nie działa; burze rozgłoszeniowe kładące VLAN; zachowanie DNS zależne od lokalizacji (anycast).
- **Wydajność**: każdy broadcast przerywa pracę *wszystkich* hostów w segmencie; duże VLAN-y płacą za to procesorem i opóźnieniami.
- **Bezpieczeństwo**: protokoły rozgłoszeniowe i multicastowe rozwiązywania nazw (LLMNR, NBT-NS, mDNS) to jeden z najczęstszych sposobów przechwytywania poświadczeń w sieciach Windows; SSDP i inne usługi UDP bywają wzmacniaczami DDoS; anycast to z kolei narzędzie obrony przed DDoS.

W tej lekcji poznasz zakresy adresów (224.0.0.0/4, ff00::/8), mapowanie multicastu na adresy MAC, IGMP/MLD i snooping, granice broadcastu, anycast oraz przechwycisz mDNS i SSDP w sieci domowej.` },

  { type: 'DEFINITION', heading: 'Tryby adresowania i ich adresy', body:
`- **Unicast** — jeden odbiorca; adresy „zwykłe” (IPv4 poza zakresami specjalnymi, IPv6 GUA/ULA/link-local), MAC z bitem I/G = 0.
- **Broadcast (IPv4)** — wszyscy w domenie rozgłoszeniowej:
  - **ograniczony** \`255.255.255.255\` — tylko lokalny segment, nigdy nie routowany (np. DHCP Discover),
  - **ukierunkowany (directed)** — adres broadcast podsieci, np. \`192.168.1.255\` dla \`192.168.1.0/24\`; routery domyślnie go nie przekazują (ochrona przed atakiem Smurf),
  - w L2: MAC \`ff:ff:ff:ff:ff:ff\`.
- **Multicast IPv4** — \`224.0.0.0/4\` (dawna klasa D): \`224.0.0.0/24\` lokalny łącza (TTL 1, np. 224.0.0.5 OSPF, 224.0.0.18 VRRP, 224.0.0.251 mDNS, 224.0.0.252 LLMNR), \`232.0.0.0/8\` SSM, \`239.0.0.0/8\` administracyjny (prywatny, np. 239.255.255.250 SSDP).
- **Multicast IPv6** — \`ff00::/8\`; czwarta cyfra to zasięg (*scope*): \`ff02::\` łącze, \`ff05::\` ośrodek, \`ff0e::\` globalny. Przykłady: \`ff02::1\` wszystkie węzły, \`ff02::2\` routery, \`ff02::fb\` mDNS, \`ff02::1:3\` LLMNR.
- **MAC dla multicastu** — IPv4: \`01:00:5e\` + dolne 23 bity adresu IP; IPv6: \`33:33\` + dolne 32 bity.
- **IGMP / MLD** — protokoły, którymi hosty zgłaszają routerowi (i przełącznikom ze *snoopingiem*) członkostwo w grupach (IPv4 / IPv6).
- **IGMP/MLD snooping** — przełącznik podsłuchuje zgłoszenia i wysyła ruch grupy tylko na porty członków.
- **Anycast** — ten sam adres przypisany wielu serwerom w różnych miejscach; routing (BGP) kieruje klienta do najbliższego.` },

  { type: 'ANALOGY', heading: 'Rozmowa, megafon, newsletter i infolinia', body:
`- **Unicast** to rozmowa telefoniczna: jeden dzwoni do jednego.
- **Broadcast** to ogłoszenie przez **megafon** na hali: słyszą wszyscy, czy chcą, czy nie — i każdy musi na chwilę przerwać pracę, by ocenić, czy to do niego. Megafon słychać tylko na tej hali (broadcast nie przechodzi przez router).
- **Multicast** to **newsletter**: dostają go tylko ci, którzy się zapisali (IGMP join). Poczta (przełącznik ze snoopingiem) wie, do których skrzynek go dostarczyć. Bez tej wiedzy poczta wrzucałaby newsletter wszystkim (zalewanie jak broadcast).
- **Anycast** to **infolinia z jednym numerem**: dzwonisz pod ten sam numer z Gdańska i z Krakowa, ale odbiera najbliższe biuro obsługi. Gdy jedno biuro jest przeciążone lub zamknięte, połączenie trafia do innego.

Problem bezpieczeństwa megafonu i newslettera: odpowiedzieć może każdy. Jeśli zapytasz przez megafon „kto jest serwerem plików FILESRV?”, a prawdziwy serwer milczy (bo literówka), może odezwać się ktoś, kto tylko udaje — i dostanie Twoje poświadczenia. Tak działa nadużywanie LLMNR i NBT-NS w sieciach Windows.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Multicast IPv4 na adres MAC', ...img('ns02-04-mcast-mac.svg', 'Adres 224.0.0.251 rozpisany na 32 bity: pierwsze 4 bity 1110 oznaczają multicast, kolejnych 5 bitów ginie, dolne 23 bity trafiają do adresu MAC 01:00:5e:00:00:fb. Ramka z ostrzeżeniem o niejednoznaczności 32 do 1 i regułą dla IPv6: MAC 33:33 plus dolne 32 bity.'),
    body:
`Ramka do grupy multicast musi mieć docelowy adres MAC, a ten wyznacza się z adresu grupy. Przykłady (sprawdź obliczenia bit po bicie):`,
    code:
['224.0.0.1', '224.0.0.5', '224.0.0.18', '224.0.0.251', '224.0.0.252', '239.255.255.250', '232.10.20.30', '225.128.1.2'].map((ip) => `${ip.padEnd(18)} → ${mac4(ip)}   ${cls(ip)}`).join('\n') + `

Reguła: MAC = 01:00:5e + (drugi oktet AND 0x7f) + trzeci + czwarty oktet
Niejednoznaczność: 2^5 = 32 adresy IP dzielą jeden MAC (np. 224.0.0.251 i 239.128.0.251)

IPv6 (33:33 + dolne 32 bity):
ff02::1     → 33:33:00:00:00:01     ff02::fb    → 33:33:00:00:00:fb
ff02::2     → 33:33:00:00:00:02     ff02::1:3   → 33:33:00:01:00:03` },

  { type: 'CODE_BASIC', heading: 'Multicast w Javie: nadawca i odbiorca', body:
`Java obsługuje multicast UDP przez \`MulticastSocket\` lub \`DatagramChannel\` (NIO). Odbiorca musi dołączyć do grupy na konkretnym interfejsie — to wysyła IGMP/MLD join.`,
    code:
`import java.net.*;
import java.nio.charset.StandardCharsets;

public class McastDemo {
    static final InetAddress GROUP = InetAddress.ofLiteral("239.1.2.3");    // Java 22+; wcześniej getByName
    static final int PORT = 4446;

    static void receiver(String ifName) throws Exception {
        NetworkInterface nif = NetworkInterface.getByName(ifName);        // np. "eth0"
        try (MulticastSocket s = new MulticastSocket(PORT)) {
            s.joinGroup(new InetSocketAddress(GROUP, PORT), nif);         // → IGMP Membership Report
            byte[] buf = new byte[1500];
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            s.receive(p);
            System.out.println("od " + p.getAddress() + ": " + new String(p.getData(), 0, p.getLength(), StandardCharsets.UTF_8));
            s.leaveGroup(new InetSocketAddress(GROUP, PORT), nif);        // → IGMP Leave
        }
    }

    static void sender() throws Exception {
        try (MulticastSocket s = new MulticastSocket()) {
            s.setTimeToLive(1);                                           // tylko lokalny segment (nie przez routery)
            byte[] msg = "ping grupy".getBytes(StandardCharsets.UTF_8);
            s.send(new DatagramPacket(msg, msg.length, GROUP, PORT));
        }
    }
}
// Broadcast w Javie: DatagramSocket.setBroadcast(true) i adres 255.255.255.255 lub broadcast podsieci.` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab: mDNS i SSDP w Twojej sieci domowej', ...img('ns02-04-igmp.svg', 'Router w roli querier, przełącznik z IGMP snooping i cztery hosty; H1 i H3 wysłały IGMP join do grupy 239.1.1.1 i tylko do nich trafia strumień, H2 i H4 są niezapisane. Podpis: bez snoopingu przełącznik zalałby strumieniem wszystkie porty.'),
    body:
`Ćwiczenie z planu lekcji — wyłącznie we własnej sieci domowej (to Twój ruch i Twoje urządzenia):
1. **Uruchom przechwytywanie** na swoim komputerze w sieci domowej: Wireshark z filtrem przechwytywania \`udp port 5353 or udp port 1900 or igmp\` (lub \`tcpdump -i <if> -nn -w home.pcap 'udp port 5353 or udp port 1900 or igmp'\`) na 5–10 minut.
2. **Pobudź ruch**: otwórz w telefonie aplikację, która szuka urządzeń (np. udostępnianie ekranu, drukowanie), albo w Linuksie \`avahi-browse -at\`.
3. **mDNS** (filtr \`mdns\`, 224.0.0.251/ff02::fb, port 5353): zapytania typu PTR \`_googlecast._tcp.local\`, \`_ipp._tcp.local\`, \`_airplay._tcp.local\` i odpowiedzi z nazwami urządzeń, adresami, portami usług.
4. **SSDP** (filtr \`ssdp\`, 239.255.255.250:1900): ogłoszenia \`NOTIFY\` z polami \`LOCATION\` (URL opisu urządzenia UPnP) i \`SERVER\` (system, wersja).
5. **IGMP** (filtr \`igmp\`): raporty członkostwa wysyłane przez urządzenia (Membership Report) i zapytania routera (Membership Query).
6. **Zinwentaryzuj urządzenia**: zrób tabelę adres IP → nazwa → usługi → producent (OUI z lekcji 02). Zwykle znajdziesz telewizor, drukarkę, router, telefony.
7. **Wniosek obrońcy**: zapisz, ile informacji o sieci ujawniają te protokoły każdemu, kto jest w tym samym segmencie (nazwy, modele, wersje firmware) — dlatego w sieciach firmowych ogranicza się je do potrzebnych segmentów.
8. **Usuń plik** po ćwiczeniu lub przechowuj go poza repozytorium.` },

  { type: 'NOTE', heading: 'Dobrze znane adresy multicast i broadcast', body:
`Adresy, które zobaczysz w każdym przechwyceniu — warto rozpoznawać je odruchowo:`,
    code:
`adres                    protokół / znaczenie                 port      uwagi bezpieczeństwa
255.255.255.255          broadcast ograniczony (DHCP)          67/68     rogue DHCP — DHCP snooping
x.x.x.255 (/24)          broadcast ukierunkowany               —         routery blokują (Smurf)
224.0.0.1 / ff02::1      wszystkie hosty / węzły                —
224.0.0.2 / ff02::2      wszystkie routery                      —
224.0.0.5, .6 / ff02::5  OSPF (routery, DR)                     IP 89    uwierzytelnianie OSPF
224.0.0.18               VRRP                                   IP 112   ogłoszenia mastera
224.0.0.22               IGMPv3 (raporty)                       IP 2
224.0.0.251 / ff02::fb   mDNS (Bonjour, Avahi)                  5353     ujawnia nazwy i usługi
224.0.0.252 / ff02::1:3  LLMNR (Windows)                        5355     przechwytywanie poświadczeń → wyłączyć
x.x.x.255 (NetBIOS)      NBT-NS (NetBIOS Name Service)          137      jw. → wyłączyć
239.255.255.250          SSDP / UPnP                            1900     wzmacnianie DDoS, ujawnianie urządzeń
ff02::1:2                serwery/relay DHCPv6                   547      DHCPv6 Guard` },

  { type: 'CODE_PRACTICAL', heading: 'Narzędzia: grupy, routing multicastu i diagnoza', body:
`Gdzie sprawdzić członkostwo w grupach, trasy multicast i statystyki broadcastu:`,
    code:
`# Linux: do jakich grup należą interfejsy
ip maddr show dev eth0              # inet 224.0.0.251, inet6 ff02::fb, link 01:00:5e:00:00:fb ...
cat /proc/net/igmp
ss -ulnp | grep -E '5353|1900|5355' # kto nasłuchuje mDNS/SSDP/LLMNR

# Windows
netsh interface ipv4 show joins
Get-NetUDPEndpoint -LocalPort 5353,5355,1900

# Wysyłanie testowe (lab): socat do grupy 239.1.2.3
socat - UDP4-DATAGRAM:239.1.2.3:4446,ip-multicast-ttl=1
socat UDP4-RECV:4446,ip-add-membership=239.1.2.3:eth0 -

# Przełącznik Cisco
show ip igmp snooping groups
show interfaces counters | include Broad    # liczniki broadcastów na portach (burza?)
storm-control broadcast level 1.00          # limit ruchu rozgłoszeniowego na porcie

# Hazelcast: wykrywanie członków bez multicastu (chmura/Kubernetes)
# hazelcast.network.join.multicast.enabled: false
# hazelcast.network.join.kubernetes.enabled: true` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz: od klastra Javy po Responder i DDoS', ...img('ns02-04-anycast.svg', 'Trzy lokalizacje resolvera DNS we Frankfurcie, Warszawie i Londynie z tym samym adresem 9.9.9.9; użytkownicy z Krakowa, Paryża i Dublina trafiają do najbliższej lokalizacji wybranej przez BGP. Podpis: zastosowania anycastu w serwerach głównych DNS, publicznych resolverach, CDN i ochronie przed DDoS.'),
    body:
`**Developer / SRE:**
- **Wykrywanie węzłów klastra** (Hazelcast, JGroups/Infinispan, stare Eureka/EhCache) przez multicast działa w biurze, ale nie w AWS/Azure/GCP i zwykle nie w Kubernetesie — w chmurze używa się wykrywania przez API platformy, DNS lub listę adresów.
- **Usługi lokalne**: Spring Boot DevTools, drukarki, Chromecast — mDNS/SSDP; w kontenerach multicast wymaga sieci host lub macvlan.
- **Anycast** sprawia, że \`8.8.8.8\` czy \`1.1.1.1\` odpowiadają z lokalizacji blisko Ciebie; CDN i DNS dostawców chmury działają tak samo — stąd różne odpowiedzi i opóźnienia z różnych regionów.
- **Broadcast** w dużych sieciach — przy tysiącach hostów w jednym VLAN-ie ruch rozgłoszeniowy (ARP, DHCP, mDNS) zajmuje zauważalną część pasma i CPU.

**Blue team:**
- **LLMNR/NBT-NS/mDNS poisoning** (ATT&CK T1557.001): gdy DNS nie zna nazwy (literówka, nieistniejący udział), Windows pyta multicastem/broadcastem; napastnik w segmencie odpowiada „to ja” i przechwytuje skrót NetNTLMv2 (narzędzia typu Responder). Obrona: **wyłączenie LLMNR i NBT-NS przez GPO**, SMB signing, ograniczenie NTLM, monitorowanie odpowiedzi LLMNR z nieoczekiwanych hostów.
- **Ujawnianie informacji** — mDNS/SSDP rozgłaszają nazwy, modele i wersje urządzeń; w sieciach firmowych i gościnnych ogranicza się je VLAN-ami i filtrami.
- **Wzmacnianie DDoS** — usługi UDP odpowiadające dużym pakietem na mały (SSDP, wcześniej NTP monlist, DNS, memcached) wystawione do Internetu są wykorzystywane jako wzmacniacze. Nie wystawiaj UPnP/SSDP na WAN.
- **Burze rozgłoszeniowe** — pętle L2 i błędne urządzenia; obrona: STP, storm control, monitoring liczników broadcastu.
- **Anycast jako obrona** — rozproszenie ataku DDoS na wiele lokalizacji (scrubbing centers, CDN).` },

  { type: 'PITFALL', heading: 'Częste nieporozumienia', body:
`- **„Broadcast przechodzi przez router”** — nie; broadcast ograniczony nigdy, ukierunkowany — zwykle zablokowany (\`no ip directed-broadcast\` to domyślne ustawienie). DHCP działa między sieciami dzięki **DHCP relay**, a nie broadcastowi przez router.
- **„IPv6 ma broadcast”** — nie ma; zastępuje go multicast (\`ff02::1\`) i adresy solicited-node.
- **„Multicast zadziała w chmurze”** — w większości VPC nie (wyjątki: specjalne funkcje, np. AWS Transit Gateway multicast). Klastry Java trzeba konfigurować z innym mechanizmem wykrywania.
- **Brak IGMP snooping** — multicast wideo lub z klastrów zalewa wszystkie porty jak broadcast i obciąża całą sieć.
- **TTL multicastu** — domyślny TTL 1 w Javie (\`MulticastSocket\`) ogranicza ruch do segmentu; zwiększanie bez potrzeby rozsyła ruch dalej (jeśli routery to przepuszczą).
- **Adres 255.255.255.255 vs broadcast podsieci** — aplikacja wysyłająca na ograniczony broadcast działa tylko w lokalnym segmencie, niezależnie od maski.
- **Anycast z protokołami stanowymi** — przy zmianie trasy w trakcie połączenia TCP klient może trafić do innego serwera; anycast najlepiej sprawdza się dla UDP (DNS) i krótkich połączeń lub z odpowiednią architekturą.
- **„LLMNR jest potrzebny”** — w sieciach z działającym DNS zwykle nie; jego wyłączenie to jedna z najtańszych i najskuteczniejszych poprawek bezpieczeństwa w Windows.` },

  { type: 'CODE_WRONG', heading: 'Źle: klaster Hazelcast z wykrywaniem przez multicast „na produkcję”', body:
`Konfiguracja działa na laptopie i w biurze, a po wdrożeniu do Kubernetesa każdy pod tworzy osobny „klaster” z jednego członka (multicast nie dociera) — dane w cache się rozjeżdżają, a w sieci płaskiej każdy, kto dołączy do grupy, może podłączyć się do klastra.`,
    code:
`# hazelcast.yaml
hazelcast:
  cluster-name: dev
  network:
    join:
      multicast:
        enabled: true
        multicast-group: 224.2.2.3
        multicast-port: 54327
# Efekty: w K8s/AWS brak wykrycia członków (split-brain od startu),
# w sieci biurowej — każdy host z Hazelcastem i tą samą nazwą klastra może dołączyć.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: wykrywanie przez API platformy i zabezpieczony klaster', body:
`W Kubernetesie członkowie są wykrywani przez API (lub DNS usługi headless), klaster ma unikatową nazwę, a komunikację ogranicza NetworkPolicy (i TLS w edycji, która go wspiera).`,
    code:
`# hazelcast.yaml
hazelcast:
  cluster-name: orders-cache-prod
  network:
    join:
      multicast:
        enabled: false
      kubernetes:
        enabled: true
        service-name: orders-cache      # usługa headless
        namespace: orders

# NetworkPolicy: ruch na port Hazelcasta tylko od podów tej samej aplikacji
# podSelector: app=orders  →  ingress from podSelector app=orders, port 5701
# Windows/AD po stronie sieci biurowej (GPO):
#   Computer Configuration → Administrative Templates → Network → DNS Client →
#   „Turn off multicast name resolution” = Enabled (wyłącza LLMNR); NetBIOS over TCP/IP wyłączony` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy który tryb ma sens', body:
`- **Unicast** — domyślnie dla wszystkiego, co ma konkretnego odbiorcę (HTTP, bazy, kolejki).
- **Broadcast** — tylko tam, gdzie nie znasz jeszcze odbiorcy w segmencie i protokół tego wymaga (ARP, DHCP Discover). Nie projektuj nowych protokołów aplikacyjnych na broadcaście.
- **Multicast** — dystrybucja tych samych danych do wielu odbiorców w sieci, którą kontrolujesz: IPTV, giełdowe strumienie danych rynkowych, protokoły routingu i redundancji (OSPF, VRRP), wykrywanie usług w LAN (mDNS). Wymaga IGMP snooping i przemyślanej segmentacji.
- **Anycast** — usługi globalne o krótkich, bezstanowych interakcjach: DNS, CDN, NTP, ochrona przed DDoS; wymaga BGP i własnej przestrzeni adresowej (lub usługi dostawcy, np. globalnych load balancerów chmury).
- **W chmurze** — zamiast multicastu: wykrywanie przez API/DNS, kolejki pub/sub (Kafka, SNS/SQS), service discovery.
- **Bezpieczeństwo** — ograniczaj protokoły rozgłoszeniowe rozwiązywania nazw (LLMNR, NBT-NS), filtruj mDNS/SSDP między segmentami, nie wystawiaj UDP z możliwością wzmocnienia do Internetu.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Unicast: jeden odbiorca; broadcast: wszyscy w segmencie (255.255.255.255, broadcast podsieci, MAC ff:ff:ff:ff:ff:ff); multicast: zapisani do grupy; anycast: najbliższy z wielu.
- Multicast IPv4: 224.0.0.0/4 (224.0.0.0/24 lokalny łącza, 239.0.0.0/8 administracyjny, 232.0.0.0/8 SSM); IPv6: ff00::/8 z zasięgiem w czwartej cyfrze (ff02 łącze).
- MAC multicastu: 01:00:5e + dolne 23 bity (IPv4, niejednoznaczność 32:1); 33:33 + dolne 32 bity (IPv6).
- IGMP/MLD zgłaszają członkostwo; snooping ogranicza ruch grupy do zainteresowanych portów.
- Broadcast nie przechodzi przez routery (DHCP relay); IPv6 nie ma broadcastu.
- W chmurze multicast zwykle nie działa — klastry Java konfiguruj z wykrywaniem przez API/DNS.
- Bezpieczeństwo: wyłącz LLMNR/NBT-NS, ogranicz mDNS/SSDP, nie wystawiaj wzmacniaczy UDP, storm control; anycast pomaga w obronie przed DDoS.
- Następna lekcja: **VLAN, trunking i podstawy STP**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const ip of ['224.0.0.5', '224.0.0.252', '239.255.255.250', '232.1.2.3', '238.200.17.99', '225.255.0.1']) {
  ex(`Wyznacz adres MAC dla grupy multicast ${ip} i sklasyfikuj adres.`, '01:00:5e + (drugi oktet AND 0x7f) + trzeci + czwarty oktet.', `MAC: ${mac4(ip)}. Typ: ${cls(ip)}.`);
}
for (const [ip, p] of [['192.168.1.77', 24], ['10.20.30.40', 22], ['172.16.5.200', 25], ['10.0.0.9', 30]]) {
  ex(`Jaki jest adres broadcast ukierunkowany podsieci, do której należy ${ip}/${p}? Czy router domyślnie przekaże pakiet na ten adres z innej sieci?`, 'Broadcast = wszystkie bity hosta ustawione na 1 (lekcja o masce).', `Broadcast: ${bcast(ip, p)}. Routery domyślnie nie przekazują broadcastu ukierunkowanego (ochrona przed atakami typu Smurf).`);
}
const more = [
  ['Wykonaj Packet Lab z lekcji w swojej sieci domowej i przygotuj tabelę urządzeń wykrytych przez mDNS/SSDP: IP, nazwa, usługi, producent.', 'Filtry mdns, ssdp; pola dns.ptr.domain_name, ssdp.server.', 'Przykład: 192.168.1.20 — „Salon-TV._googlecast._tcp.local” — Chromecast (OUI Google); 192.168.1.30 — „HP-LaserJet._ipp._tcp.local” — drukarka; 192.168.1.1 — SSDP SERVER: „Linux/5.x UPnP/1.0 …” — router z włączonym UPnP. Wniosek: każdy w sieci zna modele i wersje urządzeń.'],
  ['Uruchom nadawcę i odbiorcę multicastu z lekcji (Java) w dwóch kontenerach na tej samej sieci Dockera i przechwyć ruch. Jaki adres MAC ma ramka i co pokazuje ip maddr w kontenerze odbiorcy?', 'Sieć bridge przekazuje multicast; odbiorca musi dołączyć do grupy.', `Ramka do 239.1.2.3 ma MAC ${mac4('239.1.2.3')}; w kontenerze odbiorcy ip maddr show eth0 zawiera inet 239.1.2.3 (po joinGroup). W przechwyceniu widać IGMP Membership Report wysłany przy dołączaniu.`],
  ['Przenieś test multicastu do dwóch instancji EC2 w tym samym VPC (lub opisz). Dlaczego odbiorca nic nie dostanie i jak skonfigurować klaster Hazelcast w AWS?', 'Multicast w VPC nie jest wspierany bez specjalnych mechanizmów.', 'Standardowy VPC nie przekazuje multicastu między instancjami (wyjątek: Transit Gateway z obsługą multicast). Hazelcast w AWS: discovery przez wtyczkę AWS (tagi, Security Group) lub stała lista członków; w EKS — wykrywanie przez Kubernetes API.'],
  ['Sprawdź w Windows (lub opisz dla domeny), czy LLMNR i NetBIOS over TCP/IP są włączone, i przygotuj zmianę GPO, która je wyłącza. Jakie jest ryzyko pozostawienia ich włączonych?', 'GPO: DNS Client → Turn off multicast name resolution; NetBIOS: ustawienia karty/DHCP option 001.', 'LLMNR: GPO „Turn off multicast name resolution” = Enabled. NBT-NS: wyłączenie NetBIOS over TCP/IP w ustawieniach karty, przez skrypt/registry (NetbiosOptions = 2) lub opcję DHCP. Ryzyko: napastnik w segmencie odpowiada na zapytania o nieistniejące nazwy i przechwytuje skróty NetNTLMv2 (łamanie offline lub relay), co prowadzi do przejęcia kont.'],
  ['Napisz regułę detekcji (pseudokod SIEM/Zeek) dla podejrzanych odpowiedzi LLMNR/NBT-NS w sieci, w której protokoły te powinny być wyłączone.', 'Każda odpowiedź LLMNR (UDP 5355) lub NBT-NS (UDP 137) jest podejrzana, gdy polityka je wyłącza.', 'Alert: ruch UDP z portu źródłowego 5355 (odpowiedź LLMNR) lub odpowiedzi NBNS z hosta, który nie jest serwerem WINS; w szczególności jeden host odpowiadający na wiele różnych nazw w krótkim czasie. Dodatkowo: logowania NTLM z kont użytkowników do nietypowych hostów tuż po zapytaniach LLMNR (korelacja).'],
  ['Wyjaśnij, dlaczego SSDP wystawiony do Internetu może posłużyć jako wzmacniacz DDoS. Jak sprawdzić, czy Twój router domowy nie odpowiada na SSDP od strony WAN?', 'Mały pakiet M-SEARCH, duża odpowiedź; podszywanie adresu źródłowego w UDP.', 'Napastnik wysyła mały M-SEARCH z adresem źródłowym ofiary; urządzenie odsyła ofierze wielokrotnie większą odpowiedź (współczynnik wzmocnienia rzędu kilkudziesięciu). Sprawdzenie: wyłącz UPnP w routerze, a test z zewnątrz (np. z serwera, za zgodą — Twojego publicznego IP) nie powinien dawać odpowiedzi na UDP 1900; skanery publiczne (np. Shodan) pokazują, czy adres jest widoczny.'],
  ['Policz, ile adresów IPv4 multicast mapuje się na adres MAC 01:00:5e:7f:ff:fa i podaj trzy z nich.', 'Niejednoznaczność 32:1; zmieniają się bity 5–9.', `32 adresy. Przykłady: 239.255.255.250 (SSDP), 224.127.255.250, 225.255.255.250, 239.127.255.250 — wszystkie mają dolne 23 bity 0x7ffffa (drugi oktet AND 0x7f = 0x7f, trzeci 0xff, czwarty 0xfa).`],
  ['Opisz, jak działa DHCP między podsieciami, skoro broadcast DHCP Discover nie przechodzi przez router.', 'DHCP relay (ip helper-address).', 'Router (lub przełącznik L3) z skonfigurowanym DHCP relay (Cisco: ip helper-address 10.0.0.10) odbiera broadcast Discover na interfejsie klienta i przesyła go unicastem do serwera DHCP, wpisując w pole giaddr adres swojego interfejsu — serwer wie, z której puli przydzielić adres, i odpowiada do relay, który przekazuje odpowiedź klientowi.'],
  ['Na przełączniku ustaw storm control dla broadcastu na portach dostępowych. Jak dobrać próg i jaką akcję wybrać?', 'Próg w % pasma lub pakietach/s; akcje: odrzucanie nadmiaru, trap, shutdown.', 'Przykład: storm-control broadcast level 1.00 0.50 (próg włączenia 1%, wyłączenia 0,5% pasma), storm-control action trap (alert SNMP) lub shutdown dla portów użytkowników. Próg dobiera się z baseline (normalnie broadcast to ułamek procenta); zbyt niski może blokować legalny ruch (np. DHCP przy starcie wielu stacji).'],
  ['Wyjaśnij, jak anycast pomaga w obronie przed DDoS i jakie ma ograniczenia dla połączeń TCP.', 'Ruch rozkłada się na wiele lokalizacji; zmiana trasy w trakcie sesji.', 'Ten sam adres ogłaszany z wielu lokalizacji powoduje, że ruch ataku z różnych regionów trafia do różnych centrów (rozproszenie, lokalne filtrowanie), zamiast skumulować się w jednym. Ograniczenie: przy zmianie trasy BGP w trakcie długiego połączenia TCP klient może trafić do innego serwera bez stanu sesji — dlatego anycast stosuje się głównie dla DNS (UDP), krótkich połączeń lub z warstwą, która ten problem obsługuje (CDN).'],
  ['W Wiresharku znajdź zapytanie IGMP Membership Query od routera domowego i odczytaj wersję IGMP i interwał. Co by się stało, gdyby w sieci nie było żadnego routera wysyłającego zapytania, a przełącznik miał włączony snooping?', 'Querier jest potrzebny, by snooping utrzymywał stan grup.', 'Typowo IGMPv2/v3, Max Resp Time 10 s, zapytania co ~125 s. Bez queriera hosty przestają odświeżać członkostwo, wpisy snoopingu wygasają i ruch multicast do grup może przestać docierać (lub zależnie od implementacji — być zalewany). Rozwiązanie: włączyć IGMP querier na przełączniku.'],
  ['Porównaj, ile hostów musi przetworzyć jedno zapytanie ARP (broadcast) w VLAN-ie z 1000 hostów i jedno zapytanie ND (solicited-node) w IPv6.', 'Broadcast trafia do wszystkich, solicited-node zwykle do jednego.', 'ARP: wszystkie 999 pozostałych hostów odbiera i przetwarza ramkę (przerwanie, porównanie adresu). ND: z MLD snooping ramka trafia tylko do hosta(-ów) zapisanych do danej grupy solicited-node — zwykle jednego; bez snoopingu karty i tak odrzucają ją sprzętowo po MAC (33:33:ff:…), nie angażując systemu.'],
  ['Zaprojektuj segmentację domowej lub firmowej sieci tak, by urządzenia IoT (telewizor, drukarka) były w osobnym VLAN-ie, a mimo to drukowanie i Chromecast działały. Co z mDNS?', 'mDNS nie przechodzi przez router — potrzebny reflektor/gateway mDNS.', 'VLAN użytkowników i VLAN IoT; firewall: ruch z VLAN użytkowników do IoT tylko na potrzebne porty (IPP 631, Chromecast 8008/8009), z IoT do użytkowników — tylko odpowiedzi. mDNS: reflektor (np. Avahi z enable-reflector lub funkcja mDNS gateway w kontrolerze Wi-Fi/routerze) przekazujący ogłoszenia między VLAN-ami, najlepiej z filtrowaniem typów usług.'],
  ['Wyjaśnij różnicę między multicastem administracyjnym 239.0.0.0/8 a 224.0.0.0/24 i podaj, który zakres wybierzesz dla aplikacji wewnętrznej rozsyłającej dane rynkowe w sieci firmy.', 'Zasięg i routowanie.', '224.0.0.0/24 to multicast lokalny łącza — nie jest routowany (TTL 1), zarezerwowany dla protokołów sieciowych (OSPF, VRRP, mDNS). 239.0.0.0/8 to zakres administracyjny (odpowiednik adresów prywatnych), routowany wewnątrz organizacji, filtrowany na brzegu. Dla aplikacji firmowej — adres z 239.0.0.0/8 (np. 239.10.1.1), z ustalonym TTL i konfiguracją PIM na routerach.'],
];
for (const [q, h, a] of more) ex(q, h, a);
const extra = [
  ['Wyznacz adres MAC dla grup IPv6 ff02::1, ff02::fb i ff05::1:3.', 'MAC = 33:33 + dolne 32 bity adresu.', '33:33:00:00:00:01, 33:33:00:00:00:fb, 33:33:00:01:00:03.'],
  ['Wyjaśnij, dlaczego ping na adres broadcast podsieci (np. ping -b 192.168.1.255) często nie dostaje odpowiedzi od współczesnych systemów.', 'Systemy domyślnie ignorują echo na broadcast (ochrona przed Smurf).', 'Linux ma domyślnie net.ipv4.icmp_echo_ignore_broadcasts = 1, Windows również nie odpowiada na echo do broadcastu. To obrona przed atakiem Smurf, w którym zapytania z podrobionym źródłem na adres broadcast generowały lawinę odpowiedzi do ofiary.'],
  ['Zaprojektuj test, który pokaże różnicę między przełącznikiem z IGMP snooping i bez (np. bridge Linuksa: multicast_snooping 0/1).', 'ip link set br0 type bridge mcast_snooping 1; trzy namespace’y, jeden zapisany do grupy.', 'Bez snoopingu (mcast_snooping 0): tcpdump w niezapisanym namespace widzi strumień do 239.1.2.3. Ze snoopingiem i querierem (mcast_querier 1): tylko zapisany namespace otrzymuje ruch; bridge mdb show pokazuje porty członków grupy.'],
  ['Napisz w Javie metodę zwracającą adres MAC dla dowolnego adresu multicast IPv4 lub IPv6 (InetAddress).', 'Rozróżnij Inet4Address i Inet6Address; pracuj na bajtach.', 'static String mcastMac(InetAddress a) {\n  byte[] b = a.getAddress();\n  if (!a.isMulticastAddress()) throw new IllegalArgumentException();\n  return a instanceof Inet4Address\n    ? String.format("01:00:5e:%02x:%02x:%02x", b[1] & 0x7f, b[2] & 0xff, b[3] & 0xff)\n    : String.format("33:33:%02x:%02x:%02x:%02x", b[12] & 0xff, b[13] & 0xff, b[14] & 0xff, b[15] & 0xff);\n}'],
  ['Przygotuj listę kontrolną (5 punktów) ograniczania ryzyka protokołów rozgłoszeniowych i multicastowych w sieci firmowej.', 'Rozwiązywanie nazw, UPnP, segmentacja, monitoring, storm control.', '1) Wyłączyć LLMNR i NBT-NS (GPO), zostawić DNS. 2) Wyłączyć UPnP/SSDP na routerach i urządzeniach brzegowych; blokować SSDP z Internetu. 3) Ograniczać mDNS/SSDP do segmentów, które ich potrzebują (VLAN IoT, reflektory z filtrem). 4) Storm control i IGMP snooping na przełącznikach. 5) Detekcje: odpowiedzi LLMNR/NBNS, nietypowe źródła RA/DHCP, skoki broadcastu.'],
  ['Sprawdź, które publiczne resolvery DNS korzystają z anycastu, i porównaj RTT z dwóch lokalizacji (np. laptop i serwer w chmurze w innym regionie). Co pokazuje traceroute?', 'Ten sam adres, różne ścieżki i różne lokalizacje końcowe.', 'Resolvery 1.1.1.1, 8.8.8.8, 9.9.9.9 są anycastowe: z Polski RTT zwykle kilka–kilkanaście ms, z innego kontynentu podobnie niski, bo odpowiada lokalna instancja. traceroute kończy się w różnych miastach (widoczne w nazwach routerów lub w identyfikatorze lokalizacji, np. dig CH TXT id.server @1.1.1.1 zwraca kod lotniska).'],
];
for (const [q, h, a] of extra) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111409);
const mips = ['224.0.0.1', '224.0.0.2', '224.0.0.5', '224.0.0.9', '224.0.0.18', '224.0.0.251', '224.0.0.252', '239.255.255.250', '239.1.2.3', '232.5.6.7', '225.10.20.30', '238.129.4.5', '239.192.0.1', '224.1.1.1'];
for (const ip of mips) {
  const m = mac4(ip);
  const o = ip.split('.').map(Number);
  add(`Jaki adres MAC ma ramka wysyłana do grupy ${ip}?`, m, [`01:00:5e:${hx(o[1])}:${hx(o[2])}:${hx(o[3])}` === m ? `01:00:5e:${hx((o[1] | 0x80) & 0xff)}:${hx(o[2])}:${hx(o[3])}` : `01:00:5e:${hx(o[1])}:${hx(o[2])}:${hx(o[3])}`, `33:33:${hx(o[0])}:${hx(o[1])}:${hx(o[2])}:${hx(o[3])}`, 'ff:ff:ff:ff:ff:ff'], `01:00:5e + (drugi oktet ${o[1]} AND 0x7f = ${o[1] & 0x7f}) + ${o[2]} + ${o[3]} → ${m}.`);
}
const clsAll = ['unicast', 'multicast lokalny łącza (224.0.0.0/24, nie routowany)', 'multicast administracyjny (239.0.0.0/8, wewnątrz organizacji)', 'multicast SSM (232.0.0.0/8)', 'multicast (224.0.0.0/4)', 'broadcast ograniczony (limited broadcast)'];
for (const ip of ['224.0.0.5', '239.255.255.250', '232.1.1.1', '226.3.4.5', '255.255.255.255', '223.255.255.1', '240.0.0.1', '10.0.0.255']) {
  let c = cls(ip);
  if (ip === '240.0.0.1') c = 'unicast';
  const exp = ip === '240.0.0.1' ? '240.0.0.0/4 to zakres zarezerwowany (dawna klasa E) — nie multicast; w praktyce nieużywany, ale formalnie nie jest grupą.' : ip === '10.0.0.255' ? 'To zwykły adres (unicast) albo broadcast podsieci, jeśli maska to /24 — bez maski nie wiadomo; multicastem nie jest.' : ip === '223.255.255.1' ? 'Tuż poniżej 224.0.0.0 — zwykły adres unicast.' : `Zakres: ${c}.`;
  add(`Jak sklasyfikować adres ${ip}?`, c, clsAll.filter((x) => x !== c).slice(0, 3), exp);
}
for (const [ip, p] of [['192.168.1.77', 24], ['10.20.30.40', 22], ['172.16.5.200', 25], ['10.0.0.9', 30], ['192.168.100.130', 26], ['10.1.1.1', 16]]) {
  const b = bcast(ip, p);
  const n = intIp(Math.floor(ipInt(ip) / 2 ** (32 - p)) * 2 ** (32 - p));
  add(`Jaki jest adres broadcast podsieci dla ${ip}/${p}?`, b, [n, '255.255.255.255', intIp(ipInt(b) + 1)], `Bity hosta na 1 → ${b}.`);
}
const v6 = [['ff02::1', 'wszystkie węzły w łączu'], ['ff02::2', 'wszystkie routery w łączu'], ['ff02::fb', 'mDNS'], ['ff02::1:3', 'LLMNR'], ['ff02::5', 'routery OSPFv3'], ['ff02::1:2', 'serwery i relay DHCPv6']];
const v6All = v6.map((x) => x[1]);
for (const [a, d] of v6) add(`Co oznacza adres multicast IPv6 ${a}?`, d, v6All.filter((x) => x !== d).slice(0, 3), `${a} — ${d}.`);
const scopes = [['ff02::1', 'łącze (link-local)'], ['ff05::1:3', 'ośrodek (site-local)'], ['ff0e::101', 'globalny'], ['ff01::1', 'interfejs (node-local)']];
const scAll = scopes.map((x) => x[1]);
for (const [a, d] of scopes) add(`Jaki zasięg ma adres multicast ${a}?`, d, scAll.filter((x) => x !== d), `Czwarta cyfra adresu (${a.slice(3, 4)}) oznacza zasięg: ${d}.`);
const wk = [['224.0.0.251', 'mDNS'], ['224.0.0.252', 'LLMNR'], ['224.0.0.5', 'OSPF (wszystkie routery OSPF)'], ['224.0.0.18', 'VRRP'], ['239.255.255.250', 'SSDP / UPnP'], ['224.0.0.1', 'wszystkie hosty']];
const wkAll = wk.map((x) => x[1]);
for (const [a, d] of wk) add(`Jaki protokół lub grupa używa adresu ${a}?`, d, wkAll.filter((x) => x !== d).slice(0, 3), `${a} — ${d}.`);
const ports = [['5353', 'mDNS'], ['5355', 'LLMNR'], ['1900', 'SSDP'], ['137', 'NBT-NS (NetBIOS Name Service)'], ['67', 'serwer DHCP']];
const pAll = ports.map((x) => x[1]);
for (const [p, d] of ports) add(`Która usługa używa portu UDP ${p}?`, d, pAll.filter((x) => x !== d).slice(0, 3), `UDP ${p} — ${d}.`);
const facts = [
  ['Czy broadcast IPv4 przechodzi przez router?', 'Nie — broadcast ograniczony nigdy, a ukierunkowany jest domyślnie blokowany', ['Tak, zawsze', 'Tylko w IPv6', 'Tylko przy TTL 255'], 'DHCP między sieciami działa dzięki relay.'],
  ['Czym IPv6 zastępuje broadcast?', 'Multicastem (np. ff02::1 i adresami solicited-node)', ['Anycastem', 'Unicastem do bramy', 'IPv6 nie potrzebuje adresowania grupowego'], 'IPv6 nie ma broadcastu.'],
  ['Do czego służy IGMP?', 'Do zgłaszania przez hosty członkostwa w grupach multicast IPv4', ['Do rozwiązywania adresów MAC', 'Do routingu unicast', 'Do szyfrowania multicastu'], 'W IPv6 tę rolę pełni MLD.'],
  ['Co robi przełącznik z IGMP snooping?', 'Wysyła ruch grupy tylko na porty, na których są jej członkowie', ['Blokuje cały multicast', 'Zamienia multicast na unicast do routera', 'Szyfruje ruch grupy'], 'Bez snoopingu multicast jest zalewany jak broadcast.'],
  ['Ile adresów IPv4 multicast mapuje się na jeden adres MAC?', '32', ['1', '256', '8'], '5 bitów adresu IP nie mieści się w 23 bitach MAC.'],
  ['Dlaczego wykrywanie członków klastra przez multicast zwykle nie działa w chmurze publicznej?', 'Bo standardowe sieci VPC nie przekazują ruchu multicast między instancjami', ['Bo chmura blokuje UDP', 'Bo multicast wymaga IPv6', 'Bo Java nie wspiera multicastu w chmurze'], 'Używa się wykrywania przez API/DNS.'],
  ['Czym jest anycast?', 'Przypisaniem tego samego adresu wielu serwerom, przy czym routing kieruje klienta do najbliższego', ['Wysłaniem do wszystkich', 'Wysłaniem do zapisanych do grupy', 'Odmianą broadcastu w IPv6'], 'Typowe dla DNS i CDN.'],
  ['Dlaczego anycast pomaga w obronie przed DDoS?', 'Rozprasza ruch ataku na wiele lokalizacji zamiast kumulować go w jednej', ['Bo szyfruje ruch', 'Bo blokuje UDP', 'Bo zmienia adres serwera co sekundę'], 'Każda lokalizacja przyjmuje część ruchu.'],
  ['Na czym polega nadużycie LLMNR/NBT-NS?', 'Napastnik w segmencie odpowiada na rozgłoszeniowe zapytania o nazwy i przechwytuje poświadczenia NTLM', ['Na przepełnieniu tablicy MAC', 'Na podmianie certyfikatu TLS', 'Na zmianie trasy BGP'], 'Obrona: wyłączenie LLMNR i NBT-NS.'],
  ['Jaka jest najprostsza skuteczna obrona przed przechwytywaniem przez LLMNR?', 'Wyłączenie LLMNR (GPO) i NBT-NS w sieci z działającym DNS', ['Zwiększenie TTL', 'Włączenie broadcastu ukierunkowanego', 'Wyłączenie DNS'], 'Do tego SMB signing i ograniczenie NTLM.'],
  ['Dlaczego SSDP wystawione do Internetu jest niebezpieczne?', 'Może służyć jako wzmacniacz DDoS i ujawnia informacje o urządzeniu', ['Bo szyfruje ruch słabo', 'Bo blokuje DNS', 'Bo zmienia adres MAC routera'], 'UPnP/SSDP nie powinno odpowiadać od strony WAN.'],
  ['Co ogranicza storm control na przełączniku?', 'Ilość ruchu broadcast/multicast/unicast nieznanego na porcie powyżej progu', ['Liczbę adresów MAC', 'Prędkość łącza', 'Liczbę VLAN-ów'], 'Chroni przed burzami rozgłoszeniowymi.'],
  ['Jaki domyślny TTL ma MulticastSocket w Javie?', '1 — ruch zostaje w lokalnym segmencie', ['64', '255', '0'], 'setTimeToLive pozwala go zmienić.'],
  ['Co musi zrobić odbiorca multicastu w Javie, by dostawać pakiety grupy?', 'Dołączyć do grupy (joinGroup) na właściwym interfejsie', ['Ustawić setBroadcast(true)', 'Połączyć się connect() z nadawcą', 'Nic — multicast trafia do wszystkich'], 'joinGroup wysyła IGMP/MLD join.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const ip of ['224.0.0.6', '224.0.0.10', '224.0.0.13', '224.0.0.22', '239.10.20.30', '230.200.1.1', '226.130.2.3', '235.0.255.254', '239.200.100.50', '228.128.128.128', '231.64.32.16', '234.255.0.0']) {
  const m = mac4(ip); const o = ip.split('.').map(Number);
  const alt = `01:00:5e:${hx(o[1])}:${hx(o[2])}:${hx(o[3])}`;
  add(`Jaki adres MAC odpowiada grupie ${ip}?`, m, [alt !== m ? alt : `01:00:5e:${hx(o[1] ^ 0x40)}:${hx(o[2])}:${hx(o[3])}`, `01:00:5e:${hx(o[2])}:${hx(o[1] & 0x7f)}:${hx(o[3])}`, `33:33:${hx(o[0])}:${hx(o[1])}:${hx(o[2])}:${hx(o[3])}`, "ff:ff:ff:ff:ff:ff"], `Drugi oktet ${o[1]} AND 0x7f = ${o[1] & 0x7f} → ${m}.`);
}
for (const [ip, pr] of [['192.168.50.10', 23], ['10.8.0.77', 21], ['172.31.255.1', 20], ['100.64.10.10', 28], ['192.168.0.200', 27], ['10.250.1.1', 29], ['172.20.40.1', 24], ['10.0.128.5', 17]]) {
  const b = bcast(ip, pr);
  add(`Adres broadcast ukierunkowany sieci ${ip}/${pr} to:`, b, ['255.255.255.255', intIp(ipInt(b) - 1), intIp(Math.floor(ipInt(ip) / 2 ** (32 - pr)) * 2 ** (32 - pr))], `Ostatni adres bloku /${pr} zawierającego ${ip}: ${b}.`);
}
const m6 = (a) => { const g = a.split('::'); const tail = g[1].split(':').map((x) => x.padStart(4, '0')); while (tail.length < 2) tail.unshift('0000'); const t = tail.slice(-2).join(''); return '33:33:' + t.match(/../g).join(':'); };
for (const a of ['ff02::5', 'ff02::6', 'ff02::d', 'ff02::16', 'ff02::1:ff00:abcd', 'ff05::2', 'ff02::1:ffab:1234', 'ff0e::101']) {
  const m = m6(a);
  add(`Jaki adres MAC ma ramka do grupy IPv6 ${a}?`, m, ['ff:ff:ff:ff:ff:ff', '01:00:5e' + m.slice(5), m.replace('33:33:', '33:33:ff:').slice(0, 17) === m ? '33:33:ff:ff:ff:ff' : m.replace('33:33:', '33:33:ff:').slice(0, 17)], `33:33 + dolne 32 bity → ${m}.`);
}
for (const [m, right, w] of [['01:00:5e:00:00:fb', '224.0.0.251', ['224.0.0.252', '239.0.1.251', '224.0.1.251']], ['01:00:5e:7f:ff:fa', '239.255.255.250', ['239.255.255.251', '224.255.255.250', '239.127.254.250']], ['01:00:5e:00:00:12', '224.0.0.18', ['224.0.0.12', '224.0.18.0', '225.0.0.21']], ['01:00:5e:01:02:03', '239.1.2.3', ['239.3.2.1', '239.0.2.3', '239.1.2.4']], ['01:00:5e:00:00:05', '224.0.0.5', ['224.0.0.6', '224.0.5.0', '224.0.0.50']]]) {
  add(`Który adres grupy (spośród podanych) mapuje się na MAC ${m}?`, right, w, `${right} → ${mac4(right)}. Pozostałe dają inne dolne 23 bity.`);
}
add('Który zakres IPv4 jest zarezerwowany dla multicastu?', '224.0.0.0/4', ['240.0.0.0/4', '192.168.0.0/16', '127.0.0.0/8'], 'Dawna klasa D: 224.0.0.0–239.255.255.255.');
add('Jaki zakres IPv6 jest przeznaczony na multicast?', 'ff00::/8', ['fe80::/10', 'fc00::/7', '2000::/3'], 'Czwarta cyfra adresu wskazuje zasięg.');
add('Które protokoły w sieciach Windows odpytują rozgłoszeniowo o nazwy, gdy DNS zawiedzie?', 'LLMNR i NBT-NS (oraz mDNS)', ['DHCP i NTP', 'OSPF i VRRP', 'TLS i HTTP'], 'Ich nadużycie umożliwia przechwytywanie poświadczeń.');
add('Jak kontenery Dockera w sieci bridge mogą odbierać multicast z sieci fizycznej hosta?', 'Zwykle nie mogą — potrzebna jest sieć host lub macvlan (albo przekaźnik)', ['Zawsze automatycznie', 'Przez publikację portu -p', 'Przez DNS Dockera'], 'Bridge z NAT nie przekazuje multicastu z sieci fizycznej.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
