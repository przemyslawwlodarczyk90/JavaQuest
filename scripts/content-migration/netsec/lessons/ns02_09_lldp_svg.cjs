// Ilustracje do lekcji _ns_02/09_LldpCdpAndL2Discovery.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_09_lldp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Wymiana LLDP
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'LLDP: każde urządzenie mówi sąsiadowi „kim jestem i na którym porcie”', { size: 17, bold: true, anchor: 'middle' });
  b += box(40, 70, 220, 100, C.netFill, C.net, 10) + text(150, 100, 'SW-WAW-ACC-01', { anchor: 'middle', bold: true, fill: C.net }) + text(150, 122, 'port Gi1/0/12', { anchor: 'middle', size: 12, mono: true }) + text(150, 144, 'Catalyst 9300, IOS XE 17.12', { anchor: 'middle', size: 11 });
  b += box(640, 70, 220, 100, C.hostFill, C.host, 10) + text(750, 100, 'srv-app-07', { anchor: 'middle', bold: true, fill: C.host }) + text(750, 122, 'port eno1', { anchor: 'middle', size: 12, mono: true }) + text(750, 144, 'Ubuntu 24.04, lldpd', { anchor: 'middle', size: 11 });
  b += line(262, 104, 638, 104, { arrow: true, w: 2, color: C.net }) + line(638, 136, 262, 136, { arrow: true, w: 2, color: C.host });
  b += text(450, 96, 'LLDPDU co 30 s → 01:80:c2:00:00:0e', { anchor: 'middle', size: 11, mono: true, fill: C.net });
  b += text(450, 156, 'LLDPDU co 30 s (TTL 120 s)', { anchor: 'middle', size: 11, mono: true, fill: C.host });
  b += box(40, 200, 400, 170, C.card, C.net, 8) + text(60, 224, 'serwer widzi (lldpctl):', { bold: true, size: 13, fill: C.net });
  ['SysName: SW-WAW-ACC-01', 'PortID: Gi1/0/12   PortDescr: srv-app-07', 'MgmtIP: 10.99.0.11', 'VLAN: 20 (pvid)   Capability: Bridge, Router', 'SysDescr: Cisco IOS XE 17.12 …'].forEach((l, i) => { b += text(60, 250 + i * 22, l, { mono: true, size: 12 }); });
  b += box(460, 200, 400, 170, C.card, C.host, 8) + text(480, 224, 'przełącznik widzi (show lldp neighbors):', { bold: true, size: 13, fill: C.host });
  ['Device ID: srv-app-07', 'Local Intf: Gi1/0/12   Port ID: eno1', 'Capability: Station', 'Hold-time: 120', 'System Description: Ubuntu 24.04 …'].forEach((l, i) => { b += text(480, 250 + i * 22, l, { mono: true, size: 12 }); });
  files['ns02-09-exchange.svg'] = svg(W, H, 'Przełącznik SW-WAW-ACC-01 port Gi1/0/12 i serwer srv-app-07 port eno1 wymieniają co 30 sekund komunikaty LLDP na adres 01:80:c2:00:00:0e z czasem życia 120 sekund. Serwer widzi nazwę przełącznika, port, adres zarządzania, VLAN i system; przełącznik widzi nazwę serwera, jego port, rolę stacji i opis systemu.', b);
}

