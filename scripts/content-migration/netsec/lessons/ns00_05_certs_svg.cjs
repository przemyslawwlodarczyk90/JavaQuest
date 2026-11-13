// Ilustracje do lekcji _ns_00/05_CertificationRoadmap.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_05_certs_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { EXAMS } = require('./ns00_05_certs_data.cjs');
const files = {};

// 1. Sciezki certyfikacji
{
  const W = 900, H = 440;
  let b = text(W / 2, 32, 'Ścieżki certyfikacji dla Java developera w stronę blue team', { size: 18, bold: true, anchor: 'middle' });
  const nodeBox = (x, y, t, s, col, fill) => box(x - 90, y - 26, 180, 52, fill, col, 10) + text(x, y - 4, t, { anchor: 'middle', bold: true, fill: col, size: 13 }) + text(x, y + 14, s, { anchor: 'middle', size: 11 });
  b += nodeBox(130, 120, 'Network+', 'N10-009', C.net, C.netFill);
  b += nodeBox(130, 260, 'CCNA', '200-301 v2.0 (2027)', C.net, C.netFill);
  b += nodeBox(380, 190, 'Security+', 'SY0-801 (V8)', C.vio, C.vioFill);
  b += nodeBox(620, 110, 'CySA+', 'CS0-004 — SOC', C.ok, C.okFill);
  b += nodeBox(620, 190, 'BTL1 / CCD', 'egzaminy praktyczne', C.ok, C.okFill);
  b += nodeBox(620, 270, 'SecAI+', 'CY0-001 — AI', C.host, C.hostFill);
  b += nodeBox(620, 350, 'CSSLP / AWS / CKS', 'AppSec i chmura', C.host, C.hostFill);
  b += nodeBox(820, 150, 'SC-200', 'Microsoft SOC', C.ok, C.okFill);
  b += nodeBox(820, 240, 'GCIH / GCFA', 'IR i DFIR', C.no, C.noFill);
  b += line(220, 125, 290, 180, { arrow: true }) + line(220, 255, 290, 200, { arrow: true }) + line(130, 146, 130, 232, { color: C.line, dash: true });
  b += line(470, 180, 530, 120, { arrow: true }) + line(470, 190, 530, 190, { arrow: true }) + line(470, 200, 530, 265, { arrow: true }) + line(470, 210, 530, 345, { arrow: true });
  b += line(710, 115, 730, 140, { arrow: true }) + line(710, 195, 730, 235, { arrow: true });
  b += text(130, 330, 'sieci (część I–II toru)', { anchor: 'middle', size: 12, fill: C.net, bold: true });
  b += text(380, 260, 'fundament security', { anchor: 'middle', size: 12, fill: C.vio, bold: true });
  b += text(W / 2, 420, 'Kolejność zalecana: Network+ (lub CCNA) → Security+ → specjalizacja (CySA+ i/lub praktyczny BTL1, SC-200, GCIH; dla AppSec: CSSLP, AWS, CKS).', { anchor: 'middle', size: 12 });
  files['ns00-05-paths.svg'] = svg(W, H, 'Ścieżki certyfikacji: Network+ i CCNA prowadzą do Security+, a od niego do CySA+, egzaminów praktycznych BTL1 i CCD, SecAI+ oraz certyfikatów AppSec i chmurowych; dalej SC-200 oraz GCIH i GCFA', b);
}

// 2. Wagi domen Sec+ V8 i CySA+ V4
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Wagi domen: Security+ SY0-801 i CySA+ CS0-004', { size: 18, bold: true, anchor: 'middle' });
  const draw = (exam, x0, col) => {
    let s = text(x0, 66, `${exam.name} ${exam.code}`, { bold: true, size: 14, fill: col });
    exam.domains.forEach(([d, w], i) => {
      const y = 84 + i * 58;
      s += text(x0, y + 14, d, { size: 12 });
      s += `<rect x="${x0}" y="${y + 22}" width="${w * 9}" height="22" rx="4" fill="${col}" opacity="0.8"/>` + text(x0 + w * 9 + 8, y + 38, `${w}%`, { size: 13, bold: true });
    });
    return s;
  };
  b += draw(EXAMS[2], 40, C.vio) + draw(EXAMS[3], 480, C.ok);
  b += text(W / 2, 396, 'Największy udział w obu: operacje bezpieczeństwa (SOC, logi, detekcja, IR) — to rdzeń części III toru.', { anchor: 'middle', size: 12, bold: true });
  files['ns00-05-weights.svg'] = svg(W, H, 'Wykresy słupkowe wag domen: Security+ SY0-801 — koncepcje 16, zagrożenia 24, architektura 19, operacje 27, zarządzanie programem 14 procent; CySA+ CS0-004 — operacje 34, podatności 26, incydenty 24, raportowanie 16 procent', b);
}

