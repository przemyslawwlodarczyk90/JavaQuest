// Ilustracje do lekcji _ns_01/08_BossPacketJourney.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_08_boss_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { NODES, POINTS } = require('./ns01_08_boss_lab.cjs');
const files = {};
const tag = (x, y, k) => `<circle cx="${x}" cy="${y}" r="14" fill="${C.no}"/>` + text(x, y + 5, k, { anchor: 'middle', size: 12, bold: true, fill: '#fff' });

// 1. Topologia labu
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Lab „Packet Journey”: trzy sieci, trzy punkty przechwytywania', { size: 19, bold: true, anchor: 'middle' });
  const nets = [[20, 'lan 10.10.1.0/24', C.host, C.hostFill], [300, 'wan 10.20.0.0/24', C.net, C.netFill], [600, 'app 10.30.0.0/24', C.vio, C.vioFill]];
  nets.forEach(([x, n, col, fill]) => { b += box(x, 56, 280, 250, fill, col, 12) + text(x + 140, 80, n, { anchor: 'middle', bold: true, fill: col }); });
  const dev = (x, y, title, l1, l2) => box(x - 75, y - 34, 150, 68, C.card, C.ink, 8) + text(x, y - 12, title, { anchor: 'middle', bold: true, size: 13 }) + text(x, y + 6, l1, { anchor: 'middle', size: 11, mono: true }) + text(x, y + 22, l2, { anchor: 'middle', size: 11, mono: true, fill: C.muted });
  b += dev(110, 180, 'laptop', NODES.client.ip, 'brama 10.10.1.1');
  b += dev(300, 180, 'router + NAT', '10.10.1.1 | 10.20.0.2', 'masquerade na eth1');
  b += dev(600, 180, 'reverse proxy', '10.20.0.10 | 10.30.0.10', 'Caddy :80 → app:8080');
  b += dev(800, 180, 'Spring Boot', NODES.app.ip + ':8080', 'GET /users/{id}');
  b += line(185, 180, 225, 180, { color: C.host, w: 3 }) + line(375, 180, 525, 180, { color: C.net, w: 3 }) + line(675, 180, 725, 180, { color: C.vio, w: 3 });
  b += tag(205, 150, 'P1') + tag(450, 150, 'P2') + tag(700, 150, 'P3');
  b += text(W / 2, 336, 'P1: przed NAT · P2: po NAT, przed proxy · P3: nowe połączenie proxy → aplikacja', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 360, 'Wszystkie sieci internal: true — lab nie ma wyjścia do Internetu; jedyna droga laptopa do aplikacji prowadzi przez router i proxy.', { anchor: 'middle', size: 12, fill: C.muted });
  b += text(W / 2, 382, 'Zadanie: przechwycić ARP, ICMP i TCP/HTTP w P1, P2, P3 i wyjaśnić każdą zmianę adresów, portów i TTL.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-08-lab-topology.svg'] = svg(W, H, 'Topologia labu: laptop w sieci lan, router z NAT między lan i wan, reverse proxy między wan i app oraz aplikacja Spring Boot w sieci app; punkty przechwytywania P1, P2 i P3', b);
}

// 2. Tabela pol w P1-P3
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'To samo żądanie GET /users/42 w trzech punktach', { size: 19, bold: true, anchor: 'middle' });
  const rows = [['MAC źródłowy', 'srcMac'], ['MAC docelowy', 'dstMac'], ['IP źródłowy', 'srcIp'], ['IP docelowy', 'dstIp'], ['port źródłowy', 'srcPort'], ['port docelowy', 'dstPort'], ['TTL', 'ttl'], ['X-Forwarded-For', 'xff'], ['połączenie TCP', 'conn']];
  const X = [30, 230, 450, 670], CW = 210;
  ['pole', 'P1 (LAN)', 'P2 (WAN, po NAT)', 'P3 (proxy → app)'].forEach((h, i) => { b += text(X[i] + 8, 66, h, { bold: true, size: 13 }); });
  rows.forEach(([label, key], r) => {
    const y = 76 + r * 36;
    b += `<rect x="24" y="${y}" width="852" height="32" rx="5" fill="${r % 2 ? C.bg : C.card}"/>`;
    b += text(X[0] + 8, y + 21, label, { bold: true, size: 12 });
    POINTS.forEach((p, i) => {
      const v = String(p[key]); const prev = i > 0 ? String(POINTS[i - 1][key]) : v; const changed = i > 0 && v !== prev;
      if (changed) b += `<rect x="${X[i + 1]}" y="${y + 3}" width="${CW - 6}" height="26" rx="4" fill="${C.hostFill}"/>`;
      b += text(X[i + 1] + 8, y + 21, v, { size: 12, mono: key !== 'conn', bold: changed, fill: changed ? C.host : C.ink });
    });
  });
  b += text(W / 2, 410, 'Pomarańczowe pola zmieniły się względem poprzedniego punktu.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-08-header-table.svg'] = svg(W, H, 'Tabela wartości pól żądania w punktach P1, P2 i P3: adresy MAC, IP, porty, TTL, nagłówek X-Forwarded-For i połączenie TCP, z wyróżnieniem zmian', b);
}

