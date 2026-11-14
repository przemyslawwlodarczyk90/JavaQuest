// Ilustracje do lekcji _ns_03/12_Ipv6InDepth.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_12_ipv6_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { full, compress, eui64, slaac, linkLocal, solicitedNode, multicastMac } = require('./ns03_12_ipv6_model.cjs');
const files = {};

// 1. Zapis i skracanie
{
  const W = 900, H = 380;
  const a = '2001:0db8:0000:0000:0008:0800:200c:417a';
  let b = text(W / 2, 30, 'Zapis IPv6: 128 bitów, 8 grup po 4 cyfry hex — i dwie reguły skracania (RFC 5952)', { size: 16, bold: true, anchor: 'middle' });
  const steps = [
    ['pełny zapis', a, C.ink],
    ['① usuń zera wiodące w każdej grupie', '2001:db8:0:0:8:800:200c:417a', C.net],
    ['② najdłuższy ciąg grup zerowych (min. 2) → :: (tylko RAZ)', compress(a), C.ok],
    ['z powrotem: rozwiń :: do brakujących grup', full(compress(a)), C.vio],
  ];
  steps.forEach(([h, v, col], i) => {
    const y = 56 + i * 64;
    b += box(40, y, 820, 54, C.card, col, 8) + text(56, y + 22, h, { size: 12, bold: true, fill: col }) + text(56, y + 44, v, { mono: true, size: 16, bold: true });
  });
  b += text(40, 330, 'Zasady RFC 5952: małe litery; :: zastępuje najdłuższy ciąg zer (przy remisie — pierwszy); nie skracaj pojedynczej grupy zerowej.', { size: 12 });
  b += text(40, 354, 'Porównuj adresy po normalizacji (Java: InetAddress, Python: ipaddress) — nigdy jako tekst. W URL: http://[2001:db8::1]:8080/', { size: 12, bold: true, fill: C.no });
  files['ns03-12-notation.svg'] = svg(W, H, 'Skracanie adresu IPv6 2001:0db8:0000:0000:0008:0800:200c:417a: usunięcie zer wiodących daje 2001:db8:0:0:8:800:200c:417a, zastąpienie najdłuższego ciągu zer przez :: daje 2001:db8::8:800:200c:417a, rozwinięcie przywraca pełny zapis. Małe litery, :: tylko raz, adres w URL w nawiasach kwadratowych.', b);
}

// 2. Naglowek
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Nagłówek IPv6: stałe 40 B, mniej pól niż IPv4, opcje w nagłówkach rozszerzających', { size: 16, bold: true, anchor: 'middle' });
  const x0 = 70, x1 = 870, bw = (x1 - x0) / 32;
  const rows = [
    [[0, 4, 'Version=6', C.net], [4, 12, 'Traffic Class', C.vio], [12, 32, 'Flow Label', C.vio]],
    [[0, 16, 'Payload Length', C.net], [16, 24, 'Next Header', C.ok], [24, 32, 'Hop Limit', C.no]],
    [[0, 32, 'Source Address (128 bitów = 4 wiersze)', C.host]],
    [[0, 32, 'Destination Address (128 bitów = 4 wiersze)', C.host]],
  ];
  const fills = { [C.net]: C.netFill, [C.vio]: C.vioFill, [C.ok]: C.okFill, [C.no]: C.noFill, [C.host]: C.hostFill };
  rows.forEach((row, r) => { const y = 52 + r * 50; for (const [s, e, n, col] of row) b += box(x0 + s * bw, y, (e - s) * bw, 44, fills[col], col, 3) + text(x0 + ((s + e) / 2) * bw, y + 27, n, { anchor: 'middle', bold: true, size: 13, fill: col }); });
  b += text(40, 270, 'Brak w IPv6: IHL, Identification/Flags/Offset (fragmentuje tylko nadawca — nagłówek Fragment), Header Checksum.', { size: 12 });
  b += text(40, 290, 'Next Header = numer następnego nagłówka: 6 TCP, 17 UDP, 58 ICMPv6, 0 Hop-by-Hop, 43 Routing, 44 Fragment, 60 Destination Opts.', { size: 12 });
  const chain = [['IPv6', 'NH=0', C.net], ['Hop-by-Hop', 'NH=43', C.vio], ['Routing', 'NH=44', C.vio], ['Fragment', 'NH=6', C.vio], ['TCP', 'dane', C.ok]];
  chain.forEach(([n, nh, col], i) => { const x = 50 + i * 170; b += box(x, 318, 150, 54, fills[col], col, 6) + text(x + 75, 342, n, { anchor: 'middle', bold: true, fill: col }) + text(x + 75, 362, nh, { anchor: 'middle', mono: true, size: 11 }); if (i < 4) b += line(x + 152, 345, x + 168, 345, { arrow: true }); });
  b += text(40, 400, 'Łańcuch nagłówków rozszerzających: firewalle/IDS muszą go przejść, by znaleźć porty — długie łańcuchy i RH0 (wycofany) to ryzyko;', { size: 12 });
  b += text(40, 420, 'wiele sieci odrzuca pakiety z nietypowymi extension headers (RFC 7872). Hop Limit = TTL, Flow Label = etykieta przepływu (ECMP).', { size: 12 });
  files['ns03-12-header.svg'] = svg(W, H, 'Nagłówek IPv6 o stałej długości 40 bajtów: Version, Traffic Class, Flow Label, Payload Length, Next Header, Hop Limit, 128-bitowe adresy źródłowy i docelowy. Brak pól fragmentacji i sumy kontrolnej. Łańcuch nagłówków rozszerzających: IPv6, Hop-by-Hop, Routing, Fragment, TCP — połączonych polem Next Header.', b);
}

