// Ilustracje do lekcji _ns_03/13_DualStackAndTransition.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_13_dualstack_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { sequential, happyEyeballs, nat64, SCEN } = require('./ns03_13_dualstack_model.cjs');
const files = {};

// 1. Klient sekwencyjny vs Happy Eyeballs
{
  const W = 900, H = 400;
  const s = sequential(SCEN.v6blackhole, 10000), h = happyEyeballs(SCEN.v6blackhole);
  let b = text(W / 2, 30, 'AAAA istnieje, IPv6 nie działa: klient sekwencyjny vs Happy Eyeballs v2', { size: 17, bold: true, anchor: 'middle' });
  const X = (ms) => 60 + (ms / 10300) * 780;
  b += text(60, 70, 'Klient sekwencyjny (najpierw IPv6, timeout 10 s):', { bold: true, size: 13, fill: C.no });
  b += box(X(0), 82, X(10000) - X(0), 30, C.noFill, C.no, 4) + text(X(5000), 102, 'SYN IPv6 → brak odpowiedzi … timeout 10 000 ms', { anchor: 'middle', size: 12, bold: true });
  b += box(X(10000), 82, Math.max(X(10028) - X(10000), 6), 30, C.okFill, C.ok, 2) + text(X(10028) - 4, 128, `IPv4 OK: ${s.total} ms`, { anchor: 'end', size: 12, bold: true, fill: C.no });
  b += text(60, 172, 'Happy Eyeballs v2 (RFC 8305): IPv6, po 250 ms równolegle IPv4, wygrywa pierwszy:', { bold: true, size: 13, fill: C.ok });
  b += box(X(0), 184, X(250) - X(0) + 30, 30, C.noFill, C.no, 4) + text(X(0) + 6, 204, 'IPv6…', { size: 11 });
  b += box(X(250), 222, Math.max(X(278) - X(250), 8), 30, C.okFill, C.ok, 2) + text(X(278) + 10, 242, `IPv4 OK: ${h.total} ms — użytkownik nic nie zauważa`, { size: 12, bold: true, fill: C.ok });
  [0, 2500, 5000, 7500, 10000].forEach((t) => { b += text(X(t), 276, `${t} ms`, { anchor: 'middle', size: 10, fill: C.muted }) + line(X(t), 262, X(t), 266, { color: C.muted }); });
  b += box(40, 296, 820, 90, C.card, C.line, 8);
  b += text(60, 320, 'Przeglądarki, curl (≥ 7.59), systemowe resolvery Apple, Go, Python asyncio — mają Happy Eyeballs.', { size: 12 });
  b += text(60, 342, 'Klasyczne klienty JDK (Socket, HttpURLConnection, HttpClient) łączą się z pierwszym adresem z resolvera —', { size: 12 });
  b += text(60, 364, 'przy zepsutym IPv6 każde nowe połączenie czeka connectTimeout. Objaw: „aplikacja raz na jakiś czas strasznie zwalnia”.', { size: 12, bold: true, fill: C.no });
  files['ns03-13-happy-eyeballs.svg'] = svg(W, H, 'Porównanie: klient sekwencyjny próbuje IPv6, czeka 10 sekund do timeoutu i dopiero łączy się po IPv4 po 10 028 ms; Happy Eyeballs v2 po 250 ms startuje równolegle IPv4 i łączy się po 278 ms. Klasyczne klienty JDK łączą się z pierwszym adresem z resolvera.', b);
}

