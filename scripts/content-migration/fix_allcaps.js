// Naprawia nadużywanie WIELKICH LITER jako emfazy w polach narracyjnych (prompt/hint/question/
// options/explanation) — NIGDY nie dotyka pol solution/code (prawdziwy kod Java).
// Uzycie: node fix_allcaps.js <plik.json>
const fs = require('fs');

const WHITELIST = new Set([
  'GC','JVM','JDK','JRE','API','HTTP','HTTPS','REST','JSON','XML','SQL','JNI','OOM','CPU','RAM',
  'URL','URI','ID','IO','UI','JEP','LTS','ASCII','UTF','POJO','ORM','CRUD','JAR','WAR','TCP','IP',
  'DNS','JWT','CSS','HTML','SDK','IDE','JPA','DTO','DAO','JIT','G1','ZGC','MB','KB','GB','JAVA',
  'PI', 'E', 'A', 'B', 'C', 'D', 'AND', 'OR', 'XOR', 'NOT', 'TODO', 'FIXME'
]);

const LOW_MAP = { 'Ą':'ą','Ć':'ć','Ę':'ę','Ł':'ł','Ń':'ń','Ó':'ó','Ś':'ś','Ź':'ź','Ż':'ż' };
const UP_MAP  = { 'ą':'Ą','ć':'Ć','ę':'Ę','ł':'Ł','ń':'Ń','ó':'Ó','ś':'Ś','ź':'Ź','ż':'Ż' };

function toLowerPl(word) {
  return word.split('').map(ch => LOW_MAP[ch] || ch.toLowerCase()).join('');
}
function toUpperFirstPl(word) {
  if (!word) return word;
  const first = word[0];
  return (UP_MAP[first] || first.toUpperCase()) + word.slice(1);
}

// Polish-aware "word" = maximal run of letters (ASCII + Polish diacritics), found WITHOUT relying
// on regex \b (which does not understand non-ASCII letters and previously chopped words at the
// boundary before/after an accented character, leaving stray single uppercase letters behind).
const WORD_RE = /[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]+/g;

function fixText(text) {
  if (typeof text !== 'string') return text;
  let result = '';
  let lastIndex = 0;
  let match;
  while ((match = WORD_RE.exec(text)) !== null) {
    const word = match[0];
    const idx = match.index;
    const isAllUpper = word.length >= 3 && word === word.toUpperCase() && word !== word.toLowerCase();
    if (!isAllUpper || WHITELIST.has(word)) continue;
    // sentence-start check: look backwards from idx skipping whitespace/quotes/dashes for . ! ? or start
    let i = idx - 1;
    while (i >= 0 && /[\s"'(—–-]/.test(text[i])) i--;
    const isSentenceStart = i < 0 || /[.!?:\n]/.test(text[i]);
    let replacement = toLowerPl(word);
    if (isSentenceStart) replacement = toUpperFirstPl(replacement);
    result += text.slice(lastIndex, idx) + replacement;
    lastIndex = idx + word.length;
  }
  result += text.slice(lastIndex);
  return result;
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
if (!file) { console.error('Usage: node fix_allcaps.js <file.json>'); process.exit(1); }
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
