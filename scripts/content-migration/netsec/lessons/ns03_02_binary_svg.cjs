// Ilustracje do lekcji _ns_03/02_BinaryForNetworkers. Uzycie: node scripts/content-migration/netsec/lessons/ns03_02_binary_svg.cjs frontend/public/lesson-assets/netsec
const fs = require('fs');
const path = require('path');
const outDir = process.argv[2];

const C = {
  bg: '#fbf8f3', card: '#ffffff', ink: '#1f1a14', muted: '#6b6257', line: '#d9d0c3',
  net: '#2f6fb3', netFill: '#dce9f7', host: '#c46a12', hostFill: '#fbe6cf', ok: '#2e7d4f', okFill: '#dcefe3', no: '#b23b3b', noFill: '#f6dcdc',
};
const FONT = "font-family=\"'Segoe UI', system-ui, -apple-system, sans-serif\"";
const MONO = "font-family=\"'Cascadia Mono', Consolas, 'DejaVu Sans Mono', monospace\"";
const esc = (s) => String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
const svg = (w, h, title, body) => `<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 ${w} ${h}" width="${w}" height="${h}" role="img" aria-label="${esc(title)}">
<title>${esc(title)}</title>
<rect width="${w}" height="${h}" rx="14" fill="${C.bg}"/>
${body}
</svg>
`;
const text = (x, y, s, o = {}) => `<text x="${x}" y="${y}" ${o.mono ? MONO : FONT} font-size="${o.size || 14}" fill="${o.fill || C.ink}"${o.anchor ? ` text-anchor="${o.anchor}"` : ''}${o.bold ? ' font-weight="700"' : ''}>${esc(s)}</text>`;
const box = (x, y, w, h, fill, stroke, rx = 10) => `<rect x="${x}" y="${y}" width="${w}" height="${h}" rx="${rx}" fill="${fill}" stroke="${stroke}" stroke-width="1.5"/>`;
const bin8 = (n) => n.toString(2).padStart(8, '0');
const files = {};

// 1. Tabela wag 128..1 dla liczby 202
{
  const W = 820, H = 380, v = 202, bits = bin8(v);
  const weights = [128, 64, 32, 16, 8, 4, 2, 1];
  let b = text(W / 2, 34, 'Metoda tabeli: oktet 202 rozpisany na wagi bitów', { size: 18, bold: true, anchor: 'middle' });
  const x0 = 70, cw = 85;
  b += text(x0 - 12, 92, 'waga', { anchor: 'end', size: 13, fill: C.muted });
  b += text(x0 - 12, 142, 'bit', { anchor: 'end', size: 13, fill: C.muted });
  b += text(x0 - 12, 192, 'wkład', { anchor: 'end', size: 13, fill: C.muted });
  weights.forEach((w, i) => {
    const on = bits[i] === '1', x = x0 + i * cw;
    b += text(x + cw / 2 - 4, 66, `2^${7 - i}`, { anchor: 'middle', size: 12, fill: C.muted, mono: true });
    b += box(x, 72, cw - 8, 32, C.card, C.line, 6);
    b += text(x + cw / 2 - 4, 94, w, { anchor: 'middle', bold: true, mono: true });
    b += box(x, 118, cw - 8, 40, on ? C.netFill : C.card, on ? C.net : C.line, 6);
    b += text(x + cw / 2 - 4, 146, bits[i], { anchor: 'middle', bold: true, size: 22, mono: true, fill: on ? C.net : C.muted });
    b += text(x + cw / 2 - 4, 192, on ? `+${w}` : '0', { anchor: 'middle', mono: true, fill: on ? C.net : C.muted, bold: on });
  });
  b += text(W / 2, 236, '128 + 64 + 8 + 2 = 202   →   11001010', { anchor: 'middle', size: 18, bold: true, mono: true });
  b += box(40, 258, W - 80, 100, C.hostFill, C.host);
  b += text(60, 284, 'Dziesiętny → dwójkowy (odejmowanie od lewej):', { bold: true, fill: C.host });
  b += text(60, 310, '202 ≥ 128 → 1, zostaje 74 | 74 ≥ 64 → 1, zostaje 10 | 10 < 32 → 0 | 10 < 16 → 0', { mono: true, size: 13 });
  b += text(60, 334, '10 ≥ 8 → 1, zostaje 2 | 2 < 4 → 0 | 2 ≥ 2 → 1, zostaje 0 | 0 < 1 → 0  ⇒  11001010', { mono: true, size: 13 });
  files['ns03-02-place-values.svg'] = svg(W, H, 'Tabela wag bitów 128-64-32-16-8-4-2-1 dla liczby 202', b);
}

