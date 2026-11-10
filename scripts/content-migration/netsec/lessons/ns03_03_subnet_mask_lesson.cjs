// Generuje lekcje _ns_03_ip_i_routing/03_SubnetMaskFromZero.json (teoria, 30 zadan, 100 pytan liczonych skryptem).
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_03_subnet_mask_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/03_SubnetMaskFromZero.json
const fs = require('fs');
const out = process.argv[2];

// ---------- narzedzia ----------
const ipToInt = (ip) => ip.split('.').reduce((a, o) => (a * 256) + Number(o), 0);
const intToIp = (n) => [24, 16, 8, 0].map((s) => Math.floor(n / 2 ** s) % 256).join('.');
const maskInt = (p) => (p === 0 ? 0 : (2 ** 32 - 2 ** (32 - p)));
const maskStr = (p) => intToIp(maskInt(p));
const and = (a, b) => { let r = 0; for (let i = 31; i >= 0; i--) { const bit = 2 ** i; const x = Math.floor(a / bit) % 2, y = Math.floor(b / bit) % 2; if (x && y) r += bit; } return r; };
const netOf = (ip, p) => and(ipToInt(ip), maskInt(p));
const bcOf = (ip, p) => netOf(ip, p) + 2 ** (32 - p) - 1;
const hosts = (p) => (p >= 31 ? (p === 31 ? 2 : 1) : 2 ** (32 - p) - 2);
const bin8 = (n) => n.toString(2).padStart(8, '0');
const binIp = (ip) => ip.split('.').map((o) => bin8(Number(o))).join('.');
const fmt = (n) => n.toLocaleString('pl-PL').replace(/ /g, ' ');
const octIdx = (p) => Math.min(3, Math.floor(p / 8)); // oktet "ciekawy"
const magic = (p) => 256 - Number(maskStr(p).split('.')[octIdx(p)]);

