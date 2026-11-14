// Generuje lekcje _ns_03_ip_i_routing/06_SpecialAndPrivateAddresses.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_06_special_lesson.cjs src/main/resources/content/_ns_03_ip_i_routing/06_SpecialAndPrivateAddresses.json
const { exerciseBuilder, writeLesson } = require('./lib.cjs');
const { V4, V6, classify4, classify6 } = require('./ns03_06_special_model.cjs');
const buildQuiz = require('./ns03_06_special_quiz.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Nie każdy adres jest „zwykłym” adresem', ...img('ns03-06-ranges.svg', 'Tabela zakresów specjalnych IPv4: 0.0.0.0/8, prywatne 10.0.0.0/8, 172.16.0.0/12 i 192.168.0.0/16 (RFC 1918), CGNAT 100.64.0.0/10, loopback 127.0.0.0/8, link-local 169.254.0.0/16, dokumentacyjne 192.0.2.0/24, 198.51.100.0/24 i 203.0.113.0/24, benchmark 198.18.0.0/15, multicast 224.0.0.0/4, zarezerwowany 240.0.0.0/4 i broadcast 255.255.255.255.'),
    body:
`Spośród 4,3 miliarda adresów IPv4 spora część ma **specjalne znaczenie**: adresy prywatne, których nie routuje Internet; pętla zwrotna, która nigdy nie opuszcza hosta; adresy link-local, pod którymi chmura wystawia **metadane instancji z kluczami dostępu**; przestrzeń CGNAT operatorów; zakresy dokumentacyjne do przykładów; multicast; adres „wszystkie interfejsy” 0.0.0.0.

Dla Java developera to nie jest teoria z egzaminu — od tej wiedzy zależy, czy:
- Twoja aplikacja w kontenerze jest **w ogóle osiągalna** (\`server.address=127.0.0.1\` vs \`0.0.0.0\`),
- usługa „tylko dla mnie” nie jest przypadkiem **wystawiona na cały LAN** (\`docker run -p 8080:8080\`),
- funkcja „pobierz obrazek z podanego URL” nie staje się furtką **SSRF** do \`169.254.169.254\` i kluczy IAM,
- blokada „po IP” nie odcina setek niewinnych użytkowników za **CGNAT**,
- walidacja adresu w kodzie nie przepuszcza \`127.1\`, \`0x7f000001\` czy \`[::ffff:127.0.0.1]\`.

Cele: Network+ N10-009 (domena 1: RFC 1918, APIPA, loopback, typy adresów IPv6), CCNA v2 (adresy prywatne IPv4, typy adresów IPv6), Security+ i CySA+ (SSRF, ekspozycja usług, analiza logów), OWASP Top 10:2025 (SSRF w kategorii kontroli dostępu).` },

  { type: 'DEFINITION', heading: 'Słownik adresów specjalnych', body:
`- **Prywatne (RFC 1918)**: \`10.0.0.0/8\`, \`172.16.0.0/12\` (172.16–172.31), \`192.168.0.0/16\`. Wolno ich używać każdemu wewnątrz sieci; nie są routowane w Internecie — do wyjścia potrzebny NAT.
- **Pętla zwrotna (loopback)**: \`127.0.0.0/8\` — cały blok, nie tylko 127.0.0.1 (np. \`127.0.0.53\` to systemd-resolved). Ruch nigdy nie opuszcza hosta. IPv6: \`::1\`.
- **Link-local (APIPA)**: \`169.254.0.0/16\` — adres nadawany automatycznie, gdy brak DHCP; ważny tylko na łączu. W chmurze **169.254.169.254** = usługa metadanych instancji (AWS, GCP, Azure, OpenStack), 169.254.170.2 — metadane kontenerów ECS. IPv6: \`fe80::/10\`.
- **Współdzielona przestrzeń CGNAT (RFC 6598)**: \`100.64.0.0/10\` — między routerem klienta a NAT-em operatora (Carrier-Grade NAT); używana też przez Tailscale i jako dodatkowy CIDR podów w EKS.
- **0.0.0.0**: przy **bind** = „nasłuchuj na wszystkich interfejsach” (IPv6: \`::\`); jako źródło = „jeszcze nie mam adresu” (DHCP DISCOVER); w tablicy routingu \`0.0.0.0/0\` = trasa domyślna. **Nie jest** poprawnym adresem docelowym.
- **Dokumentacyjne (RFC 5737)**: \`192.0.2.0/24\`, \`198.51.100.0/24\`, \`203.0.113.0/24\` (IPv6: \`2001:db8::/32\`) — do przykładów w dokumentacji i testach; nigdy nie należą do nikogo.
- **Benchmark (RFC 2544)**: \`198.18.0.0/15\` — testy wydajności urządzeń.
- **Multicast**: \`224.0.0.0/4\` (224.0.0.0/24 tylko lokalnie: 224.0.0.5 OSPF, 224.0.0.251 mDNS); IPv6 \`ff00::/8\`.
- **Zarezerwowane**: \`240.0.0.0/4\` (dawna klasa E); **ograniczony broadcast** \`255.255.255.255\` — nie przechodzi przez routery.
- **IPv6**: \`::\` nieokreślony, \`::1\` loopback, \`fe80::/10\` link-local, \`fc00::/7\` ULA (odpowiednik prywatnych; w praktyce \`fd00::/8\`), \`2000::/3\` globalne unicast, \`::ffff:0:0/96\` IPv4 mapowany, \`64:ff9b::/96\` NAT64.
- **Bogon** — adres, który nie powinien pojawić się w Internecie jako źródło (prywatny, zarezerwowany, nieprzydzielony); filtrowany na brzegu sieci.` },

  { type: 'ANALOGY', heading: 'Numery wewnętrzne w biurowcu', body:
`- **Adresy prywatne** to numery wewnętrne telefonów w firmie: „wew. 214” istnieje w każdym biurowcu, ale nie zadzwonisz na nie z zewnątrz. Żeby wyjść na świat, centrala (NAT) podstawia swój numer zewnętrzny.
- **Loopback 127.0.0.1** to dzwonienie do samego siebie z tego samego aparatu — rozmowa nigdy nie wychodzi z pokoju.
- **Link-local 169.254.x.x** to rozmowa przez ścianę z sąsiadem z tego samego korytarza — działa bez centrali, ale nie dalej.
- **169.254.169.254** to **recepcja budynku**: z każdego pokoju możesz zapytać „kim jestem i jakie mam klucze do drzwi?”. Wygodne — dopóki ktoś z zewnątrz nie przekona Twojego pracownika, żeby zadzwonił na recepcję i przeczytał mu odpowiedź przez okno (**SSRF**).
- **CGNAT** to sytuacja, w której cały biurowiec (setki firm) dzwoni na zewnątrz z **jednego** numeru. Gdy ktoś zablokuje ten numer za spam od jednej firmy — nie dodzwoni się nikt z budynku.
- **0.0.0.0 przy bind** to „odbieram na każdym aparacie w pokoju”; **127.0.0.1** — „odbieram tylko na wewnętrznym”.
- **Adresy dokumentacyjne** to numery 555-xxxx z filmów — celowo nieistniejące, żeby nikt nie dzwonił do prawdziwych ludzi.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Klasyfikacja adresów: rozpoznaj na pierwszy rzut oka', body:
`Zestaw adresów, które spotkasz w logach, konfiguracjach i alertach — z klasyfikacją wyliczoną z rejestru (w tej samej kolejności sprawdza je walidator SSRF, który napiszesz niżej). Zwróć uwagę na granice: \`172.32.0.1\` jest już **publiczny** (172.16/12 kończy się na 172.31), a \`100.128.0.1\` — poza CGNAT.`,
    code:
`${['10.1.2.3', '172.31.0.1', '172.32.0.1', '192.168.0.1', '100.64.1.1', '100.128.0.1', '127.0.0.53', '169.254.169.254', '0.0.0.0', '8.8.8.8', '198.51.100.7', '198.19.0.1', '224.0.0.251', '240.0.0.1', '255.255.255.255'].map((ip) => { const c = classify4(ip); return `${ip.padEnd(17)} ${c.range.padEnd(19)} ${c.name}`; }).join('\n')}

${['::1', 'fe80::1', 'fd12:3456::1', '2001:db8::1', '2a00:1450:4001::1', 'ff02::1', '::ffff:192.168.1.1', '64:ff9b::808:808'].map((ip) => { const c = classify6(ip); return `${ip.padEnd(20)} ${c.range.padEnd(16)} ${c.name}`; }).join('\n')}` },

  { type: 'CODE_BASIC', heading: 'Ćwiczenie z planu: server.address=127.0.0.1 vs 0.0.0.0', ...img('ns03-06-bind.svg', 'Porównanie adresów nasłuchu: bind 127.0.0.1 dostępny tylko z tego samego hosta, bind 0.0.0.0 ze wszystkich sieci, bind na konkretny adres tylko przez ten interfejs. W kontenerze bind 127.0.0.1 blokuje publikację portu, a -p 8080:8080 publikuje usługę na wszystkich interfejsach hosta; bezpieczniej -p 127.0.0.1:8080:8080.'),
    body:
`Uruchom tę samą aplikację Spring Boot w trzech wariantach i sprawdź, **kto może się połączyć**: ten sam host, kontener obok, komputer w LAN. Domyślnie Spring Boot (Tomcat) nasłuchuje na wszystkich interfejsach (\`0.0.0.0\` i \`[::]\`).`,
    code:
`# 1) na hoście, tylko loopback
java -jar app.jar --server.address=127.0.0.1
ss -ltnp | grep 8080          # LISTEN 127.0.0.1:8080
curl -s localhost:8080/actuator/health        # ✓
curl -s http://192.168.1.20:8080/actuator/health  # ✗ Connection refused (z innego komputera w LAN)

# 2) na hoście, wszystkie interfejsy (domyślnie)
java -jar app.jar --server.address=0.0.0.0     # ss: 0.0.0.0:8080 / [::]:8080 → ✓ z LAN (jeśli firewall pozwala)

# 3) w kontenerze
docker run -p 8080:8080 -e SERVER_ADDRESS=127.0.0.1 app   # ✗ curl localhost:8080 z hosta: „Connection reset”
#    aplikacja słucha na loopbacku KONTENERA, a docker-proxy/iptables wysyła ruch na eth0 kontenera
docker run -p 8080:8080 app                               # ✓ z hosta, ✓ z LAN (port opublikowany na 0.0.0.0 hosta!)
docker run -p 127.0.0.1:8080:8080 app                     # ✓ z hosta, ✗ z LAN — tak dla narzędzi „tylko dla mnie”

# Uwaga: porty opublikowane przez Dockera omijają reguły UFW (Docker dopisuje własne łańcuchy iptables).
# Bazy w docker-compose dla dev: "127.0.0.1:5432:5432", nie "5432:5432".` },

  { type: 'STEP_BY_STEP', heading: 'Metadane chmury, SSRF i jak się bronić', ...img('ns03-06-metadata.svg', 'Schemat ataku SSRF: atakujący podaje aplikacji URL do 169.254.169.254, aplikacja pobiera metadane instancji, w IMDSv1 łącznie z kluczami roli IAM. Obrona: IMDSv2 z tokenem i hop limitem 1, allow-lista hostów, blokada adresów specjalnych po rozwiązaniu DNS, filtrowanie ruchu wychodzącego.'),
    body:
`**SSRF (Server-Side Request Forgery)** — aplikacja wykonuje żądanie HTTP pod adres podany przez użytkownika (podgląd linku, webhook, import z URL, generator PDF). Atakujący podaje adres wewnętrzny, którego sam nie widzi, a serwer — tak. Najcenniejszy cel: **usługa metadanych instancji** pod \`169.254.169.254\`, która w IMDSv1 zwraca tymczasowe klucze roli IAM zwykłym GET-em.

Obrona warstwowa:
1. **Allow-lista** hostów/domen, do których aplikacja może się łączyć (najlepiej: tylko te, a nie „wszystko oprócz złych”).
2. Jeśli allow-lista jest niemożliwa (webhooki klientów): **rozwiąż nazwę DNS, sprawdź KAŻDY zwrócony adres** pod kątem zakresów specjalnych (loopback, RFC 1918, link-local, CGNAT, 0.0.0.0/8, multicast, IPv6 ::1, fe80::/10, fc00::/7, ::ffff:127.0.0.1…) i **łącz się z tym sprawdzonym adresem** (inaczej DNS rebinding: pierwsze rozwiązanie zwraca adres publiczny, drugie — 127.0.0.1).
3. **Wyłącz automatyczne przekierowania** albo sprawdzaj każdy adres po przekierowaniu (302 do \`http://169.254.169.254/\`).
4. Parsuj adresy **biblioteką**, nie regexem — \`127.1\`, \`2130706433\`, \`0x7f.0.0.1\`, \`017700000001\` to także 127.0.0.1 dla wielu parserów.
5. **IMDSv2** w AWS: wymuś \`HttpTokens=required\` i \`HttpPutResponseHopLimit=1\` (token trzeba pobrać PUT-em z nagłówkiem — typowe SSRF przez GET tego nie zrobi; hop limit 1 blokuje dostęp z kontenerów za dodatkowym skokiem). GCP wymaga nagłówka \`Metadata-Flavor: Google\`, Azure — \`Metadata: true\`.
6. **Egress**: polityki sieciowe Kubernetes / security groups blokujące 169.254.169.254 z podów, które go nie potrzebują; minimalne uprawnienia roli IAM.
7. **Wykrywanie**: logi żądań wychodzących, alert na połączenia aplikacji do zakresów specjalnych, GuardDuty („credentials used from outside instance”).` },

  { type: 'NOTE', heading: 'CGNAT, IPv6 i adresy w logach', ...img('ns03-06-cgnat-ipv6.svg', 'Łańcuch CGNAT: laptop 192.168.1.20, router domowy z adresem WAN 100.72.14.9 z puli 100.64.0.0/10, CGNAT operatora z publicznym 203.0.113.50, serwer widzi tylko adres publiczny. Lista adresów specjalnych IPv6.'),
    body:
`**CGNAT** (RFC 6598, \`100.64.0.0/10\`): operatorzy mobilni i wielu stacjonarnych nie mają już adresów IPv4 dla każdego klienta. Router klienta dostaje adres z 100.64/10, a NAT operatora tłumaczy setki klientów na jeden publiczny adres. Skutki dla backendu i bezpieczeństwa:
- **rate limiting i blokady po IP** trafiają w wielu niewinnych użytkowników naraz (mobilny Internet, akademiki) — limituj per konto/token, a IP traktuj jako słaby sygnał;
- do identyfikacji sprawcy operator potrzebuje **adresu + portu źródłowego + dokładnego czasu** — loguj port źródłowy (np. \`%{remote}p\` w logach Apache, \`$remote_port\` w Nginx) i synchronizuj czas (NTP);
- klient za CGNAT nie przyjmie połączeń przychodzących (brak przekierowania portów) — stąd WebSockety, push, relaye TURN.

**IPv6**: każdy interfejs ma adres **link-local fe80::/10** zawsze (nawet bez routera), a często kilka globalnych (GUA, w tym tymczasowe „privacy addresses”). ULA \`fd00::/8\` to odpowiednik RFC 1918 — ale w IPv6 NAT zwykle nie występuje. **Uwaga na zapis mapowany** \`::ffff:192.168.1.1\` — gniazdo dual-stack w Javie raportuje klientów IPv4 właśnie tak; allow-lista na tekst „192.168.” go przepuści lub zablokuje nieoczekiwanie. Porównuj obiekty \`InetAddress\`, nie napisy.` },

  { type: 'CODE_PRACTICAL', heading: 'Walidator adresów przeciw SSRF w Javie', body:
`Kluczowe decyzje: (1) rozwiązujemy nazwę **raz** i sprawdzamy **wszystkie** adresy; (2) używamy metod \`InetAddress\` (\`isLoopbackAddress\`, \`isSiteLocalAddress\` itd.) plus brakujących zakresów (CGNAT, 0.0.0.0/8, ULA); (3) dalej łączymy się **z tym sprawdzonym adresem**, a nie ponownie po nazwie. W produkcji połącz to z allow-listą i wyłączonymi przekierowaniami.`,
    code:
`import java.net.*;
import java.util.List;

final class OutboundGuard {

    static List<InetAddress> resolveAllowed(String host) throws UnknownHostException {
        InetAddress[] all = InetAddress.getAllByName(host);     // A i AAAA — sprawdzamy KAŻDY
        for (InetAddress a : all) if (isInternal(a)) throw new SecurityException("adres wewnętrzny: " + a);
        return List.of(all);                                    // łącz się z tymi adresami (ochrona przed DNS rebinding)
    }

    static boolean isInternal(InetAddress a) {
        if (a instanceof Inet6Address v6) {
            byte[] b = v6.getAddress();
            if (isMappedV4(b)) return isInternal(toV4(b));       // ::ffff:127.0.0.1
            return v6.isLoopbackAddress() || v6.isLinkLocalAddress() || v6.isAnyLocalAddress()
                || v6.isMulticastAddress() || (b[0] & 0xFE) == 0xFC;  // fc00::/7 (ULA)
        }
        byte[] b = a.getAddress();
        int o1 = b[0] & 0xFF, o2 = b[1] & 0xFF;
        return a.isAnyLocalAddress()            // 0.0.0.0
            || a.isLoopbackAddress()            // 127.0.0.0/8
            || a.isLinkLocalAddress()           // 169.254.0.0/16 — w tym 169.254.169.254
            || a.isSiteLocalAddress()           // 10/8, 172.16/12, 192.168/16
            || a.isMulticastAddress()           // 224/4
            || o1 == 0                          // 0.0.0.0/8
            || (o1 == 100 && (o2 & 0xC0) == 64) // 100.64.0.0/10 CGNAT
            || o1 >= 240;                       // 240/4 i 255.255.255.255
    }

    private static boolean isMappedV4(byte[] b) {
        for (int i = 0; i < 10; i++) if (b[i] != 0) return false;
        return (b[10] & 0xFF) == 0xFF && (b[11] & 0xFF) == 0xFF;
    }
    private static InetAddress toV4(byte[] b) {
        try { return InetAddress.getByAddress(new byte[]{b[12], b[13], b[14], b[15]}); }
        catch (UnknownHostException e) { throw new IllegalStateException(e); }
    }

    public static void main(String[] args) throws Exception {
        for (String h : List.of("169.254.169.254", "127.1", "10.0.0.5", "100.100.0.1", "8.8.8.8", "::1", "::ffff:127.0.0.1", "fd00::1"))
            System.out.println(h + " → " + (isInternal(InetAddress.getByName(h)) ? "BLOKUJ" : "ok"));
    }
}` },

  { type: 'USAGE', heading: 'Gdzie to spotkasz jako developer i jako obrońca', body:
`**Developer / DevOps:**
- \`server.address\`, \`management.server.address\` (Actuator tylko na 127.0.0.1 lub w sieci zarządzania), bind baz w docker-compose (\`127.0.0.1:5432:5432\`).
- **Kontenery**: aplikacja w kontenerze musi słuchać na 0.0.0.0 (izolację daje sieć kontenera i publikacja portów, nie loopback); \`host.docker.internal\` zamiast 127.0.0.1 do usług na hoście.
- **Kubernetes**: probe'y kubeleta trafiają na adres poda — aplikacja na 127.0.0.1 nie przejdzie readiness; sidecary w tym samym podzie dzielą loopback.
- **Testy i dokumentacja**: w przykładach używaj 192.0.2.0/24, 198.51.100.0/24, 203.0.113.0/24, 2001:db8::/32 i domeny example.com — nigdy cudzych prawdziwych adresów.
- **Logi za proxy i CGNAT**: zapisuj X-Forwarded-For (od zaufanych proxy) i port źródłowy.

**Blue team:**
- **Bogon filtering** na brzegu: odrzucaj ruch przychodzący z Internetu ze źródłami prywatnymi, loopback, link-local, CGNAT, multicast, 240/4 (oznaka spoofingu lub błędnej konfiguracji).
- **Alerty**: połączenia procesów aplikacji do 169.254.169.254 z nietypowym User-Agentem, nagły ruch do zakresów RFC 1918 z segmentu DMZ (ruch lateralny), usługi nasłuchujące na 0.0.0.0, które powinny być lokalne (audyt \`ss -ltnp\`).
- **Ekspozycja**: skanery (Shodan, Censys) znajdują bazy, Redis, Elasticsearch i panele administracyjne przypadkowo opublikowane na 0.0.0.0 — to jedno z najczęstszych źródeł wycieków danych.
- Mapowanie: Security+ (SSRF, bezpieczna konfiguracja), CySA+ (analiza ekspozycji i logów), OWASP Top 10:2025 (SSRF w A01 Broken Access Control).` },

  { type: 'PITFALL', heading: 'Częste błędy', body:
`- **172.16.0.0/16 zamiast /12** — prywatny blok obejmuje 172.16–172.31; 172.32.x.x jest publiczny (i odwrotnie: reguła „172.*” obejmuje cudze adresy).
- **Loopback = tylko 127.0.0.1** — cały 127.0.0.0/8; walidacja \`!host.equals("127.0.0.1")\` przepuści 127.0.0.2 i 127.1.
- **Walidacja tekstu zamiast adresu** — \`localhost\`, \`127.1\`, \`2130706433\`, \`0x7f000001\`, \`[::]\`, \`[::ffff:7f00:1]\` i domena wskazująca na 127.0.0.1 przejdą przez filtr „startsWith”.
- **Sprawdzenie DNS, a potem ponowne rozwiązanie przy łączeniu** — DNS rebinding.
- **Aplikacja w kontenerze na 127.0.0.1** — „u mnie działa”, w Dockerze nie.
- **\`-p 6379:6379\` dla Redisa bez hasła** na serwerze z publicznym IP — Docker omija UFW; Redis jest wystawiony na świat.
- **Używanie cudzych adresów w przykładach i testach** (np. 1.1.1.1 jako „fałszywy serwer”) — testy uderzają w prawdziwe usługi.
- **Blokowanie po IP bez świadomości CGNAT** — wycinasz całe sieci mobilne.
- **Traktowanie 0.0.0.0 jako bezpiecznego „pustego” adresu** — w niektórych przeglądarkach i systemach żądanie do 0.0.0.0 trafia do localhosta (problem „0.0.0.0 day”).` },

  { type: 'CODE_WRONG', heading: 'Źle: „ochrona” SSRF przez porównanie tekstu', body:
`Filtr, który wygląda rozsądnie, a przepuszcza większość ataków: porównuje napisy, nie rozwiązuje DNS, podąża za przekierowaniami i łączy się ponownie po nazwie.`,
    code:
`String host = URI.create(userUrl).getHost();
if (host.equals("localhost") || host.startsWith("127.") || host.startsWith("10.")
        || host.startsWith("192.168.") || host.equals("169.254.169.254")) {
    throw new SecurityException("zabronione");
}
// przepuszcza: 127.1, 2130706433, 0x7f.1, [::1], [::ffff:a9fe:a9fe], 172.16.x.x, 100.64.x.x,
// metadata.attacker.example → 169.254.169.254 (DNS), przekierowanie 302 do http://169.254.169.254/
HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build()
          .send(HttpRequest.newBuilder(URI.create(userUrl)).build(), HttpResponse.BodyHandlers.ofString());` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: allow-lista, rozwiązanie DNS, sprawdzenie adresu i brak przekierowań', body:
`Najpierw allow-lista (jeśli możliwa). Dla dowolnych URL-i: schemat tylko https, port z listy, rozwiązanie nazwy i sprawdzenie każdego adresu, połączenie bez automatycznych przekierowań, limit czasu i rozmiaru odpowiedzi. Na poziomie infrastruktury: IMDSv2 i egress.`,
    code:
`URI uri = URI.create(userUrl);
if (!"https".equals(uri.getScheme())) throw new SecurityException("tylko https");
int port = uri.getPort() == -1 ? 443 : uri.getPort();
if (port != 443) throw new SecurityException("niedozwolony port");
if (!ALLOWED_HOSTS.contains(uri.getHost())) {                       // allow-lista, gdy tylko się da
    List<InetAddress> addrs = OutboundGuard.resolveAllowed(uri.getHost());   // każdy adres poza zakresami specjalnymi
    // łącz się z addrs.get(0), a nazwę hosta przekaż tylko do SNI/Host (np. własny resolver w kliencie HTTP)
}
HttpClient client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NEVER)                 // przekierowania obsługujesz sam, z ponowną walidacją
        .connectTimeout(Duration.ofSeconds(3)).build();
// AWS: aws ec2 modify-instance-metadata-options --instance-id i-... --http-tokens required --http-put-response-hop-limit 1` },

  { type: 'WHEN_TO_USE', heading: 'Który adres i zakres wybrać', body:
`Ściąga decyzji:`,
    code:
`CEL                                                WYBÓR
usługa dostępna tylko lokalnie (dev, admin)          bind 127.0.0.1 / docker -p 127.0.0.1:port:port
aplikacja w kontenerze                               bind 0.0.0.0 w kontenerze, izolacja siecią i publikacją portów
Actuator / panel admina na serwerze                  management.server.address=127.0.0.1 lub sieć zarządzania + uwierzytelnianie
sieć firmowa / VPC                                   RFC 1918, mniej popularny zakres (np. 10.<losowy>.0.0/16) — unikaj kolizji
dodatkowy CIDR podów w EKS                           100.64.0.0/10 (świadomie: kolidować może z CGNAT/Tailscale)
przykłady w dokumentacji i testach                   192.0.2.0/24, 198.51.100.0/24, 203.0.113.0/24, 2001:db8::/32, example.com
IPv6 wewnętrzne                                      ULA fd<losowe 40 bitów>::/48 (RFC 4193) lub GUA z firewallem
blokada SSRF                                         allow-lista → inaczej: rozwiąż DNS, odrzuć zakresy specjalne, bez przekierowań
filtr brzegowy (bogony)                              odrzucaj źródła: RFC 1918, 127/8, 169.254/16, 100.64/10, 0/8, 224/4, 240/4` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Prywatne RFC 1918: **10/8, 172.16/12 (do 172.31), 192.168/16** — bez NAT nie wyjdą do Internetu.
- Loopback **127/8** i ::1 nie opuszcza hosta; link-local **169.254/16** i fe80::/10 tylko na łączu; **169.254.169.254** = metadane chmury.
- **CGNAT 100.64/10** — wielu klientów pod jednym IP: blokady po IP i logi wymagają ostrożności (port źródłowy, czas).
- **0.0.0.0** przy bind = wszystkie interfejsy; w kontenerze aplikacja musi słuchać na 0.0.0.0, a publikację ograniczaj (\`-p 127.0.0.1:…\`).
- Dokumentacyjne 192.0.2/24, 198.51.100/24, 203.0.113/24, 2001:db8::/32 — do przykładów i testów.
- IPv6: ::, ::1, fe80::/10, fc00::/7 (ULA), 2000::/3 (GUA), ff00::/8, ::ffff:0:0/96.
- **SSRF**: allow-lista, rozwiązanie DNS i sprawdzenie każdego adresu, brak przekierowań, IMDSv2 z hop limitem 1, egress — nigdy porównywanie tekstu.
- Następna lekcja: **tablica routingu i default gateway** — longest prefix match, metryki i konflikty tras z VPN.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const ip of ['172.20.5.5', '172.40.5.5', '100.100.1.1', '169.254.10.20', '127.0.1.1', '192.0.2.44', '198.18.200.1', '239.1.1.1', '11.22.33.44', '0.0.0.0']) {
  const c = classify4(ip);
  ex(`Sklasyfikuj adres ${ip}: do jakiego zakresu należy, czy jest routowalny w Internecie i czy aplikacja z ochroną SSRF powinna pozwolić się z nim połączyć?`, 'Porównaj z tabelą zakresów specjalnych.',
    `${c.name}${c.range !== '—' ? ` (${c.range}, ${c.rfc})` : ''}. ${c.global ? 'Publiczny, routowalny — dozwolony (o ile spełnia allow-listę/inne reguły).' : 'Nie jest zwykłym adresem publicznym — walidator SSRF powinien go zablokować.'}`);
}
for (const ip of ['fe80::a00:27ff:fe4e:66a1', 'fd00:1234::10', '::ffff:10.0.0.1', '2001:db8:1::5', 'ff02::fb']) {
  const c = classify6(ip);
  ex(`Sklasyfikuj adres IPv6 ${ip}.`, 'Prefiksy: fe80::/10, fc00::/7, ::ffff:0:0/96, 2001:db8::/32, ff00::/8.', `${c.name} (${c.range}, ${c.rfc}).${ip.startsWith('::ffff') ? ' To adres IPv4 10.0.0.1 w zapisie IPv6 — dla SSRF traktuj jak 10.0.0.1 (prywatny).' : ''}${ip === 'ff02::fb' ? ' To grupa mDNS (odpowiednik 224.0.0.251).' : ''}`);
}
const more = [
  ['Wykonaj ćwiczenie z planu: uruchom aplikację Spring Boot z server.address=127.0.0.1, potem 0.0.0.0, na hoście i w kontenerze. Dla każdego wariantu sprawdź połączenie z: tego samego hosta, innego kontenera, innego komputera w LAN. Zapisz wynik ss -ltnp.', 'Loopback kontenera ≠ loopback hosta; -p publikuje na 0.0.0.0.', 'Host + 127.0.0.1: tylko lokalnie (ss: 127.0.0.1:8080). Host + 0.0.0.0: lokalnie i z LAN (jeśli firewall pozwala). Kontener + 127.0.0.1: nic z zewnątrz kontenera, nawet z hosta przez -p (połączenie resetowane). Kontener + 0.0.0.0 + -p 8080:8080: host i LAN (Docker omija UFW!). Kontener + 0.0.0.0 + -p 127.0.0.1:8080:8080: tylko host. Inny kontener w tej samej sieci Dockera łączy się po nazwie serwisu niezależnie od -p (o ile aplikacja słucha na 0.0.0.0).'],
  ['Wyjaśnij, dlaczego blokada „startsWith(\\"127.\\")” nie chroni przed SSRF do localhosta. Podaj 5 zapisów, które ją obchodzą.', 'Alternatywne zapisy adresu, DNS, IPv6.', '127.1 (skrót), 2130706433 (dziesiętnie), 0x7f000001 / 0x7f.0.0.1 (hex), 017700000001 (ósemkowo), [::1] i [::ffff:127.0.0.1] (IPv6), localhost, dowolna domena z rekordem A 127.0.0.1 (np. przez DNS rebinding), przekierowanie 302 na http://127.0.0.1. Dlatego parsuj adres biblioteką i sprawdzaj adres po rozwiązaniu DNS.'],
  ['Na serwerze z publicznym IP developer uruchomił docker run -d -p 6379:6379 redis. Na hoście działa UFW z regułą „deny incoming”. Czy Redis jest dostępny z Internetu? Jak to sprawdzić i naprawić?', 'Docker i iptables, publikacja na 0.0.0.0.', 'Tak — Docker dopisuje reguły DNAT/FORWARD w łańcuchach DOCKER, które są przetwarzane przed regułami UFW dla ruchu przekazywanego do kontenera. Sprawdzenie: ss -ltnp (docker-proxy na 0.0.0.0:6379), skan z zewnątrz (nc -vz <ip> 6379), iptables -t nat -L DOCKER. Naprawa: -p 127.0.0.1:6379:6379 lub brak publikacji (dostęp przez sieć Dockera), hasło/ACL Redisa, reguły w łańcuchu DOCKER-USER, firewall dostawcy chmury.'],
  ['Opisz konfigurację IMDSv2 w AWS i wyjaśnij, dlaczego hop limit 1 chroni przed dostępem z kontenerów.', 'PUT po token, TTL pakietu.', 'IMDSv2 wymaga najpierw PUT http://169.254.169.254/latest/api/token z nagłówkiem X-aws-ec2-metadata-token-ttl-seconds, a potem GET z nagłówkiem X-aws-ec2-metadata-token. Wymuszenie: HttpTokens=required. HttpPutResponseHopLimit=1 ustawia TTL odpowiedzi z tokenem na 1 — pakiet nie przeżyje dodatkowego skoku (np. bridge Dockera między kontenerem a hostem), więc kontener za NAT/bridge nie dostanie tokenu. Typowe SSRF (tylko GET, bez kontroli nagłówków) nie zdobędzie tokenu.'],
  ['Twoje API blokuje adres IP po 100 nieudanych logowaniach w 10 minut. Po wdrożeniu użytkownicy mobilni zgłaszają masowe blokady. Wyjaśnij i zaproponuj lepszy mechanizm.', 'CGNAT 100.64.0.0/10.', 'Operatorzy mobilni używają CGNAT — setki/tysiące użytkowników wychodzi z jednego publicznego adresu, więc limit per IP zlicza ich razem. Lepiej: limit per konto (login) i per urządzenie/token, wykładnicze opóźnienia, CAPTCHA po progu, wykrywanie rozproszonych ataków po wzorcach (wiele kont z jednego IP), IP tylko jako jeden z sygnałów z wyższym progiem. Logi: IP + port źródłowy + dokładny czas.'],
  ['Zaprojektuj regułę brzegowego firewalla (bogon filtering) dla ruchu przychodzącego z Internetu i wyjaśnij, co wykrywa.', 'Lista zakresów specjalnych jako źródło.', 'Odrzucaj (i loguj) pakiety przychodzące z Internetu ze źródłem: 0.0.0.0/8, 10.0.0.0/8, 100.64.0.0/10, 127.0.0.0/8, 169.254.0.0/16, 172.16.0.0/12, 192.0.0.0/24, 192.0.2.0/24, 192.168.0.0/16, 198.18.0.0/15, 198.51.100.0/24, 203.0.113.0/24, 224.0.0.0/4, 240.0.0.0/4 oraz własnymi publicznymi prefiksami (pakiet „od siebie” z zewnątrz). Wykrywa spoofing, błędnie skonfigurowane sieci partnerów i część ataków DoS. Uzupełnienie: listy pełnych bogonów (nieprzydzielone prefiksy) od Team Cymru, uRPF.'],
  ['Dlaczego w Kubernetes aplikacja nasłuchująca na 127.0.0.1 nie przechodzi readiness probe, a sidecar w tym samym podzie może się z nią połączyć?', 'Przestrzeń nazw sieci poda.', 'Wszystkie kontenery poda współdzielą jeden network namespace (jeden loopback i jeden adres poda). Sidecar łączy się przez 127.0.0.1 tego wspólnego namespace’u. Kubelet wykonuje probe z węzła na adres IP poda (interfejs eth0 poda), a aplikacja słucha tylko na loopbacku — połączenie odrzucone. Naprawa: bind 0.0.0.0 (izolację zapewniają NetworkPolicy i Service), ewentualnie probe typu exec.'],
  ['Wybierz zakres ULA IPv6 dla firmy zgodnie z RFC 4193 i wyjaśnij, dlaczego Global ID powinien być losowy.', 'fd + 40 bitów losowych = /48.', 'Format: fd00::/8 + 40-bitowy losowy Global ID + 16 bitów Subnet ID → prefiks /48, np. fd5a:3c91:7e04::/48 (40 losowych bitów: 5a3c917e04), podsieci fd5a:3c91:7e04:0001::/64 itd. Losowość minimalizuje ryzyko kolizji przy łączeniu sieci (fuzje firm, VPN między partnerami) — tak jak kolizje 192.168.1.0/24 w IPv4. Nie używaj fd00::/48 „bo prościej”.'],
  ['Wyjaśnij, czym jest DNS rebinding i jak kod walidujący SSRF powinien się przed nim zabezpieczyć.', 'Dwa rozwiązania tej samej nazwy.', 'Atakujący kontroluje domenę z bardzo krótkim TTL: przy walidacji DNS zwraca adres publiczny (walidacja przechodzi), a przy właściwym połączeniu — 127.0.0.1 lub 169.254.169.254. Ochrona: rozwiąż nazwę raz, sprawdź wszystkie adresy i połącz się z tym konkretnym adresem (nazwa tylko w SNI/nagłówku Host); nie polegaj na cache DNS JVM jako mechanizmie bezpieczeństwa; po przekierowaniach waliduj ponownie.'],
  ['Przejrzyj docker-compose.yml typowego projektu (app, postgres, redis, adminer) i popraw sekcje ports, aby w środowisku developerskim nic poza aplikacją nie było dostępne z LAN.', 'Publikacja na 127.0.0.1 lub brak publikacji.', 'app: "127.0.0.1:8080:8080" (lub "8080:8080", jeśli testujesz z telefonu w LAN — świadomie). postgres: "127.0.0.1:5432:5432" albo brak ports (dostęp z app po nazwie serwisu). redis: brak ports lub "127.0.0.1:6379:6379" + hasło. adminer: "127.0.0.1:8081:8080". Weryfikacja: ss -ltnp — żadnych 0.0.0.0 poza świadomie wybranymi.'],
  ['Napisz test jednostkowy JUnit dla OutboundGuard.isInternal sprawdzający po jednym adresie z każdej kategorii (loopback, RFC 1918, link-local, CGNAT, 0/8, multicast, 240/4, IPv6 ::1, ULA, mapowany) oraz 2 adresy publiczne.', '@ParameterizedTest z @CsvSource.', '@ParameterizedTest @CsvSource({"127.0.0.2,true","10.1.1.1,true","172.31.0.1,true","192.168.5.5,true","169.254.169.254,true","100.64.0.1,true","0.1.2.3,true","224.0.0.1,true","250.1.1.1,true","::1,true","fd00::1,true","::ffff:10.0.0.1,true","8.8.8.8,false","2a00:1450:4001::1,false","172.32.0.1,false"}) void classifies(String ip, boolean internal) throws Exception { assertThat(OutboundGuard.isInternal(InetAddress.getByName(ip))).isEqualTo(internal); } — uwaga: InetAddress.getByName z literałem IP nie wykonuje zapytania DNS.'],
  ['Wyjaśnij różnicę między 0.0.0.0 jako adresem nasłuchu, adresem źródłowym i elementem trasy 0.0.0.0/0.', 'Trzy różne znaczenia.', 'Nasłuch (bind): wszystkie lokalne interfejsy (INADDR_ANY). Źródło: host jeszcze nie ma adresu — np. DHCP DISCOVER wysyłany z 0.0.0.0 na 255.255.255.255. Trasa 0.0.0.0/0: prefiks o długości 0 pasujący do każdego adresu — trasa domyślna (używana, gdy nic dłuższego nie pasuje). Jako adres docelowy 0.0.0.0 jest niepoprawny; Linux traktuje połączenie z 0.0.0.0 jak z lokalnym hostem, co bywa wykorzystywane do omijania filtrów.'],
  ['Twoja aplikacja loguje adres klienta jako ::ffff:203.0.113.7, a allow-lista ma wpis 203.0.113.7. Dlaczego dopasowanie zawodzi i jak to naprawić?', 'Gniazdo dual-stack.', 'Serwer słucha na gnieździe IPv6 dual-stack ([::]), więc połączenia IPv4 są widoczne jako adresy IPv4 mapowane (::ffff:203.0.113.7). Porównanie tekstowe z „203.0.113.7” zawodzi. Naprawa: porównuj obiekty InetAddress po normalizacji (Java zwykle zwraca Inet4Address dla adresów mapowanych przy InetAddress.getByName, ale getRemoteAddress z gniazda IPv6 może zwrócić zapis mapowany — konwertuj), albo używaj biblioteki do dopasowania prefiksów (IPAddress, Spring IpAddressMatcher).'],
  ['Wymień trzy zakresy dokumentacyjne IPv4 i zakres IPv6 oraz wyjaśnij, dlaczego testy integracyjne powinny ich używać zamiast „losowych” adresów.', 'RFC 5737, RFC 3849.', '192.0.2.0/24 (TEST-NET-1), 198.51.100.0/24 (TEST-NET-2), 203.0.113.0/24 (TEST-NET-3), IPv6 2001:db8::/32. Nie należą do nikogo i nie są routowane, więc test, który przez pomyłkę wyśle ruch, nie uderzy w cudzą usługę ani nie wycieknie danych; czytelnik od razu widzi, że adres jest przykładowy. Dla domen: example.com/.org/.net (RFC 2606) i TLD .test, .example, .invalid.'],
  ['Opisz, jak wykryć w SIEM próby SSRF na metadane chmury w ruchu aplikacji (źródła danych i reguła).', 'Logi proxy wychodzącego, VPC Flow Logs, CloudTrail/GuardDuty.', 'Źródła: logi proxy egress / service mesh (host docelowy 169.254.169.254, metadata.google.internal), VPC Flow Logs (nie obejmują ruchu do IMDS — dlatego proxy/eBPF na hoście), logi aplikacji (URL-e podawane przez użytkowników), CloudTrail z użyciem kluczy roli instancji spoza instancji (GuardDuty: InstanceCredentialExfiltration). Reguła: proces aplikacyjny łączy się z IMDS, choć normalnie nie powinien, lub żądanie z parametrem URL zawierającym adres specjalny/zakodowany; korelacja z nietypowymi wywołaniami AWS API tą rolą.'],
];
for (const [p, h, s] of more) ex(p, h, s);
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

const quiz = buildQuiz();
writeLesson(process.argv[2], theory, exercises, quiz);
