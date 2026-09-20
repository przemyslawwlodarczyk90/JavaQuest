// Generator: liczy poprawne odpowiedzi quizu przez WYKONANIE kodu, generuje dystraktory, dopisuje do JSON.
const fs = require('fs'), vm = require('vm');
const ROOT = require('path').resolve(__dirname, '../../../src/main/resources/content') + '/';

function fmt(v) {
  if (typeof v === 'string') return JSON.stringify(v);
  if (Array.isArray(v)) {
    const out = [];
    for (let i = 0; i < v.length; i++) {
      if (!(i in v)) { let n = 0; while (i < v.length && !(i in v)) { n++; i++; } i--; out.push('<' + n + ' empty item' + (n > 1 ? 's' : '') + '>'); }
      else out.push(fmt(v[i]));
    }
    return '[' + out.join(', ') + ']';
  }
  if (v === null) return 'null';
  if (typeof v === 'object') {
    if (v.__opaque !== undefined && Object.keys(v).length === 1) return v.__opaque;
    const tag = Object.prototype.toString.call(v);
    if (tag === '[object Map]' || tag === '[object Set]') {
      const isMap = tag === '[object Map]';
      const items = [];
      // obiekty z piaskownicy sa z innego "realm" - metody prototypu hosta dzialaja dzieki wewnetrznym slotom
      (isMap ? Map : Set).prototype.forEach.call(v, (x, k) => items.push(isMap ? fmt(k) + ' => ' + fmt(x) : fmt(x)));
      const size = items.length;
      return (isMap ? 'Map(' : 'Set(') + size + ') ' + (size ? '{ ' + items.join(', ') + ' }' : '{}');
    }
    const ks = Object.keys(v);
    if (!ks.length) return '{}';
    return '{ ' + ks.map(k => (/^[A-Za-z_$][\w$]*$|^\d+$/.test(k) ? k : JSON.stringify(k)) + ': ' + fmt(v[k])).join(', ') + ' }';
  }
  if (typeof v === 'function') return '[Function]';
  return String(v);
}
function fmtLoose(v) { return typeof v === 'string' ? v : fmt(v); }

