// Generuje lekcje _ns_00_wprowadzenie/01_TrackMapAndLearningCycle.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_01_trackmap_lesson.cjs src/main/resources/content/_ns_00_wprowadzenie/01_TrackMapAndLearningCycle.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { plan, SHORT } = require('./track_meta.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const nCh = plan.length, nLessons = plan.reduce((a, c) => a + c[3].length, 0);
const chLabel = (i) => `${String(i).padStart(2, '0')} — ${SHORT[i]}`;

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Po co Java developerowi sieci i cyberbezpieczeństwo', ...img('ns00-01-track-map.svg', `Mapa toru w trzech częściach: I. Sieci dla Java developera (rozdziały 00–12), II. Infrastruktura i operacje (13–14), III. Cyberbezpieczeństwo blue team (15–40); razem ${nCh} rozdziałów i ${nLessons} lekcji.`),
    body:
`Większość błędów, które budzą dyżurnego o trzeciej w nocy, nie jest błędem w Javie. To \`UnknownHostException\`, bo DNS w kontenerze zwraca coś innego niż na laptopie. To \`SSLHandshakeException\`, bo ktoś podmienił certyfikat pośredni. To 504 z load balancera, który ma krótszy timeout niż zapytanie do bazy. A gdy coś pójdzie naprawdę źle — to incydent bezpieczeństwa, w którym trzeba ustalić, *kto, kiedy i jak*.

Tor **Sieci i Cyberbezpieczeństwo** jest dla developera z pasją, który chce rozumieć system *pod* aplikacją i umieć go bronić. Ma ${nCh} rozdziałów i ${nLessons} lekcji w trzech częściach:
- **Część I — Sieci dla Java developera (00–12)**: modele warstwowe, Ethernet i switching, IP i routing (z maskami od zera), TCP/UDP/QUIC, DNS, HTTP, TLS i PKI, protokoły aplikacyjne, Linux networking, Docker/Kubernetes/AWS, proxy i load balancing, sieci w Javie.
- **Część II — Infrastruktura i operacje (13–14)**: okablowanie, Wi-Fi, WAN, VPN, NAC, QoS, monitoring, automatyzacja (Python, Ansible, NETCONF), AI w operacjach sieci, troubleshooting.
- **Część III — Cyberbezpieczeństwo blue team (15–40)**: fundamenty, tożsamość, kryptografia, Windows i Active Directory, Linux, zagrożenia sieciowe, architektura, hardening i podatności, AppSec (OWASP Top 10:2025), DevSecOps, chmura i Kubernetes, SIEM i SOC, detection engineering, analiza ruchu, threat intelligence i hunting, phishing, malware, reagowanie na incydenty, DFIR, automatyzacja i AI, bezpieczeństwo systemów AI, purple team, GRC (KSC/NIS2, DORA, RODO), studia przypadków, egzaminy i kariera.

Tor jest zmapowany na aktualne certyfikaty (stan: listopad 2026): **CompTIA Network+, Cisco CCNA 200-301 v2.0, Security+ SY0-801, CySA+ CS0-004, SecAI+**, a także praktyczne egzaminy blue team (BTL1, CyberDefenders) i Microsoft SC-200. Ale celem nie jest kolekcjonowanie certyfikatów — celem jest **umiejętność**, a certyfikat ma ją potwierdzić.` },

  { type: 'DEFINITION', heading: 'Profil absolwenta i słownik toru', body:
`**Profil absolwenta**: Java backend developer, który:
- rozumie sieć jak network engineer — od bitów maski po BGP, od ramki Ethernet po HTTP/3,
- diagnozuje problemy warstwa po warstwie, zamiast zgadywać,
- broni systemów jak analityk **blue team** — wykrywa, bada i reaguje na incydenty, projektuje zabezpieczenia,
- pisze kod, który jest bezpieczny z założenia (AppSec, DevSecOps).

**Słownik toru:**
- **Blue team** — obrońcy: SOC, detection engineering, incident response, DFIR, threat hunting, AppSec, architektura bezpieczeństwa. Przeciwieństwo red teamu (kontrolowane ataki) — oba łączy purple team.
- **Poziomy lekcji**: **L1** Foundation (pojęcia i pierwsze narzędzia), **L2** Professional (samodzielna praca jak w zespole), **L3** Advanced (mechanizmy wewnętrzne, przypadki brzegowe), **L4** Attack & Defense (atak odtworzony w izolowanym labie + jego wykrycie i obrona), **L5** Expert (bez prowadzenia: Boss, śledztwo, projekt).
- **Priorytety**: **K** krytyczne (umieć stosować), **W** ważne (umieć diagnozować), **Z** zaawansowane, **U** uzupełniające.
- **Lab** — każda lekcja ma część praktyczną: Guided, Packet, Debug, Failure, Defense, Incident, Malware/Forensics, Build, Architecture, Boss.
- **Boss** — lekcja zamykająca rozdział: integruje wszystkie umiejętności rozdziału bez podpowiedzi, z artefaktami do oceny.
- **Artefakt** — dowód wykonania: plik pcap z sumą SHA-256, reguła detekcji, raport, diagram, konfiguracja z testem.` },

  { type: 'ANALOGY', heading: 'Ratownik medyczny, a nie encyklopedia', body:
`Wyobraź sobie dwie osoby, które przeczytały ten sam podręcznik anatomii. Pierwsza potrafi wyrecytować nazwy wszystkich kości. Druga — ratownik — na miejscu wypadku w ciągu minuty ustala, co jest najgroźniejsze, co zrobić najpierw i kogo wezwać.

Ten tor uczy, byś był **ratownikiem**:
- Wiedza (anatomia) to modele i protokoły — potrzebne, ale niewystarczające.
- **Diagnoza** (triage) to umiejętność zawężenia problemu: warstwa, element, dowód.
- **Procedury** (resuscytacja) to playbooki: co zrobić przy wycieku klucza, przejętym koncie, ransomware.
- **Ćwiczenia na fantomie** to laboratoria: psujesz i atakujesz *własny, izolowany* system, by w prawdziwej sytuacji działać odruchowo.
- **Dokumentacja** (karta pacjenta) to raporty i dowody — w incydencie bez nich nie ma ustaleń.

Dlatego w każdej lekcji jest teoria, zadania z rozwiązaniami i quiz — ale prawdziwy test to lab, w którym coś działa, psuje się, a ty to wykrywasz i naprawiasz.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Jak zbudowana jest każda lekcja', body:
`Wszystkie lekcje toru mają tę samą strukturę, więc szybko nauczysz się, gdzie czego szukać:`,
    code:
`TEORIA (14 bloków)
  INTRO            po co to jest i jaki problem rozwiązuje
  DEFINITION       słownik pojęć
  ANALOGY          intuicja z życia codziennego
  VISUAL_EXAMPLE   schemat, tabela, ilustracja
  CODE_BASIC       najprostszy działający przykład (Java, polecenia)
  STEP_BY_STEP     procedura krok po kroku
  NOTE             uzupełnienie, tabela referencyjna
  CODE_PRACTICAL   praktyka: narzędzia, konfiguracje, lab
  USAGE            gdzie to spotkasz w pracy developera i obrońcy
  PITFALL          najczęstsze błędy
  CODE_WRONG       źle — antywzorzec z konsekwencjami
  CODE_RIGHT       dobrze — poprawione rozwiązanie
  WHEN_TO_USE      kiedy stosować, kiedy nie
  SUMMARY          podsumowanie i zapowiedź następnej lekcji

ZADANIA (30)    z podpowiedzią i pełnym rozwiązaniem; liczby liczone skryptem, więc poprawne
QUIZ (100)      jednokrotnego wyboru z wyjaśnieniem; powtórki rozłożone w czasie
ILUSTRACJE      schematy SVG do najważniejszych pojęć` },

  { type: 'CODE_BASIC', heading: 'Twoje pierwsze polecenia: sprawdź, co już wiesz', body:
`Zanim zaczniesz, zrób szybki przegląd środowiska. Jeśli któreś polecenie nic Ci nie mówi — świetnie, właśnie po to jest ten tor. Zapisz wyniki; wrócisz do nich po rozdziale 01.`,
    code:
`# Linux / WSL2
ip -br addr                 # Twoje interfejsy i adresy (rozdz. 02–03)
ip route                    # brama domyślna (rozdz. 03)
resolvectl status | head    # skąd biorą się odpowiedzi DNS (rozdz. 05)
ss -tlnp                    # co nasłuchuje na Twoim komputerze (rozdz. 04, 09)
curl -sv https://example.com -o /dev/null 2>&1 | grep -E 'Connected|SSL connection|HTTP/'   # TCP, TLS, HTTP

# Windows (PowerShell)
Get-NetIPConfiguration
Resolve-DnsName example.com
Get-NetTCPConnection -State Listen | Select-Object LocalAddress, LocalPort, OwningProcess

# Java: jakie wersje TLS i szyfry wspiera Twoja JVM? (rozdz. 07)
jshell> javax.net.ssl.SSLContext.getDefault().getSupportedSSLParameters().getProtocols()` },

  { type: 'STEP_BY_STEP', heading: 'Cykl nauki: od zrozumienia do dowodu', ...img('ns00-01-learning-cycle.svg', 'Siedem kroków w kole: UNDERSTAND (zrozum model), BUILD (zbuduj działający lab), OBSERVE (zobacz w pakietach i logach), BREAK (zepsuj kontrolowanie), ATTACK (odtwórz atak w izolowanym labie), FIX (zabezpiecz i napraw), PROVE (udowodnij testem i dowodem). W środku: atak tylko po to, by go wykryć i zatrzymać.'),
    body:
`Każda duża kompetencja (np. DNS, TLS, Active Directory) przechodzi przez ten sam cykl:
1. **UNDERSTAND** — model pojęciowy: co, po co, jak działa. Teoria i ilustracje.
2. **BUILD** — działające środowisko: kontenery, konfiguracja, aplikacja Spring Boot.
3. **OBSERVE** — patrzysz na żywy system: pakiety (Wireshark), logi, metryki. Uczysz się, jak wygląda „normalność” (baseline).
4. **BREAK** — kontrolowana awaria: zła maska, wygasły certyfikat, 1% strat. Uczysz się objawów.
5. **ATTACK** — odtworzenie ataku *wyłącznie w izolowanym labie* (np. ARP spoofing między dwoma kontenerami), na poziomie potrzebnym do zrozumienia śladów. Bez instrukcji ataków na cudze systemy.
6. **FIX** — zabezpieczenie: konfiguracja, kod, reguła detekcji, segmentacja.
7. **PROVE** — dowód: test przechodzi, alert się włącza, skan nie znajduje luki, raport zawiera artefakty.

Jak korzystać z toru:
- Część I przechodź **po kolei** — każda lekcja korzysta z poprzednich (szczególnie 01 → 03 → 04).
- W części III możesz wybrać **ścieżkę roli** (np. SOC: 15 → 18 → 19 → 26 → 27 → 28 → 33), ale rozdziały 15–17 i 26 są wspólne dla wszystkich.
- Rób labów więcej niż quizów. Quiz sprawdza pamięć; lab — umiejętność.
- Prowadź **dziennik laboratoryjny**: polecenia, wyniki, wnioski, pliki. To przyszłe portfolio.` },

  { type: 'NOTE', heading: 'Poziomy trudności i typy laboratoriów', ...img('ns00-01-levels-labs.svg', 'Piramida poziomów: L1 Foundation u podstawy, potem L2 Professional, L3 Advanced, L4 Attack & Defense i L5 Expert na szczycie. Obok lista typów laboratoriów: Guided, Packet, Debug, Failure, Defense, Incident, Malware/Forensics, Build, Architecture, Boss.'),
    body:
`Każda lekcja ma poziom i typ laboratorium. Pozwala to dobrać tempo i wiedzieć, czego się spodziewać:`,
    code:
`poziom  nazwa              czego oczekujemy                                         przykład
L1      Foundation         rozumiesz pojęcia, uruchamiasz narzędzia z instrukcją    maska podsieci od zera
L2      Professional       samodzielnie rozwiązujesz typowe zadania                 CIDR i podsieci VPC
L3      Advanced           rozumiesz mechanizmy wewnętrzne i przypadki brzegowe     kontrola przeciążenia TCP
L4      Attack & Defense   odtwarzasz atak w labie, wykrywasz go i blokujesz        ataki na Kerberos i ich detekcja
L5      Expert             rozwiązujesz problem bez prowadzenia                     Boss: przejęcie domeny

typ lab         co robisz                                         artefakt
Guided          krok po kroku z instrukcją                        działające środowisko
Packet          analizujesz przechwycony ruch                     opisany pcap
Debug           znajdujesz celowo wprowadzoną usterkę             przyczyna + poprawka + test
Failure         wywołujesz awarię (tc netem, wyłączenie węzła)    pomiary przed/po
Defense         zabezpieczasz i budujesz detekcję                 konfiguracja + reguła + dowód działania
Incident        badasz incydent na danych                         oś czasu, IoC, raport
Malware / Forensics  analizujesz próbki i artefakty               raport techniczny, YARA
Build           budujesz narzędzie (Java/Python)                  kod z testami
Architecture    projektujesz i uzasadniasz                        diagram + decyzje
Boss            integracja rozdziału bez podpowiedzi              komplet artefaktów oceniany rubryką` },

  { type: 'CODE_PRACTICAL', heading: 'Dziennik laboratoryjny w repozytorium Git', body:
`Najlepszy nawyk, jaki możesz wynieść z toru: każde laboratorium dokumentujesz w repozytorium. Po roku masz portfolio, które pokażesz na rozmowie rekrutacyjnej (rozdział 40).`,
    code:
`netsec-journal/
├── README.md                     # cel, spis labów, ścieżka certyfikacji
├── 01-fundamenty/
│   ├── 08-boss-packet-journey/
│   │   ├── docker-compose.yml
│   │   ├── NOTES.md              # polecenia, obserwacje, wnioski, numery ramek
│   │   ├── cap/SHA256SUMS        # pcap NIE w repo (dane!), tylko sumy i opis
│   │   └── diagram.drawio.svg
├── 27-detection-engineering/
│   └── rules/sigma/proc_creation_java_spawns_shell.yml
└── .gitignore                    # *.pcap, *.pcapng, keys.log, *.evtx, .env

# Szablon NOTES.md
## Cel
## Środowisko (wersje, adresy)
## Kroki i polecenia
## Obserwacje (z odwołaniem do dowodów)
## Wnioski i co bym zrobił inaczej
## Mapowanie: rozdział / cel egzaminu (np. Sec+ V8 4.4, CySA+ V4 1.3)` },

  { type: 'USAGE', heading: 'Role, do których przygotowuje tor', ...img('ns00-01-roles.svg', 'Osiem kart ról z numerami rozdziałów: AppSec/Product Security (15, 17, 23, 24, 36), DevSecOps/Platform Security (10, 22, 24, 25), Analityk SOC (18–20, 26, 28, 31), Detection Engineer (26, 27, 30, 35), Incident Responder/DFIR (32–34), Cloud Security Engineer (10, 16, 25), Threat Intelligence/Hunter (29, 30, 39), Network/Security Architect (13, 14, 21, 38).'),
    body:
`Java developer ma w bezpieczeństwie przewagę, której brakuje wielu osobom zaczynającym od administracji: **czyta kod, rozumie systemy rozproszone, CI/CD i automatyzację**. Tor wykorzystuje tę przewagę:
- **AppSec / Product Security** — przeglądy kodu, modelowanie zagrożeń, SAST/DAST, bezpieczne wzorce w Spring.
- **DevSecOps / Platform Security** — pipeline’y, łańcuch dostaw (SBOM, podpisy), polityki jako kod, hardening kontenerów.
- **Analityk SOC** — triage alertów, analiza logów i ruchu, eskalacja. Developer szybciej rozumie logi aplikacji i ich anomalie.
- **Detection Engineer** — detekcje jako kod (Sigma, KQL, ES|QL), testy i CI — praca bardzo „developerska”.
- **Incident Responder / DFIR** — badanie incydentów, artefakty, oś czasu, raporty.
- **Cloud Security Engineer** — IAM, logi audytowe, guardrails, Kubernetes.
- **Threat Intelligence / Hunter** — hipotezy, dane, korelacje.
- **Architekt** — projektowanie sieci i zabezpieczeń dla całej organizacji.

Niezależnie od roli, każdy obrońca potrzebuje części I — bo każdy alert, log i incydent ostatecznie sprowadza się do tego, kto z kim komunikował się w sieci.` },

  { type: 'PITFALL', heading: 'Jak nie uczyć się bezpieczeństwa', body:
`- **Zaczynanie od narzędzi ofensywnych** — Kali, exploity, „hakowanie” bez zrozumienia sieci daje wiedzę płytką i często nielegalne nawyki. Obrońca musi najpierw rozumieć *normalność*.
- **Pomijanie części I** — „sieci już znam” zwykle kończy się na masce podsieci i TLS. Bez fundamentów analiza logów i ruchu to zgadywanie.
- **Kolekcjonowanie certyfikatów bez labów** — egzaminy CompTIA mają zadania praktyczne (PBQ), a rekruterzy blue team pytają o konkretne śledztwa.
- **Testowanie na cudzych systemach** — „tylko sprawdzałem” nie jest obroną prawną (lekcja 03). Wszystkie ćwiczenia ofensywne wyłącznie we własnym, izolowanym labie.
- **Brak notatek** — po miesiącu nie pamiętasz, jak rozwiązałeś problem. Dziennik laboratoryjny to też wymóg pracy w IR (dokumentacja działań).
- **Uczenie się na pamięć list portów i ID zdarzeń** bez kontekstu — zapamiętasz je naturalnie, rozwiązując realne zadania.
- **Ignorowanie Windows i Active Directory** — nawet w firmach „linuksowych” stacje pracowników i tożsamość zwykle stoją na Microsoft; to najczęstszy teren incydentów.` },

  { type: 'CODE_WRONG', heading: 'Źle: plan nauki „wszystko naraz”', body:
`Typowy plan, który kończy się zniechęceniem po dwóch tygodniach: bez kolejności, bez labów, bez mierzalnych celów.`,
    code:
`Tydzień 1: Security+ (cała książka), CCNA, kurs „ethical hacking”, Kubernetes
Tydzień 2: OSCP?, AWS Security Specialty, „może SIEM”
Metoda:    filmy na 2x, bez notatek, quizy do skutku
Lab:       „jak będzie czas”
Cel:       „pracować w cyber”` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: plan z kolejnością, labami i dowodami', body:
`Konkretne bloki, mierzalne artefakty i jedna ścieżka certyfikacji naraz. Plan łatwo dopasować do pracy na etacie (5–7 godzin tygodniowo).`,
    code:
`Kwartał 1 — Część I (rozdz. 00–06): sieci, maski, TCP, DNS, HTTP
  co tydzień: 2 lekcje + 1 lab z NOTES.md; Boss rozdz. 01 i 03 jako kamienie milowe
  egzamin: Network+ (N10-009) na koniec kwartału lub po rozdz. 14
Kwartał 2 — rozdz. 07–12 + 15–17: TLS, Linux, kontenery, sieci w Javie, fundamenty security
  artefakt: lab TLS z mTLS w Spring Boot, model zagrożeń własnej usługi
  egzamin: Security+ (SY0-801)
Kwartał 3 — rozdz. 18, 19, 26–28: Windows/AD, Linux, SIEM, detekcje, analiza ruchu
  artefakt: domowy SOC (Wazuh/Security Onion), 10 detekcji Sigma w repo z testami
Kwartał 4 — rozdz. 30, 33, 34 + 40: hunting, IR, DFIR, egzaminy
  egzamin: CySA+ (CS0-004) lub praktyczny BTL1; portfolio z 5 artefaktami` },

  { type: 'WHEN_TO_USE', heading: 'Jak dobrać ścieżkę do swojego celu', body:
`- **Chcesz po prostu lepiej rozumieć systemy jako developer** — część I w całości, potem 15, 16, 17, 23, 24. Bez presji certyfikatów.
- **Celujesz w AppSec / DevSecOps** — część I, potem 15–17, 22–25, 36; uzupełniająco 27 (detekcje z logów aplikacji). Certyfikaty: Security+, opcjonalnie CSSLP, AWS Security Specialty, CKS.
- **Celujesz w SOC / detection engineering** — część I, 15–20, 26–31, 33–35. Certyfikaty: Network+, Security+, CySA+, praktyczne BTL1/CCD, SC-200 (jeśli stos Microsoft).
- **Celujesz w IR / DFIR** — jak SOC + 32–34 i 39. Certyfikaty: CySA+, BTL1/BTL2; w dalszej perspektywie GCIH/GCFA.
- **Celujesz w sieci / architekturę** — części I i II w całości, 20–21, 38. Certyfikaty: Network+, CCNA (v2.0 od lutego 2027), potem Security+.
- **Masz mało czasu** — lekcje priorytetu **K** w kolejności rozdziałów; resztę zostaw na później.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Tor ma ${nCh} rozdziałów i ${nLessons} lekcji w trzech częściach: sieci (00–12), infrastruktura i operacje (13–14), cyberbezpieczeństwo blue team (15–40).
- Profil absolwenta: Java developer, który rozumie sieć jak network engineer i broni systemów jak analityk blue team.
- Każda lekcja: 14 bloków teorii, 30 zadań, 100 pytań, ilustracje; każdy rozdział kończy Boss.
- Poziomy L1–L5, priorytety K/W/Z/U, dziesięć typów laboratoriów.
- Cykl: UNDERSTAND → BUILD → OBSERVE → BREAK → ATTACK → FIX → PROVE — atak wyłącznie w izolowanym labie i po to, by go wykryć i zatrzymać.
- Prowadź dziennik laboratoryjny w Git — to Twoje przyszłe portfolio.
- Następne lekcje rozdziału: środowisko laboratoryjne, etyka i prawo, podróż jednego żądania, mapa certyfikatów.` },
];

