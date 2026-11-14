// Ilustracje do lekcji _ns_03/05_SubnettingDrill.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_05_drill_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { parse, sub6, count6 } = require('./ns03_05_drill_model.cjs');
const files = {};

// 1. Sciaga
{
  const W = 900, H = 470;
  let b = text(W / 2, 30, 'Ściąga do treningu: prefiks → oktet maski → magiczna liczba → hosty', { size: 17, bold: true, anchor: 'middle' });
  const cols = [60, 150, 290, 420, 560, 700];
  ['prefiks', 'maska', 'oktet maski', 'magiczna liczba', 'adresów', 'hostów'].forEach((h, i) => { b += text(cols[i], 62, h, { bold: true, size: 13, fill: C.muted }); });
  const rows = [16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32];
  rows.forEach((p, i) => {
    const y = 84 + i * 21.5, x = parse(`10.0.0.0/${p}`), oct = [8, 16, 24, 32].findIndex((t) => p <= t);
    const octVal = x.mask.split('.')[oct === -1 ? 3 : oct === 0 ? 0 : (p % 8 === 0 ? oct : oct)];
    const interesting = p % 8 === 0 ? (p === 32 ? 3 : p / 8 - 1) : Math.floor(p / 8);
    const mOct = Number(x.mask.split('.')[interesting]);
    if (i % 2 === 0) b += box(50, y - 15, 800, 21, C.card, C.bg, 0);
    b += text(cols[0], y, `/${p}`, { mono: true, bold: true, size: 13, fill: p % 8 === 0 ? C.net : C.ink });
    b += text(cols[1], y, x.mask, { mono: true, size: 12 });
    b += text(cols[2], y, `${mOct} (oktet ${interesting + 1})`, { mono: true, size: 12 });
    b += text(cols[3], y, String(256 - mOct === 256 ? 1 : 256 - mOct), { mono: true, size: 12, bold: true, fill: C.host });
    b += text(cols[4], y, x.size.toLocaleString('pl-PL'), { mono: true, size: 12 });
    b += text(cols[5], y, p === 31 ? '2 (P2P)' : p === 32 ? '1 (host)' : x.hosts.toLocaleString('pl-PL'), { mono: true, size: 12 });
  });
  b += text(60, 460, 'Klucz: oktety maski to zawsze 0, 128, 192, 224, 240, 248, 252, 254, 255; magiczna liczba = 256 − oktet maski.', { size: 12, bold: true, fill: C.ok });
  files['ns03-05-cheatsheet.svg'] = svg(W, H, 'Tabela prefiksów od /16 do /32: maska, oktet maski w „interesującym” oktecie, magiczna liczba (256 minus oktet maski), liczba adresów i hostów; np. /19: 255.255.224.0, magiczna liczba 32 w trzecim oktecie, 8192 adresy, 8190 hostów; /27: 224 w czwartym oktecie, magiczna 32, 30 hostów.', b);
}

// 2. Przynaleznosc w 3 krokach
{
  const W = 900, H = 380;
  let b = text(W / 2, 30, 'Czy 172.30.17.200 i 172.30.18.1 należą do 172.30.16.0/23? Trzy kroki w głowie', { size: 17, bold: true, anchor: 'middle' });
  const steps = [
    ['1', 'Interesujący oktet', '/23 = 8 + 8 + 7 → trzeci oktet (maska 254)', C.net, C.netFill],
    ['2', 'Magiczna liczba', '256 − 254 = 2 → bloki w 3. oktecie: …14, 16, 18, 20…', C.host, C.hostFill],
    ['3', 'Zakres bloku', '16.0 – 17.255 (sieć 172.30.16.0, broadcast 172.30.17.255)', C.vio, C.vioFill],
  ];
  steps.forEach(([n, h, t, col, fill], i) => {
    const y = 56 + i * 66;
    b += box(40, y, 820, 54, fill, col, 8) + text(70, y + 33, n, { size: 22, bold: true, fill: col, anchor: 'middle' }) + text(100, y + 23, h, { bold: true, fill: col }) + text(100, y + 43, t, { mono: true, size: 13 });
  });
  b += box(40, 266, 400, 92, C.okFill, C.ok, 8) + text(240, 294, '172.30.17.200', { anchor: 'middle', mono: true, bold: true, size: 16 }) + text(240, 318, 'trzeci oktet 17 ∈ {16, 17}', { anchor: 'middle', size: 13 }) + text(240, 342, '✓ NALEŻY', { anchor: 'middle', bold: true, fill: C.ok, size: 15 });
  b += box(460, 266, 400, 92, C.noFill, C.no, 8) + text(660, 294, '172.30.18.1', { anchor: 'middle', mono: true, bold: true, size: 16 }) + text(660, 318, 'trzeci oktet 18 → następny blok (18–19)', { anchor: 'middle', size: 13 }) + text(660, 342, '✕ NIE NALEŻY', { anchor: 'middle', bold: true, fill: C.no, size: 15 });
  files['ns03-05-membership.svg'] = svg(W, H, 'Sprawdzenie przynależności w trzech krokach dla 172.30.16.0/23: interesujący oktet to trzeci (maska 254), magiczna liczba 2, blok obejmuje 172.30.16.0–172.30.17.255. Adres 172.30.17.200 należy, 172.30.18.1 leży już w kolejnym bloku.', b);
}

