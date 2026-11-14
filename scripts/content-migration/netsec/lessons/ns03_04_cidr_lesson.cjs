// Generuje lekcje _ns_03_ip_i_routing/04_CidrAndSubnetting.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_04_cidr_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/04_CidrAndSubnetting.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const M = require('./ns03_04_cidr_model.cjs');
const buildQuiz = require('./ns03_04_cidr_quiz.cjs');
const { parse, split, vlsm, summarize, vpcPlan, prefixForHosts, maskOf, wildcardOf, size } = M;

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const fmt = (n) => n.toLocaleString('pl-PL');
const P = parse('10.20.37.200/19');
const OFFICE = [{ name: 'Biuro', hosts: 100 }, { name: 'Magazyn', hosts: 50 }, { name: 'Serwery', hosts: 20 }, { name: 'Zarządzanie', hosts: 10 }, { name: 'Łącze P2P', hosts: 2 }];
const V = vlsm('192.168.10.0/24', OFFICE);
const VPC = vpcPlan();
const S1 = summarize(['10.1.4.0/24', '10.1.5.0/24', '10.1.6.0/24', '10.1.7.0/24']);
const S2 = summarize(['10.1.4.0/24', '10.1.9.0/24']);

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Od „jednej sieci” do planu adresacji całej firmy i chmury', ...img('ns03-04-split.svg', 'Sieć 10.20.0.0/16 dzielona kolejno na dwie /17, cztery /18, osiem /19 i szesnaście /20; każdy dodatkowy bit prefiksu podwaja liczbę podsieci i zmniejsza o połowę ich rozmiar. Granice podsieci są wielokrotnościami ich rozmiaru.'),
    body:
`W poprzedniej lekcji maska odpowiadała na jedno pytanie: „czy ten adres jest w mojej sieci?”. Teraz robimy krok dalej — **projektujemy adresację**: bierzemy duży blok (np. \`10.20.0.0/16\` dla VPC w AWS albo \`192.168.10.0/24\` dla oddziału) i dzielimy go na podsieci dla działów, warstw aplikacji i stref dostępności tak, żeby:
- każda podsieć miała **wystarczająco dużo adresów** (z zapasem na wzrost i na adresy rezerwowane przez platformę),
- podsieci **się nie nakładały** — ani ze sobą, ani z sieciami, z którymi kiedyś się połączysz (VPN, peering, Docker, Kubernetes),
- trasy dało się **agregować** (jedna trasa zamiast dziesięciu),
- ACL, security groups i reguły firewalla dało się pisać **prefiksami**, a nie listami adresów.

To umiejętność, którą sprawdza każdy egzamin sieciowy (Network+ N10-009 domena 1: subnetting, CIDR, VLSM; CCNA 200-301 v2: konfiguracja i weryfikacja adresacji IPv4 i podsieci) i którą Java/DevOps developer wykorzystuje w praktyce częściej, niż się wydaje: przy zakładaniu VPC w Terraformie, konfigurowaniu sieci Dockera, planowaniu klastra Kubernetes (zakres podów i serwisów) czy przy diagnozie „po podłączeniu VPN nie działa mi Docker”.

W tej lekcji: notacja CIDR, maska i **wildcard**, adres sieci/broadcast/zakres w jednym przebiegu, podział na równe podsieci, **VLSM** (podsieci różnej wielkości), **agregacja tras** (supernetting), nakładające się zakresy i — zadanie z planu — **projekt VPC 10.20.0.0/16 dla 3 stref dostępności** (public / private / db).` },

  { type: 'DEFINITION', heading: 'Słownik CIDR i podsieci', body:
`- **CIDR (Classless Inter-Domain Routing)** — zapis \`adres/prefiks\`, np. \`10.20.0.0/16\`: pierwsze 16 bitów to sieć, reszta to hosty. Klasy nie mają znaczenia (RFC 4632).
- **Prefiks /N** — liczba jedynek w masce; rozmiar podsieci = **2^(32−N)** adresów.
- **Maska** — prefiks zapisany jak adres: /19 = ${maskOf(19)}.
- **Wildcard (maska odwrotna)** — negacja maski: /19 → ${wildcardOf(19)}; używana w ACL Cisco i w OSPF (\`network 10.20.0.0 0.0.31.255 area 0\`). Liczy się ją jako **255.255.255.255 − maska**.
- **Adres sieci** — wszystkie bity hosta = 0; **broadcast** — wszystkie bity hosta = 1; **zakres hostów** — od sieci+1 do broadcast−1; **liczba hostów** = 2^(32−N) − 2 (wyjątki: /31 na łączach punkt-punkt — RFC 3021 — 2 adresy bez sieci i broadcastu; /32 — pojedynczy adres).
- **Podsieciowanie (subnetting)** — wydłużenie prefiksu: z /16 przez „pożyczenie” k bitów powstaje 2^k podsieci /(16+k).
- **VLSM (Variable Length Subnet Mask)** — podsieci różnej długości w jednym bloku, dopasowane do potrzeb (biuro /25, łącze P2P /30).
- **Agregacja / supernetting / summarization** — skrócenie prefiksu: kilka sąsiednich, wyrównanych sieci ogłasza się jedną trasą (4 × /24 → /22).
- **Wyrównanie (alignment)** — podsieć /N może zaczynać się tylko od adresu podzielnego przez jej rozmiar: /20 od 10.20.0.0, .16.0, .32.0…, nigdy od 10.20.8.0.
- **Nakładanie się (overlap)** — dwa zakresy mają wspólne adresy; w routingu wygrywa **najdłuższy prefiks** (longest prefix match), więc węższa sieć „kradnie” ruch szerszej.
- **Adresy rezerwowane przez chmurę** — AWS rezerwuje **5 adresów** w każdej podsieci (sieć, router VPC .1, DNS .2, przyszłe użycie .3, broadcast); Azure też 5, GCP 4.` },

  { type: 'ANALOGY', heading: 'Działka budowlana i podział na parcele', body:
`Blok \`10.20.0.0/16\` to **działka** o powierzchni 65 536 m². Prefiks to linijka geodety:
- **Podział na równe parcele** (subnetting) — przecinasz działkę na pół (/17), potem każdą połowę na pół (/18)… Każde cięcie podwaja liczbę parceli i zmniejsza o połowę ich powierzchnię. Nie da się wyciąć parceli „od połowy innej parceli” — granice muszą wypadać na liniach podziału (**wyrównanie**).
- **VLSM** — na działce stawiasz halę magazynową (/19), biurowiec (/22) i kiosk (/30). Najpierw rozmierzasz największe budynki, bo wymagają dużych, równych kawałków; małe wciśniesz potem w wolne rogi. Gdybyś zaczął od kiosków, hala mogłaby już się nigdzie nie zmieścić w jednym kawałku.
- **Agregacja** — dla poczty całe osiedle ma jeden kod pocztowy (/22), a nie osobny dla każdego bloku (/24). Ale jeśli osiedle ma „dziurę” (blok, który nie jest Twój), jeden kod dla całości oznaczałby, że listy do cudzego budynku trafią do Ciebie.
- **Nakładające się zakresy** — dwóch geodetów wydzieliło tę samą parcelę dwóm właścicielom. Dopóki nie spotkają się przy płocie (VPN, peering), problemu nie widać; potem — spór, którego nie da się rozwiązać bez przemeldowania jednego z nich (renumeracja).
- **Rezerwy chmury** — gmina zabiera z każdej parceli kawałek na skrzynkę, hydrant i przyłącze (5 adresów AWS). Na dużej parceli to nic, na maleńkiej (/28 = 16 adresów, w AWS 11 użytecznych) — sporo.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Jeden adres, wszystkie parametry: 10.20.37.200/19', body:
`Przykład liczony krok po kroku metodą „magicznej liczby” (z poprzedniej lekcji) dla prefiksu, który nie kończy się na granicy oktetu. /19 = 8 + 8 + 3 bity → **interesujący jest trzeci oktet**, w którym maska ma 3 jedynki: 11100000 = 224. Magiczna liczba = 256 − 224 = **32** → podsieci w trzecim oktecie zaczynają się co 32: 0, 32, 64, 96… Adres .37 leży w bloku 32–63.`,
    code:
`adres          10.20.37.200/19
maska          ${P.mask}          (/19 = 11111111.11111111.11100000.00000000)
wildcard       ${P.wildcard}            (255.255.255.255 − maska)
rozmiar        2^(32−19) = ${fmt(P.size)} adresów
sieć           ${P.network}            (37 → w dół do wielokrotności 32 = 32)
broadcast      ${P.broadcast}          (32 + 32 − 1 = 63, ostatni oktet 255)
pierwszy host  ${P.first}
ostatni host   ${P.last}
liczba hostów  ${fmt(P.hosts)}               (w AWS: ${fmt(P.size - 5)})
zapis CIDR     ${P.cidr}

Kontrola w terminalu:   ipcalc 10.20.37.200/19     (Debian/Ubuntu: apt install ipcalc)
                        python3 -c "import ipaddress as i; n=i.ip_network('10.20.37.200/19', strict=False); print(n, n.netmask, n.hostmask, n.num_addresses)"` },

  { type: 'CODE_BASIC', heading: 'Podział na równe podsieci: ile bitów pożyczyć', body:
`Pytanie egzaminacyjne i projektowe: „podziel blok na N podsieci” albo „na podsieci po co najmniej H hostów”. Liczba pożyczonych bitów k to najmniejsze k, dla którego 2^k ≥ N; prefiks podsieci = stary prefiks + k. Albo od strony hostów: najmniejszy prefiks, dla którego 2^(32−p) − 2 ≥ H.`,
    code:
`Podział 10.20.0.0/16 na 4 podsieci → k = 2 (2² = 4) → /18:
${split('10.20.0.0/16', 18).map((c) => `  ${c.padEnd(16)} hosty ${parse(c).first} – ${parse(c).last}`).join('\n')}

Podział 172.16.8.0/22 na 8 podsieci → k = 3 → /25:
${split('172.16.8.0/22', 25).map((c) => `  ${c.padEnd(17)} broadcast ${parse(c).broadcast}`).join('\n')}

Najmniejsza podsieć na H hostów (klasycznie −2; w AWS −5):
${[10, 30, 60, 200, 500, 1000, 4000].map((h) => `  ${String(h).padStart(5)} hostów → /${prefixForHosts(h)} (${fmt(size(prefixForHosts(h)) - 2)} hostów)   w AWS → /${prefixForHosts(h, 5)} (${fmt(size(prefixForHosts(h, 5)) - 5)})`).join('\n')}` },

  { type: 'STEP_BY_STEP', heading: 'VLSM krok po kroku: oddział w jednym /24', ...img('ns03-04-vlsm.svg', 'Podział 192.168.10.0/24 metodą VLSM: Biuro 100 hostów → /25 (.0–.127), Magazyn 50 → /26 (.128), Serwery 20 → /27 (.192), Zarządzanie 10 → /28 (.224), łącze punkt-punkt 2 → /30 (.240); wolne 12 adresów.'),
    body:
`Oddział dostał \`192.168.10.0/24\`. Potrzeby: ${OFFICE.map((o) => `${o.name} ${o.hosts}`).join(', ')}. Równy podział (np. na /26 po 62 hosty) nie zmieści biura, a /25 dla wszystkich zmieści tylko dwie sieci. Rozwiązanie — **VLSM**:
1. **Posortuj potrzeby malejąco** (największa pierwsza) — to gwarantuje, że każda kolejna podsieć wypadnie na swojej granicy.
2. Dla każdej potrzeby wybierz **najmniejszy prefiks**, który ją mieści (hosty + 2; zapas na wzrost dodaj do potrzeby przed liczeniem).
3. Przydzielaj **od początku bloku**, kolejne podsieci tuż po poprzednich:
${V.result.map((r) => `   - ${r.name} (${r.need}) → **${r.cidr}** — ${r.usable} hostów, ${r.first}–${r.last}`).join('\n')}
4. Sprawdź **wyrównanie**: każda podsieć zaczyna się od wielokrotności swojego rozmiaru (.128 dla /26 ✓, .192 dla /27 ✓, .224 dla /28 ✓, .240 dla /30 ✓).
5. Zapisz **wolne miejsce**: zajęte ${V.used} z 256, wolne ${V.free} adresów — \`192.168.10.244/30\` i \`192.168.10.248/29\` na kolejne łącza lub małe segmenty.
6. **Udokumentuj** w IPAM (NetBox, arkusz w repozytorium) z bramą (zwykle pierwszy host), VLAN-em i przeznaczeniem.

Sprawdzenie na łączu P2P: od dawna zamiast /30 (2 hosty, 4 adresy) można użyć **/31** (RFC 3021) — 2 adresy, oba używalne; oszczędza połowę adresów na setkach łączy routerów.` },

  { type: 'NOTE', heading: 'Agregacja tras i nakładające się zakresy', ...img('ns03-04-overlap.svg', 'Nakładanie się zakresów: VPN firmowy 172.16.0.0/12 zawiera sieć Dockera 172.17.0.0/16 i sieci compose 172.18.0.0/16 i dalsze, co przechwytuje ruch do firmy. Agregacja: cztery sieci 10.1.4–7.0/24 dają dokładnie 10.1.4.0/22, a 10.1.4.0/24 z 10.1.9.0/24 dają 10.1.0.0/20 z 3584 nadmiarowymi adresami.'),
    body:
`**Agregacja (supernetting).** Router oddziału zamiast czterech tras ogłasza jedną: 10.1.4.0/24 … 10.1.7.0/24 → **${S1.cidr}** (dokładnie — sieci są ciągłe i blok zaczyna się na granicy /22). Mniej tras = mniejsze tablice, szybsza zbieżność, stabilniejszy routing (zmiana w jednej /24 nie „mruga” trasą na zewnątrz). Warunek: bloki **ciągłe i wyrównane**. Agregacja 10.1.4.0/24 z 10.1.9.0/24 daje **${S2.cidr}** — obejmuje ${fmt(S2.extra)} adresów, których nie masz; ruch do nich trafi do Ciebie i zginie (czarna dziura), a jeśli ktoś inny je ma — ukradniesz mu ruch.

**Nakładanie się.** W routingu zawsze wygrywa **najdłuższy pasujący prefiks**. Typowe kolizje w pracy developera:
- **Docker** domyślnie bierze \`172.17.0.0/16\` (docker0), a docker compose kolejne \`172.18.0.0/16\`, \`172.19.0.0/16\`… — jeśli firmowy VPN prowadzi do \`172.16.0.0/12\`, serwery firmy z tych zakresów stają się nieosiągalne z laptopa (trasa przez docker0 jest dłuższa niż /12 z VPN). Naprawa: w \`/etc/docker/daemon.json\` ustaw \`"bip"\` i \`"default-address-pools"\` (np. \`{"base":"10.200.0.0/16","size":24}\`) poza zakresami firmy.
- **Domowe 192.168.0.0/24 i 192.168.1.0/24** kolidują z małymi sieciami biurowymi po drugiej stronie VPN — dlatego firmy wybierają mniej popularne zakresy (np. 10.x z losowym drugim oktetem).
- **VPC peering / Transit Gateway / VPN site-to-site** nie działa dla nakładających się CIDR — AWS po prostu nie pozwoli zestawić peeringu. Renumeracja działającej VPC to tygodnie pracy, więc **plan adresacji robi się na początku**.
- **Kubernetes**: zakres podów (np. \`10.244.0.0/16\`) i serwisów (\`10.96.0.0/12\`) nie może nakładać się z siecią węzłów ani z sieciami, do których pody mają się łączyć.` },

  { type: 'CODE_PRACTICAL', heading: 'Kalkulator podsieci i walidacja planu w Javie', body:
`Kod, który przyda się w narzędziach IaC, testach planu adresacji i w walidacji konfiguracji (np. „czy zakres z formularza nie koliduje z istniejącymi podsieciami”). Bez bibliotek: adres jako \`long\`, maska jako przesunięcie bitowe. W produkcji rozważ bibliotekę **IPAddress** (seancfoley) albo Commons Net \`SubnetUtils\`.`,
    code:
`import java.util.*;

record Cidr(long network, int prefix) {
    static Cidr parse(String s) {
        String[] p = s.split("/");
        long ip = 0;
        for (String o : p[0].split("\\\\.")) ip = (ip << 8) | Integer.parseInt(o);
        int prefix = Integer.parseInt(p[1]);
        if (prefix < 0 || prefix > 32) throw new IllegalArgumentException("prefiks 0–32");
        long mask = prefix == 0 ? 0 : (0xFFFFFFFFL << (32 - prefix)) & 0xFFFFFFFFL;
        return new Cidr(ip & mask, prefix);                     // normalizacja: 10.20.37.200/19 → 10.20.32.0/19
    }
    long size()      { return 1L << (32 - prefix); }
    long broadcast() { return network + size() - 1; }
    boolean overlaps(Cidr o) { return network <= o.broadcast() && o.network <= broadcast(); }
    List<Cidr> split(int newPrefix) {                          // podział na równe podsieci
        List<Cidr> out = new ArrayList<>();
        long step = 1L << (32 - newPrefix);
        for (long n = network; n <= broadcast(); n += step) out.add(new Cidr(n, newPrefix));
        return out;
    }
    static String ip(long v) { return (v >> 24 & 255) + "." + (v >> 16 & 255) + "." + (v >> 8 & 255) + "." + (v & 255); }
    @Override public String toString() { return ip(network) + "/" + prefix; }

    public static void main(String[] args) {
        Cidr c = parse("10.20.37.200/19");
        System.out.println(c + " broadcast " + ip(c.broadcast()) + " size " + c.size());   // ${P.cidr} broadcast ${P.broadcast} size ${P.size}
        System.out.println(parse("10.20.0.0/16").split(18));    // ${JSON.stringify(split('10.20.0.0/16', 18)).replace(/"/g, '').replace(/,/g, ', ')}
        List<Cidr> plan = List.of(${VPC.plan.slice(0, 3).map((x) => `parse("${x.cidr}")`).join(', ')});
        Cidr docker = parse("172.17.0.0/16"), vpn = parse("172.16.0.0/12");
        System.out.println("docker vs vpn overlap: " + docker.overlaps(vpn));               // true
        for (int i = 0; i < plan.size(); i++)
            for (int j = i + 1; j < plan.size(); j++)
                if (plan.get(i).overlaps(plan.get(j))) throw new IllegalStateException("kolizja " + plan.get(i) + " " + plan.get(j));
    }
}` },

  { type: 'USAGE', heading: 'Zadanie z planu: adresacja VPC 10.20.0.0/16 dla 3 AZ', ...img('ns03-04-vpc.svg', 'Projekt VPC 10.20.0.0/16 w trzech strefach: w każdej strefie podsieć publiczna /22 (10.20.96.0, .100.0, .104.0), prywatna /19 (10.20.0.0, .32.0, .64.0) i bazodanowa /24 (10.20.112.0, .113.0, .114.0); AWS rezerwuje 5 adresów w każdej podsieci; 10.20.128.0/17 zostaje w rezerwie.'),
    body:
`Wymagania: aplikacja w **3 strefach dostępności** (wysoka dostępność), w każdej trzy warstwy — **public** (ALB, NAT Gateway, bastion — mało adresów), **private** (serwisy Spring Boot, węzły i **pody EKS** — z AWS VPC CNI każdy pod dostaje adres z podsieci, więc potrzeba ich tysięcy), **db** (RDS/Aurora, ElastiCache — dziesiątki adresów). Plan z uzasadnieniem rozmiarów:
${['private', 'public', 'db'].map((t) => VPC.plan.filter((x) => x.tier === t).map((x) => `- **${t}-${x.az}**: \`${x.cidr}\` — ${fmt(x.aws)} adresów do użycia w AWS`).join('\n')).join('\n')}

