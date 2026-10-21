// Sprawdza kod z lekcji toru React kompilatorem TypeScript:
// - bloki CODE_TSX i inne bloki z kodem TSX/TS (pelne moduly: zawieraja import/export) - pelne sprawdzanie typow (strict),
// - bloki CODE_JSX (pelne moduly) - tylko skladnia.
// Pomijane: CODE_WRONG, fragmenty bez import/export, JSON, polecenia terminala i fragmenty z napisem @check-skip.
// Brakujace moduly (np. './StatCard' bez definicji w lekcji) sa ignorowane - przyklady to fragmenty wiekszego projektu.
// Wymaga katalogu z zainstalowanymi: typescript react react-dom @types/react @types/react-dom (+ biblioteki kursu).
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
fs.mkdirSync(path.join(work, 'src'), { recursive: true });
fs.writeFileSync(path.join(work, 'src', 'env.d.ts'), [
  "declare module '*.svg' { const src: string; export default src; }",
  "declare module '*.css';",
  "declare module '*.module.css' { const classes: Record<string, string>; export default classes; }",
  'interface ImportMetaEnv { readonly [key: string]: string | boolean | undefined; readonly DEV: boolean; readonly PROD: boolean; readonly MODE: string; readonly VITE_API_URL?: string }',
  'interface ImportMeta { readonly env: ImportMetaEnv }',
].join('\n') + '\n');
// Dopasowania jest-dom (toBeInTheDocument, toBeDisabled...) dla przykladow testow - jak plik setup w projekcie.
fs.writeFileSync(path.join(work, 'src', 'vitest-setup.ts'), "import '@testing-library/jest-dom/vitest';\n");

const TYPES_TS = [
  "export type TicketStatus = 'OPEN' | 'IN_PROGRESS' | 'CLOSED';",
  "export type TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';",
  'export type Ticket = { id: number; title: string; description: string; status: TicketStatus; priority: TicketPriority;',
  '  assignee: string | null; createdAt: string; tags: string[] };',
].join('\n') + '\n';

// Kazda lekcja ma wlasne drzewo src/L<n>/ z modelem DevDesk (types.ts, data/tickets.ts) i katalogiem components/.
// Fragment bez nazwy trafia do components/. Fragment zaczynajacy sie komentarzem z nazwa pliku (// sorting.ts,
// // api/tickets.ts) trafia pod te sciezke (sama nazwa - do components/), wiec inne fragmenty lekcji moga go
// importowac ('./sorting', '../api/tickets').
function seedLesson(dir) {
  fs.mkdirSync(path.join(dir, 'components'), { recursive: true });
  fs.mkdirSync(path.join(dir, 'data'));
  fs.writeFileSync(path.join(dir, 'types.ts'), TYPES_TS);
  fs.writeFileSync(path.join(dir, 'data', 'tickets.ts'), "import type { Ticket } from '../types';\nexport const tickets: Ticket[] = [];\n");
}

