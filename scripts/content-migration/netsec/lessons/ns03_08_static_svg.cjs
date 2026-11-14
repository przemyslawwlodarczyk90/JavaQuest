// Ilustracje do lekcji _ns_03/08_StaticRoutingAndFloatingRoutes.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_08_static_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { AD, rib, fmt, MIXED } = require('./ns03_08_static_model.cjs');
const files = {};

// 1. Dystans administracyjny
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Dystans administracyjny (Cisco): komu router bardziej wierzy?', { size: 18, bold: true, anchor: 'middle' });
  AD.forEach(([n, v], i) => {
    const y = 52 + i * 30, w = 40 + v * 1.6;
    b += box(60, y, w, 24, i === 0 ? C.okFill : v >= 200 ? C.noFill : C.netFill, i === 0 ? C.ok : v >= 200 ? C.no : C.net, 3);
    b += text(70 + w, y + 17, `${v}  ${n}`, { size: 13, bold: v <= 1 });
  });
  const r = rib(MIXED);
  b += box(520, 60, 340, 180, C.card, C.line, 8) + text(536, 84, 'Ta sama sieć z dwóch źródeł:', { bold: true, size: 13 });
  b += text(536, 108, 'OSPF  10.50.0.0/16 [110/20] via 10.0.0.2', { mono: true, size: 11 });
  b += text(536, 128, 'static 10.50.0.0/16 [1/0] via 10.0.0.6', { mono: true, size: 11 });
  b += text(536, 154, `→ w RIB: ${fmt(r['10.50.0.0/16']).split(' — ')[0]}`, { mono: true, size: 11, bold: true, fill: C.ok });
  b += text(536, 184, 'Najpierw AD (niższy wygrywa), metryka', { size: 12 });
  b += text(536, 204, 'porównywana tylko w obrębie protokołu.', { size: 12 });
  b += text(536, 224, 'Prefiks (LPM) i tak jest ważniejszy od AD.', { size: 12, bold: true, fill: C.no });
  b += text(60, 368, 'Kolejność: 1) najdłuższy prefiks (w tablicy przekazywania), 2) przy tym samym prefiksie z różnych źródeł — najniższy AD,', { size: 12 });
  b += text(60, 390, '3) w obrębie jednego protokołu — najniższa metryka. AD 255 = trasa nieużywana. Linux (jądro) nie ma AD — ma metrykę;', { size: 12 });
  b += text(60, 412, 'AD istnieje w demonach routingu (FRR zebra: „distance”) i w routerach.', { size: 12 });
  files['ns03-08-ad.svg'] = svg(W, H, 'Wykres dystansów administracyjnych: connected 0, static 1, eBGP 20, EIGRP 90, OSPF 110, IS-IS 115, RIP 120, EIGRP zewnętrzny 170, iBGP 200, 255 nieużywana. Przykład: ta sama sieć 10.50.0.0/16 z OSPF (AD 110) i statyczna (AD 1) — do RIB trafia statyczna. Kolejność: najdłuższy prefiks, potem AD, potem metryka.', b);
}

