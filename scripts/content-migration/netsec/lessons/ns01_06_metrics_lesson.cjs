// Generuje lekcje _ns_01_fundamenty_sieci/06_PerformanceMetrics.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_06_metrics_lesson.cjs src/main/resources/content/_ns_01_fundamenty_sieci/06_PerformanceMetrics.json
const { quizBuilder, exerciseBuilder, writeLesson, fmt } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const num = (x, d = 2) => {
  const r = Math.round(x * 10 ** d) / 10 ** d;
  return r.toLocaleString('pl-PL', { maximumFractionDigits: d }).replace(/\s/g, ' ');
};
const propMs = (km) => km * 0.005;                        // swiatlowod ~200 000 km/s → 5 µs/km
const serMs = (bytes, mbps) => (bytes * 8) / (mbps * 1e6) * 1000;
const bdpBytes = (mbps, rttMs) => (mbps * 1e6 * rttMs / 1000) / 8;
const winMbps = (winBytes, rttMs) => (winBytes * 8) / (rttMs / 1000) / 1e6;
const mathis = (mss, rttMs, p) => (mss * 8 / (rttMs / 1000)) * (1.22 / Math.sqrt(p)) / 1e6;
const tail = (n, q = 0.99) => 1 - q ** n;

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: '„Jest wolno” — ale co dokładnie?', ...img('ns01-06-curl-timeline.svg', 'Dwa paski czasu żądania HTTPS z curl: przy RTT 2 ms fazy DNS 4, TCP 2, TLS 5, czekanie na serwer 32 i transfer 3 ms, razem 46 ms; po dodaniu 100 ms opóźnienia TCP 102, TLS 106, TTFB 132, transfer 103 ms, razem 447 ms.'),
    body:
