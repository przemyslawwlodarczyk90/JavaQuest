// Quiz (100 pytan) do lekcji _ns_03/06_SpecialAndPrivateAddresses.
const { quizBuilder } = require('./lib.cjs');
const { classify4, classify6 } = require('./ns03_06_special_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111406);
  const CATS4 = ['prywatny (RFC 1918)', 'pętla zwrotna (loopback)', 'link-local (APIPA)', 'współdzielony (CGNAT)', 'dokumentacyjny (RFC 5737)', 'multicast', 'publiczny (globalny unicast)', 'zarezerwowany (dawna klasa E)', 'testy wydajności (benchmark)', '„ta sieć” (0.0.0.0/8)', 'ograniczony broadcast'];
  const cat4 = (ip) => {
    const n = classify4(ip).name;
    if (n === 'prywatny') return CATS4[0];
    if (n.startsWith('pętla')) return CATS4[1];
    if (n.startsWith('link-local')) return CATS4[2];
    if (n.includes('CGNAT')) return CATS4[3];
    if (n.startsWith('dokumentacyjny')) return CATS4[4];
    if (n === 'multicast') return CATS4[5];
    if (n.startsWith('publiczny')) return CATS4[6];
    if (n.startsWith('zarezerwowany')) return CATS4[7];
    if (n.startsWith('testy')) return CATS4[8];
    if (n.startsWith('„ta sieć”')) return CATS4[9];
    if (n.includes('broadcast')) return CATS4[10];
    throw new Error('kategoria ' + n);
  };
  const others = (right, i) => CATS4.filter((c) => c !== right).filter((_, k) => (k + i) % 3 === 0).slice(0, 3).concat(CATS4.filter((c) => c !== right)).filter((x, k, a) => a.indexOf(x) === k);

  // 1) Klasyfikacja IPv4 (44)
  const ips = ['10.0.0.1', '10.255.255.254', '172.16.0.1', '172.31.255.255', '172.15.255.255', '172.32.0.1', '192.168.0.1', '192.168.255.1', '192.169.0.1', '127.0.0.1',
    '127.0.0.53', '127.255.255.254', '169.254.169.254', '169.254.0.5', '169.255.0.1', '100.64.0.1', '100.127.255.254', '100.63.255.255', '100.128.0.1', '192.0.2.1',
    '198.51.100.200', '203.0.113.9', '203.0.114.1', '224.0.0.5', '224.0.0.251', '239.255.255.250', '240.0.0.1', '250.1.2.3', '198.18.0.1', '198.19.255.255',
    '198.20.0.1', '0.0.0.0', '0.1.2.3', '255.255.255.255', '8.8.8.8', '1.1.1.1', '9.9.9.9', '11.0.0.1', '126.255.255.255', '128.0.0.1',
    '191.255.255.255', '223.255.255.255', '172.20.10.2', '192.168.1.1'];
  ips.forEach((ip, i) => add(`Do jakiej kategorii należy adres ${ip}?`, cat4(ip), others(cat4(ip), i), `${classify4(ip).range !== '—' ? classify4(ip).range + ' — ' : ''}${classify4(ip).name}.`));

  // 2) Klasyfikacja IPv6 (12)
  const CATS6 = { '::1/128': 'pętla zwrotna', 'fe80::/10': 'link-local', 'fc00::/7': 'unikalny lokalny (ULA)', '2001:db8::/32': 'dokumentacyjny', '2000::/3': 'globalny unicast', 'ff00::/8': 'multicast', '::ffff:0:0/96': 'IPv4 mapowany', '::/128': 'nieokreślony (jak 0.0.0.0)', '64:ff9b::/96': 'NAT64' };
  const ALL6 = Object.values(CATS6);
  for (const ip of ['::1', 'fe80::1', 'fe80::a00:27ff:fe4e:66a1', 'fd00::1', 'fdab:cdef:1234::7', '2001:db8::1', '2a00:1450:4001:80b::200e', 'ff02::1', 'ff02::fb', '::ffff:192.168.1.1', '::', '64:ff9b::808:808']) {
    const right = CATS6[classify6(ip).range];
    add(`Jakim typem adresu IPv6 jest ${ip}?`, right, ALL6.filter((x) => x !== right), `${classify6(ip).range} — ${classify6(ip).name}.`);
  }

  // 3) Merytoryczne (44)
  const facts = [
    ['Który zakres prywatny obejmuje adresy od 172.16.0.0 do 172.31.255.255?', '172.16.0.0/12', ['172.16.0.0/16', '172.0.0.0/8', '172.16.0.0/20'], 'RFC 1918.'],
    ['Ile adresów obejmuje blok loopback IPv4?', 'Cały 127.0.0.0/8 (ok. 16,7 mln)', ['Tylko 127.0.0.1', '127.0.0.0/24', '127.0.0.0/16'], 'Np. 127.0.0.53 dla resolvera.'],
    ['Co oznacza adres 169.254.169.254 w chmurze?', 'Usługę metadanych instancji (IMDS)', ['Domyślną bramę VPC', 'Serwer DNS Google', 'Adres NAT Gateway'], 'AWS, GCP, Azure.'],
    ['Co dostaje host, który nie otrzymał odpowiedzi DHCP (Windows/macOS)?', 'Adres link-local z 169.254.0.0/16 (APIPA)', ['Adres 127.0.0.1', 'Adres 0.0.0.0', 'Adres z 10.0.0.0/8'], 'RFC 3927.'],
    ['Do czego służy 100.64.0.0/10?', 'Przestrzeń współdzielona operatorów dla CGNAT', ['Adresy prywatne w domu', 'Multicast', 'Dokumentacja'], 'RFC 6598.'],
    ['Co oznacza bind na 0.0.0.0?', 'Nasłuch na wszystkich interfejsach IPv4', ['Brak nasłuchu', 'Nasłuch tylko na loopbacku', 'Nasłuch tylko na pierwszym interfejsie'], 'INADDR_ANY.'],
    ['Na jakim adresie domyślnie nasłuchuje Spring Boot (Tomcat)?', 'Na wszystkich interfejsach (0.0.0.0 / ::)', ['Tylko 127.0.0.1', 'Tylko na adresie z DHCP', 'Na 169.254.0.1'], 'Zmienisz server.address.'],
    ['Dlaczego aplikacja w kontenerze z server.address=127.0.0.1 jest nieosiągalna przez opublikowany port?', 'Słucha na loopbacku kontenera, a ruch z publikacji trafia na interfejs eth0 kontenera', ['Bo Docker blokuje port 8080', 'Bo kontener nie ma adresu IP', 'Bo 127.0.0.1 jest zabronione w Linuksie'], 'Bind 0.0.0.0 w kontenerze.'],
    ['Jak opublikować port kontenera tylko dla lokalnego hosta?', 'docker run -p 127.0.0.1:8080:8080', ['docker run -p 8080:8080', 'docker run --network none', 'docker run -p 0.0.0.0:8080:8080'], 'Bez ekspozycji w LAN.'],
    ['Dlaczego porty opublikowane przez Dockera mogą ominąć UFW?', 'Docker dodaje własne reguły iptables (DNAT/FORWARD) przetwarzane przed regułami UFW', ['Bo UFW nie obsługuje TCP', 'Bo Docker używa IPv6', 'Bo kontenery nie mają portów'], 'Używaj DOCKER-USER lub bind 127.0.0.1.'],
    ['Które polecenie pokaże, na jakim adresie nasłuchuje usługa?', 'ss -ltnp', ['ip route', 'dig', 'arp -a'], 'LISTEN 127.0.0.1:8080 vs 0.0.0.0:8080.'],
    ['Czym jest SSRF?', 'Zmuszeniem serwera do wykonania żądania pod adres wskazany przez atakującego', ['Fałszowaniem adresu źródłowego', 'Atakiem na DNS klienta', 'Przechwyceniem sesji w przeglądarce'], 'Server-Side Request Forgery.'],
    ['Co zwraca IMDSv1 na żądanie GET /latest/meta-data/iam/security-credentials/<rola>?', 'Tymczasowe klucze dostępu roli IAM instancji', ['Hasło roota', 'Klucz prywatny TLS', 'Nic — wymaga logowania'], 'Cel SSRF.'],
    ['Czym IMDSv2 różni się od IMDSv1?', 'Wymaga tokenu pobranego wcześniej żądaniem PUT z nagłówkiem', ['Działa na porcie 443', 'Wymaga hasła', 'Jest dostępny tylko z Internetu'], 'HttpTokens=required.'],
    ['Do czego służy HttpPutResponseHopLimit=1 w IMDSv2?', 'Odpowiedź z tokenem nie przeżyje dodatkowego skoku, np. z kontenera za bridge', ['Ogranicza liczbę żądań na sekundę', 'Wyłącza IPv6', 'Szyfruje metadane'], 'TTL = 1.'],
    ['Jaki nagłówek wymaga serwer metadanych GCP?', 'Metadata-Flavor: Google', ['X-Forwarded-For', 'Authorization: Basic', 'Host: metadata'], 'Azure: Metadata: true.'],
    ['Dlaczego walidacja SSRF powinna sprawdzać adres PO rozwiązaniu DNS?', 'Bo nazwa może wskazywać na adres wewnętrzny (np. 127.0.0.1)', ['Bo DNS szyfruje adres', 'Bo IP jest wolniejsze', 'Bo nazwy są zawsze publiczne'], 'Plus łączenie z tym samym adresem.'],
    ['Czym jest DNS rebinding?', 'Zmianą odpowiedzi DNS między walidacją a połączeniem, by trafić w adres wewnętrzny', ['Zmianą serwera DNS w systemie', 'Atakiem na DHCP', 'Przekierowaniem HTTP'], 'Krótki TTL.'],
    ['Który zapis NIE wskazuje na 127.0.0.1 w typowych parserach?', '127.0.1.0.1', ['127.1', '2130706433', '0x7f000001'], 'Alternatywne zapisy IPv4.'],
    ['Dlaczego automatyczne przekierowania w kliencie HTTP są groźne przy SSRF?', 'Serwer zewnętrzny może przekierować żądanie na adres wewnętrzny', ['Bo spowalniają żądania', 'Bo zmieniają metodę na POST', 'Bo wymagają TLS'], 'Waliduj po każdym przekierowaniu.'],
    ['Która metoda InetAddress wykrywa adresy RFC 1918?', 'isSiteLocalAddress()', ['isLoopbackAddress()', 'isLinkLocalAddress()', 'isAnyLocalAddress()'], 'Nie obejmuje CGNAT.'],
    ['Która metoda InetAddress wykrywa 169.254.0.0/16?', 'isLinkLocalAddress()', ['isSiteLocalAddress()', 'isMulticastAddress()', 'isAnyLocalAddress()'], 'W tym 169.254.169.254.'],
    ['Których zakresów NIE wykrywają standardowe metody isXxx w InetAddress?', 'CGNAT 100.64.0.0/10 i ULA fc00::/7', ['10.0.0.0/8', '127.0.0.0/8', '169.254.0.0/16'], 'Trzeba sprawdzić ręcznie.'],
    ['Jak serwer dual-stack w Javie często raportuje klienta IPv4?', 'Jako adres mapowany ::ffff:a.b.c.d', ['Jako 0.0.0.0', 'Jako fe80::', 'Jako 127.0.0.1'], 'Porównuj InetAddress, nie tekst.'],
    ['Jaki problem powoduje CGNAT dla blokad po IP?', 'Jedna blokada odcina wielu niewinnych użytkowników za tym samym adresem', ['Blokady przestają działać dla IPv6', 'Adres zmienia się co sekundę', 'Serwer nie widzi żadnego adresu'], 'Limituj per konto.'],
    ['Co musi zawierać log, aby operator mógł zidentyfikować klienta za CGNAT?', 'Adres IP, port źródłowy i dokładny czas', ['Tylko adres IP', 'Tylko User-Agent', 'Tylko adres MAC'], 'Synchronizacja NTP.'],
    ['Które zakresy IPv4 są przeznaczone do dokumentacji?', '192.0.2.0/24, 198.51.100.0/24, 203.0.113.0/24', ['10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16', '127.0.0.0/8 i 169.254.0.0/16', '198.18.0.0/15 i 100.64.0.0/10'], 'RFC 5737.'],
    ['Jaki prefiks IPv6 jest dokumentacyjny?', '2001:db8::/32', ['fe80::/10', 'fd00::/8', '2002::/16'], 'RFC 3849.'],
    ['Czym jest adres ULA w IPv6?', 'Odpowiednikiem adresów prywatnych: fc00::/7, w praktyce fd00::/8', ['Adresem link-local', 'Adresem multicast', 'Adresem publicznym'], 'RFC 4193.'],
    ['Dlaczego Global ID w ULA powinien być losowy?', 'Aby uniknąć kolizji przy łączeniu sieci różnych organizacji', ['Bo routery tego wymagają', 'Aby adres był publiczny', 'Aby skrócić zapis'], '40 losowych bitów.'],
    ['Który adres IPv6 ma każdy interfejs z włączonym IPv6, nawet bez routera?', 'Adres link-local fe80::/10', ['Adres ULA', 'Adres GUA', '::1 na każdym interfejsie'], 'Potrzebny do NDP.'],
    ['Czym jest bogon?', 'Adres, który nie powinien pojawić się jako źródło w Internecie', ['Złośliwy pakiet DNS', 'Typ ataku DDoS', 'Nieużywany port'], 'Filtrowany na brzegu.'],
    ['Co wskazuje pakiet przychodzący z Internetu ze źródłem 10.1.2.3?', 'Spoofing lub błędną konfigurację — powinien być odrzucony', ['Normalny ruch klienta', 'Ruch z CGNAT', 'Ruch multicast'], 'Bogon filtering.'],
    ['Dlaczego Actuator najlepiej wystawiać na osobnym adresie/porcie?', 'Aby endpointy diagnostyczne nie były dostępne z publicznej sieci', ['Bo Actuator nie działa na 8080', 'Bo wymaga IPv6', 'Bo tak jest szybciej'], 'management.server.address.'],
    ['Jak inny kontener w tej samej sieci Dockera łączy się z bazą bez publikacji portu?', 'Po nazwie serwisu przez sieć Dockera', ['Przez 127.0.0.1', 'Nie da się bez -p', 'Przez 169.254.169.254'], 'Wbudowany DNS Dockera.'],
    ['Jak z kontenera połączyć się z usługą działającą na hoście (Docker Desktop)?', 'Przez host.docker.internal', ['Przez 127.0.0.1', 'Przez 0.0.0.0', 'Przez 224.0.0.1'], 'Loopback kontenera ≠ hosta.'],
    ['Co oznacza 0.0.0.0 jako adres źródłowy?', 'Host jeszcze nie ma adresu (np. DHCP DISCOVER)', ['Ruch z loopbacku', 'Ruch multicast', 'Ruch z Internetu'], 'Przed uzyskaniem dzierżawy.'],
    ['Co oznacza trasa 0.0.0.0/0?', 'Trasę domyślną pasującą do każdego adresu', ['Brak trasy', 'Trasę do loopbacku', 'Trasę do sieci prywatnych'], 'Prefiks długości 0.'],
    ['Czy pakiet na 255.255.255.255 przejdzie przez router?', 'Nie — ograniczony broadcast zostaje w lokalnej sieci', ['Tak, zawsze', 'Tak, jeśli ma TTL > 1', 'Tylko w IPv6'], 'Directed broadcast to inna sprawa.'],
    ['Dlaczego w testach integracyjnych używa się example.com i 192.0.2.0/24?', 'Bo nie należą do nikogo i testy nie uderzą w prawdziwe usługi', ['Bo są szybsze', 'Bo nie wymagają DNS', 'Bo są szyfrowane'], 'RFC 2606, RFC 5737.'],
    ['Który zakres bywa używany jako dodatkowy CIDR podów w EKS?', '100.64.0.0/10', ['127.0.0.0/8', '224.0.0.0/4', '192.0.2.0/24'], 'Custom networking.'],
    ['Do czego służy 224.0.0.251?', 'mDNS (multicast DNS, np. Bonjour)', ['OSPF', 'DHCP', 'NTP'], 'IPv6: ff02::fb.'],
    ['Dlaczego reguła „blokuj 172.*” jest błędna?', 'Obejmuje też publiczne adresy spoza 172.16.0.0/12', ['Bo 172 jest zarezerwowane', 'Bo nie obejmuje 172.20.x.x', 'Bo IPv6 jej nie rozumie'], 'Prywatne to tylko 172.16–31.'],
    ['Co powinno być pierwszą linią obrony przed SSRF, jeśli to możliwe?', 'Allow-lista dozwolonych hostów docelowych', ['Blokada słowa localhost', 'Wyłączenie HTTPS', 'Większy timeout'], 'Deny-lista jest uzupełnieniem.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
