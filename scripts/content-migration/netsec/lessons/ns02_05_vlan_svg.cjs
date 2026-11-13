// Ilustracje do lekcji _ns_02/05_VlanTrunkingAndStp.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_05_vlan_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, node, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Jeden przelacznik, dwie sieci VLAN = dwa logiczne przelaczniki
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'VLAN: jeden fizyczny przełącznik, kilka logicznych domen rozgłoszeniowych', { size: 18, bold: true, anchor: 'middle' });
  b += box(40, 60, 380, 300, C.card, C.line, 10) + text(230, 86, 'Fizycznie', { anchor: 'middle', bold: true, size: 15 });
  b += node(230, 170, 'SW1 (24 porty)', { square: true, w: 110, size: 13, fill: C.netFill, stroke: C.net, color: C.net });
  const hosts = [['PC-A', 90, 'VLAN 10', C.host, C.hostFill], ['PC-B', 180, 'VLAN 10', C.host, C.hostFill], ['PC-C', 280, 'VLAN 20', C.ok, C.okFill], ['PC-D', 370, 'VLAN 20', C.ok, C.okFill]];
  hosts.forEach(([n, x, v, col, fill]) => {
    b += line(230, 186, x, 268, { color: col, w: 3 });
    b += node(x, 285, n, { square: true, w: 34, size: 11, fill, stroke: col, color: col });
    b += text(x, 320, v, { anchor: 'middle', size: 11, bold: true, fill: col });
  });
  b += text(230, 348, 'porty access: każdy port należy do jednego VLAN-u', { anchor: 'middle', size: 11, fill: C.muted });
  b += line(430, 210, 470, 210, { arrow: true, w: 3 });
  b += box(480, 60, 380, 300, C.card, C.line, 10) + text(670, 86, 'Logicznie', { anchor: 'middle', bold: true, size: 15 });
  b += box(500, 105, 340, 110, C.hostFill, C.host, 8) + text(670, 128, 'VLAN 10 — 192.168.10.0/24', { anchor: 'middle', bold: true, fill: C.host, size: 13 });
  b += node(580, 175, 'PC-A', { square: true, w: 34, size: 11 }) + node(760, 175, 'PC-B', { square: true, w: 34, size: 11 }) + line(614, 175, 726, 175, { color: C.host, w: 3 });
  b += box(500, 230, 340, 110, C.okFill, C.ok, 8) + text(670, 253, 'VLAN 20 — 192.168.20.0/24', { anchor: 'middle', bold: true, fill: C.ok, size: 13 });
  b += node(580, 300, 'PC-C', { square: true, w: 34, size: 11 }) + node(760, 300, 'PC-D', { square: true, w: 34, size: 11 }) + line(614, 300, 726, 300, { color: C.ok, w: 3 });
  b += text(W / 2, 392, 'Broadcast z PC-A dociera tylko do PC-B. Ruch VLAN 10 ↔ VLAN 20 wymaga routera (L3) — i tam stawiasz firewall.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-05-vlan-logical.svg'] = svg(W, H, 'Po lewej jeden fizyczny przełącznik SW1 z czterema komputerami: PC-A i PC-B w VLAN 10, PC-C i PC-D w VLAN 20. Po prawej ten sam układ logicznie: dwie oddzielne sieci 192.168.10.0/24 i 192.168.20.0/24. Broadcast z PC-A dociera tylko do PC-B, a ruch między VLAN-ami wymaga routera.', b);
}

