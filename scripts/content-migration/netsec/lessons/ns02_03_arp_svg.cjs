// Ilustracje do lekcji _ns_02/03_ArpAndNeighborDiscovery.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_03_arp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Wymiana ARP
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'ARP: „kto ma 192.168.1.1?” — „ja, mój MAC to …”', { size: 18, bold: true, anchor: 'middle' });
  const cols = [['laptop 192.168.1.20', 150], ['inne hosty w VLAN', 450], ['brama 192.168.1.1', 750]];
  cols.forEach(([n, x]) => { b += box(x - 110, 52, 220, 34, C.card, C.ink, 6) + text(x, 74, n, { anchor: 'middle', bold: true, size: 13 }) + line(x, 88, x, 380, { color: C.line, dash: true }); });
  b += line(150, 130, 440, 130, { color: C.host, w: 3, arrow: true }) + line(150, 130, 740, 150, { color: C.host, w: 3, arrow: true });
  b += text(300, 120, 'ARP Request (broadcast ff:ff:ff:ff:ff:ff)', { anchor: 'middle', size: 12, bold: true, fill: C.host });
  b += text(450, 176, 'ignorują (nie ich adres)', { anchor: 'middle', size: 11, fill: C.muted });
  b += line(750, 220, 160, 220, { color: C.ok, w: 3, arrow: true });
  b += text(450, 210, 'ARP Reply (unicast do laptopa): 192.168.1.1 is-at 00:1a:2b:3c:4d:5e', { anchor: 'middle', size: 12, bold: true, fill: C.ok });
  b += box(30, 250, 300, 70, C.netFill, C.net, 8) + text(180, 274, 'cache ARP laptopa', { anchor: 'middle', bold: true, fill: C.net }) + text(180, 298, '192.168.1.1 → 00:1a:2b:3c:4d:5e REACHABLE', { anchor: 'middle', size: 11, mono: true });
  b += box(600, 250, 300, 70, C.netFill, C.net, 8) + text(750, 274, 'brama też zapamiętuje', { anchor: 'middle', bold: true, fill: C.net }) + text(750, 298, '192.168.1.20 → MAC laptopa', { anchor: 'middle', size: 11, mono: true });
  b += line(150, 350, 740, 350, { color: C.vio, w: 3, arrow: true }) + text(450, 342, 'dopiero teraz: ramka z pakietem IP do MAC bramy', { anchor: 'middle', size: 12, bold: true, fill: C.vio });
  b += text(W / 2, 404, 'ARP nie ma uwierzytelnienia: każdy w segmencie może odpowiedzieć — stąd ARP spoofing i obrona DAI.', { anchor: 'middle', size: 12, bold: true, fill: C.no });
  files['ns02-03-arp-exchange.svg'] = svg(W, H, 'Diagram sekwencji ARP: laptop wysyła rozgłoszeniowe zapytanie o MAC bramy, pozostałe hosty je ignorują, brama odpowiada unicastem ze swoim adresem MAC, obie strony zapisują wpis w cache, potem laptop wysyła ramkę z pakietem IP do MAC bramy', b);
}

// 2. Pola pakietu ARP
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Pakiet ARP: 28 bajtów w ramce z EtherType 0x0806', { size: 18, bold: true, anchor: 'middle' });
  const f = [['HTYPE', 2, '1 = Ethernet'], ['PTYPE', 2, '0x0800 = IPv4'], ['HLEN', 1, '6'], ['PLEN', 1, '4'], ['OPER', 2, '1 = request, 2 = reply'], ['SHA', 6, 'MAC nadawcy'], ['SPA', 4, 'IP nadawcy'], ['THA', 6, 'MAC celu (00…00 w request)'], ['TPA', 4, 'IP celu (szukany adres)']];
  f.forEach(([n, len, d], i) => {
    const y = 56 + i * 36;
    b += box(40, y, 120, 30, i >= 5 ? C.hostFill : C.netFill, i >= 5 ? C.host : C.net, 4) + text(100, y + 20, n, { anchor: 'middle', bold: true, size: 13 });
    b += text(180, y + 20, `${len} B`, { size: 12, mono: true }) + text(240, y + 20, d, { size: 12 });
  });
  b += box(520, 56, 350, 316, C.card, C.line, 8) + text(536, 80, 'przykład (Wireshark):', { bold: true, size: 13 });
  ['Address Resolution Protocol (request)', '  Hardware type: Ethernet (1)', '  Protocol type: IPv4 (0x0800)', '  Opcode: request (1)', '  Sender MAC: 3c:52:82:1a:f0:07', '  Sender IP: 192.168.1.20', '  Target MAC: 00:00:00:00:00:00', '  Target IP: 192.168.1.1', '', 'Gratuitous ARP: Sender IP = Target IP', '(ogłoszenie własnego adresu, np. po', ' przełączeniu VRRP lub zmianie karty)'].forEach((t, i) => { b += text(536, 106 + i * 22, t, { size: 12, mono: i < 8 }); });
  files['ns02-03-arp-packet.svg'] = svg(W, H, 'Pola pakietu ARP: typ sprzętu, typ protokołu, długości adresów, kod operacji, MAC i IP nadawcy, MAC i IP celu; obok przykład zapytania w Wiresharku i opis gratuitous ARP', b);
}

