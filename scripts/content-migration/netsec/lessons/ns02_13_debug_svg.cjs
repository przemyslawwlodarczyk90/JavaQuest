// Ilustracje do lekcji _ns_02/13_DebugLanProblems.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_13_debug_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Metodyka
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Metodyka: od kabla w górę — każdy krok ma jedno polecenie i jedno pytanie', { size: 17, bold: true, anchor: 'middle' });
  const steps = [
    ['1. Łącze', 'ip -br link · ethtool', 'czy interfejs jest UP i LOWER_UP?', C.net],
    ['2. Adres', 'ip -br addr', 'czy adres i maska są właściwe? duplikat?', C.host],
    ['3. Sąsiad', 'ip neigh · arping', 'czy ARP do celu/bramy dostaje odpowiedź?', C.vio],
    ['4. Brama/trasa', 'ip route get <cel>', 'którędy pójdzie pakiet? brama w podsieci?', C.ok],
    ['5. Ścieżka/MTU', 'ping -M do -s · tracepath', 'czy duże pakiety przechodzą?', C.no],
    ['6. Nazwy i usługa', 'resolvectl · ss · curl -v', 'DNS, port, aplikacja — dopiero teraz', C.ink],
  ];
  steps.forEach(([n, cmd, q, col], i) => {
    const y = 56 + i * 56;
    b += box(60, y, 190, 44, C.card, col, 8) + text(155, y + 28, n, { anchor: 'middle', bold: true, fill: col, size: 14 });
    b += box(270, y, 250, 44, C.card, C.line, 8) + text(285, y + 28, cmd, { mono: true, size: 12 });
    b += text(540, y + 28, q, { size: 12 });
    if (i < steps.length - 1) b += line(155, y + 44, 155, y + 56, { arrow: true, w: 2 });
  });
  b += text(W / 2, 404, 'Zatrzymaj się na pierwszym kroku, który zawodzi — reszta jest jego skutkiem.', { anchor: 'middle', size: 13, bold: true, fill: C.no });
  files['ns02-13-method.svg'] = svg(W, H, 'Sześć kroków diagnozy sieci lokalnej z poleceniami: łącze (ip -br link, ethtool), adres (ip -br addr), sąsiad (ip neigh, arping), brama i trasa (ip route get), ścieżka i MTU (ping -M do -s, tracepath), nazwy i usługa (resolvectl, ss, curl -v). Zatrzymaj się na pierwszym kroku, który zawodzi.', b);
}

// 2. Topologia labu
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Debug Lab: bridge br-lab, router i cztery błędy w pięciu hostach (namespace’ach)', { size: 18, bold: true, anchor: 'middle' });
  b += box(330, 70, 240, 50, C.netFill, C.net, 8) + text(450, 92, 'br-lab (10.13.0.0/24)', { anchor: 'middle', bold: true, fill: C.net }) + text(450, 110, 'MTU 1500', { anchor: 'middle', size: 11 });
  b += box(640, 70, 220, 50, C.vioFill, C.vio, 8) + text(750, 92, 'ns-router 10.13.0.1', { anchor: 'middle', bold: true, fill: C.vio }) + text(750, 110, '→ ns-srv 10.99.0.10', { anchor: 'middle', size: 11, mono: true });
  b += line(572, 95, 638, 95, { w: 2 });
  const hs = [
    ['ns-a', '10.13.0.11/24', 'duplikat IP z ns-b', 120],
    ['ns-b', '10.13.0.11/24', '(ofiara duplikatu)', 330],
    ['ns-c', '10.13.0.13/28', 'zła maska', 540],
    ['ns-d', '10.13.0.14/24', 'brak trasy domyślnej', 750],
  ];
  hs.forEach(([n, ip, f, x]) => {
    b += line(450, 122, x, 200, { color: C.muted });
    b += box(x - 95, 202, 190, 80, C.noFill, C.no, 8) + text(x, 226, n, { anchor: 'middle', bold: true, size: 14 }) + text(x, 248, ip, { anchor: 'middle', mono: true, size: 12 }) + text(x, 270, f, { anchor: 'middle', size: 11, fill: C.no, bold: true });
  });
  b += line(450, 122, 860, 160, { color: C.muted, dash: true }) + line(860, 160, 860, 298, { color: C.muted, dash: true });
  b += box(560, 300, 300, 50, C.hostFill, C.host, 8) + text(710, 322, 'ns-e: MTU 9000 na łączu 1500', { anchor: 'middle', size: 12, bold: true, fill: C.host }) + text(710, 340, '(czwarty błąd: MTU)', { anchor: 'middle', size: 10 });
  b += box(40, 300, 500, 100, C.card, C.line, 8);
  b += text(60, 324, 'setup.sh — buduje lab (namespace, veth, bridge, błędy)', { mono: true, size: 12 });
  b += text(60, 346, 'check.sh — testy: ping do bramy i ns-srv, curl, duży pakiet', { mono: true, size: 12 });
  b += text(60, 368, 'Cel: każdy test PASS. Naprawiaj tylko konfigurację hosta,', { size: 12 });
  b += text(60, 388, 'zapisuj objaw → hipoteza → dowód → poprawka.', { size: 12, bold: true });
  files['ns02-13-lab.svg'] = svg(W, H, 'Topologia Debug Labu: bridge br-lab z podsiecią 10.13.0.0/24 i routerem 10.13.0.1 prowadzącym do serwera 10.99.0.10; zepsute namespace’y: ns-a i ns-b z tym samym adresem 10.13.0.11, ns-c z maską /28, ns-d bez trasy domyślnej, ns-e z MTU 9000 na łączu 1500. Skrypty setup.sh i check.sh.', b);
}

