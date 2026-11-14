// Quiz (100 pytan) do lekcji _ns_03/14_OspfSingleArea.
const { quizBuilder } = require('./lib.cjs');
const { cost, spf, paths, electDr, STATES, LINKS, graphFrom } = require('./ns03_14_ospf_model.cjs');
const { rng } = require('./ns03_05_drill_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111414);

  // 1) Koszt (12)
  for (const [bw, ref] of [[10, 100], [100, 100], [1000, 100], [10000, 100], [10, 10000], [100, 10000], [1000, 10000], [10000, 10000], [1000, 100000], [25000, 100000], [40000, 100000], [100000, 100000]]) {
    const c = cost(bw, ref);
    add(`Jaki koszt OSPF ma łącze ${bw >= 1000 ? bw / 1000 + ' Gb/s' : bw + ' Mb/s'} przy referencji ${ref.toLocaleString('pl-PL')} Mb/s?`, String(c), [String(c * 10), String(Math.max(c / 10, 0.1)), String(bw), String(c + 1), '0'].filter((x) => x !== String(c)), `max(1, ${ref}/${bw}).`);
  }

  // 2) SPF na losowych topologiach (16)
  const r = rng(1414);
  const names = ['A', 'B', 'C', 'D', 'E'];
  let made = 0;
  while (made < 16) {
    const g = {}; names.forEach((n) => { g[n] = {}; });
    const edges = [];
    for (let i = 0; i < names.length; i++) for (let j = i + 1; j < names.length; j++) if (r() < 0.6) { const c = [1, 5, 10, 20, 50, 100][Math.floor(r() * 6)]; g[names[i]][names[j]] = c; g[names[j]][names[i]] = c; edges.push(`${names[i]}–${names[j]} ${c}`); }
    const res = spf(g, 'A');
    const target = names[1 + (made % 4)];
    if (res.dist[target] === Infinity) continue;
    const ps = paths(res, target, 'A').map((p) => p.join('→'));
    const right = `${res.dist[target]} (${ps.join(' lub ')})`;
    const wrongDist = [...new Set([res.dist[target] + 5, Math.max(1, res.dist[target] - 5), Object.values(g.A)[0] ?? 1, res.dist[target] * 2, res.dist[target] + 1, res.dist[target] + 10, res.dist[target] * 3])].filter((x) => x !== res.dist[target]);
    add(`Łącza i koszty: ${edges.join(', ')}. Jaki jest najmniejszy koszt z A do ${target} (i ścieżka)?`, right, wrongDist.map((x) => `${x} (${ps[0]})`), 'Dijkstra od A.');
    made++;
  }

  // 3) Sciezka w topologii lekcji (4)
  for (const [ref, ov, label] of [[100, {}, 'domyślnej referencji 100'], [10000, {}, 'referencji 10 000'], [10000, { 'R1-R2': 500, 'R2-R1': 500 }, 'referencji 10 000 i koszcie 500 na R1–R2'], [10000, { 'R2-R4': 300, 'R4-R2': 300 }, 'referencji 10 000 i koszcie 300 na R2–R4']]) {
    const g = graphFrom(LINKS, ref, ov), res = spf(g, 'R1'), ps = paths(res, 'R4', 'R1').map((p) => p.join('→'));
    const right = ps.join(' oraz ');
    add(`Topologia z lekcji (R1–R2 1G, R2–R4 1G, R1–R3 100M, R3–R4 100M, R2–R3 10M). Którędy R1 wyśle ruch do R4 przy ${label}?`, right, ['R1→R2→R4', 'R1→R3→R4', 'R1→R2→R4 oraz R1→R3→R4', 'R1→R2→R3→R4', 'R1→R3→R2→R4'].filter((x) => x !== right), `Koszt ${res.dist.R4}.`);
  }

  // 4) DR/BDR (8)
  const sets = [
    [['R1', 1, '1.1.1.1'], ['R2', 1, '2.2.2.2'], ['R3', 1, '3.3.3.3']],
    [['R1', 100, '1.1.1.1'], ['R2', 1, '9.9.9.9'], ['R3', 1, '5.5.5.5']],
    [['R1', 0, '9.9.9.9'], ['R2', 1, '2.2.2.2'], ['R3', 1, '3.3.3.3']],
    [['R1', 5, '10.0.0.1'], ['R2', 5, '10.0.0.2'], ['R3', 10, '1.1.1.1']],
    [['R1', 1, '192.168.1.1'], ['R2', 1, '10.255.255.255'], ['R3', 1, '172.16.0.1']],
    [['R1', 0, '1.1.1.1'], ['R2', 0, '2.2.2.2'], ['R3', 1, '3.3.3.3']],
    [['R1', 255, '1.1.1.1'], ['R2', 254, '2.2.2.2'], ['R3', 1, '3.3.3.3']],
    [['R1', 1, '4.4.4.4'], ['R2', 2, '3.3.3.3'], ['R3', 2, '2.2.2.2']],
  ];
  for (const s of sets) {
    const e = electDr(s.map(([name, prio, rid]) => ({ name, prio, rid })));
    const right = `DR ${e.dr}, BDR ${e.bdr ?? 'brak'}`;
    const all = ['R1', 'R2', 'R3'];
    const opts = [];
    for (const a of all) for (const b2 of [...all, 'brak']) if (a !== b2) opts.push(`DR ${a}, BDR ${b2}`);
    add(`Segment broadcast, routery startują jednocześnie: ${s.map(([n, p, rid]) => `${n} prio ${p} RID ${rid}`).join('; ')}. Wynik wyboru?`, right, opts.filter((x) => x !== right).sort((x, y) => (x.length * 7 + y.charCodeAt(4)) % 5 - 2).slice(0, 5), 'Najwyższy priorytet (0 nie kandyduje), potem najwyższy RID.');
  }

  // 5) Merytoryczne (60)
  const facts = [
    ['Jaki numer protokołu IP używa OSPF?', '89', ['6', '17', '88'], 'Działa bezpośrednio nad IP.'],
    ['Jaki jest dystans administracyjny OSPF?', '110', ['1', '90', '120'], 'Cisco.'],
    ['Do jakiej rodziny protokołów należy OSPF?', 'Link-state', ['Distance-vector', 'Path-vector', 'Hybrid'], 'Mapa sieci.'],
    ['Jakiego algorytmu używa OSPF do liczenia tras?', 'Dijkstry (SPF)', ['Bellmana-Forda', 'Kruskala', 'DUAL'], 'Shortest Path First.'],
    ['Na jaki adres multicast wysyłane są Hello OSPFv2?', '224.0.0.5', ['224.0.0.6', '224.0.0.9', '255.255.255.255'], 'AllSPFRouters.'],
    ['Na jaki adres wysyłają aktualizacje DROTHER-y do DR/BDR?', '224.0.0.6', ['224.0.0.5', '224.0.0.10', '224.0.0.1'], 'AllDRouters.'],
    ['Jakie są domyślne timery Hello/Dead OSPF na Ethernecie?', '10 s / 40 s', ['30 s / 120 s', '1 s / 3 s', '5 s / 15 s'], 'Muszą się zgadzać.'],
    ['Jaki pakiet OSPF służy do wykrywania sąsiadów?', 'Hello', ['DBD', 'LSR', 'LSU'], 'Typ 1.'],
    ['Jaki pakiet zawiera spis (nagłówki) LSA w bazie?', 'DBD', ['Hello', 'LSAck', 'LSU'], 'Database Description.'],
    ['Jaki pakiet przenosi pełne LSA?', 'LSU', ['LSR', 'DBD', 'Hello'], 'Link-State Update.'],
    ['Jaki pakiet służy do żądania konkretnych LSA?', 'LSR', ['LSU', 'LSAck', 'DBD'], 'Link-State Request.'],
    ['W jakim stanie sąsiedztwo utknie przy niezgodnym MTU?', 'ExStart/Exchange', ['Init', '2-Way', 'Loading'], 'MTU w DBD.'],
    ['Jaki stan oznacza pełną synchronizację LSDB?', 'Full', ['2-Way', 'Loading', 'Exchange'], 'Koniec procesu.'],
    ['Co oznacza stan Init?', 'Otrzymałem Hello sąsiada, ale nie ma w nim mojego Router ID', ['Pełne sąsiedztwo', 'Wybór DR', 'Wymiana DBD'], 'Komunikacja jednokierunkowa.'],
    ['Co oznacza stan 2-Way między dwoma DROTHER?', 'Normalny stan — pełne sąsiedztwo mają tylko z DR i BDR', ['Błąd MTU', 'Brak uwierzytelniania', 'Awarię łącza'], 'Zamierzone.'],
    ['W którym stanie odbywa się wybór DR/BDR?', '2-Way', ['Init', 'Full', 'Loading'], 'Po dwukierunkowym Hello.'],
    ['Które parametry muszą się zgadzać w Hello?', 'Obszar, timery Hello/Dead, uwierzytelnianie, maska (broadcast)', ['Tylko Router ID', 'MTU i koszt', 'Priorytet i koszt'], 'Plus typ obszaru.'],
    ['Czy koszt musi być taki sam po obu stronach łącza do sąsiedztwa?', 'Nie — koszt nie wpływa na sąsiedztwo', ['Tak, zawsze', 'Tylko w obszarze 0', 'Tylko na P2P'], 'Wpływa na trasy.'],
    ['Jak wybierany jest Router ID, gdy nie ustawiono go ręcznie?', 'Najwyższy adres loopback, potem najwyższy adres aktywnego interfejsu', ['Najniższy adres interfejsu', 'Adres MAC', 'Losowo'], 'Najlepiej ręcznie.'],
    ['Kiedy zmiana router-id zaczyna obowiązywać?', 'Po restarcie procesu OSPF', ['Natychmiast', 'Po 40 s', 'Po zapisaniu konfiguracji'], 'clear ip ospf process.'],
    ['Co się stanie przy duplikacie Router ID w obszarze?', 'Niestabilność LSDB i problemy z trasami', ['Nic', 'Szybsza zbieżność', 'Automatyczna zmiana RID'], 'Unikaj klonowania konfiguracji.'],
    ['Jak wybierany jest DR?', 'Najwyższy priorytet interfejsu, przy remisie najwyższy Router ID', ['Najniższy Router ID', 'Najszybsze łącze', 'Pierwszy uruchomiony zawsze'], 'Priorytet 0 = nigdy.'],
    ['Czy OSPF wywłaszcza DR, gdy pojawi się router z wyższym priorytetem?', 'Nie', ['Tak, natychmiast', 'Tak, po 40 s', 'Tylko w IPv6'], 'Brak preempt.'],
    ['Co oznacza priorytet OSPF 0 na interfejsie?', 'Router nigdy nie zostanie DR ani BDR', ['Najwyższy priorytet', 'Wyłączenie OSPF', 'Brak Hello'], 'Na tym segmencie.'],
    ['Po co istnieje DR na segmencie broadcast?', 'Aby zmniejszyć liczbę sąsiedztw i LSA w segmencie', ['Aby szyfrować ruch', 'Aby nadawać adresy IP', 'Aby wybierać trasę domyślną'], 'n(n−1)/2 → mniej.'],
    ['Jaki typ LSA generuje DR?', 'Typ 2 (Network LSA)', ['Typ 1', 'Typ 3', 'Typ 5'], 'Opis segmentu.'],
    ['Jaki typ LSA generuje każdy router w obszarze?', 'Typ 1 (Router LSA)', ['Typ 2', 'Typ 5', 'Typ 7'], 'Łącza i koszty.'],
    ['Jaki typ LSA opisuje trasy zewnętrzne (redystrybucja)?', 'Typ 5', ['Typ 1', 'Typ 2', 'Typ 3'], 'ASBR.'],
    ['Jak liczony jest domyślny koszt interfejsu OSPF?', 'Referencyjna przepustowość / przepustowość interfejsu', ['Liczba skoków', 'Opóźnienie', 'Obciążenie'], 'Min. 1.'],
    ['Jaka jest domyślna referencyjna przepustowość OSPF?', '100 Mb/s', ['1 Gb/s', '10 Gb/s', '10 Mb/s'], 'Dziś za mała.'],
    ['Jaki problem powoduje domyślna referencja 100 Mb/s?', 'Łącza 100M, 1G i 10G mają ten sam koszt 1', ['OSPF nie startuje', 'Brak sąsiedztw', 'Koszty ujemne'], 'ECMP przez wolne łącza.'],
    ['Jakim poleceniem zmienia się referencyjną przepustowość?', 'auto-cost reference-bandwidth', ['ip ospf bandwidth', 'ospf ref-bw', 'bandwidth reference'], 'Na wszystkich routerach.'],
    ['Jak ręcznie ustawić koszt interfejsu?', 'ip ospf cost <wartość>', ['ospf metric', 'ip route cost', 'bandwidth cost'], 'Nadpisuje auto-cost.'],
    ['Co zawiera trasa „O … [110/20]”?', 'AD 110 i koszt OSPF 20', ['20 skoków', 'AD 20', 'Priorytet 110'], 'Kod O = intra-area.'],
    ['Dlaczego na łączu dwóch routerów stosuje się typ point-to-point?', 'Brak wyboru DR/BDR, szybsze sąsiedztwo, prostsza LSDB', ['Bo broadcast nie działa na Ethernecie', 'Bo wymaga go uwierzytelnianie', 'Bo zwiększa koszt'], 'Po obu stronach.'],
    ['Co robi passive-interface w OSPF?', 'Ogłasza sieć interfejsu, ale nie wysyła na nim Hello', ['Wyłącza interfejs', 'Blokuje cały ruch', 'Zmienia koszt na 0'], 'Ochrona przed obcymi sąsiadami.'],
    ['Dlaczego sieci użytkowników powinny być pasywne w OSPF?', 'Aby nikt w tej sieci nie mógł nawiązać sąsiedztwa i wstrzyknąć tras', ['Aby przyspieszyć Wi-Fi', 'Aby zmniejszyć MTU', 'Aby włączyć DHCP'], 'Hardening.'],
    ['Jaki rodzaj uwierzytelniania OSPF jest zalecany?', 'HMAC-SHA (np. przez key chain)', ['Hasło w postaci jawnej', 'Brak uwierzytelniania', 'MD5 bez kluczy'], 'RFC 5709.'],
    ['Jak skrócić czas wykrycia awarii sąsiada OSPF poniżej Dead interval?', 'BFD', ['Większy koszt', 'Wyższy priorytet', 'Mniejsze MTU'], 'Setki milisekund.'],
    ['Jaki obszar jest backbone w OSPF?', 'Obszar 0', ['Obszar 1', 'Obszar 255', 'Obszar 100'], 'Wszystkie inne łączą się z 0.'],
    ['Czym różni się OSPFv3 od OSPFv2?', 'Działa dla IPv6 na adresach link-local, prefiksy w osobnych LSA', ['Nie używa SPF', 'Działa po TCP', 'Nie ma DR'], 'ff02::5/6.'],
    ['Na jaki adres multicast wysyła Hello OSPFv3?', 'ff02::5', ['ff02::1', 'ff02::2', 'ff02::fb'], 'AllSPFRouters IPv6.'],
    ['Które polecenie pokazuje sąsiadów OSPF?', 'show ip ospf neighbor', ['show ip route', 'show arp', 'show interfaces trunk'], 'Stan, priorytet, Dead time.'],
    ['Które polecenie pokazuje LSDB?', 'show ip ospf database', ['show ip ospf neighbor', 'show running-config', 'show ip bgp'], 'LSA wg typów.'],
    ['Co pokazuje show ip ospf interface?', 'Koszt, typ sieci, rolę DR/BDR, timery i sąsiadów interfejsu', ['Tylko adres IP', 'Tablicę MAC', 'Logi'], 'Diagnoza interfejsu.'],
    ['Co ogłasza polecenie network 10.0.12.0 0.0.0.3 area 0?', 'Włącza OSPF na interfejsach o adresach w 10.0.12.0–10.0.12.3', ['Tworzy trasę statyczną', 'Ogłasza trasę domyślną', 'Wyłącza OSPF'], 'Wildcard.'],
    ['Czym różni się distance-vector od link-state?', 'DV zna tylko odległości od sąsiadów, LS ma pełną mapę obszaru', ['DV jest szybszy', 'LS nie używa metryk', 'Nie różnią się'], 'RIP vs OSPF.'],
    ['Jaki jest maksymalny dystans w RIP?', '15 skoków', ['255 skoków', '100 skoków', 'Bez limitu'], '16 = nieosiągalny.'],
    ['Jak zachowa się OSPF, gdy dwie ścieżki mają ten sam koszt?', 'Zainstaluje obie (ECMP)', ['Wybierze losowo jedną', 'Wybierze krótszą w skokach', 'Odrzuci obie'], 'Load sharing.'],
    ['Dlaczego różne referencje na routerach to problem?', 'Koszty są niespójne, ścieżki nieoptymalne i asymetryczne', ['Bo OSPF się nie uruchomi', 'Bo DR nie zostanie wybrany', 'Nie jest problemem'], 'Ta sama wartość wszędzie.'],
    ['Co powoduje ip ospf mtu-ignore?', 'Pomija sprawdzanie MTU w DBD', ['Zmienia MTU interfejsu', 'Wyłącza fragmentację', 'Zwiększa koszt'], 'Obejście z ryzykiem.'],
    ['Jak zablokować OSPF z sieci użytkowników na poziomie ACL?', 'Odrzucać protokół IP 89 z tych sieci', ['Blokować TCP 89', 'Blokować UDP 520', 'Blokować ICMP'], 'Plus pasywne interfejsy.'],
    ['Jaki komunikat syslog Cisco informuje o zmianie sąsiedztwa OSPF?', '%OSPF-5-ADJCHG', ['%LINK-3-UPDOWN', '%SYS-5-CONFIG_I', '%BGP-5-ADJCHANGE'], 'Do SIEM.'],
    ['Jakie oprogramowanie routingu na Linuksie obsługuje OSPF?', 'FRR (ospfd)', ['nginx', 'dnsmasq', 'keepalived'], 'Także BIRD.'],
    ['Którą trasę wybierze router: statyczną 10.9.0.0/16 (AD 1) czy OSPF 10.9.0.0/16 (AD 110)?', 'Statyczną', ['OSPF', 'Obie', 'Żadną'], 'Niższy AD.'],
    ['Co oznacza „Dead time” w show ip ospf neighbor?', 'Czas, po którym sąsiad zostanie uznany za martwy bez kolejnego Hello', ['Czas od awarii', 'Czas życia LSA', 'Czas od restartu'], 'Odliczanie od 40 s.'],
    ['Co to jest flooding w OSPF?', 'Rozsyłanie LSA do wszystkich routerów obszaru', ['Atak DDoS', 'Przepełnienie tablicy', 'Broadcast ARP'], 'Synchronizacja LSDB.'],
    ['Jak często OSPF odświeża własne LSA, nawet bez zmian?', 'Co 30 minut (LSRefreshTime)', ['Co 10 s', 'Co 24 h', 'Nigdy'], 'MaxAge 60 min.'],
    ['Jaki wpływ ma czas zbieżności OSPF na aplikacje?', 'Przez ten czas ruch może ginąć — aplikacje widzą timeouty', ['Żaden', 'Aplikacje działają szybciej', 'Zmienia się DNS'], 'Timeouty i retry w kodzie.'],
    ['Gdzie OSPF zwykle NIE jest dostępny do konfiguracji?', 'Wewnątrz VPC w chmurze publicznej', ['Na routerach Cisco', 'Na Linuksie z FRR', 'W sieci kampusowej'], 'Tam trasy statyczne i BGP.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
