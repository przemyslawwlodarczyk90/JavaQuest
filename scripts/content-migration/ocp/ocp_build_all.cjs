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
