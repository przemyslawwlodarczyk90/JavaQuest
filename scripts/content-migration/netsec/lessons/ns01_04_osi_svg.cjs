// Ilustracje do lekcji _ns_01/04_OsiModelInPractice.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_04_osi_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

const LAYERS = [
  [7, 'Aplikacji', 'dane / wiadomość', 'HTTP, DNS, SMTP, SSH, DHCP, SNMP', 'reverse proxy, WAF, API gateway', C.vio, C.vioFill],
  [6, 'Prezentacji', 'dane', 'TLS*, kodowanie znaków, kompresja, JSON/JPEG', 'terminator TLS', C.vio, C.vioFill],
  [5, 'Sesji', 'dane', 'sesje RPC, NetBIOS, SOCKS, zarządzanie dialogiem', '—', C.vio, C.vioFill],
  [4, 'Transportowa', 'segment / datagram', 'TCP, UDP, QUIC*; porty', 'firewall stanowy, load balancer L4', C.ok, C.okFill],
  [3, 'Sieciowa', 'pakiet', 'IPv4, IPv6, ICMP, IPsec, OSPF', 'router, przełącznik L3', C.net, C.netFill],
  [2, 'Łącza danych', 'ramka', 'Ethernet (MAC), 802.11, ARP, VLAN 802.1Q, PPP', 'przełącznik, AP, karta NIC', C.host, C.hostFill],
  [1, 'Fizyczna', 'bity / symbole', 'skrętka, światłowód, radio; złącza, napięcia', 'hub, repeater, kabel', C.muted, '#ece7df'],
];

// 1. Stos OSI
{
  const W = 900, H = 520;
  let b = text(W / 2, 34, 'Model OSI: siedem warstw, PDU, protokoły i urządzenia', { size: 19, bold: true, anchor: 'middle' });
  const cols = [[30, 'Warstwa'], [200, 'PDU'], [380, 'Przykłady'], [680, 'Urządzenia / elementy']];
  cols.forEach(([x, t]) => { b += text(x + 8, 70, t, { bold: true, size: 13, fill: C.muted }); });
  LAYERS.forEach(([n, name, pdu, ex, dev, col, fill], i) => {
    const y = 82 + i * 56;
    b += box(30, y, 840, 48, fill, col, 8);
    b += text(46, y + 30, `${n}`, { bold: true, size: 20, fill: col });
    b += text(72, y + 30, name, { bold: true, size: 15, fill: col });
    b += text(208, y + 30, pdu, { size: 13 });
    b += text(388, y + 30, ex, { size: 13 });
    b += text(688, y + 30, dev, { size: 12, fill: C.muted });
  });
  b += text(30, 486, '* TLS zwykle przypisuje się warstwie 6 (szyfrowanie, reprezentacja), choć działa nad TCP; QUIC łączy transport z TLS 1.3 nad UDP.', { size: 12, fill: C.muted });
  b += text(30, 506, 'Warstwy 5–7 w praktyce realizuje jedna aplikacja (np. Spring Boot + biblioteka TLS); 1–4 — system operacyjny i sprzęt.', { size: 12, fill: C.muted });
  files['ns01-04-osi-stack.svg'] = svg(W, H, 'Siedem warstw modelu OSI z jednostkami danych, przykładowymi protokołami i urządzeniami', b);
}

// 2. Co czyta ktore urzadzenie
{
  const W = 900, H = 470;
  let b = text(W / 2, 34, 'Które nagłówki „czyta” które urządzenie?', { size: 19, bold: true, anchor: 'middle' });
  const parts = [['Ethernet', 120, C.host, C.hostFill], ['IP', 100, C.net, C.netFill], ['TCP', 100, C.ok, C.okFill], ['TLS', 100, C.vio, C.vioFill], ['HTTP + dane', 210, C.vio, '#f3eefb']];
  let x = 240; const starts = [];
  parts.forEach(([t, w, col, fill]) => { starts.push(x); b += box(x, 60, w, 42, fill, col, 4) + text(x + w / 2, 87, t, { anchor: 'middle', bold: true, fill: col }); x += w; });
  b += text(230, 87, 'ramka:', { anchor: 'end', size: 13, fill: C.muted });
  const end = (i) => starts[i] + parts[i][1];
  const rows = [
    ['Hub / repeater (L1)', 240, 240, 'tylko sygnał — nie rozumie żadnego nagłówka', C.muted],
    ['Przełącznik (L2)', 240, end(0), 'MAC docelowy → port; VLAN', C.host],
    ['Router (L3)', 240, end(1), 'adres IP → trasa; zmienia nagłówek Ethernet i TTL', C.net],
    ['Firewall stanowy / LB L4', 240, end(2), 'IP + porty + stan połączenia TCP', C.ok],
    ['Reverse proxy / WAF / LB L7', 240, end(4), 'kończy TLS, czyta HTTP: ścieżka, nagłówki, treść', C.vio],
  ];
  rows.forEach(([name, x1, x2, note, col], i) => {
    const y = 140 + i * 62;
    b += text(230, y + 6, name, { anchor: 'end', bold: true, size: 13, fill: col });
    if (x2 > x1) b += `<rect x="${x1}" y="${y - 10}" width="${x2 - x1}" height="20" rx="4" fill="${col}" opacity="0.25"/>` + line(x1, y, x2, y, { color: col, w: 3 });
    else b += text(x1, y + 5, '∿∿∿ bity ∿∿∿', { size: 13, fill: col });
    b += text(240, y + 30, note, { size: 12, fill: C.muted });
  });
  b += text(W / 2, 455, 'Im wyżej sięga urządzenie, tym więcej widzi — i tym więcej może zepsuć, zablokować lub zalogować.', { anchor: 'middle', size: 13, bold: true });
  files['ns01-04-devices.svg'] = svg(W, H, 'Pasek ramki z nagłówkami Ethernet, IP, TCP, TLS i HTTP oraz zakres, który odczytuje hub, przełącznik, router, firewall stanowy i reverse proxy', b);
}

