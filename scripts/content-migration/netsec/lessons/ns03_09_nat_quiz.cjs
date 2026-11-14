// Quiz (100 pytan) do lekcji _ns_03/09_NatPatAndPortForwarding.
const { quizBuilder } = require('./lib.cjs');
const { pat, fmtFlow, steadyState, ipsNeeded, AWS_NAT_PER_DEST } = require('./ns03_09_nat_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111409);
  const fmt = (n) => n.toLocaleString('pl-PL');

  // 1) Liczenie zajetosci i adresow (16)
  const cases = [[200, 60], [500, 60], [900, 60], [1000, 60], [1200, 60], [2000, 60], [3000, 60], [100, 350], [400, 120], [5000, 30], [250, 240], [1500, 90], [700, 100], [6000, 60], [50, 350], [10000, 10]];
  cases.forEach(([r, h], i) => {
    const occ = steadyState(r, h), n = ipsNeeded(r, h, AWS_NAT_PER_DEST);
    if (i % 2 === 0) add(`Ile portów SNAT jest zajętych w stanie ustalonym przy ${r} nowych połączeniach/s do jednego celu i czasie trzymania ${h} s?`, fmt(occ), [fmt(r), fmt(r * h * 2), fmt(Math.round(occ / 2)), fmt(r + h)], `${r} × ${h}.`);
    else add(`Ile adresów AWS NAT Gateway (${fmt(AWS_NAT_PER_DEST)} połączeń/cel/adres) potrzeba dla ${r} połączeń/s do jednego celu przy ${h} s trzymania?`, String(n), [String(n + 1), String(Math.max(n - 1, 0) === n ? n + 2 : Math.max(n - 1, 0)), String(n * 2 + 1), String(Math.ceil(occ / 65535) + 3)], `${fmt(occ)} / ${fmt(AWS_NAT_PER_DEST)} w górę.`);
  });

  // 2) Translacje PAT (8)
  const scen = [
    [{ proto: 'TCP', src: '192.168.1.20', sport: 40000, dst: '198.51.100.7', dport: 443 }, { proto: 'TCP', src: '192.168.1.21', sport: 40000, dst: '198.51.100.7', dport: 443 }],
    [{ proto: 'TCP', src: '192.168.1.20', sport: 40000, dst: '198.51.100.7', dport: 443 }, { proto: 'TCP', src: '192.168.1.21', sport: 40000, dst: '198.51.100.8', dport: 443 }],
    [{ proto: 'TCP', src: '192.168.1.20', sport: 40000, dst: '198.51.100.7', dport: 443 }, { proto: 'UDP', src: '192.168.1.21', sport: 40000, dst: '198.51.100.7', dport: 443 }],
    [{ proto: 'TCP', src: '192.168.1.20', sport: 40000, dst: '198.51.100.7', dport: 443 }, { proto: 'TCP', src: '192.168.1.21', sport: 40000, dst: '198.51.100.7', dport: 80 }],
  ];
  scen.forEach((fl, i) => {
    const t = pat(fl, ['203.0.113.5'], 40000, 40001);
    const second = t[1];
    add(`PAT na 203.0.113.5 (porty 40000–40001). Pierwszy przepływ: ${fl[0].proto} ${fl[0].src}:40000 → ${fl[0].dst}:${fl[0].dport} dostał 203.0.113.5:40000. Jaki port zewnętrzny dostanie ${fl[1].proto} ${fl[1].src}:40000 → ${fl[1].dst}:${fl[1].dport}?`,
      String(second.nat.port), ['brak — zostanie odrzucony', String(second.nat.port === 40000 ? 40001 : 40000), '1024'].filter((x) => x !== String(second.nat.port)).concat(['40002']),
      second.nat.port === 40000 ? 'Krotka różni się protokołem lub celem — port można użyć ponownie.' : 'Ten sam protokół i cel — potrzebny inny port.');
  });
  for (const [ports, hosts] of [[2, 3], [3, 3], [4, 6], [10, 8]]) {
    const fl = Array.from({ length: hosts }, (_, i) => ({ proto: 'TCP', src: `10.0.0.${i + 2}`, sport: 50000, dst: '198.51.100.7', dport: 5432 }));
    const ok = pat(fl, ['203.0.113.5'], 50000, 50000 + ports - 1).filter((t) => t.nat).length;
    add(`Pula portów PAT ma rozmiar ${ports}, a liczba hostów łączących się jednocześnie z tym samym celem 198.51.100.7:5432 wynosi ${hosts}. Ile połączeń się uda?`, String(ok), [String(hosts), String(ports + 1), String(Math.max(ok - 1, 0) === ok ? 0 : Math.max(ok - 1, 0)), String(hosts * ports)].filter((x) => x !== String(ok)).concat(['0']), 'Do jednego celu: tyle połączeń, ile portów.');
  }

  // 3) Merytoryczne (76)
  const facts = [
    ['Co zmienia SNAT?', 'Adres (i ewentualnie port) źródłowy', ['Adres docelowy', 'TTL na 64', 'Adres MAC celu'], 'Ruch wychodzący.'],
    ['Co zmienia DNAT?', 'Adres (i ewentualnie port) docelowy', ['Adres źródłowy', 'Numer sekwencyjny TCP', 'Protokół'], 'Port forwarding.'],
    ['Czym jest masquerade?', 'SNAT na bieżący adres interfejsu wyjściowego', ['DNAT na serwer w DMZ', 'NAT statyczny 1:1', 'Szyfrowanie nagłówka'], 'Dla dynamicznych adresów.'],
    ['W którym łańcuchu Netfilter wykonuje się SNAT/MASQUERADE?', 'POSTROUTING', ['PREROUTING', 'INPUT', 'OUTPUT filter'], 'Po decyzji o trasie.'],
    ['W którym łańcuchu Netfilter wykonuje się DNAT dla ruchu przychodzącego?', 'PREROUTING', ['POSTROUTING', 'FORWARD', 'INPUT'], 'Przed decyzją o trasie.'],
    ['Czym jest PAT?', 'Translacją wielu adresów na jeden, rozróżnianych portami', ['Translacją 1:1', 'Translacją tylko UDP', 'Protokołem routingu'], 'NAT overload.'],
    ['Jak Cisco nazywa PAT w konfiguracji?', 'overload', ['masquerade', 'pool static', 'hairpin'], 'ip nat inside source list X interface Y overload.'],
    ['Co oznacza „inside local” w terminologii Cisco?', 'Prywatny adres hosta wewnątrz', ['Publiczny adres hosta', 'Adres serwera w Internecie', 'Adres bramy'], 'Przed translacją.'],
    ['Co oznacza „inside global”?', 'Adres hosta wewnętrznego widziany z zewnątrz (po NAT)', ['Prywatny adres hosta', 'Adres serwera DNS', 'Adres loopback'], 'Po translacji.'],
    ['Które polecenie Cisco pokazuje aktywne translacje?', 'show ip nat translations', ['show nat table', 'show ip route nat', 'show conntrack'], 'Plus show ip nat statistics.'],
    ['Do czego służy conntrack w Linuksie?', 'Do śledzenia przepływów i odwracania translacji dla odpowiedzi', ['Do routingu dynamicznego', 'Do DNS', 'Do szyfrowania'], 'Stanowy NAT i firewall.'],
    ['Na który pakiet przepływu działa reguła NAT w iptables?', 'Na pierwszy — kolejne tłumaczy conntrack', ['Na każdy osobno', 'Na ostatni', 'Tylko na pakiety SYN-ACK'], 'Dlatego zmiana reguły nie wpływa na istniejące połączenia.'],
    ['Co pokazuje druga krotka we wpisie conntrack -L?', 'Oczekiwany kierunek odpowiedzi (po translacji)', ['Poprzednie połączenie', 'Kopię pierwszej krotki', 'Adres serwera DNS'], 'Reply tuple.'],
    ['Co oznacza [ASSURED] we wpisie conntrack?', 'Ruch był widziany w obu kierunkach — wpis nie zostanie usunięty w pierwszej kolejności', ['Połączenie szyfrowane', 'Błąd translacji', 'Wpis statyczny'], 'Chroni przed usunięciem przy zapełnieniu.'],
    ['Co oznacza komunikat „nf_conntrack: table full, dropping packet”?', 'Tablica conntrack jest pełna i nowe przepływy są odrzucane', ['Brak pamięci dyskowej', 'Błąd DNS', 'Wyłączony routing'], 'Zwiększ limit, usuń przyczynę.'],
    ['Które pola zmienia router przy SNAT poza adresem źródłowym?', 'Sumy kontrolne IP i TCP/UDP (i zwykle TTL o 1)', ['Numery sekwencyjne TCP', 'Port docelowy', 'Dane aplikacji'], 'Przeliczenie sum.'],
    ['Dlaczego suma kontrolna TCP zmienia się przy NAT, choć NAT zmienia nagłówek IP?', 'Bo suma TCP obejmuje pseudo-nagłówek z adresami IP', ['Bo NAT szyfruje TCP', 'Bo zmienia się okno TCP', 'Nie zmienia się'], 'Pseudo-header.'],
    ['Do jakiej krotki odnosi się limit portów SNAT?', 'Do jednego celu (adres i port docelowy) dla danego adresu NAT', ['Do wszystkich celów łącznie', 'Do jednego hosta wewnątrz', 'Do jednego protokołu'], 'Porty można reużywać dla różnych celów.'],
    ['Ile jednoczesnych połączeń do jednego celu obsługuje AWS NAT Gateway na adres IP?', '55 000', ['1 024', '65 535 na całą bramę', 'Bez limitu'], 'Do 8 adresów na bramę.'],
    ['Jaka metryka AWS wskazuje wyczerpanie portów NAT Gateway?', 'ErrorPortAllocation', ['CPUUtilization', 'BurstBalance', 'HealthyHostCount'], 'CloudWatch.'],
    ['Co najskuteczniej zapobiega wyczerpaniu portów SNAT w aplikacji?', 'Reużywanie połączeń (pule, keep-alive, HTTP/2)', ['Zwiększenie timeoutów', 'Wyłączenie DNS', 'Krótsze hasła'], 'Mniej nowych połączeń.'],
    ['Dlaczego tworzenie nowego HttpClient przy każdym żądaniu jest złe w kontekście NAT?', 'Każde żądanie otwiera nowe połączenie i zajmuje nowy port SNAT', ['Bo HttpClient nie obsługuje HTTPS', 'Bo zmienia adres IP aplikacji', 'Bo wyłącza DNS'], 'Współdziel klienta.'],
    ['Co zajmuje port SNAT jeszcze po zamknięciu połączenia?', 'Stan TIME_WAIT / czas życia wpisu conntrack lub NAT', ['Cache DNS', 'Tablica ARP', 'Sesja TLS'], 'Czas trzymania.'],
    ['Jak zmniejszyć ruch przez NAT Gateway do usług AWS (np. S3)?', 'VPC endpoints (gateway/interface)', ['Większy MTU', 'Krótszy TTL DNS', 'Więcej security groups'], 'Ruch zostaje w sieci AWS.'],
    ['Czym jest hairpin NAT?', 'Obsługą ruchu z LAN do publicznego adresu usługi w tym samym LAN', ['NAT dla IPv6', 'NAT w CGNAT', 'Szyfrowanym NAT'], 'NAT loopback.'],
    ['Jaki objaw daje brak hairpin NAT?', 'Usługa działa z Internetu, a z LAN po domenie nie', ['Brak Internetu w LAN', 'Błędy CRC', 'Brak DHCP'], 'Odpowiedź omija router.'],
    ['Jakie jest zwykle lepsze rozwiązanie problemu hairpin niż dodatkowy SNAT?', 'Split-horizon DNS (w LAN domena wskazuje adres prywatny)', ['Wyłączenie DNS', 'Statyczny ARP', 'Większy MTU'], 'Krótsza droga.'],
    ['Czy NAT jest mechanizmem bezpieczeństwa?', 'Nie — to translacja adresów; politykę zapewnia firewall', ['Tak, zastępuje firewall', 'Tak, szyfruje ruch', 'Tak, ale tylko PAT'], 'Efekt uboczny to nie polityka.'],
    ['Co pozwala aplikacjom w LAN samodzielnie otwierać porty na routerze?', 'UPnP (IGD) lub NAT-PMP/PCP', ['DHCP', 'ARP', 'LLDP'], 'Zwykle warto wyłączyć.'],
    ['Jak Docker realizuje -p 8080:80?', 'Regułą DNAT w łańcuchu DOCKER (oraz docker-proxy)', ['Regułą SNAT', 'Trasą statyczną', 'Wpisem w /etc/hosts'], 'iptables -t nat -L DOCKER.'],
    ['W którym łańcuchu filtrować dostęp do portów opublikowanych przez Dockera?', 'DOCKER-USER', ['INPUT', 'OUTPUT', 'PREROUTING'], 'Przetwarzany przed regułami Dockera.'],
    ['Jak kontenery Dockera wychodzą do Internetu?', 'Przez MASQUERADE hosta w POSTROUTING', ['Przez własne publiczne adresy', 'Przez DNAT', 'Przez proxy ARP'], 'NAT na adres hosta.'],
    ['Czym w Kubernetes jest Service ClusterIP z perspektywy sieci?', 'Wirtualnym adresem, na który kube-proxy/eBPF robi DNAT do podów', ['Adresem fizycznego interfejsu', 'Adresem węzła', 'Adresem DNS'], 'Bez interfejsu.'],
    ['Co robi externalTrafficPolicy: Local?', 'Zachowuje adres klienta, kierując ruch tylko do podów na węźle wejściowym', ['Włącza SNAT', 'Wyłącza Service', 'Szyfruje ruch'], 'Cluster = SNAT.'],
    ['Czym jest kubectl port-forward?', 'Tunelem przez API serwer do poda (nie NAT)', ['Regułą DNAT na węźle', 'NodePortem', 'Load balancerem'], 'Do debugowania.'],
    ['Czym jest Elastic IP w AWS z perspektywy NAT?', 'Statycznym NAT 1:1 adresu prywatnego instancji na publiczny', ['PAT dla podsieci', 'Adresem prywatnym', 'Adresem IPv6'], 'Przez Internet Gateway.'],
    ['Dlaczego aplikacja za load balancerem widzi adres balansera?', 'Bo balanser kończy połączenie lub robi SNAT — klient jest w X-Forwarded-For / Proxy Protocol', ['Bo klient nie ma adresu', 'Bo DNS zwraca adres balansera klientowi', 'Bo TLS ukrywa adres'], 'Czytaj od zaufanego proxy.'],
    ['Jak w Spring Boot włączyć obsługę nagłówków X-Forwarded-*?', 'server.forward-headers-strategy=native lub framework', ['spring.http.proxy=true', 'server.nat=enabled', 'management.forwarded=true'], 'Plus lista zaufanych proxy.'],
    ['Dlaczego nie wolno ufać X-Forwarded-For od każdego?', 'Klient może podać dowolną wartość nagłówka', ['Bo nagłówek jest szyfrowany', 'Bo zawiera hasło', 'Bo zmienia port'], 'Tylko od znanych proxy.'],
    ['Czego potrzebuje IPsec za NAT?', 'NAT Traversal (ESP w UDP 4500)', ['Wyłączenia NAT', 'TCP 443', 'Statycznego ARP'], 'ESP nie ma portów.'],
    ['Jakie porty musi przepuścić firewall dla IPsec z NAT-T?', 'UDP 500 i UDP 4500', ['TCP 22 i 23', 'UDP 67 i 68', 'TCP 1723'], 'IKE i NAT-T.'],
    ['Co robi STUN?', 'Pozwala klientowi poznać swój publiczny adres i port widziany za NAT', ['Przekazuje cały ruch', 'Szyfruje ruch', 'Konfiguruje DHCP'], 'WebRTC, VoIP.'],
    ['Kiedy WebRTC musi użyć TURN?', 'Gdy bezpośrednie połączenie przez NAT (np. symetryczny, CGNAT) się nie udaje', ['Zawsze', 'Nigdy', 'Tylko w IPv6'], 'Przekaźnik ruchu.'],
    ['Który typ NAT utrudnia hole punching najbardziej?', 'NAT symetryczny (inne mapowanie dla każdego celu)', ['NAT 1:1', 'Full-cone NAT', 'Brak NAT'], 'Wymaga TURN.'],
    ['Czym jest CGNAT z perspektywy klienta?', 'Drugim poziomem NAT po stronie operatora', ['NAT w routerze domowym', 'NAT w Dockerze', 'Brakiem NAT'], '100.64.0.0/10.'],
    ['Dlaczego logi NAT są ważne w reagowaniu na incydenty?', 'Pozwalają ustalić, który host wewnętrzny stał za adresem i portem publicznym w danym czasie', ['Bo zawierają hasła', 'Bo przyspieszają ruch', 'Bo zastępują EDR'], 'Czas + krotka.'],
    ['Co trzeba znać, by zidentyfikować hosta za PAT na podstawie logu serwera?', 'Adres i port publiczny oraz dokładny czas', ['Tylko adres publiczny', 'Tylko User-Agent', 'Tylko adres MAC'], 'Synchronizacja NTP.'],
    ['Czym jest NAT 1:1 w odróżnieniu od PAT?', 'Stałym powiązaniem jednego adresu prywatnego z jednym publicznym', ['Translacją wielu na jeden', 'Translacją portów', 'Translacją IPv6 na IPv4'], 'Działa w obie strony.'],
    ['Jak zobaczyć liczbę wpisów conntrack?', 'conntrack -C lub sysctl net.netfilter.nf_conntrack_count', ['ip route', 'ss -s', 'arp -n'], 'I limit nf_conntrack_max.'],
    ['Dlaczego zapytania DNS (UDP) mocno obciążają conntrack w Kubernetes?', 'Każde zapytanie tworzy osobny wpis UDP', ['Bo DNS używa TCP', 'Bo DNS jest szyfrowany', 'Bo DNS nie przechodzi przez NAT'], 'NodeLocal DNSCache pomaga.'],
    ['Czym jest NAT64?', 'Translacją między IPv6 a IPv4 dla sieci IPv6-only', ['NAT dla 64 hostów', 'NAT na porcie 64', 'Szyfrowanym NAT'], 'Z DNS64.'],
    ['Czy w IPv6 zwykle stosuje się NAT?', 'Nie — hosty mają globalne adresy, a ochronę daje firewall', ['Tak, zawsze', 'Tak, tylko PAT', 'Tak, wymagany przez RFC'], 'NPTv6 w szczególnych przypadkach.'],
    ['Czym jest NPTv6?', 'Bezstanową translacją prefiksów IPv6 (RFC 6296)', ['PAT dla IPv6', 'Protokołem routingu', 'Typem adresu link-local'], 'Bez portów.'],
    ['Dlaczego reguła DNAT bez reguły FORWARD może nie działać?', 'Bo filtr FORWARD z polityką DROP odrzuci przekierowany ruch', ['Bo DNAT wymaga SNAT', 'Bo DNAT działa tylko w OUTPUT', 'Bo brak trasy domyślnej'], 'Translacja ≠ zezwolenie.'],
    ['Jaki port docelowy widzi łańcuch FORWARD po DNAT 8443 → 443?', '443 (już po translacji)', ['8443', 'Losowy', '0'], 'DNAT w PREROUTING jest wcześniej.'],
    ['Co się stanie, gdy serwer za DNAT odpowiada inną bramą niż router robiący DNAT?', 'Odpowiedź nie zostanie przetłumaczona i klient ją odrzuci', ['Połączenie przyspieszy', 'Router zrobi hairpin', 'Nic'], 'Asymetria.'],
    ['Które protokoły wymagają helperów ALG przy NAT?', 'Takie, które przenoszą adresy w treści (np. FTP aktywny, SIP)', ['HTTPS', 'SSH', 'DNS przez TCP'], 'nf_conntrack_ftp, sip.'],
    ['Dlaczego wystawianie RDP przez port forwarding jest ryzykowne?', 'Usługa staje się celem skanów i ataków brute force/exploitów z Internetu', ['Bo RDP nie działa przez NAT', 'Bo RDP używa UDP', 'Bo wymaga IPv6'], 'Użyj VPN/ZTNA.'],
    ['Jaki jest bezpieczniejszy sposób zdalnego dostępu do bazy niż port forwarding?', 'VPN lub bastion/ZTNA z MFA', ['Zmiana portu na niestandardowy', 'Wyłączenie hasła', 'UPnP'], 'Brak ekspozycji.'],
    ['Co oznacza wpis „tcp 6 118 TIME_WAIT” w conntrack?', 'Połączenie TCP w stanie TIME_WAIT, wpis wygaśnie za 118 s', ['118 aktywnych połączeń', 'Port 118', 'Błąd 118'], 'Czas do wygaśnięcia.'],
    ['Dlaczego port źródłowy często jest zachowywany przez NAT?', 'Implementacje próbują go zachować, gdy nie koliduje z istniejącą translacją', ['Bo RFC tego zabrania zmieniać', 'Bo NAT nie zna portów', 'Bo jest szyfrowany'], 'Przy kolizji — inny port.'],
    ['Jaki zakres portów jest typowo używany przez PAT?', 'Porty nieuprzywilejowane (np. 1024–65535)', ['Tylko 0–1023', 'Tylko 80 i 443', 'Tylko 49152–49200'], 'Konfigurowalny.'],
    ['Gdzie w AWS widać adresy przed i za NAT w logach przepływów?', 'VPC Flow Logs z polami pkt-srcaddr/pkt-dstaddr', ['CloudTrail', 'S3 access logs', 'Route 53 logs'], 'Wersja z dodatkowymi polami.'],
    ['Co to jest twice NAT?', 'Jednoczesna translacja adresu źródłowego i docelowego', ['NAT wykonany dwa razy przez pomyłkę', 'CGNAT', 'NAT dla IPv6'], 'Przy nakładających się sieciach.'],
    ['Kiedy NAT jest nieuniknionym obejściem nakładających się zakresów adresów?', 'Gdy dwie sieci z tym samym zakresem muszą się komunikować, a renumeracja nie jest możliwa od razu', ['Zawsze przy VPN', 'Nigdy', 'Tylko w IPv6'], 'Docelowo renumeracja.'],
    ['Jaki sysctl włącza przekazywanie pakietów potrzebne dla NAT na routerze Linux?', 'net.ipv4.ip_forward=1', ['net.ipv4.nat=1', 'net.core.nat=on', 'kernel.nat_enable=1'], 'Bez tego brak routingu.'],
    ['Które polecenie nftables tworzy masquerade?', 'oifname "wan0" masquerade w łańcuchu typu nat z hookiem postrouting', ['iifname "wan0" dnat', 'masq on wan0', 'nat enable wan0'], 'priority srcnat.'],
    ['Dlaczego w AWS umieszcza się NAT Gateway w każdej strefie?', 'Dla odporności na awarię strefy i uniknięcia ruchu międzystrefowego', ['Bo jeden NAT GW nie obsługuje TCP', 'Bo wymaga tego IAM', 'Bo inaczej nie działa DNS'], 'Route table per AZ.'],
    ['Co to jest Proxy Protocol v2?', 'Binarny nagłówek na początku połączenia TCP przekazujący oryginalny adres klienta', ['Protokół routingu', 'Wersja HTTP', 'Typ NAT'], 'Dla balanserów L4.'],
    ['Dlaczego limit „połączeń na cel” nie dotyczy ruchu do wielu różnych celów?', 'Bo krotki do różnych celów są różne, więc porty mogą się powtarzać', ['Bo NAT pomija takie połączenia', 'Bo różne cele używają IPv6', 'Bo wtedy NAT jest wyłączony'], 'Unikalna krotka.'],
    ['Co się dzieje z istniejącymi połączeniami po zmianie reguły NAT?', 'Zwykle działają dalej według starej translacji z conntrack', ['Natychmiast zmieniają adres', 'Są zrywane zawsze', 'Przechodzą na IPv6'], 'conntrack -F czyści wpisy.'],
    ['Jak wyczyścić wpisy conntrack po zmianie reguł NAT?', 'conntrack -F (lub -D z filtrem)', ['ip route flush', 'iptables -Z', 'sysctl -p'], 'Ostrożnie na produkcji.'],
    ['Czym różni się DNAT od reverse proxy?', 'DNAT zmienia adres w pakietach (L3/L4), proxy kończy połączenie i otwiera nowe (L7)', ['Niczym', 'Proxy działa tylko w UDP', 'DNAT szyfruje ruch'], 'Proxy widzi HTTP.'],
    ['Dlaczego serwer w Internecie nie może zainicjować połączenia do laptopa za PAT?', 'Bo bez wpisu translacji router nie wie, do którego hosta skierować ruch', ['Bo laptop nie ma karty sieciowej', 'Bo blokuje to DNS', 'Bo TCP nie pozwala'], 'Brak mapowania.'],
    ['Co oznacza adres 203.0.113.5:51000 w logu serwera, gdy klient stoi za PAT?', 'Adres i port po translacji — wiele hostów może dzielić ten adres', ['Prywatny adres klienta', 'Adres serwera DNS', 'Adres bramy klienta w LAN'], 'Inside global.'],
    ['Jaki zakres adresów współdzielonych stosuje CGNAT?', '100.64.0.0/10', ['10.0.0.0/8', '169.254.0.0/16', '192.0.2.0/24'], 'RFC 6598.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
