// Ilustracje do lekcji _ns_01/07_FirstPacketCapture.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_07_capture_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, node, writeSvgs } = require('./lib.cjs');
const files = {};

// 1. Punkty przechwytywania
{
  const W = 900, H = 440;
  let b = text(W / 2, 34, 'Gdzie przechwytywać? Miejsce decyduje, co zobaczysz', { size: 19, bold: true, anchor: 'middle' });
  b += box(30, 60, 560, 300, C.card, C.ink);
  b += text(310, 84, 'host (laptop / VM / serwer)', { anchor: 'middle', bold: true });
  b += box(60, 100, 230, 120, C.vioFill, C.vio); b += text(175, 122, 'kontener klienta', { anchor: 'middle', bold: true, fill: C.vio, size: 13 });
  b += box(330, 100, 230, 120, C.vioFill, C.vio); b += text(445, 122, 'kontener app :8080', { anchor: 'middle', bold: true, fill: C.vio, size: 13 });
  b += node(175, 190, 'eth0', { square: true, w: 26, size: 11 }) + node(445, 190, 'eth0', { square: true, w: 26, size: 11 });
  b += node(310, 270, 'docker0 / br-xxx', { square: true, w: 70, size: 11, fill: C.netFill, stroke: C.net, color: C.net });
  b += line(175, 206, 280, 254, { color: C.net }) + line(445, 206, 340, 254, { color: C.net });
  b += text(205, 246, 'veth', { size: 11, fill: C.muted }) + text(395, 246, 'veth', { size: 11, fill: C.muted });
  b += node(310, 330, 'eth0 / Wi-Fi hosta', { square: true, w: 80, size: 11, fill: C.hostFill, stroke: C.host, color: C.host });
  b += line(310, 286, 310, 314, { color: C.host });
  b += node(110, 330, 'lo', { square: true, w: 22, size: 11 });
  b += line(590, 330, 700, 330, { color: C.host, w: 3 }) + node(760, 330, 'przełącznik', { square: true, w: 52, size: 11 });
  b += node(760, 250, 'port SPAN / TAP', { square: true, w: 64, size: 11, fill: C.okFill, stroke: C.ok, color: C.ok });
  b += line(760, 314, 760, 266, { color: C.ok, dash: true });
  const notes = [
    ['A', 'eth0 w kontenerze app', 'tylko ruch tej usługi (np. docker run --net container:app netshoot)', 100],
    ['B', 'bridge docker0', 'ruch między kontenerami w tej sieci', 124],
    ['C', 'eth0 hosta', 'ruch wychodzący (po NAT Dockera!)', 148],
    ['D', 'lo', 'połączenia na 127.0.0.1 — niewidoczne na eth0', 172],
    ['E', 'SPAN/TAP', 'ruch wielu hostów — praca sensora IDS/NSM', 196],
  ];
  b += box(610, 60, 270, 160, C.bg, C.line);
  notes.forEach(([k, n, d, y]) => { b += text(622, y - 10 + 26, `${k}. ${n}`, { size: 12, bold: true }) ; });
  const tag = (x, y, k) => `<circle cx="${x}" cy="${y}" r="11" fill="${C.no}"/>` + text(x, y + 4, k, { anchor: 'middle', size: 12, bold: true, fill: '#fff' });
  b += tag(490, 178, 'A') + tag(395, 270, 'B') + tag(400, 330, 'C') + tag(148, 330, 'D') + tag(838, 250, 'E');
  b += text(W / 2, 392, 'A: tylko ta usługa · B: ruch między kontenerami · C: ruch na zewnątrz, już po NAT · D: 127.0.0.1 · E: kopia ruchu z przełącznika (sensor NSM)', { anchor: 'middle', size: 12 });
  b += text(W / 2, 418, 'Zasada: przechwytuj możliwie blisko problemu i po obu stronach elementu, który podejrzewasz (proxy, NAT, firewall).', { anchor: 'middle', size: 13, bold: true });
  files['ns01-07-capture-points.svg'] = svg(W, H, 'Host z dwoma kontenerami, bridge Dockera, interfejsem pętli zwrotnej i fizycznym interfejsem podłączonym do przełącznika z portem SPAN; zaznaczono pięć punktów przechwytywania i co widać w każdym', b);
}