// 3. EUI-64 i solicited-node
{
  const W = 900, H = 420, mac = '00:1a:2b:3c:4d:5e';
  let b = text(W / 2, 30, 'Identyfikator interfejsu z MAC (EUI-64), solicited-node multicast i MAC 33:33', { size: 16, bold: true, anchor: 'middle' });
  const rows = [
    ['MAC', mac, C.ink],
    ['podziel na pół i wstaw ff:fe', '00:1a:2b:ff:fe:3c:4d:5e', C.net],
    ['odwróć 7. bit pierwszego bajtu (U/L): 00 → 02', '02:1a:2b:ff:fe:3c:4d:5e', C.vio],
    ['identyfikator interfejsu (64 bity)', eui64(mac), C.ok],
    ['adres link-local', `fe80::${eui64(mac)}`, C.ok],
    ['adres SLAAC w prefiksie 2001:db8:abcd:12::/64', slaac('2001:db8:abcd:12::', mac), C.ok],
  ];
  rows.forEach(([h, v, col], i) => { const y = 52 + i * 40; b += text(56, y + 20, h, { size: 12, fill: C.muted }) + text(470, y + 20, v, { mono: true, size: 14, bold: true, fill: col }); if (i) b += line(40, y, 860, y, { color: C.line, w: 1 }); });
  b += box(40, 300, 820, 104, C.card, C.line, 8);
  const ll = linkLocal(mac);
  b += text(56, 324, `Solicited-node multicast (ND zamiast ARP-broadcastu): ff02::1:ff + ostatnie 24 bity adresu → ${solicitedNode(ll)}`, { size: 12 });
  b += text(56, 348, `MAC dla grupy multicast IPv6: 33:33 + ostatnie 32 bity → ${multicastMac(solicitedNode(ll))}`, { size: 12 });
  b += text(56, 374, 'EUI-64 ujawnia MAC (śledzenie urządzenia między sieciami) → dziś domyślnie: stabilne adresy prywatne (RFC 7217)', { size: 12, bold: true, fill: C.no });
  b += text(56, 394, 'i adresy tymczasowe (RFC 8981) do połączeń wychodzących.', { size: 12, bold: true, fill: C.no });
  files['ns03-12-eui64.svg'] = svg(W, H, 'Konwersja MAC 00:1a:2b:3c:4d:5e na identyfikator EUI-64: wstawienie ff:fe w środek i odwrócenie bitu U/L daje 21a:2bff:fe3c:4d5e; adres link-local fe80::21a:2bff:fe3c:4d5e i adres SLAAC 2001:db8:abcd:12:21a:2bff:fe3c:4d5e. Solicited-node multicast ff02::1:ff3c:4d5e i MAC 33:33:ff:3c:4d:5e. EUI-64 ujawnia MAC, dlatego stosuje się adresy prywatne i tymczasowe.', b);
}

// 4. Adresy jednego hosta
{
  const W = 900, H = 420;
  let b = text(W / 2, 30, 'Jeden interfejs, wiele adresów IPv6: wynik ip -6 addr na laptopie', { size: 17, bold: true, anchor: 'middle' });
  const rows = [
    ['inet6 2001:db8:abcd:12:9c4e:1f2a:77b0:3d11/64 scope global temporary dynamic', 'tymczasowy (RFC 8981) — źródło połączeń wychodzących, zmienia się co dobę', C.ok, C.okFill],
    ['inet6 2001:db8:abcd:12:5f1:2a7c:e3d9:8b44/64 scope global dynamic mngtmpaddr', 'stabilny (RFC 7217) — z SLAAC, stały w tej sieci, do połączeń przychodzących', C.ok, C.okFill],
    ['inet6 fd5a:3c91:7e04:12::20/64 scope global', 'ULA — adres wewnętrzny firmy (odpowiednik RFC 1918)', C.vio, C.vioFill],
    ['inet6 fe80::5f1:2a7c:e3d9:8b44/64 scope link', 'link-local — zawsze; ND, RA, brama domyślna (fe80::1)', C.net, C.netFill],
  ];
  rows.forEach(([cmd, desc, col, fill], i) => { const y = 52 + i * 70; b += box(40, y, 820, 62, fill, col, 8) + text(56, y + 24, cmd, { mono: true, size: 12, bold: true }) + text(56, y + 46, desc, { size: 12, fill: col }); });
  b += box(40, 340, 820, 66, C.card, C.line, 8);
  b += text(56, 364, 'Grupy multicast interfejsu (ip -6 maddr): ff02::1 (wszystkie węzły), ff02::1:ff… (solicited-node dla każdego adresu),', { size: 12 });
  b += text(56, 386, 'routery dodatkowo ff02::2. Brama domyślna: default via fe80::1 dev wlan0 proto ra — z Router Advertisement.', { size: 12 });
  files['ns03-12-host.svg'] = svg(W, H, 'Wynik ip -6 addr: adres globalny tymczasowy do połączeń wychodzących, adres globalny stabilny z SLAAC, adres ULA fd5a:3c91:7e04:12::20 i adres link-local fe80::. Interfejs należy do grup multicast ff02::1 i solicited-node, brama domyślna fe80::1 z Router Advertisement.', b);
}

writeSvgs(process.argv[2], files);
