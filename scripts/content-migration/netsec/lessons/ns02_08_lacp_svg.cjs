// Ilustracje do lekcji _ns_02/08_EtherChannelLacp.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_08_lacp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { negotiate, MODES } = require('./ns02_08_lacp_model.cjs');
const files = {};

// 1. STP blokuje vs EtherChannel laczy
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Dwa kable między przełącznikami: STP blokuje jeden, EtherChannel używa obu', { size: 17, bold: true, anchor: 'middle' });
  const pair = (ox, agg) => {
    let s = box(ox, 56, 400, 250, C.card, agg ? C.ok : C.no, 10) + text(ox + 200, 82, agg ? 'Port-channel1 (LACP): 2 × 1G aktywne' : 'Bez agregacji: 1 × 1G aktywne', { anchor: 'middle', bold: true, fill: agg ? C.ok : C.no, size: 14 });
    s += box(ox + 130, 100, 140, 40, C.netFill, C.net, 8) + text(ox + 200, 125, 'SW1', { anchor: 'middle', bold: true, fill: C.net });
    s += box(ox + 130, 230, 140, 40, C.netFill, C.net, 8) + text(ox + 200, 255, 'SW2', { anchor: 'middle', bold: true, fill: C.net });
    s += line(ox + 170, 142, ox + 170, 228, { color: C.ok, w: 4 });
    s += line(ox + 230, 142, ox + 230, 228, { color: agg ? C.ok : C.line, w: 4, dash: !agg });
    if (agg) s += `<ellipse cx="${ox + 200}" cy="185" rx="62" ry="22" fill="none" stroke="${C.vio}" stroke-width="2"/>` + text(ox + 290, 190, 'jedno łącze logiczne', { size: 11, fill: C.vio, bold: true });
    else s += box(ox + 218, 172, 24, 24, C.noFill, C.no, 4) + text(ox + 230, 189, '✕', { anchor: 'middle', bold: true, fill: C.no }) + text(ox + 252, 190, 'STP: alternate', { size: 11, fill: C.no, bold: true });
    s += text(ox + 200, 296, agg ? 'STP widzi Po1 jako jeden port · awaria kabla = brak zmiany topologii' : 'awaria aktywnego kabla = przebudowa STP', { anchor: 'middle', size: 11 });
    return s;
  };
  b += pair(40, false) + pair(460, true);
  b += text(W / 2, 340, 'Agregat zwiększa sumaryczną przepustowość i odporność — ale pojedynczy przepływ TCP nadal jedzie jednym kablem.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-08-vs-stp.svg'] = svg(W, H, 'Po lewej dwa łącza 1G między SW1 i SW2 bez agregacji: STP blokuje jedno z nich, więc aktywne jest tylko jedno. Po prawej Port-channel1 z LACP: oba łącza aktywne jako jedno łącze logiczne, które STP traktuje jak jeden port. Pojedynczy przepływ TCP nadal korzysta z jednego kabla.', b);
}

// 2. Hashowanie przeplywow
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Rozkład ruchu: hash z nagłówków wybiera jeden kabel dla każdego przepływu', { size: 17, bold: true, anchor: 'middle' });
  const flows = [['10.0.0.11 → 10.0.1.50 :5432', 0, C.host], ['10.0.0.12 → 10.0.1.50 :5432', 1, C.ok], ['10.0.0.13 → 10.0.1.60 :443', 2, C.vio], ['10.0.0.14 → 10.0.1.50 :5432', 3, C.net], ['10.0.0.15 → 10.0.1.70 :9092', 0, C.no]];
  flows.forEach(([n, l, col], i) => { const y = 76 + i * 56; b += box(30, y - 18, 250, 36, C.card, col, 6) + text(40, y + 5, n, { mono: true, size: 11, fill: col, bold: true }) + line(282, y, 370, 120 + l * 64, { color: col, w: 2, arrow: true }); });
  b += box(370, 70, 140, 300, C.vioFill, C.vio, 10) + text(440, 96, 'funkcja hash', { anchor: 'middle', bold: true, fill: C.vio }) + text(440, 114, 'src/dst IP, port', { anchor: 'middle', size: 11 }) + text(440, 130, 'mod liczba łączy', { anchor: 'middle', size: 11 });
  for (let l = 0; l < 4; l++) {
    const y = 120 + l * 64;
    b += line(512, y, 690, y, { color: C.ink, w: 5 }) + text(600, y - 8, `łącze ${l} (1G)`, { anchor: 'middle', size: 11 });
  }
  b += box(690, 90, 170, 280, C.netFill, C.net, 10) + text(775, 230, 'SW2', { anchor: 'middle', bold: true, fill: C.net, size: 16 });
  b += box(30, 384, 830, 26, C.noFill, C.no, 4) + text(445, 402, 'Łącze 0 niesie dwa przepływy, inne po jednym — rozkład zależy od różnorodności adresów i portów, nie od obciążenia.', { anchor: 'middle', size: 12, bold: true, fill: C.no });
  files['ns02-08-hash.svg'] = svg(W, H, 'Pięć przepływów od różnych klientów do serwerów trafia do funkcji hash liczonej z adresów IP i portów modulo liczba łączy; każdy przepływ zostaje przypisany do jednego z czterech łączy 1G, przy czym łącze 0 niesie dwa przepływy. Rozkład zależy od różnorodności nagłówków, a nie od obciążenia.', b);
}

