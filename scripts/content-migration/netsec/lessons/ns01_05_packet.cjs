// Buduje przykladowa ramke Ethernet/IPv4/TCP z zadaniem HTTP i poprawnymi sumami kontrolnymi.
// Wspolne dla lekcji _ns_01/05 (hexdump, pola) i ilustracji.
const PAYLOAD = 'GET /users/42 HTTP/1.1\r\nHost: api.javaquest.local\r\nAccept: application/json\r\n\r\n';
const SRC_MAC = [0x02, 0x42, 0xac, 0x11, 0x00, 0x03];
const DST_MAC = [0x02, 0x42, 0xac, 0x11, 0x00, 0x02];
const SRC_IP = [172, 17, 0, 3];
const DST_IP = [172, 17, 0, 2];
const SRC_PORT = 51334, DST_PORT = 8080, SEQ = 0x5a3c91e2, ACK = 0x11c0de01, IP_ID = 0x4c1d, TTL = 64, WINDOW = 502;

const u16 = (n) => [(n >> 8) & 255, n & 255];
const u32 = (n) => [(n >>> 24) & 255, (n >>> 16) & 255, (n >>> 8) & 255, n & 255];
function csum(bytes) {
  let s = 0;
  for (let i = 0; i < bytes.length; i += 2) s += (bytes[i] << 8) + (bytes[i + 1] ?? 0);
  while (s > 0xffff) s = (s & 0xffff) + (s >>> 16);
  return (~s) & 0xffff;
}

function build() {
  const data = [...Buffer.from(PAYLOAD, 'ascii')];
  // TCP z opcja timestamps? Dla czytelnosci: naglowek 20 B bez opcji, flagi PSH+ACK.
  const tcpNoCsum = [...u16(SRC_PORT), ...u16(DST_PORT), ...u32(SEQ), ...u32(ACK), 0x50, 0x18, ...u16(WINDOW), 0, 0, 0, 0];
  const tcpLen = tcpNoCsum.length + data.length;
  const pseudo = [...SRC_IP, ...DST_IP, 0, 6, ...u16(tcpLen)];
  const tcpSum = csum([...pseudo, ...tcpNoCsum, ...data]);
  const tcp = [...tcpNoCsum]; tcp[16] = tcpSum >> 8; tcp[17] = tcpSum & 255;
  const totalLen = 20 + tcpLen;
  const ipNoCsum = [0x45, 0x00, ...u16(totalLen), ...u16(IP_ID), 0x40, 0x00, TTL, 6, 0, 0, ...SRC_IP, ...DST_IP];
  const ipSum = csum(ipNoCsum);
  const ip = [...ipNoCsum]; ip[10] = ipSum >> 8; ip[11] = ipSum & 255;
  const eth = [...DST_MAC, ...SRC_MAC, 0x08, 0x00];
  const frame = [...eth, ...ip, ...tcp, ...data];
  return { frame, eth, ip, tcp, data, ipSum, tcpSum, totalLen, tcpLen };
}

function hexdump(bytes) {
  const lines = [];
  for (let i = 0; i < bytes.length; i += 16) {
    const chunk = bytes.slice(i, i + 16);
    const hex = chunk.map((b) => b.toString(16).padStart(2, '0')).join(' ');
    const asc = chunk.map((b) => (b >= 32 && b < 127 ? String.fromCharCode(b) : '.')).join('');
    lines.push(`${i.toString(16).padStart(4, '0')}  ${hex.padEnd(47)}  ${asc}`);
  }
  return lines.join('\n');
}

module.exports = { build, hexdump, PAYLOAD, SRC_PORT, DST_PORT, TTL, WINDOW, SEQ, ACK, IP_ID, csum };
