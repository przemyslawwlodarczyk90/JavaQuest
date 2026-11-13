// Generuje lekcje _ns_03_ip_i_routing/02_BinaryForNetworkers.json (teoria, 30 zadan, 100 pytan liczonych skryptem).
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_02_binary_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/02_BinaryForNetworkers.json
const fs = require('fs');
const out = process.argv[2];

// ---------- narzedzia ----------
const W8 = [128, 64, 32, 16, 8, 4, 2, 1];
const bin8 = (n) => n.toString(2).padStart(8, '0');
const binIp = (ip) => ip.split('.').map((o) => bin8(Number(o))).join('.');
const ipToInt = (ip) => ip.split('.').reduce((a, o) => (a * 256) + Number(o), 0);
const hex2 = (n) => n.toString(16).toUpperCase().padStart(2, '0');
const fmt = (n) => n.toLocaleString('pl-PL').replace(/\s/g, ' ');
const ones = (n) => bin8(n).split('').filter((c) => c === '1').length;
// rozpisanie metoda odejmowania od lewej
function subtractWalk(v) {
  let rest = v; const steps = [];
  for (const w of W8) {
    if (rest >= w) { steps.push(`${rest} ≥ ${w} → 1, zostaje ${rest - w}`); rest -= w; } else steps.push(`${rest} < ${w} → 0`);
  }
  return steps.join('\n') + `\nwynik: ${bin8(v)}`;
}
function sumWalk(b) {
  const parts = b.split('').map((c, i) => (c === '1' ? W8[i] : null)).filter((x) => x !== null);
  return `${b}\n${W8.join('  ')}\n${parts.length ? parts.join(' + ') : '0'} = ${parseInt(b, 2)}`;
}
function andWalk(a, m) {
  return `  ${bin8(a)}  (${a})\nAND ${bin8(m)}  (${m})\n= ${bin8(a & m)}  (${a & m})`;
}

