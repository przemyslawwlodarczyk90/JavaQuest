// Ilustracje do lekcji _ns_02/12_WirelessArchitecturesAndControllers.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_12_wlan_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { beaconAirtimePct } = require('./ns02_12_wlan_model.cjs');
const files = {};

// 1. Trzy architektury
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Trzy architektury Wi-Fi: autonomiczna, z kontrolerem, z chmury', { size: 18, bold: true, anchor: 'middle' });
  const cols = [
    ['Autonomiczne AP', C.host, C.hostFill, ['każdy AP konfigurowany osobno', 'brak wspólnego roamingu/RRM', 'dom, mała firma, 1–3 AP'], 'admin → AP1, AP2, AP3'],
    ['Kontroler WLC (lightweight)', C.net, C.netFill, ['AP bez własnej konfiguracji', 'CAPWAP do kontrolera', 'RRM, roaming, WIPS, polityki'], 'WLC ⇄ CAPWAP ⇄ AP'],
    ['Zarządzanie z chmury', C.ok, C.okFill, ['AP pobiera konfigurację z chmury', 'ruch danych zostaje lokalnie', 'oddziały, wiele lokalizacji'], 'chmura ⇢ HTTPS ⇢ AP'],
  ];
  cols.forEach(([t, col, fill, items, flow], i) => {
    const x = 30 + i * 285;
    b += box(x, 56, 270, 300, fill, col, 10) + text(x + 135, 84, t, { anchor: 'middle', bold: true, fill: col, size: 14 });
    // mini diagram
    const cx = x + 135;
    if (i === 0) { [0, 1, 2].forEach((k) => { b += box(x + 20 + k * 82, 160, 70, 34, C.card, col, 6) + text(x + 55 + k * 82, 182, `AP${k + 1}`, { anchor: 'middle', size: 12, bold: true }) + line(cx, 128, x + 55 + k * 82, 158, { color: col, w: 1.5, arrow: true }); }); b += box(cx - 40, 100, 80, 28, C.card, C.ink, 6) + text(cx, 119, 'admin', { anchor: 'middle', size: 12 }); }
    if (i === 1) { b += box(cx - 50, 100, 100, 30, C.card, C.ink, 6) + text(cx, 120, 'WLC', { anchor: 'middle', size: 13, bold: true }); [0, 1, 2].forEach((k) => { b += box(x + 20 + k * 82, 170, 70, 34, C.card, col, 6) + text(x + 55 + k * 82, 192, `AP${k + 1}`, { anchor: 'middle', size: 12, bold: true }) + line(cx, 132, x + 55 + k * 82, 168, { color: col, w: 2.5 }); }); b += text(cx, 155, 'CAPWAP', { anchor: 'middle', size: 10, fill: col, bold: true }); }
    if (i === 2) { b += `<ellipse cx="${cx}" cy="115" rx="60" ry="20" fill="${C.card}" stroke="${C.ink}" stroke-width="1.5"/>` + text(cx, 120, 'chmura', { anchor: 'middle', size: 12, bold: true }); [0, 1, 2].forEach((k) => { b += box(x + 20 + k * 82, 170, 70, 34, C.card, col, 6) + text(x + 55 + k * 82, 192, `AP${k + 1}`, { anchor: 'middle', size: 12, bold: true }) + line(cx, 136, x + 55 + k * 82, 168, { color: col, w: 1.5, dash: true }); }); b += text(cx + 70, 152, 'tylko zarządzanie', { anchor: 'middle', size: 10, fill: col, bold: true }); }
    items.forEach((it, k) => { b += text(x + 16, 240 + k * 26, '• ' + it, { size: 12 }); });
    b += text(x + 135, 336, flow, { anchor: 'middle', size: 11, mono: true, fill: col });
  });
  b += text(W / 2, 384, 'Przykłady: Cisco Catalyst 9800 (WLC), Aruba, Juniper Mist i Cisco Meraki (chmura), UniFi (kontroler programowy).', { anchor: 'middle', size: 12 });
  files['ns02-12-architectures.svg'] = svg(W, H, 'Trzy panele: autonomiczne AP konfigurowane osobno przez administratora (dom, mała firma); lightweight AP połączone tunelami CAPWAP z kontrolerem WLC, który zapewnia RRM, roaming, WIPS i polityki; AP zarządzane z chmury przez HTTPS, z ruchem danych pozostającym lokalnie (wiele lokalizacji).', b);
}