Uzasadnienie: **private /19** (8 187 adresów na strefę) — przy ~30 podach na węzeł i kilkudziesięciu węzłach zużycie rośnie szybko, a podsieci nie da się powiększyć bez tworzenia nowej; **public /22** (1 019) — kilka adresów na ALB/NAT na strefę, z dużym zapasem na publiczne endpointy; **db /24** (251) — bazy, repliki, cache, z zapasem. Warstwy ułożone tak, by każdą dało się **opisać jednym prefiksem w security group / NACL**: wszystkie prywatne mieszczą się w \`10.20.0.0/18\` + \`10.20.64.0/19\`, publiczne w \`10.20.96.0/20\`, bazy w \`10.20.112.0/20\`. Zajęte ${fmt(VPC.used)} adresów, wolne ${fmt(VPC.free)}, w tym cała połowa \`${VPC.reserve}\` — na czwartą strefę, nowe warstwy (np. podsieci dla Lambda/VPC endpoints) albo drugi CIDR podów. **Zakres całej VPC wybierz tak, by nie kolidował** z biurem, VPN-em, innymi VPC (peering!) i domyślnymi zakresami Dockera/Kubernetesa.

W Terraformie zamiast ręcznego liczenia: \`cidrsubnet("10.20.0.0/16", 3, 0)\` = \`10.20.0.0/19\` (3 bity pożyczone, podsieć numer 0), \`cidrsubnet("10.20.0.0/16", 6, 24)\` = \`10.20.96.0/22\`.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy przy podsieciowaniu', body:
`- **Niewyrównany początek**: „10.20.8.0/20” nie istnieje jako sieć — narzędzia zamienią ją na 10.20.0.0/20 (lub odrzucą: Python \`strict=True\`, AWS „invalid CIDR”).
- **Zapominanie o rezerwach**: /28 w AWS daje 11, a nie 14 hostów; /24 — 251, a nie 254. Mała podsieć dla Lambdy/EKS skończy się w najgorszym momencie.
- **VLSM w złej kolejności**: przydzielanie od najmniejszych powoduje „dziury”, w które duża podsieć się nie zmieści, choć sumarycznie miejsca jest dość.
- **Brak zapasu**: podsieci nie da się powiększyć (w AWS trzeba tworzyć nową i migrować zasoby). Planuj ×2 względem dzisiejszych potrzeb.
- **Mylenie maski z wildcardem**: w ACL Cisco \`0.0.0.255\` to /24, a \`255.255.255.0\` jako wildcard znaczyłoby „dowolne pierwsze trzy oktety, dokładnie ostatni”.
- **Kolizje z Dockerem, VPN i domowymi 192.168.x** — sprawdzaj plan względem wszystkich sieci, z którymi kiedykolwiek się połączysz.
- **Zbyt szeroka agregacja**: trasa /16 ogłoszona „na zapas” przyciąga ruch do adresów, których nie masz.
- **/24 dla wszystkiego „bo łatwo liczyć”**: marnuje adresy w małych segmentach i nie mieści dużych (pody EKS).
- **Reguły bezpieczeństwa szersze niż podsieć**: security group z \`10.0.0.0/8\` „bo wszystko jest w 10.x” wpuszcza ruch z każdej VPC w peeringu i z VPN.` },

  { type: 'CODE_WRONG', heading: 'Źle: plan VPC „na szybko”', body:
`Wszystkie podsieci /24 po kolei, zakres VPC z domyślnych przykładów, brak rezerwy i kolizja z Dockerem. Działa w dniu wdrożenia — psuje się przy pierwszym skalowaniu EKS i przy pierwszym peeringu.`,
    code:
`# Terraform — nie rób tak
resource "aws_vpc" "main"  { cidr_block = "172.17.0.0/16" }          # = domyślny docker0 na laptopach developerów
resource "aws_subnet" "pub_a"  { cidr_block = "172.17.0.0/24" }
resource "aws_subnet" "priv_a" { cidr_block = "172.17.1.0/24" }      # 251 adresów na pody EKS → wyczerpanie po kilku węzłach
resource "aws_subnet" "db_a"   { cidr_block = "172.17.2.0/24" }
resource "aws_subnet" "pub_b"  { cidr_block = "172.17.3.0/24" }      # warstwy przemieszane — żadna nie ma wspólnego prefiksu
# security group dla bazy:
ingress { from_port = 5432, to_port = 5432, cidr_blocks = ["172.16.0.0/12"] }   # cała prywatna pula 172.16/12, w tym VPN` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: plan z cidrsubnet, rezerwą i regułami na prefiks warstwy', body:
`Zakres VPC spoza domyślnych pul Dockera i VPN, podsieci liczone funkcją \`cidrsubnet\` (bez ręcznych błędów), warstwy w ciągłych blokach i reguły ograniczone do prefiksu warstwy aplikacji.`,
    code:
`locals {
  vpc_cidr = "10.20.0.0/16"
  azs      = ["eu-central-1a", "eu-central-1b", "eu-central-1c"]
}
resource "aws_vpc" "main" { cidr_block = local.vpc_cidr }
resource "aws_subnet" "private" {                                     # /19: ${VPC.plan.filter((x) => x.tier === 'private').map((x) => x.cidr).join(', ')}
  count = 3, vpc_id = aws_vpc.main.id, availability_zone = local.azs[count.index]
  cidr_block = cidrsubnet(local.vpc_cidr, 3, count.index)
}
resource "aws_subnet" "public" {                                      # /22: ${VPC.plan.filter((x) => x.tier === 'public').map((x) => x.cidr).join(', ')}
  count = 3, vpc_id = aws_vpc.main.id, availability_zone = local.azs[count.index]
  cidr_block = cidrsubnet(local.vpc_cidr, 6, 24 + count.index)
}
resource "aws_subnet" "db" {                                          # /24: ${VPC.plan.filter((x) => x.tier === 'db').map((x) => x.cidr).join(', ')}
  count = 3, vpc_id = aws_vpc.main.id, availability_zone = local.azs[count.index]
  cidr_block = cidrsubnet(local.vpc_cidr, 8, 112 + count.index)
}
# 10.20.128.0/17 — rezerwa (udokumentowana w IPAM)
# baza przyjmuje ruch tylko z podsieci prywatnych (a najlepiej: tylko z security group aplikacji)
ingress { from_port = 5432, to_port = 5432, security_groups = [aws_security_group.app.id] }` },

  { type: 'WHEN_TO_USE', heading: 'Jak dobrać rozmiar i kiedy który zabieg', body:
`Ściąga projektowa:`,
    code:
`SYTUACJA                                          ROZWIĄZANIE
łącze router–router                               /31 (RFC 3021) lub /30
małe segmenty: zarządzanie, VPN endpoints         /28–/27 (w chmurze pamiętaj o −5)
LAN biurowy / VLAN użytkowników                    /24–/22 (zapas ×2; duże domeny rozgłoszeniowe > /22 — unikaj)
podsieci prywatne w chmurze z podami (EKS CNI)     /19–/18 na strefę albo osobny CIDR podów / prefix delegation
VPC                                                /16 (maks. w AWS), zakres spoza Dockera/VPN/innych VPC
wiele podsieci różnej wielkości w jednym bloku    VLSM, od największej
wiele sąsiednich sieci za jednym routerem          agregacja do wspólnego prefiksu (tylko bloki ciągłe i wyrównane)
reguły ACL / security group                        prefiks warstwy, nie /8 „bo prościej”
liczenie w głowie (egzamin)                        magiczna liczba 256 − oktet maski
liczenie w pracy                                   ipcalc, Python ipaddress, Terraform cidrsubnet, IPAM` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- CIDR: \`adres/N\`, rozmiar 2^(32−N), hosty 2^(32−N) − 2 (/31 i /32 — wyjątki), maska = N jedynek, **wildcard** = 255.255.255.255 − maska.
- Parametry podsieci liczysz magiczną liczbą w „interesującym” oktecie: sieć = w dół do wielokrotności, broadcast = sieć + rozmiar − 1.
- Podział: k pożyczonych bitów = 2^k podsieci; granice zawsze wyrównane do rozmiaru.
- **VLSM**: sortuj potrzeby malejąco, najmniejszy pasujący prefiks, przydzielaj kolejno, dokumentuj wolne bloki.
- **Agregacja**: ciągłe i wyrównane bloki → jedna trasa; zbyt szeroka agregacja = czarna dziura.
- **Nakładanie**: wygrywa najdłuższy prefiks; Docker 172.17/16 vs VPN 172.16/12, domowe 192.168.x, peering VPC — planuj zakresy z góry.
- Chmura: −5 adresów na podsieć w AWS, VPC /16, warstwy w ciągłych blokach, \`cidrsubnet\` w Terraformie, rezerwa na rozwój.
- Następna lekcja: **trening podsieciowania** — dziesiątki zadań na czas, aż liczenie stanie się odruchem.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const c of ['10.20.37.200/19', '172.16.45.130/21', '192.168.77.99/27', '10.1.255.1/23', '100.64.12.34/10', '192.168.1.190/26']) {
  const p = parse(c);
  ex(`Dla ${c} podaj: maskę, wildcard, adres sieci, broadcast, pierwszy i ostatni host oraz liczbę hostów (klasycznie i w AWS).`,
    'Magiczna liczba w oktecie, w którym kończy się prefiks.',
    `Maska ${p.mask}, wildcard ${p.wildcard}, sieć ${p.network}, broadcast ${p.broadcast}, hosty ${p.first} – ${p.last}, ${fmt(p.hosts)} hostów (w AWS ${fmt(p.size - 5)}).`);
}
for (const [base, np] of [['10.20.0.0/16', 18], ['172.16.8.0/22', 24], ['192.168.50.0/24', 27]]) {
  const parts = split(base, np);
  ex(`Podziel ${base} na ${parts.length} równe podsieci. Podaj prefiks i wszystkie adresy sieci.`,
    `Pożycz log2(${parts.length}) bitów.`,
    `Prefiks /${np}: ${parts.join(', ')}.`);
}
for (const [h, r] of [[300, 2], [60, 5], [1000, 2], [5, 2], [2000, 5]]) {
  const p = prefixForHosts(h, r);
  ex(`Jaki najmniejszy prefiks zmieści ${h} hostów${r === 5 ? ' w podsieci AWS' : ''}?`,
    `2^(32−p) − ${r} ≥ ${h}.`,
    `/${p}: ${fmt(size(p))} adresów, ${fmt(size(p) - r)} do użycia${p < 32 ? `; /${p + 1} dałby tylko ${fmt(size(p + 1) - r)}` : ''}.`);
}
for (const [base, needs] of [
  ['192.168.10.0/24', OFFICE],
  ['10.50.0.0/22', [{ name: 'Wi-Fi gości', hosts: 400 }, { name: 'Pracownicy', hosts: 200 }, { name: 'Drukarki', hosts: 25 }, { name: 'Kamery', hosts: 40 }, { name: 'P2P-1', hosts: 2 }, { name: 'P2P-2', hosts: 2 }]],
  ['172.20.0.0/23', [{ name: 'Produkcja', hosts: 120 }, { name: 'Test', hosts: 60 }, { name: 'Dev', hosts: 60 }, { name: 'CI runners', hosts: 28 }, { name: 'Bastion', hosts: 3 }]],
]) {
  const v = vlsm(base, needs);
  ex(`VLSM: podziel ${base} dla: ${needs.map((n) => `${n.name} ${n.hosts}`).join(', ')}. Podaj podsieci i wolne miejsce.`,
    'Sortuj malejąco, najmniejszy pasujący prefiks, przydzielaj kolejno.',
    `${v.result.map((r) => `${r.name} → ${r.cidr} (${r.usable} hostów)`).join('; ')}. Zajęte ${v.used} adresów, wolne ${v.free}.`);
}
for (const set of [['10.1.4.0/24', '10.1.5.0/24', '10.1.6.0/24', '10.1.7.0/24'], ['172.16.32.0/21', '172.16.40.0/21'], ['192.168.0.0/24', '192.168.1.0/24', '192.168.2.0/24'], ['10.1.4.0/24', '10.1.9.0/24']]) {
  const s = summarize(set);
  ex(`Zagreguj trasy: ${set.join(', ')}. Czy agregacja jest dokładna?`,
    'Najdłuższy wspólny prefiks; porównaj rozmiar z sumą rozmiarów.',
    `${s.cidr} — ${s.exact ? 'dokładna (bez nadmiarowych adresów)' : `NIEdokładna: obejmuje ${fmt(s.extra)} adresów spoza listy — ryzyko czarnej dziury; lepiej ogłosić osobne trasy lub mniejsze agregaty`}.`);
}
for (const [a, b] of [['172.17.0.0/16', '172.16.0.0/12'], ['10.244.0.0/16', '10.20.0.0/16'], ['192.168.1.0/24', '192.168.0.0/23']]) {
  ex(`Czy zakresy ${a} i ${b} się nakładają? Co się stanie z ruchem do adresu z części wspólnej, jeśli oba są w tablicy routingu?`,
    'Porównaj zakresy; longest prefix match.',
    M.overlaps(a, b) ? `Tak — ${M.contains(b, a) ? `${a} leży wewnątrz ${b}` : M.contains(a, b) ? `${b} leży wewnątrz ${a}` : 'częściowo'}. Ruch do adresów z części wspólnej pójdzie trasą o dłuższym prefiksie (${parse(a).prefix > parse(b).prefix ? a : b}), więc druga sieć będzie dla tych adresów nieosiągalna.` : 'Nie — zakresy są rozłączne, trasy nie konkurują.');
}
const more = [
  ['Wykonaj zadanie z planu: zaprojektuj adresację VPC 10.20.0.0/16 dla 3 AZ (public/private/db) i uzasadnij rozmiary. Uwzględnij rezerwy AWS, EKS, reguły bezpieczeństwa i rezerwę na rozwój.', 'Największe potrzeby: private (pody). Warstwy w ciągłych blokach. cidrsubnet.', `Przykładowy plan: ${VPC.plan.map((x) => `${x.tier}-${x.az} ${x.cidr}`).join(', ')}. Uzasadnienie: private /19 (8187 adresów w AWS) na węzły i pody EKS z VPC CNI; public /22 na ALB/NAT/endpointy; db /24 na RDS i repliki. Zajęte ${fmt(VPC.used)}, wolne ${fmt(VPC.free)} z rezerwą ${VPC.reserve}. Zakres spoza 172.17/16 (Docker) i spoza zakresów biura/VPN/innych VPC. Reguły: baza tylko z SG aplikacji, publiczne bez bezpośredniego dostępu do db.`],
  ['Po podłączeniu do firmowego VPN developer traci dostęp do serwerów 172.17.5.0/24 w firmie. Zdiagnozuj i napraw.', 'ip route get; docker0; daemon.json.', 'ip route get 172.17.5.10 → dev docker0 (trasa 172.17.0.0/16 Dockera jest dłuższa niż 172.16.0.0/12 z VPN). Naprawa: w /etc/docker/daemon.json ustaw "bip": "10.200.0.1/24" i "default-address-pools": [{"base":"10.201.0.0/16","size":24}], usuń stare sieci (docker network prune) i zrestartuj Dockera; sprawdź ponownie ip route get. Długofalowo: polityka firmowa zakresów dla Dockera/WSL/Kubernetesa na laptopach.'],
  ['Napisz regułę ACL Cisco (z wildcardem) przepuszczającą ruch HTTPS z podsieci 10.20.96.0/22 do 10.20.0.0/19.', 'Wildcard = 255.255.255.255 − maska.', 'access-list 110 permit tcp 10.20.96.0 0.0.3.255 10.20.0.0 0.0.31.255 eq 443 (wildcard /22 = 0.0.3.255, /19 = 0.0.31.255). W nazwanych ACL: ip access-list extended APP-IN / permit tcp 10.20.96.0 0.0.3.255 10.20.0.0 0.0.31.255 eq 443.'],
  ['Wyjaśnij, czym różni się /31 od /30 na łączu punkt-punkt i kiedy /31 nie zadziała.', 'RFC 3021.', '/30 ma 4 adresy: sieć, 2 hosty, broadcast — 50% marnotrawstwa. /31 (RFC 3021) ma 2 adresy, oba przypisane routerom — brak sieci i broadcastu, bo na łączu P2P nie są potrzebne. Nie zadziała ze starym sprzętem/oprogramowaniem bez obsługi RFC 3021 i na segmentach wielodostępnych (Ethernet z więcej niż dwoma urządzeniami, protokoły wymagające broadcastu).'],
  ['W Terraformie policz ręcznie wynik: cidrsubnet("10.20.0.0/16", 3, 2), cidrsubnet("10.20.0.0/16", 8, 113), cidrsubnet("192.168.0.0/24", 2, 3).', 'newbits dodane do prefiksu, netnum = numer podsieci.', `/16+3 = /19, podsieć nr 2: ${split('10.20.0.0/16', 19)[2]}; /16+8 = /24, nr 113: ${split('10.20.0.0/16', 24)[113]}; /24+2 = /26, nr 3: ${split('192.168.0.0/24', 26)[3]}.`],
  ['Dlaczego w chmurze nie da się po prostu „powiększyć” podsieci, gdy zabraknie adresów, i jakie masz opcje?', 'Podsieć ma stały CIDR; dodatkowe CIDR VPC.', 'CIDR podsieci jest niezmienny po utworzeniu (AWS, Azure, GCP — z ograniczonymi wyjątkami w GCP dla rozszerzania). Opcje: utworzyć nową, większą podsieć i migrować zasoby; dodać secondary CIDR do VPC (np. 100.64.0.0/16 dla podów EKS z custom networking); w EKS — prefix delegation lub osobny zakres podów; przeprojektować z zapasem. Dlatego plan robi się z rezerwą od początku.'],
];
for (const [p, h, s] of more) ex(p, h, s);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
