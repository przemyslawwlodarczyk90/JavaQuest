// Quiz (100 pytan) do lekcji _ns_03/12_Ipv6InDepth.
const { quizBuilder } = require('./lib.cjs');
const { full, compress, eui64, linkLocal, solicitedNode, multicastMac, classify6, net6, sub6, count6 } = require('./ns03_12_ipv6_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111412);

  // 1) Skracanie (12)
  const longs = ['2001:0db8:0000:0000:0008:0800:200c:417a', '2001:0db8:0000:0001:0000:0000:0000:0001', '2001:0db8:0000:0000:0001:0000:0000:0001', 'fe80:0000:0000:0000:0a00:27ff:fe4e:66a1', '2001:0db8:00a0:0000:0000:0000:0000:0000', '0000:0000:0000:0000:0000:0000:0000:0001',
    'ff02:0000:0000:0000:0000:0000:0000:0002', 'fd5a:3c91:7e04:0012:0000:0000:0000:0020', '2001:0db8:0001:0000:0001:0001:0001:0001', '2a00:1450:4001:080b:0000:0000:0000:200e', '2001:0db8:0000:0000:0000:ff00:0042:8329', '2001:0db8:0aaa:0000:0000:00bb:0000:0001'];
  for (const a of longs) {
    const c = compress(a);
    const noLead = a.split(':').map((g) => g.replace(/^0+(?=.)/, '')).join(':');
    add(`Jaki jest poprawny skrócony zapis (RFC 5952) adresu ${a}?`, c,
      [noLead, c.toUpperCase(), c.replace('::', ':0::').replace(':0::', '::0:'), noLead.replace(/:0:/, '::'), c.replace(/::/, ':0:0:'), a, a.replace(/:0000/, ':0')].filter((x) => x !== c), 'Zera wiodące precz, najdłuższy ciąg zer → :: raz, małe litery.');
  }

  // 2) Rozwijanie (6)
  for (const a of ['2001:db8::1', 'fe80::1:2', '::1', 'fd5a:3c91:7e04:12::20', '2001:db8:0:1::', 'ff02::1:ff3c:4d5e']) {
    const f = full(a);
    const groups = f.split(':');
    add(`Jak wygląda pełny zapis adresu ${a}?`, f, [groups.slice(0, 7).join(':'), [...groups, '0000'].slice(1).join(':'), f.replace(/0000:/, ''), f.replace(/:0000/g, ':0')].filter((x) => x !== f), 'Brakujące grupy w miejscu ::.');
  }

  // 3) Klasyfikacja (10)
  const CATS = { '::1/128': 'loopback', 'fe80::/10': 'link-local', 'fc00::/7': 'ULA', '2001:db8::/32': 'dokumentacyjny', '2000::/3': 'GUA (globalny unicast)', 'ff00::/8': 'multicast', '::ffff:0:0/96': 'IPv4-mapped', '::/128': 'nieokreślony', '64:ff9b::/96': 'NAT64' };
  const ALL = Object.values(CATS);
  for (const a of ['::1', 'fe80::21a:2bff:fe3c:4d5e', 'fd00:1234::7', '2001:db8:5::10', '2606:4700:4700::1111', 'ff02::1', 'ff05::1:3', '::ffff:10.0.0.1', '::', '64:ff9b::c000:221']) {
    const right = CATS[classify6(a).range];
    add(`Jakiego typu jest adres ${a}?`, right, ALL.filter((x) => x !== right), `${classify6(a).range}.`);
  }

  // 4) EUI-64 i link-local (8)
  for (const mac of ['00:1a:2b:3c:4d:5e', '02:42:ac:11:00:02', '08:00:27:4e:66:a1', 'b8:27:eb:12:34:56']) {
    const id = eui64(mac);
    const b = mac.split(':');
    const noFlip = `${b[0]}${b[1]}:${b[2]}ff:fe${b[3]}:${b[4]}${b[5]}`.replace(/^0+/, '');
    add(`Jaki identyfikator interfejsu EUI-64 powstanie z MAC ${mac}?`, id, [noFlip, `${b[0]}${b[1]}:${b[2]}${b[3]}:${b[4]}${b[5]}:fffe`, id.replace('ff:fe', 'fe:ff').replace('fffe', 'feff'), `${b[0]}${b[1]}:${b[2]}fe:ff${b[3]}:${b[4]}${b[5]}`.replace(/^0+/, '')].filter((x) => x !== id), 'ff:fe w środek, odwróć bit U/L (0x02) pierwszego bajtu.');
    const ll = linkLocal(mac);
    add(`Jaki adres link-local (EUI-64) dostanie interfejs z MAC ${mac}?`, ll, [`fe80::${noFlip}`, `fd00::${id}`, `fe80:0:0:1:${id}`, `2001:db8::${id}`], 'fe80::/64 + IID.');
  }

  // 5) Solicited-node i MAC multicast (8)
  for (const a of ['2001:db8::1:2:3abc:def0', 'fe80::21a:2bff:fe3c:4d5e', 'fd5a:3c91:7e04:12::20', '2001:db8:abcd:12::10']) {
    const sn = solicitedNode(a);
    const tail = sn.split(':').slice(-2).join(':');
    add(`Jaki jest adres solicited-node multicast dla ${a}?`, sn, [`ff02::1:${tail}`.replace('::1:ff', '::ff'), `ff02::2:${tail}`, `ff01::1:${tail}`, `ff02::1`].filter((x) => x !== sn), 'ff02::1:ff + ostatnie 24 bity.');
    const mm = multicastMac(sn);
    add(`Na jaki adres MAC zostanie wysłana ramka do grupy ${sn}?`, mm, [mm.replace('33:33', '01:00:5e'), 'ff:ff:ff:ff:ff:ff', mm.replace('33:33:ff', '33:33:00'), mm.replace(/^33:33/, '33:00')], '33:33 + ostatnie 32 bity.');
  }

  // 6) Podsieci (6)
  for (const [base, p, np, n] of [['2001:db8:abcd::', 48, 64, 18], ['2001:db8:abcd::', 48, 64, 4096], ['2001:db8:abcd::', 48, 56, 5], ['2001:db8:abcd:1200::', 56, 64, 255], ['2001:db8::', 32, 48, 10], ['2001:db8:abcd::', 48, 52, 3]]) {
    const r = sub6(base, p, np, n);
    add(`Podsieć /${np} numer ${n} w ${base}/${p}?`, r, [sub6(base, p, np, n + 1), sub6(base, p, np, Number('0x' + n) > 2 ** (np - p) - 1 ? n - 1 : Number('0x' + n)), sub6(base, p, np, Math.max(n - 1, 0)), `${base}${n}/${np}`].filter((x, i, arr) => x !== r && arr.indexOf(x) === i), `${n} = 0x${n.toString(16)}.`);
  }

  // 7) Merytoryczne (50)
  const facts = [
    ['Ile bitów ma adres IPv6?', '128', ['64', '32', '256'], '8 grup po 16 bitów.'],
    ['Ile razy można użyć :: w jednym adresie?', 'Najwyżej raz', ['Dowolnie wiele', 'Dwa razy', 'Wcale'], 'Inaczej niejednoznaczny.'],
    ['Jak zapisać adres IPv6 z portem w URL?', 'http://[2001:db8::1]:8080/', ['http://2001:db8::1:8080/', 'http://2001:db8::1/8080', 'http://(2001:db8::1):8080/'], 'Nawiasy kwadratowe.'],
    ['Jaki prefiks ma typowa podsieć LAN w IPv6?', '/64', ['/48', '/56', '/120'], 'Wymaga go SLAAC.'],
    ['Jaki prefiks dostaje typowo firma/lokalizacja?', '/48', ['/64', '/128', '/16'], '65 536 podsieci /64.'],
    ['Jaki prefiks dostaje często klient domowy?', '/56', ['/64', '/32', '/128'], '256 podsieci /64.'],
    ['Jak nazywa się odpowiednik adresów prywatnych w IPv6?', 'ULA (fc00::/7, w praktyce fd00::/8)', ['link-local', 'GUA', 'site-local fec0::/10 (aktualny)'], 'RFC 4193.'],
    ['Czym jest adres link-local w IPv6?', 'Automatycznym adresem fe80::/10 ważnym tylko na łączu', ['Adresem globalnym', 'Adresem loopback', 'Adresem multicast'], 'Ma go każdy interfejs.'],
    ['Co oznacza zapis fe80::1%eth0?', 'Adres link-local z identyfikatorem strefy (interfejsu)', ['Adres z portem', 'Adres z maską', 'Błędny zapis'], 'Scope id.'],
    ['Jaki adres ma grupa wszystkich węzłów na łączu?', 'ff02::1', ['ff02::2', 'ff05::1', '::1'], 'All-nodes.'],
    ['Jaki adres ma grupa wszystkich routerów na łączu?', 'ff02::2', ['ff02::1', 'ff02::fb', 'fe80::1'], 'All-routers.'],
    ['Czym zastąpiono ARP w IPv6?', 'Neighbor Discovery (ICMPv6 NS/NA)', ['RARP', 'DHCPv6', 'LLDP'], 'RFC 4861.'],
    ['Czy IPv6 ma broadcast?', 'Nie — używa multicastu', ['Tak, ff:ff:ff:ff', 'Tak, ::ffff', 'Tylko w link-local'], 'Solicited-node zamiast broadcastu.'],
    ['Do czego służy solicited-node multicast?', 'Do wysyłania Neighbor Solicitation tylko do hostów z pasującą końcówką adresu', ['Do routingu', 'Do DNS', 'Do NAT64'], 'Mniej przerywanych hostów.'],
    ['Jaki prefiks ma MAC dla multicastu IPv6?', '33:33', ['01:00:5e', 'ff:ff', '00:00:5e'], '01:00:5e to IPv4.'],
    ['Co to jest SLAAC?', 'Automatyczna konfiguracja adresu z prefiksu z Router Advertisement', ['Serwer DHCP dla IPv6', 'Protokół routingu', 'Typ NAT'], 'Bez serwera.'],
    ['Skąd host IPv6 zwykle zna bramę domyślną?', 'Z Router Advertisement (adres link-local routera)', ['Z DHCPv6', 'Z DNS', 'Z ARP'], 'DHCPv6 nie podaje bramy.'],
    ['Co oznacza flaga M w Router Advertisement?', 'Adresy przydziela DHCPv6 (stateful)', ['Multicast włączony', 'MTU podane', 'Mobilny router'], 'Managed.'],
    ['Co oznacza flaga O w Router Advertisement?', 'Inne parametry (np. DNS) z DHCPv6 stateless', ['Wyłącz IPv6', 'Odbierz trasę', 'Ogranicz MTU'], 'Other config.'],
    ['Dlaczego EUI-64 jest problemem dla prywatności?', 'Ujawnia MAC i pozwala śledzić urządzenie między sieciami', ['Bo jest za krótki', 'Bo zmienia się co minutę', 'Bo nie działa w Wi-Fi'], 'RFC 7217/8981.'],
    ['Czym są adresy tymczasowe IPv6 (RFC 8981)?', 'Losowymi, okresowo zmienianymi adresami do połączeń wychodzących', ['Adresami link-local', 'Adresami z DHCPv6', 'Adresami ULA'], 'Prywatność.'],
    ['Czym są stabilne adresy prywatne (RFC 7217)?', 'IID wyliczony z hasha prefiksu i sekretu — stały w sieci, różny między sieciami', ['Adresy z MAC', 'Adresy losowe co minutę', 'Adresy anycast'], 'Brak śledzenia.'],
    ['Jaka jest długość nagłówka IPv6?', 'Stałe 40 bajtów', ['20–60 bajtów', '32 bajty', '64 bajty'], 'Opcje w extension headers.'],
    ['Którego pola NIE ma w nagłówku IPv6?', 'Header Checksum', ['Hop Limit', 'Flow Label', 'Next Header'], 'Ani pól fragmentacji.'],
    ['Co oznacza pole Next Header?', 'Typ następnego nagłówka (rozszerzającego lub warstwy 4)', ['Adres następnego routera', 'Liczbę skoków', 'Długość nagłówka'], 'Łańcuch nagłówków.'],
    ['Jaka wartość Next Header oznacza ICMPv6?', '58', ['1', '6', '17'], 'TCP 6, UDP 17.'],
    ['Jaka wartość Next Header oznacza nagłówek Fragment?', '44', ['43', '0', '60'], 'Fragmentuje tylko nadawca.'],
    ['Co jest odpowiednikiem TTL w IPv6?', 'Hop Limit', ['Flow Label', 'Traffic Class', 'Payload Length'], 'Licznik skoków.'],
    ['Do czego służy Flow Label?', 'Do oznaczania przepływu, np. dla haszowania ECMP', ['Do szyfrowania', 'Do fragmentacji', 'Do QoS z gwarancją'], '20 bitów.'],
    ['Dlaczego Routing Header typu 0 został wycofany?', 'Umożliwiał ataki wzmacniające i omijanie filtrów', ['Był za wolny', 'Nie działał z TCP', 'Wymagał NAT'], 'RFC 5095.'],
    ['Jakie ryzyko niosą długie łańcuchy extension headers?', 'Utrudniają firewallom dotarcie do nagłówka TCP/UDP', ['Zwiększają TTL', 'Zmieniają adres źródłowy', 'Wyłączają ND'], 'Często odrzucane (RFC 7872).'],
    ['Czym jest atak rogue RA?', 'Ogłaszaniem się fałszywym routerem przez Router Advertisement', ['Floodem DNS', 'Spoofingiem MAC', 'Atakiem na TLS'], 'MITM w IPv6.'],
    ['Jaki mechanizm przełącznika chroni przed rogue RA?', 'RA Guard', ['Port security', 'STP BPDU Guard', 'LACP'], 'RA tylko z portów routerów.'],
    ['Jak wyłączyć akceptowanie RA na serwerze Linux ze statycznym IPv6?', 'net.ipv6.conf.<if>.accept_ra=0', ['net.ipv6.conf.all.disable_ipv6=1', 'ip -6 route flush', 'sysctl net.ipv4.ip_forward=0'], 'Stała konfiguracja.'],
    ['Dlaczego limit zapytań per pojedynczy adres IPv6 jest słaby?', 'Klient może używać wielu adresów ze swojej /64', ['Bo IPv6 nie ma portów', 'Bo adresy są szyfrowane', 'Bo IPv6 nie przechodzi przez proxy'], 'Limituj per /64.'],
    ['Jaki typ kolumny PostgreSQL najlepiej przechowuje adresy IPv4/IPv6?', 'inet', ['VARCHAR(15)', 'INTEGER', 'BYTEA(4)'], 'Zapytania po prefiksie.'],
    ['Ile znaków może mieć tekstowy adres IPv6 (z IPv4-mapped)?', 'Do 45', ['Do 15', 'Do 32', 'Do 128'], 'VARCHAR(45).'],
    ['Co zwraca InetAddress.getByName("::ffff:192.0.2.7") w Javie?', 'Inet4Address 192.0.2.7', ['Inet6Address bez zmian', 'Wyjątek', 'null'], 'Java konwertuje IPv4-mapped.'],
    ['Jak w Javie bezpiecznie zbudować URI z adresem IPv6?', 'Konstruktorem URI(scheme, userInfo, host, port, …), który doda nawiasy', ['Łącząc tekst "http://" + host + ":" + port', 'InetAddress.toURI()', 'Nie da się'], 'Albo ręcznie [ ].'],
    ['Jak ustawić Spring Boot, by słuchał na IPv4 i IPv6?', 'server.address=:: (lub brak ustawienia — domyślnie wszystkie)', ['server.address=0.0.0.0', 'server.ipv6=true', 'server.address=127.0.0.1'], '0.0.0.0 to tylko IPv4.'],
    ['Jak włączyć IPv6 dla sieci Dockera?', 'docker network create --ipv6 --subnet <prefiks>', ['docker run --ipv6', 'sysctl docker.ipv6=1', 'Domyślnie zawsze włączone'], 'Lub daemon.json.'],
    ['Jak AWS przydziela IPv6 do VPC?', 'Blok /56, podsieci /64', ['Blok /64, podsieci /80', 'Blok /48, podsieci /56', 'Tylko adresy ULA'], 'Egress-only IGW zamiast NAT.'],
    ['Czym jest egress-only Internet Gateway w AWS?', 'Bramą pozwalającą IPv6 tylko na ruch wychodzący', ['NAT dla IPv4', 'Bramą VPN', 'Load balancerem'], 'Odpowiednik NAT bez translacji.'],
    ['Jaki prefiks zaleca się na łączach router–router w IPv6?', '/127', ['/64', '/48', '/126 obowiązkowo'], 'RFC 6164.'],
    ['Czym jest DAD w IPv6?', 'Sprawdzeniem przez NS, czy adres nie jest już używany', ['Protokołem DNS', 'Typem multicastu', 'Atakiem'], 'Duplicate Address Detection.'],
    ['Jak polecenie ping6 do wszystkich węzłów na łączu?', 'ping -6 ff02::1%eth0', ['ping -6 ::1', 'ping -6 ff02::2', 'ping 255.255.255.255'], 'Odpowiadają wszystkie węzły.'],
    ['Które polecenie pokazuje tablicę sąsiadów IPv6 w Linuksie?', 'ip -6 neigh', ['arp -a', 'ip -6 route', 'ss -6'], 'Odpowiednik ARP.'],
    ['Co oznacza „scope global temporary” w ip -6 addr?', 'Adres globalny tymczasowy (prywatność) używany do połączeń wychodzących', ['Adres link-local', 'Adres wygasły', 'Adres z DHCPv4'], 'RFC 8981.'],
    ['Dlaczego wyłączenie IPv6 nie chroni przed ruchem IPv6?', 'Tunele (Teredo, 6to4) i ruch przez aplikacje mogą go przenosić w IPv4', ['Bo IPv6 włącza się sam co godzinę', 'Bo IPv4 nie ma firewalla', 'Chroni w pełni'], 'Kontroluj i monitoruj.'],
    ['Czy host z adresem GUA bez NAT jest osiągalny z Internetu?', 'Tak, jeśli firewall na to pozwala — dlatego potrzebny stanowy firewall', ['Nie, IPv6 blokuje ruch przychodzący', 'Tylko przez port 80', 'Tylko w IPv4'], 'Domyślny DROP dla przychodzącego.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
