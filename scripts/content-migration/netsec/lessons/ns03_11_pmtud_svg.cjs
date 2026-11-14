// Ilustracje do lekcji _ns_03/11_FragmentationAndPmtud.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_11_pmtud_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { fragment, OVERHEAD, innerMtu, mss4 } = require('./ns03_11_pmtud_model.cjs');
const files = {};

// 1. Fragmentacja
{
  const W = 900, H = 380;
  const fr = fragment(4000, 1500);
  let b = text(W / 2, 30, 'Fragmentacja IPv4: datagram 4000 B przez łącze o MTU 1500', { size: 18, bold: true, anchor: 'middle' });
  const X = (bytes) => 60 + (bytes / 3980) * 780;
  b += box(60, 56, 780, 40, C.netFill, C.net, 4) + text(450, 82, 'dane 3980 B (Total Length 4000 = 20 B nagłówka + 3980 B)', { anchor: 'middle', bold: true, size: 13 });
  const cols = [[C.ok, C.okFill], [C.host, C.hostFill], [C.vio, C.vioFill]];
  fr.forEach((f, i) => {
    const [col, fill] = cols[i];
    b += box(X(f.offsetBytes), 120, X(f.offsetBytes + f.data) - X(f.offsetBytes) - 2, 46, fill, col, 4);
    b += text((X(f.offsetBytes) + X(f.offsetBytes + f.data)) / 2, 148, `fragment ${i + 1}`, { anchor: 'middle', bold: true, fill: col });
    const y = 200 + i * 38;
    b += box(60, y, 780, 32, fill, col, 4) + text(76, y + 21, `fragment ${i + 1}: Total Length ${f.total}, dane ${f.data} B, Fragment Offset ${f.offset} (= ${f.offsetBytes} B), MF=${f.mf}, to samo ID`, { size: 13, mono: true });
  });
  b += text(60, 330, 'Offset w jednostkach 8 B → dane w każdym fragmencie (poza ostatnim) to wielokrotność 8: (1500 − 20) = 1480 = 185 × 8.', { size: 12 });
  b += text(60, 354, 'Składa zawsze ODBIORCA (nie routery po drodze). Utrata jednego fragmentu = utrata całego datagramu.', { size: 12, bold: true, fill: C.no });
  files['ns03-11-fragments.svg'] = svg(W, H, 'Datagram 4000 bajtów z 3980 bajtami danych dzielony na trzy fragmenty dla MTU 1500: 1480 bajtów danych z offsetem 0, 1480 bajtów z offsetem 185 i 1020 bajtów z offsetem 370; MF=1 w dwóch pierwszych. Offset w jednostkach 8 bajtów, składa odbiorca.', b);
}

// 2. PMTUD
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Path MTU Discovery: DF=1, router odsyła „fragmentation needed” z MTU następnego łącza', { size: 16, bold: true, anchor: 'middle' });
  const nodes = [['serwer', 80, C.net, C.netFill], ['R1', 320, C.host, C.hostFill], ['R2 (tunel)', 560, C.host, C.hostFill], ['klient', 800, C.ok, C.okFill]];
  nodes.forEach(([n, x, col, fill]) => { b += box(x - 60, 60, 120, 40, fill, col, 8) + text(x, 86, n, { anchor: 'middle', bold: true }); });
  b += line(142, 80, 258, 80) + text(200, 72, 'MTU 1500', { anchor: 'middle', size: 11 }) + line(382, 80, 498, 80) + text(440, 72, 'MTU 1500', { anchor: 'middle', size: 11 }) + line(622, 80, 738, 80, { w: 3, color: C.no }) + text(680, 72, 'MTU 1420', { anchor: 'middle', size: 11, bold: true, fill: C.no });
  const steps = [
    ['① serwer wysyła segment TCP 1500 B z DF=1', C.ink],
    ['② R2: 1500 > 1420 i DF=1 → odrzuca i odsyła ICMP 3/4 „fragmentation needed, next-hop MTU 1420”', C.no],
    ['③ serwer zapisuje PMTU 1420 dla tego celu (ip route get → cache mtu 1420) i zmniejsza segmenty', C.ok],
    ['④ kolejne pakiety 1420 B przechodzą; IPv6: to samo z ICMPv6 Packet Too Big (routery nigdy nie fragmentują)', C.ok],
  ];
  steps.forEach(([t, col], i) => { b += text(60, 140 + i * 30, t, { size: 13, bold: i === 1, fill: col }); });
  b += box(40, 270, 820, 110, C.card, C.line, 8);
  b += text(60, 296, 'Sprawdzenie w Linuksie:', { bold: true, size: 13 });
  b += text(60, 320, 'tracepath -n klient.example          # pokazuje pmtu na każdym skoku', { mono: true, size: 12 });
  b += text(60, 342, 'ip route get 203.0.113.50             # po otrzymaniu ICMP: „cache expires 598sec mtu 1420”', { mono: true, size: 12 });
  b += text(60, 364, 'ping -M do -s 1392 203.0.113.50       # 1392 + 28 = 1420 przechodzi, 1393 nie', { mono: true, size: 12 });
  files['ns03-11-pmtud.svg'] = svg(W, H, 'Path MTU Discovery: serwer wysyła pakiet 1500 bajtów z flagą DF, router R2 przed łączem tunelowym o MTU 1420 odrzuca go i odsyła ICMP fragmentation needed z MTU 1420, serwer zapamiętuje PMTU i zmniejsza pakiety; w IPv6 analogicznie Packet Too Big. Polecenia tracepath, ip route get, ping -M do.', b);
}

