// Generuje lekcje _ns_01_fundamenty_sieci/07_FirstPacketCapture.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_07_capture_lesson.cjs src/main/resources/content/_ns_01_fundamenty_sieci/07_FirstPacketCapture.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Pakiety nie kłamią', ...img('ns01-07-capture-points.svg', 'Schemat hosta z kontenerem klienta i kontenerem aplikacji połączonymi przez bridge Dockera, z interfejsem pętli zwrotnej lo i fizycznym interfejsem połączonym z przełącznikiem z portem SPAN. Litery A–E oznaczają punkty przechwytywania: interfejs kontenera, bridge, interfejs hosta, pętlę zwrotną i port SPAN.'),
    body:
`Logi aplikacji mówią, co aplikacja *myśli*, że się stało. Metryki mówią, *ile* czegoś się stało. **Przechwycony ruch** pokazuje, co *naprawdę* przeszło przez sieć: czy żądanie w ogóle wyszło, kto odpowiedział RST, czy TLS dostał certyfikat, który nagłówek był inny niż w kodzie.

**Wireshark** (graficzny analizator) i **tcpdump** (narzędzie wiersza poleceń) to dwa podstawowe narzędzia każdego, kto pracuje z siecią: developera diagnozującego \`Connection reset\`, administratora szukającego pętli i analityka SOC zabezpieczającego dowody incydentu. Oba korzystają z biblioteki **libpcap** (na Windows **Npcap**) i zapisują pliki **pcap/pcapng**.

Najważniejsza decyzja przy przechwytywaniu to **gdzie** to zrobić. Na ilustracji ten sam ruch wygląda inaczej w kontenerze aplikacji, na bridge’u Dockera, na interfejsie hosta (już po NAT) i na porcie lustrzanym przełącznika. Połączenia na \`127.0.0.1\` w ogóle nie pojawią się na \`eth0\`.

W tej lekcji:
- przechwycisz żądanie do Spring Boot w labie i przeczytasz je w trzech panelach Wiresharka,
- nauczysz się różnicy między **filtrem przechwytywania (BPF)** a **filtrem wyświetlania**,
- użyjesz **Follow TCP Stream** i \`tshark\` do wyciągania danych,
- poznasz zasady **etyki i bezpieczeństwa** pracy z przechwyconym ruchem — pliki pcap zawierają hasła, tokeny i dane osobowe.` },

  { type: 'DEFINITION', heading: 'Słownik przechwytywania ruchu', body:
`- **libpcap / Npcap** — biblioteki do przechwytywania pakietów z interfejsów; używają ich tcpdump, Wireshark, Zeek, Suricata, Nmap.
- **dumpcap** — właściwy proces przechwytujący Wiresharka; tylko on potrzebuje uprawnień, GUI nie powinno działać jako root.
- **pcap / pcapng** — formaty plików; pcapng przechowuje dodatkowo wiele interfejsów, komentarze i klucze TLS (DSB).
- **Tryb promiscuous** — karta przyjmuje ramki nie tylko do siebie. W sieci z przełącznikiem i tak zobaczysz głównie własny ruch, broadcast i multicast.
- **Tryb monitor** — dla Wi-Fi: przechwytywanie surowych ramek 802.11 (zarządzanie, sygnały) zamiast ruchu po zestawieniu połączenia.
- **SPAN (port mirroring) i TAP** — kopia ruchu z przełącznika na port analizatora lub urządzenie pasywnie rozgałęziające łącze; tak zasila się sensory IDS/NSM.
- **Filtr przechwytywania (BPF)** — wyrażenie Berkeley Packet Filter (\`tcp port 8080\`), oceniane w jądrze; czego nie przepuści, tego nie ma w pliku.
- **Filtr wyświetlania** — wyrażenie Wiresharka na polach dekoderów (\`http.request.method == "POST"\`); tylko ukrywa pakiety w już zapisanym pliku.
- **Snaplen** — ile bajtów z każdego pakietu zapisać (\`-s\`); mniejszy snaplen = mniejsze pliki i mniej danych wrażliwych.
- **Dissector (dekoder)** — moduł Wiresharka rozumiejący dany protokół; dekodery działają po numerach portów lub heurystycznie (\`Decode As…\` dla niestandardowych portów).
- **Strumień TCP (\`tcp.stream\`)** — numer nadany przez Wiresharka połączeniu (czwórce adresów i portów); podstawa „Follow TCP Stream”.
- **Ring buffer** — przechwytywanie do wielu plików z rotacją (\`-C\`, \`-W\`, \`-G\`), by nie zapełnić dysku.` },

  { type: 'ANALOGY', heading: 'Kamera monitoringu i przeglądanie nagrania', body:
`Przechwytywanie ruchu przypomina monitoring wizyjny w budynku:
- **Miejsce kamery** to interfejs przechwytywania. Kamera przy wejściu głównym (interfejs hosta) nie widzi, co dzieje się między pokojami na jednym piętrze (ruch między kontenerami na bridge’u). Kamera w pokoju (interfejs kontenera) widzi tylko ten pokój.
- **Ustawienie kamery przed nagraniem** to filtr BPF: jeśli skierujesz ją tylko na drzwi do serwerowni, nie odtworzysz potem, kto wszedł do kuchni. Oszczędzasz taśmę, ale tracisz kontekst.
- **Przewijanie i wyszukiwanie w nagraniu** to filtr wyświetlania: możesz szukać „osób w czerwonych kurtkach”, ile razy chcesz, i nic z nagrania nie znika.
- **Nagrania to dane osobowe** — przechowuje się je tylko tyle, ile trzeba, z kontrolą dostępu, i nie wrzuca na publiczny czat.
- **Zgoda i podstawa prawna** — kamerę możesz zawiesić we własnym budynku, ale nie w cudzym mieszkaniu. Z ruchem sieciowym jest tak samo.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Trzy panele Wiresharka', ...img('ns01-07-wireshark-panes.svg', 'Makieta Wiresharka: u góry zielony pasek filtra „http || tcp.flags.syn == 1”, niżej lista czterech pakietów (SYN, SYN-ACK, GET /users/42, odpowiedź 200), pod nią drzewo szczegółów pakietu 4 z warstwami Frame, Ethernet, IP, TCP i HTTP, a na dole panel bajtów z podświetlonym fragmentem GET /users.'),
    body:
`Okno Wiresharka ma trzy główne panele, które odpowiadają trzem poziomom szczegółowości:`,
    code:
`1. LISTA PAKIETÓW — jeden wiersz na ramkę
   No.  Time      Source       Destination  Protocol Length Info
   kolumna Info to streszczenie najwyższej rozpoznanej warstwy (np. „GET /users/42 HTTP/1.1”)
   kolory: reguły kolorowania (View → Coloring Rules); czarne tło z czerwonym tekstem = problem TCP

2. SZCZEGÓŁY (drzewo dekoderów) — warstwy od dołu do góry:
   Frame        metadane przechwycenia: czas, długość, interfejs
   Ethernet II  MAC źródłowy/docelowy, EtherType
   IPv4/IPv6    adresy, TTL, flagi, suma kontrolna
   TCP/UDP      porty, seq/ack, flagi, okno, [analiza: retransmisje, RTT, numer strumienia]
   HTTP/TLS/DNS pola protokołu aplikacyjnego
   pola w [nawiasach] wylicza Wireshark — nie ma ich w bajtach na kablu

3. BAJTY — szesnastkowo i ASCII; kliknięcie pola w drzewie podświetla jego bajty

Skróty, które oszczędzają godziny:
   prawy klik na polu → Apply as Filter / Prepare as Filter  (gotowy filtr bez znajomości nazwy pola)
   prawy klik na polu → Apply as Column                       (np. http.host jako kolumna)
   Statistics → Conversations / Endpoints / Protocol Hierarchy / IO Graphs
   Analyze → Expert Information                                (retransmisje, RST, błędy)` },

  { type: 'CODE_BASIC', heading: 'tcpdump: niezbędnik', body:
`Na serwerach zwykle nie ma GUI — przechwytujesz \`tcpdump\`, a plik analizujesz w Wiresharku na swoim komputerze. Najważniejsze opcje:`,
    code:
`tcpdump -D                                   # lista interfejsów
tcpdump -i eth0 -nn port 8080                # -nn: bez rozwiązywania nazw hostów i portów (szybciej, czytelniej)
tcpdump -i any -nn host 10.0.3.10 and tcp port 5432
tcpdump -i eth0 -nn -c 20 'tcp[tcpflags] & (tcp-syn|tcp-rst) != 0'   # tylko SYN i RST (pierwsze 20)
tcpdump -i eth0 -nn -A -s 0 port 8080        # -A: treść jako ASCII (HTTP bez TLS), -s 0: cały pakiet
tcpdump -i eth0 -nn -e arp                   # -e: pokaż adresy MAC (warstwa 2)
tcpdump -i eth0 -nn -v icmp                  # -v: więcej pól (TTL, długość, flagi)
tcpdump -i eth0 -nn -w cap.pcap port 443     # zapis do pliku (bez dekodowania na ekran)
tcpdump -r cap.pcap -nn 'tcp port 443 and host 1.2.3.4'   # odczyt z pliku z dodatkowym filtrem
tcpdump -i eth0 -w ring-%H%M.pcap -G 300 -W 12 -s 256 port 53   # rotacja co 5 min, 12 plików, 256 B z pakietu

# Przykładowa linia wyniku:
# 14:02:11.481235 IP 172.17.0.3.51334 > 172.17.0.2.8080: Flags [P.], seq 1:80, ack 1, win 502, length 79: HTTP: GET /users/42 HTTP/1.1
# Flagi: [S] SYN, [S.] SYN-ACK, [.] ACK, [P.] PSH-ACK, [F.] FIN-ACK, [R] RST` },

  { type: 'STEP_BY_STEP', heading: 'Pierwszy capture: żądanie do Spring Boot w labie', ...img('ns01-07-follow-stream.svg', 'Okno Follow TCP Stream z żądaniem GET /users/42 klienta na czerwono i odpowiedzią HTTP 200 z JSON-em na niebiesko; ramki z opisem kolorów, filtrem tcp.stream eq 3 i ostrzeżeniem, że przechwycony ruch jawny zawiera dane osobowe.'),
    body:
`Ćwiczenie z planu lekcji — porównasz żądanie na drucie z logiem aplikacji:
1. **Uruchom lab**: aplikacja Spring Boot na porcie 8080 (HTTP bez TLS, tylko w labie) i kontener klienta w tej samej sieci Dockera. Włącz w aplikacji logowanie żądań (\`logging.level.org.springframework.web=DEBUG\` lub \`CommonsRequestLoggingFilter\`).
2. **Wybierz punkt przechwytywania** — najprościej interfejs kontenera aplikacji: \`docker run --rm -it --net container:app -v $PWD:/cap nicolaka/netshoot tcpdump -i eth0 -nn -w /cap/first.pcap port 8080\`.
3. **Wygeneruj ruch**: \`docker exec client curl -s http://app:8080/users/42\`. Zatrzymaj tcpdump (Ctrl+C) — wypisze liczbę przechwyconych pakietów.
4. **Otwórz plik w Wiresharku** i wpisz filtr \`http\`. Zobaczysz żądanie i odpowiedź.
5. **Prześledź połączenie**: prawy klik → *Follow → TCP Stream*. Czerwony tekst to klient, niebieski — serwer. Wireshark ustawił filtr \`tcp.stream eq N\`; widać też handshake (SYN, SYN-ACK, ACK) i zamknięcie (FIN).
6. **Odczytaj nagłówki z bajtów**: kliknij \`Host\` w drzewie HTTP i znajdź jego bajty w panelu dolnym (\`48 6f 73 74 3a\` = „Host:”).
7. **Porównaj z logiem aplikacji**: ścieżka, nagłówki, czas. Zwróć uwagę, że aplikacja widzi adres klienta 172.17.0.3 — ten sam co w pakiecie, bo nie ma po drodze proxy.
8. **Policz czasy**: *Statistics → Conversations → TCP* (czas trwania) i kolumna *Time* z \`View → Time Display Format → Seconds Since Previous Displayed Packet\` — ile czekał klient na odpowiedź.
9. **Posprzątaj**: plik zawiera dane (w realnej aplikacji — osobowe). Usuń go po ćwiczeniu albo przechowuj w zaszyfrowanym katalogu.` },

  { type: 'NOTE', heading: 'BPF vs filtr wyświetlania — ściąga składni', ...img('ns01-07-filters.svg', 'Lejek po lewej: cały ruch na interfejsie przechodzi przez filtr BPF tcp port 8080 i trafia do pliku pcapng. Lejek po prawej: filtr wyświetlania http.response.code >= 500 tylko ukrywa pakiety w pliku. Podpis: czego nie przechwyciłeś filtrem BPF, tego już nie odzyskasz.'),
    body:
`Dwie różne składnie, które łatwo pomylić. BPF jest prosty i działa w jądrze; filtr wyświetlania zna tysiące pól protokołów.`,
    code:
`cel                                 BPF (tcpdump, dumpcap -f)            filtr wyświetlania (Wireshark, tshark -Y)
ruch z/do hosta                     host 10.0.0.5                        ip.addr == 10.0.0.5
tylko jako źródło                   src host 10.0.0.5                    ip.src == 10.0.0.5
port TCP                            tcp port 443                         tcp.port == 443
zakres portów                       portrange 8000-8999                  tcp.port in {8000..8999}
podsieć                             net 192.168.1.0/24                   ip.addr == 192.168.1.0/24
bez SSH                             not port 22                          !(tcp.port == 22)
tylko SYN                           'tcp[tcpflags] == tcp-syn'           tcp.flags.syn == 1 && tcp.flags.ack == 0
DNS                                 udp port 53                          dns
zapytania DNS o domenę              (niewygodne w BPF)                   dns.qry.name contains "javaquest"
metoda HTTP                         (niewygodne w BPF)                   http.request.method == "POST"
błędy serwera HTTP                  (niemożliwe w BPF)                   http.response.code >= 500
SNI w TLS                           (niemożliwe w BPF)                   tls.handshake.extensions_server_name == "api.example.com"
retransmisje                        (niemożliwe w BPF)                   tcp.analysis.retransmission
reset połączeń                      'tcp[tcpflags] & tcp-rst != 0'       tcp.flags.reset == 1

Uwaga: w filtrze wyświetlania „!=” przy polach występujących wielokrotnie bywa mylące —
bezpieczniej !(ip.addr == 10.0.0.5) niż ip.addr != 10.0.0.5.` },

  { type: 'CODE_PRACTICAL', heading: 'Przechwytywanie w kontenerach, Kubernetesie i tshark', body:
`W kontenerach zwykle nie ma tcpdump (obrazy distroless). Zamiast instalować narzędzia w obrazie produkcyjnym, dołącz się do przestrzeni nazw sieci kontenera:`,
    code:
`# Docker: kontener narzędziowy w przestrzeni sieci aplikacji
docker run --rm -it --net container:app -v "$PWD:/cap" nicolaka/netshoot \\
    tcpdump -i eth0 -nn -w /cap/app.pcap

# Linux: nsenter do przestrzeni sieci procesu (PID z docker inspect -f '{{.State.Pid}}' app)
sudo nsenter -t <PID> -n tcpdump -i eth0 -nn -w app.pcap

# Kubernetes: kontener efemeryczny w podzie (bez restartu)
kubectl debug -it pod/orders-7d9c --image=nicolaka/netshoot --target=orders -- \\
    tcpdump -i eth0 -nn -w /tmp/orders.pcap port 8080
kubectl cp orders-7d9c:/tmp/orders.pcap ./orders.pcap -c <nazwa-kontenera-debug>

# Windows bez instalacji: pktmon (wbudowany)
pktmon start --capture --pkt-size 0 -f C:\\cap\\trace.etl ; pktmon stop
pktmon etl2pcap C:\\cap\\trace.etl -o C:\\cap\\trace.pcapng

# tshark: Wireshark w wierszu poleceń — wyciąganie danych do analizy i skryptów
tshark -r app.pcap -Y 'http.request' -T fields -e frame.time_relative -e ip.src -e http.request.method -e http.request.uri
tshark -r app.pcap -q -z conv,tcp                 # statystyka rozmów TCP
tshark -r app.pcap -q -z http,tree                # statystyka metod i kodów HTTP
editcap -A '2026-11-13 14:00:00' -B '2026-11-13 14:05:00' big.pcap window.pcap   # wycięcie okna czasu
mergecap -w all.pcap client.pcap server.pcap      # złączenie przechwyceń z dwóch punktów
sha256sum app.pcap > app.pcap.sha256              # skrót do łańcucha dowodowego` },

  { type: 'USAGE', heading: 'Kiedy capture ratuje developera i analityka SOC', body:
`**Developer / SRE:**
- \`Connection reset\` lub \`502\` — kto wysłał RST: serwer, proxy czy firewall? Przechwytywanie po obu stronach proxy rozstrzyga spór.
- „Klient wysyła zły nagłówek” — na drucie widać dokładnie, co wysłał \`RestClient\` lub przeglądarka (przy HTTPS: w labie z kluczami sesji).
- Opóźnienia — gdzie mija czas: handshake, TLS, czekanie na pierwszy bajt, retransmisje (\`tcp.analysis.retransmission\`, kolumna \`tcp.time_delta\`).
- Integracje z niestandardowymi protokołami (Kafka, PostgreSQL, Redis) — Wireshark ma dekodery większości z nich (\`pgsql\`, \`kafka\`, \`resp\`).

**Blue team / SOC / IR:**
- **Network Security Monitoring** — sensory (Security Onion, Zeek, Suricata) przechwytują ruch z SPAN/TAP; pełny zapis pakietów (FPC) pozwala odtworzyć incydent po fakcie.
- **Analiza alertu** — alert IDS to hipoteza; pcap z okna czasowego to dowód: co zostało wysłane, czy atak się powiódł.
- **Dowody w incydencie** — pcap zabezpiecza się z sumą SHA-256, opisem miejsca i czasu przechwycenia oraz łańcuchem dowodowym (rozdział DFIR).
- **Wyciąganie artefaktów** — pliki przesłane HTTP/SMB (\`File → Export Objects\`), zapytania DNS do domen C2, certyfikaty serwerów.
- **Egzaminy** — BTL1, CyberDefenders, CySA+ PBQ: analiza pcap to standardowe zadanie praktyczne.

**Szyfrowanie**: przy HTTPS zobaczysz tylko handshake TLS (SNI, certyfikat serwera w TLS 1.2; w TLS 1.3 certyfikat jest zaszyfrowany) i rekordy *Application Data*. W labie możesz odszyfrować ruch, eksportując klucze sesji (\`SSLKEYLOGFILE\` dla curl i przeglądarek; dla JVM — agent, np. *extract-tls-secrets*) i wskazując plik w *Preferences → Protocols → TLS*. Nigdy nie rób tego na produkcji bez procedury — plik z kluczami odszyfrowuje wszystko.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy przy przechwytywaniu', body:
`- **Zły interfejs** — przechwytujesz \`eth0\`, a ruch idzie przez \`lo\`, bridge Dockera albo tunel VPN. Zacznij od \`tcpdump -D\` i \`ip route get <cel>\`.
- **Za wąski filtr BPF** — przechwytujesz tylko \`port 443\`, a problemem jest DNS albo ICMP „fragmentation needed”. W razie wątpliwości filtruj szeroko (\`host X\`), zawężaj w Wiresharku.
- **Rozwiązywanie nazw w tcpdump** — bez \`-nn\` tcpdump wysyła zapytania DNS dla każdego adresu: spowalnia przechwytywanie i generuje własny ruch w pliku.
- **Pełny dysk** — \`tcpdump -w\` bez rotacji na zajętym serwerze potrafi zapełnić partycję i położyć aplikację. Używaj \`-C/-G/-W\`, snaplen i limitu czasu.
- **Artefakty offloadingu** — „błędne” sumy kontrolne i segmenty większe niż MTU na nadawcy to efekt TSO/checksum offload, nie awaria.
- **Tryb promiscuous na przełączniku** — w sieci przełączanej nie zobaczysz cudzego ruchu (i dobrze); do tego służy SPAN/TAP.
- **Wireshark uruchomiony jako root** — dekodery to złożony kod parsujący niezaufane dane; mają historię podatności. Przechwytuje \`dumpcap\` z uprawnieniami (grupa \`wireshark\`), a GUI działa jako zwykły użytkownik. Nieznane pliki pcap otwieraj w izolowanym środowisku.
- **Udostępnianie pcap** — plik z ruchem jawnym zawiera hasła, ciasteczka, tokeny, dane osobowe. Nie wrzucaj go do zgłoszenia, na czat ani do repozytorium.
- **Różne zegary** — łącząc przechwycenia z kilku maszyn, sprawdź synchronizację NTP, inaczej kolejność zdarzeń będzie fałszywa.` },

  { type: 'CODE_WRONG', heading: 'Źle: „złap wszystko i wyślij na czat”', body:
`Typowa reakcja na pilny incydent: przechwytywanie bez filtra, rotacji i limitu, na produkcji, z uprawnieniami roota w GUI, a plik trafia do zgłoszenia w systemie ticketowym.`,
    code:
`# na serwerze produkcyjnym z aplikacją płatności
sudo tcpdump -i any -w /tmp/all.pcap           # bez filtra, bez snaplen, bez rotacji
# ... po 2 godzinach: /tmp pełne, aplikacja nie może zapisywać plików tymczasowych → awaria

sudo wireshark /tmp/all.pcap                   # GUI z dekoderami jako root
scp prod:/tmp/all.pcap . && upload do JIRA      # w pliku: tokeny sesji, dane kart w ruchu wewnętrznym bez TLS,
                                               # nagłówki Authorization — dostępne dla każdego z dostępem do zgłoszenia` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: celowany, ograniczony i zabezpieczony capture', body:
`Przechwytujemy tylko to, czego potrzebuje diagnoza, z limitami, a plik traktujemy jak dane wrażliwe: skrót, ograniczony dostęp, termin usunięcia. Zgodnie z procedurą i za zgodą właściciela systemu.`,
    code:
`# 1. Zakres: jeden kierunek ruchu, jedna usługa, ograniczony rozmiar i czas
sudo timeout 600 tcpdump -i eth0 -nn -s 512 \\
     -w /var/tmp/cap/payments-%H%M%S.pcap -G 120 -W 5 \\
     'host 10.0.3.10 and tcp port 5432'

# 2. Uprawnienia: tylko dumpcap ma capabilities, analiza jako zwykły użytkownik
sudo setcap cap_net_raw,cap_net_admin=eip /usr/bin/dumpcap   # (lub grupa wireshark z dpkg-reconfigure)

# 3. Ochrona pliku
chmod 600 /var/tmp/cap/*.pcap
sha256sum /var/tmp/cap/*.pcap > /var/tmp/cap/SHA256SUMS      # integralność (dowód)
gpg --encrypt -r soc-team /var/tmp/cap/payments-*.pcap        # przekazanie tylko zaszyfrowane

# 4. Minimalizacja przy udostępnianiu: tylko istotne pakiety, bez treści
tshark -r payments-140210.pcap -Y 'tcp.flags.reset == 1' -w resets-only.pcap
# opcjonalnie anonimizacja adresów/treści (np. TraceWrangler) przed wysłaniem do dostawcy

# 5. Usunięcie po zamknięciu zgłoszenia — zgodnie z polityką retencji` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy sięgać po capture, a kiedy po logi i metryki', body:
`- **Najpierw logi i metryki** — są tańsze, stale zbierane i nie zawierają tylu danych wrażliwych. Wystarczą przy większości problemów aplikacyjnych.
- **Capture, gdy logi się nie zgadzają lub milczą** — RST z nieznanego źródła, zawieszone połączenia, podejrzenie, że coś po drodze modyfikuje ruch (proxy, NAT, firewall, MTU).
- **Capture po obu stronach** podejrzanego elementu, gdy zespoły przerzucają się winą („to sieć” / „to aplikacja”).
- **Pełny zapis pakietów (FPC) w NSM** — w strefach o wysokiej wartości (DMZ, wyjście do Internetu), z retencją dni–tygodni; dla incydentów to bezcenne źródło.
- **Metadane zamiast pakietów** (Zeek, NetFlow) — gdy potrzebujesz długiej retencji i szerokiego pokrycia przy mniejszych kosztach i ryzyku prywatności.
- **Nigdy bez uprawnienia** — przechwytuj tylko ruch własnych systemów lub za pisemną zgodą ich właściciela i zgodnie z polityką firmy. Nieuprawnione przechwytywanie cudzej transmisji jest przestępstwem (art. 267 § 3 Kodeksu karnego), a ruch zawierający dane osobowe podlega RODO.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Capture pokazuje, co naprawdę przeszło przez sieć; tcpdump przechwytuje, Wireshark analizuje (libpcap/Npcap, pliki pcap/pcapng).
- **Miejsce przechwytywania** decyduje o wyniku: kontener, bridge, interfejs hosta (po NAT), lo, SPAN/TAP.
- **BPF** filtruje w jądrze przed zapisem (\`host\`, \`port\`, \`net\`); **filtr wyświetlania** ukrywa w zapisanym pliku (\`ip.addr\`, \`tcp.port\`, \`http.response.code\`).
- Trzy panele Wiresharka: lista, szczegóły, bajty; *Follow TCP Stream*, *Conversations*, *Expert Information*, *Export Objects*.
- \`tcpdump -nn\`, \`-w\` z rotacją (\`-G\`, \`-W\`, \`-C\`) i snaplen; w kontenerach \`--net container:\`, \`nsenter\`, \`kubectl debug\`; \`tshark\` do skryptów.
- Pliki pcap to **dane wrażliwe**: minimalizacja, ograniczony dostęp, skrót SHA-256, szyfrowanie, retencja.
- Przechwytuj wyłącznie za zgodą i we własnych systemach — etyka i prawo są częścią warsztatu obrońcy.
- Następna lekcja: **Boss — Packet Journey**: śledzisz jedno żądanie przez NAT, proxy i aplikację w trzech punktach przechwytywania.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const bpf = [
  ['cały ruch do i z hosta 10.0.3.10 poza SSH', "host 10.0.3.10 and not port 22"],
  ['tylko zapytania i odpowiedzi DNS', 'udp port 53 or tcp port 53'],
  ['połączenia do PostgreSQL w podsieci 10.0.3.0/24', 'tcp port 5432 and net 10.0.3.0/24'],
  ['tylko segmenty z flagą RST', "'tcp[tcpflags] & tcp-rst != 0'"],
  ['ruch ICMP (w tym fragmentation needed)', 'icmp or icmp6'],
  ['ruch HTTP na portach 8000–8999 z hosta 172.17.0.3', 'src host 172.17.0.3 and tcp portrange 8000-8999'],
];
for (const [goal, f] of bpf) ex(`Napisz filtr przechwytywania BPF (dla tcpdump): ${goal}.`, 'BPF używa słów host, net, port, src/dst, and/or/not.', `tcpdump -i eth0 -nn ${f}`);
const disp = [
  ['wszystkie żądania POST do ścieżek zaczynających się od /api/admin', 'http.request.method == "POST" && http.request.uri matches "^/api/admin"'],
  ['odpowiedzi HTTP z błędami serwera', 'http.response.code >= 500'],
  ['handshake TLS z SNI zawierającym „javaquest”', 'tls.handshake.type == 1 && tls.handshake.extensions_server_name contains "javaquest"'],
  ['retransmisje i zduplikowane ACK', 'tcp.analysis.retransmission || tcp.analysis.duplicate_ack'],
  ['zapytania DNS, które dostały odpowiedź NXDOMAIN', 'dns.flags.rcode == 3'],
  ['nowe połączenia TCP (pierwszy SYN) do portu 22', 'tcp.flags.syn == 1 && tcp.flags.ack == 0 && tcp.dstport == 22'],
];
for (const [goal, f] of disp) ex(`Napisz filtr wyświetlania Wiresharka: ${goal}.`, 'Użyj nazw pól dekoderów; prawy klik na polu → Prepare as Filter podpowie nazwę.', f);
ex('W labie Docker przechwyć żądanie curl do aplikacji Spring Boot na interfejsie kontenera aplikacji, otwórz plik w Wiresharku, użyj Follow TCP Stream i porównaj nagłówki z logiem aplikacji. Zapisz trzy różnice lub zgodności.', 'Postępuj według kroków z lekcji; włącz logowanie żądań w aplikacji.', 'Przykładowe obserwacje: ścieżka i metoda identyczne; curl dodał nagłówki User-Agent: curl/8.x i Accept: */*, których kod nie ustawiał; aplikacja widzi adres klienta 172.17.0.3 jak w pakiecie (brak proxy); odpowiedź ma nagłówki dodane przez Spring (Content-Type, Transfer-Encoding: chunked lub Content-Length), których nie ma w kodzie kontrolera.');
ex('Przechwyć to samo żądanie jednocześnie na interfejsie kontenera klienta i na interfejsie hosta podczas wywołania serwisu zewnętrznego (np. curl https://example.com z kontenera). Porównaj adresy IP i porty w obu plikach.', 'Ruch wychodzący z kontenera przechodzi przez NAT (masquerade) na hoście.', 'W kontenerze: źródło 172.17.0.x i port efemeryczny kontenera. Na hoście: źródło = adres hosta (po SNAT/masquerade), port może zostać zmieniony. Docelowy adres i port bez zmian. W pliku hosta widać też ruch innych procesów — potrzebny filtr. To praktyczna demonstracja enkapsulacji i NAT z poprzednich lekcji.');
ex('Użyj tshark, aby z pliku z ruchem HTTP wypisać tabelę: czas względny, adres klienta, metoda, URI i kod odpowiedzi. Jak powiązać żądanie z odpowiedzią?', 'Pola: frame.time_relative, ip.src, http.request.method, http.request.uri, http.response.code; Wireshark łączy je polem http.response_in / http.request_in.', "tshark -r app.pcap -Y 'http.request' -T fields -E header=y -e frame.number -e frame.time_relative -e ip.src -e http.request.method -e http.request.uri -e http.response_in\ntshark -r app.pcap -Y 'http.response' -T fields -e frame.number -e http.response.code -e http.request_in -e http.time\nPole http.response_in w żądaniu wskazuje numer ramki odpowiedzi; http.time to czas od żądania do odpowiedzi.");
ex('Zapisz przechwytywanie z rotacją: pliki co 10 minut, maksymalnie 6 plików, tylko pierwsze 256 bajtów każdego pakietu, tylko ruch DNS. Wyjaśnij, dlaczego taki zapis jest bezpieczniejszy dla serwera i prywatności.', 'Opcje -G (sekundy), -W (liczba plików), -s (snaplen), nazwa pliku z formatem czasu.', "tcpdump -i eth0 -nn -s 256 -G 600 -W 6 -w 'dns-%Y%m%d-%H%M.pcap' port 53\nRotacja ogranicza zajętość dysku (najwyżej 6 plików), snaplen ogranicza ilość treści (mniej danych wrażliwych, mniejsze pliki), filtr ogranicza zakres do potrzebnego protokołu.");
ex('Z otrzymanego pliku pcap (np. z ćwiczenia CyberDefenders) wyeksportuj pliki przesłane przez HTTP i policz ich skróty SHA-256. Dlaczego nie należy otwierać ich na swoim komputerze?', 'File → Export Objects → HTTP; skróty: sha256sum lub Get-FileHash.', 'Wireshark: File → Export Objects → HTTP → Save All do odizolowanego katalogu (najlepiej w VM analizy). Skróty: sha256sum * — reputację sprawdza się po skrócie (np. w VirusTotal), bez wysyłania pliku. Pliki mogą być złośliwe (malware dostarczone przez HTTP); otwiera się je tylko w odizolowanym laboratorium analizy, nigdy na stacji roboczej.');
ex('Wyjaśnij, dlaczego uruchamianie Wiresharka jako root jest złą praktyką, i skonfiguruj przechwytywanie jako zwykły użytkownik na Linuksie.', 'Rozdziel uprawnienia: dumpcap przechwytuje, GUI analizuje.', 'Wireshark zawiera setki dekoderów parsujących niezaufane dane z sieci — błąd w jednym z nich (historycznie zdarzały się podatności) przy uruchomieniu jako root daje napastnikowi pełne uprawnienia. Konfiguracja: sudo dpkg-reconfigure wireshark-common (tak), sudo usermod -aG wireshark $USER (ponowne zalogowanie) lub setcap cap_net_raw,cap_net_admin=eip /usr/bin/dumpcap. GUI uruchamiane jako zwykły użytkownik.');
ex('Przechwyć ruch HTTPS z curl w labie, a następnie odszyfruj go w Wiresharku za pomocą SSLKEYLOGFILE. Opisz, co było widoczne przed i po odszyfrowaniu.', 'export SSLKEYLOGFILE=$PWD/keys.log przed curl; w Wiresharku Preferences → Protocols → TLS → (Pre)-Master-Secret log filename.', 'Przed: handshake TLS (ClientHello z SNI, ServerHello), w TLS 1.3 reszta zaszyfrowana; rekordy Application Data bez treści. Po: Wireshark pokazuje zdekodowane HTTP/2 lub HTTP/1.1 — metodę, ścieżkę, nagłówki, odpowiedź. Plik keys.log jest równie wrażliwy jak klucz prywatny: usunąć po ćwiczeniu.');
ex('Analityk dostał alert IDS „ET POLICY curl User-Agent Outbound” z serwera aplikacji. Opisz, jak użyjesz pcap z sensora NSM, aby ocenić alert (kroki i filtry).', 'Alert to hipoteza; szukasz kontekstu przed i po.', 'Kroki: 1) wyciągnij okno czasowe wokół alertu (editcap -A/-B) dla hosta (ip.addr == serwer); 2) Follow TCP Stream — dokąd (http.host, tls SNI), co pobrano/wysłano; 3) sprawdź, czy to znane zachowanie (skrypt aktualizacji, health check) — porównaj z historią; 4) Export Objects — skróty pobranych plików; 5) dns.qry.name dla domen kontaktowanych przez host; 6) decyzja: fałszywy alarm (dokumentacja, tuning) lub incydent (izolacja hosta, eskalacja).');
ex('Dlaczego w sieci z przełącznikiem włączenie trybu promiscuous na laptopie nie pozwala zobaczyć ruchu kolegów? Jakie mechanizmy służą legalnemu monitorowaniu wielu hostów?', 'Jak przełącznik wysyła ramki unicast?', 'Przełącznik wysyła ramki unicast tylko na port docelowego MAC, więc do laptopa trafia jego własny ruch oraz broadcast/multicast (i ramki do nieznanych MAC — flooding). Legalne monitorowanie: port lustrzany SPAN/RSPAN/ERSPAN, sprzętowy TAP, przechwytywanie na hostach (agenty EDR/NSM), NetFlow/IPFIX z routerów. Próba obejścia (np. ARP spoofing) to atak, wykrywany przez DAI i IDS.');
ex('Przygotuj krótką procedurę (6–8 punktów) przechwytywania ruchu na produkcji dla swojego zespołu: zgody, zakres, ochrona danych, przechowywanie, usunięcie.', 'Pomyśl o RODO, uprawnieniach, wpływie na system i dowodach.', 'Przykład: 1) zgłoszenie z uzasadnieniem i zgoda właściciela systemu/bezpieczeństwa; 2) minimalny zakres (host, port, czas, snaplen); 3) rotacja i limit dysku; 4) przechwytywanie przez dumpcap/tcpdump z uprawnieniami, analiza bez roota; 5) skrót SHA-256 i opis (kto, gdzie, kiedy); 6) przechowywanie zaszyfrowane, dostęp tylko dla zespołu, nie w ticketach/czatach; 7) udostępnianie wyłącznie przefiltrowanych lub zanonimizowanych wycinków; 8) usunięcie po zamknięciu sprawy, wpis w rejestrze.');
ex('Na przechwyceniu połączenia aplikacji z PostgreSQL znajdź w Wiresharku zapytanie SQL i czas jego wykonania. Jaki wniosek dla bezpieczeństwa płynie z tego, że zapytanie jest czytelne?', 'Dekoder pgsql; filtr pgsql; szukaj komunikatów Parse/Bind/Query i odpowiedzi.', 'Filtr pgsql pokazuje komunikaty protokołu: Query/Parse (treść SQL), Bind (parametry), DataRow (dane wyników), CommandComplete. Czas: różnica między żądaniem a CommandComplete (kolumna czasu względnego). Wniosek: połączenie z bazą bez TLS przesyła zapytania, parametry i dane jawnie — każdy z dostępem do sieci (lub do pcap) je zobaczy. Należy włączyć sslmode=verify-full w JDBC i TLS na serwerze PostgreSQL.');
ex('Porównaj, co zobaczysz w Wiresharku dla tego samego żądania przez HTTP/1.1 bez TLS, HTTPS z TLS 1.2 i HTTPS z TLS 1.3 (bez kluczy sesji).', 'Co jest zaszyfrowane w każdej wersji?', 'HTTP/1.1: wszystko jawne — metoda, ścieżka, nagłówki, treść. TLS 1.2: jawny ClientHello (SNI), ServerHello i certyfikat serwera, potem zaszyfrowane Application Data. TLS 1.3: jawny ClientHello (SNI, chyba że ECH) i ServerHello; certyfikat już zaszyfrowany; reszta zaszyfrowana. We wszystkich wariantach widać adresy IP, porty, rozmiary i czasy pakietów.');
const tdl = [
  ['10:01:02.100 IP 10.0.1.5.48812 > 10.0.3.10.5432: Flags [S], seq 1912, win 64240, options [mss 1460,sackOK,TS val 1 ecr 0,nop,wscale 7], length 0\n10:01:02.100 IP 10.0.3.10.5432 > 10.0.1.5.48812: Flags [R.], seq 0, ack 1913, win 0, length 0', 'Klient wysłał SYN do 5432, host bazy natychmiast odpowiedział RST — host żyje, ale nic nie nasłuchuje na 5432 (lub nasłuchuje na innym adresie). W Javie: ConnectException: Connection refused.'],
  ['10:05:00.000 IP 10.0.1.5.50110 > 10.0.9.20.443: Flags [S], seq 77, win 64240, length 0\n10:05:01.012 IP 10.0.1.5.50110 > 10.0.9.20.443: Flags [S], seq 77, win 64240, length 0\n10:05:03.044 IP 10.0.1.5.50110 > 10.0.9.20.443: Flags [S], seq 77, win 64240, length 0', 'Te same SYN-y ponawiane po ~1 s i ~2 s (wykładnicze odstępy), bez żadnej odpowiedzi — pakiety giną po drodze (firewall DROP, Security Group, brak trasy powrotnej). W Javie: SocketTimeoutException: Connect timed out.'],
  ['10:07:11.300 ARP, Request who-has 192.168.1.1 tell 192.168.1.20, length 28\n10:07:12.301 ARP, Request who-has 192.168.1.1 tell 192.168.1.20, length 28\n10:07:13.302 ARP, Request who-has 192.168.1.1 tell 192.168.1.20, length 28', 'Host pyta o MAC bramy i nie dostaje odpowiedzi (brak „is-at”) — problem warstwy 2: brama wyłączona, zły VLAN na porcie, zła podsieć lub odcięty kabel po drodze.'],
];
for (const [out, ans] of tdl) ex(`Zinterpretuj fragment wyniku tcpdump i wskaż warstwę oraz prawdopodobną przyczynę:\n${out}`, 'Zwróć uwagę na flagi, odpowiedzi i odstępy czasu.', ans);
ex('W labie zatrzymaj aplikację i przechwyć próbę połączenia curl, a potem zablokuj port regułą DROP w nftables i przechwyć ponownie. Porównaj oba przechwycenia i komunikaty curl.', 'Pierwszy przypadek: RST; drugi: retransmisje SYN.', 'Zatrzymana aplikacja: SYN → RST,ACK natychmiast; curl: „Failed to connect … Connection refused” w milisekundach. DROP: SYN, ponowienia SYN co 1, 2, 4 s, brak odpowiedzi; curl: „Connection timed out” po czasie timeoutu. Wniosek: refused = ktoś odpowiedział, timeout = nikt nie odpowiedział. Reguła REJECT zachowałaby się jak refused (lub ICMP port unreachable).');
ex('Przechwyć ruch DNS z kontenera podczas curl do nazwy zewnętrznej i znajdź zapytania A/AAAA, ich odpowiedzi oraz czas odpowiedzi resolvera (dns.time).', 'Filtr dns; kolumna dns.time pokazuje czas odpowiedzi.', 'Widoczne zwykle dwa zapytania (A i AAAA) do resolvera Dockera 127.0.0.11 lub resolvera hosta; odpowiedzi z adresami i TTL; dns.time np. 0,002–0,05 s. Jeśli kontener ma search domains, mogą pojawić się dodatkowe zapytania z sufiksami (NXDOMAIN) — źródło spowolnień opisane w rozdziale DNS.');
ex('Użyj Statistics → IO Graphs, aby narysować liczbę pakietów i retransmisji na sekundę w przechwyceniu z testu z tc netem (1% strat). Jak odczytać wpływ strat na przepływność?', 'Dodaj wykres z filtrem tcp.analysis.retransmission.', 'Wykres 1: wszystkie pakiety/s; wykres 2: filtr tcp.analysis.retransmission. Przy 1% strat widać regularne retransmisje, a po nich spadki przepływności (zmniejszenie okna przeciążenia) i stopniowy wzrost — „zęby piły” typowe dla CUBIC. To praktyczna ilustracja wzoru Mathisa z poprzedniej lekcji.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111307);
const conv = [
  ['host 10.0.0.5', 'ip.addr == 10.0.0.5'], ['tcp port 443', 'tcp.port == 443'], ['src host 10.0.0.5', 'ip.src == 10.0.0.5'], ['net 192.168.1.0/24', 'ip.addr == 192.168.1.0/24'],
  ['udp port 53', 'udp.port == 53'], ['not port 22', '!(tcp.port == 22 || udp.port == 22)'], ['dst port 8080', 'tcp.dstport == 8080 || udp.dstport == 8080'], ['icmp', 'icmp'],
];
const convAll = conv.map((c) => c[1]);
for (const [b, d] of conv) add(`Który filtr wyświetlania Wiresharka odpowiada filtrowi BPF „${b}”?`, d, convAll.filter((x) => x !== d).slice(0, 3), `BPF „${b}” ≈ filtr wyświetlania „${d}”.`);
const flags = [['-nn', 'Nie rozwiązuje nazw hostów ani numerów portów'], ['-w plik.pcap', 'Zapisuje surowe pakiety do pliku zamiast je dekodować'], ['-r plik.pcap', 'Czyta pakiety z pliku'], ['-i any', 'Przechwytuje ze wszystkich interfejsów'], ['-c 50', 'Kończy po 50 pakietach'], ['-s 128', 'Zapisuje tylko pierwsze 128 bajtów każdego pakietu'], ['-A', 'Wypisuje treść pakietów jako ASCII'], ['-e', 'Pokazuje nagłówek warstwy 2 (adresy MAC)'], ['-G 300', 'Rozpoczyna nowy plik co 300 sekund'], ['-D', 'Wypisuje listę dostępnych interfejsów']];
const flagAll = flags.map((f) => f[1]);
for (const [f, d] of flags) add(`Co robi opcja tcpdump „${f}”?`, d, flagAll.filter((x) => x !== d).slice(0, 3), `${f}: ${d.toLowerCase()}.`);
const fields = [['tcp.analysis.retransmission', 'Pakiety oznaczone przez Wiresharka jako retransmisje TCP'], ['http.response.code >= 500', 'Odpowiedzi HTTP z błędem serwera'], ['dns.flags.rcode == 3', 'Odpowiedzi DNS NXDOMAIN (nazwa nie istnieje)'], ['tls.handshake.extensions_server_name', 'Nazwa serwera (SNI) w ClientHello TLS'], ['tcp.flags.reset == 1', 'Segmenty TCP z flagą RST'], ['tcp.stream eq 7', 'Wszystkie pakiety ósmego połączenia TCP (numeracja od 0)'], ['frame.time_delta_displayed > 1', 'Pakiety, przed którymi (wśród wyświetlanych) była przerwa dłuższa niż 1 s'], ['http.request.method == "POST"', 'Żądania HTTP metodą POST']];
const fieldAll = fields.map((f) => f[1]);
for (const [f, d] of fields) add(`Co wyświetli filtr „${f}”?`, d, fieldAll.filter((x) => x !== d).slice(0, 3), `${f} → ${d.toLowerCase()}.`);
const where = [
  ['Chcesz zobaczyć ruch między dwoma kontenerami w tej samej sieci Dockera.', 'Na bridge’u tej sieci Dockera (docker0/br-…) lub w przestrzeni sieci jednego z kontenerów', ['Na fizycznym interfejsie hosta', 'Na porcie SPAN przełącznika', 'Na routerze ISP']],
  ['Aplikacja łączy się z bazą na 127.0.0.1:5432.', 'Na interfejsie pętli zwrotnej lo', ['Na eth0', 'Na bridge’u Dockera', 'Na porcie SPAN']],
  ['Podejrzewasz, że reverse proxy zrywa połączenia z backendem.', 'Po obu stronach proxy: między klientem a proxy oraz między proxy a backendem', ['Tylko na kliencie', 'Tylko na backendzie', 'Na interfejsie pętli zwrotnej klienta']],
  ['Sensor IDS ma widzieć ruch wszystkich serwerów w DMZ.', 'Na porcie SPAN lub TAP-ie na łączu do DMZ', ['Na laptopie analityka w trybie promiscuous', 'Na interfejsie lo serwera', 'W kontenerze aplikacji']],
  ['Chcesz sprawdzić, jaki adres źródłowy ma ruch kontenera po wyjściu z hosta.', 'Na interfejsie hosta skierowanym do sieci (po NAT)', ['Na eth0 wewnątrz kontenera', 'Na lo', 'Na bridge’u Dockera']],
  ['Pod w Kubernetesie ma obraz distroless bez narzędzi.', 'W kontenerze efemerycznym dołączonym przez kubectl debug --target', ['Instalując tcpdump w obrazie produkcyjnym', 'Na węźle bez filtra, bo pod nie ma interfejsu', 'Nie da się przechwycić ruchu poda']],
];
for (const [d, a, w] of where) add(`Gdzie przechwycisz ruch? ${d}`, a, w, `Najlepszy punkt: ${a.toLowerCase()}.`);
add('Czym różni się filtr przechwytywania od filtra wyświetlania?', 'Filtr przechwytywania decyduje, co zostanie zapisane; filtr wyświetlania tylko ukrywa pakiety w zapisanym pliku', ['To ta sama składnia i ten sam efekt', 'Filtr wyświetlania działa w jądrze', 'Filtr przechwytywania można zmienić po zapisie'], 'Czego nie przechwycisz, tego nie odzyskasz.');
add('Jaka biblioteka przechwytuje pakiety dla tcpdump i Wiresharka na Linuksie?', 'libpcap', ['OpenSSL', 'glibc', 'Netty'], 'Na Windows jej odpowiednikiem jest Npcap.');
add('Który proces Wiresharka powinien mieć uprawnienia do przechwytywania?', 'dumpcap', ['wireshark (GUI)', 'tshark zawsze jako root', 'żaden'], 'GUI z dekoderami powinno działać jako zwykły użytkownik.');
add('Dlaczego tcpdump uruchamia się zwykle z opcją -nn?', 'Aby nie wykonywał zapytań DNS i nie tłumaczył portów na nazwy — szybciej i bez dodatkowego ruchu', ['Aby szyfrował plik', 'Aby przechwytywał tylko IPv6', 'Aby nie zapisywał nagłówków'], 'Rozwiązywanie nazw spowalnia i generuje własny ruch DNS w przechwyceniu.');
add('Co oznacza tryb promiscuous karty sieciowej?', 'Karta przyjmuje ramki zaadresowane także do innych urządzeń', ['Karta szyfruje ruch', 'Karta działa tylko w Wi-Fi', 'Karta blokuje broadcast'], 'W sieci z przełącznikiem i tak trafia do niej głównie własny ruch.');
add('Do czego służy port SPAN na przełączniku?', 'Do kopiowania ruchu z wybranych portów lub VLAN-ów na port analizatora', ['Do zwiększenia przepustowości', 'Do blokowania pętli', 'Do przydzielania adresów IP'], 'Tak zasila się sensory IDS/NSM; alternatywą jest TAP.');
add('Co pokazuje Follow TCP Stream?', 'Złożoną treść jednego połączenia TCP w obu kierunkach', ['Wszystkie połączenia hosta', 'Tylko nagłówki IP', 'Trasę pakietu przez routery'], 'Wireshark składa segmenty jednego strumienia (tcp.stream).');
add('Co zobaczysz w przechwyceniu HTTPS z TLS 1.3 bez kluczy sesji?', 'ClientHello z SNI, ServerHello, a dalej tylko zaszyfrowane rekordy Application Data', ['Pełne nagłówki HTTP', 'Hasła użytkowników', 'Certyfikat serwera w postaci jawnej'], 'W TLS 1.3 certyfikat serwera jest już zaszyfrowany; SNI ukrywa dopiero ECH.');
add('Do czego służy zmienna SSLKEYLOGFILE?', 'Do zapisania kluczy sesji TLS, którymi Wireshark może odszyfrować przechwycony ruch', ['Do wyłączenia TLS', 'Do zapisania certyfikatów CA', 'Do logowania błędów SSL w aplikacji'], 'Obsługują ją m.in. curl, Firefox, Chrome; plik jest bardzo wrażliwy.');
add('Dlaczego pliki pcap należy traktować jak dane wrażliwe?', 'Mogą zawierać hasła, tokeny, ciasteczka i dane osobowe przesłane w sieci', ['Bo są zawsze zaszyfrowane', 'Bo zawierają klucze prywatne serwera', 'Bo zajmują dużo miejsca'], 'Minimalizacja, kontrola dostępu, szyfrowanie i retencja zgodnie z RODO.');
add('Który przepis Kodeksu karnego dotyczy nieuprawnionego przechwytywania informacji?', 'Art. 267 (m.in. § 3 — urządzenie podsłuchowe/oprogramowanie do przechwytywania)', ['Art. 148', 'Art. 286', 'Art. 212'], 'Przechwytuj wyłącznie we własnych systemach lub za zgodą właściciela.');
add('Jak przechwycić ruch kontenera Dockera bez instalowania tcpdump w jego obrazie?', 'Uruchomić kontener narzędziowy z --net container:<nazwa> i w nim tcpdump', ['Restartując kontener w trybie privileged', 'Edytując obraz produkcyjny', 'Nie da się'], 'Kontener narzędziowy współdzieli przestrzeń nazw sieci z aplikacją.');
add('Co robi tshark?', 'Jest wierszowym odpowiednikiem Wiresharka z dekoderami i filtrami wyświetlania', ['Szyfruje pliki pcap', 'Skanuje porty', 'Generuje ruch testowy'], 'Przydaje się w skryptach (-T fields, -z statystyki).');
add('Do czego służy editcap -A ... -B ...?', 'Do wycięcia z pliku pakietów z zadanego przedziału czasu', ['Do łączenia plików', 'Do odszyfrowania TLS', 'Do zmiany adresów MAC'], 'mergecap łączy pliki; editcap tnie i konwertuje.');
add('Dlaczego przed udostępnieniem pcapu dostawcy warto go przefiltrować lub zanonimizować?', 'Aby przekazać tylko dane potrzebne do diagnozy, bez zbędnych danych osobowych i poufnych', ['Bo dostawca nie umie czytać dużych plików', 'Bo Wireshark nie otwiera pełnych plików', 'Bo anonimizacja przyspiesza sieć'], 'Zasada minimalizacji danych.');
add('Wireshark na nadawcy pokazuje „TCP checksum incorrect” dla wszystkich wysyłanych pakietów. Co to zwykle oznacza?', 'Sumy liczy karta sieciowa (checksum offload), a przechwycenie nastąpiło przed nią', ['Uszkodzony kabel', 'Atak modyfikujący pakiety', 'Błąd w aplikacji'], 'To artefakt przechwytywania na hoście wysyłającym.');
add('Jaki skrót kryptograficzny dołącza się do pcapu zabezpieczanego jako dowód w incydencie?', 'SHA-256 pliku z opisem kto, gdzie i kiedy przechwycił', ['MD5 adresu IP', 'Numer portu', 'Hasło do pliku w tytule'], 'Skrót pozwala wykazać, że plik nie został zmieniony (łańcuch dowodowy).');
add('Który widok Wiresharka najszybciej pokaże, które hosty wymieniły najwięcej danych?', 'Statistics → Conversations', ['View → Coloring Rules', 'Edit → Preferences', 'File → Export Specified Packets'], 'Endpoints i Protocol Hierarchy to kolejne przydatne widoki.');
add('Do czego służy Analyze → Expert Information?', 'Do przeglądu zdarzeń oznaczonych przez dekodery: retransmisji, RST, błędów, ostrzeżeń', ['Do konfiguracji interfejsów', 'Do zapisu pliku', 'Do aktualizacji dekoderów'], 'Szybki przegląd problemów w przechwyceniu.');
add('Co oznacza w wyniku tcpdump zapis „Flags [S.]”?', 'Segment SYN-ACK', ['Segment SYN', 'Segment RST', 'Segment FIN'], '[S] SYN, [S.] SYN+ACK, [.] ACK, [P.] PSH+ACK, [F.] FIN+ACK, [R] RST.');
add('Co oznacza w wyniku tcpdump zapis „Flags [R]”?', 'Reset połączenia (RST)', ['Retransmisja', 'Żądanie HTTP', 'Odpowiedź DNS'], 'RST natychmiast kończy połączenie — w Javie zobaczysz Connection refused lub Connection reset.');
add('Jak w Wiresharku odczytać ruch HTTP na niestandardowym porcie 9123?', 'Analyze → Decode As… i wybrać HTTP dla portu 9123', ['Nie da się', 'Zmienić filtr BPF', 'Przechwycić ponownie z opcją -A'], 'Dekodery dobierają się po portach; Decode As nadpisuje to przypisanie.');
add('Dlaczego przy łączeniu przechwyceń z kilku hostów ważna jest synchronizacja czasu (NTP)?', 'Bez niej kolejność zdarzeń z różnych plików będzie błędna', ['Bo bez NTP nie działa tcpdump', 'Bo pcap nie zapisuje czasu', 'Bo NTP szyfruje pliki'], 'Znaczniki czasu pochodzą z zegara hosta przechwytującego.');
add('Co jest bezpieczniejszą alternatywą dla pełnego zapisu pakietów przy długiej retencji?', 'Metadane połączeń (Zeek, NetFlow/IPFIX)', ['Zapis wszystkiego bez limitu', 'Zrzuty ekranu Wiresharka', 'Logi przełącznika STP'], 'Metadane dają szerokie pokrycie przy mniejszym koszcie i ryzyku prywatności.');
add('Co powinno poprzedzać przechwytywanie ruchu na systemie produkcyjnym w firmie?', 'Zgoda właściciela systemu i zgodność z procedurą (zakres, ochrona i retencja danych)', ['Nic, jeśli jest się administratorem', 'Wyłączenie TLS', 'Restart serwera'], 'Etyka i prawo są częścią warsztatu obrońcy.');
add('Jak w Wiresharku dodać nazwę hosta HTTP jako kolumnę listy pakietów?', 'Prawy klik na polu Host w szczegółach → Apply as Column', ['Edit → Find Packet', 'View → Zoom In', 'Statistics → IO Graph'], 'Działa dla każdego pola dekodera.');
add('Dlaczego nieznane pliki pcap (np. z Internetu) warto otwierać w odizolowanym środowisku?', 'Dekodery Wiresharka parsują niezaufane dane i miały w przeszłości podatności', ['Bo pliki pcap zawsze zawierają wirusy', 'Bo Wireshark wysyła je do chmury', 'Bo pcap uruchamia skrypty'], 'Ostrożność jak przy każdym parserze niezaufanych danych.');
const lines = [
  ['IP 10.0.0.2.41000 > 10.0.0.9.80: Flags [S]', 'Klient otwiera połączenie TCP (SYN) do portu 80'],
  ['IP 10.0.0.9.80 > 10.0.0.2.41000: Flags [R.]', 'Serwer odrzucił połączenie resetem (brak usługi lub REJECT)'],
  ['IP 10.0.0.2.41000 > 10.0.0.9.80: Flags [P.], length 120: HTTP: GET / HTTP/1.1', 'Klient wysłał dane aplikacji (żądanie HTTP) z flagami PSH i ACK'],
  ['IP 10.0.0.2.41000 > 10.0.0.9.80: Flags [F.]', 'Klient rozpoczyna zamykanie połączenia (FIN)'],
  ['IP 10.0.0.2.53211 > 10.0.0.53.53: 4127+ A? api.example.com. (33)', 'Zapytanie DNS o rekord A dla api.example.com'],
  ['IP 10.0.0.53.53 > 10.0.0.2.53211: 4127 NXDomain 0/1/0 (108)', 'Odpowiedź DNS: nazwa nie istnieje'],
  ['ARP, Reply 10.0.0.1 is-at 00:11:22:33:44:55', 'Odpowiedź ARP z adresem MAC bramy 10.0.0.1'],
  ['IP 10.0.0.1 > 10.0.0.2: ICMP time exceeded in-transit', 'Router odrzucił pakiet z wyzerowanym TTL (ślad traceroute lub pętli)'],
];
const linesAll = lines.map((l) => l[1]);
for (const [l, d] of lines) add(`Co oznacza linia wyniku tcpdump: „${l}”?`, d, linesAll.filter((x) => x !== d).slice(0, 3), d + '.');
const bgoal = [
  ['cały ruch do i z 192.168.1.50', 'host 192.168.1.50'], ['ruch HTTPS', 'tcp port 443'], ['ruch DNS po UDP', 'udp port 53'], ['ruch z podsieci 10.10.0.0/16', 'src net 10.10.0.0/16'],
  ['wszystko oprócz SSH', 'not port 22'], ['ruch ARP', 'arp'], ['ruch ICMP', 'icmp'], ['ruch do portu 8080 hosta 172.17.0.2', 'dst host 172.17.0.2 and tcp dst port 8080'],
  ['ruch VLAN 100', 'vlan 100'], ['tylko pakiety IPv6', 'ip6'],
];
const bAll = bgoal.map((g) => g[1]);
for (const [g, f] of bgoal) add(`Który filtr BPF przechwyci: ${g}?`, f, bAll.filter((x) => x !== f).slice(0, 3), `BPF: ${f}.`);
const dgoal = [
  ['żądania HTTP do hosta api.javaquest.local', 'http.host == "api.javaquest.local"'], ['odpowiedzi HTTP 404', 'http.response.code == 404'], ['zapytania DNS zawierające „javaquest”', 'dns.qry.name contains "javaquest"'],
  ['pakiety TCP z oknem zero', 'tcp.analysis.zero_window'], ['ClientHello TLS', 'tls.handshake.type == 1'], ['pakiety z TTL mniejszym niż 5', 'ip.ttl < 5'],
  ['ruch SMB', 'smb2'], ['ruch PostgreSQL', 'pgsql'], ['segmenty z flagą SYN bez ACK', 'tcp.flags.syn == 1 && tcp.flags.ack == 0'], ['pakiety większe niż 1400 bajtów', 'frame.len > 1400'],
];
const dAll = dgoal.map((g) => g[1]);
for (const [g, f] of dgoal) add(`Który filtr wyświetlania pokaże: ${g}?`, f, dAll.filter((x) => x !== f).slice(0, 3), `Filtr: ${f}.`);
const menu = [
  ['File → Export Objects → HTTP', 'Zapisanie plików przesłanych przez HTTP'], ['Statistics → Endpoints', 'Lista hostów z liczbą pakietów i bajtów'], ['Statistics → Protocol Hierarchy', 'Udział protokołów w przechwyceniu'],
  ['Statistics → IO Graphs', 'Wykres ruchu w czasie z własnymi filtrami'], ['File → Export Specified Packets', 'Zapis tylko wyświetlanych (przefiltrowanych) pakietów do nowego pliku'], ['Edit → Preferences → Protocols → TLS', 'Wskazanie pliku z kluczami sesji do odszyfrowania TLS'],
];
const mAll = menu.map((m) => m[1]);
for (const [m, d] of menu) add(`Do czego służy w Wiresharku „${m}”?`, d, mAll.filter((x) => x !== d).slice(0, 3), `${m}: ${d.toLowerCase()}.`);
const dec = [['pgsql', 'PostgreSQL'], ['kafka', 'Apache Kafka'], ['dns', 'DNS'], ['http2', 'HTTP/2'], ['tls', 'TLS'], ['arp', 'ARP'], ['icmp', 'ICMP']];
const decAll = dec.map((d) => d[1]);
for (const [k, n] of dec) add(`Ruch jakiego protokołu pokaże filtr wyświetlania „${k}”?`, n, decAll.filter((x) => x !== n).slice(0, 3), `Dekoder ${k} — ${n}.`);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
