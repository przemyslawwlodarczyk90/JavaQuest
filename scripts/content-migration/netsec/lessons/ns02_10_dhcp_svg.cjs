// Ilustracje do lekcji _ns_02/10_DhcpAndSlaac.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_10_dhcp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { eui64 } = require('./ns02_10_dhcp_model.cjs');
const files = {};

// 1. DORA
{
  const W = 900, H = 430;
  let b = text(W / 2, 32, 'DORA: Discover → Offer → Request → Ack', { size: 19, bold: true, anchor: 'middle' });
  b += box(80, 56, 200, 50, C.hostFill, C.host, 8) + text(180, 86, 'klient (0.0.0.0)', { anchor: 'middle', bold: true, fill: C.host });
  b += box(620, 56, 200, 50, C.netFill, C.net, 8) + text(720, 86, 'serwer 10.20.0.2', { anchor: 'middle', bold: true, fill: C.net });
  b += line(180, 108, 180, 400, { dash: true, color: C.line }) + line(720, 108, 720, 400, { dash: true, color: C.line });
  const msgs = [
    [140, true, 'DHCPDISCOVER', '0.0.0.0:68 → 255.255.255.255:67 · „szukam serwera”', C.host],
    [210, false, 'DHCPOFFER', 'proponuję 10.20.0.57/24, brama .1, DNS, lease 8 h', C.net],
    [280, true, 'DHCPREQUEST', 'broadcast: „biorę ofertę serwera 10.20.0.2”', C.host],
    [350, false, 'DHCPACK', 'potwierdzam — adres Twój do T1/T2/końca dzierżawy', C.ok],
  ];
  msgs.forEach(([y, right, n, d, col]) => {
    b += right ? line(184, y, 716, y, { arrow: true, w: 2, color: col }) : line(716, y, 184, y, { arrow: true, w: 2, color: col });
    b += text(450, y - 10, n, { anchor: 'middle', bold: true, fill: col, size: 14 }) + text(450, y + 18, d, { anchor: 'middle', size: 11, mono: true });
  });
  b += text(W / 2, 416, 'Request jest broadcastem, by inne serwery wiedziały, że ich oferta odpada. Potem klient sprawdza adres (ARP probe/DAD).', { anchor: 'middle', size: 12, bold: true });
  files['ns02-10-dora.svg'] = svg(W, H, 'Wymiana DORA między klientem bez adresu a serwerem DHCP 10.20.0.2: DHCPDISCOVER jako broadcast z portu 68 na port 67, DHCPOFFER z propozycją adresu 10.20.0.57/24, bramy, DNS i dzierżawy 8 godzin, DHCPREQUEST jako broadcast potwierdzający wybór serwera i DHCPACK zatwierdzający dzierżawę.', b);
}