// 3. IPv6 hierarchia
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'IPv6: liczy się w grupach po 4 bity (cyfrach hex), a LAN to zawsze /64', { size: 17, bold: true, anchor: 'middle' });
  const groups = ['2001', '0db8', 'abcd', '0012', '0000', '0000', '0000', '0001'];
  const gx = (i) => 60 + i * 100;
  groups.forEach((g, i) => {
    const col = i < 2 ? C.vio : i < 3 ? C.net : i < 4 ? C.ok : C.host;
    const fill = i < 2 ? C.vioFill : i < 3 ? C.netFill : i < 4 ? C.okFill : C.hostFill;
    b += box(gx(i), 56, 92, 40, fill, col, 4) + text(gx(i) + 46, 82, g, { anchor: 'middle', mono: true, bold: true, size: 16 });
    b += text(gx(i) + 46, 112, `${i * 16}–${i * 16 + 15}`, { anchor: 'middle', size: 10, fill: C.muted });
  });
  const brackets = [[0, 2, '/32 — dostawca (RIR → ISP)', C.vio], [2, 3, '/48 — lokalizacja', C.net], [3, 4, '/64 — podsieć', C.ok], [4, 8, 'identyfikator interfejsu (64 bity)', C.host]];
  brackets.forEach(([s, e, t, col], i) => { b += line(gx(s) + 4, 128, gx(e) - 12, 128, { color: col, w: 3 }) + text((gx(s) + gx(e) - 8) / 2, 146 + (i % 2) * 14, t, { anchor: 'middle', size: 11, bold: true, fill: col }); });
  b += box(40, 180, 820, 200, C.card, C.line, 8);
  const rows = [
    `/48 → /64: ${count6(48, 64).toLocaleString('pl-PL')} podsieci (4. grupa: 0000–ffff)`,
    `/48 → /56: ${count6(48, 56)} podsieci (np. dla klientów lub budynków), każda /56 → ${count6(56, 64)} × /64`,
    `/56 → /64: ${count6(56, 64)} podsieci — tyle typowo dostaje klient domowy`,
    `podsieć nr 18 (0x12) /64 w 2001:db8:abcd::/48 → ${sub6('2001:db8:abcd::', 48, 64, 18)}`,
    `podsieć nr 3 /56 w 2001:db8:abcd::/48 → ${sub6('2001:db8:abcd::', 48, 56, 3)}`,
    'Granica na pełnej cyfrze hex (wielokrotność 4 bitów) = liczenie bez binarnego: /52, /56, /60, /64.',
  ];
  rows.forEach((t, i) => { b += text(60, 208 + i * 28, t, { size: 13, mono: i >= 3 && i < 5, bold: i === 5, fill: i === 5 ? C.ok : C.ink }); });
  files['ns03-05-ipv6.svg'] = svg(W, H, 'Adres 2001:0db8:abcd:0012:0000:0000:0000:0001 podzielony na grupy: pierwsze dwie grupy to prefiks /32 dostawcy, trzecia dopełnia /48 lokalizacji, czwarta wybiera podsieć /64, ostatnie cztery grupy to identyfikator interfejsu. /48 mieści 65 536 podsieci /64 lub 256 podsieci /56; /56 mieści 256 podsieci /64.', b);
}

// 4. Plan treningu
{
  const W = 900, H = 360;
  let b = text(W / 2, 30, 'Jak trenować: tempo, poprawność i dziennik błędów', { size: 18, bold: true, anchor: 'middle' });
  const lv = [
    ['Runda 1', 'bez limitu czasu', 'przynależność, hosty, maski', '100% poprawnie', C.net, C.netFill],
    ['Runda 2', '30 s / zadanie', '+ podział i nakładanie', '≥ 90%', C.host, C.hostFill],
    ['Runda 3', '15 s / zadanie', '+ agregacja i VLSM', '≥ 90%', C.vio, C.vioFill],
    ['Egzamin', '≤ 1 min na trudne', 'mieszanka + IPv6', 'odruch', C.ok, C.okFill],
  ];
  lv.forEach(([n, t, z, c, col, fill], i) => {
    const x = 40 + i * 210;
    b += box(x, 56, 196, 150, fill, col, 10) + text(x + 98, 82, n, { anchor: 'middle', bold: true, fill: col, size: 16 }) + text(x + 98, 112, t, { anchor: 'middle', size: 13 }) + text(x + 98, 140, z, { anchor: 'middle', size: 12 }) + text(x + 98, 176, `cel: ${c}`, { anchor: 'middle', size: 13, bold: true });
    if (i < 3) b += line(x + 198, 131, x + 208, 131, { arrow: true });
  });
  b += box(40, 226, 820, 116, C.card, C.line, 8);
  b += text(60, 252, 'Dziennik błędów — po każdej rundzie zapisz, CO pomyliłeś, nie tylko ile:', { bold: true, size: 13 });
  b += text(60, 276, '• zły oktet (liczysz w 4., a prefiks kończy się w 3.)   • broadcast = następna sieć zamiast −1', { size: 12 });
  b += text(60, 298, '• hosty bez −2 (albo −2 w AWS zamiast −5)   • blok niewyrównany (/20 od .8.0)   • agregat obejmuje cudze sieci', { size: 12 });
  b += text(60, 322, 'Kalkulator (ipcalc, Python ipaddress) — dopiero PO odpowiedzi, jako sędzia, nigdy zamiast liczenia.', { size: 12, bold: true, fill: C.no });
  files['ns03-05-routine.svg'] = svg(W, H, 'Plan treningu w czterech rundach: bez limitu czasu ze 100% poprawności, 30 sekund na zadanie, 15 sekund z agregacją i VLSM, egzaminacyjna mieszanka z IPv6. Dziennik typowych błędów: zły oktet, broadcast mylony z następną siecią, brak odejmowania dwóch hostów, niewyrównany blok, zbyt szeroki agregat. Kalkulator dopiero po odpowiedzi.', b);
}

writeSvgs(process.argv[2], files);
