// Ilustracje do lekcji _ns_03/09_NatPatAndPortForwarding.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_09_nat_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { pat, FLOWS, ipsNeeded, steadyState } = require('./ns03_09_nat_model.cjs');
const files = {};

// 1. SNAT i DNAT
{
  const W = 900, H = 460;
  let b = text(W / 2, 30, 'SNAT (wyjście) i DNAT (port forwarding): co router zmienia w nagłówkach', { size: 17, bold: true, anchor: 'middle' });
  const hdr = (x, y, src, dst, col, fill, changed) => box(x, y, 240, 54, fill, col, 6) + text(x + 12, y + 22, `src ${src}`, { mono: true, size: 12, bold: changed === 'src', fill: changed === 'src' ? C.no : C.ink }) + text(x + 12, y + 42, `dst ${dst}`, { mono: true, size: 12, bold: changed === 'dst', fill: changed === 'dst' ? C.no : C.ink });
  b += text(40, 70, 'SNAT / PAT — laptop łączy się z serwerem w Internecie', { bold: true, fill: C.net, size: 14 });
  b += hdr(40, 84, '192.168.1.20:51000', '93.184.216.34:443', C.net, C.netFill);
  b += box(330, 84, 200, 54, C.hostFill, C.host, 8) + text(430, 108, 'router NAT', { anchor: 'middle', bold: true }) + text(430, 128, 'conntrack zapamiętuje', { anchor: 'middle', size: 11 });
  b += hdr(620, 84, '203.0.113.5:51000', '93.184.216.34:443', C.net, C.netFill, 'src');
  b += line(282, 111, 328, 111, { arrow: true, w: 2 }) + line(532, 111, 618, 111, { arrow: true, w: 2 });
  b += text(860, 158, 'odpowiedź na :51000 → router odwraca translację', { size: 11, anchor: 'end' });
  b += text(40, 206, 'DNAT / port forwarding — klient z Internetu łączy się z usługą w LAN', { bold: true, fill: C.vio, size: 14 });
  b += hdr(40, 220, '198.51.100.9:60111', '203.0.113.5:8443', C.vio, C.vioFill);
  b += box(330, 220, 200, 54, C.hostFill, C.host, 8) + text(430, 244, 'router NAT', { anchor: 'middle', bold: true }) + text(430, 264, 'reguła: :8443 → .30:443', { anchor: 'middle', size: 11 });
  b += hdr(620, 220, '198.51.100.9:60111', '192.168.1.30:443', C.vio, C.vioFill, 'dst');
  b += line(282, 247, 328, 247, { arrow: true, w: 2 }) + line(532, 247, 618, 247, { arrow: true, w: 2 });
  b += box(40, 304, 820, 140, C.card, C.line, 8);
  b += text(60, 330, 'Masquerade = SNAT na adres interfejsu wyjściowego (dynamiczny adres, np. z DHCP operatora).', { size: 13 });
  b += text(60, 354, 'Router przelicza sumy kontrolne IP i TCP/UDP (zmienił adres/port) i zapisuje przepływ w tablicy conntrack.', { size: 13 });
  b += text(60, 378, 'Serwer widzi tylko adres publiczny routera — nie zna 192.168.1.20 (dlatego za NAT/proxy „IP klienta” to adres NAT).', { size: 13 });
  b += text(60, 402, 'iptables -t nat -A POSTROUTING -o wan0 -j MASQUERADE', { size: 11, mono: true });
  b += text(60, 422, 'iptables -t nat -A PREROUTING -i wan0 -p tcp --dport 8443 -j DNAT --to-destination 192.168.1.30:443', { size: 11, mono: true });
  files['ns03-09-snat-dnat.svg'] = svg(W, H, 'SNAT: pakiet 192.168.1.20:51000 do 93.184.216.34:443 po przejściu przez router ma źródło 203.0.113.5:51000, a odpowiedź jest tłumaczona z powrotem dzięki conntrack. DNAT: pakiet z 198.51.100.9 do 203.0.113.5:8443 jest przekierowany na 192.168.1.30:443. Masquerade to SNAT na adres interfejsu.', b);
}

