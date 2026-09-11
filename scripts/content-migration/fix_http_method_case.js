// Naprawia szkode wyrzadzona przez wczesniejsza wersje fix_allcaps.js, ktora nie miala metod HTTP
// (GET/POST/PUT/PATCH/DELETE/HEAD/OPTIONS) na liscie WHITELIST - w polach narracyjnych
// (prompt/hint/question/options/explanation) zamienialo je na "get"/"post"/... (srodek zdania)
// albo "Get"/"Post"/... (poczatek zdania), zamiast zostawic jako WIELKIE litery (nazwa metody
// protokolu HTTP, nie emfaza). NIGDY nie dotyka pol solution/code (prawdziwy kod Javy, gdzie
// np. map.get(...)/map.put(...) sa legalnie male litery jako wywolania metod Javy).
// Uzycie: node fix_http_method_case.js <plik.json>
const fs = require('fs');

const METHODS = ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'HEAD', 'OPTIONS'];
// Dopasuj TYLKO gdy caly token to metoda (male, Title-case, lub juz WIELKIE) - \b...\b pilnuje granic slowa
const variants = METHODS.flatMap(m => [m, m[0] + m.slice(1).toLowerCase(), m.toLowerCase()]);
const RE = new RegExp('\\b(' + variants.join('|') + ')\\b', 'g');

function fixText(text) {
  if (typeof text !== 'string') return text;
  return text.replace(RE, (match) => match.toUpperCase());
}

function fixExercise(ex) {
  if (ex.prompt) ex.prompt = fixText(ex.prompt);
  if (ex.hint) ex.hint = fixText(ex.hint);
  return ex;
}
function fixQuiz(q) {
  if (q.question) q.question = fixText(q.question);
  if (q.options) {
    for (const k of Object.keys(q.options)) q.options[k] = fixText(q.options[k]);
  }
  if (q.explanation) q.explanation = fixText(q.explanation);
  return q;
}

const file = process.argv[2];
if (!file) { console.error('Usage: node fix_http_method_case.js <file.json>'); process.exit(1); }
const raw = fs.readFileSync(file, 'utf8');
const data = JSON.parse(raw);
let count = 0;
if (Array.isArray(data.exercises)) {
  data.exercises = data.exercises.map(ex => {
    const before = JSON.stringify(ex);
    const fixed = fixExercise(ex);
    if (JSON.stringify(fixed) !== before) count++;
    return fixed;
  });
}
if (Array.isArray(data.quiz)) {
  data.quiz = data.quiz.map(q => {
    const before = JSON.stringify(q);
    const fixed = fixQuiz(q);
    if (JSON.stringify(fixed) !== before) count++;
    return fixed;
  });
}
fs.writeFileSync(file, JSON.stringify(data, null, 2) + '\n', 'utf8');
console.log('Zmodyfikowano wpisow:', count);