// 3. Macierz trybow
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Czy kanał powstanie? Tryby po obu stronach', { size: 18, bold: true, anchor: 'middle' });
  const cell = 108, ox = 230, oy = 60;
  MODES.forEach((m, i) => {
    b += box(ox + i * cell, oy, cell - 4, 34, C.netFill, C.net, 4) + text(ox + i * cell + cell / 2 - 2, oy + 22, m, { anchor: 'middle', bold: true, size: 12, fill: C.net });
    b += box(ox - 130, oy + 40 + i * 44, 126, 40, C.netFill, C.net, 4) + text(ox - 67, oy + 65 + i * 44, m, { anchor: 'middle', bold: true, size: 12, fill: C.net });
  });
  MODES.forEach((a, i) => MODES.forEach((c, j) => {
    const r = negotiate(a, c);
    const fill = r.ok ? C.okFill : r.risk ? C.noFill : C.card; const col = r.ok ? C.ok : r.risk ? C.no : C.muted;
    b += box(ox + j * cell, oy + 40 + i * 44, cell - 4, 40, fill, col, 4) + text(ox + j * cell + cell / 2 - 2, oy + 65 + i * 44, r.short, { anchor: 'middle', size: 11, bold: r.ok || r.risk, fill: col });
  }));
  b += text(40, 370, 'LACP: active+active, active+passive. PAgP (Cisco): desirable+desirable, desirable+auto. on: tylko z on (bez protokołu).', { size: 12, bold: true });
  b += text(40, 392, '„on” z drugą stroną bez kanału = pętla lub czarna dziura — dlatego zalecany LACP active po obu stronach.', { size: 12, fill: C.no });
  files['ns02-08-modes.svg'] = svg(W, H, 'Macierz trybów kanału: active, passive, on, desirable, auto. Kanał LACP powstaje dla active z active i active z passive, PAgP dla desirable z desirable i desirable z auto, statyczny tylko dla on z on; passive z passive i auto z auto nie tworzą kanału, a on z trybem negocjowanym grozi pętlą lub utratą ruchu.', b);
}

// 4. MLAG
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'MLAG / vPC: jeden agregat do dwóch przełączników', { size: 18, bold: true, anchor: 'middle' });
  b += box(170, 70, 200, 50, C.netFill, C.net, 8) + text(270, 101, 'LEAF-A', { anchor: 'middle', bold: true, fill: C.net });
  b += box(530, 70, 200, 50, C.netFill, C.net, 8) + text(630, 101, 'LEAF-B', { anchor: 'middle', bold: true, fill: C.net });
  b += line(370, 88, 530, 88, { color: C.vio, w: 5 }) + line(370, 104, 530, 104, { color: C.vio, w: 5 });
  b += text(450, 80, 'peer-link', { anchor: 'middle', size: 11, bold: true, fill: C.vio }) + text(450, 126, '+ keepalive', { anchor: 'middle', size: 11, fill: C.vio });
  b += box(350, 250, 200, 56, C.hostFill, C.host, 8) + text(450, 274, 'serwer', { anchor: 'middle', bold: true, fill: C.host }) + text(450, 294, 'bond0 (LACP, mode 4)', { anchor: 'middle', size: 11, mono: true });
  b += line(270, 122, 410, 248, { color: C.ok, w: 4 }) + line(630, 122, 490, 248, { color: C.ok, w: 4 });
  b += `<ellipse cx="450" cy="200" rx="110" ry="24" fill="none" stroke="${C.ok}" stroke-width="2" stroke-dasharray="6 4"/>`;
  b += text(580, 205, 'serwer widzi jednego partnera LACP', { size: 11, bold: true, fill: C.ok });
  b += text(W / 2, 340, 'Awaria całego przełącznika nie odcina serwera, oba łącza aktywne, brak blokowania przez STP.', { anchor: 'middle', size: 12, bold: true });
  b += text(W / 2, 362, 'Nazwy: MLAG (Arista), vPC (Cisco Nexus), StackWise Virtual/VSS (Catalyst), MC-LAG (Juniper), EVPN multihoming (standard).', { anchor: 'middle', size: 11, fill: C.muted });
  files['ns02-08-mlag.svg'] = svg(W, H, 'Dwa przełączniki LEAF-A i LEAF-B połączone peer-linkiem i keepalive tworzą parę MLAG; serwer z bondingiem LACP ma po jednym łączu do każdego z nich i widzi je jako jednego partnera LACP. Awaria całego przełącznika nie odcina serwera. Odpowiedniki: vPC, StackWise Virtual, MC-LAG, EVPN multihoming.', b);
}

writeSvgs(process.argv[2], files);
