// Model do lekcji _ns_03/07: tablica routingu, longest prefix match, metryki, konflikty z VPN.
const { parse, ipInt } = require('./ns03_04_cidr_model.cjs');

// trasa: { dst: 'a.b.c.d/n', via: brama|null, dev, metric, proto, note }
function lookup(table, ip) {
  const v = ipInt(ip);
  const hits = table.filter((r) => { const p = parse(r.dst); return v >= p.net && v < p.net + p.size; });
  if (!hits.length) return { route: null, reason: 'brak pasującej trasy → „Network is unreachable”', candidates: [] };
  const maxP = Math.max(...hits.map((r) => parse(r.dst).prefix));
  const longest = hits.filter((r) => parse(r.dst).prefix === maxP);
  const best = longest.sort((a, b) => (a.metric ?? 0) - (b.metric ?? 0))[0];
  const reason = longest.length > 1 ? `najdłuższy prefiks /${maxP} (${longest.length} trasy) → najniższa metryka ${best.metric ?? 0}` : `najdłuższy pasujący prefiks /${maxP}`;
  return { route: best, reason, candidates: hits };
}
const fmtRoute = (r) => r ? `${r.dst === '0.0.0.0/0' ? 'default' : r.dst}${r.via ? ` via ${r.via}` : ''} dev ${r.dev}${r.metric !== undefined ? ` metric ${r.metric}` : ''}` : '—';

// Laptop developera: Wi-Fi, Docker, VPN firmowy (split tunnel + pelny tunel jako wariant)
const LAPTOP = [
  { dst: '0.0.0.0/0', via: '192.168.1.1', dev: 'wlan0', metric: 600, proto: 'dhcp' },
  { dst: '192.168.1.0/24', via: null, dev: 'wlan0', metric: 600, proto: 'kernel' },
  { dst: '172.17.0.0/16', via: null, dev: 'docker0', metric: 0, proto: 'kernel' },
  { dst: '172.18.0.0/16', via: null, dev: 'br-3f1c', metric: 0, proto: 'kernel' },
  { dst: '10.0.0.0/8', via: '10.8.0.1', dev: 'tun0', metric: 50, proto: 'static', note: 'VPN split tunnel' },
  { dst: '172.16.0.0/12', via: '10.8.0.1', dev: 'tun0', metric: 50, proto: 'static', note: 'VPN split tunnel' },
  { dst: '10.8.0.0/24', via: null, dev: 'tun0', metric: 50, proto: 'kernel' },
];
// Wariant: VPN wpycha trase do sieci uzywanej przez Dockera (172.18.0.0/16 — dluzszy prefiks niz Docker? nie, rowny)
const VPN_CONFLICT = [
  ...LAPTOP.filter((r) => r.dev !== 'tun0'),
  { dst: '10.8.0.0/24', via: null, dev: 'tun0', metric: 50, proto: 'kernel' },
  { dst: '172.18.0.0/17', via: '10.8.0.1', dev: 'tun0', metric: 50, proto: 'static', note: 'VPN: sieć firmowa 172.18.0.0/17' },
  { dst: '0.0.0.0/1', via: '10.8.0.1', dev: 'tun0', metric: 50, proto: 'static', note: 'VPN pełny tunel (trik 0/1 + 128/1)' },
  { dst: '128.0.0.0/1', via: '10.8.0.1', dev: 'tun0', metric: 50, proto: 'static', note: 'VPN pełny tunel' },
];
// Router z kilkoma prefiksami (do zadan LPM)
const ROUTER = [
  { dst: '0.0.0.0/0', via: '203.0.113.1', dev: 'wan0', metric: 0 },
  { dst: '10.0.0.0/8', via: '10.255.0.2', dev: 'eth1', metric: 0 },
  { dst: '10.20.0.0/16', via: '10.255.0.6', dev: 'eth2', metric: 0 },
  { dst: '10.20.30.0/24', via: '10.255.0.10', dev: 'eth3', metric: 0 },
  { dst: '10.20.30.128/25', via: '10.255.0.14', dev: 'eth4', metric: 0 },
  { dst: '192.168.0.0/16', via: '10.255.0.2', dev: 'eth1', metric: 20 },
  { dst: '192.168.50.0/24', via: '10.255.0.18', dev: 'eth5', metric: 10 },
  { dst: '192.168.50.0/24', via: '10.255.0.22', dev: 'eth6', metric: 30 },
];

module.exports = { lookup, fmtRoute, LAPTOP, VPN_CONFLICT, ROUTER };
