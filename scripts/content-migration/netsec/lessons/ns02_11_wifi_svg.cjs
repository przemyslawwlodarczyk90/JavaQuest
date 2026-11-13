// Ilustracje do lekcji _ns_02/11_WifiForDevelopers.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_11_wifi_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { freq24, stats, samples } = require('./ns02_11_wifi_model.cjs');
const files = {};

// 1. Kanaly 2,4 GHz
{
  const W = 900, H = 360;
  let b = text(W / 2, 32, 'Pasmo 2,4 GHz: 13 kanałów co 5 MHz, ale tylko 3 się nie nakładają', { size: 17, bold: true, anchor: 'middle' });
  const x0 = 60, x1 = 840, f0 = 2400, f1 = 2490; const X = (f) => x0 + ((f - f0) / (f1 - f0)) * (x1 - x0);
  b += line(x0, 280, x1, 280, { color: C.ink });
  for (let f = 2400; f <= 2490; f += 10) b += line(X(f), 280, X(f), 286, { color: C.ink }) + text(X(f), 302, String(f), { anchor: 'middle', size: 10 });
  b += text(x1, 322, 'MHz', { anchor: 'end', size: 11, fill: C.muted });
  for (let ch = 1; ch <= 13; ch++) {
    const c = freq24(ch); const main = [1, 6, 11].includes(ch);
    const col = main ? (ch === 1 ? C.net : ch === 6 ? C.ok : C.host) : C.line;
    const y = main ? 270 : 270 - ((ch % 3) + 1) * 10;
    b += `<path d="M${X(c - 11)},${y + 10} Q${X(c)},${main ? 90 : 150} ${X(c + 11)},${y + 10}" fill="${main ? (ch === 1 ? C.netFill : ch === 6 ? C.okFill : C.hostFill) : 'none'}" fill-opacity="${main ? 0.6 : 0}" stroke="${col}" stroke-width="${main ? 2.5 : 1}"/>`;
    b += text(X(c), main ? 130 : 180 + (ch % 3) * 14, String(ch), { anchor: 'middle', size: main ? 15 : 10, bold: main, fill: main ? col : C.muted });
  }
  b += box(560, 50, 290, 56, C.card, C.line, 8) + text(575, 72, 'Kanał = środek co 5 MHz, szerokość ~22 MHz', { size: 11 }) + text(575, 92, 'Używaj 1, 6, 11 — inaczej wzajemne zakłócenia', { size: 11, bold: true });
  b += text(W / 2, 346, 'W 5 GHz i 6 GHz kanałów jest wielokrotnie więcej — tam stawia się sieci firmowe i ruch wymagający niskich opóźnień.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-11-channels.svg'] = svg(W, H, 'Widmo pasma 2,4 GHz od 2400 do 2490 MHz z trzynastoma kanałami co 5 MHz o szerokości około 22 MHz; wyróżnione kanały 1, 6 i 11, które się nie nakładają, pozostałe zachodzą na sąsiednie. W pasmach 5 i 6 GHz kanałów jest dużo więcej.', b);
}

