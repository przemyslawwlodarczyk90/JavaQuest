// Ilustracje do lekcji _ns_03/06_SpecialAndPrivateAddresses.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_06_special_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { V4, V6 } = require('./ns03_06_special_model.cjs');
const files = {};

// 1. Mapa zakresow IPv4
{
  const W = 900, H = 500;
  let b = text(W / 2, 30, 'Adresy IPv4, które NIE są zwykłymi adresami publicznymi (rejestr IANA, RFC 6890)', { size: 16, bold: true, anchor: 'middle' });
  const color = (n) => n.startsWith('prywatny') ? [C.ok, C.okFill] : n.includes('CGNAT') ? [C.host, C.hostFill] : n.includes('pętla') || n.includes('link-local') ? [C.net, C.netFill] : n.includes('dokument') || n.includes('benchmark') || n.includes('testy') ? [C.vio, C.vioFill] : [C.no, C.noFill];
  V4.forEach(([c, n, rfc], i) => {
    const y = 52 + i * 27, [col, fill] = color(n);
    b += box(40, y, 820, 24, fill, col, 4) + text(52, y + 17, c, { mono: true, bold: true, size: 12, fill: col }) + text(240, y + 17, n, { size: 12 }) + text(850, y + 17, rfc, { size: 11, anchor: 'end', fill: C.muted });
  });
  b += text(40, 494, 'Zielone: prywatne RFC 1918 · niebieskie: tylko host/łącze · pomarańczowe: CGNAT · fioletowe: dokumentacja/testy · czerwone: specjalne', { size: 11, fill: C.muted });
  files['ns03-06-ranges.svg'] = svg(W, H, 'Tabela zakresów specjalnych IPv4: 0.0.0.0/8, prywatne 10.0.0.0/8, 172.16.0.0/12 i 192.168.0.0/16 (RFC 1918), CGNAT 100.64.0.0/10, loopback 127.0.0.0/8, link-local 169.254.0.0/16, dokumentacyjne 192.0.2.0/24, 198.51.100.0/24 i 203.0.113.0/24, benchmark 198.18.0.0/15, multicast 224.0.0.0/4, zarezerwowany 240.0.0.0/4 i broadcast 255.255.255.255.', b);
}

// 2. Bind 127.0.0.1 vs 0.0.0.0
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'server.address: kto może się połączyć z aplikacją Spring Boot na porcie 8080?', { size: 17, bold: true, anchor: 'middle' });
  const cols = [['bind 127.0.0.1', ['✓ ten sam host (curl localhost)', '✗ inny kontener / host Dockera', '✗ komputer w LAN'], C.ok, C.okFill], ['bind 0.0.0.0', ['✓ ten sam host', '✓ host Dockera przez -p 8080:8080', '✓ LAN i Internet (jeśli firewall pozwoli)'], C.no, C.noFill], ['bind 10.0.1.15 (konkretny interfejs)', ['✓ tylko przez ten adres/sieć', '✗ localhost (127.0.0.1) — NIE', '✓ każdy, kto dotrze do 10.0.1.15'], C.net, C.netFill]];
  cols.forEach(([h, items, col, fill], i) => {
    const x = 40 + i * 280;
    b += box(x, 56, 260, 170, fill, col, 10) + text(x + 130, 84, h, { anchor: 'middle', bold: true, fill: col, size: 14 });
    items.forEach((t, j) => { b += text(x + 16, 118 + j * 32, t, { size: 12 }); });
  });
  b += box(40, 246, 820, 166, C.card, C.line, 8);
  b += text(60, 272, 'Pułapka kontenera: w Dockerze aplikacja z bind 127.0.0.1 nasłuchuje na loopbacku KONTENERA —', { size: 13 });
  b += text(60, 294, 'publikacja portu (-p 8080:8080) przekazuje ruch na interfejs eth0 kontenera, więc połączenie się nie uda.', { size: 13 });
  b += text(60, 322, 'Pułapka odwrotna: docker run -p 8080:8080 publikuje na 0.0.0.0 hosta — usługa „tylko dla mnie” jest w LAN.', { size: 13 });
  b += text(60, 344, 'Bezpiecznie: -p 127.0.0.1:8080:8080 (tylko lokalnie) albo reverse proxy z TLS.', { size: 13, bold: true, fill: C.ok });
  b += text(60, 376, '0.0.0.0 przy bind = „wszystkie interfejsy”; jako adres docelowy — błąd (Linux potraktuje jak lokalny host).', { size: 13 });
  b += text(60, 398, 'Sprawdzenie: ss -ltnp | grep 8080  →  127.0.0.1:8080 vs 0.0.0.0:8080 vs [::]:8080', { size: 13, mono: true });
  files['ns03-06-bind.svg'] = svg(W, H, 'Porównanie adresów nasłuchu: bind 127.0.0.1 dostępny tylko z tego samego hosta, bind 0.0.0.0 ze wszystkich sieci, bind na konkretny adres tylko przez ten interfejs. W kontenerze bind 127.0.0.1 blokuje publikację portu, a -p 8080:8080 publikuje usługę na wszystkich interfejsach hosta; bezpieczniej -p 127.0.0.1:8080:8080.', b);
}

