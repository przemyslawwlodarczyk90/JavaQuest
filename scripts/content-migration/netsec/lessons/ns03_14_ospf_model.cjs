// Model do lekcji _ns_03/14 (OSPF): koszt interfejsu, SPF (Dijkstra) z ECMP, wybor DR/BDR, stany sasiedztwa.
// Koszt = referencyjna przepustowosc / przepustowosc interfejsu (min. 1, czesc calkowita). Domyslnie ref = 100 Mb/s.
const cost = (bwMbps, refMbps = 100) => Math.max(1, Math.floor(refMbps / bwMbps));

// graf: { A: { B: 10, C: 1 }, ... } — koszty wyjscia z wezla (OSPF liczy koszt interfejsu wyjsciowego)
function spf(graph, src) {
  const dist = {}, prev = {}, done = new Set();
  for (const n of Object.keys(graph)) { dist[n] = Infinity; prev[n] = []; }
  dist[src] = 0;
  while (done.size < Object.keys(graph).length) {
    let u = null;
    for (const n of Object.keys(graph)) if (!done.has(n) && (u === null || dist[n] < dist[u])) u = n;
    if (u === null || dist[u] === Infinity) break;
    done.add(u);
    for (const [v, c] of Object.entries(graph[u])) {
      const nd = dist[u] + c;
      if (nd < dist[v]) { dist[v] = nd; prev[v] = [u]; } else if (nd === dist[v] && !prev[v].includes(u)) prev[v].push(u);
    }
  }
  return { dist, prev };
}
// wszystkie najkrotsze sciezki (ECMP)
function paths(res, dst, src) {
  if (dst === src) return [[src]];
  return res.prev[dst].flatMap((p) => paths(res, p, src).map((x) => [...x, dst]));
}

// DR/BDR: najwyzszy priorytet (0 = nie bierze udzialu), przy remisie najwyzszy Router ID
const ridNum = (rid) => rid.split('.').reduce((a, o) => a * 256 + Number(o), 0);
function electDr(routers) {
  const cand = routers.filter((r) => r.prio > 0).sort((a, b) => b.prio - a.prio || ridNum(b.rid) - ridNum(a.rid));
  return { dr: cand[0]?.name, bdr: cand[1]?.name };
}

const STATES = ['Down', 'Init', '2-Way', 'ExStart', 'Exchange', 'Loading', 'Full'];

// Topologia labu: R1–R2 (1 Gb/s), R2–R4 (1 Gb/s), R1–R3 (100 Mb/s), R3–R4 (100 Mb/s), R2–R3 (10 Mb/s); LAN za R4
const LINKS = [['R1', 'R2', 1000], ['R2', 'R4', 1000], ['R1', 'R3', 100], ['R3', 'R4', 100], ['R2', 'R3', 10]];
function graphFrom(links, ref = 100, overrides = {}) {
  const g = {};
  for (const [a, b, bw] of links) {
    g[a] = g[a] || {}; g[b] = g[b] || {};
    g[a][b] = overrides[`${a}-${b}`] ?? cost(bw, ref);
    g[b][a] = overrides[`${b}-${a}`] ?? cost(bw, ref);
  }
  return g;
}

module.exports = { cost, spf, paths, electDr, STATES, LINKS, graphFrom, ridNum };
