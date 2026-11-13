// Ilustracje do lekcji _ns_02/04_UnicastBroadcastMulticast.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_04_casts_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, node, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Cztery tryby dostarczania
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Cztery sposoby adresowania odbiorców', { size: 19, bold: true, anchor: 'middle' });
  const modes = [
    ['Unicast', 'jeden → jeden', C.net, [1, 0, 0, 0, 0]],
    ['Broadcast', 'jeden → wszyscy w segmencie', C.host, [1, 1, 1, 1, 1]],
    ['Multicast', 'jeden → zapisani do grupy', C.ok, [1, 0, 1, 0, 1]],
    ['Anycast', 'jeden → najbliższy z wielu', C.vio, [1, 0, 0, 0, 0]],
  ];
  modes.forEach(([n, d, col, hits], i) => {
    const x = 20 + i * 220;
    b += box(x, 56, 205, 300, C.card, col, 10) + text(x + 102, 82, n, { anchor: 'middle', bold: true, fill: col, size: 15 }) + text(x + 102, 102, d, { anchor: 'middle', size: 11 });
    b += node(x + 102, 150, 'N', { fill: C.netFill, stroke: col, color: col });
    const pts = [[x + 30, 300], [x + 66, 300], [x + 102, 300], [x + 138, 300], [x + 174, 300]];
    pts.forEach(([px, py], k) => {
      const hit = hits[k] === 1;
      b += line(x + 102, 166, px, py - 16, { color: hit ? col : C.line, w: hit ? 3 : 1, dash: !hit });
      b += node(px, py, i === 3 ? 'S' : 'H', { r: 13, size: 10, fill: hit ? C.okFill : C.card, stroke: hit ? col : C.line });
    });
    if (i === 3) b += text(x + 102, 335, 'ten sam adres na wielu serwerach', { anchor: 'middle', size: 10 }) + text(x + 102, 348, '— routing wybiera najbliższy', { anchor: 'middle', size: 10 });
    if (i === 2) b += text(x + 102, 340, 'tylko hosty z IGMP/MLD join', { anchor: 'middle', size: 10 });
    if (i === 1) b += text(x + 102, 340, 'nie przechodzi przez router', { anchor: 'middle', size: 10 });
    if (i === 0) b += text(x + 102, 340, 'zwykły ruch TCP/UDP', { anchor: 'middle', size: 10 });
  });
  b += text(W / 2, 384, 'IPv6 nie ma broadcastu — jego rolę przejął multicast (np. ff02::1 wszystkie węzły).', { anchor: 'middle', size: 12, bold: true });
  files['ns02-04-modes.svg'] = svg(W, H, 'Cztery panele: unicast — nadawca do jednego hosta; broadcast — do wszystkich w segmencie; multicast — tylko do hostów zapisanych do grupy; anycast — do najbliższego z wielu serwerów o tym samym adresie', b);
}

// 2. Mapowanie IPv4 multicast -> MAC
{
  const W = 900, H = 360;
  let b = text(W / 2, 32, 'Adres multicast IPv4 → MAC: 01:00:5e + dolne 23 bity', { size: 18, bold: true, anchor: 'middle' });
  b += text(60, 80, 'IP 224.0.0.251 (mDNS):', { bold: true, size: 13 });
  const bits = '11100000.00000000.00000000.11111011';
  bits.split('').forEach((c, i) => { const x = 250 + i * 18; const lower23 = i >= 10 && c !== '.'; if (c === '.') b += text(x + 6, 82, '.', { size: 14 }); else b += box(x, 66, 15, 22, lower23 ? C.okFill : (i < 4 ? C.hostFill : C.card), lower23 ? C.ok : C.line, 2) + text(x + 7.5, 82, c, { anchor: 'middle', mono: true, size: 11 }); });
  b += text(250 + 4 * 18, 108, '1110 = klasa D', { size: 11, fill: C.host, bold: true });
  b += text(250 + 9 * 18, 108, '5 bitów ginie', { size: 11, fill: C.no, bold: true });
  b += text(250 + 22 * 18, 108, 'dolne 23 bity → MAC', { size: 11, fill: C.ok, bold: true });
  b += box(60, 130, 780, 60, C.card, C.line, 8) + text(80, 158, 'MAC = 01:00:5e (prefiks IANA, bit 24 = 0) + 23 bity → 01:00:5e:00:00:fb', { mono: true, size: 14, bold: true }) + text(80, 180, 'ramka do grupy mDNS ma zawsze docelowy MAC 01:00:5e:00:00:fb', { size: 12, fill: C.muted });
  b += box(60, 210, 780, 110, C.noFill, C.no, 8) + text(80, 236, 'Niejednoznaczność 32 : 1', { bold: true, fill: C.no });
  b += text(80, 260, '224.0.0.251, 225.0.0.251, 239.128.0.251 … (32 adresy) mają ten sam MAC 01:00:5e:00:00:fb', { size: 12, mono: true });
  b += text(80, 284, '→ karta przyjmie ramkę, ale jądro odrzuci ją, jeśli host nie należy do tej konkretnej grupy IP.', { size: 12 });
  b += text(80, 306, 'IPv6: MAC = 33:33 + dolne 32 bity adresu multicast (np. ff02::fb → 33:33:00:00:00:fb).', { size: 12 });
  files['ns02-04-mcast-mac.svg'] = svg(W, H, 'Rozpisanie adresu 224.0.0.251 na bity: cztery pierwsze bity 1110 oznaczają multicast, dolne 23 bity przechodzą do adresu MAC 01:00:5e:00:00:fb; 5 bitów ginie, więc 32 adresy IP mają ten sam MAC; dla IPv6 MAC to 33:33 plus dolne 32 bity', b);
}

