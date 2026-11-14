// Quiz (100 pytan) do lekcji _ns_03/13_DualStackAndTransition.
const { quizBuilder } = require('./lib.cjs');
const { sequential, happyEyeballs, nat64, fromNat64, SCEN } = require('./ns03_13_dualstack_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111413);

  // 1) Czasy polaczen (20): 5 scenariuszy x (sekwencyjnie 10 s, sekwencyjnie 30 s, HE, zwyciezca HE)
  for (const k of Object.keys(SCEN)) {
    const desc = SCEN[k].map((a) => `IPv${a.fam} ${a.state === 'ok' ? `działa (RTT ${a.rtt} ms)` : a.state === 'blackhole' ? 'blackhole' : 'szybki błąd'}`).join(', ');
    for (const to of [10000, 30000]) {
      const s = sequential(SCEN[k], to).total;
      add(`Adresy w kolejności: ${desc}. Klient sekwencyjny, connectTimeout ${to} ms. Ile potrwa nawiązanie połączenia?`, `${s} ms`, [`${s + 250} ms`, `${Math.max(s - to, 30)} ms`, `${to} ms`, `${s * 2} ms`, '250 ms'].filter((x) => x !== `${s} ms`), sequential(SCEN[k], to).log.join('; '));
    }
    const h = happyEyeballs(SCEN[k]);
    add(`Adresy: ${desc}. Ile potrwa nawiązanie połączenia z Happy Eyeballs v2 (opóźnienie 250 ms)?`, `${h.total} ms`, [`${h.total + 250} ms`, `${sequential(SCEN[k], 10000).total} ms`, `${h.total + 10000} ms`, '0 ms', '500 ms'].filter((x) => x !== `${h.total} ms`), h.log.join('; '));
    add(`Adresy: ${desc}. Którą rodziną zostanie zestawione połączenie przy Happy Eyeballs v2?`, `IPv${h.winner}`, [`IPv${h.winner === 6 ? 4 : 6}`, 'oboma jednocześnie (dwa połączenia)', 'żadną — błąd'], h.log.join('; '));
  }

  // 2) NAT64 (10)
  for (const ip of ['140.82.121.4', '192.0.2.33', '8.8.8.8', '203.0.113.5', '1.1.1.1']) {
    const a = nat64(ip);
    add(`Jaki adres AAAA zsyntetyzuje DNS64 (64:ff9b::/96) dla ${ip}?`, a, [`64:ff9b::${ip}`.replace(/\./g, ':'), a.replace('64:ff9b', '2001:db8'), `::ffff:${ip}`, a.replace('64:ff9b::', '64:ff9b:1::')], 'Ostatnie 32 bity = IPv4 w hex.');
  }
  for (const a of ['64:ff9b::c000:221', '64:ff9b::808:808', '64:ff9b::8c52:7904', '64:ff9b::cb00:7105', '64:ff9b::a00:1']) {
    const v4 = fromNat64(a);
    const parts = v4.split('.');
    add(`Jaki adres IPv4 kryje się w adresie NAT64 ${a}?`, v4, [parts.slice().reverse().join('.'), parts.map((x) => (Number(x) + 1) % 256).join('.'), `${parts[0]}.${parts[1]}.0.0`, '64.255.155.0'].filter((x) => x !== v4), 'Ostatnie 32 bity.');
  }

  // 3) Merytoryczne (70)
  const facts = [
    ['Co oznacza dual-stack?', 'Jednoczesne działanie IPv4 i IPv6 na hostach i w sieci', ['Dwa routery w parze', 'Dwa stosy TCP w JVM', 'Tunel IPv6 w IPv4'], 'A i AAAA.'],
    ['Jaki rekord DNS zawiera adres IPv6?', 'AAAA', ['A', 'PTR', 'CNAME'], 'A — IPv4.'],
    ['Który RFC opisuje wybór adresu źródłowego i docelowego?', 'RFC 6724', ['RFC 8305', 'RFC 1918', 'RFC 6146'], 'Default Address Selection.'],
    ['Który RFC opisuje Happy Eyeballs v2?', 'RFC 8305', ['RFC 6724', 'RFC 6052', 'RFC 4193'], 'Następca RFC 6555.'],
    ['Jakie jest zalecane opóźnienie między próbami połączeń w Happy Eyeballs v2?', '250 ms', ['10 ms', '2 s', '30 s'], 'Connection Attempt Delay.'],
    ['Jak Happy Eyeballs v2 porządkuje adresy?', 'Przeplata rodziny: IPv6, IPv4, IPv6…', ['Najpierw wszystkie IPv6, potem IPv4', 'Losowo', 'Najpierw wszystkie IPv4'], 'Odporność na wiele martwych adresów.'],
    ['Co robi Happy Eyeballs, gdy próba IPv6 szybko zwróci błąd?', 'Od razu startuje kolejną próbę', ['Czeka 250 ms', 'Kończy z błędem', 'Ponawia IPv6'], 'Bez czekania.'],
    ['Czy klasyczne klienty JDK (Socket, HttpURLConnection) implementują Happy Eyeballs?', 'Nie — łączą się z pierwszym adresem z resolvera', ['Tak, zawsze', 'Tak, od Java 8', 'Tylko w trybie debug'], 'Fallback trzeba dodać samemu lub użyć biblioteki.'],
    ['Którą bibliotekę HTTP dla JVM wymieniono jako mającą fast fallback (Happy Eyeballs)?', 'OkHttp 5', ['Apache Commons IO', 'Jackson', 'Log4j'], 'fastFallback.'],
    ['Jaka jest domyślna wartość java.net.preferIPv6Addresses?', 'false (najpierw IPv4)', ['true', 'system', 'auto'], 'Od JDK 9 dostępne system.'],
    ['Co robi java.net.preferIPv6Addresses=system?', 'Używa kolejności adresów z systemu operacyjnego', ['Wyłącza IPv6', 'Wymusza IPv6', 'Losuje kolejność'], 'Spójnie z OS.'],
    ['Co robi -Djava.net.preferIPv4Stack=true?', 'JVM używa wyłącznie gniazd IPv4', ['Preferuje IPv4, ale używa IPv6', 'Wyłącza DNS', 'Włącza NAT64'], 'Brak IPv6 w aplikacji.'],
    ['Jaka właściwość steruje czasem cache udanych rozwiązań DNS w JVM?', 'networkaddress.cache.ttl', ['java.net.dns.ttl', 'sun.net.inetaddr.cache', 'dns.cache.seconds'], 'W java.security.'],
    ['Jaki jest domyślny czas cache negatywnych odpowiedzi DNS w JVM?', '10 s', ['0 s', '1 godzina', 'Na zawsze'], 'networkaddress.cache.negative.ttl.'],
    ['Co bierze InetSocketAddress(host, port) przy wielu adresach?', 'Pierwszy adres zwrócony przez resolver', ['Wszystkie po kolei', 'Losowy', 'Ostatni'], 'Brak fallbacku.'],
    ['Dlaczego „cisza” (blackhole) IPv6 jest gorsza niż szybki błąd?', 'Klient czeka do timeoutu, zanim spróbuje IPv4', ['Bo psuje DNS', 'Bo blokuje IPv4', 'Bo zmienia MTU'], 'Timeout.'],
    ['Ile trwa domyślnie na Linuksie łączenie TCP bez odpowiedzi (6 retransmisji SYN)?', 'Około 127 s', ['1 s', '3 s', '10 minut'], 'tcp_syn_retries=6.'],
    ['Jaka precedence ma ::/0 (IPv6) w domyślnej tabeli RFC 6724?', '40', ['35', '50', '3'], 'IPv4-mapped 35.'],
    ['Jaka precedence ma ::ffff:0:0/96 (IPv4) w RFC 6724?', '35', ['40', '50', '1'], 'Niższa niż IPv6.'],
    ['Dlaczego adres docelowy ULA jest zwykle mniej preferowany niż IPv4?', 'Ma precedence 3, niższą niż IPv4 (35)', ['Bo nie działa w Internecie', 'Bo jest dłuższy', 'Bo wymaga NAT'], 'RFC 6724.'],
    ['Gdzie w Linuksie zmienia się politykę wyboru adresów getaddrinfo?', '/etc/gai.conf', ['/etc/hosts', '/etc/resolv.conf', '/etc/nsswitch.conf'], 'precedence.'],
    ['Jak w Windows zobaczyć tabelę polityki prefiksów?', 'netsh interface ipv6 show prefixpolicies', ['ipconfig /prefix', 'route print -6', 'Get-DnsClient'], 'RFC 6724.'],
    ['Czym jest sieć IPv6-only?', 'Siecią, w której hosty nie mają adresów IPv4', ['Siecią bez DNS', 'Siecią z samym link-local', 'Siecią bez routera'], 'IPv4 przez translację.'],
    ['Co robi DNS64?', 'Syntetyzuje rekordy AAAA dla nazw mających tylko A', ['Szyfruje DNS', 'Blokuje AAAA', 'Tłumaczy pakiety'], 'RFC 6147.'],
    ['Co robi NAT64?', 'Tłumaczy pakiety IPv6 na IPv4 i z powrotem', ['Tłumaczy nazwy DNS', 'Szyfruje IPv6', 'Przydziela adresy'], 'RFC 6146.'],
    ['Jaki jest dobrze znany prefiks NAT64?', '64:ff9b::/96', ['2001:db8::/32', 'fe80::/10', '2002::/16'], 'RFC 6052.'],
    ['Dlaczego literał IPv4 w aplikacji nie działa w IPv6-only z DNS64/NAT64?', 'Nie ma zapytania DNS, z którego DNS64 mógłby zsyntetyzować AAAA', ['Bo NAT64 blokuje literały', 'Bo IPv4 jest szyfrowane', 'Działa zawsze'], 'Potrzebny 464XLAT.'],
    ['Czym jest 464XLAT?', 'CLAT na urządzeniu + NAT64 u operatora — IPv4 dla aplikacji w sieci IPv6-only', ['Tunel 6to4', 'Protokół routingu', 'Typ adresu IPv6'], 'RFC 6877.'],
    ['Gdzie 464XLAT jest powszechnie używany?', 'W sieciach komórkowych (Android, iOS)', ['W sieciach Wi-Fi gości', 'W centrach danych z IPv4', 'Tylko w laboratoriach'], 'IPv6-only u operatorów.'],
    ['Do czego służy zapytanie o ipv4only.arpa?', 'Do wykrycia DNS64 i prefiksu NAT64', ['Do testu IPv4', 'Do rejestracji domeny', 'Do DNSSEC'], 'RFC 7050.'],
    ['Dlaczego DNS64 utrudnia DNSSEC?', 'Zsyntetyzowane AAAA nie mają prawidłowego podpisu', ['Bo wyłącza TCP', 'Bo zmienia TTL na 0', 'Nie utrudnia'], 'Klient walidujący syntetyzuje sam.'],
    ['Jaki protokół IP przenosi tunele 6in4 i 6to4?', '41', ['47', '50', '17'], 'IPv6 w IPv4.'],
    ['Na jakim porcie działa Teredo?', 'UDP 3544', ['TCP 443', 'UDP 500', 'UDP 4789'], 'Przez NAT.'],
    ['Jaki prefiks używa 6to4?', '2002::/16', ['2001::/32', '64:ff9b::/96', 'fc00::/7'], 'Wycofany.'],
    ['Jaki prefiks używa Teredo?', '2001::/32', ['2002::/16', 'fe80::/10', 'ff00::/8'], 'Wycofany.'],
    ['Dlaczego tunele IPv6 są ryzykiem bezpieczeństwa?', 'Mogą omijać firewalle i monitoring skonfigurowane tylko dla IPv4', ['Bo są wolne', 'Bo wymagają hasła', 'Bo zmieniają MAC'], 'Blokuj protokół 41 i UDP 3544.'],
    ['Jak wyłączyć Teredo w Windows?', 'netsh interface teredo set state disabled', ['ipconfig /release6', 'route delete ::/0', 'Set-DnsClient -Teredo off'], 'Lub GPO.'],
    ['Co należy zrobić z rekordem AAAA, gdy usługa nie działa po IPv6?', 'Usunąć go do czasu naprawy IPv6', ['Zostawić — klienci sobie poradzą', 'Zduplikować', 'Zmienić TTL na 0'], 'AAAA = obietnica działania.'],
    ['Jak monitorować osobno IPv4 i IPv6 w blackbox exporter?', 'Moduły z preferred_ip_protocol ip4/ip6 i bez fallbacku', ['Jednym modułem z fallbackiem', 'Tylko pingiem', 'Nie da się'], 'ip_protocol_fallback: false.'],
    ['Czym jest PreferDualStack w Kubernetes?', 'Polityką Service przydzielającą adresy obu rodzin, jeśli klaster wspiera dual-stack', ['Trybem DNS', 'Typem Ingress', 'Rodzajem PersistentVolume'], 'ipFamilyPolicy.'],
    ['Jaka jest domyślna ipFamilyPolicy Service w Kubernetes?', 'SingleStack', ['RequireDualStack', 'PreferDualStack', 'IPv6Only'], 'Jedna rodzina.'],
    ['Na jakim adresie powinna słuchać aplikacja, by przyjmować IPv4 i IPv6?', ':: (gniazdo dual-stack)', ['0.0.0.0', '127.0.0.1', '::1'], '0.0.0.0 to tylko IPv4.'],
    ['Co oznacza w logach serwera klient z adresem 64:ff9b::c000:221?', 'Klient IPv4 192.0.2.33 widziany przez translator lub syntetyczny adres', ['Klient link-local', 'Klient z ULA', 'Atak spoofingu'], 'Ostatnie 32 bity.'],
    ['Dlaczego allow-lista partnera musi obejmować IPv4 i IPv6?', 'Klient dual-stack może łączyć się dowolną rodziną', ['Bo IPv6 jest szybszy', 'Bo IPv4 zostanie wyłączony', 'Nie musi'], 'Happy Eyeballs wybiera.'],
    ['Co powoduje długi connectionTimeout w HikariCP przy zepsutym IPv6?', 'Wątki czekają na połączenia i rośnie kaskada timeoutów', ['Szybsze zapytania', 'Mniejsze zużycie pamięci', 'Nic'], 'Krótkie timeouty.'],
    ['Jaki objaw sugeruje problem z AAAA i zepsutym IPv6?', 'Przeglądarka działa normalnie, aplikacja serwerowa raz na jakiś czas czeka kilkadziesiąt sekund', ['Brak adresu IP', 'Błąd certyfikatu', 'HTTP 404'], 'Przeglądarka ma HE.'],
    ['Jak porównać działanie IPv4 i IPv6 do usługi z linii poleceń?', 'curl -4 i curl -6 z --connect-timeout', ['ping -n', 'dig +trace', 'ss -s'], 'Osobne testy.'],
    ['Co pokazuje getent ahosts nazwa?', 'Adresy w kolejności, w jakiej poda je getaddrinfo', ['Tylko rekordy MX', 'Tablicę ARP', 'Trasy'], 'RFC 6724 + gai.conf.'],
    ['Dlaczego Happy Eyeballs nie startuje obu połączeń jednocześnie?', 'Aby preferować IPv6 i nie podwajać obciążenia', ['Bo jest to zabronione', 'Bo TCP tego nie pozwala', 'Startuje jednocześnie'], 'Kompromis 250 ms.'],
    ['Ile maksymalnie czeka Happy Eyeballs v2 na AAAA, gdy A przyszło pierwsze?', 'Około 50 ms (Resolution Delay)', ['5 s', '0 ms', '250 ms'], 'RFC 8305 §3.'],
    ['Czy curl używa Happy Eyeballs?', 'Tak (od wersji 7.59, domyślnie 200 ms)', ['Nie', 'Tylko z opcją -6', 'Tylko na Windows'], '--happy-eyeballs-timeout-ms.'],
    ['Co się stanie z aplikacją z preferIPv4Stack=true w sieci IPv6-only?', 'Nie połączy się z niczym', ['Będzie działać normalnie', 'Użyje NAT64 automatycznie', 'Przełączy się na IPv6'], 'Tylko gniazda IPv4.'],
    ['Czym jest DS-Lite?', 'Dostarczaniem IPv4 klientowi przez tunel IPv4-w-IPv6 do NAT operatora', ['Szyfrowaniem DNS', 'Typem VLAN', 'Wersją DHCP'], 'Mechanizm operatorski.'],
    ['Jaki adres zwraca DNS64 dla nazwy, która ma natywny AAAA?', 'Natywny AAAA (synteza tylko przy braku AAAA)', ['Zawsze syntetyczny', 'Oba', 'Żaden'], 'Preferencja natywnego IPv6.'],
    ['Jak AWS realizuje NAT64 dla podsieci IPv6-only?', 'DNS64 w podsieci i NAT64 w NAT Gateway', ['Przez Internet Gateway', 'Przez VPC peering', 'Nie obsługuje'], 'Prefiks 64:ff9b::/96.'],
    ['Co powinno się logować, by korelować aktywność użytkownika dual-stack?', 'Identyfikator sesji/konta obok adresu IP', ['Tylko adres IPv4', 'Tylko adres IPv6', 'Tylko MAC'], 'Adresy się zmieniają.'],
    ['Dlaczego test w sieci IPv6-only jest ważny dla aplikacji mobilnych?', 'Operatorzy i sklepy (np. Apple) wymagają działania w IPv6-only', ['Bo jest szybszy', 'Bo IPv4 nie istnieje', 'Bo tak każe RFC 791'], 'Od 2016 w App Store.'],
    ['Co robi Happy Eyeballs po zestawieniu pierwszego połączenia?', 'Zamyka pozostałe próby', ['Utrzymuje oba połączenia', 'Wysyła dane obydwoma', 'Restartuje DNS'], 'Wygrywa pierwszy.'],
    ['Dlaczego REJECT zamiast DROP w sieci wewnętrznej pomaga dual-stack?', 'Klient szybko dostaje błąd i przechodzi do kolejnego adresu', ['Bo szyfruje ruch', 'Bo skraca TTL', 'Nie pomaga'], 'Szybki fallback.'],
    ['Co to jest SIIT?', 'Bezstanowa translacja IPv4/IPv6 (nagłówek po nagłówku)', ['Stanowy NAT44', 'Tunel GRE', 'Protokół DNS'], 'Używany w CLAT.'],
    ['Który mechanizm daje aplikacji lokalny interfejs IPv4 na urządzeniu w sieci IPv6-only?', 'CLAT (część 464XLAT)', ['PLAT', 'DNS64', 'RA Guard'], 'Customer-side translator.'],
    ['Czy NAT64 wymaga stanu (jak PAT)?', 'Tak — stanowy NAT64 mapuje wielu klientów IPv6 na adresy IPv4 z portami', ['Nie, nigdy', 'Tylko dla UDP', 'Tylko dla ICMP'], 'RFC 6146.'],
    ['Co trzeba uwzględnić w allow-listach po włączeniu NAT64 dla klientów IPv6-only?', 'Ruch do serwerów IPv4 przychodzi z adresów puli NAT64 (IPv4)', ['Adresy link-local klientów', 'Adresy MAC', 'Nic'], 'Serwer widzi translator.'],
    ['Jaki błąd JVM zobaczysz, gdy pierwszy adres (IPv6) jest blackhole i minie connectTimeout?', 'SocketTimeoutException: Connect timed out', ['UnknownHostException', 'BindException', 'MalformedURLException'], 'Bez prób kolejnych adresów.'],
    ['Co oznacza „Resolution Delay” w RFC 8305?', 'Krótkie czekanie na odpowiedź AAAA, gdy A przyszła pierwsza', ['Opóźnienie TTL', 'Czas cache DNS', 'Czas handshake TLS'], '50 ms.'],
    ['Dlaczego warto publikować AAAA dla usług, które działają po IPv6?', 'Klienci IPv6-only i mobilni łączą się bez translacji (szybciej, taniej, bez NAT)', ['Bo IPv4 jest wyłączany jutro', 'Bo to wymagane prawem', 'Nie warto'], 'Mniej NAT64/CGNAT.'],
    ['Jaka właściwość curl ustawia opóźnienie Happy Eyeballs?', '--happy-eyeballs-timeout-ms', ['--ipv6-delay', '--he', '--dual-stack'], 'Domyślnie 200.'],
    ['Dlaczego krótki connectTimeout w puli połączeń ogranicza skutki zepsutego IPv6?', 'Bo martwy adres blokuje wątek tylko przez kilka sekund, a nie kilkadziesiąt', ['Bo wyłącza IPv6', 'Bo przyspiesza zapytania SQL', 'Nie ogranicza'], 'Plus fallback.'],
    ['Jakie jest najlepsze rozwiązanie problemu „AAAA istnieje, IPv6 nie działa”?', 'Naprawić IPv6 na ścieżce albo usunąć AAAA', ['Wyłączyć IPv6 we wszystkich JVM', 'Zwiększyć timeout', 'Zmienić port'], 'Przyczyna, nie objaw.'],
    ['Co mierzy curl -w %{time_connect}?', 'Czas do zestawienia połączenia TCP', ['Czas rozwiązania DNS', 'Czas pobrania całości', 'Czas TLS'], '%{time_appconnect} — TLS.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