// 3. Diagram sekwencji
{
  const W = 900, H = 520;
  let b = text(W / 2, 32, 'Kolejność zdarzeń: ARP, handshake, żądanie, drugie połączenie', { size: 19, bold: true, anchor: 'middle' });
  const cols = [['laptop', 110], ['router', 340], ['proxy', 570], ['app', 800]];
  cols.forEach(([n, x]) => { b += box(x - 55, 48, 110, 30, C.card, C.ink, 6) + text(x, 68, n, { anchor: 'middle', bold: true, size: 13 }) + line(x, 80, x, 500, { color: C.line, dash: true }); });
  const msgs = [
    [110, 340, 'ARP: kto ma 10.10.1.1?', C.host], [340, 110, 'ARP: 10.10.1.1 is-at …01:01', C.host],
    [110, 570, 'SYN → 10.20.0.10:80 (przez router, NAT)', C.ok], [570, 110, 'SYN-ACK', C.ok], [110, 570, 'ACK', C.ok],
    [110, 570, 'GET /users/42', C.vio],
    [570, 800, 'SYN → 10.30.0.20:8080 (nowe połączenie)', C.ok], [800, 570, 'SYN-ACK', C.ok], [570, 800, 'ACK + GET /users/42, X-Forwarded-For', C.vio],
    [800, 570, '200 OK {json}', C.vio], [570, 110, '200 OK {json}', C.vio],
  ];
  msgs.forEach(([x1, x2, t, col], i) => {
    const y = 104 + i * 36;
    b += line(x1, y, x2, y, { color: col, w: 2, arrow: true });
    b += text((x1 + x2) / 2, y - 6, t, { anchor: 'middle', size: 11, fill: col, bold: true });
  });
  b += text(340, 508, 'router nie tworzy nowych połączeń — tylko podmienia adres źródłowy (NAT)', { anchor: 'middle', size: 11, fill: C.muted });
  files['ns01-08-sequence.svg'] = svg(W, H, 'Diagram sekwencji: laptop pyta ARP o bramę, zestawia połączenie TCP z proxy przez router z NAT i wysyła GET; proxy otwiera osobne połączenie do aplikacji, przekazuje żądanie z X-Forwarded-For i odsyła odpowiedź 200', b);
}

// 4. Korelacja zdarzen w logach
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Jedno żądanie, cztery źródła dowodów — jak je połączyć', { size: 19, bold: true, anchor: 'middle' });
  const srcs = [
    ['pcap P1', `${NODES.client.ip}:45678 → 10.20.0.10:80`, C.host],
    ['conntrack routera', `10.10.1.10:45678 ⇄ 10.20.0.2:45678 (NAT)`, C.net],
    ['log Caddy (proxy)', 'remote_ip=10.20.0.2 uri=/users/42 id=7f3a…', C.ok],
    ['log Spring Boot', 'remoteAddr=10.30.0.10 XFF=10.20.0.2 traceId=7f3a…', C.vio],
  ];
  srcs.forEach(([n, v, col], i) => {
    const y = 60 + i * 70;
    b += box(40, y, 220, 52, C.card, col, 8) + text(150, y + 31, n, { anchor: 'middle', bold: true, fill: col });
    b += box(280, y, 580, 52, C.bg, col, 8) + text(296, y + 31, v, { mono: true, size: 13 });
    if (i < srcs.length - 1) b += line(150, y + 54, 150, y + 66, { color: C.muted, arrow: true });
  });
  b += text(W / 2, 352, 'Klucze korelacji: czas (NTP!), tabela NAT, porty, X-Forwarded-For i identyfikator żądania (traceparent / X-Request-ID).', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 376, 'Bez tabeli NAT nikt poza routerem nie wie, że 10.20.0.2 to laptop 10.10.1.10.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-08-correlation.svg'] = svg(W, H, 'Cztery źródła dowodów jednego żądania: pcap w P1, tabela conntrack routera, log reverse proxy i log aplikacji Spring Boot, połączone czasem, tabelą NAT, X-Forwarded-For i identyfikatorem żądania', b);
}

writeSvgs(process.argv[2], files);
