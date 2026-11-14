// Quiz (100 pytan) do lekcji _ns_03/05_SubnettingDrill — niemal w calosci generowany modelem (to trening).
const { quizBuilder } = require('./lib.cjs');
const D = require('./ns03_05_drill_model.cjs');
const { parse, split, prefixForHosts, summarize, overlaps, size, intIp, ipInt, sub6, count6, net6, fromBig6, toBig6, rng, membership } = D;

module.exports = function buildQuiz() {
  const { quiz, add } = quizBuilder(2026111405);
  const r = rng(424242);
  const near = (ip, d) => intIp((ipInt(ip) + d + 2 ** 32) % 2 ** 32);
  const seen = new Set();

  // 1) Przynaleznosc (20)
  let n = 0;
  while (n < 20) {
    const m = membership(r); const key = m.ip + m.subnet; if (seen.has(key)) continue; seen.add(key);
    const p = parse(m.subnet);
    add(`Czy ${m.ip} należy do ${m.subnet}?`, m.inside ? `Tak — zakres ${p.network}–${p.broadcast}` : `Nie — zakres to ${p.network}–${p.broadcast}`,
      m.inside ? [`Nie — zakres to ${near(p.network, p.size)}–${near(p.broadcast, p.size)}`, `Nie — zakres to ${near(p.network, -p.size)}–${near(p.broadcast, -p.size)}`, 'Nie — adres jest broadcastem']
        : [`Tak — zakres ${near(p.network, -p.size)}–${near(p.broadcast, p.size)}`, `Tak — zakres ${p.network}–${near(p.broadcast, p.size)}`, `Tak — zakres ${near(p.network, -p.size)}–${p.broadcast}`],
      `Blok ${p.size} adresów: ${p.network}–${p.broadcast}.`);
    n++;
  }

  // 2) Siec / broadcast (losowe, 24)
  for (let i = 0; i < 12; i++) {
    const c = `${D.randomIp(r)}/${17 + Math.floor(r() * 13)}`; const p = parse(c);
    if (seen.has(c)) { i--; continue; } seen.add(c);
    add(`[${i + 1}] Adres sieci dla ${c}?`, p.network, [p.ip, near(p.network, p.size), near(p.network, -p.size), p.broadcast, near(p.network, p.size / 2)], `Wielokrotność bloku ${p.size} w dół.`);
    add(`[${i + 1}] Broadcast dla ${c}?`, p.broadcast, [near(p.broadcast, 1), near(p.broadcast, -1), near(p.broadcast, p.size), near(p.broadcast, -p.size / 2), p.network], `Sieć + ${p.size} − 1.`);
  }

  // 3) Hosty i prefiks (12)
  for (const p of [18, 20, 22, 23, 25, 27]) add(`Ile hostów (klasycznie) w /${p}?`, String(size(p) - 2), [String(size(p)), String(size(p) / 2 - 2), String(size(p) * 2 - 2), String(size(p) - 5)], `2^${32 - p} − 2.`);
  for (const h of [100, 300, 700, 2500, 14, 60]) { const p = prefixForHosts(h); add(`Najmniejszy prefiks na ${h} hostów?`, `/${p}`, [`/${p + 1}`, `/${p - 1}`, `/${p + 2}`, `/${p - 2}`], `/${p}: ${size(p) - 2} hostów, /${p + 1}: ${size(p + 1) - 2}.`); }

  // 4) Nakladanie (8)
  for (const [a, b] of [['10.20.64.0/18', '10.20.100.0/22'], ['10.20.64.0/18', '10.20.128.0/22'], ['192.168.4.0/23', '192.168.5.128/25'], ['192.168.4.0/23', '192.168.6.0/24'], ['172.16.0.0/12', '172.31.255.0/24'], ['172.16.0.0/12', '172.32.0.0/16'], ['100.64.0.0/10', '100.127.0.0/16'], ['10.0.0.0/9', '10.128.0.0/9']]) {
    const o = overlaps(a, b);
    add(`Czy ${a} i ${b} się nakładają?`, o ? 'Tak (jeden zawiera drugi)' : 'Nie', o ? ['Nie', 'Tak, częściowo', 'Nie da się stwierdzić'] : ['Tak (jeden zawiera drugi)', 'Tak, częściowo', 'Nie da się stwierdzić'], o ? 'Wyrównane bloki: zawieranie.' : 'Rozłączne zakresy.');
  }

  // 5) N-ta podsiec (8)
  for (const [base, np, k] of [['10.20.0.0/16', 20, 7], ['10.20.0.0/16', 22, 25], ['192.168.8.0/22', 25, 5], ['172.16.0.0/16', 19, 6], ['10.0.0.0/8', 16, 200], ['192.168.0.0/24', 28, 11], ['172.30.0.0/20', 24, 9], ['10.99.0.0/18', 23, 30]]) {
    const parts = split(base, np);
    add(`Podsieć nr ${k} (od 0) po podziale ${base} na /${np}?`, parts[k], [parts[k - 1], parts[k + 1] || parts[0], `${intIp(parse(base).net + k * 256)}/${np}`, split(base, np + 1)[k]], `${k} × ${size(np)} od początku.`);
  }

  // 6) Agregacja (8)
  for (const set of [['10.0.8.0/24', '10.0.9.0/24', '10.0.10.0/24', '10.0.11.0/24'], ['172.16.16.0/21', '172.16.24.0/21'], ['192.168.2.0/24', '192.168.3.0/24'], ['10.1.0.0/16', '10.2.0.0/16'], ['10.4.0.0/16', '10.5.0.0/16', '10.6.0.0/16', '10.7.0.0/16'], ['192.168.100.0/25', '192.168.100.128/25'], ['172.20.32.0/20', '172.20.48.0/20'], ['10.10.4.0/22', '10.10.8.0/22']]) {
    const s = summarize(set); const p = parse(s.cidr);
    add(`Najkrótszy wspólny prefiks dla ${set.join(', ')}?`, s.cidr, [`${p.network}/${p.prefix + 1}`, `${p.network}/${p.prefix - 1}`, set[0], `${intIp(p.net + p.size)}/${p.prefix}`].filter((x) => x !== s.cidr), s.exact ? 'Agregat dokładny.' : `Niedokładny: +${s.extra} adresów.`);
  }

  // 7) IPv6 (12)
  for (const [p, np] of [[48, 64], [56, 64], [48, 56], [32, 48], [60, 64], [52, 64]]) add(`Ile podsieci /${np} mieści prefiks IPv6 /${p}?`, count6(p, np).toLocaleString('pl-PL'), [count6(p, np) * 2, count6(p, np) / 2, np - p, 2 ** (np - p - 4)].map((x) => x.toLocaleString('pl-PL')), `2^(${np} − ${p}).`);
  for (const [base, k] of [['2001:db8:77::', 42], ['2001:db8:abcd::', 255], ['2001:db8:5::', 4096]]) add(`Podsieć /64 nr ${k} w ${base}/48?`, sub6(base, 48, 64, k), [sub6(base, 48, 64, k + 1), sub6(base, 48, 64, Number('0x' + String(k))), sub6(base, 48, 56, k > 255 ? 1 : k).replace('/56', '/64'), `${base}${k}/64`].filter((x, i, a) => a.indexOf(x) === i), `${k} = 0x${k.toString(16)} w czwartej grupie.`);
  for (const a of ['2001:0db8:0000:0000:0001:0000:0000:00ff', '2001:0db8:0000:0042:0000:0000:0000:0001', 'fe80:0000:0000:0000:0a00:27ff:fe4e:66a1']) {
    const s = fromBig6(toBig6(a));
    add(`Poprawne skrócenie (RFC 5952) adresu ${a}?`, s, [a.replace(/(^|:)0+([0-9a-f])/g, '$1$2'), s.replace('::', ':0:'), s.replace(/::/, ':').replace(/:(?=[^:]*$)/, '::'), s.toUpperCase()].filter((x) => x !== s), 'Zera wiodące precz, jeden najdłuższy ciąg zer → ::, małe litery.');
  }

  // 8) Pytania o metode (8)
  const facts = [
    ['Który oktet jest „interesujący” dla prefiksu /20?', 'Trzeci', ['Czwarty', 'Drugi', 'Pierwszy'], '16 < 20 ≤ 24.'],
    ['Który oktet jest „interesujący” dla prefiksu /12?', 'Drugi', ['Trzeci', 'Pierwszy', 'Czwarty'], '8 < 12 ≤ 16.'],
    ['Jaka jest magiczna liczba dla oktetu maski 248?', '8', ['248', '7', '16'], '256 − 248.'],
    ['Które wartości może przyjmować oktet maski?', '0, 128, 192, 224, 240, 248, 252, 254, 255', ['Dowolna od 0 do 255', 'Tylko 0 i 255', 'Wielokrotności 8'], 'Jedynki zawsze z lewej.'],
    ['Kiedy w treningu użyć kalkulatora podsieci?', 'Dopiero po udzieleniu własnej odpowiedzi — jako sędziego', ['Zawsze przed odpowiedzią', 'Nigdy', 'Tylko dla IPv6'], 'Zasada z planu lekcji.'],
    ['Co najczęściej oznacza błąd „broadcast = następna sieć”?', 'Zapomniano odjąć 1 od początku następnego bloku', ['Zły oktet', 'Błędny prefiks', 'Brak −2 hostów'], 'Broadcast = sieć + rozmiar − 1.'],
    ['Jaki prefiks IPv6 ma typowo sieć LAN?', '/64', ['/48', '/56', '/128'], 'Wymaga go SLAAC.'],
    ['Dlaczego granice prefiksów IPv6 planuje się na wielokrotnościach 4 bitów?', 'Bo wtedy granica wypada na pełnej cyfrze hex i łatwo ją odczytać', ['Bo tak wymaga RFC 791', 'Bo routery nie obsługują innych', 'Bo IPv6 ma 4 bajty'], 'Np. /52, /56, /60, /64.'],
  ];
  for (const [q, a, w, e] of facts) add(q, a, w, e);
  if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);
  return quiz;
};