let seed = 20261113;
const rnd = () => { seed = (seed * 1103515245 + 12345) % 2147483648; return seed / 2147483648; };
const LET = ['A', 'B', 'C', 'D'];
function q(question, correct, wrongs, explanation, code) {
  const opts = [correct, ...wrongs.filter((w, i, arr) => w !== correct && arr.indexOf(w) === i)].slice(0, 4);
  if (opts.length !== 4) throw new Error('za malo opcji: ' + question + ' ' + JSON.stringify(opts));
  const order = opts.map((o) => [rnd(), o]).sort((x, y) => x[0] - y[0]).map((x) => x[1]);
  const options = {}; order.forEach((o, i) => { options[LET[i]] = String(o); });
  const item = { question, options, correct: LET[order.indexOf(correct)], explanation };
  if (code) item.code = code;
  return item;
}

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Dlaczego sieciowiec musi myśleć w zerach i jedynkach', code: null, body:
`Router nie widzi adresu \`192.168.1.77\`. Widzi **32 bity**: \`11000000101010000000000101001101\`. Kropki, liczby dziesiętne i „ładny” zapis to wyłącznie wygoda dla ludzi. Każda decyzja sieci — czy adres jest w mojej podsieci, którą trasą wysłać pakiet, czy reguła firewalla pasuje — zapada na poziomie pojedynczych bitów.

Dlatego **każdy, kto „nie kuma maski”, w rzeczywistości nie ma jeszcze wprawy w systemie dwójkowym**. Gdy zamiana \`224\` → \`11100000\` stanie się odruchem, maski, CIDR, podsieci, wildcardy i adresy IPv6 przestaną być magią.

W tej lekcji:
- zrozumiesz, czym jest bit, bajt i oktet, i dlaczego sieci liczą właśnie w potęgach dwójki,
- nauczysz się **metody tabeli 128-64-32-16-8-4-2-1** — zamiany w obie strony bez kalkulatora,
- zobaczysz adres IPv4 jako jedną 32-bitową liczbę,
- wykonasz ręcznie **AND** — operację, na której opiera się cała następna lekcja o masce,
- poznasz system **szesnastkowy** (MAC, IPv6) i operatory bitowe w Javie, łącznie z pułapką znakowego typu \`byte\`.` },

  { type: 'DEFINITION', heading: 'Bit, bajt, oktet, nibble i waga pozycji', code: null, body:
`- **Bit** — najmniejsza jednostka informacji: \`0\` albo \`1\`. Na kablu to poziom napięcia, w światłowodzie impuls światła, w Wi-Fi zmiana fazy/amplitudy fali.
- **Bajt** — 8 bitów. W praktyce zawsze 8, ale normy sieciowe (RFC) mówią precyzyjnie **oktet**, bo dawniej istniały maszyny z bajtem 6- lub 9-bitowym.
- **Oktet** — dokładnie 8 bitów. Adres IPv4 = 4 oktety = 32 bity. Wartości oktetu: od \`0\` (\`00000000\`) do \`255\` (\`11111111\`), czyli 256 możliwości.
- **Nibble (półbajt)** — 4 bity, czyli jedna cyfra szesnastkowa (\`0\`–\`F\`).
- **System pozycyjny** — wartość cyfry zależy od pozycji. W dziesiętnym wagi to 1, 10, 100…; w dwójkowym **1, 2, 4, 8, 16, 32, 64, 128** — każda kolejna dwa razy większa.
- **MSB / LSB** — *most / least significant bit*: najstarszy bit (lewy, waga 128 w oktecie) i najmłodszy (prawy, waga 1).
- **Kolejność sieciowa (network byte order)** — w nagłówkach protokołów najstarszy bajt idzie pierwszy (*big-endian*). Procesory x86 trzymają liczby odwrotnie (*little-endian*), stąd funkcje \`htons\`/\`ntohl\` w C. Java na szczęście używa big-endian (\`ByteBuffer\` domyślnie).
- **Zapis** — liczby dwójkowe oznacza się prefiksem \`0b\` (\`0b11000000\`), szesnastkowe \`0x\` (\`0xC0\`).` },

  { type: 'ANALOGY', heading: 'Rząd ośmiu przełączników światła', code: null, body:
`Wyobraź sobie panel z ośmioma przełącznikami. Każdy włącza żarówkę o innej mocy: **128 W, 64 W, 32 W, 16 W, 8 W, 4 W, 2 W, 1 W**. Pytanie „jak uzyskać dokładnie 202 W?” ma **dokładnie jedno** rozwiązanie — i właśnie ono jest zapisem dwójkowym 202.

Zaczynasz od największej żarówki: 128 W mieści się w 202, więc włączasz (\`1\`), zostaje 74. 64 W mieści się w 74 — włączasz, zostaje 10. 32 i 16 się nie mieszczą (\`0\`, \`0\`). 8 — tak, zostaje 2. 4 — nie. 2 — tak, zostaje 0. 1 — nie. Odczytujesz pozycje przełączników: **\`11001010\`**.

Maska podsieci to dokładnie taki panel, w którym przełączniki wolno włączać tylko **po kolei od lewej**. Dlatego oktet maski ma tylko 9 wartości: 0, 128, 128+64 = 192, 224, 240, 248, 252, 254, 255.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Tabela wag — jedno narzędzie do wszystkiego', body:
`Narysuj tę tabelę na kartce przed każdym zadaniem z podsieci (na egzaminie CompTIA możesz to zrobić na tablicy zaraz po starcie). Kilka liczb, które warto rozpoznawać na pierwszy rzut oka:`,
    code:
`waga:     128  64  32  16   8   4   2   1
──────────────────────────────────────────
  10   =    0   0   0   0   1   0   1   0    8+2
  77   =    0   1   0   0   1   1   0   1    64+8+4+1
 168   =    1   0   1   0   1   0   0   0    128+32+8
 192   =    1   1   0   0   0   0   0   0    128+64
 202   =    1   1   0   0   1   0   1   0    128+64+8+2
 224   =    1   1   1   0   0   0   0   0    128+64+32
 255   =    1   1   1   1   1   1   1   1    suma wszystkich wag

Przydatne obserwacje:
  liczba nieparzysta  ↔  ostatni bit = 1
  255 − x             ↔  odwrócenie wszystkich bitów x   (to jest wildcard!)
  x · 2               ↔  przesunięcie bitów o 1 w lewo   (x << 1)
  x / 2               ↔  przesunięcie bitów o 1 w prawo  (x >> 1)` },

  { type: 'CODE_BASIC', heading: 'Zamiana w obie strony, krok po kroku', body:
`**Dwójkowy → dziesiętny:** dodaj wagi pozycji, na których stoi \`1\`. **Dziesiętny → dwójkowy:** odejmuj wagi od największej (metoda „żarówek”) albo dziel przez 2 i zbieraj reszty od dołu. Oba sposoby dają ten sam wynik:`,
    code:
`DWÓJKOWY → DZIESIĘTNY: 10101000
${sumWalk('10101000')}

DZIESIĘTNY → DWÓJKOWY: 77 (odejmowanie wag)
${subtractWalk(77)}

DZIESIĘTNY → DWÓJKOWY: 77 (dzielenie przez 2)
77 / 2 = 38 r 1
38 / 2 = 19 r 0
19 / 2 =  9 r 1
 9 / 2 =  4 r 1
 4 / 2 =  2 r 0
 2 / 2 =  1 r 0
 1 / 2 =  0 r 1
reszty od dołu: 1001101 → dopełnij do 8 bitów: 01001101` },

  { type: 'CODE_PRACTICAL', heading: 'Adres IP bit po bicie w Javie', body:
`Mini-program z wymagań lekcji: wypisuje adres IPv4 jako 32 bity, liczbę całkowitą i hex. Zwróć uwagę na \`& 0xFF\` — bez tego Java zinterpretuje bajty ≥ 128 jako ujemne (szczegóły w bloku „Źle / Dobrze”).`,
    code:
`import java.net.InetAddress;
import java.nio.ByteBuffer;

public class IpBits {
    public static void main(String[] args) throws Exception {
        byte[] raw = InetAddress.getByName("192.168.1.77").getAddress();

        StringBuilder bits = new StringBuilder();
        for (byte b : raw) {
            int octet = b & 0xFF;                                  // 0..255, a nie -128..127
            String s = Integer.toBinaryString(octet);              // "11000000" – bez zer wiodących!
            bits.append("0".repeat(8 - s.length())).append(s).append('.');
        }
        System.out.println(bits.substring(0, bits.length() - 1));
        // 11000000.10101000.00000001.01001101

        int asInt = ByteBuffer.wrap(raw).getInt();                 // big-endian = kolejność sieciowa
        System.out.println(Integer.toUnsignedString(asInt));       // 3232235853
        System.out.println(Integer.toHexString(asInt));            // c0a8014d

        System.out.println(Integer.parseInt("11001010", 2));       // 202
        System.out.println(Integer.bitCount(0xFFFFFF00));          // 24 – liczba jedynek = prefiks /24
        System.out.println(37 & 248);                              // 32 – AND na oktecie
    }
}` },

  { type: 'STEP_BY_STEP', heading: 'Operacja AND — fundament obliczania sieci', code: null, body:
`AND (iloczyn logiczny) porównuje dwie liczby **bit po bicie** na tych samych pozycjach. Wynik ma \`1\` tylko tam, gdzie **oba** bity są \`1\`. Policzmy \`37 AND 248\`:
1. **Zamień obie liczby na 8 bitów.** 37 = \`00100101\` (32+4+1), 248 = \`11111000\` (128+64+32+16+8).
2. **Ustaw je jedna pod drugą**, wyrównane do prawej — tak jak przy dodawaniu pisemnym.
3. **Dla każdej kolumny** zapisz \`1\` tylko gdy oba bity to \`1\`: \`0&1=0, 0&1=0, 1&1=1, 0&1=0, 0&1=0, 1&0=0, 0&0=0, 1&0=0\`.
4. **Wynik:** \`00100000\` = **32**.
5. **Zinterpretuj:** 248 to oktet maski (5 jedynek). Jedynki „przepuściły” trzy najstarsze bity liczby 37, zera wyzerowały pozostałe. Tak powstaje *adres sieci* — dokładnie to zrobisz w następnej lekcji dla całych adresów.

Pokrewne operacje, które spotkasz w sieciach:
- **OR** — \`1\`, gdy którykolwiek bit to \`1\` → ustawianie bitów hosta na 1 daje *broadcast*.
- **NOT** — odwraca bity: NOT maski = *wildcard* (używany w ACL Cisco i OSPF).
- **XOR** — \`1\`, gdy bity się różnią → szybkie porównywanie adresów, sumy kontrolne, parzystość w RAID.` },

  { type: 'NOTE', heading: 'Potęgi dwójki, które sieciowiec zna na pamięć', body:
`Każdy dodatkowy bit **podwaja** liczbę możliwych wartości. Stąd biorą się wszystkie „dziwne” liczby w sieciach: 65 535 portów, 4 094 VLAN-y, 16 777 214 hostów w /8.`,
    code:
`2^1  = 2            2^9  = 512           2^17 = 131 072
2^2  = 4            2^10 = 1 024         2^20 = 1 048 576       (~1 mln)
2^3  = 8            2^11 = 2 048         2^24 = 16 777 216      (~16,7 mln, sieć /8)
2^4  = 16           2^12 = 4 096         2^32 = 4 294 967 296   (~4,3 mld, cały IPv4)
2^5  = 32           2^13 = 8 192         2^48 ≈ 2,8 · 10^14     (adresy MAC)
2^6  = 64           2^14 = 16 384        2^64 ≈ 1,8 · 10^19     (hosty w jednej sieci IPv6 /64)
2^7  = 128          2^15 = 32 768        2^128 ≈ 3,4 · 10^38    (cała przestrzeń IPv6)
2^8  = 256          2^16 = 65 536

Gdzie to widać:
  16 bitów  → numer portu TCP/UDP: 0–65 535
  12 bitów  → VLAN ID w 802.1Q: 4 096 wartości (0 i 4095 zarezerwowane → 4 094)
   8 bitów  → TTL w IPv4: maksymalnie 255 skoków
  13 bitów  → offset fragmentu IPv4 (liczony w jednostkach po 8 bajtów)` },

  { type: 'USAGE', heading: 'Gdzie dwójkowy i hex wychodzą w praktyce', code: null, body:
`- **Maski i CIDR** — \`/26\` = 26 jedynek; liczba hostów to \`2^(32−26) − 2\`. Bez potęg dwójki nie policzysz żadnej podsieci.
- **Wireshark i tcpdump** — panel bajtów pokazuje pakiet w hex (\`45 00 00 3c…\`); pierwszy bajt \`0x45\` to wersja IP = 4 i długość nagłówka = 5 słów po 32 bity. Flagi TCP (SYN, ACK, FIN…) to pojedyncze bity jednego bajtu — \`0x12\` = SYN+ACK.
- **Uprawnienia Linuksa** — \`chmod 750\` to trzy grupy po 3 bity: \`111 101 000\` (rwx r-x ---).
- **Adresy MAC i IPv6** — zawsze w hex; zrozumienie, że jedna cyfra to 4 bity, pozwala czytać prefiksy IPv6 (\`/48\`, \`/64\`) i rozpoznawać producenta karty po OUI.
- **Java** — operatory \`&\`, \`|\`, \`^\`, \`~\`, \`<<\`, \`>>\`, \`>>>\`; \`Integer.toBinaryString\`, \`Integer.parseInt(s, 2)\`, \`Integer.bitCount\`, \`HexFormat\` (Java 17+). Spotkasz je w parserach protokołów, Nettym, filtrach Bloom, flagach w \`EnumSet\` i haszowaniu \`HashMap\` (\`(n - 1) & hash\`).
- **Bezpieczeństwo** — adresy IP w nietypowym zapisie (\`http://3232235777/\`, \`0xC0A80101\`) to klasyczny sposób obchodzenia naiwnych filtrów SSRF i blokad w proxy. Rozumienie, że to *ta sama liczba*, jest warunkiem poprawnej walidacji.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy przy liczeniu bitów', code: null, body:
`- **Brak zer wiodących** — \`Integer.toBinaryString(5)\` zwraca \`101\`, nie \`00000101\`. Jeśli sklejasz oktety bez dopełnienia, granice oktetów się rozjeżdżają i maska wygląda na inną.
- **Odczytywanie od złej strony** — najstarszy bit (waga 128) jest **po lewej**. Reszty z dzielenia przez 2 czytasz *od dołu*; czytane od góry dają lustrzane odbicie.
- **Mylenie 2^n z 2^n − 1** — 8 bitów daje 256 *wartości*, ale największa z nich to 255. Port maksymalny to 65 535, nie 65 536.
- **Znakowy \`byte\` w Javie** — 192 w tablicy \`byte[]\` to \`-64\`. Każdy bajt adresu trzeba „odznakować” przez \`& 0xFF\`.
- **\`>>\` zamiast \`>>>\`** — przesunięcie w prawo ze znakiem powiela najstarszy bit; dla adresu \`192.x.x.x\` jako \`int\` (liczba ujemna!) \`ip >> 24\` daje \`-64\`, a \`(ip >>> 24)\` lub \`(ip >> 24) & 0xFF\` — poprawne 192.
- **Zera wiodące w zapisie dziesiętnym IP** — \`010.0.0.1\` niektóre parsery (np. \`inet_aton\`, starsze przeglądarki) czytają jako **ósemkowe** 8.0.0.1. Nigdy nie dopisuj zer w adresach w konfiguracji.
- **Liczenie hex jak dziesiętnego** — \`0x10\` to 16, nie 10; \`0xFF\` to 255.` },

  { type: 'CODE_WRONG', heading: 'Źle: adres IP z ujemnymi oktetami i dziurawa walidacja', body:
`Dwa błędy z prawdziwych code review: wypisywanie bajtów adresu „wprost” i filtr SSRF porównujący tekst zamiast liczby.`,
    code:
`byte[] raw = InetAddress.getByName("192.168.1.77").getAddress();
System.out.println(raw[0] + "." + raw[1] + "." + raw[2] + "." + raw[3]);
// -64.-88.1.77      ← byte w Javie ma zakres -128..127

// Filtr „chroniący” przed dostępem do sieci wewnętrznej:
boolean isInternal(String host) {
    return host.startsWith("10.") || host.startsWith("192.168.") || host.equals("127.0.0.1");
}
// isInternal("3232235777")  → false, a to jest 192.168.1.1 zapisane jako jedna liczba
// isInternal("0x7f.0.0.1")  → false, a wiele parserów (inet_aton, curl) czyta to jako 127.0.0.1
// isInternal("localhost")   → false` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: & 0xFF i decyzja na liczbach, nie na tekście', body:
`Bajty odznakowujemy, a decyzję bezpieczeństwa podejmujemy na **rozwiązanym, kanonicznym adresie** — tym samym, z którym aplikacja faktycznie się połączy. Sprawdzamy zakresy bitowo (wbudowane metody \`InetAddress\` robią to za nas):`,
    code:
`byte[] raw = InetAddress.getByName("192.168.1.77").getAddress();
System.out.println((raw[0] & 0xFF) + "." + (raw[1] & 0xFF) + "." + (raw[2] & 0xFF) + "." + (raw[3] & 0xFF));
// 192.168.1.77

boolean isInternal(InetAddress a) {               // adres PO rozwiązaniu nazwy
    return a.isLoopbackAddress()                  // 127.0.0.0/8, ::1
        || a.isSiteLocalAddress()                 // 10/8, 172.16/12, 192.168/16
        || a.isLinkLocalAddress()                 // 169.254/16 – m.in. metadane chmury!
        || a.isAnyLocalAddress();                 // 0.0.0.0
}
// Do łączenia używaj DOKŁADNIE tego InetAddress, który sprawdziłeś
// (inaczej DNS rebinding podmieni adres między sprawdzeniem a połączeniem).
// Uwaga: isSiteLocalAddress() nie obejmuje 100.64/10 (CGNAT) ani adresów IPv6 ULA fc00::/7 –
// dla pełnej ochrony porównuj adres z listą prefiksów operacją AND.` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy dwójkowy, kiedy dziesiętny, kiedy hex', code: null, body:
`- **Dwójkowy** — gdy liczy się *granica* między bitami: maski, prefiksy, podział na podsieci, flagi TCP, uprawnienia. Rozpisz tylko *ciekawy* oktet, reszta jest oczywista.
- **Dziesiętny z kropkami** — do komunikacji z ludźmi i w konfiguracji IPv4 (\`ip addr\`, Security Groups, \`application.yml\`).
- **Szesnastkowy** — gdy danych jest dużo i trzeba je zapisać zwięźle: MAC, IPv6, zrzuty pakietów, hashe (SHA-256 = 64 cyfry hex), identyfikatory certyfikatów. Jedna cyfra = 4 bity, więc konwersja hex ↔ bin jest natychmiastowa, bez liczenia.
- **Jedna liczba 32-bitowa** — w kodzie: porównania zakresów, sortowanie adresów, przechowywanie w bazie (PostgreSQL ma typ \`inet\`/\`cidr\` i robi to za ciebie), szybkie sprawdzanie przynależności do podsieci \`(ip & mask) == network\`.
- **Kalkulator / \`ipcalc\`** — do weryfikacji, nie zamiast rozumienia. Na egzaminach Network+ i Security+ kalkulatora nie ma.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', code: null, body:
`- Sieci operują na bitach; zapis dziesiętny z kropkami to tylko etykieta dla ludzi. Adres IPv4 = **4 oktety = 32 bity**.
- Wagi oktetu: **128, 64, 32, 16, 8, 4, 2, 1** (suma 255). Zamiana: dodawanie wag (bin → dec) lub odejmowanie od największej (dec → bin).
- Potęgi dwójki określają rozmiary wszystkiego: 2^8 = 256 wartości oktetu, 2^16 = 65 536 portów, 2^32 ≈ 4,3 mld adresów IPv4.
- **AND** daje 1 tylko gdy oba bity to 1 — to operacja, którą host liczy adres sieci (IP AND maska). OR, NOT i XOR też mają zastosowania sieciowe.
- **Hex**: 1 cyfra = 4 bity, 2 cyfry = bajt. MAC i IPv6 zapisuje się w hex.
- W Javie: \`& 0xFF\` dla bajtów, \`>>>\` zamiast \`>>\`, dopełniaj zera w \`toBinaryString\`.
- Bezpieczeństwo: ten sam adres ma wiele zapisów — waliduj adres **po** sparsowaniu, nie tekst.
- Następna lekcja: **maska podsieci od zera** — zastosujesz AND do całych adresów.` },
];
const IMAGES = {
  ANALOGY: ['netsec/ns03-02-place-values.svg', 'Tabela wag 128, 64, 32, 16, 8, 4, 2, 1: liczba 202 to bity 11001010, czyli 128 + 64 + 8 + 2, wraz z rozpisaniem metodą odejmowania.'],
  INTRO: ['netsec/ns03-02-ipv4-32-bits.svg', 'Adres 192.168.1.77 jako cztery oktety: 11000000, 10101000, 00000001, 01001101, numery bitów od 31 do 0 oraz wartość jako jedna liczba 3 232 235 853.'],
  STEP_BY_STEP: ['netsec/ns03-02-and-operation.svg', 'Operacja AND bit po bicie: 00100101 (37) AND 11111000 (248) daje 00100000 (32), obok tabela prawdy AND.'],
  WHEN_TO_USE: ['netsec/ns03-02-hex-nibbles.svg', 'Bajt 192 jako dwa nibble 1100 i 0000, czyli 0xC0; cyfry A–F; adres MAC w hex i adres IPv6 w pełnej i skróconej postaci.'],
};
for (const block of theory) { const im = IMAGES[block.type]; if (im) { block.image = im[0]; block.imageAlt = im[1]; } }
if (theory.length !== 14) throw new Error('teoria: ' + theory.length);