// 3. Stany sasiadow w Linuksie
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Stany wpisów sąsiadów w Linuksie (ip neigh)', { size: 18, bold: true, anchor: 'middle' });
  const st = [['INCOMPLETE', 120, 110, C.host, 'wysłano zapytanie, brak odpowiedzi'], ['REACHABLE', 400, 110, C.ok, 'potwierdzony niedawno'], ['STALE', 680, 110, C.muted, 'nieaktualny — używany, ale do sprawdzenia'], ['DELAY', 680, 260, C.vio, 'czeka chwilę na potwierdzenie z TCP'], ['PROBE', 400, 260, C.vio, 'aktywnie sprawdza (unicast)'], ['FAILED', 120, 260, C.no, 'brak odpowiedzi — host nieosiągalny']];
  st.forEach(([n, x, y, col, d]) => { b += box(x - 105, y - 30, 210, 60, C.card, col, 10) + text(x, y - 6, n, { anchor: 'middle', bold: true, fill: col, size: 14 }) + text(x, y + 16, d, { anchor: 'middle', size: 10 }); });
  b += line(225, 110, 293, 110, { arrow: true }) + text(260, 100, 'odpowiedź', { anchor: 'middle', size: 10 });
  b += line(505, 110, 573, 110, { arrow: true }) + text(540, 100, 'upływ czasu', { anchor: 'middle', size: 10 });
  b += line(680, 140, 680, 228, { arrow: true }) + text(690, 188, 'wysłanie ruchu', { size: 10 });
  b += line(573, 260, 507, 260, { arrow: true }) + text(540, 250, 'brak potwierdzenia', { anchor: 'middle', size: 10 });
  b += line(400, 230, 400, 142, { arrow: true }) + text(410, 190, 'odpowiedź', { size: 10 });
  b += line(293, 260, 227, 260, { arrow: true }) + text(260, 250, 'brak', { anchor: 'middle', size: 10 });
  b += line(120, 140, 120, 228, { arrow: true }) + text(130, 188, 'brak odp.', { size: 10 });
  b += text(W / 2, 336, '$ ip neigh', { anchor: 'middle', size: 13, mono: true, bold: true });
  b += text(W / 2, 358, '192.168.1.1 dev eth0 lladdr 00:1a:2b:3c:4d:5e REACHABLE   ·   192.168.1.77 dev eth0 FAILED', { anchor: 'middle', size: 12, mono: true });
  b += text(W / 2, 382, 'FAILED / INCOMPLETE dla bramy = problem warstwy 2 (VLAN, kabel, brama wyłączona, zła podsieć).', { anchor: 'middle', size: 12, fill: C.no, bold: true });
  files['ns02-03-neigh-states.svg'] = svg(W, H, 'Automat stanów wpisu sąsiada w Linuksie: INCOMPLETE po zapytaniu, REACHABLE po odpowiedzi, STALE po upływie czasu, DELAY przy wysłaniu ruchu, PROBE przy aktywnym sprawdzaniu i FAILED przy braku odpowiedzi; przykład wyniku ip neigh', b);
}

// 4. ARP spoofing - wykrywanie i obrona
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'ARP spoofing: jak go zobaczyć i zatrzymać', { size: 18, bold: true, anchor: 'middle' });
  b += box(30, 56, 400, 230, C.noFill, C.no, 10) + text(230, 80, 'Co widzi ofiara', { anchor: 'middle', bold: true, fill: C.no });
  ['przed:  192.168.1.1 → 00:1a:2b:3c:4d:5e (brama)', 'po:     192.168.1.1 → 66:77:88:99:aa:bb (napastnik)', '', 'w przechwyceniu: seria niezamówionych ARP Reply', '„192.168.1.1 is-at 66:77:…” co kilka sekund', '', 'Wireshark Expert Info:', '„duplicate use of 192.168.1.1 detected!”'].forEach((t, i) => { b += text(46, 108 + i * 22, t, { size: 12, mono: i < 2 }); });
  b += box(470, 56, 400, 230, C.okFill, C.ok, 10) + text(670, 80, 'Wykrywanie i obrona', { anchor: 'middle', bold: true, fill: C.ok });
  ['DHCP snooping — tablica wiązań IP ↔ MAC ↔ port', 'Dynamic ARP Inspection (DAI) — odrzuca ARP', '   niezgodne z tablicą wiązań', 'arpwatch / Zeek — alert na zmianę MAC bramy', 'IDS (Suricata) — reguły na anomalie ARP', 'statyczny wpis ARP bramy na krytycznych hostach', '802.1X — tylko uwierzytelnione urządzenia w sieci', 'TLS wszędzie — MITM nie odczyta ani nie zmieni treści'].forEach((t, i) => { b += text(486, 108 + i * 22, t, { size: 12 }); });
  b += text(W / 2, 320, 'IPv6: odpowiednik to fałszywe Neighbor Advertisement i Router Advertisement (rogue RA)', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 344, '→ obrona: RA Guard, ND Inspection / SAVI, DHCPv6 Guard na przełącznikach', { anchor: 'middle', size: 12 });
  b += text(W / 2, 384, 'Ćwiczenie ataku tylko między kontenerami w izolowanym labie (rozdział 20) — tu uczymy się śladów i kontroli.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns02-03-arp-spoofing-defense.svg'] = svg(W, H, 'Dwa panele: po lewej objawy ARP spoofingu u ofiary — zmiana MAC bramy w cache, seria niezamówionych odpowiedzi ARP i ostrzeżenie Wiresharka o zduplikowanym użyciu adresu; po prawej wykrywanie i obrona: DHCP snooping, Dynamic ARP Inspection, arpwatch i Zeek, IDS, statyczne wpisy, 802.1X i TLS; pod spodem odpowiedniki dla IPv6: RA Guard i ND Inspection', b);
}

writeSvgs(process.argv[2], files);