// 2. Wybor adresu w Javie
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Java: kolejność adresów z resolvera i właściwości java.net.*', { size: 18, bold: true, anchor: 'middle' });
  const rows = [
    ['-Djava.net.preferIPv6Addresses=false (domyślnie)', 'getAllByName: najpierw IPv4, potem IPv6 → getByName zwraca IPv4', C.net, C.netFill],
    ['-Djava.net.preferIPv6Addresses=true', 'najpierw IPv6 → przy zepsutym IPv6 każde połączenie czeka timeout', C.no, C.noFill],
    ['-Djava.net.preferIPv6Addresses=system (JDK 9+)', 'kolejność jak w systemie (RFC 6724 / gai.conf) — spójnie z resztą OS', C.ok, C.okFill],
    ['-Djava.net.preferIPv4Stack=true', 'JVM używa tylko gniazd IPv4 — brak IPv6 w ogóle (obejście, nie rozwiązanie)', C.host, C.hostFill],
  ];
  rows.forEach(([h, d, col, fill], i) => { const y = 54 + i * 70; b += box(40, y, 820, 60, fill, col, 8) + text(56, y + 24, h, { mono: true, size: 13, bold: true }) + text(56, y + 46, d, { size: 12, fill: col }); });
  b += text(40, 352, 'Cache DNS JVM: networkaddress.cache.ttl (java.security, domyślnie 30 s bez SecurityManagera);', { size: 12 });
  b += text(40, 374, 'networkaddress.cache.negative.ttl = 10 s. Po naprawie DNS aplikacja może jeszcze chwilę używać starych adresów.', { size: 12 });
  files['ns03-13-java.svg'] = svg(W, H, 'Właściwości Javy wpływające na dual-stack: preferIPv6Addresses false (domyślnie, najpierw IPv4), true (najpierw IPv6), system (kolejność systemowa RFC 6724) oraz preferIPv4Stack (tylko IPv4). Cache DNS JVM: networkaddress.cache.ttl i negative.ttl.', b);
}

// 3. NAT64/DNS64
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Sieć IPv6-only: DNS64 syntetyzuje AAAA, NAT64 tłumaczy na IPv4', { size: 18, bold: true, anchor: 'middle' });
  b += box(40, 70, 170, 60, C.okFill, C.ok, 8) + text(125, 96, 'klient IPv6-only', { anchor: 'middle', bold: true }) + text(125, 116, '2001:db8:1::20', { anchor: 'middle', mono: true, size: 11 });
  b += box(300, 70, 170, 60, C.vioFill, C.vio, 8) + text(385, 96, 'DNS64', { anchor: 'middle', bold: true }) + text(385, 116, 'resolver', { anchor: 'middle', size: 11 });
  b += box(300, 170, 170, 60, C.hostFill, C.host, 8) + text(385, 196, 'NAT64', { anchor: 'middle', bold: true }) + text(385, 216, '64:ff9b::/96', { anchor: 'middle', mono: true, size: 11 });
  b += box(600, 170, 220, 60, C.netFill, C.net, 8) + text(710, 196, 'serwer tylko IPv4', { anchor: 'middle', bold: true }) + text(710, 216, '140.82.121.4', { anchor: 'middle', mono: true, size: 11 });
  b += line(212, 92, 298, 92, { arrow: true }) + text(255, 84, '① AAAA?', { anchor: 'middle', size: 11 });
  b += line(298, 116, 212, 116, { arrow: true }) + text(222, 150, `② AAAA ${nat64('140.82.121.4')}`, { size: 10, mono: true });
  b += line(125, 132, 125, 200) + line(125, 200, 298, 200, { arrow: true }) + text(210, 192, '③ TCP do 64:ff9b::…', { anchor: 'middle', size: 11 });
  b += line(472, 200, 598, 200, { arrow: true }) + text(535, 192, '④ IPv4', { anchor: 'middle', size: 11 });
  b += box(40, 260, 820, 124, C.card, C.line, 8);
  b += text(60, 284, 'DNS64: dla domeny bez AAAA zwraca syntetyczny AAAA = prefiks 64:ff9b::/96 + adres IPv4 (140.82.121.4 → 8c52:7904).', { size: 12 });
  b += text(60, 306, 'NAT64: stanowa translacja IPv6 → IPv4 (jak PAT). Działa dla aplikacji używających nazw DNS.', { size: 12 });
  b += text(60, 328, 'Nie działa: literały IPv4 w aplikacji (http://203.0.113.5) i protokoły z adresami w treści → 464XLAT (CLAT na urządzeniu)', { size: 12, bold: true, fill: C.no });
  b += text(60, 350, 'tworzy lokalny „IPv4” i tłumaczy go na IPv6 — standard u operatorów mobilnych (Android, iOS) i w AWS (DNS64 + NAT64 w NAT GW).', { size: 12 });
  b += text(60, 372, 'DNSSEC: zsyntetyzowane AAAA nie mają podpisu — walidujący klient musi syntetyzować sam (RFC 6147).', { size: 12 });
  files['ns03-13-nat64.svg'] = svg(W, H, 'Sieć IPv6-only: klient pyta DNS64 o AAAA, resolver zwraca syntetyczny adres 64:ff9b::8c52:7904 utworzony z 140.82.121.4, klient łączy się po IPv6 z bramą NAT64, która tłumaczy ruch na IPv4 do serwera. Literały IPv4 wymagają 464XLAT.', b);
}