// 2. Timery dzierzawy
{
  const W = 900, H = 330;
  let b = text(W / 2, 32, 'Cykl życia dzierżawy: T1 = 50%, T2 = 87,5%', { size: 18, bold: true, anchor: 'middle' });
  const x0 = 60, x1 = 840, y = 120;
  b += box(x0, y - 20, (x1 - x0) * 0.5, 40, C.okFill, C.ok, 4) + text(x0 + (x1 - x0) * 0.25, y + 5, 'adres używany spokojnie', { anchor: 'middle', size: 12, bold: true, fill: C.ok });
  b += box(x0 + (x1 - x0) * 0.5, y - 20, (x1 - x0) * 0.375, 40, C.hostFill, C.host, 4) + text(x0 + (x1 - x0) * 0.6875, y + 5, 'RENEWING: unicast do serwera', { anchor: 'middle', size: 12, bold: true, fill: C.host });
  b += box(x0 + (x1 - x0) * 0.875, y - 20, (x1 - x0) * 0.125, 40, C.noFill, C.no, 4) + text(x0 + (x1 - x0) * 0.9375, y + 5, 'REBINDING', { anchor: 'middle', size: 11, bold: true, fill: C.no });
  [[0, '0 (ACK)'], [0.5, 'T1 = 4 h'], [0.875, 'T2 = 7 h'], [1, '8 h — koniec']].forEach(([f, l]) => { const x = x0 + (x1 - x0) * f; b += line(x, y + 22, x, y + 40, { color: C.ink }) + text(x, y + 58, l, { anchor: 'middle', size: 12, bold: true }); });
  b += box(60, 200, 780, 110, C.card, C.line, 8);
  b += text(80, 226, 'T1: klient wysyła DHCPREQUEST unicastem do serwera, który dał adres — zwykle dostaje ACK i licznik startuje od nowa.', { size: 12 });
  b += text(80, 250, 'T2: serwer milczy → REQUEST broadcastem do dowolnego serwera (rebinding).', { size: 12 });
  b += text(80, 274, 'Koniec dzierżawy bez odpowiedzi: klient MUSI przestać używać adresu → zrywa połączenia, wraca do DISCOVER.', { size: 12, bold: true, fill: C.no });
  b += text(80, 298, 'Dla aplikacji: awaria serwera DHCP nie wyłącza sieci od razu — dopiero po końcu dzierżaw.', { size: 12, fill: C.muted });
  files['ns02-10-lease.svg'] = svg(W, H, 'Oś czasu dzierżawy 8-godzinnej: do T1 (4 h, 50%) adres używany spokojnie, od T1 do T2 (7 h, 87,5%) klient odnawia dzierżawę unicastem u swojego serwera, od T2 do końca próbuje dowolnego serwera broadcastem; po końcu dzierżawy bez odpowiedzi musi przestać używać adresu.', b);
}

// 3. Relay
{
  const W = 900, H = 360;
  let b = text(W / 2, 32, 'DHCP relay: broadcast nie przejdzie przez router, więc router go przekazuje', { size: 17, bold: true, anchor: 'middle' });
  b += box(40, 120, 170, 60, C.hostFill, C.host, 8) + text(125, 146, 'klient', { anchor: 'middle', bold: true, fill: C.host }) + text(125, 166, 'VLAN 10', { anchor: 'middle', size: 11 });
  b += box(340, 110, 220, 80, C.vioFill, C.vio, 8) + text(450, 138, 'router / L3 (relay)', { anchor: 'middle', bold: true, fill: C.vio }) + text(450, 158, 'Vlan10: 10.10.0.1', { anchor: 'middle', size: 11, mono: true }) + text(450, 176, 'ip helper-address 10.20.0.2', { anchor: 'middle', size: 11, mono: true });
  b += box(690, 120, 170, 60, C.netFill, C.net, 8) + text(775, 146, 'serwer DHCP', { anchor: 'middle', bold: true, fill: C.net }) + text(775, 166, '10.20.0.2 (VLAN 20)', { anchor: 'middle', size: 11 });
  b += line(212, 140, 338, 140, { arrow: true, w: 2, color: C.host }) + text(275, 130, 'broadcast', { anchor: 'middle', size: 11 });
  b += line(562, 140, 688, 140, { arrow: true, w: 2, color: C.vio }) + text(625, 130, 'unicast', { anchor: 'middle', size: 11 });
  b += line(688, 165, 562, 165, { arrow: true, w: 2, color: C.net }) + line(338, 165, 212, 165, { arrow: true, w: 2, color: C.net });
  b += box(60, 220, 780, 120, C.card, C.line, 8);
  b += text(80, 246, 'giaddr = 10.10.0.1 → serwer wybiera pulę 10.10.0.0/24 (z której podsieci przyszło żądanie).', { size: 12, bold: true });
  b += text(80, 270, 'Opcja 82 (Relay Agent Information): przełącznik/relay dopisuje port i VLAN klienta — pula, polityka, ślad audytowy.', { size: 12 });
  b += text(80, 294, 'Jeden serwer (lub para z failoverem) obsługuje setki VLAN-ów; firewall musi przepuszczać UDP 67/68 między relay a serwerem.', { size: 12 });
  b += text(80, 318, 'IPv6: DHCPv6 relay (ipv6 dhcp relay destination), multicast ff02::1:2 na łączu klienta.', { size: 12, fill: C.muted });
  files['ns02-10-relay.svg'] = svg(W, H, 'Klient w VLAN 10 wysyła broadcast DHCP do routera z funkcją relay (ip helper-address 10.20.0.2), który przekazuje go unicastem do serwera DHCP w VLAN 20 i zwraca odpowiedź. Pole giaddr 10.10.0.1 wskazuje serwerowi pulę, opcja 82 dodaje port i VLAN klienta.', b);
}