let seed = 20261110;
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
function walk(ip, p) {
  const m = maskStr(p), n = intToIp(netOf(ip, p)), b = intToIp(bcOf(ip, p));
  return `IP      ${binIp(ip)}  (${ip})\nmaska   ${binIp(m)}  (${m} = /${p})\nAND  →  ${binIp(n)}  (${n})  ← adres sieci\nbroadcast: bity hosta na 1 → ${b}`;
}

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Jedno pytanie, na które odpowiada maska', code: null, body:
`Zanim komputer wyśle jakikolwiek pakiet IP, musi rozstrzygnąć jedną rzecz: **czy odbiorca jest w mojej sieci lokalnej, czy muszę oddać pakiet bramie (routerowi)?** Jeśli w tej samej — wysyła ramkę prosto do odbiorcy (po jego MAC z ARP). Jeśli w innej — wysyła ją do *default gateway*.

Do tej decyzji służy **maska podsieci**. To nie jest „jeszcze jeden adres” ani tajemnicza liczba z konfiguracji routera. To **linijka**, która mówi: *„tyle pierwszych bitów adresu to numer sieci, reszta to numer hosta”*.

W tej lekcji przejdziemy od zera: zobaczysz maskę bit po bicie, policzysz adres sieci operacją AND, nauczysz się liczyć broadcast i liczbę hostów oraz poznasz metodę **„magicznej liczby”**, dzięki której większość zadań policzysz w głowie w 10 sekund. Lekcja zakłada, że znasz zamianę liczb na system dwójkowy z lekcji poprzedniej.` },

  { type: 'DEFINITION', heading: 'Maska, prefiks, część sieci i część hosta', code: null, body:
`- **Adres IPv4** — 32 bity zapisane jako 4 oktety dziesiętnie, np. \`192.168.1.77\`.
- **Maska podsieci** — też 32 bity, ale o ścisłej budowie: **najpierw same jedynki, potem same zera**, bez mieszania. Jedynki oznaczają *część sieci*, zera — *część hosta*.
- **Prefiks (notacja CIDR)** — liczba jedynek w masce zapisana po ukośniku: \`/24\` = 24 jedynki = \`255.255.255.0\`.
- **Adres sieci** — adres IP z wyzerowanymi bitami hosta (= IP **AND** maska). Identyfikuje całą podsieć, nie przydziela się go hostowi.
- **Adres rozgłoszeniowy (broadcast)** — wszystkie bity hosta ustawione na 1. Ostatni adres w podsieci.
- **Zakres hostów** — wszystko między adresem sieci a broadcastem: od *sieć + 1* do *broadcast − 1*.
- **Liczba hostów** — \`2^(bity hosta) − 2\` (odejmujemy adres sieci i broadcast). Wyjątki: \`/31\` (2 adresy na łączu punkt-punkt, RFC 3021) i \`/32\` (pojedynczy host).
- **Oktet maski może mieć tylko 9 wartości:** \`0, 128, 192, 224, 240, 248, 252, 254, 255\` — bo jedynki muszą stać ciągiem od lewej.` },

  { type: 'ANALOGY', heading: 'Numer telefonu z kierunkowym', code: null, body:
`Pomyśl o numerze \`22 555 12 34\`. Pierwsze cyfry (\`22\`) to **kierunkowy** — mówi, *w jakim mieście* jest telefon. Reszta to **numer abonenta w tym mieście**. Centrala patrzy na kierunkowy: jeśli jest taki sam jak jej własny — łączy lokalnie; jeśli inny — przekazuje rozmowę dalej, do centrali międzymiastowej.

Maska to informacja, **ile cyfr stanowi kierunkowy**. \`/24\` mówi: „pierwsze 24 bity to kierunkowy (sieć), ostatnie 8 to abonent (host)”. Komputer porównuje *kierunkowy* swój i odbiorcy:
- taki sam → dzwoni bezpośrednio (ta sama sieć, ARP i ramka do odbiorcy),
- inny → oddaje rozmowę „centrali międzymiastowej”, czyli **bramie domyślnej**.

Dłuższy kierunkowy (większy prefiks, np. \`/28\`) = mniejsze „miasto” z mniejszą liczbą abonentów. Krótszy (\`/16\`) = ogromne miasto z dziesiątkami tysięcy numerów.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Linijka maski: gdzie kończy się sieć, a zaczyna host', body:
`Ten sam adres \`192.168.1.77\` z trzema różnymi maskami. Pionowa kreska \`|\` to granica, którą wyznacza maska — wszystko po lewej to sieć, po prawej host:`,
    code:
`adres:       11000000.10101000.00000001.01001101   (192.168.1.77)

/24 maska:   11111111.11111111.11111111|00000000   255.255.255.0
             ───────────── sieć ───────|─ host ─
             sieć 192.168.1.0  broadcast 192.168.1.255  hosty 254

/26 maska:   11111111.11111111.11111111.11|000000   255.255.255.192
             ─────────────── sieć ─────────|─host─
             sieć 192.168.1.64 broadcast 192.168.1.127  hosty 62

/20 maska:   11111111.11111111.1111|0000.00000000   255.255.240.0
             ──────── sieć ────────|──── host ────
             sieć 192.168.0.0  broadcast 192.168.15.255  hosty 4094

Im dalej w prawo przesuwasz kreskę (większe /N), tym mniejsza podsieć.` },

  { type: 'CODE_BASIC', heading: 'Adres sieci = IP AND maska — ręcznie, bit po bicie', body:
`AND daje 1 tylko wtedy, gdy **oba** bity są 1. Jedynki maski „przepuszczają” bity adresu, zera je zerują. Liczymy \`10.4.37.200/21\`:`,
    code: walk('10.4.37.200', 21) + `

Krok po kroku dla ciekawego (trzeciego) oktetu:
  37  = 00100101
  248 = 11111000   (maska /21 → 8+8+5 jedynek → trzeci oktet: 5 jedynek)
  AND = 00100000 = 32

Wynik: sieć 10.4.32.0/21, hosty 10.4.32.1 – 10.4.39.254, broadcast 10.4.39.255` },

  { type: 'CODE_PRACTICAL', heading: 'To samo w Javie i w narzędziach', body:
`Kalkulator podsieci w kilku linijkach Javy — dokładnie ta sama operacja AND, którą robi jądro systemu przy każdym pakiecie. Obok: jak sprawdzić wynik narzędziami.`,
    code:
`import java.net.InetAddress;
import java.nio.ByteBuffer;

public class SubnetCalc {
    static int toInt(String ip) throws Exception {
        return ByteBuffer.wrap(InetAddress.getByName(ip).getAddress()).getInt();
    }
    static String toIp(int v) {
        return ((v >>> 24) & 255) + "." + ((v >>> 16) & 255) + "." + ((v >>> 8) & 255) + "." + (v & 255);
    }
    public static void main(String[] args) throws Exception {
        int ip = toInt("10.4.37.200");
        int prefix = 21;
        int mask = prefix == 0 ? 0 : -1 << (32 - prefix);   // 21 jedynek z lewej
        int network = ip & mask;                            // AND
        int broadcast = network | ~mask;                    // bity hosta na 1
        long hosts = (1L << (32 - prefix)) - 2;
        System.out.println("maska     " + toIp(mask));       // 255.255.248.0
        System.out.println("sieć      " + toIp(network));    // 10.4.32.0
        System.out.println("broadcast " + toIp(broadcast));  // 10.4.39.255
        System.out.println("hosty     " + hosts);            // 2046
    }
}

# Linux:   ipcalc 10.4.37.200/21      |  ip -4 addr show   (maska jako /N)
# Windows: ipconfig                   (maska dziesiętnie)  |  Get-NetIPAddress (PrefixLength)` },

  { type: 'STEP_BY_STEP', heading: 'Metoda magicznej liczby — liczenie w głowie', code: null, body:
`Ręczne AND jest pewne, ale wolne. Na egzaminie i w pracy używa się skrótu:

1. **Znajdź ciekawy oktet** — ten, w którym maska nie jest ani 255, ani 0. Dla \`/21\` to trzeci oktet (8+8 = 16 bitów zajmują pierwsze dwa, pozostałe 5 jedynek trafia do trzeciego).
2. **Wartość maski w tym oktecie** — 5 jedynek \`11111000\` = **248**. (Zapamiętaj ciąg: 1→128, 2→192, 3→224, 4→240, 5→248, 6→252, 7→254, 8→255.)
3. **Magiczna liczba = 256 − 248 = 8.** To *rozmiar bloku* — podsieci zaczynają się co 8 w ciekawym oktecie: 0, 8, 16, 24, 32, 40…
4. **Znajdź wielokrotność ≤ wartości adresu.** Adres ma w trzecim oktecie 37 → największa wielokrotność 8 nie większa niż 37 to **32**. Sieć: \`10.4.32.0\`.
5. **Broadcast = następny blok − 1** w ciekawym oktecie, a oktety na prawo = 255: następny blok to 40 → broadcast \`10.4.39.255\`.
6. **Oktety na lewo** od ciekawego przepisujesz bez zmian, **na prawo** — w adresie sieci 0, w broadcaście 255.
7. **Hosty:** \`2^(32 − 21) − 2 = 2048 − 2 = 2046\`.

Sprawdzenie: wynik zgadza się z AND z poprzedniego bloku. Po kilkunastu zadaniach liczysz tak każdy adres w 10 sekund.` },

  { type: 'NOTE', heading: 'Tabela, którą warto mieć w głowie', body:
`Nie musisz uczyć się całej tabeli na pamięć — wystarczy ciąg wartości oktetu i potęgi dwójki. Ale te wiersze pojawiają się w pracy tak często, że i tak je zapamiętasz:`,
    code:
`/N   maska              blok   hosty        typowe użycie
/8   255.0.0.0          256    16 777 214   10.0.0.0/8 (prywatna)
/12  255.240.0.0        16     1 048 574    172.16.0.0/12 (prywatna)
/16  255.255.0.0        256    65 534       VPC AWS, sieć Dockera
/20  255.255.240.0      16     4 094        domyślne podsieci w AWS
/22  255.255.252.0      4      1 022        duży segment biurowy
/24  255.255.255.0      256    254          LAN domowy, „klasyczna” sieć
/25  255.255.255.128    128    126
/26  255.255.255.192    64     62
/27  255.255.255.224    32     30
/28  255.255.255.240    16     14           mała podsieć w chmurze
/29  255.255.255.248    8      6
/30  255.255.255.252    4      2            stare łącza punkt-punkt
/31  255.255.255.254    2      2*           łącza punkt-punkt (RFC 3021)
/32  255.255.255.255    1      1            pojedynczy host, trasa do hosta
(blok = magiczna liczba w ciekawym oktecie; * bez adresu sieci i broadcastu)` },

  { type: 'USAGE', heading: 'Gdzie spotkasz maskę jako backend developer', code: null, body:
`- **Docker i Compose** — sieci mają zakresy typu \`172.18.0.0/16\`; konflikt z siecią VPN (też \`172.x\`) to klasyczny powód „po włączeniu VPN kontenery przestają działać”.
- **AWS VPC** — projektujesz \`10.20.0.0/16\` i dzielisz na podsieci \`/24\` per strefa dostępności; AWS rezerwuje w każdej 5 adresów, więc \`/28\` daje 11 użytecznych, nie 14.
- **Security Groups, firewalle, nftables** — reguła \`allow 10.0.0.0/8\` wpuszcza 16 milionów adresów; \`203.0.113.10/32\` dokładnie jeden. Pomyłka w prefiksie = otwarta lub zamknięta usługa.
- **Kubernetes** — osobne zakresy dla Podów (\`--pod-network-cidr\`) i Service'ów; nie mogą nakładać się z siecią węzłów.
- **Spring i proxy** — listy zaufanych proxy (\`server.tomcat.remoteip.internal-proxies\`), \`pg_hba.conf\` w PostgreSQL (\`host all all 10.0.0.0/16 scram-sha-256\`), allow-listy w Spring Security (\`hasIpAddress("192.168.1.0/24")\`).
- **Diagnoza** — „ping do sąsiada nie działa”: najpierw sprawdź, czy oba hosty mają tę samą maskę i są w tej samej podsieci.` },

  { type: 'PITFALL', heading: 'Najczęstsze nieporozumienia', code: null, body:
`- **„/24 oznacza 24 hosty”** — nie. \`/24\` to 24 bity *sieci*; na hosty zostaje 8 bitów → 254 hosty.
- **„Większy prefiks = większa sieć”** — odwrotnie. \`/28\` (14 hostów) jest dużo mniejsza od \`/16\` (65 534).
- **„Maska to adres”** — \`255.255.255.0\` nie wskazuje żadnego hosta; to wzorzec bitów.
- **Maska z „dziurą”** — \`255.255.0.255\` czy \`255.255.255.100\` nie istnieją; oktet maski musi być jedną z 9 wartości.
- **Zapominanie o −2** — \`/26\` ma 64 adresy, ale 62 hosty. W chmurze odejmij jeszcze adresy zarezerwowane przez dostawcę.
- **Liczenie od oktetu z 255** — magiczną liczbę liczysz w *ciekawym* oktecie, nie w ostatnim. Dla \`/20\` blok to 16 w trzecim oktecie, a nie coś w czwartym.
- **Różne maski po dwóch stronach** — host A z \`/24\` i host B z \`/25\` mogą mieć różne zdanie o tym, czy są „w tej samej sieci” → komunikacja działa w jedną stronę.` },

  { type: 'CODE_WRONG', heading: 'Źle: reguła, która otwiera więcej, niż myślisz', body:
`Administrator chce wpuścić do PostgreSQL tylko serwer aplikacji \`10.0.5.17\` i wpisuje prefiks „na oko”:`,
    code:
`# pg_hba.conf
host  all  all  10.0.5.17/16   scram-sha-256

# PostgreSQL wylicza z tego sieć 10.0.0.0/16 (bity hosta są ignorowane)
# → dostęp ma 65 534 adresów: 10.0.0.1 – 10.0.255.254,
#   w tym każdy kontener, VPN i maszyna deweloperska w tej VPC.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: prefiks dopasowany do intencji', body:
`Jeden serwer — \`/32\`. Cała podsieć aplikacji — jej dokładny prefiks, sprawdzony kalkulatorem:`,
    code:
`# pg_hba.conf
host  all  all  10.0.5.17/32   scram-sha-256   # dokładnie jeden host
host  all  all  10.0.5.0/28    scram-sha-256   # 10.0.5.1 – 10.0.5.14 (podsieć aplikacji)

# Weryfikacja przed wdrożeniem:
$ ipcalc 10.0.5.0/28
Network:   10.0.5.0/28
HostMin:   10.0.5.1
HostMax:   10.0.5.14
Broadcast: 10.0.5.15` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy liczyć w głowie, a kiedy narzędziem', code: null, body:
`- **W głowie (magiczna liczba):** szybka diagnoza („czy te dwa adresy są w jednej podsieci?”), rozmowa rekrutacyjna, egzamin CompTIA Network+/CCNA, przegląd reguł firewalla.
- **Ręcznie bit po bicie (AND):** gdy uczysz się lub wynik z głowy budzi wątpliwość — to jest „dowód” poprawności.
- **Narzędziem (\`ipcalc\`, \`sipcalc\`, kalkulator CIDR, \`InetAddress\` w Javie):** projekt adresacji VPC, reguły produkcyjne, zakresy w Terraform — wszędzie, gdzie pomyłka kosztuje. Narzędzie liczy; ty musisz rozumieć, *czy wynik ma sens*.
- **Kod (operacje bitowe):** walidacja adresów w aplikacji, allow-listy IP, ochrona przed SSRF (sprawdzanie, czy adres należy do zakresów prywatnych).` },

  { type: 'SUMMARY', heading: 'Podsumowanie', code: null, body:
`- Maska odpowiada na pytanie **„w mojej sieci czy przez bramę?”** — dzieli 32 bity adresu na część sieci (jedynki) i hosta (zera).
- \`/N\` = liczba jedynek; oktet maski ma tylko wartości \`0, 128, 192, 224, 240, 248, 252, 254, 255\`.
- **Adres sieci = IP AND maska**, **broadcast** = bity hosta na 1, **hosty** = \`2^(32−N) − 2\`.
- **Magiczna liczba** = 256 − wartość maski w ciekawym oktecie = rozmiar bloku; sieć to największa wielokrotność bloku ≤ wartości adresu.
- Większy prefiks = **mniejsza** sieć. \`/32\` to jeden host, \`/0\` to cały Internet.
- Złe maski w regułach dostępu (pg_hba.conf, Security Groups, firewall) to realne luki bezpieczeństwa — zawsze weryfikuj zakres.
- Następna lekcja: **CIDR, VLSM i projektowanie podsieci** — dzielenie i łączenie sieci.` },
];