// Fragment spoza pary JSX/TSX traktujemy jako TS, jesli zawiera skladnie typow.
function isTsx(type, text) {
  if (type === 'CODE_TSX') return true;
  if (type === 'CODE_JSX') return false;
  return /}: {|}: [A-Z]|import type |\): \w|\w\??: (string|number|boolean|unknown|any|void|never|[A-Z]\w*)\b|<[A-Z]\w*(\[\])?>\(|\w<['"][\w-]+['"]>\(|\bdeclare |\bas const\b|^\s*(export )?type [A-Z]|\binterface [A-Z]/m.test(text);
}

const map = [];
let lessonNo = 0;
for (const file of files) {
  const lessonDir = path.join(work, 'src', `L${++lessonNo}`);
  seedLesson(lessonDir);
  const lines = fs.readFileSync(file, 'utf8').replace(/\r/g, '').split('\n');
  let type = null, heading = '', code = null, n = 0;
  for (const line of lines) {
    const m = line.match(/^#T (\S+) (.+)$/);
    if (code) {
      if (line.startsWith('```')) {
        const text = code.join('\n');
        const isModule = /^\s*(import|export) /m.test(text) && !/^\s*[{[]/.test(text);
        if (type !== 'CODE_WRONG' && isModule && !text.includes('@check-skip')) {
          const tsx = isTsx(type, text);
          const header = text.match(/^\/\/ ((?:[\w-]+\/)*[\w.-]+\.(?:tsx?|jsx?))(?=\s|$)/m);
          let rel = header ? (header[1].includes('/') ? header[1] : `components/${header[1]}`) : null;
          if (!rel || fs.existsSync(path.join(lessonDir, rel))) rel = `components/snippet${++n}.${tsx ? 'tsx' : 'jsx'}`;
          fs.mkdirSync(path.dirname(path.join(lessonDir, rel)), { recursive: true });
          fs.writeFileSync(path.join(lessonDir, rel), text + '\n');
          map.push({ name: `L${lessonNo}/${rel}`, rel, lessonDir, named: Boolean(header), file: path.relative(srcRoot, file), heading, tsx });
        }
        code = null;
      } else code.push(line);
    } else if (m) { type = m[1]; heading = m[2]; }
    else if (line.startsWith('```')) code = [];
  }
}

// Import wzgledny do pliku nazwanego w innej lekcji tego samego rozdzialu (np. './ticketsReducer' z lekcji o reducerze
// uzyty w lekcji o kontekscie) - kopiujemy ten plik (z jego sciezka) do lekcji, ktora go importuje. Dwa przebiegi,
// zeby skopiowany plik mogl pociagnac swoje wlasne importy.
const namedFiles = new Map(); // `${rozdzial}/${sciezka bez rozszerzenia}` -> fragment (pozniejsza lekcja nadpisuje)
for (const s of map) if (s.named) namedFiles.set(`${path.dirname(s.file)}/${s.rel.replace(/\.(tsx?|jsx?)$/, '')}`, s);
const exists = (base) => ['.ts', '.tsx', '.js', '.jsx'].some((ext) => fs.existsSync(base + ext));
for (let pass = 0; pass < 2; pass++) {
  for (const s of map) {
    for (const dirent of fs.readdirSync(s.lessonDir, { recursive: true })) {
      const relFile = String(dirent).split(path.sep).join('/');
      if (!/\.(tsx?|jsx?)$/.test(relFile)) continue;
      const text = fs.readFileSync(path.join(s.lessonDir, relFile), 'utf8');
      for (const [, spec] of text.matchAll(/from '(\.{1,2}\/[\w/.-]+)'/g)) {
        const target = path.join(s.lessonDir, path.dirname(relFile), spec);
        if (exists(target)) continue;
        const relTarget = path.relative(s.lessonDir, target).split(path.sep).join('/');
        const source = namedFiles.get(`${path.dirname(s.file)}/${relTarget}`);
        if (source && source.lessonDir !== s.lessonDir) {
          fs.mkdirSync(path.dirname(path.join(s.lessonDir, source.rel)), { recursive: true });
          fs.copyFileSync(path.join(source.lessonDir, source.rel), path.join(s.lessonDir, source.rel));
        }
      }
    }
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
  execFileSync(process.execPath, [path.join(harness, 'node_modules', 'typescript', 'bin', 'tsc'), '-p', work], { encoding: 'utf8', maxBuffer: 64 * 1024 * 1024 });
} catch (e) { out = e.stdout; }
const errors = out.split('\n').filter((l) => /error TS/.test(l) && !/TS2307|TS2875|TS6133|TS6196/.test(l));
for (const e of errors) {
  const normalized = e.split('\\').join('/');
  const snippet = map.find((s) => normalized.includes(`src/${s.name}`));
  console.log(snippet ? `${snippet.file} [${snippet.heading}]: ${e.replace(/^.*?\(/, '(')}` : e);
}
console.log(`Sprawdzono fragmentow: ${map.length} (TSX: ${map.filter((s) => s.tsx).length}), bledow: ${errors.length}`);
process.exitCode = errors.length ? 1 : 0;
