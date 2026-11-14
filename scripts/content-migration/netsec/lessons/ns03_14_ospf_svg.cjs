// Ilustracje do lekcji _ns_03/14_OspfSingleArea.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_14_ospf_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { LINKS, graphFrom, spf, paths, cost } = require('./ns03_14_ospf_model.cjs');
const files = {};

// 1. Topologia i koszty
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Koszt OSPF = referencyjna przepustowość / przepustowość łącza — pułapka domyślnych 100 Mb/s', { size: 16, bold: true, anchor: 'middle' });
  const draw = (x0, ref, title) => {
    const pos = { R1: [x0 + 60, 200], R2: [x0 + 200, 110], R3: [x0 + 200, 290], R4: [x0 + 340, 200] };
    const g = graphFrom(LINKS, ref), res = spf(g, 'R1'), best = paths(res, 'R4', 'R1');
    const onBest = (a, c) => best.some((p) => p.some((n, i) => i < p.length - 1 && ((n === a && p[i + 1] === c) || (n === c && p[i + 1] === a))));
    let s = text(x0 + 200, 62, title, { anchor: 'middle', bold: true, size: 14 });
    for (const [a, c, bw] of LINKS) {
      const [x1, y1] = pos[a], [x2, y2] = pos[c], hot = onBest(a, c);
      s += line(x1, y1, x2, y2, { color: hot ? C.ok : C.line, w: hot ? 4 : 2 });
      const lbl = `${bw >= 1000 ? bw / 1000 + 'G' : bw + 'M'} · koszt ${g[a][c]}`, lw = lbl.length * 6.4 + 8;
      const lx = (x1 + x2) / 2 + (a === 'R2' && c === 'R3' ? 8 + lw / 2 : 0), ly = (y1 + y2) / 2;
      s += box(lx - lw / 2, ly - 10, lw, 18, C.bg, C.bg, 4) + text(lx, ly + 4, lbl, { anchor: 'middle', size: 11, bold: true, fill: hot ? C.ok : C.muted });
    }
    for (const [n, [x, y]] of Object.entries(pos)) s += box(x - 28, y - 18, 56, 36, n === 'R1' ? C.netFill : C.hostFill, n === 'R1' ? C.net : C.host, 8) + text(x, y + 5, n, { anchor: 'middle', bold: true });
    s += text(x0 + 200, 340, `R1 → R4: ${best.map((p) => p.join('→')).join('  i  ')} (koszt ${res.dist.R4})`, { anchor: 'middle', size: 12, bold: true, fill: best.length > 1 ? C.no : C.ok });
    return s;
  };
  b += draw(30, 100, 'auto-cost reference-bandwidth 100 (domyślnie)') + draw(470, 10000, 'auto-cost reference-bandwidth 10000');
  b += box(40, 362, 820, 54, C.card, C.line, 8);
  b += text(56, 384, `Domyślnie wszystko ≥ 100 Mb/s ma koszt 1 (1G: ${cost(1000)}, 10G: ${cost(10000)}) → ECMP także przez wolne 100 Mb/s. Z referencją 10 Gb/s: 1G = ${cost(1000, 10000)}, 100M = ${cost(100, 10000)}.`, { size: 12 });
  b += text(56, 404, 'Ustaw tę samą referencję na WSZYSTKICH routerach obszaru (FRR/Cisco: auto-cost reference-bandwidth <Mb/s>).', { size: 12, bold: true, fill: C.no });
  files['ns03-14-cost.svg'] = svg(W, H, 'Topologia R1–R4 z łączami 1 Gb/s (R1–R2, R2–R4), 100 Mb/s (R1–R3, R3–R4) i 10 Mb/s (R2–R3). Przy domyślnej referencji 100 Mb/s łącza 1 Gb/s i 100 Mb/s mają koszt 1, więc R1 rozkłada ruch do R4 na dwie ścieżki (także wolną). Przy referencji 10 000 Mb/s koszty to 10 i 100, wybierana jest tylko ścieżka przez R2.', b);
}