const IMAGES = {
  INTRO: ['netsec/mask-decision.svg', 'Host liczy adres sieci swój i celu: równe — ARP i ramka bezpośrednio do odbiorcy; różne — ramka do bramy domyślnej.'],
  VISUAL_EXAMPLE: ['netsec/mask-ruler.svg', 'Adres 192.168.1.77 i maska /26 bit po bicie: 26 bitów sieci (niebieskie), 6 bitów hosta (pomarańczowe), wynik AND = 192.168.1.64.'],
  STEP_BY_STEP: ['netsec/mask-magic-number.svg', 'Oś trzeciego oktetu podzielona na bloki po 8: wartość 37 wpada w blok 32–39, więc sieć to 10.4.32.0, a broadcast 10.4.39.255.'],
  NOTE: ['netsec/mask-subnet-split.svg', 'Ta sama sieć /24 podzielona na 2, 4, 8 i 16 podsieci — każdy dodatkowy bit prefiksu dzieli bloki na pół.'],
};
for (const block of theory) { const im = IMAGES[block.type]; if (im) { block.image = im[0]; block.imageAlt = im[1]; } }

// ---------- ZADANIA ----------
const exercises = [];
const ex = (prompt, hint, solution) => exercises.push({ prompt, hint, solution });
ex('Zapisz maskę /24 w postaci dwójkowej i dziesiętnej. Ile bitów jest przeznaczonych na hosty?', 'Policz jedynki od lewej: 24 jedynki, potem zera do 32.', `${binIp(maskStr(24))} = ${maskStr(24)}\nBity hosta: 32 − 24 = 8 → 2^8 − 2 = 254 hosty.`);
ex('Wypisz wszystkie 9 możliwych wartości oktetu maski wraz z liczbą jedynek.', 'Dokładaj kolejne jedynki od lewej: 10000000, 11000000, ...', '0 jedynek → 0, 1 → 128, 2 → 192, 3 → 224, 4 → 240, 5 → 248, 6 → 252, 7 → 254, 8 → 255.');
const calcCases = [['192.168.1.77', 26], ['10.4.37.200', 21], ['172.16.45.9', 20], ['192.168.100.130', 25], ['10.10.10.10', 30], ['172.31.200.77', 19], ['192.168.7.33', 27], ['10.250.3.99', 22], ['100.64.12.200', 28], ['10.0.255.1', 23]];
for (const [ip, p] of calcCases) {
  ex(`Dla adresu ${ip}/${p} podaj: maskę dziesiętną, adres sieci, broadcast, pierwszy i ostatni host oraz liczbę hostów.`,
    `Ciekawy oktet: ${octIdx(p) + 1}. Magiczna liczba: 256 − ${maskStr(p).split('.')[octIdx(p)]} = ${magic(p)}.`,
    `maska ${maskStr(p)}\nsieć ${intToIp(netOf(ip, p))}\nbroadcast ${intToIp(bcOf(ip, p))}\nhosty ${intToIp(netOf(ip, p) + 1)} – ${intToIp(bcOf(ip, p) - 1)}\nliczba hostów ${fmt(hosts(p))}\n\n${walk(ip, p)}`);
}
const samePairs = [['192.168.1.10', '192.168.1.200', 24], ['192.168.1.10', '192.168.1.200', 25], ['10.1.4.1', '10.1.7.254', 22], ['10.1.4.1', '10.1.8.1', 22], ['172.20.0.5', '172.20.0.20', 28]];
for (const [x, y, p] of samePairs) {
  const same = netOf(x, p) === netOf(y, p);
  ex(`Host A ma ${x}/${p}, host B ma ${y}/${p}. Czy A wyśle pakiet do B bezpośrednio, czy przez bramę? Uzasadnij.`,
    'Policz adres sieci dla obu adresów z tą samą maską i porównaj.',
    `Sieć A: ${intToIp(netOf(x, p))}, sieć B: ${intToIp(netOf(y, p))} → ${same ? 'ta sama sieć, A wysyła bezpośrednio (ARP o MAC hosta B).' : 'różne sieci, A wysyła pakiet do bramy domyślnej.'}`);
}
ex('Ile hostów pomieści podsieć /27? A podsieć /27 w AWS VPC?', 'AWS rezerwuje 5 adresów w każdej podsieci.', `/27: 2^5 = 32 adresy, 30 hostów.\nAWS: 32 − 5 = 27 użytecznych adresów (sieć, router VPC, DNS, adres zarezerwowany na przyszłość, broadcast).`);
ex('Jaki najmniejszy prefiks (największą maskę) wybierzesz dla podsieci, w której ma pracować 50 hostów?', 'Szukaj najmniejszego n, dla którego 2^n − 2 ≥ 50.', '2^6 − 2 = 62 ≥ 50, a 2^5 − 2 = 30 to za mało → 6 bitów hosta → /26 (255.255.255.192).');
ex('Jaki prefiks wybierzesz dla 500 hostów? Podaj maskę dziesiętną.', '2^9 = 512.', '2^9 − 2 = 510 ≥ 500 → 9 bitów hosta → /23 = 255.255.254.0.');
ex('Zamień maskę 255.255.240.0 na notację CIDR.', '255 = 8 jedynek, 240 = 11110000.', '8 + 8 + 4 + 0 = 20 → /20.');
ex('Czy 255.255.255.100 jest poprawną maską? Dlaczego?', 'Zapisz 100 dwójkowo.', '100 = 01100100 — jedynki nie stoją ciągiem od lewej. To nie jest poprawna maska (dozwolone tylko 0,128,192,224,240,248,252,254,255).');
ex('Napisz w Javie metodę boolean sameSubnet(String a, String b, int prefix), która sprawdza, czy dwa adresy IPv4 są w tej samej podsieci.', 'Zamień adresy na int (ByteBuffer), zbuduj maskę -1 << (32 - prefix) i porównaj a & mask z b & mask. Uważaj na prefix = 0.',
  'static boolean sameSubnet(String a, String b, int prefix) throws Exception {\n    int mask = prefix == 0 ? 0 : -1 << (32 - prefix);\n    return (toInt(a) & mask) == (toInt(b) & mask);\n}\n// toInt jak w bloku CODE_PRACTICAL');
