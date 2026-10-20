// Weryfikacja pytan toru OCP kompilatorem JDK (--release 21) - klucz odpowiedzi nie jest zgadywany.
// Uzycie: node scripts/content-migration/ocp/ocp_verify.cjs <plik.txt>...
//   JAVA_HOME musi wskazywac JDK 21+ (np. ~/.jdks/openjdk-25.0.2).
//
// Tryby pola "X:" pytania (kod z bloku ``` lub z opcji):
//   out             program wypisuje dokladnie tekst poprawnej opcji (biale znaki normalizowane do spacji),
//                   a zadna bledna opcja nie jest rownowazna temu wynikowi
//   exc Nazwa       main konczy sie wyjatkiem o tej prostej nazwie klasy
//   ce              kod sie nie kompiluje
//   ce n1 n3        bledy kompilacji wystepuja dokladnie w liniach oznaczonych komentarzem // n1, // n3
//   ok              kompiluje sie i dziala bez wyjatku;  cok - tylko kompiluje sie
//   opt-ok          kazda opcja wstawiona w miejsce ___ (albo sama jako instrukcja): poprawna sie
//                   kompiluje, bledne nie;  opt-ce - odwrotnie
//   Flaga "nothrows" - fragment instrukcji opakowany w main BEZ "throws Exception".
//   Flaga "slashes" (dla out) - ukosniki wsteczne w wyniku zamieniane na / przed porownaniem.
// Fragment bez deklaracji typu na poczatku linii jest traktowany jako cialo main(); importy
// najczesciej uzywanych pakietow sa dodawane automatycznie (jedna linia na poczatku pliku).
const fs = require('fs');
const os = require('os');
const path = require('path');
const { spawn } = require('child_process');
const { parse } = require('./ocp_build.cjs');

const IMPORTS = ['java.util', 'java.util.function', 'java.util.stream', 'java.util.concurrent', 'java.util.concurrent.atomic',
  'java.util.concurrent.locks', 'java.io', 'java.nio.file', 'java.nio.file.attribute', 'java.time', 'java.time.format', 'java.time.temporal', 'java.math', 'java.text']
  .map((p) => `import ${p}.*;`).join(' ');
const UNIT = /^(import |package |module |open module |@\w|((public|abstract|final|sealed|non-sealed|strictfp|static)\s+)*(class|interface|record|enum)\s)/m;

// Pelna jednostka kompilacji: ma main albo zaczyna sie deklaracja typu/importem i konczy klamra
// w kolumnie 0. Inaczej fragment instrukcji (moze zawierac klasy/rekordy lokalne).
function isUnit(code) {
  const lines = code.split('\n').filter((l) => l.trim());
  if (/static\s+void\s+main\s*\(/.test(code)) return true;
  return UNIT.test(lines[0]) && /^\S.*}\s*$|^}\s*$/.test(lines[lines.length - 1]);
}

