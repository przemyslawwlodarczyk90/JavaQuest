// Model do lekcji _ns_03/11: fragmentacja IPv4, narzuty tuneli, MTU sciezki i MSS.
// Fragmentacja: dane kazdego fragmentu (poza ostatnim) musza byc wielokrotnoscia 8 B.
function fragment(totalLength, mtu, ihl = 20) {
  const data = totalLength - ihl;
  const maxData = Math.floor((mtu - ihl) / 8) * 8;
  const frags = [];
  for (let off = 0; off < data; off += maxData) {
    const len = Math.min(maxData, data - off);
    frags.push({ offsetBytes: off, offset: off / 8, data: len, total: len + ihl, mf: off + len < data ? 1 : 0 });
  }
  return frags;
}

// Narzuty (bajty dodawane do pakietu IP wewnatrz tunelu/enkapsulacji) — wartosci typowe
const OVERHEAD = [
  ['PPPoE (DSL/światłowód)', 8],
  ['GRE (bez klucza)', 24],
  ['IP-in-IP (IPIP)', 20],
  ['VXLAN (Docker overlay, Flannel, Calico VXLAN)', 50],
  ['WireGuard (zewnętrzny IPv4)', 60],
  ['WireGuard (zewnętrzny IPv6)', 80],
  ['IPsec ESP tunnel AES-GCM (IPv4, + NAT-T UDP)', 73],
  ['GENEVE (AWS GWLB, OVN)', 58],
];
const innerMtu = (outer, overhead) => outer - overhead;
const mss4 = (mtu) => mtu - 40; // IPv4 20 + TCP 20
const mss6 = (mtu) => mtu - 60; // IPv6 40 + TCP 20
const pathMtu = (links) => Math.min(...links);

module.exports = { fragment, OVERHEAD, innerMtu, mss4, mss6, pathMtu };
