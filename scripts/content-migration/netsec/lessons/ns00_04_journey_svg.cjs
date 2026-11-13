// Ilustracje do lekcji _ns_00/04_JourneyOfOneRequest.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_04_journey_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { STEPS, TOTAL } = require('./ns00_04_journey_data.cjs');
const files = {};
const colOf = (s) => (s.layer.startsWith('L2') ? C.host : s.layer === 'L4' ? C.ok : s.layer === 'L6' ? C.vio : s.layer === 'aplikacja' ? C.no : C.net);

// 1. Mapa podrozy
{
  const W = 900, H = 470;
  let b = text(W / 2, 32, 'GET https://api.javaquest.local/users/42 — 12 etapów', { size: 19, bold: true, anchor: 'middle' });
  STEPS.forEach((s, i) => {
    const col = i < 6 ? 0 : 1, row = i % 6;
    const x = 30 + col * 430, y = 54 + row * 66;
    const c = colOf(s);
    b += box(x, y, 410, 56, C.card, c, 8) + `<circle cx="${x + 24}" cy="${y + 28}" r="15" fill="${c}"/>` + text(x + 24, y + 33, String(s.n), { anchor: 'middle', bold: true, fill: '#fff', size: 12 });
    b += text(x + 48, y + 23, s.name, { bold: true, size: 13 }) + text(x + 48, y + 42, s.where, { size: 11, fill: C.muted });
    b += text(x + 398, y + 23, `rozdz. ${String(s.ch).padStart(2, '0')}`, { anchor: 'end', size: 11, fill: c, bold: true });
  });
  b += text(W / 2, 458, 'Kolor = warstwa: niebieski L7/DNS, pomarańczowy L2–L3, zielony L4, fioletowy TLS, czerwony wnętrze aplikacji Java.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns00-04-journey-map.svg'] = svg(W, H, 'Dwanaście etapów żądania: parsowanie URL, cache i HSTS, DNS, trasa i ARP, handshake TCP, handshake TLS 1.3, żądanie HTTP/2, reverse proxy, Tomcat i filtry, DispatcherServlet i kontroler, HikariCP i PostgreSQL, odpowiedź i droga powrotna; przy każdym numer rozdziału toru', b);
}

// 2. Wodospad czasu
{
  const W = 900, H = 500;
  let b = text(W / 2, 32, `Gdzie mija ${TOTAL.toFixed(1).replace('.', ',')} ms? Przykładowy pomiar (RTT ok. 20 ms)`, { size: 18, bold: true, anchor: 'middle' });
  const scale = 560 / TOTAL; let t = 0;
  STEPS.forEach((s, i) => {
    const y = 60 + i * 34, x = 300 + t * scale, w = Math.max(3, s.ms * scale);
    b += text(290, y + 17, `${s.n}. ${s.name}`, { anchor: 'end', size: 12 });
    b += `<rect x="${x}" y="${y + 4}" width="${w}" height="20" rx="3" fill="${colOf(s)}" opacity="0.85"/>`;
    b += text(x + w + 6, y + 18, `${String(s.ms).replace('.', ',')} ms`, { size: 11, fill: C.muted });
    t += s.ms;
  });
  b += text(W / 2, 486, 'Największe kawałki: DNS (bez cache), TCP i TLS (po 1 RTT) — sieć, nie Java. Cache DNS i połączenia keep-alive je usuwają.', { anchor: 'middle', size: 12, bold: true });
  files['ns00-04-waterfall.svg'] = svg(W, H, 'Wodospad czasu dwunastu etapów żądania: największe odcinki to DNS około 12 ms, handshake TCP 20 ms i TLS 21 ms; przetwarzanie w Javie i bazie zajmuje kilka milisekund', b);
}

// 3. Wnetrze JVM
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Wnętrze aplikacji: od gniazda Tomcata do wiersza w PostgreSQL', { size: 18, bold: true, anchor: 'middle' });
  const chain = [['Tomcat', 'acceptor + wątek z puli (lub wątek wirtualny)', C.net], ['Filtry', 'Spring Security: uwierzytelnienie, CSRF, CORS', C.no], ['DispatcherServlet', 'mapowanie @GetMapping("/users/{id}")', C.vio], ['UserController', 'walidacja id, wywołanie serwisu', C.vio], ['UserService', '@Transactional(readOnly = true)', C.ok], ['UserRepository', 'Spring Data JPA → Hibernate → SQL', C.ok], ['HikariCP', 'połączenie z puli (TCP + TLS do bazy już otwarte)', C.host], ['PostgreSQL', 'Parse/Bind/Execute → DataRow → wynik', C.host]];
  chain.forEach(([n, d, c], i) => {
    const x = 40 + (i % 4) * 210, y = 70 + Math.floor(i / 4) * 150;
    b += box(x, y, 190, 100, C.card, c, 10) + text(x + 95, y + 34, n, { anchor: 'middle', bold: true, fill: c, size: 14 });
    const words = d.split(' '); let l1 = '', l2 = '';
    words.forEach((w) => { if ((l1 + ' ' + w).length <= 26 && !l2) l1 = (l1 + ' ' + w).trim(); else l2 = (l2 + ' ' + w).trim(); });
    b += text(x + 95, y + 60, l1, { anchor: 'middle', size: 11 }) + text(x + 95, y + 78, l2, { anchor: 'middle', size: 11 });
    if (i % 4 !== 3) b += line(x + 191, y + 50, x + 208, y + 50, { arrow: true });
  });
  b += `<path d="M ${40 + 3 * 210 + 95} 170 L ${40 + 3 * 210 + 95} 196 L 135 196 L 135 218" fill="none" stroke="${C.muted}" stroke-width="2" marker-end="url(#arr)"/>`;
  b += text(W / 2, 392, 'Każdy element ma własną pulę lub kolejkę (wątki, połączenia) i własne timeouty — tam najczęściej rodzą się opóźnienia i awarie.', { anchor: 'middle', size: 12, bold: true });
  files['ns00-04-jvm-inside.svg'] = svg(W, H, 'Łańcuch wewnątrz aplikacji: Tomcat, filtry Spring Security, DispatcherServlet, UserController, UserService, UserRepository, HikariCP i PostgreSQL', b);
}

// 4. Gdzie moze sie zepsuc
{
  const W = 900, H = 520;
  let b = text(W / 2, 32, 'Każdy etap ma swój typowy błąd — i swój rozdział toru', { size: 18, bold: true, anchor: 'middle' });
  STEPS.forEach((s, i) => {
    const y = 52 + i * 38, c = colOf(s);
    b += box(30, y, 260, 32, C.card, c, 6) + text(42, y + 21, `${s.n}. ${s.name}`, { size: 12, bold: true, fill: c });
    b += box(300, y, 470, 32, C.noFill, C.no, 6) + text(312, y + 21, s.err, { size: 12 });
    b += box(780, y, 90, 32, C.bg, C.line, 6) + text(825, y + 21, `rozdz. ${String(s.ch).padStart(2, '0')}`, { anchor: 'middle', size: 12 });
  });
  files['ns00-04-failure-map.svg'] = svg(W, H, 'Tabela: dla każdego z dwunastu etapów typowy błąd (np. UnknownHostException przy DNS, Connection refused przy TCP, PKIX przy TLS, 502 i 504 przy proxy, 401 i 403 przy filtrach, wyczerpana pula przy HikariCP) i numer rozdziału toru', b);
}

writeSvgs(process.argv[2], files);
