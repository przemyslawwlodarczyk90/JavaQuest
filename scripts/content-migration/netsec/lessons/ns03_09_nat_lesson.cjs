// Generuje lekcje _ns_03_ip_i_routing/09_NatPatAndPortForwarding.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_09_nat_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/09_NatPatAndPortForwarding.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { pat, fmtFlow, steadyState, ipsNeeded, maxToOneDest, AWS_NAT_PER_DEST, FLOWS } = require('./ns03_09_nat_model.cjs');
const buildQuiz = require('./ns03_09_nat_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const fmt = (n) => n.toLocaleString('pl-PL');

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'NAT: dlaczego serwer nie widzi adresu Twojego laptopa', ...img('ns03-09-snat-dnat.svg', 'SNAT: pakiet 192.168.1.20:51000 do 93.184.216.34:443 po przejściu przez router ma źródło 203.0.113.5:51000, a odpowiedź jest tłumaczona z powrotem dzięki conntrack. DNAT: pakiet z 198.51.100.9 do 203.0.113.5:8443 jest przekierowany na 192.168.1.30:443. Masquerade to SNAT na adres interfejsu.'),
    body:
`Adresy prywatne (lekcja 06) nie są routowane w Internecie, a publicznych adresów IPv4 od dawna brakuje. Rozwiązaniem, które „uratowało” IPv4, jest **NAT (Network Address Translation)**: router podmienia adresy (i porty) w nagłówkach pakietów, zapamiętując każdą translację, żeby odpowiedzi trafiły z powrotem do właściwego hosta.

NAT jest wszędzie, gdzie pracuje Java developer:
- **router domowy i firmowy** — setki urządzeń wychodzi do Internetu z jednego adresu (PAT),
- **Docker** — kontenery wychodzą przez MASQUERADE hosta, a \`-p 8080:8080\` to **DNAT** (port forwarding),
- **Kubernetes** — Service (ClusterIP/NodePort) to DNAT robiony przez kube-proxy (iptables/IPVS) lub eBPF,
- **chmura** — NAT Gateway dla podsieci prywatnych, a „Elastic IP” to statyczny NAT 1:1,
- **operator** — CGNAT (lekcja 06), czyli NAT za NAT-em.

Skutki uboczne, które warto rozumieć: aplikacja widzi **adres NAT/proxy zamiast klienta**, liczba jednoczesnych połączeń do jednego celu jest **ograniczona liczbą portów** (wyczerpanie portów SNAT to klasyczna przyczyna sporadycznych timeoutów mikroserwisów w chmurze), połączenia przychodzące wymagają **port forwardingu**, a ruch „do własnego publicznego adresu z wnętrza” wymaga **hairpin NAT**.

W tej lekcji: SNAT, DNAT, masquerade, PAT, tablica **conntrack**, wyczerpanie portów i jak mu zapobiegać w kodzie, hairpin NAT, CGNAT, port forwarding w routerze, Dockerze i \`kubectl\`, oraz **Packet Lab z planu**: przechwyć ten sam przepływ przed i za NAT i odczytaj wpis \`conntrack -L\`. Cele: Network+ N10-009 (NAT/PAT, port forwarding), CCNA v2 (konfiguracja i weryfikacja inside source NAT: statyczny, pula, PAT), Security+ (NAT to nie firewall).` },

  { type: 'DEFINITION', heading: 'Słownik NAT', body:
`- **SNAT (source NAT)** — zmiana adresu (i ewentualnie portu) **źródłowego**; ruch wychodzący z sieci prywatnej.
- **Masquerade** — SNAT na bieżący adres interfejsu wyjściowego (dla dynamicznych adresów, np. z DHCP operatora).
- **DNAT (destination NAT)** — zmiana adresu/portu **docelowego**; ruch przychodzący do usługi wewnątrz (**port forwarding**, Docker \`-p\`, Kubernetes Service).
- **NAT statyczny (1:1)** — jeden adres prywatny ↔ jeden publiczny (np. Elastic IP); działa w obie strony.
- **NAT dynamiczny (pula)** — adresy prywatne dostają adresy z puli publicznych na czas sesji.
- **PAT / NAPT / NAT overload** — wiele adresów prywatnych → jeden (lub kilka) publiczny, rozróżnianych **numerami portów**.
- **Terminologia Cisco**: *inside local* (prywatny adres hosta), *inside global* (adres hosta widziany z zewnątrz), *outside global* (adres serwera w Internecie), *outside local* (adres serwera widziany od środka — zwykle ten sam).
- **conntrack (connection tracking)** — tablica jądra Linuksa z każdym śledzonym przepływem (5-tuple przed i po NAT, stan: NEW/ESTABLISHED, TCP: SYN_SENT/ESTABLISHED/TIME_WAIT, licznik czasu). Bez niej odpowiedzi nie dałoby się „odwrócić”.
- **Krotka (5-tuple)** — protokół, adres i port źródłowy, adres i port docelowy; identyfikuje przepływ.
- **Wyczerpanie portów SNAT** — brak wolnego portu zewnętrznego dla nowego przepływu do **tego samego celu** (adres:port); w AWS NAT Gateway limit to ${fmt(AWS_NAT_PER_DEST)} jednoczesnych połączeń do jednego celu na adres IP.
- **Hairpin NAT (NAT loopback, NAT reflection)** — obsługa ruchu z LAN do publicznego adresu własnej usługi w tym samym LAN.
- **CGNAT** — NAT operatora (100.64.0.0/10), klienci za podwójnym NAT-em.
- **Port forwarding** — reguła DNAT „publiczny port X → host:port wewnątrz”; w \`kubectl port-forward\` to coś innego: tunel przez API Kubernetesa, nie NAT.` },

  { type: 'ANALOGY', heading: 'Recepcja hotelu i numery pokoi', body:
`Hotel ma **jeden numer telefonu** (publiczny adres), a w środku setki pokoi (adresy prywatne).
- **SNAT/PAT**: gość z pokoju 214 dzwoni na zewnątrz. Recepcja łączy rozmowę ze swojej linii zewnętrznej numer 7 i zapisuje w zeszycie: „linia 7 = pokój 214, rozmowa z numerem X”. Gdy X oddzwania na linię 7, recepcja wie, do którego pokoju przełączyć. Zeszyt to **conntrack**, numer linii to **port** zewnętrzny.
- **Wyczerpanie portów**: recepcja ma 64 tysiące linii, ale jeśli wszyscy goście dzwonią **do tej samej pizzerii**, każda rozmowa musi iść inną linią — gdy linie się skończą, kolejny gość usłyszy sygnał zajętości. Rozmowy do różnych pizzerii mogą używać tych samych linii.
- **DNAT / port forwarding**: kurier dzwoni na numer hotelu z „wewnętrznym 8443” — recepcja wie, że to oznacza pokój 30, i przełącza.
- **Hairpin**: gość z pokoju 20 dzwoni na **publiczny** numer hotelu, prosząc o pokój 30. Recepcja przełącza — ale jeśli pokój 30 oddzwoni do pokoju 20 **bezpośrednio przez ścianę**, gość 20 nie rozpozna rozmówcy (czekał na telefon z recepcji). Trzeba, by odpowiedź też szła przez recepcję.
- **NAT to nie ochrona**: recepcja nie przełącza nieznanych telefonów do pokoi (efekt uboczny), ale nie sprawdza, co mówią goście, którzy sami zadzwonili na zewnątrz — od tego jest firewall.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Tablica translacji PAT i liczenie limitów', ...img('ns03-09-pat.svg', 'Tablica translacji PAT dla adresu 203.0.113.5: trzy hosty łączą się z 93.184.216.34:443; host .20 zachowuje port 51000, host .21 z tym samym portem dostaje inny port zewnętrzny, host .22 zachowuje 40000; zapytanie UDP do 1.1.1.1:53 i połączenie do innego celu mogą ponownie użyć portu 51000.'),
    body:
`Translacje policzone modelem lekcji (router stara się zachować port źródłowy; przy kolizji wybiera inny — Linux zwykle losowy z dozwolonego zakresu). Pod spodem — rachunek, który warto umieć zrobić przy projektowaniu ruchu wychodzącego z chmury.`,
    code:
`${pat(FLOWS, ['203.0.113.5']).map(fmtFlow).join('\n')}

Limit: unikalna musi być krotka (proto, adres:port po NAT, cel:port) → do JEDNEGO celu
  1 adres × ~64 512 portów (1024–65535)         = ${fmt(maxToOneDest(1, 64512))} jednoczesnych przepływów
  AWS NAT Gateway: ${fmt(AWS_NAT_PER_DEST)} połączeń / cel / adres IP (do 8 adresów → ${fmt(AWS_NAT_PER_DEST * 8)})
Zajętość = tempo nowych połączeń × czas trzymania wpisu (TIME_WAIT / timeout conntrack):
  500/s × 60 s   = ${fmt(steadyState(500, 60))} portów     → 1 adres wystarczy
  2000/s × 60 s  = ${fmt(steadyState(2000, 60))} portów    → potrzeba ${ipsNeeded(2000, 60, AWS_NAT_PER_DEST)} adresów NAT GW
  2000/s z pulą połączeń (np. 50 stałych połączeń keep-alive) → 50 portów` },

  { type: 'CODE_BASIC', heading: 'NAT w Linuksie (iptables/nftables) i na Cisco', body:
`Ten sam NAT w trzech składniach. W Linuksie NAT robi Netfilter: SNAT/MASQUERADE w łańcuchu **POSTROUTING** (po decyzji o trasie), DNAT w **PREROUTING** (przed decyzją o trasie). Wszystko opiera się na conntrack — reguła NAT dotyczy tylko **pierwszego** pakietu przepływu, kolejne są tłumaczone automatycznie.`,
    code:
`# Linux — router z wan0 (Internet) i lan0 (192.168.1.0/24)
sysctl -w net.ipv4.ip_forward=1
iptables -t nat -A POSTROUTING -s 192.168.1.0/24 -o wan0 -j MASQUERADE           # PAT na adres wan0
iptables -t nat -A POSTROUTING -s 10.0.0.0/24 -o wan0 -j SNAT --to-source 203.0.113.5-203.0.113.6   # pula
iptables -t nat -A PREROUTING -i wan0 -p tcp --dport 8443 -j DNAT --to-destination 192.168.1.30:443 # port forward
iptables -A FORWARD -i wan0 -o lan0 -p tcp -d 192.168.1.30 --dport 443 -m conntrack --ctstate NEW -j ACCEPT
iptables -A FORWARD -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT          # odpowiedzi
iptables -t nat -L -n -v                                                          # reguły i liczniki

# nftables (nowocześnie)
table ip nat {
  chain prerouting  { type nat hook prerouting priority dstnat; iifname "wan0" tcp dport 8443 dnat to 192.168.1.30:443 }
  chain postrouting { type nat hook postrouting priority srcnat; oifname "wan0" ip saddr 192.168.1.0/24 masquerade }
}

! Cisco IOS — PAT (overload) i statyczny port forwarding
interface Gi0/0
 ip nat outside
interface Gi0/1
 ip nat inside
access-list 1 permit 192.168.1.0 0.0.0.255
ip nat inside source list 1 interface Gi0/0 overload
ip nat inside source static tcp 192.168.1.30 443 interface Gi0/0 8443
show ip nat translations ; show ip nat statistics ; clear ip nat translation *` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab z planu: ten sam przepływ przed i za NAT + conntrack', body:
`Lab na trzech network namespace’ach (jak w lekcji 02/13): **inside** (192.168.1.20), **router** (lan 192.168.1.1, wan 203.0.113.5) i **outside** (serwer 203.0.113.10). Wymaga roota, \`iproute2\`, \`iptables\`, \`conntrack\` (pakiet conntrack), \`tcpdump\`, \`python3\`.
1. **Budowa**: \`ip netns add inside; ip netns add router; ip netns add outside\`; pary veth: \`ip link add i0 netns inside type veth peer name lan0 netns router\` i \`ip link add wan0 netns router type veth peer name o0 netns outside\`; adresy: inside \`192.168.1.20/24\` na i0 + \`ip route add default via 192.168.1.1\`; router \`192.168.1.1/24\` na lan0 i \`203.0.113.5/24\` na wan0; outside \`203.0.113.10/24\` na o0 (**bez** trasy do 192.168.1.0/24 — jak prawdziwy Internet). Podnieś interfejsy (\`ip -n … link set … up\`, także \`lo\`).
2. **Bez NAT**: \`ip netns exec router sysctl -w net.ipv4.ip_forward=1\`; serwer: \`ip netns exec outside python3 -m http.server 80 &\`; klient: \`ip netns exec inside curl -m 3 http://203.0.113.10/\` → **timeout**. W \`tcpdump -ni o0\` (outside) widać SYN od 192.168.1.20, ale odpowiedź nie ma trasy powrotnej.
3. **Włącz NAT**: \`ip netns exec router iptables -t nat -A POSTROUTING -o wan0 -j MASQUERADE\`. \`curl\` działa.
4. **Dwa capture’y jednocześnie**: \`ip netns exec router tcpdump -ni lan0 -w lan.pcap tcp port 80\` i \`… -ni wan0 -w wan.pcap tcp port 80\`, potem \`curl\` raz. W Wiresharku: po stronie LAN \`192.168.1.20:<port> → 203.0.113.10:80\`, po stronie WAN \`203.0.113.5:<port> → 203.0.113.10:80\`. Porównaj: adres źródłowy zmieniony, port zwykle zachowany, **suma kontrolna IP i TCP inna** (przeliczona), TTL mniejszy o 1, numery sekwencyjne bez zmian.
5. **conntrack**: w trakcie (np. \`curl --limit-rate 1k\` dużego pliku lub zaraz po) — \`ip netns exec router conntrack -L -p tcp\`:
   \`tcp 6 118 TIME_WAIT src=192.168.1.20 dst=203.0.113.10 sport=45678 dport=80 src=203.0.113.10 dst=203.0.113.5 sport=80 dport=45678 [ASSURED]\` — pierwsza krotka to **kierunek oryginalny**, druga — **oczekiwana odpowiedź** (po translacji). Liczba 118 to sekundy do wygaśnięcia.
6. **Port forwarding**: \`ip netns exec inside python3 -m http.server 8080 &\` i na routerze \`iptables -t nat -A PREROUTING -i wan0 -p tcp --dport 8443 -j DNAT --to-destination 192.168.1.20:8080\`; z outside \`curl http://203.0.113.5:8443/\` działa; \`conntrack -L\` pokazuje translację docelową.
7. **Sprzątanie**: \`ip netns del inside router outside\` (kolejno). **Notatka**: tabela „pole nagłówka — przed NAT — za NAT” dla jednego pakietu SYN.` },

  { type: 'NOTE', heading: 'Wyczerpanie portów SNAT: przyczyna i lekarstwo po stronie aplikacji', ...img('ns03-09-exhaustion.svg', 'Wykres zajętych portów SNAT w funkcji tempa nowych połączeń do jednego celu przy czasie trzymania 60 s: limit 55 000 połączeń na adres AWS NAT Gateway osiągany przy około 917 połączeniach na sekundę, dwa adresy dają 110 000. Rozwiązanie: pule połączeń i keep-alive.'),
    body:
`Każde **nowe** połączenie TCP do tego samego celu zajmuje osobny port zewnętrzny na NAT — i trzyma go jeszcze długo po zamknięciu (TIME_WAIT po stronie inicjatora, timeout wpisu conntrack / NAT GW: w AWS 350 s bezczynności). Mikroserwis, który dla każdego żądania otwiera nowe połączenie do zewnętrznego API lub bazy (np. \`new HttpClient\` przy każdym wywołaniu, brak puli JDBC, \`Connection: close\`), przy kilkuset żądaniach na sekundę **wyczerpie porty** — objaw: sporadyczne timeouty połączeń wychodzących, w AWS metryka \`ErrorPortAllocation\` NAT Gateway, w Azure „SNAT port exhaustion” (Load Balancer outbound), w Linuksie \`nf_conntrack: table full, dropping packet\` w dmesg.

Lekarstwo (od najważniejszego):
1. **Pule połączeń i keep-alive** — jeden \`HttpClient\` (lub \`RestClient\`/\`WebClient\`) na aplikację, pula JDBC (HikariCP), HTTP/2 (wiele żądań w jednym połączeniu). 2000 żądań/s może iść przez kilkadziesiąt stałych połączeń.
2. **Prywatne endpointy** zamiast NAT (VPC endpoints do S3/DynamoDB/ECR, Private Link) — ruch w ogóle nie przechodzi przez NAT.
3. **Więcej adresów NAT** (AWS NAT GW do 8 adresów, Azure NAT Gateway do 16 adresów/prefiksów) lub kilka bramek per strefa.
4. **Rozpraszanie celów** — limit dotyczy jednego celu ip:port; DNS z wieloma adresami pomaga.
5. Na własnych routerach Linux: większy \`nf_conntrack_max\`, krótsze timeouty dla UDP i TIME_WAIT (ostrożnie), szerszy zakres portów.

**NAT to nie firewall.** Brak przychodzących połączeń do hostów za PAT to efekt uboczny braku wpisu w tablicy translacji, a nie polityka bezpieczeństwa: port forwarding, UPnP, IPv6 (bez NAT), hairpin i połączenia inicjowane od środka (malware C2) całkowicie go omijają. Politykę zapewnia stanowy firewall (reguły FORWARD/INPUT, security groups).` },

  { type: 'CODE_PRACTICAL', heading: 'Java: jedna pula połączeń zamiast wyczerpania portów, i prawdziwy adres klienta', body:
`Dwa problemy NAT, które rozwiązuje się w kodzie aplikacji: (1) nie otwieraj nowego połączenia na każde żądanie — reużywaj klienta HTTP i puli JDBC; (2) za NAT/proxy adres z gniazda to adres pośrednika — prawdziwy adres klienta czytaj z nagłówków **tylko od zaufanych proxy**.`,
    code:
`// ŹLE: nowy klient (i nowe połączenia TCP → nowe porty SNAT) przy każdym wywołaniu
String callBad(String url) throws Exception {
    HttpClient c = HttpClient.newHttpClient();                 // brak współdzielenia puli
    return c.send(HttpRequest.newBuilder(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString()).body();
}

// DOBRZE: jeden klient na aplikację — połączenia keep-alive i HTTP/2 są reużywane
static final HttpClient HTTP = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(3))
        .build();
String call(String url) throws Exception {
    return HTTP.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(5)).build(),
                     HttpResponse.BodyHandlers.ofString()).body();
}
// Spring: jeden bean RestClient/WebClient; JDBC: HikariCP (maximumPoolSize dopasowany do bazy), nie DriverManager.getConnection()
// Właściwości JDK: -Djdk.httpclient.keepalive.timeout=60 (czas trzymania bezczynnych połączeń)

// Prawdziwy adres klienta za NAT/proxy (Spring Boot)
// application.properties:  server.forward-headers-strategy=native   (Tomcat RemoteIpValve, zaufane proxy: server.tomcat.remoteip.internal-proxies)
@GetMapping("/whoami")
String whoami(HttpServletRequest req) {
    return "remoteAddr=" + req.getRemoteAddr();   // po konfiguracji: adres z X-Forwarded-For od zaufanego proxy
}
// Za CGNAT/PAT różni klienci mają ten sam adres — loguj też port: req.getRemotePort() (port po stronie NAT)` },

  { type: 'USAGE', heading: 'NAT w Dockerze, Kubernetesie i chmurze; hairpin', ...img('ns03-09-hairpin.svg', 'Hairpin NAT: laptop 192.168.1.20 łączy się z publicznym adresem 203.0.113.5, router robi DNAT na serwer 192.168.1.30 w tym samym LAN, a serwer odpowiada bezpośrednio z adresu prywatnego, więc laptop odrzuca odpowiedź. Rozwiązania: dodatkowy SNAT (hairpin), split-horizon DNS, wewnętrzne endpointy w chmurze.'),
    body:
`**Docker**: ruch wychodzący kontenerów — \`MASQUERADE\` w łańcuchu \`POSTROUTING\` dla \`172.17.0.0/16 ! -o docker0\`; \`docker run -p 8080:80\` — reguła **DNAT** w łańcuchu \`DOCKER\` (plus docker-proxy w przestrzeni użytkownika dla ruchu z localhosta). Podejrzyj: \`iptables -t nat -L DOCKER -n\`. Dlatego porty Dockera omijają UFW (DNAT dzieje się przed filtrem INPUT, ruch idzie przez FORWARD) — filtruj w łańcuchu \`DOCKER-USER\`.

**Kubernetes**: Service ClusterIP to wirtualny adres bez interfejsu — kube-proxy (iptables/IPVS) lub Cilium (eBPF) robi **DNAT** na adres jednego z podów; NodePort/LoadBalancer — DNAT z portu węzła; \`externalTrafficPolicy: Cluster\` dodatkowo robi SNAT (pod widzi adres węzła, nie klienta), \`Local\` zachowuje adres klienta. **\`kubectl port-forward\`** to **nie NAT**, tylko tunel strumieniowy przez API serwer (HTTP/2/WebSocket) do poda — działa bez reguł sieciowych, ale wolno i tylko dla jednego użytkownika.

**Chmura**: NAT Gateway (prywatne podsieci → Internet, tylko wychodzące), Elastic IP (statyczny NAT 1:1 dla instancji), load balancery (SNAT/proxy — adres klienta w X-Forwarded-For lub Proxy Protocol v2).

**Hairpin**: aplikacja w biurze/VPC woła własną domenę, która rozwiązuje się na publiczny adres — bez hairpin NAT połączenie się nie uda, a przez publiczny load balancer ruch „wychodzi i wraca” (koszty transferu, opóźnienie). Najlepiej: **split-horizon DNS** (w środku domena wskazuje adres prywatny) lub wewnętrzny load balancer/Private Link.

**Blue team**: logi NAT (translacje z czasem) są potrzebne do ustalenia, który host wewnętrzny stał za adresem publicznym w incydencie — przechowuj je (syslog \`ip nat log translations\`, logi NAT GW/VPC Flow Logs z polami pkt-srcaddr). Port forwarding na routerach domowych/UPnP to częsty wektor wystawienia RDP, baz i paneli na Internet.` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Traktowanie NAT jako firewalla** — port forwarding, UPnP, IPv6 i połączenia inicjowane od środka go omijają.
- **Nowe połączenie na każde żądanie** — wyczerpanie portów SNAT i conntrack przy ruchu do jednego celu.
- **Zapominanie o regule FORWARD** przy DNAT — translacja jest, ale firewall odrzuca przekazany pakiet.
- **DNAT bez trasy powrotnej przez router** — serwer wewnętrzny odpowiada inną bramą (np. przez inne łącze) → asymetria, odpowiedzi bez translacji, klient je odrzuca.
- **Hairpin** — „z zewnątrz działa, z biura po domenie nie”.
- **Logowanie adresu z gniazda za proxy/NAT** jako adresu klienta; albo odwrotnie — ufanie \`X-Forwarded-For\` od każdego (klient sfałszuje nagłówek).
- **Protokoły z adresami w treści** (FTP aktywny, SIP, starsze VPN-y bez NAT-T) wymagają helperów ALG lub specjalnej konfiguracji (IPsec NAT-T: UDP 4500).
- **Zbyt mała tablica conntrack** na obciążonym routerze/węźle Kubernetes (\`nf_conntrack: table full\`) — spadki pakietów przy dużym ruchu (w tym przy ataku SYN flood).
- **NAT między sieciami o nakładających się zakresach bez planu** — twice NAT jest skomplikowany; lepiej uniknąć kolizji adresów (lekcja 04).` },

  { type: 'CODE_WRONG', heading: 'Źle: port forwarding „na szybko” i liczenie na NAT jako ochronę', body:
`Wystawienie bazy i panelu na świat przez przekierowanie portów, bez filtrowania źródeł, z przekonaniem, że „NAT chroni resztę sieci”.`,
    code:
`# router firmy / domowy
iptables -t nat -A PREROUTING -i wan0 -p tcp --dport 5432 -j DNAT --to 192.168.1.40:5432   # PostgreSQL dla „zdalnego developera”
iptables -t nat -A PREROUTING -i wan0 -p tcp --dport 3389 -j DNAT --to 192.168.1.50:3389   # RDP
iptables -P FORWARD ACCEPT                                                               # „bo nie działało”
# UPnP włączony — każda aplikacja w LAN może sama otworzyć port na świat
# brak logów translacji → po incydencie nie wiadomo, który host był za adresem publicznym` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: dostęp przez VPN/ZTNA, minimalny DNAT z filtrem i logowaniem', body:
`Usługi administracyjne i bazy tylko przez VPN lub bastion/ZTNA; jeśli port forwarding jest konieczny — ograniczony do konkretnych źródeł, z domyślnym DROP w FORWARD i logowaniem.`,
    code:
`iptables -P FORWARD DROP
iptables -A FORWARD -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT
# publiczna usługa HTTPS przez reverse proxy w DMZ
iptables -t nat -A PREROUTING -i wan0 -p tcp --dport 443 -j DNAT --to-destination 10.10.0.10:443
iptables -A FORWARD -i wan0 -o dmz0 -p tcp -d 10.10.0.10 --dport 443 -m conntrack --ctstate NEW -j ACCEPT
# panel partnera: tylko z jego adresów, z logowaniem
iptables -A FORWARD -i wan0 -p tcp -d 10.10.0.20 --dport 8443 -s 198.51.100.0/24 -m conntrack --ctstate NEW -j LOG --log-prefix "DNAT-partner "
iptables -A FORWARD -i wan0 -p tcp -d 10.10.0.20 --dport 8443 -s 198.51.100.0/24 -m conntrack --ctstate NEW -j ACCEPT
# bazy, RDP, SSH: wyłącznie przez VPN (WireGuard) lub bastion z MFA; UPnP wyłączony
# logi translacji: conntrackd / ulogd lub syslog z routera → SIEM (czas, krotka przed i po NAT)` },

  { type: 'WHEN_TO_USE', heading: 'Który rodzaj NAT i kiedy', body:
`Ściąga decyzji:`,
    code:
`SYTUACJA                                              ROZWIĄZANIE
LAN/podsieć prywatna → Internet                         PAT/masquerade (router, NAT Gateway)
serwer potrzebuje stałego publicznego adresu            NAT statyczny 1:1 (Elastic IP) lub publiczny adres + firewall
usługa w LAN dostępna z Internetu                       DNAT/port forwarding przez reverse proxy w DMZ (lepiej: hosting/LB)
dostęp administracyjny z zewnątrz                       VPN / ZTNA / bastion — NIE port forwarding
dużo połączeń do jednego zewnętrznego celu              pule połączeń, HTTP/2, więcej adresów NAT, prywatne endpointy
kontener → Internet / port na hoście                    Docker MASQUERADE / -p (z bindem na 127.0.0.1, jeśli lokalnie)
dostęp do poda na chwilę (debug)                        kubectl port-forward (tunel, nie NAT)
LAN → publiczny adres własnej usługi                    split-horizon DNS (lepiej) lub hairpin NAT
dwie sieci z tym samym zakresem muszą rozmawiać          twice NAT / NETMAP (tymczasowo) → docelowo renumeracja
IPv6                                                    bez NAT: globalne adresy + stanowy firewall (NPTv6 tylko w szczególnych przypadkach)` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- SNAT/masquerade zmienia źródło (wyjście, POSTROUTING), DNAT — cel (port forwarding, PREROUTING); PAT rozróżnia wiele hostów po portach.
- conntrack pamięta każdy przepływ (krotka oryginalna i odpowiedzi) — reguła NAT działa na pierwszy pakiet, reszta jest tłumaczona automatycznie; \`conntrack -L\`, \`show ip nat translations\`.
- Router przelicza sumy kontrolne IP/TCP, zmniejsza TTL; serwer widzi adres NAT, nie klienta (X-Forwarded-For tylko od zaufanych proxy).
- Wyczerpanie portów SNAT: limit na cel (AWS ${fmt(AWS_NAT_PER_DEST)}/adres); zajętość = tempo × czas trzymania; lekarstwo — pule połączeń, keep-alive, HTTP/2, prywatne endpointy.
- Hairpin NAT: ruch LAN → własny publiczny adres; split-horizon DNS jako lepsze rozwiązanie.
- Docker: MASQUERADE i DNAT (\`-p\`), filtruj w DOCKER-USER; Kubernetes Service = DNAT; \`kubectl port-forward\` = tunel przez API.
- NAT to nie firewall — polityka bezpieczeństwa należy do stanowego firewalla; loguj translacje na potrzeby incydentów.
- Następna lekcja: **ICMP, ping, traceroute i mtr** — jak czytać wyniki i dlaczego blokowanie całego ICMP psuje sieć.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [rate, hold, per] of [[300, 60, AWS_NAT_PER_DEST], [1500, 60, AWS_NAT_PER_DEST], [5000, 120, AWS_NAT_PER_DEST], [800, 240, 64512], [3000, 30, 28232]]) {
  ex(`Aplikacja otwiera ${rate} nowych połączeń na sekundę do jednego celu, wpis trzymany jest ${hold} s, na jeden adres NAT przypada ${fmt(per)} portów do tego celu. Ile portów jest zajętych w stanie ustalonym i ile adresów NAT potrzeba?`,
    'Zajętość = tempo × czas trzymania; adresy = ceil(zajętość / porty na adres).',
    `${rate} × ${hold} = ${fmt(steadyState(rate, hold))} portów → ${ipsNeeded(rate, hold, per)} adres(y). Lepsze rozwiązanie: reużywanie połączeń (pula/keep-alive) — wtedy liczba portów = liczba stałych połączeń.`);
}
const many = Array.from({ length: 5 }, (_, i) => ({ proto: 'TCP', src: `10.0.0.${i + 2}`, sport: 50000, dst: '198.51.100.7', dport: 5432 }));
ex(`Router PAT ma jeden adres 203.0.113.5 i (dla ćwiczenia) zakres portów 50000–50002. Pięć hostów 10.0.0.2–.6 łączy się z portu 50000 z 198.51.100.7:5432. Pokaż tablicę translacji.`,
  'Unikalna krotka (proto, IP:port po NAT, cel:port).', pat(many, ['203.0.113.5'], 50000, 50002).map(fmtFlow).join('; ') + '. Trzy pierwsze przepływy dostają porty 50000–50002, pozostałe nie mają wolnego portu do tego celu.');
ex(`Ten sam router (zakres 50000–50002), ale hosty łączą się z różnymi celami: 198.51.100.7:5432, 198.51.100.8:5432, 198.51.100.7:443, 198.51.100.9:5432 i 198.51.100.7:5432. Czy któryś przepływ zostanie odrzucony?`,
  'Porty można reużywać do różnych celów.', pat([
    { proto: 'TCP', src: '10.0.0.2', sport: 50000, dst: '198.51.100.7', dport: 5432 }, { proto: 'TCP', src: '10.0.0.3', sport: 50000, dst: '198.51.100.8', dport: 5432 },
    { proto: 'TCP', src: '10.0.0.4', sport: 50000, dst: '198.51.100.7', dport: 443 }, { proto: 'TCP', src: '10.0.0.5', sport: 50000, dst: '198.51.100.9', dport: 5432 },
    { proto: 'TCP', src: '10.0.0.6', sport: 50000, dst: '198.51.100.7', dport: 5432 }], ['203.0.113.5'], 50000, 50002).map(fmtFlow).join('; ') + '. Żaden — kolizja jest tylko między 1. i 5. przepływem (ten sam cel), który dostaje kolejny port.');
const more = [
  ['Wykonaj Packet Lab z planu: w labie inside/router/outside przechwyć ten sam przepływ HTTP przed NAT (lan0) i za NAT (wan0), porównaj pola nagłówków i odczytaj wpis conntrack -L.', 'Dwa tcpdumpy równolegle; conntrack -L -p tcp.', 'Po stronie LAN: src 192.168.1.20:P → dst 203.0.113.10:80, TTL 64. Po stronie WAN: src 203.0.113.5:P (port zwykle zachowany) → 203.0.113.10:80, TTL 63, inne sumy kontrolne IP i TCP, numery sekwencyjne i dane bez zmian. conntrack: tcp 6 <sekundy> ESTABLISHED/TIME_WAIT src=192.168.1.20 dst=203.0.113.10 sport=P dport=80 src=203.0.113.10 dst=203.0.113.5 sport=80 dport=P [ASSURED] — pierwsza krotka oryginalna, druga oczekiwana odpowiedź po translacji.'],
  ['Wyjaśnij, dlaczego bez NAT w kroku 2 labu curl kończy się timeoutem, choć SYN dociera do serwera.', 'Trasa powrotna.', 'Serwer outside dostaje SYN ze źródłem 192.168.1.20, ale nie ma trasy do 192.168.1.0/24 (jak prawdziwy Internet nie routuje adresów prywatnych), więc SYN-ACK nie może wrócić (lub idzie trasą domyślną donikąd). Klient nie dostaje odpowiedzi → timeout. MASQUERADE podmienia źródło na 203.0.113.5, do którego serwer ma trasę (sieć connected).'],
  ['Skonfiguruj na Cisco PAT dla 192.168.10.0/24 na interfejsie Gi0/0 i statyczne przekierowanie TCP 2222 z adresu interfejsu na 192.168.10.5:22. Jak sprawdzisz działanie?', 'ip nat inside/outside, overload, static tcp.', 'interface Gi0/1 / ip nat inside; interface Gi0/0 / ip nat outside; access-list 10 permit 192.168.10.0 0.0.0.255; ip nat inside source list 10 interface Gi0/0 overload; ip nat inside source static tcp 192.168.10.5 22 interface Gi0/0 2222. Weryfikacja: show ip nat translations (wpisy inside local/global), show ip nat statistics (trafienia, interfejsy), debug ip nat (ostrożnie). Lepiej niż SSH przez port forwarding: VPN.'],
  ['Mikroserwis w prywatnej podsieci AWS ma sporadyczne timeouty przy wywołaniach zewnętrznego API (ok. 1500 żądań/s). Metryka ErrorPortAllocation NAT Gateway rośnie. Zaproponuj diagnozę i naprawę po stronie kodu i infrastruktury.', 'Pula połączeń, keep-alive, adresy NAT.', 'Diagnoza: liczba nowych połączeń vs żądań (logi klienta HTTP, ss -tan state time-wait | wc -l w podach, metryki NAT GW ActiveConnectionCount i ErrorPortAllocation). Zapewne każde żądanie otwiera nowe połączenie. Kod: jeden współdzielony HttpClient/RestClient z pulą i keep-alive, HTTP/2, rozsądne timeouty, brak Connection: close. Infrastruktura: dodatkowe adresy na NAT GW (do 8), NAT GW per strefa, jeśli API ma prywatny endpoint — PrivateLink. Weryfikacja: spadek ErrorPortAllocation i nowych połączeń/s.'],
  ['Na węźle Kubernetes w dmesg pojawia się „nf_conntrack: table full, dropping packet”. Wyjaśnij przyczynę i działania.', 'conntrack_max, krótkie połączenia, UDP DNS.', 'Tablica conntrack (używana przez kube-proxy/iptables i NAT) osiągnęła nf_conntrack_max — nowe przepływy są odrzucane. Typowe przyczyny: bardzo dużo krótkich połączeń (brak keep-alive), ruch UDP DNS (każde zapytanie = wpis), atak SYN flood. Działania: sprawdź conntrack -C / sysctl net.netfilter.nf_conntrack_count i _max, zwiększ max (kube-proxy ustawia go wg rdzeni — parametr conntrack.maxPerCore), skróć timeouty UDP, NodeLocal DNSCache, keep-alive w aplikacjach, rozważ eBPF (Cilium) zamiast iptables.'],
  ['Opisz, jak Docker realizuje docker run -p 8080:80 i dlaczego taki port omija UFW. Jak to poprawnie ograniczyć?', 'Łańcuchy DOCKER, DOCKER-USER, FORWARD.', 'Docker dodaje w tabeli nat regułę DNAT w łańcuchu DOCKER (PREROUTING): tcp dport 8080 → <IP kontenera>:80, oraz reguły akceptacji w filtrze FORWARD (łańcuch DOCKER). Ruch do kontenera nie przechodzi przez INPUT (gdzie działa UFW), tylko przez FORWARD — więc reguły UFW go nie dotyczą. Ograniczenia: publikacja na 127.0.0.1 (-p 127.0.0.1:8080:80), reguły w łańcuchu DOCKER-USER (np. tylko z określonej sieci), brak publikacji i dostęp przez reverse proxy.'],
  ['W Kubernetes Service typu LoadBalancer aplikacja loguje adresy węzłów zamiast adresów klientów. Wyjaśnij i podaj dwa rozwiązania.', 'externalTrafficPolicy, Proxy Protocol.', 'Przy externalTrafficPolicy: Cluster kube-proxy robi SNAT (ruch może trafić do poda na innym węźle, więc odpowiedź musi wrócić przez węzeł wejściowy) — pod widzi adres węzła. Rozwiązania: externalTrafficPolicy: Local (zachowuje adres klienta, ruch tylko do podów na węźle, nierówne rozłożenie), albo load balancer z Proxy Protocol v2 / X-Forwarded-For (L7 ingress) i konfiguracja aplikacji/ingressu do odczytu prawdziwego adresu od zaufanego proxy.'],
  ['Czym różni się kubectl port-forward od NodePort i port forwardingu na routerze?', 'Tunel przez API vs DNAT.', 'kubectl port-forward otwiera lokalny port na Twoim komputerze i tuneluje strumień przez API serwer Kubernetesa (uwierzytelnienie RBAC, HTTP/2/WebSocket) do poda — nie zmienia reguł sieciowych klastra, działa tylko dla Ciebie, nadaje się do debugowania, nie do produkcji. NodePort i port forwarding na routerze to DNAT w warstwie sieciowej: ruch z dowolnego klienta do portu węzła/routera jest przekierowywany do poda/hosta, dostępny dla wszystkich, którzy dotrą do portu.'],
  ['Pracownicy w biurze nie mogą otworzyć firmowej aplikacji pod https://app.firma.pl, a z telefonu na LTE działa. Serwer stoi w tym samym biurze za NAT. Zdiagnozuj i zaproponuj dwa rozwiązania.', 'Hairpin, split DNS.', 'app.firma.pl rozwiązuje się na publiczny adres routera; router robi DNAT na serwer w LAN, serwer odpowiada bezpośrednio do pracownika (ten sam LAN) z adresu prywatnego, a klient oczekuje odpowiedzi od adresu publicznego — połączenie się nie zestawia. Diagnoza: tcpdump na serwerze (SYN z adresu pracownika, SYN-ACK bezpośrednio), dig app.firma.pl. Rozwiązania: split-horizon DNS (w LAN domena → adres prywatny) — preferowane; hairpin NAT (dodatkowy SNAT dla ruchu LAN→serwer przez adres publiczny).'],
  ['Wyjaśnij, dlaczego NAT nie jest mechanizmem bezpieczeństwa, podając cztery sposoby, w jakie ruch omija „ochronę” NAT.', 'Port forwarding, UPnP, ruch inicjowany od środka, IPv6.', '1) Port forwarding/DNAT świadomie wystawia usługi. 2) UPnP/NAT-PMP pozwala aplikacjom (i malware) samodzielnie otwierać porty. 3) Połączenia inicjowane od środka (malware, phishing, reverse shell, C2 przez HTTPS) przechodzą normalnie, a odpowiedzi są wpuszczane. 4) IPv6 zwykle bez NAT — hosty mają globalne adresy osiągalne z Internetu, jeśli firewall na to pozwala. Do tego hairpin i błędy konfiguracji. Ochronę daje stanowy firewall z domyślnym DROP i filtrowanie ruchu wychodzącego.'],
  ['Jakie dane z NAT należy przechowywać, by po incydencie ustalić, który komputer w firmie połączył się z adresem C2 o 14:03:27, i gdzie je zbierać?', 'Logi translacji z czasem.', 'Potrzebne: czas (zsynchronizowany NTP), krotka wewnętrzna (adres i port źródłowy hosta) oraz zewnętrzna (adres i port po NAT), adres i port celu, protokół, czas trwania. Źródła: logi NAT routera/firewalla (Cisco ip nat log translations syslog, firewall session logs), conntrackd/ulogd w Linuksie, logi proxy, w chmurze VPC Flow Logs z polami pkt-srcaddr/pkt-dstaddr (ruch przez NAT GW). Dopasowanie po adresie i porcie zewnętrznym oraz czasie; samo IP publiczne nie wystarczy (PAT).'],
  ['Dlaczego IPsec VPN z urządzenia za NAT wymaga NAT-T i jakiego portu używa?', 'ESP nie ma portów.', 'ESP (protokół IP 50) nie ma numerów portów, więc PAT nie potrafi rozróżnić wielu klientów i często w ogóle go nie tłumaczy; dodatkowo AH chroni nagłówek IP, więc zmiana adresu przez NAT psuje integralność. NAT Traversal (RFC 3947/3948) wykrywa NAT podczas IKE i kapsułkuje ESP w UDP 4500, które PAT obsługuje normalnie. Firewall musi przepuszczać UDP 500 (IKE) i UDP 4500.'],
  ['Zaprojektuj wyjście do Internetu dla 3 podsieci prywatnych w 3 strefach AWS tak, by awaria strefy nie odcięła pozostałych i by uniknąć opłat międzystrefowych.', 'NAT GW per AZ, route table per AZ.', 'Po jednym NAT Gateway w podsieci publicznej każdej strefy; osobna route table dla każdej podsieci prywatnej z 0.0.0.0/0 → NAT GW w tej samej strefie. Awaria strefy wyłącza tylko jej NAT; ruch nie przekracza granic stref (brak opłat cross-AZ). Do usług AWS — VPC endpoints (S3/DynamoDB gateway, interface endpoints dla ECR, STS, Secrets Manager), żeby ograniczyć ruch przez NAT i koszty przetwarzania danych. Monitorowanie ErrorPortAllocation i PacketsDropCount.'],
  ['Wyjaśnij znaczenie pól w wyniku conntrack: „udp 17 28 src=10.244.1.5 dst=10.96.0.10 sport=40213 dport=53 src=10.244.2.7 dst=10.244.1.5 sport=53 dport=40213 mark=0 use=1”.', 'Kierunek oryginalny vs odpowiedź; DNAT Service.', 'UDP (17), wpis wygaśnie za 28 s. Kierunek oryginalny: pod 10.244.1.5 wysłał zapytanie DNS do 10.96.0.10:53 (ClusterIP Service kube-dns). Oczekiwana odpowiedź: od 10.244.2.7:53 (konkretny pod CoreDNS) do 10.244.1.5:40213 — czyli kube-proxy zrobił DNAT z adresu Service na adres poda; odpowiedź zostanie przetłumaczona z powrotem tak, by pod widział ją jako przychodzącą od 10.96.0.10.'],
  ['Kiedy NAT jest uzasadniony w IPv6 i dlaczego zwykle się go nie stosuje?', 'Dużo adresów, end-to-end, NPTv6.', 'IPv6 ma wystarczająco adresów, by każdy host miał globalny adres, więc NAT nie jest potrzebny do oszczędzania adresów; zachowanie end-to-end upraszcza protokoły (VoIP, P2P, IPsec). Bezpieczeństwo zapewnia stanowy firewall (domyślnie blokujący ruch przychodzący). Wyjątki: NPTv6 (RFC 6296, bezstanowa translacja prefiksów) przy multihomingu bez własnej adresacji PI lub do ukrycia zmian prefiksu operatora; NAT64 do komunikacji z IPv4.'],
  ['Twoja aplikacja Spring Boot za reverse proxy i NAT loguje 127.0.0.1 albo adres proxy jako adres klienta. Skonfiguruj poprawne odczytywanie adresu i zabezpiecz przed fałszowaniem.', 'forward-headers-strategy, internal-proxies.', 'server.forward-headers-strategy=native (Tomcat RemoteIpValve) lub framework (ForwardedHeaderFilter); server.tomcat.remoteip.internal-proxies ustawione na adresy/zakres proxy (regex) — tylko od nich akceptowany jest X-Forwarded-For; proxy (Nginx/ALB) nadpisuje lub dopisuje nagłówek ($proxy_add_x_forwarded_for) i usuwa wartości od klienta na brzegu. Wtedy request.getRemoteAddr() zwraca adres klienta. Bez listy zaufanych proxy klient może podać dowolny X-Forwarded-For i ominąć limity/allow-listy po IP.'],
  ['Policz: biuro ma 600 pracowników za jednym adresem PAT; każdy ma średnio 40 jednoczesnych połączeń do różnych celów, ale wszyscy jednocześnie łączą się z firmowym Office 365 (jeden adres:443, po 8 połączeń). Czy grozi wyczerpanie portów?', 'Limit dotyczy jednego celu.', `Do różnych celów porty mogą się powtarzać, więc 600 × 40 = ${fmt(600 * 40)} przepływów to nie problem. Do jednego celu (ten sam adres:443): 600 × 8 = ${fmt(600 * 8)} portów — poniżej ~64 tys., więc 1 adres wystarczy, ale z zapasem tylko ~13×. Przy wzroście lub krótkich połączeniach — więcej adresów w puli PAT lub rozdzielenie ruchu (bezpośrednie wyjście dla Microsoft 365 wg zaleceń, wiele adresów docelowych w DNS).`],
  ['Wymień, które pola nagłówków IP i TCP zmienia router przy SNAT, a które pozostają bez zmian.', 'Adres, port, sumy kontrolne, TTL.', 'Zmieniane: adres źródłowy IP, (często) port źródłowy TCP/UDP, suma kontrolna nagłówka IP, suma kontrolna TCP/UDP (obejmuje pseudo-nagłówek z adresami), TTL (−1 jak przy każdym routingu). Bez zmian: adres i port docelowy, numery sekwencyjne i potwierdzeń TCP, flagi, okno, dane. Przy DNAT analogicznie zmienia się adres/port docelowy.'],
  ['Co to jest NAT statyczny 1:1 i kiedy go użyć zamiast PAT? Podaj przykład z chmury.', 'Elastic IP, serwer z publicznym adresem.', 'NAT 1:1 trwale wiąże jeden adres prywatny z jednym publicznym, w obu kierunkach (możliwe połączenia przychodzące na wszystkie porty, o ile firewall pozwala). Używany dla serwerów, które muszą mieć stały publiczny adres (allow-listy partnerów, poczta, VPN). W AWS: Elastic IP przypisany do instancji — instancja ma prywatny adres w systemie, a IGW tłumaczy go 1:1 na publiczny. PAT — dla wielu klientów wychodzących przez jeden adres.'],
  ['Napisz regułę nftables, która robi masquerade dla 10.200.0.0/16 wychodzącego przez wan0 oraz przekierowuje TCP 2222 z wan0 na 10.200.0.5:22, i dodaj filtr dopuszczający SSH tylko z 198.51.100.0/24.', 'Hook prerouting/postrouting, chain forward.', 'table ip nat { chain prerouting { type nat hook prerouting priority dstnat; iifname "wan0" tcp dport 2222 dnat to 10.200.0.5:22 } chain postrouting { type nat hook postrouting priority srcnat; oifname "wan0" ip saddr 10.200.0.0/16 masquerade } } table inet filter { chain forward { type filter hook forward priority 0; policy drop; ct state established,related accept; iifname "wan0" ip saddr 198.51.100.0/24 ip daddr 10.200.0.5 tcp dport 22 ct state new accept } }. Uwaga: w łańcuchu forward port docelowy jest już po DNAT (22).'],
  ['Dlaczego gry, VoIP i aplikacje P2P mają problemy z NAT i jak je rozwiązują (STUN, TURN, ICE)?', 'Połączenia przychodzące, typy NAT.', 'Za NAT host nie ma publicznego adresu ani otwartego portu, więc druga strona nie może zainicjować połączenia. STUN pozwala klientowi poznać swój publiczny adres i port (mapowanie NAT) i próbować „przebicia” (UDP hole punching), gdy NAT zachowuje mapowanie niezależnie od celu. Przy NAT symetrycznym (inne mapowanie dla każdego celu) i CGNAT trzeba przekaźnika TURN, przez który idzie ruch. ICE testuje kandydatów (lokalny, STUN, TURN) i wybiera działającą ścieżkę — używa tego WebRTC.'],
  ['Na routerze Linux skrócono timeout TCP TIME_WAIT w conntrack, by „oszczędzać porty”. Jakie mogą być skutki uboczne?', 'Ponowne użycie krotki, opóźnione pakiety.', 'Wpis conntrack zniknie szybciej, więc ta sama krotka (port po NAT do tego samego celu) może zostać przydzielona nowemu połączeniu, zanim do serwera dotrą opóźnione pakiety starego — możliwe odrzucenie nowego SYN przez serwer (który jeszcze trzyma stare połączenie w TIME_WAIT) lub pomieszanie segmentów. Problemy sporadyczne i trudne do zdiagnozowania. Lepiej rozwiązać przyczynę: keep-alive, pule połączeń, więcej adresów.'],
  ['Jak sprawdzić na Linuksie bieżące zużycie i limit tablicy conntrack oraz zobaczyć wpisy tylko dla danego celu?', 'sysctl, conntrack -C, -L z filtrami.', 'sysctl net.netfilter.nf_conntrack_count i net.netfilter.nf_conntrack_max (lub cat /proc/sys/net/netfilter/...); conntrack -C (liczba wpisów); conntrack -S (statystyki: drop, insert_failed); conntrack -L -p tcp --dst 198.51.100.7 --dport 5432 (wpisy do konkretnego celu); conntrack -L -p tcp --state TIME_WAIT | wc -l. Na węzłach Kubernetes także metryki node_exporter (node_nf_conntrack_entries).'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
