// Konwerter lekcji toru OCP: plik .txt -> JSON lekcji (theory/exercises/quiz).
// Uzycie: node scripts/content-migration/ocp/ocp_build.cjs <plik.txt> <plik.json> [quiz] [cwiczenia]
//   Liczby [quiz]/[cwiczenia] sa opcjonalne - gdy podane, skrypt ostrzega o niezgodnosci.
//   Pliki .case dla weryfikatora kodu (OcpVerify.java) zapisuje ocp_verify.cjs, nie ten skrypt.
//
// Format pliku .txt:
//   #T TYP Naglowek          blok teorii (TYP z ContentBlockType), dalej tresc; opcjonalnie jeden
//                            blok kodu w ``` ... ``` trafia do pola "code"
//   #Q                       pytanie quizu
//   Q: tresc                 (kolejne linie az do kodu lub opcji)
//   ``` ... ```              opcjonalny kod pytania (pole "code")
//   + poprawna               dokladnie jedna
//   - bledna                 dokladnie trzy
//   X: tryb                  opcjonalna weryfikacja kodu kompilatorem (patrz ocp_verify.cjs)
//   E: wyjasnienie           (kolejne linie)
//   #EX                      pytanie otwarte / zadanie
//   P: tresc  H: podpowiedz  S: odpowiedz (wszystkie kolejne linie)
//   Linie zaczynajace sie od "//" poza blokami kodu i S: sa komentarzami.
//
// Litery poprawnych odpowiedzi rozkladane sa rowno (A/B/C/D) deterministycznie od nazwy pliku -
// w wyjasnieniach nie wolno odwolywac sie do liter opcji.
const fs = require('fs');
const path = require('path');

const TYPES = new Set(['INTRO', 'DEFINITION', 'ANALOGY', 'VISUAL_EXAMPLE', 'CODE_BASIC', 'CODE_PRACTICAL',
  'STEP_BY_STEP', 'USAGE', 'NOTE', 'PITFALL', 'CODE_WRONG', 'CODE_RIGHT', 'WHEN_TO_USE', 'SUMMARY', 'API_REFERENCE']);
