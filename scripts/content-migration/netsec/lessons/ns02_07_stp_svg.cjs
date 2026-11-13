// Ilustracje do lekcji _ns_02/07_StpRstpDeepDive.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_07_stp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { stp } = require('./ns02_07_stp_sim.cjs');
const files = {};

// Topologia referencyjna lekcji (te same dane co w lekcji)
const SW = [{ n: 'CORE1', prio: 24576, mac: '00:aa:00:00:00:01' }, { n: 'CORE2', prio: 28672, mac: '00:aa:00:00:00:02' }, { n: 'ACC1', prio: 32768, mac: '00:0b:00:00:00:01' }, { n: 'ACC2', prio: 32768, mac: '00:0b:00:00:00:02' }];
const LINKS = [['CORE1', 1, 'CORE2', 1, '10G'], ['CORE1', 2, 'ACC1', 1, '1G'], ['CORE1', 3, 'ACC2', 1, '1G'], ['CORE2', 2, 'ACC1', 2, '1G'], ['CORE2', 3, 'ACC2', 2, '1G']];

// 1. Role portow wyliczone symulatorem
{
  const W = 900, H = 470;
  const r = stp(SW, LINKS);
  const pos = { CORE1: [250, 110], CORE2: [650, 110], ACC1: [250, 330], ACC2: [650, 330] };
  let b = text(W / 2, 32, `Wynik STP: root = ${r.root}, role portów na każdym łączu`, { size: 18, bold: true, anchor: 'middle' });
  const roleCol = { root: C.ok, designated: C.net, alternate: C.no };
  const roleShort = { root: 'RP', designated: 'DP', alternate: 'ALT' };
  LINKS.forEach(([a, pa, bb, pb, sp]) => {
    const [x1, y1] = pos[a], [x2, y2] = pos[bb];
    const blocked = r.roles[`${a}:${pa}`] === 'alternate' || r.roles[`${bb}:${pb}`] === 'alternate';
    b += line(x1, y1, x2, y2, { color: blocked ? C.line : C.ink, w: blocked ? 3 : 4, dash: blocked });
    const diag = x1 !== x2 && y1 !== y2;
    const mx = (x1 + x2) / 2 + (diag ? (a === 'CORE1' ? -70 : 70) : 0), my = (y1 + y2) / 2 + (diag ? (a === 'CORE1' ? -28 : -28) : 0);
    if (!diag) b += box(mx - 34, my - 11, 68, 22, C.bg, C.bg, 4) + text(mx, my + 5, `${sp} (${r.costs[sp]})`, { anchor: 'middle', size: 11, fill: C.muted });
    [[a, pa, x1, y1, x2, y2], [bb, pb, x2, y2, x1, y1]].forEach(([s, p, sx, sy, ox, oy]) => {
      const role = r.roles[`${s}:${p}`];
      const t = 0.36; const px = sx + (ox - sx) * t, py = sy + (oy - sy) * t;
      b += box(px - 30, py - 12, 60, 24, C.card, roleCol[role], 6) + text(px, py + 5, `p${p} ${roleShort[role]}`, { anchor: 'middle', size: 11, bold: true, fill: roleCol[role] });
    });
  });
  SW.forEach((s) => {
    const [x, y] = pos[s.n]; const isRoot = s.n === r.root;
    b += box(x - 80, y - 30, 160, 60, isRoot ? C.okFill : C.netFill, isRoot ? C.ok : C.net, 8);
    b += text(x, y - 8, s.n + (isRoot ? ' (root)' : ''), { anchor: 'middle', bold: true, size: 14, fill: isRoot ? C.ok : C.net });
    b += text(x, y + 10, `prio ${s.prio} · ${s.mac.slice(-5)}`, { anchor: 'middle', size: 10, mono: true });
    b += text(x, y + 24, `koszt do roota: ${r.dist[s.n]}`, { anchor: 'middle', size: 10 });
  });
  b += box(40, 395, 820, 60, C.card, C.line, 8);
  b += text(W / 2, 380, 'Łącza ukośne: 1G (koszt 4). Koszty STP w trybie short: 10G = 2, 1G = 4, 100M = 19.', { anchor: 'middle', size: 11, fill: C.muted });
  b += text(60, 418, 'RP — root port (najtańsza droga do roota) · DP — designated port (jeden na segment, przekazuje w stronę od roota)', { size: 12 });
  b += text(60, 440, 'ALT — alternate: zablokowany zapasowy root port; w RSTP przejmuje ruch w ~1 s po awarii RP', { size: 12, bold: true, fill: C.no });
  files['ns02-07-roles.svg'] = svg(W, H, 'Topologia czterech przełączników: CORE1 (priorytet 24576, root), CORE2, ACC1 i ACC2, z łączami 10G między rdzeniami i 1G do przełączników dostępowych. Wyliczone role: wszystkie porty CORE1 designated, CORE2 port 1 root, ACC1 i ACC2 mają root port w stronę CORE1, a porty w stronę CORE2 są alternate (zablokowane); koszty do roota: CORE2 2, ACC1 4, ACC2 4.', b);
}

