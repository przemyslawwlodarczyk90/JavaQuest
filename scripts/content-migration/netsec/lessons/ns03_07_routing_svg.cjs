// Ilustracje do lekcji _ns_03/07_RoutingTablesAndDefaultGateway.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_07_routing_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { lookup, fmtRoute, LAPTOP, VPN_CONFLICT, ROUTER } = require('./ns03_07_routing_model.cjs');
const { parse } = require('./ns03_04_cidr_model.cjs');
const files = {};

// 1. LPM
{
  const W = 900, H = 400, ip = '10.20.30.200';
  const res = lookup(ROUTER, ip);
  let b = text(W / 2, 30, `Longest prefix match: dokąd router wyśle pakiet do ${ip}?`, { size: 18, bold: true, anchor: 'middle' });
  ROUTER.forEach((r, i) => {
    const y = 56 + i * 36, hit = res.candidates.includes(r), win = r === res.route;
    const [col, fill] = win ? [C.ok, C.okFill] : hit ? [C.host, C.hostFill] : [C.line, C.card];
    b += box(60, y, 600, 30, fill, col, 4) + text(76, y + 20, fmtRoute(r), { mono: true, size: 13, bold: win });
    b += text(680, y + 20, win ? `✓ WYGRYWA (/${parse(r.dst).prefix})` : hit ? `pasuje (/${parse(r.dst).prefix})` : 'nie pasuje', { size: 13, bold: win, fill: win ? C.ok : hit ? C.host : C.muted });
  });
  b += text(60, 356, 'Pasują: /0, /8, /16, /24 i /25 — wybierana jest NAJDŁUŻSZA (najbardziej szczegółowa) trasa.', { size: 13 });
  b += text(60, 380, 'Metryka rozstrzyga dopiero między trasami o tej samej długości prefiksu (192.168.50.0/24: metric 10 < 30).', { size: 13, bold: true, fill: C.net });
  files['ns03-07-lpm.svg'] = svg(W, H, 'Tablica routera z trasami default, 10.0.0.0/8, 10.20.0.0/16, 10.20.30.0/24, 10.20.30.128/25 i trasami do 192.168. Dla adresu 10.20.30.200 pasuje pięć tras, wygrywa najdłuższy prefiks /25 przez 10.255.0.14. Metryka rozstrzyga tylko przy równej długości prefiksu.', b);
}

// 2. Laptop
{
  const W = 900, H = 430;
  let b = text(W / 2, 30, 'Tablica routingu laptopa developera: Wi-Fi, Docker i VPN (split tunnel)', { size: 17, bold: true, anchor: 'middle' });
  b += box(330, 60, 240, 70, C.card, C.ink, 10) + text(450, 92, 'laptop', { anchor: 'middle', bold: true, size: 16 }) + text(450, 114, 'ip route', { anchor: 'middle', mono: true, size: 12 });
  const ifs = [['wlan0 192.168.1.20', 'router 192.168.1.1 → Internet', 180, C.net, C.netFill], ['docker0 172.17.0.1 / br-3f1c', 'kontenery 172.17.0.0/16, 172.18.0.0/16', 450, C.host, C.hostFill], ['tun0 10.8.0.5', 'VPN → 10.0.0.0/8, 172.16.0.0/12', 720, C.vio, C.vioFill]];
  ifs.forEach(([n, t, x, col, fill]) => { b += line(450, 132, x, 168, { color: col, w: 2 }) + box(x - 130, 170, 260, 60, fill, col, 8) + text(x, 194, n, { anchor: 'middle', bold: true, size: 12, fill: col }) + text(x, 216, t, { anchor: 'middle', size: 11 }); });
  b += box(40, 250, 820, 166, C.card, C.line, 8);
  LAPTOP.forEach((r, i) => { b += text(60, 274 + i * 20, fmtRoute(r) + (r.proto ? ` proto ${r.proto}` : ''), { mono: true, size: 12 }); });
  b += text(560, 274, '172.20.5.5 → tun0 (/12 > /0)', { size: 12, bold: true, fill: C.vio });
  b += text(560, 296, '172.17.0.2 → docker0 (/16 > /12)', { size: 12, bold: true, fill: C.host });
  b += text(560, 318, '8.8.8.8 → wlan0 (tylko default)', { size: 12, bold: true, fill: C.net });
  b += text(560, 340, '172.17.5.5 w firmie → docker0 ✗', { size: 12, bold: true, fill: C.no });
  files['ns03-07-laptop.svg'] = svg(W, H, 'Laptop z interfejsami wlan0 (192.168.1.20, trasa domyślna przez 192.168.1.1), docker0 i bridge compose (172.17.0.0/16, 172.18.0.0/16) oraz tun0 VPN (10.0.0.0/8 i 172.16.0.0/12 przez 10.8.0.1). Adres 172.20.5.5 idzie do VPN, 172.17.0.2 do Dockera, 8.8.8.8 trasą domyślną, a serwer firmy 172.17.5.5 trafia błędnie do docker0.', b);
}

