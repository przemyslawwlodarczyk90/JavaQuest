// Wspolne narzedzia generatorow lekcji toru Sieci i Cyberbezpieczenstwo.
// Lekcja: 14 blokow teorii, 30 zadan, 100 pytan; ilustracje SVG w frontend/public/lesson-assets/netsec/.
const fs = require('fs');
const path = require('path');

const LET = ['A', 'B', 'C', 'D'];

// Quiz z deterministycznym tasowaniem odpowiedzi (stale ziarno = stabilne pliki przy ponownym generowaniu).
function quizBuilder(seed) {
  let s = seed;
  const rnd = () => { s = (s * 1103515245 + 12345) % 2147483648; return s / 2147483648; };
  const quiz = [];
  // Pozycja poprawnej odpowiedzi pochodzi z potasowanych blokow [0,1,2,3] — rowny rozklad A/B/C/D.
  let deck = [];
  const nextSlot = () => {
    if (!deck.length) deck = [0, 1, 2, 3].map((v) => [rnd(), v]).sort((x, y) => x[0] - y[0]).map((x) => x[1]);
    return deck.shift();
  };
  const add = (question, correct, wrongs, explanation, code) => {
    const uniqueWrongs = wrongs.map(String).filter((w, i, arr) => w !== String(correct) && arr.indexOf(w) === i).slice(0, 3);
    if (uniqueWrongs.length !== 3) throw new Error('za malo opcji: ' + question + ' ' + JSON.stringify([correct, ...uniqueWrongs]));
    const shuffled = uniqueWrongs.map((o) => [rnd(), o]).sort((x, y) => x[0] - y[0]).map((x) => x[1]);
    const slot = nextSlot();
    const order = [...shuffled.slice(0, slot), String(correct), ...shuffled.slice(slot)];
    const options = {}; order.forEach((o, i) => { options[LET[i]] = o; });
    const item = { question, options, correct: LET[slot], explanation };
    if (code) item.code = code;
    if (quiz.some((x) => x.question === question)) throw new Error('powtorzone pytanie: ' + question);
    quiz.push(item);
  };
  return { quiz, add, rnd };
}

function exerciseBuilder() {
  const exercises = [];
  const ex = (prompt, hint, solution) => exercises.push({ prompt, hint, solution });
  return { exercises, ex };
}

const fmt = (n) => n.toLocaleString('pl-PL').replace(/\s/g, ' ');

function writeLesson(out, theory, exercises, quiz) {
  if (theory.length !== 14) throw new Error('teoria: ' + theory.length);
  if (exercises.length !== 30) throw new Error('zadania: ' + exercises.length);
  if (quiz.length !== 100) throw new Error('quiz: ' + quiz.length);
  for (const b of theory) {
    if (!b.type || !b.heading || !b.body) throw new Error('niepelny blok: ' + JSON.stringify(b).slice(0, 80));
    if (b.code === undefined) b.code = null;
    if (b.image && !b.imageAlt) throw new Error('brak imageAlt: ' + b.image);
  }
  const counts = {}; quiz.forEach((x) => { counts[x.correct] = (counts[x.correct] || 0) + 1; });
  fs.writeFileSync(out, JSON.stringify({ theory, exercises, quiz }, null, 2) + '\n');
  console.log('theory', theory.length, 'exercises', exercises.length, 'quiz', quiz.length, 'odpowiedzi', JSON.stringify(counts));
}

// ---------- SVG ----------
const C = {
  bg: '#fbf8f3', card: '#ffffff', ink: '#1f1a14', muted: '#6b6257', line: '#d9d0c3',
  net: '#2f6fb3', netFill: '#dce9f7', host: '#c46a12', hostFill: '#fbe6cf', ok: '#2e7d4f', okFill: '#dcefe3',
  no: '#b23b3b', noFill: '#f6dcdc', vio: '#6a4c9c', vioFill: '#e9e1f5',
};
const FONT = "font-family=\"'Segoe UI', system-ui, -apple-system, sans-serif\"";
const MONO = "font-family=\"'Cascadia Mono', Consolas, 'DejaVu Sans Mono', monospace\"";
const esc = (s) => String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
const svg = (w, h, title, body) => `<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 ${w} ${h}" width="${w}" height="${h}" role="img" aria-label="${esc(title)}">
<title>${esc(title)}</title>
<defs><marker id="arr" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10 z" fill="${C.muted}"/></marker></defs>
<rect width="${w}" height="${h}" rx="14" fill="${C.bg}"/>
${body}
</svg>
`;
const text = (x, y, s, o = {}) => `<text x="${x}" y="${y}" ${o.mono ? MONO : FONT} font-size="${o.size || 14}" fill="${o.fill || C.ink}"${o.anchor ? ` text-anchor="${o.anchor}"` : ''}${o.bold ? ' font-weight="700"' : ''}>${esc(s)}</text>`;
const box = (x, y, w, h, fill = C.card, stroke = C.line, rx = 10) => `<rect x="${x}" y="${y}" width="${w}" height="${h}" rx="${rx}" fill="${fill}" stroke="${stroke}" stroke-width="1.5"/>`;
const line = (x1, y1, x2, y2, o = {}) => `<line x1="${x1}" y1="${y1}" x2="${x2}" y2="${y2}" stroke="${o.color || C.muted}" stroke-width="${o.w || 2}"${o.dash ? ' stroke-dasharray="6 5"' : ''}${o.arrow ? ' marker-end="url(#arr)"' : ''}/>`;
const circle = (x, y, r, fill = C.card, stroke = C.ink) => `<circle cx="${x}" cy="${y}" r="${r}" fill="${fill}" stroke="${stroke}" stroke-width="1.5"/>`;
const node = (x, y, label, o = {}) => (o.square ? box(x - (o.w || 34), y - 16, 2 * (o.w || 34), 32, o.fill || C.card, o.stroke || C.ink, 6) : circle(x, y, o.r || 16, o.fill || C.card, o.stroke || C.ink)) + text(x, y + 5, label, { anchor: 'middle', size: o.size || 12, bold: true, fill: o.color || C.ink });

function writeSvgs(outDir, files) {
  for (const [name, content] of Object.entries(files)) {
    fs.writeFileSync(path.join(outDir, name), content);
    console.log('zapisano', name);
  }
}

module.exports = { quizBuilder, exerciseBuilder, writeLesson, fmt, C, svg, text, box, line, circle, node, esc, writeSvgs };
