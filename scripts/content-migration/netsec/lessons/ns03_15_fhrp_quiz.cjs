// Quiz (100 pytan) do lekcji _ns_03/15_FirstHopRedundancyHsrpVrrp.
const { quizBuilder } = require('./lib.cjs');
const { vrrpFailover, vrrpMac, hsrp1Mac, hsrp2Mac, electActive, afterReturn } = require('./ns03_15_fhrp_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111415);
  const pl = (x) => String(x).replace('.', ',');

  // 1) Master_Down_Interval (10)
  for (const [adv, p] of [[1000, 100], [1000, 200], [1000, 254], [1000, 1], [100, 100], [100, 200], [500, 100], [200, 128], [2000, 100], [300, 250]]) {
    const f = vrrpFailover(adv, p);
    add(`VRRPv3: advert ${adv} ms, priorytet backupu ${p}. Ile wynosi Master_Down_Interval?`, `${pl(f.masterDown)} ms`, [`${3 * adv} ms`, `${pl(Math.round((3 * adv + adv) * 10) / 10)} ms`, `${pl(f.skew)} ms`, `${pl(Math.round((2 * adv + f.skew) * 10) / 10)} ms`].filter((x) => x !== `${pl(f.masterDown)} ms`), `3 × ${adv} + (256 − ${p}) × ${adv} / 256.`);
  }

  // 2) Wirtualne MAC (12)
  for (const [kind, n] of [['VRRP', 1], ['VRRP', 10], ['VRRP', 51], ['VRRP', 255], ['HSRPv1', 1], ['HSRPv1', 10], ['HSRPv1', 200], ['HSRPv2', 10], ['HSRPv2', 300], ['HSRPv2', 4095], ['HSRPv2', 1], ['VRRP', 100]]) {
    const mac = kind === 'VRRP' ? vrrpMac(n) : kind === 'HSRPv1' ? hsrp1Mac(n) : hsrp2Mac(n);
    const others = [vrrpMac(n % 256), hsrp1Mac(n % 256), hsrp2Mac(n), `00:00:5e:00:02:${(n % 256).toString(16).padStart(2, '0')}`, `01:00:5e:00:00:${(n % 256).toString(16).padStart(2, '0')}`];
    add(`Jaki wirtualny MAC ma ${kind} dla ${kind === 'VRRP' ? 'VRID' : 'grupy'} ${n}?`, mac, others.filter((x) => x !== mac), kind === 'VRRP' ? '00:00:5e:00:01:VRID.' : kind === 'HSRPv1' ? '00:00:0c:07:ac:GG.' : '00:00:0c:9f:f + 3 cyfry hex.');
  }

  // 3) Wybor aktywnego (8)
  const sets = [
    [['R1', 100, '10.0.10.2'], ['R2', 100, '10.0.10.3']],
    [['R1', 110, '10.0.10.2'], ['R2', 100, '10.0.10.3']],
    [['R1', 100, '10.0.10.200'], ['R2', 100, '10.0.10.30']],
    [['R1', 90, '10.0.10.2'], ['R2', 100, '10.0.10.3'], ['R3', 100, '10.0.10.4']],
    [['R1', 255, '10.0.10.1'], ['R2', 254, '10.0.10.3']],
    [['R1', 150, '10.0.10.9'], ['R2', 150, '10.0.10.10']],
    [['R1', 120, '10.0.10.2', false], ['R2', 100, '10.0.10.3'], ['R3', 110, '10.0.10.4']],
    [['R1', 100, '172.16.0.2'], ['R2', 99, '172.16.0.254']],
  ];
  for (const s of sets) {
    const rs = s.map(([name, prio, ip, up]) => ({ name, prio, ip, up }));
    const a = electActive(rs);
    add(`Grupa FHRP: ${s.map(([n, p, ip, up]) => `${n} prio ${p} ${ip}${up === false ? ' (awaria)' : ''}`).join(', ')}. Który router będzie aktywny/masterem?`, a, ['R1', 'R2', 'R3', 'żaden (konflikt)'].filter((x) => x !== a), 'Najwyższy priorytet, przy remisie najwyższy adres IP.');
  }

  // 4) Preempt (4)
  for (const [pre, label] of [[true, 'z preempt'], [false, 'bez preempt']]) for (const [ret, cur] of [[{ name: 'R1', prio: 110, ip: '10.0.10.2' }, { name: 'R2', prio: 100, ip: '10.0.10.3' }], [{ name: 'R1', prio: 100, ip: '10.0.10.2' }, { name: 'R2', prio: 100, ip: '10.0.10.3' }]]) {
    const r = afterReturn(cur, ret, pre);
    add(`${cur.name} (prio ${cur.prio}) jest aktywny. Wraca ${ret.name} (prio ${ret.prio}, ${ret.ip}) — ${label}. Kto będzie aktywny?`, r, [r === 'R1' ? 'R2' : 'R1', 'oba', 'żaden'], pre ? 'Preempt: wyższy priorytet (lub adres przy remisie) odbiera rolę.' : 'Bez preempt obecny aktywny zostaje.');
  }

  // 5) Merytoryczne (66)
  const facts = [
    ['Jaki problem rozwiązują protokoły FHRP?', 'Pojedynczy punkt awarii bramy domyślnej', ['Pętle przełączania', 'Brak adresów IP', 'Wolne DNS'], 'Wirtualna brama.'],
    ['Co hosty konfigurują jako bramę przy FHRP?', 'Wirtualny adres IP', ['Adres aktywnego routera', 'Adres obu routerów', 'Adres multicast'], 'Bez zmian po przełączeniu.'],
    ['Po co nowy aktywny router HSRP/VRRP wysyła gratuitous ARP, skoro wirtualny MAC się nie zmienia?', 'Aby przełączniki od razu nauczyły się nowego portu wirtualnego MAC', ['Aby hosty zmieniły bramę domyślną', 'Aby DHCP przydzielił nowe adresy', 'Aby wyłączyć stary router'], 'Hosty mają ten sam MAC w ARP — zmienia się port w tablicy MAC przełącznika.'],
    ['Który protokół FHRP jest standardem IETF?', 'VRRP', ['HSRP', 'GLBP', 'CARP'], 'RFC 5798.'],
    ['Które FHRP są własnością Cisco?', 'HSRP i GLBP', ['VRRP i HSRP', 'Tylko VRRP', 'VRRP i GLBP'], 'VRRP to IETF.'],
    ['Jak nazywają się role w HSRP?', 'Active i standby', ['Master i backup', 'AVG i AVF', 'Primary i secondary'], 'Pozostałe listen.'],
    ['Jak nazywają się role w VRRP?', 'Master i backup', ['Active i standby', 'AVG i AVF', 'Leader i follower'], 'RFC 5798.'],
    ['Co oznaczają skróty AVG i AVF w GLBP?', 'Active Virtual Gateway i Active Virtual Forwarder', ['Advanced VLAN Group', 'Automatic VRRP Gateway', 'Active VPN Forwarder'], 'Równoważenie.'],
    ['Który FHRP równoważy ruch hostów przy jednej bramie?', 'GLBP', ['HSRP', 'VRRP', 'Żaden'], 'Różne MAC w odpowiedziach ARP.'],
    ['Jaki jest domyślny priorytet w HSRP i VRRP?', '100', ['0', '255', '1'], 'Zakres 0–255.'],
    ['Co decyduje przy równym priorytecie?', 'Wyższy adres IP interfejsu', ['Niższy adres IP', 'Adres MAC', 'Czas uruchomienia'], 'Remis.'],
    ['Co oznacza priorytet 255 w VRRP?', 'Router jest właścicielem adresu (owner) i zawsze zostaje masterem', ['Router jest wyłączony', 'Najniższy priorytet', 'Router tylko backup'], 'VIP = adres interfejsu.'],
    ['Czy preempt jest domyślnie włączony w HSRP?', 'Nie', ['Tak', 'Tylko w v2', 'Tylko z MD5'], 'Trzeba włączyć.'],
    ['Czy preempt jest domyślnie włączony w VRRP?', 'Tak', ['Nie', 'Tylko w v3', 'Tylko dla ownera'], 'RFC 5798.'],
    ['Po co stosuje się preempt z opóźnieniem?', 'Aby router po powrocie zdążył zbudować trasy, zanim przejmie ruch', ['Aby szybciej przełączać', 'Aby zmniejszyć priorytet', 'Aby wyłączyć GARP'], 'Uniknięcie czarnej dziury.'],
    ['Jakie są domyślne timery HSRP?', 'Hello 3 s, hold 10 s', ['Hello 1 s, hold 3 s', 'Hello 10 s, hold 40 s', 'Hello 30 s, hold 90 s'], 'Można w ms.'],
    ['Jaki jest domyślny interwał ogłoszeń VRRP?', '1 s', ['3 s', '10 s', '100 ms'], 'advert_int 1.'],
    ['Jak liczy się Master_Down_Interval w VRRPv3?', '3 × Advertisement_Interval + Skew_Time', ['Hello × 4', 'Hold time', '2 × Advertisement_Interval'], 'RFC 5798.'],
    ['Jak liczy się Skew_Time?', '(256 − priorytet) × Advertisement_Interval / 256', ['Priorytet / 256', '256 / priorytet', 'Stałe 1 s'], 'Wyższy priorytet = szybciej.'],
    ['Jaki numer protokołu IP używa VRRP?', '112', ['89', '1985', '47'], 'Multicast 224.0.0.18.'],
    ['Na jaki adres multicast wysyła ogłoszenia VRRP (IPv4)?', '224.0.0.18', ['224.0.0.2', '224.0.0.5', '224.0.0.102'], 'IPv6: ff02::12.'],
    ['Jakiego portu UDP używa HSRP?', '1985 (IPv6: 2029)', ['520', '161', '3222'], 'HSRPv1 na 224.0.0.2.'],
    ['Na jaki adres multicast wysyła HSRPv2?', '224.0.0.102', ['224.0.0.2', '224.0.0.18', '224.0.0.9'], 'v1: 224.0.0.2.'],
    ['Ile grup obsługuje HSRPv2?', '0–4095', ['0–255', '0–15', '1–100'], '12 bitów.'],
    ['Ile VRID obsługuje VRRP?', '1–255', ['0–4095', '1–16', '1–1024'], '8 bitów.'],
    ['Po co tracking w FHRP?', 'Aby obniżyć priorytet, gdy padnie uplink lub sonda', ['Aby szyfrować ogłoszenia', 'Aby zmienić MAC', 'Aby włączyć DHCP'], 'Router bez wyjścia oddaje rolę.'],
    ['Co się stanie bez trackingu, gdy aktywnemu routerowi padnie uplink?', 'Nadal będzie bramą i stworzy czarną dziurę lub okrężną drogę', ['Automatycznie odda rolę', 'Wyłączy się', 'Hosty zmienią bramę'], 'FHRP widzi tylko LAN.'],
    ['Co robi keepalived?', 'Implementuje VRRP (wirtualny IP) i health checki na Linuksie', ['Jest serwerem DNS', 'Jest load balancerem L7', 'Jest protokołem routingu'], 'Często z HAProxy/Nginx.'],
    ['Do czego służy vrrp_script w keepalived?', 'Do sprawdzania usługi i zmiany priorytetu/stanu przy awarii', ['Do konfiguracji DNS', 'Do generowania certyfikatów', 'Do routingu BGP'], 'track_script.'],
    ['Co oznacza nopreempt w keepalived?', 'Węzeł o wyższym priorytecie nie odbiera roli po powrocie', ['Węzeł nigdy nie zostanie masterem', 'Wyłącza VRRP', 'Wyłącza health check'], 'Wymaga state BACKUP.'],
    ['Dlaczego przy nopreempt oba węzły keepalived powinny mieć state BACKUP?', 'Bo MASTER przy starcie od razu przejmuje rolę', ['Bo tak wymaga RFC', 'Bo MASTER nie wysyła ogłoszeń', 'Nie muszą'], 'Inaczej efekt preempt.'],
    ['Czym jest split brain w FHRP?', 'Sytuacją, gdy oba węzły uważają się za aktywne', ['Awarią obu węzłów', 'Podziałem VLAN', 'Brakiem priorytetu'], 'Duplikat VIP.'],
    ['Co najczęściej powoduje split brain w keepalived?', 'Zablokowany ruch VRRP między węzłami (firewall, multicast)', ['Za wysoki priorytet', 'Za krótki interwał', 'Brak DNS'], 'Przepuść protokół 112.'],
    ['Czy połączenia TCP zakończone na starym węźle reverse proxy przetrwają przełączenie VIP?', 'Nie — klient musi nawiązać nowe połączenie', ['Tak, zawsze', 'Tak, dzięki GARP', 'Tylko HTTP/2'], 'Stan TCP był na starym węźle.'],
    ['Czy połączenia przechodzące przez router FHRP (brama, nie proxy) zwykle przetrwają krótkie przełączenie?', 'Tak — routery nie trzymają stanu TCP, retransmisje pokryją przerwę', ['Nie, nigdy', 'Tylko UDP', 'Tylko z preempt'], 'Chyba że firewall stanowy.'],
    ['Co powinien mieć klient Java, by przetrwać przełączenie FHRP/VIP?', 'Krótkie timeouty i retry operacji idempotentnych', ['Nieskończony timeout', 'Wyłączone keep-alive na zawsze', 'Stały adres MAC bramy'], 'Odporność.'],
    ['Dlaczego klasyczny VRRP zwykle nie działa w VPC chmury publicznej?', 'Brak multicastu i obsługi gratuitous ARP; adresy przypisuje platforma', ['Bo chmura nie ma IP', 'Bo VRRP jest płatny', 'Bo VPC nie ma routerów'], 'Używaj LB lub API.'],
    ['Jak zrealizować VIP w AWS przy keepalived?', 'Skryptem notify przenoszącym secondary IP lub Elastic IP przez API', ['Wysyłając GARP', 'Zmieniając MAC instancji', 'Nie da się'], 'Albo NLB.'],
    ['Które narzędzie realizuje wirtualny IP dla Kubernetes on-prem?', 'kube-vip lub MetalLB', ['CoreDNS', 'etcd', 'kubelet'], 'L2 (ARP) lub BGP.'],
    ['Jak zabezpieczyć FHRP przed przejęciem bramy przez atakującego?', 'ACL na portach dostępowych, uwierzytelnianie (HSRP), monitoring zmian ról', ['Wyższy MTU', 'Wyłączenie ARP', 'Zmiana VLAN na 1'], 'Blokuj proto 112 i UDP 1985.'],
    ['Jaki komunikat syslog Cisco informuje o zmianie stanu HSRP?', '%HSRP-5-STATECHANGE', ['%OSPF-5-ADJCHG', '%LINK-3-UPDOWN', '%SYS-5-CONFIG_I'], 'Do SIEM.'],
    ['Które polecenie Cisco pokazuje stan grup HSRP?', 'show standby brief', ['show vrrp interface', 'show hsrp all', 'show ip route standby'], 'VRRP: show vrrp brief.'],
    ['Jak włączyć VRRPv3 na Cisco IOS XE?', 'fhrp version vrrp v3', ['vrrp enable v3', 'ip vrrp 3', 'standby version 3'], 'Potem vrrp N address-family.'],
    ['Jak włączyć HSRPv2 na interfejsie?', 'standby version 2', ['hsrp version 2', 'standby v2 enable', 'ip hsrp 2'], 'Więcej grup, IPv6.'],
    ['Co robi „standby 10 track 1 decrement 20”?', 'Obniża priorytet grupy 10 o 20, gdy obiekt track 1 jest DOWN', ['Ustawia priorytet 20', 'Śledzi 20 interfejsów', 'Zmienia grupę na 20'], 'Tracking.'],
    ['Czy obniżenie priorytetu przez tracking wystarczy, by drugi router przejął rolę?', 'Tylko jeśli drugi router ma włączony preempt', ['Zawsze', 'Nigdy', 'Tylko w VRRP'], 'W HSRP preempt domyślnie wyłączony.'],
    ['Jaki skutek ma brak GARP po przełączeniu VIP w keepalived (VIP z fizycznym MAC węzła)?', 'Hosty wysyłają ruch na MAC starego węzła do wygaśnięcia wpisu ARP', ['Szybsze przełączenie', 'Brak skutków', 'Utrata adresu IP przez nowy węzeł'], 'Dłuższa przerwa.'],
    ['Czy przy FHRP wirtualny MAC przenosi się na nowy router?', 'Tak — nowy aktywny używa tego samego wirtualnego MAC', ['Nie, MAC się zmienia', 'Tylko w GLBP', 'Tylko w IPv6'], 'GARP aktualizuje przełączniki.'],
    ['Jaka jest przewaga VRRP nad HSRP w środowisku wielu producentów?', 'Jest otwartym standardem obsługiwanym przez różnych producentów i Linuksa', ['Jest szybszy zawsze', 'Ma szyfrowanie', 'Nie wymaga adresu IP'], 'Interoperacyjność.'],
    ['Czym różni się uwierzytelnianie w VRRPv2 i VRRPv3?', 'VRRPv3 usunął uwierzytelnianie (polega na izolacji sieci)', ['VRRPv3 dodał TLS', 'Oba używają SHA-256', 'Nie różni się'], 'v2 miał słabe hasło.'],
    ['Jaki typ uwierzytelniania wspiera HSRP?', 'Tekst jawny lub MD5', ['TLS', 'Kerberos', 'Brak'], 'MD5 lepszy niż jawny.'],
    ['Jak zmierzyć w labie przerwę widzianą przez klienta?', 'Sondą wysyłającą żądania w krótkich odstępach i logującą zmiany stanu', ['Jednym pingiem', 'Logami DNS', 'Restartem aplikacji'], 'FailoverProbe.'],
    ['Od czego zależy dokładność pomiaru przerwy sondą?', 'Od okresu sondy (rozdzielczość) i timeoutów', ['Od adresu MAC', 'Od DNS TTL', 'Od długości URL'], '100 ms → ±100 ms.'],
    ['Co może sprawić, że VIP zostanie na węźle z martwym Nginx?', 'Brak health checku (track_script) w keepalived', ['Za niski priorytet', 'Zbyt krótkie advert_int', 'GARP'], 'Awaria usługi ≠ awaria węzła.'],
    ['Jaka jest zaleta łączenia FHRP z BFD?', 'Szybsze wykrycie awarii sąsiada (setki ms)', ['Szyfrowanie', 'Równoważenie ruchu', 'Mniej adresów'], 'Zamiast długiego hold.'],
    ['Jak zapisać w HSRP timery w milisekundach?', 'standby 10 timers msec 200 msec 750', ['standby 10 timers 0.2 0.75', 'standby timers ms 200', 'hsrp timers 200 750'], 'Hello/hold.'],
    ['Do czego służy kilka grup HSRP/VRRP na tym samym VLAN-ie?', 'Do równoważenia — część hostów używa jednej bramy, część drugiej', ['Do szyfrowania', 'Do zwiększenia MTU', 'Nie da się'], 'Ręczny podział.'],
    ['Dlaczego health check w keepalived powinien sprawdzać realną ścieżkę do aplikacji?', 'Bo działający proces proxy bez dostępu do aplikacji nie obsłuży ruchu', ['Bo tak jest szybciej', 'Bo VRRP tego wymaga', 'Nie powinien'], 'Np. curl /healthz.'],
    ['Co wysyła nowy master VRRP zaraz po przejęciu roli?', 'Gratuitous ARP (dla IPv6: Unsolicited NA) dla VIP', ['DHCP Offer', 'ICMP Redirect', 'Nic'], 'Aktualizacja sąsiadów.'],
    ['Jaki element Kubernetes pełni rolę „wirtualnej bramy” dla aplikacji?', 'Service / Ingress z wieloma replikami', ['ConfigMap', 'PersistentVolume', 'Namespace'], 'Abstrakcja adresu.'],
    ['Jak firewall hosta z keepalived powinien traktować VRRP?', 'Przepuszczać protokół 112 od drugiego węzła', ['Blokować cały multicast', 'Przepuszczać tylko TCP', 'Blokować ICMP'], 'Inaczej split brain.'],
    ['Co może zrobić keepalived poza VIP?', 'Uruchamiać skrypty notify przy zmianie stanu (alerty, przenoszenie adresu)', ['Generować certyfikaty', 'Serwować HTTP', 'Rozwiązywać DNS'], 'notify_master.'],
    ['Dlaczego FHRP nie zastępuje load balancera?', 'Przenosi tylko adres między węzłami (aktywny/zapasowy), nie rozkłada ruchu ani nie sprawdza aplikacji (bez dodatków)', ['Bo jest wolniejszy', 'Bo wymaga chmury', 'Zastępuje w pełni'], 'Wysoka dostępność ≠ skalowanie.'],
    ['Jak wykryć split brain w sieci?', 'arping do VIP pokazuje odpowiedzi z dwóch MAC', ['ping 8.8.8.8', 'dig VIP', 'traceroute VIP'], 'Duplikat IP.'],
    ['Co powoduje druga przerwa po naprawie węzła głównego?', 'Preempt — węzeł z wyższym priorytetem odbiera VIP', ['GARP', 'Brak DNS', 'Krótkie timery'], 'nopreempt lub opóźnienie.'],
    ['Jaki czas przełączenia daje VRRPv3 z advert 100 ms?', 'Około 360 ms', ['Około 10 s', 'Około 3,6 s', 'Natychmiast'], '3 × 100 + skew.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
