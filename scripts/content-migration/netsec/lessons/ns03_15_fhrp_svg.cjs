// Ilustracje do lekcji _ns_03/15_FirstHopRedundancyHsrpVrrp.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_15_fhrp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { vrrpFailover, vrrpMac, hsrp1Mac, hsrp2Mac } = require('./ns03_15_fhrp_model.cjs');
const files = {};

// 1. Problem i wirtualny adres
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Jedna brama = pojedynczy punkt awarii. FHRP: dwa routery, jeden wirtualny adres', { size: 16, bold: true, anchor: 'middle' });
  // lewa
  b += box(40, 56, 390, 320, C.card, C.no, 10) + text(235, 80, 'Bez FHRP', { anchor: 'middle', bold: true, fill: C.no });
  b += box(165, 100, 140, 50, C.noFill, C.no, 8) + text(235, 122, 'R1 10.0.10.1', { anchor: 'middle', bold: true, size: 12 }) + text(235, 140, 'brama domyślna', { anchor: 'middle', size: 11 });
  b += text(320, 130, '✕ awaria', { bold: true, fill: C.no });
  b += line(235, 152, 235, 220) + box(120, 220, 230, 46, C.netFill, C.net, 8) + text(235, 248, 'hosty: default via 10.0.10.1', { anchor: 'middle', size: 12 });
  b += text(60, 300, 'R1 pada → wszystkie hosty bez bramy,', { size: 12 }) + text(60, 320, 'mimo że obok stoi sprawny R2.', { size: 12 }) + text(60, 344, 'Zmiana bramy na hostach = ręczna praca.', { size: 12, bold: true, fill: C.no });
  // prawa
  b += box(470, 56, 390, 320, C.card, C.ok, 10) + text(665, 80, 'VRRP / HSRP', { anchor: 'middle', bold: true, fill: C.ok });
  b += box(490, 100, 160, 56, C.okFill, C.ok, 8) + text(570, 122, 'R1 .2 — MASTER', { anchor: 'middle', bold: true, size: 12 }) + text(570, 142, 'priorytet 110', { anchor: 'middle', size: 11 });
  b += box(680, 100, 160, 56, C.card, C.line, 8) + text(760, 122, 'R2 .3 — BACKUP', { anchor: 'middle', bold: true, size: 12 }) + text(760, 142, 'priorytet 100', { anchor: 'middle', size: 11 });
  b += box(560, 180, 210, 46, C.vioFill, C.vio, 8) + text(665, 200, 'wirtualny 10.0.10.1', { anchor: 'middle', bold: true, size: 12, fill: C.vio }) + text(665, 218, `MAC ${vrrpMac(10)}`, { anchor: 'middle', size: 11, mono: true });
  b += line(570, 158, 620, 180, { color: C.ok, w: 2 }) + line(760, 158, 710, 180, { color: C.line, dash: true });
  b += line(665, 228, 665, 252) + box(550, 252, 230, 40, C.netFill, C.net, 8) + text(665, 277, 'hosty: default via 10.0.10.1', { anchor: 'middle', size: 12 });
  b += text(490, 316, 'Master odpowiada na ARP o 10.0.10.1 wirtualnym MAC.', { size: 12 }) + text(490, 336, 'Po awarii R2 przejmuje IP i MAC, wysyła GARP —', { size: 12 }) + text(490, 356, 'hosty niczego nie zmieniają.', { size: 12, bold: true, fill: C.ok });
  files['ns03-15-vip.svg'] = svg(W, H, 'Porównanie: bez FHRP awaria jedynej bramy R1 odcina hosty; z VRRP lub HSRP routery R1 (master, priorytet 110) i R2 (backup, priorytet 100) dzielą wirtualny adres 10.0.10.1 z wirtualnym MAC 00:00:5e:00:01:0a; po awarii R2 przejmuje adres i MAC oraz wysyła gratuitous ARP, hosty nie zmieniają konfiguracji.', b);
}

