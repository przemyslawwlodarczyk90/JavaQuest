// Ilustracje do lekcji _ns_01/05_TcpIpModelAndEncapsulation.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_05_tcpip_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { build } = require('./ns01_05_packet.cjs');
const files = {};
const pk = build();

// 1. Enkapsulacja: zagniezdzone naglowki
{
  const W = 900, H = 440;
  let b = text(W / 2, 34, 'Enkapsulacja: każda warstwa dokłada swój nagłówek', { size: 19, bold: true, anchor: 'middle' });
  const rows = [
    ['Aplikacji', 'dane', [['HTTP: GET /users/42 …', 470, C.vio, C.vioFill]], 'wiadomość HTTP'],
    ['Transportowa', 'segment', [['TCP', 90, C.ok, C.okFill], ['dane HTTP', 470, C.vio, '#f3eefb']], '+ porty, numery sekwencyjne'],
    ['Internetowa', 'pakiet', [['IP', 90, C.net, C.netFill], ['TCP', 90, C.ok, C.okFill], ['dane HTTP', 470, C.vio, '#f3eefb']], '+ adresy IP, TTL'],
    ['Dostępu do sieci', 'ramka', [['Ethernet', 100, C.host, C.hostFill], ['IP', 90, C.net, C.netFill], ['TCP', 90, C.ok, C.okFill], ['dane HTTP', 380, C.vio, '#f3eefb'], ['FCS', 50, C.host, C.hostFill]], '+ adresy MAC, EtherType, CRC'],
  ];
  rows.forEach(([layer, pdu, parts, note], i) => {
    const y = 64 + i * 88;
    b += text(30, y + 22, layer, { bold: true, size: 14 });
    b += text(30, y + 42, 'PDU: ' + pdu, { size: 12, fill: C.muted });
    let x = 880 - parts.reduce((a, p) => a + p[1], 0);
    parts.forEach(([t, w, col, fill]) => { b += box(x, y, w, 40, fill, col, 4) + text(x + w / 2, y + 26, t, { anchor: 'middle', bold: true, size: 13, fill: col }); x += w; });
    b += text(880, y + 62, note, { anchor: 'end', size: 12, fill: C.muted });
  });
  b += line(20, 70, 20, 380, { color: C.ok, w: 3, arrow: true });
  b += text(W / 2, 425, 'Nadawca dokłada nagłówki z góry na dół; odbiorca zdejmuje je w odwrotnej kolejności (dekapsulacja).', { anchor: 'middle', size: 13, bold: true });
  files['ns01-05-encapsulation.svg'] = svg(W, H, 'Enkapsulacja w czterech warstwach TCP/IP: dane HTTP, segment TCP, pakiet IP i ramka Ethernet z sumą FCS', b);
}

// 2. Linijka bajtow ramki
{
  const W = 900, H = 330;
  let b = text(W / 2, 34, `Przykładowa ramka: ${pk.frame.length + 4} bajtów na kablu (bez preambuły)`, { size: 19, bold: true, anchor: 'middle' });
  const segs = [['Ethernet', 14, C.host, C.hostFill, 'MAC docelowy (6) · MAC źródłowy (6) · EtherType 0x0800 (2)'], ['IPv4', 20, C.net, C.netFill, 'wersja/IHL 0x45 · długość 119 · ID · flagi DF · TTL 64 · protokół 6 · suma · adresy'], ['TCP', 20, C.ok, C.okFill, 'port 51334 → 8080 · seq · ack · offset 5 · flagi PSH+ACK · okno · suma'], ['HTTP', pk.data.length, C.vio, C.vioFill, 'GET /users/42 HTTP/1.1 · Host · Accept (79 bajtów tekstu)'], ['FCS', 4, C.host, C.hostFill, 'CRC-32 liczony przez kartę sieciową']];
  const total = segs.reduce((a, s) => a + s[1], 0); const scale = 840 / total;
  let x = 30, off = 0;
  segs.forEach(([t, n, col, fill], i) => {
    const w = n * scale;
    b += box(x, 70, w, 48, fill, col, 3) + text(x + w / 2, 99, w > 40 ? t : '', { anchor: 'middle', bold: true, size: 13, fill: col });
    b += text(x + w / 2, 136, `${n} B`, { anchor: 'middle', size: 12, fill: C.muted });
    if (t !== 'FCS') b += text(x, 62, String(off), { size: 11, fill: C.muted, mono: true });
    x += w; off += n;
  });
  b += text(x - 4, 62, String(off), { size: 11, fill: C.muted, mono: true, anchor: 'end' });
  segs.forEach(([t, n, col, , desc], i) => { b += box(30, 156 + i * 32, 14, 14, col, col, 3) + text(54, 168 + i * 32, `${t}: ${desc}`, { size: 13 }); });
  files['ns01-05-frame-bytes.svg'] = svg(W, H, 'Linijka bajtów ramki: 14 bajtów Ethernet, 20 IPv4, 20 TCP, 79 bajtów HTTP i 4 bajty FCS wraz z opisem pól', b);
}

