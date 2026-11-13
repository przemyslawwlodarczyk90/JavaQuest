// Generuje lekcje _ns_00_wprowadzenie/04_JourneyOfOneRequest.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_04_journey_lesson.cjs src/main/resources/content/_ns_00_wprowadzenie/04_JourneyOfOneRequest.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { STEPS, TOTAL } = require('./ns00_04_journey_data.cjs');
const { SHORT } = require('./track_meta.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const chl = (n) => `${String(n).padStart(2, '0')} — ${SHORT[n]}`;
const num = (x) => String(x).replace('.', ',');

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Co się dzieje między Enterem a wierszem w bazie', ...img('ns00-04-journey-map.svg', 'Dwanaście ponumerowanych kart: parsowanie URL, cache i HSTS, DNS, trasa i ARP, handshake TCP, handshake TLS 1.3, żądanie HTTP/2, reverse proxy, Tomcat i filtry, DispatcherServlet i kontroler, HikariCP i protokół PostgreSQL, odpowiedź i droga powrotna; przy każdej numer rozdziału toru.'),
    body:
`Wpisujesz w przeglądarce \`https://api.javaquest.local/users/42\` i po kilkudziesięciu milisekundach widzisz JSON. Pomiędzy Enterem a odpowiedzią dzieje się co najmniej **12 etapów** w kilku systemach: przeglądarce, resolverze DNS, jądrze systemu, sieci, reverse proxy, JVM z Tomcatem i Springiem, puli połączeń i bazie PostgreSQL.

Ta lekcja to **mapa całego toru w jednym żądaniu**. Każdy etap opisujemy krótko i wskazujemy rozdział, który rozwija go szczegółowo. Dzięki temu:
- przy każdej awarii wiesz, **na którym etapie** szukać (DNS? TLS? pula połączeń?),
- rozumiesz, **gdzie mija czas** — i dlaczego zwykle nie w Twoim kodzie,
- jako obrońca widzisz **gdzie powstają dowody**: logi DNS, przepływy sieciowe, logi proxy, logi aplikacji, logi bazy.

Na końcu wykonasz Packet Lab: jedno przechwycenie całej drogi w lab-zero, w którym oznaczysz każdy etap i zmierzysz jego czas.` },

  { type: 'DEFINITION', heading: 'Dwanaście etapów w jednym zdaniu każdy', body:
STEPS.map((s) => `- **${s.n}. ${s.name}** (${s.where}) — ${s.what}. Rozdział ${chl(s.ch)}.`).join('\n') },

  { type: 'ANALOGY', heading: 'Zamówienie w restauracji', body:
`Żądanie HTTP przypomina zamówienie w dużej restauracji:
- **URL** to karta dania: „zupa dnia, stolik 42”.
- **DNS** to recepcja, która mówi, w której sali jest Twój kelner (adres).
- **TCP** to nawiązanie kontaktu z kelnerem: „dzień dobry — dzień dobry — mogę przyjąć zamówienie”.
- **TLS** to ustalenie, że rozmawiacie szeptem w języku, którego inni goście nie rozumieją, i że kelner ma identyfikator restauracji (certyfikat).
- **Reverse proxy** to kierownik sali — przyjmuje zamówienia od wszystkich gości i przekazuje je do właściwej kuchni.
- **Tomcat i filtry** to wejście do kuchni: sprawdzenie, czy zamówienie jest poprawne i czy gość ma prawo je złożyć (uwierzytelnienie).
- **Kontroler i serwis** to kucharz, który wie, co przygotować.
- **HikariCP i baza** to spiżarnia: kucharz nie chodzi do sklepu po każdy składnik (nowe połączenie), tylko bierze z gotowego zapasu (pula połączeń).
- **Odpowiedź** to danie niesione tą samą drogą z powrotem.

Najdłużej często trwa nie gotowanie, tylko **chodzenie** — dojście kelnera (RTT), szukanie sali (DNS), formalności (TLS). Tak samo w sieci: kod Javy wykonuje się w milisekundach, a podróże pakietów w dziesiątkach milisekund.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Wodospad czasu jednego żądania', ...img('ns00-04-waterfall.svg', `Wodospad: dwanaście poziomych pasków w kolejności etapów; najdłuższe to DNS (12 ms), handshake TCP (20 ms) i TLS 1.3 (21 ms); etapy w Javie trwają 1–3 ms, zapytanie do bazy 6 ms, odpowiedź 8 ms; razem około ${num(TOTAL.toFixed(1))} ms.`),
    body:
`Przykładowy pomiar pierwszego żądania (zimny cache DNS, nowe połączenie, RTT do serwera ok. 20 ms):`,
    code:
STEPS.map((s) => `${String(s.n).padStart(2)}. ${s.name.padEnd(34)} ${num(s.ms).padStart(5)} ms   ${s.layer}`).join('\n') + `
    ${'RAZEM'.padEnd(34)} ${num(TOTAL.toFixed(1)).padStart(5)} ms

Drugie żądanie po tym samym połączeniu (keep-alive, DNS w cache): etapy 3–6 znikają
→ ok. ${num((TOTAL - 12 - 1 - 20 - 21).toFixed(1))} ms. Dlatego pule połączeń i HTTP/2 tak bardzo przyspieszają aplikacje.` },

  { type: 'CODE_BASIC', heading: 'Zmierz etapy sam: curl -w i log aplikacji', body:
`Najprostszy pomiar etapów od strony klienta i od strony serwera. Porównanie obu daje odpowiedź na pytanie „sieć czy aplikacja?”.`,
    code:
`# klient: fazy połączenia (wartości narastające w sekundach)
curl -sk -o /dev/null -w 'dns=%{time_namelookup} tcp=%{time_connect} tls=%{time_appconnect} ttfb=%{time_starttransfer} total=%{time_total}\\n' \\
     https://127.0.0.1:8443/api/users/42
# dns=0.000012 tcp=0.000310 tls=0.004120 ttfb=0.015900 total=0.016050   (lab lokalny: RTT ≈ 0)

# serwer: czas obsługi w Spring Boot (application.yml)
# logging.level.org.springframework.web.servlet.DispatcherServlet: DEBUG
# server.tomcat.accesslog.enabled: true
# server.tomcat.accesslog.pattern: '%h %t "%r" %s %b %Dms'     # %D = czas obsługi w ms

# Access log Tomcata:
# 172.20.0.3 [13/Nov/2026:14:02:11 +0100] "GET /api/users/42 HTTP/1.1" 200 58 11ms
# → z 15,9 ms TTFB po stronie klienta 11 ms to aplikacja, reszta to proxy i sieć` },

  { type: 'STEP_BY_STEP', heading: 'Packet Lab: cała droga w jednym przechwyceniu', body:
`Ćwiczenie z planu lekcji, wykonywane w lab-zero (Caddy → Spring Boot → PostgreSQL):
1. **Wyczyść cache** w kontenerze klienta (np. uruchom nowy kontener netshoot) — chcemy zobaczyć DNS i nowe połączenia.
2. **Uruchom przechwytywanie na obu sieciach**: na bridge’u sieci edge (klient ↔ Caddy) i backend (Caddy ↔ app ↔ postgres) — np. dwa kontenery netshoot z \`--net container:caddy\` i \`--net container:app\`, zapis do \`edge.pcap\` i \`backend.pcap\`.
3. **Wygeneruj żądanie**: \`curl -sk https://caddy/api/users/42\` z kontenera klienta w sieci edge.
4. **Zatrzymaj przechwytywanie** i złącz pliki: \`mergecap -w journey.pcap edge.pcap backend.pcap\`.
5. **Oznacz etapy w Wiresharku** (prawy klik → *Packet Comment*):
   - DNS: \`dns\` — zapytanie i odpowiedź o \`caddy\` (resolver Dockera 127.0.0.11; w pcap z interfejsu eth0 może go nie być — zapisz dlaczego),
   - TCP: \`tcp.flags.syn == 1\` — handshake klient → 443 oraz Caddy → app:8080 i app → postgres:5432 (jeśli pula otwiera nowe połączenie),
   - TLS: \`tls.handshake\` — ClientHello z SNI i ALPN,
   - HTTP: żądanie Caddy → app (jawne w sieci backend) i odpowiedź,
   - PostgreSQL: \`pgsql\` — zapytanie i wynik.
6. **Zmierz czas każdego etapu**: \`View → Time Display Format → Seconds Since Previous Displayed Packet\` dla przefiltrowanych pakietów; zapisz tabelę etap → czas.
7. **Porównaj** z \`curl -w\` i access logiem Tomcata. Ile czasu zajęła aplikacja, a ile sieć i TLS?
8. **Powtórz** żądanie w tym samym kontenerze — które etapy zniknęły (cache DNS? keep-alive? pula Hikari)?` },

  { type: 'NOTE', heading: 'Wnętrze JVM: gdzie żądanie czeka w kolejce', ...img('ns00-04-jvm-inside.svg', 'Osiem kart połączonych strzałkami: Tomcat (acceptor i wątek z puli), filtry Spring Security, DispatcherServlet, UserController, UserService z transakcją tylko do odczytu, UserRepository ze Spring Data JPA i Hibernate, HikariCP z otwartym połączeniem, PostgreSQL z wymianą Parse/Bind/Execute.'),
    body:
`Wewnątrz aplikacji żądanie przechodzi przez kilka **pul i kolejek** — każda może stać się wąskim gardłem:`,
    code:
`element              zasób ograniczony                     domyślnie (Spring Boot 3)          objaw wyczerpania
Tomcat               wątki obsługi (server.tomcat.threads.max)  200 (lub wątki wirtualne)       rośnie TTFB, kolejka połączeń
                     kolejka akceptora (accept-count)           100                               odrzucanie połączeń
Spring Security      CPU (np. hashowanie haseł przy logowaniu)  —                                 wolne logowanie
HikariCP             połączenia (maximum-pool-size)             10                                SQLTransientConnectionException
                     czas oczekiwania (connection-timeout)      30 s                              długie wiszenie wątków
PostgreSQL           max_connections, blokady, I/O              100                               „too many clients”, wolne zapytania
Klient HTTP (dalsze  pula połączeń, timeouty                    zależnie od klienta               timeouty, kaskady awarii
wywołania)

Prawo Little’a (rozdz. 01): 300 żądań/s × 0,05 s w bazie = 15 połączeń zajętych jednocześnie
→ pula 10 połączeń za mała przy tym ruchu.` },

  { type: 'CODE_PRACTICAL', heading: 'Ten sam przepływ w kodzie Spring Boot', body:
`Minimalny kod endpointu z lab-zero. Komentarze wskazują, który etap podróży obsługuje dana linijka i gdzie zostaje ślad dla obrońcy.`,
    code:
`@RestController
@RequestMapping("/api/users")
class UserController {                                   // etap 10: DispatcherServlet → kontroler
    private final UserService service;
    UserController(UserService service) { this.service = service; }

    @GetMapping("/{id}")
    ResponseEntity<UserDto> get(@PathVariable @Positive long id, Authentication auth) {   // etap 9: filtry ustawiły auth
        log.info("user lookup id={} by={}", id, auth.getName());   // ślad: log aplikacji (kto, co, kiedy)
        return service.find(id)
            .map(ResponseEntity::ok)                      // etap 12: Jackson → JSON
            .orElse(ResponseEntity.notFound().build());
    }
}

@Service
class UserService {
    private final UserRepository repo;
    UserService(UserRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)                       // etap 11: połączenie z puli HikariCP
    Optional<UserDto> find(long id) {
        return repo.findById(id).map(UserDto::from);      // SELECT ... FROM users WHERE id = ? (parametr, nie konkatenacja)
    }
}

# application.yml — widoczność etapów
server.tomcat.accesslog.enabled: true                     # etap 9: czas obsługi i status
spring.datasource.hikari.leak-detection-threshold: 2000   # etap 11: połączenia trzymane > 2 s
management.endpoints.web.exposure.include: health,metrics # metryki pul: hikaricp.connections.*, tomcat.threads.*` },

  { type: 'USAGE', heading: 'Ta sama droga oczami obrońcy: gdzie powstają dowody', ...img('ns00-04-failure-map.svg', 'Tabela dwunastu etapów z typowym błędem i rozdziałem: m.in. DNS — UnknownHostException (rozdział 05), TCP — Connection refused (04), TLS — PKIX i wygasły certyfikat (07), reverse proxy — 502, 503, 504 (11), Tomcat i filtry — 401, 403 i wyczerpana pula (12), HikariCP — SQLTransientConnectionException (12).'),
    body:
`Każdy etap to nie tylko potencjalna awaria, ale też **źródło telemetrii**. W incydencie analityk SOC składa historię z tych właśnie śladów:
- **DNS (etap 3)** — logi resolvera: która maszyna pytała o jaką domenę. Najlepsze źródło do wykrywania C2, phishingu i tunelowania (rozdziały 28, 30).
- **Sieć (etapy 4–5)** — NetFlow/VPC Flow Logs, logi firewalla: kto z kim się łączył, ile danych (rozdział 28).
- **TLS (etap 6)** — SNI, certyfikaty, odciski JA4: nietypowi klienci i serwery (rozdział 28).
- **Reverse proxy / WAF (etap 8)** — pełne żądania HTTP: ścieżki, kody, czasy, adres klienta (X-Forwarded-For), blokady WAF (rozdziały 11, 21, 23).
- **Aplikacja (etapy 9–10)** — logi uwierzytelnienia i autoryzacji, zdarzenia bezpieczeństwa (OWASP A09), identyfikator żądania (rozdziały 16, 23, 27).
- **Baza (etap 11)** — logi zapytań i audytu: kto czytał jakie dane (rozdział 25 i 38).

**Korelacja** — identyfikator żądania (\`traceparent\` z W3C Trace Context, \`X-Request-ID\`) przekazywany od proxy przez aplikację do logów bazy pozwala połączyć te ślady w jedną oś czasu. To ta sama technika, którą ćwiczyłeś w Bossie rozdziału 01.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędne założenia o drodze żądania', body:
`- **„Wolne żądanie = wolny kod”** — w pierwszym żądaniu większość czasu to DNS, TCP i TLS. Profiluj dopiero, gdy wiesz, że czas spędzany jest w aplikacji (access log, tracing).
- **„DNS zawsze działa”** — cache w JVM, w kontenerze (resolver Dockera 127.0.0.11), w systemie i w przeglądarce może podawać stare adresy po zmianie rekordu.
- **„Aplikacja widzi adres klienta”** — za proxy widzi adres proxy; prawdziwy klient jest w X-Forwarded-For (tylko od zaufanego proxy).
- **„Połączenie do bazy jest za darmo”** — nowe połączenie to TCP + TLS + uwierzytelnienie PostgreSQL (dziesiątki ms). Dlatego pula.
- **„Większa pula = szybciej”** — zbyt duża pula przenosi kolejkę do bazy (max_connections, blokady) i pogarsza wydajność.
- **„HTTPS kończy się w aplikacji”** — często TLS kończy się na proxy, a ruch proxy → aplikacja jest jawny w sieci wewnętrznej. To decyzja architektoniczna z konsekwencjami dla bezpieczeństwa (rozdział 07: mTLS).
- **„Każdy etap ma ten sam timeout”** — proxy (np. 60 s), klient (30 s) i baza (statement timeout) muszą mieć spójne limity; inaczej klient dostaje 504, a zapytanie nadal zajmuje bazę.` },

  { type: 'CODE_WRONG', heading: 'Źle: brak timeoutów i nowe połączenie przy każdym wywołaniu', body:
`Klient wywołujący inną usługę w każdym żądaniu tworzy nowego klienta HTTP (nowe połączenie TCP + TLS), bez timeoutów. Przy awarii zależności wątki Tomcata wiszą, a pula się wyczerpuje.`,
    code:
`@GetMapping("/orders/{id}")
OrderView order(@PathVariable long id) throws Exception {
    HttpClient client = HttpClient.newHttpClient();                       // nowy klient = nowe połączenia, brak puli
    HttpRequest req = HttpRequest.newBuilder(URI.create("https://users.internal/api/users/" + id)).build();
    String body = client.send(req, HttpResponse.BodyHandlers.ofString()).body();   // brak timeoutu odczytu
    return OrderView.of(repo.findById(id).orElseThrow(), body);
}
// Efekt: każdy request płaci DNS + TCP + TLS; przy zawieszonym users.internal wątek czeka w nieskończoność.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: jeden klient, pula, timeouty i identyfikator żądania', body:
`Klient współdzielony (pula i keep-alive), jawne timeouty spójne z timeoutem proxy, propagacja identyfikatora żądania do zależności.`,
    code:
`@Configuration
class HttpConfig {
    @Bean
    RestClient usersClient(RestClient.Builder builder) {
        var factory = new JdkClientHttpRequestFactory(HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))       // etap 5: nie czekamy wiecznie na SYN-ACK
                .build());                                   // jeden klient = pula połączeń i keep-alive
        factory.setReadTimeout(Duration.ofSeconds(3));       // krócej niż timeout proxy (np. 10 s)
        return builder.baseUrl("https://users.internal")
                .requestFactory(factory)
                .requestInterceptor((req, body, exec) -> {   // korelacja w logach wszystkich usług
                    String id = MDC.get("requestId");
                    if (id != null) req.getHeaders().set("X-Request-ID", id);
                    return exec.execute(req, body);
                })
                .build();
    }
}
// Dodatkowo: micrometer-tracing (traceparent) i circuit breaker (rozdz. 12).` },

  { type: 'WHEN_TO_USE', heading: 'Jak korzystać z tej mapy przy awarii i incydencie', body:
`- **Awaria „nie działa”** — zacznij od komunikatu błędu i przypisz go do etapu (mapa błędów). \`UnknownHostException\` → etap 3, \`Connection refused\` → 5, \`PKIX\` → 6, \`502/504\` → 8, \`401/403\` → 9, \`SQLTransientConnectionException\` → 11.
- **Awaria „wolno działa”** — zmierz etapy: \`curl -w\` (klient), access log proxy i Tomcata (serwer), metryki pul (\`tomcat.threads.busy\`, \`hikaricp.connections.pending\`), tracing.
- **Incydent bezpieczeństwa** — dla każdego etapu zapytaj: jaki ślad zostawił napastnik i gdzie go zbieramy? Braki w logach na którymś etapie to luka w widoczności do naprawienia.
- **Projekt nowej usługi** — przejdź mapę i dla każdego etapu zdecyduj: timeouty, pule, szyfrowanie, logowanie, kontrola dostępu.
- **Nauka** — gdy któryś etap jest dla Ciebie „czarną skrzynką”, idź do wskazanego rozdziału.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Jedno żądanie HTTPS przechodzi przez ok. 12 etapów: URL, cache/HSTS, DNS, trasa i ARP, TCP, TLS, HTTP/2, reverse proxy, Tomcat i filtry, kontroler, pula połączeń i baza, odpowiedź.
- W pierwszym żądaniu najwięcej czasu zajmują DNS i rundy TCP/TLS (RTT); kolejne żądania korzystają z cache i keep-alive.
- Wewnątrz JVM żądanie czeka w pulach (wątki Tomcata, HikariCP) — tam rodzą się przeciążenia (prawo Little’a).
- Każdy etap ma typowy błąd i własny rozdział toru — to mapa do diagnozy.
- Każdy etap zostawia ślady (DNS, przepływy, TLS, proxy, aplikacja, baza) — dla obrońcy to mapa źródeł telemetrii, łączonych identyfikatorem żądania.
- Następna lekcja: **mapa certyfikatów** — jak tor przekłada się na Network+, CCNA, Security+, CySA+ i egzaminy praktyczne.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
for (const s of STEPS) {
  ex(`Etap ${s.n} — „${s.name}”: opisz, co się dzieje, gdzie (który system) i jaki jest typowy błąd na tym etapie. Wskaż rozdział toru, który go rozwija.`, 'Skorzystaj z mapy podróży i mapy błędów z lekcji.', `${s.what}. Gdzie: ${s.where}. Typowy błąd: ${s.err}. Rozdział ${chl(s.ch)}.`);
}
const diag = [
  ['Użytkownik zgłasza: „strona ładuje się 3 sekundy tylko przy pierwszym wejściu rano”. curl -w pokazuje dns=2.8.', 'Etap 3 (DNS): powolne rozwiązywanie nazwy przy zimnym cache — np. timeout pierwszego resolvera i przejście na drugi, długi łańcuch CNAME, problem z IPv6 (AAAA). Diagnoza: dig +trace, sprawdzenie resolverów (/etc/resolv.conf, resolvectl), czasów odpowiedzi; rozdział 05.'],
  ['Aplikacja zwraca 504 Gateway Timeout po dokładnie 60 s, a w logach aplikacji zapytanie kończy się po 75 s.', 'Etap 8 (proxy) i 11 (baza): proxy ma timeout 60 s, a zapytanie do bazy trwa 75 s — klient dostaje 504, aplikacja dalej pracuje. Naprawa: optymalizacja zapytania, timeouty spójne (statement timeout < read timeout < timeout proxy), ewentualnie praca asynchroniczna.'],
  ['Po wdrożeniu nowego certyfikatu część klientów Java zgłasza PKIX path building failed, a przeglądarki działają.', 'Etap 6 (TLS): serwer prawdopodobnie nie wysyła certyfikatu pośredniego — przeglądarki potrafią go dociągnąć (AIA fetching/cache), JVM nie. Diagnoza: openssl s_client -showcerts; naprawa: pełny łańcuch w konfiguracji serwera. Rozdział 07.'],
  ['W szczycie ruchu pojawia się SQLTransientConnectionException: Connection is not available, request timed out after 30000ms.', 'Etap 11 (HikariCP): wszystkie połączenia z puli są zajęte (wolne zapytania, wycieki połączeń, za mała pula względem ruchu). Diagnoza: metryki hikaricp.connections.active/pending, leak-detection-threshold, wolne zapytania w PostgreSQL (pg_stat_activity). Rozdział 12.'],
  ['Analityk SOC widzi w logach WAF żądanie z adresu 83.10.5.7 próbujące SQL injection. Skąd wiadomo, który użytkownik firmy (jeśli to wewnętrzny) je wysłał?', 'Ślady z etapów: log WAF/proxy (adres i czas), logi NAT/firewalla (translacja na adres wewnętrzny), DHCP (adres → urządzenie), log aplikacji z identyfikatorem żądania i ewentualnie zalogowanym użytkownikiem. Korelacja czasem (NTP) i identyfikatorami — jak w Bossie rozdziału 01.'],
];
for (const [q, a] of diag) ex(`Diagnoza na mapie podróży: ${q} Wskaż etap i uzasadnij.`, 'Przypisz objaw do etapu, potem wybierz narzędzie.', a);
ex('Wykonaj Packet Lab z lekcji w lab-zero i przygotuj tabelę: etap → filtr Wiresharka → numer pierwszej ramki → czas trwania. Których etapów nie widać w przechwyceniu i dlaczego?', 'Pomyśl o DNS przez 127.0.0.11, TLS kończonym na Caddy i połączeniach z puli otwartych wcześniej.', 'Nie widać zwykle: etapów 1–2 (przeglądarka/curl, bez ruchu), ARP (jeśli cache ciepły), DNS (resolver Dockera na pętli zwrotnej kontenera — widoczny tylko na interfejsie lo), nowego połączenia do PostgreSQL (Hikari otworzył je przy starcie). Widać: TCP i TLS klient→Caddy, HTTP Caddy→app (jawne), zapytanie pgsql app→postgres.');
ex('Porównaj czasy pierwszego i drugiego żądania z tego samego klienta (curl z --keepalive w jednej sesji lub dwa żądania w jednym wywołaniu curl). Które etapy zniknęły?', 'curl https://... https://... w jednym poleceniu używa tego samego połączenia.', 'Drugie żądanie nie ma etapów 3–6 (DNS z cache, połączenie TCP i sesja TLS ponownie użyte) — czas spada do kilku ms (proxy + aplikacja + baza). To praktyczne uzasadnienie dla pul połączeń, HTTP keep-alive i HTTP/2.');
ex('Włącz access log Tomcata ze wzorcem %D i porównaj czas obsługi aplikacji z TTFB z curl -w dla 10 żądań. Ile milisekund zajmuje „otoczenie” (proxy, sieć, TLS)?', 'TTFB klienta − czas aplikacji ≈ narzut proxy, sieci i TLS.', 'Przykład w lab-zero: TTFB 15,9 ms, %D = 11 ms → ok. 5 ms narzutu (TLS na Caddy, przekazanie do aplikacji). W środowisku rozproszonym (inny region) różnica rośnie o kilka RTT. Ćwiczenie uczy rozdzielania odpowiedzialności: sieć vs aplikacja.');
ex('Dodaj do aplikacji lab-zero filtr ustawiający X-Request-ID w MDC i odpowiedzi, a w Caddy włącz przekazywanie nagłówka. Znajdź jedno żądanie w logach Caddy i Spring Boot po identyfikatorze.', 'Caddy: header_up X-Request-ID {http.request.uuid} (lub przekazanie istniejącego).', 'W Caddyfile: reverse_proxy app:8080 { header_up X-Request-ID {http.request.uuid} }. W aplikacji: filtr z lekcji Bossa rozdziału 01 (MDC.put("requestId", ...)) i wzorzec logu z %X{requestId}. Wynik: ten sam identyfikator w logu dostępu Caddy i w logach aplikacji — gotowa korelacja.');
ex('Zaprojektuj spójne timeouty dla łańcucha: przeglądarka → ALB (timeout bezczynności 60 s) → Spring Boot → zależność HTTP → PostgreSQL. Podaj wartości i uzasadnij kolejność.', 'Wewnętrzne limity muszą być krótsze niż zewnętrzne.', 'Przykład: statement_timeout PostgreSQL 5 s; HikariCP connection-timeout 2 s; klient HTTP do zależności: connect 1 s, read 3 s; całkowity czas obsługi żądania w aplikacji (deadline) ok. 8 s; ALB 60 s (zostaje zapas na wolne pobierania). Zasada: każdy element niżej w łańcuchu kończy się wcześniej niż wyżej, by błąd wrócił jako kontrolowana odpowiedź, a nie 504 z proxy.');
ex('Dla każdego z 12 etapów wskaż jedno źródło telemetrii przydatne dla SOC i czy w Twoim środowisku (praca lub lab) jest ono zbierane.', 'Zrób tabelę: etap, źródło, zbierane tak/nie.', 'Przykład: DNS — logi resolvera (często NIE zbierane — luka!); sieć — VPC Flow Logs/NetFlow; TLS — logi proxy z SNI lub Zeek ssl.log; proxy — access log; aplikacja — logi uwierzytelnienia i zdarzeń bezpieczeństwa; baza — log audytu (pgaudit). Etapy bez zbieranych śladów to priorytety do uzupełnienia.');
ex('Wyjaśnij na przykładzie tej podróży, dlaczego zmiana rekordu DNS (migracja serwera) może nie zadziałać od razu dla działającej aplikacji Java.', 'Cache na kilku poziomach i otwarte połączenia.', 'Aplikacja ma otwarte połączenia (keep-alive, pule) do starego adresu — będą używane, dopóki się nie zamkną; JVM cache’uje wyniki DNS (networkaddress.cache.ttl, domyślnie 30 s bez SecurityManagera, ale konfiguracje bywają inne); resolver systemu i serwery DNS respektują TTL rekordu. Rozwiązanie: niskie TTL przed migracją, ograniczony czas życia połączeń w pulach (maxLifetime w Hikari, max-life w klientach HTTP).');
ex('Zrób „mapę podróży” dla jednego endpointu w swojej aplikacji (praca lub projekt): wypisz wszystkie etapy od klienta do bazy i zależności zewnętrznych, z timeoutami i miejscami logowania.', 'Uwzględnij CDN, WAF, LB, ingress, sidecary, kolejki.', 'Przykład: przeglądarka → CDN (cache, WAF) → ALB (60 s) → ingress NGINX (proxy-read-timeout 60 s) → pod Spring Boot (Tomcat 200 wątków) → Redis (cache, timeout 200 ms) → PostgreSQL (Hikari 20, statement 5 s) → zewnętrzne API płatności (connect 1 s, read 5 s, circuit breaker). Logi: CDN, ALB w S3, ingress, aplikacja (JSON + traceId), PostgreSQL (wolne zapytania).');
ex('Policz, ile czasu zajęłoby 20 sekwencyjnych wywołań tego samego endpointu z nowym połączeniem za każdym razem (bez keep-alive), a ile z jednym połączeniem, przy RTT 20 ms i czasie aplikacji 10 ms.', 'Nowe połączenie: DNS (z cache ≈ 0) + TCP 1 RTT + TLS 1 RTT + żądanie 1 RTT + aplikacja.', 'Bez keep-alive (DNS w cache): 20 × (20 + 20 + 20 + 10) = 20 × 70 = 1400 ms. Z jednym połączeniem: 40 ms na TCP + TLS raz, potem 20 × (20 + 10) = 600 ms → razem ok. 640 ms. Ponowne użycie połączeń skraca czas ponad dwukrotnie.');
ex('Sprawdź metryki pul w lab-zero przez Actuator: /actuator/metrics/hikaricp.connections.active, hikaricp.connections.pending, tomcat.threads.busy (jeśli dostępne). Wygeneruj obciążenie (np. 50 równoległych curl) i obserwuj zmiany.', 'Do obciążenia: seq 1 50 | xargs -P 50 -I{} curl -sk -o /dev/null https://127.0.0.1:8443/api/users/42', 'Przy obciążeniu active rośnie do maximum-pool-size (10), a pending > 0 oznacza, że wątki czekają na połączenie. Przy szybkich zapytaniach pending pozostaje bliskie 0. Ćwiczenie pokazuje, gdzie powstaje kolejka (prawo Little’a) i jak ją obserwować metrykami.');
ex('Wymień trzy miejsca na drodze żądania, w których mogą zostać ujawnione dane wrażliwe (np. token, dane osobowe), i zaproponuj zabezpieczenie dla każdego.', 'Myśl o logach, ruchu jawnym i cache.', '1) Logi proxy/aplikacji z pełnymi nagłówkami (Authorization, Cookie) — maskowanie, nielogowanie nagłówków wrażliwych. 2) Ruch jawny proxy → aplikacja lub aplikacja → baza — TLS/mTLS w sieci wewnętrznej, sslmode=verify-full w JDBC. 3) Cache HTTP (CDN, przeglądarka) z odpowiedziami zawierającymi dane osobowe — nagłówki Cache-Control: no-store dla danych prywatnych.');
ex('Na podstawie mapy podróży napisz checklistę „pierwsze 5 minut” dla dyżurnego przy zgłoszeniu „API nie działa”.', 'Od najszybszych testów do najbardziej szczegółowych.', '1) curl -v na endpoint health z zewnątrz (czy DNS, TCP, TLS, HTTP działają — komunikat błędu wskazuje etap). 2) Status i logi proxy/LB (502/503/504?). 3) Status aplikacji: health, metryki pul wątków i Hikari. 4) Zależności: baza (pg_isready, wolne zapytania), API zewnętrzne. 5) Ostatnie zmiany (wdrożenia, certyfikaty, DNS, reguły firewalla). Zapisuj czas i wyniki w kanale incydentu.');
ex('Wyjaśnij, czym różni się podróż żądania przez HTTP/1.1 od HTTP/3 (QUIC) na etapach 5–7.', 'QUIC działa nad UDP i łączy transport z TLS 1.3.', 'HTTP/1.1 i HTTP/2: TCP handshake (1 RTT) + TLS 1.3 (1 RTT), potem żądanie. HTTP/3: QUIC nad UDP łączy nawiązanie transportu i TLS 1.3 w jednej wymianie (1 RTT, 0-RTT przy wznowieniu), bez osobnego handshake’u TCP; strumienie są niezależne, więc utrata pakietu w jednym nie blokuje innych. Dla obrońcy: inny wzorzec ruchu (UDP/443), wymaga innych reguł i narzędzi analizy.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111404);
for (let i = 0; i < STEPS.length - 1; i++) {
  const s = STEPS[i], nxt = STEPS[i + 1];
  add(`Który etap następuje po „${s.name}” w podróży żądania?`, nxt.name, STEPS.filter((x) => x !== nxt && x !== s).map((x) => x.name).slice(i % 5, i % 5 + 3), `Kolejność: … ${s.name} → ${nxt.name} …`);
}
for (const s of STEPS) {
  const wrong = [s.ch + 1, s.ch - 1, s.ch + 2, s.ch - 2, s.ch - 3, s.ch + 5].filter((x) => x >= 1 && x <= 12 && x !== s.ch).slice(0, 3).map(chl);
  add(`W którym rozdziale toru szczegółowo omówiono etap „${s.name}”?`, chl(s.ch), wrong, `Etap ${s.n} rozwija rozdział ${chl(s.ch)}.`);
}
const errs = [
  ['UnknownHostException', 'Rozwiązanie nazwy (DNS)'], ['Connection refused', 'Handshake TCP'], ['PKIX path building failed', 'Handshake TLS 1.3'], ['502 Bad Gateway', 'Reverse proxy'],
  ['403 Forbidden z Spring Security', 'Tomcat i filtry'], ['SQLTransientConnectionException', 'HikariCP i protokół PostgreSQL'], ['No route to host', 'Trasa i ARP'], ['404 dla nieistniejącego mapowania', 'DispatcherServlet i kontroler'],
  ['connect timed out', 'Handshake TCP'], ['certificate has expired', 'Handshake TLS 1.3'], ['504 Gateway Timeout', 'Reverse proxy'], ['błąd serializacji JSON (HttpMessageNotWritableException)', 'Odpowiedź i droga powrotna'],
];
const stepNames = STEPS.map((s) => s.name);
for (const [e, st] of errs) add(`Na którym etapie podróży powstaje błąd „${e}”?`, st, stepNames.filter((x) => x !== st).slice(0, 3).concat(stepNames.filter((x) => x !== st).slice(5, 6)).slice(0, 3), `${e} → etap „${st}”.`);
const facts = [
  ['Które etapy znikają przy drugim żądaniu w tym samym połączeniu keep-alive?', 'DNS, handshake TCP i handshake TLS', ['Tylko parsowanie URL', 'Zapytanie do bazy', 'Serializacja JSON'], 'Połączenie i sesja TLS są ponownie używane, a adres jest w cache.'],
  ['Dlaczego aplikacja korzysta z puli połączeń HikariCP?', 'Bo nowe połączenie do bazy wymaga TCP, często TLS i uwierzytelnienia — to kosztowne', ['Bo PostgreSQL nie obsługuje nowych połączeń', 'Bo pula szyfruje zapytania', 'Bo bez puli nie da się użyć JPA'], 'Pula utrzymuje gotowe połączenia.'],
  ['Jaki jest domyślny rozmiar puli HikariCP w Spring Boot?', '10', ['100', '1', '200'], 'maximum-pool-size domyślnie 10.'],
  ['Jaka jest domyślna maksymalna liczba wątków Tomcata w Spring Boot?', '200', ['10', '1000', '50'], 'server.tomcat.threads.max = 200 (przy wątkach platformowych).'],
  ['Co oznacza %D we wzorcu access logu Tomcata?', 'Czas obsługi żądania w milisekundach', ['Datę żądania', 'Rozmiar odpowiedzi', 'Adres klienta'], 'Pozwala oddzielić czas aplikacji od czasu sieci.'],
  ['Co jest najlepszym kluczem do połączenia logów proxy, aplikacji i bazy dla jednego żądania?', 'Identyfikator żądania (X-Request-ID / traceparent)', ['Adres MAC', 'Numer portu 443', 'Nazwa hosta proxy'], 'Propagowany przez wszystkie warstwy.'],
  ['Dlaczego w pierwszym żądaniu kod Javy rzadko jest największym kosztem czasu?', 'Bo dominują rundy sieciowe: DNS, handshake TCP i TLS', ['Bo Java jest kompilowana', 'Bo baza zawsze odpowiada w 0 ms', 'Bo HTTP/2 nie ma narzutu'], 'Każda runda to co najmniej jedno RTT.'],
  ['Gdzie zwykle kończy się TLS w architekturze z reverse proxy?', 'Na reverse proxy (chyba że skonfigurowano TLS lub mTLS do backendu)', ['Zawsze w bazie danych', 'Na przełączniku', 'W przeglądarce'], 'Ruch proxy → aplikacja bywa jawny w sieci wewnętrznej.'],
  ['Co oznacza ALPN h2 w ClientHello?', 'Klient proponuje HTTP/2 jako protokół aplikacyjny w tym połączeniu TLS', ['Wersję TLS 2', 'Drugi certyfikat', 'Port 2'], 'Negocjacja protokołu odbywa się w handshake’u TLS.'],
  ['Co sprawdza przeglądarka w etapie „Cache i HSTS”?', 'Czy ma aktualną odpowiedź w cache i czy domena wymaga HTTPS', ['Hasło użytkownika', 'Adres MAC serwera', 'Wersję Javy'], 'HSTS wymusza HTTPS bez próby HTTP.'],
  ['Jaki adres ma resolver DNS widziany przez kontenery w sieciach użytkownika Dockera?', '127.0.0.11', ['8.8.8.8', '127.0.0.53', '10.0.0.1'], 'Wbudowany DNS Dockera w przestrzeni nazw kontenera.'],
  ['Które źródło telemetrii jest szczególnie cenne do wykrywania C2 i tunelowania?', 'Logi zapytań DNS', ['Logi kompilacji Maven', 'Metryki CPU', 'Pliki README'], 'Etap 3 — każda komunikacja zaczyna się od nazwy.'],
  ['Dlaczego timeout odczytu klienta HTTP w aplikacji powinien być krótszy niż timeout proxy?', 'By aplikacja zwróciła kontrolowany błąd, zanim proxy zwróci klientowi 504', ['Bo proxy nie ma timeoutów', 'Bo timeouty są zawsze równe', 'Bo krótszy timeout szyfruje dane'], 'Spójne limity od dołu do góry łańcucha.'],
  ['Co oznacza SQLTransientConnectionException w HikariCP?', 'Brak wolnego połączenia w puli w czasie connection-timeout', ['Błąd składni SQL', 'Wygasły certyfikat bazy', 'Brak sieci'], 'Typowo: wolne zapytania, wycieki połączeń, za mała pula.'],
  ['Dlaczego tworzenie nowego HttpClient przy każdym żądaniu jest złą praktyką?', 'Bo za każdym razem płacimy DNS, TCP i TLS, tracąc pulę i keep-alive', ['Bo HttpClient nie działa wielowątkowo', 'Bo to zabronione w Javie', 'Bo zużywa GPU'], 'Klienta tworzy się raz i współdzieli.'],
  ['Czego dotyczy parametr leak-detection-threshold w HikariCP?', 'Wykrywania połączeń trzymanych dłużej niż próg (potencjalne wycieki)', ['Szyfrowania haseł', 'Wielkości puli wątków Tomcata', 'Czasu życia certyfikatu'], 'Loguje ostrzeżenie ze stosem wywołań.'],
  ['Co jest pierwszym krokiem przy zgłoszeniu „aplikacja jest wolna”?', 'Zmierzenie, który etap zajmuje czas (curl -w, access logi, metryki pul)', ['Zwiększenie RAM serwera', 'Zakup szybszego łącza', 'Przepisanie aplikacji'], 'Diagnoza przed optymalizacją.'],
  ['W którym etapie Spring Security ustala tożsamość użytkownika?', 'Tomcat i filtry', ['DispatcherServlet i kontroler', 'HikariCP i protokół PostgreSQL', 'Rozwiązanie nazwy (DNS)'], 'Łańcuch filtrów przed DispatcherServlet.'],
  ['Dlaczego zapytanie JPA findById jest bezpieczne przed SQL injection?', 'Bo identyfikator trafia jako parametr zapytania, a nie przez sklejanie tekstu SQL', ['Bo PostgreSQL nie obsługuje injection', 'Bo id jest liczbą', 'Bo działa w transakcji'], 'Parametryzacja oddziela kod od danych.'],
  ['Ile rund (RTT) dokłada w przybliżeniu handshake TCP i TLS 1.3 przy nowym połączeniu?', '2 (1 RTT TCP + 1 RTT TLS 1.3)', ['0', '5', '10'], 'TLS 1.2 dokładał 2 RTT; TLS 1.3 — 1 RTT (0-RTT przy wznowieniu z ograniczeniami).'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
const lay = [['Handshake TCP', 'L4'], ['Handshake TLS 1.3', 'L6'], ['Trasa i ARP', 'L2–L3'], ['Rozwiązanie nazwy (DNS)', 'L7 (DNS)'], ['Reverse proxy', 'L7']];
const layAll = ['L4', 'L6', 'L2–L3', 'L7 (DNS)', 'L7', 'L1'];
for (const [st, l] of lay) add(`Której warstwy (w uproszczeniu) dotyczy etap „${st}”?`, l, layAll.filter((x) => x !== l).slice(0, 3), `${st}: ${l}.`);
const tel = [['Rozwiązanie nazwy (DNS)', 'Logi zapytań resolvera DNS'], ['Handshake TCP', 'NetFlow / VPC Flow Logs i logi firewalla'], ['Reverse proxy', 'Access log proxy i logi WAF'], ['Tomcat i filtry', 'Logi uwierzytelnienia i autoryzacji aplikacji'], ['HikariCP i protokół PostgreSQL', 'Logi audytu i wolnych zapytań bazy']];
const telAll = tel.map((x) => x[1]);
for (const [st, t] of tel) add(`Jakie źródło dowodów dla SOC odpowiada etapowi „${st}”?`, t, telAll.filter((x) => x !== t).slice(0, 3), `${st}: ${t.toLowerCase()}.`);
const whereAll = [...new Set(STEPS.map((x) => x.where))];
for (const st of STEPS) add(`Który system lub komponent obsługuje etap „${st.name}”?`, st.where, whereAll.filter((x) => x !== st.where).slice(st.n % 4, st.n % 4 + 3).concat(whereAll.filter((x) => x !== st.where)).filter((x, i, a) => a.indexOf(x) === i).slice(0, 3), `${st.name}: ${st.where}.`);
const whatAll = STEPS.map((x) => x.what);
for (const st of STEPS) add(`Co dzieje się na etapie „${st.name}”?`, st.what, whatAll.filter((x) => x !== st.what).slice((st.n * 2) % 9, (st.n * 2) % 9 + 3), `${st.name}: ${st.what}.`);
for (const [n, rtt, app] of [[10, 20, 10], [50, 5, 2], [5, 100, 20], [100, 1, 1], [20, 50, 5]]) {
  const noKa = n * (3 * rtt + app), ka = 2 * rtt + n * (rtt + app);
  add(`Ile trwa ${n} sekwencyjnych żądań HTTPS przy RTT ${rtt} ms i czasie aplikacji ${app} ms, jeśli KAŻDE otwiera nowe połączenie (TCP + TLS 1.3, DNS w cache)?`, `${noKa} ms`, [`${ka} ms`, `${n * (rtt + app)} ms`, `${noKa * 2} ms`], `Każde żądanie: 1 RTT TCP + 1 RTT TLS + 1 RTT żądanie + aplikacja = ${3 * rtt + app} ms; razem ${n} × ${3 * rtt + app} = ${noKa} ms. Z keep-alive: ${ka} ms.`);
}
for (const [rps, ms] of [[200, 50], [1000, 20], [50, 400], [500, 100], [80, 250], [300, 30]]) {
  const need = rps * ms / 1000;
  add(`Usługa obsługuje ${rps} żądań/s, a każde trzyma połączenie z bazy przez ${ms} ms. Ile połączeń jest średnio zajętych jednocześnie?`, String(need), [String(need * 2), String(rps), String(Math.max(1, need / 2))].filter((x) => x !== String(need)).concat([String(need + 10)]), `Prawo Little’a: ${rps} × ${ms / 1000} s = ${need}. Pula musi to pomieścić z zapasem.`);
}
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