// 2. Os czasu przelaczenia
{
  const W = 900, H = 380;
  const f = vrrpFailover(1000, 100);
  let b = text(W / 2, 30, 'Ile trwa przełączenie VRRP i co widzi klient', { size: 18, bold: true, anchor: 'middle' });
  const X = (ms) => 60 + (ms / 5000) * 780;
  b += line(X(0), 200, X(5000), 200, { color: C.ink }) + [0, 1000, 2000, 3000, 4000, 5000].map((t) => text(X(t), 220, `${t / 1000} s`, { anchor: 'middle', size: 11 }) + line(X(t), 195, X(t), 205, { color: C.ink })).join('');
  [0, 1000].forEach((t) => { b += line(X(t), 120, X(t), 195, { color: C.ok }) + text(X(t) + 4, 116, 'advert', { size: 10, fill: C.ok }); });
  b += text(X(0), 100, 'ogłoszenia mastera (ostatnie w 1 s, potem awaria)', { size: 11, bold: true });
  const md = 1000 + f.masterDown;
  b += box(X(1000), 140, X(md) - X(1000), 40, C.noFill, C.no, 4) + text((X(1000) + X(md)) / 2, 165, `Master_Down_Interval = 3 × 1 s + skew ${String(f.skew).replace('.', ',')} ms = ${String(f.masterDown).replace('.', ',')} ms`, { anchor: 'middle', size: 11, bold: true });
  b += line(X(md), 120, X(md), 195, { color: C.vio, w: 3 }) + text(X(md) - 6, 116, 'backup → MASTER, GARP', { size: 11, bold: true, fill: C.vio, anchor: 'end' });
  b += box(40, 240, 820, 126, C.card, C.line, 8);
  b += text(60, 264, `VRRPv3 (RFC 5798): domyślnie advert co 1 s → przełączenie ok. ${(f.masterDown / 1000).toFixed(1).replace('.', ',')} s po ostatnim ogłoszeniu; advert 100 ms → ${String(vrrpFailover(100, 100).masterDown).replace('.', ',')} ms.`, { size: 12 });
  b += text(60, 286, 'HSRP: hello 3 s, hold 10 s → do 10 s (można skrócić do milisekund). Wykrycie awarii łącza + BFD/tracking — szybciej.', { size: 12 });
  b += text(60, 312, 'Klient Java: istniejące połączenia TCP przez bramę zwykle przeżyją (retransmisje), jeśli przerwa < timeoutu;', { size: 12 });
  b += text(60, 334, 'połączenia przez reverse proxy na VIP (keepalived) zostaną ZERWANE — stan TCP był na starym węźle → potrzebny retry.', { size: 12, bold: true, fill: C.no });
  b += text(60, 356, 'Bez GARP hosty trzymają stary MAC w cache ARP do wygaśnięcia — przerwa rośnie do dziesiątek sekund.', { size: 12 });
  files['ns03-15-failover.svg'] = svg(W, H, `Oś czasu VRRP: master wysyła ogłoszenia co 1 sekundę; po ich ustaniu backup czeka Master_Down_Interval równy 3 sekundy plus skew ${f.skew} ms i staje się masterem, wysyłając gratuitous ARP. Przy ogłoszeniach co 100 ms przełączenie trwa ok. 361 ms. HSRP domyślnie do 10 s. Połączenia do VIP reverse proxy są zrywane.`, b);
}

// 3. Porownanie
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'HSRP vs VRRP vs GLBP', { size: 18, bold: true, anchor: 'middle' });
  const cols = [50, 260, 480, 690];
  ['', 'HSRP', 'VRRP', 'GLBP'].forEach((h, i) => { b += text(cols[i], 64, h, { bold: true, size: 14, fill: [C.ink, C.net, C.ok, C.vio][i] }); });
  const rows = [
    ['standard', 'Cisco (RFC 2281, v2)', 'IETF (RFC 5798, v3)', 'Cisco'],
    ['role', 'active / standby', 'master / backup', 'AVG + do 4 AVF'],
    ['wirtualny MAC', `${hsrp1Mac(10)} (v1)`, vrrpMac(10), '00:07:b4:0X:XX:YY'],
    ['', `${hsrp2Mac(10)} (v2)`, '00:00:5e:00:02:XX (IPv6)', ''],
    ['multicast', '224.0.0.2 / .102 (v2)', '224.0.0.18, ff02::12', '224.0.0.102'],
    ['timery domyślne', 'hello 3 s / hold 10 s', 'advert 1 s / ~3,6 s', 'hello 3 s / hold 10 s'],
    ['preempt domyślnie', 'wyłączony', 'włączony', 'wyłączony (AVG)'],
    ['adres = adres routera', 'nie', 'tak (owner, prio 255)', 'nie'],
    ['równoważenie ruchu', 'nie (kilka grup)', 'nie (kilka grup)', 'tak — różne MAC w ARP'],
  ];
  rows.forEach((r, i) => { const y = 92 + i * 30; if (r[0]) b += line(40, y - 20, 860, y - 20, { color: C.line, w: 1 }); r.forEach((c, j) => { b += text(cols[j], y, c, { size: j ? 12 : 12, bold: j === 0, mono: c.includes(':') && j > 0 }); }); });
  b += text(40, 376, 'Linux: keepalived (VRRP) — standard przed parą HAProxy/Nginx, bramami VPN i bazami (wirtualny IP).', { size: 12, bold: true, fill: C.ok });
  files['ns03-15-compare.svg'] = svg(W, H, 'Tabela porównawcza: HSRP (Cisco, active/standby, MAC 00:00:0c:07:ac:XX, hello 3 s hold 10 s, preempt wyłączony), VRRP (IETF RFC 5798, master/backup, MAC 00:00:5e:00:01:XX, advert 1 s, preempt włączony, adres może należeć do routera), GLBP (Cisco, AVG i do czterech AVF, równoważenie ruchu przez różne MAC). Na Linuksie keepalived.', b);
}

