// Ilustracje do lekcji _ns_01/06_PerformanceMetrics.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_06_metrics_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Wodospad curl -w przed i po netem
{
  const W = 900, H = 400;
  let b = text(W / 2, 34, 'Gdzie ucieka czas żądania HTTPS? (curl -w, przed i po +100 ms opóźnienia)', { size: 18, bold: true, anchor: 'middle' });
  const phases = [['DNS', C.muted], ['TCP connect', C.net], ['TLS', C.vio], ['czekanie na serwer (TTFB)', C.host], ['transfer', C.ok]];
  const rows = [['przed (RTT 2 ms)', [4, 2, 5, 32, 3]], ['po netem 100 ms', [4, 102, 106, 132, 103]]];
  const scale = 1.55, X0 = 200;
  rows.forEach(([label, vals], r) => {
    const y = 80 + r * 110;
    b += text(X0 - 12, y + 26, label, { anchor: 'end', bold: true, size: 13 });
    let x = X0;
    vals.forEach((v, i) => {
      const w = Math.max(3, v * scale);
      b += `<rect x="${x}" y="${y}" width="${w}" height="40" rx="3" fill="${phases[i][1]}" opacity="0.85"/>`;
      if (w > 34) b += text(x + w / 2, y + 25, `${v} ms`, { anchor: 'middle', size: 12, fill: '#fff', bold: true });
      x += w;
    });
    b += text(x + 8, y + 25, `razem ${vals.reduce((a, c) => a + c, 0)} ms`, { size: 13, bold: true });
  });
  phases.forEach(([n, col], i) => { b += `<rect x="${60 + i * 165}" y="300" width="14" height="14" rx="3" fill="${col}"/>` + text(80 + i * 165, 312, n, { size: 12 }); });
  b += text(W / 2, 350, 'Dodatkowe 100 ms RTT nie dodaje 100 ms — dodaje je w każdej fazie wymagającej wymiany pakietów:', { anchor: 'middle', size: 13 });
  b += text(W / 2, 372, 'handshake TCP (1 RTT), TLS 1.3 (1 RTT), żądanie i pierwsza odpowiedź (1 RTT) oraz transfer.', { anchor: 'middle', size: 13, bold: true });
  files['ns01-06-curl-timeline.svg'] = svg(W, H, 'Wodospad faz żądania HTTPS z curl: DNS, połączenie TCP, TLS, czekanie na serwer i transfer — przy RTT 2 ms razem 46 ms, po dodaniu 100 ms opóźnienia razem 447 ms', b);
}