// 3. Metadane chmury i SSRF
{
  const W = 900, H = 420;
  let b = text(W / 2, 30, '169.254.169.254: metadane instancji — i dlaczego to cel ataków SSRF', { size: 17, bold: true, anchor: 'middle' });
  b += box(40, 60, 200, 70, C.noFill, C.no, 8) + text(140, 90, 'Atakujący', { anchor: 'middle', bold: true, fill: C.no }) + text(140, 112, '?url=http://169.254…', { anchor: 'middle', size: 11, mono: true });
  b += box(350, 60, 220, 70, C.hostFill, C.host, 8) + text(460, 90, 'Aplikacja (EC2/pod)', { anchor: 'middle', bold: true, fill: C.host }) + text(460, 112, 'pobiera podany URL', { anchor: 'middle', size: 11 });
  b += box(680, 60, 180, 70, C.vioFill, C.vio, 8) + text(770, 86, 'IMDS', { anchor: 'middle', bold: true, fill: C.vio }) + text(770, 108, '169.254.169.254', { anchor: 'middle', size: 12, mono: true });
  b += line(242, 95, 348, 95, { arrow: true, w: 2 }) + line(572, 95, 678, 95, { arrow: true, w: 2 });
  b += text(625, 150, 'GET /latest/meta-data/iam/security-credentials/rola', { anchor: 'middle', size: 11, mono: true, fill: C.no });
  b += box(40, 176, 400, 226, C.noFill, C.no, 8) + text(60, 202, 'IMDSv1 (stary)', { bold: true, fill: C.no });
  ['Zwykły GET bez nagłówków zwraca dane,', 'w tym tymczasowe klucze roli IAM.', 'Jedno SSRF = przejęcie uprawnień', 'instancji (incydent Capital One, 2019).'].forEach((t, i) => { b += text(60, 228 + i * 24, t, { size: 12 }); });
  b += box(460, 176, 400, 226, C.okFill, C.ok, 8) + text(480, 202, 'Obrona', { bold: true, fill: C.ok });
  ['IMDSv2: najpierw PUT po token (nagłówek', 'X-aws-ec2-metadata-token-ttl-seconds),', 'potem GET z tokenem; hop limit 1.', 'Wymuś: HttpTokens=required.', 'W aplikacji: allow-lista hostów, blokada', 'adresów specjalnych PO rozwiązaniu DNS,', 'bez przekierowań do adresów wewnętrznych.', 'Egress: blokuj 169.254.169.254 z podów.'].forEach((t, i) => { b += text(480, 226 + i * 21, t, { size: 12 }); });
  files['ns03-06-metadata.svg'] = svg(W, H, 'Schemat ataku SSRF: atakujący podaje aplikacji URL do 169.254.169.254, aplikacja pobiera metadane instancji, w IMDSv1 łącznie z kluczami roli IAM. Obrona: IMDSv2 z tokenem i hop limitem 1, allow-lista hostów, blokada adresów specjalnych po rozwiązaniu DNS, filtrowanie ruchu wychodzącego.', b);
}

// 4. CGNAT i IPv6
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'CGNAT 100.64.0.0/10 i adresy specjalne IPv6', { size: 18, bold: true, anchor: 'middle' });
  b += box(40, 56, 820, 120, C.card, C.line, 8);
  const chain = [['laptop', '192.168.1.20', C.ok], ['router domowy', 'WAN 100.72.14.9', C.host], ['CGNAT operatora', 'publiczny 203.0.113.50', C.vio], ['serwer', 'widzi 203.0.113.50', C.net]];
  chain.forEach(([n, a, col], i) => {
    const x = 60 + i * 200;
    b += box(x, 76, 170, 80, C.bg, col, 8) + text(x + 85, 104, n, { anchor: 'middle', bold: true, fill: col }) + text(x + 85, 128, a, { anchor: 'middle', size: 12, mono: true });
    if (i < 3) b += line(x + 172, 116, x + 198, 116, { arrow: true });
  });
  b += text(60, 196, 'Skutki: setki klientów pod jednym publicznym adresem — blokada po IP uderza w niewinnych; brak przekierowania portów;', { size: 12 });
  b += text(60, 216, 'logi serwera muszą zapisywać port źródłowy (operator identyfikuje klienta po IP + porcie + czasie).', { size: 12 });
  V6.forEach(([c, n], i) => {
    const y = 246 + i * 20;
    b += text(60, y, c, { mono: true, bold: true, size: 12, fill: C.net }) + text(260, y, n, { size: 12 });
  });
  files['ns03-06-cgnat-ipv6.svg'] = svg(W, H, 'Łańcuch CGNAT: laptop 192.168.1.20, router domowy z adresem WAN 100.72.14.9 z puli 100.64.0.0/10, CGNAT operatora z publicznym 203.0.113.50, serwer widzi tylko adres publiczny. Lista adresów specjalnych IPv6: ::, ::1, ::ffff:0:0/96, 64:ff9b::/96, 2001:db8::/32, 2000::/3, fc00::/7, fe80::/10, ff00::/8.', b);
}

writeSvgs(process.argv[2], files);