// 2. CSMA/CA i retransmisje
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Wi-Fi to medium współdzielone: czekaj, nadawaj, czekaj na ACK, powtarzaj', { size: 17, bold: true, anchor: 'middle' });
  const rows = [['Twój laptop', C.net], ['telefon sąsiada', C.host], ['punkt dostępowy', C.ok]];
  rows.forEach(([n, col], i) => { b += text(40, 92 + i * 60, n, { bold: true, size: 12, fill: col }); b += line(170, 88 + i * 60, 860, 88 + i * 60, { color: C.line, w: 1 }); });
  const seg = (row, x, w, label, fill, col) => box(x, 72 + row * 60, w, 32, fill, col, 4) + text(x + w / 2, 93 + row * 60, label, { anchor: 'middle', size: 10, bold: true, fill: col });
  b += seg(0, 180, 40, 'DIFS', C.card, C.muted) + seg(0, 222, 60, 'backoff', C.card, C.muted) + seg(0, 284, 110, 'ramka', C.netFill, C.net);
  b += seg(2, 398, 30, 'ACK', C.okFill, C.ok);
  b += seg(1, 440, 40, 'DIFS', C.card, C.muted) + seg(1, 482, 120, 'ramka sąsiada', C.hostFill, C.host) + seg(2, 606, 30, 'ACK', C.okFill, C.ok);
  b += seg(0, 440, 70, 'czeka…', C.card, C.muted) + seg(0, 645, 110, 'ramka (kolizja)', C.noFill, C.no) + seg(1, 660, 90, 'ramka', C.noFill, C.no);
  b += seg(0, 762, 90, 'retransmisja', C.netFill, C.net);
  b += box(40, 260, 820, 104, C.card, C.line, 8);
  b += text(60, 284, 'Brak ACK = retransmisja w warstwie 2 (do kilku razy, coraz dłuższy backoff) — dla TCP i Twojej aplikacji to „tylko” większe opóźnienie.', { size: 12 });
  b += text(60, 308, 'Wszyscy w kanale dzielą czas antenowy (airtime): wolny klient (daleko, stary standard) spowalnia wszystkich.', { size: 12 });
  b += text(60, 332, 'Efekt: zmienne RTT (jitter), długi ogon p99, przepustowość realnie ~50–60% prędkości PHY, half-duplex.', { size: 12, bold: true, fill: C.no });
  b += text(60, 354, 'Wi-Fi 6/7 (OFDMA, MU-MIMO, BSS coloring) łagodzi problem, ale go nie usuwa.', { size: 12, fill: C.muted });
  files['ns02-11-csma.svg'] = svg(W, H, 'Oś czasu trzech stacji: laptop czeka DIFS i losowy backoff, nadaje ramkę, punkt dostępowy odsyła ACK; telefon sąsiada nadaje swoją ramkę, a laptop czeka; potem obie stacje nadają jednocześnie, dochodzi do kolizji i laptop retransmituje. Wnioski: retransmisje warstwy 2 zwiększają opóźnienie, airtime jest współdzielony, RTT jest zmienne, przepustowość to ok. 50–60% prędkości PHY.', b);
}

// 3. Rozklad RTT kabel vs Wi-Fi
{
  const W = 900, H = 400;
  const wi = samples('wifi'), wd = samples('wired');
  const sw = stats(wi), sd = stats(wd);
  let b = text(W / 2, 32, 'Ten sam ping, 200 próbek: kabel vs Wi-Fi', { size: 18, bold: true, anchor: 'middle' });
  const x0 = 80, x1 = 860, y0 = 300, maxV = 160; const X = (i) => x0 + (i / 199) * (x1 - x0); const Y = (v) => y0 - (v / maxV) * 230;
  b += line(x0, y0, x1, y0, { color: C.ink }) + line(x0, y0, x0, 60, { color: C.ink });
  [0, 40, 80, 120, 160].forEach((v) => { b += text(x0 - 8, Y(v) + 4, `${v}`, { anchor: 'end', size: 10 }) + line(x0, Y(v), x1, Y(v), { color: C.line, w: 1, dash: true }); });
  b += text(40, 56, 'RTT ms', { size: 11, fill: C.muted });
  b += `<polyline points="${wi.map((v, i) => `${X(i).toFixed(1)},${Y(v).toFixed(1)}`).join(' ')}" fill="none" stroke="${C.host}" stroke-width="1.5"/>`;
  b += `<polyline points="${wd.map((v, i) => `${X(i).toFixed(1)},${Y(v).toFixed(1)}`).join(' ')}" fill="none" stroke="${C.net}" stroke-width="2"/>`;
  b += box(80, 318, 380, 70, C.netFill, C.net, 8) + text(96, 340, 'kabel', { bold: true, fill: C.net }) + text(96, 362, `p50 ${sd.p50} ms · p99 ${sd.p99} ms · max ${sd.max} ms`, { mono: true, size: 12 }) + text(96, 380, `jitter ${sd.jitter} ms`, { mono: true, size: 12 });
  b += box(480, 318, 380, 70, C.hostFill, C.host, 8) + text(496, 340, 'Wi-Fi', { bold: true, fill: C.host }) + text(496, 362, `p50 ${sw.p50} ms · p99 ${sw.p99} ms · max ${sw.max} ms`, { mono: true, size: 12 }) + text(496, 380, `jitter ${sw.jitter} ms`, { mono: true, size: 12 });
  files['ns02-11-rtt.svg'] = svg(W, H, `Wykres 200 próbek RTT: przez kabel niemal płaska linia poniżej 1 ms (p50 ${sd.p50} ms, p99 ${sd.p99} ms), przez Wi-Fi wartości 2–6 ms z częstymi skokami do kilkudziesięciu i pojedynczymi powyżej 100 ms (p50 ${sw.p50} ms, p99 ${sw.p99} ms, jitter ${sw.jitter} ms).`, b);
}

