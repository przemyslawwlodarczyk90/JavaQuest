// node scripts/content-migration/react/verify_typescript_practice.cjs <katalog-pakietu-typescript>
// Kontrola treści: schemat, kompletność, rzeczywiste typowanie, wyniki kodu i odtwarzalność.
const fs = require('node:fs');
const path = require('node:path');
const vm = require('node:vm');
const assert = require('node:assert/strict');
const { execFileSync } = require('node:child_process');
const { createRequire } = require('node:module');
const root = path.resolve(__dirname, '../../..');
const ts = require(path.resolve(process.argv[2] ?? path.join(root, 'target/react-typescript-qa/node_modules/typescript')));
const chapter = '_react_01_typescript_wprowadzenie';
const frontendRequire = createRequire(path.join(root, 'frontend/package.json'));
const entries = [
  ['01', '01_WhatIsTypeScriptAndWhy', 20, 60],
  ['02', '02_BasicTypesAndInference', 30, 100],
  ['03', '03_ObjectTypesInterfacesAndOptional', 30, 100],
  ['04', '04_UnionsLiteralsAndNarrowing', 30, 100],
  ['05', '05_FunctionsGenericsAndUtilityTypes', 30, 100],
  ['06', '06_JsxVsTsxHowWeWillLearn', 30, 100],
];
const sources = new Map();
const expectedDiagnostics = new Map();
const lessonData = [];
const virtual = name => path.join(root, 'target/react-typescript-qa/samples', name + '.ts');
function addSample(name, code, errors = [], extension = '.tsx') {
  const file = virtual(name).replace(/\.ts$/, extension);
  sources.set(file, code + '\nexport {};');
  expectedDiagnostics.set(file, errors);
  return file;
}
const quizErrors = { '01-44': [2551], '01-45': [7006], '03-86': [2353] };
for (const [number, slug, exercises, questions] of entries) {
  const file = path.join(root, 'src/main/resources/content', chapter, slug + '.json');
  const lesson = JSON.parse(fs.readFileSync(file, 'utf8'));
  assert.equal(lesson.exercises.length, exercises);
  assert.equal(lesson.quiz.length, questions);
  assert.equal(new Set(lesson.exercises.map(e => e.prompt)).size, exercises);
  assert.equal(new Set(lesson.quiz.map(q => q.question + '\n' + (q.code ?? ''))).size, questions);
  for (const e of lesson.exercises) for (const field of ['prompt', 'hint', 'solution']) assert.ok(e[field]?.trim());
  for (const q of lesson.quiz) {
    assert.deepEqual(Object.keys(q.options), ['A', 'B', 'C', 'D']);
    assert.equal(new Set(Object.values(q.options)).size, 4);
    assert.ok(q.options[q.correct]);
    assert.ok(q.explanation.trim());
  }
  for (const letter of 'ABCD') assert.equal(lesson.quiz.filter(q => q.correct === letter).length, questions / 4);
  lesson.exercises.forEach((e, index) => {
    if (!/^(?:type |interface |function |const |let |import |export |\/\*\*)/.test(e.solution)) return;
    if (number === '01' && index === 10) return; // zadanie odtwarzające czysty JS bez adnotacji
    addSample(`lesson${number}/exercise${index + 1}`, e.solution, [], e.solution.startsWith('/**') ? '.jsx' : '.tsx');
  });
  lesson.quiz.forEach((q, index) => {
    if (q.code) addSample(`lesson${number}/question${index + 1}`, q.code, quizErrors[`${number}-${index + 1}`] ?? []);
  });
  lessonData.push({ number, file, lesson });
}
addSample('types', 'export type User = { login: string; name: string; active: boolean };');
addSample('checks/unknown', 'function upper(value: unknown) { return value.toUpperCase(); }', [18046]);
addSample('checks/any', 'function upper(value: any) { return value.toUpperCase(); }');
addSample('checks/missing', 'type User = { name: string }; const user: User = {};', [2741]);
addSample('checks/readonly', 'const user: { readonly id: number } = { id: 1 }; user.id = 2;', [2540]);
addSample('checks/shallow', 'const user: { readonly profile: { name: string } } = { profile: { name: "Anna" } }; user.profile.name = "Jan";');
addSample('checks/record', 'type Status = "OPEN" | "CLOSED"; const labels: Record<Status, string> = { OPEN: "Otwarty" };', [2741]);
addSample('checks/empty', 'const model = { tags: [] }; model.tags.push("api");', [2345]);
addSample('checks/literal', 'let status: "OPEN" = "OPEN"; status = "CLOSED";', [2322]);
addSample('checks/structural', 'type UserName = { name: string }; const full = { name: "Anna", login: "anna" }; const user: UserName = full;');
addSample('checks/view', 'type UserName = { name: string }; const full = { name: "Anna", login: "anna" }; const user: UserName = full; user.login;', [2339]);
const inferenceFile = addSample('checks/inference', [
  'const fixed = "OPEN";', 'let wide = "OPEN";', 'const id = 42;', 'let count = 42;',
  'const object = { done: false };', 'const tuple = [42, "API"] as const;',
  'const model = { tags: [] };', 'const ids = [1, 2];',
  'const labels = ids.map(id => `#${id}`);', 'const found = ids.find((id): boolean => id === 42);',
  'const narrowed = ids.find(id => id === 42);',
].join('\n'));
const options = { strict: true, noEmit: true, target: ts.ScriptTarget.ES2022,
  module: ts.ModuleKind.ESNext, moduleResolution: ts.ModuleResolutionKind.Bundler, skipLibCheck: true, types: [],
  jsx: ts.JsxEmit.ReactJSX, allowJs: true, checkJs: true,
  paths: { react: [path.join(root, 'frontend/node_modules/@types/react/index.d.ts')],
    'react/*': [path.join(root, 'frontend/node_modules/@types/react/*')] } };