ex('Napisz w Javie metodę String maskFromPrefix(int prefix), np. 21 → "255.255.248.0".', 'Przesunięcie -1 << (32 - prefix) i rozbicie na 4 bajty operatorem >>> i & 255.',
  'static String maskFromPrefix(int p) {\n    int m = p == 0 ? 0 : -1 << (32 - p);\n    return ((m >>> 24) & 255) + "." + ((m >>> 16) & 255) + "." + ((m >>> 8) & 255) + "." + (m & 255);\n}');
ex('Reguła Security Group pozwala na ruch z 10.0.0.0/8. Zespół chciał wpuścić tylko podsieć 10.0.12.0/24. Ile adresów zostało wpuszczonych niepotrzebnie?', '/8 = 2^24 adresów, /24 = 2^8.', `2^24 − 2^8 = ${fmt(2 ** 24 - 2 ** 8)} adresów zbyt dużo. Poprawna reguła: 10.0.12.0/24.`);
ex('Wyjaśnij własnymi słowami, dlaczego hosty z różnymi maskami w tym samym segmencie (A: 192.168.1.10/24, B: 192.168.1.200/25) mogą mieć problem z komunikacją.', 'Policz, jak każdy z hostów widzi drugiego.', 'A (/24) uważa B za sąsiada (sieć 192.168.1.0) i wysyła bezpośrednio. B (/25) jest w sieci 192.168.1.128 i uważa A (192.168.1.0/25) za obcego → odpowiada przez bramę. Ruch jest asymetryczny; zależnie od bramy i firewalla odpowiedź może nie dotrzeć.');
ex('Ile podsieci /26 zmieści się w jednej sieci /24? Wypisz ich adresy dla 192.168.50.0/24.', 'Blok /26 to 64 adresy.', '256 / 64 = 4 podsieci: 192.168.50.0/26, 192.168.50.64/26, 192.168.50.128/26, 192.168.50.192/26.');
ex('Podaj zakres adresów obejmowany przez 172.16.0.0/12 (prywatna klasa „B” z RFC 1918).', 'Ciekawy oktet: drugi, maska 240, blok 16.', 'Sieć 172.16.0.0, broadcast 172.31.255.255 → zakres 172.16.0.0 – 172.31.255.255. Dlatego 172.32.0.1 nie jest już adresem prywatnym.');
ex('Na hoście Linux uruchom ip -4 addr show i ipcalc dla swojego adresu (w WSL2 lub kontenerze). Zapisz maskę, sieć i broadcast, a potem sprawdź wynik ręcznie metodą magicznej liczby.', 'W kontenerze: docker run --rm -it alpine sh, następnie apk add ipcalc lub użyj ip addr.', 'Przykład: inet 172.17.0.2/16 → maska 255.255.0.0, sieć 172.17.0.0, broadcast 172.17.255.255, 65 534 hosty. Ręcznie: /16 kończy się dokładnie na granicy oktetu (255.255.0.0), więc nie ma ciekawego oktetu — przepisujesz dwa pierwsze oktety, reszta to 0 (sieć) lub 255 (broadcast).');
ex('Defensywnie: przejrzyj listę reguł dostępu (np. 0.0.0.0/0 na porcie 22, 10.0.0.0/8 na 5432, 192.168.1.50/32 na 8080, 172.16.0.0/12 na 6379) i dla każdej podaj liczbę dopuszczonych adresów oraz ocenę ryzyka.', 'Liczba adresów = 2^(32 − N). Zastanów się, czy usługa (SSH, PostgreSQL, Redis) powinna być dostępna z tak szerokiego zakresu.',
  `0.0.0.0/0 :22    → ${fmt(2 ** 32)} adresów — cały Internet do SSH: krytyczne, zamień na bastion/SSM lub konkretny /32.\n10.0.0.0/8 :5432 → ${fmt(2 ** 24)} adresów — baza dostępna z całej sieci prywatnej: zawęź do podsieci aplikacji.\n192.168.1.50/32 :8080 → 1 adres — OK, zgodne z zasadą najmniejszych uprawnień.\n172.16.0.0/12 :6379 → ${fmt(2 ** 20)} adresów — Redis z szerokiego zakresu: zawęź i włącz uwierzytelnianie.`);