// 4. SLAAC i EUI-64
{
  const W = 900, H = 420;
  const mac = '52:54:00:12:34:56';
  let b = text(W / 2, 32, 'IPv6 bez DHCP: Router Advertisement + SLAAC', { size: 18, bold: true, anchor: 'middle' });
  b += box(40, 56, 820, 110, C.card, C.vio, 8) + text(60, 80, 'RA od routera (ICMPv6 134, ff02::1): prefiks 2001:db8:10::/64, flagi M, O, A, brama = adres link-local routera', { size: 12, bold: true, fill: C.vio });
  const fl = [['A=1', 'host sam tworzy adres (SLAAC)'], ['M=1', 'adresy z DHCPv6 (stanowy)'], ['O=1', 'inne informacje (DNS) z DHCPv6'], ['RDNSS', 'DNS wprost w RA (RFC 8106)']];
  fl.forEach(([f, d], i) => { const x = 60 + i * 200; b += box(x, 96, 190, 56, C.vioFill, C.vio, 6) + text(x + 95, 118, f, { anchor: 'middle', bold: true, mono: true }) + text(x + 95, 138, d, { anchor: 'middle', size: 10 }); });
  b += text(60, 200, `EUI-64 z MAC ${mac}:`, { bold: true, size: 13 });
  const steps = [['MAC', '52:54:00 | 12:34:56'], ['wstaw ff:fe w środek', '52:54:00:ff:fe:12:34:56'], ['odwróć bit U/L (0x02 w 1. bajcie)', '50:54:00:ff:fe:12:34:56'], ['identyfikator interfejsu', eui64(mac)]];
  steps.forEach(([n, v], i) => { b += box(60 + i * 200, 214, 190, 64, i === 3 ? C.okFill : C.card, i === 3 ? C.ok : C.line, 6) + text(155 + i * 200, 238, n, { anchor: 'middle', size: 11 }) + text(155 + i * 200, 262, v, { anchor: 'middle', mono: true, size: 11, bold: true }); });
  b += box(40, 296, 820, 110, C.okFill, C.ok, 8);
  b += text(60, 322, `adres globalny: 2001:db8:10::${eui64(mac)}   link-local: fe80::${eui64(mac)}`, { mono: true, size: 13, bold: true });
  b += text(60, 348, 'Prywatność: EUI-64 zdradza MAC (śledzenie urządzenia) → systemy używają adresów losowych (RFC 8981 temporary, RFC 7217 stable).', { size: 12 });
  b += text(60, 372, 'Przed użyciem adresu: DAD (Duplicate Address Detection) przez NS do adresu solicited-node — lekcja 03.', { size: 12 });
  b += text(60, 394, 'Bezpieczeństwo: fałszywe RA (rogue RA) przejmują ruch IPv6 → RA Guard na przełącznikach.', { size: 12, bold: true, fill: C.no });
  files['ns02-10-slaac.svg'] = svg(W, H, 'Router Advertisement z prefiksem 2001:db8:10::/64 i flagami A (SLAAC), M (adresy z DHCPv6), O (inne informacje z DHCPv6) oraz RDNSS. Obliczenie EUI-64 z MAC 52:54:00:12:34:56: wstawienie ff:fe i odwrócenie bitu U/L daje identyfikator 5054:ff:fe12:3456 i adres 2001:db8:10::5054:ff:fe12:3456. Uwagi o prywatności, DAD i RA Guard.', b);
}

writeSvgs(process.argv[2], files);