// 2. Panele Wiresharka
{
  const W = 900, H = 470;
  let b = text(W / 2, 30, 'Trzy panele Wiresharka: lista, szczegóły, bajty', { size: 19, bold: true, anchor: 'middle' });
  b += box(30, 46, 840, 30, '#eef3ea', C.ok, 4) + text(40, 66, 'http || tcp.flags.syn == 1', { mono: true, size: 13, fill: C.ok }) + text(860, 66, '← filtr wyświetlania (zielony = poprawny)', { anchor: 'end', size: 12, fill: C.muted });
  const rows = [['1', '0.000000', '172.17.0.3', '172.17.0.2', 'TCP', '51334 → 8080 [SYN] Seq=0 Win=64240 MSS=1460', '#e7e6ff'],
    ['2', '0.000041', '172.17.0.2', '172.17.0.3', 'TCP', '8080 → 51334 [SYN, ACK] Seq=0 Ack=1', '#e7e6ff'],
    ['4', '0.000312', '172.17.0.3', '172.17.0.2', 'HTTP', 'GET /users/42 HTTP/1.1', '#e4ffc7'],
    ['6', '0.012877', '172.17.0.2', '172.17.0.3', 'HTTP', 'HTTP/1.1 200  (application/json)', '#e4ffc7']];
  b += box(30, 84, 840, 132, C.card, C.line, 4);
  ['Nr', 'Czas', 'Źródło', 'Cel', 'Protokół', 'Info'].forEach((h, i) => { b += text([38, 80, 170, 290, 410, 480][i], 102, h, { bold: true, size: 12 }); });
  rows.forEach((r, i) => { const y = 110 + i * 25; b += `<rect x="32" y="${y}" width="836" height="23" fill="${r[6]}"/>`; [38, 80, 170, 290, 410, 480].forEach((x, k) => { b += text(x, y + 16, r[k], { size: 12, mono: k < 4 }); }); });
  b += box(30, 224, 840, 116, C.card, C.line, 4);
  ['▸ Frame 4: 133 bytes on wire (1064 bits), 133 bytes captured', '▸ Ethernet II, Src: 02:42:ac:11:00:03, Dst: 02:42:ac:11:00:02', '▸ Internet Protocol Version 4, Src: 172.17.0.3, Dst: 172.17.0.2', '▸ Transmission Control Protocol, Src Port: 51334, Dst Port: 8080, Seq: 1, Ack: 1, Len: 79', '▾ Hypertext Transfer Protocol   →   GET /users/42 HTTP/1.1\\r\\n'].forEach((t, i) => { b += text(40, 244 + i * 21, t, { size: 12, mono: true, fill: i === 4 ? C.vio : C.ink }); });
  b += box(30, 348, 840, 76, C.card, C.line, 4);
  ['0030  01 f6 9f 3c 00 00 47 45 54 20 2f 75 73 65 72 73   ...<..GET /users', '0040  2f 34 32 20 48 54 54 50 2f 31 2e 31 0d 0a 48 6f   /42 HTTP/1.1..Ho'].forEach((t, i) => { b += text(40, 372 + i * 22, t, { size: 12, mono: true }); });
  b += `<rect x="210" y="358" width="215" height="18" fill="${C.vio}" opacity="0.2"/>`;
  b += text(W / 2, 446, 'Kliknięcie pola w szczegółach podświetla jego bajty — i odwrotnie. Kolory listy pochodzą z reguł kolorowania (np. czarny = problem TCP).', { anchor: 'middle', size: 12, fill: C.muted });
  files['ns01-07-wireshark-panes.svg'] = svg(W, H, 'Makieta okna Wiresharka: pasek filtra wyświetlania, lista pakietów z handshake TCP i żądaniem HTTP, drzewo szczegółów ramki z warstwami oraz panel bajtów z podświetlonym fragmentem żądania', b);
}

