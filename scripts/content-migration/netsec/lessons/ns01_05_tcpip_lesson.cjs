// Generuje lekcje _ns_01_fundamenty_sieci/05_TcpIpModelAndEncapsulation.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_05_tcpip_lesson.cjs src/main/resources/content/_ns_01_fundamenty_sieci/05_TcpIpModelAndEncapsulation.json
const { quizBuilder, exerciseBuilder, writeLesson, fmt } = require('./lib.cjs');
const { build, hexdump } = require('./ns01_05_packet.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const pk = build();
const HEX = hexdump(pk.frame);
const hx = (n, w = 2) => '0x' + n.toString(16).padStart(w, '0');
const pct = (x) => (Math.round(x * 1000) / 10).toFixed(1).replace('.', ',') + '%';
// narzut na kablu: preambula+SFD 8 B, przerwa miedzyramkowa 12 B, minimalna ramka 64 B (z FCS)
const wire = (payload, ip = 20, tcp = 20) => { const frame = Math.max(64, 14 + ip + tcp + payload + 4); return frame + 20; };
const eff = (payload, ip = 20, tcp = 20) => payload / wire(payload, ip, tcp);
const mss = (mtu, ip, ts) => mtu - ip - 20 - (ts ? 12 : 0);

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Co naprawdę wysyła twoja aplikacja', ...img('ns01-05-encapsulation.svg', 'Cztery poziomy: wiadomość HTTP; segment TCP z nagłówkiem TCP i danymi HTTP; pakiet z nagłówkiem IP, TCP i danymi; ramka z nagłówkiem Ethernet, IP, TCP, danymi i stopką FCS. Strzałka w dół oznacza kolejność dokładania nagłówków.'),
    body:
`Gdy Spring Boot zwraca \`ResponseEntity\`, a \`RestClient\` wysyła \`GET /users/42\`, w kodzie widzisz obiekty. Na kablu jest **ciąg bajtów**, w którym tekst żądania HTTP stanowi tylko część — przed nim stoją nagłówki TCP, IP i Ethernet, a za nim suma kontrolna ramki.

Ten proces to **enkapsulacja**: każda warstwa traktuje to, co dostała z góry, jako „dane” i dokłada własny nagłówek z informacjami potrzebnymi *jej* do pracy. Odbiorca zdejmuje nagłówki w odwrotnej kolejności — to **dekapsulacja**.

W tej lekcji:
- poznasz **model TCP/IP** — cztery warstwy, na których naprawdę działa Internet, i porównasz je z OSI,
- przeczytasz **bajt po bajcie** prawdziwą ramkę z żądaniem HTTP (z poprawnymi sumami kontrolnymi),
- policzysz **narzut nagłówków**, MTU i MSS,
- zobaczysz, które pola **zmieniają się po drodze** (MAC, TTL, NAT), a które nie,
- nauczysz się, dlaczego **TCP to strumień bajtów**, a nie wiadomości — co jest źródłem jednego z najczęstszych błędów w kodzie sieciowym w Javie.` },

  { type: 'DEFINITION', heading: 'Model TCP/IP: cztery warstwy i ich odpowiedniki w OSI', body:
`Model TCP/IP (RFC 1122, 1989) opisuje warstwy tak, jak zaimplementowano je w systemach operacyjnych:
- **Aplikacji** (OSI 5–7) — HTTP, DNS, TLS, SSH, SMTP, gRPC. Realizuje ją proces: JVM, Nginx, przeglądarka.
- **Transportowa** (OSI 4) — TCP, UDP, QUIC (nad UDP). Porty, niezawodność, kontrola przepływu i przeciążenia. Realizuje ją jądro systemu (gniazda).
- **Internetowa** (OSI 3) — IPv4, IPv6, ICMP. Adresowanie i routing między sieciami. Jądro systemu.
- **Dostępu do sieci / łącza** (OSI 1–2) — Ethernet, Wi-Fi, PPP; sterownik i karta sieciowa.

Często używa się też **modelu pięciowarstwowego** (hybrydowego), który rozdziela warstwę łącza na łącza danych i fizyczną — tak opisują sieci podręczniki i Cisco.

Kluczowe pojęcia:
- **Enkapsulacja / dekapsulacja** — dokładanie i zdejmowanie nagłówków.
- **PDU** (Protocol Data Unit) — jednostka danych warstwy: wiadomość, segment/datagram, pakiet, ramka.
- **MTU** (Maximum Transmission Unit) — największy *pakiet IP*, jaki mieści się w ramce danego łącza; w Ethernecie 1500 bajtów.
- **MSS** (Maximum Segment Size) — największa porcja *danych* w jednym segmencie TCP: MTU − nagłówek IP − nagłówek TCP (1500 − 20 − 20 = 1460 dla IPv4 bez opcji).
- **Multipleksacja** — pole w nagłówku mówi, komu przekazać dane wyżej: *EtherType* (który protokół L3), *Protocol / Next Header* w IP (który protokół L4), *port* w TCP/UDP (która aplikacja).` },

  { type: 'ANALOGY', heading: 'Koperta w kopercie w kopercie', body:
`Piszesz list do działu kadr w oddziale firmy (dane HTTP). Wkładasz go do koperty z numerem działu i numerem strony („dział 8080, strona 1 z 1”) — to **nagłówek TCP**. Tę kopertę wkładasz do większej z adresem budynku oddziału (**nagłówek IP**). A tę z kolei kurier wkłada do swojej torby z etykietą „następny punkt: sortownia na Woli” (**nagłówek Ethernet**).

W sortowni kurier wyjmuje kopertę z torby, czyta adres budynku, wybiera trasę i wkłada ją do *nowej* torby z etykietą kolejnego punktu — **adres budynku się nie zmienia, etykieta kuriera zmienia się na każdym odcinku**. Dopiero w budynku docelowym recepcja otwiera kopertę z adresem, przekazuje wewnętrzną do działu 8080, a dział wyjmuje list.

Dwie obserwacje, które przydadzą się przez cały tor:
- **Każdy pośrednik otwiera tylko tyle kopert, ile musi.** Kurier (przełącznik) czyta etykietę torby; sortownia (router) — adres budynku; recepcjonista z dostępem do treści (reverse proxy) — wszystko.
- **Koperty mają koszt.** Przy krótkim liście koperty ważą więcej niż treść — to narzut nagłówków.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Ramka z żądaniem HTTP bajt po bajcie', ...img('ns01-05-frame-bytes.svg', 'Linijka bajtów: Ethernet 14 bajtów (offset 0), IPv4 20 bajtów (offset 14), TCP 20 bajtów (offset 34), HTTP 79 bajtów (offset 54), FCS 4 bajty; legenda z opisem pól każdego nagłówka.'),
    body:
`Tak wygląda żądanie \`GET /users/42\` wysłane z kontenera 172.17.0.3 do aplikacji na 172.17.0.2:8080 w sieci Dockera — dokładnie w formacie panelu bajtów Wiresharka (FCS karta dokłada sama i zwykle nie widać jej w przechwyceniu):`,
    code:
`${HEX}

Ethernet (bajty 0–13)
  02 42 ac 11 00 02        MAC docelowy  (kontener aplikacji)
  02 42 ac 11 00 03        MAC źródłowy  (kontener klienta)
  08 00                    EtherType 0x0800 → w środku IPv4
IPv4 (bajty 14–33)
  45                       wersja 4, IHL 5 → nagłówek 5 × 4 = 20 B
  00                       DSCP/ECN
  00 77                    długość całkowita ${pk.totalLen} B (IP + TCP + dane)
  4c 1d                    identyfikator
  40 00                    flaga DF (nie fragmentuj), offset 0
  40                       TTL 64 (wartość domyślna Linuksa)
  06                       protokół 6 → w środku TCP
  ${hx(pk.ipSum >> 8).slice(2)} ${hx(pk.ipSum & 255).slice(2)}                    suma kontrolna nagłówka
  ac 11 00 03 / ac 11 00 02  IP źródłowy 172.17.0.3 / docelowy 172.17.0.2
TCP (bajty 34–53)
  c8 86 / 1f 90            port źródłowy 51334 (efemeryczny) / docelowy 8080
  5a 3c 91 e2 / 11 c0 de 01  numer sekwencyjny / numer potwierdzenia
  50 18                    data offset 5 (20 B), flagi 0x18 = PSH + ACK
  01 f6                    okno 502 (× skala okna z handshake’u)
  ${hx(pk.tcpSum >> 8).slice(2)} ${hx(pk.tcpSum & 255).slice(2)} 00 00              suma kontrolna, wskaźnik pilności
HTTP (bajty 54–${pk.frame.length - 1})
  47 45 54 20 2f 75 …      "GET /users/42 HTTP/1.1\\r\\nHost: …" (${pk.data.length} B tekstu ASCII)` },

  { type: 'CODE_BASIC', heading: 'Narzut nagłówków, MTU i MSS w liczbach', body:
`Policzmy, ile „kosztuje” przesłanie danych. Na kablu Ethernet każdą ramkę poprzedza 8 bajtów preambuły, a po niej musi nastąpić 12-bajtowa przerwa (IFG); minimalna ramka ma 64 bajty (krótsze dopełnia się zerami).`,
    code:
`public class Overhead {
    static int wireBytes(int payload, int ipHdr, int tcpHdr) {
        int frame = Math.max(64, 14 + ipHdr + tcpHdr + payload + 4);   // Ethernet + IP + TCP + dane + FCS
        return frame + 8 + 12;                                          // + preambuła/SFD + przerwa międzyramkowa
    }
    public static void main(String[] args) {
        int mtu = 1500;
        System.out.println("MSS IPv4      = " + (mtu - 20 - 20));        // 1460
        System.out.println("MSS IPv6      = " + (mtu - 40 - 20));        // 1440
        System.out.println("MSS IPv4 + TS = " + (mtu - 20 - 20 - 12));   // 1448 (opcja timestamps)
        for (int payload : new int[]{1460, 100, 10, 1}) {
            int wire = wireBytes(payload, 20, 20);
            System.out.printf("dane %4d B → na kablu %4d B → wydajność %.1f%%%n",
                payload, wire, 100.0 * payload / wire);
        }
    }
}
// dane 1460 B → na kablu ${wire(1460)} B → wydajność ${pct(eff(1460))}
// dane  100 B → na kablu  ${wire(100)} B → wydajność ${pct(eff(100))}
// dane   10 B → na kablu   ${wire(10)} B → wydajność ${pct(eff(10))}
// dane    1 B → na kablu   ${wire(1)} B → wydajność ${pct(eff(1))}   (ramka dopełniona do 64 B)` },

  { type: 'STEP_BY_STEP', heading: 'Od write() do read(): enkapsulacja i dekapsulacja krok po kroku', ...img('ns01-05-send-receive.svg', 'Po lewej stos nadawcy: aplikacja zapisuje do bufora gniazda, TCP dzieli na segmenty i nadaje numery, IP dodaje adresy i wybiera next hop, karta po ARP buduje ramkę z FCS. Po prawej stos odbiorcy: karta sprawdza FCS, MAC i EtherType, IP sprawdza adres i protokół, TCP porządkuje i potwierdza, aplikacja czyta z bufora.'),
    body:
`**Nadawca** (klient Java wysyła żądanie):
1. **Aplikacja** zapisuje bajty do strumienia gniazda (\`OutputStream.write\`). Trafiają do *bufora nadawczego* w jądrze — \`write()\` wraca, zanim cokolwiek opuści komputer.
2. **TCP** tnie strumień na segmenty nie większe niż MSS, nadaje numery sekwencyjne, dodaje porty i sumę kontrolną, pilnuje okna odbiorcy.
3. **IP** dodaje adresy, TTL, protokół (6 = TCP), sumę kontrolną nagłówka; z tablicy routingu wybiera interfejs i **next hop** (bramę albo samego odbiorcę, jeśli jest w tej samej sieci).
4. **Warstwa łącza** ustala MAC next hopa (ARP/ND), buduje ramkę z EtherType 0x0800 lub 0x86DD; karta dokłada FCS i zamienia bity na sygnał.

**Odbiorca** (serwer Spring Boot):
5. **Karta** sprawdza FCS (uszkodzone ramki odrzuca po cichu) i czy MAC docelowy jest jej (lub rozgłoszeniowy/multicast).
6. **EtherType** wskazuje IPv4 → jądro sprawdza sumę nagłówka i czy adres docelowy należy do hosta; **pole protokołu** wskazuje TCP.
7. **TCP** znajduje gniazdo po czwórce (IP i port źródła, IP i port celu), układa segmenty w kolejności, wysyła ACK, umieszcza dane w *buforze odbiorczym*.
8. **Aplikacja** (Tomcat) czyta bajty \`read()\` — tyle, ile akurat jest w buforze — i parsuje HTTP.

Każdy krok to też miejsce, gdzie coś może się nie udać — i gdzie obrońca może patrzeć: filtr na karcie (L2), firewall jądra nftables (L3/L4), WAF w proxy (L7).` },

  { type: 'NOTE', heading: 'Pola multipleksacji: kto następny?', body:
`Nagłówki tworzą łańcuch wskaźników „co jest w środku”. Te wartości warto rozpoznawać w Wiresharku i logach:`,
    code:
`EtherType (Ethernet, 2 B)          IP Protocol / IPv6 Next Header (1 B)     porty TCP/UDP (2 B)
0x0800  IPv4                       1   ICMP                                  22    SSH
0x86DD  IPv6                       2   IGMP                                  53    DNS
0x0806  ARP                        6   TCP                                   80    HTTP
0x8100  tag VLAN 802.1Q            17  UDP                                   443   HTTPS (TCP) / HTTP/3 (UDP)
0x88A8  podwójny tag (QinQ)        47  GRE                                   5432  PostgreSQL
0x8847  MPLS                       50  ESP (IPsec)                           6379  Redis
0x888E  EAPOL (802.1X)             58  ICMPv6                                8080  HTTP alternatywny
                                   89  OSPF                                  9092  Kafka
                                   132 SCTP

Rozmiary nagłówków (bez opcji): Ethernet 14 B (+4 z VLAN) + FCS 4 B, IPv4 20 B (do 60), IPv6 40 B,
TCP 20 B (do 60; typowo 32 z timestamps), UDP 8 B, ICMP 8 B, VXLAN 50 B narzutu, WireGuard 60/80 B.` },

  { type: 'CODE_PRACTICAL', heading: 'Parser nagłówków w Javie: ByteBuffer na surowych bajtach', body:
`Ten sam zrzut co wyżej, rozebrany kodem. Tak działają parsery protokołów (Netty, pcap4j, sondy IDS). \`ByteBuffer\` domyślnie czyta w kolejności sieciowej (big-endian), a \`& 0xFF\` / \`& 0xFFFF\` zamienia wartości ze znakiem na bez znaku.`,
    code:
`import java.nio.ByteBuffer;
import java.util.HexFormat;

public class FrameParser {
    public static void main(String[] args) {
        byte[] raw = HexFormat.of().parseHex(args[0]);          // ramka jako hex bez spacji
        ByteBuffer b = ByteBuffer.wrap(raw);

        int etherType = b.getShort(12) & 0xFFFF;                // 0x0800
        if (etherType != 0x0800) throw new IllegalArgumentException("to nie IPv4");
        int ip = 14;
        int ihl = (b.get(ip) & 0x0F) * 4;                       // 20
        int totalLen = b.getShort(ip + 2) & 0xFFFF;             // ${pk.totalLen}
        int ttl = b.get(ip + 8) & 0xFF;                         // 64
        int proto = b.get(ip + 9) & 0xFF;                       // 6 = TCP
        String src = ipv4(b, ip + 12), dst = ipv4(b, ip + 16);

        int tcp = ip + ihl;
        int sport = b.getShort(tcp) & 0xFFFF;                   // 51334
        int dport = b.getShort(tcp + 2) & 0xFFFF;               // 8080
        int dataOffset = ((b.get(tcp + 12) & 0xF0) >> 4) * 4;  // 20
        int flags = b.get(tcp + 13) & 0xFF;                     // 0x18 = PSH|ACK
        int payload = tcp + dataOffset;
        String http = new String(raw, payload, ip + totalLen - payload, java.nio.charset.StandardCharsets.US_ASCII);

        System.out.printf("%s:%d → %s:%d TTL=%d proto=%d flags=0x%02x%n", src, sport, dst, dport, ttl, proto, flags);
        System.out.println(http.lines().findFirst().orElse(""));   // GET /users/42 HTTP/1.1
    }
    static String ipv4(ByteBuffer b, int off) {
        return (b.get(off) & 0xFF) + "." + (b.get(off + 1) & 0xFF) + "." + (b.get(off + 2) & 0xFF) + "." + (b.get(off + 3) & 0xFF);
    }
}` },

  { type: 'USAGE', heading: 'Co widzi i co zmienia każdy element na drodze', ...img('ns01-05-hop-changes.svg', 'Tabela sześciu punktów drogi: laptop, przełącznik, router z NAT, routery ISP, reverse proxy, aplikacja. Adresy MAC zmieniają się na routerach i proxy, TTL i suma kontrolna IP na routerach, IP i port źródłowy na routerze z NAT, połączenie TCP i TLS na reverse proxy.'),
    body:
`Enkapsulacja wyjaśnia, *gdzie* szukać dowodów i *dlaczego* adresy w logach się różnią:
- **Przełącznik** nie zmienia niczego — przekazuje ramkę po MAC.
- **Router** zdejmuje ramkę, zmniejsza TTL (i przelicza sumę kontrolną IP), buduje nową ramkę z nowymi adresami MAC. Adresy IP zostają.
- **NAT/PAT** podmienia IP i port źródłowy — dlatego serwer widzi publiczny adres routera, a nie laptopa.
- **Reverse proxy / load balancer L7** kończy połączenie TCP i TLS i otwiera *nowe* do aplikacji. Aplikacja widzi IP proxy; prawdziwy adres klienta trafia do nagłówka \`X-Forwarded-For\` lub \`Forwarded\` (Spring: \`server.forward-headers-strategy\`), a na L4 do PROXY protocol.
- **Tunel (VPN, VXLAN, GRE)** wkłada cały pakiet lub ramkę jako *dane* do nowego pakietu — powstaje enkapsulacja w enkapsulacji, która zmniejsza dostępne MTU.

Dla obrońcy:
- Logi z różnych punktów pokazują **różne adresy tego samego połączenia** (NAT, proxy) — korelacja wymaga tabel NAT, nagłówków X-Forwarded-For i identyfikatorów żądań.
- **Tunelowanie** to także technika napastników: dane wyprowadzane w zapytaniach DNS, w ICMP albo w HTTPS do nietypowej domeny. Wykrywa się je po anomaliach rozmiaru, częstotliwości i entropii (rozdział o analizie ruchu).
- Nagłówek \`X-Forwarded-For\` od klienta jest **niezaufany** — ufaj mu tylko, gdy ustawiło go twoje proxy.` },

  { type: 'PITFALL', heading: 'Pułapki: MTU, MSS, offloading i strumień bajtów', body:
`- **TCP nie ma granic wiadomości.** Dwa \`write()\` po 100 B mogą dojść jako jeden \`read()\` 200 B albo trzy kawałki. Protokół aplikacyjny musi sam oznaczać granice (długość, separator, HTTP Content-Length). To najczęstszy błąd w ręcznie pisanym kodzie na gniazdach.
- **MTU ≠ MSS.** MTU dotyczy pakietu IP (1500), MSS danych TCP (1460). W tunelach oba maleją: WireGuard domyślnie MTU 1420, VXLAN wymaga 1550 w podkładzie albo 1450 w nakładce.
- **Za duże pakiety w tunelu** bez działającego PMTUD giną po cichu — handshake TLS (duże certyfikaty) wisi, choć ping działa.
- **Offloading karty (TSO/GSO/GRO)** — Wireshark na nadawcy pokazuje „segmenty” po 64 kB i „błędne sumy kontrolne”, bo dzieli je i liczy sumy dopiero karta. To nie błąd sieci.
- **Liczenie narzutu tylko nagłówkami** — na kablu dochodzą preambuła, przerwa międzyramkowa i dopełnienie do 64 B; przy małych wiadomościach (IoT, telemetria) wydajność spada do kilku procent.
- **IPv6 ma większy nagłówek** (40 B) — MSS 1440 zamiast 1460.
- **Zaufanie do X-Forwarded-For** z Internetu pozwala sfałszować adres klienta w logach i ominąć allow-listy IP.` },

  { type: 'CODE_WRONG', heading: 'Źle: jeden read() = jedna wiadomość', body:
`Prosty protokół „komenda w jednym pakiecie”. Działa na localhoście, psuje się w prawdziwej sieci: dłuższe wiadomości przychodzą w kawałkach, krótkie sklejają się w jeden \`read()\`.`,
    code:
`try (Socket s = server.accept()) {
    InputStream in = s.getInputStream();
    byte[] buf = new byte[4096];
    int n = in.read(buf);                        // ZAKŁADA, że cała wiadomość przyszła naraz
    String command = new String(buf, 0, n, StandardCharsets.UTF_8);
    handle(command);                             // raz pół komendy, raz dwie sklejone
}
// Objawy: losowe błędy parsowania JSON przy większym ruchu, „działa lokalnie, nie działa na produkcji”,
// problemy pojawiające się dopiero przez VPN (mniejsze MTU → więcej kawałków).` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: ramkowanie wiadomości długością', body:
`Każda wiadomość zaczyna się 4-bajtową długością (big-endian), a odbiorca czyta dokładnie tyle bajtów (\`readFully\`). Do tego limit rozmiaru — bez niego napastnik zadeklaruje 2 GB i wyczerpie pamięć (to także kwestia bezpieczeństwa).`,
    code:
`static final int MAX_MESSAGE = 1 << 20;          // 1 MiB — ochrona przed DoS

static void send(DataOutputStream out, String msg) throws IOException {
    byte[] data = msg.getBytes(StandardCharsets.UTF_8);
    out.writeInt(data.length);                   // nagłówek: długość
    out.write(data);
    out.flush();
}

static String receive(DataInputStream in) throws IOException {
    int len = in.readInt();                      // czeka na pełne 4 bajty
    if (len < 0 || len > MAX_MESSAGE) throw new IOException("Nieprawidłowa długość: " + len);
    byte[] data = new byte[len];
    in.readFully(data);                          // czyta, aż zbierze dokładnie len bajtów
    return new String(data, StandardCharsets.UTF_8);
}
// W Netty to samo robią LengthFieldBasedFrameDecoder i LengthFieldPrepender.` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy schodzić do poziomu bajtów i nagłówków', body:
`- **Diagnoza „dziwnych” problemów** — zawieszone TLS przez VPN, połączenia zrywane przez proxy, ruch idący nie tam — pomaga tylko capture i czytanie nagłówków.
- **Planowanie wydajności** — przy małych wiadomościach (telemetria, gRPC streaming, IoT, gry) narzut nagłówków dominuje; łączenie wiadomości (batching) i kompresja mają duży wpływ.
- **Tunele i chmura** — przy VXLAN, WireGuard, IPsec i sieciach overlay Kubernetesa zawsze policz MTU/MSS.
- **Własne protokoły na gniazdach** — zawsze ramkowanie (długość lub separator), limity rozmiaru, kolejność bajtów.
- **Bezpieczeństwo i forensyka** — analiza ruchu w incydencie, pisanie reguł IDS (Suricata dopasowuje do pól nagłówków i buforów protokołów), wykrywanie tunelowania i eksfiltracji.
- **Na co dzień** — wystarczą narzędzia wyższego poziomu (curl -v, logi), ale wiedza o enkapsulacji pozwala zrozumieć, *co* pokazują.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Model TCP/IP ma 4 warstwy: aplikacji (OSI 5–7), transportową (4), internetową (3), dostępu do sieci (1–2).
- **Enkapsulacja**: dane → segment (TCP) → pakiet (IP) → ramka (Ethernet + FCS) → bity; **dekapsulacja** w odwrotnej kolejności.
- Multipleksacja: EtherType (0x0800 IPv4, 0x86DD IPv6, 0x0806 ARP) → protokół IP (6 TCP, 17 UDP, 1 ICMP) → port.
- Nagłówki: Ethernet 14 B + FCS 4 B, IPv4 20 B, IPv6 40 B, TCP 20 B (typowo 32), UDP 8 B.
- **MTU 1500 → MSS 1460** (IPv4), 1440 (IPv6), mniej w tunelach.
- Po drodze zmieniają się MAC (każdy odcinek), TTL (każdy router), IP/port źródłowy (NAT), a proxy L7 tworzy nowe połączenie TCP.
- **TCP to strumień bajtów** — ramkuj wiadomości i ograniczaj ich rozmiar.
- Następna lekcja: **przepustowość, opóźnienie, jitter i utrata pakietów** — jak mierzyć jakość tej drogi.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const fr = pk.frame;
const fields = [
  ['EtherType (bajty 12–13)', `${hx(fr[12])} ${hx(fr[13])} → 0x0800 = IPv4`],
  ['wersję i długość nagłówka IP (bajt 14)', '0x45 → wersja 4, IHL 5 → 5 × 4 = 20 bajtów'],
  ['długość całkowitą pakietu IP (bajty 16–17)', `0x0077 = ${pk.totalLen} bajtów = 20 (IP) + 20 (TCP) + ${pk.data.length} (HTTP)`],
  ['TTL i protokół (bajty 22–23)', '0x40 = 64 (Linux), 0x06 = TCP'],
  ['adres IP źródłowy (bajty 26–29)', 'ac 11 00 03 → 172.17.0.3'],
  ['porty TCP (bajty 34–37)', 'c8 86 → 51334 (efemeryczny klienta), 1f 90 → 8080 (aplikacja)'],
  ['flagi TCP (bajt 47)', '0x18 = 0001 1000 → PSH (0x08) + ACK (0x10)'],
];
for (const [f, a] of fields) ex(`Na podstawie zrzutu ramki z lekcji odczytaj ${f} i zinterpretuj wartość.`, 'Policz offset: Ethernet 0–13, IPv4 od 14, TCP od 34.', a + '.');
for (const [mtu, desc, ip, ts, note] of [[1500, 'Ethernet, IPv4, bez opcji TCP', 20, false, ''], [1500, 'Ethernet, IPv6, opcja timestamps', 40, true, ''], [1492, 'PPPoE (DSL), IPv4', 20, false, 'PPPoE zabiera 8 B z 1500'], [1420, 'tunel WireGuard, IPv4 w środku', 20, false, 'WireGuard ustawia MTU 1420, rezerwując 80 B na nagłówki (IPv6 + UDP + WireGuard)'], [1450, 'nakładka VXLAN na podkładzie z MTU 1500, IPv4', 20, true, 'VXLAN dokłada 50 B']]) {
  ex(`Policz MSS dla łącza: ${desc} (MTU ${mtu}).`, 'MSS = MTU − nagłówek IP − nagłówek TCP (20 B + 12 B przy timestamps).', `MSS = ${mtu} − ${ip} − 20${ts ? ' − 12' : ''} = ${mss(mtu, ip, ts)} B.${note ? ' ' + note + '.' : ''}`);
}
for (const p of [1460, 200, 20]) {
  ex(`Ile bajtów zajmuje na kablu Ethernet (z preambułą i przerwą międzyramkową) segment TCP/IPv4 z ${p} B danych bez opcji? Jaka jest wydajność?`, 'Ramka = 14 + 20 + 20 + dane + 4 (min. 64 B); na kablu + 8 (preambuła) + 12 (IFG).', `Ramka: ${Math.max(64, 58 + p)} B; na kablu ${wire(p)} B; wydajność ${p}/${wire(p)} = ${pct(eff(p))}.`);
}
ex('Ułóż od zewnątrz do środka nagłówki ramki VXLAN przenoszącej żądanie HTTP między podami Kubernetesa na dwóch węzłach.', 'VXLAN przenosi całą ramkę L2 jako dane UDP.', 'Ethernet (węzeł → węzeł/brama) → IP zewnętrzne (adresy węzłów) → UDP (port 4789) → nagłówek VXLAN (VNI) → Ethernet wewnętrzny (MAC podów) → IP wewnętrzne (adresy podów) → TCP → HTTP. Narzut ok. 50 B, dlatego MTU w nakładce to zwykle 1450.');
ex('Klient z laptopa (192.168.1.20) łączy się przez router domowy z NAT (publiczny 83.10.5.7) z aplikacją za reverse proxy (203.0.113.10 → aplikacja 10.0.1.5). Jakie IP źródłowe zobaczy w logach: router ISP, reverse proxy, aplikacja? Jak aplikacja może poznać prawdziwy adres klienta?', 'NAT podmienia adres; proxy otwiera nowe połączenie.', 'Router ISP: 83.10.5.7 (po NAT). Reverse proxy: 83.10.5.7. Aplikacja: adres wewnętrzny proxy (np. 10.0.0.4). Prawdziwy adres (publiczny 83.10.5.7, bo prywatny 192.168.1.20 znika za NAT) przekazuje proxy w nagłówku X-Forwarded-For/Forwarded; Spring: server.forward-headers-strategy=framework i lista zaufanych proxy. Adres prywatny laptopa nie jest znany nikomu poza siecią domową.');
ex('Napisz w Javie metodę int ipv4Checksum(byte[] header), liczącą sumę kontrolną nagłówka IPv4 (suma 16-bitowych słów w arytmetyce z przeniesieniem, negacja). Sprawdź ją na nagłówku z lekcji: wynik dla nagłówka z wpisaną sumą powinien wynosić 0.', 'Sumuj słowa jako liczby bez znaku, przenoś nadmiar z bitu 16 na początek.', `static int ipv4Checksum(byte[] h) {\n    int sum = 0;\n    for (int i = 0; i < h.length; i += 2) {\n        sum += ((h[i] & 0xFF) << 8) | (h[i + 1] & 0xFF);\n    }\n    while ((sum >> 16) != 0) sum = (sum & 0xFFFF) + (sum >> 16);\n    return ~sum & 0xFFFF;\n}\nDla nagłówka z polem sumy wyzerowanym wynik to ${hx(pk.ipSum, 4)}; po wpisaniu tej wartości funkcja zwraca 0 (nagłówek poprawny).`);
ex('Uruchom w labie Docker dwa kontenery (klient z curl i aplikację), przechwyć ruch tcpdump -i any -w http.pcap port 8080, otwórz w Wiresharku i odszukaj w panelu bajtów te same pola co w lekcji. Co jest inne?', 'Zwróć uwagę na nagłówek „Linux cooked capture” przy -i any oraz opcje TCP.', 'Różnice: przy -i any zamiast Ethernetu jest pseudo-nagłówek Linux cooked capture (SLL/SLL2); segmenty TCP zwykle mają opcje (timestamps → nagłówek 32 B, data offset 8); inne numery sekwencyjne, ID i porty efemeryczne; suma kontrolna może być oznaczona jako niezweryfikowana z powodu offloadingu.');
ex('Napisz serwer i klienta w Javie, którzy wymieniają wiadomości JSON z ramkowaniem długością (4 bajty big-endian). Przetestuj, wysyłając 1000 wiadomości o losowych rozmiarach 10 B–100 kB, i sprawdź, że żadna nie została sklejona ani ucięta.', 'Użyj DataOutputStream.writeInt i DataInputStream.readFully; dodaj limit rozmiaru.', 'Szkic: klient w pętli wysyła send(out, json) z licznikiem w treści; serwer w pętli receive(in), parsuje JSON i sprawdza kolejny numer. Test przechodzi, gdy wszystkie 1000 numerów przyszło w kolejności, a rozmiary się zgadzają. Dla porównania wersja z jednym read() na wiadomość zgłasza błędy parsowania już przy wiadomościach > kilkunastu kB.');
ex('Analityk zauważył, że host wysyła co 30 sekund zapytania DNS o nazwy typu 4a6f686e446f65.x7k.example.net. Dlaczego może to być tunelowanie danych i jak to wykryć?', 'Zwróć uwagę na etykietę przed domeną.', '4a6f686e446f65 to hex tekstu ASCII („JohnDoe”) — dane zakodowane w nazwie hosta i wysyłane jako zapytania DNS (enkapsulacja danych w protokole aplikacyjnym). Wskaźniki: długie, losowo wyglądające etykiety, wysoka entropia, wiele unikalnych subdomen jednej domeny, regularność czasowa. Wykrywanie: logi DNS w SIEM, reguły na długość i entropię etykiet, Zeek dns.log; blokada: wymuszenie firmowego resolvera i filtrowanie domen.');
ex('Odczytaj ze zrzutu adres IP docelowy (bajty 30–33) i port docelowy, a następnie powiedz, do którego kontenera i usługi trafia żądanie.', 'Adres docelowy stoi po adresie źródłowym.', 'ac 11 00 02 → 172.17.0.2, port 0x1f90 = 8080 → kontener aplikacji Spring Boot nasłuchujący na 8080 w sieci bridge Dockera.');
ex('Ile bajtów danych HTTP niesie pakiet z lekcji? Policz to wyłącznie z nagłówków (długość całkowita IP, IHL, data offset TCP), bez liczenia znaków.', 'Dane = total length − nagłówek IP − nagłówek TCP.', `${pk.totalLen} − 20 − 20 = ${pk.data.length} bajtów. Tak samo robi Wireshark, wyświetlając „TCP payload (${pk.data.length} bytes)”.`);
ex('Wireshark pokazuje „Frame 1: 133 bytes on wire”, a karta wysłała 137 bajtów. Skąd różnica i czego jeszcze nie widać w przechwyceniu?', 'Co karta dokłada sama?', 'Brakuje 4 bajtów FCS — karta liczy i dokłada CRC sama, a przy odbiorze zwykle je usuwa przed przekazaniem do systemu. Nie widać też preambuły z SFD (8 B) ani przerwy międzyramkowej (12 B) — to elementy warstwy fizycznej.');
ex('Rozpisz enkapsulację zapytania DNS o api.javaquest.local wysłanego przez kontener do resolvera Dockera 127.0.0.11. Wymień nagłówki od zewnątrz i pola multipleksacji.', 'DNS zwykle używa UDP.', 'Uwaga: 127.0.0.11 to adres pętli zwrotnej w przestrzeni nazw kontenera, więc pakiet nie wychodzi na bridge — idzie przez interfejs lo. Kolejność: IPv4 (Protocol = 17) → UDP (port docelowy 53, źródłowy efemeryczny) → komunikat DNS (ID, flagi, pytanie typu A). Gdyby resolver był w sieci: Ethernet (EtherType 0x0800) → IPv4 → UDP → DNS.');
ex('Użytkownicy VPN skarżą się, że strony ładują się częściowo, a duże pobrania wiszą. Ping działa. Przeprowadź rozumowanie z MTU i zaproponuj dwie poprawki.', 'Tunel zmniejsza MTU; co, jeśli ICMP jest blokowane?', 'Tunel dokłada nagłówki, więc efektywne MTU jest mniejsze niż 1500. Pakiety z DF większe niż MTU tunelu wymagają ICMP „fragmentation needed”; jeśli firewall je blokuje, duże pakiety giną (PMTUD black hole), małe (ping, początek strony) przechodzą. Poprawki: MSS clamping na bramie VPN (np. tcp option maxseg size set rt mtu w nftables), mniejsze MTU na interfejsie tunelu, odblokowanie ICMP typu 3 kod 4.');
ex('Sprawdź narzędziami MTU interfejsów na swoim komputerze i w kontenerze Dockera (ip link / Get-NetIPInterface) oraz MSS ogłaszane w SYN przy połączeniu curl (tcpdump -nv). Porównaj wyniki.', 'MSS widać w opcjach segmentu SYN.', 'Typowo: eth0/Wi-Fi MTU 1500 → MSS 1460 w SYN; docker0 i veth 1500; w WSL2 lub VPN może być 1400–1420 → MSS 1360–1380. tcpdump: „Flags [S], ... options [mss 1460,sackOK,TS ...,nop,wscale 7]”.');
ex('Dlaczego mikroserwis wysyłający tysiące małych wiadomości telemetrycznych (po 30 B) osobnymi pakietami marnuje łącze? Policz wydajność i zaproponuj zmianę.', 'Użyj wzoru na bajty na kablu z lekcji.', `30 B danych → ramka ${Math.max(64, 58 + 30)} B → na kablu ${wire(30)} B → wydajność ${pct(eff(30))}. Zmiana: łączenie wiadomości w paczki (batching, np. co 100 ms lub 64 kB), kompresja, jedno długotrwałe połączenie ze strumieniowaniem (gRPC), co podnosi wydajność powyżej 90%.`);
ex('Zaprojektuj prosty format binarnej wiadomości dla własnego protokołu (wersja, typ, długość, dane) i napisz w Javie metody encode/decode z ByteBuffer. Uzasadnij kolejność bajtów i limity.', 'Wzoruj się na nagłówkach IP/TCP: stałe pola na początku, potem dane.', 'Przykład: [wersja 1 B][typ 1 B][długość 4 B big-endian][dane]. encode: ByteBuffer.allocate(6 + len).put(ver).put(type).putInt(len).put(data). decode: najpierw 6 B nagłówka, walidacja wersji i 0 ≤ długość ≤ limit, potem readFully(data). Big-endian (kolejność sieciowa) — zgodność z narzędziami i ByteBuffer; limit chroni przed DoS; pole wersji pozwala rozwijać protokół.');
ex('Porównaj, co widzi o tym samym żądaniu HTTPS: przełącznik w biurze, firewall stanowy na brzegu, dostawca Internetu i reverse proxy firmy. Uwzględnij adresy, porty, nazwę hosta (SNI), ścieżkę URL i treść.', 'Pomyśl, które nagłówki są zaszyfrowane TLS.', 'Przełącznik: przekazuje ramki po MAC, niczego wyżej nie interpretuje. Firewall stanowy: IP, porty, stan; SNI z ClientHello (o ile nie ma ECH), treść tylko przy inspekcji TLS. ISP: IP, porty, SNI (bez ECH), rozmiary i czasy — bez ścieżki i treści. Reverse proxy (kończy TLS): wszystko — host, ścieżka, nagłówki, treść. Wniosek: ścieżka URL i treść są chronione TLS aż do miejsca jego zakończenia.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111305);
add('Ile warstw ma model TCP/IP według RFC 1122?', '4', ['7', '5', '3'], 'Aplikacji, transportowa, internetowa, dostępu do sieci (łącza).');
add('Której warstwie OSI odpowiada warstwa internetowa modelu TCP/IP?', 'Sieciowej (3)', ['Transportowej (4)', 'Łącza danych (2)', 'Aplikacji (7)'], 'IP i ICMP to warstwa 3.');
add('Którym warstwom OSI odpowiada warstwa aplikacji TCP/IP?', 'Warstwom 5, 6 i 7', ['Tylko warstwie 7', 'Warstwom 4–7', 'Warstwom 1 i 2'], 'Sesja, prezentacja i aplikacja są w TCP/IP realizowane przez proces aplikacji.');
add('Którym warstwom OSI odpowiada warstwa dostępu do sieci TCP/IP?', 'Fizycznej i łącza danych (1–2)', ['Sieciowej (3)', 'Transportowej (4)', 'Sesji (5)'], 'Ethernet, Wi-Fi, sterowniki i karta sieciowa.');
add('Co to jest enkapsulacja?', 'Dokładanie przez każdą warstwę własnego nagłówka do danych warstwy wyższej', ['Szyfrowanie danych', 'Kompresja nagłówków', 'Usuwanie nagłówków przez odbiorcę'], 'Usuwanie nagłówków po stronie odbiorcy to dekapsulacja.');
add('Jaka jest poprawna kolejność enkapsulacji od aplikacji?', 'dane → segment → pakiet → ramka → bity', ['dane → pakiet → segment → ramka → bity', 'ramka → pakiet → segment → dane → bity', 'bity → ramka → pakiet → segment → dane'], 'TCP dokłada nagłówek do danych, IP do segmentu, Ethernet do pakietu.');
add('Co oznacza MTU 1500 w Ethernecie?', 'Największy pakiet IP, który zmieści się w jednej ramce, ma 1500 bajtów', ['Ramka ma zawsze 1500 bajtów', 'Dane TCP mogą mieć 1500 bajtów', 'Łącze ma przepustowość 1500 Mb/s'], 'Ramka z nagłówkiem i FCS ma do 1518 B (1522 z VLAN).');
add('Ile wynosi MSS dla IPv4 i TCP bez opcji przy MTU 1500?', '1460', ['1500', '1480', '1440'], '1500 − 20 (IP) − 20 (TCP).');
add('Dlaczego MSS dla IPv6 przy MTU 1500 wynosi 1440?', 'Nagłówek IPv6 ma 40 bajtów zamiast 20', ['IPv6 nie ma nagłówka TCP', 'IPv6 wymaga MTU 1280', 'IPv6 szyfruje dane'], '1500 − 40 − 20 = 1440.');
add('Co oznacza pole EtherType 0x86DD?', 'W ramce jest pakiet IPv6', ['W ramce jest pakiet IPv4', 'Ramka zawiera ARP', 'Ramka ma tag VLAN'], '0x0800 IPv4, 0x86DD IPv6, 0x0806 ARP, 0x8100 VLAN.');
add('Jaką wartość ma pole Protocol w nagłówku IPv4 dla UDP?', '17', ['6', '1', '58'], '6 TCP, 17 UDP, 1 ICMP, 58 ICMPv6.');
add('Jaka jest minimalna długość ramki Ethernet (z FCS)?', '64 bajty', ['46 bajtów', '1500 bajtów', '14 bajtów'], 'Krótsze ramki są dopełniane (padding) do 64 B.');
add('Ile bajtów ma nagłówek UDP?', '8', ['20', '4', '40'], 'Port źródłowy, docelowy, długość i suma kontrolna — po 2 B.');
add('Ile bajtów ma nagłówek IPv4 bez opcji?', '20', ['40', '14', '8'], 'Pole IHL = 5 oznacza 5 słów 32-bitowych.');
add('Co oznacza bajt 0x45 na początku nagłówka IPv4?', 'Wersja 4 i długość nagłówka 5 słów (20 bajtów)', ['TTL 69', 'Protokół 45', 'Długość pakietu 69 bajtów'], 'Górny nibble = wersja, dolny = IHL.');
add('Które pole zmienia każdy router na drodze pakietu IPv4?', 'TTL (oraz w konsekwencji suma kontrolna nagłówka)', ['Adres IP docelowy', 'Port docelowy', 'Numer sekwencyjny TCP'], 'Router dekrementuje TTL i przelicza sumę kontrolną nagłówka.');
add('Co dzieje się z adresami MAC, gdy pakiet przechodzi przez router?', 'Router tworzy nową ramkę z nowymi adresami MAC dla kolejnego odcinka', ['Adresy MAC pozostają do końca drogi', 'Adresy MAC są szyfrowane', 'Router usuwa adres MAC docelowy'], 'MAC ma znaczenie tylko w obrębie jednego segmentu L2.');
add('Dlaczego aplikacja za reverse proxy widzi adres IP proxy zamiast klienta?', 'Proxy kończy połączenie TCP klienta i otwiera nowe do aplikacji', ['Bo proxy szyfruje adres klienta', 'Bo TCP nie przenosi adresów', 'Bo aplikacja działa na warstwie 2'], 'Prawdziwy adres przekazuje się w X-Forwarded-For/Forwarded lub PROXY protocol.');
add('Kiedy można ufać nagłówkowi X-Forwarded-For?', 'Tylko gdy ustawiło go zaufane proxy własnej infrastruktury', ['Zawsze', 'Nigdy, nawet z własnego proxy', 'Tylko w HTTP/1.0'], 'Klient może wysłać dowolną wartość — trzeba ufać tylko ostatniemu wpisowi dodanemu przez własne proxy.');
add('Co jest prawdą o TCP z punktu widzenia aplikacji?', 'TCP dostarcza strumień bajtów bez granic wiadomości', ['Każdy write() odpowiada jednemu read()', 'TCP zachowuje granice wiadomości jak UDP', 'TCP dzieli dane zawsze po 1460 bajtów na read()'], 'Granice wiadomości musi wyznaczać protokół aplikacyjny.');
add('Który sposób poprawnie odczytuje wiadomość o znanej długości z gniazda w Javie?', 'DataInputStream.readFully(bufor)', ['Jedno InputStream.read(bufor)', 'InputStream.available()', 'Socket.getReceiveBufferSize()'], 'readFully czyta, aż zbierze dokładnie tyle bajtów, ile ma bufor.');
add('Dlaczego przy ramkowaniu długością należy sprawdzać maksymalny rozmiar wiadomości?', 'Bez limitu napastnik może zadeklarować ogromną długość i wyczerpać pamięć serwera', ['Bo Java nie obsługuje tablic większych niż 1 kB', 'Bo TCP odrzuca długie wiadomości', 'Bo nagłówek długości jest szyfrowany'], 'To klasyczna ochrona przed DoS w parserach protokołów.');
add('Wireshark na nadawcy pokazuje segmenty TCP po 64 kB i ostrzeżenia o błędnej sumie kontrolnej. Najbardziej prawdopodobna przyczyna to:', 'Offloading karty sieciowej (TSO/checksum offload)', ['Uszkodzony kabel', 'Atak MITM', 'Błąd w aplikacji Java'], 'Jądro przekazuje karcie duże bufory; dzieli je i liczy sumy dopiero sprzęt.');
add('Jaki narzut dokłada enkapsulacja VXLAN?', 'Około 50 bajtów', ['8 bajtów', '0 bajtów', '500 bajtów'], 'Zewnętrzny Ethernet 14 + IP 20 + UDP 8 + VXLAN 8.');
add('Jaki port UDP używa VXLAN?', '4789', ['443', '4500', '1194'], 'IANA przydzieliła 4789; 4500 to IPsec NAT-T, 1194 OpenVPN.');
add('Do czego służy suma FCS w ramce Ethernet?', 'Do wykrycia uszkodzenia ramki podczas transmisji', ['Do szyfrowania ramki', 'Do numerowania ramek', 'Do wyboru trasy'], 'CRC-32; uszkodzone ramki są odrzucane bez powiadomienia nadawcy.');
add('Co robi odbiorca, gdy suma FCS się nie zgadza?', 'Odrzuca ramkę po cichu; ewentualne ponowienie zapewni TCP', ['Wysyła ICMP do nadawcy', 'Naprawia błędne bity', 'Przekazuje ramkę z ostrzeżeniem'], 'Niezawodność to zadanie TCP (retransmisja), nie Ethernetu.');
add('Który element sieci NIE zmienia żadnego pola ramki przy przekazywaniu?', 'Przełącznik (bez tagowania VLAN)', ['Router', 'NAT', 'Reverse proxy'], 'Przełącznik przekazuje ramkę po adresie MAC bez modyfikacji.');
add('Co oznacza flaga DF w nagłówku IPv4?', 'Pakietu nie wolno fragmentować po drodze', ['Pakiet jest ostatnim fragmentem', 'Pakiet ma wysoki priorytet', 'Pakiet jest zaszyfrowany'], 'DF jest podstawą Path MTU Discovery: zbyt duży pakiet wywołuje ICMP „fragmentation needed”.');
add('Które pole wskazuje odbiorcy, że w pakiecie IPv4 znajduje się segment TCP?', 'Protocol = 6', ['EtherType = 6', 'TTL = 6', 'Port = 6'], 'Każda warstwa ma pole wskazujące następny protokół.');
add('Jak nazywa się w modelu TCP/IP mechanizm kierowania danych do właściwej aplikacji na hoście?', 'Multipleksacja z użyciem portów', ['Routing', 'Enkapsulacja VLAN', 'Fragmentacja'], 'Gniazdo identyfikuje czwórka: IP i port źródła oraz celu.');

// generowane: MSS
const mssCases = [[1500, 20, false], [1500, 20, true], [1500, 40, false], [1500, 40, true], [1492, 20, false], [1420, 20, false], [1420, 40, false], [1450, 20, true], [9000, 20, false], [1280, 40, false]];
for (const [mtu, ip, ts] of mssCases) {
  const v = mss(mtu, ip, ts);
  add(`Jaki jest MSS przy MTU ${mtu}, ${ip === 20 ? 'IPv4' : 'IPv6'}${ts ? ' i opcji TCP timestamps (12 B)' : ' i TCP bez opcji'}?`, String(v), [String(mtu - 20), String(v + 12), String(v - 20), String(mtu - ip)], `${mtu} − ${ip} − 20${ts ? ' − 12' : ''} = ${v}.`);
}
const ets = [['0x0800', 'IPv4'], ['0x0806', 'ARP'], ['0x8100', 'Tag VLAN 802.1Q'], ['0x888E', 'EAPOL (802.1X)']];
const etn = ['IPv4', 'IPv6', 'ARP', 'Tag VLAN 802.1Q', 'EAPOL (802.1X)', 'MPLS'];
for (const [v, n] of ets) add(`Co oznacza EtherType ${v}?`, n, etn.filter((x) => x !== n).slice(0, 3), `${v} → ${n}.`);
const prs = [[1, 'ICMP'], [6, 'TCP'], [50, 'ESP (IPsec)'], [89, 'OSPF'], [58, 'ICMPv6']];
const prn = ['ICMP', 'TCP', 'UDP', 'ESP (IPsec)', 'OSPF', 'ICMPv6', 'GRE'];
for (const [v, n] of prs) add(`Który protokół oznacza wartość ${v} w polu Protocol/Next Header?`, n, prn.filter((x) => x !== n).slice(0, 3), `${v} = ${n}.`);
for (const p of [1460, 512, 100, 40, 10, 1]) {
  const w = wire(p);
  add(`Ile bajtów zajmie na kablu Ethernet (z preambułą 8 B i przerwą 12 B) segment TCP/IPv4 bez opcji z ${p} B danych?`, String(w), [String(58 + p), String(w - 20), String(w + 4), String(p + 40)], `Ramka max(64, 14 + 20 + 20 + ${p} + 4) = ${Math.max(64, 58 + p)} B, plus 20 B → ${w} B. Wydajność ${pct(eff(p))}.`);
}
const hexq = [
  ['c8 86', '51334'], ['1f 90', '8080'], ['01 bb', '443'], ['00 35', '53'], ['14 46', '5190'], ['00 77', '119'],
];
for (const [h, v] of hexq) {
  const n = parseInt(h.replace(' ', ''), 16);
  add(`Jaką wartość dziesiętną ma 16-bitowe pole zapisane w zrzucie jako „${h}” (kolejność sieciowa)?`, v, [String(parseInt(h.split(' ').reverse().join(''), 16)), String(n + 1), String(n - 256 > 0 ? n - 256 : n + 256)], `${h} = ${hx(n, 4)} = ${v} (najstarszy bajt pierwszy).`);
}
for (const [b, a, w] of [['0x46', 'IHL 6 → nagłówek 24 B (z opcjami)', ['IHL 4 → 16 B', 'wersja 6 → IPv6', 'TTL 70']], ['0x18', 'PSH + ACK', ['SYN + ACK', 'FIN + ACK', 'RST']], ['0x12', 'SYN + ACK', ['PSH + ACK', 'SYN', 'FIN']], ['0x02', 'SYN', ['ACK', 'FIN', 'RST + ACK']]]) {
  const isIp = b === '0x46';
  add(isIp ? `Pierwszy bajt nagłówka IPv4 to ${b}. Co z niego wynika?` : `Bajt flag TCP ma wartość ${b}. Jakie flagi są ustawione?`, a, w, isIp ? 'Górny nibble 4 = IPv4, dolny 6 = 6 słów × 4 B = 24 B.' : 'Bity flag: FIN 0x01, SYN 0x02, RST 0x04, PSH 0x08, ACK 0x10, URG 0x20.');
}
add('Ile bajtów zajmuje nagłówek TCP z opcją timestamps (typowy w Linuksie)?', '32', ['20', '40', '60'], '20 B podstawy + 12 B opcji (timestamps 10 B + 2 B NOP) → data offset 8.');
add('Jaka jest maksymalna długość nagłówka IPv4 z opcjami?', '60 bajtów', ['40 bajtów', '20 bajtów', '1500 bajtów'], 'IHL ma 4 bity: maksymalnie 15 słów × 4 B = 60 B.');
add('Ile bajtów ma tag VLAN 802.1Q dokładany do ramki?', '4', ['2', '8', '12'], 'TPID 0x8100 (2 B) + priorytet, DEI i VLAN ID (2 B).');
add('Dlaczego przy wysyłaniu 1 bajtu danych przez TCP wydajność łącza jest bardzo niska?', 'Nagłówki, minimalna długość ramki i preambuła zajmują ponad 80 bajtów na każdy taki segment', ['Bo TCP wysyła każdy bajt dwa razy', 'Bo Ethernet nie obsługuje małych ramek', 'Bo dane są szyfrowane'], 'Dlatego stosuje się buforowanie i łączenie małych zapisów (Nagle, batching).');
add('Co nagłówek IP zawiera, a czego nie zawiera?', 'Zawiera adresy IP źródła i celu, ale nie zawiera numerów portów', ['Zawiera porty, ale nie adresy', 'Zawiera adresy MAC', 'Zawiera ścieżkę URL'], 'Porty są w nagłówku warstwy transportowej (TCP/UDP).');
add('Tunel VPN typu site-to-site to przykład:', 'Enkapsulacji pakietu IP jako danych w innym pakiecie (często zaszyfrowanym)', ['Dekapsulacji', 'Fragmentacji IPv6', 'Multipleksacji portów'], 'Wewnętrzny pakiet podróżuje „w kopercie” zewnętrznego.');
add('Dlaczego tunelowanie interesuje analityków SOC?', 'Bo napastnicy ukrywają dane i komunikację C2 w innych protokołach (DNS, ICMP, HTTPS)', ['Bo tunele zawsze oznaczają atak', 'Bo tunele wyłączają logowanie', 'Bo tunele działają tylko w warstwie 1'], 'Wykrywa się je po anomaliach: rozmiarze, częstotliwości, entropii i celach ruchu.');
const offs = [['EtherType', 12], ['pierwszy bajt nagłówka IPv4', 14], ['pole TTL', 22], ['pole Protocol', 23], ['adres IP źródłowy', 26], ['adres IP docelowy', 30], ['port źródłowy TCP', 34], ['bajt flag TCP', 47]];
for (const [f, o] of offs) add(`Od którego bajtu (licząc od 0) zaczyna się ${f} w ramce Ethernet z IPv4 bez opcji i TCP?`, String(o), [String(o + 2), String(o - 2), String(o + 14), String(o + 4)], `Ethernet zajmuje bajty 0–13, IPv4 14–33, TCP od 34; ${f} zaczyna się od bajtu ${o}.`);
const hdrs = [['Ethernet + IPv4 + TCP (bez opcji, bez FCS)', 54], ['Ethernet + IPv6 + TCP (bez opcji, bez FCS)', 74], ['Ethernet + IPv4 + UDP (bez FCS)', 42], ['Ethernet z tagiem VLAN + IPv4 + TCP (bez FCS)', 58], ['Ethernet + IPv4 + TCP z timestamps (bez FCS)', 66], ['Ethernet + IPv6 + UDP (bez FCS)', 62]];
for (const [d, v] of hdrs) add(`Ile bajtów nagłówków ma: ${d}?`, String(v), [String(v + 4), String(v - 8), String(v + 20)], 'Ethernet 14 (+4 VLAN), IPv4 20, IPv6 40, TCP 20 (32 z timestamps), UDP 8.');
const svc = [[22, 'SSH'], [53, 'DNS'], [443, 'HTTPS'], [5432, 'PostgreSQL'], [6379, 'Redis'], [9092, 'Kafka'], [3306, 'MySQL']];
const svn = ['SSH', 'DNS', 'HTTPS', 'PostgreSQL', 'Redis', 'Kafka', 'MySQL', 'SMTP'];
for (const [p, n] of svc) add(`Jaka usługa domyślnie używa portu ${p}?`, n, svn.filter((x) => x !== n).slice(0, 3), `Port ${p} — ${n}. Port w nagłówku TCP/UDP wskazuje aplikację na hoście.`);
add('Które pole w nagłówku IP pozwala odbiorcy złożyć fragmenty jednego pakietu?', 'Identification (ID) wraz z flagami i offsetem fragmentu', ['TTL', 'Protocol', 'DSCP'], 'Fragmenty mają ten sam ID; offset wskazuje ich miejsce.');
add('Co z poniższych NIE jest zadaniem warstwy transportowej TCP?', 'Wybór trasy między sieciami', ['Numerowanie bajtów i potwierdzenia', 'Kontrola przepływu', 'Identyfikacja aplikacji portami'], 'Routing to zadanie warstwy internetowej (IP).');
add('Jak nazywa się pseudo-nagłówek, który Wireshark pokazuje zamiast Ethernetu przy przechwytywaniu tcpdump -i any na Linuksie?', 'Linux cooked capture (SLL)', ['802.1Q', 'PPP', 'VXLAN'], 'Interfejs „any” nie ma jednego typu łącza, więc libpcap używa pseudo-nagłówka SLL/SLL2.');
add('W jakiej kolejności bajtów zapisywane są liczby w nagłówkach protokołów internetowych?', 'Big-endian (najstarszy bajt pierwszy)', ['Little-endian', 'Losowej', 'Zależnej od systemu operacyjnego nadawcy'], 'Kolejność sieciowa = big-endian; Java ByteBuffer domyślnie też.');
add('Co zrobi router z pakietem IPv4, gdy TTL po zmniejszeniu wynosi 0?', 'Odrzuci go i odeśle ICMP Time Exceeded', ['Przekaże go dalej z TTL 255', 'Podzieli go na fragmenty', 'Wyśle go z powrotem do nadawcy'], 'Na tym mechanizmie opiera się traceroute.');
add('Która informacja jest potrzebna, by zbudować ramkę do hosta w innej sieci?', 'Adres MAC bramy domyślnej (next hop)', ['Adres MAC hosta docelowego w innej sieci', 'Numer portu bramy', 'Adres IP przełącznika'], 'Ramka trafia do next hopa; adres IP docelowy w pakiecie pozostaje adresem odległego hosta.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
