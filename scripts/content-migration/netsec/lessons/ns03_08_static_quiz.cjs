// Quiz (100 pytan) do lekcji _ns_03/08_StaticRoutingAndFloatingRoutes.
const { quizBuilder } = require('./lib.cjs');
const { AD, rib, fmt, MIXED } = require('./ns03_08_static_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111408);
  const srcs = AD.filter(([n]) => n !== 'nieosiągalna (unusable)');

  // 1) Domyslny AD (9)
  for (const [n, v] of srcs) add(`Jaki jest domyślny dystans administracyjny (Cisco) dla: ${n}?`, String(v), srcs.map(([, x]) => String(x)).filter((x) => x !== String(v)).slice(0, 3).concat(['255', '100']), `${n} = ${v}.`);

  // 2) Ktora trasa trafi do RIB — pary zrodel dla tego samego prefiksu (21)
  let k = 0;
  for (let i = 0; i < srcs.length; i++) for (let j = i + 1; j < srcs.length; j++) {
    if (k >= 21) break;
    const [a, av] = srcs[i], [b, bv] = srcs[j];
    if ((i + j) % 2 === 1 && k > 12) continue;
    k++;
    const first = k % 2 ? [a, av] : [b, bv], second = k % 2 ? [b, bv] : [a, av];
    add(`Router zna 10.${k}.0.0/16 z dwóch źródeł: ${first[0]} (metryka ${10 + k}) i ${second[0]} (metryka ${2 + k % 3}). Która trasa trafi do RIB?`,
      `${av < bv ? a : b} — niższy AD (${Math.min(av, bv)})`,
      [`${av < bv ? b : a} — niższa metryka`, 'obie (ECMP)', `${av < bv ? b : a} — wyższy AD jest lepszy`, 'żadna — konflikt źródeł'],
      `AD ${a} = ${av}, ${b} = ${bv}; metryk różnych protokołów się nie porównuje.`);
  }

  // 3) Plywajace — jaki AD (8)
  for (const [main, v] of [['static', 1], ['eBGP', 20], ['OSPF', 110], ['RIP', 120], ['EIGRP (wewn.)', 90], ['iBGP', 200], ['IS-IS', 115], ['EIGRP (zewn.)', 170]]) {
    const ok = Math.min(v + 20, 250);
    add(`Trasa główna pochodzi z ${main}. Który AD sprawi, że zapasowa trasa statyczna będzie pływająca?`, String(ok), [...new Set([String(Math.max(v - 10, 1)), '1', String(Math.floor(v / 2)), '0', String(v)])].filter((x) => x !== String(ok) && Number(x) <= v).concat(['dowolny — AD nie ma znaczenia przy tym samym prefiksie']), `AD zapasu musi być wyższy niż ${v}.`);
  }

  // 4) RIB po awarii (6)
  for (const down of [['Gi0/1'], ['Gi0/0'], ['Gi0/2'], ['Gi0/4'], ['Gi0/1', 'Gi0/2'], ['Gi0/0', 'Gi0/3']]) {
    const r = rib(MIXED, down);
    for (const p of [down.includes('Gi0/0') ? '0.0.0.0/0' : down.includes('Gi0/4') ? '10.70.0.0/16' : down.includes('Gi0/1') && down.includes('Gi0/2') ? '10.50.0.0/16' : down.includes('Gi0/1') ? '10.60.0.0/16' : '10.50.0.0/16']) {
      const right = r[p] ? fmt(r[p]).split(' — ')[0] : 'brak trasy w RIB';
      const all = MIXED.filter((c) => c.prefix === p).map((c) => fmt({ ...c, ad: c.ad ?? AD.find(([n]) => n === c.source)[1] }).split(' — ')[0]);
      add(`Kandydaci: ${MIXED.map((c) => `${c.prefix} ${c.source}${c.ad ? ` AD ${c.ad}` : ''} via ${c.via} (${c.dev})`).join('; ')}. Co będzie w RIB dla ${p} po awarii ${down.join(' i ')}?`, right, [...all, 'brak trasy w RIB', `${p} [255/0] via 0.0.0.0`].filter((x) => x !== right), 'Trasy przez interfejsy DOWN odpadają; z pozostałych najniższy AD.');
    }
  }

  // 5) Merytoryczne (56)
  const facts = [
    ['Co jest ważniejsze przy wyborze trasy: długość prefiksu czy AD?', 'Długość prefiksu (AD porównuje się tylko dla tego samego prefiksu)', ['AD', 'Metryka', 'Kolejność konfiguracji'], 'LPM.'],
    ['Czy metryki OSPF i RIP porównuje się ze sobą?', 'Nie — między protokołami decyduje AD', ['Tak, niższa wygrywa', 'Tak, wyższa wygrywa', 'Tylko w IPv6'], 'Różne jednostki.'],
    ['Co oznacza AD 255?', 'Trasa nieużywana (niewiarygodna)', ['Najlepsza trasa', 'Trasa domyślna', 'Trasa do Null0'], 'Nie trafia do RIB.'],
    ['Czym jest trasa pływająca?', 'Zapasową trasą statyczną z AD wyższym niż trasa główna', ['Trasą zmieniającą next hop co minutę', 'Trasą multicast', 'Trasą bez maski'], 'Floating static.'],
    ['Kiedy trasa statyczna z next hopem jest usuwana z RIB bez IP SLA/BFD?', 'Gdy next hop staje się nieosiągalny lokalnie (np. interfejs DOWN)', ['Gdy zdalny serwer przestanie odpowiadać', 'Co 24 godziny', 'Gdy spadnie przepustowość'], 'Ograniczenie statyków.'],
    ['Co pozwala trasie statycznej reagować na awarie za sąsiadem?', 'IP SLA z trackingiem lub BFD', ['Wyższy AD', 'Niższa metryka', 'Maska /32'], 'Sondy i szybka detekcja.'],
    ['Dlaczego w IP SLA stosuje się delay down/up?', 'Aby uniknąć „mrugania” trasą przy chwilowych stratach', ['Aby przyspieszyć przełączenie', 'Aby szyfrować sondy', 'Aby zmniejszyć AD'], 'Histereza.'],
    ['Jaki AD ma domyślnie trasa statyczna?', '1', ['0', '5', '110'], 'Connected 0.'],
    ['Jaki AD ma sieć bezpośrednio podłączona?', '0', ['1', '20', '110'], 'Connected.'],
    ['Jak zapisać w Cisco trasę statyczną z AD 250?', 'ip route 10.9.0.0 255.255.0.0 10.0.13.2 250', ['ip route 10.9.0.0/16 10.0.13.2 metric 250', 'ip route 10.9.0.0 255.255.0.0 250 10.0.13.2', 'ip static 10.9.0.0 ad 250'], 'AD na końcu.'],
    ['Jak zapisać trasę domyślną w Cisco IOS?', 'ip route 0.0.0.0 0.0.0.0 <next hop>', ['ip default 0.0.0.0', 'ip route default <next hop>', 'route add default <next hop>'], 'Gateway of last resort.'],
    ['Czym jest trasa hosta?', 'Trasą z prefiksem /32 (IPv6 /128) do pojedynczego adresu', ['Trasą do sieci lokalnej', 'Trasą domyślną', 'Trasą multicast'], 'Najbardziej szczegółowa.'],
    ['Co to jest rekurencyjne wyszukiwanie trasy?', 'Ustalenie, jak dotrzeć do next hopa przez inną trasę (zwykle connected)', ['Podwójne szyfrowanie', 'Pętla routingu', 'Wyszukiwanie DNS'], 'Next hop musi być osiągalny.'],
    ['Dlaczego trasa tylko z interfejsem na Ethernecie jest problematyczna?', 'Router wysyła ARP o każdy cel i polega na proxy ARP', ['Bo jest wolniejsza o połowę', 'Bo nie działa z IPv4', 'Bo wymaga BGP'], 'OK na P2P.'],
    ['Kiedy trasa statyczna IPv6 musi mieć interfejs i next hop?', 'Gdy next hop jest adresem link-local (fe80::)', ['Zawsze', 'Gdy prefiks jest /64', 'Gdy używasz ::/0 z adresem globalnym'], 'Link-local jest niejednoznaczny.'],
    ['Które polecenie włącza routing IPv6 na routerze Cisco?', 'ipv6 unicast-routing', ['ipv6 enable', 'ip routing ipv6', 'ipv6 route enable'], 'Bez niego router nie przekazuje IPv6.'],
    ['Jak w Linuksie zrobić odpowiednik trasy pływającej bez FRR?', 'Dodać tę samą trasę z wyższą metryką', ['Dodać trasę z AD 250', 'Użyć ip rule', 'Nie da się'], 'Jądro nie zna AD.'],
    ['Co oznacza w FRR flaga „>” przy trasie?', 'Trasa wybrana jako najlepsza dla prefiksu', ['Trasa nieaktywna', 'Trasa zablokowana', 'Trasa IPv6'], '„*” = w FIB.'],
    ['Co oznacza w FRR flaga „*” przy trasie?', 'Trasa zainstalowana w FIB (jądrze)', ['Trasa domyślna', 'Trasa statyczna', 'Trasa zapasowa'], 'Używana do przekazywania.'],
    ['Czym jest RIB?', 'Bazą wybranych tras ze wszystkich źródeł', ['Tablicą ARP', 'Tablicą MAC', 'Listą sąsiadów OSPF'], 'Routing Information Base.'],
    ['Czym jest FIB?', 'Strukturą używaną do faktycznego przekazywania pakietów', ['Bazą haseł', 'Tablicą VLAN', 'Logiem routera'], 'Forwarding Information Base, Cisco CEF.'],
    ['Jak na Cisco zobaczyć tylko trasy statyczne?', 'show ip route static', ['show static', 'show ip static routes', 'show running static'], 'Kod S.'],
    ['Co robi trasa do Null0?', 'Odrzuca ruch do danego prefiksu', ['Przekazuje ruch do bramy domyślnej', 'Szyfruje ruch', 'Wysyła ruch broadcastem'], 'Blackhole.'],
    ['Po co tworzy się trasę Null0 dla agregatu?', 'Aby ruch do nieistniejących części agregatu nie krążył w pętli', ['Aby przyspieszyć agregat', 'Aby ukryć agregat', 'Aby wyłączyć OSPF'], 'Zapobieganie pętlom.'],
    ['Czym jest RTBH?', 'Zdalnie wyzwalanym odrzucaniem ruchu (blackhole) do/od adresu przy DDoS', ['Protokołem routingu', 'Typem VLAN', 'Szyfrowaniem tras'], 'BGP + Null0.'],
    ['Jakie community BGP oznacza blackhole według RFC 7999?', '65535:666', ['65535:65281', '0:0', '65000:100'], 'BLACKHOLE.'],
    ['Czym jest router-on-a-stick?', 'Routingiem między VLAN-ami przez podinterfejsy 802.1Q na jednym łączu', ['Routerem bez interfejsów', 'Routerem z jedną trasą', 'Przełącznikiem L3'], 'Trunk do przełącznika.'],
    ['Które polecenie przypisuje VLAN do podinterfejsu routera?', 'encapsulation dot1Q <vlan>', ['switchport access vlan', 'vlan <id> interface', 'ip vlan <id>'], 'Np. Gi0/0.10.'],
    ['Jak oznaczyć natywny VLAN na podinterfejsie routera?', 'encapsulation dot1Q 99 native', ['native vlan 99', 'switchport trunk native vlan 99', 'ip native 99'], 'Ramki bez tagu.'],
    ['Jaka jest główna wada router-on-a-stick?', 'Ruch między VLAN-ami przechodzi dwa razy przez jedno łącze', ['Brak obsługi VLAN', 'Brak obsługi IPv4', 'Wymaga BGP'], 'Wąskie gardło.'],
    ['Czym jest SVI?', 'Wirtualnym interfejsem VLAN na przełączniku (np. interface Vlan10)', ['Fizycznym portem', 'Protokołem routingu', 'Typem kabla'], 'Brama dla VLAN-u.'],
    ['Jakie polecenie włącza routing na przełączniku L3?', 'ip routing', ['switchport routing', 'router enable', 'vlan routing'], 'Bez niego SVI nie routują.'],
    ['Kiedy SVI jest w stanie down/down mimo no shutdown?', 'Gdy VLAN nie ma żadnego aktywnego portu lub nie istnieje', ['Gdy ma adres /24', 'Gdy włączono ip routing', 'Gdy jest IPv6'], 'Autostate.'],
    ['Czym jest routed port na przełączniku L3?', 'Portem przełączonym w tryb L3 (no switchport) z adresem IP', ['Portem trunk', 'Portem dostępowym w VLAN 1', 'Portem SPAN'], 'Łącza do routerów/firewalli.'],
    ['Czym różni się routing między VLAN-ami na SVI od router-on-a-stick pod względem wydajności?', 'SVI routuje sprzętowo w ASIC z prędkością przełączania', ['SVI jest zawsze wolniejsze', 'Nie ma różnicy', 'SVI działa tylko w IPv6'], 'Standard w kampusie.'],
    ['Jak w Linuksie utworzyć trasę blackhole?', 'ip route add blackhole 10.0.0.0/8', ['ip route add null 10.0.0.0/8', 'ip route del 10.0.0.0/8', 'iptables -j BLACKHOLE'], 'Także unreachable/prohibit.'],
    ['Dlaczego zmiany tras na zdalnym serwerze robi się z netplan try?', 'Bo automatycznie cofa zmiany, jeśli nie potwierdzisz (np. po utracie SSH)', ['Bo jest szybszy', 'Bo nie wymaga roota', 'Bo zapisuje do Gita'], 'Rollback po 120 s.'],
    ['Co oznacza „Gateway of last resort is not set” na Cisco?', 'Brak trasy domyślnej', ['Brak interfejsów', 'Brak OSPF', 'Brak adresu IPv6'], 'Ruch do nieznanych sieci odrzucany.'],
    ['Co się stanie, gdy dwa routery mają trasy domyślne wskazujące na siebie?', 'Pętla routingu — pakiety krążą do wyczerpania TTL', ['Podwójna przepustowość', 'Automatyczne ECMP', 'Nic'], 'Traceroute pokaże powtórki.'],
    ['Jak traceroute zdradza pętlę routingu?', 'Te same adresy powtarzają się naprzemiennie aż do limitu skoków', ['Same gwiazdki', 'Jeden skok', 'Rosnące RTT bez powtórzeń'], 'TTL expired in transit.'],
    ['Czy trasy w tablicy routingu VPC AWS są statyczne?', 'Tak, poza trasami propagowanymi (np. z VPN/TGW przez BGP)', ['Nie, zawsze z OSPF', 'Nie, zawsze z RIP', 'Tylko trasa local'], 'Konfigurujesz je w IaC.'],
    ['Jak w Terraformie dodać trasę do Transit Gateway?', 'route { cidr_block = "..." transit_gateway_id = ... } w aws_route_table', ['aws_tgw_route_static', 'aws_vpc { route = ... }', 'aws_subnet { gateway = tgw }'], 'Lub aws_route.'],
    ['Co robi CNI w trybie host-gw (np. Flannel)?', 'Dodaje na węzłach trasy statyczne do podsieci podów innych węzłów', ['Tworzy tunele VXLAN', 'Wyłącza routing', 'Konfiguruje BGP z Internetem'], 'Wymaga wspólnego L2.'],
    ['Dlaczego trasy statyczne są odporne na wstrzyknięcie fałszywych tras z sieci?', 'Bo nie ma protokołu, który można oszukać ogłoszeniem', ['Bo są szyfrowane', 'Bo mają AD 0', 'Bo są ukryte'], 'Ale wymagają kontroli zmian.'],
    ['Jaki komunikat syslog Cisco wskazuje zmianę konfiguracji z CLI?', '%SYS-5-CONFIG_I', ['%LINK-3-UPDOWN', '%OSPF-5-ADJCHG', '%SEC-6-IPACCESSLOG'], 'Monitoruj zmiany.'],
    ['Które narzędzia automatycznie wersjonują konfiguracje urządzeń sieciowych?', 'Oxidized, RANCID', ['Wireshark, tcpdump', 'nmap, masscan', 'Grafana, Prometheus'], 'Diff konfiguracji w Gicie.'],
    ['Jaki obraz FRR można uruchomić w Dockerze w labie?', 'quay.io/frrouting/frr', ['library/nginx', 'cisco/ios', 'docker/router'], 'zebra + staticd + inne demony.'],
    ['Jakie uprawnienie potrzebuje kontener FRR do zmiany tras?', 'NET_ADMIN', ['SYS_TIME', 'MKNOD', 'AUDIT_WRITE'], 'cap_add: [NET_ADMIN].'],
    ['Jaki sysctl jest potrzebny, by kontener/host przekazywał pakiety jak router?', 'net.ipv4.ip_forward=1', ['net.ipv4.tcp_syncookies=1', 'vm.swappiness=0', 'net.core.somaxconn=1024'], 'Forwarding.'],
    ['Którą trasę do 10.9.5.7 wybierze router: 10.9.0.0/16 static czy 10.9.5.0/24 OSPF?', '10.9.5.0/24 OSPF — dłuższy prefiks', ['10.9.0.0/16 static — niższy AD', 'Obie', 'Żadną'], 'LPM przed AD.'],
    ['Czym jest ECMP dla tras statycznych?', 'Kilka tras statycznych do tego samego prefiksu z tym samym AD — ruch dzielony', ['Trasa pływająca', 'Trasa do Null0', 'Trasa hosta'], 'Load sharing.'],
    ['Jaki jest typowy cel sondy IP SLA dla łącza do Internetu?', 'Stabilny adres za łączem operatora (np. własny serwer, DNS)', ['Adres bramy w tej samej podsieci', '127.0.0.1', 'Adres multicast'], 'Wykrywa awarie dalej.'],
    ['Czym różni się trasa w pełni określona od trasy z samym next hopem?', 'Podaje też interfejs, więc nie wymaga rekurencji', ['Ma AD 0', 'Działa tylko w IPv6', 'Ma maskę /32'], 'Fully specified.'],
    ['Co się stanie z trasą statyczną, której next hop nie leży w żadnej podłączonej sieci?', 'Nie zostanie zainstalowana (next hop nierozwiązywalny)', ['Zostanie zainstalowana z AD 255', 'Przejdzie przez trasę domyślną', 'Zostanie zamieniona na Null0'], 'Chyba że rekurencja przez inną trasę.'],
    ['Do czego w testach aplikacji może posłużyć trasa blackhole?', 'Do symulacji niedostępności zależności (chaos engineering)', ['Do przyspieszenia testów', 'Do generowania certyfikatów', 'Do tworzenia VLAN-ów'], 'Sprawdzisz timeouty i fallbacki.'],
    ['Dlaczego zapas przez LTE z AD 1 obok głównego OSPF jest błędem?', 'Bo statyk z AD 1 wygrywa z OSPF i cały ruch idzie przez LTE', ['Bo LTE nie obsługuje tras', 'Bo AD 1 jest niedozwolony', 'Bo OSPF wyłączy LTE'], 'AD zapasu > 110.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