if (exercises.length !== 30) throw new Error('zadania: ' + exercises.length);

// ---------- QUIZ ----------
const quiz = [];
const add = (...a) => quiz.push(q(...a));
add('Na jakie pytanie odpowiada host, używając maski podsieci przed wysłaniem pakietu?', 'Czy odbiorca jest w mojej sieci lokalnej, czy pakiet trzeba wysłać do bramy', ['Jaki jest adres MAC odbiorcy', 'Którym portem TCP wysłać dane', 'Czy odbiorca ma włączony firewall'], 'Maska pozwala policzyć adres sieci swój i odbiorcy. Taki sam → dostarczenie bezpośrednie (po ARP), inny → do default gateway. MAC ustala ARP, port — aplikacja.');
add('Jak zbudowana jest każda poprawna maska podsieci IPv4?', 'Ciąg jedynek od lewej, a po nim same zera', ['Dowolny układ jedynek i zer', 'Ciąg zer od lewej, a po nim same jedynki', 'Zawsze 24 jedynki i 8 zer'], 'Jedynki (część sieci) stoją ciągiem od najstarszego bitu, dalej wyłącznie zera (część hosta). Dlatego oktet maski ma tylko 9 możliwych wartości.');
add('Co oznacza zapis /24?', '24 pierwsze bity adresu to część sieci', ['Sieć ma 24 hosty', 'Sieć ma 24 podsieci', 'Ostatnie 24 bity to część sieci'], '/N to liczba jedynek w masce, czyli długość części sieci. /24 zostawia 8 bitów na hosty → 254 hosty.');
add('Która wartość NIE może wystąpić jako oktet maski?', '200', ['192', '248', '254'], 'Dozwolone: 0,128,192,224,240,248,252,254,255. 200 = 11001000 — jedynki nie tworzą ciągłego bloku od lewej.');
add('Jaką operację logiczną wykonuje się na adresie IP i masce, aby otrzymać adres sieci?', 'AND', ['OR', 'XOR', 'NOT'], 'AND przepuszcza bity adresu tam, gdzie maska ma 1, i zeruje bity hosta. Broadcast powstaje przez OR z negacją maski.');
add('Która sieć jest najmniejsza (ma najmniej adresów)?', '/29', ['/24', '/16', '/27'], 'Większy prefiks = mniej bitów hosta = mniejsza sieć. /29 ma 8 adresów (6 hostów).');
add('Dlaczego od liczby adresów w podsieci odejmuje się 2, licząc hosty?', 'Adres sieci i adres rozgłoszeniowy nie są przydzielane hostom', ['Dwa adresy zajmuje zawsze router i serwer DNS', 'Pierwszy i ostatni adres są zarezerwowane dla IPv6', 'Tak wymaga protokół DHCP'], 'Pierwszy adres (wszystkie bity hosta = 0) identyfikuje sieć, ostatni (wszystkie = 1) to broadcast. Router zajmuje zwykły adres hosta.');
add('Ile adresów zawiera podsieć /31 i do czego służy?', '2 adresy, łącze punkt-punkt między dwoma routerami (RFC 3021)', ['0 adresów — prefiks jest niepoprawny', '1 adres — trasa do pojedynczego hosta', '4 adresy — 2 hosty, sieć i broadcast'], 'Na łączu punkt-punkt broadcast nie ma sensu, więc RFC 3021 pozwala użyć obu adresów /31 dla dwóch końców łącza.');
add('Co oznacza prefiks /32 w regule firewalla?', 'Dokładnie jeden adres IP', ['Cały Internet', 'Całą sieć lokalną', 'Regułę bez znaczenia'], '/32 = wszystkie 32 bity są częścią „sieci”, więc zakres obejmuje jeden adres. /0 to odwrotność — wszystkie adresy.');
add('Co oznacza 0.0.0.0/0 w tablicy routingu lub regule Security Group?', 'Wszystkie adresy IPv4 (trasa domyślna / dowolne źródło)', ['Brak adresu', 'Tylko adres lokalny hosta', 'Sieć Dockera'], 'Zero bitów sieci → każdy adres pasuje. W routingu to default route, w SG — ruch z całego Internetu.');
add('Czym jest „magiczna liczba” przy liczeniu podsieci?', '256 minus wartość maski w ciekawym oktecie — rozmiar bloku podsieci', ['Liczba hostów w podsieci', 'Liczba jedynek w masce', 'Zawsze liczba 256'], 'Np. maska 255.255.255.224 → 256 − 224 = 32: podsieci zaczynają się co 32 (0, 32, 64…) w czwartym oktecie.');
add('Który oktet jest „ciekawy” dla maski /20?', 'Trzeci', ['Pierwszy', 'Drugi', 'Czwarty'], '/20 = 8 + 8 + 4: pierwsze dwa oktety maski to 255, trzeci 240 (4 jedynki), czwarty 0. Liczymy w trzecim.');
add('Host ma adres 192.168.1.10/24 i bramę 192.168.1.1. Wysyła pakiet do 192.168.2.5. Co się stanie?', 'Wyśle ramkę na MAC bramy 192.168.1.1, bo odbiorca jest w innej sieci', ['Wyśle ARP o MAC 192.168.2.5', 'Odrzuci pakiet, bo adres jest spoza sieci', 'Wyśle broadcast do całej sieci'], 'Sieć hosta: 192.168.1.0/24, odbiorcy: 192.168.2.0/24 → różne. Pakiet IP ma adres docelowy 192.168.2.5, ale ramka idzie do MAC bramy.');
add('Dlaczego w AWS podsieć /28 daje 11, a nie 14 użytecznych adresów?', 'AWS rezerwuje 5 adresów w każdej podsieci', ['AWS nie obsługuje broadcastu', 'Każda instancja potrzebuje dwóch adresów', 'Maska /28 w AWS ma inny rozmiar'], '16 adresów − 5 zarezerwowanych (sieć, router VPC, DNS, rezerwa, broadcast) = 11.');
add('Administrator wpisał w pg_hba.conf 10.0.5.17/16 chcąc wpuścić jeden serwer. Co faktycznie zostało dopuszczone?', 'Cała sieć 10.0.0.0/16 — 65 534 adresów', ['Tylko 10.0.5.17', 'Adresy 10.0.5.0 – 10.0.5.255', 'Nic — wpis jest błędny i ignorowany'], 'Bity hosta w zapisie są ignorowane — liczy się sieć 10.0.0.0/16. Dla jednego hosta właściwy jest /32.');
add('Host A: 192.168.1.10/24, host B: 192.168.1.200/25 w tym samym segmencie. Jak B widzi A?', 'Jako host w innej sieci — odpowiada przez bramę', ['Jako sąsiada w tej samej sieci', 'Nie widzi go wcale, bo maski muszą być równe', 'Jako adres rozgłoszeniowy'], 'B jest w 192.168.1.128/25 (zakres 128–255). A = 192.168.1.10 należy do 192.168.1.0/25 → dla B obca sieć. Niezgodne maski dają ruch asymetryczny.');
add('Która maska odpowiada /26?', '255.255.255.192', ['255.255.255.128', '255.255.255.224', '255.255.255.26'], '26 = 24 + 2 → czwarty oktet ma 2 jedynki: 11000000 = 192.');
add('Ile podsieci /27 mieści się w jednej sieci /24?', '8', ['4', '16', '27'], '/27 to blok 32 adresów; 256 / 32 = 8. Każdy dodatkowy bit prefiksu podwaja liczbę podsieci.');
add('Ile maksymalnie hostów potrzebujesz, by wybrać /25 zamiast /26?', 'Od 63 do 126 hostów', ['Od 31 do 62', 'Od 127 do 254', 'Dokładnie 64'], '/26 mieści 62 hosty, /25 — 126. Więcej niż 62 i do 126 → /25.');
add('Która operacja w Javie wyznacza maskę dla prefiksu p (1–32) jako int?', '-1 << (32 - p)', ['1 << p', '(1 << p) - 1', '-1 >>> p'], '-1 to 32 jedynki; przesunięcie w lewo o (32 − p) wpycha z prawej tyle zer, ile jest bitów hosta. Dla p = 0 trzeba obsłużyć osobny przypadek (maska 0).');
add('Co da wyrażenie network | ~mask w Javie?', 'Adres rozgłoszeniowy podsieci', ['Adres sieci', 'Pierwszy host', 'Maskę w zapisie dziesiętnym'], '~mask ma jedynki na bitach hosta; OR z adresem sieci ustawia je wszystkie na 1 → broadcast.');
add('Do czego służy maska wildcard (np. 0.0.0.255) w listach ACL routerów i OSPF?', 'To odwrócona maska: 0 = bit musi pasować, 1 = dowolny', ['To inna nazwa maski podsieci', 'Oznacza cały Internet', 'Określa numer portu'], 'Wildcard = 255.255.255.255 − maska. Dla /24 to 0.0.0.255. Pojawia się na egzaminach Network+ i w konfiguracji Cisco.');
add('Ile wynosi wildcard dla maski /20?', '0.0.15.255', ['0.0.0.255', '0.0.240.0', '255.255.240.0'], 'Maska 255.255.240.0; 255 − 240 = 15 w trzecim oktecie, 255 w czwartym → 0.0.15.255.');
add('Zakres 172.16.0.0/12 kończy się na adresie:', '172.31.255.255', ['172.16.255.255', '172.32.255.255', '172.255.255.255'], 'Drugi oktet, maska 240 → blok 16 → 16…31. Dlatego np. 172.32.0.1 jest już adresem publicznym.');
add('Która metoda pozwala najpewniej sprawdzić wynik liczenia podsieci z głowy?', 'Rozpisanie adresu i maski dwójkowo i wykonanie AND', ['Ping na adres sieci', 'Sprawdzenie tablicy ARP', 'Zgadywanie na podstawie klasy adresu'], 'AND bit po bicie to definicja adresu sieci — dowód poprawności. Klasy adresów są przestarzałe od 1993 r. (CIDR).');
add('Kontenery przestały działać po włączeniu firmowego VPN. Jaka jest częsta przyczyna związana z maskami?', 'Zakres sieci VPN nakłada się na zakres sieci Dockera (np. 172.16.0.0/12)', ['VPN zmienia maskę na /32', 'Docker nie obsługuje masek', 'VPN wyłącza broadcast'], 'Gdy trasy VPN obejmują 172.17.0.0/16 itp., system kieruje ruch do kontenerów w tunel. Rozwiązanie: inny zakres w daemon.json lub w compose.');

