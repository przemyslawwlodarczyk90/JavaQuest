// Minimalny symulator STP (802.1D/802.1w) do lekcji _ns_02/07: wybór root bridge, root portów, designated portów i portów blokowanych.
// Topologia: switches = [{ n, prio, mac }], links = [[swA, portA, swB, portB, speed]].
// Port ID = 128.<numer portu> (domyslny priorytet portu), porownanie po numerze portu.
const COST_SHORT = { '10M': 100, '100M': 19, '1G': 4, '10G': 2, '100G': 1 };
const COST_LONG = { '10M': 2000000, '100M': 200000, '1G': 20000, '10G': 2000, '100G': 200 };

const macNum = (m) => parseInt(m.replace(/[:.]/g, ''), 16);
const bidCmp = (a, b) => (a.prio - b.prio) || (macNum(a.mac) - macNum(b.mac));

function stp(switches, links, costs = COST_SHORT) {
  const sw = Object.fromEntries(switches.map((s) => [s.n, s]));
  const root = [...switches].sort(bidCmp)[0].n;
  const adj = {}; switches.forEach((s) => { adj[s.n] = []; });
  links.forEach(([a, pa, b, pb, sp], i) => {
    const c = costs[sp];
    adj[a].push({ me: a, port: pa, nb: b, nbPort: pb, cost: c, link: i });
    adj[b].push({ me: b, port: pb, nb: a, nbPort: pa, cost: c, link: i });
  });
  // Dijkstra kosztu do roota
  const dist = {}; switches.forEach((s) => { dist[s.n] = Infinity; }); dist[root] = 0;
  const done = new Set();
  while (done.size < switches.length) {
    const u = switches.map((s) => s.n).filter((n) => !done.has(n)).sort((x, y) => dist[x] - dist[y])[0];
    done.add(u);
    for (const e of adj[u]) if (dist[u] + e.cost < dist[e.nb]) dist[e.nb] = dist[u] + e.cost;
  }
  // Root port: najnizszy (koszt przez sasiada, BID sasiada, port sasiada, wlasny port)
  const rootPort = {};
  for (const s of switches) {
    if (s.n === root) continue;
    const cand = adj[s.n].map((e) => ({ e, key: [dist[e.nb] + e.cost, e.nb, e.nbPort, e.port] }));
    cand.sort((x, y) => (x.key[0] - y.key[0]) || bidCmp(sw[x.key[1]], sw[y.key[1]]) || (x.key[2] - y.key[2]) || (x.key[3] - y.key[3]));
    rootPort[s.n] = cand[0].e.port;
  }
  // Role portow na kazdym laczu
  const roles = {}; // `${sw}:${port}` -> 'root' | 'designated' | 'alternate' | 'backup'
  links.forEach(([a, pa, b, pb]) => {
    const ka = `${a}:${pa}`, kb = `${b}:${pb}`;
    // port, ktory ogloszona lepsza informacje (koszt nadawcy, BID nadawcy, port nadawcy) jest designated
    const cmp = (dist[a] - dist[b]) || bidCmp(sw[a], sw[b]) || (pa - pb);
    const [d, other, oSw, oPort] = cmp < 0 ? [ka, kb, b, pb] : [kb, ka, a, pa];
    roles[d] = 'designated';
    if (rootPort[oSw] === oPort) roles[other] = 'root';
    else roles[other] = oSw === (cmp < 0 ? a : b) ? 'backup' : 'alternate';
  });
  return { root, dist, rootPort, roles, costs };
}

const roleState = { root: 'forwarding', designated: 'forwarding', alternate: 'blocking/discarding', backup: 'blocking/discarding' };
module.exports = { stp, COST_SHORT, COST_LONG, roleState, bidCmp };
