// Generuje lekcje _ns_02_ethernet_lan/12_WirelessArchitecturesAndControllers.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_12_wlan_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/12_WirelessArchitecturesAndControllers.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { round, beaconAirtimePct, POE, apsForClients, apsForArea } = require('./ns02_12_wlan_model.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const MODES = [
  ['local', 'AP obsługuje klientów, a cały ruch (sterowanie i dane) tuneluje CAPWAP do kontrolera'],
  ['FlexConnect', 'AP przełącza dane klientów lokalnie, do kontrolera wysyła tylko sterowanie; działa dalej przy utracie WAN'],
  ['monitor', 'AP nie obsługuje klientów, tylko skanuje kanały (WIPS, lokalizacja, wykrywanie rogue AP)'],
  ['sniffer', 'AP przechwytuje ramki z wybranego kanału i wysyła je do analizatora (np. Wireshark)'],
  ['bridge/mesh', 'AP łączy się z innymi AP bezprzewodowo, tworząc sieć kratową tam, gdzie nie ma kabla'],
];

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Od jednego routera Wi-Fi do tysięcy punktów dostępowych', ...img('ns02-12-architectures.svg', 'Trzy panele: autonomiczne AP konfigurowane osobno przez administratora; lightweight AP połączone tunelami CAPWAP z kontrolerem WLC zapewniającym RRM, roaming, WIPS i polityki; AP zarządzane z chmury przez HTTPS z ruchem danych pozostającym lokalnie. Przykłady: Cisco Catalyst 9800, Aruba, Juniper Mist, Cisco Meraki, UniFi.'),
    body:
`W domu masz jeden router z Wi-Fi i konfigurujesz go przez przeglądarkę. W firmie z trzema piętrami, magazynem i dziesięcioma oddziałami są **setki punktów dostępowych (AP)**, które muszą:
- nadawać te same SSID z tymi samymi zasadami bezpieczeństwa,
- automatycznie dobierać kanały i moc, żeby sobie nie przeszkadzać (**RRM — Radio Resource Management**),
- przekazywać klientów między sobą bez zrywania połączeń (**roaming**),
- wykrywać obce AP i ataki (**WIPS**),
- dawać jeden widok: kto, gdzie, z jakim sygnałem i z jakimi problemami.

Dlatego powstały trzy architektury: **autonomiczne AP** (każdy osobno — dobre do 1–3 sztuk), **lightweight AP z kontrolerem WLC** (AP jest „głupi”, decyzje podejmuje kontroler; komunikacja przez tunel **CAPWAP**) oraz **zarządzanie z chmury** (AP pobiera konfigurację z portalu producenta, dane klientów zostają lokalnie).

Dla Java developera to wiedza praktyczna: Twoja aplikacja w biurze czy magazynie (skanery, tablety, terminale) działa w **VLAN-ie przypisanym do SSID**, ruch może iść **tunelem do kontrolera w centrali** (dodatkowe opóźnienie, MTU!), a roaming między AP decyduje o tym, czy połączenie WebSocket przeżyje spacer po hali. Dla blue teamu kontroler to centrum polityk Wi-Fi: 802.1X i role, izolacja gości, WIPS, logi — a jednocześnie krytyczny system, którego interfejs zarządzania musi być chroniony.

W tej lekcji: tryby AP, CAPWAP i jego porty, local vs FlexConnect, chmura, mapowanie SSID na VLAN, roaming 802.11r/k/v, sieć gości, PoE i planowanie liczby AP, oraz zadanie z planu — mapowanie trzech SSID na VLAN-y i polityki. Cele: CCNA 200-301 v2 (D2: architektury bezprzewodowe, tryby AP, połączenia AP i WLC, dostęp do zarządzania WLC, konfiguracja WLAN z WPA2/WPA3 przez GUI) i Network+ (D2: kontrolery, mesh, site survey).` },

  { type: 'DEFINITION', heading: 'Słownik architektur Wi-Fi', body:
`- **Autonomiczny AP (fat AP)** — pełna konfiguracja w każdym urządzeniu; sam realizuje uwierzytelnianie, VLAN-y, kanały.
- **Lightweight AP (thin AP)** — AP bez lokalnej konfiguracji; po starcie odnajduje kontroler i pobiera od niego wszystko (**split MAC**: czasowo krytyczne funkcje 802.11 — beacony, ACK, szyfrowanie — w AP; asocjacja, uwierzytelnianie, polityki, RRM — w kontrolerze).
- **WLC (Wireless LAN Controller)** — kontroler sprzętowy, wirtualny lub wbudowany (Cisco Catalyst 9800, Aruba Mobility Controller, UniFi Network Application).
- **CAPWAP (RFC 5415)** — protokół tunelu AP ↔ WLC: **UDP 5246** sterowanie (szyfrowane **DTLS**), **UDP 5247** dane (opcjonalnie DTLS). Następca LWAPP.
- **Wykrywanie kontrolera** — przez DHCP opcja 43, DNS (\`CISCO-CAPWAP-CONTROLLER.<domena>\`), broadcast w tej samej podsieci, wcześniej zapisany adres.
- **Tryby AP**: ${MODES.map(([m]) => m).join(', ')}.
- **Central switching vs local switching** — ruch klienta tunelowany do kontrolera vs wypuszczany lokalnie do VLAN-u przy AP.
- **Zarządzanie z chmury** — Cisco Meraki, Juniper Mist, Aruba Central, Ubiquiti UniFi Cloud: płaszczyzna zarządzania w chmurze (HTTPS), płaszczyzna danych lokalnie.
- **RRM** — automatyczny dobór kanałów (DCA) i mocy (TPC), wykrywanie dziur w pokryciu.
- **Mapowanie SSID → VLAN (WLAN → interface)** — każdy SSID wysyła ruch do określonego VLAN-u; możliwe dynamiczne przypisanie VLAN-u/roli z RADIUS (802.1X).
- **Roaming**: **802.11r** (FT — szybkie przejście bez pełnego 802.1X), **802.11k** (raport sąsiednich AP), **802.11v** (BSS Transition — sieć sugeruje lepszy AP); roaming L2 (ten sam VLAN) vs L3 (inna podsieć — kontroler tuneluje ruch, by klient zachował adres).
- **PoE** — zasilanie AP przez Ethernet: 802.3af 15,4 W, 802.3at (PoE+) 30 W, 802.3bt 60/90 W (na porcie przełącznika).
- **Site survey** — pomiar/predykcja pokrycia i pojemności przed i po wdrożeniu (Ekahau, Hamina, AirMagnet).` },

  { type: 'ANALOGY', heading: 'Sieć restauracji: każdy szef kuchni sam vs centrala z przepisami', body:
`- **Autonomiczne AP** to sieć restauracji, w której **każdy szef kuchni ma własne przepisy** i sam ustala menu. Przy trzech lokalach da się to ogarnąć. Przy stu — każdy lokal serwuje co innego, zmiana ceny wymaga stu telefonów, a klient karty lojalnościowej z jednego lokalu nie jest rozpoznawany w drugim (brak wspólnego roamingu).
- **Lightweight AP + WLC** to **centrala z jedną książką przepisów**: kuchnie (AP) tylko gotują według instrukcji, a centrala (kontroler) decyduje o menu (SSID), zasadach wejścia (802.1X) i grafiku (kanały, moc — RRM). Zamówienia z sali mogą iść **do centrali i z powrotem** (local mode — cały ruch przez kontroler) albo kuchnia może **sama wydawać dania**, a centrali tylko raportować (FlexConnect — dane lokalnie). Gdy telefon do centrali padnie, kuchnia FlexConnect dalej gotuje według ostatnich instrukcji.
- **Zarządzanie z chmury** to centrala w innym kraju, która przez internet aktualizuje przepisy w tabletach kuchni — dania (dane klientów) nie podróżują do centrali.
- **SSID → VLAN** to osobne sale: dla stałych klientów (CORP), dla gości z ulicy (GUEST — tylko do baru, bez wstępu na zaplecze), dla dostawców (IoT — tylko do rampy).
- **Roaming 802.11r/k/v** to kelner, który przekazuje stolik koledze z listą zamówień (r), mówi gościom, gdzie są wolne miejsca obok (k), i grzecznie proponuje przesiadkę bliżej (v).` },

  { type: 'VISUAL_EXAMPLE', heading: 'Policz: narzut beaconów, budżet PoE i liczba AP', ...img('ns02-12-beacons.svg', 'Wykres narzutu beaconów na czas antenowy: przy prędkości podstawowej 1 Mb/s od 2,8% dla 1 SSID i 1 AP do 89% dla 8 SSID i 4 AP; przy 24 Mb/s pojedyncze procenty. Wniosek: ograniczać liczbę SSID i wyłączać najniższe prędkości.'),
    body:
`Trzy obliczenia z projektowania sieci bezprzewodowej. Model beaconów jest dydaktyczny (beacon 350 B, ~9,77 beaconu/s na SSID na AP, 50 µs narzutu), ale dobrze pokazuje, dlaczego „po jednym SSID na każdy dział” to zły pomysł.`,
    code:
`Narzut beaconów (procent czasu antenowego kanału):
${[[1, 1], [3, 3], [5, 3], [8, 4]].map(([s, a]) => `  ${s} SSID × ${a} AP w zasięgu: ${beaconAirtimePct(s, a, 350, 1)}% przy 1 Mb/s, ${beaconAirtimePct(s, a, 350, 6)}% przy 6 Mb/s, ${beaconAirtimePct(s, a, 350, 24)}% przy 24 Mb/s`).join('\n')}

Budżet PoE przełącznika (moc na porcie przełącznika):
${Object.entries(POE).map(([k, v]) => `  ${k.padEnd(24)} ${v} W`).join('\n')}
  24 AP Wi-Fi 6E (po ~25 W, PoE+): 24 × 25 = 600 W  → przełącznik z budżetem 740 W wystarczy,
                                                         z budżetem 370 W — NIE (część AP nie wstanie lub ograniczy radia)

Liczba AP — liczy się pojemność, nie tylko zasięg:
  pokrycie: hala 3000 m², komórka ~250 m²                → ${apsForArea(3000, 250)} AP
  pojemność: 400 urządzeń, ~30 aktywnych klientów na AP → ${apsForClients(400, 30)} AP
  projekt = większa z wartości (${Math.max(apsForArea(3000, 250), apsForClients(400, 30))}) + korekta po site survey` },

  { type: 'CODE_BASIC', heading: 'Konfiguracja WLAN na kontrolerze (Cisco Catalyst 9800 — CLI)', body:
`Na egzaminie CCNA konfiguruje się WLAN przez GUI kontrolera (WLAN → Security → Advanced), ale CLI pokazuje strukturę: **WLAN** (SSID i zabezpieczenia) + **policy profile** (VLAN, central/local switching) + **policy tag** (powiązanie WLAN z profilem) przypisany do AP.`,
    code:
`! 1) WLAN: SSID i zabezpieczenia
wlan CORP 1 CORP
 no security wpa wpa2
 security wpa wpa3
 security wpa akm dot1x-sha256
 security pmf mandatory
 security ft                                  ! 802.11r
 no shutdown
wlan GUEST 2 GUEST
 no security wpa
 security owe                                 ! Enhanced Open (szyfrowanie bez hasła)
 security pmf mandatory
 no shutdown
!
! 2) Policy profile: VLAN i sposób przełączania
wireless profile policy PP-CORP
 vlan 10
 central switching                            ! lub: no central switching (FlexConnect)
 no shutdown
wireless profile policy PP-GUEST
 vlan 60
 peer-blocking drop                           ! izolacja klientów
 service-policy input  GUEST-10M              ! limit przepustowości
 no shutdown
!
! 3) Policy tag: które WLAN z którym profilem, i przypisanie do AP
wireless tag policy PT-HQ
 wlan CORP policy PP-CORP
 wlan GUEST policy PP-GUEST
ap 7069.5a00.1111
 policy-tag PT-HQ
!
show wireless summary ; show ap summary ; show wlan summary ; show wireless client summary` },

  { type: 'STEP_BY_STEP', heading: 'Zadanie z planu: zaprojektuj mapowanie 3 SSID na VLAN-y i polityki', ...img('ns02-12-ssid-vlan.svg', 'Projekt mapowania: SSID CORP (WPA3-Enterprise, EAP-TLS) → VLAN 10 z dostępem do aplikacji wewnętrznych; SSID GUEST (OWE/portal, izolacja klientów) → VLAN 60 tylko do Internetu z limitem; SSID IOT (osobne PSK) → VLAN 50 tylko do brokera MQTT i NTP. Każdy SSID kosztuje czas antenowy, 3–4 SSID to rozsądne maksimum.'),
    body:
`Ćwiczenie projektowe — wynik zapisz jako dokument (lub plik w repozytorium) z tabelą i regułami:
1. **Zbierz wymagania**: kto korzysta (pracownicy z laptopami i telefonami, goście, urządzenia IoT: czujniki, drukarki, skanery), do czego potrzebuje dostępu, ile urządzeń, jakie wymagania (roaming dla skanerów, VoIP).
2. **Zdefiniuj SSID** — możliwie mało (beacony!): CORP, GUEST, IOT. Dodatkowe rozróżnienia (dział, rola) realizuj **rolami z 802.1X/RADIUS** (dynamiczny VLAN, ACL per rola), a nie kolejnymi SSID.
3. **Zabezpieczenia**: CORP — WPA3-Enterprise (lub WPA2/3 transition) z EAP-TLS i PMF; GUEST — OWE lub portal z akceptacją regulaminu, izolacja klientów; IOT — WPA2/3-Personal z osobnym, długim PSK (lub iPSK/MPSK — osobny klucz per urządzenie).
4. **VLAN-y i podsieci**: CORP VLAN 10 (/22 — laptopy + telefony), GUEST VLAN 60 (/22, krótka dzierżawa DHCP), IOT VLAN 50 (/24). Zapisz bramy i serwery DHCP (lekcja 10).
5. **Polityki (firewall/ACL/role)**: GUEST → tylko Internet (blokada RFC 1918), limit przepustowości; IOT → tylko broker MQTT (8883), NTP, serwer aktualizacji; brak Internetu i brak dostępu do CORP; CORP → aplikacje wewnętrzne wg ról.
6. **Przełączanie**: centrala — central lub local switching; oddziały — FlexConnect z lokalnymi VLAN-ami (GUEST może być tunelowany do DMZ w centrali — „guest anchor”).
7. **Roaming**: 802.11r/k/v dla CORP (skanery, VoIP), sprawdź kompatybilność starszych klientów IoT (FT bywa problemem — dlatego IOT bez 802.11r).
8. **Monitoring**: logi RADIUS i WLC do SIEM, WIPS (rogue AP, evil twin z SSID CORP), alerty o wyczerpaniu puli DHCP w GUEST.` },

  { type: 'NOTE', heading: 'Tryby pracy AP i porty, które musi przepuścić firewall', body:
`Tryby AP (terminologia Cisco, odpowiedniki istnieją u innych producentów) i komunikacja, którą trzeba uwzględnić w regułach między sieciami AP a kontrolerem/chmurą:`,
    code:
`${MODES.map(([m, d]) => `${m.padEnd(12)} ${d}`).join('\n')}

Porty i protokoły:
  CAPWAP sterowanie   UDP 5246 (DTLS)        AP → WLC
  CAPWAP dane         UDP 5247               AP → WLC (central switching)
  Mobility (WLC↔WLC)  UDP 16666/16667        roaming L3 między kontrolerami
  RADIUS              UDP 1812/1813          WLC → serwer NAC (uwierzytelnianie, accounting)
  Chmura (Meraki/Mist) TCP 443 (+ np. UDP 7351 Meraki)  AP → Internet, tylko wychodzące
  Zarządzanie WLC     HTTPS 443, SSH 22, NETCONF 830 — tylko z sieci zarządzania

MTU: CAPWAP dodaje nagłówki (IP + UDP + CAPWAP + 802.11/802.3) → ok. 50–80 B; przy central switching przez WAN
     z MTU 1500 grozi fragmentacja — kontrolery stosują PMTUD/fragmentację CAPWAP (lekcja 01 o MTU).` },

  { type: 'CODE_PRACTICAL', heading: 'Automatyzacja i obserwowalność: API kontrolera i chmury', body:
`Współczesne kontrolery i platformy chmurowe mają API REST i strumienie zdarzeń — z perspektywy Java developera to normalne źródło danych: inwentarz AP, klienci, zdarzenia roamingu, alerty WIPS. Przykład klienta w Javie dla API typu „lista klientów z sygnałem” (struktura wzorowana na API platform chmurowych — sprawdź dokumentację swojej platformy):`,
    code:
`import java.net.URI;
import java.net.http.*;
import com.fasterxml.jackson.databind.*;

public class WifiClientsReport {
    record WifiClient(String mac, String ssid, String apName, int rssi, String ip, int vlan) {}

    public static void main(String[] args) throws Exception {
        String token = System.getenv("WIFI_API_TOKEN");           // nigdy w kodzie ani w repozytorium
        HttpClient http = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(URI.create("https://wifi.example.com/api/v1/sites/hq/clients"))
            .header("Authorization", "Bearer " + token)
            .GET().build();
        JsonNode arr = new ObjectMapper().readTree(http.send(req, HttpResponse.BodyHandlers.ofString()).body());
        for (JsonNode c : arr) {
            var wc = new WifiClient(c.path("mac").asText(), c.path("ssid").asText(), c.path("ap").asText(),
                                    c.path("rssi").asInt(), c.path("ip").asText(), c.path("vlan").asInt());
            if (wc.rssi() < -75)                                // słaby sygnał → kandydaci do problemów z aplikacją
                System.out.printf("SŁABY SYGNAŁ %s (%s) przy %s: %d dBm%n", wc.mac(), wc.ssid(), wc.apName(), wc.rssi());
            if (wc.ssid().equals("GUEST") && wc.vlan() != 60)   // kontrola zgodności mapowania SSID → VLAN
                System.out.printf("NIEZGODNOŚĆ: klient GUEST %s w VLAN %d%n", wc.mac(), wc.vlan());
        }
    }
}
// Cisco 9800: RESTCONF/NETCONF (YANG Cisco-IOS-XE-wireless-*), telemetria gNMI; Meraki Dashboard API; Mist API + webhooki.` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', ...img('ns02-12-flexconnect.svg', 'Dwa panele: w trybie local AP przesyła tunelem CAPWAP sterowanie i cały ruch klientów do kontrolera w centrali, więc ruch idzie przez WAN; w trybie FlexConnect CAPWAP przenosi tylko sterowanie, a dane klienta trafiają do lokalnego przełącznika oddziału. CAPWAP: UDP 5246 i 5247.'),
    body:
`**Developer / DevOps:**
- **Aplikacje w magazynach, szpitalach, sklepach** — terminale i skanery (często Android, Java/Kotlin) wędrują po hali; roaming 802.11r/k/v i jeden VLAN dla SSID w całym obszarze decydują, czy sesja przetrwa. Aplikacja i tak musi mieć retry i wznawianie (lekcja 11).
- **Ruch tunelowany do kontrolera** — w oddziale z central switching każdy pakiet klienta Wi-Fi jedzie przez WAN do centrali i z powrotem: dodatkowe opóźnienie, zależność od łącza, MTU. Jeśli aplikacja w oddziale „wolno działa tylko na Wi-Fi” — sprawdź tryb przełączania.
- **Adres IP i VLAN klienta** zależą od SSID i roli z RADIUS — reguły firewalla dla Twojej usługi muszą obejmować właściwe podsieci (np. skanery z VLAN 50 do API magazynu).
- **Integracje** — API kontrolera/chmury: inwentarz, lokalizacja (RTLS), analityka obecności, webhooki o zdarzeniach; tokeny API to sekrety.
- **Laby** — wirtualny kontroler (Catalyst 9800-CL), UniFi Network Application w Dockerze, OpenWrt na tanim AP — dobre do nauki.

**Blue team:**
- **Kontroler to klejnot koronny** — przejęcie WLC lub konta w chmurze oznacza kontrolę nad całą siecią bezprzewodową (nowe SSID, wyłączenie zabezpieczeń, przekierowanie ruchu). Zarządzanie tylko z sieci administracyjnej, MFA dla portali chmurowych, RBAC, aktualizacje (kontrolery bywały celem krytycznych podatności — sprawdzaj biuletyny i CISA KEV).
- **WIPS** — wykrywanie rogue AP (podłączonych do LAN), evil twin z firmowym SSID, ataków deauth (bez PMF), ad-hoc i hotspotów; AP w trybie monitor zwiększają skuteczność. Uwaga prawna: aktywne „containment” obcych AP może naruszać przepisy — tylko w uzasadnionych przypadkach i we własnej przestrzeni.
- **Sieć gości** — izolacja klientów, osobny VLAN, często **guest anchor** (tunel do kontrolera w DMZ), ograniczenia przepustowości, brak dostępu do RFC 1918, rejestracja zgód (portal).
- **Logi** — asocjacje, uwierzytelnienia 802.1X (sukces/porażka), roaming, wykrycia WIPS → SIEM; odpowiadają na pytania „kto był w sieci”, „skąd próby logowania”.
- **Segmentacja i role** — dynamiczny VLAN/ACL z RADIUS (np. Cisco ISE, Aruba ClearPass, FreeRADIUS) zamiast mnożenia SSID; mikrosegmentacja w obrębie VLAN-u.` },

  { type: 'PITFALL', heading: 'Częste błędy w architekturach Wi-Fi', body:
`- **Za dużo SSID** — „po jednym na dział” zjada czas antenowy na beacony (szczególnie przy niskich prędkościach podstawowych); rozdzielaj rolami 802.1X.
- **Central switching w oddziałach za wolnym WAN** — cały ruch lokalnych drukarek i aplikacji jedzie przez centralę; awaria WAN wyłącza Wi-Fi oddziału. FlexConnect lub chmura.
- **Brak zgodności VLAN-ów przy local switching** — SSID mapowany na VLAN 50, a port przełącznika AP nie przenosi VLAN-u 50 (trunk allowed, lekcja 05) → klienci łączą się, ale nie dostają adresu.
- **Firewall blokuje CAPWAP** (UDP 5246/5247) lub DHCP opcja 43/DNS nie wskazuje kontrolera — AP nie dołączają („AP join failed”).
- **Za mały budżet PoE** — AP startują w trybie ograniczonym (wyłączone radio, mniej strumieni) albo w ogóle; licz moc na porcie przełącznika, nie z ulotki AP.
- **802.11r włączone dla starych klientów** — część urządzeń IoT/skanerów nie potrafi się połączyć z SSID z FT; stosuj tryb adaptacyjny/mixed lub osobne ustawienia.
- **Roaming L3 bez planu** — klienci przechodzą do AP mapujących ten sam SSID na inną podsieć, zmieniają adres i zrywają sesje; projektuj obszary roamingu z jednym VLAN-em lub mobilnością kontrolerów.
- **Sieć gości bez izolacji** — goście widzą się nawzajem (ataki między urządzeniami, mDNS/SSDP z lekcji 04).
- **Domyślne hasła i zarządzanie z sieci użytkowników** — interfejs WLC/AP dostępny z VLAN-u CORP lub, gorzej, GUEST.
- **Projekt tylko pod zasięg** — „sygnał wszędzie jest” nie znaczy, że 300 osób w sali konferencyjnej dostanie przepustowość; licz pojemność.` },

  { type: 'CODE_WRONG', heading: 'Źle: SSID dla każdego działu, goście bez izolacji, PSK wszędzie', body:
`Konfiguracja, która rośnie „organicznie” przez lata.`,
    code:
`SSID                 zabezpieczenia     VLAN   uwagi
FIRMA-KSIEGOWOSC     WPA2-PSK           10     hasło to nazwa firmy + rok
FIRMA-IT             WPA2-PSK           11     hasło znane byłym pracownikom
FIRMA-HANDLOWCY      WPA2-PSK           12
FIRMA-ZARZAD         WPA2-PSK           13
FIRMA-DRUKARKI       WPA2-PSK           10     ten sam VLAN co księgowość
FIRMA-GOSCIE         otwarta            10     !!! goście w VLAN-ie księgowości, bez izolacji
FIRMA-MAGAZYN        WPA (TKIP)         14     „bo stare skanery”
FIRMA-TEST           WPA2-PSK           1      zapomniana, VLAN 1
! Skutki: 8 SSID × kilka AP = kilkadziesiąt procent czasu antenowego na beacony przy 1 Mb/s,
! hasła PSK współdzielone i nieodwoływalne, goście w sieci księgowości, TKIP podatny,
! brak ról i rozliczalności (kto się łączył), WLC zarządzany z VLAN-u 10.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: 3 SSID, role z RADIUS, izolacja i chroniony kontroler', body:
`Mało SSID, rozróżnienie użytkowników rolami z 802.1X, osobne strefy dla gości i IoT, PMF, ograniczenie niskich prędkości i bezpieczne zarządzanie.`,
    code:
`SSID    zabezpieczenia                     VLAN / rola                     polityka
CORP    WPA3-Enterprise, EAP-TLS, PMF, FT  RADIUS: rola → VLAN 10/11/13    ACL per rola (księgowość → ERP, IT → zarządzanie)
GUEST   OWE + portal, PMF, izolacja        VLAN 60 (guest anchor w DMZ)    tylko Internet, 10 Mb/s, DHCP 1 h
IOT     WPA2/3-Personal, iPSK per urządz.  VLAN 50 (lub 51 dla skanerów)   tylko MQTT/NTP/aktualizacje, bez Internetu

Radio: 2,4 GHz kanały 1/6/11, 20 MHz; 5/6 GHz 40/80 MHz z RRM; minimalna prędkość podstawowa 12–24 Mb/s.
Kontroler: zarządzanie tylko z VLAN 99 (MGMT), AAA przez TACACS+, MFA w portalu chmurowym, aktualne oprogramowanie.
WIPS: alert na SSID CORP nadawany przez obcy BSSID (evil twin), rogue AP na przewodowym LAN-ie.
Logi: RADIUS accounting + zdarzenia WLC → SIEM.` },

  { type: 'WHEN_TO_USE', heading: 'Którą architekturę wybrać', body:
`- **Autonomiczne AP** — dom, bardzo małe biuro (1–3 AP), miejsca bez zaplecza IT; ręczna konfiguracja, ograniczony roaming.
- **Kontroler on-premises (WLC)** — kampusy, szpitale, magazyny, fabryki: pełna kontrola, przewidywalny roaming, integracja z NAC, praca bez Internetu; wymaga kompetencji i HA kontrolerów (para N+1/SSO).
- **Zarządzanie z chmury** — wiele małych lokalizacji (sieci sklepów, oddziały), mały zespół IT, szybkie wdrożenia; zależność od dostawcy i subskrypcji, dane telemetryczne w chmurze dostawcy (RODO!), ruch danych lokalnie.
- **Tryb przełączania**: central switching — gdy kontroler jest blisko (kampus) i chcesz centralnej kontroli ruchu; local/FlexConnect — oddziały za WAN, duże przepustowości lokalne.
- **Liczba SSID**: 3–4 maksymalnie; rozróżnienia przez 802.1X i role.
- **Pojemność**: planuj liczbę AP z gęstości klientów i aplikacji (wideo, VoIP), potem site survey; pamiętaj o PoE.
- **Dla developera**: dowiedz się, w jakim VLAN-ie i z jakim trybem przełączania pracują klienci Twojej aplikacji, jakie są obszary roamingu i jakie reguły firewalla dotyczą ich podsieci — to skraca diagnozę „na Wi-Fi nie działa” o dni.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Architektury: autonomiczne AP (osobno), lightweight AP + WLC (split MAC, CAPWAP), zarządzanie z chmury (zarządzanie w chmurze, dane lokalnie).
- CAPWAP: UDP 5246 sterowanie (DTLS), UDP 5247 dane; wykrywanie WLC przez DHCP opcja 43, DNS, broadcast.
- Tryby AP: local, FlexConnect, monitor, sniffer, bridge/mesh. Central vs local switching — opóźnienie, zależność od WAN, MTU.
- SSID → VLAN (policy profile), dynamiczne VLAN-y i role z RADIUS; 3–4 SSID maksymalnie (beacony zjadają czas antenowy).
- Roaming: 802.11r (FT), 802.11k (sąsiedzi), 802.11v (sugestie); jeden VLAN w obszarze roamingu lub mobilność L3.
- Planowanie: liczba AP = max(pokrycie, pojemność), budżet PoE (af 15,4 W, at 30 W, bt 60/90 W), site survey.
- Bezpieczeństwo: chroniony kontroler/portal (MFA, sieć zarządzania, aktualizacje), WIPS, izolacja gości, guest anchor, logi do SIEM.
- Następna lekcja: **Debug Lab — sieć lokalna nie działa** (zakończenie rozdziału).` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [s, a, bytes, rate] of [[4, 3, 350, 1], [4, 3, 350, 12], [2, 5, 400, 6]]) ex(`Oblicz w modelu z lekcji narzut beaconów: ${s} SSID, ${a} AP słyszalnych na kanale, beacon ${bytes} B, prędkość podstawowa ${rate} Mb/s.`, 'SSID × AP × 9,77/s × (bajty·8/prędkość + 50 µs).', `${s} × ${a} × 9,77 × (${bytes * 8}/${rate} + 50) µs/s = ${beaconAirtimePct(s, a, bytes, rate)}% czasu antenowego. ${beaconAirtimePct(s, a, bytes, rate) > 10 ? 'Dużo — ogranicz liczbę SSID lub podnieś prędkość podstawową.' : 'Akceptowalnie.'}`);
for (const [n, w, budget] of [[24, 25.5, 740], [48, 13, 370], [16, 51, 740]]) ex(`Przełącznik z budżetem PoE ${budget} W ma zasilić ${n} AP pobierających po ${w} W. Czy wystarczy i jakiego standardu PoE wymagają AP?`, 'Suma mocy vs budżet; standard wg mocy na porcie.', `Potrzeba ${round(n * w)} W przy budżecie ${budget} W → ${n * w <= budget ? 'wystarczy' : 'NIE wystarczy'} (zapas ${round(budget - n * w)} W). Standard: ${w <= 15.4 ? '802.3af (15,4 W)' : w <= 30 ? '802.3at PoE+ (30 W)' : w <= 60 ? '802.3bt typ 3 (60 W)' : '802.3bt typ 4 (90 W)'}.`);
for (const [area, cell, clients, per] of [[2000, 200, 150, 30], [5000, 300, 900, 40]]) ex(`Biuro ${area} m², komórka AP ~${cell} m², ${clients} urządzeń, ~${per} aktywnych klientów na AP. Ile AP zaplanujesz na start?`, 'max(pokrycie, pojemność).', `Pokrycie: ${apsForArea(area, cell)} AP; pojemność: ${apsForClients(clients, per)} AP → plan ${Math.max(apsForArea(area, cell), apsForClients(clients, per))} AP, korekta po site survey (ściany, sale konferencyjne o dużej gęstości).`);
const more = [
  ['Wykonaj zadanie z planu lekcji: zaprojektuj mapowanie 3 SSID (pracownicy, goście, IoT) na VLAN-y i polityki dla firmy z centralą i dwoma oddziałami. Przedstaw tabelę i reguły.', 'SSID, zabezpieczenia, VLAN/podsieć, tryb przełączania, polityki, roaming, monitoring.', 'CORP: WPA3-Enterprise EAP-TLS, PMF, 802.11r/k/v; RADIUS przypisuje role → VLAN 10 (pracownicy), 11 (IT); centrala central switching, oddziały FlexConnect z lokalnym VLAN 10. GUEST: OWE + portal, izolacja klientów, VLAN 60 tunelowany do guest anchor w DMZ centrali (jedno wyjście do Internetu, jedna polityka), limit 10 Mb/s, DHCP 1 h. IOT: WPA2/3-Personal z iPSK, VLAN 50 lokalnie w każdej lokalizacji, ACL: tylko MQTT 8883 do brokera, NTP, aktualizacje; brak Internetu i CORP. Monitoring: WIPS, logi RADIUS/WLC do SIEM, alerty o pulach DHCP.'],
  ['AP w nowym oddziale nie dołącza do kontrolera w centrali (status „joining”). Wymień kolejne kroki diagnozy.', 'Adres AP, wykrywanie WLC, CAPWAP przez firewall, DTLS, czas.', '1) Czy AP dostał adres IP (DHCP w VLAN-ie zarządzania AP)? 2) Czy zna adres WLC: DHCP opcja 43, rekord DNS CISCO-CAPWAP-CONTROLLER.<domena>, statyczna konfiguracja? 3) Czy firewall/ACL między oddziałem a centralą przepuszcza UDP 5246/5247? 4) Czy MTU ścieżki nie psuje DTLS (fragmentacja przez VPN)? 5) Czy czas na AP/WLC jest poprawny (certyfikaty DTLS)? 6) Czy kontroler ma licencje/limity i obsługuje model AP? 7) Logi WLC (show wireless stats ap join summary) i konsola AP.'],
  ['Klienci SSID IOT w oddziale łączą się z siecią, ale nie dostają adresu IP. AP pracuje w FlexConnect z local switching na VLAN 50. Co sprawdzisz?', 'VLAN na trunku do AP, DHCP w VLAN 50.', 'Port przełącznika do AP musi być trunkiem z VLAN 50 w allowed (i właściwym native VLAN dla zarządzania AP — lekcja 05); VLAN 50 musi istnieć na przełączniku; w VLAN 50 musi działać DHCP (serwer lub relay ip helper-address — lekcja 10); mapowanie VLAN-u w profilu FlexConnect musi zgadzać się z numerem VLAN-u oddziału. Diagnoza: show interfaces trunk, debug klienta na WLC (stan „IP learn”), tcpdump na porcie.'],
  ['Wyjaśnij różnicę między roamingiem L2 i L3 oraz jak kontroler pozwala klientowi zachować adres IP przy przejściu do AP mapującego SSID na inną podsieć.', 'Mobility anchor/foreign.', 'Roaming L2: nowy AP mapuje SSID na ten sam VLAN/podsieć — klient zachowuje adres, wystarczy szybkie przeuwierzytelnienie (802.11r). Roaming L3: nowy AP/kontroler mapuje SSID na inną podsieć — bez dodatkowych mechanizmów klient musiałby zmienić adres (zerwane sesje). Kontrolery tworzą tunel mobility: kontroler „foreign” (nowy) przekazuje ruch klienta do kontrolera „anchor” (pierwotny), który nadal wypuszcza go do starej podsieci — klient zachowuje IP.'],
  ['Porównaj kontroler on-premises i zarządzanie z chmury pod kątem: dostępności przy utracie Internetu, prywatności danych, kosztów i bezpieczeństwa konta administratora.', 'Płaszczyzna zarządzania vs danych.', 'Dostępność: on-prem działa bez Internetu; w chmurze AP zwykle nadal obsługują klientów (dane lokalnie), ale bez zmian konfiguracji i części funkcji (portal gości, uwierzytelnianie w chmurze). Prywatność: telemetria (MAC, lokalizacja, nazwy urządzeń) trafia do dostawcy — RODO, umowy powierzenia. Koszty: on-prem — sprzęt/licencje i zespół; chmura — subskrypcje per AP. Bezpieczeństwo: konto w portalu chmurowym kontroluje wszystkie lokalizacje — MFA, SSO, RBAC, alerty logowań są obowiązkowe; on-prem — ochrona interfejsu zarządzania i aktualizacje.'],
  ['Wyjaśnij, czym jest guest anchor i dlaczego wiele firm tuneluje ruch gości do kontrolera w DMZ.', 'Izolacja ruchu gości od sieci wewnętrznej.', 'Guest anchor to kontroler w DMZ, do którego kontrolery wewnętrzne (lub AP) tunelują ruch SSID GUEST. Goście dostają adres z podsieci w DMZ, a ich ruch wychodzi do Internetu przez osobny firewall — nigdy nie przechodzi „luzem” przez sieć wewnętrzną. Daje jedną politykę dla wszystkich lokalizacji, łatwiejszy portal i logowanie, mniejsze ryzyko pomyłki w VLAN-ach na przełącznikach.'],
  ['Na czym polega dynamiczne przypisanie VLAN-u przez RADIUS i dlaczego jest lepsze niż osobne SSID dla każdej grupy?', 'Atrybuty Tunnel-Private-Group-ID, role.', 'Po udanym 802.1X serwer RADIUS (ISE, ClearPass, FreeRADIUS) zwraca atrybuty: Tunnel-Type=VLAN, Tunnel-Medium-Type=802, Tunnel-Private-Group-ID=<VLAN> lub nazwę roli/ACL. Kontroler umieszcza klienta w odpowiednim VLAN-ie mimo jednego SSID. Zalety: mniej SSID (mniej beaconów), polityka oparta na tożsamości (użytkownik/grupa/urządzenie), zmiana uprawnień bez rekonfiguracji klientów, rozliczalność.'],
  ['Opisz, jak WIPS odróżnia rogue AP podłączony do sieci firmowej od zwykłego AP sąsiada i dlaczego ma to znaczenie.', 'Korelacja przewodowa (wired correlation).', 'WIPS widzi wiele obcych AP (sąsiedzi, hotspoty telefonów). Rogue AP to obcy AP podłączony do LAN firmy — wykrywany przez korelację: adresy MAC klientów/BSSID widziane w eterze pojawiają się w tablicach MAC przełączników firmowych (lub AP wysyła ramki przez sieć firmową), testy ruchu z AP do kontrolera. Znaczenie: AP sąsiada to tylko zakłócenie, rogue AP to dziura w segmentacji i kontroli dostępu — wymaga natychmiastowej reakcji (wyłączenie portu, lokalizacja).'],
  ['Podaj trzy powody, dla których aplikacja w oddziale działa wolno tylko przez Wi-Fi, i jak je rozróżnić.', 'Central switching, słaby sygnał, przeciążony kanał.', '1) Central switching — ruch klienta Wi-Fi idzie przez WAN do centrali (traceroute z klienta pokaże bramę w centrali, RTT do lokalnego serwera duże). 2) Słaby sygnał/niski MCS/retransmisje — iw/netsh pokaże dBm i retry, problem zależny od miejsca. 3) Przeciążony kanał lub za dużo SSID/beaconów — wszyscy klienci w danym obszarze mają problem, kontroler pokazuje wysokie channel utilization. Dodatkowo MTU (tunel CAPWAP + VPN) — problem przy dużych odpowiedziach.'],
  ['Wymień zabezpieczenia interfejsu zarządzania kontrolera WLC lub portalu chmurowego (minimum 6).', 'Dostęp, uwierzytelnianie, aktualizacje, logi.', '1) Zarządzanie tylko z VLAN-u/sieci administracyjnej (ACL), nigdy z SSID CORP/GUEST ani z Internetu. 2) AAA (TACACS+/RADIUS) z kontami imiennymi, MFA w portalu chmurowym, SSO. 3) RBAC — helpdesk tylko podgląd. 4) HTTPS/SSH, wyłączone HTTP/Telnet, silne szyfry. 5) Aktualizacje zgodnie z biuletynami (PSIRT, KEV). 6) Logowanie zmian konfiguracji i logowań do SIEM. 7) Kopie konfiguracji (szyfrowane). 8) Ochrona tokenów API (sejf, rotacja, minimalne uprawnienia).'],
  ['Wyjaśnij, dlaczego 802.11r może przeszkadzać starszym klientom i jak się to rozwiązuje.', 'Nowe AKM w beaconach.', 'Włączenie FT dodaje do RSN IE nowe metody zarządzania kluczami (FT-802.1X, FT-SAE). Niektóre starsze sterowniki/urządzenia IoT źle parsują takie beacony i nie potrafią się połączyć. Rozwiązania: tryb adaptacyjny/mixed (FT opcjonalne — klienci z obsługą FT jej używają, pozostali łączą się zwykle), osobny SSID dla starych urządzeń (np. IOT bez FT), aktualizacja sterowników.'],
  ['Opisz, jak kontroler wybiera kanały i moc (RRM) i jaki może być skutek uboczny dla aplikacji.', 'DCA, TPC, zmiany w ciągu dnia.', 'RRM zbiera pomiary z AP (sąsiednie AP, zakłócenia, obciążenie) i okresowo zmienia kanały (DCA) oraz moc (TPC), by zmniejszyć zakłócenia i wyrównać pokrycie; wykrywa też dziury (coverage hole). Skutek uboczny: zmiana kanału AP w godzinach pracy rozłącza klientów lub wymusza roaming — krótkie przerwy w aplikacjach. Dobre praktyki: zmiany kanałów w oknie nocnym, ograniczenie częstotliwości DCA, monitoring zdarzeń.'],
  ['Laboratorium: uruchom UniFi Network Application w Dockerze (lub wirtualny kontroler) i opisz, jak AP adoptuje się do kontrolera oraz jakie porty są potrzebne.', 'Inform URL, port 8080, STUN 3478.', 'UniFi: AP odnajduje kontroler przez L2 discovery (UDP 10001), DHCP opcja 43 lub DNS „unifi”, albo ręcznie set-inform http://<kontroler>:8080/inform; kontroler przyjmuje adopcję i wysyła konfigurację. Porty: TCP 8080 (inform), TCP 8443 (GUI), UDP 3478 (STUN), UDP 10001 (discovery). Odpowiednio do tego reguły firewalla między siecią AP a kontrolerem; GUI tylko z sieci zarządzania.'],
  ['Jak zabezpieczyć sieć gości na poziomie L2/L3 tak, by goście nie mogli atakować siebie nawzajem ani sieci firmy?', 'Izolacja klientów, ACL, DNS, limity.', 'Izolacja klientów na AP/kontrolerze (peer-to-peer blocking), osobny VLAN i podsieć, ACL blokujące RFC 1918 i adresy firmy (poza bramą/DNS), filtrowanie mDNS/SSDP, ARP proxy/ochrona przed ARP spoofing, DHCP snooping, limit przepustowości per klient, krótkie dzierżawy, portal z akceptacją regulaminu, wyjście do Internetu przez osobny firewall (guest anchor), logowanie dla celów prawnych.'],
  ['Jakie zdarzenia z kontrolera Wi-Fi warto przesyłać do SIEM i jakie reguły korelacji na nich zbudujesz?', 'Uwierzytelnianie, WIPS, zmiany konfiguracji.', 'Zdarzenia: nieudane i udane uwierzytelnienia 802.1X (użytkownik, MAC, AP), asocjacje i roaming, alerty WIPS (rogue AP, evil twin, deauth flood), zmiany konfiguracji i logowania administratorów. Reguły: wiele nieudanych 802.1X dla różnych kont z jednego MAC (password spraying), to samo konto z dwóch odległych lokalizacji jednocześnie, SSID firmowy nadawany przez nieznany BSSID, zmiana konfiguracji WLAN poza oknem serwisowym, nowe konto administratora.'],
  ['Wyjaśnij, czym jest Wi-Fi mesh (tryb bridge/mesh) i jakie ma ograniczenia wydajnościowe.', 'Backhaul bezprzewodowy.', 'W mesh część AP (MAP) nie ma kabla — łączy się bezprzewodowo z AP podłączonym do sieci (RAP), tworząc backhaul radiowy. Ograniczenia: każdy skok bezprzewodowy dzieli czas antenowy między ruch klientów i backhaul (przy jednym radiu przepustowość spada ~o połowę na skok), rośnie opóźnienie i jitter. Dobre do miejsc bez okablowania (hale, tereny zewnętrzne), najlepiej z osobnym radiem/pasmem na backhaul (np. 5 lub 6 GHz) i małą liczbą skoków.'],
];
for (const [q, h, a] of more) ex(q, h, a);
ex('Przygotuj listę kontrolną odbioru wdrożenia sieci Wi-Fi z kontrolerem (minimum 7 punktów).', 'Projekt, bezpieczeństwo, testy, monitoring.', '1) Site survey po wdrożeniu: sygnał ≥ −67 dBm i SNR ≥ 25 dB w obszarach VoIP/skanerów. 2) Plan kanałów i mocy (RRM) bez nakładających się kanałów 2,4 GHz. 3) Maks. 3–4 SSID, minimalna prędkość podstawowa podniesiona. 4) Zabezpieczenia: WPA3/Enterprise z EAP-TLS, PMF, OWE/portal dla gości, izolacja. 5) Mapowanie SSID → VLAN i ACL zgodne z projektem; trunki do AP z właściwymi VLAN-ami. 6) Testy roamingu (ciągły ping/VoIP podczas przejścia przez budynek). 7) Budżet PoE z zapasem. 8) Zarządzanie kontrolerem tylko z sieci administracyjnej, AAA, aktualne oprogramowanie, kopie konfiguracji. 9) WIPS i logi w SIEM.');
const more2 = [
  ['Wyjaśnij, jak dobrać wielkość podsieci dla SSID GUEST w hali targowej na 2000 odwiedzających, z dzierżawą 1 h i rotacją gości co 2 h.', 'Jednoczesne urządzenia × zapas; urządzenia na osobę.', 'Zakładając ~1,5 urządzenia na osobę i do 2000 osób jednocześnie: ok. 3000 dzierżaw; przy dzierżawie 1 h adresy zwalniają się szybko, ale potrzebny zapas ~20–30% → ok. 4000 adresów → podsieć /20 (4094 hosty). Mniejsza podsieć (/22) wyczerpie się w godzinach szczytu. Dodatkowo: wiele AP o niskiej mocy (pojemność), izolacja klientów, limit przepustowości per klient.'],
  ['Policz, ile mocy PoE potrzeba dla 36 AP Wi-Fi 7 (po 38 W) i jaki standard PoE jest wymagany. Jak rozłożysz je na przełączniki z budżetem 740 W?', 'Moc > 30 W → 802.3bt.', 'Potrzeba 36 × 38 = 1368 W. AP > 30 W wymagają 802.3bt (typ 3, 60 W na porcie). Przełącznik 740 W zasili maks. 19 takich AP (19 × 38 = 722 W) — potrzebne co najmniej 2 przełączniki (np. 18 + 18 AP = 684 W każdy, z zapasem ~56 W) lub zasilacze o większym budżecie. Bez tego AP uruchomią się w trybie ograniczonym (mniej strumieni/radio wyłączone).'],
  ['Opisz, jak przetestować roaming w nowo wdrożonej sieci, zanim użytkownicy aplikacji magazynowej zaczną pracę.', 'Ciągły ruch + przejście przez obszar; pomiar przerwy.', 'Na urządzeniu docelowym (lub laptopie) uruchom ciągły ruch: ping -i 0.2 do serwera aplikacji i/lub połączenie WebSocket/VoIP. Przejdź trasę pracownika przez halę, rejestrując zmiany BSSID (iw dev wlan0 link w pętli / logi kontrolera). Policz utracone pakiety w momentach przejścia (×0,2 s = przerwa): cel < 100 ms przy 802.11r, sekundy bez FT. Sprawdź, czy adres IP się nie zmienia (jeden VLAN w obszarze). Zapisz miejsca, gdzie klient trzyma się dalekiego AP (sticky) — korekta mocy/802.11k/v.'],
  ['Kontroler wymusza 802.1X z PEAP, a część laptopów łączy się bez pytania o certyfikat serwera RADIUS. Jakie ryzyko i jak to naprawić centralnie?', 'Profil Wi-Fi z GPO/MDM.', 'Ryzyko: evil twin z tym samym SSID i własnym RADIUS przechwyci poświadczenia MSCHAPv2 (lekcja 11). Naprawa: rozesłać profil Wi-Fi przez GPO (Windows) lub MDM (Intune, Jamf) z zaufanym CA, wymaganą nazwą serwera RADIUS i zakazem pytania użytkownika o nowe certyfikaty; docelowo EAP-TLS z certyfikatami urządzeń (bez haseł). Weryfikacja: test z podstawionym AP w labie.'],
  ['Wyjaśnij, jakie informacje o lokalizacji użytkowników zbiera kontroler/chmura Wi-Fi i jakie obowiązki wynikają z RODO.', 'MAC, AP, czas, RSSI → lokalizacja.', 'Kontroler/chmura zapisuje MAC urządzeń, nazwy użytkowników (802.1X), AP i czas asocjacji, siłę sygnału — można z tego odtworzyć lokalizację i przemieszczanie się osób (dane osobowe). Obowiązki: podstawa prawna i informacja dla pracowników/gości, minimalizacja i retencja (np. 30–90 dni), umowa powierzenia z dostawcą chmury, kontrola dostępu do analityki lokalizacji, pseudonimizacja tam, gdzie możliwa.'],
];
for (const [q, h, a] of more2) ex(q, h, a);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026112212);
const mAll = MODES.map(([m]) => m);
for (const [m, d] of MODES) add(`Który tryb AP opisuje: ${d}?`, m, mAll.filter((x) => x !== m).slice(0, 3), `Tryb ${m}.`);
for (const [s, a, rate] of [[1, 1, 1], [3, 1, 1], [3, 3, 1], [5, 3, 1], [3, 3, 24], [6, 2, 6], [4, 4, 12]]) {
  const p = beaconAirtimePct(s, a, 350, rate);
  add(`Model z lekcji (beacon 350 B, 9,77/s, +50 µs): jaki narzut czasu antenowego dają ${s} SSID × ${a} AP przy prędkości podstawowej ${rate} Mb/s?`, `${p}%`, [`${round(p * 2)}%`, `${round(p / 3)}%`, `${round(p + 10)}%`], `${s} × ${a} × 9,77 × (2800/${rate} + 50) µs = ${p}%.`);
}
const poeAll = Object.entries(POE).map(([k, v]) => `${k} — ${v} W`);
for (const [k, v] of Object.entries(POE)) add(`Jaka jest maksymalna moc na porcie przełącznika w standardzie ${k}?`, `${v} W`, Object.values(POE).filter((x) => x !== v).map((x) => `${x} W`), `${k}: ${v} W na porcie PSE.`);
for (const [n, w, b] of [[24, 25, 740], [24, 25, 370], [48, 15.4, 740], [12, 60, 740], [20, 30, 600]]) add(`Czy budżet PoE ${b} W wystarczy dla ${n} AP po ${w} W?`, n * w <= b ? `Tak — potrzeba ${round(n * w)} W` : `Nie — potrzeba ${round(n * w)} W`, [n * w <= b ? `Nie — potrzeba ${round(n * w)} W` : `Tak — potrzeba ${round(n * w)} W`, `Tak — potrzeba ${round(n * w / 2)} W`, `Nie — potrzeba ${round(n * w * 2)} W`], `${n} × ${w} = ${round(n * w)} W vs ${b} W.`);
for (const [area, cell, cl, per] of [[3000, 250, 400, 30], [1000, 200, 60, 30], [6000, 300, 300, 40], [800, 200, 250, 25]]) {
  const r = Math.max(apsForArea(area, cell), apsForClients(cl, per));
  add(`Ile AP zaplanujesz (max z pokrycia i pojemności): ${area} m², komórka ${cell} m², ${cl} urządzeń, ${per} klientów na AP?`, String(r), [String(Math.min(apsForArea(area, cell), apsForClients(cl, per))) === String(r) ? String(r + 2) : String(Math.min(apsForArea(area, cell), apsForClients(cl, per))), String(r * 2), String(Math.max(1, r - 3)), String(r + 1)], `Pokrycie ${apsForArea(area, cell)}, pojemność ${apsForClients(cl, per)} → ${r}.`);
}
const facts = [
  ['Jakich portów używa CAPWAP?', 'UDP 5246 (sterowanie) i UDP 5247 (dane)', ['TCP 443 i 80', 'UDP 1812 i 1813', 'TCP 22 i 23'], '1812/1813 to RADIUS.'],
  ['Czym jest szyfrowany kanał sterowania CAPWAP?', 'DTLS', ['WEP', 'IPsec ESP zawsze', 'Nie jest szyfrowany'], 'Dane opcjonalnie też przez DTLS.'],
  ['Czym różni się lightweight AP od autonomicznego?', 'Lightweight AP pobiera konfigurację i polityki z kontrolera', ['Lightweight AP nie ma radia', 'Autonomiczny AP wymaga chmury', 'Nie ma różnicy'], 'Split MAC.'],
  ['Które funkcje w architekturze split MAC zostają w AP?', 'Funkcje czasowo krytyczne: beacony, ACK, szyfrowanie ramek', ['Uwierzytelnianie 802.1X', 'Polityki i RRM', 'Zarządzanie licencjami'], 'Reszta w kontrolerze.'],
  ['Jak AP może odnaleźć kontroler WLC?', 'Przez DHCP opcja 43, DNS, broadcast w podsieci lub zapisany adres', ['Tylko ręcznie', 'Przez LLDP-MED', 'Przez STP'], 'Np. CISCO-CAPWAP-CONTROLLER.<domena>.'],
  ['Na czym polega central switching?', 'Ruch klientów jest tunelowany do kontrolera i tam wypuszczany do VLAN-u', ['Ruch wychodzi lokalnie przy AP', 'Ruch idzie przez chmurę', 'Klienci łączą się bez AP'], 'Tryb local.'],
  ['Jaka jest główna zaleta FlexConnect w oddziałach?', 'Dane klientów wychodzą lokalnie, a AP działa dalej przy utracie WAN', ['Szyfrowanie bez kontrolera', 'Brak potrzeby VLAN-ów', 'Większa moc radia'], 'Local switching.'],
  ['Do czego służy AP w trybie monitor?', 'Do skanowania kanałów, WIPS i lokalizacji — bez obsługi klientów', ['Do obsługi gości', 'Do zasilania innych AP', 'Do routingu'], 'Zwiększa skuteczność wykrywania.'],
  ['Co jest typowe dla zarządzania Wi-Fi z chmury?', 'Zarządzanie w chmurze, ruch danych klientów lokalnie', ['Cały ruch klientów przez chmurę', 'Brak szyfrowania', 'Brak możliwości VLAN-ów'], 'Meraki, Mist, Aruba Central.'],
  ['Dlaczego ogranicza się liczbę SSID do 3–4?', 'Każdy SSID dodaje beacony z każdego AP, zjadając czas antenowy', ['Bo standard pozwala tylko na 4', 'Bo VLAN-ów jest mało', 'Bo kontroler ma limit 4'], 'Rozróżniaj rolami 802.1X.'],
  ['Co daje podniesienie minimalnej prędkości podstawowej (np. do 12 lub 24 Mb/s)?', 'Krótsze beacony i ramki zarządzające, mniej wolnych klientów, mniejsze komórki', ['Większy zasięg', 'Silniejsze szyfrowanie', 'Więcej SSID'], 'Lepsza wydajność w gęstych sieciach.'],
  ['Jak przypisać różne VLAN-y użytkownikom tego samego SSID?', 'Dynamicznie przez atrybuty RADIUS po 802.1X', ['Nie da się', 'Przez ukrycie SSID', 'Przez filtrowanie MAC'], 'Tunnel-Private-Group-ID.'],
  ['Co robi 802.11k?', 'Przekazuje klientowi listę sąsiednich AP', ['Szyfruje ramki zarządzające', 'Przyspiesza wymianę kluczy', 'Ogranicza moc'], 'Szybszy wybór celu roamingu.'],
  ['Co robi 802.11v (BSS Transition)?', 'Pozwala sieci zasugerować klientowi przejście do lepszego AP', ['Szyfruje beacony', 'Zastępuje 802.1X', 'Zmienia kanał AP'], 'Pomaga ze sticky clients.'],
  ['Co robi 802.11r?', 'Skraca przejście między AP, unikając pełnego uwierzytelnienia 802.1X', ['Wyłącza szyfrowanie przy roamingu', 'Zmienia VLAN klienta', 'Wymusza 2,4 GHz'], 'Fast BSS Transition.'],
  ['Czym jest roaming L3?', 'Przejściem do AP mapującego SSID na inną podsieć', ['Przejściem między kanałami', 'Zmianą SSID', 'Roamingiem bez szyfrowania'], 'Kontrolery tunelują ruch do anchora.'],
  ['Czym jest guest anchor?', 'Kontrolerem w DMZ, do którego tunelowany jest ruch sieci gości', ['AP dla gości', 'Portalem logowania', 'Listą dozwolonych MAC'], 'Ruch gości nie przechodzi przez sieć wewnętrzną.'],
  ['Co robi izolacja klientów (peer-to-peer blocking)?', 'Blokuje bezpośrednią komunikację między klientami w tym samym SSID/VLAN', ['Blokuje Internet', 'Szyfruje DNS', 'Ukrywa SSID'], 'Kluczowe dla gości.'],
  ['Jaki jest typowy objaw braku VLAN-u SSID na trunku do AP przy local switching?', 'Klient łączy się z SSID, ale nie dostaje adresu IP', ['AP się nie uruchamia', 'Zmienia się SSID', 'Klient dostaje adres z innej sieci'], 'Sprawdź allowed VLANs.'],
  ['Dlaczego przejęcie konta w portalu chmurowym Wi-Fi jest groźne?', 'Daje kontrolę nad siecią bezprzewodową wszystkich lokalizacji', ['Bo usuwa licencje', 'Bo wyłącza prąd AP', 'Nie jest groźne'], 'MFA, SSO, RBAC.'],
  ['Gdzie powinien być dostępny interfejs zarządzania WLC?', 'Tylko z sieci zarządzania (VLAN administracyjny)', ['Z SSID gości', 'Z Internetu przez HTTP', 'Z każdego VLAN-u'], 'Plus AAA i MFA.'],
  ['Jak WIPS rozpoznaje rogue AP podłączony do LAN firmy?', 'Korelując adresy widziane w eterze z tablicami MAC przełączników firmy', ['Po nazwie SSID', 'Po kolorze obudowy', 'Po kanale'], 'Wired correlation.'],
  ['Czym jest evil twin z perspektywy WIPS?', 'Obcym BSSID nadającym firmowy SSID', ['AP z dwoma radiami', 'Zapasowym kontrolerem', 'Klientem z dwoma kartami'], 'Alert wysokiego priorytetu.'],
  ['Co robi RRM?', 'Automatycznie dobiera kanały i moc AP oraz wykrywa dziury w pokryciu', ['Szyfruje ruch', 'Przydziela adresy IP', 'Tworzy VLAN-y'], 'DCA i TPC.'],
  ['Jaki skutek uboczny może mieć zmiana kanału przez RRM w godzinach pracy?', 'Chwilowe rozłączenia lub roaming klientów', ['Wyłączenie VLAN-ów', 'Utrata konfiguracji', 'Zmiana haseł'], 'Planuj zmiany nocą.'],
  ['Czym jest site survey?', 'Pomiarem lub predykcją pokrycia i pojemności sieci Wi-Fi', ['Audytem haseł', 'Skanem portów', 'Testem PoE'], 'Przed i po wdrożeniu.'],
  ['Co jest ważniejsze w sali konferencyjnej na 300 osób: zasięg czy pojemność?', 'Pojemność — liczba AP i kanałów dla wielu klientów', ['Zasięg — jeden mocny AP', 'Moc nadawania', 'Liczba SSID'], 'Wiele AP o mniejszej mocy.'],
  ['Jak działa Wi-Fi mesh?', 'Część AP łączy się bezprzewodowo z AP podłączonym kablem (backhaul radiowy)', ['Wszystkie AP muszą mieć kabel', 'Klienci przekazują ruch między sobą', 'Mesh używa tylko 2,4 GHz'], 'Każdy skok obniża przepustowość.'],
  ['Dlaczego tunel CAPWAP może powodować problemy z MTU?', 'Dodaje nagłówki do ramek klienta, co przy MTU 1500 na WAN wymaga fragmentacji', ['Bo zmniejsza MTU klienta do 576', 'Bo wyłącza PMTUD', 'Nie powoduje'], 'Lekcja 01 o MTU.'],
  ['Który port RADIUS używa uwierzytelnianie?', 'UDP 1812', ['UDP 1813', 'UDP 5246', 'TCP 49'], '1813 accounting, TCP 49 TACACS+.'],
  ['Co oznacza iPSK/MPSK?', 'Osobny klucz PSK dla każdego urządzenia lub grupy w jednym SSID', ['Brak hasła', 'Szyfrowanie WEP', 'Uwierzytelnianie certyfikatem'], 'Odwoływalne klucze dla IoT.'],
  ['Dlaczego starsze urządzenia IoT mogą nie łączyć się z SSID z 802.11r?', 'Źle obsługują nowe metody zarządzania kluczami ogłaszane w beaconach', ['Bo 802.11r wymaga 6 GHz', 'Bo 802.11r wyłącza DHCP', 'Bo 802.11r wymaga IPv6'], 'Tryb adaptacyjny lub osobny SSID.'],
  ['Który element konfiguracji Catalyst 9800 przypisuje SSID do VLAN-u?', 'Policy profile powiązany z WLAN w policy tag', ['RF profile', 'AP join profile', 'Site tag'], 'WLAN + policy profile + policy tag.'],
  ['Jaki tryb zabezpieczeń kontroler może zastosować dla otwartej sieci gości, by szyfrować ruch?', 'OWE (Enhanced Open)', ['WEP', 'TKIP', 'Brak — otwarte sieci nie mogą być szyfrowane'], 'Bez hasła.'],
  ['Co dzieje się z klientami FlexConnect przy utracie łączności z kontrolerem?', 'AP przechodzi w tryb standalone i dalej obsługuje klientów lokalnie', ['Wszyscy klienci są rozłączani natychmiast', 'AP się restartuje w pętli', 'Ruch idzie przez chmurę'], 'Z ograniczeniami (np. nowe uwierzytelnienia zależne od RADIUS).'],
  ['Dlaczego aplikacja w oddziale może działać wolno tylko na Wi-Fi przy central switching?', 'Ruch Wi-Fi jedzie przez WAN do kontrolera w centrali i z powrotem', ['Bo Wi-Fi nie obsługuje TCP', 'Bo central switching wyłącza QoS', 'Bo kontroler szyfruje dwukrotnie'], 'Rozważ FlexConnect.'],
  ['Kto powinien decydować o roli i VLAN-ie użytkownika firmowego Wi-Fi?', 'Serwer NAC/RADIUS na podstawie tożsamości (802.1X)', ['Użytkownik wybierając SSID', 'AP losowo', 'Serwer DHCP'], 'Polityka oparta na tożsamości.'],
  ['Jakie zdarzenie Wi-Fi w SIEM może wskazywać na password spraying?', 'Wiele nieudanych 802.1X dla różnych kont z jednego urządzenia', ['Jeden udany roaming', 'Zmiana kanału przez RRM', 'Wysoki sygnał klienta'], 'Korelacja w SIEM.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
const poeStd = (w) => (w <= 15.4 ? '802.3af (PoE)' : w <= 30 ? '802.3at (PoE+)' : w <= 60 ? '802.3bt typ 3 (PoE++)' : '802.3bt typ 4');
for (const w of [6, 12.95, 14, 18, 25.5, 29, 38, 51, 71, 85]) { const c = poeStd(w); add(`Jaki minimalny standard PoE zasili urządzenie pobierające ${w} W (na porcie przełącznika)?`, c, Object.keys(POE).filter((k) => k !== c), `${w} W ≤ ${POE[c]} W → ${c}.`); }
const pref = (n) => { for (let p = 30; p >= 8; p--) if (2 ** (32 - p) - 2 >= n) return p; return 8; };
for (const n of [50, 200, 300, 900, 1500, 3000, 5000]) { const p = pref(n); add(`Jaka najmniejsza podsieć IPv4 pomieści ${n} klientów SSID (bez zapasu)?`, `/${p}`, [`/${p + 1}`, `/${p - 1}`, `/${p + 2}`], `/${p} daje ${2 ** (32 - p) - 2} hostów, /${p + 1} tylko ${2 ** (32 - p - 1) - 2}.`); }
const facts2 = [
  ['Jak często AP wysyła beacon dla każdego SSID (domyślnie)?', 'Co 102,4 ms (100 TU)', ['Co 1 s', 'Co 10 ms', 'Co 2 s'], 'Ok. 9,77 beaconu na sekundę.'],
  ['Jaki protokół był poprzednikiem CAPWAP?', 'LWAPP', ['LLDP', 'LACP', 'L2TP'], 'Cisco Lightweight Access Point Protocol.'],
  ['Który RFC opisuje CAPWAP?', 'RFC 5415', ['RFC 2131', 'RFC 8415', 'RFC 1918'], '2131 DHCP, 8415 DHCPv6, 1918 adresy prywatne.'],
  ['Jakiego atrybutu RADIUS używa się do wskazania VLAN-u klienta?', 'Tunnel-Private-Group-ID', ['Framed-IP-Address', 'Calling-Station-Id', 'NAS-Identifier'], 'Wraz z Tunnel-Type i Tunnel-Medium-Type.'],
  ['Jaki kontroler Wi-Fi Cisco zastąpił serie AireOS?', 'Catalyst 9800 (IOS XE)', ['ASA 5500', 'Nexus 9000', 'ISR 4000'], 'Dostępny też jako 9800-CL w chmurze/VM.'],
  ['Co oznacza „orphan” AP lub klient w kontekście kontrolera?', 'Element, który stracił łączność z kontrolerem lub anchorem', ['AP bez anteny', 'Klient bez IP', 'Ukryty SSID'], 'Kontekst zależny od producenta.'],
  ['Dlaczego zarządzanie AP z chmury zwykle wymaga tylko ruchu wychodzącego?', 'AP sam łączy się z chmurą (np. TCP 443), więc nie trzeba otwierać ruchu przychodzącego', ['Bo chmura nie używa IP', 'Bo AP ma publiczny adres', 'Bo ruch idzie przez CAPWAP do chmury zawsze'], 'Prostsze reguły firewalla.'],
  ['Co jest ryzykiem prywatności przy analityce lokalizacji Wi-Fi?', 'Możliwość odtworzenia przemieszczania się osób z danych MAC/AP/czasu', ['Utrata zasięgu', 'Spadek przepustowości', 'Brak DHCP'], 'Dane osobowe w rozumieniu RODO.'],
  ['Który element sieci Wi-Fi w firmie jest celem najwyższego ryzyka przy przejęciu?', 'Kontroler WLC lub konto administratora portalu chmurowego', ['Pojedynczy klient gościa', 'Antena zewnętrzna', 'Beacon'], 'Kontrola nad całą siecią.'],
  ['Jaki test potwierdzi, że roaming działa przy 802.11r?', 'Ciągły ruch podczas przejścia między AP z pomiarem przerwy (cel < 100 ms)', ['Pojedynczy ping po połączeniu', 'Test prędkości przy AP', 'Sprawdzenie SSID'], 'Plus brak zmiany adresu IP.'],
  ['Dlaczego lepiej mieć więcej AP o niższej mocy niż mało AP o maksymalnej mocy w gęstym biurze?', 'Mniejsze komórki dają większą pojemność i lepszy roaming', ['Bo zużywają mniej licencji', 'Bo wtedy nie trzeba szyfrowania', 'Bo zmniejsza liczbę VLAN-ów'], 'Mniej zakłóceń współkanałowych.'],
  ['Co sprawdzić, gdy AP uruchomił się z wyłączonym jednym radiem?', 'Czy przełącznik dostarcza wystarczającą moc PoE', ['Czy SSID jest ukryty', 'Czy DNS działa', 'Czy VLAN 1 istnieje'], 'Tryb ograniczony przy niedoborze mocy.'],
  ['Jaka metoda uwierzytelnienia jest najlepsza dla laptopów firmowych w SSID CORP?', 'EAP-TLS z certyfikatami urządzeń', ['WPA2-PSK wspólne dla firmy', 'Sieć otwarta z portalem', 'WEP'], 'Brak haseł do przechwycenia.'],
  ['Co daje oddzielenie ruchu gości tunelem do DMZ zamiast lokalnego VLAN-u?', 'Ruch gości nigdy nie przechodzi przez sieć wewnętrzną i ma jedną politykę wyjścia', ['Większą prędkość', 'Brak potrzeby portalu', 'Szyfrowanie end-to-end'], 'Guest anchor.'],
  ['Jakie porty wymaga UniFi do adopcji AP?', 'TCP 8080 (inform), UDP 3478 (STUN), UDP 10001 (discovery)', ['UDP 5246/5247', 'TCP 22 i 23', 'UDP 67/68'], 'GUI na 8443.'],
  ['Co oznacza „channel utilization” raportowane przez kontroler?', 'Odsetek czasu, w którym kanał jest zajęty transmisjami', ['Liczbę SSID', 'Moc nadawania', 'Liczbę VLAN-ów'], 'Wysoka wartość = przeciążenie.'],
  ['Dlaczego mobility tunnel między kontrolerami pozwala zachować adres IP przy roamingu L3?', 'Bo ruch klienta wraca do kontrolera anchor w pierwotnej podsieci', ['Bo klient dostaje dwa adresy', 'Bo DHCP przedłuża dzierżawę', 'Bo zmienia się MAC'], 'Anchor/foreign.'],
  ['Kiedy sensowne jest local switching w kampusie z kontrolerem?', 'Gdy chcesz uniknąć przeciążenia kontrolera i wypuszczać ruch blisko AP', ['Nigdy', 'Tylko dla gości', 'Tylko w 2,4 GHz'], 'Kosztem rozproszenia VLAN-ów na przełącznikach.'],
  ['Co jest typowym objawem niedziałającej opcji 43 lub DNS dla kontrolera?', 'AP nie dołącza do kontrolera (join failed / discovering)', ['Klienci nie widzą SSID mimo dołączonego AP', 'Brak PoE', 'Błędy CRC'], 'Sprawdź wykrywanie i UDP 5246.'],
  ['Dlaczego kontroler Wi-Fi powinien działać w parze HA?', 'Awaria pojedynczego kontrolera wyłączyłaby całą sieć bezprzewodową', ['Bo licencja tego wymaga', 'Bo AP mają dwa porty', 'Bo CAPWAP działa tylko w parach'], 'SSO / N+1.'],
];
for (const [q, a, w, e] of facts2) add(q, a, w, e);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