add('Dlaczego w tej samej sieci wszystkie hosty powinny mieć identyczną maskę?', 'Aby zgodnie rozstrzygały, kto jest sąsiadem, a kto jest za bramą — inaczej ruch staje się asymetryczny', ['Bo switch odrzuca ramki z inną maską', 'Bo maska jest wysyłana w każdym pakiecie IP', 'Bo DHCP nie działa przy różnych maskach'], 'Maska nie podróżuje w pakiecie — każdy host stosuje ją lokalnie. Różne maski = różne decyzje o dostarczeniu bezpośrednim lub przez bramę.');
add('Czy maska podsieci jest przesyłana w nagłówku pakietu IPv4?', 'Nie — jest tylko lokalną konfiguracją interfejsu i tras', ['Tak, w polu Options', 'Tak, zamiast pola TTL', 'Tylko w pakietach ICMP'], 'Nagłówek IPv4 ma adres źródłowy i docelowy, ale nie ma maski. Protokoły routingu (OSPF, BGP) ogłaszają prefiksy, lecz zwykły pakiet danych maski nie niesie.');
add('Ile adresów zawiera sieć /22?', '1 024', ['512', '2 048', '1 022'], '2^(32−22) = 2^10 = 1024 adresy, z czego 1022 to hosty.');
add('Który prefiks wybierzesz dla podsieci z 1000 hostami, marnując jak najmniej adresów?', '/22', ['/21', '/23', '/24'], '/23 mieści 510 hostów (za mało), /22 — 1022 (wystarczy). /21 to 2046 — niepotrzebnie dużo.');
add('Z punktu widzenia bezpieczeństwa: dlaczego reguły dostępu powinny używać możliwie najwęższego prefiksu?', 'Zgodnie z zasadą najmniejszych uprawnień — każdy zbędny adres w zakresie to potencjalne źródło nieautoryzowanego dostępu', ['Bo szerokie prefiksy spowalniają routing', 'Bo firewall obsługuje tylko /32', 'Bo szerokie prefiksy wyłączają szyfrowanie'], 'Zakres 10.0.0.0/8 zamiast konkretnej podsieci wpuszcza miliony adresów, w tym zainfekowane maszyny czy niezaufane środowiska. Wąski prefiks ogranicza powierzchnię ataku.');

