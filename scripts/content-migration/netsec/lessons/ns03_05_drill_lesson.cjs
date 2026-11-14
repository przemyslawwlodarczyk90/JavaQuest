// Generuje lekcje _ns_03_ip_i_routing/05_SubnettingDrill.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_05_drill_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/05_SubnettingDrill.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const D = require('./ns03_05_drill_model.cjs');
const buildQuiz = require('./ns03_05_drill_quiz.cjs');
const { parse, split, prefixForHosts, summarize, overlaps, contains, vlsm, size, sub6, count6, net6, fromBig6, toBig6, rng, membership } = D;

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const fmt = (n) => n.toLocaleString('pl-PL');

// ---------- KARTA 40 ZADAN (deterministyczna) ----------
const r = rng(20261114);
const CARD = [];
for (let i = 0; i < 13; i++) { const m = membership(r); CARD.push([`Czy ${m.ip} należy do ${m.subnet}?`, m.inside ? `TAK (${parse(m.subnet).network}–${parse(m.subnet).broadcast})` : `NIE (zakres ${parse(m.subnet).network}–${parse(m.subnet).broadcast})`]); }
for (const c of ['10.44.77.130/20', '172.19.200.9/22', '192.168.90.77/26', '100.70.3.3/12', '10.8.9.10/29', '172.29.130.255/17']) { const p = parse(c); CARD.push([`Sieć i broadcast dla ${c}?`, `${p.network} – ${p.broadcast}`]); }
for (const p of [21, 25, 27, 19]) CARD.push([`Ile hostów w /${p}?`, fmt(size(p) - 2)]);
for (const h of [70, 450, 3000]) CARD.push([`Najmniejszy prefiks na ${h} hostów?`, `/${prefixForHosts(h)}`]);
for (const [a, b] of [['10.20.64.0/18', '10.20.100.0/22'], ['192.168.4.0/23', '192.168.6.0/24'], ['172.16.0.0/12', '172.32.0.0/16']]) CARD.push([`Czy ${a} i ${b} się nakładają?`, overlaps(a, b) ? 'TAK' : 'NIE']);
for (const [base, np, n] of [['10.20.0.0/16', 20, 7], ['192.168.8.0/22', 25, 5]]) CARD.push([`Podsieć nr ${n} (od 0) po podziale ${base} na /${np}?`, split(base, np)[n]]);
for (const set of [['10.0.8.0/24', '10.0.9.0/24', '10.0.10.0/24', '10.0.11.0/24'], ['172.16.16.0/21', '172.16.24.0/21'], ['192.168.2.0/24', '192.168.3.0/24', '192.168.4.0/24']]) { const s = summarize(set); CARD.push([`Agregat dla ${set.join(', ')}?`, `${s.cidr}${s.exact ? '' : ` (niedokładny: +${fmt(s.extra)} adresów)`}`]); }
for (const [p, np] of [[48, 64], [56, 64], [48, 56]]) CARD.push([`Ile podsieci /${np} mieści /${p} (IPv6)?`, fmt(count6(p, np))]);
CARD.push([`Podsieć /64 nr 42 w 2001:db8:77::/48?`, sub6('2001:db8:77::', 48, 64, 42)]);
CARD.push([`Do której /64 należy 2001:db8:77:2a:dead::1?`, net6('2001:db8:77:2a:dead::1', 64)]);
CARD.push([`Skróć 2001:0db8:0000:0000:0001:0000:0000:00ff`, fromBig6(toBig6('2001:0db8:0000:0000:0001:0000:0000:00ff'))]);
if (CARD.length !== 40) throw new Error('karta ' + CARD.length);

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Trening: liczenie podsieci ma stać się odruchem', ...img('ns03-05-routine.svg', 'Plan treningu w czterech rundach: bez limitu czasu ze 100% poprawności, 30 sekund na zadanie, 15 sekund z agregacją i VLSM, egzaminacyjna mieszanka z IPv6. Dziennik typowych błędów: zły oktet, broadcast mylony z następną siecią, brak odejmowania dwóch hostów, niewyrównany blok, zbyt szeroki agregat. Kalkulator dopiero po odpowiedzi.'),
    body:
