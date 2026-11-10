// Ilustracje do lekcji _ns_03/03_SubnetMaskFromZero. Uzycie: node scripts/content-migration/netsec/lessons/ns03_03_subnet_mask_svg.cjs frontend/public/lesson-assets/netsec
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
const arrow = (x1, y1, x2, y2, color = C.muted) => `<line x1="${x1}" y1="${y1}" x2="${x2}" y2="${y2}" stroke="${color}" stroke-width="2" marker-end="url(#arr)"/>`;
const defs = `<defs><marker id="arr" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10 z" fill="${C.muted}"/></marker></defs>`;

// 1. Decyzja: moja siec czy brama
{
  const W = 820, H = 400;
  let b = defs;
  b += text(W / 2, 34, 'Maska rozstrzyga: dostarczyć bezpośrednio czy oddać bramie?', { size: 18, bold: true, anchor: 'middle' });
  b += box(30, 60, 250, 92, C.card, C.line);
  b += text(155, 86, 'Host A wysyła pakiet', { anchor: 'middle', bold: true });
  b += text(155, 110, 'mój adres: 192.168.1.10/24', { anchor: 'middle', mono: true, size: 13 });
  b += text(155, 132, 'cel: 192.168.1.77 lub 8.8.8.8', { anchor: 'middle', mono: true, size: 13 });
  b += arrow(280, 106, 318, 106);
  b += box(320, 60, 250, 92, C.netFill, C.net);
  b += text(445, 86, 'Policz adresy sieci', { anchor: 'middle', bold: true, fill: C.net });
  b += text(445, 110, 'moja = 192.168.1.10 AND maska', { anchor: 'middle', mono: true, size: 12 });
  b += text(445, 132, 'celu = adres celu AND maska', { anchor: 'middle', mono: true, size: 12 });
  b += arrow(570, 106, 618, 106);
  b += `<polygon points="690,62 760,106 690,150 620,106" fill="${C.card}" stroke="${C.ink}" stroke-width="1.5"/>`;
  b += text(690, 102, 'sieci', { anchor: 'middle', bold: true });
  b += text(690, 120, 'równe?', { anchor: 'middle', bold: true });
  // tak
  b += arrow(690, 150, 690, 186);
  b += text(702, 176, 'TAK', { fill: C.ok, bold: true, size: 13 });
  b += box(520, 188, 280, 120, C.okFill, C.ok);
  b += text(660, 214, 'Ta sama sieć (192.168.1.77)', { anchor: 'middle', bold: true, fill: C.ok });
  b += text(660, 240, '1. ARP: „kto ma 192.168.1.77?”', { anchor: 'middle', size: 13 });
  b += text(660, 262, '2. ramka na MAC odbiorcy', { anchor: 'middle', size: 13 });
  b += text(660, 284, 'switch dostarcza ją w LAN', { anchor: 'middle', size: 13, fill: C.muted });
  // nie
  b += `<path d="M620,106 L600,106 L600,170 L180,170 L180,186" fill="none" stroke="${C.muted}" stroke-width="2" marker-end="url(#arr)"/>`;
  b += text(392, 162, 'NIE', { fill: C.no, bold: true, size: 13, anchor: 'middle' });
  b += box(30, 188, 300, 120, C.noFill, C.no);
  b += text(180, 214, 'Inna sieć (8.8.8.8)', { anchor: 'middle', bold: true, fill: C.no });
  b += text(180, 240, '1. ARP o MAC bramy 192.168.1.1', { anchor: 'middle', size: 13 });
  b += text(180, 262, '2. ramka na MAC bramy,', { anchor: 'middle', size: 13 });
  b += text(180, 284, 'w pakiecie IP nadal cel 8.8.8.8', { anchor: 'middle', size: 13, fill: C.muted });
  b += text(W / 2, 345, 'Maska nie jest wysyłana w pakiecie — każdy host stosuje ją lokalnie.', { anchor: 'middle', size: 14, fill: C.muted });
  b += text(W / 2, 370, 'Dlatego zła maska na jednym hoście psuje komunikację tylko „w jedną stronę”.', { anchor: 'middle', size: 14, fill: C.muted });
  fs.writeFileSync(path.join(outDir, 'mask-decision.svg'), svg(W, H, 'Schemat decyzji hosta: ta sama sieć czy brama domyślna', b));
}