// 3. IGMP i snooping
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'IGMP/MLD: zapis do grupy i snooping na przełączniku', { size: 18, bold: true, anchor: 'middle' });
  b += node(450, 90, 'router (querier)', { square: true, w: 74, size: 12, fill: C.vioFill, stroke: C.vio, color: C.vio });
  b += node(450, 190, 'przełącznik z IGMP snooping', { square: true, w: 120, size: 12, fill: C.netFill, stroke: C.net, color: C.net });
  b += line(450, 106, 450, 174, { color: C.ink });
  const hs = [['H1', 180, true], ['H2', 340, false], ['H3', 560, true], ['H4', 720, false]];
  hs.forEach(([n, x, m]) => {
    b += line(450, 206, x, 284, { color: m ? C.ok : C.line, w: m ? 3 : 1, dash: !m });
    b += node(x, 300, n, { r: 18, fill: m ? C.okFill : C.card, stroke: m ? C.ok : C.line });
    b += text(x, 340, m ? 'IGMP join 239.1.1.1' : 'nie zapisany', { anchor: 'middle', size: 11, fill: m ? C.ok : C.muted, bold: m });
  });
  b += text(610, 120, 'Membership Query (co ~125 s)', { size: 11, fill: C.vio });
  b += text(W / 2, 372, 'Strumień do 239.1.1.1 trafia tylko do H1 i H3; bez snoopingu przełącznik zalałby nim wszystkie porty.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-04-igmp.svg'] = svg(W, H, 'Router jako querier, przełącznik z IGMP snooping i cztery hosty: H1 i H3 wysłały IGMP join do grupy 239.1.1.1 i tylko do nich trafia strumień; H2 i H4 go nie dostają', b);
}

// 4. Anycast DNS
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Anycast: ten sam adres w wielu miejscach, BGP wybiera najbliższe', { size: 18, bold: true, anchor: 'middle' });
  const sites = [['Frankfurt', 200, 120], ['Warszawa', 450, 90], ['Londyn', 700, 120]];
  sites.forEach(([n, x, y]) => { b += box(x - 80, y - 26, 160, 52, C.vioFill, C.vio, 8) + text(x, y - 4, `resolver ${n}`, { anchor: 'middle', bold: true, size: 12, fill: C.vio }) + text(x, y + 14, '9.9.9.9 (ten sam!)', { anchor: 'middle', size: 11, mono: true }); });
  const users = [['użytkownik w Krakowie', 450, 290, 1], ['użytkownik w Paryżu', 200, 290, 0], ['użytkownik w Dublinie', 700, 290, 2]];
  users.forEach(([n, x, y, s]) => { b += box(x - 90, y - 22, 180, 44, C.card, C.ink, 8) + text(x, y + 5, n, { anchor: 'middle', size: 12 }); b += line(x, y - 24, sites[s][1], sites[s][2] + 28, { color: C.ok, w: 3, arrow: true }); });
  b += text(W / 2, 352, 'Zastosowania: serwery główne DNS, publiczne resolvery, CDN, ochrona przed DDoS (atak rozkłada się na wiele lokalizacji).', { anchor: 'middle', size: 12, bold: true });
  files['ns02-04-anycast.svg'] = svg(W, H, 'Trzy lokalizacje resolvera DNS we Frankfurcie, Warszawie i Londynie z tym samym adresem IP; użytkownicy z Krakowa, Paryża i Dublina trafiają do najbliższej lokalizacji wybranej przez routing BGP', b);
}

writeSvgs(process.argv[2], files);
