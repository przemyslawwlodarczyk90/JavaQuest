// Synchronizuje tor Sieci (CourseTrack.NETWORKING) z planem plan_part*.cjs:
// - blok rozdzialow NETWORKING w ChapterSeedData.java (miedzy znacznikami),
// - puste pliki lekcji w src/main/resources/content/_ns_*/ (istniejacej tresci nie nadpisuje,
//   usuwa tylko PUSTE pliki lekcji, ktorych nie ma juz w planie),
// - tabele lekcji w docs/netsec/PLAN_TORU_SIECI.md (plik roboczy, poza gitem).
// Kolejne czesci planu (plan_part2_*.cjs itd.) sa dolaczane automatycznie, jesli istnieja.
// Uzycie: node scripts/content-migration/netsec/netsec_scaffold.cjs
const fs = require('fs');
const path = require('path');

const here = __dirname;
const plan = fs.readdirSync(here)
  .filter((f) => /^plan_part\d+_.*\.cjs$/.test(f))
  .sort()
  .flatMap((f) => require(path.join(here, f)));

const root = path.join(here, '..', '..', '..');
const contentRoot = path.join(root, 'src', 'main', 'resources', 'content');
const seedFile = path.join(root, 'src', 'main', 'java', 'com', 'example', 'javaquest', 'platform', 'chapter', 'ChapterSeedData.java');
const planFile = path.join(root, 'docs', 'netsec', 'PLAN_TORU_SIECI.md');
const EMPTY = JSON.stringify({ theory: [], exercises: [], quiz: [] }, null, 2) + '\n';
const LEVELS = new Set(['L1', 'L2', 'L3', 'L4', 'L5']);
const PRIORITIES = { K: 'krytyczne', W: 'ważne', Z: 'zaawansowane', U: 'uzupełniające' };

// 0. Walidacja planu
const chapterSlugs = new Set();
for (const chapter of plan) {
  const [slug, title, goal, lessons] = chapter;
  if (chapter.length !== 4 || !/^_ns_\d{2}_[a-z0-9_]+$/.test(slug) || !title || !goal || !Array.isArray(lessons) || !lessons.length) {
    throw new Error(`Niepoprawny rozdzial w planie: ${slug}`);
  }
  if (chapterSlugs.has(slug)) throw new Error(`Powtorzony rozdzial: ${slug}`);
  chapterSlugs.add(slug);
  const lessonSlugs = new Set();
  for (const lesson of lessons) {
    const [ls, lt, level, prio, scope, practice] = lesson;
    if (lesson.length !== 6 || !/^\d{2}_[A-Za-z0-9]+$/.test(ls) || !lt || !LEVELS.has(level) || !PRIORITIES[prio] || !scope || !practice) {
      throw new Error(`Niepoprawna lekcja w planie: ${slug}/${ls}`);
    }
    if (lessonSlugs.has(ls)) throw new Error(`Powtorzona lekcja: ${slug}/${ls}`);
    lessonSlugs.add(ls);
  }
}

// 1. ChapterSeedData
const START = '            // ===== PIATY BLOK PLATFORMY: SIECI';
const END = '            // ===== KONIEC BLOKU SIECI =====';
let seed = fs.readFileSync(seedFile, 'utf8');
const nl = seed.includes('\r\n') ? '\r\n' : '\n';
const header = [
  '            // ===== PIATY BLOK PLATFORMY: SIECI (2026-11-10) =====',
  '            // Sieci komputerowe dla Java Backend Developera: modele OSI i TCP/IP, Ethernet, IP i routing,',
  '            // TCP/UDP/QUIC, DNS, HTTP, TLS i PKI, Linux networking, Docker/Kubernetes/AWS, proxy i sieci w Javie;',
  '            // czesc II: infrastruktura i operacje (CompTIA Network+) oraz security defensywne (Security+, CySA+).',
  '            // Rozdzialy koncza sie lekcja Boss (laboratorium integrujace). Struktura generowana z',
  '            // scripts/content-migration/netsec/plan_part*.cjs (netsec_scaffold.cjs) - nie edytuj recznie.',
];
const blocks = plan.map(([slug, title, , lessons], ci) => {
  const names = lessons.map(([s]) => `"${s}"`);
  const lines = [];
  let line = '                    ';
  for (const [i, n] of names.entries()) {
    const piece = n + (i < names.length - 1 ? ',' : '');
    if (line.trim() && (line + ' ' + piece).length > 110) { lines.push(line.replace(/\s+$/, '')); line = '                    '; }
    line += (line.trim() ? ' ' : '') + piece;
  }
  lines.push(line);
  const last = ci === plan.length - 1;
  return [`            new ChapterSeed("${slug}", "${title}", List.of(`, ...lines, `            ), CourseTrack.NETWORKING)${last ? '' : ','}`].join(nl);
});
const block = header.join(nl) + nl + blocks.join(nl + nl) + nl;
const a = seed.indexOf(START), b = seed.indexOf(END);
if (a < 0 || b < 0) throw new Error('Nie znaleziono bloku SIECI w ChapterSeedData');
seed = seed.slice(0, a) + block + seed.slice(b);
fs.writeFileSync(seedFile, seed);

// 2. Pliki lekcji
const wanted = new Map(plan.map(([slug, , , lessons]) => [slug, new Set(lessons.map(([s]) => s + '.json'))]));
for (const dir of fs.readdirSync(contentRoot).filter((d) => d.startsWith('_ns_'))) {
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
for (const [slug, , , lessons] of plan) {
  fs.mkdirSync(path.join(contentRoot, slug), { recursive: true });
  for (const [s] of lessons) {
    lessonsTotal++;
    const p = path.join(contentRoot, slug, s + '.json');
    if (!fs.existsSync(p)) { fs.writeFileSync(p, EMPTY); created++; }
  }
}

// 3. Tabele w planie roboczym (miedzy znacznikami)
if (fs.existsSync(planFile)) {
  const md = fs.readFileSync(planFile, 'utf8');
  const s = md.indexOf('<!-- LEKCJE:START -->'), e = md.indexOf('<!-- LEKCJE:END -->');
  if (s >= 0 && e > s) {
    const strip = (t) => t.replace(/^(Sieci|Security) - /, '');
    const body = plan.map(([slug, title, goal, lessons], ci) => [
      `### Rozdział ${String(ci).padStart(2, '0')} — ${strip(title)} (\`${slug}\`)`, '', `**Cel:** ${goal}`, '',
      '| # | Slug | Temat | Poziom | Priorytet | Zakres | Praktyka |', '|---|---|---|---|---|---|---|',
      ...lessons.map(([ls, lt, level, prio, scope, practice], i) => `| ${i + 1} | \`${ls}\` | ${lt} | ${level} | ${PRIORITIES[prio]} | ${scope} | ${practice} |`), '',
    ].join('\n')).join('\n');
    const summary = ['| Rozdział | Lekcje |', '|---|---:|',
      ...plan.map(([, title, , lessons], ci) => `| ${String(ci).padStart(2, '0')} ${strip(title)} | ${lessons.length} |`),
      `| **Razem (rozdziały: ${plan.length})** | **${lessonsTotal}** |`].join('\n');
    fs.writeFileSync(planFile, md.slice(0, s) + '<!-- LEKCJE:START -->\n\n' + summary + '\n\n' + body + '\n' + md.slice(e));
  }
}
console.log(`Rozdzialy: ${plan.length}, lekcje: ${lessonsTotal}, nowe pliki: ${created}`);
