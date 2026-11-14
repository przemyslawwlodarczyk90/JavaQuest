// Generuje lekcje _ns_03_ip_i_routing/15_FirstHopRedundancyHsrpVrrp.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_15_fhrp_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/15_FirstHopRedundancyHsrpVrrp.json
// Kod Javy (FailoverProbe) jest w ns03_15_FailoverProbe.java.txt — przetestowany na JDK 25.
const fs = require('fs');
const path = require('path');
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { vrrpFailover, vrrpMac, vrrp6Mac, hsrp1Mac, hsrp2Mac, electActive, afterReturn } = require('./ns03_15_fhrp_model.cjs');
const buildQuiz = require('./ns03_15_fhrp_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const JAVA_PROBE = fs.readFileSync(path.join(__dirname, 'ns03_15_FailoverProbe.java.txt'), 'utf8').trim();
const pl = (x) => String(x).replace('.', ',');

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Brama domyślna nie może być pojedynczym punktem awarii', ...img('ns03-15-vip.svg', 'Porównanie: bez FHRP awaria jedynej bramy R1 odcina hosty; z VRRP lub HSRP routery R1 (master) i R2 (backup) dzielą wirtualny adres 10.0.10.1 z wirtualnym MAC 00:00:5e:00:01:0a; po awarii R2 przejmuje adres i MAC oraz wysyła gratuitous ARP.'),
    body:
`Każdy host ma jedną bramę domyślną (lekcja 07). Jeśli ten router padnie — wszystkie hosty w podsieci tracą łączność ze światem, nawet gdy tuż obok stoi sprawny drugi router. Ręczna zmiana bramy na setkach serwerów nie wchodzi w grę, a DHCP nie pomoże istniejącym dzierżawom.

Rozwiązaniem są protokoły **FHRP (First Hop Redundancy Protocols)**: dwa (lub więcej) routery współdzielą **wirtualny adres IP i wirtualny MAC**, który hosty mają ustawiony jako bramę. W danej chwili jeden router jest aktywny (**active/master**) i odpowiada na ARP o wirtualny adres; gdy przestaje wysyłać komunikaty kontrolne, zapasowy przejmuje adres, ogłasza go **gratuitous ARP** i ruch płynie dalej — bez zmian po stronie hostów.

Ta sama idea działa poza routerami: **keepalived (VRRP)** utrzymuje **wirtualny IP przed parą reverse proxy** (Nginx, HAProxy), bramą VPN czy bazą danych. Dla Java developera to bardzo praktyczne: przełączenie trwa sekundy, a klient Java zobaczy w tym czasie timeouty i zerwane połączenia — od konfiguracji timeoutów i retry w kodzie zależy, czy użytkownik zauważy awarię.

W tej lekcji: wirtualny IP i MAC, role i priorytety, **preempt**, timery i czas przełączenia (policzony z RFC 5798), **HSRP vs VRRP vs GLBP**, tracking (śledzenie łącza w górę), keepalived, ograniczenia w chmurze oraz **Failure Lab z planu**: para Nginx z keepalived — zatrzymaj węzeł główny i zmierz przerwę widzianą przez klienta Java. Cele: CCNA v2 domena 3 (cel i działanie FHRP), Network+ (redundancja, wysoka dostępność).` },

  { type: 'DEFINITION', heading: 'Słownik FHRP', body:
`- **FHRP** — rodzina protokołów redundancji pierwszego skoku: HSRP, VRRP, GLBP.
- **Wirtualny adres IP (VIP)** — adres bramy ustawiony na hostach; nie należy na stałe do żadnego routera (wyjątek: VRRP „owner”).
- **Wirtualny MAC** — MAC, którym aktywny router odpowiada na ARP o VIP: VRRP **${vrrpMac(10)}** (VRID 10; IPv6: ${vrrp6Mac(10)}), HSRPv1 **${hsrp1Mac(10)}** (grupa 10), HSRPv2 **${hsrp2Mac(10)}**, GLBP 00:07:b4:0X:XX:YY.
- **Grupa / VRID** — identyfikator instancji (HSRP group, VRRP Virtual Router ID 1–255); musi być zgodny na routerach.
- **Role**: HSRP **active / standby** (pozostałe listen), VRRP **master / backup**, GLBP **AVG** (Active Virtual Gateway) + do 4 **AVF** (Active Virtual Forwarder).
- **Priorytet** — 0–255 (domyślnie 100); wyższy wygrywa, przy remisie wyższy adres IP interfejsu. VRRP: 255 = **owner** (VIP to fizyczny adres routera).
- **Preempt** — czy router o wyższym priorytecie odbierze rolę po powrocie; HSRP domyślnie **nie**, VRRP domyślnie **tak**.
- **Timery** — HSRP hello 3 s / hold 10 s; VRRPv3 advertisement 1 s, **Master_Down_Interval = 3 × advert + skew**, skew = (256 − priorytet) × advert / 256.
- **Tracking (śledzenie)** — obniżenie priorytetu, gdy padnie łącze w górę (uplink) lub sonda (IP SLA) — inaczej router bez dostępu do świata nadal jest aktywną bramą.
- **Gratuitous ARP (GARP)** — nieproszona odpowiedź ARP ogłaszająca „VIP jest teraz pod tym MAC” (lekcja 02/03); aktualizuje tablice ARP hostów i MAC przełączników.
- **keepalived** — demon Linuksa implementujący VRRP (plus health checki i LVS); typowo wirtualny IP przed reverse proxy. Domyślnie VIP odpowiada **fizycznym MAC węzła** (wtedy GARP aktualizuje tablice ARP hostów); opcja \`use_vmac\` włącza wirtualny MAC VRRP (wtedy GARP aktualizuje głównie tablice MAC przełączników).
- **Split brain** — obie strony uważają się za mastera (np. zablokowany multicast VRRP między nimi) → konflikt adresu.` },

  { type: 'ANALOGY', heading: 'Numer alarmowy i dyżurny', body:
`Hosty znają tylko jeden numer: **112** (wirtualny adres bramy). Nie obchodzi ich, kto odbierze — ważne, żeby ktoś odebrał.
- **Dwóch dyżurnych** (routery) siedzi przy jednej centrali. Telefon dzwoni u tego, który ma dyżur (**active/master**). Co sekundę mówi przez radio „jestem na posterunku” (**hello/advertisement**).
- Gdy przestaje się odzywać przez umówiony czas (**hold / Master_Down_Interval**), zastępca przejmuje numer i ogłasza wszystkim stacjom: „112 jest teraz u mnie” (**gratuitous ARP**). Dzwoniący nic nie zmieniają.
- **Priorytet** to staż: na dyżur idzie najstarszy stażem. **Preempt** decyduje, czy wracający z urlopu starszy dyżurny od razu wyrzuca zastępcę z krzesła, czy czeka na kolejną zmianę.
- **Tracking** to sytuacja, w której dyżurny ma telefon, ale zerwało mu się połączenie z karetkami (uplink) — powinien sam oddać dyżur, zamiast przyjmować zgłoszenia, których nie przekaże.
- **GLBP** to centrala, w której kierownik (AVG) mówi każdemu dzwoniącemu inny numer wewnętrzny — połowa trafia do jednego dyżurnego, połowa do drugiego (równoważenie).
- **Split brain** — obaj dyżurni nie słyszą się nawzajem (zepsute radio) i obaj odbierają ten sam numer.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Ile trwa przełączenie — policzone z RFC 5798', ...img('ns03-15-failover.svg', 'Oś czasu VRRP: master wysyła ogłoszenia co 1 sekundę; po ich ustaniu backup czeka Master_Down_Interval równy 3 sekundy plus skew i staje się masterem, wysyłając gratuitous ARP. Przy ogłoszeniach co 100 ms przełączenie trwa ok. 361 ms. HSRP domyślnie do 10 s.'),
    body:
`VRRPv3: backup uznaje mastera za martwego po **Master_Down_Interval = 3 × Advertisement_Interval + Skew_Time**, gdzie **Skew_Time = (256 − priorytet backupu) × Advertisement_Interval / 256**. Skew sprawia, że backup z wyższym priorytetem reaguje odrobinę szybciej (gdy jest kilka backupów). Przykłady:`,
    code:
`${[[1000, 100], [1000, 200], [1000, 254], [500, 100], [100, 100], [100, 200]].map(([adv, p]) => { const f = vrrpFailover(adv, p); return `advert ${String(adv).padStart(4)} ms, priorytet backupu ${String(p).padStart(3)}:  skew ${pl(f.skew).padStart(6)} ms  →  Master_Down ${pl(f.masterDown).padStart(7)} ms`; }).join('\n')}

HSRP: active martwy po hold time (domyślnie 10 s; hello 3 s), można zejść do hello 200 ms / hold 750 ms (msec)
Przerwa widziana przez klienta ≈ czas wykrycia + przełączenie ARP (z GARP: natychmiast; bez GARP: do wygaśnięcia wpisu ARP)
                                + ponowne zestawienie połączeń, które były zakończone na starym węźle (proxy, VIP keepalived)` },

  { type: 'CODE_BASIC', heading: 'Konfiguracja HSRP i VRRP (Cisco) oraz keepalived (Linux)', ...img('ns03-15-compare.svg', 'Tabela porównawcza HSRP, VRRP i GLBP: standard, role, wirtualny MAC, multicast, timery, preempt, własność adresu i równoważenie ruchu.'),
    body:
`Ta sama brama 10.0.10.1 dla VLAN 10 w trzech wariantach. W HSRP pamiętaj o \`preempt\` (domyślnie wyłączony) i trackingu uplinku.`,
    code:
`! Cisco HSRPv2 — R1 (aktywny), R2 identycznie z priority 100
interface Vlan10
 ip address 10.0.10.2 255.255.255.0
 standby version 2
 standby 10 ip 10.0.10.1
 standby 10 priority 110
 standby 10 preempt delay minimum 30           ! po powrocie poczekaj, aż routing się ustabilizuje
 standby 10 timers msec 200 msec 750           ! szybsze wykrycie
 standby 10 track 1 decrement 20               ! track 1 = stan uplinku / IP SLA
 standby 10 authentication md5 key-string <sekret>
track 1 interface GigabitEthernet0/1 line-protocol

! Cisco VRRPv3 (IPv4 i IPv6)
fhrp version vrrp v3
interface Vlan10
 vrrp 10 address-family ipv4
  address 10.0.10.1 primary
  priority 110
  track 1 decrement 20
show standby brief ; show vrrp brief

# keepalived (Linux) — /etc/keepalived/keepalived.conf na lb1 (lb2: state BACKUP, priority 100)
vrrp_script chk_nginx {
  script "/usr/bin/curl -fs -m 1 http://127.0.0.1/healthz"
  interval 1
  fall 2
  rise 2
  weight -30                                     # nginx padł → priorytet 110 − 30 = 80 < 100 → backup przejmuje
}
vrrp_instance VI_51 {
  state MASTER
  interface eth0
  virtual_router_id 51
  priority 110
  advert_int 1
  authentication { auth_type PASS auth_pass s3kr3t }   # tylko VRRPv2; nie jest to prawdziwe zabezpieczenie
  virtual_ipaddress { 10.0.10.100/24 }
  track_script { chk_nginx }
}` },

  { type: 'STEP_BY_STEP', heading: 'Failure Lab z planu: para Nginx z keepalived — zmierz przerwę widzianą przez klienta Java', ...img('ns03-15-keepalived.svg', 'Topologia labu: klient Java wysyła żądania na wirtualny adres 10.0.10.100 utrzymywany przez keepalived na dwóch węzłach Nginx — lb1 master i lb2 backup — przed dwoma aplikacjami Spring Boot.'),
    body:
`Lab na Dockerze (Linux/WSL2) lub dwóch VM w jednej sieci L2. VRRP wymaga multicastu 224.0.0.18 i możliwości dodania adresu do interfejsu — w kontenerach potrzebne \`cap_add: [NET_ADMIN]\` i \`network_mode\`/sieć bridge, w której kontenery widzą się na L2.
1. **Sieć i węzły**: sieć \`lab\` 10.0.10.0/24; kontenery **lb1** (10.0.10.11) i **lb2** (10.0.10.12) z Nginx + keepalived (obraz np. na bazie \`nginx:1.27\` z doinstalowanym \`keepalived\`), dwie aplikacje **app1/app2** (Spring Boot lub \`hashicorp/http-echo\`), Nginx na obu lb robi \`proxy_pass\` do obu aplikacji i wystawia \`/healthz\`.
2. **keepalived**: konfiguracja z bloku obok (lb1 MASTER 110, lb2 BACKUP 100, VIP 10.0.10.100, \`advert_int 1\`, \`chk_nginx\`). Sprawdź: \`docker exec lb1 ip -br addr\` → VIP na eth0 lb1; \`docker exec lb1 tcpdump -ni eth0 vrrp -c 3\` → ogłoszenia co 1 s od lb1 (VRID 51, prio 110).
3. **Sonda Java**: uruchom \`FailoverProbe\` (kod obok) z kontenera klienta w tej samej sieci: \`java FailoverProbe.java http://10.0.10.100/ 60\` — żądanie co 100 ms, timeout 800 ms.
4. **Awaria węzła**: \`docker stop lb1\` (albo \`docker exec lb1 pkill keepalived\`). Sonda loguje \`DOWN\`, a po przełączeniu \`UP po przerwie … ms\`. Oczekiwanie: ~3,6 s (Master_Down) + do 100 ms (okres sondy) + nowe połączenie TCP. W lb2: \`ip -br addr\` → VIP; \`tcpdump arp\` → gratuitous ARP od lb2.
5. **Awaria usługy, nie węzła**: przywróć lb1 (\`docker start lb1\` — preempt: VIP wraca do lb1, sonda zobaczy drugą, krótszą przerwę!), potem \`docker exec lb1 nginx -s stop\`. Bez \`track_script\` VIP zostałby na lb1 z martwym Nginx (przerwa „na zawsze”); z \`chk_nginx\` priorytet spada do 80 i lb2 przejmuje VIP.
6. **Zmniejsz przerwę**: \`advert_int 0.1\` (keepalived obsługuje ułamki z VRRPv3) → przełączenie ~0,36 s; w kliencie: retry idempotentnych żądań, krótsze timeouty, pula połączeń z walidacją.
7. **Wnioski do notatki**: tabela „scenariusz → zmierzona przerwa”: stop węzła, stop Nginx bez/z trackingiem, powrót z preempt i bez (\`nopreempt\` w keepalived), advert 1 s vs 0,1 s.` },

  { type: 'NOTE', heading: 'Preempt, tracking i ograniczenia w chmurze', body:
`**Preempt — tak czy nie?** Z preempt router o wyższym priorytecie odbiera rolę po powrocie — to druga, niepotrzebna przerwa (a czasem powrót do węzła, który jeszcze nie ma tablic routingu czy rozgrzanego cache). Dobre praktyki: preempt z opóźnieniem (\`preempt delay minimum 30\`), albo bez preempt i ręczny powrót w oknie serwisowym. W keepalived: \`nopreempt\` (wymaga \`state BACKUP\` na obu węzłach).

**Tracking jest obowiązkowy.** FHRP sprawdza tylko, czy router żyje w LAN. Jeśli aktywnemu padnie łącze do Internetu/rdzenia, nadal przyjmuje ruch hostów i… nie ma gdzie go wysłać (lub wysyła dłuższą drogą przez drugi router). Tracking interfejsu lub sondy IP SLA obniża priorytet poniżej zapasowego. W keepalived: \`track_interface\`, \`track_script\`.

**Bezpieczeństwo:** komunikaty FHRP są multicastem w LAN — atakujący z wyższym priorytetem (255) może przejąć bramę (MITM). Obrona: uwierzytelnianie (HSRP MD5, w VRRPv3 brak — polegaj na izolacji), ACL blokujące HSRP (UDP 1985/2029) i VRRP (protokół 112) z portów użytkowników, port security i monitoring zmian ról (syslog \`%HSRP-5-STATECHANGE\`).

**Chmura:** w VPC AWS/Azure/GCP zwykle **nie ma multicastu ani GARP**, a adresy przypisuje platforma — VRRP/keepalived w klasycznej formie nie działa. Rozwiązania: zarządzany load balancer (ALB/NLB, Azure LB), przenoszenie Elastic IP/secondary IP przez API przy failoverze (skrypt notify w keepalived), lub w on-prem/Kubernetes — **MetalLB/kube-vip** (tryb L2 z ARP/ND lub BGP). Kubernetes Service i Ingress Controller z kilkoma replikami pełnią rolę „wirtualnej bramy” dla aplikacji.` },

  { type: 'CODE_PRACTICAL', heading: 'Sonda failover w Javie (przetestowana)', body:
`Program do labu: wysyła żądanie co 100 ms z krótkim timeoutem i loguje każdą zmianę stanu oraz długość przerwy. Bez argumentów uruchamia samodzielny test — wbudowany serwer HTTP znika na 2 s i wraca (zmierzona przerwa ok. 2000 ms). Z argumentami (\`URL sekundy\`) — sonduje Twój VIP w labie. Ten sam wzorzec przyda się do testów odporności (chaos testing) własnych usług.`,
    code: JAVA_PROBE },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- **Brama serwerowni / VLAN-u serwerów**: Twoje serwery aplikacyjne mają bramę na parze przełączników L3 z HSRP/VRRP — przełączenie trwa sekundy; aplikacja powinna to przetrwać (timeouty, retry, pule połączeń z walidacją).
