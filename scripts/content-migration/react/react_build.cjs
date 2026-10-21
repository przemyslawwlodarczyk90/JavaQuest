// Buduje teorie lekcji toru React ze zrodel tekstowych:
//   scripts/content-migration/react/src/<rozdzial>/<lekcja>.txt -> src/main/resources/content/<rozdzial>/<lekcja>.json
// Zastepuje tylko "theory" - cwiczenia i quiz w pliku JSON zostaja bez zmian.
//
// Format zrodla - bloki zaczynaja sie linia "#T TYP Naglowek", dalej tresc, opcjonalnie jeden blok kodu:
//   #T CODE_JSX Licznik w JSX
//   Opis przykladu.
//   ```
//   export function Counter() { ... }
//   ```
// Uzycie: node scripts/content-migration/react/react_build.cjs [plik.txt ...]   (bez argumentow - wszystkie)
const fs = require('fs');
const path = require('path');

const srcRoot = path.join(__dirname, 'src');
const dstRoot = path.join(__dirname, '..', '..', '..', 'src', 'main', 'resources', 'content');
const TYPES = new Set(['INTRO', 'DEFINITION', 'ANALOGY', 'VISUAL_EXAMPLE', 'CODE_BASIC', 'CODE_PRACTICAL',
  'STEP_BY_STEP', 'USAGE', 'NOTE', 'PITFALL', 'CODE_WRONG', 'CODE_RIGHT', 'WHEN_TO_USE', 'SUMMARY',
  'API_REFERENCE', 'CODE_JSX', 'CODE_TSX']);
// Slowo zakazane w tresci toru (zapisane w base64, by nie wystepowalo jawnie w repozytorium).
const BANNED = new RegExp(Buffer.from('cmVrcnV0', 'base64').toString(), 'i');

function parse(file) {
  const lines = fs.readFileSync(file, 'utf8').replace(/\r/g, '').split('\n');
  const blocks = [];
  let block = null, code = null;
  const close = () => {
    if (!block) return;
    if (code) throw new Error(`${file}: niezamkniety blok kodu w "${block.heading}"`);
    block.body = block.body.join('\n').trim();
    if (!block.body && !block.code) throw new Error(`${file}: pusty blok "${block.heading}"`);
    blocks.push(block);
  };
  for (const [i, line] of lines.entries()) {
    if (code) {
      if (line.startsWith('```')) { block.code = code.join('\n').replace(/\s+$/, ''); code = null; }
      else code.push(line);
      continue;
    }
    const m = line.match(/^#T (\S+) (.+)$/);
    if (m) {
      close();
      if (!TYPES.has(m[1])) throw new Error(`${file}:${i + 1}: nieznany typ bloku ${m[1]}`);
      block = { type: m[1], heading: m[2].trim(), body: [] };
    } else if (line.startsWith('```')) {
      if (!block) throw new Error(`${file}:${i + 1}: kod poza blokiem`);
      if (block.code) throw new Error(`${file}:${i + 1}: drugi blok kodu w "${block.heading}"`);
      code = [];
    } else if (block) {
      if (block.code && !line.trim()) continue;
      if (block.code) throw new Error(`${file}:${i + 1}: tekst po bloku kodu w "${block.heading}" - przenies go nad kod`);
      block.body.push(line);
    } else if (line.trim()) {
      throw new Error(`${file}:${i + 1}: tekst przed pierwszym blokiem`);
    }
  }
  close();
  blocks.forEach((b, i) => {
    if (b.type === 'CODE_JSX' && blocks[i + 1]?.type !== 'CODE_TSX') throw new Error(`${file}: po CODE_JSX "${b.heading}" musi byc CODE_TSX`);
    if (b.type === 'CODE_TSX' && blocks[i - 1]?.type !== 'CODE_JSX') throw new Error(`${file}: CODE_TSX "${b.heading}" bez poprzedzajacego CODE_JSX`);
    if (b.type.startsWith('CODE_') && !b.code) throw new Error(`${file}: blok ${b.type} "${b.heading}" bez kodu`);
  });
  return blocks.map(({ type, heading, body, code }) => (code ? { type, heading, body, code } : { type, heading, body }));
}

const files = process.argv.length > 2
  ? process.argv.slice(2).map((f) => path.resolve(f))
  : fs.readdirSync(srcRoot).sort().flatMap((c) => fs.readdirSync(path.join(srcRoot, c)).filter((f) => f.endsWith('.txt')).sort().map((f) => path.join(srcRoot, c, f)));

let total = 0;
for (const file of files) {
  const chapter = path.basename(path.dirname(file));
  const dst = path.join(dstRoot, chapter, path.basename(file).replace(/\.txt$/, '.json'));
  if (!fs.existsSync(dst)) throw new Error(`Brak lekcji w planie (react_plan.cjs): ${chapter}/${path.basename(file)}`);
  const theory = parse(file);
  if (BANNED.test(JSON.stringify(theory))) throw new Error(`${file}: niedozwolone slowo w tresci`);
  const json = JSON.parse(fs.readFileSync(dst, 'utf8'));
  json.theory = theory;
  fs.writeFileSync(dst, JSON.stringify({ theory: json.theory, exercises: json.exercises ?? [], quiz: json.quiz ?? [] }, null, 2) + '\n');
  total++;
  console.log(`${chapter}/${path.basename(dst)}: bloki teorii ${theory.length}`);
}
console.log(`Zbudowano lekcji: ${total}`);
