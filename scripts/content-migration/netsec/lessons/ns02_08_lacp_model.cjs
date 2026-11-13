// Model negocjacji trybow EtherChannel i dydaktyczny hash rozkladu ruchu (lekcja _ns_02/08).
const MODES = ['active', 'passive', 'on', 'desirable', 'auto'];
const LACP = ['active', 'passive'], PAGP = ['desirable', 'auto'];

function negotiate(a, b) {
  if (a === 'on' && b === 'on') return { ok: true, short: 'tak (statyczny)', long: 'kanał statyczny bez protokołu' };
  if (a === 'on' || b === 'on') return { ok: false, risk: true, short: 'NIE — ryzyko', long: 'brak kanału po stronie negocjującej, strona „on” wysyła ruch po wszystkich łączach — ryzyko pętli lub utraty ruchu (Cisco zwykle wyłącza porty jako err-disabled przez EtherChannel guard)' };
  if (LACP.includes(a) && LACP.includes(b)) return a === 'passive' && b === 'passive' ? { ok: false, short: 'nie', long: 'obie strony czekają — kanał nie powstanie, łącza działają osobno (STP zablokuje nadmiarowe)' } : { ok: true, short: 'tak (LACP)', long: 'kanał LACP' };
  if (PAGP.includes(a) && PAGP.includes(b)) return a === 'auto' && b === 'auto' ? { ok: false, short: 'nie', long: 'obie strony czekają — kanał nie powstanie' } : { ok: true, short: 'tak (PAgP)', long: 'kanał PAgP' };
  return { ok: false, short: 'nie', long: 'różne protokoły (LACP vs PAgP) — kanał nie powstanie' };
}

// Dydaktyczny hash: XOR ostatnich oktetow (src-dst-ip) lub XOR portow, modulo liczba lacz.
const lastOct = (ip) => Number(ip.split('.')[3]);
const hashIp = (src, dst, n) => (lastOct(src) ^ lastOct(dst)) % n;
const hashL4 = (src, dst, sp, dp, n) => ((sp ^ dp) ^ (lastOct(src) ^ lastOct(dst))) % n;

module.exports = { MODES, negotiate, hashIp, hashL4, lastOct };