// 2. Ramka 802.1Q
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Znacznik 802.1Q: 4 bajty wstawione między adres źródłowy a EtherType', { size: 18, bold: true, anchor: 'middle' });
  const f = [['MAC docelowy', '6 B', 120, C.card], ['MAC źródłowy', '6 B', 120, C.card], ['802.1Q tag', '4 B', 100, C.vioFill], ['EtherType', '2 B', 90, C.card], ['dane', '46–1500 B', 240, C.card], ['FCS', '4 B', 70, C.card]];
  let x = 60;
  f.forEach(([n, len, w, fill]) => {
    b += box(x, 66, w, 50, fill, fill === C.vioFill ? C.vio : C.ink, 4) + text(x + w / 2, 90, n, { anchor: 'middle', size: 12, bold: true }) + text(x + w / 2, 108, len, { anchor: 'middle', size: 11, fill: C.muted });
    if (fill === C.vioFill) b += line(x, 118, 160, 180, { color: C.vio }) + line(x + w, 118, 840, 180, { color: C.vio });
    x += w + 4;
  });
  // szerokosci pol znacznika nieproporcjonalne, aby podpisy byly czytelne
  const t = [['TPID = 0x8100', '16 bitów', 280, C.vioFill, 'mówi: „tu jest znacznik VLAN”'], ['PCP', '3 bity', 110, C.hostFill, 'priorytet 0–7 (802.1p)'], ['DEI', '1 bit', 70, C.noFill, 'do odrzucenia'], ['VID', '12 bitów', 220, C.okFill, 'numer VLAN 0–4095']];
  let tx = 160;
  t.forEach(([n, bits, w, fill, d]) => {
    b += box(tx, 182, w, 50, fill, C.ink, 3) + text(tx + w / 2, 205, n, { anchor: 'middle', size: 12, bold: true }) + text(tx + w / 2, 223, bits, { anchor: 'middle', size: 11 });
    b += text(tx + w / 2, n === 'DEI' ? 272 : 254, d, { anchor: 'middle', size: 11, fill: C.muted });
    tx += w;
  });
  b += box(60, 296, 780, 66, C.card, C.line, 8);
  b += text(80, 320, 'Przykład TCI = 0xA014 → 1010 0000 0001 0100 → PCP = 5 (głos), DEI = 0, VID = 0x014 = 20', { mono: true, size: 13, bold: true });
  b += text(80, 344, 'Ramka z tagiem: maks. 1522 B zamiast 1518 B. VID 0 i 4095 są zarezerwowane → 4094 użyteczne VLAN-y.', { size: 12 });
  files['ns02-05-dot1q.svg'] = svg(W, H, 'Ramka Ethernet z 4-bajtowym znacznikiem 802.1Q między adresem źródłowym a polem EtherType; znacznik składa się z TPID 0x8100 (16 bitów) oraz pól PCP (3 bity priorytetu), DEI (1 bit) i VID (12 bitów numeru VLAN). Przykład: TCI 0xA014 oznacza priorytet 5 i VLAN 20.', b);
}

// 3. Access vs trunk i native VLAN
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Porty access i trunk: gdzie znacznik się pojawia, a gdzie znika', { size: 18, bold: true, anchor: 'middle' });
  b += node(250, 170, 'SW1', { square: true, w: 60, size: 14, fill: C.netFill, stroke: C.net, color: C.net });
  b += node(650, 170, 'SW2', { square: true, w: 60, size: 14, fill: C.netFill, stroke: C.net, color: C.net });
  b += line(310, 170, 590, 170, { color: C.vio, w: 6 });
  b += text(450, 150, 'trunk 802.1Q (VLAN 10, 20)', { anchor: 'middle', bold: true, fill: C.vio, size: 13 });
  b += box(330, 184, 110, 26, C.hostFill, C.host, 4) + text(385, 202, 'tag VID 10', { anchor: 'middle', size: 11, mono: true });
  b += box(460, 184, 110, 26, C.okFill, C.ok, 4) + text(515, 202, 'tag VID 20', { anchor: 'middle', size: 11, mono: true });
  b += box(395, 216, 110, 26, C.card, C.ink, 4) + text(450, 234, 'bez tagu', { anchor: 'middle', size: 11, mono: true });
  b += text(450, 260, '↑ native VLAN (domyślnie 1)', { anchor: 'middle', size: 11, fill: C.no, bold: true });
  const ends = [[120, 300, 'PC-A', 'access VLAN 10', C.host, C.hostFill, 250], [330, 300, 'PC-C', 'access VLAN 20', C.ok, C.okFill, 250], [570, 300, 'PC-B', 'access VLAN 10', C.host, C.hostFill, 650], [780, 300, 'PC-D', 'access VLAN 20', C.ok, C.okFill, 650]];
  ends.forEach(([x, y, n, d, col, fill, sx]) => {
    b += line(sx, 186, x, y - 16, { color: col, w: 3 });
    b += node(x, y, n, { square: true, w: 34, size: 11, fill, stroke: col, color: col });
    b += text(x, y + 34, d, { anchor: 'middle', size: 11, fill: col, bold: true });
  });
  b += text(W / 2, 372, 'Host na porcie access wysyła i odbiera ramki BEZ znacznika — tag dodaje i zdejmuje przełącznik.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-05-trunk.svg'] = svg(W, H, 'Dwa przełączniki SW1 i SW2 połączone łączem trunk 802.1Q przenoszącym VLAN 10 i 20 ze znacznikami VID 10 i VID 20 oraz ramki bez znacznika w native VLAN; do każdego przełącznika podłączone są dwa komputery na portach access w VLAN 10 i VLAN 20. Host na porcie access nie widzi znacznika — dodaje i zdejmuje go przełącznik.', b);
}

