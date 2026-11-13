// Ilustracje do lekcji _ns_02/02_MacAddressesAndSwitching.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_02_mac_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, node, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Struktura adresu MAC
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Adres MAC: 48 bitów, dwa ważne bity w pierwszym bajcie', { size: 18, bold: true, anchor: 'middle' });
  const bytes = ['3C', '52', '82', '1A', 'F0', '07'];
  bytes.forEach((h, i) => { const x = 150 + i * 100; b += box(x, 60, 90, 50, i < 3 ? C.netFill : C.hostFill, i < 3 ? C.net : C.host, 6) + text(x + 45, 92, h, { anchor: 'middle', bold: true, mono: true, size: 20, fill: i < 3 ? C.net : C.host }); });
  b += text(285, 132, 'OUI — identyfikator producenta (24 bity)', { anchor: 'middle', size: 12, bold: true, fill: C.net });
  b += text(585, 132, 'numer karty nadany przez producenta (24 bity)', { anchor: 'middle', size: 12, bold: true, fill: C.host });
  b += text(150, 178, 'pierwszy bajt 0x3C =', { size: 13, bold: true });
  const bits = '00111100';
  bits.split('').forEach((bt, i) => { const x = 320 + i * 44; const hl = i >= 6; b += box(x, 158, 38, 38, hl ? C.vioFill : C.card, hl ? C.vio : C.line, 4) + text(x + 19, 183, bt, { anchor: 'middle', mono: true, size: 18, bold: hl, fill: hl ? C.vio : C.ink }); });
  b += text(320 + 6 * 44 + 19, 218, 'U/L', { anchor: 'middle', size: 12, bold: true, fill: C.vio }) + text(320 + 7 * 44 + 19, 218, 'I/G', { anchor: 'middle', size: 12, bold: true, fill: C.vio });
  b += box(40, 240, 820, 140, C.card, C.line, 10);
  [['bit I/G (najmłodszy bit 1. bajtu)', '0 = unicast (jedna karta), 1 = grupowy (multicast/broadcast)'], ['bit U/L (drugi najmłodszy)', '0 = globalnie unikalny (nadany przez producenta), 1 = lokalnie administrowany'], ['przykłady', '02:42:… Docker (lokalny), 33:33:… multicast IPv6, 01:00:5e:… multicast IPv4, ff:ff:ff:ff:ff:ff broadcast'], ['losowe MAC', 'Android, iOS, Windows i macOS dla Wi-Fi: lokalnie administrowane (bit U/L = 1), inne w każdej sieci']].forEach(([k, d], i) => { b += text(56, 268 + i * 30, k, { bold: true, size: 12 }) + text(330, 268 + i * 30, d, { size: 12 }); });
  files['ns02-02-mac-structure.svg'] = svg(W, H, 'Adres MAC 3C:52:82:1A:F0:07 podzielony na OUI producenta i numer karty; pierwszy bajt rozpisany bitowo z zaznaczonymi bitami U/L i I/G oraz opisem ich znaczenia i przykładami adresów', b);
}

// 2. Uczenie sie przelacznika
{
  const W = 900, H = 470;
  let b = text(W / 2, 32, 'Przełącznik uczy się adresów źródłowych i kieruje ramki po docelowych', { size: 18, bold: true, anchor: 'middle' });
  const panel = (x, y, title, table, arrows, note, src) => {
    let s = box(x, y, 420, 200, C.card, C.line, 10) + text(x + 14, y + 22, title, { bold: true, size: 13 });
    const sw = [x + 150, y + 105];
    const hosts = [['A', x + 50, y + 60, 1], ['B', x + 250, y + 60, 2], ['C', x + 50, y + 150, 3], ['D', x + 250, y + 150, 4]];
    hosts.forEach(([h, hx, hy, p]) => { s += line(hx, hy, sw[0], sw[1], { color: C.line }) + text((hx + sw[0]) / 2 + (hx < sw[0] ? -10 : 10), (hy + sw[1]) / 2 - 4, `p${p}`, { size: 10, fill: C.muted, anchor: 'middle' }); });
    if (src) { const t = hosts.find((q) => q[0] === src); s += line(t[1], t[2], sw[0], sw[1], { color: C.ink, w: 3, dash: true }); }
    arrows.forEach(([h, col]) => { const t = hosts.find((q) => q[0] === h); s += line(sw[0], sw[1], t[1], t[2], { color: col, w: 4 }); });
    hosts.forEach(([h, hx, hy]) => { s += node(hx, hy, h); });
    s += node(sw[0], sw[1], 'SW', { square: true, w: 24, fill: C.netFill, stroke: C.net, color: C.net });
    s += box(x + 295, y + 50, 112, 26 + table.length * 18, C.bg, C.ink, 4) + text(x + 303, y + 66, 'tablica MAC', { size: 11, bold: true });
    table.forEach((r, i) => { s += text(x + 303, y + 86 + i * 18, r, { size: 12, mono: true }); });
    s += text(x + 14, y + 190, note, { size: 11, fill: C.muted });
    return s;
  };
  b += panel(20, 50, '1. A → B: źródło A nieznane', ['A → p1'], [['B', C.host], ['C', C.host], ['D', C.host]], 'uczy się A na p1; B nieznany → flooding (wszystkie porty poza p1)', 'A');
  b += panel(460, 50, '2. B odpowiada → A', ['A → p1', 'B → p2'], [['A', C.ok]], 'uczy się B na p2; A znany → tylko p1', 'B');
  b += panel(20, 260, '3. A → B ponownie', ['A → p1', 'B → p2'], [['B', C.ok]], 'oba znane → ramka tylko na p2 (C i D jej nie widzą)', 'A');
  b += panel(460, 260, '4. A → broadcast ff:ff:…', ['A → p1', 'B → p2'], [['B', C.vio], ['C', C.vio], ['D', C.vio]], 'broadcast zawsze na wszystkie porty w VLAN-ie (poza źródłowym)', 'A');
  files['ns02-02-switch-learning.svg'] = svg(W, H, 'Cztery kroki działania przełącznika: ramka od A do nieznanego B jest zalewana na wszystkie porty, odpowiedź B uczy przełącznik portu B, kolejne ramki idą tylko na właściwy port, a broadcast zawsze trafia do wszystkich', b);
}