// 2. Linijka maski: IP, maska, AND bit po bicie
{
  const ip = [192, 168, 1, 77], p = 26;
  const bits = ip.flatMap((o) => o.toString(2).padStart(8, '0').split('').map(Number));
  const mask = Array.from({ length: 32 }, (_, i) => (i < p ? 1 : 0));
  const res = bits.map((x, i) => x & mask[i]);
  const cell = 20, gap = 10, x0 = 150;
  const cx = (i) => x0 + i * cell + Math.floor(i / 8) * gap;
  const W = cx(31) + cell + 40, H = 330;
  let b = defs;
  b += text(W / 2, 32, 'Linijka maski: 192.168.1.77/26 bit po bicie', { size: 18, bold: true, anchor: 'middle' });
  const row = (y, label, sub, arr, colorize) => {
    let s = text(20, y + 15, label, { bold: true }) + text(20, y + 33, sub, { mono: true, size: 12, fill: C.muted });
    arr.forEach((v, i) => {
      const isNet = i < p;
      const fill = colorize ? (isNet ? C.netFill : C.hostFill) : C.card;
      const stroke = isNet ? C.net : C.host;
      s += `<rect x="${cx(i)}" y="${y}" width="${cell - 2}" height="26" rx="3" fill="${fill}" stroke="${stroke}" stroke-width="1"/>`;
      s += text(cx(i) + (cell - 2) / 2, y + 18, v, { mono: true, size: 13, anchor: 'middle', bold: v === 1 });
    });
    return s;
  };
  // granica
  const bx = cx(p) - 2;
  b += `<line x1="${bx}" y1="52" x2="${bx}" y2="232" stroke="${C.ink}" stroke-width="2.5" stroke-dasharray="6 4"/>`;
  b += text(bx, 66, '/26 — granica', { size: 12, anchor: 'middle', bold: true });
  b += row(80, 'adres IP', '192.168.1.77', bits, false);
  b += row(130, 'maska', '255.255.255.192', mask, true);
  b += row(190, 'IP AND maska', '= 192.168.1.64', res, true);
  b += text(20, 62, 'AND: 1 AND 1 = 1, wszystko inne = 0 → bity hosta się zerują', { size: 12, fill: C.muted });
  // podpisy czesci
  const netMid = (x0 + bx) / 2, hostMid = (bx + cx(31) + cell) / 2;
  b += `<rect x="${x0}" y="242" width="${bx - x0 - 4}" height="8" rx="4" fill="${C.net}"/>`;
  b += `<rect x="${bx + 4}" y="242" width="${cx(31) + cell - bx - 6}" height="8" rx="4" fill="${C.host}"/>`;
  b += text(netMid, 272, 'część sieci: 26 bitów (jedynki maski)', { anchor: 'middle', fill: C.net, bold: true, size: 13 });
  b += text(hostMid, 272, 'host: 6 bitów', { anchor: 'middle', fill: C.host, bold: true, size: 13 });
  b += text(W / 2, 306, 'sieć 192.168.1.64 · hosty .65–.126 · broadcast 192.168.1.127 · 2⁶ − 2 = 62 hosty', { anchor: 'middle', size: 14 });
  fs.writeFileSync(path.join(outDir, 'mask-ruler.svg'), svg(W, H, 'Adres 192.168.1.77, maska /26 i wynik operacji AND bit po bicie', b));
}