// 3. Konflikt VPN vs Docker
{
  const W = 900, H = 380, lo = 0, X = (o) => 60 + (o / 256) * 780;
  let b = text(W / 2, 30, 'Debug Lab: VPN „zjada” trasę do sieci Dockera 172.18.0.0/16', { size: 18, bold: true, anchor: 'middle' });
  b += box(X(0), 70, X(256) - X(0), 46, C.hostFill, C.host, 4) + text(X(128), 98, 'docker compose: 172.18.0.0/16 dev br-3f1c', { anchor: 'middle', bold: true, fill: C.host });
  b += box(X(0), 130, X(128) - X(0), 46, C.vioFill, C.vio, 4) + text(X(64), 158, 'VPN: 172.18.0.0/17 via 10.8.0.1 (tun0)', { anchor: 'middle', bold: true, fill: C.vio, size: 13 });
  b += text(X(0), 64, '172.18.0.0', { size: 11, fill: C.muted }) + text(X(128), 64, '172.18.128.0', { size: 11, fill: C.muted, anchor: 'middle' }) + text(X(256), 64, '172.18.255.255', { size: 11, fill: C.muted, anchor: 'end' });
  const r1 = lookup(VPN_CONFLICT, '172.18.0.5'), r2 = lookup(VPN_CONFLICT, '172.18.200.5');
  b += box(60, 196, 380, 70, C.noFill, C.no, 8) + text(250, 222, 'kontener 172.18.0.5 (np. postgres)', { anchor: 'middle', bold: true, size: 13 }) + text(250, 246, `→ ${fmtRoute(r1.route)}  ✗`, { anchor: 'middle', mono: true, size: 11, fill: C.no });
  b += box(460, 196, 380, 70, C.okFill, C.ok, 8) + text(650, 222, 'kontener 172.18.200.5', { anchor: 'middle', bold: true, size: 13 }) + text(650, 246, `→ ${fmtRoute(r2.route)}  ✓`, { anchor: 'middle', mono: true, size: 11, fill: C.ok });
  b += text(60, 296, 'Objaw: „po podłączeniu VPN aplikacja nie widzi bazy w Dockerze”, ale tylko dla części kontenerów.', { size: 13 });
  b += text(60, 320, 'Dowód: ip route get 172.18.0.5 → dev tun0. Przyczyna: /17 z VPN jest dłuższy niż /16 Dockera.', { size: 13, bold: true });
  b += text(60, 344, 'Naprawa: przenieś sieci Dockera (daemon.json default-address-pools / networks.ipam w compose) poza zakresy firmy;', { size: 13, fill: C.ok });
  b += text(60, 366, 'doraźnie: ip route add 172.18.0.0/17 dev br-3f1c metric 0 — prefiks równy trasie VPN, wygrywa niższa metryka (0 < 50).', { size: 13, fill: C.ok });
  files['ns03-07-vpn-conflict.svg'] = svg(W, H, 'Konflikt tras: sieć docker compose 172.18.0.0/16 i trasa VPN 172.18.0.0/17. Kontener 172.18.0.5 trafia do tunelu VPN, bo /17 jest dłuższy, a kontener 172.18.200.5 nadal do mostu Dockera. Dowód: ip route get; naprawa: przeniesienie sieci Dockera poza zakresy firmy.', b);
}

// 4. Proces decyzji
{
  const W = 900, H = 420;
  let b = text(W / 2, 30, 'Jak system wybiera trasę: reguły → najdłuższy prefiks → metryka', { size: 18, bold: true, anchor: 'middle' });
  const steps = [
    ['0. Policy routing (ip rule)', 'najpierw wybór TABELI: main, local, tabela VPN/fwmark (np. WireGuard, Kubernetes)', C.vio, C.vioFill],
    ['1. Longest prefix match', 'spośród tras pasujących do celu — najdłuższy prefiks (/32 > /24 > /16 > /0)', C.net, C.netFill],
    ['2. Metryka / dystans', 'przy równym prefiksie: najniższa metryka (Linux), metryka trasy + interfejsu (Windows)', C.host, C.hostFill],
    ['3. ECMP', 'kilka tras równych we wszystkim → podział ruchu po przepływach (hash 5-tuple)', C.ok, C.okFill],
    ['4. Następny skok', 'brama (via) → ARP o bramę; brak via (trasa connected) → ARP o sam cel', C.ink, C.card],
  ];
  steps.forEach(([h, t, col, fill], i) => {
    const y = 52 + i * 62;
    b += box(40, y, 820, 52, fill, col, 8) + text(60, y + 22, h, { bold: true, fill: col, size: 14 }) + text(60, y + 42, t, { size: 12 });
  });
  b += text(40, 376, 'Linux:  ip route get 172.18.0.5   ·   ip rule   ·   ip route show table all', { mono: true, size: 12 });
  b += text(40, 400, 'Windows:  route print  ·  Get-NetRoute -DestinationPrefix 0.0.0.0/0  ·  Find-NetRoute -RemoteIPAddress 172.18.0.5', { mono: true, size: 12 });
  files['ns03-07-decision.svg'] = svg(W, H, 'Kolejność decyzji o trasie: najpierw reguły policy routing wybierają tabelę, potem najdłuższy pasujący prefiks, przy równym prefiksie najniższa metryka, przy pełnej równości ECMP, na końcu wybór następnego skoku i ARP. Polecenia Linux: ip route get, ip rule; Windows: route print, Get-NetRoute, Find-NetRoute.', b);
}

writeSvgs(process.argv[2], files);
