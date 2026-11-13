// Ilustracje do lekcji _ns_00/03_EthicsScopeAndLaw.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_03_ethics_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Drzewo decyzji
{
  const W = 900, H = 500;
  let b = text(W / 2, 32, 'Czy wolno mi to zrobić? Drzewo decyzji przed każdym testem', { size: 19, bold: true, anchor: 'middle' });
  const q = (x, y, t1, t2) => `<polygon points="${x},${y - 34} ${x + 150},${y} ${x},${y + 34} ${x - 150},${y}" fill="${C.card}" stroke="${C.ink}" stroke-width="1.5"/>` + text(x, y - 4, t1, { anchor: 'middle', size: 12, bold: true }) + text(x, y + 14, t2, { anchor: 'middle', size: 12, bold: true });
  const stop = (x, y, t1, t2) => box(x - 120, y - 30, 240, 60, C.noFill, C.no, 8) + text(x, y - 6, t1, { anchor: 'middle', bold: true, fill: C.no, size: 13 }) + text(x, y + 14, t2, { anchor: 'middle', size: 11 });
  b += q(300, 90, 'Czy system jest mój', '(i tylko mój)?');
  b += line(450, 90, 610, 90, { color: C.ok, arrow: true }) + text(530, 82, 'TAK', { anchor: 'middle', size: 12, bold: true, fill: C.ok });
  b += box(620, 60, 250, 60, C.okFill, C.ok, 8) + text(745, 85, 'Własny, izolowany lab', { anchor: 'middle', bold: true, fill: C.ok, size: 13 }) + text(745, 104, 'ćwicz — z zachowaniem izolacji', { anchor: 'middle', size: 11 });
  b += line(300, 124, 300, 176, { color: C.no, arrow: true }) + text(312, 154, 'NIE', { size: 12, bold: true, fill: C.no });
  b += q(300, 214, 'Czy mam pisemną zgodę', 'właściciela systemu?');
  b += line(150, 214, 140, 214) + line(140, 214, 140, 300, { color: C.no, arrow: true }) + text(128, 260, 'NIE', { anchor: 'end', size: 12, bold: true, fill: C.no });
  b += stop(140, 332, 'STOP', 'nie testuj; możesz zgłosić obserwację (VDP)');
  b += line(300, 248, 300, 300, { color: C.ok, arrow: true }) + text(312, 278, 'TAK', { size: 12, bold: true, fill: C.ok });
  b += q(450, 338, 'Czy czynność mieści się', 'w zakresie i oknie czasu?');
  b += line(300, 300, 300, 338) + line(300, 338, 300, 338);
  b += line(600, 338, 700, 338, { color: C.no }) + line(700, 338, 700, 300, { color: C.no, arrow: true });
  b += stop(745, 270, 'STOP i zapytaj', 'rozszerzenie zakresu tylko pisemnie');
  b += text(640, 330, 'NIE', { size: 12, bold: true, fill: C.no });
  b += line(450, 372, 450, 410, { color: C.ok, arrow: true }) + text(462, 396, 'TAK', { size: 12, bold: true, fill: C.ok });
  b += box(300, 412, 300, 64, C.okFill, C.ok, 8) + text(450, 436, 'Działaj wg rules of engagement', { anchor: 'middle', bold: true, fill: C.ok, size: 13 }) + text(450, 456, 'loguj czynności, zgłaszaj krytyczne odkrycia', { anchor: 'middle', size: 11 });
  files['ns00-03-decision-tree.svg'] = svg(W, H, 'Drzewo decyzji: jeśli system jest wyłącznie mój — ćwiczę w izolowanym labie; jeśli nie, potrzebna jest pisemna zgoda właściciela, a bez niej stop; ze zgodą działam tylko w zakresie i oknie czasowym, w przeciwnym razie stop i pisemne rozszerzenie zakresu', b);
}