// 2. Skladowe opoznienia
{
  const W = 900, H = 400;
  let b = text(W / 2, 34, 'Cztery składowe opóźnienia jednego pakietu', { size: 19, bold: true, anchor: 'middle' });
  const comps = [
    ['Przetwarzanie', 'router czyta nagłówek, szuka trasy', 'µs', C.muted],
    ['Kolejkowanie', 'pakiet czeka w buforze na wolne łącze', '0 – setki ms', C.no],
    ['Transmisja (serializacja)', 'wypchnięcie bitów: rozmiar × 8 / przepustowość', '1500 B @ 10 Mb/s = 1,2 ms', C.host],
    ['Propagacja', 'sygnał biegnie przez medium: odległość / prędkość', '1000 km światłowodu ≈ 5 ms', C.net],
  ];
  comps.forEach(([n, d, ex, col], i) => {
    const y = 64 + i * 72;
    b += box(30, y, 840, 60, C.card, col, 8);
    b += `<rect x="30" y="${y}" width="10" height="60" rx="4" fill="${col}"/>`;
    b += text(56, y + 25, n, { bold: true, size: 15, fill: col });
    b += text(56, y + 47, d, { size: 13 });
    b += text(855, y + 37, ex, { size: 13, anchor: 'end', mono: true });
  });
  b += text(W / 2, 370, 'opóźnienie jednokierunkowe = przetwarzanie + kolejkowanie + transmisja + propagacja (na każdym odcinku)', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 392, 'Szybsze łącze skraca tylko transmisję; propagacji nie skróci nic poza krótszą trasą (bliższy region, CDN).', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-06-delay-components.svg'] = svg(W, H, 'Cztery składowe opóźnienia: przetwarzanie, kolejkowanie, transmisja i propagacja z przykładowymi wartościami', b);
}

// 3. BDP - rura
{
  const W = 900, H = 420;
  let b = text(W / 2, 34, 'Bandwidth-delay product: ile danych mieści się „w rurze”', { size: 19, bold: true, anchor: 'middle' });
  b += `<rect x="120" y="70" width="660" height="110" rx="55" fill="${C.netFill}" stroke="${C.net}" stroke-width="2"/>`;
  b += text(450, 62, 'RTT = 50 ms (długość rury)', { anchor: 'middle', size: 13, bold: true, fill: C.net });
  b += text(800, 130, '100 Mb/s', { size: 13, bold: true, fill: C.net });
  b += text(800, 148, '(średnica)', { size: 12, fill: C.muted });
  for (let i = 0; i < 20; i++) b += `<rect x="${140 + i * 31}" y="105" width="24" height="40" rx="4" fill="${C.ok}" opacity="0.8"/>`;
  b += text(450, 208, 'BDP = 100 Mb/s × 0,05 s = 5 Mb = 625 000 bajtów „w locie”', { anchor: 'middle', size: 15, bold: true });
  b += box(60, 236, 380, 150, C.noFill, C.no);
  b += text(250, 262, 'Okno 64 KiB (bez skalowania)', { anchor: 'middle', bold: true, fill: C.no });
  for (let i = 0; i < 2; i++) b += `<rect x="${90 + i * 31}" y="282" width="24" height="30" rx="4" fill="${C.ok}" opacity="0.8"/>`;
  b += text(250, 340, '65 535 B / 0,05 s ≈ 10,5 Mb/s', { anchor: 'middle', mono: true, size: 13 });
  b += text(250, 364, 'rura prawie pusta: nadawca czeka na ACK', { anchor: 'middle', size: 12 });
  b += box(460, 236, 380, 150, C.okFill, C.ok);
  b += text(650, 262, 'Okno ≥ BDP (skalowanie okna)', { anchor: 'middle', bold: true, fill: C.ok });
  for (let i = 0; i < 10; i++) b += `<rect x="${480 + i * 34}" y="282" width="26" height="30" rx="4" fill="${C.ok}" opacity="0.8"/>`;
  b += text(650, 340, 'okno 1 MB → do 100 Mb/s', { anchor: 'middle', mono: true, size: 13 });
  b += text(650, 364, 'rura pełna: łącze w pełni wykorzystane', { anchor: 'middle', size: 12 });
  files['ns01-06-bdp-pipe.svg'] = svg(W, H, 'Rura o średnicy 100 Mb/s i długości RTT 50 ms mieści 625 000 bajtów; okno 64 KiB wypełnia ją w małej części (około 10,5 Mb/s), okno co najmniej równe BDP pozwala wykorzystać pełne 100 Mb/s', b);
}

// 4. Percentyle - dlugi ogon
{
  const W = 900, H = 400;
  let b = text(W / 2, 34, 'Rozkład czasów odpowiedzi: dlaczego średnia kłamie', { size: 19, bold: true, anchor: 'middle' });
  const bars = [2, 10, 34, 62, 70, 55, 38, 24, 15, 10, 7, 5, 4, 3, 3, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1];
  const X0 = 70, BW = 27, Y0 = 320;
  bars.forEach((v, i) => { b += `<rect x="${X0 + i * BW}" y="${Y0 - v * 2.9}" width="${BW - 3}" height="${v * 2.9}" fill="${i < 9 ? C.net : C.host}" opacity="0.8"/>`; });
  b += line(X0, Y0, X0 + bars.length * BW, Y0, { color: C.ink });
  b += text(X0, Y0 + 20, '0 ms', { size: 12, fill: C.muted }) + text(X0 + bars.length * BW, Y0 + 20, '1400 ms', { size: 12, fill: C.muted, anchor: 'end' });
  const mark = (ms, label, col) => { const x = X0 + (ms / 50) * BW; return line(x, 62, x, Y0, { color: col, w: 2, dash: true }) + text(x + 4, 72, label, { size: 13, bold: true, fill: col }); };
  b += mark(210, 'p50 = 210 ms', C.ok);
  b += mark(290, 'średnia = 290 ms', C.muted).replace('y="72"', 'y="92"');
  b += mark(640, 'p95 = 640 ms', C.host);
  b += mark(1180, 'p99 = 1180 ms', C.no);
  b += text(W / 2, 362, 'Średnia nie opisuje żadnego realnego użytkownika: połowa ma szybciej niż 210 ms, a co setne żądanie trwa ponad sekundę.', { anchor: 'middle', size: 13 });
  b += text(W / 2, 386, 'Strona wołająca 50 usług trafi choć raz w p99 z prawdopodobieństwem 1 − 0,99^50 ≈ 39,5%.', { anchor: 'middle', size: 13, bold: true });
  files['ns01-06-percentiles.svg'] = svg(W, H, 'Histogram czasów odpowiedzi z długim ogonem: mediana 210 ms, średnia 290 ms, p95 640 ms i p99 1180 ms', b);
}

writeSvgs(process.argv[2], files);