- **Wirtualny IP przed reverse proxy / bazą**: keepalived + HAProxy/Nginx, PostgreSQL z Patroni i VIP (vip-manager), bramy VPN. Połączenia zakończone na starym węźle są zrywane — klient musi się połączyć ponownie.
- **Kubernetes on-prem**: kube-vip i MetalLB (tryb L2) robią to samo dla adresu API serwera i Service typu LoadBalancer — oparte na ARP/GARP i wyborze lidera.
- **Projekt aplikacji**: idempotentne operacje (bezpieczny retry), circuit breaker, health checki, które naprawdę sprawdzają zależności (a nie tylko „proces żyje”) — keepalived \`track_script\` powinien sprawdzać to samo.

**Blue team:**
- **Przejęcie bramy** przez fałszywe komunikaty HSRP/VRRP z wyższym priorytetem — klasyczny atak MITM w LAN (narzędzia typu Yersinia). Obrona: ACL na portach dostępowych (protokół 112, UDP 1985/2029), uwierzytelnianie HSRP, segmentacja, monitoring zmian ról.
- **Monitoring**: zdarzenia zmiany stanu (\`%HSRP-5-STATECHANGE\`, \`%VRRP-6-STATECHANGE\`, logi keepalived „Entering MASTER STATE”), nieoczekiwane źródła ogłoszeń w tcpdump/Zeek, alarm na częste przełączenia (flapping).
- **Split brain** w keepalived (blokada multicastu przez firewall hosta) — dwa węzły z tym samym VIP; wykrywanie: duplikat IP (lekcja 02/13), alerty z obu węzłów „MASTER”.
- Mapowanie: CCNA v2 (FHRP), Network+ (wysoka dostępność), Security+ (ataki L2, redundancja).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **Brak trackingu uplinku** — aktywny router bez dostępu do świata dalej „trzyma” bramę.
- **HSRP bez \`preempt\`** — po naprawie router główny nie wraca do roli (czasem celowo, ale zwykle przez pomyłkę).
- **Preempt bez opóźnienia** — powrót do węzła, zanim zbudował trasy/sąsiedztwa → druga przerwa lub czarna dziura.
- **Niezgodny numer grupy/VRID lub wersji** (HSRPv1 vs v2) — obie strony zostają aktywne (split brain, duplikat IP).
- **Zablokowany multicast** (firewall hosta z keepalived, IGMP snooping bez querier, sieć chmurowa) — split brain.
- **Ten sam VRID w tym samym segmencie dla różnych par** — konflikt wirtualnego MAC.
- **Oczekiwanie, że przełączenie zachowa połączenia** — stan TCP i TLS był na starym węźle proxy; klient musi się połączyć ponownie.
- **Długie timeouty w kliencie** — przerwa 3 s zamienia się w 30–60 s zawieszonych wątków.
- **Health check sprawdzający tylko proces** — Nginx działa, ale nie ma dostępu do aplikacji; VIP zostaje na „zombie”.
- **VRRP w chmurze** bez przenoszenia adresu przez API — po przełączeniu ruch i tak trafia do starej instancji.` },

  { type: 'CODE_WRONG', heading: 'Źle: keepalived bez health checku, klient bez timeoutów', body:
`Konfiguracja, w której awaria Nginx (nie maszyny) powoduje trwałą niedostępność, a klient wisi przy każdym przełączeniu.`,
    code:
`vrrp_instance VI_51 {
  state MASTER
  interface eth0
  virtual_router_id 51
  priority 110
  advert_int 1
  virtual_ipaddress { 10.0.10.100/24 }
  # brak track_script — nginx padł, VIP zostaje na lb1
}
# na lb2 ten sam virtual_router_id 51, ale ufw blokuje protokół 112 → oba węzły MASTER (split brain)

HttpClient http = HttpClient.newHttpClient();                 // brak connectTimeout
http.send(HttpRequest.newBuilder(URI.create("http://10.0.10.100/order")).POST(body).build(), ofString());
// brak timeoutu żądania, brak retry, nieidempotentny POST bez klucza idempotencji` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: tracking, przepuszczony VRRP, bezpieczny retry', body:
`Health check sprawdza realną usługę, firewall przepuszcza VRRP między węzłami, a klient ma krótkie timeouty i ponawia tylko operacje idempotentne (lub z kluczem idempotencji).`,
    code:
`vrrp_script chk_nginx { script "/usr/bin/curl -fs -m 1 http://127.0.0.1/healthz"  interval 1  fall 2  rise 2  weight -30 }
vrrp_instance VI_51 {
  state BACKUP                       # oba BACKUP + nopreempt → brak „drugiej przerwy” przy powrocie
  nopreempt
  interface eth0
  virtual_router_id 51
  priority 110
  advert_int 1
  virtual_ipaddress { 10.0.10.100/24 }
  track_script { chk_nginx }
  notify_master "/usr/local/bin/alert.sh MASTER"
}
# ufw allow in on eth0 from 10.0.10.12 proto vrrp     (VRRP = protokół IP 112)

HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(500)).build();
HttpRequest req = HttpRequest.newBuilder(URI.create("http://10.0.10.100/order")).timeout(Duration.ofSeconds(2))
        .header("Idempotency-Key", orderId).POST(body).build();
// retry z backoffem (np. Resilience4j Retry: 3 próby, 200 ms → 400 ms → 800 ms) — serwer deduplikuje po kluczu` },

  { type: 'WHEN_TO_USE', heading: 'Który mechanizm wybrać', body:
`Ściąga decyzji:`,
    code:
`SYTUACJA                                              WYBÓR
dwa routery Cisco jako brama VLAN-u                  HSRPv2 (lub VRRPv3 przy mieszanym sprzęcie)
sprzęt różnych producentów                            VRRPv3 (standard IETF, IPv4 i IPv6)
równoważenie ruchu hostów między bramami              GLBP (Cisco) albo kilka grup HSRP/VRRP z różnymi bramami
para reverse proxy / brama VPN na Linuksie            keepalived (VRRP) + track_script
klaster PostgreSQL z wirtualnym adresem               Patroni + vip-manager (lub keepalived)
Kubernetes on-prem: adres API / Service LB            kube-vip lub MetalLB (L2 lub BGP)
chmura publiczna                                      zarządzany load balancer; przenoszenie EIP/secondary IP przez API
szybkie wykrycie awarii                               krótkie timery (ms) lub BFD + tracking
uniknięcie drugiej przerwy po naprawie                preempt z opóźnieniem albo nopreempt` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- FHRP: routery współdzielą wirtualny IP i MAC bramy; aktywny odpowiada na ARP, po awarii zapasowy przejmuje adres i wysyła gratuitous ARP — hosty nic nie zmieniają.
- HSRP (Cisco, active/standby, hello 3/hold 10, preempt domyślnie wyłączony), VRRP (IETF RFC 5798, master/backup, advert 1 s, preempt włączony, owner 255), GLBP (Cisco, równoważenie ruchu).
- Wirtualne MAC: VRRP 00:00:5e:00:01:VRID, HSRPv1 00:00:0c:07:ac:GG, HSRPv2 00:00:0c:9f:fG:GG.
- Czas przełączenia VRRPv3: 3 × advert + skew (≈ 3,6 s przy 1 s; ≈ 0,36 s przy 100 ms); przerwa klienta = wykrycie + ARP + ponowne połączenia.
- Tracking uplinku/usługi jest obowiązkowy; preempt z opóźnieniem lub nopreempt; ochrona przed fałszywymi ogłoszeniami (ACL, uwierzytelnianie, monitoring).
- keepalived przed reverse proxy; w chmurze — LB zarządzany lub przenoszenie adresu przez API; w Kubernetes — kube-vip/MetalLB.
- Klient Java: krótkie timeouty, retry idempotentnych operacji, pule z walidacją — wtedy przełączenie bramy jest niewidoczne dla użytkownika.
- Następna lekcja: **routing dynamiczny i BGP** — jak działa Internet.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [adv, p] of [[1000, 100], [1000, 200], [1000, 254], [100, 100], [500, 150], [200, 50]]) {
  const f = vrrpFailover(adv, p);
  ex(`VRRPv3: Advertisement_Interval ${adv} ms, priorytet backupu ${p}. Policz Skew_Time i Master_Down_Interval.`, 'Skew = (256 − prio) × adv / 256; Master_Down = 3 × adv + skew.', `Skew = (256 − ${p}) × ${adv} / 256 = ${pl(f.skew)} ms; Master_Down = 3 × ${adv} + ${pl(f.skew)} = ${pl(f.masterDown)} ms.`);
}
for (const [kind, n] of [['VRRP', 10], ['VRRP', 200], ['HSRPv1', 10], ['HSRPv1', 255], ['HSRPv2', 10], ['HSRPv2', 1000]]) {
  const mac = kind === 'VRRP' ? vrrpMac(n) : kind === 'HSRPv1' ? hsrp1Mac(n) : hsrp2Mac(n);
  ex(`Jaki wirtualny adres MAC użyje ${kind} dla ${kind === 'VRRP' ? 'VRID' : 'grupy'} ${n}?`, kind === 'VRRP' ? '00:00:5e:00:01 + VRID hex.' : kind === 'HSRPv1' ? '00:00:0c:07:ac + grupa hex.' : '00:00:0c:9f:f + grupa (3 cyfry hex).', `${mac}.`);
}
for (const [rs, label] of [
  [[{ name: 'R1', prio: 100, ip: '10.0.10.2' }, { name: 'R2', prio: 100, ip: '10.0.10.3' }], 'oba z domyślnym priorytetem'],
  [[{ name: 'R1', prio: 110, ip: '10.0.10.2' }, { name: 'R2', prio: 100, ip: '10.0.10.3' }], 'R1 z priorytetem 110'],
  [[{ name: 'R1', prio: 110, ip: '10.0.10.2', up: false }, { name: 'R2', prio: 100, ip: '10.0.10.3' }, { name: 'R3', prio: 100, ip: '10.0.10.4' }], 'R1 (110) uległ awarii, R2 i R3 po 100'],
]) {
  ex(`Grupa FHRP: ${rs.map((r) => `${r.name} prio ${r.prio} adres ${r.ip}${r.up === false ? ' (awaria)' : ''}`).join(', ')} — ${label}. Który router będzie aktywny?`, 'Najwyższy priorytet, przy remisie najwyższy adres IP.', `${electActive(rs)}.`);
}
ex('R1 (priorytet 110) uległ awarii, rolę przejął R2 (100). Co stanie się po powrocie R1 przy HSRP z domyślną konfiguracją, a co przy VRRP z domyślną? Jakie są skutki dla klientów?', 'Preempt domyślnie: HSRP wyłączony, VRRP włączony.',
  `HSRP (bez preempt): aktywny zostaje ${afterReturn({ name: 'R2', prio: 100, ip: '10.0.10.3' }, { name: 'R1', prio: 110, ip: '10.0.10.2' }, false)} — brak drugiej przerwy, ale ruch idzie „zapasowym” routerem, dopóki ktoś nie przełączy ręcznie. VRRP (preempt): rolę odbiera ${afterReturn({ name: 'R2', prio: 100, ip: '10.0.10.3' }, { name: 'R1', prio: 110, ip: '10.0.10.2' }, true)} — klienci widzą drugą, krótką przerwę; jeśli R1 nie zdążył zbudować tras, może powstać czarna dziura — stąd preempt z opóźnieniem.`);
