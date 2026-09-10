// Druga, celowana tura: naprawia dwa typy artefaktow pozostawionych przez fix_allcaps.js:
// 1) sufiksy po apostrofie sklejone z obcym slowem, np. "cache'OW"/"cache'A" -> "cache'ow"/"cache'a"
// 2) krotkie polskie slowa funkcyjne pozostawione w WIELKICH LITERACH (ponizej progu 3 znakow albo
//    pojedyncze, ktore i tak nie sa akronimami) - dokladna lista, NIGDY pojedyncze litery T/A/B/C/D
//    (mogloby to skasowac np. typ generyczny <T> albo etykiety odpowiedzi).
// Dziala WYLACZNIE na prompt/hint/question/options/explanation - NIGDY na solution/code.
const fs = require('fs');

const SHORT_WORDS = ['TO','ZE','JA','NA','OD','CO','ZA','DO','PO','TA','TE','MA'];
const LOW_MAP = { 'Ą':'ą','Ć':'ć','Ę':'ę','Ł':'ł','Ń':'ń','Ó':'ó','Ś':'ś','Ź':'ź','Ż':'ż' };
function toLowerPl(w){ return w.split('').map(ch=>LOW_MAP[ch]||ch.toLowerCase()).join(''); }
const UP_MAP  = { 'ą':'Ą','ć':'Ć','ę':'Ę','ł':'Ł','ń':'Ń','ó':'Ó','ś':'Ś','ź':'Ź','ż':'Ż' };
function toUpperFirstPl(w){ if(!w) return w; return (UP_MAP[w[0]]||w[0].toUpperCase())+w.slice(1); }

const WORD_RE = /[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]+/g;

function fixText(text) {
  if (typeof text !== 'string') return text;
  // 1) apostrophe-suffix bug: word'UPPERSUFFIX -> word'lowersuffix
  text = text.replace(/([a-ząćęłńóśźżA-Za-z]')([A-ZĄĆĘŁŃÓŚŹŻ]{1,3})\b/g, (m, pre, suf) => pre + toLowerPl(suf));

  // 2) short Polish function words left in ALL CAPS mid-text
  let result = '', lastIndex = 0, match;
  WORD_RE.lastIndex = 0;
  while ((match = WORD_RE.exec(text)) !== null) {
    const word = match[0];
    const idx = match.index;
    if (!SHORT_WORDS.includes(word)) continue;
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

function fixExercise(ex){ if(ex.prompt) ex.prompt=fixText(ex.prompt); if(ex.hint) ex.hint=fixText(ex.hint); return ex; }
function fixQuiz(q){
  if(q.question) q.question=fixText(q.question);
  if(q.options) for(const k of Object.keys(q.options)) q.options[k]=fixText(q.options[k]);
  if(q.explanation) q.explanation=fixText(q.explanation);
  return q;
}

const file = process.argv[2];
if (!file) { console.error('Usage: node fix_allcaps_residual.js <file.json>'); process.exit(1); }
const data = JSON.parse(fs.readFileSync(file, 'utf8'));
let count = 0;
if (Array.isArray(data.exercises)) data.exercises = data.exercises.map(ex => { const b=JSON.stringify(ex); const f=fixExercise(ex); if(JSON.stringify(f)!==b) count++; return f; });
if (Array.isArray(data.quiz)) data.quiz = data.quiz.map(q => { const b=JSON.stringify(q); const f=fixQuiz(q); if(JSON.stringify(f)!==b) count++; return f; });
fs.writeFileSync(file, JSON.stringify(data, null, 2) + '\n', 'utf8');
console.log('Zmodyfikowano wpisow (runda 2):', count);