// 2. Struktura TLV
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Ramka LLDP: lista TLV (typ · długość · wartość)', { size: 18, bold: true, anchor: 'middle' });
  const fr = [['MAC dst', 110, '01:80:c2:00:00:0e'], ['MAC src', 110, 'MAC portu'], ['EtherType', 90, '0x88CC'], ['TLV 1 Chassis ID', 130, 'obowiązkowe'], ['TLV 2 Port ID', 110, 'obowiązkowe'], ['TLV 3 TTL', 90, 'obowiązkowe'], ['TLV 4…127', 110, 'opcjonalne'], ['TLV 0 End', 70, '']];
  let x = 20;
  fr.forEach(([n, w, d], i) => { const fill = i >= 3 && i <= 5 ? C.okFill : i === 6 ? C.vioFill : C.card; b += box(x, 60, w - 3, 54, fill, C.ink, 4) + text(x + w / 2, 84, n, { anchor: 'middle', size: 11, bold: true }) + text(x + w / 2, 102, d, { anchor: 'middle', size: 10, fill: C.muted }); x += w; });
  b += box(120, 150, 660, 60, C.card, C.ink, 6);
  b += box(120, 150, 150, 60, C.hostFill, C.host, 4) + text(195, 175, 'typ', { anchor: 'middle', bold: true }) + text(195, 196, '7 bitów (0–127)', { anchor: 'middle', size: 11 });
  b += box(270, 150, 190, 60, C.netFill, C.net, 4) + text(365, 175, 'długość', { anchor: 'middle', bold: true }) + text(365, 196, '9 bitów (0–511 B)', { anchor: 'middle', size: 11 });
  b += text(620, 175, 'wartość', { anchor: 'middle', bold: true }) + text(620, 196, '„długość” bajtów', { anchor: 'middle', size: 11 });
  b += text(W / 2, 236, 'Nagłówek TLV = 2 bajty: (typ << 9) | długość', { anchor: 'middle', mono: true, size: 13, bold: true });
  b += box(40, 256, 820, 106, C.card, C.line, 8);
  b += text(60, 280, 'Przykład: System Name „SW-WAW-ACC-01” (13 B): typ 5 → (5 << 9) | 13 = 0x0A0D', { mono: true, size: 12 });
  b += text(60, 302, 'TTL 120 s (2 B): typ 3 → (3 << 9) | 2 = 0x0602, wartość 0x0078', { mono: true, size: 12 });
  b += text(60, 324, 'TLV 127 (organizacyjne): OUI 00-80-C2 (802.1: VLAN), 00-12-0F (802.3: MAC/PHY, PoE), 00-12-BB (LLDP-MED)', { size: 12 });
  b += text(60, 346, 'Ten sam format TLV zobaczysz w wielu protokołach (BGP, RADIUS, TLS) — warto go rozpoznawać.', { size: 12, fill: C.muted });
  files['ns02-09-tlv.svg'] = svg(W, H, 'Ramka LLDP: MAC docelowy 01:80:c2:00:00:0e, MAC źródłowy, EtherType 0x88CC, obowiązkowe TLV Chassis ID, Port ID i TTL, opcjonalne TLV 4–127 i TLV End. Nagłówek TLV ma 7 bitów typu i 9 bitów długości; przykład: System Name o długości 13 bajtów ma nagłówek 0x0A0D, TTL 120 sekund nagłówek 0x0602.', b);
}