// pytania generowane: maski, hosty, sieci, broadcast, ta sama sieć
const prefixes = [8, 12, 16, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30];
for (const p of prefixes) {
  const wrong = [maskStr(Math.min(32, p + 1)), maskStr(p - 1), maskStr(Math.min(32, p + 2))];
  if (p % 8 === 0) wrong[2] = maskStr(p + 4);
  add(`Jaka maska dziesiętna odpowiada prefiksowi /${p}?`, maskStr(p), wrong,
    `/${p} = ${p} jedynek: ${binIp(maskStr(p))} = ${maskStr(p)}.`);
}
for (const p of [9, 14, 17, 18, 20, 22, 25, 26, 27, 28, 29, 30]) {
  const h = hosts(p);
  add(`Ile hostów można zaadresować w podsieci /${p}?`, fmt(h), [fmt(2 ** (32 - p)), fmt(hosts(p + 1)), fmt(hosts(p - 1)), fmt(hosts(p - 2))],
    `Bity hosta: 32 − ${p} = ${32 - p}. 2^${32 - p} = ${fmt(2 ** (32 - p))} adresów, minus adres sieci i broadcast = ${fmt(h)}.`);
}
for (const m of [255.255, '255.255.248.0', '255.255.255.240', '255.255.252.0', '255.255.255.128', '255.224.0.0', '255.255.255.252', '255.255.192.0'].filter((x) => typeof x === 'string')) {
  const p = m.split('.').map(Number).reduce((a, o) => a + bin8(o).split('').filter((c) => c === '1').length, 0);
  add(`Maskę ${m} zapisz w notacji CIDR.`, `/${p}`, [`/${p - 1}`, `/${p + 1}`, `/${p + 2}`], `${binIp(m)} → ${p} jedynek → /${p}.`);
}
const netCases = [['192.168.1.77', 26], ['10.4.37.200', 21], ['172.16.45.9', 20], ['192.168.100.130', 25], ['10.10.10.10', 30], ['172.31.200.77', 19], ['192.168.7.33', 27], ['10.250.3.99', 22], ['100.64.12.200', 28], ['10.0.255.1', 23], ['192.168.33.250', 29], ['10.77.130.5', 17], ['172.20.15.100', 24], ['10.200.47.1', 18], ['192.168.200.66', 26]];
for (const [ip, p] of netCases) {
  const net = netOf(ip, p), b = 2 ** (32 - p);
  add(`Jaki jest adres sieci dla ${ip}/${p}?`, intToIp(net), [intToIp(net + b), intToIp(Math.max(0, net - b)), intToIp(bcOf(ip, p))],
    `Magiczna liczba: ${magic(p)} w oktecie ${octIdx(p) + 1}.\n${walk(ip, p)}`, `${ip}/${p}`);
}
for (const [ip, p] of netCases.slice(0, 10)) {
  const net = netOf(ip, p), b = 2 ** (32 - p), bc = bcOf(ip, p);
  add(`Jaki jest adres rozgłoszeniowy (broadcast) podsieci, do której należy ${ip}/${p}?`, intToIp(bc), [intToIp(net), intToIp(bc - 1), intToIp(bc + b)],
    `Sieć ${intToIp(net)}, rozmiar bloku ${fmt(b)} adresów → broadcast = sieć + ${fmt(b - 1)} = ${intToIp(bc)}.`);
}
const pairs = [['192.168.10.5', '192.168.10.129', 25], ['10.1.4.1', '10.1.7.254', 22], ['10.1.4.1', '10.1.8.1', 22], ['172.20.0.5', '172.20.0.20', 28], ['172.20.0.5', '172.20.0.14', 28], ['192.168.0.1', '192.168.15.200', 20], ['10.0.0.1', '10.0.0.6', 30], ['10.0.0.1', '10.0.0.2', 30], ['192.168.5.63', '192.168.5.64', 26], ['10.10.32.1', '10.10.63.254', 19]];
for (const [x, y, p] of pairs) {
  const same = netOf(x, p) === netOf(y, p);
  add(`Czy adresy ${x} i ${y} z maską /${p} należą do tej samej podsieci?`,
    same ? 'Tak — oba mają ten sam adres sieci' : 'Nie — mają różne adresy sieci',
    [same ? 'Nie — mają różne adresy sieci' : 'Tak — oba mają ten sam adres sieci', 'Nie da się tego określić bez bramy', 'Tylko jeśli oba hosty mają ten sam MAC'],
    `Sieć ${x}: ${intToIp(netOf(x, p))}; sieć ${y}: ${intToIp(netOf(y, p))} (maska ${maskStr(p)}).`);
}
if (quiz.length !== 100) throw new Error('quiz: ' + quiz.length);

fs.writeFileSync(out, JSON.stringify({ theory, exercises, quiz }, null, 2) + '\n');
console.log('theory', theory.length, 'exercises', exercises.length, 'quiz', quiz.length);
