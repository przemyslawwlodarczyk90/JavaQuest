// Quiz (100 pytan) do lekcji _ns_03/10_IcmpPingTraceroute.
const { quizBuilder } = require('./lib.cjs');
const { ICMP, ICMP6, analyze, MTR } = require('./ns03_10_icmp_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111410);

  // 1) Typ/kod → znaczenie (10) i odwrotnie (10)
  const names = ICMP.map(([, , n]) => n);
  for (const [t, c, n] of ICMP) add(`Co oznacza ICMP typ ${t} kod ${c}?`, n, names.filter((x) => x !== n), `${t}/${c}.`);
  const codes = ICMP.map(([t, c]) => `${t}/${c}`);
  for (const [t, c, n] of ICMP) add(`Jaki typ/kod ICMP odpowiada komunikatowi „${n}”?`, `${t}/${c}`, codes.filter((x) => x !== `${t}/${c}`), n + '.');

  // 2) ICMPv6 (9)
  const n6 = ICMP6.map(([, n]) => n);
  for (const [t, n] of ICMP6) add(`Co oznacza ICMPv6 typ ${t}?`, n, n6.filter((x) => x !== n), `Typ ${t}.`);

  // 3) TTL → system i skoki (10)
  for (const ttl of [63, 57, 52, 127, 119, 113, 254, 247, 60, 125]) {
    const start = ttl <= 64 ? 64 : ttl <= 128 ? 128 : 255;
    const sys = start === 64 ? 'Linux/macOS (start 64)' : start === 128 ? 'Windows (start 128)' : 'urządzenie sieciowe (start 255)';
    add(`Odpowiedź ping ma ttl=${ttl}. Jaki system i ile skoków (typowa heurystyka)?`, `${sys}, ${start - ttl} skoków`,
      ['Linux/macOS (start 64)', 'Windows (start 128)', 'urządzenie sieciowe (start 255)'].filter((s) => s !== sys).map((s) => { const st = Number(s.match(/start (\d+)/)[1]); return `${s}, ${Math.abs(st - ttl)} skoków`; }).concat([`${sys}, ${ttl} skoków`]),
      `Najbliższa wartość startowa powyżej ${ttl}: ${start}.`);
  }

  // 4) mtr (5)
  const verdict = { A: 'Rate limiting ICMP na jednym routerze — ścieżka OK', B: 'Realna strata od skoku 4 do celu', C: 'Realne opóźnienie z odległości (łącze transatlantyckie)', D: 'Wolne generowanie ICMP przez jeden router — ignorować', E: 'Problem lokalny od pierwszego skoku (Wi-Fi/łącze domowe)' };
  for (const [k, hops] of Object.entries(MTR)) {
    add(`mtr: ${hops.map((h) => `${h.host} ${h.loss}% avg ${h.avg} ms (best ${h.best}, worst ${h.worst})`).join(' → ')}. Jaka jest diagnoza?`, verdict[k], Object.values(verdict).filter((v) => v !== verdict[k]), analyze(hops).join('; '));
  }

  // 5) Merytoryczne (56)
  const facts = [
    ['Jaki numer protokołu IP ma ICMP?', '1', ['6', '17', '58'], 'ICMPv6 = 58.'],
    ['Jaki numer protokołu (Next Header) ma ICMPv6?', '58', ['1', '41', '50'], 'IPv6.'],
    ['Na jakim komunikacie ICMP opiera się traceroute?', 'Time Exceeded (TTL exceeded in transit)', ['Echo Reply', 'Redirect', 'Source Quench'], 'Typ 11 kod 0.'],
    ['Na jakie porty wysyła sondy klasyczny traceroute w Linuksie?', 'UDP od 33434 w górę', ['TCP 80', 'ICMP Echo', 'UDP 53'], 'Koniec = Port Unreachable.'],
    ['Jakiego protokołu używa domyślnie tracert w Windows?', 'ICMP Echo Request', ['UDP 33434', 'TCP SYN 443', 'ARP'], 'Linux: traceroute -I.'],
    ['Który tryb traceroute najlepiej przechodzi przez firewalle przed usługą HTTPS?', 'TCP SYN na port 443 (traceroute -T -p 443)', ['UDP 33434', 'ICMP Echo', 'ARP'], 'Te same reguły co prawdziwy ruch.'],
    ['Co oznacza gwiazdka (*) w traceroute?', 'Brak odpowiedzi od danego skoku w czasie oczekiwania', ['Pętlę routingu', 'Utratę 100% ruchu na łączu', 'Adres IPv6'], 'Często filtr lub limit ICMP.'],
    ['Co oznaczają straty na jednym skoku pośrednim mtr przy 0% strat na celu?', 'Ograniczanie odpowiedzi ICMP przez router — nie problem ścieżki', ['Awarię łącza', 'Pętlę routingu', 'Atak DDoS'], 'Control plane.'],
    ['Kiedy strata w mtr jest realna?', 'Gdy zaczyna się na pewnym skoku i utrzymuje aż do celu', ['Gdy występuje tylko na jednym skoku', 'Gdy dotyczy pierwszego skoku z 0% na kolejnych', 'Nigdy'], 'Patrz na cel.'],
    ['Dlaczego RTT do routera pośredniego bywa większe niż do celu?', 'Router generuje ICMP wolno w procesorze sterującym, a odpowiedź może wracać inną drogą', ['Bo cel jest bliżej', 'Bo traceroute liczy źle', 'Bo TTL rośnie'], 'Ignoruj, jeśli nie utrzymuje się do celu.'],
    ['Ile cykli mtr warto zebrać, by wiarygodnie wykryć 1–2% strat?', 'Co najmniej 100', ['3', '10', '1'], 'mtr -c 100.'],
    ['Jakie polecenie Windows łączy tracert ze statystyką strat?', 'pathping', ['ipconfig', 'netstat', 'nslookup'], 'Odpowiednik mtr.'],
    ['Co robi mtr -T -P 443?', 'Mierzy trasę i straty sondami TCP na port 443', ['Wysyła ICMP na port 443', 'Testuje DNS', 'Skanuje porty'], 'Gdy ICMP jest filtrowany.'],
    ['Dlaczego w traceroute warto używać -n?', 'Aby nie czekać na odwrotny DNS i nie sugerować się mylącymi nazwami', ['Aby wymusić IPv6', 'Aby zwiększyć TTL', 'Aby użyć TCP'], 'Szybciej i czytelniej.'],
    ['Co pokazuje mtr -z?', 'Numery systemów autonomicznych (AS) skoków', ['Zera w adresach', 'Strefy DNS', 'Zakres portów'], 'Kto jest operatorem.'],
    ['Co oznacza „Destination Host Unreachable” od routera ostatniego skoku?', 'Router nie otrzymał odpowiedzi ARP/ND od hosta docelowego', ['Brak trasy do sieci', 'Port zamknięty', 'Za duży pakiet'], 'Kod 1.'],
    ['Co wysyła host, gdy datagram UDP trafi na zamknięty port?', 'ICMP Port Unreachable (3/3)', ['TCP RST', 'ICMP Echo Reply', 'Nic, zawsze'], 'Chyba że firewall blokuje.'],
    ['Co wysyła host, gdy segment TCP SYN trafi na zamknięty port?', 'TCP RST', ['ICMP Port Unreachable', 'ICMP Time Exceeded', 'SYN-ACK'], 'TCP ma własny mechanizm.'],
    ['Który komunikat ICMP jest kluczowy dla Path MTU Discovery w IPv4?', 'Destination Unreachable — fragmentation needed (3/4)', ['Echo Reply', 'Redirect', 'Time Exceeded'], 'Nie blokować.'],
    ['Który komunikat ICMPv6 jest odpowiednikiem fragmentation needed?', 'Packet Too Big (typ 2)', ['Neighbor Solicitation', 'Echo Request', 'Router Advertisement'], 'Routery IPv6 nie fragmentują.'],
    ['Co się stanie, gdy zablokujesz cały ICMPv6?', 'IPv6 przestanie działać (Neighbor Discovery, RA, PMTUD)', ['Nic, IPv6 nie używa ICMP', 'Tylko ping przestanie działać', 'Zwiększy się MTU'], 'RFC 4890.'],
    ['Jaki objaw daje blokada ICMP fragmentation needed na ścieżce z mniejszym MTU?', 'Połączenie się nawiązuje, ping działa, ale TLS/duże odpowiedzi wiszą', ['Natychmiastowy błąd DNS', 'Brak adresu IP', 'Błędy CRC'], 'PMTUD black hole.'],
    ['Który ICMP warto blokować na hostach i routerach brzegowych?', 'Redirect (5)', ['Fragmentation needed (3/4)', 'Time Exceeded (11)', 'Packet Too Big (ICMPv6 2)'], 'Ryzyko MITM.'],
    ['Jak wyłączyć akceptowanie ICMP Redirect w Linuksie?', 'sysctl net.ipv4.conf.all.accept_redirects=0', ['sysctl net.ipv4.icmp_echo_ignore_all=1', 'ip route flush cache', 'iptables -F'], 'Także IPv6.'],
    ['Czym jest ICMP tunneling?', 'Ukrywaniem danych w polu danych pakietów Echo', ['Tunelem VPN po ICMP-u w nagłówku', 'Szyfrowaniem ICMP', 'Routingiem ICMP'], 'Eksfiltracja/C2.'],
    ['Jaka cecha ruchu sugeruje ICMP tunneling?', 'Echo o nietypowym, zmiennym rozmiarze i dużej liczbie do jednego zewnętrznego adresu', ['Echo co sekundę do bramy', 'Pojedynczy ping do 8.8.8.8', 'Time Exceeded przy traceroute'], 'Zeek/Suricata.'],
    ['Czym jest ping sweep?', 'Wysyłaniem Echo do wielu adresów w celu znalezienia aktywnych hostów', ['Pingiem z dużym rozmiarem', 'Pingiem w IPv6', 'Testem MTU'], 'nmap -sn.'],
    ['Czym był atak Smurf?', 'Wzmocnieniem ICMP Echo przez broadcast skierowany z fałszywym źródłem ofiary', ['Atakiem na DNS', 'Atakiem na ARP', 'Atakiem SQL'], 'Dziś no ip directed-broadcast.'],
    ['Jak ograniczyć ryzyko floodu ICMP Echo bez wyłączania ping?', 'Rate limiting (np. limit 10/s) i CoPP na routerach', ['Blokada wszystkiego', 'Zwiększenie TTL', 'Wyłączenie IPv6'], 'Ping zostaje dla diagnozy.'],
    ['Co robi InetAddress.isReachable() bez uprawnień do surowych gniazd?', 'Próbuje połączenia TCP na port 7 (echo)', ['Wysyła ICMP mimo to', 'Wysyła UDP 53', 'Zawsze zwraca true'], 'Zależne od uprawnień.'],
    ['Czym lepiej sprawdzać dostępność zależności w aplikacji niż pingiem?', 'Połączeniem z usługą (TCP/HTTP health check)', ['ICMP Timestamp', 'Traceroute', 'ARP'], 'Liczy się usługa.'],
    ['Który wyjątek Java oznacza zwykle RST (port zamknięty)?', 'ConnectException (Connection refused)', ['SocketTimeoutException', 'UnknownHostException', 'BindException'], 'Host osiągalny.'],
    ['Który wyjątek Java oznacza brak odpowiedzi (np. DROP)?', 'SocketTimeoutException', ['ConnectException', 'NoRouteToHostException', 'MalformedURLException'], 'Czekanie do timeoutu.'],
    ['Co często oznacza NoRouteToHostException w Javie?', 'ICMP host/net unreachable lub brak trasy', ['Port zamknięty', 'Błąd TLS', 'Zły login'], 'Problem L3.'],
    ['Dlaczego load balancer w chmurze może nie odpowiadać na ping?', 'Obsługuje tylko skonfigurowane listenery, nie ICMP Echo', ['Bo jest wyłączony', 'Bo używa IPv6', 'Bo TTL jest za mały'], 'Monitoruj portem.'],
    ['Czy security group AWS domyślnie przepuszcza ICMP?', 'Nie, trzeba dodać regułę', ['Tak, zawsze', 'Tak, tylko Echo', 'Tylko w IPv6'], 'Dodaj typ 3/4 przy tunelach.'],
    ['Co mierzy ping?', 'Osiągalność hosta przez ICMP i czas tam i z powrotem (RTT)', ['Przepustowość łącza', 'Liczbę skoków', 'Działanie usługi HTTP'], 'Nie usługę.'],
    ['Co oznacza „mdev” w podsumowaniu ping w Linuksie?', 'Odchylenie RTT (zmienność, jitter)', ['Maksymalny TTL', 'Liczbę skoków', 'Rozmiar pakietu'], 'Stabilność.'],
    ['Ile bajtów ma domyślny ping w Linuksie (dane)?', '56 bajtów danych (84 z nagłówkami IP i ICMP)', ['32 bajty', '1500 bajtów', '0 bajtów'], 'Windows: 32 B.'],
    ['Jak wysłać ping z TTL=3 w Linuksie?', 'ping -t 3', ['ping -s 3', 'ping -c 3', 'ping -i 3'], 'Odpowie 3. router.'],
    ['Czym jest asymetria w kontekście traceroute?', 'Droga powrotna może być inna niż droga pakietu w przód', ['Różna liczba sond', 'Różne MTU', 'Różne TTL'], 'Mierz z obu stron.'],
    ['Dlaczego warto zrobić mtr także z serwera do klienta?', 'Bo problem może leżeć na drodze powrotnej', ['Bo mtr działa tylko z serwera', 'Bo tak każe RFC', 'Bo klient nie ma ICMP'], 'Asymetria.'],
    ['Co zawiera komunikat ICMP o błędzie oprócz typu i kodu?', 'Nagłówek IP i początek (≥8 B) oryginalnego pakietu', ['Cały oryginalny pakiet zawsze', 'Hasło', 'Tablicę routingu'], 'Dopasowanie do gniazda.'],
    ['Jak firewall stanowy traktuje ICMP błędu dotyczący istniejącego połączenia?', 'Jako RELATED i przepuszcza', ['Jako NEW i blokuje', 'Jako INVALID', 'Ignoruje'], 'ct state related.'],
    ['Co oznacza ICMP 3/13 od firewalla?', 'Ruch odrzucony administracyjnie (REJECT)', ['Brak trasy', 'Za duży pakiet', 'TTL wyczerpany'], 'Szybki błąd dla klienta.'],
    ['Jaka jest wada DROP zamiast REJECT w sieci wewnętrznej?', 'Klienci czekają do timeoutu, a diagnoza jest trudniejsza', ['Ujawnia politykę', 'Zwiększa ruch', 'Wyłącza IPv6'], 'REJECT wewnątrz, DROP na brzegu.'],
    ['Dlaczego w traceroute mogą pojawić się adresy prywatne operatora?', 'Operatorzy używają ich na łączach wewnętrznych', ['To zawsze błąd', 'To atak', 'To adresy klienta'], 'Lub CGNAT.'],
    ['Co może ukryć skoki w traceroute w sieci operatora?', 'MPLS bez propagacji TTL', ['IPv6', 'DNSSEC', 'TLS'], 'Mniej widocznych skoków.'],
    ['Jak zgłosić problem sieciowy operatorowi, by był użyteczny?', 'mtr w obu kierunkach (200+ cykli), czas, protokół i objaw', ['Zrzut ekranu ping 4 pakietów', 'Tylko adres IP', 'Plik logów aplikacji'], 'Konkretne dane.'],
    ['Które narzędzie Prometheus sprawdza dostępność przez ICMP/TCP/HTTP?', 'blackbox exporter', ['node exporter', 'alertmanager', 'pushgateway'], 'Moduły http_2xx, tcp_connect, icmp.'],
    ['Co oznacza duża zmienność RTT już na pierwszym skoku (router domowy)?', 'Problem lokalny: Wi-Fi lub przeciążone łącze domowe', ['Awarię operatora', 'Atak DDoS na serwer', 'Problem DNS'], 'Testuj kablem.'],
    ['Jakie opóźnienie wynika z samej odległości Europa–USA?', 'Około 70–100 ms RTT', ['1–2 ms', '500–1000 ms', '0 ms'], 'Fizyka światłowodu.'],
    ['Jak w Wiresharku odfiltrować komunikaty TTL exceeded?', 'icmp.type == 11', ['icmp.type == 8', 'ip.ttl == 64', 'tcp.flags.reset == 1'], 'Traceroute.'],
    ['Jak w tcpdump przechwycić ICMP dla IPv4 i IPv6?', 'tcpdump -ni any icmp or icmp6', ['tcpdump port 1', 'tcpdump arp', 'tcpdump udp 53'], 'Oba protokoły.'],
    ['Który typ ICMPv6 musi przychodzić z hop limit 255, by był zaufany?', 'Neighbor Discovery i Router Advertisement', ['Echo Request', 'Packet Too Big', 'Time Exceeded'], 'Tylko z łącza.'],
    ['Co to jest RA Guard?', 'Blokowanie fałszywych Router Advertisement na portach przełącznika', ['Szyfrowanie RA', 'Typ VLAN', 'Wersja ICMP'], 'Ochrona IPv6.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
