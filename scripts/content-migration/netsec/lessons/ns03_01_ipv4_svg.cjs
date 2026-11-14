// Ilustracje do lekcji _ns_03/01_Ipv4HeaderAndAddressing.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_01_ipv4_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { SAMPLES, decode, checksumSteps, hex2, hex4, CLASSES } = require('./ns03_01_ipv4_model.cjs');
const files = {};

// 1. Uklad naglowka: 5 wierszy po 32 bity
{
  const W = 900, H = 470, x0 = 70, x1 = 870, bw = (x1 - x0) / 32;
  let b = text(W / 2, 30, 'Nagłówek IPv4 (RFC 791): 5 wierszy po 32 bity = 20 bajtów bez opcji', { size: 18, bold: true, anchor: 'middle' });
  for (const bit of [0, 4, 8, 16, 19, 24, 31]) b += text(x0 + bit * bw + bw / 2, 58, String(bit), { anchor: 'middle', size: 11, fill: C.muted });
  const rows = [
    [[0, 4, 'Version', C.net], [4, 8, 'IHL', C.net], [8, 14, 'DSCP', C.vio], [14, 16, 'ECN', C.vio], [16, 32, 'Total Length', C.net]],
    [[0, 16, 'Identification', C.host], [16, 19, 'Flags', C.host], [19, 32, 'Fragment Offset', C.host]],
    [[0, 8, 'TTL', C.no], [8, 16, 'Protocol', C.ok], [16, 32, 'Header Checksum', C.no]],
    [[0, 32, 'Source Address (adres źródłowy)', C.ok]],
    [[0, 32, 'Destination Address (adres docelowy)', C.ok]],
  ];
  const fills = { [C.net]: C.netFill, [C.vio]: C.vioFill, [C.host]: C.hostFill, [C.no]: C.noFill, [C.ok]: C.okFill };
  rows.forEach((row, r) => {
    const y = 68 + r * 52;
    b += text(x0 - 12, y + 31, `${r * 4}`, { anchor: 'end', size: 11, fill: C.muted });
    for (const [s, e, n, col] of row) b += box(x0 + s * bw, y, (e - s) * bw, 46, fills[col], col, 3) + text(x0 + ((s + e) / 2) * bw, y + 29, n, { anchor: 'middle', bold: true, size: 13, fill: col });
  });
  b += box(x0, 68 + 5 * 52, x1 - x0, 34, C.card, C.line, 3) + text((x0 + x1) / 2, 68 + 5 * 52 + 22, 'Options (0–40 B, rzadkie) — wtedy IHL > 5', { anchor: 'middle', size: 12, fill: C.muted });
  b += text(x0 - 12, 58, 'bajt', { anchor: 'end', size: 10, fill: C.muted });
  const notes = ['IHL = długość nagłówka w słowach 32-bit (5 → 20 B)', 'Flags: 0 | DF (nie fragmentuj) | MF (więcej fragmentów)', 'TTL: każdy router −1, przy 0 pakiet ginie (ICMP Time Exceeded)', 'Protocol: 1 ICMP, 6 TCP, 17 UDP, 50 ESP, 89 OSPF'];
  notes.forEach((n, i) => { b += text(x0, 402 + i * 18, '• ' + n, { size: 12 }); });
  files['ns03-01-header.svg'] = svg(W, H, 'Układ nagłówka IPv4 w pięciu 32-bitowych wierszach: Version i IHL po 4 bity, DSCP 6 bitów, ECN 2 bity, Total Length 16 bitów; Identification 16, Flags 3, Fragment Offset 13; TTL 8, Protocol 8, Header Checksum 16; adres źródłowy 32 bity; adres docelowy 32 bity; opcjonalne opcje do 40 bajtów.', b);
}

