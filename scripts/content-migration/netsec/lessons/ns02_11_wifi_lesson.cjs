// Generuje lekcje _ns_02_ethernet_lan/11_WifiForDevelopers.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_11_wifi_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/11_WifiForDevelopers.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { freq24, freq5, freq6, overlap24, mw, stats, samples, round } = require('./ns02_11_wifi_model.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const fmtMw = (v) => (v >= 1 ? `${round(v, 2)} mW` : v >= 0.001 ? `${round(v * 1000, 2)} µW` : v >= 1e-6 ? `${round(v * 1e6, 2)} nW` : `${round(v * 1e9, 2)} pW`);
const STD = [
  ['802.11b', 'brak (dawniej)', '2,4 GHz', '11 Mb/s'],
  ['802.11g', 'brak (dawniej)', '2,4 GHz', '54 Mb/s'],
  ['802.11n', 'Wi-Fi 4', '2,4 i 5 GHz', '600 Mb/s'],
  ['802.11ac', 'Wi-Fi 5', '5 GHz', '~3,5 Gb/s (wave 2)'],
  ['802.11ax', 'Wi-Fi 6 / 6E', '2,4, 5 i 6 GHz (6E)', '~9,6 Gb/s'],
  ['802.11be', 'Wi-Fi 7', '2,4, 5 i 6 GHz', '~46 Gb/s (teoretycznie)'],
];
const WI = samples('wifi'), WD = samples('wired');
const SW = stats(WI), SD = stats(WD);

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Dlaczego „u mnie działa szybko”, a w biurze laguje', ...img('ns02-11-rtt.svg', `Wykres 200 próbek RTT do tej samej bramy: przez kabel niemal płaska linia poniżej 1 ms (p50 ${SD.p50} ms, p99 ${SD.p99} ms), przez zatłoczone Wi-Fi wartości 2–6 ms z częstymi skokami do kilkudziesięciu ms i pojedynczymi powyżej 100 ms (p50 ${SW.p50} ms, p99 ${SW.p99} ms, jitter ${SW.jitter} ms).`),
    body:
`Większość developerów pracuje przez **Wi-Fi**, a większość użytkowników Twoich aplikacji — przez Wi-Fi albo sieć komórkową. Jednocześnie prawie wszystko, czego uczyliśmy się w tym rozdziale, zakłada **kabel**: pełny dupleks, stałe opóźnienie, brak kolizji. Wi-Fi (IEEE **802.11**) wygląda dla aplikacji jak Ethernet (ma adresy MAC, ramki, VLAN-y za punktem dostępowym), ale fizycznie zachowuje się zupełnie inaczej:
- **medium współdzielone i half-duplex** — w danym kanale w danej chwili nadaje jedna stacja (z wyjątkami w Wi-Fi 6/7); wszyscy dzielą **czas antenowy**,
- **retransmisje w warstwie 2** — zgubiona ramka jest powtarzana przez kartę, zanim TCP cokolwiek zauważy; dla aplikacji to „tylko” zmienne opóźnienie (**jitter**) i długi ogon **p99**,
- **zmienna prędkość** — szybkość zależy od odległości, ścian, zakłóceń i sąsiednich sieci; deklarowane „2400 Mb/s” to prędkość PHY w idealnych warunkach,
- **mobilność** — **roaming** między punktami dostępowymi, zmiana sieci, a czasem adresu IP; **captive portale** w hotelach i na lotniskach przechwytują ruch, zanim zaakceptujesz regulamin.

Konsekwencje dla Java developera: **testy wydajności przez Wi-Fi są niewiarygodne** (p99 z laptopa w kawiarni nie mówi nic o serwerze), timeouty w aplikacjach mobilnych i desktopowych muszą uwzględniać jitter i przełączenia, a połączenia długotrwałe (WebSocket, gRPC streaming) — zrywanie przy roamingu. Dla blue teamu Wi-Fi to sieć, której zasięg wychodzi poza budynek: **rogue AP**, **evil twin**, ataki na hasła WPA2-PSK, sieci gości i publiczne hotspoty.

W tej lekcji: 802.11 w skrócie (standardy, pasma, kanały), CSMA/CA i airtime, jitter i p99, roaming, captive portale, WPA2/WPA3/OWE, praca w sieciach publicznych i zadanie z planu — porównanie jitter i p99 RTT na Wi-Fi i kablu. Mapowanie: Network+ (D2: standardy 802.11, kanały, pasma, szyfrowanie), Security+ (ataki na sieci bezprzewodowe), CCNA (D1/D2: zasady działania Wi-Fi). Architektury kontrolerów i projektowanie zasięgu — w następnej lekcji.` },

  { type: 'DEFINITION', heading: 'Słownik Wi-Fi dla developera', body:
`- **802.11** — rodzina standardów Wi-Fi; nazwy handlowe: **Wi-Fi 4** (802.11n), **Wi-Fi 5** (802.11ac), **Wi-Fi 6/6E** (802.11ax), **Wi-Fi 7** (802.11be).
- **Pasma**: **2,4 GHz** (zasięg, ale tylko 3 nienakładające się kanały 20 MHz: 1, 6, 11 i dużo zakłóceń), **5 GHz** (wiele kanałów, DFS — kanały dzielone z radarami), **6 GHz** (Wi-Fi 6E/7, czyste pasmo, krótszy zasięg).
- **Kanał i szerokość** — środek kanału (2,4 GHz: f = 2407 + 5·n MHz; 5 GHz: f = 5000 + 5·n MHz; 6 GHz: f = 5950 + 5·n MHz) i szerokość 20/40/80/160/320 MHz; szerszy kanał = więcej przepustowości, mniej kanałów do rozdzielenia między AP.
- **SSID / BSSID** — nazwa sieci / MAC radia punktu dostępowego; jeden SSID może być nadawany przez setki AP.
- **CSMA/CA** — dostęp do medium: nasłuch, odczekanie (DIFS + losowy backoff), nadanie, oczekiwanie na **ACK**; brak ACK = retransmisja.
- **Airtime** — czas antenowy; wolny klient (daleko, stary standard) zajmuje go nieproporcjonalnie dużo.
- **RSSI / dBm, SNR** — siła sygnału w dBm (−30 bardzo silny, −67 próg dla VoIP, −80 słaby), szum (np. −95 dBm), **SNR = sygnał − szum** w dB.
- **MCS** — schemat modulacji i kodowania; spada wraz z jakością sygnału, a z nim prędkość PHY.
- **Roaming** — przejście klienta do innego AP; przyspieszają go **802.11r** (Fast BSS Transition), **802.11k** (lista sąsiednich AP) i **802.11v** (sugestie od sieci).
- **Captive portal** — strona logowania/regulaminu w sieci gości; przechwytuje HTTP/DNS do czasu akceptacji.
- **WPA2 / WPA3** — zabezpieczenia: **Personal** (hasło; WPA2: PSK, WPA3: **SAE**), **Enterprise** (802.1X z RADIUS, EAP-TLS/PEAP), **OWE** (Enhanced Open — szyfrowanie bez hasła), **PMF 802.11w** (ochrona ramek zarządzających).
- **Rogue AP / evil twin** — nieautoryzowany punkt dostępowy / AP podszywający się pod znany SSID; **WIPS** — system wykrywania i blokowania zagrożeń bezprzewodowych.` },

  { type: 'ANALOGY', heading: 'Sala konferencyjna z jednym mikrofonem', body:
`Kabel Ethernet to **prywatna linia telefoniczna**: każdy ma swoją, rozmowa w obie strony naraz, bez zakłóceń.

Wi-Fi to **sala konferencyjna z jednym mikrofonem** (kanał):
- Zanim zaczniesz mówić, **słuchasz**, czy ktoś nie mówi, i odczekujesz losową chwilę (CSMA/CA, backoff). Gdy dwie osoby zaczną naraz — nikt nic nie rozumie (kolizja), więc obie powtarzają po chwili (retransmisja).
- Prowadzący po każdej Twojej wypowiedzi kiwa głową (ACK). Jeśli nie kiwnął — powtarzasz. Słuchacze w ostatnim rzędzie (daleko od AP) muszą mówić **wolno i wyraźnie** (niski MCS) — zajmują mikrofon dłużej i wszyscy czekają (airtime).
- Sala obok ma **ten sam mikrofon na tej samej częstotliwości** (sąsiednia sieć na tym samym kanale) — słychać ich, więc też czekacie. Dlatego w 2,4 GHz są tylko trzy „sale”, które sobie nie przeszkadzają (kanały 1, 6, 11).
- **Roaming** to przechodzenie z sali do sali w trakcie rozmowy — jeśli nowa sala nie wie, kim jesteś (brak 802.11r), musisz się ponownie przedstawić i rozmowa na chwilę się urywa.
- **Captive portal** to bramkarz przy wejściu do budynku: zanim podpiszesz regulamin, każde Twoje pytanie („gdzie jest google.com?”) kończy się odpowiedzią „najpierw recepcja”.
- **Evil twin** to oszust, który rozstawia własną salę z identycznym szyldem tuż przy wejściu — wchodzisz do niego, bo jest bliżej (silniejszy sygnał).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Kanały, częstotliwości i moc sygnału w liczbach', ...img('ns02-11-channels.svg', 'Widmo pasma 2,4 GHz od 2400 do 2490 MHz z trzynastoma kanałami co 5 MHz o szerokości około 22 MHz; wyróżnione kanały 1, 6 i 11, które się nie nakładają, pozostałe zachodzą na sąsiednie. W pasmach 5 i 6 GHz kanałów jest dużo więcej.'),
    body:
`Obliczenia przydatne przy czytaniu skanerów Wi-Fi (\`nmcli dev wifi\`, \`iw dev wlan0 scan\`, \`netsh wlan show networks mode=bssid\`) i na egzaminie Network+. Skala **dBm** jest logarytmiczna: +3 dB ≈ dwa razy więcej mocy, +10 dB = dziesięć razy więcej.`,
    code:
`2,4 GHz: ${[1, 6, 11, 13].map((c) => `kanał ${c} = ${freq24(c)} MHz`).join(', ')}
5 GHz:   ${[36, 40, 100, 149].map((c) => `kanał ${c} = ${freq5(c)} MHz`).join(', ')}
6 GHz:   ${[1, 37, 93].map((c) => `kanał ${c} = ${freq6(c)} MHz`).join(', ')}
Czy kanały 2,4 GHz się nakładają (odległość środków < 20 MHz)?
  1 i 6: ${overlap24(1, 6) ? 'tak' : 'nie'}   1 i 4: ${overlap24(1, 4) ? 'tak' : 'nie'}   6 i 11: ${overlap24(6, 11) ? 'tak' : 'nie'}   3 i 6: ${overlap24(3, 6) ? 'tak' : 'nie'}

Moc: mW = 10^(dBm/10)
${[20, 0, -30, -67, -80].map((d) => `  ${String(d).padStart(4)} dBm = ${fmtMw(mw(d))}`).join('\n')}
SNR = sygnał − szum: −60 dBm − (−95 dBm) = 35 dB (bardzo dobrze); −80 − (−92) = 12 dB (słabo, niskie MCS, retransmisje)
Progi praktyczne: ≥ −67 dBm i SNR ≥ 25 dB dla VoIP/wideo; < −75 dBm — spodziewaj się problemów.` },

  { type: 'CODE_BASIC', heading: 'Diagnoza Wi-Fi z linii poleceń', body:
`Zanim uznasz, że „serwer jest wolny”, sprawdź swoje łącze radiowe: siłę sygnału, kanał, prędkość PHY, retransmisje.`,
    code:
`# Linux
nmcli -f IN-USE,SSID,BSSID,CHAN,RATE,SIGNAL,SECURITY dev wifi       # sieci w zasięgu, kanały, sygnał
iw dev wlan0 link                 # SSID, BSSID, freq, signal: -58 dBm, tx bitrate: 866.7 MBit/s ...
iw dev wlan0 station dump         # tx retries, tx failed, beacon loss — retransmisje L2!
watch -n1 "iw dev wlan0 link"     # obserwuj sygnał podczas chodzenia po biurze (roaming)

# Windows
netsh wlan show interfaces        # SSID, BSSID, kanał, Signal %, Receive/Transmit rate, uwierzytelnianie
netsh wlan show networks mode=bssid
netsh wlan show wlanreport        # raport HTML: rozłączenia, roaming, przyczyny

# macOS
/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -I   # starsze wersje
wdutil info                       # nowsze wersje (sudo)

# Pomiar jakości ścieżki: opóźnienie i jego zmienność
ping -i 0.2 -c 300 <brama>        # najpierw do bramy — to izoluje Wi-Fi od Internetu
mtr -n -i 0.2 -c 300 <serwer>     # potem do celu: gdzie rośnie opóźnienie i straty` },

  { type: 'STEP_BY_STEP', heading: 'Zadanie z planu: porównaj jitter i p99 RTT na Wi-Fi i kablu', ...img('ns02-11-csma.svg', 'Oś czasu trzech stacji w jednym kanale: laptop czeka DIFS i losowy backoff, nadaje ramkę, AP odsyła ACK; telefon sąsiada nadaje, laptop czeka; potem kolizja i retransmisja. Wnioski: retransmisje L2 zwiększają opóźnienie, airtime jest współdzielony, RTT zmienne, przepustowość ok. 50–60% PHY.'),
    body:
`Eksperyment na własnym sprzęcie — najlepiej laptop z kartą Wi-Fi i portem Ethernet (lub przejściówką USB):
1. **Przygotuj cel**: brama domowa/biurowa (izoluje sieć lokalną) oraz serwer w Internecie lub w chmurze (pełna ścieżka).
2. **Pomiar przez kabel**: wyłącz Wi-Fi, podłącz kabel, \`ping -i 0.2 -c 300 <brama> > wired.txt\` (Windows: \`ping -n 300 <brama>\`) oraz \`mtr -n -r -c 300 -i 0.2 <serwer>\`.
3. **Pomiar przez Wi-Fi**: odłącz kabel, włącz Wi-Fi, te same polecenia do \`wifi.txt\`. Powtórz w trzech miejscach: obok AP, za dwiema ścianami, w trakcie dużego pobierania (np. aktualizacja systemu) na innym urządzeniu.
4. **Policz statystyki**: wyciągnij czasy (\`grep -oP 'time=\\K[0-9.]+' wifi.txt\`) i policz min, p50, p90, p99, max i jitter (średnia różnica kolejnych próbek) — np. w jshell lub skryptem z bloku CODE_PRACTICAL.
5. **Sprawdź retransmisje L2**: \`iw dev wlan0 station dump\` przed i po teście — przyrost \`tx retries\`.
6. **Test przepustowości**: \`iperf3 -c <serwer w LAN> -t 20\` i \`-R\` (odwrotny kierunek) dla kabla i Wi-Fi; porównaj z prędkością PHY z \`iw dev wlan0 link\`.
7. **Zapisz tabelę**: miejsce/medium, sygnał (dBm), p50, p99, jitter, straty, przepustowość. Typowo: kabel p99 < 1 ms do bramy, Wi-Fi p99 kilka–kilkadziesiąt ms, w zatłoczonym miejscu ponad 100 ms.
8. **Wniosek**: testy wydajności i SLO mierz z sieci zbliżonej do produkcyjnej (kabel, ten sam region chmury), a aplikacje kliencie projektuj z myślą o ogonie p99 Wi-Fi.` },

  { type: 'NOTE', heading: 'Standardy 802.11 i co zmieniają dla aplikacji', body:
`Tabela, którą warto znać na Network+ — z dopiskiem, co oznacza w praktyce:`,
    code:
`${STD.map(([s, n, b, r]) => `${s.padEnd(9)} ${n.padEnd(15)} ${b.padEnd(22)} maks. PHY ${r}`).join('\n')}

Wi-Fi 6/6E: OFDMA (wielu klientów w jednej transmisji), MU-MIMO w obie strony, BSS coloring (mniej czekania
            na sąsiednie sieci), TWT (oszczędzanie baterii IoT) → mniejszy jitter w zatłoczonych sieciach.
Wi-Fi 7:    kanały 320 MHz, 4096-QAM, MLO (Multi-Link Operation — jednoczesne łącza w kilku pasmach
            → mniejsze opóźnienia i płynniejszy roaming).
Realna przepustowość TCP: ok. 50–60% prędkości PHY (narzut MAC, ACK, half-duplex, retransmisje),
dzielona między wszystkich klientów w kanale.` },

  { type: 'CODE_PRACTICAL', heading: 'Statystyki RTT w Javie: p50, p99, jitter', body:
`Prosty program liczący statystyki z wyjścia \`ping\`. Ten sam sposób myślenia (percentyle zamiast średniej) stosujesz w metrykach aplikacji (Micrometer, histogramy). Średnia ukrywa ogon, który widzą użytkownicy Wi-Fi.`,
    code:
`import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class RttStats {
    public static void main(String[] args) throws Exception {
        Pattern p = Pattern.compile("time[=<]([0-9.]+)");
        List<Double> rtt = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of(args[0]))) {
            Matcher m = p.matcher(line);
            if (m.find()) rtt.add(Double.parseDouble(m.group(1)));
        }
        double jitter = 0;
        for (int i = 1; i < rtt.size(); i++) jitter += Math.abs(rtt.get(i) - rtt.get(i - 1));
        jitter /= Math.max(1, rtt.size() - 1);                  // średnia różnica kolejnych próbek
        List<Double> s = rtt.stream().sorted().toList();
        System.out.printf("n=%d p50=%.1f p90=%.1f p99=%.1f max=%.1f jitter=%.2f ms%n",
            s.size(), pct(s, 50), pct(s, 90), pct(s, 99), s.getLast(), jitter);
    }
    static double pct(List<Double> s, double p) {               // metoda nearest-rank
        return s.get((int) Math.min(s.size() - 1, Math.ceil(p / 100 * s.size()) - 1));
    }
}
// Przykład dla danych z ilustracji: kabel p50=${SD.p50} p99=${SD.p99} jitter=${SD.jitter}; Wi-Fi p50=${SW.p50} p99=${SW.p99} jitter=${SW.jitter}` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', ...img('ns02-11-security.svg', 'Tabela trybów zabezpieczeń Wi-Fi: sieć otwarta i WEP/WPA z TKIP niebezpieczne; WPA2-Personal warunkowo (atak słownikowy offline); WPA2-Enterprise bezpieczne przy weryfikacji certyfikatu; WPA3-Personal z SAE i WPA3-Enterprise bezpieczne; OWE szyfruje bez hasła; PMF chroni ramki zarządzające.'),
    body:
`**Developer:**
- **Testy wydajności i benchmarki** — nigdy przez Wi-Fi, jeśli wynik ma coś mówić o serwerze. Uruchamiaj load testy z maszyny w tej samej sieci/regionie co system (kabel, chmura). Wyniki z laptopa w biurze mierzą głównie Twoje radio.
- **Timeouty i retry w klientach** (aplikacje desktopowe, mobilne, IoT) — ustawiaj z uwzględnieniem p99/p99.9 sieci bezprzewodowej i krótkich przerw przy roamingu; stosuj retry z backoffem i idempotencję.
- **Połączenia długotrwałe** — WebSocket, gRPC streaming, SSE zrywają się przy zmianie sieci lub adresu; potrzebne heartbeaty i automatyczne wznawianie (oraz QUIC/HTTP/3, które obsługują **connection migration**).
- **Captive portal** — aplikacja mobilna/desktopowa może dostać przekierowanie HTTP lub fałszywą odpowiedź DNS zamiast swojego API; obsługuj błędy TLS i nieoczekiwane odpowiedzi jako „brak sieci”, nie jako dane. Systemy wykrywają portal, odpytując znane adresy (np. \`connectivitycheck.gstatic.com/generate_204\`, \`captive.apple.com\`).
- **Wideokonferencje i VoIP** — wrażliwe na jitter; QoS (WMM) i 5/6 GHz pomagają.

**Blue team:**
- **Rogue AP** — nieautoryzowany AP podłączony do sieci firmowej (np. przez pracownika) omija segmentację i kontrolę dostępu; wykrywanie: WIPS kontrolera, porównanie BSSID z inwentarzem, LLDP/port na przełączniku (lekcja 09), 802.1X na portach.
- **Evil twin** — AP z tym samym SSID i silniejszym sygnałem; w WPA2-Enterprise bez weryfikacji certyfikatu serwera RADIUS klient oddaje poświadczenia (MSCHAPv2) podstawionemu serwerowi. Obrona: EAP-TLS lub PEAP z **wymuszoną walidacją certyfikatu i nazwy serwera** (profil MDM), WPA3, PMF.
- **Ataki na WPA2-PSK** — przechwycony 4-way handshake lub PMKID pozwala na atak słownikowy **offline**; obrona: długie losowe hasła, WPA3-SAE, Enterprise w firmach. **Deauthentication** (wymuszone rozłączenie) — obrona PMF (802.11w).
- **Sieci publiczne** — zakładaj, że sieć jest wroga: TLS z weryfikacją certyfikatów (żadnego \`trustAll\` w aplikacjach!), HSTS, VPN firmowy z „always-on”, brak udostępniania plików, firewall hosta w profilu „publiczny”.
- **Zasięg poza budynkiem** — sieć firmową słychać na parkingu; segmentacja (VLAN dla gości i IoT, lekcja 05), 802.1X, monitoring nowych klientów i nieudanych uwierzytelnień w SIEM.` },

  { type: 'PITFALL', heading: 'Częste błędy i mity', body:
`- **„Mam Wi-Fi 6 na 2,4 Gb/s, więc mam 2,4 Gb/s”** — to prędkość PHY w idealnych warunkach; realny TCP to ok. połowa, dzielona między klientów, malejąca z odległością.
- **Benchmark przez Wi-Fi** — wynik p99 i throughput mierzy radio, nie aplikację; wnioski o regresji wydajności są fałszywe.
- **Średnia zamiast percentyli** — średnie RTT 6 ms wygląda dobrze, ale p99 130 ms oznacza, że co setne żądanie użytkownika czeka 20 razy dłużej.
- **Kanały 2,4 GHz inne niż 1/6/11** — kanał 3 zakłóca zarówno 1, jak i 6; w biurze lepiej przenieść ruch na 5/6 GHz.
- **Szerokie kanały wszędzie** — 160 MHz w gęstym biurze to mniej kanałów do rozdzielenia i więcej zakłóceń między AP; czasem 20/40 MHz daje lepszy wynik zbiorczy.
- **„Mocniejszy sygnał AP = lepiej”** — za duża moc AP sprawia, że klienci trzymają się dalekiego AP (sticky client) zamiast przejść do bliższego; projektuje się pokrycie, nie maksymalną moc.
- **Ignorowanie roamingu** — aplikacja traktuje chwilową przerwę jako błąd krytyczny; potrzebne retry i wznawianie.
- **WPA2-Enterprise bez walidacji certyfikatu** — klienci łączą się z każdym serwerem RADIUS, także podstawionym (evil twin) — wyciek haseł domenowych.
- **„Ukryty SSID to zabezpieczenie”** — nie; klienci i tak rozgłaszają nazwę w Probe Request (co dodatkowo ujawnia ją wszędzie, gdzie jesteś).
- **Filtrowanie MAC jako ochrona** — adresy MAC widać w eterze i łatwo je podrobić; a nowoczesne systemy losują MAC per sieć (prywatność), co psuje filtry i rezerwacje DHCP.` },

  { type: 'CODE_WRONG', heading: 'Źle: klient HTTP „z laptopa w kawiarni” i trust-all', body:
`Klient dla aplikacji desktopowej: brak timeoutów lub zbyt krótkie, brak retry, a „żeby działało w hotelu” — wyłączona weryfikacja certyfikatów.`,
    code:
`// Wyłączona weryfikacja TLS „bo w hotelu był błąd certyfikatu” (captive portal!)
SSLContext ctx = SSLContext.getInstance("TLS");
ctx.init(null, new TrustManager[]{ new X509TrustManager() {
    public void checkClientTrusted(X509Certificate[] c, String a) {}
    public void checkServerTrusted(X509Certificate[] c, String a) {}   // akceptuje każdy certyfikat
    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
}}, null);
HttpClient client = HttpClient.newBuilder().sslContext(ctx).build();      // brak connectTimeout
HttpRequest req = HttpRequest.newBuilder(URI.create("https://api.example.com/sync"))
    .timeout(Duration.ofMillis(200))                                       // „u mnie na kablu wystarcza”
    .build();
// Skutki: w sieci publicznej evil twin/captive portal podsuwa własny certyfikat → MITM, wyciek tokenów;
// na Wi-Fi co setne żądanie przekracza 200 ms → losowe błędy użytkowników; brak retry przy roamingu.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: weryfikacja TLS, realistyczne timeouty, retry i obsługa portalu', body:
`Domyślna weryfikacja certyfikatów, timeouty dobrane do ogona sieci bezprzewodowej, retry z backoffem tylko dla operacji idempotentnych i rozpoznawanie captive portalu jako „brak sieci”.`,
    code:
`HttpClient client = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(5))            // zestawienie przez Wi-Fi/roaming bywa wolne
    .version(HttpClient.Version.HTTP_2)
    .build();                                         // domyślny SSLContext = pełna weryfikacja

<T> HttpResponse<T> sendWithRetry(HttpRequest req, HttpResponse.BodyHandler<T> h) throws Exception {
    Duration backoff = Duration.ofMillis(300);
    for (int attempt = 1; ; attempt++) {
        try {
            return client.send(req, h);
        } catch (HttpTimeoutException | ConnectException e) {
            if (attempt == 4) throw e;                // tylko dla żądań idempotentnych (GET, PUT z kluczem)
            Thread.sleep(backoff.toMillis() + ThreadLocalRandom.current().nextLong(200));
            backoff = backoff.multipliedBy(2);
        } catch (SSLHandshakeException e) {
            throw new NoNetworkException("Sieć przechwytuje ruch (captive portal?) — zaloguj się w przeglądarce", e);
        }
    }
}
// Żądanie: HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(10)) — limit całego żądania, nie 200 ms.
// Połączenia długotrwałe: heartbeat + automatyczne wznowienie po zmianie sieci.` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy Wi-Fi, kiedy kabel — praktyczne zasady', body:
`- **Kabel**: serwery (zawsze), stacje do testów wydajności i pomiarów, stanowiska z wideokonferencjami całodziennymi, urządzenia stacjonarne o wysokich wymaganiach (NAS, stacje robocze CI), sprzęt sieciowy i kamery tam, gdzie się da.
- **Wi-Fi**: mobilność, laptopy, telefony, sale konferencyjne, goście, IoT bez kabla. Projektuj aplikacje klienckie pod Wi-Fi: percentyle, retry, wznawianie.
- **Pasma**: 5 GHz i 6 GHz dla firm i ruchu wrażliwego na opóźnienia; 2,4 GHz dla zasięgu i starych urządzeń IoT (oddzielny SSID/VLAN).
- **Zabezpieczenia**: firmy — **WPA3-Enterprise / WPA2-Enterprise z EAP-TLS** (certyfikaty urządzeń) i walidacją serwera; dom/małe biuro — **WPA3-Personal** (lub WPA2/WPA3 transition) z długim hasłem; goście — osobny SSID, VLAN, izolacja klientów, OWE lub portal; zawsze PMF.
- **Praca w sieci publicznej**: VPN, TLS wszędzie, aplikacje z poprawną walidacją certyfikatów, unikanie wrażliwych operacji bez VPN.
- **Mierz i monitoruj** — po stronie sieci: kontroler/WIPS (rogue AP, nieudane 802.1X, sygnał i retry klientów); po stronie aplikacji: metryki RUM (real user monitoring) z rozbiciem na typ połączenia.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Wi-Fi (802.11) to medium współdzielone i half-duplex z CSMA/CA, ACK i retransmisjami L2 → zmienne RTT, jitter, długi ogon p99, realny TCP ~50–60% PHY.
- Standardy: Wi-Fi 4 (n), 5 (ac), 6/6E (ax), 7 (be); pasma 2,4 GHz (kanały 1/6/11), 5 GHz (DFS), 6 GHz.
- Częstotliwości: 2,4 GHz f = 2407 + 5n; 5 GHz f = 5000 + 5n; 6 GHz f = 5950 + 5n. Moc: mW = 10^(dBm/10); SNR = sygnał − szum.
- Diagnoza: iw dev wlan0 link / station dump, nmcli dev wifi, netsh wlan show interfaces / wlanreport, ping do bramy i mtr.
- Aplikacje: nie testuj wydajności przez Wi-Fi, mierz percentyle, timeouty i retry z backoffem, wznawianie połączeń przy roamingu, rozpoznawanie captive portalu bez wyłączania TLS.
- Bezpieczeństwo: WPA3 (SAE), Enterprise z walidacją certyfikatu, PMF, OWE dla sieci otwartych; zagrożenia: rogue AP, evil twin, atak słownikowy na PSK, deauth; w sieciach publicznych VPN i TLS.
- Następna lekcja: **Architektury Wi-Fi — autonomiczne AP, kontroler WLC i chmura**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [band, ch] of [['2,4', 11], ['5', 44], ['6', 69]]) {
  const f = band === '2,4' ? freq24(ch) : band === '5' ? freq5(ch) : freq6(ch);
  ex(`Oblicz częstotliwość środkową kanału ${ch} w paśmie ${band} GHz.`, '2,4 GHz: 2407 + 5n; 5 GHz: 5000 + 5n; 6 GHz: 5950 + 5n (MHz).', `Kanał ${ch}: ${f} MHz.`);
}
ex('Które pary kanałów 2,4 GHz (szerokość 20 MHz) się nakładają: 1–5, 1–6, 4–9, 6–11, 2–7? Uzasadnij obliczeniem.', 'Środki co 5 MHz; nakładanie, gdy odległość środków < 20 MHz.', [[1, 5], [1, 6], [4, 9], [6, 11], [2, 7]].map(([a, b]) => `${a}–${b}: ${Math.abs(freq24(a) - freq24(b))} MHz → ${overlap24(a, b) ? 'nakładają się' : 'nie nakładają się (minimalnie)'}`).join('; ') + '. W praktyce widmo ma ~22 MHz, dlatego standardem jest 1/6/11 (odstęp 25 MHz).');
for (const [s, n] of [[-55, -95], [-78, -90]]) ex(`Sygnał ${s} dBm, szum ${n} dBm. Oblicz SNR i moc sygnału w miliwatach. Czy łącze nada się do rozmowy wideo?`, 'SNR = sygnał − szum; mW = 10^(dBm/10).', `SNR = ${s} − (${n}) = ${s - n} dB; moc = ${fmtMw(mw(s))}. ${s - n >= 25 && s >= -67 ? 'Tak — sygnał i SNR powyżej progów (−67 dBm, 25 dB).' : 'Nie — za słaby sygnał/SNR: niskie MCS, retransmisje, jitter; potrzebny bliższy AP lub inne pasmo.'}`);
for (const set of [[3.1, 2.9, 3.4, 15.2, 3.0, 3.3, 2.8, 48.7, 3.2, 3.0], [0.7, 0.8, 0.7, 0.9, 0.7, 0.8, 0.7, 0.8, 0.7, 0.8], [5, 6, 30, 5, 6, 7, 5, 120, 6, 5]]) {
  const st = stats(set);
  ex(`Z próbek RTT [${set.join(', ')}] ms policz: min, p50, p90, max, średnią i jitter (średnia bezwzględna różnica kolejnych próbek). Co pokazuje porównanie średniej i p50?`, 'Posortuj; percentyl metodą nearest-rank; jitter z kolejności pomiarów.', `min ${st.min}, p50 ${st.p50}, p90 ${st.p90}, max ${st.max}, średnia ${st.mean}, jitter ${st.jitter} ms. ${st.mean > st.p50 * 1.5 ? 'Średnia jest wyraźnie wyższa niż mediana — pojedyncze skoki (retransmisje, zakłócenia) zawyżają średnią; typowy użytkownik widzi p50, ale ogon psuje doświadczenie.' : 'Średnia bliska medianie — stabilne łącze (typowe dla kabla).'}`);
}
const more = [
  ['Wykonaj zadanie z planu lekcji: porównaj jitter i p99 RTT do bramy i do serwera w Internecie przez kabel i przez Wi-Fi (trzy miejsca). Przedstaw tabelę i wnioski.', 'ping -i 0.2 -c 300; statystyki programem z lekcji; iw station dump.', 'Przykładowy wynik: kabel → brama: p50 0,6 ms, p99 0,9 ms, jitter 0,1 ms; Wi-Fi obok AP (−45 dBm): p50 3 ms, p99 12 ms, jitter 2 ms; Wi-Fi za dwiema ścianami (−72 dBm): p50 6 ms, p99 60 ms, jitter 9 ms, tx retries +15%; Wi-Fi przy dużym pobieraniu na innym urządzeniu: p99 > 100 ms. Do serwera w Internecie różnica median maleje względnie (dominuje WAN), ale ogon Wi-Fi nadal jest widoczny. Wniosek: pomiary wydajności przez kabel; aplikacje klienckie odporne na ogon p99.'],
  ['Laptop pokazuje prędkość łącza Wi-Fi 1201 Mb/s, a iperf3 do serwera w LAN daje 420 Mb/s. Wyjaśnij różnicę.', 'PHY vs przepustowość TCP; half-duplex, narzut, airtime.', '1201 Mb/s to prędkość PHY (MCS przy danym sygnale i szerokości kanału) — surowa szybkość modulacji. Realny TCP jest niższy przez: narzut MAC i nagłówków, ACK warstwy 2 i TCP, przerwy międzyramkowe i backoff (CSMA/CA), half-duplex (ACK TCP w przeciwnym kierunku zajmują ten sam kanał), retransmisje, innych klientów w kanale. 35–60% PHY to typowy wynik.'],
  ['Aplikacja desktopowa w Javie zgłasza losowe „Connection reset” u użytkowników chodzących z laptopami po biurze, ale nie u pracujących przy biurkach. Jaka hipoteza i co zmienisz w aplikacji?', 'Roaming między AP.', 'Hipoteza: roaming między punktami dostępowymi (lub zmiana sieci/adresu) przerywa długotrwałe połączenia TCP/WebSocket; przy wolnym roamingu (bez 802.11r) przerwa trwa setki ms–sekundy, a jeśli zmienia się adres IP lub NAT — połączenie jest zrywane. Zmiany: heartbeaty i automatyczne wznawianie połączeń, retry idempotentnych żądań z backoffem, buforowanie zmian offline; po stronie sieci — 802.11r/k/v, jeden VLAN dla SSID w obszarze roamingu. Diagnoza: netsh wlan show wlanreport — zdarzenia roamingu w czasie błędów.'],
  ['Jak aplikacja mobilna lub desktopowa może wykryć captive portal i jak powinna się zachować?', 'Zapytanie o znany adres zwracający 204.', 'Wykrycie: żądanie HTTP do adresu, który normalnie zwraca 204 No Content lub znaną treść (np. http://connectivitycheck.gstatic.com/generate_204, http://captive.apple.com, http://www.msftconnecttest.com/connecttest.txt); odpowiedź 302/200 z inną treścią = portal. Zachowanie: pokazać użytkownikowi komunikat i otworzyć przeglądarkę/systemowy ekran logowania; nie wysyłać danych i tokenów; błędy TLS w tym czasie traktować jako brak sieci, a nie wyłączać weryfikacji certyfikatów.'],
  ['Opisz koncepcyjnie atak evil twin na sieć WPA2-Enterprise z PEAP-MSCHAPv2 i trzy środki zaradcze.', 'Brak walidacji certyfikatu RADIUS.', 'Napastnik uruchamia AP z tym samym SSID i własnym serwerem RADIUS. Klient bez wymuszonej walidacji certyfikatu serwera (lub użytkownik klikający „akceptuj”) zestawia tunel PEAP z podstawionym serwerem i wysyła wyzwanie-odpowiedź MSCHAPv2, którą da się złamać offline — wyciek hasła domenowego. Środki: (1) profil Wi-Fi z MDM/GPO wymuszający walidację certyfikatu CA i nazwy serwera RADIUS; (2) EAP-TLS z certyfikatami urządzeń zamiast haseł; (3) WIPS wykrywający obce AP z firmowym SSID, PMF, szkolenie użytkowników.'],
  ['Wyjaśnij, dlaczego atak na WPA2-PSK jest możliwy offline i czym różni się od tego WPA3-SAE.', 'Handshake/PMKID jako materiał do zgadywania hasła.', 'W WPA2-PSK klucz PMK wyprowadza się bezpośrednio z hasła i SSID (PBKDF2); przechwycony 4-way handshake lub PMKID pozwala sprawdzać kandydatów hasła offline, bez kontaktu z siecią — słabe hasło pada szybko na GPU. WPA3-SAE (Dragonfly) wykonuje wymianę klucza odporną na ataki słownikowe offline: każda próba hasła wymaga interakcji z AP (atak online, łatwy do ograniczenia), a przechwycona wymiana nie wystarcza do zgadywania. Dodatkowo daje forward secrecy.'],
  ['Zaprojektuj sieć Wi-Fi dla małej firmy (biuro 40 osób, goście, drukarki i IoT): SSID, zabezpieczenia, VLAN-y, pasma.', 'Osobne SSID/VLAN dla stref; WPA3; izolacja gości.', 'SSID „CORP”: WPA3-Enterprise (lub WPA2/3-Enterprise) z EAP-TLS, VLAN 10, 5/6 GHz, PMF wymagane, 802.11r/k/v. SSID „GUEST”: OWE lub WPA3-Personal z rotowanym hasłem/portal, VLAN 60 z dostępem tylko do Internetu, izolacja klientów, limity przepustowości. SSID „IOT”: WPA2/3-Personal z osobnym długim hasłem (stare urządzenia), 2,4 GHz, VLAN 50 z regułami tylko do potrzebnych usług. Kanały 1/6/11 w 2,4 GHz, 20/40 MHz w 5 GHz przy gęstym rozmieszczeniu AP. Monitoring: WIPS, logi RADIUS w SIEM.'],
  ['Wyjaśnij, dlaczego ukrycie SSID i filtrowanie MAC nie są skutecznymi zabezpieczeniami.', 'Probe Request; MAC widoczny w eterze.', 'Ukryty SSID nadal pojawia się w ramkach Probe Request/Response i Association, gdy klienci się łączą — każdy skaner go odczyta; dodatkowo klienci aktywnie wołają ukrytą nazwę wszędzie, ujawniając ją (i umożliwiając evil twin). Filtrowanie MAC: adresy autoryzowanych klientów są widoczne w nieszyfrowanych nagłówkach ramek; podrobienie MAC to jedno polecenie. Do tego współczesne systemy używają losowych MAC per sieć. Skuteczne: WPA3/Enterprise, segmentacja, 802.1X.'],
  ['W biurze wszystkie AP pracują w 2,4 GHz na kanałach 1, 3, 6, 9 i 11 z maksymalną mocą. Co poprawisz i dlaczego?', 'Nakładające się kanały, moc, pasmo.', 'Kanały 3 i 9 nakładają się z 1/6 i 6/11 — wzajemne zakłócenia (nie tylko „czekanie” jak przy tym samym kanale, ale szum obniżający SNR). Zmiana: tylko 1/6/11 w 2,4 GHz (lub wyłączenie 2,4 GHz na części AP), przeniesienie klientów na 5/6 GHz (band steering), obniżenie mocy AP (mniejsze komórki, lepszy roaming, mniej zakłóceń współkanałowych), automatyczne planowanie kanałów (RRM) na kontrolerze — lekcja 12.'],
  ['Wyjaśnij, czym są retransmisje warstwy 2 w Wi-Fi i dlaczego TCP często ich „nie widzi”. Jak je zmierzyć?', 'ACK 802.11, retry, iw station dump.', 'Każda ramka unicast w Wi-Fi wymaga ACK od odbiorcy; brak ACK powoduje ponowną transmisję przez kartę (do kilku razy), zanim ramka zostanie uznana za utraconą. TCP widzi tylko opóźnienie (pakiet doszedł później), nie stratę — więc nie ma retransmisji TCP, ale rośnie jitter i p99. Pomiar: iw dev wlan0 station dump (tx retries, tx failed), na kontrolerze statystyki retry rate klientów, w Wiresharku z trybem monitor flaga Retry w nagłówku 802.11.'],
  ['Pracujesz zdalnie z hotelowej sieci Wi-Fi. Wymień 6 praktyk, które chronią Ciebie i dostęp do firmowych systemów.', 'Sieć wroga.', '1) VPN firmowy (najlepiej always-on) przed jakimkolwiek dostępem do zasobów. 2) Tylko HTTPS z poprawną walidacją certyfikatów; ostrzeżenie o certyfikacie = przerwij, nie klikaj „kontynuuj”. 3) Profil sieci „publiczna” w systemie (firewall blokuje przychodzące, wyłączone udostępnianie). 4) Uważaj na captive portal — loguj się w przeglądarce, bez podawania haseł firmowych. 5) MFA dla kont (przechwycone hasło nie wystarczy). 6) Hotspot z telefonu jako alternatywa dla wrażliwych operacji; aktualny system i przeglądarka.'],
  ['Kontener lub maszyna wirtualna na laptopie w sieci Wi-Fi nie dostaje adresu z sieci w trybie bridge, choć na kablu działa. Dlaczego?', 'Wi-Fi w trybie klienta i adresy MAC.', 'Karta Wi-Fi w trybie klienta (station) może nadawać ramki tylko z własnym adresem MAC uwierzytelnionym przy AP; ramki z innymi MAC (maszyny wirtualnej za bridge’em) są odrzucane przez AP (4-adresowy tryb/WDS zwykle niedostępny). Dlatego hiperwizory i Docker na Wi-Fi używają NAT (lub macvtap/ipvlan L3), a bridge do sieci fizycznej działa tylko na kablu.'],
];
for (const [q, h, a] of more) ex(q, h, a);
ex('Przygotuj listę kontrolną audytu Wi-Fi firmy (minimum 7 punktów).', 'Zabezpieczenia, segmentacja, monitoring, klienci.', '1) Brak sieci otwartych/WEP/TKIP; firmowy SSID w WPA3-Enterprise lub WPA2-Enterprise z EAP-TLS. 2) Wymuszona walidacja certyfikatu serwera RADIUS w profilach klientów (MDM/GPO). 3) PMF (802.11w) włączone/wymagane. 4) Sieć gości i IoT w osobnych VLAN-ach z izolacją klientów i dostępem tylko do potrzebnych zasobów. 5) Hasła PSK (jeśli są) długie, losowe, rotowane; WPA3-SAE. 6) WIPS: wykrywanie rogue AP i evil twin, alerty w SIEM. 7) Plan kanałów (1/6/11, 5/6 GHz), moc dobrana do pokrycia. 8) Aktualne oprogramowanie AP/kontrolerów; interfejsy zarządzania niedostępne z sieci gości. 9) Logi uwierzytelnień 802.1X w SIEM (nieudane próby, nowe urządzenia).');
const more2 = [
  ['Oblicz, ile kanałów 20 MHz, 80 MHz i 160 MHz mieści się w paśmie 6 GHz o szerokości 1200 MHz (5925–7125 MHz). Co to oznacza dla gęstych biur?', 'Podziel szerokość pasma przez szerokość kanału.', '1200 / 20 = 59 kanałów 20 MHz (w praktyce 59), 1200 / 80 = 14 kanałów 80 MHz (w praktyce 14), 1200 / 160 = 7 kanałów 160 MHz. Dla porównania 2,4 GHz ma 3 nienakładające się kanały. W gęstych biurach 6 GHz pozwala przydzielić sąsiednim AP różne szerokie kanały bez zakłóceń współkanałowych — mniejszy jitter i większa przepustowość.'],
  ['Zmierz (lub opisz), jak zmienia się sygnał (dBm), prędkość PHY i RTT, gdy oddalasz się od AP o kolejne pomieszczenia. Jakie zależności zobaczysz?', 'iw dev wlan0 link w pętli + ping.', 'Typowo: obok AP −35…−45 dBm, PHY maksymalne (np. 1201 Mb/s), RTT do bramy 2–4 ms; jedna ściana −55 dBm, PHY spada (np. 864 Mb/s); dwie ściany −65…−70 dBm, PHY 300–400 Mb/s, rośnie p99 i liczba retry; dalej −75…−80 dBm, PHY kilkadziesiąt Mb/s, duży jitter, możliwy roaming. Zależność: słabszy sygnał → niższe MCS → dłuższy airtime ramki → więcej retransmisji → ogon opóźnień.'],
  ['Wyjaśnij, czym jest Wi-Fi Direct/hotspot w telefonie i jakie ryzyka niesie podłączanie laptopa firmowego do hotspotu prywatnego telefonu vs do hotelowego Wi-Fi.', 'Kto kontroluje sieć.', 'Hotspot telefonu to prywatna sieć (WPA2/WPA3) z jednym zaufanym urządzeniem pośrednim i operatorem komórkowym — zwykle bezpieczniejszy niż hotelowe Wi-Fi, w którym są obcy klienci, możliwy evil twin i captive portal. Ryzyka hotspotu: słabe hasło (WPA2-PSK), polityka firmy (DLP, kontrola ruchu), koszty transferu. W obu przypadkach VPN i TLS są nadal wymagane.'],
  ['Jakie dane o jakości sieci po stronie klienta warto zbierać w aplikacji (RUM), żeby odróżnić problem serwera od problemu Wi-Fi użytkownika?', 'Typ połączenia, czasy faz żądania.', 'Typ połączenia (Wi-Fi/komórkowe/kabel — Network Information API w przeglądarce, ConnectivityManager w Androidzie), czasy faz żądania (DNS, TCP connect, TLS, TTFB, transfer — Resource Timing API), percentyle per typ połączenia, liczba retry i zerwanych połączeń, region. Jeśli serwerowy czas przetwarzania jest stały, a rośnie connect/TTFB tylko dla Wi-Fi — problem po stronie sieci klienta.'],
  ['Opisz, jak deauthentication wpływa na aplikacje i dlaczego PMF (802.11w) jest ważne także dla dostępności.', 'Fałszywe ramki rozłączające.', 'Bez PMF ramki zarządzające (deauth/disassoc) nie są uwierzytelnione — ktoś w zasięgu może podrobić ramkę od AP i rozłączyć klientów (DoS, wymuszenie ponownego handshake’u do przechwycenia, wypchnięcie do evil twin). Aplikacje widzą zerwane połączenia i przerwy. PMF podpisuje ramki zarządzające — fałszywe rozłączenia są ignorowane. Dlatego WPA3 wymaga PMF, a w WPA2 warto je włączyć jako „wymagane” lub co najmniej „opcjonalne”.'],
  ['Policz, ile czasu antenowego (w µs) zajmuje transmisja ramki 1500 B przy prędkości PHY 54 Mb/s i 6 Mb/s (bez narzutu). Co to mówi o wpływie wolnego klienta na sieć?', 'czas = bity / prędkość.', '1500 B = 12 000 bitów. Przy 54 Mb/s: 12 000 / 54 = ok. 222 µs; przy 6 Mb/s: 12 000 / 6 = 2000 µs. Wolny klient zajmuje kanał 9 razy dłużej na tę samą ramkę — w tym czasie nikt inny nie nadaje. Dlatego AP wyłączają najniższe prędkości (minimum basic rate np. 12 lub 24 Mb/s) i stosują airtime fairness.'],
  ['Wyjaśnij różnicę między opóźnieniem, jitterem i stratą pakietów oraz który z nich najbardziej szkodzi wideokonferencji, a który pobieraniu dużego pliku.', 'Aplikacje czasu rzeczywistego vs transfer masowy.', 'Opóźnienie (RTT) — czas w obie strony; jitter — zmienność opóźnienia; strata — pakiety, które nie dotarły. Wideokonferencji (UDP, czas rzeczywisty) najbardziej szkodzi jitter i strata — bufor jitter musi rosnąć, pojawiają się zacięcia; umiarkowane stałe opóźnienie jest tolerowane. Pobieraniu pliku (TCP) szkodzi strata (kontrola przeciążenia zmniejsza okno) i duże RTT przy małym oknie (BDP — lekcja 01/06); jitter ma mniejsze znaczenie.'],
  ['Na czym polega problem „hidden node” w Wi-Fi i jak RTS/CTS go łagodzi?', 'Dwie stacje nie słyszą się nawzajem, ale obie słyszą AP.', 'Stacje A i B są po przeciwnych stronach AP i nie słyszą się nawzajem — CSMA/CA nie działa między nimi, więc nadają jednocześnie, a ramki kolidują przy AP (retransmisje, spadek przepustowości). RTS/CTS: stacja wysyła krótką prośbę RTS, AP odpowiada CTS słyszanym przez wszystkich, rezerwując medium na czas transmisji — inne stacje (także ukryte) czekają. Koszt: dodatkowy narzut, więc stosowane dla dużych ramek lub przy problemach.'],
];
for (const [q, h, a] of more2) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026112111);
for (const ch of [1, 6, 11, 13, 3, 14]) add(`Jaka jest częstotliwość środkowa kanału ${ch} w paśmie 2,4 GHz?`, `${freq24(ch)} MHz`, [`${freq24(ch) + 5} MHz`, `${2400 + 5 * ch} MHz` === `${freq24(ch)} MHz` ? `${freq24(ch) - 10} MHz` : `${2400 + 5 * ch} MHz`, `${5000 + 5 * ch} MHz`], ch === 14 ? 'Kanał 14 (Japonia) to wyjątek: 2484 MHz.' : `2407 + 5 × ${ch} = ${freq24(ch)} MHz.`);
for (const ch of [36, 44, 100, 149, 165]) add(`Jaka jest częstotliwość środkowa kanału ${ch} w paśmie 5 GHz?`, `${freq5(ch)} MHz`, [`${freq5(ch) + 5} MHz`, `${2407 + 5 * ch} MHz`, `${5950 + 5 * ch} MHz`], `5000 + 5 × ${ch} = ${freq5(ch)} MHz.`);
for (const ch of [1, 5, 37, 93]) add(`Jaka jest częstotliwość środkowa kanału ${ch} w paśmie 6 GHz?`, `${freq6(ch)} MHz`, [`${freq5(ch)} MHz`, `${freq6(ch) + 10} MHz`, `${6000 + 5 * ch} MHz`], `5950 + 5 × ${ch} = ${freq6(ch)} MHz.`);
for (const [a, b] of [[1, 6], [1, 3], [6, 11], [4, 6], [1, 11], [9, 11]]) add(`Czy kanały ${a} i ${b} w paśmie 2,4 GHz (20 MHz) zakłócają się wzajemnie (nakładają)?`, overlap24(a, b) ? 'Tak — środki są bliżej niż 20 MHz' : 'Nie — środki są oddalone o co najmniej 20 MHz', [overlap24(a, b) ? 'Nie — środki są oddalone o co najmniej 20 MHz' : 'Tak — środki są bliżej niż 20 MHz', 'Tylko w nocy', 'Tylko przy WPA3'], `Odległość środków: ${Math.abs(freq24(a) - freq24(b))} MHz.`);
for (const d of [0, 10, 20, -10, -30, -60]) add(`Ile to ${d} dBm?`, fmtMw(mw(d)), [fmtMw(mw(d) * 10), fmtMw(mw(d) / 10), fmtMw(mw(d) * 2)], `mW = 10^(${d}/10) = ${fmtMw(mw(d))}.`);
for (const [s, n] of [[-50, -95], [-67, -92], [-75, -90], [-60, -85], [-82, -95]]) add(`Sygnał ${s} dBm, szum ${n} dBm — jaki jest SNR?`, `${s - n} dB`, [`${s + n} dB`, `${Math.abs(s)} dB`, `${s - n + 10} dB`], 'SNR = sygnał − szum.');
for (const set of [[2, 3, 3, 4, 40], [1, 1, 2, 2, 2, 3, 3, 3, 4, 100], [10, 12, 11, 13, 10, 90, 11, 12, 10, 11]]) {
  const st = stats(set);
  add(`Próbki RTT [${set.join(', ')}] ms. Jaka jest mediana (p50)?`, `${st.p50} ms`, [`${st.mean} ms`, `${st.max} ms`, `${st.min} ms`], `Posortowane próbki, wartość środkowa (nearest-rank): ${st.p50} ms; średnia ${st.mean} ms jest zawyżona przez ogon.`);
  add(`Próbki RTT [${set.join(', ')}] ms. Jaki jest jitter (średnia bezwzględna różnica kolejnych próbek)?`, `${st.jitter} ms`, [`${round(st.jitter * 2)} ms`, `${st.max - st.min} ms`, `${st.mean} ms`, `${round(st.jitter / 2)} ms`], `Suma |różnic| / (n−1) = ${st.jitter} ms.`);
}
const sAll = STD.map(([s, n]) => n);
for (const [s, n] of STD.slice(2)) add(`Jaka nazwa handlowa odpowiada standardowi ${s}?`, n, sAll.filter((x) => x !== n && !x.startsWith('brak')).concat(['Wi-Fi 3']).slice(0, 3), `${s} = ${n}.`);
const facts = [
  ['Które kanały 2,4 GHz (20 MHz) są zalecane, bo się nie nakładają?', '1, 6, 11', ['1, 2, 3', '1, 5, 9, 13', '6, 7, 8'], 'Odstęp 25 MHz.'],
  ['Jakiego mechanizmu dostępu do medium używa Wi-Fi?', 'CSMA/CA', ['CSMA/CD', 'Token passing', 'TDMA jak w GSM'], 'Unikanie kolizji, ACK, backoff.'],
  ['Co robi karta Wi-Fi, gdy nie otrzyma ACK na wysłaną ramkę?', 'Retransmituje ją (retransmisja warstwy 2)', ['Zgłasza błąd TCP', 'Zmienia kanał', 'Wyłącza szyfrowanie'], 'TCP widzi tylko większe opóźnienie.'],
  ['Dlaczego realna przepustowość TCP w Wi-Fi to ok. połowa prędkości PHY?', 'Narzut MAC, ACK, backoff, half-duplex i współdzielenie kanału', ['Bo TCP jest wolny', 'Bo szyfrowanie zabiera połowę', 'Bo PHY podaje się w bajtach'], 'Plus retransmisje.'],
  ['Dlaczego testy wydajności przez Wi-Fi są niewiarygodne?', 'Bo zmienne opóźnienie i przepustowość radia dominują wynik', ['Bo Wi-Fi nie obsługuje HTTP/2', 'Bo Java nie wspiera Wi-Fi', 'Bo Wi-Fi szyfruje ruch'], 'Testuj z sieci zbliżonej do produkcyjnej.'],
  ['Która miara lepiej opisuje doświadczenie użytkowników sieci o zmiennym opóźnieniu?', 'Percentyle (p95/p99), nie średnia', ['Średnia arytmetyczna', 'Minimum', 'Liczba hopów'], 'Ogon dotyka wielu żądań.'],
  ['Czym jest airtime?', 'Czasem antenowym kanału dzielonym przez wszystkie stacje', ['Czasem pracy baterii', 'Czasem życia dzierżawy DHCP', 'Czasem roamingu'], 'Wolny klient zajmuje go najwięcej.'],
  ['Co przyspiesza roaming między punktami dostępowymi?', '802.11r (Fast BSS Transition), wspierany przez 802.11k i 802.11v', ['802.11w', '802.11b', '802.1Q'], '802.11w to PMF.'],
  ['Co chroni PMF (802.11w)?', 'Ramki zarządzające przed fałszowaniem (np. deauthentication)', ['Ramki danych przed podsłuchem', 'Hasło PSK', 'Adres IP klienta'], 'Obowiązkowe w WPA3.'],
  ['Jaką metodę uwierzytelniania używa WPA3-Personal?', 'SAE (Simultaneous Authentication of Equals)', ['PSK z PBKDF2 jak WPA2', 'WEP', 'EAP-TLS'], 'Odporna na atak słownikowy offline.'],
  ['Dlaczego WPA2-PSK jest podatne na atak słownikowy offline?', 'Przechwycony handshake lub PMKID pozwala sprawdzać hasła bez kontaktu z siecią', ['Bo hasło jest wysyłane jawnie', 'Bo używa WEP', 'Bo nie ma szyfrowania'], 'Obrona: długie hasło, WPA3-SAE.'],
  ['Co daje OWE (Enhanced Open)?', 'Szyfrowanie ruchu w sieci otwartej bez hasła', ['Uwierzytelnianie użytkowników', 'Ochronę przed evil twin', 'Szybszy roaming'], 'Brak uwierzytelnienia AP.'],
  ['Na czym polega evil twin?', 'Na AP podszywającym się pod znany SSID, by przechwycić klientów', ['Na zalewaniu tablicy MAC', 'Na przejęciu roota STP', 'Na podwójnym tagowaniu VLAN'], 'Obrona: walidacja certyfikatów, WIPS.'],
  ['Co jest kluczowe w konfiguracji klientów WPA2/WPA3-Enterprise z PEAP?', 'Wymuszona walidacja certyfikatu i nazwy serwera RADIUS', ['Wyłączenie PMF', 'Ukrycie SSID', 'Filtrowanie MAC'], 'Inaczej evil twin przechwyci poświadczenia.'],
  ['Czym jest rogue AP?', 'Nieautoryzowanym punktem dostępowym podłączonym do sieci firmowej', ['Zapasowym kontrolerem', 'AP gościnnym', 'Wzmacniaczem sygnału'], 'Omija segmentację i kontrolę dostępu.'],
  ['Czy ukrycie SSID chroni sieć?', 'Nie — nazwa jest widoczna w ramkach Probe i Association', ['Tak, całkowicie', 'Tak, przed atakiem słownikowym', 'Tylko w 6 GHz'], 'Dodatkowo klienci ją rozgłaszają.'],
  ['Czy filtrowanie MAC skutecznie chroni Wi-Fi?', 'Nie — MAC widać w eterze i łatwo go podrobić', ['Tak, jest nie do obejścia', 'Tak, w WPA3', 'Tylko w 2,4 GHz'], 'Używaj 802.1X.'],
  ['Czym jest captive portal?', 'Stroną logowania/regulaminu, która przechwytuje ruch do czasu akceptacji', ['Rodzajem VPN', 'Protokołem roamingu', 'Kontrolerem Wi-Fi'], 'Hotele, lotniska.'],
  ['Jak aplikacja powinna zareagować na błąd certyfikatu TLS w sieci hotelowej?', 'Uznać to za brak sieci/przechwycenie i nie wysyłać danych', ['Wyłączyć weryfikację certyfikatów', 'Przejść na HTTP', 'Zignorować błąd'], 'Nigdy trust-all.'],
  ['Jak system wykrywa captive portal?', 'Odpytuje znany adres oczekujący konkretnej odpowiedzi (np. 204)', ['Skanuje porty AP', 'Sprawdza moc sygnału', 'Pyta serwer DHCP'], 'Np. generate_204.'],
  ['Które pasmo oferuje najwięcej czystych kanałów i jest dostępne od Wi-Fi 6E?', '6 GHz', ['2,4 GHz', '900 MHz', '60 GHz'], 'Krótszy zasięg.'],
  ['Co to są kanały DFS w 5 GHz?', 'Kanały współdzielone z radarami, z których AP musi zejść po wykryciu radaru', ['Kanały tylko dla gości', 'Kanały 2,4 GHz', 'Kanały szyfrowane'], 'Może powodować chwilowe przerwy.'],
  ['Jaki próg sygnału przyjmuje się zwykle dla VoIP i wideo?', 'Około −67 dBm lub lepiej', ['−90 dBm', '−30 dBm minimum', '0 dBm'], 'Plus SNR ≥ 25 dB.'],
  ['Co oznacza wartość −80 dBm sygnału?', 'Słaby sygnał — niskie prędkości i retransmisje', ['Bardzo silny sygnał', 'Brak szyfrowania', 'Optymalny sygnał'], 'Bliżej zera = silniej.'],
  ['O ile dB rośnie sygnał przy dwukrotnym wzroście mocy?', 'O około 3 dB', ['O 10 dB', 'O 1 dB', 'O 20 dB'], '+10 dB = 10 razy.'],
  ['Dlaczego za duża moc AP może pogorszyć działanie sieci?', 'Klienci trzymają się dalekiego AP (sticky client) i rosną zakłócenia współkanałowe', ['Bo AP się przegrzewa', 'Bo spada szyfrowanie', 'Bo zmienia się SSID'], 'Projektuj pokrycie.'],
  ['Co zmienia OFDMA w Wi-Fi 6?', 'Pozwala obsłużyć wielu klientów w jednej transmisji, zmniejszając opóźnienia', ['Szyfruje ruch', 'Wyłącza retransmisje', 'Zwiększa zasięg do kilometrów'], 'Lepsze w zatłoczonych sieciach.'],
  ['Co wprowadza Wi-Fi 7 (MLO)?', 'Jednoczesne łącza w kilku pasmach dla jednego klienta', ['Usunięcie szyfrowania', 'Kanały 5 MHz', 'Pracę bez AP'], 'Mniejsze opóźnienia, płynniejszy roaming.'],
  ['Jakie polecenie Linuksa pokaże retransmisje Wi-Fi klienta?', 'iw dev wlan0 station dump', ['ip route', 'ss -t', 'arp -n'], 'tx retries, tx failed.'],
  ['Jakie polecenie Windows tworzy raport HTML z historią połączeń Wi-Fi?', 'netsh wlan show wlanreport', ['ipconfig /all', 'tracert', 'netstat -r'], 'Roaming, rozłączenia, przyczyny.'],
  ['Dlaczego warto mierzyć ping najpierw do bramy, a potem do serwera?', 'Aby oddzielić wpływ Wi-Fi/LAN od reszty ścieżki', ['Bo serwer nie odpowiada na ping', 'Bo brama jest szybsza', 'Bo tak wymaga ICMP'], 'Izolacja problemu.'],
  ['Co dzieje się z połączeniem WebSocket przy zmianie sieci i adresu IP?', 'Zostaje zerwane i trzeba je wznowić', ['Przenosi się automatycznie', 'Zmienia się w HTTP/1.0', 'Nic'], 'QUIC wspiera connection migration.'],
  ['Który protokół transportowy wspiera migrację połączenia przy zmianie adresu klienta?', 'QUIC (HTTP/3)', ['TCP', 'UDP bez QUIC', 'SCTP w przeglądarkach'], 'Identyfikator połączenia niezależny od adresu.'],
  ['Dlaczego maszyna wirtualna w trybie bridge na Wi-Fi zwykle nie działa?', 'AP przyjmuje ramki tylko z MAC uwierzytelnionego klienta', ['Bo Wi-Fi nie obsługuje IPv4', 'Bo bridge wyłącza WPA', 'Bo VM nie ma karty'], 'Stąd NAT na Wi-Fi.'],
  ['Jaka segmentacja jest właściwa dla sieci gości Wi-Fi?', 'Osobny SSID i VLAN z dostępem tylko do Internetu i izolacją klientów', ['Ten sam VLAN co pracownicy', 'Brak szyfrowania i brak VLAN-u', 'VLAN zarządzania'], 'Plus limity przepustowości.'],
  ['Czym jest WIPS?', 'Systemem wykrywania i blokowania zagrożeń bezprzewodowych (rogue AP, evil twin)', ['Rodzajem anteny', 'Protokołem roamingu', 'Standardem szyfrowania'], 'Zwykle część kontrolera.'],
  ['Co oznacza BSSID?', 'Adres MAC radia punktu dostępowego dla danej sieci', ['Nazwę sieci', 'Hasło sieci', 'Numer kanału'], 'SSID to nazwa.'],
  ['Jaki jest główny problem 2,4 GHz w gęstym biurze?', 'Tylko 3 nienakładające się kanały i dużo zakłóceń (Bluetooth, mikrofalówki, sąsiedzi)', ['Brak obsługi WPA2', 'Za krótka długość fali', 'Zbyt duża liczba kanałów'], 'Przenoś klientów na 5/6 GHz.'],
  ['Co zrobić z aplikacją wykonującą ważne operacje w sieci publicznej?', 'Używać TLS z weryfikacją certyfikatów, najlepiej przez VPN', ['Wyłączyć TLS dla szybkości', 'Ukryć SSID', 'Zmienić kanał'], 'Sieć publiczna = wroga.'],
  ['Dlaczego losowe adresy MAC w telefonach psują rezerwacje DHCP i filtry MAC?', 'Bo urządzenie używa innego MAC w każdej sieci (lub okresowo)', ['Bo MAC jest szyfrowany', 'Bo DHCP nie obsługuje telefonów', 'Bo MAC ma 64 bity'], 'Funkcja prywatności.'],
  ['Jaki tryb zabezpieczeń zaleca się w firmach?', 'WPA3-Enterprise lub WPA2-Enterprise z EAP-TLS', ['WEP', 'Sieć otwarta z filtrowaniem MAC', 'WPA-TKIP'], 'Certyfikaty urządzeń, walidacja serwera.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const [bytes, rate] of [[1500, 54], [1500, 6], [1500, 300], [500, 24], [1500, 12]]) {
  const us = round((bytes * 8) / rate, 1);
  add(`Ile mikrosekund czasu antenowego zajmie sama transmisja ${bytes} B przy prędkości PHY ${rate} Mb/s (bez narzutu)?`, `${us} µs`, [`${round(us * 8, 1)} µs`, `${round(us / 8, 1)} µs`, `${round(us * 2, 1)} µs`], `${bytes} × 8 = ${bytes * 8} bitów / ${rate} Mb/s = ${us} µs.`);
}
const facts2 = [
  ['Na czym polega problem „hidden node”?', 'Dwie stacje nie słyszą się nawzajem i nadają jednocześnie do AP', ['AP ukrywa SSID', 'Klient ma ukryty adres MAC', 'Router nie odpowiada na ping'], 'Łagodzi go RTS/CTS.'],
  ['Do czego służy mechanizm RTS/CTS?', 'Do rezerwacji medium przed transmisją, także wobec ukrytych stacji', ['Do szyfrowania ramek', 'Do roamingu', 'Do przydziału adresów IP'], 'Dodatkowy narzut.'],
  ['Dlaczego AP wyłącza najniższe prędkości podstawowe (np. 1–11 Mb/s)?', 'Aby wolni klienci nie zajmowali nieproporcjonalnie dużo czasu antenowego', ['Aby zwiększyć zasięg', 'Aby wyłączyć szyfrowanie', 'Aby zmienić kanał'], 'Także zmniejsza zasięg komórki — lepszy roaming.'],
  ['Który parametr jakości łącza najbardziej szkodzi wideokonferencjom?', 'Jitter i straty pakietów', ['Długi czas dzierżawy DHCP', 'Wysoka prędkość PHY', 'Duże MTU'], 'Bufor jitter rośnie, pojawiają się zacięcia.'],
  ['Co oznacza „sticky client”?', 'Klient trzymający się dalekiego AP zamiast przejść do bliższego', ['Klient z przypiętym adresem IP', 'Klient z ukrytym SSID', 'Klient bez szyfrowania'], 'Pomaga 802.11k/v i właściwa moc AP.'],
  ['Czy Wi-Fi w trybie klienta działa w pełnym dupleksie jak kabel?', 'Nie — w kanale w danej chwili nadaje jedna stacja (half-duplex)', ['Tak, zawsze', 'Tak, od Wi-Fi 4', 'Tylko w 2,4 GHz'], 'Wyjątki częściowe: OFDMA, MU-MIMO.'],
  ['Jakie źródła zakłóceń typowo dotyczą pasma 2,4 GHz?', 'Bluetooth, kuchenki mikrofalowe, sąsiednie sieci', ['Radary pogodowe', 'Sieci 5G mmWave', 'Światłowody'], 'Radary dotyczą kanałów DFS w 5 GHz.'],
  ['Która metoda EAP jest najbezpieczniejsza dla firmowego Wi-Fi?', 'EAP-TLS z certyfikatami klienta i serwera', ['PEAP-MSCHAPv2 bez walidacji certyfikatu', 'EAP-MD5', 'LEAP'], 'Brak haseł do przechwycenia.'],
  ['Co jest celem izolacji klientów w sieci gości?', 'Uniemożliwienie bezpośredniej komunikacji między urządzeniami gości', ['Zwiększenie prędkości', 'Szyfrowanie DNS', 'Ukrycie SSID'], 'Ogranicza ataki między gośćmi.'],
  ['Dlaczego zbieranie metryk RUM per typ połączenia pomaga w diagnozie?', 'Pozwala odróżnić problemy sieci klientów (np. Wi-Fi) od problemów serwera', ['Bo zmniejsza ruch', 'Bo szyfruje dane', 'Bo zastępuje logi serwera'], 'Np. rośnie tylko connect/TTFB na Wi-Fi.'],
  ['Które polecenie Linuksa pokaże sieci Wi-Fi w zasięgu z kanałem i sygnałem?', 'nmcli dev wifi', ['ip addr', 'ss -u', 'route -n'], 'Lub iw dev wlan0 scan.'],
  ['Jaki skutek ma kanał 160 MHz w gęstym biurze z wieloma AP?', 'Mniej kanałów do rozdzielenia i więcej zakłóceń między AP', ['Zawsze lepszy zasięg', 'Brak wpływu na sąsiadów', 'Automatyczne WPA3'], 'Często lepsze są 20/40/80 MHz.'],
];
for (const [q, a, w, e] of facts2) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
