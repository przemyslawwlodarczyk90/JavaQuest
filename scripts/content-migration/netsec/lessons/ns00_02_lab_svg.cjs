// Ilustracje do lekcji _ns_00/02_LabEnvironmentSetup.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_02_lab_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Architektura stanowiska
{
  const W = 900, H = 440;
  let b = text(W / 2, 32, 'Stanowisko laboratoryjne: warstwy od Windows do kontenera', { size: 19, bold: true, anchor: 'middle' });
  b += box(20, 52, 860, 360, C.card, C.ink, 12) + text(36, 76, 'Windows 11 (host): przeglądarka, Wireshark, IntelliJ, Docker Desktop', { bold: true, size: 13 });
  b += box(40, 92, 520, 300, C.netFill, C.net, 10) + text(56, 114, 'WSL2 — lekka maszyna wirtualna z jądrem Linux', { bold: true, fill: C.net, size: 13 });
  b += text(56, 134, 'Ubuntu: bash, tcpdump, dig, curl, openssl, ip, mtr, nmap, JDK, Maven', { size: 12 });
  b += box(60, 150, 480, 226, C.okFill, C.ok, 10) + text(76, 172, 'Docker Engine (backend WSL2 Docker Desktop)', { bold: true, fill: C.ok, size: 13 });
  const nets = [['sieć edge', 80, C.host, C.hostFill, ['caddy']], ['sieć backend (internal)', 300, C.vio, C.vioFill, ['app', 'postgres']]];
  nets.forEach(([n, x, col, fill, items]) => {
    b += box(x, 190, 200, 170, fill, col, 8) + text(x + 100, 210, n, { anchor: 'middle', bold: true, fill: col, size: 12 });
    items.forEach((it, i) => { b += box(x + 30, 226 + i * 60, 140, 44, C.card, col, 6) + text(x + 100, 253 + i * 60, it, { anchor: 'middle', bold: true, size: 13 }); });
    if (x === 80) b += text(x + 100, 296, 'caddy należy też do backend', { anchor: 'middle', size: 11, fill: C.muted });
  });
  b += line(250, 248, 330, 248, { color: C.muted, w: 2 });
  b += box(580, 92, 280, 300, C.vioFill, C.vio, 10) + text(720, 114, 'Hypervisor (Hyper-V / VirtualBox)', { anchor: 'middle', bold: true, fill: C.vio, size: 13 });
  ['VM: kontroler domeny Windows', 'VM: stacja Windows + Sysmon', 'VM: Security Onion / Wazuh', 'VM: REMnux / FLARE (malware)'].forEach((t, i) => { b += box(600, 128 + i * 60, 240, 46, C.card, C.vio, 6) + text(720, 156 + i * 60, t, { anchor: 'middle', size: 12 }); });
  b += text(720, 384, 'od rozdziału 18 (Windows, AD, SIEM)', { anchor: 'middle', size: 11, fill: C.muted });
  b += text(W / 2, 432, 'Część I wymaga tylko WSL2 + Docker; część III dokłada maszyny wirtualne (16 GB RAM to minimum, 32 GB wygodnie).', { anchor: 'middle', size: 12, bold: true });
  files['ns00-02-lab-architecture.svg'] = svg(W, H + 10, 'Warstwy stanowiska: Windows 11 z narzędziami, w nim WSL2 z Ubuntu i Docker Engine z sieciami edge i backend, obok hypervisor z maszynami wirtualnymi do labów Windows, AD, SIEM i analizy malware', b);
}