// 2. Adres IPv4 jako 32 bity
{
  const W = 820, H = 330, ip = [192, 168, 1, 77];
  let b = text(W / 2, 34, 'Adres IPv4 to 32 bity — zapis z kropkami to tylko wygoda dla ludzi', { size: 18, bold: true, anchor: 'middle' });
  const x0 = 40, ow = 185;
  ip.forEach((o, i) => {
    const x = x0 + i * ow;
    b += box(x, 60, ow - 10, 44, C.card, C.line, 8);
    b += text(x + (ow - 10) / 2, 89, o, { anchor: 'middle', bold: true, size: 20, mono: true });
    b += text(x + (ow - 10) / 2, 124, `oktet ${i + 1}`, { anchor: 'middle', size: 12, fill: C.muted });
    b += box(x, 134, ow - 10, 44, i % 2 ? C.hostFill : C.netFill, i % 2 ? C.host : C.net, 8);
    b += text(x + (ow - 10) / 2, 163, bin8(o), { anchor: 'middle', bold: true, size: 18, mono: true, fill: i % 2 ? C.host : C.net });
    b += text(x + 6, 196, `bit ${31 - i * 8}`, { size: 11, fill: C.muted, mono: true });
    b += text(x + ow - 16, 196, `bit ${24 - i * 8}`, { size: 11, fill: C.muted, mono: true, anchor: 'end' });
  });
  b += text(W / 2, 234, '11000000101010000000000101001101', { anchor: 'middle', size: 18, mono: true, bold: true });
  b += text(W / 2, 262, 'jako jedna liczba: 192·2^24 + 168·2^16 + 1·2^8 + 77 = 3 232 235 853', { anchor: 'middle', size: 14, mono: true });
  b += text(W / 2, 296, '4 oktety × 8 bitów = 32 bity → 2^32 ≈ 4,29 mld możliwych adresów IPv4', { anchor: 'middle', size: 14, fill: C.muted });
  files['ns03-02-ipv4-32-bits.svg'] = svg(W, H, 'Adres 192.168.1.77 jako cztery oktety i 32 bity', b);
}

// 3. AND bit po bicie
{
  const W = 820, H = 360;
  const a = 37, m = 248, r = a & m;
  let b = text(W / 2, 34, 'AND bit po bicie: 1 tylko tam, gdzie oba bity są 1', { size: 18, bold: true, anchor: 'middle' });
  const rows = [['adres', a, C.ink], ['maska', m, C.net], ['AND', r, C.ok]];
  const x0 = 150, cw = 50;
  rows.forEach(([label, val, col], ri) => {
    const y = 70 + ri * 62, bits = bin8(val);
    b += text(x0 - 16, y + 30, label, { anchor: 'end', bold: true, fill: col });
    for (let i = 0; i < 8; i++) {
      const on = bits[i] === '1';
      const fill = ri === 2 ? (on ? C.okFill : C.card) : (ri === 1 ? (on ? C.netFill : C.card) : C.card);
      b += box(x0 + i * cw, y, cw - 6, 44, fill, ri === 2 && on ? C.ok : C.line, 6);
      b += text(x0 + i * cw + (cw - 6) / 2, y + 30, bits[i], { anchor: 'middle', mono: true, size: 20, bold: on, fill: on ? col : C.muted });
    }
    b += text(x0 + 8 * cw + 14, y + 30, `= ${val}`, { mono: true, size: 18, bold: true, fill: col });
    if (ri === 1) b += `<line x1="${x0 - 4}" y1="${y + 54}" x2="${x0 + 8 * cw}" y2="${y + 54}" stroke="${C.ink}" stroke-width="2"/>`;
  });
  // tabela prawdy
  const tx = 620, ty = 70;
  b += box(tx, ty, 170, 170, C.card, C.line);
  b += text(tx + 85, ty + 26, 'Tabela prawdy AND', { anchor: 'middle', bold: true, size: 13 });
  [['0', '0', '0'], ['0', '1', '0'], ['1', '0', '0'], ['1', '1', '1']].forEach(([p, q, o], i) => {
    b += text(tx + 85, ty + 58 + i * 28, `${p} AND ${q} = ${o}`, { anchor: 'middle', mono: true, size: 15, bold: o === '1', fill: o === '1' ? C.ok : C.ink });
  });
  b += box(40, 270, W - 80, 70, C.netFill, C.net);
  b += text(W / 2, 297, 'Jedynki maski „przepuszczają” bity adresu, zera je zerują.', { anchor: 'middle', bold: true, fill: C.net });
  b += text(W / 2, 322, 'Dokładnie tak host wylicza adres sieci: 10.4.37.200 AND 255.255.248.0 = 10.4.32.0', { anchor: 'middle', size: 13, mono: true });
  files['ns03-02-and-operation.svg'] = svg(W, H, 'Operacja AND: 37 AND 248 = 32, bit po bicie, z tabelą prawdy', b);
}