// 3. Drabina diagnozy
{
  const W = 900, H = 550;
  let b = text(W / 2, 34, 'Diagnoza od dołu: każda warstwa ma swoje pytanie i narzędzie', { size: 19, bold: true, anchor: 'middle' });
  const steps = [
    ['L1', 'Czy jest link?', 'ip link, ethtool, dioda portu', 'NO-CARRIER, błędy CRC'],
    ['L2', 'Czy znam MAC bramy/sąsiada?', 'ip neigh, arp -a', 'INCOMPLETE / FAILED'],
    ['L3', 'Czy jest trasa i odpowiedź IP?', 'ip route get, ping, traceroute', 'Network is unreachable, No route to host'],
    ['L4', 'Czy port przyjmuje połączenie?', 'nc -zv host 5432, ss -tlnp', 'Connection refused / timed out'],
    ['L5–6', 'Czy TLS się zestawia?', 'openssl s_client -connect host:443', 'PKIX path building failed'],
    ['L7', 'Czy aplikacja odpowiada poprawnie?', 'curl -v, logi aplikacji', '4xx, 5xx, zły JSON'],
  ];
  steps.forEach(([l, q, tool, err], i) => {
    const y = 440 - i * 66, xo = 40 + i * 30;
    const col = [C.muted, C.host, C.net, C.ok, C.vio, C.vio][i];
    b += box(xo, y, 520, 54, C.card, col, 8);
    b += text(xo + 14, y + 33, l, { bold: true, size: 16, fill: col });
    b += text(xo + 80, y + 23, q, { bold: true, size: 14 });
    b += text(xo + 80, y + 43, tool, { mono: true, size: 12, fill: C.net });
    b += text(xo + 540, y + 33, 'błąd: ' + err, { size: 12, fill: C.no });
  });
  b += line(20, 490, 20, 90, { color: C.ok, w: 3, arrow: true });
  b += text(W / 2, 532, 'Pierwsza warstwa, która nie przechodzi testu, to miejsce problemu — wyżej nie ma sensu szukać.', { anchor: 'middle', size: 13, bold: true });
  files['ns01-04-troubleshooting.svg'] = svg(W, H, 'Drabina diagnozy od warstwy 1 do 7: pytanie, narzędzie i typowy komunikat błędu dla każdej warstwy', b);
}

// 4. Zagrozenia i zabezpieczenia wg warstw
{
  const W = 900, H = 520;
  let b = text(W / 2, 34, 'Obrona w głąb według warstw: zagrożenie → zabezpieczenie', { size: 19, bold: true, anchor: 'middle' });
  b += text(260, 68, 'Zagrożenia (rozpoznaj i wykryj)', { anchor: 'middle', bold: true, fill: C.no });
  b += text(660, 68, 'Zabezpieczenia', { anchor: 'middle', bold: true, fill: C.ok });
  const rows = [
    ['7 Aplikacji', 'SQL injection, XSS, BOLA, przejęcie konta', 'walidacja, WAF, autoryzacja, MFA, logi'],
    ['6 Prezentacji', 'downgrade TLS, słabe szyfry, SSL stripping', 'TLS 1.3, HSTS, poprawne certyfikaty'],
    ['5 Sesji', 'przejęcie sesji, kradzież tokenu', 'krótkie sesje, wiązanie tokenu, wylogowanie'],
    ['4 Transportowa', 'SYN flood, skanowanie portów', 'firewall stanowy, SYN cookies, limity'],
    ['3 Sieciowa', 'IP spoofing, ICMP flood, przejęcie trasy', 'ACL, uRPF, filtrowanie, RPKI'],
    ['2 Łącza danych', 'ARP spoofing, MAC flooding, VLAN hopping', '802.1X, port security, DAI, DHCP snooping'],
    ['1 Fizyczna', 'podpięcie do gniazda, zagłuszanie, podsłuch kabla', 'zamki, kontrola dostępu, nieużywane porty off'],
  ];
  rows.forEach(([l, t, c], i) => {
    const y = 82 + i * 60;
    b += box(30, y, 130, 50, C.card, C.ink, 8) + text(95, y + 31, l, { anchor: 'middle', bold: true, size: 13 });
    b += box(170, y, 340, 50, C.noFill, C.no, 8) + text(182, y + 31, t, { size: 13 });
    b += line(512, y + 25, 556, y + 25, { color: C.muted, arrow: true });
    b += box(560, y, 310, 50, C.okFill, C.ok, 8) + text(572, y + 31, c, { size: 13 });
  });
  files['ns01-04-defense-layers.svg'] = svg(W, H, 'Tabela siedmiu warstw z przykładowymi zagrożeniami i odpowiadającymi im zabezpieczeniami', b);
}

writeSvgs(process.argv[2], files);