// ---------- ZADANIA ----------
const exercises = [];
const ex = (prompt, hint, solution) => exercises.push({ prompt, hint, solution });

ex('Narysuj z pamięci tabelę wag jednego oktetu i podaj sumę wszystkich wag. Dlaczego ta suma jest równa największej wartości oktetu?',
  'Zacznij od 1 po prawej i podwajaj w lewo.',
  '128  64  32  16  8  4  2  1\nSuma = 255 = 2^8 − 1. Osiem jedynek (11111111) to największa liczba zapisana na 8 bitach; o jeden więcej (256) wymagałoby dziewiątego bitu.');
for (const v of [10, 77, 100, 172, 192, 200, 240, 254]) {
  ex(`Zamień liczbę ${v} na 8-bitowy zapis dwójkowy metodą odejmowania wag.`, 'Zaczynaj od 128; jeśli waga mieści się w reszcie — wpisz 1 i odejmij.', subtractWalk(v));
}
for (const b of ['00011111', '01100100', '10000001', '10101010', '11110000', '11111100']) {
  ex(`Zamień liczbę dwójkową ${b} na dziesiętną.`, 'Dodaj wagi pozycji, na których stoi 1.', sumWalk(b));
}
for (const ip of ['10.0.0.1', '172.16.254.3', '192.168.10.130', '8.8.4.4']) {
  ex(`Zapisz adres ${ip} w postaci 32 bitów (z kropkami między oktetami) oraz jako jedną liczbę dziesiętną.`,
    'Zamień każdy oktet osobno, potem: o1·2^24 + o2·2^16 + o3·2^8 + o4.',
    `${binIp(ip)}\n${ip.split('.').map((o, i) => `${o}·2^${24 - 8 * i}`).join(' + ')} = ${fmt(ipToInt(ip))}`);
}
for (const [a, m] of [[37, 248], [200, 192], [130, 224], [77, 240]]) {
  ex(`Oblicz ${a} AND ${m} bit po bicie.`, 'Zapisz obie liczby na 8 bitach jedna pod drugą; 1 tylko tam, gdzie obie mają 1.', andWalk(a, m));
}
ex('Zamień na hex: 192, 168, 255, 10. Następnie zamień 0xAC i 0x7F na dziesiętny.', 'Podziel bajt na dwa nibble po 4 bity; każdy nibble to jedna cyfra 0–F.',
  [192, 168, 255, 10].map((v) => `${v} = ${bin8(v).slice(0, 4)} ${bin8(v).slice(4)} = 0x${hex2(v)}`).join('\n') + '\n0xAC = 10·16 + 12 = 172\n0x7F = 7·16 + 15 = 127');
