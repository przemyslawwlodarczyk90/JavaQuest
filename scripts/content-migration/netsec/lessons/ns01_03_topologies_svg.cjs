// Ilustracje do lekcji _ns_01/03_NetworkTopologiesAndTypes.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_03_topologies_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, node, writeSvgs } = require('./lib.cjs');
const files = {};

const poly = (cx, cy, r, n, start = -Math.PI / 2) => Array.from({ length: n }, (_, i) => [cx + r * Math.cos(start + (2 * Math.PI * i) / n), cy + r * Math.sin(start + (2 * Math.PI * i) / n)]);

// 1. Przeglad topologii
{
  const W = 900, H = 560;
  let b = text(W / 2, 34, 'Topologie fizyczne: kto jest połączony z kim', { size: 19, bold: true, anchor: 'middle' });
  const panels = [
    [30, 'Magistrala (bus)', 'wspólny kabel, terminatory', '1 medium, n przyłączy'],
    [320, 'Pierścień (ring)', 'każdy ma dwóch sąsiadów', 'n łączy'],
    [610, 'Gwiazda (star)', 'wszyscy do przełącznika', 'n łączy'],
  ];
  const panels2 = [
    [30, 'Drzewo / rozszerzona gwiazda', 'gwiazdy połączone hierarchicznie', 'n − 1 łączy'],
    [320, 'Siatka pełna (full mesh)', 'każdy z każdym', 'n(n − 1)/2 łączy'],
    [610, 'Hub-and-spoke', 'oddziały tylko do centrali', 'n − 1 łączy'],
  ];
  const drawPanel = (x, y, [_, title, sub, formula]) => box(x, y, 260, 240) + text(x + 130, y + 26, title, { anchor: 'middle', bold: true }) +
    text(x + 130, y + 46, sub, { anchor: 'middle', size: 12, fill: C.muted }) + text(x + 130, y + 228, formula, { anchor: 'middle', size: 13, mono: true, fill: C.net, bold: true });
  panels.forEach((p) => { b += drawPanel(p[0], 60, p); });
  panels2.forEach((p) => { b += drawPanel(p[0], 310, p); });
  // bus
  b += line(55, 180, 265, 180, { color: C.ink, w: 4 });
  b += `<rect x="47" y="172" width="8" height="16" fill="${C.no}"/><rect x="265" y="172" width="8" height="16" fill="${C.no}"/>`;
  [80, 130, 180, 230].forEach((x, i) => { b += line(x, 180, x, i % 2 ? 220 : 140) + node(x, i % 2 ? 226 : 134, String.fromCharCode(65 + i)); });
  // ring
  const rp = poly(450, 185, 56, 6);
  rp.forEach((p, i) => { const q = rp[(i + 1) % 6]; b += line(p[0], p[1], q[0], q[1], { color: C.ink }); });
  rp.forEach((p, i) => { b += node(p[0], p[1], String.fromCharCode(65 + i)); });
  // star
  const sp = poly(740, 185, 58, 6);
  sp.forEach((p) => { b += line(740, 185, p[0], p[1], { color: C.ink }); });
  b += node(740, 185, 'SW', { square: true, w: 22, fill: C.netFill, stroke: C.net, color: C.net });
  sp.forEach((p, i) => { b += node(p[0], p[1], String.fromCharCode(65 + i)); });
  // tree
  b += node(160, 380, 'Core', { square: true, w: 30, fill: C.netFill, stroke: C.net, color: C.net });
  [[95, 445], [225, 445]].forEach(([x, y], k) => {
    b += line(160, 396, x, y - 16, { color: C.ink }) + node(x, y, 'SW' + (k + 1), { square: true, w: 26, fill: C.netFill, stroke: C.net, color: C.net });
    [x - 40, x, x + 40].forEach((hx, j) => { b += line(x, y + 16, hx, 500 - 14, { color: C.ink }) + node(hx, 500, String.fromCharCode(65 + k * 3 + j), { r: 13, size: 11 }); });
  });
  // full mesh
  const mp = poly(450, 448, 60, 5);
  mp.forEach((p, i) => mp.forEach((q, j) => { if (j > i) b += line(p[0], p[1], q[0], q[1], { color: C.ink, w: 1.5 }); }));
  mp.forEach((p, i) => { b += node(p[0], p[1], String.fromCharCode(65 + i)); });
  // hub and spoke
  const hp = poly(740, 448, 62, 5);
  hp.forEach((p) => { b += line(740, 448, p[0], p[1], { color: C.ink }); });
  b += node(740, 448, 'HQ', { square: true, w: 22, fill: C.hostFill, stroke: C.host, color: C.host });
  hp.forEach((p, i) => { b += node(p[0], p[1], 'O' + (i + 1), { size: 11 }); });
  files['ns01-03-topologies.svg'] = svg(W, H, 'Sześć topologii fizycznych z liczbą łączy: magistrala, pierścień, gwiazda, drzewo, siatka pełna, hub-and-spoke', b);
}

