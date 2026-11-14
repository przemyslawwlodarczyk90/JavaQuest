// Model do lekcji _ns_03/04 (CIDR, maski i podsieci): parsowanie CIDR, maska/wildcard, podzial, VLSM,
// agregacja (supernetting), nakladanie sie zakresow, projekt VPC. Wszystkie liczby w lekcji pochodza stad.
const ipInt = (ip) => ip.split('.').reduce((a, o) => a * 256 + Number(o), 0);
const intIp = (n) => [24, 16, 8, 0].map((s) => Math.floor(n / 2 ** s) % 256).join('.');
const size = (p) => 2 ** (32 - p);
const maskOf = (p) => intIp(2 ** 32 - size(p));
const wildcardOf = (p) => intIp(size(p) - 1);
const prefixOfMask = (m) => 32 - Math.log2(2 ** 32 - ipInt(m));

function parse(cidr) {
  const [ip, p] = cidr.split('/');
  const prefix = Number(p);
  const net = Math.floor(ipInt(ip) / size(prefix)) * size(prefix);
  return { ip, prefix, net, size: size(prefix), mask: maskOf(prefix), wildcard: wildcardOf(prefix),
    network: intIp(net), broadcast: intIp(net + size(prefix) - 1),
    first: intIp(net + 1), last: intIp(net + size(prefix) - 2), hosts: Math.max(size(prefix) - 2, 0),
    aligned: ipInt(ip) === net, cidr: `${intIp(net)}/${prefix}` };
}

// Podzial sieci na 2^k rownych podsieci o prefiksie newPrefix.
function split(cidr, newPrefix) {
  const { net, prefix } = parse(cidr);
  const out = [];
  for (let i = 0; i < 2 ** (newPrefix - prefix); i++) out.push(`${intIp(net + i * size(newPrefix))}/${newPrefix}`);
  return out;
}

// Najmniejszy prefiks mieszczacy n hostow (klasycznie: -2 na siec i broadcast; reserved = ile adresow rezerwuje platforma).
const prefixForHosts = (n, reserved = 2) => { let p = 32; while (size(p) - reserved < n) p--; return p; };

// VLSM: przydzial od najwiekszych potrzeb, kazda podsiec wyrownana do swojego rozmiaru.
function vlsm(base, needs, reserved = 2) {
  const b = parse(base);
  let cursor = b.net;
  const sorted = needs.map((x, i) => ({ ...x, i })).sort((a, c) => c.hosts - a.hosts || a.i - c.i);
  const result = [];
  for (const n of sorted) {
    const p = prefixForHosts(n.hosts, reserved);
    cursor = Math.ceil(cursor / size(p)) * size(p);
    if (cursor + size(p) > b.net + b.size) throw new Error('brak miejsca: ' + n.name);
    result.push({ name: n.name, need: n.hosts, cidr: `${intIp(cursor)}/${p}`, usable: size(p) - reserved, first: intIp(cursor + 1), last: intIp(cursor + size(p) - 2) });
    cursor += size(p);
  }
  return { result, used: cursor - b.net, free: b.net + b.size - cursor };
}

// Agregacja: najmniejszy wspolny prefiks obejmujacy wszystkie sieci (+ czy jest „dokladny”, bez nadmiarowych adresow).
function summarize(cidrs) {
  const ps = cidrs.map(parse);
  const lo = Math.min(...ps.map((x) => x.net)), hi = Math.max(...ps.map((x) => x.net + x.size - 1));
  let p = 32;
  while (p > 0 && !(Math.floor(lo / size(p)) === Math.floor(hi / size(p)))) p--;
  const net = Math.floor(lo / size(p)) * size(p);
  const covered = ps.reduce((a, x) => a + x.size, 0);
  return { cidr: `${intIp(net)}/${p}`, exact: covered === size(p), extra: size(p) - covered };
}

const overlaps = (a, b) => { const x = parse(a), y = parse(b); return x.net < y.net + y.size && y.net < x.net + x.size; };
const contains = (outer, inner) => { const o = parse(outer), i = parse(inner); return i.net >= o.net && i.net + i.size <= o.net + o.size; };

// Projekt VPC 10.20.0.0/16: 3 AZ x (public, private, db) — wariant z lekcji.
function vpcPlan(base = '10.20.0.0/16', azs = ['a', 'b', 'c']) {
  const b = parse(base);
  const tiers = [['private', 19], ['public', 22], ['db', 24]];
  const plan = [];
  // private: /19 (8190 adresow) na AZ od 10.20.0.0; public: /22 od 10.20.96.0; db: /24 od 10.20.112.0
  const starts = { private: b.net, public: b.net + 96 * 256, db: b.net + 112 * 256 };
  for (const [tier, p] of tiers) azs.forEach((az, i) => plan.push({ az, tier, cidr: `${intIp(starts[tier] + i * size(p))}/${p}`, aws: size(p) - 5 }));
  const used = plan.reduce((a, x) => a + parse(x.cidr).size, 0);
  return { plan, used, free: b.size - used, reserve: `${intIp(b.net + 128 * 256)}/17` };
}

module.exports = { ipInt, intIp, size, maskOf, wildcardOf, prefixOfMask, parse, split, prefixForHosts, vlsm, summarize, overlaps, contains, vpcPlan };