// 4. Szesnastkowy: nibble, MAC, IPv6
{
  const W = 820, H = 380;
  let b = text(W / 2, 34, 'Szesnastkowy: jedna cyfra hex = 4 bity (nibble)', { size: 18, bold: true, anchor: 'middle' });
  // bajt 0xC0
  b += box(40, 60, 360, 140, C.card, C.line);
  b += text(220, 86, 'Bajt 192 = 0xC0', { anchor: 'middle', bold: true });
  b += box(70, 100, 140, 40, C.netFill, C.net, 6); b += text(140, 127, '1100', { anchor: 'middle', mono: true, size: 20, bold: true, fill: C.net });
  b += box(230, 100, 140, 40, C.hostFill, C.host, 6); b += text(300, 127, '0000', { anchor: 'middle', mono: true, size: 20, bold: true, fill: C.host });
  b += text(140, 168, '8+4 = 12 = C', { anchor: 'middle', mono: true });
  b += text(300, 168, '0', { anchor: 'middle', mono: true });
  b += text(220, 190, 'C·16 + 0 = 192', { anchor: 'middle', mono: true, size: 13, fill: C.muted });
  // tabela 0-F
  b += box(420, 60, 360, 140, C.card, C.line);
  b += text(600, 86, 'Cyfry 10–15', { anchor: 'middle', bold: true });
  [['A', 10, '1010'], ['B', 11, '1011'], ['C', 12, '1100'], ['D', 13, '1101'], ['E', 14, '1110'], ['F', 15, '1111']].forEach(([h, d, bn], i) => {
    const x = 440 + (i % 3) * 112, y = 116 + Math.floor(i / 3) * 40;
    b += text(x, y, `${h} = ${d} = ${bn}`, { mono: true, size: 14 });
  });
  // MAC i IPv6
  b += box(40, 220, W - 80, 64, C.card, C.line);
  b += text(60, 246, 'MAC (48 bitów = 6 bajtów = 12 cyfr hex):', { bold: true, size: 13 });
  b += text(60, 270, '02:42:AC:11:00:02   — MAC kontenera Dockera; AC.11.00.02 hex = 172.17.0.2', { mono: true, size: 13 });
  b += box(40, 296, W - 80, 64, C.card, C.line);
  b += text(60, 322, 'IPv6 (128 bitów = 8 grup po 16 bitów = 4 cyfry hex w grupie):', { bold: true, size: 13 });
  b += text(60, 346, '2001:0db8:0000:0000:0000:ff00:0042:8329  →  2001:db8::ff00:42:8329', { mono: true, size: 13 });
  files['ns03-02-hex-nibbles.svg'] = svg(W, H, 'System szesnastkowy: bajt 0xC0 jako dwa nibble, cyfry A-F, MAC i IPv6 w hex', b);
}

for (const [name, content] of Object.entries(files)) {
  fs.writeFileSync(path.join(outDir, name), content);
  console.log('zapisano', name);
}
