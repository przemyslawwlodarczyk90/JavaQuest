// Generuje lekcje _ns_01_fundamenty_sieci/08_BossPacketJourney.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_08_boss_lesson.cjs src/main/resources/content/_ns_01_fundamenty_sieci/08_BossPacketJourney.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { NODES, POINTS, CLIENT_PORT, PROXY_EPH, COMPOSE } = require('./ns01_08_boss_lab.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const [P1, P2, P3] = POINTS;

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Boss: jedno żądanie, trzy punkty obserwacji', ...img('ns01-08-lab-topology.svg', 'Trzy kolorowe sieci: lan z laptopem 10.10.1.10, wan z routerem NAT (10.10.1.1 i 10.20.0.2) i reverse proxy Caddy (10.20.0.10), app z interfejsem proxy 10.30.0.10 i aplikacją Spring Boot 10.30.0.20:8080. Czerwone znaczniki P1, P2 i P3 na łączach oznaczają punkty przechwytywania.'),
    body:
`To laboratorium zamyka rozdział o fundamentach. Nie ma tu nowej teorii — jest **misja**: prześledzić jedno żądanie \`GET /users/42\` z laptopa do aplikacji Spring Boot przez **router z NAT** i **reverse proxy**, przechwycić ruch w trzech punktach i wyjaśnić *każdą* zmianę adresów, portów i TTL.

Wykorzystasz wszystko z rozdziału: topologię (lekcja 03), warstwy OSI (04), enkapsulację i pola nagłówków (05), pomiar czasu (06) i warsztat przechwytywania (07).

**Zasady Bossa:**
- Lab uruchamiasz lokalnie z dostarczonego \`docker-compose.yml\`; wszystkie sieci są izolowane (\`internal: true\`).
- Treść lekcji opisuje cel, narzędzia i kryteria — ale **nie podaje gotowych odpowiedzi** do Twoich plików pcap. Wartości w tabeli tej lekcji to *oczekiwania*: Twój lab może mieć inne porty efemeryczne lub TTL, a Twoim zadaniem jest wyjaśnić, *dlaczego*.
- **Artefakty do oddania:** diagram z opisanymi zmianami (P1 → P2 → P3), trzy pliki pcap z sumami SHA-256, tabela korelacji żądania w czterech źródłach (pcap, conntrack, log proxy, log aplikacji) i krótki raport z odpowiedziami na pytania kontrolne.

Ten sam schemat — klient, NAT, proxy, aplikacja — spotkasz w każdej firmie: biuro za NAT-em, WAF lub ingress w Kubernetesie i mikroserwis. Analityk SOC, który umie przejść po nim krok po kroku, potrafi odpowiedzieć na najważniejsze pytanie incydentu: **kto naprawdę wysłał to żądanie?**` },

  { type: 'DEFINITION', heading: 'Co musisz umieć wyjaśnić po tym laboratorium', body:
`Lista kompetencji, które sprawdza Boss (każda wraca w pytaniach kontrolnych):
- **Rola bramy domyślnej** — dlaczego laptop wysyła ramkę z żądaniem do proxy na **MAC routera**, a nie proxy (inna podsieć → next hop = brama).
- **ARP** — kiedy laptop pyta o MAC bramy, a kiedy korzysta z cache (\`ip neigh\`).
- **Zmiana MAC na każdym odcinku L3** — router buduje nową ramkę z własnym MAC źródłowym na interfejsie WAN.
- **TTL** — router zmniejsza TTL o 1; proxy *nie* przekazuje pakietu, tylko tworzy nowy, więc TTL w P3 zaczyna od wartości domyślnej systemu proxy.
- **NAT (masquerade)** — podmiana IP źródłowego z 10.10.1.10 na 10.20.0.2; port źródłowy zwykle zostaje (jeśli nie koliduje); wpis w tablicy **conntrack** pozwala odwrócić translację dla odpowiedzi.
- **Reverse proxy = dwa połączenia TCP** — nowy handshake, nowy port efemeryczny proxy, nowy port docelowy aplikacji (8080).
- **X-Forwarded-For** — proxy dopisuje adres, z którego *ono* widziało klienta (10.20.0.2 — adres po NAT, nie laptopa!).
- **Korelacja** — jak połączyć wpisy z pcap, conntrack, logu proxy i logu aplikacji: czas, porty, tabela NAT, XFF, identyfikator żądania.
- **Granice widoczności** — czego nie da się ustalić z jednego punktu i dlaczego obrońca potrzebuje logów z wielu warstw.` },

  { type: 'ANALOGY', heading: 'Paczka przez sortownię i recepcję', body:
`Wysyłasz paczkę z biura (laptop) do działu w innej firmie (aplikacja):
- **Sortownia w Twoim biurowcu (router z NAT)** zdejmuje Twoją wewnętrzną etykietę „pokój 10” i nakleja adres zwrotny całego biurowca. Zapisuje w zeszycie: „paczka nr 45678 z pokoju 10”. Gdy przyjdzie odpowiedź na adres biurowca z numerem 45678, sortownia wie, do którego pokoju ją zanieść (conntrack).
- **Recepcja firmy docelowej (reverse proxy)** odbiera paczkę, *przepakowuje* ją do wewnętrznej koperty i wysyła wewnętrzną pocztą do działu. Na kopercie dopisuje: „nadawca: biurowiec przy ul. X” (X-Forwarded-For) — bo tylko tyle wie. Pokoju 10 nie zna.
- **Kurier na każdym odcinku (MAC)** jest inny, a licznik przystanków (TTL) maleje tylko tam, gdzie paczka jest przekazywana dalej bez przepakowania (router).

Gdy dział zgłosi „podejrzaną paczkę”, ustalenie, kto ją wysłał, wymaga zajrzenia **do zeszytu sortowni i do książki recepcji** — dokładnie jak w incydencie: logi NAT i proxy są niezbędne do identyfikacji źródła.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Oczekiwane wartości pól w P1, P2 i P3', ...img('ns01-08-header-table.svg', 'Tabela: w P1 MAC laptopa do MAC routera, IP 10.10.1.10 do 10.20.0.10, porty 45678 do 80, TTL 64; w P2 MAC routera WAN do MAC proxy, IP źródłowy 10.20.0.2, te same porty, TTL 63; w P3 MAC proxy do MAC aplikacji, IP 10.30.0.10 do 10.30.0.20, porty 38210 do 8080, TTL 64, X-Forwarded-For 10.20.0.2, nowe połączenie TCP.'),
    body:
`Tak powinno wyglądać żądanie HTTP w trzech punktach (port efemeryczny klienta i proxy w Twoim labie będzie inny — ważne są *relacje*):`,
    code:
POINTS.map((p) => `${p.id}  ${p.where}
    Ethernet  ${p.srcMac} → ${p.dstMac}
    IPv4      ${p.srcIp} → ${p.dstIp}   TTL ${p.ttl}
    TCP       ${p.srcPort} → ${p.dstPort}   (${p.conn})
    HTTP      GET /users/42   X-Forwarded-For: ${p.xff}`).join('\n\n') + `

Kontrole spójności:
  P1→P2: IP docelowy, porty i dane bez zmian; IP źródłowy podmieniony (NAT); TTL −1; nowe MAC
  P2→P3: wszystko nowe poza treścią żądania — to inne połączenie TCP; XFF niesie adres z P2
  Odpowiedź 200 w P2 ma cel 10.20.0.2:${CLIENT_PORT}, w P1 — 10.10.1.10:${CLIENT_PORT} (odwrotna translacja NAT)` },

  { type: 'CODE_BASIC', heading: 'Środowisko labu: docker-compose.yml', body:
`Lab składa się z czterech kontenerów i trzech izolowanych sieci. Router to zwykły kontener Linux z włączonym przekazywaniem pakietów (\`ip_forward\`) i regułą \`masquerade\` w nftables; laptop ma trasę domyślną przez router. Uruchomienie: \`docker compose up -d\`, sprzątanie: \`docker compose down\`.`,
    code: COMPOSE },

  { type: 'STEP_BY_STEP', heading: 'Przebieg laboratorium', ...img('ns01-08-sequence.svg', 'Diagram sekwencji z czterema uczestnikami: laptop, router, proxy, aplikacja. Kolejno: zapytanie i odpowiedź ARP o bramę, handshake TCP laptopa z proxy przez router, żądanie GET, osobny handshake proxy z aplikacją, żądanie z X-Forwarded-For, odpowiedź 200 od aplikacji do proxy i od proxy do laptopa.'),
    body:
`1. **Uruchom lab** i sprawdź adresy: \`docker compose exec router ip -br addr\` (który interfejs to LAN, a który WAN?), \`docker compose exec client ip route\` (brama 10.10.1.1).
2. **Wyczyść cache ARP laptopa**, by zobaczyć ARP w przechwyceniu: \`docker compose exec client ip neigh flush all\`.
3. **Uruchom trzy przechwytywania** (każde w osobnym terminalu, z zapisem do katalogu \`./cap\`):
   - P1: interfejs LAN routera (\`docker run --rm --net container:<router> -v $PWD/cap:/cap nicolaka/netshoot tcpdump -i eth0 -nn -w /cap/p1.pcap\`),
   - P2: interfejs WAN routera (\`-i eth1 -w /cap/p2.pcap\`),
   - P3: interfejs proxy w sieci app (\`--net container:<proxy>\`, właściwy interfejs ustal \`ip -br addr\`).
4. **Wygeneruj ruch** z laptopa: \`ping -c 3 10.20.0.10\`, potem \`curl -s -H 'X-Request-ID: boss-001' http://10.20.0.10/users/42\`.
5. **Zrzuć tablicę NAT** w trakcie lub tuż po: \`docker compose exec router conntrack -L\` (lub \`cat /proc/net/nf_conntrack\`).
6. **Zatrzymaj przechwytywania**, policz skróty: \`sha256sum cap/*.pcap > cap/SHA256SUMS\`.
7. **Analiza w Wiresharku** — dla każdego pliku: ARP (kto pyta, kto odpowiada), ICMP echo (TTL w P1 i P2), TCP/HTTP (adresy, porty, TTL, nagłówki). Dodaj kolumny \`eth.src\`, \`eth.dst\`, \`ip.ttl\`, \`tcp.srcport\`, \`http.x_forwarded_for\`.
8. **Zestaw tabelę zmian** P1 → P2 → P3 i wyjaśnij każdą zmianę mechanizmem (brama, router, NAT, proxy).
9. **Skoreluj** żądanie w czterech źródłach: pcap, conntrack, log Caddy, log Spring Boot (identyfikator \`X-Request-ID\`).
10. **Odpowiedz na pytania kontrolne** (zadania tej lekcji) i przygotuj raport.` },

  { type: 'NOTE', heading: 'Jak łączyć dowody z różnych punktów', ...img('ns01-08-correlation.svg', 'Cztery ramki połączone strzałkami: pcap P1 z połączeniem 10.10.1.10:45678 do 10.20.0.10:80, wpis conntrack z translacją na 10.20.0.2:45678, log Caddy z remote_ip 10.20.0.2 i identyfikatorem żądania oraz log Spring Boot z remoteAddr 10.30.0.10, XFF 10.20.0.2 i tym samym identyfikatorem.'),
    body:
`W incydencie rzadko masz jeden plik, który mówi wszystko. Uczysz się teraz techniki, którą stosuje każdy zespół IR:`,
    code:
`klucz korelacji          gdzie go znajdziesz                         co łączy
czas                     wszystkie źródła (zsynchronizowane NTP)     zawęża okno; ±1 s to często za mało przy dużym ruchu
port źródłowy klienta    pcap P1/P2, conntrack                       P1 ↔ P2 (NAT zwykle zachowuje port)
tablica NAT (conntrack)  router, firewall, NAT Gateway (flow logs)   adres prywatny ↔ publiczny
X-Forwarded-For          nagłówek w P3, log aplikacji                proxy ↔ aplikacja (adres widziany przez proxy)
identyfikator żądania    X-Request-ID, traceparent (W3C Trace Ctx)   proxy ↔ aplikacja ↔ dalsze usługi
treść / rozmiar          pcap (przy HTTP), log z rozmiarem odpowiedzi  potwierdzenie, że to „to samo” żądanie

W Wiresharku: View → Time Display Format → UTC Date and Time; przy kilku plikach
mergecap -w all.pcap p1.pcap p2.pcap p3.pcap łączy je chronologicznie (znaczniki czasu z jednego hosta).
W chmurze rolę conntrack pełnią VPC Flow Logs / logi NAT Gateway — bez nich adres publiczny w logu WAF
nie wskaże konkretnej instancji.` },

  { type: 'CODE_PRACTICAL', heading: 'Analiza bez klikania: tshark dla trzech plików', body:
`Gdy masz trzy pliki i dziesiątki żądań, tabela z \`tshark\` jest szybsza niż Wireshark. To samo polecenie dla każdego punktu daje porównywalne wiersze.`,
    code:
`for p in p1 p2 p3; do
  echo "== $p"
  tshark -r cap/$p.pcap -Y 'http.request' -T fields -E separator=' | ' \\
    -e frame.time_epoch -e eth.src -e eth.dst -e ip.src -e ip.dst -e ip.ttl \\
    -e tcp.srcport -e tcp.dstport -e http.request.uri -e http.x_forwarded_for -e http.request.line
done

# ARP i ICMP w P1 i P2
tshark -r cap/p1.pcap -Y 'arp || icmp' -T fields -e frame.number -e eth.src -e arp.opcode -e arp.src.proto_ipv4 -e ip.src -e ip.dst -e ip.ttl
tshark -r cap/p2.pcap -Y 'icmp' -T fields -e frame.number -e ip.src -e ip.dst -e ip.ttl -e icmp.type

# Połączenia TCP w każdym punkcie (powinno być: 1 w P1, 1 w P2, 1 w P3 — ale P3 to INNE połączenie)
tshark -r cap/p3.pcap -q -z conv,tcp

# NAT na routerze
docker compose exec router conntrack -L -p tcp --dport 80
# tcp 6 117 TIME_WAIT src=10.10.1.10 dst=10.20.0.10 sport=${CLIENT_PORT} dport=80 src=10.20.0.10 dst=10.20.0.2 sport=80 dport=${CLIENT_PORT} [ASSURED] ...` },

  { type: 'USAGE', heading: 'Ten sam schemat w prawdziwej firmie', body:
`Lab jest miniaturą typowej ścieżki ruchu:
- **Użytkownik w biurze** → firewall z NAT → Internet → **WAF/CDN** → **load balancer / ingress** → **pod w Kubernetesie**. Każdy z tych elementów zmienia adresy lub tworzy nowe połączenie.
- **W chmurze AWS**: instancja w podsieci prywatnej → **NAT Gateway** (VPC Flow Logs pokazują translację) → usługa zewnętrzna; ruch przychodzący: **ALB** (nowe połączenie, nagłówek X-Forwarded-For) → instancja lub kontener.
- **W Javie**: Spring Boot za proxy widzi \`request.getRemoteAddr()\` = adres proxy. Prawdziwy klient (o ile proxy jest zaufane) jest w \`X-Forwarded-For\`/\`Forwarded\`; konfiguruje się to przez \`server.forward-headers-strategy\` i listę zaufanych proxy (\`server.tomcat.remoteip.internal-proxies\`).

**Znaczenie dla obrońcy:**
- **Atrybucja w incydencie** — alert WAF z adresem 83.x.x.x to dopiero początek; kto w firmie to był, ustala się z logów NAT/firewalla i DHCP. Brak tych logów = brak odpowiedzi.
- **Retencja logów** — zgodnie z KSC/NIS2 i dobrą praktyką logi NAT, proxy i uwierzytelnienia muszą być przechowywane i zsynchronizowane czasowo, inaczej nie da się odtworzyć łańcucha zdarzeń.
- **Fałszowanie nagłówków** — napastnik może dodać własny \`X-Forwarded-For: 127.0.0.1\`, licząc na aplikację, która ufa mu bezkrytycznie (obejście allow-listy IP, fałszywe wpisy w logach). Ufaj tylko wpisom dodanym przez własne proxy.
- **Segmentacja** — w labie laptop nie ma żadnej trasy do sieci \`app\`; jedyna droga do aplikacji prowadzi przez proxy (punkt kontroli i logowania). Tak samo projektuje się DMZ.` },

  { type: 'PITFALL', heading: 'Pułapki, na które trafia większość podchodzących do Bossa', body:
`- **Pomylone interfejsy routera** — Docker nie gwarantuje, że \`lan\` to \`eth0\`. Zawsze sprawdź \`ip -br addr\`; inaczej reguła masquerade trafi na zły interfejs, a P1 i P2 zamienią się miejscami.
- **Brak ARP w przechwyceniu** — cache był ciepły. Wyczyść \`ip neigh flush all\` przed testem.
- **„NAT zmienił port”** — zwykle nie zmienia; zmieni, gdy port jest zajęty przez inne połączenie (PAT). Jeśli widzisz zmianę, wyjaśnij ją.
- **Oczekiwanie TTL 62 w P3** — proxy nie przekazuje pakietu, tylko tworzy nowy, więc TTL zaczyna od wartości domyślnej (64 w Linuksie).
- **XFF = adres laptopa?** — nie. Proxy widzi tylko 10.20.0.2 (adres po NAT). Adres 10.10.1.10 zna wyłącznie router.
- **Dwa połączenia czy jedno?** — Caddy może utrzymywać połączenia do backendu (keep-alive); przy kolejnych żądaniach w P3 nie będzie nowego handshake’u. Do analizy wygodnie zrestartować proxy przed testem albo opisać to zjawisko.
- **Offloading i „złe sumy”** — w przechwyceniach na hostach wysyłających sumy kontrolne mogą być oznaczone jako błędne. To nie jest usterka labu.
- **Różne zegary** — wszystkie kontenery korzystają z zegara hosta, więc w labie problem nie wystąpi; w prawdziwej sieci bez NTP korelacja czasem zawodzi.` },

  { type: 'CODE_WRONG', heading: 'Źle: aplikacja, która nie wie, kto jest klientem', body:
`Aplikacja za proxy loguje i stosuje allow-listę na podstawie \`getRemoteAddr()\` (zawsze adres proxy), a w innym miejscu ślepo ufa pierwszemu wpisowi \`X-Forwarded-For\` od klienta.`,
    code:
`@GetMapping("/admin/report")
ResponseEntity<?> report(HttpServletRequest req) {
    String ip = req.getRemoteAddr();                       // za proxy ZAWSZE 10.30.0.10
    log.info("admin report from {}", ip);                  // log bezużyteczny w incydencie
    String xff = req.getHeader("X-Forwarded-For");
    String client = xff != null ? xff.split(",")[0].trim() : ip;   // pierwszy wpis = kontrolowany przez klienta!
    if (!client.startsWith("10.")) return ResponseEntity.status(403).build();
    return ResponseEntity.ok(service.report());
}
// Atak: curl -H 'X-Forwarded-For: 10.0.0.1' https://app/admin/report → dostęp przyznany` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: zaufane proxy, poprawny adres i identyfikator żądania', body:
`Tomcat (RemoteIpValve) przetwarza \`X-Forwarded-For\` od prawej strony i ufa tylko adresom zdefiniowanym jako wewnętrzne proxy. Aplikacja dostaje właściwy \`getRemoteAddr()\`, loguje go razem z identyfikatorem żądania, a autoryzacja nie opiera się wyłącznie na IP.`,
    code:
`# application.yml
server:
  forward-headers-strategy: native            # Tomcat RemoteIpValve
  tomcat:
    remoteip:
      internal-proxies: "10\\\\.30\\\\.0\\\\.10"         # regex: tylko nasze proxy jest zaufane
      remote-ip-header: x-forwarded-for
      protocol-header: x-forwarded-proto

// Filtr: identyfikator żądania w MDC (trafia do każdego wpisu logu)
@Component
class RequestIdFilter extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String id = Optional.ofNullable(req.getHeader("X-Request-ID")).filter(v -> v.matches("[\\\\w-]{1,64}"))
                            .orElse(UUID.randomUUID().toString());
        MDC.put("requestId", id); MDC.put("clientIp", req.getRemoteAddr());
        res.setHeader("X-Request-ID", id);
        try { chain.doFilter(req, res); } finally { MDC.clear(); }
    }
}
// Dostęp do /admin/** — Spring Security: uwierzytelnienie + rola ADMIN; IP to najwyżej dodatkowy warunek.` },

  { type: 'WHEN_TO_USE', heading: 'Kryteria zaliczenia Bossa', body:
`Ocena artefaktów (każde kryterium: spełnione / częściowo / niespełnione):
- **Diagram** — trzy sieci, urządzenia, adresy, punkty P1–P3; strzałki z opisem zmian (MAC, IP, porty, TTL, nowe połączenie).
- **Pliki pcap** — trzy pliki z ARP, ICMP i HTTP; sumy SHA-256; opis, gdzie i kiedy przechwycono.
- **Tabela zmian** — dla każdego pola wartość w P1, P2, P3 i *mechanizm*, który ją zmienił (lub dlaczego jej nie zmienił).
- **Korelacja** — żądanie \`boss-001\` odnalezione w czterech źródłach z kluczami korelacji.
- **Pytania kontrolne** — poprawne odpowiedzi z odwołaniem do numerów ramek w pcap.
- **Wniosek obrońcy** — krótko: jakie logi musiałaby mieć firma, by w incydencie wskazać laptop jako źródło żądania, i jakie ryzyko niesie zaufanie do nagłówka XFF.

**Poziom ekspercki (opcjonalnie):** powtórz lab z HTTPS na proxy (certyfikat z prywatnego CA) i odszyfruj P1 kluczami sesji curl; dodaj drugie proxy w łańcuchu i pokaż, jak rośnie XFF; zmień NAT na statyczny SNAT i opisz różnicę w conntrack.` },

  { type: 'SUMMARY', heading: 'Podsumowanie rozdziału', body:
`- Jedno żądanie przechodzi przez **kilka odcinków L2** (nowe MAC na każdym) i **jeden lub więcej routerów** (TTL −1).
- **NAT** podmienia adres źródłowy (i czasem port); odwrotność zna tylko tablica conntrack urządzenia NAT.
- **Reverse proxy** kończy połączenie i otwiera nowe: nowe adresy, porty, TTL; klienta opisuje nagłówek **X-Forwarded-For** — i to tylko w zakresie, w jakim proxy go widziało.
- Aplikacja za proxy musi wiedzieć, którym proxy ufa (\`server.forward-headers-strategy\`, internal-proxies); ślepe zaufanie do XFF to luka.
- **Korelacja** dowodów wymaga zsynchronizowanego czasu, logów NAT, logów proxy, identyfikatorów żądań — to fundament pracy SOC i IR.
- Zamykasz rozdział 01: wiesz, jak zbudowana jest sieć, jak warstwy dzielą odpowiedzialność, jak wygląda pakiet bajt po bajcie, jak mierzyć wydajność i jak przechwytywać ruch.
- Następny rozdział: **Ethernet, switching i sieć lokalna** — warstwa, od której zaczyna się każda podróż pakietu.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const ctrl = [
  ['Dlaczego w P1 MAC docelowy ramki z żądaniem to MAC routera (10.10.1.1), a nie proxy?', 'Proxy (10.20.0.10) jest w innej podsieci niż laptop (10.10.1.0/24), więc laptop wysyła ramkę do next hopa — bramy domyślnej 10.10.1.1. Adres IP docelowy w pakiecie pozostaje adresem proxy.'],
  ['Którą ramkę ARP zobaczysz w P1 przed żądaniem i kto na nią odpowiada? Czy w P2 też będzie ARP od laptopa?', 'Laptop pyta „who-has 10.10.1.1 tell 10.10.1.10”, odpowiada router „10.10.1.1 is-at 02:42:0a:0a:01:01”. W P2 nie ma ARP laptopa — ARP nie przechodzi przez router; w P2 może być ARP routera o MAC proxy (10.20.0.10).'],
  ['Jak zmienia się TTL pakietu ICMP echo od laptopa między P1 a P2 i dlaczego?', 'P1: 64, P2: 63 — router zmniejsza TTL o 1 przy przekazywaniu pakietu (i przelicza sumę kontrolną). Odpowiedź echo reply z proxy ma w P2 TTL 64, a w P1 63.'],
  ['Które pola żądania HTTP są identyczne w P1 i P2, a które różne? Wyjaśnij mechanizm każdej różnicy.', 'Identyczne: IP docelowy 10.20.0.10, porty (45678 → 80), numery sekwencyjne, treść HTTP. Różne: MAC źródłowy i docelowy (nowy odcinek L2, ramkę zbudował router), IP źródłowy 10.10.1.10 → 10.20.0.2 (masquerade), TTL 64 → 63 (router), suma kontrolna IP i TCP (zmiana adresu i TTL).'],
  ['Dlaczego w P3 widzisz zupełnie inne porty i adresy niż w P2?', 'Proxy zakończyło połączenie klienta i otworzyło nowe połączenie TCP do aplikacji: źródło 10.30.0.10 z nowym portem efemerycznym, cel 10.30.0.20:8080. To osobny handshake (SYN, SYN-ACK, ACK) i osobna sesja TCP.'],
  ['Jaką wartość ma X-Forwarded-For w P3 i dlaczego nie jest to adres laptopa?', 'X-Forwarded-For: 10.20.0.2 — proxy wpisuje adres, z którego przyszło połączenie. Z powodu NAT na routerze proxy nigdy nie widziało adresu 10.10.1.10.'],
  ['Na podstawie wpisu conntrack routera wyjaśnij, jak odpowiedź 200 trafia z powrotem do laptopa.', 'Wpis zawiera krotkę oryginalną (10.10.1.10:45678 → 10.20.0.10:80) i odwrotną (10.20.0.10:80 → 10.20.0.2:45678). Odpowiedź przychodzi na 10.20.0.2:45678; router znajduje wpis i podmienia adres docelowy z powrotem na 10.10.1.10, po czym wysyła ramkę do MAC laptopa.'],
  ['Ile połączeń TCP widzi aplikacja Spring Boot, gdy laptop wyśle 5 żądań w ciągu sekundy przez keep-alive? A ile proxy od strony klienta?', 'Od strony klienta: jedno połączenie (curl z keep-alive w jednej sesji) lub pięć (osobne wywołania curl). Od strony aplikacji: zależy od puli połączeń proxy do backendu — Caddy utrzymuje połączenia keep-alive, więc zwykle jedno lub kilka, niezależnie od liczby połączeń klientów. Liczba połączeń po obu stronach proxy jest niezależna.'],
  ['Co zobaczyłaby aplikacja w request.getRemoteAddr() bez konfiguracji forward-headers i dlaczego to problem dla audytu?', '10.30.0.10 — adres proxy. Wszystkie żądania wyglądałyby na pochodzące z jednego adresu: brak rozróżnienia klientów w logach, rate limiting i blokady IP przestają działać, a w incydencie nie da się wskazać źródła.'],
  ['Wskaż w pcap P3 numer ramki z nagłówkiem X-Request-ID: boss-001 i odpowiadający wpis w logu Spring Boot. Jakie pola potwierdzają, że to to samo żądanie?', 'Filtr: http.request && http contains "boss-001" (lub pole http.request.line). Potwierdzenie: ten sam identyfikator w logu (MDC requestId), zgodny czas (± ms), ścieżka /users/42, adres proxy 10.30.0.10 jako remoteAddr i XFF 10.20.0.2, rozmiar odpowiedzi.'],
];
for (const [q, a] of ctrl) ex(`Pytanie kontrolne: ${q}`, 'Odwołaj się do konkretnych ramek w swoich plikach pcap.', a);
const variants = [
  ['Dodaj drugie proxy (Nginx) przed Caddy w sieci wan. Jak będzie wyglądał nagłówek X-Forwarded-For w P3 i co zmieni się w konfiguracji zaufanych proxy w Spring?', 'XFF: „10.20.0.2, <adres Nginx>” — każde proxy dopisuje adres swojego klienta na końcu. W Spring internal-proxies musi obejmować oba proxy, a RemoteIpValve, idąc od prawej, pominie zaufane adresy i wybierze 10.20.0.2 jako klienta.'],
  ['Zmień regułę masquerade na statyczny SNAT na adres 10.20.0.100 (dodatkowy adres na interfejsie WAN). Co zmieni się w P2, conntrack i XFF?', 'IP źródłowy w P2 = 10.20.0.100 zamiast 10.20.0.2; conntrack pokazuje translację na 10.20.0.100; XFF w P3 = 10.20.0.100. Proxy musi znać trasę powrotną do 10.20.0.100 (ten sam segment — ARP na routerze musi odpowiadać za ten adres).'],
  ['Włącz HTTPS na proxy (certyfikat z prywatnego CA) i powtórz pomiar. Co zobaczysz w P1/P2, a co w P3? Jak odszyfrować P1?', 'P1/P2: handshake TLS (ClientHello z SNI) i zaszyfrowane Application Data — bez ścieżki i nagłówków. P3: nadal HTTP jawne (proxy kończy TLS), chyba że skonfigurujesz TLS do backendu. Odszyfrowanie P1: SSLKEYLOGFILE dla curl i wskazanie pliku kluczy w Wiresharku.'],
  ['Przenieś laptop do sieci wan (bez routera). Co zniknie z obrazu i jakie ryzyko architektoniczne to ilustruje?', 'Znika NAT i router: proxy widzi prawdziwy adres laptopa, TTL w P2 = 64, brak ARP o bramę (laptop pyta bezpośrednio o MAC proxy). Ilustruje to brak segmentacji — klient w tej samej sieci co serwery brzegowe, bez punktu kontroli (firewall/NAT) pomiędzy.'],
  ['Spróbuj z laptopa połączyć się bezpośrednio z aplikacją 10.30.0.20:8080. Co pokaże curl i przechwycenie w P1? Dlaczego?', 'Laptop wyśle pakiety do bramy (router), ale router nie ma trasy do 10.30.0.0/24 (zna tylko lan i wan) — odeśle ICMP Network Unreachable lub pakiety zginą; curl: „No route to host” lub timeout. To zamierzona segmentacja: jedyna droga do aplikacji prowadzi przez proxy.'],
];
for (const [q, a] of variants) ex(`Wariant: ${q}`, 'Zmień jeden element labu i przewidź skutki przed pomiarem.', a);
ex('Zmierz czas żądania curl -w z laptopa i porównaj z czasem odpowiedzi w logu aplikacji oraz w logu Caddy. Skąd biorą się różnice?', 'Każdy element mierzy inny odcinek.', 'curl mierzy całość z perspektywy klienta (DNS, TCP z proxy, czekanie, transfer); Caddy mierzy od przyjęcia żądania do wysłania odpowiedzi (obejmuje połączenie do aplikacji i jej czas); aplikacja mierzy tylko przetwarzanie w Spring. Różnice to czas sieci między odcinkami, handshake proxy–aplikacja (jeśli nowe połączenie) i buforowanie w proxy.');
ex('Przygotuj w Wiresharku profil „Boss” z kolumnami: eth.src, eth.dst, ip.src, ip.dst, ip.ttl, tcp.srcport, tcp.dstport, http.x_forwarded_for, tcp.stream. Wyeksportuj go i opisz, do czego służy każda kolumna w tym labie.', 'Edit → Configuration Profiles; kolumny dodasz przez Apply as Column.', 'Profil ułatwia porównanie trzech plików: MAC pokazują odcinki L2, IP i porty — NAT i nowe połączenie, TTL — przejście przez router, XFF — informację przekazaną przez proxy, tcp.stream — granice połączeń. Eksport profilu (folder profilu) pozwala powtórzyć analizę w zespole.');
ex('Zapisz w raporcie „wniosek obrońcy”: jakie logi i ustawienia musiałaby mieć firma o takiej architekturze, by po alercie z aplikacji wskazać konkretny laptop?', 'Myśl o każdym punkcie, który zmienia adresy.', 'Wymagane: logi aplikacji z identyfikatorem żądania i adresem klienta z XFF (przetworzonym przez zaufane proxy); logi proxy (adres źródłowy, czas, identyfikator); logi NAT/firewalla z translacjami (conntrack/NetFlow z NAT, retencja co najmniej kilka miesięcy); logi DHCP (adres IP → MAC → urządzenie) i ewentualnie 802.1X (urządzenie → użytkownik); synchronizacja czasu NTP we wszystkich systemach; centralny SIEM z korelacją.');
ex('Zaproponuj trzy reguły detekcji, które w tej architekturze wykryłyby próbę fałszowania nagłówka X-Forwarded-For przez klienta.', 'Porównaj to, co dodało proxy, z tym, co przysłał klient.', '1) W proxy: zapisuj nagłówek XFF otrzymany od klienta (przed modyfikacją); alert, gdy klient z Internetu przysyła XFF zawierający adresy prywatne lub 127.0.0.1. 2) W aplikacji: alert, gdy łańcuch XFF ma więcej wpisów niż liczba proxy w architekturze. 3) W SIEM: korelacja — ten sam identyfikator sesji z różnymi „adresami klienta” w krótkim czasie. Dodatkowo: proxy brzegowe powinno usuwać XFF od klienta lub nadpisywać go (Caddy/Nginx: konfiguracja trusted_proxies / real_ip).');
ex('Oblicz, ile ramek ARP, ICMP i TCP zobaczysz minimalnie w P1 dla sekwencji: ping -c 3 10.20.0.10, a potem jedno curl (bez keep-alive po zakończeniu). Uzasadnij.', 'ARP raz (cache), ICMP 3 × 2, TCP: handshake, żądanie, odpowiedź, ACK-i, zamknięcie.', 'ARP: 2 (request + reply) — dalej cache. ICMP: 6 (3 echo request + 3 echo reply). TCP: handshake 3, żądanie 1, ACK serwera 0–1 (może być sklejony z odpowiedzią), odpowiedź 1–2, ACK klienta 1, zamknięcie 3–4 (FIN/ACK w obie strony) — razem ok. 9–12. Dokładna liczba zależy od sklejania ACK i podziału odpowiedzi; w raporcie podaj liczby z własnego pliku.');
ex('Na podstawie rozdziału przygotuj „kartę ścieżki pakietu” dla swojej aplikacji w pracy lub projektu: wszystkie elementy od klienta do bazy, które zmieniają adresy lub tworzą nowe połączenia, i gdzie zbierane są ich logi.', 'Uwzględnij CDN/WAF, load balancer, ingress, sidecar service mesh, pule połączeń do bazy.', 'Przykład: przeglądarka → CDN (nowe połączenie, XFF) → ALB (nowe połączenie, XFF dopisany, logi ALB w S3) → ingress NGINX w K8s (nowe połączenie, logi ingress) → pod Spring Boot (logi aplikacji, traceId) → HikariCP → PostgreSQL (połączenia z puli, adres poda/węzła po SNAT, logi PostgreSQL). Dla każdego: jaki adres widzi, gdzie loguje, jak długo przechowuje.');
ex('Uruchom w labie ten sam scenariusz, ale z IPv6 (dodaj podsieci IPv6 do sieci lan/wan/app i usuń NAT). Jakie różnice zaobserwujesz w P1–P3?', 'IPv6 nie potrzebuje NAT; zamiast ARP działa Neighbor Discovery; zamiast TTL — Hop Limit.', 'Różnice: Neighbor Solicitation/Advertisement (ICMPv6) zamiast ARP; Hop Limit zamiast TTL (zmniejszany przez router); brak NAT — proxy widzi globalny/ULA adres laptopa, więc XFF zawiera prawdziwy adres klienta; nagłówek IPv6 ma 40 B. Bezpieczeństwo nie wynika już z NAT — potrzebny jawny firewall na routerze.');
const extra = [
  ['W pliku P2 znajdź zapytanie ARP wysłane przez router. O jaki adres pyta i dlaczego?', 'Router pyta „who-has 10.20.0.10 tell 10.20.0.2” — musi poznać MAC proxy, by zbudować ramkę na odcinku WAN. Każdy router rozwiązuje adres następnego skoku w swojej sieci.'],
  ['Opisz odpowiedź HTTP 200 w P2: adresy, porty i TTL. Czym różni się od tej samej odpowiedzi w P1?', 'P2: 10.20.0.10:80 → 10.20.0.2:45678, TTL 64 (prosto z proxy). P1: 10.20.0.10:80 → 10.10.1.10:45678, TTL 63 — router odwrócił NAT (adres docelowy) i zmniejszył TTL, ramkę zbudował z MAC LAN routera do MAC laptopa.'],
  ['Na proxy uruchom ss -tn podczas trwania połączenia. Ile gniazd TCP zobaczysz dla jednego żądania i jakie mają adresy?', 'Dwa: 10.20.0.10:80 ⇄ 10.20.0.2:45678 (od strony klienta) oraz 10.30.0.10:38210 ⇄ 10.30.0.20:8080 (do backendu). To bezpośredni dowód, że proxy utrzymuje dwa niezależne połączenia.'],
  ['Wpis conntrack ma stan ASSURED i TIME_WAIT. Co oznaczają te stany?', 'ASSURED — połączenie widziało ruch w obu kierunkach i nie zostanie usunięte przy przepełnieniu tablicy w pierwszej kolejności. TIME_WAIT — połączenie zostało zamknięte (FIN), a wpis jest jeszcze przez chwilę utrzymywany, by obsłużyć spóźnione pakiety; potem wygasa.'],
  ['Dodaj na odcinku WAN tc netem z utratą 5% pakietów i powtórz żądanie 20 razy. Gdzie (P1, P2, P3) zobaczysz retransmisje i dlaczego?', 'Retransmisje połączenia klient–proxy zobaczysz w P1 i P2 (segmenty gubione na odcinku WAN; w P1 ponowienia od klienta i zduplikowane ACK). W P3 nie — połączenie proxy–aplikacja nie przechodzi przez uszkodzony odcinek. Wniosek: przechwytywanie z wielu punktów lokalizuje odcinek z problemem.'],
  ['Przygotuj tabelę korelacji żądania boss-001 w czterech źródłach (pcap P1, conntrack, log Caddy, log Spring Boot) z kolumnami: czas, adres źródłowy, port, identyfikator, uwagi.', 'Przykład wiersza Caddy: 14:02:11.482, remote_ip 10.20.0.2, remote_port 45678, X-Request-ID boss-001, uri /users/42, status 200. Spring: 14:02:11.490, remoteAddr 10.20.0.2 (po konfiguracji RemoteIpValve) lub 10.30.0.10 (bez), requestId boss-001. conntrack: 10.10.1.10:45678 ↔ 10.20.0.2:45678. pcap P1: ramka N, 10.10.1.10:45678 → 10.20.0.10:80.'],
  ['Zrób przegląd raportu kolegi (peer review): sprawdź, czy każdy wniosek ma odwołanie do ramki, czy tabela zmian zawiera mechanizm każdej zmiany i czy wniosek obrońcy wymienia logi NAT i DHCP.', 'Lista kontrolna recenzji: odwołania do ramek; spójność wartości między tabelą a pcap; poprawna interpretacja TTL w P3 i XFF; sumy SHA-256 zgodne z plikami; wniosek obrońcy obejmuje czas (NTP), NAT, proxy, aplikację, DHCP/802.1X oraz ryzyko fałszowania XFF.'],
  ['Posprzątaj lab i potwierdź, że nie zostały po nim sieci, kontenery i pliki z danymi. Dlaczego to element dobrej praktyki?', 'docker compose down --remove-orphans; docker network ls (brak sieci lab), usunięcie lub zaszyfrowane zarchiwizowanie katalogu cap/. Higiena labów zapobiega konfliktom adresów w kolejnych ćwiczeniach, a pliki pcap — nawet z danymi testowymi — są artefaktami, które trzeba świadomie przechowywać lub usuwać.'],
];
for (const [q, a] of extra) ex(q, 'Sprawdź w swoich plikach i opisz z odwołaniem do ramek.', a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(83);
const fieldsQ = [['srcMac', 'MAC źródłowy'], ['dstMac', 'MAC docelowy'], ['srcIp', 'adres IP źródłowy'], ['dstIp', 'adres IP docelowy'], ['srcPort', 'port źródłowy TCP'], ['dstPort', 'port docelowy TCP'], ['ttl', 'TTL']];
for (const p of POINTS) {
  for (const [k, label] of fieldsQ) {
    const v = String(p[k]);
    const pool = [...new Set(POINTS.map((x) => String(x[k])).concat(Object.values(NODES).map((n) => (k.includes('Mac') ? n.mac : k.includes('Ip') ? n.ip : null)).filter(Boolean)).concat(k === 'ttl' ? ['62', '65', '128'] : []).concat(k.includes('Port') ? ['443', '53', '22', String(CLIENT_PORT + 1)] : []))].filter((x) => x !== v);
    add(`Jaki jest ${label} żądania HTTP w punkcie ${p.id} (${p.where})?`, v, pool.slice(0, 3), `${p.id}: ${label} = ${v}.`);
  }
}
add('Który element labu zmienia adres IP źródłowy żądania między P1 a P2?', 'Router z regułą masquerade (NAT)', ['Reverse proxy', 'Aplikacja Spring Boot', 'Przełącznik (bridge) Dockera'], 'Masquerade podmienia adres źródłowy na adres interfejsu wyjściowego.');
add('Który element labu tworzy nowe połączenie TCP?', 'Reverse proxy', ['Router z NAT', 'Bridge Dockera', 'Laptop przy każdym pakiecie'], 'Proxy kończy połączenie klienta i otwiera własne do backendu.');
add('Gdzie zapisane jest powiązanie 10.10.1.10:45678 ⇄ 10.20.0.2:45678?', 'W tablicy conntrack routera', ['W logu aplikacji', 'W tablicy ARP proxy', 'W nagłówku X-Forwarded-For'], 'Tylko urządzenie NAT zna translację.');
add('Jaki adres zapisze w X-Forwarded-For reverse proxy w tym labie?', NODES.rwan.ip, [NODES.client.ip, NODES.papp.ip, NODES.pwan.ip], 'Proxy widzi połączenie przychodzące z adresu po NAT.');
add('Co zwróci request.getRemoteAddr() w aplikacji bez konfiguracji forward-headers?', NODES.papp.ip, [NODES.client.ip, NODES.rwan.ip, NODES.app.ip], 'Bez przetwarzania XFF aplikacja widzi adres proxy.');
add('Dlaczego TTL w P3 wynosi 64, a nie 62?', 'Proxy tworzy nowy pakiet w nowym połączeniu z domyślnym TTL swojego systemu', ['Bo proxy zwiększa TTL o 2', 'Bo NAT resetuje TTL', 'Bo aplikacja ustawia TTL'], 'TTL zmniejsza się tylko przy przekazywaniu pakietu przez router.');
add('Dlaczego laptop wysyła ramkę z żądaniem do MAC routera?', 'Bo proxy jest w innej podsieci, więc next hopem jest brama domyślna', ['Bo router ma najkrótszy MAC', 'Bo proxy nie ma adresu MAC', 'Bo ARP jest wyłączony'], 'IP docelowy pozostaje adresem proxy, MAC docelowy to brama.');
add('Czy w P2 zobaczysz zapytanie ARP wysłane przez laptop?', 'Nie — ARP działa tylko w obrębie segmentu L2 laptopa', ['Tak, ARP przechodzi przez routery', 'Tak, ale z adresem NAT', 'Tylko przy IPv6'], 'W P2 może pojawić się ARP routera o MAC proxy.');
add('Port źródłowy klienta po masquerade w P2 zwykle:', 'Pozostaje taki sam, o ile nie koliduje z innym połączeniem', ['Zawsze zmienia się na 1024', 'Zmienia się na 80', 'Znika z nagłówka'], 'Zmiana portu (PAT) następuje przy kolizji.');
add('Które pole najlepiej łączy wpis logu proxy z wpisem logu aplikacji?', 'Identyfikator żądania (X-Request-ID / traceparent)', ['TTL', 'Adres MAC', 'Port docelowy 80'], 'Identyfikator przechodzi przez całą ścieżkę i trafia do MDC.');
add('Jaki skutek ma zaufanie aplikacji do pierwszego wpisu X-Forwarded-For przysłanego przez klienta?', 'Klient może podać dowolny adres i obejść allow-listę IP lub sfałszować logi', ['Brak skutków', 'Spadek wydajności', 'Błąd TLS'], 'Ufaj tylko wpisom dodanym przez własne proxy.');
add('Do czego służy w Spring Boot server.tomcat.remoteip.internal-proxies?', 'Określa adresy proxy, którym ufamy przy przetwarzaniu X-Forwarded-For', ['Blokuje ruch z proxy', 'Ustawia port serwera', 'Włącza HTTP/2'], 'RemoteIpValve pomija zaufane proxy, idąc od prawej strony XFF.');
add('Która opcja Docker Compose odcina sieć od wyjścia do Internetu?', 'internal: true', ['external: true', 'driver: host', 'attachable: true'], 'Lab jest izolowany — ruch nie wydostanie się poza host.');
add('Jakim poleceniem wyświetlisz tablicę translacji NAT na routerze Linux?', 'conntrack -L', ['ip neigh', 'ss -tlnp', 'arp -a'], 'Alternatywnie /proc/net/nf_conntrack.');
add('Dlaczego przed testem czyści się cache ARP laptopa?', 'Aby w przechwyceniu pojawiła się wymiana ARP o MAC bramy', ['Aby przyspieszyć ping', 'Aby wyłączyć NAT', 'Aby zmienić TTL'], 'Przy ciepłym cache ARP nie zostanie wysłany.');
add('Jaki element w prawdziwej chmurze AWS pełni rolę routera z NAT dla instancji w podsieci prywatnej?', 'NAT Gateway', ['Internet Gateway dla podsieci prywatnej', 'Security Group', 'Route 53'], 'Translacje widać w VPC Flow Logs.');
add('Jaki element AWS odpowiada reverse proxy z labu (nowe połączenie, X-Forwarded-For)?', 'Application Load Balancer', ['Network Load Balancer w trybie TCP passthrough', 'NAT Gateway', 'VPC peering'], 'ALB kończy połączenia i dodaje XFF; NLB domyślnie przekazuje połączenia na poziomie L4.');
add('Których logów potrzebuje firma, by wskazać laptop jako źródło żądania widocznego w aplikacji?', 'Logów aplikacji, proxy, NAT/firewalla i DHCP, zsynchronizowanych czasowo', ['Tylko logów aplikacji', 'Tylko logów DNS', 'Wystarczy pcap z P3'], 'Każdy punkt zmieniający adresy musi zostawić ślad.');
add('Co oznacza w pliku pcap P3 nowy handshake SYN, SYN-ACK, ACK między 10.30.0.10 a 10.30.0.20?', 'Proxy otwiera osobne połączenie do aplikacji', ['Retransmisję połączenia klienta', 'Atak SYN flood', 'Odpowiedź NAT'], 'To dowód, że proxy działa w warstwie 7.');
add('Dlaczego w P3 przy kolejnych żądaniach może nie być nowego handshake’u?', 'Proxy utrzymuje połączenia keep-alive do backendu i używa ich ponownie', ['Bo NAT zapamiętał połączenie', 'Bo aplikacja wyłącza TCP', 'Bo TTL wygasł'], 'Pula połączeń proxy jest niezależna od połączeń klientów.');
add('Laptop próbuje połączyć się bezpośrednio z 10.30.0.20. Czego się spodziewasz?', 'Braku połączenia — router nie ma trasy do sieci app (segmentacja)', ['Połączenia przez NAT', 'Połączenia przez ARP', 'Odpowiedzi od proxy z kodem 200'], 'Jedyna droga do aplikacji prowadzi przez proxy.');
add('Co zmienia się między P1 a P2 w nagłówku TCP?', 'Suma kontrolna (z powodu zmiany adresu IP w pseudo-nagłówku)', ['Numer sekwencyjny', 'Port docelowy', 'Flagi'], 'Suma kontrolna TCP obejmuje pseudo-nagłówek z adresami IP.');
add('Jak w tshark wypisać nagłówek X-Forwarded-For z żądań HTTP?', "tshark -r p3.pcap -Y http.request -T fields -e http.x_forwarded_for", ["tshark -r p3.pcap -e xff", "tcpdump -r p3.pcap xff", "tshark -r p3.pcap -z xff"], 'Pole dekodera HTTP: http.x_forwarded_for.');
add('Jaką rolę pełni X-Request-ID w tym labie?', 'Pozwala odnaleźć to samo żądanie w logach proxy, aplikacji i w pcap', ['Szyfruje żądanie', 'Zastępuje uwierzytelnienie', 'Zmienia trasę'], 'Identyfikator przekazywany przez wszystkie warstwy i zapisywany w MDC.');
add('Który element jest punktem kontroli i logowania całego ruchu do aplikacji w tym labie?', 'Reverse proxy', ['Laptop', 'Bridge sieci lan', 'Interfejs pętli zwrotnej'], 'Tak projektuje się DMZ: jedna kontrolowana droga do usług wewnętrznych.');
add('W IPv6 (bez NAT) X-Forwarded-For w P3 zawierałby:', 'Prawdziwy adres IPv6 laptopa', ['Adres routera', 'Adres proxy', 'Pusty ciąg'], 'Brak translacji adresów — proxy widzi źródło bezpośrednio.');
add('Dlaczego w P2 adres docelowy żądania nadal wynosi 10.20.0.10?', 'NAT typu masquerade zmienia tylko adres źródłowy', ['Bo router zawsze kieruje do bramy', 'Bo proxy podmienia adres', 'Bo TTL jest za niski'], 'Zmianę adresu docelowego robi DNAT (np. port forwarding).');
add('Który plik dołącza się do pcapów jako dowód integralności?', 'SHA256SUMS z sumami SHA-256 plików', ['Plik z kluczami TLS', 'Zrzut ekranu Wiresharka', 'Plik docker-compose.yml'], 'Skrót pozwala wykazać, że plik nie był modyfikowany.');
add('Co oznacza „połączenie 1” w P1 i P2 w tabeli lekcji?', 'To samo połączenie TCP widziane przed i po NAT', ['Dwa różne połączenia', 'Połączenie UDP', 'Połączenie proxy z aplikacją'], 'NAT nie tworzy nowego połączenia, tylko tłumaczy adresy w istniejącym.');
add('Po co w raporcie z Bossa odwołania do numerów ramek?', 'Aby każdy wniosek był weryfikowalny w dostarczonym pliku pcap', ['Bo Wireshark tego wymaga', 'Bo numery ramek szyfrują raport', 'Bo tak jest szybciej'], 'Weryfikowalność to podstawa pracy analitycznej i dowodowej.');
add('Jaka jest główna lekcja Bossa dla analityka SOC?', 'Identyfikacja źródła żądania wymaga korelacji logów z każdego punktu zmieniającego adresy', ['Wystarczy log aplikacji', 'NAT zapewnia bezpieczeństwo', 'X-Forwarded-For zawsze wskazuje klienta'], 'Atrybucja = pcap/NetFlow + NAT + proxy + aplikacja + DHCP + czas.');
add('Które polecenie ustali, który interfejs routera należy do sieci lan?', 'ip -br addr', ['ip route get 8.8.8.8', 'ss -s', 'nft list ruleset'], 'Docker nie gwarantuje kolejności eth0/eth1.');
add('Co powinno zrobić proxy brzegowe z nagłówkiem X-Forwarded-For otrzymanym od klienta z Internetu?', 'Nie ufać mu: usunąć lub nadpisać zgodnie z listą zaufanych proxy', ['Przekazać go bez zmian jako pierwszy wpis', 'Zamienić na adres MAC', 'Zaszyfrować'], 'Klient kontroluje ten nagłówek w całości.');
add('Jaki typ NAT stosuje się, aby udostępnić usługę z sieci wewnętrznej na adresie zewnętrznym (port forwarding)?', 'DNAT (zmiana adresu docelowego)', ['Masquerade', 'SNAT', 'NAT64'], 'Masquerade/SNAT zmieniają adres źródłowy ruchu wychodzącego.');
add('Ile połączeń TCP minimalnie uczestniczy w obsłudze jednego żądania laptopa w tym labie?', '2', ['1', '3', '4'], 'Klient ↔ proxy (przez NAT) oraz proxy ↔ aplikacja.');
add('Dlaczego segmentacja w labie (brak trasy z lan do app) jest ważna dla obrony?', 'Wymusza przejście przez proxy, gdzie ruch jest kontrolowany i logowany', ['Przyspiesza aplikację', 'Wyłącza TLS', 'Zastępuje uwierzytelnianie'], 'Ogranicza też ruch boczny po przejęciu laptopa.');
const RESP = [
  { id: 'P1', srcMac: NODES.rlan.mac, dstMac: NODES.client.mac, srcIp: NODES.pwan.ip, dstIp: NODES.client.ip, srcPort: '80', dstPort: String(CLIENT_PORT), ttl: '63' },
  { id: 'P2', srcMac: NODES.pwan.mac, dstMac: NODES.rwan.mac, srcIp: NODES.pwan.ip, dstIp: NODES.rwan.ip, srcPort: '80', dstPort: String(CLIENT_PORT), ttl: '64' },
  { id: 'P3', srcMac: NODES.app.mac, dstMac: NODES.papp.mac, srcIp: NODES.app.ip, dstIp: NODES.papp.ip, srcPort: '8080', dstPort: String(PROXY_EPH), ttl: '64' },
];
for (const r of RESP) {
  for (const [k, label] of fieldsQ) {
    const v = String(r[k]);
    const pool = [...new Set(RESP.map((x) => String(x[k])).concat(POINTS.map((x) => String(x[k]))).concat(Object.values(NODES).map((n) => (k.includes('Mac') ? n.mac : k.includes('Ip') ? n.ip : null)).filter(Boolean)).concat(k === 'ttl' ? ['62', '65', '128'] : []).concat(k.includes('Port') ? ['443', '53', String(CLIENT_PORT + 1)] : []))].filter((x) => x !== v);
    add(`Jaki jest ${label} odpowiedzi HTTP 200 w punkcie ${r.id}?`, v, pool.slice(0, 3), `${r.id} (odpowiedź): ${label} = ${v}.`);
  }
}
add('Jaki TTL ma pakiet ICMP echo request laptopa w P1?', '64', ['63', '62', '128'], 'Laptop (Linux) wysyła z TTL 64.');
add('Jaki TTL ma pakiet ICMP echo request laptopa w P2?', '63', ['64', '62', '1'], 'Router zmniejszył TTL o 1.');
add('Jaki TTL ma odpowiedź ICMP echo reply od proxy w P2?', '64', ['63', '62', '128'], 'Proxy wysyła odpowiedź z domyślnym TTL 64; w P2 jeszcze nie przeszła przez router.');
add('Jaki TTL ma odpowiedź ICMP echo reply od proxy w P1?', '63', ['64', '62', '65'], 'Router zmniejszył TTL odpowiedzi o 1.');
const who = [
  ['MAC źródłowy między P1 a P2', 'Router (buduje nową ramkę na interfejsie WAN)'], ['IP źródłowy między P1 a P2', 'Router — reguła masquerade (NAT)'], ['TTL między P1 a P2', 'Router (zmniejsza o 1 przy przekazywaniu)'],
  ['IP docelowy między P2 a P3', 'Reverse proxy (nowe połączenie do aplikacji)'], ['port docelowy między P2 a P3', 'Reverse proxy (łączy się z backendem na 8080)'], ['IP docelowy odpowiedzi między P2 a P1', 'Router — odwrotna translacja NAT z conntrack'],
  ['nagłówek X-Forwarded-For między P2 a P3', 'Reverse proxy (dopisuje adres swojego klienta)'], ['port źródłowy między P2 a P3', 'Reverse proxy (nowy port efemeryczny)'], ['MAC docelowy między P2 a P3', 'Reverse proxy (ramka do MAC aplikacji w sieci app)'], ['suma kontrolna nagłówka IP między P1 a P2', 'Router (zmiana TTL i adresu wymaga przeliczenia)'],
];
const whoAll = ['Router (buduje nową ramkę na interfejsie WAN)', 'Router — reguła masquerade (NAT)', 'Reverse proxy (nowe połączenie do aplikacji)', 'Aplikacja Spring Boot', 'Laptop', 'Bridge sieci wan'];
for (const [f, a] of who) add(`Który element i dlaczego zmienia: ${f}?`, a, whoAll.filter((x) => x !== a).slice(0, 3), a + '.');
const sees = [
  ['router', 'Adresy IP i porty obu stron połączenia klient–proxy oraz translację NAT, ale nie treść HTTPS'],
  ['reverse proxy', 'Adres po NAT (10.20.0.2), pełne żądanie HTTP i odpowiedź aplikacji'],
  ['aplikacja Spring Boot', 'Połączenie od proxy, żądanie HTTP i nagłówek X-Forwarded-For z adresem po NAT'],
  ['laptop', 'Tylko połączenie z adresem proxy 10.20.0.10 — nic nie wie o sieci app ani o NAT'],
];
const seesAll = sees.map((x) => x[1]);
for (const [d, a] of sees) add(`Co widzi ${d} w tym labie?`, a, seesAll.filter((x) => x !== a), a + '.');
add('Który plik pcap pozwoli ustalić prywatny adres laptopa bez dostępu do routera?', 'P1 — przechwycony w sieci LAN przed NAT', ['P2', 'P3', 'Żaden — adres jest zawsze ukryty'], 'Po NAT adres prywatny znika z pakietów.');
add('Który plik pcap pokaże nagłówek X-Forwarded-For?', 'P3', ['P1', 'P2', 'Wszystkie trzy'], 'Nagłówek dodaje proxy w nowym połączeniu do aplikacji.');
add('W którym punkcie zobaczysz handshake TCP z portem docelowym 8080?', 'P3', ['P1', 'P2', 'W P1 i P2'], 'Port 8080 dotyczy wyłącznie połączenia proxy–aplikacja.');
add('Po zmianie labu na IPv6 bez NAT, co zastąpi ARP w P1?', 'Neighbor Discovery (ICMPv6 Neighbor Solicitation/Advertisement)', ['RARP', 'DHCPv6', 'Nic — IPv6 nie potrzebuje adresów MAC'], 'ND pełni w IPv6 rolę ARP.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