// 2. Stany sasiedztwa
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Od Hello do Full: stany sąsiedztwa i pakiety OSPF', { size: 18, bold: true, anchor: 'middle' });
  const st = [
    ['Down', 'brak Hello od sąsiada', C.muted], ['Init', 'dostałem Hello, ale bez mojego RID', C.host], ['2-Way', 'widzimy się nawzajem; wybór DR/BDR', C.host],
    ['ExStart', 'master/slave dla DBD — tu utyka przy złym MTU', C.no], ['Exchange', 'wymiana DBD (spis LSA)', C.vio], ['Loading', 'LSR → LSU: dociągnięcie brakujących LSA', C.vio], ['Full', 'LSDB zsynchronizowane — liczymy SPF', C.ok],
  ];
  st.forEach(([n, d, col], i) => {
    const y = 52 + i * 46;
    b += box(40, y, 140, 38, col === C.muted ? C.card : col === C.ok ? C.okFill : col === C.no ? C.noFill : col === C.vio ? C.vioFill : C.hostFill, col, 6) + text(110, y + 24, n, { anchor: 'middle', bold: true, fill: col });
    b += text(196, y + 24, d, { size: 12, bold: col === C.no });
    if (i < st.length - 1) b += line(110, y + 38, 110, y + 46, { arrow: true });
  });
  b += box(560, 52, 300, 316, C.card, C.line, 8);
  ['Typy pakietów OSPF (protokół IP 89):', '1 Hello — wykrywanie sąsiadów, keepalive', '2 DBD — opis bazy (nagłówki LSA)', '3 LSR — żądanie konkretnych LSA', '4 LSU — aktualizacja (pełne LSA)', '5 LSAck — potwierdzenie', '', 'Hello musi się zgadzać:', '• obszar i typ obszaru', '• maska podsieci (sieci broadcast)', '• Hello/Dead interval (10/40 s)', '• uwierzytelnianie', '• unikalny Router ID', 'MTU sprawdzany w DBD → ExStart'].forEach((t, i) => { b += text(576, 76 + i * 21, t, { size: 12, bold: i === 0 || i === 7 }); });
  b += text(40, 392, 'Multicast: 224.0.0.5 (AllSPFRouters), 224.0.0.6 (AllDRouters); OSPFv3: ff02::5, ff02::6. Na łączu P2P brak DR/BDR.', { size: 12 });
  b += text(40, 414, 'Diagnoza: show ip ospf neighbor (FRR: vtysh -c "show ip ospf neighbor") — stan, priorytet, Dead time, interfejs.', { size: 12, mono: true });
  files['ns03-14-states.svg'] = svg(W, H, 'Stany sąsiedztwa OSPF: Down, Init, 2-Way (wybór DR/BDR), ExStart (ustalanie master/slave, tu zatrzymuje się przy niezgodnym MTU), Exchange (DBD), Loading (LSR i LSU), Full. Typy pakietów: Hello, DBD, LSR, LSU, LSAck; parametry, które muszą się zgadzać w Hello; adresy multicast 224.0.0.5 i 224.0.0.6.', b);
}

// 3. DR/BDR
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Segment broadcast: DR i BDR ograniczają liczbę pełnych sąsiedztw', { size: 18, bold: true, anchor: 'middle' });
  b += box(330, 70, 240, 30, C.netFill, C.net, 6) + text(450, 90, 'przełącznik / VLAN 10', { anchor: 'middle', bold: true, size: 12 });
  const rs = [['R1', 'prio 1, RID 1.1.1.1', 'DROTHER', 90], ['R2', 'prio 1, RID 2.2.2.2', 'BDR', 270], ['R3', 'prio 100, RID 3.3.3.3', 'DR', 450], ['R4', 'prio 1, RID 4.4.4.4', 'DROTHER', 630], ['R5', 'prio 0', 'nigdy DR', 810]];
  rs.forEach(([n, p, role, x]) => {
    const col = role === 'DR' ? C.ok : role === 'BDR' ? C.vio : C.muted;
    b += line(x, 140, 450, 100, { color: C.line }) + box(x - 70, 140, 140, 70, role === 'DR' ? C.okFill : role === 'BDR' ? C.vioFill : C.card, col, 8) + text(x, 164, n, { anchor: 'middle', bold: true }) + text(x, 182, p, { anchor: 'middle', size: 10 }) + text(x, 200, role, { anchor: 'middle', size: 12, bold: true, fill: col });
  });
  b += box(40, 236, 820, 148, C.card, C.line, 8);
  b += text(60, 260, 'Wybór: najwyższy priorytet interfejsu (0 = nie kandyduje), przy remisie najwyższy Router ID. Brak wywłaszczania —', { size: 12 });
  b += text(60, 280, 'nowy router z wyższym priorytetem NIE przejmie roli DR, dopóki obecny DR działa (restart procesu: clear ip ospf process).', { size: 12 });
  b += text(60, 306, 'Bez DR: n(n−1)/2 sąsiedztw Full (5 routerów → 10). Z DR/BDR: każdy DROTHER Full tylko z DR i BDR (5 → 7), między DROTHER — 2-Way.', { size: 12, bold: true });
  b += text(60, 332, 'Router ID: ręcznie (router-id) > najwyższy adres loopback > najwyższy adres aktywnego interfejsu. Zmiana RID = restart procesu.', { size: 12 });
  b += text(60, 358, 'Na łączach P2P (i w Kubernetes/chmurze z /31) ustaw ip ospf network point-to-point — szybciej, bez wyboru DR.', { size: 12, fill: C.ok, bold: true });
  files['ns03-14-dr.svg'] = svg(W, H, 'Pięć routerów w jednym VLAN-ie: R3 z priorytetem 100 zostaje DR, R2 z najwyższym Router ID wśród pozostałych BDR, R1 i R4 DROTHER, R5 z priorytetem 0 nigdy nie zostanie DR. Brak wywłaszczania, liczba pełnych sąsiedztw spada z 10 do 7, kolejność wyboru Router ID.', b);
}

