// Synchronizuje tor React z planem react_plan.cjs:
// - blok rozdzialow REACT w ChapterSeedData.java,
// - puste pliki lekcji w src/main/resources/content/_react_*/ (istniejacej tresci nie nadpisuje,
//   usuwa tylko PUSTE pliki lekcji, ktorych nie ma juz w planie),
// - tabele lekcji w docs/react/PLAN_KURSU_REACT.md (plik roboczy, poza gitem).
// Uzycie: node scripts/content-migration/react/react_scaffold.cjs
const fs = require('fs');
const path = require('path');
const plan = require('./react_plan.cjs');

const root = path.join(__dirname, '..', '..', '..');
const contentRoot = path.join(root, 'src', 'main', 'resources', 'content');
const seedFile = path.join(root, 'src', 'main', 'java', 'com', 'example', 'javaquest', 'platform', 'chapter', 'ChapterSeedData.java');
const planFile = path.join(root, 'docs', 'react', 'PLAN_KURSU_REACT.md');
const EMPTY = JSON.stringify({ theory: [], exercises: [], quiz: [] }, null, 2) + '\n';

// 1. ChapterSeedData
const START = '            // ===== TOR REACT';
const END = '            // ===== TRZECI BLOK PLATFORMY';
let seed = fs.readFileSync(seedFile, 'utf8');
const nl = seed.includes('\r\n') ? '\r\n' : '\n';
const header = [
  '            // ===== TOR REACT (2026-10-21) =====',
  '            // Kurs po torze JavaScript, prowadzony dwutorowo: kazda lekcja praktyczna w wersji .jsx i .tsx',
  '            // z omowieniem roznic (rozdzial 01 - wprowadzenie do TypeScript). Jeden rozwijany projekt',
  '            // (DevDesk - panel ticketow), rozdzialy koncza sie lekcja Boss. Struktura generowana z',
  '            // scripts/content-migration/react/react_plan.cjs (react_scaffold.cjs).',
];
const blocks = plan.map(([slug, title, lessons]) => {
  const names = lessons.map(([s]) => `"${s}"`);
  const lines = [];
  let line = '                    ';
  for (const [i, n] of names.entries()) {
    const piece = n + (i < names.length - 1 ? ',' : '');
    if (line.trim() && (line + ' ' + piece).length > 110) { lines.push(line.replace(/\s+$/, '')); line = '                    '; }
    line += (line.trim() ? ' ' : '') + piece;
  }
  lines.push(line);
  return [`            new ChapterSeed("${slug}", "${title}", List.of(`, ...lines, '            ), CourseTrack.REACT),'].join(nl);
});
const block = header.join(nl) + nl + blocks.join(nl + nl) + nl + nl;
const a = seed.indexOf(START), b = seed.indexOf(END);
if (a < 0 || b < 0) throw new Error('Nie znaleziono bloku REACT w ChapterSeedData');
seed = seed.slice(0, a) + block + seed.slice(b);
fs.writeFileSync(seedFile, seed);

// 2. Pliki lekcji
const wanted = new Map(plan.map(([slug, , lessons]) => [slug, new Set(lessons.map(([s]) => s + '.json'))]));
for (const dir of fs.readdirSync(contentRoot).filter((d) => d.startsWith('_react_'))) {
  const keep = wanted.get(dir) ?? new Set();
  for (const file of fs.readdirSync(path.join(contentRoot, dir))) {
    const p = path.join(contentRoot, dir, file);
    if (!keep.has(file)) {
      if (fs.readFileSync(p, 'utf8').replace(/\s/g, '') !== EMPTY.replace(/\s/g, '')) throw new Error(`Lekcja z trescia poza planem: ${dir}/${file}`);
      fs.unlinkSync(p);
    }
  }
  if (!fs.readdirSync(path.join(contentRoot, dir)).length) fs.rmdirSync(path.join(contentRoot, dir));
}
let created = 0, lessonsTotal = 0;
for (const [slug, , lessons] of plan) {
  fs.mkdirSync(path.join(contentRoot, slug), { recursive: true });
  for (const [s] of lessons) {
    lessonsTotal++;
    const p = path.join(contentRoot, slug, s + '.json');
    if (!fs.existsSync(p)) { fs.writeFileSync(p, EMPTY); created++; }
  }
}

// 3. Tabele w planie (miedzy znacznikami)
if (fs.existsSync(planFile)) {
  const md = fs.readFileSync(planFile, 'utf8');
  const s = md.indexOf('<!-- LEKCJE:START -->'), e = md.indexOf('<!-- LEKCJE:END -->');
  if (s >= 0 && e > s) {
    const body = plan.map(([slug, title, lessons], ci) => [
      `### Rozdział ${String(ci).padStart(2, '0')} — ${title.replace(/^React - /, '')} (\`${slug}\`)`, '',
      '| # | Slug | Temat | Podrozdziały | JSX ↔ TSX |', '|---|---|---|---|---|',
      ...lessons.map(([ls, lt, sub, diff], i) => `| ${i + 1} | \`${ls}\` | ${lt} | ${sub} | ${diff} |`), '',
    ].join('\n')).join('\n');
    const summary = ['| Rozdział | Lekcje |', '|---|---:|',
      ...plan.map(([, title, lessons], ci) => `| ${String(ci).padStart(2, '0')} ${title.replace(/^React - /, '')} | ${lessons.length} |`),
      `| **Razem (rozdziały: ${plan.length})** | **${lessonsTotal}** |`].join('\n');
    fs.writeFileSync(planFile, md.slice(0, s) + '<!-- LEKCJE:START -->\n\n' + summary + '\n\n' + body + '\n' + md.slice(e));
  }
}
console.log(`Rozdzialy: ${plan.length}, lekcje: ${lessonsTotal}, nowe pliki: ${created}`);