// 2. Stany portu: 802.1D vs RSTP
{
  const W = 900, H = 360;
  let b = text(W / 2, 32, 'Ile trwa, zanim port zacznie przekazywać ruch', { size: 18, bold: true, anchor: 'middle' });
  const sx = 170, scale = 13; // px na sekunde
  b += text(40, 92, '802.1D', { bold: true, size: 15 }) + text(40, 110, 'awaria pośrednia', { size: 11, fill: C.muted });
  const seg = [['blocking (max age)', 20, C.noFill, C.no], ['listening', 15, C.hostFill, C.host], ['learning', 15, C.vioFill, C.vio], ['forwarding', 0, C.okFill, C.ok]];
  let x = sx;
  seg.forEach(([n, s, fill, col]) => {
    const w = s ? s * scale : 70;
    b += box(x, 76, w, 40, fill, col, 4) + text(x + w / 2, 101, n, { anchor: 'middle', size: 12, bold: true, fill: col });
    x += w;
  });
  [0, 20, 35, 50].forEach((t) => { b += line(sx + t * scale, 120, sx + t * scale, 130, { color: C.muted }) + text(sx + t * scale, 145, `${t} s`, { anchor: 'middle', size: 11 }); });
  b += text(40, 192, 'RSTP', { bold: true, size: 15 }) + text(40, 210, '802.1w', { size: 11, fill: C.muted });
  b += box(sx, 176, 2 * scale, 40, C.vioFill, C.vio, 4) + box(sx + 2 * scale, 176, 70, 40, C.okFill, C.ok, 4) + text(sx + 2 * scale + 35, 201, 'forwarding', { anchor: 'middle', size: 12, bold: true, fill: C.ok });
  b += text(sx + 2 * scale + 90, 192, 'proposal/agreement lub alternate → root: ~1 s;', { size: 12 });
  b += text(sx + 2 * scale + 90, 210, 'port edge (PortFast) — natychmiast', { size: 12 });
  b += box(40, 246, 820, 96, C.card, C.line, 8);
  b += text(60, 270, 'Co widzi aplikacja: przez 30–50 s ramki nie przechodzą — DHCP timeout, zerwane połączenia TCP,', { size: 12 });
  b += text(60, 290, 'błędy health-checków, failover klastra. Do tego TCN skraca czas życia tablic MAC → chwilowy flooding.', { size: 12 });
  b += text(60, 316, 'Rozwiązanie: RSTP (rapid-pvst / MST) + PortFast na portach brzegowych + stabilne łącza.', { size: 12, bold: true, fill: C.ok });
  files['ns02-07-timers.svg'] = svg(W, H, 'Oś czasu: w 802.1D port przechodzi przez blocking do 20 s (max age), listening 15 s i learning 15 s, więc forwarding zaczyna się po 30–50 s; w RSTP dzięki proposal/agreement port przechodzi do forwarding w około sekundę, a port brzegowy natychmiast. Skutki dla aplikacji: timeouty DHCP, zerwane połączenia TCP, błędy health-checków.', b);
}