// 3. Filtry przechwytywania vs wyswietlania
{
  const W = 900, H = 425;
  let b = text(W / 2, 34, 'Filtr przechwytywania (BPF) vs filtr wyświetlania', { size: 19, bold: true, anchor: 'middle' });
  b += `<polygon points="60,70 400,70 330,190 130,190" fill="${C.hostFill}" stroke="${C.host}" stroke-width="2"/>`;
  b += text(230, 100, 'cały ruch na interfejsie', { anchor: 'middle', size: 13 });
  b += text(230, 130, 'BPF: tcp port 8080', { anchor: 'middle', size: 15, bold: true, mono: true, fill: C.host });
  b += text(230, 160, 'odrzuca w jądrze, zanim zapisze', { anchor: 'middle', size: 12 });
  b += line(230, 192, 230, 226, { color: C.host, w: 3, arrow: true });
  b += box(140, 230, 180, 50, C.card, C.ink, 8) + text(230, 260, 'plik .pcapng', { anchor: 'middle', bold: true });
  b += line(322, 255, 470, 255, { color: C.ok, w: 3, arrow: true });
  b += `<polygon points="480,200 840,200 780,310 540,310" fill="${C.okFill}" stroke="${C.ok}" stroke-width="2"/>`;
  b += text(660, 228, 'filtr wyświetlania:', { anchor: 'middle', size: 13 });
  b += text(660, 256, 'http.response.code >= 500', { anchor: 'middle', size: 15, bold: true, mono: true, fill: C.ok });
  b += text(660, 284, 'ukrywa, ale nic nie usuwa', { anchor: 'middle', size: 12 });
  b += text(260, 330, 'BPF (tcpdump, dumpcap, -f w Wiresharku):', { size: 13, bold: true, anchor: 'middle' });
  b += text(260, 352, 'prosty, szybki, ustalany PRZED startem', { size: 12, anchor: 'middle' });
  b += text(660, 344, 'filtr wyświetlania (Wireshark, tshark -Y):', { size: 13, bold: true, anchor: 'middle' });
  b += text(660, 366, 'pola dekoderów, zmieniany w każdej chwili', { size: 12, anchor: 'middle' });
  b += text(W / 2, 410, 'Czego nie przechwyciłeś filtrem BPF, tego już nie odzyskasz.', { anchor: 'middle', size: 13, bold: true, fill: C.no });
  files['ns01-07-filters.svg'] = svg(W, H, 'Lejek: filtr BPF tcp port 8080 odrzuca ruch przed zapisem do pliku pcapng; filtr wyświetlania http.response.code >= 500 tylko ukrywa pakiety w zapisanym pliku', b);
}

// 4. Follow TCP Stream
{
  const W = 900, H = 380;
  let b = text(W / 2, 34, 'Follow TCP Stream: rozmowa złożona z wielu segmentów', { size: 19, bold: true, anchor: 'middle' });
  b += box(30, 56, 840, 260, C.card, C.line, 8);
  const lines = [
    ['c', 'GET /users/42 HTTP/1.1'], ['c', 'Host: api.javaquest.local'], ['c', 'Accept: application/json'], ['c', ''],
    ['s', 'HTTP/1.1 200'], ['s', 'Content-Type: application/json'], ['s', 'Content-Length: 51'], ['s', ''], ['s', '{"id":42,"login":"anna","email":"anna@example.com"}'],
  ];
  lines.forEach(([who, t], i) => { b += text(50, 82 + i * 25, t || '·', { mono: true, size: 13, fill: who === 'c' ? C.no : C.net }); });
  b += box(600, 70, 250, 54, C.noFill, C.no, 6) + text(725, 93, 'czerwony: klient → serwer', { anchor: 'middle', size: 12, bold: true, fill: C.no }) + text(725, 112, 'filtr: tcp.stream eq 3', { anchor: 'middle', size: 12, mono: true });
  b += box(600, 136, 250, 40, C.netFill, C.net, 6) + text(725, 161, 'niebieski: serwer → klient', { anchor: 'middle', size: 12, bold: true, fill: C.net });
  b += box(600, 228, 250, 76, C.hostFill, C.host, 6) + text(725, 250, 'Uwaga: dane osobowe!', { anchor: 'middle', size: 12, bold: true, fill: C.host }) + text(725, 270, 'pcap z ruchem jawnym to', { anchor: 'middle', size: 12 }) + text(725, 288, 'dane wrażliwe (RODO)', { anchor: 'middle', size: 12 });
  b += text(W / 2, 346, 'Wireshark skleja segmenty w strumień po czwórce adresów i portów — widzisz to, co zobaczyła aplikacja.', { anchor: 'middle', size: 13 });
  b += text(W / 2, 368, 'Przy HTTPS bez kluczy sesji zobaczysz tylko rekordy TLS (Application Data).', { anchor: 'middle', size: 13, bold: true });
  files['ns01-07-follow-stream.svg'] = svg(W, H, 'Okno Follow TCP Stream: żądanie GET klienta na czerwono i odpowiedź JSON serwera na niebiesko, z uwagą, że przechwycony ruch jawny zawiera dane osobowe', b);
}

writeSvgs(process.argv[2], files);
