// Model do lekcji _ns_03/01 (naglowek IPv4 i adresacja): budowa i dekodowanie naglowka, suma kontrolna
// krok po kroku, klasy adresow (historycznie). Wszystkie liczby w lekcji pochodza z tych funkcji.
const { csum } = require('./ns01_05_packet.cjs');

const u16 = (n) => [(n >> 8) & 255, n & 255];
const hex2 = (b) => b.toString(16).padStart(2, '0');
const hex4 = (n) => n.toString(16).padStart(4, '0');
const ipBytes = (ip) => ip.split('.').map(Number);
const PROTO = { 1: 'ICMP', 2: 'IGMP', 6: 'TCP', 17: 'UDP', 41: 'IPv6 (enkapsulacja)', 47: 'GRE', 50: 'ESP (IPsec)', 51: 'AH (IPsec)', 89: 'OSPF', 112: 'VRRP', 132: 'SCTP' };

// Buduje 20-bajtowy naglowek IPv4 (bez opcji) z poprawna suma kontrolna.
function header({ dscp = 0, ecn = 0, total, id, df = true, mf = false, frag = 0, ttl = 64, proto = 6, src, dst }) {
  const flagsFrag = (df ? 0x4000 : 0) | (mf ? 0x2000 : 0) | (frag & 0x1fff);
  const b = [0x45, (dscp << 2) | ecn, ...u16(total), ...u16(id), ...u16(flagsFrag), ttl, proto, 0, 0, ...ipBytes(src), ...ipBytes(dst)];
  const sum = csum(b);
  b[10] = sum >> 8; b[11] = sum & 255;
  return b;
}

// Dekoduje naglowek z bajtow — dokladnie to, co robi Wireshark.
function decode(b) {
  const ihl = b[0] & 15;
  const ff = (b[6] << 8) | b[7];
  const words = [];
  for (let i = 0; i < ihl * 4; i += 2) words.push((b[i] << 8) | b[i + 1]);
  let s = words.reduce((a, w) => a + w, 0);
  const raw = s;
  while (s > 0xffff) s = (s & 0xffff) + (s >>> 16);
  return {
    version: b[0] >> 4, ihl, headerLen: ihl * 4, dscp: b[1] >> 2, ecn: b[1] & 3,
    total: (b[2] << 8) | b[3], id: (b[4] << 8) | b[5],
    df: Boolean(ff & 0x4000), mf: Boolean(ff & 0x2000), frag: ff & 0x1fff, fragBytes: (ff & 0x1fff) * 8,
    ttl: b[8], proto: b[9], protoName: PROTO[b[9]] || '?', checksum: (b[10] << 8) | b[11],
    src: b.slice(12, 16).join('.'), dst: b.slice(16, 20).join('.'),
    payload: ((b[2] << 8) | b[3]) - ihl * 4,
    verifySum: raw, verifyFolded: s, valid: s === 0xffff,
  };
}

// Kroki liczenia sumy kontrolnej (pole checksum = 0): slowa 16-bitowe, suma, przeniesienie, negacja.
function checksumSteps(b) {
  const z = [...b]; z[10] = 0; z[11] = 0;
  const words = [];
  for (let i = 0; i < 20; i += 2) words.push((z[i] << 8) | z[i + 1]);
  const sum = words.reduce((a, w) => a + w, 0);
  let folded = sum;
  while (folded > 0xffff) folded = (folded & 0xffff) + (folded >>> 16);
  return { words, sum, carry: sum >>> 16, folded, result: (~folded) & 0xffff };
}

const dump = (b) => b.map(hex2).join(' ');

// Klasy adresow (historia, RFC 791 / sprzed CIDR).
function ipClass(ip) {
  const o = ipBytes(ip)[0];
  if (o < 128) return 'A';
  if (o < 192) return 'B';
  if (o < 224) return 'C';
  if (o < 240) return 'D';
  return 'E';
}
const CLASSES = [
  ['A', '0', '0–127', '/8', '128 sieci po 16 777 214 hostów'],
  ['B', '10', '128–191', '/16', '16 384 sieci po 65 534 hosty'],
  ['C', '110', '192–223', '/24', '2 097 152 sieci po 254 hosty'],
  ['D', '1110', '224–239', '—', 'multicast'],
  ['E', '1111', '240–255', '—', 'zarezerwowane (eksperymentalne)'],
];

// Przykladowe naglowki uzywane w lekcji, zadaniach i quizie.
const SAMPLES = {
  tcp: header({ total: 60, id: 0x1c46, ttl: 64, proto: 6, src: '192.168.1.10', dst: '93.184.216.34' }),
  udp: header({ total: 76, id: 0x8f2a, ttl: 128, proto: 17, src: '10.0.0.5', dst: '10.0.0.53', dscp: 0 }),
  icmp: header({ total: 84, id: 0x0001, ttl: 1, proto: 1, src: '172.16.0.7', dst: '8.8.8.8' }),
  voip: header({ total: 200, id: 0x3344, ttl: 63, proto: 17, src: '10.20.1.15', dst: '10.20.9.40', dscp: 46 }),
  frag: header({ total: 1500, id: 0xbeef, df: false, mf: true, frag: 185, ttl: 59, proto: 17, src: '203.0.113.9', dst: '198.51.100.20' }),
  ecn: header({ total: 52, id: 0x0a0b, ttl: 47, proto: 6, src: '151.101.1.69', dst: '192.168.0.23', ecn: 2 }),
};

module.exports = { header, decode, checksumSteps, dump, ipClass, CLASSES, SAMPLES, PROTO, hex2, hex4 };