// 2. Dekodowanie hexdumpu
{
  const W = 900, H = 440;
  const bytes = SAMPLES.tcp, d = decode(bytes);
  let b = text(W / 2, 30, 'Packet Lab: 20 bajtów z Wiresharka → pola nagłówka', { size: 18, bold: true, anchor: 'middle' });
  const groups = [[0, 1, C.net], [1, 2, C.vio], [2, 4, C.net], [4, 6, C.host], [6, 8, C.host], [8, 9, C.no], [9, 10, C.ok], [10, 12, C.no], [12, 16, C.ok], [16, 20, C.ok]];
  const fills = { [C.net]: C.netFill, [C.vio]: C.vioFill, [C.host]: C.hostFill, [C.no]: C.noFill, [C.ok]: C.okFill };
  const bx = (i) => 60 + i * 39.5;
  for (const [s, e, col] of groups) {
    b += box(bx(s) - 2, 56, (e - s) * 39.5 - 3, 40, fills[col], col, 4);
    for (let i = s; i < e; i++) b += text(bx(i) + 17, 82, hex2(bytes[i]), { anchor: 'middle', mono: true, bold: true, size: 15 });
  }
  const rows = [
    ['45', `Version ${d.version}, IHL ${d.ihl} → nagłówek ${d.headerLen} B`, C.net],
    ['00', `DSCP ${d.dscp} (best effort), ECN ${d.ecn} (nieużywane)`, C.vio],
    ['00 3c', `Total Length ${d.total} B → dane ${d.payload} B (nagłówek TCP + treść)`, C.net],
    ['1c 46', `Identification 0x1c46 = ${d.id}`, C.host],
    ['40 00', `Flags: DF=${d.df ? 1 : 0}, MF=${d.mf ? 1 : 0}; Fragment Offset ${d.frag}`, C.host],
    ['40', `TTL ${d.ttl} (typowo Linux/macOS)`, C.no],
    ['06', `Protocol ${d.proto} = ${d.protoName}`, C.ok],
    ['26 e9', `Header Checksum 0x${hex4(d.checksum)} — weryfikacja: suma = 0xffff ✓`, C.no],
    ['c0 a8 01 0a', `Źródło ${d.src}`, C.ok],
    ['5d b8 d8 22', `Cel ${d.dst}`, C.ok],
  ];
  rows.forEach(([h, t, col], i) => {
    const y = 128 + i * 29;
    b += text(80, y, h, { mono: true, bold: true, size: 13, fill: col }) + text(220, y, t, { size: 13 });
  });
  b += text(W / 2, 425, 'Hex → dziesiętnie: c0 = 192, a8 = 168, 01 = 1, 0a = 10. Dwie cyfry hex = jeden bajt (oktet).', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns03-01-decode.svg'] = svg(W, H, `Hexdump 20 bajtów nagłówka IPv4: 45 00 00 3c 1c 46 40 00 40 06 26 e9 c0 a8 01 0a 5d b8 d8 22 zdekodowany na pola: wersja 4, IHL 5, DSCP 0, długość całkowita 60, identyfikator 0x1c46, flaga DF, TTL 64, protokół 6 TCP, suma kontrolna 0x26e9, źródło 192.168.1.10, cel 93.184.216.34.`, b);
}

// 3. Suma kontrolna krok po kroku
{
  const W = 900, H = 420;
  const st = checksumSteps(SAMPLES.tcp);
  let b = text(W / 2, 30, 'Suma kontrolna nagłówka: dodawanie słów 16-bitowych z przeniesieniem i negacja', { size: 17, bold: true, anchor: 'middle' });
  b += text(60, 66, '1. Pole checksum = 0000, nagłówek dzielimy na 10 słów 16-bitowych:', { size: 13, bold: true });
  st.words.forEach((w, i) => { b += box(60 + i * 79, 78, 72, 32, i === 5 ? C.noFill : C.card, i === 5 ? C.no : C.line, 4) + text(96 + i * 79, 99, hex4(w), { anchor: 'middle', mono: true, size: 14, bold: true }); });
  b += text(60, 146, `2. Suma: ${st.words.map(hex4).join(' + ')} = 0x${st.sum.toString(16)}`, { size: 12, mono: true });
  b += text(60, 180, `3. Przeniesienie (bity powyżej 16): 0x${hex4(st.sum & 0xffff)} + 0x${st.carry.toString(16)} = 0x${hex4(st.folded)}`, { size: 13, mono: true });
  b += text(60, 214, `4. Negacja (NOT, dopełnienie do jedności): ~0x${hex4(st.folded)} = 0x${hex4(st.result)}  ← wpisujemy do nagłówka`, { size: 13, mono: true, bold: true, fill: C.ok });
  b += box(60, 240, 780, 160, C.card, C.line, 8);
  b += text(80, 266, 'Weryfikacja u odbiorcy (i na każdym routerze): suma wszystkich 10 słów RAZEM z checksum → 0xffff = OK.', { size: 13 });
  b += text(80, 292, 'Router zmniejsza TTL, więc MUSI przeliczyć sumę na każdym skoku (RFC 1624 — aktualizacja przyrostowa).', { size: 13 });
  b += text(80, 318, 'Suma chroni tylko NAGŁÓWEK, nie dane — dane chronią TCP/UDP (z pseudo-nagłówkiem IP) i CRC ramki Ethernet.', { size: 13 });
  b += text(80, 344, 'W IPv6 nie ma sumy kontrolnej nagłówka — routery pracują szybciej, ochronę dają L2 i L4.', { size: 13 });
  b += text(80, 376, 'Wireshark „checksum incorrect” na pakietach wychodzących = zwykle checksum offload karty sieciowej, nie błąd.', { size: 13, bold: true, fill: C.host });
  files['ns03-01-checksum.svg'] = svg(W, H, `Obliczenie sumy kontrolnej nagłówka IPv4: pole sumy wyzerowane, 10 słów 16-bitowych zsumowanych do 0x${st.sum.toString(16)}, przeniesienie daje 0x${hex4(st.folded)}, negacja daje 0x${hex4(st.result)}. Odbiorca sumuje słowa razem z polem sumy i oczekuje 0xffff. Router przelicza sumę po zmianie TTL; IPv6 nie ma sumy nagłówka.`, b);
}

// 4. Klasy adresow i CIDR
{
  const W = 900, H = 430, x0 = 60, x1 = 840, X = (o) => x0 + (o / 256) * (x1 - x0);
  let b = text(W / 2, 30, 'Klasy adresów (1981–1993) i dlaczego zastąpił je CIDR', { size: 18, bold: true, anchor: 'middle' });
  const spans = [[0, 128, C.net, C.netFill], [128, 192, C.ok, C.okFill], [192, 224, C.host, C.hostFill], [224, 240, C.vio, C.vioFill], [240, 256, C.muted, C.card]];
  CLASSES.forEach(([k, bits, range, prefix, desc], i) => {
    const [s, e, col, fill] = spans[i];
    b += box(X(s), 56, X(e) - X(s), 52, fill, col, 3) + text((X(s) + X(e)) / 2, 80, `Klasa ${k}`, { anchor: 'middle', bold: true, fill: col, size: e - s > 20 ? 14 : 11 }) + text((X(s) + X(e)) / 2, 98, range, { anchor: 'middle', size: e - s > 20 ? 12 : 9 });
    const y = 150 + i * 20;
    if (i === 0) b += ['klasa', 'pierwsze bity', 'pierwszy oktet', 'maska domyślna', 'pojemność'].map((h, j) => text([x0, 120, 240, 370, 500][j], 132, h, { size: 11, bold: true, fill: C.muted })).join('');
    b += text(x0, y, k, { bold: true, size: 12, fill: spans[i][2] }) + text(120, y, bits, { mono: true, size: 12 }) + text(240, y, range, { mono: true, size: 12 }) + text(370, y, prefix, { mono: true, size: 12 }) + text(500, y, desc, { size: 12 });
  });
  b += text(x0, 52, '0', { size: 10, fill: C.muted }) + text(x1, 52, '255 (pierwszy oktet)', { size: 10, fill: C.muted, anchor: 'end' });
  b += box(x0, 252, 370, 160, C.noFill, C.no, 8);
  b += text(x0 + 16, 276, 'Dlaczego klasy umarły', { bold: true, fill: C.no, size: 14 });
  ['Firma na 2000 hostów: C za mała (254),', 'B (65 534) — 97% adresów zmarnowane.', 'Klasy B kończyły się na początku lat 90.', 'Tablice routingu rosły (wiele sieci C).', 'Tylko 3 rozmiary sieci — zero elastyczności.'].forEach((t, i) => { b += text(x0 + 16, 300 + i * 21, t, { size: 12 }); });
  b += box(450, 252, 390, 160, C.okFill, C.ok, 8);
  b += text(466, 276, 'CIDR (RFC 1519, 1993; dziś RFC 4632)', { bold: true, fill: C.ok, size: 14 });
  ['Prefiks dowolnej długości: /22 = 1022 hosty.', 'Agregacja tras: 4 sieci /24 → jedna /22.', 'Klasa nie wynika już z adresu — maska', 'ZAWSZE podawana jawnie (adres/prefiks).', '„Klasa C” w mowie potocznej = /24.'].forEach((t, i) => { b += text(466, 300 + i * 21, t, { size: 12 }); });
  files['ns03-01-classes.svg'] = svg(W, H, 'Podział pierwszego oktetu na klasy: A 0–127 z maską /8, B 128–191 z /16, C 192–223 z /24, D 224–239 multicast, E 240–255 zarezerwowane. Klasy zmarnowały adresy i powiększały tablice routingu; w 1993 zastąpił je CIDR z prefiksem dowolnej długości i agregacją tras.', b);
}

writeSvgs(process.argv[2], files);