const POLY = `globalThis.structuredClone = function sc(v, seen) { seen = seen || new Map(); if (v === null || typeof v !== 'object') { if (typeof v === 'function' || typeof v === 'symbol') { const e = new Error('cannot clone'); e.name = 'DataCloneError'; throw e; } return v; } if (seen.has(v)) return seen.get(v); if (v instanceof Date) return new Date(v.getTime()); let r; if (v instanceof Map) { r = new Map(); seen.set(v, r); v.forEach((x, k) => r.set(sc(k, seen), sc(x, seen))); return r; } if (v instanceof Set) { r = new Set(); seen.set(v, r); v.forEach(x => r.add(sc(x, seen))); return r; } r = Array.isArray(v) ? [] : {}; seen.set(v, r); for (const k of Object.keys(v)) { if (typeof v[k] === 'function') { const e = new Error('cannot clone'); e.name = 'DataCloneError'; throw e; } r[k] = sc(v[k], seen); } return r; };
globalThis.FormData = class FormData { constructor(){ this._e = []; } append(k,v){ this._e.push([k, typeof v === 'string' ? v : String(v)]); } get(k){ const e = this._e.find(p => p[0] === k); return e ? e[1] : null; } getAll(k){ return this._e.filter(p => p[0] === k).map(p => p[1]); } has(k){ return this._e.some(p => p[0] === k); } delete(k){ this._e = this._e.filter(p => p[0] !== k); } set(k,v){ this.delete(k); this.append(k,v); } entries(){ return this._e[Symbol.iterator](); } keys(){ return this._e.map(p => p[0])[Symbol.iterator](); } values(){ return this._e.map(p => p[1])[Symbol.iterator](); } forEach(fn){ this._e.forEach(p => fn(p[1], p[0])); } [Symbol.iterator](){ return this.entries(); } };`;
// Kod wieloplikowy / z import-export uruchamiany jest jako moduly ES w procesie potomnym (modrun.mjs).
// Konwencja: pliki oddzielone naglowkami `// nazwa.js`; wejscie = main.js albo ostatni plik.
function isModuleCode(code) {
  return /^\s*(import|export)\b/m.test(code) || /\bimport\s*\(/.test(code) || /\bimport\.meta\b/.test(code) || /^\/\/ [\w.\/-]+\.m?js\s*$/m.test(code);
}
function reviveArg(a) {
  switch (a.t) {
    case 's': return a.v;
    case 'n': return Number(a.v);
    case 'b': return a.v;
    case 'u': return undefined;
    case 'nl': return null;
    case 'bi': return BigInt(a.v);
    default: return { __opaque: a.v };
  }
}
function runModule(code) {
  const r = require('child_process').spawnSync(process.execPath, ['--experimental-vm-modules', '--no-warnings', require('path').join(__dirname, 'modrun.mjs')], { input: code, encoding: 'utf8', timeout: 15000 });
  let out;
  try { out = JSON.parse(r.stdout); } catch (e) { return { err: 'Error', logs: [], raw: (r.stdout || '') + (r.stderr || '') }; }
  const logs = out.logs.map(a => a.map(reviveArg));
  return out.err ? { err: out.err, logs } : { logs };
}

function run(code) {
  if (isModuleCode(code)) return runModule(code);
  const logs = [];
  const con = { log: (...a) => logs.push(a), error: (...a) => logs.push(a) };
  // wirtualny zegar: setTimeout/clearTimeout wykonuja sie PO glownym kodzie, w kolejnosci (opoznienie, kolejnosc dodania)
  const timers = []; let now = 0;
  const sandbox = {
    console: con,
    setTimeout: (fn, ms, ...a) => { timers.push({ fn, a, due: now + (Number(ms) || 0), id: timers.length + 1 }); return timers.length; },
    clearTimeout: id => { const t = timers.find(x => x.id === id); if (t) t.cancelled = true; },
  };
  try {
    const strict = /^\s*["']use strict["']/.test(code) ? '"use strict";\n' : '';
    vm.runInNewContext(strict + POLY + '\n' + code, sandbox, { timeout: 1500 });
    for (let guard = 0; guard < 1000; guard++) {
      const pend = timers.filter(t => !t.done && !t.cancelled).sort((x, y) => x.due - y.due || x.id - y.id);
      if (!pend.length) break;
      const t = pend[0]; t.done = true; now = t.due;
      const f = t.fn; f(...t.a);
    }
    return { logs };
  } catch (e) { return { err: e.name || 'Error', logs }; }
}

let seed = 12345;
function rnd() { seed = (seed * 1103515245 + 12345) & 0x7fffffff; return seed / 0x7fffffff; }
function shuffle(a) { a = a.slice(); for (let i = a.length - 1; i > 0; i--) { const j = Math.floor(rnd() * (i + 1)); [a[i], a[j]] = [a[j], a[i]]; } return a; }

function candidates(code, res, correct) {
  const c = [];
  const push = s => { if (s !== correct && s.trim() !== '' && !c.includes(s)) c.push(s); };
  const ERRS = ['TypeError', 'ReferenceError', 'SyntaxError', 'RangeError'];
  const TYPES = ['number', 'string', 'boolean', 'object', 'undefined', 'function'];
  if (res.err) {
    ERRS.forEach(push); ['undefined', 'null', '0'].forEach(push);
    return { specific: [], generic: c };
  }
  const flat = res.logs.flat();
  const spec = [];
  const sp = s => { if (s !== correct && s.trim() !== '' && !spec.includes(s)) spec.push(s); };
  if (res.logs.length === 1 && res.logs[0].length === 1) {
    const v = res.logs[0][0];
    if (typeof v === 'number') { [v + 1, v - 1, v * 2, 0, v + 10].forEach(x => sp(String(x))); }
    else if (typeof v === 'boolean') { sp(String(!v)); }
    else if (typeof v === 'string') {
      if (/Error$/.test(v)) ERRS.forEach(e => sp(JSON.stringify(e)));
      else if (TYPES.includes(v)) TYPES.forEach(e => sp(JSON.stringify(e)));
      else {
        const isPath = l => /^["']\.{1,2}\//.test(l);
        (code.match(/"([^"\\\n]*)"/g) || []).filter(l => l !== '"use strict"' && !isPath(l)).forEach(l => sp(l));
        (code.match(/'([^'\\\n]*)'/g) || []).filter(l => !isPath(l)).forEach(l => sp(JSON.stringify(l.slice(1, -1))));
        if (v.endsWith('undefined') && v.length > 9) sp(JSON.stringify(v.slice(0, -9)));
        if (v.includes(': ')) { v.split(': ').forEach(p => sp(JSON.stringify(p))); }
        if (v.includes(' ') && !v.startsWith('[object')) { v.split(' ').forEach(p => sp(JSON.stringify(p))); }
      }
    } else if (Array.isArray(v)) {
      if (v.length > 1) { sp(fmt(v.slice().reverse())); sp(fmt(v.slice(1))); sp(fmt(v.slice(0, -1))); }
      if (v.length && typeof v[0] === 'number') sp(fmt(v.map(x => x + 1)));
      sp(fmt(v.concat(v.length ? [v[v.length - 1]] : [1]))); sp('[]');
    } else if (v && typeof v === 'object' && v.__opaque === undefined) {
      const ks = Object.keys(v);
      const num = ks.every(k => typeof v[k] === 'number');
      if (num) { sp(fmt(Object.fromEntries(ks.map(k => [k, v[k] + 1])))); sp(fmt(Object.fromEntries(ks.map(k => [k, v[k] * 2])))); sp(fmt(Object.fromEntries(ks.map(k => [k, v[k] - 1])))); }
      if (ks.length > 1) { sp(fmt(Object.fromEntries(ks.slice(0, -1).map(k => [k, v[k]])))); }
      sp('{}');
    }
  } else if (res.logs.length === 1) {
    const a = res.logs[0];
    if (a.length >= 2) {
      const sw = a.slice(); [sw[0], sw[1]] = [sw[1], sw[0]]; sp(sw.map(fmtLoose).join(' '));
      a.forEach((x, i) => {
        if (typeof x === 'number') { const b = a.slice(); b[i] = x + 1; sp(b.map(fmtLoose).join(' ')); const d = a.slice(); d[i] = x - 1; sp(d.map(fmtLoose).join(' ')); }
        if (typeof x === 'boolean') { const b = a.slice(); b[i] = !x; sp(b.map(fmtLoose).join(' ')); }
      });
      if (a.length === 2) { sp(fmtLoose(a[0])); sp(fmtLoose(a[1])); }
      if (a.length > 2 && a.every(x => typeof x === 'boolean')) { sp(a.map(x => !x).map(fmtLoose).join(' ')); }
    }
  } else {
    const items = flat.map(fmtLoose);
    if (res.logs.every(l => l.length === 1) && items.length > 1) {
      sp(items.slice(1).join(', ')); sp(items.slice(0, -1).join(', ')); sp(items.slice().reverse().join(', '));
      const last = flat[flat.length - 1];
      if (typeof last === 'number') sp(items.slice(0, -1).concat([String(last + 1)]).join(', '));
      const first = flat[0];
      if (typeof first === 'number') sp([String(first + 1)].concat(items.slice(1)).join(', '));
      if (items.length > 2) sp(items.slice(0, -2).join(', '));
    } else {
      const lines = res.logs.map(a => a.map(fmtLoose).join(' '));
      sp(lines.slice(1).join(', ')); sp(lines.slice(0, -1).join(', ')); sp(lines.slice().reverse().join(', '));
    }
  }
  ['undefined', 'TypeError', 'NaN', 'null', '0', 'ReferenceError', 'false'].forEach(push);
  return { specific: spec, generic: c };
}

function build(code, wrong, expl, idx) {
  code = code.replace(/^\n/, '').replace(/\n$/, '');
  const res = run(code);
  let correct;
  if (res.err) correct = res.err;
  else if (res.logs.length === 1 && res.logs[0].length === 1) correct = fmt(res.logs[0][0]);
  else if (res.logs.length === 0) correct = 'nic (brak wypisania)';
  else correct = res.logs.map(a => a.map(fmtLoose).join(' ')).join(', ');
  let wr = wrong && wrong.length ? wrong.slice() : [];
  if (wr.includes(correct)) throw new Error('wrong contains correct: ' + code);
  if (wr.length < 3) {
    const { specific, generic } = candidates(code, res, correct);
    for (const s of shuffle(specific).concat(generic)) { if (wr.length >= 3) break; if (!wr.includes(s) && s !== correct) wr.push(s); }
  }
  wr = wr.slice(0, 3);
  const pos = ((idx * 7 + 3) % 4);
  const arr = wr.slice(); arr.splice(pos, 0, correct);
  const options = {}; ['A', 'B', 'C', 'D'].forEach((k, i) => options[k] = arr[i]);
  const WL = /\b(JSON|URL|API|HTTP|HTML|CSS|UTF|JVM|GET|POST|PUT|PATCH|DELETE|HEAD)\b/g;
  for (const v of arr) if (/\b[A-Z]{2,}\b/.test(v.replace(WL, ''))) throw new Error('ALLCAPS w opcji: ' + v);
  if (/\b[A-Z][A-Z_]{2,}\b/.test(code.replace(WL, ''))) throw new Error('UPPERCASE identyfikator w kodzie: ' + code);
  if (new Set(arr).size !== 4) throw new Error('duplikaty opcji: ' + JSON.stringify(arr) + ' dla ' + code);
  return { question: 'Co wypisze poniższy kod?\n' + code, options, correct: ['A', 'B', 'C', 'D'][pos], explanation: expl };
}

// conceptual: correct text + 3 wrong
function concept(question, correct, wrong, expl, idx) {
  const pos = ((idx * 5 + 1) % 4);
  const arr = wrong.slice(); arr.splice(pos, 0, correct);
  const options = {}; ['A', 'B', 'C', 'D'].forEach((k, i) => options[k] = arr[i]);
  return { question, options, correct: ['A', 'B', 'C', 'D'][pos], explanation: expl };
}

// data: { ex: [[prompt,hint,solution],...], q: [ ['c', code, expl, wrong?] | ['t', question, correct, wrongs, expl] ] }
function apply(file, data, targetEx, targetQ) {
  const p = ROOT + file;
  const j = JSON.parse(fs.readFileSync(p, 'utf8'));
  // weryfikacja rozwiazan cwiczen (wykonanie)
  data.ex.forEach(([prompt, hint, sol], i) => {
    const r = run(sol);
    if (r.err && !/DOM|document|window|fetch|setInterval|clearInterval/.test(sol)) throw new Error('Cwiczenie ' + i + ' rzuca ' + r.err + ': ' + prompt);
    j.exercises.push({ prompt, hint, solution: sol });
  });
  const base = j.quiz.length;
  data.q.forEach((it, k) => {
    const idx = base + k;
    if (it[0] === 'c') j.quiz.push(build(it[1], it[3], it[2], idx));
    else j.quiz.push(concept(it[1], it[2], it[3], it[4], idx));
  });
  const seen = new Set(); j.quiz.forEach(q => { if (seen.has(q.question)) throw new Error('duplikat pytania: ' + q.question.slice(0, 80)); seen.add(q.question); });
  if (targetEx && j.exercises.length !== targetEx) console.log('UWAGA: cwiczenia', j.exercises.length, '/', targetEx);
  if (targetQ && j.quiz.length !== targetQ) console.log('UWAGA: quiz', j.quiz.length, '/', targetQ);
  fs.writeFileSync(p, JSON.stringify(j, null, 2) + '\n');
  console.log(file, 'ex', j.exercises.length, 'quiz', j.quiz.length);
}
module.exports = { apply, run, fmt, POLY };