ex('Adres MAC kontenera Dockera to 02:42:ac:11:00:03. Ostatnie 4 bajty Docker generuje z adresu IP kontenera. Jaki to adres IP?',
  'Zamień każdy bajt hex na dziesiętny.', '0xac = 172, 0x11 = 17, 0x00 = 0, 0x03 = 3 → 172.17.0.3 (domyślna sieć bridge 172.17.0.0/16).');
ex('Ile różnych wartości można zapisać na 12 bitach i gdzie w sieciach spotkasz pole 12-bitowe?', '2^12.',
  '2^12 = 4 096 wartości (0–4 095). To pole VLAN ID w tagu 802.1Q; wartości 0 i 4 095 są zarezerwowane, więc użytecznych VLAN-ów jest 4 094.');
ex('Napisz metodę w Javie String toBin8(int octet), która zawsze zwraca 8 znaków (np. 5 → "00000101"), oraz metodę int fromBin(String s).',
  'Integer.toBinaryString nie dopełnia zer; Integer.parseInt przyjmuje podstawę systemu.',
  'static String toBin8(int octet) {\n    String s = Integer.toBinaryString(octet & 0xFF);\n    return "0".repeat(8 - s.length()) + s;\n}\n// alternatywa: String.format("%8s", Integer.toBinaryString(octet & 0xFF)).replace(\' \', \'0\')\n\nstatic int fromBin(String s) {\n    return Integer.parseInt(s, 2);\n}');