// 4. RFC 6724
{
  const W = 900, H = 390;
  let b = text(W / 2, 30, 'Wybór adresu docelowego (RFC 6724) — dlaczego system „woli” IPv6, a czasem IPv4', { size: 16, bold: true, anchor: 'middle' });
  const rows = [
    ['::1/128', '50', 'loopback'], ['::/0', '40', 'IPv6 (GUA)'], ['::ffff:0:0/96', '35', 'IPv4 (mapowane)'], ['2002::/16', '30', '6to4'], ['2001::/32', '5', 'Teredo'], ['fc00::/7', '3', 'ULA'], ['::/96', '1', 'IPv4-compatible (przestarzałe)'],
  ];
  b += text(60, 62, 'prefiks', { bold: true, size: 12, fill: C.muted }) + text(240, 62, 'precedence', { bold: true, size: 12, fill: C.muted }) + text(360, 62, 'rodzaj', { bold: true, size: 12, fill: C.muted });
  rows.forEach(([p, pr, d], i) => { const y = 86 + i * 26; b += box(40, y - 18, 520, 24, i % 2 ? C.card : C.bg, C.line, 3) + text(60, y, p, { mono: true, size: 12 }) + text(240, y, pr, { mono: true, size: 12, bold: true }) + text(360, y, d, { size: 12 }); });
  b += box(580, 50, 280, 210, C.card, C.line, 8);
  ['Najważniejsze reguły:', '• unikaj adresów nieosiągalnych', '• preferuj zgodny zakres (scope)', '• preferuj wyższą precedence', '  (IPv6 GUA 40 > IPv4 35)', '• preferuj najdłuższy wspólny', '  prefiks ze źródłem', 'Linux: /etc/gai.conf', 'Windows: netsh int ipv6 show prefixpolicies'].forEach((t, i) => { b += text(596, 76 + i * 21, t, { size: 12, bold: i === 0 }); });
  b += text(40, 300, 'Skutek: gdy nazwa ma AAAA (GUA) i A, system próbuje najpierw IPv6. Gdy AAAA wskazuje ULA, a A — IPv4, wybierze IPv4 (3 < 35).', { size: 12 });
  b += text(40, 324, 'Zmiana polityki (np. preferuj IPv4): w gai.conf „precedence ::ffff:0:0/96 100” — to obejście, nie naprawa zepsutego IPv6.', { size: 12 });
  b += text(40, 352, 'Właściwa naprawa: działające IPv6 albo brak AAAA; po stronie klienta — Happy Eyeballs i krótkie timeouty.', { size: 12, bold: true, fill: C.ok });
  files['ns03-13-rfc6724.svg'] = svg(W, H, 'Tabela domyślnej polityki RFC 6724: ::1 precedence 50, ::/0 (IPv6) 40, ::ffff:0:0/96 (IPv4) 35, 6to4 30, Teredo 5, ULA 3. Reguły: unikanie nieosiągalnych, zgodny zakres, wyższa precedence, najdłuższy wspólny prefiks. Konfiguracja: /etc/gai.conf i netsh prefixpolicies.', b);
}

writeSvgs(process.argv[2], files);
