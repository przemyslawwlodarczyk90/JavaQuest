// Dopisuje ćwiczenia i pytania quizowe do istniejącej lekcji (NIE rusza teorii ani istniejących
// pozycji - nowe trafiają na koniec, więc numeracja pytań "widzianych" przez użytkowników zostaje).
//
// Użycie: node scripts/content-migration/append_practice.js <lekcja.json> <dodatki.json>
//   dodatki.json: { "exercises": [{prompt, hint, solution}], "quiz": [{question, code?, options:{A,B,C,D}, correct, explanation}] }
// albo:   node scripts/content-migration/append_practice.js <paczka.json>
//   paczka.json: { "<ścieżka do lekcji.json>": <dodatki jak wyżej>, ... } - każda lekcja walidowana osobno.
//
// Waliduje: kompletność pól, opcje A-D, poprawna litera, duplikaty treści pytań/ćwiczeń, cyrylicę.
// Przy błędzie NIC nie zapisuje.
const fs = require('fs');

const args = process.argv.slice(2);
if (args.length === 1) {
  const batch = JSON.parse(fs.readFileSync(args[0], 'utf8'));
  let failed = 0;
  for (const [lessonPath, add] of Object.entries(batch)) if (!appendTo(lessonPath, add)) failed++;
  process.exit(failed ? 1 : 0);
} else if (args.length === 2) {
  process.exit(appendTo(args[0], JSON.parse(fs.readFileSync(args[1], 'utf8'))) ? 0 : 1);
} else {
  console.error('Użycie: node append_practice.js <lekcja.json> <dodatki.json> | <paczka.json>');
  process.exit(2);
}

function appendTo(lessonPath, add) {
  const lesson = JSON.parse(fs.readFileSync(lessonPath, 'utf8'));
  const errors = [];
  const nonEmpty = (v) => typeof v === 'string' && v.trim().length > 0;

  (add.exercises || []).forEach((e, i) => {
    for (const k of ['prompt', 'hint', 'solution']) if (!nonEmpty(e[k])) errors.push(`exercise[${i}] bez pola ${k}`);
  });
  (add.quiz || []).forEach((q, i) => {
    if (!nonEmpty(q.question)) errors.push(`quiz[${i}] bez treści pytania`);
    if (q.code !== undefined && !nonEmpty(q.code)) errors.push(`quiz[${i}] ma puste pole code`);
    const keys = Object.keys(q.options || {}).join('');
    if (keys !== 'ABCD') errors.push(`quiz[${i}] opcje muszą być dokładnie A,B,C,D (jest: ${keys})`);
    for (const k of 'ABCD') if (q.options && !nonEmpty(q.options[k])) errors.push(`quiz[${i}] pusta opcja ${k}`);
    if (!'ABCD'.includes(q.correct) || q.correct.length !== 1) errors.push(`quiz[${i}] zła litera correct: ${q.correct}`);
    if (!nonEmpty(q.explanation)) errors.push(`quiz[${i}] bez wyjaśnienia`);
    if (q.options && new Set(Object.values(q.options)).size !== 4) errors.push(`quiz[${i}] zdublowane opcje`);
  });

  const norm = (s) => s.toLowerCase().replace(/\s+/g, ' ').trim();
  const seenQ = new Set((lesson.quiz || []).map((q) => norm(q.question + (q.code || ''))));
  for (const q of add.quiz || []) {
    const key = norm(q.question + (q.code || ''));
    if (seenQ.has(key)) errors.push(`duplikat pytania: ${q.question.slice(0, 70)}`);
    seenQ.add(key);
  }
  const seenE = new Set((lesson.exercises || []).map((e) => norm(e.prompt)));
  for (const e of add.exercises || []) {
    if (seenE.has(norm(e.prompt))) errors.push(`duplikat ćwiczenia: ${e.prompt.slice(0, 70)}`);
    seenE.add(norm(e.prompt));
  }

  const addText = JSON.stringify(add);
  const cyr = (addText.match(/[Ѐ-ӿ]/g) || []).length;
  if (cyr) errors.push(`cyrylica w dodatkach: ${cyr} znaków`);

  if (errors.length) {
    console.error(`BŁĘDY (${lessonPath}):\n - ` + errors.join('\n - '));
    process.exit(1);
  }

  lesson.exercises = [...(lesson.exercises || []), ...(add.exercises || [])];
lesson.quiz = [...(lesson.quiz || []), ...(add.quiz || [])];
fs.writeFileSync(lessonPath, JSON.stringify(lesson, null, 2) + '\n', 'utf8');

const dist = { A: 0, B: 0, C: 0, D: 0 };
lesson.quiz.forEach((q) => dist[q.correct]++);
const withCode = lesson.quiz.filter((q) => q.code).length;
console.log(`OK ${lessonPath}: ex=${lesson.exercises.length} quiz=${lesson.quiz.length} (z kodem: ${withCode}) ` +
  `rozkład odpowiedzi A/B/C/D=${dist.A}/${dist.B}/${dist.C}/${dist.D}`);
return true;
}