// 2. Tablica PAT
{
  const W = 900, H = 360;
  const t = pat(FLOWS, ['203.0.113.5']);
  let b = text(W / 2, 30, 'PAT (NAT overload): wiele hostów, jeden publiczny adres, rozróżnienie po portach', { size: 17, bold: true, anchor: 'middle' });
  const cols = [50, 140, 330, 520, 720];
  ['proto', 'wewnątrz (src)', 'cel (dst)', 'na zewnątrz (src po NAT)', 'uwaga'].forEach((h, i) => { b += text(cols[i], 66, h, { bold: true, size: 13, fill: C.muted }); });
  t.forEach((r, i) => {
    const y = 96 + i * 36, changed = r.nat.port !== r.sport;
    b += box(40, y - 22, 820, 32, i % 2 ? C.card : C.bg, C.line, 4);
    b += text(cols[0], y, r.proto, { mono: true, size: 12 }) + text(cols[1], y, `${r.src}:${r.sport}`, { mono: true, size: 12 }) + text(cols[2], y, `${r.dst}:${r.dport}`, { mono: true, size: 12 });
    b += text(cols[3], y, `${r.nat.ip}:${r.nat.port}`, { mono: true, size: 12, bold: true, fill: changed ? C.no : C.ok });
    b += text(cols[4], y, changed ? 'port zmieniony (kolizja)' : 'port zachowany', { size: 11, fill: changed ? C.no : C.muted });
  });
  b += text(40, 300, 'Dwa laptopy użyły tego samego portu 51000 do tego samego celu — drugi dostał inny port zewnętrzny.', { size: 13 });
  b += text(40, 324, 'Unikalność wymagana jest dla (protokół, adres i port po NAT, cel, port celu): do różnych celów port 51000 można użyć wielokrotnie.', { size: 13, bold: true });
  files['ns03-09-pat.svg'] = svg(W, H, 'Tablica translacji PAT dla adresu 203.0.113.5: trzy hosty łączą się z 93.184.216.34:443; host .20 zachowuje port 51000, host .21 z tym samym portem dostaje inny port zewnętrzny, host .22 zachowuje 40000; zapytanie UDP do 1.1.1.1:53 i połączenie do innego celu mogą ponownie użyć portu 51000.', b);
}

// 3. Wyczerpanie portow
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Wyczerpanie portów SNAT: dużo krótkich połączeń do JEDNEGO celu', { size: 18, bold: true, anchor: 'middle' });
  const x0 = 80, y0 = 300, xs = 640, ys = 210;
  b += line(x0, y0, x0 + xs, y0, { color: C.ink }) + line(x0, y0, x0, y0 - ys, { color: C.ink });
  b += text(x0 + xs / 2, y0 + 36, 'nowe połączenia na sekundę do tego samego ip:port', { anchor: 'middle', size: 12 });
  b += text(x0 - 10, y0 - ys - 10, 'zajęte porty (czas trzymania 60 s)', { size: 12 });
  const maxR = 2000, maxY = 130000, X = (r) => x0 + (r / maxR) * xs, Y = (v) => y0 - (v / maxY) * ys;
  [0, 500, 1000, 1500, 2000].forEach((r) => { b += text(X(r), y0 + 18, String(r), { anchor: 'middle', size: 11 }); });
  [55000, 110000].forEach((v, i) => { b += line(x0, Y(v), x0 + xs, Y(v), { color: i ? C.vio : C.no, dash: true }) + text(x0 + xs + 6, Y(v) + 4, i ? '2 IP (110 000)' : '1 IP AWS NAT GW (55 000)', { size: 11, fill: i ? C.vio : C.no }); });
  b += line(X(0), Y(0), X(maxR), Y(steadyState(maxR, 60)), { color: C.host, w: 3 });
  b += text(X(917) - 8, Y(55000) - 10, '~917/s → limit 1 IP', { size: 11, bold: true, fill: C.no, anchor: 'end' });
  b += box(40, 352, 820, 66, C.card, C.line, 8);
  b += text(56, 376, `Przykład: 2000 nowych połączeń/s do bazy w Internecie × 60 s TIME_WAIT/conntrack = 120 000 portów → potrzeba ${ipsNeeded(2000, 60, 55000)} adresów IP NAT.`, { size: 12 });
  b += text(56, 400, 'Objawy: sporadyczne timeouty wychodzące, ErrorPortAllocation (AWS), „nf_conntrack: table full”. Lek: pule połączeń, keep-alive.', { size: 12, bold: true });
  files['ns03-09-exhaustion.svg'] = svg(W, H, 'Wykres zajętych portów SNAT w funkcji tempa nowych połączeń do jednego celu przy czasie trzymania 60 s: limit 55 000 połączeń na adres AWS NAT Gateway osiągany przy około 917 połączeniach na sekundę, dwa adresy dają 110 000. Przy 2000 połączeń na sekundę potrzeba 3 adresów. Rozwiązanie: pule połączeń i keep-alive.', b);
}