// 3. Macierz rozdzialy x egzaminy
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Które rozdziały toru pokrywają który egzamin', { size: 18, bold: true, anchor: 'middle' });
  const chs = Array.from({ length: 41 }, (_, i) => i);
  const X0 = 110, CW = 18.5;
  chs.forEach((c) => { b += text(X0 + c * CW + CW / 2, 62, String(c).padStart(2, '0'), { anchor: 'middle', size: 9, fill: C.muted }); });
  const cols = [C.net, C.net, C.vio, C.ok, C.host];
  EXAMS.forEach((e, r) => {
    const y = 74 + r * 52;
    b += text(X0 - 10, y + 22, e.id, { anchor: 'end', bold: true, size: 13, fill: cols[r] });
    const cover = new Map();
    e.domains.forEach(([, w, chapters]) => chapters.forEach((c) => cover.set(c, (cover.get(c) || 0) + w)));
    chs.forEach((c) => {
      const v = cover.get(c) || 0;
      b += `<rect x="${X0 + c * CW + 1}" y="${y}" width="${CW - 2}" height="36" rx="3" fill="${v ? cols[r] : C.card}" opacity="${v ? Math.min(1, 0.25 + v / 60) : 1}" stroke="${C.line}" stroke-width="0.5"/>`;
    });
  });
  b += text(W / 2, 352, 'Intensywność koloru = suma wag domen egzaminu, które rozdział wspiera. Rozdział 00 i 39–40 wspierają wszystkie ścieżki pośrednio.', { anchor: 'middle', size: 12, fill: C.muted });
  b += text(W / 2, 376, 'Część I (00–12) to fundament Network+ i CCNA; część III (15–40) — Security+, CySA+ i SecAI+.', { anchor: 'middle', size: 12, bold: true });
  files['ns00-05-matrix.svg'] = svg(W, H, 'Mapa cieplna: wiersze to egzaminy Network+, CCNA, Security+, CySA+ i SecAI+, kolumny to rozdziały 00–40; kolorowe komórki pokazują, które rozdziały wspierają dany egzamin', b);
}

// 4. Os czasu wersji
{
  const W = 900, H = 330;
  let b = text(W / 2, 32, 'Oś czasu wersji egzaminów (stan: listopad 2026)', { size: 18, bold: true, anchor: 'middle' });
  b += line(60, 170, 860, 170, { color: C.ink, w: 3 });
  const months = (y, m) => (y - 2024) * 12 + (m - 1);
  const X = (y, m) => 70 + (months(y, m) - 3) * (740 / 39);
  [[2024, 6, 'Network+ N10-009', C.net, -1], [2026, 2, 'SecAI+ CY0-001', C.host, 1], [2026, 6, 'CySA+ CS0-004', C.ok, -1], [2026, 11, 'Security+ SY0-801', C.vio, 1], [2027, 2, 'CCNA 200-301 v2.0', C.net, -1], [2027, 6, 'koniec SY0-701 (EN)', C.no, 1]].forEach(([y, m, t, col, dir]) => {
    const x = X(y, m);
    b += `<circle cx="${x}" cy="170" r="8" fill="${col}"/>` + line(x, 170, x, 170 + dir * 50, { color: col });
    b += text(x, 170 + dir * 62 + (dir > 0 ? 12 : 0), t, { anchor: 'middle', size: 12, bold: true, fill: col });
    b += text(x, 170 + dir * 62 + (dir > 0 ? 28 : -16), `${String(m).padStart(2, '0')}.${y}`, { anchor: 'middle', size: 11, fill: C.muted });
  });
  b += `<line x1="${X(2026, 11)}" y1="120" x2="${X(2026, 11)}" y2="220" stroke="${C.no}" stroke-dasharray="4 4"/>` + text(X(2026, 11) - 6, 132, 'dziś', { size: 11, fill: C.no, bold: true, anchor: 'end' });
  b += text(W / 2, 312, 'Wersje egzaminów zmieniają się co ok. 3 lata — przed zapisem zawsze sprawdź „exam objectives” i daty u wydawcy.', { anchor: 'middle', size: 12, bold: true });
  files['ns00-05-timeline.svg'] = svg(W, H, 'Oś czasu: Network+ N10-009 od czerwca 2024, SecAI+ od lutego 2026, CySA+ CS0-004 od czerwca 2026, Security+ SY0-801 od listopada 2026, CCNA v2.0 od lutego 2027, koniec SY0-701 w czerwcu 2027', b);
}

writeSvgs(process.argv[2], files);
