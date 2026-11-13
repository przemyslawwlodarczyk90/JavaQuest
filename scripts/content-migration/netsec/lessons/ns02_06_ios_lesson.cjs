// Generuje lekcje _ns_02_ethernet_lan/06_CiscoIosCliEssentials.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_06_ios_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/06_CiscoIosCliEssentials.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// Polecenie -> tryb, w ktorym je wpisujesz
const MODES = {
  user: 'User EXEC (SW1>)',
  priv: 'Privileged EXEC (SW1#)',
  conf: 'konfiguracja globalna (SW1(config)#)',
  intf: 'konfiguracja interfejsu (SW1(config-if)#)',
  line: 'konfiguracja linii (SW1(config-line)#)',
  vlan: 'konfiguracja VLAN-u (SW1(config-vlan)#)',
};
const cmdMode = [
  ['enable', 'user'], ['show running-config', 'priv'], ['copy running-config startup-config', 'priv'], ['reload in 10', 'priv'], ['debug ip packet', 'priv'],
  ['hostname SW1', 'conf'], ['enable secret algorithm-type scrypt secret …', 'conf'], ['ip domain-name lab.local', 'conf'], ['crypto key generate rsa modulus 3072', 'conf'], ['banner motd #Tylko autoryzowany dostęp#', 'conf'], ['service password-encryption', 'conf'], ['username admin privilege 15 algorithm-type scrypt secret …', 'conf'],
  ['switchport mode access', 'intf'], ['shutdown', 'intf'], ['description uplink do SW2', 'intf'], ['ip address 10.99.0.2 255.255.255.0', 'intf'],
  ['transport input ssh', 'line'], ['login local', 'line'], ['exec-timeout 5 0', 'line'], ['access-class MGMT-ONLY in', 'line'],
  ['name SERVERS', 'vlan'],
];
// show ip interface brief: Status / Protocol -> interpretacja
const ifState = [
  ['up', 'up', 'interfejs działa (L1 i L2 w porządku)'],
  ['administratively down', 'down', 'interfejs wyłączony poleceniem shutdown'],
  ['down', 'down', 'brak sygnału w warstwie 1 (kabel, drugi koniec wyłączony, SFP)'],
  ['up', 'down', 'warstwa 1 działa, ale warstwa 2 nie (np. niezgodna enkapsulacja, keepalive, err-disabled po drugiej stronie)'],
];
// Typy hasel w konfiguracji IOS
const pwTypes = [
  ['0', 'tekst jawny', 'niedopuszczalne'],
  ['5', 'MD5 z solą (enable secret — stary domyślny)', 'przestarzałe'],
  ['7', 'odwracalne szyfrowanie Vigenère (service password-encryption)', 'do złamania w sekundę'],
  ['8', 'PBKDF2-SHA-256', 'zalecane'],
  ['9', 'scrypt', 'zalecane'],
];
const ifNames = [['Gi1/0/24', 'GigabitEthernet1/0/24', '1 Gb/s'], ['Fa0/1', 'FastEthernet0/1', '100 Mb/s'], ['Te1/1/1', 'TenGigabitEthernet1/1/1', '10 Gb/s'], ['Twe1/0/1', 'TwentyFiveGigE1/0/1', '25 Gb/s'], ['Hu1/0/49', 'HundredGigE1/0/49', '100 Gb/s'], ['Po1', 'Port-channel1', 'agregat (EtherChannel)']];

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Dlaczego developer powinien umieć „wejść na switcha”', ...img('ns02-06-modes.svg', 'Hierarchia trybów Cisco IOS: User EXEC ze znakiem SW1>, polecenie enable przechodzi do Privileged EXEC SW1#, configure terminal do konfiguracji globalnej SW1(config)#, a z niej do podtrybów interfejsu, linii vty, VLAN-u i routera. exit cofa o poziom, end wraca do trybu uprzywilejowanego.'),
    body:
`Większość sieci firmowych, banków i operatorów, z którymi będzie rozmawiać Twoja aplikacja, zbudowano z urządzeń sterowanych **wierszem poleceń** — najczęściej Cisco IOS / IOS XE, a obok nich Arista EOS i Juniper Junos. Nawet jeśli nigdy nie zostaniesz administratorem sieci, umiejętność przeczytania \`show running-config\`, \`show interfaces\` czy \`show mac address-table\` sprawia, że:
- rozmawiasz z zespołem sieciowym konkretnie („port Gi1/0/12 jest w VLAN 10, a serwer oczekuje 20”), zamiast pisać „sieć nie działa”,
- rozumiesz zrzuty konfiguracji w zgłoszeniach, audytach i raportach z testów bezpieczeństwa,
- automatyzujesz sieć z Javy lub Pythona (Netmiko, NETCONF, Ansible — rozdział 14) — a do tego trzeba znać polecenia, które skrypt wysyła,
- zdajesz **CCNA 200-301** (CLI to ok. 20–25% egzaminu w ćwiczeniach praktycznych) i rozumiesz pytania Network+ o konfigurację przełączników.

W tej lekcji poznasz: tryby pracy IOS i nawigację, różnicę między **running-config** a **startup-config**, najważniejsze polecenia \`show\`, podstawową konfigurację bezpieczeństwa (hostname, \`enable secret\`, użytkownicy lokalni, **SSH zamiast Telnetu**, bannery, timeouty), zapisywanie i kopie konfiguracji, odpowiedniki w Arista EOS i Junos oraz emulatory, w których zbudujesz własny lab: Packet Tracer, Cisco Modeling Labs, GNS3 i **Containerlab**.

Myśl o urządzeniu sieciowym jak o serwerze z bardzo specyficzną powłoką: ma konfigurację (plik), stan w pamięci, logi, użytkowników, zdalny dostęp — i wszystkie zasady hardeningu serwera mają tu odpowiedniki.` },

  { type: 'DEFINITION', heading: 'Słownik CLI urządzeń sieciowych', body:
`- **IOS / IOS XE / NX-OS** — systemy Cisco: klasyczny IOS (starsze routery i przełączniki), **IOS XE** (współczesne Catalyst 9000, routery ISR/ASR — Linux pod spodem, API NETCONF/RESTCONF), NX-OS (przełączniki centrów danych Nexus).
- **User EXEC** (\`SW1>\`) — tryb podglądu po zalogowaniu; ograniczone \`show\`, \`ping\`.
- **Privileged EXEC** (\`SW1#\`) — tryb uprzywilejowany po \`enable\`: wszystkie \`show\`, \`debug\`, \`copy\`, \`reload\`. Odpowiednik roota.
- **Global configuration** (\`SW1(config)#\`) — po \`configure terminal\`; zmiany dotyczące całego urządzenia.
- **Podtryby** — \`(config-if)#\` interfejs, \`(config-line)#\` linie konsoli i vty, \`(config-vlan)#\`, \`(config-router)#\`.
- **running-config** — konfiguracja działająca w **RAM**; każda zmiana działa natychmiast.
- **startup-config** — konfiguracja w **NVRAM**, ładowana przy starcie; zapis: \`copy running-config startup-config\` (lub \`write memory\`).
- **Linie vty** — wirtualne terminale do zdalnego logowania (SSH/Telnet), zwykle \`line vty 0 15\`; **linia con 0** — port konsoli (dostęp fizyczny lub serwer konsol).
- **enable secret** — hasło do trybu uprzywilejowanego zapisane jako skrót (typ 5, 8 lub 9); **enable password** — przestarzałe, jawne lub typ 7.
- **AAA** — uwierzytelnianie, autoryzacja i rozliczalność; centralnie przez **TACACS+** lub **RADIUS** (rozdział 15).
- **Poziomy uprawnień (privilege levels)** — 0–15; 1 to User EXEC, 15 to pełny dostęp.
- **Emulatory** — **Packet Tracer** (symulator do nauki CCNA), **Cisco Modeling Labs (CML)** (prawdziwe obrazy IOS XE/NX-OS), **GNS3** i **EVE-NG** (emulacja wielu dostawców), **Containerlab** (topologie z kontenerów, np. Arista cEOS, Nokia SR Linux, FRR; „lab jako kod” w YAML).` },

  { type: 'ANALOGY', heading: 'Edytor bez Ctrl+S i z autozapisem na żywo', body:
`Wyobraź sobie, że edytujesz plik konfiguracyjny **działającej** aplikacji produkcyjnej w edytorze, w którym:
- **każdy wpisany wiersz od razu działa** — nie ma przycisku „Zastosuj”, nie ma transakcji (to jest **running-config** w RAM),
- plik na dysku zmienia się dopiero, gdy świadomie zapiszesz (\`copy run start\`) — to jest **startup-config**,
- restart przywraca stan z dysku: niezapisane zmiany znikają, a zapisane błędy wracają.

Dlatego sieciowcy mają nawyki, które developerowi przypominają dobre praktyki z baz danych:
- **zanim zmienisz — zrób kopię** (\`show run\` do pliku, \`archive\`, Git),
- przy zdalnej zmianie, która może odciąć Ci dostęp (np. ACL na vty), ustaw **bezpiecznik**: \`reload in 10\` — jeśli stracisz dostęp, urządzenie samo zrestartuje się do ostatniej zapisanej konfiguracji; gdy wszystko działa, \`reload cancel\`,
- **Junos i Arista** mają już prawdziwe „transakcje”: konfiguracja kandydacka, \`commit\`, \`rollback\`, a nawet \`commit confirmed 5\` (automatyczne wycofanie po 5 minutach bez potwierdzenia) — to dokładnie wzorzec, który znasz z \`BEGIN … COMMIT\` i z wdrożeń z automatycznym rollbackiem.

Tryby IOS to z kolei jak \`sudo\` i podkatalogi: \`enable\` to „sudo -i”, \`configure terminal\` to „otwórz plik konfiguracji”, \`interface Gi1/0/5\` to „przejdź do sekcji tego portu”.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Sesja krok po kroku: pierwsze minuty na nowym przełączniku', ...img('ns02-06-config.svg', 'Cztery miejsca przechowywania: Flash z obrazem IOS, RAM z running-config, NVRAM ze startup-config i zewnętrzne kopie przez TFTP, SCP lub Git. copy running-config startup-config zapisuje zmiany, przy starcie startup-config ładuje się do running-config. Ostrzeżenie: zmiany działają od razu, ale tylko w RAM; porada: reload in 10 przed ryzykowną zmianą zdalną.'),
    body:
`Poniżej sesja przez konsolę na świeżym przełączniku. Zwróć uwagę na znak zachęty — zawsze mówi, w jakim trybie jesteś, i jest pierwszą rzeczą, którą sprawdzasz, gdy polecenie „nie istnieje”.`,
    code:
`Switch> enable
Switch# configure terminal
Enter configuration commands, one per line.  End with CNTL/Z.
Switch(config)# hostname SW1
SW1(config)# no ip domain-lookup                 ! literówka nie będzie traktowana jak nazwa hosta do rozwiązania
SW1(config)# enable algorithm-type scrypt secret Tajne-Haslo-2026
SW1(config)# username admin privilege 15 algorithm-type scrypt secret Inne-Haslo-2026
SW1(config)# interface vlan 99
SW1(config-if)# ip address 10.99.0.11 255.255.255.0
SW1(config-if)# no shutdown
SW1(config-if)# exit
SW1(config)# ip default-gateway 10.99.0.1
SW1(config)# end
SW1# show running-config | section line vty
line vty 0 4
 login
line vty 5 15
 login
SW1# copy running-config startup-config
Destination filename [startup-config]?
Building configuration...
[OK]

Przydatne skróty: polecenia można skracać do jednoznacznego prefiksu (conf t, sh ip int br, wr),
Tab dopełnia, ? pokazuje możliwości (show ?), Ctrl+Z = end, Ctrl+Shift+6 przerywa ping/traceroute,
„no <polecenie>” cofa ustawienie, „do show …” uruchamia polecenie EXEC z trybu konfiguracji.` },

  { type: 'CODE_BASIC', heading: 'Polecenia show, które trzeba znać na pamięć', body:
`Te polecenia odpowiadają na 90% pytań diagnostycznych — również na egzaminie CCNA. Wyjście możesz filtrować: \`| include\`, \`| exclude\`, \`| begin\`, \`| section\`.`,
    code:
`SW1# show ip interface brief
Interface              IP-Address      OK? Method Status                Protocol
Vlan99                 10.99.0.11      YES manual up                    up
GigabitEthernet1/0/1   unassigned      YES unset  up                    up
GigabitEthernet1/0/2   unassigned      YES unset  down                  down
GigabitEthernet1/0/3   unassigned      YES unset  administratively down down

SW1# show interfaces GigabitEthernet1/0/1        ! prędkość, duplex, błędy CRC, kolizje, drops
SW1# show interfaces status                      ! port, opis, status, VLAN, duplex, prędkość, typ
SW1# show vlan brief                             ! VLAN-y i porty access
SW1# show interfaces trunk                       ! trunki: tryb, native, allowed
SW1# show mac address-table dynamic interface Gi1/0/1
SW1# show spanning-tree vlan 10
SW1# show cdp neighbors / show lldp neighbors    ! kto jest po drugiej stronie (lekcja 09)
SW1# show version                                ! wersja IOS, uptime, model, numer seryjny, przyczyna restartu
SW1# show logging                                ! bufor logów
SW1# show users / show ssh                       ! kto jest zalogowany i jak
SW1# show running-config interface Gi1/0/1       ! konfiguracja jednego portu
SW1# show archive config differences nvram:startup-config system:running-config   ! co zmieniono od zapisu

Status/Protocol w show ip int brief:
${ifState.map(([s, p, d]) => `  ${(s + '/' + p).padEnd(28)} → ${d}`).join('\n')}` },

  { type: 'STEP_BY_STEP', heading: 'Guided Lab: Containerlab z dwoma przełącznikami', ...img('ns02-06-lab.svg', 'Topologia laboratorium Containerlab na hoście z Dockerem: dwa przełączniki sw1 i sw2 połączone trunkiem z VLAN 10 i 20 oraz cztery hosty pc1–pc4 w VLAN 10 i 20. Zadania: SSH zamiast Telnetu, enable secret, VLAN-y i trunk, banner, zapis i kopia konfiguracji.'),
    body:
`Laboratorium z planu lekcji — na własnej maszynie z Linuksem i Dockerem (lub WSL2). Containerlab jest darmowy; obraz **Arista cEOS** pobierzesz po bezpłatnej rejestracji, **Cisco IOL** jest dostępny w CML Free, a bez rejestracji zadziała Nokia SR Linux lub FRR.
1. **Zainstaluj** Containerlab (\`bash -c "$(curl -sL https://get.containerlab.dev)"\`) i zaimportuj obraz (\`docker import cEOS64-lab-4.xx.tar.xz ceos:4.xx\`).
2. **Opisz topologię jako kod** w \`lab.clab.yml\`: węzły sw1, sw2 (\`kind: arista_ceos\` lub \`cisco_iol\`), pc1–pc4 (\`kind: linux\`, obraz alpine), łącza \`sw1:eth1 — sw2:eth1\` i hosty na eth2/eth3.
3. **Uruchom**: \`containerlab deploy -t lab.clab.yml\`; Containerlab wypisze adresy zarządzania (sieć 172.20.20.0/24) — zaloguj się \`ssh admin@clab-lab-sw1\`.
4. **Skonfiguruj podstawy na obu przełącznikach**: hostname, użytkownik lokalny z silnym skrótem hasła, \`enable secret\`, banner.
5. **SSH zamiast Telnetu**: \`ip domain-name\`, klucz RSA ≥ 3072 lub ECDSA, \`ip ssh version 2\`, na \`line vty\`: \`transport input ssh\`, \`login local\`, \`exec-timeout 10 0\`. Sprawdź, że \`telnet\` do przełącznika jest odrzucany.
6. **VLAN-y i trunk**: VLAN 10 i 20, porty do hostów jako access, \`eth1\` jako trunk z \`allowed vlan 10,20\` i nieużywanym native VLAN (lekcja 05). Sprawdź \`show vlan brief\`, \`show interfaces trunk\`, ping pc1 → pc3 (ten sam VLAN, różne przełączniki) i brak łączności pc1 → pc2.
7. **Zapisz i zrób kopię**: \`copy run start\` (EOS: \`write memory\`), a potem \`containerlab save -t lab.clab.yml\` lub \`show running-config\` przez SSH do pliku w repozytorium Git. Zrestartuj węzeł i sprawdź, że konfiguracja przetrwała.
8. **Zniszcz lab**: \`containerlab destroy -t lab.clab.yml --cleanup\`. Plik YAML i konfiguracje w Git pozwalają odtworzyć wszystko jednym poleceniem — to podstawa „sieci jako kodu”.` },

  { type: 'NOTE', heading: 'Hasła w konfiguracji: typy 0, 5, 7, 8 i 9', body:
`W plikach konfiguracji (w kopiach zapasowych, zgłoszeniach, repozytoriach!) zobaczysz hasła z numerem typu. To jedna z pierwszych rzeczy sprawdzanych w audycie — i jedna z najczęstszych znalezisk w wyciekach konfiguracji:`,
    code:
`${pwTypes.map(([t, d, v]) => `typ ${t}  ${d.padEnd(62)} ${v}`).join('\n')}

Przykłady w running-config:
enable secret 9 $9$nhEmQVczB7dqsO$X.HsgL6x1il0RxkOSSvyQYwucySCt7qFm4v7pqCxkKM   ← dobrze
username admin privilege 15 secret 8 $8$dsYGNam3K1SIJO$7nv/35M/qr6t.dVc7UY9zrJDWRVqncHub1PE9UlMQFs
username backup password 7 0822455D0A16                                     ← złe: typ 7 odwracalny
enable password cisco                                                       ← złe: jawne

Zasady:
- zawsze „secret” z algorithm-type scrypt (typ 9) lub sha256 (typ 8), nigdy „password”,
- service password-encryption tylko zaciemnia hasła typu 7 (ochrona przed podglądem przez ramię), to NIE jest szyfrowanie,
- konfiguracje z hasłami traktuj jak sekrety: nie wklejaj do zgłoszeń, czatów, publicznych repozytoriów,
- w produkcji logowanie przez AAA (TACACS+/RADIUS) z kontami imiennymi; konto lokalne tylko awaryjne.` },

  { type: 'CODE_PRACTICAL', heading: 'Hardening zarządzania: SSH, timeouty, ACL i logi', body:
`Minimalny zestaw zabezpieczeń dostępu do urządzenia — odpowiednik „SSH tylko kluczami, z sieci administracyjnej, z logowaniem” na serwerze Linux. To również typowy materiał ćwiczeń CCNA i Security+ (hardening urządzeń infrastruktury).`,
    code:
`ip domain-name corp.example
crypto key generate rsa modulus 3072            ! klucz hosta SSH
ip ssh version 2
ip ssh time-out 60
ip ssh authentication-retries 3
login block-for 120 attempts 5 within 60        ! blokada po serii nieudanych logowań
login on-failure log
login on-success log
!
ip access-list standard MGMT-ONLY
 permit 10.99.0.0 0.0.0.255                     ! tylko sieć administracyjna / jump host
 deny any log
!
line con 0
 login local
 exec-timeout 5 0
line vty 0 15
 access-class MGMT-ONLY in
 transport input ssh                            ! Telnet wyłączony
 login local
 exec-timeout 10 0
!
no ip http server                               ! wyłącz HTTP; jeśli potrzebny interfejs WWW — tylko ip http secure-server
no cdp run                                      ! lub per port: no cdp enable na portach brzegowych (lekcja 09)
service timestamps log datetime msec localtime show-timezone
logging host 10.99.0.50                         ! syslog do SIEM
ntp server 10.99.0.1                            ! poprawny czas = korelacja logów
archive
 path flash:archive/config
 write-memory                                   ! kopia przy każdym zapisie
 log config
  logging enable                                ! kto, kiedy, jakie polecenie konfiguracyjne
banner login ^Dostęp tylko dla upoważnionych. Działania są rejestrowane.^` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz: od zgłoszenia „nie działa” po audyt i automatyzację', ...img('ns02-06-vendors.svg', 'Tabela porównująca Cisco IOS, Arista EOS i Juniper Junos: wejście w konfigurację, podgląd interfejsów, tablica MAC, zatwierdzanie i zapis zmian, wycofanie, bezpieczna zmiana z automatycznym wycofaniem i filtrowanie wyjścia. Podpis: Junos i EOS mają konfigurację kandydacką i commit.'),
    body:
`**Developer / DevOps:**
- **Diagnoza z zespołem sieci** — „serwer 10.20.0.15 nie ma sieci”: \`show mac address-table address <MAC serwera>\` (na którym porcie?), \`show interfaces status\` (VLAN, prędkość, duplex), \`show interfaces Gi1/0/7\` (błędy CRC = kabel), \`show logging\` (port err-disabled?).
- **Automatyzacja** — Twój kod w Javie/Pythonie wysyła przez SSH dokładnie te polecenia (Netmiko, Ansible \`cisco.ios\`) albo korzysta z API modelowanego w YANG (NETCONF/RESTCONF na IOS XE, eAPI w Arista, NETCONF w Junos). Zrozumienie trybów i running/startup to warunek, by automat nie zostawił połowicznej, niezapisanej zmiany.
- **Laby i testy** — Containerlab pozwala uruchomić prawdziwy system sieciowy w CI i testować zmiany konfiguracji jak kod (lint, wdrożenie do labu, testy łączności) przed produkcją.
- **Wielu dostawców** — w chmurze i centrach danych spotkasz Arista EOS (składnia bardzo podobna do IOS) i Junos (hierarchiczna konfiguracja, \`set\`/\`delete\`, commit); w sieciach operatorskich także Nokia SR OS/SR Linux, Huawei VRP, MikroTik RouterOS.

**Blue team:**
- **Audyt konfiguracji** — szukasz: Telnetu, \`enable password\`, haseł typu 7, braku \`exec-timeout\`, braku ACL na vty, włączonego HTTP, SNMP z community „public”, braku logowania i NTP; narzędzia: benchmarki **CIS** dla Cisco IOS, skrypty porównujące z wzorcem.
- **Kopie konfiguracji to sekrety** — zawierają skróty haseł, klucze SNMP, PSK VPN; ich wyciek (np. z serwera TFTP lub repozytorium) to poważny incydent.
- **Logi zmian** — \`archive log config\` + syslog + AAA accounting (TACACS+) dają ślad: kto, kiedy, jakie polecenie; to podstawa dochodzeń i wykrywania nieautoryzowanych zmian.
- **Podatności urządzeń brzegowych** — interfejsy WWW zarządzania urządzeń sieciowych (w tym IOS XE) bywały celem masowych ataków; zasada: interfejs zarządzania nigdy nie wystawiony do Internetu, aktualizacje oprogramowania, monitoring nowych kont i zmian konfiguracji (CISA KEV regularnie zawiera podatności urządzeń sieciowych).` },

  { type: 'PITFALL', heading: 'Częste błędy w pracy z CLI', body:
`- **Zapomniany zapis** — zmiany działają, wszyscy są zadowoleni, a po zaniku zasilania przełącznik wstaje ze starą konfiguracją. Po weryfikacji: \`copy run start\`.
- **Zapis bez weryfikacji** — odwrotny błąd: zapisanie błędnej zmiany pozbawia Cię najprostszego rollbacku (restartu).
- **Odcięcie się od urządzenia** — zastosowanie ACL na vty bez reguły dla własnego adresu, \`shutdown\` na interfejsie, przez który jesteś zalogowany, zmiana VLAN-u zarządzania. Przed ryzykowną zmianą: \`reload in 10\` lub dostęp konsolowy (serwer konsol / out-of-band).
- **\`switchport trunk allowed vlan X\` bez \`add\`** — zastępuje listę (lekcja 05).
- **Literówka jako nazwa hosta** — wpisanie nieistniejącego polecenia w trybie EXEC powoduje próbę rozwiązania go przez DNS i długie oczekiwanie; \`no ip domain-lookup\` (w labie) lub \`transport preferred none\` na liniach.
- **\`debug\` na produkcji** — \`debug all\` lub debug ruchu na mocno obciążonym urządzeniu potrafi je zawiesić; używaj ostrożnie, z filtrem, i pamiętaj o \`undebug all\`.
- **\`enable password\` i typ 7** — „zaszyfrowane” hasła typu 7 łamie się w sekundę narzędziami dostępnymi online.
- **Telnet** — hasła i polecenia idą jawnym tekstem; wszędzie SSH v2.
- **Brak \`exec-timeout\`** — porzucona sesja administratora pozostaje otwarta na konsoli lub vty.
- **Konfiguracja wklejona do zgłoszenia lub czatu** — z hasłami i community SNMP; maskuj sekrety (\`show running-config\` w nowszych wersjach i narzędzia do sanitizacji).` },

  { type: 'CODE_WRONG', heading: 'Źle: konfiguracja dostępu „z pudełka”', body:
`Taką konfigurację wciąż znajduje się w audytach: Telnet, jawne hasła, wspólne konto, brak ograniczeń i logów.`,
    code:
`hostname Switch
enable password cisco
!
line con 0
 password cisco
 login
line vty 0 15
 password cisco
 login
 transport input all            ! Telnet i SSH
 exec-timeout 0 0               ! sesja nigdy nie wygasa
!
ip http server                  ! interfejs WWW po HTTP
snmp-server community public RO
snmp-server community private RW
! brak: logging host, ntp, archive log config, ACL na vty, banner
! Skutki: hasło podsłuchane w Telnecie daje pełny dostęp z dowolnej sieci,
! SNMP RW z community „private” pozwala zmienić konfigurację,
! brak śladu, kto i kiedy coś zmienił.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: dostęp imienny, szyfrowany, ograniczony i rejestrowany', body:
`Konta imienne przez AAA (TACACS+), konto lokalne tylko awaryjne, SSH v2 z ACL, timeouty, SNMPv3, logi i NTP, kopie konfiguracji przy każdym zapisie.`,
    code:
`hostname SW-WAW-ACC-01
enable algorithm-type scrypt secret <silne-hasło-z-sejfu>
username break-glass privilege 15 algorithm-type scrypt secret <hasło-awaryjne-z-sejfu>
!
aaa new-model
aaa authentication login default group tacacs+ local
aaa authorization exec default group tacacs+ local
aaa accounting commands 15 default start-stop group tacacs+
!
ip ssh version 2
ip access-list standard MGMT-ONLY
 permit 10.99.0.0 0.0.0.255
 deny any log
line vty 0 15
 access-class MGMT-ONLY in
 transport input ssh
 exec-timeout 10 0
line con 0
 exec-timeout 5 0
!
no ip http server
no ip http secure-server        ! jeśli nieużywany
snmp-server group RO-GRP v3 priv
snmp-server user monitor RO-GRP v3 auth sha <klucz> priv aes 256 <klucz>
logging host 10.99.0.50
ntp server 10.99.0.1
archive
 log config
  logging enable
 path scp://backup@10.99.0.60/configs/$h-
 write-memory
banner login ^Dostęp tylko dla upoważnionych. Działania są rejestrowane.^` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy CLI, kiedy GUI, a kiedy API i automatyzacja', body:
`- **CLI ręcznie** — diagnoza (\`show\`, \`ping\`, \`traceroute\`), pojedyncze zmiany w małej sieci, praca awaryjna przez konsolę, nauka i egzamin CCNA. Zawsze z kopią przed zmianą i weryfikacją po.
- **GUI / kontroler** — sieci zarządzane centralnie (Cisco Catalyst Center, Meraki, Aruba Central, UniFi): konfiguracja z szablonów, widok całej sieci; CLI wtedy głównie do diagnozy.
- **Automatyzacja (Ansible, Netmiko, Nornir)** — powtarzalne zmiany na wielu urządzeniach, standardy konfiguracji, kopie zapasowe, raporty zgodności. Polecenia CLI wciąż są podstawą — skrypt robi to, co Ty robiłbyś ręcznie.
- **API (NETCONF/RESTCONF, gNMI, eAPI)** — integracja z systemami (CMDB, IPAM, CI/CD), konfiguracja jako dane (YANG), telemetria strumieniowa; to naturalne miejsce dla Java developera (rozdział 14).
- **Emulatory**: Packet Tracer do nauki CCNA i prostych scenariuszy; CML do realnych obrazów Cisco; GNS3/EVE-NG do labów wielu dostawców; **Containerlab** do labów jako kod i testów w CI.
- **Bezpieczeństwo** zawsze: dostęp przez SSH/HTTPS/API z uwierzytelnianiem centralnym i logowaniem, z sieci zarządzania, nigdy z Internetu.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Tryby: \`SW1>\` User EXEC → \`enable\` → \`SW1#\` Privileged → \`configure terminal\` → \`SW1(config)#\` → podtryby (\`config-if\`, \`config-line\`, \`config-vlan\`); \`exit\` w górę, \`end\` do \`#\`, \`do\` dla poleceń EXEC w konfiguracji.
- running-config (RAM) działa natychmiast; startup-config (NVRAM) ładuje się przy starcie; zapis \`copy run start\`; zabezpieczenie zdalnych zmian \`reload in\`; w Junos/EOS commit, rollback, commit confirmed.
- Najważniejsze \`show\`: ip interface brief, interfaces (status), vlan brief, interfaces trunk, mac address-table, spanning-tree, cdp/lldp neighbors, version, logging, running-config.
- Status/Protocol: up/up działa, administratively down = shutdown, down/down = L1, up/down = problem L2.
- Hasła: tylko \`secret\` typu 8/9; typ 7 i \`enable password\` są niebezpieczne; konfiguracje to sekrety.
- Hardening: SSH v2 zamiast Telnetu, ACL na vty, exec-timeout, AAA z kontami imiennymi, SNMPv3, syslog + NTP, archive log config, brak HTTP i zarządzania z Internetu.
- Laby: Packet Tracer, CML, GNS3/EVE-NG, Containerlab (lab jako kod w Git).
- Następna lekcja: **STP i RSTP od środka**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const exCmds = [['hostname SW1', 'conf'], ['transport input ssh', 'line'], ['copy running-config startup-config', 'priv'], ['switchport mode access', 'intf'], ['name SERVERS', 'vlan']];
ex(`Dla każdego polecenia podaj tryb IOS, w którym je wpisujesz, i sekwencję przejścia z SW1>: ${exCmds.map((c) => c[0]).join('; ')}.`, 'enable → configure terminal → podtryb (interface / line / vlan).', exCmds.map(([c, m]) => `${c} — ${MODES[m]}`).join('; ') + '. Sekwencja: enable → configure terminal → np. interface Gi1/0/5 / line vty 0 15 / vlan 20.');
for (const [s, p, d] of ifState) {
  ex(`W show ip interface brief interfejs ma Status „${s}” i Protocol „${p}”. Co to oznacza i co sprawdzisz w pierwszej kolejności?`, 'Status = warstwa 1 (lub shutdown), Protocol = warstwa 2.', `${d}. ` + (s === 'up' && p === 'up' ? 'Jeśli mimo to brak łączności — sprawdź VLAN, adresację, ACL, tablicę MAC.' : s.startsWith('admin') ? 'Sprawdź, czy wyłączenie było celowe (description, zgłoszenie); włączenie: no shutdown.' : s === 'down' ? 'Sprawdź kabel, SFP, drugi koniec, show interfaces (liczniki), port err-disabled po drugiej stronie.' : 'Sprawdź enkapsulację/keepalive na łączu szeregowym, zgodność ustawień obu stron, show interfaces i logi.'));
}
const more = [
  ['Wykonaj Guided Lab z lekcji (Containerlab z dwoma przełącznikami). Przedstaw plik lab.clab.yml, konfigurację sw1 i wynik testów łączności.', 'kind: arista_ceos lub cisco_iol; hosty kind: linux.', 'name: lab\ntopology:\n  nodes:\n    sw1: {kind: arista_ceos, image: ceos:4.32}\n    sw2: {kind: arista_ceos, image: ceos:4.32}\n    pc1: {kind: linux, image: alpine}\n    ...\n  links:\n    - endpoints: ["sw1:eth1", "sw2:eth1"]\n    - endpoints: ["sw1:eth2", "pc1:eth1"]\n    ...\nKonfiguracja sw1: vlan 10,20; interface Ethernet2 switchport access vlan 10; interface Ethernet1 switchport mode trunk, switchport trunk allowed vlan 10,20, native vlan 999; username admin secret …; management ssh. Wyniki: pc1 → pc3 (VLAN 10 na różnych przełącznikach) odpowiada, pc1 → pc2 brak odpowiedzi; konfiguracja przetrwała restart po write memory.'],
  ['Skonfiguruj na przełączniku dostęp SSH v2 od zera (domena, klucz, użytkownik, linie vty) i wyłącz Telnet. Jak zweryfikujesz efekt?', 'ip domain-name, crypto key generate rsa, ip ssh version 2, transport input ssh, login local.', 'conf t; ip domain-name lab.local; crypto key generate rsa modulus 3072; ip ssh version 2; username admin privilege 15 algorithm-type scrypt secret …; line vty 0 15; transport input ssh; login local; exec-timeout 10 0; end; copy run start. Weryfikacja: show ip ssh (SSH Enabled - version 2.0), show ssh podczas sesji, próba telnet <IP> kończy się odrzuceniem połączenia, logi login on-success.'],
  ['Przeanalizuj fragment konfiguracji z audytu: „enable password 7 0822455D0A16”, „line vty 0 4 / password cisco / transport input telnet”, „snmp-server community public RW”. Wypisz ryzyka i poprawki.', 'Typ 7, Telnet, SNMP RW z domyślnym community.', 'Ryzyka: hasło typu 7 odwracalne natychmiast; Telnet przesyła hasła jawnie; wspólne hasło na vty bez kont imiennych; SNMP v1/v2c z community „public” i prawem zapisu pozwala zmienić konfigurację każdemu, kto zna/zgadnie community. Poprawki: enable algorithm-type scrypt secret; konta imienne lub AAA; transport input ssh + access-class; usunięcie community RW i przejście na SNMPv3 authPriv tylko z adresów monitoringu; exec-timeout; logowanie.'],
  ['Zaplanuj bezpieczną zdalną zmianę ACL na liniach vty, która może odciąć Ci dostęp. Podaj kolejność poleceń dla Cisco IOS i odpowiednik w Junos.', 'reload in / commit confirmed.', 'IOS: 1) show run | section vty (kopia), 2) copy run start (punkt powrotu), 3) reload in 10, 4) wprowadź ACL i access-class, 5) z nowej sesji SSH sprawdź dostęp, 6) reload cancel i copy run start. Jeśli stracisz dostęp — po 10 minutach urządzenie wróci do startup-config. Junos: configure; zmiany; commit confirmed 5; po weryfikacji commit (bez potwierdzenia nastąpi automatyczny rollback).'],
  ['Pokaż, jak za pomocą filtrów wyjścia znaleźć: (a) wszystkie interfejsy w stanie down, (b) konfigurację linii vty, (c) konfigurację od sekcji interface Vlan99.', '| include, | section, | begin.', '(a) show ip interface brief | include down (lub show interfaces status | include notconnect); (b) show running-config | section line vty; (c) show running-config | begin interface Vlan99. Dodatkowo | exclude ukrywa linie, | count liczy dopasowania (nowsze IOS).'],
  ['Zautomatyzuj kopię zapasową konfiguracji: opisz dwa sposoby (na urządzeniu i poza nim) i gdzie przechowywać kopie.', 'archive na IOS; Ansible/Netmiko/Oxidized poza urządzeniem.', 'Na urządzeniu: archive; path scp://backup@10.99.0.60/configs/$h-; write-memory (kopia przy każdym zapisie) lub time-period 1440. Poza urządzeniem: Oxidized/RANCID lub Ansible (cisco.ios.ios_config backup: yes) uruchamiane cyklicznie, z wersjonowaniem w Git (diff zmian). Przechowywanie: repozytorium prywatne z kontrolą dostępu, szyfrowanie, bez publikacji — kopie zawierają skróty haseł i klucze; alert przy nieoczekiwanej różnicy.'],
  ['Napisz w Javie prosty parser wyjścia „show ip interface brief”, który zwraca listę interfejsów ze stanem i flagą „problem” (wszystko poza up/up i administratively down).', 'Kolumny oddzielone wieloma spacjami; Status może zawierać spację („administratively down”).', 'record If(String name, String ip, String status, String protocol) { boolean problem() { return !(status.equals("up") && protocol.equals("up")) && !status.startsWith("administratively"); } }\nstatic List<If> parse(String out) {\n  return out.lines().skip(1).filter(l -> !l.isBlank()).map(l -> {\n    String[] p = l.trim().split("\\\\s+");\n    String proto = p[p.length - 1];\n    String status = p[p.length - 2].equals("down") && p[p.length - 3].equals("administratively") ? "administratively down" : p[p.length - 2];\n    return new If(p[0], p[1], status, proto);\n  }).toList();\n}\nW praktyce lepiej użyć ustrukturyzowanego wyjścia (TextFSM/Genie, NETCONF, eAPI JSON) niż parsować tekst.'],
  ['Wyjaśnij, czym różni się konfiguracja w Cisco IOS od Junos pod względem modelu zmian, i jakie to ma znaczenie dla automatyzacji.', 'Natychmiastowe zmiany vs konfiguracja kandydacka i commit.', 'IOS: każde polecenie działa od razu w running-config; brak transakcji — skrypt przerwany w połowie zostawia częściową zmianę; zapis osobnym poleceniem. Junos: zmiany trafiają do konfiguracji kandydackiej, commit stosuje je atomowo (po walidacji), rollback N przywraca poprzednie wersje, commit confirmed daje automatyczne wycofanie. Dla automatyzacji model transakcyjny jest bezpieczniejszy; w IOS XE podobne możliwości daje NETCONF z candidate datastore lub configure replace.'],
  ['Na przełączniku po wpisaniu „shwo run” konsola zawiesza się na kilkanaście sekund z komunikatem „Translating "shwo"...domain server (255.255.255.255)”. Wyjaśnij i napraw.', 'Nieznane polecenie traktowane jako nazwa hosta do telnetu.', 'IOS traktuje nieznane słowo w trybie EXEC jako nazwę hosta, z którym ma się połączyć (Telnet), i próbuje rozwiązać ją przez DNS broadcastem. Naprawa: no ip domain-lookup (gdy DNS niepotrzebny, np. lab) lub na liniach: transport preferred none (zachowuje DNS, ale wyłącza automatyczne łączenie).'],
  ['Opisz, jak sprawdzisz, na którym porcie przełącznika jest podłączony serwer o adresie 10.20.0.15, znając tylko jego IP.', 'IP → MAC (ARP na bramie) → port (tablica MAC), ewentualnie kolejne przełączniki.', '1) Na bramie (router/L3): show ip arp 10.20.0.15 → MAC, np. 0050.56ab.1234. 2) Na przełączniku: show mac address-table address 0050.56ab.1234 → port, np. Gi1/0/7. 3) Jeśli to trunk/uplink do innego przełącznika (show cdp/lldp neighbors), powtórz na następnym. 4) show interfaces status | include Gi1/0/7 — VLAN, prędkość, opis. Format MAC w Cisco: xxxx.xxxx.xxxx.'],
  ['Wymień 8 punktów, które sprawdzisz w audycie konfiguracji przełącznika według benchmarku CIS (zarządzanie i dostęp).', 'Hasła, protokoły zarządzania, AAA, logowanie, usługi.', '1) enable secret typu 8/9, brak enable password i haseł typu 7; 2) AAA z TACACS+/RADIUS, konto lokalne awaryjne; 3) SSH v2, brak Telnetu (transport input ssh); 4) access-class na vty; 5) exec-timeout na con/vty; 6) wyłączone ip http server, nieużywane usługi (cdp na portach brzegowych, small servers); 7) SNMPv3 lub brak SNMP RW, ACL dla SNMP; 8) syslog na serwer zewnętrzny, timestamps, NTP z uwierzytelnianiem; 9) banner; 10) archive log config i kopie konfiguracji.'],
  ['Porównaj Packet Tracer, Cisco Modeling Labs, GNS3 i Containerlab: co emulują, jakie mają ograniczenia i do czego wybierzesz każdy z nich.', 'Symulacja vs prawdziwe obrazy; GUI vs lab jako kod.', 'Packet Tracer: symulator (nie prawdziwy IOS), darmowy przez Cisco NetAcad, idealny do CCNA, ale ograniczony zestaw poleceń. CML: prawdziwe obrazy Cisco (IOS XE, NX-OS, IOL), wersja Free do 5 węzłów, płatna większa; dobre do realistycznych labów Cisco. GNS3/EVE-NG: emulatory wielu dostawców (QEMU), GUI, duża elastyczność, większe wymagania sprzętowe. Containerlab: topologie kontenerowe definiowane w YAML (cEOS, SR Linux, FRR, IOL, vrnetlab), szybki start, lab jako kod, świetny do automatyzacji i CI.'],
  ['Wyjaśnij, dlaczego plik kopii konfiguracji przełącznika jest wrażliwy, i zaproponuj, jak bezpiecznie dzielić się konfiguracją w zgłoszeniu do dostawcy.', 'Co zawiera running-config.', 'Zawiera skróty haseł (typ 5/8/9 — możliwy atak słownikowy offline; typ 7 — od razu jawne), klucze i community SNMP, klucze TACACS+/RADIUS, PSK VPN, adresację i topologię (mapa dla napastnika). W zgłoszeniu: przekazać tylko potrzebny fragment (show run interface …), zamaskować sekrety (sanitizacja skryptem lub narzędziem dostawcy), przekazać przez bezpieczny kanał, nie wklejać do publicznych forów i czatów.'],
  ['Skonfiguruj interfejs zarządzania przełącznika L2 w VLAN 99 (SVI), bramę domyślną i sprawdź łączność. Dlaczego przełącznik L2 potrzebuje ip default-gateway?', 'Przełącznik L2 nie routuje — dla własnego ruchu zarządzania potrzebuje bramy.', 'vlan 99; interface vlan 99; ip address 10.99.0.11 255.255.255.0; no shutdown; ip default-gateway 10.99.0.1; trunk do rdzenia musi przenosić VLAN 99. Weryfikacja: show ip interface brief (Vlan99 up/up), ping 10.99.0.1. Przełącznik L2 (bez ip routing) nie ma tablicy routingu dla własnego ruchu, więc odpowiedzi do stacji administratora z innej podsieci wysyła na ip default-gateway. Na przełączniku L3 z ip routing używa się ip route 0.0.0.0 0.0.0.0.'],
  ['Na podstawie „show version” wskaż, jak odczytać wersję oprogramowania, czas działania i przyczynę ostatniego restartu. Dlaczego te informacje są ważne dla bezpieczeństwa?', 'Linie Cisco IOS Software…, uptime is…, System returned to ROM by…, Last reload reason.', 'Wersja: linia „Cisco IOS XE Software, Version 17.x.y”; uptime: „SW1 uptime is 2 weeks, 3 days”; przyczyna: „Last reload reason: …” (np. power-on, reload command, crash). Bezpieczeństwo: wersja do porównania z biuletynami (Cisco PSIRT, CISA KEV) — czy urządzenie ma znane podatności; nieoczekiwany restart lub bardzo długi uptime (brak aktualizacji od lat) to sygnały do dalszej analizy.'],
  ['Opisz różnicę między poleceniami exit, end i Ctrl+Z oraz do czego służy prefiks do.', 'Poziomy trybów.', 'exit — cofa o jeden poziom (z config-if do config, z config do #, z # wylogowuje); end i Ctrl+Z — z dowolnego podtrybu konfiguracji wraca od razu do Privileged EXEC (#); do — pozwala uruchomić polecenie EXEC (np. do show ip int brief, do copy run start) bez wychodzenia z trybu konfiguracji.'],
];
for (const [q, h, a] of more) ex(q, h, a);
const extra = [
  ['Zinterpretuj wpis logu: „%SEC_LOGIN-4-LOGIN_FAILED: Login failed [user: admin] [Source: 203.0.113.50] [localport: 22] [Reason: Login Authentication Failed]”. Jakie działania podejmiesz?', 'Źródło z Internetu = interfejs zarządzania wystawiony na zewnątrz.', 'Nieudane logowanie SSH na konto admin z publicznego adresu 203.0.113.50 — oznacza, że port 22 urządzenia jest osiągalny z Internetu (błąd architektury) i ktoś próbuje zgadywać hasła. Działania: natychmiast ograniczyć dostęp (access-class na vty, ACL na brzegu), sprawdzić inne logowania z tego adresu i udane logowania (login on-success), zweryfikować brak zmian w konfiguracji (archive log config, porównanie z kopią), włączyć login block-for, przekazać IOC do SIEM, rozważyć zmianę haseł kont lokalnych.'],
  ['Przygotuj szablon konfiguracji bazowej (baseline) przełącznika dostępowego jako plik do repozytorium Git, z miejscami na zmienne (hostname, adres zarządzania).', 'Szablon Jinja2 lub zwykły plik z placeholderami; bez sekretów w repozytorium.', 'hostname {{ hostname }}\nno ip domain-lookup\nip domain-name corp.example\nenable algorithm-type scrypt secret {{ vault.enable_secret }}\naaa new-model\n…\ninterface vlan 99\n ip address {{ mgmt_ip }} 255.255.255.0\nip default-gateway {{ mgmt_gw }}\nline vty 0 15\n access-class MGMT-ONLY in\n transport input ssh\n exec-timeout 10 0\nlogging host {{ syslog }}\nntp server {{ ntp }}\nSekrety w sejfie (Ansible Vault, HashiCorp Vault), nie w Git; szablon renderowany i wdrażany przez Ansible; różnice między szablonem a urządzeniem raportowane jako niezgodność.'],
  ['Napisz, jak Arista EOS i Junos realizują odpowiedniki poleceń: configure terminal, show ip interface brief, copy run start, show mac address-table. Gdzie składnia znacząco się różni?', 'Tabela z ilustracji.', 'EOS: configure (lub configure terminal), show ip interface brief, write memory (lub copy running-config startup-config), show mac address-table — bardzo podobnie do IOS. Junos: configure (tryb konfiguracji kandydackiej), show interfaces terse, commit (zapisuje i stosuje; brak osobnego „startup-config”), show ethernet-switching table. Największa różnica: Junos używa hierarchii i poleceń set/delete (set interfaces ge-0/0/1 unit 0 family ethernet-switching vlan members USERS) oraz modelu commit/rollback.'],
  ['Wyjaśnij, czym różni się dostęp przez konsolę od dostępu przez vty i dlaczego firmy utrzymują sieć zarządzania out-of-band.', 'Konsola działa bez sieci produkcyjnej.', 'Konsola (port szeregowy/USB) daje dostęp lokalny niezależny od konfiguracji IP — działa, gdy urządzenie straciło łączność lub konfigurację. Vty to sesje zdalne (SSH) przez sieć, zależne od działającego IP, ACL, routingu. Out-of-band (osobna sieć zarządzania, serwery konsol, łącze LTE) pozwala naprawić awarię sieci produkcyjnej, która odcięła dostęp in-band — i ogranicza powierzchnię ataku na interfejsy zarządzania.'],
  ['Zinterpretuj wyjście: interfejs Gi1/0/7 ma w show interfaces „Full-duplex, 1000Mb/s”, rosnące liczniki „input errors” i „CRC”. Co to sugeruje i co zrobisz?', 'CRC = uszkodzone ramki na łączu fizycznym.', 'Błędy CRC i input errors na łączu bez niezgodności duplex wskazują problem fizyczny: uszkodzony lub zbyt długi kabel, zła końcówka, zakłócenia, uszkodzony SFP lub port. Działania: clear counters i obserwacja przyrostu, wymiana kabla/patchcordu, test kabla (test cable-diagnostics tdr), zamiana portu/SFP. Dla aplikacji objawem są retransmisje TCP i skoki opóźnień (lekcja 01/06 o metrykach).'],
  ['Przygotuj procedurę zmiany (change) dla dodania VLAN-u na trunku między dwoma przełącznikami produkcyjnymi: przygotowanie, wykonanie, weryfikacja, rollback.', 'allowed vlan add; kopia; okno serwisowe.', 'Przygotowanie: zgłoszenie z opisem i oknem serwisowym, kopia konfiguracji obu przełączników, sprawdzenie show interfaces trunk (aktualna lista), przygotowane polecenia z „add”. Wykonanie: vlan 30 / name VOICE na obu, interface Gi1/0/48 / switchport trunk allowed vlan add 30 na obu końcach. Weryfikacja: show interfaces trunk (VLAN 30 allowed and active), show spanning-tree vlan 30, test hosta w VLAN 30. Zapis: copy run start. Rollback: switchport trunk allowed vlan remove 30 i no vlan 30; w razie utraty dostępu — konsola/OOB.'],
];
for (const [q, h, a] of extra) ex(q, h, a);
const extra2 = [
  ['Podaj pełne nazwy i prędkości interfejsów: ' + ifNames.map((x) => x[0]).join(', ') + '. Jak ustalisz, który port jest uplinkiem?', 'Skróty w IOS pochodzą od typu interfejsu; uplink = sąsiad przełącznik.', ifNames.map(([s, f, sp]) => `${s} = ${f} (${sp})`).join('; ') + '. Uplink: show cdp/lldp neighbors (sąsiad to przełącznik/router), show interfaces trunk, opis portu (description), zwykle port o większej prędkości.'],
  ['Skonfiguruj port Gi1/0/10 dla serwera w VLAN 20: opis, tryb access, PortFast, BPDU Guard, i sprawdź wynik dwoma poleceniami show.', 'interface → description → switchport → spanning-tree.', 'conf t; interface Gi1/0/10; description SRV-APP-01 eth0; switchport mode access; switchport access vlan 20; switchport nonegotiate; spanning-tree portfast; spanning-tree bpduguard enable; no shutdown; end. Weryfikacja: show interfaces status | include Gi1/0/10 (connected, VLAN 20, a-full, a-1000), show running-config interface Gi1/0/10; po podłączeniu serwera show mac address-table interface Gi1/0/10.'],
  ['Wyjaśnij, czym są poziomy uprawnień 0–15 w IOS i jak bezpieczniej realizować podział uprawnień (np. helpdesk tylko z show) w dużej sieci.', 'privilege level; AAA authorization commands.', 'Poziom 1 to User EXEC, 15 to pełne uprawnienia; poziomy 2–14 można przypisać do wybranych poleceń (privilege exec level 5 show running-config), ale lokalne zarządzanie tym na wielu urządzeniach jest uciążliwe i podatne na błędy. W dużej sieci: AAA z TACACS+ i autoryzacją poleceń (aaa authorization commands 15) — serwer decyduje, które polecenia wolno wykonać grupie (np. helpdesk: tylko show i ping), a accounting rejestruje każde polecenie.'],
];
for (const [q, h, a] of extra2) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111606);
const modeVals = Object.values(MODES);
for (const [c, m] of cmdMode) {
  add(`W którym trybie wpisujesz polecenie „${c}”?`, MODES[m], modeVals.filter((v) => v !== MODES[m]).slice(0, 3), `„${c}” — ${MODES[m]}.`);
}
const prompts = [['SW1>', 'User EXEC'], ['SW1#', 'Privileged EXEC'], ['SW1(config)#', 'konfiguracja globalna'], ['SW1(config-if)#', 'konfiguracja interfejsu'], ['SW1(config-line)#', 'konfiguracja linii (konsola/vty)'], ['SW1(config-vlan)#', 'konfiguracja VLAN-u'], ['R1(config-router)#', 'konfiguracja protokołu routingu']];
const pAll = prompts.map((x) => x[1]);
for (const [p, d] of prompts) add(`Jaki tryb oznacza znak zachęty ${p}?`, d, pAll.filter((x) => x !== d).slice(0, 3), `${p} — ${d}.`);
const stAll = ifState.map((x) => x[2]);
for (const [s, p, d] of ifState) add(`Co oznacza Status „${s}” i Protocol „${p}” w show ip interface brief?`, d, stAll.filter((x) => x !== d), `${s}/${p}: ${d}.`);
const pwAll = pwTypes.map((x) => x[1]);
for (const [t, d] of pwTypes) add(`Czym jest hasło typu ${t} w konfiguracji Cisco IOS?`, d, pwAll.filter((x) => x !== d).slice(0, 3), `Typ ${t}: ${d}.`);
add('Które typy haseł Cisco są obecnie zalecane?', '8 (PBKDF2-SHA-256) i 9 (scrypt)', ['5 i 7', '0 i 7', 'tylko 7'], 'Typ 5 (MD5) jest przestarzały, 7 odwracalny, 0 jawny.');
const ifnAll = ifNames.map((x) => x[1]);
for (const [s, f, sp] of ifNames) add(`Jaka jest pełna nazwa interfejsu ${s}?`, f, ifnAll.filter((x) => x !== f).slice(0, 3), `${s} = ${f} (${sp}).`);
const filt = [['| include', 'pokazuje tylko linie zawierające wzorzec'], ['| exclude', 'ukrywa linie zawierające wzorzec'], ['| begin', 'pokazuje wyjście od pierwszej linii z wzorcem'], ['| section', 'pokazuje całe sekcje konfiguracji pasujące do wzorca']];
const fAll = filt.map((x) => x[1]);
for (const [f, d] of filt) add(`Co robi filtr wyjścia „${f}”?`, d, fAll.filter((x) => x !== d), `${f} — ${d}.`);
const shows = [
  ['show ip interface brief', 'adresy IP i stan wszystkich interfejsów w skrócie'],
  ['show vlan brief', 'VLAN-y i przypisane do nich porty access'],
  ['show interfaces trunk', 'porty trunk, native VLAN i dozwolone VLAN-y'],
  ['show mac address-table', 'nauczone adresy MAC z portem i VLAN-em'],
  ['show version', 'wersję oprogramowania, model, uptime i przyczynę restartu'],
  ['show cdp neighbors', 'sąsiednie urządzenia Cisco podłączone do portów'],
  ['show running-config', 'bieżącą konfigurację działającą w RAM'],
  ['show startup-config', 'konfigurację zapisaną w NVRAM'],
  ['show logging', 'bufor komunikatów systemowych (logów)'],
  ['show interfaces status', 'porty z opisem, stanem, VLAN-em, prędkością i duplexem'],
];
const sAll = shows.map((x) => x[1]);
for (const [c, d] of shows) add(`Co pokazuje polecenie ${c}?`, d, sAll.filter((x) => x !== d).slice(0, 3), `${c} — ${d}.`);
const facts = [
  ['Gdzie przechowywana jest running-config?', 'W RAM', ['W NVRAM', 'We Flash', 'Na serwerze TFTP'], 'Działa natychmiast, ginie po restarcie bez zapisu.'],
  ['Gdzie przechowywana jest startup-config?', 'W NVRAM', ['W RAM', 'W ROM', 'W pamięci podręcznej CPU'], 'Ładowana przy starcie.'],
  ['Gdzie przechowywany jest obraz systemu IOS?', 'We Flash', ['W NVRAM', 'W RAM', 'Na porcie konsoli'], 'Plik .bin lub pakiety IOS XE.'],
  ['Które polecenie zapisuje bieżącą konfigurację na stałe?', 'copy running-config startup-config', ['copy startup-config running-config', 'reload', 'erase startup-config'], 'Skrót: write memory (wr).'],
  ['Co się stanie z niezapisanymi zmianami po restarcie urządzenia?', 'Zostaną utracone — urządzenie wstanie ze startup-config', ['Zostaną zapisane automatycznie', 'Urządzenie nie uruchomi się', 'Zostaną przeniesione do Flash'], 'Dlatego copy run start po weryfikacji.'],
  ['Do czego służy reload in 10 przed ryzykowną zmianą zdalną?', 'Do automatycznego restartu do zapisanej konfiguracji, jeśli stracisz dostęp', ['Do przyspieszenia zmiany', 'Do zapisania konfiguracji za 10 minut', 'Do wylogowania wszystkich użytkowników'], 'Gdy wszystko działa — reload cancel.'],
  ['Jaki jest odpowiednik bezpiecznej zmiany z automatycznym wycofaniem w Junos?', 'commit confirmed', ['write memory', 'reload in', 'show compare'], 'Bez potwierdzenia nastąpi rollback.'],
  ['Które polecenie przenosi z User EXEC do Privileged EXEC?', 'enable', ['configure terminal', 'login', 'exit'], 'disable wraca do User EXEC.'],
  ['Które polecenie wraca z dowolnego podtrybu konfiguracji do Privileged EXEC?', 'end (lub Ctrl+Z)', ['exit', 'disable', 'logout'], 'exit cofa tylko o jeden poziom.'],
  ['Jak uruchomić show z trybu konfiguracji bez wychodzenia z niego?', 'Poprzedzić je słowem do (np. do show vlan brief)', ['Nie da się', 'Poprzedzić słowem run', 'Wpisać ! przed poleceniem'], 'do działa dla poleceń EXEC.'],
  ['Jak cofnąć ustawienie w konfiguracji IOS?', 'Wpisać to samo polecenie poprzedzone słowem no', ['Poleceniem undo', 'Poleceniem delete', 'Tylko przez restart'], 'Np. no shutdown, no ip http server.'],
  ['Dlaczego Telnet powinien być wyłączony?', 'Bo przesyła hasła i polecenia jawnym tekstem', ['Bo jest wolniejszy od SSH', 'Bo nie działa przez IPv6', 'Bo wymaga licencji'], 'transport input ssh.'],
  ['Które polecenie ogranicza linie vty tylko do SSH?', 'transport input ssh', ['ip ssh version 2', 'login local', 'service password-encryption'], 'W trybie line vty.'],
  ['Co jest potrzebne do wygenerowania klucza RSA dla SSH w IOS?', 'Ustawiony hostname (inny niż domyślny) i ip domain-name', ['Włączony Telnet', 'Serwer TFTP', 'Licencja Security'], 'Nazwa klucza powstaje z hostname i domeny.'],
  ['Co robi service password-encryption?', 'Zaciemnia jawne hasła w konfiguracji jako odwracalny typ 7', ['Szyfruje ruch SSH', 'Haszuje hasła algorytmem scrypt', 'Szyfruje plik startup-config'], 'To nie jest prawdziwa ochrona.'],
  ['Czym enable secret różni się od enable password?', 'enable secret zapisuje hasło jako skrót kryptograficzny, enable password — jawnie lub jako typ 7', ['Niczym', 'enable password jest silniejsze', 'enable secret działa tylko na konsoli'], 'Gdy ustawione oba, obowiązuje secret.'],
  ['Do czego służy exec-timeout na liniach?', 'Do automatycznego zamknięcia nieaktywnej sesji', ['Do ograniczenia czasu wykonania polecenia show', 'Do ustawienia zegara', 'Do opóźnienia logowania'], 'exec-timeout 0 0 wyłącza wygasanie — źle.'],
  ['Do czego służy access-class na liniach vty?', 'Do ograniczenia adresów, z których można się zalogować zdalnie', ['Do filtrowania ruchu przechodzącego przez przełącznik', 'Do szyfrowania sesji', 'Do zmiany VLAN-u zarządzania'], 'ACL standardowa z siecią administracyjną.'],
  ['Co daje archive log config?', 'Rejestrowanie poleceń konfiguracyjnych: kto, kiedy, co zmienił', ['Kompresję konfiguracji', 'Szyfrowanie logów', 'Automatyczny restart'], 'Ślad audytowy, najlepiej wysyłany do syslog/SIEM.'],
  ['Po co urządzeniu sieciowemu NTP?', 'Aby znaczniki czasu logów były poprawne i dało się je korelować', ['Aby szybciej przełączać ramki', 'Aby działało SSH', 'Aby wybrać root bridge'], 'Bez czasu analiza incydentu jest bardzo trudna.'],
  ['Która wersja SNMP zapewnia uwierzytelnianie i szyfrowanie?', 'SNMPv3 (authPriv)', ['SNMPv1', 'SNMPv2c', 'Żadna'], 'v1/v2c używają jawnego community.'],
  ['Co oznacza no ip domain-lookup?', 'Wyłącza rozwiązywanie nazw DNS, więc literówka nie powoduje długiego oczekiwania', ['Wyłącza serwer DNS w sieci', 'Blokuje ruch DNS przez przełącznik', 'Usuwa nazwę domeny'], 'Przydatne w labie.'],
  ['Który emulator uruchamia topologie z kontenerów opisane w YAML?', 'Containerlab', ['Packet Tracer', 'Wireshark', 'PuTTY'], 'Lab jako kod.'],
  ['Czym Packet Tracer różni się od Cisco Modeling Labs?', 'Packet Tracer symuluje IOS, a CML uruchamia prawdziwe obrazy systemów Cisco', ['Niczym', 'CML jest tylko do Wi-Fi', 'Packet Tracer emuluje wszystkich dostawców'], 'PT wystarcza do nauki CCNA.'],
  ['Jakie polecenie w Junos odpowiada show ip interface brief?', 'show interfaces terse', ['show ip brief', 'show int summary', 'display interface brief'], 'display interface brief to Huawei VRP.'],
  ['Czym jest AAA z TACACS+ na urządzeniach sieciowych?', 'Centralnym uwierzytelnianiem, autoryzacją poleceń i rejestrowaniem działań administratorów', ['Protokołem routingu', 'Systemem kopii zapasowych', 'Rodzajem VLAN-u'], 'Konta imienne zamiast wspólnego hasła.'],
  ['Dlaczego konfiguracje urządzeń sieciowych traktuje się jak sekrety?', 'Zawierają skróty haseł, klucze SNMP/TACACS+ i opis topologii', ['Bo są duże', 'Bo są w formacie binarnym', 'Bo zawierają licencje'], 'Nie publikuj ich w zgłoszeniach i repozytoriach publicznych.'],
  ['Co robi polecenie login block-for 120 attempts 5 within 60?', 'Blokuje logowanie na 120 s po 5 nieudanych próbach w ciągu 60 s', ['Wylogowuje po 120 s bezczynności', 'Pozwala na 120 sesji', 'Zmienia hasło co 120 dni'], 'Utrudnia zgadywanie haseł.'],
  ['Czym różni się konsola od linii vty?', 'Konsola to dostęp lokalny niezależny od sieci, vty to sesje zdalne przez IP', ['Niczym', 'Vty działa tylko w IPv6', 'Konsola wymaga SSH'], 'Konsola ratuje, gdy sieć nie działa.'],
  ['Co oznacza znak # w znaku zachęty?', 'Tryb uprzywilejowany (lub konfiguracji) z pełnymi uprawnieniami', ['Tryb tylko do odczytu', 'Komentarz', 'Błąd składni'], '> oznacza User EXEC.'],
  ['Co robi znak ? w CLI IOS?', 'Pokazuje dostępne polecenia lub argumenty w danym miejscu', ['Uruchamia pomoc online', 'Usuwa ostatnie polecenie', 'Zapisuje konfigurację'], 'Np. show ?, interface ?.'],
  ['Czy w IOS można skracać polecenia?', 'Tak, do jednoznacznego prefiksu (np. conf t, sh ip int br)', ['Nie, trzeba pisać pełne nazwy', 'Tylko w trybie konfiguracji', 'Tylko w trybie User EXEC'], 'Tab dopełnia pełną nazwę.'],
  ['Jak bezpiecznie przekazywać konfigurację do kopii zapasowej?', 'Protokołem szyfrowanym (SCP/SFTP) na serwer z kontrolą dostępu', ['Przez TFTP do dowolnego serwera', 'E-mailem bez szyfrowania', 'Wklejając do czatu zespołu'], 'TFTP nie ma uwierzytelniania ani szyfrowania.'],
  ['Co sugerują błędy CRC rosnące na interfejsie?', 'Problem fizyczny: kabel, złącze, SFP lub zakłócenia', ['Złą konfigurację VLAN-u', 'Brak trasy domyślnej', 'Błędny rekord DNS'], 'Najpierw wymień kabel.'],
  ['Dlaczego interfejs zarządzania urządzenia sieciowego nie powinien być dostępny z Internetu?', 'Bo jest celem zgadywania haseł i ataków na podatności oprogramowania', ['Bo spowalnia Internet', 'Bo nie obsługuje IPv4', 'Bo wymaga licencji'], 'Dostęp tylko z sieci zarządzania lub przez VPN/jump host.'],
  ['Jaki format adresu MAC pokazuje Cisco IOS?', 'xxxx.xxxx.xxxx (np. 0050.56ab.1234)', ['xx:xx:xx:xx:xx:xx', 'xx-xx-xx-xx-xx-xx', 'xxxxxx:xxxxxx'], 'Linux używa dwukropków, Windows myślników.'],
  ['Które polecenie pokaże różnice między running-config a startup-config?', 'show archive config differences', ['show diff', 'compare run start', 'show version'], 'Wymaga funkcji archive.'],
  ['Co robi erase startup-config?', 'Usuwa zapisaną konfigurację — po restarcie urządzenie wstanie bez konfiguracji', ['Czyści bieżącą konfigurację natychmiast', 'Usuwa obraz IOS', 'Czyści logi'], 'Używane przy przywracaniu do ustawień fabrycznych.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
add('Dlaczego przełącznik L2 potrzebuje ip default-gateway?', 'Aby odpowiadać na ruch zarządzania z innych podsieci — sam nie routuje', ['Aby przełączać ramki między VLAN-ami', 'Aby działał STP', 'Aby nadawać adresy DHCP'], 'Dotyczy wyłącznie ruchu do/z samego przełącznika.');
add('Który system Cisco działa na współczesnych przełącznikach Catalyst 9000 i ma API NETCONF/RESTCONF?', 'IOS XE', ['NX-OS', 'IOS 12.2', 'ASA OS'], 'IOS XE działa na jądrze Linux.');
add('Jaki model zmian ma Juniper Junos?', 'Konfiguracja kandydacka zatwierdzana poleceniem commit, z możliwością rollback', ['Każde polecenie działa natychmiast bez zapisu', 'Zmiany tylko przez GUI', 'Zmiany tylko po restarcie'], 'Bliski transakcjom w bazie danych.');
add('Co robi debug na mocno obciążonym urządzeniu produkcyjnym?', 'Może przeciążyć CPU i zawiesić urządzenie — używać ostrożnie i z filtrem', ['Przyspiesza przełączanie', 'Nic — debug jest zawsze bezpieczny', 'Automatycznie zapisuje konfigurację'], 'Po diagnozie: undebug all.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