// 3. Black hole
{
  const W = 900, H = 420;
  let b = text(W / 2, 30, 'PMTUD black hole: ICMP zablokowany — handshake TCP przechodzi, TLS wisi', { size: 17, bold: true, anchor: 'middle' });
  b += box(60, 56, 140, 40, C.okFill, C.ok, 8) + text(130, 82, 'klient', { anchor: 'middle', bold: true });
  b += box(700, 56, 140, 40, C.netFill, C.net, 8) + text(770, 82, 'serwer', { anchor: 'middle', bold: true });
  b += box(390, 56, 120, 40, C.noFill, C.no, 8) + text(450, 76, 'tunel 1420', { anchor: 'middle', bold: true, size: 12 }) + text(450, 90, 'ICMP DROP', { anchor: 'middle', size: 10, fill: C.no, bold: true });
  const msgs = [
    ['SYN (60 B)', 'r', true], ['SYN-ACK (60 B)', 'l', true], ['ACK + ClientHello (~600 B)', 'r', true],
    ['ServerHello + Certificate + … (1500 B, DF=1)', 'l', false], ['… ICMP 3/4 od routera tunelu → zablokowany przez firewall', 'x', false],
    ['retransmisje 1500 B … timeout (klient: „TLS handshake timeout”)', 'l', false],
  ];
  msgs.forEach(([t, dir, ok], i) => {
    const y = 130 + i * 36;
    if (dir === 'x') { b += text(450, y + 4, t, { anchor: 'middle', size: 12, fill: C.no, bold: true }); return; }
    b += line(dir === 'r' ? 130 : 770, y, dir === 'r' ? 770 : 130, y, { arrow: true, color: ok ? C.ok : C.no, w: 2, dash: !ok });
    b += text(450, y - 6, t + (ok ? '  ✓' : '  ✕'), { anchor: 'middle', size: 12, bold: true, fill: ok ? C.ok : C.no });
  });
  b += box(40, 352, 820, 56, C.card, C.line, 8);
  b += text(60, 376, 'Objawy: ping OK, curl -v wisi po „Client hello”, SSH loguje się, ale scp/git clone stoi, małe API OK, duże odpowiedzi nie.', { size: 12 });
  b += text(60, 398, 'Naprawa: przepuść ICMP 3/4 i Packet Too Big; MSS clamping na routerze tunelu; mniejsze MTU interfejsu; PLPMTUD.', { size: 12, bold: true, fill: C.ok });
  files['ns03-11-blackhole.svg'] = svg(W, H, 'PMTUD black hole: małe pakiety SYN, SYN-ACK i ClientHello przechodzą przez tunel o MTU 1420, a duża odpowiedź serwera 1500 bajtów z DF jest odrzucana; komunikat ICMP fragmentation needed jest blokowany przez firewall, więc serwer retransmituje bez końca, a klient zgłasza timeout handshake TLS.', b);
}

// 4. MSS i narzuty
{
  const W = 900, H = 440;
  let b = text(W / 2, 30, 'Narzut tuneli, MTU wewnętrzne i MSS (TCP po IPv4)', { size: 18, bold: true, anchor: 'middle' });
  const cols = [50, 470, 590, 720];
  ['enkapsulacja (łącze zewnętrzne MTU 1500)', 'narzut', 'MTU wewn.', 'MSS IPv4'].forEach((h, i) => { b += text(cols[i], 62, h, { bold: true, size: 13, fill: C.muted }); });
  OVERHEAD.forEach(([n, o], i) => {
    const y = 88 + i * 30;
    b += box(40, y - 20, 820, 26, i % 2 ? C.card : C.bg, C.line, 3);
    b += text(cols[0], y, n, { size: 12 }) + text(cols[1], y, `${o} B`, { mono: true, size: 12 }) + text(cols[2], y, String(innerMtu(1500, o)), { mono: true, size: 12, bold: true }) + text(cols[3], y, String(mss4(innerMtu(1500, o))), { mono: true, size: 12 });
  });
  b += box(40, 336, 820, 92, C.okFill, C.ok, 8);
  b += text(60, 360, 'MSS clamping: router przepisuje opcję MSS w pakietach SYN, więc obie strony od początku wysyłają mniejsze segmenty —', { size: 12 });
  b += text(60, 380, 'działa nawet przy zablokowanym ICMP (dotyczy tylko TCP).', { size: 12 });
  b += text(60, 406, 'iptables -t mangle -A FORWARD -p tcp --tcp-flags SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu', { mono: true, size: 12, bold: true });
  files['ns03-11-mss.svg'] = svg(W, H, 'Tabela narzutów enkapsulacji dla łącza o MTU 1500: PPPoE 8 B (MTU 1492, MSS 1452), GRE 24 B, IPIP 20 B, VXLAN 50 B (1450, MSS 1410), WireGuard 60 lub 80 B (1440 lub 1420), IPsec ESP z NAT-T około 73 B, GENEVE 58 B. MSS clamping przepisuje MSS w pakietach SYN.', b);
}

writeSvgs(process.argv[2], files);
