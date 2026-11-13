// Ilustracje do lekcji _ns_02/01_EthernetFramesAndMtu.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_01_ethernet_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

const strip = (y, parts, X0, scale, title) => {
  let s = title ? text(X0, y - 10, title, { bold: true, size: 13 }) : '';
  let x = X0;
  parts.forEach(([t, n, col, fill, sub]) => {
    const w = Math.max(38, n * scale);
    s += box(x, y, w, 46, fill, col, 3) + text(x + w / 2, y + 20, t, { anchor: 'middle', bold: true, size: 11, fill: col }) + text(x + w / 2, y + 37, sub || `${n} B`, { anchor: 'middle', size: 11 });
    x += w;
  });
  return s;
};

// 1. Anatomia ramki
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Ramka Ethernet II na kablu', { size: 19, bold: true, anchor: 'middle' });
  b += strip(80, [['preamb.', 7, C.muted, '#ece7df'], ['SFD', 1, C.muted, '#ece7df', '1 B'], ['MAC dst', 6, C.host, C.hostFill], ['MAC src', 6, C.host, C.hostFill], ['Type', 2, C.host, C.hostFill], ['dane (payload)', 40, C.net, C.netFill, '46–1500 B'], ['FCS', 4, C.host, C.hostFill], ['IFG', 12, C.muted, '#ece7df']], 40, 9.4, '');
  b += text(330, 172, 'ramka: 64–1518 B (z FCS)', { anchor: 'middle', bold: true, size: 13, fill: C.host });
  b += text(330, 192, 'to widzi przełącznik i (bez FCS) Wireshark', { anchor: 'middle', size: 12, fill: C.muted });
  b += box(40, 220, 820, 160, C.card, C.line, 10);
  [['MAC docelowy / źródłowy', 'kto ma odebrać ramkę w tym segmencie i kto ją wysłał (6 bajtów, np. 02:42:ac:11:00:02)'], ['EtherType', 'co jest w środku: 0x0800 IPv4, 0x86DD IPv6, 0x0806 ARP, 0x8100 tag VLAN; wartość ≤ 1500 = długość (802.3)'], ['dane', 'pakiet warstwy wyżej; krótsze niż 46 B dopełniane zerami (padding) do minimalnej ramki 64 B'], ['FCS', 'CRC-32 liczony przez kartę; błędna suma = ramka odrzucona po cichu (licznik CRC errors)'], ['preambuła, SFD, IFG', 'warstwa fizyczna: synchronizacja zegara i przerwa między ramkami — nie trafiają do przechwycenia']].forEach(([k, d], i) => { b += text(56, 248 + i * 27, k, { bold: true, size: 12 }) + text(250, 248 + i * 27, d, { size: 12 }); });
  files['ns02-01-frame-anatomy.svg'] = svg(W, H, 'Pasek ramki Ethernet: preambuła 7 bajtów, SFD 1 bajt, MAC docelowy 6, MAC źródłowy 6, EtherType 2, dane 46–1500, FCS 4, przerwa międzyramkowa 12, z opisem roli każdego pola', b);
}

// 2. Tag VLAN i jumbo
{
  const W = 900, H = 330;
  let b = text(W / 2, 32, 'Warianty ramki: tag 802.1Q i jumbo frames', { size: 19, bold: true, anchor: 'middle' });
  b += strip(80, [['MAC dst', 6, C.host, C.hostFill], ['MAC src', 6, C.host, C.hostFill], ['Type', 4, C.host, C.hostFill, '2 B'], ['dane ≤ 1500 B', 46, C.net, C.netFill, 'pakiet IP'], ['FCS', 4, C.host, C.hostFill]], 40, 11, 'zwykła: maks. 1518 B');
  b += strip(180, [['MAC dst', 6, C.host, C.hostFill], ['MAC src', 6, C.host, C.hostFill], ['TPID', 5, C.vio, C.vioFill, '0x8100'], ['TCI', 5, C.vio, C.vioFill, 'VLAN ID'], ['Type', 4, C.host, C.hostFill, '2 B'], ['dane ≤ 1500 B', 36, C.net, C.netFill, 'pakiet IP'], ['FCS', 4, C.host, C.hostFill]], 40, 11, 'z tagiem VLAN: maks. 1522 B (+4 B: TPID 2 B, TCI = PCP + DEI + VLAN ID 2 B)');
  b += text(W / 2, 290, 'Jumbo frames: dane do ok. 9000 B (MTU 9000; w AWS w VPC 9001) — mniej ramek i przerwań, ale wszystkie urządzenia w drodze muszą je obsługiwać.', { anchor: 'middle', size: 12, bold: true });
  b += text(W / 2, 312, 'Tag VLAN nie zmienia MTU (1500 dla pakietu IP) — rośnie ramka, nie miejsce na dane.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns02-01-vlan-jumbo.svg'] = svg(W, H, 'Porównanie ramki zwykłej do 1518 bajtów i ramki z tagiem 802.1Q do 1522 bajtów: tag zawiera TPID 0x8100 oraz pola PCP, DEI i VLAN ID; adnotacja o jumbo frames do 9000 bajtów danych', b);
}

