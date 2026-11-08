// Wspólne narzędzia źródeł praktyki rozdziałów 19+.
// probes(header, rows): pytania z kodem uruchamianym przez verify_vitest_practice.cjs.
// Wiersz: [pytanie, wyrażenie, odpowiedź, wyjaśnienie, dystraktory?]. Kod pytania wypisuje wynik przez console.log,
// więc odpowiedź w quizie jest sprawdzona prawdziwym uruchomieniem, a nie tylko zadeklarowana.
function distractors(value, given = []) {
  const text = String(value);
  const pool = [...given.map(String)];
  if (typeof value === 'boolean') pool.push(String(!value), 'undefined', 'null', 'Błąd w czasie wykonania');
  else if (typeof value === 'number') pool.push(String(value + 1), String(Math.max(0, value - 1)), String(value + 2), String(value * 2 + 3), 'NaN');
  else pool.push('undefined', 'null', 'pusty tekst', 'Błąd w czasie wykonania');
  const unique = [...new Set(pool)].filter(item => item !== text);
  if (unique.length < 3) throw Error('Za mało dystraktorów: ' + text);
  return unique.slice(0, 3);
}
function probes(header, rows) {
  return rows.map(([question, expression, answer, why, given]) => {
    const [b, c, d] = distractors(answer, given);
    const code = (header ? header + '\n' : '') + 'console.log(' + expression + ');';
    return [question, String(answer), b, c, d, why, code];
  });
}
// Liczniki pilnowane w każdym źródle, żeby brak pytania wyszedł przed zapisem JSON.
function lesson({ exercises, concepts, probeRows, project, mutants, header, dependencyRoot, exerciseCount = 30, questionCount = 100 }) {
  const executed = probes(header, probeRows);
  const quiz = [...concepts, ...executed];
  if (exercises.length !== exerciseCount || quiz.length !== questionCount) {
    throw Error(`Liczby: zadania ${exercises.length}/${exerciseCount}, pytania ${quiz.length}/${questionCount} (koncepcje ${concepts.length}, próbki ${executed.length})`);
  }
  return { exercises, quiz, project, mutants, probeCount: executed.length, ...(dependencyRoot ? { dependencyRoot } : {}) };
}
module.exports = { probes, lesson };
