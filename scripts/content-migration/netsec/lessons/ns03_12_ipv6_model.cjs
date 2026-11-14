// Model do lekcji _ns_03/12 (IPv6 od srodka): rozwijanie i skracanie (RFC 5952), EUI-64, solicited-node,
// multicast MAC, typy adresow. Korzysta z funkcji IPv6 z modelu treningu podsieci.
const { expand6, toBig6, fromBig6, net6, sub6, count6 } = require('./ns03_05_drill_model.cjs');
const { classify6 } = require('./ns03_06_special_model.cjs');

const full = (a) => expand6(a).join(':');
const compress = (a) => fromBig6(toBig6(a));

// EUI-64: MAC → identyfikator interfejsu (wstaw ff:fe w srodek, odwroc bit U/L — 7. bit pierwszego bajtu)
function eui64(mac) {
  const b = mac.split(/[:-]/).map((x) => parseInt(x, 16));
  b[0] ^= 0x02;
  const v = [b[0], b[1], b[2], 0xff, 0xfe, b[3], b[4], b[5]];
  const groups = [];
  for (let i = 0; i < 8; i += 2) groups.push(((v[i] << 8) | v[i + 1]).toString(16));
  return groups.join(':');
}
const slaac = (prefix64, mac) => compress(`${prefix64.replace(/::$/, '')}:${eui64(mac)}`);
const linkLocal = (mac) => compress(`fe80::${eui64(mac)}`);

// Solicited-node multicast: ff02::1:ffXX:XXXX z ostatnich 24 bitow adresu
function solicitedNode(addr) {
  const n = toBig6(addr) & 0xffffffn;
  const hi = Number(n >> 16n), lo = Number(n & 0xffffn);
  return compress(`ff02::1:ff${hi.toString(16).padStart(2, '0')}:${lo.toString(16)}`);
}
// Adres MAC multicast dla grupy IPv6: 33:33 + ostatnie 32 bity
function multicastMac(group) {
  const n = toBig6(group) & 0xffffffffn;
  const bytes = [24n, 16n, 8n, 0n].map((s) => Number((n >> s) & 0xffn).toString(16).padStart(2, '0'));
  return `33:33:${bytes.join(':')}`;
}

module.exports = { full, compress, eui64, slaac, linkLocal, solicitedNode, multicastMac, classify6, net6, sub6, count6 };