// 4. LSDB -> SPF -> RIB
{
  const W = 900, H = 360;
  let b = text(W / 2, 30, 'Link-state: każdy router ma tę samą mapę (LSDB) i sam liczy najkrótsze ścieżki', { size: 17, bold: true, anchor: 'middle' });
  const steps = [['LSA typ 1 (Router)', 'każdy router:', 'moje łącza i koszty', C.net, C.netFill], ['LSA typ 2 (Network)', 'DR opisuje', 'segment broadcast', C.vio, C.vioFill], ['LSDB', 'identyczna', 'w całym obszarze', C.host, C.hostFill], ['SPF (Dijkstra)', 'drzewo najkrótszych', 'ścieżek od siebie', C.ok, C.okFill], ['RIB', 'trasy „O”', 'AD 110 + koszt', C.ok, C.okFill]];
  steps.forEach(([h, d1, d2, col, fill], i) => { const x = 30 + i * 172; b += box(x, 70, 160, 90, fill, col, 8) + text(x + 80, 100, h, { anchor: 'middle', bold: true, fill: col, size: 13 }) + text(x + 80, 126, d1, { anchor: 'middle', size: 11 }) + text(x + 80, 144, d2, { anchor: 'middle', size: 11 }); if (i < 4) b += line(x + 162, 115, x + 170, 115, { arrow: true }); });
  b += box(40, 186, 820, 156, C.card, C.line, 8);
  b += text(60, 210, 'Distance-vector (RIP): router zna tylko „odległość i kierunek” od sąsiadów — wolna zbieżność, pętle, limit 15 skoków.', { size: 12 });
  b += text(60, 234, 'Link-state (OSPF, IS-IS): pełna mapa obszaru → szybka zbieżność, brak pętli, ale więcej pamięci/CPU i flooding przy zmianach.', { size: 12 });
  b += text(60, 260, 'Inne typy LSA (w kolejnych obszarach i redystrybucji): 3 Summary (ABR), 4 ASBR-summary, 5 External, 7 NSSA.', { size: 12 });
  b += text(60, 286, 'FRR:  show ip ospf database   ·   show ip ospf route   ·   show ip route ospf   ·   show ip ospf interface', { size: 12, mono: true });
  b += text(60, 316, 'OSPFv2 = IPv4 (protokół 89, 224.0.0.5/6); OSPFv3 = IPv6 (link-local, ff02::5/6, adresy w osobnych LSA, AF dla IPv4).', { size: 12, bold: true });
  files['ns03-14-lsa.svg'] = svg(W, H, 'Przepływ w OSPF: LSA typu 1 (router) i typu 2 (network, od DR) tworzą identyczną bazę LSDB w obszarze, każdy router liczy SPF algorytmem Dijkstry i instaluje trasy O z AD 110. Porównanie link-state z distance-vector, inne typy LSA, polecenia FRR, OSPFv2 i OSPFv3.', b);
}

writeSvgs(process.argv[2], files);
