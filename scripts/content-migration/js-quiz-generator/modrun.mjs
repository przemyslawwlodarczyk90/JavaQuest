// Uruchamia kod wieloplikowy jako moduly ES w piaskownicy vm (vm.SourceTextModule).
// Wejscie (stdin): tekst z plikami oddzielonymi naglowkami `// nazwa.js` (ostatni plik albo main.js = wejscie).
// Wyjscie (stdout): JSON { logs: [[{t,v}...]...], err }.
// Wymaga: node --experimental-vm-modules
import fs from 'node:fs';
import vm from 'node:vm';
import path from 'node:path';
import { createRequire } from 'node:module';

const require = createRequire(import.meta.url);
const { fmt, POLY } = require('./lib.js');

const src = fs.readFileSync(0, 'utf8');
const files = {};
const names = [];
let cur = null;
for (const line of src.split('\n')) {
  const m = line.match(/^\/\/ ([\w.\/-]+\.m?js)\s*$/);
  if (m) { cur = m[1].replace(/^\.\//, ''); if (!(cur in files)) { names.push(cur); files[cur] = ''; } }
  if (cur === null) { cur = 'main.js'; names.push(cur); files[cur] = ''; }
  files[cur] += line + '\n';
}
const entry = 'main.js' in files ? 'main.js' : names[names.length - 1];

const logs = [];
const enc = v => {
  if (typeof v === 'string') return { t: 's', v };
  if (typeof v === 'number') return { t: 'n', v: String(v) };
  if (typeof v === 'boolean') return { t: 'b', v };
  if (v === undefined) return { t: 'u' };
  if (v === null) return { t: 'nl' };
  if (typeof v === 'bigint') return { t: 'bi', v: String(v) };
  return { t: 'o', v: fmt(v) };
};
const timers = [];
let now = 0;
const sandbox = {
  console: { log: (...a) => logs.push(a.map(enc)), error: (...a) => logs.push(a.map(enc)), warn: (...a) => logs.push(a.map(enc)), info: (...a) => logs.push(a.map(enc)) },
  setTimeout: (fn, ms, ...a) => { timers.push({ fn, a, due: now + (Number(ms) || 0), id: timers.length + 1 }); return timers.length; },
  clearTimeout: id => { const t = timers.find(x => x.id === id); if (t) t.cancelled = true; },
  URL, URLSearchParams,
};
const ctx = vm.createContext(sandbox);
vm.runInContext(POLY + '\nglobalThis.queueMicrotask = f => Promise.resolve().then(f);', ctx);

const mods = new Map();
function resolve(spec, from) {
  if (!spec.startsWith('.')) { const e = new Error('Cannot find package ' + spec); e.code = 'ERR_MODULE_NOT_FOUND'; throw e; }
  const p = path.posix.normalize(path.posix.join(path.posix.dirname(from), spec));
  if (!(p in files)) { const e = new Error('Cannot find module ' + p); e.code = 'ERR_MODULE_NOT_FOUND'; throw e; }
  return p;
}
function get(name) {
  if (mods.has(name)) return mods.get(name);
  const m = new vm.SourceTextModule(files[name], {
    context: ctx,
    identifier: name,
    importModuleDynamically: async (spec, ref) => { const t = get(resolve(spec, ref.identifier)); await ensure(t); return t; },
    initializeImportMeta: meta => { meta.url = 'file:///' + name; },
  });
  mods.set(name, m);
  return m;
}
async function ensure(m) {
  if (m.status === 'unlinked') await m.link((spec, ref) => get(resolve(spec, ref.identifier)));
  if (m.status === 'linked') await m.evaluate();
  else if (m.status === 'errored') throw m.error;
}
const flush = () => new Promise(r => setImmediate(r));

let err = null;
process.on('unhandledRejection', e => { if (!err) err = (e && e.name) || 'Error'; });

let done = false;
try {
  ensure(get(entry)).then(() => { done = true; }, e => { done = true; if (!err) err = (e && e.name) || 'Error'; });
} catch (e) {
  done = true;
  err = (e && e.name) || 'Error';
}
for (let g = 0; g < 1000 && !err; g++) {
  await flush();
  const pend = timers.filter(t => !t.done && !t.cancelled).sort((x, y) => x.due - y.due || x.id - y.id);
  if (!pend.length) break;
  const t = pend[0];
  t.done = true;
  now = t.due;
  try { t.fn(...t.a); } catch (e) { err = (e && e.name) || 'Error'; }
}
await flush();
process.stdout.write(JSON.stringify(err ? { logs, err } : { logs }));
process.exit(0);
