// Obliczenia do lekcji _ns_02/11 (Wi-Fi z perspektywy aplikacji): kanaly, dBm, statystyki RTT.
const freq24 = (ch) => (ch === 14 ? 2484 : 2407 + 5 * ch);
const freq5 = (ch) => 5000 + 5 * ch;
const freq6 = (ch) => 5950 + 5 * ch;
// Kanaly 2,4 GHz o szerokosci 20 MHz nie nakladaja sie, gdy srodki sa oddalone o >= 20 MHz (praktycznie 25 MHz: 1/6/11)
const overlap24 = (a, b) => Math.abs(freq24(a) - freq24(b)) < 20;
const mw = (dbm) => 10 ** (dbm / 10);
const round = (x, d = 2) => Math.round(x * 10 ** d) / 10 ** d;
function stats(samples) {
  const s = [...samples].sort((a, b) => a - b);
  const pct = (p) => s[Math.min(s.length - 1, Math.ceil((p / 100) * s.length) - 1)];
  const mean = samples.reduce((a, b) => a + b, 0) / samples.length;
  let jit = 0; for (let i = 1; i < samples.length; i++) jit += Math.abs(samples[i] - samples[i - 1]);
  return { min: s[0], p50: pct(50), p90: pct(90), p99: pct(99), max: s[s.length - 1], mean: round(mean), jitter: round(jit / (samples.length - 1)) };
}
// Deterministyczne probki RTT: kabel stabilny, Wi-Fi z retransmisjami i ogonem
function samples(kind, n = 200, seed = 7) {
  let x = seed; const r = () => { x = (x * 1103515245 + 12345) % 2147483648; return x / 2147483648; };
  const out = [];
  for (let i = 0; i < n; i++) {
    if (kind === 'wired') out.push(round(0.6 + r() * 0.3, 2));
    else { let v = 2 + r() * 4; if (r() < 0.12) v += 10 + r() * 25; if (r() < 0.02) v += 80 + r() * 60; out.push(round(v, 1)); }
  }
  return out;
}
module.exports = { freq24, freq5, freq6, overlap24, mw, stats, samples, round };
