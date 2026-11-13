// Ilustracje do lekcji _ns_02/06_CiscoIosCliEssentials.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_06_ios_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Hierarchia trybow IOS
{
  const W = 900, H = 440;
  let b = text(W / 2, 32, 'Tryby Cisco IOS: znak zachęty mówi, gdzie jesteś', { size: 19, bold: true, anchor: 'middle' });
  const lvl = [
    [60, 'User EXEC', 'SW1>', 'podgląd: ping, show (ograniczone)', C.card, C.ink],
    [150, 'Privileged EXEC', 'SW1#', 'pełne show, debug, copy, reload', C.netFill, C.net],
    [240, 'Global configuration', 'SW1(config)#', 'hostname, enable secret, vlan, ip route', C.hostFill, C.host],
  ];
  lvl.forEach(([y, n, p, d, fill, col]) => {
    b += box(250, y, 400, 64, fill, col, 10) + text(270, y + 26, n, { bold: true, fill: col, size: 15 }) + text(630, y + 26, p, { mono: true, bold: true, anchor: 'end', size: 15 }) + text(270, y + 50, d, { size: 12, fill: C.muted });
  });
  b += line(450, 124, 450, 150, { arrow: true, w: 2 }) + text(462, 142, 'enable', { mono: true, size: 12, fill: C.ok, bold: true });
  b += line(450, 214, 450, 240, { arrow: true, w: 2 }) + text(462, 232, 'configure terminal', { mono: true, size: 12, fill: C.ok, bold: true });
  b += text(240, 142, 'disable', { mono: true, size: 12, fill: C.no, bold: true, anchor: 'end' });
  b += text(240, 232, 'end / Ctrl+Z', { mono: true, size: 12, fill: C.no, bold: true, anchor: 'end' });
  const subs = [['interface Gi1/0/1', 'SW1(config-if)#'], ['line vty 0 15', 'SW1(config-line)#'], ['vlan 10', 'SW1(config-vlan)#'], ['router ospf 1', 'R1(config-router)#']];
  subs.forEach(([cmd, p], i) => {
    const x = 40 + i * 210;
    b += line(450, 304, x + 100, 344, { color: C.vio });
    b += box(x, 346, 200, 56, C.vioFill, C.vio, 8) + text(x + 100, 368, cmd, { mono: true, size: 12, anchor: 'middle', bold: true }) + text(x + 100, 390, p, { mono: true, size: 12, anchor: 'middle', fill: C.vio });
  });
  b += text(W / 2, 426, 'exit — poziom w górę · end — prosto do SW1# · do <polecenie EXEC> — show z trybu konfiguracji', { anchor: 'middle', size: 12, bold: true });
  files['ns02-06-modes.svg'] = svg(W, H, 'Hierarchia trybów Cisco IOS: User EXEC ze znakiem SW1>, polecenie enable przechodzi do Privileged EXEC SW1#, configure terminal do trybu konfiguracji globalnej SW1(config)#, a z niego do podtrybów interfejsu, linii vty, VLAN-u i routera; exit cofa o poziom, end wraca do trybu uprzywilejowanego.', b);
}

// 2. Running vs startup config i pamieci
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Gdzie żyje konfiguracja: running-config vs startup-config', { size: 18, bold: true, anchor: 'middle' });
  const mem = [
    [40, 'Flash', 'obraz systemu IOS (.bin)', C.card, C.ink],
    [250, 'RAM', 'running-config — działa TERAZ', C.okFill, C.ok],
    [460, 'NVRAM', 'startup-config — po restarcie', C.netFill, C.net],
    [670, 'TFTP/SCP/Git', 'kopie zapasowe, wersjonowanie', C.vioFill, C.vio],
  ];
  mem.forEach(([x, n, d, fill, col]) => { b += box(x, 70, 190, 90, fill, col, 10) + text(x + 95, 104, n, { anchor: 'middle', bold: true, size: 16, fill: col }) + text(x + 95, 132, d, { anchor: 'middle', size: 11 }); });
  b += line(250, 210, 440, 210, { arrow: true, w: 3, color: C.ok }) ;
  b += text(345, 200, 'copy running-config startup-config', { anchor: 'middle', mono: true, size: 11, bold: true });
  b += text(345, 228, '(write memory)', { anchor: 'middle', mono: true, size: 11, fill: C.muted });
  b += line(555, 250, 365, 250, { arrow: true, w: 2, color: C.net });
  b += text(460, 270, 'przy starcie: startup → running', { anchor: 'middle', size: 11, fill: C.net });
  b += line(650, 210, 740, 170, { arrow: true, w: 2, color: C.vio }) + text(700, 222, 'copy run scp://…', { mono: true, size: 11, fill: C.vio });
  b += box(40, 296, 820, 80, C.noFill, C.no, 8);
  b += text(60, 322, 'Zmiany w trybie konfiguracji działają natychmiast, ale tylko w RAM.', { bold: true, fill: C.no });
  b += text(60, 346, 'Brak zapisu → utrata zmian po restarcie. Zły zapis → błąd przetrwa restart. Zanim zapiszesz: sprawdź.', { size: 12 });
  b += text(60, 366, 'Bezpiecznik: reload in 10 przed ryzykowną zmianą zdalną (cofnie do startup-config, jeśli stracisz dostęp).', { size: 12 });
  files['ns02-06-config.svg'] = svg(W, H, 'Cztery miejsca przechowywania: Flash z obrazem IOS, RAM z running-config działającym teraz, NVRAM ze startup-config ładowanym po restarcie oraz zewnętrzne kopie przez TFTP, SCP lub Git; polecenie copy running-config startup-config zapisuje zmiany; ostrzeżenie, że zmiany działają od razu, ale bez zapisu znikną po restarcie, i porada reload in 10.', b);
}

