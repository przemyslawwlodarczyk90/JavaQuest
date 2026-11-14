// Model do lekcji _ns_03/10: typy ICMP, symulacja traceroute (TTL), analiza wynikow mtr.
const ICMP = [
  [0, 0, 'Echo Reply', 'odpowiedź na ping'],
  [3, 0, 'Destination Unreachable — net unreachable', 'router nie ma trasy do sieci'],
  [3, 1, 'Destination Unreachable — host unreachable', 'ostatni router nie dostał odpowiedzi ARP/ND od hosta'],
  [3, 3, 'Destination Unreachable — port unreachable', 'host nie słucha na porcie UDP (traceroute UDP: koniec trasy)'],
  [3, 4, 'Destination Unreachable — fragmentation needed (DF set)', 'pakiet za duży, a DF=1 — podstawa PMTUD'],
  [3, 13, 'Destination Unreachable — communication administratively prohibited', 'odrzucone przez ACL/firewall (REJECT)'],
  [5, 1, 'Redirect — for host', 'użyj innej bramy (lekcja 02/13)'],
  [8, 0, 'Echo Request', 'ping'],
  [11, 0, 'Time Exceeded — TTL exceeded in transit', 'TTL spadł do 0 — na tym działa traceroute'],
  [11, 1, 'Time Exceeded — fragment reassembly time exceeded', 'nie dotarły wszystkie fragmenty'],
];
const ICMP6 = [
  [1, 'Destination Unreachable'], [2, 'Packet Too Big (PMTUD w IPv6)'], [3, 'Time Exceeded'], [128, 'Echo Request'], [129, 'Echo Reply'],
  [133, 'Router Solicitation'], [134, 'Router Advertisement'], [135, 'Neighbor Solicitation'], [136, 'Neighbor Advertisement'],
];

// Wynik mtr: [{ host, loss, avg, best, worst, stdev }]
// Regula: strata „prawdziwa” = utrzymuje sie (≥ prog) od pewnego skoku az do celu.
// Strata tylko na skoku posrednim (kolejne skoki ~0%) = ograniczanie ICMP przez router (control plane), nie problem.
function analyze(hops, threshold = 2) {
  const last = hops[hops.length - 1];
  const notes = [];
  if (last.loss < threshold) {
    const lossy = hops.filter((h) => h.loss >= threshold && h !== last);
    notes.push(lossy.length ? `straty tylko na skokach pośrednich (${lossy.map((h) => h.host).join(', ')}) nie przenoszą się do celu → rate limiting ICMP, ścieżka OK` : 'brak istotnych strat');
  } else {
    let start = hops.length - 1;
    while (start > 0 && hops[start - 1].loss >= threshold) start--;
    notes.push(`strata zaczyna się na skoku ${start + 1} (${hops[start].host}) i trwa do celu → realny problem na łączu ${start ? `${hops[start - 1].host} → ` : ''}${hops[start].host} lub dalej`);
  }
  // skok opoznienia, ktory utrzymuje sie do konca = realne opoznienie (np. lacze transoceaniczne); pojedynczy wysoki avg = wolna odpowiedz control plane
  for (let i = 1; i < hops.length; i++) {
    const jump = hops[i].avg - hops[i - 1].avg;
    const persists = hops.slice(i).every((h) => h.avg >= hops[i].avg - 5);
    if (jump > 40 && persists) notes.push(`skok opóźnienia +${Math.round(jump)} ms na ${hops[i].host}, utrzymany do celu → realne opóźnienie (odległość/łącze/kolejka)`);
    if (jump > 40 && !persists) notes.push(`wysokie opóźnienie tylko na ${hops[i].host} → router wolno generuje ICMP (ignoruj)`);
  }
  const jitter = last.worst - last.best;
  if (jitter > 100 && hops[0].worst - hops[0].best > 100) notes.push(`duża zmienność już od pierwszego skoku (${hops[0].host}: ${hops[0].best}–${hops[0].worst} ms) → problem lokalny: Wi-Fi, przeciążone łącze domowe lub LAN`);
  else if (jitter > 100) notes.push(`duża zmienność na celu (best ${last.best} ms, worst ${last.worst} ms) → przeciążenie/kolejkowanie lub Wi-Fi`);
  return notes;
}

const H = (host, loss, avg, best, worst) => ({ host, loss, avg, best, worst });
const MTR = {
  A: [H('192.168.1.1', 0, 1.2, 0.8, 3), H('10.10.0.1', 0, 8, 6, 12), H('isp-core-1', 40, 9, 7, 30), H('ix-waw', 0, 10, 8, 14), H('cdn-edge', 0, 11, 9, 15)],
  B: [H('192.168.1.1', 0, 1.1, 0.8, 2), H('10.10.0.1', 0, 7, 6, 10), H('isp-core-1', 0, 9, 7, 13), H('isp-edge-fra', 12, 25, 22, 80), H('dc-gw', 13, 26, 23, 85), H('api.example', 12, 27, 24, 90)],
  C: [H('192.168.1.1', 0, 2, 1, 4), H('10.10.0.1', 0, 9, 7, 15), H('ix-fra', 0, 22, 20, 30), H('atlantic-1', 0, 112, 109, 120), H('us-east-gw', 0, 114, 110, 125), H('api.us', 0, 115, 111, 126)],
  D: [H('192.168.1.1', 0, 1, 0.8, 2), H('10.10.0.1', 0, 8, 6, 11), H('isp-core-1', 0, 210, 9, 900), H('ix-waw', 0, 11, 9, 15), H('cdn-edge', 0, 12, 10, 16)],
  E: [H('192.168.1.1', 0, 35, 2, 410), H('10.10.0.1', 0, 44, 8, 420), H('isp-core-1', 0, 46, 9, 430), H('ix-waw', 0, 47, 10, 435), H('cdn-edge', 0, 48, 11, 440)],
};

// Traceroute: dla TTL = 1..n kto odpowiada Time Exceeded
const traceroute = (path) => path.map((h, i) => ({ ttl: i + 1, reply: i < path.length - 1 ? `ICMP Time Exceeded od ${h}` : `koniec: ${h} (UDP → Port Unreachable, ICMP → Echo Reply, TCP → SYN-ACK/RST)` }));

module.exports = { ICMP, ICMP6, analyze, MTR, traceroute };