// 4. Hairpin NAT
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Hairpin NAT (NAT loopback): klient w LAN łączy się z publicznym adresem własnego serwera', { size: 16, bold: true, anchor: 'middle' });
  b += box(60, 80, 200, 60, C.netFill, C.net, 8) + text(160, 106, 'laptop', { anchor: 'middle', bold: true }) + text(160, 126, '192.168.1.20', { anchor: 'middle', mono: true, size: 12 });
  b += box(350, 80, 200, 60, C.hostFill, C.host, 8) + text(450, 106, 'router NAT', { anchor: 'middle', bold: true }) + text(450, 126, 'WAN 203.0.113.5', { anchor: 'middle', mono: true, size: 12 });
  b += box(640, 80, 200, 60, C.okFill, C.ok, 8) + text(740, 106, 'serwer www', { anchor: 'middle', bold: true }) + text(740, 126, '192.168.1.30', { anchor: 'middle', mono: true, size: 12 });
  b += line(262, 100, 348, 100, { arrow: true }) + text(305, 72, '① do 203.0.113.5:443', { anchor: 'middle', size: 11 });
  b += line(552, 100, 638, 100, { arrow: true }) + text(595, 72, '② DNAT → .30', { anchor: 'middle', size: 11 });
  b += line(640, 140, 262, 140, { arrow: true, color: C.no, dash: true }) + text(450, 160, '③ bez SNAT: .30 odpowiada BEZPOŚREDNIO do .20 (ten sam LAN) — z adresu .30', { anchor: 'middle', size: 11, fill: C.no });
  b += text(450, 180, 'laptop czekał na odpowiedź od 203.0.113.5 → odrzuca pakiet (RST / brak połączenia)', { anchor: 'middle', size: 11, fill: C.no });
  b += box(60, 210, 780, 170, C.card, C.line, 8);
  b += text(80, 236, 'Rozwiązania:', { bold: true, size: 14 });
  b += text(80, 262, '• hairpin NAT: dodatkowy SNAT dla ruchu LAN→LAN przez publiczny adres (odpowiedź wraca przez router)', { size: 12 });
  b += text(80, 286, '  iptables -t nat -A POSTROUTING -s 192.168.1.0/24 -d 192.168.1.30 -p tcp --dport 443 -j MASQUERADE', { size: 11, mono: true });
  b += text(80, 312, '• split-horizon DNS: www.firma.pl w LAN rozwiązuje się na 192.168.1.30, w Internecie na 203.0.113.5 (lepsze)', { size: 12 });
  b += text(80, 338, '• w chmurze: prywatny endpoint / wewnętrzny load balancer zamiast publicznego adresu z wnętrza VPC', { size: 12 });
  b += text(80, 362, 'Objaw dla developera: „strona działa z telefonu na LTE, a z biura po domenie nie” (a po IP prywatnym działa).', { size: 12, bold: true });
  files['ns03-09-hairpin.svg'] = svg(W, H, 'Hairpin NAT: laptop 192.168.1.20 łączy się z publicznym adresem 203.0.113.5, router robi DNAT na serwer 192.168.1.30 w tym samym LAN, a serwer odpowiada bezpośrednio z adresu prywatnego, więc laptop odrzuca odpowiedź. Rozwiązania: dodatkowy SNAT (hairpin), split-horizon DNS, wewnętrzne endpointy w chmurze.', b);
}

writeSvgs(process.argv[2], files);
