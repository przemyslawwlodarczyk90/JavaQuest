// Model do lekcji _ns_03/06: rejestr adresow specjalnych IPv4/IPv6 (IANA, RFC 6890 i nastepne) i klasyfikator.
const { ipInt, parse } = require('./ns03_04_cidr_model.cjs');
const { toBig6 } = require('./ns03_05_drill_model.cjs');

// [prefiks, nazwa, RFC, czy routowalny w Internecie, uwagi]
const V4 = [
  ['0.0.0.0/8', '„ta sieć” (0.0.0.0 = dowolny adres przy bind)', 'RFC 791/1122', false, 'nie jako cel'],
  ['10.0.0.0/8', 'prywatny', 'RFC 1918', false, ''],
  ['100.64.0.0/10', 'współdzielony (CGNAT)', 'RFC 6598', false, 'przestrzeń operatora'],
  ['127.0.0.0/8', 'pętla zwrotna (loopback)', 'RFC 1122', false, 'nigdy nie opuszcza hosta'],
  ['169.254.0.0/16', 'link-local (APIPA)', 'RFC 3927', false, '169.254.169.254 = metadane chmury'],
  ['172.16.0.0/12', 'prywatny', 'RFC 1918', false, ''],
  ['192.0.0.0/24', 'przypisania protokołów IETF', 'RFC 6890', false, ''],
  ['192.0.2.0/24', 'dokumentacyjny TEST-NET-1', 'RFC 5737', false, ''],
  ['192.88.99.0/24', '6to4 relay anycast (wycofany)', 'RFC 7526', false, ''],
  ['192.168.0.0/16', 'prywatny', 'RFC 1918', false, ''],
  ['198.18.0.0/15', 'testy wydajności (benchmark)', 'RFC 2544', false, ''],
  ['198.51.100.0/24', 'dokumentacyjny TEST-NET-2', 'RFC 5737', false, ''],
  ['203.0.113.0/24', 'dokumentacyjny TEST-NET-3', 'RFC 5737', false, ''],
  ['224.0.0.0/4', 'multicast', 'RFC 5771', true, '224.0.0.0/24 tylko lokalnie'],
  ['240.0.0.0/4', 'zarezerwowany (dawna klasa E)', 'RFC 1112', false, ''],
  ['255.255.255.255/32', 'ograniczony broadcast', 'RFC 919', false, 'nie przechodzi przez router'],
];
const V6 = [
  ['::/128', 'adres nieokreślony (jak 0.0.0.0)', 'RFC 4291'],
  ['::1/128', 'pętla zwrotna', 'RFC 4291'],
  ['::ffff:0:0/96', 'IPv4 mapowany na IPv6', 'RFC 4291'],
  ['64:ff9b::/96', 'NAT64 (prefiks dobrze znany)', 'RFC 6052'],
  ['2001:db8::/32', 'dokumentacyjny', 'RFC 3849'],
  ['2000::/3', 'globalne unicast (GUA)', 'RFC 4291'],
  ['fc00::/7', 'unikalne lokalne (ULA, w praktyce fd00::/8)', 'RFC 4193'],
  ['fe80::/10', 'link-local', 'RFC 4291'],
  ['ff00::/8', 'multicast', 'RFC 4291'],
];

function in4(ip, cidr) { const p = parse(cidr); const v = ipInt(ip); return v >= p.net && v < p.net + p.size; }
function classify4(ip) {
  const hits = V4.filter(([c]) => in4(ip, c)).sort((a, b) => parse(b[0]).prefix - parse(a[0]).prefix);
  return hits.length ? { range: hits[0][0], name: hits[0][1], rfc: hits[0][2], global: false && hits[0][3] } : { range: '—', name: 'publiczny (globalny unicast)', rfc: '—', global: true };
}
// zamiana koncowki w zapisie kropkowym (::ffff:192.168.1.1) na dwie grupy hex
const norm6 = (ip) => ip.replace(/(\d+)\.(\d+)\.(\d+)\.(\d+)$/, (_, a, b, c, d) => `${((+a << 8) | +b).toString(16)}:${((+c << 8) | +d).toString(16)}`);
function in6(ip, cidr) {
  const [a, p] = cidr.split('/'); const bits = BigInt(128 - Number(p));
  return (toBig6(norm6(ip)) >> bits) === (toBig6(a) >> bits);
}
function classify6(ip) {
  const hits = V6.filter(([c]) => in6(ip, c)).sort((a, b) => Number(b[0].split('/')[1]) - Number(a[0].split('/')[1]));
  return hits.length ? { range: hits[0][0], name: hits[0][1], rfc: hits[0][2] } : { range: '—', name: 'inny / zarezerwowany', rfc: '—' };
}
// Czy adres jest „wewnetrzny” z perspektywy ochrony przed SSRF (blokuj, gdy true)
const ssrfBlocked4 = (ip) => !classify4(ip).global;

module.exports = { V4, V6, classify4, classify6, in4, in6, ssrfBlocked4 };