// 3. Zla maska
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Zła maska: host „widzi” mniejszą sieć niż reszta', { size: 18, bold: true, anchor: 'middle' });
  const x0 = 60, x1 = 840; const X = (o) => x0 + (o / 256) * (x1 - x0);
  b += box(X(0), 80, X(256) - X(0), 50, C.okFill, C.ok, 4) + text(X(128), 110, 'sieć wg routera i innych: 10.13.0.0/24 (.0–.255)', { anchor: 'middle', bold: true, fill: C.ok });
  b += box(X(0), 150, X(16) - X(0), 50, C.noFill, C.no, 4) + text(X(16) + 8, 180, '← ns-c /28: „moja sieć” to tylko 10.13.0.0–.15', { size: 12, bold: true, fill: C.no });
  [[1, 'brama .1'], [13, 'ns-c .13'], [14, 'ns-d .14'], [40, 'drukarka .40'], [200, 'serwer .200']].forEach(([o, n], i) => { b += line(X(o), 220, X(o), 240, { color: C.ink }) + text(X(o) + (i === 2 ? 30 : 0), 256 + (i % 2) * 16, n, { anchor: 'middle', size: 11 }); });
  b += box(60, 290, 780, 74, C.card, C.line, 8);
  b += text(80, 314, 'ns-c → brama .1, ns-d .14: w „jego” sieci → ARP, działa. ns-c → drukarka .40: poza /28 → wysyła do bramy.', { size: 12 });
  b += text(80, 336, 'Router odsyła ramkę z powrotem na ten sam interfejs (ICMP redirect) — działa „dziwnie” albo wcale (firewall).', { size: 12 });
  b += text(80, 356, 'Objaw: „niektóre hosty w tej samej sieci odpowiadają, inne nie”. Dowód: ip route get 10.13.0.40 → via 10.13.0.1.', { size: 12, bold: true, fill: C.no });
  files['ns02-13-mask.svg'] = svg(W, H, 'Oś adresów 10.13.0.0–255: według routera i hostów sieć to /24, ale host ns-c z maską /28 uważa za lokalne tylko adresy 0–15. Do bramy .1 i hosta .14 wysyła bezpośrednio, a do drukarki .40 i serwera .200 przez bramę, co daje przekierowania ICMP lub brak łączności. Dowód: ip route get 10.13.0.40 pokazuje via 10.13.0.1.', b);
}

// 4. MTU black hole
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'MTU: małe pakiety przechodzą, duże giną — „SSH działa, a scp i HTTPS wiszą”', { size: 16, bold: true, anchor: 'middle' });
  b += box(40, 80, 180, 60, C.hostFill, C.host, 8) + text(130, 106, 'ns-e', { anchor: 'middle', bold: true }) + text(130, 126, 'MTU 9000', { anchor: 'middle', size: 12, mono: true });
  b += box(360, 80, 180, 60, C.netFill, C.net, 8) + text(450, 106, 'br-lab / łącze', { anchor: 'middle', bold: true }) + text(450, 126, 'MTU 1500', { anchor: 'middle', size: 12, mono: true });
  b += box(680, 80, 180, 60, C.vioFill, C.vio, 8) + text(770, 106, 'ns-router', { anchor: 'middle', bold: true }) + text(770, 126, 'MTU 1500', { anchor: 'middle', size: 12, mono: true });
  b += line(542, 110, 678, 110, { w: 2 });
  b += line(222, 100, 358, 100, { arrow: true, w: 2, color: C.ok }) + text(290, 92, 'ping 56 B ✓', { anchor: 'middle', size: 11, fill: C.ok, bold: true });
  b += line(222, 124, 330, 124, { w: 4, color: C.no }) + text(345, 130, '✕', { size: 18, bold: true, fill: C.no }) + text(276, 152, 'ramka 4000 B — odrzucona', { anchor: 'middle', size: 11, fill: C.no, bold: true });
  b += box(40, 180, 820, 180, C.card, C.line, 8);
  b += text(60, 206, 'Test: ip netns exec ns-e ping -M do -s 1472 10.13.0.1   → przechodzi (1472 + 28 = 1500)', { mono: true, size: 12 });
  b += text(105, 230, 'ip netns exec ns-e ping -M do -s 1473 10.13.0.1   → nie przechodzi (1501 > 1500)', { mono: true, size: 12 });
  b += text(105, 254, 'ip netns exec ns-e ping -M do -s 3000 10.13.0.1   → host wysyła, bo jego MTU = 9000; ramka ginie na łączu', { mono: true, size: 12 });
  b += text(60, 286, 'Dlaczego TCP „wisi”: handshake (małe pakiety) przechodzi, MSS wynegocjowane z MTU 9000,', { size: 12 });
  b += text(60, 306, 'pierwsze pełne segmenty danych giną bez ICMP „Fragmentation needed” (to nie router, tylko ta sama domena L2).', { size: 12 });
  b += text(60, 334, 'Naprawa: wszystkie interfejsy w jednej domenie L2 mają to samo MTU → ip link set dev eth0 mtu 1500.', { size: 12, bold: true, fill: C.ok });
  files['ns02-13-mtu.svg'] = svg(W, H, 'Host ns-e ma MTU 9000, a łącze i router MTU 1500: mały ping przechodzi, ramka 4000 bajtów jest odrzucana. Testy ping -M do: 1472 bajty przechodzą, 1473 nie, 3000 host wysyła, ale ramka ginie. TCP wisi po handshake, bo MSS wynegocjowano z MTU 9000. Naprawa: jedno MTU w domenie L2.', b);
}

writeSvgs(process.argv[2], files);
