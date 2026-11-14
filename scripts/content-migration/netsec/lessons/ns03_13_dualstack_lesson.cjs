// Generuje lekcje _ns_03_ip_i_routing/13_DualStackAndTransition.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_13_dualstack_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/13_DualStackAndTransition.json
// Kod Javy (HappyEyeballs) jest w ns03_13_HappyEyeballs.java.txt — przetestowany na JDK 25.
const fs = require('fs');
const path = require('path');
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { sequential, happyEyeballs, nat64, fromNat64, SCEN } = require('./ns03_13_dualstack_model.cjs');
const buildQuiz = require('./ns03_13_dualstack_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const JAVA_HE = fs.readFileSync(path.join(__dirname, 'ns03_13_HappyEyeballs.java.txt'), 'utf8').trim();
const scenLine = (k, to) => { const s = sequential(SCEN[k], to), h = happyEyeballs(SCEN[k]); return `${k.padEnd(15)} sekwencyjnie: ${String(s.total).padStart(6)} ms   Happy Eyeballs: ${String(h.total).padStart(4)} ms (wygrywa IPv${h.winner})`; };

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Dwa protokoły naraz: co może pójść źle', ...img('ns03-13-happy-eyeballs.svg', 'Porównanie: klient sekwencyjny próbuje IPv6, czeka 10 sekund do timeoutu i dopiero łączy się po IPv4 po 10 028 ms; Happy Eyeballs v2 po 250 ms startuje równolegle IPv4 i łączy się po 278 ms. Klasyczne klienty JDK łączą się z pierwszym adresem z resolvera.'),
    body:
`Przez najbliższe lata większość sieci będzie **dual-stack**: hosty mają adresy IPv4 i IPv6, a nazwy DNS — rekordy **A** i **AAAA**. Klient musi wybrać, którym protokołem się połączyć. Gdy oba działają — wszystko jedno. Problem pojawia się, gdy **rekord AAAA istnieje, a IPv6 po drodze nie działa** (zepsuty router, firewall gubiący IPv6, VPN tunelujący tylko IPv4, błędna trasa w chmurze): klient, który „uczciwie” spróbuje najpierw IPv6, czeka do timeoutu połączenia, zanim spróbuje IPv4.

Dla użytkownika przeglądarki to niewidoczne — przeglądarki od lat stosują **Happy Eyeballs** (równoległe próby z niewielkim opóźnieniem). Dla aplikacji Java bywa to dramatyczne: klasyczne klienty JDK łączą się z **pierwszym adresem z resolvera**, więc „losowe” 10–30-sekundowe opóźnienia, timeouty w pulach połączeń i alerty „usługa wolna” mogą mieć jedną przyczynę: zepsute IPv6 przy działającym AAAA.

Druga strona medalu: sieci **IPv6-only** (operatorzy mobilni, nowe centra danych, chmury oszczędzające na IPv4) korzystają z **NAT64/DNS64** i **464XLAT**, żeby dotrzeć do starego Internetu IPv4 — co psuje aplikacje z wpisanymi na sztywno adresami IPv4.

W tej lekcji: kolejność adresów (RFC 6724), **Happy Eyeballs v2** (RFC 8305), właściwości \`java.net.preferIPv6Addresses\` i \`preferIPv4Stack\`, cache DNS w JVM, IPv6-only, NAT64/DNS64, 464XLAT, tunelowanie (6to4, Teredo — i dlaczego są ryzykiem) oraz **Failure Lab z planu**: zepsuty IPv6 przy działającym AAAA — zmierz opóźnienie klienta Java i napraw. Cele: Network+ N10-009 (dual-stack, tunelowanie, NAT64), CCNA v2 (IPv6 w sieciach dual-stack), Security+ (tunele omijające kontrole).` },

  { type: 'DEFINITION', heading: 'Słownik przejścia na IPv6', body:
`- **Dual-stack** — host/sieć z równoczesnym IPv4 i IPv6; aplikacja wybiera protokół na podstawie adresów z DNS.
- **RFC 6724 (Default Address Selection)** — algorytm sortowania adresów docelowych i wyboru źródłowego: unikaj nieosiągalnych, preferuj zgodny zakres, wyższą *precedence* (domyślnie IPv6 GUA 40 > IPv4 35), najdłuższy wspólny prefiks. Konfiguracja: Linux \`/etc/gai.conf\`, Windows \`netsh interface ipv6 show prefixpolicies\`.
- **Happy Eyeballs v2 (RFC 8305)** — klient przeplata rodziny adresów (v6, v4, v6…), startuje pierwszą próbę, a kolejną po **250 ms** (Connection Attempt Delay) lub natychmiast po błędzie; wygrywa pierwsze udane połączenie, pozostałe są zamykane. Zapytania A i AAAA wysyłane równolegle.
- **\`java.net.preferIPv6Addresses\`** — \`false\` (domyślnie: IPv4 pierwsze), \`true\` (IPv6 pierwsze), \`system\` (JDK 9+, kolejność systemowa).
- **\`java.net.preferIPv4Stack=true\`** — JVM używa tylko gniazd IPv4 (wyłącza IPv6 w aplikacji).
- **Cache DNS JVM** — \`networkaddress.cache.ttl\` (domyślnie 30 s bez SecurityManagera) i \`networkaddress.cache.negative.ttl\` (10 s) w \`java.security\` lub \`Security.setProperty\`.
- **IPv6-only** — sieć bez IPv4 na hostach; dostęp do IPv4 przez translację.
- **DNS64 (RFC 6147)** — resolver, który dla nazw bez AAAA syntetyzuje AAAA z prefiksu NAT64 i adresu IPv4.
- **NAT64 (RFC 6146)** — stanowa translacja IPv6 → IPv4 z prefiksem **64:ff9b::/96** (well-known, RFC 6052) lub własnym; np. 140.82.121.4 → **${nat64('140.82.121.4')}**.
- **464XLAT (RFC 6877)** — CLAT na urządzeniu (tłumaczy lokalny IPv4 na IPv6) + PLAT (NAT64 u operatora); pozwala działać aplikacjom z literałami IPv4. Standard w sieciach mobilnych (Android, iOS).
- **Tunele** — 6in4 (protokół 41), 6to4 (2002::/16, wycofany), Teredo (UDP 3544, 2001::/32, wycofany), ISATAP; przenoszą IPv6 w IPv4 (i potrafią omijać firewalle IPv4).
- **MAP-T/MAP-E, DS-Lite** — mechanizmy operatorskie dostarczania IPv4 przez sieć IPv6 (IPv4-as-a-service).` },

  { type: 'ANALOGY', heading: 'Dwie kasy w sklepie', body:
`Sklep ma dwie kasy: nową (IPv6) i starą (IPv4). Szyld nad wejściem (rekord AAAA) zaprasza do nowej.
- **Klient sekwencyjny** grzecznie staje w kolejce do nowej kasy. Kasa jest zamknięta, ale nikt tego nie mówi — klient stoi, aż straci cierpliwość (timeout 10–30 s), i dopiero wtedy idzie do starej. Każdy kolejny zakup — to samo.
- **Happy Eyeballs** to klient, który staje do nowej kasy, ale po ćwierć sekundy wysyła kolegę do starej. Kto pierwszy dojdzie do kasjera, ten płaci; drugi rezygnuje. Gdy nowa kasa działa — zwykle wygrywa (bo startuje pierwsza); gdy nie — strata to ćwierć sekundy.
- **\`preferIPv6Addresses=false\`** to klient, który z przyzwyczajenia zawsze idzie najpierw do starej kasy — nie cierpi, gdy nowa nie działa, ale nie korzysta z nowej nawet wtedy, gdy jest szybsza.
- **NAT64/DNS64** to sklep, w którym zostały tylko nowe kasy, a obsługa przy wejściu (DNS64) wydaje klientom „nowe” numerki także dla towarów z magazynu starego typu; tłumacz na zapleczu (NAT64) obsługuje stare zamówienia. Klient z kartką „idź do kasy nr 4 na starym piętrze” (literał IPv4) się gubi — chyba że ma w kieszeni własnego tłumacza (CLAT, 464XLAT).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Ile trwa nawiązanie połączenia w pięciu scenariuszach', ...img('ns03-13-java.svg', 'Właściwości Javy wpływające na dual-stack: preferIPv6Addresses false (domyślnie, najpierw IPv4), true (najpierw IPv6), system (kolejność systemowa RFC 6724) oraz preferIPv4Stack (tylko IPv4). Cache DNS JVM: networkaddress.cache.ttl i negative.ttl.'),
    body:
`Model z lekcji: lista adresów w kolejności z resolvera (IPv6 pierwsze), connectTimeout klienta 10 s, RTT ~30 ms. „Blackhole” = pakiety giną bez odpowiedzi (najgorszy przypadek), „unreachable” = szybki błąd (ICMPv6 unreachable/RST). Zwróć uwagę, że przy szybkim błędzie nawet klient sekwencyjny działa dobrze — problem tworzy **cisza** po drodze.`,
    code:
`${Object.keys(SCEN).map((k) => scenLine(k, 10000)).join('\n')}

Przebieg Happy Eyeballs dla v6blackhole:
  ${happyEyeballs(SCEN.v6blackhole).log.join('\n  ')}
Przebieg Happy Eyeballs dla v6slow (IPv6 działa, ale ma RTT 400 ms):
  ${happyEyeballs(SCEN.v6slow).log.join('\n  ')}
  → wygrywa IPv4, bo zestawił połączenie wcześniej (start 250 + 30 < 400)` },

  { type: 'CODE_BASIC', heading: 'Diagnoza dual-stack: DNS, trasy, curl i Java', body:
`Szybki zestaw poleceń, który odpowiada na pytania: czy nazwa ma AAAA, czy IPv6 w ogóle działa z tego hosta, ile trwa połączenie każdym protokołem i co wybiera JVM.`,
    code:
`dig +short A api.example.com ; dig +short AAAA api.example.com     # czy są oba rekordy
getent ahosts api.example.com                                        # kolejność adresów wg systemu (RFC 6724, gai.conf)
ip -6 route get 2001:db8::10                                         # czy jest trasa IPv6 (default via fe80::…)
curl -6 -sv -o /dev/null --connect-timeout 5 https://api.example.com 2>&1 | grep -E 'Trying|Connected|timed out'
curl -4 -s  -o /dev/null -w 'v4 connect=%{time_connect}s\\n' https://api.example.com
curl -s -o /dev/null -w 'auto connect=%{time_connect}s ip=%{remote_ip}\\n' https://api.example.com   # curl: Happy Eyeballs (200 ms)
# Windows: Resolve-DnsName api.example.com ; Test-NetConnection api.example.com -Port 443

# Java — co zwróci resolver i w jakiej kolejności
jshell> java.util.Arrays.toString(java.net.InetAddress.getAllByName("api.example.com"))
java -Djava.net.preferIPv6Addresses=system -jar app.jar        # kolejność jak w systemie
java -Djava.net.preferIPv6Addresses=false -jar app.jar         # IPv4 najpierw (domyślne)
# cache DNS JVM (np. w kodzie startowym):  java.security.Security.setProperty("networkaddress.cache.ttl", "30");` },

  { type: 'STEP_BY_STEP', heading: 'Failure Lab z planu: zepsuty IPv6 przy działającym AAAA — zmierz i napraw', ...img('ns03-13-rfc6724.svg', 'Tabela domyślnej polityki RFC 6724: ::1 precedence 50, ::/0 (IPv6) 40, ::ffff:0:0/96 (IPv4) 35, 6to4 30, Teredo 5, ULA 3. Reguły: unikanie nieosiągalnych, zgodny zakres, wyższa precedence, najdłuższy wspólny prefiks.'),
    body:
`Lab na jednym Linuksie (root). Tworzymy nazwę \`api.lab\` z rekordami A i AAAA, usługę dostępną tylko po IPv4 i „czarną dziurę” dla IPv6 — dokładnie jak źle skonfigurowany firewall gubiący IPv6.
1. **Usługa IPv4**: \`python3 -m http.server 8080 --bind 127.0.0.1 &\`.
2. **Czarna dziura IPv6**: namespace \`dead\` połączony vethem: \`ip netns add dead; ip link add v-dead type veth peer name eth0 netns dead; ip link set v-dead up; ip -n dead link set eth0 up\`; trasa: \`ip -6 route add 2001:db8:dead::/48 via fe80::<link-local eth0 w dead> dev v-dead\` (adres sprawdź \`ip -n dead -6 addr\`). W \`dead\` forwarding IPv6 jest wyłączony, więc pakiety do 2001:db8:dead::10 są **po cichu porzucane** (prawdziwy blackhole — ND do bramy działa, SYN ginie).
3. **Nazwa**: w \`/etc/hosts\` dopisz \`2001:db8:dead::10 api.lab\` i \`127.0.0.1 api.lab\`. Sprawdź \`getent ahosts api.lab\` — IPv6 jest pierwszy (precedence 40 > 35).
4. **Pomiar curl**: \`curl -6 --connect-timeout 10 http://api.lab:8080/\` → timeout po 10 s; \`curl -s -o /dev/null -w '%{time_connect} %{remote_ip}\\n' http://api.lab:8080/\` → ~0,2 s i \`127.0.0.1\` (Happy Eyeballs w curl).
5. **Pomiar Java**: prosty program \`new Socket()\` + \`connect(new InetSocketAddress("api.lab", 8080), 10_000)\` z \`-Djava.net.preferIPv6Addresses=true\` → **~10 s i SocketTimeoutException** (InetSocketAddress bierze pierwszy adres i nie próbuje kolejnych); z \`=false\` → kilka ms; z \`=system\` → jak w systemie (IPv6 pierwsze → 10 s).
6. **Naprawy — od najlepszej**: (a) napraw IPv6 na ścieżce albo **usuń rekord AAAA**, jeśli usługa nie działa po IPv6 (w labie: usuń wpis z /etc/hosts); (b) klient z **Happy Eyeballs** (kod obok: łączy się w ~250 ms mimo blackhole) lub biblioteka z fast fallback (OkHttp); (c) **krótki connectTimeout + pętla po \`getAllByName\`** (gorzej: 2 s straty na każdy martwy adres); (d) obejścia: \`preferIPv6Addresses=false\` lub \`gai.conf\` — tylko gdy nie możesz naprawić sieci.
7. **Sprzątanie**: \`ip netns del dead\`, usuń wpisy z /etc/hosts, zakończ serwer. **Notatka**: czasy dla każdej konfiguracji i wybrana naprawa z uzasadnieniem.` },

  { type: 'NOTE', heading: 'IPv6-only, NAT64/DNS64 i 464XLAT', ...img('ns03-13-nat64.svg', 'Sieć IPv6-only: klient pyta DNS64 o AAAA, resolver zwraca syntetyczny adres 64:ff9b::8c52:7904 utworzony z 140.82.121.4, klient łączy się po IPv6 z bramą NAT64, która tłumaczy ruch na IPv4 do serwera. Literały IPv4 wymagają 464XLAT.'),
    body:
`W sieci **IPv6-only** host nie ma adresu IPv4. Gdy chce połączyć się z serwisem, który ma tylko rekord A:
1. Resolver **DNS64** nie znajduje AAAA, więc **syntetyzuje** go: prefiks 64:ff9b::/96 + 32 bity IPv4 (140.82.121.4 → ${nat64('140.82.121.4')}; z powrotem: ${nat64('192.0.2.33')} → ${fromNat64(nat64('192.0.2.33'))}).
2. Klient łączy się po IPv6 z tym adresem; trasa do 64:ff9b::/96 prowadzi do bramy **NAT64**.
3. NAT64 tłumaczy nagłówki (stanowo, jak PAT) na IPv4 i z powrotem.

**Co się psuje:** aplikacje z **literałami IPv4** (\`http://203.0.113.5/\`, \`jdbc:postgresql://10.0.0.5\`) — DNS64 nie ma czego syntetyzować; aplikacje, które same sprawdzają „czy mam adres IPv4” zamiast próbować połączenia; protokoły z adresami IPv4 w treści; walidujące DNSSEC resolvery (syntetyczne AAAA nie mają podpisu). Rozwiązaniem po stronie urządzenia jest **464XLAT**: CLAT daje aplikacjom lokalny interfejs IPv4 i tłumaczy go na IPv6 (dlatego w sieciach mobilnych z IPv6-only Android i iOS działają z każdą aplikacją).

**W chmurze**: AWS obsługuje podsieci IPv6-only z DNS64 (opcja w podsieci) i NAT64 w NAT Gateway; Kubernetes dual-stack i IPv6-only to już normalne konfiguracje. **Wytyczne dla aplikacji Java**: zawsze nazwy DNS zamiast literałów; nie zakładaj obecności IPv4; testuj w sieci IPv6-only (Apple wymaga tego od aplikacji w App Store od 2016 r.).

**Tunele a bezpieczeństwo**: 6to4, Teredo (UDP 3544) i 6in4 (protokół 41) mogą przenosić ruch IPv6 przez firewall, który filtruje tylko IPv4 — kanał omijający kontrole i monitoring. Blokuj protokół 41 i UDP 3544 tam, gdzie nie są potrzebne, wyłącz Teredo w Windows (\`netsh interface teredo set state disabled\`), monitoruj ruch IPv6.` },

  { type: 'CODE_PRACTICAL', heading: 'Happy Eyeballs v2 w Javie (przetestowany)', body:
`Minimalna implementacja RFC 8305 na wątkach wirtualnych (JDK 21+): adresy z \`getAllByName\` są przeplatane rodzinami, kolejne próby startują co 250 ms lub natychmiast po błędzie, wygrywa pierwsze połączenie, pozostałe są zamykane. Test w \`main\`: usługa nasłuchuje tylko na 127.0.0.1, a klient z \`-Djava.net.preferIPv6Addresses=true\` próbuje najpierw \`::1\` — połączenie i tak zestawia się w kilkadziesiąt ms. W produkcji użyj biblioteki z wbudowanym mechanizmem (np. OkHttp 5 — *fast fallback*) albo opakuj tę logikę w fabrykę gniazd dla swojego klienta.`,
    code: JAVA_HE },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **„Co jakiś czas połączenie do API trwa 20 s”** — sprawdź AAAA i IPv6 na ścieżce; logi z adresem docelowym (IPv6 czy IPv4) i czasem \`connect\`.
- **Kontenery i Kubernetes**: węzeł z IPv6, pod bez trasy IPv6 (lub odwrotnie); usługa Service tylko IPv4 w klastrze dual-stack (\`ipFamilyPolicy: SingleStack\`).
- **Pule połączeń** (HikariCP, klienci HTTP): długi \`connectTimeout\` × martwy adres = wątki zablokowane, kaskadowe timeouty. Ustaw krótkie timeouty i monitoruj czas zestawiania połączeń.
- **Konfiguracja**: zawsze nazwy, nie literały; aplikacja ma słuchać na \`::\` (dual-stack); testy w CI także w sieci IPv6-only (Docker z siecią IPv6).
- **Cache DNS JVM**: po zmianie rekordów (usunięcie zepsutego AAAA) aplikacja może korzystać ze starych adresów przez \`networkaddress.cache.ttl\`; przy cache „na zawsze” (stare ustawienia z SecurityManagerem) — aż do restartu.

**Blue team:**
- **Tunele IPv6 omijające kontrole**: Teredo, 6to4, 6in4 — wykrywanie (Zeek: protokół 41, UDP 3544, ruch do 2001::/32 i 2002::/16), blokada na brzegu.
- **Widoczność IPv6**: polityki firewalla i reguły IDS muszą obejmować IPv6 tak samo jak IPv4 — inaczej dual-stack daje atakującemu „drugie, niepilnowane drzwi”.
- **Logowanie i korelacja**: ten sam użytkownik raz po IPv4 (przez NAT/CGNAT), raz po IPv6 (adres tymczasowy) — korelacja po sesji/koncie, nie po IP.
- **NAT64 w logach**: adresy 64:ff9b::/96 w logach serwera IPv6 oznaczają klientów IPv4 za translatorem (i odwrotnie — w logach IPv4 widać adres NAT64).
- Mapowanie: Network+ (dual-stack, tunelowanie, NAT64), CCNA (IPv6 w praktyce), Security+ (protokoły tunelowania), CySA+ (analiza ruchu).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Publikowanie AAAA bez działającego IPv6** (lub bez monitoringu IPv6) — połowa klientów „losowo” czeka na timeouty.
- **„Naprawa” przez \`preferIPv4Stack=true\` na zawsze** — wyłączasz IPv6 w aplikacji; w sieci IPv6-only aplikacja przestaje działać.
- **Długi \`connectTimeout\`** (domyślnie nieskończony lub minuty) — wzmacnia każdy problem z dual-stack.
- **\`new Socket(host, port)\` / \`InetSocketAddress(host, port)\`** — biorą pierwszy adres; brak próby kolejnych.
- **Literały IPv4 w konfiguracji** — nie działają w IPv6-only bez 464XLAT.
- **Sprawdzanie „czy mamy IPv4/IPv6” przez listę interfejsów** zamiast po prostu próby połączenia — błędne decyzje w sieciach z NAT64 lub z samym link-local.
- **Testy tylko w sieci IPv4** — błędy IPv6 wychodzą dopiero u klientów mobilnych.
- **Zostawione tunele (Teredo, 6to4)** — preferowane przez stare systemy i omijające kontrole.
- **Ignorowanie cache DNS JVM** — „usunęliśmy AAAA, a problem trwa jeszcze kilka minut/do restartu”.` },

  { type: 'CODE_WRONG', heading: 'Źle: połączenie do pierwszego adresu z długim timeoutem', body:
`Typowy kod i konfiguracja, które przy zepsutym IPv6 dają 30-sekundowe opóźnienia każdego nowego połączenia.`,
    code:
`// JVM: -Djava.net.preferIPv6Addresses=true  (ktoś „włączył IPv6”)
Socket s = new Socket();
s.connect(new InetSocketAddress("api.partner.com", 443), 30_000);   // tylko pierwszy adres (AAAA), 30 s timeout
// HikariCP: connectionTimeout=30000, sterownik JDBC bez connectTimeout
// konfiguracja: partner.url=https://203.0.113.5/api                   // literał IPv4 — nie działa w IPv6-only
// DNS: AAAA dla api.firma.pl opublikowany „na zapas”, IPv6 na load balancerze niewłączone` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: nazwy, krótkie timeouty, fallback i monitoring obu protokołów', body:
`Klient próbuje wszystkich adresów z szybkim fallbackiem, timeouty są krótkie, konfiguracja używa nazw, a rekordy AAAA są publikowane tylko dla usług z działającym i monitorowanym IPv6.`,
    code:
`Socket s = HappyEyeballs.connect("api.partner.com", 443, 250, 3_000);   // przeplot v6/v4, 250 ms, 3 s łącznie
// lub OkHttp 5: OkHttpClient.Builder().fastFallback(true) (domyślnie włączone)
// HikariCP: connectionTimeout=3000; JDBC: ?connectTimeout=3
// JVM: -Djava.net.preferIPv6Addresses=system   (spójnie z OS; naprawy rób w sieci, nie w JVM)
// konfiguracja: partner.url=https://api.partner.com/api                 // nazwa — działa w IPv6-only przez DNS64
// DNS: AAAA tylko z działającym IPv6 na LB; monitoring syntetyczny osobno dla IPv4 i IPv6 (curl -4 / curl -6)
// blackbox exporter: moduły http_2xx_v4 (preferred_ip_protocol: ip4) i http_2xx_v6 (ip6, ip_protocol_fallback: false)` },

  { type: 'WHEN_TO_USE', heading: 'Objaw → przyczyna → działanie', body:
`Ściąga diagnostyczna dla dual-stack i IPv6-only:`,
    code:
`OBJAW                                              PRZYCZYNA                                DZIAŁANIE
nowe połączenia czasem trwają ~connectTimeout       AAAA + zepsute IPv6, klient bez HE        napraw IPv6 / usuń AAAA; HE; krótki timeout
przeglądarka OK, aplikacja Java wolna               przeglądarka ma Happy Eyeballs, JDK nie   jw.; porównaj curl -6 i curl -4
działa w biurze, nie działa w sieci komórkowej      literał IPv4 / brak obsługi IPv6-only      nazwy DNS, test w IPv6-only, 464XLAT
po usunięciu AAAA problem trwa                      cache DNS JVM / resolvera                 networkaddress.cache.ttl, restart, TTL DNS
w logach adresy 64:ff9b::…                          klienci IPv4 za NAT64 (lub odwrotnie)     uwzględnij w allow-listach i analizie
ruch IPv6 do 2001::/32 lub 2002::/16                Teredo / 6to4 — tunel omijający kontrole   zablokuj UDP 3544 i protokół 41, wyłącz tunele
klaster dual-stack: pod nie widzi usługi po IPv6    Service SingleStack, brak trasy w CNI      ipFamilyPolicy, ipFamilies, trasy CNI` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Dual-stack: wybór protokołu zależy od kolejności adresów (RFC 6724: IPv6 GUA przed IPv4) i od klienta.
- **AAAA + zepsute IPv6** = opóźnienie równe connectTimeout dla klientów sekwencyjnych; **Happy Eyeballs v2** (przeplot, 250 ms) zmniejsza je do ~250 ms.
- Java: klasyczne klienty łączą się z pierwszym adresem; \`preferIPv6Addresses\` (false/true/system), \`preferIPv4Stack\`; cache DNS \`networkaddress.cache.ttl\`; krótkie timeouty i fallback w kodzie lub bibliotece (OkHttp fast fallback).
- IPv6-only: DNS64 syntetyzuje AAAA (64:ff9b::/96 + IPv4), NAT64 tłumaczy; literały IPv4 wymagają 464XLAT (CLAT).
- Tunele 6to4/Teredo/6in4 omijają firewalle IPv4 — blokuj i monitoruj; polityki bezpieczeństwa równoważnie dla IPv4 i IPv6.
- Naprawy: działające i monitorowane IPv6 albo brak AAAA; obejścia w JVM/gai.conf tylko tymczasowo.
- Następna lekcja: **OSPF w jednym obszarze** — sąsiedztwa, LSA, koszt i lab na FRR.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [k, to] of [['v6blackhole', 10000], ['v6blackhole', 30000], ['twoV6blackhole', 10000], ['v6unreachable', 10000], ['v6slow', 10000], ['healthy', 10000]]) {
  const s = sequential(SCEN[k], to), h = happyEyeballs(SCEN[k]);
  ex(`Scenariusz „${k}” (${SCEN[k].map((a) => `IPv${a.fam} ${a.state}${a.rtt ? ` ${a.rtt} ms` : ''}`).join(', ')}), connectTimeout ${to} ms. Ile potrwa nawiązanie połączenia przez klienta sekwencyjnego, a ile z Happy Eyeballs v2 (250 ms)?`,
    'Sekwencyjnie: sumuj czasy prób; HE: przeplot, kolejna próba po 250 ms lub od razu po błędzie, wygrywa najszybsza.',
    `Sekwencyjnie: ${s.total} ms (${s.log.join('; ')}). Happy Eyeballs: ${h.total} ms, wygrywa IPv${h.winner} (${h.log.join('; ')}).`);
}
for (const ip of ['140.82.121.4', '203.0.113.5']) {
  ex(`Jaki syntetyczny adres AAAA zwróci DNS64 (prefiks 64:ff9b::/96) dla serwera z adresem ${ip}?`, 'Ostatnie 32 bity = adres IPv4 w hex.', `${nat64(ip)} (ostatnie 32 bity: ${ip} w zapisie szesnastkowym).`);
}
for (const a of ['64:ff9b::cb00:7105', '64:ff9b::808:808', '64:ff9b::c000:221']) ex(`Klient w sieci IPv6-only łączy się z adresem ${a}. Jaki serwer IPv4 jest po drugiej stronie translatora NAT64?`, 'Ostatnie 32 bity adresu to IPv4.', `${fromNat64(a)} — DNS64 zsyntetyzował ten AAAA z rekordu A, a NAT64 przetłumaczy ruch na IPv4 do ${fromNat64(a)}.`);
const more = [
  ['Wykonaj Failure Lab z planu: odtwórz zepsuty IPv6 przy działającym AAAA, zmierz czas połączenia curl i klienta Java (preferIPv6Addresses=true/false/system) i zastosuj naprawę.', 'Namespace „dead” jako blackhole, /etc/hosts z A i AAAA.', 'Pomiary: curl -6 → timeout po connect-timeout; curl (auto) → ~0,2 s przez IPv4 (Happy Eyeballs); Java InetSocketAddress z preferIPv6Addresses=true → ~10 s i SocketTimeoutException (pierwszy adres to IPv6, brak kolejnych prób); false → kilka ms (IPv4 pierwszy); system → jak getent ahosts (IPv6 pierwszy) → ~10 s. Naprawy: usunięcie AAAA lub naprawa IPv6 (najlepsze), klient Happy Eyeballs (~250 ms), krótki timeout + pętla po getAllByName, obejście preferIPv6Addresses=false.'],
  ['Wyjaśnij, dlaczego przy szybkim błędzie IPv6 (ICMPv6 unreachable lub RST) nawet klient sekwencyjny działa dobrze, a przy „ciszy” — źle.', 'Natychmiastowy błąd vs timeout.', 'Szybki błąd (ICMPv6 Destination Unreachable, RST) zwraca do aplikacji wyjątek po kilku milisekundach, więc klient od razu próbuje kolejnego adresu (IPv4) — opóźnienie pomijalne. Cisza (firewall DROP, zepsuty router, brak trasy powrotnej) oznacza, że SYN ginie bez odpowiedzi; klient czeka cały connectTimeout (lub systemowe retransmisje SYN: na Linuksie ~127 s domyślnie), zanim uzna próbę za nieudaną. Dlatego REJECT zamiast DROP w sieci wewnętrznej i krótkie timeouty w klientach.'],
  ['Porównaj java.net.preferIPv6Addresses=false, true i system — kiedy którego użyć?', 'Kolejność adresów z resolvera.', 'false (domyślne): IPv4 pierwsze — bezpieczne przy niepewnym IPv6, ale nie korzysta z IPv6 nawet gdy lepsze; true: IPv6 pierwsze — tylko gdy IPv6 jest pewne i monitorowane; system (JDK 9+): kolejność jak w systemie (RFC 6724/gai.conf) — spójne zachowanie z innymi programami, polecane, gdy sieć dual-stack działa poprawnie. Żadne nie zastępuje Happy Eyeballs ani naprawy sieci.'],
  ['Twoja aplikacja ma w konfiguracji adres bazy jdbc:postgresql://10.20.112.10:5432/app i zostaje przeniesiona do podsieci IPv6-only w AWS z DNS64/NAT64. Co się stanie i jak to naprawić?', 'Literały IPv4, DNS64.', 'Połączenie się nie uda: host nie ma IPv4, a literału IPv4 nie da się przetłumaczyć przez DNS64 (nie ma zapytania DNS do syntetyzowania). Naprawa: użyć nazwy DNS bazy (np. endpointu RDS) — DNS64 zsyntetyzuje AAAA w prefiksie NAT64 lub zwróci natywny AAAA, jeśli baza ma IPv6; albo dać bazie adres IPv6 (dual-stack RDS); unikać literałów IPv4 w konfiguracji.'],
  ['Wyjaśnij działanie 464XLAT i dlaczego dzięki niemu aplikacje mobilne z literałami IPv4 działają w sieciach IPv6-only.', 'CLAT + PLAT.', 'Urządzenie (Android, iOS) uruchamia CLAT: lokalny interfejs z adresem IPv4 (np. 192.0.0.4) i trasą domyślną IPv4 do niego; pakiety IPv4 aplikacji są bezstanowo tłumaczone na IPv6 (z prefiksem NAT64 wykrytym przez RFC 7050 — zapytanie o ipv4only.arpa) i wysyłane w sieć IPv6-only do PLAT (NAT64 operatora), który tłumaczy je na IPv4. Aplikacja z literałem IPv4 lub gniazdem IPv4 widzi „normalne” IPv4.'],
  ['Jak wykryć i zablokować tunele IPv6 (6to4, Teredo, 6in4) w sieci firmowej?', 'Protokół 41, UDP 3544, prefiksy.', 'Wykrywanie: ruch IP z protokołem 41 (6in4/6to4/ISATAP) i UDP na port 3544 (Teredo) w logach firewalla/NetFlow/Zeek; zapytania DNS o teredo.ipv6.microsoft.com; ruch do 2001::/32 (Teredo) i 2002::/16 (6to4). Blokada: odrzucanie protokołu 41 i UDP 3544 na brzegu (poza zatwierdzonymi tunelami), wyłączenie Teredo/6to4/ISATAP w Windows przez GPO (netsh interface teredo set state disabled), zapewnienie natywnego IPv6 tam, gdzie potrzebne.'],
  ['Skonfiguruj Prometheus blackbox exporter do osobnego monitorowania dostępności usługi po IPv4 i IPv6.', 'preferred_ip_protocol, ip_protocol_fallback.', 'Moduły: http_2xx_v4: prober http, http: { preferred_ip_protocol: ip4, ip_protocol_fallback: false } oraz http_2xx_v6: { preferred_ip_protocol: ip6, ip_protocol_fallback: false }. Dwa zadania scrape z tym samym celem i różnymi modułami; alarmy osobno dla probe_success{module="http_2xx_v6"} == 0 — wykrywa sytuację „AAAA jest, IPv6 nie działa”, której nie widać w monitoringu z fallbackiem.'],
  ['Dlaczego po usunięciu błędnego rekordu AAAA aplikacja Java może jeszcze przez jakiś czas łączyć się po IPv6? Jak to kontrolować?', 'Cache DNS JVM i resolvera.', 'JVM trzyma wyniki rozwiązywania nazw w cache (networkaddress.cache.ttl, domyślnie 30 s bez SecurityManagera; w starszych konfiguracjach z SecurityManagerem — na zawsze), a do tego resolver systemowy (nscd, systemd-resolved) i serwery DNS trzymają rekord przez jego TTL. Kontrola: ustawić rozsądny networkaddress.cache.ttl (np. 30–60 s) w java.security lub Security.setProperty na starcie, krótkie TTL rekordów przed planowanymi zmianami, restart aplikacji przy pilnych naprawach.'],
  ['Kubernetes dual-stack: pod ma adresy IPv4 i IPv6, ale połączenia do Service po IPv6 nie działają. Co sprawdzisz?', 'ipFamilies, ipFamilyPolicy, kube-proxy, CNI.', 'Czy Service ma ipFamilyPolicy PreferDualStack/RequireDualStack i ipFamilies [IPv4, IPv6] (domyślnie SingleStack — tylko jedna rodzina, ClusterIP IPv4); kubectl get svc -o yaml (clusterIPs). Czy kube-proxy/CNI obsługują IPv6 (tryb dual-stack, reguły ip6tables/IPVS/eBPF), czy węzły mają trasy IPv6 do podsieci podów, czy NetworkPolicy dopuszczają IPv6, czy aplikacja słucha na [::] (nie tylko 0.0.0.0).'],
  ['Dlaczego Happy Eyeballs ustawia opóźnienie 250 ms, a nie 0 ms (równoległe próby od razu)?', 'Preferencja IPv6, obciążenie.', 'Gdyby oba połączenia startowały jednocześnie, IPv4 często wygrywałby o milisekundy (mniej narzutu, inne ścieżki), więc IPv6 byłby rzadko używany mimo działania — a celem jest preferowanie IPv6. Do tego podwojenie liczby połączeń obciąża serwery i NAT-y. 250 ms (RFC 8305: zalecane 250, dopuszczalne 100–2000) to kompromis: niewidoczne dla użytkownika opóźnienie przy awarii, a w normalnej sytuacji IPv6 zdąży nawiązać połączenie pierwszy.'],
  ['Wyjaśnij, jak system wybiera adres źródłowy IPv6 (RFC 6724), gdy interfejs ma adres tymczasowy, stabilny GUA i ULA, a cel jest w Internecie.', 'Zakres, temporary, longest match.', 'Dla celu GUA: adres ULA ma niepasujący zakres/etykietę i niższą preferencję, więc wybierany jest GUA; spośród GUA reguła „prefer temporary addresses” (jeśli use_tempaddr=2) wskazuje adres tymczasowy — prywatność połączeń wychodzących. Dla celu w ULA tej samej organizacji wybrany zostanie adres ULA (zgodna etykieta i najdłuższy wspólny prefiks).'],
  ['Twój load balancer ma rekord AAAA, ale grupa docelowa nie obsługuje IPv6 i LB nie ma listenera IPv6. Jakie objawy zobaczą klienci i jak to wykryć przed użytkownikami?', 'Rekord bez usługi.', 'Klienci z IPv6 (mobilni, domowi) próbują najpierw IPv6: przeglądarki po ~250 ms przechodzą na IPv4 (niewielkie opóźnienie), klienci bez Happy Eyeballs (aplikacje serwerowe, IoT, starsze SDK) czekają do timeoutu lub kończą się błędem. Wykrywanie: monitoring syntetyczny osobno po IPv6 (curl -6, blackbox ip6 bez fallbacku), testy po każdej zmianie DNS, alarm na różnicę dostępności v4/v6. Naprawa: włączyć IPv6 end-to-end albo usunąć AAAA.'],
  ['Napisz w Javie prosty fallback bez wątków: pętla po InetAddress.getAllByName z krótkim timeoutem na każdy adres. Jaka jest jego wada w porównaniu z Happy Eyeballs?', 'Sekwencyjnie, timeout per adres.', 'for (InetAddress a : InetAddress.getAllByName(host)) { try { Socket s = new Socket(); s.connect(new InetSocketAddress(a, port), 2000); return s; } catch (IOException e) { last = e; } } throw last; — działa przy każdym martwym adresie, ale każdy blackhole kosztuje pełne 2 s (dwa martwe IPv6 = 4 s), a zbyt krótki timeout może zrywać poprawne, ale wolne połączenia (daleki serwer). Happy Eyeballs traci tylko ~250 ms i nie przerywa wolnej, ale działającej próby.'],
  ['Wyjaśnij, dlaczego zapytania A i AAAA powinny być wysyłane równolegle i co się dzieje, gdy serwer DNS nie odpowiada na AAAA.', 'RFC 8305 §3, czekanie na DNS.', 'Sekwencyjne zapytania (najpierw AAAA, potem A) wydłużają rozwiązywanie nazwy; gdy serwer DNS (lub firewall) gubi zapytania AAAA, klient czeka timeout resolvera (np. 5 s), zanim zapyta o A. Happy Eyeballs v2 wysyła oba zapytania równolegle i — jeśli A przyjdzie pierwsze — czeka najwyżej 50 ms (Resolution Delay) na AAAA, po czym startuje połączenie IPv4. W glibc pomaga opcja single-request lub poprawa DNS.'],
  ['Opisz, jak Windows i Linux pozwalają zmienić preferencję IPv4 nad IPv6 bez wyłączania IPv6, i dlaczego to tylko obejście.', 'gai.conf, prefixpolicies.', 'Linux: /etc/gai.conf — precedence ::ffff:0:0/96 100 (podnosi priorytet adresów IPv4-mapped ponad ::/0 40) — wpływa na getaddrinfo (i Javę z preferIPv6Addresses=system). Windows: netsh interface ipv6 set prefixpolicy ::ffff:0:0/96 precedence=60 label=4 lub rejestr DisabledComponents=0x20 („prefer IPv4 over IPv6”). Obejście, bo maskuje problem z IPv6 tylko na tym hoście; inni klienci nadal cierpią, a w IPv6-only nic nie zmienia.'],
  ['Jakie testy warto dodać do CI aplikacji Java, by wcześnie wykryć problemy z dual-stack i IPv6-only?', 'Testcontainers/Docker z siecią IPv6.', '1) Test integracyjny w sieci Dockera tylko IPv6 (docker network create --ipv6 --subnet fd00:… --ipv4=false, Docker 27+): aplikacja startuje i łączy się z zależnościami po nazwach. 2) Test, że aplikacja słucha na [::] (połączenie przez ::1). 3) Test klienta z rekordem AAAA wskazującym blackhole i A działającym — czas połączenia < 1 s (Happy Eyeballs/fallback). 4) Skan konfiguracji pod kątem literałów IPv4 (regex w application*.yml). 5) Logowanie adresów w formacie obsługującym IPv6.'],
  ['Dlaczego allow-lista po adresach IP w aplikacji dual-stack musi zawierać wpisy IPv4 i IPv6 partnera? Co się stanie, gdy partner włączy IPv6?', 'Klient może przyjść innym protokołem.', 'Klient partnera z dual-stack i Happy Eyeballs zacznie łączyć się po IPv6, gdy tylko Twoja usługa ma AAAA i działający IPv6 — z adresu, którego nie ma na allow-liście (lista zawierała tylko jego IPv4). Skutek: nagłe odrzucenia po zmianie po stronie partnera lub Twojego DNS. Allow-lista musi obejmować prefiksy IPv4 i IPv6 (najlepiej prefiksy, nie pojedyncze adresy IPv6), a uwierzytelnianie nie powinno opierać się wyłącznie na IP (mTLS, tokeny).'],
  ['W sieci IPv6-only klient pyta o ipv4only.arpa. Po co?', 'RFC 7050, wykrywanie prefiksu NAT64.', 'Nazwa ipv4only.arpa ma wyłącznie rekordy A (192.0.0.170 i 192.0.0.171). Jeśli resolver zwraca dla niej AAAA, to znaczy, że działa DNS64 — a ostatnie 32 bity zwróconego adresu pozwalają klientowi ustalić używany prefiks NAT64 (np. 64:ff9b::/96 lub prefiks operatora). Korzysta z tego CLAT (464XLAT) i aplikacje, które same syntetyzują adresy IPv6 dla literałów IPv4 (np. Apple, przy walidacji DNSSEC).'],
  ['Jak zachowa się pula połączeń HikariCP z connectionTimeout=30000 przy zepsutym IPv6 i bazie z rekordami A i AAAA, gdy sterownik łączy się z pierwszym adresem (IPv6)?', 'Wątki czekają, kaskada.', 'Każda próba otwarcia nowego połączenia (start aplikacji, odnawianie po maxLifetime, wzrost ruchu) czeka do timeoutu sterownika/HikariCP — wątki żądań czekające na połączenie z puli również czekają do 30 s, rośnie liczba wiszących żądań, health checki padają, orkiestrator restartuje pody — kaskada. Naprawa: działające IPv6 lub brak AAAA, krótki connectionTimeout (np. 3 s) i connectTimeout w URL JDBC, fabryka gniazd z fallbackiem po wszystkich adresach (Happy Eyeballs) tam, gdzie sterownik na to pozwala (np. własny SocketFactory w pgjdbc), monitoring czasu pozyskania połączenia (hikaricp_connections_acquire).'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