// 3. Nadawca i odbiorca
{
  const W = 900, H = 460;
  let b = text(W / 2, 34, 'Droga przez stos: write() u nadawcy, read() u odbiorcy', { size: 19, bold: true, anchor: 'middle' });
  const layers = [['Aplikacja (JVM)', C.vio, C.vioFill], ['TCP', C.ok, C.okFill], ['IP', C.net, C.netFill], ['Ethernet / karta', C.host, C.hostFill]];
  const sendNotes = ['out.write(bajty) → bufor gniazda', 'dzieli na segmenty ≤ MSS, nadaje seq', 'dodaje IP, wybiera trasę i next hop', 'ARP → MAC next hopa, ramka + FCS'];
  const recvNotes = ['in.read() ← bufor gniazda', 'porządkuje seq, wysyła ACK, port → gniazdo', 'czy IP docelowy mój? protokół 6 → TCP', 'FCS OK? MAC mój? EtherType 0x0800 → IP'];
  layers.forEach(([n, col, fill], i) => {
    const y = 70 + i * 82;
    b += box(40, y, 200, 56, fill, col, 8) + text(140, y + 34, n, { anchor: 'middle', bold: true, fill: col });
    b += box(660, y, 200, 56, fill, col, 8) + text(760, y + 34, n, { anchor: 'middle', bold: true, fill: col });
    b += text(250, y + 24, sendNotes[i], { size: 12 });
    b += text(650, y + 44, recvNotes[i], { size: 12, anchor: 'end' });
  });
  b += line(30, 80, 30, 380, { color: C.ok, w: 3, arrow: true }) + text(30, 400, 'enkapsulacja', { size: 12, fill: C.ok, bold: true });
  b += line(870, 380, 870, 80, { color: C.net, w: 3, arrow: true }) + text(870, 400, 'dekapsulacja', { size: 12, fill: C.net, bold: true, anchor: 'end' });
  b += line(140, 382, 760, 382, { color: C.ink, w: 4, arrow: true });
  b += text(W / 2, 410, 'bity na medium (kabel, światłowód, radio)', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 440, 'Uwaga: jeden write() nie oznacza jednego segmentu ani jednego read() — TCP to strumień bajtów.', { anchor: 'middle', size: 13, fill: C.no, bold: true });
  files['ns01-05-send-receive.svg'] = svg(W, H, 'Dwa stosy: u nadawcy dane schodzą od aplikacji przez TCP i IP do karty sieciowej, u odbiorcy wędrują w górę z kontrolami FCS, adresu i portu', b);
}

// 4. Co zmienia sie po drodze
{
  const W = 900, H = 470;
  let b = text(W / 2, 34, 'Które pola zmieniają się po drodze?', { size: 19, bold: true, anchor: 'middle' });
  const hops = [['Laptop', 'nadawca'], ['Przełącznik', 'L2'], ['Router + NAT', 'L3, dom'], ['Routery ISP', 'L3'], ['Reverse proxy', 'L7'], ['Aplikacja', 'odbiorca']];
  const X0 = 250, CW = 108;
  hops.forEach(([h, l], i) => {
    const cx = X0 + i * CW + CW / 2;
    b += box(cx - 50, 56, 100, 44, C.card, C.ink, 8) + text(cx, 76, h, { anchor: 'middle', bold: true, size: 12 }) + text(cx, 92, l, { anchor: 'middle', size: 11, fill: C.muted });
  });
  const rows = [
    ['adresy MAC', 'nowe na każdym odcinku L3', C.host, [2, 3, 4]],
    ['TTL', '−1 na każdym routerze', C.net, [2, 3]],
    ['IP źródłowy', 'NAT: prywatny → publiczny', C.net, [2]],
    ['port źródłowy', 'PAT podmienia port', C.ok, [2]],
    ['suma kontrolna IP', 'liczona od nowa po zmianie', C.net, [2, 3]],
    ['połączenie TCP', 'proxy kończy i otwiera nowe', C.ok, [4]],
    ['TLS', 'proxy odszyfrowuje', C.vio, [4]],
  ];
  rows.forEach(([field, desc, col, marks], r) => {
    const y = 116 + r * 42;
    b += `<rect x="20" y="${y}" width="860" height="38" rx="6" fill="${r % 2 ? C.bg : C.card}"/>`;
    b += text(30, y + 17, field, { bold: true, size: 13 });
    b += text(30, y + 32, desc, { size: 11, fill: C.muted });
    hops.forEach((_, i) => {
      const cx = X0 + i * CW + CW / 2;
      b += marks.includes(i) ? `<circle cx="${cx}" cy="${y + 19}" r="10" fill="${col}"/>` : `<circle cx="${cx}" cy="${y + 19}" r="4" fill="${C.line}"/>`;
    });
  });
  b += text(W / 2, 430, '● zmiana w tym punkcie   · bez zmian', { anchor: 'middle', size: 12, fill: C.muted });
  b += text(W / 2, 454, 'Bez zmian aż do proxy: IP docelowy, port docelowy i dane aplikacji (zaszyfrowane TLS).', { anchor: 'middle', size: 13, bold: true });
  files['ns01-05-hop-changes.svg'] = svg(W, H, 'Tabela: droga pakietu przez laptop, przełącznik, router z NAT, routery ISP, reverse proxy i aplikację; zaznaczono, gdzie zmieniają się adresy MAC, TTL, IP i port źródłowy, suma kontrolna, połączenie TCP i TLS', b);
}

writeSvgs(process.argv[2], files);
