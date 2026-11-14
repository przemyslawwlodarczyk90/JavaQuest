// Model do lekcji _ns_03/05 (trening podsieciowania): deterministyczny generator zadan IPv4 i IPv6.
const M = require('./ns03_04_cidr_model.cjs');

// Prosty LCG — te same zadania przy kazdym generowaniu.
function rng(seed) {
  let s = seed;
  return () => { s = (s * 1103515245 + 12345) % 2147483648; return s / 2147483648; };
}

function randomIp(r, prefixMin = 8) {
  const firsts = [10, 172, 192, 100, 198];
  const f = firsts[Math.floor(r() * firsts.length)];
  const o2 = f === 172 ? 16 + Math.floor(r() * 16) : f === 192 ? 168 : f === 100 ? 64 + Math.floor(r() * 64) : f === 198 ? 51 : Math.floor(r() * 256);
  return `${f}.${o2}.${Math.floor(r() * 256)}.${1 + Math.floor(r() * 254)}`;
}

// Zadanie: przynaleznosc adresu do podsieci
function membership(r) {
  const p = 17 + Math.floor(r() * 13);
  const host = randomIp(r);
  const net = M.parse(`${host}/${p}`);
  const inside = r() < 0.5;
  // poza siecia: tuz obok (najczestszy blad) — w sasiednim bloku przed lub po
  const off = inside ? Math.floor(r() * net.size) : (r() < 0.5 ? net.size + Math.floor(r() * Math.min(net.size, 300)) : -1 - Math.floor(r() * Math.min(net.size, 300)));
  const cand = M.intIp((net.net + off + 2 ** 32) % 2 ** 32);
  const candInside = M.ipInt(cand) >= net.net && M.ipInt(cand) < net.net + net.size;
  return { subnet: net.cidr, ip: cand, inside: candInside };
}

// ---------- IPv6 ----------
const expand6 = (a) => {
  let [head, tail] = a.split('::');
  const h = head ? head.split(':') : [];
  const t = tail !== undefined ? (tail ? tail.split(':') : []) : [];
  const fill = tail !== undefined ? Array(8 - h.length - t.length).fill('0') : [];
  return [...h, ...fill, ...t].map((x) => x.padStart(4, '0'));
};
const toBig6 = (a) => expand6(a).reduce((acc, g) => (acc << 16n) + BigInt(parseInt(g, 16)), 0n);
function fromBig6(n) {
  const g = [];
  for (let i = 0; i < 8; i++) { g.unshift(Number(n & 0xffffn).toString(16)); n >>= 16n; }
  // skrocenie: najdluzszy ciag zer (min. 2 grupy) → ::
  let best = -1, bestLen = 0;
  for (let i = 0; i < 8;) {
    if (g[i] === '0') { let j = i; while (j < 8 && g[j] === '0') j++; if (j - i > bestLen && j - i >= 2) { best = i; bestLen = j - i; } i = j; } else i++;
  }
  if (best < 0) return g.join(':');
  return `${g.slice(0, best).join(':')}::${g.slice(best + bestLen).join(':')}`;
}
// n-ta podsiec /newP w bloku base/p
function sub6(base, p, newP, n) {
  const net = (toBig6(base) >> BigInt(128 - p)) << BigInt(128 - p);
  return `${fromBig6(net + (BigInt(n) << BigInt(128 - newP)))}/${newP}`;
}
const count6 = (p, newP) => 2 ** (newP - p);
const net6 = (addr, p) => `${fromBig6((toBig6(addr) >> BigInt(128 - p)) << BigInt(128 - p))}/${p}`;

module.exports = { ...M, rng, randomIp, membership, expand6, toBig6, fromBig6, sub6, count6, net6 };