// 2. Trasa plywajaca
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Guided Lab: trasa główna i pływająca (AD 250) — przed i po awarii łącza', { size: 17, bold: true, anchor: 'middle' });
  const draw = (x0, title, down) => {
    let s = text(x0 + 205, 62, title, { anchor: 'middle', bold: true, size: 14, fill: down ? C.no : C.ok });
    const n = { r1: [x0 + 60, 200], r2: [x0 + 205, 110], r3: [x0 + 205, 290], srv: [x0 + 350, 200] };
    const link = (a, b2, col, w, dash) => line(n[a][0], n[a][1], n[b2][0], n[b2][1], { color: col, w, dash });
    s += link('r1', 'r2', down ? C.no : C.ok, down ? 2 : 4, down) + link('r1', 'r3', down ? C.ok : C.line, down ? 4 : 2, !down) + link('r2', 'srv', down ? C.line : C.ok, down ? 2 : 4) + link('r3', 'srv', down ? C.ok : C.line, down ? 4 : 2);
    if (down) s += text((n.r1[0] + n.r2[0]) / 2 - 6, (n.r1[1] + n.r2[1]) / 2 + 6, '✕', { size: 22, bold: true, fill: C.no });
    for (const [k, [x, y]] of Object.entries(n)) s += box(x - 38, y - 20, 76, 40, k === 'srv' ? C.vioFill : C.netFill, k === 'srv' ? C.vio : C.net, 8) + text(x, y + 5, k, { anchor: 'middle', bold: true });
    s += text(x0 + 350, 236, '198.51.100.0/24', { anchor: 'middle', size: 11, mono: true });
    s += box(x0, 330, 410, 76, C.card, C.line, 8);
    s += text(x0 + 12, 352, 'r1# show ip route 198.51.100.0/24', { mono: true, size: 11 });
    s += text(x0 + 12, 374, down ? 'S>* 198.51.100.0/24 [250/0] via 10.0.13.2, eth-r3' : 'S>* 198.51.100.0/24 [1/0] via 10.0.12.2, eth-r2', { mono: true, size: 11, bold: true, fill: C.ok });
    s += text(x0 + 12, 394, down ? '(trasa główna zniknęła — interfejs DOWN)' : 'S   198.51.100.0/24 [250/0] via 10.0.13.2 (inactive)', { mono: true, size: 11, fill: C.muted });
    return s;
  };
  b += draw(30, 'Normalnie: trasa główna AD 1 przez r2', false) + draw(470, 'Po awarii r1–r2: pływająca AD 250 przez r3', true);
  files['ns03-08-floating.svg'] = svg(W, H, 'Topologia labu: r1 połączony z r2 i r3, oba połączone z serwerem w sieci 198.51.100.0/24. Normalnie w RIB r1 jest trasa statyczna AD 1 przez r2, a trasa przez r3 z AD 250 czeka nieaktywna. Po wyłączeniu łącza r1–r2 trasa główna znika i instalowana jest pływająca przez r3.', b);
}

// 3. Next hop vs interfejs
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Trasa statyczna: następny skok, interfejs czy oba?', { size: 18, bold: true, anchor: 'middle' });
  const rows = [
    ['ip route 10.9.0.0 255.255.0.0 10.0.12.2', 'następny skok', 'rekurencyjne wyszukanie: jak dojść do 10.0.12.2? (connected). Ethernet: ARP o 10.0.12.2. Najczęstszy wybór.', C.ok, C.okFill],
    ['ip route 10.9.0.0 255.255.0.0 Gi0/1', 'tylko interfejs', 'na Ethernecie router traktuje cel jak „na łączu”: ARP o KAŻDY adres docelowy → proxy ARP sąsiada, wielkie tablice ARP. OK tylko na łączach P2P (serial, tunel).', C.no, C.noFill],
    ['ip route 10.9.0.0 255.255.0.0 Gi0/1 10.0.12.2', 'w pełni określona', 'interfejs + następny skok: brak rekurencji, trasa znika, gdy interfejs DOWN; wymagana dla next hop link-local w IPv6.', C.net, C.netFill],
  ];
  rows.forEach(([cmd, k, t, col, fill], i) => {
    const y = 56 + i * 104;
    b += box(40, y, 820, 94, fill, col, 8) + text(56, y + 24, cmd, { mono: true, size: 13, bold: true }) + text(844, y + 24, k, { anchor: 'end', bold: true, fill: col, size: 13 });
    const words = t.split(' '); let lineTxt = '', ly = y + 50;
    for (const w of words) { if ((lineTxt + ' ' + w).length > 110) { b += text(56, ly, lineTxt.trim(), { size: 12 }); lineTxt = ''; ly += 20; } lineTxt += ' ' + w; }
    b += text(56, ly, lineTxt.trim(), { size: 12 });
  });
  b += text(40, 388, 'IPv6: ipv6 route ::/0 Gi0/0 FE80::1 (link-local wymaga interfejsu) · ipv6 route 2001:db8:9::/48 2001:db8:12::2', { mono: true, size: 12 });
  files['ns03-08-nexthop.svg'] = svg(W, H, 'Trzy formy trasy statycznej: z następnym skokiem (rekurencyjne wyszukanie i ARP o bramę), tylko z interfejsem (na Ethernecie ARP o każdy cel i zależność od proxy ARP, poprawne na łączach punkt-punkt) oraz w pełni określona (interfejs i następny skok, wymagana dla next hop link-local w IPv6).', b);
}