// 2. Local mode vs FlexConnect
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Gdzie trafia ruch klienta: tryb local (centralny) vs FlexConnect (lokalny)', { size: 17, bold: true, anchor: 'middle' });
  const panel = (ox, title, central) => {
    let s = box(ox, 56, 410, 280, C.card, central ? C.net : C.ok, 10) + text(ox + 205, 82, title, { anchor: 'middle', bold: true, fill: central ? C.net : C.ok, size: 14 });
    s += box(ox + 20, 250, 90, 36, C.hostFill, C.host, 6) + text(ox + 65, 273, 'klient', { anchor: 'middle', size: 12, bold: true });
    s += box(ox + 140, 250, 70, 36, C.card, C.ink, 6) + text(ox + 175, 273, 'AP', { anchor: 'middle', size: 12, bold: true });
    s += box(ox + 240, 250, 150, 36, C.netFill, C.net, 6) + text(ox + 315, 273, 'przełącznik oddziału', { anchor: 'middle', size: 11, bold: true });
    s += box(ox + 260, 110, 130, 40, C.vioFill, C.vio, 6) + text(ox + 325, 135, 'WLC (centrala)', { anchor: 'middle', size: 12, bold: true, fill: C.vio });
    s += line(ox + 110, 268, ox + 138, 268, { color: C.host, w: 2 });
    if (central) {
      s += line(ox + 175, 248, ox + 290, 152, { color: C.net, w: 2.5, arrow: true }) + text(ox + 170, 190, 'CAPWAP: sterowanie + dane', { size: 11, bold: true, fill: C.net });
      s += text(ox + 205, 316, 'cały ruch przez kontroler (WAN!)', { anchor: 'middle', size: 12, bold: true, fill: C.no });
    } else {
      s += line(ox + 175, 248, ox + 290, 152, { color: C.vio, w: 1.5, dash: true }) + text(ox + 150, 190, 'CAPWAP: tylko sterowanie', { size: 11, bold: true, fill: C.vio });
      s += line(ox + 212, 268, ox + 238, 268, { color: C.ok, w: 2.5, arrow: true });
      s += text(ox + 205, 316, 'dane wychodzą lokalnie do VLAN-u oddziału', { anchor: 'middle', size: 12, bold: true, fill: C.ok });
    }
    return s;
  };
  b += panel(30, 'Local mode (central switching)', true) + panel(460, 'FlexConnect (local switching)', false);
  b += text(W / 2, 362, 'CAPWAP: UDP 5246 (sterowanie, DTLS) i UDP 5247 (dane). Przy zerwaniu WAN FlexConnect może dalej obsługiwać klientów.', { anchor: 'middle', size: 12, bold: true });
  b += text(W / 2, 384, 'Kampus z kontrolerem w serwerowni → local; oddziały za WAN → FlexConnect lub AP zarządzane z chmury.', { anchor: 'middle', size: 12 });
  files['ns02-12-flexconnect.svg'] = svg(W, H, 'Dwa panele: w trybie local AP przesyła tunelem CAPWAP zarówno sterowanie, jak i cały ruch klientów do kontrolera WLC w centrali, więc ruch idzie przez WAN; w trybie FlexConnect CAPWAP przenosi tylko sterowanie, a dane klienta trafiają bezpośrednio do lokalnego przełącznika oddziału. CAPWAP używa UDP 5246 i 5247.', b);
}