function createProgram(extraOptions = {}, files = sources) {
  const settings = { ...options, ...extraOptions };
  const host = ts.createCompilerHost(settings);
  const read = host.readFile.bind(host), exists = host.fileExists.bind(host), directoryExists = host.directoryExists.bind(host);
  host.readFile = file => files.get(path.resolve(file)) ?? read(file);
  host.fileExists = file => files.has(path.resolve(file)) || exists(file);
  host.directoryExists = dir => [...files.keys()].some(file => file.startsWith(path.resolve(dir) + path.sep)) || directoryExists(dir);
  return ts.createProgram([...files.keys()], settings, host);
}
const program = createProgram();
const diagnostics = ts.getPreEmitDiagnostics(program);
assert.ok(diagnostics.every(d => d.file), 'Nieoczekiwana diagnostyka globalna');
for (const [file, expected] of expectedDiagnostics) {
  const actual = diagnostics.filter(d => path.resolve(d.file.fileName) === file).map(d => d.code).sort();
  assert.deepEqual(actual, [...expected].sort(), file + ': nieoczekiwana diagnostyka');
}
const checker = program.getTypeChecker();
const observed = {};
function visit(node) {
  if (ts.isVariableDeclaration(node) && ts.isIdentifier(node.name)) observed[node.name.text] = checker.typeToString(checker.getTypeAtLocation(node.name));
  ts.forEachChild(node, visit);
}
visit(program.getSourceFile(inferenceFile));
assert.deepEqual(observed, {
  fixed: '"OPEN"', wide: 'string', id: '42', count: 'number', object: '{ done: boolean; }',
  tuple: 'readonly [42, "API"]', model: '{ tags: never[]; }', ids: 'number[]', labels: 'string[]', found: 'number | undefined', narrowed: '42 | undefined',
});
// Osobne programy weryfikują pytania zależne od nieobowiązkowych opcji.
const optional = new Map([[virtual('optional'), 'type Contact = { email?: string }; const c: Contact = { email: undefined }; export {};']]);
assert.equal(ts.getPreEmitDiagnostics(createProgram({}, optional)).length, 0);
assert.deepEqual(ts.getPreEmitDiagnostics(createProgram({ exactOptionalPropertyTypes: true }, optional)).map(d => d.code), [2375]);
const indexed = new Map([[virtual('indexed'), 'const ids: number[] = []; const first: number = ids[0]; export {};']]);
assert.equal(ts.getPreEmitDiagnostics(createProgram({}, indexed)).length, 0);
assert.deepEqual(ts.getPreEmitDiagnostics(createProgram({ noUncheckedIndexedAccess: true }, indexed)).map(d => d.code), [2322]);
function run(code) {
  const output = [];
  const js = ts.transpileModule(code, { compilerOptions: { target: ts.ScriptTarget.ES2022, module: ts.ModuleKind.CommonJS, jsx: ts.JsxEmit.ReactJSX } }).outputText;
  vm.runInNewContext(js, { exports: {}, require: frontendRequire, console: { log: (...args) => output.push(args.map(value => value === undefined ? '__undefined__' : JSON.parse(JSON.stringify(value)))) } });
  return output;
}
let quizRuns = 0;
const semanticRuntimeAnswers = { '03-90': 'Anna' }; // odpowiedź w quizie opisuje brak mutacji
for (const { number, lesson } of lessonData) for (const [index, q] of lesson.quiz.entries()) {
  if (!q.code?.includes('console.log')) continue;
  const output = run(q.code);
  if (number === '06' && output.length === 0) continue; // log wewnątrz niewywołanego callbacka
  assert.equal(output.length, 1);
  assert.equal(output[0].length, 1);
  const expected = semanticRuntimeAnswers[`${number}-${index + 1}`] ?? q.options[q.correct];
  assert.equal(output[0][0] === '__undefined__' ? 'undefined' : String(output[0][0]), expected, q.question);
  quizRuns++;
}
const runtimeCases = [
  ['01', 5, [['#42 Eksport']]], ['01', 8, [[5]]], ['01', 10, [[true], [false]]],
  ['02', 14, [['Brak']]], ['02', 15, [['API'], ['Brak']]], ['02', 16, [[6], [null]]],
  ['02', 17, [['API']]], ['02', 26, [['Brak'], ['Brak'], ['']]],
  ['02', 30, [[{ titles: ['Eksport', 'API'], firstTitle: 'Eksport' }]]],
  ['03', 3, [['Anna'], ['Jan (nieaktywny)']]], ['03', 5, [[null]]],
  ['03', 6, [['Brak'], ['']]], ['03', 8, [['Anna Nowak']]],
  ['03', 20, [['W toku']]], ['03', 29, [['Anna', 'Anna Nowak']]], ['03', 30, [[5, 'Otwarty']]],
  ['04', 2, [['btn btn--danger']]], ['04', 3, [['#42'], ['#tmp-1']]],
  ['04', 4, [['API'], ['3.00']]], ['04', 6, [['Brak'], [0]]], ['04', 7, [['Brak'], [0]]],
  ['04', 8, [['api, ui']]], ['04', 22, [['Gość']]], ['04', 23, [['Gość'], ['Gość'], [0]]],
  ['04', 27, [['api, ui'], [null]]], ['04', 29, [['#0']]],
  ['04', 30, [['Gotowe'], ['Zapisywanie'], ['Zapisano #42'], ['Błąd: Brak sieci'], ['Zmienione dane (wersja 2)']]],
  ['05', 2, [[true]]], ['05', 3, [['Cześć Anna']]], ['05', 6, [[1]]],
  ['05', 7, [[1]]], ['05', 9, [['__undefined__'], ['api']]], ['05', 10, [[[3, 4]]]],
  ['05', 11, [[['api']]]], ['05', 15, [['{"api":2,"ui":1}']]],
  ['05', 19, [['API', 'Eksport']]], ['05', 23, [[42, 'table']]], ['05', 27, [[[1, 2]]]],
  ['05', 30, [[[3]], ['{"OPEN":2,"CLOSED":1}'], [['API', 'Login', 'Eksport']]]],
];
for (const [number, index, expected] of runtimeCases) {
  const item = lessonData.find(item => item.number === number);
  assert.deepEqual(run(item.lesson.exercises[index - 1].solution), expected, `${number} zadanie ${index}`);
}
const helpers = lessonData.find(item => item.number === '05').lesson;
assert.deepEqual(run(helpers.exercises[14].solution + '\nconsole.log(JSON.stringify(countBy(["__proto__", "toString", "__proto__"], x => x)));'),
  [['{"api":2,"ui":1}'], ['{"__proto__":2,"toString":1}']]);