// 4. Routing miedzy VLAN-ami
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Routing między VLAN-ami: router-on-a-stick vs SVI na przełączniku L3', { size: 17, bold: true, anchor: 'middle' });
  // lewa
  b += box(40, 56, 400, 330, C.card, C.line, 10) + text(240, 80, 'Router-on-a-stick', { anchor: 'middle', bold: true, fill: C.host });
  b += box(170, 96, 140, 44, C.hostFill, C.host, 8) + text(240, 123, 'router', { anchor: 'middle', bold: true });
  b += line(240, 140, 240, 190, { w: 4, color: C.host }) + text(250, 170, 'trunk 802.1Q', { size: 11 });
  b += box(160, 190, 160, 44, C.netFill, C.net, 8) + text(240, 217, 'switch L2', { anchor: 'middle', bold: true });
  b += line(200, 234, 120, 280) + line(280, 234, 360, 280);
  b += box(60, 280, 120, 40, C.okFill, C.ok, 6) + text(120, 305, 'VLAN 10', { anchor: 'middle', size: 12, bold: true }) + box(300, 280, 120, 40, C.vioFill, C.vio, 6) + text(360, 305, 'VLAN 20', { anchor: 'middle', size: 12, bold: true });
  b += text(56, 344, 'Gi0/0.10: encapsulation dot1Q 10, 10.10.0.1/24', { mono: true, size: 11 });
  b += text(56, 362, 'Gi0/0.20: encapsulation dot1Q 20, 10.20.0.1/24', { mono: true, size: 11 });
  b += text(56, 380, 'Tani, ale ruch między VLAN-ami idzie dwa razy przez 1 łącze', { size: 11, fill: C.no });
  // prawa
  b += box(460, 56, 400, 330, C.card, C.line, 10) + text(660, 80, 'Przełącznik L3 (SVI)', { anchor: 'middle', bold: true, fill: C.net });
  b += box(560, 110, 200, 84, C.netFill, C.net, 8) + text(660, 136, 'switch L3', { anchor: 'middle', bold: true }) + text(660, 158, 'ip routing', { anchor: 'middle', mono: true, size: 11 }) + text(660, 178, 'Vlan10 .1 · Vlan20 .1', { anchor: 'middle', mono: true, size: 11 });
  b += line(600, 194, 540, 280) + line(720, 194, 780, 280);
  b += box(480, 280, 120, 40, C.okFill, C.ok, 6) + text(540, 305, 'VLAN 10', { anchor: 'middle', size: 12, bold: true }) + box(720, 280, 120, 40, C.vioFill, C.vio, 6) + text(780, 305, 'VLAN 20', { anchor: 'middle', size: 12, bold: true });
  b += text(476, 344, 'interface Vlan10: ip address 10.10.0.1 255.255.255.0', { mono: true, size: 11 });
  b += text(476, 362, 'interface Vlan20: ip address 10.20.0.1 255.255.255.0', { mono: true, size: 11 });
  b += text(476, 380, 'Routing sprzętowy z prędkością przełączania — standard w LAN', { size: 11, fill: C.ok });
  files['ns03-08-intervlan.svg'] = svg(W, H, 'Porównanie routingu między VLAN-ami: router-on-a-stick z podinterfejsami Gi0/0.10 i Gi0/0.20 na jednym łączu trunk 802.1Q do przełącznika L2, oraz przełącznik L3 z poleceniem ip routing i interfejsami SVI Vlan10 i Vlan20 pełniącymi rolę bram.', b);
}

writeSvgs(process.argv[2], files);
