// Model do lekcji _ns_03/08: dystans administracyjny, wybor trasy do RIB, trasa plywajaca i przelaczenie po awarii.
const AD = [
  ['connected', 0], ['static', 1], ['eBGP', 20], ['EIGRP (wewn.)', 90], ['OSPF', 110], ['IS-IS', 115], ['RIP', 120], ['EIGRP (zewn.)', 170], ['iBGP', 200], ['nieosiągalna (unusable)', 255],
];
const adOf = (src) => AD.find(([n]) => n === src)[1];

// kandydat: { prefix, source, ad (opcjonalnie nadpisany), metric, via, dev }
// Dla kazdego prefiksu do RIB trafia kandydat z najnizszym AD, przy remisie — najnizsza metryka (porownywana tylko w ramach zrodla).
function rib(candidates, downDevs = []) {
  const alive = candidates.filter((c) => !downDevs.includes(c.dev));
  const byPrefix = {};
  for (const c of alive) {
    const ad = c.ad ?? adOf(c.source);
    const cur = byPrefix[c.prefix];
    if (!cur || ad < cur.ad || (ad === cur.ad && (c.metric ?? 0) < (cur.metric ?? 0))) byPrefix[c.prefix] = { ...c, ad };
  }
  return byPrefix;
}
const fmt = (r) => r ? `${r.prefix} [${r.ad}/${r.metric ?? 0}] via ${r.via} (${r.dev}) — ${r.source}` : 'brak trasy';

// Scenariusz labu: r1 ma dwie drogi do 198.51.100.0/24 — przez r2 (glowna) i przez r3 (plywajaca AD 250)
const LAB = [
  { prefix: '198.51.100.0/24', source: 'static', metric: 0, via: '10.0.12.2', dev: 'eth-r2' },
  { prefix: '198.51.100.0/24', source: 'static', ad: 250, metric: 0, via: '10.0.13.2', dev: 'eth-r3' },
  { prefix: '10.0.12.0/30', source: 'connected', metric: 0, via: 'bezpośrednio', dev: 'eth-r2' },
  { prefix: '10.0.13.0/30', source: 'connected', metric: 0, via: 'bezpośrednio', dev: 'eth-r3' },
];
// Router w firmie: ta sama siec z OSPF i statycznie (pokazuje, ze AD wygrywa z metryka)
const MIXED = [
  { prefix: '10.50.0.0/16', source: 'OSPF', metric: 20, via: '10.0.0.2', dev: 'Gi0/1' },
  { prefix: '10.50.0.0/16', source: 'static', metric: 0, via: '10.0.0.6', dev: 'Gi0/2' },
  { prefix: '10.60.0.0/16', source: 'OSPF', metric: 30, via: '10.0.0.2', dev: 'Gi0/1' },
  { prefix: '10.60.0.0/16', source: 'static', ad: 130, metric: 0, via: '10.0.0.6', dev: 'Gi0/2' },
  { prefix: '0.0.0.0/0', source: 'eBGP', metric: 0, via: '203.0.113.1', dev: 'Gi0/0' },
  { prefix: '0.0.0.0/0', source: 'static', ad: 210, metric: 0, via: '198.51.100.1', dev: 'Gi0/3' },
  { prefix: '10.70.0.0/16', source: 'RIP', metric: 2, via: '10.0.0.10', dev: 'Gi0/4' },
  { prefix: '10.70.0.0/16', source: 'iBGP', metric: 0, via: '10.0.0.14', dev: 'Gi0/5' },
];

module.exports = { AD, adOf, rib, fmt, LAB, MIXED };