// 3. Mapowanie SSID -> VLAN
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Zadanie z planu: trzy SSID, trzy VLAN-y, trzy polityki', { size: 18, bold: true, anchor: 'middle' });
  const ss = [
    ['CORP', 'WPA3-Enterprise · EAP-TLS', 'VLAN 10 · 10.10.0.0/22', 'dostęp do aplikacji wewnętrznych, 802.11r/k/v', C.net, C.netFill],
    ['GUEST', 'OWE / portal · izolacja klientów', 'VLAN 60 · 10.60.0.0/22', 'tylko Internet, limit 10 Mb/s, krótka dzierżawa', C.host, C.hostFill],
    ['IOT', 'WPA2/3-Personal · osobne PSK', 'VLAN 50 · 10.50.0.0/24', 'tylko do brokera MQTT i NTP, brak Internetu', C.ok, C.okFill],
  ];
  ss.forEach(([n, sec, vlan, pol, col, fill], i) => {
    const y = 60 + i * 110;
    b += box(40, y, 160, 90, fill, col, 8) + text(120, y + 40, `SSID ${n}`, { anchor: 'middle', bold: true, fill: col, size: 15 }) + text(120, y + 62, '(wszystkie AP)', { anchor: 'middle', size: 10, fill: C.muted });
    b += line(202, y + 45, 248, y + 45, { arrow: true, w: 2, color: col });
    b += box(250, y, 270, 90, C.card, col, 8) + text(266, y + 26, sec, { size: 12, bold: true }) + text(266, y + 52, vlan, { size: 12, mono: true }) + text(266, y + 76, 'trunk AP ↔ przełącznik', { size: 10, fill: C.muted });
    b += line(522, y + 45, 568, y + 45, { arrow: true, w: 2, color: col });
    b += box(570, y, 290, 90, fill, col, 8) + text(586, y + 30, 'polityka na firewallu/WLC:', { size: 11, fill: C.muted }) + text(586, y + 54, pol, { size: 11, bold: true });
  });
  b += text(W / 2, 404, 'Zasada: każdy SSID kosztuje czas antenowy (beacony) — 3–4 SSID to rozsądne maksimum; rozdzielaj ruch VLAN-ami i rolami z 802.1X.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-12-ssid-vlan.svg'] = svg(W, H, 'Projekt mapowania: SSID CORP z WPA3-Enterprise i EAP-TLS do VLAN 10 z dostępem do aplikacji wewnętrznych; SSID GUEST z OWE lub portalem i izolacją klientów do VLAN 60 tylko z dostępem do Internetu i limitem; SSID IOT z osobnym PSK do VLAN 50 z dostępem tylko do brokera MQTT i NTP. Każdy SSID kosztuje czas antenowy, więc 3–4 SSID to rozsądne maksimum.', b);
}

// 4. Narzut beaconow
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Każdy SSID to beacon co 102,4 ms z każdego AP — przy niskiej prędkości to dużo', { size: 16, bold: true, anchor: 'middle' });
  const cases = [[1, 1], [3, 1], [5, 1], [3, 3], [5, 3], [8, 4]];
  const x0 = 220, maxW = 600;
  cases.forEach(([s, a], i) => {
    const y = 64 + i * 42;
    const p1 = beaconAirtimePct(s, a, 350, 1), p24 = beaconAirtimePct(s, a, 350, 24);
    b += text(40, y + 22, `${s} SSID × ${a} AP w zasięgu`, { size: 12, bold: true });
    b += box(x0, y + 4, Math.min(maxW, (p1 / 100) * maxW), 16, C.noFill, C.no, 2) + text(x0 + Math.min(maxW, (p1 / 100) * maxW) + 6, y + 17, `${p1}% (1 Mb/s)`, { size: 11, fill: C.no, bold: true });
    b += box(x0, y + 22, Math.max(2, (p24 / 100) * maxW), 12, C.okFill, C.ok, 2) + text(x0 + Math.max(2, (p24 / 100) * maxW) + 6, y + 33, `${p24}% (24 Mb/s)`, { size: 10, fill: C.ok });
  });
  b += box(40, 318, 820, 50, C.card, C.line, 8);
  b += text(60, 340, 'Model: beacon 350 B, ~9,77 beaconu/s na SSID na AP, + 50 µs narzutu. Wyłączenie najniższych prędkości', { size: 12 });
  b += text(60, 360, 'i ograniczenie liczby SSID zwracają czas antenowy klientom. Powyżej 100% kanał jest nasycony samymi beaconami.', { size: 12, bold: true });
  files['ns02-12-beacons.svg'] = svg(W, H, 'Wykres słupkowy narzutu beaconów na czas antenowy dla różnych liczb SSID i słyszalnych AP: przy prędkości podstawowej 1 Mb/s narzut rośnie od 2,8% dla jednego SSID i jednego AP do 89% dla 8 SSID i 4 AP, przy 24 Mb/s pozostaje rzędu pojedynczych procent. Wniosek: ograniczać liczbę SSID i wyłączać najniższe prędkości.', b);
}

writeSvgs(process.argv[2], files);
