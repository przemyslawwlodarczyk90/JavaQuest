// Konwerter: plik .txt z blokami "@@ TYP | Naglowek" / "@@code" -> theory[] w pliku JSON lekcji.
// Uzycie: node scripts/content-migration/lx_build_theory.js <plik.txt> <plik.json>
// Zachowuje istniejace exercises/quiz w pliku docelowym.
const fs = require('fs');
const [src, dst] = process.argv.slice(2);
const TYPES = new Set(['INTRO','DEFINITION','ANALOGY','VISUAL_EXAMPLE','CODE_BASIC','CODE_PRACTICAL',
  'STEP_BY_STEP','USAGE','NOTE','PITFALL','CODE_WRONG','CODE_RIGHT','WHEN_TO_USE','SUMMARY','API_REFERENCE']);
const lines = fs.readFileSync(src, 'utf8').replace(/\r\n/g, '\n').split('\n');
const theory = [];
let cur = null, inCode = false;
const trim = a => { while (a.length && a[a.length-1].trim() === '') a.pop(); while (a.length && a[0].trim() === '') a.shift(); return a.join('\n'); };
const flush = () => {
  if (!cur) return;
  const b = { type: cur.type, heading: cur.heading, body: trim(cur.body) };
  if (cur.code) b.code = trim(cur.code);
  theory.push(b);
};
for (const line of lines) {
  const m = line.match(/^@@ ([A-Z_]+) \| (.+)$/);
  if (m) {
    flush();
    if (!TYPES.has(m[1])) throw new Error('Nieznany typ: ' + m[1]);
    cur = { type: m[1], heading: m[2].trim(), body: [], code: null };
    inCode = false;
  } else if (line === '@@code') {
    cur.code = []; inCode = true;
  } else if (cur) {
    (inCode ? cur.code : cur.body).push(line);
  }
}
flush();
for (const b of theory) {
  if (!b.body) throw new Error('Pusty body: ' + b.heading);
  if (b.code && /[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]/.test(b.code)) throw new Error('Polskie znaki w code: ' + b.heading);
  if (/[Ѐ-ӿ]/.test(b.body + b.heading + (b.code || ''))) throw new Error('Cyrylica: ' + b.heading);
}
let out = { theory: [], exercises: [], quiz: [] };
if (fs.existsSync(dst)) out = JSON.parse(fs.readFileSync(dst, 'utf8'));
out.theory = theory;
fs.writeFileSync(dst, JSON.stringify(out, null, 2) + '\n');
console.log(dst.split(/[\\/]/).slice(-2).join('/'), '-', theory.length, 'blokow:', theory.map(b => b.type).join(','));
