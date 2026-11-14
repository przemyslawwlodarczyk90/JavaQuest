// Quiz (100 pytan) do lekcji _ns_03/04_CidrAndSubnetting. Pytania liczbowe licza sie modelem.
const { quizBuilder } = require('./lib.cjs');
const { parse, split, prefixForHosts, summarize, overlaps, maskOf, wildcardOf, size, intIp} = require('./ns03_04_cidr_model.cjs');

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111404);
  const near = (ip, delta) => intIp(Math.max(0, require('./ns03_04_cidr_model.cjs').ipInt(ip) + delta));

  // 1) Adres sieci i broadcast (2 x 12 = 24)
  const addrs = ['10.20.37.200/19', '172.16.45.130/21', '192.168.77.99/27', '10.1.255.1/23', '100.64.12.34/10', '192.168.1.190/26',
    '10.0.130.7/17', '172.31.200.200/20', '192.168.5.5/30', '10.99.99.99/12', '172.18.3.77/25', '10.10.10.10/28'];
  for (const c of addrs) {
    const p = parse(c);
    add(`Jaki jest adres sieci dla ${c}?`, p.network, [p.ip, near(p.network, p.size), near(p.network, -p.size), p.broadcast, near(p.network, 1)], `Magiczna liczba / wielokrotność rozmiaru ${p.size}.`);
    add(`Jaki jest broadcast podsieci ${c}?`, p.broadcast, [near(p.broadcast, -1), near(p.broadcast, p.size), p.network, near(p.broadcast, -p.size), near(p.broadcast, 1)], `Sieć ${p.network} + ${p.size} − 1.`);
  }

  // 2) Maska i wildcard (2 x 8 = 16)
  for (const n of [17, 19, 20, 22, 25, 26, 27, 29]) {
    add(`Jaka maska odpowiada prefiksowi /${n}?`, maskOf(n), [maskOf(n - 1), maskOf(n + 1), wildcardOf(n), maskOf(n + 8 > 32 ? n - 8 : n + 8)], `/${n} = ${n} jedynek.`);
    add(`Jaki wildcard odpowiada prefiksowi /${n}?`, wildcardOf(n), [maskOf(n), wildcardOf(n - 1), wildcardOf(n + 1), wildcardOf(n + 2)], `255.255.255.255 − ${maskOf(n)}.`);
  }

  // 3) Liczba hostow i prefiks dla H hostow (8 + 8)
  for (const n of [18, 21, 23, 24, 26, 28, 30, 31]) {
    const right = n === 31 ? '2 (RFC 3021, bez sieci i broadcastu)' : String(size(n) - 2);
    add(`Ile adresów hostów daje prefiks /${n} (klasycznie)?`, right, [String(size(n)), String(size(n) - 1), String(size(n) / 2), String(size(n) - 5), String(size(n) * 2 - 2)].filter((x) => x !== right), `2^${32 - n}${n === 31 ? '' : ' − 2'}.`);
  }
  for (const [h, r] of [[50, 2], [120, 2], [130, 2], [500, 2], [1000, 2], [11, 5], [12, 5], [250, 5]]) {
    const p = prefixForHosts(h, r);
    add(`Najmniejszy prefiks dla ${h} hostów${r === 5 ? ' w podsieci AWS (5 adresów zarezerwowanych)' : ''}?`, `/${p}`, [`/${p + 1}`, `/${p - 1}`, `/${p + 2}`, `/${p - 2}`], `/${p} daje ${size(p) - r} adresów, /${p + 1} tylko ${size(p + 1) - r}.`);
  }

  // 4) Podzial (8)
  for (const [base, np, idx] of [['10.20.0.0/16', 18, 2], ['10.20.0.0/16', 19, 5], ['172.16.8.0/22', 24, 3], ['192.168.50.0/24', 27, 6], ['10.0.0.0/8', 12, 9], ['192.168.0.0/16', 20, 15], ['172.20.0.0/23', 26, 4], ['10.64.0.0/14', 16, 2]]) {
    const parts = split(base, np);
    add(`${base} podzielono na podsieci /${np}. Jaki adres ma podsieć numer ${idx} (licząc od 0)?`, parts[idx],
      [parts[idx - 1] || parts[idx + 2], parts[idx + 1] || parts[0], `${intIp(parse(parts[idx]).net + 256 * idx)}/${np}`, split(base, np + 1)[idx], split(base, np - 1)[Math.min(idx, 1)]],
      `Rozmiar /${np} = ${size(np)}; ${idx} × ${size(np)} od początku bloku.`);
  }
  for (const [base, np] of [['10.20.0.0/16', 20], ['172.16.0.0/12', 16], ['192.168.10.0/24', 28], ['10.0.0.0/22', 26]]) {
    const n = 2 ** (np - parse(base).prefix);
    add(`Ile podsieci /${np} mieści ${base}?`, String(n), [String(n * 2), String(n / 2), String(np - parse(base).prefix), String(n - 2)], `2^(${np} − ${parse(base).prefix}).`);
  }

  // 5) Agregacja i nakladanie (8)
  for (const set of [['10.1.4.0/24', '10.1.5.0/24', '10.1.6.0/24', '10.1.7.0/24'], ['172.16.32.0/21', '172.16.40.0/21'], ['192.168.0.0/24', '192.168.1.0/24'], ['10.8.0.0/16', '10.9.0.0/16', '10.10.0.0/16', '10.11.0.0/16']]) {
    const s = summarize(set); const p = parse(s.cidr);
    add(`Jaka jest najkrótsza wspólna trasa (agregat) dla: ${set.join(', ')}?`, s.cidr,
      [`${p.network}/${p.prefix + 1}`, `${p.network}/${p.prefix - 1}`, `${parse(set[0]).network}/${parse(set[0]).prefix}`, `${p.network}/${p.prefix - 2}`], 'Najdłuższy wspólny prefiks wszystkich sieci.');
  }
  for (const [a, b] of [['172.17.0.0/16', '172.16.0.0/12'], ['10.244.0.0/16', '10.20.0.0/16'], ['192.168.1.0/24', '192.168.0.0/23'], ['10.20.96.0/22', '10.20.100.0/22']]) {
    const o = overlaps(a, b);
    add(`Czy zakresy ${a} i ${b} się nakładają?`, o ? 'Tak' : 'Nie', o ? ['Nie', 'Tylko jeśli są w tej samej VPC', 'Nie da się określić bez bramy'] : ['Tak', 'Tak, bo mają ten sam pierwszy oktet', 'Nie da się określić bez bramy'], o ? 'Węższy zakres leży wewnątrz szerszego.' : 'Zakresy są rozłączne.');
  }

  // 6) Pytania merytoryczne (28)
  const facts = [
    ['Co oznacza zapis 10.20.0.0/16?', 'Pierwsze 16 bitów to część sieciowa, pozostałe 16 to hosty', ['Sieć ma 16 hostów', 'Maska to 255.255.255.16', 'To klasa A z 16 podsieciami'], 'CIDR.'],
    ['Jak obliczyć wildcard z maski?', '255.255.255.255 minus maska', ['Maska plus 1', 'Odwrócenie kolejności oktetów', 'Maska podzielona przez 2'], 'Np. /24 → 0.0.0.255.'],
    ['Gdzie używa się wildcardów?', 'W ACL Cisco i poleceniach network w OSPF', ['W DHCP', 'W DNS', 'W nagłówku IP'], 'Maska odwrotna.'],
    ['Co to jest VLSM?', 'Podsieci różnej długości w jednym bloku adresów', ['Wirtualny VLAN', 'Protokół routingu', 'Typ NAT'], 'Variable Length Subnet Mask.'],
    ['Dlaczego w VLSM przydziela się podsieci od największej?', 'Aby każda podsieć wypadła na wyrównanej granicy bez dziur', ['Bo routery tego wymagają', 'Aby zmniejszyć broadcast', 'Bo DHCP działa tylko tak'], 'Małe podsieci mieszczą się w resztkach.'],
    ['Od jakich adresów może zaczynać się podsieć /20?', 'Od wielokrotności 4096 adresów (trzeci oktet 0, 16, 32…)', ['Od dowolnego adresu', 'Od wielokrotności 20', 'Od adresów kończących się na .0'], 'Wyrównanie.'],
    ['Co dzieje się z ruchem, gdy dwa pasujące prefiksy są w tablicy routingu?', 'Wygrywa najdłuższy pasujący prefiks', ['Wygrywa krótszy prefiks', 'Ruch jest dzielony po połowie', 'Wygrywa trasa dodana pierwsza'], 'Longest prefix match.'],
    ['Ile adresów rezerwuje AWS w każdej podsieci VPC?', '5', ['2', '1', '10'], 'Sieć, router, DNS, przyszłe użycie, broadcast.'],
    ['Jaki jest największy dozwolony blok CIDR dla VPC w AWS?', '/16', ['/8', '/12', '/20'], 'Najmniejszy /28.'],
    ['Jaka jest najmniejsza podsieć w AWS?', '/28', ['/30', '/31', '/24'], '16 adresów, 11 do użycia.'],
    ['Jaki domyślny zakres ma sieć docker0?', '172.17.0.0/16', ['10.0.0.0/8', '192.168.0.0/16', '100.64.0.0/10'], 'Zmienisz go w daemon.json (bip).'],
    ['Czym grozi nakładanie się zakresu Dockera z zakresem firmowego VPN?', 'Ruch do części serwerów firmy trafia na lokalny bridge Dockera', ['Szybszym VPN', 'Utratą DNS', 'Zmianą MAC'], 'Dłuższy prefiks wygrywa.'],
    ['Dlaczego nie można zestawić peeringu dwóch VPC z tym samym CIDR?', 'Adresy byłyby niejednoznaczne — routing nie wiedziałby, którą sieć wybrać', ['Bo peering wymaga IPv6', 'Bo AWS pobiera opłatę', 'Bo VPC muszą być w tym samym regionie'], 'Planuj zakresy z góry.'],
    ['Co robi funkcja Terraform cidrsubnet("10.20.0.0/16", 3, 1)?', 'Zwraca drugą podsieć /19: 10.20.32.0/19', ['Zwraca 10.20.3.0/24', 'Zwraca 10.20.1.0/19', 'Zwraca 10.23.0.0/16'], 'newbits 3, netnum 1.'],
    ['Co oznacza agregacja tras?', 'Ogłoszenie wielu sąsiednich sieci jednym krótszym prefiksem', ['Dzielenie sieci na mniejsze', 'Szyfrowanie tras', 'Łączenie VLAN-ów'], 'Supernetting.'],
    ['Kiedy agregacja tras jest niebezpieczna?', 'Gdy agregat obejmuje adresy, których nie posiadasz', ['Gdy sieci są ciągłe', 'Gdy używasz OSPF', 'Gdy prefiks jest dłuższy niż /24'], 'Czarna dziura.'],
    ['Co opisuje RFC 3021?', 'Użycie prefiksu /31 na łączach punkt-punkt', ['Adresy prywatne', 'CIDR', 'DHCP'], 'Oszczędność adresów.'],
    ['Jak w Pythonie uzyskać sieć z adresu z bitami hosta (np. 10.20.37.200/19)?', 'ipaddress.ip_network("10.20.37.200/19", strict=False)', ['ipaddress.ip_address("10.20.37.200/19")', 'socket.inet_aton("10.20.37.200/19")', 'int("10.20.37.200/19")'], 'strict=True zgłosi błąd.'],
    ['Dlaczego podsieci prywatne dla EKS planuje się szeroko (np. /19)?', 'Bo z VPC CNI każdy pod dostaje adres z podsieci', ['Bo EKS wymaga /19', 'Bo pody używają IPv6', 'Bo ALB potrzebuje tysięcy adresów'], 'Wyczerpanie adresów blokuje skalowanie.'],
    ['Dlaczego warto układać warstwy (public/private/db) w ciągłych blokach?', 'Aby reguły bezpieczeństwa i trasy obejmowały warstwę jednym prefiksem', ['Bo AWS tego wymaga', 'Aby zmniejszyć MTU', 'Bo DNS tego potrzebuje'], 'Proste ACL i NACL.'],
    ['Dlaczego security group z 10.0.0.0/8 to zły pomysł?', 'Wpuszcza ruch z każdej sieci 10.x — innych VPC, VPN, biura', ['Bo /8 nie jest dozwolone', 'Bo blokuje cały ruch', 'Bo dotyczy tylko IPv6'], 'Najmniejsze uprawnienia.'],
    ['Czy po utworzeniu podsieci w AWS można zmienić jej CIDR?', 'Nie — trzeba utworzyć nową podsieć i przenieść zasoby', ['Tak, dowolnie', 'Tak, tylko zmniejszyć', 'Tak, przez restart VPC'], 'Planuj zapas.'],
    ['Co można dodać do VPC, gdy kończą się adresy?', 'Dodatkowy (secondary) blok CIDR', ['Drugą maskę do tej samej podsieci', 'Większy MTU', 'Nowy internet gateway'], 'Np. 100.64.0.0/16 dla podów.'],
    ['Co się stanie, jeśli wpiszesz w AWS podsieć 10.20.8.0/20?', 'Zostanie odrzucona jako nieprawidłowy (niewyrównany) CIDR', ['Zostanie utworzona od 10.20.8.0', 'Zostanie zamieniona na /24', 'AWS doda brakujące adresy'], '/20 zaczyna się od 10.20.0.0 lub 10.20.16.0.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
