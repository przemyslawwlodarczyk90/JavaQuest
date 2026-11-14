// Generuje lekcje _ns_02_ethernet_lan/13_DebugLanProblems.json (Debug Lab: siec lokalna nie dziala).
// Uzycie: node scripts/content-migration/netsec/lessons/ns02_13_debug_lesson.cjs src/main/resources/content/_ns_02_ethernet_lan/13_DebugLanProblems.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { netStr, bcast, decide, maxPayload, intIp, netOf } = require('./ns02_13_debug_model.cjs');
const buildQuiz = require('./ns02_13_debug_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// Przypadki „dokad wysle host” liczone modelem (adres/prefiks hosta, brama, cel).
const CASES = [
  ['10.13.0.13', 28, '10.13.0.1', '10.13.0.14'],
  ['10.13.0.13', 28, '10.13.0.1', '10.13.0.40'],
  ['10.13.0.14', 24, null, '10.99.0.10'],
  ['10.13.0.14', 24, null, '10.13.0.200'],
  ['192.168.1.50', 24, '192.168.2.1', '8.8.8.8'],
  ['172.16.5.20', 16, '172.16.0.1', '172.16.200.9'],
];

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Debug Lab: sieć lokalna nie działa — i masz to udowodnić, nie zgadywać', ...img('ns02-13-lab.svg', 'Topologia Debug Labu: bridge br-lab z podsiecią 10.13.0.0/24 i routerem 10.13.0.1 prowadzącym do serwera 10.99.0.10; zepsute namespace’y: ns-a i ns-b z tym samym adresem 10.13.0.11, ns-c z maską /28, ns-d bez trasy domyślnej, ns-e z MTU 9000 na łączu 1500. Skrypty setup.sh i check.sh.'),
    body:
`To lekcja zamykająca rozdział o Ethernecie i sieci lokalnej. Zamiast nowej teorii dostajesz **zepsuty lab** i jedno zadanie: znaleźć i naprawić każdy błąd, a na koniec skrypt \`check.sh\` ma pokazać same **PASS**.

Lab buduje się na jednym Linuksie (WSL2, maszyna wirtualna, VPS) z **network namespace’ów** — każdy namespace to osobny „komputer” z własnymi interfejsami, adresami i tablicą routingu. Połączone są parami **veth** do mostu **br-lab** (wirtualny przełącznik), a namespace **ns-router** prowadzi do „serwera” 10.99.0.10 w innej podsieci. Ukryte błędy to klasyka zgłoszeń „nie działa mi sieć”:
1. **Duplikat adresu IP** — ns-a i ns-b mają 10.13.0.11; połączenia „raz działają, raz nie”.
2. **Zła maska** — ns-c ma /28 zamiast /24; „do bramy działa, do drukarki nie”.
3. **Brak trasy domyślnej** — ns-d widzi sąsiadów, ale nie widzi niczego poza podsiecią.
4. **Niezgodne MTU** — ns-e ma MTU 9000 na łączu 1500; „ping działa, a HTTPS i scp wiszą”.

Do tego w metodyce omawiamy piąty klasyk z planu — **zły VLAN** na porcie przełącznika (w labie odpowiednikiem jest interfejs podpięty do złego mostu).

Najważniejsze, czego uczy ta lekcja, to **metodyka**: od kabla w górę (łącze → adres → sąsiad/ARP → brama i trasa → ścieżka i MTU → DNS i usługa), jedno polecenie na krok i zapis **objaw → hipoteza → dowód → poprawka → test**. Dokładnie tak wygląda dobra notatka z incydentu w zespole SRE i dokładnie tego wymaga egzamin: Network+ N10-009 domena 5 (Network Troubleshooting — metodyka CompTIA i typowe problemy: duplikat IP, zła maska, zła brama, niezgodne MTU, zły VLAN) oraz CCNA 200-301 v2 (weryfikacja adresacji IPv4, łączności L2/L3 i interfejsów).

Dla Java developera to codzienność: „aplikacja nie łączy się z bazą” w 80% przypadków jest problemem sieci lub konfiguracji hosta, a nie kodu. Kto umie w pięć minut odróżnić złą maskę od blokady firewalla, ten oszczędza zespołowi godziny.` },

  { type: 'DEFINITION', heading: 'Słownik Debug Labu', body:
`- **Network namespace (netns)** — izolowana kopia stosu sieciowego Linuksa: interfejsy, adresy, trasy, reguły firewalla, tablica ARP. \`ip netns add\`, \`ip netns exec <ns> <polecenie>\`. Na tym samym mechanizmie stoją kontenery Dockera i pody Kubernetesa.
- **veth** — para połączonych wirtualnych interfejsów („kabel z dwoma wtyczkami”); co wejdzie jednym końcem, wychodzi drugim.
- **bridge (most) Linuksa** — programowy przełącznik L2 z tablicą MAC (\`bridge fdb\`); odpowiednik switcha z lekcji 02.
- **Duplikat IP (IP conflict)** — dwa hosty z tym samym adresem w jednej domenie rozgłoszeniowej; tablice ARP innych hostów „skaczą” między dwoma MAC-ami.
- **DAD (Duplicate Address Detection)** — sprawdzenie przed użyciem adresu: w IPv4 ARP probe (RFC 5227, \`arping -D\`), w IPv6 Neighbor Solicitation (adres w stanie *tentative*, potem *dadfailed*).
- **Zła maska (prefiks)** — host inaczej niż reszta sieci ocenia, które adresy są „lokalne” (decyzja: ARP do celu czy do bramy).
- **Trasa domyślna (default route, 0.0.0.0/0)** — dokąd wysłać pakiet do sieci, której nie ma w tablicy routingu; brak = „Network is unreachable”.
- **MTU** — maksymalny rozmiar pakietu IP na łączu; w jednej domenie L2 wszystkie interfejsy muszą mieć to samo MTU.
- **PMTUD** — wykrywanie MTU ścieżki przez ICMP „Fragmentation needed” / „Packet too big”; nie działa w obrębie jednej domeny L2 (nie ma routera, który odeśle ICMP).
- **ICMP Redirect** — router informuje hosta: „do tego celu wysyłaj bezpośrednio / przez innego sąsiada” — typowy ślad złej maski.
- **Zły VLAN (VLAN mismatch)** — port dostępowy w innym VLAN-ie niż podsieć hosta; łącze UP, adres poprawny, ale ARP nie dostaje odpowiedzi.
- **Metodyka CompTIA (7 kroków)** — zidentyfikuj problem → ustal teorię prawdopodobnej przyczyny → przetestuj teorię → ustal plan działania → wdroż rozwiązanie → zweryfikuj działanie systemu → udokumentuj.` },

  { type: 'ANALOGY', heading: 'Lekarz na izbie przyjęć, nie wróżka', body:
`Pacjent mówi „źle się czuję” — tak jak użytkownik mówi „nie działa internet”. Zły lekarz od razu przepisuje antybiotyk (restart routera, „wyłącz firewall”). Dobry lekarz robi **triaż w stałej kolejności**: oddycha? (łącze UP?) → tętno? (adres i maska?) → reaguje na głos? (ARP do sąsiada?) → widzi i słyszy? (brama i trasa?) → dopiero potem badania specjalistyczne (MTU, DNS, aplikacja).

Każdy objaw ma **różne możliwe przyczyny**, a badanie ma rozstrzygać między nimi:
- „Raz działa, raz nie” — jak gorączka, która przychodzi i odchodzi: częsty winowajca to **duplikat IP** (dwa hosty walczą o ten sam adres — tablica ARP sąsiadów zmienia zdanie).
- „Do jednych działa, do innych nie” — pacjent widzi tylko jednym okiem: **zła maska** (host za lokalne uważa mniej lub więcej adresów niż reszta sieci).
- „Sąsiedzi tak, świat nie” — pacjent słyszy rodzinę, ale nie telefon: **brak bramy/trasy**.
- „Krótkie zdania tak, długie nie” — pacjent przełyka małe kęsy, duże stają w gardle: **MTU**.

I najważniejsze: **lekarz zapisuje w karcie** wynik każdego badania. Ty też — w notatce z labu i w zgłoszeniu w zespole.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Metodyka w sześciu krokach i decyzja „ARP do celu czy do bramy”', ...img('ns02-13-method.svg', 'Sześć kroków diagnozy sieci lokalnej z poleceniami: łącze (ip -br link, ethtool), adres (ip -br addr), sąsiad (ip neigh, arping), brama i trasa (ip route get), ścieżka i MTU (ping -M do -s, tracepath), nazwy i usługa (resolvectl, ss, curl -v). Zatrzymaj się na pierwszym kroku, który zawodzi.'),
    body:
`Serce diagnozy L3 to jedno pytanie, które host zadaje przy każdym pakiecie: **czy cel jest w mojej podsieci?** Host bierze **swój** adres i **swoją** maskę, wylicza adres sieci dla siebie i dla celu. Równe → ARP o adres celu i wysyłka bezpośrednio. Różne → pakiet do bramy (ARP o adres bramy). Brak bramy → błąd od razu, bez wysyłania czegokolwiek. Poniżej decyzje policzone modelem z lekcji — zwróć uwagę, że liczy się **maska nadawcy**, nie odbiorcy:`,
    code:
`${CASES.map(([a, p, gw, b]) => `host ${a}/${p}, brama ${gw ?? '—'}, cel ${b}\n  sieć hosta ${netStr(a, p)}, sieć celu wg maski hosta ${netStr(b, p)} → ${decide(a, p, gw, b)}`).join('\n')}

Ten sam wynik daje Linux — zawsze sprawdzaj decyzję jądra, zamiast liczyć w głowie:
  ip route get 10.13.0.40            # „10.13.0.40 via 10.13.0.1 dev eth0 src 10.13.0.13” → pójdzie przez bramę
  ip route get 10.13.0.14            # „10.13.0.14 dev eth0 src 10.13.0.13”                → bezpośrednio` },

  { type: 'CODE_BASIC', heading: 'setup.sh — zbuduj zepsuty lab na jednym Linuksie', body:
`Skrypt tworzy most, router, serwer i pięć „hostów” z błędami. Wymaga roota (lub \`sudo\`) i pakietu iproute2; działa na Ubuntu/Debianie, w WSL2 i w maszynie wirtualnej. **Uruchamiaj tylko w izolowanym środowisku labowym** — skrypt dodaje interfejsy do systemu. \`reset.sh\` (na końcu) sprząta wszystko.`,
    code:
`#!/usr/bin/env bash
set -euo pipefail
ip link add br-lab type bridge && ip link set br-lab up

host() {   # host <ns> <adres/prefiks> [brama] [mtu]
  local ns=$1 addr=$2 gw=\${3:-} mtu=\${4:-1500}
  ip netns add "$ns"
  ip link add "v-$ns" type veth peer name eth0 netns "$ns"
  ip link set "v-$ns" master br-lab up
  ip -n "$ns" link set lo up
  ip -n "$ns" link set eth0 mtu "$mtu" up
  ip -n "$ns" addr add "$addr" dev eth0
  [ -n "$gw" ] && ip -n "$ns" route add default via "$gw"
  true
}

# router: noga w LAN (10.13.0.1/24) i noga do serwera (10.99.0.1/24)
ip netns add ns-router
ip link add v-router type veth peer name eth0 netns ns-router
ip link set v-router master br-lab up
ip -n ns-router link set eth0 up && ip -n ns-router addr add 10.13.0.1/24 dev eth0
ip netns add ns-srv
ip link add r-srv netns ns-router type veth peer name eth0 netns ns-srv
ip -n ns-router link set r-srv up && ip -n ns-router addr add 10.99.0.1/24 dev r-srv
ip -n ns-srv link set eth0 up && ip -n ns-srv addr add 10.99.0.10/24 dev eth0
ip -n ns-srv route add default via 10.99.0.1
ip netns exec ns-router sysctl -qw net.ipv4.ip_forward=1
ip netns exec ns-srv sh -c 'nohup python3 -m http.server 8080 >/dev/null 2>&1 &'

host ns-a 10.13.0.11/24 10.13.0.1          # błąd 1: duplikat …
host ns-b 10.13.0.11/24 10.13.0.1          # … z ns-b (powinno być .12)
host ns-c 10.13.0.13/28 10.13.0.1          # błąd 2: maska /28 zamiast /24
host ns-d 10.13.0.14/24                    # błąd 3: brak trasy domyślnej
host ns-e 10.13.0.15/24 10.13.0.1 9000     # błąd 4: MTU 9000 na łączu 1500
host ns-p 10.13.0.40/24 10.13.0.1          # „drukarka” — poprawny host do testów spoza /28
ip netns exec ns-srv sh -c 'ip link set eth0 mtu 1500'
echo "lab gotowy — uruchom ./check.sh"

# reset.sh:
#   for ns in ns-a ns-b ns-c ns-d ns-e ns-p ns-router ns-srv; do ip netns del $ns 2>/dev/null; done
#   ip link del br-lab 2>/dev/null` },

  { type: 'STEP_BY_STEP', heading: 'Błąd 1: duplikat IP — objaw, hipoteza, dowód, poprawka', body:
`**Objaw:** z ns-c ping do 10.13.0.11 czasem działa, a czasem nie; połączenia TCP do usługi na ns-a zrywają się bez powodu; w logach aplikacji „Connection reset”.

1. **Łącze i adres** w obu podejrzanych: \`ip -n ns-a -br addr\`, \`ip -n ns-b -br addr\` → oba 10.13.0.11/24. Jeśli nie wiesz, gdzie szukać: \`arping -I eth0 -c 3 10.13.0.11\` z trzeciego hosta — **odpowiedzi z dwóch różnych MAC** to dowód.
2. **Tablica ARP sąsiada** zmienia zdanie: \`ip -n ns-c neigh show 10.13.0.11\` uruchomione kilka razy pokazuje raz jeden, raz drugi \`lladdr\`. W Wiresharku filtr \`arp.duplicate-address-detected\` lub alert „Duplicate IP address configured”.
3. **Test DAD przed nadaniem adresu:** \`arping -D -I eth0 -c 2 10.13.0.11\` — kod wyjścia 1 oznacza, że ktoś już używa adresu. Tak samo robi klient DHCP (RFC 5227) i systemd-networkd/NetworkManager.
4. **Przyczyna w realnym świecie:** adres statyczny ręcznie wpisany w zakresie puli DHCP, sklonowana maszyna wirtualna z tą samą konfiguracją, dwa kontenery z \`--ip\` w tej samej sieci, urządzenie, które po restarcie wzięło stary adres.
5. **Poprawka:** \`ip -n ns-b addr del 10.13.0.11/24 dev eth0 && ip -n ns-b addr add 10.13.0.12/24 dev eth0\`; potem **wyczyść cache ARP** sąsiadów (\`ip -n ns-c neigh flush dev eth0\`) albo wyślij gratuitous ARP (\`arping -U -I eth0 10.13.0.12\`).
6. **Zapobieganie:** rezerwacje DHCP zamiast ręcznych adresów, wykluczenia statycznych z puli, IPAM (NetBox), DHCP snooping + Dynamic ARP Inspection na przełącznikach (lekcja 10), alerty z \`arpwatch\`/IDS o zmianie MAC dla IP.
7. **Notatka:** „ns-a i ns-b = 10.13.0.11 (dowód: arping 2 MAC). Zmieniono ns-b na .12, flush ARP. check.sh: test 1 PASS.”` },

  { type: 'NOTE', heading: 'Błąd 2: zła maska — „do jednych działa, do innych nie”', ...img('ns02-13-mask.svg', 'Oś adresów 10.13.0.0–255: według routera i hostów sieć to /24, ale host ns-c z maską /28 uważa za lokalne tylko adresy 0–15. Do bramy .1 i hosta .14 wysyła bezpośrednio, a do drukarki .40 i serwera .200 przez bramę, co daje przekierowania ICMP lub brak łączności. Dowód: ip route get 10.13.0.40 pokazuje via 10.13.0.1.'),
    body:
`ns-c ma 10.13.0.13/**28**. Dla niego „moja sieć” to ${netStr('10.13.0.13', 28)} (adresy .0–.15, broadcast ${bcast('10.13.0.13', 28)}), a dla reszty świata — ${netStr('10.13.0.13', 24)} (broadcast ${bcast('10.13.0.13', 24)}). Skutki zależą od celu:
- cel **.1–.14** (brama, ns-d) — w „jego” /28 → ARP, działa normalnie. Dlatego pierwsze testy („ping bramy działa”) **nie wykrywają** błędu.
- cel **.16–.254** (np. drukarka .40, serwer .200; uwaga: ns-e .15 to jeszcze wnętrze /28) — poza /28 → pakiet do bramy. Router widzi, że cel jest w tej samej sieci, z której przyszedł pakiet: wysyła **ICMP Redirect** i przekazuje pakiet (działa wolniej i asymetrycznie) albo — jeśli redirecty/forwarding na tym interfejsie są zablokowane lub firewall odrzuca ruch „hairpin” — **nie działa**.
- odwrotny kierunek: hosty /24 odpowiadają do ns-c bezpośrednio — ruch jest **asymetryczny**, co psuje stanowe firewalle.
- maska **za szeroka** (np. /16 zamiast /24) daje odwrotny objaw: host próbuje ARP do adresów, które są za routerem — brak odpowiedzi ARP, „Destination Host Unreachable” od samego nadawcy.

**Dowód:** \`ip -n ns-c -br addr\` (widać /28), \`ip -n ns-c route\` (trasa \`10.13.0.0/28 dev eth0\` zamiast /24), \`ip -n ns-c route get 10.13.0.40\` → *via 10.13.0.1*. **Poprawka:** \`ip -n ns-c addr del 10.13.0.13/28 dev eth0; ip -n ns-c addr add 10.13.0.13/24 dev eth0\` (trasa podsieci tworzy się automatycznie; bramę dodaj ponownie, jeśli zniknęła). W produkcji: popraw źródło prawdy — DHCP, netplan, szablon chmury — inaczej błąd wróci przy restarcie.` },

  { type: 'CODE_PRACTICAL', heading: 'check.sh — automatyczny sędzia labu (i wzór testów sieci w CI)', body:
`Skrypt sprawdza z każdego hosta to, czego oczekuje użytkownik: brama, serwer w innej podsieci, usługa HTTP i duży pakiet. Każdy test drukuje PASS/FAIL i zwraca kod wyjścia — tak samo budujesz testy dymne (smoke tests) środowiska w pipeline CI/CD przed wdrożeniem aplikacji.`,
    code:
`#!/usr/bin/env bash
fail=0
t() { local name=$1; shift; if "$@" >/dev/null 2>&1; then echo "PASS $name"; else echo "FAIL $name"; fail=1; fi; }

for ns in ns-a ns-b ns-c ns-d ns-e; do
  t "$ns: brama 10.13.0.1"          ip netns exec $ns ping -c1 -W1 10.13.0.1
  t "$ns: serwer 10.99.0.10"        ip netns exec $ns ping -c1 -W1 10.99.0.10
  t "$ns: sąsiad 10.13.0.40 (spoza /28)" ip netns exec $ns ping -c1 -W1 10.13.0.40
  t "$ns: HTTP serwera"             ip netns exec $ns curl -s -m 3 -o /dev/null http://10.99.0.10:8080/
  t "$ns: pakiet 1472 B bez fragm." ip netns exec $ns ping -c1 -W1 -M do -s 1472 10.13.0.1
done
# unikalność adresów w LAN: każdy adres IP ma dokładnie jeden MAC
dups=$(for ns in ns-a ns-b ns-c ns-d ns-e; do ip -n $ns -4 -br addr show eth0 | awk '{print $3}'; done | sort | uniq -d)
t "brak duplikatów IP" test -z "$dups"
# spójne MTU w domenie L2
t "jedno MTU w br-lab" test "$(for ns in ns-a ns-b ns-c ns-d ns-e; do ip -n $ns -o link show eth0 | grep -o 'mtu [0-9]*'; done | sort -u | wc -l)" -eq 1
exit $fail

# 10.13.0.40 to poprawny host ns-p („drukarka”) z setup.sh — cel spoza /28, który wykrywa złą maskę.
# Ten sam test z Javy (np. w teście integracyjnym środowiska):
#   InetAddress.getByName("10.99.0.10").isReachable(1000)  — ICMP lub TCP 7 (echo), zależnie od uprawnień
#   new Socket().connect(new InetSocketAddress("10.99.0.10", 8080), 3000)  — test usługi, nie tylko ping` },

  { type: 'USAGE', heading: 'Błędy 3 i 4: brak trasy domyślnej i niezgodne MTU', ...img('ns02-13-mtu.svg', 'Host ns-e ma MTU 9000, a łącze i router MTU 1500: mały ping przechodzi, ramka 4000 bajtów jest odrzucana. Testy ping -M do: 1472 bajty przechodzą, 1473 nie, 3000 host wysyła, ale ramka ginie. TCP wisi po handshake, bo MSS wynegocjowano z MTU 9000. Naprawa: jedno MTU w domenie L2.'),
    body:
`**Błąd 3 — ns-d bez trasy domyślnej.** Objaw: ping do sąsiadów i bramy działa, do 10.99.0.10 natychmiast „connect: Network is unreachable” (bez czekania — jądro nie ma trasy, więc nawet nie wysyła pakietu). Dowód: \`ip -n ns-d route\` pokazuje tylko \`10.13.0.0/24 dev eth0\`; \`ip -n ns-d route get 10.99.0.10\` → *RTNETLINK answers: Network is unreachable*. Poprawka: \`ip -n ns-d route add default via 10.13.0.1\`. Odmiany w realnym świecie: brama spoza podsieci (*Nexthop has invalid gateway*), DHCP bez opcji 3 (router), dwie trasy domyślne z różnymi metrykami (VPN!), brak trasy powrotnej na routerze lub serwerze — wtedy pakiet dochodzi, a odpowiedź ginie (\`tcpdump\` na serwerze pokaże przychodzące żądania bez odpowiedzi wychodzących).

**Błąd 4 — ns-e z MTU 9000 na łączu 1500.** Objaw: ping działa, SSH loguje się, ale \`scp\`, \`git clone\` i HTTPS wiszą po kilku sekundach. Dowód: \`ping -M do -s ${maxPayload(1500)} 10.13.0.1\` przechodzi (${maxPayload(1500)} + 28 = 1500), a \`-s ${maxPayload(1500) + 1}\` z ns-e jest **wysyłany** (bo lokalne MTU to 9000), ale nie wraca — ramkę odrzuca most/odbiorca (\`ip -s link\` pokazuje błędy/odrzucenia RX). W obrębie jednej domeny L2 nie ma routera, który odesłałby ICMP „Fragmentation needed”, więc PMTUD **nie pomoże**. Poprawka: \`ip -n ns-e link set eth0 mtu 1500\`. W produkcji: MTU ustawiaj spójnie w całej domenie L2 (jumbo frames tylko end-to-end: karty, przełączniki, hypervisor), a przy tunelach (VPN, VXLAN, Docker overlay) pamiętaj o narzucie — lekcja 01.

**Bonus — zły VLAN.** Objaw identyczny jak przy odłączonym kablu na wyższej warstwie: łącze UP, adres poprawny, ale \`ip neigh\` pokazuje FAILED/INCOMPLETE dla bramy. Na przełączniku \`show interfaces status\` / \`show vlan brief\` ujawnia port w VLAN 20 zamiast 10. W labie odpowiednik: interfejs podpięty do innego mostu (\`bridge link\`, \`ip link show master br-lab\`).

**Gdzie to spotkasz:** kontener nie łączy się z bazą (zła sieć Dockera = zły „VLAN”), pod w Kubernetes widzi serwisy, ale nie Internet (trasa/NAT), aplikacja Java w VPN-ie wisi na dużych odpowiedziach (MTU tunelu), dwie VM-ki z klonu z tym samym IP.` },

  { type: 'PITFALL', heading: 'Pułapki diagnozy, które kosztują godziny', body:
`- **Zaczynanie od góry** („to pewnie DNS”, „to firewall”) zamiast od łącza i adresu. Sprawdź warstwy po kolei — pierwszy niedziałający krok jest przyczyną, reszta to skutki.
- **Ping jako jedyny test.** Ping (ICMP, 84 B) przechodzi przy złym MTU i bywa blokowany tam, gdzie TCP działa. Testuj też usługę (\`curl -v\`, \`nc -vz host port\`) i duże pakiety (\`ping -M do -s 1472\`).
- **Test z „dobrego” hosta.** ns-c ma działać do bramy — błąd maski widać dopiero przy celu spoza /28. Testuj **kilka celów**: brama, sąsiad, adres na końcu podsieci, host za routerem.
- **Stary cache ARP** po poprawce duplikatu — sąsiedzi pamiętają zły MAC do kilkudziesięciu sekund (stan STALE/REACHABLE). Wyczyść cache albo wyślij gratuitous ARP.
- **Poprawka tylko w runtime** (\`ip addr\`), a źródło prawdy (DHCP, netplan, NetworkManager, cloud-init, docker-compose) zostaje złe — po restarcie błąd wraca.
- **Zmiana wielu rzeczy naraz** — nie wiesz, co pomogło, i nie umiesz napisać post-mortem. Jedna zmiana → test → notatka.
- **Wyłączenie firewalla „na chwilę”** jako metoda diagnozy w produkcji. Zamiast tego czytaj liczniki reguł (\`nft list ruleset\` z licznikami, \`iptables -L -v\`) i logi odrzuceń.
- **Ignorowanie asymetrii** — ruch tam idzie inną drogą niż z powrotem (zła maska, dwie bramy); stanowy firewall odrzuca „pakiet spoza połączenia”.
- **Mylenie „Network is unreachable” z „Destination Host Unreachable”** — pierwsze: brak trasy lokalnie (natychmiast); drugie: trasa jest, ale ARP do celu/bramy nie dostał odpowiedzi (po ~3 s).` },

  { type: 'CODE_WRONG', heading: 'Źle: „naprawa” metodą prób i błędów', body:
`Typowy przebieg nieudanej diagnozy — wiele zmian bez dowodów, poprawki psujące bezpieczeństwo i brak notatki. Problem „zniknął”, ale nikt nie wie dlaczego, a po restarcie wróci.`,
    code:
`$ ping 10.99.0.10            # nie działa
$ sudo systemctl restart NetworkManager
$ sudo iptables -F            # „może firewall” — właśnie wyłączyłeś całą ochronę hosta
$ sudo ip link set eth0 mtu 1400
$ sudo ip addr add 10.13.0.99/16 dev eth0     # drugi adres z inną maską „na wszelki wypadek”
$ sudo ip route add default via 10.13.0.254   # brama, która nie istnieje
$ ping 10.99.0.10            # „działa” (bo ktoś w międzyczasie naprawił router)
# zamknięcie zgłoszenia: „zrestartowałem i działa”` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: jedna hipoteza, jeden dowód, jedna zmiana, test i notatka', body:
`Ten sam problem rozwiązany metodycznie. Każdy krok ma polecenie i oczekiwany wynik; zmiana jest jedna, odwracalna i utrwalona w źródle prawdy; notatka nadaje się do zgłoszenia i post-mortem.`,
    code:
`$ ip -br link show eth0          # eth0 UP <BROADCAST,MULTICAST,UP,LOWER_UP>     → łącze OK
$ ip -br addr show eth0          # 10.13.0.14/24                                   → adres i maska OK
$ ip neigh show 10.13.0.1        # 10.13.0.1 lladdr 02:..:01 REACHABLE             → brama odpowiada na ARP
$ ip route get 10.99.0.10        # RTNETLINK answers: Network is unreachable      → BRAK TRASY ← pierwszy błąd
$ ip route                       # tylko 10.13.0.0/24 dev eth0                     → potwierdzenie
# hipoteza: brak trasy domyślnej (DHCP nie podał opcji 3 / brak gateway4 w netplan)
$ sudo ip route add default via 10.13.0.1         # zmiana w runtime
$ ip route get 10.99.0.10        # via 10.13.0.1 dev eth0                          → decyzja jądra OK
$ curl -s -o /dev/null -w '%{http_code}\\n' http://10.99.0.10:8080/   # 200      → usługa OK
# utrwalenie: netplan → routes: [{to: default, via: 10.13.0.1}] ; sudo netplan try
# notatka: „Objaw: brak dostępu poza LAN. Dowód: ip route get → unreachable. Przyczyna: brak trasy domyślnej
#           (netplan bez routes). Zmiana: dodano trasę w netplan. Test: check.sh PASS. Zapobieganie: szablon netplan w repo.”` },

  { type: 'WHEN_TO_USE', heading: 'Objaw → najbardziej prawdopodobna przyczyna → pierwsze polecenie', body:
`Ściąga do pracy i na egzamin (Network+ lubi pytania „technik widzi X — co sprawdzi najpierw?”):`,
    code:
`OBJAW                                              PRZYCZYNA (najczęściej)          PIERWSZE POLECENIE
łącze DOWN / NO-CARRIER                            kabel, port, sterownik, wyłączony ip -br link ; ethtool eth0
adres 169.254.x.x (APIPA)                          brak odpowiedzi DHCP             ip -br addr ; journalctl -u NetworkManager
„raz działa, raz nie”, ARP zmienia MAC             duplikat IP                      arping -c3 <ip> (2 różne MAC?)
do bramy działa, do części LAN nie                 za wąska maska                   ip -br addr ; ip route get <cel>
ARP do zdalnych adresów, Host Unreachable          za szeroka maska                 ip route ; ip neigh
„Network is unreachable” natychmiast               brak trasy (default)             ip route get <cel>
brama nie odpowiada na ARP, łącze UP               zły VLAN / zły most / zła brama  ip neigh ; show vlan brief
mały ping OK, duże transfery wiszą                 niezgodne MTU / blackhole PMTUD  ping -M do -s 1472 <cel>
ping po IP działa, po nazwie nie                   DNS                              resolvectl query <nazwa>
port „Connection refused”                          usługa nie słucha / zły adres    ss -ltnp
port „timeout”                                     firewall / brak trasy powrotnej  tcpdump -ni eth0 port <p>` },

  { type: 'SUMMARY', heading: 'Podsumowanie rozdziału i Debug Labu', body:
`- Metodyka od kabla w górę: łącze → adres/maska → sąsiad (ARP) → brama i trasa → ścieżka/MTU → DNS i usługa. Zatrzymaj się na pierwszym kroku, który zawodzi.
- Każdy krok to jedno polecenie (\`ip -br link\`, \`ip -br addr\`, \`ip neigh\`/\`arping\`, \`ip route get\`, \`ping -M do -s\`, \`resolvectl\`, \`ss\`, \`curl -v\`) i zapis: objaw → hipoteza → dowód → poprawka → test → dokumentacja (7 kroków CompTIA).
- Duplikat IP: dwa MAC na \`arping\`, skacząca tablica ARP; zapobiegaj rezerwacjami DHCP, IPAM, DAD, DHCP snooping + DAI.
- Zła maska: decyzję „lokalny czy przez bramę” podejmuje host swoją maską — za wąska daje redirecty i asymetrię, za szeroka daje ARP w próżnię. Dowód: \`ip route get\`.
- Brak trasy domyślnej: „Network is unreachable” natychmiast; pamiętaj też o trasie powrotnej.
- MTU: w jednej domenie L2 jedno MTU; test \`ping -M do -s 1472\` (IPv4: rozmiar − 28 B, IPv6: − 48 B); objaw „ping OK, transfery wiszą”.
- Zły VLAN: łącze UP, adres OK, ARP bez odpowiedzi — sprawdź port na przełączniku.
- check.sh to wzór testów dymnych sieci w CI; poprawki utrwalaj w źródle prawdy (netplan, DHCP, IaC).
- Koniec rozdziału 02. Następny rozdział: **adresacja IP i routing** — nagłówek IPv4, maski od zera, CIDR i subnetting.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const [a, p, gw, b] of CASES) {
  ex(`Host ${a}/${p}, brama ${gw ?? '(brak)'}. Jak wyśle pakiet do ${b}? Uzasadnij obliczeniem adresów sieci.`,
    'Policz adres sieci hosta i celu maską NADAWCY; potem sprawdź bramę.',
    `Sieć hosta: ${netStr(a, p)}; cel w masce hosta: ${netStr(b, p)} → ${decide(a, p, gw, b)}. Potwierdzenie na Linuksie: ip route get ${b}.`);
}
for (const [ip, p] of [['10.13.0.13', 28], ['10.13.0.200', 26], ['192.168.10.77', 27], ['172.20.33.5', 20]]) {
  ex(`Dla adresu ${ip}/${p} podaj adres sieci, broadcast i zakres hostów.`,
    'Wyzeruj bity hosta (sieć), ustaw je na 1 (broadcast).',
    `Sieć ${netStr(ip, p)}, broadcast ${bcast(ip, p)}, hosty ${intIp(netOf(ip, p) + 1)} – ${intIp(netOf(ip, p) + 2 ** (32 - p) - 2)}.`);
}
for (const [mtu, v6] of [[1500, false], [1500, true], [9000, false], [1420, false]]) {
  ex(`Jaki maksymalny rozmiar danych podasz w ping -M do -s, żeby przetestować MTU ${mtu} dla ${v6 ? 'IPv6' : 'IPv4'}?`,
    v6 ? 'Nagłówek IPv6 40 B + ICMPv6 8 B.' : 'Nagłówek IPv4 20 B + ICMP 8 B.',
    `${mtu} − ${v6 ? 48 : 28} = ${maxPayload(mtu, v6)} B (np. ping ${v6 ? '-6 ' : ''}-M do -s ${maxPayload(mtu, v6)} <cel>); o 1 B więcej musi już nie przejść.`);
}
const more = [
  ['Wykonaj zadanie z planu: uruchom setup.sh, znajdź i napraw wszystkie cztery błędy, a check.sh ma pokazać same PASS. Dla każdego błędu zapisz: objaw, hipoteza, dowód (polecenie i wynik), poprawka, test.', 'Kolejność: łącze → adres → ARP → trasa → MTU.', 'Duplikat: arping -c3 10.13.0.11 z ns-c → odpowiedzi z dwóch MAC; ns-b → 10.13.0.12/24, flush ARP. Maska: ip -n ns-c -br addr → /28, ip route get 10.13.0.40 → via 10.13.0.1; zmiana na /24. Trasa: ip -n ns-d route get 10.99.0.10 → Network is unreachable; route add default via 10.13.0.1. MTU: ip -n ns-e link → mtu 9000; ping -M do -s 1473 nie wraca; mtu 1500. check.sh: wszystkie PASS. Notatka z czterema wpisami objaw/dowód/zmiana/test.'],
  ['Użytkownik zgłasza: „połączenie z serwerem plików co kilka minut się zrywa, a potem wraca”. Opisz diagnozę pod kątem duplikatu IP.', 'arping, ip neigh, logi DHCP, tablica MAC przełącznika.', 'Z innego hosta: arping -c5 <IP serwera> — czy odpowiadają dwa MAC? ip neigh show <IP> kilka razy — czy lladdr się zmienia? Na przełączniku: show mac address-table address <MAC> → port drugiego urządzenia; show ip arp. Logi DHCP: czy adres jest w puli i komuś wydany? Windows: zdarzenie 4199 (konflikt adresu). Poprawka: zmiana adresu intruza, rezerwacja, wykluczenie z puli; zapobieganie: DHCP snooping + DAI, IPAM.'],
  ['Wyjaśnij, dlaczego zła maska /28 na ns-c nie psuje pingu do bramy, a psuje komunikację z częścią LAN-u. Podaj polecenie dowodu.', 'Decyzja hosta jego maską.', 'Brama .1 jest w /28 ns-c (.0–.15), więc ARP i ping działają. Cele od .16 wzwyż są dla ns-c poza siecią → idą do bramy; router odsyła ICMP Redirect albo ruch hairpin jest blokowany, a odpowiedzi wracają bezpośrednio (asymetria). Dowód: ip -n ns-c route get 10.13.0.40 → via 10.13.0.1 (a dla .14 → dev eth0).'],
  ['Czym różni się komunikat „connect: Network is unreachable” od „Destination Host Unreachable” i co mówi o miejscu błędu?', 'Lokalna tablica routingu vs ARP.', '„Network is unreachable” zwraca lokalne jądro natychmiast — brak pasującej trasy (np. brak default). „Destination Host Unreachable” pojawia się po kilku sekundach — trasa istnieje, ale ARP o następny przeskok (cel lub bramę) nie dostał odpowiedzi; jeśli przychodzi „From <router>”, to router nie znalazł celu w swojej sieci.'],
  ['Aplikacja Spring Boot w kontenerze łączy się z PostgreSQL, ale zapytania zwracające duże wyniki „wiszą”, a małe działają. Jak potwierdzisz i naprawisz problem z MTU?', 'Overlay/VPN, ping -M do, MTU interfejsów, MSS.', 'Sprawdź MTU interfejsów na ścieżce (ip link w kontenerze, na hoście, w tunelu VPN/VXLAN). Test: ping -M do -s 1472 <db> — nie przechodzi, a -s 1372 przechodzi → ścieżka ma mniejsze MTU. Naprawa: ustaw MTU sieci Dockera (daemon.json "mtu" lub driver_opts com.docker.network.driver.mtu) zgodne z tunelem, lub MSS clamping na bramie (iptables TCPMSS --clamp-mss-to-pmtu); upewnij się, że ICMP „Fragmentation needed” nie jest blokowany.'],
  ['Laptop dostaje adres 169.254.12.7. Co to znaczy i jakie są kolejne kroki?', 'APIPA, DHCP.', 'Adres link-local (APIPA, RFC 3927) — host nie dostał odpowiedzi DHCP. Kroki: łącze i VLAN (czy port w dobrym VLAN-ie?), czy serwer DHCP działa i ma wolne adresy, czy relay (ip helper-address) jest skonfigurowany, czy DHCP snooping nie blokuje (port zaufany). tcpdump -ni eth0 port 67 or port 68 pokaże, czy DISCOVER wychodzi i czy przychodzi OFFER.'],
  ['Opisz, jak rozpoznasz zły VLAN na porcie dostępowym, mając dostęp tylko do hosta (bez przełącznika).', 'ARP, LLDP, DHCP.', 'Łącze UP, adres poprawny, ale ip neigh pokazuje bramę jako INCOMPLETE/FAILED; DHCP daje adres z innej podsieci lub APIPA; lldpctl/tcpdump LLDP pokazuje Port VLAN ID innego VLAN-u; tcpdump -ni eth0 arp pokazuje zapytania ARP obcej podsieci. Wniosek: host jest w innej domenie rozgłoszeniowej — zgłoś port do zespołu sieci z dowodem (VLAN z LLDP).'],
  ['Napisz w Javie prosty test łączności do wielu celów (host:port) z limitem czasu, który odróżnia „odmowa połączenia” od „timeout”.', 'Socket.connect z timeoutem, ConnectException vs SocketTimeoutException.', 'for (var t : targets) { try (var s = new Socket()) { s.connect(new InetSocketAddress(t.host(), t.port()), 3000); System.out.println("OK " + t); } catch (ConnectException e) { System.out.println("ODMOWA (usługa nie słucha / RST) " + t); } catch (SocketTimeoutException e) { System.out.println("TIMEOUT (firewall/brak trasy/zgubione pakiety) " + t); } catch (NoRouteToHostException e) { System.out.println("BRAK TRASY " + t); } }. Odmowa = host osiągalny; timeout = coś po drodze gubi pakiety.'],
  ['Wyjaśnij, dlaczego po poprawieniu duplikatu IP niektóre hosty nadal nie mogą się połączyć przez kilkadziesiąt sekund, i jak to przyspieszyć.', 'Cache ARP, stany neighbor.', 'Sąsiedzi mają w cache ARP stary (zły) MAC w stanie REACHABLE/STALE i używają go do czasu ponownej weryfikacji. Przyspieszenie: gratuitous ARP z poprawnego hosta (arping -U / -A), ip neigh flush na sąsiadach, na routerze clear arp; na przełączniku tablica MAC aktualizuje się przy pierwszej ramce.'],
  ['Zaplanuj zapobieganie czterem błędom z labu w firmowej sieci (po jednym środku technicznym i jednym procesowym na błąd).', 'IPAM, DHCP, IaC, monitoring.', 'Duplikat: DHCP snooping + DAI / rezerwacje i IPAM (NetBox). Maska: konfiguracja z DHCP lub szablon IaC (netplan, cloud-init) + przegląd zmian. Brak trasy: DHCP opcja 3 / test check.sh w CI po zmianie. MTU: standard MTU per segment w dokumentacji, monitoring błędów interfejsów i test ping -M do po zmianach. Proces: zmiany przez review, testy dymne, runbook diagnozy.'],
  ['Jak w Wiresharku/tcpdump zobaczysz każdy z czterech błędów labu? Podaj filtry.', 'arp, icmp, tcp.analysis.', 'Duplikat: arp (dwie odpowiedzi is-at dla jednego IP z różnych MAC; Wireshark: arp.duplicate-address-detected). Maska: icmp.type == 5 (Redirect) z routera. Brak trasy: nic nie wychodzi (błąd lokalny) — sam brak pakietów jest dowodem. MTU: tcp.analysis.retransmission dla dużych segmentów bez ACK, ramki o długości > 1514 w capture po stronie hosta, brak ICMP type 3 code 4.'],
  ['Na czym polega 7-krokowa metodyka CompTIA i jak mapuje się na Debug Lab?', 'Network+ domena 5.', '1) Zidentyfikuj problem (zbierz informacje, objawy, co się zmieniło, odtwórz). 2) Ustal teorię prawdopodobnej przyczyny (od dołu lub od góry modelu OSI). 3) Przetestuj teorię (polecenie i dowód; jeśli nie — nowa teoria lub eskalacja). 4) Ustal plan działania i skutki uboczne. 5) Wdróż rozwiązanie lub eskaluj. 6) Zweryfikuj pełną funkcjonalność i wdroż środki zapobiegawcze. 7) Udokumentuj ustalenia, działania i wyniki. W labie: objaw → hipoteza → dowód → poprawka → check.sh → notatka.'],
  ['Serwer otrzymuje żądania (widać je w tcpdump), ale klient nie dostaje odpowiedzi. Jakie są najczęstsze przyczyny?', 'Trasa powrotna, asymetria, firewall.', 'Brak trasy powrotnej na serwerze do sieci klienta (lub odpowiedź wychodzi inną bramą/interfejsem — asymetria odrzucana przez stanowy firewall lub rp_filter), zła maska na serwerze (odpowiada przez ARP zamiast przez bramę), firewall wychodzący, NAT bez śledzenia. Dowód: ip route get <klient> na serwerze, tcpdump na obu interfejsach, sysctl net.ipv4.conf.*.rp_filter.'],
  ['Przygotuj szablon notatki z diagnozy sieci, którą można wkleić do zgłoszenia (ticketu) lub post-mortem.', 'Pola: objaw, zakres, oś czasu, dowody, przyczyna, zmiana, test, zapobieganie.', 'Objaw (kto, co, od kiedy, jak odtworzyć) | Zakres (które hosty/sieci) | Oś czasu | Dowody (polecenia i wyniki, capture) | Przyczyna źródłowa | Zmiana (co, gdzie, kto, rollback) | Test (check.sh, curl) | Zapobieganie (monitoring, IaC, proces) | Linki (zgłoszenia, commity).'],
  ['Rozbuduj check.sh o test, który wykrywa złą maskę na dowolnym hoście bez znajomości „poprawnej” maski — porównując prefiks hosta z prefiksem routera.', 'ip -br addr na hoście i routerze.', 'ref=$(ip -n ns-router -4 -br addr show eth0 | awk "{print $3}" | cut -d/ -f2); for ns in ...; do p=$(ip -n $ns -4 -br addr show eth0 | awk "{print $3}" | cut -d/ -f2); t "$ns: prefiks /$p = /$ref" test "$p" = "$ref"; done. W produkcji to samo robi się w audycie konfiguracji (Ansible facts, NetBox) — maska hosta musi zgadzać się z definicją podsieci w IPAM.'],
  ['Kolega naprawił ns-d poleceniem ip route add default via 10.13.0.254. Ping dalej nie działa. Co pokazują ip route get i ip neigh i dlaczego?', 'Brama musi istnieć i odpowiadać na ARP.', 'ip route get 10.99.0.10 → via 10.13.0.254 dev eth0 (trasa jest), ale ip neigh show 10.13.0.254 → INCOMPLETE/FAILED: nikt nie ma tego adresu, więc ARP nie dostaje odpowiedzi i ping zwraca „Destination Host Unreachable” od samego hosta. Poprawka: ip route replace default via 10.13.0.1. Wniosek: po zmianie trasy zawsze sprawdź ARP do bramy.'],
];
for (const [p, h, s] of more) ex(p, h, s);

const quiz = buildQuiz({ CASES });
writeLesson(process.argv[2], theory, exercises, quiz);
