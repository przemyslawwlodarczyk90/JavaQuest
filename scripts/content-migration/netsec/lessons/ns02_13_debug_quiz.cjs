// Quiz (100 pytan) do lekcji _ns_02/13_DebugLanProblems. Pytania liczbowe licza sie modelem ns02_13_debug_model.cjs.
const { quizBuilder } = require('./lib.cjs');
const { netStr, bcast, decide, maxPayload, sameNet, intIp, netOf } = require('./ns02_13_debug_model.cjs');

const DECISIONS = [
  'bezpośrednio (ARP o adres celu)',
  'przez bramę (ARP o adres bramy)',
  'brak trasy — „Network is unreachable”',
  'brama poza podsiecią — trasa domyślna nie zadziała',
];

module.exports = function buildQuiz({ CASES }) {
  const { quiz, add } = quizBuilder(2026111413);

  // 1) Decyzja hosta: bezposrednio / przez brame / brak trasy (16)
  const extra = [
    ['10.13.0.13', 24, '10.13.0.1', '10.13.0.40'],
    ['10.13.0.20', 28, '10.13.0.1', '10.13.0.25'],
    ['10.13.0.20', 28, '10.13.0.17', '10.13.0.40'],
    ['192.168.1.10', 16, '192.168.0.1', '192.168.77.3'],
    ['192.168.1.10', 24, '192.168.1.1', '192.168.2.10'],
    ['10.0.0.5', 8, null, '10.200.1.1'],
    ['10.0.0.5', 24, null, '10.0.1.5'],
    ['172.16.1.100', 25, '172.16.1.129', '1.1.1.1'],
    ['172.16.1.200', 25, '172.16.1.129', '172.16.1.10'],
    ['100.64.3.3', 22, '100.64.0.1', '100.64.2.200'],
  ];
  for (const [a, p, gw, b] of [...CASES, ...extra]) {
    add(`Host ${a}/${p}, brama ${gw ?? 'nieskonfigurowana'}. Jak host wyśle pakiet do ${b}?`,
      decide(a, p, gw, b), DECISIONS,
      `Sieć hosta ${netStr(a, p)}, cel w masce hosta ${netStr(b, p)}. Decyzję podejmuje nadawca swoją maską.`);
  }

  // 2) Adres sieci (12)
  const nets = [['10.13.0.13', 28], ['10.13.0.200', 26], ['192.168.10.77', 27], ['172.20.33.5', 20], ['10.1.2.3', 30],
    ['192.168.5.130', 25], ['10.99.0.10', 24], ['172.31.255.254', 19], ['10.13.0.100', 29], ['192.168.200.9', 23],
    ['100.64.7.7', 21], ['10.250.10.10', 12]];
  for (const [ip, p] of nets) {
    const right = netStr(ip, p);
    const wrongs = [p - 1, p + 1, p - 2].filter((q) => q > 0 && q < 32).map((q) => `${intIp(netOf(ip, q))}/${p}`);
    wrongs.push(`${ip}/${p}`, `${intIp(netOf(ip, p) + 2 ** (32 - p))}/${p}`, `${intIp(Math.max(netOf(ip, p) - 2 ** (32 - p), 0))}/${p}`,
      `${intIp(netOf(ip, p) + 1)}/${p}`, `${intIp(netOf(ip, p))}/${p === 24 ? 16 : 24}`);
    add(`Jaki jest adres sieci dla ${ip}/${p}?`, right, wrongs, `Wyzeruj ${32 - p} bitów hosta: ${right}.`);
  }

  // 3) Broadcast (8)
  for (const [ip, p] of nets.slice(0, 8)) {
    const right = bcast(ip, p);
    const size = 2 ** (32 - p);
    const wrongs = [bcast(ip, p + 1), bcast(ip, p - 1), intIp(netOf(ip, p) + size - 2), bcast(ip, p + 2),
      intIp(netOf(ip, p) + size), intIp(netOf(ip, p)), intIp(netOf(ip, p) + 2 * size - 1)];
    add(`Jaki jest adres rozgłoszeniowy (broadcast) podsieci ${ip}/${p}?`, right, wrongs, `Ustaw ${32 - p} bitów hosta na 1: ${right}.`);
  }

  // 4) Rozmiar danych w ping -M do (6)
  for (const [mtu, v6] of [[1500, false], [1500, true], [9000, false], [1420, false], [1400, true], [1492, false]]) {
    const right = String(maxPayload(mtu, v6));
    add(`Jaki największy rozmiar danych (-s) przejdzie w ${v6 ? 'ping -6' : 'ping'} -M do przy MTU ${mtu} (${v6 ? 'IPv6' : 'IPv4'})?`, right,
      [String(mtu), String(mtu - (v6 ? 28 : 48)), String(mtu - 20), String(mtu - 8)],
      `${mtu} − ${v6 ? '40 (IPv6) − 8 (ICMPv6)' : '20 (IPv4) − 8 (ICMP)'} = ${right}.`);
  }

  // 5) Ta sama podsiec? (8)
  const pairs = [['10.13.0.13', '10.13.0.14', 28], ['10.13.0.13', '10.13.0.40', 28], ['10.13.0.13', '10.13.0.40', 24],
    ['192.168.1.130', '192.168.1.10', 25], ['172.16.4.1', '172.16.7.254', 22], ['172.16.4.1', '172.16.8.1', 22],
    ['10.0.0.1', '10.0.0.6', 30], ['10.0.0.1', '10.0.0.2', 31]];
  for (const [a, b, p] of pairs) {
    const same = sameNet(a, b, p);
    add(`Czy przy prefiksie /${p} adresy ${a} i ${b} są w tej samej podsieci?`,
      same ? `Tak — obie w ${netStr(a, p)}` : `Nie — ${netStr(a, p)} i ${netStr(b, p)}`,
      same ? [`Nie — ${netStr(a, p + 1)} i ${netStr(b, p + 1 === 32 ? p : p + 1)}`, 'Nie — różnią się ostatnim oktetem', 'Nie da się ustalić bez bramy']
        : [`Tak — obie w ${netStr(a, Math.max(p - 8, 1))}`, 'Tak — różnią się tylko ostatnim oktetem', 'Nie da się ustalić bez bramy'],
      `Porównaj adresy sieci po zamaskowaniu: ${netStr(a, p)} vs ${netStr(b, p)}.`);
  }

  // 6) Pytania merytoryczne (50)
  const facts = [
    ['Od którego kroku zaczyna się metodyczna diagnoza „sieć nie działa” na hoście?', 'Od stanu łącza (ip -br link, ethtool)', ['Od DNS', 'Od restartu routera', 'Od wyłączenia firewalla'], 'Od kabla w górę.'],
    ['Które polecenie pokazuje decyzję jądra Linuksa, którędy pójdzie pakiet do konkretnego celu?', 'ip route get <cel>', ['ip route flush', 'ip neigh flush', 'ip link set up'], 'Pokazuje bramę, interfejs i adres źródłowy.'],
    ['Co oznacza stan LOWER_UP w wyniku ip link?', 'Warstwa fizyczna ma nośnik (kabel/łącze aktywne)', ['Interfejs ma adres IP', 'Działa DHCP', 'Interfejs jest w VLAN 1'], 'UP = włączony administracyjnie, LOWER_UP = jest sygnał.'],
    ['Jak arping pomaga wykryć duplikat IP?', 'Na zapytanie o jeden adres odpowiadają dwa różne MAC', ['Pokazuje dwie trasy domyślne', 'Zwraca błąd DNS', 'Pokazuje MTU'], 'Dowód konfliktu adresu.'],
    ['Co robi arping -D?', 'Sprawdza, czy adres jest już używany (Duplicate Address Detection, RFC 5227)', ['Usuwa wpis ARP', 'Wysyła gratuitous ARP', 'Zmienia MAC'], 'Kod wyjścia 1 = adres zajęty.'],
    ['Jak nazywa się stan adresu IPv6, którego DAD wykrył konflikt?', 'dadfailed', ['tentative', 'deprecated', 'preferred'], 'tentative = w trakcie sprawdzania.'],
    ['Który objaw jest typowy dla duplikatu IP?', 'Połączenia raz działają, raz nie; ARP sąsiadów zmienia MAC', ['Natychmiastowe Network is unreachable', 'Brak LOWER_UP', 'Adres 169.254.x.x'], 'Dwa hosty walczą o adres.'],
    ['Co najlepiej zapobiega duplikatom IP w sieci firmowej?', 'Rezerwacje DHCP/IPAM oraz DHCP snooping z Dynamic ARP Inspection', ['Krótsza dzierżawa DNS', 'Większe MTU', 'Wyłączenie ARP'], 'Plus wykluczenie adresów statycznych z puli.'],
    ['Jak szybko odświeżyć tablice ARP sąsiadów po zmianie adresu hosta?', 'Wysłać gratuitous ARP (arping -U/-A) z hosta', ['Zmienić MTU', 'Dodać trasę domyślną', 'Zrestartować DNS'], 'Sąsiedzi aktualizują wpis.'],
    ['Host z maską /28 zamiast /24 może pingować bramę .1. Dlaczego błąd nie jest od razu widoczny?', 'Brama mieści się w jego węższej podsieci, więc ruch do niej działa normalnie', ['Bo brama ignoruje maskę', 'Bo ARP nie używa maski', 'Bo ping używa UDP'], 'Testuj cele spoza /28.'],
    ['Jaki komunikat ICMP często zdradza za wąską maskę hosta?', 'ICMP Redirect (typ 5) od routera', ['ICMP Echo Reply', 'ICMP Time Exceeded', 'ICMP Source Quench'], 'Router wskazuje bezpośrednią drogę.'],
    ['Co się dzieje przy za szerokiej masce (np. /16 zamiast /24)?', 'Host wysyła ARP o adresy, które są za routerem, i nie dostaje odpowiedzi', ['Host wysyła wszystko do bramy', 'Host przestaje mieć łącze', 'DNS przestaje działać'], 'Destination Host Unreachable od nadawcy.'],
    ['Kiedy pojawia się komunikat „connect: Network is unreachable”?', 'Gdy w lokalnej tablicy routingu nie ma pasującej trasy', ['Gdy cel odrzuca połączenie', 'Gdy ARP nie dostał odpowiedzi', 'Gdy DNS nie zna nazwy'], 'Błąd natychmiastowy, bez wysyłki.'],
    ['Kiedy host sam zgłasza „Destination Host Unreachable”?', 'Trasa istnieje, ale ARP o następny przeskok nie dostał odpowiedzi', ['Brak trasy domyślnej', 'Port zamknięty', 'Zły rekord DNS'], 'Po ok. 3 s prób ARP.'],
    ['Którą opcję DHCP należy sprawdzić, gdy klient dostaje adres, ale nie ma trasy domyślnej?', 'Opcja 3 (Router)', ['Opcja 6 (DNS)', 'Opcja 43', 'Opcja 82'], 'Bez niej klient nie zna bramy.'],
    ['Jaki błąd zwróci Linux przy próbie dodania bramy spoza podsieci interfejsu?', 'Nexthop has invalid gateway', ['File exists', 'Permission denied', 'No buffer space available'], 'Brama musi być osiągalna bezpośrednio.'],
    ['Serwer widzi żądania w tcpdump, ale klient nie dostaje odpowiedzi. Co sprawdzisz w pierwszej kolejności?', 'Trasę powrotną na serwerze (ip route get <klient>)', ['MTU klienta', 'DNS klienta', 'Licencję serwera'], 'Lub asymetrię i rp_filter.'],
    ['Co jest klasycznym objawem niezgodnego MTU?', 'Ping i logowanie SSH działają, a transfery i HTTPS wiszą', ['Brak adresu IP', 'Adres 169.254.x.x', 'Natychmiastowe Network is unreachable'], 'Małe pakiety przechodzą, duże giną.'],
    ['Dlaczego PMTUD nie pomoże przy różnym MTU w jednej domenie L2?', 'Nie ma routera, który odesłałby ICMP Fragmentation needed', ['Bo PMTUD działa tylko w IPv6', 'Bo przełącznik fragmentuje ramki', 'Bo TCP nie używa MTU'], 'Ramka jest po prostu odrzucana.'],
    ['Co robi flaga -M do w poleceniu ping na Linuksie?', 'Ustawia bit DF — zakazuje fragmentacji', ['Wymusza IPv6', 'Wyłącza ICMP', 'Ustawia TTL na 1'], 'Pozwala testować MTU.'],
    ['Ile bajtów nagłówków dodaje ping IPv4 do rozmiaru danych z -s?', '28 (20 IPv4 + 8 ICMP)', ['20', '40', '48'], '1472 + 28 = 1500.'],
    ['Gdzie najczęściej pojawia się problem z MTU w środowisku developera?', 'W tunelach: VPN, VXLAN, sieci overlay Dockera i Kubernetesa', ['W pętli zwrotnej 127.0.0.1', 'W DNS', 'W NTP'], 'Narzut enkapsulacji zmniejsza MTU.'],
    ['Co robi MSS clamping na bramie?', 'Zmniejsza wartość MSS w pakietach SYN, aby segmenty TCP mieściły się w MTU ścieżki', ['Zwiększa MTU', 'Blokuje ICMP', 'Wyłącza fragmentację w IPv6'], 'iptables TCPMSS --clamp-mss-to-pmtu.'],
    ['Host ma łącze UP i poprawny adres, ale ARP do bramy jest INCOMPLETE. Co jest częstą przyczyną?', 'Port przełącznika w złym VLAN-ie', ['Zła maska DNS', 'Za duże MTU', 'Brak IPv6'], 'Host jest w innej domenie rozgłoszeniowej.'],
    ['Jak z poziomu hosta sprawdzić VLAN portu przełącznika?', 'Odczytać LLDP (lldpctl lub tcpdump), pole Port VLAN ID', ['ip route get', 'resolvectl', 'ss -ltnp'], 'Jeśli przełącznik wysyła LLDP.'],
    ['Co oznacza adres 169.254.x.x na interfejsie?', 'Host nie dostał odpowiedzi DHCP i nadał sobie adres link-local (APIPA)', ['Adres publiczny', 'Adres pętli zwrotnej', 'Adres multicast'], 'RFC 3927.'],
    ['Który krok metodyki CompTIA następuje po przetestowaniu teorii?', 'Ustalenie planu działania i identyfikacja potencjalnych skutków', ['Udokumentowanie wyników', 'Zidentyfikowanie problemu', 'Weryfikacja pełnej funkcjonalności'], 'Kolejność z Network+.'],
    ['Jaki jest ostatni krok metodyki rozwiązywania problemów CompTIA?', 'Udokumentowanie ustaleń, działań i wyników', ['Restart urządzenia', 'Eskalacja', 'Zmiana konfiguracji'], 'Dokumentacja to część procesu.'],
    ['Dlaczego nie zmienia się kilku rzeczy naraz podczas diagnozy?', 'Nie wiadomo, która zmiana pomogła, i trudno ją odwrócić lub opisać', ['Bo konfiguracja się nie zapisze', 'Bo przełącznik się zrestartuje', 'Bo DNS się wyczyści'], 'Jedna zmiana → test.'],
    ['Czym jest network namespace w Linuksie?', 'Izolowaną kopią stosu sieciowego: interfejsy, adresy, trasy, firewall', ['Nazwą domeny DNS', 'Typem VLAN-u', 'Plikiem konfiguracyjnym netplan'], 'Podstawa kontenerów.'],
    ['Do czego służy para veth?', 'Łączy dwa namespace’y lub namespace z mostem jak wirtualny kabel', ['Szyfruje ruch', 'Nadaje adresy IP', 'Tłumaczy adresy NAT'], 'Dwa końce jednego „kabla”.'],
    ['Jakie polecenie uruchamia program w konkretnym namespace?', 'ip netns exec <ns> <polecenie>', ['ip ns run', 'netns start', 'nsenter --vlan'], 'Lub ip -n <ns> dla poleceń ip.'],
    ['Które polecenie pokazuje tablicę MAC mostu Linuksa?', 'bridge fdb show', ['ip route show', 'arp -a', 'ss -tan'], 'Odpowiednik show mac address-table.'],
    ['Dlaczego poprawka „ip addr add” na serwerze może nie przetrwać?', 'To zmiana w runtime; po restarcie wraca konfiguracja ze źródła prawdy (netplan, DHCP, cloud-init)', ['Bo ip wymaga licencji', 'Bo jądro usuwa adresy co godzinę', 'Bo DNS nadpisuje adres'], 'Utrwal w źródle prawdy.'],
    ['Dlaczego „iptables -F” jako krok diagnozy na produkcji to zły pomysł?', 'Usuwa całą ochronę hosta i niczego nie dowodzi', ['Bo spowalnia DNS', 'Bo zmienia MTU', 'Bo kasuje trasy'], 'Czytaj liczniki reguł i logi.'],
    ['Co pokaże ss -ltnp przy błędzie „Connection refused”?', 'Czy usługa w ogóle nasłuchuje i na jakim adresie/porcie', ['Tablicę ARP', 'Trasę domyślną', 'MTU interfejsu'], 'Np. nasłuch tylko na 127.0.0.1.'],
    ['Co zwykle oznacza „Connection refused” w odróżnieniu od timeoutu?', 'Host jest osiągalny, ale na porcie nic nie słucha (RST)', ['Firewall gubi pakiety', 'Brak trasy', 'Zły DNS'], 'Timeout = coś po drodze gubi pakiety.'],
    ['Który wyjątek Javy oznacza przekroczenie czasu przy Socket.connect z timeoutem?', 'SocketTimeoutException', ['ConnectException', 'UnknownHostException', 'BindException'], 'ConnectException = odmowa (RST).'],
    ['Który wyjątek Javy oznacza, że nazwa hosta nie została rozwiązana?', 'UnknownHostException', ['NoRouteToHostException', 'SocketTimeoutException', 'PortUnreachableException'], 'Problem DNS, nie routingu.'],
    ['Jaki filtr Wiresharka pokaże przekierowania ICMP?', 'icmp.type == 5', ['icmp.type == 8', 'arp.opcode == 1', 'tcp.flags.syn == 1'], 'Typ 8 to Echo Request.'],
    ['Jaki filtr tcpdump przechwyci ruch DHCP?', 'port 67 or port 68', ['port 53', 'port 123', 'port 161'], 'Serwer 67, klient 68.'],
    ['Co oznacza asymetryczny ruch i dlaczego szkodzi?', 'Pakiety w obie strony idą różnymi drogami; stanowy firewall odrzuca odpowiedzi „spoza połączenia”', ['Różne MTU w obie strony', 'Różne serwery DNS', 'Różne wersje TLS'], 'Częsty skutek złej maski lub dwóch bram.'],
    ['Co robi parametr rp_filter w Linuksie?', 'Odrzuca pakiety, których adres źródłowy nie pasuje do trasy powrotnej przez interfejs wejściowy', ['Ogranicza MTU', 'Filtruje DNS', 'Szyfruje ARP'], 'Reverse path filtering — psuje ruch asymetryczny.'],
    ['Jak check.sh pomaga w pracy zespołu poza labem?', 'Jako test dymny środowiska w CI/CD przed wdrożeniem aplikacji', ['Zastępuje firewall', 'Konfiguruje DHCP', 'Generuje certyfikaty'], 'Automatyczny sędzia gotowości sieci.'],
    ['Jaki test potwierdza naprawę MTU 1500 na ścieżce IPv4?', 'ping -M do -s 1472 przechodzi, a -s 1473 nie', ['ping bez opcji przechodzi', 'traceroute pokazuje 3 skoki', 'dig zwraca rekord A'], 'Test granicy z obu stron.'],
    ['Po co w Debug Labie host „drukarka” 10.13.0.40?', 'To cel spoza /28, który ujawnia złą maskę ns-c', ['Do testu DNS', 'Do testu MTU', 'Jako serwer DHCP'], 'Testuj kilka celów.'],
    ['Czym jest IPAM?', 'System zarządzania adresacją IP (np. NetBox) — źródło prawdy o podsieciach i przydziałach', ['Protokół routingu', 'Typ kabla', 'Rodzaj ataku'], 'Zapobiega duplikatom i złym maskom.'],
    ['Dlaczego test z jednego „dobrego” celu (bramy) nie wystarcza?', 'Niektóre błędy (zła maska) ujawniają się tylko dla części adresów', ['Bo brama nie odpowiada na ping', 'Bo ICMP jest zawsze blokowany', 'Bo ARP działa tylko raz'], 'Brama, sąsiad, koniec podsieci, host za routerem.'],
    ['Które narzędzie śledzi zmiany par IP–MAC w sieci i alarmuje?', 'arpwatch', ['dig', 'ethtool', 'netplan'], 'Wykrywa konflikty i spoofing.'],
    ['Co powinna zawierać notatka z diagnozy?', 'Objaw, dowody (polecenia i wyniki), przyczynę, zmianę, test i zapobieganie', ['Tylko „zrestartowano i działa”', 'Hasła administratora', 'Pełną konfigurację wszystkich urządzeń'], 'Wzór post-mortem.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
