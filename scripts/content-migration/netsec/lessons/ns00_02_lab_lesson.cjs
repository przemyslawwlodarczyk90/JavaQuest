// Generuje lekcje _ns_00_wprowadzenie/02_LabEnvironmentSetup.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_02_lab_lesson.cjs src/main/resources/content/_ns_00_wprowadzenie/02_LabEnvironmentSetup.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

const LAB_ZERO = `# lab-zero/docker-compose.yml
services:
  caddy:
    image: caddy:2
    ports: ["127.0.0.1:8443:443"]          # tylko lokalny komputer
    volumes: ["./config/Caddyfile:/etc/caddy/Caddyfile:ro"]
    networks: [edge, backend]
  app:
    image: javaquest/lab-zero-app:1.0       # Spring Boot 3, GET /api/hello, /actuator/health
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/lab
      SPRING_DATASOURCE_USERNAME: lab
      SPRING_DATASOURCE_PASSWORD_FILE: /run/secrets/db_password
    secrets: [db_password]
    depends_on: { postgres: { condition: service_healthy } }
    networks: [backend]
  postgres:
    image: postgres:17
    environment: { POSTGRES_DB: lab, POSTGRES_USER: lab, POSTGRES_PASSWORD_FILE: /run/secrets/db_password }
    secrets: [db_password]
    healthcheck: { test: ["CMD", "pg_isready", "-U", "lab"], interval: 5s, retries: 10 }
    networks: [backend]                     # brak "ports:" — baza niedostępna z hosta

networks:
  edge: {}
  backend: { internal: true }
secrets:
  db_password: { file: ./secrets/db_password.txt }   # plik w .gitignore, generowany przez reset.sh

# config/Caddyfile
# :443 {
#   tls internal
#   reverse_proxy app:8080
# }`;

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Laboratorium, które możesz bezpiecznie psuć', ...img('ns00-02-lab-architecture.svg', 'Schemat warstw: Windows 11 z przeglądarką, Wiresharkiem, IntelliJ i Docker Desktop; wewnątrz WSL2 z Ubuntu i narzędziami sieciowymi; w nim Docker Engine z siecią edge (Caddy) i wewnętrzną siecią backend (app, postgres). Obok hypervisor z maszynami wirtualnymi: kontroler domeny, stacja Windows z Sysmon, Security Onion lub Wazuh, REMnux lub FLARE.'),
    body:
`Bez laboratorium nauka sieci i bezpieczeństwa zamienia się w czytanie o pływaniu. W tym torze niemal każda lekcja ma część praktyczną: przechwytujesz pakiety, psujesz DNS, odtwarzasz w izolacji atak ARP spoofing, budujesz detekcję w SIEM, badasz artefakty incydentu. Do tego potrzebujesz środowiska, które:
- **uruchamia się jednym poleceniem** i równie łatwo **wraca do stanu początkowego**,
- jest **izolowane** — celowo podatne aplikacje i ruch ćwiczeniowy nie mogą wydostać się do Twojej sieci domowej, firmowej czy Internetu,
- jest **powtarzalne** — to samo na Twoim laptopie, u kolegi i w CI,
- jest **tanie** — działa na zwykłym komputerze developera.

Fundamentem jest **Docker** (na Windows 11 przez **WSL2**) — kontenery i ich sieci wystarczą do całej części I i większości części III. Od rozdziału o Windows i Active Directory dochodzą **maszyny wirtualne** (Hyper-V lub VirtualBox), bo kontroler domeny, Sysmon czy analiza malware wymagają pełnych systemów.

W tej lekcji skonfigurujesz stanowisko, zainstalujesz narzędzia, poznasz anatomię pakietu laboratorium i uruchomisz **lab-zero**: Caddy (reverse proxy z TLS) → Spring Boot → PostgreSQL, sprawdzony skryptem \`check.sh\` i zresetowany \`reset.sh\`.` },

  { type: 'DEFINITION', heading: 'Słownik stanowiska laboratoryjnego', body:
`- **WSL2 (Windows Subsystem for Linux 2)** — lekka maszyna wirtualna z prawdziwym jądrem Linux zintegrowana z Windows. Daje natywne narzędzia (tcpdump, ip, dig) i backend dla Docker Desktop.
- **Tryb sieci WSL2** — domyślnie **NAT** (WSL ma własną podsieć, Windows przekierowuje \`localhost\`); w Windows 11 dostępny tryb **mirrored** (\`networkingMode=mirrored\` w \`.wslconfig\`), w którym WSL widzi te same interfejsy co Windows.
- **Docker Desktop** — Docker Engine w WSL2 plus integracja z Windows (publikowanie portów, GUI). Na Linuksie wystarczy Docker Engine.
- **Docker Compose** — opis wielu usług, sieci, wolumenów i sekretów w jednym pliku YAML; \`docker compose up -d\` / \`down -v\`.
- **Sieć użytkownika Dockera (bridge)** — wirtualny przełącznik z wbudowanym DNS (nazwy usług); **\`internal: true\`** — sieć bez trasy na zewnątrz.
- **Publikowanie portu** — \`ports: ["127.0.0.1:8443:443"]\` mapuje port hosta na port kontenera; bez adresu — na wszystkich interfejsach hosta (0.0.0.0).
- **Hypervisor** — Hyper-V (wbudowany w Windows Pro/Enterprise) lub VirtualBox; maszyny wirtualne z migawkami (snapshots) do szybkiego powrotu.
- **Pakiet labu** — katalog z \`docker-compose.yml\`, \`scenario.json\`, \`reset.sh\`, \`check.sh\`, \`tests/\`, \`expected/\`, \`config/\`, \`README.md\`.
- **Migawka (snapshot)** — zapis stanu maszyny wirtualnej; podstawa bezpiecznej pracy z malware i ćwiczeniami incydentowymi.
- **Celowo podatna aplikacja** — aplikacja z wbudowanymi lukami do ćwiczeń (np. OWASP Juice Shop); uruchamiana wyłącznie lokalnie i w izolacji.` },

  { type: 'ANALOGY', heading: 'Pracownia chemiczna z dygestorium', body:
`Laboratorium bezpieczeństwa przypomina szkolną pracownię chemiczną:
- **Stół laboratoryjny** to Twoje kontenery — eksperymenty robisz na nim, a nie na kuchennym stole (systemie, z którego korzystasz na co dzień).
- **Dygestorium** to izolacja sieci (\`internal: true\`, porty na \`127.0.0.1\`) — opary (ruch ćwiczeniowy, podatne usługi) nie wydostają się do reszty budynku.
- **Instrukcja doświadczenia** to \`README.md\` i \`scenario.json\` — cel, kroki i oczekiwany wynik.
- **Odczynniki w opisanych butelkach** to wersjonowane obrazy (\`postgres:17\`, \`caddy:2\`) — wiesz dokładnie, czego używasz.
- **Sprzątanie stanowiska** to \`reset.sh\` i \`docker compose down -v\` — następne doświadczenie zaczyna się od czystego stołu.
- **Sejf na niebezpieczne substancje** to oddzielne maszyny wirtualne z migawkami do analizy malware — nigdy na stole ogólnym.

Doświadczony chemik nie jest odważny — jest systematyczny. Tak samo dobry obrońca: każdy eksperyment jest powtarzalny, izolowany i posprzątany.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Anatomia pakietu laboratorium', ...img('ns00-02-lab-package.svg', 'Drzewo katalogu lab-zero: docker-compose.yml (usługi, sieci, wolumeny), scenario.json (cel, poziom, kroki, punktacja), reset.sh (powrót do stanu początkowego), check.sh (automatyczne sprawdzenie celu), tests, expected, config i README.md.'),
    body:
`Każdy lab toru ma tę samą strukturę. Najważniejsze pliki to scenariusz i skrypty sprawdzające — dzięki nim wiesz, kiedy zadanie jest naprawdę wykonane:`,
    code:
`// scenario.json
{
  "id": "ns-00-02-lab-zero",
  "title": "lab-zero: proxy, aplikacja i baza",
  "level": "L1",
  "type": "Guided",
  "goal": "Uruchomić lab, zweryfikować działanie i izolację, zresetować środowisko",
  "steps": ["up", "check", "explore", "reset"],
  "checks": [
    { "id": "proxy_tls",   "cmd": "curl -sk https://127.0.0.1:8443/api/hello", "expect": "Hello" },
    { "id": "health",      "cmd": "curl -sk https://127.0.0.1:8443/actuator/health", "expect": "\\"UP\\"" },
    { "id": "db_isolated", "cmd": "nc -z -w 2 127.0.0.1 5432", "expectExit": 1 }
  ],
  "exam": ["N+ 1.0", "Sec+ V8 3.0"],
  "cleanup": "docker compose down -v"
}` },

  { type: 'CODE_BASIC', heading: 'Instalacja: WSL2, Docker i narzędzia', body:
`Polecenia dla Windows 11 (PowerShell jako administrator) i Ubuntu w WSL2. Na natywnym Linuksie pomiń część Windows i zainstaluj Docker Engine z repozytorium Dockera.`,
    code:
`# Windows (PowerShell, administrator)
wsl --install -d Ubuntu-24.04            # WSL2 + Ubuntu; po restarcie utwórz użytkownika
wsl --set-default-version 2
winget install Docker.DockerDesktop      # w ustawieniach: Use WSL 2 based engine + integracja z Ubuntu
winget install WiresharkFoundation.Wireshark   # z Npcap
winget install EclipseAdoptium.Temurin.25.JDK

# %UserProfile%\\.wslconfig (opcjonalnie)
# [wsl2]
# memory=8GB
# networkingMode=mirrored                 # WSL widzi interfejsy Windows (Windows 11 nowszych wersji)

# Ubuntu w WSL2
sudo apt update && sudo apt install -y \\
  iproute2 iputils-ping traceroute mtr-tiny tcpdump tshark dnsutils curl wget openssl \\
  netcat-openbsd nmap iperf3 jq socat conntrack nftables git
sudo usermod -aG wireshark $USER         # przechwytywanie bez roota (dumpcap)
docker version && docker compose version # sprawdzenie integracji z Docker Desktop

# Kontener narzędziowy (gdy nie chcesz instalować wszystkiego lokalnie)
docker run --rm -it nicolaka/netshoot` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab: uruchom, sprawdź i zresetuj lab-zero', ...img('ns00-02-lab-zero.svg', 'Topologia lab-zero: przeglądarka lub curl łączy się z https://127.0.0.1:8443 do Caddy (sieci edge i backend, TLS wewnętrzny), Caddy przekazuje żądania do aplikacji Spring Boot na porcie 8080 w wewnętrznej sieci backend, a aplikacja łączy się z PostgreSQL na 5432 bez portu na hoście.'),
    body:
`1. **Pobierz pakiet** labu do katalogu w systemie plików WSL (\`~/labs/lab-zero\`, nie \`/mnt/c/...\` — dostęp do dysku Windows z WSL jest wielokrotnie wolniejszy).
2. **Wygeneruj sekret** i uruchom środowisko: \`./reset.sh\` (tworzy \`secrets/db_password.txt\`, wykonuje \`docker compose down -v\` i \`up -d\`).
3. **Poczekaj na zdrowie usług**: \`docker compose ps\` — \`postgres\` w stanie *healthy*, \`app\` i \`caddy\` *running*.
4. **Uruchom sprawdzenie**: \`./check.sh\` — trzy testy: proxy odpowiada przez TLS, \`/actuator/health\` zwraca \`UP\`, port 5432 jest **niedostępny** z hosta.
5. **Zbadaj środowisko**: \`docker network ls\`, \`docker network inspect lab-zero_backend\` (kto jest w sieci, czy \`Internal: true\`), \`docker compose exec app getent hosts postgres\` (DNS Dockera), \`curl -vk https://127.0.0.1:8443/api/hello\` (handshake TLS z certyfikatem wewnętrznego CA Caddy).
6. **Sprawdź izolację**: z innego urządzenia w sieci domowej spróbuj otworzyć \`https://<IP-laptopa>:8443\` — powinno się nie udać. Z kontenera aplikacji: \`docker compose exec app curl -sS -m 3 https://example.com\` — też powinno się nie udać (sieć wewnętrzna).
7. **Zresetuj**: zmień coś (np. zatrzymaj bazę), uruchom \`./check.sh\` (test nie przechodzi), potem \`./reset.sh\` i ponownie \`./check.sh\` (przechodzi).
8. **Posprzątaj**: \`docker compose down -v\` usuwa kontenery, sieci i wolumeny labu. Zapisz wyniki w dzienniku laboratoryjnym.` },

  { type: 'NOTE', heading: 'Izolacja labu: porty, sieci wewnętrzne i firewall', ...img('ns00-02-isolation.svg', 'Dwa panele: po lewej ports 8443:443 nasłuchuje na 0.0.0.0, więc zarówno przeglądarka na laptopie, jak i kolega w tym samym Wi-Fi mogą się połączyć; po prawej ports 127.0.0.1:8443:443 nasłuchuje tylko lokalnie i połączenie kolegi jest zablokowane.'),
    body:
`Laboratoria toru zawierają celowo podatne aplikacje i ruch ćwiczeniowy. Trzy zasady izolacji obowiązują zawsze:`,
    code:
`1. Porty na hoście tylko na pętli zwrotnej
   ports: ["127.0.0.1:8443:443"]      ✔   ports: ["8443:443"]   ✘ (0.0.0.0 — cała sieć Wi-Fi)

2. Usługi wewnętrzne bez portów i w sieciach internal
   networks: { backend: { internal: true } }   — brak wyjścia do Internetu i brak dostępu z zewnątrz

3. Nie polegaj na firewallu hosta przy portach Dockera (Linux)
   Docker dodaje własne reguły iptables/nftables PRZED regułami UFW/firewalld,
   więc „ufw deny 8443” NIE zablokuje portu opublikowanego przez kontener.
   Skuteczne: publikowanie na 127.0.0.1, brak publikacji, reguły w łańcuchu DOCKER-USER.

Sprawdzenie, co faktycznie nasłuchuje:
   ss -tlnp | grep 8443                         # Linux/WSL: 127.0.0.1:8443 czy 0.0.0.0:8443?
   Get-NetTCPConnection -LocalPort 8443         # Windows: LocalAddress 127.0.0.1 czy 0.0.0.0
   docker ps --format '{{.Names}} {{.Ports}}'   # 127.0.0.1:8443->443/tcp` },

  { type: 'CODE_PRACTICAL', heading: 'lab-zero: docker-compose.yml i Caddyfile', body:
`Kompletna definicja środowiska. Zwróć uwagę na: publikację tylko na \`127.0.0.1\`, sieć \`backend\` jako wewnętrzną, brak portu bazy, sekret hasła z pliku (a nie w YAML), \`healthcheck\` i \`depends_on\` z warunkiem zdrowia.`,
    code: LAB_ZERO },

  { type: 'USAGE', heading: 'Narzędzia stanowiska i gdzie ich użyjesz', body:
`- **Wireshark / tshark / tcpdump** — analiza pakietów od rozdziału 01; w części III analiza incydentów i NSM.
- **curl, openssl, dig, ip, ss, mtr, nc, iperf3** — codzienna diagnostyka warstw 3–7 (rozdziały 03–09).
- **nmap** — inwentaryzacja *własnych* usług i weryfikacja reguł firewalla w labie (rozdziały 09, 20, 22). Skanowanie cudzych sieci bez zgody jest nielegalne (lekcja 03).
- **OWASP ZAP i Burp Suite Community** — testy bezpieczeństwa *własnej* aplikacji Spring Boot w labie (rozdział 23); w pipeline CI — ZAP baseline (rozdział 24).
- **Postman / Bruno / HTTPie** — praca z API (rozdział 06).
- **JDK 21/25, Maven/Gradle, IntelliJ** — przykłady w Javie w każdym rozdziale.
- **Maszyny wirtualne** — kontroler domeny i stacje Windows z Sysmon (rozdział 18), Security Onion/Wazuh (26, 28), REMnux i FLARE-VM (32), Velociraptor (34).
- **Kontener netshoot** — zestaw narzędzi sieciowych do dołączania do przestrzeni sieci innych kontenerów (\`--net container:\`).

Z perspektywy obrońcy laboratorium to także **trening dyscypliny operacyjnej**: wersjonowane środowisko, sekrety poza repozytorium, minimalna ekspozycja, dokumentacja i sprzątanie. Dokładnie tych nawyków wymaga praca w SOC i zespole IR.` },

  { type: 'PITFALL', heading: 'Najczęstsze problemy ze stanowiskiem', body:
`- **Pliki labu na \`/mnt/c\`** — operacje dyskowe z WSL na dysku Windows są wolne, a uprawnienia plików (\`chmod +x\`) działają inaczej. Trzymaj laby w \`~\` w WSL.
- **„Port is already allocated”** — inny lab lub usługa zajmuje port; sprawdź \`docker ps\`, \`ss -tlnp\`, posprzątaj poprzedni lab (\`down\`).
- **Konflikty podsieci** — sieci Dockera (172.17–172.31, 192.168.x) kolidują z VPN lub siecią firmową; ustaw własne zakresy (\`ipam.config.subnet\`) albo \`default-address-pools\` w daemon.json.
- **Za mało pamięci** — WSL2 domyślnie bierze do połowy RAM; przy labach z wieloma kontenerami i VM ustaw limity w \`.wslconfig\` i zamykaj nieużywane środowiska.
- **Brak uprawnień do przechwytywania** — dodaj użytkownika do grupy \`wireshark\` (po ponownym zalogowaniu), nie uruchamiaj Wiresharka jako root.
- **Porty opublikowane na 0.0.0.0** — Twój podatny lab widzą wszyscy w sieci hotelowej. Zawsze \`127.0.0.1:\`.
- **Wiara w UFW przy Dockerze** — na Linuksie reguły UFW nie chronią portów publikowanych przez kontenery.
- **Sekrety w repozytorium** — hasło w \`docker-compose.yml\` trafia do historii Git. Używaj plików sekretów w \`.gitignore\` lub zmiennych z \`.env\` (też w \`.gitignore\`).
- **Tag „latest”** — obraz zmienia się bez ostrzeżenia, lab przestaje być powtarzalny. Używaj konkretnych wersji (\`postgres:17\`), a w CI — skrótów (\`@sha256:…\`).` },

  { type: 'CODE_WRONG', heading: 'Źle: wygodny, ale niebezpieczny compose', body:
`Wszystko na wszystkich interfejsach, hasła w pliku, najnowsze obrazy, celowo podatna aplikacja dostępna z sieci.`,
    code:
`services:
  juice-shop:
    image: bkimminich/juice-shop:latest     # celowo podatna aplikacja
    ports: ["3000:3000"]                     # 0.0.0.0:3000 — każdy w Wi-Fi może ją „zhakować”…
  postgres:                                  # …a potem dostać się do Twoich innych usług
    image: postgres:latest
    environment: { POSTGRES_PASSWORD: postgres }   # hasło w repo
    ports: ["5432:5432"]                     # baza otwarta dla sieci
# „Mam włączony UFW, więc jest bezpiecznie” — porty Dockera i tak go omijają` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: lokalnie, wersjonowanie, bez sekretów w pliku', body:
`Ta sama funkcjonalność, ale z minimalną ekspozycją i powtarzalnością. Dodatkowo reguła w łańcuchu \`DOCKER-USER\` jako zabezpieczenie na wypadek pomyłki w publikacji portu.`,
    code:
`services:
  juice-shop:
    image: bkimminich/juice-shop:v17.3.0     # konkretna wersja (przykładowa)
    ports: ["127.0.0.1:3000:3000"]          # tylko lokalnie
    networks: [lab]
  postgres:
    image: postgres:17
    environment: { POSTGRES_PASSWORD_FILE: /run/secrets/pg }
    secrets: [pg]
    networks: [lab]                          # bez ports:
networks:
  lab: { internal: false }                   # juice-shop potrzebuje tylko lokalnego dostępu
secrets:
  pg: { file: ./secrets/pg.txt }             # .gitignore: secrets/

# Linux: zabezpieczenie „pasa i szelek” — odrzuć ruch spoza hosta do kontenerów
# (interfejs eth0 = wyjście do sieci LAN; dopasuj nazwę)
sudo iptables -I DOCKER-USER -i eth0 ! -s 127.0.0.1 -j DROP` },

  { type: 'WHEN_TO_USE', heading: 'Kontener, maszyna wirtualna czy chmura?', body:
`- **Kontener (Docker)** — sieci, usługi, aplikacje Java, proxy, bazy, większość ćwiczeń z ruchem i logami. Szybki start, mały koszt, łatwy reset. Domyślny wybór toru.
- **Maszyna wirtualna** — gdy potrzebujesz pełnego systemu: Windows i Active Directory, EDR i Sysmon, sensor NSM z kilkoma interfejsami, analiza malware (izolacja na poziomie hypervisora + migawki), laboratoria z jądrem (eBPF, moduły).
- **Containerlab** — topologie z routerami i przełącznikami (FRR, cEOS) w rozdziałach 02, 03, 13, 14.
- **Chmura (konto testowe)** — laboratoria AWS/Azure (rozdziały 10, 16, 25): zawsze z budżetem i alertem kosztowym, bez kluczy w repozytorium, z porządkiem po ćwiczeniu (koszty i ekspozycja).
- **Gotowe platformy treningowe** (CyberDefenders, Blue Team Labs Online, TryHackMe) — ćwiczenia śledcze na przygotowanych danych, dobre przed egzaminami praktycznymi.
- **Nigdy**: produkcja, sieć firmowa bez zgody, cudze systemy — nawet „tylko do sprawdzenia”.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Stanowisko: Windows 11 + WSL2 + Docker (lub natywny Linux); od rozdziału 18 także maszyny wirtualne.
- Narzędzia: Wireshark/tshark/tcpdump, curl, openssl, dig, ip, ss, mtr, nc, iperf3, nmap (tylko własny lab), ZAP/Burp (własna aplikacja), JDK i IntelliJ.
- Pakiet labu: compose, scenariusz, \`reset.sh\`, \`check.sh\`, testy, wzorce, konfiguracja, README — uruchamiasz, sprawdzasz i resetujesz jednym poleceniem.
- Izolacja: porty tylko na \`127.0.0.1\`, usługi wewnętrzne w sieciach \`internal\`, sekrety poza repozytorium, konkretne wersje obrazów; na Linuksie porty Dockera omijają UFW.
- lab-zero: Caddy (TLS) → Spring Boot → PostgreSQL; \`check.sh\` potwierdza działanie i izolację.
- Następna lekcja: **etyka, zakres i prawo** — gdzie kończy się ćwiczenie, a zaczyna przestępstwo.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const tasks = [
  ['Zainstaluj WSL2 z Ubuntu i Docker Desktop z backendem WSL2. Pokaż wynik wsl -l -v oraz docker version w Ubuntu.', 'wsl -l -v powinno pokazać VERSION 2.', 'Przykład: „Ubuntu-24.04  Running  2”; docker version pokazuje Client i Server (Docker Desktop). Jeśli brak serwera — włącz integrację WSL w ustawieniach Docker Desktop (Resources → WSL Integration).'],
  ['Zainstaluj zestaw narzędzi z lekcji i sprawdź wersje: tcpdump --version, tshark -v, dig -v, openssl version, nmap --version, iperf3 --version.', 'Pakiety: tcpdump tshark dnsutils openssl nmap iperf3.', 'Każde polecenie powinno wypisać wersję. Brak dig oznacza brak pakietu dnsutils (Debian/Ubuntu) lub bind-utils (Fedora/RHEL).'],
  ['Skonfiguruj przechwytywanie bez roota w WSL i sprawdź, że tcpdump -i eth0 -c 3 działa bez sudo (lub z dumpcap).', 'Grupa wireshark i ponowne zalogowanie (wsl --shutdown).', 'sudo usermod -aG wireshark $USER; wsl --shutdown (z Windows); po ponownym uruchomieniu: groups pokazuje wireshark; dumpcap -i eth0 -c 3 -w /tmp/t.pcapng działa bez sudo. tcpdump bez sudo wymaga capabilities (setcap cap_net_raw,cap_net_admin=eip $(which tcpdump)).'],
  ['Uruchom lab-zero i check.sh. Zapisz wynik każdego z trzech testów i wyjaśnij, co sprawdza test db_isolated.', 'Test oczekuje, że połączenie z 5432 NIE powiedzie się.', 'proxy_tls: OK (odpowiedź Hello przez TLS), health: OK ("status":"UP"), db_isolated: OK — nc -z 127.0.0.1 5432 kończy się kodem 1, bo baza nie publikuje portu. Ten test chroni przed przypadkowym wystawieniem bazy.'],
  ['Sprawdź, na jakim adresie nasłuchuje opublikowany port lab-zero (ss -tlnp w WSL i Get-NetTCPConnection w Windows). Zmień publikację na "8443:443" i porównaj.', 'Zwróć uwagę na LocalAddress.', 'Z "127.0.0.1:8443:443": 127.0.0.1:8443. Z "8443:443": 0.0.0.0:8443 (i [::]:8443) — port dostępny dla innych urządzeń w sieci. Przywróć wersję z 127.0.0.1.'],
  ['Z kontenera app spróbuj połączyć się z example.com i z postgres:5432. Wyjaśnij wyniki na podstawie konfiguracji sieci.', 'Sieć backend ma internal: true.', 'example.com: brak połączenia (sieć wewnętrzna nie ma trasy na zewnątrz; często także błąd DNS dla nazw zewnętrznych). postgres:5432: działa — oba kontenery są w sieci backend, a wbudowany DNS Dockera rozwiązuje nazwę usługi.'],
  ['Wyświetl szczegóły sieci lab-zero_backend (docker network inspect) i wskaż: podsieć, bramę, pole Internal i listę kontenerów.', 'Szukaj kluczy IPAM.Config, Internal i Containers.', 'Przykład: Subnet 172.20.0.0/16, Gateway 172.20.0.1, "Internal": true, Containers: caddy, app, postgres z adresami IP. Brama istnieje na hoście, ale reguły nie przepuszczają ruchu na zewnątrz.'],
  ['Zepsuj lab: zatrzymaj kontener postgres i uruchom check.sh. Który test nie przechodzi i dlaczego? Przywróć środowisko reset.sh.', 'Health aplikacji Spring Boot zależy od bazy.', 'Test health zwróci DOWN lub 503 (wskaźnik db w Actuatorze), proxy_tls może nadal działać dla /api/hello, jeśli endpoint nie używa bazy. reset.sh przywraca stan — ponowne check.sh przechodzi.'],
  ['Napisz własny prosty check.sh dla lab-zero, który kończy się kodem 0 tylko wtedy, gdy wszystkie testy przechodzą, i wypisuje czytelny raport.', 'Użyj set -u, funkcji pomocniczej i licznika błędów.', '#!/usr/bin/env bash\nset -u\nfail=0\ncheck() { local name=$1; shift; if "$@" >/dev/null 2>&1; then echo "OK   $name"; else echo "FAIL $name"; fail=1; fi; }\ncheck proxy_tls curl -sfk https://127.0.0.1:8443/api/hello\ncheck health sh -c "curl -sk https://127.0.0.1:8443/actuator/health | grep -q UP"\ncheck db_isolated sh -c "! nc -z -w 2 127.0.0.1 5432"\nexit $fail'],
  ['Przygotuj reset.sh, który: generuje losowe hasło do secrets/db_password.txt (jeśli nie istnieje), usuwa poprzednie środowisko z wolumenami i uruchamia je ponownie, czekając na zdrowie bazy.', 'openssl rand, docker compose down -v, up -d --wait.', '#!/usr/bin/env bash\nset -euo pipefail\ncd "$(dirname "$0")"\nmkdir -p secrets\n[ -f secrets/db_password.txt ] || openssl rand -base64 24 > secrets/db_password.txt\nchmod 600 secrets/db_password.txt\ndocker compose down -v --remove-orphans\ndocker compose up -d --wait   # czeka na healthcheck\ndocker compose ps'],
  ['Sprawdź, co pokazuje certyfikat TLS lab-zero (openssl s_client) i dlaczego curl wymaga -k. Jak sprawić, by curl ufał certyfikatowi bez -k?', 'Caddy z „tls internal” wystawia certyfikat z własnego, lokalnego CA.', 'openssl s_client -connect 127.0.0.1:8443 -servername localhost pokazuje łańcuch z wystawcą „Caddy Local Authority”. curl nie zna tego CA, więc bez -k odrzuca połączenie. Rozwiązanie: wyeksportuj certyfikat główny z kontenera (/data/caddy/pki/authorities/local/root.crt) i użyj curl --cacert root.crt (lub dodaj go do zaufanych w systemie labu — nie na stałe w przeglądarce codziennego użytku).'],
  ['Skonfiguruj .wslconfig z limitem pamięci i (jeśli Twój Windows to wspiera) trybem mirrored. Porównaj adres IP w WSL przed i po.', 'wsl --shutdown po zmianie konfiguracji.', 'Tryb NAT: eth0 w WSL ma adres z osobnej podsieci (np. 172.2x.x.x), inny niż Windows. Tryb mirrored: interfejsy WSL odzwierciedlają karty Windows (ten sam adres w sieci domowej). Limit pamięci sprawdzisz free -h.'],
  ['Zmień podsieci sieci Dockera labu tak, by nie kolidowały z siecią VPN firmy (np. firma używa 172.16.0.0/12). Pokaż konfigurację.', 'networks: nazwa: ipam: config: - subnet.', 'networks:\n  edge: { ipam: { config: [{ subnet: 10.250.1.0/24 }] } }\n  backend: { internal: true, ipam: { config: [{ subnet: 10.250.2.0/24 }] } }\nGlobalnie: /etc/docker/daemon.json (lub ustawienia Docker Desktop) — "default-address-pools": [{"base":"10.250.0.0/16","size":24}].'],
  ['Na Linuksie (VM lub natywnie) z włączonym UFW (deny incoming) opublikuj port kontenera "8080:80" i sprawdź z innego hosta, czy port jest dostępny. Wyjaśnij wynik i napraw.', 'Docker modyfikuje iptables/nftables przed regułami UFW.', 'Port jest dostępny mimo UFW: reguły NAT/forward Dockera omijają łańcuchy UFW (ruch do kontenera idzie przez FORWARD, nie INPUT). Naprawa: publikuj na 127.0.0.1, albo dodaj regułę w łańcuchu DOCKER-USER (np. -i eth0 -j DROP z wyjątkami), albo nie publikuj portu.'],
  ['Utwórz migawkę maszyny wirtualnej (Hyper-V lub VirtualBox) przed instalacją oprogramowania, zainstaluj coś, a potem przywróć migawkę. Do czego posłuży to w rozdziale o malware?', 'Checkpoint w Hyper-V, Snapshot w VirtualBox.', 'Po przywróceniu VM wraca do stanu sprzed instalacji. W analizie malware: przed detonacją próbki robisz migawkę czystego systemu; po analizie przywracasz ją, mając pewność, że nie zostały żadne zmiany ani trwałość malware.'],
  ['Sprawdź, ile zasobów zużywa lab-zero (docker stats) i oszacuj, ile takich labów uruchomisz jednocześnie na swoim komputerze.', 'Najwięcej pamięci zużyje JVM.', 'Typowo: app (JVM) 250–400 MB, postgres 30–80 MB, caddy 15–30 MB. Przy 8 GB dla WSL kilka labów naraz jest możliwe, ale lepiej uruchamiać jeden i sprzątać. Pamięć JVM w kontenerze można ograniczyć: JAVA_TOOL_OPTIONS=-XX:MaxRAMPercentage=50 i limit mem_limit/deploy.resources.'],
  ['Dodaj do lab-zero kontener netshoot dołączony do przestrzeni sieci aplikacji i przechwyć ruch między app a postgres. Co widzisz?', 'docker run --rm -it --net container:<app> nicolaka/netshoot tcpdump -i eth0 -nn port 5432', 'Widać połączenie JDBC aplikacji z bazą: handshake TCP, komunikaty protokołu PostgreSQL. Jeśli połączenie nie używa TLS (domyślnie w labie), zapytania SQL są czytelne — ćwiczenie pokazuje, dlaczego produkcyjne połączenia do bazy powinny być szyfrowane (sslmode=verify-full).'],
  ['Przejrzyj docker-compose.yml lab-zero i wypisz wszystkie decyzje bezpieczeństwa (co najmniej 6) z uzasadnieniem.', 'Szukaj: porty, sieci, sekrety, wersje, healthcheck, uprawnienia.', '1) port tylko na 127.0.0.1; 2) brak portu bazy; 3) sieć backend internal — brak wyjścia do Internetu; 4) hasło z pliku sekretu, nie w YAML; 5) konkretne wersje obrazów (postgres:17, caddy:2 — w CI warto przypiąć skrótem); 6) Caddyfile tylko do odczytu (:ro); 7) TLS na proxy; 8) zależności z warunkiem zdrowia (mniej niejasnych stanów). Do poprawy: użytkownik nie-root i read_only dla app, limity zasobów.'],
  ['Utwórz w repozytorium dziennika katalog lab-zero z NOTES.md według szablonu z lekcji 01 i opisz w nim przebieg tego laboratorium.', 'Sekcje: cel, środowisko, kroki, obserwacje, wnioski, mapowanie na egzamin.', 'NOTES.md powinien zawierać wersje (Docker, WSL, obrazy), polecenia z wynikami (np. ss -tlnp, docker network inspect), obserwacje izolacji, wnioski (np. „UFW nie chroni portów Dockera”) i mapowanie (Network+ 1.x, Security+ V8 3.x — architektura).'],
  ['Wyjaśnij, dlaczego laby trzymane na /mnt/c w WSL działają wolniej i mają problemy z uprawnieniami. Gdzie je przenieść?', 'System plików Windows jest udostępniany do WSL przez protokół 9P.', 'Dostęp WSL2 do plików Windows (/mnt/c) przechodzi przez warstwę udostępniania (9P), co spowalnia operacje na wielu małych plikach (Maven, npm, Docker build) i nie wspiera w pełni uprawnień POSIX (bit wykonania, właściciel). Laby i repozytoria trzymaj w ~ (ext4 w WSL); z Windows są dostępne pod \\\\wsl$\\Ubuntu\\home\\<user>.'],
];
for (const [q, h, a] of tasks) ex(q, h, a);
const more = [
  ['Uruchom OWASP ZAP baseline przeciwko lab-zero (tylko lokalnie) i przejrzyj ostrzeżenia. Które dotyczą nagłówków bezpieczeństwa?', 'docker run --rm --network host ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t https://127.0.0.1:8443 -I', 'Typowe ostrzeżenia: brak Content-Security-Policy, X-Content-Type-Options, Strict-Transport-Security, ewentualnie ujawnienie wersji serwera. Wróci to w rozdziale 23 (A02 Security Misconfiguration). Uruchamiaj ZAP wyłącznie wobec własnych aplikacji.'],
  ['Zinwentaryzuj nmapem porty na własnym laptopie (127.0.0.1) przy działającym lab-zero. Które porty są otwarte i czy wszystkie znasz?', 'nmap -sT -p- 127.0.0.1 (pełny skan TCP własnego hosta).', 'Powinien pojawić się 8443 (lab) oraz porty innych lokalnych usług (np. IDE, Docker Desktop, bazy deweloperskie). Każdy nieznany port warto zidentyfikować (ss -tlnp / Get-NetTCPConnection z OwningProcess). To pierwszy krok inwentaryzacji powierzchni ataku.'],
  ['Zaprojektuj strukturę katalogów ~/labs na cały tor (np. per rozdział) i skrypt, który wypisuje, które laby mają uruchomione kontenery.', 'docker compose ls pokazuje uruchomione projekty.', 'Przykład: ~/labs/<rozdział>-<lekcja>/ z pakietami; skrypt: docker compose ls --format json | jq -r \'.[] | "\\(.Name) \\(.Status)"\' — przypomnienie, by sprzątać laby po ćwiczeniach i nie zostawiać podatnych usług działających w tle.'],
  ['Na podstawie lekcji przygotuj własną „checklistę bezpieczeństwa labu” (8–10 punktów), którą będziesz stosować w każdym kolejnym laboratorium.', 'Pomyśl o portach, sieciach, sekretach, wersjach, danych, sprzątaniu.', 'Przykład: porty tylko 127.0.0.1; usługi wewnętrzne bez portów; sieci internal tam, gdzie możliwe; sekrety w plikach poza repo; wersje obrazów przypięte; brak prawdziwych danych osobowych (dane syntetyczne); pliki pcap/logi poza repo; migawki VM przed ryzykownymi ćwiczeniami; sprzątanie po labie; ćwiczenia ofensywne tylko wewnątrz labu.'],
  ['Wyjaśnij różnicę między docker compose down a docker compose down -v. Kiedy używać każdego?', 'Wolumeny przechowują dane baz.', 'down: zatrzymuje i usuwa kontenery i sieci, zostawia nazwane wolumeny (dane bazy przetrwają). down -v: usuwa także wolumeny — pełny reset danych. W labach (reset do stanu początkowego) — down -v; gdy chcesz zachować dane między sesjami — down.'],
  ['Zastanów się, jakie dane mogą znaleźć się w Twoim labie (logi, pcap, zrzuty bazy) i dlaczego w labach używa się danych syntetycznych zamiast kopii produkcji.', 'RODO, tajemnica przedsiębiorstwa, ryzyko wycieku.', 'Kopie produkcyjne zawierają dane osobowe i poufne; przeniesienie ich do labu (laptop, repozytorium, pcap) tworzy niekontrolowaną kopię i ryzyko naruszenia RODO. Dane syntetyczne (generowane, np. Faker/Datafaker w Javie) dają ten sam efekt dydaktyczny bez ryzyka.'],
  ['Sprawdź, jakie domyślne pule adresów Docker przydziela nowym sieciom i jak to może kolidować z typowymi sieciami domowymi i firmowymi.', 'docker network inspect bridge; docker network create test && docker network inspect test.', 'Domyślnie: bridge 172.17.0.0/16, kolejne sieci 172.18–172.31.0.0/16, potem 192.168.x.0/20. Kolizje: firmy często używają 172.16.0.0/12 lub 192.168.0.0/16 — trasy VPN mogą „przykryć” sieci Dockera lub odwrotnie. Rozwiązanie: default-address-pools z rzadko używanego zakresu (np. 10.250.0.0/16).'],
  ['Opisz, jak bezpiecznie uruchomić w labie celowo podatną aplikację (np. OWASP Juice Shop) do ćwiczeń z rozdziału 23.', 'Port, sieć, wersja, dane, czas działania.', 'Konkretna wersja obrazu; port tylko 127.0.0.1; osobna sieć bez dostępu do innych usług labu; uruchamianie na czas ćwiczenia i usuwanie po nim (docker compose down -v); brak montowania katalogów hosta; brak prawdziwych danych; ruch testowy wyłącznie z narzędzi na tym samym komputerze.'],
  ['Zaplanuj zasoby sprzętowe na część III toru: ile RAM i dysku potrzebuje jednoczesne uruchomienie kontrolera domeny, stacji Windows, sensora Security Onion (eval) i Wazuh?', 'Sprawdź minimalne wymagania w dokumentacji narzędzi.', 'Orientacyjnie: DC 2–4 GB RAM, stacja Windows 4 GB, Security Onion eval 12–16 GB (wymagający), Wazuh all-in-one 4–8 GB → razem 24–32 GB RAM i 200+ GB dysku SSD. Przy 16 GB: uruchamiaj komponenty etapami lub użyj lżejszego stosu (Wazuh + Sysmon zamiast pełnego Security Onion).'],
  ['Udokumentuj w dzienniku wersje wszystkich składników stanowiska (Windows, WSL, jądro, Docker, Compose, JDK, Wireshark). Dlaczego jest to ważne przy zgłaszaniu problemów i w pracy IR?', 'wsl --version, uname -r, docker version, java -version.', 'Wersje determinują zachowanie (np. tryb mirrored WSL, funkcje Compose, domyślne algorytmy TLS w JDK). Przy zgłaszaniu problemu bez wersji nie da się go odtworzyć. W IR dokumentowanie wersji narzędzi użytych do zbierania dowodów jest wymagane dla wiarygodności i powtarzalności analizy.'],
];
for (const [q, h, a] of more) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111402);
const tools = [['tcpdump', 'Przechwytywanie pakietów w wierszu poleceń'], ['Wireshark', 'Graficzna analiza przechwyconych pakietów'], ['dig', 'Zapytania DNS i analiza odpowiedzi'], ['openssl s_client', 'Diagnostyka połączenia TLS i certyfikatów'], ['mtr', 'Ciągły pomiar opóźnień i strat na trasie'], ['iperf3', 'Pomiar przepływności między hostami'], ['nc (netcat)', 'Sprawdzanie, czy port TCP/UDP przyjmuje połączenia'], ['ss', 'Wyświetlanie gniazd i nasłuchujących portów'], ['nmap', 'Inwentaryzacja usług i portów własnej infrastruktury'], ['OWASP ZAP', 'Dynamiczne testy bezpieczeństwa własnej aplikacji webowej'], ['conntrack', 'Podgląd tablicy śledzenia połączeń i NAT'], ['jq', 'Przetwarzanie danych JSON w wierszu poleceń']];
const toolAll = tools.map((t) => t[1]);
for (const [t, d] of tools) add(`Do czego służy w laboratorium narzędzie ${t}?`, d, toolAll.filter((x) => x !== d).slice(0, 3), `${t}: ${d.toLowerCase()}.`);
const pub = [['"8443:443"', 'Na wszystkich interfejsach hosta (0.0.0.0) — także z sieci LAN'], ['"127.0.0.1:8443:443"', 'Tylko na pętli zwrotnej — wyłącznie z tego komputera'], ['brak sekcji ports', 'Port nie jest publikowany na hoście — dostępny tylko z sieci Dockera'], ['"0.0.0.0:8443:443"', 'Jawnie na wszystkich interfejsach IPv4 hosta']];
const pubAll = pub.map((x) => x[1]);
for (const [p, d] of pub) add(`Gdzie będzie dostępna usługa przy publikacji portu ${p}?`, d, pubAll.filter((x) => x !== d), d + '.');
const comp = [['internal: true', 'Sieć bez trasy na zewnątrz i bez dostępu z zewnątrz'], ['depends_on z condition: service_healthy', 'Uruchomienie usługi dopiero po pozytywnym healthchecku zależności'], ['secrets', 'Przekazanie sekretu jako pliku zamiast zmiennej w YAML'], ['healthcheck', 'Okresowe sprawdzanie, czy usługa w kontenerze działa poprawnie'], [':ro przy wolumenie', 'Montowanie pliku lub katalogu tylko do odczytu'], ['docker compose down -v', 'Usunięcie kontenerów, sieci i wolumenów projektu'], ['docker compose up -d --wait', 'Uruchomienie w tle z oczekiwaniem na zdrowie usług'], ['ipam.config.subnet', 'Ustawienie własnej podsieci dla sieci Dockera']];
const compAll = comp.map((x) => x[1]);
for (const [c, d] of comp) add(`Co oznacza w Docker Compose: ${c}?`, d, compAll.filter((x) => x !== d).slice(0, 3), `${c}: ${d.toLowerCase()}.`);
const files = [['scenario.json', 'Cel, poziom, kroki i automatyczne testy laboratorium'], ['reset.sh', 'Przywrócenie środowiska do stanu początkowego'], ['check.sh', 'Automatyczne sprawdzenie, czy cel laboratorium został osiągnięty'], ['expected/', 'Wzorcowe wyniki do porównania'], ['README.md', 'Instrukcja, wymagania i zasady sprzątania']];
const fileAll = files.map((x) => x[1]);
for (const [f, d] of files) add(`Jaką rolę pełni w pakiecie labu ${f}?`, d, fileAll.filter((x) => x !== d).slice(0, 3), `${f}: ${d.toLowerCase()}.`);
const facts = [
  ['Czym jest WSL2?', 'Lekką maszyną wirtualną z prawdziwym jądrem Linux zintegrowaną z Windows', ['Emulatorem wywołań Linuksa bez jądra', 'Kontenerem Dockera', 'Przeglądarką plików Linux'], 'WSL2 uruchamia jądro Linux w lekkiej VM (Hyper-V).'],
  ['Co daje tryb networkingMode=mirrored w WSL2?', 'WSL widzi te same interfejsy sieciowe co Windows', ['Wyłącza sieć w WSL', 'Szyfruje ruch WSL', 'Zastępuje Docker Desktop'], 'Domyślny tryb to NAT z osobną podsiecią.'],
  ['Dlaczego laby lepiej trzymać w systemie plików WSL (~), a nie na /mnt/c?', 'Dostęp z WSL do dysku Windows jest wolny i nie wspiera w pełni uprawnień POSIX', ['Bo /mnt/c jest tylko do odczytu', 'Bo Docker nie widzi /mnt/c', 'Bo Windows usuwa pliki z /mnt/c'], 'Pliki Windows są udostępniane przez warstwę 9P.'],
  ['Dlaczego na Linuksie reguła UFW „deny” może nie chronić portu opublikowanego przez Dockera?', 'Docker dodaje własne reguły iptables/nftables, przez które ruch do kontenerów omija łańcuchy UFW', ['Bo UFW nie działa z IPv4', 'Bo Docker wyłącza UFW przy starcie', 'Bo kontenery nie używają sieci hosta'], 'Skuteczne: publikacja na 127.0.0.1 lub reguły w DOCKER-USER.'],
  ['Do czego służy łańcuch iptables DOCKER-USER?', 'Do własnych reguł filtrujących ruch do kontenerów, przetwarzanych przed regułami Dockera', ['Do logowania użytkowników Dockera', 'Do konfiguracji DNS', 'Do przechowywania haseł'], 'Docker nie nadpisuje tego łańcucha przy restarcie.'],
  ['Dlaczego w labach unika się tagu „latest”?', 'Obraz może się zmienić bez ostrzeżenia, co psuje powtarzalność', ['Bo latest jest zawsze podatny', 'Bo Docker nie pozwala go używać', 'Bo jest wolniejszy'], 'Przypinaj wersje, a w CI — skróty sha256.'],
  ['Jakie minimum pamięci RAM zalecamy dla części III toru z maszynami wirtualnymi?', '16 GB (wygodnie 32 GB)', ['4 GB', '2 GB', '256 GB'], 'Kontroler domeny, stacje Windows i SIEM zużywają dużo pamięci.'],
  ['Kiedy zamiast kontenera potrzebna jest maszyna wirtualna?', 'Gdy potrzebny jest pełny system, np. Windows z Active Directory lub izolacja do analizy malware', ['Zawsze, kontenery są niebezpieczne', 'Do każdej aplikacji Java', 'Nigdy'], 'Kontenery wystarczą do większości ćwiczeń sieciowych.'],
  ['Do czego służy migawka (snapshot) maszyny wirtualnej w labie malware?', 'Do przywrócenia czystego stanu systemu po analizie próbki', ['Do przyspieszenia procesora', 'Do szyfrowania dysku', 'Do wysyłania próbki do chmury'], 'Gwarantuje, że po analizie nie zostaje trwałość malware.'],
  ['Co jest główną zasadą uruchamiania celowo podatnych aplikacji w labie?', 'Wyłącznie lokalnie, z portem na 127.0.0.1 i w odizolowanej sieci', ['Na publicznym serwerze, by inni mogli ćwiczyć', 'W sieci firmowej, by przetestować IDS', 'Bez ograniczeń, bo to tylko lab'], 'Podatna aplikacja dostępna z sieci staje się realnym ryzykiem.'],
  ['Gdzie przechowywać hasło bazy w lab-zero?', 'W pliku sekretu poza repozytorium (np. secrets/ w .gitignore)', ['Bezpośrednio w docker-compose.yml', 'W README.md', 'W nazwie kontenera'], 'Hasło w YAML trafia do historii Git.'],
  ['Co sprawdza test db_isolated w check.sh lab-zero?', 'Że port PostgreSQL nie jest osiągalny z hosta', ['Że baza działa szybko', 'Że hasło jest silne', 'Że baza ma kopię zapasową'], 'Test pilnuje, by baza nie została przypadkiem wystawiona.'],
  ['Dlaczego curl do lab-zero wymaga -k lub wskazania CA?', 'Caddy z tls internal używa certyfikatu z własnego lokalnego CA, którego curl nie zna', ['Bo lab nie używa TLS', 'Bo certyfikat jest wygasły', 'Bo curl nie obsługuje TLS 1.3'], 'Rozwiązanie: --cacert z certyfikatem głównym Caddy.'],
  ['Jakie polecenie pokaże, na jakim adresie nasłuchuje opublikowany port w Linuksie/WSL?', 'ss -tlnp', ['ip route', 'dig', 'arp -a'], 'Kolumna Local Address: 127.0.0.1 czy 0.0.0.0.'],
  ['Do czego służy kontener nicolaka/netshoot?', 'Do diagnostyki sieci z gotowym zestawem narzędzi, także w przestrzeni sieci innego kontenera', ['Do hostowania stron WWW', 'Do budowania obrazów Javy', 'Do szyfrowania dysków'], 'Używany z --net container:<nazwa>.'],
  ['Co oznacza, że ruch z kontenera w sieci internal: true do example.com się nie udaje?', 'Izolacja działa — sieć nie ma trasy na zewnątrz', ['Docker jest uszkodzony', 'DNS w Internecie nie działa', 'Kontener ma błędny obraz'], 'To zamierzone zachowanie sieci wewnętrznej.'],
  ['Jak kontenery w tej samej sieci użytkownika Dockera znajdują się nawzajem?', 'Przez wbudowany DNS Dockera — po nazwie usługi', ['Przez plik /etc/hosts hosta', 'Przez broadcast NetBIOS', 'Nie mogą się znaleźć'], 'Domyślna sieć bridge nie ma DNS nazw usług; sieci użytkownika — mają.'],
  ['Czym różnią się dane syntetyczne od kopii produkcyjnych w labie?', 'Dane syntetyczne są generowane i nie zawierają prawdziwych danych osobowych', ['Są zawsze mniejsze', 'Są szyfrowane', 'Nie można ich używać w Javie'], 'Kopie produkcji w labie to ryzyko naruszenia RODO.'],
  ['Które narzędzie wolno używać do skanowania portów w ramach toru?', 'nmap — wyłącznie wobec własnych systemów i labów', ['nmap — wobec dowolnych adresów w Internecie', 'Żadne', 'Tylko narzędzia komercyjne'], 'Skanowanie cudzych sieci bez zgody może być przestępstwem.'],
  ['Które polecenie Windows PowerShell pokaże adres nasłuchu portu 8443?', 'Get-NetTCPConnection -LocalPort 8443', ['Get-Process 8443', 'Resolve-DnsName 8443', 'Test-Path 8443'], 'Kolumna LocalAddress pokazuje 127.0.0.1 lub 0.0.0.0.'],
  ['Jak włączyć przechwytywanie pakietów bez uprawnień roota w Ubuntu?', 'Dodać użytkownika do grupy wireshark (dumpcap z capabilities) i zalogować się ponownie', ['Uruchomić Wiresharka przez sudo', 'Wyłączyć firewall', 'Zainstalować Docker Desktop'], 'GUI nie powinno działać jako root.'],
  ['Co robi „docker compose ls”?', 'Wypisuje projekty Compose i ich status', ['Listuje pliki w katalogu', 'Pokazuje logi kontenerów', 'Usuwa wszystkie kontenery'], 'Przydatne do sprawdzenia, które laby działają w tle.'],
  ['Jakie ryzyko niesie trzymanie wszystkich labów uruchomionych w tle?', 'Zużycie zasobów i pozostawione podatne usługi oraz konflikty portów i podsieci', ['Żadne', 'Szybsze działanie systemu', 'Automatyczne aktualizacje'], 'Sprzątaj po każdym ćwiczeniu.'],
  ['Gdzie ustawić globalne pule adresów dla nowych sieci Dockera?', 'default-address-pools w daemon.json (lub ustawieniach Docker Desktop)', ['W /etc/hosts', 'W pliku .wslconfig', 'W Caddyfile'], 'Pozwala unikać kolizji z siecią VPN i firmową.'],
  ['Jaki skutek ma zostawienie podatnej aplikacji labu na 0.0.0.0 w hotelowym Wi-Fi?', 'Inne osoby w tej sieci mogą ją zaatakować i wykorzystać jako punkt wejścia do laptopa', ['Żaden — Wi-Fi hotelowe izoluje klientów zawsze', 'Aplikacja przestanie działać', 'Laptop wyłączy Wi-Fi'], 'Izolacja klientów nie zawsze jest włączona.'],
  ['Do czego w toru używa się Containerlab?', 'Do topologii z routerami i przełącznikami (np. FRR, cEOS) w kontenerach', ['Do analizy malware', 'Do testów frontendów', 'Do zarządzania hasłami'], 'Rozdziały o switchingu, routingu i automatyzacji.'],
  ['Dlaczego wersje narzędzi zapisuje się w dzienniku laboratoryjnym?', 'Wersje wpływają na zachowanie i są potrzebne do powtórzenia wyników i zgłaszania problemów', ['Bo tego wymaga Docker', 'Bo wersje są tajne', 'Bo bez tego narzędzia nie działają'], 'W IR to też element wiarygodności analizy.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
const ports = [['5432', 'PostgreSQL'], ['8080', 'aplikacja Spring Boot (domyślny port HTTP)'], ['443', 'HTTPS'], ['3000', 'OWASP Juice Shop (domyślnie)'], ['6379', 'Redis']];
const portAll = ports.map((x) => x[1]);
for (const [p, d] of ports) add(`Z czym w labach toru zwykle kojarzy się port ${p}?`, d, portAll.filter((x) => x !== d).slice(0, 3), `Port ${p}: ${d}.`);
const cmds = [['wsl -l -v', 'Lista dystrybucji WSL z wersją (1 lub 2) i stanem'], ['wsl --shutdown', 'Zatrzymanie wszystkich dystrybucji i maszyny WSL2'], ['docker network inspect <sieć>', 'Szczegóły sieci: podsieć, brama, Internal, kontenery'], ['docker compose ps', 'Stan usług projektu, w tym zdrowie (healthy)'], ['docker stats', 'Bieżące zużycie CPU i pamięci przez kontenery']];
const cmdAll = cmds.map((x) => x[1]);
for (const [c, d] of cmds) add(`Co pokazuje lub robi polecenie „${c}”?`, d, cmdAll.filter((x) => x !== d).slice(0, 3), `${c}: ${d.toLowerCase()}.`);
const trouble = [
  ['docker compose up zgłasza „port is already allocated”', 'Inny kontener lub proces już używa tego portu na hoście'],
  ['docker: command not found w Ubuntu WSL, choć Docker Desktop działa', 'Brak włączonej integracji WSL dla tej dystrybucji w Docker Desktop'],
  ['Po włączeniu VPN kontenery nie łączą się ze sobą przez sieć hosta', 'Kolizja podsieci Dockera z trasami VPN'],
  ['tcpdump: eth0: You don’t have permission to capture on that device', 'Brak uprawnień do przechwytywania (grupa wireshark/capabilities)'],
  ['Maven w WSL buduje projekt z /mnt/c kilka razy wolniej niż w ~', 'Wolny dostęp WSL do systemu plików Windows'],
  ['check.sh: health zwraca DOWN, proxy_tls działa', 'Aplikacja działa, ale jej zależność (np. baza) jest niedostępna'],
  ['Aplikacja w kontenerze nie rozwiązuje nazwy postgres', 'Kontenery są w różnych sieciach Dockera'],
  ['WSL zajmuje prawie całą pamięć komputera', 'Brak limitu memory w .wslconfig przy wielu labach naraz'],
  ['Kolega w tej samej sieci Wi-Fi otworzył Twoją aplikację labową', 'Port opublikowany bez adresu, czyli na 0.0.0.0'],
  ['Po aktualizacji obrazu lab zachowuje się inaczej niż tydzień temu', 'Użycie tagu latest zamiast przypiętej wersji'],
  ['Mimo reguły ufw deny port kontenera jest osiągalny z sieci', 'Reguły Dockera omijają łańcuchy UFW'],
  ['curl: (60) SSL certificate problem: unable to get local issuer certificate dla lab-zero', 'curl nie ufa lokalnemu CA Caddy (tls internal)'],
];
const trAll = trouble.map((x) => x[1]);
for (const [sym, cause] of trouble) add(`Objaw: „${sym}”. Jaka jest najbardziej prawdopodobna przyczyna?`, cause, trAll.filter((x) => x !== cause).slice(0, 3), `Przyczyna: ${cause.toLowerCase()}.`);
const pkgs = [['dig', 'dnsutils'], ['tshark', 'tshark'], ['mtr', 'mtr-tiny'], ['nc (OpenBSD)', 'netcat-openbsd'], ['ip, ss', 'iproute2'], ['conntrack', 'conntrack'], ['ping', 'iputils-ping'], ['nft', 'nftables']];
const pkgAll = pkgs.map((x) => x[1]);
for (const [t, k] of pkgs) add(`Który pakiet Ubuntu dostarcza narzędzie ${t}?`, k, pkgAll.filter((x) => x !== k).slice(0, 3), `${t} → apt install ${k}.`);
const chk = [
  ['OK proxy_tls, OK health, FAIL db_isolated', 'Baza została wystawiona na hoście — port 5432 jest osiągalny'],
  ['FAIL proxy_tls, FAIL health, OK db_isolated', 'Proxy nie działa lub port 8443 nie jest opublikowany'],
  ['OK proxy_tls, FAIL health, OK db_isolated', 'Aplikacja lub jej zależność zgłasza problem ze zdrowiem'],
  ['OK proxy_tls, OK health, OK db_isolated', 'Lab działa poprawnie i baza jest odizolowana'],
];
const chkAll = chk.map((x) => x[1]);
for (const [r, d] of chk) add(`Wynik check.sh: „${r}”. Co to oznacza?`, d, chkAll.filter((x) => x !== d), d + '.');
const safe = [
  ['ports: ["127.0.0.1:5432:5432"] dla bazy w labie', 'Akceptowalne — baza dostępna tylko lokalnie (choć lepiej bez publikacji)'],
  ['ports: ["5432:5432"] i POSTGRES_PASSWORD: postgres', 'Niebezpieczne — baza ze słabym hasłem dostępna z całej sieci'],
  ['Juice Shop w sieci internal z portem 127.0.0.1:3000', 'Bezpieczne do ćwiczeń lokalnych'],
  ['Hasło bazy w pliku secrets/db_password.txt dodanym do .gitignore', 'Dobra praktyka — sekret poza repozytorium'],
  ['Kopia produkcyjnej bazy klientów zaimportowana do labu na laptopie', 'Niedopuszczalne — ryzyko naruszenia danych osobowych'],
  ['Analiza próbki malware w kontenerze na stacji roboczej', 'Niebezpieczne — do malware służy izolowana VM z migawkami'],
  ['Wireshark uruchamiany jako zwykły użytkownik z dumpcap w grupie wireshark', 'Dobra praktyka — minimalne uprawnienia'],
  ['Laby w ~/labs w WSL, sprzątane docker compose down -v po ćwiczeniu', 'Dobra praktyka — wydajność i porządek'],
  ['Skan nmap całej podsieci biura „żeby sprawdzić lab”', 'Niedopuszczalne bez zgody — dotyczy cudzych systemów'],
  ['Obrazy przypięte do wersji i w CI do skrótu sha256', 'Dobra praktyka — powtarzalność i integralność'],
];
const safeAll = [...new Set(safe.map((x) => x[1]))];
for (const [c, d] of safe) add(`Oceń konfigurację lub praktykę: ${c}.`, d, safeAll.filter((x) => x !== d).slice(0, 3), d + '.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