// 4. Zabezpieczenia
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Tryby zabezpieczeń Wi-Fi: co chroni, a co nie', { size: 18, bold: true, anchor: 'middle' });
  const cols = [['tryb', 190], ['uwierzytelnianie', 230], ['ocena', 150], ['uwagi', 250]];
  let x = 30; const xs = [];
  cols.forEach(([n, w]) => { xs.push([x, w]); b += box(x, 50, w - 4, 32, C.netFill, C.net, 4) + text(x + 8, 71, n, { bold: true, size: 12, fill: C.net }); x += w; });
  const rows = [
    ['otwarta', 'brak', 'NIE', 'ruch jawny w eterze', C.noFill],
    ['WEP / WPA (TKIP)', 'klucz statyczny', 'NIE', 'złamane, wycofane', C.noFill],
    ['WPA2-Personal', 'PSK (hasło)', 'warunkowo', 'atak słownikowy offline', C.hostFill],
    ['WPA2-Enterprise', '802.1X / RADIUS', 'TAK', 'weryfikuj certyfikat serwera!', C.okFill],
    ['WPA3-Personal', 'SAE (Dragonfly)', 'TAK', 'odporne na słownik offline', C.okFill],
    ['WPA3-Enterprise', '802.1X (+192-bit)', 'TAK', 'standard w firmach', C.okFill],
    ['OWE (Enhanced Open)', 'brak, ale szyfrowanie', 'częściowo', 'hotspoty: szyfr bez hasła', C.vioFill],
  ];
  rows.forEach((r, i) => { const y = 88 + i * 38; r.slice(0, 4).forEach((c, j) => { const [cx, w] = xs[j]; b += box(cx, y, w - 4, 34, j === 2 ? r[4] : C.card, C.line, 3) + text(cx + 8, y + 22, c, { size: 12, bold: j === 0 || j === 2 }); }); });
  b += text(W / 2, 372, 'PMF (802.11w) chroni ramki zarządzające przed fałszywym rozłączaniem — obowiązkowe w WPA3.', { anchor: 'middle', size: 12, bold: true });
  b += text(W / 2, 392, 'W sieci publicznej i tak zakładaj, że sieć jest wroga: TLS wszędzie, VPN, brak zaufania do captive portali.', { anchor: 'middle', size: 12, fill: C.no });
  files['ns02-11-security.svg'] = svg(W, H, 'Tabela trybów zabezpieczeń Wi-Fi: sieć otwarta i WEP/WPA z TKIP niebezpieczne; WPA2-Personal z PSK warunkowo (atak słownikowy offline); WPA2-Enterprise z 802.1X bezpieczne przy weryfikacji certyfikatu; WPA3-Personal z SAE i WPA3-Enterprise bezpieczne; OWE szyfruje bez hasła. PMF chroni ramki zarządzające.', b);
}

writeSvgs(process.argv[2], files);