assert.deepEqual(run(helpers.exercises[11].solution + '\ntry { paginate([1,2], 0, 1); } catch (error) { console.log(error.name); }'), [['RangeError']]);
const React = frontendRequire('react');
const { renderToStaticMarkup } = frontendRequire('react-dom/server');
const jsxLesson = lessonData.find(item => item.number === '06').lesson;
function loadComponent(index, name) {
  const code = ts.transpileModule(jsxLesson.exercises[index - 1].solution, {
    compilerOptions: { target: ts.ScriptTarget.ES2022, module: ts.ModuleKind.CommonJS, jsx: ts.JsxEmit.ReactJSX },
  }).outputText;
  const context = { exports: {}, require: frontendRequire };
  vm.runInNewContext(code, context);
  return context.exports[name];
}
let renders = 0;
for (const [index, name, props, expected] of [
  [2, 'StatCard', { label: 'Otwarte', value: 3 }, '3'],
  [5, 'Panel', { title: 'Opis', children: 'Treść' }, 'Treść'],
  [13, 'Assignee', { ticket: { assignee: null } }, 'Nieprzypisany'],
  [13, 'Assignee', { ticket: { assignee: 'anna' } }, 'ANNA'],
  [15, 'Counter', {}, '>0<'], [16, 'Selection', {}, 'Brak'], [17, 'SelectedTicket', {}, 'Brak'],
  [18, 'SearchInput', { value: 'api', onValueChange() {} }, 'value="api"'],
  [19, 'SearchInput', { value: 'ui', onValueChange() {} }, 'value="ui"'],
  [20, 'Toggle', { checked: true, onCheckedChange() {} }, 'checked=""'],
  [22, 'FocusInput', {}, 'Fokus'], [26, 'Title', { title: 'DevDesk' }, 'DevDesk'],
  [27, 'Badge', { label: 'Nowy' }, 'badge--primary'],
  [28, 'Panel', { title: 'Opis', children: 'Treść' }, 'Treść'],
]) {
  const Component = loadComponent(index, name);
  assert.ok(renderToStaticMarkup(React.createElement(Component, props)).includes(expected), name);
  renders++;
}
const TicketTable = loadComponent(30, 'TicketTable');
const selected = [];
const tableProps = { tickets: [{ id: 1, title: 'API', assignee: null }, { id: 2, title: 'Login', assignee: 'anna' }], selectedId: 2, onSelect: id => selected.push(id) };
const tableHtml = renderToStaticMarkup(React.createElement(TicketTable, tableProps));
assert.ok(tableHtml.includes('row--selected')); assert.ok(tableHtml.includes('—')); renders++;
function clickButtons(element) {
  if (!React.isValidElement(element)) return;
  if (element.type === 'button') element.props.onClick();
  React.Children.forEach(element.props.children, clickButtons);
}
clickButtons(TicketTable(tableProps));
assert.deepEqual(selected, [1, 2]);
const SaveForm = loadComponent(21, 'SaveForm');
let prevented = false, saved = 0;
SaveForm({ onSave: () => saved++ }).props.onSubmit({ preventDefault: () => { prevented = true; } });
assert.ok(prevented); assert.equal(saved, 1);
const snapshots = lessonData.map(({ file }) => [file, fs.readFileSync(file, 'utf8')]);
execFileSync(process.execPath, [path.join(__dirname, 'write_next_practice.cjs')], { cwd: root });
for (const [file, original] of snapshots) assert.equal(fs.readFileSync(file, 'utf8'), original);
console.log(`OK TypeScript ${ts.version}: ${entries.reduce((sum, e) => sum + e[2], 0)} zadań / ${entries.reduce((sum, e) => sum + e[3], 0)} pytań; ${sources.size} plików sprawdzonych, 11 typów inferencji, 4 warianty opcji.`);
console.log(`OK: ${quizRuns} wyników kodu quizowego, ${runtimeCases.length} rozwiązań i odtwarzalność generatora.`);
console.log(`OK: ${renders} renderów React DOM; callbacki tabeli i formularza; słownik ze szczególnymi kluczami oraz walidacja paginacji.`);
