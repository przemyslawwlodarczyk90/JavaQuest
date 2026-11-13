// Ilustracje do lekcji _ns_00/01_TrackMapAndLearningCycle.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_01_trackmap_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};
const { plan, SHORT } = require('./track_meta.cjs');
const short = (t, i) => SHORT[i];

// 1. Mapa toru: 41 rozdzialow w trzech czesciach
{
  const W = 900, H = 640;
  let b = text(W / 2, 30, `Mapa toru: ${plan.length} rozdziałów, ${plan.reduce((a, c) => a + c[3].length, 0)} lekcji`, { size: 19, bold: true, anchor: 'middle' });
  const parts = [
    ['I. Sieci dla Java developera', 0, 12, C.net, C.netFill],
    ['II. Infrastruktura i operacje', 13, 14, C.ok, C.okFill],
    ['III. Cyberbezpieczeństwo blue team', 15, 40, C.vio, C.vioFill],
  ];
  let y = 48;
  parts.forEach(([title, from, to, col, fill]) => {
    const chs = plan.slice(from, to + 1);
    const rows = Math.ceil(chs.length / 3);
    const h = 30 + rows * 26;
    b += box(20, y, 860, h, fill, col, 10);
    b += text(34, y + 21, title, { bold: true, fill: col, size: 14 });
    chs.forEach((c, i) => {
      const cx = 34 + (i % 3) * 282, cy = y + 44 + Math.floor(i / 3) * 26;
      const n = String(from + i).padStart(2, '0');
      b += text(cx, cy, `${n} ${short(c[1], from + i)}`, { size: 12 });
    });
    y += h + 10;
  });
  files['ns00-01-track-map.svg'] = svg(W, y + 6, 'Mapa toru Sieci i Cyberbezpieczeństwo: trzy części z listą rozdziałów od 00 do 40', b);
}

// 2. Cykl nauki
{
  const W = 900, H = 430;
  let b = text(W / 2, 32, 'Cykl każdej dużej kompetencji', { size: 19, bold: true, anchor: 'middle' });
  const steps = [['UNDERSTAND', 'zrozum model', C.net], ['BUILD', 'zbuduj działający lab', C.ok], ['OBSERVE', 'zobacz w pakietach i logach', C.host], ['BREAK', 'zepsuj kontrolowanie', C.no], ['ATTACK', 'odtwórz atak w izolowanym labie', C.no], ['FIX', 'zabezpiecz i napraw', C.ok], ['PROVE', 'udowodnij testem i dowodem', C.vio]];
  const cx = 450, cy = 235, R = 150;
  steps.forEach(([n, d, col], i) => {
    const a = -Math.PI / 2 + (2 * Math.PI * i) / steps.length;
    const x = cx + R * 1.55 * Math.cos(a), y = cy + R * Math.sin(a);
    const a2 = -Math.PI / 2 + (2 * Math.PI * (i + 1)) / steps.length;
    const x2 = cx + R * 1.55 * Math.cos(a2), y2 = cy + R * Math.sin(a2);
    b += line(x + (x2 - x) * 0.3, y + (y2 - y) * 0.3, x + (x2 - x) * 0.7, y + (y2 - y) * 0.7, { color: C.muted, arrow: true });
    b += box(x - 85, y - 24, 170, 48, C.card, col, 10) + text(x, y - 3, n, { anchor: 'middle', bold: true, fill: col, size: 13 }) + text(x, y + 15, d, { anchor: 'middle', size: 11 });
  });
  b += text(cx, cy - 6, 'blue team:', { anchor: 'middle', size: 13, fill: C.muted });
  b += text(cx, cy + 16, 'atak tylko po to, by go wykryć i zatrzymać', { anchor: 'middle', size: 13, bold: true });
  files['ns00-01-learning-cycle.svg'] = svg(W, H, 'Cykl nauki w siedmiu krokach: zrozum, zbuduj, obserwuj, zepsuj, odtwórz atak w izolowanym labie, napraw, udowodnij', b);
}