// 4. keepalived przed reverse proxy
{
  const W = 900, H = 420;
  let b = text(W / 2, 30, 'Failure Lab: para Nginx z keepalived (VRRP) — wirtualny IP przed aplikacją', { size: 17, bold: true, anchor: 'middle' });
  b += box(330, 56, 240, 50, C.netFill, C.net, 8) + text(450, 78, 'klient Java', { anchor: 'middle', bold: true }) + text(450, 96, 'GET http://10.0.10.100/', { anchor: 'middle', size: 11, mono: true });
  b += line(450, 108, 450, 140, { arrow: true });
  b += box(300, 140, 300, 40, C.vioFill, C.vio, 8) + text(450, 165, 'VIP 10.0.10.100 (VRRP VRID 51)', { anchor: 'middle', bold: true, fill: C.vio, size: 12 });
  b += box(140, 210, 260, 70, C.okFill, C.ok, 8) + text(270, 236, 'lb1: Nginx + keepalived', { anchor: 'middle', bold: true }) + text(270, 256, 'MASTER, priority 110', { anchor: 'middle', size: 11 }) + text(270, 272, 'track_script: nginx żyje?', { anchor: 'middle', size: 11 });
  b += box(500, 210, 260, 70, C.card, C.line, 8) + text(630, 236, 'lb2: Nginx + keepalived', { anchor: 'middle', bold: true }) + text(630, 256, 'BACKUP, priority 100', { anchor: 'middle', size: 11 }) + text(630, 272, 'przejmuje VIP po awarii', { anchor: 'middle', size: 11 });
  b += line(380, 182, 300, 208, { color: C.ok, w: 2 }) + line(520, 182, 600, 208, { color: C.line, dash: true });
  b += box(300, 300, 300, 40, C.hostFill, C.host, 8) + text(450, 325, 'aplikacje Spring Boot (app1, app2)', { anchor: 'middle', bold: true, size: 12 });
  b += line(270, 282, 400, 300) + line(630, 282, 500, 300);
  b += box(40, 352, 820, 56, C.card, C.line, 8);
  b += text(56, 374, 'Test: klient Java wysyła żądanie co 100 ms i loguje czas/błąd; docker stop lb1 (lub systemctl stop keepalived).', { size: 12 });
  b += text(56, 396, 'Wynik: przerwa ≈ Master_Down_Interval + nowe połączenie TCP. W chmurze VRRP zwykle nie działa → load balancer chmury.', { size: 12, bold: true, fill: C.no });
  files['ns03-15-keepalived.svg'] = svg(W, H, 'Topologia labu: klient Java wysyła żądania na wirtualny adres 10.0.10.100 utrzymywany przez keepalived (VRRP) na dwóch węzłach Nginx — lb1 master z priorytetem 110 i skryptem sprawdzającym Nginx, lb2 backup z priorytetem 100 — przed dwoma aplikacjami Spring Boot. Po zatrzymaniu lb1 lb2 przejmuje VIP; w chmurze VRRP często nie działa i używa się load balancera chmury.', b);
}

writeSvgs(process.argv[2], files);
