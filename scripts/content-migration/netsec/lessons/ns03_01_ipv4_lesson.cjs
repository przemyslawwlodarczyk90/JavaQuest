// Generuje lekcje _ns_03_ip_i_routing/01_Ipv4HeaderAndAddressing.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_01_ipv4_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/01_Ipv4HeaderAndAddressing.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { SAMPLES, decode, checksumSteps, dump, hex4, ipClass, CLASSES, PROTO } = require('./ns03_01_ipv4_model.cjs');
const buildQuiz = require('./ns03_01_ipv4_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const T = decode(SAMPLES.tcp);
const ST = checksumSteps(SAMPLES.tcp);
const F = decode(SAMPLES.frag);
const V = decode(SAMPLES.voip);

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Każdy pakiet ma kopertę — naucz się ją czytać', ...img('ns03-01-header.svg', 'Układ nagłówka IPv4 w pięciu 32-bitowych wierszach: Version i IHL po 4 bity, DSCP 6 bitów, ECN 2 bity, Total Length 16 bitów; Identification 16, Flags 3, Fragment Offset 13; TTL 8, Protocol 8, Header Checksum 16; adres źródłowy 32 bity; adres docelowy 32 bity; opcjonalne opcje do 40 bajtów.'),
    body:
`Gdy Twoja aplikacja Spring Boot wysyła odpowiedź JSON, dane trafiają do TCP, a TCP oddaje segment warstwie sieciowej. Ta dokleja z przodu **20 bajtów nagłówka IPv4** — kopertę z adresem nadawcy, adresem odbiorcy i kilkoma polami, które decydują o losie pakietu w drodze przez kilkanaście routerów: ile skoków może jeszcze przeżyć (TTL), czy wolno go pociąć (DF), jak pilny jest (DSCP), co jest w środku (Protocol) i czy koperta nie została uszkodzona (Header Checksum).

Rozdział 03 to serce sieci: **adresacja IP i routing**. Zaczynamy od nagłówka, bo każda kolejna lekcja — maski, podsieci, routing, NAT, fragmentacja, ICMP, traceroute — to po prostu „co router robi z którym polem”. W tej lekcji:
- wszystkie pola nagłówka IPv4 (RFC 791) i ich praktyczne znaczenie,
- **Packet Lab**: ręczne dekodowanie nagłówka z hexdumpu i sprawdzenie w Wiresharku,
- suma kontrolna nagłówka policzona krok po kroku,
- adres jako dwie części — **sieć i host** — oraz historia klas A/B/C i dlaczego zastąpił je CIDR,
- parsowanie nagłówka w Javie (\`ByteBuffer\`) i co z nagłówka wynika dla bezpieczeństwa (spoofing, TTL, fragmentacja).

Cele egzaminacyjne: **Network+ N10-009** domena 1 (koncepcje sieciowe: model OSI, adresacja IPv4, enkapsulacja) i **CCNA 200-301 v2** (adresacja IPv4, weryfikacja parametrów IP). Dla blue teamu nagłówek to pierwsze, co widzisz w alertach IDS i w logach firewalla — TTL, flagi i długości często zdradzają skaner, tunel albo próbę obejścia filtrów.` },

  { type: 'DEFINITION', heading: 'Pola nagłówka IPv4 — słownik', body:
`- **Version (4 bity)** — 4 dla IPv4 (6 dla IPv6). Pierwsza cyfra hex nagłówka: \`4x\`.
- **IHL — Internet Header Length (4 bity)** — długość nagłówka w **słowach 32-bitowych**; 5 = 20 B (bez opcji), maksimum 15 = 60 B. Druga cyfra hex: \`45\` to „IPv4, 20 B”.
- **DSCP (6 bitów)** — klasa usługi dla QoS (dawniej ToS): 0 = best effort, **46 = EF** (głos), 34 = AF41 (wideo), 48 = CS6 (sterowanie siecią).
- **ECN (2 bity)** — Explicit Congestion Notification: router zamiast wyrzucić pakiet oznacza go (11 = CE, „zatłoczenie”); 00 = nieużywane, 01/10 = nadawca obsługuje ECN.
- **Total Length (16 bitów)** — długość całego pakietu (nagłówek + dane) w bajtach; maksimum 65 535.
- **Identification (16 bitów)** — numer pakietu; wspólny dla wszystkich fragmentów jednego datagramu.
- **Flags (3 bity)** — bit zarezerwowany (0), **DF** (Don’t Fragment — nie wolno ciąć; podstawa PMTUD), **MF** (More Fragments — po mnie są kolejne fragmenty).
- **Fragment Offset (13 bitów)** — pozycja fragmentu w oryginale, w **jednostkach 8 bajtów**.
- **TTL — Time To Live (8 bitów)** — licznik skoków; każdy router zmniejsza o 1, przy 0 pakiet jest odrzucany i wraca ICMP Time Exceeded (na tym działa traceroute). Typowe wartości startowe: 64 (Linux, macOS, Android), 128 (Windows), 255 (urządzenia sieciowe).
- **Protocol (8 bitów)** — co jest w środku: ${Object.entries(PROTO).map(([n, p]) => `${n} ${p}`).join(', ')}.
- **Header Checksum (16 bitów)** — suma kontrolna samego nagłówka (dopełnienie do jedności sumy słów 16-bitowych).
- **Source / Destination Address (po 32 bity)** — adresy nadawcy i odbiorcy.
- **Options** — rzadkie (np. Record Route, Router Alert); zwykle blokowane lub ignorowane.
- **Adres = sieć + host** — początkowe bity wskazują sieć (o tym decyduje maska/prefiks), końcowe — konkretny interfejs w tej sieci.
- **Klasy A/B/C/D/E** — historyczny (do 1993) podział, w którym długość części sieciowej wynikała z pierwszych bitów adresu.
- **CIDR** — Classless Inter-Domain Routing: prefiks dowolnej długości zapisywany jawnie (\`10.20.0.0/16\`).` },

  { type: 'ANALOGY', heading: 'Koperta listu poleconego', body:
`Nagłówek IPv4 to **koperta**, a segment TCP z danymi to list w środku:
- **adresy** — nadawca i odbiorca; poczta (routery) patrzy tylko na adres odbiorcy, a adres nadawcy… każdy może wpisać dowolny (to jest **IP spoofing** — dlatego nie wolno ufać adresowi źródłowemu jako dowodowi tożsamości).
- **TTL** — pieczątka „ważne przez 64 sortownie”; każda sortownia odlicza jedną, a list, który krąży w kółko (pętla routingu), w końcu zostaje zniszczony zamiast blokować pocztę na zawsze.
- **Protocol** — naklejka „w środku: dokumenty / paczka / list do działu reklamacji” — mówi odbiorcy, któremu działowi (TCP, UDP, ICMP) oddać zawartość.
- **DSCP** — „priorytet” albo „ekspres”; poczta firmowa (sieć z QoS) obsługuje go szybciej, poczta publiczna (Internet) zwykle ignoruje.
- **DF + Fragment Offset** — „nie przepakowywać” albo numeracja paczek „część 2 z 3, zaczyna się od strony 185”.
- **Header Checksum** — suma kontrolna kodu pocztowego: sortownia sprawdza, czy adres na kopercie nie został zamazany. Nie sprawdza zawartości listu — od tego jest TCP.
- **Klasy adresów** to stary system, w którym kod pocztowy miał tylko trzy rozmiary: „miasto”, „dzielnica” i „ulica”. Firma potrzebująca dwóch ulic musiała dostać całą dzielnicę. CIDR pozwolił przydzielać „tyle ulic, ile trzeba”.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Packet Lab: zdekoduj nagłówek z hexdumpu', ...img('ns03-01-decode.svg', 'Hexdump 20 bajtów nagłówka IPv4: 45 00 00 3c 1c 46 40 00 40 06 26 e9 c0 a8 01 0a 5d b8 d8 22 zdekodowany na pola: wersja 4, IHL 5, DSCP 0, długość całkowita 60, identyfikator 0x1c46, flaga DF, TTL 64, protokół 6 TCP, suma kontrolna 0x26e9, źródło 192.168.1.10, cel 93.184.216.34.'),
    body:
`Oto pierwsze 20 bajtów pakietu (po nagłówku Ethernet) — tak wygląda okno „Packet Bytes” w Wiresharku. Dekoduj po kolei: najpierw bajt z wersją i IHL, potem idź według rysunku nagłówka. Bajty wielobajtowych pól czytamy w kolejności **big-endian** („network byte order” — najstarszy bajt pierwszy).`,
    code:
`${dump(SAMPLES.tcp)}

bajt 0      45           → Version ${T.version}, IHL ${T.ihl} → nagłówek ${T.headerLen} B
bajt 1      00           → DSCP ${T.dscp}, ECN ${T.ecn}
bajty 2–3   00 3c        → Total Length 0x003c = ${T.total} B → dane: ${T.total} − ${T.headerLen} = ${T.payload} B
bajty 4–5   1c 46        → Identification 0x1c46 = ${T.id}
bajty 6–7   40 00        → 0100 0000 0000 0000: DF=${T.df ? 1 : 0}, MF=${T.mf ? 1 : 0}, Fragment Offset ${T.frag}
bajt 8      40           → TTL 0x40 = ${T.ttl}
bajt 9      06           → Protocol ${T.proto} = ${T.protoName}
bajty 10–11 26 e9        → Header Checksum 0x${hex4(T.checksum)} (poprawna: suma słów = 0x${hex4(T.verifyFolded)})
bajty 12–15 c0 a8 01 0a  → ${T.src}
bajty 16–19 5d b8 d8 22  → ${T.dst}

Sprawdzenie w Wiresharku: zapisz bajty do pliku i zaimportuj (File → Import from Hex Dump, typ „Raw IPv4”)
albo przechwyć własny ruch:  sudo tcpdump -ni any -c 1 -XX 'tcp port 443'` },

  { type: 'CODE_BASIC', heading: 'Nagłówek w Javie: parsowanie z ByteBuffer', body:
`Ten sam dekoder w Javie. Kluczowe szczegóły: \`ByteBuffer\` domyślnie jest **big-endian** (zgodny z siecią), bajty w Javie są ze znakiem, więc maskujemy \`& 0xFF\` / \`& 0xFFFF\`, a pola bitowe wyciągamy przesunięciami i maskami. Takiego kodu użyjesz w parserze pcap, w teście protokołu albo w narzędziu, które analizuje ruch z \`tcpdump -w\`.`,
    code:
`import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.HexFormat;

record Ipv4Header(int version, int ihl, int dscp, int ecn, int totalLength, int id, boolean df, boolean mf,
                  int fragmentOffset, int ttl, int protocol, int checksum, InetAddress src, InetAddress dst) {

    static Ipv4Header parse(byte[] packet) throws Exception {
        ByteBuffer b = ByteBuffer.wrap(packet);                  // big-endian = network byte order
        int vihl = b.get() & 0xFF;
        int tos = b.get() & 0xFF;
        int total = b.getShort() & 0xFFFF;
        int id = b.getShort() & 0xFFFF;
        int flagsFrag = b.getShort() & 0xFFFF;
        int ttl = b.get() & 0xFF, proto = b.get() & 0xFF, csum = b.getShort() & 0xFFFF;
        byte[] s = new byte[4], d = new byte[4];
        b.get(s).get(d);
        return new Ipv4Header(vihl >>> 4, vihl & 0x0F, tos >>> 2, tos & 0x03, total, id,
                (flagsFrag & 0x4000) != 0, (flagsFrag & 0x2000) != 0, flagsFrag & 0x1FFF,
                ttl, proto, csum, InetAddress.getByAddress(s), InetAddress.getByAddress(d));
    }

    static boolean checksumOk(byte[] packet, int headerLen) {
        long sum = 0;
        for (int i = 0; i < headerLen; i += 2) sum += ((packet[i] & 0xFF) << 8) | (packet[i + 1] & 0xFF);
        while ((sum >>> 16) != 0) sum = (sum & 0xFFFF) + (sum >>> 16);
        return sum == 0xFFFF;
    }

    public static void main(String[] args) throws Exception {
        byte[] p = HexFormat.ofDelimiter(" ").parseHex("${dump(SAMPLES.tcp)}");
        Ipv4Header h = parse(p);
        System.out.println(h);                                   // ... ttl=${T.ttl}, protocol=${T.proto}, src=/${T.src}, dst=/${T.dst}
        System.out.println("checksum OK: " + checksumOk(p, h.ihl() * 4));   // true
    }
}` },

  { type: 'STEP_BY_STEP', heading: 'Suma kontrolna nagłówka krok po kroku', ...img('ns03-01-checksum.svg', 'Obliczenie sumy kontrolnej nagłówka IPv4: pole sumy wyzerowane, 10 słów 16-bitowych zsumowanych, przeniesienie dodane do wyniku, negacja daje 0x26e9. Odbiorca sumuje słowa razem z polem sumy i oczekuje 0xffff. Router przelicza sumę po zmianie TTL; IPv6 nie ma sumy nagłówka.'),
    body:
`Algorytm z RFC 791/1071 — „suma w arytmetyce dopełnienia do jedności”:
1. Wpisz **0000** w pole Header Checksum.
2. Podziel nagłówek na **słowa 16-bitowe** (20 B → 10 słów): ${ST.words.map(hex4).join(' ')}.
3. Dodaj je zwykle: suma = **0x${ST.sum.toString(16)}**.
4. Bity powyżej 16 (**przeniesienie** = ${ST.carry}) dodaj z powrotem do dolnych 16 bitów: 0x${hex4(ST.sum & 0xffff)} + ${ST.carry} = **0x${hex4(ST.folded)}** (powtarzaj, dopóki jest przeniesienie).
5. **Zaneguj** bity: ~0x${hex4(ST.folded)} = **0x${hex4(ST.result)}** — to wpisujesz do nagłówka.
6. **Weryfikacja**: odbiorca sumuje wszystkie słowa łącznie z sumą kontrolną; wynik 0xffff (po zawinięciu przeniesień) = nagłówek nieuszkodzony.
7. Każdy router zmniejsza TTL, więc **musi przeliczyć sumę** na każdym skoku (w praktyce przyrostowo, RFC 1624).

Dlaczego to ważne w praktyce: Wireshark na **wychodzących** pakietach często pokazuje „Header checksum: 0x0000 [incorrect]” — to nie błąd sieci, tylko **checksum offload**: system zostawia liczenie karcie sieciowej, a capture łapie pakiet, zanim karta go policzy. Uszkodzony nagłówek w ruchu **przychodzącym** to już sygnał problemu (wadliwy sprzęt, błędny „middlebox”, ręcznie skrojony pakiet).` },

  { type: 'NOTE', heading: 'Adres to sieć + host; klasy to historia, ale wciąż wracają w pytaniach', ...img('ns03-01-classes.svg', 'Podział pierwszego oktetu na klasy: A 0–127 z maską /8, B 128–191 z /16, C 192–223 z /24, D 224–239 multicast, E 240–255 zarezerwowane. Klasy marnowały adresy i powiększały tablice routingu; w 1993 zastąpił je CIDR z prefiksem dowolnej długości i agregacją tras.'),
    body:
`Każdy adres IPv4 ma dwie części: **część sieciową** (wspólną dla wszystkich hostów w podsieci — według niej routery wybierają trasę) i **część hosta** (unikalną w obrębie podsieci). Granicę wyznacza **maska / prefiks** — o niej cała lekcja 03 i 04. Przykład: w \`192.168.1.10/24\` część sieciowa to \`192.168.1\`, host to \`.10\`.

**Klasy (RFC 791, 1981)**: długość części sieciowej wynikała z pierwszych bitów adresu:
${CLASSES.map(([k, bits, range, prefix, desc]) => `- **klasa ${k}**: pierwsze bity ${bits}, pierwszy oktet ${range}, maska domyślna ${prefix} — ${desc}`).join('\n')}

**Dlaczego umarły:** firma z 2000 hostami nie mieściła się w klasie C (254), więc dostawała klasę B (65 534 adresy — ponad 96% zmarnowanych); pula klas B wyczerpywała się na początku lat 90., a rozdawanie wielu klas C zamiast jednej B rozdymało tablice routingu w Internecie. W 1993 r. wprowadzono **CIDR** (RFC 1519, dziś RFC 4632): prefiks dowolnej długości (/22, /27…) i **agregacja tras** (cztery sieci /24 ogłaszane jako jedna /22).

**Co z klas zostało:** nazwy w mowie potocznej („klasa C” = /24), zakres D = **multicast** (224.0.0.0/4), zakres E = zarezerwowany (240.0.0.0/4), prywatne pule z RFC 1918 pokrywające się z dawnymi granicami (10/8, 172.16/12, 192.168/16 — lekcja 06) i… pytania egzaminacyjne (Network+ wciąż pyta o klasy). Pamiętaj: **dziś maska zawsze jest podawana jawnie**, a urządzenie nie „zgaduje” jej z klasy (wyjątek: niektóre stare protokoły jak RIPv1 — classful).` },

  { type: 'CODE_PRACTICAL', heading: 'Nagłówek na żywo: tcpdump, Wireshark, ip i ping', body:
`Zobacz pola nagłówka we własnym ruchu i sprawdź, jak zmieniają je polecenia. Każde polecenie poniżej ustawia lub odczytuje konkretne pole:`,
    code:
`# pełny nagłówek z dekodowaniem (-v pokazuje TTL, ID, flagi, długość, protokół, DSCP jako tos)
sudo tcpdump -ni any -v -c 3 icmp
#  IP (tos 0x0, ttl 64, id 47123, offset 0, flags [DF], proto ICMP (1), length 84)
#      10.0.2.15 > 8.8.8.8: ICMP echo request ...

# TTL: ustaw ręcznie i zobacz, kto odpowiada Time Exceeded (zasada działania traceroute)
ping -c1 -t 1 8.8.8.8          # Linux: -t TTL;   Windows: ping -i 1 8.8.8.8
ping -c1 -t 3 8.8.8.8          # odpowie 3. router na ścieżce

# DF i rozmiar pakietu (Total Length = 1472 + 8 + 20 = 1500)
ping -c1 -M do -s 1472 8.8.8.8

# DSCP: oznacz ruch jako EF (46 << 2 = 184 = 0xb8)
ping -c1 -Q 0xb8 10.0.0.1      # tcpdump pokaże "tos 0xb8"

# filtry Wiresharka na pola nagłówka
ip.ttl < 5                      # pakiety bliskie wygaśnięcia (traceroute, pętle)
ip.flags.df == 0 && ip.len > 1400
ip.dsfield.dscp == 46           # ruch głosowy
ip.flags.mf == 1 || ip.frag_offset > 0      # fragmenty
ip.checksum_bad.expert          # błędna suma (pamiętaj o offloadzie)
ip.src == 192.168.1.10 && ip.proto == 17    # UDP z danego hosta

# Java: TTL i DSCP gniazda
#   socket.setOption(StandardSocketOptions.IP_TOS, 0xb8);        // DSCP EF (system może zignorować)
#   multicastSocket.setTimeToLive(1);                             // multicast tylko w lokalnej sieci` },

  { type: 'USAGE', heading: 'Gdzie nagłówek IPv4 spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **Diagnoza „wolnej sieci”** — Total Length i DF w capture pokazują, czy pakiety mieszczą się w MTU (lekcja 11); ID i Fragment Offset — czy ktoś je tnie.
- **QoS i DSCP** — wideokonferencje, VoIP, ruch sterujący w Kubernetes; aplikacja może oznaczać ruch (\`IP_TOS\`), ale chmura i Internet zwykle zerują DSCP.
- **Kontenery i tunele** — overlay (VXLAN, IPsec, WireGuard) dokleja kolejny nagłówek IP: Protocol 50 (ESP), 17 (VXLAN po UDP), 4 (IP-in-IP) — w capture widzisz „pakiet w pakiecie”.
- **Logi load balancerów** — adres źródłowy po NAT/proxy to adres balansera; prawdziwy adres klienta jest w nagłówku HTTP \`X-Forwarded-For\` lub w protokole PROXY — a nie w nagłówku IP.
- **Parsowanie ruchu w Javie** — biblioteki pcap4j, Netty (\`EpollDatagramChannel\` z surowymi pakietami), testy protokołów.

**Blue team:**
- **Spoofing** — adres źródłowy nie jest uwierzytelniony; ataki odbite/wzmocnione (DNS, NTP, memcached) podszywają się pod adres ofiary. Obrona: **BCP 38 / RFC 2827** (filtrowanie ruchu wychodzącego z nieswoich adresów), uRPF na routerach.
- **TTL jako odcisk palca** — ruch z „Windowsa” z TTL 64 albo nagłe zmiany TTL od tego samego źródła mogą zdradzać NAT, proxy lub spreparowane pakiety; narzędzia pasywnego fingerprintingu (p0f) i reguły IDS korzystają z TTL.
- **Fragmentacja** — nakładające się fragmenty i bardzo małe pierwsze fragmenty to klasyczna próba ominięcia IDS/firewalla; nowoczesne urządzenia składają fragmenty przed inspekcją (reguły Suricata \`fragbits\`, \`fragoffset\`).
- **Pola nietypowe** — opcje IP (np. Source Route), zarezerwowany bit flag ustawiony, Total Length niezgodne z rozmiarem ramki — to anomalie warte alertu.
- **Mapowanie na certyfikaty:** Security+ (ataki sieciowe, spoofing), CySA+ (analiza ruchu i alertów IDS), BTL1 (analiza pcap).` },

  { type: 'PITFALL', heading: 'Częste błędy przy czytaniu nagłówka', body:
`- **IHL to nie bajty.** Wartość 5 oznacza 5 słów × 4 B = 20 B. Pomyłka przesuwa cały dalszy parsing.
- **Total Length obejmuje nagłówek.** Dane = Total Length − IHL×4, a nie Total Length.
- **Fragment Offset jest w jednostkach 8 bajtów.** Offset ${F.frag} oznacza ${F.fragBytes}. bajt oryginału, nie ${F.frag}.
- **Kolejność bajtów.** Sieć używa big-endian; w C na x86 trzeba \`ntohs()\`, w Javie \`ByteBuffer\` robi to domyślnie — chyba że ktoś ustawi \`ByteOrder.LITTLE_ENDIAN\`.
- **Bajty w Javie są ze znakiem.** \`(byte) 0xc0\` to −64; bez \`& 0xFF\` adres 192.168… wyjdzie z liczbami ujemnymi.
- **„Checksum incorrect” w Wiresharku** na ruchu wychodzącym to prawie zawsze checksum offload, nie uszkodzenie.
- **TTL nie jest czasem.** Mimo nazwy to licznik skoków (każdy router −1); TTL w DNS to zupełnie inna rzecz (sekundy w cache).
- **Adres źródłowy nie jest dowodem tożsamości.** Allow-listy po IP to tylko jedna warstwa — za NAT-em, proxy i przy spoofingu UDP adres kłamie.
- **Klasa nie wyznacza maski.** \`10.1.2.3/24\` to poprawna podsieć /24 z dawnej klasy A; „adres z klasy A” nie oznacza maski /8.
- **DSCP to nie gwarancja.** Oznaczenie pakietu nic nie daje, jeśli urządzenia po drodze nie mają polityk QoS — a w Internecie DSCP bywa zerowane.` },

  { type: 'CODE_WRONG', heading: 'Źle: parser nagłówka z typowymi błędami', body:
`Ten kod „prawie działa” na testowym pakiecie, ale psuje się na prawdziwym ruchu: ignoruje IHL (opcje), myli jednostki i znak bajtów.`,
    code:
`int version = packet[0] >> 4;                    // -64 >> 4 dla bajtu 0xC0? tu akurat 0x45, ale znak bajtu to pułapka
int headerLen = packet[0] & 0x0F;                 // BŁĄD: to słowa, nie bajty (5 zamiast 20)
int totalLength = packet[2] + packet[3];          // BŁĄD: to nie suma bajtów, tylko liczba 16-bitowa (i znak!)
int payloadLen = totalLength;                     // BŁĄD: Total Length zawiera nagłówek
int fragOffset = ((packet[6] & 0x1F) << 8) | packet[7];   // BŁĄD: brak & 0xFF i brak ×8
String src = packet[12] + "." + packet[13] + "." + packet[14] + "." + packet[15];   // "-64.-88.1.10"
byte[] tcp = Arrays.copyOfRange(packet, 20, packet.length);  // BŁĄD: zakłada brak opcji IP
boolean trusted = src.startsWith("10.");          // BŁĄD: adres źródłowy jako uwierzytelnienie` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: maski, jednostki i granice z nagłówka', body:
`Każde pole czytane jako liczba bez znaku, długości w poprawnych jednostkach, dane wycinane według IHL i Total Length (a nie według długości bufora — ramka Ethernet może mieć padding), walidacja przed użyciem.`,
    code:
`int vihl = packet[0] & 0xFF;
if ((vihl >>> 4) != 4) throw new IllegalArgumentException("to nie IPv4");
int headerLen = (vihl & 0x0F) * 4;                                    // słowa → bajty
if (headerLen < 20) throw new IllegalArgumentException("IHL < 5");
int totalLength = ((packet[2] & 0xFF) << 8) | (packet[3] & 0xFF);
if (totalLength < headerLen || totalLength > packet.length) throw new IllegalArgumentException("zła długość");
int flagsFrag = ((packet[6] & 0xFF) << 8) | (packet[7] & 0xFF);
int fragOffsetBytes = (flagsFrag & 0x1FFF) * 8;                       // jednostki 8 B
InetAddress src = InetAddress.getByAddress(Arrays.copyOfRange(packet, 12, 16));
byte[] payload = Arrays.copyOfRange(packet, headerLen, totalLength);  // bez opcji i bez paddingu ramki
// tożsamość klienta: mTLS / token / podpis — adres IP najwyżej jako dodatkowy sygnał` },

  { type: 'WHEN_TO_USE', heading: 'Które pole sprawdzić przy jakim problemie', body:
`Ściąga: objaw → pole nagłówka → narzędzie.`,
    code:
`OBJAW / PYTANIE                                   POLE                         JAK SPRAWDZIĆ
traceroute, „TTL exceeded in transit”, pętla       TTL                          tcpdump -v ; ip.ttl < 5
duże pakiety giną, małe przechodzą                 Total Length, DF             ping -M do -s ; ip.flags.df
pakiety pocięte na kawałki                         ID, MF, Fragment Offset      ip.flags.mf == 1 || ip.frag_offset > 0
QoS nie działa dla VoIP                            DSCP                         ip.dsfield.dscp == 46 po obu stronach łącza
co niesie pakiet (TCP/UDP/ESP/GRE)?                Protocol                     ip.proto ; tcpdump "proto 50"
„checksum incorrect” w capture                     Header Checksum              sprawdź offload: ethtool -k eth0 | grep checksum
skąd naprawdę przyszedł ruch?                      Source Address (+ NAT!)      logi NAT/proxy, X-Forwarded-For, protokół PROXY
podejrzenie skanowania / spoofingu                 TTL, ID, opcje, flagi        p0f, reguły IDS, porównanie TTL z typem systemu
odpowiedzi ECN-CE, mniejsza przepustowość          ECN                          ip.dsfield.ecn == 3` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Nagłówek IPv4 bez opcji ma **20 B** w 5 wierszach po 32 bity; \`45\` na początku = IPv4 + IHL 5.
- Pola: Version, IHL (słowa 32-bit), DSCP/ECN, Total Length (z nagłówkiem), ID, flagi DF/MF, Fragment Offset (×8 B), TTL (skoki), Protocol (1/6/17…), Header Checksum, adresy.
- Suma kontrolna: suma słów 16-bit z przeniesieniem i negacja; weryfikacja daje 0xffff; router przelicza ją po zmianie TTL; IPv6 jej nie ma.
- Dekodowanie: bajty w kolejności big-endian; w Javie \`ByteBuffer\` i maski \`& 0xFF\`/\`& 0xFFFF\`.
- Adres = część sieci + część hosta; granicę wyznacza maska. Klasy A/B/C (do 1993) zastąpił **CIDR** (RFC 1519/4632) — maska zawsze jawna.
- Bezpieczeństwo: adres źródłowy można sfałszować (BCP 38, uRPF), TTL i flagi pomagają wykrywać anomalie, fragmentacja bywa użyta do omijania IDS.
- Następna lekcja: **system dwójkowy dla sieciowca** — bity, oktety i AND, bez których nie da się policzyć maski.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const key of ['udp', 'icmp', 'voip', 'frag', 'ecn']) {
  const d = decode(SAMPLES[key]);
  ex(`Packet Lab: zdekoduj nagłówek IPv4 z hexdumpu: ${dump(SAMPLES[key])}. Podaj wersję, długość nagłówka, DSCP/ECN, długość całkowitą i danych, ID, flagi, offset, TTL, protokół, adresy.`,
    'Bajt 0: wersja i IHL; bajty 2–3 długość; 6–7 flagi i offset; 8 TTL; 9 protokół; 12–19 adresy.',
    `Version ${d.version}, IHL ${d.ihl} (${d.headerLen} B), DSCP ${d.dscp}, ECN ${d.ecn}, Total Length ${d.total} B (dane ${d.payload} B), ID 0x${hex4(d.id)} = ${d.id}, DF=${d.df ? 1 : 0}, MF=${d.mf ? 1 : 0}, offset ${d.frag} (= ${d.fragBytes} B), TTL ${d.ttl}, Protocol ${d.proto} (${d.protoName}), checksum 0x${hex4(d.checksum)} (${d.valid ? 'poprawna' : 'błędna'}), ${d.src} → ${d.dst}.`);
}
for (const key of ['udp', 'icmp', 'voip']) {
  const st = checksumSteps(SAMPLES[key]);
  ex(`Policz ręcznie sumę kontrolną nagłówka ${dump(SAMPLES[key]).replace(/^(.{30})(.{5})/, '$1xx xx')} (pole sumy zastąpione „xx xx”).`,
    'Wyzeruj sumę, dodaj 10 słów 16-bit, dodaj przeniesienie, zaneguj.',
    `Słowa: ${st.words.map(hex4).join(' + ')} = 0x${st.sum.toString(16)}; przeniesienie ${st.carry} → 0x${hex4(st.folded)}; negacja → 0x${hex4(st.result)}.`);
}
for (const ip of ['10.20.30.40', '172.31.0.1', '192.168.100.7', '224.0.0.251', '127.0.0.1', '240.1.1.1']) {
  const k = ipClass(ip); const row = CLASSES.find((c) => c[0] === k);
  ex(`Do której historycznej klasy należy adres ${ip} i jaką maskę domyślną przypisałby mu system klasowy? Czy dziś ta maska obowiązuje?`,
    'Pierwszy oktet: 0–127 A, 128–191 B, 192–223 C, 224–239 D, 240–255 E.',
    `Klasa ${k} (pierwszy oktet ${ip.split('.')[0]}, zakres ${row[2]}), maska klasowa ${row[3]}${k === 'D' ? ' — to adres multicast' : k === 'E' ? ' — zakres zarezerwowany' : ''}. Dziś maska jest podawana jawnie (CIDR) i nie wynika z klasy${ip.startsWith('127') ? '; 127.0.0.0/8 to pętla zwrotna' : ''}.`);
}
const more = [
  ['Przechwyć tcpdumpem jeden pakiet HTTPS ze swojego komputera (tcpdump -ni any -c1 -XX "tcp port 443"), zdekoduj ręcznie nagłówek IP z hexdumpu, a potem porównaj z dekodowaniem Wiresharka.', 'Pamiętaj o nagłówku Ethernet/SLL przed IP.', 'Na interfejsie fizycznym IP zaczyna się po 14 B Ethernetu (EtherType 0x0800); na „any” — po 16 B nagłówka Linux cooked (SLL). Dekodujesz: 45 → IPv4/20 B, długość, ID, DF zwykle ustawione dla TCP, TTL 64 (Linux) lub 128 (Windows), protokół 6, adresy. Suma kontrolna wychodzącego pakietu może być „incorrect” przez offload — to normalne.'],
  ['Wyjaśnij, dlaczego router po zmniejszeniu TTL musi przeliczyć sumę kontrolną i dlaczego IPv6 zrezygnował z tej sumy.', 'Suma obejmuje TTL; koszt na każdym skoku.', 'Suma kontrolna obejmuje cały nagłówek, w tym TTL, więc każda zmiana TTL unieważnia sumę — router aktualizuje ją (przyrostowo, RFC 1624) na każdym skoku. IPv6 usunął sumę nagłówka, bo warstwy L2 (CRC ramki) i L4 (sumy TCP/UDP obowiązkowe w IPv6) wykrywają błędy, a brak przeliczania przyspiesza przekazywanie pakietów.'],
  ['Napisz w Javie metodę, która przyjmuje 20-bajtowy nagłówek z wyzerowaną sumą kontrolną, wylicza sumę i wpisuje ją do bajtów 10–11. Przetestuj na przykładzie z lekcji.', 'Pętla po słowach, zawijanie przeniesień, ~ i maska 0xFFFF.', `static void fillChecksum(byte[] h) { h[10] = 0; h[11] = 0; long s = 0; for (int i = 0; i < 20; i += 2) s += ((h[i] & 0xFF) << 8) | (h[i + 1] & 0xFF); while ((s >>> 16) != 0) s = (s & 0xFFFF) + (s >>> 16); int c = (int) (~s & 0xFFFF); h[10] = (byte) (c >>> 8); h[11] = (byte) c; } — dla nagłówka z lekcji wynik to 0x${hex4(ST.result)} (bajty ${hex4(ST.result).slice(0, 2)} ${hex4(ST.result).slice(2)}).`],
  ['Na podstawie TTL w odpowiedziach oszacuj system i liczbę skoków: odpowiedzi przychodzą z TTL 116, 57 i 249.', 'Najbliższa typowa wartość startowa powyżej odczytu: 64, 128, 255.', 'TTL 116 → start 128 (Windows), 12 skoków; TTL 57 → start 64 (Linux/macOS/Android), 7 skoków; TTL 249 → start 255 (router/urządzenie sieciowe, np. Cisco), 6 skoków. To heurystyka — TTL można zmienić, a NAT i tunele zakłócają odczyt.'],
  ['Firma potrzebuje adresów dla 2000 hostów. Porównaj przydział klasowy z przydziałem CIDR i policz marnotrawstwo.', 'Klasa C = 254, klasa B = 65 534; CIDR: najmniejszy prefiks ≥ 2000 hostów.', 'Klasowo: C za mała (254), więc klasa B — 65 534 adresy, wykorzystane 2000 (ok. 3%), zmarnowane ok. 63 500 (ok. 97%). CIDR: /21 = 2046 hostów (2^11 − 2) — marnotrawstwo 46 adresów. Alternatywnie klasowo 8 sieci C, ale to 8 wpisów w tablicach routingu zamiast jednego /21.'],
  ['Wyjaśnij, czym jest IP spoofing, dlaczego jest łatwy dla UDP, a trudny dla pełnego połączenia TCP, oraz jak mu przeciwdziałać.', 'Brak uwierzytelnienia źródła; handshake TCP; BCP 38.', 'Nadawca może wpisać dowolny adres źródłowy — sieć go nie weryfikuje. Dla UDP wystarczy jeden pakiet (np. zapytanie DNS/NTP z adresem ofiary → atak odbity i wzmocniony). Dla TCP fałszywy nadawca nie dostaje SYN-ACK (idzie do prawdziwego właściciela adresu), więc nie zestawi połączenia (poza zgadywaniem numerów sekwencyjnych — dziś losowych). Obrona: BCP 38/RFC 2827 (ISP i firmy odrzucają ruch wychodzący z obcych adresów), uRPF, wyłączanie otwartych resolverów/wzmacniaczy, rate limiting, a w aplikacjach — nieufanie adresowi IP jako tożsamości.'],
  ['Zaprojektuj trzy reguły IDS/SIEM wykorzystujące pola nagłówka IPv4 do wykrywania anomalii i opisz fałszywe alarmy.', 'TTL, fragmenty, opcje, niezgodność długości.', '1) Pakiety z opcjami IP (IHL > 5) z Internetu — rzadkie, często skanery lub próby source routing; FP: niektóre protokoły multicast (Router Alert w IGMP). 2) Fragmenty o małym pierwszym fragmencie lub nakładające się offsety — próba ominięcia inspekcji; FP: rzadko, czasem źle skonfigurowane tunele. 3) Nagła zmiana TTL dla tego samego źródła w trakcie sesji — możliwe wstrzykiwanie pakietów lub zmiana trasy; FP: zmiany routingu, load balancery. Wszystkie korelować z innymi sygnałami, nie blokować w ciemno.'],
  ['Wyjaśnij różnicę między DSCP a ECN i podaj po jednym przykładzie wykorzystania każdego w środowisku aplikacji Java.', 'QoS vs sygnalizacja zatłoczenia.', 'DSCP (6 bitów) to klasa ruchu dla polityk QoS — np. ruch WebRTC/VoIP oznaczony EF (46) dostaje priorytet w sieci firmowej; aplikacja może ustawić IP_TOS na gnieździe, ale sieć decyduje, czy go uszanuje. ECN (2 bity) to sygnał od routerów, że kolejka się zapełnia — zamiast gubić pakiety oznaczają je CE, a TCP odbiorcy informuje nadawcę, który zwalnia; włączany w systemie (net.ipv4.tcp_ecn), zwiększa płynność np. przy dużych transferach między mikroserwisami w centrum danych.'],
  ['Pakiet ma Total Length 1500, MF=1 i Fragment Offset 185. Którą część oryginalnego datagramu niesie i czy jest ostatni?', 'Offset × 8; dane = Total Length − 20.', `Dane fragmentu: 1500 − 20 = 1480 B, zaczynają się od bajtu ${185 * 8} oryginalnych danych (185 × 8), więc obejmują bajty ${185 * 8}–${185 * 8 + 1479}. MF=1 oznacza, że nie jest ostatni — po nim przychodzą kolejne fragmenty z tym samym ID.`],
  ['Dlaczego w capture z interfejsu „any” na Linuksie nagłówek IP zaczyna się w innym miejscu niż przy przechwytywaniu z eth0?', 'Linux cooked capture (SLL).', 'Na konkretnym interfejsie Ethernet przed IP jest nagłówek Ethernet II (14 B: MAC docelowy, źródłowy, EtherType). Pseudo-interfejs „any” nie ma jednej warstwy L2, więc libpcap stosuje nagłówek Linux cooked capture SLL (16 B, w SLL2 — 20 B). Parser musi brać pod uwagę typ łącza (DLT/LINKTYPE) z nagłówka pliku pcap.'],
  ['Oblicz wartość bajtu DSCP/ECN (drugi bajt nagłówka) dla: a) DSCP 46, ECN 0; b) DSCP 34, ECN 2; c) DSCP 0, ECN 3. Podaj wynik hex i co oznacza.', 'Bajt = DSCP × 4 + ECN (DSCP zajmuje 6 starszych bitów).', 'a) 46 × 4 = 184 = 0xb8 — EF, głos (tcpdump: tos 0xb8). b) 34 × 4 + 2 = 138 = 0x8a — AF41 (wideo), nadawca obsługuje ECN (ECT(0)). c) 0 + 3 = 0x03 — best effort, CE: router zasygnalizował zatłoczenie.'],
  ['Jaka jest maksymalna liczba bajtów danych w pakiecie IPv4 i maksymalna długość nagłówka? Skąd te limity?', 'Total Length 16 bitów; IHL 4 bity.', 'Total Length ma 16 bitów → maks. 65 535 B całego pakietu; przy nagłówku 20 B danych może być 65 515 B. IHL ma 4 bity → maks. 15 słów × 4 B = 60 B nagłówka (40 B opcji). W praktyce pakiet ogranicza MTU łącza (zwykle 1500 B), więc większe datagramy są fragmentowane lub — przy DF — odrzucane.'],
  ['Dlaczego allow-lista adresów IP w aplikacji za load balancerem często „nie działa” i jak to poprawnie rozwiązać?', 'Adres źródłowy po proxy; X-Forwarded-For; zaufane proxy.', 'Za reverse proxy/load balancerem nagłówek IP zawiera adres balansera, więc aplikacja widzi zawsze ten sam adres. Prawdziwy adres klienta jest w X-Forwarded-For/Forwarded lub w protokole PROXY. W Spring Boot: server.forward-headers-strategy=native/framework i ufanie nagłówkom tylko od znanych proxy (inaczej klient sfałszuje X-Forwarded-For). Allow-lista po IP to i tak dodatek do uwierzytelnienia, nie jego zamiennik.'],
  ['Podaj numery protokołów i znaczenie: ICMP, TCP, UDP, GRE, ESP, OSPF, VRRP. Do czego przyda Ci się ta wiedza przy regułach firewalla?', 'Pole Protocol.', 'ICMP 1, TCP 6, UDP 17, GRE 47, ESP 50, OSPF 89, VRRP 112. Firewall filtruje po numerze protokołu — tunel IPsec wymaga przepuszczenia ESP (50) i UDP 500/4500, GRE wymaga protokołu 47 (nie portu!), OSPF między routerami 89. Reguły „tylko TCP i UDP” po cichu psują VPN-y i protokoły routingu.'],
  ['Wireshark pokazuje na pakietach wysyłanych z Twojego laptopa „Header checksum: 0x0000 incorrect”, a serwer odpowiada normalnie. Wyjaśnij i potwierdź hipotezę.', 'Checksum offload; ethtool -k.', 'To checksum offload: system przekazuje karcie nagłówek z wyzerowaną sumą, a karta liczy ją sprzętowo tuż przed wysłaniem — capture widzi pakiet wcześniej. Potwierdzenie: ethtool -k eth0 | grep checksum → tx-checksumming: on; capture po drugiej stronie (na serwerze) pokazuje poprawną sumę. W Wiresharku można wyłączyć walidację sumy w ustawieniach protokołu IPv4.'],
  ['Wyjaśnij, czym różni się adres sieci od adresu hosta na przykładzie 192.168.1.10/24 i 10.20.30.40/16 oraz dlaczego routery patrzą tylko na część sieciową.', 'Maska jako granica; tablica routingu zawiera sieci.', '192.168.1.10/24: sieć 192.168.1.0 (pierwsze 24 bity), host .10 (ostatnie 8 bitów). 10.20.30.40/16: sieć 10.20.0.0, część hosta 30.40. Routery przechowują trasy do sieci (prefiksów), a nie do każdego hosta — dzięki temu tablica Internetu ma ok. miliona prefiksów zamiast miliardów adresów; dopiero ostatni router (w sieci docelowej) używa ARP, by znaleźć konkretny host.'],
];
for (const [p, h, s] of more) ex(p, h, s);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