`„Aplikacja jest wolna” to najczęstsze zgłoszenie, jakie dostaje backend developer. Bez metryk zaczyna się zgadywanie: dokupić większe łącze? Dodać rdzeni? Zwiększyć pulę połączeń? Tymczasem każda z tych decyzji pomaga tylko na *jeden* rodzaj problemu.

Sieć opisują cztery podstawowe wielkości:
- **przepustowość** (ile danych na sekundę),
- **opóźnienie** (ile trwa dostarczenie i odpowiedź — RTT),
- **jitter** (jak bardzo opóźnienie się zmienia),
- **utrata pakietów** (jaki odsetek ginie po drodze).

A użytkownik odczuwa jeszcze piątą: **rozkład czasów odpowiedzi** — nie średnią, tylko to, jak często trafia na wolne żądanie (p95, p99).

Na ilustracji to samo żądanie HTTPS przy RTT 2 ms i po sztucznym dodaniu 100 ms opóźnienia: czas całkowity rośnie z 46 do 447 ms, bo każda faza wymagająca wymiany pakietów płaci RTT. W tej lekcji nauczysz się rozkładać czas żądania na fazy, liczyć opóźnienia i ograniczenia TCP (BDP, wzór Mathisa), czytać percentyle i mierzyć to wszystko narzędziami: \`curl -w\`, \`ping\`, \`mtr\`, \`iperf3\`, \`tc netem\` oraz Micrometer w Spring Boot.` },

  { type: 'DEFINITION', heading: 'Słownik metryk sieci i usług', body:
`- **Przepustowość nominalna (bandwidth)** — pojemność łącza, np. 1 Gb/s. To górna granica, nie obietnica.
- **Przepływność rzeczywista (throughput)** — ile bitów faktycznie przechodzi w jednostce czasu (z nagłówkami i retransmisjami).
- **Goodput** — ile *użytecznych danych aplikacji* przechodzi w jednostce czasu; zawsze mniej niż throughput.
- **Opóźnienie (latency)** — czas dostarczenia pakietu w jedną stronę; składa się z przetwarzania, kolejkowania, transmisji i propagacji.
- **RTT (round-trip time)** — czas tam i z powrotem; to mierzy \`ping\` i od tego zależy TCP.
- **Jitter** — zmienność opóźnienia między kolejnymi pakietami; zabójczy dla głosu i wideo (RTP), mniej dla HTTP.
- **Utrata pakietów (packet loss)** — odsetek pakietów, które nie dotarły; TCP ją naprawia retransmisjami, ale kosztem przepustowości i opóźnienia.
- **BDP (bandwidth-delay product)** — przepustowość × RTT: ile danych musi być „w locie”, by w pełni wykorzystać łącze.
- **Percentyl pN** — wartość, poniżej której mieści się N% pomiarów; p50 = mediana, p99 = co setne żądanie jest wolniejsze.
- **SLI / SLO / SLA** — wskaźnik (np. odsetek żądań < 300 ms), cel (99,9% żądań < 300 ms w miesiącu) i umowa z klientem z konsekwencjami.
- **Bufferbloat** — nadmiernie duże bufory w urządzeniach, które przy obciążeniu zamieniają przeciążenie w setki milisekund opóźnienia zamiast w utratę pakietów.

Jednostki: **b = bit, B = bajt**. 100 Mb/s to 12,5 MB/s. Prefiksy przepustowości są dziesiętne (1 Mb/s = 10⁶ b/s), a rozmiary w pamięci często binarne (1 MiB = 2²⁰ B).` },

  { type: 'ANALOGY', heading: 'Autostrada z bramkami', body:
`- **Przepustowość** to liczba pasów — więcej pasów to więcej samochodów na godzinę, ale żaden samochód nie jedzie przez to szybciej.
- **Propagacja** to długość trasy podzielona przez prędkość: z Warszawy do Lizbony nie dojedziesz szybciej, dokładając pasy. Światło w światłowodzie też ma limit prędkości (~200 000 km/s).
- **Transmisja** to czas, w którym długa ciężarówka wjeżdża w całości na drogę — im więcej pasów (szybsze łącze), tym szybciej.
- **Kolejkowanie** to korek przed bramkami — rośnie gwałtownie, gdy ruch zbliża się do pojemności.
- **Jitter** to sytuacja, gdy raz jedziesz 3 godziny, raz 5 — ważne dla autobusu z rozkładem jazdy (wideokonferencja), mniej dla kuriera z paczką (pobieranie pliku).
- **Utrata pakietów** to samochód, który nie dojechał: trzeba wysłać kolejny, a kierowcy (TCP) na wszelki wypadek zwalniają.

Wniosek, który oszczędza pieniądze: **szersza autostrada nie skraca podróży z Warszawy do Lizbony.** Gdy problemem jest RTT, większe łącze nie pomoże — pomoże bliższy serwer, mniej rund komunikacji albo pamięć podręczna.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Składowe opóźnienia w liczbach', ...img('ns01-06-delay-components.svg', 'Cztery paski: przetwarzanie (mikrosekundy), kolejkowanie (od zera do setek milisekund), transmisja (1500 bajtów przy 10 Mb/s to 1,2 ms) i propagacja (1000 km światłowodu to około 5 ms).'),
    body:
`Każdy odcinek drogi dokłada cztery składowe. Kilka liczb, które warto znać na pamięć:`,
    code:
`PROPAGACJA (światłowód ≈ 200 000 km/s → 5 µs na km, w jedną stronę)
  serwerownia, 100 m                  0,0005 ms
  Warszawa – Frankfurt (~1 000 km*)   ${num(propMs(1000))} ms     → RTT ≥ ${num(2 * propMs(1000))} ms
  Warszawa – Wirginia (~7 500 km*)    ${num(propMs(7500))} ms    → RTT ≥ ${num(2 * propMs(7500))} ms
  Warszawa – Sydney (~16 000 km*)     ${num(propMs(16000))} ms    → RTT ≥ ${num(2 * propMs(16000))} ms
  * długość kabla, nie odległość w linii prostej; realne RTT jest większe (routing, sprzęt)

TRANSMISJA pakietu 1500 B (rozmiar × 8 / przepustowość)
  1 Mb/s     ${num(serMs(1500, 1))} ms
  10 Mb/s    ${num(serMs(1500, 10))} ms
  100 Mb/s   ${num(serMs(1500, 100), 3)} ms
  1 Gb/s     ${num(serMs(1500, 1000), 3)} ms
  10 Gb/s    ${num(serMs(1500, 10000), 4)} ms

KOLEJKOWANIE — zależy od obciążenia: przy wykorzystaniu łącza 50% zwykle pomijalne,
przy 95–100% rośnie gwałtownie (setki ms przy dużych buforach = bufferbloat)
PRZETWARZANIE — mikrosekundy w sprzęcie; więcej w urządzeniach z inspekcją (NGFW, WAF, proxy TLS)` },

  { type: 'CODE_BASIC', heading: 'Kalkulator opóźnień i ograniczeń TCP w Javie', body:
`Wzory z tej lekcji w jednym miejscu. Wzór Mathisa szacuje maksymalną przepustowość jednego połączenia TCP przy danej utracie pakietów — pokazuje, jak bardzo nawet 1% strat dławi transfer na dalekich trasach.`,
    code:
`public class NetMath {
    static double propagationMs(double km)              { return km * 0.005; }            // 5 µs/km
    static double transmissionMs(long bytes, double mbps) { return bytes * 8 / (mbps * 1e6) * 1000; }
    static double bdpBytes(double mbps, double rttMs)    { return mbps * 1e6 * rttMs / 1000 / 8; }
    static double windowLimitedMbps(long window, double rttMs) { return window * 8 / (rttMs / 1000) / 1e6; }
    static double mathisMbps(int mss, double rttMs, double loss) {   // Mathis i in., 1997
        return (mss * 8 / (rttMs / 1000)) * (1.22 / Math.sqrt(loss)) / 1e6;
    }
    static double tailProbability(int fanOut, double p) { return 1 - Math.pow(p, fanOut); }

    public static void main(String[] args) {
        System.out.printf("BDP 1 Gb/s × 40 ms = %.0f B%n", bdpBytes(1000, 40));          // ${fmt(bdpBytes(1000, 40))}
        System.out.printf("okno 64 KiB, RTT 100 ms → %.2f Mb/s%n", windowLimitedMbps(65535, 100)); // ${num(winMbps(65535, 100))}
        System.out.printf("Mathis MSS 1460, RTT 50 ms, 1%% strat → %.2f Mb/s%n", mathisMbps(1460, 50, 0.01)); // ${num(mathis(1460, 50, 0.01))}
        System.out.printf("Mathis MSS 1460, RTT 5 ms, 1%% strat → %.2f Mb/s%n", mathisMbps(1460, 5, 0.01));   // ${num(mathis(1460, 5, 0.01))}
        System.out.printf("fan-out 100 usług, każda p99 → %.1f%% żądań trafi w ogon%n", 100 * tailProbability(100, 0.99)); // ${num(100 * tail(100), 1)}
    }
}` },

  { type: 'STEP_BY_STEP', heading: 'Dlaczego transfer jest wolny, choć łącze jest szybkie', ...img('ns01-06-bdp-pipe.svg', 'Rura 100 Mb/s o długości RTT 50 ms mieści 625 000 bajtów. Przy oknie 64 KiB w rurze są tylko dwa pakiety i przepustowość spada do około 10,5 Mb/s; przy oknie 1 MB rura jest pełna i łącze daje 100 Mb/s.'),
    body:
`Klasyczny przypadek: kopia zapasowa bazy do innego regionu idzie z prędkością 10 Mb/s po łączu 1 Gb/s. Postępuj tak:
1. **Zmierz RTT** (\`ping\`, \`mtr\`) i przepustowość łącza (\`iperf3 -c host -t 30\`, także \`-P 4\` z kilkoma strumieniami).
2. **Policz BDP** = przepustowość × RTT. Dla 1 Gb/s i 40 ms to ${fmt(bdpBytes(1000, 40))} B — tyle danych musi być „w locie”.
3. **Sprawdź okno TCP.** Pojedyncze połączenie nie przekroczy *okno / RTT*. Bez skalowania okna (64 KiB) przy 40 ms to tylko ${num(winMbps(65535, 40))} Mb/s. W Linuksie: \`sysctl net.ipv4.tcp_rmem tcp_wmem\`, \`ss -ti\` (pola wscale, rcv_space, cwnd).
4. **Sprawdź utratę pakietów** (\`mtr -rwc 200\`, liczniki retransmisji \`nstat -az | grep -i retrans\`, Wireshark: Analyze → Expert Info). Według wzoru Mathisa przy RTT 40 ms i 0,1% strat pojedyncze połączenie osiągnie około ${num(mathis(1460, 40, 0.001))} Mb/s.
5. **Sprawdź aplikację** — czy nie zapisuje małymi porcjami, czy bufor gniazda w Javie (\`setReceiveBufferSize\`) nie jest ustawiony na sztywno za mały, czy nie czeka na potwierdzenia na poziomie protokołu (synchroniczne „wyślij–czekaj”).
6. **Popraw właściwą przyczynę**: większe bufory/okna, równoległe strumienie, algorytm kontroli przeciążenia BBR na dalekich trasach, usunięcie źródła strat, kompresja, bliższy region.

Zasada: **przepustowość pojedynczego połączenia TCP ograniczają okno, RTT i straty — nie tylko łącze.**` },

  { type: 'NOTE', heading: 'Percentyle, długi ogon i prawo Little’a', ...img('ns01-06-percentiles.svg', 'Histogram czasów odpowiedzi z długim ogonem w prawo: mediana 210 ms, średnia 290 ms, p95 640 ms, p99 1180 ms. Podpis: przy 50 wywołaniach co najmniej jedno trafi w p99 z prawdopodobieństwem około 39,5 procent.'),
    body:
`Czasy odpowiedzi prawie nigdy nie mają rozkładu normalnego — mają **długi ogon** (GC, kolejki, retransmisje TCP, zimne cache, blokady w bazie). Dlatego:`,
    code:
`ŚREDNIA ukrywa ogon: 99 żądań po 100 ms + 1 po 10 s → średnia 199 ms, p99 = 10 s.
PERCENTYLE: p50 (typowy użytkownik), p95/p99 (prawie najgorszy), max (najgorszy pojedynczy przypadek).
Percentyli NIE da się uśredniać między instancjami — trzeba łączyć histogramy.

OGON PRZY FAN-OUT — żądanie wołające N usług czeka na najwolniejszą:
  P(co najmniej jedna odpowiedź z p99) = 1 − 0,99^N
  N = 1    → ${num(100 * tail(1), 1)}%
  N = 10   → ${num(100 * tail(10), 1)}%
  N = 50   → ${num(100 * tail(50), 1)}%
  N = 100  → ${num(100 * tail(100), 1)}%

PRAWO LITTLE’A: L = λ × W  (współbieżność = tempo napływu × czas obsługi)
  200 żądań/s × 0,25 s = 50 żądań obsługiwanych jednocześnie
  → pula wątków, połączeń JDBC lub HTTP mniejsza niż 50 zbuduje kolejkę;
  → jeśli backend zwolni do 2 s: 200 × 2 = 400 jednocześnie — pule się wyczerpują, rośnie opóźnienie w górę łańcucha.` },

  { type: 'CODE_PRACTICAL', heading: 'Pomiar w praktyce: curl -w, tc netem, iperf3 i mtr', body:
`Ćwiczenie z planu lekcji: mierzymy fazy żądania przed i po sztucznym pogorszeniu sieci w kontenerze (wymaga uprawnienia NET_ADMIN, tylko w labie).`,
    code:
`# format czasów curl (zapisz jako curl-format.txt)
dns=%{time_namelookup} connect=%{time_connect} tls=%{time_appconnect} ttfb=%{time_starttransfer} total=%{time_total}\\n

curl -s -o /dev/null -w @curl-format.txt https://app.lab.local/api/health
# dns=0.004 connect=0.006 tls=0.011 ttfb=0.043 total=0.046      (wartości narastające, w sekundach)

# pogorszenie łącza w kontenerze klienta: +100 ms opóźnienia, ±10 ms jitter, 1% strat
docker run --rm -it --cap-add NET_ADMIN --network lab nicolaka/netshoot bash
tc qdisc add dev eth0 root netem delay 100ms 10ms loss 1%
curl -s -o /dev/null -w @curl-format.txt https://app.lab.local/api/health
# dns=0.004 connect=0.106 tls=0.212 ttfb=0.344 total=0.447
tc qdisc del dev eth0 root                                   # przywrócenie

# przepustowość i straty
iperf3 -s                                  # na serwerze
iperf3 -c serwer -t 20 [-P 4] [-R] [-u -b 50M]  # TCP, wiele strumieni, odwrotnie, UDP z zadanym tempem
mtr -rwc 100 serwer                        # straty i opóźnienie na każdym skoku (raport)
ping -i 0.2 -c 100 serwer | tail -2        # rtt min/avg/max/mdev — mdev to przybliżenie jittera` },

  { type: 'USAGE', heading: 'Gdzie te metryki decydują w pracy developera i obrońcy', body:
`- **Timeouty** — ustawiaj na podstawie p99 zależności (z zapasem), a nie średniej; zbyt długie timeouty przy awarii zamieniają się w wyczerpane pule (prawo Little’a).
- **Rozmiary pul** (HikariCP, wątki Tomcata, klienty HTTP) — licz z λ × W i obserwuj czas oczekiwania na połączenie.
- **Wybór regionu i CDN** — RTT do użytkowników jest twardym limitem; każda runda (handshake TCP, TLS, przekierowanie, zapytanie N+1 przez sieć) kosztuje RTT.
- **Projekt API** — mniej rund komunikacji (agregacja, batch, HTTP/2 multiplexing) zamiast „gadatliwych” wywołań.
- **Monitoring i SLO** — Micrometer z histogramami (\`http.server.requests\`), Prometheus \`histogram_quantile\`, alerty na p99 i odsetek błędów, a nie na średnią.
- **Testy obciążeniowe** — uważaj na *coordinated omission*: generator, który czeka na odpowiedź przed wysłaniem kolejnego żądania, zaniża percentyle (Gatling i wrk2 ustalają stałe tempo).
- **Bezpieczeństwo (blue team)**:
  - **DDoS i wyczerpanie zasobów** widać najpierw jako skok opóźnień, retransmisji i połączeń półotwartych — baseline metryk to podstawa wykrywania (Security+ i CySA+ wymagają znajomości baseline).
  - **Eksfiltracja** zdradza się nietypową przepływnością wychodzącą (np. nocny transfer GB do nowego celu) — wykrywana z NetFlow i metryk interfejsów.
  - **Beaconing C2** to regularne połączenia z małym jitterem — analiza odstępów czasowych i ich rozrzutu to klasyczna technika huntingu.
  - **Dostępność** to trzecia litera triady CIA: SLO i metryki wydajności są częścią bezpieczeństwa.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy w mierzeniu wydajności', body:
`- **Mylenie Mb/s i MB/s.** Łącze 100 Mb/s pobiera 1 GB w ~80 s, nie w 10 s.
- **Średnia zamiast percentyli** — i uśrednianie p99 z kilku instancji (matematycznie bez sensu).
- **„Większe łącze rozwiąże problem”** — nie, gdy dominuje RTT, liczba rund lub straty.
- **Ping jako miara aplikacji** — ICMP bywa traktowany z niższym priorytetem i nie przechodzi przez kolejki aplikacji, TLS ani bazę.
- **Pomiar z laptopa przez Wi-Fi** — jitter i straty radiowe fałszują wyniki; mierz z serwera w tej samej sieci co produkcja.
- **System.currentTimeMillis() do pomiaru czasu** — zegar ścienny może skakać (NTP); do odcinków czasu używa się \`System.nanoTime()\`.
- **Coordinated omission** w testach obciążeniowych — zaniżone ogony.
- **Ignorowanie kolejkowania** — wykorzystanie 95% łącza czy puli to nie „5% zapasu”, tylko gwałtownie rosnące opóźnienie.
- **Bufferbloat** — „Internet działa, ale wideokonferencja się tnie, gdy ktoś wysyła duży plik”; pomaga AQM (fq_codel, CAKE) i QoS.` },

  { type: 'CODE_WRONG', heading: 'Źle: średnia z currentTimeMillis i średnia ze średnich', body:
`Ręczny pomiar w kontrolerze: zegar ścienny, sama średnia, a na dashboardzie uśrednione wartości z pięciu instancji. Ogon jest niewidoczny, a alarm nigdy się nie włączy.`,
    code:
`private final AtomicLong total = new AtomicLong(), count = new AtomicLong();

@GetMapping("/orders/{id}")
Order get(@PathVariable long id) {
    long start = System.currentTimeMillis();          // zegar ścienny — może cofnąć się przy korekcie NTP
    try {
        return service.find(id);
    } finally {
        total.addAndGet(System.currentTimeMillis() - start);
        count.incrementAndGet();                      // tylko średnia: 99 × 100 ms + 1 × 10 s = 199 ms
    }
}
// Grafana: avg(avg_latency) po instancjach → „wszystko poniżej 200 ms”, choć co setny klient czeka 10 s` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: histogramy i percentyle z Micrometera', body:
`Spring Boot Actuator mierzy \`http.server.requests\` automatycznie; wystarczy włączyć histogramy i progi SLO. Własne operacje mierzymy \`Timer\`em. W Prometheusie percentyle liczymy z kubełków histogramu (sumowanych po instancjach), a nie uśredniamy.`,
    code:
`# application.yml
management:
  metrics:
    distribution:
      percentiles-histogram:
        http.server.requests: true
      slo:
        http.server.requests: 100ms,300ms,1s     # kubełki pod cele SLO

// własny pomiar operacji
private final Timer dbTimer;
OrderService(MeterRegistry registry) {
    this.dbTimer = Timer.builder("orders.db.find")
        .publishPercentileHistogram()             // kubełki do agregacji między instancjami
        .register(registry);
}
Order find(long id) {
    return dbTimer.record(() -> repository.findById(id).orElseThrow());   // używa nanoTime
}

# PromQL: p99 dla całej usługi, ze zsumowanych histogramów wszystkich instancji
histogram_quantile(0.99, sum by (le) (rate(http_server_requests_seconds_bucket{uri="/orders/{id}"}[5m])))` },

  { type: 'WHEN_TO_USE', heading: 'Która metryka odpowiada na które pytanie', body:
`- **„Czy łącze jest za wąskie?”** — przepływność względem przepustowości (wykorzystanie interfejsów, iperf3); kolejki i odrzucenia na interfejsach.
- **„Dlaczego pojedynczy transfer jest wolny?”** — RTT, okno TCP, BDP, retransmisje (wzór Mathisa).
- **„Dlaczego wideokonferencja się tnie?”** — jitter i straty (RTP wymaga zwykle jittera < 30 ms i strat < 1%), bufferbloat.
- **„Czy użytkownicy są zadowoleni?”** — percentyle czasu odpowiedzi (p95/p99) i odsetek błędów względem SLO.
- **„Ile wątków/połączeń potrzebuję?”** — prawo Little’a z realnym tempem i czasem obsługi.
- **„Czy dzieje się coś podejrzanego?”** — odchylenia od baseline: nagłe skoki ruchu, połączeń, retransmisji, transferów wychodzących.
- **„Gdzie umieścić usługę?”** — RTT do użytkowników i zależności; liczba rund w typowym scenariuszu.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Przepustowość ≠ przepływność ≠ goodput; **b ≠ B** (100 Mb/s = 12,5 MB/s).
- Opóźnienie = przetwarzanie + kolejkowanie + transmisja + propagacja; światłowód: ~5 µs/km w jedną stronę.
- RTT płacisz w każdej rundzie: handshake TCP, TLS, żądanie — dlatego +100 ms RTT dodaje kilkaset ms do żądania.
- **BDP = przepustowość × RTT**; pojedyncze połączenie TCP ≤ okno / RTT; przy stratach — wzór Mathisa (MSS/RTT × 1,22/√p).
- Jitter i straty niszczą media czasu rzeczywistego; bufferbloat zamienia obciążenie w opóźnienie.
- **Średnia kłamie** — używaj p50/p95/p99 i histogramów; ogon rośnie przy fan-out: 1 − 0,99^N.
- **Prawo Little’a**: współbieżność = tempo × czas obsługi — podstawa doboru pul i timeoutów.
- Dla obrońcy baseline metryk to fundament wykrywania DDoS, eksfiltracji i beaconingu.
- Następna lekcja: **pierwszy capture w Wiresharku i tcpdump** — zobaczysz te zjawiska w pakietach.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [km, desc] of [[400, 'Warszawa – Berlin (kabel ok. 600 km)'], [1500, 'Warszawa – Londyn (kabel ok. 1 800 km)'], [9000, 'Warszawa – Singapur (kabel ok. 12 000 km)']]) {
  const cable = desc.match(/ok\. ([\d ]+) km/)[1].replace(/\s/g, '');
  ex(`Oszacuj minimalne RTT wynikające z propagacji dla trasy ${desc}. Dlaczego realny ping będzie większy?`, 'Światłowód: ok. 5 µs na km w jedną stronę; RTT to dwie strony.', `Propagacja w jedną stronę: ${cable} km × 0,005 ms = ${num(propMs(+cable))} ms; minimalne RTT ≈ ${num(2 * propMs(+cable))} ms. Realne RTT jest większe przez routing nie po linii prostej, czas przetwarzania i kolejkowania w routerach oraz transmisję na wolniejszych odcinkach.`);
}
for (const [bytes, mbps] of [[1500, 2], [9000, 10000], [65536, 100]]) {
  ex(`Ile trwa transmisja (serializacja) ${fmt(bytes)} B na łączu ${fmt(mbps)} Mb/s?`, 'Czas = rozmiar × 8 / przepustowość.', `${fmt(bytes)} × 8 / ${fmt(mbps)}·10⁶ s = ${num(serMs(bytes, mbps), 4)} ms.`);
}
for (const [mbps, rtt] of [[100, 30], [1000, 80], [10000, 2]]) {
  ex(`Policz BDP dla łącza ${fmt(mbps)} Mb/s i RTT ${rtt} ms. Jakie minimalne okno TCP jest potrzebne do pełnego wykorzystania łącza jednym połączeniem?`, 'BDP = przepustowość × RTT (pamiętaj o zamianie bitów na bajty).', `BDP = ${fmt(mbps)}·10⁶ b/s × ${rtt / 1000} s / 8 = ${fmt(Math.round(bdpBytes(mbps, rtt)))} B. Okno odbiorcy (i okno przeciążenia) musi mieć co najmniej tyle — wymaga to skalowania okna TCP (powyżej 64 KiB).`);
}
for (const [win, rtt] of [[65535, 20], [262144, 150]]) {
  ex(`Połączenie ma okno ${fmt(win)} B, RTT ${rtt} ms. Jaka jest maksymalna przepływność tego połączenia niezależnie od szybkości łącza?`, 'Nadawca może wysłać najwyżej jedno okno na RTT.', `${fmt(win)} × 8 / ${rtt / 1000} s = ${num(winMbps(win, rtt))} Mb/s.`);
}
for (const [rtt, p] of [[20, 0.001], [100, 0.01], [100, 0.0001]]) {
  ex(`Oszacuj wzorem Mathisa maksymalną przepływność jednego połączenia TCP: MSS 1460 B, RTT ${rtt} ms, utrata ${num(p * 100, 2)}%.`, 'Przepływność ≈ (MSS × 8 / RTT) × 1,22 / √p.', `(1460 × 8 / ${rtt / 1000}) × 1,22 / √${p} ≈ ${num(mathis(1460, rtt, p))} Mb/s. Wniosek: na dalekich trasach nawet niewielkie straty dramatycznie ograniczają pojedyncze połączenie.`);
}
ex('Zestaw pomiarów czasu odpowiedzi (ms): 120, 95, 110, 105, 2300, 98, 101, 130, 99, 115. Policz średnią i medianę oraz p90 metodą najbliższej rangi. Która wartość lepiej opisuje typowego użytkownika?', 'Posortuj wartości; p90 = wartość na pozycji ⌈0,9 × 10⌉ = 9.', 'Posortowane: 95, 98, 99, 101, 105, 110, 115, 120, 130, 2300. Średnia = 3273 / 10 = 327,3 ms. Mediana = (105 + 110) / 2 = 107,5 ms. p90 (9. wartość) = 130 ms, p100 = 2300 ms. Typowego użytkownika opisuje mediana; średnia jest zawyżona przez jedno żądanie 2,3 s, które warto zbadać osobno (ogon).');
ex('Usługa przyjmuje 300 żądań/s, a średni czas obsługi rośnie z 80 ms do 1,5 s z powodu wolnej bazy. Policz współbieżność przed i po zmianie oraz oceń, co stanie się z pulą 100 wątków Tomcata.', 'Prawo Little’a: L = λ × W.', 'Przed: 300 × 0,08 = 24 jednocześnie — pula 100 wystarcza. Po: 300 × 1,5 = 450 jednocześnie — pula 100 jest wyczerpana, żądania czekają w kolejce akceptora, czas odpowiedzi rośnie lawinowo, a klienci zaczynają ponawiać (dodatkowe obciążenie). Rozwiązania: krótszy timeout zapytań do bazy, circuit breaker, ograniczenie współbieżności (bulkhead), naprawa przyczyny w bazie.');
ex('Strona produktu wywołuje równolegle 30 mikroserwisów, każdy ma p99 = 400 ms i p50 = 40 ms. Jaki odsetek wyświetleń strony trafi na co najmniej jedno wywołanie z ogona? Jak to ograniczyć?', 'P = 1 − 0,99^30.', `P = 1 − 0,99^30 ≈ ${num(100 * tail(30), 1)}% wyświetleń. Ograniczanie: mniej wywołań (agregacja, cache), timeouty z degradacją (pokaż stronę bez rekomendacji), hedged requests (drugie żądanie po p95), poprawa ogona usług (GC, pule, indeksy).`);
ex('Zinterpretuj wynik curl -w: dns=0.250 connect=0.262 tls=0.290 ttfb=1.910 total=1.950 (sekundy, wartości narastające). Która faza dominuje i gdzie szukać przyczyny?', 'Odejmuj kolejne wartości, by dostać czas każdej fazy.', 'DNS 250 ms (dużo — sprawdź resolver, cache, search domains w kontenerze), TCP 12 ms, TLS 28 ms, czekanie na serwer 1620 ms (dominuje — problem w aplikacji lub jej zależnościach: baza, wywołania zewnętrzne, kolejki), transfer 40 ms. Kolejność działań: profil aplikacji i tracing (OpenTelemetry), potem DNS.');
ex('W labie dodaj tc netem z opóźnieniem 50 ms i jitterem 30 ms do kontenera klienta, a następnie uruchom ping -c 100 i curl -w 20 razy. Opisz, jak jitter wpływa na rozkład czasów HTTP w porównaniu z samym opóźnieniem.', 'tc qdisc add dev eth0 root netem delay 50ms 30ms; porównaj min/avg/max/mdev i percentyle curl.', 'Bez jittera czasy są stabilne (RTT ≈ 50 ms + baza). Z jitterem 30 ms ping pokazuje mdev ~ 20–30 ms i szeroki zakres min/max; czasy HTTP rosną w ogonie, bo każda runda (TCP, TLS, żądanie) losuje opóźnienie, a netem może też zmieniać kolejność pakietów (reordering), co wywołuje duplikaty ACK. p50 rośnie umiarkowanie, p95/p99 wyraźnie.');
ex('Zaprojektuj alert dla usługi płatności oparty na SLO: 99,5% żądań w 30 dniach ma kończyć się sukcesem w czasie < 500 ms. Jakie SLI mierzysz i jaki budżet błędów masz w miesiącu przy 10 mln żądań?', 'Budżet błędów = (1 − SLO) × liczba żądań.', 'SLI: odsetek żądań ze statusem sukcesu i czasem < 500 ms (z histogramu http.server.requests i kubełka 0,5 s). Budżet błędów: 0,5% × 10 000 000 = 50 000 „złych” żądań w miesiącu. Alert: tempo spalania budżetu (burn rate), np. ostrzeżenie, gdy w ciągu 1 h zużyto 2% budżetu, krytyczny przy 5% w 5 min — zamiast alertu na pojedynczy skok.');
ex('Analityk SOC widzi na wykresie NetFlow, że serwer aplikacji co noc o 2:00 wysyła 4 GB do adresu w nieznanym ASN, choć zwykle wysyła 50 MB na dobę. Jakie metryki i kroki zastosujesz, by ocenić, czy to eksfiltracja?', 'Zacznij od baseline i kontekstu: kto, dokąd, jakim protokołem, od kiedy.', 'Porównanie z baseline (80× więcej niż zwykle), identyfikacja procesu (EDR, ss/lsof, logi), protokół i port (np. HTTPS do nowej domeny), reputacja i rejestracja domeny/ASN, czy istnieje zadanie (np. kopia zapasowa do nowego dostawcy) — rozmowa z właścicielem. Jeśli brak uzasadnienia: izolacja hosta, zachowanie dowodów, blokada celu, analiza zakresu danych. Metryki: bajty wychodzące na cel, czas trwania sesji, liczba połączeń, nowe cele.');
ex('Wyjaśnij, dlaczego dla wideokonferencji ważniejszy jest jitter niż przepustowość, a dla kopii zapasowej odwrotnie. Jaki mechanizm w aplikacjach głosowych kompensuje jitter i jakim kosztem?', 'Media czasu rzeczywistego odtwarzają pakiety w stałym rytmie.', 'Wideo/głos potrzebują stosunkowo małej przepustowości, ale pakiety muszą przychodzić regularnie — spóźniony pakiet jest bezużyteczny. Kompensuje to bufor jittera (jitter buffer), który opóźnia odtwarzanie o kilkadziesiąt ms, by wyrównać zmienność — kosztem dodatkowego opóźnienia rozmowy. Kopia zapasowa toleruje zmienność, liczy się suma przesłanych danych w czasie (przepływność) i brak strat.');
ex('Napisz test w Javie mierzący 1000 wywołań HttpClient do lokalnej usługi i wypisujący p50, p95, p99 i max (bez zewnętrznych bibliotek). Uzasadnij wybór zegara i metody liczenia percentyli.', 'Zbierz czasy w tablicy long, posortuj, użyj metody najbliższej rangi.', 'long[] t = new long[1000]; for (int i = 0; i < t.length; i++) { long s = System.nanoTime(); client.send(req, BodyHandlers.discarding()); t[i] = System.nanoTime() - s; }\nArrays.sort(t);\nlong p(double q) { return t[(int) Math.ceil(q * t.length) - 1]; }\nWypisz p(0.5), p(0.95), p(0.99), t[t.length - 1] w ms. nanoTime jest monotoniczny (nie skacze przy korektach zegara). Do produkcji lepszy HdrHistogram lub Micrometer (stała pamięć, łączenie histogramów).');
ex('Kolega proponuje zwiększyć łącze biura z 300 Mb/s do 1 Gb/s, bo „aplikacja w chmurze w USA działa wolno”. Pomiary: RTT 110 ms, wykorzystanie łącza 15%, strona robi 40 sekwencyjnych wywołań API. Oceń propozycję.', 'Policz czas samych rund sieciowych.', 'Łącze jest wykorzystane w 15% — przepustowość nie jest wąskim gardłem. 40 sekwencyjnych wywołań × 110 ms RTT ≈ 4,4 s samego czekania na sieć, niezależnie od szybkości łącza. Pomoże: mniej rund (agregacja wywołań, równoległość, HTTP/2), cache, przeniesienie usługi lub jej repliki do regionu w Europie, CDN dla zasobów statycznych. Zwiększenie łącza nic nie da.');
ex('Porównaj w tabeli: przepustowość, przepływność i goodput dla transferu pliku 1 GB przez HTTPS po łączu 100 Mb/s, przy którym iperf3 pokazał 94 Mb/s, a pobranie pliku trwało 95 s.', 'Goodput = dane użyteczne / czas.', 'Przepustowość nominalna: 100 Mb/s. Przepływność (z nagłówkami TCP/IP, zmierzona iperf3): ~94 Mb/s. Goodput pliku: 1 GB = 8 000 Mb / 95 s ≈ 84,2 Mb/s. Różnice wynikają z nagłówków, TLS, potwierdzeń, startu połączenia (slow start) i ewentualnych retransmisji.');
ex('Sprawdź na Linuksie, czy połączenie TCP używa skalowania okna i jakie ma okno przeciążenia: uruchom duży transfer (curl lub iperf3) i obejrzyj ss -ti dst <serwer>. Wyjaśnij pola wscale, rtt, cwnd i retrans.', 'Pola pojawiają się w drugiej linii wyniku ss -ti.', 'Przykład: „wscale:7,7 rtt:41.2/3.1 cwnd:120 retrans:0/3 send 34Mbps”. wscale — współczynnik skalowania okna (2^7 = ×128) wynegocjowany w SYN; rtt — wygładzone RTT/odchylenie w ms; cwnd — okno przeciążenia w segmentach (120 × MSS ≈ 175 kB w locie); retrans — retransmisje bieżące/łączne. Brak wscale oznacza limit 64 KiB okna.');
ex('Zbadaj bufferbloat w swojej sieci: uruchom ping do bramy, a równolegle duże wysyłanie (np. iperf3 -c serwer -R lub upload pliku). Opisz zmianę RTT i zaproponuj rozwiązanie.', 'Porównaj RTT w spoczynku i pod obciążeniem.', 'Typowo RTT do bramy rośnie z 1–5 ms do 100–500 ms w trakcie uploadu, bo pakiety czekają w przepełnionych buforach modemu/routera. Rozwiązanie: włączyć SQM/AQM (fq_codel lub CAKE) z limitem nieco poniżej realnej przepustowości łącza, QoS dla ruchu interaktywnego; w serwerach — fq + BBR.');
ex('Usługa ma SLO 99,9% żądań poniżej 250 ms w 28 dni, a ruch to 2 mln żądań dziennie. Policz budżet błędów i oceń, ile minut całkowitej niedostępności (wszystkie żądania złe) zużyje cały budżet.', 'Budżet = 0,1% żądań; przy pełnej awarii zużycie jest proporcjonalne do czasu.', 'Żądania w 28 dniach: 56 mln; budżet = 56 000 złych żądań. Pełna awaria zużywa ruch ~1389 żądań/min (2 mln / 1440), więc budżet wystarczy na ok. 40 minut całkowitej niedostępności w okresie (0,1% × 28 dni × 1440 min ≈ 40,3 min).');
ex('Zmierz w Javie fazy połączenia HTTPS bez curl: czas rozwiązania nazwy (InetAddress.getByName), czas nawiązania TCP (Socket.connect) i czas handshake’u TLS (SSLSocket.startHandshake). Porównaj z curl -w.', 'Każdą fazę mierz osobno System.nanoTime().', 'long t0 = System.nanoTime(); InetAddress a = InetAddress.getByName(host); long t1 = System.nanoTime();\nSocket s = new Socket(); s.connect(new InetSocketAddress(a, 443), 3000); long t2 = System.nanoTime();\nSSLSocket ssl = (SSLSocket) ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(s, host, 443, true); ssl.startHandshake(); long t3 = System.nanoTime();\nDNS = t1 − t0, TCP = t2 − t1, TLS = t3 − t2. Uwaga: JVM cache’uje DNS (networkaddress.cache.ttl), więc drugi pomiar DNS będzie bliski zera.');
ex('Wyjaśnij, dlaczego zwiększenie timeoutu połączenia z 2 s do 60 s w kliencie, który woła niedostępną usługę 100 razy na sekundę, może położyć cały system. Użyj prawa Little’a.', 'Ile wątków będzie jednocześnie czekać?', 'Przy niedostępnej usłudze każde wywołanie czeka do końca timeoutu. Współbieżność = 100/s × 60 s = 6000 oczekujących wywołań (zamiast 200 przy 2 s). Wątki lub połączenia z puli wyczerpują się, rosną kolejki i pamięć, a awaria rozlewa się na wywołujących (kaskada). Rozwiązanie: krótkie timeouty, circuit breaker, limit współbieżności (bulkhead), szybka porażka z degradacją.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111306);
add('Czym różni się przepustowość (bandwidth) od przepływności (throughput)?', 'Przepustowość to pojemność łącza, przepływność to faktycznie przesyłana ilość danych w czasie', ['To synonimy', 'Przepływność jest zawsze większa', 'Przepustowość mierzy się w milisekundach'], 'Przepływność ograniczają m.in. okno TCP, RTT, straty i inne strumienie.');
add('Co to jest goodput?', 'Ilość użytecznych danych aplikacji dostarczonych w jednostce czasu', ['Przepływność z nagłówkami', 'Maksymalna przepustowość łącza', 'Liczba dobrych pakietów na sekundę'], 'Goodput nie liczy nagłówków, retransmisji ani narzutu protokołów.');
add('Które składowe opóźnienia skraca szybsze łącze?', 'Tylko opóźnienie transmisji (serializacji)', ['Propagację', 'Wszystkie składowe', 'Tylko przetwarzanie'], 'Propagacja zależy od odległości i prędkości sygnału.');
add('Jaka jest przybliżona prędkość sygnału w światłowodzie?', 'Około 200 000 km/s (ok. 5 µs na km)', ['300 000 km/s', '20 000 km/s', '1 000 km/s'], 'Współczynnik załamania szkła ~1,5 → około 2/3 prędkości światła w próżni.');
add('Co mierzy polecenie ping?', 'Czas w obie strony (RTT) dla pakietów ICMP echo', ['Przepustowość łącza', 'Czas odpowiedzi aplikacji HTTP', 'Liczbę skoków'], 'RTT dla ICMP może różnić się od czasu odpowiedzi aplikacji.');
add('Czym jest jitter?', 'Zmiennością opóźnienia między kolejnymi pakietami', ['Utratą pakietów', 'Maksymalną przepustowością', 'Opóźnieniem propagacji'], 'Najważniejszy dla VoIP i wideo.');
add('Czym jest bufferbloat?', 'Nadmiernym opóźnieniem wynikającym ze zbyt dużych buforów w urządzeniach przy obciążeniu', ['Przepełnieniem pamięci JVM', 'Zbyt małym oknem TCP', 'Utratą pakietów przez kabel'], 'Pomaga AQM (fq_codel, CAKE) i QoS.');
add('Co oznacza BDP (bandwidth-delay product)?', 'Ilość danych, która musi być „w locie”, by w pełni wykorzystać łącze', ['Maksymalny rozmiar pakietu', 'Liczbę pakietów traconych na sekundę', 'Czas propagacji'], 'BDP = przepustowość × RTT.');
add('Co ogranicza przepływność pojedynczego połączenia TCP niezależnie od łącza?', 'Rozmiar okna podzielony przez RTT', ['Liczba rdzeni procesora', 'Wersja HTTP', 'Długość adresu IP'], 'Nadawca może mieć w locie najwyżej jedno okno niepotwierdzonych danych.');
add('Jak utrata pakietów wpływa na TCP?', 'Wymusza retransmisje i zmniejszenie okna przeciążenia, co obniża przepływność', ['Nie ma wpływu, bo TCP jest niezawodny', 'Zwiększa przepływność', 'Powoduje przejście na UDP'], 'Wzór Mathisa: przepływność ~ 1/√p.');
add('Dlaczego średni czas odpowiedzi jest złą miarą doświadczenia użytkowników?', 'Ukrywa długi ogon wolnych żądań i nie opisuje typowego ani najgorszego przypadku', ['Bo jest zawsze równy medianie', 'Bo nie da się go policzyć', 'Bo mierzy tylko DNS'], 'Używa się percentyli i histogramów.');
add('Co oznacza p99 = 800 ms?', '99% żądań trwało nie dłużej niż 800 ms, 1% dłużej', ['Średnia wynosi 800 ms', '99 żądań trwało 800 ms', 'Maksimum to 800 ms'], 'p99 opisuje „prawie najgorszy” przypadek.');
add('Czy można policzyć p99 całej usługi, uśredniając p99 z pięciu instancji?', 'Nie — trzeba połączyć histogramy (kubełki) i dopiero z nich liczyć percentyl', ['Tak, to standardowa metoda', 'Tak, jeśli instancje mają tyle samo ruchu', 'Tak, w Prometheusie avg() robi to poprawnie'], 'Percentyle nie są addytywne.');
add('Co mówi prawo Little’a?', 'Średnia liczba obsługiwanych jednocześnie żądań = tempo napływu × średni czas obsługi', ['Przepływność = okno / RTT', 'Opóźnienie = odległość / prędkość', 'Straty rosną z kwadratem RTT'], 'L = λ × W; podstawa doboru pul i wątków.');
add('Który zegar w Javie należy stosować do pomiaru czasu trwania operacji?', 'System.nanoTime()', ['System.currentTimeMillis()', 'new Date()', 'LocalDateTime.now()'], 'nanoTime jest monotoniczny; zegar ścienny może skakać przy korektach NTP.');
add('Co oznacza „coordinated omission” w testach obciążeniowych?', 'Generator czeka na odpowiedź przed wysłaniem kolejnego żądania, przez co zaniża opóźnienia w ogonie', ['Pominięcie testów w CI', 'Brak koordynacji między zespołami', 'Utratę pakietów UDP'], 'Narzędzia ze stałym tempem (wrk2, Gatling) ograniczają ten efekt.');
add('Co zwraca curl -w %{time_appconnect}?', 'Czas od startu do zakończenia handshake’u TLS (narastająco)', ['Czas samego TLS', 'Czas DNS', 'Czas transferu treści'], 'Wartości curl -w są narastające — fazy liczy się przez odejmowanie.');
add('Która faza curl -w obejmuje czekanie na przetworzenie żądania przez serwer?', 'time_starttransfer minus time_appconnect (TTFB po TLS)', ['time_namelookup', 'time_connect minus time_namelookup', 'time_total minus time_starttransfer'], 'Między wysłaniem żądania a pierwszym bajtem odpowiedzi serwer przetwarza żądanie (plus 1 RTT).');
add('Do czego służy polecenie tc qdisc add dev eth0 root netem delay 100ms loss 1%?', 'Do celowego dodania opóźnienia i strat na interfejsie w celach testowych', ['Do przyspieszenia interfejsu', 'Do szyfrowania ruchu', 'Do blokowania portu 100'], 'Narzędzie do testów odporności (failure lab).');
add('Które narzędzie mierzy przepływność TCP/UDP między dwoma hostami?', 'iperf3', ['dig', 'nslookup', 'arp'], 'iperf3 -s na serwerze, iperf3 -c na kliencie.');
add('Co pokazuje mtr?', 'Opóźnienie i straty na każdym skoku trasy, mierzone wielokrotnie', ['Tablicę ARP', 'Certyfikat TLS', 'Wolne porty'], 'Łączy traceroute z ciągłym pingowaniem.');
add('Łącze 100 Mb/s pobiera plik 1 GB w przybliżeniu w czasie:', '80 sekund', ['10 sekund', '800 sekund', '1 sekundy'], '8 000 Mb / 100 Mb/s = 80 s (bez narzutu).');
add('Ile MB/s odpowiada przepustowości 100 Mb/s?', '12,5 MB/s', ['100 MB/s', '800 MB/s', '1,25 MB/s'], 'Bajt = 8 bitów.');
add('Dlaczego większe łącze nie przyspieszy aplikacji wykonującej 40 sekwencyjnych wywołań do odległego regionu?', 'Czas dominuje RTT każdej rundy, którego nie skraca szersze łącze', ['Bo łącze nie obsługuje HTTPS', 'Bo TCP nie działa na dużych łączach', 'Bo DNS jest zbyt wolny'], 'Pomaga mniej rund, równoległość, cache lub bliższy region.');
add('Jak wykrywa się podejrzany beaconing C2 w metrykach połączeń?', 'Po regularnych połączeniach do tego samego celu z małym rozrzutem odstępów czasowych', ['Po bardzo dużej przepustowości', 'Po braku DNS', 'Po błędach CRC'], 'Niska zmienność odstępów (mały jitter) wskazuje na automat, nie człowieka.');
add('Który objaw w metrykach sieci może być pierwszym sygnałem ataku DDoS?', 'Gwałtowny wzrost liczby połączeń, opóźnień i retransmisji względem baseline', ['Spadek liczby zapytań DNS', 'Zmiana adresu MAC bramy', 'Wydłużenie certyfikatu'], 'Baseline jest konieczny, by odróżnić atak od normalnego szczytu.');
add('Co wskazuje mdev w wyniku ping?', 'Odchylenie RTT — przybliżoną miarę jittera', ['Utratę pakietów', 'Maksymalne RTT', 'Liczbę skoków'], 'rtt min/avg/max/mdev.');
add('Jaki algorytm kontroli przeciążenia TCP często poprawia przepływność na dalekich trasach ze stratami?', 'BBR', ['Nagle', 'ARP', 'STP'], 'BBR modeluje przepustowość i RTT zamiast reagować wyłącznie na straty (jak CUBIC).');
add('Dlaczego pomiary wydajności z laptopa przez Wi-Fi bywają mylące?', 'Wi-Fi dokłada własny jitter, straty i retransmisje radiowe', ['Wi-Fi nie obsługuje TCP', 'Laptop nie ma zegara', 'Wi-Fi szyfruje dane, więc ich nie widać'], 'Mierz z hosta w tej samej sieci co produkcja.');
add('Co oznacza SLO 99,9% żądań poniżej 300 ms w miesiącu?', 'Cel wewnętrzny: co najwyżej 0,1% żądań może trwać dłużej lub zakończyć się błędem', ['Umowę z karami dla klienta', 'Średni czas 300 ms', 'Maksymalny czas 300 ms dla wszystkich żądań'], 'SLA to umowa z konsekwencjami; SLO to cel, SLI — mierzony wskaźnik.');

// generowane
for (const km of [200, 800, 2000, 6000, 15000]) {
  add(`Ile wynosi minimalne RTT wynikające wyłącznie z propagacji dla ${fmt(km)} km światłowodu (w jedną stronę)?`, `${num(2 * propMs(km))} ms`, [`${num(propMs(km))} ms`, `${num(4 * propMs(km))} ms`, `${num(2 * propMs(km) * 10)} ms`], `Jedna strona: ${fmt(km)} × 5 µs = ${num(propMs(km))} ms; RTT = 2 × ${num(propMs(km))} = ${num(2 * propMs(km))} ms.`);
}
for (const [bytes, mbps] of [[1500, 1], [1500, 100], [1500, 1000], [9000, 1000], [64, 10], [1500, 10000]]) {
  const v = serMs(bytes, mbps);
  add(`Ile trwa transmisja ramki ${fmt(bytes)} B na łączu ${fmt(mbps)} Mb/s?`, `${num(v, 4)} ms`, [`${num(v * 10, 4)} ms`, `${num(v / 10, 4)} ms`, `${num(v * 8, 4)} ms`], `${fmt(bytes)} × 8 / (${fmt(mbps)} · 10⁶) s = ${num(v, 4)} ms.`);
}
for (const [mbps, rtt] of [[100, 50], [1000, 10], [1000, 100], [10000, 1], [50, 200]]) {
  const v = Math.round(bdpBytes(mbps, rtt));
  add(`Ile wynosi BDP dla ${fmt(mbps)} Mb/s i RTT ${rtt} ms?`, `${fmt(v)} B`, [`${fmt(v * 8)} B`, `${fmt(Math.round(v / 10))} B`, `${fmt(v * 2)} B`], `${fmt(mbps)}·10⁶ × ${rtt / 1000} / 8 = ${fmt(v)} B.`);
}
for (const [win, rtt] of [[65535, 10], [65535, 50], [65535, 200], [1048576, 100]]) {
  const v = winMbps(win, rtt);
  add(`Jaka jest maksymalna przepływność połączenia TCP z oknem ${fmt(win)} B przy RTT ${rtt} ms?`, `${num(v)} Mb/s`, [`${num(v / 8)} Mb/s`, `${num(v * 2)} Mb/s`, `${num(v * 8)} Mb/s`], `${fmt(win)} × 8 / ${rtt / 1000} s = ${num(v)} Mb/s.`);
}
for (const [rtt, p] of [[10, 0.01], [50, 0.001], [100, 0.001], [200, 0.01]]) {
  const v = mathis(1460, rtt, p);
  add(`Wzór Mathisa: MSS 1460 B, RTT ${rtt} ms, straty ${num(p * 100, 2)}%. Ile w przybliżeniu wynosi maksymalna przepływność jednego połączenia?`, `${num(v)} Mb/s`, [`${num(v * 10)} Mb/s`, `${num(v / 10)} Mb/s`, `${num(v * Math.sqrt(10))} Mb/s`], `(1460 × 8 / ${rtt / 1000}) × 1,22 / √${p} ≈ ${num(v)} Mb/s.`);
}
for (const n of [5, 20, 50, 200]) {
  add(`Żądanie czeka na ${n} równoległych wywołań, z których każde z prawdopodobieństwem 1% trafia w ogon (p99). Jaki odsetek żądań trafi co najmniej raz w ogon?`, `${num(100 * tail(n), 1)}%`, [`${num(n, 1)}%`, `${num(100 * tail(n) / 2, 1)}%`, '1%'], `1 − 0,99^${n} ≈ ${num(100 * tail(n), 1)}%.`);
}
for (const [rps, ms] of [[100, 200], [500, 40], [1000, 2000], [50, 3000]]) {
  const v = rps * ms / 1000;
  add(`Usługa obsługuje ${fmt(rps)} żądań/s przy średnim czasie ${fmt(ms)} ms. Ile żądań jest średnio obsługiwanych jednocześnie?`, fmt(v), [fmt(v * 10), fmt(rps), fmt(Math.round(rps / ms * 1000))].filter((x) => x !== fmt(v)).concat([fmt(v / 2)]), `Prawo Little’a: ${fmt(rps)} × ${ms / 1000} s = ${fmt(v)}.`);
}
for (const [gb, mbps] of [[1, 100], [10, 1000], [4, 50]]) {
  const s = gb * 8000 / mbps;
  add(`Ile co najmniej trwa przesłanie ${gb} GB przez łącze ${fmt(mbps)} Mb/s (bez narzutu)?`, `${fmt(s)} s`, [`${fmt(s / 8)} s`, `${fmt(s * 8)} s`, `${fmt(s * 2)} s`], `${gb} GB = ${fmt(gb * 8000)} Mb; ${fmt(gb * 8000)} / ${fmt(mbps)} = ${fmt(s)} s.`);
}
for (const [line_, a, w] of [['dns=0.120 connect=0.135 tls=0.170 ttfb=0.200 total=0.210', 'DNS (120 ms)', ['TLS', 'czekanie na serwer', 'transfer']], ['dns=0.002 connect=0.004 tls=0.010 ttfb=1.400 total=1.420', 'czekanie na serwer (1390 ms)', ['DNS', 'TCP connect', 'TLS']], ['dns=0.003 connect=0.090 tls=0.180 ttfb=0.270 total=0.300', 'równomiernie TCP, TLS i TTFB po ok. 90 ms — duże RTT', ['DNS', 'sam transfer', 'awaria serwera']]]) {
  add(`Wynik curl -w (sekundy, narastająco): ${line_}. Co dominuje w czasie żądania?`, a, w, 'Odejmuj kolejne wartości: connect − dns = TCP, tls − connect = TLS, ttfb − tls = serwer + 1 RTT, total − ttfb = transfer.');
}
for (const mb of [10, 50, 400, 1000, 2500]) add(`Ile MB/s odpowiada przepustowości ${fmt(mb)} Mb/s?`, `${num(mb / 8)} MB/s`, [`${num(mb)} MB/s`, `${num(mb * 8)} MB/s`, `${num(mb / 10)} MB/s`], `${fmt(mb)} / 8 = ${num(mb / 8)} MB/s.`);
const sets = [[[10, 12, 11, 13, 200], 'mediana', 12], [[5, 7, 6, 8, 9, 100, 7, 6, 5, 8], 'p90 (najbliższa ranga)', 9], [[30, 31, 29, 35, 900, 32, 33, 30, 31, 34], 'mediana', 31.5], [[100, 102, 98, 99, 101, 103, 97, 500, 100, 104], 'p90 (najbliższa ranga)', 104]];
for (const [arr, kind, v] of sets) {
  const mean = arr.reduce((a, c) => a + c, 0) / arr.length;
  add(`Dla czasów ${arr.join(', ')} ms ile wynosi ${kind}?`, `${num(v, 1)} ms`, [`${num(mean, 1)} ms`, `${num(Math.max(...arr), 1)} ms`, `${num(Math.min(...arr), 1)} ms`], `Posortuj wartości: ${[...arr].sort((x, y) => x - y).join(', ')}. ${kind} = ${num(v, 1)} ms; średnia = ${num(mean, 1)} ms jest zawyżona przez wartość odstającą.`);
}
const series = [[20, 22, 20, 24, 20], [50, 50, 50, 50, 50], [10, 30, 10, 30, 10], [100, 105, 95, 100, 110]];
for (const sr of series) {
  const d = sr.slice(1).map((x, i) => Math.abs(x - sr[i]));
  const j = d.reduce((a, c) => a + c, 0) / d.length;
  add(`Kolejne RTT wynoszą ${sr.join(', ')} ms. Ile wynosi jitter liczony jako średnia bezwzględnych różnic sąsiednich pomiarów?`, `${num(j, 2)} ms`, [`${num(j * 2, 2)} ms`, `${num(Math.max(...sr) - Math.min(...sr), 2)} ms`, `${num(sr.reduce((a, c) => a + c, 0) / sr.length, 2)} ms`].filter((x) => x !== `${num(j, 2)} ms`).concat([`${num(j + 1, 2)} ms`, `${num(j + 5, 2)} ms`]), `Różnice: ${d.join(', ')}; średnia = ${num(j, 2)} ms. (RTP używa wygładzonej odmiany tego estymatora, RFC 3550.)`);
}
for (const [slo, n] of [[99.9, 1000000], [99.5, 2000000], [99.99, 50000000], [99, 300000]]) {
  const budget = Math.round((100 - slo) / 100 * n);
  add(`SLO ${num(slo, 2)}% dobrych żądań przy ${fmt(n)} żądań w okresie. Ile wynosi budżet błędów?`, `${fmt(budget)} żądań`, [`${fmt(budget * 10)} żądań`, `${fmt(Math.round(budget / 10))} żądań`, `${fmt(n - budget)} żądań`], `(100% − ${num(slo, 2)}%) × ${fmt(n)} = ${fmt(budget)}.`);
}
for (const [mbps, rtt] of [[200, 25], [1000, 50], [50, 120], [500, 8], [2000, 30]]) {
  const v = Math.round(bdpBytes(mbps, rtt));
  add(`Jakie minimalne okno TCP pozwoli jednemu połączeniu osiągnąć ${fmt(mbps)} Mb/s przy RTT ${rtt} ms?`, `${fmt(v)} B`, [`${fmt(v * 8)} B`, `${fmt(Math.round(v / 8))} B`, '65 535 B'.replace(' ', ' ')], `Okno ≥ BDP = ${fmt(mbps)}·10⁶ × ${rtt / 1000} / 8 = ${fmt(v)} B.`);
}
const scen = [['Wideokonferencja rwie się, choć łącze jest wykorzystane w 20%', 'jitter i utrata pakietów', ['przepustowość', 'liczba rdzeni serwera', 'rozmiar okna TCP']], ['Kopia zapasowa do innego kontynentu idzie powoli przy niskim wykorzystaniu łącza', 'RTT, okno TCP i straty (BDP, wzór Mathisa)', ['jitter', 'czas DNS', 'liczba skoków traceroute']], ['Użytkownicy narzekają na „czasem bardzo wolną” stronę, średnia wygląda dobrze', 'percentyle p95/p99 czasu odpowiedzi', ['średni czas odpowiedzi', 'przepustowość łącza', 'liczba adresów IP']], ['Trzeba dobrać rozmiar puli połączeń JDBC', 'tempo żądań i czas obsługi (prawo Little’a)', ['jitter', 'MTU', 'TTL']], ['Ruch wychodzący z serwera nagle wzrósł 50-krotnie w nocy', 'odchylenie przepływności od baseline (możliwa eksfiltracja)', ['p50 czasu odpowiedzi', 'czas propagacji', 'rozmiar ramki']]];
for (const [d, a, w] of scen) add(`Którą metrykę analizujesz najpierw w sytuacji: ${d}?`, a, w, `Najbardziej trafna: ${a}.`);
const comp = [['Czas wypchnięcia 1500 B na łącze 10 Mb/s', 'transmisja (serializacja)'], ['Czas przebiegu sygnału przez 3000 km światłowodu', 'propagacja'], ['Czas oczekiwania pakietu w buforze zatłoczonego routera', 'kolejkowanie'], ['Czas sprawdzenia nagłówka i wyszukania trasy w routerze', 'przetwarzanie'], ['Dodatkowe 300 ms opóźnienia przy uploadzie dużego pliku przez domowy router', 'kolejkowanie']];
const compAll = ['transmisja (serializacja)', 'propagacja', 'kolejkowanie', 'przetwarzanie'];
for (const [d, a] of comp) add(`Którą składową opóźnienia opisuje: ${d}?`, a, compAll.filter((x) => x !== a), `To ${a}.`);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
