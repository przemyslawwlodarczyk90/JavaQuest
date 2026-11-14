// Model do Debug Labu _ns_02/13: przynaleznosc do podsieci, analiza konfiguracji hosta, MTU.
const ipInt = (ip) => ip.split('.').reduce((a, o) => a * 256 + Number(o), 0);
const intIp = (n) => [24, 16, 8, 0].map((s) => Math.floor(n / 2 ** s) % 256).join('.');
const netOf = (ip, p) => Math.floor(ipInt(ip) / 2 ** (32 - p)) * 2 ** (32 - p);
const sameNet = (a, b, p) => netOf(a, p) === netOf(b, p);
const netStr = (ip, p) => `${intIp(netOf(ip, p))}/${p}`;
const bcast = (ip, p) => intIp(netOf(ip, p) + 2 ** (32 - p) - 1);
// Czy A wysle do B bezposrednio (ARP), przez brame, czy nie ma trasy
function decide(a, pa, gw, b) {
  if (sameNet(a, b, pa)) return 'bezpośrednio (ARP o adres celu)';
  if (!gw) return 'brak trasy — „Network is unreachable”';
  if (!sameNet(a, gw, pa)) return 'brama poza podsiecią — trasa domyślna nie zadziała';
  return 'przez bramę (ARP o adres bramy)';
}
const maxPayload = (mtu, v6 = false) => mtu - (v6 ? 48 : 28); // ICMP echo: IPv4 20 + ICMP 8; IPv6 40 + 8
module.exports = { ipInt, intIp, netOf, sameNet, netStr, bcast, decide, maxPayload };