// 3. Domeny kolizyjne i rozgloszeniowe
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Domeny kolizyjne i rozgłoszeniowe: hub, przełącznik, router', { size: 18, bold: true, anchor: 'middle' });
  const col = (x, title, dev, coll, bc, fills) => {
    let s = box(x, 56, 270, 280, C.card, C.line, 10) + text(x + 135, 80, title, { anchor: 'middle', bold: true });
    s += node(x + 135, 150, dev, { square: true, w: 34, fill: C.netFill, stroke: C.net, color: C.net });
    [[x + 50, 230], [x + 105, 250], [x + 165, 250], [x + 220, 230]].forEach(([hx, hy], i) => { s += line(hx, hy - 14, x + 135, 166, { color: C.line }) + `<circle cx="${hx}" cy="${hy}" r="15" fill="${fills[i]}" stroke="${C.ink}"/>`; });
    s += text(x + 135, 296, `kolizyjne: ${coll}`, { anchor: 'middle', size: 13, bold: true, fill: C.no });
    s += text(x + 135, 318, `rozgłoszeniowe: ${bc}`, { anchor: 'middle', size: 13, bold: true, fill: C.vio });
    return s;
  };
  b += col(20, 'Hub (L1)', 'HUB', '1 (wszyscy)', '1', [C.noFill, C.noFill, C.noFill, C.noFill]);
  b += col(315, 'Przełącznik (L2)', 'SW', '4 (każdy port)', '1 (na VLAN)', [C.okFill, C.hostFill, C.vioFill, C.netFill]);
  b += col(610, 'Router (L3), 2 interfejsy', 'R', 'osobne per port', '2 (każdy interfejs)', [C.okFill, C.okFill, C.vioFill, C.vioFill]);
  b += text(W / 2, 362, 'Przełącznik dzieli domeny kolizyjne, ale przepuszcza broadcast; router (lub VLAN) dzieli domeny rozgłoszeniowe.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-02-domains.svg'] = svg(W, H, 'Trzy panele: hub tworzy jedną domenę kolizyjną i jedną rozgłoszeniową, przełącznik — osobną domenę kolizyjną na każdy port i jedną rozgłoszeniową na VLAN, router — osobną domenę rozgłoszeniową na każdy interfejs', b);
}

// 4. Lab: bridge Linux
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Guided Lab: bridge Linuksa jako przełącznik dla trzech namespace’ów', { size: 17, bold: true, anchor: 'middle' });
  b += box(300, 60, 300, 70, C.netFill, C.net, 10) + text(450, 90, 'br0 (bridge = przełącznik)', { anchor: 'middle', bold: true, fill: C.net }) + text(450, 112, 'bridge fdb show br br0', { anchor: 'middle', size: 12, mono: true });
  [['ns1', 140, '10.0.0.1'], ['ns2', 450, '10.0.0.2'], ['ns3', 760, '10.0.0.3']].forEach(([n, x, ip]) => {
    b += line(x, 220, 450, 130, { color: C.net }) + box(x - 80, 220, 160, 70, C.card, C.ink, 8) + text(x, 248, n, { anchor: 'middle', bold: true }) + text(x, 268, `${ip}/24`, { anchor: 'middle', size: 12, mono: true });
    b += text((x + 450) / 2 + (x < 450 ? -20 : x > 450 ? 20 : 10), 178, `veth-${n}`, { anchor: 'middle', size: 11, fill: C.muted });
  });
  b += box(40, 310, 820, 54, C.bg, C.line, 8);
  b += text(56, 332, 'fdb po ping ns1 → ns2:  aa:bb:cc:00:00:01 dev veth-ns1-br master br0', { size: 12, mono: true });
  b += text(56, 352, '                        aa:bb:cc:00:00:02 dev veth-ns2-br master br0   (ns3 jeszcze nieznany)', { size: 12, mono: true });
  files['ns02-02-bridge-lab.svg'] = svg(W, H, 'Bridge br0 łączący trzy przestrzenie nazw sieci ns1, ns2 i ns3 przez pary veth; pod spodem tablica FDB po pingu z ns1 do ns2 zawierająca tylko dwa nauczone adresy', b);
}

writeSvgs(process.argv[2], files);
