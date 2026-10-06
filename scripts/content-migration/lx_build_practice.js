// Konwerter: plik .txt z cwiczeniami i pytaniami -> pola exercises[] i quiz[] w pliku JSON lekcji Linux.
// Uzycie: node scripts/content-migration/lx_build_practice.js <plik.txt> <plik.json> [oczekiwane_cw] [oczekiwane_quiz]
// Zachowuje theory. Zastepuje exercises i quiz w calosci.
//
// Format pliku .txt:
//   #EX                      nowe cwiczenie
//   P: tresc polecenia       (moze miec kolejne linie)
//   H: podpowiedz            (moze miec kolejne linie)
//   S:                       rozwiazanie - wszystkie kolejne linie az do nastepnego "#EX"/"#Q"
//   #Q                       nowe pytanie
//   Q: tresc pytania         (kolejne linie, np. z poleceniem/wynikiem, az do pierwszej opcji)
//   + poprawna odpowiedz
//   - bledna odpowiedz       (dokladnie 3)
//   E: wyjasnienie           (moze miec kolejne linie)
//   Linie zaczynajace sie od "//" poza blokiem S: sa komentarzami.
//
// Litery poprawnych odpowiedzi sa rozkladane rowno (A/B/C/D) deterministycznym tasowaniem
// zaleznym od nazwy pliku - nie wolno odwolywac sie do liter opcji w wyjasnieniach.
const fs = require('fs');
const path = require('path');
const [src, dst, expEx = '30', expQ = '100'] = process.argv.slice(2);
if (!src || !dst) { console.error('Uzycie: node lx_build_practice.js <plik.txt> <plik.json> [cw] [quiz]'); process.exit(2); }

const lines = fs.readFileSync(src, 'utf8').replace(/\r\n/g, '\n').split('\n');
const exercises = [], quiz = [];
let cur = null, field = null;
const trim = (a) => { while (a.length && a[a.length - 1].trim() === '') a.pop(); while (a.length && a[0].trim() === '') a.shift(); return a.join('\n'); };
const flush = () => {
  if (!cur) return;
  if (cur.kind === 'EX') {
    exercises.push({ prompt: trim(cur.P), hint: trim(cur.H), solution: trim(cur.S) });
  } else {
    quiz.push({ question: trim(cur.Q), good: cur.good, bad: cur.bad, explanation: trim(cur.E) });
  }
  cur = null; field = null;
};
for (const line of lines) {
  if (line === '#EX' || line === '#Q') { flush(); cur = line === '#EX' ? { kind: 'EX', P: [], H: [], S: [] } : { kind: 'Q', Q: [], good: null, bad: [], E: [] }; continue; }
  if (!cur) { if (line.trim() && !line.startsWith('//')) throw new Error('Tekst poza blokiem: ' + line); continue; }
  if (cur.kind === 'EX') {
    if (field !== 'S' && line.startsWith('//')) continue;
    const m = field !== 'S' && line.match(/^([PHS]):\s?(.*)$/);
    if (m) { field = m[1]; if (m[2]) cur[field].push(m[2]); continue; }
    if (!field) { if (line.trim()) throw new Error('Brak pola w cwiczeniu: ' + line); continue; }
    cur[field].push(line);
  } else {
    if (line.startsWith('//')) continue;
    let m;
    if ((m = line.match(/^Q:\s?(.*)$/))) { field = 'Q'; if (m[1]) cur.Q.push(m[1]); continue; }
    if ((m = line.match(/^\+ (.+)$/))) { if (cur.good !== null) throw new Error('Dwie poprawne: ' + cur.Q[0]); cur.good = m[1].trim(); field = 'O'; continue; }
    if ((m = line.match(/^- (.+)$/)) && (field === 'O')) { cur.bad.push(m[1].trim()); continue; }
    if ((m = line.match(/^E:\s?(.*)$/))) { field = 'E'; if (m[1]) cur.E.push(m[1]); continue; }
    if (field === 'Q' || field === 'E') { cur[field].push(line); continue; }
    if (line.trim()) throw new Error('Nieoczekiwana linia w pytaniu: ' + line);
  }
}
flush();

// walidacja
const PL = /[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]/, CYR = /[Ѐ-ӿ]/, LETTER_REF = /\b[Oo]pcj[aeiąę]\s+[ABCD]\b|\b[Oo]dpowied[źz]\w*\s+[ABCD]\b/;
exercises.forEach((e, i) => {
  const id = `cwiczenie ${i + 1}`;
  if (!e.prompt || !e.hint || !e.solution) throw new Error(`Puste pole: ${id}`);
  if (PL.test(e.solution)) throw new Error(`Polskie znaki w solution: ${id}`);
  if (CYR.test(e.prompt + e.hint + e.solution)) throw new Error(`Cyrylica: ${id}`);
});
const seenQ = new Set();
quiz.forEach((q, i) => {
  const id = `pytanie ${i + 1} (${q.question.slice(0, 50)})`;
  if (!q.question || q.good === null || !q.explanation) throw new Error(`Puste pole: ${id}`);
  if (q.bad.length !== 3) throw new Error(`Wymagane 3 bledne opcje: ${id}`);
  const all = [q.good, ...q.bad];
  if (new Set(all).size !== 4) throw new Error(`Powtorzone opcje: ${id}`);
  if (LETTER_REF.test(q.explanation)) throw new Error(`Odwolanie do litery opcji: ${id}`);
  if (CYR.test(q.question + all.join('') + q.explanation)) throw new Error(`Cyrylica: ${id}`);
  if (seenQ.has(q.question)) throw new Error(`Powtorzone pytanie: ${id}`);
  seenQ.add(q.question);
});
const exSet = new Set(exercises.map((e) => e.prompt));
if (exSet.size !== exercises.length) throw new Error('Powtorzone polecenia cwiczen');

// deterministyczne rozlozenie liter poprawnych odpowiedzi
let seed = 0;
for (const ch of path.basename(dst)) seed = (seed * 31 + ch.charCodeAt(0)) >>> 0;
const rnd = () => { seed = (seed * 1664525 + 1013904223) >>> 0; return seed / 4294967296; };
const L = ['A', 'B', 'C', 'D'];
const slots = quiz.map((_, i) => L[i % 4]);
for (let i = slots.length - 1; i > 0; i--) { const j = Math.floor(rnd() * (i + 1)); [slots[i], slots[j]] = [slots[j], slots[i]]; }
const outQuiz = quiz.map((q, i) => {
  const bad = [...q.bad];
  for (let k = bad.length - 1; k > 0; k--) { const j = Math.floor(rnd() * (k + 1)); [bad[k], bad[j]] = [bad[j], bad[k]]; }
  const options = {};
  let b = 0;
  for (const l of L) options[l] = l === slots[i] ? q.good : bad[b++];
  return { question: q.question, options, correct: slots[i], explanation: q.explanation };
});

const out = JSON.parse(fs.readFileSync(dst, 'utf8'));
out.exercises = exercises;
out.quiz = outQuiz;
fs.writeFileSync(dst, JSON.stringify(out, null, 2) + '\n');
const dist = L.map((l) => l + '=' + outQuiz.filter((q) => q.correct === l).length).join(' ');
const warn = (exercises.length != expEx || quiz.length != expQ) ? `  UWAGA: oczekiwano ${expEx}/${expQ}` : '';
console.log(`${dst.split(/[\\/]/).slice(-2).join('/')} - cwiczenia ${exercises.length}, quiz ${quiz.length} (${dist})${warn}`);