`Teoria masek i CIDR jest już za Tobą (lekcje 03 i 04). Ta lekcja jest inna: to **sala treningowa**. Na egzaminie Network+ i CCNA pytania o podsieci są krótkie, ale jest ich dużo i liczy się czas — nie ma kalkulatora. W pracy kalkulator jest, ale **liczenie w głowie pozwala zauważyć błąd w ułamku sekundy**: w code review Terraformu, w regule security group, w konfiguracji Dockera czy w logu „no route to host”.

Program treningu:
- **ściąga** do zapamiętania (prefiks → oktet maski → magiczna liczba → hosty),
- metoda **trzech kroków** dla przynależności adresu do podsieci,
- typy zadań: przynależność, sieć i broadcast, liczba hostów, najmniejszy prefiks, nakładanie się, n-ta podsieć, agregacja, **IPv6 /48, /56, /64**,
- **karta 40 zadań** z kluczem w osobnym bloku (najpierw licz, potem sprawdzaj),
- **program treningowy w Javie**, który losuje zadania, mierzy czas i sprawdza odpowiedzi,
- dziennik błędów — bo poprawia się nie przez liczbę zadań, tylko przez zrozumienie, **który typ błędu popełniasz**.

Zasada z planu lekcji: **kalkulator dopiero po odpowiedzi** — jako sędzia, nigdy zamiast liczenia. Cel: przynależność i sieć/broadcast w ≤ 15 s, agregacja i VLSM w ≤ 1 min.` },

  { type: 'DEFINITION', heading: 'Typy zadań i co w nich naprawdę liczysz', body:
`- **Przynależność** — „czy adres X należy do podsieci S?”: wyznacz zakres S (sieć–broadcast) i sprawdź X. Wystarczy oktet interesujący.
- **Sieć / broadcast / zakres hostów** — z adresu z prefiksem: zaokrąglenie w dół do wielokrotności magicznej liczby, potem + rozmiar − 1.
- **Liczba hostów** — 2^(32−N) − 2 (w chmurze: AWS −5, GCP −4; /31 = 2, /32 = 1).
- **Najmniejszy prefiks** na H hostów — najmniejsza potęga dwójki ≥ H + 2 (lub H + 5 w AWS).
- **Nakładanie się** — dwa zakresy mają część wspólną; dla prefiksów CIDR oznacza to zawsze, że **jeden zawiera drugi** (bloki wyrównane nie mogą zachodzić „na ukos”).
- **N-ta podsieć** — po podziale bloku na /P: początek + n × 2^(32−P).
- **Agregacja** — najdłuższy wspólny prefiks wszystkich sieci; „dokładna”, gdy sumaryczny rozmiar = rozmiar agregatu.
- **Wyrównanie** — czy adres jest początkiem bloku /P (adres mod 2^(32−P) = 0).
- **IPv6** — prefiksy liczysz w **cyfrach hex** (1 cyfra = 4 bity): /48 to trzy grupy, /56 to trzy grupy i dwie cyfry, /64 cztery grupy. LAN = /64, lokalizacja = /48, klient domowy = /56.
- **Interesujący oktet** — oktet, w którym kończy się prefiks (dla /19 — trzeci, dla /27 — czwarty; dla /16 i /24 granica wypada „między” oktetami).
- **Magiczna liczba (rozmiar bloku w oktecie)** — 256 − wartość oktetu maski: 128, 64, 32, 16, 8, 4, 2, 1.` },

  { type: 'ANALOGY', heading: 'Tabliczka mnożenia dla sieciowca', body:
`W podstawówce nikt nie liczy 7 × 8 przez dodawanie siódemek — wynik **wyskakuje sam**, bo był wyćwiczony. Podsieci działają tak samo: /27 → „32, maska 224, 30 hostów” ma wyskakiwać bez myślenia, a myślenie zostawiasz na to, co jest naprawdę nowe w zadaniu (czy adres jest na granicy? czy agregat obejmuje dziurę?).

Trening przypomina też naukę gry na instrumencie: najpierw **wolno i bezbłędnie** (runda 1 bez limitu czasu), potem z metronomem (30 s, 15 s). Przyspieszanie przed opanowaniem utrwala błędy. A **dziennik błędów** to nagranie siebie — dopiero gdy usłyszysz, że zawsze „mylisz się w tym samym takcie” (np. broadcast = następna sieć), możesz to poprawić celowo.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Ściąga do zapamiętania', ...img('ns03-05-cheatsheet.svg', 'Tabela prefiksów od /16 do /32: maska, oktet maski w interesującym oktecie, magiczna liczba (256 minus oktet maski), liczba adresów i hostów; np. /19: 255.255.224.0, magiczna liczba 32 w trzecim oktecie, 8192 adresy, 8190 hostów; /27: 224 w czwartym oktecie, magiczna 32, 30 hostów.'),
    body:
`Zapamiętaj **jeden wiersz** wartości oktetu maski: 128, 192, 224, 240, 248, 252, 254, 255 (kolejne bity dokładane od lewej) i **jeden wiersz** magicznych liczb: 128, 64, 32, 16, 8, 4, 2, 1. Reszta wynika z położenia prefiksu: /17–/24 działa w trzecim oktecie, /25–/32 w czwartym (a /9–/16 w drugim). Przykłady „na głos”:`,
    code:
`/20  → trzeci oktet, maska 240, blok 16  → sieci: x.x.0.0, x.x.16.0, x.x.32.0 …  hosty 4094
/26  → czwarty oktet, maska 192, blok 64 → .0, .64, .128, .192                   hosty 62
/22  → trzeci oktet, maska 252, blok 4   → x.x.0.0, x.x.4.0, x.x.8.0 …            hosty 1022
/29  → czwarty oktet, maska 248, blok 8  → .0, .8, .16 …                         hosty 6
/12  → drugi oktet,  maska 240, blok 16  → 172.16.0.0–172.31.255.255             hosty 1 048 574

Potęgi dwójki (hosty + 2): 2^7=128  2^8=256  2^9=512  2^10=1024  2^11=2048  2^12=4096  2^13=8192  2^16=65 536` },

  { type: 'STEP_BY_STEP', heading: 'Przynależność w trzech krokach (i sieć/broadcast przy okazji)', ...img('ns03-05-membership.svg', 'Sprawdzenie przynależności w trzech krokach dla 172.30.16.0/23: interesujący oktet to trzeci (maska 254), magiczna liczba 2, blok obejmuje 172.30.16.0–172.30.17.255. Adres 172.30.17.200 należy, 172.30.18.1 leży już w kolejnym bloku.'),
    body:
`1. **Znajdź interesujący oktet**: prefiks /N → oktet numer ⌈N/8⌉ (dla N podzielnego przez 8 granica jest między oktetami — porównujesz całe oktety). Oktety przed nim muszą się zgadzać **dokładnie**, po nim — nie mają znaczenia.
2. **Magiczna liczba**: 256 − oktet maski. Bloki w interesującym oktecie zaczynają się od 0 co tę liczbę.
3. **Zakres**: wartość oktetu sieci zaokrąglij w dół do wielokrotności; koniec = początek + magiczna − 1 (z 255 w dalszych oktetach). Adres należy, gdy jego oktet mieści się w zakresie (i wcześniejsze oktety się zgadzają).

Przykład: 192.168.90.77/26 → czwarty oktet, blok 64 → 77 leży w 64–127 → sieć **192.168.90.64**, broadcast **192.168.90.127**, hosty .65–.126. Najczęstsze pułapki: porównanie w złym oktecie (dla /20 patrzysz na trzeci, nie czwarty!), zapominanie, że wcześniejsze oktety też muszą się zgadzać (10.1.17.5 nie należy do 10.2.16.0/20 mimo pasującego trzeciego oktetu), broadcast liczony jako „następna sieć”.` },

  { type: 'CODE_BASIC', heading: 'Karta treningowa: 40 zadań (najpierw licz, klucz jest niżej)', body:
`Zmierz czas całej karty. Odpowiedzi zapisz na kartce, a dopiero potem porównaj z kluczem w bloku „Klucz odpowiedzi”. Błędne pozycje wpisz do dziennika z typem błędu.`,
    code: CARD.map(([q], i) => `${String(i + 1).padStart(2)}. ${q}`).join('\n') },

  { type: 'NOTE', heading: 'Klucz odpowiedzi do karty 40 zadań', body:
`Porównuj dopiero po skończeniu całej karty. Wynik ≥ 36/40 w czasie poniżej 15 minut = gotowość egzaminacyjna dla tej części materiału.`,
    code: CARD.map(([, a], i) => `${String(i + 1).padStart(2)}. ${a}`).join('\n') },

  { type: 'CODE_PRACTICAL', heading: 'Program treningowy w Javie: losuje, mierzy czas, sprawdza', body:
`Prosty trener konsolowy: losuje adres i prefiks, pyta o sieć i broadcast, mierzy czas odpowiedzi i na końcu wypisuje wynik oraz średni czas. Dzięki stałemu ziarnu (\`new Random(seed)\`) możesz powtarzać tę samą serię i porównywać postęp. Uruchom: \`java SubnetTrainer.java 20\`.`,
    code:
`import java.util.*;

public class SubnetTrainer {
    static String ip(long v) { return (v >> 24 & 255) + "." + (v >> 16 & 255) + "." + (v >> 8 & 255) + "." + (v & 255); }

    public static void main(String[] args) {
        int rounds = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        Random rnd = new Random(args.length > 1 ? Long.parseLong(args[1]) : System.nanoTime());
        Scanner in = new Scanner(System.in);
        int ok = 0; long totalMs = 0;
        for (int i = 1; i <= rounds; i++) {
            long addr = (10L << 24) | rnd.nextInt(1 << 24);           // 10.x.y.z
            int prefix = 17 + rnd.nextInt(13);                        // /17 – /29
            long size = 1L << (32 - prefix);
            long net = addr & ~(size - 1) & 0xFFFFFFFFL;
            String expected = ip(net) + " " + ip(net + size - 1);
            System.out.printf("%2d) %s/%d → podaj: sieć broadcast%n> ", i, ip(addr), prefix);
            long t0 = System.currentTimeMillis();
            String answer = in.nextLine().trim().replaceAll("\\\\s+", " ");
            long ms = System.currentTimeMillis() - t0; totalMs += ms;
            if (answer.equals(expected)) { ok++; System.out.printf("   ✓ (%.1f s)%n", ms / 1000.0); }
            else System.out.printf("   ✗ poprawnie: %s (blok %d w oktecie %d)%n", expected,
                    256 - ((0xFFFFFFFFL << (32 - prefix)) >> (8 * ((32 - prefix) / 8)) & 255), (prefix - 1) / 8 + 1);
        }
        System.out.printf("Wynik %d/%d, średnio %.1f s na zadanie%n", ok, rounds, totalMs / 1000.0 / rounds);
    }
}` },

  { type: 'USAGE', heading: 'IPv6: /48, /56 i /64 bez binarnego liczenia', ...img('ns03-05-ipv6.svg', 'Adres 2001:0db8:abcd:0012:0000:0000:0000:0001 podzielony na grupy: pierwsze dwie grupy to prefiks /32 dostawcy, trzecia dopełnia /48 lokalizacji, czwarta wybiera podsieć /64, ostatnie cztery grupy to identyfikator interfejsu. /48 mieści 65 536 podsieci /64 lub 256 podsieci /56; /56 mieści 256 podsieci /64.'),
    body:
`W IPv6 nikt nie liczy hostów — /64 ma 2^64 adresów i **każda sieć LAN to /64** (wymaga tego SLAAC). Liczysz **podsieci**, a granice prefiksów planuje się na pełnych cyfrach hex (wielokrotności 4 bitów), żeby wszystko dało się odczytać „na oko”:
- **/48** — typowy przydział dla firmy/lokalizacji: ${fmt(count6(48, 64))} podsieci /64 (czwarta grupa od 0000 do ffff).
- **/56** — typowy przydział dla klienta domowego lub budynku: ${count6(56, 64)} podsieci /64 (ostatnie dwie cyfry czwartej grupy). W /48 mieści się ${count6(48, 56)} takich bloków.
- **/64** — pojedyncza sieć (VLAN, podsieć VPC; AWS daje VPC /56 i podsieci /64).
- N-ta podsieć /64 w 2001:db8:77::/48 to po prostu numer w hex w czwartej grupie: nr 42 = 0x2a → **${sub6('2001:db8:77::', 48, 64, 42)}**.
- Przynależność: adres 2001:db8:77:2a:dead::1 należy do **${net6('2001:db8:77:2a:dead::1', 64)}** — zeruj wszystko po czwartej grupie.
- Skracanie: zera wiodące w grupie można pominąć, a **jeden** najdłuższy ciąg zerowych grup zastąpić \`::\` (RFC 5952: małe litery, \`::\` tylko raz, nie dla pojedynczej grupy): 2001:0db8:0000:0000:0001:0000:0000:00ff → **${fromBig6(toBig6('2001:0db8:0000:0000:0001:0000:0000:00ff'))}**.

Dla developera: w Javie \`Inet6Address\` i \`InetAddress.getByName("2001:db8::1")\` normalizują zapis; w logach i regułach zawsze porównuj **znormalizowane** adresy, nigdy tekst.` },

  { type: 'PITFALL', heading: 'Błędy, które pojawiają się w dziennikach najczęściej', body:
`- **Zły oktet** — dla /20 liczysz w czwartym oktecie zamiast w trzecim; dla /12 — w trzecim zamiast w drugim.
- **Broadcast = następna sieć** — 10.20.32.0/19 kończy się na 10.20.**63**.255, a nie 10.20.64.0.
- **Brak −2** (lub −2 w AWS zamiast −5).
- **Zaokrąglenie w górę** zamiast w dół przy wyznaczaniu sieci (77 w bloku 64 → 64, nie 128).
- **Ignorowanie wcześniejszych oktetów** przy przynależności.
- **Prefiks za długi o 1** — dla 500 hostów /24 (254) nie wystarczy; /23 (510) tak.
- **Agregat z dziurą** — 192.168.2.0/24 + .3.0 + .4.0 to nie /22 (192.168.0.0/22 obejmuje .0 i .1, a .4 wychodzi poza) — wymaga /21 albo dwóch tras (.2.0/23 i .4.0/24).
- **IPv6: liczenie bitów zamiast cyfr hex**, podwójne \`::\`, wielkie litery w porównaniach tekstowych.
- **Przyspieszanie przed opanowaniem** — trenuj na czas dopiero, gdy runda bez limitu jest bezbłędna.` },

  { type: 'CODE_WRONG', heading: 'Źle: trening, który nic nie daje', body:
`Typowe „ćwiczenie” bez efektu: kalkulator przed odpowiedzią, zadania tylko jednego typu, brak pomiaru i brak analizy błędów.`,
    code:
`for (int i = 0; i < 100; i++) {
    String task = randomTask();                 // zawsze /24 — „bo łatwe”
    System.out.println(task + " → " + calculator(task));   // wynik od razu: czytasz, nie liczysz
}
// brak: pomiaru czasu, mieszania typów (agregacja, nakładanie, IPv6), zapisu błędów, powtórki słabych typów` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: odpowiedź → sędzia → dziennik → powtórka słabego typu', body:
`Trening deliberatywny: każda odpowiedź jest najpierw liczona, potem sprawdzana, a błędy trafiają do dziennika według typu — kolejna seria losuje częściej te typy, w których się mylisz.`,
    code:
`Map<String, Integer> errors = new HashMap<>();                 // typ zadania → liczba błędów
for (Task t : drill.next(20, errors)) {                         // losowanie ważone błędami
    long t0 = System.nanoTime();
    String answer = ask(t);                                     // najpierw liczysz sam
    boolean ok = t.check(answer);                               // „kalkulator” jako sędzia
    log(t.type(), ok, (System.nanoTime() - t0) / 1_000_000);   // czas per typ
    if (!ok) { errors.merge(t.type(), 1, Integer::sum); explain(t); }   // wyjaśnienie bit po bicie
}
report(errors);   // np. „broadcast: 3 błędy, agregacja: 2, IPv6 skracanie: 0” → jutro więcej broadcastów` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy liczyć w głowie, kiedy na kartce, kiedy narzędziem', body:
`Dobór metody do sytuacji:`,
    code:
`SYTUACJA                                       METODA
egzamin Network+/CCNA                          w głowie + kartka: ściąga, magiczna liczba, 3 kroki
code review (Terraform, SG, ACL, compose)      w głowie: „czy /20 zaczyna się od .16.0? czy SG nie jest za szeroka?”
diagnoza na produkcji                          ip route get, ipcalc — szybko i bez ryzyka pomyłki
projekt adresacji (VPC, oddział, VLSM)         narzędzie/IPAM + kontrola w głowie każdego wyniku
walidacja w kodzie i testach                   biblioteka (IPAddress, Commons Net, Python ipaddress) — nigdy regex na tekście
IPv6                                           granice na cyfrach hex → w głowie; skracanie → narzędzie (RFC 5952)` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Zapamiętaj dwa wiersze: oktety maski 128–255 i magiczne liczby 128–1; resztę wyznacza położenie prefiksu.
- Przynależność w 3 krokach: interesujący oktet → magiczna liczba → zakres bloku (wcześniejsze oktety muszą się zgadzać).
- Typy zadań: sieć/broadcast, hosty (−2, AWS −5), najmniejszy prefiks, nakładanie (wyrównane bloki: zawieranie), n-ta podsieć, agregacja (dokładna lub z dziurą), IPv6 /48–/56–/64 w cyfrach hex.
- Trening: najpierw bezbłędnie, potem na czas; kalkulator tylko jako sędzia; dziennik błędów według typu.
- Cel: ≥ 36/40 karty w < 15 min; przynależność i sieć/broadcast ≤ 15 s.
- Następna lekcja: **adresy prywatne, publiczne i specjalne** — RFC 1918, loopback, link-local i metadane chmury, CGNAT, 0.0.0.0 przy bind.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const r2 = rng(777);
for (let i = 0; i < 6; i++) {
  const m = membership(r2); const p = parse(m.subnet);
  ex(`Drill (≤ 15 s): czy ${m.ip} należy do ${m.subnet}?`, 'Interesujący oktet → magiczna liczba → zakres.', `${m.inside ? 'TAK' : 'NIE'} — zakres ${p.network}–${p.broadcast} (blok ${p.size} adresów).`);
}
for (const c of ['10.77.155.3/21', '172.22.9.250/23', '192.168.200.199/27', '10.200.1.1/13', '172.16.255.129/25']) {
  const p = parse(c);
  ex(`Drill (≤ 15 s): sieć, broadcast i liczba hostów dla ${c}.`, 'Zaokrąglij w dół do wielokrotności bloku.', `Sieć ${p.network}, broadcast ${p.broadcast}, ${fmt(p.hosts)} hostów (hosty ${p.first}–${p.last}).`);
}
for (const [h, rsv] of [[900, 2], [33, 2], [120, 5], [8000, 2]]) {
  const p = prefixForHosts(h, rsv);
  ex(`Drill: najmniejszy prefiks dla ${h} hostów${rsv === 5 ? ' w AWS' : ''}.`, `2^(32−p) − ${rsv} ≥ ${h}.`, `/${p} (${fmt(size(p) - rsv)} adresów do użycia).`);
}
for (const [a, b] of [['10.30.0.0/17', '10.30.96.0/19'], ['192.168.10.0/25', '192.168.10.128/26'], ['100.64.0.0/10', '100.100.100.0/24']]) {
  ex(`Drill: czy ${a} i ${b} się nakładają? Jeśli tak — który zawiera który?`, 'Zakresy wyrównanych bloków albo są rozłączne, albo jeden zawiera drugi.', overlaps(a, b) ? `TAK — ${contains(a, b) ? `${a} zawiera ${b}` : `${b} zawiera ${a}`}.` : 'NIE — zakresy rozłączne.');
}
for (const set of [['10.0.8.0/24', '10.0.9.0/24', '10.0.10.0/24', '10.0.11.0/24'], ['192.168.2.0/24', '192.168.3.0/24', '192.168.4.0/24'], ['172.16.64.0/20', '172.16.80.0/20', '172.16.96.0/19']]) {
  const s = summarize(set);
  ex(`Drill: agregacja ${set.join(', ')} — jaki agregat i czy dokładny? Jeśli nie, zaproponuj dokładny zestaw tras.`, 'Najdłuższy wspólny prefiks; porównaj rozmiary.', `${s.cidr} — ${s.exact ? 'dokładny.' : `niedokładny (+${fmt(s.extra)} adresów). Dokładnie: ${set[0] === '192.168.2.0/24' ? '192.168.2.0/23 + 192.168.4.0/24' : 'osobne trasy'}.`}`);
}
for (const [base, np, n] of [['10.50.0.0/16', 21, 11], ['172.20.0.0/22', 26, 9], ['192.168.0.0/20', 24, 13]]) {
  ex(`Drill: ${base} dzielisz na /${np}. Podaj podsieć nr ${n} (od 0) i jej broadcast.`, `Początek + ${n} × ${size(np)}.`, `${split(base, np)[n]}, broadcast ${parse(split(base, np)[n]).broadcast}.`);
}
ex(`Drill IPv6: ile podsieci /64 i /56 mieści 2001:db8:400::/48? Podaj podsieć /64 nr 300 i /56 nr 10.`, '/48 → /64: 16 bitów; /56: 8 bitów; numer w hex.', `/64: ${fmt(count6(48, 64))}, /56: ${count6(48, 56)}. /64 nr 300 (0x12c): ${sub6('2001:db8:400::', 48, 64, 300)}; /56 nr 10 (0x0a): ${sub6('2001:db8:400::', 48, 56, 10)}.`);
ex(`Drill IPv6: do jakich sieci /64 i /56 należy adres 2001:db8:400:abcd:1::7? Skróć też 2001:0db8:0400:0000:0000:0000:0000:0001.`, 'Zeruj bity po prefiksie; RFC 5952.', `/64: ${net6('2001:db8:400:abcd:1::7', 64)}; /56: ${net6('2001:db8:400:abcd:1::7', 56)}; skrót: ${fromBig6(toBig6('2001:0db8:0400:0000:0000:0000:0000:0001'))}.`);
const v = vlsm('10.60.0.0/23', [{ name: 'Aplikacje', hosts: 200 }, { name: 'Bazy', hosts: 60 }, { name: 'Monitoring', hosts: 25 }, { name: 'Bastion', hosts: 4 }]);
ex('Drill VLSM (≤ 2 min): podziel 10.60.0.0/23 dla: Aplikacje 200, Bazy 60, Monitoring 25, Bastion 4.', 'Od największej, najmniejszy pasujący prefiks.', `${v.result.map((x) => `${x.name} ${x.cidr}`).join(', ')}; wolne ${v.free} adresów.`);
ex('Wykonaj kartę 40 zadań z lekcji na czas, zapisz wynik i dziennik błędów według typów. Powtórz kartę po 3 dniach i porównaj.', 'Klucz — w bloku teorii „Klucz odpowiedzi”.', 'Cel: ≥ 36/40 w < 15 min. Dziennik: dla każdego błędu typ (zły oktet, broadcast, −2, wyrównanie, agregacja, IPv6) i przyczyna. Po 3 dniach powtórz — postęp mierzy się spadkiem błędów w najsłabszym typie, nie tylko wynikiem.');
ex('Rozbuduj SubnetTrainer z lekcji o drugi typ zadań: „czy adres należy do podsieci” oraz o zapis wyników do pliku CSV (data, typ, czas, wynik).', 'Losuj typ; Files.writeString z StandardOpenOption.APPEND.', 'Dodaj enum Type { NET_BCAST, MEMBERSHIP }, losuj rnd.nextBoolean(); dla MEMBERSHIP losuj adres w bloku lub w sąsiednim (net ± size) i oczekuj „tak”/„nie”. Po każdej odpowiedzi: Files.writeString(Path.of("drill.csv"), String.join(",", LocalDate.now().toString(), type.name(), String.valueOf(ms), ok ? "1" : "0") + "\\n", CREATE, APPEND). Analiza: średni czas i skuteczność per typ w arkuszu lub prostym strumieniu Javy.');
ex('Wyjaśnij, dlaczego dwa bloki CIDR nigdy nie nakładają się „częściowo” i jak to upraszcza sprawdzanie kolizji w kodzie.', 'Wyrównanie do potęg dwójki.', 'Blok /N zaczyna się od wielokrotności 2^(32−N), a jego długość to ta sama potęga dwójki. Dla dwóch bloków krótszy (większy) jest podzielny na bloki dłuższego prefiksu, więc dłuższy albo całkowicie leży w jednym z nich, albo poza. W kodzie wystarczy porównać adres sieci dłuższego prefiksu zamaskowany maską krótszego: (net_b & mask_a) == net_a → zawieranie; inaczej rozłączne.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