ex('Dlaczego poniższy kod wypisuje -64 zamiast 192 i jak to naprawić?\nint ip = (192 << 24) | (168 << 16) | (1 << 8) | 77;\nSystem.out.println(ip >> 24);',
  'Jaki jest najstarszy bit tej liczby i co robi z nim operator >>?',
  'Najstarszy bit to 1, więc int jest ujemny. >> (przesunięcie arytmetyczne) powiela bit znaku: wynik 11111111…11000000 = -64.\nPoprawnie: (ip >>> 24) albo (ip >> 24) & 0xFF → 192.');
ex('Defensywnie: na liście blokad proxy jest wpis „169.254.169.254” (endpoint metadanych chmury). Podaj dwa inne zapisy tego samego adresu, które mógłby przepuścić filtr porównujący tekst, i zaproponuj poprawną kontrolę.',
  'Zamień adres na jedną liczbę dziesiętną i na hex.',
  `Jako jedna liczba: ${ipToInt('169.254.169.254')} (http://${ipToInt('169.254.169.254')}/)\nW hex: 0xA9FEA9FE (http://0xA9FEA9FE/)\nPoprawna kontrola: rozwiąż nazwę/adres do InetAddress, sprawdź isLinkLocalAddress() (169.254.0.0/16) lub porównaj (ip & maska) z zakazanymi prefiksami, a połącz się dokładnie z tym sprawdzonym adresem. W chmurze dodatkowo wymuś IMDSv2 (AWS), który wymaga tokenu.`);