// 4. Petla L2 i STP
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Pętla L2 bez STP = burza rozgłoszeniowa; STP blokuje jeden port', { size: 18, bold: true, anchor: 'middle' });
  const tri = (ox, withStp) => {
    let s = box(ox, 56, 400, 310, C.card, withStp ? C.ok : C.no, 10) + text(ox + 200, 82, withStp ? 'Z STP: drzewo bez pętli' : 'Bez STP: ramki krążą w nieskończoność', { anchor: 'middle', bold: true, fill: withStp ? C.ok : C.no, size: 14 });
    const A = [ox + 200, 130], B = [ox + 80, 290], Cc = [ox + 320, 290];
    s += line(A[0], A[1] + 16, B[0] + 10, B[1] - 16, { color: withStp ? C.ok : C.no, w: 4 });
    s += line(A[0], A[1] + 16, Cc[0] - 10, Cc[1] - 16, { color: withStp ? C.ok : C.no, w: 4 });
    s += line(B[0] + 40, B[1], Cc[0] - 40, Cc[1], { color: withStp ? C.line : C.no, w: 4, dash: withStp });
    s += node(A[0], A[1], withStp ? 'SW1 root' : 'SW1', { square: true, w: 50, size: 12, fill: withStp ? C.okFill : C.noFill, stroke: withStp ? C.ok : C.no });
    s += node(B[0], B[1], 'SW2', { square: true, w: 40, size: 12, fill: C.netFill, stroke: C.net });
    s += node(Cc[0], Cc[1], 'SW3', { square: true, w: 40, size: 12, fill: C.netFill, stroke: C.net });
    if (withStp) {
      s += box(Cc[0] - 70, Cc[1] - 12, 26, 24, C.noFill, C.no, 4) + text(Cc[0] - 57, Cc[1] + 5, '✕', { anchor: 'middle', bold: true, fill: C.no });
      s += text(ox + 200, 330, 'port SW3 → SW2 zablokowany (alternate)', { anchor: 'middle', size: 12, fill: C.no, bold: true });
      s += text(ox + 200, 350, 'przy awarii łącza STP go odblokuje', { anchor: 'middle', size: 11, fill: C.muted });
    } else {
      s += text(ox + 125, 200, '↻', { size: 30, fill: C.no, bold: true }) + text(ox + 255, 200, '↻', { size: 30, fill: C.no, bold: true });
      s += text(ox + 200, 330, 'broadcast mnoży się na każdym przełączniku', { anchor: 'middle', size: 12, fill: C.no, bold: true });
      s += text(ox + 200, 350, 'Ethernet nie ma TTL · tablice MAC „skaczą”', { anchor: 'middle', size: 11, fill: C.muted });
    }
    return s;
  };
  b += tri(40, false) + tri(460, true);
  b += text(W / 2, 396, 'Root bridge = najniższy Bridge ID (priorytet + VLAN + MAC). Szczegóły ról i stanów portów: lekcja 07.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-05-stp-loop.svg'] = svg(W, H, 'Dwa trójkąty z przełącznikami SW1, SW2 i SW3. Po lewej bez STP: ramki rozgłoszeniowe krążą w pętli i mnożą się, bo Ethernet nie ma TTL. Po prawej z STP: SW1 jest root bridge, a port SW3 w stronę SW2 jest zablokowany, więc topologia jest drzewem; przy awarii łącza STP go odblokuje.', b);
}

writeSvgs(process.argv[2], files);