// 3. Narzut tuneli
{
  const W = 900, H = 430;
  let b = text(W / 2, 32, 'Każdy tunel zabiera miejsce: MTU wewnątrz przy łączu 1500', { size: 18, bold: true, anchor: 'middle' });
  const rows = [['bez tunelu', 0], ['PPPoE (DSL)', 8], ['GRE', 24], ['VXLAN (K8s overlay)', 50], ['IPsec ESP (tryb tunelowy, typowo)', 73], ['WireGuard (rezerwa na IPv6)', 80]];
  rows.forEach(([n, o], i) => {
    const y = 60 + i * 56, inner = 1500 - o;
    b += text(260, y + 26, n, { anchor: 'end', size: 13, bold: true });
    b += `<rect x="270" y="${y + 6}" width="${inner * 0.33}" height="30" rx="3" fill="${C.net}" opacity="0.8"/>`;
    if (o) b += `<rect x="${270 + inner * 0.33}" y="${y + 6}" width="${o * 1.2}" height="30" rx="3" fill="${C.no}" opacity="0.85"/>`;
    b += text(270 + inner * 0.33 / 2, y + 26, `MTU wewnątrz: ${inner}`, { anchor: 'middle', size: 12, fill: '#fff', bold: true });
    b += text(270 + inner * 0.33 + o * 1.2 + 10, y + 26, o ? `−${o} B` : "—", { size: 13, bold: true, fill: o ? C.no : C.muted });
  });
  b += text(W / 2, 404, 'Czerwony fragment (w powiększeniu) to nagłówki tunelu. Za duże pakiety bez działającego PMTUD giną po cichu.', { anchor: 'middle', size: 12 });
  files['ns02-01-tunnel-overhead.svg'] = svg(W, H, 'Paski MTU wewnątrz tunelu przy łączu 1500: bez tunelu 1500, PPPoE 1492, GRE 1476, VXLAN 1450, IPsec ESP około 1427, WireGuard 1420', b);
}

// 4. Szukanie MTU sciezki
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Szukanie MTU ścieżki: ping z flagą DF', { size: 19, bold: true, anchor: 'middle' });
  const nodes = [['laptop', 80], ['router domowy', 300], ['tunel VPN', 520], ['serwer', 780]];
  nodes.forEach(([n, x]) => { b += box(x - 70, 70, 140, 50, C.card, C.ink, 8) + text(x, 100, n, { anchor: 'middle', bold: true, size: 13 }); });
  b += text(190, 140, 'MTU 1500', { anchor: 'middle', size: 12, fill: C.ok, bold: true }) + text(410, 140, 'MTU 1500', { anchor: 'middle', size: 12, fill: C.ok, bold: true }) + text(650, 140, 'MTU 1420', { anchor: 'middle', size: 12, fill: C.no, bold: true });
  b += line(150, 95, 230, 95, { color: C.ink, w: 3 }) + line(370, 95, 450, 95, { color: C.ink, w: 3 }) + line(590, 95, 710, 95, { color: C.no, w: 3 });
  const tries = [['ping -M do -s 1472 serwer', '1472 + 8 (ICMP) + 20 (IP) = 1500 → „Frag needed and DF set (mtu = 1420)” lub cisza', C.no], ['ping -M do -s 1392 serwer', '1392 + 28 = 1420 → odpowiedź OK', C.ok], ['ping -M do -s 1393 serwer', '1393 + 28 = 1421 → za duży: MTU ścieżki = 1420', C.no]];
  tries.forEach(([c, r, col], i) => { const y = 180 + i * 56; b += box(40, y, 820, 46, C.card, col, 8) + text(56, y + 20, c, { mono: true, size: 13, bold: true }) + text(56, y + 38, r, { size: 12, fill: col }); });
  b += text(W / 2, 362, 'Windows: ping -f -l 1392 serwer · Linux: tracepath serwer (pokazuje pmtu na każdym skoku)', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns02-01-pmtu-discovery.svg'] = svg(W, H, 'Ścieżka laptop, router domowy, tunel VPN z MTU 1420 i serwer; trzy próby ping z flagą DF: 1472 bajty danych za duże, 1392 przechodzi, 1393 za duże — MTU ścieżki wynosi 1420', b);
}

writeSvgs(process.argv[2], files);