// 2. Mapa przepisow
{
  const W = 900, H = 470;
  let b = text(W / 2, 32, 'Kodeks karny: przestępstwa przeciwko ochronie informacji (rozdz. XXXIII)', { size: 17, bold: true, anchor: 'middle' });
  const rows = [
    ['art. 267', 'nieuprawniony dostęp do informacji i systemu (przełamanie zabezpieczeń, dostęp do systemu), podsłuch, ujawnienie', C.no],
    ['art. 268', 'niszczenie, uszkadzanie, zmiana lub utrudnianie dostępu do istotnej informacji', C.host],
    ['art. 268a', 'niszczenie, zmiana, utrudnianie dostępu do danych informatycznych; zakłócanie ich przetwarzania', C.host],
    ['art. 269', 'sabotaż danych o szczególnym znaczeniu (obronność, administracja, bezpieczeństwo komunikacji)', C.no],
    ['art. 269a', 'zakłócanie pracy systemu lub sieci (np. DoS) przez transmisję, zmianę lub usunięcie danych', C.no],
    ['art. 269b', 'wytwarzanie, pozyskiwanie, udostępnianie narzędzi i haseł przystosowanych do tych przestępstw', C.vio],
    ['art. 269c', 'niekaralność (kontratyp) działań wyłącznie w celu zabezpieczenia systemu — przy ścisłych warunkach', C.ok],
  ];
  rows.forEach(([a, d, col], i) => {
    const y = 56 + i * 52;
    b += box(30, y, 130, 42, C.card, col, 8) + text(95, y + 27, a, { anchor: 'middle', bold: true, fill: col, size: 14 });
    b += box(170, y, 700, 42, C.bg, C.line, 8) + text(182, y + 27, d, { size: 12 });
  });
  b += text(W / 2, 436, 'Uproszczony opis dydaktyczny — nie zastępuje tekstu ustawy ani porady prawnej. Zawsze sprawdzaj aktualny stan prawny.', { anchor: 'middle', size: 12, fill: C.muted });
  b += text(W / 2, 458, 'Do tego RODO (dane osobowe), Kodeks pracy (monitoring pracowników), KSC/NIS2 i umowy (NDA, regulaminy bug bounty).', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns00-03-law-map.svg'] = svg(W, H + 6, 'Tabela przepisów Kodeksu karnego od art. 267 do 269c z krótkim opisem: dostęp do informacji, niszczenie informacji i danych, sabotaż, zakłócanie systemów, narzędzia hakerskie oraz kontratyp działań w celu zabezpieczenia', b);
}

// 3. Odpowiedzialne ujawnianie
{
  const W = 900, H = 330;
  let b = text(W / 2, 32, 'Skoordynowane ujawnianie podatności (CVD)', { size: 19, bold: true, anchor: 'middle' });
  const steps = [['1', 'Odkrycie', ['bez eksploatacji', 'i bez kopiowania danych'], C.host], ['2', 'Zgłoszenie', ['security.txt, VDP', 'lub CERT Polska'], C.net], ['3', 'Potwierdzenie', ['właściciel potwierdza', 'i ocenia ryzyko'], C.vio], ['4', 'Naprawa', ['poprawka, test,', 'wdrożenie'], C.ok], ['5', 'Publikacja', ['po naprawie lub', 'w terminie (np. 90 dni)'], C.ink]];
  steps.forEach(([n, t, d, col], i) => {
    const x = 30 + i * 172;
    b += box(x, 70, 156, 150, C.card, col, 10) + `<circle cx="${x + 78}" cy="100" r="18" fill="${col}"/>` + text(x + 78, 106, n, { anchor: 'middle', bold: true, fill: '#fff' });
    b += text(x + 78, 142, t, { anchor: 'middle', bold: true, fill: col, size: 14 });
    b += text(x + 78, 168, d[0], { anchor: 'middle', size: 11 }) + text(x + 78, 186, d[1], { anchor: 'middle', size: 11 });
    if (i < steps.length - 1) b += line(x + 157, 145, x + 171, 145, { arrow: true });
  });
  b += text(W / 2, 260, 'Nigdy: żądanie zapłaty za nieujawnienie, „dowód” przez pobranie danych, publikacja przed naprawą bez uzgodnienia.', { anchor: 'middle', size: 13, bold: true, fill: C.no });
  b += text(W / 2, 286, 'Zawsze: minimalny dowód, dokumentacja czasu i czynności, kanał oficjalny, poufność do czasu publikacji.', { anchor: 'middle', size: 13, bold: true, fill: C.ok });
  files['ns00-03-disclosure.svg'] = svg(W, H, 'Pięć kroków skoordynowanego ujawniania podatności: odkrycie bez eksploatacji, zgłoszenie oficjalnym kanałem, potwierdzenie przez właściciela, naprawa i publikacja po naprawie lub w ustalonym terminie', b);
}

// 4. Dokument zakresu (RoE)
{
  const W = 900, H = 440;
  let b = text(W / 2, 32, 'Dokument zakresu i zasady zaangażowania (rules of engagement)', { size: 18, bold: true, anchor: 'middle' });
  const items = [
    ['Strony i podpisy', 'zleceniodawca (właściciel systemu), wykonawca, osoba upoważniona do zgody'],
    ['Cel', 'co sprawdzamy i po co (np. odporność API, skuteczność detekcji SOC)'],
    ['Zakres (in scope)', 'adresy, domeny, aplikacje, konta testowe — wyliczone jawnie'],
    ['Wyłączenia (out of scope)', 'systemy produkcyjne krytyczne, systemy stron trzecich, DoS, socjotechnika'],
    ['Okno czasowe', 'daty, godziny, strefa czasowa; procedura przerwania testu'],
    ['Dozwolone metody', 'narzędzia, intensywność, dane testowe; czego nie wolno (np. eksfiltracja danych)'],
    ['Kontakty i eskalacja', 'kto odbiera telefon o 2:00 i co robić przy znalezisku krytycznym'],
    ['Dane i poufność', 'jak przechowywać i usuwać dowody, NDA, RODO'],
    ['Raport', 'format, termin, retest'],
  ];
  items.forEach(([k, d], i) => { const y = 56 + i * 40; b += box(30, y, 230, 32, C.netFill, C.net, 6) + text(42, y + 21, k, { bold: true, size: 13, fill: C.net }) + text(274, y + 21, d, { size: 12 }); });
  files['ns00-03-scope-roe.svg'] = svg(W, H, 'Elementy dokumentu zakresu: strony i podpisy, cel, zakres, wyłączenia, okno czasowe, dozwolone metody, kontakty i eskalacja, dane i poufność, raport', b);
}

writeSvgs(process.argv[2], files);
