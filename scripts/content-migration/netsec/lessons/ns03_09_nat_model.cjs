// Model do lekcji _ns_03/09: SNAT/PAT z tablica translacji, DNAT (port forwarding), wyczerpanie portow.
// PAT: kazdy przeplyw (proto, src, sport, dst, dport) dostaje (publicIp, port). Unikalnosc wymagana per (proto, publicIp, port, dst, dport).
function pat(flows, publicIps, firstPort = 1024, lastPort = 65535) {
  const used = new Set();
  const table = [];
  for (const f of flows) {
    let assigned = null;
    // sprobuj zachowac port zrodlowy (wiele implementacji tak robi), potem kolejne wolne
    const tryPorts = [f.sport, ...Array.from({ length: lastPort - firstPort + 1 }, (_, i) => firstPort + i)];
    outer: for (const ip of publicIps) for (const p of tryPorts) {
      if (p < firstPort || p > lastPort) continue;
      const key = `${f.proto}|${ip}|${p}|${f.dst}|${f.dport}`;
      if (!used.has(key)) { used.add(key); assigned = { ip, port: p }; break outer; }
    }
    table.push({ ...f, nat: assigned });
  }
  return table;
}
const fmtFlow = (t) => `${t.proto} ${t.src}:${t.sport} → ${t.dst}:${t.dport}  ⇒  ${t.nat ? `${t.nat.ip}:${t.nat.port}` : 'BRAK PORTU (odrzucone)'} → ${t.dst}:${t.dport}`;

// Maksymalna liczba jednoczesnych polaczen do JEDNEGO celu (ip:port) przy N publicznych adresach i zakresie portow.
const maxToOneDest = (publicIps, portsPerIp) => publicIps * portsPerIp;
// Ile polaczen „wisi” w TIME_WAIT/conntrack przy stalym tempie nowych polaczen: rate × czas trzymania.
const steadyState = (ratePerSec, holdSec) => ratePerSec * holdSec;
// Minimalna liczba publicznych IP, aby utrzymac tempo do jednego celu.
const ipsNeeded = (ratePerSec, holdSec, portsPerIp) => Math.ceil(steadyState(ratePerSec, holdSec) / portsPerIp);

const AWS_NAT_PER_DEST = 55000; // AWS NAT Gateway: do 55 000 jednoczesnych polaczen do kazdego unikalnego celu na adres IP

const FLOWS = [
  { proto: 'TCP', src: '192.168.1.20', sport: 51000, dst: '93.184.216.34', dport: 443 },
  { proto: 'TCP', src: '192.168.1.21', sport: 51000, dst: '93.184.216.34', dport: 443 },
  { proto: 'TCP', src: '192.168.1.22', sport: 40000, dst: '93.184.216.34', dport: 443 },
  { proto: 'UDP', src: '192.168.1.20', sport: 53000, dst: '1.1.1.1', dport: 53 },
  { proto: 'TCP', src: '192.168.1.20', sport: 51000, dst: '140.82.121.4', dport: 443 },
];

module.exports = { pat, fmtFlow, maxToOneDest, steadyState, ipsNeeded, AWS_NAT_PER_DEST, FLOWS };
