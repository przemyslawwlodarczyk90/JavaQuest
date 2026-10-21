// Sprawdza kod z lekcji toru React kompilatorem TypeScript:
// - bloki CODE_TSX i inne bloki z kodem TSX/TS (pelne moduly: zawieraja import/export) - pelne sprawdzanie typow (strict),
// - bloki CODE_JSX (pelne moduly) - tylko skladnia.
// Pomijane: CODE_WRONG, fragmenty bez import/export, JSON, polecenia terminala. Brakujace moduly wzgledne
// (np. './StatCard') sa ignorowane - przyklady to fragmenty wiekszego projektu.
// Wymaga katalogu z zainstalowanymi: typescript react react-dom @types/react @types/react-dom
// Uzycie: node scripts/content-migration/react/react_check.cjs <katalog-z-node_modules> [plik.txt ...]
const fs = require('fs');
const path = require('path');
const { execFileSync } = require('child_process');

const harness = path.resolve(process.argv[2] ?? '');
if (!fs.existsSync(path.join(harness, 'node_modules', 'typescript'))) throw new Error('Podaj katalog z zainstalowanym typescript i @types/react');
const srcRoot = path.join(__dirname, 'src');
const files = process.argv.length > 3
  ? process.argv.slice(3).map((f) => path.resolve(f))
  : fs.readdirSync(srcRoot).sort().flatMap((c) => fs.readdirSync(path.join(srcRoot, c)).filter((f) => f.endsWith('.txt')).sort().map((f) => path.join(srcRoot, c, f)));

const work = path.join(harness, 'snippets');
fs.rmSync(work, { recursive: true, force: true });
fs.mkdirSync(path.join(work, 'src', 'components'), { recursive: true });
// Model DevDesk dostepny pod '../types' dla przykladow z src/components.
fs.writeFileSync(path.join(work, 'src', 'types.ts'), `export type TicketStatus = 'OPEN' | 'IN_PROGRESS' | 'CLOSED';
export type TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type Ticket = { id: number; title: string; description: string; status: TicketStatus; priority: TicketPriority;
  assignee: string | null; createdAt: string; tags: string[] };
`);
fs.mkdirSync(path.join(work, 'src', 'data'));
fs.writeFileSync(path.join(work, 'src', 'data', 'tickets.ts'), "import type { Ticket } from '../types';\nexport const tickets: Ticket[] = [];\n");
fs.writeFileSync(path.join(work, 'src', 'env.d.ts'), "declare module '*.svg' { const src: string; export default src; }\ndeclare module '*.css';\ndeclare module '*.module.css' { const classes: Record<string, string>; export default classes; }\n");

const map = [];
let lessonNo = 0;
for (const file of files) {
  // Kazda lekcja we wlasnym katalogu src/L<n>/ (ten sam poziom co components - dziala import '../types').
  // Fragment zaczynajacy sie komentarzem z nazwa pliku (np. // sorting.ts) jest zapisywany pod ta nazwa,
  // wiec inne fragmenty tej lekcji moga go importowac ('./sorting').
  const lessonDir = path.join(work, 'src', `L${++lessonNo}`);
  fs.mkdirSync(lessonDir);
  const lines = fs.readFileSync(file, 'utf8').replace(/\r/g, '').split('\n');
  let type = null, heading = '', code = null, n = 0;
  for (const line of lines) {
    const m = line.match(/^#T (\S+) (.+)$/);
    if (code) {
      if (line.startsWith('```')) {
        const text = code.join('\n');
        const isModule = /^\s*(import|export) /m.test(text) && !/^\s*[{[]/.test(text);
        if (type !== 'CODE_WRONG' && isModule && !text.includes('@check-skip')) {
          const tsx = type === "CODE_TSX" || (type !== "CODE_JSX" && /}: {|}: [A-Z]|import type |\): \w|\w\??: (string|number|boolean|unknown|any|void|never|[A-Z]\w*)\b|<[A-Z]\w*(\[\])?>\(|\bdeclare |\bas const\b|^\s*(export )?type [A-Z]|\binterface [A-Z]/m.test(text));
          const named = text.match(/^\/\/ ([\w.-]+\.(?:tsx?|jsx?))(?=\s|$)/m);
          const fileName = named && !fs.existsSync(path.join(lessonDir, named[1])) ? named[1] : `snippet${++n}.${tsx ? 'tsx' : 'jsx'}`;
          const name = `L${lessonNo}/${fileName}`;
          fs.writeFileSync(path.join(lessonDir, fileName), text + '\n');
          map.push({ name, file: path.relative(srcRoot, file), heading, tsx });
        }
        code = null;
      } else code.push(line);
    } else if (m) { type = m[1]; heading = m[2]; }
    else if (line.startsWith('```')) code = [];
  }
}
fs.writeFileSync(path.join(work, 'tsconfig.json'), JSON.stringify({
  compilerOptions: { target: 'ES2023', lib: ['ES2023', 'DOM', 'DOM.Iterable'], module: 'ESNext', moduleResolution: 'bundler',
    jsx: 'react-jsx', strict: true, noEmit: true, skipLibCheck: true, allowJs: true, checkJs: false,
    allowImportingTsExtensions: true, isolatedModules: true, moduleDetection: 'force', typeRoots: [path.join(harness, 'node_modules', '@types')] },
  include: ['src'],
}, null, 2));
let out = '';
try {
  execFileSync(process.execPath, [path.join(harness, 'node_modules', 'typescript', 'bin', 'tsc'), '-p', work], { encoding: 'utf8' });
} catch (e) { out = e.stdout; }
const errors = out.split('\n').filter((l) => /error TS/.test(l) && !/TS2307|TS2875|TS6133|TS6196/.test(l));
for (const e of errors) {
  const snippet = map.find((s) => e.includes(s.name));
  console.log(snippet ? `${snippet.file} [${snippet.heading}]: ${e.replace(/^.*?\(/, '(')}` : e);
}
console.log(`Sprawdzono fragmentow: ${map.length} (TSX: ${map.filter((s) => s.tsx).length}), bledow: ${errors.length}`);
process.exitCode = errors.length ? 1 : 0;