// 3. Diagram z danych LLDP
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Z tabel sąsiedztwa do diagramu fizycznego', { size: 18, bold: true, anchor: 'middle' });
  b += box(30, 56, 330, 340, C.card, C.line, 8) + text(45, 80, 'show lldp neighbors (zebrane z 3 urządzeń)', { bold: true, size: 12 });
  const rows = [['CORE1 Te1/1/1', 'ACC1 Te1/1/1'], ['CORE1 Te1/1/2', 'ACC2 Te1/1/1'], ['ACC1 Gi1/0/5', 'srv-db-01 eno1'], ['ACC1 Gi1/0/6', 'srv-db-01 eno2'], ['ACC2 Gi1/0/9', 'ap-2p-03 eth0'], ['ACC2 Gi1/0/10', 'phone-1043'], ['ACC1 Gi1/0/48', 'ACC2 Gi1/0/48']];
  rows.forEach(([a, c], i) => { b += text(45, 108 + i * 34, a, { mono: true, size: 11 }) + text(170, 108 + i * 34, '↔', { size: 12 }) + text(190, 108 + i * 34, c, { mono: true, size: 11 }); });
  const n = (x, y, t, fill, col) => box(x - 60, y - 18, 120, 36, fill, col, 6) + text(x, y + 5, t, { anchor: 'middle', size: 12, bold: true, fill: col });
  b += line(630, 90, 500, 200, { w: 3 }) + line(630, 90, 760, 200, { w: 3 }) + line(500, 200, 760, 200, { w: 2, dash: true });
  b += line(500, 200, 440, 320, { color: C.host }) + line(500, 200, 540, 320, { color: C.host }) + line(760, 200, 700, 320, { color: C.ok }) + line(760, 200, 820, 320, { color: C.ok });
  b += n(630, 90, 'CORE1', C.okFill, C.ok) + n(500, 200, 'ACC1', C.netFill, C.net) + n(760, 200, 'ACC2', C.netFill, C.net);
  b += box(430, 302, 120, 36, C.hostFill, C.host, 6) + text(490, 325, 'srv-db-01 (×2)', { anchor: 'middle', size: 12, bold: true, fill: C.host });
  b += n(700, 320, 'ap-2p-03', C.vioFill, C.vio) + n(820, 320, 'phone-1043', C.card, C.ink);
  b += text(630, 222, 'Gi1/0/48 (STP alt?)', { anchor: 'middle', size: 10, fill: C.muted });
  b += text(640, 380, 'srv-db-01 ma dwa łącza do ACC1 → bond, ale bez redundancji przełącznika!', { anchor: 'middle', size: 12, bold: true, fill: C.no });
  files['ns02-09-diagram.svg'] = svg(W, H, 'Po lewej siedem par sąsiedztwa zebranych poleceniem show lldp neighbors z trzech przełączników; po prawej narysowany z nich diagram: CORE1 połączony z ACC1 i ACC2, łącze ACC1–ACC2, serwer srv-db-01 z dwoma łączami do ACC1, punkt dostępowy i telefon przy ACC2. Wniosek: serwer ma oba łącza do jednego przełącznika, więc brak redundancji przełącznika.', b);
}

// 4. Bezpieczenstwo
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'LLDP/CDP: gdzie włączyć, gdzie wyłączyć', { size: 18, bold: true, anchor: 'middle' });
  const cols = [
    ['WŁĄCZONE', C.ok, C.okFill, ['łącza między przełącznikami', 'porty do serwerów i hiperwizorów', 'porty do telefonów VoIP (LLDP-MED)', 'porty do punktów dostępowych', 'sieć zarządzania']],
    ['WYŁĄCZONE', C.no, C.noFill, ['interfejsy do Internetu / operatora', 'porty w strefie gości', 'porty w salach konferencyjnych', 'porty w DMZ do obcych systemów', 'nieużywane porty']],
  ];
  cols.forEach(([t, col, fill, items], i) => { const x = 40 + i * 420; b += box(x, 60, 400, 220, fill, col, 10) + text(x + 200, 88, t, { anchor: 'middle', bold: true, fill: col, size: 15 }); items.forEach((it, k) => { b += text(x + 24, 120 + k * 32, '• ' + it, { size: 13 }); }); });
  b += box(40, 296, 820, 70, C.card, C.line, 8);
  b += text(60, 320, 'Co ujawnia ramka LLDP/CDP każdemu w segmencie: nazwę i model urządzenia, wersję oprogramowania,', { size: 12 });
  b += text(60, 342, 'adres zarządzania, VLAN-y, numer portu — gotowa mapa i lista podatnych wersji dla napastnika.', { size: 12, bold: true, fill: C.no });
  files['ns02-09-security.svg'] = svg(W, H, 'Dwie kolumny: LLDP i CDP włączone na łączach między przełącznikami, portach do serwerów, telefonów VoIP, punktów dostępowych i w sieci zarządzania; wyłączone na interfejsach do Internetu, w strefie gości, salach konferencyjnych, DMZ i na nieużywanych portach. Ramka ujawnia nazwę, model, wersję oprogramowania, adres zarządzania i VLAN-y.', b);
}

writeSvgs(process.argv[2], files);
