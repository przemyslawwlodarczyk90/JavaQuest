// Wyrównuje rozkład liter poprawnych odpowiedzi (A/B/C/D) w pytaniach DOPISANYCH do lekcji.
//
// Użycie: node scripts/content-migration/rebalance_answers.js <git-ref-bazowy> <lekcja.json>...
//   <git-ref-bazowy> - commit, w którym lekcja miała jeszcze tylko oryginalne pytania; pytania
//   o indeksach >= liczby pytań w tej wersji są traktowane jako dopisane i mogą być przestawione.
//
// Dla każdego dopisanego pytania poprawna opcja zamienia się miejscem z opcją stojącą na literze,
// która jest w CAŁEJ lekcji (oryginały + dopisane) najrzadziej poprawna. Oryginalne pytania nie są
// ruszane (numeracja i odpowiedzi zapisane w podejściach użytkowników zostają spójne). Pytania,
// których wyjaśnienie odwołuje się do liter opcji ("Opcja A to..."), są pomijane.
const fs = require('fs');
const { execSync } = require('child_process');

const [baseRef, ...files] = process.argv.slice(2);
if (!baseRef || files.length === 0) {
  console.error('Użycie: node rebalance_answers.js <git-ref-bazowy> <lekcja.json>...');
  process.exit(2);
}

const LETTERS = ['A', 'B', 'C', 'D'];
const LETTER_REF = /\b[Oo]pcj[aeiąę]\s+[ABCD]\b|\b[Oo]dpowied[źz]\w*\s+[ABCD]\b/;

for (const file of files) {
  const gitPath = file.replace(/\\/g, '/');
  let origCount;
  try {
    origCount = JSON.parse(execSync(`git show ${baseRef}:./${gitPath}`, { encoding: 'utf8', stdio: ['pipe', 'pipe', 'ignore'] })).quiz.length;
  } catch {
    console.error(`POMINIĘTO ${file}: brak pliku w ${baseRef}`);
    continue;
  }
  const lesson = JSON.parse(fs.readFileSync(file, 'utf8'));
  const counts = { A: 0, B: 0, C: 0, D: 0 };
  lesson.quiz.forEach((q) => counts[q.correct]++);

  let moved = 0;
  lesson.quiz.forEach((q, i) => {
    if (i < origCount || LETTER_REF.test(q.explanation)) return;
    const target = LETTERS.reduce((best, l) => (counts[l] < counts[best] ? l : best), q.correct);
    if (target === q.correct) return;
    [q.options[q.correct], q.options[target]] = [q.options[target], q.options[q.correct]];
    counts[q.correct]--;
    counts[target]++;
    q.correct = target;
    moved++;
  });

  fs.writeFileSync(file, JSON.stringify(lesson, null, 2) + '\n', 'utf8');
  console.log(`${file}: przestawiono ${moved}, rozkład A/B/C/D=${counts.A}/${counts.B}/${counts.C}/${counts.D}`);
}