ex('Rozpisz pierwszy bajt nagłówka IPv4 0x45 na bity i zinterpretuj: górne 4 bity to wersja, dolne 4 to IHL (długość nagłówka w słowach 32-bitowych).',
  '0x45 → dwa nibble: 4 i 5.',
  '0x45 = 0100 0101\nwersja = 0100 = 4 (IPv4)\nIHL = 0101 = 5 słów × 4 bajty = 20 bajtów — nagłówek bez opcji.');
if (exercises.length !== 30) throw new Error('zadania: ' + exercises.length);

// ---------- QUIZ ----------
const quiz = [];
const add = (...a) => quiz.push(q(...a));

add('Ile bitów ma adres IPv4?', '32', ['8', '64', '128'], '4 oktety × 8 bitów = 32 bity. IPv6 ma 128 bitów.');
add('Dlaczego dokumenty RFC używają słowa „oktet” zamiast „bajt”?', 'Bo oktet to zawsze dokładnie 8 bitów, a historycznie bajt bywał innej długości', ['Bo oktet ma 16 bitów', 'Bo bajt dotyczy tylko pamięci RAM', 'To synonim wprowadzony przez IEEE dla Wi-Fi'], 'Na starych architekturach bajt miał np. 6, 7 lub 9 bitów. „Oktet” jest jednoznaczny.');
add('Jaki jest zakres wartości jednego oktetu?', '0–255', ['1–256', '0–256', '0–127'], '8 bitów = 2^8 = 256 wartości: od 00000000 (0) do 11111111 (255).');
add('Jakie są wagi bitów w oktecie, od lewej?', '128, 64, 32, 16, 8, 4, 2, 1', ['1, 2, 4, 8, 16, 32, 64, 128', '256, 128, 64, 32, 16, 8, 4, 2', '100, 50, 25, 12, 6, 3, 2, 1'], 'Najstarszy bit (MSB) jest po lewej i ma wagę 2^7 = 128.');
add('Ile bitów reprezentuje jedna cyfra szesnastkowa?', '4', ['8', '2', '16'], 'Cyfry 0–F to 16 wartości = 2^4. Dlatego bajt to dokładnie 2 cyfry hex, a konwersja hex ↔ bin jest natychmiastowa.');
add('Jak nazywa się grupa 4 bitów?', 'Nibble (półbajt)', ['Oktet', 'Słowo', 'Kwartet'], 'Nibble = 4 bity = jedna cyfra hex.');
add('Co oznacza „network byte order”?', 'Najstarszy bajt liczby przesyłany jest jako pierwszy (big-endian)', ['Najmłodszy bajt jest przesyłany pierwszy (little-endian)', 'Bajty są sortowane rosnąco', 'Kolejność ustala router'], 'Protokoły internetowe używają big-endian. x86 trzyma liczby jako little-endian, stąd htons/ntohl w C. ByteBuffer w Javie domyślnie jest big-endian.');
add('Jak rozpoznać po zapisie dwójkowym, że liczba jest nieparzysta?', 'Ostatni (najmłodszy) bit ma wartość 1', ['Pierwszy bit ma wartość 1', 'Ma nieparzystą liczbę jedynek', 'Kończy się na 0'], 'Tylko bit o wadze 1 może dać nieparzystą sumę; wszystkie inne wagi są parzyste.');
add('Jaki skutek ma przesunięcie liczby dwójkowej o jedną pozycję w lewo (x << 1)?', 'Mnoży ją przez 2', ['Dzieli ją przez 2', 'Dodaje 1', 'Odwraca wszystkie bity'], 'Każdy bit trafia na pozycję o dwukrotnie większej wadze.');
add('Jaki wynik daje AND dla bitów 1 i 0?', '0', ['1', 'Zależy od pozycji', 'Błąd'], 'AND daje 1 wyłącznie dla pary 1 i 1.');
add('Która operacja logiczna służy do wyznaczenia adresu sieci z adresu IP i maski?', 'AND', ['OR', 'XOR', 'NOT'], 'Jedynki maski przepuszczają bity adresu, zera je zerują. Broadcast powstaje przez OR z negacją maski.');
add('Którą operacją uzyskasz maskę wildcard z maski podsieci?', 'NOT (odwrócenie bitów), czyli 255 − oktet', ['AND z 255', 'OR z maską', 'Przesunięcie w lewo'], 'Wildcard to negacja maski: dla 255.255.255.0 → 0.0.0.255.');
add('XOR dwóch bitów daje 1, gdy:', 'Bity są różne', ['Oba są równe 1', 'Oba są równe 0', 'Pierwszy bit to 1'], 'XOR = „albo-albo”. Wykorzystuje się go m.in. w sumach kontrolnych i porównywaniu wzorców bitów.');
add('Co zwraca Integer.toBinaryString(5) w Javie?', '"101"', ['"00000101"', '"0b101"', '"5"'], 'Metoda nie dopełnia zer wiodących — trzeba to zrobić samemu, np. "0".repeat(8 - s.length()) + s.');
add('Co wypisze System.out.println((byte) 192)?', '-64', ['192', '64', '-192'], 'byte w Javie ma zakres -128..127. 11000000 jako liczba ze znakiem (U2) to -64. Dlatego bajty adresu odczytuje się przez & 0xFF.');
add('Po co w Javie pisze się b & 0xFF przy odczycie bajtów adresu IP?', 'Aby zamienić bajt ze znakiem (-128..127) na wartość 0..255', ['Aby wyzerować adres', 'Aby zamienić bajt na hex', 'Aby odwrócić kolejność bajtów'], 'b jest promowany do int z rozszerzeniem znaku; AND z 0xFF zostawia tylko 8 najmłodszych bitów.');
add('Czym różni się >>> od >> w Javie?', '>>> wpycha z lewej zera, >> powiela bit znaku', ['>>> przesuwa o 3 pozycje', '>> działa tylko na long', 'Niczym — to synonimy'], 'Dla adresu 192.x.x.x jako int (liczba ujemna) ip >> 24 daje -64, a ip >>> 24 daje 192.');
add('Co zwraca Integer.parseInt("11111111", 2)?', '255', ['11111111', '-1', '256'], 'Drugi argument to podstawa systemu liczbowego.');
add('Co zwraca Integer.bitCount(0xFFFFFF00)?', '24', ['32', '8', '255'], 'Liczba jedynek w masce = długość prefiksu. 0xFFFFFF00 to 255.255.255.0 = /24.');
add('Ile wynosi 0xFF dziesiętnie?', '255', ['256', '99', '15'], 'F = 15; 15·16 + 15 = 255.');
add('Ile wynosi 0x10 dziesiętnie?', '16', ['10', '2', '100'], '1·16 + 0 = 16. W hex „10” to szesnastka.');
add('Ile bitów ma adres MAC?', '48', ['32', '64', '128'], '6 bajtów × 8 = 48 bitów, zapisywanych jako 12 cyfr hex.');
add('Ile bitów ma jedna grupa w adresie IPv6 (np. 2001)?', '16', ['8', '4', '32'], '4 cyfry hex × 4 bity = 16 bitów; 8 grup × 16 = 128 bitów.');
add('Ile różnych numerów portów TCP może istnieć?', '65 536 (0–65 535)', ['65 535 (1–65 535)', '32 768', '1 024'], 'Pole portu ma 16 bitów → 2^16 = 65 536 wartości. Port 0 jest zarezerwowany i nie używa się go do połączeń.');
add('Pole TTL w IPv4 ma 8 bitów. Jaka jest jego maksymalna wartość?', '255', ['256', '128', '64'], '2^8 − 1 = 255. Typowe wartości początkowe to 64 (Linux), 128 (Windows), 255 (urządzenia sieciowe).');
add('Ile identyfikatorów VLAN można zakodować w 12-bitowym polu 802.1Q?', '4 096 (z czego 4 094 użyteczne)', ['1 024', '65 536', '256'], '2^12 = 4 096; wartości 0 i 4 095 są zarezerwowane.');
add('Ile wynosi 2^32?', '4 294 967 296', ['4 294 967 295', '2 147 483 648', '65 536'], 'To liczba wszystkich adresów IPv4 (~4,3 mld). Największy adres 255.255.255.255 to 2^32 − 1.');
add('Adres IPv4 przechowywany jako int w Javie dla 192.168.1.1 jest liczbą:', 'Ujemną, bo najstarszy bit jest równy 1', ['Dodatnią', 'Zawsze równą 0', 'Większą niż Integer.MAX_VALUE'], 'int jest ze znakiem; adresy od 128.0.0.0 wzwyż mają MSB = 1. Do wypisania użyj Integer.toUnsignedString lub long z & 0xFFFFFFFFL.');
add('Jaki adres IPv4 kryje się za zapisem http://2130706433/?', '127.0.0.1', ['192.168.1.1', '10.0.0.1', 'To niepoprawny adres'], '127·2^24 + 1 = 2 130 706 433. Wiele parserów akceptuje adres jako jedną liczbę — naiwne filtry tekstowe tego nie wykrywają.');
add('Jak poprawnie bronić się przed obejściem filtra SSRF zapisem adresu w hex lub jako liczba?', 'Sparsować adres do postaci kanonicznej (InetAddress) i sprawdzać zakresy na liczbach, łącząc się z tym samym sprawdzonym adresem', ['Dodać więcej wzorców tekstowych do czarnej listy', 'Zablokować znaki „0x” w URL', 'Sprawdzać tylko nazwę domeny'], 'Czarne listy tekstowe zawsze coś pominą (ósemkowy, liczba, IPv6-mapped). Decyzja musi zapadać na tej samej reprezentacji, której używa połączenie.');
add('Dlaczego adresu 010.0.0.1 nie należy wpisywać w konfiguracji?', 'Niektóre parsery traktują zero wiodące jako zapis ósemkowy i odczytają 8.0.0.1', ['Bo 010 to niepoprawny oktet', 'Bo routery wymagają trzech cyfr', 'Bo to adres broadcast'], 'inet_aton i część przeglądarek interpretuje 010 ósemkowo (= 8). Inne parsery odczytają 10 — niejednoznaczność to źródło błędów i luk.');
add('Pierwszy bajt nagłówka IPv4 w zrzucie to 0x45. Co oznacza cyfra 5?', 'Długość nagłówka: 5 słów po 32 bity = 20 bajtów', ['Wersja IP 5', '5 skoków TTL', 'Protokół numer 5'], 'Górny nibble (4) to wersja, dolny (5) to IHL w słowach 32-bitowych.');
add('chmod 750 w systemie Linux oznacza bity:', '111 101 000', ['111 110 000', '101 111 000', '111 101 101'], 'Każda cyfra ósemkowa to 3 bity: 7 = 111 (rwx), 5 = 101 (r-x), 0 = 000.');
add('Ile wartości ma oktet maski podsieci, jeśli jedynki muszą stać ciągiem od lewej?', '9', ['8', '256', '255'], '0, 128, 192, 224, 240, 248, 252, 254, 255 — od zera do ośmiu jedynek.');
add('Które wyrażenie w Javie sprawdza, czy adres ip należy do sieci network z maską mask?', '(ip & mask) == network', ['(ip | mask) == network', '(ip ^ mask) == network', 'ip > network'], 'Dokładnie ta sama operacja, której używa jądro przy wyborze trasy.');

