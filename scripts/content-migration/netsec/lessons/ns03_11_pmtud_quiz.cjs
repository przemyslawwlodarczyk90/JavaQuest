// Quiz (100 pytan) do lekcji _ns_03/11_FragmentationAndPmtud.
const { quizBuilder } = require('./lib.cjs');
const { fragment, OVERHEAD, innerMtu, mss4, mss6, pathMtu } = require('./ns03_11_pmtud_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111411);

  // 1) Fragmentacja (24): liczba fragmentow, offset drugiego, dane ostatniego
  const cases = [[4000, 1500], [3000, 1500], [2000, 1400], [1600, 1500], [5000, 1492], [3000, 576], [6000, 1500], [2500, 1280]];
  for (const [t, m] of cases) {
    const f = fragment(t, m);
    add(`Na ile fragmentów zostanie podzielony datagram IPv4 o Total Length ${t} B przy MTU ${m} (DF=0)?`, String(f.length), [String(f.length + 1), String(Math.max(f.length - 1, 1) === f.length ? f.length + 2 : Math.max(f.length - 1, 1)), String(Math.ceil(t / m)), String(f.length * 2)], `Dane na fragment: ${f[0].data} B.`);
    if (f.length > 1) add(`Jaki Fragment Offset (pole nagłówka) ma DRUGI fragment datagramu ${t} B przy MTU ${m}?`, String(f[1].offset), [String(f[1].offsetBytes), String(m - 20), String(f[1].offset + 1), String(Math.round((m - 20) / 8)), String(f[1].offset - 1), String(f[1].offset * 2), String(m)], `${f[0].data} B / 8 = ${f[1].offset}.`);
    add(`Ile bajtów danych niesie OSTATNI fragment datagramu ${t} B przy MTU ${m}?`, String(f[f.length - 1].data), [String(f[f.length - 1].total), String(f[0].data), String(t - 20 - (f.length - 1) * (m - 20)), String(f[f.length - 1].data + 8)], `${t - 20} − ${(f.length - 1)} × ${f[0].data}.`);
  }

  // 2) Narzuty i MSS (16)
  for (const [n, o] of OVERHEAD) {
    const im = innerMtu(1500, o);
    add(`Jakie MTU wewnątrz tunelu przy enkapsulacji ${n} na łączu 1500?`, String(im), [String(1500), String(im - 20), String(1500 + o), String(im + 8)], `1500 − ${o}.`);
    add(`Jakie MSS (TCP/IPv4) ogłosi host w tunelu ${n} (MTU ${im})?`, String(mss4(im)), [String(im - 20), String(mss4(1500)), String(mss6(im)), String(im)], `${im} − 40.`);
  }

  // 3) PMTU (6)
  for (const links of [[1500, 1500, 1420, 1500], [9001, 1500, 9001], [1500, 1492, 1450], [1500, 1280, 1500], [9000, 1500, 9000], [1500, 1400, 1476, 1500]]) {
    const p = pathMtu(links);
    add(`Łącza na ścieżce mają MTU ${links.join(', ')}. Jakie jest Path MTU?`, String(p), [String(Math.max(...links)), String(links[0]), String(Math.round(links.reduce((a, b) => a + b, 0) / links.length)), String(p - 28), String(p - 40), '1500'].filter((x) => x !== String(p)), 'Minimum na ścieżce.');
  }

  // 4) ping -s (6)
  for (const [mtu, v6] of [[1420, false], [1400, false], [1450, true], [1280, true], [9000, false], [1492, false]]) {
    const s = mtu - (v6 ? 48 : 28);
    add(`Jaki największy rozmiar danych (-s) przejdzie w ping -M do przez ścieżkę o PMTU ${mtu} (${v6 ? 'IPv6' : 'IPv4'})?`, String(s), [String(mtu), String(mtu - 20), String(mtu - (v6 ? 28 : 48)), String(mtu - 40)].filter((x) => x !== String(s)), `${mtu} − ${v6 ? 48 : 28}.`);
  }

  // 5) Merytoryczne (48)
  const facts = [
    ['Co oznacza flaga DF?', 'Zakaz fragmentacji pakietu', ['Ostatni fragment', 'Pakiet uszkodzony', 'Priorytet QoS'], 'Don’t Fragment.'],
    ['Kto składa fragmenty IPv4?', 'Odbiorca (host docelowy)', ['Każdy router po drodze', 'Pierwszy router', 'Nadawca'], 'Routery nie składają.'],
    ['W jakich jednostkach jest Fragment Offset?', '8 bajtów', ['1 bajt', '4 bajty', '16 bajtów'], 'Dlatego dane to wielokrotność 8.'],
    ['Co robi router z pakietem większym niż MTU łącza, gdy DF=1?', 'Odrzuca go i odsyła ICMP 3/4 z MTU następnego łącza', ['Fragmentuje go', 'Przekazuje bez zmian', 'Obniża TTL'], 'PMTUD.'],
    ['Jaki komunikat ICMPv6 odpowiada fragmentation needed?', 'Packet Too Big (typ 2)', ['Time Exceeded', 'Neighbor Advertisement', 'Echo Reply'], 'IPv6.'],
    ['Czy routery IPv6 fragmentują pakiety?', 'Nie — może to zrobić tylko nadawca', ['Tak, zawsze', 'Tak, gdy DF=0', 'Tylko przy MTU < 1280'], 'Nagłówek Fragment.'],
    ['Jakie jest minimalne MTU wymagane przez IPv6?', '1280', ['576', '1500', '68'], 'Każde łącze IPv6.'],
    ['Czym jest Path MTU?', 'Najmniejszym MTU na całej ścieżce', ['MTU pierwszego łącza', 'Sumą MTU', 'MTU bramy domyślnej'], 'Minimum.'],
    ['Czym jest PMTUD black hole?', 'Sytuacją, gdy ICMP fragmentation needed jest blokowany i duże pakiety giną bez informacji', ['Pętlą routingu', 'Brakiem DNS', 'Przepełnieniem conntrack'], 'Ping OK, TLS wisi.'],
    ['Jaki jest klasyczny objaw PMTUD black hole?', 'Ping i handshake TCP działają, ale TLS/duże odpowiedzi wiszą', ['Brak adresu IP', 'Błąd certyfikatu', 'Natychmiastowe Connection refused'], 'Małe przechodzą.'],
    ['Jak obliczyć MSS dla IPv4?', 'MTU − 40', ['MTU − 20', 'MTU − 28', 'MTU − 60'], 'IP 20 + TCP 20.'],
    ['Jak obliczyć MSS dla IPv6?', 'MTU − 60', ['MTU − 40', 'MTU − 48', 'MTU − 20'], 'IPv6 40 + TCP 20.'],
    ['Jakie MSS ogłasza host z MTU 1500 (IPv4)?', '1460', ['1500', '1480', '1440'], '1500 − 40.'],
    ['Co robi MSS clamping?', 'Zmniejsza MSS w pakietach SYN przechodzących przez router', ['Zwiększa MTU', 'Blokuje ICMP', 'Fragmentuje TCP'], 'Działa bez ICMP.'],
    ['Dla jakiego protokołu działa MSS clamping?', 'Tylko TCP', ['UDP', 'QUIC', 'ICMP'], 'Opcja MSS jest w SYN.'],
    ['Jaka reguła iptables robi MSS clamping do PMTU?', '-t mangle -A FORWARD -p tcp --tcp-flags SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu', ['-t nat -A POSTROUTING -j MASQUERADE', '-A INPUT -p icmp -j DROP', '-t raw -A PREROUTING -j NOTRACK'], 'Tabela mangle.'],
    ['Jakie polecenie Cisco ustawia MSS na tunelu?', 'ip tcp adjust-mss 1360', ['ip mtu clamp 1360', 'tcp mss 1360 set', 'mss adjust tunnel'], 'Na interfejsie.'],
    ['Co pokazuje tracepath?', 'Kolejne skoki i Path MTU', ['Tylko DNS', 'Przepustowość', 'Otwarte porty'], 'pmtu na skoku.'],
    ['Gdzie Linux przechowuje wykryte PMTU?', 'W cache tras (ip route get → mtu)', ['W /etc/hosts', 'W tablicy ARP', 'W conntrack'], 'Wygasa po ~10 min.'],
    ['Jaki parametr Linuksa włącza PLPMTUD dla TCP?', 'net.ipv4.tcp_mtu_probing', ['net.ipv4.ip_forward', 'net.ipv4.tcp_ecn', 'net.core.rmem_max'], '1 = po wykryciu black hole.'],
    ['Czym jest PLPMTUD?', 'Wykrywaniem MTU przez warstwę transportową bez polegania na ICMP', ['Protokołem routingu', 'Typem VLAN', 'Formą NAT'], 'RFC 4821/8899.'],
    ['Od jakiego rozmiaru zaczyna QUIC?', '1200 bajtów', ['576', '1500', '9000'], 'Potem sonduje większe.'],
    ['Jaki domyślny MTU ma interfejs WireGuard w wg-quick?', '1420', ['1500', '1280', '9000'], 'Bezpieczny dla IPv6 zewnętrznego.'],
    ['Ile bajtów dodaje VXLAN?', '50', ['8', '20', '100'], 'Ethernet + IP + UDP + VXLAN.'],
    ['Jakie MTU ma typowo PPPoE?', '1492', ['1500', '1450', '1280'], '8 B nagłówka.'],
    ['Jakie MTU ma ruch w obrębie VPC w AWS?', '9001 (jumbo)', ['1500', '1280', '576'], 'Przez IGW 1500.'],
    ['Jakie MTU ma ruch przez Internet Gateway w AWS?', '1500', ['9001', '8500', '1280'], 'PMTUD potrzebny.'],
    ['Jak ustawić MTU sieci bridge w Dockerze?', '"mtu" w /etc/docker/daemon.json', ['docker run --mtu', 'sysctl docker.mtu', 'Nie da się'], 'Lub driver_opts w compose.'],
    ['Dlaczego MTU podów w CNI z VXLAN musi być mniejsze niż MTU węzła?', 'Bo enkapsulacja dodaje narzut 50 B', ['Bo pody nie obsługują 1500', 'Bo Kubernetes tego wymaga', 'Bo DNS się psuje'], 'Inaczej fragmentacja/black hole.'],
    ['Jaki objaw daje złe MTU w overlay Kubernetes?', 'Timeouty dużych żądań tylko między podami na różnych węzłach', ['Brak DNS w klastrze', 'Pody nie startują', 'Błędy RBAC'], 'W obrębie węzła OK.'],
    ['Co się stanie z datagramem, gdy zginie jeden fragment?', 'Odbiorca odrzuci cały datagram', ['Zostanie złożony bez brakującej części', 'Router dośle fragment', 'Nic'], 'IP nie retransmituje.'],
    ['Dlaczego fragmentacja utrudnia pracę firewalli?', 'Porty TCP/UDP są tylko w pierwszym fragmencie', ['Bo fragmenty są szyfrowane', 'Bo zmienia się adres', 'Bo brak TTL'], 'Składanie przed inspekcją.'],
    ['Czym był atak Teardrop?', 'Wysłaniem nakładających się fragmentów psujących składanie', ['Floodem SYN', 'Atakiem na DNS', 'Spoofingiem ARP'], 'Błędy reassembly.'],
    ['Czym był Ping of Death?', 'Fragmentami składającymi się w pakiet > 65 535 B', ['Pingiem bez odpowiedzi', 'Pingiem z TTL 0', 'Floodem ICMP'], 'Przepełnienie bufora.'],
    ['Czym jest atak tiny fragments?', 'Rozbiciem nagłówka TCP między fragmenty, by ominąć filtr portów', ['Wysyłaniem małych pingów', 'Zmniejszeniem MTU', 'Floodem UDP'], 'RFC 1858.'],
    ['Jak Suricata radzi sobie z fragmentacją?', 'Składa fragmenty (defrag) przed inspekcją', ['Ignoruje fragmenty', 'Blokuje cały ruch IP', 'Wyłącza TCP'], 'Polityki per system.'],
    ['Jaki rozmiar bufora EDNS0 zaleca DNS Flag Day 2020?', '1232 bajty', ['512 bajtów', '4096 bajtów', '65 535 bajtów'], 'Unikanie fragmentacji.'],
    ['Jak testować MTU w Windows?', 'ping -f -l <rozmiar>', ['ping -M do -s', 'tracert -m', 'netstat -m'], '-f = DF.'],
    ['Co oznacza komunikat Windows „Packet needs to be fragmented but DF set”?', 'Pakiet jest za duży dla lokalnego interfejsu lub ścieżki z DF', ['Host nie istnieje', 'Brak DNS', 'Port zamknięty'], 'Zmniejsz rozmiar.'],
    ['Dlaczego jumbo frames muszą być włączone na wszystkich urządzeniach w domenie L2?', 'Bo ramki większe niż MTU urządzenia są odrzucane bez ICMP', ['Bo przyspieszają DNS', 'Bo wymaga tego IPv6', 'Nie muszą'], 'Lekcja 02/13.'],
    ['Co pokazuje ss -ti dla połączenia?', 'Między innymi mss, pmtu i retransmisje', ['Tablicę ARP', 'Tablicę routingu', 'Reguły firewalla'], 'Diagnoza TCP.'],
    ['Jaki filtr Wiresharka pokaże ICMP fragmentation needed?', 'icmp.type == 3 && icmp.code == 4', ['icmp.type == 11', 'ip.ttl == 0', 'tcp.flags.syn == 1'], 'PMTUD.'],
    ['Jaki filtr Wiresharka pokaże fragmenty IPv4?', 'ip.flags.mf == 1 || ip.frag_offset > 0', ['ip.flags.df == 1', 'ip.len > 1500', 'icmp.type == 3'], 'Wszystkie fragmenty.'],
    ['Czy aplikacja Java może ustawić flagę DF dla połączeń TCP?', 'Nie w przenośny sposób — robi to system (PMTUD)', ['Tak, Socket.setDF(true)', 'Tak, przez IP_TOS', 'Tak, przez HttpClient'], 'Konfiguruj sieć, nie kod.'],
    ['Jak aplikacja UDP powinna unikać fragmentacji?', 'Trzymać datagramy ≤ ~1200–1400 B', ['Wysyłać 64 kB datagramy', 'Wyłączyć IPv6', 'Używać TTL 255'], 'Lub przejść na TCP/QUIC.'],
    ['Dlaczego MSS clamping powinien obejmować SYN w obu kierunkach?', 'Bo każda strona ogłasza w SYN/SYN-ACK swój MSS', ['Bo SYN-ACK nie ma opcji', 'Bo tylko serwer wysyła dane', 'Nie musi'], 'Upload i download.'],
    ['Co jest właściwą naprawą PMTUD black hole (a nie obejściem)?', 'Przepuszczenie ICMP 3/4 i Packet Too Big', ['Wyłączenie TLS', 'Zwiększenie MTU', 'Zmiana DNS'], 'Clamping to zabezpieczenie.'],
    ['Którą regułę AWS dodać przy problemach z MTU przez VPN?', 'ICMP typ 3 kod 4 w security group i NACL', ['TCP 22 z 0.0.0.0/0', 'UDP 53 do Internetu', 'ICMP Echo do wszystkich'], 'Fragmentation needed.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