function makeSources(code, nothrows) {
  if (isUnit(code)) {
    const pub = code.match(/^(?:(?:public|abstract|final|sealed|non-sealed|strictfp)\s+)*?public\s+(?:(?:abstract|final|sealed|non-sealed|strictfp)\s+)*(?:class|interface|record|enum)\s+(\w+)/m)
      || code.match(/^public\s+(?:\w+\s+)*?(?:class|interface|record|enum)\s+(\w+)/m);
    const mainIdx = code.search(/static\s+void\s+main\s*\(/);
    let hint = null;
    if (mainIdx >= 0) {
      const decls = [...code.slice(0, mainIdx).matchAll(/\b(?:class|record|enum|interface)\s+(\w+)/g)];
      if (decls.length) hint = decls[decls.length - 1][1];
    }
    const hasImports = /^import /m.test(code) || /^package /m.test(code);
    return { file: (pub ? pub[1] : 'Main') + '.java', text: (hasImports ? '' : IMPORTS) + '\n' + code + '\n', offset: 1, hint };
  }
  const head = `public class Main { public static void main(String[] args)${nothrows ? '' : ' throws Exception'} {`;
  return { file: 'Main.java', text: IMPORTS + '\n' + head + '\n' + code + '\n}}\n', offset: 2, hint: 'Main' };
}

const norm = (s) => s.replace(/\s+/g, ' ').trim();

function main() {
  const files = process.argv.slice(2);
  if (!files.length) { console.error('Uzycie: node ocp_verify.cjs <plik.txt>...'); process.exit(2); }
  const root = fs.mkdtempSync(path.join(os.tmpdir(), 'ocp_verify_'));
  const checks = [];
  let n = 0;
  const addCase = (code, nothrows, mode) => {
    const id = String(n++).padStart(5, '0');
    const dir = path.join(root, `p${n % 4}`, id);
    fs.mkdirSync(dir, { recursive: true });
    const src = makeSources(code, nothrows);
    fs.writeFileSync(path.join(dir, src.file), src.text);
    fs.writeFileSync(path.join(dir, 'mode'), mode);
    if (src.hint) fs.writeFileSync(path.join(dir, 'main'), src.hint);
    return { id, offset: src.offset };
  };
  let total = 0;
  for (const f of files) {
    for (const q of parse(f).quiz) {
      if (!q.verify) continue;
      total++;
      const tokens = q.verify.split(/\s+/);
      const nothrows = tokens.includes('nothrows');
      const mode = tokens[0];
      const where = `${path.basename(f)}:${q.line}`;
      if (mode === 'opt-ok' || mode === 'opt-ce') {
        const opts = [q.good, ...q.bad].map((o) => ({ o, good: o === q.good, c: addCase(q.code ? q.code.split('___').join(o) : o, nothrows, 'compile') }));
        checks.push({ where, mode, opts });
      } else {
        if (!q.code) throw new Error(`${where}: tryb ${mode} wymaga kodu`);
        const run = ['out', 'exc', 'ok'].includes(mode);
        const c = addCase(q.code, nothrows, run ? 'run' : 'compile');
        const markers = tokens.slice(1).filter((t) => /^n\d+$/.test(t));
        const markerLines = markers.map((m) => {
          const idx = q.code.split('\n').findIndex((l) => new RegExp(`//.*\\b${m}\\b`).test(l));
          if (idx < 0) throw new Error(`${where}: brak znacznika ${m}`);
          return idx + 1;
        });
        checks.push({ where, mode, c, q, arg: tokens[1], markerLines, slashes: tokens.includes('slashes') });
      }
    }
  }
  const java = process.env.JAVA_HOME ? path.join(process.env.JAVA_HOME, 'bin', 'java') : 'java';
  const harness = path.join(__dirname, 'OcpVerify.java');
  const results = {};
  const parts = fs.readdirSync(root);
  let pending = parts.length;
  if (!pending) { console.log('Brak pytan do weryfikacji.'); return; }
  parts.forEach((p) => {
    const proc = spawn(java, [harness, path.join(root, p)], { stdio: ['ignore', 'pipe', 'inherit'] });
    let buf = '';
    proc.stdout.on('data', (d) => { buf += d; });
    proc.on('close', () => {
      buf.split(/\r?\n/).filter(Boolean).forEach((l) => { const [id, ...rest] = l.split('\t'); results[id] = rest; });
      if (--pending === 0) report();
    });
  });

  function report() {
    const errors = [];
    const dec = (s) => Buffer.from(s ?? '', 'base64').toString('utf8');
    const res = (c) => results[c.id] ?? ['MISSING'];
    for (const ch of checks) {
      if (ch.opts) {
        for (const { o, good, c } of ch.opts) {
          const compiled = res(c)[0] === 'COMPILED';
          const want = (ch.mode === 'opt-ok') === good;
          if (compiled !== want) errors.push(`${ch.where}: opcja "${o}" ${compiled ? 'kompiluje sie' : 'NIE kompiluje sie: ' + dec(res(c)[2]).slice(0, 160)}`);
        }
        continue;
      }
      const r = res(ch.c);
      const [kind] = r;
      const ceInfo = () => kind === 'CE' ? ` [linie ${r[1].split(',').map((x) => +x - ch.c.offset).join(',')}: ${dec(r[2]).slice(0, 200)}]` : '';
      if (ch.mode === 'out') {
        if (kind !== 'OK') { errors.push(`${ch.where}: oczekiwano wyniku, jest ${kind} ${r[1] && kind === 'EXC' ? r[1] : ''}${ceInfo()}`); continue; }
        // flaga "slashes": wyniki ze sciezkami porownywane w zapisie Unix (weryfikacja dziala tez na Windows)
        const raw = dec(r[1]);
        const actual = norm(ch.slashes ? raw.replace(/\\/g, '/') : raw);
        if (actual !== norm(ch.q.good)) errors.push(`${ch.where}: wynik "${actual}" != poprawna "${ch.q.good}"`);
        ch.q.bad.forEach((b) => { if (norm(b) === actual) errors.push(`${ch.where}: bledna opcja "${b}" rowna wynikowi`); });
      } else if (ch.mode === 'exc') {
        if (kind !== 'EXC' || r[1] !== ch.arg) errors.push(`${ch.where}: oczekiwano wyjatku ${ch.arg}, jest ${kind} ${kind === 'EXC' ? r[1] : kind === 'OK' ? '"' + norm(dec(r[1])) + '"' : ''}${ceInfo()}`);
      } else if (ch.mode === 'ce') {
        if (kind !== 'CE') { errors.push(`${ch.where}: oczekiwano bledu kompilacji, jest ${kind}`); continue; }
        if (ch.markerLines.length) {
          const got = [...new Set(r[1].split(',').map((x) => +x - ch.c.offset))].sort((a, b) => a - b).join(',');
          const want = [...ch.markerLines].sort((a, b) => a - b).join(',');
          if (got !== want) errors.push(`${ch.where}: bledy w liniach ${got}, oczekiwano ${want}${ceInfo()}`);
        }
      } else if (ch.mode === 'ok') {
        if (kind !== 'OK') errors.push(`${ch.where}: oczekiwano poprawnego dzialania, jest ${kind} ${kind === 'EXC' ? r[1] : ''}${ceInfo()}`);
      } else if (ch.mode === 'cok') {
        if (kind !== 'COMPILED') errors.push(`${ch.where}: oczekiwano kompilacji, jest ${kind}${ceInfo()}`);
      } else {
        errors.push(`${ch.where}: nieznany tryb ${ch.mode}`);
      }
    }
    fs.rmSync(root, { recursive: true, force: true });
    errors.forEach((e) => console.log('BLAD ' + e));
    console.log(`Zweryfikowano ${total} pytan, bledow: ${errors.length}`);
    if (errors.length) process.exitCode = 1;
  }
}

main();