// 2. Analiza awarii
{
  const W = 900, H = 380;
  let b = text(W / 2, 34, 'Co przestaje działać po jednej awarii?', { size: 19, bold: true, anchor: 'middle' });
  const X = (x, y) => line(x - 10, y - 10, x + 10, y + 10, { color: C.no, w: 4 }) + line(x - 10, y + 10, x + 10, y - 10, { color: C.no, w: 4 });
  // star: awaria przelacznika
  b += box(20, 56, 280, 300);
  b += text(160, 82, 'Gwiazda: pada przełącznik', { anchor: 'middle', bold: true });
  const sp = poly(160, 200, 80, 6);
  sp.forEach((p) => { b += line(160, 200, p[0], p[1], { color: C.line, dash: true }); });
  b += node(160, 200, 'SW', { square: true, w: 22, fill: C.noFill, stroke: C.no, color: C.no }) + X(160, 200);
  sp.forEach((p, i) => { b += node(p[0], p[1], String.fromCharCode(65 + i), { fill: C.noFill, stroke: C.no }); });
  b += text(160, 316, 'SPOF: nikt z nikim', { anchor: 'middle', bold: true, fill: C.no });
  b += text(160, 338, 'awaria łącza A: odcięty tylko A', { anchor: 'middle', size: 12, fill: C.muted });
  // ring: jedno lacze
  b += box(310, 56, 280, 300);
  b += text(450, 82, 'Pierścień: pęka jedno łącze', { anchor: 'middle', bold: true });
  const rp = poly(450, 200, 80, 6);
  rp.forEach((p, i) => { const q = rp[(i + 1) % 6]; b += line(p[0], p[1], q[0], q[1], { color: i === 1 ? C.line : C.ok, dash: i === 1 }); });
  b += X((rp[1][0] + rp[2][0]) / 2, (rp[1][1] + rp[2][1]) / 2);
  rp.forEach((p, i) => { b += node(p[0], p[1], String.fromCharCode(65 + i), { fill: C.okFill, stroke: C.ok }); });
  b += text(450, 316, 'ruch idzie „dookoła”', { anchor: 'middle', bold: true, fill: C.ok });
  b += text(450, 338, 'druga awaria dzieli pierścień na dwa', { anchor: 'middle', size: 12, fill: C.muted });
  // mesh
  b += box(600, 56, 280, 300);
  b += text(740, 82, 'Siatka pełna: pada węzeł C', { anchor: 'middle', bold: true });
  const mp = poly(740, 200, 80, 5);
  mp.forEach((p, i) => mp.forEach((q, j) => { if (j > i) b += line(p[0], p[1], q[0], q[1], { color: i === 2 || j === 2 ? C.line : C.ok, w: 1.5, dash: i === 2 || j === 2 }); }));
  mp.forEach((p, i) => { b += node(p[0], p[1], String.fromCharCode(65 + i), i === 2 ? { fill: C.noFill, stroke: C.no } : { fill: C.okFill, stroke: C.ok }); });
  b += X(mp[2][0], mp[2][1]);
  b += text(740, 316, 'reszta działa bez zmian', { anchor: 'middle', bold: true, fill: C.ok });
  b += text(740, 338, 'cena: n(n − 1)/2 łączy', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-03-failure.svg'] = svg(W, H, 'Skutki pojedynczej awarii: gwiazda traci wszystko przy awarii przełącznika, pierścień przekierowuje ruch, siatka pełna działa dalej', b);
}

// 3. Trojwarstwowa vs spine-leaf
{
  const W = 900, H = 430;
  let b = text(W / 2, 34, 'Data center: model trójwarstwowy vs spine-leaf', { size: 19, bold: true, anchor: 'middle' });
  b += box(20, 56, 420, 356);
  b += text(230, 82, 'Trójwarstwowy (core / distribution / access)', { anchor: 'middle', bold: true, size: 14 });
  const core = [[170, 125], [290, 125]];
  const dist = [[110, 210], [200, 210], [260, 210], [350, 210]];
  const acc = [[70, 295], [130, 295], [190, 295], [270, 295], [330, 295], [390, 295]];
  dist.forEach((d, i) => core.forEach((c) => { b += line(c[0], c[1] + 16, d[0], d[1] - 16, { color: C.line }); }));
  acc.forEach((a, i) => { const pair = i < 3 ? [dist[0], dist[1]] : [dist[2], dist[3]]; pair.forEach((d) => { b += line(d[0], d[1] + 16, a[0], a[1] - 16, { color: C.line }); }); });
  core.forEach(([x, y]) => { b += node(x, y, 'core', { square: true, w: 28, fill: C.vioFill, stroke: C.vio, color: C.vio, size: 11 }); });
  dist.forEach(([x, y]) => { b += node(x, y, 'dist', { square: true, w: 24, fill: C.netFill, stroke: C.net, color: C.net, size: 11 }); });
  acc.forEach(([x, y]) => { b += node(x, y, 'acc', { square: true, w: 22, size: 11 }); });
  b += `<path d="M70,312 C70,380 390,380 390,312" fill="none" stroke="${C.host}" stroke-width="3" marker-end="url(#arr)"/>`;
  b += text(230, 372, 'serwer → serwer między blokami: do 5 przeskoków', { anchor: 'middle', size: 12, fill: C.host, bold: true });
  b += text(230, 396, 'zaprojektowany dla ruchu north–south (klient ↔ serwer)', { anchor: 'middle', size: 12, fill: C.muted });
  b += box(460, 56, 420, 356);
  b += text(670, 82, 'Spine-leaf (sieć Clos)', { anchor: 'middle', bold: true, size: 14 });
  const spines = [[560, 140], [670, 140], [780, 140]];
  const leaves = [[510, 270], [600, 270], [690, 270], [780, 270], [850, 270]].map(([x, y]) => [x - 10, y]);
  leaves.forEach((l) => spines.forEach((s) => { b += line(s[0], s[1] + 16, l[0], l[1] - 16, { color: C.line }); }));
  spines.forEach(([x, y]) => { b += node(x, y, 'spine', { square: true, w: 30, fill: C.vioFill, stroke: C.vio, color: C.vio, size: 11 }); });
  leaves.forEach(([x, y]) => { b += node(x, y, 'leaf', { square: true, w: 24, fill: C.netFill, stroke: C.net, color: C.net, size: 11 }); });
  b += line(spines[0][0], 156, leaves[0][0], 254, { color: C.ok, w: 3 }) + line(spines[0][0], 156, leaves[4][0], 254, { color: C.ok, w: 3 });
  b += text(670, 320, 'każdy leaf połączony z każdym spine', { anchor: 'middle', size: 13, bold: true });
  b += text(670, 346, 'serwer → serwer: zawsze leaf → spine → leaf', { anchor: 'middle', size: 12, fill: C.ok, bold: true });
  b += text(670, 370, 'ECMP po wszystkich spine; skalowanie = dodaj spine/leaf', { anchor: 'middle', size: 12, fill: C.muted });
  b += text(670, 396, 'zaprojektowany dla ruchu east–west (mikroserwisy)', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-03-three-tier-vs-spine-leaf.svg'] = svg(W, H, 'Porównanie architektury trójwarstwowej z przełącznikami core, distribution i access oraz architektury spine-leaf, w której każdy leaf łączy się z każdym spine', b);
}

// 4. Docker: fizyczna vs logiczna topologia
{
  const W = 900, H = 400;
  let b = text(W / 2, 34, 'Jeden host fizycznie, dwie sieci logicznie (Docker Compose)', { size: 19, bold: true, anchor: 'middle' });
  b += box(20, 56, 270, 320);
  b += text(155, 84, 'Fizycznie', { anchor: 'middle', bold: true });
  b += box(50, 110, 210, 200, C.card, C.ink);
  b += text(155, 134, 'jeden laptop / VM', { anchor: 'middle', size: 13 });
  ['proxy', 'app', 'db'].forEach((n, i) => { b += box(80, 150 + i * 48, 150, 36, C.hostFill, C.host, 6) + text(155, 173 + i * 48, n, { anchor: 'middle', bold: true, fill: C.host }); });
  b += text(155, 340, 'jedna karta sieciowa, jeden kabel', { anchor: 'middle', size: 12, fill: C.muted });
  b += box(310, 56, 570, 320);
  b += text(595, 84, 'Logicznie: dwa bridge’e = dwie gwiazdy', { anchor: 'middle', bold: true });
  b += box(330, 110, 250, 210, C.netFill, C.net);
  b += text(455, 134, 'sieć frontend', { anchor: 'middle', bold: true, fill: C.net });
  b += box(610, 110, 250, 210, C.vioFill, C.vio);
  b += text(735, 134, 'sieć backend (internal: true)', { anchor: 'middle', bold: true, fill: C.vio, size: 13 });
  b += node(455, 180, 'br-frontend', { square: true, w: 48, size: 11, fill: C.card, stroke: C.net, color: C.net });
  b += node(735, 180, 'br-backend', { square: true, w: 48, size: 11, fill: C.card, stroke: C.vio, color: C.vio });
  b += line(455, 196, 400, 254, { color: C.net }) + node(400, 270, 'proxy', { square: true, w: 34, size: 12 });
  b += line(455, 196, 595, 254, { color: C.net }) + line(735, 196, 595, 254, { color: C.vio }) + node(595, 270, 'app', { square: true, w: 34, size: 12, fill: C.hostFill, stroke: C.host, color: C.host });
  b += line(735, 196, 800, 254, { color: C.vio }) + node(800, 270, 'db', { square: true, w: 30, size: 12 });
  b += text(595, 350, 'proxy nie ma żadnej ścieżki do bazy; tylko app należy do obu sieci', { anchor: 'middle', size: 13, bold: true, fill: C.ok });
  files['ns01-03-docker-logical.svg'] = svg(W, H, 'Ten sam host fizyczny z kontenerami proxy, app i db oraz logiczna topologia dwóch sieci Docker: frontend i backend, gdzie tylko app należy do obu', b);
}

writeSvgs(process.argv[2], files);
