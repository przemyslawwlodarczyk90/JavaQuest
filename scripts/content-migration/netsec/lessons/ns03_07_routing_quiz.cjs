// Quiz (100 pytan) do lekcji _ns_03/07_RoutingTablesAndDefaultGateway.
const { quizBuilder } = require('./lib.cjs');
const { lookup, fmtRoute, LAPTOP, VPN_CONFLICT, ROUTER } = require('./ns03_07_routing_model.cjs');
const { parse, intIp } = require('./ns03_04_cidr_model.cjs');
const { rng } = require('./ns03_05_drill_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111407);
  const askTable = (name, table, ip) => {
    const r = lookup(table, ip);
    const right = r.route ? fmtRoute(r.route) : 'brak trasy (Network is unreachable)';
    const wrong = table.filter((x) => x !== r.route).map(fmtRoute);
    add(`${name} Którą trasę wybierze system dla ${ip}?`, right, [...wrong.slice(0, 2), ...wrong.reverse()], r.reason + '.');
  };

  // 1) Router z lekcji (12)
  const rt = `Tablica: ${ROUTER.map(fmtRoute).join('; ')}.`;
  for (const ip of ['10.20.30.129', '10.20.30.127', '10.20.31.1', '10.21.0.1', '10.0.0.1', '11.0.0.1', '192.168.50.1', '192.168.50.255', '192.168.49.9', '192.169.0.1', '10.20.30.255', '10.20.0.0']) askTable(rt, ROUTER, ip);

  // 2) Laptop (8) i konflikt VPN (6)
  const lt = `Laptop: ${LAPTOP.map(fmtRoute).join('; ')}.`;
  for (const ip of ['10.255.0.1', '172.17.255.254', '172.18.3.3', '172.16.0.10', '192.168.1.1', '192.168.2.1', '1.1.1.1', '10.8.0.1']) askTable(lt, LAPTOP, ip);
  const vt = `Laptop z VPN: ${VPN_CONFLICT.map(fmtRoute).join('; ')}.`;
  for (const ip of ['172.18.100.1', '172.18.128.1', '172.17.0.5', '129.0.0.1', '100.1.1.1', '192.168.1.30']) askTable(vt, VPN_CONFLICT, ip);

  // 3) Losowe tablice zagniezdzonych prefiksow (16)
  const r = rng(707);
  for (let i = 0; i < 16; i++) {
    const base = `10.${Math.floor(r() * 250)}.${Math.floor(r() * 256)}.0`;
    const p1 = 16 + Math.floor(r() * 4), p2 = p1 + 2 + Math.floor(r() * 3), p3 = p2 + 2 + Math.floor(r() * 3);
    const n1 = parse(`${base}/${p1}`), n2 = parse(`${intIp(n1.net + Math.floor(r() * 2 ** (p2 - p1)) * 2 ** (32 - p2))}/${p2}`), n3 = parse(`${intIp(n2.net + Math.floor(r() * 2 ** (p3 - p2)) * 2 ** (32 - p3))}/${p3}`);
    const table = [
      { dst: '0.0.0.0/0', via: '192.0.2.1', dev: 'eth0', metric: 100 },
      { dst: n1.cidr, via: '192.0.2.11', dev: 'eth1', metric: 10 },
      { dst: n2.cidr, via: '192.0.2.12', dev: 'eth2', metric: 500 },
      { dst: n3.cidr, via: '192.0.2.13', dev: 'eth3', metric: 900 },
    ];
    const targets = [n3.net + 1, n2.net + n2.size - 1, n1.net + n1.size - 2, n1.net + n1.size + 5];
    const ip = intIp(targets[i % 4]);
    askTable(`[${i + 1}] Tablica: ${table.map(fmtRoute).join('; ')}.`, table, ip);
  }

  // 4) Merytoryczne (58)
  const facts = [
    ['Co decyduje w pierwszej kolejności o wyborze trasy (w obrębie tabeli)?', 'Najdłuższy pasujący prefiks', ['Najniższa metryka', 'Kolejność wpisów w tabeli', 'Najnowsza trasa'], 'Longest prefix match.'],
    ['Kiedy liczy się metryka trasy?', 'Gdy kilka tras ma ten sam (najdłuższy) prefiks', ['Zawsze przed prefiksem', 'Tylko dla trasy domyślnej', 'Tylko w IPv6'], 'Rozstrzyga remisy.'],
    ['Co oznacza trasa 0.0.0.0/0?', 'Trasę domyślną pasującą do każdego adresu', ['Brak trasy', 'Trasę do loopbacku', 'Trasę tylko do sieci prywatnych'], 'Default route.'],
    ['Które polecenie pokazuje decyzję jądra Linuksa dla konkretnego celu?', 'ip route get <cel>', ['ip route flush', 'ip link show', 'ip neigh'], 'Z adresem źródłowym.'],
    ['Które polecenie PowerShell pokazuje trasę wybraną dla celu?', 'Find-NetRoute -RemoteIPAddress <cel>', ['Get-NetAdapter', 'Test-Path', 'Get-DnsClient'], 'Ostatni obiekt to trasa.'],
    ['Jak Windows liczy koszt trasy?', 'Metryka trasy + metryka interfejsu', ['Tylko metryka trasy', 'Tylko długość prefiksu', 'Liczba skoków'], 'InterfaceMetric bywa automatyczna.'],
    ['Co oznacza brak „via” w trasie Linuksa?', 'Sieć podłączona bezpośrednio — ARP o sam cel', ['Brak trasy', 'Trasę przez VPN', 'Błąd konfiguracji'], 'scope link.'],
    ['O jaki adres host wysyła ARP, gdy trasa ma „via 10.8.0.1”?', 'O adres bramy 10.8.0.1', ['O adres docelowy', 'O 255.255.255.255', 'O adres serwera DNS'], 'Następny skok.'],
    ['Co oznacza „proto kernel” przy trasie?', 'Trasa utworzona automatycznie z adresu interfejsu', ['Trasa z DHCP', 'Trasa z BGP', 'Trasa dodana ręcznie'], 'Connected.'],
    ['Co oznacza flaga „linkdown” przy trasie?', 'Interfejs nie ma łącza, trasa jest pomijana', ['Trasa jest zablokowana przez firewall', 'Trasa jest preferowana', 'Trasa do sieci zdalnej'], 'Np. pusty most docker0.'],
    ['Dlaczego VPN full tunnel często dodaje 0.0.0.0/1 i 128.0.0.0/1?', 'Bo są dłuższe niż /0 i przykrywają trasę domyślną bez jej usuwania', ['Bo /0 jest zabronione', 'Bo tak działa NAT', 'Aby podwoić przepustowość'], 'Łatwe przywrócenie.'],
    ['Po co VPN dodaje trasę /32 do serwera VPN przez starą bramę?', 'Aby pakiety tunelu nie trafiały same do tunelu', ['Aby przyspieszyć DNS', 'Aby zablokować serwer', 'Aby włączyć IPv6'], 'Unikanie pętli.'],
    ['Czym jest split tunnel?', 'VPN przejmuje tylko trasy do sieci firmy, reszta ruchu idzie lokalnie', ['VPN dzieli pakiety na pół', 'Dwa tunele jednocześnie', 'VPN tylko dla IPv6'], 'Full tunnel = cały ruch.'],
    ['Jakie ryzyko bezpieczeństwa niesie split tunnel?', 'Ruch do Internetu omija firmowe filtry i monitoring', ['Brak szyfrowania ruchu firmowego', 'Wyciek kluczy VPN', 'Brak DNS'], 'Rozważ SWG/ZTNA.'],
    ['Dlaczego trasa VPN 172.18.0.0/17 przechwytuje część sieci Dockera 172.18.0.0/16?', 'Bo /17 jest dłuższy niż /16', ['Bo VPN ma niższą metrykę', 'Bo VPN jest dodany później', 'Bo Docker nie używa tras'], 'LPM.'],
    ['Jaka jest trwała naprawa konfliktu adresów VPN i Dockera?', 'Zmiana zakresów sieci Dockera (bip, default-address-pools)', ['Usunięcie trasy VPN', 'Wyłączenie metryk', 'Restart laptopa'], 'Konflikt adresów rozwiązuje się adresacją.'],
    ['Czy dodanie trasy 172.18.0.0/16 z metryką 0 pokona trasę VPN 172.18.0.0/17?', 'Nie — /17 nadal jest dłuższym prefiksem', ['Tak — metryka 0 zawsze wygrywa', 'Tak — nowsza trasa wygrywa', 'Tylko w Windows'], 'Prefiks przed metryką.'],
    ['Czym jest policy routing?', 'Wyborem tabeli routingu na podstawie reguł (źródło, znacznik, interfejs)', ['Firewallem dla tras', 'Szyfrowaniem tras', 'Protokołem dynamicznym'], 'ip rule.'],
    ['Które polecenie pokazuje reguły policy routing?', 'ip rule', ['ip route', 'ip addr', 'ss -r'], 'Plus ip route show table <n>.'],
    ['Dlaczego przy WireGuard ip route (tabela main) może „kłamać”?', 'Bo ruch kierowany jest regułą do osobnej tabeli', ['Bo WireGuard nie tworzy tras', 'Bo ip route nie pokazuje IPv4', 'Bo tabela main jest szyfrowana'], 'Sprawdź ip route get.'],
    ['Czym jest ECMP?', 'Rozkładaniem ruchu na kilka tras o równym koszcie', ['Szyfrowaniem tras', 'Trasą awaryjną', 'Protokołem DNS'], 'Equal-Cost Multi-Path.'],
    ['Jak ECMP zwykle przydziela ruch do ścieżek?', 'Po przepływach (hash adresów i portów)', ['Po każdym pakiecie losowo', 'Po rozmiarze pakietu', 'Zawsze pierwszą ścieżką'], 'Zachowuje kolejność w połączeniu.'],
    ['Co się stanie, gdy host nie ma trasy domyślnej i wysyła do 8.8.8.8?', 'Błąd „Network is unreachable” bez wysyłania pakietu', ['Pakiet pójdzie broadcastem', 'Pakiet pójdzie do DNS', 'Pakiet zostanie wysłany do loopbacku'], 'Brak pasującej trasy.'],
    ['Dlaczego potrzebna jest trasa powrotna?', 'Routing jest jednokierunkowy — odpowiedź też musi znaleźć drogę', ['Bo TCP wymaga dwóch tras domyślnych', 'Bo ARP działa w jedną stronę', 'Nie jest potrzebna'], 'Częsta przyczyna timeoutów.'],
    ['Co powoduje asymetria routingu na serwerze z dwoma interfejsami?', 'Odpowiedzi wychodzą innym interfejsem i mogą być odrzucane (rp_filter, firewall)', ['Podwójną prędkość', 'Utratę adresu MAC', 'Błąd DNS'], 'Policy routing po źródle.'],
    ['Jak utrwalić trasę statyczną w Ubuntu z netplan?', 'Sekcja routes: z to/via w pliku netplan', ['ip route add w .bashrc', 'Wpis w /etc/hosts', 'Wpis w resolv.conf'], 'netplan try/apply.'],
    ['Jak dodać trwałą trasę w Windows poleceniem route?', 'route -p add', ['route add -temp', 'route save', 'netsh dns add'], 'Lub New-NetRoute.'],
    ['Co oznacza podsieć „publiczna” w AWS VPC?', 'Podsieć z trasą 0.0.0.0/0 do Internet Gateway', ['Podsieć z publicznym CIDR', 'Podsieć bez security group', 'Podsieć w innym regionie'], 'Prywatna: przez NAT Gateway.'],
    ['Jaka trasa zawsze istnieje w tabeli routingu VPC?', 'Trasa local dla CIDR VPC', ['Trasa do Internetu', 'Trasa do 169.254.169.254 przez IGW', 'Trasa do 0.0.0.0/1'], 'Ruch wewnątrz VPC.'],
    ['Co pokaże ip route get z adresem źródłowym (src)?', 'Adres, który system wstawi jako źródło pakietu', ['Adres bramy', 'Adres serwera DNS', 'Adres MAC'], 'Ważne przy kilku interfejsach.'],
    ['Jak w Javie sprawdzić adres źródłowy wybrany dla celu bez wysyłania danych?', 'DatagramSocket.connect(cel) i getLocalAddress()', ['InetAddress.isReachable()', 'Socket.getInetAddress()', 'NetworkInterface.getDefault()'], 'UDP connect wybiera trasę.'],
    ['Jak wymusić w Javie konkretny adres źródłowy połączenia?', 'Socket.bind(new InetSocketAddress(localIp, 0)) przed connect', ['System.setProperty("route", ...)', 'InetAddress.setDefault()', 'Nie da się'], 'Przydatne z policy routing.'],
    ['Co oznacza „Destination Host Unreachable” zgłoszone przez sam host?', 'Trasa jest, ale ARP o następny skok nie dostał odpowiedzi', ['Brak trasy', 'Firewall odrzucił pakiet', 'Błąd DNS'], 'Po kilku sekundach.'],
    ['Dlaczego dwie trasy domyślne z tą samą metryką są problemem?', 'Zachowanie jest nieprzewidywalne (ECMP/flapping) i może łamać połączenia', ['Są niedozwolone w Linuksie', 'Podwajają przepustowość', 'Wyłączają DNS'], 'Ustaw różne metryki.'],
    ['Co dzieje się z połączeniami TCP, gdy ruch przełączy się z Ethernetu na Wi-Fi?', 'Zwykle zrywają się, bo zmienia się adres źródłowy', ['Przechodzą bez zmian', 'Przyspieszają', 'Zmieniają port docelowy'], 'Chyba że MPTCP/QUIC migration.'],
    ['W jakim celu Kubernetes (CNI) dodaje trasy na węzłach?', 'Aby kierować ruch do podsieci podów na innych węzłach', ['Aby blokować pody', 'Aby obsługiwać DNS', 'Aby tworzyć certyfikaty'], 'Np. Calico z BGP.'],
    ['Co jest typową bramą domyślną kontenera w sieci bridge Dockera?', 'Adres mostu na hoście (np. 172.17.0.1)', ['Router domowy bezpośrednio', '127.0.0.1', '169.254.169.254'], 'Dalej NAT na hoście.'],
    ['Jak zobaczyć tablicę routingu kontenera bez narzędzi w obrazie?', 'nsenter -t <PID> -n ip route z hosta', ['docker logs', 'docker inspect --routes', 'ip route na hoście'], 'Wejście do namespace sieci.'],
    ['Co oznacza „Nexthop has invalid gateway” przy dodawaniu trasy?', 'Brama nie leży w żadnej bezpośrednio podłączonej sieci', ['Brama nie odpowiada na ping', 'Trasa już istnieje', 'Brak uprawnień root'], 'Brama musi być na łączu.'],
    ['Co oznacza komunikat „RTNETLINK answers: File exists” przy ip route add?', 'Identyczna trasa już istnieje', ['Brak interfejsu', 'Zła maska', 'Brak uprawnień'], 'Użyj ip route replace.'],
    ['Które polecenie podmienia istniejącą trasę bez błędu?', 'ip route replace', ['ip route add -f', 'ip route swap', 'ip route update'], 'Idempotentne.'],
    ['Jaki atak w Internecie wykorzystuje regułę najdłuższego prefiksu?', 'Przejęcie prefiksu BGP przez ogłoszenie bardziej szczegółowej trasy', ['Phishing', 'SQL injection', 'Brute force SSH'], 'RPKI/ROV pomaga.'],
    ['Jakie zdarzenie na stacji roboczej może wskazywać na nieautoryzowany tunel?', 'Nowe trasy 0.0.0.0/1 i 128.0.0.0/1 poza firmowym klientem VPN', ['Nowy wpis w /etc/hosts', 'Zmiana tapety', 'Aktualizacja przeglądarki'], 'Monitoruj zmiany tras.'],
    ['Co oznacza „scope link” w trasie?', 'Cel jest bezpośrednio na łączu tego interfejsu', ['Trasa globalna', 'Trasa tylko dla loopbacku', 'Trasa IPv6'], 'Brak bramy.'],
    ['Dlaczego VPN tunelujący tylko IPv4 może powodować wyciek?', 'Ruch IPv6 nadal idzie lokalną siecią poza tunelem', ['Bo IPv4 jest nieszyfrowany', 'Bo DNS nie działa', 'Bo MTU spada'], 'Tunel lub blokada IPv6.'],
    ['Jak Windows zwykle preferuje Ethernet nad Wi-Fi przy dwóch trasach domyślnych?', 'Przez niższą automatyczną metrykę interfejsu przewodowego', ['Przez kolejność instalacji kart', 'Przez długość prefiksu', 'Losowo'], 'Zależna od prędkości.'],
    ['Do czego służy route table w AWS przypisana do podsieci?', 'Określa trasy (IGW, NAT, TGW, peering) dla ruchu z tej podsieci', ['Określa security group', 'Określa DNS', 'Określa AMI'], 'LPM jak na hoście.'],
    ['Jak zapewnić dostęp prywatnej podsieci do S3 bez Internetu?', 'Gateway VPC endpoint z wpisem w tablicy routingu', ['Publiczny adres IP', 'Usunięcie trasy local', 'Trasa 0.0.0.0/1'], 'Prefix list S3.'],
    ['Która trasa wygra dla 10.1.2.3: 10.0.0.0/8 metric 1 czy 10.1.0.0/16 metric 1000?', '10.1.0.0/16 metric 1000', ['10.0.0.0/8 metric 1', 'Obie po połowie', 'Żadna'], 'Dłuższy prefiks.'],
    ['Która trasa wygra dla 10.1.2.3: 10.1.0.0/16 metric 50 czy 10.1.0.0/16 metric 20?', '10.1.0.0/16 metric 20', ['10.1.0.0/16 metric 50', 'Obie (ECMP)', 'Żadna'], 'Równy prefiks, niższa metryka.'],
    ['Co zawiera tabela local w Linuksie?', 'Trasy do adresów lokalnych hosta i broadcastów', ['Trasy VPN', 'Trasę domyślną', 'Trasy Dockera'], 'ip route show table local.'],
    ['Jak zobaczyć wszystkie tabele routingu w Linuksie?', 'ip route show table all', ['ip route -a', 'route -all', 'netstat -r -all'], 'Plus ip -6.'],
    ['Co oznacza „metric 600” przy trasie z DHCP na Wi-Fi w NetworkManager?', 'Wyższy koszt, więc Ethernet (np. metric 100) będzie preferowany przy równym prefiksie', ['600 skoków', '600 ms opóźnienia', 'Priorytet najwyższy'], 'NetworkManager: 100 Ethernet, 600 Wi-Fi.'],
    ['Kiedy routing nie rozwiąże problemu dwóch sieci o tym samym zakresie?', 'Zawsze — potrzebny NAT lub zmiana adresacji', ['Gdy dodasz metrykę', 'Gdy użyjesz ECMP', 'Gdy włączysz IPv6'], 'Niejednoznaczność adresów.'],
    ['Co jest pierwszym krokiem diagnozy „po włączeniu VPN nie działa X”?', 'ip route get <adres X> przy włączonym i wyłączonym VPN', ['Reinstalacja Dockera', 'Restart routera domowego', 'Zmiana DNS na 8.8.8.8'], 'Porównaj decyzje.'],
    ['Na co wskazuje „Network is unreachable” tylko przy włączonym VPN?', 'VPN usunął lub przykrył trasy (np. full tunnel bez dostępu), albo brak trasy w tabeli VPN', ['Awarię karty Wi-Fi', 'Błąd certyfikatu TLS', 'Brak miejsca na dysku'], 'Sprawdź ip rule i tabele.'],
    ['Czym różni się tablica routingu routera od hosta?', 'Niczym zasadniczo — router ma więcej tras i przekazuje cudze pakiety', ['Router nie używa prefiksów', 'Host nie ma trasy domyślnej', 'Router nie używa metryk'], 'Host też jest „routerem”.'],
    ['Co musi być włączone w Linuksie, aby host przekazywał cudze pakiety jak router?', 'net.ipv4.ip_forward=1', ['net.ipv4.tcp_ecn=1', 'net.core.somaxconn', 'fs.file-max'], 'Docker włącza to sam.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