// 3. Poziomy L1-L5 i typy labow
{
  const W = 900, H = 430;
  let b = text(W / 2, 32, 'Poziomy trudności i typy laboratoriów', { size: 19, bold: true, anchor: 'middle' });
  const lv = [['L5 Expert', 'bez prowadzenia: Boss, śledztwo, projekt', C.no], ['L4 Attack & Defense', 'atak w labie + wykrycie i obrona', C.host], ['L3 Advanced', 'mechanizmy wewnętrzne, przypadki brzegowe', C.vio], ['L2 Professional', 'samodzielna praca jak w zespole', C.ok], ['L1 Foundation', 'pojęcia, modele, pierwsze narzędzia', C.net]];
  lv.forEach(([n, d, col], i) => {
    const w = 200 + i * 60, x = 250 - w / 2, y = 56 + i * 64;
    b += `<rect x="${x}" y="${y}" width="${w}" height="56" rx="8" fill="${col}" opacity="0.18" stroke="${col}" stroke-width="1.5"/>`;
    b += text(250, y + 24, n, { anchor: 'middle', bold: true, fill: col, size: 14 }) + text(250, y + 43, d, { anchor: 'middle', size: 11 });
  });
  const labs = [['Guided', 'krok po kroku'], ['Packet', 'analiza pakietów'], ['Debug', 'znajdź usterkę'], ['Failure', 'awaria kontrolowana'], ['Defense', 'zabezpiecz i wykryj'], ['Incident', 'zbadaj incydent'], ['Malware / Forensics', 'artefakty i próbki'], ['Build', 'zbuduj narzędzie'], ['Architecture', 'zaprojektuj i uzasadnij'], ['Boss', 'integracja bez podpowiedzi']];
  b += text(690, 64, 'Typy laboratoriów', { anchor: 'middle', bold: true, size: 14 });
  labs.forEach(([n, d], i) => { const y = 80 + i * 33; b += box(530, y, 320, 28, C.card, C.line, 6) + text(544, y + 19, n, { bold: true, size: 12 }) + text(840, y + 19, d, { anchor: 'end', size: 12, fill: C.muted }); });
  files['ns00-01-levels-labs.svg'] = svg(W, H, 'Piramida poziomów L1 do L5 od podstaw do eksperckiego oraz lista dziesięciu typów laboratoriów toru', b);
}

// 4. Role blue team a czesci toru
{
  const W = 900, H = 450;
  let b = text(W / 2, 32, 'Dokąd prowadzi tor: role, w których przewaga Java developera jest największa', { size: 17, bold: true, anchor: 'middle' });
  const roles = [
    ['AppSec / Product Security', 'rozdz. 15, 17, 23, 24, 36', C.vio],
    ['DevSecOps / Platform Security', 'rozdz. 10, 22, 24, 25', C.ok],
    ['Analityk SOC (L1–L3)', 'rozdz. 18–20, 26, 28, 31', C.net],
    ['Detection Engineer', 'rozdz. 26, 27, 30, 35', C.net],
    ['Incident Responder / DFIR', 'rozdz. 32, 33, 34', C.no],
    ['Cloud Security Engineer', 'rozdz. 10, 16, 25', C.ok],
    ['Threat Intelligence / Hunter', 'rozdz. 29, 30, 39', C.host],
    ['Network / Security Architect', 'rozdz. 13, 14, 21, 38', C.vio],
  ];
  roles.forEach(([r, ch, col], i) => {
    const x = 40 + (i % 2) * 420, y = 56 + Math.floor(i / 2) * 82;
    b += box(x, y, 400, 68, C.card, col, 10) + `<rect x="${x}" y="${y}" width="10" height="68" rx="4" fill="${col}"/>`;
    b += text(x + 26, y + 29, r, { bold: true, size: 15, fill: col }) + text(x + 26, y + 52, ch, { size: 12, fill: C.muted });
  });
  b += text(W / 2, 400, 'Wspólny fundament wszystkich ról: sieci (część I), Linux i Windows, logi, myślenie o zagrożeniach.', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 424, 'Rozdział 40 pomaga zaplanować przejście na wybraną rolę i zbudować portfolio.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns00-01-roles.svg'] = svg(W, H, 'Osiem ról bezpieczeństwa (AppSec, DevSecOps, analityk SOC, detection engineer, incident responder, cloud security, threat intelligence, architekt) z numerami rozdziałów toru, które do nich przygotowują', b);
}

writeSvgs(process.argv[2], files);