// 3. Lab Containerlab
{
  const W = 900, H = 380;
  let b = text(W / 2, 32, 'Guided Lab: dwa przełączniki w Containerlab', { size: 18, bold: true, anchor: 'middle' });
  b += box(30, 56, 840, 230, C.card, C.line, 12) + text(50, 80, 'host Linux z Dockerem + containerlab deploy -t lab.clab.yml', { mono: true, size: 12, fill: C.muted });
  const sw = (x, n) => box(x, 110, 170, 70, C.netFill, C.net, 8) + text(x + 85, 140, n, { anchor: 'middle', bold: true, fill: C.net, size: 15 }) + text(x + 85, 162, 'cEOS / IOL / vrnetlab', { anchor: 'middle', size: 11 });
  b += sw(170, 'sw1') + sw(560, 'sw2');
  b += line(340, 145, 560, 145, { color: C.vio, w: 5 }) + text(450, 136, 'eth1 ↔ eth1: trunk VLAN 10,20', { anchor: 'middle', size: 12, bold: true, fill: C.vio });
  const pcs = [[120, 'pc1 VLAN 10', C.host, 255], [330, 'pc2 VLAN 20', C.ok, 255], [520, 'pc3 VLAN 10', C.host, 645], [730, 'pc4 VLAN 20', C.ok, 645]];
  pcs.forEach(([x, n, col, sx]) => { b += line(sx, 182, x, 228, { color: col, w: 2 }) + box(x - 60, 230, 120, 36, C.card, col, 6) + text(x, 253, n, { anchor: 'middle', size: 12, bold: true, fill: col }); });
  b += box(30, 300, 840, 64, C.okFill, C.ok, 8);
  b += text(50, 324, 'Zadania: SSH zamiast Telnetu · enable secret · VLAN-y i trunk · banner · zapis i kopia konfiguracji', { size: 12, bold: true });
  b += text(50, 346, 'Dostęp: ssh admin@clab-lab-sw1 (sieć zarządzania Containerlab 172.20.20.0/24) · konfiguracja jako kod w Git', { size: 12 });
  files['ns02-06-lab.svg'] = svg(W, H, 'Topologia laboratorium Containerlab na hoście z Dockerem: dwa przełączniki sw1 i sw2 połączone trunkiem z VLAN 10 i 20 oraz cztery hosty pc1 do pc4 w VLAN 10 i 20; zadania: SSH, enable secret, VLAN-y, banner, zapis i kopia konfiguracji.', b);
}

// 4. Porownanie dostawcow
{
  const W = 900, H = 420;
  let b = text(W / 2, 32, 'Te same czynności u trzech dostawców', { size: 18, bold: true, anchor: 'middle' });
  const cols = [['czynność', 190], ['Cisco IOS / IOS XE', 210], ['Arista EOS', 190], ['Juniper Junos', 250]];
  let x = 30; const xs = [];
  cols.forEach(([n, w]) => { xs.push([x, w]); b += box(x, 50, w - 4, 34, C.netFill, C.net, 4) + text(x + 8, 72, n, { bold: true, size: 13, fill: C.net }); x += w; });
  const rows = [
    ['wejście w konfigurację', 'configure terminal', 'configure', 'configure'],
    ['interfejsy w skrócie', 'show ip int brief', 'show ip int brief', 'show interfaces terse'],
    ['tablica MAC', 'show mac address-table', 'show mac address-table', 'show ethernet-switching table'],
    ['zatwierdzenie zmian', '(od razu w RAM)', '(od razu) / sesje', 'commit'],
    ['zapis na stałe', 'copy run start', 'write memory', '(commit zapisuje)'],
    ['wycofanie zmian', 'configure replace', 'rollback / sesje', 'rollback 1'],
    ['bezpieczna zmiana', 'reload in 10', 'commit timer', 'commit confirmed 5'],
    ['filtr wyjścia', '| include', '| include / grep', '| match'],
  ];
  rows.forEach((r, i) => {
    const y = 90 + i * 36;
    r.forEach((c, j) => { const [cx, w] = xs[j]; b += box(cx, y, w - 4, 32, i % 2 ? C.card : C.bg, C.line, 3) + text(cx + 8, y + 21, c, { size: 12, mono: j > 0, bold: j === 0 }); });
  });
  b += text(W / 2, 404, 'Junos i EOS (sesje) mają kandydacką konfigurację i commit — model bliższy transakcjom w bazie danych.', { anchor: 'middle', size: 12, bold: true });
  files['ns02-06-vendors.svg'] = svg(W, H, 'Tabela porównująca Cisco IOS, Arista EOS i Juniper Junos: wejście w konfigurację, podgląd interfejsów, tablica MAC, zatwierdzanie i zapis zmian, wycofanie, bezpieczna zmiana z automatycznym wycofaniem (reload in, commit timer, commit confirmed) i filtrowanie wyjścia.', b);
}

writeSvgs(process.argv[2], files);