// 2. lab-zero
{
  const W = 900, H = 360;
  let b = text(W / 2, 32, 'lab-zero: Caddy → Spring Boot → PostgreSQL', { size: 19, bold: true, anchor: 'middle' });
  b += box(30, 120, 170, 80, C.card, C.ink, 8) + text(115, 150, 'przeglądarka / curl', { anchor: 'middle', bold: true, size: 13 }) + text(115, 172, 'https://127.0.0.1:8443', { anchor: 'middle', size: 11, mono: true });
  b += box(260, 70, 190, 190, C.hostFill, C.host, 10) + text(355, 92, 'edge', { anchor: 'middle', bold: true, fill: C.host });
  b += box(285, 120, 140, 80, C.card, C.host, 8) + text(355, 150, 'caddy:2', { anchor: 'middle', bold: true }) + text(355, 172, 'TLS (tls internal)', { anchor: 'middle', size: 11 }) + text(355, 245, 'sieci: edge + backend', { anchor: 'middle', size: 11, fill: C.host, bold: true });
  b += box(500, 70, 370, 190, C.vioFill, C.vio, 10) + text(685, 92, 'backend (internal: true)', { anchor: 'middle', bold: true, fill: C.vio });
  b += box(520, 120, 150, 80, C.card, C.vio, 8) + text(595, 150, 'app :8080', { anchor: 'middle', bold: true }) + text(595, 172, 'Spring Boot', { anchor: 'middle', size: 11 });
  b += box(700, 120, 150, 80, C.card, C.vio, 8) + text(775, 150, 'postgres :5432', { anchor: 'middle', bold: true }) + text(775, 172, 'bez portu na hoście', { anchor: 'middle', size: 11 });
  b += line(200, 160, 283, 160, { color: C.ink, w: 2, arrow: true }) + line(425, 160, 518, 160, { color: C.ink, w: 2, arrow: true }) + line(670, 160, 698, 160, { color: C.ink, w: 2, arrow: true });
  b += text(240, 150, '127.0.0.1:8443', { anchor: 'middle', size: 10, mono: true, fill: C.muted });
  b += text(W / 2, 300, 'Jedyny port na hoście: 127.0.0.1:8443 (niedostępny z sieci LAN). Baza i aplikacja tylko w sieci wewnętrznej.', { anchor: 'middle', size: 13, bold: true });
  b += text(W / 2, 326, 'check.sh potwierdza: proxy odpowiada, /actuator/health = UP, baza nieosiągalna z hosta.', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns00-02-lab-zero.svg'] = svg(W, H, 'Topologia lab-zero: przeglądarka łączy się z Caddy na 127.0.0.1:8443 w sieci edge, Caddy przekazuje ruch do aplikacji Spring Boot w sieci wewnętrznej backend, aplikacja łączy się z PostgreSQL bez portu na hoście', b);
}

// 3. Anatomia pakietu labu
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Anatomia pakietu laboratorium', { size: 19, bold: true, anchor: 'middle' });
  const rows = [
    ['lab-zero/', '', C.ink],
    ['├── docker-compose.yml', 'usługi, sieci, wolumeny — środowisko w jednym pliku', C.net],
    ['├── scenario.json', 'cel, poziom, kroki, punktacja, mapowanie na egzaminy', C.vio],
    ['├── reset.sh', 'powrót do stanu początkowego (down -v, up -d, seed danych)', C.host],
    ['├── check.sh', 'automatyczne sprawdzenie: czy cel został osiągnięty', C.ok],
    ['├── tests/', 'testy szczegółowe (curl, nc, zapytania, reguły)', C.ok],
    ['├── expected/', 'wzorcowe wyniki do porównania (np. hash, liczba alertów)', C.muted],
    ['├── config/', 'Caddyfile, application.yml, reguły nftables …', C.net],
    ['└── README.md', 'instrukcja, wymagania, sprzątanie, uwagi o bezpieczeństwie', C.ink],
  ];
  rows.forEach(([f, d, col], i) => { const y = 70 + i * 36; b += text(60, y, f, { mono: true, size: 14, bold: true, fill: col }) + text(330, y, d, { size: 13 }); });
  b += text(W / 2, 390, 'Zasada: każdy lab da się uruchomić, sprawdzić i zresetować jednym poleceniem — bez ręcznego klikania.', { anchor: 'middle', size: 13, bold: true });
  files['ns00-02-lab-package.svg'] = svg(W, H + 10, 'Drzewo plików pakietu laboratorium: docker-compose.yml, scenario.json, reset.sh, check.sh, tests, expected, config i README z opisem roli każdego elementu', b);
}

// 4. Izolacja: 0.0.0.0 vs 127.0.0.1
{
  const W = 900, H = 400;
  let b = text(W / 2, 32, 'Publikowanie portów: kto może połączyć się z Twoim labem?', { size: 19, bold: true, anchor: 'middle' });
  const panel = (x, title, col, fill, bind, ok) => {
    let s = box(x, 56, 410, 300, fill, col, 10) + text(x + 205, 80, title, { anchor: 'middle', bold: true, fill: col, size: 14 });
    s += text(x + 205, 104, bind, { anchor: 'middle', mono: true, size: 13 });
    s += box(x + 140, 130, 130, 60, C.card, C.ink, 8) + text(x + 205, 158, 'Twój laptop', { anchor: 'middle', bold: true, size: 13 }) + text(x + 205, 176, 'lab :8443', { anchor: 'middle', size: 11, mono: true });
    s += box(x + 20, 250, 150, 56, C.card, C.ink, 8) + text(x + 95, 276, 'kolega w Wi-Fi', { anchor: 'middle', size: 12, bold: true }) + text(x + 95, 294, '192.168.1.37', { anchor: 'middle', size: 11, mono: true });
    s += box(x + 240, 250, 150, 56, C.card, C.ink, 8) + text(x + 315, 276, 'przeglądarka', { anchor: 'middle', size: 12, bold: true }) + text(x + 315, 294, 'na laptopie', { anchor: 'middle', size: 11 });
    s += ok ? line(x + 95, 248, x + 170, 192, { color: C.no, w: 2, arrow: true }) : line(x + 95, 248, x + 150, 206, { color: C.muted, w: 2, dash: true }) + text(x + 158, 212, '✕', { size: 22, bold: true, fill: C.no });
    s += line(x + 315, 248, x + 240, 192, { color: C.ok, w: 2, arrow: true });
    s += text(x + 205, 336, ok ? 'cała sieć Wi-Fi widzi lab (i jego podatności)' : 'tylko lokalny komputer', { anchor: 'middle', size: 12, bold: true, fill: ok ? C.no : C.ok });
    return s;
  };
  b += panel(30, 'ports: ["8443:443"]', C.no, C.noFill, 'nasłuch 0.0.0.0:8443', true);
  b += panel(460, 'ports: ["127.0.0.1:8443:443"]', C.ok, C.okFill, 'nasłuch 127.0.0.1:8443', false);
  b += text(W / 2, 384, 'Celowo podatne aplikacje z labów nigdy nie powinny być osiągalne z sieci — w hotelu, na uczelni czy w biurze.', { anchor: 'middle', size: 12, bold: true });
  files['ns00-02-isolation.svg'] = svg(W, H, 'Porównanie publikowania portu: 8443:443 nasłuchuje na wszystkich interfejsach, więc lab jest osiągalny dla innych osób w Wi-Fi; 127.0.0.1:8443:443 udostępnia lab tylko lokalnemu komputerowi', b);
}

writeSvgs(process.argv[2], files);
