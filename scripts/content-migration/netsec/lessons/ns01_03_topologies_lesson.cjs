// Generuje lekcje _ns_01_fundamenty_sieci/03_NetworkTopologiesAndTypes.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns01_03_topologies_lesson.cjs src/main/resources/content/_ns_01_fundamenty_sieci/03_NetworkTopologiesAndTypes.json
const { quizBuilder, exerciseBuilder, writeLesson, fmt } = require('./lib.cjs');

const mesh = (n) => (n * (n - 1)) / 2;
const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Topologia, czyli mapa tego, co może się zepsuć', ...img('ns01-03-topologies.svg', 'Sześć topologii: magistrala ze wspólnym kablem, pierścień sześciu węzłów, gwiazda z przełącznikiem w środku, drzewo z przełącznikiem core i dwoma przełącznikami dostępowymi, siatka pełna pięciu węzłów i hub-and-spoke z centralą i oddziałami; pod każdą wzór na liczbę łączy.'),
    body:
`**Topologia** to odpowiedź na pytanie: *kto jest połączony z kim i którędy*. Brzmi jak temat z podręcznika do szkoły średniej, ale w praktyce to najważniejsza wiedza przy trzech decyzjach, które podejmuje każdy backend developer i każdy obrońca:

- **Co się stanie, gdy jeden element padnie?** Topologia pokazuje *pojedyncze punkty awarii* (SPOF) — przełącznik, router, strefę dostępności, a nawet jeden serwer DNS.
- **Jak daleko jest od usługi do usługi?** Liczba przeskoków i wąskie gardła decydują o opóźnieniu wywołań między mikroserwisami.
- **Jak daleko zajdzie napastnik?** Płaska sieć, w której „każdy widzi każdego”, to dla obrońcy koszmar: przejęcie jednego hosta otwiera drogę do wszystkich. Topologia logiczna (segmenty, VLAN-y, sieci Dockera) wyznacza *promień rażenia* (blast radius).

W tej lekcji poznasz klasyczne topologie (magistrala, pierścień, gwiazda, drzewo, siatka, hub-and-spoke), podział sieci według zasięgu (PAN … WAN, SAN), modele komunikacji (klient-serwer, peer-to-peer) i dwie architektury, które spotkasz w każdym data center: **trójwarstwową** i **spine-leaf**. Na koniec przeniesiemy to na Docker Compose, Kubernetes i chmurę — bo tam topologię projektujesz *ty*, w pliku YAML.` },

  { type: 'DEFINITION', heading: 'Słownik: topologia fizyczna, logiczna i rodzaje połączeń', body:
`- **Węzeł (node)** — każde urządzenie w sieci: host, przełącznik, router, punkt dostępowy, kontener.
- **Łącze (link)** — połączenie między dwoma węzłami: kabel, światłowód, kanał radiowy, tunel VPN, wirtualny interfejs \`veth\`.
- **Topologia fizyczna** — jak *naprawdę* biegną kable i fale radiowe.
- **Topologia logiczna** — jak *płyną dane* i kto kogo „widzi”. Może różnić się od fizycznej: kilka VLAN-ów na jednym przełączniku to kilka logicznych sieci na jednej fizycznej gwieździe.
- **Magistrala (bus)** — wszyscy na jednym wspólnym medium (historyczny Ethernet 10BASE2 na kablu koncentrycznym z terminatorami). Każda ramka trafia do wszystkich; kolizje; przerwanie kabla zatrzymuje całość.
- **Pierścień (ring)** — każdy węzeł ma dwóch sąsiadów, dane krążą w pętli (Token Ring, FDDI, dziś pierścienie światłowodowe operatorów z ochroną, np. ERPS, oraz pierścień węzłów w Cassandrze czy w consistent hashing).
- **Gwiazda (star)** — wszystkie węzły podłączone do centralnego urządzenia (dziś przełącznika). Standard współczesnego Ethernetu i Wi-Fi.
- **Rozszerzona gwiazda / drzewo (tree)** — gwiazdy połączone hierarchicznie (przełącznik piętra → przełącznik budynku).
- **Siatka pełna (full mesh)** — każdy z każdym; liczba łączy rośnie jak **n(n − 1)/2**.
- **Siatka częściowa (partial mesh)** — tylko wybrane nadmiarowe łącza; kompromis kosztu i odporności.
- **Punkt-punkt (point-to-point)** — dokładnie dwa węzły na łączu (łącze między routerami, tunel).
- **Hub-and-spoke** — oddziały łączą się tylko z centralą (spoke → hub), ruch oddział–oddział idzie przez centralę.
- **Hybrydowa** — połączenie kilku powyższych; tak wygląda prawie każda prawdziwa sieć.
- **SPOF (single point of failure)** — element, którego awaria wyłącza całą usługę lub segment.` },

  { type: 'ANALOGY', heading: 'Topologie jako sieć dróg i lotnisk', body:
`- **Magistrala** to jedna główna ulica w miasteczku: wszystkie domy stoją przy niej. Wystarczy, że jeden samochód zablokuje jezdnię, a cały ruch staje. Do tego wszyscy słyszą, co się dzieje na ulicy.
- **Pierścień** to obwodnica bez skrzyżowań: z każdego miejsca jedziesz do sąsiada. Jeśli obwodnica jest dwukierunkowa, po wypadku pojedziesz „naokoło”. Drugi wypadek dzieli miasto na dwie części.
- **Gwiazda** to lotnisko przesiadkowe: wszystkie loty idą przez jeden hub. Prosto, tanio i łatwo zarządzać, ale gdy hub zamknie mgła, nikt nigdzie nie poleci.
- **Siatka pełna** to bezpośrednie połączenia między wszystkimi miastami. Bardzo odporna i szybka, ale przy 100 miastach potrzebujesz ${fmt(mesh(100))} tras.
- **Hub-and-spoke** to firma z centralą w Warszawie: każdy oddział ma linię tylko do centrali, a paczka z Krakowa do Gdańska jedzie przez Warszawę.

Projektując sieć, zawsze płacisz za odporność: więcej dróg to więcej kabli, portów, konfiguracji i rzeczy do monitorowania. Sztuka polega na tym, by dodać nadmiarowość *tam, gdzie awaria boli najbardziej*.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Data center: model trójwarstwowy i spine-leaf', ...img('ns01-03-three-tier-vs-spine-leaf.svg', 'Po lewej sieć trójwarstwowa: dwa przełączniki core, cztery distribution i sześć access; ruch między serwerami z różnych bloków przechodzi do pięciu przełączników. Po prawej spine-leaf: trzy spine i pięć leaf, każdy leaf połączony z każdym spine, ruch zawsze leaf–spine–leaf.'),
    body:
`Klasyczna sieć firmowa i dawne centra danych to **model trójwarstwowy**: *access* (przełączniki, do których podłączone są komputery i serwery), *distribution* (agregacja, routing między VLAN-ami, polityki) i *core* (szybki szkielet). To drzewo z nadmiarowymi łączami — dobre dla ruchu **north-south** (klient z zewnątrz ↔ serwer).

Mikroserwisy zmieniły proporcje: większość ruchu to **east-west** (serwis ↔ serwis, aplikacja ↔ baza, replikacja). Dlatego nowoczesne DC używa **spine-leaf** (sieci Clos):`,
    code:
`Spine-leaf w liczbach:
  S spine, L leaf  →  łączy między warstwami: S × L
  4 spine × 16 leaf = 64 łącza
  serwer → serwer na innym leafie: zawsze 3 przełączniki (leaf → spine → leaf)
  serwer → serwer na tym samym leafie: 1 przełącznik

Nadsubskrypcja (oversubscription) leafa = przepustowość w dół / przepustowość w górę
  48 portów × 25 Gb/s do serwerów = 1200 Gb/s
   6 portów × 100 Gb/s do spine   =  600 Gb/s
  1200 / 600 = 2:1  → przy pełnym obciążeniu serwery dostaną połowę

Zalety: przewidywalne opóźnienie, ECMP (równoległe ścieżki przez wszystkie spine),
awaria jednego spine zabiera 1/S przepustowości, a nie łączność; skalujesz, dokładając spine lub leaf.` },

  { type: 'NOTE', heading: 'Sieci według zasięgu: od PAN do WAN i SAN', body:
`Podział według zasięgu pojawia się na egzaminie Network+ i w ofertach operatorów. Granice są umowne — liczy się charakter sieci:`,
    code:
`skrót  nazwa                          zasięg i przykład
PAN    Personal Area Network          kilka metrów: słuchawki Bluetooth, smartwatch
LAN    Local Area Network             budynek/piętro: biuro, dom, laboratorium
WLAN   Wireless LAN                   LAN przez Wi-Fi (802.11)
CAN    Campus Area Network            kilka budynków jednej organizacji: uczelnia, fabryka
MAN    Metropolitan Area Network      miasto: sieć miejska, światłowody operatora
WAN    Wide Area Network              kraj, kontynent, świat: łącza między oddziałami, Internet
SAN    Storage Area Network           osobna sieć do pamięci masowej: Fibre Channel, iSCSI, NVMe-oF

Pokrewne pojęcia:
SD-WAN        programowo sterowany WAN wybierający łącze wg aplikacji (lekcja w rozdziale 13)
intranet      sieć wewnętrzna organizacji; extranet — jej część udostępniona partnerom
VPC / VNet    logiczna sieć prywatna w chmurze — topologię definiujesz w kodzie (Terraform)` },

  { type: 'STEP_BY_STEP', heading: 'Jak przeanalizować topologię pod kątem awarii', ...img('ns01-03-failure.svg', 'Trzy panele: w gwieździe awaria przełącznika odcina wszystkie sześć hostów; w pierścieniu przerwane jedno łącze nie odcina nikogo, bo ruch idzie dookoła; w siatce pełnej awaria węzła C nie wpływa na komunikację pozostałych czterech.'),
    body:
`Tej samej metody używa architekt przy projekcie, SRE przy przeglądzie niezawodności i obrońca przy ocenie skutków ataku na dostępność:
1. **Narysuj węzły i łącza** — także te „niewidzialne”: zasilanie, strefę dostępności, serwer DNS, load balancer, łącze do Internetu.
2. **Policz łącza.** Gwiazda z n hostami: n łączy; pierścień: n; drzewo: liczba węzłów − 1; siatka pełna: n(n − 1)/2 (dla 6 węzłów: ${mesh(6)}, dla 10: ${mesh(10)}).
3. **Usuń w myślach jeden element naraz** — najpierw każde urządzenie centralne, potem każde łącze. Zapisz, kto traci łączność z kim.
4. **Oznacz SPOF-y** — elementy, których usunięcie odcina więcej niż jeden węzeł albo całą usługę.
5. **Sprawdź awarie podwójne** dla krytycznych usług: pierścień przeżyje jedną przerwę, ale druga dzieli go na dwie części.
6. **Oceń wpływ**: ilu użytkowników, które usługi, jak długo (czas przełączenia: STP, VRRP, ECMP, health checki).
7. **Zdecyduj**: dodać nadmiarowość (drugi przełącznik, drugie łącze, druga strefa), zaakceptować ryzyko albo zmienić architekturę (np. gwiazda → spine-leaf).

Przykład: biuro z jednym przełącznikiem i jednym routerem ma dwa SPOF-y. Drugi router z VRRP usuwa jeden z nich; drugi przełącznik wymaga podłączenia hostów do obu (dwie karty sieciowe lub stos przełączników) — inaczej awaria nadal odetnie hosty z „martwego” przełącznika.` },

  { type: 'CODE_BASIC', heading: 'Policz to w Javie: łącza, SPOF i nadsubskrypcja', body:
`Krótki program, który liczy łącza dla topologii i sprawdza, czy po usunięciu węzła graf pozostaje spójny (przeszukiwanie BFS) — dokładnie tak działa analiza SPOF w narzędziach do modelowania sieci.`,
    code:
`import java.util.*;

public class Topology {
    static long fullMeshLinks(int n) { return (long) n * (n - 1) / 2; }

    // czy graf pozostaje spójny po usunięciu węzła "removed"?
    static boolean connectedWithout(Map<String, Set<String>> g, String removed) {
        List<String> nodes = g.keySet().stream().filter(v -> !v.equals(removed)).toList();
        if (nodes.isEmpty()) return true;
        Set<String> seen = new HashSet<>(List.of(nodes.get(0)));
        Deque<String> queue = new ArrayDeque<>(seen);
        while (!queue.isEmpty()) {
            for (String next : g.get(queue.poll())) {
                if (!next.equals(removed) && seen.add(next)) queue.add(next);
            }
        }
        return seen.size() == nodes.size();
    }

    public static void main(String[] args) {
        System.out.println(fullMeshLinks(10));            // 45
        Map<String, Set<String>> star = Map.of(
            "SW", Set.of("A", "B", "C"), "A", Set.of("SW"), "B", Set.of("SW"), "C", Set.of("SW"));
        for (String v : star.keySet())
            if (!connectedWithout(star, v)) System.out.println("SPOF: " + v);   // SPOF: SW

        double down = 48 * 25, up = 6 * 100;              // Gb/s
        System.out.printf("nadsubskrypcja %.1f:1%n", down / up);               // 2.0:1
    }
}` },

  { type: 'CODE_PRACTICAL', heading: 'Topologia logiczna w Docker Compose', ...img('ns01-03-docker-logical.svg', 'Po lewej jeden host fizyczny z kontenerami proxy, app i db. Po prawej dwie logiczne sieci: frontend z proxy i app oraz backend z app i db; proxy nie ma ścieżki do bazy.'),
    body:
`Fizycznie wszystkie kontenery stoją na jednym hoście. Logicznie każda sieć Dockera to osobny **bridge**, czyli wirtualny przełącznik, a więc osobna gwiazda. Kontener w dwóch sieciach działa jak router między nimi (choć domyślnie nie przekazuje ruchu dalej). Tak projektujesz segmentację w pliku YAML:`,
    code:
`services:
  proxy:
    image: caddy:2
    ports: ["443:443"]
    networks: [frontend]
  app:
    image: javaquest/app:1.0
    networks: [frontend, backend]     # jedyny "most" między segmentami
  db:
    image: postgres:17
    networks: [backend]               # brak ports: — baza niedostępna z hosta

networks:
  frontend: {}
  backend:
    internal: true                    # brak trasy do Internetu z tej sieci

# Sprawdzenie topologii:
# docker network ls
# docker network inspect <projekt>_backend --format '{{range .Containers}}{{.Name}} {{end}}'
# docker compose exec proxy ping db   → bad address 'db' (inna sieć, brak nazwy w DNS)` },

  { type: 'USAGE', heading: 'Gdzie spotkasz topologie w pracy developera i obrońcy', body:
`- **Dom i małe biuro** — fizyczna gwiazda (router z wbudowanym przełącznikiem) plus Wi-Fi. Router jest SPOF-em i jednocześnie granicą bezpieczeństwa.
- **Biuro firmowe** — drzewo/model trójwarstwowy z VLAN-ami; pętle w drzewie z nadmiarowymi łączami blokuje STP (rozdział 02).
- **Data center i chmura** — spine-leaf pod spodem; w AWS widzisz tylko logiczną warstwę: VPC, podsieci, tablice routingu.
- **Wiele VPC i oddziałów** — *hub-and-spoke* przez AWS Transit Gateway lub Azure Virtual WAN zamiast siatki peeringów (przy 10 VPC pełna siatka to ${mesh(10)} połączeń peering do utrzymania). Hub to też naturalne miejsce na inspekcję ruchu (firewall).
- **VPN** — hub-and-spoke (wszyscy do koncentratora) vs pełna siatka (WireGuard mesh, Tailscale) — kompromis między kontrolą a opóźnieniem.
- **Kubernetes** — model sieci zakłada, że *każdy pod może rozmawiać z każdym podem* — logicznie pełna siatka. Dlatego NetworkPolicy (domyślne „deny all”) jest kluczowa dla bezpieczeństwa.
- **Service mesh** — mimo nazwy nie jest fizyczną siatką; to sidecary/proxy tworzące logiczną siatkę połączeń mTLS.
- **Systemy rozproszone** — topologie replikacji: lider–naśladowcy w PostgreSQL (gwiazda), pierścień tokenów w Cassandrze, peer-to-peer w protokołach plotkujących (gossip), BitTorrent, WebRTC.
- **Bezpieczeństwo** — topologia logiczna określa, jak daleko dotrze ruch boczny po przejęciu hosta. Płaska sieć = jeden segment = zero barier.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy w myśleniu o topologii', body:
`- **„Fizyczna = logiczna”.** Jeden przełącznik może obsługiwać 10 VLAN-ów, czyli 10 logicznych sieci. Odwrotnie: dwa budynki połączone tunelem są logicznie jedną siecią.
- **Ukryte SPOF-y.** Dwa serwery aplikacji w jednej szafie z jednym zasilaczem, dwa przełączniki na jednym UPS-ie, „wysoko dostępna” baza z replikami w jednej strefie dostępności, jeden resolver DNS.
- **„Pełna siatka zawsze najlepsza”.** Koszt i złożoność rosną kwadratowo: 50 węzłów to ${fmt(mesh(50))} łączy. W praktyce stosuje się siatkę częściową lub spine-leaf.
- **Hub zamiast przełącznika.** Stary *hub* to logicznie magistrala: każdą ramkę dostają wszyscy, występują kolizje, a każdy może podsłuchiwać. Przełącznik tworzy logiczną gwiazdę z osobnymi domenami kolizyjnymi.
- **Pętla w gwieździe.** Dodanie „zapasowego” kabla między przełącznikami bez STP tworzy pętlę L2 i burzę rozgłoszeniową, która kładzie całą sieć w kilka sekund.
- **Wi-Fi to nie przewodowa gwiazda.** Fizycznie klienci łączą się do AP (gwiazda), ale medium radiowe jest współdzielone jak magistrala: jeden wolny klient spowalnia wszystkich.
- **Hub-and-spoke a opóźnienie.** Ruch oddział–oddział przez odległą centralę dokłada opóźnienie. To częsta przyczyna „wolnej aplikacji” po migracji do VPN.` },

  { type: 'CODE_WRONG', heading: 'Źle: płaska sieć, w której wszystko widzi wszystko', body:
`Domyślna sieć Compose: wszystkie usługi w jednym segmencie, baza wystawiona na hosta „bo wygodnie”. Przejęcie proxy albo panelu admina daje napastnikowi bezpośredni dostęp do bazy i Redisa.`,
    code:
`services:
  proxy:   { image: caddy:2,           ports: ["443:443"] }
  admin:   { image: javaquest/admin:1,  ports: ["8081:8080"] }
  app:     { image: javaquest/app:1 }
  db:      { image: postgres:17,        ports: ["5432:5432"] }   # baza na wszystkich interfejsach hosta!
  redis:   { image: redis:7,            ports: ["6379:6379"] }   # Redis bez hasła, też wystawiony
# Jedna sieć "default" = logiczna gwiazda bez barier:
# proxy → db, admin → redis, każdy → każdy.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: segmenty według funkcji i minimum połączeń', body:
`Każda usługa należy tylko do sieci, których potrzebuje; dane są w sieci wewnętrznej bez wyjścia na świat; panel admina nasłuchuje tylko na pętli zwrotnej hosta. To zasada najmniejszych uprawnień zastosowana do topologii.`,
    code:
`services:
  proxy: { image: caddy:2, ports: ["443:443"], networks: [edge] }
  admin: { image: javaquest/admin:1, ports: ["127.0.0.1:8081:8080"], networks: [backend] }
  app:   { image: javaquest/app:1, networks: [edge, backend] }
  db:    { image: postgres:17, networks: [data] }
  redis: { image: redis:7, command: ["redis-server", "--requirepass", "\${REDIS_PASSWORD}"], networks: [data] }
  # app potrzebuje danych → dołącz ją do sieci data (albo pośrednik/usługa dostępu do danych)

networks:
  edge: {}
  backend: { internal: true }
  data:    { internal: true }
# Weryfikacja: docker compose exec proxy nc -zv db 5432 → nazwa nie istnieje w tej sieci` },

  { type: 'WHEN_TO_USE', heading: 'Jak dobrać topologię do potrzeby', body:
`- **Gwiazda / drzewo** — LAN w biurze i domu; tanio, prosto, łatwo diagnozować. Nadmiarowość dodawaj w rdzeniu (dwa przełączniki core, łącza agregowane).
- **Spine-leaf** — data center z dużym ruchem east-west, klastry Kubernetes, platformy z mikroserwisami. Przewidywalne opóźnienie i łatwe skalowanie.
- **Hub-and-spoke** — wiele oddziałów lub wiele VPC, gdy liczy się centralna kontrola i inspekcja ruchu; zaakceptuj dodatkowe opóźnienie oddział–oddział.
- **Siatka częściowa** — łącza WAN między kluczowymi lokalizacjami, gdzie awaria jednego łącza nie może zatrzymać firmy.
- **Siatka pełna** — mała liczba krytycznych węzłów (np. 3–5 routerów rdzeniowych, klaster baz danych w trzech strefach).
- **Pierścień** — sieci operatorskie i przemysłowe z szybką ochroną pierścienia (ERPS, poniżej 50 ms); w aplikacjach — algorytmy rozpraszania danych (consistent hashing).
- **Punkt-punkt** — łącza między routerami, tunele site-to-site, /31 w adresacji.
- **Segmentacja logiczna zawsze** — niezależnie od fizycznej topologii dziel sieć wg funkcji i poziomu zaufania (rozdział 21: segmentacja, DMZ, mikrosegmentacja).` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Topologia mówi, **kto z kim jest połączony** — fizycznie (kable, radio) i logicznie (przepływ danych, VLAN-y, sieci Dockera, VPC).
- Klasyczne topologie: magistrala, pierścień, gwiazda, drzewo, siatka pełna i częściowa, punkt-punkt, hub-and-spoke; prawdziwe sieci są hybrydowe.
- Liczba łączy: gwiazda n, pierścień n, drzewo (węzły − 1), siatka pełna **n(n − 1)/2**.
- Analiza awarii: usuwaj po jednym elemencie i szukaj SPOF-ów — także w zasilaniu, strefach, DNS i load balancerach.
- Zasięg: PAN, LAN, WLAN, CAN, MAN, WAN, SAN; komunikacja: klient-serwer lub peer-to-peer.
- Data center: model trójwarstwowy (north-south) vs spine-leaf (east-west, S × L łączy, zawsze leaf → spine → leaf, nadsubskrypcja = w dół / w górę).
- Dla bezpieczeństwa topologia logiczna = **promień rażenia**: segmentuj sieci wg funkcji, a dane trzymaj w sieciach wewnętrznych.
- Następna lekcja: **model OSI w praktyce** — warstwy, przez które przechodzi każdy pakiet w tych topologiach.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const diagrams = [
  ['Pięć komputerów jest podłączonych kablami do jednego przełącznika, przełączniki nie są połączone z niczym innym.', 'gwiazda', 'awaria przełącznika odcina wszystkich; awaria jednego kabla odcina tylko jeden komputer'],
  ['Cztery routery: każdy ma bezpośrednie łącze z każdym z pozostałych.', 'siatka pełna (6 łączy)', 'awaria jednego łącza nic nie odcina — ruch idzie przez inny router; awaria routera odcina tylko jego sieć'],
  ['Sześć węzłów, każdy połączony z dwoma sąsiadami, ostatni z pierwszym.', 'pierścień', 'jedna przerwa: ruch idzie „dookoła” (jeśli pierścień jest dwukierunkowy); dwie przerwy dzielą sieć na dwie części'],
  ['Przełącznik w serwerowni połączony z trzema przełącznikami pięter, do każdego z nich podłączonych 20 komputerów.', 'drzewo (rozszerzona gwiazda)', 'awaria przełącznika piętra odcina to piętro; awaria przełącznika w serwerowni rozdziela piętra od siebie i od serwerów'],
  ['Centrala i 7 oddziałów; każdy oddział ma tunel VPN tylko do centrali.', 'hub-and-spoke', 'awaria koncentratora VPN w centrali odcina wszystkie oddziały od centrali i od siebie nawzajem'],
  ['Stary kabel koncentryczny z terminatorami na końcach, do którego wpięto 8 komputerów.', 'magistrala', 'przerwanie kabla lub brak terminatora zatrzymuje całą sieć; wszyscy dzielą pasmo i domenę kolizyjną'],
  ['4 przełączniki spine i 10 przełączników leaf, każdy leaf połączony z każdym spine.', 'spine-leaf (sieć Clos)', 'awaria jednego spine odbiera 1/4 przepustowości, ale nie łączność; awaria leafa odcina tylko jego serwery (chyba że mają podwójne podłączenie)'],
  ['Pięć routerów: R1 połączony z R2, R3 i R4; R2 z R3; R5 tylko z R4.', 'siatka częściowa (hybryda)', 'R4 jest SPOF-em dla R5; awaria łącza R1–R2 nie odcina nikogo (R2 osiągalny przez R3)'],
];
for (const [d, topo, fail] of diagrams) {
  ex(`Rozpoznaj topologię i oceń skutki awarii: ${d}`, 'Narysuj węzły i łącza, potem usuń w myślach jeden element naraz.', `Topologia: ${topo}.\nSkutki awarii: ${fail}.`);
}
for (const n of [4, 6, 8, 12, 20, 40]) {
  ex(`Ile łączy potrzeba do zbudowania siatki pełnej z ${n} węzłów? Ile portów zajmie to na każdym węźle?`, 'Każdy węzeł łączy się z n − 1 pozostałymi; każde łącze liczysz raz.',
    `Łącza: n(n − 1)/2 = ${n}·${n - 1}/2 = ${fmt(mesh(n))}.\nPorty na węzeł: n − 1 = ${n - 1}.\nDla porównania gwiazda z ${n} hostami: ${n} łączy i jeden przełącznik z ${n} portami.`);
}
for (const [s, l, dp, ds, up, us] of [[2, 8, 48, 10, 4, 40], [4, 16, 48, 25, 8, 100], [6, 32, 32, 100, 8, 400]]) {
  const down = dp * ds, upb = up * us;
  ex(`Spine-leaf: ${s} spine, ${l} leaf. Każdy leaf ma ${dp} portów po ${ds} Gb/s do serwerów i ${up} portów po ${us} Gb/s do spine. Policz łącza spine–leaf i nadsubskrypcję leafa. Czy liczba portów w górę zgadza się z liczbą spine?`,
    'Łącza = S × L. Nadsubskrypcja = przepustowość do serwerów / przepustowość do spine.',
    `Łącza: ${s} × ${l} = ${s * l}.\nW dół: ${dp} × ${ds} = ${fmt(down)} Gb/s, w górę: ${up} × ${us} = ${fmt(upb)} Gb/s → ${(down / upb).toFixed(2).replace('.', ',')}:1.\n${up === s ? 'Porty w górę = liczba spine: po jednym łączu do każdego spine.' : `Portów w górę (${up}) jest ${up > s ? 'więcej' : 'mniej'} niż spine (${s}) — ${up > s && up % s === 0 ? `po ${up / s} łącza do każdego spine (ECMP)` : 'nie każdy leaf połączy się z każdym spine po równo — popraw projekt'}.`}`);
}
ex('Firma ma 12 VPC w AWS, które muszą się ze sobą komunikować. Porównaj liczbę połączeń przy pełnej siatce peeringów VPC i przy hub-and-spoke z Transit Gateway. Co jeszcze przemawia za hubem?', 'Peering nie jest przechodni: każda para VPC potrzebuje własnego połączenia.',
  `Pełna siatka: 12·11/2 = ${mesh(12)} połączeń peering (i tablice routingu w każdym VPC).\nHub-and-spoke: 12 załączników do Transit Gateway.\nZa hubem: centralna kontrola tras, jedno miejsce na inspekcję ruchu (firewall), łatwe dodawanie VPC. Przeciw: koszt przetwarzania danych w TGW i dodatkowy przeskok.`);
ex('Przypisz zasięg (PAN, LAN, WLAN, CAN, MAN, WAN, SAN): a) słuchawki Bluetooth, b) sieć kampusu uczelni z 12 budynkami, c) połączenie biur w Warszawie i Berlinie, d) macierz dyskowa podłączona Fibre Channel do serwerów baz, e) światłowodowa sieć miejska, f) Wi-Fi w kawiarni.', 'Kryterium to zasięg i przeznaczenie, nie technologia.', 'a) PAN, b) CAN, c) WAN, d) SAN, e) MAN, f) WLAN.');
ex('Wskaż wszystkie SPOF-y: dwa serwery aplikacji za jednym load balancerem, obie maszyny w tej samej strefie dostępności, jedna baza PostgreSQL bez repliki, jeden resolver DNS w VPC, jedno łącze do Internetu przez NAT Gateway w jednej strefie.', 'SPOF to także strefa, DNS i bramy, nie tylko urządzenia.',
  'SPOF-y: load balancer (jeśli to pojedyncza instancja, a nie usługa zarządzana wielostrefowa), strefa dostępności (wszystko w jednej), baza bez repliki, resolver DNS, NAT Gateway w jednej strefie.\nNaprawa: ALB w wielu strefach, serwery w ≥ 2 strefach, replika/Multi-AZ dla bazy, NAT Gateway per strefa, drugi resolver lub usługa zarządzana.');
ex('W Docker Compose masz usługi: web (Nginx), api (Spring Boot), worker, postgres, redis, mailhog. Zaprojektuj sieci logiczne tak, by każda usługa widziała tylko potrzebnych rozmówców. Wypisz sieci i członków.', 'Zacznij od przepływów: kto z kim rozmawia i po co.',
  'Przykład:\nedge: web, api (web → api)\nbackend (internal): api, worker, redis (kolejka/cache)\ndata (internal): api, worker, postgres\nmail (internal): worker, mailhog\nWeb nie widzi bazy ani Redisa; mailhog widzi tylko worker. Porty na hosta tylko dla web (443).');
ex('Wyjaśnij, dlaczego w Kubernetesie bez NetworkPolicy przejęcie jednego poda jest groźniejsze niż w sieci z segmentacją VLAN, i zaproponuj pierwszą politykę do wdrożenia.', 'Model sieci K8s: każdy pod może połączyć się z każdym podem.',
  'Domyślnie klaster to logicznie pełna siatka — przejęty pod może łączyć się z bazą, API innych zespołów i komponentami systemowymi. Pierwsza polityka: domyślne „deny all ingress” (a docelowo także egress) w każdej przestrzeni nazw, potem jawne zezwolenia dla potrzebnych przepływów (np. tylko pody app → postgres:5432).');
ex('Narysuj topologię fizyczną i logiczną swojej sieci domowej oraz labu Docker z tego toru. Zaznacz SPOF-y i miejsca, gdzie fizyczna topologia różni się od logicznej.', 'Fizycznie: router, przełącznik, AP, kable; logicznie: podsieci, sieć gości, bridge’e Dockera.',
  'Przykład: fizycznie gwiazda (router Wi-Fi z 4 portami + AP); logicznie: LAN 192.168.1.0/24, sieć gości (osobna podsieć), na laptopie bridge docker0 172.17.0.0/16 i sieci projektu. SPOF: router (łącze, DHCP, DNS, Wi-Fi w jednym), zasilanie. Różnice: sieć gości to osobna sieć logiczna na tym samym AP; sieci Dockera istnieją tylko w jądrze laptopa.');
ex('Rozbuduj klasę Topology z lekcji o metodę List<String> findSpofs(Map<String, Set<String>> g), która zwraca wszystkie węzły, których usunięcie rozspójnia graf. Przetestuj na topologii drzewa i pierścienia.', 'Wywołaj connectedWithout dla każdego węzła; dla dużych grafów istnieje szybszy algorytm Tarjana (punkty artykulacji).',
  'static List<String> findSpofs(Map<String, Set<String>> g) {\n    return g.keySet().stream().filter(v -> !connectedWithout(g, v)).sorted().toList();\n}\nDrzewo Core–SW1–SW2 z hostami: SPOF-y to Core, SW1, SW2 (każdy przełącznik z dziećmi). Pierścień: brak SPOF-ów węzłowych — usunięcie jednego węzła zostawia ścieżkę.\nZłożoność: O(V·(V+E)); algorytm Tarjana robi to w O(V+E).');
ex('Porównaj w tabeli model trójwarstwowy i spine-leaf pod kątem: typu ruchu, liczby przeskoków serwer–serwer, skalowania, roli STP i sposobu wykorzystania nadmiarowych łączy.', 'Pomyśl, co dzieje się z drugim łączem w drzewie z STP, a co w Clos z ECMP.',
  'Trójwarstwowy: north-south; do 5 przełączników między blokami; skalowanie przez większe przełączniki (scale-up); STP blokuje nadmiarowe łącza L2 (część pasma stoi bezczynnie).\nSpine-leaf: east-west; zawsze 3 (leaf–spine–leaf); skalowanie przez dokładanie spine/leaf (scale-out); zwykle routowany L3 do leafa, bez STP w rdzeniu; ECMP wykorzystuje wszystkie łącza równolegle.');
ex('Zespół chce połączyć 6 oddziałów: albo hub-and-spoke przez centralę w Warszawie, albo pełna siatka tuneli WireGuard. Oceń oba warianty pod kątem liczby tuneli, opóźnienia oddział–oddział, punktu kontroli bezpieczeństwa i SPOF.', '7 lokalizacji z centralą; policz tunele dla obu wariantów.',
  `Hub-and-spoke: 6 tuneli; ruch oddział–oddział przez Warszawę (dłużej); jedno miejsce na firewall i logowanie; SPOF: koncentrator w centrali (rozwiązanie: dwa koncentratory).\nPełna siatka 7 lokalizacji: ${mesh(7)} tuneli; najkrótsza droga; kontrola rozproszona (polityki w każdym węźle lub zarządzane centralnie, np. Tailscale ACL); brak pojedynczego koncentratora, ale więcej kluczy i konfiguracji do utrzymania.`);
ex('W sieci biurowej użytkownicy narzekają, że gdy jeden pracownik pobiera duże pliki przez Wi-Fi, wszyscy w sali mają wolny Internet, choć przewodowi koledzy nie mają problemu. Wyjaśnij to topologią.', 'Medium radiowe a przewodowa gwiazda.',
  'Klienci Wi-Fi dzielą jedno medium radiowe (kanał) punktu dostępowego — logicznie zachowuje się ono jak magistrala: tylko jedna stacja nadaje naraz, a wolni klienci zajmują dużo czasu antenowego. Przewodowi użytkownicy mają osobne łącza do przełącznika (gwiazda) z osobnym pasmem. Rozwiązania: więcej AP na mniejszej mocy, pasmo 5/6 GHz, limity (QoS, airtime fairness), kabel dla stacji o dużym ruchu.');
ex('Zaproponuj topologię logiczną (sieci i ich członków) dla laboratorium SOC z tego toru: Security Onion, Wazuh, kontroler domeny, 3 stacje Windows, serwer Linux z aplikacją Java i atakujący kontener do ćwiczeń purple team. Uzasadnij granice.', 'Oddziel sieć zarządzania narzędziami, sieć „ofiar” i sieć ćwiczeń ofensywnych.',
  'Przykład:\nmgmt: Security Onion (interfejs zarządzania), Wazuh manager, stacja analityka.\ncorp: DC, stacje Windows, serwer Linux z aplikacją — agenci Wazuh wysyłają dane do mgmt przez jeden dozwolony port.\nredteam (izolowana): kontener ćwiczeniowy z dostępem tylko do corp, bez dostępu do mgmt i Internetu.\nmonitor: port lustrzany (SPAN) z corp do sensora Security Onion (interfejs bez adresu IP).\nGranice zapobiegają atakowi na narzędzia SOC i wyjściu ruchu ćwiczeniowego poza lab.');
ex('Dlaczego architekci chmury mówią, że „strefa dostępności to też węzeł topologii”? Podaj przykład awarii, której nie przeżyje aplikacja z dwiema instancjami w jednej strefie.', 'Pomyśl o zasilaniu, sieci i chłodzeniu wspólnych dla całej strefy.',
  'Strefa dostępności to osobne centrum danych (lub ich grupa) ze wspólnym zasilaniem, chłodzeniem i siecią. Awaria zasilania, sieci lub chłodzenia całej strefy wyłącza wszystkie instancje w niej — dwie maszyny w tej samej strefie nie dają odporności na taką awarię. Dlatego instancje, bramy NAT i repliki baz rozkłada się na co najmniej dwie strefy.');
ex('Na podstawie wyniku polecenia „docker network inspect” (lista kontenerów w sieciach app_frontend: proxy, app; app_backend: app, db, redis, debug-shell) oceń, czy topologia jest bezpieczna. Co byś zmienił?', 'Szukaj kontenerów, które nie powinny być w danym segmencie.',
  'Problem: kontener debug-shell w sieci backend ma bezpośredni dostęp do bazy i Redisa — to furtka (często pozostawiona po diagnostyce). Zmiany: usunąć debug-shell lub uruchamiać go tylko doraźnie (docker compose run --rm) i usuwać po użyciu; rozważyć oddzielenie sieci danych (db, redis) od sieci aplikacji; sprawdzić, czy backend ma internal: true i czy db/redis nie publikują portów.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(20261113);
add('Co opisuje topologia fizyczna sieci?', 'Rzeczywiste połączenia kablowe i radiowe między urządzeniami', ['Sposób adresowania hostów', 'Kolejność warstw modelu OSI', 'Listę protokołów aplikacyjnych'], 'Topologia fizyczna to „gdzie biegną kable i fale”, logiczna — jak płyną dane i kto kogo widzi.');
add('Co opisuje topologia logiczna?', 'Jak płyną dane i które urządzenia mogą się ze sobą komunikować', ['Długość kabli w budynku', 'Rozmieszczenie szaf rack', 'Liczbę portów w przełączniku'], 'Na jednej fizycznej gwieździe może istnieć wiele logicznych sieci (VLAN-y, sieci Dockera).');
add('Która topologia dominuje we współczesnych sieciach Ethernet LAN?', 'Gwiazda (rozszerzona gwiazda) z przełącznikiem w centrum', ['Magistrala', 'Pierścień', 'Siatka pełna'], 'Każdy host ma własny kabel do portu przełącznika; przełączniki łączą się hierarchicznie.');
add('Co jest pojedynczym punktem awarii (SPOF) w topologii gwiazdy?', 'Centralny przełącznik', ['Kabel dowolnego hosta', 'Dowolny host', 'Nie ma SPOF-u'], 'Awaria centralnego urządzenia odcina wszystkich; awaria kabla hosta odcina tylko ten host.');
add('Jaki jest skutek przerwania wspólnego kabla w topologii magistrali?', 'Cała sieć przestaje działać', ['Odcięty jest tylko najbliższy host', 'Ruch idzie drugą stroną', 'Nic się nie dzieje'], 'Wszyscy korzystają z jednego medium; przerwanie (lub brak terminatora) powoduje odbicia sygnału i zatrzymuje transmisję.');
add('Co się dzieje w dwukierunkowym pierścieniu po przerwaniu jednego łącza?', 'Ruch może zostać przekierowany w drugą stronę pierścienia', ['Cała sieć przestaje działać', 'Pierścień zamienia się w gwiazdę', 'Wszystkie węzły tracą adresy'], 'Pierścień z ochroną przeżywa jedną przerwę; druga dzieli go na dwie części.');
add('Jak nazywa się topologia, w której każdy węzeł ma bezpośrednie łącze z każdym innym?', 'Siatka pełna (full mesh)', ['Siatka częściowa', 'Drzewo', 'Hub-and-spoke'], 'Najwyższa odporność, ale liczba łączy rośnie kwadratowo: n(n − 1)/2.');
add('Która topologia opisuje oddziały połączone tylko z centralą?', 'Hub-and-spoke', ['Siatka pełna', 'Pierścień', 'Magistrala'], 'Ruch oddział–oddział przechodzi przez hub w centrali; typowe dla VPN i Transit Gateway.');
add('Dlaczego dawny hub Ethernet był logicznie magistralą, mimo że fizycznie tworzył gwiazdę?', 'Powtarzał każdą ramkę na wszystkie porty, tworząc jedną domenę kolizyjną', ['Bo miał jeden port', 'Bo łączył się w pierścień', 'Bo używał kabla koncentrycznego'], 'Przełącznik uczy się adresów MAC i wysyła ramkę tylko na właściwy port, dlatego tworzy logiczną gwiazdę.');
add('Jakie ryzyko bezpieczeństwa wynika z użycia huba zamiast przełącznika?', 'Każdy podłączony host może podsłuchać ruch innych', ['Hub szyfruje ruch słabym algorytmem', 'Hub blokuje protokół ARP', 'Hub zmienia adresy IP'], 'Ponieważ każda ramka trafia na wszystkie porty, karta w trybie promiscuous widzi cały ruch segmentu.');
add('Która architektura data center jest zoptymalizowana pod ruch east-west?', 'Spine-leaf', ['Trójwarstwowa core/distribution/access', 'Magistrala', 'Hub-and-spoke'], 'Każdy leaf łączy się z każdym spine, więc ruch serwer–serwer ma stałą, krótką ścieżkę.');
add('Ile przełączników przechodzi ruch między serwerami podłączonymi do różnych leafów w spine-leaf?', '3 (leaf → spine → leaf)', ['1', '2', '5'], 'To przewidywalność opóźnienia, której brakuje w modelu trójwarstwowym.');
add('Co oznacza ruch north-south?', 'Ruch między klientami spoza data center a serwerami wewnątrz', ['Ruch między serwerami w data center', 'Ruch zarządzający przełącznikami', 'Ruch między dwiema strefami czasowymi'], 'East-west to ruch serwis–serwis wewnątrz DC.');
add('Jaka jest rola warstwy access w modelu trójwarstwowym?', 'Podłączenie urządzeń końcowych (komputerów, serwerów, AP)', ['Szybki szkielet całej sieci', 'Połączenie z Internetem', 'Przechowywanie danych'], 'Distribution agreguje i stosuje polityki, core zapewnia szybki szkielet.');
add('Która warstwa modelu trójwarstwowego zwykle realizuje routing między VLAN-ami i polityki?', 'Distribution', ['Access', 'Core', 'Żadna'], 'Core ma być możliwie prosty i szybki; polityki stosuje się w distribution.');
add('Co oznacza nadsubskrypcja 3:1 na przełączniku leaf?', 'Przepustowość do serwerów jest trzy razy większa niż do spine', ['Na każdy serwer przypadają trzy przełączniki', 'Trzy spine na jeden leaf', 'Trzy VLAN-y na port'], 'Przy pełnym obciążeniu wszystkich serwerów ruch w górę zostanie ograniczony do 1/3.');
add('Co daje ECMP w sieci spine-leaf?', 'Równoległe wykorzystanie wielu ścieżek o równym koszcie przez różne spine', ['Szyfrowanie ruchu między leafami', 'Automatyczne przydzielanie adresów IP', 'Blokowanie pętli jak STP'], 'Equal-Cost Multi-Path rozkłada przepływy na wszystkie spine.');
add('Jaki skutek ma awaria jednego z czterech spine?', 'Utrata około 1/4 przepustowości między leafami, ale bez utraty łączności', ['Brak łączności całego DC', 'Odcięcie jednego leafa', 'Brak skutków dla przepustowości'], 'Pozostałe spine nadal łączą każdy leaf z każdym.');
add('Jak nazywa się sieć łącząca kilka budynków jednej organizacji, np. uczelni?', 'CAN', ['PAN', 'MAN', 'SAN'], 'Campus Area Network.');
add('Jak nazywa się sieć dedykowana pamięci masowej, np. Fibre Channel lub iSCSI?', 'SAN', ['CAN', 'WAN', 'WLAN'], 'Storage Area Network oddziela ruch dyskowy od zwykłego LAN.');
add('Do jakiej kategorii należą słuchawki Bluetooth połączone z telefonem?', 'PAN', ['LAN', 'MAN', 'CAN'], 'Personal Area Network ma zasięg kilku metrów wokół osoby.');
add('Połączenie biur firmy w Polsce i Niemczech to przykład sieci:', 'WAN', ['MAN', 'CAN', 'PAN'], 'Wide Area Network obejmuje kraje i kontynenty.');
add('Światłowodowa sieć obejmująca obszar miasta to:', 'MAN', ['CAN', 'SAN', 'PAN'], 'Metropolitan Area Network.');
add('Czym różni się model peer-to-peer od klient-serwer?', 'W P2P węzły są równorzędne i mogą jednocześnie udostępniać i pobierać zasoby', ['P2P wymaga centralnego serwera', 'P2P działa tylko w LAN', 'W P2P nie używa się IP'], 'BitTorrent, WebRTC, protokoły gossip w klastrach to przykłady P2P.');
add('Jaki model sieci zakłada Kubernetes dla podów (bez NetworkPolicy)?', 'Każdy pod może komunikować się z każdym podem bez NAT', ['Każdy pod jest izolowany domyślnie', 'Pody komunikują się tylko przez Ingress', 'Pody widzą tylko pody z tego samego węzła'], 'Logicznie pełna siatka — dlatego NetworkPolicy z domyślnym deny jest podstawą bezpieczeństwa.');
add('Czym w topologii jest sieć Dockera typu bridge?', 'Wirtualnym przełącznikiem tworzącym logiczną gwiazdę kontenerów', ['Fizycznym kablem', 'Routerem brzegowym', 'Serwerem DNS w Internecie'], 'Każdy kontener ma interfejs veth podłączony do bridge’a na hoście.');
add('Co robi opcja internal: true w definicji sieci Docker Compose?', 'Odcina sieci trasę na zewnątrz, więc kontenery w niej nie wyjdą do Internetu', ['Szyfruje ruch w sieci', 'Udostępnia sieć na hoście', 'Włącza IPv6'], 'Przydatne dla baz danych i usług wewnętrznych — mniejszy promień rażenia i brak eksfiltracji bezpośredniej.');
add('Dlaczego w AWS przy wielu VPC stosuje się Transit Gateway zamiast samych peeringów?', 'Peering nie jest przechodni, a pełna siatka peeringów rośnie kwadratowo', ['Peering nie działa w jednym regionie', 'Transit Gateway jest darmowy', 'Peering wymaga IPv6'], 'TGW tworzy hub-and-spoke z centralnym routingiem i miejscem na inspekcję.');
add('Który element NIE jest zwykle traktowany jako potencjalny SPOF, ale powinien być?', 'Jedyny resolver DNS używany przez aplikacje', ['Drugi serwer aplikacji', 'Zapasowy zasilacz', 'Replika bazy w innej strefie'], 'Awaria DNS zatrzymuje wszystko, co rozwiązuje nazwy — także „wysoko dostępne” aplikacje.');
add('Co stanie się po dodaniu drugiego kabla między dwoma przełącznikami bez STP?', 'Powstanie pętla warstwy 2 i burza rozgłoszeniowa', ['Przepustowość podwoi się automatycznie', 'Nic — przełączniki zignorują drugi kabel', 'Przełączniki zsynchronizują tablice ARP'], 'Ramki rozgłoszeniowe krążą w nieskończoność; do agregacji łączy służy LACP, a do blokowania pętli STP.');
add('Dlaczego Wi-Fi, choć fizycznie przypomina gwiazdę, zachowuje się częściowo jak magistrala?', 'Medium radiowe jest współdzielone przez wszystkich klientów punktu dostępowego', ['Bo AP nie ma adresu MAC', 'Bo Wi-Fi nie używa IP', 'Bo klienci łączą się w pierścień'], 'Klienci dzielą kanał; wolny lub daleki klient zajmuje czas antenowy wszystkich.');
add('Jaka topologia jest typowa dla klasycznego VPN z koncentratorem w centrali?', 'Hub-and-spoke', ['Pierścień', 'Magistrala', 'Spine-leaf'], 'Wszyscy łączą się z koncentratorem; alternatywą jest siatka (np. WireGuard mesh).');
add('Jakie jest główne zastosowanie topologii pierścienia we współczesnych sieciach?', 'Sieci operatorskie i przemysłowe z szybką ochroną pierścienia', ['Domowe sieci Wi-Fi', 'Połączenia Bluetooth', 'Sieci Dockera'], 'Protokoły takie jak ERPS przełączają ruch po awarii w ułamku sekundy.');
add('Dlaczego płaska sieć jest groźna z punktu widzenia obrońcy?', 'Przejęcie jednego hosta daje możliwość bezpośredniej komunikacji ze wszystkimi innymi', ['Bo płaskie sieci są wolniejsze', 'Bo nie da się w nich używać DHCP', 'Bo wymagają IPv6'], 'Segmentacja ogranicza ruch boczny i promień rażenia incydentu.');
add('Co to jest promień rażenia (blast radius) w kontekście topologii?', 'Zakres systemów, które może dotknąć awaria lub kompromitacja jednego elementu', ['Zasięg sygnału Wi-Fi', 'Długość kabla w metrach', 'Liczba hostów w podsieci /24'], 'Topologia logiczna i segmentacja bezpośrednio go ograniczają.');
add('Która topologia ma najwyższy koszt okablowania przy tej samej liczbie węzłów?', 'Siatka pełna', ['Gwiazda', 'Pierścień', 'Magistrala'], 'n(n − 1)/2 łączy wobec n w gwieździe lub pierścieniu.');
add('Hybryda gwiazdy z kilkoma nadmiarowymi łączami między przełącznikami rdzeniowymi to przykład:', 'Topologii hybrydowej z siatką częściową w rdzeniu', ['Czystej magistrali', 'Pierścienia tokenowego', 'Sieci PAN'], 'Większość prawdziwych sieci łączy kilka topologii.');
add('W topologii hub-and-spoke ruch między dwoma oddziałami:', 'Przechodzi przez hub w centrali', ['Zawsze idzie bezpośrednio', 'Jest blokowany', 'Idzie przez Internet bez szyfrowania'], 'To dodatkowe opóźnienie, ale też jedno miejsce kontroli i inspekcji.');
add('Który zapis portów w Compose wystawia bazę wyłącznie na pętlę zwrotną hosta?', '127.0.0.1:5432:5432', ['5432:5432', '0.0.0.0:5432:5432', '5432'], 'Bez adresu Docker publikuje port na wszystkich interfejsach hosta.');
add('Co stanie się z ruchem w pierścieniu jednokierunkowym (bez ochrony) po przerwaniu jednego łącza?', 'Część węzłów straci możliwość komunikacji', ['Ruch automatycznie zawróci', 'Nic się nie stanie', 'Pierścień stanie się siatką'], 'Dlatego stosowano podwójne pierścienie (FDDI) lub protokoły ochrony.');

// generowane: siatka pelna
for (const n of [3, 5, 7, 9, 11, 15, 25, 30, 60, 100]) {
  add(`Ile łączy ma siatka pełna z ${n} węzłów?`, fmt(mesh(n)), [fmt(n * (n - 1)), fmt(n), fmt(mesh(n + 1)), fmt(n * n), fmt(mesh(n - 1)), fmt(n - 1)], `n(n − 1)/2 = ${n}·${n - 1}/2 = ${fmt(mesh(n))}. Każde łącze łączy dwa węzły, więc dzielimy przez 2.`);
}
for (const n of [5, 9, 13, 24, 48]) {
  add(`Ile portów sieciowych potrzebuje każdy węzeł w siatce pełnej z ${n} węzłów?`, String(n - 1), [String(n), String(mesh(n)), String(n + 1), String(n - 2)], `Każdy węzeł łączy się ze wszystkimi pozostałymi: n − 1 = ${n - 1}.`);
}
for (const n of [6, 10, 24, 48]) {
  add(`Ile łączy kablowych potrzeba w gwieździe z ${n} hostami i jednym przełącznikiem?`, String(n), [String(n - 1), String(mesh(n)), String(2 * n), String(n + 1)], 'Każdy host ma jeden kabel do przełącznika.');
}
for (const [s, l] of [[2, 6], [4, 12], [4, 20], [6, 24], [8, 32]]) {
  add(`Ile łączy spine–leaf ma sieć z ${s} spine i ${l} leaf (po jednym łączu między każdą parą)?`, String(s * l), [String(s + l), String(mesh(s + l)), String(s * l * 2), String(s * l - s)], `Każdy z ${l} leafów łączy się z każdym z ${s} spine: ${s} × ${l} = ${s * l}.`);
}
for (const [dp, ds, up, us] of [[48, 10, 4, 40], [48, 25, 6, 100], [32, 100, 8, 400], [48, 10, 2, 40], [24, 25, 4, 100], [48, 25, 4, 100]]) {
  const r = (dp * ds) / (up * us);
  const fr = (x) => `${Number.isInteger(x) ? x : x.toFixed(1).replace('.', ',')}:1`;
  add(`Leaf ma ${dp} portów ${ds} Gb/s do serwerów i ${up} porty ${us} Gb/s do spine. Jaka jest nadsubskrypcja?`, fr(r), [fr(r * 2), fr(r + 1), fr(r + 0.5), fr(r * 4)],
    `W dół ${dp}·${ds} = ${fmt(dp * ds)} Gb/s, w górę ${up}·${us} = ${fmt(up * us)} Gb/s, stosunek ${fr(r)}.`);
}
for (const n of [5, 8, 20]) {
  add(`Ile łączy ma topologia hub-and-spoke z centralą i ${n} oddziałami?`, String(n), [String(mesh(n + 1)), String(n + 1), String(2 * n), String(n - 1)], `Każdy oddział ma jedno łącze do huba: ${n}. Pełna siatka ${n + 1} lokalizacji wymagałaby ${mesh(n + 1)} łączy.`);
}
for (const n of [12, 31]) {
  add(`Ile łączy ma topologia drzewa (bez nadmiarowości) obejmująca ${n} węzłów łącznie z przełącznikami?`, String(n - 1), [String(n), String(mesh(n)), String(n + 1), String(n - 2)], 'Drzewo to graf spójny bez cykli: liczba krawędzi = liczba węzłów − 1.');
}
const scen = [
  ['Smartwatch zsynchronizowany z telefonem przez Bluetooth', 'PAN'], ['Sieć przewodowa w biurze na jednym piętrze', 'LAN'], ['Wi-Fi w hotelu', 'WLAN'],
  ['Sieć fabryki z halami produkcyjnymi i biurowcem na jednym terenie', 'CAN'], ['Łącza między centrami danych w Europie i USA', 'WAN'], ['Macierz All-Flash połączona z serwerami przez NVMe over Fabrics', 'SAN'],
];
const kinds = ['PAN', 'LAN', 'WLAN', 'CAN', 'MAN', 'WAN', 'SAN'];
for (const [d, k] of scen) {
  const wr = kinds.filter((x) => x !== k).slice(0, 3);
  add(`Do której kategorii zasięgu należy: ${d}?`, k, wr, `Odpowiedź: ${k}. Kategorie: PAN (osobista), LAN/WLAN (lokalna), CAN (kampus), MAN (miasto), WAN (rozległa), SAN (pamięć masowa).`);
}
const tops = [
  ['Ruch krąży od węzła do węzła w zamkniętej pętli.', 'Pierścień', ['Gwiazda', 'Magistrala', 'Drzewo']],
  ['Każde urządzenie ma osobny kabel do centralnego przełącznika.', 'Gwiazda', ['Pierścień', 'Siatka pełna', 'Magistrala']],
  ['Wszystkie urządzenia dzielą jeden przewód zakończony terminatorami.', 'Magistrala', ['Gwiazda', 'Pierścień', 'Hub-and-spoke']],
  ['Przełączniki pięter połączone z przełącznikiem budynku, a ten z przełącznikiem kampusu.', 'Drzewo (rozszerzona gwiazda)', ['Siatka pełna', 'Magistrala', 'Pierścień']],
  ['Tylko wybrane routery mają dodatkowe łącza zapasowe.', 'Siatka częściowa', ['Siatka pełna', 'Magistrala', 'Gwiazda']],
  ['Dwa routery połączone jednym dedykowanym łączem.', 'Punkt-punkt', ['Magistrala', 'Pierścień', 'Hub-and-spoke']],
];
for (const [d, k, w] of tops) add(`Jaką topologię opisuje zdanie: „${d}”?`, k, w, `To ${k.toLowerCase()}.`);
for (const [d, k, w, e] of [
  ['Leaf z serwerami bazy danych ma tylko jedno łącze do spine.', 'To łącze i ten spine dla tego leafa', ['Wszystkie spine', 'Żaden element', 'Serwery na innych leafach'], 'Bez drugiego łącza do innego spine leaf traci łączność po awarii jedynej ścieżki.'],
  ['Trzy serwery aplikacji w trzech strefach, ale wszystkie łączą się z jedną bazą bez repliki.', 'Baza danych', ['Serwery aplikacji', 'Strefy dostępności', 'Load balancer wielostrefowy'], 'Nadmiarowa warstwa aplikacji nie pomoże, gdy wspólna zależność jest pojedyncza.'],
  ['Dwa przełączniki core, ale oba zasilane z tego samego UPS.', 'UPS (zasilanie)', ['Przełączniki core', 'Kable między przełącznikami', 'Porty access'], 'SPOF może leżeć poza siecią — w zasilaniu, chłodzeniu, strefie.'],
]) add(`Co jest SPOF-em w scenariuszu: ${d}`, k, w, e);
add('Czym jest service mesh (np. Istio, Linkerd) z punktu widzenia topologii?', 'Logiczną siatką połączeń między usługami realizowaną przez proxy (sidecary), niezależną od fizycznych kabli', ['Fizyczną siatką pełną kabli między serwerami', 'Rodzajem przełącznika spine', 'Siecią SAN dla kontenerów'], 'Proxy przy każdej usłudze zestawiają połączenia (często mTLS) — to topologia logiczna.');
add('Dlaczego replikacja PostgreSQL lider–repliki przypomina gwiazdę?', 'Wszystkie repliki pobierają zmiany z jednego lidera, który jest centralnym węzłem', ['Bo repliki tworzą pierścień', 'Bo każdy węzeł zapisuje do każdego', 'Bo używa magistrali SCSI'], 'Awaria lidera wymaga przełączenia (failover) — to SPOF bez automatyzacji.');
add('Jaką topologię logiczną tworzą węzły Cassandry w klasycznym consistent hashing?', 'Pierścień tokenów', ['Gwiazdę z liderem', 'Magistralę', 'Drzewo'], 'Każdy węzeł odpowiada za zakres tokenów na pierścieniu; dane są replikowane na kolejnych węzłach.');
add('Co najlepiej ogranicza ruch boczny napastnika w sieci firmowej?', 'Segmentacja logiczna (VLAN-y, strefy, mikrosegmentacja) z kontrolą ruchu między segmentami', ['Zamiana gwiazdy na pierścień', 'Większa przepustowość łączy', 'Usunięcie routera brzegowego'], 'Topologia logiczna z politykami ruchu wyznacza promień rażenia.');
add('Który element jest SPOF-em w biurze z jednym routerem i dwoma przełącznikami połączonymi z tym routerem?', 'Router', ['Każdy z przełączników osobno odcina całe biuro', 'Kabel dowolnego komputera', 'Żaden element'], 'Awaria routera odcina oba przełączniki od Internetu i od siebie (jeśli ruch między nimi idzie przez router).');
add('W jaki sposób protokół VRRP zmienia analizę SPOF bramy domyślnej?', 'Dwa routery dzielą wirtualny adres bramy, więc awaria jednego nie odcina hostów', ['Łączy routery w pierścień', 'Zamienia router w przełącznik', 'Wyłącza routing statyczny'], 'Szczegóły w lekcji o redundancji bramy (FHRP) w rozdziale 03.');
add('Jaki kompromis występuje przy wyborze hub-and-spoke zamiast pełnej siatki dla oddziałów?', 'Mniej połączeń i centralna kontrola kosztem dłuższej drogi oddział–oddział i SPOF w hubie', ['Większa liczba tuneli i brak kontroli', 'Brak możliwości szyfrowania', 'Konieczność użycia magistrali'], 'Hub warto zdublować i umieścić blisko większości ruchu.');
add('Dlaczego zwiększenie liczby spine poprawia odporność sieci spine-leaf?', 'Awaria jednego spine odbiera mniejszy procent przepustowości', ['Bo spine przechowują dane', 'Bo leafy mogą wtedy łączyć się ze sobą bezpośrednio', 'Bo zmniejsza liczbę serwerów'], 'Przy 2 spine awaria zabiera 50%, przy 8 — 12,5% pojemności rdzenia.');
add('Który zapis najlepiej opisuje topologię hybrydową?', 'Gwiazdy na piętrach połączone siatką częściową w rdzeniu i hub-and-spoke do oddziałów', ['Jeden kabel koncentryczny', 'Pojedyncze łącze punkt-punkt', 'Jedna sieć PAN'], 'Prawie każda prawdziwa sieć firmowa łączy kilka topologii.');
add('Dlaczego dokumentacja topologii jest ważna dla zespołu reagowania na incydenty?', 'Pozwala szybko ocenić, dokąd mógł dotrzeć napastnik i gdzie odizolować segment', ['Bo zastępuje logi', 'Bo wymaga jej protokół TCP', 'Bo bez niej nie działa DHCP'], 'Aktualny diagram logiczny skraca czas powstrzymania incydentu (containment).');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
