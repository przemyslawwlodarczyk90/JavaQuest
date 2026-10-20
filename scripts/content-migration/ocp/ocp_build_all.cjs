// Buduje wszystkie lekcje toru OCP: scripts/content-migration/ocp/src/<rozdzial>/<lekcja>.txt
// -> src/main/resources/content/<rozdzial>/<lekcja>.json i wypisuje sumy pytan.
// Uzycie: node scripts/content-migration/ocp/ocp_build_all.cjs
const fs = require('fs');
const path = require('path');
const { execFileSync } = require('child_process');

const srcRoot = path.join(__dirname, 'src');
const dstRoot = path.join(__dirname, '..', '..', '..', 'src', 'main', 'resources', 'content');
let quiz = 0, exercises = 0;
for (const chapter of fs.readdirSync(srcRoot).sort()) {
  for (const file of fs.readdirSync(path.join(srcRoot, chapter)).filter((f) => f.endsWith('.txt')).sort()) {
    const dst = path.join(dstRoot, chapter, file.replace(/\.txt$/, '.json'));
    if (!fs.existsSync(dst)) throw new Error(`Brak lekcji w ChapterSeedData/content: ${chapter}/${file}`);
    execFileSync(process.execPath, [path.join(__dirname, 'ocp_build.cjs'), path.join(srcRoot, chapter, file), dst], { stdio: 'inherit' });
    const json = JSON.parse(fs.readFileSync(dst, 'utf8'));
    quiz += json.quiz.length;
    exercises += json.exercises.length;
  }
}
console.log(`Razem: quiz ${quiz}, pytania otwarte ${exercises}`);

// Ostatnia lekcja "Podsumowanie - trening": quiz i pytania otwarte ze WSZYSTKICH lekcji toru w jednej
// puli - quiz losuje z calosci, pytania otwarte wyswietlaja sie w losowej kolejnosci.
const POOL_CHAPTER = '_ocp_14_podsumowanie';
const poolQuiz = [], poolOpen = [], seen = new Set();
for (const chapter of fs.readdirSync(srcRoot).sort()) {
  for (const file of fs.readdirSync(path.join(srcRoot, chapter)).filter((f) => f.endsWith('.txt')).sort()) {
    const json = JSON.parse(fs.readFileSync(path.join(dstRoot, chapter, file.replace(/\.txt$/, '.json')), 'utf8'));
    for (const q of json.quiz) {
      const key = q.question + '\n' + (q.code ?? '') + '\n' + q.options[q.correct];
      if (!seen.has(key)) { seen.add(key); poolQuiz.push(q); }
    }
    poolOpen.push(...json.exercises);
  }
}
const pool = {
  theory: [
    { type: 'INTRO', heading: 'Podsumowanie toru — trening całościowy',
      body: `Ta lekcja zbiera wszystkie pytania toru OCP. Zakładka Quiz losuje z całej puli ${poolQuiz.length} pytań quizowych ze wszystkich lekcji naraz — tematy są przemieszane jak na prawdziwym egzaminie. Zakładka z pytaniami otwartymi zawiera wszystkie ${poolOpen.length} pytań ze wszystkich lekcji w losowej kolejności, innej przy każdym wejściu.` },
    { type: 'USAGE', heading: 'Jak trenować',
      body: 'Najpierw przerób lekcje tematyczne, a pulę traktuj jako powtórkę przekrojową. Pytania, na które odpowiesz źle, wracają w kolejnych losowaniach. Przy pytaniach otwartych odpowiedz najpierw własnymi słowami, a dopiero potem odsłoń podpowiedź i odpowiedź wzorcową.' },
  ],
  exercises: poolOpen,
  quiz: poolQuiz,
};
const poolDir = path.join(dstRoot, POOL_CHAPTER);
fs.mkdirSync(poolDir, { recursive: true });
fs.writeFileSync(path.join(poolDir, '01_FinalTraining.json'), JSON.stringify(pool, null, 2) + '\n');
console.log(`Pula: quiz ${poolQuiz.length}, pytania otwarte ${poolOpen.length}`);