const more = [
  ['Wykonaj Failure Lab z planu: para Nginx z keepalived, sonda Java na VIP; zatrzymaj węzeł główny, potem sam Nginx (z trackingiem i bez), przywróć węzeł z preempt i bez. Zapisz zmierzone przerwy.', 'FailoverProbe, docker stop, track_script, nopreempt.', `Stop lb1 (advert 1 s): przerwa ok. ${pl(vrrpFailover(1000, 100).masterDown)} ms + okres sondy + nowe połączenie (typowo 3,7–4 s). Stop Nginx bez track_script: przerwa trwa do ręcznej interwencji (VIP na lb1 z martwym Nginx). Z track_script (interval 1, fall 2): ok. 2 s na wykrycie + przełączenie + GARP (2–3 s). Powrót lb1 z preempt: druga, krótka przerwa przy odbieraniu VIP; z nopreempt: brak. advert_int 0.1: przełączenie ok. ${pl(vrrpFailover(100, 100).masterDown)} ms.`],
  ['Wyjaśnij, dlaczego router aktywny w HSRP powinien śledzić stan swojego uplinku, i skonfiguruj tracking z obniżeniem priorytetu.', 'track interface, decrement.', 'HSRP sprawdza tylko, czy router żyje w LAN; po awarii uplinku aktywny router nadal przyjmuje ruch hostów, a nie może go przekazać (czarna dziura lub okrężna droga przez drugi router). Konfiguracja: track 1 interface GigabitEthernet0/1 line-protocol; interface Vlan10 / standby 10 priority 110 / standby 10 preempt / standby 10 track 1 decrement 20 — po awarii uplinku priorytet spada do 90 < 100, R2 przejmuje (wymaga preempt na R2). Lepsza wersja: track z IP SLA do celu za uplinkiem.'],
  ['Opisz atak przejęcia bramy przez fałszywe ogłoszenia HSRP/VRRP i trzy środki obrony.', 'Priorytet 255, MITM.', 'Atakujący w VLAN-ie wysyła ogłoszenia HSRP/VRRP z tym samym numerem grupy i wyższym priorytetem (np. 255) oraz preempt — staje się aktywną bramą, przyjmuje ruch hostów (MITM, podsłuch, modyfikacja) lub go porzuca (DoS). Obrona: ACL/VACL na portach dostępowych blokujące HSRP (UDP 1985 na 224.0.0.2, UDP 2029 dla v2) i VRRP (protokół IP 112 na 224.0.0.18), uwierzytelnianie HSRP MD5 (i izolacja segmentu dla VRRPv3), monitoring zmian ról (syslog STATECHANGE) i nieznanych źródeł ogłoszeń.'],
  ['Dlaczego klasyczny keepalived (VRRP) zwykle nie działa w VPC AWS i jak zrealizować wirtualny IP w chmurze?', 'Brak multicastu i GARP, adresy przydziela platforma.', 'Sieć VPC nie przenosi multicastu (VRRP używa 224.0.0.18) i ignoruje gratuitous ARP — mapowanie IP→instancja zna i kontroluje platforma. Nawet jeśli węzeł doda VIP do interfejsu, ruch nie zostanie do niego skierowany. Rozwiązania: zarządzany load balancer (NLB/ALB) przed węzłami; keepalived w trybie unicast (unicast_peer) + skrypt notify_master przenoszący secondary private IP lub Elastic IP przez API (aws ec2 assign-private-ip-addresses --allow-reassignment / associate-address); dla Kubernetes — Service LoadBalancer.'],
  ['Czym różni się GLBP od HSRP i VRRP pod względem wykorzystania łączy?', 'AVG, AVF, różne MAC.', 'HSRP/VRRP: w danej grupie tylko jeden router przekazuje ruch, drugi czeka bezczynnie (równoważenie tylko przez kilka grup z różnymi bramami na hostach). GLBP: AVG odpowiada na ARP o wirtualny IP, podając różnym hostom różne wirtualne MAC należące do różnych AVF (do 4) — ruch hostów rozkłada się na routery przy jednej bramie skonfigurowanej na hostach (round-robin, weighted, host-dependent). Po awarii AVF inny router przejmuje jego wirtualny MAC.'],
  ['Twoja aplikacja Java po przełączeniu VIP keepalived loguje przez 60 s błędy „Read timed out”, choć VIP przełączył się w 4 s. Wyjaśnij i napraw.', 'Martwe połączenia w puli, brak timeoutów.', 'Połączenia w puli (HTTP keep-alive, JDBC) były zestawione ze starym węzłem; po przełączeniu nowe pakiety trafiają do nowego węzła, który nie zna tych połączeń (RST) albo do nikogo — przy braku RST klient czeka na odpowiedź do timeoutu odczytu (np. 60 s). Naprawa: krótszy read/socket timeout, walidacja połączeń z puli (HikariCP keepaliveTime/validation, maxLifetime), retry idempotentnych żądań, TCP keepalive; nowy węzeł powinien odpowiadać RST na nieznane połączenia (domyślne zachowanie Linuksa), by klient szybko dostał błąd zamiast czekać.'],
  ['Dlaczego w keepalived przy nopreempt oba węzły muszą mieć state BACKUP?', 'Węzeł w stanie MASTER przy starcie zawsze przejmie rolę.', 'state MASTER oznacza, że przy starcie węzeł od razu ogłasza się masterem, niezależnie od tego, czy inny węzeł już pełni tę rolę — co odbiera VIP (efekt jak preempt). nopreempt działa tylko dla instancji startujących jako BACKUP: węzeł z wyższym priorytetem po powrocie zostaje backupem, dopóki obecny master działa.'],
  ['Policz wirtualny MAC HSRPv2 dla grupy 4095 i wyjaśnij, dlaczego HSRPv2 obsługuje więcej grup niż v1.', '12 bitów numeru grupy.', `${hsrp2Mac(4095)} — HSRPv2 koduje numer grupy na 12 bitach (0–4095) w końcówce 00:00:0c:9f:fX:XX, podczas gdy HSRPv1 ma tylko 8 bitów (0–255) w 00:00:0c:07:ac:XX. Dzięki temu można np. dopasować numer grupy do numeru VLAN-u (do 4094).`],
  ['Jak wykryć split brain w parze keepalived i jak mu zapobiegać?', 'Oba MASTER, duplikat IP.', 'Wykrywanie: obie instancje logują „Entering MASTER STATE” i mają VIP na interfejsie (ip -br addr na obu), ARP w sieci zmienia MAC dla VIP (arping -c3 VIP pokaże dwa MAC), alerty z notify_master z obu węzłów. Przyczyny: firewall blokujący VRRP (protokół 112), multicast filtrowany przez IGMP snooping, różny virtual_router_id lub hasło, awaria łącza między węzłami. Zapobieganie: reguły firewalla dla VRRP, unicast_peer zamiast multicastu, osobne łącze/monitoring heartbeat, fencing (np. przez API chmury lub STONITH w klastrach).'],
  ['Wyjaśnij różnicę między awarią węzła a awarią usługi w kontekście FHRP/keepalived i jak skonfigurować reakcję na obie.', 'Heartbeat vs health check.', 'Awaria węzła (maszyna, keepalived, sieć): ustają ogłoszenia VRRP → backup przejmuje VIP po Master_Down_Interval — działa bez dodatkowej konfiguracji. Awaria usługi (Nginx, aplikacja za nim) przy żywym węźle: ogłoszenia płyną dalej, VIP zostaje na węźle, który nie obsługuje ruchu. Reakcja: vrrp_script/track_script sprawdzający realną ścieżkę (np. curl /healthz przez proxy do aplikacji) z weight lub przejściem w FAULT; na routerach — tracking interfejsów i IP SLA.'],
  ['Zaprojektuj redundancję bramy dla VLAN-u serwerów aplikacji Java z dwoma przełącznikami L3 i wymień ustawienia, które ograniczą przerwę dla aplikacji do ~1 s.', 'VRRPv3/HSRP z ms, tracking, BFD, timeouty.', 'VRRPv3 (lub HSRPv2) na SVI Vlan20 obu przełączników: VIP .1, przełącznik A priorytet 110 z preempt delay 60 s i trackingiem uplinków (IP SLA do rdzenia), B priorytet 100; timery advert 100–200 ms (Master_Down ~0,4–0,7 s) lub BFD; ACL blokujące FHRP z portów serwerów; monitoring zmian stanów. Po stronie aplikacji: connectTimeout 0,5–1 s, read timeout dopasowany do usługi, retry idempotentnych operacji, pula połączeń z walidacją — dzięki temu krótkie przełączenie nie powoduje kaskady błędów.'],
  ['Uruchom FailoverProbe z lekcji bez argumentów i wyjaśnij zmierzoną przerwę. Następnie zmień okres sondy na 500 ms — jak zmieni się dokładność pomiaru?', 'Rozdzielczość pomiaru = okres sondy.', 'Samodzielny test zatrzymuje wbudowany serwer na 2 s, sonda zgłasza DOWN przy pierwszym nieudanym żądaniu i UP przy pierwszym udanym — zmierzona przerwa ≈ 2000 ms ± okres sondy (100 ms) + czas timeoutu/odrzucenia połączenia. Przy okresie 500 ms błąd pomiaru rośnie do ±500 ms (krótkie przerwy mogą zostać niezauważone). Do pomiaru przełączeń sekundowych potrzebny okres rzędu 50–100 ms.'],
  ['Jaki protokół i adres multicast wykorzystuje VRRP (IPv4 i IPv6), a jaki HSRP? Napisz filtry tcpdump do podejrzenia ogłoszeń.', 'Protokół 112, UDP 1985.', 'VRRP: protokół IP 112, adres 224.0.0.18 (IPv6: ff02::12) — tcpdump -ni eth0 vrrp (lub "ip proto 112"). HSRPv1: UDP 1985 na 224.0.0.2, HSRPv2: UDP 1985 na 224.0.0.102 (IPv6: UDP 2029, ff02::66) — tcpdump -ni eth0 "udp port 1985 or udp port 2029". W wyniku widać VRID/grupę, priorytet, interwał i adres VIP.'],
  ['Dlaczego VRRP pozwala, by wirtualny adres był fizycznym adresem jednego z routerów (owner), a HSRP nie? Jakie są tego konsekwencje?', 'Priorytet 255.', 'W VRRP router, którego interfejs ma adres równy VIP, jest „właścicielem” — ma automatycznie priorytet 255 i zawsze zostaje masterem, gdy działa (oszczędza adres w małych podsieciach). HSRP wymaga osobnego adresu dla VIP i dla każdego routera. Konsekwencja ownera: po powrocie owner zawsze odbiera rolę (nie da się wyłączyć wywłaszczenia dla 255), a jego awaria oznacza też utratę adresu zarządzania pod VIP.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