const FENCE = /^```(java)?\s*$/;

function parse(src) {
  const lines = fs.readFileSync(src, 'utf8').replace(/\r\n/g, '\n').split('\n');
  const theory = [], quiz = [], exercises = [];
  let cur = null, field = null, inCode = false;
  const trim = (a) => { a = [...a]; while (a.length && a[a.length - 1].trim() === '') a.pop(); while (a.length && a[0].trim() === '') a.shift(); return a.join('\n'); };
  const flush = () => {
    if (!cur) return;
    if (inCode) throw new Error('Niezamkniety blok kodu: ' + JSON.stringify(cur).slice(0, 80));
    if (cur.kind === 'T') {
      const b = { type: cur.type, heading: cur.heading, body: trim(cur.body) };
      if (cur.code) b.code = trim(cur.code);
      theory.push(b);
    } else if (cur.kind === 'Q') {
      quiz.push({ question: trim(cur.Q), code: cur.code ? trim(cur.code) : null, good: cur.good, bad: cur.bad,
        verify: cur.X, explanation: trim(cur.E), line: cur.line });
    } else {
      exercises.push({ prompt: trim(cur.P), hint: trim(cur.H), solution: trim(cur.S) });
    }
    cur = null; field = null;
  };
  lines.forEach((line, idx) => {
    if (inCode) {
      if (FENCE.test(line)) { inCode = false; return; }
      cur.code.push(line); return;
    }
    let m;
    if ((m = line.match(/^#T (\w+) (.+)$/))) {
      flush();
      if (!TYPES.has(m[1])) throw new Error(`Nieznany typ bloku ${m[1]} (linia ${idx + 1})`);
      cur = { kind: 'T', type: m[1], heading: m[2].trim(), body: [], code: null }; return;
    }
    if (line === '#Q') { flush(); cur = { kind: 'Q', Q: [], code: null, good: null, bad: [], X: null, E: [], line: idx + 1 }; field = 'Q'; return; }
    if (line === '#EX') { flush(); cur = { kind: 'EX', P: [], H: [], S: [] }; return; }
    if (!cur) { if (line.trim() && !line.startsWith('//')) throw new Error(`Tekst poza blokiem (linia ${idx + 1}): ${line}`); return; }
    if (cur.kind === 'EX' && field === 'S') { cur.S.push(line); return; }
    if (line.startsWith('//')) return;
    if (FENCE.test(line) && cur.kind !== 'EX') {
      if (cur.code) throw new Error(`Drugi blok kodu (linia ${idx + 1})`);
      cur.code = []; inCode = true; return;
    }
    if (cur.kind === 'T') { cur.body.push(line); return; }
    if (cur.kind === 'EX') {
      if ((m = line.match(/^([PHS]):\s?(.*)$/))) { field = m[1]; if (m[2]) cur[field].push(m[2]); return; }
      if (!field) { if (line.trim()) throw new Error(`Brak pola w zadaniu (linia ${idx + 1})`); return; }
      cur[field].push(line); return;
    }
    if ((m = line.match(/^Q:\s?(.*)$/))) { field = 'Q'; if (m[1]) cur.Q.push(m[1]); return; }
    if ((m = line.match(/^\+ (.+)$/))) { if (cur.good !== null) throw new Error(`Dwie poprawne (linia ${idx + 1})`); cur.good = m[1].trim(); field = 'O'; return; }
    if ((m = line.match(/^- (.+)$/)) && field === 'O') { cur.bad.push(m[1].trim()); return; }
    if ((m = line.match(/^X:\s?(.*)$/))) { cur.X = m[1].trim(); field = 'X'; return; }
    if ((m = line.match(/^E:\s?(.*)$/))) { field = 'E'; if (m[1]) cur.E.push(m[1]); return; }
    if (field === 'Q' || field === 'E') { cur[field].push(line); return; }
    if (line.trim()) throw new Error(`Nieoczekiwana linia w pytaniu (linia ${idx + 1}): ${line}`);
  });
  flush();
  return { theory, quiz, exercises };
}

const LETTER_REF = /\b[Oo]pcj[aeiąę]\s+[ABCD]\b|\b[Oo]dpowied[źz]\w*\s+[ABCD]\b/;
const BANNED = /rekrut/i;

function validate({ theory, quiz, exercises }, src) {
  const all = JSON.stringify({ theory, quiz, exercises });
  if (BANNED.test(all)) throw new Error(`${src}: niedozwolone slowo w tresci`);
  theory.forEach((b, i) => { if (!b.heading || !b.body) throw new Error(`${src}: pusty blok teorii ${i + 1}`); });
  const seen = new Set();
  quiz.forEach((q) => {
    const id = `${src}:${q.line}`;
    if (!q.question || q.good === null || !q.explanation) throw new Error(`Puste pole: ${id}`);
    if (q.bad.length !== 3) throw new Error(`Wymagane 3 bledne opcje: ${id}`);
    if (new Set([q.good, ...q.bad]).size !== 4) throw new Error(`Powtorzone opcje: ${id}`);
    if (LETTER_REF.test(q.explanation)) throw new Error(`Odwolanie do litery opcji: ${id}`);
    const key = q.question + '\n' + (q.code ?? '');
    if (seen.has(key)) throw new Error(`Powtorzone pytanie: ${id}`);
    seen.add(key);
  });
  exercises.forEach((e, i) => { if (!e.prompt || !e.hint || !e.solution) throw new Error(`${src}: puste pole w zadaniu ${i + 1}`); });
  if (new Set(exercises.map((e) => e.prompt)).size !== exercises.length) throw new Error(`${src}: powtorzone zadania`);
}

// Deterministyczny generator (mulberry32) zasiewany nazwa pliku.
function rng(seedText) {
  let h = 1779033703;
  for (const ch of seedText) h = Math.imul(h ^ ch.charCodeAt(0), 3432918353) >>> 0;
  return () => { h = (h + 0x6D2B79F5) >>> 0; let t = h; t = Math.imul(t ^ (t >>> 15), t | 1); t ^= t + Math.imul(t ^ (t >>> 7), t | 61); return ((t ^ (t >>> 14)) >>> 0) / 4294967296; };
}

function toJson(parsed, seed) {
  const rand = rng(seed);
  const LETTERS = ['A', 'B', 'C', 'D'];
  let targets = [];
  const quiz = parsed.quiz.map((q) => {
    if (targets.length === 0) { targets = [...LETTERS]; for (let i = 3; i > 0; i--) { const j = Math.floor(rand() * (i + 1)); [targets[i], targets[j]] = [targets[j], targets[i]]; } }
    const correct = targets.pop();
    const bad = [...q.bad];
    for (let i = bad.length - 1; i > 0; i--) { const j = Math.floor(rand() * (i + 1)); [bad[i], bad[j]] = [bad[j], bad[i]]; }
    const options = {};
    LETTERS.forEach((l) => { options[l] = l === correct ? q.good : bad.shift(); });
    const out = { question: q.question };
    if (q.code) out.code = q.code;
    return { ...out, options, correct, explanation: q.explanation };
  });
  return { theory: parsed.theory, exercises: parsed.exercises, quiz };
}

module.exports = { parse, validate };

if (require.main === module) {
  const [src, dst, expQ, expEx] = process.argv.slice(2);
  if (!src || !dst) { console.error('Uzycie: node ocp_build.cjs <plik.txt> <plik.json> [quiz] [cwiczenia]'); process.exit(2); }
  const parsed = parse(src);
  validate(parsed, src);
  const json = toJson(parsed, path.basename(dst));
  fs.writeFileSync(dst, JSON.stringify(json, null, 2) + '\n');
  const counts = `teoria ${json.theory.length}, zadania ${json.exercises.length}, quiz ${json.quiz.length}`;
  console.log(`${path.basename(dst)}: ${counts}`);
  if ((expQ && +expQ !== json.quiz.length) || (expEx && +expEx !== json.exercises.length)) {
    console.error(`UWAGA: oczekiwano quiz ${expQ ?? '-'}, zadania ${expEx ?? '-'}`);
    process.exitCode = 1;
  }
}