// 3. Bridge ID i BPDU
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Bridge ID (8 bajtów) i najważniejsze pola BPDU', { size: 18, bold: true, anchor: 'middle' });
  const parts = [['priorytet', '4 bity', 120, C.hostFill, '0…61440, co 4096'], ['system ID ext.', '12 bitów', 200, C.okFill, '= numer VLAN (PVST+)'], ['adres MAC', '48 bitów', 440, C.netFill, 'rozstrzyga remis']];
  let x = 60;
  parts.forEach(([n, bits, w, fill, d]) => { b += box(x, 60, w, 52, fill, C.ink, 4) + text(x + w / 2, 82, n, { anchor: 'middle', bold: true, size: 13 }) + text(x + w / 2, 102, bits, { anchor: 'middle', size: 11 }) + text(x + w / 2, 130, d, { anchor: 'middle', size: 11, fill: C.muted }); x += w; });
  b += text(60, 160, 'Przykład: 32768 + VLAN 10 = 32778 · 00:0c:29:aa:00:01  → mniejszy = lepszy', { mono: true, size: 13, bold: true });
  const fields = [['Root Identifier', 'kogo nadawca uważa za roota'], ['Root Path Cost', 'koszt nadawcy do roota'], ['Bridge Identifier', 'kto wysyła'], ['Port Identifier', 'z którego portu (128.N)'], ['Flags (RSTP)', 'rola portu, proposal, agreement, TC'], ['Timery', 'hello 2 s, max age 20 s, forward delay 15 s']];
  fields.forEach(([n, d], i) => { const cx = 60 + (i % 3) * 270, cy = 186 + Math.floor(i / 3) * 70; b += box(cx, cy, 255, 58, C.card, C.vio, 6) + text(cx + 12, cy + 24, n, { bold: true, fill: C.vio, size: 13 }) + text(cx + 12, cy + 44, d, { size: 11 }); });
  b += text(W / 2, 356, 'Porównanie BPDU: najniższy Root ID → najniższy koszt → najniższy Bridge ID nadawcy → najniższy Port ID.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-07-bpdu.svg'] = svg(W, H, 'Struktura Bridge ID: 4 bity priorytetu, 12 bitów system ID extension równego numerowi VLAN i 48 bitów adresu MAC; przykład 32768 plus VLAN 10 daje 32778. Najważniejsze pola BPDU: Root Identifier, Root Path Cost, Bridge Identifier, Port Identifier, flagi RSTP i timery. Kolejność porównań: Root ID, koszt, Bridge ID nadawcy, Port ID.', b);
}

// 4. Gdzie wlaczyc ochrony
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Gdzie włączyć ochrony STP', { size: 18, bold: true, anchor: 'middle' });
  const sw = (x, y, n, fill, col) => box(x - 70, y - 22, 140, 44, fill, col, 8) + text(x, y + 5, n, { anchor: 'middle', bold: true, fill: col });
  b += sw(300, 80, 'CORE1 (root)', C.okFill, C.ok) + sw(600, 80, 'CORE2 (zapas.)', C.netFill, C.net);
  b += sw(200, 230, 'ACC1', C.netFill, C.net) + sw(450, 230, 'ACC2', C.netFill, C.net) + sw(700, 230, 'ACC3', C.netFill, C.net);
  b += line(370, 80, 530, 80, { w: 4 });
  [[200, 230], [450, 230], [700, 230]].forEach(([x, y]) => { b += line(300, 102, x, y - 22, { w: 2 }) + line(600, 102, x, y - 22, { w: 2 }); });
  b += box(150, 140, 120, 22, C.vioFill, C.vio, 4) + text(210, 156, 'Root Guard ↓', { anchor: 'middle', size: 11, bold: true, fill: C.vio });
  b += box(640, 140, 160, 22, C.hostFill, C.host, 4) + text(720, 156, 'Loop Guard / UDLD', { anchor: 'middle', size: 11, bold: true, fill: C.host });
  [[150, 'PC'], [250, 'PC'], [450, 'drukarka'], [650, 'serwer'], [760, 'AP']].forEach(([x, n], i) => {
    const sx = i < 2 ? 200 : i < 3 ? 450 : 700;
    b += line(sx, 252, x, 312, { color: C.muted }) + box(x - 40, 312, 80, 30, C.card, C.ink, 6) + text(x, 332, n, { anchor: 'middle', size: 12 });
  });
  b += box(110, 352, 680, 26, C.noFill, C.no, 4) + text(450, 370, 'porty brzegowe: PortFast + BPDU Guard (+ storm control)', { anchor: 'middle', size: 12, bold: true, fill: C.no });
  b += text(W / 2, 404, 'Root Guard na rdzeniu w stronę dostępu · Loop Guard na portach alternate/root uplinków · BPDU Guard na portach użytkowników', { anchor: 'middle', size: 12, bold: true });
  files['ns02-07-guards.svg'] = svg(W, H, 'Sieć hierarchiczna: CORE1 jako root, CORE2 zapasowy, trzy przełączniki dostępowe połączone z oboma rdzeniami i hosty na portach brzegowych. Root Guard na portach rdzenia w stronę dostępu, Loop Guard i UDLD na uplinkach, PortFast z BPDU Guard i storm control na portach użytkowników.', b);
}

writeSvgs(process.argv[2], files);