// ---------- ZADANIA (diagnoza startowa + planowanie) ----------
const { exercises, ex } = exerciseBuilder();
const diag = [
  ['Co się stanie, gdy aplikacja Spring Boot nasłuchuje na 127.0.0.1:8080 w kontenerze, a port jest opublikowany na hoście (-p 8080:8080)?', 'Połączenie z hosta nie zadziała (Connection reset / empty reply): ruch z hosta trafia na interfejs eth0 kontenera, a aplikacja nasłuchuje tylko na pętli zwrotnej kontenera. Trzeba nasłuchiwać na 0.0.0.0 (server.address). Rozdział 10.'],
  ['Co się stanie, gdy dwa hosty w tej samej sieci mają różne maski (/24 i /25)?', 'Mogą różnie oceniać, czy są w tej samej sieci: jeden wyśle bezpośrednio, drugi przez bramę — komunikacja asymetryczna lub zerwana. Rozdział 03 (maska podsieci).'],
  ['Co się stanie z połączeniami TLS aplikacji Java, gdy certyfikat serwera wygaśnie?', 'Handshake się nie powiedzie: SSLHandshakeException (certificate expired / PKIX validation failed). Klienci z poprawną walidacją przestaną się łączyć. Rozdział 07.'],
  ['Co się stanie, gdy zmienisz rekord DNS usługi, a aplikacja Java działa od tygodnia?', 'Może dalej łączyć się ze starym adresem: cache DNS w JVM (networkaddress.cache.ttl), pule połączeń z otwartymi połączeniami. Rozdział 05.'],
  ['Co się stanie, gdy reverse proxy ma timeout 30 s, a zapytanie do bazy trwa 45 s?', 'Proxy zwróci klientowi 504 Gateway Timeout po 30 s, a aplikacja nadal będzie wykonywać zapytanie (marnując zasoby), chyba że ma własny timeout. Rozdział 11.'],
  ['Co się stanie, gdy w sieci firmowej ktoś podłączy własny router z serwerem DHCP?', 'Rogue DHCP: część klientów dostanie złą bramę/DNS — przerwy w łączności lub przechwytywanie ruchu. Obrona: DHCP snooping. Rozdziały 02 i 20.'],
  ['Co się stanie, gdy pula HikariCP ma 10 połączeń, a 50 wątków jednocześnie potrzebuje bazy?', '40 wątków czeka na połączenie do connectionTimeout; po jego przekroczeniu SQLTransientConnectionException. Rosną opóźnienia i kolejki. Rozdziały 06 (prawo Little’a) i 12.'],
  ['Co się stanie, gdy firewall odrzuca pakiety (DROP) na porcie 5432, a aplikacja ma connect timeout 60 s?', 'Każda próba połączenia wisi 60 s (retransmisje SYN), wątki się blokują, możliwa kaskadowa awaria. Rozdziały 04 i 12.'],
  ['Co się stanie, gdy aplikacja zaufa nagłówkowi X-Forwarded-For od klienta przy allow-liście IP?', 'Klient może podać dowolny adres i obejść allow-listę. Rozdział 01 (Boss) i 23.'],
  ['Co się stanie, gdy klucz dostępowy AWS trafi do publicznego repozytorium?', 'W ciągu minut automaty znajdą klucz i mogą go użyć (np. do kopania kryptowalut, kradzieży danych). Trzeba natychmiast unieważnić i zrotować klucz, zbadać CloudTrail. Rozdziały 24 i 25.'],
  ['Co się stanie, gdy proces Java na serwerze uruchomi /bin/sh?', 'W typowej usłudze to bardzo podejrzane (np. eksploatacja deserializacji lub Log4Shell). Dobrze skonfigurowany EDR/Falco/auditd zgłosi alert. Rozdziały 19 i 27.'],
  ['Co się stanie, gdy MTU tunelu VPN jest mniejsze niż 1500, a ICMP jest blokowane?', 'PMTUD black hole: małe pakiety przechodzą, duże giną — handshake TLS i duże transfery wiszą, ping działa. Rozdziały 01 i 03.'],
  ['Co się stanie, gdy użytkownik potwierdzi w aplikacji MFA push, którego sam nie zainicjował?', 'Napastnik znający hasło zaloguje się (MFA fatigue). Obrona: number matching, MFA odporne na phishing (FIDO2), detekcja serii pushy. Rozdział 16.'],
  ['Co się stanie, gdy wyłączysz cały ruch ICMP na firewallu „dla bezpieczeństwa”?', 'Przestanie działać PMTUD i część diagnostyki (ping, traceroute), mogą pojawić się zawieszone połączenia. Należy przepuszczać potrzebne typy ICMP. Rozdział 03.'],
  ['Co się stanie, gdy zapis dziennika audytowego w chmurze zostanie wyłączony przez napastnika?', 'Obrońcy stracą widoczność; samo wyłączenie (np. StopLogging w CloudTrail) powinno być wykrywane i blokowane politykami (SCP). Rozdział 25.'],
  ['Co się stanie, gdy serwer DNS w Kubernetesie (CoreDNS) przestanie odpowiadać?', 'Pody nie rozwiążą nazw usług — fale UnknownHostException, mimo że same usługi działają. Rozdział 05 i 10.'],
  ['Co się stanie, gdy aplikacja loguje pełne nagłówki HTTP na poziomie INFO?', 'W logach trafią tokeny (Authorization), ciasteczka sesyjne i dane osobowe — każdy z dostępem do logów może przejąć sesje. Rozdział 23 (A09).'],
  ['Co się stanie, gdy dwie usługi wołają się nawzajem z retry bez limitu podczas awarii jednej z nich?', 'Burza ponowień (retry storm) zwielokrotni ruch i pogłębi awarię. Potrzebne: backoff z jitterem, limity, circuit breaker. Rozdział 12.'],
  ['Co się stanie, gdy użytkownik z uprawnieniami admina domeny zaloguje się na zainfekowaną stację roboczą?', 'Jego poświadczenia mogą zostać przechwycone z pamięci (LSASS) i użyte do przejęcia domeny. Obrona: model warstwowy, PAW, Protected Users. Rozdział 18.'],
  ['Co się stanie, gdy zablokujesz w Security Group cały ruch wychodzący z podsieci aplikacji?', 'Aplikacja nie połączy się z zależnościami zewnętrznymi (API, repozytoria, DNS do zewnętrznych resolverów — zależnie od konfiguracji). To dobry kierunek (egress control), ale wymaga jawnych wyjątków. Rozdziały 10 i 23 (SSRF).'],
];
for (const [q, a] of diag) ex(`Diagnoza startowa — co się stanie? ${q}`, 'Odpowiedz najpierw sam, zanim zajrzysz do rozwiązania; zapisz, czy wiedziałeś. Wynik pokaże, które rozdziały są dla Ciebie najważniejsze.', a);
ex('Policz swój wynik diagnozy startowej (20 pytań): 0–7 poprawnych — przejdź część I po kolei; 8–14 — część I w szybszym tempie, z naciskiem na Bossy; 15–20 — zacznij od Bossów części I, a luki uzupełniaj wybiórczo. Zapisz wynik w dzienniku.', 'Bądź uczciwy — to narzędzie dla Ciebie, nie egzamin.', 'Zapisz liczbę poprawnych odpowiedzi, listę pytań, na które nie znałeś odpowiedzi, i odpowiadające im rozdziały. Po rozdziale 12 powtórz diagnozę — wzrost wyniku to pierwszy mierzalny dowód postępu.');
ex('Załóż repozytorium „netsec-journal” zgodnie ze strukturą z lekcji, z plikiem .gitignore wykluczającym pliki pcap, klucze i pliki .env. Dodaj README z celem i planowaną ścieżką.', 'Wykorzystaj szablon z bloku „Dziennik laboratoryjny”.', 'Minimalny .gitignore: *.pcap, *.pcapng, *.evtx, keys.log, *.key, *.pem (prywatne), .env, *.dmp, *.raw. README: cel (np. „przejście do roli SOC w 12 miesięcy”), ścieżka rozdziałów, certyfikaty z terminami, link do tabeli postępu.');
ex('Wybierz jedną rolę z lekcji i wypisz rozdziały, które są dla niej kluczowe, oraz trzy artefakty portfolio, które chcesz wytworzyć w ciągu najbliższych 6 miesięcy.', 'Skorzystaj z ilustracji ról i bloku „Jak dobrać ścieżkę”.', 'Przykład (Detection Engineer): rozdziały 01–05, 09, 15, 18, 19, 26, 27, 28, 30; artefakty: repozytorium detekcji Sigma z testami w CI, domowy SIEM z Sysmon i dashboardem pokrycia ATT&CK, raport z huntu na danych labu.');
ex('Ułóż tygodniowy rytm nauki dopasowany do Twojego czasu (np. 6 godzin): ile lekcji, ile labów, kiedy powtórki quizów i dziennik.', 'Najpierw lab, potem quiz; powtórki rozłożone w czasie.', 'Przykład: pon. 1 h teoria lekcji A; śr. 1,5 h lab lekcji A + NOTES.md; czw. 1 h teoria lekcji B; sob. 2 h lab B lub Boss; nd. 0,5 h powtórka quizów z poprzednich tygodni (wygasające lekcje). Co miesiąc: przegląd dziennika i aktualizacja planu.');
ex('Przejrzyj mapę toru i wskaż trzy rozdziały, które wydają Ci się najtrudniejsze, oraz trzy, które są najbliżej Twojej obecnej pracy. Jak to wpłynie na kolejność nauki?', 'Trudne nie znaczy „na koniec” — fundamenty muszą być pierwsze.', 'Przykład: trudne — 03 (maski), 18 (AD), 34 (DFIR); bliskie pracy — 06 (HTTP), 12 (Java), 23 (AppSec). Wniosek: rozdział 03 zrób wcześnie i dokładnie (fundament), 06/12 można przejść szybciej, 18 i 34 zaplanować z większym zapasem czasu i labami.');
ex('Sprawdź na stronie wydawcy aktualne wersje egzaminów, które planujesz (np. Security+ SY0-801, CySA+ CS0-004, CCNA v2.0) i daty wycofania poprzednich wersji. Zapisz źródło i datę sprawdzenia w README.', 'Egzaminy zmieniają wersje co ok. 3 lata; materiały firm trzecich bywają nieaktualne.', 'Zapisz: nazwę i kod egzaminu, datę premiery/wycofania, link do „exam objectives” u wydawcy (CompTIA, Cisco, Microsoft), datę sprawdzenia. Przykład stanu z listopada 2026: Security+ SY0-801 od 17.11.2026 (SY0-701 do 11.06.2027), CySA+ CS0-004 od 23.06.2026, CCNA 200-301 v2.0 od 3.02.2027.');
ex('Uruchom polecenia z bloku „Twoje pierwsze polecenia” i zapisz wyniki. Przy każdym poleceniu zanotuj, czego nie rozumiesz — to lista pytań do rozdziałów 01–05.', 'Nie szukaj jeszcze odpowiedzi — zbierz pytania.', 'Przykładowe pytania: dlaczego mam kilka adresów (IPv4, IPv6 link-local, docker0)? Co to „proto dhcp” w ip route? Dlaczego resolver to 127.0.0.53? Co oznacza 0.0.0.0:* vs 127.0.0.1:* w ss? Jaki to szyfr TLS_AES_256_GCM_SHA384? Odpowiedzi znajdziesz w rozdziałach 02–07.');
ex('Zapisz w dzienniku swoje zobowiązanie etyczne: wszystkie ćwiczenia ofensywne wykonujesz wyłącznie we własnym, izolowanym labie lub w środowiskach, na które masz pisemną zgodę. Dlaczego warto zrobić to jawnie, a nie tylko „w głowie”?', 'Zobacz lekcję 03 rozdziału: etyka, zakres i prawo.', 'Jawne zobowiązanie (np. w README) porządkuje nawyki, pokazuje rekruterom dojrzałość i przypomina granice w sytuacjach „tylko sprawdzę”. W pracy obrońcy każda czynność ofensywna (testy, symulacje) wymaga zakresu i autoryzacji — dziennik to pierwszy krok do tej dyscypliny.');
ex('Zaprojektuj prostą tabelę postępu (arkusz lub Markdown): rozdział, lekcja, status (teoria/lab/quiz), artefakt, data, notatki. Wypełnij ją dla rozdziału 00.', 'Tabela ma być narzędziem, nie celem — 5 kolumn wystarczy.', '| Rozdz. | Lekcja | Status | Artefakt | Data |\n|---|---|---|---|---|\n| 00 | 01 Mapa toru | teoria ✔ zadania ✔ quiz ✔ | README + diagnoza | 2026-11-13 |\n| 00 | 02 Środowisko | … | lab-zero działa | … |');
ex('Porównaj dwa podejścia: „najpierw narzędzia ofensywne” i „najpierw fundamenty sieci i obrona”. Wypisz po trzy konsekwencje każdego dla kogoś, kto chce pracować w SOC.', 'Pomyśl o tym, co analityk robi na co dzień.', 'Narzędzia ofensywne na start: szybka satysfakcja, ale płytkie rozumienie śladów w logach i ruchu; ryzyko nawyków działania bez autoryzacji; trudność w odróżnianiu normalności od ataku. Fundamenty i obrona: wolniejszy start, ale umiejętność czytania pakietów, logów i architektury; łatwiejsze zdanie CySA+/BTL1; lepsze przygotowanie do pracy z alertami. Wiedza o atakach przychodzi potem w kontrolowanych labach L4 i ćwiczeniach purple team.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111401);
// generowane z planu: w ktorym rozdziale jest lekcja?
const picks = [];
plan.forEach((c, ci) => {
  if (ci === 0) return;
  const l = c[3].find((x) => !/Boss/.test(x[0]) && !/Overview|Landscape|Map/.test(x[0]));
  if (l) picks.push([l[1], ci]);
});
for (const [title, ci] of picks) {
  const wrong = [ci + 1, ci - 1, ci + 3, ci - 3, ci + 5, ci - 5, ci - 7].filter((x) => x > 0 && x < nCh && x !== ci).slice(0, 3).map(chLabel);
  add(`W którym rozdziale toru znajdziesz lekcję „${title}”?`, chLabel(ci), wrong, `Lekcja należy do rozdziału ${chLabel(ci)}.`);
}
const lv = [['L1', 'Foundation — pojęcia, modele, pierwsze narzędzia'], ['L2', 'Professional — samodzielne rozwiązywanie typowych zadań'], ['L3', 'Advanced — mechanizmy wewnętrzne i przypadki brzegowe'], ['L4', 'Attack & Defense — atak w izolowanym labie, jego wykrycie i obrona'], ['L5', 'Expert — rozwiązanie problemu bez prowadzenia']];
for (const [k, d] of lv) add(`Co oznacza poziom lekcji ${k}?`, d, lv.filter((x) => x[0] !== k).map((x) => x[1]).slice(0, 3), `${k}: ${d}.`);
const pr = [['K', 'krytyczne — umieć stosować'], ['W', 'ważne — umieć diagnozować'], ['Z', 'zaawansowane'], ['U', 'uzupełniające']];
for (const [k, d] of pr) add(`Co oznacza priorytet lekcji „${k}”?`, d, pr.filter((x) => x[0] !== k).map((x) => x[1]), `${k}: ${d}.`);
const labs = [['Guided', 'Wykonanie zadania krok po kroku według instrukcji'], ['Packet', 'Analiza przechwyconego ruchu sieciowego'], ['Debug', 'Znalezienie i naprawa celowo wprowadzonej usterki'], ['Failure', 'Wywołanie kontrolowanej awarii i pomiar jej skutków'], ['Defense', 'Zabezpieczenie systemu i zbudowanie detekcji z dowodem działania'], ['Incident', 'Zbadanie incydentu na dostarczonych danych'], ['Build', 'Zbudowanie narzędzia w Javie lub Pythonie z testami'], ['Architecture', 'Zaprojektowanie rozwiązania i uzasadnienie decyzji'], ['Boss', 'Integracja całego rozdziału bez podpowiedzi, oceniana rubryką']];
for (const [k, d] of labs) add(`Na czym polega laboratorium typu „${k}”?`, d, labs.filter((x) => x[0] !== k).map((x) => x[1]).slice(0, 3), `${k}: ${d.toLowerCase()}.`);
const cyc = ['UNDERSTAND', 'BUILD', 'OBSERVE', 'BREAK', 'ATTACK', 'FIX', 'PROVE'];
for (let i = 0; i < cyc.length - 1; i++) add(`Który krok cyklu nauki następuje po „${cyc[i]}”?`, cyc[i + 1], cyc.filter((x, j) => j !== i + 1 && j !== i).slice(0, 3), `Cykl: ${cyc.join(' → ')}.`);
add('Który krok cyklu nauki kończy pracę nad kompetencją dowodem (test, alert, raport)?', 'PROVE', ['FIX', 'OBSERVE', 'BUILD'], 'PROVE: udowodnij, że zabezpieczenie lub detekcja działa.');
const roles = [['Detection Engineer', 'Projektowanie, testowanie i utrzymanie reguł wykrywania jako kodu'], ['AppSec Engineer', 'Bezpieczeństwo kodu i aplikacji: przeglądy, modelowanie zagrożeń, SAST/DAST'], ['Analityk SOC', 'Triage i analiza alertów, eskalacja incydentów'], ['Incident Responder', 'Prowadzenie reakcji na incydent: ograniczenie, usunięcie, odtworzenie'], ['Threat Hunter', 'Proaktywne szukanie zagrożeń, których nie wykryły reguły'], ['Cloud Security Engineer', 'Zabezpieczanie IAM, sieci i usług chmurowych oraz ich logów']];
for (const [r, d] of roles) add(`Czym zajmuje się ${r}?`, d, roles.filter((x) => x[0] !== r).map((x) => x[1]).slice(0, 3), `${r}: ${d.toLowerCase()}.`);
add('Ile bloków teorii, zadań i pytań ma standardowa lekcja toru?', '14 bloków, 30 zadań, 100 pytań', ['7 bloków, 10 zadań, 20 pytań', '20 bloków, 50 zadań, 50 pytań', '10 bloków, 30 zadań, 30 pytań'], 'Stały format ułatwia naukę i powtórki.');
add('Czym jest lekcja „Boss”?', 'Lekcją zamykającą rozdział, integrującą jego umiejętności bez podpowiedzi', ['Lekcją dla kierowników zespołów', 'Egzaminem certyfikacyjnym', 'Lekcją z grą komputerową'], 'Boss ma artefakty oceniane rubryką.');
add('Na czym polega ograniczenie dotyczące ataków w tym torze?', 'Ataki odtwarza się wyłącznie we własnym, izolowanym labie i tylko po to, by je wykryć i zatrzymać', ['Ataków nie ma w ogóle', 'Ataki można testować na dowolnych systemach w Internecie', 'Ataki wykonuje się na systemach pracodawcy bez zgody'], 'Tor ma profil blue team.');
add('Co oznacza termin „blue team”?', 'Zespół obrońców: wykrywanie, reagowanie, zabezpieczanie systemów', ['Zespół testujący ataki na zlecenie', 'Dział sprzedaży rozwiązań bezpieczeństwa', 'Zespół projektujący interfejsy'], 'Red team atakuje kontrolowanie, purple team łączy obie strony.');
add('Czym jest purple teaming?', 'Współpracą red i blue team w celu sprawdzenia i poprawy detekcji oraz zabezpieczeń', ['Atakiem bez zgody', 'Nazwą certyfikatu', 'Rodzajem zapory sieciowej'], 'Rozdział 37.');
add('Dlaczego część I (sieci) jest fundamentem dla wszystkich ról bezpieczeństwa?', 'Bo alerty, logi i incydenty sprowadzają się do tego, kto z kim komunikował się w sieci', ['Bo egzaminy wymagają tylko sieci', 'Bo bezpieczeństwo dotyczy wyłącznie routerów', 'Bo bez niej nie da się zainstalować Javy'], 'Analiza ruchu, logów i architektury wymaga rozumienia sieci.');
add('Który certyfikat CompTIA w wersji V8 obowiązuje od 17 listopada 2026?', 'Security+ SY0-801', ['Network+ N10-008', 'CySA+ CS0-002', 'Security+ SY0-601'], 'SY0-701 można zdawać do 11 czerwca 2027 r. (wersja angielska) — sprawdź aktualne daty u wydawcy.');
add('Który egzamin CompTIA jest najbliżej pracy analityka SOC?', 'CySA+ (CS0-004)', ['A+', 'Linux+', 'Project+'], 'Domeny: operacje bezpieczeństwa, zarządzanie podatnościami, reagowanie, raportowanie.');
add('Który egzamin praktyczny blue team polega na śledztwie w środowisku z narzędziami?', 'BTL1 (Blue Team Level 1)', ['Network+', 'CCNA', 'ITIL Foundation'], 'Egzaminy praktyczne sprawdzają umiejętności analizy, nie tylko wiedzę.');
add('Od kiedy obowiązuje CCNA 200-301 v2.0 z nową domeną AI i operacji sieci?', 'Od 3 lutego 2027', ['Od 2020', 'Od 17 listopada 2026', 'Od 2030'], 'Wersję v1.1 można zdawać do 2 lutego 2027 — sprawdź u Cisco.');
add('Jaka jest najlepsza metoda dokumentowania nauki w tym torze?', 'Dziennik laboratoryjny w repozytorium Git z notatkami i artefaktami', ['Zrzuty ekranu na pulpicie', 'Brak dokumentacji — wiedza zostaje w głowie', 'Publikowanie plików pcap w repozytorium publicznym'], 'Dziennik staje się portfolio; pcap i klucze nie trafiają do repo.');
add('Dlaczego pliki pcap nie powinny trafiać do publicznego repozytorium z dziennikiem?', 'Mogą zawierać dane wrażliwe (hasła, tokeny, dane osobowe)', ['Bo Git nie obsługuje plików binarnych', 'Bo są zbyt małe', 'Bo Wireshark tego zabrania'], 'Do repo trafiają opisy i sumy SHA-256.');
add('Która przewaga Java developera jest szczególnie cenna w roli detection engineera?', 'Praca z kodem, testami, CI i automatyzacją — detekcje to „kod”', ['Znajomość grafiki', 'Szybkie pisanie na klawiaturze', 'Znajomość arkuszy kalkulacyjnych'], 'Detection-as-code to praktyka inżynierska.');
add('Jak najlepiej łączyć quizy i laboratoria?', 'Najpierw lab i rozumienie, potem quiz i powtórki rozłożone w czasie', ['Tylko quizy do skutku', 'Tylko filmy', 'Laboratoria dopiero po certyfikacie'], 'Quiz sprawdza pamięć, lab umiejętność.');
add('Co oznacza dla obrońcy etap OBSERVE?', 'Poznanie normalnego zachowania systemu (baseline) w pakietach, logach i metrykach', ['Atak na system', 'Usunięcie logów', 'Zakup narzędzi'], 'Bez baseline nie da się rozpoznać anomalii.');
add('W której części toru znajdziesz automatyzację sieci (Ansible, NETCONF) i AI w operacjach sieci?', 'Części II — rozdział 14', ['Części I — rozdział 01', 'Części III — rozdział 39', 'Nigdzie'], 'Odpowiada nowej domenie CCNA v2.0.');
add('W którym rozdziale toru znajdziesz OWASP Top 10:2025?', chLabel(23), [chLabel(21), chLabel(26), chLabel(15)], 'Rozdział 23 jest zbudowany wokół OWASP Top 10:2025, API Security Top 10 i ASVS 5.0.');
add('W którym rozdziale toru są obowiązki wynikające z KSC/NIS2 i DORA?', chLabel(38), [chLabel(33), chLabel(15), chLabel(24)], 'Rozdział 38 (GRC); obowiązki zgłoszeniowe pojawiają się też w rozdziale 33.');
const bosses = plan.map((c, ci) => [c[3][c[3].length - 1], ci]).filter(([l, ci]) => /Boss/.test(l[0]) && ci > 1);
for (const [l, ci] of bosses.filter((_, i) => i % 2 === 0).slice(0, 11)) {
  const wrong = [ci + 2, ci - 2, ci + 4, ci - 4, ci + 6, ci - 6].filter((x) => x > 0 && x < nCh && x !== ci).slice(0, 3).map(chLabel);
  add(`Który rozdział zamyka lekcja „${l[1]}”?`, chLabel(ci), wrong, `To Boss rozdziału ${chLabel(ci)}.`);
}
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