// generowane: dec -> bin
const decs = [3, 12, 33, 64, 99, 127, 128, 150, 172, 192, 201, 224, 248, 252, 255];
for (const v of decs) {
  const b = bin8(v);
  const rev = b.split('').reverse().join('');
  const wrongs = [rev, bin8((v + 1) % 256), bin8(v ^ 0b00010000), bin8((v + 128) % 256)];
  add(`Jak zapisać liczbę ${v} na 8 bitach?`, b, wrongs, subtractWalk(v));
}
// bin -> dec
const bins = ['00000111', '00010100', '00101010', '01000000', '01010101', '01111111', '10000000', '10010110', '10111110', '11000011', '11001000', '11100000', '11110000', '11111000', '11111110'];
for (const b of bins) {
  const v = parseInt(b, 2);
  add(`Jaka liczba dziesiętna odpowiada zapisowi ${b}?`, String(v), [String(v + 1), String(255 - v), String(parseInt(b.split('').reverse().join(''), 2)), String(v + 2)], sumWalk(b));
}
// potegi dwojki
for (const n of [4, 6, 9, 10, 12, 16, 20, 24]) {
  add(`Ile wynosi 2^${n}?`, fmt(2 ** n), [fmt(2 ** n - 1), fmt(2 ** (n + 1)), fmt(2 ** (n - 1)), fmt(n * 2)], `2^${n} = ${fmt(2 ** n)}. Każdy bit podwaja liczbę wartości.`);
}
// AND
const ands = [[37, 248], [200, 192], [130, 224], [77, 240], [255, 128], [99, 252], [170, 15], [168, 248], [45, 224], [190, 254], [63, 192], [7, 248]];
for (const [a, m] of ands) {
  const r = a & m;
  add(`Ile wynosi ${a} AND ${m}?`, String(r), [String(a | m), String(a ^ m), String(Math.min(255, r + (256 - m))), String(a), String(m), String(Math.abs(a - m)), String(r >> 1)], andWalk(a, m));
}
// hex
for (const v of [10, 15, 127, 172, 192, 254]) {
  const h = hex2(v);
  add(`Jak zapisać ${v} w systemie szesnastkowym?`, `0x${h}`, [`0x${v}`, `0x${hex2(255 - v)}`, `0x${h[1]}${h[0]}`, `0x${hex2((v + 16) % 256)}`],
    `${v} = ${bin8(v).slice(0, 4)} ${bin8(v).slice(4)} → ${h[0]} i ${h[1]} → 0x${h}.`);
}
for (const h of ['1F', '80', 'C0', 'A8', 'FE']) {
  const v = parseInt(h, 16);
  add(`Ile wynosi 0x${h} dziesiętnie?`, String(v), [String(Number(h) || v + 1), String(v - 1), String(255 - v), String(v + 16)], `${h[0]}·16 + ${h[1]} → ${parseInt(h[0], 16)}·16 + ${parseInt(h[1], 16)} = ${v}; dwójkowo ${bin8(v)}.`);
}
// liczba jedynek / prefiks
for (const v of [192, 240, 252, 254]) {
  add(`Ile jedynek ma oktet maski ${v}?`, String(ones(v)), [String(ones(v) + 1), String(ones(v) - 1), String(ones(v) + 2)], `${v} = ${bin8(v)} → ${ones(v)} jedynek.`);
}
if (quiz.length !== 100) throw new Error('quiz: ' + quiz.length);

fs.writeFileSync(out, JSON.stringify({ theory, exercises, quiz }, null, 2) + '\n');
console.log('theory', theory.length, 'exercises', exercises.length, 'quiz', quiz.length);
