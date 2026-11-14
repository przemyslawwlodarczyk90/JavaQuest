// Model do lekcji _ns_03/13: czas nawiazania polaczenia w dual-stack (klient sekwencyjny vs Happy Eyeballs v2),
// synteza adresow NAT64 (64:ff9b::/96), kolejnosc adresow.
const { toBig6, fromBig6 } = require('./ns03_05_drill_model.cjs');

// Adres: { fam: 6|4, state: 'ok'|'blackhole'|'refused'|'unreachable', rtt } ; timeout polaczenia (ms)
// Klient sekwencyjny (np. Java Socket z adresem z InetAddress.getByName = PIERWSZY adres; lub petla po getAllByName)
function sequential(addrs, connectTimeout) {
  let t = 0; const log = [];
  for (const a of addrs) {
    if (a.state === 'ok') { t += a.rtt; log.push(`IPv${a.fam} OK po ${a.rtt} ms`); return { total: t, ok: true, log }; }
    if (a.state === 'blackhole') { t += connectTimeout; log.push(`IPv${a.fam} brak odpowiedzi → timeout ${connectTimeout} ms`); }
    else { t += a.fail ?? 1; log.push(`IPv${a.fam} natychmiastowy błąd (${a.state}) po ${a.fail ?? 1} ms`); }
  }
  return { total: t, ok: false, log };
}
// Happy Eyeballs v2 (RFC 8305): proby startuja kolejno co attemptDelay (domyslnie 250 ms) albo natychmiast po bledzie poprzedniej
// RFC 8305 §4: lista jest przeplatana rodzinami (pierwsza preferowana, potem na zmiane)
function interleave(addrs) {
  const first = addrs[0]?.fam;
  const a = addrs.filter((x) => x.fam === first), o = addrs.filter((x) => x.fam !== first), out = [];
  while (a.length || o.length) { if (a.length) out.push(a.shift()); if (o.length) out.push(o.shift()); }
  return out;
}
function happyEyeballs(addrs0, attemptDelay = 250) {
  const addrs = interleave(addrs0);
  let start = 0, best = Infinity, winner = null;
  const log = [];
  for (const a of addrs) {
    if (start >= best) break;
    if (a.state === 'ok') {
      const done = start + a.rtt;
      log.push(`start IPv${a.fam} w ${start} ms → OK w ${done} ms`);
      if (done < best) { best = done; winner = a.fam; }
      start += attemptDelay;
    } else if (a.state === 'blackhole') {
      log.push(`start IPv${a.fam} w ${start} ms → brak odpowiedzi (kolejna próba po ${attemptDelay} ms)`);
      start += attemptDelay;
    } else {
      log.push(`start IPv${a.fam} w ${start} ms → błąd po ${a.fail ?? 1} ms (kolejna próba od razu)`);
      start += a.fail ?? 1;
    }
  }
  return { total: best === Infinity ? null : best, winner, log };
}

// NAT64: wbudowanie IPv4 w prefiks /96
function nat64(ipv4, prefix = '64:ff9b::') {
  const v = ipv4.split('.').reduce((a, o) => a * 256 + Number(o), 0);
  return fromBig6(toBig6(prefix) + BigInt(v));
}
function fromNat64(addr) {
  const n = Number(toBig6(addr) & 0xffffffffn);
  return [24, 16, 8, 0].map((s) => Math.floor(n / 2 ** s) % 256).join('.');
}

const SCEN = {
  healthy: [{ fam: 6, state: 'ok', rtt: 30 }, { fam: 4, state: 'ok', rtt: 28 }],
  v6blackhole: [{ fam: 6, state: 'blackhole' }, { fam: 4, state: 'ok', rtt: 28 }],
  v6unreachable: [{ fam: 6, state: 'unreachable', fail: 2 }, { fam: 4, state: 'ok', rtt: 28 }],
  v6slow: [{ fam: 6, state: 'ok', rtt: 400 }, { fam: 4, state: 'ok', rtt: 30 }],
  twoV6blackhole: [{ fam: 6, state: 'blackhole' }, { fam: 6, state: 'blackhole' }, { fam: 4, state: 'ok', rtt: 28 }],
};

module.exports = { sequential, happyEyeballs, interleave, nat64, fromNat64, SCEN };
