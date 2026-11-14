// Quiz (100 pytan) do lekcji _ns_03/01_Ipv4HeaderAndAddressing. Pytania liczbowe licza sie modelem.
const { quizBuilder } = require('./lib.cjs');
const { SAMPLES, decode, checksumSteps, dump, hex4, ipClass, CLASSES, header } = require('./ns03_01_ipv4_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111431);
  const keys = Object.keys(SAMPLES);

  // 1) Dekodowanie pol z hexdumpu: po 4 pytania na 6 naglowkow (24)
  for (const k of keys) {
    const b = SAMPLES[k], d = decode(b), hd = dump(b);
    add(`Nagłówek: ${hd}. Jaki jest TTL?`, String(d.ttl), [String(b[8] + 1), String(b[9]), String(b[8] * 2), hex4(b[8]), String(b[8] + 64), String(255 - b[8]), String(b[8] * 16)], `Bajt 8 = 0x${b[8].toString(16)} = ${d.ttl}.`);
    add(`Nagłówek: ${hd}. Jaki protokół niesie pakiet?`, `${d.proto} — ${d.protoName}`, ['6 — TCP', '17 — UDP', '1 — ICMP', '50 — ESP (IPsec)'], `Bajt 9 = ${d.proto}.`);
    add(`Nagłówek: ${hd}. Ile bajtów danych (bez nagłówka IP) zawiera pakiet?`, String(d.payload), [String(d.total), String(d.total + 20), String(d.payload - 8), String(d.total - 28)], `Total Length ${d.total} − ${d.headerLen} B nagłówka = ${d.payload}.`);
    add(`Nagłówek: ${hd}. Jaki jest adres docelowy?`, d.dst, [d.src, d.dst.split('.').reverse().join('.'), b.slice(12, 16).map((x) => x.toString(16)).join('.'), `${b[16]}.${b[17]}.${b[18]}.${(b[19] + 1) % 256}`], `Bajty 16–19 → ${d.dst}.`);
  }

  // 2) Flagi i offset (6) — rozne wartosci bajtow 6–7
  for (const ff of [0x4000, 0x0000, 0x2000, 0x20b9, 0x00b9, 0x2172]) {
    const df = (ff & 0x4000) ? 1 : 0, mf = (ff & 0x2000) ? 1 : 0, off = ff & 0x1fff;
    const hx = ff.toString(16).padStart(4, '0');
    add(`Bajty 6–7 nagłówka to ${hx.slice(0, 2)} ${hx.slice(2)}. Co oznaczają?`,
      `DF=${df}, MF=${mf}, offset ${off} (${off * 8} B)`,
      [`DF=${1 - df}, MF=${mf}, offset ${off} (${off * 8} B)`, `DF=${df}, MF=${1 - mf}, offset ${off} (${off * 8} B)`, `DF=${df}, MF=${mf}, offset ${off * 8 || 8} (${(off * 8 || 8) * 8} B)`, `DF=${df}, MF=${mf}, offset ${off} (${off} B)`],
      'Bit 0x4000 = DF, 0x2000 = MF, dolne 13 bitów = offset w jednostkach 8 B.');
  }

  // 3) Suma kontrolna (6)
  for (const k of keys) {
    const st = checksumSteps(SAMPLES[k]);
    add(`Słowa 16-bitowe nagłówka (z sumą wyzerowaną) dają sumę 0x${st.sum.toString(16)}. Jaka jest suma kontrolna?`, `0x${hex4(st.result)}`,
      [`0x${hex4(st.folded)}`, `0x${hex4(st.sum & 0xffff)}`, `0x${hex4((~(st.sum & 0xffff)) & 0xffff)}`, `0x${hex4((st.result + 1) & 0xffff)}`],
      `Przeniesienie: 0x${hex4(st.sum & 0xffff)} + ${st.carry} = 0x${hex4(st.folded)}; negacja → 0x${hex4(st.result)}.`);
  }

  // 4) Bajt DSCP/ECN (6)
  for (const [dscp, ecn, name] of [[46, 0, 'EF'], [34, 0, 'AF41'], [0, 3, 'best effort + CE'], [48, 0, 'CS6'], [26, 2, 'AF31 + ECT(0)'], [8, 1, 'CS1 + ECT(1)']]) {
    const v = dscp * 4 + ecn;
    add(`Jaką wartość ma drugi bajt nagłówka dla DSCP ${dscp} (${name}) i ECN ${ecn}?`, `0x${v.toString(16).padStart(2, '0')}`,
      [`0x${(dscp + ecn).toString(16).padStart(2, '0')}`, `0x${((ecn << 6) | dscp).toString(16).padStart(2, '0')}`, `0x${(dscp * 2 + ecn).toString(16).padStart(2, '0')}`, `0x${(v ^ 0x80).toString(16).padStart(2, '0')}`, `0x${(dscp * 8 + ecn).toString(16).padStart(2, '0')}`, `0x${((v + 4) & 0xff).toString(16).padStart(2, '0')}`, `0x${(ecn * 4 + dscp).toString(16).padStart(2, '0')}`],
      `DSCP zajmuje 6 starszych bitów: ${dscp} × 4 + ${ecn} = ${v}.`);
  }

  // 5) Klasy (8)
  for (const ip of ['10.1.1.1', '126.0.0.1', '128.0.0.1', '191.255.0.1', '192.0.2.1', '223.1.1.1', '224.0.0.5', '239.255.255.250']) {
    const k = ipClass(ip);
    add(`Do której historycznej klasy należy adres ${ip}?`, `Klasa ${k}`, ['Klasa A', 'Klasa B', 'Klasa C', 'Klasa D', 'Klasa E'], `Pierwszy oktet ${ip.split('.')[0]} → ${CLASSES.find((c) => c[0] === k)[2]}.`);
  }

  // 6) IHL i Total Length (6)
  for (const [ihl, total] of [[5, 1500], [6, 576], [15, 1500], [5, 40], [7, 100], [5, 65535]]) {
    add(`IHL = ${ihl}, Total Length = ${total}. Ile bajtów danych niesie pakiet?`, String(total - ihl * 4),
      [String(total - ihl), String(total), String(total - 20 - ihl * 4), String(total - ihl * 8)], `Nagłówek = ${ihl} × 4 = ${ihl * 4} B; dane = ${total} − ${ihl * 4}.`);
  }

  // 7) Pytania merytoryczne (44)
  const facts = [
    ['Ile bajtów ma nagłówek IPv4 bez opcji?', '20', ['8', '40', '60'], 'IHL 5.'],
    ['Jaka jest maksymalna długość nagłówka IPv4?', '60 bajtów', ['20 bajtów', '40 bajtów', '64 bajty'], 'IHL 15 × 4.'],
    ['W jakich jednostkach podawane jest pole IHL?', 'W słowach 32-bitowych (4 B)', ['W bajtach', 'W bitach', 'W jednostkach 8 B'], '5 = 20 B.'],
    ['W jakich jednostkach podawany jest Fragment Offset?', 'W jednostkach 8 bajtów', ['W bajtach', 'W słowach 32-bit', 'W bitach'], '13 bitów × 8 = do 65 528 B.'],
    ['Co oznacza pierwsza cyfra hex nagłówka „45”?', 'Wersję protokołu: 4', ['Długość nagłówka', 'Klasę adresu', 'DSCP'], 'Druga cyfra to IHL.'],
    ['Co zawiera pole Total Length?', 'Długość całego pakietu: nagłówek plus dane', ['Tylko długość danych', 'Tylko długość nagłówka', 'Długość ramki Ethernet'], 'Maks. 65 535.'],
    ['Do czego służy pole Identification?', 'Łączy fragmenty jednego datagramu', ['Identyfikuje sesję TCP', 'Identyfikuje użytkownika', 'Numeruje porty'], 'Fragmenty mają to samo ID.'],
    ['Co oznacza flaga DF?', 'Pakietu nie wolno fragmentować', ['Pakiet jest ostatnim fragmentem', 'Pakiet ma priorytet', 'Pakiet jest uszkodzony'], 'Podstawa PMTUD.'],
    ['Co oznacza flaga MF?', 'Po tym fragmencie są kolejne', ['Pakietu nie wolno ciąć', 'Pakiet jest multicastem', 'Brak opcji'], 'Ostatni fragment ma MF=0.'],
    ['Co robi router, gdy TTL spadnie do 0?', 'Odrzuca pakiet i wysyła ICMP Time Exceeded', ['Ustawia TTL na 64', 'Przekazuje pakiet dalej', 'Wysyła TCP RST'], 'Na tym opiera się traceroute.'],
    ['Jaka jest typowa początkowa wartość TTL w Windows?', '128', ['64', '255', '32'], 'Linux/macOS 64, urządzenia sieciowe 255.'],
    ['Jaka jest typowa początkowa wartość TTL w Linuksie?', '64', ['128', '255', '30'], 'sysctl net.ipv4.ip_default_ttl.'],
    ['Po co istnieje TTL?', 'Aby pakiet nie krążył w nieskończoność przy pętli routingu', ['Aby mierzyć czas w sekundach', 'Aby szyfrować nagłówek', 'Aby wybrać trasę'], 'Licznik skoków, nie czas.'],
    ['Jaki numer protokołu ma UDP?', '17', ['6', '1', '50'], 'TCP 6, ICMP 1.'],
    ['Jaki numer protokołu ma ESP (IPsec)?', '50', ['51', '47', '89'], 'AH 51, GRE 47, OSPF 89.'],
    ['Jaki numer protokołu ma GRE?', '47', ['50', '41', '17'], 'Firewall musi przepuścić protokół 47.'],
    ['Co oznacza DSCP 46?', 'Expedited Forwarding — ruch głosowy', ['Best effort', 'Ruch sterujący siecią', 'Ruch do odrzucenia'], 'Bajt ToS 0xb8.'],
    ['Do czego służy ECN?', 'Do sygnalizowania zatłoczenia bez gubienia pakietów', ['Do szyfrowania', 'Do fragmentacji', 'Do wyboru trasy'], 'CE = 11.'],
    ['Co chroni suma kontrolna nagłówka IPv4?', 'Tylko nagłówek IP', ['Cały pakiet z danymi', 'Tylko dane', 'Ramkę Ethernet'], 'Dane chroni TCP/UDP.'],
    ['Dlaczego router musi przeliczać sumę kontrolną nagłówka?', 'Bo zmienia TTL, który jest objęty sumą', ['Bo zmienia adres docelowy', 'Bo szyfruje nagłówek', 'Bo dodaje opcje'], 'RFC 1624 — aktualizacja przyrostowa.'],
    ['Jaki wynik daje weryfikacja poprawnej sumy kontrolnej u odbiorcy?', '0xffff po zsumowaniu wszystkich słów z sumą', ['0x0000 po zsumowaniu bez sumy', '0x4500', 'Dowolny wynik parzysty'], 'Dopełnienie do jedności.'],
    ['Czy nagłówek IPv6 ma sumę kontrolną?', 'Nie — zrezygnowano z niej', ['Tak, 16-bitową', 'Tak, 32-bitową CRC', 'Tylko przy fragmentacji'], 'Szybsze przekazywanie.'],
    ['Co zwykle oznacza „checksum incorrect” w Wiresharku na pakietach wychodzących?', 'Checksum offload — sumę liczy karta sieciowa po przechwyceniu', ['Uszkodzony kabel', 'Atak spoofingu', 'Błąd DNS'], 'Sprawdź ethtool -k.'],
    ['W jakiej kolejności bajtów przesyłane są pola nagłówka?', 'Big-endian (network byte order)', ['Little-endian', 'Zależnie od systemu', 'Losowo'], 'Najstarszy bajt pierwszy.'],
    ['Dlaczego w Javie przy czytaniu bajtów nagłówka stosuje się & 0xFF?', 'Bo byte w Javie jest ze znakiem i 0xC0 dałby −64', ['Bo ByteBuffer jest little-endian', 'Aby szyfrować', 'Aby skrócić tablicę'], 'Konwersja na wartość bez znaku.'],
    ['Jaka jest domyślna kolejność bajtów ByteBuffer w Javie?', 'BIG_ENDIAN', ['LITTLE_ENDIAN', 'nativeOrder()', 'Brak domyślnej'], 'Zgodna z siecią.'],
    ['Co oznacza IP spoofing?', 'Podanie fałszywego adresu źródłowego w nagłówku', ['Przechwytywanie haseł', 'Zmianę adresu MAC switcha', 'Fragmentację pakietów'], 'Adres źródłowy nie jest uwierzytelniony.'],
    ['Który dokument opisuje filtrowanie ruchu z fałszywym adresem źródłowym u dostawców?', 'BCP 38 (RFC 2827)', ['RFC 1918', 'RFC 791', 'RFC 2131'], 'Ingress filtering.'],
    ['Dlaczego atak odbity (reflection) używa zwykle UDP?', 'Bo UDP nie ma handshake, więc fałszywy adres źródłowy wystarczy', ['Bo UDP jest szyfrowane', 'Bo TCP nie ma adresów', 'Bo UDP ma większy TTL'], 'Np. DNS, NTP, memcached.'],
    ['Jaki zakres pierwszego oktetu miała klasa B?', '128–191', ['0–127', '192–223', '224–239'], 'Pierwsze bity 10.'],
    ['Jaką maskę domyślną miała klasa C?', '/24 (255.255.255.0)', ['/8', '/16', '/32'], '254 hosty.'],
    ['Do czego służy dawna klasa D?', 'Do multicastu', ['Do adresów prywatnych', 'Do pętli zwrotnej', 'Do eksperymentów'], '224.0.0.0/4.'],
    ['Kiedy wprowadzono CIDR?', 'W 1993 roku (RFC 1519)', ['W 1981 roku (RFC 791)', 'W 1998 roku z IPv6', 'W 2011 roku'], 'Dziś RFC 4632.'],
    ['Co umożliwia agregacja tras w CIDR?', 'Ogłoszenie wielu sąsiednich sieci jednym prefiksem', ['Szyfrowanie tras', 'Fragmentację', 'Translację adresów'], 'Np. 4 × /24 → /22.'],
    ['Dlaczego klasy adresów zostały porzucone?', 'Marnowały adresy i powiększały tablice routingu', ['Bo nie działały z TCP', 'Bo wymagały IPv6', 'Bo były zbyt szybkie'], 'Tylko 3 rozmiary sieci.'],
    ['Co wyznacza granicę między częścią sieciową a hosta?', 'Maska podsieci / długość prefiksu', ['Klasa adresu', 'TTL', 'Pole Protocol'], 'Dziś zawsze jawnie.'],
    ['Na podstawie której części adresu routery wybierają trasę?', 'Części sieciowej (prefiksu)', ['Części hosta', 'Adresu źródłowego', 'Pola ID'], 'Tablica routingu zawiera prefiksy.'],
    ['Który filtr Wiresharka pokaże fragmenty IP?', 'ip.flags.mf == 1 || ip.frag_offset > 0', ['ip.ttl == 0', 'tcp.flags.syn == 1', 'ip.dsfield.dscp == 46'], 'Pierwszy fragment ma MF, kolejne offset > 0.'],
    ['Który filtr pokaże pakiety z małym TTL?', 'ip.ttl < 5', ['ip.len < 5', 'ip.id < 5', 'ip.proto < 5'], 'Traceroute, pętle.'],
    ['Jak w Linuksie wysłać ping z TTL równym 1?', 'ping -t 1 <cel>', ['ping -s 1 <cel>', 'ping -c 1 <cel>', 'ping -W 1 <cel>'], 'Windows: ping -i 1.'],
    ['Gdzie jest prawdziwy adres klienta, gdy aplikacja stoi za reverse proxy?', 'W nagłówku X-Forwarded-For/Forwarded lub protokole PROXY', ['W adresie źródłowym IP', 'W polu TTL', 'W polu Identification'], 'Ufaj tylko znanym proxy.'],
    ['Co może sugerować pakiet z opcjami IP (IHL > 5) przychodzący z Internetu?', 'Nietypowy ruch — skaner lub próba source routing', ['Normalny ruch HTTPS', 'Ruch DNS', 'Pakiet IPv6'], 'Warto alertować.'],
    ['Dlaczego nakładające się fragmenty IP są podejrzane?', 'Mogą służyć do ominięcia inspekcji IDS/firewalla', ['Bo przyspieszają transmisję', 'Bo są wymagane przez TLS', 'Bo zawierają hasła'], 'Urządzenia składają fragmenty przed inspekcją.'],
    ['Jakie pole nagłówka wykorzystuje narzędzie p0f do pasywnego rozpoznawania systemu?', 'Między innymi TTL (oraz cechy TCP)', ['Tylko adres docelowy', 'Tylko sumę kontrolną', 'Pole Protocol'], 'Fingerprinting pasywny.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