// 3. Magiczna liczba: os trzeciego oktetu dla 10.4.37.200/21
{
  const W = 820, H = 300, x0 = 50, x1 = 770, max = 64;
  const X = (v) => x0 + (x1 - x0) * v / max;
  let b = defs;
  b += text(W / 2, 32, 'Magiczna liczba: 10.4.37.200/21 → blok 256 − 248 = 8 w trzecim oktecie', { size: 17, bold: true, anchor: 'middle' });
  for (let v = 0; v < max; v += 8) {
    const hit = v === 32;
    b += `<rect x="${X(v) + 1}" y="90" width="${X(8) - X(0) - 2}" height="46" rx="6" fill="${hit ? C.netFill : C.card}" stroke="${hit ? C.net : C.line}" stroke-width="${hit ? 2.5 : 1.2}"/>`;
    b += text((X(v) + X(v + 8)) / 2, 118, `${v}–${v + 7}`, { mono: true, size: 13, anchor: 'middle', bold: hit, fill: hit ? C.net : C.muted });
    b += `<line x1="${X(v)}" y1="136" x2="${X(v)}" y2="150" stroke="${C.muted}" stroke-width="1.5"/>`;
    b += text(X(v), 168, v, { mono: true, size: 12, anchor: 'middle', fill: C.muted });
  }
  b += text(x0, 78, 'trzeci oktet adresu — podsieci zaczynają się co 8:', { size: 13, fill: C.muted });
  // marker 37
  const m = X(37) + (X(1) - X(0)) / 2;
  b += `<polygon points="${m - 8},60 ${m + 8},60 ${m},76" fill="${C.host}"/>`;
  b += text(m, 54, '37', { mono: true, bold: true, anchor: 'middle', fill: C.host });
  b += box(50, 192, 340, 84, C.netFill, C.net);
  b += text(220, 218, 'sieć: największa wielokrotność 8 ≤ 37', { anchor: 'middle', size: 13 });
  b += text(220, 248, '10.4.32.0/21', { anchor: 'middle', mono: true, bold: true, size: 17, fill: C.net });
  b += box(430, 192, 340, 84, C.hostFill, C.host);
  b += text(600, 218, 'broadcast: następny blok (40) − 1, reszta 255', { anchor: 'middle', size: 13 });
  b += text(600, 248, '10.4.39.255', { anchor: 'middle', mono: true, bold: true, size: 17, fill: C.host });
  fs.writeFileSync(path.join(outDir, 'mask-magic-number.svg'), svg(W, H, 'Metoda magicznej liczby na osi trzeciego oktetu dla 10.4.37.200/21', b));
}

// 4. Podzial /24 na podsieci roznych rozmiarow
{
  const W = 820, H = 300, x0 = 40, x1 = 780;
  const X = (v) => x0 + (x1 - x0) * v / 256;
  let b = defs;
  b += text(W / 2, 32, 'Jedna sieć 192.168.10.0/24 — im dłuższy prefiks, tym mniejsze bloki', { size: 17, bold: true, anchor: 'middle' });
  const rows = [[24, 256], [25, 128], [26, 64], [27, 32], [28, 16]];
  rows.forEach(([p, size], r) => {
    const y = 56 + r * 44;
    b += text(x0 - 8, y + 22, `/${p}`, { mono: true, bold: true, anchor: 'end', size: 14 });
    for (let v = 0; v < 256; v += size) {
      const shade = (v / size) % 2 === 0 ? C.netFill : '#c7dcf2';
      b += `<rect x="${X(v) + 1}" y="${y}" width="${X(size) - X(0) - 2}" height="32" rx="4" fill="${shade}" stroke="${C.net}" stroke-width="1"/>`;
      if (size >= 32) b += text((X(v) + X(v + size)) / 2, y + 21, `.${v}`, { mono: true, size: 12, anchor: 'middle' });
    }
    b += text(x1 + 8, y + 22, '', {});
  });
  b += text(W / 2, 286, '/24: 1×254 hostów · /25: 2×126 · /26: 4×62 · /27: 8×30 · /28: 16×14', { anchor: 'middle', size: 14, fill: C.muted });
  fs.writeFileSync(path.join(outDir, 'mask-subnet-split.svg'), svg(W + 20, H, 'Podział sieci /24 na podsieci /25, /26, /27 i /28', b.replace('x="40"', 'x="40"')));
}
console.log('svg ok');
